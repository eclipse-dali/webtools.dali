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
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
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

	protected final Vector<OrmNamedQuery> namedQueries = new Vector<OrmNamedQuery>();
	protected NamedQueryContainerAdapter namedQueryContainerAdapter = new NamedQueryContainerAdapter();

	protected final Vector<OrmNamedNativeQuery> namedNativeQueries = new Vector<OrmNamedNativeQuery>();
	protected NamedNativeQueryContainerAdapter namedNativeQueryContainerAdapter = new NamedNativeQueryContainerAdapter();


	public GenericOrmQueryContainer(XmlContextNode parent, XmlQueryContainer xmlQueryContainer) {
		super(parent);
		this.xmlQueryContainer = xmlQueryContainer;
		this.initializeNamedQueries();
		this.initializeNamedNativeQueries();
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

	public ListIterator<OrmNamedQuery> namedQueries() {
		return this.getNamedQueries().iterator();
	}

	protected ListIterable<OrmNamedQuery> getNamedQueries() {
		return new LiveCloneListIterable<OrmNamedQuery>(this.namedQueries);
	}

	public int namedQueriesSize() {
		return this.namedQueries.size();
	}

	public OrmNamedQuery addNamedQuery() {
		return this.addNamedQuery(this.namedQueries.size());
	}

	public OrmNamedQuery addNamedQuery(int index) {
		XmlNamedQuery xmlQuery = this.buildXmlNamedQuery();
		OrmNamedQuery query = this.addNamedQuery_(index, xmlQuery);
		this.xmlQueryContainer.getNamedQueries().add(index, xmlQuery);
		return query;
	}

	protected XmlNamedQuery buildXmlNamedQuery() {
		return OrmFactory.eINSTANCE.createXmlNamedQuery();
	}

	public void removeNamedQuery(NamedQuery namedQuery) {
		this.removeNamedQuery(this.namedQueries.indexOf(namedQuery));
	}

	public void removeNamedQuery(int index) {
		this.removeNamedQuery_(index);
		this.xmlQueryContainer.getNamedQueries().remove(index);
	}

	protected void removeNamedQuery_(int index) {
		this.removeItemFromList(index, this.namedQueries, NAMED_QUERIES_LIST);
	}

	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.namedQueries, NAMED_QUERIES_LIST);
		this.xmlQueryContainer.getNamedQueries().move(targetIndex, sourceIndex);
	}

	protected void initializeNamedQueries() {
		for (XmlNamedQuery xmlQuery : this.getXmlNamedQueries()) {
			this.namedQueries.add(this.buildNamedQuery(xmlQuery));
		}
	}

	protected OrmNamedQuery buildNamedQuery(XmlNamedQuery xmlNamedQuery) {
		return this.getContextNodeFactory().buildOrmNamedQuery(this, xmlNamedQuery);
	}

	protected void syncNamedQueries() {
		ContextContainerTools.synchronizeWithResourceModel(this.namedQueryContainerAdapter);
	}

	protected Iterable<XmlNamedQuery> getXmlNamedQueries() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlNamedQuery>(this.xmlQueryContainer.getNamedQueries());
	}

	protected void moveNamedQuery_(int index, OrmNamedQuery namedQuery) {
		this.moveItemInList(index, namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
	}

	protected OrmNamedQuery addNamedQuery_(int index, XmlNamedQuery xmlNamedQuery) {
		OrmNamedQuery query = this.buildNamedQuery(xmlNamedQuery);
		this.addItemToList(index, query, this.namedQueries, NAMED_QUERIES_LIST);
		return query;
	}

	protected void removeNamedQuery_(OrmNamedQuery namedQuery) {
		this.removeNamedQuery_(this.namedQueries.indexOf(namedQuery));
	}

	/**
	 * named query container adapter
	 */
	protected class NamedQueryContainerAdapter
		implements ContextContainerTools.Adapter<OrmNamedQuery, XmlNamedQuery>
	{
		public Iterable<OrmNamedQuery> getContextElements() {
			return GenericOrmQueryContainer.this.getNamedQueries();
		}
		public Iterable<XmlNamedQuery> getResourceElements() {
			return GenericOrmQueryContainer.this.getXmlNamedQueries();
		}
		public XmlNamedQuery getResourceElement(OrmNamedQuery contextElement) {
			return contextElement.getXmlQuery();
		}
		public void moveContextElement(int index, OrmNamedQuery element) {
			GenericOrmQueryContainer.this.moveNamedQuery_(index, element);
		}
		public void addContextElement(int index, XmlNamedQuery resourceElement) {
			GenericOrmQueryContainer.this.addNamedQuery_(index, resourceElement);
		}
		public void removeContextElement(OrmNamedQuery element) {
			GenericOrmQueryContainer.this.removeNamedQuery_(element);
		}
	}


	// ********** named native queries **********

	public ListIterator<OrmNamedNativeQuery> namedNativeQueries() {
		return this.getNamedNativeQueries().iterator();
	}

	protected ListIterable<OrmNamedNativeQuery> getNamedNativeQueries() {
		return new LiveCloneListIterable<OrmNamedNativeQuery>(this.namedNativeQueries);
	}

	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}

	public OrmNamedNativeQuery addNamedNativeQuery() {
		return this.addNamedNativeQuery(this.namedNativeQueries.size());
	}

	public OrmNamedNativeQuery addNamedNativeQuery(int index) {
		XmlNamedNativeQuery xmlQuery = this.buildXmlNamedNativeQuery();
		OrmNamedNativeQuery query = this.addNamedNativeQuery_(index, xmlQuery);
		this.xmlQueryContainer.getNamedNativeQueries().add(index, xmlQuery);
		return query;
	}

	protected XmlNamedNativeQuery buildXmlNamedNativeQuery() {
		return OrmFactory.eINSTANCE.createXmlNamedNativeQuery();
	}

	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueries.indexOf(namedNativeQuery));
	}

	public void removeNamedNativeQuery(int index) {
		this.removeNamedNativeQuery_(index);
		this.xmlQueryContainer.getNamedNativeQueries().remove(index);
	}

	protected void removeNamedNativeQuery_(int index) {
		this.removeItemFromList(index, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}

	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
		this.xmlQueryContainer.getNamedNativeQueries().move(targetIndex, sourceIndex);
	}

	protected void initializeNamedNativeQueries() {
		for (XmlNamedNativeQuery xmlQuery : this.getXmlNamedNativeQueries()) {
			this.namedNativeQueries.add(this.buildNamedNativeQuery(xmlQuery));
		}
	}

	protected OrmNamedNativeQuery buildNamedNativeQuery(XmlNamedNativeQuery xmlNamedNativeQuery) {
		return this.getContextNodeFactory().buildOrmNamedNativeQuery(this, xmlNamedNativeQuery);
	}

	protected void syncNamedNativeQueries() {
		ContextContainerTools.synchronizeWithResourceModel(this.namedNativeQueryContainerAdapter);
	}

	protected Iterable<XmlNamedNativeQuery> getXmlNamedNativeQueries() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlNamedNativeQuery>(this.xmlQueryContainer.getNamedNativeQueries());
	}

	protected void moveNamedNativeQuery_(int index, OrmNamedNativeQuery namedNativeQuery) {
		this.moveItemInList(index, namedNativeQuery, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}

	protected OrmNamedNativeQuery addNamedNativeQuery_(int index, XmlNamedNativeQuery xmlNamedNativeQuery) {
		OrmNamedNativeQuery query = this.buildNamedNativeQuery(xmlNamedNativeQuery);
		this.addItemToList(index, query, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
		return query;
	}

	protected void removeNamedNativeQuery_(OrmNamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery_(this.namedNativeQueries.indexOf(namedNativeQuery));
	}

	/**
	 * named native query container adapter
	 */
	protected class NamedNativeQueryContainerAdapter
		implements ContextContainerTools.Adapter<OrmNamedNativeQuery, XmlNamedNativeQuery>
	{
		public Iterable<OrmNamedNativeQuery> getContextElements() {
			return GenericOrmQueryContainer.this.getNamedNativeQueries();
		}
		public Iterable<XmlNamedNativeQuery> getResourceElements() {
			return GenericOrmQueryContainer.this.getXmlNamedNativeQueries();
		}
		public XmlNamedNativeQuery getResourceElement(OrmNamedNativeQuery contextElement) {
			return contextElement.getXmlQuery();
		}
		public void moveContextElement(int index, OrmNamedNativeQuery element) {
			GenericOrmQueryContainer.this.moveNamedNativeQuery_(index, element);
		}
		public void addContextElement(int index, XmlNamedNativeQuery resourceElement) {
			GenericOrmQueryContainer.this.addNamedNativeQuery_(index, resourceElement);
		}
		public void removeContextElement(OrmNamedNativeQuery element) {
			GenericOrmQueryContainer.this.removeNamedNativeQuery_(element);
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
