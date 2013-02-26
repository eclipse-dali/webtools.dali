/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkStructureAnnotation2_3;

public class EclipseLinkJavaStructureMappingDefinition2_3
	implements JavaAttributeMappingDefinition
{
	// singleton
	private static final JavaAttributeMappingDefinition INSTANCE = new EclipseLinkJavaStructureMappingDefinition2_3();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaStructureMappingDefinition2_3() {
		super();
	}

	public String getKey() {
		return EclipseLinkMappingKeys.STRUCTURE_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return EclipseLinkStructureAnnotation2_3.ANNOTATION_NAME;
	}

	public boolean isSpecified(JavaModifiablePersistentAttribute persistentAttribute) {
		return persistentAttribute.getResourceAttribute().getAnnotation(this.getAnnotationName()) != null;
	}

	public Iterable<String> getSupportingAnnotationNames() {
		return EmptyIterable.instance();
	}

	public JavaAttributeMapping buildMapping(JavaModifiablePersistentAttribute persistentAttribute, JpaFactory factory) {
		return new JavaEclipseLinkStructureMapping2_3(persistentAttribute);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
