open module com.guicedee.guicedservlets.jsf {
	exports com.guicedee.guicedservlets.jsf;

	requires transitive jakarta.faces;

	requires jakarta.el;
	requires jakarta.enterprise.cdi;
	requires java.desktop;

	requires transitive com.guicedee.guicedservlets.undertow;

	requires java.xml.bind;

	provides com.guicedee.guicedservlets.services.IGuiceSiteBinder with com.guicedee.guicedservlets.jsf.GuicedServletJSFModule;
	provides com.guicedee.guicedservlets.undertow.services.UndertowDeploymentConfigurator with com.guicedee.guicedservlets.jsf.implementations.GuicedJSFDeploymentInfoConfiguration;
	provides com.guicedee.guicedinjection.interfaces.IGuiceModule with com.guicedee.guicedservlets.jsf.implementations.JsfNamedBinder;
	requires org.apache.commons.beanutils;
	requires org.apache.commons.collections4;
}
