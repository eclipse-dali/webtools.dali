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
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbSchemaComponentRef;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlElementMapping
		extends GenericJavaContainmentMapping<XmlElementAnnotation>
		implements XmlElementMapping {
	
	protected Boolean specifiedNillable;
	
	protected String defaultValue;
	
	protected String specifiedType;
	
	protected XmlElementWrapper xmlElementWrapper;
	
	
	public GenericJavaXmlElementMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.specifiedNillable = this.buildSpecifiedNillable();
		this.defaultValue = this.buildDefaultValue();
		this.specifiedType = this.buildSpecifiedType();
		this.initializeXmlElementWrapper();			
	}
	
	
	@Override
	protected JaxbSchemaComponentRef buildSchemaComponentRef() {
		return new XmlElementSchemaComponentRef(this);
	}
	
	public String getKey() {
		return MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return JAXB.XML_ELEMENT;
	}
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedNillable_(this.buildSpecifiedNillable());
		this.setDefaultValue_(this.buildDefaultValue());
		this.setSpecifiedType_(this.buildSpecifiedType());
		this.syncXmlElementWrapper();
	}
	
	@Override
	public void update() {
		super.update();
		this.updateXmlElementWrapper();
	}
	
	
	//************  XmlElement.nillable ***************
	
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
		this.getOrCreateAnnotation().setNillable(newSpecifiedNillable);
		this.setSpecifiedNillable_(newSpecifiedNillable);
	}
	
	protected void setSpecifiedNillable_(Boolean newSpecifiedNillable) {
		Boolean oldNillable = this.specifiedNillable;
		this.specifiedNillable = newSpecifiedNillable;
		firePropertyChanged(SPECIFIED_NILLABLE_PROPERTY, oldNillable, newSpecifiedNillable);
	}
	
	protected Boolean buildSpecifiedNillable() {
		return getAnnotation() == null ? null : getAnnotation().getNillable();
	}
	
	
	//************  XmlElement.defaultValue ***************
	
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.getOrCreateAnnotation().setDefaultValue(defaultValue);
		this.setDefaultValue_(defaultValue);
	}
	
	protected void setDefaultValue_(String defaultValue) {
		String oldDefaultValue = this.defaultValue;
		this.defaultValue = defaultValue;
		firePropertyChanged(DEFAULT_VALUE_PROPERTY, oldDefaultValue, defaultValue);		
	}
	
	protected String buildDefaultValue() {
		return getAnnotation() == null ? null : getAnnotation().getDefaultValue();
	}
	
	
	//************  XmlElement.type ***************
	
	public String getType() {
		return getSpecifiedType() == null ? getDefaultType() : getSpecifiedType();
	}
	
	public String getDefaultType() {
		//TODO calculate default type
		return null;
	}
	
	public String getSpecifiedType() {
		return this.specifiedType;
	}
	
	public void setSpecifiedType(String newSpecifiedType) {
		this.getOrCreateAnnotation().setType(newSpecifiedType);
		this.setSpecifiedType_(newSpecifiedType);
	}
	
	protected void setSpecifiedType_(String newSpecifiedType) {
		String oldType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldType, newSpecifiedType);
	}
	
	protected String buildSpecifiedType() {
		return getAnnotation() == null ? null : getAnnotation().getType();
	}
	
	
	//************  XmlElementWrapper ***************
	
	public XmlElementWrapper getXmlElementWrapper() {
		return this.xmlElementWrapper;
	}
	
	public XmlElementWrapper addXmlElementWrapper() {
		if (this.xmlElementWrapper != null) {
			throw new IllegalStateException();
		}
		XmlElementWrapperAnnotation annotation = 
				(XmlElementWrapperAnnotation) this.getJavaResourceAttribute().addAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		XmlElementWrapper xmlElementWrapper = this.buildXmlElementWrapper(annotation);
		this.setXmlElementWrapper_(xmlElementWrapper);
		return xmlElementWrapper;
	}
	
	protected XmlElementWrapper buildXmlElementWrapper(XmlElementWrapperAnnotation xmlElementWrapperAnnotation) {
		return new GenericJavaXmlElementWrapper(this, xmlElementWrapperAnnotation);
	}
	
	public void removeXmlElementWrapper() {
		if (this.xmlElementWrapper == null) {
			throw new IllegalStateException();
		}
		this.getJavaResourceAttribute().removeAnnotation(XmlElementWrapperAnnotation.ANNOTATION_NAME);
		this.setXmlElementWrapper_(null);
	}
	
	protected void initializeXmlElementWrapper() {
		XmlElementWrapperAnnotation annotation = this.getXmlElementWrapperAnnotation();
		if (annotation != null) {
			this.xmlElementWrapper = this.buildXmlElementWrapper(annotation);
		}
	}
	
	protected XmlElementWrapperAnnotation getXmlElementWrapperAnnotation() {
		return (XmlElementWrapperAnnotation) this.getJavaResourceAttribute().getAnnotation(XmlElementWrapperAnnotation.ANNOTATION_NAME);
	}
	
	protected void syncXmlElementWrapper() {
		XmlElementWrapperAnnotation annotation = this.getXmlElementWrapperAnnotation();
		if (annotation != null) {
			if (this.getXmlElementWrapper() != null) {
				this.getXmlElementWrapper().synchronizeWithResourceModel();
			}
			else {
				this.setXmlElementWrapper_(this.buildXmlElementWrapper(annotation));
			}
		}
		else {
			this.setXmlElementWrapper_(null);
		}
	}
	
	protected void updateXmlElementWrapper() {
		if (this.getXmlElementWrapper() != null) {
			this.getXmlElementWrapper().update();
		}
	}
	
	protected void setXmlElementWrapper_(XmlElementWrapper xmlElementWrapper) {
		XmlElementWrapper oldXmlElementWrapper = this.xmlElementWrapper;
		this.xmlElementWrapper = xmlElementWrapper;
		firePropertyChanged(XML_ELEMENT_WRAPPER_PROPERTY, oldXmlElementWrapper, xmlElementWrapper);
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		if (this.xmlElementWrapper != null) {
			result = this.xmlElementWrapper.getJavaCompletionProposals(pos, filter, astRoot);
			
			if (! CollectionTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	
	// **************** validation ********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		if (this.getXmlElementWrapper() != null) {
			this.getXmlElementWrapper().validate(messages, reporter, astRoot);
		}
	}
	
	
	protected class XmlElementSchemaComponentRef
			extends ContainmentMappingSchemaComponentRef {
		
		protected XmlElementSchemaComponentRef(JavaContextNode parent) {
			super(parent);
		}
		
		
		@Override
		public String getDefaultNamespace() {
			return (GenericJavaXmlElementMapping.this.getJaxbPackage().getElementFormDefault() == XmlNsForm.QUALIFIED) ?
					GenericJavaXmlElementMapping.this.getPersistentClass().getSchemaTypeRef().getNamespace() : "";
		}
		
		@Override
		public Iterable<String> getNameProposals(Filter<String> filter) {
			XsdTypeDefinition xsdType = GenericJavaXmlElementMapping.this.getPersistentClass().getXsdTypeDefinition();
			
			if (xsdType == null) {
				return EmptyIterable.instance();
			}
			
			if (GenericJavaXmlElementMapping.this.xmlElementWrapper != null) {
				XsdElementDeclaration wrapperElement = GenericJavaXmlElementMapping.this.xmlElementWrapper.getXsdElementDeclaration();
				return (wrapperElement == null) ? EmptyIterable.<String>instance() : wrapperElement.getElementNameProposals(getNamespace(), filter);
			}
			else {
				return xsdType.getElementNameProposals(getNamespace(), filter);
			}
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
			
			XsdElementDeclaration element = null;
			
			if (GenericJavaXmlElementMapping.this.xmlElementWrapper != null) {
				XsdElementDeclaration wrapperElement = GenericJavaXmlElementMapping.this.xmlElementWrapper.getXsdElementDeclaration();
				if (wrapperElement == null) {
					return;
				}
				element = wrapperElement.getElement(getNamespace(), getName());
			}
			else {
				element = type.getElement(getNamespace(), getName());
			}
			
			if (element == null) {
				messages.add(getUnresolveSchemaComponentMessage(astRoot));
			}
		}
	}
}
