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

import java.util.ListIterator;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

/**
 * The state object used to create or edit a primary key join column on a join
 * table.
 *
 * @see JoinColumn
 * @see JoinTable
 * @see InverseJoinColumnInJoinTableDialog
 *
 * @version 2.0
 * @since 2.0
 */
public class InverseJoinColumnInJoinTableStateObject extends JoinColumnStateObject
{
	/**
	 * Creates a new <code>JoinColumnInJoinTableStateObject</code>.
	 *
	 * @param joinTable
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public InverseJoinColumnInJoinTableStateObject(JoinTable joinTable,
	                                               JoinColumn joinColumn) {

		super(joinTable, joinColumn);
	}

	@Override
	protected Schema getSchema() {
		return null;
	}

	@Override
	public String getDefaultTable() {
		return null;
	}


	@Override
	public Table getNameTable() {
		return getOwner().getDbTable();
	}


	@Override
	public JoinTable getOwner() {
		return (JoinTable) super.getOwner();
	}


	@Override
	public Table getReferencedNameTable() {
		Entity targetEntity = relationshipMapping().getResolvedTargetEntity();

		if (targetEntity == null) {
			return null;
		}

		return targetEntity.getPrimaryDbTable();
	}

	@Override
	protected String initialTable() {
		return getOwner().getName();
	}

	/**
	 * Returns the mapping where the join column is located.
	 *
	 * @return The owner of the join column to create or to edit
	 */
	public RelationshipMapping relationshipMapping() {
		return getOwner().getParent();
	}

	@Override
	public ListIterator<String> tables() {
		return new SingleElementListIterator<String>(initialTable());
	}
}