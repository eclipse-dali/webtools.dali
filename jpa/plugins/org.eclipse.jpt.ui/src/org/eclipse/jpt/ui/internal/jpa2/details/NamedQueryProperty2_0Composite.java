/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.core.jpa2.context.NamedQuery2_0;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.details.NamedQueryPropertyComposite;
import org.eclipse.jpt.ui.internal.details.QueryHintsComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  NamedQueryProperty2_0Composite
 */
public class NamedQueryProperty2_0Composite extends NamedQueryPropertyComposite<NamedQuery2_0>
{
	/**
	 * Creates a new <code>NamedQueryProperty2_0Composite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public NamedQueryProperty2_0Composite(Pane<?> parentPane,
	                                   PropertyValueModel<NamedQuery2_0> subjectHolder,
	                                   Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		
		this.addLabeledText(
			container, 
			JptUiDetailsMessages.NamedQueryComposite_nameTextLabel, 
			this.buildNameTextHolder());

		// Query text area
		this.addLabeledMultiLineText(
			container,
			JptUiDetailsMessages.NamedQueryPropertyComposite_query,
			this.buildQueryHolder(),
			4,
			null
		);

		new LockModeComposite(this, container);
			
		// Query Hints pane
		container = this.addTitledGroup(
			this.addSubPane(container, 5),
			JptUiDetailsMessages.NamedQueryPropertyComposite_queryHintsGroupBox
		);

		new QueryHintsComposite(this, container);
	}
	
}
