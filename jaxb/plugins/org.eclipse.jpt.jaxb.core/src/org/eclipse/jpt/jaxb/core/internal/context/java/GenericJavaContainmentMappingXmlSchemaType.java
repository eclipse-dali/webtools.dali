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

import org.eclipse.jpt.jaxb.core.context.JaxbContainmentMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;

public class GenericJavaContainmentMappingXmlSchemaType
		extends GenericJavaXmlSchemaType {
	
	public GenericJavaContainmentMappingXmlSchemaType(JaxbContainmentMapping parent, XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation) {
		super(parent, xmlSchemaTypeAnnotation);
	}

	@Override
	public JaxbContainmentMapping getParent() {
		return (JaxbContainmentMapping) super.getParent();
	}

	protected JaxbContainmentMapping getJaxbContainmentMapping() {
		return getParent();
	}

	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getJaxbContainmentMapping().getParent();
	}

	protected JaxbPersistentClass getPersistentClass() {
		return getPersistentAttribute().getPersistentClass();
	}

	@Override
	protected JaxbPackage getJaxbPackage() {
		return this.getPersistentClass().getJaxbPackage();
	}
}
