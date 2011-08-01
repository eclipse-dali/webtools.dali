/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;

/**
 * binary persistent member
 */
abstract class BinaryMember
	extends BinaryAnnotatedElement
	implements JavaResourceMember
{

	private boolean final_;  // 'final' is a reserved word
	private boolean transient_;  // 'transient' is a reserved word
	private boolean public_;  // 'public' is a reserved word
	private boolean static_;  // 'static' is a reserved word


	// ********** construction/initialization **********

	public BinaryMember(JavaResourceNode parent, Adapter adapter) {
		super(parent, adapter);
		this.final_ = this.buildFinal();
		this.transient_ = this.buildTransient();
		this.public_ = this.buildPublic();
		this.static_ = this.buildStatic();
	}


	// ********** updating **********

	@Override
	public void update() {
		super.update();
		this.setFinal(this.buildFinal());
		this.setTransient(this.buildTransient());
		this.setPublic(this.buildPublic());
		this.setStatic(this.buildStatic());
	}


	// ********** simple state **********

	// ***** final
	public boolean isFinal() {
		return this.final_;
	}

	private void setFinal(boolean final_) {
		boolean old = this.final_;
		this.final_ = final_;
		this.firePropertyChanged(FINAL_PROPERTY, old, final_);
	}

	private boolean buildFinal() {
		try {
			return Flags.isFinal(this.getMember().getFlags());
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	// ***** transient
	public boolean isTransient() {
		return this.transient_;
	}

	private void setTransient(boolean transient_) {
		boolean old = this.transient_;
		this.transient_ = transient_;
		this.firePropertyChanged(TRANSIENT_PROPERTY, old, transient_);
	}

	private boolean buildTransient() {
		try {
			return Flags.isTransient(this.getMember().getFlags());
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	// ***** public
	public boolean isPublic() {
		return this.public_;
	}

	private void setPublic(boolean public_) {
		boolean old = this.public_;
		this.public_ = public_;
		this.firePropertyChanged(PUBLIC_PROPERTY, old, public_);
	}

	private boolean buildPublic() {
		try {
			return Flags.isPublic(this.getMember().getFlags());
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	// ***** static
	public boolean isStatic() {
		return this.static_;
	}

	private void setStatic(boolean static_) {
		boolean old = this.static_;
		this.static_ = static_;
		this.firePropertyChanged(STATIC_PROPERTY, old, static_);
	}

	private boolean buildStatic() {
		try {
			return Flags.isStatic(this.getMember().getFlags());
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}


	// ********** miscellaneous **********

	IMember getMember() {
		return this.getAdapter().getElement();
	}

	private Adapter getAdapter() {
		return (Adapter) this.adapter;
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


	// ********** IMember adapter **********

	interface Adapter extends BinaryAnnotatedElement.Adapter {
		/**
		 * Return the adapter's JDT member (IType, IField, IMethod).
		 */
		IMember getElement();

	}


	// ********** unsupported JavaResourceMember implementation **********

	public Annotation setPrimaryAnnotation(String primaryAnnotationName, Iterable<String> supportingAnnotationNames) {
		throw new UnsupportedOperationException();
	}

	public void resolveTypes(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean isFor(String memberName, int occurrence) {
		throw new UnsupportedOperationException();
	}
}
