/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource;

import static org.eclipse.jst.common.project.facet.core.internal.FacetedProjectFrameworkJavaPlugin.log;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.common.core.internal.utility.XPointTools;
import org.eclipse.jpt.common.core.resource.ResourceLocator;
import org.eclipse.jpt.common.utility.internal.StringTools;

public class ResourceLocatorConfig 
		implements Comparable<ResourceLocatorConfig> {
	
	public static final String PROJECT_ENABLEMENT_VARIABLE = "project";  //$NON-NLS-1$
	
	private String id;
	private String pluginId;
	private String className;
	private Priority priority;
	private Expression enablementCondition;
	
	
	ResourceLocatorConfig() {
		super();
	}
	
	
	public String getId() {
		return this.id;
	}
	
	void setId(String id) {
		this.id = id;
	}
	
	public String getPluginId() {
		return this.pluginId;
	}
	
	void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	void setClassName(String className) {
		this.className = className;
	}
	
	public Priority getPriority() {
		return this.priority;
	}
	
	void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	public Expression getEnablementCondition() {
		return this.enablementCondition;
	}
	
	void setEnablementCondition(Expression enablementCondition) {
		this.enablementCondition = enablementCondition;
	}
	
	public ResourceLocator getResourceLocator() {
		return XPointTools.instantiate(
				this.pluginId, ResourceLocatorManager.QUALIFIED_EXTENSION_POINT_ID, 
				this.className, ResourceLocator.class);
	}
	
	public boolean isEnabledFor(IProject project) {
		EvaluationContext evalContext = new EvaluationContext(null, project);
		evalContext.setAllowPluginActivation(true);
		evalContext.addVariable(PROJECT_ENABLEMENT_VARIABLE, project);
		
		if (this.enablementCondition != null) {
			try {
				EvaluationResult evalResult = this.enablementCondition.evaluate(evalContext);
				
				if (evalResult == EvaluationResult.FALSE) {
					return false;
				}
			}
			catch (CoreException e) {
				log(e);
			}
		}
		
		return true;
	}
	
	public int compareTo(ResourceLocatorConfig other) {
		return Priority.compare(this.priority, other.priority);
	}
	
	
	public static enum Priority {
		
		/* The lowest priority for a resource locator */
		LOWEST(6, "lowest"), //$NON-NLS-1$
		
		/* The second lowest priority for a resource locator */
		LOWER(5, "lower"), //$NON-NLS-1$
		
		/* The third lowest priority for a resource locator */
		LOW(4, "low"), //$NON-NLS-1$
		
		/* The default priority for a resource locator */
		NORMAL(3, "normal"), //$NON-NLS-1$
		
		/* The third highest priority for a resource locator */
		HIGH(2, "high"), //$NON-NLS-1$
		
		/* The second highest priority for a resource locator */
		HIGHER(1, "higher"), //$NON-NLS-1$
		
		/* The highest priority for a resource locator */
		HIGHEST(0, "highest"); //$NON-NLS-1$
		
		
		public static int compare(Priority priority1, Priority priority2) {
			return priority1.value.compareTo(priority2.value);
		}
		
		public static Priority get(String literal) {
			if (literal == null) {
				return NORMAL;
			}
			for (Priority priority : values()) {
				if (StringTools.stringsAreEqual(literal, priority.literal)) {
					return priority;
				}
			}
			return null;
		}
		
		
		private Integer value;
		private String literal;
		
		
		private Priority(int value, String literal) {
			this.value = Integer.valueOf(value);
			this.literal = literal;
		}
		
		
		@Override
		public String toString() {
			return this.literal;
		}
	}
}
