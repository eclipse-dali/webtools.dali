/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.JoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.swt.widgets.Composite;

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
 * @see {@link JoinTableRelationship}
 * @see {@link JoinTableRelationshipStrategy}
 * @see {@link ManyToOneJoiningStrategyPane}
 * @see {@link ManyToManyJoiningStrategyPane}
 *
 * @version 2.3
 * @since 2.1
 */
public class JoinTableJoiningStrategyPane
	extends AbstractJoiningStrategyPane
		<ReadOnlyJoinTableRelationship, ReadOnlyJoinTableRelationshipStrategy>
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
	protected Composite buildStrategyDetailsComposite(Composite parent) {
		return new JoinTableComposite(this, buildJoinTableHolder(), parent).getControl();
	}

	@Override
	protected WritablePropertyValueModel<Boolean> buildUsesStrategyHolder() {
		return buildUsesJoinTableJoiningStrategyHolder(getSubjectHolder());
	}

	protected PropertyValueModel<ReadOnlyJoinTableRelationshipStrategy> buildJoinTableJoiningStrategyHolder() {
		return new PropertyAspectAdapter
				<ReadOnlyJoinTableRelationship, ReadOnlyJoinTableRelationshipStrategy>(
					getSubjectHolder()) {
			@Override
			protected ReadOnlyJoinTableRelationshipStrategy buildValue_() {
				return this.subject.getJoinTableStrategy();
			}
		};
	}

	protected PropertyValueModel<ReadOnlyJoinTable> buildJoinTableHolder() {
		return new PropertyAspectAdapter<ReadOnlyJoinTableRelationshipStrategy, ReadOnlyJoinTable>(
				this.buildJoinTableJoiningStrategyHolder(), ReadOnlyJoinTableRelationshipStrategy.JOIN_TABLE_PROPERTY) {
			@Override
			protected ReadOnlyJoinTable buildValue_() {
				return this.subject.getJoinTable();
			}
		};
	}

	public static WritablePropertyValueModel<Boolean> buildUsesJoinTableJoiningStrategyHolder(PropertyValueModel<? extends ReadOnlyJoinTableRelationship> subjectHolder) {
		return new PropertyAspectAdapter<ReadOnlyJoinTableRelationship, Boolean>(
			subjectHolder, ReadOnlyRelationship.STRATEGY_PROPERTY) {
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
					((JoinTableRelationship) this.subject).setStrategyToJoinTable();
				}
				//value == FALSE - selection of another radio button causes this strategy to get unset
			}
		};
	}
}
