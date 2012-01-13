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
import javax.xml.namespace.QName;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.Bag;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.HashBag;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlElement;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlElementsMapping;
import org.eclipse.jpt.jaxb.core.context.XmlIDREF;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlIDREF.ValidatableReference;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDREFAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdFeature;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaXmlElementsMapping
		extends AbstractJavaAdaptableAttributeMapping<XmlElementsAnnotation> 
		implements XmlElementsMapping {
	
	protected final ContextListContainer<XmlElement, XmlElementAnnotation> xmlElementContainer;
	
	protected XmlElementWrapper xmlElementWrapper;
	
	protected XmlIDREF xmlIDREF;
	
	
	public GenericJavaXmlElementsMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.xmlElementContainer = buildXmlElementContainer();
		initializeXmlElementWrapper();
		initializeXmlIDREF();
	}
	
	
	public String getKey() {
		return MappingKeys.XML_ELEMENTS_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return JAXB.XML_ELEMENTS;
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.xmlElementContainer.synchronizeWithResourceModel();
		syncXmlElementWrapper();
		syncXmlIDREF();
	}
	
	@Override
	public void update() {
		super.update();
		this.xmlElementContainer.update();
		updateXmlElementWrapper();
		updateXmlIDREF();
	}
	
	
	// ***** xml elements *****
	
	public ListIterable<XmlElement> getXmlElements() {
		return this.xmlElementContainer.getContextElements();
	}
	
	public int getXmlElementsSize() {
		return this.xmlElementContainer.getContextElementsSize();
	}
	
	public XmlElement addXmlElement(int index) {
		XmlElementAnnotation annotation = getAnnotation().addXmlElement(index);
		return this.xmlElementContainer.addContextElement(index, annotation);
	}
	
	public void removeXmlElement(int index) {
		getAnnotation().removeXmlElement(index);
		this.xmlElementContainer.removeContextElement(index);
	}
	
	public void removeXmlElement(XmlElement xmlElement) {
		removeXmlElement(this.xmlElementContainer.indexOfContextElement(xmlElement));
	}
	
	public void moveXmlElement(int targetIndex, int sourceIndex) {
		getAnnotation().moveXmlElement(targetIndex, sourceIndex);
		this.xmlElementContainer.moveContextElement(targetIndex, sourceIndex);
	}
	
	protected XmlElement buildXmlElement(XmlElementAnnotation xmlElementAnnotation) {
		return new GenericJavaXmlElement(this, new XmlElementContext(xmlElementAnnotation));
	}
	
	protected ListIterable<XmlElementAnnotation> getXmlElementAnnotations() {
		return getAnnotation().getXmlElements();
	}
	
	
	// ***** XmlElementWrapper *****
	
	public XmlElementWrapper getXmlElementWrapper() {
		return this.xmlElementWrapper;
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
	
	protected void setXmlElementWrapper_(XmlElementWrapper xmlElementWrapper) {
		XmlElementWrapper oldXmlElementWrapper = this.xmlElementWrapper;
		this.xmlElementWrapper = xmlElementWrapper;
		firePropertyChanged(XML_ELEMENT_WRAPPER_PROPERTY, oldXmlElementWrapper, xmlElementWrapper);
	}
	
	protected XmlElementWrapper buildXmlElementWrapper() {
		return new GenericJavaXmlElementWrapper(this, new GenericJavaXmlElementWrapper.SimpleContext(getXmlElementWrapperAnnotation()));
	}
	
	protected XmlElementWrapperAnnotation getXmlElementWrapperAnnotation() {
		return (XmlElementWrapperAnnotation) getJavaResourceAttribute().getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
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

	protected ContextListContainer<XmlElement, XmlElementAnnotation> buildXmlElementContainer() {
		XmlElementContainer container = new XmlElementContainer();
		container.initialize();
		return container;
	}
	
	
	// ***** XmlIDREF *****
	
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
	
	protected XmlIDREFContext buildXmlIDREFContext() {
		return new XmlIDREFContext();
	}
	
	
	// ***** misc *****
	
	@Override
	public Iterable<String> getReferencedXmlTypeNames() {
		return new CompositeIterable<String>(
				super.getReferencedXmlTypeNames(),
				new CompositeIterable<String>(
						new TransformationIterable<XmlElement, Iterable<String>>(getXmlElements()) {
							@Override
							protected Iterable<String> transform(XmlElement o) {
								return o.getReferencedXmlTypeNames();
							}
						}));
	}
	
	@Override
	public boolean isParticleMapping() {
		return true;
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		for (XmlElement xmlElement : getXmlElements()) {
			result = xmlElement.getJavaCompletionProposals(pos, filter, astRoot);
			if (! CollectionTools.isEmpty(result)) {
				return result;
			}
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
		
		validateDuplicateTypesAndQNames(messages, reporter, astRoot);
		
		for (XmlElement xmlElement : getXmlElements()) {
			xmlElement.validate(messages, reporter, astRoot);
		}
		
		if (this.xmlElementWrapper != null) {
			this.xmlElementWrapper.validate(messages, reporter, astRoot);
		}
		
		if (this.xmlIDREF != null) {
			this.xmlIDREF.validate(messages, reporter, astRoot);
		}
	}
	
	protected void validateDuplicateTypesAndQNames(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		Bag<String> xmlElementTypes = new HashBag<String>();
		Bag<QName> xmlElementQnames = new HashBag<QName>();
		
		for (XmlElement xmlElement : getXmlElements()) {
			String typeName = xmlElement.getFullyQualifiedType();
			if (! StringTools.stringIsEmpty(typeName)) {
				xmlElementTypes.add(typeName);
			}
			String elementName = xmlElement.getQName().getName();
			if (! StringTools.stringIsEmpty(elementName)) {
				xmlElementQnames.add(new QName(xmlElement.getQName().getNamespace(), elementName));
			}
		}
		
		for (XmlElement xmlElement : getXmlElements()) {
			String xmlElementType = xmlElement.getFullyQualifiedType();
			if (xmlElementTypes.count(xmlElementType) > 1) {
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.XML_ELEMENTS__DUPLICATE_XML_ELEMENT_TYPE,
								new String[] { xmlElementType },
								xmlElement,
								xmlElement.getTypeTextRange(astRoot)));
			}
			
			String xmlElementNamespace = xmlElement.getQName().getNamespace();
			String xmlElementName = xmlElement.getQName().getName();
			if (xmlElementQnames.count(new QName(xmlElementNamespace, xmlElementName)) > 1) {
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.XML_ELEMENTS__DUPLICATE_XML_ELEMENT_QNAME,
								new String[] { xmlElementName },
								xmlElement,
								xmlElement.getQName().getNameTextRange(astRoot)));
			}
		}
	}
	
	
	protected class XmlElementContainer
			extends ContextListContainer<XmlElement, XmlElementAnnotation> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return XmlElementsMapping.XML_ELEMENTS_LIST;
		}
		
		@Override
		protected XmlElement buildContextElement(XmlElementAnnotation resourceElement) {
			return GenericJavaXmlElementsMapping.this.buildXmlElement(resourceElement);
		}
		
		@Override
		protected ListIterable<XmlElementAnnotation> getResourceElements() {
			return GenericJavaXmlElementsMapping.this.getXmlElementAnnotations();
		}
		
		@Override
		protected XmlElementAnnotation getResourceElement(XmlElement contextElement) {
			// in the context of this mapping, there will never be an XmlElement without an annotation
			return contextElement.getAnnotation(false);
		}
	}
	
	
	protected class XmlElementContext
			implements GenericJavaXmlElement.Context {
		
		protected XmlElementAnnotation annotation;
		
		protected XmlElementContext(XmlElementAnnotation annotation) {
			this.annotation = annotation;
		}
		
		public XmlElementAnnotation getAnnotation(boolean createIfNull) {
			// will never be null in this context
			return annotation;
		}
		
		public JaxbAttributeMapping getAttributeMapping() {
			return GenericJavaXmlElementsMapping.this;
		}
		
		public String getDefaultType() {
			return null;
		}
		
		public XmlElementWrapper getElementWrapper() {
			return GenericJavaXmlElementsMapping.this.getXmlElementWrapper();
		}
		
		public boolean hasXmlID() {
			return false;
		}
		
		public boolean hasXmlIDREF() {
			return GenericJavaXmlElementsMapping.this.getXmlIDREF() != null;
		}
		
		public boolean hasXmlList() {
			return false;
		}
	}
	
	
	protected class XmlIDREFContext
			implements GenericJavaXmlIDREF.Context {
		
		public XmlIDREFAnnotation getAnnotation() {
			return GenericJavaXmlElementsMapping.this.getXmlIDREFAnnotation();
		}
		
		public Iterable<ValidatableReference> getReferences() {
			return new TransformationIterable<XmlElement, ValidatableReference>(
					GenericJavaXmlElementsMapping.this.getXmlElements()) {
				
				@Override
				protected ValidatableReference transform(final XmlElement o) {
					
					return new ValidatableReference() {
						
						public String getFullyQualifiedType() {
							return o.getFullyQualifiedType();
						}
						
						public TextRange getTypeTextRange(CompilationUnit astRoot) {
							return o.getTypeTextRange(astRoot);
						}
						
						public XsdFeature getXsdFeature() {
							return o.getXsdElement();
						}
						
						public TextRange getXsdFeatureTextRange(CompilationUnit astRoot) {
							return o.getQName().getNameTextRange(astRoot);
						}
					};
				}
			};
		}
		
		public boolean isList() {
			return false;
		}
	}
}
