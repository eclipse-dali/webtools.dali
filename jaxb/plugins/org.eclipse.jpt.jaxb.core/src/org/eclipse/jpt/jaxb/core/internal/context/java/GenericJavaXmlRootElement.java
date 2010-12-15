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

import org.eclipse.jpt.jaxb.core.context.JaxbPersistentType;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;

public class GenericJavaXmlRootElement
	extends AbstractJaxbContextNode
	implements XmlRootElement
{

	protected final XmlRootElementAnnotation resourceXmlRootElementAnnotation;

	protected String name;

	protected String namespace;

	public GenericJavaXmlRootElement(JaxbPersistentType parent, XmlRootElementAnnotation resourceXmlRootElementAnnotation) {
		super(parent);
		this.resourceXmlRootElementAnnotation = resourceXmlRootElementAnnotation;
		this.name = this.getResourceName();
		this.namespace = this.getResourceNamespace();
	}


	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		this.setName_(this.getResourceName());
		this.setNamespace_(this.getResourceNamespace());
	}

	public void update() {
		//nothing yet
	}


	@Override
	public JaxbPersistentType getParent() {
		return (JaxbPersistentType) super.getParent();
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String namespace) {
		this.resourceXmlRootElementAnnotation.setName(namespace);
		this.setName_(namespace);	
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	protected String getResourceName() {
		return this.resourceXmlRootElementAnnotation.getName();
	}

	// ********** namespace **********

	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		this.resourceXmlRootElementAnnotation.setNamespace(namespace);
		this.setNamespace_(namespace);	
	}

	protected void setNamespace_(String namespace) {
		String old = this.namespace;
		this.namespace = namespace;
		this.firePropertyChanged(NAMESPACE_PROPERTY, old, namespace);
	}

	protected String getResourceNamespace() {
		return this.resourceXmlRootElementAnnotation.getNamespace();
	}


	//****************** miscellaneous ********************

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.name);
	}
}
