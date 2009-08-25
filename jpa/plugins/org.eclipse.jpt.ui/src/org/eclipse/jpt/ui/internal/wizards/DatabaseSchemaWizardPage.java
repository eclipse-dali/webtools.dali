/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import java.util.EventListener;
import java.util.Iterator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.db.ConnectionAdapter;
import org.eclipse.jpt.db.ConnectionListener;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.properties.JpaProjectPropertiesPage;
import org.eclipse.jpt.utility.internal.ListenerList;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * Most of the behavior is in DatabaseGroup....
 * 
 * We open the wizard page with the JPA project's connection profile and
 * default schema selected (if possible), but then the selections are driven
 * by the user. If the user re-selects the JPA project's connection profile,
 * we will pre-select the project's default schema if possible.
 */
public class DatabaseSchemaWizardPage extends WizardPage {

	final JpaProject jpaProject;

	private final ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);

	private DatabaseGroup databaseGroup;


	public DatabaseSchemaWizardPage(JpaProject jpaProject) {
		super("Database Schema"); //$NON-NLS-1$
		if (jpaProject == null) {
			throw new NullPointerException();
		}
		this.jpaProject = jpaProject;
		this.setTitle(JptUiMessages.DatabaseSchemaWizardPage_title);
		this.setMessage(JptUiMessages.DatabaseSchemaWizardPage_desc);
	}

	public void createControl(Composite parent) {
		this.setPageComplete(false);
		this.setControl(this.buildTopLevelControl(parent));
	}

	private Control buildTopLevelControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		this.databaseGroup = new DatabaseGroup(composite);
		Dialog.applyDialogFont(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE_CONNECTION);
		return composite;
	}

	public ConnectionProfile getJpaProjectConnectionProfile() {
		return this.jpaProject.getConnectionProfile();
	}
	
	public Schema getSelectedSchema() {
		return this.databaseGroup.getSelectedSchema();
	}

	@Override
	public void dispose() {
		this.databaseGroup.dispose();
		super.dispose();
	}


	// ********** listeners **********

	public void addListener(Listener listener) {
		this.listenerList.add(listener);
	}

	public void removeListener(Listener listener) {
		this.listenerList.remove(listener);
	}

	void fireSchemaChanged(Schema schema) {
		this.setPageComplete(schema != null);
		for (Listener listener : this.listenerList.getListeners()) {
			listener.selectedSchemaChanged(schema);
		}
	}


	// ********** listener interface **********

	/**
	 * Allows clients to listen for changes to the selected connection profile
	 * and schema.
	 */
	public interface Listener extends EventListener {
		void selectedSchemaChanged(Schema schema);
	}


	// ********** database group **********

	/**
	 * schema combo-box
	 * add project connection link
	 * reconnect link
	 */
	class DatabaseGroup {

		// these are kept in synch with the selection
		private Schema selectedSchema;

		private final Combo schemaComboBox;

		private final Link reconnectLink;

		private Link addJpaProjectConnectionLink;
		
		private final ConnectionListener connectionListener;


		// ********** construction **********

		DatabaseGroup(Composite composite) {
			super();

			Group group = new Group(composite, SWT.NONE);
			group.setLayout(new GridLayout(2, false));  // false = do not make columns equal width
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			group.setText(JptUiMessages.DatabaseSchemaWizardPage_schemaSettings);
			// TODO PlatformUI.getWorkbench().getHelpSystem().setHelp(this.group, JpaHelpContextIds.XXX);

			// schema combo-box
			this.buildLabel(group, 1, JptUiMessages.DatabaseSchemaWizardPage_schema);
			this.schemaComboBox = this.buildComboBox(group, this.buildSchemaComboBoxSelectionListener());
			
			String message = (this.projectHasAConnection()) ?
				JptUiMessages.DatabaseSchemaWizardPage_schemaInfo :
				JptUiMessages.DatabaseSchemaWizardPage_connectionInfo;
			
			this.buildLabel(group, 2, message);

			// add project's connection link
			if( ! this.projectHasAConnection()) {
				this.addJpaProjectConnectionLink = this.buildLink(group, 
							JptUiMessages.DatabaseSchemaWizardPage_addConnectionToProject, 
							this.buildAddJpaProjectConnectionLinkListener());
			}

			// reconnect link
			this.reconnectLink = this.buildLink(group, JptUiMessages.DatabaseSchemaWizardPage_connectLink, this.buildReconnectLinkSelectionListener());
			this.reconnectLink.setEnabled(true);

			this.selectedSchema = this.getDefaultSchema();

			if (this.selectedSchema != null) {
				DatabaseSchemaWizardPage.this.fireSchemaChanged(this.selectedSchema);
			}

			this.connectionListener = this.buildConnectionListener();
			this.addJpaProjectConnectionProfileListener(this.connectionListener);

			this.updateSchemaComboBox();
			this.updateReconnectLink();
		}


		// ********** intra-wizard methods **********

		Schema getSelectedSchema() {
			return this.selectedSchema;
		}

		void dispose() {
			if(this.projectHasAConnection()) {
				this.getJpaProjectConnectionProfile().removeConnectionListener(this.connectionListener);
			}
		}


		// ********** internal methods **********

		private void addJpaProjectConnectionListener() {
			this.addJpaProjectConnectionProfileListener(this.connectionListener);
		}
		
		
		private void addJpaProjectConnectionProfileListener(ConnectionListener listener) {
			if(this.projectHasAConnection()) {
				this.getJpaProjectConnectionProfile().addConnectionListener(listener);
			}
		}

		private boolean projectHasAConnection() {
			return this.getJpaProjectConnectionProfile() != null;
		}
		
		/**
		 * this can return null;
		 * called at start-up and when the selected connection profile changes
		 */
		private ConnectionProfile getJpaProjectConnectionProfile() {
			return DatabaseSchemaWizardPage.this.jpaProject.getConnectionProfile();
		}

		/**
		 * this can return null;
		 * called at start-up and when the selected connection profile changes
		 */
		private Schema getDefaultSchema() {
			return DatabaseSchemaWizardPage.this.jpaProject.getDefaultDbSchema();
		}

		private SchemaContainer getDefaultSchemaContainer() {
			return DatabaseSchemaWizardPage.this.jpaProject.getDefaultDbSchemaContainer();
		}

		/**
		 * called at start-up and when the selected connection profile changes
		 */
		private void updateReconnectLink() {
			this.reconnectLink.setEnabled(this.reconnectLinkCanBeEnabled());
		}

		private void updateAddJpaProjectConnectionLink() {
			this.addJpaProjectConnectionLink.setEnabled(this.addJpaProjectConnectionLinkCanBeEnabled());
		}

		private boolean reconnectLinkCanBeEnabled() {
			if(this.projectHasAConnection()) {
				return this.getJpaProjectConnectionProfile().isInactive();
			}
			return false;
		}

		private boolean addJpaProjectConnectionLinkCanBeEnabled() {
			return ! this.projectHasAConnection();
		}

		/**
		 * the schema combo-box is updated at start-up and
		 * when the selected connection profile changes
		 */
		private void updateSchemaComboBox() {
			this.schemaComboBox.removeAll();
			for (Iterator<String> stream = this.getSchemata(); stream.hasNext(); ) {
				this.schemaComboBox.add(stream.next());
			}
			// the current schema *should* be in the current connection profile
			if (this.selectedSchema != null) {
				this.schemaComboBox.select(this.schemaComboBox.indexOf(this.selectedSchema.getIdentifier()));
			}
		}

		private Iterator<String> getSchemata() {
			SchemaContainer sc = this.getDefaultSchemaContainer();
			return (sc == null) ? EmptyIterator.<String>instance() : sc.sortedSchemaIdentifiers();
		}

		// ********** listener callbacks **********

		void selectedSchemaChanged() {
			Schema old = this.selectedSchema;
			this.selectedSchema = this.getDefaultSchemaContainer().getSchemaNamed(this.schemaComboBox.getText());
			if (this.selectedSchema != old) {
				DatabaseSchemaWizardPage.this.fireSchemaChanged(this.selectedSchema);
			}
		}

		void reconnect() {
			this.getJpaProjectConnectionProfile().connect();
			// everything should be synchronized when we get the resulting open event
		}

		/**
		 * called when
		 *     - a connection is set to the Jpa project
		 *     - the connection was opened
		 *     - the connection was closed (never happens?)
		 * we need to update the schema stuff and the reconnect link
		 */
		void connectionChanged() {
			Schema old = this.selectedSchema;
			this.selectedSchema = this.getDefaultSchema();
			if (this.selectedSchema != old) {
				DatabaseSchemaWizardPage.this.fireSchemaChanged(this.selectedSchema);
			}
			this.updateSchemaComboBox();
			this.updateReconnectLink();
		}


		// ********** listeners **********

		private SelectionListener buildSchemaComboBoxSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					// nothing special for "default" (double-click?)
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					DatabaseGroup.this.selectedSchemaChanged();
				}
				@Override
				public String toString() {
					return "DatabaseSchemaWizardPage schema combo-box selection listener"; //$NON-NLS-1$
				}
			};
		}

		private SelectionListener buildReconnectLinkSelectionListener() {
			return new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					DatabaseGroup.this.reconnect();
				}
				@Override
				public String toString() {
					return "DatabaseSchemaWizardPage reconnect link selection listener"; //$NON-NLS-1$
				}
			};
		}

		private ConnectionListener buildConnectionListener() {
			return new ConnectionAdapter() {
				@Override
				public void opened(ConnectionProfile cp) {
					this.connectionChanged();
				}
				@Override  // this probably won't ever get called...
				public void closed(ConnectionProfile cp) {
					this.connectionChanged();
				}
				private void connectionChanged() {
					Display.getDefault().asyncExec(
						new Runnable() {
							public void run() {
								DatabaseGroup.this.connectionChanged();
							}
						}
					);
				}
				@Override
				public String toString() {
					return "DatabaseSchemaWizardPage connection listener"; //$NON-NLS-1$
				}
			};
		}

		private SelectionListener buildAddJpaProjectConnectionLinkListener() {
			return new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					DatabaseGroup.this.promptToConfigJpaProjectConnection();
					
					DatabaseGroup.this.addJpaProjectConnectionListener();
					DatabaseGroup.this.updateAddJpaProjectConnectionLink();
					DatabaseGroup.this.connectionChanged();
				}
				@Override
				public String toString() {
					return "DatabaseSchemaWizardPage AddProjectConnection link selection listener"; //$NON-NLS-1$
				}
			};
		}

		private void promptToConfigJpaProjectConnection() {
			PreferenceDialog dialog =
				PreferencesUtil.createPropertyDialogOn(
					getShell(), DatabaseSchemaWizardPage.this.jpaProject.getProject(),
					JpaProjectPropertiesPage.PROP_ID,
					null,
					null);
			dialog.open();
		}

		// ********** UI components **********

		/**
		 * build and return a label
		 */
		private Label buildLabel(Composite parent, int span, String text) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(text);
			GridData gd = new GridData();
			gd.horizontalSpan = span;
			label.setLayoutData(gd);
			return label;
		}

		/**
		 * build and return a combo-box
		 */
		private Combo buildComboBox(Composite parent, SelectionListener listener) {
			Combo combo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
			combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			combo.addSelectionListener(listener);
			return combo;
		}

		/**
		 * build and return a link
		 */
		private Link buildLink(Composite parent, String text, SelectionListener listener) {
			Link link = new Link(parent, SWT.NONE);
			GridData data = new GridData(GridData.END, GridData.CENTER, false, false);
			data.horizontalSpan = 2;
			link.setLayoutData(data);
			link.setText(text);
			link.addSelectionListener(listener);
			return link;
		}

	}

}
