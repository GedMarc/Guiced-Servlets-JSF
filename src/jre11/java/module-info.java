import com.jwebmp.guicedinjection.interfaces.IGuiceScanJarExclusions;
import com.jwebmp.guicedinjection.interfaces.IGuiceScanModuleExclusions;
import com.jwebmp.guicedservlets.jsf.GuicedServletJSFBindings;
import com.jwebmp.guicedservlets.jsf.implementations.GuicedServletsJSFModuleExclusions;

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
	requires com.jwebmp.guicedinjection;
	requires java.validation;

	provides com.jwebmp.guicedservlets.services.IGuiceSiteBinder with GuicedServletJSFBindings;
	provides IGuiceScanJarExclusions with GuicedServletsJSFModuleExclusions;
	provides IGuiceScanModuleExclusions with GuicedServletsJSFModuleExclusions;

	opens com.jwebmp.guicedservlets.jsf to com.google.guice;
	opens com.jwebmp.guicedservlets.jsf.implementations to com.google.guice;
}