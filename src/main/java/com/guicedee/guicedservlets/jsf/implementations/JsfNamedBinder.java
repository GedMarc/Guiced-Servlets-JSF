package com.guicedee.guicedservlets.jsf.implementations;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedinjection.interfaces.IGuiceModule;
import io.github.classgraph.ClassInfo;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.guicedee.guicedinjection.json.StaticStrings.*;

public class JsfNamedBinder
		extends AbstractModule
		implements IGuiceModule<JsfNamedBinder>
{
	public static final Map<String, Class<?>> facesConvertors = new HashMap<>();

	@SuppressWarnings("deprecation")
	@Override
	protected void configure()
	{
		List<String> completed = new ArrayList<>();

		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation("javax.faces.convert.FacesConverter"))
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
			javax.faces.convert.FacesConverter nn = clazz.getAnnotation(javax.faces.convert.FacesConverter.class);
			String name = nn.value();
			if (name.equals(STRING_EMPTY))
			{
				name = classInfo.getSimpleName();
				StringBuilder sb = new StringBuilder(name);
				sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
				name = sb.toString();
			}
			bindToScope(clazz, name);

			facesConvertors.put(name, clazz);
		}

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
			String name = nn.value();
			if (name.equals(STRING_EMPTY))
			{
				name = classInfo.getSimpleName();
				StringBuilder sb = new StringBuilder(name);
				sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
				name = sb.toString();
			}
			bindToScope(clazz, name);
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
			String name = nn.name();
			if (name.equals(STRING_EMPTY))
			{
				name = classInfo.getSimpleName();
				StringBuilder sb = new StringBuilder(name);
				sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
				name = sb.toString();
			}

			if (nn.eager())
			{
				bind(clazz).asEagerSingleton();
				bind(Object.class).annotatedWith(Names.named(name))
				                  .to(clazz);
			}
			else
			{
				bindToScope(clazz, name);
			}
		}

		super.configure();
	}

	private void bindToScope(Class<?> clazz, String name)
	{
		bind(clazz);
		bind(Object.class).annotatedWith(Names.named(name))
		                  .to(clazz);
		System.out.println("Bound : " + name + " to " + clazz.getCanonicalName());
	}
}
