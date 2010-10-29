/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.jaxb21;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.jaxb.core.internal.AbstractJaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.internal.JavaPackageInfoResourceModelProvider;
import org.eclipse.jpt.jaxb.core.internal.JavaResourceModelProvider;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlAccessorOrderAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlAccessorTypeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlAnyAttributeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlAnyElementAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlAttachmentRefAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlAttributeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlElementAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlElementDeclAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlElementRefAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlElementRefsAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlElementWrapperAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlElementsAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlEnumAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlEnumValueAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlIDAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlIDREFAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlInlineBinaryDataAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlJavaTypeAdapterAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlJavaTypeAdaptersAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlListAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlMimeTypeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlMixedAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlRegistryAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlRootElementAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlSchemaAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlSchemaTypeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlSchemaTypesAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlSeeAlsoAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlTransientAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlTypeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlValueAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.resource.java.AnnotationDefinition;

public class Generic_2_1_JaxbPlatformDefinition
	extends  AbstractJaxbPlatformDefinition
{
	// singleton
	private static final JaxbPlatformDefinition INSTANCE = new Generic_2_1_JaxbPlatformDefinition();

	/**
	 * Return the singleton.
	 */
	public static JaxbPlatformDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private Generic_2_1_JaxbPlatformDefinition() {
		super();
	}

	public JaxbFactory getFactory() {
		return Generic_2_1_JaxbFactory.instance();
	}
	
	@Override
	protected AnnotationDefinition[] buildAnnotationDefinitions() {
		return new AnnotationDefinition[] {
				XmlAccessorOrderAnnotationDefinition.instance(),
				XmlAccessorTypeAnnotationDefinition.instance(),
				XmlAnyAttributeAnnotationDefinition.instance(),
				XmlAnyElementAnnotationDefinition.instance(),
				XmlAttachmentRefAnnotationDefinition.instance(),
				XmlAttributeAnnotationDefinition.instance(),
				XmlElementAnnotationDefinition.instance(),
				XmlElementDeclAnnotationDefinition.instance(),
				XmlElementsAnnotationDefinition.instance(),
				XmlElementRefAnnotationDefinition.instance(),
				XmlElementRefsAnnotationDefinition.instance(),
				XmlElementWrapperAnnotationDefinition.instance(),
				XmlEnumAnnotationDefinition.instance(),
				XmlEnumValueAnnotationDefinition.instance(),
				XmlIDAnnotationDefinition.instance(),
				XmlIDREFAnnotationDefinition.instance(),
				XmlInlineBinaryDataAnnotationDefinition.instance(),
				XmlJavaTypeAdapterAnnotationDefinition.instance(),
				XmlJavaTypeAdaptersAnnotationDefinition.instance(),
				XmlListAnnotationDefinition.instance(),
				XmlMimeTypeAnnotationDefinition.instance(),
				XmlMixedAnnotationDefinition.instance(),
				XmlRegistryAnnotationDefinition.instance(),
				XmlRootElementAnnotationDefinition.instance(),
				XmlSchemaAnnotationDefinition.instance(),
				XmlSchemaTypeAnnotationDefinition.instance(),
				XmlSchemaTypesAnnotationDefinition.instance(),
				XmlSeeAlsoAnnotationDefinition.instance(),
				XmlTransientAnnotationDefinition.instance(),
				XmlTypeAnnotationDefinition.instance(),
				XmlValueAnnotationDefinition.instance()};
	}
	
	// ********** resource models **********
	
	public JpaResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		if (contentType.equals(JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE)) {
			return JptCorePlugin.JAVA_SOURCE_RESOURCE_TYPE;
		}
		else if (contentType.equals(JptCorePlugin.JAVA_SOURCE_PACKAGE_INFO_CONTENT_TYPE)) {
			return JptCorePlugin.JAVA_SOURCE_PACKAGE_INFO_RESOURCE_TYPE;
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
			JavaResourceModelProvider.instance(),
			JavaPackageInfoResourceModelProvider.instance()};
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
