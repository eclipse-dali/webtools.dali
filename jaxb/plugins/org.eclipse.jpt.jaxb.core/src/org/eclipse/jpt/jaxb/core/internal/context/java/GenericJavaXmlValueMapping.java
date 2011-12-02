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
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlValueMapping;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlListAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlValueAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdSimpleTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.xsd.XSDVariety;

public class GenericJavaXmlValueMapping
		extends AbstractJavaAdaptableAttributeMapping<XmlValueAnnotation>
		implements XmlValueMapping {
	
	protected boolean specifiedXmlList;
	
	protected boolean defaultXmlList;
	
	
	public GenericJavaXmlValueMapping(JaxbPersistentAttribute parent) {
		super(parent);
		initializeXmlList();
	}
	
	
	public String getKey() {
		return MappingKeys.XML_VALUE_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return JAXB.XML_VALUE;
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncXmlList();
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
		this.defaultXmlList = getPersistentAttribute().isJavaResourceAttributeCollectionType();
	}
	
	protected void syncXmlList() {
		setSpecifiedXmlList_(getXmlListAnnotation() != null);
		setDefaultXmlList_(getPersistentAttribute().isJavaResourceAttributeCollectionType());
	}
	
	protected XmlListAnnotation getXmlListAnnotation() {
		return (XmlListAnnotation) getJavaResourceAttribute().getAnnotation(JAXB.XML_LIST);
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		if (isXmlList()) {
			if (! getPersistentAttribute().isJavaResourceAttributeCollectionType()) {
				messages.add(
					DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.XML_LIST__ATTRIBUTE_NOT_COLLECTION_TYPE,
						this,
						getXmlListValidationTextRange(astRoot)));
			}
			else {
				XsdTypeDefinition xsdType = getDataTypeXsdTypeDefinition();
				if (xsdType != null
						&& (xsdType.getKind() != XsdTypeDefinition.Kind.SIMPLE
								|| ((XsdSimpleTypeDefinition) xsdType).getXSDComponent().getVariety() == XSDVariety.LIST_LITERAL)) {
					
					messages.add(
							DefaultValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									JaxbValidationMessages.XML_LIST__ITEM_TYPE_NOT_MAPPED_TO_VALID_SCHEMA_TYPE,
									new String[] { getValueTypeName() },
									this,
									getValidationTextRange(astRoot)));
				}
			}
		}
		
		validateSchemaType(messages, reporter, astRoot);
	}
	
	
	
	protected void validateSchemaType(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		XsdTypeDefinition xsdClassType = getClassMapping().getXsdTypeDefinition();
		XsdTypeDefinition xsdType = (xsdClassType == null) ? null : xsdClassType.getBaseType();
		XsdTypeDefinition expectedSchemaType = getDataTypeXsdTypeDefinition();
		
		if (xsdType == null || expectedSchemaType == null) {
			return;
		}
		
		if (! xsdType.typeIsValid(expectedSchemaType, isXmlList())) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_VALUE__INVALID_SCHEMA_TYPE,
							new String[] { getValueTypeName() },
							this,
							getValidationTextRange(astRoot)));
		}
	}
	
	protected TextRange getXmlListValidationTextRange(CompilationUnit astRoot) {
		XmlListAnnotation annotation = getXmlListAnnotation();
		return (annotation == null) ? getValidationTextRange(astRoot) : annotation.getTextRange(astRoot);
	}
}
