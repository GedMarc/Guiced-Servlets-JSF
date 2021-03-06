package com.guicedee.guicedservlets.jsf.implementations;

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
		return deploymentInfo;
	}
}
