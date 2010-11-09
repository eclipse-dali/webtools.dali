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
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefsAnnotation;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;

/**
 * javax.xml.bind.annotation.adapters.XmlElementRefs
 */
public class BinaryXmlElementRefsAnnotation
		extends BinaryContainerAnnotation<XmlElementRefAnnotation>
		implements XmlElementRefsAnnotation {
	
	private final Vector<XmlElementRefAnnotation> xmlElementRefs;
	
	
	public BinaryXmlElementRefsAnnotation(JavaResourceAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.xmlElementRefs = this.buildXmlElementRefs();
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	public ListIterable<XmlElementRefAnnotation> getNestedAnnotations() {
		return new LiveCloneListIterable<XmlElementRefAnnotation>(this.xmlElementRefs);
	}
	
	public int getNestedAnnotationsSize() {
		return this.xmlElementRefs.size();
	}
	
	private Vector<XmlElementRefAnnotation> buildXmlElementRefs() {
		Object[] jdtElementRefs = this.getJdtMemberValues(JAXB.XML_ELEMENT_REFS__VALUE);
		Vector<XmlElementRefAnnotation> result = new Vector<XmlElementRefAnnotation>(jdtElementRefs.length);
		for (Object jdtElementRef : jdtElementRefs) {
			result.add(this.buildXmlElementRefAnnotation(jdtElementRef));
		}
		return result;
	}
	
	protected XmlElementRefAnnotation buildXmlElementRefAnnotation(Object jdtElementRef) {
		return new BinaryXmlElementRefAnnotation(this, (IAnnotation) jdtElementRef);
	}
	
	@Override
	public void update() {
		super.update();
		this.updateXmlElementRefs();
	}
	
	// TODO
	private void updateXmlElementRefs() {
		throw new UnsupportedOperationException();
	}
}
