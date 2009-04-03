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

import java.util.ArrayList;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.model.value.CachingTransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ReadOnlyWritablePropertyValueModelWrapper;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ValueListAdapter;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Join Columns ---------------------------------------------------------- |
 * | |                                                                       | |
 * | | x Override Default   (optional)                                       | |
 * | |                                                                       | |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | AddRemoveListPane                                                 | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see JoinColumnEnabledRelationshipReference
 * @see JoinColumnJoiningStrategy
 * @see JoinColumnJoiningStrategyPane
 * @see JoinColumnInJoiningStrategyDialog
 *
 * @version 2.0
 * @since 2.0
 */
public class JoinColumnComposite 
	extends FormPane<JoinColumnJoiningStrategy>
{
	private WritablePropertyValueModel<JoinColumn> joinColumnHolder;
	
	public JoinColumnComposite(
			FormPane<?> parentPane,
			PropertyValueModel<JoinColumnJoiningStrategy> subjectHolder,
			Composite parent) {
		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.joinColumnHolder = buildJoinColumnHolder();
	}
	
	private WritablePropertyValueModel<JoinColumn> buildJoinColumnHolder() {
		return new SimplePropertyValueModel<JoinColumn>();
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Override Default Join Columns check box
		addCheckBox(
			addSubPane(container, 8),
			JptUiMappingsMessages.JoinColumnComposite_overrideDefaultJoinColumns,
			buildOverrideDefaultJoinColumnHolder(),
			null
		);
		
		// Join Columns list pane
		AddRemoveListPane<JoinColumnJoiningStrategy> joinColumnsListPane =
			new AddRemoveListPane<JoinColumnJoiningStrategy>(
				this,
				container,
				buildJoinColumnsAdapter(),
				buildJoinColumnsListModel(),
				joinColumnHolder,
				buildJoinColumnsListLabelProvider(),
				JpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS,
				false
			);
		
		installJoinColumnsListPaneEnabler(joinColumnsListPane);
	}
	
	private WritablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnHolder() {
		return new OverrideDefaultJoinColumnHolder();
	}

	private Adapter buildJoinColumnsAdapter() {
		return new AddRemoveJoinColumnAdapter();
	}

	private ListValueModel<JoinColumn> buildJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<JoinColumn>(buildJoinColumnsListHolder(),
			NamedColumn.SPECIFIED_NAME_PROPERTY,
			NamedColumn.DEFAULT_NAME_PROPERTY,
			BaseJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			BaseJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY);
	}
	
	private ListValueModel<JoinColumn> buildJoinColumnsListHolder() {
		java.util.List<ListValueModel<JoinColumn>> list = new ArrayList<ListValueModel<JoinColumn>>();
		list.add(buildSpecifiedJoinColumnsListHolder());
		list.add(buildDefaultJoinColumnListHolder());
		return new CompositeListValueModel<ListValueModel<JoinColumn>, JoinColumn>(list);
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
	
	private ListValueModel<JoinColumn> buildDefaultJoinColumnListHolder() {
		return new PropertyListValueModelAdapter<JoinColumn>(
			new PropertyAspectAdapter<JoinColumnJoiningStrategy, JoinColumn>(
					getSubjectHolder(), JoinColumnJoiningStrategy.DEFAULT_JOIN_COLUMN_PROPERTY) {
				@Override
				protected JoinColumn buildValue_() {
					return this.subject.getDefaultJoinColumn();
				}
			});
	}

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new JoinColumnLabelProvider();
	}
	
	private void installJoinColumnsListPaneEnabler(AddRemoveListPane<JoinColumnJoiningStrategy> pane) {
		new PaneEnabler(new JoinColumnPaneEnablerHolder(), pane);
	}

	
	private class AddRemoveJoinColumnAdapter 
		extends AddRemovePane.AbstractAdapter 
	{
		public void addNewItem(ObjectListSelectionModel listSelectionModel) {
			JoinColumnInJoiningStrategyDialog dialog =
				new JoinColumnInJoiningStrategyDialog(getShell(), getSubject(), null);
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
		}

		@Override
		public boolean hasOptionalButton() {
			return true;
		}

		@Override
		public String optionalButtonText() {
			return JptUiMappingsMessages.JoinColumnComposite_edit;
		}

		@Override
		public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
			JoinColumn joinColumn = (JoinColumn) listSelectionModel.selectedValue();
			JoinColumnInJoiningStrategyDialog dialog =
				new JoinColumnInJoiningStrategyDialog(getShell(), getSubject(), joinColumn);
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
		
		public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
			removeJoinColumn(listSelectionModel);
		}
		
		private void removeJoinColumn(ObjectListSelectionModel listSelectionModel) {
			int[] selectedIndices = listSelectionModel.selectedIndices();
			
			for (int index = selectedIndices.length; --index >= 0; ) {
				getSubject().removeSpecifiedJoinColumn(selectedIndices[index]);
			}
		}
	}
	
	
	private class JoinColumnLabelProvider
		extends LabelProvider
	{
		@Override
		public String getText(Object element) {
			JoinColumn joinColumn = (JoinColumn) element;
			return buildJoinColumnLabel(joinColumn);
		}
		
		private String buildJoinColumnLabel(JoinColumn joinColumn) {
			if (joinColumn.isVirtual()) {
				return NLS.bind(
					JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParamsDefault,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}
			if (joinColumn.getSpecifiedName() == null) {
				if (joinColumn.getSpecifiedReferencedColumnName() == null) {
					return NLS.bind(
						JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParamsBothDefault,
						joinColumn.getName(),
						joinColumn.getReferencedColumnName()
					);
				}
	
				return NLS.bind(
					JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParamsFirstDefault,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}
			else if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(
					JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParamsSecDefault,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}
			else {
				return NLS.bind(
					JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParams,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}
		}
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
						
						joinColumnHolder.setValue(joinColumn);
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
					new ReadOnlyWritablePropertyValueModelWrapper(getSubjectHolder()), 
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
				return false;
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
