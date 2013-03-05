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
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationshipStrategy;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class JoinColumnInJoiningStrategyDialog 
	extends BaseJoinColumnDialog<JoinColumnRelationshipStrategy, JoinColumn, JoinColumnInJoiningStrategyStateObject> 
{
	/**
	 * Use this constructor to create a <em>new</em> join column.
	 */
	protected JoinColumnInJoiningStrategyDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			JoinColumnRelationshipStrategy strategy) {
		super(parentShell, resourceManager, strategy);
	}

	/**
	 * Use this constructor to edit an <em>existing</em> join column.
	 */
	protected JoinColumnInJoiningStrategyDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			JoinColumnRelationshipStrategy strategy,
			JoinColumn joinColumn) {
		super(parentShell, resourceManager, strategy, joinColumn);
	}

	@Override
	protected JoinColumnInJoiningStrategyStateObject buildStateObject() {
		return new JoinColumnInJoiningStrategyStateObject(this.getParent(), this.getJoinColumn());
	}

	@Override
	protected DialogPane<?> buildLayout(Composite container) {
		return new JoinColumnDialogPane<JoinColumnInJoiningStrategyStateObject>(
				this.getSubjectHolder(),
				container,
				this.resourceManager
			);
	}
}
