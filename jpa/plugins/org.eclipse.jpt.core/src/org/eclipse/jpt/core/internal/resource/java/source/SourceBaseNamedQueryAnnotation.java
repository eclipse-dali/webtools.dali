/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.BaseNamedQueryAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableQueryHintAnnotation;
import org.eclipse.jpt.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.NamedQuery
 * javax.persistence.NamedNativeQuery
 */
abstract class SourceBaseNamedQueryAnnotation
	extends SourceAnnotation<Type> 
	implements BaseNamedQueryAnnotation
{
	final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	final AnnotationElementAdapter<String> nameAdapter;
	String name;

	final DeclarationAnnotationElementAdapter<String> queryDeclarationAdapter;
	final AnnotationElementAdapter<String> queryAdapter;
	String query;

	final Vector<NestableQueryHintAnnotation> hints = new Vector<NestableQueryHintAnnotation>();
	final HintsAnnotationContainer hintsContainer = new HintsAnnotationContainer();


	SourceBaseNamedQueryAnnotation(JavaResourceNode parent, Type type,DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildNameAdapter(daa);
		this.queryDeclarationAdapter = this.buildQueryAdapter(daa);
		this.nameAdapter = this.buildAdapter(this.nameDeclarationAdapter);
		this.queryAdapter = this.buildAdapter(this.queryDeclarationAdapter);
	}

	public void initialize(CompilationUnit astRoot) {
		this.name = this.buildName(astRoot);
		this.query = this.buildQuery(astRoot);
		AnnotationContainerTools.initialize(this.hintsContainer, astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.setName(this.buildName(astRoot));
		this.setQuery(this.buildQuery(astRoot));
		AnnotationContainerTools.update(this.hintsContainer, astRoot);
	}

	/**
	 * convenience method
	 */
	protected AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(this.member, daea);
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
		if (this.attributeValueHasNotChanged(this.name, name)) {
			return;
		}
		String old = this.name;
		this.name = name;
		this.nameAdapter.setValue(name);
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	private DeclarationAnnotationElementAdapter<String> buildNameAdapter(DeclarationAnnotationAdapter daAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daAdapter, this.getNameElementName());
	}

	abstract String getNameElementName();

	// ***** query
	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		if (this.attributeValueHasNotChanged(this.query, query)) {
			return;
		}
		String old = this.query;
		this.query = query;
		this.queryAdapter.setValue(query);
		this.firePropertyChanged(QUERY_PROPERTY, old, query);
	}

	private String buildQuery(CompilationUnit astRoot) {
		return this.queryAdapter.getValue(astRoot);
	}

	public TextRange getQueryTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.queryDeclarationAdapter, astRoot);
	}

	private DeclarationAnnotationElementAdapter<String> buildQueryAdapter(DeclarationAnnotationAdapter daAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daAdapter, this.getQueryElementName());
	}

	abstract String getQueryElementName();

	// ***** hints
	public ListIterator<QueryHintAnnotation> hints() {
		return new CloneListIterator<QueryHintAnnotation>(this.hints);
	}

	ListIterator<NestableQueryHintAnnotation> nestableHints() {
		return new CloneListIterator<NestableQueryHintAnnotation>(this.hints);
	}

	public int hintsSize() {
		return this.hints.size();
	}

	public NestableQueryHintAnnotation hintAt(int index) {
		return this.hints.get(index);
	}

	public int indexOfHint(QueryHintAnnotation queryHint) {
		return this.hints.indexOf(queryHint);
	}

	public NestableQueryHintAnnotation addHint(int index) {
		return (NestableQueryHintAnnotation) AnnotationContainerTools.addNestedAnnotation(index, this.hintsContainer);
	}

	NestableQueryHintAnnotation addHintInternal() {
		NestableQueryHintAnnotation hint = this.buildQueryHint(this.hints.size());
		this.hints.add(hint);
		return hint;
	}

	abstract NestableQueryHintAnnotation buildQueryHint(int index);

	void hintAdded(int index, NestableQueryHintAnnotation hint) {
		this.fireItemAdded(HINTS_LIST, index, hint);
	}

	public void moveHint(int targetIndex, int sourceIndex) {
		AnnotationContainerTools.moveNestedAnnotation(targetIndex, sourceIndex, this.hintsContainer);
	}

	NestableQueryHintAnnotation moveHintInternal(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.hints, targetIndex, sourceIndex).get(targetIndex);
	}

	void hintMoved(int targetIndex, int sourceIndex) {
		this.fireItemMoved(HINTS_LIST, targetIndex, sourceIndex);
	}

	public void removeHint(int index) {
		AnnotationContainerTools.removeNestedAnnotation(index, this.hintsContainer);
	}

	NestableQueryHintAnnotation removeHintInternal(int index) {
		return this.hints.remove(index);
	}

	void hintRemoved(int index, NestableQueryHintAnnotation hint) {
		this.fireItemRemoved(HINTS_LIST, index, hint);
	}

	abstract String getHintsElementName();


	// ********** NestableAnnotation implementation **********

	/**
	 * convenience implementation of method from NestableAnnotation interface
	 * for subclasses
	 */
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		BaseNamedQueryAnnotation oldQuery = (BaseNamedQueryAnnotation) oldAnnotation;
		this.setName(oldQuery.getName());
		this.setQuery(oldQuery.getQuery());
		for (QueryHintAnnotation oldQueryHint : CollectionTools.iterable(oldQuery.hints())) {
			NestableQueryHintAnnotation newQueryHint = this.addHint(oldQuery.indexOfHint(oldQueryHint));
			newQueryHint.initializeFrom((NestableQueryHintAnnotation) oldQueryHint);
		}
	}

	/**
	 * convenience implementation of method from NestableAnnotation interface
	 * for subclasses
	 */
	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}

	private IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}


	// ********** hint container **********

	/**
	 * adapt the AnnotationContainer interface to the override's join columns
	 */
	class HintsAnnotationContainer
		implements AnnotationContainer<NestableQueryHintAnnotation>
	{
		public String getContainerAnnotationName() {
			return SourceBaseNamedQueryAnnotation.this.getAnnotationName();
		}

		public Annotation getContainerJdtAnnotation(CompilationUnit astRoot) {
			return SourceBaseNamedQueryAnnotation.this.getJdtAnnotation(astRoot);
		}

		public String getElementName() {
			return SourceBaseNamedQueryAnnotation.this.getHintsElementName();
		}

		public String getNestableAnnotationName() {
			return QueryHintAnnotation.ANNOTATION_NAME;
		}

		public ListIterator<NestableQueryHintAnnotation> nestedAnnotations() {
			return SourceBaseNamedQueryAnnotation.this.nestableHints();
		}

		public int nestedAnnotationsSize() {
			return SourceBaseNamedQueryAnnotation.this.hintsSize();
		}

		public NestableQueryHintAnnotation addNestedAnnotationInternal() {
			return SourceBaseNamedQueryAnnotation.this.addHintInternal();
		}

		public void nestedAnnotationAdded(int index, NestableQueryHintAnnotation nestedAnnotation) {
			SourceBaseNamedQueryAnnotation.this.hintAdded(index, nestedAnnotation);
		}

		public NestableQueryHintAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
			return SourceBaseNamedQueryAnnotation.this.moveHintInternal(targetIndex, sourceIndex);
		}

		public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
			SourceBaseNamedQueryAnnotation.this.hintMoved(targetIndex, sourceIndex);
		}

		public NestableQueryHintAnnotation removeNestedAnnotationInternal(int index) {
			return SourceBaseNamedQueryAnnotation.this.removeHintInternal(index);
		}

		public void nestedAnnotationRemoved(int index, NestableQueryHintAnnotation nestedAnnotation) {
			SourceBaseNamedQueryAnnotation.this.hintRemoved(index, nestedAnnotation);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}

}
