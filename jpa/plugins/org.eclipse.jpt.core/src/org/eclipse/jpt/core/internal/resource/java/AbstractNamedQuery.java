/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableQueryHint;
import org.eclipse.jpt.core.resource.java.QueryAnnotation;
import org.eclipse.jpt.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public abstract class AbstractNamedQuery extends AbstractAnnotationResource<Type> 
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
	
	private final List<NestableQueryHint> hints;
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
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, nameElementName());
	}

	protected DeclarationAnnotationElementAdapter<String> queryAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, queryElementName());
	}

	protected abstract String nameElementName();

	protected abstract String queryElementName();

	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.nameAdapter.setValue(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String newQuery) {
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
	
	private void addHint(int index, NestableQueryHint queryHint) {
		addItemToList(index, queryHint, this.hints, HINTS_LIST);
	}
	
	public void removeHint(int index) {
		NestableQueryHint queryHint = hintAt(index);
		removeHint(queryHint);
		queryHint.removeAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, this.hintsContainerAnnotation);
	}
	
	private void removeHint(NestableQueryHint queryHint) {
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
	
	public TextRange nameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	public TextRange queryTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.queryDeclarationAdapter, astRoot);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
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

	
	private class HintsContainerAnnotation extends AbstractResource implements ContainerAnnotation<NestableQueryHint> {

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
				if (jdtAnnotation == uniqueConstraint.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
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

		public Annotation jdtAnnotation(CompilationUnit astRoot) {
			return AbstractNamedQuery.this.jdtAnnotation(astRoot);
		}

		public void newAnnotation() {
			AbstractNamedQuery.this.newAnnotation();
		}

		public void removeAnnotation() {
			AbstractNamedQuery.this.removeAnnotation();
		}

		public void updateFromJava(CompilationUnit astRoot) {
			AbstractNamedQuery.this.updateFromJava(astRoot);
		}
		
		public TextRange textRange(CompilationUnit astRoot) {
			return AbstractNamedQuery.this.textRange(astRoot);
		}
		
		public String getElementName() {
			return "hints";
		}

	}
}
