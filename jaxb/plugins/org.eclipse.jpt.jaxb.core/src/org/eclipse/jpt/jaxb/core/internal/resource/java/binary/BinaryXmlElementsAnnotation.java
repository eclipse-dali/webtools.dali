/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;

/**
 * javax.xml.bind.annotation.adapters.XmlElements
 */
public class BinaryXmlElementsAnnotation
		extends BinaryContainerAnnotation<XmlElementAnnotation>
		implements XmlElementsAnnotation {
	
	private final Vector<XmlElementAnnotation> xmlElements;
	
	
	public BinaryXmlElementsAnnotation(JavaResourceAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.xmlElements = this.buildXmlElements();
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	public Iterable<XmlElementAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<XmlElementAnnotation>(this.xmlElements);
	}
	
	public int getNestedAnnotationsSize() {
		return this.xmlElements.size();
	}
	
	private Vector<XmlElementAnnotation> buildXmlElements() {
		Object[] jdtElements = this.getJdtMemberValues(JAXB.XML_ELEMENTS__VALUE);
		Vector<XmlElementAnnotation> result = new Vector<XmlElementAnnotation>(jdtElements.length);
		for (Object jdtElement : jdtElements) {
			result.add(this.buildXmlElementAnnotation(jdtElement));
		}
		return result;
	}
	
	protected XmlElementAnnotation buildXmlElementAnnotation(Object jdtElement) {
		return new BinaryXmlElementAnnotation(this, (IAnnotation) jdtElement);
	}
	
	@Override
	public void update() {
		super.update();
		this.updateXmlElements();
	}
	
	// TODO
	private void updateXmlElements() {
		throw new UnsupportedOperationException();
	}
}
