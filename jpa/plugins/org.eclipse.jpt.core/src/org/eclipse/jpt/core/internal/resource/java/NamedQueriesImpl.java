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
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class NamedQueriesImpl extends AbstractAnnotationResource<Type> implements NamedQueries
{
	private List<NestableNamedQuery> namedQueries;
	
	protected NamedQueriesImpl(JavaResource parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.namedQueries = new ArrayList<NestableNamedQuery>();
	}

	public String getAnnotationName() {
		return JPA.NAMED_QUERIES;
	}

	public String getNestableAnnotationName() {
		return JPA.NAMED_QUERY;
	}
		
	public ListIterator<NestableNamedQuery> nestedAnnotations() {
		return new CloneListIterator<NestableNamedQuery>(this.namedQueries);
	}
	
	public int nestedAnnotationsSize() {
		return this.namedQueries.size();
	}	

	public NestableNamedQuery add(int index) {
		NestableNamedQuery namedQuery = createNamedQuery(index);
		add(index, namedQuery);
		return namedQuery;
	}
	
	private void add(int index, NestableNamedQuery attributeOverride) {
		this.namedQueries.add(index, attributeOverride);
		//TODO event notification
	}

	public void remove(NestableNamedQuery attributeOverride) {
		this.namedQueries.remove(attributeOverride);		
	}
	
	public void remove(int index) {
		this.namedQueries.remove(index);
	}
	
	public int indexOf(NestableNamedQuery attributeOverride) {
		return this.namedQueries.indexOf(attributeOverride);
	}
	
	public NestableNamedQuery nestedAnnotationAt(int index) {
		return this.namedQueries.get(index);
	}
	
	public NestableNamedQuery nestedAnnotationFor(Annotation jdtAnnotation) {
		for (NestableNamedQuery namedQuery : this.namedQueries) {
			if (jdtAnnotation == namedQuery.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
				return namedQuery;
			}
		}
		return null;
	}
	
	public void move(int oldIndex, int newIndex) {
		this.namedQueries.add(newIndex, this.namedQueries.remove(oldIndex));
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this);
	}
	
	public NestableNamedQuery createNestedAnnotation(int index) {
		return createNamedQuery(index);
	}
	
	private NamedQueryImpl createNamedQuery(int index) {
		return NamedQueryImpl.createNestedNamedQuery(this, getMember(), index, getDeclarationAnnotationAdapter());
	}

}
