/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.binary;

import java.util.Vector;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefsAnnotation;


public class BinaryXmlElementRefsAnnotation
		extends BinaryAnnotation
		implements XmlElementRefsAnnotation {
	
	private final Vector<XmlElementRefAnnotation> xmlElementRefs;
	
	
	public BinaryXmlElementRefsAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.xmlElementRefs = this.buildXmlElementRefs();
	}
	
	
	private Vector<XmlElementRefAnnotation> buildXmlElementRefs() {
		Object[] jdtXmlElementRefs = this.getJdtMemberValues(JAXB.XML_ELEMENT_REFS__VALUE);
		Vector<XmlElementRefAnnotation> result = new Vector<XmlElementRefAnnotation>(jdtXmlElementRefs.length);
		for (Object jdtXmlElementRef : jdtXmlElementRefs) {
			result.add(new BinaryXmlElementRefAnnotation(this, (IAnnotation) jdtXmlElementRef));
		}
		return result;
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public ListIterable<XmlElementRefAnnotation> getXmlElementRefs() {
		return new LiveCloneListIterable<XmlElementRefAnnotation>(this.xmlElementRefs);
	}

	public int getXmlElementRefsSize() {
		return this.xmlElementRefs.size();
	}

	public XmlElementRefAnnotation xmlElementRefAt(int index) {
		return this.xmlElementRefs.elementAt(index);
	}

	public XmlElementRefAnnotation addXmlElementRef(int index) {
		throw new UnsupportedOperationException();
	}

	public void moveXmlElementRef(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeXmlElementRef(int index) {
		throw new UnsupportedOperationException();
	}
}
