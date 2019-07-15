package com.jwebmp.guicedservlets.jsf.implementations;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.jwebmp.guicedinjection.GuiceContext;
import com.jwebmp.guicedinjection.interfaces.IGuiceModule;
import io.github.classgraph.ClassInfo;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

public class JsfNamedBinder
		extends AbstractModule
		implements IGuiceModule<JsfNamedBinder>
{

	@Override
	protected void configure()
	{
		List<String> completed = new ArrayList<>();
		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation(Named.class.getCanonicalName()))
		{
			if (classInfo.isInterfaceOrAnnotation() || classInfo.hasAnnotation("javax.enterprise.context.Dependent"))
			{
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			completed.add(clazz.getCanonicalName());
			Named nn = clazz.getAnnotation(Named.class);
			String name = nn.value()
			                .equals("") ? classInfo.getName() : nn.value();
			bind(Object.class).annotatedWith(Names.named(name))
			                  .to(clazz);
		}

		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation(ManagedBean.class.getCanonicalName()))
		{
			if (classInfo.isInterfaceOrAnnotation()
			    || classInfo.hasAnnotation("javax.enterprise.context.Dependent"))
			{
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			if (completed.contains(clazz.getCanonicalName()))
			{
				continue;
			}
			completed.add(clazz.getCanonicalName());
			ManagedBean nn = clazz.getAnnotation(ManagedBean.class);
			if (nn.eager())
			{
				bind(Object.class).annotatedWith(Names.named(nn.name()))
				                  .to(clazz)
				                  .asEagerSingleton();
			}
			else
			{
				bind(Object.class).annotatedWith(Names.named(nn.name()))
				                  .to(clazz);
			}
		}

		super.configure();
	}

}
