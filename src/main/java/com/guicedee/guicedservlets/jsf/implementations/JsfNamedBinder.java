package com.guicedee.guicedservlets.jsf.implementations;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.guicedee.cdi.services.NamedBindings;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedinjection.interfaces.IGuiceModule;
import io.github.classgraph.ClassInfo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.bean.ManagedBean;

public class JsfNamedBinder
		extends AbstractModule
		implements IGuiceModule<JsfNamedBinder>
{
	@SuppressWarnings("deprecation")
	@Override
	protected void configure()
	{
		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation(ManagedBean.class.getCanonicalName()))
		{
			if (classInfo.isInterfaceOrAnnotation()
			    || classInfo.hasAnnotation("jakarta.enterprise.context.Dependent"))
			{
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			ManagedBean nn = clazz.getAnnotation(ManagedBean.class);
			String name = NamedBindings.cleanName(classInfo, nn.name());
			if (nn.eager())
			{
				NamedBindings.bindToEagerSingleton(binder(), clazz, name);
			}
			else
			{
				boolean sessionScoped = clazz.isAnnotationPresent(SessionScoped.class);
				boolean requestScoped = clazz.isAnnotationPresent(RequestScoped.class);
				boolean applicationScoped = clazz.isAnnotationPresent(ApplicationScoped.class);
				if (sessionScoped)
				{
					NamedBindings.bindToScope(binder(), clazz, name, com.google.inject.servlet.SessionScoped.class);
				}
				else if (requestScoped)
				{
					NamedBindings.bindToScope(binder(), clazz, name, com.google.inject.servlet.RequestScoped.class);
				}
				else if (applicationScoped)
				{
					NamedBindings.bindToScope(binder(), clazz, name, Singleton.class);
				}
				else
				{
					NamedBindings.bindToScope(binder(), clazz, name);
				}
			}
		}

		super.configure();
	}

	@Override
	public Integer sortOrder()
	{
		return 151;
	}

}
