/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jaxb.core.resource.java.XmlSeeAlsoAnnotation;

/**
 * javax.xml.bind.annotation.XmlSeeAlso
 */
public final class BinaryXmlSeeAlsoAnnotation
		extends BinaryAnnotation
		implements XmlSeeAlsoAnnotation {
	
	private final Vector<String> classes;


	public BinaryXmlSeeAlsoAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.classes = this.buildClasses();
	}

	public String getAnnotationName() {
		return JAXB.XML_SEE_ALSO;
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
		return IterableTools.cloneLive(this.classes);
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
	
	public ListIterable<String> getFullyQualifiedClasses() {
		return getClasses();
	}

	// TODO
	private void updateClasses() {
		throw new UnsupportedOperationException();
	}

}
