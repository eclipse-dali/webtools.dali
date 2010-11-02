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

import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;

public class GenericJavaXmlNs
	extends AbstractJaxbContextNode
	implements XmlNs
{

	protected final XmlNsAnnotation resourceXmlNs;

	protected String namespaceURI;

	protected String prefix;

	public GenericJavaXmlNs(XmlSchema parent, XmlNsAnnotation xmlNsAnnotation) {
		super(parent);
		this.resourceXmlNs = xmlNsAnnotation;
		this.namespaceURI = this.getResourceNamespaceURI();
		this.prefix = this.getResourcePrefix();
	}

	public XmlNsAnnotation getResourceXmlNs() {
		return this.resourceXmlNs;
	}

	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		this.setNamespaceURI_(this.getResourceNamespaceURI());
		this.setPrefix_(this.getResourcePrefix());
	}

	public void update() {
		//nothing yet
	}


	@Override
	public JaxbPackageInfo getParent() {
		return (JaxbPackageInfo) super.getParent();
	}

	protected JavaResourcePackage getResourcePackage() {
		return getParent().getResourcePackage();
	}


	// ********** namespaceURI **********

	public String getNamespaceURI() {
		return this.namespaceURI;
	}

	public void setNamespaceURI(String namespace) {
		this.resourceXmlNs.setNamespaceURI(namespace);
		this.setNamespaceURI_(namespace);	
	}

	protected void setNamespaceURI_(String namespaceURI) {
		String old = this.namespaceURI;
		this.namespaceURI = namespaceURI;
		this.firePropertyChanged(NAMESPACE_URI_PROPERTY, old, namespaceURI);
	}

	protected String getResourceNamespaceURI() {
		return this.resourceXmlNs.getNamespaceURI();
	}

	// ********** prefix **********

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.resourceXmlNs.setPrefix(prefix);
		this.setPrefix_(prefix);	
	}

	protected void setPrefix_(String prefix) {
		String old = this.prefix;
		this.prefix = prefix;
		this.firePropertyChanged(PREFIX_PROPERTY, old, prefix);
	}

	protected String getResourcePrefix() {
		return this.resourceXmlNs.getPrefix();
	}


	//****************** miscellaneous ********************

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.namespaceURI);
	}
}
