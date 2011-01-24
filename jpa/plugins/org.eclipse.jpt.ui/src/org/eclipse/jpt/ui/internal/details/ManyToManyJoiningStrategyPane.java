/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.JoinTableRelationship;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToManyRelationship;
import org.eclipse.jpt.core.context.MappedByRelationship;
import org.eclipse.jpt.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:  
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Joining Strategy ------------------------------------------------------ |
 * | |                                                                       | |
 * | | o MappedByJoiningStrategyPane _______________________________________ | |
 * | | |                                                                   | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | | o JoinTableStrategyPane _____________________________________________ | |
 * | | |                                                                   | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link ManyToManyMapping}
 * @see {@link ManyToManyRelationship}
 * @see {@link ManyToManyMappingComposite}
 * @see {@link MappedByStrategyPane}
 * @see {@link JoinTableStrategyPane}
 *
 * @version 2.3
 * @since 2.1
 */
public class ManyToManyJoiningStrategyPane 
	extends Pane<ManyToManyRelationship>
{
	public ManyToManyJoiningStrategyPane(
			Pane<?> parentPane, 
			PropertyValueModel<? extends ManyToManyRelationship> subjectHolder, 
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		Composite composite = addCollapsibleSection(
				container,
				JptUiDetailsMessages.Joining_title,
				new SimplePropertyValueModel<Boolean>(Boolean.TRUE));
		
		addRadioButton(
			composite,
			JptUiDetailsMessages.Joining_mappedByLabel,
			MappedByJoiningStrategyPane.buildUsesMappedByJoiningStrategyHolder(getSubjectHolder()),
			null);

		new MappedByJoiningStrategyPane(this, composite);
		
		addRadioButton(
			composite,
			JptUiDetailsMessages.Joining_joinTableJoiningLabel,
			JoinTableJoiningStrategyPane.buildUsesJoinTableJoiningStrategyHolder(getSubjectHolder()),
			null);

		new JoinTableJoiningStrategyPane(this, composite);
		
		addSubPane(composite, 5);
	}

	protected WritablePropertyValueModel<Boolean> buildUsesMappedByStrategyHolder() {
		return new PropertyAspectAdapter<MappedByRelationship, Boolean>(
				this.getSubjectHolder(), ReadOnlyRelationship.PREDOMINANT_JOINING_STRATEGY_PROPERTY) {
			@Override
			protected Boolean buildValue() {
				return (this.subject == null) ? Boolean.FALSE :
					Boolean.valueOf(this.subject.usesMappedByJoiningStrategy());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					this.subject.setMappedByJoiningStrategy();
				}
				//value == FALSE - selection of another radio button causes this strategy to get unset
			}
		};
	}

	protected WritablePropertyValueModel<Boolean> buildUsesJoinTableStrategyHolder() {
		return new PropertyAspectAdapter<JoinTableRelationship, Boolean>(
				this.getSubjectHolder(), ReadOnlyRelationship.PREDOMINANT_JOINING_STRATEGY_PROPERTY) {
			@Override
			protected Boolean buildValue() {
				return (this.subject == null) ? Boolean.FALSE :
					Boolean.valueOf(this.subject.usesJoinTableJoiningStrategy());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					this.subject.setJoinTableJoiningStrategy();
				}
				//value == FALSE - selection of another radio button causes this strategy to get unset
			}
		};
	}
}
