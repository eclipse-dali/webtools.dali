/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractValidatingDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @see AbstractJoinColumnStateObject
 *
 * TODO: If there is only 1 join column and the user is editing it, they should
 * be able to define defaults. otherwise, we probably shouldn't allow it.
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class AbstractJoinColumnDialog<T extends AbstractJoinColumnStateObject> extends AbstractValidatingDialog<T> {

	private IAbstractJoinColumn joinColumn;

	/**
	 * Creates a new <code>AbstractJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 */
	public AbstractJoinColumnDialog(Shell parent) {
		super(parent);
	}

	/**
	 * Creates a new <code>AbstractJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public AbstractJoinColumnDialog(Shell parent,
	                                IAbstractJoinColumn joinColumn) {

		this(parent);
		this.joinColumn = joinColumn;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String description() {
		return JptUiMappingsMessages.JoinColumnDialog_description;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String descriptionTitle() {

		if (joinColumn == null) {
			return JptUiMappingsMessages.JoinColumnDialog_addJoinColumnDescriptionTitle;
		}

		return JptUiMappingsMessages.JoinColumnDialog_editJoinColumnDescriptionTitle;
	}

	/**
	 * Returns
	 *
	 * @return
	 */
	public IAbstractJoinColumn getJoinColumn() {
		return joinColumn;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String title() {

		if (joinColumn == null) {
			return JptUiMappingsMessages.JoinColumnDialog_addJoinColumnTitle;
		}

		return JptUiMappingsMessages.JoinColumnDialog_editJoinColumnTitle;
	}
}