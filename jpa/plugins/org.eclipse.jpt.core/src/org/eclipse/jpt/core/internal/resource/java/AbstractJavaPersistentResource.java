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
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

public abstract class AbstractJavaPersistentResource<E extends Member> extends AbstractResource<E> implements JavaPersistentResource
{	
	/**
	 * stores all annotations(non-mapping) except duplicates, java compiler has an error for duplicates
	 */
	private Collection<Annotation> annotations;
	
	/**
	 * stores all mapping annotations except duplicates, java compiler has an error for duplicates
	 */
	private Collection<MappingAnnotation> mappingAnnotations;
	
	private boolean persistable;

	public AbstractJavaPersistentResource(E member, IJpaPlatform jpaPlatform){
		super(member, jpaPlatform);
		this.annotations = new ArrayList<Annotation>();
		this.mappingAnnotations = new ArrayList<MappingAnnotation>();
	}

	protected abstract Annotation buildAnnotation(String annotationName);
	
	protected abstract MappingAnnotation buildMappingAnnotation(String mappingAnnotationName);
	
	protected abstract Iterator<String> correspondingAnnotationNames(String mappingAnnotationName);
	
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
	
	public MappingAnnotation mappingAnnotation(String annotationName) {
		for (Iterator<MappingAnnotation> i = mappingAnnotations(); i.hasNext(); ) {
			MappingAnnotation mappingAnnotation = i.next();
			if (mappingAnnotation.getAnnotationName().equals(annotationName)) {
				return mappingAnnotation;
			}
		}
		return null;
	}


	public Iterator<Annotation> annotations() {
		return new CloneIterator<Annotation>(this.annotations);
	}

	public Annotation addAnnotation(String annotationName) {
		Annotation annotation = buildAnnotation(annotationName);
		addAnnotation(annotation);		
		annotation.newAnnotation();
		
		return annotation;
	}

	@SuppressWarnings("unchecked")
	private ContainerAnnotation<NestableAnnotation> addContainerAnnotation(String containerAnnotationName) {
		return (ContainerAnnotation<NestableAnnotation>) addAnnotation(containerAnnotationName);
	}
	
	@SuppressWarnings("unchecked")
	private ContainerAnnotation<NestableAnnotation> containerAnnotation(String containerAnnotationName) {
		return (ContainerAnnotation<NestableAnnotation>) annotation(containerAnnotationName);
	}
	
	private NestableAnnotation nestableAnnotation(String nestableAnnotationName) {
		return (NestableAnnotation) annotation(nestableAnnotationName);
	}
	
	private NestableAnnotation addNestableAnnotation(String nestableAnnotationName) {
		return (NestableAnnotation) addAnnotation(nestableAnnotationName);
	}
	
	public NestableAnnotation addAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		NestableAnnotation nestableAnnotation = (NestableAnnotation) annotation(nestableAnnotationName);
		
		ContainerAnnotation<NestableAnnotation> containerAnnotation = containerAnnotation(containerAnnotationName);
		
		if (containerAnnotation != null) {
			//ignore any nestableAnnotation and just add to the plural one
			NestableAnnotation newNestableAnnotation = containerAnnotation.add(index);
			synchAnnotationsAfterAdd(index + 1, containerAnnotation);
			newNestableAnnotation.newAnnotation();
			return newNestableAnnotation;
		}
		if (nestableAnnotation == null) {
			//add the nestable since neither nestable or container exists
			return addNestableAnnotation(nestableAnnotationName);
		}
		//move the nestable to a new container annotation and add to it
		removeAnnotation(nestableAnnotation);
		ContainerAnnotation<NestableAnnotation> newContainerAnnotation = addContainerAnnotation(containerAnnotationName);
		NestableAnnotation newSingularAnnotation = newContainerAnnotation.add(0);
		newSingularAnnotation.newAnnotation();
		newSingularAnnotation.initializeFrom(nestableAnnotation);
		return newContainerAnnotation.add(newContainerAnnotation.nestedAnnotationsSize());
	}
	
	/**
	 * synchronize the source annotations with the model nestableAnnotations,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchAnnotationsAfterAdd(int index, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		List<NestableAnnotation> nestableAnnotations = CollectionTools.list(containerAnnotation.nestedAnnotations());
		for (int i = nestableAnnotations.size(); i-- > index;) {
			this.synch(nestableAnnotations.get(i), i);
		}
	}

	/**
	 * synchronize the source annotations with the model nestableAnnotations,
	 * starting at the specified index to prevent overlap
	 */
	private void synchAnnotationsAfterRemove(int index, ContainerAnnotation<NestableAnnotation> pluralAnnotation) {
		List<NestableAnnotation> nestableAnnotations = CollectionTools.list(pluralAnnotation.nestedAnnotations());
		for (int i = index; i < nestableAnnotations.size(); i++) {
			this.synch(nestableAnnotations.get(i), i);
		}
	}

	private void synch(NestableAnnotation nestableAnnotation, int index) {
		nestableAnnotation.moveAnnotation(index);
	}

	public void move(int newIndex, NestableAnnotation nestedAnnotation, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = containerAnnotation(containerAnnotationName);
		int oldIndex = containerAnnotation.indexOf(nestedAnnotation);
		move(oldIndex, newIndex, containerAnnotation);
	}
	
	public void move(int oldIndex, int newIndex, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = containerAnnotation(containerAnnotationName);
		move(oldIndex, newIndex, containerAnnotation);
	}
	
	private void move(int sourceIndex, int targetIndex, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		containerAnnotation.move(sourceIndex, targetIndex);
		synchAnnotationsAfterMove(sourceIndex, targetIndex, containerAnnotation);
	}
	
	/**
	 * synchronize the annotations with the model nestableAnnotations
	 */
	private void synchAnnotationsAfterMove(int sourceIndex, int targetIndex, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		NestableAnnotation nestableAnnotation = containerAnnotation.nestedAnnotationAt(targetIndex);
		
		this.synch(nestableAnnotation, containerAnnotation.nestedAnnotationsSize());
		
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
		this.synch(nestableAnnotation, targetIndex);
	}
	
	private void addAnnotation(Annotation annotation) {
		this.annotations.add(annotation);
		//TODO event notification
	}
	
	private void removeAnnotation(Annotation annotation) {
		this.annotations.remove(annotation);
		//TODO looks odd that we remove the annotation here, but in addAnnotation(Annotation) we don't do the same
		annotation.removeAnnotation();
		//TODO event notification
	}
	
	private void addMappingAnnotation(String mappingAnnotationName) {
		if (mappingAnnotation(mappingAnnotationName) != null) {
			return;
		}
		MappingAnnotation mappingAnnotation = buildMappingAnnotation(mappingAnnotationName);
		addMappingAnnotation(mappingAnnotation);
		//TODO should this be done here or should creating the Annotation do this??
		mappingAnnotation.newAnnotation();
	}
	

	private void addMappingAnnotation(MappingAnnotation annotation) {
		this.mappingAnnotations.add(annotation);
		//TODO event notification
	}
	
	private void removeMappingAnnotation(MappingAnnotation annotation) {
		this.mappingAnnotations.remove(annotation);
		annotation.removeAnnotation();
		//TODO event notification
	}
	
	public Iterator<MappingAnnotation> mappingAnnotations() {
		return new CloneIterator<MappingAnnotation>(this.mappingAnnotations);
	}

	public void removeAnnotation(String annotationName) {
		Annotation annotation = annotation(annotationName);
		if (annotation != null) {
			removeAnnotation(annotation);
		}
	}

	public void removeAnnotation(NestableAnnotation nestableAnnotation, String containerAnnotationName) {
		if (nestableAnnotation == annotation(nestableAnnotation.getAnnotationName())) {
			removeAnnotation(nestableAnnotation);
		}
		else {
			ContainerAnnotation<NestableAnnotation> containerAnnotation = containerAnnotation(containerAnnotationName);
			removeAnnotation(containerAnnotation.indexOf(nestableAnnotation), containerAnnotation);
		}
	}
	
	public void removeAnnotation(int index, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = containerAnnotation(containerAnnotationName);
		removeAnnotation(index, containerAnnotation);
	}
	
	public void removeAnnotation(int index, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		NestableAnnotation nestableAnnotation = containerAnnotation.nestedAnnotationAt(index);
		containerAnnotation.remove(index);
		nestableAnnotation.removeAnnotation();
		synchAnnotationsAfterRemove(index, containerAnnotation);
		
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
	
	public void removeMappingAnnotation(String annotationName) {
		MappingAnnotation mappingAnnotation = mappingAnnotation(annotationName);
		removeMappingAnnotation(mappingAnnotation);
	}
	
	//TODO how to handle calling setMappingAnnotation with the same annotation as already exists?  is this an error?
	//or should we remove it and add it back as an empty annotation??
	public void setMappingAnnotation(String annotationName) {
		MappingAnnotation oldMapping = mappingAnnotation();
		if (oldMapping != null) {
			removeUnnecessaryAnnotations(oldMapping.getAnnotationName(), annotationName);
		}
		addMappingAnnotation(annotationName);
	}
	
	/**
	 * removes annotations that applied to the old mapping annotation, but not to the new mapping annotation.
	 * also remove all mapping annotations that already exist
	 */
	private void removeUnnecessaryAnnotations(String oldMappingAnnotationName, String newMappingAnnotationName) {		
		//TODO what about corresponding annotations for all other mapping types, those will stay??
		Collection<String> annotationsToRemove = CollectionTools.collection(correspondingAnnotationNames(oldMappingAnnotationName));
		CollectionTools.removeAll(annotationsToRemove, correspondingAnnotationNames(newMappingAnnotationName));
		
		for (String annotationName : annotationsToRemove) {
			removeAnnotation(annotationName);
		}
		
		for (ListIterator<String> i = possibleMappingAnnotationNames(); i.hasNext(); ) {
			String mappingAnnotationName = i.next();
			if (mappingAnnotationName != newMappingAnnotationName) {
				MappingAnnotation mappingAnnotation = mappingAnnotation(mappingAnnotationName);
				if (mappingAnnotation != null) {
					removeMappingAnnotation(mappingAnnotation);
				}
			}
		}
	}
	
	//TODO need property change notification on this mappingAnnotation changing
	//from the context model we don't really care if their are multiple mapping annotations,
	//just which one we need to use
	public MappingAnnotation mappingAnnotation() {
		for (ListIterator<String> i = possibleMappingAnnotationNames(); i.hasNext(); ) {
			String mappingAnnotationName = i.next();
			for (Iterator<MappingAnnotation> j = mappingAnnotations(); j.hasNext(); ) {
				MappingAnnotation mappingAnnotation = j.next();
				if (mappingAnnotationName == mappingAnnotation.getAnnotationName()) {
					return mappingAnnotation;
				}
			}
		}
		return null;
	}
	
	public ListIterator<NestableAnnotation> annotations(String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = containerAnnotation(containerAnnotationName);
		if (containerAnnotation != null) {
			return containerAnnotation.nestedAnnotations();
		}
		return new SingleElementListIterator<NestableAnnotation>(nestableAnnotation(nestableAnnotationName));
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		updateAnnotations(astRoot);
		setPersistable(calculatePersistability(astRoot));
	}
	
	private void updateAnnotations(CompilationUnit astRoot) {
		getMember().bodyDeclaration(astRoot).accept(javaMemberAnnotationAstVisitor(astRoot));
		removeAnnotationsNotInSource(astRoot);
		removeMappingAnnotationsNotInSource(astRoot);
	}
	
	private void removeAnnotationsNotInSource(CompilationUnit astRoot) {
		for (Annotation annotation : CollectionTools.iterable(annotations())) {
			if (!getMember().containsAnnotation(annotation.getDeclarationAnnotationAdapter(), astRoot)) {
				removeAnnotation(annotation);
			}
		}		
	}
	
	private void removeMappingAnnotationsNotInSource(CompilationUnit astRoot) {
		for (MappingAnnotation mappingAnnotation : CollectionTools.iterable(mappingAnnotations())) {
			if (!getMember().containsAnnotation(mappingAnnotation.getDeclarationAnnotationAdapter(), astRoot)) {
				removeMappingAnnotation(mappingAnnotation);
			}
		}	
	}
	
	private ASTVisitor javaMemberAnnotationAstVisitor(final CompilationUnit astRoot) {
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
				addAnnotation(node);
				return false;
			}
		};
	}
	
	protected void addAnnotation(org.eclipse.jdt.core.dom.Annotation node) {
		String qualifiedAnnotationName = qualifiedAnnotationName(node);
		if (qualifiedAnnotationName == null) {
			return;
		}
		Annotation annotation = null;
		if (isPossibleAnnotation(qualifiedAnnotationName)) {
			annotation = annotation(qualifiedAnnotationName);
			if (annotation == null) {
				annotation = buildAnnotation(qualifiedAnnotationName);
				addAnnotation(annotation);				
			}
		}
		else if (isPossibleMappingAnnotation(qualifiedAnnotationName)) {
			annotation = mappingAnnotation(qualifiedAnnotationName);
			if (annotation == null) {
				annotation = buildMappingAnnotation(qualifiedAnnotationName);
				addMappingAnnotation((MappingAnnotation) annotation);				
			}
		}
		if (annotation != null) {
			//would be null in the case of a non JPA annotation
			annotation.updateFromJava((CompilationUnit) node.getRoot());
		}
	}
	
	private static String qualifiedAnnotationName(org.eclipse.jdt.core.dom.Annotation node) {
		IAnnotationBinding annotationBinding = node.resolveAnnotationBinding();
		if (annotationBinding == null) {
			return null;
		}
		ITypeBinding annotationTypeBinding = annotationBinding.getAnnotationType();
		if (annotationTypeBinding == null) {
			return null;
		}
		return annotationTypeBinding.getQualifiedName();
	}
	
	public boolean isFor(IMember member) {
		return getMember().wraps(member);
	}
	
	public boolean isPersistable() {
		return this.persistable;
	}
	
	private void setPersistable(boolean persistable) {
		this.persistable = persistable;
		//TODO change notification to parent so that the context model gets notification 
		//that the list of persistable fields has been updated
		//
	}
}
