package com.guicedee.guicedservlets.jsf;

import com.guicedee.guicedservlets.services.GuiceSiteInjectorModule;
import com.guicedee.guicedservlets.services.IGuiceSiteBinder;
import com.guicedee.logger.LogFactory;

import javax.faces.webapp.FacesServlet;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class GuicedServletJSFModule
		implements IGuiceSiteBinder<GuiceSiteInjectorModule>
{
	/**
	 * The logger
	 */
	private static final Logger log = LogFactory.getLog("GuicedServletJSFModule");

	private static Set<String> facesServPattern = new HashSet<>();

	@Override
	public void onBind(GuiceSiteInjectorModule module)
	{
		module.bind(FacesServlet.class)
		      .asEagerSingleton();
		log.config("Configured Faces Servlet");
		module.serve$("/faces/", "/faces/*", "*.jsf", "*.faces", "*.xhtml")
		      .with(FacesHttpServlet.class);
		log.config("Serving /faces with Faces Servlet");
		module.bindScope(ViewScoped.class, new ViewScopeImpl());
		log.config("Added View Scope??");
	}
}
