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
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmQuery;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper.EscapeType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedQuery;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> named query
 */
public abstract class AbstractOrmNamedQuery
	extends AbstractOrmQuery<XmlNamedQuery>
	implements OrmNamedQuery
{

	protected String query;

	protected AbstractOrmNamedQuery(JpaContextNode parent, XmlNamedQuery resourceNamedQuery) {
		super(parent, resourceNamedQuery);
		this.query = this.xmlQuery.getQuery();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setQuery_(this.xmlQuery.getQuery());
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

	// ********** metadata conversion **********

	public void convertFrom(JavaNamedQuery javaQuery) {
		super.convertFrom(javaQuery);
		this.setQuery(javaQuery.getQuery());
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
					JpaValidationMessages.QUERY_STATEMENT_UNDEFINED,
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
		return ObjectTools.equals(this.query, other.getQuery());
	}


	// ********** misc **********

	public Class<NamedQuery> getType() {
		return NamedQuery.class;
	}
}
