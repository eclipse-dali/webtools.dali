/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.java;

import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaAttributeMappingDefinitionWrapper;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaIdMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMappingDefinition;

public class EclipseLinkJavaIdMappingDefinition2_0
	extends JavaAttributeMappingDefinitionWrapper
{
	private static final JavaAttributeMappingDefinition DELEGATE = JavaIdMappingDefinition2_0.instance();

	// singleton
	private static final JavaAttributeMappingDefinition INSTANCE = new EclipseLinkJavaIdMappingDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaIdMappingDefinition2_0() {
		super();
	}

	@Override
	protected JavaAttributeMappingDefinition getDelegate() {
		return DELEGATE;
	}

	/**
	 * Check whether the <code>Id</code> mapping is "specified" according to
	 * the spec then check for EclipseLink default mappings that negate the
	 * <code>Id</code> mapping.
	 */
	@Override
	public boolean isSpecified(JavaPersistentAttribute persistentAttribute) {
		boolean specSpecified = super.isSpecified(persistentAttribute);
		return specSpecified && ! this.isDefaultDerivedId(persistentAttribute);
	}

	/**
	 * EclipseLink supports default M:1 and 1:1 mappings.
	 */
	private boolean isDefaultDerivedId(JavaPersistentAttribute persistentAttribute) {
		String defaultKey = persistentAttribute.getDefaultMappingKey();
		return Tools.valuesAreEqual(defaultKey, this.getManyToOneKey()) ||
				Tools.valuesAreEqual(defaultKey, this.getOneToOneKey());
	}

	private String getManyToOneKey() {
		return EclipseLinkJavaOneToOneMappingDefinition2_0.instance().getKey();
	}

	private String getOneToOneKey() {
		return EclipseLinkJavaOneToOneMappingDefinition2_0.instance().getKey();
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
}
