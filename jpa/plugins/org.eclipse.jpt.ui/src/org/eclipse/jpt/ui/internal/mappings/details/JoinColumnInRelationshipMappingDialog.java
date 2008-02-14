/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog is used to either create or edit a joing column that is located
 * on a relational mapping.
 *
 * @see IJoinColumn
 * @see IRelationshipMapping
 * @see JoinColumnInRelationshipMappingStateObject
 * @see JoinColumnDialogPane
 *
 * @version 2.0
 * @since 2.0
 */
public class JoinColumnInRelationshipMappingDialog extends JoinColumnDialog<JoinColumnInRelationshipMappingStateObject> {

	/**
	 * Creates a new <code>AbstractJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param relationshipMapping The owner of the join column to edit or to
	 * create
	 * @param joinColumn The join column to edit or <code>null</code> if this is
	 * used to create a new one
	 */
	JoinColumnInRelationshipMappingDialog(Shell parent,
	                                      IRelationshipMapping relationshipMapping,
	                                      IJoinColumn joinColumn) {

		super(parent, relationshipMapping, joinColumn);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected JoinColumnInRelationshipMappingStateObject buildStateObject() {
		return new JoinColumnInRelationshipMappingStateObject(
			getOwner(),
			getJoinColumn()
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected IRelationshipMapping getOwner() {
		return (IRelationshipMapping) super.getOwner();
	}
}