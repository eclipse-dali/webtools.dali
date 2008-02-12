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
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;

/**
 * @version 2.0
 * @since 2.0
 */
public class JoinColumnInRelationshipMappingStateObject extends JoinColumnStateObject
{
	private IRelationshipMapping relationshipMapping;

	/**
	 * Creates a new <code>JoinColumnInRelationshipMappingStateObject</code>.
	 *
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public JoinColumnInRelationshipMappingStateObject(IJoinColumn joinColumn) {
		super(joinColumn);
		initialize(joinColumn.owner().relationshipMapping());
	}

	/**
	 * Creates a new <code>JoinColumnInRelationshipMappingStateObject</code>.
	 *
	 * @param relationshipMapping
	 */
	public JoinColumnInRelationshipMappingStateObject(IRelationshipMapping relationshipMapping) {
		super();
		initialize(relationshipMapping);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public String defaultTableName() {

		IJoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getDefaultTable();
		}

		return relationshipMapping.typeMapping().tableName();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getNameTable() {
		Schema schema = getSchema();
		return (schema == null) ? null : schema.tableNamed(tableName());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getReferencedNameTable() {
		IEntity targetEntity = relationshipMapping.getResolvedTargetEntity();

		if (targetEntity != null) {
			return targetEntity.primaryDbTable();
		}

		return null;
	}

	/**
	 * Returns
	 *
	 * @return
	 */
	public IRelationshipMapping getRelationshipMapping() {
		return relationshipMapping;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Schema getSchema() {
		return relationshipMapping.typeMapping().dbSchema();
	}

	private void initialize(IRelationshipMapping relationshipMapping) {
		this.relationshipMapping = relationshipMapping;

		// If the table isn't set or is the default table, then
		// use the mapping's table
		if ((getTable() == null) || isDefaultTableSelected()) {
			String table = relationshipMapping.typeMapping().tableName();

			setTable(table);
			setDefaultTableSelected(table != null);
		}
	}
}