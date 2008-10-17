/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableQueryHint;
import org.eclipse.jpt.core.resource.java.QueryAnnotation;
import org.eclipse.jpt.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public abstract class AbstractNamedQuery extends AbstractResourceAnnotation<Type> 
	implements QueryAnnotation
{
	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;

	// hold this so we can get the 'query' text range
	private final DeclarationAnnotationElementAdapter<String> queryDeclarationAdapter;

	private final AnnotationElementAdapter<String> nameAdapter;

	private final AnnotationElementAdapter<String> queryAdapter;

	private String name;
	
	private String query;
	
	protected final List<NestableQueryHint> hints;
	private final HintsContainerAnnotation hintsContainerAnnotation;
	
	protected AbstractNamedQuery(JavaResourceNode parent, Type type,DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
		this.nameDeclarationAdapter = nameAdapter(daa);
		this.queryDeclarationAdapter = queryAdapter(daa);
		this.nameAdapter = this.buildAdapter(this.nameDeclarationAdapter);
		this.queryAdapter = this.buildAdapter(this.queryDeclarationAdapter);
		this.hints = new ArrayList<NestableQueryHint>();
		this.hintsContainerAnnotation = new HintsContainerAnnotation();
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.name = this.name(astRoot);
		this.query = this.query(astRoot);
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this.hintsContainerAnnotation);
	}
	
	
	// ********** initialization **********
	protected AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(getMember(), daea);
	}

	protected DeclarationAnnotationElementAdapter<String> nameAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, getNameElementName());
	}

	protected DeclarationAnnotationElementAdapter<String> queryAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, getQueryElementName());
	}

	protected abstract String getNameElementName();

	protected abstract String getQueryElementName();

	/**
	 * Return the uniqueConstraints element name
	 */
	protected abstract String getHintsElementName();

	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		if (attributeValueHasNotChanged(this.name, newName)) {
			return;
		}
		String oldName = this.name;
		this.name = newName;
		this.nameAdapter.setValue(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String newQuery) {
		if (attributeValueHasNotChanged(this.query, newQuery)) {
			return;
		}
		String oldQuery = this.query;
		this.query = newQuery;
		this.queryAdapter.setValue(newQuery);
		firePropertyChanged(QUERY_PROPERTY, oldQuery, newQuery);
	}

	public ListIterator<QueryHintAnnotation> hints() {
		return new CloneListIterator<QueryHintAnnotation>(this.hints);
	}
	
	public int hintsSize() {
		return this.hints.size();
	}
	
	public NestableQueryHint hintAt(int index) {
		return this.hints.get(index);
	}
	
	public int indexOfHint(QueryHintAnnotation queryHint) {
		return this.hints.indexOf(queryHint);
	}
	
	public NestableQueryHint addHint(int index) {
		NestableQueryHint queryHint = (NestableQueryHint) ContainerAnnotationTools.addNestedAnnotation(index, this.hintsContainerAnnotation);
		fireItemAdded(QueryAnnotation.HINTS_LIST, index, queryHint);
		return queryHint;
	}
	
	protected void addHint(int index, NestableQueryHint queryHint) {
		addItemToList(index, queryHint, this.hints, HINTS_LIST);
	}
	
	public void removeHint(int index) {
		NestableQueryHint queryHint = hintAt(index);
		removeHint(queryHint);
		queryHint.removeAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, this.hintsContainerAnnotation);
	}
	
	protected void removeHint(NestableQueryHint queryHint) {
		removeItemFromList(queryHint, this.hints, HINTS_LIST);
	}

	public void moveHint(int targetIndex, int sourceIndex) {
		moveHintInternal(targetIndex, sourceIndex);
		ContainerAnnotationTools.synchAnnotationsAfterMove(targetIndex, sourceIndex, this.hintsContainerAnnotation);
		fireItemMoved(QueryAnnotation.HINTS_LIST, targetIndex, sourceIndex);
	}
	
	protected void moveHintInternal(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.hints, targetIndex, sourceIndex);
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	public TextRange getQueryTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.queryDeclarationAdapter, astRoot);
	}
	
	public void update(CompilationUnit astRoot) {
		this.setName(this.name(astRoot));
		this.setQuery(this.query(astRoot));
		this.updateQueryHintsFromJava(astRoot);
	}

	protected String name(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}
	
	protected String query(CompilationUnit astRoot) {
		return this.queryAdapter.getValue(astRoot);
	}
	
	private void updateQueryHintsFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this.hintsContainerAnnotation);
	}
	
	protected abstract NestableQueryHint createQueryHint(int index);
	
	// ********** persistence model -> java annotations **********
	public IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) super.getAnnotationAdapter();
	}

	public void moveAnnotation(int newIndex) {
		getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}

	public void initializeFrom(NestableAnnotation oldAnnotation) {
		AbstractNamedQuery oldNamedQuery = (AbstractNamedQuery) oldAnnotation;
		setName(oldNamedQuery.getName());
		setQuery(oldNamedQuery.getQuery());
		for (QueryHintAnnotation queryHint : CollectionTools.iterable(oldNamedQuery.hints())) {
			NestableQueryHint newQueryHint = addHint(oldNamedQuery.indexOfHint(queryHint));
			newQueryHint.initializeFrom((NestableQueryHint) queryHint);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}

	
	private class HintsContainerAnnotation extends AbstractJavaResourceNode implements ContainerAnnotation<NestableQueryHint> {

		public HintsContainerAnnotation() {
			super(AbstractNamedQuery.this);
		}
		
		public void initialize(CompilationUnit astRoot) {
			// nothing to initialize
		}
		
		public NestableQueryHint add(int index) {
			NestableQueryHint queryHint = AbstractNamedQuery.this.createQueryHint(index);
			AbstractNamedQuery.this.addHint(index, queryHint);
			return queryHint;
		}
		
		public NestableQueryHint addInternal(int index) {
			NestableQueryHint queryHint = AbstractNamedQuery.this.createQueryHint(index);
			AbstractNamedQuery.this.hints.add(index, queryHint);
			return queryHint;
		}
		
		public String getAnnotationName() {
			return AbstractNamedQuery.this.getAnnotationName();
		}

		public String getNestableAnnotationName() {
			return JPA.QUERY_HINT;
		}

		public int indexOf(NestableQueryHint hint) {
			return AbstractNamedQuery.this.indexOfHint(hint);
		}

		public void move(int targetIndex, int sourceIndex) {
			AbstractNamedQuery.this.moveHint(targetIndex, sourceIndex);
		}
		
		public void moveInternal(int targetIndex, int sourceIndex) {
			AbstractNamedQuery.this.moveHintInternal(targetIndex, sourceIndex);
		}

		public NestableQueryHint nestedAnnotationAt(int index) {
			return AbstractNamedQuery.this.hintAt(index);
		}

		public NestableQueryHint nestedAnnotationFor(Annotation jdtAnnotation) {
			for (NestableQueryHint uniqueConstraint : CollectionTools.iterable(nestedAnnotations())) {
				if (jdtAnnotation == uniqueConstraint.getJdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
					return uniqueConstraint;
				}
			}
			return null;
		}

		public ListIterator<NestableQueryHint> nestedAnnotations() {
			return new CloneListIterator<NestableQueryHint>(AbstractNamedQuery.this.hints);
		}

		public int nestedAnnotationsSize() {
			return AbstractNamedQuery.this.hintsSize();
		}

		public void remove(NestableQueryHint queryHint) {
			AbstractNamedQuery.this.removeHint(queryHint);
		}

		public void remove(int index) {
			AbstractNamedQuery.this.removeHint(nestedAnnotationAt(index));	
		}

		public Annotation getJdtAnnotation(CompilationUnit astRoot) {
			return AbstractNamedQuery.this.getJdtAnnotation(astRoot);
		}

		public void newAnnotation() {
			AbstractNamedQuery.this.newAnnotation();
		}

		public void removeAnnotation() {
			AbstractNamedQuery.this.removeAnnotation();
		}

		public void update(CompilationUnit astRoot) {
			AbstractNamedQuery.this.update(astRoot);
		}
		
		public TextRange getTextRange(CompilationUnit astRoot) {
			return AbstractNamedQuery.this.getTextRange(astRoot);
		}
		
		public String getElementName() {
			return "hints";
		}

		@Override
		public void toString(StringBuilder sb) {
			AbstractNamedQuery.this.toString(sb);
		}

	}
}
