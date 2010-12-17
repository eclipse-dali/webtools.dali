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

import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;

public class GenericJavaXmlSchemaType
	extends AbstractJaxbContextNode
	implements XmlSchemaType
{

	protected final XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation;

	protected String name;

	protected String namespace;

	protected String type;

	public GenericJavaXmlSchemaType(JaxbContextNode parent, XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation) {
		super(parent);
		this.xmlSchemaTypeAnnotation = xmlSchemaTypeAnnotation;
		this.name = this.getResourceName();
		this.namespace = this.getResourceNamespace();
		this.type = this.getResourceTypeString();
	}


	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		this.setName_(this.getResourceName());
		this.setNamespace_(this.getResourceNamespace());
		this.setType_(this.getResourceTypeString());
	}

	public void update() {
		//nothing yet
	}


	// ********** xml schema type annotation **********

	public XmlSchemaTypeAnnotation getResourceXmlSchemaType() {
		return this.xmlSchemaTypeAnnotation;
	}

	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.xmlSchemaTypeAnnotation.setName(name);
		this.setName_(name);	
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	protected String getResourceName() {
		return this.xmlSchemaTypeAnnotation.getName();
	}

	// ********** namespace **********

	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String location) {
		this.xmlSchemaTypeAnnotation.setNamespace(location);
		this.setNamespace_(location);	
	}

	protected void setNamespace_(String namespace) {
		String old = this.namespace;
		this.namespace = namespace;
		this.firePropertyChanged(NAMESPACE_PROPERTY, old, namespace);
	}

	protected String getResourceNamespace() {
		return this.xmlSchemaTypeAnnotation.getNamespace();
	}

	// ********** type **********

	public String getType() {
		return this.type;
	}

	public void setType(String location) {
		this.xmlSchemaTypeAnnotation.setType(location);
		this.setType_(location);	
	}

	protected void setType_(String type) {
		String old = this.type;
		this.type = type;
		this.firePropertyChanged(TYPE_PROPERTY, old, type);
	}

	protected String getResourceTypeString() {
		return this.xmlSchemaTypeAnnotation.getType();
	}

}
