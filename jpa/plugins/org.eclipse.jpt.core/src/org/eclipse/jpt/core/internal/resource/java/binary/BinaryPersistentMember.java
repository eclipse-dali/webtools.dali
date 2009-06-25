/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.StaticCloneIterable;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

/**
 * binary persistent member
 */
abstract class BinaryPersistentMember
	extends BinaryNode
	implements JavaResourcePersistentMember
{
	/** JDT member adapter */
	final Adapter adapter;

	/** mapping annotations */
	final Vector<Annotation> mappingAnnotations = new Vector<Annotation>();

	/** supporting annotations */
	final Vector<Annotation> supportingAnnotations = new Vector<Annotation>();

	boolean persistable;


	// ********** construction/initialization **********

	public BinaryPersistentMember(JavaResourceNode parent, Adapter adapter) {
		super(parent);
		this.adapter = adapter;
		this.initializeAnnotations();
		this.persistable = this.buildPersistable();
	}

	private void initializeAnnotations() {
		for (IAnnotation annotation : this.getJdtAnnotations()) {
			this.addAnnotation(annotation);
		}
	}

	private void addAnnotation(IAnnotation jdtAnnotation) {
		if (this.annotationIsValidSupportingAnnotation(jdtAnnotation)) {
			this.supportingAnnotations.add(this.buildSupportingAnnotation(jdtAnnotation));
		} else if (this.annotationIsValidMappingAnnotation(jdtAnnotation)) {
			this.mappingAnnotations.add(this.buildMappingAnnotation(jdtAnnotation));
		}
	}


	// ********** updating **********

	@Override
	public void update() {
		super.update();
		this.updateAnnotations();
		this.setPersistable(this.buildPersistable());
	}

	// TODO
	private void updateAnnotations() {
		throw new UnsupportedOperationException();
	}


	// ********** mapping annotations **********

	public Iterator<Annotation> mappingAnnotations() {
		return this.getMappingAnnotations().iterator();
	}

	private Iterable<Annotation> getMappingAnnotations() {
		return new LiveCloneIterable<Annotation>(this.mappingAnnotations);
	}

	public int mappingAnnotationsSize() {
		return this.mappingAnnotations.size();
	}

	public Annotation getMappingAnnotation() {
		Iterable<Annotation> annotations = new StaticCloneIterable<Annotation>(this.mappingAnnotations);
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

	private boolean annotationIsValidMappingAnnotation(IAnnotation jdtAnnotation) {
		return CollectionTools.contains(this.validMappingAnnotationNames(), jdtAnnotation.getElementName());
	}

	abstract ListIterator<String> validMappingAnnotationNames();

	abstract Annotation buildMappingAnnotation(IAnnotation jdtAnnotation);


	// ********** supporting annotations **********

	public Iterator<Annotation> supportingAnnotations() {
		return this.getSupportingAnnotations().iterator();
	}

	private Iterable<Annotation> getSupportingAnnotations() {
		return new LiveCloneIterable<Annotation>(this.supportingAnnotations);
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
		return (nestableAnnotation == null) ?
				EmptyListIterator.<NestableAnnotation>instance() :
				new SingleElementListIterator<NestableAnnotation>(nestableAnnotation);
	}

	private NestableAnnotation getSupportingNestableAnnotation(String annotationName) {
		return (NestableAnnotation) this.getSupportingAnnotation(annotationName);
	}

	public Annotation getSupportingAnnotation(String annotationName) {
		return this.selectAnnotationNamed(this.getSupportingAnnotations(), annotationName);
	}

	public Annotation getNonNullSupportingAnnotation(String annotationName) {
		Annotation annotation = this.getSupportingAnnotation(annotationName);
		return (annotation != null) ? annotation : this.buildNullSupportingAnnotation(annotationName);
	}

	abstract Annotation buildNullSupportingAnnotation(String annotationName);

	abstract Annotation buildSupportingAnnotation(IAnnotation jdtAnnotation);

	private boolean annotationIsValidSupportingAnnotation(IAnnotation jdtAnnotation) {
		return CollectionTools.contains(this.validSupportingAnnotationNames(), jdtAnnotation.getElementName());
	}

	abstract ListIterator<String> validSupportingAnnotationNames();

	@SuppressWarnings("unchecked")
	private ContainerAnnotation<NestableAnnotation> getSupportingContainerAnnotation(String annotationName) {
		return (ContainerAnnotation<NestableAnnotation>) this.getSupportingAnnotation(annotationName);
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

	private boolean buildPersistable() {
		return this.adapter.isPersistable();
	}

	public boolean isPersisted() {
		return this.getMappingAnnotation() != null;
	}


	// ********** miscellaneous **********

	IMember getMember() {
		return this.adapter.getMember();
	}

	private Annotation selectAnnotationNamed(Iterable<Annotation> annotations, String annotationName) {
		for (Annotation annotation : annotations) {
			if (annotation.getAnnotationName().equals(annotationName)) {
				return annotation;
			}
		}
		return null;
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

	/**
	 * Strip off the type signature's parameters if present.
	 * Convert to a readable string.
	 */
	static String convertTypeSignatureToTypeName(String typeSignature) {
		return (typeSignature == null) ? null : convertTypeSignatureToTypeName_(typeSignature);
	}

	/**
	 * no null check
	 */
	static String convertTypeSignatureToTypeName_(String typeSignature) {
		return Signature.toString(Signature.getTypeErasure(typeSignature));
	}

	private IAnnotation[] getJdtAnnotations() {
		try {
			return this.adapter.getAnnotations();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return EMPTY_JDT_ANNOTATION_ARRAY;
		}
	}
	private static final IAnnotation[] EMPTY_JDT_ANNOTATION_ARRAY = new IAnnotation[0];


	// ********** IMember adapter **********

	interface Adapter {
		/**
		 * Return the adapter's JDT member (IType, IField, IMethod).
		 */
		IMember getMember();

		/**
		 * Return whether the adapter's member is "persistable"
		 * (i.e. according to the JPA spec the member can be mapped)
		 */
		boolean isPersistable();

		/**
		 * Return the adapter's member's JDT annotations.
		 */
		IAnnotation[] getAnnotations() throws JavaModelException;
	}


	// ********** unsupported JavaResourcePersistentMember implementation **********

	public Annotation setMappingAnnotation(String annotationName) {
		throw new UnsupportedOperationException();
	}

	public Annotation addSupportingAnnotation(String annotationName) {
		throw new UnsupportedOperationException();
	}

	public Annotation addSupportingAnnotation(String annotationName, AnnotationInitializer foo) {
		throw new UnsupportedOperationException();
	}

	public Annotation addSupportingAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		throw new UnsupportedOperationException();
	}

	public void moveSupportingAnnotation(int targetIndex, int sourceIndex, String containerAnnotationName) {
		throw new UnsupportedOperationException();
	}

	public void removeSupportingAnnotation(String annotationName) {
		throw new UnsupportedOperationException();
	}

	public void removeSupportingAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		throw new UnsupportedOperationException();
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public void resolveTypes(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean isFor(String memberName, int occurrence) {
		throw new UnsupportedOperationException();
	}

}
