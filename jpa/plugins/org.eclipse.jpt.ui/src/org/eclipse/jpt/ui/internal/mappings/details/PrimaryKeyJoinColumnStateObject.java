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
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.db.internal.Table;

/**
 * The state object used to create or edit a primary key join column on an
 * entity.
 *
 * @see IPrimaryKeyJoinColumn
 * @see IEntity
 * @see PrimaryKeyJoinColumnDialog
 * @see PrimaryKeyJoinColumnDialogPane
 *
 * @version 2.0
 * @since 2.0
 */
public class PrimaryKeyJoinColumnStateObject extends AbstractJoinColumnStateObject
{
	/**
	 * Creates a new <code>PrimaryKeyJoinColumnStateObject</code>.
	 *
	 * @param entity The owner of the join column to create or where it is
	 * located
	 * @param joinColumn The join column to edit or <code>null</code> if this is
	 * used to create a new one
	 */
	public PrimaryKeyJoinColumnStateObject(IEntity entity,
	                                       IPrimaryKeyJoinColumn joinColumn) {
		super(entity, joinColumn);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public IPrimaryKeyJoinColumn getJoinColumn() {
		return (IPrimaryKeyJoinColumn) super.getJoinColumn();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getNameTable() {
		return getOwner().primaryDbTable();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public IEntity getOwner() {
		return (IEntity) super.getOwner();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getReferencedNameTable() {
		return getOwner().parentEntity().primaryDbTable();
	}
}