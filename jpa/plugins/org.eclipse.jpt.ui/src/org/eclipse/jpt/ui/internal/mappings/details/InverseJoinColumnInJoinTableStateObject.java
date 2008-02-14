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
 * @version 2.0
 * @since 2.0
 */
public class InverseJoinColumnInJoinTableStateObject extends AbstractJoinColumnStateObject
{
	private IJoinTable joinTable;

	/**
	 * Creates a new <code>JoinColumnInJoinTableStateObject</code>.
	 *
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public InverseJoinColumnInJoinTableStateObject(IJoinTable joinTable, IJoinColumn joinColumn) {
		super(joinColumn);
		this.joinTable = joinTable;
	}

	/**
	 * Creates a new <code>JoinColumnInJoinTableStateObject</code>.
	 *
	 * @param joinTable
	 */
	public InverseJoinColumnInJoinTableStateObject(IJoinTable joinTable) {
		this(joinTable, null);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public IJoinColumn getJoinColumn() {
		return (IJoinColumn) super.getJoinColumn();
	}

	public final IJoinTable getJoinTable() {
		return this.joinTable;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getNameTable() {
		return getJoinTable().dbTable();
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

	public IRelationshipMapping relationshipMapping() {
		return getJoinTable().parent();
	}
}
