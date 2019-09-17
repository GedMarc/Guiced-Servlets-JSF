package com.jwebmp.guicedservlets.jsf.implementations;


import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import com.jwebmp.guicedinjection.GuiceContext;
import com.jwebmp.guicedinjection.interfaces.IGuiceModule;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.MethodInfo;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsfNamedBinder
		extends AbstractModule
		implements IGuiceModule<JsfNamedBinder> {

	public static final Map<String, Class<?>> facesConvertors = new HashMap<>();

	@Override
	protected void configure() {
		List<String> completed = new ArrayList<>();
		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation(Named.class.getCanonicalName())) {
			if (classInfo.isInterfaceOrAnnotation() || classInfo.hasAnnotation("javax.enterprise.context.Dependent")) {
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			completed.add(clazz.getCanonicalName());
			Named nn = clazz.getAnnotation(Named.class);
			String name = nn.value()
			                .equals("") ? classInfo.getName() : nn.value();
			bind(clazz);
			bind(Object.class).annotatedWith(Names.named(name))
			                  .to(clazz);
		}

		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation(ManagedBean.class.getCanonicalName())) {
			if (classInfo.isInterfaceOrAnnotation()
			    || classInfo.hasAnnotation("javax.enterprise.context.Dependent")) {
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			if (completed.contains(clazz.getCanonicalName())) {
				continue;
			}
			completed.add(clazz.getCanonicalName());
			ManagedBean nn = clazz.getAnnotation(ManagedBean.class);
			String name = nn.name()
			                .equals("") ? classInfo.getName() : nn.name();
			StringBuilder sb = new StringBuilder(name);
			sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
			name = sb.toString();

			if (nn.eager()) {
				bind(clazz).asEagerSingleton();
				bind(Object.class).annotatedWith(Names.named(name))
				                  .to(clazz);
			}
			else {
				bind(Object.class).annotatedWith(Names.named(name))
				                  .to(clazz);
				bind(clazz);
			}
		}

		for (ClassInfo classInfo : GuiceContext.instance()
		                                       .getScanResult()
		                                       .getClassesWithAnnotation("javax.faces.convert.FacesConverter")) {
			if (classInfo.isInterfaceOrAnnotation()
			    || classInfo.hasAnnotation("javax.enterprise.context.Dependent")) {
				continue;
			}
			Class<?> clazz = classInfo.loadClass();
			if (completed.contains(clazz.getCanonicalName())) {
				continue;
			}
			completed.add(clazz.getCanonicalName());
			javax.faces.convert.FacesConverter nn = clazz.getAnnotation(javax.faces.convert.FacesConverter.class);
			String name = nn.value()
			                .equals("") ? classInfo.getName() : nn.value();
			StringBuilder sb = new StringBuilder(name);
			sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
			name = sb.toString();

			bind(clazz);
			bind(Object.class).annotatedWith(Names.named(name))
			                  .to(clazz);
			facesConvertors.put(name, clazz);
		}

		bindListener(Matchers.any(), new  TypeListener() {
			@Override
			public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
				Class<?> clazz = typeLiteral.getRawType();
				try {
					ClassInfo info = GuiceContext.instance()
					                             .getScanResult()
					                             .getClassInfo(clazz.getCanonicalName());
					if (info != null && info.hasMethodAnnotation("javax.annotation.PostConstruct")) {
						for (MethodInfo methodInfo : GuiceContext.instance()
						                                         .getScanResult()
						                                         .getClassInfo(clazz.getCanonicalName())
						                                         .getMethodInfo()) {
							if (methodInfo.hasAnnotation("javax.annotation.PostConstruct")) {
								typeEncounter.register(new InjectionListener<I>() {
									@Override
									public void afterInjection(I i) {
										try {
											methodInfo.loadClassAndGetMethod()
											          .invoke(i);
										} catch (IllegalAccessException | InvocationTargetException e) {
											e.printStackTrace();
										}
									}
								});
							}
						}
					}
				}catch(NullPointerException npe)
				{
					npe.printStackTrace();
				}
			}
		});

		super.configure();
	}
}
