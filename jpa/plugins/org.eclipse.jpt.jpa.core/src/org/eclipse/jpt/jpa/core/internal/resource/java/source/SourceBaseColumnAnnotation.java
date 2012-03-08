/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.core.resource.java.BaseColumnAnnotation;

/**
 * <code><ul>
 * <li>javax.persistence.Column
 * <li>javax.persistence.JoinColumn
 * <li>javax.persistence.MapKeyColumn
 * <li>javax.persistence.MapKeyJoinColumn
 * </ul></code>
 */
public abstract class SourceBaseColumnAnnotation
	extends SourceNamedColumnAnnotation
	implements BaseColumnAnnotation
{
	protected DeclarationAnnotationElementAdapter<String> tableDeclarationAdapter;
	protected AnnotationElementAdapter<String> tableAdapter;
	protected String table;
	protected TextRange tableTextRange;

	protected DeclarationAnnotationElementAdapter<Boolean> uniqueDeclarationAdapter;
	protected AnnotationElementAdapter<Boolean> uniqueAdapter;
	protected Boolean unique;
	protected TextRange uniqueTextRange;

	protected DeclarationAnnotationElementAdapter<Boolean> nullableDeclarationAdapter;
	protected AnnotationElementAdapter<Boolean> nullableAdapter;
	protected Boolean nullable;
	protected TextRange nullableTextRange;

	protected DeclarationAnnotationElementAdapter<Boolean> insertableDeclarationAdapter;
	protected AnnotationElementAdapter<Boolean> insertableAdapter;
	protected Boolean insertable;
	protected TextRange insertableTextRange;

	protected DeclarationAnnotationElementAdapter<Boolean> updatableDeclarationAdapter;
	protected AnnotationElementAdapter<Boolean> updatableAdapter;
	protected Boolean updatable;
	protected TextRange updatableTextRange;


	protected SourceBaseColumnAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}
	
	protected SourceBaseColumnAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
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
		this.tableTextRange = this.buildTableTextRange(astRoot);

		this.unique = this.buildUnique(astRoot);
		this.uniqueTextRange = this.buildUniqueTextRange(astRoot);

		this.nullable = this.buildNullable(astRoot);
		this.nullableTextRange = this.buildNullableTextRange(astRoot);

		this.insertable = this.buildInsertable(astRoot);
		this.insertableTextRange = this.buildInsertableTextRange(astRoot);

		this.updatable = this.buildUpdatable(astRoot);
		this.updatableTextRange = this.buildUpdatableTextRange(astRoot);
	}
	
	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);

		this.syncTable(this.buildTable(astRoot));
		this.tableTextRange = this.buildTableTextRange(astRoot);

		this.syncUnique(this.buildUnique(astRoot));
		this.uniqueTextRange = this.buildUniqueTextRange(astRoot);

		this.syncNullable(this.buildNullable(astRoot));
		this.nullableTextRange = this.buildNullableTextRange(astRoot);

		this.syncInsertable(this.buildInsertable(astRoot));
		this.insertableTextRange = this.buildInsertableTextRange(astRoot);

		this.syncUpdatable(this.buildUpdatable(astRoot));
		this.updatableTextRange = this.buildUpdatableTextRange(astRoot);
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
	
	public TextRange getTableTextRange() {
		return this.tableTextRange;
	}
	
	private TextRange buildTableTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.tableDeclarationAdapter, astRoot);
	}
	
	public boolean tableTouches(int pos) {
		return this.textRangeTouches(this.tableTextRange, pos);
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
	
	public TextRange getUniqueTextRange() {
		return this.uniqueTextRange;
	}
	
	private TextRange buildUniqueTextRange(CompilationUnit astRoot) {
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
	
	public TextRange getNullableTextRange() {
		return this.nullableTextRange;
	}
	
	private TextRange buildNullableTextRange(CompilationUnit astRoot) {
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
	
	public TextRange getInsertableTextRange() {
		return this.insertableTextRange;
	}
	
	private TextRange buildInsertableTextRange(CompilationUnit astRoot) {
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

	public TextRange getUpdatableTextRange() {
		return this.updatableTextRange;
	}
	
	private TextRange buildUpdatableTextRange(CompilationUnit astRoot) {
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
}
