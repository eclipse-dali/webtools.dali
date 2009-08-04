/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;

/**
 * This mapping provider implementation is used to describe the specified mapping for 
 * a java attribute when no other mapping applies.
 */
public class NullSpecifiedJavaAttributeMappingProvider
	implements JavaAttributeMappingProvider
{
	// singleton
	private static final NullSpecifiedJavaAttributeMappingProvider INSTANCE = 
		new NullSpecifiedJavaAttributeMappingProvider();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private NullSpecifiedJavaAttributeMappingProvider() {
		super();
	}
	
	
	public String getKey() {
		return MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return null;
	}
	
	/**
	 * There is no specified mapping in this case, attributes should revert to the default
	 */
	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaFactory factory) {
		return null;
	}
	
	/**
	 * Obviously false
	 */
	public boolean defaultApplies(JavaPersistentAttribute persistentAttribute) {
		return false;
	}
	
	/**
	 * This is typically the final test, so it should always apply
	 */
	public boolean specifiedApplies(JavaPersistentAttribute persistentAttribute) {
		return true;
	}
}
