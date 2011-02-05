/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.java;

import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOneToOneMappingDefinition;
import org.eclipse.jpt.core.internal.jpa2.context.java.JavaOneToOneMappingDefinition2_0;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToOneMappingDefinition;

public class JavaEclipseLinkOneToOneMappingDefinition2_0
	extends AbstractJavaOneToOneMappingDefinition
	implements DefaultJavaAttributeMappingDefinition
{
	// singleton
	private static final DefaultJavaAttributeMappingDefinition INSTANCE = new JavaEclipseLinkOneToOneMappingDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaEclipseLinkOneToOneMappingDefinition2_0() {
		super();
	}

	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return COMBINED_SUPPORTING_ANNOTATION_NAMES;
	}

	protected static final String[] COMBINED_SUPPORTING_ANNOTATION_NAMES_ARRAY = ArrayTools.concatenate(
		SUPPORTING_ANNOTATION_NAMES_ARRAY,
		JavaOneToOneMappingDefinition2_0.SUPPORTING_ANNOTATION_NAMES_ARRAY_2_0,
		JavaEclipseLinkOneToOneMappingDefinition.ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES_ARRAY
	);
	protected static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = new ArrayIterable<String>(COMBINED_SUPPORTING_ANNOTATION_NAMES_ARRAY);

	public boolean isDefault(JavaPersistentAttribute persistentAttribute) {
		return JavaEclipseLinkOneToOneMappingDefinition.instance().isDefault(persistentAttribute);
	}
}
