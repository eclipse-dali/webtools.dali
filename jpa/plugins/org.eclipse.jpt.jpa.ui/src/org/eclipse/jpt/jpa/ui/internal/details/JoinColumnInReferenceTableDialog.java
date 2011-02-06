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

import org.eclipse.jpt.common.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyReferenceTable;
import org.eclipse.jpt.jpa.core.context.ReferenceTable;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog is used to either create or edit a join column that is located
 * on a join table.
 *
 * @see JoinColumn
 * @see JoinTable
 * @see JoinColumnInReferenceTableStateObject
 * @see BaseJoinColumnDialogPane
 *
 * @version 2.0
 * @since 1.0
 */
public class JoinColumnInReferenceTableDialog extends BaseJoinColumnDialog<JoinColumnInReferenceTableStateObject> {

	/**
	 * Creates a new <code>JoinColumnInReferenceTableDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param joinTable The parent of the join column to edit or to create
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public JoinColumnInReferenceTableDialog(Shell parent,
	                                   ReadOnlyReferenceTable referenceTable,
	                                   ReadOnlyJoinColumn joinColumn) {

		super(parent, referenceTable, joinColumn);
	}

	@Override
	protected DialogPane<JoinColumnInReferenceTableStateObject> buildLayout(Composite container) {
		return new JoinColumnDialogPane<JoinColumnInReferenceTableStateObject>(
			getSubjectHolder(),
			container
		) {
			@Override
			protected boolean isTableEditable() {
				return false;
			}
		};
	}

	@Override
	protected JoinColumnInReferenceTableStateObject buildStateObject() {
		return new JoinColumnInReferenceTableStateObject(
			getOwner(),
			getJoinColumn()
		);
	}

	@Override
	public JoinColumn getJoinColumn() {
		return (JoinColumn) super.getJoinColumn();
	}

	@Override
	protected ReferenceTable getOwner() {
		return (ReferenceTable) super.getOwner();
	}
}