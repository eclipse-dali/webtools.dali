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
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;

/**
 * javax.xml.bind.annotation.XmlSchema
 */
public final class NullXmlSchemaAnnotation
	extends NullAnnotation
	implements XmlSchemaAnnotation
{
	protected NullXmlSchemaAnnotation(JavaResourceNode parent) {
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
	
	public boolean namespaceTouches(int pos, CompilationUnit astRoot) {
		return false;
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
		return EmptyListIterable.instance();
	}

	public int getXmlnsSize() {
		return 0;
	}

	public XmlNsAnnotation xmlnsAt(int index) {
		return null;
	}

	public XmlNsAnnotation addXmlns(int index) {
		return this.addAnnotation().addXmlns(index);
	}

	public void moveXmlns(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeXmlns(int index) {
		throw new UnsupportedOperationException();
	}

}
