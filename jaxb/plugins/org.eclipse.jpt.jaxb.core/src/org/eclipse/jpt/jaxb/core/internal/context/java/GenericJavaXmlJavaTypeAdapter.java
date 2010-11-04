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
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;

public class GenericJavaXmlJavaTypeAdapter
	extends AbstractJaxbContextNode
	implements XmlJavaTypeAdapter
{

	protected final XmlJavaTypeAdapterAnnotation resourceXmlJavaTypeAdapter;

	protected String value;

	protected String specifiedType;

	public GenericJavaXmlJavaTypeAdapter(JaxbContextNode parent, XmlJavaTypeAdapterAnnotation resource) {
		super(parent);
		this.resourceXmlJavaTypeAdapter = resource;
		this.value = this.getResourceValue();
		this.specifiedType = this.getResourceTypeString();
	}


	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		this.setValue_(this.getResourceValue());
		this.setSpecifiedType_(this.getResourceTypeString());
	}

	public void update() {
		//nothing yet
	}


	// ********** xml java type adapter annotation **********

	public XmlJavaTypeAdapterAnnotation getResourceXmlJavaTypeAdapter() {
		return this.resourceXmlJavaTypeAdapter;
	}

	// ********** value **********

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.resourceXmlJavaTypeAdapter.setValue(value);
		this.setValue_(value);	
	}

	protected void setValue_(String value) {
		String old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	protected String getResourceValue() {
		return this.resourceXmlJavaTypeAdapter.getValue();
	}


	// ********** type **********

	public String getType() {
		return (this.specifiedType != null) ? this.specifiedType : this.getDefaultType();
	}

	public String getSpecifiedType() {
		return this.specifiedType;
	}

	public void setSpecifiedType(String location) {
		this.resourceXmlJavaTypeAdapter.setType(location);
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
		return this.resourceXmlJavaTypeAdapter.getType();
	}

}
