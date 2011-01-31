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
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableQueryHintAnnotation;

/**
 * <code>javax.persistence.QueryHint</code>
 */
public final class SourceQueryHintAnnotation
	extends SourceAnnotation<Type>
	implements NestableQueryHintAnnotation
{
	private DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private AnnotationElementAdapter<String> nameAdapter;
	private String name;

	private DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	private AnnotationElementAdapter<String> valueAdapter;
	private String value;


	public SourceQueryHintAnnotation(JavaResourceNode parent, Type type, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, type, idaa, new ElementIndexedAnnotationAdapter(type, idaa));
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildNameAdapter();
		this.valueDeclarationAdapter = this.buildValueDeclarationAdapter();
		this.valueAdapter = this.buildValueAdapter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.name = this.buildName(astRoot);
		this.value = this.buildValue(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncName(this.buildName(astRoot));
		this.syncValue(this.buildValue(astRoot));
	}


	// ********** QueryHintAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.attributeValueHasChanged(this.name, name)) {
			this.name = name;
			this.nameAdapter.setValue(name);
		}
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	private DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.QUERY_HINT__NAME);
	}

	private AnnotationElementAdapter<String> buildNameAdapter() {
		return this.buildStringElementAdapter(this.nameDeclarationAdapter);
	}

	// ***** value
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		if (this.attributeValueHasChanged(this.value, value)) {
			this.value = value;
			this.valueAdapter.setValue(value);
		}
	}

	private void syncValue(String astValue) {
		String old = this.value;
		this.value = astValue;
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}

	private String buildValue(CompilationUnit astRoot) {
		return this.valueAdapter.getValue(astRoot);
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.valueDeclarationAdapter, astRoot);
	}

	private DeclarationAnnotationElementAdapter<String> buildValueDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.QUERY_HINT__VALUE);
	}

	private AnnotationElementAdapter<String> buildValueAdapter() {
		return this.buildStringElementAdapter(this.valueDeclarationAdapter);
	}


	// ********** NestableAnnotation implementation **********

	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.name == null) &&
				(this.value == null);
	}

	@Override
	protected void rebuildAdapters() {
		super.rebuildAdapters();
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildNameAdapter();
		this.valueDeclarationAdapter = this.buildValueDeclarationAdapter();
		this.valueAdapter = this.buildValueAdapter();
	}

	@Override
	public void storeOn(Map<String, Object> map) {
		super.storeOn(map);
		map.put(NAME_PROPERTY, this.name);
		this.name = null;
		map.put(VALUE_PROPERTY, this.value);
		this.value = null;
	}

	@Override
	public void restoreFrom(Map<String, Object> map) {
		super.restoreFrom(map);
		this.setName((String) map.get(NAME_PROPERTY));
		this.setValue((String) map.get(VALUE_PROPERTY));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** static methods **********

	static SourceQueryHintAnnotation createNamedQueryQueryHint(JavaResourceNode parent, Type type,  DeclarationAnnotationAdapter namedQueryAdapter, int index) {
		return new SourceQueryHintAnnotation(parent, type, buildNamedQueryQueryHintAnnotationAdapter(namedQueryAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildNamedQueryQueryHintAnnotationAdapter(DeclarationAnnotationAdapter namedQueryAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedQueryAdapter, JPA.NAMED_QUERY__HINTS, index, ANNOTATION_NAME);
	}

	static SourceQueryHintAnnotation createNamedNativeQueryQueryHint(JavaResourceNode parent, Type type, DeclarationAnnotationAdapter namedNativeQueryAdapter, int index) {
		return new SourceQueryHintAnnotation(parent, type, buildNamedNativeQueryQueryHintAnnotationAdapter(namedNativeQueryAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildNamedNativeQueryQueryHintAnnotationAdapter(DeclarationAnnotationAdapter namedNativeQueryAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedNativeQueryAdapter, JPA.NAMED_NATIVE_QUERY__HINTS, index, ANNOTATION_NAME);
	}
}
