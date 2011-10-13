/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlElementRef;
import org.eclipse.jpt.jaxb.core.context.XmlElementRefMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlMixed;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMixedAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaXmlElementRefMapping
		extends AbstractJavaAdaptableAttributeMapping<XmlElementRefAnnotation>
		implements XmlElementRefMapping {
	
	protected final XmlElementRef xmlElementRef;
	
	protected XmlElementWrapper xmlElementWrapper;
	
	protected XmlMixed xmlMixed;
	
	
	public GenericJavaXmlElementRefMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.xmlElementRef = buildXmlElementRef();
		initializeXmlElementWrapper();			
		initializeXmlMixed();			
	}
	
	
	public String getKey() {
		return MappingKeys.XML_ELEMENT_REF_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return JAXB.XML_ELEMENT_REF;
	}
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.xmlElementRef.synchronizeWithResourceModel();
		syncXmlElementWrapper();
		syncXmlMixed();
	}
	
	@Override
	public void update() {
		super.update();
		this.xmlElementRef.update();
		updateXmlElementWrapper();
		updateXmlMixed();
	}
	
	
	// ***** XmlElementRef *****
	
	public XmlElementRef getXmlElementRef() {
		return this.xmlElementRef;
	}
	
	protected XmlElementRef buildXmlElementRef() {
		return new GenericJavaXmlElementRef(this, new XmlElementRefContext());
	}
	
	
	// ***** XmlElementWrapper *****
	
	public XmlElementWrapper getXmlElementWrapper() {
		return this.xmlElementWrapper;
	}
	
	protected void setXmlElementWrapper_(XmlElementWrapper xmlElementWrapper) {
		XmlElementWrapper oldXmlElementWrapper = this.xmlElementWrapper;
		this.xmlElementWrapper = xmlElementWrapper;
		firePropertyChanged(XML_ELEMENT_WRAPPER_PROPERTY, oldXmlElementWrapper, xmlElementWrapper);
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
	
	public void removeXmlElementWrapper() {
		if (this.xmlElementWrapper == null) {
			throw new IllegalStateException();
		}
		this.getJavaResourceAttribute().removeAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		this.setXmlElementWrapper_(null);
	}
	
	protected XmlElementWrapperAnnotation getXmlElementWrapperAnnotation() {
		return (XmlElementWrapperAnnotation) this.getJavaResourceAttribute().getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
	}
	
	protected XmlElementWrapper buildXmlElementWrapper(XmlElementWrapperAnnotation xmlElementWrapperAnnotation) {
		return new GenericJavaXmlElementWrapper(this, xmlElementWrapperAnnotation);
	}
	
	protected void initializeXmlElementWrapper() {
		XmlElementWrapperAnnotation annotation = this.getXmlElementWrapperAnnotation();
		if (annotation != null) {
			this.xmlElementWrapper = this.buildXmlElementWrapper(annotation);
		}
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
	
	
	// ***** XmlMixed *****
	
	public XmlMixed getXmlMixed() {
		return this.xmlMixed;
	}
	
	protected void setXmlMixed_(XmlMixed xmlMixed) {
		XmlMixed oldXmlMixed = this.xmlMixed;
		this.xmlMixed = xmlMixed;
		firePropertyChanged(XML_MIXED_PROPERTY, oldXmlMixed, xmlMixed);
	}
	
	public XmlMixed addXmlMixed() {
		if (this.xmlMixed != null) {
			throw new IllegalStateException();
		}
		XmlMixedAnnotation annotation = (XmlMixedAnnotation) getJavaResourceAttribute().addAnnotation(JAXB.XML_MIXED);
		
		XmlMixed xmlMixed = buildXmlMixed(annotation);
		setXmlMixed_(xmlMixed);
		return xmlMixed;
	}
	
	public void removeXmlMixed() {
		if (this.xmlMixed == null) {
			throw new IllegalStateException();
		}
		getJavaResourceAttribute().removeAnnotation(JAXB.XML_MIXED);
		setXmlMixed_(null);
	}
	
	protected XmlMixedAnnotation getXmlMixedAnnotation() {
		return (XmlMixedAnnotation) getJavaResourceAttribute().getAnnotation(JAXB.XML_MIXED);
	}
	
	protected XmlMixed buildXmlMixed(XmlMixedAnnotation xmlMixedAnnotation) {
		return new GenericJavaXmlMixed(this, xmlMixedAnnotation);
	}
	
	protected void initializeXmlMixed() {
		XmlMixedAnnotation annotation = getXmlMixedAnnotation();
		if (annotation != null) {
			this.xmlMixed = buildXmlMixed(annotation);
		}
	}
	
	protected void syncXmlMixed() {
		XmlMixedAnnotation annotation = getXmlMixedAnnotation();
		if (annotation != null) {
			if (this.xmlMixed != null) {
				this.xmlMixed.synchronizeWithResourceModel();
			}
			else {
				setXmlMixed_(buildXmlMixed(annotation));
			}
		}
		else {
			setXmlMixed_(null);
		}
	}

	protected void updateXmlMixed() {
		if (this.xmlMixed != null) {
			this.xmlMixed.update();
		}
	}
	
	
	// ***** misc *****
	
	@Override
	public Iterable<String> getReferencedXmlTypeNames() {
		return this.xmlElementRef.getReferencedXmlTypeNames();
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		result = this.xmlElementRef.getJavaCompletionProposals(pos, filter, astRoot);
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
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		this.xmlElementRef.validate(messages, reporter, astRoot);
		
		if (this.xmlElementWrapper != null) {
			this.xmlElementWrapper.validate(messages, reporter, astRoot);
		}
	}
	
	
	public class XmlElementRefContext
			implements GenericJavaXmlElementRef.Context {
		
		public XmlElementRefAnnotation getAnnotation() {
			return GenericJavaXmlElementRefMapping.this.getAnnotation();
		}
		
		public JaxbAttributeMapping getAttributeMapping() {
			return GenericJavaXmlElementRefMapping.this;
		}
		
		public String getDefaultType() {
			return GenericJavaXmlElementRefMapping.this.getPersistentAttribute().getJavaResourceAttributeBaseTypeName();
		}
		
		public XmlElementWrapper getElementWrapper() {
			return GenericJavaXmlElementRefMapping.this.getXmlElementWrapper();
		}
	}
}
