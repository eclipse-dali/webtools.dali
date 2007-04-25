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
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.mappings.IAbstractColumn;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.db.internal.Connection;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

//TODO repopulate this panel based on the Entity table changing
public class ColumnComposite extends BaseJpaComposite
{
	
	private IColumn column;

	private Adapter columnListener;
	private ConnectionListener connectionListener;
		
	protected CCombo columnCombo;
	protected CCombo tableCombo;

	private ConnectionProfile connectionProfile;
	
	public ColumnComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
		this.columnListener = buildColumnListener();
		this.connectionListener = buildConnectionListener();
	}
	
	private Adapter buildColumnListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				columnChanged(notification);
			}
		};
	}

    private ConnectionListener buildConnectionListener() {
		return new ConnectionListener() {

			public void aboutToClose(Connection connection) {
				// not interested to this event.
			}

			public void closed(Connection connection) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						ColumnComposite.this.populateColumnCombo();
					}
				});
			}

			public void modified(Connection connection) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						ColumnComposite.this.populateColumnCombo();
					}
				});
			}

			public boolean okToClose(Connection connection) {
				// not interested to this event.
				return true;
			}

			public void opened(Connection connection) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						ColumnComposite.this.populateColumnCombo();
					}
				});
			}

			public void databaseChanged(Connection connection, final Database database) {
				return;
			}

			public void schemaChanged(Connection connection, final Schema schema) {
				return;
			}

			public void tableChanged(Connection connection, final Table table) {
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
    
	@Override
	protected void initializeLayout(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		composite.setLayout(layout);	
		
		CommonWidgets.buildColumnLabel(composite, getWidgetFactory());
		
		this.columnCombo = buildColumnCombo(composite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.columnCombo.setLayoutData(gridData);
		helpSystem.setHelp(columnCombo, IJpaHelpContextIds.MAPPING_COLUMN);
		
		
		CommonWidgets.buildColumnTableLabel(composite, getWidgetFactory());
		
		this.tableCombo = buildTableCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.tableCombo.setLayoutData(gridData);
		helpSystem.setHelp(tableCombo, IJpaHelpContextIds.MAPPING_COLUMN_TABLE);
	}
	
	
	private CCombo buildColumnCombo(Composite parent) {
		final CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
  		combo.add(JpaUiMappingsMessages.ColumnComposite_defaultEmpty);
		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String columnText = ((CCombo) e.getSource()).getText();
				if (columnText.equals("")) { //$NON-NLS-1$
					columnText = null;
					if (column.getSpecifiedName() == null || column.getSpecifiedName().equals("")) { //$NON-NLS-1$
						return;
					}
				}
				
				if (columnText != null && combo.getItemCount() > 0 && columnText.equals(combo.getItem(0))) {
					columnText = null;
				}

				if (column.getSpecifiedName() == null && columnText != null) {
					column.setSpecifiedName(columnText);
				}
				if (column.getSpecifiedName() != null && !column.getSpecifiedName().equals(columnText)) {
					column.setSpecifiedName(columnText);
				}
			}
		});
		return combo;
	}
	
	private CCombo buildTableCombo(Composite parent) {
		final CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
  		combo.add(JpaUiMappingsMessages.ColumnComposite_defaultEmpty);
		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String tableText = ((CCombo) e.getSource()).getText();
				if (tableText.equals("")) { //$NON-NLS-1$
					tableText = null;
					if (column.getSpecifiedTable() == null || column.getSpecifiedTable().equals("")) { //$NON-NLS-1$
						return;
					}
				}
				
				if (tableText != null && combo.getItemCount() > 0 && tableText.equals(combo.getItem(0))) {
					tableText = null;
				}

				if (column.getSpecifiedTable() == null && tableText != null) {
					column.setSpecifiedTable(tableText);
				}
				if (column.getSpecifiedTable() != null && !column.getSpecifiedTable().equals(tableText)) {
					column.setSpecifiedTable(tableText);
				}
			}
		});
		return combo;
		
	}
	
	protected void columnChanged(Notification notification) {
		if (notification.getFeatureID(INamedColumn.class) == JpaCoreMappingsPackage.INAMED_COLUMN__SPECIFIED_NAME) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed() || isPopulating()) {
						return;
					}
					populateColumnName();
				}
			});
		}
		else if (notification.getFeatureID(INamedColumn.class) == JpaCoreMappingsPackage.INAMED_COLUMN__DEFAULT_NAME) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					populateDefaultColumnName();
				}
			});
		}
		else if (notification.getFeatureID(IAbstractColumn.class) == JpaCoreMappingsPackage.IABSTRACT_COLUMN__DEFAULT_TABLE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					populateDefaultColumnTable();
					populateColumnCombo();
				}
			});
		}
		else if (notification.getFeatureID(IAbstractColumn.class) == JpaCoreMappingsPackage.IABSTRACT_COLUMN__SPECIFIED_TABLE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					populateColumnTable();
					populateColumnCombo();
				}
			});
		}
	}
	
	@Override
	protected void engageListeners() {
		if (this.column != null) {
			this.column.eAdapters().add(this.columnListener);
			this.addConnectionListener();
		}
	}

	@Override
	protected void disengageListeners() {
		if (this.column != null) {
			this.removeConnectionListener();
			this.column.eAdapters().remove(this.columnListener);
		}
	}

	private ConnectionProfile getConnectionProfile() {
		if(this.connectionProfile == null) {
			IJpaProject jpaProject = this.column.getJpaProject();
			this.connectionProfile = jpaProject.connectionProfile();
		}
		return this.connectionProfile;
	}
	
	private void addConnectionListener() {
		this.getConnectionProfile().addConnectionListener(this.connectionListener);
	}
	
	private void removeConnectionListener() {
		this.getConnectionProfile().removeConnectionListener(this.connectionListener);
	}
	
	private Table getDbTable() {
		return this.column.dbTable();
	}

	private void populateColumnCombo() {
		//TODO don't do instanceof check here - check on Table, or isRoot check on Entity
		//this.tableCombo.setEnabled(!(this.table instanceof SingleTableInheritanceChildTableImpl));
		populateDefaultColumnName();

		if (this.getConnectionProfile().isConnected()) {
			this.columnCombo.remove(1, this.columnCombo.getItemCount()-1);
			Table table = getDbTable();
			if (table != null) {
				for (Iterator i = CollectionTools.sort(CollectionTools.list(table.columnNames())).iterator(); i.hasNext();) {
					this.columnCombo.add((String) i.next());
				}
			}
		}
		else {
			this.columnCombo.remove(1, this.columnCombo.getItemCount()-1);
		}
		populateColumnName();
	}
	
	protected void populateDefaultColumnName() {
		String defaultTableName = column.getDefaultName();
		int selectionIndex = columnCombo.getSelectionIndex();
		columnCombo.setItem(0, NLS.bind(JpaUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultTableName));
		if (selectionIndex == 0) {
			//combo text does not update when switching between 2 mappings of the same type
			//that both have a default column name.  clear the selection and then set it again
			columnCombo.clearSelection();
			columnCombo.select(0);
		}		
	}
	
	protected void populateColumnName() {
		String specifiedColumnName = this.column.getSpecifiedName();
		if (specifiedColumnName != null) {
			if (!this.columnCombo.getText().equals(specifiedColumnName)) {
				this.columnCombo.setText(specifiedColumnName);
			}
		}
		else {
			String defaultColumnName = this.column.getDefaultName();
			if (!this.columnCombo.getText().equals(NLS.bind(JpaUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultColumnName))) {
				this.columnCombo.select(0);
			}
		}
	}
	
	private void populateTableCombo() {
		//TODO don't do instanceof check here - check on Table, or isRoot check on Entity
		//this.tableCombo.setEnabled(!(this.table instanceof SingleTableInheritanceChildTableImpl));
		populateDefaultColumnTable();
		this.tableCombo.remove(1, this.tableCombo.getItemCount()-1);
		
		if (this.column != null) {
			for (Iterator i = this.column.getOwner().getTypeMapping().associatedTableNamesIncludingInherited(); i.hasNext(); ) {
				this.tableCombo.add((String) i.next());			
			}
		}
		populateColumnTable();
	}
	
	protected void populateDefaultColumnTable() {
		String defaultTableName = column.getDefaultTable();
		int selectionIndex = tableCombo.getSelectionIndex();
		tableCombo.setItem(0, NLS.bind(JpaUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultTableName));
		if (selectionIndex == 0) {
			//combo text does not update when switching between 2 mappings of the same type
			//that both have a default column name.  clear the selection and then set it again
			tableCombo.clearSelection();
			tableCombo.select(0);
		}		
	}
	
	protected void populateColumnTable() {
		String tableName = this.column.getSpecifiedTable();
		String defaultTableName = this.column.getDefaultTable();
		if (tableName != null) {
			if (!this.tableCombo.getText().equals(tableName)) {
				this.tableCombo.setText(tableName);
			}
		}
		else {
			if (!this.tableCombo.getText().equals(NLS.bind(JpaUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultTableName))) {
				this.tableCombo.select(0);
			}
		}
	}

	public void doPopulate(EObject obj) {
		this.column = (IColumn) obj;
		if (this.column != null) {
			populateColumnCombo();
			populateTableCombo();
		}
	}
	
	public void doPopulate() {
		if (this.column != null) {
			populateColumnCombo();
			populateTableCombo();
		}
	}
	
	protected void enableWidgets(boolean enabled) {
		this.columnCombo.setEnabled(enabled);
		this.tableCombo.setEnabled(enabled);
		//this.insertableComboViewer.getCombo().setEnabled(enabled);
		//this.updatableComboViewer.getCombo().setEnabled(enabled);
	}

}
