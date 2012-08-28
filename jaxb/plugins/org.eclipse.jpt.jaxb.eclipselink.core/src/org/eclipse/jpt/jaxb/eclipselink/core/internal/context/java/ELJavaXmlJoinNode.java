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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaContextNode;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELClassMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlJoinNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlJoinNodesMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.xpath.java.XPath;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.xpath.java.XPathFactory;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessages;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlJoinNodeAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlJoinNode 
		extends AbstractJavaContextNode
		implements ELXmlJoinNode {
	
	protected String xmlPath;
	
	protected String referencedXmlPath;
	
	protected Context context;
	
	
	public ELJavaXmlJoinNode(JavaContextNode parent, Context context) {
		super(parent);
		this.context = context;
		initXmlPath();
		initReferencedXmlPath();
	}
	
	
	protected JaxbPackage getJaxbPackage() {
		return getClassMapping().getJaxbType().getJaxbPackage();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncXmlPath();
		syncReferencedXmlPath();
	}
	
	
	// ***** xmlPath *****
	
	public String getXmlPath() {
		return this.xmlPath;
	}
	
	public void setXmlPath(String xmlPath) {
		getAnnotation().setXmlPath(xmlPath);
		setXmlPath_(xmlPath);
	}
	
	protected void setXmlPath_(String xmlPath) {
		String old = this.xmlPath;
		this.xmlPath = xmlPath;
		firePropertyChanged(XML_PATH_PROPERTY, old, this.xmlPath);
	}
	
	protected void initXmlPath() {
		this.xmlPath = getAnnotation().getXmlPath();
	}
	
	protected void syncXmlPath() {
		setXmlPath_(getAnnotation().getXmlPath());
	}
	
	
	// ***** referencedXmlPath *****
	
	public String getReferencedXmlPath() {
		return this.referencedXmlPath;
	}
	
	public void setReferencedXmlPath(String referencedXmlPath) {
		getAnnotation().setReferencedXmlPath(referencedXmlPath);
		setReferencedXmlPath_(referencedXmlPath);
	}
	
	protected void setReferencedXmlPath_(String referencedXmlPath) {
		String old = this.referencedXmlPath;
		this.referencedXmlPath = referencedXmlPath;
		firePropertyChanged(REFERENCED_XML_PATH_PROPERTY, old, this.referencedXmlPath);
	}
	
	protected void initReferencedXmlPath() {
		this.referencedXmlPath = getAnnotation().getReferencedXmlPath();
	}
	
	protected void syncReferencedXmlPath() {
		setReferencedXmlPath_(getAnnotation().getReferencedXmlPath());
	}
	
	
	protected XmlJoinNodeAnnotation getAnnotation() {
		return this.context.getAnnotation();
	}
	
	protected ELXmlJoinNodesMapping getAttributeMapping() {
		return this.context.getAttributeMapping();
	}
	
	protected JaxbClassMapping getClassMapping() {
		return getAttributeMapping().getClassMapping();
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(
			int pos, Filter<String> filter, CompilationUnit astRoot) {
		
		if (getAnnotation().xmlPathTouches(pos) && this.xmlPath != null) {
			XsdTypeDefinition xsdType = getClassMapping().getXsdTypeDefinition();
			XPath xpath = XPathFactory.instance().getXpath(this.xmlPath);
			return xpath.getCompletionProposals(new XmlPathContext(), xsdType, pos, filter);
		}
		
		if (getAnnotation().referencedXmlPathTouches(pos) && this.referencedXmlPath != null) {
			
			
			XsdTypeDefinition xsdType = getAttributeMapping().getReferencedXsdTypeDefinition();
			XPath xpath = XPathFactory.instance().getXpath(this.referencedXmlPath);
			Iterable<String> result = xpath.getCompletionProposals(new ReferencedXmlPathContext(), xsdType, pos, filter);
			
			ELClassMapping referencedClassMapping = this.context.getAttributeMapping().getReferencedClassMapping();
			if (referencedClassMapping != null) {
				result = new CompositeIterable<String>(
								result, 
								StringTools.convertToJavaStringLiterals(referencedClassMapping.getKeyXPaths()));
			}
			
			return CollectionTools.sortedSet(result);
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		return getAnnotation().getTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateXmlPath(messages);
		validateReferencedXmlPath(messages);
	}
	
	protected void validateXmlPath(List<IMessage> messages) {
		if (StringTools.stringIsEmpty(this.xmlPath)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
								IMessage.HIGH_SEVERITY,
								ELJaxbValidationMessages.XML_JOIN_NODE__XML_PATH_NOT_SPECIFIED,
								ELJavaXmlJoinNode.this,
								getXmlPathTextRange()));
			return;
		}
		
		if (this.xmlPath.startsWith(XPath.DELIM)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
								IMessage.HIGH_SEVERITY,
								ELJaxbValidationMessages.XPATH__ROOT_NOT_SUPPORTED,
								ELJavaXmlJoinNode.this,
								getXmlPathTextRange()));
			return;
		}
		
		XsdTypeDefinition xsdType = getClassMapping().getXsdTypeDefinition();
		XPath xpath = XPathFactory.instance().getXpath(this.xmlPath);
		xpath.validate(new XmlPathContext(), xsdType, messages);
	}
	
	protected void validateReferencedXmlPath(List<IMessage> messages) {
		if (StringTools.stringIsEmpty(this.referencedXmlPath)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.XML_JOIN_NODE__REFERENCED_XML_PATH_NOT_SPECIFIED,
							ELJavaXmlJoinNode.this,
							getReferencedXmlPathTextRange()));
			return;
		}
		
		if (this.referencedXmlPath.startsWith(XPath.DELIM)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.XPATH__ROOT_NOT_SUPPORTED,
							ELJavaXmlJoinNode.this,
							getReferencedXmlPathTextRange()));
			return;
		}
		
		ELClassMapping referencedClassMapping = this.context.getAttributeMapping().getReferencedClassMapping();
		if (referencedClassMapping != null && 
				! CollectionTools.contains(referencedClassMapping.getKeyXPaths(), this.referencedXmlPath)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.XML_JOIN_NODE__REFERENCED_XML_PATH_NOT_IN_REFERENCED_CLASS_KEYS,
							new String[] { referencedClassMapping.getJaxbType().getFullyQualifiedName(), this.referencedXmlPath },
							ELJavaXmlJoinNode.this,
							getReferencedXmlPathTextRange()));
		}
		
		XsdTypeDefinition xsdType = getAttributeMapping().getReferencedXsdTypeDefinition();
		XPath xpath = XPathFactory.instance().getXpath(this.referencedXmlPath);
		xpath.validate(new ReferencedXmlPathContext(), xsdType, messages);
	}
	
	protected TextRange getXmlPathTextRange() {
		// should never be null
		return getAnnotation().getXmlPathTextRange();
	}
	
	protected TextRange getReferencedXmlPathTextRange() {
		// should never be null
		return getAnnotation().getReferencedXmlPathTextRange();
	}
	
	
	public interface Context {
		
		XmlJoinNodeAnnotation getAnnotation();
		
		ELXmlJoinNodesMapping getAttributeMapping();
	}
	
	
	protected abstract class XPathContext
			implements XPath.Context {
		
		protected XPathContext() {
		}
		
		
		public JaxbNode getContextObject() {
			return ELJavaXmlJoinNode.this;
		}
		
		public JaxbPackage getJaxbPackage() {
			return ELJavaXmlJoinNode.this.getJaxbPackage();
		}
	}
	
	
	protected class XmlPathContext
			extends XPathContext {
		
		protected XmlPathContext() {
			super();
		}
		
		
		public TextRange getTextRange() {
			return ELJavaXmlJoinNode.this.getXmlPathTextRange();
		}
	}
	
	
	protected class ReferencedXmlPathContext
			extends XPathContext {
		
		protected ReferencedXmlPathContext() {
			super();
		}
		
		
		public TextRange getTextRange() {
			return ELJavaXmlJoinNode.this.getReferencedXmlPathTextRange();
		}
	}
}
