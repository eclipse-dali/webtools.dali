/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.StructureAnnotation2_3;

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
		return StructureAnnotation2_3.ANNOTATION_NAME;
	}

	public boolean isSpecified(JavaSpecifiedPersistentAttribute persistentAttribute) {
		return persistentAttribute.getResourceAttribute().getAnnotation(this.getAnnotationName()) != null;
	}

	public Iterable<String> getSupportingAnnotationNames() {
		return EmptyIterable.instance();
	}

	public JavaAttributeMapping buildMapping(JavaSpecifiedPersistentAttribute persistentAttribute, JpaFactory factory) {
		return new EclipseLinkJavaStructureMapping2_3(persistentAttribute);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
