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
	 * stores all annotations(non-mapping) except duplicates, java compiler has an error for duplicates
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
		for (ListIterator<MappingAnnotationProvider> i = mappingAnnotationProviders(); i.hasNext(); ) {
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
	
	private void addMappingAnnotation(String annotationName) {
		if (mappingAnnotation(annotationName) != null) {
			return;
		}
		MappingAnnotationProvider provider = mappingAnnotationProvider(annotationName);
		//jpaPlatform().buildMappingAnnotation(annotationName);
		MappingAnnotation mappingAnnotation = provider.buildAnnotation(getMember(), jpaPlatform());
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
	private void removeUnnecessaryAnnotations(String oldMappingAnnotation, String newMappingAnnotation) {
		MappingAnnotationProvider oldProvider = mappingAnnotationProvider(oldMappingAnnotation);
		MappingAnnotationProvider newProvider = mappingAnnotationProvider(newMappingAnnotation);
		
		//TODO what about corresponding annotations for all other mapping types, those will stay??
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
	
	public ListIterator<NestableAnnotation> annotations(String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = containerAnnotation(containerAnnotationName);
		if (containerAnnotation != null) {
			return containerAnnotation.nestedAnnotations();
		}
		return new SingleElementListIterator<NestableAnnotation>(nestableAnnotation(nestableAnnotationName));
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
	
	//searches through possible mapping annotations and adds a MappingAnnotation to the model
	//for each one found.  If we want to include duplicates we would need to instead look at 
	//all the annotations on the Member (using a visitor).  Duplicates are handled by the compiler so there
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
	
	
	
	
	
//TODO should we use a visitor for finding all the annotations, or do it the way we currently do???	
	
//	private void updateJavaTypeAnnotations(CompilationUnit astRoot) {
//		astRoot.accept(getJavaTypeAnnotationAstVisitor());
//	}
//
//	private ASTVisitor typeVisitor;
//
//	private ASTVisitor getJavaTypeAnnotationAstVisitor() {
//		if (this.typeVisitor == null) {
//			this.typeVisitor =  new ASTVisitor() {
////				@Override
////				public boolean visit(TypeDeclaration node) {
////					if (getMember().b.equals(node.)
////					return super.visit(node);
////				}
//				@Override
//				public boolean visit(SingleMemberAnnotation node) {
//					return visit((org.eclipse.jdt.core.dom.Annotation) node);
//				}
//			
//				@Override
//				public boolean visit(NormalAnnotation node) {
//					return visit((org.eclipse.jdt.core.dom.Annotation) node);
//				}
//			
//				@Override
//				public boolean visit(MarkerAnnotation node) {
//					return visit((org.eclipse.jdt.core.dom.Annotation) node);
//				}
//				
//				private boolean visit(org.eclipse.jdt.core.dom.Annotation node) {
//					if (node.getParent().getNodeType() != ASTNode.TYPE_DECLARATION) {
//						return false;
//					}
//					String qualifiedAnnotationName = qualifiedAnnotationName(node);
//					if (qualifiedAnnotationName == null) {
//						return false;
//					}
//					AnnotationProvider provider = annotationProvider(qualifiedAnnotationName);
//					if (provider != null) {
//						Annotation annotation = annotation(qualifiedAnnotationName);
//						if (annotation == null) {
//							annotation = provider.buildAnnotation(getMember(), jpaPlatform());
//							addAnnotation(annotation);				
//						}
//						//TODO is it a bad idea to cast here??
//						annotation.updateFromJava((CompilationUnit) node.getRoot());
//					}
//					return false;
//				}
//			};
//		}
//		return this.typeVisitor;
//	}
//
//	
//	private static String qualifiedAnnotationName(org.eclipse.jdt.core.dom.Annotation node) {
//		IAnnotationBinding annotationBinding = node.resolveAnnotationBinding();
//		if (annotationBinding == null) {
//			return null;
//		}
//		ITypeBinding annotationTypeBinding = annotationBinding.getAnnotationType();
//		if (annotationTypeBinding == null) {
//			return null;
//		}
//		return annotationTypeBinding.getQualifiedName();
//	}
	
	
	
}
