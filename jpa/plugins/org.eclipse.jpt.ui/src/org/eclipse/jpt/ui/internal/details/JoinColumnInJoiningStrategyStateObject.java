/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * The state object used to create or edit a primary key join column on a
 * relationship mapping.
 *
 * @see JoinColumn
 * @see JoinColumnJoiningStrategy
 * @see JoinColumnInJoiningStrategyDialog
 *
 * @version 2.3
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

	@Override
	public ListIterator<String> tables() {
		Schema schema = getDbSchema();
		return schema == null ? super.tables() : CollectionTools.list(schema.getSortedTableIdentifiers()).listIterator();
	}
	
	protected Schema getDbSchema() {
		return getRelationshipSource().getDbSchema();
	}

	protected TypeMapping getRelationshipSource() {
		return getOwner().getRelationshipSource();
	}
	
	protected TypeMapping getRelationshipTarget() {
		return getOwner().getRelationshipTarget();
	}
	
	@Override
	public String getDefaultTable() {
		JoinColumn joinColumn = getJoinColumn();

		if (joinColumn != null) {
			return joinColumn.getDefaultTable();
		}
		
		return getRelationshipSource().getPrimaryTableName();
	}
	
	@Override
	public Table getNameTable() {
		TypeMapping typeMapping = getRelationshipSource();
		return typeMapping == null ? null : typeMapping.getPrimaryDbTable();
	}
	
	@Override
	public Table getReferencedNameTable() {
		TypeMapping relationshipTarget = getRelationshipTarget();
		return relationshipTarget == null ? null : relationshipTarget.getPrimaryDbTable();
	}

}
