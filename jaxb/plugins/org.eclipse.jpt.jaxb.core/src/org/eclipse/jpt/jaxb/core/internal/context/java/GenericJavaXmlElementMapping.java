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

import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;

public class GenericJavaXmlElementMapping
	extends GenericJavaContainmentMapping<XmlElementAnnotation>
	implements XmlElementMapping
{

	protected Boolean specifiedNillable;

	protected String defaultValue;

	protected String specifiedType;

	public GenericJavaXmlElementMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.specifiedNillable = this.buildSpecifiedNillable();
		this.defaultValue = this.buildDefaultValue();
		this.specifiedType = this.buildSpecifiedType();
	}

	public String getKey() {
		return MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return XmlElementAnnotation.ANNOTATION_NAME;
	}

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedNillable_(this.buildSpecifiedNillable());
		this.setDefaultValue_(this.buildDefaultValue());
		this.setSpecifiedType_(this.buildSpecifiedType());
	}


	//************  XmlElement.nillable ***************

	public boolean isNillable() {
		return (this.getSpecifiedNillable() == null) ? this.isDefaultNillable() : this.getSpecifiedNillable().booleanValue();
	}

	public boolean isDefaultNillable() {
		return DEFAULT_NILLABLE;
	}

	public Boolean getSpecifiedNillable() {
		return this.specifiedNillable;
	}

	public void setSpecifiedNillable(Boolean newSpecifiedNillable) {
		this.getAnnotationForUpdate().setNillable(newSpecifiedNillable);
		this.setSpecifiedNillable_(newSpecifiedNillable);
	}

	protected void setSpecifiedNillable_(Boolean newSpecifiedNillable) {
		Boolean oldNillable = this.specifiedNillable;
		this.specifiedNillable = newSpecifiedNillable;
		firePropertyChanged(SPECIFIED_NILLABLE_PROPERTY, oldNillable, newSpecifiedNillable);
	}

	protected Boolean buildSpecifiedNillable() {
		return getMappingAnnotation() == null ? null : getMappingAnnotation().getNillable();
	}


	//************  XmlElement.defaultValue ***************

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.getAnnotationForUpdate().setDefaultValue(defaultValue);
		this.setDefaultValue_(defaultValue);
	}

	protected void setDefaultValue_(String defaultValue) {
		String oldDefaultValue = this.defaultValue;
		this.defaultValue = defaultValue;
		firePropertyChanged(DEFAULT_VALUE_PROPERTY, oldDefaultValue, defaultValue);		
	}

	protected String buildDefaultValue() {
		return getMappingAnnotation() == null ? null : getMappingAnnotation().getDefaultValue();
	}

	//************  XmlElement.type ***************

	public String getType() {
		return getSpecifiedType() == null ? getDefaultType() : getSpecifiedType();
	}

	public String getDefaultType() {
		//TODO calculate default type
		return null;
	}

	public String getSpecifiedType() {
		return this.specifiedType;
	}

	public void setSpecifiedType(String newSpecifiedType) {
		this.getAnnotationForUpdate().setType(newSpecifiedType);
		this.setSpecifiedType_(newSpecifiedType);
	}

	protected void setSpecifiedType_(String newSpecifiedType) {
		String oldType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldType, newSpecifiedType);
	}

	protected String buildSpecifiedType() {
		return getMappingAnnotation() == null ? null : getMappingAnnotation().getType();
	}
}
