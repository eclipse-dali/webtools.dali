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
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAdaptableMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElement;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaXmlElement
		extends AbstractJavaContextNode
		implements XmlElement {
	
	protected final Context context;
	
	protected final JaxbQName qName;
	
	protected Boolean specifiedNillable;
	
	protected boolean defaultNillable;
	
	protected Boolean specifiedRequired;
	
	protected String defaultValue;
	
	protected String specifiedType;
	
	protected String defaultType;
	
	
	public GenericJavaXmlElement(JaxbContextNode parent, Context context) {
		super(parent);
		this.context = context;
		this.qName = buildQName();
		this.specifiedNillable = buildSpecifiedNillable();
		this.defaultNillable = buildDefaultNillable();
		this.specifiedRequired = buildSpecifiedRequired();
		this.defaultValue = buildDefaultValue();
		this.specifiedType = buildSpecifiedType();
		this.defaultType = buildDefaultType();
	}
	
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.qName.synchronizeWithResourceModel();
		setSpecifiedNillable_(buildSpecifiedNillable());
		setDefaultNillable_(buildDefaultNillable());
		setSpecifiedRequired_(buildSpecifiedRequired());
		setDefaultValue_(buildDefaultValue());
		setSpecifiedType_(buildSpecifiedType());
		setDefaultType_(buildDefaultType());
	}
	
	@Override
	public void update() {
		super.update();
		this.qName.update();
	}
	
	protected Context getContext() {
		return this.context;
	}
	
	protected JaxbAttributeMapping getAttributeMapping() {
		return getContext().getAttributeMapping();
	}
	
	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getContext().getAttributeMapping().getPersistentAttribute();
	}
	
	protected JaxbClassMapping getClassMapping() {
		return getPersistentAttribute().getClassMapping();
	}
	
	protected JaxbPackage getJaxbPackage() {
		return getClassMapping().getJaxbPackage();
	}
	
	public XmlElementAnnotation getAnnotation(boolean createIfNull) {
		return this.context.getAnnotation(createIfNull);
	}
	
	
	// ***** schema component ref *****
	
	public JaxbQName getQName() {
		return this.qName;
	}
	
	protected JaxbQName buildQName() {
		return new XmlElementQName(this);
	}
	
	
	// ***** XmlElement.nillable *****
	
	public boolean isNillable() {
		return (this.specifiedNillable == null) ? isDefaultNillable() : getSpecifiedNillable().booleanValue();
	}
	
	public Boolean getSpecifiedNillable() {
		return this.specifiedNillable;
	}
	
	public void setSpecifiedNillable(Boolean newSpecifiedNillable) {
		getAnnotation(true).setNillable(newSpecifiedNillable);
		setSpecifiedNillable_(newSpecifiedNillable);
	}
	
	protected void setSpecifiedNillable_(Boolean newSpecifiedNillable) {
		Boolean oldNillable = this.specifiedNillable;
		this.specifiedNillable = newSpecifiedNillable;
		firePropertyChanged(SPECIFIED_NILLABLE_PROPERTY, oldNillable, newSpecifiedNillable);
	}
	
	protected Boolean buildSpecifiedNillable() {
		XmlElementAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? null : annotation.getNillable();
	}
	
	public boolean isDefaultNillable() {
		return this.defaultNillable;
	}
	
	protected void setDefaultNillable_(boolean newNillable) {
		boolean oldNillable = this.defaultNillable;
		this.defaultNillable = newNillable;
		firePropertyChanged(DEFAULT_NILLABLE_PROPERTY, oldNillable, newNillable);
	}
	
	protected boolean buildDefaultNillable() {
		XmlElementAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? getPersistentAttribute().isJavaResourceAttributeCollectionType() : false;
	}
	
	
	// ***** XmlElement.required *****

	public boolean isRequired() {
		return (this.specifiedRequired == null) ? isDefaultRequired() : this.specifiedRequired.booleanValue();
	}
	
	public Boolean getSpecifiedRequired() {
		return this.specifiedRequired;
	}
	
	public void setSpecifiedRequired(Boolean newSpecifiedRequired) {
		getAnnotation(true).setRequired(newSpecifiedRequired);
		setSpecifiedRequired_(newSpecifiedRequired);
	}
	
	protected void setSpecifiedRequired_(Boolean newSpecifiedRequired) {
		Boolean oldRequired = this.specifiedRequired;
		this.specifiedRequired = newSpecifiedRequired;
		firePropertyChanged(SPECIFIED_REQUIRED_PROPERTY, oldRequired, newSpecifiedRequired);
	}
	
	protected Boolean buildSpecifiedRequired() {
		XmlElementAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? null : annotation.getRequired();
	}
	
	public boolean isDefaultRequired() {
		return false;
	}
	
	
	// ***** XmlElement.defaultValue *****
	
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		getAnnotation(true).setDefaultValue(defaultValue);
		setDefaultValue_(defaultValue);
	}
	
	protected void setDefaultValue_(String defaultValue) {
		String oldDefaultValue = this.defaultValue;
		this.defaultValue = defaultValue;
		firePropertyChanged(DEFAULT_VALUE_PROPERTY, oldDefaultValue, defaultValue);		
	}
	
	protected String buildDefaultValue() {
		XmlElementAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? null : annotation.getDefaultValue();
	}
	
	
	// ***** XmlElement.type *****
	
	public String getType() {
		return (this.specifiedType == null) ? getDefaultType() : this.specifiedType;
	}
	
	public String getSpecifiedType() {
		return this.specifiedType;
	}
	
	public void setSpecifiedType(String newSpecifiedType) {
		getAnnotation(true).setType(newSpecifiedType);
		setSpecifiedType_(newSpecifiedType);
	}
	
	protected void setSpecifiedType_(String newSpecifiedType) {
		String oldType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldType, newSpecifiedType);
	}
	
	protected String buildSpecifiedType() {
		XmlElementAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? null : annotation.getType();
	}
	
	public String getDefaultType() {
		return this.defaultType;
	}
	
	protected void setDefaultType_(String newType) {
		String oldType = this.defaultType;
		this.defaultType = newType;
		firePropertyChanged(DEFAULT_TYPE_PROPERTY, oldType, newType);
	}
	
	protected String buildDefaultType() {
		return this.context.getDefaultType();
	}
	
	public String getFullyQualifiedType() {
		return (this.specifiedType == null) ? getDefaultType() : getAnnotation(false).getFullyQualifiedTypeName();
	}
	
	
	// ***** misc *****
	
	public Iterable<String> getReferencedXmlTypeNames() {
		// only return the specified type - the default type should already be included
		return (this.specifiedType == null) ? 
				EmptyIterable.<String>instance() 
				: new SingleElementIterable(getFullyQualifiedType());
	}
	
	public XsdElementDeclaration getXsdElement() {
		XsdTypeDefinition xsdType = getClassMapping().getXsdTypeDefinition();
		return (xsdType == null) ? null : xsdType.getElement(this.qName.getNamespace(), this.qName.getName());
	}
	
	/**
	 * Return the expected schema type associated with the data type
	 */
	public XsdTypeDefinition getTypeXsdTypeDefinition() {
		String type = getFullyQualifiedType();
		if (StringTools.isBlank(type) || XmlElement.DEFAULT_TYPE_PROPERTY.equals(type)) {
			return null;
		}
		
		JaxbPackage pkg = getJaxbPackage();
		JaxbPackageInfo pkgInfo = (pkg == null) ? null : pkg.getPackageInfo();
		if (pkgInfo != null) {
			for (XmlSchemaType schemaType : pkgInfo.getXmlSchemaTypes()) {
				if (type.equals(schemaType.getFullyQualifiedType())) {
					return schemaType.getXsdTypeDefinition();
				}
			}
		}
		
		JaxbTypeMapping jaxbTypeMapping = getContextRoot().getTypeMapping(type);
		if (jaxbTypeMapping != null) {
			return jaxbTypeMapping.getXsdTypeDefinition();
		}
		
		String builtInType = getJaxbProject().getPlatform().getDefinition().getSchemaTypeMapping(type);
		if (builtInType != null) {
			return XsdUtil.getSchemaForSchema().getTypeDefinition(builtInType);
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
		
		result = this.qName.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		XmlElementAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? getParent().getValidationTextRange() : annotation.getTextRange();
	}
	
	public TextRange getTypeTextRange() {
		XmlElementAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? getParent().getValidationTextRange() : annotation.getTypeTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		validateQName(messages, reporter);
		validateType(messages, reporter);
		validateSchemaType(messages, reporter);
	}
	
	protected void validateQName(List<IMessage> messages, IReporter reporter) {
		this.qName.validate(messages, reporter);
	}
	
	protected void validateType(List<IMessage> messages, IReporter reporter) {
		String fqType = getFullyQualifiedType();
		if (StringTools.isBlank(fqType)) {
			messages.add(
					this.buildErrorValidationMessage(
							getTypeTextRange(),
							JptJaxbCoreValidationMessages.XML_ELEMENT__UNSPECIFIED_TYPE
						));
		}
		else {
			//TODO - the following check can be removed once proper binary support is added to JAXB projects
			//verify that a binary XmlAdapter is not being used before continuing with validation (to avoid invalid errors) - see bug 394063
			XmlAdaptableMapping mapping = (XmlAdaptableMapping) getAttributeMapping();
			if (mapping.getXmlJavaTypeAdapter() != null && mapping.getXmlAdapter() == null) { // XmlAdapter is either binary or invalid (which results in other validation errors)
				return;
			}
			if (! StringTools.isBlank(this.specifiedType)
					// verify that type actually exists before validating
					&& JDTTools.findType(getJaxbProject().getJavaProject(), fqType) != null) {
				String attributeBaseType = getAttributeMapping().getValueTypeName();
				if (! JDTTools.typeIsSubType(getJaxbProject().getJavaProject(), fqType, attributeBaseType)) {
					messages.add(
							this.buildErrorValidationMessage(
									getTypeTextRange(),
									JptJaxbCoreValidationMessages.XML_ELEMENT__ILLEGAL_TYPE,
									attributeBaseType));
				}
			}
		}
	}
	
	protected void validateSchemaType(List<IMessage> messages, IReporter reporter) {
		XsdElementDeclaration xsdElement = getXsdElement();
		if (xsdElement == null) {
			return;
		}
		
		XsdTypeDefinition expectedSchemaType = null;
		String typeName = this.context.getAttributeMapping().getDataTypeName();
		if (! XmlElement.DEFAULT_TYPE_PROPERTY.equals(getFullyQualifiedType())) {
			typeName = getFullyQualifiedType();
		}
		
		if (this.context.hasXmlID()) {
			expectedSchemaType = XsdUtil.getSchemaForSchema().getTypeDefinition("ID");
		}
		else if (this.context.hasXmlIDREF()) {
			expectedSchemaType = XsdUtil.getSchemaForSchema().getTypeDefinition("IDREF");
		}
		else if (this.context.hasXmlSchemaType()) {
			expectedSchemaType = this.context.getXmlSchemaType().getXsdTypeDefinition();
		}
		else if (! XmlElement.DEFAULT_TYPE_PROPERTY.equals(getFullyQualifiedType())) {
			expectedSchemaType = getTypeXsdTypeDefinition();
		}
		else {
			expectedSchemaType = this.context.getAttributeMapping().getDataTypeXsdTypeDefinition();
		}
		
		if (expectedSchemaType == null) {
			return;
		}
		
		if (! xsdElement.typeIsValid(expectedSchemaType, this.context.hasXmlList())) {
			messages.add(
					this.buildErrorValidationMessage(
							this.qName.getNameTextRange(),
							JptJaxbCoreValidationMessages.XML_ELEMENT__INVALID_SCHEMA_TYPE,
							typeName,
							xsdElement.getName()));
		}
	}
	
	
	protected class XmlElementQName
			extends AbstractJavaElementQName {
		
		protected XmlElementQName(JaxbContextNode parent) {
			super(parent, new QNameAnnotationProxy());
		}
		
		
		@Override
		protected JaxbPersistentAttribute getPersistentAttribute() {
			return GenericJavaXmlElement.this.getPersistentAttribute();
		}
		
		@Override
		protected XmlElementWrapper getElementWrapper() {
			return GenericJavaXmlElement.this.context.getElementWrapper();
		}	
	}
	
	
	protected class QNameAnnotationProxy 
			extends AbstractQNameAnnotationProxy {
		
		@Override
		protected QNameAnnotation getAnnotation(boolean createIfNull) {
			return GenericJavaXmlElement.this.getAnnotation(createIfNull);
		}
	}
	
	
	public interface Context {
		
		JaxbAttributeMapping getAttributeMapping();
		
		XmlElementAnnotation getAnnotation(boolean createIfNull);
		
		String getDefaultType();
		
		XmlElementWrapper getElementWrapper();
		
		boolean hasXmlID();
		
		boolean hasXmlIDREF();
		
		boolean hasXmlList();
		
		boolean hasXmlSchemaType();
		
		XmlSchemaType getXmlSchemaType();
	}
}
