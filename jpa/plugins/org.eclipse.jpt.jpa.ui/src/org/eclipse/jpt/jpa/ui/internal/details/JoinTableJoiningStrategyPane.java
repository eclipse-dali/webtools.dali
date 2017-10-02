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
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.JoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.JoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.SpecifiedOrVirtual;
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
 * @see SpecifiedJoinTableRelationship
 * @see SpecifiedJoinTableRelationshipStrategy
 * @see ManyToOneJoiningStrategyPane
 * @see ManyToManyJoiningStrategyPane
 *
 * @version 2.3
 * @since 2.1
 */
public class JoinTableJoiningStrategyPane
	extends AbstractJoiningStrategyPane<JoinTableRelationship>
{
	public JoinTableJoiningStrategyPane(
			Pane<? extends JoinTableRelationship> parentPane, 
			Composite parent) {
		super(parentPane, parent);
	}

	public JoinTableJoiningStrategyPane(Pane<?> parentPane,
		PropertyValueModel<? extends JoinTableRelationship> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected Control buildStrategyDetailsComposite(Composite parent) {
		JoinTableComposite joinTableComposite = new JoinTableComposite(this, buildJoinTableModel(), buildJoinTablePaneEnablerModel(), parent);
		return joinTableComposite.getControl();
	}

	@Override
	protected ModifiablePropertyValueModel<Boolean> buildUsesStrategyModel() {
		return buildUsesJoinTableJoiningStrategyModel(getSubjectHolder());
	}

	protected PropertyValueModel<JoinTableRelationshipStrategy> buildJoinTableJoiningStrategyModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> m.getJoinTableStrategy());
	}

	protected PropertyValueModel<JoinTable> buildJoinTableModel() {
		return PropertyValueModelTools.subjectModelAspectAdapter(
				this.buildJoinTableJoiningStrategyModel(),
				JoinTableRelationshipStrategy.JOIN_TABLE_PROPERTY,
				m -> m.getJoinTable()
			);
	}

	public static ModifiablePropertyValueModel<Boolean> buildUsesJoinTableJoiningStrategyModel(PropertyValueModel<? extends JoinTableRelationship> subjectHolder) {
		return PropertyValueModelTools.modifiableSubjectModelAspectAdapter(
				subjectHolder,
				JoinTableRelationship.STRATEGY_IS_JOIN_TABLE_PROPERTY,
				m -> Boolean.valueOf(m.strategyIsJoinTable()),
				(m, value) -> {
					if ((value != null) && value.booleanValue()) {
						((SpecifiedJoinTableRelationship) m).setStrategyToJoinTable();
					}
				}
			);
	}

	private PropertyValueModel<Boolean> buildJoinTablePaneEnablerModel() {
		return PropertyValueModelTools.valueAffirms(this.getSubjectHolder(), SpecifiedOrVirtual.SPECIFIED_PREDICATE);
	}
}
