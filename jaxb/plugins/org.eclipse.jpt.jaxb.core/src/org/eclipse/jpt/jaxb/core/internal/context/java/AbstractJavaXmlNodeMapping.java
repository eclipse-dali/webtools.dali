/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlNodeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlListAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdSimpleTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.xsd.XSDVariety;


public abstract class AbstractJavaXmlNodeMapping<A extends Annotation>
		extends AbstractJavaAdaptableAttributeMapping<A>
		implements XmlNodeMapping {
	
	protected XmlSchemaType xmlSchemaType;
	
	protected boolean specifiedXmlList;
	
	protected boolean defaultXmlList;
	
	
	public AbstractJavaXmlNodeMapping(JaxbPersistentAttribute parent) {
		super(parent);
		initializeXmlSchemaType();
		initializeXmlList();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncXmlSchemaType();
		syncXmlList();
	}
	
	@Override
	public void update() {
		super.update();
		updateXmlSchemaType();
	}
	
	
	// ***** XmlSchemaType *****
	
	public XmlSchemaType getXmlSchemaType() {
		return this.xmlSchemaType;
	}
	
	protected void setXmlSchemaType_(XmlSchemaType xmlSchemaType) {
		XmlSchemaType oldXmlSchemaType = this.xmlSchemaType;
		this.xmlSchemaType = xmlSchemaType;
		this.firePropertyChanged(XML_SCHEMA_TYPE_PROPERTY, oldXmlSchemaType, xmlSchemaType);
	}
	
	public boolean hasXmlSchemaType() {
		return this.xmlSchemaType != null;
	}
	
	public XmlSchemaType addXmlSchemaType() {
		if (this.xmlSchemaType != null) {
			throw new IllegalStateException();
		}
		XmlSchemaTypeAnnotation annotation = (XmlSchemaTypeAnnotation) this.getJavaResourceAttribute().addAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		
		XmlSchemaType xmlJavaTypeAdapter = this.buildXmlSchemaType(annotation);
		this.setXmlSchemaType_(xmlJavaTypeAdapter);
		return xmlJavaTypeAdapter;
	}
	
	public void removeXmlSchemaType() {
		if (this.xmlSchemaType == null) {
			throw new IllegalStateException();
		}
		this.getJavaResourceAttribute().removeAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		this.setXmlSchemaType_(null);
	}
	
	protected XmlSchemaType buildXmlSchemaType(XmlSchemaTypeAnnotation annotation) {
		return new GenericJavaAttributeMappingXmlSchemaType(this, annotation);
	}
	
	protected XmlSchemaTypeAnnotation getXmlSchemaTypeAnnotation() {
		if (getJavaResourceAttribute().getAnnotationsSize(JAXB.XML_SCHEMA_TYPE) > 0) {
			return (XmlSchemaTypeAnnotation) getJavaResourceAttribute().getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		}
		return null;
	}
	
	protected void initializeXmlSchemaType() {
		XmlSchemaTypeAnnotation annotation = this.getXmlSchemaTypeAnnotation();
		if (annotation != null) {
			this.xmlSchemaType = this.buildXmlSchemaType(annotation);
		}		
	}
	
	protected void updateXmlSchemaType() {
		if (this.xmlSchemaType != null) {
			this.xmlSchemaType.update();
		}
	}
	
	protected void syncXmlSchemaType() {
		XmlSchemaTypeAnnotation annotation = this.getXmlSchemaTypeAnnotation();
		if (annotation != null) {
			if (this.getXmlSchemaType() != null) {
				this.getXmlSchemaType().synchronizeWithResourceModel();
			}
			else {
				this.setXmlSchemaType_(this.buildXmlSchemaType(annotation));
			}
		}
		else {
			this.setXmlSchemaType_(null);
		}
	}
	
	
	// ***** XmlList *****
	
	public boolean isXmlList() {
		return isSpecifiedXmlList() || isDefaultXmlList();
	}
	
	public boolean isSpecifiedXmlList() {
		return this.specifiedXmlList;
	}
	
	public void setSpecifiedXmlList(boolean newValue) {
		if (this.specifiedXmlList == newValue) {
			throw new IllegalStateException();
		}
		
		if (newValue) {
			getJavaResourceAttribute().addAnnotation(JAXB.XML_LIST);
		}
		else {
			getJavaResourceAttribute().removeAnnotation(JAXB.XML_LIST);
		}
		
		setSpecifiedXmlList_(newValue);
	}
	
	protected void setSpecifiedXmlList_(boolean newValue) {
		boolean oldValue = this.specifiedXmlList;
		this.specifiedXmlList = newValue;
		firePropertyChanged(SPECIFIED_XML_LIST_PROPERTY, oldValue, newValue);
	}
	
	public boolean isDefaultXmlList() {
		return this.defaultXmlList;
	}
	
	protected void setDefaultXmlList_(boolean newValue) {
		boolean oldValue = this.defaultXmlList;
		this.defaultXmlList = newValue;
		firePropertyChanged(DEFAULT_XML_LIST_PROPERTY, oldValue, newValue);
	}
	
	protected void initializeXmlList() {
		this.specifiedXmlList = getXmlListAnnotation() != null;
		this.defaultXmlList = calculateDefaultXmlList();
	}
	
	protected void syncXmlList() {
		setSpecifiedXmlList_(getXmlListAnnotation() != null);
		setDefaultXmlList_(calculateDefaultXmlList());
	}
	
	protected XmlListAnnotation getXmlListAnnotation() {
		return (XmlListAnnotation) getJavaResourceAttribute().getAnnotation(JAXB.XML_LIST);
	}
	
	protected abstract boolean calculateDefaultXmlList();
	
	
	// ***** misc *****
	
	@Override
	public XsdTypeDefinition getDataTypeXsdTypeDefinition() {
		if (this.xmlSchemaType != null) {
			return this.xmlSchemaType.getXsdTypeDefinition();
		}
		return super.getDataTypeXsdTypeDefinition();
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		if (this.xmlSchemaType != null) {
			result = this.xmlSchemaType.getCompletionProposals(pos);
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
		
		if (this.xmlSchemaType != null) {
			this.xmlSchemaType.validate(messages, reporter);
		}
		
		if (isXmlList()) {
			validateXmlList(messages, reporter);
		}
	}
	
	protected void validateXmlList(List<IMessage> messages, IReporter reporter) {
		if (! getPersistentAttribute().isJavaResourceAttributeCollectionType()) {
			messages.add(
				this.buildErrorValidationMessage(
					getXmlListValidationTextRange(),
					JptJaxbCoreValidationMessages.XML_LIST__ATTRIBUTE_NOT_COLLECTION_TYPE
				));
		}
		else {
			XsdTypeDefinition xsdType = getDataTypeXsdTypeDefinition();
			if (xsdType != null
					&& (xsdType.getKind() != XsdTypeDefinition.Kind.SIMPLE
							|| ((XsdSimpleTypeDefinition) xsdType).getXSDComponent().getVariety() == XSDVariety.LIST_LITERAL)) {
				
				messages.add(
						this.buildErrorValidationMessage(
								getValidationTextRange(),
								JptJaxbCoreValidationMessages.XML_LIST__ITEM_TYPE_NOT_MAPPED_TO_VALID_SCHEMA_TYPE,
								getValueTypeName()
							));
			}
		}
	}
	
	protected TextRange getXmlListValidationTextRange() {
		XmlListAnnotation annotation = getXmlListAnnotation();
		return (annotation == null) ? getValidationTextRange() : annotation.getTextRange();
	}
}
