/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.binary;

import java.util.Vector;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;


public class BinaryXmlSchemaAnnotation
		extends BinaryAnnotation
		implements XmlSchemaAnnotation {


	private XmlNsForm attributeFormDefault;

	private XmlNsForm elementFormDefault;
	
	private String location;
	
	private String namespace;
	
	private final Vector<XmlNsAnnotation> xmlNses;
	
	
	public BinaryXmlSchemaAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.attributeFormDefault = buildAttributeFormDefault();
		this.elementFormDefault = buildElementFormDefault();
		this.location = buildLocation();
		this.namespace = buildNamespace();
		this.xmlNses = buildXmlNses();
	}
	
	
	public String getAnnotationName() {
		return JAXB.XML_SCHEMA;
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.namespace);
	}
	
	
	// **************** attribute form default ********************************
	
	public XmlNsForm getAttributeFormDefault() {
		return this.attributeFormDefault;
	}
	
	public void setAttributeFormDefault(XmlNsForm attributeFormDefault) {
		throw new UnsupportedOperationException();
	}
	
	private XmlNsForm buildAttributeFormDefault() {
		return XmlNsForm.fromJavaAnnotationValue(this.getJdtMemberValue(JAXB.XML_SCHEMA__ATTRIBUTE_FORM_DEFAULT));
	}
	
	public TextRange getAttributeFormDefaultTextRange() {
		throw new UnsupportedOperationException();
	}
	
	
	// **************** element form default ********************************
	
	public XmlNsForm getElementFormDefault() {
		return this.elementFormDefault;
	}
	
	public void setElementFormDefault(XmlNsForm elementFormDefault) {
		throw new UnsupportedOperationException();
	}
	
	private XmlNsForm buildElementFormDefault() {
		return XmlNsForm.fromJavaAnnotationValue(this.getJdtMemberValue(JAXB.XML_SCHEMA__ELEMENT_FORM_DEFAULT));
	}

	public TextRange getElementFormDefaultTextRange() {
		throw new UnsupportedOperationException();
	}
	
	
	// **************** location **********************************************
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String location) {
		throw new UnsupportedOperationException();
	}
	
	private String buildLocation() {
		return (String) this.getJdtMemberValue(JAXB.XML_SCHEMA__LOCATION);
	}
	

	public TextRange getLocationTextRange() {
		throw new UnsupportedOperationException();
	}
	
	
	// **************** namespace *********************************************
	
	public String getNamespace() {
		return this.namespace;
	}
	
	public void setNamespace(String namespace) {
		throw new UnsupportedOperationException();
	}
	
	private String buildNamespace() {
		return (String) getJdtMemberValue(JAXB.XML_SCHEMA__NAMESPACE);
	}

	public TextRange getNamespaceTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public boolean namespaceTouches(int pos) {
		throw new UnsupportedOperationException();
	}
	
	
	// **************** xmlns *************************************************
	
	private Vector<XmlNsAnnotation> buildXmlNses() {
		Object[] jdtXmlNses = this.getJdtMemberValues(JAXB.XML_SCHEMA__XMLNS);
		Vector<XmlNsAnnotation> result = new Vector<XmlNsAnnotation>(jdtXmlNses.length);
		for (Object jdtXmlNs : jdtXmlNses) {
			result.add(new BinaryXmlNsAnnotation(this, (IAnnotation) jdtXmlNs));
		}
		return result;
	}
	
	
	public ListIterable<XmlNsAnnotation> getXmlns() {
		return new LiveCloneListIterable<XmlNsAnnotation>(this.xmlNses);
	}
	
	public int getXmlnsSize() {
		return this.xmlNses.size();
	}
	
	public XmlNsAnnotation xmlnsAt(int index) {
		return this.xmlNses.get(index);
	}
	
	public XmlNsAnnotation addXmlns(int index) {
		throw new UnsupportedOperationException();
	}
	
	public void moveXmlns(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}
	
	public void removeXmlns(int index) {
		throw new UnsupportedOperationException();
	}
	
}
