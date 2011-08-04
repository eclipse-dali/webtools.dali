/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2;

import java.util.ArrayList;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jpa.core.JpaPlatformProvider;
import org.eclipse.jpt.jpa.core.JpaResourceModelProvider;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.ResourceDefinition;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaPlatformProvider;
import org.eclipse.jpt.jpa.core.internal.JarResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.JavaResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.OrmResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.PersistenceResourceModelProvider;
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
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmXml2_0Definition;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.persistence.Generic2_0PersistenceXmlDefinition;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class Generic2_0JpaPlatformProvider
	extends AbstractJpaPlatformProvider
{
	// singleton
	private static final JpaPlatformProvider INSTANCE = new Generic2_0JpaPlatformProvider();

	/**
	 * Return the singleton
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private Generic2_0JpaPlatformProvider() {
		super();
	}


	// ********** resource models **********

	public JptResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		if (contentType.equals(JptCommonCorePlugin.JAVA_SOURCE_CONTENT_TYPE)) {
			return JptCommonCorePlugin.JAVA_SOURCE_RESOURCE_TYPE;
		}
		else if (contentType.equals(JptCommonCorePlugin.JAR_CONTENT_TYPE)) {
			return JptCommonCorePlugin.JAR_RESOURCE_TYPE;
		}
		else if (contentType.equals(JptJpaCorePlugin.PERSISTENCE_XML_CONTENT_TYPE)) {
			return JptJpaCorePlugin.PERSISTENCE_XML_2_0_RESOURCE_TYPE;
		}
		else if (contentType.equals(JptJpaCorePlugin.ORM_XML_CONTENT_TYPE)) {
			return JptJpaCorePlugin.ORM_XML_2_0_RESOURCE_TYPE;
		}
		else {
			throw new IllegalArgumentException(contentType.toString());
		}
	}

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
	protected void addResourceDefinitionsTo(ArrayList<ResourceDefinition> definitions) {
		CollectionTools.addAll(definitions, RESOURCE_DEFINITIONS);
	}

	protected static final ResourceDefinition[] RESOURCE_DEFINITIONS = new ResourceDefinition[] {
		GenericPersistenceXmlDefinition.instance(),
		Generic2_0PersistenceXmlDefinition.instance(),
		GenericOrmXmlDefinition.instance(),
		GenericOrmXml2_0Definition.instance()
	};
}
