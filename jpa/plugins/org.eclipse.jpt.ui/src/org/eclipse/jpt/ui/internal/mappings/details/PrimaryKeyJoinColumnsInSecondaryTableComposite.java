/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.ControlEnabler;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

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
 * @see ISecondaryTable
 * @see EntityComposite - The container of this pane
 * @see AddRemoveListPane
 *
 * @version 2.0
 * @since 1.0
 */
public class PrimaryKeyJoinColumnsInSecondaryTableComposite extends AbstractFormPane<ISecondaryTable>
{
	private WritablePropertyValueModel<IPrimaryKeyJoinColumn> joinColumnHolder;

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnsInSecondaryTableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public PrimaryKeyJoinColumnsInSecondaryTableComposite(AbstractFormPane<?> parentPane,
	                                                      PropertyValueModel<? extends ISecondaryTable> subjectHolder,
	                                                      Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnsInSecondaryTableComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>ISecondaryTable</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public PrimaryKeyJoinColumnsInSecondaryTableComposite(PropertyValueModel<? extends ISecondaryTable> subjectHolder,
	                                                      Composite parent,
	                                                      TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private void addJoinColumn(PrimaryKeyJoinColumnInSecondaryTableStateObject stateObject) {

		ISecondaryTable secondaryTable = stateObject.getOwner();
		int index = secondaryTable.specifiedPrimaryKeyJoinColumnsSize();

		IPrimaryKeyJoinColumn joinColumn = secondaryTable.addSpecifiedPrimaryKeyJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
	}

	private void addPrimaryKeyJoinColumn() {

		PrimaryKeyJoinColumnInSecondaryTableDialog dialog =
			new PrimaryKeyJoinColumnInSecondaryTableDialog(shell(), subject(), null);

		dialog.openDialog(buildAddPrimaryKeyJoinColumnPostExecution());
	}

	private PostExecution<PrimaryKeyJoinColumnInSecondaryTableDialog> buildAddPrimaryKeyJoinColumnPostExecution() {
		return new PostExecution<PrimaryKeyJoinColumnInSecondaryTableDialog>() {
			public void execute(PrimaryKeyJoinColumnInSecondaryTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					addJoinColumn(dialog.subject());
				}
			}
		};
	}

	private PropertyValueModel<Boolean> buildControlBooleanHolder() {
		return new TransformationPropertyValueModel<ISecondaryTable, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(ISecondaryTable value) {
				return (value != null);
			}
		};
	}

	private PropertyValueModel<IPrimaryKeyJoinColumn> buildDefaultJoinColumnHolder() {
		return new PropertyAspectAdapter<ISecondaryTable, IPrimaryKeyJoinColumn>(getSubjectHolder(), ISecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMN) {
			@Override
			protected IPrimaryKeyJoinColumn buildValue_() {
				return subject.getDefaultPrimaryKeyJoinColumn();
			}
		};
	}

	private ListValueModel<IPrimaryKeyJoinColumn> buildDefaultJoinColumnListHolder() {
		return new PropertyListValueModelAdapter<IPrimaryKeyJoinColumn>(
			buildDefaultJoinColumnHolder()
		);
	}

	private PostExecution<PrimaryKeyJoinColumnInSecondaryTableDialog> buildEditPrimaryKeyJoinColumnPostExecution() {
		return new PostExecution<PrimaryKeyJoinColumnInSecondaryTableDialog>() {
			public void execute(PrimaryKeyJoinColumnInSecondaryTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					editPrimaryKeyJoinColumn(dialog.subject());
				}
			}
		};
	}

	private String buildJoinColumnLabel(IPrimaryKeyJoinColumn joinColumn) {

		if (joinColumn.isVirtual()) {
			return NLS.bind(
				JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedName() == null) {
			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(
					JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsBothDefault,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}

			return NLS.bind(
				JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsFirstDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(
				JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsSecDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		return NLS.bind(
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParams,
			joinColumn.getName(),
			joinColumn.getReferencedColumnName()
		);
	}

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				IPrimaryKeyJoinColumn joinColumn = (IPrimaryKeyJoinColumn) element;
				return buildJoinColumnLabel(joinColumn);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultHolder() {
		return new OverrideDefaultJoinColumnHolder();
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnHolder() {
		return new OverrideDefaultJoinColumnHolder();
	}

	private AddRemovePane.Adapter buildPrimaryKeyJoinColumnAdapter() {
		return new AddRemovePane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addPrimaryKeyJoinColumn();
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_edit;
			}

			@Override
			public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
				editPrimaryKeyJoinColumn(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				removePrimaryKeyJoinColumn(listSelectionModel);
			}
		};
	}

	private WritablePropertyValueModel<IPrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnHolder() {
		return new SimplePropertyValueModel<IPrimaryKeyJoinColumn>();
	}

	private ListValueModel<IPrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListHolder() {
		List<ListValueModel<IPrimaryKeyJoinColumn>> list = new ArrayList<ListValueModel<IPrimaryKeyJoinColumn>>();
		list.add(buildSpecifiedJoinColumnsListHolder());
		list.add(buildDefaultJoinColumnListHolder());
		return new CompositeListValueModel<ListValueModel<IPrimaryKeyJoinColumn>, IPrimaryKeyJoinColumn>(list);
	}

	private ListValueModel<IPrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<IPrimaryKeyJoinColumn>(
			buildPrimaryKeyJoinColumnsListHolder(),
			INamedColumn.SPECIFIED_NAME_PROPERTY,
			INamedColumn.DEFAULT_NAME_PROPERTY,
			IAbstractJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			IAbstractJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY
		);
	}

	private ListValueModel<IPrimaryKeyJoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<ISecondaryTable, IPrimaryKeyJoinColumn>(getSubjectHolder(), ISecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterator<IPrimaryKeyJoinColumn> listIterator_() {
				return subject.specifiedPrimaryKeyJoinColumns();
			}

			@Override
			protected int size_() {
				return subject.specifiedPrimaryKeyJoinColumnsSize();
			}
		};
	}

	private void editPrimaryKeyJoinColumn(ObjectListSelectionModel listSelectionModel) {

		IPrimaryKeyJoinColumn joinColumn = (IPrimaryKeyJoinColumn) listSelectionModel.selectedValue();

		PrimaryKeyJoinColumnInSecondaryTableDialog dialog =
			new PrimaryKeyJoinColumnInSecondaryTableDialog(
				shell(),
				subject(),
				joinColumn
			);

		dialog.openDialog(buildEditPrimaryKeyJoinColumnPostExecution());
	}

	private void editPrimaryKeyJoinColumn(PrimaryKeyJoinColumnInSecondaryTableStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		joinColumnHolder = buildPrimaryKeyJoinColumnHolder();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Primary Key Join Columns group pane
		Group groupPane = buildTitledPane(
			container,
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_primaryKeyJoinColumn
		);

		// Override Default check box
		Button button = buildCheckBox(
			buildSubPane(groupPane, 8),
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_overrideDefaultPrimaryKeyJoinColumns,
			buildOverrideDefaultHolder()
		);

		installOverrideDefaultButtonEnabler(button);

		// Primary Key Join Columns list pane
		AddRemoveListPane<ISecondaryTable> joinColumnsPane = new AddRemoveListPane<ISecondaryTable>(
			this,
			groupPane,
			buildPrimaryKeyJoinColumnAdapter(),
			buildPrimaryKeyJoinColumnsListModel(),
			joinColumnHolder,
			buildJoinColumnsListLabelProvider(),
			IJpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);

		installPrimaryKeyJoinColumnListPaneEnabler(joinColumnsPane);
	}

	private void installOverrideDefaultButtonEnabler(Button overrideDefaultButton) {

		new ControlEnabler(
			buildControlBooleanHolder(),
			overrideDefaultButton
		);
	}

	private void installPrimaryKeyJoinColumnListPaneEnabler(AddRemoveListPane<ISecondaryTable> pkJoinColumnListPane) {

		new PaneEnabler(
			buildOverrideDefaultJoinColumnHolder(),
			pkJoinColumnListPane
		);
	}

	private void removePrimaryKeyJoinColumn(ObjectListSelectionModel listSelectionModel) {
		int[] selectedIndices = listSelectionModel.selectedIndices();

		for (int index = selectedIndices.length; --index >= 0; ) {
			subject().removeSpecifiedPrimaryKeyJoinColumn(selectedIndices[index]);
		}
	}

	private void updateJoinColumns(boolean selected) {

		if (isPopulating()) {
			return;
		}

		setPopulating(true);

		try {
			ISecondaryTable secondaryTable = subject();

			// Add a join column by creating a specified one using the default
			// one if it exists
			if (selected) {

				IPrimaryKeyJoinColumn defaultJoinColumn = secondaryTable.getDefaultPrimaryKeyJoinColumn();

				if (defaultJoinColumn != null) {
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					IPrimaryKeyJoinColumn pkJoinColumn = secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0);
					pkJoinColumn.setSpecifiedName(columnName);
					pkJoinColumn.setSpecifiedReferencedColumnName(referencedColumnName);

					joinColumnHolder.setValue(pkJoinColumn);
				}
			}
			else {
				for (int index = secondaryTable.specifiedPrimaryKeyJoinColumnsSize(); --index >= 0; ) {
					secondaryTable.removeSpecifiedPrimaryKeyJoinColumn(index);
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