package com.guicedee.guicedservlets.jsf.implementations;

import com.guicedee.guicedinjection.interfaces.IPathContentsScanner;

import java.util.HashSet;
import java.util.Set;

public class GuiceInjectionWebInfScanner
		implements IPathContentsScanner
{
	@Override
	public Set<String> searchFor()
	{
		Set<String> strings = new HashSet<>();
		strings.add("WEB-INF");
		return strings;
	}
}
