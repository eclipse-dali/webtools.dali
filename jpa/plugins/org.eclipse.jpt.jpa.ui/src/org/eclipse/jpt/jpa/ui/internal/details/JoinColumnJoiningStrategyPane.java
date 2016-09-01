/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumnRelationship;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Here is the layout of this pane:  
 * <pre>
 * -----------------------------------------------------------------------------
 * | o Join columns __________________________________________________________ |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | |  JoiningStrategyJoinColumnsComposite                              | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see SpecifiedJoinColumnRelationship
 * @see JoinColumnRelationshipStrategy
 * @see OneToOneJoiningStrategyPane
 * @see ManyToOneJoiningStrategyPane
 *
 * @version 2.3
 * @since 2.1
 */
public class JoinColumnJoiningStrategyPane
	extends AbstractJoiningStrategyPane<JoinColumnRelationship>
{
	private final boolean includeOverrideCheckBox;
	
	
	public static JoinColumnJoiningStrategyPane buildJoinColumnJoiningStrategyPaneWithIncludeOverrideCheckBox(
		Pane<? extends JoinColumnRelationship> parentPane, 
		Composite parent) {
		return new JoinColumnJoiningStrategyPane(parentPane, parent, true);
	}
	
	public static JoinColumnJoiningStrategyPane buildJoinColumnJoiningStrategyPaneWithoutIncludeOverrideCheckBox(
		Pane<? extends JoinColumnRelationship> parentPane, 
		Composite parent) {
		return new JoinColumnJoiningStrategyPane(parentPane, parent, false);
	}
	
	public static JoinColumnJoiningStrategyPane buildJoinColumnJoiningStrategyPaneWithIncludeOverrideCheckBox(
		Pane<?> parentPane,
		PropertyValueModel<? extends JoinColumnRelationship> subjectHolder,
        Composite parent) {
		return new JoinColumnJoiningStrategyPane(parentPane, subjectHolder, parent, true);
	}
	
	public static JoinColumnJoiningStrategyPane buildJoinColumnJoiningStrategyPaneWithoutIncludeOverrideCheckBox(
		Pane<?> parentPane,
		PropertyValueModel<? extends JoinColumnRelationship> subjectHolder,
        Composite parent) {
		return new JoinColumnJoiningStrategyPane(parentPane, subjectHolder, parent, false);
	}
	
	
	private JoinColumnJoiningStrategyPane(
			Pane<? extends JoinColumnRelationship> parentPane, 
			Composite parent,
	        boolean includeOverrideCheckBox) {
		super(parentPane, parent);
		this.includeOverrideCheckBox = includeOverrideCheckBox;
		initializeLayout2(getControl());
	}
	
	private JoinColumnJoiningStrategyPane(Pane<?> parentPane,
			PropertyValueModel<? extends JoinColumnRelationship> subjectHolder,
			Composite parent,
			boolean includeOverrideCheckBox) {
		
		super(parentPane, subjectHolder, parent);
		this.includeOverrideCheckBox = includeOverrideCheckBox;
		initializeLayout2(getControl());
	}

	@Override
	protected void initializeLayout(Composite container) {
		//see initializeLayout2
	}

	protected void initializeLayout2(Composite container) {
		super.initializeLayout(container); 
		//just call super, we are delaying the initializeLayout because of the includeOverrideCheckBox boolean
	}

	@Override
	protected Control buildStrategyDetailsComposite(Composite parent) {
		PropertyValueModel<JoinColumnRelationshipStrategy> joiningStrategyModel = this.buildJoinColumnJoiningStrategyModel();

		return this.includeOverrideCheckBox ?
				new JoiningStrategyJoinColumnsWithOverrideOptionComposite(this, joiningStrategyModel, parent).getControl() :
				new JoiningStrategyJoinColumnsComposite(this, joiningStrategyModel, parent).getControl();
	}

	@Override
	protected ModifiablePropertyValueModel<Boolean> buildUsesStrategyModel() {
		return buildUsesJoinColumnJoiningStrategyModel(getSubjectHolder());
	}

	protected PropertyValueModel<JoinColumnRelationshipStrategy> buildJoinColumnJoiningStrategyModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> m.getJoinColumnStrategy());
	}

	public static ModifiablePropertyValueModel<Boolean> buildUsesJoinColumnJoiningStrategyModel(PropertyValueModel<? extends JoinColumnRelationship> subjectHolder) {
		return PropertyValueModelTools.modifiableSubjectModelAspectAdapter_(
				subjectHolder,
				Relationship.STRATEGY_PROPERTY,
				m -> Boolean.valueOf((m != null) && m.strategyIsJoinColumn()),
				(m, value) -> {
					if ((m != null) && (value != null) && value.booleanValue()) {
						((SpecifiedJoinColumnRelationship) m).setStrategyToJoinColumn();
					}
				}
			);
	}
}
