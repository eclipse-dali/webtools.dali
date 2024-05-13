/*******************************************************************************
 * Copyright (c) 2024 Lakshminarayana Nekkanti. All rights reserved.
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
package org.eclipse.jpt.jpa.core.internal.jpa3_0;

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
import org.eclipse.jpt.jpa.core.internal.JarResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.JavaResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.OrmResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.PersistenceResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.context.java.JarDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaPersistentTypeDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaSourceFileDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaTransientMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.GenericPersistenceXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaBasicMappingDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaElementCollectionMappingDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaEmbeddableDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaEmbeddedIdMappingDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaEmbeddedMappingDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaEntityDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaIdMappingDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaManyToManyMappingDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaManyToOneMappingDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaMappedSuperclassDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaOneToManyMappingDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaOneToOneMappingDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaVersionMappingDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmXmlDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.persistence.GenericPersistenceXmlDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.JavaConverterTypeDefinition2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.GenericOrmXmlDefinition2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.persistence.GenericPersistenceXmlDefinition2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_2.context.orm.GenericOrmXmlDefinition2_2;
import org.eclipse.jpt.jpa.core.internal.jpa2_2.context.persistence.GenericPersistenceXmlDefinition2_2;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.context.orm.GenericOrmXmlDefinition3_0;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.context.persistence.GenericPersistenceXmlDefinition3_0;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJpaPlatformProvider3_0
	extends AbstractJpaPlatformProvider
{
	// singleton
	private static final JpaPlatformProvider INSTANCE = new GenericJpaPlatformProvider3_0();

	/**
	 * Return the singleton
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private GenericJpaPlatformProvider3_0() {
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
		GenericOrmXmlDefinition3_0.instance().getResourceType()
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
		OrmResourceModelProvider.instance()
	};


	// ********* Java managed types *********

	/**
	 * To the specified list, add java managed type definitions to use for 
	 * analyzing the type given all annotations on it. The order is 
	 * important, as once a managed type definition tests positive for a
	 * type, all following managed type definitions are ignored.
	 */
	@Override
	protected void addJavaManagedTypeDefinitionsTo(ArrayList<JavaManagedTypeDefinition> definitions) {
		CollectionTools.addAll(definitions, JAVA_MANAGED_TYPE_DEFINITIONS_3_0);
	}

	protected static final JavaManagedTypeDefinition[] JAVA_MANAGED_TYPE_DEFINITIONS_3_0 = new JavaManagedTypeDefinition[] {
		JavaPersistentTypeDefinition.instance(),
		JavaConverterTypeDefinition2_1.instance()
	};


	// ********** Java type mappings **********

	@Override
	protected void addJavaTypeMappingDefinitionsTo(ArrayList<JavaTypeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, JAVA_TYPE_MAPPING_DEFINITIONS);
	}

	// order matches that used by the Reference Implementation (EclipseLink)
	protected static final JavaTypeMappingDefinition[] JAVA_TYPE_MAPPING_DEFINITIONS = new JavaTypeMappingDefinition[] {
		JavaEntityDefinition2_0.instance(),
		JavaEmbeddableDefinition2_0.instance(),
		JavaMappedSuperclassDefinition2_0.instance()
	};


	// ********** Java attribute mappings **********

	@Override
	protected void addDefaultJavaAttributeMappingDefinitionsTo(ArrayList<DefaultJavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS);
	}

	// order matches that used by the Reference Implementation (EclipseLink)
	protected static final DefaultJavaAttributeMappingDefinition[] DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS = new DefaultJavaAttributeMappingDefinition[] {
		JavaEmbeddedMappingDefinition2_0.instance(),
		JavaBasicMappingDefinition2_0.instance()
	};

	@Override
	protected void addSpecifiedJavaAttributeMappingDefinitionsTo(ArrayList<JavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS);
	}

	// order matches that used by the Reference Implementation (EclipseLink)
	protected static final JavaAttributeMappingDefinition[] SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS = new JavaAttributeMappingDefinition[] {
		JavaTransientMappingDefinition.instance(),
		JavaElementCollectionMappingDefinition2_0.instance(),
		JavaIdMappingDefinition2_0.instance(),
		JavaVersionMappingDefinition2_0.instance(),
		JavaBasicMappingDefinition2_0.instance(),
		JavaEmbeddedMappingDefinition2_0.instance(),
		JavaEmbeddedIdMappingDefinition2_0.instance(),
		JavaManyToManyMappingDefinition2_0.instance(),
		JavaManyToOneMappingDefinition2_0.instance(),
		JavaOneToManyMappingDefinition2_0.instance(),
		JavaOneToOneMappingDefinition2_0.instance()
	};


	// ********** resource definitions **********

	@Override
	protected void addResourceDefinitionsTo(ArrayList<JpaResourceDefinition> definitions) {
		CollectionTools.addAll(definitions, RESOURCE_DEFINITIONS);
	}

	protected static final JpaResourceDefinition[] RESOURCE_DEFINITIONS = new JpaResourceDefinition[] {
		JavaSourceFileDefinition.instance(),
		JarDefinition.instance(),
		GenericPersistenceXmlDefinition.instance(),
		GenericPersistenceXmlDefinition2_0.instance(),
		GenericPersistenceXmlDefinition2_1.instance(),
		GenericPersistenceXmlDefinition2_2.instance(),
		GenericPersistenceXmlDefinition3_0.instance(),
		GenericOrmXmlDefinition.instance(),
		GenericOrmXmlDefinition2_0.instance(),
		GenericOrmXmlDefinition2_1.instance(),
		GenericOrmXmlDefinition2_2.instance(),
		GenericOrmXmlDefinition3_0.instance()
	};
}
