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
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;

/**
 * binary persistent member
 */
abstract class BinaryPersistentMember
	extends BinaryNode
	implements JavaResourcePersistentMember
{
	/** JDT member adapter */
	final Adapter adapter;
	
	/** annotations */
	final Vector<Annotation> annotations = new Vector<Annotation>();
	
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
		if (this.annotationIsValid(jdtAnnotation)) {
			this.annotations.add(this.buildAnnotation(jdtAnnotation));
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
				getContainerAnnotation(containerAnnotationName);
		if (containerAnnotation != null) {
			return containerAnnotation.nestedAnnotations();
		}
		NestableAnnotation nestableAnnotation = 
				getNestableAnnotation(nestableAnnotationName);
		return (nestableAnnotation == null) ?
				EmptyListIterator.<NestableAnnotation>instance() :
				new SingleElementIterator<NestableAnnotation>(nestableAnnotation);
	}
	
	private NestableAnnotation getNestableAnnotation(String annotationName) {
		return (NestableAnnotation) this.getAnnotation(annotationName);
	}
	
	@SuppressWarnings("unchecked")
	private ContainerAnnotation<NestableAnnotation> getContainerAnnotation(String annotationName) {
		return (ContainerAnnotation<NestableAnnotation>) this.getAnnotation(annotationName);
	}
	
	private boolean annotationIsValid(IAnnotation jdtAnnotation) {
		return CollectionTools.contains(
				this.validAnnotationNames(), jdtAnnotation.getElementName());
	}
	
	abstract Iterator<String> validAnnotationNames();
	
	abstract Annotation buildAnnotation(IAnnotation jdtAnnotation);
	
	abstract Annotation buildNullAnnotation(String annotationName);
	
	
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

	public boolean isAnnotated() {
		return ! this.annotations.isEmpty();
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
	
	public Annotation addAnnotation(String annotationName) {
		throw new UnsupportedOperationException();
	}
	
	public Annotation addAnnotation(String annotationName, AnnotationInitializer foo) {
		throw new UnsupportedOperationException();
	}
	
	public NestableAnnotation addAnnotation(
			int index, String nestableAnnotationName, String containerAnnotationName) {
		throw new UnsupportedOperationException();
	}
	
	public void moveAnnotation(
			int targetIndex, int sourceIndex, String containerAnnotationName) {
		throw new UnsupportedOperationException();
	}
	
	public void removeAnnotation(String annotationName) {
		throw new UnsupportedOperationException();
	}
	
	public void removeAnnotation(
			int index, String nestableAnnotationName, String containerAnnotationName) {
		throw new UnsupportedOperationException();
	}
	
	public Annotation setPrimaryAnnotation(
			String primaryAnnotationName, String[] supportingAnnotationNames) {
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
