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

import java.util.Collections;
import java.util.List;
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

	public Boolean getDefaultInsertable() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getDefaultInsertable();
		}

		return BaseColumn.DEFAULT_INSERTABLE;
	}

	public Boolean getDefaultNullable() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getDefaultNullable();
		}

		return BaseColumn.DEFAULT_NULLABLE;
	}

	public Boolean getDefaultUnique() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getDefaultUnique();
		}

		return BaseColumn.DEFAULT_UNIQUE;
	}

	public Boolean getDefaultUpdatable() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getDefaultUpdatable();
		}

		return BaseColumn.DEFAULT_UPDATABLE;
	}

	public Boolean getInsertable() {
		return insertable;
	}

	@Override
	public JoinColumn getJoinColumn() {
		return (JoinColumn) super.getJoinColumn();
	}

	public Boolean getNullable() {
		return nullable;
	}

	protected abstract Schema getSchema();

	public Boolean getUnique() {
		return unique;
	}

	public Boolean getUpdatable() {
		return updatable;
	}

	@Override
	protected void initialize(Object owner, BaseJoinColumn baseJoinColumn) {

		super.initialize(owner, baseJoinColumn);

		if (baseJoinColumn != null) {
			JoinColumn joinColumn = (JoinColumn) baseJoinColumn;

			insertable       = joinColumn.getSpecifiedInsertable();
			nullable         = joinColumn.getSpecifiedNullable();
			unique           = joinColumn.getSpecifiedUnique();
			updatable        = joinColumn.getSpecifiedUpdatable();
		}
	}

	@Override
	protected String initialTable() {
		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn == null) {
			return null;
		}

		return joinColumn.getSpecifiedTable();
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

	/*
	 * (non-Javadoc)
	 */
	@Override
	public ListIterator<String> tables() {
		Schema schema = getSchema();

		if (schema == null) {
			return EmptyListIterator.instance();
		}

		List<String> names = CollectionTools.list(schema.tableNames());
		Collections.sort(names);
		return names.listIterator();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void updateJoinColumn(BaseJoinColumn abstractJoinColumn) {

		super.updateJoinColumn(abstractJoinColumn);

		JoinColumn joinColumn = (JoinColumn) abstractJoinColumn;

		// Table
		String table = getTable();

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
	}
}