/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * Utility methods for manipulating annotation containers.
 */
public final class AnnotationContainerTools {

	/**
	 * Add a nested annotation to the specified annotation container
	 * at the specified index.
	 * This method modifies both the model annotation container and the
	 * AST; with the appropriate change notification occurring afterwards.
	 */
	public static <T extends NestableAnnotation> NestableAnnotation addNestedAnnotation(int index, AnnotationContainer<T> annotationContainer) {
		// add a new annotation to the end of the list...
		int sourceIndex = annotationContainer.nestedAnnotationsSize();
		T nestedAnnotation = annotationContainer.addNestedAnnotationInternal();
		nestedAnnotation.newAnnotation();
		// ...then move it to the specified index...
		annotationContainer.moveNestedAnnotationInternal(index, sourceIndex);
		synchJavaAnnotationsAfterMove(index, sourceIndex, annotationContainer, nestedAnnotation);
		// ...then, when all is settled, tell the container to fire change notification
		annotationContainer.nestedAnnotationAdded(index, nestedAnnotation);
		return nestedAnnotation;
	}

	/**
	 * Move the nested annotation at the specified source index in the
	 * specified annotation container to the specified target index.
	 * This method modifies both the model annotation container and the
	 * AST; with the appropriate change notification occurring afterwards.
	 */
	public static <T extends NestableAnnotation> void moveNestedAnnotation(int targetIndex, int sourceIndex, AnnotationContainer<T> annotationContainer) {
		NestableAnnotation nestedAnnotation = annotationContainer.moveNestedAnnotationInternal(targetIndex, sourceIndex);
		synchJavaAnnotationsAfterMove(targetIndex, sourceIndex, annotationContainer, nestedAnnotation);
		annotationContainer.nestedAnnotationMoved(targetIndex, sourceIndex);
	}

	/**
	 * An annotation was moved within the specified annotation container from
	 * the specified source index to the specified target index.
	 * Synchronize the AST annotations with the model annotation container,
	 * starting with the lower index to prevent overlap.
	 */
	private static <T extends NestableAnnotation> void synchJavaAnnotationsAfterMove(int targetIndex, int sourceIndex, AnnotationContainer<T> annotationContainer, NestableAnnotation nestedAnnotationAnnotation) {
		// move the Java annotation to the end of the list...
		nestedAnnotationAnnotation.moveAnnotation(annotationContainer.nestedAnnotationsSize());
		// ...then shift the other Java annotations over one slot...
		List<T> nestableAnnotations = CollectionTools.list(annotationContainer.nestedAnnotations());
		if (sourceIndex < targetIndex) {
			for (int i = sourceIndex; i < targetIndex; i++) {
				nestableAnnotations.get(i).moveAnnotation(i);
			}
		} else {
			for (int i = sourceIndex; i > targetIndex; i-- ) {
				nestableAnnotations.get(i).moveAnnotation(i);
			}
		}
		// ...then move the Java annotation to the now empty slot at the target index
		nestedAnnotationAnnotation.moveAnnotation(targetIndex);
	}

	/**
	 * Remove the nested annotation at the specified index in the
	 * specified annotation container.
	 * This method modifies both the model annotation container and the
	 * AST; with the appropriate change notification occurring afterwards.
	 */
	public static <T extends NestableAnnotation> void removeNestedAnnotation(int index, AnnotationContainer<T> annotationContainer) {
		T nestedAnnotation = annotationContainer.removeNestedAnnotationInternal(index);
		nestedAnnotation.removeAnnotation();
		synchJavaAnnotationsAfterRemove(index, annotationContainer);
		annotationContainer.nestedAnnotationRemoved(index, nestedAnnotation);
	}

	/**
	 * An annotation was removed from the specified annotation container at the
	 * specified index.
	 * Synchronize the AST annotations with the model annotation container,
	 * starting at the specified index to prevent overlap.
	 */
	private static <T extends NestableAnnotation> void synchJavaAnnotationsAfterRemove(int index, AnnotationContainer<T> annotationContainer) {
		List<T> nestableAnnotations = CollectionTools.list(annotationContainer.nestedAnnotations());
		for (int i = index; i < nestableAnnotations.size(); i++) {
			// the indices are the same because the model annotations are
			// already in the proper locations - it's the Java annotations that
			// need to be moved to the same location
			nestableAnnotations.get(i).moveAnnotation(i);
		}
	}

	/**
	 * Initialize the specified annotation container to be in synch with the
	 * specified AST. No change notification will occur.
	 */
	public static <T extends NestableAnnotation> void initialize(AnnotationContainer<T> annotationContainer, CompilationUnit astRoot) {
		// ignore the nested JDT annotations themselves
		// (maybe someday we can use them during initialization...)
		int size = getNestedJdtAnnotations(astRoot, annotationContainer).size();
		for (int i = 0; i < size; i++) {
			T nestedAnnotation = annotationContainer.addNestedAnnotationInternal();
			nestedAnnotation.initialize(astRoot);
		}
	}

	/**
	 * Return a list of the nested JDT DOM annotations.
	 */
	private static <T extends NestableAnnotation> ArrayList<Annotation> getNestedJdtAnnotations(CompilationUnit astRoot, AnnotationContainer<T> annotationContainer) {
		ArrayList<Annotation> result = new ArrayList<Annotation>();
		Annotation containerJdtAnnotation = annotationContainer.getContainerJdtAnnotation(astRoot);
		if (containerJdtAnnotation.isMarkerAnnotation()) {
			// no nested annotations
		}
		else if (containerJdtAnnotation.isSingleMemberAnnotation()) {
			if (annotationContainer.getElementName().equals("value")) { //$NON-NLS-1$
				Expression ex = ((SingleMemberAnnotation) containerJdtAnnotation).getValue();
				addJdtAnnotationsTo(ex, annotationContainer.getNestableAnnotationName(), result);
			} else {
				// no nested annotations
			}
		}
		else if (containerJdtAnnotation.isNormalAnnotation()) {
			MemberValuePair pair = getMemberValuePair((NormalAnnotation) containerJdtAnnotation, annotationContainer.getElementName());
			if (pair == null) {
				// no nested annotations
			} else {
				addJdtAnnotationsTo(pair.getValue(), annotationContainer.getNestableAnnotationName(), result);
			}
		}
		return result;
	}

	/**
	 * Add whatever annotations are represented by the specified expression to
	 * the specified bag. Add null to the bag for any non-annotation expression.
	 */
	private static void addJdtAnnotationsTo(Expression expression, String annotationName, ArrayList<Annotation> jdtAnnotations) {
		if (expression == null) {
			jdtAnnotations.add(null);  // not sure how we would get here...
		}
		else if (expression.getNodeType() == ASTNode.ARRAY_INITIALIZER) {
			addJdtAnnotationsTo((ArrayInitializer) expression, annotationName, jdtAnnotations);
		}
		else {
			jdtAnnotations.add(getJdtAnnotation_(expression, annotationName));
		}
	}

	private static void addJdtAnnotationsTo(ArrayInitializer arrayInitializer, String annotationName, ArrayList<Annotation> jdtAnnotations) {
		@SuppressWarnings("unchecked")
		List<Expression> expressions = arrayInitializer.expressions();
		for (Expression expression : expressions) {
			jdtAnnotations.add(getJdtAnnotation(expression, annotationName));
		}
	}

	/**
	 * If the specified expression is an annotation with the specified name, return it;
	 * otherwise return null.
	 */
	private static Annotation getJdtAnnotation(Expression expression, String annotationName) {
		// not sure how the expression could be null...
		return (expression == null) ? null : getJdtAnnotation_(expression, annotationName);
	}

	/**
	 * pre-condition: expression is not null
	 */
	private static Annotation getJdtAnnotation_(Expression expression, String annotationName) {
		switch (expression.getNodeType()) {
			case ASTNode.NORMAL_ANNOTATION:
			case ASTNode.SINGLE_MEMBER_ANNOTATION:
			case ASTNode.MARKER_ANNOTATION:
				Annotation jdtAnnotation = (Annotation) expression;
				if (getQualifiedName(jdtAnnotation).equals(annotationName)) {
					return jdtAnnotation;
				}
				return null;
			default:
				return null;
		}
	}

	private static String getQualifiedName(Annotation jdtAnnotation) {
		ITypeBinding typeBinding = jdtAnnotation.resolveTypeBinding();
		if (typeBinding != null) {
			String resolvedName = typeBinding.getQualifiedName();
			if (resolvedName != null) {
				return resolvedName;
			}
		}
		return jdtAnnotation.getTypeName().getFullyQualifiedName();
	}

	private static MemberValuePair getMemberValuePair(NormalAnnotation annotation, String elementName) {
		@SuppressWarnings("unchecked")
		List<MemberValuePair> pairs = annotation.values();
		for (MemberValuePair pair : pairs) {
			if (pair.getName().getFullyQualifiedName().equals(elementName)) {
				return pair;
			}
		}
		return null;
	}

	/**
	 * Update the annotations in the specified annotation container to be in
	 * synch with those in the specified AST. The appropriate change
	 * notification will occur.
	 */
	public static <T extends NestableAnnotation> void update(AnnotationContainer<T> annotationContainer, CompilationUnit astRoot) {
		ListIterator<Annotation> jdtAnnotations = getNestedJdtAnnotations(astRoot, annotationContainer).listIterator();

		for (ListIterator<T> nestedAnnotations = annotationContainer.nestedAnnotations(); nestedAnnotations.hasNext(); ) {
			T nestedAnnotation = nestedAnnotations.next();
			if (jdtAnnotations.hasNext()) {
				// matching JDT DOM annotation is present - update the nested annotation
				jdtAnnotations.next();  // maybe someday we can pass this to the update
				nestedAnnotation.update(astRoot);
			} else {
				// no more JDT DOM annotations - remove the nested annotation at the end of the container's list
				int last = annotationContainer.nestedAnnotationsSize() - 1;
				T remove = annotationContainer.removeNestedAnnotationInternal(last);
				annotationContainer.nestedAnnotationRemoved(last, remove);
			}
		}

		// add nested annotations for the remaining JDT DOM annotations
		int i = annotationContainer.nestedAnnotationsSize();
		while (jdtAnnotations.hasNext()) {
			jdtAnnotations.next();  // maybe someday we can pass this to the initialize
			T nestedAnnotation = annotationContainer.addNestedAnnotationInternal();
			nestedAnnotation.initialize(astRoot);
			annotationContainer.nestedAnnotationAdded(i++, nestedAnnotation);
		}
	}

	private AnnotationContainerTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
