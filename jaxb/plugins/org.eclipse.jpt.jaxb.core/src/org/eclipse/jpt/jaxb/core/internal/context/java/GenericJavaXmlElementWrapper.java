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
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlElementWrapper
		extends AbstractJavaContextNode
		implements XmlElementWrapper {
	
	protected final Context context;
	
	protected JaxbQName qName;
	
	protected Boolean specifiedRequired;
	
	protected Boolean specifiedNillable;
	
	
	public GenericJavaXmlElementWrapper(JaxbAttributeMapping parent, Context context) {
		super(parent);
		this.context = context;
		this.qName = buildQName();
		this.specifiedRequired = buildSpecifiedRequired();
		this.specifiedNillable = this.buildSpecifiedNillable();
	}
	
	
	protected JaxbQName buildQName() {
		return new XmlElementQName(this);
	}
	
	@Override
	public JaxbAttributeMapping getParent() {
		return (JaxbAttributeMapping) super.getParent();
	}
	
	protected JaxbAttributeMapping getAttributeMapping() {
		return getParent();
	}
	
	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getAttributeMapping().getPersistentAttribute();
	}
	
	protected JaxbClassMapping getJaxbClassMapping() {
		return getPersistentAttribute().getClassMapping();
	}
	
	protected JaxbPackage getJaxbPackage() {
		return getJaxbClassMapping().getJaxbType().getJaxbPackage();
	}
	
	protected XmlElementWrapperAnnotation getAnnotation() {
		return this.context.getAnnotation();
	}
	
	
	// ********** synchronize/update **********
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.qName.synchronizeWithResourceModel();
		setSpecifiedRequired_(buildSpecifiedRequired());
		this.setSpecifiedNillable_(this.buildSpecifiedNillable());
	}
	
	@Override
	public void update() {
		super.update();
		this.qName.update();
	}
	
	
	// ***** schema component ref *****
	
	public JaxbQName getQName() {
		return this.qName;
	}
	
	
	//************ XmlElementWrapper.required ***************
	
	public boolean isRequired() {
		return (this.getSpecifiedRequired() == null) ? this.isDefaultRequired() : this.getSpecifiedRequired().booleanValue();
	}
	
	public boolean isDefaultRequired() {
		return DEFAULT_REQUIRED;
	}
	
	public Boolean getSpecifiedRequired() {
		return this.specifiedRequired;
	}
	
	public void setSpecifiedRequired(Boolean newSpecifiedRequired) {
		getAnnotation().setRequired(newSpecifiedRequired);
		this.setSpecifiedRequired_(newSpecifiedRequired);
	}
	
	protected void setSpecifiedRequired_(Boolean newSpecifiedRequired) {
		Boolean oldRequired = this.specifiedRequired;
		this.specifiedRequired = newSpecifiedRequired;
		firePropertyChanged(SPECIFIED_REQUIRED_PROPERTY, oldRequired, newSpecifiedRequired);
	}
	
	protected Boolean buildSpecifiedRequired() {
		return getAnnotation().getRequired();
	}
	
	
	//************  XmlElementWrapper.nillable ***************
	
	public boolean isNillable() {
		return (this.getSpecifiedNillable() == null) ? this.isDefaultNillable() : this.getSpecifiedNillable().booleanValue();
	}
	
	public boolean isDefaultNillable() {
		return DEFAULT_NILLABLE;
	}
	
	public Boolean getSpecifiedNillable() {
		return this.specifiedNillable;
	}
	
	public void setSpecifiedNillable(Boolean newSpecifiedNillable) {
		getAnnotation().setNillable(newSpecifiedNillable);
		this.setSpecifiedNillable_(newSpecifiedNillable);
	}
	
	protected void setSpecifiedNillable_(Boolean newSpecifiedNillable) {
		Boolean oldNillable = this.specifiedNillable;
		this.specifiedNillable = newSpecifiedNillable;
		firePropertyChanged(SPECIFIED_NILLABLE_PROPERTY, oldNillable, newSpecifiedNillable);
	}
	
	protected Boolean buildSpecifiedNillable() {
		return getAnnotation().getNillable();
	}
	
	
	// **************** misc **************************************************
	
	public XsdElementDeclaration getXsdElementDeclaration() {
		XsdTypeDefinition xsdType = getPersistentAttribute().getClassMapping().getXsdTypeDefinition();
		return (xsdType == null) ? null : xsdType.getElement(this.qName.getNamespace(), this.qName.getName());
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		result = this.qName.getCompletionProposals(pos);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		return EmptyIterable.instance();
	}
	
	
	//************* validation ****************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		if (! getPersistentAttribute().isJavaResourceAttributeCollectionType()) {
			messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.XML_ELEMENT_WRAPPER_DEFINED_ON_NON_ARRAY_NON_COLLECTION,
					this,
					getValidationTextRange()));
		}
		
		validateQName(messages, reporter);
	}
	
	protected void validateQName(List<IMessage> messages, IReporter reporter) {
		this.qName.validate(messages, reporter);
	}
	
	@Override
	public TextRange getValidationTextRange() {
		return getAnnotation().getTextRange();
	}
	
	
	public interface Context {
		
		/**
		 * This should never be null
		 */
		XmlElementWrapperAnnotation getAnnotation();
	}
	
	
	public static class SimpleContext
			implements Context {
		
		protected XmlElementWrapperAnnotation annotation;
		
		public SimpleContext(XmlElementWrapperAnnotation annotation) {
			this.annotation = annotation;
		}
		
		public XmlElementWrapperAnnotation getAnnotation() {
			return this.annotation;
		}
	}
	
	
	protected class XmlElementQName
			extends AbstractJavaQName {
		
		protected XmlElementQName(JavaContextNode parent) {
			super(parent, new QNameAnnotationProxy());
		}
		
		
		@Override
		protected JaxbPackage getJaxbPackage() {
			return GenericJavaXmlElementWrapper.this.getJaxbPackage();
		}
		
		@Override
		public String getDefaultName() {
			return "";
		}
		
		@Override
		public String getDefaultNamespace() {
			JaxbPackage jaxbPackage = this.getJaxbPackage();
			return (jaxbPackage != null && jaxbPackage.getElementFormDefault() == XmlNsForm.QUALIFIED) ?
					GenericJavaXmlElementWrapper.this.getJaxbClassMapping().getQName().getNamespace() : "";
		}
		
		@Override
		public Iterable<String> getNameProposals() {
			XsdTypeDefinition xsdType = GenericJavaXmlElementWrapper.this.getJaxbClassMapping().getXsdTypeDefinition();
			return (xsdType == null) ? EmptyIterable.instance() : xsdType.getElementNameProposals(getNamespace());
		}
		
		@Override
		public Iterable<String> getNamespaceProposals() {
			XsdSchema schema = this.getXsdSchema();
			return (schema == null) ? EmptyIterable.<String>instance() : schema.getNamespaceProposals();
		}
		
		@Override
		public String getReferencedComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_ELEMENT_DESC;
		}
		
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter) {
			XsdTypeDefinition type = getJaxbClassMapping().getXsdTypeDefinition();
			if (type == null) {
				return;
			}
			
			if (type.getElement(getNamespace(), getName()) == null) {
				messages.add(getUnresolveSchemaComponentMessage());
			}
		}
	}
	
	
	protected class QNameAnnotationProxy 
			extends AbstractJavaQName.AbstractQNameAnnotationProxy {
		
		@Override
		protected QNameAnnotation getAnnotation(boolean createIfNull) {
			return GenericJavaXmlElementWrapper.this.getAnnotation();
		}
	}
}
