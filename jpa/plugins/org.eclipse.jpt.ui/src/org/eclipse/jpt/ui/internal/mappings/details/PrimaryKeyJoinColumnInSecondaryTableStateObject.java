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

import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.db.Table;

/**
 * The state object used to create or edit a primary key join column on an
 * secondary table.
 *
 * @see PrimaryKeyJoinColumn
 * @see SecondaryTable
 * @see PrimaryKeyJoinColumnInSecondaryTableDialog
 *
 * @version 2.0
 * @since 2.0
 */
public class PrimaryKeyJoinColumnInSecondaryTableStateObject extends BaseJoinColumnStateObject
{
	/**
	 * Creates a new <code>PrimaryKeyJoinColumnInSecondaryTableStateObject</code>.
	 *
	 * @param secondaryTable The owner of the join column to create or where it
	 * is located
	 * @param joinColumn The join column to edit or <code>null</code> if it is to
	 * create a new one
	 */
	public PrimaryKeyJoinColumnInSecondaryTableStateObject(SecondaryTable secondaryTable,
	                                                       PrimaryKeyJoinColumn joinColumn) {

		super(secondaryTable, joinColumn);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public PrimaryKeyJoinColumn getJoinColumn() {
		return (PrimaryKeyJoinColumn) super.getJoinColumn();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getNameTable() {
		return getOwner().dbTable();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public SecondaryTable getOwner() {
		return (SecondaryTable) super.getOwner();
	}

	/* (non-Javadoc)
	 */
	@Override
	public Table getReferencedNameTable() {
		return getOwner().getParent().primaryDbTable();
	}
}