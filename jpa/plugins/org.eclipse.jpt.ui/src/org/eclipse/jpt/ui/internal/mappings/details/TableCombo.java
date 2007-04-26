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
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.db.internal.Connection;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class TableCombo extends BaseJpaController
{
	private ITable table;
	private Adapter listener;
	private ConnectionListener connectionListener;
	
	private CCombo combo;
	
	public TableCombo(Composite parent, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, theCommandStack, widgetFactory);
		this.listener = buildTableListener();
		this.connectionListener = buildConnectionListener();
	}
	
	private Adapter buildTableListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				tableChanged(notification);
			}
		};
	}

    private ConnectionListener buildConnectionListener() {
		return new ConnectionListener() {

			public void aboutToClose(Connection connection) {
				// not interested to this event.
			}

			public void closed(Connection connection) {
				getCombo().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						TableCombo.this.populateTableCombo();
					}
				});
			}

			public void modified(Connection connection) {
				getCombo().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						TableCombo.this.populateTableCombo();
					}
				});
			}

			public boolean okToClose(Connection connection) {
				// not interested to this event.
				return true;
			}

			public void opened(Connection connection) {
				getCombo().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						TableCombo.this.populateTableCombo();
					}
				});
			}

			public void databaseChanged(Connection connection, final Database database) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if(database == TableCombo.this.getDatabase()) {
							if (!getControl().isDisposed()) {
								TableCombo.this.populateTableCombo();
							}
						}
					}
				});
			}
			
			public void schemaChanged(Connection connection, final Schema schema) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if(schema == TableCombo.this.getTableSchema()) {
							if (!getControl().isDisposed()) {
								TableCombo.this.populateTableCombo();
							}
						}
					}
				});
			}

			public void tableChanged(Connection connection, final Table table) {
				// not interested to this event.
			}
		};
    }
    
	@Override
	protected void buildWidget(Composite parent) {
		this.combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
		this.combo.add(JpaUiMappingsMessages.TableComposite_defaultEmpty);
		this.combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String tableText = ((CCombo) e.getSource()).getText();
				if (tableText.equals("")) { //$NON-NLS-1$
					tableText = null;
					if (table.getSpecifiedName() == null || table.getSpecifiedName().equals("")) { //$NON-NLS-1$
						return;
					}
				}
				
				if (tableText != null && combo.getItemCount() > 0 && tableText.equals(combo.getItem(0))) {
					tableText = null;
				}

				if (table.getSpecifiedName() == null && tableText != null) {
					table.setSpecifiedName(tableText);
				}

				if (table.getSpecifiedName() != null && !table.getSpecifiedName().equals(tableText)) {
					table.setSpecifiedName(tableText);
				}
			}
		});
	}
	
	protected void tableChanged(Notification notification) {
		if (notification.getFeatureID(ITable.class) == JpaCoreMappingsPackage.ITABLE__SPECIFIED_NAME) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					populateTableName();
				}
			});
		}
		else if (notification.getFeatureID(ITable.class) == JpaCoreMappingsPackage.ITABLE__DEFAULT_NAME) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					populateDefaultTableName();
				}
			});
		}
		else if (notification.getFeatureID(ITable.class) == JpaCoreMappingsPackage.ITABLE__DEFAULT_SCHEMA) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					populateTableCombo();
				}
			});
		}
		else if (notification.getFeatureID(ITable.class) == JpaCoreMappingsPackage.ITABLE__SPECIFIED_SCHEMA) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					populateTableCombo();
				}
			});
		}
	}
	public void doPopulate(EObject obj) {
		this.table = (ITable) obj;
		if (this.table != null) {
			populateTableCombo();
		}
	}
	
	public void doPopulate() {
		if (this.table != null) {
			populateTableCombo();
		}
	}

	protected Database getDatabase() {
		return this.getConnectionProfile().getDatabase();
	}

	protected Schema getTableSchema() {
		return this.getConnectionProfile().getDatabase().schemaNamed(table.getSchema());
	}
	
	private ConnectionProfile getConnectionProfile() {
		return this.table.getJpaProject().connectionProfile();
	}
	
	private void populateTableCombo() {
		if (this.table == null) {
			return;
		}
		//TODO don't do instanceof check here - check on Table, or isRoot check on Entity
		//this.tableCombo.setEnabled(!(this.table instanceof SingleTableInheritanceChildTableImpl));
		populateDefaultTableName();

		if (this.getConnectionProfile().isConnected()) {
			this.combo.remove(1, this.combo.getItemCount()-1);
			Schema schema = this.getTableSchema();
			if (schema != null) {
				Iterator tables = schema.tableNames();
				for (Iterator stream = CollectionTools.sort( tables); stream.hasNext(); ) {
					this.combo.add((String) stream.next());
				}
			}
		}
		else {
			this.combo.remove(1, this.combo.getItemCount()-1);
		}
		
		populateTableName();
	}
	
	protected void populateDefaultTableName() {
		if (this.table == null) {
			return;
		}
		String defaultTableName = table.getDefaultName();
		int selectionIndex = combo.getSelectionIndex();
		combo.setItem(0, NLS.bind(JpaUiMappingsMessages.TableComposite_defaultWithOneParam, defaultTableName));
		if (selectionIndex == 0) {
			//combo text does not update when switching between 2 mappings of the same type
			//that both have a default column name.  clear the selection and then set it again
			combo.clearSelection();
			combo.select(0);
		}		
	}
	
	protected void populateTableName() {
		if (this.table == null) {
			return;
		}
		String tableName = this.table.getSpecifiedName();
		String defaultTableName = this.table.getDefaultName();
		if (tableName != null) {
			if (!this.combo.getText().equals(tableName)) {
				this.combo.setText(tableName);
			}
		}
		else {
			if (!this.combo.getText().equals(NLS.bind(JpaUiMappingsMessages.TableComposite_defaultWithOneParam, defaultTableName))) {
				this.combo.select(0);
			}
		}
	}

	public CCombo getCombo() {
		return this.combo;
	}
	
	@Override
	public Control getControl() {
		return getCombo();
	}
	
	@Override
	protected void disengageListeners() {
		if (this.table != null) {
			this.removeConnectionListener();
			this.table.eAdapters().remove(this.listener);
		}
	}
	
	@Override
	protected void engageListeners() {
		if (this.table != null) {
			this.table.eAdapters().add(this.listener);
			this.addConnectionListener();
		}		
	}
	
	private void addConnectionListener() {
		this.getConnectionProfile().addConnectionListener(this.connectionListener);
	}
	
	private void removeConnectionListener() {
		this.getConnectionProfile().removeConnectionListener(this.connectionListener);
	}
	
}
