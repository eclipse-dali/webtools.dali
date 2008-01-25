/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.BaseJpaControllerEnabler;
import org.eclipse.jpt.ui.internal.util.ControlEnabler;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | x Override Default                                                        |
 * |                                                                           |
 * | - Primary Key Join Columns ---------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
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
public class PrimaryKeyJoinColumnsInSecondaryTableComposite extends BaseJpaController<ISecondaryTable>
{
	/**
	 * Creates a new <code>PrimaryKeyJoinColumnsInSecondaryTableComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public PrimaryKeyJoinColumnsInSecondaryTableComposite(BaseJpaController<?> parentController,
	                                                      PropertyValueModel<? extends ISecondaryTable> subjectHolder,
	                                                      Composite parent) {

		super(parentController, subjectHolder, parent);
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

	private void addJoinColumnFromDialog(PrimaryKeyJoinColumnInSecondaryTableDialog dialog) {
		if (dialog.open() == Window.OK) {
			int index = this.subject().specifiedPrimaryKeyJoinColumnsSize();
			String name = dialog.getSelectedName();
			String referencedColumnName = dialog.getReferencedColumnName();
			IPrimaryKeyJoinColumn joinColumn = this.subject().addSpecifiedPrimaryKeyJoinColumn(index);
			joinColumn.setSpecifiedName(name);
			joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}
	}

	private void addPrimaryKeyJoinColumn() {
		PrimaryKeyJoinColumnInSecondaryTableDialog dialog = new PrimaryKeyJoinColumnInSecondaryTableDialog(this.getControl().getShell(), this.subject());
		addJoinColumnFromDialog(dialog);
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
		return new OverrideDefaultValueModel(getSubjectHolder());
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

	private ListValueModel/*<IPrimaryKeyJoinColumn>*/ buildPrimaryKeyJoinColumnListHolder() {
		return new ListAspectAdapter<ISecondaryTable>/*<IPrimaryKeyJoinColumn, ISecondaryTable>*/(
			getSubjectHolder(),
			ISecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST,
			ISecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST)
		{
			@Override
			protected ListIterator<IPrimaryKeyJoinColumn> listIterator_() {
				return subject.primaryKeyJoinColumns();
			}
		};
	}

	private ListValueModel/*<IPrimaryKeyJoinColumn>*/ buildSortedPrimaryKeyJoinColumnListHolder() {
		return new SortedListValueModelAdapter/*<IPrimaryKeyJoinColumn, ISecondaryTable>*/(
			buildPrimaryKeyJoinColumnListHolder()
		);
	}

	private void editJoinColumnDialogOkd(PrimaryKeyJoinColumnInSecondaryTableDialog dialog, IPrimaryKeyJoinColumn joinColumn) {
		String name = dialog.getSelectedName();
		String referencedColumnName = dialog.getReferencedColumnName();

		if (dialog.isDefaultNameSelected()) {
			if (joinColumn.getSpecifiedName() != null) {
				joinColumn.setSpecifiedName(null);
			}
		}
		else if (joinColumn.getSpecifiedName() == null || !joinColumn.getSpecifiedName().equals(name)){
			joinColumn.setSpecifiedName(name);
		}

		if (dialog.isDefaultReferencedColumnNameSelected()) {
			if (joinColumn.getSpecifiedReferencedColumnName() != null) {
				joinColumn.setSpecifiedReferencedColumnName(null);
			}
		}
		else if (joinColumn.getSpecifiedReferencedColumnName() == null || !joinColumn.getSpecifiedReferencedColumnName().equals(referencedColumnName)){
			joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}
	}

	private void editJoinColumnFromDialog(PrimaryKeyJoinColumnInSecondaryTableDialog dialog, IPrimaryKeyJoinColumn joinColumn) {
		if (dialog.open() == Window.OK) {
			editJoinColumnDialogOkd(dialog, joinColumn);
		}
	}

	private void editPrimaryKeyJoinColumn(ObjectListSelectionModel listSelectionModel) {
		IPrimaryKeyJoinColumn joinColumn = (IPrimaryKeyJoinColumn) listSelectionModel.selectedValue();
		PrimaryKeyJoinColumnInSecondaryTableDialog dialog = new PrimaryKeyJoinColumnInSecondaryTableDialog(this.getControl().getShell(), joinColumn);
		editJoinColumnFromDialog(dialog, joinColumn);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = groupBoxMargin();

		// Override Default check box
		Button overrideDefaultButton = buildCheckBox(
			buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin),
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_overrideDefaultPrimaryKeyJoinColumns,
			buildOverrideDefaultHolder()
		);

		installOverrideDefaultButtonEnabler(overrideDefaultButton);

		// Primary Key Join Columns group pane
		container = buildTitledPane(
			container,
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_primaryKeyJoinColumn
		);

		// Primary Key Join Columns add/remove list pane
		AddRemoveListPane<ISecondaryTable> pkJoinColumnListPane = new AddRemoveListPane<ISecondaryTable>(
			this,
			container,
			buildPrimaryKeyJoinColumnAdapter(),
			buildSortedPrimaryKeyJoinColumnListHolder(),
			buildPrimaryKeyJoinColumnHolder(),
			buildJoinColumnsListLabelProvider()
		);

		helpSystem().setHelp(
			pkJoinColumnListPane.getControl(),
			IJpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);

		installPrimaryKeyJoinColumnListPaneEnabler(pkJoinColumnListPane);
	}

	private void installOverrideDefaultButtonEnabler(Button overrideDefaultButton) {

		new ControlEnabler(
			buildControlBooleanHolder(),
			overrideDefaultButton
		);
	}

	private void installPrimaryKeyJoinColumnListPaneEnabler(AddRemoveListPane<ISecondaryTable> pkJoinColumnListPane) {

		new BaseJpaControllerEnabler(
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

	private class OverrideDefaultValueModel extends TransformationPropertyValueModel<ISecondaryTable, Boolean>
	                                        implements WritablePropertyValueModel<Boolean>
	{
		OverrideDefaultValueModel(PropertyValueModel<ISecondaryTable> valueHolder) {
			super(valueHolder);
			engageListChangeListener(valueHolder);
		}

		private void engageListChangeListener(PropertyValueModel<ISecondaryTable> valueHolder) {
			new ListAspectAdapter<ISecondaryTable>/*<IPrimaryKeyJoinColumn, ISecondaryTable>*/(valueHolder, ISecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST) {
				@Override
				protected void itemsAdded(ListChangeEvent e) {
					super.itemsAdded(e);
					valueChanged(e);
				}

				@Override
				protected void itemsMoved(ListChangeEvent e) {
					super.itemsMoved(e);
					valueChanged(e);
				}

				@Override
				protected void itemsRemoved(ListChangeEvent e) {
					super.itemsRemoved(e);
					valueChanged(e);
				}

				@Override
				protected void itemsReplaced(ListChangeEvent e) {
					super.itemsReplaced(e);
					valueChanged(e);
				}

				@Override
				protected void listChanged(ListChangeEvent e) {
					super.listChanged(e);
					valueChanged(e);
				}

				@Override
				protected void listCleared(ListChangeEvent e) {
					super.listCleared(e);
					valueChanged(e);
				}

				@Override
				protected ListIterator<IPrimaryKeyJoinColumn> listIterator_() {
					return subject.specifiedPrimaryKeyJoinColumns();
				}

				@Override
				protected int size_() {
					return subject.specifiedPrimaryKeyJoinColumnsSize();
				}

				private void valueChanged(ListChangeEvent e) {
					PropertyChangeEvent event = new PropertyChangeEvent(e.getSource(), e.aspectName(), null, subject);
					OverrideDefaultValueModel.this.valueChanged(event);
				}
			};
		}

		public void setValue(Boolean value) {
			ISecondaryTable secondaryTable = subject();

			if (value) {
				IPrimaryKeyJoinColumn defaultJoinColumn = secondaryTable.defaultPrimaryKeyJoinColumns().next();
				String columnName = defaultJoinColumn.getDefaultName();
				String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

				IPrimaryKeyJoinColumn pkJoinColumn = secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0);
				pkJoinColumn.setSpecifiedName(columnName);
				pkJoinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
			}
			else {
				for (int index = secondaryTable.specifiedPrimaryKeyJoinColumnsSize(); --index >= 0; ) {
					secondaryTable.removeSpecifiedPrimaryKeyJoinColumn(index);
				}
			}
		}

		@Override
		protected Boolean transform_(ISecondaryTable value) {
			return value.specifiedPrimaryKeyJoinColumnsSize() > 0;
		}
	}
}