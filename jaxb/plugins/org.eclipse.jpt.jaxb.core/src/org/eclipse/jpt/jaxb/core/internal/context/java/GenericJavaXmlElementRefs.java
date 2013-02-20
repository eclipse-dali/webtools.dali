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
import javax.xml.namespace.QName;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.XmlElementRef;
import org.eclipse.jpt.jaxb.core.context.XmlElementRefs;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaXmlElementRefs
		extends AbstractJavaContextNode 
		implements XmlElementRefs {
	
	protected final Context context;
	
	protected final ContextListContainer<XmlElementRef, XmlElementRefAnnotation> xmlElementRefContainer;
	
	
	public GenericJavaXmlElementRefs(JaxbContextNode parent, Context context) {
		super(parent);
		this.context = context;
		this.xmlElementRefContainer = this.buildXmlElementRefContainer();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.xmlElementRefContainer.synchronizeWithResourceModel();
	}
	
	@Override
	public void update() {
		super.update();
		this.xmlElementRefContainer.update();
	}
	
	
	// ***** xml element refs *****
	
	public ListIterable<XmlElementRef> getXmlElementRefs() {
		return this.xmlElementRefContainer.getContextElements();
	}
	
	public int getXmlElementRefsSize() {
		return this.xmlElementRefContainer.getContextElementsSize();
	}
	
	public XmlElementRef addXmlElementRef(int index) {
		XmlElementRefAnnotation annotation = this.context.addXmlElementRefAnnotation(index);
		return this.xmlElementRefContainer.addContextElement(index, annotation);
	}
	
	public void removeXmlElementRef(int index) {
		this.context.removeXmlElementRefAnnotation(index);
		this.xmlElementRefContainer.removeContextElement(index);
	}
	
	public void removeXmlElementRef(XmlElementRef xmlElementRef) {
		removeXmlElementRef(this.xmlElementRefContainer.indexOfContextElement(xmlElementRef));
	}
	
	public void moveXmlElementRef(int targetIndex, int sourceIndex) {
		this.context.moveXmlElementRefAnnotation(targetIndex, sourceIndex);
		this.xmlElementRefContainer.moveContextElement(targetIndex, sourceIndex);
	}
	
	protected XmlElementRef buildXmlElementRef(XmlElementRefAnnotation xmlElementRefAnnotation) {
		return this.context.buildXmlElementRef(this, xmlElementRefAnnotation);
	}
	
	protected ListIterable<XmlElementRefAnnotation> getXmlElementRefAnnotations() {
		return this.context.getXmlElementRefAnnotations();
	}

	protected ContextListContainer<XmlElementRef, XmlElementRefAnnotation> buildXmlElementRefContainer() {
		XmlElementRefContainer container = new XmlElementRefContainer();
		container.initialize();
		return container;
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		for (XmlElementRef elementRef : getXmlElementRefs()) {
			result = elementRef.getCompletionProposals(pos);
			if (! IterableTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		return this.context.getValidationTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateDuplicateTypesAndQNames(messages, reporter);
		
		for (XmlElementRef elementRef : getXmlElementRefs()) {
			elementRef.validate(messages, reporter);
		}
	}
	
	protected void validateDuplicateTypesAndQNames(List<IMessage> messages, IReporter reporter) {
		
		Bag<String> xmlElementRefTypes = new HashBag<String>();
		Bag<QName> xmlElementRefQnames = new HashBag<QName>();
		
		for (XmlElementRef xmlElementRef : getXmlElementRefs()) {
			String typeName = xmlElementRef.getFullyQualifiedType();
			boolean isJaxbElement = JAXB.JAXB_ELEMENT.equals(typeName);
			if (! isJaxbElement && ! StringTools.isBlank(typeName)) {
				xmlElementRefTypes.add(typeName);
			}
			String elementRefName = xmlElementRef.getQName().getName();
			if (isJaxbElement && ! StringTools.isBlank(elementRefName)) {
				xmlElementRefQnames.add(new QName(xmlElementRef.getQName().getNamespace(), elementRefName));
			}
		}
		
		for (XmlElementRef xmlElementRef : getXmlElementRefs()) {
			String typeName = xmlElementRef.getFullyQualifiedType();
			boolean isJaxbElement = JAXB.JAXB_ELEMENT.equals(typeName);
			if (! isJaxbElement && xmlElementRefTypes.count(typeName) > 1) {
				messages.add(
						this.buildErrorValidationMessage(
								JptJaxbCoreValidationMessages.XML_ELEMENT_REFS__DUPLICATE_XML_ELEMENT_TYPE,
								xmlElementRef,
								xmlElementRef.getTypeTextRange(), typeName));
			}
			
			String xmlElementNamespace = xmlElementRef.getQName().getNamespace();
			String xmlElementName = xmlElementRef.getQName().getName();
			if (isJaxbElement && xmlElementRefQnames.count(new QName(xmlElementNamespace, xmlElementName)) > 1) {
				messages.add(
						this.buildErrorValidationMessage(
								JptJaxbCoreValidationMessages.XML_ELEMENT_REFS__DUPLICATE_XML_ELEMENT_QNAME,
								xmlElementRef,
								xmlElementRef.getQName().getNameTextRange(), xmlElementName));
			}
		}
	}
	
	
	
	// ***** misc *****
	
	public Iterable<String> getReferencedXmlTypeNames() {
		return IterableTools.children(getXmlElementRefs(), XmlElementRef.REFERENCED_XML_TYPE_NAMES_TRANSFORMER);
	}
	
	
	
	protected class XmlElementRefContainer
			extends ContextListContainer<XmlElementRef, XmlElementRefAnnotation> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return XmlElementRefs.XML_ELEMENT_REFS_LIST;
		}
		
		@Override
		protected XmlElementRef buildContextElement(XmlElementRefAnnotation resourceElement) {
			return GenericJavaXmlElementRefs.this.buildXmlElementRef(resourceElement);
		}
		
		@Override
		protected ListIterable<XmlElementRefAnnotation> getResourceElements() {
			return GenericJavaXmlElementRefs.this.getXmlElementRefAnnotations();
		}
		
		@Override
		protected XmlElementRefAnnotation getResourceElement(XmlElementRef contextElement) {
			// in this context, there will never be an XmlElementRef without an annotation
			return contextElement.getAnnotation();
		}
	}
	
	
	public interface Context {
		
		ListIterable<XmlElementRefAnnotation> getXmlElementRefAnnotations();
		
		XmlElementRefAnnotation addXmlElementRefAnnotation(int index);
		
		void removeXmlElementRefAnnotation(int index);
		
		void moveXmlElementRefAnnotation(int targetIndex, int sourceIndex);
		
		XmlElementRef buildXmlElementRef(JaxbContextNode parent, XmlElementRefAnnotation annotation);
		
		TextRange getValidationTextRange();
	}
}
