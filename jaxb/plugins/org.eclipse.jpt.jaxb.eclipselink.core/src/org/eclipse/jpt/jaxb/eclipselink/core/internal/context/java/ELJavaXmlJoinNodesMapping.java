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
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.Bag;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.HashBag;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbMappingKeys;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELClassMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlElementsMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlJoinNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlJoinNodesMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessages;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlJoinNodeAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlJoinNodesMapping
		extends AbstractJavaAttributeMapping
		implements ELXmlJoinNodesMapping {
	
	protected final ContextListContainer<ELJavaXmlJoinNode, XmlJoinNodeAnnotation> xmlJoinNodeContainer;
	
	
	public ELJavaXmlJoinNodesMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.xmlJoinNodeContainer = buildXmlJoinNodeContainer();
	}
	
	
	public String getKey() {
		return ELJaxbMappingKeys.XML_JOIN_NODES_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return ELJaxb.XML_JOIN_NODES;
	}

	@Override
	public Annotation getAnnotation() {
		return getJavaResourceAttribute().getContainerAnnotation(getAnnotationName());
	}
	
	// ***** overrides *****
	
	@Override
	protected boolean buildDefault() {
		return getAnnotation() == null 
				&& CollectionTools.isEmpty(getPersistentAttribute().getJavaResourceAttribute().getAnnotations(ELJaxb.XML_JOIN_NODE));
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.xmlJoinNodeContainer.synchronizeWithResourceModel();
	}
	
	@Override
	public void update() {
		super.update();
		this.xmlJoinNodeContainer.update();
	}
	
	
	// ***** xmlJoinNodes *****
	
	public ListIterable<ELXmlJoinNode> getXmlJoinNodes() {
		return new SuperListIterableWrapper<ELXmlJoinNode>(this.xmlJoinNodeContainer.getContextElements());
	}
	
	public int getXmlJoinNodesSize() {
		return this.xmlJoinNodeContainer.getContextElementsSize();
	}
	
	public ELXmlJoinNode addXmlJoinNode(int index) {
		XmlJoinNodeAnnotation annotation = 
				(XmlJoinNodeAnnotation) getJavaResourceAttribute().addAnnotation(index, ELJaxb.XML_JOIN_NODE);
		return this.xmlJoinNodeContainer.addContextElement(index, annotation);
	}
	
	public void removeXmlJoinNode(int index) {
		getJavaResourceAttribute().removeAnnotation(index, ELJaxb.XML_JOIN_NODE);
		this.xmlJoinNodeContainer.removeContextElement(index);
	}
	
	public void moveXmlJoinNode(int targetIndex, int sourceIndex) {
		getJavaResourceAttribute().moveAnnotation(targetIndex, sourceIndex, ELJaxb.XML_JOIN_NODE);
		this.xmlJoinNodeContainer.moveContextElement(targetIndex, sourceIndex);
	}
	
	protected ELJavaXmlJoinNode buildXmlJoinNode(XmlJoinNodeAnnotation xmlJoinNodeAnnotation) {
		return new ELJavaXmlJoinNode(this, new XmlJoinNodeContext(xmlJoinNodeAnnotation));
	}
	
	protected ContextListContainer<ELJavaXmlJoinNode, XmlJoinNodeAnnotation> buildXmlJoinNodeContainer() {
		XmlJoinNodeContainer container = new XmlJoinNodeContainer();
		container.initialize();
		return container;
	}
	
	protected ListIterable<XmlJoinNodeAnnotation> getXmlJoinNodeAnnotations() {
		return new SubListIterableWrapper<NestableAnnotation, XmlJoinNodeAnnotation>(
				getJavaResourceAttribute().getAnnotations(ELJaxb.XML_JOIN_NODE));
	}
	
	
	// ***** misc *****
	
	public ELClassMapping getReferencedClassMapping() {
		String valueType = getValueTypeName();
		if (StringTools.stringIsEmpty(valueType)) {
			return null;
		}
		
		return (ELClassMapping) getContextRoot().getClassMapping(valueType);
	}
	
	public XsdTypeDefinition getReferencedXsdTypeDefinition() {
		JaxbClassMapping classMapping = getReferencedClassMapping();
		return (classMapping == null) ? null : classMapping.getXsdTypeDefinition();
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(
			int pos, Filter<String> filter, CompilationUnit astRoot) {
		
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		for (ELJavaXmlJoinNode xmlJoinNode : this.xmlJoinNodeContainer.getContextElements()) {
			result = xmlJoinNode.getJavaCompletionProposals(pos, filter, astRoot);
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
		
		JaxbClassMapping referencedClass = getReferencedClassMapping();
		
		if (referencedClass == null) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
								IMessage.HIGH_SEVERITY,
								ELJaxbValidationMessages.XML_JOIN_NODES__INVALID_REFERENCED_CLASS,
								new String[] { getValueTypeName() },
								ELJavaXmlJoinNodesMapping.this,
								getValidationTextRange()));
		}
		
		validateDuplicateXmlPaths(messages, reporter);
		
		for (ELJavaXmlJoinNode xmlJoinNode : this.xmlJoinNodeContainer.getContextElements()) {
			xmlJoinNode.validate(messages, reporter);
		}
	}
	
	protected void validateDuplicateXmlPaths(List<IMessage> messages, IReporter reporter) {
		Bag<String> xmlPaths = new HashBag<String>();
		Bag<String> referencedXmlPaths = new HashBag<String>();
		
		for (ELJavaXmlJoinNode joinNode : this.xmlJoinNodeContainer.getContextElements()) {
			String xmlPath = joinNode.getXmlPath();
			if (! StringTools.stringIsEmpty(xmlPath)) {
				xmlPaths.add(xmlPath);
			}
			String referencedXmlPath = joinNode.getReferencedXmlPath();
			if (! StringTools.stringIsEmpty(referencedXmlPath)) {
				referencedXmlPaths.add(referencedXmlPath);
			}
		}
		
		for (ELJavaXmlJoinNode joinNode : this.xmlJoinNodeContainer.getContextElements()) {
			validateDuplicateXmlPath(joinNode, xmlPaths, messages);
			validateDuplicateReferencedXmlPath(joinNode, referencedXmlPaths, messages);	
		}
	}
	
	protected void validateDuplicateXmlPath(ELJavaXmlJoinNode joinNode, Bag<String> xmlPaths,
				List<IMessage> messages) {
		String xmlPath = joinNode.getXmlPath();
		if (xmlPaths.count(xmlPath) > 1) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.XML_JOIN_NODES__DUPLICATE_XML_PATH,
							new String[] { xmlPath },
							joinNode,
							joinNode.getXmlPathTextRange()));
		}
	}
	
	protected void validateDuplicateReferencedXmlPath(ELJavaXmlJoinNode joinNode, Bag<String> referencedXmlPaths,
				List<IMessage> messages) {
		String referencedXmlPath = joinNode.getReferencedXmlPath();
		if (referencedXmlPaths.count(referencedXmlPath) > 1) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.XML_JOIN_NODES__DUPLICATE_REFERENCED_XML_PATH,
							new String[] { referencedXmlPath },
							joinNode,
							joinNode.getReferencedXmlPathTextRange()));
		}
	}
	
	@Override
	public TextRange getValidationTextRange() {
		Annotation annotation = getAnnotation();
		if (annotation == null) {
			annotation = getJavaResourceAttribute().getAnnotation(0, ELJaxb.XML_JOIN_NODE);
		}
		return annotation.getTextRange();
	}
	
	
	protected class XmlJoinNodeContainer
			extends ContextListContainer<ELJavaXmlJoinNode, XmlJoinNodeAnnotation> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return ELXmlElementsMapping.XML_PATHS_LIST;
		}
		
		@Override
		protected ELJavaXmlJoinNode buildContextElement(XmlJoinNodeAnnotation resourceElement) {
			return ELJavaXmlJoinNodesMapping.this.buildXmlJoinNode(resourceElement);
		}
		
		@Override
		protected ListIterable<XmlJoinNodeAnnotation> getResourceElements() {
			return ELJavaXmlJoinNodesMapping.this.getXmlJoinNodeAnnotations();
		}
		
		@Override
		protected XmlJoinNodeAnnotation getResourceElement(ELJavaXmlJoinNode contextElement) {
			// in the context of this mapping, there will never be an ELXmlJoinNode without an annotation
			return contextElement.getAnnotation();
		}
	}
	
	
	protected class XmlJoinNodeContext
			implements ELJavaXmlJoinNode.Context {
		
		protected XmlJoinNodeAnnotation annotation;
		
		
		protected XmlJoinNodeContext(XmlJoinNodeAnnotation annotation) {
			this.annotation = annotation;
		}
		
		public XmlJoinNodeAnnotation getAnnotation() {
			return this.annotation;
		}
		
		public ELXmlJoinNodesMapping getAttributeMapping() {
			return ELJavaXmlJoinNodesMapping.this;
		}
	}
}
