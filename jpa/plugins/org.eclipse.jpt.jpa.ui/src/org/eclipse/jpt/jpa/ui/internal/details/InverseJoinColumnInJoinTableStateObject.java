/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.db.Table;

/**
 * The state object used to create or edit a primary key join column on a join
 * table.
 *
 * @see JoinColumn
 * @see JoinTable
 * @see InverseJoinColumnInJoinTableDialog
 *
 * @version 2.3
 * @since 2.0
 */
public class InverseJoinColumnInJoinTableStateObject 
	extends JoinColumnStateObject
{
	/**
	 * Creates a new <code>JoinColumnInJoinTableStateObject</code>.
	 *
	 * @param joinTable
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public InverseJoinColumnInJoinTableStateObject(
			JoinTable joinTable,
	        JoinColumn joinColumn) {
		super(joinTable, joinColumn);
	}
	
	
	@Override
	public JoinTable getOwner() {
		return (JoinTable) super.getOwner();
	}
	
	private RelationshipMapping getRelationshipMapping() {
		return getOwner().getRelationshipMapping();
	}
	
	@Override
	public String getDefaultTable() {
		return null;
	}
	
	@Override
	public Table getNameTable() {
		return getOwner().getDbTable();
	}
	
	@Override
	public Table getReferencedNameTable() {
		RelationshipMapping relationshipMapping = getRelationshipMapping();
		if (relationshipMapping == null) {
			return null;
		}
		Entity targetEntity = relationshipMapping.getResolvedTargetEntity();
		if (targetEntity == null) {
			return null;
		}
		return targetEntity.getPrimaryDbTable();
	}
	
	@Override
	protected String getInitialTable() {
		return getOwner().getName();
	}
	
	@Override
	protected boolean isTableEditable() {
		return false;
	}
	
	@Override
	public ListIterator<String> tables() {
		return new SingleElementListIterator<String>(getInitialTable());
	}
}