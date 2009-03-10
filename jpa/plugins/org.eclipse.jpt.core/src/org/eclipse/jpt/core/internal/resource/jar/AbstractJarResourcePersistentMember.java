/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.jar;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.resource.jar.JarResourceNode;
import org.eclipse.jpt.core.resource.jar.JarResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

/**
 * JAR persistent member
 */
public abstract class AbstractJarResourcePersistentMember
	extends AbstractJarResourceNode
	implements JarResourcePersistentMember
{
	/** JDT member adapter */
	private final Adapter adapter;

	/** mapping annotations */
	private final Vector<Annotation> mappingAnnotations = new Vector<Annotation>();

	/** supporting annotations */
	private final Vector<Annotation> supportingAnnotations = new Vector<Annotation>();

	private boolean persistable;


	// ********** construction/initialization **********

	public AbstractJarResourcePersistentMember(JarResourceNode parent, Adapter adapter) {
		super(parent);
		this.adapter = adapter;
		this.initializeAnnotations();
		this.persistable = this.buildPersistable();
	}

	protected void initializeAnnotations() {
		for (IAnnotation annotation : this.getJdtAnnotations()) {
			this.addAnnotation(annotation);
		}
	}

	protected IAnnotation[] getJdtAnnotations() {
		try {
			return this.adapter.getAnnotations();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return EMPTY_JDT_ANNOTATION_ARRAY;
		}
	}
	protected static final IAnnotation[] EMPTY_JDT_ANNOTATION_ARRAY = new IAnnotation[0];

	// TODO
	protected void addAnnotation(IAnnotation jdtAnnotation) {
//		if (this.annotationIsValidSupportingAnnotation(jdtAnnotation)) {
//			this.supportingAnnotations.add(this.buildSupportingAnnotation(jdtAnnotation));
//		} else if (this.annotationIsValidMappingAnnotation(jdtAnnotation)) {
//			this.mappingAnnotations.add(this.buildMappingAnnotation(jdtAnnotation));
//		}
	}


	// ********** mapping annotations **********

	public Iterator<Annotation> mappingAnnotations() {
		return new CloneIterator<Annotation>(this.mappingAnnotations);
	}

	public int mappingAnnotationsSize() {
		return this.mappingAnnotations.size();
	}

	public JavaResourceNode getMappingAnnotation() {
		synchronized (this.mappingAnnotations) {
			return this.getMappingAnnotation_();
		}
	}

	protected Annotation getMappingAnnotation_() {
		for (ListIterator<String> stream = this.validMappingAnnotationNames(); stream.hasNext();) {
			Annotation annotation = this.getMappingAnnotation_(stream.next());
			if (annotation != null) {
				return annotation;
			}
		}
		return null;
	}

	/**
	 * use this method when 'mappingAnnotations' is synchronized
	 */
	protected Annotation getMappingAnnotation_(String annotationName) {
		return this.getAnnotation(this.mappingAnnotations, annotationName);
	}

	public JavaResourceNode getMappingAnnotation(String annotationName) {
		return this.getAnnotation(this.mappingAnnotations(), annotationName);
	}

	public JavaResourceNode getNullMappingAnnotation(String annotationName) {
		return (annotationName == null) ? null : this.buildNullMappingAnnotation(annotationName);
	}

	protected abstract Annotation buildNullMappingAnnotation(String annotationName);

	protected boolean annotationIsValidMappingAnnotation(IAnnotation jdtAnnotation) {
		return CollectionTools.contains(this.validMappingAnnotationNames(), jdtAnnotation.getElementName());
	}

	protected abstract ListIterator<String> validMappingAnnotationNames();

	protected abstract Annotation buildMappingAnnotation(IAnnotation jdtAnnotation);


	// ********** supporting annotations **********

	public Iterator<Annotation> supportingAnnotations() {
		return new CloneIterator<Annotation>(this.supportingAnnotations);
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
		return (nestableAnnotation == null) ? EmptyListIterator.<NestableAnnotation>instance() : new SingleElementListIterator<NestableAnnotation>(nestableAnnotation);
	}

	protected NestableAnnotation getSupportingNestableAnnotation(String annotationName) {
		return (NestableAnnotation) this.getSupportingAnnotation(annotationName);
	}

	public Annotation getSupportingAnnotation(String annotationName) {
		return this.getAnnotation(this.supportingAnnotations(), annotationName);
	}

	public JavaResourceNode getNonNullSupportingAnnotation(String annotationName) {
		Annotation annotation = this.getSupportingAnnotation(annotationName);
		return (annotation != null) ? annotation : this.buildNullSupportingAnnotation(annotationName);
	}

	protected abstract Annotation buildNullSupportingAnnotation(String annotationName);

	protected abstract Annotation buildSupportingAnnotation(IAnnotation jdtAnnotation);

	protected boolean annotationIsValidSupportingAnnotation(IAnnotation jdtAnnotation) {
		return CollectionTools.contains(this.validSupportingAnnotationNames(), jdtAnnotation.getElementName());
	}

	protected abstract ListIterator<String> validSupportingAnnotationNames();

	@SuppressWarnings("unchecked")
	protected ContainerAnnotation<NestableAnnotation> getSupportingContainerAnnotation(String annotationName) {
		return (ContainerAnnotation<NestableAnnotation>) this.getSupportingAnnotation(annotationName);
	}


	// ********** simple state **********

	public boolean isPersistable() {
		return this.persistable;
	}

	protected void setPersistable(boolean persistable) {
		boolean old = this.persistable;
		this.persistable = persistable;
		this.firePropertyChanged(PERSISTABLE_PROPERTY, old, persistable);
	}

	protected boolean buildPersistable() {
		return this.adapter.isPersistable();
	}

	public boolean isPersisted() {
		return this.getMappingAnnotation() != null;
	}


	// ********** miscellaneous **********

	protected Adapter getAdapter() {
		return this.adapter;
	}

	public IMember getMember() {
		return this.adapter.getMember();
	}

	protected Annotation getAnnotation(Iterable<Annotation> annotations, String annotationName) {
		return this.getAnnotation(annotations.iterator(), annotationName);
	}

	protected Annotation getAnnotation(Iterator<Annotation> annotations, String annotationName) {
		while (annotations.hasNext()) {
			Annotation annotation = annotations.next();
			if (annotation.getAnnotationName().equals(annotationName)) {
				return annotation;
			}
		}
		return null;
	}

	protected <T extends JavaResourcePersistentMember> Iterator<T> persistableMembers(Iterator<T> members) {
		return new FilteringIterator<T, T>(members) {
			@Override
			protected boolean accept(T m) {
				return m.isPersistable();
			}
		};
	}


	// ********** updating **********

	@Override
	public void update() {
		super.update();
		// TODO
	}


	// ********** IMember adapter **********

	protected interface Adapter {
		IMember getMember();
		boolean isPersistable();
		IAnnotation[] getAnnotations() throws JavaModelException;
	}

	// ============== TODO remove... ========================
	public void setMappingAnnotation(String annotationName) {
		throw new UnsupportedOperationException();
	}

	public JavaResourceNode addSupportingAnnotation(String annotationName) {
		throw new UnsupportedOperationException();
	}

	public JavaResourceNode addSupportingAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
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
