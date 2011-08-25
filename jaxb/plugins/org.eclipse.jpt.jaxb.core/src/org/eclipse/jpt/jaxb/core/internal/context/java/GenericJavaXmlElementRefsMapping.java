/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlElementRefsMapping;
import org.eclipse.jpt.jaxb.core.context.XmlMixed;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMixedAnnotation;


public class GenericJavaXmlElementRefsMapping
		extends AbstractJavaAttributeMapping<XmlElementRefsAnnotation>
		implements XmlElementRefsMapping {
	
	protected XmlMixed xmlMixed;
	
	
	public GenericJavaXmlElementRefsMapping(JaxbPersistentAttribute parent) {
		super(parent);
		initializeXmlMixed();			
	}
	
	
	public String getKey() {
		return MappingKeys.XML_ELEMENT_REFS_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return JAXB.XML_ELEMENT_REFS;
	}
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
//		this.xmlElementRef.synchronizeWithResourceModel();
//		this.xmlAdaptable.synchronizeWithResourceModel();
//		syncXmlElementWrapper();
		syncXmlMixed();
	}
	
	@Override
	public void update() {
		super.update();
//		this.xmlElementRef.update();
//		this.xmlAdaptable.update();
//		updateXmlElementWrapper();
		updateXmlMixed();
	}
	
	
	// ***** XmlMixed *****
	
	public XmlMixed getXmlMixed() {
		return this.xmlMixed;
	}
	
	protected void setXmlMixed_(XmlMixed xmlMixed) {
		XmlMixed oldXmlMixed = this.xmlMixed;
		this.xmlMixed = xmlMixed;
		firePropertyChanged(XML_MIXED_PROPERTY, oldXmlMixed, xmlMixed);
	}
	
	public XmlMixed addXmlMixed() {
		if (this.xmlMixed != null) {
			throw new IllegalStateException();
		}
		XmlMixedAnnotation annotation = (XmlMixedAnnotation) getJavaResourceAttribute().addAnnotation(JAXB.XML_MIXED);
		
		XmlMixed xmlMixed = buildXmlMixed(annotation);
		setXmlMixed_(xmlMixed);
		return xmlMixed;
	}
	
	public void removeXmlMixed() {
		if (this.xmlMixed == null) {
			throw new IllegalStateException();
		}
		getJavaResourceAttribute().removeAnnotation(JAXB.XML_MIXED);
		setXmlMixed_(null);
	}
	
	protected XmlMixedAnnotation getXmlMixedAnnotation() {
		return (XmlMixedAnnotation) getJavaResourceAttribute().getAnnotation(JAXB.XML_MIXED);
	}
	
	protected XmlMixed buildXmlMixed(XmlMixedAnnotation xmlMixedAnnotation) {
		return new GenericJavaXmlMixed(this, xmlMixedAnnotation);
	}
	
	protected void initializeXmlMixed() {
		XmlMixedAnnotation annotation = getXmlMixedAnnotation();
		if (annotation != null) {
			this.xmlMixed = buildXmlMixed(annotation);
		}
	}
	
	protected void syncXmlMixed() {
		XmlMixedAnnotation annotation = getXmlMixedAnnotation();
		if (annotation != null) {
			if (this.xmlMixed != null) {
				this.xmlMixed.synchronizeWithResourceModel();
			}
			else {
				setXmlMixed_(buildXmlMixed(annotation));
			}
		}
		else {
			setXmlMixed_(null);
		}
	}

	protected void updateXmlMixed() {
		if (this.xmlMixed != null) {
			this.xmlMixed.update();
		}
	}
}
