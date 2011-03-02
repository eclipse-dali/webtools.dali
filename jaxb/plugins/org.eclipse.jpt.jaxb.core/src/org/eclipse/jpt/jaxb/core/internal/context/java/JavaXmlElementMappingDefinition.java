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

import java.util.Collection;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayListIterable;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttachmentRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDREFAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlInlineBinaryDataAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlListAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMimeTypeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;


public class JavaXmlElementMappingDefinition
	extends AbstractJavaAttributeMappingDefinition
	implements DefaultJavaAttributeMappingDefinition
{
	// singleton
	private static final JavaXmlElementMappingDefinition INSTANCE = 
		new JavaXmlElementMappingDefinition();

	private static final String[] SUPPORTING_ANNOTATION_NAMES = 
	{XmlIDAnnotation.ANNOTATION_NAME,
	XmlIDREFAnnotation.ANNOTATION_NAME,
	XmlListAnnotation.ANNOTATION_NAME,
	XmlSchemaTypeAnnotation.ANNOTATION_NAME,
	XmlAttachmentRefAnnotation.ANNOTATION_NAME,
	XmlMimeTypeAnnotation.ANNOTATION_NAME,
	XmlInlineBinaryDataAnnotation.ANNOTATION_NAME,
	XmlElementWrapperAnnotation.ANNOTATION_NAME,
	XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME};

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
		return XmlElementAnnotation.ANNOTATION_NAME;
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
	 * </ul>
	 */
	public boolean isDefault(JaxbPersistentAttribute persistentAttribute) {
		JavaResourceAttribute resourceAttribute = persistentAttribute.getJavaResourceAttribute();
		if (resourceAttribute.typeIsSubTypeOf(Collection.class.getName())) {
			return resourceAttribute.getAnnotation(XmlListAnnotation.ANNOTATION_NAME) != null;
		}
		return true;
	}
}
