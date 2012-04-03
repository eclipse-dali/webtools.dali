/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2;

import java.util.ArrayList;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.jaxb22.GenericJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbPlatform;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlJoinNodesMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlTransformationMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlCDATAAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlDiscriminatorNodeAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlDiscriminatorValueAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlJoinNodeAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlJoinNodesAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlKeyAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.XmlTransformationAnnotationDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_1.ELJaxb_2_1_PlatformDefinition;


public class ELJaxb_2_2_PlatformDefinition
		extends  ELJaxb_2_1_PlatformDefinition {
	
	// singleton
	private static final JaxbPlatformDefinition INSTANCE = new ELJaxb_2_2_PlatformDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JaxbPlatformDefinition instance() {
		return INSTANCE;
	}
	
	
	protected ELJaxb_2_2_PlatformDefinition() {
		super();
	}
	
	
	@Override
	public JaxbPlatformDescription getDescription() {
		return ELJaxbPlatform.VERSION_2_2;
	}
	
	@Override
	protected JaxbPlatformDefinition getGenericJaxbPlatformDefinition() {
		return GenericJaxb_2_2_PlatformDefinition.instance();
	}
	
	@Override
	protected AnnotationDefinition[] buildAnnotationDefinitions() {
		return ArrayTools.addAll(
				super.buildAnnotationDefinitions(),
				XmlCDATAAnnotationDefinition.instance(),
				XmlDiscriminatorNodeAnnotationDefinition.instance(),
				XmlDiscriminatorValueAnnotationDefinition.instance(),
				XmlJoinNodesAnnotationDefinition.instance(),
				XmlKeyAnnotationDefinition.instance(),
				XmlTransformationAnnotationDefinition.instance());
	}
	
	@Override
	protected NestableAnnotationDefinition[] buildNestableAnnotationDefinitions() {
		return ArrayTools.addAll(
				super.buildNestableAnnotationDefinitions(),
				XmlJoinNodeAnnotationDefinition.instance());
	}
	
	@Override
	protected void addSpecifiedJavaAttributeMappingDefinitionsTo(
			ArrayList<JavaAttributeMappingDefinition> definitions) {
		super.addSpecifiedJavaAttributeMappingDefinitionsTo(definitions);
		definitions.add(ELJavaXmlJoinNodesMappingDefinition.instance());
		definitions.add(ELJavaXmlTransformationMappingDefinition.instance());
	}
}
