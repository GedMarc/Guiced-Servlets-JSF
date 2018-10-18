package com.jwebmp.guicedservlets.jsf;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.faces.webapp.FacesServlet;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

@Singleton
public class FacesHttpServlet
		extends HttpServlet
{

	private final Servlet wrapped;

	@Inject
	public FacesHttpServlet(FacesServlet facesServlet)
	{
		wrapped = facesServlet;
	}

	@Override
	public void destroy()
	{
		super.destroy();
		wrapped.destroy();
	}

	@Override
	public ServletConfig getServletConfig()
	{
		return wrapped.getServletConfig();
	}

	@Override
	public String getServletInfo()
	{
		return wrapped.getServletInfo();
	}

	@Override
	public void init(ServletConfig config) throws ServletException
	{
		wrapped.init(config);
	}

	@Override
	public void service(ServletRequest req, ServletResponse resp)
			throws ServletException, IOException
	{
		wrapped.service(req, resp);
	}
}