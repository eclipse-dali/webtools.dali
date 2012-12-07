/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnumMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdSimpleTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaEnumMapping
		extends AbstractJavaTypeMapping
		implements JavaEnumMapping {
	
	protected String specifiedXmlEnumValue;

	protected final EnumConstantContainer enumConstantContainer;

	
	public GenericJavaEnumMapping(JavaEnum parent) {
		super(parent);
		this.enumConstantContainer = new EnumConstantContainer();
		
		initXmlEnumValue();
		initEnumConstants();
	}
	
	
	@Override
	public JavaResourceEnum getJavaResourceType() {
		return (JavaResourceEnum) super.getJavaResourceType();
	}
	
	
	// ********** sync/update **********
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncXmlEnumValue();
		syncEnumConstants();
	}
	
	@Override
	public void update() {
		super.update();
		updateEnumConstants();
	}
	

	// ***** XmlEnum.value *****
	
	public String getXmlEnumValue() {
		return (this.specifiedXmlEnumValue != null) ? this.specifiedXmlEnumValue : DEFAULT_XML_ENUM_VALUE;
	}
	
	public String getSpecifiedXmlEnumValue() {
		return this.specifiedXmlEnumValue;
	}
	
	public void setSpecifiedXmlEnumValue(String xmlEnumValue) {
		getXmlEnumAnnotation().setValue(xmlEnumValue);
		setSpecifiedXmlEnumValue_(xmlEnumValue);	
	}
	
	protected void setSpecifiedXmlEnumValue_(String xmlEnumValue) {
		String old = this.specifiedXmlEnumValue;
		this.specifiedXmlEnumValue = xmlEnumValue;
		firePropertyChanged(SPECIFIED_XML_ENUM_VALUE_PROPERTY, old, xmlEnumValue);
	}
	
	public String getFullyQualifiedXmlEnumValue() {
		return (this.specifiedXmlEnumValue != null) ? 
				getXmlEnumAnnotation().getFullyQualifiedValueClassName()
				: DEFAULT_XML_ENUM_VALUE;
	}
	
	protected XmlEnumAnnotation getXmlEnumAnnotation() {
		return (XmlEnumAnnotation) getJavaResourceType().getNonNullAnnotation(JAXB.XML_ENUM);
	}
	
	protected String getResourceXmlEnumValue() {
		return getXmlEnumAnnotation().getValue();
	}
	
	protected void initXmlEnumValue() {
		this.specifiedXmlEnumValue = getResourceXmlEnumValue();
	}
	
	protected void syncXmlEnumValue() {
		setSpecifiedXmlEnumValue_(getResourceXmlEnumValue());
	}
	
	
	// ***** enum constants *****
	
	public Iterable<JaxbEnumConstant> getEnumConstants() {
		return this.enumConstantContainer.getContextElements();
	}
	
	public int getEnumConstantsSize() {
		return this.enumConstantContainer.getContextElementsSize();
	}
	
	protected void initEnumConstants() {
		this.enumConstantContainer.initialize();
	}
	
	protected void syncEnumConstants() {
		this.enumConstantContainer.synchronizeWithResourceModel();
	}
	
	protected void updateEnumConstants() {
		this.enumConstantContainer.update();
	}
	
	protected Iterable<JavaResourceEnumConstant> getResourceEnumConstants() {
		return getJavaResourceType().getEnumConstants();
	}
	
	private JaxbEnumConstant buildEnumConstant(JavaResourceEnumConstant resourceEnumConstant) {
		return getFactory().buildJavaEnumConstant(this, resourceEnumConstant);
	}
	
	
	// ***** misc *****
	
	@Override
	protected Iterable<String> getNonTransientReferencedXmlTypeNames() {
		if (this.specifiedXmlEnumValue != null) {
			return new CompositeIterable<String>(
					super.getNonTransientReferencedXmlTypeNames(),
					new SingleElementIterable(getFullyQualifiedXmlEnumValue()));
		}
		return super.getNonTransientReferencedXmlTypeNames();
	}
	
	public XsdSimpleTypeDefinition getValueXsdTypeDefinition() {
		XsdTypeDefinition xsdType = getValueXsdTypeDefinition_();
		if (xsdType == null || xsdType.getKind() != XsdTypeDefinition.Kind.SIMPLE) {
			return null;
		}
		return (XsdSimpleTypeDefinition) xsdType;
	}
	
	protected XsdTypeDefinition getValueXsdTypeDefinition_() {
		String fqXmlEnumValue = getFullyQualifiedXmlEnumValue();
		
		JavaType jaxbType = getContextRoot().getJavaType(fqXmlEnumValue);
		if (jaxbType != null) {
			JaxbTypeMapping typeMapping = jaxbType.getMapping();
			if (typeMapping != null) {
				return typeMapping.getXsdTypeDefinition();
			}
		}
		else {
			String typeMapping = getJaxbProject().getPlatform().getDefinition().getSchemaTypeMapping(fqXmlEnumValue);
			if (typeMapping != null) {
				XsdSchema xsdSchema = XsdUtil.getSchemaForSchema();
				return xsdSchema.getTypeDefinition(typeMapping);
			}
		}
		
		return null;
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		for (JaxbEnumConstant constant : getEnumConstants()) {
			result = constant.getCompletionProposals(pos);
			if (! IterableTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateXmlType(messages, reporter);
		validateXmlEnum(messages, reporter);
		
		for (JaxbEnumConstant constant : getEnumConstants()) {
			constant.validate(messages, reporter);
		}
	}
	
	protected void validateXmlType(List<IMessage> messages, IReporter reporter) {
		XmlTypeAnnotation annotation = getXmlTypeAnnotation();
		
		if (annotation.getFactoryClass() != null) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.NORMAL_SEVERITY,
							JaxbValidationMessages.XML_TYPE__FACTORY_CLASS_IGNORED_FOR_ENUM,
							this,
							annotation.getFactoryClassTextRange()));
		}
		
		if (annotation.getFactoryMethod() != null) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.NORMAL_SEVERITY,
							JaxbValidationMessages.XML_TYPE__FACTORY_METHOD_IGNORED_FOR_ENUM,
							this,
							annotation.getFactoryMethodTextRange()));
		}
		
		if (! IterableTools.isEmpty(annotation.getPropOrder())) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.NORMAL_SEVERITY,
							JaxbValidationMessages.XML_TYPE__PROP_ORDER_IGNORED_FOR_ENUM,
							this,
							annotation.getPropOrderTextRange()));
		}
	}
	
	protected void validateXmlEnum(List<IMessage> messages, IReporter reporter) {
		XsdSchema xsdSchema = getJaxbPackage().getXsdSchema();
		XsdTypeDefinition xsdType = getValueXsdTypeDefinition_();
		
		if ((xsdSchema != null && xsdType == null)
				|| (xsdType != null && xsdType.getKind() != XsdTypeDefinition.Kind.SIMPLE)) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ENUM__NON_SIMPLE_SCHEMA_TYPE,
							new String[] { getFullyQualifiedXmlEnumValue() },
							this,
							getXmlEnumValueTextRange()));
		}
	}
	
	protected TextRange getXmlEnumValueTextRange() {
		return getXmlEnumAnnotation().getValueTextRange();
	}
	
	
	/**
	 * enum constant container adapter
	 */
	protected class EnumConstantContainer
			extends ContextCollectionContainer<JaxbEnumConstant, JavaResourceEnumConstant> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return ENUM_CONSTANTS_COLLECTION;
		}
		
		@Override
		protected JaxbEnumConstant buildContextElement(JavaResourceEnumConstant resourceElement) {
			return GenericJavaEnumMapping.this.buildEnumConstant(resourceElement);
		}
		
		@Override
		protected Iterable<JavaResourceEnumConstant> getResourceElements() {
			return GenericJavaEnumMapping.this.getResourceEnumConstants();
		}
		
		@Override
		protected JavaResourceEnumConstant getResourceElement(JaxbEnumConstant contextElement) {
			return contextElement.getResourceEnumConstant();
		}
	}
}
