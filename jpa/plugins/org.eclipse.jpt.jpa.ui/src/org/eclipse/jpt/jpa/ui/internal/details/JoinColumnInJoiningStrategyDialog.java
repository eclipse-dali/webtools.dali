/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationshipStrategy;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog is used to either create or edit a joing column that is located
 * on a relational mapping.
 *
 * @see JoinColumn
 * @see JoinColumnRelationshipStrategy
 * @see JoinColumnInJoiningStrategyStateObject
 * @see JoinColumnDialogPane
 *
 * @version 2.0
 * @since 2.0
 */
public class JoinColumnInJoiningStrategyDialog 
	extends JoinColumnDialog<JoinColumnInJoiningStrategyStateObject> 
{
	/**
	 * Creates a new <code>AbstractJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param relationshipMapping The owner of the join column to edit or to
	 * create
	 * @param joinColumn The join column to edit or <code>null</code> if this is
	 * used to create a new one
	 */
	JoinColumnInJoiningStrategyDialog(
		Shell parent,
	    ReadOnlyJoinColumnRelationshipStrategy joinColumnOwner,
	    ReadOnlyJoinColumn joinColumn) {

		super(parent, joinColumnOwner, joinColumn);
	}

	@Override
	protected JoinColumnInJoiningStrategyStateObject buildStateObject() {
		return new JoinColumnInJoiningStrategyStateObject(
			getOwner(),
			getJoinColumn()
		);
	}

	@Override
	protected JoinColumnRelationshipStrategy getOwner() {
		return (JoinColumnRelationshipStrategy) super.getOwner();
	}
}