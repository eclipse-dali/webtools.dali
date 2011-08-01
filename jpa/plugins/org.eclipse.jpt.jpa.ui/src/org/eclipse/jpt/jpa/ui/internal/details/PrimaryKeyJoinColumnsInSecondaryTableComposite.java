/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlySecondaryTable;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
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
 * @see SecondaryTable
 * @see AddRemoveListPane
 *
 * @version 2.0
 * @since 1.0
 */
public class PrimaryKeyJoinColumnsInSecondaryTableComposite extends Pane<ReadOnlySecondaryTable>
{
	private WritablePropertyValueModel<ReadOnlyPrimaryKeyJoinColumn> joinColumnHolder;

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnsInSecondaryTableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public PrimaryKeyJoinColumnsInSecondaryTableComposite(Pane<?> parentPane,
	                                                      PropertyValueModel<? extends ReadOnlySecondaryTable> subjectHolder,
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
	public PrimaryKeyJoinColumnsInSecondaryTableComposite(PropertyValueModel<? extends ReadOnlySecondaryTable> subjectHolder,
	                                                      Composite parent,
	                                                      WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	void addJoinColumn(PrimaryKeyJoinColumnInSecondaryTableStateObject stateObject) {

		SecondaryTable secondaryTable = stateObject.getOwner();
		int index = secondaryTable.getSpecifiedPrimaryKeyJoinColumnsSize();

		PrimaryKeyJoinColumn joinColumn = secondaryTable.addSpecifiedPrimaryKeyJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
	}

	void addPrimaryKeyJoinColumn() {

		PrimaryKeyJoinColumnInSecondaryTableDialog dialog =
			new PrimaryKeyJoinColumnInSecondaryTableDialog(getShell(), (SecondaryTable) getSubject(), null);

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
		return new TransformationPropertyValueModel<ReadOnlySecondaryTable, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(ReadOnlySecondaryTable value) {
				if (value == null) {
					return Boolean.FALSE;
				}
				return Boolean.valueOf(!value.isVirtual());
			}
		};
	}

	private PropertyValueModel<ReadOnlyPrimaryKeyJoinColumn> buildDefaultJoinColumnHolder() {
		return new PropertyAspectAdapter<ReadOnlySecondaryTable, ReadOnlyPrimaryKeyJoinColumn>(getSubjectHolder(), ReadOnlySecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMN) {
			@Override
			protected ReadOnlyPrimaryKeyJoinColumn buildValue_() {
				return this.subject.getDefaultPrimaryKeyJoinColumn();
			}
		};
	}

	private ListValueModel<ReadOnlyPrimaryKeyJoinColumn> buildDefaultJoinColumnListHolder() {
		return new PropertyListValueModelAdapter<ReadOnlyPrimaryKeyJoinColumn>(
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

	String buildJoinColumnLabel(ReadOnlyPrimaryKeyJoinColumn joinColumn) {

		if (joinColumn.isDefault()) {
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
				ReadOnlyPrimaryKeyJoinColumn joinColumn = (ReadOnlyPrimaryKeyJoinColumn) element;
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

	private WritablePropertyValueModel<ReadOnlyPrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnHolder() {
		return new SimplePropertyValueModel<ReadOnlyPrimaryKeyJoinColumn>();
	}

	private ListValueModel<ReadOnlyPrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListHolder() {
		List<ListValueModel<ReadOnlyPrimaryKeyJoinColumn>> list = new ArrayList<ListValueModel<ReadOnlyPrimaryKeyJoinColumn>>();
		list.add(buildSpecifiedJoinColumnsListHolder());
		list.add(buildDefaultJoinColumnListHolder());
		return new CompositeListValueModel<ListValueModel<ReadOnlyPrimaryKeyJoinColumn>, ReadOnlyPrimaryKeyJoinColumn>(list);
	}

	private ListValueModel<ReadOnlyPrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<ReadOnlyPrimaryKeyJoinColumn>(
			buildPrimaryKeyJoinColumnsListHolder(),
			ReadOnlyNamedColumn.SPECIFIED_NAME_PROPERTY,
			ReadOnlyNamedColumn.DEFAULT_NAME_PROPERTY,
			ReadOnlyBaseJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			ReadOnlyBaseJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY
		);
	}

	ListValueModel<ReadOnlyPrimaryKeyJoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<ReadOnlySecondaryTable, ReadOnlyPrimaryKeyJoinColumn>(getSubjectHolder(), ReadOnlySecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterable<ReadOnlyPrimaryKeyJoinColumn> getListIterable() {
				return new SuperListIterableWrapper<ReadOnlyPrimaryKeyJoinColumn>(subject.getSpecifiedPrimaryKeyJoinColumns());
			}

			@Override
			protected int size_() {
				return subject.getSpecifiedPrimaryKeyJoinColumnsSize();
			}
		};
	}

	void editPrimaryKeyJoinColumn(ObjectListSelectionModel listSelectionModel) {

		PrimaryKeyJoinColumn joinColumn = (PrimaryKeyJoinColumn) listSelectionModel.selectedValue();

		PrimaryKeyJoinColumnInSecondaryTableDialog dialog =
			new PrimaryKeyJoinColumnInSecondaryTableDialog(
				getShell(),
				(SecondaryTable) getSubject(),
				joinColumn
			);

		dialog.openDialog(buildEditPrimaryKeyJoinColumnPostExecution());
	}

	void editPrimaryKeyJoinColumn(PrimaryKeyJoinColumnInSecondaryTableStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	@Override
	protected void initialize() {
		super.initialize();
		joinColumnHolder = buildPrimaryKeyJoinColumnHolder();
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Primary Key Join Columns group pane
		Group groupPane = addTitledGroup(
			container,
			JptUiDetailsMessages.PrimaryKeyJoinColumnsComposite_primaryKeyJoinColumn
		);

		// Override Default check box
		addCheckBox(
			addSubPane(groupPane, 8),
			JptUiDetailsMessages.PrimaryKeyJoinColumnsComposite_overrideDefaultPrimaryKeyJoinColumns,
			buildOverrideDefaultJoinColumnHolder(),
			null,
			buildControlBooleanHolder()
		);

		// Primary Key Join Columns list pane
		AddRemoveListPane<ReadOnlySecondaryTable> joinColumnsPane = new AddRemoveListPane<ReadOnlySecondaryTable>(
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

	private void installPrimaryKeyJoinColumnListPaneEnabler(AddRemoveListPane<ReadOnlySecondaryTable> pkJoinColumnListPane) {

		new PaneEnabler(
			buildOverrideDefaultJoinColumnHolder(),
			pkJoinColumnListPane
		);
	}

	void removePrimaryKeyJoinColumn(ObjectListSelectionModel listSelectionModel) {
		int[] selectedIndices = listSelectionModel.selectedIndices();

		for (int index = selectedIndices.length; --index >= 0; ) {
			((SecondaryTable) getSubject()).removeSpecifiedPrimaryKeyJoinColumn(selectedIndices[index]);
		}
	}

	void updateJoinColumns(boolean selected) {

		if (isPopulating()) {
			return;
		}

		setPopulating(true);

		try {
			SecondaryTable secondaryTable = (SecondaryTable) getSubject();

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
				for (int index = secondaryTable.getSpecifiedPrimaryKeyJoinColumnsSize(); --index >= 0; ) {
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
			return Boolean.valueOf(this.buildValue_());
		}

		protected boolean buildValue_() {
			ReadOnlySecondaryTable table = getSubject();
			return (table != null) && ! table.isVirtual() && listHolder.size() > 0;
		}

		public void setValue(Boolean value) {
			updateJoinColumns(value.booleanValue());
		}
	}
}