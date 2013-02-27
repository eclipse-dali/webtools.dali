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
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.Relationship;
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
	extends AbstractJoiningStrategyPane
		<ReadOnlyJoinColumnRelationship, JoinColumnRelationshipStrategy>
{
	private final boolean includeOverrideCheckBox;
	
	
	public static JoinColumnJoiningStrategyPane buildJoinColumnJoiningStrategyPaneWithIncludeOverrideCheckBox(
		Pane<? extends ReadOnlyJoinColumnRelationship> parentPane, 
		Composite parent) {
		return new JoinColumnJoiningStrategyPane(parentPane, parent, true);
	}
	
	public static JoinColumnJoiningStrategyPane buildJoinColumnJoiningStrategyPaneWithoutIncludeOverrideCheckBox(
		Pane<? extends ReadOnlyJoinColumnRelationship> parentPane, 
		Composite parent) {
		return new JoinColumnJoiningStrategyPane(parentPane, parent, false);
	}
	
	public static JoinColumnJoiningStrategyPane buildJoinColumnJoiningStrategyPaneWithIncludeOverrideCheckBox(
		Pane<?> parentPane,
		PropertyValueModel<? extends ReadOnlyJoinColumnRelationship> subjectHolder,
        Composite parent) {
		return new JoinColumnJoiningStrategyPane(parentPane, subjectHolder, parent, true);
	}
	
	public static JoinColumnJoiningStrategyPane buildJoinColumnJoiningStrategyPaneWithoutIncludeOverrideCheckBox(
		Pane<?> parentPane,
		PropertyValueModel<? extends ReadOnlyJoinColumnRelationship> subjectHolder,
        Composite parent) {
		return new JoinColumnJoiningStrategyPane(parentPane, subjectHolder, parent, false);
	}
	
	
	private JoinColumnJoiningStrategyPane(
			Pane<? extends ReadOnlyJoinColumnRelationship> parentPane, 
			Composite parent,
	        boolean includeOverrideCheckBox) {
		super(parentPane, parent);
		this.includeOverrideCheckBox = includeOverrideCheckBox;
		initializeLayout2(getControl());
	}
	
	private JoinColumnJoiningStrategyPane(Pane<?> parentPane,
			PropertyValueModel<? extends ReadOnlyJoinColumnRelationship> subjectHolder,
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
		PropertyValueModel<JoinColumnRelationshipStrategy> joiningStrategyModel = this.buildJoinColumnJoiningStrategyHolder();

		return this.includeOverrideCheckBox ?
				new JoiningStrategyJoinColumnsWithOverrideOptionComposite(this, joiningStrategyModel, parent).getControl() :
				new JoiningStrategyJoinColumnsComposite(this, joiningStrategyModel, parent).getControl();
	}

	@Override
	protected ModifiablePropertyValueModel<Boolean> buildUsesStrategyHolder() {
		return buildUsesJoinColumnJoiningStrategyHolder(getSubjectHolder());
	}

	protected PropertyValueModel<JoinColumnRelationshipStrategy> buildJoinColumnJoiningStrategyHolder() {
		return new PropertyAspectAdapter
				<ReadOnlyJoinColumnRelationship, JoinColumnRelationshipStrategy>(
					getSubjectHolder()) {
			@Override
			protected JoinColumnRelationshipStrategy buildValue_() {
				return this.subject.getJoinColumnStrategy();
			}
		};
	}

	public static ModifiablePropertyValueModel<Boolean> buildUsesJoinColumnJoiningStrategyHolder(PropertyValueModel<? extends ReadOnlyJoinColumnRelationship> subjectHolder) {
		return new PropertyAspectAdapter<ReadOnlyJoinColumnRelationship, Boolean>(
				subjectHolder, Relationship.STRATEGY_PROPERTY) {
			@Override
			protected Boolean buildValue() {
				return Boolean.valueOf(this.buildBooleanValue());
			}
			
			protected boolean buildBooleanValue() {
				return (this.subject != null) && this.subject.strategyIsJoinColumn();
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					((SpecifiedJoinColumnRelationship) this.subject).setStrategyToJoinColumn();
				}
				//value == FALSE - selection of another radio button causes this strategy to get unset
			}
		};
	}
}
