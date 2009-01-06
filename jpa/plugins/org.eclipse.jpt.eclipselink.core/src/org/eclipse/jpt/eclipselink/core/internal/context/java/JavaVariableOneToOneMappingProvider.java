/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.java.VariableOneToOneAnnotation;

public class JavaVariableOneToOneMappingProvider
	implements DefaultJavaAttributeMappingProvider
{

	// singleton
	private static final JavaVariableOneToOneMappingProvider INSTANCE = new JavaVariableOneToOneMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private JavaVariableOneToOneMappingProvider() {
		super();
	}

	public String getKey() {
		return EclipseLinkMappingKeys.VARIABLE_ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return VariableOneToOneAnnotation.ANNOTATION_NAME;
	}

	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaFactory factory) {
		return ((EclipseLinkJpaFactory) factory).buildJavaVariableOneToOneMapping(parent);
	}
	
	
	public boolean defaultApplies(JavaPersistentAttribute persistentAttribute) {
		if (persistentAttribute.getResourcePersistentAttribute().typeIsInterface()) {
			if (persistentAttribute.getResourcePersistentAttribute().typeIsContainer()) {
				return false;
			}
			if (persistentAttribute.getResourcePersistentAttribute().typeIsValueHolder()) {
				return false;
			}
			return true;
		}
		return false;
	}
}
