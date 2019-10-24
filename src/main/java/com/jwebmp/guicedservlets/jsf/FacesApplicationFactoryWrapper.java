package com.guicedee.guicedservlets.jsf;

import com.guicedee.guicedservlets.jsf.implementations.JsfNamedBinder;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;

import java.util.Map;

/**
 * An implementation of {@link ApplicationFactory}.
 *
 * @author John Yeary
 * @version 1.0
 */
public class FacesApplicationFactoryWrapper
		extends ApplicationFactory {

	private ApplicationFactory factory;

	/**
	 * Constructor that wraps an {@link ApplicationFactory} instance.
	 *
	 * @param factory The factory instance to be wrapped.
	 */
	@SuppressWarnings("deprecation")
	public FacesApplicationFactoryWrapper(ApplicationFactory factory) {
		this.factory = factory;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method returns a {@link FacesApplicationWrapper} instance.</p>
	 */
	@Override
	public Application getApplication() {
		Application application = factory.getApplication();


		for (Map.Entry<String, Class<?>> entry : JsfNamedBinder.facesConvertors.entrySet()) {
			String key = entry.getKey();
			Class<?> value = entry.getValue();
			application.addConverter(key,value.getCanonicalName());
		}
		return new FacesApplicationWrapper(application);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplication(Application application) {
		factory.setApplication(application);
	}


}
