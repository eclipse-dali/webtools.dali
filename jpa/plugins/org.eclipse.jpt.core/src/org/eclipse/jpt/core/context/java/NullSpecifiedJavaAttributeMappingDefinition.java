/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
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
 * This mapping definition implementation is used to describe the specified mapping for 
 * a java attribute when no other mapping applies.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public class NullSpecifiedJavaAttributeMappingDefinition
	implements JavaAttributeMappingDefinition
{
	// singleton
	private static final NullSpecifiedJavaAttributeMappingDefinition INSTANCE = 
		new NullSpecifiedJavaAttributeMappingDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private NullSpecifiedJavaAttributeMappingDefinition() {
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
	public boolean testDefault(JavaPersistentAttribute persistentAttribute) {
		return false;
	}
	
	/**
	 * This is typically the final test, so it should always apply
	 */
	public boolean testSpecified(JavaPersistentAttribute persistentAttribute) {
		return true;
	}
}
