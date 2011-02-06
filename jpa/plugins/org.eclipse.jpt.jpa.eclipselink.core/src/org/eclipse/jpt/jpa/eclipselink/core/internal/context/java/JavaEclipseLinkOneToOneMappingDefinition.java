/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaOneToOneMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkJoinFetchAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkPrivateOwnedAnnotation;

public class JavaEclipseLinkOneToOneMappingDefinition
	extends AbstractJavaOneToOneMappingDefinition
	implements DefaultJavaAttributeMappingDefinition
{
	// singleton
	private static final DefaultJavaAttributeMappingDefinition INSTANCE = new JavaEclipseLinkOneToOneMappingDefinition();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaEclipseLinkOneToOneMappingDefinition() {
		super();
	}

	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return COMBINED_SUPPORTING_ANNOTATION_NAMES;
	}

	public static final String[] ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES_ARRAY = new String[] {
		EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME,
		EclipseLinkPrivateOwnedAnnotation.ANNOTATION_NAME
	};

	protected static final String[] COMBINED_SUPPORTING_ANNOTATION_NAMES_ARRAY = ArrayTools.concatenate(
		SUPPORTING_ANNOTATION_NAMES_ARRAY,
		ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES_ARRAY
	);
	protected static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = new ArrayIterable<String>(COMBINED_SUPPORTING_ANNOTATION_NAMES_ARRAY);

	public boolean isDefault(JavaPersistentAttribute persistentAttribute) {
		String targetEntity = persistentAttribute.getSingleReferenceTargetTypeName();
		return (targetEntity != null)
				&& (persistentAttribute.getPersistenceUnit().getEntity(targetEntity) != null);
	}
}
