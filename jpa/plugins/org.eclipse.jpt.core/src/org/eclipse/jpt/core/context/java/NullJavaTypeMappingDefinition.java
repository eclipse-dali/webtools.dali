/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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
