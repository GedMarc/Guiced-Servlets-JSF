import com.guicedee.guicedservlets.jsf.implementations.GuicedJSFModuleInclusions;

open module com.guicedee.guicedservlets.jsf {
	exports com.guicedee.guicedservlets.jsf;

	requires transitive jakarta.faces;

	requires com.guicedee.guicedinjection;

	requires jakarta.el;
	requires jakarta.enterprise.cdi;
	requires java.desktop;

	requires transitive com.guicedee.guicedservlets.undertow;

	requires jakarta.xml.bind;

	provides com.guicedee.guicedservlets.services.IGuiceSiteBinder with com.guicedee.guicedservlets.jsf.GuicedServletJSFModule;
	provides com.guicedee.guicedservlets.undertow.services.UndertowDeploymentConfigurator with com.guicedee.guicedservlets.jsf.implementations.GuicedJSFDeploymentInfoConfiguration;
	provides com.guicedee.guicedinjection.interfaces.IGuiceModule with com.guicedee.guicedservlets.jsf.implementations.JsfNamedBinder;
	provides com.guicedee.guicedinjection.interfaces.IGuiceScanModuleInclusions with GuicedJSFModuleInclusions;

	requires org.apache.commons.beanutils;
	requires org.apache.commons.collections4;
}
