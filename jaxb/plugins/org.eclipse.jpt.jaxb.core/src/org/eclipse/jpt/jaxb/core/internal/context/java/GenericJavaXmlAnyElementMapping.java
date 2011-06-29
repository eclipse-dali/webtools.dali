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

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAdaptable;
import org.eclipse.jpt.jaxb.core.context.XmlAnyElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.context.XmlMixed;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAnyElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMixedAnnotation;

public class GenericJavaXmlAnyElementMapping
	extends AbstractJavaAttributeMapping<XmlAnyElementAnnotation>
	implements XmlAnyElementMapping
{

	protected Boolean specifiedLax;

	protected String specifiedValue;

	protected XmlMixed xmlMixed;

	protected final XmlAdaptable xmlAdaptable;


	public GenericJavaXmlAnyElementMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.specifiedLax = buildSpecifiedLax();
		this.specifiedValue = this.getResourceValueString();
		this.xmlAdaptable = buildXmlAdaptable();
		this.initializeXmlMixed();			
	}

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedLax(buildSpecifiedLax());
		this.setSpecifiedValue_(this.getResourceValueString());
		this.xmlAdaptable.synchronizeWithResourceModel();
		this.syncXmlMixed();
	}

	@Override
	public void update() {
		super.update();
		this.xmlAdaptable.update();
		this.updateXmlMixed();
	}

	public String getKey() {
		return MappingKeys.XML_ANY_ELEMENT_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return XmlAnyElementAnnotation.ANNOTATION_NAME;
	}


	//************ lax ***************

	public boolean isLax() {
		return (this.getSpecifiedLax() == null) ? this.isDefaultLax() : this.getSpecifiedLax().booleanValue();
	}

	public boolean isDefaultLax() {
		return DEFAULT_LAX;
	}

	public Boolean getSpecifiedLax() {
		return this.specifiedLax;
	}

	public void setSpecifiedLax(Boolean newSpecifiedLax) {
		this.getOrCreateAnnotation().setLax(newSpecifiedLax);
		this.setSpecifiedLax_(newSpecifiedLax);
	}

	protected void setSpecifiedLax_(Boolean newSpecifiedLax) {
		Boolean oldLax = this.specifiedLax;
		this.specifiedLax = newSpecifiedLax;
		firePropertyChanged(SPECIFIED_LAX_PROPERTY, oldLax, newSpecifiedLax);
	}

	protected Boolean buildSpecifiedLax() {
		return getAnnotation().getLax();
	}

	// ********** value **********

	public String getValue() {
		return this.getSpecifiedValue() == null ? this.getDefaultValue() : this.getSpecifiedValue();
	}

	public String getDefaultValue() {
		return DEFAULT_VALUE;
	}

	public String getSpecifiedValue() {
		return this.specifiedValue;
	}

	public void setSpecifiedValue(String location) {
		this.getOrCreateAnnotation().setValue(location);
		this.setSpecifiedValue_(location);	
	}

	protected void setSpecifiedValue_(String type) {
		String old = this.specifiedValue;
		this.specifiedValue = type;
		this.firePropertyChanged(SPECIFIED_VALUE_PROPERTY, old, type);
	}

	protected String getResourceValueString() {
		return getAnnotation().getValue();
	}

	//****************** XmlJavaTypeAdapter *********************

	public XmlAdaptable buildXmlAdaptable() {
		return new GenericJavaXmlAdaptable(this, new XmlAdaptable.Owner() {
			public JavaResourceAnnotatedElement getResource() {
				return getJavaResourceAttribute();
			}
			public XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation adapterAnnotation) {
				return GenericJavaXmlAnyElementMapping.this.buildXmlJavaTypeAdapter(adapterAnnotation);
			}
			public void fireXmlAdapterChanged(XmlJavaTypeAdapter oldAdapter, XmlJavaTypeAdapter newAdapter) {
				GenericJavaXmlAnyElementMapping.this.firePropertyChanged(XML_JAVA_TYPE_ADAPTER_PROPERTY, oldAdapter, newAdapter);
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


	//************  XmlMixed ***************

	public XmlMixed getXmlMixed() {
		return this.xmlMixed;
	}

	public XmlMixed addXmlMixed() {
		if (this.xmlMixed != null) {
			throw new IllegalStateException();
		}
		XmlMixedAnnotation annotation = (XmlMixedAnnotation) this.getJavaResourceAttribute().addAnnotation(XmlMixedAnnotation.ANNOTATION_NAME);

		XmlMixed xmlMixed = this.buildXmlMixed(annotation);
		this.setXmlMixed_(xmlMixed);
		return xmlMixed;
	}

	protected XmlMixed buildXmlMixed(XmlMixedAnnotation xmlMixedAnnotation) {
		return new GenericJavaXmlMixed(this, xmlMixedAnnotation);
	}

	public void removeXmlMixed() {
		if (this.xmlMixed == null) {
			throw new IllegalStateException();
		}
		this.getJavaResourceAttribute().removeAnnotation(XmlMixedAnnotation.ANNOTATION_NAME);
		this.setXmlMixed_(null);
	}

	protected void initializeXmlMixed() {
		XmlMixedAnnotation annotation = this.getXmlMixedAnnotation();
		if (annotation != null) {
			this.xmlMixed = this.buildXmlMixed(annotation);
		}
	}

	protected XmlMixedAnnotation getXmlMixedAnnotation() {
		return (XmlMixedAnnotation) this.getJavaResourceAttribute().getAnnotation(XmlMixedAnnotation.ANNOTATION_NAME);
	}

	protected void syncXmlMixed() {
		XmlMixedAnnotation annotation = this.getXmlMixedAnnotation();
		if (annotation != null) {
			if (this.getXmlMixed() != null) {
				this.getXmlMixed().synchronizeWithResourceModel();
			}
			else {
				this.setXmlMixed_(this.buildXmlMixed(annotation));
			}
		}
		else {
			this.setXmlMixed_(null);
		}
	}

	protected void updateXmlMixed() {
		if (this.getXmlMixed() != null) {
			this.getXmlMixed().update();
		}
	}

	protected void setXmlMixed_(XmlMixed xmlMixed) {
		XmlMixed oldXmlMixed = this.xmlMixed;
		this.xmlMixed = xmlMixed;
		firePropertyChanged(XML_MIXED_PROPERTY, oldXmlMixed, xmlMixed);
	}
}
