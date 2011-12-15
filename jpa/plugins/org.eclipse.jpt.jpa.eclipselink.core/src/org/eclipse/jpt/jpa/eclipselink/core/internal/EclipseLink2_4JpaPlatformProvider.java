/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import java.util.ArrayList;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jpa.core.JpaPlatformProvider;
import org.eclipse.jpt.jpa.core.ResourceDefinition;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaTransientMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
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
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_1Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_2Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_3Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_4Definition;


public class EclipseLink2_4JpaPlatformProvider
		extends AbstractEclipseLink2_0JpaPlatformProvider {

	// singleton
	private static final JpaPlatformProvider INSTANCE = new EclipseLink2_4JpaPlatformProvider();


	/**
	 * Return the singleton
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLink2_4JpaPlatformProvider() {
		super();
	}


	// ********** resource models **********

	@Override
	public JptResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		if (contentType.equals(JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE)) {
			return JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_4_RESOURCE_TYPE;
		}
		return super.getMostRecentSupportedResourceType(contentType);
	}


	// ********** resource definitions **********

	@Override
	protected void addResourceDefinitionsTo(ArrayList<ResourceDefinition> definitions) {
		super.addResourceDefinitionsTo(definitions);
		CollectionTools.addAll(definitions, RESOURCE_DEFINITIONS);
	}

	protected static final ResourceDefinition[] RESOURCE_DEFINITIONS = new ResourceDefinition[] {
		EclipseLinkOrmXml2_1Definition.instance(),
		EclipseLinkOrmXml2_2Definition.instance(),
		EclipseLinkOrmXml2_3Definition.instance(),
		EclipseLinkOrmXml2_4Definition.instance()
	};

	// ********* Java type mappings *********

	@Override
	protected void addJavaTypeMappingDefinitionsTo(ArrayList<JavaTypeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, JAVA_TYPE_MAPPING_DEFINITIONS);
	}

	// order matches that used by EclipseLink
	// NB: no EclipseLink-specific mappings
	protected static final JavaTypeMappingDefinition[] JAVA_TYPE_MAPPING_DEFINITIONS = new JavaTypeMappingDefinition[] {
		EclipseLinkJavaEntityDefinition2_3.instance(),
		EclipseLinkJavaEmbeddableDefinition2_2.instance(),
		EclipseLinkJavaMappedSuperclassDefinition2_3.instance()
	};


	// ********* Java attribute mappings *********

	@Override
	protected void addDefaultJavaAttributeMappingDefinitionsTo(ArrayList<DefaultJavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS);
	}

	// order matches that used by EclipseLink
	// NB: no change from EclipseLink 1.2 to 2.0
	protected static final DefaultJavaAttributeMappingDefinition[] DEFAULT_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS = new DefaultJavaAttributeMappingDefinition[] {
		EclipseLinkJavaEmbeddedMappingDefinition2_2.instance(),
		EclipseLinkJavaOneToManyMappingDefinition2_2.instance(),
		EclipseLinkJavaOneToOneMappingDefinition2_2.instance(),
		EclipseLinkJavaVariableOneToOneMappingDefinition2_0.instance(),
		EclipseLinkJavaBasicMappingDefinition2_2.instance()
	};

	@Override
	protected void addSpecifiedJavaAttributeMappingDefinitionsTo(ArrayList<JavaAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS);
	}

	// order matches that used by EclipseLink
	protected static final JavaAttributeMappingDefinition[] SPECIFIED_JAVA_ATTRIBUTE_MAPPING_DEFINITIONS = new JavaAttributeMappingDefinition[] {
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
