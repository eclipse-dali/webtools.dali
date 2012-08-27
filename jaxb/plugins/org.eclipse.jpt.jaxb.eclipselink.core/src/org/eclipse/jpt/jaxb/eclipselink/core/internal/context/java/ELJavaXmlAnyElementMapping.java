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
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlElementRef;
import org.eclipse.jpt.jaxb.core.context.XmlElementRefs;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlAnyElementMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementRefs;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlAnyElementMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlPath;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlPathAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlAnyElementMapping
		extends GenericJavaXmlAnyElementMapping 
		implements ELXmlAnyElementMapping {
	
	protected ELJavaXmlPath xmlPath;
	
	
	public ELJavaXmlAnyElementMapping(JaxbPersistentAttribute parent) {
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
		if (getJavaResourceAttribute().getAnnotationsSize(ELJaxb.XML_PATH) > 0) {
			return (XmlPathAnnotation) getJavaResourceAttribute().getAnnotation(0, ELJaxb.XML_PATH);
		}
		return null;
	}
	
	
	// ***** misc *****
	
	@Override
	protected XmlElementWrapper buildXmlElementWrapper() {
		return new ELJavaXmlElementWrapper(this, new XmlElementWrapperContext());
	}
	
	@Override
	protected XmlElementRefs buildXmlElementRefs() {
		return new GenericJavaXmlElementRefs(this, new XmlElementRefsContext());
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
			return ELJavaXmlAnyElementMapping.this.getXmlPathAnnotation();
		}
		
		public JaxbAttributeMapping getAttributeMapping() {
			return ELJavaXmlAnyElementMapping.this;
		}
	}
	
	
	protected class XmlElementWrapperContext
			implements ELJavaXmlElementWrapper.Context {
		
		public XmlElementWrapperAnnotation getAnnotation() {
			return ELJavaXmlAnyElementMapping.this.getXmlElementWrapperAnnotation();
		}
		
		public boolean hasXmlPath() {
			return ELJavaXmlAnyElementMapping.this.xmlPath != null;
		}
	}
	
	
	protected class XmlElementRefsContext
			extends GenericJavaXmlAnyElementMapping.XmlElementRefsContext {
		
		@Override
		public XmlElementRef buildXmlElementRef(JavaContextNode parent, XmlElementRefAnnotation annotation) {
			return new ELJavaXmlElementRef(parent, new GenericJavaXmlAnyElementMapping.XmlElementRefContext(annotation));
		}
	}
}
