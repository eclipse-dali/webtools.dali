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
import org.eclipse.jpt.jaxb.core.resource.java.AbstractJavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSeeAlsoAnnotation;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;

/**
 * javax.xml.bind.annotation.XmlSeeAlso
 */
public final class BinaryXmlSeeAlsoAnnotation
	extends BinaryAnnotation
	implements XmlSeeAlsoAnnotation
{
	private final Vector<String> classes;


	public BinaryXmlSeeAlsoAnnotation(AbstractJavaResourceType parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.classes = this.buildClasses();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.updateClasses();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.classes);
	}


	// ********** XmlSeeAlsoAnnotation implementation **********

	// ***** value
	public ListIterable<String> getClasses() {
		return new LiveCloneListIterable<String>(this.classes);
	}

	public int getClassesSize() {
		return this.classes.size();
	}

	private Vector<String> buildClasses() {
		Object[] jdtClasses = this.getJdtMemberValues(JAXB.XML_SEE_ALSO__VALUE);
		Vector<String> result = new Vector<String>(jdtClasses.length);
		for (Object jdtClass : jdtClasses) {
			result.add((String) jdtClass);
		}
		return result;
	}

	public void addClass(String clazz) {
		throw new UnsupportedOperationException();
	}

	public void addClass(int index, String clazz) {
		throw new UnsupportedOperationException();
	}

	public void moveClass(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeClass(String clazz) {
		throw new UnsupportedOperationException();
	}

	public void removeClass(int index) {
		throw new UnsupportedOperationException();
	}

	// TODO
	private void updateClasses() {
		throw new UnsupportedOperationException();
	}

}
