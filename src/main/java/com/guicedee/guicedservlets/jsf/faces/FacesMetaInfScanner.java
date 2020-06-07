package com.guicedee.guicedservlets.jsf.faces;

import com.guicedee.guicedinjection.interfaces.IPathContentsScanner;

import java.util.HashSet;
import java.util.Set;

public class FacesMetaInfScanner
		implements IPathContentsScanner
{
	@Override
	public Set<String> searchFor()
	{
		Set<String> strings = new HashSet<>();
		strings.add("/");
		strings.add("META-INF");
		strings.add("WEB-INF");
		return strings;
	}
}
