/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.iterator.SingleElementListIterator;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.db.Table;

/**
 * The state object used to create or edit a primary key join column on an
 * entity.
 *
 * @see SpecifiedPrimaryKeyJoinColumn
 * @see Entity
 * @see PrimaryKeyJoinColumnDialog
 * @see PrimaryKeyJoinColumnDialogPane
 *
 * @version 2.0
 * @since 2.0
 */
public class PrimaryKeyJoinColumnStateObject extends BaseJoinColumnStateObject
{
	/**
	 * Creates a new <code>PrimaryKeyJoinColumnStateObject</code>.
	 *
	 * @param entity The owner of the join column to create or where it is
	 * located
	 * @param joinColumn The join column to edit or <code>null</code> if this is
	 * used to create a new one
	 */
	public PrimaryKeyJoinColumnStateObject(Entity entity,
	                                       SpecifiedPrimaryKeyJoinColumn joinColumn) {
		super(entity, joinColumn);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public String getDefaultTable() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public SpecifiedPrimaryKeyJoinColumn getJoinColumn() {
		return (SpecifiedPrimaryKeyJoinColumn) super.getJoinColumn();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getNameTable() {
		return getOwner().getPrimaryDbTable();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Entity getOwner() {
		return (Entity) super.getOwner();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getReferencedNameTable() {
		Entity parentEntity = getOwner().getParentEntity();
		return (parentEntity == null) ? getOwner().getPrimaryDbTable() : parentEntity.getPrimaryDbTable();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String getInitialTable() {
		return getOwner().getPrimaryTableName();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public ListIterator<String> tables() {
		return new SingleElementListIterator<String>(getInitialTable());
	}
}