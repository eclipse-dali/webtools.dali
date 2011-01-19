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
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttributeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlAttributeMapping
	extends AbstractJavaAttributeMapping<XmlAttributeAnnotation>
	implements XmlAttributeMapping
{

	protected String specifiedName;

	protected Boolean specifiedRequired;

	protected String specifiedNamespace;

	protected final XmlAdaptable xmlAdaptable;

	public GenericJavaXmlAttributeMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.specifiedName = buildSpecifiedName();
		this.specifiedNamespace = buildSpecifiedNamespace();
		this.specifiedRequired = buildSpecifiedRequired();
		this.xmlAdaptable = buildXmlAdaptable();
	}

	public String getKey() {
		return MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return XmlAttributeAnnotation.ANNOTATION_NAME;
	}

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setSpecifiedName_(buildSpecifiedName());
		setSpecifiedNamespace_(buildSpecifiedNamespace());
		setSpecifiedRequired_(buildSpecifiedRequired());
		this.xmlAdaptable.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.xmlAdaptable.update();
	}

	//************ XmlAttribute.name ***************
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


	//************ required ***************

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


	//************ XmlAttribute.namespace ***************

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


	//****************** XmlJavaTypeAdapter *********************

	public XmlAdaptable buildXmlAdaptable() {
		return new GenericJavaXmlAdaptable(this, new XmlAdaptable.Owner() {
			public JavaResourceAnnotatedElement getResource() {
				return getJavaResourceAttribute();
			}
			public XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation adapterAnnotation) {
				return GenericJavaXmlAttributeMapping.this.buildXmlJavaTypeAdapter(adapterAnnotation);
			}
			public void fireXmlAdapterChanged(XmlJavaTypeAdapter oldAdapter, XmlJavaTypeAdapter newAdapter) {
				GenericJavaXmlAttributeMapping.this.firePropertyChanged(XML_JAVA_TYPE_ADAPTER_PROPERTY, oldAdapter, newAdapter);
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
