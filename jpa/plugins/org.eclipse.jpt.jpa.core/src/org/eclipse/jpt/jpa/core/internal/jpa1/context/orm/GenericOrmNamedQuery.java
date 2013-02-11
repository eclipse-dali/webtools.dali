/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.Collections;
import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmQuery;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.LockModeType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.NamedQuery2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaNamedQuery2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmNamedQuery2_0;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper.EscapeType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> named query
 */
public final class GenericOrmNamedQuery
	extends AbstractOrmQuery<XmlNamedQuery>
	implements OrmNamedQuery2_0
{

	protected String query;

	private LockModeType2_0 specifiedLockMode;
	private LockModeType2_0 defaultLockMode;

	public GenericOrmNamedQuery(JpaContextNode parent, XmlNamedQuery resourceNamedQuery) {
		super(parent, resourceNamedQuery);
		this.query = this.xmlQuery.getQuery();
		this.specifiedLockMode = this.buildSpecifiedLockMode();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setQuery_(this.xmlQuery.getQuery());
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
		this.setQuery_(query);
		this.xmlQuery.setQuery(query);
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
		this.setSpecifiedLockMode_(lockMode);
		this.xmlQuery.setLockMode(LockModeType2_0.toOrmResourceModel(lockMode));
	}

	public void setSpecifiedLockMode_(LockModeType2_0 lockMode) {
		LockModeType2_0 old = this.specifiedLockMode;
		this.specifiedLockMode = lockMode;
		this.firePropertyChanged(SPECIFIED_LOCK_MODE_PROPERTY, old, lockMode);
	}

	protected LockModeType2_0 buildSpecifiedLockMode() {
		return this.isJpa2_0Compatible() ? LockModeType2_0.fromOrmResourceModel(this.xmlQuery.getLockMode()) : null;
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
		return this.isJpa2_0Compatible() ? LockModeType2_0.NONE : null;
	}

	// ********** metadata conversion **********

	public void convertFrom(JavaNamedQuery javaQuery) {
		super.convertFrom(javaQuery);
		this.setQuery(javaQuery.getQuery());
		if (this.isJpa2_0Compatible()) {
			this.setSpecifiedLockMode(((JavaNamedQuery2_0) javaQuery).getSpecifiedLockMode());
		}
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
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JptJpaCoreValidationMessages.QUERY_STATEMENT_UNDEFINED,
					new String[] {this.name},
					this,
					this.getNameTextRange()
				)
			);
		} else {
			this.validateQuery_(queryHelper, messages, reporter);
		}
	}

	protected void validateQuery_(JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter) {
		XmlNamedQuery xmlQuery = this.getXmlQuery();
		EscapeType escapeType = xmlQuery.isQueryInsideCDATASection() ? EscapeType.NONE : EscapeType.XML;

		queryHelper.validate(
			this,
			this.query,
			xmlQuery.getActualQuery(),
			this.getQueryTextRanges(),
			xmlQuery.getQueryOffset(),
			escapeType,
			messages
		);
	}

	public List<TextRange> getQueryTextRanges() {
		return Collections.singletonList(this.xmlQuery.getQueryTextRange());
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
