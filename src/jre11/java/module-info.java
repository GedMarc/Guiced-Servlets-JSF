module com.jwebmp.guicedservlets.jsf {
	exports com.jwebmp.guicedservlets.jsf;

	requires transitive com.jwebmp.guicedservlets;
	requires com.google.guice;

	requires transitive jakarta.faces;

	requires transitive javax.servlet.api;
	requires com.google.guice.extensions.servlet;
	requires com.jwebmp.logmaster;
	requires java.logging;
	requires javax.el;
	requires java.desktop;
	requires com.jwebmp.guicedinjection;
	requires java.validation;
	requires com.jwebmp.undertow;
	requires undertow.servlet;
	requires io.github.classgraph;
	requires javax.inject;

	provides com.jwebmp.guicedservlets.services.IGuiceSiteBinder with com.jwebmp.guicedservlets.jsf.GuicedServletJSFBindings;
	provides com.jwebmp.guicedinjection.interfaces.IGuiceScanJarExclusions with com.jwebmp.guicedservlets.jsf.implementations.GuicedServletsJSFModuleExclusions;
	provides com.jwebmp.guicedinjection.interfaces.IGuiceScanModuleExclusions with com.jwebmp.guicedservlets.jsf.implementations.GuicedServletsJSFModuleExclusions;
	provides com.jwebmp.undertow.services.UndertowDeploymentConfigurator with com.jwebmp.guicedservlets.jsf.implementations.GuicedJSFDeploymentInfoConfiguration;
	provides com.jwebmp.guicedinjection.interfaces.IGuiceModule with com.jwebmp.guicedservlets.jsf.implementations.JsfNamedBinder;

	opens com.jwebmp.guicedservlets.jsf to com.google.guice;
	opens com.jwebmp.guicedservlets.jsf.implementations to com.google.guice;
}
