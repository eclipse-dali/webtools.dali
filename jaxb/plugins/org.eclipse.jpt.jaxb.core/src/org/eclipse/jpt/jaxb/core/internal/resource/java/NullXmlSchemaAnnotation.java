/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

/**
 * javax.xml.bind.annotation.XmlSchema
 */
public final class NullXmlSchemaAnnotation
	extends NullAnnotation
	implements XmlSchemaAnnotation
{
	protected NullXmlSchemaAnnotation(JavaResourcePackage parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected XmlSchemaAnnotation addAnnotation() {
		return (XmlSchemaAnnotation) super.addAnnotation();
	}


	// ********** XmlSchemaAnnotation implementation **********

	// ***** namespace
	public String getNamespace() {
		return null;
	}

	public void setNamespace(String namespace) {
		if (namespace != null) {
			this.addAnnotation().setNamespace(namespace);
		}
	}

	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** location
	public String getLocation() {
		return null;
	}

	public void setLocation(String location) {
		if (location != null) {
			this.addAnnotation().setLocation(location);
		}
	}

	public TextRange getLocationTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return null;
	}

	public XmlNsForm getAttributeFormDefault() {
		return null;
	}

	public void setAttributeFormDefault(XmlNsForm attributeFormDefault) {
		if (attributeFormDefault != null) {
			this.addAnnotation().setAttributeFormDefault(attributeFormDefault);
		}
	}

	public TextRange getAttributeFormDefaultTextRange(CompilationUnit astRoot) {
		return null;
	}

	public XmlNsForm getElementFormDefault() {
		return null;
	}

	public void setElementFormDefault(XmlNsForm elementFormDefault) {
		if (elementFormDefault != null) {
			this.addAnnotation().setElementFormDefault(elementFormDefault);
		}
	}

	public TextRange getElementFormDefaultTextRange(CompilationUnit astRoot) {
		return null;
	}

	public ListIterable<XmlNsAnnotation> getXmlns() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getXmlnsSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public XmlNsAnnotation xmlnsAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public XmlNsAnnotation addXmlns(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public void moveXmlns(int targetIndex, int sourceIndex) {
		// TODO Auto-generated method stub
		
	}

	public void removeXmlns(int index) {
		// TODO Auto-generated method stub
		
	}

}
