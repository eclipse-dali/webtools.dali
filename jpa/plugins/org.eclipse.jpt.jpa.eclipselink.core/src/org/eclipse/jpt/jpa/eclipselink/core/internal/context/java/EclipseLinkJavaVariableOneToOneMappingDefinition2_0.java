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

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.DefaultJavaAttributeMappingDefinitionWrapper;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.AccessAnnotation2_0;

public class EclipseLinkJavaVariableOneToOneMappingDefinition2_0
	extends DefaultJavaAttributeMappingDefinitionWrapper
{
	private static final DefaultJavaAttributeMappingDefinition DELEGATE = EclipseLinkJavaVariableOneToOneMappingDefinition.instance();

	// singleton
	private static final DefaultJavaAttributeMappingDefinition INSTANCE = new EclipseLinkJavaVariableOneToOneMappingDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaVariableOneToOneMappingDefinition2_0() {
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

	public static final String[] SUPPORTING_ANNOTATION_NAMES_ARRAY_2_0 = new String[] {
		AccessAnnotation2_0.ANNOTATION_NAME,
	};
	private static final Iterable<String> SUPPORTING_ANNOTATION_NAMES_2_0 = IterableTools.iterable(SUPPORTING_ANNOTATION_NAMES_ARRAY_2_0);

	@SuppressWarnings("unchecked")
	private static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = IterableTools.concatenate(
		DELEGATE.getSupportingAnnotationNames(),
		SUPPORTING_ANNOTATION_NAMES_2_0
	);
}
