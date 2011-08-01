/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;

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
	 * Annotation containers keyed on nestable annotation name.
	 * This is used to store annotations that can be both standalone and nested
	 * and are moved back and forth between the 2.
	 */
	final Map<String, AnnotationContainer> annotationContainers = new HashMap<String, AnnotationContainer>();

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
		String jdtAnnotationName = jdtAnnotation.getElementName();
		if (this.annotationIsValid(jdtAnnotationName)) {
			this.annotations.add(this.getAnnotationProvider().buildAnnotation(this, jdtAnnotation));
		}
		if (this.annotationIsValidNestable(jdtAnnotationName)) {
			AnnotationContainer container = new AnnotationContainer(jdtAnnotation);
			this.annotationContainers.put(jdtAnnotationName, container);
		}
		if (this.annotationIsValidContainer(jdtAnnotationName)) {
			String nestableAnnotationName = this.getNestableAnnotationName(jdtAnnotationName);
			AnnotationContainer container = new AnnotationContainer(jdtAnnotation);
			this.annotationContainers.put(nestableAnnotationName, container);
		}
	}

	private boolean annotationIsValid(String annotationName) {
		return CollectionTools.contains(this.getValidAnnotationNames(), annotationName);
	}

	private boolean annotationIsValidContainer(String annotationName) {
		return CollectionTools.contains(this.getValidContainerAnnotationNames(), annotationName);
	}

	private boolean annotationIsValidNestable(String annotationName) {
		return CollectionTools.contains(this.getValidNestableAnnotationNames(), annotationName);
	}

	Iterable<String> getValidAnnotationNames() {
		return this.getAnnotationProvider().getAnnotationNames();
	}

	Iterable<String> getValidContainerAnnotationNames() {
		return this.getAnnotationProvider().getContainerAnnotationNames();
	}

	Iterable<String> getValidNestableAnnotationNames() {
		return this.getAnnotationProvider().getNestableAnnotationNames();
	}


	private String getNestableAnnotationName(String containerAnnotationName) {
		return getAnnotationProvider().getNestableAnnotationName(containerAnnotationName);
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

	public Iterable<Annotation> getAnnotations() {
		return new LiveCloneIterable<Annotation>(this.annotations);
	}

	public int getAnnotationsSize() {
		return this.annotations.size();
	}

	protected Iterable<NestableAnnotation> getNestableAnnotations() {
		return new CompositeIterable<NestableAnnotation>(this.getNestableAnnotationLists());
	}

	private Iterable<Iterable<NestableAnnotation>> getNestableAnnotationLists() {
		return new TransformationIterable<AnnotationContainer, Iterable<NestableAnnotation>>(this.annotationContainers.values()) {
			@Override
			protected Iterable<NestableAnnotation> transform(AnnotationContainer container) {
				return container.getNestedAnnotations();
			}
		};
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

	private Annotation buildNullAnnotation(String annotationName) {
		return getAnnotationProvider().buildNullAnnotation(this, annotationName);
	}

	// ********** nestable annotations **********

	public ListIterable<NestableAnnotation> getAnnotations(String nestableAnnotationName) {
		AnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		return container != null ? container.getNestedAnnotations() : EmptyListIterable.<NestableAnnotation> instance();
	}


	public int getAnnotationsSize(String nestableAnnotationName) {
		AnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		return container == null ? 0 : container.getNestedAnnotationsSize();
	}

	public NestableAnnotation getAnnotation(int index, String nestableAnnotationName) {
		AnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		return container == null ? null : container.nestedAnnotationAt(index);
	}

	// ********** simple state **********
	
	public boolean isAnnotated() {
		return ! this.annotations.isEmpty();
	}

	public boolean isAnnotatedWith(Iterable<String> annotationNames) {
		for (Annotation annotation : this.getAnnotations()) {
			if (CollectionTools.contains(annotationNames, annotation.getAnnotationName())) {
				return true;
			}
		}
		for (Annotation annotation : this.getNestableAnnotations()) {
			if (CollectionTools.contains(annotationNames, annotation.getAnnotationName())) {
				return true;
			}
		}
		return false;
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
			JptCommonCorePlugin.log(ex);
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

	public NestableAnnotation addAnnotation(int index, String nestableAnnotationName) {
		throw new UnsupportedOperationException();
	}

	public void moveAnnotation(int targetIndex, int sourceIndex, String nestableAnnotationName) {
		throw new UnsupportedOperationException();
	}

	public void removeAnnotation(String annotationName) {
		throw new UnsupportedOperationException();
	}

	public void removeAnnotation(int index, String nestableAnnotationName) {
		throw new UnsupportedOperationException();
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	private static final IMemberValuePair[] EMPTY_MEMBER_VALUE_PAIR_ARRAY = new IMemberValuePair[0];

	class AnnotationContainer
	{
		private final IAnnotation containerAnnotation;

		/** annotations */
		final Vector<NestableAnnotation> nestedAnnotations = new Vector<NestableAnnotation>();

		protected AnnotationContainer(IAnnotation containerAnnotation) {
			super();
			this.containerAnnotation = containerAnnotation;
		}		

		protected void initializeNestedAnnotations() {
			int index = 0;
			for(IMemberValuePair valuePair : this.getJdtMemberValuePairs()) {
				IAnnotation nestedAnnotation = (IAnnotation) valuePair.getValue();
				this.nestedAnnotations.add(getAnnotationProvider().buildAnnotation(BinaryAnnotatedElement.this, nestedAnnotation, index++));
			}
		}

		public ListIterable<NestableAnnotation> getNestedAnnotations() {
			return new LiveCloneListIterable<NestableAnnotation>(this.nestedAnnotations);
		}

		public int getNestedAnnotationsSize() {
			return this.nestedAnnotations.size();
		}

		public NestableAnnotation nestedAnnotationAt(int index) {
			return this.nestedAnnotations.get(index);
		}

		private IMemberValuePair[] getJdtMemberValuePairs() {
			try {
				return this.containerAnnotation.getMemberValuePairs();
			} catch (JavaModelException ex) {
				JptCommonCorePlugin.log(ex);
				return EMPTY_MEMBER_VALUE_PAIR_ARRAY;
			}
		}
	}
}
