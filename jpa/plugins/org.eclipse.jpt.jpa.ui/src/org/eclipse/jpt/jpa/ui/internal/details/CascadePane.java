/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Cascade --------------------------------------------------------------- |
 * | |                                                                       | |
 * | | x All       x Persist   x Merge     x Remove    x Refresh             | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 */
public class CascadePane
	extends AbstractCascadePane<Cascade>
{
	public CascadePane(
		Pane<? extends RelationshipMapping> parentPane,
		PropertyValueModel<? extends Cascade> cascadeModel,
		Composite parent
	) {
		super(parentPane, cascadeModel, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {		
		addAllCheckBox(container);
		addPersistCheckBox(container);
		addMergeCheckBox(container);
		addRemoveCheckBox(container);
		addRefreshCheckBox(container);
	}

}