package com.guicedee.guicedservlets.jsf;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.logger.LogFactory;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.beans.FeatureDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

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

	private ELResolver wrapped;

	/**
	 * Default constructor.
	 */
	public GuiceELResolverWrapper()
	{
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
		Object obj = getWrapped().getValue(context, base, property);
		if (obj != null)
		{
			return obj;
		}

		if (base != null)
		{
			if (base instanceof Collections || base instanceof Map)
			{
				return null;
			}
			try
			{

				Field f = base.getClass()
				              .getDeclaredField(property.toString());
				f.setAccessible(true);
				obj = f.get(base);
				if (obj != null)
				{
					GuiceContext.inject()
					            .injectMembers(obj);
				}
			}
			catch (IllegalAccessException | NoSuchFieldException e)
			{
				LogFactory.getLog(GuiceELResolverWrapper.class)
				          .log(Level.FINE, "Could not access field " + property.toString()
				                           + " on "
				                           + " obj '" + base.getClass()
				                                            .getCanonicalName()
				                           + "'", e);
				return getWrapped().getValue(context, base, property);
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
					javax.faces.convert.Converter conv = (Converter) obj;
					FacesContext fctx = (FacesContext) context.getContext(FacesContext.class);
					return conv.getAsObject(fctx, null, null);
				}
			}
			catch (Throwable e)
			{
				try
				{
					return getWrapped().getValue(context, base, property);
				}
				catch (Throwable T)
				{
					LogFactory.getLog(GuiceELResolverWrapper.class)
					          .log(Level.FINE, "Could not locate jsf property " + property.toString()
					                           + " using"
					                           + " key '" + Key.get(Object.class, Names.named(property.toString()))
					                           + "'", e);
				}
				return null;
			}
		}
		context.setPropertyResolved(true);
		return obj;
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
					for (int i = 0; i < params.length; i++)
					{
						Object param = params[i];
						paramTypes[i] = param.getClass();
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