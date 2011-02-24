/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.Collection;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlElementWrapper
	extends AbstractJavaContextNode
	implements XmlElementWrapper
{

	protected final XmlElementWrapperAnnotation resourceXmlElementWrapper;

	protected String specifiedName;

	protected Boolean specifiedRequired;

	protected String specifiedNamespace;

	protected Boolean specifiedNillable;

	public GenericJavaXmlElementWrapper(JaxbAttributeMapping parent, XmlElementWrapperAnnotation resource) {
		super(parent);
		this.resourceXmlElementWrapper = resource;
		this.specifiedName = buildSpecifiedName();
		this.specifiedNamespace = buildSpecifiedNamespace();
		this.specifiedRequired = buildSpecifiedRequired();
		this.specifiedNillable = this.buildSpecifiedNillable();
	}

	@Override
	public JaxbAttributeMapping getParent() {
		return (JaxbAttributeMapping) super.getParent();
	}

	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getParent().getParent();
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setSpecifiedName_(buildSpecifiedName());
		setSpecifiedNamespace_(buildSpecifiedNamespace());
		setSpecifiedRequired_(buildSpecifiedRequired());
		this.setSpecifiedNillable_(this.buildSpecifiedNillable());
	}


	//************ XmlElementWrapper.name ***************

	public String getName() {
		return this.getSpecifiedName() == null ? this.getDefaultName() : getSpecifiedName();
	}

	public String getDefaultName() {
		return getParent().getParent().getName();
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String name) {
		this.resourceXmlElementWrapper.setName(name);
		this.setSpecifiedName_(name);
	}

	protected  void setSpecifiedName_(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedName() {
		return this.resourceXmlElementWrapper.getName();
	}


	//************ XmlElementWrapper.required ***************

	public boolean isRequired() {
		return (this.getSpecifiedRequired() == null) ? this.isDefaultRequired() : this.getSpecifiedRequired().booleanValue();
	}

	public boolean isDefaultRequired() {
		return DEFAULT_REQUIRED;
	}

	public Boolean getSpecifiedRequired() {
		return this.specifiedRequired;
	}

	public void setSpecifiedRequired(Boolean newSpecifiedRequired) {
		this.resourceXmlElementWrapper.setRequired(newSpecifiedRequired);
		this.setSpecifiedRequired_(newSpecifiedRequired);
	}

	protected void setSpecifiedRequired_(Boolean newSpecifiedRequired) {
		Boolean oldRequired = this.specifiedRequired;
		this.specifiedRequired = newSpecifiedRequired;
		firePropertyChanged(SPECIFIED_REQUIRED_PROPERTY, oldRequired, newSpecifiedRequired);
	}

	protected Boolean buildSpecifiedRequired() {
		return this.resourceXmlElementWrapper.getRequired();
	}


	//************ XmlElementWrapper.namespace ***************

	public String getNamespace() {
		return getSpecifiedNamespace() == null ? getDefaultNamespace() : getSpecifiedNamespace();
	}

	public String getDefaultNamespace() {
		return null;
		//TODO what is the default namespace? Is the default namespace in GeneraicJavaContainmentMapping even right? ask Paul
		//return getPersistentAttribute().getParent().getNamespace();
	}

	public String getSpecifiedNamespace() {
		return this.specifiedNamespace;
	}

	public void setSpecifiedNamespace(String newSpecifiedNamespace) {
		this.resourceXmlElementWrapper.setNamespace(newSpecifiedNamespace);
		this.setSpecifiedNamespace_(newSpecifiedNamespace);
	}

	protected void setSpecifiedNamespace_(String newSpecifiedNamespace) {
		String oldNamespace = this.specifiedNamespace;
		this.specifiedNamespace = newSpecifiedNamespace;
		firePropertyChanged(SPECIFIED_NAMESPACE_PROPERTY, oldNamespace, newSpecifiedNamespace);
	}

	protected String buildSpecifiedNamespace() {
		return this.resourceXmlElementWrapper.getNamespace();
	}

	//************  XmlElementWrapper.nillable ***************

	public boolean isNillable() {
		return (this.getSpecifiedNillable() == null) ? this.isDefaultNillable() : this.getSpecifiedNillable().booleanValue();
	}

	public boolean isDefaultNillable() {
		return DEFAULT_NILLABLE;
	}

	public Boolean getSpecifiedNillable() {
		return this.specifiedNillable;
	}

	public void setSpecifiedNillable(Boolean newSpecifiedNillable) {
		this.resourceXmlElementWrapper.setNillable(newSpecifiedNillable);
		this.setSpecifiedNillable_(newSpecifiedNillable);
	}

	protected void setSpecifiedNillable_(Boolean newSpecifiedNillable) {
		Boolean oldNillable = this.specifiedNillable;
		this.specifiedNillable = newSpecifiedNillable;
		firePropertyChanged(SPECIFIED_NILLABLE_PROPERTY, oldNillable, newSpecifiedNillable);
	}

	protected Boolean buildSpecifiedNillable() {
		return this.resourceXmlElementWrapper.getNillable();
	}


	//************* validation ****************
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (!getPersistentAttribute().isJavaResourceAttributeTypeSubTypeOf(Collection.class.getName()) && !getPersistentAttribute().isJavaResourceAttributeTypeArray()) {
			messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.XML_ELEMENT_WRAPPER_DEFINED_ON_NON_ARRAY_NON_COLLECTION,
					this,
					getValidationTextRange(astRoot)));
		}
	}

	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourceXmlElementWrapper.getTextRange(astRoot);
	}
}
