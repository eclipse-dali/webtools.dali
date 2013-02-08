/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbMappingKeys;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;


public class ELJavaXmlJoinNodesMappingDefinition
		extends AbstractJavaAttributeMappingDefinition {
	
	// singleton
	private static final ELJavaXmlJoinNodesMappingDefinition INSTANCE = 
			new ELJavaXmlJoinNodesMappingDefinition();
	
	private static final String[] SUPPORTING_ANNOTATION_NAMES = { ELJaxb.XML_JOIN_NODE };
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	protected ELJavaXmlJoinNodesMappingDefinition() {
		super();
	}


	public String getKey() {
		return ELJaxbMappingKeys.XML_JOIN_NODES_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return ELJaxb.XML_JOIN_NODES;
	}
	
	@Override
	public boolean isSpecified(JaxbPersistentAttribute persistentAttribute) {
		return persistentAttribute.getJavaResourceAttribute().getContainerAnnotation(getAnnotationName()) != null
				|| ! IterableTools.isEmpty(persistentAttribute.getJavaResourceAttribute().getAnnotations(ELJaxb.XML_JOIN_NODE));
	}
	
	public Iterable<String> getSupportingAnnotationNames() {
		return IterableTools.listIterable(SUPPORTING_ANNOTATION_NAMES);
	}
	
	public JaxbAttributeMapping buildMapping(JaxbPersistentAttribute parent, JaxbFactory factory) {
		return new ELJavaXmlJoinNodesMapping(parent);
	}
}
