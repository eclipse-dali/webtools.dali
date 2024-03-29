/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SubListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbMappingKeys;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELClassMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlElementsMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlJoinNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlJoinNodesMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlJoinNodeAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.validation.JptJaxbEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlJoinNodesMapping
		extends AbstractJavaAttributeMapping
		implements ELXmlJoinNodesMapping {
	
	protected final ContextListContainer<ELJavaXmlJoinNode, XmlJoinNodeAnnotation> xmlJoinNodeContainer;
	
	
	public ELJavaXmlJoinNodesMapping(JavaPersistentAttribute parent) {
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
				&& IterableTools.isEmpty(getPersistentAttribute().getJavaResourceAttribute().getAnnotations(ELJaxb.XML_JOIN_NODE));
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
		if (StringTools.isBlank(valueType)) {
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
	public Iterable<String> getCompletionProposals(int pos) {
		
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		for (ELJavaXmlJoinNode xmlJoinNode : this.xmlJoinNodeContainer.getContextElements()) {
			result = xmlJoinNode.getCompletionProposals(pos);
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
		
		JaxbClassMapping referencedClass = getReferencedClassMapping();
		
		if (referencedClass == null) {
			messages.add(
					this.buildValidationMessage(
								ELJavaXmlJoinNodesMapping.this,
								getValidationTextRange(),
								JptJaxbEclipseLinkCoreValidationMessages.XML_JOIN_NODES__INVALID_REFERENCED_CLASS,
								getValueTypeName()));
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
			if (StringTools.isNotBlank(xmlPath)) {
				xmlPaths.add(xmlPath);
			}
			String referencedXmlPath = joinNode.getReferencedXmlPath();
			if (StringTools.isNotBlank(referencedXmlPath)) {
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
					this.buildValidationMessage(
							joinNode,
							joinNode.getXmlPathTextRange(),
							JptJaxbEclipseLinkCoreValidationMessages.XML_JOIN_NODES__DUPLICATE_XML_PATH,
							xmlPath));
		}
	}
	
	protected void validateDuplicateReferencedXmlPath(ELJavaXmlJoinNode joinNode, Bag<String> referencedXmlPaths,
				List<IMessage> messages) {
		String referencedXmlPath = joinNode.getReferencedXmlPath();
		if (referencedXmlPaths.count(referencedXmlPath) > 1) {
			messages.add(
					this.buildValidationMessage(
							joinNode,
							joinNode.getReferencedXmlPathTextRange(),
							JptJaxbEclipseLinkCoreValidationMessages.XML_JOIN_NODES__DUPLICATE_REFERENCED_XML_PATH,
							referencedXmlPath));
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
