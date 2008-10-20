/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class ContainerAnnotationTools
{

	public static NestableAnnotation addNestedAnnotation(int index, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		int size = containerAnnotation.nestedAnnotationsSize();
		NestableAnnotation nestedAnnotation = containerAnnotation.addInternal(size);
		nestedAnnotation.newAnnotation();
		containerAnnotation.moveInternal(index, size);
		synchAnnotationsAfterMove(index, size, containerAnnotation);
		return nestedAnnotation;
	}

	/**
	 * synchronize the source annotations with the model nestableAnnotations,
	 * starting at the end of the list to prevent overlap
	 */
	public static void synchAnnotationsAfterAdd(int index, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		List<NestableAnnotation> nestableAnnotations = CollectionTools.list(containerAnnotation.nestedAnnotations());
		for (int i = nestableAnnotations.size(); i-- > index;) {
			synch(nestableAnnotations.get(i), i);
		}
	}

	/**
	 * synchronize the source annotations with the model nestableAnnotations,
	 * starting at the specified index to prevent overlap
	 */
	public static void synchAnnotationsAfterRemove(int index, ContainerAnnotation<? extends NestableAnnotation> pluralAnnotation) {
		List<NestableAnnotation> nestableAnnotations = CollectionTools.list(pluralAnnotation.nestedAnnotations());
		for (int i = index; i < nestableAnnotations.size(); i++) {
			synch(nestableAnnotations.get(i), i);
		}
	}

	private static void synch(NestableAnnotation nestableAnnotation, int index) {
		nestableAnnotation.moveAnnotation(index);
	}

	/**
	 * synchronize the annotations with the model nestableAnnotations
	 */
	public static void synchAnnotationsAfterMove(int targetIndex, int sourceIndex, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		NestableAnnotation nestableAnnotation = containerAnnotation.nestedAnnotationAt(targetIndex);

		synch(nestableAnnotation, containerAnnotation.nestedAnnotationsSize());

		List<NestableAnnotation> nestableAnnotations = CollectionTools.list(containerAnnotation.nestedAnnotations());
		if (sourceIndex < targetIndex) {
			for (int i = sourceIndex; i < targetIndex; i++) {
				synch(nestableAnnotations.get(i), i);
			}
		}
		else {
			for (int i = sourceIndex; i > targetIndex; i-- ) {
				synch(nestableAnnotations.get(i), i);
			}
		}
		synch(nestableAnnotation, targetIndex);
	}


	public static void initializeNestedAnnotations(CompilationUnit astRoot, ContainerAnnotation<?> containerAnnotation) {
		addAnnotationsFromSource(astRoot, containerAnnotation);
	}

	private static void addAnnotationsFromSource(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		containerAnnotation.getJdtAnnotation(astRoot).accept(buildInitialAnnotationVisitor(astRoot, containerAnnotation));
	}

	private static ASTVisitor buildInitialAnnotationVisitor(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		return new InitialAnnotationVisitor(astRoot, containerAnnotation);
	}

	public static void updateNestedAnnotationsFromJava(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		addOrUpdateAnnotationInSource(astRoot, containerAnnotation);
		//TODO not sure how to handle generics here and get rid of this warning
		removeAnnotationsNotInSource(astRoot, (ContainerAnnotation<NestableAnnotation>) containerAnnotation);
	}

	private static void addOrUpdateAnnotationInSource(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		containerAnnotation.getJdtAnnotation(astRoot).accept(buildUpdateAnnotationVisitor(astRoot, containerAnnotation));
	}

	private static void removeAnnotationsNotInSource(CompilationUnit astRoot, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		for (NestableAnnotation annotation : CollectionTools.iterable(containerAnnotation.nestedAnnotations())) {
			if (annotation.getJdtAnnotation(astRoot) == null) {
				containerAnnotation.remove(annotation);
			}
		}
	}

	private static ASTVisitor buildUpdateAnnotationVisitor(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		return new UpdateAnnotationVisitor(astRoot, containerAnnotation);
	}

	private ContainerAnnotationTools() {
		super();
		throw new UnsupportedOperationException();
	}


	// ********** annotation visitor **********

	/**
	 * Only visit the member value pair for the container annotation's element name.
	 */
	private abstract static class AnnotationVisitor extends ASTVisitor {
		protected final CompilationUnit astRoot;
		protected final ContainerAnnotation<? extends NestableAnnotation> containerAnnotation;

		AnnotationVisitor(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
			this.astRoot = astRoot;
			this.containerAnnotation = containerAnnotation;
		}

		@Override
		public boolean visit(MemberValuePair node) {
			return node.getName().getFullyQualifiedName().equals(this.containerAnnotation.getElementName());
		}

		@Override
		public boolean visit(SingleMemberAnnotation node) {
			return this.visit_(node);
		}

		@Override
		public boolean visit(NormalAnnotation node) {
			return this.visit_(node);
		}

		@Override
		public boolean visit(MarkerAnnotation node) {
			return this.visit_(node);
		}

		protected boolean visit_(org.eclipse.jdt.core.dom.Annotation node) {
			String jdtAnnotationName = JDTTools.resolveAnnotation(node);
			if (this.containerAnnotation.getAnnotationName().equals(jdtAnnotationName)) {
				return true;
			}
			if (this.containerAnnotation.getNestableAnnotationName().equals(jdtAnnotationName)) {
				this.visitNestedAnnotation(node);
			}
			return false;
		}

		protected abstract void visitNestedAnnotation(org.eclipse.jdt.core.dom.Annotation node);

	}


	// ********** initial annotation visitor **********

	private static class InitialAnnotationVisitor extends AnnotationVisitor {

		InitialAnnotationVisitor(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
			super(astRoot, containerAnnotation);
		}

		@Override
		protected void visitNestedAnnotation(org.eclipse.jdt.core.dom.Annotation node) {
			NestableAnnotation nestedAnnotation = this.containerAnnotation.addInternal(this.containerAnnotation.nestedAnnotationsSize());
			nestedAnnotation.initialize(this.astRoot);
		}

	}


	// ********** update annotation visitor **********

	private static class UpdateAnnotationVisitor extends AnnotationVisitor {

		UpdateAnnotationVisitor(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
			super(astRoot, containerAnnotation);
		}

		@Override
		protected void visitNestedAnnotation(org.eclipse.jdt.core.dom.Annotation node) {
			NestableAnnotation nestedAnnotation = this.containerAnnotation.nestedAnnotationFor(node);
			if (nestedAnnotation == null) {
				nestedAnnotation = this.containerAnnotation.add(this.containerAnnotation.nestedAnnotationsSize());
				nestedAnnotation.initialize(this.astRoot);
			} else {
				nestedAnnotation.update(this.astRoot);
			}
		}

	}

}
