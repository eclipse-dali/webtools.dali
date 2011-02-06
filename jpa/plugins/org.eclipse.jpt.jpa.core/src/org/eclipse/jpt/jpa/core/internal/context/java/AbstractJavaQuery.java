/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.Iterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.QueryHint;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryHint;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.resource.java.QueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;

/**
 * Java query
 */
public abstract class AbstractJavaQuery<A extends QueryAnnotation>
	extends AbstractJavaJpaContextNode
	implements JavaQuery
{
	protected final A queryAnnotation;

	protected String name;

	protected String query;

	protected final Vector<JavaQueryHint> hints = new Vector<JavaQueryHint>();
	protected final HintContainerAdapter hintContainerAdapter = new HintContainerAdapter();


	protected AbstractJavaQuery(JavaJpaContextNode parent, A queryAnnotation) {
		super(parent);
		this.queryAnnotation = queryAnnotation;
		this.name = queryAnnotation.getName();
		this.query = queryAnnotation.getQuery();
		this.initializeHints();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.queryAnnotation.getName());
		this.setQuery_(this.queryAnnotation.getQuery());
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
		this.queryAnnotation.setName(name);
		this.setName_(name);
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
		this.queryAnnotation.setQuery(query);
		this.setQuery_(query);
	}

	protected void setQuery_(String query) {
		String old = this.query;
		this.query = query;
		this.firePropertyChanged(QUERY_PROPERTY, old, query);
	}


	// ********** hints **********

	public ListIterable<JavaQueryHint> getHints() {
		return new LiveCloneListIterable<JavaQueryHint>(this.hints);
	}

	public int getHintsSize() {
		return this.hints.size();
	}

	public JavaQueryHint addHint() {
		return this.addHint(this.hints.size());
	}

	public JavaQueryHint addHint(int index) {
		QueryHintAnnotation annotation = this.queryAnnotation.addHint(index);
		return this.addHint_(index, annotation);
	}

	public void removeHint(QueryHint hint) {
		this.removeHint(this.hints.indexOf(hint));
	}

	public void removeHint(int index) {
		this.queryAnnotation.removeHint(index);
		this.removeHint_(index);
	}

	protected void removeHint_(int index) {
		this.removeItemFromList(index, this.hints, HINTS_LIST);
	}

	public void moveHint(int targetIndex, int sourceIndex) {
		this.queryAnnotation.moveHint(targetIndex, sourceIndex);
		this.moveItemInList(targetIndex, sourceIndex, this.hints, HINTS_LIST);
	}

	protected void initializeHints() {
		for (Iterator<QueryHintAnnotation> stream = this.queryAnnotation.hints(); stream.hasNext(); ) {
			this.hints.add(this.buildHint(stream.next()));
		}
	}

	protected JavaQueryHint buildHint(QueryHintAnnotation hintAnnotation) {
		return this.getJpaFactory().buildJavaQueryHint(this, hintAnnotation);
	}

	protected void syncHints() {
		ContextContainerTools.synchronizeWithResourceModel(this.hintContainerAdapter);
	}

	protected Iterable<QueryHintAnnotation> getHintAnnotations() {
		return CollectionTools.iterable(this.queryAnnotation.hints());
	}

	protected void moveHint_(int index, JavaQueryHint hint) {
		this.moveItemInList(index, hint, this.hints, HINTS_LIST);
	}

	protected JavaQueryHint addHint_(int index, QueryHintAnnotation hintAnnotation) {
		JavaQueryHint hint = this.buildHint(hintAnnotation);
		this.addItemToList(index, hint, this.hints, HINTS_LIST);
		return hint;
	}

	protected void removeHint_(JavaQueryHint hint) {
		this.removeHint_(this.hints.indexOf(hint));
	}

	/**
	 * hint container adapter
	 */
	protected class HintContainerAdapter
		implements ContextContainerTools.Adapter<JavaQueryHint, QueryHintAnnotation>
	{
		public Iterable<JavaQueryHint> getContextElements() {
			return AbstractJavaQuery.this.getHints();
		}
		public Iterable<QueryHintAnnotation> getResourceElements() {
			return AbstractJavaQuery.this.getHintAnnotations();
		}
		public QueryHintAnnotation getResourceElement(JavaQueryHint contextElement) {
			return contextElement.getQueryHintAnnotation();
		}
		public void moveContextElement(int index, JavaQueryHint element) {
			AbstractJavaQuery.this.moveHint_(index, element);
		}
		public void addContextElement(int index, QueryHintAnnotation resourceElement) {
			AbstractJavaQuery.this.addHint_(index, resourceElement);
		}
		public void removeContextElement(JavaQueryHint element) {
			AbstractJavaQuery.this.removeHint_(element);
		}
	}


	// ********** misc **********

	public A getQueryAnnotation() {
		return this.queryAnnotation;
	}

	public boolean overrides(Query other) {
		return MappingTools.nodeOverrides(this, other, PRECEDENCE_TYPE_LIST);
	}

	public boolean duplicates(Query other) {
		return MappingTools.nodesAreDuplicates(this, other);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.queryAnnotation.getTextRange(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.queryAnnotation.getNameTextRange(astRoot);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
