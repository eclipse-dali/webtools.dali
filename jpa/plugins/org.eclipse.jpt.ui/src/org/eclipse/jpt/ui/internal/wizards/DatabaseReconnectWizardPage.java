/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.ConnectionProfileRepository;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.JptDbPlugin;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.db.ui.internal.DTPUiTools;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;

public class DatabaseReconnectWizardPage extends WizardPage {
	private IJpaProject jpaProject;

	private ConnectionProfile profile;
	private ConnectionListener connectionListener;

	public DatabaseReconnectWizardPage( IJpaProject jpaProject) {
		super( "Database Settings"); //$NON-NLS-1$
		this.jpaProject = jpaProject;
		setTitle( JptUiMessages.DatabaseReconnectWizardPage_databaseConnection);
		setMessage( JptUiMessages.DatabaseReconnectWizardPage_reconnectToDatabase);
	}

	public void createControl( Composite parent) {
		this.setPageComplete( false);
		Composite top = this.createTopLevelComposite( parent);
		this.setControl( top);
	}

	protected Composite createTopLevelComposite( Composite parent) {
		Composite composite = new Composite( parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		composite.setLayout( layout);
		new DatabaseGroup( composite);
		Dialog.applyDialogFont( parent);
		// TODO Add Help - testing
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IJpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE_CONNECTION);
		return composite;
	}

	private Label createLabel( Composite container, int span, String text) {
		Label label = new Label( container, SWT.NONE);
		label.setText( text);
		GridData gd = new GridData();
		gd.horizontalIndent = 30;
		gd.horizontalSpan = span;
		label.setLayoutData( gd);
		return label;
	}

	private Combo createCombo( Composite container, boolean fillHorizontal) {
		Combo combo = new Combo( container, SWT.BORDER | SWT.SINGLE);
		if ( fillHorizontal) {
			combo.setLayoutData( new GridData( GridData.FILL_HORIZONTAL));
		}
		else {
			combo.setLayoutData( new GridData());
		}
		return combo;
	}

	/**
	 * Initialize a grid layout with the default Dialog settings.
	 */
	protected GridLayout initGridLayout( GridLayout layout, boolean margins) {
		layout.horizontalSpacing = this.convertHorizontalDLUsToPixels( IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = this.convertVerticalDLUsToPixels( IDialogConstants.VERTICAL_SPACING);
		if ( margins) {
			layout.marginWidth = this.convertHorizontalDLUsToPixels( IDialogConstants.HORIZONTAL_MARGIN);
			layout.marginHeight = this.convertVerticalDLUsToPixels( IDialogConstants.VERTICAL_MARGIN);
		}
		else {
			layout.marginWidth = 0;
			layout.marginHeight = 0;
		}
		return layout;
	}

	public Collection<Table> getTables() {
		Schema schema = this.getDefaultSchema();
		if ( schema != null && schema.getName() != null) {
			return CollectionTools.collection( schema.tables());
		}
		return Collections.<Table>emptyList();
	}

	private void updateGenerateEntitiesPage( Schema schema) {
		GenerateEntitiesWizard generateEntitiesWizard = (( GenerateEntitiesWizard) this.getWizard());
		generateEntitiesWizard.updatePossibleTables( CollectionTools.collection( schema.tables()));
	}

	public void dispose() {
		this.removeConnectionListener();
		super.dispose();
	}

	private void removeConnectionListener() {
		if ( this.connectionListener != null) {
			if ( this.profile != null) {
				this.profile.removeConnectionListener( this.connectionListener);
			}
			this.connectionListener = null;
		}
	}
	

	ConnectionProfile getProjectConnectionProfile() {
		String profileName = this.jpaProject.getDataSource().getConnectionProfileName();
		return JptDbPlugin.getDefault().getConnectionProfileRepository().profileNamed( profileName);
	}

	Schema getDefaultSchema() {
		ConnectionProfile profile = getProjectConnectionProfile();
		return profile.getDatabase().schemaNamed( profile.getDefaultSchema());
	}

	// ********** member classes **********

	private final class DatabaseGroup {
		private final Group group;
		private final Combo connectionCombo;
		private final Combo schemaCombo;
		
		private Link addConnectionLink;
		private Link reconnectLink;

		public DatabaseGroup( Composite composite) {
			this.group = new Group( composite, SWT.NONE);
			GridLayout layout = new GridLayout();
			layout.numColumns = 2;
			this.group.setLayout( layout);
			this.group.setLayoutData( new GridData( GridData.FILL_HORIZONTAL));
			this.group.setText( JptUiMessages.DatabaseReconnectWizardPage_database);
			//TODO Add Help
//			PlatformUI.getWorkbench().getHelpSystem().setHelp( this.group, IDaliHelpContextIds.XXX);
			createLabel( this.group, 1, JptUiMessages.DatabaseReconnectWizardPage_connection);
			this.connectionCombo = createCombo( this.group, true);
			this.connectionCombo.addSelectionListener( new SelectionAdapter() {
				public void widgetDefaultSelected( SelectionEvent e) {
					widgetSelected( e);
				}

				public void widgetSelected( SelectionEvent e) {
					handleConnectionChange();
				}
			});
			createLabel( this.group, 1, JptUiMessages.DatabaseReconnectWizardPage_schema);
			this.schemaCombo = createCombo( this.group, true);
			this.schemaCombo.addSelectionListener( new SelectionAdapter() {
				public void widgetDefaultSelected( SelectionEvent e) {
					widgetSelected( e);
				}

				public void widgetSelected( SelectionEvent e) {
					handleSchemaChange();
				}
			});
			createLabel( this.group, 2, JptUiMessages.DatabaseReconnectWizardPage_schemaInfo);
			this.addConnectionLink = new Link( this.group, SWT.NONE);
			GridData data = new GridData( GridData.END, GridData.CENTER, false, false);
			data.horizontalSpan = 2;
			this.addConnectionLink.setLayoutData( data);
			this.addConnectionLink.setText( JptUiMessages.DatabaseReconnectWizardPage_addConnectionLink);
			this.addConnectionLink.addSelectionListener( new SelectionAdapter() {
				public void widgetSelected( SelectionEvent e) {
					openNewConnectionWizard();
				}
			});
			this.reconnectLink = new Link( this.group, SWT.NONE);
			data = new GridData( GridData.END, GridData.CENTER, false, false);
			data.horizontalSpan = 2;
			this.reconnectLink.setLayoutData( data);
			this.reconnectLink.setText( JptUiMessages.DatabaseReconnectWizardPage_reconnectLink);
			this.reconnectLink.setEnabled( false);
			this.reconnectLink.addSelectionListener( new SelectionAdapter() {
				public void widgetSelected( SelectionEvent e) {
					openConnectionProfileNamed( connectionCombo.getText());
				}
			});
			this.populateConnectionCombo();
			this.populateSchemaCombo();
		}

		private ConnectionProfile getConnectionProfileNamed( String profileName) {
			return JptDbPlugin.getDefault().getConnectionProfileRepository().profileNamed( profileName);
		}
		
		private Iterator<String> dtpConnectionProfileNames() {
			return JptDbPlugin.getDefault().getConnectionProfileRepository().profileNames();
		}

		private String getProjectConnectionProfileName() {
			return jpaProject.getDataSource().getConnectionProfileName();
		}
		
		Schema getDefaultSchema() {
			ConnectionProfile profile = getProjectConnectionProfile();
			return profile.getDatabase().schemaNamed( profile.getDefaultSchema());
		}

		private void openConnectionProfileNamed( String connectionProfileName) {
			DatabaseReconnectWizardPage.this.removeConnectionListener();

			DatabaseReconnectWizardPage.this.profile = JptDbPlugin.getDefault().getConnectionProfileRepository().profileNamed( connectionProfileName);
			DatabaseReconnectWizardPage.this.profile.connect();
			if( DatabaseReconnectWizardPage.this.profile.isConnected()) {
				this.populateSchemaCombo();
				DatabaseReconnectWizardPage.this.connectionListener = this.buildConnectionListener();
				DatabaseReconnectWizardPage.this.profile.addConnectionListener( DatabaseReconnectWizardPage.this.connectionListener);
			}
			return;
		}
		
		private void populateConnectionCombo() {
			// clear out connection entries from previous login.
			this.connectionCombo.removeAll();
			for ( Iterator<String> i = CollectionTools.sort( this.dtpConnectionProfileNames()); i.hasNext();) {
				this.connectionCombo.add( ( String) i.next());
			}

			String connectionName = getProjectConnectionProfileName();
			if ( !StringTools.stringIsEmpty( connectionName)) {
				this.connectionCombo.select( connectionCombo.indexOf( connectionName));
				this.reconnectLink.setEnabled( true);
			}
		}

		private void handleConnectionChange() {
			this.reconnectLink.setEnabled( true);
			this.populateSchemaCombo();
		}

		private void handleSchemaChange() {
			ConnectionProfile connectionProfile = this.getConnectionProfileNamed( getConnectionProfileName());
			Schema schema =  connectionProfile.getDatabase().schemaNamed( this.getSchemaName());
			DatabaseReconnectWizardPage.this.updateGenerateEntitiesPage( schema);
			DatabaseReconnectWizardPage.this.setPageComplete( true);
		}

		private void populateSchemaCombo() {
			// clear out schema entries from previous connection selection
			this.schemaCombo.removeAll();
			ConnectionProfile connectionProfile = this.getConnectionProfileNamed( getConnectionProfileName());
			for ( Iterator<String> stream = CollectionTools.sort( connectionProfile.getDatabase().schemaNames()); stream.hasNext();) {
				this.schemaCombo.add( ( String) stream.next());
			}
			// set login user name as default schema
			Schema schema = this.getDefaultSchema();
			if ( schema != null && schema.getName() != null) {
				schema =  connectionProfile.getDatabase().schemaNamed( schema.getName()); // verify schema exist
				if ( schema != null) {
					this.schemaCombo.select( this.schemaCombo.indexOf( schema.getName()));
					updateGenerateEntitiesPage( schema);
					setPageComplete( true);
				}
			}
		}

		private String getConnectionProfileName() {
			return this.connectionCombo.getText();
		}

		private String getSchemaName() {
			return this.schemaCombo.getText();
		}

		private void openNewConnectionWizard() {
			String addedProfileName = DTPUiTools.createNewProfile();
			  
			ConnectionProfile addedProfile = ConnectionProfileRepository.instance().profileNamed( addedProfileName);
		
			if( !addedProfile.isNull()) {
				addedProfile.connect();
				this.populateConnectionCombo();
				this.connectionCombo.select( connectionCombo.indexOf( addedProfile.getName()));
				this.handleConnectionChange();
			}
		}

		private ConnectionListener buildConnectionListener() {
			return new ConnectionListener() {

				public void modified( ConnectionProfile profile) {
				// not interested to this event.
				}

				public boolean okToClose( ConnectionProfile profile) {
				// not interested to this event.
					return true;
				}

				public void opened( ConnectionProfile profile) {
					if( DatabaseReconnectWizardPage.this.profile.equals( profile)) {
						DatabaseGroup.this.populateSchemaCombo();
					}
				}

				public void aboutToClose( ConnectionProfile profile) {
					if( DatabaseReconnectWizardPage.this.profile.equals( profile)) {
						DatabaseReconnectWizardPage.this.removeConnectionListener();
					}
				}

				public void closed( ConnectionProfile profile) {
				// not interested to this event.
				}

				public void databaseChanged(ConnectionProfile profile, final Database database) {
				// not interested to this event.
				}

				public void schemaChanged(ConnectionProfile profile, final Schema schema) {
				// not interested to this event.
				}

				public void tableChanged(ConnectionProfile profile, final Table table) {
				// not interested to this event.
				}
			};
		}
	}
}
