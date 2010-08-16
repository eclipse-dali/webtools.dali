/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.ResourceDefinition;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.core.internal.AbstractJpaPlatformProvider;
import org.eclipse.jpt.core.internal.JarResourceModelProvider;
import org.eclipse.jpt.core.internal.JavaResourceModelProvider;
import org.eclipse.jpt.core.internal.OrmResourceModelProvider;
import org.eclipse.jpt.core.internal.PersistenceResourceModelProvider;
import org.eclipse.jpt.core.internal.context.java.JavaBasicMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddableDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedIdMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaEntityDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaManyToManyMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaManyToOneMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaMappedSuperclassDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaTransientMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaVersionMappingDefinition;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmXmlDefinition;
import org.eclipse.jpt.core.internal.jpa2.context.java.JavaElementCollectionMappingDefinition2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmXml2_0Definition;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkOrmResourceModelProvider;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicCollectionMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToManyMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToOneMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceXmlDefinition;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmXml1_1Definition;
import org.eclipse.jpt.eclipselink.core.internal.v1_2.context.orm.EclipseLinkOrmXml1_2Definition;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.java.JavaEclipseLinkIdMappingDefinition2_0;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.orm.EclipseLinkOrmXml2_0Definition;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence.EclipseLink2_0PersistenceXmlDefinition;

/**
 *  EclipseLink2_0JpaPlatformProvider
 */
public class EclipseLink2_0JpaPlatformProvider
		extends AbstractJpaPlatformProvider {
	
	// singleton
	private static final JpaPlatformProvider INSTANCE = 
			new EclipseLink2_0JpaPlatformProvider();
	
	
	/**
	 * Return the singleton
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	protected EclipseLink2_0JpaPlatformProvider() {
		super();
	}
	
	
	// ********** resource models **********
	
	public JpaResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		if (contentType.equals(JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE)) {
			return JptCorePlugin.JAVA_SOURCE_RESOURCE_TYPE;
		}
		else if (contentType.equals(JptCorePlugin.JAR_CONTENT_TYPE)) {
			return JptCorePlugin.JAR_RESOURCE_TYPE;
		}
		else if (contentType.equals(JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE)) {
			return JptCorePlugin.PERSISTENCE_XML_2_0_RESOURCE_TYPE;
		}
		else if (contentType.equals(JptCorePlugin.ORM_XML_CONTENT_TYPE)) {
			return JptCorePlugin.ORM_XML_2_0_RESOURCE_TYPE;
		}
		else if (contentType.equals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE)) {
			return JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_0_RESOURCE_TYPE;
		}
		else {
			throw new IllegalArgumentException(contentType.toString());
		}
	}
	
	@Override
	protected JpaResourceModelProvider[] buildResourceModelProviders() {
		// order should not be important here
		return new JpaResourceModelProvider[] {
			JavaResourceModelProvider.instance(),
			JarResourceModelProvider.instance(),
			PersistenceResourceModelProvider.instance(),
			OrmResourceModelProvider.instance(),
			EclipseLinkOrmResourceModelProvider.instance()};
	}

	
	// ********* java type mappings *********	
	
	@Override
	protected JavaTypeMappingDefinition[] buildNonNullJavaTypeMappingDefinitions() {
		// order determined by analyzing order that eclipselink uses
		// NOTE: no type mappings specific to eclipselink
		return new JavaTypeMappingDefinition[] {
			JavaEntityDefinition.instance(),
			JavaEmbeddableDefinition.instance(),
			JavaMappedSuperclassDefinition.instance()};
	}
	
	
	// ********* java attribute mappings *********	
	
	@Override
	protected JavaAttributeMappingDefinition[] buildNonNullDefaultJavaAttributeMappingDefinitions() {
		// order determined by analyzing order that eclipselink uses
		// NOTE: no new attribute mappings from eclipselink 1.0 to 1.1
		return new JavaAttributeMappingDefinition[] {
			JavaEmbeddedMappingDefinition.instance(),
			JavaEclipseLinkOneToManyMappingDefinition.instance(),
			JavaEclipseLinkOneToOneMappingDefinition.instance(),
			JavaEclipseLinkVariableOneToOneMappingDefinition.instance(),
			JavaBasicMappingDefinition.instance()};
	}
	
	@Override
	protected JavaAttributeMappingDefinition[] buildNonNullSpecifiedJavaAttributeMappingDefinitions() {
		// order determined by analyzing order that eclipselink uses
		// NOTE: no new attribute mappings from eclipselink 1.0 to 2.0
		return new JavaAttributeMappingDefinition[] {
			JavaTransientMappingDefinition.instance(),
			JavaEclipseLinkBasicCollectionMappingDefinition.instance(),
			JavaEclipseLinkBasicMapMappingDefinition.instance(),
			JavaElementCollectionMappingDefinition2_0.instance(),
			JavaEclipseLinkIdMappingDefinition2_0.instance(),
			JavaVersionMappingDefinition.instance(),
			JavaBasicMappingDefinition.instance(),
			JavaEmbeddedMappingDefinition.instance(),
			JavaEmbeddedIdMappingDefinition.instance(),
			JavaEclipseLinkTransformationMappingDefinition.instance(),
			JavaManyToManyMappingDefinition.instance(),
			JavaManyToOneMappingDefinition.instance(),
			JavaEclipseLinkOneToManyMappingDefinition.instance(),
			JavaEclipseLinkOneToOneMappingDefinition.instance(),
			JavaEclipseLinkVariableOneToOneMappingDefinition.instance()};
	}
	
	
	// ********* mapping files *********	
	
	@Override
	protected ResourceDefinition[] buildResourceDefinitions() {
		// order should not be important here
		return new ResourceDefinition[] {
			EclipseLinkPersistenceXmlDefinition.instance(),
			EclipseLink2_0PersistenceXmlDefinition.instance(),
			GenericOrmXmlDefinition.instance(),
			GenericOrmXml2_0Definition.instance(),
			EclipseLinkOrmXmlDefinition.instance(),
			EclipseLinkOrmXml1_1Definition.instance(),
			EclipseLinkOrmXml1_2Definition.instance(),
			EclipseLinkOrmXml2_0Definition.instance()};
	}
}