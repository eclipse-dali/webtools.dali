/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Java source code or binary annotated element.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaResourceAnnotatedElement
	extends JavaResourceModel
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
	 * Return the member's top-level stand-alone annotations in the order that they appear.
	 * <br>
	 * Do not return duplicate annotations as this error is handled by the Java
	 * compiler.
	 * <br>
	 * This is only used to return top-level annotations that are not container
	 * or nestable annotations.
	 * @see #getContainerAnnotation(String)
	 * @see #getAnnotations(String)
	 */
	Iterable<Annotation> getAnnotations();
	
	/**
	 * Return the number of top-level stand-alone annotations.
	 * This does not include the number of container or nestable annotations
	 * 
	 * @see #getAnnotationsSize(String)
	 */
	int getAnnotationsSize();
	
	/**
	 * Return the annotation with the specified name.
	 * Return the first if there are duplicates in the source code.
	 * <br>
	 * This is only used to return a top-level standalone annotation.
	 * It will not return container or nestable annotations
	 * 
	 * @see #getContainerAnnotation(String)
	 * @see #getAnnotation(int, String)
	 */
	Annotation getAnnotation(String annotationName);

	/**
	 * Return the "container" annotation with the specified name.
	 * Return the first if there are duplicates in the source code.
	 * <br>
	 * This is only used to return a top-level container annotations.
	 * 
	 * @see #getAnnotation(String)
	 * @see #getAnnotation(int, String)
	 */
	Annotation getContainerAnnotation(String containerAnnotationName);

	/**
	 * Return the specified top-level stand-alone annotation.
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
	 * 
	 * @see #getAnnotations()
	 * @see #getContainerAnnotation(String)
	 */
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
	 * Add a top-level stand-alone annotation with the specified name
	 * and return the newly-created annotation.
	 * <br>
	 * Do not use this to add nestable annotations or container annotations
	 * @see #addAnnotation(int, String)
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
	 * Remove the specified top-level standalone annotation.
	 * <br>
	 * Do not use this to remove container or nestable annotations
	 *
	 * @see #removeAnnotation(int, String)
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
	 * Return the element's "top level" annotations.
	 * For "combination" annotations (i.e. a virtual array of annotations that
	 * can be either a single <em>standalone</em> annotation [representing an
	 * array of length one] or an array of annotations witin a
	 * <em>container</em> annotation), only the <em>container</em> annotation is
	 * included (if it is present) or the single <em>standalone</em> annotation.
	 */
	Iterable<Annotation> getTopLevelAnnotations();
	
	/**
	 * Return whether the underlying JDT member is currently annotated with any recognized
	 * annotations.
	 */
	boolean isAnnotated();
	Predicate<JavaResourceAnnotatedElement> IS_ANNOTATED = new IsAnnotated();
	class IsAnnotated
		extends PredicateAdapter<JavaResourceAnnotatedElement>
	{
		@Override
		public boolean evaluate(JavaResourceAnnotatedElement element) {
			return element.isAnnotated();
		}
	}

	/**
	 * Return whether the underlying JDT member is annotated with any of the given annotations.
	 */
	boolean isAnnotatedWithAnyOf(Iterable<String> annotationNames);

	class IsAnnotatedWithAnyOf
		extends CriterionPredicate<JavaResourceAnnotatedElement, Iterable<String>>
	{
		public IsAnnotatedWithAnyOf(Iterable<String> annotationNames) {
			super(annotationNames);
		}
		public boolean evaluate(JavaResourceAnnotatedElement element) {
			return element.isAnnotatedWithAnyOf(this.criterion);
		}
	}

	/**
	 * Return the element's ASTNode type
	 */
	AstNodeType getAstNodeType();
	class AstNodeTypeEquals
		extends CriterionPredicate<JavaResourceAnnotatedElement, AstNodeType>
	{
		public AstNodeTypeEquals(AstNodeType astNodeType) {
			super(astNodeType);
		}
		public boolean evaluate(JavaResourceAnnotatedElement element) {
			return element.getAstNodeType() == this.criterion;
		}
	}
	
	/**
	 * Return the text range for the member's name.
	 */
	TextRange getNameTextRange();
	
	/**
	 * Return the text range for the nestable annotation if it is currently
	 * unnested. If it is nested, return the text range for the corresponding
	 * container annotation.
	 * This is not used for stand-alone annotations
	 * 
	 * @see Annotation#getTextRange()
	 * @see AnnotationProvider#getContainerAnnotationName(String)
	 */
	TextRange getTextRange(String nestableAnnotationName);
	
	/**
	 * The java element's ASTNode type. These are the only ASTNode types that
	 * our resource model supports
	 */
	public enum AstNodeType {
		
		/**
		 * Represents an annotatable package.
		 * An {@link JavaResourceAnnotatedElement} of {@link AstNodeType} PACKAGE may safely be cast as a 
		 * {@link JavaResourcePackage}
		 */
		PACKAGE(ASTNode.PACKAGE_DECLARATION),
		
		/**
		 * Represents a class or interface.
		 * An {@link JavaResourceAnnotatedElement} of {@link AstNodeType} TYPE may safely be cast as a 
		 * {@link JavaResourceType}
		 */
		TYPE(ASTNode.TYPE_DECLARATION),
		
		/**
		 * Represents an enum.
		 * An {@link JavaResourceAnnotatedElement} of {@link AstNodeType} ENUM may safely be cast as a 
		 * {@link JavaResourceEnum}
		 */
		ENUM(ASTNode.ENUM_DECLARATION),
		
		/**
		 * Represents a method.
		 * An {@link JavaResourceAnnotatedElement} of {@link AstNodeType} METHOD may safely be cast as a 
		 * {@link JavaResourceMethod}
		 */
		METHOD(ASTNode.METHOD_DECLARATION),
		
		/**
		 * Represents a type field.
		 * An {@link JavaResourceAnnotatedElement} of {@link AstNodeType} FIELD may safely be cast as a 
		 * {@link JavaResourceField}
		 */
		FIELD(ASTNode.FIELD_DECLARATION),
		
		/**
		 * Represents an enum constant.
		 * An {@link JavaResourceAnnotatedElement} of {@link AstNodeType} ENUM_CONSTANT may safely be cast as a 
		 * {@link JavaResourceEnumConstant}
		 */
		ENUM_CONSTANT(ASTNode.ENUM_CONSTANT_DECLARATION);


		private int astNodeType;

		AstNodeType(int astNodeType) {
			this.astNodeType = astNodeType;
		}

		/**
		 * Return whether the given astNodeType matches this type
		 */
		public boolean matches(int type) {
			return this.astNodeType == type;
		}
	}
}
