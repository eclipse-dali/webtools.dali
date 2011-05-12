/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.jpa.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.NestableQueryHintAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;

/**
 * <ul>
 * <li><code>javax.persistence.NamedQuery</code>
 * <li><code>javax.persistence.NamedNativeQuery</code>
 * </ul>
 */
abstract class SourceQueryAnnotation
	extends SourceAnnotation<Type> 
	implements QueryAnnotation
{
	DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	AnnotationElementAdapter<String> nameAdapter;
	String name;
	TextRange nameTextRange;

	DeclarationAnnotationElementAdapter<String> queryDeclarationAdapter;
	AnnotationElementAdapter<String> queryAdapter;
	String query;
	TextRange queryTextRange;

	final Vector<NestableQueryHintAnnotation> hints = new Vector<NestableQueryHintAnnotation>();
	final HintsAnnotationContainer hintsContainer = new HintsAnnotationContainer();


	SourceQueryAnnotation(JavaResourceNode parent, Type type,DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildNameAdapter();
		this.queryDeclarationAdapter = this.buildQueryDeclarationAdapter();
		this.queryAdapter = this.buildQueryAdapter();
	}

	public void initialize(CompilationUnit astRoot) {
		this.name = this.buildName(astRoot);
		this.nameTextRange = this.buildNameTextRange(astRoot);
		this.query = this.buildQuery(astRoot);
		this.queryTextRange = this.buildQueryTextRange(astRoot);
		AnnotationContainerTools.initialize(this.hintsContainer, astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncName(this.buildName(astRoot));
		this.nameTextRange = this.buildNameTextRange(astRoot);
		this.syncQuery(this.buildQuery(astRoot));
		this.queryTextRange = this.buildQueryTextRange(astRoot);
		AnnotationContainerTools.synchronize(this.hintsContainer, astRoot);
	}


	// ********** BaseNamedQueryAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.attributeValueHasChanged(this.name, name)) {
			this.name = name;
			this.nameAdapter.setValue(name);
		}
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.nameTextRange;
	}

	private TextRange buildNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	private DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, this.getNameElementName());
	}

	private AnnotationElementAdapter<String> buildNameAdapter() {
		return this.buildStringElementAdapter(this.nameDeclarationAdapter);
	}

	abstract String getNameElementName();

	// ***** query
	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		if (this.attributeValueHasChanged(this.query, query)) {
			this.query = query;
			this.queryAdapter.setValue(query);
		}
	}

	private void syncQuery(String annotationQuery) {
		String old = this.query;
		this.query = annotationQuery;
		this.firePropertyChanged(QUERY_PROPERTY, old, annotationQuery);
	}

	private String buildQuery(CompilationUnit astRoot) {
		return this.queryAdapter.getValue(astRoot);
	}

	public TextRange getQueryTextRange(CompilationUnit astRoot) {
		return this.queryTextRange;
	}

	private TextRange buildQueryTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.queryDeclarationAdapter, astRoot);
	}

	private DeclarationAnnotationElementAdapter<String> buildQueryDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, this.getQueryElementName());
	}

	private AnnotationElementAdapter<String> buildQueryAdapter() {
		return this.buildStringElementAdapter(this.queryDeclarationAdapter);
	}

	abstract String getQueryElementName();

	// ***** hints
	public ListIterator<QueryHintAnnotation> hints() {
		return new CloneListIterator<QueryHintAnnotation>(this.hints);
	}

	Iterable<NestableQueryHintAnnotation> getNestableHints() {
		return new LiveCloneIterable<NestableQueryHintAnnotation>(this.hints);
	}

	public int hintsSize() {
		return this.hints.size();
	}

	public NestableQueryHintAnnotation hintAt(int index) {
		return this.hints.get(index);
	}

	public int indexOfHint(QueryHintAnnotation hint) {
		return this.hints.indexOf(hint);
	}

	private NestableQueryHintAnnotation addHint() {
		return this.addHint(this.hints.size());
	}

	public NestableQueryHintAnnotation addHint(int index) {
		return (NestableQueryHintAnnotation) AnnotationContainerTools.addNestedAnnotation(index, this.hintsContainer);
	}

	NestableQueryHintAnnotation addHint_() {
		return this.addHint_(this.hints.size());
	}

	private NestableQueryHintAnnotation addHint_(int index) {
		NestableQueryHintAnnotation hint = this.buildHint(index);
		this.hints.add(index, hint);
		return hint;
	}

	void syncAddHint(Annotation astAnnotation) {
		int index = this.hints.size();
		NestableQueryHintAnnotation hint = this.addHint_(index);
		hint.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(HINTS_LIST, index, hint);
	}

	abstract NestableQueryHintAnnotation buildHint(int index);

	void hintAdded(int index, NestableQueryHintAnnotation hint) {
		this.fireItemAdded(HINTS_LIST, index, hint);
	}

	public void moveHint(int targetIndex, int sourceIndex) {
		AnnotationContainerTools.moveNestedAnnotation(targetIndex, sourceIndex, this.hintsContainer);
	}

	NestableQueryHintAnnotation moveHint_(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.hints, targetIndex, sourceIndex).get(targetIndex);
	}

	public void removeHint(int index) {
		AnnotationContainerTools.removeNestedAnnotation(index, this.hintsContainer);
	}

	NestableQueryHintAnnotation removeHint_(int index) {
		return this.hints.remove(index);
	}

	void syncRemoveHints(int index) {
		this.removeItemsFromList(index, this.hints, HINTS_LIST);
	}

	abstract String getHintsElementName();


	// ********** NestableAnnotation implementation **********

	/**
	 * convenience implementation of method from NestableAnnotation interface
	 * for subclasses
	 */
	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.name == null) &&
				(this.query == null) &&
				this.hints.isEmpty();
	}

	@Override
	protected void rebuildAdapters() {
		super.rebuildAdapters();
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildNameAdapter();
		this.queryDeclarationAdapter = this.buildQueryDeclarationAdapter();
		this.queryAdapter = this.buildQueryAdapter();
	}

	@Override
	public void storeOn(Map<String, Object> map) {
		super.storeOn(map);

		map.put(NAME_PROPERTY, this.name);
		this.name = null;
		map.put(QUERY_PROPERTY, this.query);
		this.query = null;

		List<Map<String, Object>> hintsState = this.buildStateList(this.hints.size());
		for (NestableQueryHintAnnotation hint : this.getNestableHints()) {
			Map<String, Object> hintState = new HashMap<String, Object>();
			hint.storeOn(hintState);
			hintsState.add(hintState);
		}
		map.put(HINTS_LIST, hintsState);
		this.hints.clear();
	}

	@Override
	public void restoreFrom(Map<String, Object> map) {
		super.restoreFrom(map);

		this.setName((String) map.get(NAME_PROPERTY));
		this.setQuery((String) map.get(QUERY_PROPERTY));

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> hintsState = (List<Map<String, Object>>) map.get(HINTS_LIST);
		for (Map<String, Object> hintState : hintsState) {
			this.addHint().restoreFrom(hintState);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** hint container **********

	/**
	 * adapt the AnnotationContainer interface to the override's join columns
	 */
	class HintsAnnotationContainer
		implements AnnotationContainer<NestableQueryHintAnnotation>
	{
		public Annotation getAstAnnotation(CompilationUnit astRoot) {
			return SourceQueryAnnotation.this.getAstAnnotation(astRoot);
		}

		public String getElementName() {
			return SourceQueryAnnotation.this.getHintsElementName();
		}

		public String getNestedAnnotationName() {
			return QueryHintAnnotation.ANNOTATION_NAME;
		}

		public Iterable<NestableQueryHintAnnotation> getNestedAnnotations() {
			return SourceQueryAnnotation.this.getNestableHints();
		}

		public int getNestedAnnotationsSize() {
			return SourceQueryAnnotation.this.hintsSize();
		}

		public NestableQueryHintAnnotation addNestedAnnotation() {
			return SourceQueryAnnotation.this.addHint_();
		}

		public void syncAddNestedAnnotation(Annotation astAnnotation) {
			SourceQueryAnnotation.this.syncAddHint(astAnnotation);
		}

		public NestableQueryHintAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
			return SourceQueryAnnotation.this.moveHint_(targetIndex, sourceIndex);
		}

		public NestableQueryHintAnnotation removeNestedAnnotation(int index) {
			return SourceQueryAnnotation.this.removeHint_(index);
		}

		public void syncRemoveNestedAnnotations(int index) {
			SourceQueryAnnotation.this.syncRemoveHints(index);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}
}
