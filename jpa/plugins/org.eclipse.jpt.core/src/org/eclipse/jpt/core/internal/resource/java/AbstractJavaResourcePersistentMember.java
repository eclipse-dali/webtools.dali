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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

public abstract class AbstractJavaResourcePersistentMember<E extends Member>
	extends AbstractJavaResourceNode
	implements JavaResourcePersistentMember
{	
	private final E member;

	/**
	 * stores all annotations(non-mapping) except duplicates, java compiler has an error for duplicates
	 */
	private final Collection<Annotation> annotations;
	
	/**
	 * stores all mapping annotations except duplicates, java compiler has an error for duplicates
	 */
	private final Collection<Annotation> mappingAnnotations;
	
	private boolean persistable;

	AbstractJavaResourcePersistentMember(JavaResourceNode parent, E member){
		super(parent);
		this.member = member;
		this.annotations = new ArrayList<Annotation>();
		this.mappingAnnotations = new ArrayList<Annotation>();
	}

	public void initialize(CompilationUnit astRoot) {
		getMember().bodyDeclaration(astRoot).accept(initializeAnnotationVisitor(astRoot));
		this.persistable = calculatePersistability(astRoot);		
	}
	
	protected ASTVisitor initializeAnnotationVisitor(final CompilationUnit astRoot) {
		return new ASTVisitor() {
			@Override
			public boolean visit(SingleMemberAnnotation node) {
				return visit((org.eclipse.jdt.core.dom.Annotation) node);
			}
		
			@Override
			public boolean visit(NormalAnnotation node) {
				return visit((org.eclipse.jdt.core.dom.Annotation) node);
			}
		
			@Override
			public boolean visit(MarkerAnnotation node) {
				return visit((org.eclipse.jdt.core.dom.Annotation) node);
			}
			
			private boolean visit(org.eclipse.jdt.core.dom.Annotation node) {
				if (node.getParent() != getMember().bodyDeclaration(astRoot)) {
					//we don't want to look at annotations for child members, only this member
					return false;
				}
				addInitialAnnotation(node, astRoot);
				return false;
			}
		};
	}
	
	protected void addInitialAnnotation(org.eclipse.jdt.core.dom.Annotation node, CompilationUnit astRoot) {
		String qualifiedAnnotationName = JDTTools.resolveAnnotation(node);
		if (qualifiedAnnotationName == null) {
			return;
		}
		if (isPossibleAnnotation(qualifiedAnnotationName)) {
			if (annotation(qualifiedAnnotationName) == null) { //don't want duplicates
				Annotation annotation = buildAnnotation(qualifiedAnnotationName);
				annotation.initialize(astRoot);
				this.annotations.add(annotation);
			}
		}
		else if (isPossibleMappingAnnotation(qualifiedAnnotationName)) {
			if (mappingAnnotation(qualifiedAnnotationName) == null) { //don't want duplicates
				Annotation annotation = buildMappingAnnotation(qualifiedAnnotationName);
				annotation.initialize(astRoot);
				this.mappingAnnotations.add(annotation);
			}
		}
	}

	public E getMember() {
		return this.member;
	}
	
	protected abstract Annotation buildAnnotation(String annotationName);
	
	protected abstract Annotation buildNullAnnotation(String annotationName);

	protected abstract Annotation buildMappingAnnotation(String mappingAnnotationName);
	
	protected abstract Annotation buildNullMappingAnnotation(String annotationName);

	protected abstract ListIterator<String> possibleMappingAnnotationNames();

	protected abstract boolean isPossibleAnnotation(String annotationName);
	
	protected abstract boolean isPossibleMappingAnnotation(String annotationName);
	
	protected abstract boolean calculatePersistability(CompilationUnit astRoot);

	public Annotation annotation(String annotationName) {
		for (Iterator<Annotation> i = annotations(); i.hasNext(); ) {
			Annotation annotation = i.next();
			if (annotation.getAnnotationName().equals(annotationName)) {
				return annotation;
			}
		}
		return null;
	}
	
	public JavaResourceNode nonNullAnnotation(String annotationName) {
		Annotation annotation = annotation(annotationName);
		if (annotation == null) {
			return buildNullAnnotation(annotationName);	
		}
		return annotation;
	}
	
	public Annotation mappingAnnotation(String annotationName) {
		for (Iterator<Annotation> i = mappingAnnotations(); i.hasNext(); ) {
			Annotation mappingAnnotation = i.next();
			if (mappingAnnotation.getAnnotationName().equals(annotationName)) {
				return mappingAnnotation;
			}
		}
		return null;
	}

	public JavaResourceNode nullMappingAnnotation(String annotationName) {
		return buildNullMappingAnnotation(annotationName);	
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<Annotation> annotations() {
		return new CloneIterator<Annotation>(this.annotations);
	}
	
	public int annotationsSize() {
		return this.annotations.size();
	}
	
	public Annotation addAnnotation(String annotationName) {
		Annotation annotation = buildAnnotation(annotationName);
		this.annotations.add(annotation);
		annotation.newAnnotation();
		this.fireItemAdded(ANNOTATIONS_COLLECTION, annotation);
		return annotation;
	}

	@SuppressWarnings("unchecked")
	protected ContainerAnnotation<NestableAnnotation> addContainerAnnotation(String containerAnnotationName) {
		return (ContainerAnnotation<NestableAnnotation>) addAnnotation(containerAnnotationName);
	}
	
	protected ContainerAnnotation<NestableAnnotation> addContainerAnnotationTwoNestableAnnotations(String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = buildContainerAnnotation(containerAnnotationName);
		this.annotations.add(containerAnnotation);
		containerAnnotation.newAnnotation();
		containerAnnotation.addInternal(0).newAnnotation();
		containerAnnotation.addInternal(1).newAnnotation();
		return containerAnnotation;
	}
		
	@SuppressWarnings("unchecked")
	protected ContainerAnnotation<NestableAnnotation>  buildContainerAnnotation(String containerAnnotationName) {
		return (ContainerAnnotation<NestableAnnotation>) buildAnnotation(containerAnnotationName);
	}
	
	@SuppressWarnings("unchecked")
	protected ContainerAnnotation<NestableAnnotation> containerAnnotation(String containerAnnotationName) {
		return (ContainerAnnotation<NestableAnnotation>) annotation(containerAnnotationName);
	}
	
	protected NestableAnnotation nestableAnnotation(String nestableAnnotationName) {
		return (NestableAnnotation) annotation(nestableAnnotationName);
	}
	
	protected NestableAnnotation addNestableAnnotation(String nestableAnnotationName) {
		return (NestableAnnotation) addAnnotation(nestableAnnotationName);
	}
	
	//TODO it seems we should be firing one change notification here, that a new nestable annotation was added.
	public NestableAnnotation addAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		NestableAnnotation nestedAnnotation = (NestableAnnotation) annotation(nestableAnnotationName);
		
		ContainerAnnotation<NestableAnnotation> containerAnnotation = containerAnnotation(containerAnnotationName);
		
		if (containerAnnotation != null) {
			//ignore any nestableAnnotation and just add to the plural one
			NestableAnnotation newNestedAnnotation = ContainerAnnotationTools.addNestedAnnotation(index, containerAnnotation);
			//TODO any event notification being fired for the add???
			return newNestedAnnotation;
		}
		if (nestedAnnotation == null) {
			//add the nestable since neither nestable or container exists
			return addNestableAnnotation(nestableAnnotationName);
		}
		//move the nestable to a new container annotation and add to it
		ContainerAnnotation<NestableAnnotation> newContainerAnnotation = addContainerAnnotationTwoNestableAnnotations(containerAnnotationName);
		if (index == 0) {
			newContainerAnnotation.nestedAnnotationAt(1).initializeFrom(nestedAnnotation);
		}
		else {
			newContainerAnnotation.nestedAnnotationAt(0).initializeFrom(nestedAnnotation);		
		}
		removeAnnotation(nestedAnnotation);
		return newContainerAnnotation.nestedAnnotationAt(index);
	}
	
	public void move(int targetIndex, int sourceIndex, String containerAnnotationName) {
		move(targetIndex, sourceIndex, containerAnnotation(containerAnnotationName));
	}
	
	protected void move(int targetIndex, int sourceIndex, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		containerAnnotation.move(targetIndex, sourceIndex);
		ContainerAnnotationTools.synchAnnotationsAfterMove(targetIndex, sourceIndex, containerAnnotation);
	}
	
	protected void addAnnotation(Annotation annotation) {
		addItemToCollection(annotation, this.annotations, ANNOTATIONS_COLLECTION);
	}
	
	protected void removeAnnotation(Annotation annotation) {
		removeAnnotation_(annotation);
		//TODO looks odd that we remove the annotation here, but in addAnnotation(Annotation) we don't do the same
		annotation.removeAnnotation();
	}
	
	protected void removeAnnotation_(Annotation annotation) {
		removeItemFromCollection(annotation, this.annotations, ANNOTATIONS_COLLECTION);
	}
	
	protected void addMappingAnnotation(String mappingAnnotationName) {
		if (mappingAnnotation(mappingAnnotationName) != null) {
			return;
		}
		Annotation mappingAnnotation = buildMappingAnnotation(mappingAnnotationName);
		addMappingAnnotation(mappingAnnotation);
		//TODO should this be done here or should creating the Annotation do this??
		mappingAnnotation.newAnnotation();
	}	

	protected void addMappingAnnotation(Annotation annotation) {
		addItemToCollection(annotation, this.mappingAnnotations, MAPPING_ANNOTATIONS_COLLECTION);
	}
	
	protected void removeMappingAnnotation(Annotation annotation) {
		removeMappingAnnotation_(annotation);
		annotation.removeAnnotation();
	}
	
	protected void removeMappingAnnotation_(Annotation annotation) {
		removeItemFromCollection(annotation, this.mappingAnnotations, MAPPING_ANNOTATIONS_COLLECTION);
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<Annotation> mappingAnnotations() {
		return new CloneIterator<Annotation>(this.mappingAnnotations);
	}

	public int mappingAnnotationsSize() {
		return this.mappingAnnotations.size();
	}
	
	public void removeAnnotation(String annotationName) {
		Annotation annotation = annotation(annotationName);
		if (annotation != null) {
			removeAnnotation(annotation);
		}
	}
	
	public void removeAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = containerAnnotation(containerAnnotationName);
		if (containerAnnotation == null) {
			Annotation annotation = annotation(nestableAnnotationName);
			removeAnnotation(annotation);
		}
		else {
			removeAnnotation(index, containerAnnotation);
		}
	}
	
	protected void removeAnnotation(int index, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		NestableAnnotation nestableAnnotation = containerAnnotation.nestedAnnotationAt(index);
		containerAnnotation.remove(index);
		//TODO move these 2 lines to the ContainerAnnotation implementation, i think
		nestableAnnotation.removeAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, containerAnnotation);
		
		if (containerAnnotation.nestedAnnotationsSize() == 0) {
			removeAnnotation(containerAnnotation);
		}
		else if (containerAnnotation.nestedAnnotationsSize() == 1) {
			NestableAnnotation nestedAnnotation = containerAnnotation.nestedAnnotationAt(0);
			
			this.annotations.remove(containerAnnotation);
			containerAnnotation.removeAnnotation();


			NestableAnnotation newAnnotation = (NestableAnnotation) buildAnnotation(containerAnnotation.getNestableAnnotationName());
			this.annotations.add(newAnnotation);
			newAnnotation.newAnnotation();

			newAnnotation.initializeFrom(nestedAnnotation);
			
			this.fireItemAdded(ANNOTATIONS_COLLECTION, newAnnotation);
			fireItemRemoved(ANNOTATIONS_COLLECTION, containerAnnotation);
		}
	}
	// removes all other *mapping* annotations that exist, not just the one returned by mappingAnnotation()
	// mappingAnnotation() returns the first mapping annotation found in the source.  if there are multiple
	// mapping annotations (which is a validation error condition) then calling this api would not work
	// because the new mapping annotatio would be added to the end of the list of annotations.
	public void setMappingAnnotation(String annotationName) {
		if (mappingAnnotation(annotationName) != null) {
			throw new IllegalStateException("The mapping annotation named " + annotationName + " already exists.");
		}
		Annotation oldMapping = mappingAnnotation();
		Annotation newMapping = null;
		if (oldMapping != null) {
			removeUnnecessaryAnnotations(annotationName);
		}
		if (annotationName != null) {
			if (mappingAnnotation(annotationName) != null) {
				return;
			}
			newMapping = buildMappingAnnotation(annotationName);
			this.mappingAnnotations.add(newMapping);
			newMapping.newAnnotation();
		}
		//have to hold property change notification until the end so a project update does not occur
		//before we are finished removing the old mapping and adding the new mapping
		//just firing collectio changed since one or more removes and one add was completed.
		//if we ever listen for specific events instead of just doing a mass update, we might need to make this more specific
		fireCollectionChanged(MAPPING_ANNOTATIONS_COLLECTION);
	}
	/**
	 * Remove all mapping annotations that already exist.
	 * No change notification fired.
	 */
	protected void removeUnnecessaryAnnotations(String newMappingAnnotationName) {	
		for (ListIterator<String> i = possibleMappingAnnotationNames(); i.hasNext(); ) {
			String mappingAnnotationName = i.next();
			if (mappingAnnotationName != newMappingAnnotationName) {
				Annotation mappingAnnotation = mappingAnnotation(mappingAnnotationName);
				if (mappingAnnotation != null) {
					this.mappingAnnotations.remove(mappingAnnotation);
					mappingAnnotation.removeAnnotation();
				}
			}
		}
	}
	
	//TODO need property change notification on this mappingAnnotation changing
	//from the context model we don't really care if their are multiple mapping annotations,
	//just which one we need to use
	public Annotation mappingAnnotation() {
		for (ListIterator<String> i = possibleMappingAnnotationNames(); i.hasNext(); ) {
			String mappingAnnotationName = i.next();
			for (Iterator<Annotation> j = mappingAnnotations(); j.hasNext(); ) {
				Annotation mappingAnnotation = j.next();
				if (mappingAnnotationName == mappingAnnotation.getAnnotationName()) {
					return mappingAnnotation;
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<NestableAnnotation> annotations(String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = containerAnnotation(containerAnnotationName);
		if (containerAnnotation != null) {
			return containerAnnotation.nestedAnnotations();
		}
		NestableAnnotation nestableAnnotation = nestableAnnotation(nestableAnnotationName);
		if (nestableAnnotation != null) {
			return new SingleElementListIterator<NestableAnnotation>(nestableAnnotation);
		}
		return EmptyListIterator.instance();
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		updateAnnotations(astRoot);
		setPersistable(calculatePersistability(astRoot));		
	}
	
	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);
		setPersistable(calculatePersistability(astRoot));		
	}
	
	protected void updateAnnotations(CompilationUnit astRoot) {
		getMember().bodyDeclaration(astRoot).accept(annotationVisitor(astRoot));
		removeMappingAnnotationsNotInSource(astRoot);
		removeAnnotationsNotInSource(astRoot);
	}
	
	protected void removeAnnotationsNotInSource(CompilationUnit astRoot) {
		for (Annotation annotation : CollectionTools.iterable(annotations())) {
			if (annotation.jdtAnnotation(astRoot) == null) {
				removeAnnotation_(annotation);
			}
		}		
	}
	
	protected void removeMappingAnnotationsNotInSource(CompilationUnit astRoot) {
		for (Annotation mappingAnnotation : CollectionTools.iterable(mappingAnnotations())) {
			if (mappingAnnotation.jdtAnnotation(astRoot) == null) {
				removeMappingAnnotation_(mappingAnnotation);
			}
		}	
	}
	
	protected ASTVisitor annotationVisitor(final CompilationUnit astRoot) {
		return new ASTVisitor() {
			@Override
			public boolean visit(SingleMemberAnnotation node) {
				return visit((org.eclipse.jdt.core.dom.Annotation) node);
			}
		
			@Override
			public boolean visit(NormalAnnotation node) {
				return visit((org.eclipse.jdt.core.dom.Annotation) node);
			}
		
			@Override
			public boolean visit(MarkerAnnotation node) {
				return visit((org.eclipse.jdt.core.dom.Annotation) node);
			}
			
			private boolean visit(org.eclipse.jdt.core.dom.Annotation node) {
				if (node.getParent() != getMember().bodyDeclaration(astRoot)) {
					//we don't want to look at annotations for child members, only this member
					return false;
				}
				addOrUpdateAnnotation(node, astRoot);
				return false;
			}
		};
	}
	
	protected void addOrUpdateAnnotation(org.eclipse.jdt.core.dom.Annotation node, CompilationUnit astRoot) {
		String qualifiedAnnotationName = JDTTools.resolveAnnotation(node);
		if (qualifiedAnnotationName == null) {
			return;
		}
		if (isPossibleAnnotation(qualifiedAnnotationName)) {
			Annotation annotation = annotation(qualifiedAnnotationName);
			if (annotation != null) {
				annotation.updateFromJava(astRoot);
			}
			else {
				annotation = buildAnnotation(qualifiedAnnotationName);
				annotation.initialize(astRoot);
				addAnnotation(annotation);				
			}
		}
		else if (isPossibleMappingAnnotation(qualifiedAnnotationName)) {
			Annotation annotation = mappingAnnotation(qualifiedAnnotationName);
			if (annotation != null) {
				annotation.updateFromJava(astRoot);
			}
			else {
				annotation = buildMappingAnnotation(qualifiedAnnotationName);
				annotation.initialize(astRoot);
				addMappingAnnotation(annotation);				
			}
		}
	}

	public boolean isFor(IMember iMember) {
		return getMember().wraps(iMember);
	}
	
	public boolean isPersistable() {
		return this.persistable;
	}
	
	protected void setPersistable(boolean newPersistable) {
		boolean oldPersistable = this.persistable;
		this.persistable = newPersistable;
		firePropertyChanged(PERSISTABLE_PROPERTY, oldPersistable, newPersistable);
		//TODO change notification to parent so that the context model gets notification 
		//that the list of persistable fields has been updated
		//
	}
	
	public boolean isPersisted() {
		return mappingAnnotation() != null;
	}
	

	public TextRange fullTextRange(CompilationUnit astRoot) {
		return this.getMember().textRange(astRoot);
	}

	public TextRange textRange(CompilationUnit astRoot) {
		return this.fullTextRange(astRoot);
	}

	public TextRange nameTextRange(CompilationUnit astRoot) {
		return this.getMember().nameTextRange(astRoot);
	}

}
