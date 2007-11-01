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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class NamedQueriesImpl extends AbstractAnnotationResource<Type> implements NamedQueries
{
	private static final String ANNOTATION_NAME = JPA.NAMED_QUERIES;
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private List<NestableNamedQuery> namedQueries;
	
	protected NamedQueriesImpl(JavaResource parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.namedQueries = new ArrayList<NestableNamedQuery>();
	}

	public void initialize(CompilationUnit astRoot) {
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return NamedQuery.ANNOTATION_NAME;
	}
		
	public ListIterator<NestableNamedQuery> nestedAnnotations() {
		return new CloneListIterator<NestableNamedQuery>(this.namedQueries);
	}
	
	public int nestedAnnotationsSize() {
		return this.namedQueries.size();
	}	

	public void addInternal(int index) {
		NestableNamedQuery namedQuery = createNamedQuery(index);
		this.namedQueries.add(index, namedQuery);
	}
	
	public NestableNamedQuery add(int index) {
		NestableNamedQuery namedQuery = createNamedQuery(index);
		add(index, namedQuery);
		return namedQuery;
	}
	
	protected void add(int index, NestableNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
	}

	public void remove(NestableNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
	}
	
	public void remove(int index) {
		removeItemFromList(index, this.namedQueries, NAMED_QUERIES_LIST);
	}
	
	public int indexOf(NestableNamedQuery attributeOverride) {
		return this.namedQueries.indexOf(attributeOverride);
	}
	
	public NestableNamedQuery nestedAnnotationAt(int index) {
		return this.namedQueries.get(index);
	}
	
	public NestableNamedQuery nestedAnnotationFor(org.eclipse.jdt.core.dom.Annotation jdtAnnotation) {
		for (NestableNamedQuery namedQuery : this.namedQueries) {
			if (jdtAnnotation == namedQuery.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
				return namedQuery;
			}
		}
		return null;
	}
	
	public void move(int oldIndex, int newIndex) {
		moveItemInList(newIndex, oldIndex, this.namedQueries, NAMED_QUERIES_LIST);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this);
	}
	
	private NamedQueryImpl createNamedQuery(int index) {
		return NamedQueryImpl.createNestedNamedQuery(this, getMember(), index, getDeclarationAnnotationAdapter());
	}

	public static class NamedQueriesAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final NamedQueriesAnnotationDefinition INSTANCE = new NamedQueriesAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private NamedQueriesAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new NamedQueriesImpl(parent, (Type) member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
