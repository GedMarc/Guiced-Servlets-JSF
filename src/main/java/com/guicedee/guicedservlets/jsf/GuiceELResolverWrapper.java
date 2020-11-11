package com.guicedee.guicedservlets.jsf;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedinjection.pairing.Pair;
import com.guicedee.logger.LogFactory;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.MethodInfo;
import org.apache.commons.beanutils.PropertyUtilsBean;

import jakarta.el.ELContext;
import jakarta.el.ELResolver;
import jakarta.faces.FacesWrapper;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import java.beans.FeatureDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A {@link FacesWrapper} implementation that wraps an {@link ELResolver} and
 * provides a mechanism to inject Guice {@link Injector} implementations.
 *
 * @author John Yeary
 * @version 1.0
 */
public class GuiceELResolverWrapper
		extends ELResolver
		implements FacesWrapper<ELResolver>
{

	private static final Logger log = LogFactory.getLog("GuiceELResolver");

	private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS
			= new ImmutableMap.Builder<Class<?>, Class<?>>()
					  .put(boolean.class, Boolean.class)
					  .put(byte.class, Byte.class)
					  .put(char.class, Character.class)
					  .put(double.class, Double.class)
					  .put(float.class, Float.class)
					  .put(int.class, Integer.class)
					  .put(long.class, Long.class)
					  .put(short.class, Short.class)
					  .put(void.class, Void.class)
					  .build();

	private ELResolver wrapped;

	/**
	 * Default constructor.
	 */
	public GuiceELResolverWrapper()
	{
		//No config required
	}

	/**
	 * Constructor that wraps an {@link ELResolver}.
	 *
	 * @param wrapped
	 * 		The resolver to wrap;
	 */
	public GuiceELResolverWrapper(ELResolver wrapped)
	{
		this.wrapped = wrapped;
	}

	private static final Map<String,Key<Object>> instanceMap = new ConcurrentHashMap<>();
	private static final Map<String,jakarta.faces.convert.Converter<?>> convertorsMap = new ConcurrentHashMap<>();

	private static final Map<String, String> wrappedMap = new ConcurrentHashMap<>();
	private static final Map<String, String> nulledMap = new ConcurrentHashMap<>();
	/**
	 * {@inheritDoc}
	 * <p>
	 * <strong>Note:</strong> This implementation checks to see if the property
	 * can be resolved using the wrapped instance. If the property is resolved,
	 * this method will inject the Guice {@link Injector} implementations.
	 */
	@Override
	public Object getValue(ELContext context, Object base, Object property)
	{
		if(base == null && convertorsMap.containsKey(property.toString()))
		{
			context.setPropertyResolved(true);
			return convertorsMap.get(property.toString()).getAsObject((FacesContext) context.getContext(FacesContext.class),
					null,null);
		}
		if(base == null && instanceMap.containsKey(property.toString()))
		{
			context.setPropertyResolved(true);
			return GuiceContext.get(instanceMap.get(property.toString()));
		}
		if(base == null)
		{
			if(nulledMap.containsKey(property.toString())  && nulledMap.get(property.toString()).equals(property.toString()) ) {
				context.setPropertyResolved(true);
				return null;
			}
			if(wrappedMap.containsKey(property.toString()) && wrappedMap.get(property.toString()).equals(property.toString()) )
			{
				return getWrapped().getValue(context, base, property);
			}
		}
		else
		{
			if(wrappedMap.containsKey(base.toString()) && wrappedMap.get(base.toString()).equals(property.toString()) )
			{
				return getWrapped().getValue(context, base, property);
			}
		}


		Object obj;
		if (base != null)
		{
			if (base instanceof Collections || base instanceof Map)
			{
				return getWrapped().getValue(context, base, property);
			}
			try
			{
				Object objProps = new PropertyUtilsBean().getProperty(base, property.toString());
				context.setPropertyResolved(true);
				return objProps;
			}
			catch (IllegalAccessException | InvocationTargetException e)
			{
				log.log(Level.FINE, "Could not access property " + property.toString()
				                           + " on "
				                           + " obj '" + base.getClass()
				                                            .getCanonicalName()
				                           + "'", e);
				Object value = getWrapped().getValue(context, base, property);
				if(value != null)
				{
					wrappedMap.put(base.toString(), property.toString());
				}
				else
				{
					nulledMap.put(base.toString(), property.toString());
				}
				return value;
			}
			catch (NoSuchMethodException e)
			{
				Object value = getWrapped().getValue(context, base, property);
				if(value != null)
				{
					wrappedMap.put(base.toString(), property.toString());
				}
				else
				{
					log.log(Level.SEVERE,"No Field/Method Found - " + base + " / " + property);
					nulledMap.put(base.toString(), property.toString());
				}
				return value;
			}catch (Throwable e)
			{
				Object value = getWrapped().getValue(context, base, property);
				if(value != null)
				{
					wrappedMap.put(base.toString(), property.toString());
				}
				else
				{
					log.log(Level.SEVERE,"Throwable looking for property - " + base + " / " + property);
					nulledMap.put(base.toString(), property.toString());
				}
				return value;
			}
		}
		else
		{
			try
			{
				obj = GuiceContext.get(Key.get(Object.class, Names.named(property.toString())));
				if (obj.getClass()
				       .isAnnotationPresent(FacesConverter.class))
				{
					jakarta.faces.convert.Converter conv = (Converter) obj;
					FacesContext fctx = (FacesContext) context.getContext(FacesContext.class);
					context.setPropertyResolved(true);
					convertorsMap.put(property.toString(),conv);
					return conv.getAsObject(fctx, null, null);
				}
				context.setPropertyResolved(true);
				instanceMap.put(property.toString(),Key.get(Object.class, Names.named(property.toString())));
				return obj;
			}
			catch (Throwable e)
			{
				try
				{
					Object value = getWrapped().getValue(context, base, property);
					if(value != null)
					{
						wrappedMap.put(property.toString(), property.toString());
					}
					else
					{
						log.log(Level.WARNING,"Couldn't find injection - "+ base + " - " + property);
						nulledMap.put(property.toString(), property.toString());
					}
					return value;
				}
				catch (Throwable T)
				{
					nulledMap.put(property.toString(), property.toString());
					LogFactory.getLog(GuiceELResolverWrapper.class)
					          .log(Level.WARNING, "Could not locate jsf property " + property.toString()
					                              + " using"
					                              + " key '" + Key.get(Object.class, Names.named(property.toString()))
					                              + "'", e);
				}
				return null;
			}
		}
	}

	/** @noinspection JavaReflectionInvocation */
	@Override
	public Object invoke(ELContext context, Object base, Object method, Class<?>[] paramTypes, Object... params)
	{
		try
		{
			if (paramTypes == null)
			{
				if (params != null && params.length > 0)
				{
					paramTypes = new Class<?>[params.length];
					for (Method declaredMethod : base.getClass()
					                                 .getDeclaredMethods())
					{
						if (declaredMethod.getName()
						                  .equals(method.toString()) && declaredMethod.getParameterTypes().length == params.length)
						{
							Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
							for (int i = 0; i < parameterTypes.length; i++)
							{
								Class<?> parameterType = parameterTypes[i];
								Object o = params[i];
								if (o == null)
								{
									paramTypes[i] = parameterType;
									continue;
								}
								parameterType = wrap(parameterType);
								if (o.getClass()
								     .equals(parameterType))
								{
									paramTypes[i] = parameterTypes[i];
								}
							}

						}
					}
				}
				else
				{
					paramTypes = new Class[0];
				}
			}
			Method m = base.getClass()
			               .getMethod(method.toString(), paramTypes);
			return m.invoke(base, params);
		}
		catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
		{
			LogFactory.getLog(GuiceELResolverWrapper.class)
			          .log(Level.FINE, "Could not locate jsf method " + method.toString()
			                           + " using"
			                           + " " + base.getClass()
			                                       .getCanonicalName()
			                           + "'", e);
		}
		return super.invoke(context, base, method, paramTypes, params);
	}

	// safe because both Long.class and long.class are of type Class<Long>
	@SuppressWarnings("unchecked")
	private static <T> Class<T> wrap(Class<T> c)
	{
		return c.isPrimitive() ? (Class<T>) PRIMITIVES_TO_WRAPPERS.get(c) : c;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * <strong>Note:</strong> This returns the wrapped implementation.</p>
	 */
	@Override
	public Class<?> getType(ELContext context, Object base, Object property)
	{
		return getWrapped().getType(context, base, property);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * <strong>Note:</strong> This returns the wrapped implementation.</p>
	 */
	@Override
	public void setValue(ELContext context, Object base, Object property, Object value)
	{
		getWrapped().setValue(context, base, property, value);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * <strong>Note:</strong> This returns the wrapped implementation.</p>
	 */
	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property)
	{
		return getWrapped().isReadOnly(context, base, property);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * <strong>Note:</strong> This returns the wrapped implementation.</p>
	 */
	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base)
	{
		return getWrapped().getFeatureDescriptors(context, base);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * <strong>Note:</strong> This returns the wrapped implementation.</p>
	 */
	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base)
	{
		return getWrapped().getCommonPropertyType(context, base);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ELResolver getWrapped()
	{
		return wrapped;
	}

}
