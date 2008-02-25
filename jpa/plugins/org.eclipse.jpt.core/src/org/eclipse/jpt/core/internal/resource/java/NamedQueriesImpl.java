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
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NamedQueries;
import org.eclipse.jpt.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NestableNamedQuery;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class NamedQueriesImpl extends AbstractAnnotationResource<Type> implements NamedQueries
{
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(NamedQueries.ANNOTATION_NAME);

	private List<NestableNamedQuery> namedQueries;
	
	protected NamedQueriesImpl(JavaResourceNode parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.namedQueries = new ArrayList<NestableNamedQuery>();
	}

	public void initialize(CompilationUnit astRoot) {
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this);
	}
	
	public String getAnnotationName() {
		return NamedQueries.ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return NamedQueryAnnotation.ANNOTATION_NAME;
	}

	public String getElementName() {
		return "value";
	}
		
	public ListIterator<NestableNamedQuery> nestedAnnotations() {
		return new CloneListIterator<NestableNamedQuery>(this.namedQueries);
	}
	
	public int nestedAnnotationsSize() {
		return this.namedQueries.size();
	}	

	public NestableNamedQuery addInternal(int index) {
		NestableNamedQuery namedQuery = createNamedQuery(index);
		this.namedQueries.add(index, namedQuery);
		return namedQuery;
	}
	
	public NestableNamedQuery add(int index) {
		NestableNamedQuery namedQuery = createNamedQuery(index);
		add(index, namedQuery);
		return namedQuery;
	}
	
	protected void add(int index, NestableNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, NamedQueries.NAMED_QUERIES_LIST);
	}

	public void remove(NestableNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, NamedQueries.NAMED_QUERIES_LIST);
	}
	
	public void remove(int index) {
		removeItemFromList(index, this.namedQueries, NamedQueries.NAMED_QUERIES_LIST);
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
	
	public void move(int targetIndex, int sourceIndex) {
		moveItemInList(targetIndex, sourceIndex, this.namedQueries, NamedQueries.NAMED_QUERIES_LIST);
	}
	
	public void moveInternal(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedQueries, targetIndex, sourceIndex);
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

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new NamedQueriesImpl(parent, (Type) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
