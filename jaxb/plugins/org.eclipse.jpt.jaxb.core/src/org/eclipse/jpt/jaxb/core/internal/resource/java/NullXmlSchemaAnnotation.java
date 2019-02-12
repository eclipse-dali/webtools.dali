/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java;

import org.eclipse.jpt.common.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;

/**
 * javax.xml.bind.annotation.XmlSchema
 */
public final class NullXmlSchemaAnnotation
	extends NullAnnotation<XmlSchemaAnnotation>
	implements XmlSchemaAnnotation
{
	protected NullXmlSchemaAnnotation(JavaResourceModel parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return JAXB.XML_SCHEMA;
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

	public TextRange getNamespaceTextRange() {
		return null;
	}
	
	public boolean namespaceTouches(int pos) {
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

	public TextRange getLocationTextRange() {
		return null;
	}

	public TextRange getValueTextRange() {
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

	public TextRange getAttributeFormDefaultTextRange() {
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

	public TextRange getElementFormDefaultTextRange() {
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
