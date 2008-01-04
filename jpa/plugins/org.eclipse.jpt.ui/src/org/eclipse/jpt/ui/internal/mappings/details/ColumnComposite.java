/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jpt.core.internal.context.base.IAbstractColumn;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

//TODO repopulate this panel based on the Entity table changing
@SuppressWarnings("nls")
public class ColumnComposite extends BaseJpaComposite<IColumn>
{
	private CCombo columnCombo;
	private ConnectionListener connectionListener;
	private ConnectionProfile connectionProfile;
	private EnumComboViewer<IColumn, Boolean> insertableCombo;
	private CCombo tableCombo;
	private EnumComboViewer<IColumn, Boolean> updatableCombo;

	/**
	 * Creates a new <code>ColumnComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>T</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public ColumnComposite(PropertyValueModel<? extends IColumn> subjectHolder,
	                       Composite parent,
	                       TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, SWT.NULL, widgetFactory);
	}

	private void addConnectionListener() {
		this.getConnectionProfile().addConnectionListener(this.connectionListener);
	}

	private CCombo buildColumnCombo(Composite parent) {
		final CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
  		combo.add(JptUiMappingsMessages.ColumnComposite_defaultEmpty);
		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String columnText = ((CCombo) e.getSource()).getText();
				if (columnText.equals("")) {
					columnText = null;
					if (subject().getSpecifiedName() == null || subject().getSpecifiedName().equals("")) {
						return;
					}
				}

				if (columnText != null && combo.getItemCount() > 0 && columnText.equals(combo.getItem(0))) {
					columnText = null;
				}

				if (subject().getSpecifiedName() == null && columnText != null) {
					subject().setSpecifiedName(columnText);
				}
				if (subject().getSpecifiedName() != null && !subject().getSpecifiedName().equals(columnText)) {
					subject().setSpecifiedName(columnText);
				}
			}
		});
		return combo;
	}

    private Adapter buildColumnListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				columnChanged(notification);
			}
		};
	}

	private ConnectionListener buildConnectionListener() {
		return new ConnectionListener() {

			public void aboutToClose(ConnectionProfile profile) {
				// not interested to this event.
			}

			public void closed(ConnectionProfile profile) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						ColumnComposite.this.populateColumnCombo();
					}
				});
			}

			public void databaseChanged(ConnectionProfile profile, final Database database) {
				return;
			}

			public void modified(ConnectionProfile profile) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						ColumnComposite.this.populateColumnCombo();
					}
				});
			}

			public boolean okToClose(ConnectionProfile profile) {
				// not interested to this event.
				return true;
			}

			public void opened(ConnectionProfile profile) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						ColumnComposite.this.populateColumnCombo();
					}
				});
			}

			public void schemaChanged(ConnectionProfile profile, final Schema schema) {
				return;
			}

			public void tableChanged(ConnectionProfile profile, final Table table) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if(table == getDbTable()) {
							if (!getControl().isDisposed()) {
								ColumnComposite.this.populateColumnCombo();
							}
						}
					}
				});
			}
		};
    }


	private EnumComboViewer<IColumn, Boolean> buildInsertableCombo(Composite container) {

		return new EnumComboViewer<IColumn, Boolean>(getSubjectHolder(), container, getWidgetFactory()) {
			@Override
			protected Boolean[] choices() {
				return new Boolean[] { Boolean.TRUE, Boolean.FALSE };
			}

			@Override
			protected Boolean defaultValue() {
				return subject().getDefaultInsertable();
			}

			@Override
			protected String displayString(Boolean value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					ColumnComposite.this,
					value
				);
			}

			@Override
			protected Boolean getValue() {
				return subject().getSpecifiedInsertable();
			}

			@Override
			protected String propertyName() {
				return IAbstractColumn.SPECIFIED_INSERTABLE_PROPERTY;
			}

			@Override
			protected void setValue(Boolean value) {
				subject().setSpecifiedInsertable(value);
			}
		};
	}

	private CCombo buildTableCombo(Composite parent) {
		final CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
  		combo.add(JptUiMappingsMessages.ColumnComposite_defaultEmpty);
		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String tableText = ((CCombo) e.getSource()).getText();
				if (tableText.equals("")) {
					tableText = null;
					if (subject().getSpecifiedTable() == null || subject().getSpecifiedTable().equals("")) {
						return;
					}
				}

				if (tableText != null && combo.getItemCount() > 0 && tableText.equals(combo.getItem(0))) {
					tableText = null;
				}

				if (subject().getSpecifiedTable() == null && tableText != null) {
					subject().setSpecifiedTable(tableText);
				}
				if (subject().getSpecifiedTable() != null && !subject().getSpecifiedTable().equals(tableText)) {
					subject().setSpecifiedTable(tableText);
				}
			}
		});
		return combo;

	}

	private EnumComboViewer<IColumn, Boolean> buildUpdatableCombo(Composite container) {

		return new EnumComboViewer<IColumn, Boolean>(getSubjectHolder(), container, getWidgetFactory()) {
			@Override
			protected Boolean[] choices() {
				return new Boolean[] { Boolean.TRUE, Boolean.FALSE };
			}

			@Override
			protected Boolean defaultValue() {
				return subject().getDefaultUpdatable();
			}

			@Override
			protected String displayString(Boolean value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					ColumnComposite.this,
					value
				);
			}

			@Override
			protected Boolean getValue() {
				return subject().getSpecifiedUpdatable();
			}

			@Override
			protected String propertyName() {
				return IAbstractColumn.SPECIFIED_UPDATABLE_PROPERTY;
			}

			@Override
			protected void setValue(Boolean value) {
				subject().setSpecifiedUpdatable(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void disengageListeners() {
		super.disengageListeners();

		if (this.subject() != null) {
			this.removeConnectionListener();
//			this.subject().eAdapters().remove(this.columnListener);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void dispose() {
		super.dispose();

		this.insertableCombo.dispose();
		this.updatableCombo.dispose();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
//		this.populateColumnCombo();
//		this.populateTableCombo();
		this.connectionProfile = null;
		this.insertableCombo.populate();
		this.updatableCombo.populate();
	}

	protected void enableWidgets(boolean enabled) {
		this.columnCombo.setEnabled(enabled);
		this.tableCombo.setEnabled(enabled);
		this.insertableCombo.getControl().setEnabled(enabled);
		this.updatableCombo.getControl().setEnabled(enabled);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void engageListeners() {
		super.engageListeners();

		if (this.subject() != null) {
//			this.subject().eAdapters().add(this.columnListener);
			this.addConnectionListener();
		}
	}

	private ConnectionProfile getConnectionProfile() {
		if (this.connectionProfile == null) {
			this.connectionProfile = this.subject().jpaProject().connectionProfile();
		}
		return this.connectionProfile;
	}

	private Table getDbTable() {
		return this.subject().dbTable();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();

//		this.columnListener     = buildColumnListener();
		this.connectionListener = buildConnectionListener();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		container = buildTitledPane(
			JptUiMappingsMessages.ColumnComposite_columnSection,
			container
		);

		// Column widgets
		columnCombo = buildColumnCombo(container);

		buildLabeledComposite(
			JptUiMappingsMessages.ColumnChooser_label,
			container,
			columnCombo,
			IJpaHelpContextIds.MAPPING_COLUMN
		);

		// Table widgets
		tableCombo = buildTableCombo(container);

		buildLabeledComposite(
			JptUiMappingsMessages.ColumnTableChooser_label,
			container,
			tableCombo,
			IJpaHelpContextIds.MAPPING_COLUMN_TABLE
		);

		// Insertable widgets
		insertableCombo = buildInsertableCombo(container);

		buildLabeledComposite(
			JptUiMappingsMessages.ColumnComposite_insertable,
			container,
			insertableCombo.getControl(),
			IJpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);

		// Updatable widgets
		updatableCombo = buildUpdatableCombo(container);

		buildLabeledComposite(
			JptUiMappingsMessages.ColumnComposite_updatable,
			container,
			updatableCombo.getControl(),
			IJpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
		);
	}

	private void populateColumnCombo() {
		//TODO don't do instanceof check here - check on Table, or isRoot check on Entity
		//this.tableCombo.setEnabled(!(this.table instanceof SingleTableInheritanceChildTableImpl));
		populateDefaultColumnName();

		if (this.getConnectionProfile().isConnected()) {
			this.columnCombo.remove(1, this.columnCombo.getItemCount()-1);
			Table table = getDbTable();
			if (table != null) {
				for (Iterator<String> i = CollectionTools.sort(CollectionTools.list(table.columnNames())).iterator(); i.hasNext();) {
					this.columnCombo.add(i.next());
				}
			}
		}
		else {
			this.columnCombo.remove(1, this.columnCombo.getItemCount()-1);
		}
		populateColumnName();
	}

	protected void populateColumnName() {
		String specifiedColumnName = this.subject().getSpecifiedName();
		if (specifiedColumnName != null) {
			if (!this.columnCombo.getText().equals(specifiedColumnName)) {
				this.columnCombo.setText(specifiedColumnName);
			}
		}
		else {
			String defaultColumnName = this.subject().getDefaultName();
			if (!this.columnCombo.getText().equals(NLS.bind(JptUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultColumnName))) {
				this.columnCombo.select(0);
			}
		}
	}

	protected void populateColumnTable() {
		String tableName = this.subject().getSpecifiedTable();
		String defaultTableName = this.subject().getDefaultTable();
		if (tableName != null) {
			if (!this.tableCombo.getText().equals(tableName)) {
				this.tableCombo.setText(tableName);
			}
		}
		else {
			if (!this.tableCombo.getText().equals(NLS.bind(JptUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultTableName))) {
				this.tableCombo.select(0);
			}
		}
	}

	protected void populateDefaultColumnName() {
		String defaultTableName = subject().getDefaultName();
		int selectionIndex = columnCombo.getSelectionIndex();
		columnCombo.setItem(0, NLS.bind(JptUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultTableName));
		if (selectionIndex == 0) {
			//combo text does not update when switching between 2 mappings of the same type
			//that both have a default subject() name.  clear the selection and then set it again
			columnCombo.clearSelection();
			columnCombo.select(0);
		}
	}

	protected void populateDefaultColumnTable() {
		String defaultTableName = subject().getDefaultTable();
		int selectionIndex = tableCombo.getSelectionIndex();
		tableCombo.setItem(0, NLS.bind(JptUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultTableName));
		if (selectionIndex == 0) {
			//combo text does not update when switching between 2 mappings of the same type
			//that both have a default subject() name.  clear the selection and then set it again
			tableCombo.clearSelection();
			tableCombo.select(0);
		}
	}

	private void populateTableCombo() {
		//TODO don't do instanceof check here - check on Table, or isRoot check on Entity
		//this.tableCombo.setEnabled(!(this.table instanceof SingleTableInheritanceChildTableImpl));
		populateDefaultColumnTable();
		this.tableCombo.remove(1, this.tableCombo.getItemCount()-1);

		if (this.subject() != null) {
			for (Iterator<String> i = this.subject().owner().typeMapping().associatedTableNamesIncludingInherited(); i.hasNext(); ) {
				this.tableCombo.add(i.next());
			}
		}
		populateColumnTable();
	}

	private void removeConnectionListener() {
		this.getConnectionProfile().removeConnectionListener(this.connectionListener);
	}
}