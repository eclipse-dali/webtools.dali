/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Vector;

import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.QueryHint;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmQuery;
import org.eclipse.jpt.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlQuery;
import org.eclipse.jpt.core.resource.orm.XmlQueryHint;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;

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

	protected final Vector<OrmQueryHint> hints = new Vector<OrmQueryHint>();
	protected final HintContainerAdapter hintContainerAdapter = new HintContainerAdapter();


	protected AbstractOrmQuery(XmlContextNode parent, X xmlQuery) {
		super(parent);
		this.xmlQuery = xmlQuery;
		this.name = xmlQuery.getName();
		this.query = xmlQuery.getQuery();
		this.initializeHints();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() { 
		super.synchronizeWithResourceModel();
		this.setName_(this.xmlQuery.getName());
		this.setQuery_(this.xmlQuery.getQuery());
		this.syncHints();
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getHints());
		this.getPersistenceUnit().addQuery(this);
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
		this.xmlQuery.setQuery(query);
	}

	protected void setQuery_(String query) {
		String old = this.query;
		this.query = query;
		this.firePropertyChanged(QUERY_PROPERTY, old, query);
	}


	// ********** hints **********

	public ListIterable<OrmQueryHint> getHints() {
		return new LiveCloneListIterable<OrmQueryHint>(this.hints);
	}

	public int getHintsSize() {
		return this.hints.size();
	}

	public OrmQueryHint addHint() {
		return this.addHint(this.hints.size());
	}

	public OrmQueryHint addHint(int index) {
		XmlQueryHint xmlHint = this.buildXmlQueryHint();
		OrmQueryHint hint = this.addHint_(index, xmlHint);
		this.xmlQuery.getHints().add(index, xmlHint);
		return hint;
	}

	protected XmlQueryHint buildXmlQueryHint() {
		return OrmFactory.eINSTANCE.createXmlQueryHint();
	}

	public void removeHint(QueryHint hint) {
		this.removeHint(this.hints.indexOf(hint));
	}

	public void removeHint(int index) {
		this.removeHint_(index);
		this.xmlQuery.getHints().remove(index);
	}

	protected void removeHint_(int index) {
		this.removeItemFromList(index, this.hints, HINTS_LIST);
	}

	public void moveHint(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.hints, HINTS_LIST);
		this.xmlQuery.getHints().move(targetIndex, sourceIndex);
	}

	protected void initializeHints() {
		for (XmlQueryHint xmlHint : this.getXmlHints()) {
			this.hints.add(this.buildHint(xmlHint));
		}
	}

	protected OrmQueryHint buildHint(XmlQueryHint xmlHint) {
		return this.getContextNodeFactory().buildOrmQueryHint(this, xmlHint);
	}

	protected void syncHints() {
		ContextContainerTools.synchronizeWithResourceModel(this.hintContainerAdapter);
	}

	protected Iterable<XmlQueryHint> getXmlHints() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlQueryHint>(this.xmlQuery.getHints());
	}

	protected void moveHint_(int index, OrmQueryHint hint) {
		this.moveItemInList(index, hint, this.hints, HINTS_LIST);
	}

	protected OrmQueryHint addHint_(int index, XmlQueryHint xmlHint) {
		OrmQueryHint hint = this.buildHint(xmlHint);
		this.addItemToList(index, hint, this.hints, HINTS_LIST);
		return hint;
	}

	protected void removeHint_(OrmQueryHint hint) {
		this.removeHint_(this.hints.indexOf(hint));
	}

	/**
	 * hint container adapter
	 */
	protected class HintContainerAdapter
		implements ContextContainerTools.Adapter<OrmQueryHint, XmlQueryHint>
	{
		public Iterable<OrmQueryHint> getContextElements() {
			return AbstractOrmQuery.this.getHints();
		}
		public Iterable<XmlQueryHint> getResourceElements() {
			return AbstractOrmQuery.this.getXmlHints();
		}
		public XmlQueryHint getResourceElement(OrmQueryHint contextElement) {
			return contextElement.getXmlQueryHint();
		}
		public void moveContextElement(int index, OrmQueryHint element) {
			AbstractOrmQuery.this.moveHint_(index, element);
		}
		public void addContextElement(int index, XmlQueryHint resourceElement) {
			AbstractOrmQuery.this.addHint_(index, resourceElement);
		}
		public void removeContextElement(OrmQueryHint element) {
			AbstractOrmQuery.this.removeHint_(element);
		}
	}


	// ********** misc **********

	public X getXmlQuery() {
		return this.xmlQuery;
	}

	public boolean overrides(Query other) {
		return MappingTools.nodeOverrides(this, other, PRECEDENCE_TYPE_LIST);
	}

	public boolean duplicates(Query other) {
		return MappingTools.nodesAreDuplicates(this, other);
	}

	public TextRange getValidationTextRange() {
		return this.xmlQuery.getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.xmlQuery.getNameTextRange();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}

}
