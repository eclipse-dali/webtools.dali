/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.common.utility.internal.iterators.SingleElementIterator;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.resource.java.Annotation;
import org.eclipse.jpt.jpa.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.NestableAnnotation;

/**
 * binary annotated element
 */
abstract class BinaryAnnotatedElement
	extends BinaryNode
	implements JavaResourceAnnotatedElement
{
	/** JDT annotated element adapter */
	final Adapter adapter;

	/** annotations */
	final Vector<Annotation> annotations = new Vector<Annotation>();

	/**
	 * these are built as needed
	 */
	private final HashMap<String, Annotation> nullAnnotationsCache = new HashMap<String, Annotation>();


	// ********** construction/initialization **********

	public BinaryAnnotatedElement(JavaResourceNode parent, Adapter adapter) {
		super(parent);
		this.adapter = adapter;
		this.initializeAnnotations();
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
		return (annotation != null) ? annotation : this.getNullAnnotation(annotationName);
	}

	private synchronized Annotation getNullAnnotation(String annotationName) {
		Annotation annotation = this.nullAnnotationsCache.get(annotationName);
		if (annotation == null) {
			annotation = this.buildNullAnnotation(annotationName);
			this.nullAnnotationsCache.put(annotationName, annotation);
		}
		return annotation;
	}

	public Iterator<NestableAnnotation> annotations(String nestableAnnotationName, String containerAnnotationName) {
		ContainerAnnotation<NestableAnnotation> containerAnnotation = this.getContainerAnnotation(containerAnnotationName);
		if (containerAnnotation != null) {
			return containerAnnotation.getNestedAnnotations().iterator();
		}
		NestableAnnotation nestableAnnotation = this.getNestableAnnotation(nestableAnnotationName);
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
		return CollectionTools.contains(this.validAnnotationNames(), jdtAnnotation.getElementName());
	}

	abstract Iterator<String> validAnnotationNames();

	abstract Annotation buildAnnotation(IAnnotation jdtAnnotation);

	abstract Annotation buildNullAnnotation(String annotationName);


	// ********** simple state **********
	
	public boolean isAnnotated() {
		return ! this.annotations.isEmpty();
	}


	// ********** misc **********

	IJavaElement getAnnotatedElement() {
		return this.adapter.getElement();
	}

	private Annotation selectAnnotationNamed(Iterable<Annotation> annotationList, String annotationName) {
		for (Annotation annotation : annotationList) {
			if (annotation.getAnnotationName().equals(annotationName)) {
				return annotation;
			}
		}
		return null;
	}

	private IAnnotation[] getJdtAnnotations() {
		try {
			return this.adapter.getAnnotations();
		} catch (JavaModelException ex) {
			JptJpaCorePlugin.log(ex);
			return EMPTY_JDT_ANNOTATION_ARRAY;
		}
	}
	private static final IAnnotation[] EMPTY_JDT_ANNOTATION_ARRAY = new IAnnotation[0];


	// ********** IJavaElement adapter **********

	interface Adapter {
		/**
		 * Return the adapter's JDT element (IPackageFragment, IType, IField, IMethod).
		 */
		IJavaElement getElement();

		/**
		 * Return the adapter's element's JDT annotations.
		 */
		IAnnotation[] getAnnotations() throws JavaModelException;
	}


	// ********** unsupported JavaResourcePersistentMember implementation **********

	public Annotation addAnnotation(String annotationName) {
		throw new UnsupportedOperationException();
	}

	public NestableAnnotation addAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		throw new UnsupportedOperationException();
	}

	public void moveAnnotation(int targetIndex, int sourceIndex, String containerAnnotationName) {
		throw new UnsupportedOperationException();
	}

	public void removeAnnotation(String annotationName) {
		throw new UnsupportedOperationException();
	}

	public void removeAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		throw new UnsupportedOperationException();
	}

	public void addStandAloneAnnotation(NestableAnnotation standAloneAnnotation) {
		throw new UnsupportedOperationException();
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
}
