/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToManyRelationship2_0;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.JoinColumnJoiningStrategyPane;
import org.eclipse.jpt.jpa.ui.internal.details.JoinTableJoiningStrategyPane;
import org.eclipse.jpt.jpa.ui.internal.details.MappedByJoiningStrategyPane;
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
 * | | o MappedByJoiningStrategyPane _______________________________________ | |
 * | | |                                                                   | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | | o JoinTableStrategyPane _____________________________________________ | |
 * | | |                                                                   | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.3
 * @since 2.1
 */
public class OneToManyJoiningStrategyPane2_0
	extends Pane<OneToManyRelationship2_0>
{
	public OneToManyJoiningStrategyPane2_0(
			Pane<?> parentPane, 
			PropertyValueModel<? extends OneToManyRelationship2_0> subjectHolder, 
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected Composite addComposite(Composite container) {
		Section section = getWidgetFactory().createSection(container, 
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
	@SuppressWarnings("unused")
	protected void initializeLayout(Composite container) {
		addRadioButton(
			container,
			JptJpaUiDetailsMessages.JOINING_MAPPED_BY_LABEL,
			MappedByJoiningStrategyPane.buildUsesMappedByJoiningStrategyModel(getSubjectHolder()),
			null);

		new MappedByJoiningStrategyPane(this, container);
		
		addRadioButton(
			container,
			JptJpaUiDetailsMessages.JOINING_JOIN_COLUMN_JOINING_LABEL,
			JoinColumnJoiningStrategyPane.buildUsesJoinColumnJoiningStrategyModel(getSubjectHolder()),
			null);

		JoinColumnJoiningStrategyPane.
			buildJoinColumnJoiningStrategyPaneWithoutIncludeOverrideCheckBox(this, container);
		
		addRadioButton(
			container,
			JptJpaUiDetailsMessages.JOINING_JOIN_TABLE_JOINING_LABEL,
			JoinTableJoiningStrategyPane.buildUsesJoinTableJoiningStrategyModel(getSubjectHolder()),
			null);

		new JoinTableJoiningStrategyPane(this, container);
	}
}
