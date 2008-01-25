/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | PrimaryKeyJoinColumnsInSecondaryTableComposite                        | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IEntity
 * @see EntityComposite - The container of this pane
 * @see AddRemoveListPane
 * @see PrimaryKeyJoinColumnsInSecondaryTableComposite
 *
 * @TODO handle xml, how to handle virtual secondaryTables, adding them to xml, are they overriden, etc??
 * @version 2.0
 * @since 1.0
 */
public class SecondaryTablesComposite extends BaseJpaController<IEntity>
{
	/**
	 * Creates a new <code>SecondaryTablesComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	public SecondaryTablesComposite(BaseJpaController<? extends IEntity> parentController,
	                                Composite parent) {

		super(parentController, parent);
	}

	/**
	 * Creates a new <code>SecondaryTablesComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public SecondaryTablesComposite(PropertyValueModel<? extends IEntity> subjectHolder,
	                                Composite parent,
	                                TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent,widgetFactory);
	}

	private void addSecondaryTableFromDialog(SecondaryTableDialog dialog,
	                                         ObjectListSelectionModel listSelectionModel) {
		if (dialog.open() == Window.OK) {
			int index = this.subject().specifiedSecondaryTablesSize();
			String name = dialog.getSelectedName();
			String catalog = dialog.getSelectedCatalog();
			String schema = dialog.getSelectedSchema();
			ISecondaryTable secondaryTable = this.subject().addSpecifiedSecondaryTable(index);
			secondaryTable.setSpecifiedName(name);
			secondaryTable.setSpecifiedCatalog(catalog);
			secondaryTable.setSpecifiedSchema(schema);

			listSelectionModel.setSelectedValue(secondaryTable);
		}
	}

	private WritablePropertyValueModel<ISecondaryTable> buildSecondaryTableHolder() {
		return new SimplePropertyValueModel<ISecondaryTable>();
	}

	private ILabelProvider buildSecondaryTableLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				// TODO display a qualified name instead
				ISecondaryTable secondaryTable = (ISecondaryTable) element;
				return secondaryTable.getName();
			}
		};
	}

	private AddRemoveListPane.Adapter buildSecondaryTablesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				SecondaryTableDialog dialog = new SecondaryTableDialog(getControl().getShell(), subject());
				addSecondaryTableFromDialog(dialog, listSelectionModel);
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptUiMappingsMessages.SecondaryTablesComposite_edit;
			}

			@Override
			public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
				ISecondaryTable secondaryTable = (ISecondaryTable) listSelectionModel.selectedValue();
				SecondaryTableDialog dialog = new SecondaryTableDialog(getControl().getShell(), secondaryTable, subject());
				editSecondaryTableFromDialog(dialog, secondaryTable);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				IEntity entity = subject();
				int[] selectedIndices = listSelectionModel.selectedIndices();

				for (int index = selectedIndices.length; --index >= 0; ) {
					entity.removeSpecifiedSecondaryTable(selectedIndices[index]);
				}
			}
		};
	}

	private ListValueModel/*<ISecondaryTable>*/ buildSecondaryTablesListHolder() {
		return new ListAspectAdapter<IEntity>/*<ISecondaryTable, IEntity*/(getSubjectHolder(), IEntity.SPECIFIED_SECONDARY_TABLES_LIST) {
			@Override
			protected ListIterator<ISecondaryTable> listIterator_() {
				return subject.secondaryTables();
			}

			@Override
			protected int size_() {
				return subject.secondaryTablesSize();
			}
		};
	}

	private ListValueModel/*<ISecondaryTable>*/ buildSortedSecondaryTablesListHolder() {
		return new SortedListValueModelAdapter/*<IEntity, ISecondaryTable>*/(
			buildSecondaryTablesListHolder()
		);
	}

	private void editSecondaryTableDialogOkd(SecondaryTableDialog dialog, ISecondaryTable secondaryTable) {
		String name = dialog.getSelectedName();
		String catalog = dialog.getSelectedCatalog();
		String schema = dialog.getSelectedSchema();

		if (secondaryTable.getSpecifiedName() == null || !secondaryTable.getSpecifiedName().equals(name)){
			secondaryTable.setSpecifiedName(name);
		}

		if (dialog.isDefaultCatalogSelected()) {
			if (secondaryTable.getSpecifiedCatalog() != null) {
				secondaryTable.setSpecifiedCatalog(null);
			}
		}
		else if (secondaryTable.getSpecifiedCatalog() == null || !secondaryTable.getSpecifiedCatalog().equals(catalog)){
			secondaryTable.setSpecifiedCatalog(catalog);
		}

		if (dialog.isDefaultSchemaSelected()) {
			if (secondaryTable.getSpecifiedSchema() != null) {
				secondaryTable.setSpecifiedSchema(null);
			}
		}
		else if (secondaryTable.getSpecifiedSchema() == null || !secondaryTable.getSpecifiedSchema().equals(schema)){
			secondaryTable.setSpecifiedSchema(schema);
		}
	}

	private void editSecondaryTableFromDialog(SecondaryTableDialog dialog, ISecondaryTable secondaryTable) {
		if (dialog.open() == Window.OK) {
			editSecondaryTableDialogOkd(dialog, secondaryTable);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = groupBoxMargin();

		WritablePropertyValueModel<ISecondaryTable> secondaryTableHolder =
			buildSecondaryTableHolder();

		// Secondary Tables add/remove list pane
		new AddRemoveListPane<IEntity>(
			this,
			buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin),
			buildSecondaryTablesAdapter(),
			buildSortedSecondaryTablesListHolder(),
			secondaryTableHolder,
			buildSecondaryTableLabelProvider(),
			IJpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);

		// Primary Key Join Columns pane
		new PrimaryKeyJoinColumnsInSecondaryTableComposite(
			this,
			secondaryTableHolder,
			container
		);
	}
}