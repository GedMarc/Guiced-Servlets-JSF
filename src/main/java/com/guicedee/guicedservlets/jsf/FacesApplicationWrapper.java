package com.guicedee.guicedservlets.jsf;

import jakarta.el.ELResolver;
import jakarta.faces.application.Application;
import jakarta.faces.application.ApplicationWrapper;

/**
 * An implementation of {@link ApplicationWrapper}.
 *
 * @author John Yeary
 * @version 1.0
 */
public class FacesApplicationWrapper
		extends ApplicationWrapper
{

	private Application wrapped;

	/**
	 * Constructor that wraps an {@link Application} instance.
	 *
	 * @param wrapped
	 * 		The {@link } to be wrapped.
	 */
	@SuppressWarnings("deprecation")
	public FacesApplicationWrapper(Application wrapped)
	{
		this.wrapped = wrapped;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Application getWrapped()
	{
		return wrapped;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method returns a {@link GuiceELResolverWrapper} that wraps the
	 * default {@link ELResolver}.
	 */
	@Override
	public ELResolver getELResolver()
	{
		return new GuiceELResolverWrapper(getWrapped().getELResolver());
	}
}