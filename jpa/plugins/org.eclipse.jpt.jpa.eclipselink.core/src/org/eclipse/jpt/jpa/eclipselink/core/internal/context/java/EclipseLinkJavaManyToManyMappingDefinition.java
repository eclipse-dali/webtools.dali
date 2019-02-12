/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaAttributeMappingDefinitionWrapper;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaManyToManyMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.StructConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TypeConverterAnnotation;

public class EclipseLinkJavaManyToManyMappingDefinition
	extends JavaAttributeMappingDefinitionWrapper
{
	private static final JavaAttributeMappingDefinition DELEGATE = JavaManyToManyMappingDefinition.instance();

	// singleton
	private static final JavaAttributeMappingDefinition INSTANCE = new EclipseLinkJavaManyToManyMappingDefinition();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaManyToManyMappingDefinition() {
		super();
	}

	@Override
	protected JavaAttributeMappingDefinition getDelegate() {
		return DELEGATE;
	}

	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return COMBINED_SUPPORTING_ANNOTATION_NAMES;
	}

	public static final String[] ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES_ARRAY = new String[] {
		ConverterAnnotation.ANNOTATION_NAME,
		JoinFetchAnnotation.ANNOTATION_NAME,
		ObjectTypeConverterAnnotation.ANNOTATION_NAME,
		StructConverterAnnotation.ANNOTATION_NAME,
		TypeConverterAnnotation.ANNOTATION_NAME,
	};
	public static final Iterable<String> ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES = IterableTools.iterable(ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES_ARRAY);

	@SuppressWarnings("unchecked")
	private static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = IterableTools.concatenate(
		DELEGATE.getSupportingAnnotationNames(),
		ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES
	);
}
