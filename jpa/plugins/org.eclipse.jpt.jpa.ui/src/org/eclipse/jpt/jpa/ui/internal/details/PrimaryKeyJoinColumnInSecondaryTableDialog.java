/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class PrimaryKeyJoinColumnInSecondaryTableDialog
	extends BaseJoinColumnDialog<SecondaryTable, PrimaryKeyJoinColumn, PrimaryKeyJoinColumnInSecondaryTableStateObject>
{
	/**
	 * Use this constructor to create a <em>new</em> join column.
	 */
	protected PrimaryKeyJoinColumnInSecondaryTableDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			SecondaryTable secondaryTable) {
		this(parentShell, resourceManager, secondaryTable, null);
	}

	/**
	 * Use this constructor to edit an <em>existing</em> join column.
	 */
	protected PrimaryKeyJoinColumnInSecondaryTableDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			SecondaryTable secondaryTable,
			PrimaryKeyJoinColumn joinColumn) {
		super(parentShell, resourceManager, secondaryTable, joinColumn, buildTitle(joinColumn));
	}

	private static String buildTitle(PrimaryKeyJoinColumn joinColumn) {
		return (joinColumn == null) ?
				JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMN_IN_SECONDARY_TABLE_DIALOG_ADD_TITLE :
				JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMN_IN_SECONDARY_TABLE_DIALOG_EDIT_TITLE;
	}

	@Override
	protected DialogPane<PrimaryKeyJoinColumnInSecondaryTableStateObject> buildLayout(Composite container) {
		return new BaseJoinColumnDialogPane<PrimaryKeyJoinColumnInSecondaryTableStateObject>(
				this.getSubjectHolder(),
				container,
				this.resourceManager
			);
	}

	@Override
	protected PrimaryKeyJoinColumnInSecondaryTableStateObject buildStateObject() {
		return new PrimaryKeyJoinColumnInSecondaryTableStateObject(this.getParent(), this.getJoinColumn());
	}

	@Override
	protected String getDescriptionTitle() {
		return (this.getJoinColumn() == null) ?
				JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMN_IN_SECONDARY_TABLE_DIALOG_ADD_DESCRIPTION_TITLE :
				JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMN_IN_SECONDARY_TABLE_DIALOG_EDIT_DESCRIPTION_TITLE;
	}
}
