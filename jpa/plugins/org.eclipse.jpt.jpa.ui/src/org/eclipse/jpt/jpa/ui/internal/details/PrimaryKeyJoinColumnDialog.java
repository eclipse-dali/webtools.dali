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
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog is used to either create or edit a primary key join column that
 * is located on an entity.
 *
 * @see PrimaryKeyJoinColumn
 * @see Entity
 * @see PrimaryKeyJoinColumnStateObject
 * @see BaseJoinColumnDialogPane
 *
 * @version 2.0
 * @since 2.0
 */
public class PrimaryKeyJoinColumnDialog extends BaseJoinColumnDialog<PrimaryKeyJoinColumnStateObject> {

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param entity The owner of the join column to create or where it is
	 * located
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public PrimaryKeyJoinColumnDialog(Shell parent,
	                                  Entity entity,
	                                  PrimaryKeyJoinColumn joinColumn) {

		super(parent, entity, joinColumn);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected DialogPane<PrimaryKeyJoinColumnStateObject> buildLayout(Composite container) {
		return new BaseJoinColumnDialogPane<PrimaryKeyJoinColumnStateObject>(
			getSubjectHolder(),
			container
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected PrimaryKeyJoinColumnStateObject buildStateObject() {
		return new PrimaryKeyJoinColumnStateObject(getOwner(), getJoinColumn());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String getDescriptionTitle() {

		if (getJoinColumn() == null) {
			return JptUiDetailsMessages.PrimaryKeyJoinColumnDialog_addDescriptionTitle;
		}

		return JptUiDetailsMessages.PrimaryKeyJoinColumnDialog_editDescriptionTitle;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public PrimaryKeyJoinColumn getJoinColumn() {
		return (PrimaryKeyJoinColumn) super.getJoinColumn();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Entity getOwner() {
		return (Entity) super.getOwner();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String getTitle() {

		if (getJoinColumn() == null) {
			return JptUiDetailsMessages.PrimaryKeyJoinColumnDialog_addTitle;
		}

		return JptUiDetailsMessages.PrimaryKeyJoinColumnDialog_editTitle;
	}
}