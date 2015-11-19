/*******************************************************************************
 * Copyright (c) 2011, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2;

import java.util.ArrayList;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.jaxb22.GenericJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.OxmResourceModelProvider;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlJoinNodesMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlTransformationMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlCDATAAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlDiscriminatorNodeAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlDiscriminatorValueAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlJoinNodeAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlJoinNodesAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlKeyAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlTransformationAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_1.AbstractELJaxb_2_1_PlatformDefinition;

public abstract class AbstractELJaxb_2_2_PlatformDefinition
		extends  AbstractELJaxb_2_1_PlatformDefinition {

	protected AbstractELJaxb_2_2_PlatformDefinition() {
		super();
	}
	
	@Override
	protected JaxbPlatformDefinition getGenericJaxbPlatformDefinition() {
		return GenericJaxb_2_2_PlatformDefinition.instance();
	}
	
	@Override
	protected JaxbResourceModelProvider[] buildResourceModelProviders() {
		return ArrayTools.add(super.buildResourceModelProviders(), OxmResourceModelProvider.instance());
	}
	
	@Override
	protected AnnotationDefinition[] buildAnnotationDefinitions() {
		return ArrayTools.addAll(super.buildAnnotationDefinitions(),
				new AnnotationDefinition[] {
					XmlCDATAAnnotationDefinition.instance(),
					XmlDiscriminatorNodeAnnotationDefinition.instance(),
					XmlDiscriminatorValueAnnotationDefinition.instance(),
					XmlJoinNodesAnnotationDefinition.instance(),
					XmlKeyAnnotationDefinition.instance(),
					XmlTransformationAnnotationDefinition.instance()
				}
			);
	}
	
	@Override
	protected NestableAnnotationDefinition[] buildNestableAnnotationDefinitions() {
		return ArrayTools.add(super.buildNestableAnnotationDefinitions(), XmlJoinNodeAnnotationDefinition.instance());
	}
	
	@Override
	protected void addSpecifiedJavaAttributeMappingDefinitionsTo(
			ArrayList<JavaAttributeMappingDefinition> definitions) {
		super.addSpecifiedJavaAttributeMappingDefinitionsTo(definitions);
		definitions.add(ELJavaXmlJoinNodesMappingDefinition.instance());
		definitions.add(ELJavaXmlTransformationMappingDefinition.instance());
	}
}
