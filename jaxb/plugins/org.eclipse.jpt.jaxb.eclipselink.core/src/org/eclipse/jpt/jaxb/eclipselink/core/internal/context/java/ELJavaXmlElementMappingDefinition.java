/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlElementMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;


public class ELJavaXmlElementMappingDefinition
		extends AbstractJavaAttributeMappingDefinition
		implements DefaultJavaAttributeMappingDefinition {
	
	// singleton
	private static final ELJavaXmlElementMappingDefinition INSTANCE = 
			new ELJavaXmlElementMappingDefinition();
	
	private static final String[] SUPPORTING_ANNOTATION_NAMES = 
			{
				ELJaxb.XML_PATH };
	
	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private ELJavaXmlElementMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return JavaXmlElementMappingDefinition.instance().getKey();
	}
	
	public String getAnnotationName() {
		return JavaXmlElementMappingDefinition.instance().getKey();
	}
	
	public Iterable<String> getSupportingAnnotationNames() {
		return new CompositeIterable<String>(
				JavaXmlElementMappingDefinition.instance().getSupportingAnnotationNames(),
				new ArrayIterable<String>(SUPPORTING_ANNOTATION_NAMES));
	}
	
	public JaxbAttributeMapping buildMapping(JaxbPersistentAttribute persistentAttribute, JaxbFactory factory) {
		return JavaXmlElementMappingDefinition.instance().buildMapping(persistentAttribute, factory);
	}
	
	public boolean isDefault(JaxbPersistentAttribute persistentAttribute) {
		return JavaXmlElementMappingDefinition.instance().isDefault(persistentAttribute);
	}
}
