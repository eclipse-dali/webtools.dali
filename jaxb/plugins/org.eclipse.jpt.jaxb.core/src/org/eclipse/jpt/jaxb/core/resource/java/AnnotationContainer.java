/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

/**
 * Common behavior for all annotation "containers".
 * This interface is used mainly in
 * {@link org.eclipse.jpt.jaxb.core.internal.resource.java.source.AnnotationContainerTools}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface AnnotationContainer<T extends NestableAnnotation>
{
	/**
	 * Return the corresponding JDT DOM annotation from the specified
	 * AST compilation unit. Used as a starting point when traversing the AST.
	 */
	org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot);

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
	String getNestedAnnotationName();

	/**
	 * Return the nested annotations held by the container.
	 */
	ListIterable<T> getNestedAnnotations();

	/**
	 * Return the number of nested annotations held by the container.
	 */
	int getNestedAnnotationsSize();

	/**
	 * Add a nested annotation to the container
	 * without firing change notification.
	 */
	T addNestedAnnotation(int index);

	/**
	 * The specified nested annotation was added to the container at the
	 * specified index; notify interested parties.
	 */
	void syncAddNestedAnnotation(org.eclipse.jdt.core.dom.Annotation astAnnotation);

	/**
	 * Move the nested annotation at the specified source index in the
	 * container to the specified target index without firing change notification.
	 * Return the moved nested annotation.
	 */
	T moveNestedAnnotation(int targetIndex, int sourceIndex);

	/**
	 * Remove the nested annotation at the specified index from the
	 * container without firing change notification.
	 */
	T removeNestedAnnotation(int index);

	/**
	 * Remove the nested annotations starting at the specified index from the
	 * container; notify interested parties.
	 */
	void syncRemoveNestedAnnotations(int index);

}
