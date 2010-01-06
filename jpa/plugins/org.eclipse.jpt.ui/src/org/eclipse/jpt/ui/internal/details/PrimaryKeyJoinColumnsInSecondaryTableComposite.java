/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Button;
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
 * @see SecondaryTable
 * @see EntityComposite - The container of this pane
 * @see AddRemoveListPane
 *
 * @version 2.0
 * @since 1.0
 */
public class PrimaryKeyJoinColumnsInSecondaryTableComposite extends Pane<SecondaryTable>
{
	private WritablePropertyValueModel<PrimaryKeyJoinColumn> joinColumnHolder;

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnsInSecondaryTableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public PrimaryKeyJoinColumnsInSecondaryTableComposite(Pane<?> parentPane,
	                                                      PropertyValueModel<? extends SecondaryTable> subjectHolder,
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
	public PrimaryKeyJoinColumnsInSecondaryTableComposite(PropertyValueModel<? extends SecondaryTable> subjectHolder,
	                                                      Composite parent,
	                                                      WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private void addJoinColumn(PrimaryKeyJoinColumnInSecondaryTableStateObject stateObject) {

		SecondaryTable secondaryTable = stateObject.getOwner();
		int index = secondaryTable.specifiedPrimaryKeyJoinColumnsSize();

		PrimaryKeyJoinColumn joinColumn = secondaryTable.addSpecifiedPrimaryKeyJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
	}

	private void addPrimaryKeyJoinColumn() {

		PrimaryKeyJoinColumnInSecondaryTableDialog dialog =
			new PrimaryKeyJoinColumnInSecondaryTableDialog(getShell(), getSubject(), null);

		dialog.openDialog(buildAddPrimaryKeyJoinColumnPostExecution());
	}

	private PostExecution<PrimaryKeyJoinColumnInSecondaryTableDialog> buildAddPrimaryKeyJoinColumnPostExecution() {
		return new PostExecution<PrimaryKeyJoinColumnInSecondaryTableDialog>() {
			public void execute(PrimaryKeyJoinColumnInSecondaryTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					addJoinColumn(dialog.getSubject());
				}
			}
		};
	}

	private PropertyValueModel<Boolean> buildControlBooleanHolder() {
		return new TransformationPropertyValueModel<SecondaryTable, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(SecondaryTable value) {
				if (value == null) {
					return Boolean.FALSE;
				}
				return Boolean.valueOf(!value.isVirtual());
			}
		};
	}

	private PropertyValueModel<PrimaryKeyJoinColumn> buildDefaultJoinColumnHolder() {
		return new PropertyAspectAdapter<SecondaryTable, PrimaryKeyJoinColumn>(getSubjectHolder(), SecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMN) {
			@Override
			protected PrimaryKeyJoinColumn buildValue_() {
				return subject.getDefaultPrimaryKeyJoinColumn();
			}
		};
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildDefaultJoinColumnListHolder() {
		return new PropertyListValueModelAdapter<PrimaryKeyJoinColumn>(
			buildDefaultJoinColumnHolder()
		);
	}

	private PostExecution<PrimaryKeyJoinColumnInSecondaryTableDialog> buildEditPrimaryKeyJoinColumnPostExecution() {
		return new PostExecution<PrimaryKeyJoinColumnInSecondaryTableDialog>() {
			public void execute(PrimaryKeyJoinColumnInSecondaryTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					editPrimaryKeyJoinColumn(dialog.getSubject());
				}
			}
		};
	}

	private String buildJoinColumnLabel(PrimaryKeyJoinColumn joinColumn) {

		if (joinColumn.isVirtual()) {
			return NLS.bind(
				JptUiDetailsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedName() == null) {
			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(
					JptUiDetailsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsBothDefault,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}

			return NLS.bind(
				JptUiDetailsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsFirstDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(
				JptUiDetailsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsSecDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		return NLS.bind(
			JptUiDetailsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParams,
			joinColumn.getName(),
			joinColumn.getReferencedColumnName()
		);
	}

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				PrimaryKeyJoinColumn joinColumn = (PrimaryKeyJoinColumn) element;
				return buildJoinColumnLabel(joinColumn);
			}
		};
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
				return JptUiDetailsMessages.PrimaryKeyJoinColumnsComposite_edit;
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

	private WritablePropertyValueModel<PrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnHolder() {
		return new SimplePropertyValueModel<PrimaryKeyJoinColumn>();
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListHolder() {
		List<ListValueModel<PrimaryKeyJoinColumn>> list = new ArrayList<ListValueModel<PrimaryKeyJoinColumn>>();
		list.add(buildSpecifiedJoinColumnsListHolder());
		list.add(buildDefaultJoinColumnListHolder());
		return new CompositeListValueModel<ListValueModel<PrimaryKeyJoinColumn>, PrimaryKeyJoinColumn>(list);
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<PrimaryKeyJoinColumn>(
			buildPrimaryKeyJoinColumnsListHolder(),
			NamedColumn.SPECIFIED_NAME_PROPERTY,
			NamedColumn.DEFAULT_NAME_PROPERTY,
			BaseJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			BaseJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY
		);
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<SecondaryTable, PrimaryKeyJoinColumn>(getSubjectHolder(), SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterator<PrimaryKeyJoinColumn> listIterator_() {
				return subject.specifiedPrimaryKeyJoinColumns();
			}

			@Override
			protected int size_() {
				return subject.specifiedPrimaryKeyJoinColumnsSize();
			}
		};
	}

	private void editPrimaryKeyJoinColumn(ObjectListSelectionModel listSelectionModel) {

		PrimaryKeyJoinColumn joinColumn = (PrimaryKeyJoinColumn) listSelectionModel.selectedValue();

		PrimaryKeyJoinColumnInSecondaryTableDialog dialog =
			new PrimaryKeyJoinColumnInSecondaryTableDialog(
				getShell(),
				getSubject(),
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
		Group groupPane = addTitledGroup(
			container,
			JptUiDetailsMessages.PrimaryKeyJoinColumnsComposite_primaryKeyJoinColumn
		);

		// Override Default check box
		Button button = addCheckBox(
			addSubPane(groupPane, 8),
			JptUiDetailsMessages.PrimaryKeyJoinColumnsComposite_overrideDefaultPrimaryKeyJoinColumns,
			buildOverrideDefaultJoinColumnHolder(),
			null
		);

		installOverrideDefaultButtonEnabler(button);

		// Primary Key Join Columns list pane
		AddRemoveListPane<SecondaryTable> joinColumnsPane = new AddRemoveListPane<SecondaryTable>(
			this,
			groupPane,
			buildPrimaryKeyJoinColumnAdapter(),
			buildPrimaryKeyJoinColumnsListModel(),
			joinColumnHolder,
			buildJoinColumnsListLabelProvider(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);

		installPrimaryKeyJoinColumnListPaneEnabler(joinColumnsPane);
	}

	private void installOverrideDefaultButtonEnabler(Button overrideDefaultButton) {
		SWTTools.controlEnabledState(buildControlBooleanHolder(), overrideDefaultButton);
	}

	private void installPrimaryKeyJoinColumnListPaneEnabler(AddRemoveListPane<SecondaryTable> pkJoinColumnListPane) {

		new PaneEnabler(
			buildOverrideDefaultJoinColumnHolder(),
			pkJoinColumnListPane
		);
	}

	private void removePrimaryKeyJoinColumn(ObjectListSelectionModel listSelectionModel) {
		int[] selectedIndices = listSelectionModel.selectedIndices();

		for (int index = selectedIndices.length; --index >= 0; ) {
			getSubject().removeSpecifiedPrimaryKeyJoinColumn(selectedIndices[index]);
		}
	}

	private void updateJoinColumns(boolean selected) {

		if (isPopulating()) {
			return;
		}

		setPopulating(true);

		try {
			SecondaryTable secondaryTable = getSubject();

			// Add a join column by creating a specified one using the default
			// one if it exists
			if (selected) {

				PrimaryKeyJoinColumn defaultJoinColumn = secondaryTable.getDefaultPrimaryKeyJoinColumn();

				if (defaultJoinColumn != null) {
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					PrimaryKeyJoinColumn pkJoinColumn = secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0);
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
			if (getSubject() == null) {
				return Boolean.FALSE;
			}
			return !getSubject().isVirtual() && listHolder.size() > 0;
		}

		public void setValue(Boolean value) {
			updateJoinColumns(value);
		}
	}
}