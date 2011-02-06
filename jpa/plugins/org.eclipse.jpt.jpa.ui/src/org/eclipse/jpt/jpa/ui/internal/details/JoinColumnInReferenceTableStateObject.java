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
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.ReferenceTable;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.db.Table;

/**
 * The state object used to create or edit a primary key join column on a
 * joint table.
 *
 * @see JoinColumn
 * @see JoinTable
 * @see InverseJoinColumnDialog
 * @see InverseJoinColumnDialogPane
 * @see JoinColumnInReferenceTableDialog
 *
 * @version 2.0
 * @since 2.0
 */
public class JoinColumnInReferenceTableStateObject 
	extends JoinColumnStateObject
{
	/**
	 * Creates a new <code>JoinColumnInJoinTableStateObject</code>.
	 *
	 * @param joinTable The owner of the join column to create or to edit
	 * @param joinColumn The join column to edit
	 */
	public JoinColumnInReferenceTableStateObject(
			ReferenceTable referenceTable,
	        JoinColumn joinColumn) {
		super(referenceTable, joinColumn);
	}
	
	
	@Override
	public ReferenceTable getOwner() {
		return (ReferenceTable) super.getOwner();
	}
	
	private TypeMapping getTypeMapping() {
		return getOwner().getPersistentAttribute().getOwningTypeMapping();
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
		return getTypeMapping().getPrimaryDbTable();
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