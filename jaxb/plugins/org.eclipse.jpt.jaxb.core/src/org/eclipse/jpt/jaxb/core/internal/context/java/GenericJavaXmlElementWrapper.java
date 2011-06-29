/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.Collection;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbSchemaComponentRef;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.SchemaComponentRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlElementWrapper
		extends AbstractJavaContextNode
		implements XmlElementWrapper {
	
	protected final XmlElementWrapperAnnotation annotation;
	
	protected JaxbSchemaComponentRef schemaComponentRef;
	
	protected Boolean specifiedRequired;
	
	protected Boolean specifiedNillable;
	
	
	public GenericJavaXmlElementWrapper(JaxbAttributeMapping parent, XmlElementWrapperAnnotation annotation) {
		super(parent);
		this.annotation = annotation;
		this.schemaComponentRef = buildSchemaComponentRef();
		this.specifiedRequired = buildSpecifiedRequired();
		this.specifiedNillable = this.buildSpecifiedNillable();
	}
	
	
	protected JaxbSchemaComponentRef buildSchemaComponentRef() {
		return new XmlElementSchemaComponentRef(this);
	}
	
	@Override
	public JaxbAttributeMapping getParent() {
		return (JaxbAttributeMapping) super.getParent();
	}
	
	protected JaxbAttributeMapping getAttributeMapping() {
		return getParent();
	}
	
	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getAttributeMapping().getParent();
	}
	
	protected JaxbPersistentClass getPersistentClass() {
		return getPersistentAttribute().getPersistentClass();
	}
	
	
	// ********** synchronize/update **********
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.schemaComponentRef.synchronizeWithResourceModel();
		setSpecifiedRequired_(buildSpecifiedRequired());
		this.setSpecifiedNillable_(this.buildSpecifiedNillable());
	}
	
	@Override
	public void update() {
		super.update();
		this.schemaComponentRef.update();
	}
	
	
	// ***** schema component ref *****
	
	public JaxbSchemaComponentRef getSchemaElementRef() {
		return this.schemaComponentRef;
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
		this.annotation.setRequired(newSpecifiedRequired);
		this.setSpecifiedRequired_(newSpecifiedRequired);
	}
	
	protected void setSpecifiedRequired_(Boolean newSpecifiedRequired) {
		Boolean oldRequired = this.specifiedRequired;
		this.specifiedRequired = newSpecifiedRequired;
		firePropertyChanged(SPECIFIED_REQUIRED_PROPERTY, oldRequired, newSpecifiedRequired);
	}
	
	protected Boolean buildSpecifiedRequired() {
		return this.annotation.getRequired();
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
		this.annotation.setNillable(newSpecifiedNillable);
		this.setSpecifiedNillable_(newSpecifiedNillable);
	}
	
	protected void setSpecifiedNillable_(Boolean newSpecifiedNillable) {
		Boolean oldNillable = this.specifiedNillable;
		this.specifiedNillable = newSpecifiedNillable;
		firePropertyChanged(SPECIFIED_NILLABLE_PROPERTY, oldNillable, newSpecifiedNillable);
	}
	
	protected Boolean buildSpecifiedNillable() {
		return this.annotation.getNillable();
	}
	
	
	// **************** misc **************************************************
	
	public XsdElementDeclaration getXsdElementDeclaration() {
		XsdTypeDefinition xsdType = getPersistentAttribute().getPersistentClass().getXsdTypeDefinition();
		return (xsdType == null) ? null : xsdType.getElement(this.schemaComponentRef.getNamespace(), this.schemaComponentRef.getName());
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		result = this.schemaComponentRef.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		return EmptyIterable.instance();
	}
	
	
	//************* validation ****************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (!getPersistentAttribute().isJavaResourceAttributeTypeSubTypeOf(Collection.class.getName()) && !getPersistentAttribute().isJavaResourceAttributeTypeArray()) {
			messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.XML_ELEMENT_WRAPPER_DEFINED_ON_NON_ARRAY_NON_COLLECTION,
					this,
					getValidationTextRange(astRoot)));
		}
	}

	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.annotation.getTextRange(astRoot);
	}
	
	
	protected class XmlElementSchemaComponentRef
			extends AbstractJavaSchemaComponentRef {
		
		protected XmlElementSchemaComponentRef(JavaContextNode parent) {
			super(parent);
		}
		
		
		@Override
		protected SchemaComponentRefAnnotation getAnnotation(boolean createIfNull) {
			// never null
			return GenericJavaXmlElementWrapper.this.annotation;
		}
		
		@Override
		public String getDefaultName() {
			return "";
		}
		
		@Override
		public String getDefaultNamespace() {
			return (GenericJavaXmlElementWrapper.this.getPersistentClass().getJaxbPackage().getElementFormDefault() == XmlNsForm.QUALIFIED) ?
					GenericJavaXmlElementWrapper.this.getPersistentClass().getSchemaTypeRef().getNamespace() : "";
		}
		
		@Override
		public Iterable<String> getNameProposals(Filter<String> filter) {
			XsdTypeDefinition xsdType = GenericJavaXmlElementWrapper.this.getPersistentClass().getXsdTypeDefinition();
			return (xsdType == null) ? EmptyIterable.instance() : xsdType.getElementNameProposals(getNamespace(), filter);
		}
		
		@Override
		public Iterable<String> getNamespaceProposals(Filter<String> filter) {
			XsdSchema schema = GenericJavaXmlElementWrapper.this.getPersistentClass().getJaxbPackage().getXsdSchema();
			return (schema == null) ? EmptyIterable.<String>instance() : schema.getNamespaceProposals(filter);
		}
		
		@Override
		public String getSchemaComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_ELEMENT_DESC;
		}
		
		@Override
		protected void validateSchemaComponentRef(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			XsdTypeDefinition type = getPersistentClass().getXsdTypeDefinition();
			if (type == null) {
				return;
			}
			
			if (type.getElement(getNamespace(), getName()) == null) {
				messages.add(getUnresolveSchemaComponentMessage(astRoot));
			}
		}
	}
}
