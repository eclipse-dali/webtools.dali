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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentType;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlRootElement
		extends AbstractJavaContextNode
		implements XmlRootElement {
	
	protected final XmlRootElementAnnotation resourceXmlRootElementAnnotation;

	protected String specifiedName;

	protected String specifiedNamespace;
	
	
	public GenericJavaXmlRootElement(JaxbPersistentType parent, XmlRootElementAnnotation resourceXmlRootElementAnnotation) {
		super(parent);
		this.resourceXmlRootElementAnnotation = resourceXmlRootElementAnnotation;
		this.specifiedName = this.getResourceName();
		this.specifiedNamespace = this.getResourceNamespace();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedName_(this.getResourceName());
		this.setSpecifiedNamespace_(this.getResourceNamespace());
	}


	@Override
	public JaxbPersistentType getParent() {
		return (JaxbPersistentType) super.getParent();
	}


	// ********** name **********

	public String getName() {
		return (this.specifiedName != null) ? this.specifiedName : getDefaultName();
	}
	
	public String getSpecifiedName() {
		return this.specifiedName;
	}
	
	public void setSpecifiedName(String name) {
		this.resourceXmlRootElementAnnotation.setName(name);
		this.setSpecifiedName_(name);	
	}
	
	protected void setSpecifiedName_(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}
	
	public String getDefaultName() {
		return Introspector.decapitalize(getParent().getSimpleName());
	}
	
	protected String getResourceName() {
		return this.resourceXmlRootElementAnnotation.getName();
	}
	
	
	// ********** namespace **********
	
	public String getNamespace() {
		return (this.specifiedNamespace != null) ? this.specifiedNamespace : getDefaultNamespace();
	}
	
	public String getSpecifiedNamespace() {
		return this.specifiedNamespace;
	}
	
	public void setSpecifiedNamespace(String namespace) {
		this.resourceXmlRootElementAnnotation.setNamespace(namespace);
		this.setSpecifiedNamespace_(namespace);	
	}
	
	protected void setSpecifiedNamespace_(String namespace) {
		String old = this.specifiedNamespace;
		this.specifiedNamespace = namespace;
		this.firePropertyChanged(SPECIFIED_NAMESPACE_PROPERTY, old, namespace);
	}
	
	public String getDefaultNamespace() {
		return getParent().getJaxbPackage().getNamespace();
	}
	
	protected String getResourceNamespace() {
		return this.resourceXmlRootElementAnnotation.getNamespace();
	}
	
	
	// **************** content assist ****************************************
	
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
		return this.resourceXmlRootElementAnnotation.namespaceTouches(pos, astRoot);
	}
	
	protected Iterable<String> getNamespaceProposals(Filter<String> filter) {
		XsdSchema schema = getParent().getJaxbPackage().getXsdSchema();
		if (schema == null) {
			return EmptyIterable.instance();
		}
		return schema.getNamespaceProposals(filter);
	}
	
	protected boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.resourceXmlRootElementAnnotation.nameTouches(pos, astRoot);
	}
	
	protected Iterable<String> getNameProposals(Filter<String> filter) {
		String namespace = getNamespace();
		XsdSchema schema = getParent().getJaxbPackage().getXsdSchema();
		if (schema == null) {
			return EmptyIterable.instance();
		}
		return StringTools.convertToJavaStringLiterals(
				new FilteringIterable<String>(
					new TransformationIterable<XsdElementDeclaration, String>(schema.getElementDeclarations(namespace)) {
						@Override
						protected String transform(XsdElementDeclaration o) {
							return o.getName();
						}
					},
					filter));
	}
	
	
	// **************** validation ********************************************
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourceXmlRootElementAnnotation.getTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		String name = getName();
		String namespace = getNamespace();
		
		XsdSchema schema = getParent().getJaxbPackage().getXsdSchema();
		
		if (schema != null) {
			// element must resolve
			XsdElementDeclaration schemaElement = schema.getElementDeclaration(namespace, name);
			if (schemaElement == null) {
				messages.add(
						DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ROOT_ELEMENT_UNRESOLVED_SCHEMA_ELEMENT,
							new String[] {name, namespace},
							this,
							this.resourceXmlRootElementAnnotation.getTextRange(astRoot)));
			}
			else {
				// element type must agree with parent's schema type
				XsdTypeDefinition schemaType = getParent().getXsdTypeDefinition();
				if (schemaType != null) {
					if (! schemaType.equals(schemaElement.getType())) {
						messages.add(
								DefaultValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									JaxbValidationMessages.XML_ROOT_ELEMENT_TYPE_CONFLICTS_WITH_XML_TYPE,
									new String[] {name, namespace},
									this,
									this.resourceXmlRootElementAnnotation.getTextRange(astRoot)));
					}
				}
			}
		}
	}
	

	//****************** miscellaneous ********************

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append("namespace = \"" + this.specifiedNamespace + "\"");
		sb.append("; name = \"" + this.specifiedName + "\"");
	}
}
