package com.guicedee.guicedservlets.jsf.implementations;

import com.guicedee.guicedservlets.jsf.FacesConfiguration;
import com.guicedee.guicedservlets.jsf.faces.FacesConfigByteArrayConsumer;
import com.guicedee.guicedservlets.undertow.services.UndertowDeploymentConfigurator;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.ListenerInfo;

import static com.guicedee.guicedinjection.json.StaticStrings.*;

public class GuicedJSFDeploymentInfoConfiguration
		implements UndertowDeploymentConfigurator
{

	@Override
	public DeploymentInfo configure(DeploymentInfo deploymentInfo)
	{
		deploymentInfo.addServletContextAttribute("com.sun.faces.facesInitializerMappingsAdded", Boolean.TRUE)
		              .addListener(new ListenerInfo(com.sun.faces.config.ConfigureListener.class));

		StringBuilder facesConfigLocations = new StringBuilder();
		for (String facesConfig : FacesConfiguration.getFacesConfig())
		{
			facesConfigLocations.append(facesConfig)
			                    .append(STRING_COMMNA);
		}
		if (facesConfigLocations.length() > 0)
		{
			facesConfigLocations.deleteCharAt(facesConfigLocations.length() - 1);
		}
		deploymentInfo.addServletContextAttribute("javax.faces.application.CONFIG_FILES", facesConfigLocations.toString());

		StringBuilder facesTagLibsLocations = new StringBuilder();
		for (String facesConfig : FacesConfiguration.getTagLibraries())
		{
			facesTagLibsLocations.append(facesConfig)
			                     .append(STRING_COMMNA);
		}
		if (facesTagLibsLocations.length() > 0)
		{
			facesTagLibsLocations.deleteCharAt(facesTagLibsLocations.length() - 1);
		}
		deploymentInfo.addServletContextAttribute("javax.faces.FACELETS_LIBRARIES", facesTagLibsLocations.toString());

		return deploymentInfo;
	}
}
