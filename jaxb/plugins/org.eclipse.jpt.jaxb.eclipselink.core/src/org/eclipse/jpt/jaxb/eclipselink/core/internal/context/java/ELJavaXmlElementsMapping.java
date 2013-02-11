/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SubListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlElement;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementsMapping;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlElementsMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlPath;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlPathAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.validation.JptJaxbEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlElementsMapping
		extends GenericJavaXmlElementsMapping
		implements ELXmlElementsMapping {
	
	protected final ContextListContainer<ELJavaXmlPath, XmlPathAnnotation> xmlPathContainer;
	
	
	public ELJavaXmlElementsMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.xmlPathContainer = buildXmlPathContainer();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.xmlPathContainer.synchronizeWithResourceModel();
	}
	
	@Override
	public void update() {
		super.update();
		this.xmlPathContainer.update();
	}
	
	
	// ***** xmlPaths *****
	
	public ListIterable<ELXmlPath> getXmlPaths() {
		return new SuperListIterableWrapper<ELXmlPath>(this.xmlPathContainer.getContextElements());
	}
	
	public int getXmlPathsSize() {
		return this.xmlPathContainer.getContextElementsSize();
	}
	
	public ELXmlPath addXmlPath(int index) {
		XmlPathAnnotation annotation = (XmlPathAnnotation) getJavaResourceAttribute().addAnnotation(index, ELJaxb.XML_PATH);
		return this.xmlPathContainer.addContextElement(index, annotation);
	}
	
	public void removeXmlPath(int index) {
		getJavaResourceAttribute().removeAnnotation(index, ELJaxb.XML_PATH);
		this.xmlPathContainer.removeContextElement(index);
	}
	
	public void moveXmlPath(int targetIndex, int sourceIndex) {
		getJavaResourceAttribute().moveAnnotation(targetIndex, sourceIndex, ELJaxb.XML_PATH);
		this.xmlPathContainer.moveContextElement(targetIndex, sourceIndex);
	}
	
	protected ELJavaXmlPath buildXmlPath(XmlPathAnnotation xmlPathAnnotation) {
		return new ELJavaXmlPath(this, new XmlPathContext(xmlPathAnnotation));
	}
	
	protected ContextListContainer<ELJavaXmlPath, XmlPathAnnotation> buildXmlPathContainer() {
		XmlPathContainer container = new XmlPathContainer();
		container.initialize();
		return container;
	}
	
	protected ListIterable<XmlPathAnnotation> getXmlPathAnnotations() {
		return new SubListIterableWrapper<NestableAnnotation, XmlPathAnnotation>(
				getJavaResourceAttribute().getAnnotations(ELJaxb.XML_PATH));
	}
	
	
	// ***** misc *****
	
	@Override
	protected XmlElement buildXmlElement(XmlElementAnnotation xmlElementAnnotation) {
		return new ELJavaXmlElement(this, new XmlElementContext(xmlElementAnnotation));
	}
	
	@Override
	protected XmlElementWrapper buildXmlElementWrapper() {
		return new ELJavaXmlElementWrapper(this, new XmlElementWrapperContext());
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		for (ELJavaXmlPath xmlPath : this.xmlPathContainer.getContextElements()) {
			result = xmlPath.getCompletionProposals(pos);
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
		
		if (getXmlPathsSize() > 0 ) {
			validateXmlPaths(messages, reporter);
		}
	}
	
	protected void validateXmlPaths(List<IMessage> messages, IReporter reporter) {
		Iterator<XmlElement> xmlElements = getXmlElements().iterator();
		Iterator<ELJavaXmlPath> xmlPaths = this.xmlPathContainer.getContextElements().iterator();
		
		while (xmlElements.hasNext() && xmlPaths.hasNext()) {
			xmlElements.next();
			xmlPaths.next();
		}
		
		if (xmlElements.hasNext()) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							JptJaxbEclipseLinkCoreValidationMessages.XML_PATH__INSUFFICIENT_XML_PATHS_FOR_XML_ELEMENTS,
							this,
							getXmlPathsTextRange()));
		}
		
		while (xmlPaths.hasNext()) {
			ELJavaXmlPath xmlPath = xmlPaths.next();
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							JptJaxbEclipseLinkCoreValidationMessages.XML_PATH__INSUFFICIENT_XML_ELEMENTS_FOR_XML_PATHS,
							this,
							xmlPath.getValidationTextRange()));
		}
		
		for (ELJavaXmlPath xmlPath : this.xmlPathContainer.getContextElements()) {
			xmlPath.validate(messages, reporter);
		}
	}
	
	@Override
	protected void validateDuplicateQName(XmlElement xmlElement, Bag<QName> xmlElementQNames, 
				List<IMessage> messages) {
		
		if (getXmlPathsSize() == 0) { 
			super.validateDuplicateQName(xmlElement, xmlElementQNames, messages);
		}
	}
	
	protected TextRange getXmlPathsTextRange() {
		Annotation annotation = getJavaResourceAttribute().getAnnotation(ELJaxb.XML_PATHS);
		if (annotation == null) {
			annotation = getJavaResourceAttribute().getAnnotation(0, ELJaxb.XML_PATH);
		}
		return annotation.getTextRange();
	}
	
	
	protected class XmlPathContainer
			extends ContextListContainer<ELJavaXmlPath, XmlPathAnnotation> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return ELXmlElementsMapping.XML_PATHS_LIST;
		}
		
		@Override
		protected ELJavaXmlPath buildContextElement(XmlPathAnnotation resourceElement) {
			return ELJavaXmlElementsMapping.this.buildXmlPath(resourceElement);
		}
		
		@Override
		protected ListIterable<XmlPathAnnotation> getResourceElements() {
			return ELJavaXmlElementsMapping.this.getXmlPathAnnotations();
		}
		
		@Override
		protected XmlPathAnnotation getResourceElement(ELJavaXmlPath contextElement) {
			// in the context of this mapping, there will never be an ELXmlPath without an annotation
			return contextElement.getAnnotation();
		}
	}
	
	
	protected class XmlPathContext
			implements ELJavaXmlPath.Context {
		
		protected XmlPathAnnotation annotation;
		
		
		protected XmlPathContext(XmlPathAnnotation annotation) {
			this.annotation = annotation;
		}
		
		public XmlPathAnnotation getAnnotation() {
			return this.annotation;
		}
		
		public JaxbAttributeMapping getAttributeMapping() {
			return ELJavaXmlElementsMapping.this;
		}
	}
	
	
	protected class XmlElementContext
			extends GenericJavaXmlElementsMapping.XmlElementContext
			implements ELJavaXmlElement.Context {
		
		protected XmlElementContext(XmlElementAnnotation annotation) {
			super(annotation);
		}
		
		public boolean hasXmlPath() {
			return ELJavaXmlElementsMapping.this.getXmlPathsSize() > 0;
		}
	}
	
	protected class XmlElementWrapperContext
			implements ELJavaXmlElementWrapper.Context {
		
		public XmlElementWrapperAnnotation getAnnotation() {
			return ELJavaXmlElementsMapping.this.getXmlElementWrapperAnnotation();
		}
		
		public boolean hasXmlPath() {
			return ELJavaXmlElementsMapping.this.getXmlPathsSize() > 0;
		}
	}
}
