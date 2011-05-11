/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.jaxb21;

import java.util.ArrayList;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jaxb.core.GenericJaxbPlatform;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.jaxb.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.AbstractJaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.internal.JavaPackageInfoResourceModelProvider;
import org.eclipse.jpt.jaxb.core.internal.JavaResourceModelProvider;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlAnyAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlAnyElementMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlElementMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlElementRefMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlElementRefsMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlElementsMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlTransientMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlValueMappingDefinition;
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
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlListAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlMimeTypeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlMixedAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlRegistryAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlRootElementAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlSchemaAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlSchemaTypeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlSeeAlsoAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlTransientAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlTypeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlValueAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.jaxbindex.JaxbIndexResourceModelProvider;
import org.eclipse.jpt.jaxb.core.internal.resource.jaxbprops.JaxbPropertiesResourceModelProvider;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.jaxb.core.resource.java.NestableAnnotationDefinition;

public class GenericJaxb_2_1_PlatformDefinition
		extends  AbstractJaxbPlatformDefinition {
	
	// singleton
	private static final JaxbPlatformDefinition INSTANCE = new GenericJaxb_2_1_PlatformDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static JaxbPlatformDefinition instance() {
		return INSTANCE;
	}
	
	protected GenericJaxb_2_1_PlatformDefinition() {
		super();
	}
	
	
	public JaxbPlatformDescription getDescription() {
		return GenericJaxbPlatform.VERSION_2_1;
	}
	
	public JaxbFactory getFactory() {
		return GenericJaxb_2_1_Factory.instance();
	}
	
	@Override
	protected JaxbResourceModelProvider[] buildResourceModelProviders() {
		// order should not be important here
		return new JaxbResourceModelProvider[] {
			JavaResourceModelProvider.instance(),
			JavaPackageInfoResourceModelProvider.instance(),
			JaxbIndexResourceModelProvider.instance(),
			JaxbPropertiesResourceModelProvider.instance()};
	}
	
	public JptResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		if (contentType.equals(JptCommonCorePlugin.JAVA_SOURCE_CONTENT_TYPE)) {
			return JptCommonCorePlugin.JAVA_SOURCE_RESOURCE_TYPE;
		}
		else if (contentType.equals(JptCommonCorePlugin.JAVA_SOURCE_PACKAGE_INFO_CONTENT_TYPE)) {
			return JptCommonCorePlugin.JAVA_SOURCE_PACKAGE_INFO_RESOURCE_TYPE;
		}
		throw new IllegalArgumentException(contentType.toString());
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
				XmlElementRefAnnotationDefinition.instance(),
				XmlElementRefsAnnotationDefinition.instance(),
				XmlElementsAnnotationDefinition.instance(),
				XmlElementWrapperAnnotationDefinition.instance(),
				XmlEnumAnnotationDefinition.instance(),
				XmlEnumValueAnnotationDefinition.instance(),
				XmlIDAnnotationDefinition.instance(),
				XmlIDREFAnnotationDefinition.instance(),
				XmlInlineBinaryDataAnnotationDefinition.instance(),
				XmlListAnnotationDefinition.instance(),
				XmlMimeTypeAnnotationDefinition.instance(),
				XmlMixedAnnotationDefinition.instance(),
				XmlRegistryAnnotationDefinition.instance(),
				XmlRootElementAnnotationDefinition.instance(),
				XmlSchemaAnnotationDefinition.instance(),
				XmlSeeAlsoAnnotationDefinition.instance(),
				XmlTransientAnnotationDefinition.instance(),
				XmlTypeAnnotationDefinition.instance(),
				XmlValueAnnotationDefinition.instance()};
	}
	
	@Override
	protected NestableAnnotationDefinition[] buildNestableAnnotationDefinitions() {
		return new NestableAnnotationDefinition[] {
			XmlJavaTypeAdapterAnnotationDefinition.instance(),
			XmlSchemaTypeAnnotationDefinition.instance()
		};
	}
	
	@Override
	protected void addDefaultJavaAttributeMappingDefinitionsTo(ArrayList<DefaultJavaAttributeMappingDefinition> definitions) {
		definitions.add(JavaXmlElementMappingDefinition.instance());
		definitions.add(JavaXmlElementsMappingDefinition.instance());
	}
	
	@Override
	protected void addSpecifiedJavaAttributeMappingDefinitionsTo(ArrayList<JavaAttributeMappingDefinition> definitions) {
		definitions.add(JavaXmlAnyAttributeMappingDefinition.instance());
		definitions.add(JavaXmlAnyElementMappingDefinition.instance());
		definitions.add(JavaXmlAttributeMappingDefinition.instance());
		definitions.add(JavaXmlElementMappingDefinition.instance());
		definitions.add(JavaXmlElementRefMappingDefinition.instance());
		definitions.add(JavaXmlElementRefsMappingDefinition.instance());
		definitions.add(JavaXmlElementsMappingDefinition.instance());
		definitions.add(JavaXmlTransientMappingDefinition.instance());
		definitions.add(JavaXmlValueMappingDefinition.instance());
	}
}
