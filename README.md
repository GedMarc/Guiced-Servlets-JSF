# Guiced-Servlets-JSF

JSF Integration for Guiced Injection Framework. Allows for Faces to execute through the Guice Filter

To configure correctly, add the following to faces-config

```
<factory>
    <application-factory>com.jwebmp.guicedservlets.jsf.FacesApplicationFactoryWrapper</application-factory>
</factory>
```
