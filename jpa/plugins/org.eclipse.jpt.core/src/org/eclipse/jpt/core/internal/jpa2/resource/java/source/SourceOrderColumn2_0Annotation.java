/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceNamedColumnAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OrderColumn2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;

/**
 * javax.persistence.OrderColumn
 */
public class SourceOrderColumn2_0Annotation
	extends SourceNamedColumnAnnotation
	implements OrderColumn2_0Annotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final DeclarationAnnotationElementAdapter<Boolean> nullableDeclarationAdapter;
	private final AnnotationElementAdapter<Boolean> nullableAdapter;
	private Boolean nullable;

	private final DeclarationAnnotationElementAdapter<Boolean> insertableDeclarationAdapter;
	private final AnnotationElementAdapter<Boolean> insertableAdapter;
	private Boolean insertable;

	private final DeclarationAnnotationElementAdapter<Boolean> updatableDeclarationAdapter;
	private final AnnotationElementAdapter<Boolean> updatableAdapter;
	private Boolean updatable;

	public SourceOrderColumn2_0Annotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.nullableDeclarationAdapter = this.buildBooleanElementAdapter(this.getNullableElementName());
		this.nullableAdapter = this.buildShortCircuitBooleanElementAdapter(this.nullableDeclarationAdapter);
		this.insertableDeclarationAdapter = this.buildBooleanElementAdapter(this.getInsertableElementName());
		this.insertableAdapter = this.buildShortCircuitBooleanElementAdapter(this.insertableDeclarationAdapter);
		this.updatableDeclarationAdapter = this.buildBooleanElementAdapter(this.getUpdatableElementName());
		this.updatableAdapter = this.buildShortCircuitBooleanElementAdapter(this.updatableDeclarationAdapter);
	}
	
	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.nullable = this.buildNullable(astRoot);
		this.insertable = this.buildInsertable(astRoot);
		this.updatable = this.buildUpdatable(astRoot);
	}
	
	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setNullable(this.buildNullable(astRoot));
		this.setInsertable(this.buildInsertable(astRoot));
		this.setUpdatable(this.buildUpdatable(astRoot));
	}
	
	@Override
	protected DeclarationAnnotationElementAdapter<String> buildStringElementAdapter(String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, elementName, false);
	}
	
	@Override
	protected DeclarationAnnotationElementAdapter<Boolean> buildBooleanElementAdapter(String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(this.daa, elementName, false, BooleanExpressionConverter.instance());
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
		if (this.attributeValueHasNotChanged(this.nullable, nullable)) {
			return;
		}
		Boolean old = this.nullable;
		this.nullable = nullable;
		this.nullableAdapter.setValue(nullable);
		this.firePropertyChanged(NULLABLE_PROPERTY, old, nullable);
	}

	private Boolean buildNullable(CompilationUnit astRoot) {
		return this.nullableAdapter.getValue(astRoot);
	}
	
	public TextRange getNullableTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nullableDeclarationAdapter, astRoot);
	}
	
	String getNullableElementName() {
		return JPA2_0.ORDER_COLUMN__NULLABLE;
	}

	// ***** insertable
	public Boolean getInsertable() {
		return this.insertable;
	}

	public void setInsertable(Boolean insertable) {
		if (this.attributeValueHasNotChanged(this.insertable, insertable)) {
			return;
		}
		Boolean old = this.insertable;
		this.insertable = insertable;
		this.insertableAdapter.setValue(insertable);
		this.firePropertyChanged(INSERTABLE_PROPERTY, old, insertable);
	}

	private Boolean buildInsertable(CompilationUnit astRoot) {
		return this.insertableAdapter.getValue(astRoot);
	}
	
	public TextRange getInsertableTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.insertableDeclarationAdapter, astRoot);
	}
	
	String getInsertableElementName() {
		return JPA2_0.ORDER_COLUMN__INSERTABLE;
	}

	// ***** updatable
	public Boolean getUpdatable() {
		return this.updatable;
	}

	public void setUpdatable(Boolean updatable) {
		if (this.attributeValueHasNotChanged(this.updatable, updatable)) {
			return;
		}
		Boolean old = this.updatable;
		this.updatable = updatable;
		this.updatableAdapter.setValue(updatable);
		this.firePropertyChanged(UPDATABLE_PROPERTY, old, updatable);
	}

	private Boolean buildUpdatable(CompilationUnit astRoot) {
		return this.updatableAdapter.getValue(astRoot);
	}

	public TextRange getUpdatableTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.updatableDeclarationAdapter, astRoot);
	}
	
	String getUpdatableElementName() {
		return JPA2_0.ORDER_COLUMN__UPDATABLE;
	}
}
