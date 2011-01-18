/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import java.util.Vector;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NamedNativeQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableNamedNativeQueryAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;

/**
 * <code>javax.persistence.NamedNativeQueries</code>
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

	public void synchronizeWith(CompilationUnit astRoot) {
		AnnotationContainerTools.synchronize(this, astRoot);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				this.namedNativeQueries.isEmpty();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.namedNativeQueries);
	}


	// ********** AnnotationContainer implementation **********

	public String getElementName() {
		return JPA.NAMED_NATIVE_QUERIES__VALUE;
	}

	public String getNestedAnnotationName() {
		return NamedNativeQueryAnnotation.ANNOTATION_NAME;
	}

	public Iterable<NestableNamedNativeQueryAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<NestableNamedNativeQueryAnnotation>(this.namedNativeQueries);
	}

	public int getNestedAnnotationsSize() {
		return this.namedNativeQueries.size();
	}

	public void nestStandAloneAnnotation(NestableAnnotation standAloneAnnotation) {
		this.nestStandAloneAnnotation(standAloneAnnotation, this.namedNativeQueries.size());
	}

	private void nestStandAloneAnnotation(NestableAnnotation standAloneAnnotation, int index) {
		standAloneAnnotation.convertToNested(this, this.daa, index);
	}

	public void addNestedAnnotation(int index, NestableAnnotation annotation) {
		this.namedNativeQueries.add(index, (NestableNamedNativeQueryAnnotation) annotation);
	}

	public void convertLastNestedAnnotationToStandAlone() {
		this.namedNativeQueries.remove(0).convertToStandAlone();
	}

	public NestableNamedNativeQueryAnnotation addNestedAnnotation() {
		return this.addNestedAnnotation(this.namedNativeQueries.size());
	}

	private NestableNamedNativeQueryAnnotation addNestedAnnotation(int index) {
		NestableNamedNativeQueryAnnotation namedNativeQuery = this.buildNamedNativeQuery(index);
		this.namedNativeQueries.add(index, namedNativeQuery);
		return namedNativeQuery;
	}

	public void syncAddNestedAnnotation(Annotation astAnnotation) {
		int index = this.namedNativeQueries.size();
		NestableNamedNativeQueryAnnotation namedNativeQuery = this.addNestedAnnotation(index);
		namedNativeQuery.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
	}

	private NestableNamedNativeQueryAnnotation buildNamedNativeQuery(int index) {
		// pass the Java resource persistent member as the nested annotation's parent
		// since the nested annotation can be converted to stand-alone
		return SourceNamedNativeQueryAnnotation.createNestedNamedNativeQuery(this.parent, this.annotatedElement, index, this.daa);
	}

	public NestableNamedNativeQueryAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.namedNativeQueries, targetIndex, sourceIndex).get(targetIndex);
	}

	public NestableNamedNativeQueryAnnotation removeNestedAnnotation(int index) {
		return this.namedNativeQueries.remove(index);
	}

	public void syncRemoveNestedAnnotations(int index) {
		this.removeItemsFromList(index, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}
}
