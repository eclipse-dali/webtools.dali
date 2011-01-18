/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
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

/**
 * JPA 2.0
 * Java named query
 */
public class GenericJavaNamedQuery2_0
	extends AbstractJavaQuery<NamedQuery2_0Annotation>
	implements JavaNamedQuery2_0
{
	protected LockModeType2_0 specifiedLockMode;
	protected LockModeType2_0 defaultLockMode;


	public GenericJavaNamedQuery2_0(JavaJpaContextNode parent, NamedQuery2_0Annotation queryAnnotation) {
		super(parent, queryAnnotation);
		this.specifiedLockMode = this.buildSpecifiedLockMode();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedLockMode_(this.buildSpecifiedLockMode());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultLockMode(this.buildDefaultLockMode());
	}


	// ********** lock mode **********

	public LockModeType2_0 getLockMode() {
		return (this.specifiedLockMode != null) ? this.specifiedLockMode : this.defaultLockMode;
	}

	public LockModeType2_0 getSpecifiedLockMode() {
		return this.specifiedLockMode;
	}

	public void setSpecifiedLockMode(LockModeType2_0 lockMode) {
		this.queryAnnotation.setLockMode(LockModeType2_0.toJavaResourceModel(lockMode));
		this.setSpecifiedLockMode_(lockMode);
	}

	protected void setSpecifiedLockMode_(LockModeType2_0 lockMode) {
		LockModeType2_0 old = this.specifiedLockMode;
		this.specifiedLockMode = lockMode;
		this.firePropertyChanged(SPECIFIED_LOCK_MODE_PROPERTY, old, lockMode);
	}

	protected LockModeType2_0 buildSpecifiedLockMode() {
		return LockModeType2_0.fromJavaResourceModel(this.queryAnnotation.getLockMode());
	}

	public LockModeType2_0 getDefaultLockMode() {
		return this.defaultLockMode;
	}

	protected void setDefaultLockMode(LockModeType2_0 lockMode) {
		LockModeType2_0 old = this.defaultLockMode;
		this.defaultLockMode = lockMode;
		this.firePropertyChanged(DEFAULT_LOCK_MODE_PROPERTY, old, lockMode);
	}

	protected LockModeType2_0 buildDefaultLockMode() {
		return LockModeType2_0.NONE;
	}

}
