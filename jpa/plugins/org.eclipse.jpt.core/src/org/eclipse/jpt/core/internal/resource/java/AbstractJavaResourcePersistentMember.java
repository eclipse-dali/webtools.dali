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

import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
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
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

/**
 * 
 */
public abstract class AbstractJavaResourcePersistentMember<E extends Member> extends AbstractJavaResourceNode implements JavaResourcePersistentMember {
	private final E member;

	/**
	 * mapping annotations; no duplicates (java compiler has an error for
	 * duplicates)
	 */
	private final Vector<Annotation> mappingAnnotations;

	/**
	 * supporting annotations; no duplicates (java compiler has an error for
	 * duplicates)
	 */
	private final Vector<Annotation> supportingAnnotations;

	private boolean persistable;

	// ********** construction/initialization **********

	protected AbstractJavaResourcePersistentMember(JavaResourceNode parent, E member) {
		super(parent);
		this.member = member;
		this.supportingAnnotations = new Vector<Annotation>();
		this.mappingAnnotations = new Vector<Annotation>();
	}

	public void initialize(CompilationUnit astRoot) {
		this.getMember().getBodyDeclaration(astRoot).accept(this.buildInitialAnnotationVisitor(astRoot));
		this.persistable = this.buildPersistable(astRoot);
	}

	protected ASTVisitor buildInitialAnnotationVisitor(CompilationUnit astRoot) {
		return new InitialAnnotationVisitor(astRoot, this.getMember().getBodyDeclaration(astRoot));
	}

	protected void addInitialAnnotation(org.eclipse.jdt.core.dom.Annotation node, CompilationUnit astRoot) {
		String jdtAnnotationName = JDTTools.resolveAnnotation(node);
		if (jdtAnnotationName == null) {
			return;
		}
		if (this.supportingAnnotationIsValid(jdtAnnotationName)) {
			if (this.getSupportingAnnotation(jdtAnnotationName) == null) { // ignore duplicates
				Annotation annotation = this.buildSupportingAnnotation(jdtAnnotationName);
				annotation.initialize(astRoot);
				this.supportingAnnotations.add(annotation);
			}
		} else if (this.mappingAnnotationIsValid(jdtAnnotationName)) {
			if (this.getMappingAnnotation(jdtAnnotationName) == null) { // ignore duplicates
				Annotation annotation = this.buildMappingAnnotation(jdtAnnotationName);
				annotation.initialize(astRoot);
				this.mappingAnnotations.add(annotation);
			}
		}
	}

	// ********** mapping annotations **********

	public Iterator<Annotation> mappingAnnotations() {
		return new CloneIterator<Annotation>(this.mappingAnnotations);
	}

	public int mappingAnnotationsSize() {
		return this.mappingAnnotations.size();
	}

	// TODO need property change notification on this mappingAnnotation changing
	// from the context model we don't really care if their are multiple mapping
	// annotations, just which one we need to use
	public Annotation getMappingAnnotation() {
		synchronized (this.mappingAnnotations) {
			return this.getMappingAnnotation_();
		}
	}

	protected Annotation getMappingAnnotation_() {
		for (ListIterator<String> stream = this.validMappingAnnotationNames(); stream.hasNext();) {
			Annotation annotation = this.getMappingAnnotation(stream.next());
			if (annotation != null) {
				return annotation;
			}
		}
		return null;
	}

	public Annotation getMappingAnnotation(String annotationName) {
		return getAnnotation(this.mappingAnnotations(), annotationName);
	}

	/**
	 * Remove all other *mapping* annotations that exist; not just the one
	 * returned by #getMappingAnnotation(). #getMappingAnnotation() returns the
	 * first mapping annotation found in the source. If there were multiple
	 * mapping annotations (which is a validation error) then calling this
	 * method would not work because the new mapping annotation would be added
	 * to the end of the list of annotations.
	 */
	public void setMappingAnnotation(String annotationName) {
		synchronized (this.mappingAnnotations) {
			this.setMappingAnnotation_(annotationName);
		}
		// hold change notification until the end so a project update does not
		// occur
		// before we are finished removing the old mapping(s) and adding the new
		// mapping;
		// just fire "collection changed" since one or more removes and/or one
		// add occurred;
		this.fireCollectionChanged(MAPPING_ANNOTATIONS_COLLECTION);
	}

	protected void setMappingAnnotation_(String annotationName) {
		if (annotationName == null) {
			this.removeMappingAnnotations_();
			return;
		}

		if (this.getMappingAnnotation(annotationName) != null) {
			throw new IllegalStateException("duplicate mapping annotation: " + annotationName); //$NON-NLS-1$
		}

		this.removeMappingAnnotations_();
		Annotation newMapping = this.buildMappingAnnotation(annotationName);
		this.mappingAnnotations.add(newMapping);
		newMapping.newAnnotation();
	}

	/**
	 * Remove all mapping annotations that already exist. No change notification
	 * is fired.
	 */
	protected void removeMappingAnnotations() {
		synchronized (this.mappingAnnotations) {
			this.removeMappingAnnotations_();
		}
	}

	protected void removeMappingAnnotations_() {
		for (ListIterator<String> stream = this.validMappingAnnotationNames(); stream.hasNext();) {
			Annotation mappingAnnotation = this.getMappingAnnotation(stream.next());
			if (mappingAnnotation != null) {
				this.mappingAnnotations.remove(mappingAnnotation);
				mappingAnnotation.removeAnnotation();
			}
		}
	}

	public JavaResourceNode getNullMappingAnnotation(String annotationName) {
		return this.buildNullMappingAnnotation(annotationName);
	}

	protected abstract Annotation buildNullMappingAnnotation(String annotationName);

	protected boolean mappingAnnotationIsValid(String annotationName) {
		return CollectionTools.contains(this.validMappingAnnotationNames(), annotationName);
	}

	protected abstract ListIterator<String> validMappingAnnotationNames();

	protected abstract Annotation buildMappingAnnotation(String mappingAnnotationName);

	protected void addMappingAnnotation(Annotation annotation) {
		this.addItemToCollection(annotation, this.mappingAnnotations, MAPPING_ANNOTATIONS_COLLECTION);
	}

	protected void removeMappingAnnotation(Annotation annotation) {
		this.removeMappingAnnotation_(annotation);
		annotation.removeAnnotation();
	}

	protected void removeMappingAnnotation_(Annotation annotation) {
		this.removeItemFromCollection(annotation, this.mappingAnnotations, MAPPING_ANNOTATIONS_COLLECTION);
	}

	// ********** supporting annotations **********

	public Iterator<Annotation> supportingAnnotations() {
		return new CloneIterator<Annotation>(this.supportingAnnotations);
	}

	public int supportingAnnotationsSize() {
		return this.supportingAnnotations.size();
	}

	public ListIterator<NestableAnnotation> supportingAnnotations(String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.getSupportingContainerAnnotation(containerAnnotationName);
		if (containerAnnotation != null) {
			return containerAnnotation.nestedAnnotations();
		}
		NestableAnnotation nestableAnnotation = this.getSupportingNestableAnnotation(nestableAnnotationName);
		return (nestableAnnotation == null) ? EmptyListIterator.<NestableAnnotation> instance() : new SingleElementListIterator<NestableAnnotation>(nestableAnnotation);
	}

	protected NestableAnnotation getSupportingNestableAnnotation(String annotationName) {
		return (NestableAnnotation) this.getSupportingAnnotation(annotationName);
	}

	public Annotation getSupportingAnnotation(String annotationName) {
		return getAnnotation(this.supportingAnnotations(), annotationName);
	}

	public JavaResourceNode getNonNullSupportingAnnotation(String annotationName) {
		Annotation annotation = this.getSupportingAnnotation(annotationName);
		return (annotation != null) ? annotation : this.buildNullSupportingAnnotation(annotationName);
	}

	protected abstract Annotation buildNullSupportingAnnotation(String annotationName);

	public Annotation addSupportingAnnotation(String annotationName) {
		Annotation annotation = this.buildSupportingAnnotation(annotationName);
		this.supportingAnnotations.add(annotation);
		annotation.newAnnotation();
		this.fireItemAdded(SUPPORTING_ANNOTATIONS_COLLECTION, annotation);
		return annotation;
	}

	protected abstract Annotation buildSupportingAnnotation(String annotationName);

	protected void addSupportingAnnotation(Annotation annotation) {
		this.addItemToCollection(annotation, this.supportingAnnotations, SUPPORTING_ANNOTATIONS_COLLECTION);
	}

	public void removeSupportingAnnotation(String annotationName) {
		synchronized (this.supportingAnnotations) {
			this.removeSupportingAnnotation_(annotationName);
		}
	}

	protected void removeSupportingAnnotation_(String annotationName) {
		Annotation annotation = this.getSupportingAnnotation(annotationName);
		if (annotation != null) {
			this.removeSupportingAnnotation(annotation);
		}
	}

	protected void removeSupportingAnnotation(Annotation annotation) {
		this.removeSupportingAnnotation_(annotation);
		// TODO looks odd that we remove the annotation here, but in
		// addAnnotation(Annotation) we don't do the same
		annotation.removeAnnotation();
	}

	protected void removeSupportingAnnotation_(Annotation annotation) {
		this.removeItemFromCollection(annotation, this.supportingAnnotations, SUPPORTING_ANNOTATIONS_COLLECTION);
	}

	public NestableAnnotation addSupportingAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		synchronized (this.supportingAnnotations) {
			return this.addSupportingAnnotation_(index, nestableAnnotationName, containerAnnotationName);
		}
	}

	// TODO it seems we should be firing one change notification here, that a
	// new nestable annotation was added.
	protected NestableAnnotation addSupportingAnnotation_(int index, String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.getSupportingContainerAnnotation(containerAnnotationName);
		if (containerAnnotation != null) {
			// ignore any nestable annotation and just add to the container
			// annotation
			NestableAnnotation newNestedAnnotation = ContainerAnnotationTools.addNestedAnnotation(index, containerAnnotation);
			// TODO any event notification being fired for the add???
			return newNestedAnnotation;
		}

		NestableAnnotation nestedAnnotation = this.getSupportingNestableAnnotation(nestableAnnotationName);
		if (nestedAnnotation == null) {
			// add the nestable annotation since neither the nestable nor the
			// container exist
			return this.addSupportingNestableAnnotation(nestableAnnotationName);
		}

		// move the existing nestable annotation to a new container annotation
		// and add a new one
		ContainerAnnotation<NestableAnnotation> newContainerAnnotation = this.addSupportingContainerAnnotationTwoNestableAnnotations(containerAnnotationName);
		if (index == 0) {
			newContainerAnnotation.nestedAnnotationAt(1).initializeFrom(nestedAnnotation);
		} else {
			newContainerAnnotation.nestedAnnotationAt(0).initializeFrom(nestedAnnotation);
		}
		this.removeSupportingAnnotation(nestedAnnotation);
		return newContainerAnnotation.nestedAnnotationAt(index);
	}

	@SuppressWarnings("unchecked")
	protected ContainerAnnotation<NestableAnnotation> getSupportingContainerAnnotation(String annotationName) {
		return (ContainerAnnotation<NestableAnnotation>) this.getSupportingAnnotation(annotationName);
	}

	protected NestableAnnotation addSupportingNestableAnnotation(String annotationName) {
		return (NestableAnnotation) this.addSupportingAnnotation(annotationName);
	}

	protected ContainerAnnotation<NestableAnnotation> addSupportingContainerAnnotationTwoNestableAnnotations(String annotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.buildContainerAnnotation(annotationName);
		this.supportingAnnotations.add(containerAnnotation);
		containerAnnotation.newAnnotation();
		containerAnnotation.addInternal(0).newAnnotation();
		containerAnnotation.addInternal(1).newAnnotation();
		return containerAnnotation;
	}

	@SuppressWarnings("unchecked")
	protected ContainerAnnotation<NestableAnnotation> buildContainerAnnotation(String annotationName) {
		return (ContainerAnnotation<NestableAnnotation>) this.buildSupportingAnnotation(annotationName);
	}

	protected boolean supportingAnnotationIsValid(String annotationName) {
		return CollectionTools.contains(this.validSupportingAnnotationNames(), annotationName);
	}

	protected abstract ListIterator<String> validSupportingAnnotationNames();

	public void removeSupportingAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		synchronized (this.supportingAnnotations) {
			this.removeSupportingAnnotation_(index, nestableAnnotationName, containerAnnotationName);
		}
	}

	protected void removeSupportingAnnotation_(int index, String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.getSupportingContainerAnnotation(containerAnnotationName);
		if (containerAnnotation == null) {
			Annotation annotation = this.getSupportingAnnotation(nestableAnnotationName);
			this.removeSupportingAnnotation(annotation);
		} else {
			this.removeSupportingAnnotation(index, containerAnnotation);
		}
	}

	protected void removeSupportingAnnotation(int index, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		NestableAnnotation nestableAnnotation = containerAnnotation.nestedAnnotationAt(index);
		containerAnnotation.remove(index);
		// TODO move these 2 lines to the ContainerAnnotation implementation, i think
		nestableAnnotation.removeAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, containerAnnotation);

		if (containerAnnotation.nestedAnnotationsSize() == 0) {
			this.removeSupportingAnnotation(containerAnnotation);
		} else if (containerAnnotation.nestedAnnotationsSize() == 1) {
			NestableAnnotation nestedAnnotation = containerAnnotation.nestedAnnotationAt(0);

			this.supportingAnnotations.remove(containerAnnotation);
			containerAnnotation.removeAnnotation();

			NestableAnnotation newAnnotation = (NestableAnnotation) this.buildSupportingAnnotation(containerAnnotation.getNestableAnnotationName());
			this.supportingAnnotations.add(newAnnotation);
			newAnnotation.newAnnotation();

			newAnnotation.initializeFrom(nestedAnnotation);

			this.fireItemAdded(SUPPORTING_ANNOTATIONS_COLLECTION, newAnnotation);
			this.fireItemRemoved(SUPPORTING_ANNOTATIONS_COLLECTION, containerAnnotation);
		}
	}

	public void moveSupportingAnnotation(int targetIndex, int sourceIndex, String containerAnnotationName) {
		this.moveAnnotation(targetIndex, sourceIndex, this.getSupportingContainerAnnotation(containerAnnotationName));
	}

	protected void moveAnnotation(int targetIndex, int sourceIndex, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		containerAnnotation.move(targetIndex, sourceIndex);
		ContainerAnnotationTools.synchAnnotationsAfterMove(targetIndex, sourceIndex, containerAnnotation);
	}

	// ********** simple state **********

	public boolean isPersistable() {
		return this.persistable;
	}

	protected void setPersistable(boolean persistable) {
		boolean old = this.persistable;
		this.persistable = persistable;
		this.firePropertyChanged(PERSISTABLE_PROPERTY, old, persistable);
	}

	protected boolean buildPersistable(CompilationUnit astRoot) {
		return this.getMember().isPersistable(astRoot);
	}

	public boolean isPersisted() {
		return this.getMappingAnnotation() != null;
	}

	public boolean isFor(String memberName, int occurrence) {
		return this.member.matches(memberName, occurrence);
	}

	public boolean isFor(MethodSignature methodSignature, int occurrence) {
		return false;
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		return this.fullTextRange(astRoot);
	}

	protected TextRange fullTextRange(CompilationUnit astRoot) {
		return getTextRange(this.getMember().getBodyDeclaration(astRoot));
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getMember().getNameTextRange(astRoot);
	}

	// ********** update **********

	public void update(CompilationUnit astRoot) {
		this.updateAnnotations(astRoot);
		this.setPersistable(this.buildPersistable(astRoot));
	}

	protected void updateAnnotations(CompilationUnit astRoot) {
		synchronized (this.mappingAnnotations) {
		synchronized (this.supportingAnnotations) {
			this.updateAnnotations_(astRoot);
		}
		}
	}

	protected void updateAnnotations_(CompilationUnit astRoot) {
		HashSet<Annotation> mappingAnnotationsToRemove = new HashSet<Annotation>(this.mappingAnnotations);
		HashSet<Annotation> supportingAnnotationsToRemove = new HashSet<Annotation>(this.supportingAnnotations);

		this.getMember().getBodyDeclaration(astRoot).accept(this.buildUpdateAnnotationVisitor(astRoot, mappingAnnotationsToRemove, supportingAnnotationsToRemove));

		for (Annotation annotation : mappingAnnotationsToRemove) {
			this.removeMappingAnnotation_(annotation);
		}
		for (Annotation annotation : supportingAnnotationsToRemove) {
			this.removeSupportingAnnotation_(annotation);
		}
	}

	protected ASTVisitor buildUpdateAnnotationVisitor(CompilationUnit astRoot, Set<Annotation> mappingAnnotationsToRemove, Set<Annotation> supportingAnnotationsToRemove) {
		return new UpdateAnnotationVisitor(astRoot, this.getMember().getBodyDeclaration(astRoot), mappingAnnotationsToRemove, supportingAnnotationsToRemove);
	}

	protected void addOrUpdateAnnotation(org.eclipse.jdt.core.dom.Annotation node, CompilationUnit astRoot, Set<Annotation> mappingAnnotationsToRemove, Set<Annotation> supportingAnnotationsToRemove) {
		String jdtAnnotationName = JDTTools.resolveAnnotation(node);
		if (jdtAnnotationName == null) {
			return;
		}
		if (this.supportingAnnotationIsValid(jdtAnnotationName)) {
			this.addOrUpdateSupportingAnnotation(jdtAnnotationName, astRoot, supportingAnnotationsToRemove);
			return;
		}
		if (this.mappingAnnotationIsValid(jdtAnnotationName)) {
			this.addOrUpdateMappingAnnotation(jdtAnnotationName, astRoot, mappingAnnotationsToRemove);
			return;
		}
	}

	protected void addOrUpdateSupportingAnnotation(String jdtAnnotationName, CompilationUnit astRoot, Set<Annotation> supportingAnnotationsToRemove) {
		Annotation annotation = getAnnotation(supportingAnnotationsToRemove, jdtAnnotationName);
		if (annotation != null) {
			annotation.update(astRoot);
			supportingAnnotationsToRemove.remove(annotation);
		} else {
			annotation = this.buildSupportingAnnotation(jdtAnnotationName);
			annotation.initialize(astRoot);
			this.addSupportingAnnotation(annotation);
		}
	}

	protected void addOrUpdateMappingAnnotation(String jdtAnnotationName, CompilationUnit astRoot, Set<Annotation> mappingAnnotationsToRemove) {
		Annotation annotation = getAnnotation(mappingAnnotationsToRemove, jdtAnnotationName);
		if (annotation != null) {
			annotation.update(astRoot);
			mappingAnnotationsToRemove.remove(annotation);
		} else {
			annotation = this.buildMappingAnnotation(jdtAnnotationName);
			annotation.initialize(astRoot);
			this.addMappingAnnotation(annotation);
		}
	}

	// ********** miscellaneous **********

	public void resolveTypes(CompilationUnit astRoot) {
		this.setPersistable(this.buildPersistable(astRoot));
	}

	protected E getMember() {
		return this.member;
	}

	protected static Annotation getAnnotation(Iterable<Annotation> annotations, String annotationName) {
		return getAnnotation(annotations.iterator(), annotationName);
	}

	protected static Annotation getAnnotation(Iterator<Annotation> annotations, String annotationName) {
		while (annotations.hasNext()) {
			Annotation annotation = annotations.next();
			if (annotation.getAnnotationName().equals(annotationName)) {
				return annotation;
			}
		}
		return null;
	}

	protected static TextRange getTextRange(ASTNode astNode) {
		return (astNode == null) ? null : new ASTNodeTextRange(astNode);
	}

	protected static <T extends JavaResourcePersistentMember> Iterator<T> persistableMembers(Iterator<T> members) {
		return new FilteringIterator<T, T>(members) {
			@Override
			protected boolean accept(T member) {
				return member.isPersistable();
			}
		};
	}

	// ********** AST visitor **********

	/**
	 * annotation visitor
	 */
	protected static abstract class AnnotationVisitor extends ASTVisitor {
		private final CompilationUnit astRoot;
		private final BodyDeclaration bodyDeclaration;

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

		protected CompilationUnit getASTRoot() {
			return this.astRoot;
		}

		protected abstract void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation node);

	}

	/**
	 * initial annotation visitor
	 */
	protected class InitialAnnotationVisitor extends AnnotationVisitor {

		protected InitialAnnotationVisitor(CompilationUnit astRoot, BodyDeclaration bodyDeclaration) {
			super(astRoot, bodyDeclaration);
		}

		@Override
		protected void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation node) {
			AbstractJavaResourcePersistentMember.this.addInitialAnnotation(node, this.getASTRoot());
		}

	}

	/**
	 * update annotation visitor
	 */
	protected class UpdateAnnotationVisitor extends AnnotationVisitor {
		protected final Set<Annotation> mappingAnnotationsToRemove;
		protected final Set<Annotation> supportingAnnotationsToRemove;

		protected UpdateAnnotationVisitor(CompilationUnit astRoot, BodyDeclaration bodyDeclaration, Set<Annotation> mappingAnnotationsToRemove, Set<Annotation> supportingAnnotationsToRemove) {
			super(astRoot, bodyDeclaration);
			this.mappingAnnotationsToRemove = mappingAnnotationsToRemove;
			this.supportingAnnotationsToRemove = supportingAnnotationsToRemove;
		}

		@Override
		protected void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation node) {
			AbstractJavaResourcePersistentMember.this.addOrUpdateAnnotation(node, this.getASTRoot(), this.mappingAnnotationsToRemove, this.supportingAnnotationsToRemove);
		}

	}

}
