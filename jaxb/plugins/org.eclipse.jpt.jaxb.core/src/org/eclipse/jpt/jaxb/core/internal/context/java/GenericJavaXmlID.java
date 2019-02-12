/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.XmlID;
import org.eclipse.jpt.jaxb.core.context.XmlNamedNodeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
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
	
	protected JavaPersistentAttribute getPersistentAttribute() {
		return (JavaPersistentAttribute) getMapping().getPersistentAttribute();
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
					this.buildValidationMessage(
						getValidationTextRange(),
						JptJaxbCoreValidationMessages.XML_ID__SCHEMA_TYPE_NOT_ID,
						xsdFeature.getName()));
		}
	}
	
	protected void validateAttributeType(List<IMessage> messages) {
		if (! getPersistentAttribute().isJavaResourceAttributeTypeSubTypeOf(String.class.getName())) {
			messages.add(
				this.buildValidationMessage(
					getValidationTextRange(),
					JptJaxbCoreValidationMessages.XML_ID__ATTRIBUTE_TYPE_NOT_STRING
				));
		}
	}

	@Override
	public TextRange getValidationTextRange() {
		return this.resourceXmlID.getTextRange();
	}
}
