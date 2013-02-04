/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaQuery;
import org.eclipse.jpt.jpa.core.jpa2.context.LockModeType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.NamedQuery2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaNamedQuery2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.NamedQuery2_0Annotation;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java named query
 */
public class GenericJavaNamedQuery
	extends AbstractJavaQuery<NamedQueryAnnotation>
	implements JavaNamedQuery2_0
{
	protected LockModeType2_0 specifiedLockMode;
	protected LockModeType2_0 defaultLockMode;

	public GenericJavaNamedQuery(JavaQueryContainer parent, NamedQueryAnnotation queryAnnotation) {
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
		((NamedQuery2_0Annotation) this.queryAnnotation).setLockMode(LockModeType2_0.toJavaResourceModel(lockMode));
		this.setSpecifiedLockMode_(lockMode);
	}

	protected void setSpecifiedLockMode_(LockModeType2_0 lockMode) {
		LockModeType2_0 old = this.specifiedLockMode;
		this.specifiedLockMode = lockMode;
		this.firePropertyChanged(SPECIFIED_LOCK_MODE_PROPERTY, old, lockMode);
	}

	protected LockModeType2_0 buildSpecifiedLockMode() {
		if (isJpa2_0Compatible()) {
			return LockModeType2_0.fromJavaResourceModel(((NamedQuery2_0Annotation) this.queryAnnotation).getLockMode());
		}
		return null;
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
		if (isJpa2_0Compatible()) {
			return LockModeType2_0.NONE;
		}
		return null;
	}

	// ********** metadata conversion *********

	public void convertTo(OrmQueryContainer queryContainer) {
		queryContainer.addNamedQuery().convertFrom(this);
	}

	public void delete() {
		this.getParent().removeNamedQuery(this);
	}

	// ********** validation **********

	@Override
	protected void validateQuery_(JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter) {
		queryHelper.validate(
			this,
			this.query,
			this.query,
			this.queryAnnotation.getQueryTextRanges(),
			1,
			JpaJpqlQueryHelper.EscapeType.JAVA,
			messages
		);
	}

	@Override
	protected boolean isEquivalentTo(Query other) {
		return super.isEquivalentTo(other)
				&& this.isEquivalentTo((NamedQuery) other);
	}

	protected boolean isEquivalentTo(NamedQuery other) {
		boolean queriesEquivalent = super.isEquivalentTo(other);
		if (this.isJpa2_0Compatible()) {
			return queriesEquivalent && this.isEquivalentTo((NamedQuery2_0) other); 
		}
		return queriesEquivalent;
	}

	protected boolean isEquivalentTo(NamedQuery2_0 other) {
		return this.specifiedLockMode == other.getSpecifiedLockMode();
	}

	// ********** misc **********

	public Class<NamedQuery> getType() {
		return NamedQuery.class;
	}
}