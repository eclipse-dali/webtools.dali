/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import java.util.Iterator;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * binary persistent member
 */
abstract class BinaryPersistentMember
	extends BinaryAnnotatedElement
	implements JavaResourcePersistentMember
{

	boolean persistable;

	private boolean final_;  // 'final' is a reserved word


	// ********** construction/initialization **********

	public BinaryPersistentMember(JavaResourceNode parent, Adapter adapter) {
		super(parent, adapter);
		this.persistable = this.buildPersistable();
		this.final_ = this.buildFinal();
	}


	private Adapter getAdapter() {
		return (Adapter) this.adapter;
	}

	// ********** updating **********

	@Override
	public void update() {
		super.update();
		this.setPersistable(this.buildPersistable());
		this.setFinal(this.buildFinal());
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
		return this.getAdapter().isPersistable();
	}

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
			JptCorePlugin.log(ex);
			return false;
		}
	}


	// ********** miscellaneous **********

	IMember getMember() {
		return (IMember) this.adapter.getElement();
	}

	/**
	 * convenience method
	 */
	<T extends JavaResourcePersistentMember> Iterator<T> persistableMembers(Iterator<T> members) {
		@SuppressWarnings("unchecked")
		Filter<T> filter = (Filter<T>) PERSISTABLE_MEMBER_FILTER;
		return new FilteringIterator<T>(members, filter);
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

		/**
		 * Return whether the adapter's member is "persistable"
		 * (i.e. according to the JPA spec the member can be mapped)
		 */
		boolean isPersistable();
	}


	// ********** unsupported JavaResourceAnnotatedElement implementation **********

	@Override
	public Annotation addAnnotation(String annotationName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NestableAnnotation addAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void moveAnnotation(int targetIndex, int sourceIndex, String containerAnnotationName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAnnotation(String annotationName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAnnotation(int index, String nestableAnnotationName, String containerAnnotationName) {
		throw new UnsupportedOperationException();
	}

	public Annotation setPrimaryAnnotation(String primaryAnnotationName, Iterable<String> supportingAnnotationNames) {
		throw new UnsupportedOperationException();
	}

	@Override
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
