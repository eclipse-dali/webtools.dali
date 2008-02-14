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

import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinTable;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.db.internal.Table;

/**
 * The state object used to create or edit a primary key join column on a join
 * table.
 *
 * @see IJoinColumn
 * @see IJoinTable
 * @see InverseJoinColumnInJoinTableDialog
 *
 * @version 2.0
 * @since 2.0
 */
public class InverseJoinColumnInJoinTableStateObject extends AbstractJoinColumnStateObject
{
	/**
	 * Creates a new <code>JoinColumnInJoinTableStateObject</code>.
	 *
	 * @param joinTable
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public InverseJoinColumnInJoinTableStateObject(IJoinTable joinTable,
	                                               IJoinColumn joinColumn) {

		super(joinTable, joinColumn);
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
		return getOwner().dbTable();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public IJoinTable getOwner() {
		return (IJoinTable) super.getOwner();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getReferencedNameTable() {
		IEntity targetEntity = relationshipMapping().getResolvedTargetEntity();

		if (targetEntity == null) {
			return null;
		}

		return targetEntity.primaryDbTable();
	}

	/**
	 * Returns the mapping where the join column is located.
	 *
	 * @return The owner of the join column to create or to edit
	 */
	public IRelationshipMapping relationshipMapping() {
		return getOwner().parent();
	}
}