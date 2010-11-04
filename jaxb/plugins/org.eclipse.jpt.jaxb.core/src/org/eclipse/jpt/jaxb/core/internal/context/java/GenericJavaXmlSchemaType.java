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

	protected String specifiedNamespace;

	protected String specifiedType;

	public GenericJavaXmlSchemaType(JaxbContextNode parent, XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation) {
		super(parent);
		this.xmlSchemaTypeAnnotation = xmlSchemaTypeAnnotation;
		this.name = this.getResourceName();
		this.specifiedNamespace = this.getResourceNamespace();
		this.specifiedType = this.getResourceTypeString();
	}


	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		this.setName_(this.getResourceName());
		this.setSpecifiedNamespace_(this.getResourceNamespace());
		this.setSpecifiedType_(this.getResourceTypeString());
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
		return (this.specifiedNamespace != null) ? this.specifiedNamespace : this.getDefaultNamespace();
	}

	public String getSpecifiedNamespace() {
		return this.specifiedNamespace;
	}

	public void setSpecifiedNamespace(String location) {
		this.xmlSchemaTypeAnnotation.setNamespace(location);
		this.setSpecifiedNamespace_(location);	
	}

	protected void setSpecifiedNamespace_(String namespace) {
		String old = this.specifiedNamespace;
		this.specifiedNamespace = namespace;
		this.firePropertyChanged(SPECIFIED_NAMESPACE_PROPERTY, old, namespace);
	}

	public String getDefaultNamespace() {
		return DEFAULT_NAMESPACE;
	}

	protected String getResourceNamespace() {
		return this.xmlSchemaTypeAnnotation.getNamespace();
	}

	// ********** type **********

	public String getType() {
		return (this.specifiedType != null) ? this.specifiedType : this.getDefaultType();
	}

	public String getSpecifiedType() {
		return this.specifiedType;
	}

	public void setSpecifiedType(String location) {
		this.xmlSchemaTypeAnnotation.setType(location);
		this.setSpecifiedType_(location);	
	}

	protected void setSpecifiedType_(String type) {
		String old = this.specifiedType;
		this.specifiedType = type;
		this.firePropertyChanged(SPECIFIED_TYPE_PROPERTY, old, type);
	}

	public String getDefaultType() {
		return DEFAULT_TYPE;
	}

	protected String getResourceTypeString() {
		return this.xmlSchemaTypeAnnotation.getType();
	}

}
