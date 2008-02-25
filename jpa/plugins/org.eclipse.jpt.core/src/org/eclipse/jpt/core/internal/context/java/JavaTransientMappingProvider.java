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

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.platform.JpaFactory;
import org.eclipse.jpt.core.resource.java.Transient;

public class JavaTransientMappingProvider
	implements JavaAttributeMappingProvider
{

	// singleton
	private static final JavaTransientMappingProvider INSTANCE = new JavaTransientMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaTransientMappingProvider() {
		super();
	}

	public String key() {
		return MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String annotationName() {
		return Transient.ANNOTATION_NAME;
	}

	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaFactory factory) {
		return factory.buildJavaTransientMapping(parent);
	}
}
