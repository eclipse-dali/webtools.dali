/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.iterator.EmptyListIterator;
import org.eclipse.jpt.jpa.core.context.SpecifiedBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumn;

/**
 * The state object used to edit a <code>JoinColumn</code>.
 *
 * @see SpecifiedJoinColumn
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class JoinColumnStateObject
	extends BaseJoinColumnStateObject
{
	private Boolean insertable;
	private Boolean nullable;
	private Boolean unique;
	private Boolean updatable;

	public static final String INSERTABLE_PROPERTY = "insertable";
	public static final String NULLABLE_PROPERTY = "nullable";
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

	public boolean isDefaultInsertable() {

		SpecifiedJoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.isDefaultInsertable();
		}

		return ReadOnlyBaseColumn.DEFAULT_INSERTABLE;
	}

	public boolean isDefaultNullable() {

		SpecifiedJoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.isDefaultNullable();
		}

		return ReadOnlyBaseColumn.DEFAULT_NULLABLE;
	}

	public boolean isDefaultUnique() {

		SpecifiedJoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.isDefaultUnique();
		}

		return ReadOnlyBaseColumn.DEFAULT_UNIQUE;
	}

	public boolean isDefaultUpdatable() {

		SpecifiedJoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.isDefaultUpdatable();
		}

		return ReadOnlyBaseColumn.DEFAULT_UPDATABLE;
	}

	public Boolean getInsertable() {
		return this.insertable;
	}

	@Override
	public SpecifiedJoinColumn getJoinColumn() {
		return (SpecifiedJoinColumn) super.getJoinColumn();
	}

	public Boolean getNullable() {
		return this.nullable;
	}

	public Boolean getUnique() {
		return this.unique;
	}

	public Boolean getUpdatable() {
		return this.updatable;
	}

	@Override
	protected void initialize(Object owner, BaseJoinColumn baseJoinColumn) {

		super.initialize(owner, baseJoinColumn);

		if (baseJoinColumn != null) {
			JoinColumn joinColumn = (JoinColumn) baseJoinColumn;

			this.insertable       = joinColumn.getSpecifiedInsertable();
			this.nullable         = joinColumn.getSpecifiedNullable();
			this.unique           = joinColumn.getSpecifiedUnique();
			this.updatable        = joinColumn.getSpecifiedUpdatable();
		}
	}

	@Override
	protected String getInitialTable() {
		SpecifiedJoinColumn joinColumn = getJoinColumn();

		if (joinColumn == null) {
			return null;
		}

		return joinColumn.getSpecifiedTableName();
	}

	protected boolean isTableEditable() {
		return true;
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

	@Override
	public ListIterator<String> tables() {
		return EmptyListIterator.instance();
	}

	@Override
	public void updateJoinColumn(SpecifiedBaseJoinColumn abstractJoinColumn) {

		super.updateJoinColumn(abstractJoinColumn);

		SpecifiedJoinColumn joinColumn = (SpecifiedJoinColumn) abstractJoinColumn;

		// Table
		if (isTableEditable()) {
			String table = getTable();

			if (valuesAreDifferent(table, joinColumn.getSpecifiedTableName())) {
				joinColumn.setSpecifiedTableName(table);
			}
		}

		// Insertable
		if (joinColumn.getSpecifiedInsertable() != this.insertable){
			joinColumn.setSpecifiedInsertable(this.insertable);
		}

		// Updatable
		if (joinColumn.getSpecifiedUpdatable() != this.updatable){
			joinColumn.setSpecifiedUpdatable(this.updatable);
		}

		// Unique
		if (joinColumn.getSpecifiedUnique() != this.unique){
			joinColumn.setSpecifiedUnique(this.unique);
		}

		// Nullable
		if (joinColumn.getSpecifiedNullable() != this.nullable){
			joinColumn.setSpecifiedNullable(this.nullable);
		}
	}
}