/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0;

import java.util.ArrayList;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
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
import org.eclipse.jpt.jpa.core.internal.context.java.JavaEmbeddableDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaEmbeddedIdMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaEntityDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaMappedSuperclassDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaTransientMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaEmbeddedMappingDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmXml2_0Definition;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkOrmResourceModelProvider;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkBasicCollectionMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkVersionMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmXml1_1Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v1_2.context.orm.EclipseLinkOrmXml1_2Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.java.JavaEclipseLinkElementCollectionMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.java.JavaEclipseLinkIdMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.java.JavaEclipseLinkManyToManyMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.java.JavaEclipseLinkManyToOneMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.java.JavaEclipseLinkOneToManyMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.java.JavaEclipseLinkOneToOneMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.orm.EclipseLinkOrmXml2_0Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.persistence.EclipseLink2_0PersistenceXmlDefinition;

/**
 *  EclipseLink 2.0 platform config
 */
public abstract class AbstractEclipseLink2_0JpaPlatformProvider
	extends AbstractJpaPlatformProvider
{
	protected AbstractEclipseLink2_0JpaPlatformProvider() {
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
		else if (contentType.equals(JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE)) {
			return JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_0_RESOURCE_TYPE;
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
		JavaEntityDefinition.instance(),
		JavaEmbeddableDefinition.instance(),
		JavaMappedSuperclassDefinition.instance()
	};


	// ********* Java attribute mappings *********

	@Override
	protected void addDefaultJavaAttributeMappingDefinitionsTo(ArrayList<DefaultJavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS);
	}

	// order matches that used by EclipseLink
	// NB: no change from EclipseLink 1.2 to 2.0
	protected static final DefaultJavaAttributeMappingDefinition[] DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS = new DefaultJavaAttributeMappingDefinition[] {
		JavaEmbeddedMappingDefinition2_0.instance(),
		JavaEclipseLinkOneToManyMappingDefinition2_0.instance(),
		JavaEclipseLinkOneToOneMappingDefinition2_0.instance(),
		JavaEclipseLinkVariableOneToOneMappingDefinition.instance(),
		JavaEclipseLinkBasicMappingDefinition.instance()
	};

	@Override
	protected void addSpecifiedJavaAttributeMappingDefinitionsTo(ArrayList<JavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS);
	}

	// order matches that used by EclipseLink
	protected static final JavaAttributeMappingDefinition[] SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS = new JavaAttributeMappingDefinition[] {
		JavaTransientMappingDefinition.instance(),
		JavaEclipseLinkBasicCollectionMappingDefinition.instance(),
		JavaEclipseLinkBasicMapMappingDefinition.instance(),
		JavaEclipseLinkElementCollectionMappingDefinition2_0.instance(),
		JavaEclipseLinkIdMappingDefinition2_0.instance(),
		JavaEclipseLinkVersionMappingDefinition.instance(),
		JavaEclipseLinkBasicMappingDefinition.instance(),
		JavaEmbeddedMappingDefinition2_0.instance(),
		JavaEmbeddedIdMappingDefinition.instance(),
		JavaEclipseLinkTransformationMappingDefinition.instance(),
		JavaEclipseLinkManyToManyMappingDefinition2_0.instance(),
		JavaEclipseLinkManyToOneMappingDefinition2_0.instance(),
		JavaEclipseLinkOneToManyMappingDefinition2_0.instance(),
		JavaEclipseLinkOneToOneMappingDefinition2_0.instance(),
		JavaEclipseLinkVariableOneToOneMappingDefinition.instance()
	};


	// ********** resource definitions **********

	@Override
	protected void addResourceDefinitionsTo(ArrayList<ResourceDefinition> definitions) {
		CollectionTools.addAll(definitions, RESOURCE_DEFINITIONS);
	}

	protected static final ResourceDefinition[] RESOURCE_DEFINITIONS = new ResourceDefinition[] {
		EclipseLinkPersistenceXmlDefinition.instance(),
		EclipseLink2_0PersistenceXmlDefinition.instance(),
		GenericOrmXmlDefinition.instance(),
		GenericOrmXml2_0Definition.instance(),
		EclipseLinkOrmXmlDefinition.instance(),
		EclipseLinkOrmXml1_1Definition.instance(),
		EclipseLinkOrmXml1_2Definition.instance(),
		EclipseLinkOrmXml2_0Definition.instance()
	};
}
