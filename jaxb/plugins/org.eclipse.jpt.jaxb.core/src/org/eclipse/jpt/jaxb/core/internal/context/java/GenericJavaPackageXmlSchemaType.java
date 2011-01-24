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

import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;

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
		return getJaxbPackageInfo().getParent();
	}
}
