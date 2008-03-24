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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NamedNativeQueries;
import org.eclipse.jpt.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NestableNamedNativeQuery;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class NamedNativeQueriesImpl extends AbstractResourceAnnotation<Type> implements NamedNativeQueries
{
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private List<NestableNamedNativeQuery> namedNativeQueries;
	
	protected NamedNativeQueriesImpl(JavaResourceNode parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.namedNativeQueries = new ArrayList<NestableNamedNativeQuery>();
	}
	
	public void initialize(CompilationUnit astRoot) {
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this);
	}

	public String getAnnotationName() {
		return NamedNativeQueries.ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return NamedNativeQueryAnnotation.ANNOTATION_NAME;
	}

	public String getElementName() {
		return "value";
	}
		
	public ListIterator<NestableNamedNativeQuery> nestedAnnotations() {
		return new CloneListIterator<NestableNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int nestedAnnotationsSize() {
		return this.namedNativeQueries.size();
	}	

	public NestableNamedNativeQuery addInternal(int index) {
		NestableNamedNativeQuery namedNativeQuery = createNamedNativeQuery(index);
		this.namedNativeQueries.add(index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	public NestableNamedNativeQuery add(int index) {
		NestableNamedNativeQuery namedNativeQuery = createNamedNativeQuery(index);
		add(index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void add(int index, NestableNamedNativeQuery query) {
		addItemToList(index, query, this.namedNativeQueries, NamedNativeQueries.NAMED_NATIVE_QUERIES_LIST);
	}

	public void remove(NestableNamedNativeQuery query) {
		removeItemFromList(query, this.namedNativeQueries, NamedNativeQueries.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void remove(int index) {
		removeItemFromList(index, this.namedNativeQueries, NamedNativeQueries.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public int indexOf(NestableNamedNativeQuery query) {
		return this.namedNativeQueries.indexOf(query);
	}
	
	public NestableNamedNativeQuery nestedAnnotationAt(int index) {
		return this.namedNativeQueries.get(index);
	}
	
	public NestableNamedNativeQuery nestedAnnotationFor(org.eclipse.jdt.core.dom.Annotation jdtAnnotation) {
		for (NestableNamedNativeQuery namedQuery : this.namedNativeQueries) {
			if (jdtAnnotation == namedQuery.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
				return namedQuery;
			}
		}
		return null;
	}
	
	public void move(int targetIndex, int sourceIndex) {
		moveItemInList(targetIndex, sourceIndex, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void moveInternal(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedNativeQueries, targetIndex, sourceIndex);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this);
	}
	
	private NamedNativeQueryImpl createNamedNativeQuery(int index) {
		return NamedNativeQueryImpl.createNestedNamedNativeQuery(this, getMember(), index, getDeclarationAnnotationAdapter());
	}

	
	public static class NamedNativeQueriesAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final NamedNativeQueriesAnnotationDefinition INSTANCE = new NamedNativeQueriesAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private NamedNativeQueriesAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new NamedNativeQueriesImpl(parent, (Type) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
