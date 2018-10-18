# Guiced-Servlets-JSF

JSF Integration for Guiced Injection Framework. Allows for Faces to execute through the Guice Filter

To configure correctly, add the following to faces-config

```
<factory>
    <application-factory>com.jwebmp.guicedservlets.jsf.FacesApplicationFactoryWrapper</application-factory>
</factory>
```

Remember to remove your context path from web.xml or it will be skipped

REMOVE
```
 <servlet>
        <servlet-name>Faces Servlet</servlet-name>
        <servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>Faces Servlet</servlet-name>
        <url-pattern>*.jsf</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>Faces Servlet</servlet-name>
        <url-pattern>*.xhtml</url-pattern>
    </servlet-mapping>
```

This mapping is performed through the servlet binder

You can change/add URL mappings by configuring GuicedServletJSFBindings or by mapping your own

```
module.serve$("/faces/", "/faces/*", "*.jsf", "*.faces", "*.xhtml")
		      .with(FacesHttpServlet.class);
```

This implementation is a merge of http://javaevangelist.blogspot.com/2013/08/jsf-2x-tip-of-day-guice-elresolver.html and https://github.com/skuzzle/guice-jsf and uses the automated configurations of guiced-servlets and guiced-injection