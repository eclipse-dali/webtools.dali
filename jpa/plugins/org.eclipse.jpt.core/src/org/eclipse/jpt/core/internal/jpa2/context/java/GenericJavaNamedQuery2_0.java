/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaQuery;
import org.eclipse.jpt.core.jpa2.context.LockModeType2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaNamedQuery2_0;
import org.eclipse.jpt.core.jpa2.resource.java.NamedQuery2_0Annotation;
import org.eclipse.jpt.core.resource.java.NamedQueryAnnotation;

/**
 *  GenericJavaNamedQuery2_0
 */
public class GenericJavaNamedQuery2_0 extends AbstractJavaQuery 
	implements JavaNamedQuery2_0
{
	private LockModeType2_0 specifiedLockMode;
	private LockModeType2_0 defaultLockMode;

	public GenericJavaNamedQuery2_0(JavaJpaContextNode parent) {
		super(parent);
	}

	// ********** NamedQuery2_0 implementation **********

	public LockModeType2_0 getLockMode() {
		return (this.specifiedLockMode != null) ? this.specifiedLockMode : this.defaultLockMode;
	}

	public LockModeType2_0 getSpecifiedLockMode() {
		return this.specifiedLockMode;
	}

	public void setSpecifiedLockMode(LockModeType2_0 lockMode) {
		LockModeType2_0 old = this.specifiedLockMode;
		this.specifiedLockMode = lockMode;
		this.getResourceQuery().setLockMode(LockModeType2_0.toJavaResourceModel(lockMode));
		this.firePropertyChanged(SPECIFIED_LOCK_MODE_PROPERTY, old, lockMode);
	}
	
	protected void setSpecifiedLockMode_(LockModeType2_0 lockMode) {
		LockModeType2_0 old = this.specifiedLockMode;
		this.specifiedLockMode = lockMode;
		this.firePropertyChanged(SPECIFIED_LOCK_MODE_PROPERTY, old, lockMode);
	}

	public LockModeType2_0 getDefaultLockMode() {
		return this.defaultLockMode;
	}

	protected void setDefaultLockMode(LockModeType2_0 lockMode) {
		LockModeType2_0 old = this.defaultLockMode;
		this.defaultLockMode = lockMode;
		firePropertyChanged(DEFAULT_LOCK_MODE_PROPERTY, old, lockMode);
	}

	protected LockModeType2_0 buildDefaultLockMode() {
		return LockModeType2_0.NONE;
	}

	// ********** resource => context **********

	public void initialize(NamedQueryAnnotation resourceQuery) {
		super.initialize(resourceQuery);
		this.defaultLockMode = this.buildDefaultLockMode();
		this.specifiedLockMode = this.getResourceQueryLockMode(resourceQuery);
	}

	public void update(NamedQueryAnnotation resourceQuery) {
		super.update(resourceQuery);
		this.setSpecifiedLockMode_(this.getResourceQueryLockMode(resourceQuery));
	}
	
	@Override
	protected NamedQuery2_0Annotation getResourceQuery() {
		return (NamedQuery2_0Annotation) super.getResourceQuery();
	}
	
	protected LockModeType2_0 getResourceQueryLockMode(NamedQueryAnnotation resourceQuery) {
		return LockModeType2_0.fromJavaResourceModel(
					((NamedQuery2_0Annotation)resourceQuery).getLockMode());
	}
	
}
