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
import org.eclipse.jpt.jpa.core.internal.context.java.JavaPersistentTypeDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaSourceFileDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmXmlDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.GenericOrmXmlDefinition2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_2.context.orm.GenericOrmXmlDefinition2_2;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.context.java.JakartaDefaultJavaAttributeMappingDefinitionAdapter;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.context.java.JakartaJavaAttributeMappingDefinitionAdapter;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.context.java.JavaConverterTypeDefinition3_0;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.context.orm.GenericOrmXmlDefinition3_0;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.context.persistence.GenericPersistenceXmlDefinition3_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaEmbeddableDefinition3_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaEntityDefinition3_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaIdMappingDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaIdMappingDefinition3_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaMappedSuperclassDefinition3_0;
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
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition2_4;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition2_5;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition2_7;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition3_0;

/**
 * EclipseLink 3.0 platform config - the first EclipseLink release on
 * {@code jakarta.persistence}.
 * <p>
 * EclipseLink 3.0 implements Jakarta Persistence 3.0 and introduces
 * <code>eclipselink_orm_3_0.xsd</code>, whose element set is identical to 2.5
 * apart from the removal of the deprecated <code>shared</code> attribute on
 * <code>&lt;cache&gt;</code>. The EclipseLink mapping and persistence unit
 * models therefore carry over; what changes is that the Java mapping
 * definitions must match {@code jakarta.persistence.*} annotations, which is
 * handled by wrapping the EclipseLink 2.x definitions in the Jakarta adapters.
 */
public class EclipseLinkJpaPlatformProvider3_0
	extends AbstractJpaPlatformProvider
{
	// singleton
	private static final JpaPlatformProvider INSTANCE = new EclipseLinkJpaPlatformProvider3_0();

	/**
	 * Return the singleton
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJpaPlatformProvider3_0() {
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
		GenericPersistenceXmlDefinition3_0.instance().getResourceType(),
		GenericOrmXmlDefinition3_0.instance().getResourceType(),
		EclipseLinkOrmXmlDefinition3_0.instance().getResourceType()
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
		EclipseLinkPersistenceXmlDefinition3_0.instance(),
		GenericOrmXmlDefinition.instance(),
		GenericOrmXmlDefinition2_0.instance(),
		GenericOrmXmlDefinition2_1.instance(),
		GenericOrmXmlDefinition2_2.instance(),
		GenericOrmXmlDefinition3_0.instance(),
		EclipseLinkOrmXmlDefinition.instance(),
		EclipseLinkOrmXmlDefinition1_1.instance(),
		EclipseLinkOrmXmlDefinition1_2.instance(),
		EclipseLinkOrmXmlDefinition2_0.instance(),
		EclipseLinkOrmXmlDefinition2_1.instance(),
		EclipseLinkOrmXmlDefinition2_2.instance(),
		EclipseLinkOrmXmlDefinition2_3.instance(),
		EclipseLinkOrmXmlDefinition2_4.instance(),
		EclipseLinkOrmXmlDefinition2_5.instance(),
		EclipseLinkOrmXmlDefinition3_0.instance()
	};


	// ********* Java managed types *********

	@Override
	protected void addJavaManagedTypeDefinitionsTo(ArrayList<JavaManagedTypeDefinition> definitions) {
		CollectionTools.addAll(definitions, JAVA_MANAGED_TYPE_DEFINITIONS_3_0);
	}

	protected static final JavaManagedTypeDefinition[] JAVA_MANAGED_TYPE_DEFINITIONS_3_0 = new JavaManagedTypeDefinition[] {
		JavaPersistentTypeDefinition.instance(),
		JavaConverterTypeDefinition3_0.instance()
	};


	// ********* Java type mappings *********

	@Override
	protected void addJavaTypeMappingDefinitionsTo(ArrayList<JavaTypeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, JAVA_TYPE_MAPPING_DEFINITIONS_3_0);
	}

	// order matches that used by EclipseLink
	protected static final JavaTypeMappingDefinition[] JAVA_TYPE_MAPPING_DEFINITIONS_3_0 = new JavaTypeMappingDefinition[] {
		EclipseLinkJavaEntityDefinition3_0.instance(),
		EclipseLinkJavaEmbeddableDefinition3_0.instance(),
		EclipseLinkJavaMappedSuperclassDefinition3_0.instance()
	};


	// ********* Java attribute mappings *********

	/**
	 * The EclipseLink 2.5 attribute mapping definitions, each wrapped so that it
	 * matches the {@code jakarta.persistence.*} annotation instead of the
	 * {@code javax.persistence.*} one. EclipseLink's own annotation names are
	 * left untouched by the adapter.
	 */
	protected static final DefaultJavaAttributeMappingDefinition[] DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_3_0 =
			buildJakartaDefaultDefinitions();

	private static DefaultJavaAttributeMappingDefinition[] buildJakartaDefaultDefinitions() {
		DefaultJavaAttributeMappingDefinition[] source = EclipseLinkJpaPlatformProvider2_5.DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_2_5;
		DefaultJavaAttributeMappingDefinition[] result = new DefaultJavaAttributeMappingDefinition[source.length];
		for (int i = 0; i < source.length; i++) {
			result[i] = new JakartaDefaultJavaAttributeMappingDefinitionAdapter(source[i]);
		}
		return result;
	}

	protected static final JavaAttributeMappingDefinition[] SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_3_0 =
			buildJakartaSpecifiedDefinitions();

	private static JavaAttributeMappingDefinition[] buildJakartaSpecifiedDefinitions() {
		JavaAttributeMappingDefinition[] source = EclipseLinkJpaPlatformProvider2_5.SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_2_5;
		JavaAttributeMappingDefinition[] result = new JavaAttributeMappingDefinition[source.length];
		for (int i = 0; i < source.length; i++) {
			// the plain adapter would drop EclipseLink's "default derived id" check
			result[i] = (source[i] == EclipseLinkJavaIdMappingDefinition2_2.instance()) ?
					EclipseLinkJavaIdMappingDefinition3_0.instance() :
					new JakartaJavaAttributeMappingDefinitionAdapter(source[i]);
		}
		return result;
	}

	@Override
	protected void addDefaultJavaAttributeMappingDefinitionsTo(ArrayList<DefaultJavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_3_0);
	}

	@Override
	protected void addSpecifiedJavaAttributeMappingDefinitionsTo(ArrayList<JavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_3_0);
	}
}
