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
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaQName
		extends AbstractJavaContextNode 
		implements JaxbQName {
	
	protected final AnnotationProxy proxy;
	
	protected String specifiedNamespace;
	
	protected String specifiedName;
	
	
	public AbstractJavaQName(JavaContextNode parent, AnnotationProxy proxy) {
		super(parent);
		this.proxy = proxy;
		this.specifiedNamespace = getAnnotationNamespace();
		this.specifiedName = getAnnotationName();
	}
	
	
	@Override
	public JavaContextNode getParent() {
		return (JavaContextNode) super.getParent();
	}
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setSpecifiedNamespace_(getAnnotationNamespace());
		setSpecifiedName_(getAnnotationName());
	}
	
	
	protected abstract JaxbPackage getJaxbPackage();
	
	protected final XsdSchema getXsdSchema() {
		JaxbPackage jaxbPackage = this.getJaxbPackage();
		return (jaxbPackage == null) ? null : jaxbPackage.getXsdSchema();
	}
	
	
	// ***** namespace *****
	
	public String getNamespace() {
		if (StringTools.stringIsEmpty(getSpecifiedNamespace())  // namespace="" is actually interpreted as unspecified by JAXB tools
				|| StringTools.stringsAreEqual(getSpecifiedNamespace(), JAXB.DEFAULT_STRING)) {
			return getDefaultNamespace();
		}
		return getSpecifiedNamespace();
	}
	
	public abstract String getDefaultNamespace();
	
	public String getSpecifiedNamespace() {
		return this.specifiedNamespace;
	}
	
	public void setSpecifiedNamespace(String newSpecifiedNamespace) {
		setAnnotationNamespace(newSpecifiedNamespace);
		setSpecifiedNamespace_(newSpecifiedNamespace);
	}
	
	protected void setSpecifiedNamespace_(String newSpecifiedNamespace) {
		String oldNamespace = this.specifiedNamespace;
		this.specifiedNamespace = newSpecifiedNamespace;
		firePropertyChanged(SPECIFIED_NAMESPACE_PROPERTY, oldNamespace, newSpecifiedNamespace);
	}
	
	protected void setAnnotationNamespace(String newNamespace) {
		this.proxy.setNamespace(newNamespace);
	}
	
	protected String getAnnotationNamespace() {
		return this.proxy.getNamespace();
	}
	
	
	// ***** name *****
	
	public String getName() {
		if (getSpecifiedName() == null
				|| StringTools.stringsAreEqual(getSpecifiedName(), JAXB.DEFAULT_STRING)) {
			return getDefaultName();
		}
		return getSpecifiedName();
	}
	
	public abstract String getDefaultName();
	
	public String getSpecifiedName() {
		return this.specifiedName;
	}
	
	public void setSpecifiedName(String newSpecifiedName) {
		setAnnotationName(newSpecifiedName);
		setSpecifiedName_(newSpecifiedName);
	}
	
	protected  void setSpecifiedName_(String newSpecifiedName) {
		String old = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, newSpecifiedName);
	}
	
	protected void setAnnotationName(String newName) {
		this.proxy.setName(newName);
	}
	
	protected String getAnnotationName() {
		return this.proxy.getName();
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(
			int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		if (this.proxy.namespaceTouches(pos, astRoot)) {
			return getNamespaceProposals(filter);
		}
		
		if (this.proxy.nameTouches(pos, astRoot)) {
			return getNameProposals(filter);
		}
		
		return EmptyIterable.instance();
	}
	
	protected abstract Iterable<String> getNamespaceProposals(Filter<String> filter);
	
	protected abstract Iterable<String> getNameProposals(Filter<String> filter);
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getParent().getValidationTextRange(astRoot);
	}
	
	protected TextRange getTextRange(TextRange textRange, CompilationUnit astRoot) {
		return (textRange != null) ? textRange : getParent().getValidationTextRange(astRoot);
	}
	
	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		return getTextRange(this.proxy.getNamespaceTextRange(astRoot), astRoot);
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return getTextRange(this.proxy.getNameTextRange(astRoot), astRoot);
	}
	
	/**
	 * e.g. "XML element"
	 */
	protected String getReferencedComponentTypeDescription() {
		// default impl
		return null;
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		validateName(messages, reporter, astRoot);
		
		if (! StringTools.stringIsEmpty(getName())) {
			validateReference(messages, reporter, astRoot);
		}
	}
	
	protected void validateName(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (StringTools.stringIsEmpty(getName())) {
			messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.QNAME__MISSING_NAME,
					new String[] { getReferencedComponentTypeDescription() },
					this,
					getNameTextRange(astRoot)));
		}
	}
	
	protected abstract void validateReference(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot);
	
	protected IMessage getUnresolveSchemaComponentMessage(CompilationUnit astRoot) {
		return DefaultValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JaxbValidationMessages.QNAME__UNRESOLVED_COMPONENT,
				new String[] { getReferencedComponentTypeDescription(), getNamespace(), getName() },
				this,
				getNameTextRange(astRoot));
	}
	
	
	public interface AnnotationProxy {
		
		String getNamespace();
		
		void setNamespace(String namespace);
		
		boolean namespaceTouches(int pos, CompilationUnit astRoot);
		
		TextRange getNamespaceTextRange(CompilationUnit astRoot);
		
		String getName();
		
		void setName(String name);
		
		boolean nameTouches(int pos, CompilationUnit astRoot);
		
		TextRange getNameTextRange(CompilationUnit astRoot);
	}
	
	
	/**
	 * represents a {@link QNameAnnotation}
	 */
	public static abstract class AbstractQNameAnnotationProxy
			implements AnnotationProxy {
		
		protected abstract QNameAnnotation getAnnotation(boolean createIfNull);
		
		public String getNamespace() {
			QNameAnnotation annotation = getAnnotation(false);
			return annotation == null ? null : annotation.getNamespace();
		}
		
		public void setNamespace(String newSpecifiedNamespace) {
			getAnnotation(true).setNamespace(newSpecifiedNamespace);
		}
		
		public boolean namespaceTouches(int pos, CompilationUnit astRoot) {
			QNameAnnotation annotation = getAnnotation(false);
			return (annotation == null) ? false : annotation.namespaceTouches(pos, astRoot);
		}
		
		public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
			QNameAnnotation annotation = getAnnotation(false);
			return (annotation == null) ? null : annotation.getNamespaceTextRange(astRoot);
		}
			
		public String getName() {
			QNameAnnotation annotation = getAnnotation(false);
			return annotation == null ? null : annotation.getName();
		}
		
		public void setName(String newSpecifiedName) {
			getAnnotation(true).setName(newSpecifiedName);
		}
		
		public boolean nameTouches(int pos, CompilationUnit astRoot) {
			QNameAnnotation annotation = getAnnotation(false);
			return (annotation == null) ? false : annotation.nameTouches(pos, astRoot);
		}
		
		public TextRange getNameTextRange(CompilationUnit astRoot) {
			QNameAnnotation annotation = getAnnotation(false);
			return (annotation == null) ? null : annotation.getNameTextRange(astRoot);
		}
	}
}
