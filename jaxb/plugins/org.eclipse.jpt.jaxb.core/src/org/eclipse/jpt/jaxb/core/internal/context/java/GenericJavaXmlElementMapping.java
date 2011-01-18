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

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAdaptable;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlElementMapping
	extends AbstractJavaAttributeMapping<XmlElementAnnotation>
	implements XmlElementMapping
{

	protected String specifiedName;

	protected Boolean specifiedNillable;

	protected Boolean specifiedRequired;

	protected String specifiedNamespace;

	protected String defaultValue;

	protected String specifiedType;

	protected final XmlAdaptable xmlAdaptable;

	public GenericJavaXmlElementMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.specifiedName = this.buildSpecifiedName();
		this.specifiedNillable = this.buildSpecifiedNillable();
		this.specifiedRequired = this.buildSpecifiedRequired();
		this.specifiedNamespace = this.buildSpecifiedNamespace();
		this.defaultValue = this.buildDefaultValue();
		this.specifiedType = this.buildSpecifiedType();
		this.xmlAdaptable = this.buildXmlAdaptable();
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
		this.setSpecifiedName_(this.buildSpecifiedName());
		this.setSpecifiedNillable_(this.buildSpecifiedNillable());
		this.setSpecifiedRequired_(this.buildSpecifiedRequired());
		this.setSpecifiedNamespace_(this.buildSpecifiedNamespace());
		this.setDefaultValue_(this.buildDefaultValue());
		this.setSpecifiedType_(this.buildSpecifiedType());
		this.xmlAdaptable.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.xmlAdaptable.update();
	}

	//************ XmlElement.name ***************
	public String getName() {
		return this.getSpecifiedName() == null ? this.getDefaultName() : getSpecifiedName();
	}

	public String getDefaultName() {
		return getJavaResourceAttribute().getName();
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String name) {
		this.getAnnotationForUpdate().setName(name);
		this.setSpecifiedName_(name);
	}

	protected  void setSpecifiedName_(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedName() {
		return getMappingAnnotation() == null ? null : getMappingAnnotation().getName();
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


	//************  XmlElement.required ***************

	public boolean isRequired() {
		return (this.getSpecifiedRequired() == null) ? this.isDefaultRequired() : this.getSpecifiedRequired().booleanValue();
	}

	public boolean isDefaultRequired() {
		return DEFAULT_REQUIRED;
	}

	public Boolean getSpecifiedRequired() {
		return this.specifiedRequired;
	}

	public void setSpecifiedRequired(Boolean newSpecifiedRequired) {
		this.getAnnotationForUpdate().setRequired(newSpecifiedRequired);
		this.setSpecifiedRequired_(newSpecifiedRequired);
	}

	protected void setSpecifiedRequired_(Boolean newSpecifiedRequired) {
		Boolean oldRequired = this.specifiedRequired;
		this.specifiedRequired = newSpecifiedRequired;
		firePropertyChanged(SPECIFIED_REQUIRED_PROPERTY, oldRequired, newSpecifiedRequired);
	}

	protected Boolean buildSpecifiedRequired() {
		return getMappingAnnotation() == null ? null : getMappingAnnotation().getRequired();
	}


	//************  XmlElement.namespace ***************

	public String getNamespace() {
		return getSpecifiedNamespace() == null ? getDefaultNamespace() : getSpecifiedNamespace();
	}

	public String getDefaultNamespace() {
		//TODO calculate default namespace
		return null;
	}

	public String getSpecifiedNamespace() {
		return this.specifiedNamespace;
	}

	public void setSpecifiedNamespace(String newSpecifiedNamespace) {
		this.getAnnotationForUpdate().setNamespace(newSpecifiedNamespace);
		this.setSpecifiedNamespace_(newSpecifiedNamespace);
	}

	protected void setSpecifiedNamespace_(String newSpecifiedNamespace) {
		String oldNamespace = this.specifiedNamespace;
		this.specifiedNamespace = newSpecifiedNamespace;
		firePropertyChanged(SPECIFIED_NAMESPACE_PROPERTY, oldNamespace, newSpecifiedNamespace);
	}

	protected String buildSpecifiedNamespace() {
		return getMappingAnnotation() == null ? null : getMappingAnnotation().getNamespace();
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


	//****************** XmlJavaTypeAdapter *********************

	public XmlAdaptable buildXmlAdaptable() {
		return new GenericJavaXmlAdaptable(this, new XmlAdaptable.Owner() {
			public JavaResourceAnnotatedElement getResource() {
				return getJavaResourceAttribute();
			}
			public XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation adapterAnnotation) {
				return GenericJavaXmlElementMapping.this.buildXmlJavaTypeAdapter(adapterAnnotation);
			}
			public void fireXmlAdapterChanged(XmlJavaTypeAdapter oldAdapter, XmlJavaTypeAdapter newAdapter) {
				GenericJavaXmlElementMapping.this.firePropertyChanged(XML_JAVA_TYPE_ADAPTER_PROPERTY, oldAdapter, newAdapter);
			}
		});
	}

	public XmlJavaTypeAdapter getXmlJavaTypeAdapter() {
		return this.xmlAdaptable.getXmlJavaTypeAdapter();
	}

	public XmlJavaTypeAdapter addXmlJavaTypeAdapter() {
		return this.xmlAdaptable.addXmlJavaTypeAdapter();
	}

	protected XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation) {
		return new GenericJavaAttributeXmlJavaTypeAdapter(this, xmlJavaTypeAdapterAnnotation);
	}

	public void removeXmlJavaTypeAdapter() {
		this.xmlAdaptable.removeXmlJavaTypeAdapter();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.xmlAdaptable.validate(messages, reporter, astRoot);
	}
}
