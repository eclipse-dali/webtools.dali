/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.platform.JpaFactory;

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
	 * Ensure non-instantiability.
	 */
	private JavaNullTypeMappingProvider() {
		super();
	}

	public String key() {
		return null;
	}

	public String annotationName() {
		return null;
	}
	
	public JavaTypeMapping buildMapping(JavaPersistentType parent, JpaFactory factory) {
		return factory.buildJavaNullTypeMapping(parent);
	}

}
