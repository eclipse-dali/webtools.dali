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

public class NamedNativeQueriesImpl extends AbstractAnnotationResource<Type> implements NamedNativeQueries
{
	private static final String ANNOTATION_NAME = JPA.NAMED_NATIVE_QUERIES;
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private List<NestableNamedNativeQuery> namedNativeQueries;
	
	protected NamedNativeQueriesImpl(JavaResource parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.namedNativeQueries = new ArrayList<NestableNamedNativeQuery>();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return JPA.NAMED_NATIVE_QUERY;
	}
		
	public ListIterator<NestableNamedNativeQuery> nestedAnnotations() {
		return new CloneListIterator<NestableNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int nestedAnnotationsSize() {
		return this.namedNativeQueries.size();
	}	

	public NestableNamedNativeQuery add(int index) {
		NestableNamedNativeQuery namedNativeQuery = createNamedNativeQuery(index);
		add(index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	private void add(int index, NestableNamedNativeQuery query) {
		this.namedNativeQueries.add(index, query);
		//TODO event notification
	}

	public void remove(NestableNamedNativeQuery query) {
		this.namedNativeQueries.remove(query);		
	}
	
	public void remove(int index) {
		this.namedNativeQueries.remove(index);
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
	
	public void move(int oldIndex, int newIndex) {
		this.namedNativeQueries.add(newIndex, this.namedNativeQueries.remove(oldIndex));
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

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new NamedNativeQueriesImpl(parent, (Type) member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
