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

import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.platform.base.JpaBaseContextFactory;

/**
 * This mapping provider implementation is used to create a JavaNullAttributeMapping.
 * A JavaNullAttributeMapping should be used when no "mapping" annotation
 * exists on a Java attribute *and* no default mapping applies.
 */
public class JavaNullAttributeMappingProvider
	implements JavaAttributeMappingProvider
{

	// singleton
	private static final JavaNullAttributeMappingProvider INSTANCE = new JavaNullAttributeMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaNullAttributeMappingProvider() {
		super();
	}

	public String key() {
		return null;
	}

	public String annotationName() {
		return null;
	}
	
	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaBaseContextFactory factory) {
		return factory.buildJavaNullAttributeMapping(parent);
	}
}
