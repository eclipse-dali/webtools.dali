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
import org.eclipse.jpt.core.internal.context.base.ISingleRelationshipMapping;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;

/**
 * @version 2.0
 * @since 2.0
 */
public class JoinColumnInRelationshipMappingStateObject extends JoinColumnStateObject
{
	private ISingleRelationshipMapping relationshipMapping;

	/**
	 * Creates a new <code>JoinColumnInRelationshipMappingStateObject</code>.
	 *
	 * @param joinColumn
	 */
	public JoinColumnInRelationshipMappingStateObject(IJoinColumn joinColumn) {
		super(joinColumn);
		this.relationshipMapping = (ISingleRelationshipMapping) joinColumn.parent();
	}

	/**
	 * Creates a new <code>JoinColumnInRelationshipMappingStateObject</code>.
	 */
	public JoinColumnInRelationshipMappingStateObject(ISingleRelationshipMapping relationshipMapping) {
		super();
		this.relationshipMapping = relationshipMapping;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public String defaultTableName() {

		if (getJoinColumn() != null) {
			return getJoinColumn().getDefaultTable();
		}

		return relationshipMapping.typeMapping().getTableName();
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
	public ISingleRelationshipMapping getRelationshipMapping() {
		return relationshipMapping;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Schema getSchema() {
		return relationshipMapping.typeMapping().dbSchema();
	}
}
