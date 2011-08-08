/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.context.java;

import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaTypeMappingDefinitionWrapper;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.java.EclipseLinkJavaEntityDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkMultitenantAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkTenantDiscriminatorColumnAnnotation;

public class EclipseLinkJavaEntityDefinition2_3
	extends JavaTypeMappingDefinitionWrapper
{
	private static final JavaTypeMappingDefinition DELEGATE = EclipseLinkJavaEntityDefinition2_0.instance();

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
		EclipseLinkMultitenantAnnotation.ANNOTATION_NAME,
		EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME,
		EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMNS
	};
	public static final Iterable<String> ECLIPSE_LINK2_3_SUPPORTING_ANNOTATION_NAMES = new ArrayIterable<String>(ECLIPSE_LINK2_3_SUPPORTING_ANNOTATION_NAMES_ARRAY);

	@SuppressWarnings("unchecked")
	private static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = new CompositeIterable<String>(
		DELEGATE.getSupportingAnnotationNames(),
		ECLIPSE_LINK2_3_SUPPORTING_ANNOTATION_NAMES
	);
}
