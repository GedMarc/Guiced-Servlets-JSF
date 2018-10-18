import com.jwebmp.guicedservlets.jsf.GuicedServletJSFBindings;

module com.jwebmp.guicedservlets.jsf {
	exports com.jwebmp.guicedservlets.jsf;

	requires com.jwebmp.guicedservlets;
	requires com.google.guice;
	requires javax.faces;
	requires javax.servlet.api;
	requires com.google.guice.extensions.servlet;
	requires com.jwebmp.logmaster;
	requires java.logging;
	requires javax.el;
	requires java.desktop;

	provides com.jwebmp.guicedservlets.services.IGuiceSiteBinder with GuicedServletJSFBindings;
	opens com.jwebmp.guicedservlets.jsf to com.google.guice;
}