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
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlElement;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlElementMapping
		extends GenericJavaBasicMapping<XmlElementAnnotation>
		implements XmlElementMapping {
	
	protected final XmlElement xmlElement;
	
	protected XmlElementWrapper xmlElementWrapper;
	
	
	public GenericJavaXmlElementMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.xmlElement = buildXmlElement();
		this.initializeXmlElementWrapper();			
	}
	
	
	
	public String getKey() {
		return MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return JAXB.XML_ELEMENT;
	}
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.xmlElement.synchronizeWithResourceModel();
		syncXmlElementWrapper();
	}
	
	@Override
	public void update() {
		super.update();
		this.xmlElement.update();
		updateXmlElementWrapper();
	}
	
	
	// ***** XmlElement *****
	
	public XmlElement getXmlElement() {
		return this.xmlElement;
	}
	
	protected XmlElement buildXmlElement() {
		return new GenericJavaXmlElement(this, new XmlElementContext());
	}
	
	
	// ***** XmlElementWrapper *****
	
	public XmlElementWrapper getXmlElementWrapper() {
		return this.xmlElementWrapper;
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
	
	protected XmlElementWrapper buildXmlElementWrapper(XmlElementWrapperAnnotation xmlElementWrapperAnnotation) {
		return new GenericJavaXmlElementWrapper(this, xmlElementWrapperAnnotation);
	}
	
	public void removeXmlElementWrapper() {
		if (this.xmlElementWrapper == null) {
			throw new IllegalStateException();
		}
		this.getJavaResourceAttribute().removeAnnotation(XmlElementWrapperAnnotation.ANNOTATION_NAME);
		this.setXmlElementWrapper_(null);
	}
	
	protected void initializeXmlElementWrapper() {
		XmlElementWrapperAnnotation annotation = this.getXmlElementWrapperAnnotation();
		if (annotation != null) {
			this.xmlElementWrapper = this.buildXmlElementWrapper(annotation);
		}
	}
	
	protected XmlElementWrapperAnnotation getXmlElementWrapperAnnotation() {
		return (XmlElementWrapperAnnotation) this.getJavaResourceAttribute().getAnnotation(XmlElementWrapperAnnotation.ANNOTATION_NAME);
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
	
	protected void setXmlElementWrapper_(XmlElementWrapper xmlElementWrapper) {
		XmlElementWrapper oldXmlElementWrapper = this.xmlElementWrapper;
		this.xmlElementWrapper = xmlElementWrapper;
		firePropertyChanged(XML_ELEMENT_WRAPPER_PROPERTY, oldXmlElementWrapper, xmlElementWrapper);
	}
	
	
	// ***** misc *****
	
	@Override
	public Iterable<String> getDirectlyReferencedTypeNames() {
		return new CompositeIterable<String>(
				super.getDirectlyReferencedTypeNames(),
				this.xmlElement.getDirectlyReferencedTypeNames());
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		result = this.xmlElement.getJavaCompletionProposals(pos, filter, astRoot);
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
		
		this.xmlElement.validate(messages, reporter, astRoot);
		
		if (this.getXmlElementWrapper() != null) {
			this.getXmlElementWrapper().validate(messages, reporter, astRoot);
		}
	}
	
	
	public class XmlElementContext
			implements GenericJavaXmlElement.Context {
		
		public XmlElementAnnotation getAnnotation(boolean createIfNull) {
			return (createIfNull) ? 
					GenericJavaXmlElementMapping.this.getOrCreateAnnotation() 
					: GenericJavaXmlElementMapping.this.getAnnotation();
		}
		
		public JaxbAttributeMapping getAttributeMapping() {
			return GenericJavaXmlElementMapping.this;
		}
		
		public String getDefaultType() {
			return GenericJavaXmlElementMapping.this.getPersistentAttribute().getJavaResourceAttributeBaseTypeName();
		}
		
		public XmlElementWrapper getElementWrapper() {
			return GenericJavaXmlElementMapping.this.getXmlElementWrapper();
		}
	}
}
