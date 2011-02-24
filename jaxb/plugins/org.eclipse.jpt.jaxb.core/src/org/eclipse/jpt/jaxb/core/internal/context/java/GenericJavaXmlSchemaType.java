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
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.xsd.util.XSDUtil;

public abstract class GenericJavaXmlSchemaType
		extends AbstractJavaContextNode
		implements XmlSchemaType {
	
	protected final XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation;
	
	protected String name;
	
	protected String specifiedNamespace;
	
	protected String type;
	
	
	protected GenericJavaXmlSchemaType(JaxbContextNode parent, XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation) {
		super(parent);
		this.xmlSchemaTypeAnnotation = xmlSchemaTypeAnnotation;
		this.name = this.getResourceName();
		this.specifiedNamespace = this.getResourceNamespace();
		this.type = this.getResourceTypeString();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.getResourceName());
		this.setNamespace_(this.getResourceNamespace());
		this.setType_(this.getResourceTypeString());
	}


	// ********** xml schema type annotation **********

	public XmlSchemaTypeAnnotation getResourceXmlSchemaType() {
		return this.xmlSchemaTypeAnnotation;
	}

	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.xmlSchemaTypeAnnotation.setName(name);
		this.setName_(name);	
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	protected String getResourceName() {
		return this.xmlSchemaTypeAnnotation.getName();
	}

	// ********** namespace **********

	public String getNamespace() {
		return getSpecifiedNamespace() == null ? getDefaultNamespace() : getSpecifiedNamespace();
	}
	
	public String getDefaultNamespace() {
		return XSDUtil.SCHEMA_FOR_SCHEMA_URI_2001;
	}
	
	public String getSpecifiedNamespace() {
		return this.specifiedNamespace;
	}
	
	public void setSpecifiedNamespace(String location) {
		this.xmlSchemaTypeAnnotation.setNamespace(location);
		this.setNamespace_(location);	
	}
	
	protected void setNamespace_(String namespace) {
		String old = this.specifiedNamespace;
		this.specifiedNamespace = namespace;
		this.firePropertyChanged(SPECIFIED_NAMESPACE_PROPERTY, old, namespace);
	}
	
	protected String getResourceNamespace() {
		return this.xmlSchemaTypeAnnotation.getNamespace();
	}
	
	
	// ********** type **********

	public String getType() {
		return this.type;
	}

	public void setType(String location) {
		this.xmlSchemaTypeAnnotation.setType(location);
		this.setType_(location);	
	}

	protected void setType_(String type) {
		String old = this.type;
		this.type = type;
		this.firePropertyChanged(TYPE_PROPERTY, old, type);
	}

	protected String getResourceTypeString() {
		return this.xmlSchemaTypeAnnotation.getType();
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
	}

	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.xmlSchemaTypeAnnotation.getTextRange(astRoot);
	}


	// *********** content assist ***********

	@Override
	public Iterable<String> getJavaCompletionProposals(
			int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		if (namespaceTouches(pos, astRoot)) {
			return getNamespaceProposals(filter);
		}
		
		if (nameTouches(pos, astRoot)) {
			return getNameProposals(filter);
		}
		
		return EmptyIterable.instance();
	}
	
	protected boolean namespaceTouches(int pos, CompilationUnit astRoot) {
		return getResourceXmlSchemaType().namespaceTouches(pos, astRoot);
	}
	
	protected Iterable<String> getNamespaceProposals(Filter<String> filter) {
		XsdSchema schema = getJaxbPackage().getXsdSchema();
		if (schema == null) {
			return EmptyIterable.instance();
		}
		return schema.getNamespaceProposals(filter);
	}
	
	protected boolean nameTouches(int pos, CompilationUnit astRoot) {
		return getResourceXmlSchemaType().nameTouches(pos, astRoot);
	}
	
	protected Iterable<String> getNameProposals(Filter<String> filter) {
		XsdSchema schema = getJaxbPackage().getXsdSchema();
		if (schema == null) {
			return EmptyIterable.instance();
		}
		return schema.getSimpleTypeNameProposals(getNamespace(), filter);
	}

	protected abstract JaxbPackage getJaxbPackage();

}
