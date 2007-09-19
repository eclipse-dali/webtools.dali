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

public interface PluralTypeAnnotation<E extends SingularTypeAnnotation> extends TypeAnnotation
{
	String getSingularAnnotationName();
	
	ListIterator<E> javaTypeAnnotations();
	
	int javaTypeAnnotationsSize();

	E singularAnnotationAt(int index);
	
	int indexOf(E singularTypeAnnotation);
	
	E add(int index);
	
	void remove(E javaTypeAnnotation);
	
	void remove(int index);
	
	void move(int oldIndex, int newIndex);
}
