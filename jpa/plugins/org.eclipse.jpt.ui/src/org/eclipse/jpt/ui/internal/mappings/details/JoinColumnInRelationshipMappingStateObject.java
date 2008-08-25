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

import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;

/**
 * The state object used to create or edit a primary key join column on a
 * relationship mapping.
 *
 * @see JoinColumn
 * @see RelationshipMapping
 * @see JoinColumnInRelationshipMappingDialog
 *
 * @version 2.0
 * @since 2.0
 */
public class JoinColumnInRelationshipMappingStateObject extends JoinColumnStateObject
{
	/**
	 * Creates a new <code>JoinColumnInRelationshipMappingStateObject</code>.
	 *
	 * @param relationshipMapping The owner of the join column to create
	 * @param joinColumn The join column to edit or <code>null</code> if this is
	 * used to create a new one
	 */
	public JoinColumnInRelationshipMappingStateObject(RelationshipMapping relationshipMapping,
	                                                  JoinColumn joinColumn) {
		super(relationshipMapping, joinColumn);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public String getDefaultTable() {

		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getDefaultTable();
		}

		return getOwner().getTypeMapping().getTableName();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getNameTable() {
		Schema schema = getDbSchema();

		if (schema == null) {
			return null;
		}

		String table = getTable();

		if (table == null) {
			table = getDefaultTable();
		}

		return schema.getTableNamed(table);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public RelationshipMapping getOwner() {
		return (RelationshipMapping) super.getOwner();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getReferencedNameTable() {
		Entity targetEntity = getOwner().getResolvedTargetEntity();

		if (targetEntity != null) {
			return targetEntity.getPrimaryDbTable();
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Schema getDbSchema() {
		return getOwner().getTypeMapping().getDbSchema();
	}
}