/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.ListIterator;

import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Common behavior for all annotation "containers".
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface AnnotationContainer<T extends NestableAnnotation>
{
	/**
	 * Return the name of the container annotation.
	 * Used when traversing the AST.
	 */
	String getContainerAnnotationName();

	/**
	 * Return the corresponding JDT DOM annotation from the specified
	 * AST compilation unit. Used as a starting point when traversing the AST.
	 */
	org.eclipse.jdt.core.dom.Annotation getContainerJdtAnnotation(CompilationUnit astRoot);

	/**
	 * Return the name of the container annotation's element that is used
	 * to indicate the nested annotations (typically "value").
	 * Used when traversing the AST.
	 */
	String getElementName();

	/**
	 * Return the name of the nested annotations held by the container.
	 * Used when traversing the AST.
	 */
	String getNestableAnnotationName();

	/**
	 * Return the nested annotations held by the container.
	 */
	ListIterator<T> nestedAnnotations();

	/**
	 * Return the number of nested annotations held by the container.
	 */
	int nestedAnnotationsSize();

	/**
	 * Add a nested annotation to the container
	 * without firing change notification.
	 */
	T addNestedAnnotationInternal();

	/**
	 * The specified nested annotation was added to the container at the
	 * specified index; notify interested parties.
	 */
	void nestedAnnotationAdded(int index, T nestedAnnotation);

	/**
	 * Move the nested annotation at the specified source index in the
	 * container to the specified target index without firing change notification.
	 * Return the moved nested annotation.
	 */
	T moveNestedAnnotationInternal(int targetIndex, int sourceIndex);

	/**
	 * A nested annotation was moved within the container annotation from the
	 * specified source index to the specified target index; notify interested
	 * parties.
	 */
	void nestedAnnotationMoved(int targetIndex, int sourceIndex);

	/**
	 * Remove the nested annotation at the specified index from the
	 * container without firing change notification.
	 */
	T removeNestedAnnotationInternal(int index);

	/**
	 * The specified nested annotation was removed from the container
	 * at the specified index; notify interested parties.
	 */
	void nestedAnnotationRemoved(int index, T nestedAnnotation);

}
