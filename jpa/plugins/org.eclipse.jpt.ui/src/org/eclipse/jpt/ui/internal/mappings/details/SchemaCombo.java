/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

public class SchemaCombo extends AbstractFormPane<ITable>
{
	/**
	 * Caching the connectionProfile so we can remove the listener. If the
	 * cached table object has been removed from the model then we no longer
	 * have access to the parent and cannot find the connectionProfile
	 */
	private ConnectionProfile connectionProfile;

	private ConnectionListener connectionListener;

	private CCombo combo;

	/**
	 * Creates a new <code>SchemaCombo</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public SchemaCombo(AbstractFormPane<? extends ITable> parentPane,
                      Composite parent) {

		super(parentPane, parent);
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
						SchemaCombo.this.populateShemaCombo();
					}
				});
			}

			public void modified(ConnectionProfile profile) {
				getCombo().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						SchemaCombo.this.populateShemaCombo();
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
						SchemaCombo.this.populateShemaCombo();
					}
				});
			}

			public void databaseChanged(ConnectionProfile profile, final Database database) {
				getControl().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (database == SchemaCombo.this.getDatabase()) {
							if (!getControl().isDisposed()) {
								SchemaCombo.this.populateShemaCombo();
							}
						}
					}
				});
			}

			public void schemaChanged(ConnectionProfile profile, final Schema schema) {
				getControl().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (schema == SchemaCombo.this.getTableSchema()) {
							if (!getControl().isDisposed()) {
								SchemaCombo.this.populateShemaCombo();
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
	protected void initializeLayout(Composite container) {
		this.combo = buildCombo(container);
		this.combo.add(JptUiMappingsMessages.TableComposite_defaultEmpty);
		this.combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String schemaText = ((CCombo) e.getSource()).getText();
				if (schemaText.equals("")) { //$NON-NLS-1$
					schemaText = null;
					if (SchemaCombo.this.subject().getSpecifiedSchema() == null || SchemaCombo.this.subject().getSpecifiedSchema().equals("")) { //$NON-NLS-1$
						return;
					}
				}
				if (schemaText != null && combo.getItemCount() > 0 && schemaText.equals(combo.getItem(0))) {
					schemaText = null;
				}
				if (SchemaCombo.this.subject().getSpecifiedSchema() == null && schemaText != null) {
					SchemaCombo.this.subject().setSpecifiedSchema(schemaText);
				}
				if (SchemaCombo.this.subject().getSpecifiedSchema() != null && !SchemaCombo.this.subject().getSpecifiedSchema().equals(schemaText)) {
					SchemaCombo.this.subject().setSpecifiedSchema(schemaText);
				}
			}
		});
	}

	@Override
	public void doPopulate() {
		super.doPopulate();
		if (this.subject() != null) {
			this.populateShemaCombo();
		}
		else {
			this.connectionProfile = null;
		}
	}

	protected Database getDatabase() {
		return this.getConnectionProfile().getDatabase();
	}

	private ConnectionProfile getConnectionProfile() {
		if (this.connectionProfile == null) {
			this.connectionProfile = this.subject().jpaProject().connectionProfile();
		}
		return this.connectionProfile;
	}

	private void populateShemaCombo() {
		if (this.subject() == null) {
			return;
		}
		this.populateDefaultSchemaName();
		if (this.getConnectionProfile().isConnected()) {
			this.combo.remove(1, this.combo.getItemCount() - 1);
			Database database = this.getDatabase();
			if (database != null) {
				Iterator<String> schemata = database.schemaNames();
				for (Iterator<String> stream = CollectionTools.sort(schemata); stream.hasNext();) {
					this.combo.add(stream.next());
				}
			}
		}
		else {
			this.combo.remove(1, this.combo.getItemCount() - 1);
		}
		this.populateSchemaName();
	}

	protected void populateDefaultSchemaName() {
		String defaultSchemaName = this.subject().getDefaultSchema();
		int selectionIndex = combo.getSelectionIndex();
		combo.setItem(0, NLS.bind(JptUiMappingsMessages.TableComposite_defaultWithOneParam, defaultSchemaName));
		if (selectionIndex == 0) {
			combo.clearSelection();
			combo.select(0);
		}
	}

	protected void populateSchemaName() {
		if (this.subject() == null) {
			return;
		}
		String schemaName = this.subject().getSpecifiedSchema();
		String defaultSchemaName = this.subject().getDefaultSchema();
		if (!StringTools.stringIsEmpty(schemaName)) {
			if (!this.combo.getText().equals(schemaName)) {
				this.combo.setText(schemaName);
			}
		}
		else {
			if (!this.combo.getText().equals(NLS.bind(JptUiMappingsMessages.TableComposite_defaultWithOneParam, defaultSchemaName))) {
				this.combo.select(0);
			}
		}
	}

	public CCombo getCombo() {
		return this.combo;
	}

	protected Schema getTableSchema() {
		return this.getConnectionProfile().getDatabase().schemaNamed(subject().getSchema());
	}

	@Override
	protected void disengageListeners() {
		super.disengageListeners();
//		if (this.subject() != null) {
//			this.removeConnectionListener();
//			this.subject().eAdapters().remove(this.listener);
//		}
	}

	@Override
	protected void engageListeners() {
		super.engageListeners();
//		if (this.subject() != null) {
//			this.subject().eAdapters().add(this.listener);
//			this.addConnectionListener();
//		}
	}

	private void addConnectionListener() {
		this.getConnectionProfile().addConnectionListener(this.connectionListener);
	}

	private void removeConnectionListener() {
		this.getConnectionProfile().removeConnectionListener(this.connectionListener);
	}
}