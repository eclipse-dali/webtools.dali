/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.core.resource.java.EntityAnnotation;

public class JavaEntityDefinition
	extends AbstractJavaTypeMappingDefinition
{
	// singleton
	private static final JavaEntityDefinition INSTANCE = 
			new JavaEntityDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static JavaTypeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private JavaEntityDefinition() {
		super();
	}
	
	
	public String getKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return EntityAnnotation.ANNOTATION_NAME;
	}
	
	public JavaEntity buildMapping(JavaPersistentType parent, JpaFactory factory) {
		return factory.buildJavaEntity(parent);
	}
}
