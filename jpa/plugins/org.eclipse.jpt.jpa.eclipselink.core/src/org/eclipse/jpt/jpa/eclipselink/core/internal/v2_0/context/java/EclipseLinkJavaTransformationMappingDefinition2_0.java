/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.java;

import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaAttributeMappingDefinitionWrapper;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaTransformationMappingDefinition;

public class EclipseLinkJavaTransformationMappingDefinition2_0
	extends JavaAttributeMappingDefinitionWrapper
{
	private static final JavaAttributeMappingDefinition DELEGATE = EclipseLinkJavaTransformationMappingDefinition.instance();

	// singleton
	private static final JavaAttributeMappingDefinition INSTANCE = new EclipseLinkJavaTransformationMappingDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaTransformationMappingDefinition2_0() {
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

	public static final String[] SUPPORTING_ANNOTATION_NAMES_ARRAY_2_0 = new String[] {
		Access2_0Annotation.ANNOTATION_NAME,
	};
	private static final Iterable<String> SUPPORTING_ANNOTATION_NAMES_2_0 = new ArrayIterable<String>(SUPPORTING_ANNOTATION_NAMES_ARRAY_2_0);

	@SuppressWarnings("unchecked")
	private static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = new CompositeIterable<String>(
		DELEGATE.getSupportingAnnotationNames(),
		SUPPORTING_ANNOTATION_NAMES_2_0
	);

}
