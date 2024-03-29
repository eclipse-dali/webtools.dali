/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.jdt.Member;

/**
 * Java source member (annotations, modifiers)
 */
abstract class SourceMember<M extends Member>
	extends SourceAnnotatedElement<M>
	implements JavaResourceMember
{
	boolean final_;  // 'final' is a reserved word

	boolean transient_;  // 'transient' is a reserved word

	boolean public_;  // 'public' is a reserved word

	boolean static_;  // 'static' is a reserved word

	boolean protected_; // 'protected' is a reserved word

	// ********** construction/initialization **********

	SourceMember(JavaResourceModel parent, M member) {
		super(parent, member);
	}

	/**
	 * Subclasses are responsible for calling this initialize method.
	 */
	protected void initialize(IBinding binding) {
		this.final_ = this.buildFinal(binding);
		this.transient_ = this.buildTransient(binding);
		this.public_ = this.buildPublic(binding);
		this.static_ = this.buildStatic(binding);
		this.protected_ = this.buildProtected(binding);
	}

	/**
	 * Subclasses are responsible for calling this initialize method.
	 */
	protected void synchronizeWith(IBinding binding) {
		this.syncFinal(this.buildFinal(binding));
		this.syncTransient(this.buildTransient(binding));
		this.syncPublic(this.buildPublic(binding));
		this.syncStatic(this.buildStatic(binding));
		this.syncProtected(this.buildProtected(binding));
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
	
	// ***** protected
	public boolean isProtected() {
		return this.protected_;
	}

	private void syncProtected(boolean astProtected) {
		boolean old = this.protected_;
		this.protected_ = astProtected;
		this.firePropertyChanged(PROTECTED_PROPERTY, old, astProtected);
	}

	private boolean buildProtected(IBinding binding) {
		return (binding == null) ? false : Modifier.isProtected(binding.getModifiers());
	}

	// ********** miscellaneous **********

	public boolean isFor(String memberName, int occurrence) {
		return this.annotatedElement.matches(memberName, occurrence);
	}

	@Override
	public Annotation setPrimaryAnnotation(String primaryAnnotationName, Iterable<String> supportingAnnotationNames) {
		return super.setPrimaryAnnotation(primaryAnnotationName, supportingAnnotationNames);
	}

	public boolean isPublicOrProtected() {
		return this.isPublic() || this.isProtected();
	}
}
