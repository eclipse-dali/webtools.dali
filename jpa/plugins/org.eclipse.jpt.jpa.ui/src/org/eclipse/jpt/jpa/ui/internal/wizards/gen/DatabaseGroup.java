/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.gen;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.SynchronizedBoolean;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.db.ConnectionAdapter;
import org.eclipse.jpt.jpa.db.ConnectionListener;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.JptJpaDbPlugin;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jpt.jpa.db.ui.internal.DTPUiTools;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.ImageRepository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * A composite used to connect to database, includes following UI controls:
 * - connection combo-box
 * - schema combo-box
 * - add connection button
 * - reconnect button
 */
public class DatabaseGroup
{
	private final JpaProject jpaProject;
	private final Set<Listener> listeners = Collections.synchronizedSet(new HashSet<Listener>());

	// these are kept in sync with the selection
	private ConnectionProfile selectedConnectionProfile;
	
	private Schema selectedSchema;

	private final Combo connectionComboBox;

	private final Combo schemaComboBox;

	private final Button reconnectButton;

	private final ConnectionListener connectionListener;

	private IWizardContainer wizardContainer;

	protected final ResourceManager resourceManager;

	// ********** construction **********

	protected DatabaseGroup(IWizardContainer wizardContainer, JpaProject jpaProject, Composite parent, ResourceManager resourceManager, int widthHint) {
		super();
		this.wizardContainer = wizardContainer;
		this.jpaProject = jpaProject;
		this.resourceManager = resourceManager;

		// connection combo-box
		this.buildLabel(parent, 1, JptUiEntityGenMessages.connection);
		this.connectionComboBox = this.buildConnectionComboBox(parent, widthHint);

		// add connection button
		Button addConnectionButton = this.buildButton(parent, JptUiEntityGenMessages.addConnectionLink, ImageRepository.getAddConnectionButtonImage(this.resourceManager), this.buildAddConnectionLinkSelectionListener());
		addConnectionButton.setLayoutData(new GridData());

		// A composite holds the reconnect button & text
		this.buildLabel(parent, 1, ""); //$NON-NLS-1$
		Composite comp = new Composite( parent , SWT.NONE );
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true ;
		gridData.horizontalSpan = 2;
		comp.setLayoutData(gridData);
		GridLayout gridLayout = new GridLayout(2, false);
		// Make the reconnect button to be closer to the connection combo.
		gridLayout.marginTop = -5;
		comp.setLayout(gridLayout);

		// reconnection button
		this.reconnectButton = this.buildButton(comp, JptUiEntityGenMessages.connectLink, ImageRepository.getReconnectButtonImage(this.resourceManager),  this.buildReconnectLinkSelectionListener());
		this.buildLabel(comp, 1, JptUiEntityGenMessages.schemaInfo);

		// schema combo-box
		this.buildLabel(parent, 1, JptUiEntityGenMessages.schema);
		this.schemaComboBox = this.buildSchemaComboBox(parent);
		// filler
		new Label(parent, SWT.NULL);

		this.connectionListener = this.buildConnectionListener();
	}

	public void init()
	{
		// initialize state, based on JPA project
		this.selectedConnectionProfile = this.getJpaProjectConnectionProfile();
		this.selectedSchema = this.getDefaultSchema();

		if(this.selectedSchema != null) {
			this.fireSchemaChanged(this.selectedSchema);
		}
		if(this.selectedConnectionProfile != null) {
			this.selectedConnectionProfile.addConnectionListener(this.connectionListener);
			this.fireConnectionProfileChanged(this.selectedConnectionProfile);
		}

		this.updateConnectionComboBox();
		this.updateSchemaComboBox();
		this.updateReconnectLink();
	}
	
	// ********** intra-wizard methods **********

	public Schema getSelectedSchema() {
		return this.selectedSchema;
	}

	public void dispose() {
		if(this.selectedConnectionProfile != null) {
			this.selectedConnectionProfile.removeConnectionListener(this.connectionListener);
		}
	}
	
	public boolean connectionIsActive() {
		return (this.selectedConnectionProfile != null) && (this.selectedConnectionProfile.isActive());
	}

	// ********** internal methods **********

	/**
	 * this can return null;
	 * called at start-up and when the selected connection profile changes
	 */
	private ConnectionProfile getJpaProjectConnectionProfile() {
		return this.jpaProject.getConnectionProfile();
	}

	/**
	 * this can return null;
	 * called at start-up and when the selected connection profile changes
	 */
	private Schema getDefaultSchema() {
		Schema schema = null;
		// If a schema is specified for the JPA project, set schema to the schema of the JPA project
		if (this.getJpaProjectConnectionProfile() != null) {
			// schema could be null when the default schema does not exist
			schema = jpaProject.getDefaultDbSchema();
		}
		
		// selected connection profile could be null when no connection specified
		if (schema == null && this.selectedConnectionProfile != null) {
			// check if the selected connection profile is active
			if (this.selectedConnectionProfile.isActive()) {
				// if active, get the default schema of the selected connection profile
				schema = this.selectedConnectionProfile.getDatabase().getDefaultSchema();
				// if the default schema does not exist, get the first available schema
				if (schema == null) {
					Iterable<Schema> schemata = this.selectedConnectionProfile.getDatabase().getSchemata();
					if (schemata.iterator().hasNext()) {
						schema = schemata.iterator().next();
					}
				}
			}
		}
		
		return schema;
	}

	/**
	 * the connection combo-box is updated at start-up and when the user
	 * adds a connection profile
	 */
	private void updateConnectionComboBox() {
		this.connectionComboBox.removeAll();
		for(String cpName : this.buildSortedConnectionProfileNames()) {
			this.connectionComboBox.add(cpName);
		}
		if(this.selectedConnectionProfile != null) {
			this.connectionComboBox.select(this.connectionComboBox.indexOf(this.selectedConnectionProfile.getName()));
		}
	}

	private SortedSet<String> buildSortedConnectionProfileNames() {
		return CollectionTools.sortedSet(JptJpaDbPlugin.getConnectionProfileFactory().getConnectionProfileNames());
	}

	/**
	 * called at start-up and when the selected connection profile changes
	 */
	private void updateReconnectLink() {
		this.reconnectButton.setEnabled(this.reconnectLinkCanBeEnabled());
	}

	private boolean reconnectLinkCanBeEnabled() {
		return (this.selectedConnectionProfile != null) && !(this.selectedConnectionProfile.isActive());
	}

	/**
	 * the schema combo-box is updated at start-up and
	 * when the selected connection profile changes
	 */
	private void updateSchemaComboBox() {
		this.schemaComboBox.removeAll();
		for(String name : this.getSchemaNames()) {
			this.schemaComboBox.add(name);
		}
		// the current schema *should* be in the current connection profile
		if(this.selectedSchema != null) {
			this.schemaComboBox.select(this.schemaComboBox.indexOf(this.selectedSchema.getName()));
		}
	}

	private Iterable<String> getSchemaNames() {
		SchemaContainer sc = this.jpaProject.getDefaultDbSchemaContainer();
		// use schema *names* since the combo-box is read-only
		return (sc != null) ? sc.getSortedSchemaNames() : EmptyIterable.<String>instance();
	}

	/**
	 * If the specified name matches the name of the JPA project's
	 * connection profile, return it; otherwise, build a new connection
	 * profile.
	 */
	private ConnectionProfile checkJpaProjectConnectionProfile(String cpName) {
		ConnectionProfile cp = this.getJpaProjectConnectionProfile();
		if((cp != null) && cp.getName().equals(cpName)) {
			return cp;
		}
		return this.buildConnectionProfile(cpName);
	}

	private ConnectionProfile buildConnectionProfile(String name) {
		return JptJpaDbPlugin.getConnectionProfileFactory().buildConnectionProfile(name);
	}


	// ********** listener callbacks **********

	private void selectedConnectionChanged() {
		String text = this.connectionComboBox.getText();
		if(text.length() == 0) {
			if(this.selectedConnectionProfile == null) {
				return;  // no change
			}
			this.selectedConnectionProfile.removeConnectionListener(this.connectionListener);
			this.selectedConnectionProfile = null;
		} 
		else {
			if(this.selectedConnectionProfile == null) {
				this.selectedConnectionProfile = this.checkJpaProjectConnectionProfile(text);
			} 
			else {
				if(text.equals(this.selectedConnectionProfile.getName())) {
					return;  // no change
				}
				this.selectedConnectionProfile.removeConnectionListener(this.connectionListener);
				this.selectedConnectionProfile = this.checkJpaProjectConnectionProfile(text);
			}
			this.selectedConnectionProfile.addConnectionListener(this.connectionListener);
		}
		this.fireConnectionProfileChanged(this.selectedConnectionProfile);
		this.connectionChanged();
	}

	private void selectedSchemaChanged() {
		Schema old = this.selectedSchema;
		this.selectedSchema = this.jpaProject.getDefaultDbSchemaContainer().getSchemaNamed(this.schemaComboBox.getText());
		if(this.selectedSchema != old) {
			fireSchemaChanged(this.selectedSchema);
		}
	}

	/**
	 * Open the DTP New Connection Profile wizard.
	 * If the user creates a new connection profile, start using it and
	 * connect it
	 */
	private void addConnection() {
		String addedProfileName = DTPUiTools.createNewConnectionProfile();
		if(addedProfileName == null) {
			return;  // user pressed "Cancel"
		}
		if(this.selectedConnectionProfile != null) {
			this.selectedConnectionProfile.removeConnectionListener(this.connectionListener);
		}
		this.selectedConnectionProfile = this.buildConnectionProfile(addedProfileName);
		this.selectedConnectionProfile.addConnectionListener(this.connectionListener);
		this.updateConnectionComboBox();
		this.selectedConnectionProfile.connect();
		// everything else should be synchronized when we get the resulting open event
		this.fireConnectionProfileChanged(this.selectedConnectionProfile);
		this.updateSchemaComboBox();
	}

	private void reconnect() {
		try {
			this.wizardContainer.run(true, true, new IRunnableWithProgress() {
				public void run(final IProgressMonitor monitor) 
			    	throws InvocationTargetException, InterruptedException
			    {
					monitor.beginTask(JptUiEntityGenMessages.connectingToDatabase, 10);
					final SynchronizedBoolean finished = new SynchronizedBoolean(false);
					Thread t = new Thread() {
						@Override
						public void run() {
							try {
								DatabaseGroup.this.selectedConnectionProfile.connect();
							} 
							catch (Exception ex) {
								JptJpaUiPlugin.log(ex);
							} 
							finally {
								finished.setTrue();
							}
						}						
					};
					t.start();
// Updating the monitor caused TablesSelectorWizardPage buttons to be disable
//					while(finished.isFalse()) {
//						Thread.sleep(1000);
//						monitor.worked(1);
//					}
			        // everything should be synchronized when we get the resulting open event
					monitor.done();
			    }
			});
		} 
		catch (Exception e) {
			JptJpaUiPlugin.log(e);
		}
		this.wizardContainer.updateButtons();
	}

	/**
	 * called when
	 *     - the user selects a new connection
	 *     - the connection was opened
	 *     - the connection was closed (never happens?)
	 * we need to update the schema stuff and the reconnect link
	 */
	/* CU private */ void connectionChanged() {
		Schema old = this.selectedSchema;
		this.selectedSchema = this.getDefaultSchema();
		if(this.selectedSchema != old) {
			this.fireSchemaChanged(this.selectedSchema);
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
			org.eclipse.jpt.common.ui.internal.util.SWTUtil.asyncExec(
				new Runnable() {
					public void run() {
						DatabaseGroup.this.connectionChanged();
					}
				}
			);
		}
	}

	// ********** listeners **********

	public void addListener(Listener listener) {
		if( ! this.listeners.add(listener)) {
			throw new IllegalArgumentException("duplicate listener: " + listener); //$NON-NLS-1$
		}
	}

	public void removeListener(Listener listener) {
		if( ! this.listeners.remove(listener)) {
			throw new IllegalArgumentException("missing listener: " + listener); //$NON-NLS-1$
		}
	}

	private Iterator<Listener> listeners() {
		return new CloneIterator<Listener>(this.listeners);
	}

	private void fireConnectionProfileChanged(ConnectionProfile connectionProfile) {
		for(Iterator<Listener> stream = this.listeners(); stream.hasNext(); ) {
			stream.next().selectedConnectionProfileChanged(connectionProfile);
		}
	}

	private void fireSchemaChanged(Schema schema) {
		for(Iterator<Listener> stream = this.listeners(); stream.hasNext(); ) {
			stream.next().selectedSchemaChanged(schema);
		}
	}

	// ********** UI components **********

	private Label buildLabel(Composite parent, int span, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		GridData gridData = new GridData();
		gridData.horizontalSpan = span;
		label.setLayoutData(gridData);
		return label;
	}

	private Combo buildConnectionComboBox(Composite parent, int widthHint) {
		Combo combo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		//gridData.grabExcessHorizontalSpace = true ;
		gridData.widthHint = widthHint;
		combo.setLayoutData(gridData);
		combo.addSelectionListener(this.buildConnectionComboBoxSelectionListener());
		return combo;
	}

	private Combo buildSchemaComboBox(Composite parent) {
		Combo combo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
		GridData gridData = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
		gridData.horizontalAlignment = SWT.FILL;
		gridData.horizontalSpan = 1;
		gridData.grabExcessHorizontalSpace = true ;
		combo.setLayoutData(gridData);
		combo.addSelectionListener(this.buildSchemaComboBoxSelectionListener());
		return combo;
	}

	private Button buildButton(Composite parent, String toolTipText, Image image, SelectionListener listener) {
		Button button = new Button(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.END, GridData.CENTER, false, false);
		gridData.horizontalSpan = 1;
		button.setLayoutData(gridData);
		button.setImage( image );
		button.setToolTipText( toolTipText);
		button.addSelectionListener(listener);
		return button;
	}

	// ********** listener interface **********

	/**
	 * Allows clients to listen for changes to the selected connection profile
	 * and schema.
	 */
	public interface Listener extends EventListener
	{
		void selectedConnectionProfileChanged(ConnectionProfile connectionProfile);
		
		void selectedSchemaChanged(Schema schema);
	}

}
