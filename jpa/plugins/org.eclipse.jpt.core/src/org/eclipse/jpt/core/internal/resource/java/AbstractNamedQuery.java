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
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public abstract class AbstractNamedQuery extends AbstractAnnotationResource<Type> 
	implements Query
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
	
	protected AbstractNamedQuery(JavaResource parent, Type type, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
		this.nameDeclarationAdapter = nameAdapter(daa);
		this.queryDeclarationAdapter = queryAdapter(daa);
		this.nameAdapter = this.buildAdapter(this.nameDeclarationAdapter);
		this.queryAdapter = this.buildAdapter(this.queryDeclarationAdapter);
		this.hints = new ArrayList<NestableQueryHint>();
		this.hintsContainerAnnotation = new HintsContainerAnnotation();
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
	
	public void setName(String name) {
		this.name = name;
		this.nameAdapter.setValue(name);
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.query = query;
		this.queryAdapter.setValue(query);
	}

	public ListIterator<QueryHint> hints() {
		return new CloneListIterator<QueryHint>(this.hints);
	}
	
	public int hintsSize() {
		return this.hints.size();
	}
	
	public NestableQueryHint hintAt(int index) {
		return this.hints.get(index);
	}
	
	public int indexOfHint(QueryHint queryHint) {
		return this.hints.indexOf(queryHint);
	}
	
	public NestableQueryHint addHint(int index) {
		NestableQueryHint queryHint = createQueryHint(index);
		addHint(queryHint);
		queryHint.newAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterAdd(index, this.hintsContainerAnnotation);
		return queryHint;
	}
	
	private void addHint(NestableQueryHint queryHint) {
		this.hints.add(queryHint);
		//property change notification
	}
	
	public void removeHint(int index) {
		NestableQueryHint queryHint = this.hints.remove(index);
		queryHint.removeAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, this.hintsContainerAnnotation);
	}

	public void moveHint(int oldIndex, int newIndex) {
		this.hints.add(newIndex, this.hints.remove(oldIndex));
		ContainerAnnotationTools.synchAnnotationsAfterMove(newIndex, oldIndex, this.hintsContainerAnnotation);
	}
	

	public void updateFromJava(CompilationUnit astRoot) {
		this.setName(this.nameAdapter.getValue(astRoot));
		this.setQuery(this.queryAdapter.getValue(astRoot));
		this.updateQueryHintsFromJava(astRoot);
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
		for (QueryHint queryHint : CollectionTools.iterable(oldNamedQuery.hints())) {
			NestableQueryHint newQueryHint = addHint(oldNamedQuery.indexOfHint(queryHint));
			newQueryHint.initializeFrom((NestableQueryHint) queryHint);
		}
	}

	
	private class HintsContainerAnnotation implements ContainerAnnotation<NestableQueryHint> {

		public NestableQueryHint add(int index) {
			NestableQueryHint queryHint = createNestedAnnotation(index);
			AbstractNamedQuery.this.addHint(queryHint);
			return queryHint;
		}

		public NestableQueryHint createNestedAnnotation(int index) {
			return AbstractNamedQuery.this.createQueryHint(index);
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

		public void move(int oldIndex, int newIndex) {
			moveHint(oldIndex, newIndex);
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
			this.remove(indexOf(queryHint));
		}

		public void remove(int index) {
			AbstractNamedQuery.this.removeHint(index);	
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

		public IJpaPlatform jpaPlatform() {
			return AbstractNamedQuery.this.jpaPlatform();
		}

		public void updateFromJava(CompilationUnit astRoot) {
			AbstractNamedQuery.this.updateFromJava(astRoot);
		}
		
	}
}
