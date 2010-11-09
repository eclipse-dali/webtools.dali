/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.jaxb.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jaxb.core.resource.java.NestableAnnotation;

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

	public String getElementName() {
		throw new UnsupportedOperationException();
	}

	public String getNestedAnnotationName() {
		throw new UnsupportedOperationException();
	}

	public T addNestedAnnotation(int index) {
		throw new UnsupportedOperationException();
	}

	public void syncAddNestedAnnotation(Annotation astAnnotation) {
		throw new UnsupportedOperationException();
	}

	public T moveNestedAnnotation(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public T removeNestedAnnotation(int index) {
		throw new UnsupportedOperationException();
	}

	public void syncRemoveNestedAnnotations(int index) {
		throw new UnsupportedOperationException();
	}

}
