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
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public class QueryHintImpl extends AbstractAnnotationResource<Type>
	implements NestableQueryHint
{

	private final AnnotationElementAdapter<String> nameAdapter;

	private final AnnotationElementAdapter<String> valueAdapter;

	private String name;
	
	private String value;
	
	public QueryHintImpl(JavaResource parent, Type type, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, type, idaa, new MemberIndexedAnnotationAdapter(type, idaa));
		this.nameAdapter = this.buildAdapter(nameAdapter(idaa));
		this.valueAdapter = this.buildAdapter(valueAdapter(idaa));
	}
	
	// ********** initialization **********
	protected AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(getMember(), daea);
	}

	protected DeclarationAnnotationElementAdapter<String> nameAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JPA.QUERY_HINT__NAME);
	}

	protected DeclarationAnnotationElementAdapter<String> valueAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JPA.QUERY_HINT__VALUE);
	}

	public String getAnnotationName() {
		return JPA.QUERY_HINT;
	}
	
	@Override
	public IndexedAnnotationAdapter getAnnotationAdapter() {
		return (IndexedAnnotationAdapter) super.getAnnotationAdapter();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
		this.nameAdapter.setValue(name);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
		this.valueAdapter.setValue(value);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		this.setName(this.nameAdapter.getValue(astRoot));
		this.setValue(this.valueAdapter.getValue(astRoot));
	}
	
	// ********** persistence model -> java annotations **********
	public void moveAnnotation(int newIndex) {
		getAnnotationAdapter().moveAnnotation(newIndex);
	}

	public void initializeFrom(NestableAnnotation oldAnnotation) {
		// TODO Auto-generated method stub
		
	}
	
	// ********** static methods **********
	static QueryHintImpl createNamedQueryQueryHint(JavaResource parent, Type type, DeclarationAnnotationAdapter namedQueryAdapter, int index) {
		return new QueryHintImpl(parent, type, buildNamedQueryQueryHintAnnotationAdapter(namedQueryAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildNamedQueryQueryHintAnnotationAdapter(DeclarationAnnotationAdapter namedQueryAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedQueryAdapter, JPA.NAMED_QUERY__HINTS, index, JPA.QUERY_HINT);
	}

	static QueryHintImpl createNamedNativeQueryQueryHint(JavaResource parent, Type type, DeclarationAnnotationAdapter namedNativeQueryAdapter, int index) {
		return new QueryHintImpl(parent, type, buildNamedNativeQueryQueryHintAnnotationAdapter(namedNativeQueryAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildNamedNativeQueryQueryHintAnnotationAdapter(DeclarationAnnotationAdapter namedNativeQueryAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedNativeQueryAdapter, JPA.NAMED_NATIVE_QUERY__HINTS, index, JPA.QUERY_HINT);
	}
}
