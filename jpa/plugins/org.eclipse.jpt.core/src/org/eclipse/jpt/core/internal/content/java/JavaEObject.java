/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java;

import java.util.Iterator;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.IJpaSourceObject;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.JpaEObject;
import org.eclipse.jpt.utility.internal.Filter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaEObject()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class JavaEObject extends JpaEObject
	implements IJpaSourceObject
{
	protected JavaEObject() {
		super();
		this.eAdapters().add(this.buildListener());
	}

	protected Adapter buildListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				JavaEObject.this.notifyChanged(notification);
			}
		};
	}

	protected void notifyChanged(Notification notification) {
	// do nothing by default
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaPackage.Literals.JAVA_EOBJECT;
	}

	public IJpaFile getJpaFile() {
		return this.getRoot().getJpaFile();
	}

	public IJpaRootContentNode getRoot() {
		return ((JavaEObject) this.eContainer()).getRoot();
	}

	@Override
	public IResource getResource() {
		return this.getJpaFile().getResource();
	}

	/**
	 * All features are "insiginificant". We do a resynch of our java model
	 * when it gets notification from the jdt java model. We had problems
	 * with the java model being in a bad state while doing our resynch.
	 */
	@Override
	protected boolean featureIsInsignificant(int featureId) {
		return true;
	}

	/**
	 * Convenience method. If the specified element text range is null
	 * return the Java object's text range instead (which is usually the
	 * annotation's text range).
	 */
	protected ITextRange elementTextRange(ITextRange elementTextRange) {
		return (elementTextRange != null) ? elementTextRange : this.validationTextRange();
	}

	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		if (this.isConnected()) {
			Iterator<String> result = this.connectedCandidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * called if the database is connected, allowing us to get candidates
	 * from the various database tables etc.
	 */
	public Iterator<String> connectedCandidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		return null;
	}

	/**
	 * Convenience method. Return whether element's text range is not
	 * null (meaning the element exists) and the specified position touches it.
	 */
	protected boolean elementTouches(ITextRange elementTextRange, int pos) {
		return (elementTextRange != null) && elementTextRange.touches(pos);
	}
}
