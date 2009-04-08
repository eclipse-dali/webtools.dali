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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableQueryHintAnnotation;
import org.eclipse.jpt.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 * javax.persistence.QueryHint
 */
public final class SourceQueryHintAnnotation
	extends SourceAnnotation<Type>
	implements NestableQueryHintAnnotation
{
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;

	private final DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;


	public SourceQueryHintAnnotation(JavaResourceNode parent, Type type, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, type, idaa, new MemberIndexedAnnotationAdapter(type, idaa));
		this.nameDeclarationAdapter = this.buildNameAdapter(idaa);
		this.nameAdapter = this.buildAdapter(this.nameDeclarationAdapter);
		this.valueDeclarationAdapter = this.buildValueAdapter(idaa);
		this.valueAdapter = this.buildAdapter(this.valueDeclarationAdapter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	private AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(this.member, daea);
	}

	private DeclarationAnnotationElementAdapter<String> buildNameAdapter(DeclarationAnnotationAdapter adapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(adapter, JPA.QUERY_HINT__NAME);
	}

	private DeclarationAnnotationElementAdapter<String> buildValueAdapter(DeclarationAnnotationAdapter adapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(adapter, JPA.QUERY_HINT__VALUE);
	}

	public void initialize(CompilationUnit astRoot) {
		this.name = this.buildName(astRoot);
		this.value = this.buildValue(astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.setName(this.buildName(astRoot));
		this.setValue(this.buildValue(astRoot));
	}

	public IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** QueryHintAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.attributeValueHasNotChanged(this.name, name)) {
			return;
		}
		String old = this.name;
		this.name = name;
		this.nameAdapter.setValue(name);
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	// ***** value
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		if (this.attributeValueHasNotChanged(this.value, value)) {
			return;
		}
		String old = this.value;
		this.value = value;
		this.valueAdapter.setValue(value);
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private String buildValue(CompilationUnit astRoot) {
		return this.valueAdapter.getValue(astRoot);
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.valueDeclarationAdapter, astRoot);
	}


	// ********** NestableAnnotation implementation **********

	public void initializeFrom(NestableAnnotation oldAnnotation) {
		QueryHintAnnotation oldHint = (QueryHintAnnotation) oldAnnotation;
		this.setName(oldHint.getName());
		this.setValue(oldHint.getValue());
	}

	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}


	// ********** static methods **********

	static SourceQueryHintAnnotation createNamedQueryQueryHint(JavaResourceNode parent, Type type,  DeclarationAnnotationAdapter namedQueryAdapter, int index) {
		return new SourceQueryHintAnnotation(parent, type, buildNamedQueryQueryHintAnnotationAdapter(namedQueryAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildNamedQueryQueryHintAnnotationAdapter(DeclarationAnnotationAdapter namedQueryAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedQueryAdapter, JPA.NAMED_QUERY__HINTS, index, JPA.QUERY_HINT);
	}

	static SourceQueryHintAnnotation createNamedNativeQueryQueryHint(JavaResourceNode parent, Type type, DeclarationAnnotationAdapter namedNativeQueryAdapter, int index) {
		return new SourceQueryHintAnnotation(parent, type, buildNamedNativeQueryQueryHintAnnotationAdapter(namedNativeQueryAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildNamedNativeQueryQueryHintAnnotationAdapter(DeclarationAnnotationAdapter namedNativeQueryAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedNativeQueryAdapter, JPA.NAMED_NATIVE_QUERY__HINTS, index, JPA.QUERY_HINT);
	}

}
