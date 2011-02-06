/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source;

import java.util.Map;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Attribute;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceNamedColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OrderColumn2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;

/**
 * <code>javax.persistence.OrderColumn</code>
 */
public final class SourceOrderColumn2_0Annotation
	extends SourceNamedColumnAnnotation
	implements OrderColumn2_0Annotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private DeclarationAnnotationElementAdapter<Boolean> nullableDeclarationAdapter;
	private AnnotationElementAdapter<Boolean> nullableAdapter;
	private Boolean nullable;

	private DeclarationAnnotationElementAdapter<Boolean> insertableDeclarationAdapter;
	private AnnotationElementAdapter<Boolean> insertableAdapter;
	private Boolean insertable;

	private DeclarationAnnotationElementAdapter<Boolean> updatableDeclarationAdapter;
	private AnnotationElementAdapter<Boolean> updatableAdapter;
	private Boolean updatable;


	public SourceOrderColumn2_0Annotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.nullableDeclarationAdapter = this.buildNullableDeclarationAdapter();
		this.nullableAdapter = this.buildNullableAdapter();
		this.insertableDeclarationAdapter = this.buildInsertableDeclarationAdapter();
		this.insertableAdapter = this.buildInsertableAdapter();
		this.updatableDeclarationAdapter = this.buildUpdatableDeclarationAdapter();
		this.updatableAdapter = this.buildUpdatableAdapter();
	}
	
	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.nullable = this.buildNullable(astRoot);
		this.insertable = this.buildInsertable(astRoot);
		this.updatable = this.buildUpdatable(astRoot);
	}
	
	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncNullable(this.buildNullable(astRoot));
		this.syncInsertable(this.buildInsertable(astRoot));
		this.syncUpdatable(this.buildUpdatable(astRoot));
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

	private Boolean buildNullable(CompilationUnit astRoot) {
		return this.nullableAdapter.getValue(astRoot);
	}
	
	public TextRange getNullableTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nullableDeclarationAdapter, astRoot);
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

	private Boolean buildInsertable(CompilationUnit astRoot) {
		return this.insertableAdapter.getValue(astRoot);
	}
	
	public TextRange getInsertableTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.insertableDeclarationAdapter, astRoot);
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

	private Boolean buildUpdatable(CompilationUnit astRoot) {
		return this.updatableAdapter.getValue(astRoot);
	}

	public TextRange getUpdatableTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.updatableDeclarationAdapter, astRoot);
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

	@Override
	protected void rebuildAdapters() {
		super.rebuildAdapters();
		this.nullableDeclarationAdapter = this.buildNullableDeclarationAdapter();
		this.nullableAdapter = this.buildNullableAdapter();
		this.insertableDeclarationAdapter = this.buildInsertableDeclarationAdapter();
		this.insertableAdapter = this.buildInsertableAdapter();
		this.updatableDeclarationAdapter = this.buildUpdatableDeclarationAdapter();
		this.updatableAdapter = this.buildUpdatableAdapter();
	}

	@Override
	public void storeOn(Map<String, Object> map) {
		super.storeOn(map);
		map.put(NULLABLE_PROPERTY, this.nullable);
		this.nullable = null;
		map.put(INSERTABLE_PROPERTY, this.insertable);
		this.insertable = null;
		map.put(UPDATABLE_PROPERTY, this.updatable);
		this.updatable = null;
	}

	@Override
	public void restoreFrom(Map<String, Object> map) {
		super.restoreFrom(map);
		this.setNullable((Boolean) map.get(NULLABLE_PROPERTY));
		this.setInsertable((Boolean) map.get(INSERTABLE_PROPERTY));
		this.setUpdatable((Boolean) map.get(UPDATABLE_PROPERTY));
	}
}
