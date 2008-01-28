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

import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinTable;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * TODO
 *
 * @see JoinColumnInJoinTableStateObject
 * @see InverseJoinColumnDialogPane
 *
 * @version 2.0
 * @since 2.0
 */
public class InverseJoinColumnDialog extends JoinColumnInJoinTableDialog {

	/**
	 * Creates a new <code>InverseJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param joinColumn
	 */
	public InverseJoinColumnDialog(Shell parent, IJoinColumn joinColumn) {

		super(parent, joinColumn);
	}

	/**
	 * Creates a new <code>InverseJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param joinTable
	 */
	public InverseJoinColumnDialog(Shell parent, IJoinTable joinTable) {

		super(parent, joinTable);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeMainPane(Composite container) {
		new InverseJoinColumnDialogPane(getSubjectHolder(), container);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String title() {

		if (getJoinColumn() != null) {
			return JptUiMappingsMessages.InverseJoinColumnDialog_editInverseJoinColumn;
		}

		return super.title();
	}
}