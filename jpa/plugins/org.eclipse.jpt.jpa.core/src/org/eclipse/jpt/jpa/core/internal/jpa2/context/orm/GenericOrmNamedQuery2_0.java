/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmQuery;
import org.eclipse.jpt.jpa.core.jpa2.context.LockModeType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.NamedQuery2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaNamedQuery2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmNamedQuery2_0;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedQuery;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * JPA 2.0
 * <code>orm.xml</code> named query
 */
public class GenericOrmNamedQuery2_0
	extends AbstractOrmQuery<XmlNamedQuery>
	implements OrmNamedQuery2_0
{
	private LockModeType2_0 specifiedLockMode;
	private LockModeType2_0 defaultLockMode;


	public GenericOrmNamedQuery2_0(XmlContextNode parent, XmlNamedQuery xmlNamedQuery) {
		super(parent, xmlNamedQuery);
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
		this.setSpecifiedLockMode_(lockMode);
		this.xmlQuery.setLockMode(LockModeType2_0.toOrmResourceModel(lockMode));
	}

	public void setSpecifiedLockMode_(LockModeType2_0 lockMode) {
		LockModeType2_0 old = this.specifiedLockMode;
		this.specifiedLockMode = lockMode;
		this.firePropertyChanged(SPECIFIED_LOCK_MODE_PROPERTY, old, lockMode);
	}

	protected LockModeType2_0 buildSpecifiedLockMode() {
		return LockModeType2_0.fromOrmResourceModel(this.xmlQuery.getLockMode());
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

	// ********** metadata conversion **********
 
	public void convertFrom(JavaNamedQuery javaQuery) {
		super.convertFrom(javaQuery);
		this.setSpecifiedLockMode(((JavaNamedQuery2_0)javaQuery).getSpecifiedLockMode());
	}

	// ********** validation **********

	@Override
	protected void validateQuery_(JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter) {

		// Convert the literal escape characters into actual escape characters
		String jpqlQuery = ExpressionTools.unescape(this.query, new int[1]);

		queryHelper.validate(this, jpqlQuery, this.getQueryTextRange(), 0, messages);
	}

	@Override
	protected boolean isEquivalentTo(Query other) {
		return super.isEquivalentTo(other)
				&& this.isEquivalentTo((NamedQuery) other);
	}
	
	protected boolean isEquivalentTo(NamedQuery namedQuery) {
		return this.specifiedLockMode == ((NamedQuery2_0) namedQuery).getSpecifiedLockMode();
	}
	
	// ********** misc **********

	public Class<NamedQuery> getType() {
		return NamedQuery.class;
	}
}