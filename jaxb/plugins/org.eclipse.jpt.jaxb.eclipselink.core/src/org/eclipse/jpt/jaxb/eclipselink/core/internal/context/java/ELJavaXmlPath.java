/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaContextNode;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlPath;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.xpath.java.XPath;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.xpath.java.XPathFactory;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlPathAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.validation.JptJaxbEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlPath
		extends AbstractJavaContextNode
		implements ELXmlPath {
		
	protected String value;
	
	protected Context context;
	
	
	public ELJavaXmlPath(JaxbContextNode parent, Context context) {
		super(parent);
		this.context = context;
		initValue();
	}
	
	
	protected JaxbPackage getJaxbPackage() {
		return getClassMapping().getJaxbPackage();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncValue();
	}
	
	
	// ***** value *****
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		getAnnotation().setValue(value);
		setValue_(value);
	}
	
	protected void setValue_(String value) {
		String old = this.value;
		this.value = value;
		firePropertyChanged(VALUE_PROPERTY, old, this.value);
	}
	
	protected void initValue() {
		this.value = getAnnotation().getValue();
	}
	
	protected void syncValue() {
		setValue_(getAnnotation().getValue());
	}
	
	protected XmlPathAnnotation getAnnotation() {
		return this.context.getAnnotation();
	}
	
	protected JaxbAttributeMapping getAttributeMapping() {
		return this.context.getAttributeMapping();
	}
	
	protected JaxbClassMapping getClassMapping() {
		return getAttributeMapping().getClassMapping();
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		
		if (getAnnotation().valueTouches(pos) && this.value != null) {
			XsdTypeDefinition xsdType = getClassMapping().getXsdTypeDefinition();
			XPath xpath = XPathFactory.instance().getXpath(this.value);
			return xpath.getCompletionProposals(new XPathContext(), xsdType, pos);
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
		
		if (StringTools.isBlank(this.value)) {
			messages.add(
					this.buildValidationMessage(
								ELJavaXmlPath.this,
								getValueTextRange(),
								JptJaxbEclipseLinkCoreValidationMessages.XML_PATH__NOT_SPECIFIED
							));
			return;
		}
		
		if (this.value.startsWith(XPath.DELIM)) {
			messages.add(
					this.buildValidationMessage(
								ELJavaXmlPath.this,
								getValueTextRange(),
								JptJaxbEclipseLinkCoreValidationMessages.XPATH__ROOT_NOT_SUPPORTED
							));
			return;
		}
		
		XsdTypeDefinition xsdType = getClassMapping().getXsdTypeDefinition();
		XPath xpath = XPathFactory.instance().getXpath(this.value);
		xpath.validate(new XPathContext(), xsdType, messages);
	}
	
	protected TextRange getValueTextRange() {
		// should never be null
		return getAnnotation().getValueTextRange();
	}
	
	
	public interface Context {
		
		XmlPathAnnotation getAnnotation();
		
		JaxbAttributeMapping getAttributeMapping();
	}
	
	
	protected class XPathContext
		implements XPath.Context {
		
		protected XPathContext() {
		}
		
		
		public JaxbNode getContextObject() {
			return ELJavaXmlPath.this;
		}
		
		public JaxbPackage getJaxbPackage() {
			return ELJavaXmlPath.this.getJaxbPackage();
		}
		
		public TextRange getTextRange() {
			return ELJavaXmlPath.this.getValueTextRange();
		}
	}
}
