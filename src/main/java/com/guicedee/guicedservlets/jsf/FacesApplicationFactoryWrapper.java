package com.guicedee.guicedservlets.jsf;

import com.google.inject.Key;
import com.guicedee.cdi.services.NamedBindings;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedservlets.jsf.implementations.JsfNamedBinder;
import io.github.classgraph.ClassInfo;

import jakarta.faces.application.Application;
import jakarta.faces.application.ApplicationFactory;
import jakarta.faces.convert.FacesConverter;
import java.util.Map;

/**
 * An implementation of {@link ApplicationFactory}.
 *
 * @author John Yeary
 * @version 1.0
 */
public class FacesApplicationFactoryWrapper
		extends ApplicationFactory
{

	private ApplicationFactory factory;

	/**
	 * Constructor that wraps an {@link ApplicationFactory} instance.
	 *
	 * @param factory
	 * 		The factory instance to be wrapped.
	 */
	@SuppressWarnings("deprecation")
	public FacesApplicationFactoryWrapper(ApplicationFactory factory)
	{
		this.factory = factory;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method returns a {@link FacesApplicationWrapper} instance.</p>
	 */
	@Override
	public Application getApplication()
	{
		Application application = factory.getApplication();
		NamedBindings.getConverters().forEach((key,value)->{
			application.addConverter(key,value.getCanonicalName());
		});
		NamedBindings.getValidators().forEach((key,value)->{
			application.addValidator(key,value.getCanonicalName());
		});
		return new FacesApplicationWrapper(application);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplication(Application application)
	{
		factory.setApplication(application);
	}


}
