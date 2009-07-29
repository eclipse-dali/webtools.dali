/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnsComposite.JoinColumnsEditor;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.model.value.CachingTransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ReadOnlyWritablePropertyValueModelWrapper;
import org.eclipse.jpt.utility.internal.model.value.ValueListAdapter;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:
 * <pre>
 * -------------------------------------------------------------------------
 * x Override Default                                                    
 * ---------------------------------------------------------------------
 * |                                                                   |
 * | JoinColumnsComposite                                              |
 * |                                                                   |
 * ---------------------------------------------------------------------
 * -------------------------------------------------------------------------</pre>
 *
 * @see JoinColumnEnabledRelationshipReference
 * @see JoinColumnJoiningStrategy
 * @see JoinColumnJoiningStrategyPane
 * @see JoinColumnInJoiningStrategyDialog
 *
 * @version 3.0
 * @since 2.0
 */
public class JoiningStrategyJoinColumnsComposite 
	extends FormPane<JoinColumnJoiningStrategy>
{
	
	private JoinColumnsComposite<JoinColumnJoiningStrategy> joinColumnsComposite;
	
	public JoiningStrategyJoinColumnsComposite(
			FormPane<?> parentPane,
			PropertyValueModel<JoinColumnJoiningStrategy> subjectHolder,
			Composite parent) {
		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Override Default Join Columns check box
		addCheckBox(
			addSubPane(container, 8),
			JptUiMappingsMessages.JoiningStrategyJoinColumnsComposite_overrideDefaultJoinColumns,
			buildOverrideDefaultJoinColumnHolder(),
			null
		);
		
		this.joinColumnsComposite = new JoinColumnsComposite<JoinColumnJoiningStrategy>(this, container, buildJoinColumnsProvider());
		
		this.joinColumnsComposite.installJoinColumnsPaneEnabler(new JoinColumnPaneEnablerHolder());
	}
	
	private JoinColumnsEditor<JoinColumnJoiningStrategy> buildJoinColumnsProvider() {
		return new JoinColumnsEditor<JoinColumnJoiningStrategy>() {

			public void addJoinColumn(JoinColumnJoiningStrategy subject) {
				JoiningStrategyJoinColumnsComposite.this.addJoinColumn(subject);
			}

			public boolean hasSpecifiedJoinColumns(JoinColumnJoiningStrategy subject) {
				return subject.hasSpecifiedJoinColumns();
			}

			public void editJoinColumn(JoinColumnJoiningStrategy subject, JoinColumn joinColumn) {
				JoiningStrategyJoinColumnsComposite.this.editJoinColumn(subject, joinColumn);
			}

			public JoinColumn getDefaultJoinColumn(JoinColumnJoiningStrategy subject) {
				return subject.getDefaultJoinColumn();
			}

			public String getDefaultPropertyName() {
				return JoinColumnJoiningStrategy.DEFAULT_JOIN_COLUMN_PROPERTY;
			}

			public String getSpecifiedJoinColumnsListPropertyName() {
				return JoinColumnJoiningStrategy.SPECIFIED_JOIN_COLUMNS_LIST;
			}

			public void removeJoinColumns(JoinColumnJoiningStrategy subject, int[] selectedIndices) {
				for (int index = selectedIndices.length; --index >= 0; ) {
					subject.removeSpecifiedJoinColumn(selectedIndices[index]);
				}				
			}

			public ListIterator<JoinColumn> specifiedJoinColumns(JoinColumnJoiningStrategy subject) {
				return subject.specifiedJoinColumns();
			}

			public int specifiedJoinColumnsSize(JoinColumnJoiningStrategy subject) {
				return subject.specifiedJoinColumnsSize();
			}
		};
	}
	
	private void addJoinColumn(JoinColumnJoiningStrategy joiningStrategy) {
		JoinColumnInJoiningStrategyDialog dialog =
			new JoinColumnInJoiningStrategyDialog(getShell(), joiningStrategy, null);
		dialog.openDialog(buildAddJoinColumnPostExecution());
	}
	
	private PostExecution<JoinColumnInJoiningStrategyDialog> buildAddJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoiningStrategyDialog>() {
			public void execute(JoinColumnInJoiningStrategyDialog dialog) {
				if (dialog.wasConfirmed()) {
					addJoinColumn(dialog.getSubject());
				}
			}
		};
	}
	
	private void addJoinColumn(JoinColumnInJoiningStrategyStateObject stateObject) {
		JoinColumnJoiningStrategy subject = getSubject();
		int index = subject.specifiedJoinColumnsSize();
		
		JoinColumn joinColumn = subject.addSpecifiedJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
		this.setSelectedJoinColumn(joinColumn);
	}

	private void setSelectedJoinColumn(JoinColumn joinColumn) {
		this.joinColumnsComposite.setSelectedJoinColumn(joinColumn);
	}
	
	private void editJoinColumn(JoinColumnJoiningStrategy joiningStrategy, JoinColumn joinColumn) {
		JoinColumnInJoiningStrategyDialog dialog =
			new JoinColumnInJoiningStrategyDialog(getShell(), joiningStrategy, joinColumn);
		dialog.openDialog(buildEditJoinColumnPostExecution());
	}
	
	private PostExecution<JoinColumnInJoiningStrategyDialog> buildEditJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoiningStrategyDialog>() {
			public void execute(JoinColumnInJoiningStrategyDialog dialog) {
				if (dialog.wasConfirmed()) {
					updateJoinColumn(dialog.getSubject());
				}
			}
		};
	}
	
	private void updateJoinColumn(JoinColumnInJoiningStrategyStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnHolder() {
		return new OverrideDefaultJoinColumnHolder();
	}
	
	private ListValueModel<JoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<JoinColumnJoiningStrategy, JoinColumn>(
				getSubjectHolder(), JoinColumnJoiningStrategy.SPECIFIED_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterator<JoinColumn> listIterator_() {
				return this.subject.specifiedJoinColumns();
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
				JoinColumnJoiningStrategy subject = getSubject();
	
				// Add a join column by creating a specified one using the default
				// one if it exists
				if (selected) {
					JoinColumn defaultJoinColumn = subject.getDefaultJoinColumn();//TODO could be null, disable override default check box?
					
					if (defaultJoinColumn != null) {
						String columnName = defaultJoinColumn.getDefaultName();
						String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();
						
						JoinColumn joinColumn = subject.addSpecifiedJoinColumn(0);
						joinColumn.setSpecifiedName(columnName);
						joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
						
						JoiningStrategyJoinColumnsComposite.this.setSelectedJoinColumn(joinColumn);
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
	
	
	private class JoinColumnPaneEnablerHolder 
		extends CachingTransformationPropertyValueModel<JoinColumnJoiningStrategy, Boolean>
	{
		private StateChangeListener stateChangeListener;
		
		
		public JoinColumnPaneEnablerHolder() {
			super(
				new ValueListAdapter<JoinColumnJoiningStrategy>(
					new ReadOnlyWritablePropertyValueModelWrapper<JoinColumnJoiningStrategy>(getSubjectHolder()), 
					JoinColumnJoiningStrategy.SPECIFIED_JOIN_COLUMNS_LIST));
			this.stateChangeListener = buildStateChangeListener();
		}
		
		
		private StateChangeListener buildStateChangeListener() {
			return new StateChangeListener() {
				public void stateChanged(StateChangeEvent event) {
					valueStateChanged(event);
				}
			};
		}
		
		private void valueStateChanged(StateChangeEvent event) {
			Object oldValue = this.cachedValue;
			Object newValue = transformNew(this.valueHolder.getValue());
			firePropertyChanged(VALUE, oldValue, newValue);
		}
		
		@Override
		protected Boolean transform(JoinColumnJoiningStrategy value) {
			if (value == null) {
				return Boolean.FALSE;
			}
			return super.transform(value);
		}
		
		@Override
		protected Boolean transform_(JoinColumnJoiningStrategy value) {
			boolean virtual = value.getRelationshipReference().getRelationshipMapping().getPersistentAttribute().isVirtual();
			return Boolean.valueOf(! virtual && value.specifiedJoinColumnsSize() > 0);
		}
		
		@Override
		protected void engageValueHolder() {
			super.engageValueHolder();
			this.valueHolder.addStateChangeListener(this.stateChangeListener);
		}
		
		@Override
		protected void disengageValueHolder() {
			this.valueHolder.removeStateChangeListener(this.stateChangeListener);
			super.disengageValueHolder();
		}
	}
}
