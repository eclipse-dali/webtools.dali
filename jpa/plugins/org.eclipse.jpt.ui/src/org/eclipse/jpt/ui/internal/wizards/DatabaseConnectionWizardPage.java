/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import java.util.Collections;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.db.ConnectionAdapter;
import org.eclipse.jpt.db.ConnectionListener;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.db.ui.internal.DTPUiTools;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
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

/**
 * Most of the behavior is in DatabaseGroup....
 * 
 * We open the wizard page with the JPA project's connection profile and
 * default schema selected (if possible), but then the selections are driven
 * by the user. If the user re-selects the JPA project's connection profile,
 * we will pre-select the project's default schema if possible.
 */
public class DatabaseConnectionWizardPage extends WizardPage {

	final JpaProject jpaProject;

	private final Set<Listener> listeners = Collections.synchronizedSet(new HashSet<Listener>());

	private DatabaseGroup databaseGroup;


	public DatabaseConnectionWizardPage(JpaProject jpaProject) {
		super("Database Settings"); //$NON-NLS-1$
		if (jpaProject == null) {
			throw new NullPointerException();
		}
		this.jpaProject = jpaProject;
		this.setTitle(JptUiMessages.DatabaseReconnectWizardPage_databaseConnection);
		this.setMessage(JptUiMessages.DatabaseReconnectWizardPage_reconnectToDatabase);
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

	public ConnectionProfile getSelectedConnectionProfile() {
		return this.databaseGroup.getSelectedConnectionProfile();
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
		if ( ! this.listeners.add(listener)) {
			throw new IllegalArgumentException("duplicate listener: " + listener); //$NON-NLS-1$
		}
	}

	public void removeListener(Listener listener) {
		if ( ! this.listeners.remove(listener)) {
			throw new IllegalArgumentException("missing listener: " + listener); //$NON-NLS-1$
		}
	}

	private Iterator<Listener> listeners() {
		return new CloneIterator<Listener>(this.listeners);
	}

	void fireConnectionProfileChanged(ConnectionProfile connectionProfile) {
		for (Iterator<Listener> stream = this.listeners(); stream.hasNext(); ) {
			stream.next().selectedConnectionProfileChanged(connectionProfile);
		}
	}

	void fireSchemaChanged(Schema schema) {
		this.setPageComplete(schema != null);
		for (Iterator<Listener> stream = this.listeners(); stream.hasNext(); ) {
			stream.next().selectedSchemaChanged(schema);
		}
	}


	// ********** listener interface **********

	/**
	 * Allows clients to listen for changes to the selected connection profile
	 * and schema.
	 */
	public interface Listener extends EventListener {
		void selectedConnectionProfileChanged(ConnectionProfile connectionProfile);
		void selectedSchemaChanged(Schema schema);
	}


	// ********** database group **********

	/**
	 * connection combo-box
	 * schema combo-box
	 * add connection link
	 * reconnect link
	 */
	class DatabaseGroup {

		// these are kept in synch with the selection
		private ConnectionProfile selectedConnectionProfile;
		private Schema selectedSchema;

		private final Combo connectionComboBox;

		private final Combo schemaComboBox;

		private final Link reconnectLink;

		private final ConnectionListener connectionListener;


		// ********** construction **********

		DatabaseGroup(Composite composite) {
			super();

			Group group = new Group(composite, SWT.NONE);
			group.setLayout(new GridLayout(2, false));  // false = do not make columns equal width
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			group.setText(JptUiMessages.DatabaseReconnectWizardPage_database);
			// TODO PlatformUI.getWorkbench().getHelpSystem().setHelp(this.group, JpaHelpContextIds.XXX);

			// connection combo-box
			this.buildLabel(group, 1, JptUiMessages.DatabaseReconnectWizardPage_connection);
			this.connectionComboBox = this.buildComboBox(group, this.buildConnectionComboBoxSelectionListener());

			// schema combo-box
			this.buildLabel(group, 1, JptUiMessages.DatabaseReconnectWizardPage_schema);
			this.schemaComboBox = this.buildComboBox(group, this.buildSchemaComboBoxSelectionListener());
			this.buildLabel(group, 2, JptUiMessages.DatabaseReconnectWizardPage_schemaInfo);

			// add connection link
			this.buildLink(group, JptUiMessages.DatabaseReconnectWizardPage_addConnectionLink, this.buildAddConnectionLinkSelectionListener());

			// reconnect link
			this.reconnectLink = this.buildLink(group, JptUiMessages.DatabaseReconnectWizardPage_reconnectLink, this.buildReconnectLinkSelectionListener());

			this.connectionListener = this.buildConnectionListener();

			// initialize state, based on JPA project
			this.selectedConnectionProfile = this.getJpaProjectConnectionProfile();
			this.selectedSchema = this.getDefaultSchema();

			if (this.selectedSchema != null) {
				DatabaseConnectionWizardPage.this.fireSchemaChanged(this.selectedSchema);
			}
			if (this.selectedConnectionProfile != null) {
				this.selectedConnectionProfile.addConnectionListener(this.connectionListener);
				DatabaseConnectionWizardPage.this.fireConnectionProfileChanged(this.selectedConnectionProfile);
			}

			this.updateConnectionComboBox();
			this.updateSchemaComboBox();
			this.updateReconnectLink();
		}


		// ********** intra-wizard methods **********

		ConnectionProfile getSelectedConnectionProfile() {
			return this.selectedConnectionProfile;
		}

		Schema getSelectedSchema() {
			return this.selectedSchema;
		}

		void dispose() {
			if (this.selectedConnectionProfile != null) {
				this.selectedConnectionProfile.removeConnectionListener(this.connectionListener);
			}
		}


		// ********** internal methods **********

		/**
		 * this can return null;
		 * called at start-up and when the selected connection profile changes
		 */
		private ConnectionProfile getJpaProjectConnectionProfile() {
			return DatabaseConnectionWizardPage.this.jpaProject.getConnectionProfile();
		}

		/**
		 * this can return null;
		 * called at start-up and when the selected connection profile changes
		 */
		private Schema getDefaultSchema() {
			return (this.selectedConnectionProfile == this.getJpaProjectConnectionProfile()) ?
							DatabaseConnectionWizardPage.this.jpaProject.getDefaultDbSchema()
						:
							null;
		}

		/**
		 * the connection combo-box is updated at start-up and when the user
		 * adds a connection profile
		 */
		private void updateConnectionComboBox() {
			this.connectionComboBox.removeAll();
			for (String cpName : this.buildSortedConnectionProfileNames()) {
				this.connectionComboBox.add(cpName);
			}
			if (this.selectedConnectionProfile != null) {
				this.connectionComboBox.select(this.connectionComboBox.indexOf(this.selectedConnectionProfile.getName()));
			}
		}

		private SortedSet<String> buildSortedConnectionProfileNames() {
			return CollectionTools.sortedSet(JptDbPlugin.instance().getConnectionProfileFactory().connectionProfileNames());
		}

		/**
		 * called at start-up and when the selected connection profile changes
		 */
		private void updateReconnectLink() {
			this.reconnectLink.setEnabled(this.reconnectLinkCanBeEnabled());
		}

		private boolean reconnectLinkCanBeEnabled() {
			return (this.selectedConnectionProfile != null) && this.selectedConnectionProfile.isInactive();
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
			SchemaContainer sc = DatabaseConnectionWizardPage.this.jpaProject.getDefaultDbSchemaContainer();
			return (sc == null) ? EmptyIterator.<String>instance() : sc.sortedSchemaIdentifiers();
		}

		/**
		 * If the specified name matches the name of the JPA project's
		 * connection profile, return it; otherwise, build a new connection
		 * profile.
		 */
		private ConnectionProfile checkJpaProjectConnectionProfile(String cpName) {
			ConnectionProfile cp = this.getJpaProjectConnectionProfile();
			if ((cp != null) && cp.getName().equals(cpName)) {
				return cp;
			}
			return this.buildConnectionProfile(cpName);
		}

		private ConnectionProfile buildConnectionProfile(String name) {
			return JptDbPlugin.instance().getConnectionProfileFactory().buildConnectionProfile(name);
		}


		// ********** listener callbacks **********

		void selectedConnectionChanged() {
			String text = this.connectionComboBox.getText();
			if (text.length() == 0) {
				if (this.selectedConnectionProfile == null) {
					return;  // no change
				}
				this.selectedConnectionProfile.removeConnectionListener(this.connectionListener);
				this.selectedConnectionProfile = null;
			} else {
				if (this.selectedConnectionProfile == null) {
					this.selectedConnectionProfile = this.checkJpaProjectConnectionProfile(text);
				} else {
					if (text.equals(this.selectedConnectionProfile.getName())) {
						return;  // no change
					}
					this.selectedConnectionProfile.removeConnectionListener(this.connectionListener);
					this.selectedConnectionProfile = this.checkJpaProjectConnectionProfile(text);
				}
				this.selectedConnectionProfile.addConnectionListener(this.connectionListener);
			}
			this.connectionChanged();
			DatabaseConnectionWizardPage.this.fireConnectionProfileChanged(this.selectedConnectionProfile);
		}

		void selectedSchemaChanged() {
			Schema old = this.selectedSchema;
			this.selectedSchema = this.selectedConnectionProfile.getDatabase().getSchemaForIdentifier(this.schemaComboBox.getText());
			if (this.selectedSchema != old) {
				DatabaseConnectionWizardPage.this.fireSchemaChanged(this.selectedSchema);
			}
		}

		/**
		 * Open the DTP New Connection Profile wizard.
		 * If the user creates a new connection profile, start using it and
		 * connect it
		 */
		void addConnection() {
			String addedProfileName = DTPUiTools.createNewConnectionProfile();
			if (addedProfileName == null) {
				return;  // user pressed "Cancel"
			}
			if (this.selectedConnectionProfile != null) {
				this.selectedConnectionProfile.removeConnectionListener(this.connectionListener);
			}
			this.selectedConnectionProfile = this.buildConnectionProfile(addedProfileName);
			this.selectedConnectionProfile.addConnectionListener(this.connectionListener);
			this.updateConnectionComboBox();
			this.selectedConnectionProfile.connect();
			// everything else should be synchronized when we get the resulting open event
			DatabaseConnectionWizardPage.this.fireConnectionProfileChanged(this.selectedConnectionProfile);
		}

		void reconnect() {
			this.selectedConnectionProfile.connect();
			// everything should be synchronized when we get the resulting open event
		}

		/**
		 * called when
		 *     - the user selects a new connection
		 *     - the connection was opened
		 *     - the connection was closed (never happens?)
		 * we need to update the schema stuff and the reconnect link
		 */
		void connectionChanged() {
			Schema old = this.selectedSchema;
			this.selectedSchema = this.getDefaultSchema();
			if (this.selectedSchema != old) {
				DatabaseConnectionWizardPage.this.fireSchemaChanged(this.selectedSchema);
			}
			this.updateSchemaComboBox();
			this.updateReconnectLink();
		}


		// ********** listeners **********

		private SelectionListener buildConnectionComboBoxSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					// nothing special for "default" (double-click?)
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					DatabaseGroup.this.selectedConnectionChanged();
				}
				@Override
				public String toString() {
					return "DatabaseConnectionWizardPage connection combo-box selection listener"; //$NON-NLS-1$
				}
			};
		}

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
					return "DatabaseConnectionWizardPage schema combo-box selection listener"; //$NON-NLS-1$
				}
			};
		}

		private SelectionListener buildAddConnectionLinkSelectionListener() {
			return new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					DatabaseGroup.this.addConnection();
				}
				@Override
				public String toString() {
					return "DatabaseConnectionWizardPage add connection link selection listener"; //$NON-NLS-1$
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
					return "DatabaseConnectionWizardPage reconnect link selection listener"; //$NON-NLS-1$
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
					return "DatabaseConnectionWizardPage connection listener"; //$NON-NLS-1$
				}
			};
		}


		// ********** UI components **********

		/**
		 * build and return a label
		 */
		private Label buildLabel(Composite parent, int span, String text) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(text);
			GridData gd = new GridData();
			gd.horizontalIndent = 30;
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
