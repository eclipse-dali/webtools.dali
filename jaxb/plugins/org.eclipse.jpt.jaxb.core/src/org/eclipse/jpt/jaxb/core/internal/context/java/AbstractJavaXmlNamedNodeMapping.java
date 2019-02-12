/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAttachmentRef;
import org.eclipse.jpt.jaxb.core.context.XmlID;
import org.eclipse.jpt.jaxb.core.context.XmlIDREF;
import org.eclipse.jpt.jaxb.core.context.XmlNamedNodeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JaxbBasicSchemaComponentAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttachmentRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDREFAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaXmlNamedNodeMapping<A extends JaxbBasicSchemaComponentAnnotation>
		extends AbstractJavaXmlNodeMapping<A>
		implements XmlNamedNodeMapping {
	
	protected XmlID xmlID;
	
	protected XmlIDREF xmlIDREF;
	
	protected XmlAttachmentRef xmlAttachmentRef;
	
	
	public AbstractJavaXmlNamedNodeMapping(JavaPersistentAttribute parent) {
		super(parent);
		initializeXmlID();
		initializeXmlIDREF();
		initializeXmlAttachmentRef();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncXmlID();
		syncXmlIDREF();
		syncXmlAttachmentRef();
	}
	
	@Override
	public void update() {
		super.update();
		updateXmlID();
		updateXmlIDREF();
		updateXmlAttachmentRef();
	}
	
	
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
	
	
	// ***** validation *****

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		if (this.xmlID != null) {
			this.xmlID.validate(messages, reporter);
		}
		
		if (this.xmlIDREF != null) {
			this.xmlIDREF.validate(messages, reporter);
		}
		
		if (this.xmlAttachmentRef != null) {
			this.xmlAttachmentRef.validate(messages, reporter);
		}
	}
	
	
	protected abstract class XmlIDREFContext
			implements GenericJavaXmlIDREF.Context {
		
		public XmlIDREFAnnotation getAnnotation() {
			return AbstractJavaXmlNamedNodeMapping.this.getXmlIDREFAnnotation();
		}
		
		public boolean isList() {
			return AbstractJavaXmlNamedNodeMapping.this.isXmlList();
		}
	}
}
