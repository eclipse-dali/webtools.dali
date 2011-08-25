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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAdaptable;
import org.eclipse.jpt.jaxb.core.context.XmlElementRef;
import org.eclipse.jpt.jaxb.core.context.XmlElementRefMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaXmlElementRefMapping
		extends AbstractJavaAttributeMapping<XmlElementRefAnnotation>
		implements XmlElementRefMapping {
	
	protected final XmlElementRef xmlElementRef;
	
	protected final XmlAdaptable xmlAdaptable;
	
	protected XmlElementWrapper xmlElementWrapper;
	
	
	public GenericJavaXmlElementRefMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.xmlElementRef = buildXmlElementRef();
		this.xmlAdaptable = buildXmlAdaptable();
		this.initializeXmlElementWrapper();			
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
		this.xmlAdaptable.synchronizeWithResourceModel();
		syncXmlElementWrapper();
	}
	
	@Override
	public void update() {
		super.update();
		this.xmlElementRef.update();
		this.xmlAdaptable.update();
		updateXmlElementWrapper();
	}
	
	
	// ***** XmlElementRef *****
	
	public XmlElementRef getXmlElementRef() {
		return this.xmlElementRef;
	}
	
	protected XmlElementRef buildXmlElementRef() {
		return new GenericJavaXmlElementRef(this, new XmlElementRefContext());
	}
	
	
	// ***** XmlJavaTypeAdapter *****

	public XmlJavaTypeAdapter getXmlJavaTypeAdapter() {
		return this.xmlAdaptable.getXmlJavaTypeAdapter();
	}

	public XmlJavaTypeAdapter addXmlJavaTypeAdapter() {
		return this.xmlAdaptable.addXmlJavaTypeAdapter();
	}

	public void removeXmlJavaTypeAdapter() {
		this.xmlAdaptable.removeXmlJavaTypeAdapter();
	}

	protected XmlAdaptable buildXmlAdaptable() {
		return new GenericJavaXmlAdaptable(this, new XmlAdaptable.Owner() {
			public JavaResourceAnnotatedElement getResource() {
				return getJavaResourceAttribute();
			}
			public XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation adapterAnnotation) {
				return GenericJavaXmlElementRefMapping.this.buildXmlJavaTypeAdapter(adapterAnnotation);
			}
			public void fireXmlAdapterChanged(XmlJavaTypeAdapter oldAdapter, XmlJavaTypeAdapter newAdapter) {
				GenericJavaXmlElementRefMapping.this.firePropertyChanged(XML_JAVA_TYPE_ADAPTER_PROPERTY, oldAdapter, newAdapter);
			}
		});
	}

	protected XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation) {
		return new GenericJavaAttributeXmlJavaTypeAdapter(this, xmlJavaTypeAdapterAnnotation);
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
		
		this.xmlAdaptable.validate(messages, reporter, astRoot);
		
		if (this.getXmlElementWrapper() != null) {
			this.getXmlElementWrapper().validate(messages, reporter, astRoot);
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
