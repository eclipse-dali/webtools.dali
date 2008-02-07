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

import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
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
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
	private AddRemoveListPane<ISecondaryTable> joinColumnsListPane;
	private Button overrideDefaultJoinColumnsCheckBox;

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

		ISecondaryTable secondaryTable = stateObject.getSecondaryTable();
		int index = secondaryTable.specifiedPrimaryKeyJoinColumnsSize();

		IPrimaryKeyJoinColumn joinColumn = secondaryTable.addSpecifiedPrimaryKeyJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
	}

	private void addPrimaryKeyJoinColumn() {

		PrimaryKeyJoinColumnInSecondaryTableDialog dialog =
			new PrimaryKeyJoinColumnInSecondaryTableDialog(shell(), subject());

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

	private String buildDefaultJoinColumnLabel(IPrimaryKeyJoinColumn joinColumn) {
		return NLS.bind(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());
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
		if (joinColumn.getSpecifiedName() == null) {
			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsBothDefault, joinColumn.getName(),joinColumn.getReferencedColumnName());
			}
			return NLS.bind(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsFirstDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());
		}

		if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsSecDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());
		}

		return NLS.bind(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParams, joinColumn.getName(), joinColumn.getReferencedColumnName());
	}

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				IPrimaryKeyJoinColumn joinColumn = (IPrimaryKeyJoinColumn) element;
				return subject().specifiedPrimaryKeyJoinColumnsSize() > 0 ?
					buildJoinColumnLabel(joinColumn) :
					buildDefaultJoinColumnLabel(joinColumn);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultHolder() {
		return new SimplePropertyValueModel<Boolean>();
	}

	private SelectionListener buildOverrideDefaultJoinColumnSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateJoinColumns();
			}
		};
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

	private ListValueModel<IPrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnListHolder() {
		return new ListAspectAdapter<ISecondaryTable, IPrimaryKeyJoinColumn>(
			getSubjectHolder(),
			ISecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMN,//TODO
			ISecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST)
		{
			@Override
			protected ListIterator<IPrimaryKeyJoinColumn> listIterator_() {
				return subject.primaryKeyJoinColumns();
			}
		};
	}

	private ListValueModel<IPrimaryKeyJoinColumn> buildSortedPrimaryKeyJoinColumnListHolder() {
		return new SortedListValueModelAdapter<IPrimaryKeyJoinColumn>(
			buildPrimaryKeyJoinColumnListHolder()
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();

		ISecondaryTable subject = subject();
		boolean enabled = (subject != null) && subject.specifiedPrimaryKeyJoinColumnsSize() > 0;

		overrideDefaultJoinColumnsCheckBox.setSelection(enabled);
		joinColumnsListPane.enableWidgets(enabled);
	}

	private void editPrimaryKeyJoinColumn(ObjectListSelectionModel listSelectionModel) {

		IPrimaryKeyJoinColumn joinColumn = (IPrimaryKeyJoinColumn) listSelectionModel.selectedValue();

		PrimaryKeyJoinColumnInSecondaryTableDialog dialog =
			new PrimaryKeyJoinColumnInSecondaryTableDialog(shell(), joinColumn);

		dialog.openDialog(buildEditPrimaryKeyJoinColumnPostExecution());
	}

	private void editPrimaryKeyJoinColumn(PrimaryKeyJoinColumnInSecondaryTableStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
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
		overrideDefaultJoinColumnsCheckBox = buildCheckBox(
			buildSubPane(groupPane, 8),
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_overrideDefaultPrimaryKeyJoinColumns,
			buildOverrideDefaultHolder()
		);

		overrideDefaultJoinColumnsCheckBox.addSelectionListener(
			buildOverrideDefaultJoinColumnSelectionListener()
		);

		installOverrideDefaultButtonEnabler(overrideDefaultJoinColumnsCheckBox);

		// Primary Key Join Columns list pane
		joinColumnsListPane = new AddRemoveListPane<ISecondaryTable>(
			this,
			groupPane,
			buildPrimaryKeyJoinColumnAdapter(),
			buildSortedPrimaryKeyJoinColumnListHolder(),
			buildPrimaryKeyJoinColumnHolder(),
			buildJoinColumnsListLabelProvider(),
			IJpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);

		installPrimaryKeyJoinColumnListPaneEnabler(joinColumnsListPane);
	}

	private void installOverrideDefaultButtonEnabler(Button overrideDefaultButton) {

		new ControlEnabler(
			buildControlBooleanHolder(),
			overrideDefaultButton
		);
	}

	private void installPrimaryKeyJoinColumnListPaneEnabler(AddRemoveListPane<ISecondaryTable> pkJoinColumnListPane) {

		new PaneEnabler(
			buildControlBooleanHolder(),
			pkJoinColumnListPane
		);
	}

	private void removePrimaryKeyJoinColumn(ObjectListSelectionModel listSelectionModel) {
		int[] selectedIndices = listSelectionModel.selectedIndices();

		for (int index = selectedIndices.length; --index > 0; ) {
			subject().removeSpecifiedPrimaryKeyJoinColumn(selectedIndices[index]);
		}
	}

	private void updateJoinColumns() {

		if (isPopulating()) {
			return;
		}

		ISecondaryTable secondaryTable = subject();
		boolean selected = overrideDefaultJoinColumnsCheckBox.getSelection();
		joinColumnsListPane.enableWidgets(selected);
		setPopulating(true);

		try {
			// Add a join column by creating a specified one using the default
			// one if it exists
			if (selected) {

				IPrimaryKeyJoinColumn defaultJoinColumn = secondaryTable.getDefaultPrimaryKeyJoinColumn();//TODO possibly null

				if (defaultJoinColumn != null) {
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					IPrimaryKeyJoinColumn pkJoinColumn = secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0);
					pkJoinColumn.setSpecifiedName(columnName);
					pkJoinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
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
}