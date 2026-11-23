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
import org.eclipse.jpt.jpa.core.internal.context.java.JarDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaSourceFileDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmXmlDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.GenericOrmXmlDefinition2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_2.context.orm.GenericOrmXmlDefinition2_2;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.context.orm.GenericOrmXmlDefinition3_0;
import org.eclipse.jpt.jpa.core.internal.jpa3_1.context.orm.GenericOrmXmlDefinition3_1;
import org.eclipse.jpt.jpa.core.internal.jpa3_1.context.persistence.GenericPersistenceXmlDefinition3_1;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition1_1;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition1_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_1;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_4;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_5;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition3_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition3_1;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition2_4;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition2_5;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition2_7;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition3_1;

/**
 * EclipseLink 4.0 platform config.
 * <p>
 * EclipseLink 4.0 implements Jakarta Persistence 3.1. It publishes no
 * <code>eclipselink_orm_4_0.xsd</code> - the 4.0 runtime ships
 * <code>eclipselink_orm_3_1.xsd</code> as its EclipseLink mapping file schema -
 * so the most recent EclipseLink mapping file here is 3.1. Jakarta Persistence
 * 3.1 adds no new attribute mapping annotations, so the Jakarta-aware Java
 * mapping definitions are shared with the EclipseLink 3.0 platform.
 */
public class EclipseLinkJpaPlatformProvider4_0
	extends AbstractJpaPlatformProvider
{
	// singleton
	private static final JpaPlatformProvider INSTANCE = new EclipseLinkJpaPlatformProvider4_0();

	/**
	 * Return the singleton
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJpaPlatformProvider4_0() {
		super();
	}


	// ********** resource models **********

	@Override
	protected void addMostRecentSupportedResourceTypesTo(ArrayList<JptResourceType> types) {
		CollectionTools.addAll(types, MOST_RECENT_SUPPORTED_RESOURCE_TYPES);
	}

	// order should not be important here
	protected static final JptResourceType[] MOST_RECENT_SUPPORTED_RESOURCE_TYPES = new JptResourceType[] {
		JavaSourceFileDefinition.instance().getResourceType(),
		JarDefinition.instance().getResourceType(),
		GenericPersistenceXmlDefinition3_1.instance().getResourceType(),
		GenericOrmXmlDefinition3_1.instance().getResourceType(),
		EclipseLinkOrmXmlDefinition3_1.instance().getResourceType()
	};

	@Override
	protected void addResourceModelProvidersTo(ArrayList<JpaResourceModelProvider> providers) {
		CollectionTools.addAll(providers, EclipseLinkJpaPlatformProvider2_5.RESOURCE_MODEL_PROVIDERS);
	}


	// ********** resource definitions **********

	@Override
	protected void addResourceDefinitionsTo(ArrayList<JpaResourceDefinition> definitions) {
		CollectionTools.addAll(definitions, RESOURCE_DEFINITIONS);
	}

	protected static final JpaResourceDefinition[] RESOURCE_DEFINITIONS = new JpaResourceDefinition[] {
		JavaSourceFileDefinition.instance(),
		JarDefinition.instance(),
		EclipseLinkPersistenceXmlDefinition.instance(),
		EclipseLinkPersistenceXmlDefinition2_4.instance(),
		EclipseLinkPersistenceXmlDefinition2_5.instance(),
		EclipseLinkPersistenceXmlDefinition2_7.instance(),
		// No 3_0 entry: a JPA 3.1 persistence.xml declares version="3.0", so the 3_1 definition already handles it (both would clash)
		EclipseLinkPersistenceXmlDefinition3_1.instance(),
		GenericOrmXmlDefinition.instance(),
		GenericOrmXmlDefinition2_0.instance(),
		GenericOrmXmlDefinition2_1.instance(),
		GenericOrmXmlDefinition2_2.instance(),
		GenericOrmXmlDefinition3_0.instance(),
		GenericOrmXmlDefinition3_1.instance(),
		EclipseLinkOrmXmlDefinition.instance(),
		EclipseLinkOrmXmlDefinition1_1.instance(),
		EclipseLinkOrmXmlDefinition1_2.instance(),
		EclipseLinkOrmXmlDefinition2_0.instance(),
		EclipseLinkOrmXmlDefinition2_1.instance(),
		EclipseLinkOrmXmlDefinition2_2.instance(),
		EclipseLinkOrmXmlDefinition2_3.instance(),
		EclipseLinkOrmXmlDefinition2_4.instance(),
		EclipseLinkOrmXmlDefinition2_5.instance(),
		EclipseLinkOrmXmlDefinition3_0.instance(),
		EclipseLinkOrmXmlDefinition3_1.instance()
	};


	// ********* Java managed types *********

	@Override
	protected void addJavaManagedTypeDefinitionsTo(ArrayList<JavaManagedTypeDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkJpaPlatformProvider3_0.JAVA_MANAGED_TYPE_DEFINITIONS_3_0);
	}


	// ********* Java type mappings *********

	@Override
	protected void addJavaTypeMappingDefinitionsTo(ArrayList<JavaTypeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkJpaPlatformProvider3_0.JAVA_TYPE_MAPPING_DEFINITIONS_3_0);
	}


	// ********* Java attribute mappings *********

	@Override
	protected void addDefaultJavaAttributeMappingDefinitionsTo(ArrayList<DefaultJavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkJpaPlatformProvider3_0.DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_3_0);
	}

	@Override
	protected void addSpecifiedJavaAttributeMappingDefinitionsTo(ArrayList<JavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkJpaPlatformProvider3_0.SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_3_0);
	}
}
