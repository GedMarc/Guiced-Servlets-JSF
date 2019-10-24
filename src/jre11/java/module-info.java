import com.guicedee.guicedinjection.interfaces.*;
import com.guicedee.guicedservlets.jsf.GuicedServletJSFModule;
import com.guicedee.guicedservlets.jsf.implementations.*;
import com.guicedee.guicedservlets.services.IGuiceSiteBinder;
import com.guicedee.guicedservlets.undertow.services.UndertowDeploymentConfigurator;

module com.guicedee.guicedservlets.jsf {
	exports com.guicedee.guicedservlets.jsf;
	exports com.guicedee.schemas.facesconfig;

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

	provides IGuiceSiteBinder with GuicedServletJSFModule;
	provides IGuiceScanJarExclusions with GuicedServletsJSFModuleExclusions;
	provides IGuiceScanModuleExclusions with GuicedServletsJSFModuleExclusions;
	provides UndertowDeploymentConfigurator with GuicedJSFDeploymentInfoConfiguration;
	provides IGuiceModule with JsfNamedBinder;


	provides IFileContentsScanner with FacesConfigFileHandler;
	provides IPathContentsScanner with GuiceInjectionWebInfScanner;
	provides IGuicePostStartup with FacesConfigMergerPostStartup;

	opens com.guicedee.guicedservlets.jsf to com.google.guice;
	opens com.guicedee.schemas.facesconfig to java.xml.bind;
	opens com.guicedee.guicedservlets.jsf.implementations to com.google.guice;
}
