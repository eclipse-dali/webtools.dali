/*******************************************************************************
 * Copyright (c) 2005, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.closure.BooleanClosure;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;

public class JoiningStrategyJoinColumnsWithOverrideOptionComposite 
	extends Pane<JoinColumnRelationshipStrategy>
{
	
	private JoiningStrategyJoinColumnsComposite joiningStrategyComposite;
	
	public JoiningStrategyJoinColumnsWithOverrideOptionComposite(
			Pane<?> parentPane,
			PropertyValueModel<JoinColumnRelationshipStrategy> subjectHolder,
			Composite parent) {
		super(parentPane, subjectHolder, parent);
	}


	@Override
	protected void initializeLayout(Composite container) {
		// Override Default Join Columns check box
		addCheckBox(
			container,
			JptJpaUiDetailsMessages.JOINING_STRATEGY_JOIN_COLUMNS_COMPOSITE_OVERRIDE_DEFAULT_JOIN_COLUMNS,
			buildOverrideDefaultJoinColumnModel(),
			null
		);
		
		this.joiningStrategyComposite = new JoiningStrategyJoinColumnsComposite(this, container);
	}

	void setSelectedJoinColumn(SpecifiedJoinColumn joinColumn) {
		this.joiningStrategyComposite.setSelectedJoinColumn(joinColumn);
	}

	private ModifiablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnModel() {
		return ListValueModelTools.isNotEmptyModifiablePropertyValueModel(this.buildSpecifiedJoinColumnsListModel(), new OverrideDefaultJoinColumnModelSetValueClosure());
	}
	
	ListValueModel<JoinColumn> buildSpecifiedJoinColumnsListModel() {
		return new ListAspectAdapter<JoinColumnRelationshipStrategy, JoinColumn>(
				getSubjectHolder(), JoinColumnRelationshipStrategy.SPECIFIED_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterable<JoinColumn> getListIterable() {
				return new SuperListIterableWrapper<JoinColumn>(this.subject.getSpecifiedJoinColumns());
			}

			@Override
			protected int size_() {
				return this.subject.getSpecifiedJoinColumnsSize();
			}
		};
	}
	
	class OverrideDefaultJoinColumnModelSetValueClosure
		implements BooleanClosure.Adapter
	{
		public void execute(boolean value) {
			updateJoinColumns(value);
		}
	}

	void updateJoinColumns(boolean selected) {
		if (isPopulating()) {
			return;
		}
		
		setPopulating(true);
		
		try {
			SpecifiedJoinColumnRelationshipStrategy subject = (SpecifiedJoinColumnRelationshipStrategy) getSubject();
			if (selected) {
				if (subject.getDefaultJoinColumn() != null) {//TODO can this be null, disable override default check box? or have it checked if there are not default join columns?
					subject.convertDefaultJoinColumnsToSpecified();
					JoiningStrategyJoinColumnsWithOverrideOptionComposite.this.setSelectedJoinColumn(subject.getSpecifiedJoinColumn(0));
				}
			}
			else {
				subject.clearSpecifiedJoinColumns();
			}
		}
		finally {
			setPopulating(false);
		}
	}
}
