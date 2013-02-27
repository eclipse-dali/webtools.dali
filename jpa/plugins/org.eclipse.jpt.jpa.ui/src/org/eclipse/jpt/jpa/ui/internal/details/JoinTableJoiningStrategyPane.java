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
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.JoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Here is the layout of this pane:  
 * <pre>
 * -----------------------------------------------------------------------------
 * | o Join table ____________________________________________________________ |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | |  JoinTableComposite                                               | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link SpecifiedJoinTableRelationship}
 * @see {@link SpecifiedJoinTableRelationshipStrategy}
 * @see {@link ManyToOneJoiningStrategyPane}
 * @see {@link ManyToManyJoiningStrategyPane}
 *
 * @version 2.3
 * @since 2.1
 */
public class JoinTableJoiningStrategyPane
	extends AbstractJoiningStrategyPane
		<ReadOnlyJoinTableRelationship, JoinTableRelationshipStrategy>
{
	public JoinTableJoiningStrategyPane(
			Pane<? extends ReadOnlyJoinTableRelationship> parentPane, 
			Composite parent) {
		super(parentPane, parent);
	}

	public JoinTableJoiningStrategyPane(Pane<?> parentPane,
		PropertyValueModel<? extends ReadOnlyJoinTableRelationship> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected Control buildStrategyDetailsComposite(Composite parent) {
		JoinTableComposite joinTableComposite = new JoinTableComposite(this, buildJoinTableHolder(), buildJoinTablePaneEnablerHolder(), parent);
		return joinTableComposite.getControl();
	}

	@Override
	protected ModifiablePropertyValueModel<Boolean> buildUsesStrategyHolder() {
		return buildUsesJoinTableJoiningStrategyHolder(getSubjectHolder());
	}

	protected PropertyValueModel<JoinTableRelationshipStrategy> buildJoinTableJoiningStrategyHolder() {
		return new PropertyAspectAdapter
				<ReadOnlyJoinTableRelationship, JoinTableRelationshipStrategy>(
					getSubjectHolder()) {
			@Override
			protected JoinTableRelationshipStrategy buildValue_() {
				return this.subject.getJoinTableStrategy();
			}
		};
	}

	protected PropertyValueModel<ReadOnlyJoinTable> buildJoinTableHolder() {
		return new PropertyAspectAdapter<JoinTableRelationshipStrategy, ReadOnlyJoinTable>(
				this.buildJoinTableJoiningStrategyHolder(), JoinTableRelationshipStrategy.JOIN_TABLE_PROPERTY) {
			@Override
			protected ReadOnlyJoinTable buildValue_() {
				return this.subject.getJoinTable();
			}
		};
	}

	public static ModifiablePropertyValueModel<Boolean> buildUsesJoinTableJoiningStrategyHolder(PropertyValueModel<? extends ReadOnlyJoinTableRelationship> subjectHolder) {
		return new PropertyAspectAdapter<ReadOnlyJoinTableRelationship, Boolean>(
			subjectHolder, Relationship.STRATEGY_PROPERTY) {
			@Override
			protected Boolean buildValue() {
				return Boolean.valueOf(this.buildBooleanValue());
			}
			
			protected boolean buildBooleanValue() {
				return (this.subject != null) && this.subject.strategyIsJoinTable();
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					((SpecifiedJoinTableRelationship) this.subject).setStrategyToJoinTable();
				}
				//value == FALSE - selection of another radio button causes this strategy to get unset
			}
		};
	}

	private TransformationPropertyValueModel<ReadOnlyJoinTableRelationship, Boolean> buildJoinTablePaneEnablerHolder() {
		return new TransformationPropertyValueModel<ReadOnlyJoinTableRelationship, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform_(ReadOnlyJoinTableRelationship v) {
				return Boolean.valueOf(!v.isVirtual());
			}
		};
	}
}
