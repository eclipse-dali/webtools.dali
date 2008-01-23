/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class CatalogCombo extends BaseJpaController<ITable>
{
	private CCombo combo;

	private ConnectionListener connectionListener;

	/**
	 * Caching the connectionProfile so we can remove the listener. If the
	 * cached table object has been removed from the model then we no longer
	 * have access to the parent and cannot find the connectionProfile
	 */
	private ConnectionProfile connectionProfile;

	public CatalogCombo(BaseJpaController<? extends ITable> parentController,
	                    Composite parent) {

		super(parentController, parent);
	}

	public CatalogCombo(PropertyValueModel<? extends ITable> subjectHolder,
	                    Composite parent,
	                    TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
		this.connectionListener = this.buildConnectionListener();
	}

	private void addConnectionListener() {
		this.getConnectionProfile().addConnectionListener(this.connectionListener);
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
						CatalogCombo.this.populateCatalogCombo();
					}
				});
			}

			public void databaseChanged(ConnectionProfile profile, final Database database) {
				getControl().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (database == CatalogCombo.this.getDatabase()) {
							if (!getControl().isDisposed()) {
								CatalogCombo.this.populateCatalogCombo();
							}
						}
					}
				});
			}

			public void modified(ConnectionProfile profile) {
				getCombo().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						CatalogCombo.this.populateCatalogCombo();
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
						CatalogCombo.this.populateCatalogCombo();
					}
				});
			}

			public void schemaChanged(ConnectionProfile profile, final Schema schema) {
			// not interested to this event.
			}

			public void tableChanged(ConnectionProfile profile, final Table table) {
			// not interested to this event.
			}
		};
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
	public void doPopulate() {
		super.doPopulate();
		if (this.subject() != null) {
			this.populateCatalogCombo();
		}
		else {
			this.connectionProfile = null;
		}
	}

//	public void doPopulate() {
//		this.populateCatalogCombo();
//	}

	@Override
	protected void engageListeners() {
		super.engageListeners();
//		if (this.subject() != null) {
//			this.subject().eAdapters().add(this.listener);
//			this.addConnectionListener();
//		}
	}

	public CCombo getCombo() {
		return this.combo;
	}

	private ConnectionProfile getConnectionProfile() {
		if (this.connectionProfile == null) {
			this.connectionProfile = this.subject().jpaProject().connectionProfile();
		}
		return this.connectionProfile;
	}

	protected Database getDatabase() {
		return this.getConnectionProfile().getDatabase();
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.combo = buildCombo(container); // TODO: Was using SWT.READ_ONLY
		this.combo.add(JptUiMappingsMessages.TableComposite_defaultEmpty);
		this.combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String catalogText = ((CCombo) e.getSource()).getText();
				if (catalogText.equals("")) { //$NON-NLS-1$
					catalogText = null;
					if (CatalogCombo.this.subject().getSpecifiedCatalog() == null || CatalogCombo.this.subject().getSpecifiedCatalog().equals("")) { //$NON-NLS-1$
						return;
					}
				}
				if (catalogText != null && combo.getItemCount() > 0 && catalogText.equals(combo.getItem(0))) {
					catalogText = null;
				}
				if (CatalogCombo.this.subject().getSpecifiedCatalog() == null && catalogText != null) {
					CatalogCombo.this.setSpecifiedCatalog(catalogText);
				}
				if (CatalogCombo.this.subject().getSpecifiedCatalog() != null && !CatalogCombo.this.subject().getSpecifiedCatalog().equals(catalogText)) {
					CatalogCombo.this.setSpecifiedCatalog(catalogText);
				}
			}
		});
	}

	private void populateCatalogCombo() {
		if (this.subject() == null) {
			return;
		}
		this.populateDefaultCatalogName();
		if (this.getConnectionProfile().isConnected()) {
			this.combo.remove(1, this.combo.getItemCount() - 1);
			Database database = this.getDatabase();
			if (database != null) {
				Iterator<String> catalogs = database.catalogNames();
				for (Iterator<String> stream = CollectionTools.sort(catalogs); stream.hasNext();) {
					this.combo.add(stream.next());
				}
			}
		}
		else {
			this.combo.remove(1, this.combo.getItemCount() - 1);
		}
		this.populateCatalogName();
	}

	protected void populateCatalogName() {
		if (this.subject() == null) {
			return;
		}
		String catalogName = this.subject().getSpecifiedCatalog();
		// String defaultCatalogName = this.subject().getDefaultCatalog(); //
		// DefaultCatalog cannot be initialized if DB not online
		String defaultCatalogName = this.getDatabase().getDefaultCatalogName(); // TOREVIEW
		if (!StringTools.stringIsEmpty(catalogName)) {
			if (!this.combo.getText().equals(catalogName)) {
				this.combo.setText(catalogName);
			}
		}
		else {
			if (!this.combo.getText().equals(NLS.bind(JptUiMappingsMessages.TableComposite_defaultWithOneParam, defaultCatalogName))) {
				this.combo.select(0);
			}
		}
	}

	protected void populateDefaultCatalogName() {
		// String defaultCatalogName = this.subject().getDefaultCatalog(); //
		// DefaultCatalog cannot be initialized if DB not online
		String defaultCatalogName = this.getDatabase().getDefaultCatalogName(); // TOREVIEW
		int selectionIndex = combo.getSelectionIndex();
		combo.setItem(0, NLS.bind(JptUiMappingsMessages.TableComposite_defaultWithOneParam, defaultCatalogName));
		if (selectionIndex == 0) {
			combo.clearSelection();
			combo.select(0);
		}
	}

	private void removeConnectionListener() {
		this.getConnectionProfile().removeConnectionListener(this.connectionListener);
	}

	private void setSpecifiedCatalog(String catalogName) {
		this.subject().setSpecifiedCatalog(catalogName);
		this.getConnectionProfile().setCatalogName(catalogName);
	}
}