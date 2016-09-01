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
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumnRelationshipStrategy;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:  
 * <pre>
 * -----------------------------------------------------------------------------
 * | o Primary key join columns ______________________________________________ |
 * | |     (no actual content)                                               | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.3
 * @since 2.1
 */
public class PrimaryKeyJoinColumnJoiningStrategyPane 
	extends AbstractJoiningStrategyPane<PrimaryKeyJoinColumnRelationship>
{
	public PrimaryKeyJoinColumnJoiningStrategyPane(
			Pane<? extends PrimaryKeyJoinColumnRelationship> parentPane, 
			Composite parent) {
		super(parentPane, parent);
	}

	@Override
	protected ModifiablePropertyValueModel<Boolean> buildUsesStrategyModel() {
		return buildUsesPrimaryKeyJoinColumnJoiningStrategyModel(getSubjectHolder());
	}

	protected PropertyValueModel<SpecifiedPrimaryKeyJoinColumnRelationshipStrategy> buildPrimaryKeyJoinColumnJoiningStrategyModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> m.getPrimaryKeyJoinColumnStrategy());
	}

	@Override
	protected Composite buildStrategyDetailsComposite(Composite parent) {
		return null;
	}

	public static ModifiablePropertyValueModel<Boolean> buildUsesPrimaryKeyJoinColumnJoiningStrategyModel(PropertyValueModel<? extends PrimaryKeyJoinColumnRelationship> subjectHolder) {
		return PropertyValueModelTools.modifiableSubjectModelAspectAdapter_(
				subjectHolder,
				Relationship.STRATEGY_PROPERTY,
				m -> Boolean.valueOf((m != null) && m.strategyIsPrimaryKeyJoinColumn()),
				(m, value) -> {
					if ((m != null) && (value != null) && value.booleanValue()) {
						m.setStrategyToPrimaryKeyJoinColumn();
					}
				}
			);
	}
}
