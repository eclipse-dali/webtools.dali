/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.ConnectionProfileRepository;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.ProfileListener;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;

/**
 * 
 */
public class JpaDataSource extends JpaNodeModel implements IJpaDataSource
{
	// cache the connection profile name so we can detect when it changes and fire events
	protected String connectionProfileName;
	
	// this should never be null
	protected transient ConnectionProfile connectionProfile;
	
	protected final ProfileListener profileListener;
	
	protected final ConnectionListener connectionListener;
	
	
	// **************** constructor/initialization ****************************
	
	protected JpaDataSource(IJpaProject jpaProject) {
		super(jpaProject);
		this.profileListener = this.buildProfileListener();
		ConnectionProfileRepository.instance().addProfileListener(this.profileListener);
		this.connectionListener = this.buildConnectionListener();
	}

	public JpaDataSource(IJpaProject jpaProject, String connectionProfileName) {
		this(jpaProject);
		this.connectionProfileName = connectionProfileName;
		this.connectionProfile = this.profileNamed(connectionProfileName);
		this.connectionProfile.addConnectionListener(this.connectionListener);
	}

	protected ProfileListener buildProfileListener() {
		return new LocalProfileListener();
	}

	protected ConnectionListener buildConnectionListener() {
		return new LocalConnectionListener();
	}
	
	
	// **************** API ***************************************************
	
	/**
	 * @see IJpaDataSource#getConnectionProfileName()
	 */
	public String getConnectionProfileName() {
		return connectionProfileName;
	}
	
	/**
	 * @see IJpaDataSource#setConnectionProfileName(String)
	 */
	public void setConnectionProfileName(String connectionProfileName) {
		if (! connectionProfileName.equals(getConnectionProfileName())) {
			setConnectionProfileNameInternal(connectionProfileName);
			 // set the connection profile when the name changes
			setConnectionProfile(profileNamed(connectionProfileName));
			jpaProject().update();
		}
	}
	
	private ConnectionProfile profileNamed(String name) {
		return ConnectionProfileRepository.instance().profileNamed(name);
	}

	protected void setConnectionProfileNameInternal(String newConnectionProfileName) {
		if (! newConnectionProfileName.equals(this.connectionProfileName)) {
			String oldConnectionProfileName = this.connectionProfileName;
			this.connectionProfileName = newConnectionProfileName;
			firePropertyChanged(CONNECTION_PROFILE_NAME_PROPERTY, oldConnectionProfileName, newConnectionProfileName);
		}
	}
	
	protected void setConnectionProfile(ConnectionProfile newConnectionProfile) {
		if (! newConnectionProfile.equals(this.connectionProfile)) {
			this.connectionProfile.removeConnectionListener(this.connectionListener);
			this.connectionProfile = newConnectionProfile;
			this.connectionProfile.addConnectionListener(this.connectionListener);
		}
	}

	
	/**
	 * @see IJpaDataSource#getConnectionProfile()
	 */
	public ConnectionProfile getConnectionProfile() {
		return this.connectionProfile;
	}
	
	/**
	 * @see IJpaDataSource#isConnected()
	 */
	public boolean isConnected() {
		ConnectionProfile profile = ConnectionProfileRepository.instance().profileNamed(getConnectionProfileName());
		return profile.isConnected();
	}
	
	/**
	 * @see IJpaDataSource#hasAConnection()
	 */
	public boolean hasAConnection() {
		ConnectionProfile profile = ConnectionProfileRepository.instance().profileNamed(getConnectionProfileName());
		return ! profile.isNull();
	}
	
	/**
	 * @see IJpaDataSource#dispose()
	 */
	public void dispose() {
		this.connectionProfile.removeConnectionListener(this.connectionListener);
		ConnectionProfileRepository.instance().removeProfileListener(this.profileListener);
	}
	
	@Override
	public void toString(StringBuffer sb) {
		sb.append("connectionProfileName: ");
		sb.append(connectionProfileName);
	}
	
	
	// **************** member classes ****************************************
	
	/**
	 * Listen for a connection profile with our name being added or removed.
	 * Also listen for our connection's name begin changed.
	 */
	protected class LocalProfileListener implements ProfileListener 
	{	
		protected LocalProfileListener() {
			super();
		}

		public void profileChanged(ConnectionProfile profile) {
			if (profile == JpaDataSource.this.connectionProfile) {
				JpaDataSource.this.setConnectionProfileName(profile.getName());
			}
		}

		public void profileReplaced(ConnectionProfile oldProfile, ConnectionProfile newProfile) {
			if (oldProfile == JpaDataSource.this.connectionProfile) {
				JpaDataSource.this.setConnectionProfile(newProfile);
			}
		}
	}
	
	
	/**
	 * Whenever the connection is opened or closed trigger a project update.
	 */
	protected class LocalConnectionListener implements ConnectionListener 
	{
		protected LocalConnectionListener() {
			super();
		}
		
		public void opened(ConnectionProfile profile) {
			JpaDataSource.this.jpaProject().update();
		}
		
		public void aboutToClose(ConnectionProfile profile) {
			// do nothing
		}
		
		public boolean okToClose(ConnectionProfile profile) {
			return true;
		}
		
		public void closed(ConnectionProfile profile) {
			JpaDataSource.this.jpaProject().update();
		}
		
		public void modified(ConnectionProfile profile) {
			// do nothing
		}
		
		public void databaseChanged(ConnectionProfile profile, Database database) {
			// do nothing
		}
		
		public void schemaChanged(ConnectionProfile profile, Schema schema) {
			// do nothing
		}
		
		public void tableChanged(ConnectionProfile profile, Table table) {
			// do nothing
		}
	}
}
