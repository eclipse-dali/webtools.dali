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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

public abstract class AbstractJavaPersistentResource<E extends Member> extends AbstractResource<E> implements JavaPersistentResource
{	
	/**
	 * stores all annotations except duplicates, java compiler has an error for duplicates
	 */
	private Collection<Annotation> annotations;
	
	/**
	 * stores all mapping annotations except duplicates, java compiler has an error for duplicates
	 */
	private Collection<MappingAnnotation> mappingAnnotations;
	
	private boolean persistable;
	
	public AbstractJavaPersistentResource(E member, JpaPlatform jpaPlatform){
		super(member, jpaPlatform);
		this.annotations = new ArrayList<Annotation>();
		this.mappingAnnotations = new ArrayList<MappingAnnotation>();
	}

	protected abstract ListIterator<MappingAnnotationProvider> mappingAnnotationProviders();
	
	protected abstract Iterator<AnnotationProvider> annotationProviders();

	public MappingAnnotationProvider mappingAnnotationProvider(String annotationName) {
		for (Iterator<MappingAnnotationProvider> i = mappingAnnotationProviders(); i.hasNext(); ) {
			MappingAnnotationProvider provider = i.next();
			if (provider.getAnnotationName().equals(annotationName)) {
				return provider;
			}
		}
		throw new IllegalArgumentException(annotationName + " is an unsupported mapping annotation");
	}

	public AnnotationProvider annotationProvider(String annotationName) {
		for (Iterator<AnnotationProvider> i = annotationProviders(); i.hasNext(); ) {
			AnnotationProvider provider = i.next();
			if (provider.getAnnotationName().equals(annotationName)) {
				return provider;
			}
		}
		return null;
	}

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
		AnnotationProvider provider = annotationProvider(annotationName);
		Annotation annotation = provider.buildAnnotation(getMember(), jpaPlatform());
		addAnnotation(annotation);		
		annotation.newAnnotation();
		
		return annotation;
	}

	
	public Annotation addAnnotation(int index, String singularAnnotationName, String pluralAnnotationName) {
		SingularAnnotation singularAnnotation = (SingularAnnotation) annotation(singularAnnotationName);
		
		PluralAnnotation<SingularAnnotation> pluralAnnotation = (PluralAnnotation<SingularAnnotation>) annotation(pluralAnnotationName);
		
		if (pluralAnnotation != null) {
			//ignore any singularAnnotation and just add to the plural one
			SingularAnnotation newSingularAnnotation = pluralAnnotation.add(index);
			synchAnnotationsAfterAdd(index + 1, pluralAnnotation);
			newSingularAnnotation.newAnnotation();
			return newSingularAnnotation;
		}
		if (singularAnnotation == null) {
			//add the singular since neither singular or plural exists
			return addAnnotation(singularAnnotationName);
		}
		//move the singular to a new plural annotation and add to it
		removeAnnotation(singularAnnotation);
		PluralAnnotation<SingularAnnotation> newPluralAnnotation = (PluralAnnotation<SingularAnnotation>) addAnnotation(pluralAnnotationName);
		SingularAnnotation newSingularAnnotation = newPluralAnnotation.add(0);
		newSingularAnnotation.newAnnotation();
		newSingularAnnotation.initializeFrom(singularAnnotation);
		return newPluralAnnotation.add(newPluralAnnotation.singularAnnotationsSize());
	}
	
	/**
	 * synchronize the annotations with the model singularTypeAnnotations,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchAnnotationsAfterAdd(int index, PluralAnnotation<SingularAnnotation> pluralAnnotation) {
		List<SingularAnnotation> singularAnnotations = CollectionTools.list(pluralAnnotation.singularAnnotations());
		for (int i = singularAnnotations.size(); i-- > index;) {
			this.synch(singularAnnotations.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model singularTypeAnnotations,
	 * starting at the specified index to prevent overlap
	 */
	private void synchAnnotationsAfterRemove(int index, PluralAnnotation<SingularAnnotation> pluralAnnotation) {
		List<SingularAnnotation> singularAnnotations = CollectionTools.list(pluralAnnotation.singularAnnotations());
		for (int i = index; i < singularAnnotations.size(); i++) {
			this.synch(singularAnnotations.get(i), i);
		}
	}

	private void synch(SingularAnnotation singularAnnotation, int index) {
		singularAnnotation.moveAnnotation(index);
	}

	public void move(int newIndex, SingularAnnotation singularAnnotation, String pluralAnnotationName) {
		PluralAnnotation<SingularAnnotation> pluralAnnotation = (PluralAnnotation<SingularAnnotation>) annotation(pluralAnnotationName);
		int oldIndex = pluralAnnotation.indexOf(singularAnnotation);
		move(oldIndex, newIndex, pluralAnnotation);
	}
	
	public void move(int oldIndex, int newIndex, String pluralAnnotationName) {
		PluralAnnotation<SingularAnnotation> pluralAnnotation = (PluralAnnotation<SingularAnnotation>) annotation(pluralAnnotationName);
		move(oldIndex, newIndex, pluralAnnotation);
	}
	
	private void move(int sourceIndex, int targetIndex, PluralAnnotation<SingularAnnotation> pluralAnnotation) {
		pluralAnnotation.move(sourceIndex, targetIndex);
		synchAnnotationsAfterMove(sourceIndex, targetIndex, pluralAnnotation);
	}
	
	/**
	 * synchronize the annotations with the model singularTypeAnnotations
	 */
	private void synchAnnotationsAfterMove(int sourceIndex, int targetIndex, PluralAnnotation<SingularAnnotation> pluralAnnotation) {
		SingularAnnotation singularAnnotation = pluralAnnotation.singularAnnotationAt(targetIndex);
		
		this.synch(singularAnnotation, pluralAnnotation.singularAnnotationsSize());
		
		List<SingularAnnotation> singularAnnotations = CollectionTools.list(pluralAnnotation.singularAnnotations());
		if (sourceIndex < targetIndex) {
			for (int i = sourceIndex; i < targetIndex; i++) {
				synch(singularAnnotations.get(i), i);
			}
		}
		else {
			for (int i = sourceIndex; i > targetIndex; i-- ) {
				synch(singularAnnotations.get(i), i);			
			}
		}
		this.synch(singularAnnotation, targetIndex);
	}
	
	private void addAnnotation(Annotation annotation) {
		this.annotations.add(annotation);
		//TODO event notification
	}
	
	private void removeAnnotation(Annotation annotation) {
		this.annotations.remove(annotation);
		//TODO looks odd that we remove the annotation here, but in addJavaTypeannotation(JavaTypeAnnotation) we don't do the same
		annotation.removeAnnotation();
		//TODO event notification
	}
	
	private void addMappingAnnotation(String annotation) {
		if (mappingAnnotation(annotation) != null) {
			return;
		}
		MappingAnnotationProvider provider = mappingAnnotationProvider(annotation);
		MappingAnnotation mappingAnnotation = provider.buildAnnotation(getMember(), jpaPlatform());
		addMappingAnnotation(mappingAnnotation);
		//TODO should this be done here or should creating the JavaTypeAnnotation do this??
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

	public void removeAnnotation(String typeAnnotationName) {
		Annotation javaTypeAnnotation = annotation(typeAnnotationName);
		if (javaTypeAnnotation != null) {
			removeAnnotation(javaTypeAnnotation);
		}
	}

	public void removeAnnotation(SingularAnnotation singularAnnotation, String pluralAnnotationName) {
		if (singularAnnotation == annotation(singularAnnotation.getAnnotationName())) {
			removeAnnotation(singularAnnotation);
		}
		else {
			PluralAnnotation<SingularAnnotation> pluralAnnotation = (PluralAnnotation) annotation(pluralAnnotationName);
			removeAnnotation(pluralAnnotation.indexOf(singularAnnotation), pluralAnnotation);
		}
	}
	
	public void removeAnnotation(int index, String pluralAnnotationName) {
		PluralAnnotation<SingularAnnotation> pluralAnnotation = (PluralAnnotation) annotation(pluralAnnotationName);
		removeAnnotation(index, pluralAnnotation);
	}
	
	public void removeAnnotation(int index, PluralAnnotation<SingularAnnotation> pluralAnnotation) {
		SingularAnnotation singularAnnotation = pluralAnnotation.singularAnnotationAt(index);
		pluralAnnotation.remove(index);
		singularAnnotation.removeAnnotation();
		synchAnnotationsAfterRemove(index, pluralAnnotation);
		
		if (pluralAnnotation.singularAnnotationsSize() == 0) {
			removeAnnotation(pluralAnnotation);
		}
		else if (pluralAnnotation.singularAnnotationsSize() == 1) {
			SingularAnnotation nestedAnnotation = pluralAnnotation.singularAnnotationAt(0);
			removeAnnotation(pluralAnnotation);
			SingularAnnotation newAnnotation = (SingularAnnotation) addAnnotation(pluralAnnotation.getSingularAnnotationName());
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
	private void removeUnnecessaryAnnotations(String oldMappingAnnotation, String newMappingAnnotation) {
		MappingAnnotationProvider oldProvider = mappingAnnotationProvider(oldMappingAnnotation);
		MappingAnnotationProvider newProvider = mappingAnnotationProvider(newMappingAnnotation);
		
		Collection<String> annotationsToRemove = CollectionTools.collection(oldProvider.correspondingAnnotationNames());
		CollectionTools.removeAll(annotationsToRemove, newProvider.correspondingAnnotationNames());
		
		for (String annotationName : annotationsToRemove) {
			removeAnnotation(annotationName);
		}
		
		for (ListIterator<MappingAnnotationProvider> i = mappingAnnotationProviders(); i.hasNext(); ) {
			MappingAnnotationProvider provider = i.next();
			String mappingAnnotationName = provider.getAnnotationName();
			if (mappingAnnotationName != newMappingAnnotation) {
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
		for (ListIterator<MappingAnnotationProvider> i = mappingAnnotationProviders(); i.hasNext(); ) {
			MappingAnnotationProvider provider = i.next();
			for (Iterator<MappingAnnotation> j = mappingAnnotations(); j.hasNext(); ) {
				MappingAnnotation mappingAnnotation = j.next();
				if (provider.getAnnotationName() == mappingAnnotation.getAnnotationName()) {
					return mappingAnnotation;
				}
			}
		}
		return null;
	}
	
	public ListIterator<Annotation> annotations(String singularAnnotation, String pluralAnnotationName) {
		Annotation pluralAnnotation = annotation(pluralAnnotationName);
		if (pluralAnnotation != null) {
			return ((PluralAnnotation) pluralAnnotation).singularAnnotations();
		}
		return new SingleElementListIterator<Annotation>(annotation(singularAnnotation));
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		updateAnnotations(astRoot);
		updateMappingAnnotations(astRoot);
		setPersistable(calculatePersistability(astRoot));
	}
		
	//searches through possible type annotations and adds a JavaTypeAnnotation to the model
	//for each one found.  If we want to include duplicates we would need to instead look at 
	//all the annotations on a Type.  Duplicates are handled by the compiler so there
	//doesn't seem to be a reason to include them in our model
	private void updateAnnotations(CompilationUnit astRoot) {
		for (Iterator<AnnotationProvider> i = annotationProviders(); i.hasNext(); ) {
			AnnotationProvider provider = i.next();
			if (getMember().containsAnnotation(provider.getDeclarationAnnotationAdapter(), astRoot)) {
				Annotation annotation = annotation(provider.getAnnotationName());
				if (annotation == null) {
					annotation = provider.buildAnnotation(getMember(), jpaPlatform());
					addAnnotation(annotation);
				}
				annotation.updateFromJava(astRoot);
			}
		}
		
		for (Iterator<Annotation> i = annotations(); i.hasNext(); ) {
			Annotation annotation = i.next();
			if (!getMember().containsAnnotation(annotation.getDeclarationAnnotationAdapter(), astRoot)) {
				removeAnnotation(annotation);
			}
		}		
	}	
	
	//searches through possible type mapping annotations and adds a JavaTypeMappingAnnotation to the model
	//for each one found.  If we want to include duplicates we would need to instead look at 
	//all the annotations on a Type.  Duplicates are handled by the compiler so there
	//doesn't seem to be a reason to include them in our model
	private void updateMappingAnnotations(CompilationUnit astRoot) {
		for (Iterator<MappingAnnotationProvider> i = mappingAnnotationProviders(); i.hasNext(); ) {
			MappingAnnotationProvider provider = i.next();
			if (getMember().containsAnnotation(provider.getDeclarationAnnotationAdapter(), astRoot)) {
				MappingAnnotation mappingAnnotation = mappingAnnotation(provider.getAnnotationName());
				if (mappingAnnotation == null) {
					mappingAnnotation = provider.buildAnnotation(getMember(), jpaPlatform());
					addMappingAnnotation(mappingAnnotation);
				}
				mappingAnnotation.updateFromJava(astRoot);
			}
		}
		
		for (Iterator<MappingAnnotation> i = mappingAnnotations(); i.hasNext(); ) {
			MappingAnnotation mappingAnnotation = i.next();
			if (!getMember().containsAnnotation(mappingAnnotation.getDeclarationAnnotationAdapter(), astRoot)) {
				removeMappingAnnotation(mappingAnnotation);
			}
		}
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
	
	protected abstract boolean calculatePersistability(CompilationUnit astRoot);
	
}
