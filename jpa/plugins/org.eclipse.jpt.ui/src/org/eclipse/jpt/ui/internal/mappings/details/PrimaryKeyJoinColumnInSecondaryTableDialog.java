/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.ui.internal.widgets.AbstractDialogPane;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * TODO
 *
 * @see PrimaryKeyJoinColumnInSecondaryTableStateObject
 * @see AbstractJoinColumnDialogPane
 *
 * @version 2.0
 * @since 2.0
 */
public class PrimaryKeyJoinColumnInSecondaryTableDialog extends AbstractJoinColumnDialog<PrimaryKeyJoinColumnInSecondaryTableStateObject> {

	private ISecondaryTable secondaryTable;

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public PrimaryKeyJoinColumnInSecondaryTableDialog(Shell parent,
	                                                  IPrimaryKeyJoinColumn joinColumn) {

		super(parent, joinColumn);
	}

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param secondaryTable
	 */
	public PrimaryKeyJoinColumnInSecondaryTableDialog(Shell parent,
	                                                  ISecondaryTable secondaryTable) {

		super(parent);
		this.secondaryTable = secondaryTable;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected AbstractDialogPane<PrimaryKeyJoinColumnInSecondaryTableStateObject> buildLayout(Composite container) {
		return new AbstractJoinColumnDialogPane<PrimaryKeyJoinColumnInSecondaryTableStateObject>(
			subjectHolder(),
			container
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected PrimaryKeyJoinColumnInSecondaryTableStateObject buildStateObject() {

		if (secondaryTable != null) {
			return new PrimaryKeyJoinColumnInSecondaryTableStateObject(secondaryTable);
		}

		return new PrimaryKeyJoinColumnInSecondaryTableStateObject(getJoinColumn());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public IPrimaryKeyJoinColumn getJoinColumn() {
		return (IPrimaryKeyJoinColumn) super.getJoinColumn();
	}
}