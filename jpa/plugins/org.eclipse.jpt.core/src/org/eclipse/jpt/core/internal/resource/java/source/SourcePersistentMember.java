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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ASTNodeTextRange;
import org.eclipse.jpt.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;

/**
 * Java source persistent member (annotations, "persistable")
 */
abstract class SourcePersistentMember<M extends Member>
	extends SourceNode
	implements JavaResourcePersistentMember
{
	final M member;

	/**
	 * annotations; no duplicates (java compiler has an error for duplicates)
	 */
	final Vector<Annotation> annotations = new Vector<Annotation>();

	boolean persistable;

	boolean final_;  // 'final' is a reserved word


	// ********** construction/initialization **********

	SourcePersistentMember(JavaResourceNode parent, M member) {
		super(parent);
		this.member = member;
	}

	public void initialize(CompilationUnit astRoot) {
		this.member.getBodyDeclaration(astRoot).accept(this.buildInitialAnnotationVisitor(astRoot));
		this.persistable = this.buildPersistable(astRoot);
		IBinding binding = this.member.getBinding(astRoot);
		this.final_ = this.buildFinal(binding);
	}

	private ASTVisitor buildInitialAnnotationVisitor(CompilationUnit astRoot) {
		return new InitialAnnotationVisitor(astRoot, this.member.getBodyDeclaration(astRoot));
	}

	/**
	 * called from {@link InitialAnnotationVisitor}
	 */
	/* private */ void addInitialAnnotation(org.eclipse.jdt.core.dom.Annotation node, CompilationUnit astRoot) {
		String jdtAnnotationName = ASTTools.resolveAnnotation(node);
		if (jdtAnnotationName != null) {
			this.addInitialAnnotation(jdtAnnotationName, astRoot);
		}
	}

	/**
	 * pre-condition: jdtAnnotationName is not null
	 */
	void addInitialAnnotation(String jdtAnnotationName, CompilationUnit astRoot) {
		if (this.annotationIsValid(jdtAnnotationName)) {
			if (this.selectAnnotationNamed(this.annotations, jdtAnnotationName) == null) { // ignore duplicates
				Annotation annotation = this.buildAnnotation(jdtAnnotationName);
				annotation.initialize(astRoot);
				this.annotations.add(annotation);
			}
		}
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncAnnotations(astRoot);
		this.syncPersistable(this.buildPersistable(astRoot));
		IBinding binding = this.member.getBinding(astRoot);
		this.syncFinal(this.buildFinal(binding));
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
		return (annotation != null) ? annotation : this.buildNullAnnotation(annotationName);
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
	 * 1. check for a container annotation;
	 *     if it is present, add a nested annotation to it
	 * 2. check for a stand-alone nested annotation;
	 *     if it is missing, add a stand-alone nested annotation
	 * 3. if there is an existing stand-alone nested annotation,
	 *     add a container annotation and move the stand-alone nested annotation to it
	 *     and add a new nested annotation to it also
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
	 * "move" the existing nestable annotation to a new container annotation and
	 * add a new nestable annotation at the specified index (which should be 0 or 1)
	 */
	private NestableAnnotation addSecondNestedAnnotation(int index, String containerAnnotationName, NestableAnnotation standAloneAnnotation) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.buildContainerAnnotation(containerAnnotationName);
		this.annotations.add(containerAnnotation);
		containerAnnotation.newAnnotation();

		NestableAnnotation nestedAnnotation0 = containerAnnotation.addNestedAnnotation();
		nestedAnnotation0.newAnnotation();
		NestableAnnotation nestedAnnotation1 = containerAnnotation.addNestedAnnotation();
		nestedAnnotation1.newAnnotation();
		this.removeAnnotation(standAloneAnnotation);

		if (index == 0) {
			// adding new annotation at 0, so stand-alone is "copied" to slot 1
			nestedAnnotation1.initializeFrom(standAloneAnnotation);
		} else {
			// adding new annotation at 1, so stand-alone is "copied" to slot 0
			nestedAnnotation0.initializeFrom(standAloneAnnotation);
		}

		return (index == 0) ? nestedAnnotation0 : nestedAnnotation1;
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
		if (containerAnnotation == null) {  // assume the index is 0
			this.removeAnnotation(this.getAnnotation(nestableAnnotationName));
		} else {
			this.removeAnnotation(index, containerAnnotation);
		}
	}

	/**
	 * after we remove the nested annotation, check to see whether we need to
	 * either remove the container (if it is empty) or convert the last nested
	 * annotation to a stand-alone annotation
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
	 * convert the last nested annotation in the container to a stand-alone
	 * annotation
	 */
	private void convertLastNestedAnnotation(ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		NestableAnnotation lastNestedAnnotation = containerAnnotation.getNestedAnnotations().iterator().next();
		this.annotations.remove(containerAnnotation);
		containerAnnotation.removeAnnotation();

		NestableAnnotation standAloneAnnotation = this.buildNestableAnnotation(lastNestedAnnotation.getAnnotationName());
		this.annotations.add(standAloneAnnotation);
		standAloneAnnotation.newAnnotation();
		standAloneAnnotation.initializeFrom(lastNestedAnnotation);
	}

	public Annotation setPrimaryAnnotation(String primaryAnnotationName, Iterable<String> supportingAnnotationNames) {
		ArrayList<String> annotationNames = new ArrayList<String>();
		CollectionTools.addAll(annotationNames, supportingAnnotationNames);
		if (primaryAnnotationName != null) {
			annotationNames.add(primaryAnnotationName);
		}
		for (Annotation annotation : this.getAnnotations()) {
			if ( ! CollectionTools.contains(annotationNames, annotation.getAnnotationName())) {
				this.annotations.remove(annotation);
				annotation.removeAnnotation();
			}
		}
		Annotation newPrimaryAnnotation = null;
		if ((primaryAnnotationName != null) && (this.getAnnotation(primaryAnnotationName) == null)) {
			newPrimaryAnnotation = this.buildAnnotation(primaryAnnotationName);
			this.annotations.add(newPrimaryAnnotation);
			newPrimaryAnnotation.newAnnotation();
		}
		// fire collection change event after all annotation changes are done
		this.fireCollectionChanged(ANNOTATIONS_COLLECTION, this.annotations);
		return newPrimaryAnnotation;
	}

	private boolean annotationIsValid(String annotationName) {
		return CollectionTools.contains(this.validAnnotationNames(), annotationName);
	}

	abstract Iterator<String> validAnnotationNames();

	abstract Annotation buildAnnotation(String mappingAnnotationName);

	abstract Annotation buildNullAnnotation(String annotationName);

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private ContainerAnnotation<NestableAnnotation> buildContainerAnnotation(String annotationName) {
		return (ContainerAnnotation<NestableAnnotation>) this.buildAnnotation(annotationName);
	}

	private NestableAnnotation buildNestableAnnotation(String annotationName) {
		return (NestableAnnotation) this.buildAnnotation(annotationName);
	}

	private void syncAnnotations(CompilationUnit astRoot) {
		HashSet<Annotation> annotationsToRemove = new HashSet<Annotation>(this.annotations);

		this.member.getBodyDeclaration(astRoot).accept(this.buildSynchronizeAnnotationVisitor(astRoot, annotationsToRemove));

		for (Annotation annotation : annotationsToRemove) {
			this.removeItemFromCollection(annotation, this.annotations, ANNOTATIONS_COLLECTION);
		}
	}

	private ASTVisitor buildSynchronizeAnnotationVisitor(CompilationUnit astRoot, Set<Annotation> annotationsToRemove) {
		return new SynchronizeAnnotationVisitor(astRoot, this.member.getBodyDeclaration(astRoot), annotationsToRemove);
	}

	/**
	 * called from {@link SynchronizeAnnotationVisitor}
	 */
	/* private */ void addOrSyncAnnotation(org.eclipse.jdt.core.dom.Annotation node, CompilationUnit astRoot, Set<Annotation> annotationsToRemove) {
		String jdtAnnotationName = ASTTools.resolveAnnotation(node);
		if (jdtAnnotationName != null) {
			this.addOrSyncAnnotation(jdtAnnotationName, astRoot, annotationsToRemove);
		}
	}

	/**
	 * pre-condition: jdtAnnotationName is not null
	 */
	void addOrSyncAnnotation(String jdtAnnotationName, CompilationUnit astRoot, Set<Annotation> annotationsToRemove) {
		if (this.annotationIsValid(jdtAnnotationName)) {
			this.addOrSyncAnnotation_(jdtAnnotationName, astRoot, annotationsToRemove);
		}
	}

	/**
	 * pre-condition: jdtAnnotationName is valid
	 */
	private void addOrSyncAnnotation_(String jdtAnnotationName, CompilationUnit astRoot, Set<Annotation> annotationsToRemove) {
		Annotation annotation = this.selectAnnotationNamed(annotationsToRemove, jdtAnnotationName);
		if (annotation != null) {
			annotation.synchronizeWith(astRoot);
			annotationsToRemove.remove(annotation);
		} else {
			annotation = this.buildAnnotation(jdtAnnotationName);
			annotation.initialize(astRoot);
			this.addItemToCollection(annotation, this.annotations, ANNOTATIONS_COLLECTION);
		}
	}


	// ********** persistable **********

	public boolean isPersistable() {
		return this.persistable;
	}

	private void syncPersistable(boolean astPersistable) {
		boolean old = this.persistable;
		this.persistable = astPersistable;
		this.firePropertyChanged(PERSISTABLE_PROPERTY, old, astPersistable);
	}

	private boolean buildPersistable(CompilationUnit astRoot) {
		return this.member.isPersistable(astRoot);
	}

	// ***** final
	public boolean isFinal() {
		return this.final_;
	}

	private void syncFinal(boolean astFinal) {
		boolean old = this.final_;
		this.final_ = astFinal;
		this.firePropertyChanged(FINAL_PROPERTY, old, astFinal);
	}

	private boolean buildFinal(IBinding binding) {
		return (binding == null) ? false : Modifier.isFinal(binding.getModifiers());
	}


	// ********** miscellaneous **********

	public boolean isAnnotated() {
		return ! this.annotations.isEmpty();
	}

	public boolean isFor(String memberName, int occurrence) {
		return this.member.matches(memberName, occurrence);
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		return this.fullTextRange(astRoot);
	}

	private TextRange fullTextRange(CompilationUnit astRoot) {
		return this.buildTextRange(this.member.getBodyDeclaration(astRoot));
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.member.getNameTextRange(astRoot);
	}

	public void resolveTypes(CompilationUnit astRoot) {
		this.syncPersistable(this.buildPersistable(astRoot));
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

	/**
	 * convenience method
	 */
	<T extends JavaResourcePersistentMember> Iterator<T> persistableMembers(Iterator<T> members) {
		return new FilteringIterator<T>(members) {
			@Override
			protected boolean accept(T m) {
				return m.isPersistable();
			}
		};
	}


	// ********** AST visitors **********

	/**
	 * annotation visitor
	 */
	protected static abstract class AnnotationVisitor
			extends ASTVisitor
	{
		protected final CompilationUnit astRoot;
		protected final BodyDeclaration bodyDeclaration;

		protected AnnotationVisitor(CompilationUnit astRoot, BodyDeclaration bodyDeclaration) {
			super();
			this.astRoot = astRoot;
			this.bodyDeclaration = bodyDeclaration;
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
			// ignore annotations for child members, only this member
			if (node.getParent() == this.bodyDeclaration) {
				this.visitChildAnnotation(node);
			}
			return false;
		}

		protected abstract void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation node);
	}


	/**
	 * initial annotation visitor
	 */
	protected class InitialAnnotationVisitor
			extends AnnotationVisitor
	{
		protected InitialAnnotationVisitor(CompilationUnit astRoot, BodyDeclaration bodyDeclaration) {
			super(astRoot, bodyDeclaration);
		}

		@Override
		protected void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation node) {
			SourcePersistentMember.this.addInitialAnnotation(node, this.astRoot);
		}
	}


	/**
	 * synchronize annotation visitor
	 */
	protected class SynchronizeAnnotationVisitor
			extends AnnotationVisitor
	{
		protected final Set<Annotation> annotationsToRemove;

		protected SynchronizeAnnotationVisitor(CompilationUnit astRoot, BodyDeclaration bodyDeclaration, Set<Annotation> annotationsToRemove) {
			super(astRoot, bodyDeclaration);
			this.annotationsToRemove = annotationsToRemove;
		}

		@Override
		protected void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation node) {
			SourcePersistentMember.this.addOrSyncAnnotation(node, this.astRoot, this.annotationsToRemove);
		}
	}
}
