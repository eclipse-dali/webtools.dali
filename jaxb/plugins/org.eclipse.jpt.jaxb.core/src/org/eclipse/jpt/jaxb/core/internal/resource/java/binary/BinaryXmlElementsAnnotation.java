/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementsAnnotation;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;

/**
 * javax.xml.bind.annotation.XmlElements
 */
public final class BinaryXmlElementsAnnotation
	extends BinaryAnnotation
	implements XmlElementsAnnotation
{
	private final Vector<XmlElementAnnotation> xmlElements;


	public BinaryXmlElementsAnnotation(JavaResourceAttribute parent, IAnnotation jdtAnnotation) {
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
		return ANNOTATION_NAME;
	}

	public ListIterable<XmlElementAnnotation> getXmlElements() {
		return new LiveCloneListIterable<XmlElementAnnotation>(this.xmlElements);
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
