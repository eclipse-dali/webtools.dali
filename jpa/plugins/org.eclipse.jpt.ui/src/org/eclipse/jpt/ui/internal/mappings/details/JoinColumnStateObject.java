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

import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;

/**
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class JoinColumnStateObject extends AbstractJoinColumnStateObject
{
	private Boolean insertable;
	private String table;
	private Boolean updatable;

	public static final String INSERTABLE_PROPERTY = "insertable";
	public static final String TABLE_PROPERTY = "table";
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

	public Boolean getDefaultInsertable() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getDefaultInsertable();
		}

		return null;
	}

	public Boolean getDefaultUpdatable() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getDefaultUpdatable();
		}

		return null;
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

	public Boolean getUpdatable() {
		return updatable;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize(Object owner,
	                          AbstractJoinColumn abstractJoinColumn) {

		super.initialize(owner, abstractJoinColumn);

		if (abstractJoinColumn != null) {
			JoinColumn joinColumn = (JoinColumn) abstractJoinColumn;

			table      = joinColumn.getSpecifiedTable();
			insertable = joinColumn.getSpecifiedInsertable();
			updatable  = joinColumn.getSpecifiedUpdatable();
		}
	}

	public void setInsertable(Boolean insertable) {
		Boolean oldInsertable = this.insertable;
		this.insertable = insertable;
		firePropertyChanged(INSERTABLE_PROPERTY, oldInsertable, insertable);
	}

	public void setTable(String table) {
		String oldTable = this.table;
		this.table = table;
		firePropertyChanged(TABLE_PROPERTY, oldTable, table);
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
	public void updateJoinColumn(AbstractJoinColumn abstractJoinColumn) {

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
	}
}