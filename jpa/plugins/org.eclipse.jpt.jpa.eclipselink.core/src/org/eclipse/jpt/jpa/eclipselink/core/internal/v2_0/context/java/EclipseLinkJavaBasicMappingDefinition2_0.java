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

import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaAttributeMappingDefinitionWrapper;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaBasicMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMappingDefinition;

public class EclipseLinkJavaBasicMappingDefinition2_0
	extends JavaAttributeMappingDefinitionWrapper
	implements DefaultJavaAttributeMappingDefinition
{
	private static final JavaAttributeMappingDefinition DELEGATE = JavaBasicMappingDefinition2_0.instance();

	// singleton
	private static final DefaultJavaAttributeMappingDefinition INSTANCE = new EclipseLinkJavaBasicMappingDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaBasicMappingDefinition2_0() {
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

	@SuppressWarnings("unchecked")
	private static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = new CompositeIterable<String>(
		DELEGATE.getSupportingAnnotationNames(),
		EclipseLinkJavaBasicMappingDefinition.ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES
	);

	public boolean isDefault(JavaPersistentAttribute persistentAttribute) {
		return EclipseLinkJavaBasicMappingDefinition.instance().isDefault(persistentAttribute);
	}
}
