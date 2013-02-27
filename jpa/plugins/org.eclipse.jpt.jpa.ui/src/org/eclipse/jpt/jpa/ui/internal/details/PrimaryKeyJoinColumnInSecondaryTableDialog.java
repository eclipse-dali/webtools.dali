/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlySecondaryTable;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class PrimaryKeyJoinColumnInSecondaryTableDialog
	extends BaseJoinColumnDialog<ReadOnlySecondaryTable, PrimaryKeyJoinColumn, PrimaryKeyJoinColumnInSecondaryTableStateObject>
{
	/**
	 * Use this constructor to create a <em>new</em> join column.
	 */
	protected PrimaryKeyJoinColumnInSecondaryTableDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			ReadOnlySecondaryTable secondaryTable) {
		this(parentShell, resourceManager, secondaryTable, null);
	}

	/**
	 * Use this constructor to edit an <em>existing</em> join column.
	 */
	protected PrimaryKeyJoinColumnInSecondaryTableDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			ReadOnlySecondaryTable secondaryTable,
			PrimaryKeyJoinColumn joinColumn) {
		super(parentShell, resourceManager, secondaryTable, joinColumn, buildTitle(joinColumn));
	}

	private static String buildTitle(PrimaryKeyJoinColumn joinColumn) {
		return (joinColumn == null) ?
				JptJpaUiDetailsMessages.PrimaryKeyJoinColumnInSecondaryTableDialog_addTitle :
				JptJpaUiDetailsMessages.PrimaryKeyJoinColumnInSecondaryTableDialog_editTitle;
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
		return new PrimaryKeyJoinColumnInSecondaryTableStateObject(this.getOwner(), this.getJoinColumn());
	}

	@Override
	protected String getDescriptionTitle() {
		return (this.getJoinColumn() == null) ?
				JptJpaUiDetailsMessages.PrimaryKeyJoinColumnInSecondaryTableDialog_addDescriptionTitle :
				JptJpaUiDetailsMessages.PrimaryKeyJoinColumnInSecondaryTableDialog_editDescriptionTitle;
	}
}
