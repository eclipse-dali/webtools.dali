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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NamedQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NestableNamedQueryAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.NamedQueries
 */
public class SourceNamedQueriesAnnotation
	extends SourceAnnotation<Type>
	implements NamedQueriesAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final Vector<NestableNamedQueryAnnotation> namedQueries = new Vector<NestableNamedQueryAnnotation>();


	public SourceNamedQueriesAnnotation(JavaResourceNode parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		AnnotationContainerTools.initialize(this, astRoot);
	}

	public void update(CompilationUnit astRoot) {
		AnnotationContainerTools.update(this, astRoot);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.namedQueries);
	}


	// ********** AnnotationContainer implementation **********

	public String getContainerAnnotationName() {
		return this.getAnnotationName();
	}

	public org.eclipse.jdt.core.dom.Annotation getContainerJdtAnnotation(CompilationUnit astRoot) {
		return this.getJdtAnnotation(astRoot);
	}

	public String getElementName() {
		return JPA.NAMED_QUERIES__VALUE;
	}

	public String getNestableAnnotationName() {
		return NamedQueryAnnotation.ANNOTATION_NAME;
	}

	public ListIterator<NestableNamedQueryAnnotation> nestedAnnotations() {
		return new CloneListIterator<NestableNamedQueryAnnotation>(this.namedQueries);
	}

	public int nestedAnnotationsSize() {
		return this.namedQueries.size();
	}

	public NestableNamedQueryAnnotation addNestedAnnotationInternal() {
		NestableNamedQueryAnnotation namedQuery = this.buildNamedQuery(this.namedQueries.size());
		this.namedQueries.add(namedQuery);
		return namedQuery;
	}

	protected NestableNamedQueryAnnotation buildNamedQuery(int index) {
		return SourceNamedQueryAnnotation.createNestedNamedQuery(this, member, index, this.daa);
	}

	public void nestedAnnotationAdded(int index, NestableNamedQueryAnnotation nestedAnnotation) {
		this.fireItemAdded(NAMED_QUERIES_LIST, index, nestedAnnotation);
	}

	public NestableNamedQueryAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.namedQueries, targetIndex, sourceIndex).get(targetIndex);
	}

	public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
		this.fireItemMoved(NAMED_QUERIES_LIST, targetIndex, sourceIndex);
	}

	public NestableNamedQueryAnnotation removeNestedAnnotationInternal(int index) {
		return this.namedQueries.remove(index);
	}

	public void nestedAnnotationRemoved(int index, NestableNamedQueryAnnotation nestedAnnotation) {
		this.fireItemRemoved(NAMED_QUERIES_LIST, index, nestedAnnotation);
	}

}
