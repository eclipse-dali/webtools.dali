/*******************************************************************************
 * Copyright (c) 2011, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_1;

import java.util.ArrayList;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.jaxb.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.ELJaxbPlatformDefinition;
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
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.Oxm;


public abstract class AbstractELJaxb_2_1_PlatformDefinition
		extends  ELJaxbPlatformDefinition {
	
	protected AbstractELJaxb_2_1_PlatformDefinition() {
		super();
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
		if (contentType.equals(Oxm.CONTENT_TYPE)) {
			return getMostRecentOxmResourceType();
		}
		return getGenericJaxbPlatformDefinition().getMostRecentSupportedResourceType(contentType);
	}
	
	protected abstract JptResourceType getMostRecentOxmResourceType();
	
	@Override
	protected AnnotationDefinition[] buildAnnotationDefinitions() {
		return ArrayTools.addAll(getGenericJaxbPlatformDefinition().getAnnotationDefinitions(),
				new AnnotationDefinition[] {
					XmlInverseReferenceAnnotationDefinition.instance(),
					XmlPathsAnnotationDefinition.instance()
				});
	}
	
	@Override
	protected NestableAnnotationDefinition[] buildNestableAnnotationDefinitions() {
		return ArrayTools.add(
				getGenericJaxbPlatformDefinition().getNestableAnnotationDefinitions(),
				XmlPathAnnotationDefinition.instance()
			);
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
