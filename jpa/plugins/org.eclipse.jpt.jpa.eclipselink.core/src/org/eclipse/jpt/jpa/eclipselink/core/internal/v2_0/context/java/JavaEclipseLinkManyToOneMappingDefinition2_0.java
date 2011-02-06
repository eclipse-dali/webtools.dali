/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.java;

import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaManyToOneMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaManyToOneMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkManyToOneMappingDefinition;

public class JavaEclipseLinkManyToOneMappingDefinition2_0
	extends AbstractJavaManyToOneMappingDefinition
{
	// singleton
	private static final JavaAttributeMappingDefinition INSTANCE = new JavaEclipseLinkManyToOneMappingDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaEclipseLinkManyToOneMappingDefinition2_0() {
		super();
	}

	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return COMBINED_SUPPORTING_ANNOTATION_NAMES;
	}

	protected static final String[] COMBINED_SUPPORTING_ANNOTATION_NAMES_ARRAY = ArrayTools.concatenate(
		SUPPORTING_ANNOTATION_NAMES_ARRAY,
		JavaManyToOneMappingDefinition2_0.SUPPORTING_ANNOTATION_NAMES_ARRAY_2_0,
		JavaEclipseLinkManyToOneMappingDefinition.ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES_ARRAY
	);
	protected static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = new ArrayIterable<String>(COMBINED_SUPPORTING_ANNOTATION_NAMES_ARRAY);
}
