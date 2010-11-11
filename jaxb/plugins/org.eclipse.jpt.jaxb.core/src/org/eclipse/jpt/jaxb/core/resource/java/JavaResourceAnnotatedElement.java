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
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Java source code or binary annotated element.
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
public interface JavaResourceAnnotatedElement
	extends JavaResourceNode
{
	// ********** annotations **********
	
	/**
	 * String associated with changes to the "annotations" collection
	 */
	String ANNOTATIONS_COLLECTION = "annotations"; //$NON-NLS-1$
	
	/**
	 * Return the member's annotations in the order that they appear.
	 * Do not return duplicate annotations as this error is handled by the Java
	 * compiler.
	 */
	Iterable<Annotation> getAnnotations();
	
	/**
	 * Return the number of annotations.
	 */
	int getAnnotationsSize();
	
	/**
	 * Return the annotation with the specified name.
	 * Return the first if there are duplicates in the source code.
	 */
	Annotation getAnnotation(String annotationName);
	
	/**
	 * Return the specified annotation.
	 * Return the first if there are duplicates in the source code.
	 * Do not return null, but a Null Object instead if no annotation
	 * with the specified name exists in the source code.
	 */
	Annotation getNonNullAnnotation(String annotationName);
	
	/**
	 * Return the nestable annotations with the specified name in the order that
	 * they appear.
	 * If nestable and container annotations are both specified on the
	 * member directly, return only the nestable annotations specified within
	 * the container annotation.
	 */
	// TODO tie the singular and plural annotations together so we can generate
	// a validation error when both are specified
	Iterable<? extends NestableAnnotation> getAnnotations(String nestableAnnotationName, String containerAnnotationName);
	
	/**
	 * Add an annotation with the specified name.
	 * Return the newly-created annotation.
	 */
	Annotation addAnnotation(String annotationName);
	
	/**
	 * Add a new nestable annotation with the specified name.
	 * Create a new container annotation if necessary and add the nestable
	 * annotation to it.
	 * If both the nestable annotation and the container annotation already
	 * exist, then add to the container annotation, leaving the existing
	 * nestable annotation alone.
	 * If only the nestable annotation exists, then create the new container
	 * annotation and move the existing nestable annotation to it along with
	 * the new one. If neither annotation exists, then create a new nestable
	 * annotation.
	 */
	NestableAnnotation addAnnotation(int index, String nestableAnnotationName, String containerAnnotationName);
	
	/**
	 * Move the nestable annotation found in the specified container
	 * annotation at the specified source index to the specified target index.
	 */
	void moveAnnotation(int targetIndex, int sourceIndex, String containerAnnotationName);
	
	/**
	 * Remove the specified annotation.
	 */
	void removeAnnotation(String annotationName);
	
	/**
	 * Remove the specified nestable annotation from the container annotation at the specified
	 * index.
	 * If there is no container, assume the index is zero and this does the same as 
	 * {@link #removeAnnotation(String)}
	 */
	void removeAnnotation(int index, String nestableAnnotationName, String containerAnnotationName);
	
	
	// ********** queries **********
		
	/**
	 * Return whether the underlying JDT member is currently annotated with any recognized
	 * annotations.
	 */
	boolean isAnnotated();
	
	/**
	 * Return the text range for the member's name.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);
}
