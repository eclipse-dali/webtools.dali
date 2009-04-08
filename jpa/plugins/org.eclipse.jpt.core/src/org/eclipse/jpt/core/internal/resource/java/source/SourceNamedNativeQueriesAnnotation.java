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
import org.eclipse.jpt.core.resource.java.NamedNativeQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NestableNamedNativeQueryAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.NamedNativeQueries
 */
public final class SourceNamedNativeQueriesAnnotation
	extends SourceAnnotation<Type>
	implements NamedNativeQueriesAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final Vector<NestableNamedNativeQueryAnnotation> namedNativeQueries = new Vector<NestableNamedNativeQueryAnnotation>();


	public SourceNamedNativeQueriesAnnotation(JavaResourceNode parent, Type type) {
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
		sb.append(this.namedNativeQueries);
	}


	// ********** AnnotationContainer implementation **********

	public String getContainerAnnotationName() {
		return this.getAnnotationName();
	}

	public org.eclipse.jdt.core.dom.Annotation getContainerJdtAnnotation(CompilationUnit astRoot) {
		return this.getJdtAnnotation(astRoot);
	}

	public String getElementName() {
		return JPA.NAMED_NATIVE_QUERIES__VALUE;
	}

	public String getNestableAnnotationName() {
		return NamedNativeQueryAnnotation.ANNOTATION_NAME;
	}

	public ListIterator<NestableNamedNativeQueryAnnotation> nestedAnnotations() {
		return new CloneListIterator<NestableNamedNativeQueryAnnotation>(this.namedNativeQueries);
	}

	public int nestedAnnotationsSize() {
		return this.namedNativeQueries.size();
	}

	public NestableNamedNativeQueryAnnotation addNestedAnnotationInternal() {
		NestableNamedNativeQueryAnnotation namedNativeQuery = this.buildNamedNativeQuery(this.namedNativeQueries.size());
		this.namedNativeQueries.add(namedNativeQuery);
		return namedNativeQuery;
	}

	private NestableNamedNativeQueryAnnotation buildNamedNativeQuery(int index) {
		return SourceNamedNativeQueryAnnotation.createNestedNamedNativeQuery(this, this.member, index, this.daa);
	}

	public void nestedAnnotationAdded(int index, NestableNamedNativeQueryAnnotation nestedAnnotation) {
		this.fireItemAdded(NAMED_NATIVE_QUERIES_LIST, index, nestedAnnotation);
	}

	public NestableNamedNativeQueryAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.namedNativeQueries, targetIndex, sourceIndex).get(targetIndex);
	}

	public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
		this.fireItemMoved(NAMED_NATIVE_QUERIES_LIST, targetIndex, sourceIndex);
	}

	public NestableNamedNativeQueryAnnotation removeNestedAnnotationInternal(int index) {
		return this.namedNativeQueries.remove(index);
	}

	public void nestedAnnotationRemoved(int index, NestableNamedNativeQueryAnnotation nestedAnnotation) {
		this.fireItemRemoved(NAMED_NATIVE_QUERIES_LIST, index, nestedAnnotation);
	}

}
