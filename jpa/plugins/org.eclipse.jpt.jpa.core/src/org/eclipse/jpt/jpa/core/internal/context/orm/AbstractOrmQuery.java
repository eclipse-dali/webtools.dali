/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.QueryHint;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlQueryHint;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> query
 */
public abstract class AbstractOrmQuery<X extends XmlQuery>
	extends AbstractOrmXmlContextNode
	implements OrmQuery
{
	protected final X xmlQuery;

	protected String name;

	protected String query;

	protected final ContextListContainer<OrmQueryHint, XmlQueryHint> queryHintContainer;


	protected AbstractOrmQuery(XmlContextNode parent, X xmlQuery) {
		super(parent);
		this.xmlQuery = xmlQuery;
		this.name = xmlQuery.getName();
		this.query = this.getUnescapedQuery();
		this.queryHintContainer = this.buildHintContainer();
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.xmlQuery.getName());
		this.setQuery_(this.getUnescapedQuery());
		this.syncHints();
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getHints());
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.setName_(name);
		this.xmlQuery.setName(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** query **********

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.setQuery_(query);
		this.xmlQuery.setQuery(this.convertToEscapeQuery(query));
	}

	protected void setQuery_(String query) {
		String old = this.query;
		this.query = query;
		this.firePropertyChanged(QUERY_PROPERTY, old, query);
	}

	protected String getUnescapedQuery() {
		String queryString = this.xmlQuery.getQuery();
		if (StringTools.stringIsNotEmpty(queryString)) {
			queryString = ExpressionTools.unescape(queryString, new int[1]);
		}
		return queryString;
	}

	// TODO bjv add method to ExpressionTools?
	protected String convertToEscapeQuery(String queryString) {
		if (StringTools.stringIsNotEmpty(queryString)) {
			queryString = ExpressionTools.escape(queryString, new int[1]);
		}
		return queryString;
	}


	// ********** hints **********

	public ListIterable<OrmQueryHint> getHints() {
		return this.queryHintContainer.getContextElements();
	}

	public int getHintsSize() {
		return this.queryHintContainer.getContextElementsSize();
	}

	public OrmQueryHint addHint() {
		return this.addHint(this.getHintsSize());
	}

	public OrmQueryHint addHint(int index) {
		XmlQueryHint xmlHint = this.buildXmlQueryHint();
		OrmQueryHint hint = this.queryHintContainer.addContextElement(index, xmlHint);
		this.xmlQuery.getHints().add(index, xmlHint);
		return hint;
	}

	protected XmlQueryHint buildXmlQueryHint() {
		return OrmFactory.eINSTANCE.createXmlQueryHint();
	}

	public void removeHint(QueryHint hint) {
		this.removeHint(this.queryHintContainer.indexOfContextElement((OrmQueryHint) hint));
	}

	public void removeHint(int index) {
		this.queryHintContainer.removeContextElement(index);
		this.xmlQuery.getHints().remove(index);
	}

	public void moveHint(int targetIndex, int sourceIndex) {
		this.queryHintContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlQuery.getHints().move(targetIndex, sourceIndex);
	}

	protected OrmQueryHint buildHint(XmlQueryHint xmlHint) {
		return this.getContextNodeFactory().buildOrmQueryHint(this, xmlHint);
	}

	protected void syncHints() {
		this.queryHintContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlQueryHint> getXmlHints() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlQueryHint>(this.xmlQuery.getHints());
	}

	protected ContextListContainer<OrmQueryHint, XmlQueryHint> buildHintContainer() {
		return new HintContainer();
	}

	/**
	 * query hint container
	 */
	protected class HintContainer
		extends ContextListContainer<OrmQueryHint, XmlQueryHint>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return HINTS_LIST;
		}
		@Override
		protected OrmQueryHint buildContextElement(XmlQueryHint resourceElement) {
			return AbstractOrmQuery.this.buildHint(resourceElement);
		}
		@Override
		protected ListIterable<XmlQueryHint> getResourceElements() {
			return AbstractOrmQuery.this.getXmlHints();
		}
		@Override
		protected XmlQueryHint getResourceElement(OrmQueryHint contextElement) {
			return contextElement.getXmlQueryHint();
		}
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateName(messages);
		this.validateQuery(messages, reporter);
	}

	protected void validateName(List<IMessage> messages) {
		if (StringTools.stringIsEmpty(this.name)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.QUERY_NAME_UNDEFINED,
					EMPTY_STRING_ARRAY,
					this,
					this.getNameTextRange()
				)
			);
		}
	}

	protected void validateQuery(List<IMessage> messages, IReporter reporter) {
		if (StringTools.stringIsEmpty(this.query)){
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
			this.validateQuery_(messages, reporter);
		}
	}

	protected abstract void validateQuery_(List<IMessage> messages, IReporter reporter);

	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlQuery.getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.xmlQuery.getNameTextRange());
	}

	public TextRange getQueryTextRange() {
		return this.getValidationTextRange(this.xmlQuery.getQueryTextRange());
	}


	// ********** misc **********

	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}

	public X getXmlQuery() {
		return this.xmlQuery;
	}

	public boolean overrides(Query other) {
		return MappingTools.nodeOverrides(this, other, PRECEDENCE_TYPE_LIST);
	}

	public boolean duplicates(Query other) {
		return MappingTools.nodesAreDuplicates(this, other);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
