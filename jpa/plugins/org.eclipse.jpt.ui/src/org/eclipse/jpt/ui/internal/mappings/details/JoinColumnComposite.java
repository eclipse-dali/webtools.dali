/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.SingleRelationshipMapping;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Join Columns ---------------------------------------------------------- |
 * | |                                                                       | |
 * | | x Override Default                                                    | |
 * | |                                                                       | |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | AddRemoveListPane                                                 | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see SingleRelationshipMapping
 * @see JoinColumn
 * @see ManyToOneMappingComposite - A container of this pane
 * @see OneToOneMappingComposite - A container of this pane
 * @see JoinColumnInRelationshipMappingDialog
 *
 * @version 2.0
 * @since 2.0
 */
public class JoinColumnComposite extends AbstractFormPane<SingleRelationshipMapping>
{
	private WritablePropertyValueModel<JoinColumn> joinColumnHolder;

	/**
	 * Creates a new <code>JoinColumnComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public JoinColumnComposite(AbstractFormPane<? extends SingleRelationshipMapping> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>JoinColumnComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>ISingleRelationshipMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JoinColumnComposite(PropertyValueModel<? extends SingleRelationshipMapping> subjectHolder,
	                           Composite parent,
	                           WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private void addJoinColumn() {

		JoinColumnInRelationshipMappingDialog dialog =
			new JoinColumnInRelationshipMappingDialog(shell(), subject(), null);

		dialog.openDialog(buildAddJoinColumnPostExecution());
	}

	private void addJoinColumn(JoinColumnInRelationshipMappingStateObject stateObject) {

		SingleRelationshipMapping subject = subject();
		int index = subject.specifiedJoinColumnsSize();

		JoinColumn joinColumn = subject.addSpecifiedJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
	}

	private PostExecution<JoinColumnInRelationshipMappingDialog> buildAddJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInRelationshipMappingDialog>() {
			public void execute(JoinColumnInRelationshipMappingDialog dialog) {
				if (dialog.wasConfirmed()) {
					addJoinColumn(dialog.subject());
				}
			}
		};
	}

	private PropertyValueModel<JoinColumn> buildDefaultJoinColumnHolder() {
		return new PropertyAspectAdapter<SingleRelationshipMapping, JoinColumn>(getSubjectHolder(), SingleRelationshipMapping.DEFAULT_JOIN_COLUMN) {
			@Override
			protected JoinColumn buildValue_() {
				return subject.getDefaultJoinColumn();
			}
		};
	}

	private ListValueModel<JoinColumn> buildDefaultJoinColumnListHolder() {
		return new PropertyListValueModelAdapter<JoinColumn>(buildDefaultJoinColumnHolder());
	}

	private PostExecution<JoinColumnInRelationshipMappingDialog> buildEditJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInRelationshipMappingDialog>() {
			public void execute(JoinColumnInRelationshipMappingDialog dialog) {
				if (dialog.wasConfirmed()) {
					updateJoinColumn(dialog.subject());
				}
			}
		};
	}

	private WritablePropertyValueModel<JoinColumn> buildJoinColumnHolder() {
		return new SimplePropertyValueModel<JoinColumn>();
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

	private Adapter buildJoinColumnsAdapter() {
		return new AddRemovePane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addJoinColumn();
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
				editJoinColumn(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				removeJoinColumn(listSelectionModel);
			}
		};
	}

	private ListValueModel<JoinColumn> buildJoinColumnsListHolder() {
		java.util.List<ListValueModel<JoinColumn>> list = new ArrayList<ListValueModel<JoinColumn>>();
		list.add(buildSpecifiedJoinColumnsListHolder());
		list.add(buildDefaultJoinColumnListHolder());
		return new CompositeListValueModel<ListValueModel<JoinColumn>, JoinColumn>(list);
	}

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				JoinColumn joinColumn = (JoinColumn) element;
				return buildJoinColumnLabel(joinColumn);
			}
		};
	}

	private ListValueModel<JoinColumn> buildJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<JoinColumn>(buildJoinColumnsListHolder(),
			NamedColumn.SPECIFIED_NAME_PROPERTY,
			NamedColumn.DEFAULT_NAME_PROPERTY,
			AbstractJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			AbstractJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY);
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnHolder() {
		return new OverrideDefaultJoinColumnHolder();
	}

	private ListValueModel<JoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<SingleRelationshipMapping, JoinColumn>(getSubjectHolder(), SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterator<JoinColumn> listIterator_() {
				return subject.specifiedJoinColumns();
			}

			@Override
			protected int size_() {
				return subject.specifiedJoinColumnsSize();
			}
		};
	}

	private void editJoinColumn(ObjectListSelectionModel listSelectionModel) {

		JoinColumn joinColumn = (JoinColumn) listSelectionModel.selectedValue();

		JoinColumnInRelationshipMappingDialog dialog =
			new JoinColumnInRelationshipMappingDialog(shell(), subject(), joinColumn);

		dialog.openDialog(buildEditJoinColumnPostExecution());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		joinColumnHolder = buildJoinColumnHolder();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Join Columns group
		Group groupPane = buildTitledPane(
			container,
			JptUiMappingsMessages.JoinColumnComposite_joinColumn
		);

		// Override Default Join Columns check box
		buildCheckBox(
			buildSubPane(groupPane, 8),
			JptUiMappingsMessages.JoinColumnComposite_overrideDefaultJoinColumns,
			buildOverrideDefaultJoinColumnHolder()
		);

		// Join Columns list pane
		AddRemoveListPane<SingleRelationshipMapping> joinColumnsListPane =
			new AddRemoveListPane<SingleRelationshipMapping>(
				this,
				groupPane,
				buildJoinColumnsAdapter(),
				buildJoinColumnsListModel(),
				joinColumnHolder,
				buildJoinColumnsListLabelProvider(),
				JpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
			);

		installJoinColumnsListPaneEnabler(joinColumnsListPane);
	}

	private void installJoinColumnsListPaneEnabler(AddRemoveListPane<SingleRelationshipMapping> pane) {
		new PaneEnabler(
			buildOverrideDefaultJoinColumnHolder(),
			pane
		);
	}

	private void removeJoinColumn(ObjectListSelectionModel listSelectionModel) {

		int[] selectedIndices = listSelectionModel.selectedIndices();

		for (int index = selectedIndices.length; --index >= 0; ) {
			subject().removeSpecifiedJoinColumn(selectedIndices[index]);
		}
	}

	private void updateJoinColumn(JoinColumnInRelationshipMappingStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	private void updateJoinColumns(boolean selected) {

		if (isPopulating()) {
			return;
		}

		setPopulating(true);

		try {
			SingleRelationshipMapping subject = subject();

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

	private class OverrideDefaultJoinColumnHolder extends ListPropertyValueModelAdapter<Boolean>
	                                              implements WritablePropertyValueModel<Boolean> {

		public OverrideDefaultJoinColumnHolder() {
			super(buildSpecifiedJoinColumnsListHolder());
		}

		@Override
		protected Boolean buildValue() {
			return listHolder.size() > 0;
		}

		public void setValue(Boolean value) {
			updateJoinColumns(value);
		}
	}
}