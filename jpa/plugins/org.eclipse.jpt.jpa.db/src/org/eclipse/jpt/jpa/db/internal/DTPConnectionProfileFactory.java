/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal;

import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.IProfileListener1;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;
import org.eclipse.jpt.jpa.db.ConnectionProfileListener;
import org.eclipse.jpt.jpa.db.DatabaseIdentifierAdapter;

/**
 * Wrap the DTP ProfileManager in yet another singleton.
 */
public final class DTPConnectionProfileFactory
	implements ConnectionProfileFactory
{
	private ProfileManager dtpProfileManager;

	private LocalProfileListener profileListener;


	// ********** singleton **********

	private static final DTPConnectionProfileFactory INSTANCE = new DTPConnectionProfileFactory();

	public static DTPConnectionProfileFactory instance() {
		return INSTANCE;
	}

	/**
	 * 'private' to ensure singleton
	 */
	private DTPConnectionProfileFactory() {
		super();
	}


	// ********** lifecycle **********

	/**
	 * called by plug-in
	 */
	public synchronized void start() {
		this.dtpProfileManager = ProfileManager.getInstance();
		this.profileListener = new LocalProfileListener();
		this.dtpProfileManager.addProfileListener(this.profileListener);
	}

	/**
	 * called by plug-in
	 */
	public synchronized void stop() {
		this.dtpProfileManager.removeProfileListener(this.profileListener);
		this.profileListener = null;
		this.dtpProfileManager = null;
	}


	// ********** connection profiles **********

	public synchronized ConnectionProfile buildConnectionProfile(String name, DatabaseIdentifierAdapter adapter) {
		for (IConnectionProfile dtpProfile : this.dtpProfileManager.getProfiles()) {
			if (dtpProfile.getName().equals(name)) {
				return new DTPConnectionProfileWrapper(dtpProfile, adapter);
			}
		}
		return null;
	}

	public ConnectionProfile buildConnectionProfile(String name) {
		return this.buildConnectionProfile(name, DatabaseIdentifierAdapter.Default.instance());
	}

	public Iterable<String> getConnectionProfileNames() {
		return new TransformationIterable<IConnectionProfile, String>(this.getDTPConnectionProfiles()) {
			@Override
			protected String transform(IConnectionProfile dtpProfile) {
				 return dtpProfile.getName();
			}
		};
	}

	private synchronized Iterable<IConnectionProfile> getDTPConnectionProfiles() {
		return new ArrayIterable<IConnectionProfile>(this.dtpProfileManager.getProfiles());
	}


	// ********** listeners **********

	public void addConnectionProfileListener(ConnectionProfileListener listener) {
		this.profileListener.addConnectionProfileListener(listener);
	}

	public void removeConnectionProfileListener(ConnectionProfileListener listener) {
		this.profileListener.removeConnectionProfileListener(listener);
	}


	// ********** listener **********

	/**
	 * Forward events to the factory's listeners.
	 */
	private static class LocalProfileListener implements IProfileListener1 {
		private ListenerList<ConnectionProfileListener> listenerList = new ListenerList<ConnectionProfileListener>(ConnectionProfileListener.class);

		LocalProfileListener() {
			super();
		}

		void addConnectionProfileListener(ConnectionProfileListener listener) {
			this.listenerList.add(listener);
		}

		void removeConnectionProfileListener(ConnectionProfileListener listener) {
			this.listenerList.remove(listener);
		}

		// ********** IProfileListener implementation **********

		public void profileAdded(IConnectionProfile dtpProfile) {
			String name = dtpProfile.getName();
			for (ConnectionProfileListener listener : this.listenerList.getListeners()) {
				listener.connectionProfileAdded(name);
			}
		}

		public void profileChanged(IConnectionProfile dtpProfile, String oldName, String oldDescription, Boolean oldAutoConnect) {
			String newName = dtpProfile.getName();
			if ( ! newName.equals(oldName)) {
				for (ConnectionProfileListener listener : this.listenerList.getListeners()) {
					listener.connectionProfileRenamed(oldName, newName);
				}
			}
		}

		public void profileChanged(IConnectionProfile dtpProfile) {
			// this method shouldn't be called on IProfileListener1
			throw new UnsupportedOperationException();
		}

		public void profileDeleted(IConnectionProfile dtpProfile) {
			String name = dtpProfile.getName();
			for (ConnectionProfileListener listener : this.listenerList.getListeners()) {
				listener.connectionProfileRemoved(name);
			}
		}

	}

}
