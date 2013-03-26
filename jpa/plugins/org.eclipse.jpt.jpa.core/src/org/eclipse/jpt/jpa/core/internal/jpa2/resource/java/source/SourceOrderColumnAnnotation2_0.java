/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceNamedColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OrderColumnAnnotation2_0;

/**
 * <code>javax.persistence.OrderColumn</code>
 */
public final class SourceOrderColumnAnnotation2_0
	extends SourceNamedColumnAnnotation
	implements OrderColumnAnnotation2_0
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private DeclarationAnnotationElementAdapter<Boolean> nullableDeclarationAdapter;
	private AnnotationElementAdapter<Boolean> nullableAdapter;
	private Boolean nullable;
	private TextRange nullableTextRange;

	private DeclarationAnnotationElementAdapter<Boolean> insertableDeclarationAdapter;
	private AnnotationElementAdapter<Boolean> insertableAdapter;
	private Boolean insertable;
	private TextRange insertableTextRange;

	private DeclarationAnnotationElementAdapter<Boolean> updatableDeclarationAdapter;
	private AnnotationElementAdapter<Boolean> updatableAdapter;
	private Boolean updatable;
	private TextRange updatableTextRange;


	public SourceOrderColumnAnnotation2_0(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.nullableDeclarationAdapter = this.buildNullableDeclarationAdapter();
		this.nullableAdapter = this.buildNullableAdapter();
		this.insertableDeclarationAdapter = this.buildInsertableDeclarationAdapter();
		this.insertableAdapter = this.buildInsertableAdapter();
		this.updatableDeclarationAdapter = this.buildUpdatableDeclarationAdapter();
		this.updatableAdapter = this.buildUpdatableAdapter();
	}
	
	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);

		this.nullable = this.buildNullable(astAnnotation);
		this.nullableTextRange = this.buildNullableTextRange(astAnnotation);

		this.insertable = this.buildInsertable(astAnnotation);
		this.insertableTextRange = this.buildInsertableTextRange(astAnnotation);

		this.updatable = this.buildUpdatable(astAnnotation);
		this.updatableTextRange = this.buildUpdatableTextRange(astAnnotation);
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);

		this.syncNullable(this.buildNullable(astAnnotation));
		this.nullableTextRange = this.buildNullableTextRange(astAnnotation);

		this.syncInsertable(this.buildInsertable(astAnnotation));
		this.insertableTextRange = this.buildInsertableTextRange(astAnnotation);

		this.syncUpdatable(this.buildUpdatable(astAnnotation));
		this.updatableTextRange = this.buildUpdatableTextRange(astAnnotation);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	@Override
	protected String getNameElementName() {
		return JPA2_0.ORDER_COLUMN__NAME;
	}
	
	@Override
	protected String getColumnDefinitionElementName() {
		return JPA2_0.ORDER_COLUMN__COLUMN_DEFINITION;
	}


	//************* OrderColumn2_0Annotation implementation *************

	// ***** nullable
	public Boolean getNullable() {
		return this.nullable;
	}

	public void setNullable(Boolean nullable) {
		if (this.attributeValueHasChanged(this.nullable, nullable)) {
			this.nullable = nullable;
			this.nullableAdapter.setValue(nullable);
		}
	}

	private void syncNullable(Boolean astNullable) {
		Boolean old = this.nullable;
		this.nullable = astNullable;
		this.firePropertyChanged(NULLABLE_PROPERTY, old, astNullable);
	}

	private Boolean buildNullable(Annotation astAnnotation) {
		return this.nullableAdapter.getValue(astAnnotation);
	}
	
	public TextRange getNullableTextRange() {
		return this.nullableTextRange;
	}
	
	private TextRange buildNullableTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.nullableDeclarationAdapter, astAnnotation);
	}
	
	private DeclarationAnnotationElementAdapter<Boolean> buildNullableDeclarationAdapter() {
		return this.buildBooleanElementAdapter(this.getNullableElementName());
	}

	private AnnotationElementAdapter<Boolean> buildNullableAdapter() {
		return this.buildBooleanElementAdapter(this.nullableDeclarationAdapter);
	}

	String getNullableElementName() {
		return JPA2_0.ORDER_COLUMN__NULLABLE;
	}

	// ***** insertable
	public Boolean getInsertable() {
		return this.insertable;
	}

	public void setInsertable(Boolean insertable) {
		if (this.attributeValueHasChanged(this.insertable, insertable)) {
			this.insertable = insertable;
			this.insertableAdapter.setValue(insertable);
		}
	}

	private void syncInsertable(Boolean astInsertable) {
		Boolean old = this.insertable;
		this.insertable = astInsertable;
		this.firePropertyChanged(INSERTABLE_PROPERTY, old, astInsertable);
	}

	private Boolean buildInsertable(Annotation astAnnotation) {
		return this.insertableAdapter.getValue(astAnnotation);
	}
	
	public TextRange getInsertableTextRange() {
		return this.insertableTextRange;
	}
	
	private TextRange buildInsertableTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.insertableDeclarationAdapter, astAnnotation);
	}
	
	private DeclarationAnnotationElementAdapter<Boolean> buildInsertableDeclarationAdapter() {
		return this.buildBooleanElementAdapter(this.getInsertableElementName());
	}

	private AnnotationElementAdapter<Boolean> buildInsertableAdapter() {
		return this.buildBooleanElementAdapter(this.insertableDeclarationAdapter);
	}

	String getInsertableElementName() {
		return JPA2_0.ORDER_COLUMN__INSERTABLE;
	}

	// ***** updatable
	public Boolean getUpdatable() {
		return this.updatable;
	}

	public void setUpdatable(Boolean updatable) {
		if (this.attributeValueHasChanged(this.updatable, updatable)) {
			this.updatable = updatable;
			this.updatableAdapter.setValue(updatable);
		}
	}

	private void syncUpdatable(Boolean astUpdatable) {
		Boolean old = this.updatable;
		this.updatable = astUpdatable;
		this.firePropertyChanged(UPDATABLE_PROPERTY, old, astUpdatable);
	}

	private Boolean buildUpdatable(Annotation astAnnotation) {
		return this.updatableAdapter.getValue(astAnnotation);
	}

	public TextRange getUpdatableTextRange() {
		return this.updatableTextRange;
	}
	
	private TextRange buildUpdatableTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.updatableDeclarationAdapter, astAnnotation);
	}
	
	private DeclarationAnnotationElementAdapter<Boolean> buildUpdatableDeclarationAdapter() {
		return this.buildBooleanElementAdapter(this.getUpdatableElementName());
	}

	private AnnotationElementAdapter<Boolean> buildUpdatableAdapter() {
		return this.buildBooleanElementAdapter(this.updatableDeclarationAdapter);
	}

	String getUpdatableElementName() {
		return JPA2_0.ORDER_COLUMN__UPDATABLE;
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.nullable == null) &&
				(this.insertable == null) &&
				(this.updatable == null);
	}
}
