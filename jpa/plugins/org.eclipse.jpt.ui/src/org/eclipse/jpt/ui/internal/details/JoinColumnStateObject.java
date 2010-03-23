/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

/**
 * The state object used to edit a <code>JoinColumn</code>.
 *
 * @see JoinColumn
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class JoinColumnStateObject extends BaseJoinColumnStateObject
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

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.isDefaultInsertable();
		}

		return BaseColumn.DEFAULT_INSERTABLE;
	}

	public boolean isDefaultNullable() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.isDefaultNullable();
		}

		return BaseColumn.DEFAULT_NULLABLE;
	}

	public boolean isDefaultUnique() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.isDefaultUnique();
		}

		return BaseColumn.DEFAULT_UNIQUE;
	}

	public boolean isDefaultUpdatable() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.isDefaultUpdatable();
		}

		return BaseColumn.DEFAULT_UPDATABLE;
	}

	public Boolean getInsertable() {
		return this.insertable;
	}

	@Override
	public JoinColumn getJoinColumn() {
		return (JoinColumn) super.getJoinColumn();
	}

	public Boolean getNullable() {
		return this.nullable;
	}

	protected abstract Schema getDbSchema();

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
		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn == null) {
			return null;
		}

		return joinColumn.getSpecifiedTable();
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
		Schema schema = getDbSchema();
		if (schema == null) {
			return EmptyListIterator.instance();
		}
		return CollectionTools.list(schema.getSortedTableIdentifiers()).listIterator();
	}

	@Override
	public void updateJoinColumn(BaseJoinColumn abstractJoinColumn) {

		super.updateJoinColumn(abstractJoinColumn);

		JoinColumn joinColumn = (JoinColumn) abstractJoinColumn;

		// Table
		if (isTableEditable()) {
			String table = getTable();

			if (valuesAreDifferent(table, joinColumn.getSpecifiedTable())) {
				joinColumn.setSpecifiedTable(table);
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