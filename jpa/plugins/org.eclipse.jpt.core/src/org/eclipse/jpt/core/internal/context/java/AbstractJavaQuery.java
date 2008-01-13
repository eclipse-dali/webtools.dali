/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IQuery;
import org.eclipse.jpt.core.internal.resource.java.Query;
import org.eclipse.jpt.core.internal.resource.java.QueryHint;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;


public abstract class AbstractJavaQuery<E extends Query> extends JavaContextModel implements IJavaQuery<E>
{
	protected String name;

	protected String query;

	protected final List<IJavaQueryHint> hints;

	protected E queryResource;
	
	protected AbstractJavaQuery(IJavaJpaContextNode parent) {
		super(parent);
		this.hints = new ArrayList<IJavaQueryHint>();
	}

	protected E query() {
		return this.queryResource;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.queryResource.setName(newName);
		firePropertyChanged(IQuery.NAME_PROPERTY, oldName, newName);
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String newQuery) {
		String oldQuery = this.query;
		this.query = newQuery;
		this.queryResource.setQuery(newQuery);
		firePropertyChanged(IQuery.QUERY_PROPERTY, oldQuery, newQuery);
	}

	public ListIterator<IJavaQueryHint> hints() {
		return new CloneListIterator<IJavaQueryHint>(this.hints);
	}

	public int hintsSize() {
		return this.hints.size();
	}
	
	public IJavaQueryHint addHint(int index) {
		IJavaQueryHint hint = jpaFactory().createJavaQueryHint(this);
		this.hints.add(index, hint);
		this.query().addHint(index);
		this.fireItemAdded(IQuery.HINTS_LIST, index, hint);
		return hint;
	}

	protected void addHint(int index, IJavaQueryHint hint) {
		addItemToList(index, hint, this.hints, IQuery.HINTS_LIST);
	}
	
	public void removeHint(int index) {
		IJavaQueryHint removedHint = this.hints.remove(index);
		this.query().removeHint(index);
		fireItemRemoved(IQuery.HINTS_LIST, index, removedHint);
	}

	protected void removeHint(IJavaQueryHint hint) {
		removeItemFromList(hint, this.hints, IQuery.HINTS_LIST);
	}
	
	public void moveHint(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.hints, targetIndex, sourceIndex);
		this.query().moveHint(targetIndex, sourceIndex);
		fireItemMoved(IQuery.HINTS_LIST, targetIndex, sourceIndex);		
	}
	
	public void initializeFromResource(E queryResource) {
		this.queryResource = queryResource;
		this.name = queryResource.getName();
		this.query = queryResource.getQuery();
		this.initializeQueryHints(queryResource);
	}

	public void update(E queryResource) {
		this.queryResource = queryResource;
		this.setName(queryResource.getName());
		this.setQuery(queryResource.getQuery());
		this.updateQueryHints(queryResource);
	}

	protected void initializeQueryHints(E queryResource) {
		ListIterator<QueryHint> annotations = queryResource.hints();
		
		while(annotations.hasNext()) {
			this.hints.add(createQueryHint(annotations.next()));
		}
	}
	protected void updateQueryHints(E queryResource) {
		ListIterator<IJavaQueryHint> hints = hints();
		ListIterator<QueryHint> resourceHints = queryResource.hints();
		
		while (hints.hasNext()) {
			IJavaQueryHint hint = hints.next();
			if (resourceHints.hasNext()) {
				hint.update(resourceHints.next());
			}
			else {
				removeHint(hint);
			}
		}
		
		while (resourceHints.hasNext()) {
			addHint(hintsSize(), createQueryHint(resourceHints.next()));
		}
	}

	protected IJavaQueryHint createQueryHint(QueryHint hintResource) {
		IJavaQueryHint queryHint =  jpaFactory().createJavaQueryHint(this);
		queryHint.initializeFromResource(hintResource);
		return queryHint;
	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
}
