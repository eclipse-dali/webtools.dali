/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;

/**
 * Utility methods for manipulating annotation containers.
 */
public final class AnnotationContainerTools {

	/**
	 * Add a nested annotation to the specified annotation container
	 * at the specified index.
	 * This method modifies both the resource model annotation container and the
	 * AST; with <em>no</em> change notification.
	 */
	public static <T extends NestableAnnotation> NestableAnnotation addNestedAnnotation(int index, AnnotationContainer<T> annotationContainer) {
		// add a new annotation to the end of the list...
		int sourceIndex = annotationContainer.getNestedAnnotationsSize();
		T nestedAnnotation = annotationContainer.addNestedAnnotation();
		nestedAnnotation.newAnnotation();
		// ...then move it to the specified index
		moveNestedAnnotation(index, sourceIndex, annotationContainer);
		return nestedAnnotation;
	}

	/**
	 * Move the nested annotation at the specified source index in the
	 * specified annotation container to the specified target index.
	 * This method modifies both the resource model annotation container and the
	 * AST; with <em>no</em> change notification.
	 */
	public static <T extends NestableAnnotation> void moveNestedAnnotation(int targetIndex, int sourceIndex, AnnotationContainer<T> annotationContainer) {
		if (targetIndex != sourceIndex) {
			moveNestedAnnotation_(targetIndex, sourceIndex, annotationContainer);
		}
	}

	private static <T extends NestableAnnotation> void moveNestedAnnotation_(int targetIndex, int sourceIndex, AnnotationContainer<T> annotationContainer) {
		NestableAnnotation nestedAnnotation = annotationContainer.moveNestedAnnotation(targetIndex, sourceIndex);
		syncAstAnnotationsAfterMove(targetIndex, sourceIndex, annotationContainer, nestedAnnotation);
	}

	/**
	 * An annotation was moved within the specified annotation container from
	 * the specified source index to the specified target index.
	 * Synchronize the AST annotations with the resource model annotation container,
	 * starting with the lower index to prevent overlap.
	 */
	private static <T extends NestableAnnotation> void syncAstAnnotationsAfterMove(int targetIndex, int sourceIndex, AnnotationContainer<T> annotationContainer, NestableAnnotation nestedAnnotation) {
		// move the Java annotation to the end of the list...
		nestedAnnotation.moveAnnotation(annotationContainer.getNestedAnnotationsSize());
		// ...then shift the other AST annotations over one slot...
		List<T> nestableAnnotations = CollectionTools.list(annotationContainer.getNestedAnnotations());
		if (sourceIndex < targetIndex) {
			for (int i = sourceIndex; i < targetIndex; i++) {
				nestableAnnotations.get(i).moveAnnotation(i);
			}
		} else {
			for (int i = sourceIndex; i > targetIndex; i-- ) {
				nestableAnnotations.get(i).moveAnnotation(i);
			}
		}
		// ...then move the AST annotation to the now empty slot at the target index
		nestedAnnotation.moveAnnotation(targetIndex);
	}

	/**
	 * Remove the nested annotation at the specified index in the
	 * specified annotation container.
	 * This method modifies both the resource model annotation container and the
	 * AST; with <em>no</em> change notification.
	 */
	public static <T extends NestableAnnotation> void removeNestedAnnotation(int index, AnnotationContainer<T> annotationContainer) {
		T nestedAnnotation = annotationContainer.removeNestedAnnotation(index);
		nestedAnnotation.removeAnnotation();
		syncAstAnnotationsAfterRemove(index, annotationContainer);
	}

	/**
	 * An annotation was removed from the specified annotation container at the
	 * specified index.
	 * Synchronize the AST annotations with the resource model annotation container,
	 * starting at the specified index to prevent overlap.
	 */
	private static <T extends NestableAnnotation> void syncAstAnnotationsAfterRemove(int index, AnnotationContainer<T> annotationContainer) {
		List<T> nestableAnnotations = CollectionTools.list(annotationContainer.getNestedAnnotations());
		for (int i = index; i < nestableAnnotations.size(); i++) {
			// the indices are the same because the model annotations are
			// already in the proper locations - it's the AST annotations that
			// need to be moved to the matching location
			nestableAnnotations.get(i).moveAnnotation(i);
		}
	}

	/**
	 * Initialize the specified resource model annotation container to be in
	 * sync with the specified AST. No change notification will occur.
	 */
	public static <T extends NestableAnnotation> void initialize(AnnotationContainer<T> annotationContainer, CompilationUnit astRoot) {
		// ignore the nested AST annotations themselves
		// (maybe someday we can use them during initialization...)
		int size = getNestedAstAnnotations(astRoot, annotationContainer).size();
		for (int i = 0; i < size; i++) {
			T nestedAnnotation = annotationContainer.addNestedAnnotation();
			nestedAnnotation.initialize(astRoot);
		}
	}

	/**
	 * Return a list of the nested AST annotations.
	 */
	private static <T extends NestableAnnotation> List<Annotation> getNestedAstAnnotations(CompilationUnit astRoot, AnnotationContainer<T> annotationContainer) {
		Annotation astContainerAnnotation = annotationContainer.getAstAnnotation(astRoot);
		if (astContainerAnnotation == null) {
			// seems unlikely the AST container annotation would be null,
			// since the resource container annotation is only created and
			// initialized (or synchronized) when the AST container annotation
			// is discovered
			return Collections.emptyList();
		}

		if (astContainerAnnotation.isMarkerAnnotation()) {
			return Collections.emptyList();  // no nested annotations
		}

		if (astContainerAnnotation.isSingleMemberAnnotation()) {
			return getNestedAstAnnotations((SingleMemberAnnotation) astContainerAnnotation, annotationContainer);
		}

		if (astContainerAnnotation.isNormalAnnotation()) {
			return getNestedAstAnnotations((NormalAnnotation) astContainerAnnotation, annotationContainer);
		}

		throw new IllegalStateException("unknown annotation type: " + astContainerAnnotation); //$NON-NLS-1$
	}

	private static <T extends NestableAnnotation> List<Annotation> getNestedAstAnnotations(SingleMemberAnnotation astContainerAnnotation, AnnotationContainer<T> annotationContainer) {
		return annotationContainer.getElementName().equals("value") ? //$NON-NLS-1$
				getAstAnnotations(astContainerAnnotation.getValue(), annotationContainer) :
				Collections.<Annotation>emptyList();
	}

	private static <T extends NestableAnnotation> List<Annotation> getNestedAstAnnotations(NormalAnnotation astContainerAnnotation, AnnotationContainer<T> annotationContainer) {
		MemberValuePair pair = getMemberValuePair(astContainerAnnotation, annotationContainer.getElementName());
		return (pair != null) ?
				getAstAnnotations(pair.getValue(), annotationContainer) :
				Collections.<Annotation>emptyList();
	}

	private static <T extends NestableAnnotation> List<Annotation> getAstAnnotations(Expression expression, AnnotationContainer<T> annotationContainer) {
		return (expression != null) ?
				getAstAnnotations_(expression, annotationContainer.getNestedAnnotationName()) :
				Collections.<Annotation>emptyList();
	}

	/**
	 * pre-condition: expression is not null
	 */
	private static <T extends NestableAnnotation> List<Annotation> getAstAnnotations_(Expression expression, String annotationName) {
		ArrayList<Annotation> result = new ArrayList<Annotation>();
		addAstAnnotationsTo(expression, annotationName, result);
		return result;
	}

	/**
	 * pre-condition: expression is not null
	 * <p>
	 * Add whatever annotations are represented by the specified expression to
	 * the specified list. Skip any non-annotation expressions.
	 */
	private static void addAstAnnotationsTo(Expression expression, String annotationName, ArrayList<Annotation> astAnnotations) {
		if (expression.getNodeType() == ASTNode.ARRAY_INITIALIZER) {
			addAstAnnotationsTo((ArrayInitializer) expression, annotationName, astAnnotations);
		} else {
			addAstAnnotationTo(expression, annotationName, astAnnotations);
		}
	}

	private static void addAstAnnotationsTo(ArrayInitializer arrayInitializer, String annotationName, ArrayList<Annotation> astAnnotations) {
		@SuppressWarnings("unchecked")
		List<Expression> expressions = arrayInitializer.expressions();
		for (Expression expression : expressions) {
			if (expression != null) {
				addAstAnnotationTo(expression, annotationName, astAnnotations);
			}
		}
	}

	private static void addAstAnnotationTo(Expression expression, String annotationName, ArrayList<Annotation> astAnnotations) {
		switch (expression.getNodeType()) {
			case ASTNode.NORMAL_ANNOTATION:
			case ASTNode.SINGLE_MEMBER_ANNOTATION:
			case ASTNode.MARKER_ANNOTATION:
				Annotation astAnnotation = (Annotation) expression;
				if (getQualifiedName(astAnnotation).equals(annotationName)) {
					astAnnotations.add(astAnnotation);
				}
				break;
			default:
				break;
		}
	}

	private static String getQualifiedName(Annotation astAnnotation) {
		ITypeBinding typeBinding = astAnnotation.resolveTypeBinding();
		if (typeBinding != null) {
			String resolvedName = typeBinding.getQualifiedName();
			if (resolvedName != null) {
				return resolvedName;
			}
		}
		return astAnnotation.getTypeName().getFullyQualifiedName();
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
	 * Synchronize the resource model annotations in the specified annotation
	 * container with those in the specified AST. Trigger the appropriate change
	 * notification.
	 */
	public static <T extends NestableAnnotation> void synchronize(AnnotationContainer<T> annotationContainer, CompilationUnit astRoot) {
		List<Annotation> astAnnotations = getNestedAstAnnotations(astRoot, annotationContainer);
		Iterator<Annotation> astAnnotationStream = astAnnotations.iterator();

		for (T nestedAnnotation : annotationContainer.getNestedAnnotations()) {
			if (astAnnotationStream.hasNext()) {
				// matching AST annotation is present - synchronize the nested annotation
				astAnnotationStream.next();  // maybe someday we can pass this to the update
				nestedAnnotation.synchronizeWith(astRoot);
			} else {
				// no more AST annotations - remove the remaining nested annotations and exit
				annotationContainer.syncRemoveNestedAnnotations(astAnnotations.size());
				return;
			}
		}

		// add nested annotations for any remaining AST annotations
		while (astAnnotationStream.hasNext()) {
			annotationContainer.syncAddNestedAnnotation(astAnnotationStream.next());
		}
	}

	private AnnotationContainerTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
