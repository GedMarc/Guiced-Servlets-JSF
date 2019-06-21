package com.jwebmp.guicedservlets.jsf.implementations;

import com.jwebmp.undertow.services.UndertowDeploymentConfigurator;
import com.sun.faces.RIConstants;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.ListenerInfo;

public class GuicedJSFDeploymentInfoConfiguration implements UndertowDeploymentConfigurator
{

	@Override
	public DeploymentInfo configure(DeploymentInfo deploymentInfo)
	{
		deploymentInfo.addServletContextAttribute(RIConstants.FACES_INITIALIZER_MAPPINGS_ADDED, Boolean.TRUE)
				 .addListener(new ListenerInfo(com.sun.faces.config.ConfigureListener.class));
		return deploymentInfo;
	}
}
