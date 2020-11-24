package com.guicedee.guicedservlets.jsf;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.guicedee.cdi.services.NamedBindings;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedinjection.pairing.Pair;
import com.guicedee.logger.LogFactory;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.MethodInfo;
import org.apache.commons.beanutils.PropertyUtilsBean;

import jakarta.el.ELContext;
import jakarta.el.ELResolver;
import jakarta.faces.FacesWrapper;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import java.beans.FeatureDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A {@link FacesWrapper} implementation that wraps an {@link ELResolver} and
 * provides a mechanism to inject Guice {@link Injector} implementations.
 *
 * @author John Yeary
 * @version 1.0
 */
public class GuiceELResolverWrapper
		extends ELResolver {
	@Override
	public Object getValue(ELContext elContext, Object o, Object o1) {
		if (o != null) {
			return null;
		} else {
			if (!NamedBindings.isBound(o1.toString()))
				return null;
			try {
				Object o2 = GuiceContext.get(Key.get(Object.class, Names.named(o1.toString())));
				elContext.setPropertyResolved(o, o1);
				return o2;
			} catch (Throwable T) {
			}
		}
		return null;
	}

	@Override
	public Class<?> getType(ELContext elContext, Object o, Object o1) {
		return null;
	}

	@Override
	public void setValue(ELContext elContext, Object o, Object o1, Object o2) {

	}

	@Override
	public boolean isReadOnly(ELContext elContext, Object o, Object o1) {
		return false;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext elContext, Object o) {
		return null;
	}

	@Override
	public Class<?> getCommonPropertyType(ELContext elContext, Object o) {
		return null;
	}
}