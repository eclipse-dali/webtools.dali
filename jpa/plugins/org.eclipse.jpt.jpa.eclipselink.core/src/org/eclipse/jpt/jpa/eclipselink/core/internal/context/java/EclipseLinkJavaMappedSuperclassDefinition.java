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
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaMappedSuperclassDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaTypeMappingDefinitionWrapper;

public class EclipseLinkJavaMappedSuperclassDefinition
	extends JavaTypeMappingDefinitionWrapper
{
	private static final JavaTypeMappingDefinition DELEGATE = JavaMappedSuperclassDefinition.instance();

	// singleton
	private static final JavaTypeMappingDefinition INSTANCE = new EclipseLinkJavaMappedSuperclassDefinition();

	/**
	 * Return the singleton.
	 */
	public static JavaTypeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaMappedSuperclassDefinition() {
		super();
	}

	@Override
	protected JavaTypeMappingDefinition getDelegate() {
		return DELEGATE;
	}

	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return COMBINED_SUPPORTING_ANNOTATION_NAMES;
	}
	@SuppressWarnings("unchecked")
	private static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = IterableTools.concatenate(
		DELEGATE.getSupportingAnnotationNames(),
		EclipseLinkJavaEntityDefinition.ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES
	);
}
