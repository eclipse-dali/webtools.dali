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
import org.eclipse.jpt.common.ui.internal.widgets.ValidatingDialog;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.widgets.Shell;

public abstract class BaseJoinColumnDialog<P, C extends BaseJoinColumn, S extends BaseJoinColumnStateObject>
	extends ValidatingDialog<S>
{
	/**
	 * This will be <code>null</code> when creating a new join column.
	 */
	private final C joinColumn;

	/**
	 * The parent of the new or existing join column.
	 */
	private final P parent;


	/**
	 * Use this constructor to create a <em>new</em> join column.
	 */
	protected BaseJoinColumnDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			P parent) {
		this(parentShell, resourceManager, parent, null);
	}

	/**
	 * Use this constructor to edit an <em>existing</em> join column.
	 */
	protected BaseJoinColumnDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			P parent,
			C joinColumn) {
		this(parentShell, resourceManager, parent, joinColumn, buildTitle(joinColumn));
	}

	private static String buildTitle(BaseJoinColumn joinColumn) {
		return (joinColumn == null) ?
				JptJpaUiDetailsMessages.JoinColumnDialog_addJoinColumnTitle :
				JptJpaUiDetailsMessages.JoinColumnDialog_editJoinColumnTitle;
	}

	protected BaseJoinColumnDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			P parent,
			C joinColumn,
			String title) {
		super(parentShell, resourceManager, title);
		this.parent = parent;
		this.joinColumn = joinColumn;
	}

	@Override
	protected String getDescription() {
		return JptJpaUiDetailsMessages.JoinColumnDialog_description;
	}

	@Override
	protected String getDescriptionTitle() {
		return (this.joinColumn == null) ?
				JptJpaUiDetailsMessages.JoinColumnDialog_addJoinColumnDescriptionTitle :
				JptJpaUiDetailsMessages.JoinColumnDialog_editJoinColumnDescriptionTitle;
	}

	public C getJoinColumn() {
		return this.joinColumn;
	}

	protected P getParent() {
		return this.parent;
	}
}
