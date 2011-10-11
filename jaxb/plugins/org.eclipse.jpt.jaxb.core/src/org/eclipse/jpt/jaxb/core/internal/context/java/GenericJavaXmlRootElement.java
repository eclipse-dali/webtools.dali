/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.beans.Introspector;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlRootElement
		extends AbstractJavaContextNode
		implements XmlRootElement {
	
	protected final XmlRootElementAnnotation annotation;
	
	protected JaxbQName qName;
	
	
	public GenericJavaXmlRootElement(JaxbTypeMapping parent, XmlRootElementAnnotation resourceXmlRootElementAnnotation) {
		super(parent);
		this.annotation = resourceXmlRootElementAnnotation;
		this.qName = buildQName();
	}
	
	
	protected JaxbQName buildQName() {
		return new XmlRootElementQName(this);
	}
	
	public JaxbTypeMapping getTypeMapping() {
		return (JaxbTypeMapping) getParent();
	}
	
	protected JaxbType getJaxbType() {
		return getTypeMapping().getJaxbType();
	}
	
	protected JaxbPackage getJaxbPackage() {
		return getJaxbType().getJaxbPackage();
	}
	
	
	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.qName.synchronizeWithResourceModel();
	}
	
	@Override
	public void update() {
		super.update();
		this.qName.update();
	}
	
	
	// ***** schema element ref *****
	
	public JaxbQName getQName() {
		return this.qName;
	}
		
	
	// **************** content assist ****************************************
	
	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		result = this.qName.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		return EmptyIterable.instance();
	}
	
	
	// **************** validation ********************************************
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.annotation.getTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		this.qName.validate(messages, reporter, astRoot);
	}
	
	
	//****************** miscellaneous ********************

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.qName.toString());
	}
	
	
	protected class XmlRootElementQName
			extends AbstractJavaQName {
		
		protected XmlRootElementQName(JavaContextNode parent) {
			super(parent, new QNameAnnotationProxy());
		}
		
		
		@Override
		public String getDefaultNamespace() {
			return GenericJavaXmlRootElement.this.getJaxbPackage().getNamespace();
		}
		
		@Override
		public String getDefaultName() {
			return Introspector.decapitalize(GenericJavaXmlRootElement.this.getJaxbType().getSimpleName());
		}
		
		@Override
		protected Iterable<String> getNamespaceProposals(Filter<String> filter) {
			XsdSchema schema = GenericJavaXmlRootElement.this.getJaxbPackage().getXsdSchema();
			if (schema == null) {
				return EmptyIterable.instance();
			}
			return schema.getNamespaceProposals(filter);
		}
		
		@Override
		protected Iterable<String> getNameProposals(Filter<String> filter) {
			XsdSchema schema = GenericJavaXmlRootElement.this.getJaxbPackage().getXsdSchema();
			if (schema == null) {
				return EmptyIterable.instance();
			}
			return schema.getElementNameProposals(getNamespace(), filter);
		}
		
		@Override
		public String getReferencedComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_ELEMENT_DESC;
		}
		
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			String name = getName();
			String namespace = getNamespace();
			XsdSchema schema = GenericJavaXmlRootElement.this.getJaxbPackage().getXsdSchema();
			
			if (schema != null) {
				// element must resolve
				XsdElementDeclaration schemaElement = schema.getElementDeclaration(namespace, name);
				if (schemaElement == null) {
					messages.add(getUnresolveSchemaComponentMessage(astRoot));
				}
				else {
					// element type must agree with parent's schema type
					XsdTypeDefinition schemaType = GenericJavaXmlRootElement.this.getTypeMapping().getXsdTypeDefinition();
					if (schemaType != null) {
						if (! schemaType.equals(schemaElement.getType())) {
							messages.add(
									DefaultValidationMessages.buildMessage(
										IMessage.HIGH_SEVERITY,
										JaxbValidationMessages.XML_ROOT_ELEMENT_TYPE_CONFLICTS_WITH_XML_TYPE,
										new String[] {name, namespace},
										this,
										getValidationTextRange(astRoot)));
						}
					}
				}
			}
		}
	}
	
	
	protected class QNameAnnotationProxy 
			extends AbstractJavaQName.AbstractQNameAnnotationProxy {
		
		@Override
		protected QNameAnnotation getAnnotation(boolean createIfNull) {
			return GenericJavaXmlRootElement.this.annotation;
		}
	}
}
