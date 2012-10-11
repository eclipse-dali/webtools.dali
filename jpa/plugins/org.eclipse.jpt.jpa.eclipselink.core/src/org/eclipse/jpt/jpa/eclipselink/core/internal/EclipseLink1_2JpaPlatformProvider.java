/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import java.util.ArrayList;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jpa.core.JpaPlatformProvider;
import org.eclipse.jpt.jpa.core.JpaResourceModelProvider;
import org.eclipse.jpt.jpa.core.ResourceDefinition;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaPlatformProvider;
import org.eclipse.jpt.jpa.core.internal.JarResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.JavaResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.OrmResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.PersistenceResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.context.java.JarDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaBasicMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaEmbeddedIdMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaEmbeddedMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaSourceFileDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaTransientMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.GenericPersistenceXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaBasicCollectionMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMapMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaEmbeddableDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaEntityDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaIdMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaManyToManyMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaManyToOneMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaMappedSuperclassDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaOneToManyMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaOneToOneMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaTransformationMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaVariableOneToOneMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaVersionMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml1_1Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml1_2Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition;

/**
 * EclipseLink 2.2 platform config
 */
public class EclipseLink1_2JpaPlatformProvider
	extends AbstractJpaPlatformProvider
{
	// singleton
	private static final JpaPlatformProvider INSTANCE = new EclipseLink1_2JpaPlatformProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private EclipseLink1_2JpaPlatformProvider() {
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
		GenericPersistenceXmlDefinition.instance().getResourceType(),
		GenericOrmXmlDefinition.instance().getResourceType(),
		org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml1_2Definition.instance().getResourceType()
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


	// ********* Java type mappings *********

	@Override
	protected void addJavaTypeMappingDefinitionsTo(ArrayList<JavaTypeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, JAVA_TYPE_MAPPING_DEFINITIONS);
	}

	// order matches that used by EclipseLink
	// NB: no EclipseLink-specific mappings
	protected static final JavaTypeMappingDefinition[] JAVA_TYPE_MAPPING_DEFINITIONS = new JavaTypeMappingDefinition[] {
		EclipseLinkJavaEntityDefinition.instance(),
		EclipseLinkJavaEmbeddableDefinition.instance(),
		EclipseLinkJavaMappedSuperclassDefinition.instance()
	};


	// ********* Java attribute mappings *********

	@Override
	protected void addDefaultJavaAttributeMappingDefinitionsTo(ArrayList<DefaultJavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS);
	}

	// order matches that used by EclipseLink
	// NB: no change from EclipseLink 1.1 to 1.2
	protected static final DefaultJavaAttributeMappingDefinition[] DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS = new DefaultJavaAttributeMappingDefinition[] {
		JavaEmbeddedMappingDefinition.instance(),
		EclipseLinkJavaOneToManyMappingDefinition.instance(),
		EclipseLinkJavaOneToOneMappingDefinition.instance(),
		EclipseLinkJavaVariableOneToOneMappingDefinition.instance(),
		EclipseLinkJavaBasicMappingDefinition.instance()
	};

	@Override
	protected void addSpecifiedJavaAttributeMappingDefinitionsTo(ArrayList<JavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS);
	}

	// order matches that used by EclipseLink
	// NB: no change from EclipseLink 1.1 to 1.2
	protected static final JavaAttributeMappingDefinition[] SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS = new JavaAttributeMappingDefinition[] {
		JavaTransientMappingDefinition.instance(),
		EclipseLinkJavaBasicCollectionMappingDefinition.instance(),
		EclipseLinkJavaBasicMapMappingDefinition.instance(),
		EclipseLinkJavaIdMappingDefinition.instance(),
		EclipseLinkJavaVersionMappingDefinition.instance(),
		JavaBasicMappingDefinition.instance(),
		JavaEmbeddedMappingDefinition.instance(),
		JavaEmbeddedIdMappingDefinition.instance(),
		EclipseLinkJavaTransformationMappingDefinition.instance(),
		EclipseLinkJavaManyToManyMappingDefinition.instance(),
		EclipseLinkJavaManyToOneMappingDefinition.instance(),
		EclipseLinkJavaOneToManyMappingDefinition.instance(),
		EclipseLinkJavaOneToOneMappingDefinition.instance(),
		EclipseLinkJavaVariableOneToOneMappingDefinition.instance()
	};


	// ********** resource definitions **********

	@Override
	protected void addResourceDefinitionsTo(ArrayList<ResourceDefinition> definitions) {
		CollectionTools.addAll(definitions, RESOURCE_DEFINITIONS);
	}

	protected static final ResourceDefinition[] RESOURCE_DEFINITIONS = new ResourceDefinition[] {
		JavaSourceFileDefinition.instance(),
		JarDefinition.instance(),
		EclipseLinkPersistenceXmlDefinition.instance(),
		GenericOrmXmlDefinition.instance(),
		EclipseLinkOrmXmlDefinition.instance(),
		EclipseLinkOrmXml1_1Definition.instance(),
		EclipseLinkOrmXml1_2Definition.instance()
	};
}
