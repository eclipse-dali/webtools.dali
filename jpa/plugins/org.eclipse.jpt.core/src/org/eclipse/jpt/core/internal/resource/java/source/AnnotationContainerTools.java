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

import java.util.List;
import java.util.ListIterator;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;

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
	 * Use the annotation visitor to gather up the nested JDT DOM annotations.
	 */
	private static <T extends NestableAnnotation> HashBag<org.eclipse.jdt.core.dom.Annotation> getNestedJdtAnnotations(CompilationUnit astRoot, AnnotationContainer<T> annotationContainer) {
		AnnotationVisitor<T> visitor = new AnnotationVisitor<T>(annotationContainer);
		annotationContainer.getContainerJdtAnnotation(astRoot).accept(visitor);
		return visitor.jdtAnnotations;
	}

	/**
	 * Update the annotations in the specified annotation container to be in
	 * synch with those in the specified AST. The appropriate change
	 * notification will occur.
	 */
	public static <T extends NestableAnnotation> void update(AnnotationContainer<T> annotationContainer, CompilationUnit astRoot) {
		HashBag<org.eclipse.jdt.core.dom.Annotation> jdtAnnotations = getNestedJdtAnnotations(astRoot, annotationContainer);
		for (ListIterator<T> stream = annotationContainer.nestedAnnotations(); stream.hasNext(); ) {
			T nestedAnnotation = stream.next();
			org.eclipse.jdt.core.dom.Annotation jdtAnnotation = nestedAnnotation.getJdtAnnotation(astRoot);
			if (jdtAnnotations.isEmpty()) {
				// no more JDT DOM annotations - remove the nested annotation at the end of the container's list
				int last = annotationContainer.nestedAnnotationsSize() - 1;
				T remove = annotationContainer.removeNestedAnnotationInternal(last);
				annotationContainer.nestedAnnotationRemoved(last, remove);
			} else {
				if (jdtAnnotations.remove(jdtAnnotation)) {
					// matching JDT DOM annotation found - update the nested annotation
					nestedAnnotation.update(astRoot);
				} else {
					throw new IllegalStateException("invalid nested annotation: " + nestedAnnotation); //$NON-NLS-1$
				}
			}
		}
		// add nested annotations for the remaining JDT DOM annotations
		int start = annotationContainer.nestedAnnotationsSize();
		int size = start + jdtAnnotations.size();
		for (int i = start; i < size; i++) {
			T nestedAnnotation = annotationContainer.addNestedAnnotationInternal();
			nestedAnnotation.initialize(astRoot);
			annotationContainer.nestedAnnotationAdded(i, nestedAnnotation);
		}
	}

	private AnnotationContainerTools() {
		super();
		throw new UnsupportedOperationException();
	}


	// ********** annotation visitor **********

	/**
	 * Gather up the nested JDT annotations.
	 */
	static class AnnotationVisitor<T extends NestableAnnotation>
		extends ASTVisitor
	{
		final AnnotationContainer<T> annotationContainer;
		final HashBag<org.eclipse.jdt.core.dom.Annotation> jdtAnnotations = new HashBag<org.eclipse.jdt.core.dom.Annotation>();

		AnnotationVisitor(AnnotationContainer<T> annotationContainer) {
			super();
			this.annotationContainer = annotationContainer;
		}

		/**
		 * MarkerAnnotation children:
		 *     typeName - Name
		 * we probably don't need to visit a MarkerAnnotation's children
		 * since it doesn't hold anything interesting...
		 */
		@Override
		public boolean visit(MarkerAnnotation node) {
			return this.visit_(node);
		}

		/**
		 * SingleMemberAnnotation children:
		 *     typeName - Name
		 *     value - Expression (which can be an Annotation)
		 */
		@Override
		public boolean visit(SingleMemberAnnotation node) {
			return this.visit_(node);
		}

		/**
		 * NormalAnnotation children:
		 *     typeName - Name
		 *     values - MemberValuePair
		 */
		@Override
		public boolean visit(NormalAnnotation node) {
			return this.visit_(node);
		}

		/**
		 * MemberValuePair children:
		 *     name - SimpleName
		 *     value - Expression (which can be an Annotation)
		 */
		@Override
		public boolean visit(MemberValuePair node) {
			// only process the children if the mvp's name matches the container's element name
			return node.getName().getFullyQualifiedName().equals(this.annotationContainer.getElementName());
		}

		boolean visit_(org.eclipse.jdt.core.dom.Annotation jdtAnnotation) {
			String jdtAnnotationName = JDTTools.resolveAnnotation(jdtAnnotation);
			if (jdtAnnotationName == null) {
				return false; // unknown annotation - skip its children
			}
			if (jdtAnnotationName.equals(this.annotationContainer.getContainerAnnotationName())) {
				return true; // process the container annotation's children
			}
			if (jdtAnnotationName.equals(this.annotationContainer.getNestableAnnotationName())) {
				this.jdtAnnotations.add(jdtAnnotation);
				return false; // no need to visit the nested annotation's children
			}
			return false; // ignore other annotations
		}

	}

}
