import com.guicedee.guicedinjection.interfaces.IFileContentsScanner;
import com.guicedee.guicedservlets.jsf.faces.FacesConfigFileSearcher;

module com.guicedee.guicedservlets.jsf {
	exports com.guicedee.guicedservlets.jsf;

	requires transitive javax.faces;
	requires transitive javax.el;
	requires java.desktop;

	requires transitive com.guicedee.guicedservlets.undertow;

	requires java.xml.bind;

	provides com.guicedee.guicedservlets.services.IGuiceSiteBinder with com.guicedee.guicedservlets.jsf.GuicedServletJSFModule;
	provides com.guicedee.guicedservlets.undertow.services.UndertowDeploymentConfigurator with com.guicedee.guicedservlets.jsf.implementations.GuicedJSFDeploymentInfoConfiguration;
	provides com.guicedee.guicedinjection.interfaces.IGuiceModule with com.guicedee.guicedservlets.jsf.implementations.JsfNamedBinder;
	provides com.guicedee.guicedinjection.interfaces.IPathContentsScanner with com.guicedee.guicedservlets.jsf.faces.FacesMetaInfScanner;
	provides IFileContentsScanner with FacesConfigFileSearcher;

	opens com.guicedee.guicedservlets.jsf to com.google.guice;
	opens com.guicedee.guicedservlets.jsf.implementations to com.google.guice;
}
