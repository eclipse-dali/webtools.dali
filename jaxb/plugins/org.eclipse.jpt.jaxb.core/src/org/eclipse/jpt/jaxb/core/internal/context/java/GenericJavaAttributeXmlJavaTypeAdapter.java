/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaAttributeXmlJavaTypeAdapter
	extends AbstractJavaXmlJavaTypeAdapter
{

	public GenericJavaAttributeXmlJavaTypeAdapter(JaxbAttributeMapping parent, XmlJavaTypeAdapterAnnotation resource) {
		super(parent, resource);
	}

	@Override
	public JaxbAttributeMapping getParent() {
		return (JaxbAttributeMapping) super.getParent();
	}

	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getParent().getPersistentAttribute();
	}

	// ********** type **********

	@Override
	protected String buildDefaultType() {
		return this.getPersistentAttribute().getJavaResourceAttributeBaseTypeName();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (getType() == null || getFullyQualifiedType().equals(XmlJavaTypeAdapter.DEFAULT_TYPE)) {
			messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JptJaxbCoreValidationMessages.ATTRIBUTE_MAPPING_XML_JAVA_TYPE_ADAPTER_TYPE_NOT_DEFINED,
					this,
					getAnnotation().getTypeTextRange()));
		}
	}
}
