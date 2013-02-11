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

import java.util.List;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlValueMapping;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlValueAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlValueMapping
		extends AbstractJavaXmlNodeMapping<XmlValueAnnotation>
		implements XmlValueMapping {
	
	public GenericJavaXmlValueMapping(JaxbPersistentAttribute parent) {
		super(parent);
	}
	
	
	public String getKey() {
		return MappingKeys.XML_VALUE_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return JAXB.XML_VALUE;
	}
	
	
	// ***** XmlList *****
	
	@Override
	protected boolean calculateDefaultXmlList() {
		return getPersistentAttribute().isJavaResourceAttributeCollectionType();
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateSchemaType(messages, reporter);
	}
	
	protected void validateSchemaType(List<IMessage> messages, IReporter reporter) {
		XsdTypeDefinition xsdClassType = getClassMapping().getXsdTypeDefinition();
		
		if (xsdClassType == null) {
			return;
		}
		
		if (! xsdClassType.hasTextContent()) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JptJaxbCoreValidationMessages.XML_VALUE__NO_TEXT_CONTENT,
							this,
							getValidationTextRange()));
			return;
		}
		
		XsdTypeDefinition xsdType = xsdClassType.getBaseType();
		XsdTypeDefinition expectedSchemaType = getDataTypeXsdTypeDefinition();
		
		if (xsdType == null || expectedSchemaType == null) {
			return;
		}
		
		if (! xsdType.typeIsValid(expectedSchemaType, isXmlList())) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JptJaxbCoreValidationMessages.XML_VALUE__INVALID_SCHEMA_TYPE,
							new String[] { getValueTypeName() },
							this,
							getValidationTextRange()));
		}
	}
}
