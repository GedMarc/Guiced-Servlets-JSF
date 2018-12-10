package com.jwebmp.guicedservlets.jsf.implementations;

import com.jwebmp.guicedinjection.interfaces.IGuiceScanJarExclusions;
import com.jwebmp.guicedinjection.interfaces.IGuiceScanModuleExclusions;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class GuicedServletsJSFModuleExclusions
		implements IGuiceScanModuleExclusions<GuicedServletsJSFModuleExclusions>,
				           IGuiceScanJarExclusions<GuicedServletsJSFModuleExclusions>
{
	@Override
	public @NotNull Set<String> excludeJars()
	{
		Set<String> strings = new HashSet<>();
		strings.add("guiced-servlets-jsf-*");
		strings.add("javax.faces-*");
		strings.add("javax.el-*");
		return strings;
	}

	@Override
	public @NotNull Set<String> excludeModules()
	{
		Set<String> strings = new HashSet<>();
		strings.add("com.jwebmp.guicedservlets.jsf");


		strings.add("com.jwebmp.guicedservlets");
		strings.add("com.google.guice");
		strings.add("javax.faces");
		strings.add("javax.servlet.api");
		strings.add("com.google.guice.extensions.servlet");
		strings.add("com.jwebmp.logmaster");
		strings.add("java.logging");
		strings.add("javax.el");
		strings.add("java.desktop");
		strings.add("com.jwebmp.guicedinjection");
		strings.add("java.validation");

		return strings;
	}
}