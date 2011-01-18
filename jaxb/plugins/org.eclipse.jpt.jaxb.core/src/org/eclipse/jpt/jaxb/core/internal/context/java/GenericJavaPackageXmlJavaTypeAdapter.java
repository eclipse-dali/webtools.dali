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
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
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
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (getSpecifiedType() == null || getSpecifiedType().equals(XmlJavaTypeAdapter.DEFAULT_TYPE)) {
			messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.PACKAGE_XML_JAVA_TYPE_ADAPTER_TYPE_NOT_SPECIFIED,
					this,
					getResourceXmlJavaTypeAdapter().getTypeTextRange(astRoot)));
		}

	}
}
