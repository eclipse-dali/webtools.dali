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
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.Relationship;
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
 * @see {@link SpecifiedPrimaryKeyJoinColumnRelationship}
 * @see {@link SpecifiedPrimaryKeyJoinColumnRelationshipStrategy}
 * @see {@link OneToOneJoiningStrategyPane}
 *
 * @version 2.3
 * @since 2.1
 */
public class PrimaryKeyJoinColumnJoiningStrategyPane 
	extends AbstractJoiningStrategyPane
		<SpecifiedPrimaryKeyJoinColumnRelationship, SpecifiedPrimaryKeyJoinColumnRelationshipStrategy>
{
	public PrimaryKeyJoinColumnJoiningStrategyPane(
			Pane<? extends SpecifiedPrimaryKeyJoinColumnRelationship> parentPane, 
			Composite parent) {
		super(parentPane, parent);
	}

	@Override
	protected ModifiablePropertyValueModel<Boolean> buildUsesStrategyHolder() {
		return buildUsesPrimaryKeyJoinColumnJoiningStrategyHolder(getSubjectHolder());
	}

	protected PropertyValueModel<SpecifiedPrimaryKeyJoinColumnRelationshipStrategy> buildPrimaryKeyJoinColumnJoiningStrategyHolder() {
		return new PropertyAspectAdapter
				<SpecifiedPrimaryKeyJoinColumnRelationship, SpecifiedPrimaryKeyJoinColumnRelationshipStrategy>(
					getSubjectHolder()) {
			@Override
			protected SpecifiedPrimaryKeyJoinColumnRelationshipStrategy buildValue_() {
				return this.subject.getPrimaryKeyJoinColumnStrategy();
			}
		};
	}

	@Override
	protected Composite buildStrategyDetailsComposite(Composite parent) {
		return null;
	}

	public static ModifiablePropertyValueModel<Boolean> buildUsesPrimaryKeyJoinColumnJoiningStrategyHolder(PropertyValueModel<? extends SpecifiedPrimaryKeyJoinColumnRelationship> subjectHolder) {
		return new PropertyAspectAdapter<SpecifiedPrimaryKeyJoinColumnRelationship, Boolean>(
				subjectHolder, Relationship.STRATEGY_PROPERTY) {
			@Override
			protected Boolean buildValue() {
				return (this.subject == null) ? Boolean.FALSE :
					Boolean.valueOf(this.subject.strategyIsPrimaryKeyJoinColumn());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					this.subject.setStrategyToPrimaryKeyJoinColumn();
				}
				//value == FALSE - selection of another radio button causes this strategy to get unset
			}
		};
	}
}
