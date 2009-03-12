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
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;

/**
 * The state object used to create or edit a primary key join column on a
 * relationship mapping.
 *
 * @see JoinColumn
 * @see JoinColumnJoiningStrategy
 * @see JoinColumnInJoiningStrategyDialog
 *
 * @version 2.0
 * @since 2.0
 */
public class JoinColumnInJoiningStrategyStateObject 
	extends JoinColumnStateObject
{
	/**
	 * Creates a new <code>JoinColumnInJoiningStrategyStateObject</code>.
	 *
	 * @param joiningStrategy The owner of the join column to create
	 * @param joinColumn The join column to edit or <code>null</code> if this is
	 * used to create a new one
	 */
	public JoinColumnInJoiningStrategyStateObject(
			JoinColumnJoiningStrategy joiningStrategy,
		    JoinColumn joinColumn) {
		super(joiningStrategy, joinColumn);
	}
	
	
	@Override
	public JoinColumnJoiningStrategy getOwner() {
		return (JoinColumnJoiningStrategy) super.getOwner();
	}
	
	private RelationshipReference getRelationshipReference() {
		return getOwner().getRelationshipReference();
	}
	
	private RelationshipMapping getRelationshipMapping() {
		return getRelationshipReference().getRelationshipMapping();
	}
	
	private TypeMapping getTypeMapping() {
		return getRelationshipMapping().getTypeMapping();
	}
	
	@Override
	public String getDefaultTable() {
		JoinColumn joinColumn = getJoinColumn();
		
		if (joinColumn != null) {
			return joinColumn.getDefaultTable();
		}
		
		return getTypeMapping().getPrimaryTableName();
	}
	
	@Override
	public Table getNameTable() {
		Schema schema = this.getDbSchema();
		if (schema == null) {
			return null;
		}

		String tableIdentifier = this.getTable();
		if (tableIdentifier == null) {
			tableIdentifier = this.getDefaultTable();
		}

		return schema.getTableForIdentifier(tableIdentifier);
	}
	
	@Override
	public Table getReferencedNameTable() {
		Entity targetEntity = getRelationshipMapping().getResolvedTargetEntity();

		if (targetEntity != null) {
			return targetEntity.getPrimaryDbTable();
		}

		return null;
	}
	
	@Override
	public Schema getDbSchema() {
		return getTypeMapping().getDbSchema();
	}
}
