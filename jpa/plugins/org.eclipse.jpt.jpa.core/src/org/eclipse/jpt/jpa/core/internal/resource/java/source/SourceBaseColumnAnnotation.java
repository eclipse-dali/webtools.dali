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
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
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


	protected SourceBaseColumnAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}
	
	protected SourceBaseColumnAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
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
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);

		this.table = this.buildTable(astAnnotation);
		this.tableTextRange = this.buildTableTextRange(astAnnotation);

		this.unique = this.buildUnique(astAnnotation);
		this.uniqueTextRange = this.buildUniqueTextRange(astAnnotation);

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

		this.syncTable(this.buildTable(astAnnotation));
		this.tableTextRange = this.buildTableTextRange(astAnnotation);

		this.syncUnique(this.buildUnique(astAnnotation));
		this.uniqueTextRange = this.buildUniqueTextRange(astAnnotation);

		this.syncNullable(this.buildNullable(astAnnotation));
		this.nullableTextRange = this.buildNullableTextRange(astAnnotation);

		this.syncInsertable(this.buildInsertable(astAnnotation));
		this.insertableTextRange = this.buildInsertableTextRange(astAnnotation);

		this.syncUpdatable(this.buildUpdatable(astAnnotation));
		this.updatableTextRange = this.buildUpdatableTextRange(astAnnotation);
	}


	//************* BaseColumnAnnotation implementation *************

	// ***** table
	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		if (ObjectTools.notEquals(this.table, table)) {
			this.table = table;
			this.tableAdapter.setValue(table);
		}
	}
	
	private void syncTable(String astTable) {
		String old = this.table;
		this.table = astTable;
		this.firePropertyChanged(TABLE_PROPERTY, old, astTable);
	}
	
	private String buildTable(Annotation astAnnotation) {
		return this.tableAdapter.getValue(astAnnotation);
	}
	
	public TextRange getTableTextRange() {
		return this.tableTextRange;
	}
	
	private TextRange buildTableTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.tableDeclarationAdapter, astAnnotation);
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
		if (ObjectTools.notEquals(this.unique, unique)) {
			this.unique = unique;
			this.uniqueAdapter.setValue(unique);
		}
	}

	private void syncUnique(Boolean astUnique) {
		Boolean old = this.unique;
		this.unique = astUnique;
		this.firePropertyChanged(UNIQUE_PROPERTY, old, astUnique);
	}

	private Boolean buildUnique(Annotation astAnnotation) {
		return this.uniqueAdapter.getValue(astAnnotation);
	}
	
	public TextRange getUniqueTextRange() {
		return this.uniqueTextRange;
	}
	
	private TextRange buildUniqueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.uniqueDeclarationAdapter, astAnnotation);
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
		if (ObjectTools.notEquals(this.nullable, nullable)) {
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

	protected abstract String getNullableElementName();

	// ***** insertable
	public Boolean getInsertable() {
		return this.insertable;
	}

	public void setInsertable(Boolean insertable) {
		if (ObjectTools.notEquals(this.insertable, insertable)) {
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

	protected abstract String getInsertableElementName();

	// ***** updatable
	public Boolean getUpdatable() {
		return this.updatable;
	}

	public void setUpdatable(Boolean updatable) {
		if (ObjectTools.notEquals(this.updatable, updatable)) {
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
