/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlElement;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementMapping;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlElementMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlPath;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlPathAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlElementMapping
		extends GenericJavaXmlElementMapping
		implements ELXmlElementMapping {
	
	protected ELJavaXmlPath xmlPath;
	
	
	public ELJavaXmlElementMapping(JaxbPersistentAttribute parent) {
		super(parent);
		initXmlPath();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncXmlPath();
	}
	
	
	// ***** XmlPath *****
	
	public ELXmlPath getXmlPath() {
		return this.xmlPath;
	}
	
	protected void setXmlPath_(ELJavaXmlPath xmlPath) {
		ELJavaXmlPath old = this.xmlPath;
		this.xmlPath = xmlPath;
		firePropertyChanged(XML_PATH_PROPERTY, old, this.xmlPath);
	}
	
	public ELXmlPath addXmlPath() {
		if (this.xmlPath != null) {
			throw new IllegalStateException();
		}
		getJavaResourceAttribute().addAnnotation(0, ELJaxb.XML_PATH);
		ELJavaXmlPath xmlPath = buildXmlPath();
		setXmlPath_(xmlPath);
		return xmlPath;
	}
	
	public void removeXmlPath() {
		if (this.xmlPath == null) {
			throw new IllegalStateException();
		}
		while (getXmlPathAnnotation() != null) {
			getJavaResourceAttribute().removeAnnotation(0, ELJaxb.XML_PATH);
		}
		setXmlPath_(null);
	}
	
	protected void initXmlPath() {
		XmlPathAnnotation annotation = getXmlPathAnnotation();
		this.xmlPath = (annotation == null) ? null : buildXmlPath();
	}
	
	protected void syncXmlPath() {
		XmlPathAnnotation annotation = getXmlPathAnnotation();
		if (annotation != null) {
			if (this.xmlPath == null) {
				setXmlPath_(buildXmlPath());
			}
			else {
				this.xmlPath.synchronizeWithResourceModel();
			}
		}
		else {
			setXmlPath_(null);
		}
	}
	
	protected ELJavaXmlPath buildXmlPath() {
		return new ELJavaXmlPath(this, new XmlPathContext());
	}
	
	protected XmlPathAnnotation getXmlPathAnnotation() {
		return (XmlPathAnnotation) getJavaResourceAttribute().getAnnotation(0, ELJaxb.XML_PATH);
	}
	
	
	// ***** misc *****
	
	@Override
	protected XmlElement buildXmlElement() {
		return new ELJavaXmlElement(this, new XmlElementContext());
	}
	
	@Override
	protected XmlElementWrapper buildXmlElementWrapper() {
		return new ELJavaXmlElementWrapper(this, new XmlElementWrapperContext());
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		if (this.xmlPath != null) {
			validateXmlPath(messages, reporter, astRoot);
		}
	}
	
	protected void validateXmlPath(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		this.xmlPath.validate(messages, reporter, astRoot);
	}
	
	
	protected class XmlPathContext
			implements ELJavaXmlPath.Context {
		
		public XmlPathAnnotation getAnnotation() {
			return ELJavaXmlElementMapping.this.getXmlPathAnnotation();
		}
	}
	
	
	protected class XmlElementContext
			extends GenericJavaXmlElementMapping.XmlElementContext
			implements ELJavaXmlElement.Context {
		
		public boolean hasXmlPath() {
			return ELJavaXmlElementMapping.this.xmlPath != null;
		}
	}
	
	
	protected class XmlElementWrapperContext
			implements ELJavaXmlElementWrapper.Context {
		
		public XmlElementWrapperAnnotation getAnnotation() {
			return ELJavaXmlElementMapping.this.getXmlElementWrapperAnnotation();
		}
		
		public boolean hasXmlPath() {
			return ELJavaXmlElementMapping.this.xmlPath != null;
		}
	}
}
