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
import org.eclipse.jpt.jpa.core.context.ReferenceTable;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class JoinColumnInReferenceTableDialog
	extends BaseJoinColumnDialog<ReferenceTable, JoinColumn, JoinColumnInReferenceTableStateObject>
{
	/**
	 * Use this constructor to create a <em>new</em> join column.
	 */
	protected JoinColumnInReferenceTableDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			ReferenceTable referenceTable) {
		super(parentShell, resourceManager, referenceTable);
	}

	/**
	 * Use this constructor to edit an <em>existing</em> join column.
	 */
	protected JoinColumnInReferenceTableDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			ReferenceTable referenceTable,
			JoinColumn joinColumn) {
		super(parentShell, resourceManager, referenceTable, joinColumn);
	}

	@Override
	protected DialogPane<JoinColumnInReferenceTableStateObject> buildLayout(Composite container) {
		return new JoinColumnDialogPane<JoinColumnInReferenceTableStateObject>(
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
	protected JoinColumnInReferenceTableStateObject buildStateObject() {
		return new JoinColumnInReferenceTableStateObject(this.getParent(), this.getJoinColumn());
	}
}
