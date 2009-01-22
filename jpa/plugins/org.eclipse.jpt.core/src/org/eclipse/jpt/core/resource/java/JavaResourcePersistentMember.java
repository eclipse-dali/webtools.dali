/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.MethodSignature;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaResourcePersistentMember
	extends JavaResourceNode
{

	// ********** mapping annotations **********

	/**
	 * Return the member's mapping annotations.
	 * Do not return duplicate annotations as this error is handled by the java
	 * compiler.
	 * @see #supportingAnnotations()
	 */
	Iterator<Annotation> mappingAnnotations();
		String MAPPING_ANNOTATIONS_COLLECTION = "mappingAnnotations"; //$NON-NLS-1$

	/**
	 * Return the number of mapping annotations.
	 */
	int mappingAnnotationsSize();

	/**
	 * Return the member's mapping annotation.
	 */
	JavaResourceNode getMappingAnnotation();

	/**
	 * Return the mapping annotation with the specified name.
	 * Return the first if there are duplicates in the source code
	 */
	JavaResourceNode getMappingAnnotation(String annotationName);

	/**
	 * Change the mapping annotation. Remove any existing mapping annotations.
	 * Do not remove any non-mapping annotations.
	 */
	void setMappingAnnotation(String annotationName);

	/**
	 * Return a null mapping annotation with the specified name. If annotationName is 
	 * null then return null instead of throwing an exception
	 * The corresponding AnnotationDefinition must implement #buildNullAnnotation()
	 * {@link AnnotationDefinition#buildNullAnnotation(JavaResourcePersistentMember,
	 * org.eclipse.jpt.core.utility.jdt.Member)}
	 */
	JavaResourceNode getNullMappingAnnotation(String annotationName);


	// ********** supporting annotations **********

	/**
	 * Return the member's supporting annotations.
	 * Do not return duplicate annotations as this error is handled by the java
	 * compiler. Do not return any mapping annotations.
	 * @see #mappingAnnotations()
	 */
	Iterator<Annotation> supportingAnnotations();
		String SUPPORTING_ANNOTATIONS_COLLECTION = "supportingAnnotations"; //$NON-NLS-1$

	/**
	 * Return the number of supporting annotations.
	 */
	int supportingAnnotationsSize();

	/**
	 * Return the specified supporting nested annotations.
	 * If both the nestable and container annotations are specified on the
	 * member directly, return only the nestable annotations specified within
	 * the container annotation.
	 */
	// TODO tie the singular and plural annotations together so we can generate
	// a validation error when both are specified
	ListIterator<NestableAnnotation> supportingAnnotations(String nestableAnnotationName, String containerAnnotationName);

	/**
	 * Return the specified supporting annotation.
	 * Return the first if there are duplicates in the source code.
	 */
	JavaResourceNode getSupportingAnnotation(String annotationName);

	/**
	 * Return the specified supporting annotation.
	 * Return the first if there are duplicates in the source code.
	 * Do not return null, but a Null Object instead if no annotation
	 * with the specified name exists in the source code.
	 */
	JavaResourceNode getNonNullSupportingAnnotation(String annotationName);

	/**
	 * Add a supporting annotation with the specified name.
	 */
	JavaResourceNode addSupportingAnnotation(String annotationName);
	
	/**
	 * Remove the specified supporting annotation.
	 */
	void removeSupportingAnnotation(String annotationName);

	/**
	 * Add a new supporting nestable annotation with the specified name.
	 * Create a new container annotation if necessary and add the nestable
	 * annotation to it.
	 * If both the nestable annotation and the container annotation already
	 * exist, then add to the container annotation, leaving the existing
	 * nestable annotaion alone.
	 * If only the nestable annotation exists, then create the new container
	 * annotation and move the existing nestable annotation to it along with
	 * the new one. If neither annotation exists, then create a new nestable
	 * annotation.
	 */
	JavaResourceNode addSupportingAnnotation(int index, String nestableAnnotationName, String containerAnnotationName);

	/**
	 * Remove the specified supporting nestable annotation.
	 */
	void removeSupportingAnnotation(int index, String nestableAnnotationName, String containerAnnotationName);
	
	/**
	 * Move the supporting nestable annotation found in the specified container
	 * annotation at the specified source index to the specified target index.
	 */
	void moveSupportingAnnotation(int targetIndex, int sourceIndex, String containerAnnotationName);
	

	// ********** queries **********

	/**
	 * Return whether the underlying JDT member is persistable according to
	 * the JPA spec.
	 */
	boolean isPersistable();
		String PERSISTABLE_PROPERTY = "persistable"; //$NON-NLS-1$
		
	/**
	 * Return whether the underlying JDT member is currently annotated as being
	 * persistent (equivalent to "is mapped").
	 */
	boolean isPersisted();

	/**
	 * Return whether the Java resource persistent member is for the specified
	 * member.
	 */
	boolean isFor(String memberName, int occurrence);

	/**
	 * Return whether the Java resource persistent member is for the specified
	 * method.
	 */
	boolean isFor(MethodSignature methodSignature, int occurrence);

	/**
	 * Return the text range for the member's name.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);
	

	// ********** behavior **********

	/**
	 * Resolve type information that could be dependent on other files being
	 * added or removed.
	 */
	void resolveTypes(CompilationUnit astRoot);

}
