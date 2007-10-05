/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ListIterator;

public interface ContainerAnnotation<T extends NestableAnnotation> extends Annotation
{
	String getNestableAnnotationName();

	ListIterator<T> nestedAnnotations();
	
	int nestedAnnotationsSize();

	T nestedAnnotationAt(int index);
	
	T nestedAnnotationFor(org.eclipse.jdt.core.dom.Annotation jdtAnnotation);
	
	int indexOf(Object nestedAnnotation);
	
	T add(int index);
	
	void remove(Object nestedAnnotation);
	
	void remove(int index);
	
	void move(int oldIndex, int newIndex);
	
	T createNestedAnnotation(int index);
}
