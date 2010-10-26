/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.jaxb.core.JavaResourceModelProvider;
import org.eclipse.jpt.jaxb.core.JaxbPlatformProvider;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;

/**
 * All the state in the JAXB platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJaxbPlatformProvider
	extends AbstractJaxbPlatformProvider
{
	// singleton
	private static final JaxbPlatformProvider INSTANCE = 
		new GenericJaxbPlatformProvider();
	
	
	/**
	 * Return the singleton.
	 */
	public static JaxbPlatformProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private GenericJaxbPlatformProvider() {
		super();
	}
	
	
	// ********** resource models **********
	
	public JpaResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		if (contentType.equals(JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE)) {
			return JptCorePlugin.JAVA_SOURCE_RESOURCE_TYPE;
		}
//		else if (contentType.equals(JptCorePlugin.JAR_CONTENT_TYPE)) {
//			return JptCorePlugin.JAR_RESOURCE_TYPE;
//		}
//		else if (contentType.equals(JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE)) {
//			return JptCorePlugin.PERSISTENCE_XML_1_0_RESOURCE_TYPE;
//		}
//		else if (contentType.equals(JptCorePlugin.ORM_XML_CONTENT_TYPE)) {
//			return JptCorePlugin.ORM_XML_1_0_RESOURCE_TYPE;
//		}
		throw new IllegalArgumentException(contentType.toString());
	}
	
	@Override
	protected JaxbResourceModelProvider[] buildResourceModelProviders() {
		// order should not be important here
		return new JaxbResourceModelProvider[] {
			JavaResourceModelProvider.instance()};
	}
	
//	
//	// ********** Java type mappings **********
//	
//	@Override
//	protected JavaTypeMappingDefinition[] buildNonNullJavaTypeMappingDefinitions() {
//		// order determined by analyzing order that reference implementation (toplink) uses
//		return new JavaTypeMappingDefinition[] {
//			JavaEntityDefinition.instance(),
//			JavaEmbeddableDefinition.instance(),
//			JavaMappedSuperclassDefinition.instance()};
//	}
//	
//	
//	// ********** Java attribute mappings **********
//	
//	@Override
//	protected JavaAttributeMappingDefinition[] buildNonNullDefaultJavaAttributeMappingDefinitions() {
//		// order determined by analyzing order that reference implementation (toplink) uses
//		return new JavaAttributeMappingDefinition[] {
//			JavaEmbeddedMappingDefinition.instance(),
//			JavaBasicMappingDefinition.instance()};
//	}
//	
//	@Override
//	protected JavaAttributeMappingDefinition[] buildNonNullSpecifiedJavaAttributeMappingDefinitions() {
//		// order determined by analyzing order that reference implementation (eclipselink) uses
//		return new JavaAttributeMappingDefinition[] {
//			JavaTransientMappingDefinition.instance(),
//			JavaIdMappingDefinition.instance(),
//			JavaVersionMappingDefinition.instance(),
//			JavaBasicMappingDefinition.instance(),
//			JavaEmbeddedMappingDefinition.instance(),
//			JavaEmbeddedIdMappingDefinition.instance(),
//			JavaManyToManyMappingDefinition.instance(),
//			JavaManyToOneMappingDefinition.instance(),
//			JavaOneToManyMappingDefinition.instance(),
//			JavaOneToOneMappingDefinition.instance()};
//	}
//	
//	
//	// ********** Mapping Files **********
//	
//	@Override
//	protected ResourceDefinition[] buildResourceDefinitions() {
//		return new ResourceDefinition[] {
//			GenericPersistenceXmlDefinition.instance(),
//			GenericOrmXmlDefinition.instance()};
//	}
}
