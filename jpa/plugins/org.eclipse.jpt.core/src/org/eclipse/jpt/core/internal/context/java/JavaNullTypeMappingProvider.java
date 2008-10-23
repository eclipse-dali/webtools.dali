/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;

/**
 * This mapping provider implementation is used to create a JavaNullAttributeMapping.
 * A JavaNullAttributeMapping should be used when no "mapping" annotation
 * exists on a Java type. 
 */
public class JavaNullTypeMappingProvider
	implements JavaTypeMappingProvider
{

	// singleton
	private static final JavaNullTypeMappingProvider INSTANCE = new JavaNullTypeMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaTypeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private JavaNullTypeMappingProvider() {
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

}
