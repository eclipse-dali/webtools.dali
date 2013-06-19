/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards;

import java.util.EventListener;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.db.ConnectionAdapter;
import org.eclipse.jpt.jpa.db.ConnectionListener;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.properties.JpaProjectPropertiesPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
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
		this.setTitle(JptJpaUiMessages.DATABASE_SCHEMA_WIZARD_PAGE_TITLE);
		this.setMessage(JptJpaUiMessages.DATABASE_SCHEMA_WIZARD_PAGE_DESC);
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
		WorkbenchTools.setHelp(composite, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE_CONNECTION);
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
		for (Listener listener : this.listenerList) {
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

		// these are kept in sync with the selection
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
			group.setText(JptJpaUiMessages.DATABASE_SCHEMA_WIZARD_PAGE_SCHEMA_SETTINGS);
			// TODO PlatformUI.getWorkbench().getHelpSystem().setHelp(this.group, JpaHelpContextIds.XXX);

			// schema combo-box
			this.buildLabel(group, 1, JptJpaUiMessages.DATABASE_SCHEMA_WIZARD_PAGE_SCHEMA);
			this.schemaComboBox = this.buildComboBox(group, this.buildSchemaComboBoxSelectionListener());
			
			String message = (this.projectHasAConnection()) ?
				JptJpaUiMessages.DATABASE_SCHEMA_WIZARD_PAGE_SCHEMA_INFO :
				JptJpaUiMessages.DATABASE_SCHEMA_WIZARD_PAGE_CONNECTION_INFO;
			
			this.buildLabel(group, 2, message);

			// add project's connection link
			if( ! this.projectHasAConnection()) {
				this.addJpaProjectConnectionLink = this.buildLink(group, 
							JptJpaUiMessages.DATABASE_SCHEMA_WIZARD_PAGE_ADD_CONNECTION_TO_PROJECT, 
							this.buildAddJpaProjectConnectionLinkListener());
			}

			// reconnect link
			this.reconnectLink = this.buildLink(group, JptJpaUiMessages.DATABASE_SCHEMA_WIZARD_PAGE_CONNECT_LINK, this.buildReconnectLinkSelectionListener());
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

		void addJpaProjectConnectionListener() {
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

		void updateAddJpaProjectConnectionLink() {
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
			for (String name : this.getSchemaNames()) {
				this.schemaComboBox.add(name);
			}
			// the current schema *should* be in the current connection profile
			if (this.selectedSchema != null) {
				this.schemaComboBox.select(this.schemaComboBox.indexOf(this.selectedSchema.getName()));
			}
		}

		private Iterable<String> getSchemaNames() {
			SchemaContainer sc = this.getDefaultSchemaContainer();
			// use schema *names* since the combo-box is read-only
			return (sc != null) ? sc.getSortedSchemaNames() : EmptyIterable.<String>instance();
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
			return new LocalConnectionListener();
		}

		class LocalConnectionListener
			extends ConnectionAdapter
		{
			@Override
			public void opened(ConnectionProfile cp) {
				this.connectionChanged();
			}
			@Override  // this probably won't ever get called...
			public void closed(ConnectionProfile cp) {
				this.connectionChanged();
			}
			private void connectionChanged() {
				DisplayTools.asyncExec(
					new Runnable() {
						public void run() {
							DatabaseGroup.this.connectionChanged();
						}
					}
				);
			}
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

		void promptToConfigJpaProjectConnection() {
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
