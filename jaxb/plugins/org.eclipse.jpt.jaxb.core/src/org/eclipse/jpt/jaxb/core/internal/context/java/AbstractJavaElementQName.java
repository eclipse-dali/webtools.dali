/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaElementQName
		extends AbstractJavaQName {
	
	public AbstractJavaElementQName(JaxbContextNode parent, AbstractJavaQName.AnnotationProxy proxy) {
		super(parent, proxy);
	}
	
		
	@Override
	public String getReferencedComponentTypeDescription() {
		return JptJaxbCoreMessages.XML_ELEMENT_DESC;
	}
	
	protected abstract JaxbPersistentAttribute getPersistentAttribute();
	
	protected JaxbClassMapping getJaxbClassMapping() {
		return getPersistentAttribute().getClassMapping();
	}
	
	@Override
	protected JaxbPackage getJaxbPackage() {
		return getJaxbClassMapping().getJaxbType().getJaxbPackage();
	}
	
	protected abstract XmlElementWrapper getElementWrapper();
	
	@Override
	public String getDefaultName() {
		return getPersistentAttribute().getJavaResourceAttribute().getName();
	}
	
	@Override
	public Iterable<String> getNameProposals() {
		XsdTypeDefinition xsdType = getJaxbClassMapping().getXsdTypeDefinition();
		if (xsdType == null) {
			return EmptyIterable.instance();
		}
		
		XmlElementWrapper elementWrapper = getElementWrapper();
		
		if (elementWrapper == null) {
			return xsdType.getElementNameProposals(getNamespace());
		}
		else {
			XsdElementDeclaration xsdWrapperElement = elementWrapper.getXsdElementDeclaration();
			if (xsdWrapperElement != null) {
				return xsdWrapperElement.getElementNameProposals(getNamespace());
			}
		}
		
		return EmptyIterable.instance();
	}
	
	@Override
	public String getDefaultNamespace() {
		JaxbPackage jaxbPackage = this.getJaxbPackage();
		return (jaxbPackage != null && jaxbPackage.getElementFormDefault() == XmlNsForm.QUALIFIED) ?
				getJaxbClassMapping().getQName().getNamespace() : "";
	}
	
	@Override
	public Iterable<String> getNamespaceProposals() {
		XsdSchema schema = this.getXsdSchema();
		return (schema == null) ? EmptyIterable.<String>instance() : schema.getNamespaceProposals();
	}

	@Override
	protected void validateReference(List<IMessage> messages, IReporter reporter) {
		XsdTypeDefinition xsdType = getJaxbClassMapping().getXsdTypeDefinition();
		if (xsdType == null) {
			return;
		}
		
		XsdElementDeclaration resolvedXsdElement = null;
		
		XmlElementWrapper elementWrapper = getElementWrapper();
		
		if (elementWrapper == null) {
			resolvedXsdElement = xsdType.getElement(getNamespace(), getName());
		}
		else {
			XsdElementDeclaration xsdWrapperElement = elementWrapper.getXsdElementDeclaration();
			if (xsdWrapperElement == null) {
				// there will be a separate message for unresolved wrapper element
				// no need to also have a message for the nested element
				return;
			}
			resolvedXsdElement = xsdWrapperElement.getElement(getNamespace(), getName());
		}
		
		if (resolvedXsdElement == null) {
			messages.add(getUnresolveSchemaComponentMessage());
		}
	}
}
