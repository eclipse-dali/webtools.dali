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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public class NamedNativeQueryImpl extends AbstractNamedQuery
	implements NestableNamedNativeQuery
{

	public static final SimpleDeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.NAMED_NATIVE_QUERY);

	private final AnnotationElementAdapter<String> resultClassAdapter;

	private final AnnotationElementAdapter<String> resultSetMappingAdapter;

	private String resultClass;
	
	private String fullyQualifiedResultClass;
	
	private String resultSetMapping;
	
	protected NamedNativeQueryImpl(JavaResource parent, Type type, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
		this.resultClassAdapter = this.buildAdapter(resultClassAdapter(daa));
		this.resultSetMappingAdapter = this.buildAdapter(resultSetMappingAdapter(daa));
	}

	// ********** initialization **********
	protected DeclarationAnnotationElementAdapter<String> resultClassAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(daa, JPA.NAMED_NATIVE_QUERY__RESULT_CLASS, SimpleTypeStringExpressionConverter.instance());
	}

	protected DeclarationAnnotationElementAdapter<String> resultSetMappingAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JPA.NAMED_NATIVE_QUERY__RESULT_SET_MAPPING);
	}
	
	public String getAnnotationName() {
		return JPA.NAMED_NATIVE_QUERY;
	}
	
	//************* AbstractNamedQuery implementation *************

	@Override
	protected String nameElementName() {
		return JPA.NAMED_NATIVE_QUERY__NAME;
	}

	@Override
	protected String queryElementName() {
		return JPA.NAMED_NATIVE_QUERY__QUERY;
	}
	
	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		NamedNativeQuery oldNamedQuery = (NamedNativeQuery) oldAnnotation;
		setResultClass(oldNamedQuery.getResultClass());
		setResultSetMapping(oldNamedQuery.getResultSetMapping());
	}
	
	public String getResultClass() {
		return this.resultClass;
	}
	
	public void setResultClass(String resultClass) {
		this.resultClass = resultClass;
		this.resultClassAdapter.setValue(resultClass);
	}
	
	public String getFullyQualifiedResultClass()  {
		return this.fullyQualifiedResultClass;
	}
	
	private void setFullyQualifiedResultClass(String qualifiedResultClass) {
		this.fullyQualifiedResultClass = qualifiedResultClass;
		//change notification
	}

	public String getResultSetMapping() {
		return this.resultSetMapping;
	}
	
	public void setResultSetMapping(String resultSetMapping) {
		this.resultSetMapping = resultSetMapping;
		this.resultSetMappingAdapter.setValue(resultSetMapping);
	}
	
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setResultClass(this.resultClassAdapter.getValue(astRoot));
		this.setFullyQualifiedResultClass(fullyQualifiedResultClass(astRoot));
		this.setResultSetMapping(this.resultSetMappingAdapter.getValue(astRoot));
	}

	private String fullyQualifiedResultClass(CompilationUnit astRoot) {
		if (getResultClass() == null) {
			return null;
		}
		return JDTTools.resolveFullyQualifiedName(this.resultClassAdapter.expression(astRoot));
	}

	@Override
	protected NestableQueryHint createQueryHint(int index) {
		return QueryHintImpl.createNamedNativeQueryQueryHint(this, this.getMember(), this.getDeclarationAnnotationAdapter(), index);
	}

	// ********** static methods **********
	static NamedNativeQueryImpl createNamedNativeQuery(JavaResource parent, Type type) {
		return new NamedNativeQueryImpl(parent, type, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(type, DECLARATION_ANNOTATION_ADAPTER));
	}

	static NamedNativeQueryImpl createNestedNamedNativeQuery(JavaResource parent, Type type, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(type, idaa);
		return new NamedNativeQueryImpl(parent, type, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter namedQueriesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedQueriesAdapter, index, JPA.NAMED_NATIVE_QUERY);
	}
}
