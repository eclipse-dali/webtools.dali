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

import org.eclipse.jpt.common.utility.internal.iterable.CompositeIterable;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.DefaultJavaAttributeMappingDefinitionWrapper;

public class EclipseLinkJavaOneToManyMappingDefinition2_2
	extends DefaultJavaAttributeMappingDefinitionWrapper
	implements DefaultJavaAttributeMappingDefinition
{
	private static final DefaultJavaAttributeMappingDefinition DELEGATE = EclipseLinkJavaOneToManyMappingDefinition2_0.instance();

	// singleton
	private static final DefaultJavaAttributeMappingDefinition INSTANCE = new EclipseLinkJavaOneToManyMappingDefinition2_2();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaOneToManyMappingDefinition2_2() {
		super();
	}

	@Override
	protected DefaultJavaAttributeMappingDefinition getDelegate() {
		return DELEGATE;
	}

	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return COMBINED_SUPPORTING_ANNOTATION_NAMES;
	}

	@SuppressWarnings("unchecked")
	private static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = new CompositeIterable<String>(
		DELEGATE.getSupportingAnnotationNames(),
		EclipseLinkJavaBasicMappingDefinition2_2.ECLIPSE_LINK2_2_SUPPORTING_ANNOTATION_NAMES
	);
}
