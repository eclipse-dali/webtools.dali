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
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementRef;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaXmlElementRef
		extends AbstractJavaContextNode 
		implements XmlElementRef {
	
	protected final Context context;
	
	protected final JaxbQName qName;
	
	protected Boolean specifiedRequired;
	
	protected String specifiedType;
	
	protected String defaultType;
	
	
	public GenericJavaXmlElementRef(JavaContextNode parent, Context context) {
		super(parent);
		this.context = context;
		this.qName = buildQName();
		this.specifiedRequired = buildSpecifiedRequired();
		this.specifiedType = buildSpecifiedType();
		this.defaultType = buildDefaultType();
	}
	
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.qName.synchronizeWithResourceModel();
		setSpecifiedRequired_(buildSpecifiedRequired());
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
	
	protected JaxbClassMapping getJaxbClassMapping() {
		return getPersistentAttribute().getClassMapping();
	}
	
	protected JaxbPackage getJaxbPackage() {
		return getJaxbClassMapping().getJaxbType().getJaxbPackage();
	}
	
	public XmlElementRefAnnotation getAnnotation() {
		return this.context.getAnnotation();
	}
	
	
	// ***** schema component ref *****
	
	public JaxbQName getQName() {
		return this.qName;
	}
	
	protected JaxbQName buildQName() {
		return new XmlElementRefQName(this);
	}
	
	
	// ***** XmlElementRef.required *****
	
	public boolean isRequired() {
		return (this.specifiedRequired == null) ? isDefaultRequired() : this.specifiedRequired.booleanValue();
	}
	
	public Boolean getSpecifiedRequired() {
		return this.specifiedRequired;
	}
	
	public void setSpecifiedRequired(Boolean newSpecifiedRequired) {
		getAnnotation().setRequired(newSpecifiedRequired);
		setSpecifiedRequired_(newSpecifiedRequired);
	}
	
	protected void setSpecifiedRequired_(Boolean newSpecifiedRequired) {
		Boolean oldRequired = this.specifiedRequired;
		this.specifiedRequired = newSpecifiedRequired;
		firePropertyChanged(SPECIFIED_REQUIRED_PROPERTY, oldRequired, newSpecifiedRequired);
	}
	
	protected Boolean buildSpecifiedRequired() {
		return getAnnotation().getRequired();
	}
	
	public boolean isDefaultRequired() {
		return false;
	}
	
	
	// ***** XmlElementRef.type *****
	
	public String getType() {
		return (this.specifiedType == null) ? getDefaultType() : this.specifiedType;
	}
	
	public String getSpecifiedType() {
		return this.specifiedType;
	}
	
	public void setSpecifiedType(String newSpecifiedType) {
		getAnnotation().setType(newSpecifiedType);
		setSpecifiedType_(newSpecifiedType);
	}
	
	protected void setSpecifiedType_(String newSpecifiedType) {
		String oldType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldType, newSpecifiedType);
	}
	
	protected String buildSpecifiedType() {
		return getAnnotation().getType();
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
		return (this.specifiedType == null) ? getDefaultType() : getAnnotation().getFullyQualifiedTypeName();
	}
	
	
	// ***** misc *****
	
	public Iterable<String> getReferencedXmlTypeNames() {
		// only return the specified type - the default type should already be included
		if (this.specifiedType != null
				&& ! isTypeJAXBElement()) {
			return new SingleElementIterable(getFullyQualifiedType());
		}
		
		return EmptyIterable.instance();
	}
	
	protected boolean isTypeJAXBElement() {
		return (StringTools.stringsAreEqual(JAXB.JAXB_ELEMENT, getFullyQualifiedType()));
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
		return getAnnotation().getTextRange(astRoot);
	}
	
	public TextRange getTypeTextRange(CompilationUnit astRoot) {
		return getAnnotation().getTypeTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.qName.validate(messages, reporter, astRoot);
		validateType(messages, reporter, astRoot);
	}
	
	protected void validateType(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		
		String fqType = getFullyQualifiedType();
		if (StringTools.stringIsEmpty(fqType)) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ELEMENT_REF__UNSPECIFIED_TYPE,
							this,
							getTypeTextRange(astRoot)));
		}
		else if (! StringTools.stringIsEmpty(this.specifiedType)
				// verify that type actually exists before validating
				&& JDTTools.findType(getJaxbProject().getJavaProject(), fqType) != null) {
			String attributeValueType = getContext().getAttributeMapping().getValueTypeName();
			if (! JDTTools.typeIsSubType(getJaxbProject().getJavaProject(), fqType, attributeValueType)) {
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.XML_ELEMENT_REF__ILLEGAL_TYPE,
								new String[] { attributeValueType },
								this,
								getTypeTextRange(astRoot)));
								
			}
			
			// if type is a persistent class, check that it or a subclass has a root element specified
			JaxbTypeMapping typeMapping = getJaxbProject().getContextRoot().getTypeMapping(fqType);
			if (typeMapping != null && ! typeMapping.hasRootElementInHierarchy()) {
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.XML_ELEMENT_REF__NO_ROOT_ELEMENT,
								new String[] { attributeValueType },
								this,
								getTypeTextRange(astRoot)));
			}
		}
	}
	
	
	protected class XmlElementRefQName
			extends AbstractJavaElementQName {
		
		protected XmlElementRefQName(JavaContextNode parent) {
			super(parent, new QNameAnnotationProxy());
		}
		
		
		@Override
		protected JaxbPersistentAttribute getPersistentAttribute() {
			return GenericJavaXmlElementRef.this.getPersistentAttribute();
		}
		
		protected boolean isTypeJAXBElement() {
			return GenericJavaXmlElementRef.this.isTypeJAXBElement();
		}
		
		protected JaxbTypeMapping getReferencedTypeMapping() {
			String fqTypeName = GenericJavaXmlElementRef.this.getFullyQualifiedType();
			return getJaxbProject().getContextRoot().getTypeMapping(fqTypeName);
		}
		
		@Override
		protected XmlElementWrapper getElementWrapper() {
			return GenericJavaXmlElementRef.this.context.getElementWrapper();
		}
		
		@Override
		public String getDefaultName() {
			if (isTypeJAXBElement()) {
				return super.getDefaultName();
			}
			
			JaxbTypeMapping referencedTypeMapping = getReferencedTypeMapping();
			if (referencedTypeMapping != null) {
				XmlRootElement rootElement = referencedTypeMapping.getXmlRootElement();
				if (rootElement != null) {
					return rootElement.getQName().getName();
				}
			}
			
			return "";
		}
		
		@Override
		public String getDefaultNamespace() {
			JaxbTypeMapping referencedTypeMapping = getReferencedTypeMapping();
			if (referencedTypeMapping != null) {
				XmlRootElement rootElement = referencedTypeMapping.getXmlRootElement();
				if (rootElement != null) {
					return rootElement.getQName().getNamespace();
				}
			}
			
			return super.getDefaultNamespace();
		}
		
		@Override
		protected void validateName(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			// only validate if type is JAXBElement
			if (isTypeJAXBElement()) {
				super.validateName(messages, reporter, astRoot);
			}
		}
		
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			super.validateReference(messages, reporter, astRoot);
			
			// if type is JAXBElement, then name/namespace must also point at a valid XmlElementDecl
			if (! isTypeJAXBElement()) {
				return;
			}
			
			XmlRegistry registry = getJaxbPackage().getRegistry();
			
			if (registry == null) {
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.XML_ELEMENT_REF__NO_REGISTRY,
								this,
								getValidationTextRange(astRoot)));
				return;
			}
			
			for (JaxbElementFactoryMethod elementDecl : registry.getElementFactoryMethods()) {
				if (Tools.valuesAreEqual(getName(), elementDecl.getQName().getName())
						&& Tools.valuesAreEqual(getNamespace(), elementDecl.getQName().getNamespace())) {
					return;
				}
			}
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ELEMENT_REF__NO_MATCHING_ELEMENT_DECL,
							new String[] { getNamespace(), getName() },
							this,
							getValidationTextRange(astRoot)));
							
		}
	}
	
	
	protected class QNameAnnotationProxy 
			extends AbstractJavaQName.AbstractQNameAnnotationProxy {
		
		@Override
		protected QNameAnnotation getAnnotation(boolean createIfNull) {
			return GenericJavaXmlElementRef.this.getAnnotation();
		}
	}
	
	
	public interface Context {
		
		JaxbAttributeMapping getAttributeMapping();
		
		XmlElementRefAnnotation getAnnotation();
		
		String getDefaultType();
		
		XmlElementWrapper getElementWrapper();
	}
}
