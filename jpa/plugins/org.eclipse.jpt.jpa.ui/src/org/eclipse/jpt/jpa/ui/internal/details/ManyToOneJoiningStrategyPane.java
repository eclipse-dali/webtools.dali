/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ManyToOneRelationship;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Here is the layout of this pane:  
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Joining Strategy ------------------------------------------------------ |
 * | |                                                                       | |
 * | | o JoinColumnStrategyPane ____________________________________________ | |
 * | | |                                                                   | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.3
 * @since 2.1
 */
public class ManyToOneJoiningStrategyPane extends Pane<ManyToOneRelationship>
{
	public ManyToOneJoiningStrategyPane(
			Pane<?> parentPane, 
			PropertyValueModel<? extends ManyToOneRelationship> subjectHolder, 
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected Composite addComposite(Composite container) {
		Section section = this.getWidgetFactory().createSection(container, 
				ExpandableComposite.TITLE_BAR | 
				ExpandableComposite.TWISTIE | 
				ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.JOINING_TITLE);

		Composite client = this.getWidgetFactory().createComposite(section);
		client.setLayout(new GridLayout(1, false));
		client.setLayoutData(new GridData(GridData.FILL_BOTH));
		section.setClient(client);

		return client;
	}

	@Override
	protected void initializeLayout(Composite container) {
		addRadioButton(
			container,
			JptJpaUiDetailsMessages.JOINING_JOIN_COLUMN_JOINING_LABEL,
			JoinColumnJoiningStrategyPane.buildUsesJoinColumnJoiningStrategyHolder(getSubjectHolder()),
			null);

		JoinColumnJoiningStrategyPane.
			buildJoinColumnJoiningStrategyPaneWithIncludeOverrideCheckBox(this, container);
	}
}
