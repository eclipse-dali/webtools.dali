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

import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;

public abstract class AbstractJavaAttributeMappingProvider
	implements JavaAttributeMappingProvider
{
	protected AbstractJavaAttributeMappingProvider() {
		super();
	}
	
	/**
	 * Default implementation.  Override if the mapping provider ever applies in the default case.
	 */
	public boolean defaultApplies(JavaPersistentAttribute persistentAttribute) {
		return false;
	}
	
	/**
	 * Default implementation.  Override if the mapping provider needs to do more analysis.
	 */
	public boolean specifiedApplies(JavaPersistentAttribute persistentAttribute) {
		return persistentAttribute.getResourcePersistentAttribute().getMappingAnnotation(getAnnotationName()) 
			!= null;
	}
}
