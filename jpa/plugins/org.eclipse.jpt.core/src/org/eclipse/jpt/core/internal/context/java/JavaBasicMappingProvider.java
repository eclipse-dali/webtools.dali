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
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.platform.JpaFactory;
import org.eclipse.jpt.core.resource.java.Basic;

public class JavaBasicMappingProvider
	implements DefaultJavaAttributeMappingProvider
{

	// singleton
	private static final JavaBasicMappingProvider INSTANCE = new JavaBasicMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaBasicMappingProvider() {
		super();
	}

	public String key() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String annotationName() {
		return Basic.ANNOTATION_NAME;
	}

	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaFactory factory) {
		return factory.buildJavaBasicMapping(parent);
	}
	
	public boolean defaultApplies(JavaPersistentAttribute persistentAttribute) {
		return persistentAttribute.getPersistentAttributeResource().typeIsBasic();
	}

}
