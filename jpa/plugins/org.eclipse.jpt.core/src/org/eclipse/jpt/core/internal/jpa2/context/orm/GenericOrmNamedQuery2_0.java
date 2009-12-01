/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmQuery;
import org.eclipse.jpt.core.jpa2.context.LockModeType_2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmNamedQuery2_0;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;

/**
 *  GenericOrmNamedQuery2_0
 */
public class GenericOrmNamedQuery2_0 extends AbstractOrmQuery<XmlNamedQuery>
	implements OrmNamedQuery2_0
{
	private LockModeType_2_0 specifiedLockMode;
	private LockModeType_2_0 defaultLockMode;

	// ********** constructor **********
	public GenericOrmNamedQuery2_0(XmlContextNode parent, XmlNamedQuery resourceNamedQuery) {
		super(parent, resourceNamedQuery);
	}

	// ********** NamedQuery2_0 implementation **********

	public LockModeType_2_0 getLockMode() {
		return (this.specifiedLockMode != null) ? this.specifiedLockMode : this.defaultLockMode;
	}

	public LockModeType_2_0 getSpecifiedLockMode() {
		return this.specifiedLockMode;
	}

	public void setSpecifiedLockMode(LockModeType_2_0 lockMode) {
		LockModeType_2_0 old = this.specifiedLockMode;
		this.specifiedLockMode = lockMode;
		this.getResourceQuery().setLockMode(LockModeType_2_0.toOrmResourceModel(lockMode));
		this.firePropertyChanged(SPECIFIED_LOCK_MODE_PROPERTY, old, lockMode);
	}

	public void setSpecifiedLockMode_(LockModeType_2_0 lockMode) {
		LockModeType_2_0 old = this.specifiedLockMode;
		this.specifiedLockMode = lockMode;
		this.firePropertyChanged(SPECIFIED_LOCK_MODE_PROPERTY, old, lockMode);
	}
	
	public LockModeType_2_0 getDefaultLockMode() {
		return this.defaultLockMode;
	}

	protected void setDefaultLockMode(LockModeType_2_0 lockMode) {
		LockModeType_2_0 old = this.defaultLockMode;
		this.defaultLockMode = lockMode;
		this.firePropertyChanged(DEFAULT_LOCK_MODE_PROPERTY, old, lockMode);
	}

	// ********** resource => context **********

	@Override
	protected void initialize(XmlNamedQuery xmlQuery) {
		super.initialize(xmlQuery);

		this.specifiedLockMode = this.getResourceLockModeOf(xmlQuery);
	}

	@Override
	public void update(XmlNamedQuery xmlQuery) {
		super.update(xmlQuery);

		this.setSpecifiedLockMode_(this.getResourceLockModeOf(xmlQuery));
	}

	private LockModeType_2_0 getResourceLockModeOf(XmlNamedQuery xmlQuery) {
		return LockModeType_2_0.fromOrmResourceModel(xmlQuery.getLockMode());
	}
	
}
