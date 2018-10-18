package com.jwebmp.guicedservlets.jsf;

import javax.el.ELResolver;
import javax.faces.application.Application;
import javax.faces.application.ApplicationWrapper;

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