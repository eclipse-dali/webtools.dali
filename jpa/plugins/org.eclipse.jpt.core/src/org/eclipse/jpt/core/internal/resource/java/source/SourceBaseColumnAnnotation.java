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
public abstract class SourceBaseColumnAnnotation
	extends SourceNamedColumnAnnotation
	implements BaseColumnAnnotation
{
	protected final DeclarationAnnotationElementAdapter<String> tableDeclarationAdapter;
	protected final AnnotationElementAdapter<String> tableAdapter;
	protected String table;

	protected final DeclarationAnnotationElementAdapter<Boolean> uniqueDeclarationAdapter;
	protected final AnnotationElementAdapter<Boolean> uniqueAdapter;
	protected Boolean unique;

	protected final DeclarationAnnotationElementAdapter<Boolean> nullableDeclarationAdapter;
	protected final AnnotationElementAdapter<Boolean> nullableAdapter;
	protected Boolean nullable;

	protected final DeclarationAnnotationElementAdapter<Boolean> insertableDeclarationAdapter;
	protected final AnnotationElementAdapter<Boolean> insertableAdapter;
	protected Boolean insertable;

	protected final DeclarationAnnotationElementAdapter<Boolean> updatableDeclarationAdapter;
	protected final AnnotationElementAdapter<Boolean> updatableAdapter;
	protected Boolean updatable;


	protected SourceBaseColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new MemberAnnotationAdapter(member, daa));
	}
	
	protected SourceBaseColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
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
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncTable(this.buildTable(astRoot));
		this.syncUnique(this.buildUnique(astRoot));
		this.syncNullable(this.buildNullable(astRoot));
		this.syncInsertable(this.buildInsertable(astRoot));
		this.syncUpdatable(this.buildUpdatable(astRoot));
	}
	

	//************* BaseColumnAnnotation implementation *************

	// ***** table
	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		if (this.attributeValueHasChanged(this.table, table)) {
			this.table = table;
			this.tableAdapter.setValue(table);
		}
	}
	
	private void syncTable(String astTable) {
		String old = this.table;
		this.table = astTable;
		this.firePropertyChanged(TABLE_PROPERTY, old, astTable);
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

	protected abstract String getTableElementName();

	// ***** unique
	public Boolean getUnique() {
		return this.unique;
	}

	public void setUnique(Boolean unique) {
		if (this.attributeValueHasChanged(this.unique, unique)) {
			this.unique = unique;
			this.uniqueAdapter.setValue(unique);
		}
	}

	private void syncUnique(Boolean astUnique) {
		Boolean old = this.unique;
		this.unique = astUnique;
		this.firePropertyChanged(UNIQUE_PROPERTY, old, astUnique);
	}

	private Boolean buildUnique(CompilationUnit astRoot) {
		return this.uniqueAdapter.getValue(astRoot);
	}
	
	public TextRange getUniqueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.uniqueDeclarationAdapter, astRoot);
	}
	
	protected abstract String getUniqueElementName();

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
	
	protected abstract String getNullableElementName();

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
	
	protected abstract String getInsertableElementName();

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
	
	protected abstract String getUpdatableElementName();


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
