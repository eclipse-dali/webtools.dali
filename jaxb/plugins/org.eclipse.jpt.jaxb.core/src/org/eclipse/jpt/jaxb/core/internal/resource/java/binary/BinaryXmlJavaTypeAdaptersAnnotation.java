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
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryContainerAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdaptersAnnotation;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;

/**
 * javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters
 */
public class BinaryXmlJavaTypeAdaptersAnnotation
		extends BinaryContainerAnnotation<XmlJavaTypeAdapterAnnotation>
		implements XmlJavaTypeAdaptersAnnotation {
	
	private final Vector<XmlJavaTypeAdapterAnnotation> xmlJavaTypeAdapters;
	
	
	public BinaryXmlJavaTypeAdaptersAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.xmlJavaTypeAdapters = this.buildXmlJavaTypeAdapters();
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	public Iterable<XmlJavaTypeAdapterAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<XmlJavaTypeAdapterAnnotation>(this.xmlJavaTypeAdapters);
	}
	
	public int getNestedAnnotationsSize() {
		return this.xmlJavaTypeAdapters.size();
	}
	
	private Vector<XmlJavaTypeAdapterAnnotation> buildXmlJavaTypeAdapters() {
		Object[] jdtTypeAdapters = this.getJdtMemberValues(JAXB.XML_JAVA_TYPE_ADAPTERS__VALUE);
		Vector<XmlJavaTypeAdapterAnnotation> result = new Vector<XmlJavaTypeAdapterAnnotation>(jdtTypeAdapters.length);
		for (Object jdtJavaTypeAdapter : jdtTypeAdapters) {
			result.add(this.buildXmlJavaTypeAdapterAnnotation(jdtJavaTypeAdapter));
		}
		return result;
	}
	
	protected XmlJavaTypeAdapterAnnotation buildXmlJavaTypeAdapterAnnotation(Object jdtJavaTypeAdapter) {
		return new BinaryXmlJavaTypeAdapterAnnotation(this, (IAnnotation) jdtJavaTypeAdapter);
	}
	
	@Override
	public void update() {
		super.update();
		this.updateXmlJavaTypeAdapters();
	}
	
	// TODO
	private void updateXmlJavaTypeAdapters() {
		throw new UnsupportedOperationException();
	}
}
