package com.jwebmp.guicedservlets.jsf;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.Scopes;
import com.google.inject.servlet.SessionScoped;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * This is a copy of guice's implementation of {@link SessionScoped} with
 * adjustments to place objects into the current view scope.
 *
 * @author Simon Taddiken
 */
final class ViewScopeImpl
		implements Scope
{

	@Override
	public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped)
	{
		String name = key.toString();
		return new Provider<T>()
		{

			@Override
			public T get()
			{
				FacesContext facesContext = FacesContext.getCurrentInstance();
				UIViewRoot viewRoot = facesContext.getViewRoot();

				// fallback if no view is active. Consider to throw an exception
				// (you might also want to check whether there actually is a FacesContext
				// available to prevent injecting your beans outside a valid view scope)
				if (viewRoot == null)
				{
					return unscoped.get();
				}

				Map<String, Object> viewMap = viewRoot.getViewMap(true);
				synchronized (viewMap)
				{
					Object obj = viewMap.get(name);
					if (obj == NullObject.INSTANCE)
					{
						return null;
					}

					@SuppressWarnings("unchecked")
					T t = (T) obj;
					if (t == null)
					{
						t = unscoped.get();
						if (!Scopes.isCircularProxy(t))
						{
							viewRoot.getViewMap()
							        .put(name, t == null
							                   ? NullObject.INSTANCE
							                   : t);
						}
					}
					return t;
				}
			}

			@Override
			public String toString()
			{
				return String.format("%s[%s]", unscoped, ViewScopeImpl.this);
			}

		};
	}

	private enum NullObject
	{
		INSTANCE
	}

	@Override
	public final String toString()
	{
		return "Custom.ViewScope";
	}

}