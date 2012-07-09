/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.OneToOneRelationship;
import org.eclipse.swt.widgets.Composite;

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
 * | | o JoinColumnStrategyPane ____________________________________________ | |
 * | | |                                                                   | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | | o PrimaryKeyJoinColumnStrategyPane __________________________________ | |
 * | | |                                                                   | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link OneToOneMapping}
 * @see {@link OneToOneRelationship}
 * @see {@link OneToOneMappingComposite}
 * @see {@link MappedByStrategyPane}
 * @see {@link JoinColumnStrategyPane}
 * @see {@link PrimaryKeyJoinColumnStrategyPane}
 *
 * @version 2.3
 * @since 2.1
 */
public class OneToOneJoiningStrategyPane 
	extends Pane<OneToOneRelationship>
{
	public OneToOneJoiningStrategyPane(
			Pane<?> parentPane, 
			PropertyValueModel<? extends OneToOneRelationship> subjectHolder, 
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	@Override
	protected Composite addComposite(Composite container) {
		return addCollapsibleSection(
			container,
			JptUiDetailsMessages.Joining_title,
			new SimplePropertyValueModel<Boolean>(Boolean.TRUE));
	}

	@Override
	protected void initializeLayout(Composite container) {		
		addRadioButton(
			container,
			JptUiDetailsMessages.Joining_mappedByLabel,
			MappedByJoiningStrategyPane.buildUsesMappedByJoiningStrategyHolder(getSubjectHolder()),
			null);

		new MappedByJoiningStrategyPane(this, container);
		
		addRadioButton(
			container,
			JptUiDetailsMessages.Joining_primaryKeyJoinColumnJoiningLabel,
			PrimaryKeyJoinColumnJoiningStrategyPane.buildUsesPrimaryKeyJoinColumnJoiningStrategyHolder(getSubjectHolder()),
			null);

		new PrimaryKeyJoinColumnJoiningStrategyPane(this, container);
		
		addRadioButton(
			container,
			JptUiDetailsMessages.Joining_joinColumnJoiningLabel,
			JoinColumnJoiningStrategyPane.buildUsesJoinColumnJoiningStrategyHolder(getSubjectHolder()),
			null);

		JoinColumnJoiningStrategyPane.
				buildJoinColumnJoiningStrategyPaneWithIncludeOverrideCheckBox(this, container);
	}
}
