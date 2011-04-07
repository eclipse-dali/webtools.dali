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

import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttributeAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdComponent;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;

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
	
	@Override
	public String getDefaultNamespace() {
		return (getJaxbPackage().getAttributeFormDefault() == XmlNsForm.QUALIFIED) ?
				getPersistentClass().getNamespace()
				: "";
	}
	
	@Override
	public XsdComponent getXsdComponent() {
		XsdTypeDefinition xsdType = getPersistentClass().getXsdTypeDefinition();
		return (xsdType == null) ? null : xsdType.getAttribute(getNamespace(), getName());
	}
	
	
	@Override
	protected Iterable<String> getNameProposals(XsdTypeDefinition type, String namespace, Filter<String> filter) {
		return type.getAttributeNameProposals(namespace, filter);
	}
	
	@Override
	protected String getMissingNameMessage() {
		return JaxbValidationMessages.XML_ATTRIBUTE__MISSING_NAME;
	}
	
	@Override
	protected String getUnresolvedComponentMessage() {
		return JaxbValidationMessages.XML_ATTRIBUTE__UNRESOLVED_ATTRIBUTE;
	}
}
