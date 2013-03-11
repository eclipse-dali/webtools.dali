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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaQuery;
import org.eclipse.jpt.jpa.core.jpa2.context.LockModeType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.NamedQuery2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaNamedQuery2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.NamedQueryAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java named query
 */
public class GenericJavaNamedQuery
	extends AbstractJavaQuery<JavaQueryContainer, NamedQueryAnnotation>
	implements JavaNamedQuery2_0
{
	protected String query;

	protected LockModeType2_0 specifiedLockMode;
	protected LockModeType2_0 defaultLockMode;

	public GenericJavaNamedQuery(JavaQueryContainer parent, NamedQueryAnnotation queryAnnotation) {
		super(parent, queryAnnotation);
		this.specifiedLockMode = this.buildSpecifiedLockMode();
		this.query = queryAnnotation.getQuery();
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setQuery_(this.queryAnnotation.getQuery());
		this.setSpecifiedLockMode_(this.buildSpecifiedLockMode());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultLockMode(this.buildDefaultLockMode());
	}


	// ********** query **********

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.queryAnnotation.setQuery(query);
		this.setQuery_(query);
		this.query = this.queryAnnotation.getQuery();
}

	protected void setQuery_(String query) {
		String old = this.query;
		this.query = query;
		this.firePropertyChanged(QUERY_PROPERTY, old, query);
	}


	// ********** lock mode **********

	public LockModeType2_0 getLockMode() {
		return (this.specifiedLockMode != null) ? this.specifiedLockMode : this.defaultLockMode;
	}

	public LockModeType2_0 getSpecifiedLockMode() {
		return this.specifiedLockMode;
	}

	public void setSpecifiedLockMode(LockModeType2_0 lockMode) {
		((NamedQueryAnnotation2_0) this.queryAnnotation).setLockMode(LockModeType2_0.toJavaResourceModel(lockMode));
		this.setSpecifiedLockMode_(lockMode);
	}

	protected void setSpecifiedLockMode_(LockModeType2_0 lockMode) {
		LockModeType2_0 old = this.specifiedLockMode;
		this.specifiedLockMode = lockMode;
		this.firePropertyChanged(SPECIFIED_LOCK_MODE_PROPERTY, old, lockMode);
	}

	protected LockModeType2_0 buildSpecifiedLockMode() {
		if (isJpa2_0Compatible()) {
			return LockModeType2_0.fromJavaResourceModel(((NamedQueryAnnotation2_0) this.queryAnnotation).getLockMode());
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
		this.parent.removeNamedQuery(this);
	}

	// ********** validation **********

	@Override
	public void validate(JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateQuery(queryHelper, messages, reporter);
	}

	protected void validateQuery(JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter) {
		if (StringTools.isBlank(this.query)){
			messages.add(
					this.buildValidationMessage(
							this.getNameTextRange(),
							JptJpaCoreValidationMessages.QUERY_STATEMENT_UNDEFINED,
							this.name
					)
			);
		} else {
			this.validateQuery_(queryHelper, messages, reporter);
		}
	}

	protected void validateQuery_(JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter) {
		queryHelper.validate(
				this,
				this.query,
				this.query,
				this.getQueryTextRanges(),
				1,
				JpaJpqlQueryHelper.EscapeType.JAVA,
				messages
				);
	}

	public List<TextRange> getQueryTextRanges() {
		return this.queryAnnotation.getQueryTextRanges();
	}

	@Override
	protected boolean isEquivalentTo(Query other) {
		return super.isEquivalentTo(other)
				&& this.isEquivalentTo((NamedQuery) other);
	}

	protected boolean isEquivalentTo(NamedQuery other) {
		boolean queriesEquivalent = ObjectTools.equals(this.query, other.getQuery());
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
