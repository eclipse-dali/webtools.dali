/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import java.util.ArrayList;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jpa.core.JpaPlatformProvider;
import org.eclipse.jpt.jpa.core.JpaResourceDefinition;
import org.eclipse.jpt.jpa.core.JpaResourceModelProvider;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaPlatformProvider;

/**
 * EclipseLink 2.6 platform config.
 * <p>
 * EclipseLink 2.6 implements JPA 2.1 and introduces no new
 * <code>eclipselink-orm.xml</code> schema; it continues to use
 * <code>eclipselink_orm_2_5.xsd</code>. The resource types, resource models and
 * Java mapping definitions are therefore those of
 * {@link EclipseLinkJpaPlatformProvider2_5}, which this provider reuses rather
 * than copies. It exists so that 2.6 can diverge from 2.5 without affecting
 * the 2.5 platform.
 */
public class EclipseLinkJpaPlatformProvider2_6
	extends AbstractJpaPlatformProvider
{
	// singleton
	private static final JpaPlatformProvider INSTANCE = new EclipseLinkJpaPlatformProvider2_6();

	/**
	 * Return the singleton
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJpaPlatformProvider2_6() {
		super();
	}


	// ********** resource models **********

	@Override
	protected void addMostRecentSupportedResourceTypesTo(ArrayList<JptResourceType> types) {
		CollectionTools.addAll(types, EclipseLinkJpaPlatformProvider2_5.MOST_RECENT_SUPPORTED_RESOURCE_TYPES);
	}

	@Override
	protected void addResourceModelProvidersTo(ArrayList<JpaResourceModelProvider> providers) {
		CollectionTools.addAll(providers, EclipseLinkJpaPlatformProvider2_5.RESOURCE_MODEL_PROVIDERS);
	}


	// ********** resource definitions **********

	@Override
	protected void addResourceDefinitionsTo(ArrayList<JpaResourceDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkJpaPlatformProvider2_5.RESOURCE_DEFINITIONS);
	}


	// ********* Java managed types *********

	@Override
	protected void addJavaManagedTypeDefinitionsTo(ArrayList<JavaManagedTypeDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkJpaPlatformProvider2_5.JAVA_MANAGED_TYPE_DEFINITIONS_2_5);
	}


	// ********* Java type mappings *********

	@Override
	protected void addJavaTypeMappingDefinitionsTo(ArrayList<JavaTypeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkJpaPlatformProvider2_5.JAVA_TYPE_MAPPING_DEFINITIONS_2_5);
	}


	// ********* Java attribute mappings *********

	@Override
	protected void addDefaultJavaAttributeMappingDefinitionsTo(ArrayList<DefaultJavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkJpaPlatformProvider2_5.DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_2_5);
	}

	@Override
	protected void addSpecifiedJavaAttributeMappingDefinitionsTo(ArrayList<JavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkJpaPlatformProvider2_5.SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_2_5);
	}
}
