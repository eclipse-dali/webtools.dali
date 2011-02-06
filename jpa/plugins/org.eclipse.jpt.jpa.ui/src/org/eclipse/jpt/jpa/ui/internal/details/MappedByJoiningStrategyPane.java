/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.MappedByRelationship;
import org.eclipse.jpt.jpa.core.context.MappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:  
 * <pre>
 * -----------------------------------------------------------------------------
 * | o Mapped by _____________________________________________________________ |
 * | |             ---------------------------------------------  ---------- | |
 * | |  Attribute: |                                           |  |Browse..| | |
 * | |             ---------------------------------------------  ---------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link MappedByRelationship}
 * @see {@link MappedByRelationshipStrategy}
 * @see {@link OneToOneJoiningStrategyPane}
 * @see {@link OneToManyJoiningStrategyPane}
 * @see {@link ManyToManyJoiningStrategyPane}
 *
 * @version 2.3
 * @since 2.1
 */
public class MappedByJoiningStrategyPane 
	extends AbstractJoiningStrategyPane<MappedByRelationship, MappedByRelationshipStrategy>
{
	/**
	 * Creates a new <code>MappedByJoiningStrategyPane</code>.
	 *
	 * @param parentPane The parent form pane
	 * @param parent The parent container
	 */
	public MappedByJoiningStrategyPane(
			Pane<? extends MappedByRelationship> parentPane, 
			Composite parent) {
		super(parentPane, parent);
	}

	@Override
	protected Composite buildStrategyDetailsComposite(Composite parent) {
		return new MappedByPane(this, this.buildMappedByJoiningStrategyHolder(), parent).getControl();
	}	

	@Override
	protected WritablePropertyValueModel<Boolean> buildUsesStrategyHolder() {
		return buildUsesMappedByJoiningStrategyHolder(getSubjectHolder());
	}

	protected PropertyValueModel<MappedByRelationshipStrategy> buildMappedByJoiningStrategyHolder() {
		return new PropertyAspectAdapter<MappedByRelationship, MappedByRelationshipStrategy>(
				getSubjectHolder()) {
			@Override
			protected MappedByRelationshipStrategy buildValue_() {
				return this.subject.getMappedByStrategy();
			}
		};
	}

	public static WritablePropertyValueModel<Boolean> buildUsesMappedByJoiningStrategyHolder(PropertyValueModel<? extends MappedByRelationship> subjectHolder) {
		return new PropertyAspectAdapter<MappedByRelationship, Boolean>(
				subjectHolder, ReadOnlyRelationship.STRATEGY_PROPERTY) {
			@Override
			protected Boolean buildValue() {
				return (this.subject == null) ? Boolean.FALSE :
					Boolean.valueOf(this.subject.strategyIsMappedBy());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					this.subject.setStrategyToMappedBy();
				}
				//value == FALSE - selection of another radio button causes this strategy to get unset
			}
		};
	}
}
