/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import java.util.HashSet;
import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;

/**
 * Java source persistent member (annotations, "persistable")
 */
abstract class SourcePersistentMember<M extends Member>
	extends SourceAnnotatedElement<M>
	implements JavaResourcePersistentMember
{
	/**
	 * We can cache this setting because its value is not affected by any API
	 * calls - it can only be affected by changes to the Java source.
	 * @see org.eclipse.jpt.common.core.internal.utility.jdt.JPTTools#typeIsPersistable(org.eclipse.jpt.common.core.internal.utility.jdt.JPTTools.TypeAdapter)
	 */
	boolean persistable;

	boolean final_;  // 'final' is a reserved word


	// ********** construction/initialization **********

	SourcePersistentMember(JavaResourceNode parent, M member) {
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
		HashSet<String> annotationNames = CollectionTools.set(supportingAnnotationNames);
		if (primaryAnnotationName != null) {
			annotationNames.add(primaryAnnotationName);
		}
		for (Annotation annotation : this.getAnnotations()) {
			if ( ! annotationNames.contains(annotation.getAnnotationName())) {
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
	<T extends JavaResourcePersistentMember> Iterator<T> persistableMembers(Iterator<T> members) {
		@SuppressWarnings("unchecked")
		Filter<T> filter = (Filter<T>) PERSISTABLE_MEMBER_FILTER;
		return new FilteringIterator<T>(members, filter);
	}
}
