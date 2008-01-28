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
import org.eclipse.jpt.core.internal.context.base.ISingleRelationshipMapping;
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

	private ISingleRelationshipMapping relationshipMapping;

	/**
	 * Creates a new <code>AbstractJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param joinColumn
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
	                                      ISingleRelationshipMapping relationshipMapping) {

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