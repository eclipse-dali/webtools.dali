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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.QueryHint;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaQuery;
import org.eclipse.jpt.core.context.java.JavaQueryHint;
import org.eclipse.jpt.core.resource.java.QueryAnnotation;
import org.eclipse.jpt.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;


public abstract class AbstractJavaQuery<E extends QueryAnnotation> extends JavaContextModel implements JavaQuery<E>
{
	protected String name;

	protected String query;

	protected final List<JavaQueryHint> hints;

	protected E queryResource;
	
	protected AbstractJavaQuery(JavaJpaContextNode parent) {
		super(parent);
		this.hints = new ArrayList<JavaQueryHint>();
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
		firePropertyChanged(Query.NAME_PROPERTY, oldName, newName);
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String newQuery) {
		String oldQuery = this.query;
		this.query = newQuery;
		this.queryResource.setQuery(newQuery);
		firePropertyChanged(Query.QUERY_PROPERTY, oldQuery, newQuery);
	}

	public ListIterator<JavaQueryHint> hints() {
		return new CloneListIterator<JavaQueryHint>(this.hints);
	}

	public int hintsSize() {
		return this.hints.size();
	}
	
	public JavaQueryHint addHint(int index) {
		JavaQueryHint hint = jpaFactory().buildJavaQueryHint(this);
		this.hints.add(index, hint);
		this.query().addHint(index);
		this.fireItemAdded(Query.HINTS_LIST, index, hint);
		return hint;
	}

	protected void addHint(int index, JavaQueryHint hint) {
		addItemToList(index, hint, this.hints, Query.HINTS_LIST);
	}
	
	public void removeHint(QueryHint queryHint) {
		removeHint(this.hints.indexOf(queryHint));
	}
	
	public void removeHint(int index) {
		JavaQueryHint removedHint = this.hints.remove(index);
		this.query().removeHint(index);
		fireItemRemoved(Query.HINTS_LIST, index, removedHint);
	}
	
	protected void removeHint_(JavaQueryHint hint) {
		removeItemFromList(hint, this.hints, Query.HINTS_LIST);
	}
	
	public void moveHint(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.hints, targetIndex, sourceIndex);
		this.query().moveHint(targetIndex, sourceIndex);
		fireItemMoved(Query.HINTS_LIST, targetIndex, sourceIndex);		
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
		ListIterator<QueryHintAnnotation> annotations = queryResource.hints();
		
		while(annotations.hasNext()) {
			this.hints.add(createQueryHint(annotations.next()));
		}
	}
	protected void updateQueryHints(E queryResource) {
		ListIterator<JavaQueryHint> hints = hints();
		ListIterator<QueryHintAnnotation> resourceHints = queryResource.hints();
		
		while (hints.hasNext()) {
			JavaQueryHint hint = hints.next();
			if (resourceHints.hasNext()) {
				hint.update(resourceHints.next());
			}
			else {
				removeHint_(hint);
			}
		}
		
		while (resourceHints.hasNext()) {
			addHint(hintsSize(), createQueryHint(resourceHints.next()));
		}
	}

	protected JavaQueryHint createQueryHint(QueryHintAnnotation hintResource) {
		JavaQueryHint queryHint =  jpaFactory().buildJavaQueryHint(this);
		queryHint.initializeFromResource(hintResource);
		return queryHint;
	}

	public TextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
}
