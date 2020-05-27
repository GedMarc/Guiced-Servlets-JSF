module com.guicedee.guicedservlets.jsf {
	exports com.guicedee.guicedservlets.jsf;
	//	exports com.guicedee.schemas.facesconfig;
	requires javax.faces;
	requires javax.el;
	requires static java.desktop;
	requires com.guicedee.guicedinjection;
	requires com.guicedee.guicedservlets.undertow;

	requires java.xml.bind;

	provides com.guicedee.guicedservlets.services.IGuiceSiteBinder with com.guicedee.guicedservlets.jsf.GuicedServletJSFModule;
	provides com.guicedee.guicedservlets.undertow.services.UndertowDeploymentConfigurator with com.guicedee.guicedservlets.jsf.implementations.GuicedJSFDeploymentInfoConfiguration;
	provides com.guicedee.guicedinjection.interfaces.IGuiceModule with com.guicedee.guicedservlets.jsf.implementations.JsfNamedBinder;

	opens com.guicedee.guicedservlets.jsf to com.google.guice;
	//opens com.guicedee.schemas.facesconfig to java.xml.bind;
	opens com.guicedee.guicedservlets.jsf.implementations to com.google.guice;
}
