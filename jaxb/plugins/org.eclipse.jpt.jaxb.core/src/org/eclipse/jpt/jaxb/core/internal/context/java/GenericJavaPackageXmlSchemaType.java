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
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaPackageXmlSchemaType
	extends GenericJavaXmlSchemaType
{

	public GenericJavaPackageXmlSchemaType(JaxbPackageInfo parent, XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation) {
		super(parent, xmlSchemaTypeAnnotation);
	}

	@Override
	public JaxbPackageInfo getParent() {
		return (JaxbPackageInfo) super.getParent();
	}

	protected JaxbPackageInfo getJaxbPackageInfo() {
		return getParent();
	}

	@Override
	protected JaxbPackage getJaxbPackage() {
		return getJaxbPackageInfo().getJaxbPackage();
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		String fqType = getFullyQualifiedType();
		if (fqType == null || XmlSchemaTypeAnnotation.DEFAULT_TYPE.equals(fqType)) {
			messages.add(
					this.buildValidationMessage(
							getValidationTextRange(),
							JptJaxbCoreValidationMessages.XML_SCHEMA_TYPE__TYPE_NOT_SPECIFIED_ON_PACKAGE
						));
		}
	}
}
