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

import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;

public class GenericJavaAttributeMappingXmlSchemaType
		extends GenericJavaXmlSchemaType {
	
	public GenericJavaAttributeMappingXmlSchemaType(JaxbAttributeMapping parent, XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation) {
		super(parent, xmlSchemaTypeAnnotation);
	}

	@Override
	public JaxbAttributeMapping getParent() {
		return (JaxbAttributeMapping) super.getParent();
	}

	protected JaxbAttributeMapping getAttributeMapping() {
		return getParent();
	}

	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getAttributeMapping().getPersistentAttribute();
	}

	protected JaxbClassMapping getJaxbClassMapping() {
		return getPersistentAttribute().getJaxbClassMapping();
	}

	@Override
	protected JaxbPackage getJaxbPackage() {
		return this.getJaxbClassMapping().getJaxbType().getJaxbPackage();
	}
}
