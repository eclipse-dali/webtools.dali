/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.PrimaryKeyJoinColumnRelationship;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
	protected WritablePropertyValueModel<Boolean> buildUsesStrategyHolder() {
		return buildUsesPrimaryKeyJoinColumnJoiningStrategyHolder(getSubjectHolder());
	}

	protected PropertyValueModel<PrimaryKeyJoinColumnRelationshipStrategy> buildPrimaryKeyJoinColumnJoiningStrategyHolder() {
		return new PropertyAspectAdapter
				<PrimaryKeyJoinColumnRelationship, PrimaryKeyJoinColumnRelationshipStrategy>(
					getSubjectHolder()) {
			@Override
			protected PrimaryKeyJoinColumnRelationshipStrategy buildValue_() {
				return this.subject.getPrimaryKeyJoinColumnJoiningStrategy();
			}
		};
	}

	@Override
	protected Composite buildStrategyDetailsComposite(Composite parent) {
		return null;
	}

	public static WritablePropertyValueModel<Boolean> buildUsesPrimaryKeyJoinColumnJoiningStrategyHolder(PropertyValueModel<? extends PrimaryKeyJoinColumnRelationship> subjectHolder) {
		return new PropertyAspectAdapter<PrimaryKeyJoinColumnRelationship, Boolean>(
				subjectHolder, ReadOnlyRelationship.PREDOMINANT_JOINING_STRATEGY_PROPERTY) {
			@Override
			protected Boolean buildValue() {
				return (this.subject == null) ? Boolean.FALSE :
					Boolean.valueOf(this.subject.usesPrimaryKeyJoinColumnJoiningStrategy());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					this.subject.setPrimaryKeyJoinColumnJoiningStrategy();
				}
				//value == FALSE - selection of another radio button causes this strategy to get unset
			}
		};
	}
}
