/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.AbstractAdapter;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.context.SpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmEntityComposite;
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

		super(parentPane, parent);
	}

	protected SpecifiedSecondaryTable addSecondaryTableFromDialog(SecondaryTableDialog dialog) {
		if (dialog.open() != Window.OK) {
			return null;
		}

		SpecifiedSecondaryTable secondaryTable = this.getSubject().addSpecifiedSecondaryTable();
		secondaryTable.setSpecifiedName(dialog.getSelectedTable());
		secondaryTable.setSpecifiedCatalog(dialog.getSelectedCatalog());
		secondaryTable.setSpecifiedSchema(dialog.getSelectedSchema());

		return secondaryTable;
	}

	protected ModifiableCollectionValueModel<SpecifiedSecondaryTable> buildSelectedSecondaryTablesModel() {
		return new SimpleCollectionValueModel<SpecifiedSecondaryTable>();
	}

	protected PropertyValueModel<SpecifiedSecondaryTable> buildSelectedSecondaryTableModel(CollectionValueModel<SpecifiedSecondaryTable> selectedSecondaryTablesModel) {
		return new CollectionPropertyValueModelAdapter<SpecifiedSecondaryTable, SpecifiedSecondaryTable>(selectedSecondaryTablesModel) {
			@Override
			protected SpecifiedSecondaryTable buildValue() {
				if (this.collectionModel.size() == 1) {
					return this.collectionModel.iterator().next();
				}
				return null;
			}
		};
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
	
	protected AddRemoveListPane.Adapter<SpecifiedSecondaryTable> buildSecondaryTablesAdapter() {
		return new AbstractAdapter<SpecifiedSecondaryTable>() {

			public SpecifiedSecondaryTable addNewItem() {
				SecondaryTableDialog dialog = buildSecondaryTableDialogForAdd();
				return addSecondaryTableFromDialog(dialog);
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptJpaUiDetailsMessages.SecondaryTablesComposite_edit;
			}

			@Override
			public void optionOnSelection(CollectionValueModel<SpecifiedSecondaryTable> selectedItemsModel) {
				//assume only 1 item in the list based on the optionalButtonEnabledModel
				SpecifiedSecondaryTable secondaryTable = selectedItemsModel.iterator().next();
				SecondaryTableDialog dialog = new SecondaryTableDialog(getShell(), getSubject().getJpaProject(), secondaryTable);
				editSecondaryTableFromDialog(dialog, secondaryTable);
			}

			public void removeSelectedItems(CollectionValueModel<SpecifiedSecondaryTable> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				SpecifiedSecondaryTable secondaryTable = selectedItemsModel.iterator().next();
				getSubject().removeSpecifiedSecondaryTable(secondaryTable);
			}

			/**
			 * If any of the selected secondary tables are virtual, the Remove button is disabled
			 */
			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<SpecifiedSecondaryTable> selectedItemsModel) {
				return buildOptionalButtonEnabledModel(selectedItemsModel);
			}

			@Override
			public PropertyValueModel<Boolean> buildOptionalButtonEnabledModel(CollectionValueModel<SpecifiedSecondaryTable> selectedItemsModel) {
				return new CollectionPropertyValueModelAdapter<Boolean, SpecifiedSecondaryTable>(selectedItemsModel) {
					@Override
					protected Boolean buildValue() {
						if (this.collectionModel.size() == 1) {
							SpecifiedSecondaryTable secondaryTable = this.collectionModel.iterator().next();
							return Boolean.valueOf(!secondaryTable.isVirtual());				
						}
						return Boolean.FALSE;
					}
				};
			}
		};
	}

	protected void editSecondaryTableFromDialog(SecondaryTableDialog dialog, SpecifiedSecondaryTable secondaryTable) {
		if (dialog.open() != Window.OK) {
			return;
		}

		secondaryTable.setSpecifiedName(dialog.getSelectedTable());
		secondaryTable.setSpecifiedCatalog(dialog.getSelectedCatalog());
		secondaryTable.setSpecifiedSchema(dialog.getSelectedSchema());
	}

}
