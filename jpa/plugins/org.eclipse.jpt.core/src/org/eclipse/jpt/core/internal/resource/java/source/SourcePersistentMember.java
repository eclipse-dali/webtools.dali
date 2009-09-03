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

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ASTNodeTextRange;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;

/**
 * Java source persistent member (annotations, "persistable")
 */
abstract class SourcePersistentMember<E extends Member>
	extends SourceNode
	implements JavaResourcePersistentMember
{
	final E member;
	
	/**
	 * annotations; no duplicates (java compiler has an error for duplicates)
	 */
	final Vector<Annotation> annotations = new Vector<Annotation>();
	
	boolean persistable;
	
	
	// ********** construction/initialization **********
	
	SourcePersistentMember(JavaResourceNode parent, E member) {
		super(parent);
		this.member = member;
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.member.getBodyDeclaration(astRoot).accept(this.buildInitialAnnotationVisitor(astRoot));
		this.persistable = this.buildPersistable(astRoot);
	}
	
	private ASTVisitor buildInitialAnnotationVisitor(CompilationUnit astRoot) {
		return new InitialAnnotationVisitor(astRoot, this.member.getBodyDeclaration(astRoot));
	}
	
	/**
	 * called from InitialAnnotationVisitor 
	 */
	void addInitialAnnotation(org.eclipse.jdt.core.dom.Annotation node, CompilationUnit astRoot) {
		String jdtAnnotationName = JDTTools.resolveAnnotation(node);
		if (jdtAnnotationName == null) {
			return;
		}
		if (this.annotationIsValid(jdtAnnotationName)) {
			if (this.selectAnnotationNamed(this.annotations, jdtAnnotationName) == null) { // ignore duplicates
				Annotation annotation = this.buildAnnotation(jdtAnnotationName);
				annotation.initialize(astRoot);
				this.annotations.add(annotation);
			}
		}
	}
	
	
	// ********** annotations **********
	
	public Iterator<Annotation> annotations() {
		return this.getAnnotations().iterator();
	}
	
	private Iterable<Annotation> getAnnotations() {
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
	
	public Iterator<NestableAnnotation> annotations(
			String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = 
				this.getContainerAnnotation(containerAnnotationName);
		if (containerAnnotation != null) {
			return containerAnnotation.nestedAnnotations();
		}
		NestableAnnotation nestableAnnotation = 
				getNestableAnnotation(nestableAnnotationName);
		if (nestableAnnotation != null) {
			return new SingleElementIterator<NestableAnnotation>(nestableAnnotation);
		}
		return EmptyIterator.instance();
	}
	
	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private ContainerAnnotation<NestableAnnotation> getContainerAnnotation(String annotationName) {
		return (ContainerAnnotation<NestableAnnotation>) getAnnotation(annotationName);
	}
	
	private NestableAnnotation getNestableAnnotation(String annotationName) {
		return (NestableAnnotation) this.getAnnotation(annotationName);
	}
	
	public Annotation addAnnotation(String annotationName) {
		Annotation annotation = this.buildAnnotation(annotationName);
		this.annotations.add(annotation);
		annotation.newAnnotation();
		this.fireItemAdded(ANNOTATIONS_COLLECTION, annotation);
		return annotation;
	}
	
	public Annotation addAnnotation(String annotationName, AnnotationInitializer annotationInitializer) {
		Annotation annotation = this.buildAnnotation(annotationName);
		this.annotations.add(annotation);
		annotation.newAnnotation();
		Annotation nestedAnnotation = annotationInitializer.initializeAnnotation(annotation);
		nestedAnnotation.newAnnotation();
		this.fireItemAdded(ANNOTATIONS_COLLECTION, annotation);
		return nestedAnnotation;
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
	public NestableAnnotation addAnnotation(
			int index, String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = 
				getContainerAnnotation(containerAnnotationName);
		if (containerAnnotation != null) {
			// ignore any stand-alone nestable annotations and just add to the container annotation
			return AnnotationContainerTools.addNestedAnnotation(index, containerAnnotation);
		}
		NestableAnnotation standAloneAnnotation = 
				getNestableAnnotation(nestableAnnotationName);
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
	 * 
	 * NB: we make all the necessary changes to the resource model and the
	 * Java source code *before* we fire any events; so that everything is in
	 * place when the context model gets an "update" and looks at the resource
	 * model to determine what has changed
	 */
	private NestableAnnotation addSecondNestedAnnotation(
			int index, String containerAnnotationName, NestableAnnotation standAloneAnnotation) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = 
				buildContainerAnnotation(containerAnnotationName);
		this.annotations.add(containerAnnotation);
		containerAnnotation.newAnnotation();
		
		NestableAnnotation nestedAnnotation0 = containerAnnotation.addNestedAnnotationInternal();
		nestedAnnotation0.newAnnotation();
		NestableAnnotation nestedAnnotation1 = containerAnnotation.addNestedAnnotationInternal();
		nestedAnnotation1.newAnnotation();
		this.removeAnnotation(standAloneAnnotation);
		this.fireItemAdded(ANNOTATIONS_COLLECTION, containerAnnotation);
		
		if (index == 0) {
			// adding new annotation at 0, so stand-alone is "copied" to slot 1
			nestedAnnotation1.initializeFrom(standAloneAnnotation);
		} else {
			// adding new annotation at 1, so stand-alone is "copied" to slot 0
			nestedAnnotation0.initializeFrom(standAloneAnnotation);
		}
		
		return (index == 0) ? nestedAnnotation0 : nestedAnnotation1;
	}
	
	public void moveAnnotation(
			int targetIndex, int sourceIndex, String containerAnnotationName) {
		moveAnnotation(targetIndex, sourceIndex, getContainerAnnotation(containerAnnotationName));
	}
	
	private void moveAnnotation(
			int targetIndex, int sourceIndex, 
			ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		AnnotationContainerTools.moveNestedAnnotation(targetIndex, sourceIndex, containerAnnotation);
	}

	public void removeAnnotation(String annotationName) {
		Annotation annotation = getAnnotation(annotationName);
		if (annotation != null) {
			this.removeAnnotation(annotation);
		}
	}
	
	private void removeAnnotation(Annotation annotation) {
		this.annotations.remove(annotation);
		annotation.removeAnnotation();
		this.fireItemRemoved(ANNOTATIONS_COLLECTION, annotation);
	}
	
	public void removeAnnotation(
			int index, String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = 
				getContainerAnnotation(containerAnnotationName);
		if (containerAnnotation == null) {  // assume the index is 0
			removeAnnotation(getAnnotation(nestableAnnotationName));
		} 
		else {
			removeAnnotation(index, containerAnnotation);
		}
	}
	
	/**
	 * after we remove the nested annotation, check to see whether we need to
	 * either remove the container (if it is empty) or convert the last nested
	 * annotation to a stand-alone annotation
	 */
	private void removeAnnotation(
			int index, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		AnnotationContainerTools.removeNestedAnnotation(index, containerAnnotation);
		switch (containerAnnotation.nestedAnnotationsSize()) {
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
	 * 
	 * NB: we make all the necessary changes to the resource model and the
	 * Java source code *before* we fire any events; so that everything is in
	 * place when the context model gets an "update" and looks at the resource
	 * model to determine what has changed
	 */
	private void convertLastNestedAnnotation(
			ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		NestableAnnotation lastNestedAnnotation = containerAnnotation.nestedAnnotations().next();
		annotations.remove(containerAnnotation);
		containerAnnotation.removeAnnotation();
		
		NestableAnnotation standAloneAnnotation = 
				buildNestableAnnotation(lastNestedAnnotation.getAnnotationName());
		this.annotations.add(standAloneAnnotation);
		standAloneAnnotation.newAnnotation();
		this.fireItemRemoved(ANNOTATIONS_COLLECTION, containerAnnotation);
		this.fireItemAdded(ANNOTATIONS_COLLECTION, standAloneAnnotation);
		standAloneAnnotation.initializeFrom(lastNestedAnnotation);
	}
	
	public Annotation setPrimaryAnnotation(
			String primaryAnnotationName, String[] supportingAnnotationNames) {
		Annotation newPrimaryAnnotation = null;
		String[] annotationNamesToKeep = supportingAnnotationNames;
		if (primaryAnnotationName != null) {
			annotationNamesToKeep = ArrayTools.add(supportingAnnotationNames, primaryAnnotationName);
		}
		for (Annotation existingAnnotation : getAnnotations()) {
			if (! ArrayTools.contains(annotationNamesToKeep, existingAnnotation.getAnnotationName())) {
				this.annotations.remove(existingAnnotation);
				existingAnnotation.removeAnnotation();
			}
		}
		if (primaryAnnotationName != null && getAnnotation(primaryAnnotationName) == null) {
			newPrimaryAnnotation = buildAnnotation(primaryAnnotationName);
			this.annotations.add(newPrimaryAnnotation);
			newPrimaryAnnotation.newAnnotation();
		}
		// fire collection change event after all annotation changes are done
		fireCollectionChanged(ANNOTATIONS_COLLECTION, Collections.unmodifiableCollection(this.annotations));
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
		return (ContainerAnnotation<NestableAnnotation>) buildAnnotation(annotationName);
	}
	
	@SuppressWarnings("unchecked")
	private NestableAnnotation buildNestableAnnotation(String annotationName) {
		return (NestableAnnotation) buildAnnotation(annotationName);
	}
	
	
	// ********** simple state **********
	
	public boolean isPersistable() {
		return this.persistable;
	}
	
	private void setPersistable(boolean persistable) {
		boolean old = this.persistable;
		this.persistable = persistable;
		this.firePropertyChanged(PERSISTABLE_PROPERTY, old, persistable);
	}
	
	private boolean buildPersistable(CompilationUnit astRoot) {
		return this.member.isPersistable(astRoot);
	}
	
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
	
	
	// ********** update **********
	
	public void update(CompilationUnit astRoot) {
		this.updateAnnotations(astRoot);
		this.setPersistable(this.buildPersistable(astRoot));
	}
	
	private void updateAnnotations(CompilationUnit astRoot) {
		HashSet<Annotation> annotationsToRemove = 
				new HashSet<Annotation>(this.annotations);
		
		this.member.getBodyDeclaration(astRoot).accept(
				this.buildUpdateAnnotationVisitor(astRoot, annotationsToRemove));
		
		for (Annotation annotation : annotationsToRemove) {
			this.removeItemFromCollection(annotation, this.annotations, ANNOTATIONS_COLLECTION);
		}
	}
	
	private ASTVisitor buildUpdateAnnotationVisitor(
			CompilationUnit astRoot, Set<Annotation> annotationsToRemove) {
		return new UpdateAnnotationVisitor(
				astRoot, this.member.getBodyDeclaration(astRoot), annotationsToRemove);
	}
	
	void addOrUpdateAnnotation(
			org.eclipse.jdt.core.dom.Annotation node, CompilationUnit astRoot, 
			Set<Annotation> annotationsToRemove) {
		String jdtAnnotationName = JDTTools.resolveAnnotation(node);
		if (jdtAnnotationName == null) {
			return;
		}
		if (this.annotationIsValid(jdtAnnotationName)) {
			this.addOrUpdateAnnotation(jdtAnnotationName, astRoot, annotationsToRemove);
			return;
		}
	}
	
	private void addOrUpdateAnnotation(
			String jdtAnnotationName, CompilationUnit astRoot, 
			Set<Annotation> annotationsToRemove) {
		Annotation annotation = this.selectAnnotationNamed(annotationsToRemove, jdtAnnotationName);
		if (annotation != null) {
			annotation.update(astRoot);
			annotationsToRemove.remove(annotation);
		}
		else {
			annotation = this.buildAnnotation(jdtAnnotationName);
			annotation.initialize(astRoot);
			this.addItemToCollection(annotation, this.annotations, ANNOTATIONS_COLLECTION);
		}
	}
	
	
	// ********** miscellaneous **********
	
	public void resolveTypes(CompilationUnit astRoot) {
		this.setPersistable(this.buildPersistable(astRoot));
	}
	
	private Annotation selectAnnotationNamed(Iterable<Annotation> annotations, String annotationName) {
		for (Annotation annotation : annotations) {
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
		return new FilteringIterator<T, T>(members) {
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
			return visit_(node);
		}
		
		@Override
		public boolean visit(NormalAnnotation node) {
			return visit_(node);
		}
		
		@Override
		public boolean visit(MarkerAnnotation node) {
			return visit_(node);
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
	 * update annotation visitor
	 */
	protected class UpdateAnnotationVisitor 
			extends AnnotationVisitor 
	{
		protected final Set<Annotation> annotationsToRemove;
		
		protected UpdateAnnotationVisitor(
				CompilationUnit astRoot, BodyDeclaration bodyDeclaration, 
				Set<Annotation> annotationsToRemove) {
			super(astRoot, bodyDeclaration);
			this.annotationsToRemove = annotationsToRemove;
		}
		
		@Override
		protected void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation node) {
			SourcePersistentMember.this.addOrUpdateAnnotation(
					node, this.astRoot, this.annotationsToRemove);
		}		
	}
}
