/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.QueryAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.NamedQuery
 * javax.persistence.NamedNativeQuery
 */
abstract class BinaryQueryAnnotation
	extends BinaryAnnotation
	implements QueryAnnotation
{
	String name;
	String query;
	final Vector<QueryHintAnnotation> hints;


	BinaryQueryAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
		this.query = this.buildQuery();
		this.hints = this.buildHints();
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
		this.setQuery_(this.buildQuery());
		this.updateHints();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** BaseNamedQueryAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return (String) this.getJdtMemberValue(this.getNameElementName());
	}

	abstract String getNameElementName();

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** query
	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		throw new UnsupportedOperationException();
	}

	private void setQuery_(String query) {
		String old = this.query;
		this.query = query;
		this.firePropertyChanged(QUERY_PROPERTY, old, query);
	}

	private String buildQuery() {
		return (String) this.getJdtMemberValue(this.getQueryElementName());
	}

	abstract String getQueryElementName();

	public TextRange getQueryTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** hints
	public ListIterator<QueryHintAnnotation> hints() {
		return new CloneListIterator<QueryHintAnnotation>(this.hints);
	}

	public int hintsSize() {
		return this.hints.size();
	}

	public QueryHintAnnotation hintAt(int index) {
		return this.hints.get(index);
	}

	public int indexOfHint(QueryHintAnnotation queryHint) {
		return this.hints.indexOf(queryHint);
	}

	public QueryHintAnnotation addHint(int index) {
		throw new UnsupportedOperationException();
	}

	public void moveHint(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeHint(int index) {
		throw new UnsupportedOperationException();
	}

	private Vector<QueryHintAnnotation> buildHints() {
		Object[] jdtHints = this.getJdtMemberValues(this.getHintsElementName());
		Vector<QueryHintAnnotation> result = new Vector<QueryHintAnnotation>(jdtHints.length);
		for (Object jdtHint : jdtHints) {
			result.add(new BinaryQueryHintAnnotation(this, (IAnnotation) jdtHint));
		}
		return result;
	}

	abstract String getHintsElementName();

	// TODO
	private void updateHints() {
		throw new UnsupportedOperationException();
	}

}
