/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;

public abstract class AbstractJavaAttributeMappingDefinition
	implements JavaAttributeMappingDefinition
{
	protected AbstractJavaAttributeMappingDefinition() {
		super();
	}
	
	
	/**
	 * Default implementation.  Override if the mapping definition applies in the annotationless case.
	 */
	public boolean testDefault(JavaPersistentAttribute persistentAttribute) {
		return false;
	}
	
	/**
	 * Default implementation.  Override if the mapping definition needs to do more analysis.
	 */
	public boolean testSpecified(JavaPersistentAttribute persistentAttribute) {
		return persistentAttribute.getResourcePersistentAttribute().getAnnotation(getAnnotationName()) 
			!= null;
	}
}
