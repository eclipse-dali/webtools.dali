/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.QueryHint;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaQuery;
import org.eclipse.jpt.core.context.java.JavaQueryHint;
import org.eclipse.jpt.core.resource.java.QueryAnnotation;
import org.eclipse.jpt.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;


public abstract class AbstractJavaQuery extends AbstractJavaJpaContextNode 
	implements JavaQuery
{
	protected String name;

	protected String query;

	protected final List<JavaQueryHint> hints;

	protected QueryAnnotation resourceQuery;
	
	protected AbstractJavaQuery(JavaJpaContextNode parent) {
		super(parent);
		this.hints = new ArrayList<JavaQueryHint>();
	}

	protected QueryAnnotation getResourceQuery() {
		return this.resourceQuery;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.resourceQuery.setName(newName);
		firePropertyChanged(Query.NAME_PROPERTY, oldName, newName);
	}
	
	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(Query.NAME_PROPERTY, oldName, newName);
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String newQuery) {
		String oldQuery = this.query;
		this.query = newQuery;
		this.resourceQuery.setQuery(newQuery);
		firePropertyChanged(Query.QUERY_PROPERTY, oldQuery, newQuery);
	}
	
	protected void setQuery_(String newQuery) {
		String oldQuery = this.query;
		this.query = newQuery;
		firePropertyChanged(Query.QUERY_PROPERTY, oldQuery, newQuery);
	}

	public ListIterator<JavaQueryHint> hints() {
		return new CloneListIterator<JavaQueryHint>(this.hints);
	}

	public int hintsSize() {
		return this.hints.size();
	}
	
	public JavaQueryHint addHint(int index) {
		JavaQueryHint hint = getJpaFactory().buildJavaQueryHint(this);
		this.hints.add(index, hint);
		this.getResourceQuery().addHint(index);
		this.fireItemAdded(Query.HINTS_LIST, index, hint);
		return hint;
	}

	protected void addHint(int index, JavaQueryHint hint) {
		addItemToList(index, hint, this.hints, Query.HINTS_LIST);
	}
	
	protected void addHint(JavaQueryHint hint) {
		addHint(this.hints.size(), hint);
	}
	
	public void removeHint(QueryHint queryHint) {
		removeHint(this.hints.indexOf(queryHint));
	}
	
	public void removeHint(int index) {
		JavaQueryHint removedHint = this.hints.remove(index);
		this.getResourceQuery().removeHint(index);
		fireItemRemoved(Query.HINTS_LIST, index, removedHint);
	}
	
	protected void removeHint_(JavaQueryHint hint) {
		removeItemFromList(hint, this.hints, Query.HINTS_LIST);
	}
	
	public void moveHint(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.hints, targetIndex, sourceIndex);
		this.getResourceQuery().moveHint(targetIndex, sourceIndex);
		fireItemMoved(Query.HINTS_LIST, targetIndex, sourceIndex);		
	}
	
	protected void initialize(QueryAnnotation queryAnnotation) {
		this.resourceQuery = queryAnnotation;
		this.name = queryAnnotation.getName();
		this.query = queryAnnotation.getQuery();
		this.initializeQueryHints(queryAnnotation);
		getPersistenceUnit().addQuery(this);
	}

	protected void update(QueryAnnotation queryAnnotation) {
		this.resourceQuery = queryAnnotation;
		this.setName_(queryAnnotation.getName());
		this.setQuery_(queryAnnotation.getQuery());
		this.updateQueryHints(queryAnnotation);
		getPersistenceUnit().addQuery(this);
	}

	protected void initializeQueryHints(QueryAnnotation queryAnnotation) {
		ListIterator<QueryHintAnnotation> annotations = queryAnnotation.hints();
		
		while(annotations.hasNext()) {
			this.hints.add(createQueryHint(annotations.next()));
		}
	}
	
	protected void updateQueryHints(QueryAnnotation queryAnnotation) {
		ListIterator<JavaQueryHint> contextHints = hints();
		ListIterator<QueryHintAnnotation> resourceHints = queryAnnotation.hints();
		
		while (contextHints.hasNext()) {
			JavaQueryHint hint = contextHints.next();
			if (resourceHints.hasNext()) {
				hint.update(resourceHints.next());
			}
			else {
				removeHint_(hint);
			}
		}
		
		while (resourceHints.hasNext()) {
			addHint(createQueryHint(resourceHints.next()));
		}
	}

	protected JavaQueryHint createQueryHint(QueryHintAnnotation resourceQueryHint) {
		JavaQueryHint queryHint =  getJpaFactory().buildJavaQueryHint(this);
		queryHint.initialize(resourceQueryHint);
		return queryHint;
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourceQuery.getTextRange(astRoot);
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.resourceQuery.getNameTextRange(astRoot);
	}
	
	public boolean overrides(Query other) {
		// java is at the base of the tree
		return false;
	}
	
	public boolean duplicates(Query other) {
		return (this != other)
				&& ! StringTools.stringIsEmpty(this.name)
				&& this.name.equals(other.getName())
				&& ! this.overrides(other)
				&& ! other.overrides(this);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
