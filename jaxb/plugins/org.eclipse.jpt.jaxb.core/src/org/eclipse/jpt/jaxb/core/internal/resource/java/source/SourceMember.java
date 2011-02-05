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
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jaxb.core.resource.java.Annotation;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceNode;

/**
 * Java source member (annotations, "persistable")
 */
abstract class SourceMember<M extends Member>
	extends SourceAnnotatedElement<M>
	implements JavaResourceMember
{

	boolean final_;  // 'final' is a reserved word

	boolean transient_;  // 'transient' is a reserved word

	boolean public_;  // 'public' is a reserved word

	boolean static_;  // 'static' is a reserved word


	// ********** construction/initialization **********

	SourceMember(JavaResourceNode parent, M member) {
		super(parent, member);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		IBinding binding = this.annotatedElement.getBinding(astRoot);
		this.final_ = this.buildFinal(binding);
		this.transient_ = this.buildTransient(binding);
		this.public_ = this.buildPublic(binding);
		this.static_ = this.buildStatic(binding);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		IBinding binding = this.annotatedElement.getBinding(astRoot);
		this.syncFinal(this.buildFinal(binding));
		this.syncTransient(this.buildTransient(binding));
		this.syncPublic(this.buildPublic(binding));
		this.syncStatic(this.buildStatic(binding));
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

	// ***** transient
	public boolean isTransient() {
		return this.transient_;
	}

	private void syncTransient(boolean astTransient) {
		boolean old = this.transient_;
		this.transient_ = astTransient;
		this.firePropertyChanged(TRANSIENT_PROPERTY, old, astTransient);
	}

	private boolean buildTransient(IBinding binding) {
		return (binding == null) ? false : Modifier.isTransient(binding.getModifiers());
	}

	// ***** public
	public boolean isPublic() {
		return this.public_;
	}

	private void syncPublic(boolean astPublic) {
		boolean old = this.public_;
		this.public_ = astPublic;
		this.firePropertyChanged(PUBLIC_PROPERTY, old, astPublic);
	}

	private boolean buildPublic(IBinding binding) {
		return (binding == null) ? false : Modifier.isPublic(binding.getModifiers());
	}

	// ***** static
	public boolean isStatic() {
		return this.static_;
	}

	private void syncStatic(boolean astStatic) {
		boolean old = this.static_;
		this.static_ = astStatic;
		this.firePropertyChanged(STATIC_PROPERTY, old, astStatic);
	}

	private boolean buildStatic(IBinding binding) {
		return (binding == null) ? false : Modifier.isStatic(binding.getModifiers());
	}


	// ********** miscellaneous **********

	public boolean isFor(String memberName, int occurrence) {
		return this.annotatedElement.matches(memberName, occurrence);
	}

	public void resolveTypes(CompilationUnit astRoot) {
	}

}
