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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.XmlAdapter;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;

public abstract class AbstractJavaXmlJavaTypeAdapter
		extends AbstractJavaContextNode
		implements XmlJavaTypeAdapter {
	
	protected final XmlJavaTypeAdapterAnnotation annotation;
	
	protected String value;
	
	protected XmlAdapter xmlAdapter;
	
	protected String specifiedType;
	
	protected String defaultType;
	
	
	protected AbstractJavaXmlJavaTypeAdapter(JaxbContextNode parent, XmlJavaTypeAdapterAnnotation annotation) {
		super(parent);
		this.annotation = annotation;
		this.value = getResourceValue();
		initializeXmlAdapter();
		this.specifiedType = getResourceTypeString();
		this.defaultType = buildDefaultType();
	}
	
	
	public XmlJavaTypeAdapterAnnotation getAnnotation() {
		return this.annotation;
	}
	
	
	// ***** synchronize/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setValue_(getResourceValue());
		syncXmlAdapter();
		setSpecifiedType_(getResourceTypeString());
		setDefaultType(buildDefaultType());
	}
	
	@Override
	public void update() {
		super.update();
		updateXmlAdapter();
	}
	
	
	// ***** value *****
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.annotation.setValue(value);
		this.setValue_(value);	
	}
	
	protected void setValue_(String value) {
		String old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}
	
	protected String getResourceValue() {
		return this.annotation.getValue();
	}
	
	public String getFullyQualifiedValue() {
		return this.annotation.getFullyQualifiedValue();
	}
	
	
	// ***** xmlAdapter *****
	
	public XmlAdapter getXmlAdapter() {
		return this.xmlAdapter;
	}
	
	protected void setXmlAdapter_(XmlAdapter xmlAdapter) {
		XmlAdapter old = this.xmlAdapter;
		this.xmlAdapter = xmlAdapter;
		firePropertyChanged(XML_ADAPTER_PROPERTY, old, xmlAdapter);
	}
	
	protected JavaResourceType getXmlAdapterResourceType() {
		String xmlAdapterName = getFullyQualifiedValue();
		return (JavaResourceType) getJaxbProject().getJavaResourceType(xmlAdapterName, JavaResourceAbstractType.Kind.TYPE);
	}
	
	protected XmlAdapter buildXmlAdapter(JavaResourceType xmlAdapterResourceType) {
		return new GenericJavaXmlAdapter(this, xmlAdapterResourceType);
	}
	
	protected void initializeXmlAdapter() {
		JavaResourceType xmlAdapterResourceType = getXmlAdapterResourceType();
		if (xmlAdapterResourceType != null) {
			this.xmlAdapter = buildXmlAdapter(xmlAdapterResourceType);
		}
	}
	
	protected void syncXmlAdapter() {
		if (this.xmlAdapter != null) {
			this.xmlAdapter.synchronizeWithResourceModel();
		}
	}
	
	protected void updateXmlAdapter() {
		JavaResourceType newResourceType = getXmlAdapterResourceType();
		if (newResourceType == null) {
			setXmlAdapter_(null);
		}
		else {
			if (this.xmlAdapter == null) {
				setXmlAdapter_(buildXmlAdapter(newResourceType));
			}
			else {
				JavaResourceType currentResourceType = this.xmlAdapter.getJavaResourceType();
				if (currentResourceType == null || ! currentResourceType.equals(newResourceType)) {
					setXmlAdapter_(buildXmlAdapter(newResourceType));
				}
				else {
					this.xmlAdapter.update();
				}
			}
		}
	}
	
	
	// ***** type *****
	
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
		this.annotation.setType(location);
		this.setSpecifiedType_(location);	
	}
	
	protected void setSpecifiedType_(String type) {
		String old = this.specifiedType;
		this.specifiedType = type;
		this.firePropertyChanged(SPECIFIED_TYPE_PROPERTY, old, type);
	}
	
	protected String getResourceTypeString() {
		return this.annotation.getType();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.annotation.getTextRange(astRoot);
	}
}
