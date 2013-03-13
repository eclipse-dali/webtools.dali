/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource;

import java.util.Comparator;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.ResourceLocator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

class ResourceLocatorConfig
	implements Comparable<ResourceLocatorConfig>
{
	private final InternalResourceLocatorManager manager;
	private final String id;
	private final String className;
	private final Priority priority;
	private /* final */ String pluginID;
	private /* final */ Expression enablementExpression;

	// lazily initialized
	private ResourceLocator resourceLocator;


	ResourceLocatorConfig(InternalResourceLocatorManager manager, String id, String className, Priority priority) {
		super();
		this.manager = manager;
		this.id = id;
		this.className = className;
		this.priority = priority;
	}

	InternalResourceLocatorManager getManager() {
		return this.manager;
	}

	String getID() {
		return this.id;
	}

	String getClassName() {
		return this.className;
	}

	Priority getPriority() {
		return this.priority;
	}

	String getPluginID() {
		return this.pluginID;
	}

	void setPluginID(String pluginID) {
		this.pluginID = pluginID;
	}

	Expression getEnablementExpression() {
		return this.enablementExpression;
	}

	void setEnablementExpression(Expression enablementExpression) {
		this.enablementExpression = enablementExpression;
	}

	boolean isEnabled(IProject project) {
		return (this.enablementExpression == null) || this.isEnabled_(project);
	}

	/**
	 * Pre-condition: enablement expression is not <code>null</code>.
	 */
	boolean isEnabled_(IProject project) {
		EvaluationContext evalContext = new EvaluationContext(null, project);
		evalContext.setAllowPluginActivation(true);
		evalContext.addVariable(PROJECT_ENABLEMENT_EXPRESSION_VARIABLE, project);

		try {
			// EvaluationResult.NOT_LOADED will return false
			return this.enablementExpression.evaluate(evalContext) == EvaluationResult.TRUE;
		} catch (CoreException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}
	private static final String PROJECT_ENABLEMENT_EXPRESSION_VARIABLE = "project";  //$NON-NLS-1$

	synchronized ResourceLocator getResourceLocator() {
		if (this.resourceLocator == null) {
			this.resourceLocator = this.buildResourceLocator();
		}
		return this.resourceLocator;
	}

	private ResourceLocator buildResourceLocator() {
		return PlatformTools.instantiate(this.pluginID, this.manager.getExtensionPointName(), this.className, ResourceLocator.class);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.className);
	}

	public int compareTo(ResourceLocatorConfig other) {
		return Priority.DefaultComparator.instance().compare(this.priority, other.priority);
	}


	// ********** resource locator priority **********

	static enum Priority {
		HIGHEST(0, "highest"), //$NON-NLS-1$
		HIGHER(1, "higher"), //$NON-NLS-1$
		HIGH(2, "high"), //$NON-NLS-1$
		NORMAL(3, "normal"), //$NON-NLS-1$
		LOW(4, "low"), //$NON-NLS-1$
		LOWER(5, "lower"), //$NON-NLS-1$
		LOWEST(6, "lowest"); //$NON-NLS-1$

		/**
		 * Return the priority for the specified literal.
		 * Return {@link #NORMAL} if the specified literal is <code>null</code>.
		 * Return <code>null</code> if the specified literal is invalid.
		 */
		static Priority get(String literal) {
			if (literal == null) {
				return NORMAL;
			}
			for (Priority priority : values()) {
				if (literal.equals(priority.literal)) {
					return priority;
				}
			}
			return null;
		}


		int value;
		private String literal;

		private Priority(int value, String literal) {
			this.value = value;
			this.literal = literal;
		}

		@Override
		public String toString() {
			return this.literal;
		}


		// ********** comparator **********

		static class DefaultComparator
			implements Comparator<Priority>
		{
			private static final Comparator<Priority> INSTANCE = new DefaultComparator();
			static Comparator<Priority> instance() {
				return INSTANCE;
			}
			private DefaultComparator() {
				super();
			}
			public int compare(Priority priority1, Priority priority2) {
				int value1 = priority1.value;
				int value2 = priority2.value;
				return (value1 < value2) ? -1 : ((value1 == value2) ? 0 : 1);
			}
			@Override
			public String toString() {
				return ObjectTools.singletonToString(this);
			}
		}
	}


	// ********** enabled filter **********

	static class EnabledFilter
		extends PredicateAdapter<ResourceLocatorConfig>
	{
		private final IProject project;
		EnabledFilter(IProject project) {
			super();
			this.project = project;
		}
		@Override
		public boolean evaluate(ResourceLocatorConfig config) {
			return config.isEnabled(this.project);
		}
	}


	// ********** resource locator transformer **********

	static final Transformer<ResourceLocatorConfig, ResourceLocator> RESOURCE_LOCATOR_TRANSFORMER = new ResourceLocatorTransformer();
	static class ResourceLocatorTransformer
		extends TransformerAdapter<ResourceLocatorConfig, ResourceLocator>
	{
		@Override
		public ResourceLocator transform(ResourceLocatorConfig config) {
			return config.getResourceLocator();
		}
	}
}
