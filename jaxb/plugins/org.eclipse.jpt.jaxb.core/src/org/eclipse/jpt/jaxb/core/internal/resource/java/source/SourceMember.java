/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import java.util.ArrayList;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.jaxb.core.resource.java.Annotation;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;

/**
 * Java source member (annotations, "persistable")
 */
abstract class SourceMember<M extends Member>
	extends SourceAnnotatedElement<M>
	implements JavaResourceMember
{

	boolean persistable;

	boolean final_;  // 'final' is a reserved word


	// ********** construction/initialization **********

	SourceMember(JavaResourceNode parent, M member) {
		super(parent, member);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.persistable = this.buildPersistable(astRoot);
		IBinding binding = this.annotatedElement.getBinding(astRoot);
		this.final_ = this.buildFinal(binding);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncPersistable(this.buildPersistable(astRoot));
		IBinding binding = this.annotatedElement.getBinding(astRoot);
		this.syncFinal(this.buildFinal(binding));
	}


	// ********** annotations **********

	public Annotation setPrimaryAnnotation(String primaryAnnotationName, Iterable<String> supportingAnnotationNames) {
		ArrayList<String> annotationNames = new ArrayList<String>();
		CollectionTools.addAll(annotationNames, supportingAnnotationNames);
		if (primaryAnnotationName != null) {
			annotationNames.add(primaryAnnotationName);
		}
		for (Annotation annotation : this.getAnnotations()) {
			if ( ! CollectionTools.contains(annotationNames, annotation.getAnnotationName())) {
				this.annotations.remove(annotation);
				annotation.removeAnnotation();
			}
		}
		Annotation newPrimaryAnnotation = null;
		if ((primaryAnnotationName != null) && (this.getAnnotation(primaryAnnotationName) == null)) {
			newPrimaryAnnotation = this.buildAnnotation(primaryAnnotationName);
			this.annotations.add(newPrimaryAnnotation);
			newPrimaryAnnotation.newAnnotation();
		}
		// fire collection change event after all annotation changes are done
		this.fireCollectionChanged(ANNOTATIONS_COLLECTION, this.annotations);
		return newPrimaryAnnotation;
	}


	// ********** persistable **********

	public boolean isPersistable() {
		return this.persistable;
	}

	private void syncPersistable(boolean astPersistable) {
		boolean old = this.persistable;
		this.persistable = astPersistable;
		this.firePropertyChanged(PERSISTABLE_PROPERTY, old, astPersistable);
	}

	private boolean buildPersistable(CompilationUnit astRoot) {
		return this.annotatedElement.isPersistable(astRoot);
	}

	// ***** final
	public boolean isFinal() {
		return this.final_;
	}

	private void syncFinal(boolean astFinal) {
		boolean old = this.final_;
		this.final_ = astFinal;
		this.firePropertyChanged(FINAL_PROPERTY, old, astFinal);
	}

	private boolean buildFinal(IBinding binding) {
		return (binding == null) ? false : Modifier.isFinal(binding.getModifiers());
	}


	// ********** miscellaneous **********

	public boolean isFor(String memberName, int occurrence) {
		return this.annotatedElement.matches(memberName, occurrence);
	}

	public void resolveTypes(CompilationUnit astRoot) {
		this.syncPersistable(this.buildPersistable(astRoot));
	}

	/**
	 * convenience method
	 */
	<T extends JavaResourceMember> Iterable<T> getPersistableMembers(Iterable<T> members) {
		return new FilteringIterable<T>(members) {
			@Override
			protected boolean accept(T m) {
				return m.isPersistable();
			}
		};
	}
}
