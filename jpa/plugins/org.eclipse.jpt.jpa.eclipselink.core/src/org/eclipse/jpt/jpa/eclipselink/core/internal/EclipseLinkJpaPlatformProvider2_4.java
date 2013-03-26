/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import java.util.ArrayList;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jpa.core.JpaPlatformProvider;
import org.eclipse.jpt.jpa.core.JpaResourceModelProvider;
import org.eclipse.jpt.jpa.core.JpaResourceDefinition;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaPlatformProvider;
import org.eclipse.jpt.jpa.core.internal.JarResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.JavaResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.OrmResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.PersistenceResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.context.java.JarDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaSourceFileDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaTransientMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmXmlDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.persistence.GenericPersistenceXmlDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaArrayMappingDefinition2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaBasicCollectionMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMapMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMappingDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaElementCollectionMappingDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaEmbeddableDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaEmbeddedIdMappingDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaEmbeddedMappingDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaEntityDefinition2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaIdMappingDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaManyToManyMappingDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaManyToOneMappingDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaMappedSuperclassDefinition2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaOneToManyMappingDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaOneToOneMappingDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaStructureMappingDefinition2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaTransformationMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaVariableOneToOneMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaVersionMappingDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition1_1;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition1_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_1;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_4;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition2_4;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition;

/**
 * EclipseLink 2.4 platform config
 */
public class EclipseLinkJpaPlatformProvider2_4
	extends AbstractJpaPlatformProvider
{
	// singleton
	private static final JpaPlatformProvider INSTANCE = new EclipseLinkJpaPlatformProvider2_4();

	/**
	 * Return the singleton
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJpaPlatformProvider2_4() {
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
		GenericPersistenceXmlDefinition2_0.instance().getResourceType(),
		GenericOrmXmlDefinition2_0.instance().getResourceType(),
		org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_4.instance().getResourceType()
	};

	@Override
	protected void addResourceModelProvidersTo(ArrayList<JpaResourceModelProvider> providers) {
		CollectionTools.addAll(providers, RESOURCE_MODEL_PROVIDERS);
	}

	// order should not be important here
	protected static final JpaResourceModelProvider[] RESOURCE_MODEL_PROVIDERS = new JpaResourceModelProvider[] {
		JavaResourceModelProvider.instance(),
		JarResourceModelProvider.instance(),
		PersistenceResourceModelProvider.instance(),
		OrmResourceModelProvider.instance(),
		EclipseLinkOrmResourceModelProvider.instance()
	};


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
		GenericOrmXmlDefinition.instance(),
		GenericOrmXmlDefinition2_0.instance(),
		EclipseLinkOrmXmlDefinition.instance(),
		EclipseLinkOrmXmlDefinition1_1.instance(),
		EclipseLinkOrmXmlDefinition1_2.instance(),
		EclipseLinkOrmXmlDefinition2_0.instance(),
		EclipseLinkOrmXmlDefinition2_1.instance(),
		EclipseLinkOrmXmlDefinition2_2.instance(),
		EclipseLinkOrmXmlDefinition2_3.instance(),
		EclipseLinkOrmXmlDefinition2_4.instance()
	};


	// ********* Java type mappings *********

	@Override
	protected void addJavaTypeMappingDefinitionsTo(ArrayList<JavaTypeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, JAVA_TYPE_MAPPING_DEFINITIONS_2_4);
	}

	// order matches that used by EclipseLink
	// NB: no EclipseLink-specific mappings
	protected static final JavaTypeMappingDefinition[] JAVA_TYPE_MAPPING_DEFINITIONS_2_4 = new JavaTypeMappingDefinition[] {
		EclipseLinkJavaEntityDefinition2_3.instance(),
		EclipseLinkJavaEmbeddableDefinition2_2.instance(),
		EclipseLinkJavaMappedSuperclassDefinition2_3.instance()
	};


	// ********* Java attribute mappings *********

	@Override
	protected void addDefaultJavaAttributeMappingDefinitionsTo(ArrayList<DefaultJavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_2_4);
	}

	// order matches that used by EclipseLink
	// NB: no change from EclipseLink 1.2 to 2.0
	protected static final DefaultJavaAttributeMappingDefinition[] DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_2_4 = new DefaultJavaAttributeMappingDefinition[] {
		EclipseLinkJavaEmbeddedMappingDefinition2_2.instance(),
		EclipseLinkJavaOneToManyMappingDefinition2_2.instance(),
		EclipseLinkJavaOneToOneMappingDefinition2_2.instance(),
		EclipseLinkJavaVariableOneToOneMappingDefinition2_0.instance(),
		EclipseLinkJavaBasicMappingDefinition2_2.instance()
	};

	@Override
	protected void addSpecifiedJavaAttributeMappingDefinitionsTo(ArrayList<JavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_2_4);
	}

	// order matches that used by EclipseLink
	protected static final JavaAttributeMappingDefinition[] SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS_2_4 = new JavaAttributeMappingDefinition[] {
		JavaTransientMappingDefinition.instance(),
		EclipseLinkJavaBasicCollectionMappingDefinition2_0.instance(),
		EclipseLinkJavaBasicMapMappingDefinition2_0.instance(),
		EclipseLinkJavaArrayMappingDefinition2_3.instance(),
		EclipseLinkJavaElementCollectionMappingDefinition2_2.instance(),
		EclipseLinkJavaIdMappingDefinition2_2.instance(),
		EclipseLinkJavaVersionMappingDefinition2_2.instance(),
		EclipseLinkJavaBasicMappingDefinition2_2.instance(),
		EclipseLinkJavaStructureMappingDefinition2_3.instance(),
		EclipseLinkJavaEmbeddedMappingDefinition2_2.instance(),
		EclipseLinkJavaEmbeddedIdMappingDefinition2_2.instance(),
		EclipseLinkJavaTransformationMappingDefinition2_0.instance(),
		EclipseLinkJavaManyToManyMappingDefinition2_2.instance(),
		EclipseLinkJavaManyToOneMappingDefinition2_2.instance(),
		EclipseLinkJavaOneToManyMappingDefinition2_2.instance(),
		EclipseLinkJavaOneToOneMappingDefinition2_2.instance(),
		EclipseLinkJavaVariableOneToOneMappingDefinition2_0.instance()
	};
}
