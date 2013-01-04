/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractQName
		extends AbstractJaxbContextNode
		implements JaxbQName {
	
	protected final ResourceProxy proxy;
	
	protected String namespace;
	protected String defaultNamespace;
	protected String specifiedNamespace;
	
	protected String name;
	protected String defaultName;
	protected String specifiedName;
	
	
	protected AbstractQName(JaxbContextNode parent, ResourceProxy proxy) {
		super(parent);
		this.proxy = proxy;
		this.specifiedNamespace = getResourceNamespace();
		this.specifiedName = getResourceName();
	}
	
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncNamespace();
		syncName();
	}
	
	@Override
	public void update() {
		super.update();
		updateNamespace();
		updateName();
	}
	
	
	protected abstract JaxbPackage getJaxbPackage();
	
	protected final XsdSchema getXsdSchema() {
		JaxbPackage jaxbPackage = this.getJaxbPackage();
		return (jaxbPackage == null) ? null : jaxbPackage.getXsdSchema();
	}
	
	
	// ***** namespace *****
	
	public String getNamespace() {
		return this.namespace;
	}
	
	protected void setNamespace_(String newNamespace) {
		String oldNamespace = this.namespace;
		this.namespace = newNamespace;
		firePropertyChanged(NAMESPACE_PROPERTY, oldNamespace, newNamespace);
	}
	
	public String getDefaultNamespace() {
		return this.defaultNamespace;
	}
	
	protected void setDefaultNamespace_(String newDefaultNamespace) {
		String oldDefaultNamespace = this.defaultNamespace;
		this.defaultNamespace = newDefaultNamespace;
		firePropertyChanged(DEFAULT_NAMESPACE_PROPERTY, oldDefaultNamespace, newDefaultNamespace);
	}
	
	public String getSpecifiedNamespace() {
		return this.specifiedNamespace;
	}
	
	public void setSpecifiedNamespace(String newSpecifiedNamespace) {
		setResourceNamespace(newSpecifiedNamespace);
		setSpecifiedNamespace_(newSpecifiedNamespace);
	}
	
	protected void setSpecifiedNamespace_(String newSpecifiedNamespace) {
		String oldNamespace = this.specifiedNamespace;
		this.specifiedNamespace = newSpecifiedNamespace;
		firePropertyChanged(SPECIFIED_NAMESPACE_PROPERTY, oldNamespace, newSpecifiedNamespace);
	}
	
	protected abstract String buildDefaultNamespace();
	
	protected String getResourceNamespace() {
		return this.proxy.getNamespace();
	}
	
	protected void setResourceNamespace(String newNamespace) {
		this.proxy.setNamespace(newNamespace);
	}
	
	protected void syncNamespace() {
		setSpecifiedNamespace_(getResourceNamespace());
	}
	
	protected void updateNamespace() {
		setDefaultNamespace_(buildDefaultNamespace());
		
		String namespace = this.specifiedNamespace;
		
		// if specified namespace is "unspecified", use default namespace
		if (StringTools.isBlank(namespace)  // namespace="" is actually interpreted as unspecified by JAXB tools
				|| ObjectTools.equals(namespace, JAXB.DEFAULT_STRING)) {
			namespace = this.defaultNamespace;
		}
		
		setNamespace_(namespace);
	}
	
	
	// ***** name *****
	
	public String getName() {
		return this.name;
	}
	
	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	public String getDefaultName() {
		return this.defaultName;
	}
	
	protected void setDefaultName_(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		firePropertyChanged(DEFAULT_NAME_PROPERTY, oldDefaultName, newDefaultName);
	}
	
	public String getSpecifiedName() {
		return this.specifiedName;
	}
	
	public void setSpecifiedName(String newSpecifiedName) {
		setResourceName(newSpecifiedName);
		setSpecifiedName_(newSpecifiedName);
	}
	
	protected  void setSpecifiedName_(String newSpecifiedName) {
		String old = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, newSpecifiedName);
	}
	
	protected abstract String buildDefaultName();
	
	protected String getResourceName() {
		return this.proxy.getName();
	}
	
	protected void setResourceName(String newName) {
		this.proxy.setName(newName);
	}
	
	protected void syncName() {
		setSpecifiedName_(getResourceName());
	}
	
	protected void updateName() {
		setDefaultName_(buildDefaultName());
		
		String name = this.specifiedName;
		
		// if specified name is "unspecified", use default namespace
		if (name == null
				|| ObjectTools.equals(name, JAXB.DEFAULT_STRING)) {
			name = this.defaultName;
		}
		
		setName_(name);
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		if (this.proxy.namespaceTouches(pos)) {
			return getNamespaceProposals();
		}
		
		if (this.proxy.nameTouches(pos)) {
			return getNameProposals();
		}
		
		return EmptyIterable.instance();
	}
	
	protected abstract Iterable<String> getNamespaceProposals();
	
	protected abstract Iterable<String> getNameProposals();
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		return getParent().getValidationTextRange();
	}
	
	protected TextRange getTextRange(TextRange textRange) {
		return (textRange != null) ? textRange : getParent().getValidationTextRange();
	}
	
	public TextRange getNamespaceTextRange() {
		return getTextRange(this.proxy.getNamespaceTextRange());
	}
	
	public TextRange getNameTextRange() {
		return getTextRange(this.proxy.getNameTextRange());
	}
	
	/**
	 * e.g. "XML element"
	 */
	protected String getReferencedComponentTypeDescription() {
		// default impl
		return null;
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateName(messages, reporter);
		
		if (! StringTools.isBlank(getName())) {
			validateReference(messages, reporter);
		}
	}
	
	protected void validateName(List<IMessage> messages, IReporter reporter) {
		if (StringTools.isBlank(getName())) {
			messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.QNAME__MISSING_NAME,
					new String[] { getReferencedComponentTypeDescription() },
					this,
					getNameTextRange()));
		}
	}
	
	protected abstract void validateReference(List<IMessage> messages, IReporter reporter);
	
	protected IMessage getUnresolveSchemaComponentMessage() {
		return DefaultValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JaxbValidationMessages.QNAME__UNRESOLVED_COMPONENT,
				new String[] { getReferencedComponentTypeDescription(), getNamespace(), getName() },
				this,
				getNameTextRange());
	}
	
	
	public interface ResourceProxy {
		
		String getNamespace();
		
		void setNamespace(String namespace);
		
		boolean namespaceTouches(int pos);
		
		TextRange getNamespaceTextRange();
		
		String getName();
		
		void setName(String name);
		
		boolean nameTouches(int pos);
		
		TextRange getNameTextRange();
	}
}
