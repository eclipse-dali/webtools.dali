/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;


public class JavaXmlAttributeMappingDefinition
		extends AbstractJavaAttributeMappingDefinition {
	
	// singleton
	private static final JavaXmlAttributeMappingDefinition INSTANCE = 
		new JavaXmlAttributeMappingDefinition();


	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}

	private static final String[] SUPPORTING_ANNOTATION_NAMES = {
			JAXB.XML_ATTACHMENT_REF,
			JAXB.XML_ID,
			JAXB.XML_IDREF,
			JAXB.XML_INLINE_BINARY_DATA,
			JAXB.XML_JAVA_TYPE_ADAPTER,
			JAXB.XML_LIST,
			JAXB.XML_MIME_TYPE,
			JAXB.XML_SCHEMA_TYPE };

	/**
	 * Enforce singleton usage
	 */
	protected JavaXmlAttributeMappingDefinition() {
		super();
	}


	public String getKey() {
		return MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return JAXB.XML_ATTRIBUTE;
	}

	public Iterable<String> getSupportingAnnotationNames() {
		return IterableTools.listIterable(SUPPORTING_ANNOTATION_NAMES);
	}

	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JaxbFactory factory) {
		return factory.buildJavaXmlAttributeMapping(parent);
	}
}
