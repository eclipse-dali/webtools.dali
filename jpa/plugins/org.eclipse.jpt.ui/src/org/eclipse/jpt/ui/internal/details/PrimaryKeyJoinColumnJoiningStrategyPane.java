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

import org.eclipse.jpt.core.context.PrimaryKeyJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumnJoiningStrategy;
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
 * | o Primary key join columns ______________________________________________ |
 * | |     (no actual content)                                               | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link PrimaryKeyJoinColumnEnabledRelationshipReference}
 * @see {@link PrimaryKeyJoinColumnJoiningStrategy}
 * @see {@link OneToOneJoiningStrategyPane}
 *
 * @version 2.3
 * @since 2.1
 */
public class PrimaryKeyJoinColumnJoiningStrategyPane 
	extends AbstractJoiningStrategyPane
		<PrimaryKeyJoinColumnEnabledRelationshipReference, PrimaryKeyJoinColumnJoiningStrategy>
{
	public PrimaryKeyJoinColumnJoiningStrategyPane(
			Pane<? extends PrimaryKeyJoinColumnEnabledRelationshipReference> parentPane, 
			Composite parent) {
		super(parentPane, parent);
	}

	@Override
	protected WritablePropertyValueModel<Boolean> buildUsesStrategyHolder() {
		return buildUsesPrimaryKeyJoinColumnJoiningStrategyHolder(getSubjectHolder());
	}

	protected PropertyValueModel<PrimaryKeyJoinColumnJoiningStrategy> buildPrimaryKeyJoinColumnJoiningStrategyHolder() {
		return new PropertyAspectAdapter
				<PrimaryKeyJoinColumnEnabledRelationshipReference, PrimaryKeyJoinColumnJoiningStrategy>(
					getSubjectHolder()) {
			@Override
			protected PrimaryKeyJoinColumnJoiningStrategy buildValue_() {
				return this.subject.getPrimaryKeyJoinColumnJoiningStrategy();
			}
		};
	}

	@Override
	protected Composite buildStrategyDetailsComposite(Composite parent) {
		return null;
	}

	public static WritablePropertyValueModel<Boolean> buildUsesPrimaryKeyJoinColumnJoiningStrategyHolder(PropertyValueModel<? extends PrimaryKeyJoinColumnEnabledRelationshipReference> subjectHolder) {
		return new PropertyAspectAdapter<PrimaryKeyJoinColumnEnabledRelationshipReference, Boolean>(
				subjectHolder, RelationshipReference.PREDOMINANT_JOINING_STRATEGY_PROPERTY) {
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
				else {
					this.subject.unsetPrimaryKeyJoinColumnJoiningStrategy();
				}
			}
		};
	}
}
