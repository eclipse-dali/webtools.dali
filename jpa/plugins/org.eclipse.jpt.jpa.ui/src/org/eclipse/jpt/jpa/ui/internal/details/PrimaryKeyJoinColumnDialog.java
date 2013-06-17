/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class PrimaryKeyJoinColumnDialog
	extends BaseJoinColumnDialog<Entity, SpecifiedPrimaryKeyJoinColumn, PrimaryKeyJoinColumnStateObject>
{
	/**
	 * Use this constructor to create a <em>new</em> join column.
	 */
	protected PrimaryKeyJoinColumnDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			Entity entity) {
		this(parentShell, resourceManager, entity, null);
	}

	/**
	 * Use this constructor to edit an <em>existing</em> join column.
	 */
	protected PrimaryKeyJoinColumnDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			Entity entity,
			SpecifiedPrimaryKeyJoinColumn joinColumn) {
		super(parentShell, resourceManager, entity, joinColumn, buildTitle(joinColumn));
	}

	private static String buildTitle(SpecifiedPrimaryKeyJoinColumn joinColumn) {
		return (joinColumn == null) ?
				JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMN_DIALOG_ADD_TITLE :
				JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMN_DIALOG_EDIT_TITLE;
	}

	@Override
	protected DialogPane<PrimaryKeyJoinColumnStateObject> buildLayout(Composite container) {
		return new BaseJoinColumnDialogPane<PrimaryKeyJoinColumnStateObject>(
				this.getSubjectHolder(),
				container,
				this.resourceManager
			);
	}

	@Override
	protected PrimaryKeyJoinColumnStateObject buildStateObject() {
		return new PrimaryKeyJoinColumnStateObject(this.getParent(), this.getJoinColumn());
	}

	@Override
	protected String getDescriptionTitle() {
		return (this.getJoinColumn() == null) ?
				JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMN_DIALOG_ADD_DESCRIPTION_TITLE :
				JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMN_DIALOG_EDIT_DESCRIPTION_TITLE;
	}
}
