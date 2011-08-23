/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.XmlElement;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
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
	
	
	public GenericJavaXmlElement(JavaContextNode parent, Context context) {
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
	
	@Override
	public JavaContextNode getParent() {
		return (JavaContextNode) super.getParent();
	}
	
	protected Context getContext() {
		return this.context;
	}
	
	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getContext().getAttributeMapping().getPersistentAttribute();
	}
	
	protected JaxbPersistentClass getPersistentClass() {
		return getPersistentAttribute().getPersistentClass();
	}
	
	protected JaxbPackage getJaxbPackage() {
		return getPersistentClass().getJaxbPackage();
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
	
	public Iterable<String> getDirectlyReferencedTypeNames() {
		// only return the specified type - the default type should already be included
		return (this.specifiedType == null) ? EmptyIterable.<String>instance() : new SingleElementIterable(getFullyQualifiedType());
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		result = this.qName.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		XmlElementAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? getParent().getValidationTextRange(astRoot) : annotation.getTextRange(astRoot);
	}
	
	public TextRange getTypeTextRange(CompilationUnit astRoot) {
		XmlElementAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? getParent().getValidationTextRange(astRoot) : annotation.getTypeTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		validateType(messages, reporter, astRoot);
		
		if (StringTools.stringIsEmpty(this.qName.getName())) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ELEMENT__UNSPECIFIED_ELEMENT_NAME,
							this,
							this.qName.getNameTextRange(astRoot)));
		}
	}
	
	protected void validateType(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		String fqType = getFullyQualifiedType();
		if (StringTools.stringIsEmpty(fqType)) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ELEMENT__UNSPECIFIED_TYPE,
							this,
							getTypeTextRange(astRoot)));
		}
		else if (! StringTools.stringIsEmpty(this.specifiedType)
				// verify that type actually exists before validating
				&& JDTTools.findType(getJaxbProject().getJavaProject(), fqType) != null) {
			String attributeBaseType = getPersistentAttribute().getJavaResourceAttributeBaseTypeName();
			if (! JDTTools.typeIsSubType(getJaxbProject().getJavaProject(), fqType, attributeBaseType)) {
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.XML_ELEMENT__ILLEGAL_TYPE,
								new String[] { attributeBaseType },
								this,
								getTypeTextRange(astRoot)));
								
			}
		}
	}
	
	
	protected class XmlElementQName
			extends AbstractJavaQName {
		
		protected XmlElementQName(JavaContextNode parent) {
			super(parent);
		}
		
		@Override
		public String getReferencedComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_ELEMENT_DESC;
		}
		
		@Override
		protected QNameAnnotation getAnnotation(boolean createIfNull) {
			return GenericJavaXmlElement.this.getAnnotation(createIfNull);
		}
		
		@Override
		public String getDefaultName() {
			return GenericJavaXmlElement.this.getPersistentAttribute().getJavaResourceAttribute().getName();
		}
		
		@Override
		public Iterable<String> getNameProposals(Filter<String> filter) {
			XsdTypeDefinition xsdType = GenericJavaXmlElement.this.getPersistentClass().getXsdTypeDefinition();
			if (xsdType == null) {
				return EmptyIterable.instance();
			}
			
			XmlElementWrapper elementWrapper = GenericJavaXmlElement.this.getContext().getElementWrapper();
			
			if (elementWrapper == null) {
				return xsdType.getElementNameProposals(getNamespace(), filter);
			}
			else {
				XsdElementDeclaration xsdWrapperElement = elementWrapper.getXsdElementDeclaration();
				if (xsdWrapperElement != null) {
					return xsdWrapperElement.getElementNameProposals(getNamespace(), filter);
				}
			}
			
			return EmptyIterable.instance();
		}
		
		@Override
		public String getDefaultNamespace() {
			return (GenericJavaXmlElement.this.getJaxbPackage().getElementFormDefault() == XmlNsForm.QUALIFIED) ?
					GenericJavaXmlElement.this.getPersistentClass().getQName().getNamespace() : "";
		}
		
		@Override
		public Iterable<String> getNamespaceProposals(Filter<String> filter) {
			XsdSchema schema = GenericJavaXmlElement.this.getJaxbPackage().getXsdSchema();
			return (schema == null) ? EmptyIterable.<String>instance() : schema.getNamespaceProposals(filter);
		}
		
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			XsdTypeDefinition xsdType = GenericJavaXmlElement.this.getPersistentClass().getXsdTypeDefinition();
			if (xsdType == null) {
				return;
			}
			
			XsdElementDeclaration resolvedXsdElement = null;
			
			XmlElementWrapper elementWrapper = GenericJavaXmlElement.this.getContext().getElementWrapper();
			
			if (elementWrapper == null) {
				resolvedXsdElement = xsdType.getElement(getNamespace(), getName());
			}
			else {
				XsdElementDeclaration xsdWrapperElement = elementWrapper.getXsdElementDeclaration();
				if (xsdWrapperElement == null) {
					// there will be a separate message for unresolved wrapper element
					// no need to also have a message for the nested element
					return;
				}
				resolvedXsdElement = xsdWrapperElement.getElement(getNamespace(), getName());
			}
			
			if (resolvedXsdElement == null) {
				messages.add(getUnresolveSchemaComponentMessage(astRoot));
			}
		}
	}
	
	
	public interface Context {
		
		JaxbAttributeMapping getAttributeMapping();
		
		XmlElementAnnotation getAnnotation(boolean createIfNull);
		
		String getDefaultType();
		
		XmlElementWrapper getElementWrapper();
	}
}
