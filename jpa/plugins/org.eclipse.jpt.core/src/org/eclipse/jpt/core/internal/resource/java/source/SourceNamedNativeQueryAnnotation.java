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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableNamedNativeQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NestableQueryHintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 * javax.persistence.NamedNativeQuery
 */
public final class SourceNamedNativeQueryAnnotation
	extends SourceBaseNamedQueryAnnotation
	implements NestableNamedNativeQueryAnnotation
{
	public static final SimpleDeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final DeclarationAnnotationElementAdapter<String> resultClassDeclarationAdapter;
	private final AnnotationElementAdapter<String> resultClassAdapter;
	private String resultClass;

	private String fullyQualifiedResultClassName;

	private final DeclarationAnnotationElementAdapter<String> resultSetMappingDeclarationAdapter;
	private final AnnotationElementAdapter<String> resultSetMappingAdapter;
	private String resultSetMapping;


	public SourceNamedNativeQueryAnnotation(JavaResourceNode parent, Type type, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
		this.resultClassDeclarationAdapter = this.buildResultClassAdapter(daa);
		this.resultClassAdapter = this.buildAdapter(this.resultClassDeclarationAdapter);
		this.resultSetMappingDeclarationAdapter = this.buildResultSetMappingAdapter(daa);
		this.resultSetMappingAdapter = this.buildAdapter(this.resultSetMappingDeclarationAdapter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.resultClass = this.buildResultClass(astRoot);
		this.fullyQualifiedResultClassName = this.buildFullyQualifiedResultClassName(astRoot);
		this.resultSetMapping = this.buildResultSetMapping(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncResultClass(this.buildResultClass(astRoot));
		this.syncFullyQualifiedResultClassName(this.buildFullyQualifiedResultClassName(astRoot));
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
		return SourceQueryHintAnnotation.createNamedNativeQueryQueryHint(this, this.member, this.daa, index);
	}


	// ********** NamedNativeQueryAnnotation implementation **********

	// ***** result class
	public String getResultClass() {
		return this.resultClass;
	}

	public void setResultClass(String resultClass) {
		if (this.attributeValueHasChanged(this.resultClass, resultClass)) {
			this.resultClass = resultClass;
			this.resultClassAdapter.setValue(resultClass);
		}
	}

	private void syncResultClass(String astResultClass) {
		String old = this.resultClass;
		this.resultClass = astResultClass;
		this.firePropertyChanged(RESULT_CLASS_PROPERTY, old, astResultClass);
	}

	private String buildResultClass(CompilationUnit astRoot) {
		return this.resultClassAdapter.getValue(astRoot);
	}

	public TextRange getResultClassTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.resultClassDeclarationAdapter, astRoot);
	}

	private DeclarationAnnotationElementAdapter<String> buildResultClassAdapter(DeclarationAnnotationAdapter daAdapter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(daAdapter, JPA.NAMED_NATIVE_QUERY__RESULT_CLASS, SimpleTypeStringExpressionConverter.instance());
	}

	// ***** fully-qualified result class name
	public String getFullyQualifiedResultClassName()  {
		return this.fullyQualifiedResultClassName;
	}

	private void syncFullyQualifiedResultClassName(String astName) {
		String old = this.fullyQualifiedResultClassName;
		this.fullyQualifiedResultClassName = astName;
		this.firePropertyChanged(FULLY_QUALIFIED_RESULT_CLASS_NAME_PROPERTY, old, astName);
	}

	private String buildFullyQualifiedResultClassName(CompilationUnit astRoot) {
		return (this.resultClass == null) ? null : JDTTools.resolveFullyQualifiedName(this.resultClassAdapter.getExpression(astRoot));
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


	// ********** NestableAnnotation implementation **********

	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		NamedNativeQueryAnnotation oldQuery = (NamedNativeQueryAnnotation) oldAnnotation;
		this.setResultClass(oldQuery.getResultClass());
		this.setResultSetMapping(oldQuery.getResultSetMapping());
	}


	// ********** static methods **********

	public static SourceNamedNativeQueryAnnotation createNamedNativeQuery(JavaResourceNode parent, Type type) {
		return new SourceNamedNativeQueryAnnotation(parent, type, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(type, DECLARATION_ANNOTATION_ADAPTER));
	}

	static SourceNamedNativeQueryAnnotation createNestedNamedNativeQuery(JavaResourceNode parent, Type type, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(type, idaa);
		return new SourceNamedNativeQueryAnnotation(parent, type, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter namedQueriesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedQueriesAdapter, index, JPA.NAMED_NATIVE_QUERY);
	}

}
