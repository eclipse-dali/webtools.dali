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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.db.internal.Connection;
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

	private Connection connection;
	private ConnectionListener connectionListener;
	private DatabaseGroup databaseGroup;

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
		this.databaseGroup = new DatabaseGroup( composite);
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

	public Collection getTables() {
		Schema schema = this.getProjectUserSchema();
		if ( schema != null && schema.getName() != null) {
			return CollectionTools.collection( schema.tables());
		}
		return new ArrayList();
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
			if ( this.connection != null) {
				this.connection.removeConnectionListener( this.connectionListener);
			}
			this.connectionListener = null;
		}
	}
	

	ConnectionProfile getProjectConnectionProfile() {
		String profileName = this.jpaProject.getDataSource().getConnectionProfileName();
		return JptDbPlugin.getDefault().getConnectionProfileRepository().profileNamed( profileName);
	}
	
	Schema getProjectUserSchema() {
		ConnectionProfile profile = this.getProjectConnectionProfile();
		return profile.getDatabase().schemaNamed( profile.getUserName());
	}

	// ********** member classes **********

	private final class DatabaseGroup {
		private final Group group;
		private final Label connectionLabel;
		private final Combo connectionCombo;
		private final Label schemaLabel;
		private final Combo schemaCombo;
		private final Label schemaInfoLabel;
		
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
			this.connectionLabel = createLabel( this.group, 1, JptUiMessages.DatabaseReconnectWizardPage_connection);
			this.connectionCombo = createCombo( this.group, true);
			this.connectionCombo.addSelectionListener( new SelectionAdapter() {
				public void widgetDefaultSelected( SelectionEvent e) {
					widgetSelected( e);
				}

				public void widgetSelected( SelectionEvent e) {
					handleConnectionChange();
				}
			});
			this.schemaLabel = createLabel( this.group, 1, JptUiMessages.DatabaseReconnectWizardPage_schema);
			this.schemaCombo = createCombo( this.group, true);
			this.schemaCombo.addSelectionListener( new SelectionAdapter() {
				public void widgetDefaultSelected( SelectionEvent e) {
					widgetSelected( e);
				}

				public void widgetSelected( SelectionEvent e) {
					handleSchemaChange();
				}
			});
			schemaInfoLabel = createLabel( this.group, 2, JptUiMessages.DatabaseReconnectWizardPage_schemaInfo);
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
		
		private Iterator dtpConnectionProfileNames() {
			return JptDbPlugin.getDefault().getConnectionProfileRepository().profileNames();
		}

		private String getProjectConnectionProfileName() {
			return jpaProject.getDataSource().getConnectionProfileName();
		}
		
		Schema getProjectUserSchema() {
			ConnectionProfile profile = getProjectConnectionProfile();
			return profile.getDatabase().schemaNamed( profile.getUserName());
		}

		private void openConnectionProfileNamed( String connectionProfileName) {
			if( DatabaseReconnectWizardPage.this.connection != null) {
				DatabaseReconnectWizardPage.this.removeConnectionListener();
			}
			ConnectionProfile profile = JptDbPlugin.getDefault().getConnectionProfileRepository().profileNamed( connectionProfileName);
			profile.connect();
			DatabaseReconnectWizardPage.this.connection = profile.getConnection();
			if( DatabaseReconnectWizardPage.this.connection != null) {
				this.populateSchemaCombo();
				DatabaseReconnectWizardPage.this.connectionListener = this.buildConnectionListener();
				DatabaseReconnectWizardPage.this.connection.addConnectionListener( DatabaseReconnectWizardPage.this.connectionListener);
			}
			return;
		}
		
		private void populateConnectionCombo() {
			// clear out connection entries from previous login.
			this.connectionCombo.removeAll();
			for ( Iterator i = CollectionTools.sort( this.dtpConnectionProfileNames()); i.hasNext();) {
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
			for ( Iterator stream = CollectionTools.sort( connectionProfile.getDatabase().schemaNames()); stream.hasNext();) {
				this.schemaCombo.add( ( String) stream.next());
			}
			// set login user name as default schema
			Schema schema = this.getProjectUserSchema();
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
				this.populateConnectionCombo();
				this.connectionCombo.select( connectionCombo.indexOf( addedProfile.getName()));
				this.handleConnectionChange();
			}
		}

		private ConnectionListener buildConnectionListener() {
			return new ConnectionListener() {

				public void modified( Connection connection) {
				// not interested to this event.
				}

				public boolean okToClose( Connection connection) {
				// not interested to this event.
					return true;
				}

				public void opened( Connection connection) {
					if( DatabaseReconnectWizardPage.this.connection.equals( connection)) {
						DatabaseGroup.this.populateSchemaCombo();
					}
				}

				public void aboutToClose( Connection connection) {
					if( DatabaseReconnectWizardPage.this.connection.equals( connection)) {
						DatabaseReconnectWizardPage.this.removeConnectionListener();
					}
				}

				public void closed( Connection connection) {
				// not interested to this event.
				}

				public void databaseChanged(Connection connection, final Database database) {
				// not interested to this event.
				}

				public void schemaChanged(Connection connection, final Schema schema) {
				// not interested to this event.
				}

				public void tableChanged(Connection connection, final Table table) {
				// not interested to this event.
				}
			};
		}
	}
}
