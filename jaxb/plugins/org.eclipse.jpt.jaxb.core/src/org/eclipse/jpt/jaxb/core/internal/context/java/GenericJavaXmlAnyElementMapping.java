/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAnyElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementRef;
import org.eclipse.jpt.jaxb.core.context.XmlElementRefs;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlMixed;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAnyElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMixedAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlAnyElementMapping
		extends AbstractJavaAdaptableAttributeMapping<XmlAnyElementAnnotation>
		implements XmlAnyElementMapping {
	
	protected Boolean specifiedLax;
	
	protected String specifiedValue;
	
	protected final XmlElementRefs xmlElementRefs;
	
	protected XmlElementWrapper xmlElementWrapper;
	
	protected XmlMixed xmlMixed;
	
	
	public GenericJavaXmlAnyElementMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.specifiedLax = buildSpecifiedLax();
		this.specifiedValue = getResourceValueString();
		this.xmlElementRefs = buildXmlElementRefs();
		initializeXmlElementWrapper();
		initializeXmlMixed();			
	}
	
	
	public String getKey() {
		return MappingKeys.XML_ANY_ELEMENT_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return JAXB.XML_ANY_ELEMENT;
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setSpecifiedLax_(buildSpecifiedLax());
		setSpecifiedValue_(getResourceValueString());
		this.xmlElementRefs.synchronizeWithResourceModel();
		syncXmlElementWrapper();
		syncXmlMixed();
	}
	
	@Override
	public void update() {
		super.update();
		this.xmlElementRefs.update();
		updateXmlElementWrapper();
		updateXmlMixed();
	}
	
	
	// ***** lax *****
	
	public boolean isLax() {
		return (getSpecifiedLax() == null) ? isDefaultLax() : getSpecifiedLax().booleanValue();
	}
	
	public Boolean getSpecifiedLax() {
		return this.specifiedLax;
	}
	
	public void setSpecifiedLax(Boolean newSpecifiedLax) {
		getOrCreateAnnotation().setLax(newSpecifiedLax);
		setSpecifiedLax_(newSpecifiedLax);
	}
	
	protected void setSpecifiedLax_(Boolean newSpecifiedLax) {
		Boolean oldLax = this.specifiedLax;
		this.specifiedLax = newSpecifiedLax;
		firePropertyChanged(SPECIFIED_LAX_PROPERTY, oldLax, newSpecifiedLax);
	}
	
	protected Boolean buildSpecifiedLax() {
		return getAnnotation().getLax();
	}
	
	public boolean isDefaultLax() {
		return DEFAULT_LAX;
	}
	
	
	// ***** value *****
	
	public String getValue() {
		return getSpecifiedValue() == null ? getDefaultValue() : getSpecifiedValue();
	}
	
	public String getSpecifiedValue() {
		return this.specifiedValue;
	}
	
	public void setSpecifiedValue(String location) {
		getOrCreateAnnotation().setValue(location);
		setSpecifiedValue_(location);	
	}
	
	protected void setSpecifiedValue_(String type) {
		String old = this.specifiedValue;
		this.specifiedValue = type;
		firePropertyChanged(SPECIFIED_VALUE_PROPERTY, old, type);
	}
	
	protected String getResourceValueString() {
		return getAnnotation().getValue();
	}
	
	public String getDefaultValue() {
		return DEFAULT_VALUE;
	}
	
	
	// ***** XmlElementRefs *****
	
	public XmlElementRefs getXmlElementRefs() {
		return this.xmlElementRefs;
	}
	
	protected XmlElementRefs buildXmlElementRefs() {
		return new GenericJavaXmlElementRefs(this, new XmlElementRefsContext());
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
		getJavaResourceAttribute().addAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		XmlElementWrapper xmlElementWrapper = buildXmlElementWrapper();
		setXmlElementWrapper_(xmlElementWrapper);
		return xmlElementWrapper;
	}
	
	public void removeXmlElementWrapper() {
		if (this.xmlElementWrapper == null) {
			throw new IllegalStateException();
		}
		getJavaResourceAttribute().removeAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		setXmlElementWrapper_(null);
	}
	
	protected XmlElementWrapperAnnotation getXmlElementWrapperAnnotation() {
		return (XmlElementWrapperAnnotation) getJavaResourceAttribute().getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
	}
	
	protected XmlElementWrapper buildXmlElementWrapper() {
		return new GenericJavaXmlElementWrapper(this, new GenericJavaXmlElementWrapper.SimpleContext(getXmlElementWrapperAnnotation()));
	}
	
	protected void initializeXmlElementWrapper() {
		if (getXmlElementWrapperAnnotation() != null) {
			this.xmlElementWrapper = buildXmlElementWrapper();
		}
	}
	
	protected void syncXmlElementWrapper() {
		if (getXmlElementWrapperAnnotation() != null) {
			if (this.xmlElementWrapper != null) {
				this.xmlElementWrapper.synchronizeWithResourceModel();
			}
			else {
				setXmlElementWrapper_(buildXmlElementWrapper());
			}
		}
		else {
			setXmlElementWrapper_(null);
		}
	}
	
	protected void updateXmlElementWrapper() {
		if (this.xmlElementWrapper != null) {
			this.xmlElementWrapper.update();
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
		return this.xmlElementRefs.getReferencedXmlTypeNames();
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		result = this.xmlElementRefs.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		if (this.xmlElementWrapper != null) {
			result = this.xmlElementWrapper.getCompletionProposals(pos);
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
		
		this.xmlElementRefs.validate(messages, reporter);
		
		if (getJavaResourceAttribute().getAnnotation(JAXB.XML_ELEMENT_REFS) != null) {
			XmlElementRefAnnotation xmlElementRefAnnotation = 
					(XmlElementRefAnnotation) getJavaResourceAttribute().getAnnotation(JAXB.XML_ELEMENT_REF);
			if (xmlElementRefAnnotation != null) {
				messages.add(
						this.buildErrorValidationMessage(
								getPersistentAttribute(),
								xmlElementRefAnnotation.getTextRange(),
								JptJaxbCoreValidationMessages.ATTRIBUTE_MAPPING__UNSUPPORTED_ANNOTATION,
								JAXB.XML_ELEMENT_REF,
								JAXB.XML_ELEMENT_REFS));
			}
		}
		
		if (this.xmlElementWrapper != null) {
			this.xmlElementWrapper.validate(messages, reporter);
		}
		
		if (this.xmlMixed != null) {
			this.xmlMixed.validate(messages, reporter);
		}
	}
	
	protected class XmlElementRefsContext
			implements GenericJavaXmlElementRefs.Context {
		
		protected XmlElementRefsAnnotation getXmlElementRefsAnnotation() {
			return (XmlElementRefsAnnotation) GenericJavaXmlAnyElementMapping.this.getJavaResourceAttribute().getAnnotation(JAXB.XML_ELEMENT_REFS);
		}
		
		protected XmlElementRefsAnnotation addXmlElementRefsAnnotation() {
			return (XmlElementRefsAnnotation) GenericJavaXmlAnyElementMapping.this.getJavaResourceAttribute().addAnnotation(JAXB.XML_ELEMENT_REFS);
		}
		
		protected void removeXmlElementRefsAnnotation() {
			GenericJavaXmlAnyElementMapping.this.getJavaResourceAttribute().removeAnnotation(JAXB.XML_ELEMENT_REFS);
		}
		
		protected XmlElementRefAnnotation getXmlElementRefAnnotation() {
			return (XmlElementRefAnnotation) GenericJavaXmlAnyElementMapping.this.getJavaResourceAttribute().getAnnotation(JAXB.XML_ELEMENT_REF);
		}
		
		protected XmlElementRefAnnotation addXmlElementRefAnnotation() {
			return (XmlElementRefAnnotation) GenericJavaXmlAnyElementMapping.this.getJavaResourceAttribute().addAnnotation(JAXB.XML_ELEMENT_REF);
		}
		
		protected void removeXmlElementRefAnnotation() {
			GenericJavaXmlAnyElementMapping.this.getJavaResourceAttribute().removeAnnotation(JAXB.XML_ELEMENT_REF);
		}
		
		public ListIterable<XmlElementRefAnnotation> getXmlElementRefAnnotations() {
			XmlElementRefsAnnotation xmlElementRefsAnnotation = getXmlElementRefsAnnotation();
			if (xmlElementRefsAnnotation != null) {
				return xmlElementRefsAnnotation.getXmlElementRefs();
			}
			
			XmlElementRefAnnotation xmlElementRefAnnotation = getXmlElementRefAnnotation();
			if (xmlElementRefAnnotation != null) {
				return new SingleElementListIterable(xmlElementRefAnnotation);
			}
			
			return EmptyListIterable.instance();
		}
		
		public XmlElementRefAnnotation addXmlElementRefAnnotation(int index) {
			XmlElementRefsAnnotation xmlElementRefsAnnotation = getXmlElementRefsAnnotation();
			if (xmlElementRefsAnnotation != null) {
				return xmlElementRefsAnnotation.addXmlElementRef(index);
			}
			
			XmlElementRefAnnotation xmlElementRefAnnotation = getXmlElementRefAnnotation();
			if (xmlElementRefAnnotation != null) {
				if (index > 1) {
					throw new IllegalArgumentException(String.valueOf(index));
				}
				xmlElementRefsAnnotation = addXmlElementRefsAnnotation();
				XmlElementRefAnnotation xmlElementRefAnnotationCopy = xmlElementRefsAnnotation.addXmlElementRef(0);
				xmlElementRefAnnotationCopy.setName(xmlElementRefAnnotation.getName());
				xmlElementRefAnnotationCopy.setNamespace(xmlElementRefAnnotation.getNamespace());
				xmlElementRefAnnotationCopy.setRequired(xmlElementRefAnnotation.getRequired());
				xmlElementRefAnnotationCopy.setType(xmlElementRefAnnotation.getType());
				
				removeXmlElementRefAnnotation();
				
				return xmlElementRefsAnnotation.addXmlElementRef(index);
			}
			
			if (index > 0) {
				throw new IllegalArgumentException(String.valueOf(index));
			}
			return addXmlElementRefAnnotation();
		}
		
		public void removeXmlElementRefAnnotation(int index) {
			XmlElementRefsAnnotation xmlElementRefsAnnotation = getXmlElementRefsAnnotation();
			if (xmlElementRefsAnnotation != null) {
				xmlElementRefsAnnotation.removeXmlElementRef(index);
				if (xmlElementRefsAnnotation.getXmlElementRefsSize() == 0) {
					removeXmlElementRefsAnnotation();
				}
				else if (xmlElementRefsAnnotation.getXmlElementRefsSize() == 1) {
					XmlElementRefAnnotation xmlElementRefAnnotation = IterableTools.get(xmlElementRefsAnnotation.getXmlElementRefs(), 0);
					XmlElementRefAnnotation xmlElementRefAnnotationCopy = addXmlElementRefAnnotation();
					xmlElementRefAnnotationCopy.setName(xmlElementRefAnnotation.getName());
					xmlElementRefAnnotationCopy.setNamespace(xmlElementRefAnnotation.getNamespace());
					xmlElementRefAnnotationCopy.setRequired(xmlElementRefAnnotation.getRequired());
					xmlElementRefAnnotationCopy.setType(xmlElementRefAnnotation.getType());
					removeXmlElementRefsAnnotation();
				}
				return;
			}
			
			XmlElementRefAnnotation xmlElementRefAnnotation = getXmlElementRefAnnotation();
			if (xmlElementRefAnnotation != null) {
				if (index != 0) {
					throw new IllegalArgumentException(String.valueOf(index));
				}
				removeXmlElementRefAnnotation();
				return;
			}
			
			throw new IllegalArgumentException(String.valueOf(index));
		}
		
		public void moveXmlElementRefAnnotation(int targetIndex, int sourceIndex) {
			if (targetIndex == sourceIndex) {
				return;
			}
			
			XmlElementRefsAnnotation xmlElementRefsAnnotation = getXmlElementRefsAnnotation();
			if (xmlElementRefsAnnotation == null) {
				throw new IllegalArgumentException(String.valueOf(targetIndex) + ", " + String.valueOf(sourceIndex)); //$NON-NLS-1$
			}
			xmlElementRefsAnnotation.moveXmlElementRef(targetIndex, sourceIndex);
		}
		
		public XmlElementRef buildXmlElementRef(JaxbContextNode parent, XmlElementRefAnnotation annotation) {
			return new GenericJavaXmlElementRef(parent, new XmlElementRefContext(annotation));
		}
		
		public TextRange getValidationTextRange() {
			XmlElementRefsAnnotation xmlElementRefsAnnotation = getXmlElementRefsAnnotation();
			if (xmlElementRefsAnnotation != null) {
				return xmlElementRefsAnnotation.getTextRange();
			}
			
			XmlElementRefAnnotation xmlElementRefAnnotation = getXmlElementRefAnnotation();
			if (xmlElementRefAnnotation != null) {
				return xmlElementRefAnnotation.getTextRange();
			}
			
			return GenericJavaXmlAnyElementMapping.this.getValidationTextRange();
		}
	}
	
	
	protected class XmlElementRefContext
			implements GenericJavaXmlElementRef.Context {
		
		protected XmlElementRefAnnotation annotation;
		
		public XmlElementRefContext(XmlElementRefAnnotation annotation) {
			this.annotation = annotation;
		}
		
		public XmlElementRefAnnotation getAnnotation() {
			return this.annotation;
		}
		
		public JaxbAttributeMapping getAttributeMapping() {
			return GenericJavaXmlAnyElementMapping.this;
		}
		
		public String getDefaultType() {
			return null;
		}
		
		public XmlElementWrapper getElementWrapper() {
			return GenericJavaXmlAnyElementMapping.this.getXmlElementWrapper();
		}
	}
}
