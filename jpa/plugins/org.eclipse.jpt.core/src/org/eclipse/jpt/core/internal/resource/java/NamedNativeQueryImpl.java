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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableNamedNativeQuery;
import org.eclipse.jpt.core.resource.java.NestableQueryHint;

public class NamedNativeQueryImpl extends AbstractNamedQuery
	implements NestableNamedNativeQuery
{

	public static final SimpleDeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	// hold this so we can get the 'resultClass' text range
	private final DeclarationAnnotationElementAdapter<String> resultClassDeclarationAdapter;

	// hold this so we can get the 'resultSetMapping' text range
	private final DeclarationAnnotationElementAdapter<String> resultSetMappingDeclarationAdapter;


	private final AnnotationElementAdapter<String> resultClassAdapter;

	private final AnnotationElementAdapter<String> resultSetMappingAdapter;

	private String resultClass;
	
	private String fullyQualifiedResultClass;
	
	private String resultSetMapping;
	
	protected NamedNativeQueryImpl(JavaResourceNode parent, Type type, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
		this.resultClassDeclarationAdapter = resultClassAdapter(daa);
		this.resultClassAdapter = this.buildAdapter(this.resultClassDeclarationAdapter);
		this.resultSetMappingDeclarationAdapter = resultSetMappingAdapter(daa);
		this.resultSetMappingAdapter = this.buildAdapter(this.resultSetMappingDeclarationAdapter);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.resultClass = this.resultClass(astRoot);
		this.fullyQualifiedResultClass = this.fullyQualifiedResultClass(astRoot);
		this.resultSetMapping = this.resultSetMapping(astRoot);
	}
	

	// ********** initialization **********
	protected DeclarationAnnotationElementAdapter<String> resultClassAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(daa, JPA.NAMED_NATIVE_QUERY__RESULT_CLASS, SimpleTypeStringExpressionConverter.instance());
	}

	protected DeclarationAnnotationElementAdapter<String> resultSetMappingAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JPA.NAMED_NATIVE_QUERY__RESULT_SET_MAPPING);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
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
		NamedNativeQueryAnnotation oldNamedQuery = (NamedNativeQueryAnnotation) oldAnnotation;
		setResultClass(oldNamedQuery.getResultClass());
		setResultSetMapping(oldNamedQuery.getResultSetMapping());
	}
	
	public String getResultClass() {
		return this.resultClass;
	}
	
	public void setResultClass(String newResultClass) {
		String oldResultClass = this.resultClass;
		this.resultClass = newResultClass;
		this.resultClassAdapter.setValue(newResultClass);
		firePropertyChanged(RESULT_CLASS_PROPERTY, oldResultClass, newResultClass);
	}
	
	public String getFullyQualifiedResultClass()  {
		return this.fullyQualifiedResultClass;
	}
	
	protected void setFullyQualifiedResultClass(String newQualifiedResultClass) {
		String oldFullyQualifiedResultClass = this.fullyQualifiedResultClass;
		this.fullyQualifiedResultClass = newQualifiedResultClass;
		firePropertyChanged(FULLY_QUALIFIED_RESULT_CLASS_PROPERTY, oldFullyQualifiedResultClass, newQualifiedResultClass);
	}

	public String getResultSetMapping() {
		return this.resultSetMapping;
	}
	
	public void setResultSetMapping(String newResultSetMapping) {
		String oldResultSetMapping = this.resultSetMapping;
		this.resultSetMapping = newResultSetMapping;
		this.resultSetMappingAdapter.setValue(newResultSetMapping);
		firePropertyChanged(RESULT_SET_MAPPING_PROPERTY, oldResultSetMapping, newResultSetMapping);
	}

	public TextRange resultClassTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.resultClassDeclarationAdapter, astRoot);
	}
	
	public TextRange resultSetMappingTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.resultSetMappingDeclarationAdapter, astRoot);
	}
	
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setResultClass(this.resultClass(astRoot));
		this.setFullyQualifiedResultClass(this.fullyQualifiedResultClass(astRoot));
		this.setResultSetMapping(this.resultSetMapping(astRoot));
	}

	protected String resultClass(CompilationUnit astRoot) {
		return this.resultClassAdapter.getValue(astRoot);
	}
	
	protected String resultSetMapping(CompilationUnit astRoot) {
		return this.resultSetMappingAdapter.getValue(astRoot);
	}
	
	protected String fullyQualifiedResultClass(CompilationUnit astRoot) {
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
	static NamedNativeQueryImpl createNamedNativeQuery(JavaResourceNode parent, Type type) {
		return new NamedNativeQueryImpl(parent, type, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(type, DECLARATION_ANNOTATION_ADAPTER));
	}

	static NamedNativeQueryImpl createNestedNamedNativeQuery(JavaResourceNode parent, Type type, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(type, idaa);
		return new NamedNativeQueryImpl(parent, type, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter namedQueriesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedQueriesAdapter, index, JPA.NAMED_NATIVE_QUERY);
	}
	
	public static class NamedNativeQueryAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final NamedNativeQueryAnnotationDefinition INSTANCE = new NamedNativeQueryAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private NamedNativeQueryAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return NamedNativeQueryImpl.createNamedNativeQuery(parent, (Type) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
