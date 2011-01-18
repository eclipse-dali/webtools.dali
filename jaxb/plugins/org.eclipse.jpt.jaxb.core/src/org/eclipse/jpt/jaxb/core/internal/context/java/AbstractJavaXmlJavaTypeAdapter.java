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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;

public abstract class AbstractJavaXmlJavaTypeAdapter
	extends AbstractJavaContextNode
	implements XmlJavaTypeAdapter
{

	protected final XmlJavaTypeAdapterAnnotation resourceXmlJavaTypeAdapter;

	protected String value;

	protected String specifiedType;

	protected String defaultType;

	public AbstractJavaXmlJavaTypeAdapter(JaxbContextNode parent, XmlJavaTypeAdapterAnnotation resource) {
		super(parent);
		this.resourceXmlJavaTypeAdapter = resource;
		this.value = this.getResourceValue();
		this.specifiedType = this.getResourceTypeString();
		this.defaultType = this.buildDefaultType();
	}


	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		this.setValue_(this.getResourceValue());
		this.setSpecifiedType_(this.getResourceTypeString());
		this.setDefaultType(this.buildDefaultType());
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
		return this.specifiedTypeNotSet() ? this.getDefaultType() : this.getSpecifiedType();
	}

	/**
	 * @see javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT
	 */
	protected boolean specifiedTypeNotSet() {
		return getSpecifiedType() == null || getSpecifiedType().equals(DEFAULT_TYPE);
	}

	public String getDefaultType() {
		return this.defaultType;
	}

	protected void setDefaultType(String defaultType) {
		String oldDefaultType = this.defaultType;
		this.defaultType = defaultType;
		firePropertyChanged(DEFAULT_TYPE_PROPERTY, oldDefaultType, defaultType);
	}

	protected abstract String buildDefaultType();

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

	protected String getResourceTypeString() {
		return this.resourceXmlJavaTypeAdapter.getType();
	}


	// ********** validation **********

	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getResourceXmlJavaTypeAdapter().getTextRange(astRoot);
	}
}
