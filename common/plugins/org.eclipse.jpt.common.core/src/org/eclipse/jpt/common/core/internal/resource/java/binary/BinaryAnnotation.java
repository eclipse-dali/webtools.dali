/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;

/**
 * JAR annotation
 */
public abstract class BinaryAnnotation
	extends BinaryNode
	implements Annotation
{
	final IAnnotation jdtAnnotation;

	protected BinaryAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent);
		this.jdtAnnotation = jdtAnnotation;
	}

	// ********** convenience methods **********

	/**
	 * Return the values of the JDT annotation's member with the specified name.
	 */
	protected Object[] getJdtMemberValues(String memberName) {
		return this.getJdtMemberValues(this.jdtAnnotation, memberName);
	}

	/**
	 * Return the value of the JDT annotation's member with the specified name.
	 */
	protected Object getJdtMemberValue(String memberName) {
		return this.getJdtMemberValue(this.jdtAnnotation, memberName);
	}


	// ********** Annotation implementation **********

	public org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	public void newAnnotation() {
		throw new UnsupportedOperationException();
	}
	public void removeAnnotation() {
		throw new UnsupportedOperationException();
	}
	public boolean isUnset() {
		throw new UnsupportedOperationException();
	}

	// ********** NestableAnnotation implementation **********
	public void moveAnnotation(@SuppressWarnings("unused") int index) {
		throw new UnsupportedOperationException();
	}

}
