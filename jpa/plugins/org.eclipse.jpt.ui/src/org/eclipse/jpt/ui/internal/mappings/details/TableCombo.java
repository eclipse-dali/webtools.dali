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
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
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

public class TableCombo extends BaseJpaController<ITable>
{
	private Adapter listener;

	/**
	 * Caching the connectionProfile so we can remove the listener. If the
	 * cached table object has been removed from the model then we no longer
	 * have access to parent and cannot find the connectionProfile
	 */
	private ConnectionProfile connectionProfile;

	private ConnectionListener connectionListener;

	private CCombo combo;

	public TableCombo(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, widgetFactory);
		this.listener = buildTableListener();
		this.connectionListener = buildConnectionListener();
	}

	private Adapter buildTableListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				tableChanged(notification);
			}
		};
	}

	private ConnectionListener buildConnectionListener() {
		return new ConnectionListener() {
			public void aboutToClose(ConnectionProfile profile) {
			// not interested to this event.
			}

			public void closed(ConnectionProfile profile) {
				getCombo().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						TableCombo.this.populateTableCombo();
					}
				});
			}

			public void modified(ConnectionProfile profile) {
				getCombo().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						TableCombo.this.populateTableCombo();
					}
				});
			}

			public boolean okToClose(ConnectionProfile profile) {
				// not interested to this event.
				return true;
			}

			public void opened(ConnectionProfile profile) {
				getCombo().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						TableCombo.this.populateTableCombo();
					}
				});
			}

			public void databaseChanged(ConnectionProfile profile, final Database database) {
				getControl().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (database == TableCombo.this.getDatabase()) {
							if (!getControl().isDisposed()) {
								TableCombo.this.populateTableCombo();
							}
						}
					}
				});
			}

			public void schemaChanged(ConnectionProfile profile, final Schema schema) {
				getControl().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (schema == TableCombo.this.getTableSchema()) {
							if (!getControl().isDisposed()) {
								TableCombo.this.populateTableCombo();
							}
						}
					}
				});
			}

			public void tableChanged(ConnectionProfile profile, final Table table) {
			// not interested to this event.
			}
		};
	}

	@Override
	protected void buildWidget(Composite parent) {
		this.combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
		this.combo.add(JptUiMappingsMessages.TableComposite_defaultEmpty);
		this.combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String tableText = ((CCombo) e.getSource()).getText();
				if (tableText.equals("")) { //$NON-NLS-1$
					tableText = null;
					if (subject().getSpecifiedName() == null || subject().getSpecifiedName().equals("")) { //$NON-NLS-1$
						return;
					}
				}
				if (tableText != null && combo.getItemCount() > 0 && tableText.equals(combo.getItem(0))) {
					tableText = null;
				}
				if (subject().getSpecifiedName() == null && tableText != null) {
					subject().setSpecifiedName(tableText);
				}
				if (subject().getSpecifiedName() != null && !subject().getSpecifiedName().equals(tableText)) {
					subject().setSpecifiedName(tableText);
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

	@Override
	public void doPopulate() {
		if (this.subject() != null) {
			populateTableCombo();
		}
		else {
			this.connectionProfile = null;
		}
	}

	protected Database getDatabase() {
		return getConnectionProfile().getDatabase();
	}

	protected Schema getTableSchema() {
		return getConnectionProfile().getDatabase().schemaNamed(subject().getSchema());
	}

	private ConnectionProfile getConnectionProfile() {
		if (this.connectionProfile == null) {
			this.connectionProfile = this.subject().getJpaProject().connectionProfile();
		}
		return this.connectionProfile;
	}

	private void populateTableCombo() {
		if (this.subject() == null) {
			return;
		}
		// TODO don't do instanceof check here - check on Table, or isRoot check
		// on Entity
		// this.tableCombo.setEnabled(!(this.table instanceof
		// SingleTableInheritanceChildTableImpl));
		populateDefaultTableName();
		if (getConnectionProfile().isConnected()) {
			this.combo.remove(1, this.combo.getItemCount() - 1);
			Schema schema = this.getTableSchema();
			if (schema != null) {
				Iterator<String> tables = schema.tableNames();
				for (Iterator<String> stream = CollectionTools.sort(tables); stream.hasNext();) {
					this.combo.add(stream.next());
				}
			}
		}
		else {
			this.combo.remove(1, this.combo.getItemCount() - 1);
		}
		populateTableName();
	}

	protected void populateDefaultTableName() {
		if (this.subject() == null) {
			return;
		}
		String defaultTableName = subject().getDefaultName();
		int selectionIndex = combo.getSelectionIndex();
		combo.setItem(0, NLS.bind(JptUiMappingsMessages.TableComposite_defaultWithOneParam, defaultTableName));
		if (selectionIndex == 0) {
			// combo text does not update when switching between 2 mappings of
			// the same type
			// that both have a default column name. clear the selection and
			// then set it again
			combo.clearSelection();
			combo.select(0);
		}
	}

	protected void populateTableName() {
		if (this.subject() == null) {
			return;
		}
		String tableName = this.subject().getSpecifiedName();
		String defaultTableName = this.subject().getDefaultName();
		if (tableName != null) {
			if (!this.combo.getText().equals(tableName)) {
				this.combo.setText(tableName);
			}
		}
		else {
			if (!this.combo.getText().equals(NLS.bind(JptUiMappingsMessages.TableComposite_defaultWithOneParam, defaultTableName))) {
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
		if (this.subject() != null) {
			this.removeConnectionListener();
			this.subject().eAdapters().remove(this.listener);
		}
	}

	@Override
	protected void engageListeners() {
		if (this.subject() != null) {
			this.subject().eAdapters().add(this.listener);
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