/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.IQuery;
import org.eclipse.jpt.core.internal.context.base.IQueryHint;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.Query;
import org.eclipse.jpt.core.internal.resource.orm.QueryHint;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;


public abstract class AbstractXmlQuery<E extends Query> extends JpaContextNode implements IQuery
{

	protected String name;

	protected String query;

	protected final List<XmlQueryHint> hints;

	protected E queryResource;
	
	protected AbstractXmlQuery(IJpaContextNode parent) {
		super(parent);
		this.hints = new ArrayList<XmlQueryHint>();
	}

	protected E queryResource() {
		return this.queryResource;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.queryResource().setName(newName);
		firePropertyChanged(IQuery.NAME_PROPERTY, oldName, newName);
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String newQuery) {
		String oldQuery = this.query;
		this.query = newQuery;
		this.queryResource().setQuery(newQuery);
		firePropertyChanged(IQuery.QUERY_PROPERTY, oldQuery, newQuery);
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlQueryHint> hints() {
		return new CloneListIterator<XmlQueryHint>(this.hints);
	}
	
	public int hintsSize() {
		return this.hints.size();
	}
	
	public XmlQueryHint addHint(int index) {
		XmlQueryHint queryHint = new XmlQueryHint(this);
		this.hints.add(index, queryHint);
		this.queryResource().getHints().add(index, OrmFactory.eINSTANCE.createQueryHint());
		this.fireItemAdded(IQuery.HINTS_LIST, index, queryHint);
		return queryHint;
	}

	protected void addHint(int index, XmlQueryHint queryHint) {
		addItemToList(index, queryHint, this.hints, IQuery.HINTS_LIST);
	}
	
	public void removeHint(IQueryHint queryHint) {
		removeHint(this.hints.indexOf(queryHint));
	}
	
	public void removeHint(int index) {
		XmlQueryHint queryHint = this.hints.remove(index);
		this.queryResource.getHints().remove(index);
		fireItemRemoved(IQuery.HINTS_LIST, index, queryHint);
	}

	protected void removeHint_(XmlQueryHint queryHint) {
		removeItemFromList(queryHint, this.hints, IQuery.HINTS_LIST);
	}
	
	public void moveHint(int targetIndex, int sourceIndex) {
		this.queryResource.getHints().move(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.hints, IQuery.HINTS_LIST);		
		
	}

	
	public void initialize(E queryResource) {
		this.queryResource = queryResource;
		this.name = queryResource.getName();
		this.query = queryResource.getQuery();
		this.initializeHints(queryResource);
	}
	
	protected void initializeHints(E queryResource) {
		for (QueryHint queryhint : queryResource.getHints()) {
			this.hints.add(createHint(queryhint));
		}
	}

	protected XmlQueryHint createHint(QueryHint queryhint) {
		XmlQueryHint xmlQueryHint = new XmlQueryHint(this);
		xmlQueryHint.initialize(queryhint);
		return xmlQueryHint;
	}
	
	public void update(E queryResource) {
		this.queryResource = queryResource;
		this.setName(queryResource.getName());
		this.setQuery(queryResource.getQuery());
		this.updateHints(queryResource);
	}
	
	protected void updateHints(E queryResource) {
		ListIterator<XmlQueryHint> hints = hints();
		ListIterator<QueryHint> resourceHints = queryResource.getHints().listIterator();
		
		while (hints.hasNext()) {
			XmlQueryHint hint = hints.next();
			if (resourceHints.hasNext()) {
				hint.update(resourceHints.next());
			}
			else {
				removeHint_(hint);
			}
		}
		
		while (resourceHints.hasNext()) {
			addHint(hintsSize(), createHint(resourceHints.next()));
		}
	}


}
