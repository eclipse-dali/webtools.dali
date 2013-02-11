/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumMapping;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumValueAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdSimpleTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaEnumConstant
		extends AbstractJavaContextNode
		implements JaxbEnumConstant {

	final protected JavaResourceEnumConstant resourceEnumConstant;
	
	protected String specifiedValue;

	public GenericJavaEnumConstant(JaxbEnumMapping parent, JavaResourceEnumConstant resourceEnumConstant) {
		super(parent);
		this.resourceEnumConstant = resourceEnumConstant;
		this.specifiedValue = this.getResourceEnumValue();
	}
	
	
	protected JaxbEnumMapping getEnumMapping() {
		return (JaxbEnumMapping) getParent();
	}
	
	public JavaResourceEnumConstant getResourceEnumConstant() {
		return this.resourceEnumConstant;
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedValue_(this.getResourceEnumValue());
	}


	// ********** xml enum value annotation **********

	protected XmlEnumValueAnnotation getXmlEnumValueAnnotation() {
		return (XmlEnumValueAnnotation) this.getResourceEnumConstant().getNonNullAnnotation(JAXB.XML_ENUM_VALUE);
	}


	// ********** name **********

	public String getName() {
		return this.resourceEnumConstant.getName();
	}

	// ********** value **********

	public String getValue() {
		return this.getSpecifiedValue() != null ? this.getSpecifiedValue() : this.getDefaultValue();
	}

	public String getDefaultValue() {
		return this.getName();
	}

	public String getSpecifiedValue() {
		return this.specifiedValue;
	}

	public void setSpecifiedValue(String value) {
		this.getXmlEnumValueAnnotation().setValue(value);
		this.setSpecifiedValue_(value);	
	}

	protected void setSpecifiedValue_(String value) {
		String old = this.specifiedValue;
		this.specifiedValue = value;
		this.firePropertyChanged(SPECIFIED_VALUE_PROPERTY, old, value);
	}

	protected String getResourceEnumValue() {
		return this.getXmlEnumValueAnnotation().getValue();
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		if (valueTouches(pos)) {
			XsdTypeDefinition<?> xsdType = getEnumMapping().getXsdTypeDefinition();
			if (xsdType != null && xsdType.getKind() == XsdTypeDefinition.Kind.SIMPLE) {
				XsdSimpleTypeDefinition xsdSimpleType = (XsdSimpleTypeDefinition) xsdType;
				return xsdSimpleType.getEnumValueProposals();
			}
		}
		
		return EmptyIterable.instance();
	}
	
	protected boolean valueTouches(int pos) {
		return getXmlEnumValueAnnotation().valueTouches(pos);
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		String value = getValue();
		XsdTypeDefinition<?> xsdType = getEnumMapping().getXsdTypeDefinition();
		
		if (xsdType == null || xsdType.getKind() != XsdTypeDefinition.Kind.SIMPLE) {
			return;
		}
		
		if (! ((XsdSimpleTypeDefinition) xsdType).getXSDComponent().isValidLiteral(value)) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JptJaxbCoreValidationMessages.XML_ENUM_VALUE__INVALID_LEXICAL_VALUE,
							new String[] { value, xsdType.getName() },
							this,
							getValueTextRange()));
		}
	}
	
	@Override
	public TextRange getValidationTextRange() {
		return getResourceEnumConstant().getTextRange();
	}
	
	protected TextRange getValueTextRange() {
		TextRange enumValueTextRange = getXmlEnumValueAnnotation().getValueTextRange();
		return enumValueTextRange != null ? enumValueTextRange : getValidationTextRange();
	}
}
