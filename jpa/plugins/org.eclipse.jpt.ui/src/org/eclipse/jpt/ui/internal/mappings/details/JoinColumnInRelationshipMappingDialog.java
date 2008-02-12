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
 * TODO
 *
 * @see JoinColumnInRelationshipMappingStateObject
 * @see JoinColumnDialogPane
 *
 * @version 2.0
 * @since 2.0
 */
public class JoinColumnInRelationshipMappingDialog extends JoinColumnDialog<JoinColumnInRelationshipMappingStateObject> {

	private IRelationshipMapping relationshipMapping;

	/**
	 * Creates a new <code>AbstractJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	JoinColumnInRelationshipMappingDialog(Shell parent, IJoinColumn joinColumn) {
		super(parent, joinColumn);
	}

	/**
	 * Creates a new <code>AbstractJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param relationshipMapping
	 */
	JoinColumnInRelationshipMappingDialog(Shell parent,
		IRelationshipMapping relationshipMapping) {

		super(parent);
		this.relationshipMapping = relationshipMapping;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected JoinColumnInRelationshipMappingStateObject buildStateObject() {

		if (relationshipMapping != null) {
			return new JoinColumnInRelationshipMappingStateObject(relationshipMapping);
		}

		return new JoinColumnInRelationshipMappingStateObject(getJoinColumn());
	}
}