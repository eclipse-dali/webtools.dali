/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTNodeTextRange;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.common.utility.internal.iterators.SingleElementIterator;
import org.eclipse.jpt.jpa.core.resource.java.Annotation;
import org.eclipse.jpt.jpa.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.NestableAnnotation;

/**
 * Java source annotated element (annotations)
 */
abstract class SourceAnnotatedElement<A extends AnnotatedElement>
	extends SourceNode
	implements JavaResourceAnnotatedElement
{
	final A annotatedElement;

	/**
	 * annotations; no duplicates (java compiler has an error for duplicates)
	 */
	final Vector<Annotation> annotations = new Vector<Annotation>();

	/**
	 * these are built as needed
	 */
	private final HashMap<String, Annotation> nullAnnotationCache = new HashMap<String, Annotation>();


	// ********** construction/initialization **********

	SourceAnnotatedElement(JavaResourceNode parent, A annotatedElement) {
		super(parent);
		this.annotatedElement = annotatedElement;
	}

	public void initialize(CompilationUnit astRoot) {
		this.annotatedElement.getBodyDeclaration(astRoot).accept(this.buildInitialAnnotationVisitor(astRoot));
	}

	private ASTVisitor buildInitialAnnotationVisitor(CompilationUnit astRoot) {
		return new InitialAnnotationVisitor(astRoot, this.annotatedElement.getBodyDeclaration(astRoot));
	}

	/**
	 * called from {@link InitialAnnotationVisitor#visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation)}
	 */
	/* CU private */ void addInitialAnnotation(org.eclipse.jdt.core.dom.Annotation node, CompilationUnit astRoot) {
		String astAnnotationName = ASTTools.resolveAnnotation(node);
		if (astAnnotationName != null) {
			this.addInitialAnnotation(astAnnotationName, astRoot);
		}
	}

	/**
	 * pre-condition: <code>astAnnotationName</code> is not <code>null</code>
	 */
	void addInitialAnnotation(String astAnnotationName, CompilationUnit astRoot) {
		if (this.annotationIsValid(astAnnotationName)) {
			if (this.selectAnnotationNamed(this.annotations, astAnnotationName) == null) { // ignore duplicates
				Annotation annotation = this.buildAnnotation(astAnnotationName);
				annotation.initialize(astRoot);
				this.annotations.add(annotation);
			}
		}
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncAnnotations(astRoot);
	}


	// ********** annotations **********

	public Iterator<Annotation> annotations() {
		return this.getAnnotations().iterator();
	}

	Iterable<Annotation> getAnnotations() {
		return new LiveCloneIterable<Annotation>(this.annotations);
	}

	public int annotationsSize() {
		return this.annotations.size();
	}

	public Annotation getAnnotation(String annotationName) {
		return this.selectAnnotationNamed(this.getAnnotations(), annotationName);
	}

	public Annotation getNonNullAnnotation(String annotationName) {
		Annotation annotation = this.getAnnotation(annotationName);
		return (annotation != null) ? annotation : this.getNullAnnotation(annotationName);
	}

	private synchronized Annotation getNullAnnotation(String annotationName) {
		Annotation annotation = this.nullAnnotationCache.get(annotationName);
		if (annotation == null) {
			annotation = this.buildNullAnnotation(annotationName);
			this.nullAnnotationCache.put(annotationName, annotation);
		}
		return annotation;
	}

	public Iterator<NestableAnnotation> annotations(String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.getContainerAnnotation(containerAnnotationName);
		if (containerAnnotation != null) {
			return containerAnnotation.getNestedAnnotations().iterator();
		}
		NestableAnnotation nestableAnnotation = this.getNestableAnnotation(nestableAnnotationName);
		if (nestableAnnotation != null) {
			return new SingleElementIterator<NestableAnnotation>(nestableAnnotation);
		}
		return EmptyIterator.instance();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private ContainerAnnotation<NestableAnnotation> getContainerAnnotation(String annotationName) {
		return (ContainerAnnotation<NestableAnnotation>) this.getAnnotation(annotationName);
	}

	private NestableAnnotation getNestableAnnotation(String annotationName) {
		return (NestableAnnotation) this.getAnnotation(annotationName);
	}

	public Annotation addAnnotation(String annotationName) {
		Annotation annotation = this.buildAnnotation(annotationName);
		this.annotations.add(annotation);
		annotation.newAnnotation();
		return annotation;
	}

	/**
	 * <ol>
	 * <li>Check for a container annotation;
	 *     if it is present, add a nested annotation to it.
	 * <li>Check for a stand-alone nested annotation;
	 *     if it is missing, add a stand-alone nested annotation.
	 * <li>If there is an existing stand-alone nested annotation,
	 *     add a container annotation and move the stand-alone nested annotation to it
	 *     and add a new nested annotation to it also
	 * <ol>
	 */
	public NestableAnnotation addAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.getContainerAnnotation(containerAnnotationName);
		if (containerAnnotation != null) {
			// ignore any stand-alone nestable annotations and just add to the container annotation
			return AnnotationContainerTools.addNestedAnnotation(index, containerAnnotation);
		}
		NestableAnnotation standAloneAnnotation = this.getNestableAnnotation(nestableAnnotationName);
		if (standAloneAnnotation == null) {
			// add a stand-alone nestable annotation since neither the nestable nor the container exist
			return (NestableAnnotation) this.addAnnotation(nestableAnnotationName);
		}
		// move the stand-alone nestable annotation to a container and add another nestable
		return this.addSecondNestedAnnotation(index, containerAnnotationName, standAloneAnnotation);
	}

	/**
	 * <ol>
	 * <li>Build a new container annotation
	 * <li>If the new annotation is to be added at index 0<ol>
	 *     <li>Add a new nestable annotation to the new container annotation
	 *     at index 0
	 *     <li>Move the existing stand-alone nestable annotation to the new
	 *     container annotation at index 1
	 * </ol>
	 * <li>If the new annotation is to be added at index 1<ol>
	 *     <li>Move the existing stand-alone nestable annotation to the new
	 *     container annotation at index 0
	 *     <li>Add a new nestable annotation to the new container annotation
	 *     at index 1
	 * </ol>
	 * </ol>
	 */
	private NestableAnnotation addSecondNestedAnnotation(int index, String containerAnnotationName, NestableAnnotation standAloneAnnotation) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.buildContainerAnnotation(containerAnnotationName);
		this.annotations.add(containerAnnotation);
		containerAnnotation.newAnnotation();

		NestableAnnotation nestedAnnotation = null;
		switch (index) {
			case 0 :
				// adding new annotation at 0, so move the stand-alone to slot 1
				nestedAnnotation = containerAnnotation.addNestedAnnotation();
				nestedAnnotation.newAnnotation();
				this.annotations.remove(standAloneAnnotation);
				containerAnnotation.nestStandAloneAnnotation(standAloneAnnotation);
				break;
			case 1 :
				// adding new annotation at 1, so move the stand-alone to slot 0
				this.annotations.remove(standAloneAnnotation);
				containerAnnotation.nestStandAloneAnnotation(standAloneAnnotation);
				nestedAnnotation = containerAnnotation.addNestedAnnotation();
				nestedAnnotation.newAnnotation();
				break;
			default :
				throw new IllegalArgumentException("invalid index: " + index); //$NON-NLS-1$
		}

		return nestedAnnotation;
	}

	public void moveAnnotation(int targetIndex, int sourceIndex, String containerAnnotationName) {
		this.moveAnnotation(targetIndex, sourceIndex, this.getContainerAnnotation(containerAnnotationName));
	}

	private void moveAnnotation(int targetIndex, int sourceIndex, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		AnnotationContainerTools.moveNestedAnnotation(targetIndex, sourceIndex, containerAnnotation);
	}

	public void removeAnnotation(String annotationName) {
		Annotation annotation = this.getAnnotation(annotationName);
		if (annotation != null) {
			this.removeAnnotation(annotation);
		}
	}

	private void removeAnnotation(Annotation annotation) {
		this.annotations.remove(annotation);
		annotation.removeAnnotation();
	}

	public void removeAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.getContainerAnnotation(containerAnnotationName);
		if (containerAnnotation == null) {
			if (index != 0) {
				throw new IllegalArgumentException("invalid index: " + index); //$NON-NLS-1$
			}
			this.removeAnnotation(this.getAnnotation(nestableAnnotationName));
		} else {
			this.removeAnnotation(index, containerAnnotation);
		}
	}

	/**
	 * After we remove the nested annotation, check to see whether we need to
	 * either remove the container (if it is empty) or convert the last nested
	 * annotation to a stand-alone annotation.
	 */
	private void removeAnnotation(int index, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		AnnotationContainerTools.removeNestedAnnotation(index, containerAnnotation);
		switch (containerAnnotation.getNestedAnnotationsSize()) {
			case 0:
				this.removeAnnotation(containerAnnotation);
				break;
			case 1:
				this.convertLastNestedAnnotation(containerAnnotation);
				break;
			default:
				break;
		}
	}

	/**
	 * Convert the last nested annotation in the container to a stand-alone
	 * annotation and remove the container annotation.
	 */
	private void convertLastNestedAnnotation(ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		containerAnnotation.convertLastNestedAnnotationToStandAlone();
		this.annotations.remove(containerAnnotation);
		containerAnnotation.removeAnnotation();
	}

	public void addStandAloneAnnotation(NestableAnnotation standAloneAnnotation) {
		this.annotations.add(standAloneAnnotation);
	}

	private boolean annotationIsValid(String annotationName) {
		return CollectionTools.contains(this.validAnnotationNames(), annotationName);
	}

	abstract Iterator<String> validAnnotationNames();

	abstract Annotation buildAnnotation(String annotationName);

	abstract Annotation buildNullAnnotation(String annotationName);

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private ContainerAnnotation<NestableAnnotation> buildContainerAnnotation(String annotationName) {
		return (ContainerAnnotation<NestableAnnotation>) this.buildAnnotation(annotationName);
	}

	private void syncAnnotations(CompilationUnit astRoot) {
		HashSet<Annotation> annotationsToRemove = new HashSet<Annotation>(this.annotations);

		this.annotatedElement.getBodyDeclaration(astRoot).accept(this.buildSynchronizeAnnotationVisitor(astRoot, annotationsToRemove));

		this.removeItemsFromCollection(annotationsToRemove, this.annotations, ANNOTATIONS_COLLECTION);
	}

	private ASTVisitor buildSynchronizeAnnotationVisitor(CompilationUnit astRoot, Set<Annotation> annotationsToRemove) {
		return new SynchronizeAnnotationVisitor(astRoot, this.annotatedElement.getBodyDeclaration(astRoot), annotationsToRemove);
	}

	/**
	 * called from {@link SynchronizeAnnotationVisitor#visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation)}
	 */
	/* CU private */ void addOrSyncAnnotation(org.eclipse.jdt.core.dom.Annotation astAnnotation, CompilationUnit astRoot, Set<Annotation> annotationsToRemove) {
		String astAnnotationName = ASTTools.resolveAnnotation(astAnnotation);
		if (astAnnotationName != null) {
			this.addOrSyncAnnotation(astAnnotationName, astRoot, annotationsToRemove);
		}
	}

	/**
	 * pre-condition: <code>astAnnotationName</code> is not <code>null</code>
	 */
	void addOrSyncAnnotation(String astAnnotationName, CompilationUnit astRoot, Set<Annotation> annotationsToRemove) {
		if (this.annotationIsValid(astAnnotationName)) {
			this.addOrSyncAnnotation_(astAnnotationName, astRoot, annotationsToRemove);
		}
	}

	/**
	 * pre-condition: <code>astAnnotationName</code> is valid
	 */
	private void addOrSyncAnnotation_(String astAnnotationName, CompilationUnit astRoot, Set<Annotation> annotationsToRemove) {
		Annotation annotation = this.selectAnnotationNamed(annotationsToRemove, astAnnotationName);
		if (annotation != null) {
			annotation.synchronizeWith(astRoot);
			annotationsToRemove.remove(annotation);
		} else {
			annotation = this.buildAnnotation(astAnnotationName);
			annotation.initialize(astRoot);
			this.addItemToCollection(annotation, this.annotations, ANNOTATIONS_COLLECTION);
		}
	}


	// ********** misc **********

	public boolean isAnnotated() {
		return ! this.annotations.isEmpty();
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		// the AST is null for virtual Java attributes
		// TODO remove the AST null check once we start storing text ranges
		// in the resource model
		return (astRoot == null) ? null : this.buildTextRange(this.annotatedElement.getBodyDeclaration(astRoot));
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		// the AST is null for virtual Java attributes
		// TODO remove the AST null check once we start storing text ranges
		// in the resource model
		return (astRoot == null) ? null : this.annotatedElement.getNameTextRange(astRoot);
	}

	private Annotation selectAnnotationNamed(Iterable<Annotation> list, String annotationName) {
		for (Annotation annotation : list) {
			if (annotation.getAnnotationName().equals(annotationName)) {
				return annotation;
			}
		}
		return null;
	}

	private TextRange buildTextRange(ASTNode astNode) {
		return (astNode == null) ? null : new ASTNodeTextRange(astNode);
	}


	// ********** AST visitors **********

	/**
	 * annotation visitor
	 */
	static abstract class AnnotationVisitor
			extends ASTVisitor
	{
		final CompilationUnit astRoot;
		final ASTNode node;


		protected AnnotationVisitor(CompilationUnit astRoot, ASTNode node) {
			super();
			this.astRoot = astRoot;
			this.node = node;
		}

		@Override
		public boolean visit(SingleMemberAnnotation annotation) {
			return this.visit_(annotation);
		}

		@Override
		public boolean visit(NormalAnnotation annotation) {
			return this.visit_(annotation);
		}

		@Override
		public boolean visit(MarkerAnnotation annotation) {
			return this.visit_(annotation);
		}

		protected boolean visit_(org.eclipse.jdt.core.dom.Annotation annotation) {
			// ignore annotations for child members, only this member
			if (annotation.getParent() == this.node) {
				this.visitChildAnnotation(annotation);
			}
			return false;
		}

		protected abstract void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation annotation);
	}


	/**
	 * initial annotation visitor
	 */
	class InitialAnnotationVisitor
			extends AnnotationVisitor
	{
		protected InitialAnnotationVisitor(CompilationUnit astRoot, ASTNode node) {
			super(astRoot, node);
		}

		@Override
		protected void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation annotation) {
			SourceAnnotatedElement.this.addInitialAnnotation(annotation, this.astRoot);
		}
	}


	/**
	 * synchronize annotation visitor
	 */
	class SynchronizeAnnotationVisitor
			extends AnnotationVisitor
	{
		protected final Set<Annotation> annotationsToRemove;

		protected SynchronizeAnnotationVisitor(CompilationUnit astRoot, ASTNode node, Set<Annotation> annotationsToRemove) {
			super(astRoot, node);
			this.annotationsToRemove = annotationsToRemove;
		}

		@Override
		protected void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation annotation) {
			SourceAnnotatedElement.this.addOrSyncAnnotation(annotation, this.astRoot, this.annotationsToRemove);
		}
	}
}
