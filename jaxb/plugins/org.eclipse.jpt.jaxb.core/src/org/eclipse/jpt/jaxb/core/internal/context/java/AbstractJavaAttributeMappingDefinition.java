/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;


public abstract class AbstractJavaAttributeMappingDefinition
		implements JavaAttributeMappingDefinition {
	
	protected AbstractJavaAttributeMappingDefinition() {
		super();
	}

	/**
	 * Default implementation.  Override if the mapping definition needs to do more analysis.
	 */
	public boolean isSpecified(JavaPersistentAttribute persistentAttribute) {
		return persistentAttribute.getJavaResourceAttribute().getAnnotation(getAnnotationName()) 
			!= null;
	}
}
