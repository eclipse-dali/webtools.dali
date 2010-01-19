/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;

/**
 * JAR annotations do not support most of the container annotation protocol.
 */
public abstract class BinaryContainerAnnotation<T extends NestableAnnotation>
	extends BinaryAnnotation
	implements ContainerAnnotation<T>
{

	protected BinaryContainerAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getContainerAnnotationName() {
		throw new UnsupportedOperationException();
	}

	public Annotation getContainerAstAnnotation(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public String getElementName() {
		throw new UnsupportedOperationException();
	}

	public String getNestableAnnotationName() {
		throw new UnsupportedOperationException();
	}

	public T addNestedAnnotationInternal() {
		throw new UnsupportedOperationException();
	}

	public void nestedAnnotationAdded(int index, T nestedAnnotation) {
		throw new UnsupportedOperationException();
	}

	public T moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public T removeNestedAnnotationInternal(int index) {
		throw new UnsupportedOperationException();
	}

	public void nestedAnnotationRemoved(int index, T nestedAnnotation) {
		throw new UnsupportedOperationException();
	}

}
