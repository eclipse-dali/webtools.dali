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
public class JpaDataSource
	extends JpaNode
	implements IJpaDataSource
{
	/**
	 * cache the connection profile name so we can detect when
	 * it changes and notify listeners
	 */
	protected String connectionProfileName;

	/**
	 * this should never be null; if we do not have a connection, this will be
	 * a "null" connection profile
	 */
	protected transient ConnectionProfile connectionProfile;

	/**
	 * listen for the connection to be added or removed or its name changed
	 */
	protected final ProfileListener profileListener;

	/**
	 * listen for the connection to be opened or closed
	 */
	protected final ConnectionListener connectionListener;


	// ********** constructor/initialization **********

	public JpaDataSource(IJpaProject jpaProject, String connectionProfileName) {
		super(jpaProject);

		this.profileListener = this.buildProfileListener();
		ConnectionProfileRepository.instance().addProfileListener(this.profileListener);

		this.connectionListener = this.buildConnectionListener();
		this.connectionProfileName = connectionProfileName;
		this.connectionProfile = this.connectionProfileNamed(connectionProfileName);
		this.connectionProfile.addConnectionListener(this.connectionListener);
	}

	protected ProfileListener buildProfileListener() {
		return new LocalProfileListener();
	}

	protected ConnectionListener buildConnectionListener() {
		return new LocalConnectionListener();
	}


	// ********** IJpaDataSource implementation **********

	public String connectionProfileName() {
		return this.connectionProfileName;
	}

	public void setConnectionProfileName(String connectionProfileName) {
		String old = this.connectionProfileName;
		this.connectionProfileName = connectionProfileName;
		this.firePropertyChanged(CONNECTION_PROFILE_NAME_PROPERTY, old, connectionProfileName);
		 // synch the connection profile when the name changes
		this.setConnectionProfile(this.connectionProfileNamed(connectionProfileName));
	}

	@Override
	public ConnectionProfile connectionProfile() {
		return this.connectionProfile;
	}

	public boolean hasAConnection() {
		return ! this.connectionProfile.isNull();
	}

	public void dispose() {
		this.connectionProfile.removeConnectionListener(this.connectionListener);
		ConnectionProfileRepository.instance().removeProfileListener(this.profileListener);
	}


	// ********** internal methods **********

	private ConnectionProfile connectionProfileNamed(String name) {
		return ConnectionProfileRepository.instance().profileNamed(name);
	}

	protected void setConnectionProfile(ConnectionProfile connectionProfile) {
		ConnectionProfile old = this.connectionProfile;
		this.connectionProfile.removeConnectionListener(this.connectionListener);
		this.connectionProfile = connectionProfile;
		this.connectionProfile.addConnectionListener(this.connectionListener);
		this.firePropertyChanged(CONNECTION_PROFILE_PROPERTY, old, connectionProfile);
	}


	// ********** overrides **********

	@Override
	public boolean isConnected() {
		return this.connectionProfile.isConnected();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.connectionProfileName);
	}


	// ********** member classes **********

	/**
	 * Listen for a connection profile with our name being added or removed.
	 * Also listen for our connection's name being changed.
	 */
	protected class LocalProfileListener implements ProfileListener {
		protected LocalProfileListener() {
			super();
		}

		// possible name change
		public void profileChanged(ConnectionProfile profile) {
			if (profile == JpaDataSource.this.connectionProfile) {
				JpaDataSource.this.setConnectionProfileName(profile.getName());
			}
		}

		// profile added or removed
		public void profileReplaced(ConnectionProfile oldProfile, ConnectionProfile newProfile) {
			if (oldProfile == JpaDataSource.this.connectionProfile) {
				JpaDataSource.this.setConnectionProfile(newProfile);
			}
		}

	}


	/**
	 * Whenever the connection is opened or closed trigger a project update.
	 */
	protected class LocalConnectionListener implements ConnectionListener {

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
