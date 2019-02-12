/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaPackageXmlJavaTypeAdapter
	extends AbstractJavaXmlJavaTypeAdapter
{

	public GenericJavaPackageXmlJavaTypeAdapter(JaxbPackageInfo parent, XmlJavaTypeAdapterAnnotation resource) {
		super(parent, resource);
	}

	@Override
	protected String buildDefaultType() {
		//there is no default type on a package level XmlJavaTypeAdapter, it must be specified
		return null;
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (getSpecifiedType() == null || getFullyQualifiedType().equals(XmlJavaTypeAdapter.DEFAULT_TYPE)) {
			messages.add(
				this.buildValidationMessage(
					getAnnotation().getTypeTextRange(),
					JptJaxbCoreValidationMessages.PACKAGE_XML_JAVA_TYPE_ADAPTER_TYPE_NOT_SPECIFIED
				));
		}

	}
}
