/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlID;
import org.eclipse.jpt.jaxb.core.context.XmlNamedNodeMapping;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdFeature;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlID
	extends AbstractJavaContextNode
	implements XmlID
{

	protected final XmlIDAnnotation resourceXmlID;

	public GenericJavaXmlID(XmlNamedNodeMapping parent, XmlIDAnnotation resource) {
		super(parent);
		this.resourceXmlID = resource;
	}
	
	
	public XmlNamedNodeMapping getMapping() {
		return (XmlNamedNodeMapping) getParent();
	}
	
	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getMapping().getPersistentAttribute();
	}


	//************* validation ****************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateAttributeType(messages);
		
		XsdFeature xsdFeature = getMapping().getXsdFeature();
		if (xsdFeature == null) {
			return;
		}
		
		XsdTypeDefinition idrefType = XsdUtil.getSchemaForSchema().getTypeDefinition("ID");
		if (! xsdFeature.typeIsValid(idrefType, false)) {
			messages.add(
					DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.XML_ID__SCHEMA_TYPE_NOT_ID,
						new String [] { xsdFeature.getName() },
						this,
						getValidationTextRange()));
		}
	}
	
	protected void validateAttributeType(List<IMessage> messages) {
		if (! getPersistentAttribute().isJavaResourceAttributeTypeSubTypeOf(String.class.getName())) {
			messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.XML_ID__ATTRIBUTE_TYPE_NOT_STRING,
					this,
					getValidationTextRange()));
		}
	}

	@Override
	public TextRange getValidationTextRange() {
		return this.resourceXmlID.getTextRange();
	}
}
