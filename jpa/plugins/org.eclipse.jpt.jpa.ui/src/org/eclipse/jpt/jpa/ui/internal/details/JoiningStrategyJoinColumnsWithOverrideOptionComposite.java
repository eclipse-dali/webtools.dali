/*******************************************************************************
 * Copyright (c) 2005, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ListIterator;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterators.SuperListIteratorWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationshipStrategy;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:
 * <pre>
 * -------------------------------------------------------------------------
 * x Override Default                                                    
 * ---------------------------------------------------------------------
 * |                                                                   |
 * | JoiningStrategyJoinColumnsComposite                               |
 * |                                                                   |
 * ---------------------------------------------------------------------
 * -------------------------------------------------------------------------</pre>
 *
 * @see JoinColumnRelationship
 * @see JoinColumnRelationshipStrategy
 * @see JoinColumnJoiningStrategyPane
 * @see JoinColumnInJoiningStrategyDialog
 *
 * @version 3.0
 * @since 2.0
 */
public class JoiningStrategyJoinColumnsWithOverrideOptionComposite 
	extends Pane<ReadOnlyJoinColumnRelationshipStrategy>
{
	
	private JoiningStrategyJoinColumnsComposite joiningStrategyComposite;
	
	public JoiningStrategyJoinColumnsWithOverrideOptionComposite(
			Pane<?> parentPane,
			PropertyValueModel<ReadOnlyJoinColumnRelationshipStrategy> subjectHolder,
			Composite parent) {
		super(parentPane, subjectHolder, parent);
	}


	@Override
	protected void initializeLayout(Composite container) {
		// Override Default Join Columns check box
		addCheckBox(
			addSubPane(container, 8),
			JptUiDetailsMessages.JoiningStrategyJoinColumnsComposite_overrideDefaultJoinColumns,
			buildOverrideDefaultJoinColumnHolder(),
			null
		);
		
		this.joiningStrategyComposite = new JoiningStrategyJoinColumnsComposite(this, getSubjectHolder(), container);
	}

	void setSelectedJoinColumn(JoinColumn joinColumn) {
		this.joiningStrategyComposite.setSelectedJoinColumn(joinColumn);
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnHolder() {
		return new OverrideDefaultJoinColumnHolder();
	}
	
	ListValueModel<ReadOnlyJoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<ReadOnlyJoinColumnRelationshipStrategy, ReadOnlyJoinColumn>(
				getSubjectHolder(), ReadOnlyJoinColumnRelationshipStrategy.SPECIFIED_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterator<ReadOnlyJoinColumn> listIterator_() {
				return new SuperListIteratorWrapper<ReadOnlyJoinColumn>(this.subject.specifiedJoinColumns());
			}

			@Override
			protected int size_() {
				return this.subject.specifiedJoinColumnsSize();
			}
		};
	}
	
	private class OverrideDefaultJoinColumnHolder 
		extends ListPropertyValueModelAdapter<Boolean>
		implements WritablePropertyValueModel<Boolean> 
	{
		public OverrideDefaultJoinColumnHolder() {
			super(buildSpecifiedJoinColumnsListHolder());
		}
		
		@Override
		protected Boolean buildValue() {
			return Boolean.valueOf(this.listHolder.size() > 0);
		}
		
		public void setValue(Boolean value) {
			updateJoinColumns(value.booleanValue());
		}
		
		private void updateJoinColumns(boolean selected) {
			if (isPopulating()) {
				return;
			}
			
			setPopulating(true);
			
			try {
				JoinColumnRelationshipStrategy subject = (JoinColumnRelationshipStrategy) getSubject();
	
				// Add a join column by creating a specified one using the default
				// one if it exists
				if (selected) {
					ReadOnlyJoinColumn defaultJoinColumn = subject.getDefaultJoinColumn();//TODO could be null, disable override default check box?
					
					if (defaultJoinColumn != null) {
						String columnName = defaultJoinColumn.getDefaultName();
						String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();
						
						JoinColumn joinColumn = subject.addSpecifiedJoinColumn();
						joinColumn.setSpecifiedName(columnName);
						joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
						
						JoiningStrategyJoinColumnsWithOverrideOptionComposite.this.setSelectedJoinColumn(joinColumn);
					}
				}
				// Remove all the specified join columns
				else {
					for (int index = subject.specifiedJoinColumnsSize(); --index >= 0; ) {
						subject.removeSpecifiedJoinColumn(index);
					}
				}
			}
			finally {
				setPopulating(false);
			}
		}
	}
}
