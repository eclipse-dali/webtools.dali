/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.ListIterator;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ContainerAnnotation<T extends NestableAnnotation> extends Annotation
{
	/**
	 * Return the fully qualified nestable annotation name.  
	 */
	String getNestableAnnotationName();

	/**
	 * Return the element name of the nestable annotation when
	 * it is nested withing the container annotatio as a member value pai  
	 */
	String getElementName();

	ListIterator<T> nestedAnnotations();
	
	int nestedAnnotationsSize();

	T nestedAnnotationAt(int index);
	
	T nestedAnnotationFor(org.eclipse.jdt.core.dom.Annotation jdtAnnotation);
	
	int indexOf(T nestedAnnotation);
		
	T add(int index);
	
	/**
	 * Add directly to the List without firing change notification.
	 */
	T addInternal(int index);
	
	void remove(T nestedAnnotation);
	
	void remove(int index);
	
	/**
	 * Move in the List without firing change notification.
	 */
	void moveInternal(int targetIndex, int sourceIndex);
	
	void move(int targetIndex, int sourceIndex);
	
}
