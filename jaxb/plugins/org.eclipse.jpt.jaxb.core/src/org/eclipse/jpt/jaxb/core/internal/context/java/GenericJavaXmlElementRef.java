/*******************************************************************************
 * Copyright (c) 2011, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.TypeTools;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementRef;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
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
	
	
	public GenericJavaXmlElementRef(JaxbContextNode parent, Context context) {
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
	
	protected Context getContext() {
		return this.context;
	}
	
	protected JavaPersistentAttribute getPersistentAttribute() {
		return getContext().getAttributeMapping().getPersistentAttribute();
	}
	
	protected JavaClassMapping getClassMapping() {
		return getPersistentAttribute().getClassMapping();
	}
	
	protected JaxbPackage getJaxbPackage() {
		return getClassMapping().getJaxbPackage();
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
		return (ObjectTools.equals(JAXB.JAXB_ELEMENT, getFullyQualifiedType()));
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
		return getAnnotation().getTextRange();
	}
	
	public TextRange getTypeTextRange() {
		return getAnnotation().getTypeTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.qName.validate(messages, reporter);
		validateType(messages, reporter);
	}
	
	protected void validateType(List<IMessage> messages, IReporter reporter) {
		
		String fqType = getFullyQualifiedType();
		if (StringTools.isBlank(fqType)) {
			messages.add(
					this.buildValidationMessage(
							getTypeTextRange(),
							JptJaxbCoreValidationMessages.XML_ELEMENT_REF__UNSPECIFIED_TYPE
						));
		}
		else if (! StringTools.isBlank(this.specifiedType)
				// verify that type actually exists before validating
				&& JavaProjectTools.findType(getJaxbProject().getJavaProject(), fqType) != null) {
			String attributeValueType = getContext().getAttributeMapping().getValueTypeName();
			if (! TypeTools.isSubTypeOf(fqType, attributeValueType, getJaxbProject().getJavaProject())) {
				messages.add(
						this.buildValidationMessage(
								getTypeTextRange(),
								JptJaxbCoreValidationMessages.XML_ELEMENT_REF__ILLEGAL_TYPE,
								attributeValueType));
								
			}
			
			// if type is a persistent class, check that it or a subclass has a root element specified
			JaxbTypeMapping typeMapping = getJaxbProject().getContextRoot().getTypeMapping(fqType);
			if (typeMapping != null && ! typeMapping.hasRootElementInHierarchy()) {
				messages.add(
						this.buildValidationMessage(
								getTypeTextRange(),
								JptJaxbCoreValidationMessages.XML_ELEMENT_REF__NO_ROOT_ELEMENT,
								attributeValueType));
			}
		}
	}
	
	
	protected class XmlElementRefQName
			extends AbstractJavaElementQName {
		
		protected XmlElementRefQName(JaxbContextNode parent) {
			super(parent, new QNameAnnotationProxy());
		}
		
		
		@Override
		protected JavaPersistentAttribute getPersistentAttribute() {
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
		protected String buildDefaultName() {
			if (isTypeJAXBElement()) {
				return super.buildDefaultName();
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
		protected String buildDefaultNamespace() {
			JaxbTypeMapping referencedTypeMapping = getReferencedTypeMapping();
			if (referencedTypeMapping != null) {
				XmlRootElement rootElement = referencedTypeMapping.getXmlRootElement();
				if (rootElement != null) {
					return rootElement.getQName().getNamespace();
				}
			}
			
			return super.buildDefaultNamespace();
		}
		
		@Override
		protected void validateName(List<IMessage> messages, IReporter reporter) {
			// only validate if type is JAXBElement
			if (isTypeJAXBElement()) {
				super.validateName(messages, reporter);
			}
		}
		
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter) {
			super.validateReference(messages, reporter);
			
			// if type is JAXBElement, then name/namespace must also point at a valid XmlElementDecl
			if (! isTypeJAXBElement()) {
				return;
			}
			
			XmlRegistry registry = getJaxbPackage().getRegistry();
			
			if (registry == null) {
				messages.add(
						this.buildValidationMessage(
								getValidationTextRange(),
								JptJaxbCoreValidationMessages.XML_ELEMENT_REF__NO_REGISTRY
							));
				return;
			}
			
			for (JaxbElementFactoryMethod elementDecl : registry.getElementFactoryMethods()) {
				if (ObjectTools.equals(getName(), elementDecl.getQName().getName())
						&& ObjectTools.equals(getNamespace(), elementDecl.getQName().getNamespace())) {
					return;
				}
			}
			messages.add(
					this.buildValidationMessage(
							getValidationTextRange(),
							JptJaxbCoreValidationMessages.XML_ELEMENT_REF__NO_MATCHING_ELEMENT_DECL,
							getNamespace(),
							getName()));
							
		}
	}
	
	
	protected class QNameAnnotationProxy 
			extends AbstractQNameAnnotationProxy {
		
		@Override
		protected QNameAnnotation getAnnotation(boolean createIfNull) {
			return GenericJavaXmlElementRef.this.getAnnotation();
		}
	}
	
	
	public interface Context {
		
		JavaAttributeMapping getAttributeMapping();
		
		XmlElementRefAnnotation getAnnotation();
		
		String getDefaultType();
		
		XmlElementWrapper getElementWrapper();
	}
}
