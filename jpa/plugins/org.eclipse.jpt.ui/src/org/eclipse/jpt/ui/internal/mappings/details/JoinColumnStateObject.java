/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;

/**
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class JoinColumnStateObject extends BaseJoinColumnStateObject
{
	private String columnDefinition;
	private Boolean insertable;
	private Boolean nullable;
	private String table;
	private Boolean unique;
	private Boolean updatable;

	public static final String COLUMN_DEFINITION_PROPERTY = "columnDefinition";
	public static final String INSERTABLE_PROPERTY = "insertable";
	public static final String NULLABLE_PROPERTY = "nullable";
	public static final String TABLE_PROPERTY = "table";
	public static final String UNIQUE_PROPERTY = "unique";
	public static final String UPDATABLE_PROPERTY = "updatable";

	/**
	 * Creates a new <code>JoinColumnStateObject</code>.
	 *
	 * @param owner The owner of the join column to create or where it is located
	 * @param joinColumn The join column to edit
	 */
	public JoinColumnStateObject(Object owner, JoinColumn joinColumn) {
		super(owner, joinColumn);
	}
	public abstract String defaultTableName();

	public String getColumnDefinition() {
		return columnDefinition;
	}

	public Boolean getDefaultInsertable() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getDefaultInsertable();
		}

		return JoinColumn.DEFAULT_INSERTABLE;
	}

	public Boolean getDefaultNullable() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getDefaultNullable();
		}

		return JoinColumn.DEFAULT_NULLABLE;
	}

	public Boolean getDefaultUnique() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getDefaultUnique();
		}

		return JoinColumn.DEFAULT_UNIQUE;
	}

	public Boolean getDefaultUpdatable() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getDefaultUpdatable();
		}

		return JoinColumn.DEFAULT_UPDATABLE;
	}

	public Boolean getInsertable() {
		return insertable;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public JoinColumn getJoinColumn() {
		return (JoinColumn) super.getJoinColumn();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getNameTable() {
		return null;
	}

	public Boolean getNullable() {
		return nullable;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getReferencedNameTable() {
		return null;
	}

	public abstract Schema getSchema();

	public String getTable() {
		return table;
	}

	public Boolean getUnique() {
		return unique;
	}

	public Boolean getUpdatable() {
		return updatable;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize(Object owner,
	                          BaseJoinColumn abstractJoinColumn) {

		super.initialize(owner, abstractJoinColumn);

		if (abstractJoinColumn != null) {
			JoinColumn joinColumn = (JoinColumn) abstractJoinColumn;

			table            = joinColumn.getSpecifiedTable();
			insertable       = joinColumn.getSpecifiedInsertable();
			nullable         = joinColumn.getSpecifiedNullable();
			unique           = joinColumn.getSpecifiedUnique();
			updatable        = joinColumn.getSpecifiedUpdatable();
			columnDefinition = joinColumn.getColumnDefinition();
		}
	}

	public void setColumnDefinition(String columnDefinition) {
		String oldColumnDefinition = this.columnDefinition;
		this.columnDefinition = columnDefinition;
		firePropertyChanged(COLUMN_DEFINITION_PROPERTY, oldColumnDefinition, columnDefinition);
	}

	public void setInsertable(Boolean insertable) {
		Boolean oldInsertable = this.insertable;
		this.insertable = insertable;
		firePropertyChanged(INSERTABLE_PROPERTY, oldInsertable, insertable);
	}

	public void setNullable(Boolean nullable) {
		Boolean oldNullable = this.nullable;
		this.nullable = nullable;
		firePropertyChanged(NULLABLE_PROPERTY, oldNullable, nullable);
	}

	public void setTable(String table) {
		String oldTable = this.table;
		this.table = table;
		firePropertyChanged(TABLE_PROPERTY, oldTable, table);
	}

	public void setUnique(Boolean unique) {
		Boolean oldUnique = this.unique;
		this.unique = unique;
		firePropertyChanged(UNIQUE_PROPERTY, oldUnique, unique);
	}

	public void setUpdatable(Boolean updatable) {
		Boolean oldUpdatable = this.updatable;
		this.updatable = updatable;
		firePropertyChanged(UPDATABLE_PROPERTY, oldUpdatable, updatable);
	}

	public String specifiedTableName() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getSpecifiedTable();
		}

		return null;
	}

	public String tableName() {
		return (specifiedTableName() == null) ? defaultTableName() : specifiedTableName();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void updateJoinColumn(BaseJoinColumn abstractJoinColumn) {

		super.updateJoinColumn(abstractJoinColumn);

		JoinColumn joinColumn = (JoinColumn) abstractJoinColumn;

		// Table
		if (valuesAreDifferent(table, joinColumn.getSpecifiedTable())) {
			joinColumn.setSpecifiedTable(table);
		}

		// Insertable
		if (joinColumn.getSpecifiedInsertable() != insertable){
			joinColumn.setSpecifiedInsertable(insertable);
		}

		// Updatable
		if (joinColumn.getSpecifiedUpdatable() != updatable){
			joinColumn.setSpecifiedUpdatable(updatable);
		}

		// Unique
		if (joinColumn.getSpecifiedUnique() != unique){
			joinColumn.setSpecifiedUnique(unique);
		}

		// Nullable
		if (joinColumn.getSpecifiedNullable() != nullable){
			joinColumn.setSpecifiedNullable(nullable);
		}

		// Column Definition
		if (valuesAreDifferent(columnDefinition, joinColumn.getColumnDefinition())) {
			joinColumn.setColumnDefinition(columnDefinition);
		}
	}
}