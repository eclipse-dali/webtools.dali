/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbSchemaComponentRef;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.SchemaComponentRefAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaSchemaComponentRef
		extends AbstractJavaContextNode 
		implements JaxbSchemaComponentRef {
	
	protected String specifiedNamespace;
	
	protected String specifiedName;
	
	
	public AbstractJavaSchemaComponentRef(JavaContextNode parent) {
		super(parent);
		this.specifiedNamespace = buildSpecifiedNamespace();
		this.specifiedName = buildSpecifiedName();
	}
	
	
	@Override
	public JavaContextNode getParent() {
		return (JavaContextNode) super.getParent();
	}
	
	protected abstract SchemaComponentRefAnnotation getAnnotation(boolean createIfNull);
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setSpecifiedNamespace_(buildSpecifiedNamespace());
		setSpecifiedName_(buildSpecifiedName());
	}
	
	
	// ***** namespace *****
	
	public String getNamespace() {
		return StringTools.stringIsEmpty(getSpecifiedNamespace()) ? // namespace="" is actually interpreted as unspecified by JAXB tools
				getDefaultNamespace() : getSpecifiedNamespace();
	}
	
	public abstract String getDefaultNamespace();
	
	public String getSpecifiedNamespace() {
		return this.specifiedNamespace;
	}
	
	public void setSpecifiedNamespace(String newSpecifiedNamespace) {
		getAnnotation(true).setNamespace(newSpecifiedNamespace);
		this.setSpecifiedNamespace_(newSpecifiedNamespace);
	}
	
	protected void setSpecifiedNamespace_(String newSpecifiedNamespace) {
		String oldNamespace = this.specifiedNamespace;
		this.specifiedNamespace = newSpecifiedNamespace;
		firePropertyChanged(SPECIFIED_NAMESPACE_PROPERTY, oldNamespace, newSpecifiedNamespace);
	}
	
	protected String buildSpecifiedNamespace() {
		SchemaComponentRefAnnotation annotation = getAnnotation(false);
		return annotation == null ? null : annotation.getNamespace();
	}
	
	
	// ***** name *****
	
	public String getName() {
		return this.getSpecifiedName() == null ? getDefaultName() : getSpecifiedName();
	}
	
	public abstract String getDefaultName();
	
	public String getSpecifiedName() {
		return this.specifiedName;
	}
	
	public void setSpecifiedName(String newSpecifiedName) {
		getAnnotation(true).setName(newSpecifiedName);
		setSpecifiedName_(newSpecifiedName);
	}
	
	protected  void setSpecifiedName_(String newSpecifiedName) {
		String old = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, newSpecifiedName);
	}
	
	protected String buildSpecifiedName() {
		SchemaComponentRefAnnotation annotation = getAnnotation(false);
		return annotation == null ? null : annotation.getName();
	}
	
	
	// ***** content assist *****
	
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
		SchemaComponentRefAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? false : annotation.namespaceTouches(pos, astRoot);
	}
	
	protected abstract Iterable<String> getNamespaceProposals(Filter<String> filter);
	
	protected boolean nameTouches(int pos, CompilationUnit astRoot) {
		SchemaComponentRefAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? false : annotation.nameTouches(pos, astRoot);
	}
	
	protected abstract Iterable<String> getNameProposals(Filter<String> filter);
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getParent().getValidationTextRange(astRoot);
	}
	
	protected TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		SchemaComponentRefAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? null : getTextRange(annotation.getNamespaceTextRange(astRoot), astRoot);
	}
	
	protected TextRange getNameTextRange(CompilationUnit astRoot) {
		SchemaComponentRefAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? null : getTextRange(annotation.getNameTextRange(astRoot), astRoot);
	}
	
	protected TextRange getTextRange(TextRange textRange, CompilationUnit astRoot) {
		return (textRange != null) ? textRange : getParent().getValidationTextRange(astRoot);
	}
	
	/**
	 * e.g. "XML element"
	 */
	protected abstract String getSchemaComponentTypeDescription();
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		validateName(messages, reporter, astRoot);
		
		if (! StringTools.stringIsEmpty(getName())) {
			validateSchemaComponentRef(messages, reporter, astRoot);
		}
	}
	
	protected void validateName(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (StringTools.stringIsEmpty(getName())) {
			messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.SCHEMA_COMPONENT_REF__MISSING_NAME,
					new String[] { getSchemaComponentTypeDescription() },
					this,
					getNameTextRange(astRoot)));
		}
	}
	
	protected abstract void validateSchemaComponentRef(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot);
	
	protected IMessage getUnresolveSchemaComponentMessage(CompilationUnit astRoot) {
		return DefaultValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JaxbValidationMessages.SCHEMA_COMPONENT_REF__UNRESOLVED_COMPONENT,
				new String[] { getSchemaComponentTypeDescription(), getNamespace(), getName() },
				this,
				getValidationTextRange(astRoot));
	}
}
