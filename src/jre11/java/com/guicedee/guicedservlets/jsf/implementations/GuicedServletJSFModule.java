package com.guicedee.guicedservlets.jsf;

import com.guicedee.guicedservlets.services.GuiceSiteInjectorModule;
import com.guicedee.guicedservlets.services.IGuiceSiteBinder;
import com.guicedee.logger.LogFactory;

import jakarta.faces.webapp.FacesServlet;
import java.util.Set;
import java.util.logging.Logger;

public class GuicedServletJSFModule
		implements IGuiceSiteBinder<GuiceSiteInjectorModule>
{
	private static final Logger log = LogFactory.getLog("GuicedServletJSFModule");
	public static Set<String> JsfListenURLs = Set.of("/faces/", "/faces/*", "*.jsf", "*.faces", "*.xhtml");

	@Override
	public void onBind(GuiceSiteInjectorModule module)
	{
		module.bind(FacesServlet.class)
		      .asEagerSingleton();
		module.serve$(JsfListenURLs)
		      .with(FacesHttpServlet.class);
		log.config("Serving " + JsfListenURLs.toString() + " with Faces Servlet");
	}
}
