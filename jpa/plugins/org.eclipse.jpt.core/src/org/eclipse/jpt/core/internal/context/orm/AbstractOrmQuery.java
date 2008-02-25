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
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.QueryHint;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlQuery;
import org.eclipse.jpt.core.resource.orm.XmlQueryHint;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;


public abstract class AbstractOrmQuery<E extends XmlQuery> extends AbstractJpaContextNode implements Query
{

	protected String name;

	protected String query;

	protected final List<GenericOrmQueryHint> hints;

	protected E queryResource;
	
	protected AbstractOrmQuery(JpaContextNode parent) {
		super(parent);
		this.hints = new ArrayList<GenericOrmQueryHint>();
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
		firePropertyChanged(Query.NAME_PROPERTY, oldName, newName);
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String newQuery) {
		String oldQuery = this.query;
		this.query = newQuery;
		this.queryResource().setQuery(newQuery);
		firePropertyChanged(Query.QUERY_PROPERTY, oldQuery, newQuery);
	}

	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmQueryHint> hints() {
		return new CloneListIterator<GenericOrmQueryHint>(this.hints);
	}
	
	public int hintsSize() {
		return this.hints.size();
	}
	
	public GenericOrmQueryHint addHint(int index) {
		GenericOrmQueryHint queryHint = new GenericOrmQueryHint(this);
		this.hints.add(index, queryHint);
		this.queryResource().getHints().add(index, OrmFactory.eINSTANCE.createQueryHint());
		this.fireItemAdded(Query.HINTS_LIST, index, queryHint);
		return queryHint;
	}

	protected void addHint(int index, GenericOrmQueryHint queryHint) {
		addItemToList(index, queryHint, this.hints, Query.HINTS_LIST);
	}
	
	public void removeHint(QueryHint queryHint) {
		removeHint(this.hints.indexOf(queryHint));
	}
	
	public void removeHint(int index) {
		GenericOrmQueryHint queryHint = this.hints.remove(index);
		this.queryResource.getHints().remove(index);
		fireItemRemoved(Query.HINTS_LIST, index, queryHint);
	}

	protected void removeHint_(GenericOrmQueryHint queryHint) {
		removeItemFromList(queryHint, this.hints, Query.HINTS_LIST);
	}
	
	public void moveHint(int targetIndex, int sourceIndex) {
		this.queryResource.getHints().move(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.hints, Query.HINTS_LIST);		
		
	}

	
	public void initialize(E queryResource) {
		this.queryResource = queryResource;
		this.name = queryResource.getName();
		this.query = queryResource.getQuery();
		this.initializeHints(queryResource);
	}
	
	protected void initializeHints(E queryResource) {
		for (XmlQueryHint queryhint : queryResource.getHints()) {
			this.hints.add(createHint(queryhint));
		}
	}

	protected GenericOrmQueryHint createHint(XmlQueryHint queryhint) {
		GenericOrmQueryHint xmlQueryHint = new GenericOrmQueryHint(this);
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
		ListIterator<GenericOrmQueryHint> hints = hints();
		ListIterator<XmlQueryHint> resourceHints = queryResource.getHints().listIterator();
		
		while (hints.hasNext()) {
			GenericOrmQueryHint hint = hints.next();
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
