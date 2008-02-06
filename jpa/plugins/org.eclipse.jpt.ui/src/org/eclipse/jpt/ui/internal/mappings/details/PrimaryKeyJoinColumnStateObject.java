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
 * @version 2.0
 * @since 2.0
 */
public class PrimaryKeyJoinColumnStateObject extends AbstractJoinColumnStateObject
{
	private IEntity entity;

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnStateObject</code>.
	 *
	 * @param entity
	 */
	public PrimaryKeyJoinColumnStateObject(IEntity entity) {
		super();
		this.entity = entity;
	}

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnStateObject</code>.
	 *
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public PrimaryKeyJoinColumnStateObject(IPrimaryKeyJoinColumn joinColumn) {
		super(joinColumn);
		this.entity = (IEntity) joinColumn.parent();
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
		return entity.primaryDbTable();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getReferencedNameTable() {
		return entity.parentEntity().primaryDbTable();
	}
}
