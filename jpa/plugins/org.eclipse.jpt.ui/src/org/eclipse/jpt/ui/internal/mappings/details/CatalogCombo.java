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
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class CatalogCombo extends BaseJpaController
{
	private ITable table;
	private Adapter listener;
	private ConnectionListener connectionListener;
	
	private CCombo combo;
	
	public CatalogCombo( Composite parent, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, theCommandStack, widgetFactory);
		this.listener = this.buildTableListener();
		this.connectionListener = this.buildConnectionListener();
	}
	
	private Adapter buildTableListener() {
		return new AdapterImpl() {
			public void notifyChanged( Notification notification) {
				CatalogCombo.this.catalogChanged( notification);
			}
		};
	}

    private ConnectionListener buildConnectionListener() {
		return new ConnectionListener() {

			public void aboutToClose( Connection connection) {
				// not interested to this event.
			}

			public void closed( Connection connection) {
				getCombo().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if ( getControl().isDisposed()) {
							return;
						}
						CatalogCombo.this.populateCatalogCombo();
					}
				});
			}

			public void modified( Connection connection) {
				getCombo().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if ( getControl().isDisposed()) {
							return;
						}
						CatalogCombo.this.populateCatalogCombo();
					}
				});
			}

			public boolean okToClose( Connection connection) {
				// not interested to this event.
				return true;
			}

			public void opened( Connection connection) {
				getCombo().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if ( getControl().isDisposed()) {
							return;
						}
						CatalogCombo.this.populateCatalogCombo();
					}
				});
			}

			public void databaseChanged( Connection connection, final Database database) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if( database == CatalogCombo.this.getDatabase()) {
							if ( !getControl().isDisposed()) {
								CatalogCombo.this.populateCatalogCombo();
							}
						}
					}
				});
			}
			
			public void schemaChanged( Connection connection, final Schema schema) {
				// not interested to this event.
			}

			public void tableChanged( Connection connection, final Table table) {
				// not interested to this event.
			}
		};
    }
    
	@Override
	protected void buildWidget( Composite parent) {
		this.combo = getWidgetFactory().createCCombo( parent, SWT.FLAT);
		this.combo.add( JpaUiMappingsMessages.TableComposite_defaultEmpty);
		
		this.combo.addModifyListener( new ModifyListener() {
			
			public void modifyText( ModifyEvent e) {
				if ( isPopulating()) {
					return;
				}
				String catalogText = (( CCombo) e.getSource()).getText();
				if ( catalogText.equals("")) { //$NON-NLS-1$
					catalogText = null;
					if ( CatalogCombo.this.table.getSpecifiedCatalog() == null 
						|| CatalogCombo.this.table.getSpecifiedCatalog().equals("")) { //$NON-NLS-1$
						return;
					}
				}
				
				if ( catalogText != null && combo.getItemCount() > 0 && catalogText.equals(combo.getItem(0))) {
					catalogText = null;
				}

				if ( CatalogCombo.this.table.getSpecifiedCatalog() == null && catalogText != null) {
					CatalogCombo.this.setSpecifiedCatalog( catalogText);
				}

				if ( CatalogCombo.this.table.getSpecifiedCatalog() != null 
					&& ! CatalogCombo.this.table.getSpecifiedCatalog().equals( catalogText)) {
					CatalogCombo.this.setSpecifiedCatalog( catalogText);
				}
			}
		});
	}
	
	private void setSpecifiedCatalog( String catalogName) {
		this.table.setSpecifiedCatalog( catalogName);
		this.getConnectionProfile().setCatalogName( catalogName);
	}
	
	protected void catalogChanged( Notification notification) {
		
		if ( notification.getFeatureID( ITable.class) == JpaCoreMappingsPackage.ITABLE__SPECIFIED_CATALOG) {
			Display.getDefault().asyncExec( new Runnable() {
				public void run() {
					if ( getControl().isDisposed()) {
						return;
					}
					CatalogCombo.this.populateCatalogName();
				}
			});
		}
		else if ( notification.getFeatureID( ITable.class) == JpaCoreMappingsPackage.ITABLE__DEFAULT_CATALOG) {
			Display.getDefault().asyncExec( new Runnable() {
				public void run() {
					if ( getControl().isDisposed()) {
						return;
					}
					CatalogCombo.this.populateDefaultCatalogName();
				}
			});
		}
	}
	
	public void doPopulate( EObject obj) {
		this.table = ( ITable) obj;
		this.populateCatalogCombo(); 
	}
	
	public void doPopulate() {
		this.populateCatalogCombo();
	}

	protected Database getDatabase() {
		return this.getConnectionProfile().getDatabase();
	}
	
	private ConnectionProfile getConnectionProfile() {
		return ( this.table != null) ? this.table.getJpaProject().connectionProfile() : null;
	}
	
	private void populateCatalogCombo() {
		if( this.table == null) {
			return;
		}
		this.populateDefaultCatalogName();

		if ( this.getConnectionProfile().isConnected()) {
			this.combo.remove( 1, this.combo.getItemCount() - 1);
			Database database = this.getDatabase();
			
			if ( database != null) {
				Iterator<String> catalogs = database.catalogNames();
				for ( Iterator<String> stream = CollectionTools.sort(catalogs); stream.hasNext(); ) {
					this.combo.add(stream.next());
				}
			}
		}
		else {
			this.combo.remove( 1, this.combo.getItemCount() - 1);
		}
		this.populateCatalogName();
	}
	
	protected void populateDefaultCatalogName() {
//		String defaultCatalogName = this.table.getDefaultCatalog();		// DefaultCatalog cannot be initialized if DB not online
		String defaultCatalogName = this.getDatabase().getDefaultCatalogName();		// TOREVIEW
		int selectionIndex = combo.getSelectionIndex();
		combo.setItem( 0, NLS.bind( JpaUiMappingsMessages.TableComposite_defaultWithOneParam, defaultCatalogName));
		if ( selectionIndex == 0) {
			combo.clearSelection();
			combo.select( 0);
		}		
	}
	
	protected void populateCatalogName() {
		if ( this.table == null) {
			return;
		}
		String catalogName = this.table.getSpecifiedCatalog();
//		String defaultCatalogName = this.table.getDefaultCatalog();		// DefaultCatalog cannot be initialized if DB not online
		String defaultCatalogName = this.getDatabase().getDefaultCatalogName();		// TOREVIEW
		if ( ! StringTools.stringIsEmpty( catalogName)) {
			if ( !this.combo.getText().equals( catalogName)) {
				this.combo.setText( catalogName);
			}
		}
		else {
			if ( ! this.combo.getText().equals( NLS.bind( JpaUiMappingsMessages.TableComposite_defaultWithOneParam, defaultCatalogName))) {
				this.combo.select( 0);
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
			this.table.eAdapters().remove( this.listener);
		}
	}
	
	@Override
	protected void engageListeners() {
		if (this.table != null) {
			this.table.eAdapters().add( this.listener);
			this.addConnectionListener();
		}		
	}
	
	private void addConnectionListener() {
		this.getConnectionProfile().addConnectionListener( this.connectionListener);
	}
	
	private void removeConnectionListener() {
		this.getConnectionProfile().removeConnectionListener( this.connectionListener);
	}
	
}
