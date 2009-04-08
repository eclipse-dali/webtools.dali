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
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.BaseColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * javax.persistence.Column
 * javax.persistence.JoinColumn
 */
abstract class SourceBaseColumnAnnotation
	extends SourceNamedColumnAnnotation
	implements BaseColumnAnnotation
{
	final DeclarationAnnotationElementAdapter<String> tableDeclarationAdapter;
	final AnnotationElementAdapter<String> tableAdapter;
	String table;

	final DeclarationAnnotationElementAdapter<Boolean> uniqueDeclarationAdapter;
	final AnnotationElementAdapter<Boolean> uniqueAdapter;
	Boolean unique;

	final DeclarationAnnotationElementAdapter<Boolean> nullableDeclarationAdapter;
	final AnnotationElementAdapter<Boolean> nullableAdapter;
	Boolean nullable;

	final DeclarationAnnotationElementAdapter<Boolean> insertableDeclarationAdapter;
	final AnnotationElementAdapter<Boolean> insertableAdapter;
	Boolean insertable;

	final DeclarationAnnotationElementAdapter<Boolean> updatableDeclarationAdapter;
	final AnnotationElementAdapter<Boolean> updatableAdapter;
	Boolean updatable;


	SourceBaseColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new MemberAnnotationAdapter(member, daa));
	}
	
	SourceBaseColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.tableDeclarationAdapter = this.buildStringElementAdapter(this.getTableElementName());
		this.tableAdapter = this.buildShortCircuitElementAdapter(this.tableDeclarationAdapter);
		this.uniqueDeclarationAdapter = this.buildBooleanElementAdapter(this.getUniqueElementName());
		this.uniqueAdapter = this.buildShortCircuitBooleanElementAdapter(this.uniqueDeclarationAdapter);
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
		this.table = this.buildTable(astRoot);
		this.unique = this.buildUnique(astRoot);
		this.nullable = this.buildNullable(astRoot);
		this.insertable = this.buildInsertable(astRoot);
		this.updatable = this.buildUpdatable(astRoot);
	}
	
	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setTable(this.buildTable(astRoot));
		this.setUnique(this.buildUnique(astRoot));
		this.setNullable(this.buildNullable(astRoot));
		this.setInsertable(this.buildInsertable(astRoot));
		this.setUpdatable(this.buildUpdatable(astRoot));
	}
	

	//************* BaseColumnAnnotation implementation *************

	// ***** table
	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		if (this.attributeValueHasNotChanged(this.table, table)) {
			return;
		}
		String old = this.table;
		this.table = table;
		this.tableAdapter.setValue(table);
		this.firePropertyChanged(TABLE_PROPERTY, old, table);
	}
	
	private String buildTable(CompilationUnit astRoot) {
		return this.tableAdapter.getValue(astRoot);
	}
	
	public TextRange getTableTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.tableDeclarationAdapter, astRoot);
	}
	
	public boolean tableTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.tableDeclarationAdapter, pos, astRoot);
	}

	abstract String getTableElementName();

	// ***** unique
	public Boolean getUnique() {
		return this.unique;
	}

	public void setUnique(Boolean unique) {
		if (this.attributeValueHasNotChanged(this.unique, unique)) {
			return;
		}
		Boolean old = this.unique;
		this.unique = unique;
		this.uniqueAdapter.setValue(unique);
		this.firePropertyChanged(UNIQUE_PROPERTY, old, unique);
	}

	private Boolean buildUnique(CompilationUnit astRoot) {
		return this.uniqueAdapter.getValue(astRoot);
	}
	
	public TextRange getUniqueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.uniqueDeclarationAdapter, astRoot);
	}
	
	abstract String getUniqueElementName();

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
	
	abstract String getNullableElementName();

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
	
	abstract String getInsertableElementName();

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
	
	abstract String getUpdatableElementName();


	//************* NestableAnnotation implementation *************

	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		BaseColumnAnnotation oldColumn = (BaseColumnAnnotation) oldAnnotation;
		this.setTable(oldColumn.getTable());
		this.setUnique(oldColumn.getUnique());
		this.setNullable(oldColumn.getNullable());
		this.setInsertable(oldColumn.getInsertable());
		this.setUpdatable(oldColumn.getUpdatable());
	}

}
