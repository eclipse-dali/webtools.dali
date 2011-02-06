/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import java.util.Map;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.jpa.core.resource.java.BaseColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;

/**
 * <ul>
 * <li><code>javax.persistence.Column</code>
 * <li><code>javax.persistence.JoinColumn</code>
 * <li><code>javax.persistence.MapKeyColumn</code>
 * <li><code>javax.persistence.MapKeyJoinColumn</code>
 * </ul>
 */
public abstract class SourceBaseColumnAnnotation
	extends SourceNamedColumnAnnotation
	implements BaseColumnAnnotation
{
	protected DeclarationAnnotationElementAdapter<String> tableDeclarationAdapter;
	protected AnnotationElementAdapter<String> tableAdapter;
	protected String table;

	protected DeclarationAnnotationElementAdapter<Boolean> uniqueDeclarationAdapter;
	protected AnnotationElementAdapter<Boolean> uniqueAdapter;
	protected Boolean unique;

	protected DeclarationAnnotationElementAdapter<Boolean> nullableDeclarationAdapter;
	protected AnnotationElementAdapter<Boolean> nullableAdapter;
	protected Boolean nullable;

	protected DeclarationAnnotationElementAdapter<Boolean> insertableDeclarationAdapter;
	protected AnnotationElementAdapter<Boolean> insertableAdapter;
	protected Boolean insertable;

	protected DeclarationAnnotationElementAdapter<Boolean> updatableDeclarationAdapter;
	protected AnnotationElementAdapter<Boolean> updatableAdapter;
	protected Boolean updatable;


	protected SourceBaseColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new ElementAnnotationAdapter(member, daa));
	}
	
	protected SourceBaseColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.tableDeclarationAdapter = this.buildTableDeclarationAdapter();
		this.tableAdapter = this.buildTableAdapter();
		this.uniqueDeclarationAdapter = this.buildUniqueDeclarationAdapter();
		this.uniqueAdapter = this.buildUniqueAdapter();
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

	private DeclarationAnnotationElementAdapter<String> buildTableDeclarationAdapter() {
		return this.buildStringElementAdapter(this.getTableElementName());
	}

	private AnnotationElementAdapter<String> buildTableAdapter() {
		return this.buildStringElementAdapter(this.tableDeclarationAdapter);
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
	
	private DeclarationAnnotationElementAdapter<Boolean> buildUniqueDeclarationAdapter() {
		return this.buildBooleanElementAdapter(this.getUniqueElementName());
	}

	private AnnotationElementAdapter<Boolean> buildUniqueAdapter() {
		return this.buildBooleanElementAdapter(this.uniqueDeclarationAdapter);
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
	
	private DeclarationAnnotationElementAdapter<Boolean> buildNullableDeclarationAdapter() {
		return this.buildBooleanElementAdapter(this.getNullableElementName());
	}

	private AnnotationElementAdapter<Boolean> buildNullableAdapter() {
		return this.buildBooleanElementAdapter(this.nullableDeclarationAdapter);
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
	
	private DeclarationAnnotationElementAdapter<Boolean> buildInsertableDeclarationAdapter() {
		return this.buildBooleanElementAdapter(this.getInsertableElementName());
	}

	private AnnotationElementAdapter<Boolean> buildInsertableAdapter() {
		return this.buildBooleanElementAdapter(this.insertableDeclarationAdapter);
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
	
	private DeclarationAnnotationElementAdapter<Boolean> buildUpdatableDeclarationAdapter() {
		return this.buildBooleanElementAdapter(this.getUpdatableElementName());
	}

	private AnnotationElementAdapter<Boolean> buildUpdatableAdapter() {
		return this.buildBooleanElementAdapter(this.updatableDeclarationAdapter);
	}

	protected abstract String getUpdatableElementName();


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.table == null) &&
				(this.unique == null) &&
				(this.nullable == null) &&
				(this.insertable == null) &&
				(this.updatable == null);
	}

	@Override
	protected void rebuildAdapters() {
		super.rebuildAdapters();
		this.tableDeclarationAdapter = this.buildTableDeclarationAdapter();
		this.tableAdapter = this.buildTableAdapter();
		this.uniqueDeclarationAdapter = this.buildUniqueDeclarationAdapter();
		this.uniqueAdapter = this.buildUniqueAdapter();
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
		map.put(TABLE_PROPERTY, this.table);
		this.table = null;
		map.put(UNIQUE_PROPERTY, this.unique);
		this.unique = null;
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
		this.setTable((String) map.get(TABLE_PROPERTY));
		this.setUnique((Boolean) map.get(UNIQUE_PROPERTY));
		this.setNullable((Boolean) map.get(NULLABLE_PROPERTY));
		this.setInsertable((Boolean) map.get(INSERTABLE_PROPERTY));
		this.setUpdatable((Boolean) map.get(UPDATABLE_PROPERTY));
	}
}
