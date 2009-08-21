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
 * This mapping provider implementation is used to create a JavaNullAttributeMapping.
 * A JavaNullAttributeMapping should be used when no "mapping" annotation
 * exists on a Java type. 
 */
public class NullJavaTypeMappingProvider
	implements JavaTypeMappingProvider
{
	// singleton
	private static final NullJavaTypeMappingProvider INSTANCE = 
			new NullJavaTypeMappingProvider();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaTypeMappingProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private NullJavaTypeMappingProvider() {
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
