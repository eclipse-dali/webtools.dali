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
import java.util.Collection;
import java.util.Collections;
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
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.CloneIterable;
import org.eclipse.jpt.utility.internal.iterables.FixedCloneIterable;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

/**
 * Java source persistent member (annotations, "persistable")
 */
abstract class SourcePersistentMember<E extends Member>
	extends SourceNode
	implements JavaResourcePersistentMember
{
	final E member;

	/**
	 * mapping annotations; no duplicates (java compiler has an error for
	 * duplicates)
	 */
	final Vector<Annotation> mappingAnnotations = new Vector<Annotation>();

	/**
	 * supporting annotations; no duplicates (java compiler has an error for
	 * duplicates)
	 */
	final Vector<Annotation> supportingAnnotations = new Vector<Annotation>();

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
		if (this.annotationIsValidSupportingAnnotation(jdtAnnotationName)) {
			if (this.selectAnnotationNamed(this.supportingAnnotations, jdtAnnotationName) == null) { // ignore duplicates
				Annotation annotation = this.buildSupportingAnnotation(jdtAnnotationName);
				annotation.initialize(astRoot);
				this.supportingAnnotations.add(annotation);
			}
		} else if (this.annotationIsValidMappingAnnotation(jdtAnnotationName)) {
			if (this.selectAnnotationNamed(this.mappingAnnotations, jdtAnnotationName) == null) { // ignore duplicates
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

	private Iterable<Annotation> getMappingAnnotations() {
		return new CloneIterable<Annotation>(this.mappingAnnotations);
	}

	public int mappingAnnotationsSize() {
		return this.mappingAnnotations.size();
	}

	// TODO need property change notification on this mappingAnnotation changing
	// from the context model we don't really care if their are multiple mapping
	// annotations, just which one we need to use
	public Annotation getMappingAnnotation() {
		Iterable<Annotation> annotations = new FixedCloneIterable<Annotation>(this.mappingAnnotations);
		for (ListIterator<String> stream = this.validMappingAnnotationNames(); stream.hasNext();) {
			Annotation annotation = this.selectAnnotationNamed(annotations, stream.next());
			if (annotation != null) {
				return annotation;
			}
		}
		return null;
	}

	public Annotation getMappingAnnotation(String annotationName) {
		return this.selectAnnotationNamed(this.getMappingAnnotations(), annotationName);
	}

	/**
	 * Remove all other *mapping* annotations that exist; not just the one
	 * returned by #getMappingAnnotation(). #getMappingAnnotation() returns the
	 * first mapping annotation found in the source. If there were multiple
	 * mapping annotations (which is a validation error) then calling this
	 * method would not work because the new mapping annotation would be added
	 * to the end of the list of annotations.
	 */
	public Annotation setMappingAnnotation(String annotationName) {
		Collection<Annotation> removedAnnotations = null;
		Annotation newMapping = null;
		if (annotationName == null) {
			removedAnnotations = this.removeMappingAnnotations();
		} else {
			if (this.selectAnnotationNamed(this.mappingAnnotations, annotationName) != null) {
				throw new IllegalStateException("duplicate mapping annotation: " + annotationName); //$NON-NLS-1$
			}
	
			removedAnnotations = this.removeMappingAnnotations();

			newMapping = this.buildMappingAnnotation(annotationName);
			this.mappingAnnotations.add(newMapping);
			newMapping.newAnnotation();
			this.fireItemAdded(MAPPING_ANNOTATIONS_COLLECTION, newMapping);
		}
		// hold change notifications until the end so a project update does not
		// occur before we are finished removing the old mapping(s) and/or adding
		// the new mapping
		this.fireItemsRemoved(MAPPING_ANNOTATIONS_COLLECTION, removedAnnotations);
		return newMapping;
	}

	/**
	 * Remove all mapping annotations that already exist. No change notification
	 * is fired. Return the removed annotations.
	 */
	private Collection<Annotation> removeMappingAnnotations() {
		ArrayList<Annotation> removedAnnotations = null;
		for (String mappingAnnotationName : CollectionTools.iterable(this.validMappingAnnotationNames())) {
			Annotation mappingAnnotation = this.selectAnnotationNamed(this.mappingAnnotations, mappingAnnotationName);
			if (mappingAnnotation != null) {
				// only need to delete one, since we do not hold duplicates
				this.mappingAnnotations.remove(mappingAnnotation);
				mappingAnnotation.removeAnnotation();
				if (removedAnnotations == null) {
					removedAnnotations = new ArrayList<Annotation>();
				}
				removedAnnotations.add(mappingAnnotation);
			}
		}
		return (removedAnnotations != null) ? removedAnnotations : Collections.<Annotation>emptySet();
	}

	abstract Annotation buildMappingAnnotation(String mappingAnnotationName);

	private boolean annotationIsValidMappingAnnotation(String annotationName) {
		return CollectionTools.contains(this.validMappingAnnotationNames(), annotationName);
	}

	abstract ListIterator<String> validMappingAnnotationNames();


	// ********** supporting annotations **********

	public Iterator<Annotation> supportingAnnotations() {
		return new CloneIterator<Annotation>(this.supportingAnnotations);
	}

	private Iterable<Annotation> getSupportingAnnotations() {
		return new CloneIterable<Annotation>(this.supportingAnnotations);
	}

	public int supportingAnnotationsSize() {
		return this.supportingAnnotations.size();
	}

	public Annotation getSupportingAnnotation(String annotationName) {
		return this.selectAnnotationNamed(this.getSupportingAnnotations(), annotationName);
	}

	public Annotation getNonNullSupportingAnnotation(String annotationName) {
		Annotation annotation = this.getSupportingAnnotation(annotationName);
		return (annotation != null) ? annotation : this.buildNullSupportingAnnotation(annotationName);
	}

	abstract Annotation buildNullSupportingAnnotation(String annotationName);

	public Annotation addSupportingAnnotation(String annotationName) {
		Annotation annotation = this.buildSupportingAnnotation(annotationName);
		this.supportingAnnotations.add(annotation);
		annotation.newAnnotation();
		this.fireItemAdded(SUPPORTING_ANNOTATIONS_COLLECTION, annotation);
		return annotation;
	}

	abstract Annotation buildSupportingAnnotation(String annotationName);

	public void removeSupportingAnnotation(String annotationName) {
		Annotation annotation = this.getSupportingAnnotation(annotationName);
		if (annotation != null) {
			this.removeSupportingAnnotation(annotation);
		}
	}

	private void removeSupportingAnnotation(Annotation annotation) {
		this.supportingAnnotations.remove(annotation);
		annotation.removeAnnotation();
		this.fireItemRemoved(SUPPORTING_ANNOTATIONS_COLLECTION, annotation);
	}

	private boolean annotationIsValidSupportingAnnotation(String annotationName) {
		return CollectionTools.contains(this.validSupportingAnnotationNames(), annotationName);
	}

	abstract ListIterator<String> validSupportingAnnotationNames();


	// ********** supporting "combo" annotations **********

	public ListIterator<NestableAnnotation> supportingAnnotations(String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.getSupportingContainerAnnotation(containerAnnotationName);
		if (containerAnnotation != null) {
			return containerAnnotation.nestedAnnotations();
		}

		NestableAnnotation nestableAnnotation = this.getSupportingNestableAnnotation(nestableAnnotationName);
		if (nestableAnnotation != null) {
			return new SingleElementListIterator<NestableAnnotation>(nestableAnnotation);
		}

		return EmptyListIterator.instance();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private ContainerAnnotation<NestableAnnotation> getSupportingContainerAnnotation(String annotationName) {
		return (ContainerAnnotation<NestableAnnotation>) this.getSupportingAnnotation(annotationName);
	}

	private NestableAnnotation getSupportingNestableAnnotation(String annotationName) {
		return (NestableAnnotation) this.getSupportingAnnotation(annotationName);
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
	public NestableAnnotation addSupportingAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.getSupportingContainerAnnotation(containerAnnotationName);
		if (containerAnnotation != null) {
			// ignore any stand-alone nestable annotations and just add to the container annotation
			return AnnotationContainerTools.addNestedAnnotation(index, containerAnnotation);
		}

		NestableAnnotation standAloneAnnotation = this.getSupportingNestableAnnotation(nestableAnnotationName);
		if (standAloneAnnotation == null) {
			// add a stand-alone nestable annotation since neither the nestable nor the container exist
			return (NestableAnnotation) this.addSupportingAnnotation(nestableAnnotationName);
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
	private NestableAnnotation addSecondNestedAnnotation(int index, String containerAnnotationName, NestableAnnotation standAloneAnnotation) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.buildSupportingContainerAnnotation(containerAnnotationName);
		this.supportingAnnotations.add(containerAnnotation);
		containerAnnotation.newAnnotation();

		NestableAnnotation nestedAnnotation0 = containerAnnotation.addNestedAnnotationInternal();
		nestedAnnotation0.newAnnotation();

		NestableAnnotation nestedAnnotation1 = containerAnnotation.addNestedAnnotationInternal();
		nestedAnnotation1.newAnnotation();

		this.removeSupportingAnnotation(standAloneAnnotation);

		this.fireItemAdded(SUPPORTING_ANNOTATIONS_COLLECTION, containerAnnotation);

		if (index == 0) {
			// adding new annotation at 0, so stand-alone is "copied" to slot 1
			nestedAnnotation1.initializeFrom(standAloneAnnotation);
		} else {
			// adding new annotation at 1, so stand-alone is "copied" to slot 0
			nestedAnnotation0.initializeFrom(standAloneAnnotation);
		}

		return (index == 0) ? nestedAnnotation0 : nestedAnnotation1;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private ContainerAnnotation<NestableAnnotation> buildSupportingContainerAnnotation(String annotationName) {
		return (ContainerAnnotation<NestableAnnotation>) this.buildSupportingAnnotation(annotationName);
	}

	public void moveSupportingAnnotation(int targetIndex, int sourceIndex, String containerAnnotationName) {
		this.moveAnnotation(targetIndex, sourceIndex, this.getSupportingContainerAnnotation(containerAnnotationName));
	}

	private void moveAnnotation(int targetIndex, int sourceIndex, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		AnnotationContainerTools.moveNestedAnnotation(targetIndex, sourceIndex, containerAnnotation);
	}

	public void removeSupportingAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.getSupportingContainerAnnotation(containerAnnotationName);
		if (containerAnnotation == null) {  // assume the index is 0
			this.removeSupportingAnnotation(this.getSupportingAnnotation(nestableAnnotationName));
		} else {
			this.removeSupportingAnnotation(index, containerAnnotation);
		}
	}

	/**
	 * after we remove the nested annotation, check to see whether we need to
	 * either remove the container (if it is empty) or convert the last nested
	 * annotation to a stand-alone annotation
	 */
	private void removeSupportingAnnotation(int index, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		AnnotationContainerTools.removeNestedAnnotation(index, containerAnnotation);
		switch (containerAnnotation.nestedAnnotationsSize()) {
			case 0:
				this.removeSupportingAnnotation(containerAnnotation);
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
	private void convertLastNestedAnnotation(ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		NestableAnnotation lastNestedAnnotation = containerAnnotation.nestedAnnotations().next();
		this.supportingAnnotations.remove(containerAnnotation);
		containerAnnotation.removeAnnotation();

		NestableAnnotation standAloneAnnotation = (NestableAnnotation) this.buildSupportingAnnotation(lastNestedAnnotation.getAnnotationName());
		this.supportingAnnotations.add(standAloneAnnotation);
		standAloneAnnotation.newAnnotation();
		this.fireItemRemoved(SUPPORTING_ANNOTATIONS_COLLECTION, containerAnnotation);
		this.fireItemAdded(SUPPORTING_ANNOTATIONS_COLLECTION, standAloneAnnotation);
		standAloneAnnotation.initializeFrom(lastNestedAnnotation);
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

	public boolean isPersisted() {
		return this.getMappingAnnotation() != null;
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
		HashSet<Annotation> mappingAnnotationsToRemove = new HashSet<Annotation>(this.mappingAnnotations);
		HashSet<Annotation> supportingAnnotationsToRemove = new HashSet<Annotation>(this.supportingAnnotations);

		this.member.getBodyDeclaration(astRoot).accept(this.buildUpdateAnnotationVisitor(astRoot, mappingAnnotationsToRemove, supportingAnnotationsToRemove));

		for (Annotation annotation : mappingAnnotationsToRemove) {
			this.removeItemFromCollection(annotation, this.mappingAnnotations, MAPPING_ANNOTATIONS_COLLECTION);
		}
		for (Annotation annotation : supportingAnnotationsToRemove) {
			this.removeItemFromCollection(annotation, this.supportingAnnotations, SUPPORTING_ANNOTATIONS_COLLECTION);
		}
	}

	private ASTVisitor buildUpdateAnnotationVisitor(CompilationUnit astRoot, Set<Annotation> mappingAnnotationsToRemove, Set<Annotation> supportingAnnotationsToRemove) {
		return new UpdateAnnotationVisitor(astRoot, this.member.getBodyDeclaration(astRoot), mappingAnnotationsToRemove, supportingAnnotationsToRemove);
	}

	void addOrUpdateAnnotation(org.eclipse.jdt.core.dom.Annotation node, CompilationUnit astRoot, Set<Annotation> mappingAnnotationsToRemove, Set<Annotation> supportingAnnotationsToRemove) {
		String jdtAnnotationName = JDTTools.resolveAnnotation(node);
		if (jdtAnnotationName == null) {
			return;
		}
		if (this.annotationIsValidSupportingAnnotation(jdtAnnotationName)) {
			this.addOrUpdateSupportingAnnotation(jdtAnnotationName, astRoot, supportingAnnotationsToRemove);
			return;
		}
		if (this.annotationIsValidMappingAnnotation(jdtAnnotationName)) {
			this.addOrUpdateMappingAnnotation(jdtAnnotationName, astRoot, mappingAnnotationsToRemove);
			return;
		}
	}

	private void addOrUpdateSupportingAnnotation(String jdtAnnotationName, CompilationUnit astRoot, Set<Annotation> supportingAnnotationsToRemove) {
		Annotation annotation = this.selectAnnotationNamed(supportingAnnotationsToRemove, jdtAnnotationName);
		if (annotation != null) {
			annotation.update(astRoot);
			supportingAnnotationsToRemove.remove(annotation);
		} else {
			annotation = this.buildSupportingAnnotation(jdtAnnotationName);
			annotation.initialize(astRoot);
			this.addItemToCollection(annotation, this.supportingAnnotations, SUPPORTING_ANNOTATIONS_COLLECTION);
		}
	}

	private void addOrUpdateMappingAnnotation(String jdtAnnotationName, CompilationUnit astRoot, Set<Annotation> mappingAnnotationsToRemove) {
		Annotation annotation = this.selectAnnotationNamed(mappingAnnotationsToRemove, jdtAnnotationName);
		if (annotation != null) {
			annotation.update(astRoot);
			mappingAnnotationsToRemove.remove(annotation);
		} else {
			annotation = this.buildMappingAnnotation(jdtAnnotationName);
			annotation.initialize(astRoot);
			this.addItemToCollection(annotation, this.mappingAnnotations, MAPPING_ANNOTATIONS_COLLECTION);
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
	protected static abstract class AnnotationVisitor extends ASTVisitor {
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
	protected class InitialAnnotationVisitor extends AnnotationVisitor {

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
			SourcePersistentMember.this.addOrUpdateAnnotation(node, this.astRoot, this.mappingAnnotationsToRemove, this.supportingAnnotationsToRemove);
		}

	}

}
