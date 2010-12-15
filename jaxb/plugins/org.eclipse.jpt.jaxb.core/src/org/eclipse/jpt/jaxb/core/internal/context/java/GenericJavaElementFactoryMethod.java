/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementDeclAnnotation;

public class GenericJavaElementFactoryMethod
		extends AbstractJaxbContextNode
		implements JaxbElementFactoryMethod {

	protected final JavaResourceMethod resourceMethod;

	protected String elementName;

	public GenericJavaElementFactoryMethod(JaxbRegistry parent, JavaResourceMethod resourceMethod) {
		super(parent);
		this.resourceMethod = resourceMethod;
		this.elementName = this.getResourceElementName();
	}

	public JavaResourceMethod getResourceMethod() {
		return this.resourceMethod;
	}

	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		this.setElementName_(this.getResourceElementName());
	}

	public void update() {
		// nothing yet
	}

	// ********** xml enum value annotation **********

	protected XmlElementDeclAnnotation getXmlElementDeclAnnotation() {
		return (XmlElementDeclAnnotation) this.getResourceMethod().getNonNullAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
	}


	// ************** JaxbElementFactoryMethod impl ***********

	public String getName() {
		return this.resourceMethod.getName();
	}

	// ********** element name **********

	public String getElementName() {
		return this.elementName;
	}


	public void setElementName(String elementName) {
		this.getXmlElementDeclAnnotation().setName(elementName);
		this.setElementName_(elementName);	
	}

	protected void setElementName_(String elementName) {
		String old = this.elementName;
		this.elementName = elementName;
		this.firePropertyChanged(ELEMENT_NAME_PROPERTY, old, elementName);
	}

	protected String getResourceElementName() {
		return this.getXmlElementDeclAnnotation().getName();
	}
}
