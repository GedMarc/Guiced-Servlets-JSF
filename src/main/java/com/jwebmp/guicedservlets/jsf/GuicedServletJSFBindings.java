package com.jwebmp.guicedservlets.jsf;

import com.jwebmp.guicedservlets.services.GuiceSiteInjectorModule;
import com.jwebmp.guicedservlets.services.IGuiceSiteBinder;
import com.jwebmp.logger.LogFactory;

import javax.faces.webapp.FacesServlet;
import java.util.logging.Logger;

public class GuicedServletJSFBindings
		implements IGuiceSiteBinder<GuiceSiteInjectorModule>
{
	/**
	 * The logger
	 */
	private static final Logger log = LogFactory.getLog("GuicedServletJSFBindings");

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
		log.config("Added View Scope");
	}
}
