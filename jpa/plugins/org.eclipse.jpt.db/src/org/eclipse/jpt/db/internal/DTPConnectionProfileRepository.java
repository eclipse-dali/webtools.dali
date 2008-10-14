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
import org.eclipse.datatools.connectivity.IProfileListener;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.ConnectionProfileRepository;
import org.eclipse.jpt.db.ConnectionProfileListener;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * Wrap the DTP ProfileManager.
 */
public final class DTPConnectionProfileRepository
	implements ConnectionProfileRepository
{
	private ProfileManager dtpProfileManager;

	private LocalProfileListener profileListener;

	private Vector<DTPConnectionProfileWrapper> connectionProfiles;


	// ********** singleton **********

	private static final DTPConnectionProfileRepository INSTANCE = new DTPConnectionProfileRepository();

	public static DTPConnectionProfileRepository instance() {
		return INSTANCE;
	}

	/**
	 * 'private' to ensure singleton
	 */
	private DTPConnectionProfileRepository() {
		super();
	}


	// ********** lifecycle **********

	/**
	 * called by plug-in
	 */
	public synchronized void start() {
		this.dtpProfileManager = ProfileManager.getInstance();
		this.profileListener = new LocalProfileListener();
	}

	/**
	 * called by plug-in
	 */
	public synchronized void stop() {
		if (this.connectionProfiles != null) {
			for (DTPConnectionProfileWrapper profile : this.connectionProfiles) {
				profile.dispose();
			}
			this.dtpProfileManager.removeProfileListener(this.profileListener);
			this.connectionProfiles = null;
		}
		this.profileListener = null;
		this.dtpProfileManager = null;
	}


	// ********** profiles **********

	private synchronized Vector<DTPConnectionProfileWrapper> getConnectionProfiles() {
		if (this.connectionProfiles == null) {
			this.connectionProfiles = this.buildConnectionProfiles();
			//Add the profile listener after initializing the profiles; otherwise we end up
			//with duplicate connection profiles.  The DTP #loadProfiles() method both
			//loads the profiles and fires event notification for each added profile.
			//This is a temporary measure for bug 246948 and we can hopefully get DTP
			//to fix the underlying issue.
			this.dtpProfileManager.addProfileListener(this.profileListener);
		}
		return this.connectionProfiles;
	}

	private Vector<DTPConnectionProfileWrapper> buildConnectionProfiles() {
		Vector<DTPConnectionProfileWrapper> profiles = new Vector<DTPConnectionProfileWrapper>();
		for (IConnectionProfile dtpProfile : this.dtpProfileManager.getProfiles()) {
			profiles.add(new DTPConnectionProfileWrapper(dtpProfile));
		}
		return profiles;
	}

	public synchronized Iterator<ConnectionProfile> connectionProfiles() {
		return new CloneIterator<ConnectionProfile>(this.getConnectionProfiles());  // read-only
	}

	private synchronized Iterator<DTPConnectionProfileWrapper> connectionProfileWrappers() {
		return new CloneIterator<DTPConnectionProfileWrapper>(this.getConnectionProfiles());  // read-only
	}

	public int connectionProfilesSize() {
		return this.getConnectionProfiles().size();
	}

	public Iterator<String> connectionProfileNames() {
		return new TransformationIterator<DTPConnectionProfileWrapper, String>(this.connectionProfileWrappers()) {
			@Override
			protected String transform(DTPConnectionProfileWrapper profile) {
				 return profile.getName();
			}
		};
	}

	public boolean containsConnectionProfileNamed(String name) {
		return ! this.connectionProfileNamed(name).isNull();
	}

	public ConnectionProfile connectionProfileNamed(String name) {
		for (Iterator<DTPConnectionProfileWrapper> stream = this.connectionProfileWrappers(); stream.hasNext(); ) {
			DTPConnectionProfileWrapper profile = stream.next();
			if (profile.getName().equals(name)) {
				return profile;
			}
		}
		return NullConnectionProfile.instance();
	}

	synchronized DTPConnectionProfileWrapper addConnectionProfile(IConnectionProfile dtpConnectionProfile) {
		for (DTPConnectionProfileWrapper wrapper : this.getConnectionProfiles()) {
			if (wrapper.wraps(dtpConnectionProfile)) {
				throw new IllegalStateException("duplicate connection profile: " + dtpConnectionProfile.getName());  //$NON-NLS-1$
			}
		}
		DTPConnectionProfileWrapper wrapper = new DTPConnectionProfileWrapper(dtpConnectionProfile);
		this.getConnectionProfiles().add(wrapper);
		return wrapper;
	}

	synchronized DTPConnectionProfileWrapper removeConnectionProfile(IConnectionProfile dtpConnectionProfile) {
		for (Iterator<DTPConnectionProfileWrapper> stream = this.getConnectionProfiles().iterator(); stream.hasNext(); ) {
			DTPConnectionProfileWrapper wrapper = stream.next();
			if (wrapper.wraps(dtpConnectionProfile)) {
				stream.remove();
				return wrapper;
			}
		}
		throw new IllegalStateException("invalid connection profile: " + dtpConnectionProfile.getName());  //$NON-NLS-1$
	}

	synchronized DTPConnectionProfileWrapper connectionProfile(IConnectionProfile dtpConnectionProfile) {
		for (DTPConnectionProfileWrapper wrapper : this.getConnectionProfiles()) {
			if (wrapper.wraps(dtpConnectionProfile)) {
				return wrapper;
			}
		}
		throw new IllegalStateException("invalid connection profile: " + dtpConnectionProfile.getName());  //$NON-NLS-1$
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ClassTools.toStringClassNameForObject(this));
		sb.append((this.connectionProfiles != null) ? this.connectionProfiles : "<'connectionProfiles' uninitialized>");  //$NON-NLS-1$
		return sb.toString();
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
	 * Keep the repository in synch with the DTP profile manager
	 * and forward events to the repository's listeners.
	 */
	private class LocalProfileListener implements IProfileListener {
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
			// synch the repository then notify listeners
			DTPConnectionProfileWrapper profile = DTPConnectionProfileRepository.this.addConnectionProfile(dtpProfile);
			for (Iterator<ConnectionProfileListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().connectionProfileReplaced(NullConnectionProfile.instance(), profile);
			}
		}

		public void profileChanged(IConnectionProfile dtpProfile) {
			DTPConnectionProfileWrapper profile = DTPConnectionProfileRepository.this.connectionProfile(dtpProfile);
			for (Iterator<ConnectionProfileListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().connectionProfileChanged(profile);
			}
		}

		public void profileDeleted(IConnectionProfile dtpProfile) {
			// synch the repository then notify listeners
			DTPConnectionProfileWrapper profile = DTPConnectionProfileRepository.this.removeConnectionProfile(dtpProfile);
			for (Iterator<ConnectionProfileListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().connectionProfileReplaced(profile, NullConnectionProfile.instance());
			}
		}

	}

}
