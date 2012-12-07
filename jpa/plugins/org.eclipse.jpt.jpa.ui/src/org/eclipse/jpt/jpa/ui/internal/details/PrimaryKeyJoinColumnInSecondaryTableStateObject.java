/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.iterator.SingleElementListIterator;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlySecondaryTable;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.db.Table;

/**
 * The state object used to create or edit a primary key join column on an
 * secondary table.
 *
 * @see PrimaryKeyJoinColumn
 * @see SecondaryTable
 * @see PrimaryKeyJoinColumnInSecondaryTableDialog
 */
public class PrimaryKeyJoinColumnInSecondaryTableStateObject
	extends BaseJoinColumnStateObject
{
	public PrimaryKeyJoinColumnInSecondaryTableStateObject(
			ReadOnlySecondaryTable secondaryTable,
			ReadOnlyPrimaryKeyJoinColumn joinColumn) {
		super(secondaryTable, joinColumn);
	}

	@Override
	public String getDefaultTable() {
		return null;
	}

	@Override
	public ReadOnlyPrimaryKeyJoinColumn getJoinColumn() {
		return (ReadOnlyPrimaryKeyJoinColumn) super.getJoinColumn();
	}

	@Override
	public Table getNameTable() {
		return getOwner().getDbTable();
	}

	@Override
	public ReadOnlySecondaryTable getOwner() {
		return (ReadOnlySecondaryTable) super.getOwner();
	}

	@Override
	public Table getReferencedNameTable() {
		return getOwner().getParent().getPrimaryDbTable();
	}

	@Override
	protected String getInitialTable() {
		return getOwner().getName();
	}

	@Override
	public ListIterator<String> tables() {
		return new SingleElementListIterator<String>(getInitialTable());
	}
}
