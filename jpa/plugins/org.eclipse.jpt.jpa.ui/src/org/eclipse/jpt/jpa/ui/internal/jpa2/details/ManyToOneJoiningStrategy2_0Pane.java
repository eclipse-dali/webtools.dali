/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ManyToOneRelationship;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToOneRelationship2_0;
import org.eclipse.jpt.jpa.ui.internal.details.JoinColumnJoiningStrategyPane;
import org.eclipse.jpt.jpa.ui.internal.details.JoinTableJoiningStrategyPane;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;

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
 * | | o JoinTableJoiningStrategyPane_______________________________________ | |
 * | | |                                                                   | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link ManyToOneMapping}
 * @see {@link ManyToOneRelationship}
 * @see {@link OrmManyToOneMappingComposite}
 * @see {@link JoinColumnStrategyPane}
 *
 * @version 2.3
 * @since 2.1
 */
public class ManyToOneJoiningStrategy2_0Pane extends Pane<ManyToOneRelationship2_0>
{
	public ManyToOneJoiningStrategy2_0Pane(
			Pane<?> parentPane, 
			PropertyValueModel<? extends ManyToOneRelationship2_0> subjectHolder, 
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		Composite composite = addCollapsibleSection(
				container,
				JptUiDetailsMessages.Joining_title,
				new SimplePropertyValueModel<Boolean>(Boolean.TRUE));
		
		addRadioButton(
			composite,
			JptUiDetailsMessages.Joining_joinColumnJoiningLabel,
			JoinColumnJoiningStrategyPane.buildUsesJoinColumnJoiningStrategyHolder(getSubjectHolder()),
			null);

		JoinColumnJoiningStrategyPane.
			buildJoinColumnJoiningStrategyPaneWithIncludeOverrideCheckBox(this, composite);
		
		addRadioButton(
			composite,
			JptUiDetailsMessages.Joining_joinTableJoiningLabel,
			JoinTableJoiningStrategyPane.buildUsesJoinTableJoiningStrategyHolder(getSubjectHolder()),
			null);

		new JoinTableJoiningStrategyPane(this, composite);

		addSubPane(composite, 5);
	}
}
