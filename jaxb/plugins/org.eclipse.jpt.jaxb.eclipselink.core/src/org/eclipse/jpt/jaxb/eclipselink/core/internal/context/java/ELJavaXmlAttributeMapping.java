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
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlID;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlAttributeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlKey;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlPath;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.xpath.java.XPath;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlKeyAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlPathAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlAttributeMapping
		extends GenericJavaXmlAttributeMapping
		implements ELXmlAttributeMapping {
	
	protected ELJavaXmlPath xmlPath;
	
	protected ELJavaXmlKey xmlKey;
	
	
	public ELJavaXmlAttributeMapping(JaxbPersistentAttribute parent) {
		super(parent);
		initXmlPath();
		initXmlKey();
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
	
	
	// ***** misc *****
	
	public String getXPath() {
		if (this.xmlPath != null) {
			return this.xmlPath.getValue();
		}
		
		String name = this.qName.getName();
		if (StringTools.stringIsEmpty(name)) {
			// no name is invalid
			return null;
		}
		
		String namespace = this.qName.getNamespace();
		if (StringTools.stringIsEmpty(namespace)) {
			// empty namespace means "no" namespace
			return XPath.attributeXPath(null, name);
		}
		
		String prefix = getJaxbPackage().getPackageInfo().getPrefixForNamespace(namespace);
		if (prefix == null) {
			// no prefix for non-null namespace is invalid
			return null;
		}
		
		return XPath.attributeXPath(prefix, this.qName.getName());
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		if (this.xmlPath != null) {
			result = this.xmlPath.getJavaCompletionProposals(pos, filter, astRoot);
			if (! CollectionTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	protected void validateQName(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (this.xmlPath == null) {
			super.validateQName(messages, reporter, astRoot);
		}
		else {
			validateXmlPath(messages, reporter, astRoot);
		}
	}
	
	protected void validateXmlPath(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		this.xmlPath.validate(messages, reporter, astRoot);
	}
	
	
	protected class XmlPathContext
			implements ELJavaXmlPath.Context {
		
		public XmlPathAnnotation getAnnotation() {
			return ELJavaXmlAttributeMapping.this.getXmlPathAnnotation();
		}
		
		public JaxbAttributeMapping getAttributeMapping() {
			return ELJavaXmlAttributeMapping.this;
		}
	}
	
	
	protected class XmlKeyContext
			implements ELJavaXmlKey.Context {
		
		public XmlKeyAnnotation getAnnotation() {
			return ELJavaXmlAttributeMapping.this.getXmlKeyAnnotation();
		}
	}
}