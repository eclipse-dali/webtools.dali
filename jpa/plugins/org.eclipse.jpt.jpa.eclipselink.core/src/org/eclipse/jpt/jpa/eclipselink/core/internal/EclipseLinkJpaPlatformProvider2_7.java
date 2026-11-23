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
import org.eclipse.jpt.jpa.core.internal.jpa2_2.context.persistence.GenericPersistenceXmlDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition1_1;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition1_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_1;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_4;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_5;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition2_4;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition2_5;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition2_7;

/**
 * EclipseLink 2.7 platform config.
 * <p>
 * EclipseLink 2.7 implements JPA 2.2, so the generic <code>persistence.xml</code>
 * and <code>orm.xml</code> 2.2 resources are added on top of the 2.5 set. The
 * EclipseLink mapping file itself is unchanged: EclipseLink 2.6 and 2.7 both
 * still use <code>eclipselink_orm_2_5.xsd</code>, so the EclipseLink resource
 * definitions and the Java mapping definitions are shared with 2.5.
 */
public class EclipseLinkJpaPlatformProvider2_7
	extends AbstractJpaPlatformProvider
{
	// singleton
	private static final JpaPlatformProvider INSTANCE = new EclipseLinkJpaPlatformProvider2_7();

	/**
	 * Return the singleton
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJpaPlatformProvider2_7() {
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
		GenericPersistenceXmlDefinition2_2.instance().getResourceType(),
		GenericOrmXmlDefinition2_2.instance().getResourceType(),
		EclipseLinkOrmXmlDefinition2_5.instance().getResourceType()
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
		GenericOrmXmlDefinition.instance(),
		GenericOrmXmlDefinition2_0.instance(),
		GenericOrmXmlDefinition2_1.instance(),
		GenericOrmXmlDefinition2_2.instance(),
		EclipseLinkOrmXmlDefinition.instance(),
		EclipseLinkOrmXmlDefinition1_1.instance(),
		EclipseLinkOrmXmlDefinition1_2.instance(),
		EclipseLinkOrmXmlDefinition2_0.instance(),
		EclipseLinkOrmXmlDefinition2_1.instance(),
		EclipseLinkOrmXmlDefinition2_2.instance(),
		EclipseLinkOrmXmlDefinition2_3.instance(),
		EclipseLinkOrmXmlDefinition2_4.instance(),
		EclipseLinkOrmXmlDefinition2_5.instance()
	};


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
