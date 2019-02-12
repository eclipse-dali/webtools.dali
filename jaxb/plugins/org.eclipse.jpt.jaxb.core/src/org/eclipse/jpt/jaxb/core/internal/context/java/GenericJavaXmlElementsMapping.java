/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import javax.xml.namespace.QName;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.XmlElement;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlElementsMapping;
import org.eclipse.jpt.jaxb.core.context.XmlIDREF;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlElementsMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlIDREF.ValidatableReference;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDREFAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaXmlElementsMapping
		extends AbstractJavaAdaptableAttributeMapping<XmlElementsAnnotation> 
		implements JavaXmlElementsMapping {
	
	protected final ContextListContainer<XmlElement, XmlElementAnnotation> xmlElementContainer;
	
	protected XmlElementWrapper xmlElementWrapper;
	
	protected XmlIDREF xmlIDREF;
	
	
	public GenericJavaXmlElementsMapping(JavaPersistentAttribute parent) {
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
		return IterableTools.concatenate(
				super.getReferencedXmlTypeNames(),
				IterableTools.children(getXmlElements(), XmlElement.REFERENCED_XML_TYPE_NAMES_TRANSFORMER));
	}
	
	@Override
	public boolean isParticleMapping() {
		return true;
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		for (XmlElement xmlElement : getXmlElements()) {
			result = xmlElement.getCompletionProposals(pos);
			if (! IterableTools.isEmpty(result)) {
				return result;
			}
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
		
		validateDuplicateTypesAndQNames(messages, reporter);
		
		for (XmlElement xmlElement : getXmlElements()) {
			xmlElement.validate(messages, reporter);
		}
		
		if (this.xmlElementWrapper != null) {
			this.xmlElementWrapper.validate(messages, reporter);
		}
		
		if (this.xmlIDREF != null) {
			this.xmlIDREF.validate(messages, reporter);
		}
	}
	
	protected void validateDuplicateTypesAndQNames(List<IMessage> messages, IReporter reporter) {
		Bag<String> xmlElementTypes = new HashBag<String>();
		Bag<QName> xmlElementQNames = new HashBag<QName>();
		
		for (XmlElement xmlElement : getXmlElements()) {
			String typeName = xmlElement.getFullyQualifiedType();
			if (! StringTools.isBlank(typeName)) {
				xmlElementTypes.add(typeName);
			}
			String elementName = xmlElement.getQName().getName();
			if (! StringTools.isBlank(elementName)) {
				xmlElementQNames.add(new QName(xmlElement.getQName().getNamespace(), elementName));
			}
		}
		
		for (XmlElement xmlElement : getXmlElements()) {
			validateDuplicateType(xmlElement, xmlElementTypes, messages);
			validateDuplicateQName(xmlElement, xmlElementQNames, messages);	
		}
	}
	
	protected void validateDuplicateType(XmlElement xmlElement, Bag<String> xmlElementTypes,
				List<IMessage> messages) {
		String xmlElementType = xmlElement.getFullyQualifiedType();
		if (xmlElementTypes.count(xmlElementType) > 1) {
			messages.add(
					this.buildValidationMessage(
							xmlElement,
							xmlElement.getTypeTextRange(),
							JptJaxbCoreValidationMessages.XML_ELEMENTS__DUPLICATE_XML_ELEMENT_TYPE,
							xmlElementType));
		}
	}
	
	protected void validateDuplicateQName(XmlElement xmlElement, Bag<QName> xmlElementQNames,
				List<IMessage> messages) {
		String xmlElementNamespace = xmlElement.getQName().getNamespace();
		String xmlElementName = xmlElement.getQName().getName();
		if (xmlElementQNames.count(new QName(xmlElementNamespace, xmlElementName)) > 1) {
			messages.add(
					this.buildValidationMessage(
							xmlElement,
							xmlElement.getQName().getNameValidationTextRange(),
							JptJaxbCoreValidationMessages.XML_ELEMENTS__DUPLICATE_XML_ELEMENT_QNAME,
							xmlElementName));
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
		
		public JavaAttributeMapping getAttributeMapping() {
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
		
		public boolean hasXmlSchemaType() {
			return false;
		}
		
		public XmlSchemaType getXmlSchemaType() {
			return null;
		}
	}
	
	
	protected class XmlIDREFContext
			implements GenericJavaXmlIDREF.Context {
		
		public XmlIDREFAnnotation getAnnotation() {
			return GenericJavaXmlElementsMapping.this.getXmlIDREFAnnotation();
		}
		
		public Iterable<ValidatableReference> getReferences() {
			return IterableTools.transform(GenericJavaXmlElementsMapping.this.getXmlElements(), XML_ELEMENT_TRANSFORMER);
		}
		
		public boolean isList() {
			return false;
		}
	}
	protected static final Transformer<XmlElement, ValidatableReference> XML_ELEMENT_TRANSFORMER = new XmlElementTransformer();
	public static class XmlElementTransformer
		extends TransformerAdapter<XmlElement, ValidatableReference>
	{
		@Override
		public ValidatableReference transform(XmlElement xmlElement) {
			return new XmlElementValidatableReference(xmlElement);
		}
	}

	public static class XmlElementValidatableReference
			implements ValidatableReference {
		protected final XmlElement xmlElement;
		public XmlElementValidatableReference(XmlElement xmlElement) {
			super();
			this.xmlElement = xmlElement;
		}
		public String getFullyQualifiedType() {
			return this.xmlElement.getFullyQualifiedType();
		}
		
		public TextRange getTypeValidationTextRange() {
			return this.xmlElement.getTypeTextRange();
		}
		
		public XsdElementDeclaration getXsdFeature() {
			return this.xmlElement.getXsdElement();
		}
		
		public TextRange getXsdFeatureValidationTextRange() {
			return this.xmlElement.getQName().getNameValidationTextRange();
		}
	}
}
