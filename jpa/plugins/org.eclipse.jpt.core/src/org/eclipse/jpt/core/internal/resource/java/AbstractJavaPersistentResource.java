/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

public abstract class AbstractJavaPersistentResource<E extends Member> extends AbstractMemberResource<E> 
	implements JavaPersistentResource
{	
	/**
	 * stores all annotations(non-mapping) except duplicates, java compiler has an error for duplicates
	 */
	private final Collection<Annotation> annotations;
	
	/**
	 * stores all mapping annotations except duplicates, java compiler has an error for duplicates
	 */
	private final Collection<Annotation> mappingAnnotations;
	
	private boolean persistable;

	public AbstractJavaPersistentResource(JavaResource parent, E member){
		super(parent, member);
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

	protected abstract Annotation buildAnnotation(String annotationName);
	
	protected abstract Annotation buildNullAnnotation(String annotationName);

	protected abstract Annotation buildMappingAnnotation(String mappingAnnotationName);
	
	protected abstract Annotation buildNullMappingAnnotation(String annotationName);

	protected abstract ListIterator<String> possibleMappingAnnotationNames();

	protected abstract boolean isPossibleAnnotation(String annotationName);
	
	protected abstract boolean isPossibleMappingAnnotation(String annotationName);
	
	protected abstract boolean calculatePersistability(CompilationUnit astRoot);

	public Annotation annotation(String annotationName) {
		for (Iterator<Annotation> i = _annotations(); i.hasNext(); ) {
			Annotation annotation = i.next();
			if (annotation.getAnnotationName().equals(annotationName)) {
				return annotation;
			}
		}
		return null;
	}
	
	public JavaResource nonNullAnnotation(String annotationName) {
		Annotation annotation = annotation(annotationName);
		if (annotation == null) {
			return buildNullAnnotation(annotationName);	
		}
		return annotation;
	}
	
	public Annotation mappingAnnotation(String annotationName) {
		for (Iterator<Annotation> i = _mappingAnnotations(); i.hasNext(); ) {
			Annotation mappingAnnotation = i.next();
			if (mappingAnnotation.getAnnotationName().equals(annotationName)) {
				return mappingAnnotation;
			}
		}
		return null;
	}

	public JavaResource nonNullMappingAnnotation(String annotationName) {
		Annotation annotation = mappingAnnotation(annotationName);
		if (annotation == null) {
			return buildNullMappingAnnotation(annotationName);	
		}
		return annotation;
	}

	public Iterator<JavaResource> annotations() {
		return new CloneIterator<JavaResource>(this.annotations);
	}
	
	protected Iterator<Annotation> _annotations() {
		return new CloneIterator<Annotation>(this.annotations);
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
		ContainerAnnotation<NestableAnnotation> containerAnnotation = (ContainerAnnotation<NestableAnnotation>) buildAnnotation(containerAnnotationName);
		this.annotations.add(containerAnnotation);
		containerAnnotation.newAnnotation();
		containerAnnotation.addInternal(0).newAnnotation();
		containerAnnotation.addInternal(1).newAnnotation();
		return containerAnnotation;
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
	
	public void move(int oldIndex, int newIndex, String containerAnnotationName) {
		move(oldIndex, newIndex, containerAnnotation(containerAnnotationName));
	}
	
	protected void move(int oldIndex, int newIndex, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		containerAnnotation.move(oldIndex, newIndex);
		ContainerAnnotationTools.synchAnnotationsAfterMove(oldIndex, newIndex, containerAnnotation);
	}
	
	protected void addAnnotation(Annotation annotation) {
		addItemToCollection(annotation, this.annotations, ANNOTATIONS_COLLECTION);
	}
	
	protected void removeAnnotation(Annotation annotation) {
		removeItemFromCollection(annotation, this.annotations, ANNOTATIONS_COLLECTION);
		//TODO looks odd that we remove the annotation here, but in addAnnotation(Annotation) we don't do the same
		annotation.removeAnnotation();
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
		removeItemFromCollection(annotation, this.mappingAnnotations, MAPPING_ANNOTATIONS_COLLECTION);
		annotation.removeAnnotation();
	}
	
	public Iterator<JavaResource> mappingAnnotations() {
		return new CloneIterator<JavaResource>(this.mappingAnnotations);
	}
	
	protected Iterator<Annotation> _mappingAnnotations() {
		return new CloneIterator<Annotation>(this.mappingAnnotations);
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
			removeAnnotation(containerAnnotation);
			NestableAnnotation newAnnotation = (NestableAnnotation) addAnnotation(containerAnnotation.getNestableAnnotationName());
			newAnnotation.initializeFrom(nestedAnnotation);
		}
	}
		
	//TODO how to handle calling setMappingAnnotation with the same annotation as already exists?  is this an error?
	//or should we remove it and add it back as an empty annotation??
	public void setMappingAnnotation(String annotationName) {
		Annotation oldMapping = mappingAnnotation();
		if (oldMapping != null) {
			removeUnnecessaryAnnotations(oldMapping.getAnnotationName(), annotationName);
		}
		if (annotationName != null) {
			addMappingAnnotation(annotationName);
		}
	}
	
	/**
	 * Remove all mapping annotations that already exist
	 */
	protected void removeUnnecessaryAnnotations(String oldMappingAnnotationName, String newMappingAnnotationName) {		
		
		for (ListIterator<String> i = possibleMappingAnnotationNames(); i.hasNext(); ) {
			String mappingAnnotationName = i.next();
			if (mappingAnnotationName != newMappingAnnotationName) {
				Annotation mappingAnnotation = mappingAnnotation(mappingAnnotationName);
				if (mappingAnnotation != null) {
					removeMappingAnnotation(mappingAnnotation);
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
			for (Iterator<Annotation> j = _mappingAnnotations(); j.hasNext(); ) {
				Annotation mappingAnnotation = j.next();
				if (mappingAnnotationName == mappingAnnotation.getAnnotationName()) {
					return mappingAnnotation;
				}
			}
		}
		return null;
	}
	
	//TODO handle this compiler warning.  Also look at _annotations() and _mappingAnnotations, my workarounds
	//for those compiler warnings
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
		removeAnnotationsNotInSource(astRoot);
		removeMappingAnnotationsNotInSource(astRoot);
	}
	
	protected void removeAnnotationsNotInSource(CompilationUnit astRoot) {
		for (Annotation annotation : CollectionTools.iterable(_annotations())) {
			if (annotation.jdtAnnotation(astRoot) == null) {
				removeAnnotation(annotation);
			}
		}		
	}
	
	protected void removeMappingAnnotationsNotInSource(CompilationUnit astRoot) {
		for (Annotation mappingAnnotation : CollectionTools.iterable(_mappingAnnotations())) {
			if (mappingAnnotation.jdtAnnotation(astRoot) == null) {
				removeMappingAnnotation(mappingAnnotation);
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

	public boolean isFor(IMember member) {
		return getMember().wraps(member);
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
	

	public ITextRange fullTextRange(CompilationUnit astRoot) {
		return this.getMember().textRange(astRoot);
	}

	public ITextRange textRange(CompilationUnit astRoot) {
		return this.selectionTextRange(astRoot);
	}

	public ITextRange selectionTextRange(CompilationUnit astRoot) {
		return this.getMember().nameTextRange(astRoot);
	}

}
