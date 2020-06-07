package com.guicedee.guicedservlets.jsf;

import java.util.LinkedHashSet;
import java.util.Set;

public class FacesConfiguration
{
	private static final Set<String> tagLibraries = new LinkedHashSet<>();
	private static final Set<String> facesConfig = new LinkedHashSet<>();

	public static Set<String> getTagLibraries()
	{
		return tagLibraries;
	}

	public static Set<String> getFacesConfig()
	{
		return facesConfig;
	}
}
