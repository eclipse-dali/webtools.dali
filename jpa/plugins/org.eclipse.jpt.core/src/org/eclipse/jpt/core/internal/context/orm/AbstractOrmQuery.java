/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.QueryHint;
import org.eclipse.jpt.core.context.java.JavaQuery;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmQuery;
import org.eclipse.jpt.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlQuery;
import org.eclipse.jpt.core.resource.orm.XmlQueryHint;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;


public abstract class AbstractOrmQuery<E extends XmlQuery> extends AbstractOrmJpaContextNode 
	implements OrmQuery
{

	protected String name;

	protected String query;

	protected final List<OrmQueryHint> hints;

	protected E resourceQuery;
	
	protected AbstractOrmQuery(OrmJpaContextNode parent, E resourceQuery) {
		super(parent);
		this.hints = new ArrayList<OrmQueryHint>();
		this.initialize(resourceQuery);
	}

	protected E getResourceQuery() {
		return this.resourceQuery;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.getResourceQuery().setName(newName);
		firePropertyChanged(Query.NAME_PROPERTY, oldName, newName);
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String newQuery) {
		String oldQuery = this.query;
		this.query = newQuery;
		this.getResourceQuery().setQuery(newQuery);
		firePropertyChanged(Query.QUERY_PROPERTY, oldQuery, newQuery);
	}

	public ListIterator<OrmQueryHint> hints() {
		return new CloneListIterator<OrmQueryHint>(this.hints);
	}
	
	public int hintsSize() {
		return this.hints.size();
	}
	
	public OrmQueryHint addHint(int index) {
		XmlQueryHint resourceQueryHint = OrmFactory.eINSTANCE.createXmlQueryHint();
		OrmQueryHint contextQueryHint = buildQueryHint(resourceQueryHint);
		this.hints.add(index, contextQueryHint);
		this.getResourceQuery().getHints().add(index, resourceQueryHint);
		this.fireItemAdded(Query.HINTS_LIST, index, contextQueryHint);
		return contextQueryHint;
	}

	protected void addHint(int index, OrmQueryHint queryHint) {
		addItemToList(index, queryHint, this.hints, Query.HINTS_LIST);
	}
	
	protected void addHint(OrmQueryHint queryHint) {
		this.addHint(this.hints.size(), queryHint);
	}
	
	public void removeHint(QueryHint queryHint) {
		removeHint(this.hints.indexOf(queryHint));
	}
	
	public void removeHint(int index) {
		OrmQueryHint queryHint = this.hints.remove(index);
		this.getResourceQuery().getHints().remove(index);
		fireItemRemoved(Query.HINTS_LIST, index, queryHint);
	}

	protected void removeHint_(OrmQueryHint queryHint) {
		removeItemFromList(queryHint, this.hints, Query.HINTS_LIST);
	}
	
	public void moveHint(int targetIndex, int sourceIndex) {
		this.getResourceQuery().getHints().move(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.hints, Query.HINTS_LIST);		
	}

	
	protected void initialize(E resourceQuery) {
		this.resourceQuery = resourceQuery;
		this.name = resourceQuery.getName();
		this.query = resourceQuery.getQuery();
		this.initializeHints(resourceQuery);
	}
	
	protected void initializeHints(E resourceQuery) {
		for (XmlQueryHint resourceQueryHint : resourceQuery.getHints()) {
			this.hints.add(buildQueryHint(resourceQueryHint));
		}
	}

	protected OrmQueryHint buildQueryHint(XmlQueryHint resourceQueryHint) {
		return getJpaFactory().buildOrmQueryHint(this, resourceQueryHint);
	}
	
	public void update(E resourceQuery) {
		this.resourceQuery = resourceQuery;
		this.setName(resourceQuery.getName());
		this.setQuery(resourceQuery.getQuery());
		this.updateHints(resourceQuery);
	}
	
	protected void updateHints(E resourceQuery) {
		ListIterator<OrmQueryHint> contextHints = hints();
		ListIterator<XmlQueryHint> resourceHints = new CloneListIterator<XmlQueryHint>(resourceQuery.getHints());//prevent ConcurrentModificiationException
		
		while (contextHints.hasNext()) {
			OrmQueryHint contextHint = contextHints.next();
			if (resourceHints.hasNext()) {
				contextHint.update(resourceHints.next());
			}
			else {
				removeHint_(contextHint);
			}
		}
		
		while (resourceHints.hasNext()) {
			addHint(buildQueryHint(resourceHints.next()));
		}
	}
	
	public boolean overrides(Query query) {
		if (getName() == null) {
			return false;
		}
		// this isn't ideal, but it will have to do until we have further adopter input
		return this.getName().equals(query.getName()) && query instanceof JavaQuery;
	}

	public TextRange getValidationTextRange() {
		return this.getResourceQuery().getValidationTextRange();
	}
	
	public TextRange getNameTextRange() {
		return this.getResourceQuery().getNameTextRange();
	}
}
