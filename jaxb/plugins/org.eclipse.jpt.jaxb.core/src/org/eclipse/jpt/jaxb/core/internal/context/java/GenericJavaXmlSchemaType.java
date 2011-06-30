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

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbSchemaComponentRef;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.resource.java.SchemaComponentRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class GenericJavaXmlSchemaType
		extends AbstractJavaContextNode
		implements XmlSchemaType {
	
	protected final XmlSchemaTypeAnnotation annotation;
	
	protected JaxbSchemaComponentRef schemaTypeRef;
	
	protected String type;
	
	
	protected GenericJavaXmlSchemaType(JaxbContextNode parent, XmlSchemaTypeAnnotation annotation) {
		super(parent);
		this.annotation = annotation;
		this.schemaTypeRef = buildSchemaTypeRef();
		this.type = this.getResourceTypeString();
	}
	
	
	protected JaxbSchemaComponentRef buildSchemaTypeRef() {
		return new XmlSchemaTypeRef(this);
	}
	
	public JaxbSchemaComponentRef getSchemaTypeRef() {
		return this.schemaTypeRef;
	}
	
	public XmlSchemaTypeAnnotation getXmlSchemaTypeAnnotation() {
		return this.annotation;
	}

	protected abstract JaxbPackage getJaxbPackage();
	
	
	// ********** synchronize/update **********
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.schemaTypeRef.synchronizeWithResourceModel();
		this.setType_(this.getResourceTypeString());
	}
	
	@Override
	public void update() {
		super.update();
		this.schemaTypeRef.update();
	}
	
	
	// ********** type **********
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String location) {
		this.annotation.setType(location);
		this.setType_(location);	
	}
	
	protected void setType_(String type) {
		String old = this.type;
		this.type = type;
		this.firePropertyChanged(TYPE_PROPERTY, old, type);
	}
	
	protected String getResourceTypeString() {
		return this.annotation.getType();
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		result = this.schemaTypeRef.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.annotation.getTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		this.schemaTypeRef.validate(messages, reporter, astRoot);
	}
	
	
	protected class XmlSchemaTypeRef
			extends AbstractJavaSchemaComponentRef {
		
		protected XmlSchemaTypeRef(JavaContextNode parent) {
			super(parent);
		}
		
		
		@Override
		protected SchemaComponentRefAnnotation getAnnotation(boolean createIfNull) {
			// never null
			return GenericJavaXmlSchemaType.this.annotation;
		}
		
		@Override
		protected String getSchemaComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_TYPE_DESC;
		}
		
		@Override
		public String getDefaultName() {
			return null;
		}
		
		@Override
		public String getDefaultNamespace() {
			return XmlSchemaType.DEFAULT_NAMESPACE;
		}
		
		@Override
		protected Iterable<String> getNamespaceProposals(Filter<String> filter) {
			XsdSchema schema = GenericJavaXmlSchemaType.this.getJaxbPackage().getXsdSchema();
			if (schema == null) {
				return EmptyIterable.instance();
			}
			return schema.getNamespaceProposals(filter);
		}
		
		@Override
		protected Iterable<String> getNameProposals(Filter<String> filter) {
			XsdSchema schema = GenericJavaXmlSchemaType.this.getJaxbPackage().getXsdSchema();
			if (schema == null) {
				return EmptyIterable.instance();
			}
			return schema.getSimpleTypeNameProposals(getNamespace(), filter);
		}
		
		@Override
		protected void validateSchemaComponentRef(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			String name = getName();
			String namespace = getNamespace();
			
			if (! StringTools.stringIsEmpty(name)) {
				XsdSchema schema = GenericJavaXmlSchemaType.this.getJaxbPackage().getXsdSchema();
				
				if (schema != null) {
					XsdTypeDefinition schemaType = schema.getTypeDefinition(namespace, name);
					if (schemaType == null) {
						messages.add(getUnresolveSchemaComponentMessage(astRoot));
					}
				}
			}
		}
	}
}
