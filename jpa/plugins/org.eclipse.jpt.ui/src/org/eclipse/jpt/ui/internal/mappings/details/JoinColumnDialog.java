/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * TODO
 *
 * @see JoinColumnStateObject
 * @see JoinColumnDialogPane
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class JoinColumnDialog<T extends JoinColumnStateObject> extends AbstractJoinColumnDialog<T> {

	/**
	 * Creates a new <code>AbstractJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 */
	public JoinColumnDialog(Shell parent) {
		super(parent);
	}

	/**
	 * Creates a new <code>AbstractJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param joinColumn
	 */
	public JoinColumnDialog(Shell parent, IJoinColumn joinColumn) {
		super(parent, joinColumn);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public IJoinColumn getJoinColumn() {
		return (IJoinColumn) super.getJoinColumn();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeMainPane(Composite container) {
		new JoinColumnDialogPane(getSubjectHolder(), container);
	}
}