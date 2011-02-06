/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.swt.widgets.Composite;

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
 * @see OrmEntity
 * @see OrmEntityComposite - The container of this pane
 * @see AddRemoveListPane
 * @see PrimaryKeyJoinColumnsInSecondaryTableComposite
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class AbstractSecondaryTablesComposite<T extends Entity> extends Pane<T>
{
	/**
	 * Creates a new <code>SecondaryTablesComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public AbstractSecondaryTablesComposite(Pane<? extends T> parentPane,
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
	public AbstractSecondaryTablesComposite(PropertyValueModel<? extends T> subjectHolder,
	                                Composite parent,
	                                WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	protected void addSecondaryTableFromDialog(SecondaryTableDialog dialog, ObjectListSelectionModel listSelectionModel) {
		if (dialog.open() != Window.OK) {
			return;
		}

		SecondaryTable secondaryTable = this.getSubject().addSpecifiedSecondaryTable();
		secondaryTable.setSpecifiedName(dialog.getSelectedTable());
		secondaryTable.setSpecifiedCatalog(dialog.getSelectedCatalog());
		secondaryTable.setSpecifiedSchema(dialog.getSelectedSchema());

		listSelectionModel.setSelectedValue(secondaryTable);
	}

	protected WritablePropertyValueModel<SecondaryTable> buildSecondaryTableHolder() {
		return new SimplePropertyValueModel<SecondaryTable>();
	}

	protected ILabelProvider buildSecondaryTableLabelProvider() {
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

	protected SecondaryTableDialog buildSecondaryTableDialogForAdd() {
		return new SecondaryTableDialog(getShell(), getSubject().getJpaProject(), getSubject().getTable().getDefaultCatalog(), getSubject().getTable().getDefaultSchema());
	}
	
	protected AddRemoveListPane.Adapter buildSecondaryTablesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				SecondaryTableDialog dialog = buildSecondaryTableDialogForAdd();
				addSecondaryTableFromDialog(dialog, listSelectionModel);
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptUiDetailsMessages.SecondaryTablesComposite_edit;
			}

			@Override
			public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
				SecondaryTable secondaryTable = (SecondaryTable) listSelectionModel.selectedValue();
				SecondaryTableDialog dialog = new SecondaryTableDialog(getShell(), getSubject().getJpaProject(), secondaryTable);
				editSecondaryTableFromDialog(dialog, secondaryTable);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				Entity entity = getSubject();
				int[] selectedIndices = listSelectionModel.selectedIndices();

				for (int index = selectedIndices.length; --index >= 0; ) {
					entity.removeSpecifiedSecondaryTable(selectedIndices[index]);
				}
			}
			
			@Override
			public boolean enableOptionOnSelectionChange(ObjectListSelectionModel listSelectionModel) {
				if (listSelectionModel.selectedValuesSize() != 1) {
					return false;
				}
				SecondaryTable secondaryTable = (SecondaryTable) listSelectionModel.selectedValue();
				return !secondaryTable.isVirtual();
			}
			
			@Override
			public boolean enableRemoveOnSelectionChange(ObjectListSelectionModel listSelectionModel) {
				if (listSelectionModel.selectedValue() == null) {
					return false;
				}
				SecondaryTable secondaryTable = (SecondaryTable) listSelectionModel.selectedValue();
				return !secondaryTable.isVirtual();				
			}
		};
	}

	protected void editSecondaryTableFromDialog(SecondaryTableDialog dialog, SecondaryTable secondaryTable) {
		if (dialog.open() != Window.OK) {
			return;
		}

		secondaryTable.setSpecifiedName(dialog.getSelectedTable());
		secondaryTable.setSpecifiedCatalog(dialog.getSelectedCatalog());
		secondaryTable.setSpecifiedSchema(dialog.getSelectedSchema());
	}

}
