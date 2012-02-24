/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_1;

import java.util.ArrayList;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.jaxb.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.jaxb21.GenericJaxb_2_1_PlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbPlatform;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.AbstractELJaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlAnyAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlAnyElementMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlElementMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlElementsMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlInverseReferenceMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlValueMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlInverseReferenceAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlPathAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlPathsAnnotationDefinition;


public class ELJaxb_2_1_PlatformDefinition
		extends  AbstractELJaxbPlatformDefinition {
	
	// singleton
	private static final JaxbPlatformDefinition INSTANCE = new ELJaxb_2_1_PlatformDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static JaxbPlatformDefinition instance() {
		return INSTANCE;
	}
	
	
	protected ELJaxb_2_1_PlatformDefinition() {
		super();
	}
	
	
	@Override
	protected JaxbPlatformDefinition getGenericJaxbPlatformDefinition() {
		return GenericJaxb_2_1_PlatformDefinition.instance();
	}
	
	public JaxbPlatformDescription getDescription() {
		return ELJaxbPlatform.VERSION_2_1;
	}
	
	public JaxbFactory getFactory() {
		return ELJaxb_2_1_Factory.instance();
	}
	
	@Override
	protected JaxbResourceModelProvider[] buildResourceModelProviders() {
		return ArrayTools.array(
				getGenericJaxbPlatformDefinition().getResourceModelProviders(), 
				new JaxbResourceModelProvider[0]);
			
	}
	
	public JptResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		return getGenericJaxbPlatformDefinition().getMostRecentSupportedResourceType(contentType);
	}
	
	@Override
	protected AnnotationDefinition[] buildAnnotationDefinitions() {
		return ArrayTools.addAll(
				getGenericJaxbPlatformDefinition().getAnnotationDefinitions(),
				XmlInverseReferenceAnnotationDefinition.instance(),
				XmlPathsAnnotationDefinition.instance());
	}
	
	@Override
	protected NestableAnnotationDefinition[] buildNestableAnnotationDefinitions() {
		return ArrayTools.addAll(
				getGenericJaxbPlatformDefinition().getNestableAnnotationDefinitions(),
				XmlPathAnnotationDefinition.instance());
	}
	
	@Override
	protected void addDefaultJavaAttributeMappingDefinitionsTo(
			ArrayList<DefaultJavaAttributeMappingDefinition> definitions) {
		definitions.add(ELJavaXmlAttributeMappingDefinition.instance());
		definitions.add(ELJavaXmlElementMappingDefinition.instance());
		CollectionTools.addAll(definitions, getGenericJaxbPlatformDefinition().getDefaultJavaAttributeMappingDefinitions());
	}
	
	@Override
	protected void addSpecifiedJavaAttributeMappingDefinitionsTo(
			ArrayList<JavaAttributeMappingDefinition> definitions) {
		definitions.add(ELJavaXmlAnyAttributeMappingDefinition.instance());
		definitions.add(ELJavaXmlAnyElementMappingDefinition.instance());
		definitions.add(ELJavaXmlAttributeMappingDefinition.instance());
		definitions.add(ELJavaXmlElementMappingDefinition.instance());
		definitions.add(ELJavaXmlElementsMappingDefinition.instance());
		definitions.add(ELJavaXmlInverseReferenceMappingDefinition.instance());
		definitions.add(ELJavaXmlValueMappingDefinition.instance());
		CollectionTools.addAll(definitions, getGenericJaxbPlatformDefinition().getSpecifiedJavaAttributeMappingDefinitions());
	}
}
