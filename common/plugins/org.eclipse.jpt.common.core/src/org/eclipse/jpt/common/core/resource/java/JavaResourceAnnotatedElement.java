/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;

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
	 * String associated with changes to the "nestableAnnotations" collection
	 */
	String NESTABLE_ANNOTATIONS_COLLECTION = "nestableAnnotations"; //$NON-NLS-1$
	
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
	 * member directly, the behavior is undefined
	 */
	// TODO tie the singular and plural annotations together so we can generate
	// a validation error when both are specified
	ListIterable<NestableAnnotation> getAnnotations(String nestableAnnotationName);
	
	/**
	 * Return the number of nestable annotations with the specified name.
	 * If nestable and container annotations are both specified on the
	 * member directly, the behavior is undefined
	 */
	int getAnnotationsSize(String nestableAnnotationName);
	
	/**
	 * Return the nestable annotation at the specified index with the specified name.
	 * If nestable and container annotations are both specified on the
	 * member directly, the behavior is undefined
	 */
	NestableAnnotation getAnnotation(int index, String nestableAnnotationName);
	
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
	NestableAnnotation addAnnotation(int index, String nestableAnnotationName);
	
	/**
	 * Move the nestable annotation at the specified source index to the specified target index.
	 */
	void moveAnnotation(int targetIndex, int sourceIndex, String nestableAnnotationName);
	
	/**
	 * Remove the specified annotation.
	 */
	void removeAnnotation(String annotationName);
	
	/**
	 * Remove the specified nestable annotation from the container annotation at the specified
	 * index.
	 * If there is no container, assume the index is zero and just remove the nestable annotation
	 */
	void removeAnnotation(int index, String nestableAnnotationName);
	
	
	// ********** queries **********
		
	/**
	 * Return whether the underlying JDT member is currently annotated with any recognized
	 * annotations.
	 */
	boolean isAnnotated();

	/**
	 * Return whether the underlying JDT member is annotated with any of the given annotations.
	 */
	boolean isAnnotatedWith(Iterable<String> annotationNames);

	/**
	 * Return the element kind
	 */
	Kind getKind();
	
	/**
	 * Return the text range for the member's name.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the text range for the nestable annotation if it is currently
	 * unnested. If it is nested, return the text range for the corresponding
	 * container annotation.
	 * 
	 * @see AnnotationProvider#getContainerAnnotationName(String)
	 */
	TextRange getTextRange(String nestableAnnotationName, CompilationUnit astRoot);
	
	/**
	 * The kind of java element.
	 */
	public enum Kind {
		
		/**
		 * Represents an annotatable package.
		 * An {@link JavaResourceAnnotatedElement} of {@link Kind} PACKAGE may safely be cast as a 
		 * {@link JavaResourcePackage}
		 */
		PACKAGE(ASTNode.PACKAGE_DECLARATION),
		
		/**
		 * Represents a class or interface.
		 * An {@link JavaResourceAnnotatedElement} of {@link Kind} TYPE may safely be cast as a 
		 * {@link JavaResourceType}
		 */
		TYPE(ASTNode.TYPE_DECLARATION),
		
		/**
		 * Represents an enum.
		 * An {@link JavaResourceAnnotatedElement} of {@link Kind} ENUM may safely be cast as a 
		 * {@link JavaResourceEnum}
		 */
		ENUM(ASTNode.ENUM_DECLARATION),
		
		/**
		 * Represents a method.
		 * An {@link JavaResourceAnnotatedElement} of {@link Kind} METHOD may safely be cast as a 
		 * {@link JavaResourceMethod}
		 */
		METHOD(ASTNode.METHOD_DECLARATION),
		
		/**
		 * Represents a type field.
		 * An {@link JavaResourceAnnotatedElement} of {@link Kind} FIELD may safely be cast as a 
		 * {@link JavaResourceField}
		 */
		FIELD(ASTNode.FIELD_DECLARATION),
		
		/**
		 * Represents an enum constant.
		 * An {@link JavaResourceAnnotatedElement} of {@link Kind} ENUM_CONSTANT may safely be cast as a 
		 * {@link JavaResourceEnumConstant}
		 */
		ENUM_CONSTANT(ASTNode.ENUM_CONSTANT_DECLARATION);


		private int astNodeType;

		Kind(int astNodeType) {
			this.astNodeType = astNodeType;
		}

		public int getAstNodeType() {
			return this.astNodeType;
		}
	}
}
