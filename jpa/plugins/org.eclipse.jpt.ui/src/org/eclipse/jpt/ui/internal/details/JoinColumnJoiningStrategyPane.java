/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.JoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

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
 * @see {@link JoinColumnEnabledRelationshipReference}
 * @see {@link JoinColumnJoiningStrategy}
 * @see {@link OneToOneJoiningStrategyPane}
 * @see {@link ManyToOneJoiningStrategyPane}
 *
 * @version 3.0
 * @since 2.1
 */
public class JoinColumnJoiningStrategyPane
	extends AbstractJoiningStrategyPane
		<JoinColumnEnabledRelationshipReference, JoinColumnJoiningStrategy>
{
	private final boolean includeOverrideCheckBox;
	
	
	public static JoinColumnJoiningStrategyPane buildJoinColumnJoiningStrategyPaneWithIncludeOverrideCheckBox(
		Pane<? extends JoinColumnEnabledRelationshipReference> parentPane, 
		Composite parent) {
		return new JoinColumnJoiningStrategyPane(parentPane, parent, true);
	}
	
	public static JoinColumnJoiningStrategyPane buildJoinColumnJoiningStrategyPaneWithoutIncludeOverrideCheckBox(
		Pane<? extends JoinColumnEnabledRelationshipReference> parentPane, 
		Composite parent) {
		return new JoinColumnJoiningStrategyPane(parentPane, parent, false);
	}
	
	public static JoinColumnJoiningStrategyPane buildJoinColumnJoiningStrategyPaneWithIncludeOverrideCheckBox(
		Pane<?> parentPane,
		PropertyValueModel<? extends JoinColumnEnabledRelationshipReference> subjectHolder,
        Composite parent) {
		return new JoinColumnJoiningStrategyPane(parentPane, subjectHolder, parent, true);
	}
	
	public static JoinColumnJoiningStrategyPane buildJoinColumnJoiningStrategyPaneWithoutIncludeOverrideCheckBox(
		Pane<?> parentPane,
		PropertyValueModel<? extends JoinColumnEnabledRelationshipReference> subjectHolder,
        Composite parent) {
		return new JoinColumnJoiningStrategyPane(parentPane, subjectHolder, parent, false);
	}
	
	
	private JoinColumnJoiningStrategyPane(
			Pane<? extends JoinColumnEnabledRelationshipReference> parentPane, 
			Composite parent,
	        boolean includeOverrideCheckBox) {
		super(parentPane, parent);
		this.includeOverrideCheckBox = includeOverrideCheckBox;
		initializeLayout2(getControl());
	}
	
	private JoinColumnJoiningStrategyPane(Pane<?> parentPane,
			PropertyValueModel<? extends JoinColumnEnabledRelationshipReference> subjectHolder,
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
	protected WritablePropertyValueModel<Boolean> buildUsesStrategyHolder() {
		return new PropertyAspectAdapter<JoinColumnEnabledRelationshipReference, Boolean>(
				this.getSubjectHolder(), RelationshipReference.PREDOMINANT_JOINING_STRATEGY_PROPERTY) {
			@Override
			protected Boolean buildValue() {
				return (this.subject == null) ? Boolean.FALSE :
					Boolean.valueOf(this.subject.usesJoinColumnJoiningStrategy());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					this.subject.setJoinColumnJoiningStrategy();
				}
				else {
					this.subject.unsetJoinColumnJoiningStrategy();
				}
			}
		};
	}
	
	@Override
	protected PropertyValueModel<JoinColumnJoiningStrategy> buildJoiningStrategyHolder() {
		return new PropertyAspectAdapter
				<JoinColumnEnabledRelationshipReference, JoinColumnJoiningStrategy>(
					getSubjectHolder()) {
			@Override
			protected JoinColumnJoiningStrategy buildValue_() {
				return this.subject.getJoinColumnJoiningStrategy();
			}
		};
	}
	
	@Override
	protected String getStrategyLabelKey() {
		return JptUiDetailsMessages.Joining_joinColumnJoiningLabel;
	}
	
	@Override
	protected Composite buildStrategyDetailsComposite(Composite parent) {
		if (this.includeOverrideCheckBox) {
			return new JoiningStrategyJoinColumnsWithOverrideOptionComposite(this, this.joiningStrategyHolder, parent).getControl();
		}
		return new JoiningStrategyJoinColumnsComposite(this, this.joiningStrategyHolder, parent).getControl();
	}
}
