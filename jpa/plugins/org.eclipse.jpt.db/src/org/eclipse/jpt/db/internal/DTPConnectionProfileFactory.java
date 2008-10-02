/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.util.Iterator;
import java.util.Vector;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.IProfileListener1;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.ConnectionProfileListener;
import org.eclipse.jpt.db.DatabaseFinder;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

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

	public ConnectionProfile buildConnectionProfile(String name, DatabaseFinder finder) {
		for (IConnectionProfile dtpProfile : this.dtpProfileManager.getProfiles()) {
			if (dtpProfile.getName().equals(name)) {
				return new DTPConnectionProfileWrapper(dtpProfile, finder);
			}
		}
		return null;
	}

	public ConnectionProfile buildConnectionProfile(String name) {
		return this.buildConnectionProfile(name, DatabaseFinder.Simple.instance());
	}

	public Iterator<String> connectionProfileNames() {
		return new TransformationIterator<IConnectionProfile, String>(this.dtpConnectionProfiles()) {
			@Override
			protected String transform(IConnectionProfile dtpProfile) {
				 return dtpProfile.getName();
			}
		};
	}

	private Iterator<IConnectionProfile> dtpConnectionProfiles() {
		return new ArrayIterator<IConnectionProfile>(this.dtpProfileManager.getProfiles());
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
	private class LocalProfileListener implements IProfileListener1 {
		private Vector<ConnectionProfileListener> listeners = new Vector<ConnectionProfileListener>();

		LocalProfileListener() {
			super();
		}

		void addConnectionProfileListener(ConnectionProfileListener listener) {
			this.listeners.add(listener);
		}

		void removeConnectionProfileListener(ConnectionProfileListener listener) {
			this.listeners.remove(listener);
		}

		private Iterator<ConnectionProfileListener> listeners() {
			return new CloneIterator<ConnectionProfileListener>(this.listeners);
		}

		// ********** IProfileListener implementation **********

		public void profileAdded(IConnectionProfile dtpProfile) {
			String name = dtpProfile.getName();
			for (Iterator<ConnectionProfileListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().connectionProfileAdded(name);
			}
		}

		public void profileChanged(IConnectionProfile dtpProfile, String oldName, String oldDescription, Boolean oldAutoConnect) {
			String newName = dtpProfile.getName();
			if ( ! newName.equals(oldName)) {
				for (Iterator<ConnectionProfileListener> stream = this.listeners(); stream.hasNext(); ) {
					stream.next().connectionProfileRenamed(oldName, newName);
				}
			}
		}

		public void profileChanged(IConnectionProfile dtpProfile) {
			// this method shouldn't be called on IProfileListener1
			throw new UnsupportedOperationException();
		}

		public void profileDeleted(IConnectionProfile dtpProfile) {
			String name = dtpProfile.getName();
			for (Iterator<ConnectionProfileListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().connectionProfileRemoved(name);
			}
		}

	}

}
