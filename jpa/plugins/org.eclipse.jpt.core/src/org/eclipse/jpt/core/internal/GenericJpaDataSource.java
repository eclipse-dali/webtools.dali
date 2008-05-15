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
import org.eclipse.jpt.db.ConnectionAdapter;
import org.eclipse.jpt.db.ConnectionListener;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.ConnectionProfileListener;
import org.eclipse.jpt.db.ConnectionProfileRepository;
import org.eclipse.jpt.db.JptDbPlugin;

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

	/**
	 * this should never be null; if we do not have a connection, this will be
	 * a "null" connection profile
	 */
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

	private ConnectionProfileRepository getConnectionProfileRepository() {
		return JptDbPlugin.instance().getConnectionProfileRepository();
	}

	public GenericJpaDataSource(JpaProject jpaProject, String connectionProfileName) {
		super(jpaProject);

		this.connectionProfileListener = this.buildConnectionProfileListener();
		this.getConnectionProfileRepository().addConnectionProfileListener(this.connectionProfileListener);

		this.connectionListener = this.buildConnectionListener();
		this.connectionProfileName = connectionProfileName;
		this.connectionProfile = this.connectionProfileNamed(connectionProfileName);
		this.connectionProfile.addConnectionListener(this.connectionListener);
	}

	protected ConnectionProfileListener buildConnectionProfileListener() {
		return new LocalConnectionProfileListener();
	}

	protected ConnectionListener buildConnectionListener() {
		return new LocalConnectionListener();
	}


	// ********** IJpaDataSource implementation **********

	public String getConnectionProfileName() {
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
	public ConnectionProfile getConnectionProfile() {
		return this.connectionProfile;
	}

	public boolean hasAConnection() {
		return ! this.connectionProfile.isNull();
	}

	public void dispose() {
		this.connectionProfile.removeConnectionListener(this.connectionListener);
		this.getConnectionProfileRepository().removeConnectionProfileListener(this.connectionProfileListener);
	}


	// ********** internal methods **********

	private ConnectionProfile connectionProfileNamed(String name) {
		return this.getConnectionProfileRepository().connectionProfileNamed(name);
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
	public boolean connectionProfileIsActive() {
		return this.connectionProfile.isActive();
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
	protected class LocalConnectionProfileListener implements ConnectionProfileListener {
		protected LocalConnectionProfileListener() {
			super();
		}

		// possible name change
		public void connectionProfileChanged(ConnectionProfile profile) {
			if (profile == GenericJpaDataSource.this.connectionProfile) {
				GenericJpaDataSource.this.setConnectionProfileName(profile.getName());
			}
		}

		// profile added or removed
		public void connectionProfileReplaced(ConnectionProfile oldProfile, ConnectionProfile newProfile) {
			if (GenericJpaDataSource.this.hasAConnection() &&
				(oldProfile == GenericJpaDataSource.this.connectionProfile)) {
					GenericJpaDataSource.this.setConnectionProfile(newProfile);
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
