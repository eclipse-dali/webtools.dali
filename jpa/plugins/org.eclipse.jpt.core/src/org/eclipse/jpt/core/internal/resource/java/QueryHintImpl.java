/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableQueryHint;
import org.eclipse.jpt.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

public class QueryHintImpl extends AbstractResourceAnnotation<Type>
	implements NestableQueryHint
{

	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	
	// hold this so we can get the 'value' text range
	private final DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	
	private final AnnotationElementAdapter<String> nameAdapter;

	private final AnnotationElementAdapter<String> valueAdapter;

	private String name;
	
	private String value;
	
	public QueryHintImpl(JavaResourceNode parent, Type type, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, type, idaa, new MemberIndexedAnnotationAdapter(type, idaa));
		this.nameDeclarationAdapter = this.nameAdapter(idaa);
		this.nameAdapter = this.buildAdapter(this.nameDeclarationAdapter);
		this.valueDeclarationAdapter = this.valueAdapter(idaa);
		this.valueAdapter = this.buildAdapter(this.valueDeclarationAdapter);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.name = this.name(astRoot);
		this.value = this.value(astRoot);		
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
		return ANNOTATION_NAME;
	}
	
	@Override
	public IndexedAnnotationAdapter getAnnotationAdapter() {
		return (IndexedAnnotationAdapter) super.getAnnotationAdapter();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.nameAdapter.setValue(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		this.valueAdapter.setValue(newValue);
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}

	public TextRange nameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.nameDeclarationAdapter, astRoot);
	}
	
	public TextRange valueTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.valueDeclarationAdapter, astRoot);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		this.setName(this.name(astRoot));
		this.setValue(this.value(astRoot));
	}
	
	protected String name(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}
	
	protected String value(CompilationUnit astRoot) {
		return this.valueAdapter.getValue(astRoot);
	}

	// ********** persistence model -> java annotations **********
	public void moveAnnotation(int newIndex) {
		getAnnotationAdapter().moveAnnotation(newIndex);
	}

	public void initializeFrom(NestableAnnotation oldAnnotation) {
		QueryHintAnnotation oldQueryHint = (QueryHintAnnotation) oldAnnotation;
		setName(oldQueryHint.getName());
		setValue(oldQueryHint.getValue());
	}
	
	// ********** static methods **********
	static QueryHintImpl createNamedQueryQueryHint(JavaResourceNode parent, Type type,  DeclarationAnnotationAdapter namedQueryAdapter, int index) {
		return new QueryHintImpl(parent, type, buildNamedQueryQueryHintAnnotationAdapter(namedQueryAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildNamedQueryQueryHintAnnotationAdapter(DeclarationAnnotationAdapter namedQueryAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedQueryAdapter, JPA.NAMED_QUERY__HINTS, index, JPA.QUERY_HINT);
	}

	static QueryHintImpl createNamedNativeQueryQueryHint(JavaResourceNode parent, Type type, DeclarationAnnotationAdapter namedNativeQueryAdapter, int index) {
		return new QueryHintImpl(parent, type, buildNamedNativeQueryQueryHintAnnotationAdapter(namedNativeQueryAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildNamedNativeQueryQueryHintAnnotationAdapter(DeclarationAnnotationAdapter namedNativeQueryAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedNativeQueryAdapter, JPA.NAMED_NATIVE_QUERY__HINTS, index, JPA.QUERY_HINT);
	}
}
