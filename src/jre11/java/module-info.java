import com.jwebmp.guicedservlets.jsf.GuicedServletJSFBindings;

module com.jwebmp.guicedservlets.jsf {
	exports com.jwebmp.guicedservlets.jsf;

	requires transitive com.jwebmp.guicedservlets;

	requires transitive javax.faces;
	requires transitive javax.el;
	requires transitive java.desktop;

	provides com.jwebmp.guicedservlets.services.IGuiceSiteBinder with GuicedServletJSFBindings;

	opens com.jwebmp.guicedservlets.jsf to com.google.guice;
}