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

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlIDREF.ValidatableType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttributeAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlAttributeMapping
		extends GenericJavaBasicMapping<XmlAttributeAnnotation>
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
	
	
	// ***** XmlIDREF *****
	
	@Override
	protected GenericJavaXmlIDREF.Context buildXmlIDREFContext() {
		return new XmlIDREFContext();
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
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		this.qName.validate(messages, reporter, astRoot);
	}
	
	
	protected class XmlAttributeQName
			extends AbstractJavaQName {
		
		protected XmlAttributeQName(JavaContextNode parent) {
			super(parent);
		}
		
		
		@Override
		public String getReferencedComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_ATTRIBUTE_DESC;
		}
		
		@Override
		protected QNameAnnotation getAnnotation(boolean createIfNull) {
			if (createIfNull) {
				return GenericJavaXmlAttributeMapping.this.getOrCreateAnnotation();
			}
			else {
				return GenericJavaXmlAttributeMapping.this.getAnnotation();
			}
		}
		
		@Override
		public String getDefaultName() {
			return GenericJavaXmlAttributeMapping.this.getPersistentAttribute().getJavaResourceAttribute().getName();
		}
		
		@Override
		public Iterable<String> getNameProposals(Filter<String> filter) {
			XsdTypeDefinition xsdType = GenericJavaXmlAttributeMapping.this.getPersistentClass().getXsdTypeDefinition();
			return (xsdType == null) ? EmptyIterable.instance() : xsdType.getAttributeNameProposals(getNamespace(), filter);
		}
		
		@Override
		public String getDefaultNamespace() {
			return (GenericJavaXmlAttributeMapping.this.getJaxbPackage().getAttributeFormDefault() == XmlNsForm.QUALIFIED) ?
					GenericJavaXmlAttributeMapping.this.getPersistentClass().getQName().getNamespace() : "";
		}
		
		@Override
		public Iterable<String> getNamespaceProposals(Filter<String> filter) {
			XsdSchema schema = GenericJavaXmlAttributeMapping.this.getJaxbPackage().getXsdSchema();
			return (schema == null) ? EmptyIterable.<String>instance() : schema.getNamespaceProposals(filter);
		}
		
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			XsdTypeDefinition type = getPersistentClass().getXsdTypeDefinition();
			if (type != null) {
				if (type.getAttribute(getNamespace(), getName()) == null) {
					messages.add(getUnresolveSchemaComponentMessage(astRoot));
				}
			}
		}
	}
	
	
	protected class XmlIDREFContext
			extends GenericJavaBasicMapping.XmlIDREFContext {
		
		public Iterable<ValidatableType> getReferencedTypes() {
			return new SingleElementIterable<ValidatableType>(
					new ValidatableType() {
						public String getFullyQualifiedName() {
							return GenericJavaXmlAttributeMapping.this.getPersistentAttribute().getJavaResourceAttributeBaseTypeName();
						}
						
						public TextRange getValidationTextRange(CompilationUnit astRoot) {
							// 1) if we're getting here, XmlIDREF will not be null
							// 2) use the @XmlIDREF text range, since there is no specific place where the type is specified
							return GenericJavaXmlAttributeMapping.this.getXmlIDREF().getValidationTextRange(astRoot);
						}
					});
		}
	}
}
