/*******************************************************************************
 *  Copyright (c) 2011, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbMappingKeys;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;


public class ELJavaXmlTransformationMappingDefinition
		extends AbstractJavaAttributeMappingDefinition {
	
	// singleton
	private static final JavaAttributeMappingDefinition INSTANCE = new ELJavaXmlTransformationMappingDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	protected ELJavaXmlTransformationMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return ELJaxbMappingKeys.XML_TRANSFORMATION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return ELJaxb.XML_TRANSFORMATION;
	}
	
	public Iterable<String> getSupportingAnnotationNames() {
		return EmptyIterable.instance();
	}
	
	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JaxbFactory factory) {
		// TODO: move to factory once API opens up again
		return new ELJavaXmlTransformationMapping(parent);
	}
}
