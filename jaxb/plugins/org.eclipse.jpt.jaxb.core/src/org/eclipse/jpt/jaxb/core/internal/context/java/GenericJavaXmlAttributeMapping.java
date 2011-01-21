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

import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttributeAnnotation;

public class GenericJavaXmlAttributeMapping
	extends GenericJavaContainmentMapping<XmlAttributeAnnotation>
	implements XmlAttributeMapping
{


	public GenericJavaXmlAttributeMapping(JaxbPersistentAttribute parent) {
		super(parent);
	}

	public String getKey() {
		return MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return XmlAttributeAnnotation.ANNOTATION_NAME;
	}
}
