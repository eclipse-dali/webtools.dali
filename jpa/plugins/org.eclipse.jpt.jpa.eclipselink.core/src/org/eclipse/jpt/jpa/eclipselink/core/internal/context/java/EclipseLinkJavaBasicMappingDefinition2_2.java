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

import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaAttributeMappingDefinitionWrapper;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;

public class EclipseLinkJavaBasicMappingDefinition2_2
	extends JavaAttributeMappingDefinitionWrapper
	implements DefaultJavaAttributeMappingDefinition
{
	private static final DefaultJavaAttributeMappingDefinition DELEGATE = EclipseLinkJavaBasicMappingDefinition2_0.instance();

	// singleton
	private static final DefaultJavaAttributeMappingDefinition INSTANCE = new EclipseLinkJavaBasicMappingDefinition2_2();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaBasicMappingDefinition2_2() {
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

	private static final String[] ECLIPSE_LINK2_2_SUPPORTING_ANNOTATION_NAMES_ARRAY = new String[] {
		EclipseLink.CONVERTERS,
		EclipseLink.OBJECT_TYPE_CONVERTERS,
		EclipseLink.STRUCT_CONVERTERS,
		EclipseLink.TYPE_CONVERTERS,
	};
	public static final Iterable<String> ECLIPSE_LINK2_2_SUPPORTING_ANNOTATION_NAMES = new ArrayIterable<String>(ECLIPSE_LINK2_2_SUPPORTING_ANNOTATION_NAMES_ARRAY);

	@SuppressWarnings("unchecked")
	private static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = new CompositeIterable<String>(
		DELEGATE.getSupportingAnnotationNames(),
		ECLIPSE_LINK2_2_SUPPORTING_ANNOTATION_NAMES
	);


	public boolean isDefault(JavaPersistentAttribute persistentAttribute) {
		return DELEGATE.isDefault(persistentAttribute);
	}
}
