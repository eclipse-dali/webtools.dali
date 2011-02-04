/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlListAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlValueAnnotation;
import org.eclipse.jpt.utility.internal.iterables.ArrayListIterable;


public class JavaXmlValueMappingDefinition
	extends AbstractJavaAttributeMappingDefinition
{
	// singleton
	private static final JavaXmlValueMappingDefinition INSTANCE = 
		new JavaXmlValueMappingDefinition();

	private static final String[] SUPPORTING_ANNOTATION_NAMES = 
		{XmlListAnnotation.ANNOTATION_NAME,
		XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME};

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaXmlValueMappingDefinition() {
		super();
	}


	public String getKey() {
		return MappingKeys.XML_VALUE_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return XmlValueAnnotation.ANNOTATION_NAME;
	}

	public Iterable<String> getSupportingAnnotationNames() {
		return new ArrayListIterable<String>(SUPPORTING_ANNOTATION_NAMES);
	}

	public JaxbAttributeMapping buildMapping(JaxbPersistentAttribute parent, JaxbFactory factory) {
		return factory.buildJavaXmlValueMapping(parent);
	}
}
