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
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbBasicMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAttachmentRef;
import org.eclipse.jpt.jaxb.core.context.XmlID;
import org.eclipse.jpt.jaxb.core.context.XmlIDREF;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JaxbBasicSchemaComponentAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttachmentRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDREFAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlListAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdSimpleTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.xsd.XSDVariety;

public abstract class AbstractJavaBasicMapping<A extends JaxbBasicSchemaComponentAnnotation>
		extends AbstractJavaAdaptableAttributeMapping<A>
		implements JaxbBasicMapping {
	
	protected XmlSchemaType xmlSchemaType;
	
	protected boolean specifiedXmlList;
	
	protected boolean defaultXmlList;
	
	protected XmlID xmlID;
	
	protected XmlIDREF xmlIDREF;
	
	protected XmlAttachmentRef xmlAttachmentRef;
	
	
	public AbstractJavaBasicMapping(JaxbPersistentAttribute parent) {
		super(parent);
		initializeXmlSchemaType();
		initializeXmlList();
		initializeXmlID();
		initializeXmlIDREF();
		initializeXmlAttachmentRef();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncXmlSchemaType();
		syncXmlList();
		syncXmlID();
		syncXmlIDREF();
		syncXmlAttachmentRef();
	}
	
	@Override
	public void update() {
		super.update();
		updateXmlSchemaType();
		updateXmlID();
		updateXmlIDREF();
		updateXmlAttachmentRef();
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
		this.getJavaResourceAttribute().removeAnnotation(JAXB.XML_SCHEMA_TYPE);
		this.setXmlSchemaType_(null);
	}

	protected XmlSchemaType buildXmlSchemaType(XmlSchemaTypeAnnotation annotation) {
		return new GenericJavaAttributeMappingXmlSchemaType(this, annotation);
	}

	protected XmlSchemaTypeAnnotation getXmlSchemaTypeAnnotation() {
		return (XmlSchemaTypeAnnotation) this.getJavaResourceAttribute().getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
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
	
	
	// ***** XmlID *****

	public XmlID getXmlID() {
		return this.xmlID;
	}

	public XmlID addXmlID() {
		if (this.xmlID != null) {
			throw new IllegalStateException();
		}
		XmlIDAnnotation annotation = (XmlIDAnnotation) this.getJavaResourceAttribute().addAnnotation(JAXB.XML_ID);

		XmlID xmlID = this.buildXmlID(annotation);
		this.setXmlID_(xmlID);
		return xmlID;
	}

	protected XmlID buildXmlID(XmlIDAnnotation xmlIDAnnotation) {
		return new GenericJavaXmlID(this, xmlIDAnnotation);
	}

	public void removeXmlID() {
		if (this.xmlID == null) {
			throw new IllegalStateException();
		}
		this.getJavaResourceAttribute().removeAnnotation(JAXB.XML_ID);
		this.setXmlID_(null);
	}

	protected void initializeXmlID() {
		XmlIDAnnotation annotation = this.getXmlIDAnnotation();
		if (annotation != null) {
			this.xmlID = this.buildXmlID(annotation);
		}
	}

	protected XmlIDAnnotation getXmlIDAnnotation() {
		return (XmlIDAnnotation) this.getJavaResourceAttribute().getAnnotation(JAXB.XML_ID);
	}

	protected void syncXmlID() {
		XmlIDAnnotation annotation = this.getXmlIDAnnotation();
		if (annotation != null) {
			if (this.getXmlID() != null) {
				this.getXmlID().synchronizeWithResourceModel();
			}
			else {
				this.setXmlID_(this.buildXmlID(annotation));
			}
		}
		else {
			this.setXmlID_(null);
		}
	}

	protected void updateXmlID() {
		if (this.getXmlID() != null) {
			this.getXmlID().update();
		}
	}

	protected void setXmlID_(XmlID xmlID) {
		XmlID oldXmlID = this.xmlID;
		this.xmlID = xmlID;
		firePropertyChanged(XML_ID_PROPERTY, oldXmlID, xmlID);
	}

	
	//************  XmlIDREF ***************

	public XmlIDREF getXmlIDREF() {
		return this.xmlIDREF;
	}
	
	protected void setXmlIDREF_(XmlIDREF xmlIDREF) {
		XmlIDREF oldXmlIDREF = this.xmlIDREF;
		this.xmlIDREF = xmlIDREF;
		firePropertyChanged(XML_IDREF_PROPERTY, oldXmlIDREF, xmlIDREF);
	}
	
	public XmlIDREF addXmlIDREF() {
		if (this.xmlIDREF != null) {
			throw new IllegalStateException();
		}
		
		getJavaResourceAttribute().addAnnotation(JAXB.XML_IDREF);
		
		XmlIDREF xmlIDREF = buildXmlIDREF();
		setXmlIDREF_(xmlIDREF);
		return xmlIDREF;
	}
	
	public void removeXmlIDREF() {
		if (this.xmlIDREF == null) {
			throw new IllegalStateException();
		}
		getJavaResourceAttribute().removeAnnotation(JAXB.XML_IDREF);
		setXmlIDREF_(null);
	}
	
	protected void initializeXmlIDREF() {
		if (getXmlIDREFAnnotation() != null) {
			this.xmlIDREF = buildXmlIDREF();
		}
	}
	
	protected void syncXmlIDREF() {
		if (getXmlIDREFAnnotation() == null) {
			setXmlIDREF_(null);
		}
		else if (this.xmlIDREF == null) {
			setXmlIDREF_(buildXmlIDREF());
		}
		else {
			this.xmlIDREF.synchronizeWithResourceModel();
		}
	}

	protected void updateXmlIDREF() {
		if (this.xmlIDREF != null) {
			this.xmlIDREF.update();
		}
	}
	
	protected XmlIDREFAnnotation getXmlIDREFAnnotation() {
		return (XmlIDREFAnnotation) getJavaResourceAttribute().getAnnotation(JAXB.XML_IDREF);
	}
	
	protected XmlIDREF buildXmlIDREF() {
		return new GenericJavaXmlIDREF(this, buildXmlIDREFContext());
	}
	
	protected abstract GenericJavaXmlIDREF.Context buildXmlIDREFContext();


	//************  XmlAttachmentRef ***************

	public XmlAttachmentRef getXmlAttachmentRef() {
		return this.xmlAttachmentRef;
	}

	public XmlAttachmentRef addXmlAttachmentRef() {
		if (this.xmlAttachmentRef != null) {
			throw new IllegalStateException();
		}
		XmlAttachmentRefAnnotation annotation = (XmlAttachmentRefAnnotation) this.getJavaResourceAttribute().addAnnotation(JAXB.XML_ATTACHMENT_REF);

		XmlAttachmentRef xmlAttachmentRef = this.buildXmlAttachmentRef(annotation);
		this.setXmlAttachmentRef_(xmlAttachmentRef);
		return xmlAttachmentRef;
	}

	protected XmlAttachmentRef buildXmlAttachmentRef(XmlAttachmentRefAnnotation xmlAttachmentRefAnnotation) {
		return new GenericJavaXmlAttachmentRef(this, xmlAttachmentRefAnnotation);
	}

	public void removeXmlAttachmentRef() {
		if (this.xmlAttachmentRef == null) {
			throw new IllegalStateException();
		}
		this.getJavaResourceAttribute().removeAnnotation(JAXB.XML_ATTACHMENT_REF);
		this.setXmlAttachmentRef_(null);
	}

	protected void initializeXmlAttachmentRef() {
		XmlAttachmentRefAnnotation annotation = this.getXmlAttachmentRefAnnotation();
		if (annotation != null) {
			this.xmlAttachmentRef = this.buildXmlAttachmentRef(annotation);
		}
	}

	protected XmlAttachmentRefAnnotation getXmlAttachmentRefAnnotation() {
		return (XmlAttachmentRefAnnotation) this.getJavaResourceAttribute().getAnnotation(JAXB.XML_ATTACHMENT_REF);
	}

	protected void syncXmlAttachmentRef() {
		XmlAttachmentRefAnnotation annotation = this.getXmlAttachmentRefAnnotation();
		if (annotation != null) {
			if (this.getXmlAttachmentRef() != null) {
				this.getXmlAttachmentRef().synchronizeWithResourceModel();
			}
			else {
				this.setXmlAttachmentRef_(this.buildXmlAttachmentRef(annotation));
			}
		}
		else {
			this.setXmlAttachmentRef_(null);
		}
	}

	protected void updateXmlAttachmentRef() {
		if (this.getXmlAttachmentRef() != null) {
			this.getXmlAttachmentRef().update();
		}
	}

	protected void setXmlAttachmentRef_(XmlAttachmentRef xmlAttachmentRef) {
		XmlAttachmentRef oldXmlAttachmentRef = this.xmlAttachmentRef;
		this.xmlAttachmentRef = xmlAttachmentRef;
		firePropertyChanged(XML_ATTACHMENT_REF_PROPERTY, oldXmlAttachmentRef, xmlAttachmentRef);
	}
	
	
	// ***** misc *****
	
	@Override
	public String getDataTypeName() {
		if (this.xmlIDREF != null) {
			JaxbClassMapping referenceMapping = getContextRoot().getClassMapping(getValueTypeName());
			if (referenceMapping != null) {
				JaxbAttributeMapping idMapping = referenceMapping.getXmlIdMapping();
				if (idMapping != null) {
					return idMapping.getValueTypeName();
				}
			}
			return String.class.getName();
		}
		return getValueTypeName();
	}
	
	@Override
	public XsdTypeDefinition getDataTypeXsdTypeDefinition() {
		if (this.xmlSchemaType != null) {
			return this.xmlSchemaType.getXsdTypeDefinition();
		}
		return super.getDataTypeXsdTypeDefinition();
	}
	
	
	// ***** content assist *****

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		if (this.xmlSchemaType != null) {
			result = this.xmlSchemaType.getJavaCompletionProposals(pos, filter, astRoot);
			if (! CollectionTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	

	// ***** validation *****

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		if (this.xmlSchemaType != null) {
			this.xmlSchemaType.validate(messages, reporter, astRoot);
		}
		
		if (isXmlList()) {
			validateXmlList(messages, reporter, astRoot);
		}
		
		if (this.xmlID != null) {
			this.xmlID.validate(messages, reporter, astRoot);
		}
		
		if (this.xmlIDREF != null) {
			this.xmlIDREF.validate(messages, reporter, astRoot);
		}
		
		if (this.xmlAttachmentRef != null) {
			this.xmlAttachmentRef.validate(messages, reporter, astRoot);
		}
	}
	
	protected void validateXmlList(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
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
	
	protected TextRange getXmlListValidationTextRange(CompilationUnit astRoot) {
		XmlListAnnotation annotation = getXmlListAnnotation();
		return (annotation == null) ? getValidationTextRange(astRoot) : annotation.getTextRange(astRoot);
	}
	
	
	protected abstract class XmlIDREFContext
			implements GenericJavaXmlIDREF.Context {
		
		public XmlIDREFAnnotation getAnnotation() {
			return AbstractJavaBasicMapping.this.getXmlIDREFAnnotation();
		}
		
		public boolean isList() {
			return AbstractJavaBasicMapping.this.isXmlList();
		}
	}
}
