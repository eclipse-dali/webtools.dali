/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;

/**
 * <code>javax.persistence.QueryHint</code>
 */
public final class SourceQueryHintAnnotation
	extends SourceAnnotation
	implements QueryHintAnnotation
{
	private DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private AnnotationElementAdapter<String> nameAdapter;
	private String name;
	private TextRange nameTextRange;

	private DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	private AnnotationElementAdapter<String> valueAdapter;
	private String value;
	private TextRange valueTextRange;


	static SourceQueryHintAnnotation buildNamedQueryQueryHint(JavaResourceModel parent, AnnotatedElement element,  DeclarationAnnotationAdapter namedQueryAdapter, int index) {
		return buildNestedSourceQueryHintAnnotation(parent, element, buildNamedQueryQueryHintAnnotationAdapter(namedQueryAdapter, index));
	}

	static SourceQueryHintAnnotation buildNamedNativeQueryQueryHint(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter namedNativeQueryAdapter, int index) {
		return buildNestedSourceQueryHintAnnotation(parent, element, buildNamedNativeQueryQueryHintAnnotationAdapter(namedNativeQueryAdapter, index));
	}
	
	public static SourceQueryHintAnnotation buildNamedStoredProcedureQuery2_1QueryHint(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter namedStoredProcedureQuery2_1Adapter, int index) {
		return buildNestedSourceQueryHintAnnotation(parent, element, buildNamedStoredProcedureQuery2_1QueryHintAnnotationAdapter(namedStoredProcedureQuery2_1Adapter, index));
	}
	
	public static SourceQueryHintAnnotation buildNestedSourceQueryHintAnnotation(
			JavaResourceModel parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		return new SourceQueryHintAnnotation(parent, element, idaa);
	}

	private SourceQueryHintAnnotation(JavaResourceModel parent, AnnotatedElement element, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, element, idaa, new ElementIndexedAnnotationAdapter(element, idaa));
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildNameAdapter();
		this.valueDeclarationAdapter = this.buildValueDeclarationAdapter();
		this.valueAdapter = this.buildValueAdapter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.name = this.buildName(astAnnotation);
		this.nameTextRange = this.buildNameTextRange(astAnnotation);

		this.value = this.buildValue(astAnnotation);
		this.valueTextRange = this.buildValueTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncName(this.buildName(astAnnotation));
		this.nameTextRange = this.buildNameTextRange(astAnnotation);

		this.syncValue(this.buildValue(astAnnotation));
		this.valueTextRange = this.buildValueTextRange(astAnnotation);
	}


	// ********** QueryHintAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (ObjectTools.notEquals(this.name, name)) {
			this.name = name;
			this.nameAdapter.setValue(name);
		}
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(Annotation astAnnotation) {
		return this.nameAdapter.getValue(astAnnotation);
	}

	public TextRange getNameTextRange() {
		return this.nameTextRange;
	}

	private TextRange buildNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astAnnotation);
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
		if (ObjectTools.notEquals(this.value, value)) {
			this.value = value;
			this.valueAdapter.setValue(value);
		}
	}

	private void syncValue(String astValue) {
		String old = this.value;
		this.value = astValue;
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}

	private String buildValue(Annotation astAnnotation) {
		return this.valueAdapter.getValue(astAnnotation);
	}

	public TextRange getValueTextRange() {
		return this.valueTextRange;
	}

	private TextRange buildValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.valueDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildValueDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.QUERY_HINT__VALUE);
	}

	private AnnotationElementAdapter<String> buildValueAdapter() {
		return this.buildStringElementAdapter(this.valueDeclarationAdapter);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.name == null) &&
				(this.value == null);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** static methods **********
	private static IndexedDeclarationAnnotationAdapter buildNamedQueryQueryHintAnnotationAdapter(DeclarationAnnotationAdapter namedQueryAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedQueryAdapter, JPA.NAMED_QUERY__HINTS, index, ANNOTATION_NAME);
	}

	private static IndexedDeclarationAnnotationAdapter buildNamedNativeQueryQueryHintAnnotationAdapter(DeclarationAnnotationAdapter namedNativeQueryAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedNativeQueryAdapter, JPA.NAMED_NATIVE_QUERY__HINTS, index, ANNOTATION_NAME);
	}
	private static IndexedDeclarationAnnotationAdapter buildNamedStoredProcedureQuery2_1QueryHintAnnotationAdapter(DeclarationAnnotationAdapter namedStoredProcedureQuery2_1Adapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedStoredProcedureQuery2_1Adapter, JPA2_1.NAMED_STORED_PROCEDURE_QUERY__HINTS, index, ANNOTATION_NAME);
	}
}
