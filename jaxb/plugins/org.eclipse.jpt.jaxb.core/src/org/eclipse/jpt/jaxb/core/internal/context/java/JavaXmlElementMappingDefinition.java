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

import org.eclipse.jpt.common.utility.internal.iterables.ArrayListIterable;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;


public class JavaXmlElementMappingDefinition
		extends AbstractJavaAttributeMappingDefinition
		implements DefaultJavaAttributeMappingDefinition {
	
	// singleton
	private static final JavaXmlElementMappingDefinition INSTANCE = 
			new JavaXmlElementMappingDefinition();
	
	private static final String[] SUPPORTING_ANNOTATION_NAMES = 
			{
				JAXB.XML_ID,
				JAXB.XML_IDREF,
				JAXB.XML_LIST,
				JAXB.XML_SCHEMA_TYPE,
				JAXB.XML_ATTACHMENT_REF,
				JAXB.XML_MIME_TYPE,
				JAXB.XML_INLINE_BINARY_DATA,
				JAXB.XML_ELEMENT_WRAPPER,
				JAXB.XML_JAVA_TYPE_ADAPTER };
	
	
	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private JavaXmlElementMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return JAXB.XML_ELEMENT;
	}
	
	public Iterable<String> getSupportingAnnotationNames() {
		return new ArrayListIterable<String>(SUPPORTING_ANNOTATION_NAMES);
	}
	
	public JaxbAttributeMapping buildMapping(JaxbPersistentAttribute parent, JaxbFactory factory) {
		return factory.buildJavaXmlElementMapping(parent);
	}
	
	/**
	 * From the JAXB spec section 8.12.5.1 Default Mapping:
	 * <p>
	 * A single valued property or field must be mapped by with the following default mapping annotation:<ul>
	 * <li> @XmlElement
	 * </ul>
	 * <p>
	 * A property or field with a collection type must be mapped by with the following default mapping annotation:<ul>
	 * <li> if the property or field is annotated with @XmlList, then the default mapping annotation is:<ul>
	 * <li> @XmlElement
	 * </ul>
	 * <li> otherwise the default mapping annotation is:<ul>
	 * <li> @XmlElements({ @XmlElement(nillable=true)})  
	 * 			(NB:  this actually means the same as
	 *      		@XmlElement(nillable=true)
	 *      	)
	 * </ul>
	 */
	public boolean isDefault(JaxbPersistentAttribute persistentAttribute) {
		return true;
	}
}
