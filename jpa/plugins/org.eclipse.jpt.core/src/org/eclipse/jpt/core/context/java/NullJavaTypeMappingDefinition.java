/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * This mapping definition implementation is used to create a JavaNullAttributeMapping.
 * A JavaNullAttributeMapping should be used when no "mapping" annotation
 * exists on a Java type. 
 */
public class NullJavaTypeMappingDefinition
	implements JavaTypeMappingDefinition
{
	// singleton
	private static final NullJavaTypeMappingDefinition INSTANCE = 
			new NullJavaTypeMappingDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaTypeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private NullJavaTypeMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return null;
	}
	
	public String getAnnotationName() {
		return null;
	}
	
	public JavaTypeMapping buildMapping(JavaPersistentType parent, JpaFactory factory) {
		return factory.buildJavaNullTypeMapping(parent);
	}
	
	/**
	 * This is typically the final test, so it should always apply
	 */
	public boolean test(JavaPersistentType persistentType) {
		return true;
	}
	
	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getAnnotationName());
	}
}
