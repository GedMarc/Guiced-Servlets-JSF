module com.guicedee.guicedservlets.jsf {
	exports com.guicedee.guicedservlets.jsf;
//	exports com.guicedee.schemas.facesconfig;

	requires com.guicedee.guicedservlets;
	requires com.google.guice;
	requires javax.faces;
	requires javax.servlet.api;
	requires com.google.guice.extensions.servlet;
	requires com.guicedee.logmaster;
	requires java.logging;
	requires javax.el;
	requires java.desktop;
	requires com.guicedee.guicedinjection;
	requires java.validation;
	requires com.guicedee.guicedservlets.undertow;
	requires undertow.servlet;
	requires io.github.classgraph;
	requires javax.inject;
	requires com.fasterxml.jackson.databind;

	requires java.xml.bind;

	provides com.guicedee.guicedservlets.services.IGuiceSiteBinder with com.guicedee.guicedservlets.jsf.GuicedServletJSFModule;
	provides com.guicedee.guicedservlets.undertow.services.UndertowDeploymentConfigurator with com.guicedee.guicedservlets.jsf.implementations.GuicedJSFDeploymentInfoConfiguration;
	provides com.guicedee.guicedinjection.interfaces.IGuiceModule with com.guicedee.guicedservlets.jsf.implementations.JsfNamedBinder;

	opens com.guicedee.guicedservlets.jsf to com.google.guice;
	//opens com.guicedee.schemas.facesconfig to java.xml.bind;
	opens com.guicedee.guicedservlets.jsf.implementations to com.google.guice;
}
