/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaTypeMappingDefinitionWrapper;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkMultitenantAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TenantDiscriminatorColumnAnnotation2_3;

public class EclipseLinkJavaEntityDefinition2_3
	extends JavaTypeMappingDefinitionWrapper
{
	private static final JavaTypeMappingDefinition DELEGATE = EclipseLinkJavaEntityDefinition2_2.instance();

	// singleton
	private static final JavaTypeMappingDefinition INSTANCE = new EclipseLinkJavaEntityDefinition2_3();

	/**
	 * Return the singleton.
	 */
	public static JavaTypeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaEntityDefinition2_3() {
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

	public static final String[] ECLIPSE_LINK2_3_SUPPORTING_ANNOTATION_NAMES_ARRAY = new String[] {
		EclipseLinkMultitenantAnnotation2_3.ANNOTATION_NAME,
		TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME,
		EclipseLink.TENANT_DISCRIMINATOR_COLUMNS
	};
	public static final Iterable<String> ECLIPSE_LINK2_3_SUPPORTING_ANNOTATION_NAMES = IterableTools.iterable(ECLIPSE_LINK2_3_SUPPORTING_ANNOTATION_NAMES_ARRAY);

	@SuppressWarnings("unchecked")
	private static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = IterableTools.concatenate(
		DELEGATE.getSupportingAnnotationNames(),
		ECLIPSE_LINK2_3_SUPPORTING_ANNOTATION_NAMES
	);
}
