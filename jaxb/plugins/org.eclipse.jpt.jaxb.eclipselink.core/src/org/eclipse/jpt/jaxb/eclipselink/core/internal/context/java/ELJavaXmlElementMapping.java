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

import java.util.List;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElement;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlID;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementMapping;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlCDATA;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlElementMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlKey;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlPath;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.xpath.java.XPath;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlCDATAAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlKeyAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlPathAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlElementMapping
		extends GenericJavaXmlElementMapping
		implements ELXmlElementMapping {
	
	protected ELJavaXmlPath xmlPath;
	
	protected ELJavaXmlKey xmlKey;
	
	protected ELJavaXmlCDATA xmlCDATA;
	
	
	public ELJavaXmlElementMapping(JavaPersistentAttribute parent) {
		super(parent);
		initXmlPath();
		initXmlKey();
		initXmlCDATA();
	}
	
	
	@Override
	protected XmlID buildXmlID(XmlIDAnnotation xmlIDAnnotation) {
		return new ELJavaXmlID(this, xmlIDAnnotation);
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncXmlPath();
		syncXmlKey();
		syncXmlCDATA();
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
		if (getJavaResourceAttribute().getAnnotationsSize(ELJaxb.XML_PATH) > 0) {
			return (XmlPathAnnotation) getJavaResourceAttribute().getAnnotation(0, ELJaxb.XML_PATH);
		}
		return null;
	}
	
	
	// ***** XmlKey *****
	
	public ELXmlKey getXmlKey() {
		return this.xmlKey;
	}
	
	protected void setXmlKey_(ELJavaXmlKey xmlKey) {
		ELJavaXmlKey old = this.xmlKey;
		this.xmlKey = xmlKey;
		firePropertyChanged(XML_KEY_PROPERTY, old, this.xmlKey);
	}
	
	public ELXmlKey addXmlKey() {
		if (this.xmlKey != null) {
			throw new IllegalStateException();
		}
		getJavaResourceAttribute().addAnnotation(ELJaxb.XML_KEY);
		ELJavaXmlKey xmlKey = buildXmlKey();
		setXmlKey_(xmlKey);
		return xmlKey;
	}
	
	public void removeXmlKey() {
		if (this.xmlKey == null) {
			throw new IllegalStateException();
		}
		getJavaResourceAttribute().removeAnnotation(ELJaxb.XML_KEY);
		setXmlKey_(null);
	}
	
	protected void initXmlKey() {
		XmlKeyAnnotation annotation = getXmlKeyAnnotation();
		this.xmlKey = (annotation == null) ? null : buildXmlKey();
	}
	
	protected void syncXmlKey() {
		XmlKeyAnnotation annotation = getXmlKeyAnnotation();
		if (annotation != null) {
			if (this.xmlKey == null) {
				setXmlKey_(buildXmlKey());
			}
		}
		else {
			setXmlKey_(null);
		}
	}
	
	protected ELJavaXmlKey buildXmlKey() {
		return new ELJavaXmlKey(this, new XmlKeyContext());
	}
	
	protected XmlKeyAnnotation getXmlKeyAnnotation() {
		return (XmlKeyAnnotation) getJavaResourceAttribute().getAnnotation(ELJaxb.XML_KEY);
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
	
	
	// ***** misc *****
	
	@Override
	protected XmlElement buildXmlElement() {
		return new ELJavaXmlElement(this, new XmlElementContext());
	}
	
	@Override
	protected XmlElementWrapper buildXmlElementWrapper() {
		return new ELJavaXmlElementWrapper(this, new XmlElementWrapperContext());
	}
	
	public String getXPath() {
		if (this.xmlPath != null) {
			return this.xmlPath.getValue();
		}
		
		String name = this.xmlElement.getQName().getName();
		if (StringTools.isBlank(name)) {
			// no name is invalid
			return null;
		}
		
		String namespace = this.xmlElement.getQName().getNamespace();
		if (StringTools.isBlank(namespace)) {
			// empty namespace means "no" namespace
			return XPath.elementXPath(null, name);
		}
		
		String prefix = getJaxbPackage().getPackageInfo().getPrefixForNamespace(namespace);
		if (prefix == null) {
			// no prefix for non-null namespace is invalid
			return null;
		}
		
		return XPath.elementXPath(prefix, name);
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		if (this.xmlPath != null) {
			result = this.xmlPath.getCompletionProposals(pos);
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
		
		if (this.xmlPath != null) {
			validateXmlPath(messages, reporter);
		}
	}
	
	protected void validateXmlPath(List<IMessage> messages, IReporter reporter) {
		this.xmlPath.validate(messages, reporter);
	}
	
	
	protected class XmlPathContext
			implements ELJavaXmlPath.Context {
		
		public XmlPathAnnotation getAnnotation() {
			return ELJavaXmlElementMapping.this.getXmlPathAnnotation();
		}
		
		public JaxbAttributeMapping getAttributeMapping() {
			return ELJavaXmlElementMapping.this;
		}
	}
	
	
	protected class XmlKeyContext
			implements ELJavaXmlKey.Context {
		
		public XmlKeyAnnotation getAnnotation() {
			return ELJavaXmlElementMapping.this.getXmlKeyAnnotation();
		}
	}
	
	
	protected class XmlCDATAContext
			implements ELJavaXmlCDATA.Context {
		
		public XmlCDATAAnnotation getAnnotation() {
			return ELJavaXmlElementMapping.this.getXmlCDATAAnnotation();
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
