/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.IProfileListener;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 *  ConnectionProfileRepository is a mediator to the DTP ProfileManager.
 */
public class ConnectionProfileRepository {
	private final ProfileManager dtpProfileManager;

	private LocalProfileListener profileListener;

	// lazily-initialized
	private Set<ConnectionProfile> profiles;

	public static final String DATABASE_CATEGORY_ID = "org.eclipse.datatools.connectivity.db.category"; //$NON-NLS-1$

	// ********** singleton **********

	private static final ConnectionProfileRepository INSTANCE = new ConnectionProfileRepository();

	public static ConnectionProfileRepository instance() {
		return INSTANCE;
	}

	// ********** constructor **********

	private ConnectionProfileRepository() {
		super();
		this.dtpProfileManager = ProfileManager.getInstance();
	}

	// ********** behavior **********
	
	public void start() {
		if( this.profileListener == null) {
			this.profileListener = new LocalProfileListener();
			this.dtpProfileManager.addProfileListener( this.profileListener);	
		}
	}

	public void stop() {
		for( Iterator<ConnectionProfile> stream = this.profiles(); stream.hasNext(); ) {
			stream.next().dispose();
		}
		if( this.profileListener != null) {
			this.dtpProfileManager.removeProfileListener( this.profileListener);
			this.profileListener = null;
		}
	}
	
	@Override
	public String toString() {
		return this.profiles.toString();
	}
	
	// ********** profiles **********

	public Iterator<ConnectionProfile> profiles() {
		return new CloneIterator<ConnectionProfile>(this.getProfiles());  // read-only
	}

	public Iterator<String> profileNames() {
		return new TransformationIterator<ConnectionProfile, String>( this.profiles()) {
			@Override
			protected String transform( ConnectionProfile profile) {
				 return profile.getName();
			}
		};
	}

	/**
	 * Never return null.
	 */
	public ConnectionProfile profileNamed( String name) {
		for( Iterator<ConnectionProfile> stream = this.profiles(); stream.hasNext(); ) {
			ConnectionProfile profile = stream.next();
			if( profile.getName().equals( name)) {
				return profile;
			}
		}
		return NullConnectionProfile.instance();
	}

	private Set<ConnectionProfile> getProfiles() {
		if( this.profiles == null) {
			this.profiles = this.buildProfiles();
		}
		return this.profiles;
	}

	private Set<ConnectionProfile> buildProfiles() {
		IConnectionProfile[] dtpProfiles = this.dtpProfileManager.getProfiles();
		Set<ConnectionProfile> result = Collections.synchronizedSet(new HashSet<ConnectionProfile>( dtpProfiles.length));
		for (IConnectionProfile dtpProfile : dtpProfiles) {
			result.add( ConnectionProfile.createProfile( this, dtpProfile));
		}
		return result;
	}

	ConnectionProfile addProfile( IConnectionProfile dtpProfile) {
		ConnectionProfile cp = this.profile(dtpProfile);
		if (cp.isNull()) {
			cp = ConnectionProfile.createProfile(ConnectionProfileRepository.this, dtpProfile);
			this.profiles.add(cp);
		}
		return cp;
	}

	ConnectionProfile removeProfile( IConnectionProfile dtpProfile) {
		ConnectionProfile cp = this.profile(dtpProfile);
		this.profiles.remove(cp);
		return cp;
	}

	/**
	 * Never return null.
	 */
	ConnectionProfile profile( IConnectionProfile dtpProfile) {
		for( Iterator<ConnectionProfile> stream = this.profiles(); stream.hasNext(); ) {
			ConnectionProfile profile = stream.next();
			if( profile.wraps( dtpProfile)) {
				return profile;
			}
		}
		return NullConnectionProfile.instance();
	}

	// ********** listeners **********

	public void addProfileListener( ProfileListener listener) {

		this.profileListener.addProfileListener( listener);
	}

	public void removeProfileListener( ProfileListener listener) {
		
		this.profileListener.removeProfileListener( listener);
	}

	@SuppressWarnings("unused")
	private void addInternalProfileListener( IProfileListener listener) {

		this.dtpProfileManager.addProfileListener( listener);
	}

	@SuppressWarnings("unused")
	private void removeInternalProfileListener( IProfileListener listener) {
		
		this.dtpProfileManager.removeProfileListener( listener);
	}

	// ********** member class **********
	/**
	 * Keep the repository in synch with the DTP profile manager
	 * and forward events to the repositories listeners.
	 */
	private class LocalProfileListener implements IProfileListener {
		private Collection<ProfileListener> listeners = new Vector<ProfileListener>();

		LocalProfileListener() {
			super();
		}

		void addProfileListener( ProfileListener listener) {
			this.listeners.add( listener);
		}

		void removeProfileListener( ProfileListener listener) {
			this.listeners.remove( listener);
		}

		private Iterator<ProfileListener> listeners() {
			return new CloneIterator<ProfileListener>(this.listeners);
		}

		// ********** IProfileListener implementation **********

		public void profileAdded( IConnectionProfile dtpProfile) {
			// synch the repository then notify listeners
			ConnectionProfile profile = ConnectionProfileRepository.this.addProfile(dtpProfile);
			for (Iterator<ProfileListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().profileReplaced(NullConnectionProfile.instance(), profile);
			}
		}

		public void profileChanged( IConnectionProfile dtpProfile) {
			ConnectionProfile profile = ConnectionProfileRepository.this.profile(dtpProfile);
			for (Iterator<ProfileListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().profileChanged(profile);
			}
		}

		public void profileDeleted( IConnectionProfile dtpProfile) {
			// synch the repository then notify listeners
			ConnectionProfile profile = ConnectionProfileRepository.this.removeProfile(dtpProfile);
			for (Iterator<ProfileListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().profileReplaced(profile, NullConnectionProfile.instance());
			}
		}

	}

}
