/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class InverseJoinColumnInJoinTableDialog
	extends BaseJoinColumnDialog<JoinTable, JoinColumn, InverseJoinColumnInJoinTableStateObject>
{
	/**
	 * Use this constructor to create a <em>new</em> join column.
	 */
	protected InverseJoinColumnInJoinTableDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			JoinTable joinTable) {
		super(parentShell, resourceManager, joinTable);
	}

	/**
	 * Use this constructor to edit an <em>existing</em> join column.
	 */
	protected InverseJoinColumnInJoinTableDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			JoinTable joinTable,
			JoinColumn joinColumn) {
		super(parentShell, resourceManager, joinTable, joinColumn);
	}

	@Override
	protected DialogPane<InverseJoinColumnInJoinTableStateObject> buildLayout(Composite container) {
		return new JoinColumnDialogPane<InverseJoinColumnInJoinTableStateObject>(
				this.getSubjectHolder(),
				container,
				this.resourceManager
			) {
				@Override
				protected boolean isTableEditable() {
					return false;
				}
			};
	}

	@Override
	protected InverseJoinColumnInJoinTableStateObject buildStateObject() {
		return new InverseJoinColumnInJoinTableStateObject(
			getOwner(),
			getJoinColumn()
		);
	}
}
