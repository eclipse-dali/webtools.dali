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
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlDiscriminatorValue;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlDiscriminatorValueAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.validation.JptJaxbEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlDiscriminatorValue
		extends AbstractJavaContextNode
		implements ELXmlDiscriminatorValue {
	
	protected String value;
	
	
	public ELJavaXmlDiscriminatorValue(ELJavaClassMapping parent) {
		super(parent);
		initValue();
	}
	
	
	protected ELJavaClassMapping getClassMapping() {
		return (ELJavaClassMapping) getParent();
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
	
	protected XmlDiscriminatorValueAnnotation getAnnotation() {
		return getClassMapping().getXmlDiscriminatorValueAnnotation();
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
								ELJavaXmlDiscriminatorValue.this,
								getValueTextRange(),
								JptJaxbEclipseLinkCoreValidationMessages.XML_DISCRIMINATOR_VALUE__NOT_SPECIFIED
							));
			return;
		}
	}
	
	protected TextRange getValueTextRange() {
		// should never be null
		return getAnnotation().getValueTextRange();
	}
}
