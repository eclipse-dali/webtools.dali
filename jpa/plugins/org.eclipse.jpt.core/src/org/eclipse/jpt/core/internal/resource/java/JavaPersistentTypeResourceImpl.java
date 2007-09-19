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
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AttributeAnnotationTools;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

public class JavaPersistentTypeResourceImpl extends AbstractJavaResource<Type> implements JavaPersistentTypeResource
{	
	/**
	 * stores all type annotations except duplicates, java compiler has an error for duplicates
	 */
	private Collection<TypeAnnotation> javaTypeAnnotations;
	
	/**
	 * stores all type mapping annotations except duplicates, java compiler has an error for duplicates
	 */
	private Collection<TypeMappingAnnotation> javaTypeMappingAnnotations;

	/**
	 * store all member types including those that aren't persistable so we can include validation errors.
	 */
	private List<JavaPersistentTypeResource> nestedTypes;
	
	public JavaPersistentTypeResourceImpl(Type type, JpaPlatform jpaPlatform){
		super(type, jpaPlatform);
		this.javaTypeAnnotations = new ArrayList<TypeAnnotation>();
		this.javaTypeMappingAnnotations = new ArrayList<TypeMappingAnnotation>();
		this.nestedTypes = new ArrayList<JavaPersistentTypeResource>(); 
	}

	protected ListIterator<TypeMappingAnnotationProvider> javaTypeMappingAnnotationProviders() {
		return jpaPlatform().javaTypeMappingAnnotationProviders();
	}


	public TypeAnnotation javaTypeAnnotation(String annotationName) {
		for (Iterator<TypeAnnotation> i = javaTypeAnnotations(); i.hasNext(); ) {
			TypeAnnotation javaTypeAnnotation = i.next();
			if (javaTypeAnnotation.getAnnotationName().equals(annotationName)) {
				return javaTypeAnnotation;
			}
		}
		return null;
	}
	
	public TypeMappingAnnotation javaTypeMappingAnnotation(String annotationName) {
		for (Iterator<TypeMappingAnnotation> i = javaTypeMappingAnnotations(); i.hasNext(); ) {
			TypeMappingAnnotation javaTypeMappingAnnotation = i.next();
			if (javaTypeMappingAnnotation.getAnnotationName().equals(annotationName)) {
				return javaTypeMappingAnnotation;
			}
		}
		return null;
	}


	public Iterator<TypeAnnotation> javaTypeAnnotations() {
		return new CloneIterator<TypeAnnotation>(this.javaTypeAnnotations);
	}

	public TypeAnnotation addJavaTypeAnnotation(String annotationName) {
		TypeAnnotationProvider provider = jpaPlatform().javaTypeAnnotationProvider(annotationName);
		TypeAnnotation javaTypeAnnotation = provider.buildJavaTypeAnnotation(getMember(), jpaPlatform());
		addJavaTypeAnnotation(javaTypeAnnotation);		
		javaTypeAnnotation.newAnnotation();
		
		return javaTypeAnnotation;
	}

	public TypeAnnotation addJavaTypeAnnotation(int index, String singularAnnotationName, String pluralAnnotationName) {
		SingularTypeAnnotation singularAnnotation = (SingularTypeAnnotation) javaTypeAnnotation(singularAnnotationName);
		PluralTypeAnnotation pluralAnnotation = (PluralTypeAnnotation) javaTypeAnnotation(pluralAnnotationName);
		
		if (pluralAnnotation != null) {
			//ignore any singularAnnotation and just add to the plural one
			SingularTypeAnnotation singularTypeAnnotation = pluralAnnotation.add(index);
			synchAnnotationsAfterAdd(index + 1, pluralAnnotation);
			singularTypeAnnotation.newAnnotation();
			return singularTypeAnnotation;
		}
		if (singularAnnotation == null) {
			//add the singular since neither singular or plural exists
			return addJavaTypeAnnotation(singularAnnotationName);
		}
		//move the singular to a new plural annotation and add to it
		removeJavaTypeAnnotation(singularAnnotation);
		PluralTypeAnnotation pluralTypeAnnotation = (PluralTypeAnnotation) addJavaTypeAnnotation(pluralAnnotationName);
		SingularTypeAnnotation newSingularAnnotation = pluralTypeAnnotation.add(0);
		newSingularAnnotation.newAnnotation();
		newSingularAnnotation.initializeFrom(singularAnnotation);
		return pluralTypeAnnotation.add(pluralTypeAnnotation.javaTypeAnnotationsSize());
	}
	
	/**
	 * synchronize the annotations with the model singularTypeAnnotations,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchAnnotationsAfterAdd(int index, PluralTypeAnnotation<SingularTypeAnnotation> pluralAnnotation) {
		List<SingularTypeAnnotation> singularAnnotations = CollectionTools.list(pluralAnnotation.javaTypeAnnotations());
		for (int i = singularAnnotations.size(); i-- > index;) {
			this.synch(singularAnnotations.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model singularTypeAnnotations,
	 * starting at the specified index to prevent overlap
	 */
	private void synchAnnotationsAfterRemove(int index, PluralTypeAnnotation<SingularTypeAnnotation> pluralAnnotation) {
		List<SingularTypeAnnotation> singularAnnotations = CollectionTools.list(pluralAnnotation.javaTypeAnnotations());
		for (int i = index; i < singularAnnotations.size(); i++) {
			this.synch(singularAnnotations.get(i), i);
		}
	}

	private void synch(SingularTypeAnnotation singularTypeAnnotation, int index) {
		singularTypeAnnotation.moveAnnotation(index);
	}

	public void move(int newIndex, SingularTypeAnnotation singularAnnotation, String pluralAnnotationName) {
		PluralTypeAnnotation<SingularTypeAnnotation> pluralTypeAnnotation = (PluralTypeAnnotation<SingularTypeAnnotation>) javaTypeAnnotation(pluralAnnotationName);
		int oldIndex = pluralTypeAnnotation.indexOf(singularAnnotation);
		move(oldIndex, newIndex, pluralTypeAnnotation);
	}
	
	public void move(int oldIndex, int newIndex, String pluralAnnotationName) {
		PluralTypeAnnotation<SingularTypeAnnotation> pluralTypeAnnotation = (PluralTypeAnnotation<SingularTypeAnnotation>) javaTypeAnnotation(pluralAnnotationName);
		move(oldIndex, newIndex, pluralTypeAnnotation);
	}
	
	private void move(int sourceIndex, int targetIndex, PluralTypeAnnotation<SingularTypeAnnotation> pluralAnnotation) {
		pluralAnnotation.move(sourceIndex, targetIndex);
		synchAnnotationsAfterMove(sourceIndex, targetIndex, pluralAnnotation);
	}
	
	/**
	 * synchronize the annotations with the model singularTypeAnnotations
	 */
	private void synchAnnotationsAfterMove(int sourceIndex, int targetIndex, PluralTypeAnnotation<SingularTypeAnnotation> pluralAnnotation) {
		SingularTypeAnnotation singularTypeAnnotation = pluralAnnotation.singularAnnotationAt(targetIndex);
		
		this.synch(singularTypeAnnotation, pluralAnnotation.javaTypeAnnotationsSize());
		
		List<SingularTypeAnnotation> singularAnnotations = CollectionTools.list(pluralAnnotation.javaTypeAnnotations());
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
		this.synch(singularTypeAnnotation, targetIndex);
	}
	
	private void addJavaTypeAnnotation(TypeAnnotation javaTypeAnnotation) {
		this.javaTypeAnnotations.add(javaTypeAnnotation);
		//TODO event notification
	}
	
	private void removeJavaTypeAnnotation(TypeAnnotation javaTypeAnnotation) {
		this.javaTypeAnnotations.remove(javaTypeAnnotation);
		//TODO looks odd that we remove the annotation here, but in addJavaTypeannotation(JavaTypeAnnotation) we don't do the same
		javaTypeAnnotation.removeAnnotation();
		//TODO event notification
	}
	
	private void addJavaTypeMappingAnnotation(String annotation) {
		if (javaTypeMappingAnnotation(annotation) != null) {
			return;
		}
		TypeMappingAnnotationProvider provider = jpaPlatform().javaTypeMappingAnnotationProvider(annotation);
		TypeMappingAnnotation javaTypeMappingAnnotation = provider.buildJavaTypeAnnotation(getMember(), jpaPlatform());
		addJavaTypeMappingAnnotation(javaTypeMappingAnnotation);
		//TODO should this be done here or should creating the JavaTypeAnnotation do this??
		javaTypeMappingAnnotation.newAnnotation();
	}

	private void addJavaTypeMappingAnnotation(TypeMappingAnnotation annotation) {
		this.javaTypeMappingAnnotations.add(annotation);
		//TODO event notification
	}
	
	private void removeJavaTypeMappingAnnotation(TypeMappingAnnotation annotation) {
		this.javaTypeMappingAnnotations.remove(annotation);
		annotation.removeAnnotation();
		//TODO event notification
	}
	
	public Iterator<TypeMappingAnnotation> javaTypeMappingAnnotations() {
		return new CloneIterator<TypeMappingAnnotation>(this.javaTypeMappingAnnotations);
	}

	public void removeJavaTypeAnnotation(String typeAnnotationName) {
		TypeAnnotation javaTypeAnnotation = javaTypeAnnotation(typeAnnotationName);
		if (javaTypeAnnotation != null) {
			removeJavaTypeAnnotation(javaTypeAnnotation);
		}
	}

	public void removeJavaTypeAnnotation(SingularTypeAnnotation singularAnnotation, String pluralAnnotationName) {
		if (singularAnnotation == javaTypeAnnotation(singularAnnotation.getAnnotationName())) {
			removeJavaTypeAnnotation(singularAnnotation);
		}
		else {
			PluralTypeAnnotation<SingularTypeAnnotation> pluralAnnotation = (PluralTypeAnnotation) javaTypeAnnotation(pluralAnnotationName);
			removeJavaTypeAnnotation(pluralAnnotation.indexOf(singularAnnotation), pluralAnnotation);
		}
	}
	
	public void removeJavaTypeAnnotation(int index, String pluralAnnotationName) {
		PluralTypeAnnotation<SingularTypeAnnotation> pluralAnnotation = (PluralTypeAnnotation) javaTypeAnnotation(pluralAnnotationName);
		removeJavaTypeAnnotation(index, pluralAnnotation);
	}
	
	public void removeJavaTypeAnnotation(int index, PluralTypeAnnotation<SingularTypeAnnotation> javaPluralTypeAnnotation) {
		SingularTypeAnnotation singularTypeAnnotation = javaPluralTypeAnnotation.singularAnnotationAt(index);
		javaPluralTypeAnnotation.remove(index);
		singularTypeAnnotation.removeAnnotation();
		synchAnnotationsAfterRemove(index, javaPluralTypeAnnotation);
		
		if (javaPluralTypeAnnotation.javaTypeAnnotationsSize() == 0) {
			removeJavaTypeAnnotation(javaPluralTypeAnnotation);
		}
		else if (javaPluralTypeAnnotation.javaTypeAnnotationsSize() == 1) {
			SingularTypeAnnotation nestedJavaTypeAnnotation = javaPluralTypeAnnotation.singularAnnotationAt(0);
			removeJavaTypeAnnotation(javaPluralTypeAnnotation);
			SingularTypeAnnotation newJavaTypeAnnotation = (SingularTypeAnnotation) addJavaTypeAnnotation(javaPluralTypeAnnotation.getSingularAnnotationName());
			newJavaTypeAnnotation.initializeFrom(nestedJavaTypeAnnotation);
		}
	}
	
	public void removeJavaTypeMappingAnnotation(String annotationName) {
		TypeMappingAnnotation javaTypeMappingAnnotation = javaTypeMappingAnnotation(annotationName);
		removeJavaTypeMappingAnnotation(javaTypeMappingAnnotation);
	}
	
	//TODO how to handle calling setJavaTypeMappingAnnotation with the same annotation as already exists?  is this an error?
	//or should we remove it and add it back as an empty annotation??
	public void setJavaTypeMappingAnnotation(String annotationName) {
		TypeMappingAnnotation oldMapping = javaTypeMappingAnnotation();
		if (oldMapping != null) {
			removeUnnecessaryAnnotations(oldMapping.getAnnotationName(), annotationName);
		}
		addJavaTypeMappingAnnotation(annotationName);
	}
	/**
	 * removes annotations that applied to the old mapping annotation, but not to the new mapping annotation.
	 * also remove all mapping annotations that already exist
	 */
	private void removeUnnecessaryAnnotations(String oldMappingAnnotation, String newMappingAnnotation) {
		TypeMappingAnnotationProvider oldProvider = jpaPlatform().javaTypeMappingAnnotationProvider(oldMappingAnnotation);
		TypeMappingAnnotationProvider newProvider = jpaPlatform().javaTypeMappingAnnotationProvider(newMappingAnnotation);
		
		Collection<String> annotationsToRemove = CollectionTools.collection(oldProvider.correspondingAnnotationNames());
		CollectionTools.removeAll(annotationsToRemove, newProvider.correspondingAnnotationNames());
		
		for (String annotationName : annotationsToRemove) {
			removeJavaTypeAnnotation(annotationName);
		}
		
		for (ListIterator<TypeMappingAnnotationProvider> i = javaTypeMappingAnnotationProviders(); i.hasNext(); ) {
			TypeMappingAnnotationProvider provider = i.next();
			String mappingAnnotationName = provider.getAnnotationName();
			if (mappingAnnotationName != newMappingAnnotation) {
				TypeMappingAnnotation javaTypeMappingAnnotation = javaTypeMappingAnnotation(mappingAnnotationName);
				if (javaTypeMappingAnnotation != null) {
					removeJavaTypeMappingAnnotation(javaTypeMappingAnnotation);
				}
			}
		}
	}
	
	//TODO need property change notification on this javaTypeMappingAnnotation changing
	//from the context model we don't really care if their are multiple mapping annotations,
	//just which one we need to use
	public TypeMappingAnnotation javaTypeMappingAnnotation() {
		for (ListIterator<TypeMappingAnnotationProvider> i = javaTypeMappingAnnotationProviders(); i.hasNext(); ) {
			TypeMappingAnnotationProvider provider = i.next();
			for (Iterator<TypeMappingAnnotation> j = javaTypeMappingAnnotations(); j.hasNext(); ) {
				TypeMappingAnnotation javaTypeMappingAnnotation = j.next();
				if (provider.getAnnotationName() == javaTypeMappingAnnotation.getAnnotationName()) {
					return javaTypeMappingAnnotation;
				}
			}
		}
		return null;
	}
	
	public ListIterator<TypeAnnotation> javaTypeAnnotations(String singularAnnotation, String pluralAnnotation) {
		TypeAnnotation javaPluralTypeAnnotation = javaTypeAnnotation(pluralAnnotation);
		if (javaPluralTypeAnnotation != null) {
			return ((PluralTypeAnnotation) javaPluralTypeAnnotation).javaTypeAnnotations();
		}
		return new SingleElementListIterator<TypeAnnotation>(javaTypeAnnotation(singularAnnotation));
	}
	
	public Iterator<JavaPersistentTypeResource> nestedTypes() {
		return new FilteringIterator<JavaPersistentTypeResource>(this.nestedTypes.listIterator()) {
			@Override
			protected boolean accept(Object o) {
				return ((JavaPersistentTypeResource) o).isPersistable();
			}
		};
	}
	
	private JavaPersistentTypeResource nestedType(IType type) {
		for (JavaPersistentTypeResource nestedType : this.nestedTypes) {
			if (nestedType.isFor(type)) {
				return nestedType;
			}
		}
		return null;
	}
	//I think this should be private since adding/removing of nestedTypes
	//only depends on the underlying java IType, not on anything our context model can do.
	private JavaPersistentTypeResource addNestedType(IType nestedType) {
		JavaPersistentTypeResource persistentType = createJavaPersistentType(nestedType);
		addNestedType(persistentType);
		return persistentType;
	}

	private void addNestedType(JavaPersistentTypeResource nestedType) {
		this.nestedTypes.add(nestedType);
		//TODO property change notification, or other notification to the context model
	}
	
	//I think this should be private since adding/removing of nestedTypes
	//only depends on the underlying java IType, not on anything our context model can do.
	private void removeNestedType(JavaPersistentTypeResource nestedType) {
		this.nestedTypes.remove(nestedType);
		//TODO property change notification, or other notification to the context model
	}
	
	private JavaPersistentTypeResource createJavaPersistentType(IType nestedType) {
		Type type = new Type(nestedType, this.modifySharedDocumentCommandExecutorProvider());
		return new JavaPersistentTypeResourceImpl(type, jpaPlatform());
	}

	
	public void updateFromJava(CompilationUnit astRoot) {
		updateNestedTypes(astRoot);
		updateJavaTypeAnnotations(astRoot);
		updateJavaTypeMappingAnnotations(astRoot);
	}
	
	private void updateNestedTypes(CompilationUnit astRoot) {
		IType[] declaredTypes = getMember().declaredTypes();
		
		List<JavaPersistentTypeResource> nestedTypesToRemove = new ArrayList<JavaPersistentTypeResource>(this.nestedTypes);
		for (IType declaredType : declaredTypes) {
			JavaPersistentTypeResource nestedType = nestedType(declaredType);
			if (nestedType == null) {
				nestedType = addNestedType(declaredType);
			}
			else {
				nestedTypesToRemove.remove(nestedType);
			}
			nestedType.updateFromJava(astRoot);
		}
		for (JavaPersistentTypeResource nestedType : nestedTypesToRemove) {
			removeNestedType(nestedType);
		}
	}
	
	
	/**
	 * delegate to the type's project (there is one provider per project)
	 */
	private CommandExecutorProvider modifySharedDocumentCommandExecutorProvider() {
		return this.jpaPlatform().modifySharedDocumentCommandExecutorProvider();
	}
	
	//searches through possible type annotations and adds a JavaTypeAnnotation to the model
	//for each one found.  If we want to include duplicates we would need to instead look at 
	//all the annotations on a Type.  Duplicates are handled by the compiler so there
	//doesn't seem to be a reason to include them in our model
	private void updateJavaTypeAnnotations(CompilationUnit astRoot) {
		for (Iterator<TypeAnnotationProvider> i = jpaPlatform().javaTypeAnnotationProviders(); i.hasNext(); ) {
			TypeAnnotationProvider provider = i.next();
			if (getMember().containsAnnotation(provider.getDeclarationAnnotationAdapter(), astRoot)) {
				TypeAnnotation javaTypeAnnotation = javaTypeAnnotation(provider.getAnnotationName());
				if (javaTypeAnnotation == null) {
					javaTypeAnnotation = provider.buildJavaTypeAnnotation(getMember(), jpaPlatform());
					addJavaTypeAnnotation(javaTypeAnnotation);
				}
				javaTypeAnnotation.updateFromJava(astRoot);
			}
		}
		
		for (Iterator<TypeAnnotation> i = javaTypeAnnotations(); i.hasNext(); ) {
			TypeAnnotation javaTypeAnnotation = i.next();
			if (!getMember().containsAnnotation(javaTypeAnnotation.getDeclarationAnnotationAdapter(), astRoot)) {
				removeJavaTypeAnnotation(javaTypeAnnotation);
			}
		}		
	}	
	
	//searches through possible type mapping annotations and adds a JavaTypeMappingAnnotation to the model
	//for each one found.  If we want to include duplicates we would need to instead look at 
	//all the annotations on a Type.  Duplicates are handled by the compiler so there
	//doesn't seem to be a reason to include them in our model
	private void updateJavaTypeMappingAnnotations(CompilationUnit astRoot) {
		for (Iterator<TypeMappingAnnotationProvider> i = jpaPlatform().javaTypeMappingAnnotationProviders(); i.hasNext(); ) {
			TypeMappingAnnotationProvider provider = i.next();
			if (getMember().containsAnnotation(provider.getDeclarationAnnotationAdapter(), astRoot)) {
				TypeMappingAnnotation javaTypeMappingAnnotation = javaTypeMappingAnnotation(provider.getAnnotationName());
				if (javaTypeMappingAnnotation == null) {
					javaTypeMappingAnnotation = provider.buildJavaTypeAnnotation(getMember(), jpaPlatform());
					addJavaTypeMappingAnnotation(javaTypeMappingAnnotation);
				}
				javaTypeMappingAnnotation.updateFromJava(astRoot);
			}
		}
		
		for (Iterator<TypeMappingAnnotation> i = javaTypeMappingAnnotations(); i.hasNext(); ) {
			TypeMappingAnnotation javaTypeMappingAnnotation = i.next();
			if (!getMember().containsAnnotation(javaTypeMappingAnnotation.getDeclarationAnnotationAdapter(), astRoot)) {
				removeJavaTypeMappingAnnotation(javaTypeMappingAnnotation);
			}
		}
	}

	public boolean isFor(IType type) {
		return getMember().wraps(type);
	}
	
	public boolean isPersistable() {
		return AttributeAnnotationTools.typeIsPersistable(getMember().getJdtMember());
	}
}
