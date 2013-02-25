/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import java.util.List;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;

/**
 * <code>javax.persistence.NamedQuery</code>
 */
public abstract class SourceNamedQueryAnnotation
	extends SourceQueryAnnotation
	implements NamedQueryAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.NAMED_QUERIES);

	DeclarationAnnotationElementAdapter<String> queryDeclarationAdapter;
	AnnotationElementAdapter<String> queryAdapter;
	String query;
	List<TextRange> queryTextRanges;
	
	protected SourceNamedQueryAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.queryDeclarationAdapter = this.buildQueryDeclarationAdapter();
		this.queryAdapter = this.buildQueryAdapter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		
		this.query = this.buildQuery(astAnnotation);
		this.queryTextRanges = this.buildQueryTextRanges(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		
		this.syncQuery(this.buildQuery(astAnnotation));
		this.queryTextRanges = this.buildQueryTextRanges(astAnnotation);
	}

	// ********** AbstractBaseNamedQueryAnnotation implementation **********

	@Override
	public String getNameElementName() {
		return JPA.NAMED_QUERY__NAME;
	}

	String getQueryElementName() {
		return JPA.NAMED_QUERY__QUERY;
	}

	@Override
	public String getHintsElementName() {
		return JPA.NAMED_QUERY__HINTS;
	}

	@Override
	public QueryHintAnnotation buildHint(int index) {
		return SourceQueryHintAnnotation.buildNamedQueryQueryHint(this, this.annotatedElement, this.daa, index);
	}

	// ***** query
	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		if (this.attributeValueHasChanged(this.query, query)) {
			this.query = query;
			this.queryAdapter.setValue(query);
		}
	}

	private void syncQuery(String annotationQuery) {
		String old = this.query;
		this.query = annotationQuery;
		this.firePropertyChanged(QUERY_PROPERTY, old, annotationQuery);
	}

	private String buildQuery(Annotation astAnnotation) {
		return this.queryAdapter.getValue(astAnnotation);
	}

	public List<TextRange> getQueryTextRanges() {
		return this.queryTextRanges;
	}

	private List<TextRange> buildQueryTextRanges(Annotation astAnnotation) {
		return this.getElementTextRanges(this.queryDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildQueryDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, this.getQueryElementName());
	}

	private AnnotationElementAdapter<String> buildQueryAdapter() {
		return this.buildStringElementAdapter(this.queryDeclarationAdapter);
	}
	
	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.query == null);
	}


	// ********** static methods **********

	protected static IndexedAnnotationAdapter buildNamedQueryAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	protected static IndexedDeclarationAnnotationAdapter buildNamedQueryDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				ANNOTATION_NAME);
		return idaa;
	}
}
