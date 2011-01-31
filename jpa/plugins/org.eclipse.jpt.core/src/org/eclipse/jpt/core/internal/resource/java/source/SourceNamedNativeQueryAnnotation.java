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

import java.util.Map;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableNamedNativeQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NestableQueryHintAnnotation;

/**
 * <code>javax.persistence.NamedNativeQuery</code>
 */
public final class SourceNamedNativeQueryAnnotation
	extends SourceQueryAnnotation
	implements NestableNamedNativeQueryAnnotation
{
	public static final SimpleDeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private DeclarationAnnotationElementAdapter<String> resultClassDeclarationAdapter;
	private AnnotationElementAdapter<String> resultClassAdapter;
	private String resultClass;

	/**
	 * @see org.eclipse.jpt.core.internal.resource.java.source.SourceIdClassAnnotation#fullyQualifiedClassName
	 */
	private String fullyQualifiedResultClassName;
	// we need a flag since the f-q name can be null
	private boolean fqResultClassNameStale = true;

	private DeclarationAnnotationElementAdapter<String> resultSetMappingDeclarationAdapter;
	private AnnotationElementAdapter<String> resultSetMappingAdapter;
	private String resultSetMapping;


	public SourceNamedNativeQueryAnnotation(JavaResourceNode parent, Type type, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
		this.resultClassDeclarationAdapter = this.buildResultClassDeclarationAdapter();
		this.resultClassAdapter = this.buildResultClassAdapter();
		this.resultSetMappingDeclarationAdapter = this.buildResultSetMappingAdapter(daa);
		this.resultSetMappingAdapter = this.buildResultSetMappingAdapter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.resultClass = this.buildResultClass(astRoot);
		this.resultSetMapping = this.buildResultSetMapping(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncResultClass(this.buildResultClass(astRoot));
		this.syncResultSetMapping(this.buildResultSetMapping(astRoot));
	}


	// ********** AbstractBaseNamedQueryAnnotation implementation **********

	@Override
	String getNameElementName() {
		return JPA.NAMED_NATIVE_QUERY__NAME;
	}

	@Override
	String getQueryElementName() {
		return JPA.NAMED_NATIVE_QUERY__QUERY;
	}

	@Override
	String getHintsElementName() {
		return JPA.NAMED_NATIVE_QUERY__HINTS;
	}

	@Override
	NestableQueryHintAnnotation buildHint(int index) {
		return SourceQueryHintAnnotation.createNamedNativeQueryQueryHint(this, this.annotatedElement, this.daa, index);
	}


	// ********** NamedNativeQueryAnnotation implementation **********

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

	private String buildResultClass(CompilationUnit astRoot) {
		return this.resultClassAdapter.getValue(astRoot);
	}

	public TextRange getResultClassTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.resultClassDeclarationAdapter, astRoot);
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

	private String buildResultSetMapping(CompilationUnit astRoot) {
		return this.resultSetMappingAdapter.getValue(astRoot);
	}

	public TextRange getResultSetMappingTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.resultSetMappingDeclarationAdapter, astRoot);
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
				(this.resultClass == null) &&
				(this.resultSetMapping == null);
	}

	@Override
	protected void rebuildAdapters() {
		super.rebuildAdapters();
		this.resultClassDeclarationAdapter = this.buildResultClassDeclarationAdapter();
		this.resultClassAdapter = this.buildResultClassAdapter();
		this.resultSetMappingDeclarationAdapter = this.buildResultSetMappingAdapter(daa);
		this.resultSetMappingAdapter = this.buildResultSetMappingAdapter();
	}

	@Override
	public void storeOn(Map<String, Object> map) {
		super.storeOn(map);
		map.put(RESULT_CLASS_PROPERTY, this.resultClass);
		this.resultClass = null;
		map.put(RESULT_SET_MAPPING_PROPERTY, this.resultSetMapping);
		this.resultSetMapping = null;
	}

	@Override
	public void restoreFrom(Map<String, Object> map) {
		super.restoreFrom(map);
		this.setResultClass((String) map.get(RESULT_CLASS_PROPERTY));
		this.setResultSetMapping((String) map.get(RESULT_SET_MAPPING_PROPERTY));
	}


	// ********** static methods **********

	public static SourceNamedNativeQueryAnnotation createNamedNativeQuery(JavaResourceNode parent, Type type) {
		return new SourceNamedNativeQueryAnnotation(parent, type, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(type, DECLARATION_ANNOTATION_ADAPTER));
	}

	static SourceNamedNativeQueryAnnotation createNestedNamedNativeQuery(JavaResourceNode parent, Type type, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter, ANNOTATION_NAME);
		IndexedAnnotationAdapter annotationAdapter = new ElementIndexedAnnotationAdapter(type, idaa);
		return new SourceNamedNativeQueryAnnotation(parent, type, idaa, annotationAdapter);
	}
}
