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

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.JaxbBasicMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlID;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlID
	extends AbstractJavaContextNode
	implements XmlID
{

	protected final XmlIDAnnotation resourceXmlID;

	public GenericJavaXmlID(JaxbBasicMapping parent, XmlIDAnnotation resource) {
		super(parent);
		this.resourceXmlID = resource;
	}

	@Override
	public JaxbBasicMapping getParent() {
		return (JaxbBasicMapping) super.getParent();
	}

	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getParent().getPersistentAttribute();
	}


	//************* validation ****************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (!getPersistentAttribute().isJavaResourceAttributeTypeSubTypeOf(String.class.getName())) {
			messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.XML_ID_DEFINED_ON_NON_STRING,
					this,
					getValidationTextRange(astRoot)));
		}
	}

	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourceXmlID.getTextRange(astRoot);
	}
}
