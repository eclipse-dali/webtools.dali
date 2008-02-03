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

import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;

/**
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class JoinColumnStateObject extends AbstractJoinColumnStateObject
{
	private boolean defaultTableSelected;
	private Boolean insertable;
	private String selectedTable;
	private Boolean updatable;

	public static final String INSERTABLE_PROPERTY = "insertable";
	public static final String UPDATABLE_PROPERTY = "updatable";

	/**
	 * Creates a new <code>JoinColumnStateObject</code>.
	 */
	public JoinColumnStateObject() {
		super();
	}

	/**
	 * Creates a new <code>JoinColumnStateObject</code>.
	 *
	 * @param joinColumn
	 */
	public JoinColumnStateObject(IJoinColumn joinColumn) {
		super(joinColumn);
	}

	public abstract String defaultTableName();

	public Boolean getInsertable() {
		return insertable;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public IJoinColumn getJoinColumn() {
		return (IJoinColumn) super.getJoinColumn();
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

	public String getSelectedTable() {
		return selectedTable;
	}

	public Boolean getUpdatable() {
		return updatable;
	}

	public boolean isDefaultTableSelected() {
		return defaultTableSelected;
	}

	public void setInsertable(Boolean insertable) {
		Boolean oldInsertable = this.insertable;
		this.insertable = insertable;
		firePropertyChanged(INSERTABLE_PROPERTY, oldInsertable, insertable);
	}

	public void setUpdatable(Boolean updatable) {
		Boolean oldUpdatable = this.updatable;
		this.updatable = updatable;
		firePropertyChanged(UPDATABLE_PROPERTY, oldUpdatable, updatable);
	}

	public String specifiedTableName() {

		if (getJoinColumn() != null) {
			return getJoinColumn().getSpecifiedTable();
		}

		return null;
	}

	public String tableName() {
		return (specifiedTableName() == null) ? defaultTableName() : specifiedTableName();
	}
}
