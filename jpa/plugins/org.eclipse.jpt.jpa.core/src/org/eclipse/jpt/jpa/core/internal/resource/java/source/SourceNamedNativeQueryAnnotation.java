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
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;

/**
 * <code>javax.persistence.NamedNativeQuery</code>
 */
public final class SourceNamedNativeQueryAnnotation
	extends SourceQueryAnnotation
	implements NamedNativeQueryAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.NAMED_NATIVE_QUERIES);

	DeclarationAnnotationElementAdapter<String> queryDeclarationAdapter;
	AnnotationElementAdapter<String> queryAdapter;
	String query;
	List<TextRange> queryTextRanges;

	private DeclarationAnnotationElementAdapter<String> resultClassDeclarationAdapter;
	private AnnotationElementAdapter<String> resultClassAdapter;
	private String resultClass;
	private TextRange resultClassTextRange;

	/**
	 * @see org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceIdClassAnnotation#fullyQualifiedClassName
	 */
	private String fullyQualifiedResultClassName;
	// we need a flag since the f-q name can be null
	private boolean fqResultClassNameStale = true;

	private DeclarationAnnotationElementAdapter<String> resultSetMappingDeclarationAdapter;
	private AnnotationElementAdapter<String> resultSetMappingAdapter;
	private String resultSetMapping;
	private TextRange resultSetMappingTextRange;

	public static SourceNamedNativeQueryAnnotation buildSourceNamedNativeQueryAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildNamedNativeQueryDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildNamedNativeQueryAnnotationAdapter(element, idaa);
		return new SourceNamedNativeQueryAnnotation(
			parent,
			element,
			idaa,
			iaa);
	}

	private SourceNamedNativeQueryAnnotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement element,
			IndexedDeclarationAnnotationAdapter daa,
			IndexedAnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.queryDeclarationAdapter = this.buildQueryDeclarationAdapter();
		this.queryAdapter = this.buildQueryAdapter();
		this.resultClassDeclarationAdapter = this.buildResultClassDeclarationAdapter();
		this.resultClassAdapter = this.buildResultClassAdapter();
		this.resultSetMappingDeclarationAdapter = this.buildResultSetMappingAdapter(daa);
		this.resultSetMappingAdapter = this.buildResultSetMappingAdapter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		
		this.query = this.buildQuery(astAnnotation);
		this.queryTextRanges = this.buildQueryTextRanges(astAnnotation);

		this.resultClass = this.buildResultClass(astAnnotation);
		this.resultClassTextRange = this.buildResultClassTextRange(astAnnotation);

		this.resultSetMapping = this.buildResultSetMapping(astAnnotation);
		this.resultSetMappingTextRange = this.buildResultSetMappingTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		
		this.syncQuery(this.buildQuery(astAnnotation));
		this.queryTextRanges = this.buildQueryTextRanges(astAnnotation);

		this.syncResultClass(this.buildResultClass(astAnnotation));
		this.resultClassTextRange = this.buildResultClassTextRange(astAnnotation);

		this.syncResultSetMapping(this.buildResultSetMapping(astAnnotation));
		this.resultSetMappingTextRange = this.buildResultSetMappingTextRange(astAnnotation);
	}


	// ********** AbstractNamedNativeQueryAnnotation implementation **********

	@Override
	public String getNameElementName() {
		return JPA.NAMED_NATIVE_QUERY__NAME;
	}

	@Override
	public String getHintsElementName() {
		return JPA.NAMED_NATIVE_QUERY__HINTS;
	}

	@Override
	public QueryHintAnnotation buildHint(int index) {
		return SourceQueryHintAnnotation.buildNamedNativeQueryQueryHint(this, this.annotatedElement, this.daa, index);
	}


	// ********** NamedNativeQueryAnnotation implementation **********

	String getQueryElementName() {
		return JPA.NAMED_NATIVE_QUERY__QUERY;
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

	// ***** result class
	public String getResultClass() {
		return this.resultClass;
	}

	public void setResultClass(String resultClass) {
		if (this.attributeValueHasChanged(this.resultClass, resultClass)) {
			this.resultClass = resultClass;
			this.fqResultClassNameStale = true;
			this.resultClassAdapter.setValue(resultClass);
		}
	}

	private void syncResultClass(String astResultClass) {
		if (this.attributeValueHasChanged(this.resultClass, astResultClass)) {
			this.syncResultClass_(astResultClass);
		}
	}

	private void syncResultClass_(String astResultClass) {
		String old = this.resultClass;
		this.resultClass = astResultClass;
		this.fqResultClassNameStale = true;
		this.firePropertyChanged(RESULT_CLASS_PROPERTY, old, astResultClass);
	}

	private String buildResultClass(Annotation astAnnotation) {
		return this.resultClassAdapter.getValue(astAnnotation);
	}

	public TextRange getResultClassTextRange() {
		return this.resultClassTextRange;
	}

	private TextRange buildResultClassTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.resultClassDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildResultClassDeclarationAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(this.daa, JPA.NAMED_NATIVE_QUERY__RESULT_CLASS, SimpleTypeStringExpressionConverter.instance());
	}

	private AnnotationElementAdapter<String> buildResultClassAdapter() {
		return this.buildStringElementAdapter(this.resultClassDeclarationAdapter);
	}

	// ***** fully-qualified result class name
	public String getFullyQualifiedResultClassName()  {
		if (this.fqResultClassNameStale) {
			this.fullyQualifiedResultClassName = this.buildFullyQualifiedResultClassName();
			this.fqResultClassNameStale = false;
		}
		return this.fullyQualifiedResultClassName;
	}

	private String buildFullyQualifiedResultClassName() {
		return (this.resultClass == null) ? null : this.buildFullyQualifiedResultClassName_();
	}

	private String buildFullyQualifiedResultClassName_() {
		return ASTTools.resolveFullyQualifiedName(this.resultClassAdapter.getExpression(this.buildASTRoot()));
	}

	// ***** result set mapping
	public String getResultSetMapping() {
		return this.resultSetMapping;
	}

	public void setResultSetMapping(String resultSetMapping) {
		if (this.attributeValueHasChanged(this.resultSetMapping, resultSetMapping)) {
			this.resultSetMapping = resultSetMapping;
			this.resultSetMappingAdapter.setValue(resultSetMapping);
		}
	}

	private void syncResultSetMapping(String astResultSetMapping) {
		String old = this.resultSetMapping;
		this.resultSetMapping = astResultSetMapping;
		this.firePropertyChanged(RESULT_SET_MAPPING_PROPERTY, old, astResultSetMapping);
	}

	private String buildResultSetMapping(Annotation astAnnotation) {
		return this.resultSetMappingAdapter.getValue(astAnnotation);
	}

	public TextRange getResultSetMappingTextRange() {
		return this.resultSetMappingTextRange;
	}

	private TextRange buildResultSetMappingTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.resultSetMappingDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildResultSetMappingAdapter(DeclarationAnnotationAdapter daAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daAdapter, JPA.NAMED_NATIVE_QUERY__RESULT_SET_MAPPING);
	}

	private AnnotationElementAdapter<String> buildResultSetMappingAdapter() {
		return this.buildStringElementAdapter(this.resultSetMappingDeclarationAdapter);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.query == null) &&
				(this.resultClass == null) &&
				(this.resultSetMapping == null);
	}

	// ********** static methods **********

	private static IndexedAnnotationAdapter buildNamedNativeQueryAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	private static IndexedDeclarationAnnotationAdapter buildNamedNativeQueryDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				ANNOTATION_NAME);
		return idaa;
	}
}
