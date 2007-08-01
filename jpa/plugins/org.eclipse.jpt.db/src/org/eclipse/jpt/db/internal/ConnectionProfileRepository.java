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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.IProfileListener;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 *  ConnectionProfileRepository is a mediator to the DTP ProfileManager.
 */
public class ConnectionProfileRepository {
	private ProfileManager dtpProfileManager;
	
	private LocalRepositoryListener repositoryListener;
	private LocalProfileListener profileListener;
	private Set<ConnectionProfile> profiles;

	private static ConnectionProfileRepository INSTANCE;

	public static final String DATABASE_CATEGORY_ID = "org.eclipse.datatools.connectivity.db.category"; //$NON-NLS-1$

	/**
	 * singleton support
	 */
	public static ConnectionProfileRepository instance() {
		if (INSTANCE == null) {
			INSTANCE = new ConnectionProfileRepository();
		}
		return INSTANCE;
	}
	
	// ********** constructors **********

	private ConnectionProfileRepository() {
		super();
		this.dtpProfileManager = ProfileManager.getInstance();
	}

	// ********** behavior **********
	
	public void initializeListeners() {

		if( this.repositoryListener == null) {
			this.repositoryListener = new LocalRepositoryListener();
			this.dtpProfileManager.addProfileListener( this.repositoryListener);
		}
		if( this.profileListener == null) {
			this.profileListener = new LocalProfileListener();
			this.dtpProfileManager.addProfileListener( this.profileListener);	
		}
	}

	public void disposeListeners() {
		for( Iterator<ConnectionProfile> stream = this.profiles(); stream.hasNext(); ) {
			stream.next().dispose();
		}
		if( this.repositoryListener != null) {
			this.dtpProfileManager.removeProfileListener( this.repositoryListener);
			this.repositoryListener = null;
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
	
	// ********** profiles

	public Iterator<ConnectionProfile> profiles() {
		return this.getProfiles().iterator();
	}

	public Iterator<String> profileNames() {
		return new TransformationIterator<ConnectionProfile, String>( this.profiles()) {
			@Override
			protected String transform( ConnectionProfile profile) {
				 return profile.getName();
			}
		};
	}

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
		Set<ConnectionProfile> result = new HashSet<ConnectionProfile>( dtpProfiles.length);
		for (IConnectionProfile dtpProfile : dtpProfiles) {
			result.add( ConnectionProfile.createProfile( this, dtpProfile));
		}
		return result;
	}

	void addProfile( IConnectionProfile dtpProfile) {
		
		if( !this.profileExists( dtpProfile)) {
			ConnectionProfile newProfile = ConnectionProfile.createProfile( ConnectionProfileRepository.this, dtpProfile);
			this.profiles.add( newProfile);
		}
	}
	
	void removeProfile( IConnectionProfile dtpProfile) {
		
		this.profiles.remove( this.getProfile( dtpProfile));
	}
	
	private boolean profileExists( IConnectionProfile dtpProfile) {

		return ( this.getProfile( dtpProfile) == null) ? false : true;
	}

	ConnectionProfile getProfile( IConnectionProfile dtpProfile) {
		
		for( Iterator<ConnectionProfile> stream = this.profiles(); stream.hasNext(); ) {
			ConnectionProfile profile = stream.next();
			if( profile.wraps( dtpProfile)) {
				return profile;
			}
		}
		return null;
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
	 * Listens to ProfileManager events and updates the repository.
	 */
	private class LocalRepositoryListener implements IProfileListener {

		LocalRepositoryListener() {
			super();
		}

		public void profileAdded( IConnectionProfile connectionProfile) {
			ConnectionProfileRepository.this.addProfile( connectionProfile);
		}

		public void profileChanged( IConnectionProfile connectionProfile) {
			// do nothing
		}

		public void profileDeleted( IConnectionProfile connectionProfile) {
			ConnectionProfileRepository.this.removeProfile( connectionProfile);
		}
	}
	

	/**
	 * This listener translates and forwards IProfileListener events to ProfileListener.
	 */
	private class LocalProfileListener implements IProfileListener {
		private Collection<ProfileListener> listeners = new ArrayList<ProfileListener>();

		LocalProfileListener() {
			super();
		}

		void addProfileListener( ProfileListener listener) {
			this.listeners.add( listener);
		}

		void removeProfileListener( ProfileListener listener) {
			this.listeners.remove( listener);
		}
		
		// ********** behavior **********
		
		public void profileAdded( IConnectionProfile dtpProfile) {
			ConnectionProfile profile = getProfile( dtpProfile);
			for (ProfileListener listener : this.listeners) {
				listener.profileAdded( profile);
			}
		}

		public void profileChanged( IConnectionProfile dtpProfile) {
			ConnectionProfile profile = getProfile( dtpProfile);
			for (ProfileListener listener : this.listeners) {
				listener.profileChanged( profile);
			}
		}

		public void profileDeleted( IConnectionProfile dtpProfile) {
			String profileName = dtpProfile.getName();
			for (ProfileListener listener : this.listeners) {
				listener.profileDeleted( profileName);
			}
		}
	}
}


