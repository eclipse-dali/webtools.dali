/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.jpt.core.JpaDataSource;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.db.ConnectionAdapter;
import org.eclipse.jpt.db.ConnectionListener;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.ConnectionProfileListener;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.DatabaseObject;

/**
 * GenericJpaDataSource
 */
public class GenericJpaDataSource
	extends AbstractJpaNode
	implements JpaDataSource
{
	/**
	 * cache the connection profile name so we can detect when
	 * it changes and notify listeners
	 */
	protected String connectionProfileName;

	protected transient ConnectionProfile connectionProfile;

	/**
	 * listen for the connection to be added or removed or its name changed
	 */
	protected final ConnectionProfileListener connectionProfileListener;

	/**
	 * listen for the connection to be opened or closed
	 */
	protected final ConnectionListener connectionListener;

	private static final long serialVersionUID = 1L;


	// ********** constructor/initialization **********

	public GenericJpaDataSource(JpaProject jpaProject, String connectionProfileName) {
		super(jpaProject);
		//moving the building of the connection profile before the connectionProfileListener
		//is added.  Need to make sure the loading of db profiles is completed before
		//listening, otherwise we get notifications before our model is finished being built.
		//this means our updater is called before it is set which results in an IllegalStateException.
		//Hopefully this change is temporary and DTP will fix bug 246270 where I suggest they
		//not fire events when building profiles.
		this.connectionProfileName = connectionProfileName;
		this.connectionProfile = this.buildConnectionProfile(connectionProfileName);

		this.connectionProfileListener = this.buildConnectionProfileListener();
		this.getConnectionProfileFactory().addConnectionProfileListener(this.connectionProfileListener);

		this.connectionListener = this.buildConnectionListener();
		if (this.connectionProfile != null) {
			this.connectionProfile.addConnectionListener(this.connectionListener);
		}
	}

	protected ConnectionProfileFactory getConnectionProfileFactory() {
		return this.getJpaPlatform().getConnectionProfileFactory();
	}

	protected ConnectionProfileListener buildConnectionProfileListener() {
		return new LocalConnectionProfileListener();
	}

	protected ConnectionListener buildConnectionListener() {
		return new LocalConnectionListener();
	}


	// ********** JpaDataSource implementation **********

	public String getConnectionProfileName() {
		return this.connectionProfileName;
	}

	public void setConnectionProfileName(String connectionProfileName) {
		String old = this.connectionProfileName;
		this.connectionProfileName = connectionProfileName;
		if (this.attributeValueHasChanged(old, connectionProfileName)) {
			this.firePropertyChanged(CONNECTION_PROFILE_NAME_PROPERTY, old, connectionProfileName);
			 // synch the connection profile when the name changes
			this.setConnectionProfile(this.buildConnectionProfile(connectionProfileName));
			JptCorePlugin.setConnectionProfileName(getJpaProject().getProject(), connectionProfileName);
		}
	}

	public ConnectionProfile getConnectionProfile() {
		return this.connectionProfile;
	}

	@Override
	public boolean connectionProfileIsActive() {
		ConnectionProfile cp = this.connectionProfile;
		return (cp != null) && cp.isActive();
	}

	@Override
	public Database getDatabase() {
		ConnectionProfile cp = this.connectionProfile;
		return (cp == null) ? null : cp.getDatabase();
	}

	public <T extends DatabaseObject> T selectDatabaseObjectForIdentifier(T[] databaseObjects, String identifier) {
		Database db = this.getDatabase();
		return (db == null) ? null : db.selectDatabaseObjectForIdentifier(databaseObjects, identifier);
	}

	public void dispose() {
		if (this.connectionProfile != null) {
			this.connectionProfile.removeConnectionListener(this.connectionListener);
		}
		this.getConnectionProfileFactory().removeConnectionProfileListener(this.connectionProfileListener);
	}


	// ********** internal methods **********

	protected ConnectionProfile buildConnectionProfile(String name) {
		return this.getConnectionProfileFactory().buildConnectionProfile(name, this.getJpaPlatform().getDatabaseFinder());
	}

	protected void setConnectionProfile(ConnectionProfile connectionProfile) {
		ConnectionProfile old = this.connectionProfile;
		if (old != null) {
			old.removeConnectionListener(this.connectionListener);
		}
		this.connectionProfile = connectionProfile;
		if (connectionProfile != null) {
			connectionProfile.addConnectionListener(this.connectionListener);
		}
		this.firePropertyChanged(CONNECTION_PROFILE_PROPERTY, old, connectionProfile);
	}


	// ********** overrides **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.connectionProfileName);
	}


	// ********** member classes **********

	/**
	 * Listen for a connection profile with our name being removed.
	 * Also listen for our connection's name being changed.
	 */
	protected class LocalConnectionProfileListener implements ConnectionProfileListener {

		protected LocalConnectionProfileListener() {
			super();
		}

		public void connectionProfileAdded(String name) {
			// check to see if a connection profile with our name was added
			// (assume our connection profile is null)
			if (GenericJpaDataSource.this.connectionProfile == null) {
				if (name.equals(GenericJpaDataSource.this.getConnectionProfileName())) {
					GenericJpaDataSource.this.setConnectionProfileName(name);  // this will trigger creation of CP
				}
			}
		}

		public void connectionProfileRemoved(String name) {
			if (GenericJpaDataSource.this.connectionProfile == null) {
				return;
			}
			if (name.equals(GenericJpaDataSource.this.connectionProfile.getName())) {
				GenericJpaDataSource.this.setConnectionProfile(null);
			}
		}

		public void connectionProfileRenamed(String oldName, String newName) {
			if (GenericJpaDataSource.this.connectionProfile == null) {
				if (newName.equals(connectionProfileName)) {
					GenericJpaDataSource.this.setConnectionProfileName(newName);
				}
				return;
			}
			// the connection profile will already have the new name,
			// we just need to synch the name held by the data source
			if (newName.equals(GenericJpaDataSource.this.connectionProfile.getName())) {
				GenericJpaDataSource.this.setConnectionProfileName(newName);
				// bug 232225 - also update project setting when connection profile
				//   name has changed
				JptCorePlugin.setConnectionProfileName(getJpaProject().getProject(), newName);
			}
		}
	}


	/**
	 * Whenever the connection is opened or closed trigger a project update.
	 */
	protected class LocalConnectionListener extends ConnectionAdapter {

		@Override
		public void opened(ConnectionProfile profile) {
			GenericJpaDataSource.this.getJpaProject().update();
		}

		@Override
		public void closed(ConnectionProfile profile) {
			GenericJpaDataSource.this.getJpaProject().update();
		}

	}

}
