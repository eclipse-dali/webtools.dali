/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards.gen;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.db.ConnectionAdapter;
import org.eclipse.jpt.db.ConnectionListener;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.db.ui.internal.DTPUiTools;
import org.eclipse.jpt.ui.CommonImages;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
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
import org.eclipse.swt.widgets.Display;
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

	// these are kept in synch with the selection
	private ConnectionProfile selectedConnectionProfile;
	private Schema selectedSchema;

	private final Combo connectionComboBox;

	private final Combo schemaComboBox;

	private final Button reconnectButton;

	private final ConnectionListener connectionListener;

	private IWizardContainer wizardContainer;

	// ********** construction **********

	DatabaseGroup(IWizardContainer wizardContainer, JpaProject jpaProject, Composite parent, int widthHint) 
	{
		super();
		this.wizardContainer = wizardContainer;
		this.jpaProject = jpaProject;

		// connection combo-box
		this.buildLabel(parent, 1, JptUiEntityGenMessages.connection);
		this.connectionComboBox = this.buildComboBox(parent, widthHint, this.buildConnectionComboBoxSelectionListener());

		// add connection button
		this.buildButton(parent, JptUiEntityGenMessages.addConnectionLink, CommonImages.createImage( CommonImages.ADD_CONNECTION_IMAGE ), this.buildAddConnectionLinkSelectionListener());

		// A composite holds the reconnect button & text
		this.buildLabel(parent, 1, "");
		Composite comp = new Composite( parent , SWT.NONE );
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true ;
		gd.horizontalSpan = 2;
		comp.setLayoutData( gd );
		GridLayout gl = new GridLayout(2, false);
		// Make the reconnect button to be closer to the connection combo.
		gl.marginTop = -5;
		comp.setLayout(gl);
		this.reconnectButton = this.buildButton(comp, JptUiEntityGenMessages.connectLink, CommonImages.createImage( CommonImages.RECONNECT_IMAGE ),  this.buildReconnectLinkSelectionListener());
		this.buildLabel(comp, 1, JptUiEntityGenMessages.schemaInfo);

		// schema combo-box
		this.buildLabel(parent, 1, JptUiEntityGenMessages.schema);
		this.schemaComboBox = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
		GridData data = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
		data.horizontalAlignment = SWT.FILL;
		data.horizontalSpan = 1;
		data.grabExcessHorizontalSpace = true ;
		this.schemaComboBox.setLayoutData(data);
		this.schemaComboBox.addSelectionListener(this.buildSchemaComboBoxSelectionListener());
		// filler
		new Label(parent, SWT.NULL);

		this.connectionListener = this.buildConnectionListener();
	}


	public void init()
	{
		// initialize state, based on JPA project
		this.selectedConnectionProfile = this.getJpaProjectConnectionProfile();
		this.selectedSchema = this.getDefaultSchema();

		if (this.selectedSchema != null) {
			this.fireSchemaChanged(this.selectedSchema);
		}
		if (this.selectedConnectionProfile != null) {
			this.selectedConnectionProfile.addConnectionListener(this.connectionListener);
			this.fireConnectionProfileChanged(this.selectedConnectionProfile);
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
		return this.jpaProject.getConnectionProfile();
	}

	/**
	 * this can return null;
	 * called at start-up and when the selected connection profile changes
	 */
	private Schema getDefaultSchema() {
		try{
		return (this.selectedConnectionProfile == this.getJpaProjectConnectionProfile()) ?
						jpaProject.getDefaultDbSchema()	: null;
		}catch(Exception e ){
			e.printStackTrace();
		}
		return null;
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
		for (Iterator<String> stream = this.getSchemata(); stream.hasNext(); ) {
			this.schemaComboBox.add(stream.next());
		}
		// the current schema *should* be in the current connection profile
		if (this.selectedSchema != null) {
			this.schemaComboBox.select(this.schemaComboBox.indexOf(this.selectedSchema.getName()));
		}
	}

	private Iterator<String> getSchemata() {
		SchemaContainer sc = jpaProject.getDefaultDbSchemaContainer();
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
		this.fireConnectionProfileChanged(this.selectedConnectionProfile);
		this.connectionChanged();
	}

	void selectedSchemaChanged() {
		Schema old = this.selectedSchema;
		this.selectedSchema = this.selectedConnectionProfile.getDatabase().getSchemaForIdentifier(this.schemaComboBox.getText());
		if (this.selectedSchema != old) {
			fireSchemaChanged(this.selectedSchema);
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
		this.updateSchemaComboBox();
		// everything else should be synchronized when we get the resulting open event
		this.fireConnectionProfileChanged(this.selectedConnectionProfile);
	}

	void reconnect() {
		try {
			wizardContainer.run(true, true, new IRunnableWithProgress(){
				public void run( final IProgressMonitor monitor ) 
			    	throws InvocationTargetException, InterruptedException
			    {
					monitor.beginTask("Connecting to database", 10);
					final boolean[] isConnected= new boolean[1];
					isConnected[0]=false;
					Thread t= new Thread(){
						public void run() {
							selectedConnectionProfile.connect();
							isConnected[0]=true;
						}						
					};
					t.start();
					while( !isConnected[0]){
						Thread.sleep(1000);
						monitor.worked(1);
					}
			        // everything should be synchronized when we get the resulting open event
					monitor.done();
			    }
			});
		} catch (Exception e) {
			JptUiPlugin.log(e);
		}
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
		for (Iterator<Listener> stream = this.listeners(); stream.hasNext(); ) {
			stream.next().selectedSchemaChanged(schema);
		}
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
	private Combo buildComboBox(Composite parent, int widthHint, SelectionListener listener) {
		Combo combo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
		combo.addSelectionListener(listener);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		//data.grabExcessHorizontalSpace = true ;
		data.widthHint = widthHint;
		combo.setLayoutData(data);
		return combo;
	}

	/**
	 * build and return a link
	 */
	private Button buildButton(Composite parent, String toolTipText, Image image, SelectionListener listener) {
		Button button = new Button(parent, SWT.NONE);
		GridData data = new GridData(GridData.END, GridData.CENTER, false, false);
		data.horizontalSpan = 1;
		button.setLayoutData(data);
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
