/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlQueryContainer;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> query container
 */
public class GenericOrmQueryContainer
	extends AbstractOrmXmlContextNode
	implements OrmQueryContainer
{
	protected final XmlQueryContainer xmlQueryContainer;

	protected final ContextListContainer<OrmNamedQuery, XmlNamedQuery> namedQueryContainer;
	protected final ContextListContainer<OrmNamedNativeQuery, XmlNamedNativeQuery> namedNativeQueryContainer;


	public GenericOrmQueryContainer(XmlContextNode parent, XmlQueryContainer xmlQueryContainer) {
		super(parent);
		this.xmlQueryContainer = xmlQueryContainer;
		this.namedQueryContainer = this.buildNamedQueryContainer();
		this.namedNativeQueryContainer = this.buildNamedNativeQueryContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncNamedQueries();
		this.syncNamedNativeQueries();
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getNamedQueries());
		this.updateNodes(this.getNamedNativeQueries());
	}


	// ********** named queries **********

	public ListIterable<OrmNamedQuery> getNamedQueries() {
		return this.namedQueryContainer.getContextElements();
	}

	public int getNamedQueriesSize() {
		return this.namedQueryContainer.getContextElementsSize();
	}

	public OrmNamedQuery addNamedQuery() {
		return this.addNamedQuery(this.getNamedQueriesSize());
	}

	public OrmNamedQuery addNamedQuery(int index) {
		XmlNamedQuery xmlQuery = this.buildXmlNamedQuery();
		OrmNamedQuery query = this.namedQueryContainer.addContextElement(index, xmlQuery);
		this.xmlQueryContainer.getNamedQueries().add(index, xmlQuery);
		return query;
	}

	protected XmlNamedQuery buildXmlNamedQuery() {
		return OrmFactory.eINSTANCE.createXmlNamedQuery();
	}

	public void removeNamedQuery(NamedQuery namedQuery) {
		this.removeNamedQuery(this.namedQueryContainer.indexOfContextElement((OrmNamedQuery) namedQuery));
	}

	public void removeNamedQuery(int index) {
		this.namedQueryContainer.removeContextElement(index);
		this.xmlQueryContainer.getNamedQueries().remove(index);
	}

	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		this.namedQueryContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlQueryContainer.getNamedQueries().move(targetIndex, sourceIndex);
	}

	protected OrmNamedQuery buildNamedQuery(XmlNamedQuery xmlNamedQuery) {
		return this.getContextNodeFactory().buildOrmNamedQuery(this, xmlNamedQuery);
	}

	protected void syncNamedQueries() {
		this.namedQueryContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlNamedQuery> getXmlNamedQueries() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlNamedQuery>(this.xmlQueryContainer.getNamedQueries());
	}

	protected ContextListContainer<OrmNamedQuery, XmlNamedQuery> buildNamedQueryContainer() {
		return new NamedQueryContainer();
	}

	/**
	 * named query container
	 */
	protected class NamedQueryContainer
		extends ContextListContainer<OrmNamedQuery, XmlNamedQuery>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return NAMED_QUERIES_LIST;
		}
		@Override
		protected OrmNamedQuery buildContextElement(XmlNamedQuery resourceElement) {
			return GenericOrmQueryContainer.this.buildNamedQuery(resourceElement);
		}
		@Override
		protected ListIterable<XmlNamedQuery> getResourceElements() {
			return GenericOrmQueryContainer.this.getXmlNamedQueries();
		}
		@Override
		protected XmlNamedQuery getResourceElement(OrmNamedQuery contextElement) {
			return contextElement.getXmlQuery();
		}
	}

	// ********** named native queries **********

	public ListIterable<OrmNamedNativeQuery> getNamedNativeQueries() {
		return this.namedNativeQueryContainer.getContextElements();
	}

	public int getNamedNativeQueriesSize() {
		return this.namedNativeQueryContainer.getContextElementsSize();
	}

	public OrmNamedNativeQuery addNamedNativeQuery() {
		return this.addNamedNativeQuery(this.getNamedNativeQueriesSize());
	}

	public OrmNamedNativeQuery addNamedNativeQuery(int index) {
		XmlNamedNativeQuery xmlQuery = this.buildXmlNamedNativeQuery();
		OrmNamedNativeQuery query = this.namedNativeQueryContainer.addContextElement(index, xmlQuery);
		this.xmlQueryContainer.getNamedNativeQueries().add(index, xmlQuery);
		return query;
	}

	protected XmlNamedNativeQuery buildXmlNamedNativeQuery() {
		return OrmFactory.eINSTANCE.createXmlNamedNativeQuery();
	}

	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueryContainer.indexOfContextElement((OrmNamedNativeQuery) namedNativeQuery));
	}

	public void removeNamedNativeQuery(int index) {
		this.namedNativeQueryContainer.removeContextElement(index);
		this.xmlQueryContainer.getNamedNativeQueries().remove(index);
	}

	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		this.namedNativeQueryContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlQueryContainer.getNamedNativeQueries().move(targetIndex, sourceIndex);
	}

	protected OrmNamedNativeQuery buildNamedNativeQuery(XmlNamedNativeQuery xmlNamedNativeQuery) {
		return this.getContextNodeFactory().buildOrmNamedNativeQuery(this, xmlNamedNativeQuery);
	}

	protected void syncNamedNativeQueries() {
		this.namedNativeQueryContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlNamedNativeQuery> getXmlNamedNativeQueries() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlNamedNativeQuery>(this.xmlQueryContainer.getNamedNativeQueries());
	}

	protected ContextListContainer<OrmNamedNativeQuery, XmlNamedNativeQuery> buildNamedNativeQueryContainer() {
		return new NamedNativeQueryContainer();
	}

	/**
	 * named query container
	 */
	protected class NamedNativeQueryContainer
		extends ContextListContainer<OrmNamedNativeQuery, XmlNamedNativeQuery>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return NAMED_NATIVE_QUERIES_LIST;
		}
		@Override
		protected OrmNamedNativeQuery buildContextElement(XmlNamedNativeQuery resourceElement) {
			return GenericOrmQueryContainer.this.buildNamedNativeQuery(resourceElement);
		}
		@Override
		protected ListIterable<XmlNamedNativeQuery> getResourceElements() {
			return GenericOrmQueryContainer.this.getXmlNamedNativeQueries();
		}
		@Override
		protected XmlNamedNativeQuery getResourceElement(OrmNamedNativeQuery contextElement) {
			return contextElement.getXmlQuery();
		}
	}


	// ********** validation **********

	/**
	 * The queries are validated in the persistence unit.
	 * @see org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit#validateQueries(List, IReporter)
	 */
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// queries are validated in the persistence unit
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlQueryContainer.getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}


	// ********** misc **********

	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}
}
