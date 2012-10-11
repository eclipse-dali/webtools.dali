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
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlIDREF.ValidatableReference;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttributeAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdAttributeUse;
import org.eclipse.jpt.jaxb.core.xsd.XsdFeature;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdSimpleTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlAttributeMapping
		extends AbstractJavaXmlNamedNodeMapping<XmlAttributeAnnotation>
		implements XmlAttributeMapping {
	
	protected final JaxbQName qName;
	
	protected Boolean specifiedRequired;
	
	
	public GenericJavaXmlAttributeMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.qName = buildQName();
		this.specifiedRequired = buildSpecifiedRequired();
	}
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.qName.synchronizeWithResourceModel();
		setSpecifiedRequired_(buildSpecifiedRequired());
	}
	
	@Override
	public void update() {
		super.update();
		this.qName.update();
	}
	
	public String getKey() {
		return MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return JAXB.XML_ATTRIBUTE;
	}
	
	
	// ***** schema component ref *****
	
	public JaxbQName getQName() {
		return this.qName;
	}
	
	protected JaxbQName buildQName() {
		return new XmlAttributeQName(this);
	}
	
	
	// ***** XmlAttribute.required *****

	public boolean isRequired() {
		return (this.specifiedRequired == null) ? isDefaultRequired() : this.specifiedRequired.booleanValue();
	}
	
	public Boolean getSpecifiedRequired() {
		return this.specifiedRequired;
	}
	
	public void setSpecifiedRequired(Boolean newSpecifiedRequired) {
		getOrCreateAnnotation().setRequired(newSpecifiedRequired);
		setSpecifiedRequired_(newSpecifiedRequired);
	}
	
	protected void setSpecifiedRequired_(Boolean newSpecifiedRequired) {
		Boolean oldRequired = this.specifiedRequired;
		this.specifiedRequired = newSpecifiedRequired;
		firePropertyChanged(SPECIFIED_REQUIRED_PROPERTY, oldRequired, newSpecifiedRequired);
	}
	
	protected Boolean buildSpecifiedRequired() {
		XmlAttributeAnnotation annotation = getAnnotation();
		return (annotation == null) ? null : annotation.getRequired();
	}
	
	public boolean isDefaultRequired() {
		return false;
	}
	
	
	// ***** XmlList *****
	
	@Override
	protected boolean calculateDefaultXmlList() {
		return getPersistentAttribute().isJavaResourceAttributeCollectionType();
	}
	
	
	// ***** XmlIDREF *****
	
	@Override
	protected GenericJavaXmlIDREF.Context buildXmlIDREFContext() {
		return new XmlIDREFContext();
	}
	
	
	// ***** misc *****
	
	public XsdFeature getXsdFeature() {
		XsdTypeDefinition type = getJaxbClassMapping().getXsdTypeDefinition();
		if (type != null) {
			XsdAttributeUse attributeUse = type.getAttribute(this.qName.getNamespace(), this.qName.getName());
			return (attributeUse == null) ? null : attributeUse.getAttributeDeclaration();
		}
		return null;		
	}
	
	@Override
	public XsdTypeDefinition getDataTypeXsdTypeDefinition() {
		XsdTypeDefinition xsdType = super.getDataTypeXsdTypeDefinition();
		if (xsdType != null 
				&& isXmlList() 
				&& xsdType.getKind() == XsdTypeDefinition.Kind.SIMPLE 
				&& ((XsdSimpleTypeDefinition) xsdType).getItemType() != null) {
			xsdType = ((XsdSimpleTypeDefinition) xsdType).getItemType();
		}
		return xsdType;
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		result = this.qName.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateQName(messages, reporter);
		validateSchemaType(messages, reporter);
	}
	
	protected void validateQName(List<IMessage> messages, IReporter reporter) {
		this.qName.validate(messages, reporter);
	}
	
	protected void validateSchemaType(List<IMessage> messages, IReporter reporter) {
		XsdFeature xsdFeature = getXsdFeature();
		if (xsdFeature == null) {
			return;
		}
		
		XsdTypeDefinition expectedSchemaType = null;
		if (this.xmlID != null) {
			expectedSchemaType = XsdUtil.getSchemaForSchema().getTypeDefinition("ID");
		}
		else if (this.xmlIDREF != null) {
			expectedSchemaType = XsdUtil.getSchemaForSchema().getTypeDefinition("IDREF");
		}
		else {
			expectedSchemaType = getDataTypeXsdTypeDefinition();
		}
		
		if (expectedSchemaType == null) {
			return;
		}
		
		boolean isItemType = isXmlList() && this.xmlSchemaType == null;
		if (! xsdFeature.typeIsValid(expectedSchemaType, isItemType)) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ATTRIBUTE__INVALID_SCHEMA_TYPE,
							new String[] { getValueTypeName(), xsdFeature.getName() },
							this,
							this.qName.getNameTextRange()));
		}
	}
	
	
	protected class XmlAttributeQName
			extends AbstractJavaQName {
		
		protected XmlAttributeQName(JaxbContextNode parent) {
			super(parent, new QNameAnnotationProxy());
		}
		
		
		@Override
		protected JaxbPackage getJaxbPackage() {
			return GenericJavaXmlAttributeMapping.this.getJaxbPackage();
		}
		
		@Override
		public String getReferencedComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_ATTRIBUTE_DESC;
		}
		
		@Override
		public String getDefaultName() {
			return GenericJavaXmlAttributeMapping.this.getPersistentAttribute().getJavaResourceAttribute().getName();
		}
		
		@Override
		public Iterable<String> getNameProposals() {
			XsdTypeDefinition xsdType = GenericJavaXmlAttributeMapping.this.getJaxbClassMapping().getXsdTypeDefinition();
			return (xsdType == null) ? EmptyIterable.<String>instance() : xsdType.getAttributeNameProposals(getNamespace());
		}
		
		@Override
		public String getDefaultNamespace() {
			JaxbPackage jaxbPackage = this.getJaxbPackage();
			return (jaxbPackage != null && jaxbPackage.getAttributeFormDefault() == XmlNsForm.QUALIFIED) ?
					GenericJavaXmlAttributeMapping.this.getJaxbClassMapping().getQName().getNamespace() : "";
		}
		
		@Override
		public Iterable<String> getNamespaceProposals() {
			XsdSchema schema = this.getXsdSchema();
			return (schema == null) ? EmptyIterable.<String>instance() : schema.getNamespaceProposals();
		}
		
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter) {
			XsdTypeDefinition type = getJaxbClassMapping().getXsdTypeDefinition();
			if (type != null) {
				if (type.getAttribute(getNamespace(), getName()) == null) {
					messages.add(getUnresolveSchemaComponentMessage());
				}
			}
		}
	}
	
	
	protected class QNameAnnotationProxy 
			extends AbstractJavaQName.AbstractQNameAnnotationProxy {
		
		@Override
		protected QNameAnnotation getAnnotation(boolean createIfNull) {
			if (createIfNull) {
				return GenericJavaXmlAttributeMapping.this.getOrCreateAnnotation();
			}
			else {
				return GenericJavaXmlAttributeMapping.this.getAnnotation();
			}
		}
	}
	
	
	protected class XmlIDREFContext
			extends AbstractJavaXmlNamedNodeMapping.XmlIDREFContext {
		
		public Iterable<ValidatableReference> getReferences() {
			
			return new SingleElementIterable<ValidatableReference>(
					
					new ValidatableReference() {
						
						public String getFullyQualifiedType() {
							return GenericJavaXmlAttributeMapping.this.getPersistentAttribute().getJavaResourceAttributeBaseTypeName();
						}
						
						public TextRange getTypeTextRange() {
							// 1) if we're getting here, XmlIDREF will not be null
							// 2) use the @XmlIDREF text range, since there is no specific place where the type is specified
							return GenericJavaXmlAttributeMapping.this.getXmlIDREF().getValidationTextRange();
						}
						
						public XsdFeature getXsdFeature() {
							return GenericJavaXmlAttributeMapping.this.getXsdFeature();
						}
						
						public TextRange getXsdFeatureTextRange() {
							return GenericJavaXmlAttributeMapping.this.getQName().getNameTextRange();
						}
					});
		}
		
		@Override
		public boolean isList() {
			return super.isList() 
				|| GenericJavaXmlAttributeMapping.this.getPersistentAttribute().isJavaResourceAttributeCollectionType();
		}
	}
}
