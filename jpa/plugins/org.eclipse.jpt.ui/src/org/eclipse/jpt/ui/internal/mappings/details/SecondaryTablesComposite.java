/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
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
 * @see Entity
 * @see EntityComposite - The container of this pane
 * @see AddRemoveListPane
 * @see PrimaryKeyJoinColumnsInSecondaryTableComposite
 *
 * @TODO handle xml, how to handle virtual secondaryTables, adding them to xml, are they overriden, etc??
 * @version 2.0
 * @since 1.0
 */
public class SecondaryTablesComposite extends AbstractFormPane<Entity>
{
	/**
	 * Creates a new <code>SecondaryTablesComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public SecondaryTablesComposite(AbstractFormPane<? extends Entity> parentPane,
	                                Composite parent) {

		super(parentPane, parent, false);
	}

	/**
	 * Creates a new <code>SecondaryTablesComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public SecondaryTablesComposite(PropertyValueModel<? extends Entity> subjectHolder,
	                                Composite parent,
	                                TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private void addSecondaryTableFromDialog(SecondaryTableDialog dialog,
	                                         ObjectListSelectionModel listSelectionModel) {
		if (dialog.open() == Window.OK) {
			int index = this.subject().specifiedSecondaryTablesSize();
			String name = dialog.getSelectedName();
			String catalog = dialog.getSelectedCatalog();
			String schema = dialog.getSelectedSchema();
			SecondaryTable secondaryTable = this.subject().addSpecifiedSecondaryTable(index);
			secondaryTable.setSpecifiedName(name);
			secondaryTable.setSpecifiedCatalog(catalog);
			secondaryTable.setSpecifiedSchema(schema);

			listSelectionModel.setSelectedValue(secondaryTable);
		}
	}

	private WritablePropertyValueModel<SecondaryTable> buildSecondaryTableHolder() {
		return new SimplePropertyValueModel<SecondaryTable>();
	}

	private ILabelProvider buildSecondaryTableLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				// TODO display a qualified name instead
				SecondaryTable secondaryTable = (SecondaryTable) element;
				if (secondaryTable.getName() != null) {
					return secondaryTable.getName();
				}
				return "";//TODO
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
				SecondaryTable secondaryTable = (SecondaryTable) listSelectionModel.selectedValue();
				SecondaryTableDialog dialog = new SecondaryTableDialog(getControl().getShell(), secondaryTable, subject());
				editSecondaryTableFromDialog(dialog, secondaryTable);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				Entity entity = subject();
				int[] selectedIndices = listSelectionModel.selectedIndices();

				for (int index = selectedIndices.length; --index >= 0; ) {
					entity.removeSpecifiedSecondaryTable(selectedIndices[index]);
				}
			}
		};
	}
	
	private ListValueModel<SecondaryTable> buildSecondaryTablesListModel() {
		return new ItemPropertyListValueModelAdapter<SecondaryTable>(buildSecondaryTablesListHolder(), 
			Table.SPECIFIED_NAME_PROPERTY);
	}	

	private ListValueModel<SecondaryTable> buildSecondaryTablesListHolder() {
		return new ListAspectAdapter<Entity, SecondaryTable>(getSubjectHolder(), Entity.SPECIFIED_SECONDARY_TABLES_LIST) {
			@Override
			protected ListIterator<SecondaryTable> listIterator_() {
				return subject.secondaryTables();
			}

			@Override
			protected int size_() {
				return subject.secondaryTablesSize();
			}
		};
	}

	private void editSecondaryTableDialogOkd(SecondaryTableDialog dialog, SecondaryTable secondaryTable) {
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

	private void editSecondaryTableFromDialog(SecondaryTableDialog dialog, SecondaryTable secondaryTable) {
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

		WritablePropertyValueModel<SecondaryTable> secondaryTableHolder =
			buildSecondaryTableHolder();

		// Secondary Tables add/remove list pane
		new AddRemoveListPane<Entity>(
			this,
			buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin),
			buildSecondaryTablesAdapter(),
			buildSecondaryTablesListModel(),
			secondaryTableHolder,
			buildSecondaryTableLabelProvider(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS//TODO need a help context id for this
		);

		// Primary Key Join Columns pane
		new PrimaryKeyJoinColumnsInSecondaryTableComposite(
			this,
			secondaryTableHolder,
			container
		);
	}
}