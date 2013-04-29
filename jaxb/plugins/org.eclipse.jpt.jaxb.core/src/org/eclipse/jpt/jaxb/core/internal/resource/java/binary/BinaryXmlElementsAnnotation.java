/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementsAnnotation;

/**
 * javax.xml.bind.annotation.XmlElements
 */
public final class BinaryXmlElementsAnnotation
		extends BinaryAnnotation
		implements XmlElementsAnnotation {
	
	private final Vector<XmlElementAnnotation> xmlElements;


	public BinaryXmlElementsAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.xmlElements = this.buildXmlElements();
	}

	private Vector<XmlElementAnnotation> buildXmlElements() {
		Object[] jdtXmlElements = this.getJdtMemberValues(JAXB.XML_ELEMENTS__VALUE);
		Vector<XmlElementAnnotation> result = new Vector<XmlElementAnnotation>(jdtXmlElements.length);
		for (Object jdtXmlElement : jdtXmlElements) {
			result.add(new BinaryXmlElementAnnotation(this, (IAnnotation) jdtXmlElement));
		}
		return result;
	}

	public String getAnnotationName() {
		return JAXB.XML_ELEMENTS;
	}

	public ListIterable<XmlElementAnnotation> getXmlElements() {
		return IterableTools.cloneLive(this.xmlElements);
	}

	public int getXmlElementsSize() {
		return this.xmlElements.size();
	}

	public XmlElementAnnotation xmlElementAt(int index) {
		return this.xmlElements.elementAt(index);
	}

	public XmlElementAnnotation addXmlElement(int index) {
		throw new UnsupportedOperationException();
	}

	public void moveXmlElement(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeXmlElement(int index) {
		throw new UnsupportedOperationException();
	}
}
