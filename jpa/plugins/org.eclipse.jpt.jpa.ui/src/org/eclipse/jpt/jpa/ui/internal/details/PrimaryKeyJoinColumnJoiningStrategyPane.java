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
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
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
 * @see {@link PrimaryKeyJoinColumnRelationship}
 * @see {@link PrimaryKeyJoinColumnRelationshipStrategy}
 * @see {@link OneToOneJoiningStrategyPane}
 *
 * @version 2.3
 * @since 2.1
 */
public class PrimaryKeyJoinColumnJoiningStrategyPane 
	extends AbstractJoiningStrategyPane
		<PrimaryKeyJoinColumnRelationship, PrimaryKeyJoinColumnRelationshipStrategy>
{
	public PrimaryKeyJoinColumnJoiningStrategyPane(
			Pane<? extends PrimaryKeyJoinColumnRelationship> parentPane, 
			Composite parent) {
		super(parentPane, parent);
	}

	@Override
	protected ModifiablePropertyValueModel<Boolean> buildUsesStrategyHolder() {
		return buildUsesPrimaryKeyJoinColumnJoiningStrategyHolder(getSubjectHolder());
	}

	protected PropertyValueModel<PrimaryKeyJoinColumnRelationshipStrategy> buildPrimaryKeyJoinColumnJoiningStrategyHolder() {
		return new PropertyAspectAdapter
				<PrimaryKeyJoinColumnRelationship, PrimaryKeyJoinColumnRelationshipStrategy>(
					getSubjectHolder()) {
			@Override
			protected PrimaryKeyJoinColumnRelationshipStrategy buildValue_() {
				return this.subject.getPrimaryKeyJoinColumnStrategy();
			}
		};
	}

	@Override
	protected Composite buildStrategyDetailsComposite(Composite parent) {
		return null;
	}

	public static ModifiablePropertyValueModel<Boolean> buildUsesPrimaryKeyJoinColumnJoiningStrategyHolder(PropertyValueModel<? extends PrimaryKeyJoinColumnRelationship> subjectHolder) {
		return new PropertyAspectAdapter<PrimaryKeyJoinColumnRelationship, Boolean>(
				subjectHolder, ReadOnlyRelationship.STRATEGY_PROPERTY) {
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