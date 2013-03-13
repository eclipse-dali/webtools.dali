/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlValueMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlCDATA;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlValueMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlCDATAAnnotation;


public class ELJavaXmlValueMapping
		extends GenericJavaXmlValueMapping
		implements ELXmlValueMapping {
	
	protected ELJavaXmlCDATA xmlCDATA;
	
	
	public ELJavaXmlValueMapping(JavaPersistentAttribute parent) {
		super(parent);
		initXmlCDATA();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncXmlCDATA();
	}
	
	
	// ***** XmlCDATA *****
	
	public ELXmlCDATA getXmlCDATA() {
		return this.xmlCDATA;
	}
	
	protected void setXmlCDATA_(ELJavaXmlCDATA xmlCDATA) {
		ELJavaXmlCDATA old = this.xmlCDATA;
		this.xmlCDATA = xmlCDATA;
		firePropertyChanged(XML_CDATA_PROPERTY, old, this.xmlCDATA);
	}
	
	public ELXmlCDATA addXmlCDATA() {
		if (this.xmlCDATA != null) {
			throw new IllegalStateException();
		}
		getJavaResourceAttribute().addAnnotation(ELJaxb.XML_CDATA);
		ELJavaXmlCDATA xmlCDATA = buildXmlCDATA();
		setXmlCDATA_(xmlCDATA);
		return xmlCDATA;
	}
	
	public void removeXmlCDATA() {
		if (this.xmlCDATA == null) {
			throw new IllegalStateException();
		}
		getJavaResourceAttribute().removeAnnotation(ELJaxb.XML_CDATA);
		setXmlCDATA_(null);
	}
	
	protected void initXmlCDATA() {
		XmlCDATAAnnotation annotation = getXmlCDATAAnnotation();
		this.xmlCDATA = (annotation == null) ? null : buildXmlCDATA();
	}
	
	protected void syncXmlCDATA() {
		XmlCDATAAnnotation annotation = getXmlCDATAAnnotation();
		if (annotation != null) {
			if (this.xmlCDATA == null) {
				setXmlCDATA_(buildXmlCDATA());
			}
		}
		else {
			setXmlCDATA_(null);
		}
	}
	
	protected ELJavaXmlCDATA buildXmlCDATA() {
		return new ELJavaXmlCDATA(this, new XmlCDATAContext());
	}
	
	protected XmlCDATAAnnotation getXmlCDATAAnnotation() {
		return (XmlCDATAAnnotation) getJavaResourceAttribute().getAnnotation(ELJaxb.XML_CDATA);
	}
	
	
	protected class XmlCDATAContext
			implements ELJavaXmlCDATA.Context {
		
		public XmlCDATAAnnotation getAnnotation() {
			return ELJavaXmlValueMapping.this.getXmlCDATAAnnotation();
		}
	}
}
