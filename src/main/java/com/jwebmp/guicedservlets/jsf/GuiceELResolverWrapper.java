package com.jwebmp.guicedservlets.jsf;

import com.google.inject.Injector;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;
import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.Map;

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

		if (null != obj)
		{
			FacesContext fctx = (FacesContext) context.getContext(FacesContext.class);

			if (null != fctx)
			{

				Map map = fctx.getExternalContext()
				              .getApplicationMap();
				Injector injector = (Injector) map.get(Injector.class.getName());
				if (injector == null)
				{
					throw new NullPointerException("Could not locate "
					                               + "Guice Injector in application scope using"
					                               + " key '" + Injector.class.getName() + "'");
				}
				injector.injectMembers(obj);
			}
		}

		return obj;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ELResolver getWrapped()
	{
		return wrapped;
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

}