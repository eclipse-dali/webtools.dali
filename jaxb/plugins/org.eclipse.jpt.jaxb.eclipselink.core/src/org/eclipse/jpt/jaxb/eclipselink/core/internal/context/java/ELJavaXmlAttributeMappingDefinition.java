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

import java.util.StringTokenizer;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlPathAnnotation;


public class ELJavaXmlAttributeMappingDefinition
		extends AbstractJavaAttributeMappingDefinition
		implements DefaultJavaAttributeMappingDefinition {
	
	// singleton
	private static final ELJavaXmlAttributeMappingDefinition INSTANCE = 
			new ELJavaXmlAttributeMappingDefinition();
	
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
	private ELJavaXmlAttributeMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return JavaXmlAttributeMappingDefinition.instance().getKey();
	}
	
	public String getAnnotationName() {
		return JavaXmlAttributeMappingDefinition.instance().getKey();
	}
	
	public Iterable<String> getSupportingAnnotationNames() {
		return new CompositeIterable<String>(
				JavaXmlAttributeMappingDefinition.instance().getSupportingAnnotationNames(),
				new ArrayIterable<String>(SUPPORTING_ANNOTATION_NAMES));
	}
	
	public JaxbAttributeMapping buildMapping(JaxbPersistentAttribute persistentAttribute, JaxbFactory factory) {
		return JavaXmlAttributeMappingDefinition.instance().buildMapping(persistentAttribute, factory);
	}
	
	public boolean isDefault(JaxbPersistentAttribute persistentAttribute) {
		// test whether annotated with @XmlPath, and if so, if last segment starts with "@"
		// (presence of "@" elsewhere may be a node select clause)
		XmlPathAnnotation xmlPathAnnotation = 
				(XmlPathAnnotation) persistentAttribute.getJavaResourceAttribute().getAnnotation(0, ELJaxb.XML_PATH);
		if (xmlPathAnnotation == null) {
			return false;
		}
		String value = xmlPathAnnotation.getValue();
		if (value == null) {
			return false;
		}
		StringTokenizer tokenizer = new StringTokenizer(value, "/");
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (! tokenizer.hasMoreTokens()) {
				return token.startsWith("@");
			}
		}
		return false;
	}
}
