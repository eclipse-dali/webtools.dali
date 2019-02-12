/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
