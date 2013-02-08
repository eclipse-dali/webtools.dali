/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.IProfileListener1;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.datatools.enablement.jdt.classpath.DriverClasspathContainer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;
import org.eclipse.jpt.jpa.db.ConnectionProfileListener;
import org.eclipse.jpt.jpa.db.DatabaseIdentifierAdapter;

/**
 * Wrap the DTP {@link ProfileManager}.
 */
public final class DTPConnectionProfileFactory
	implements ConnectionProfileFactory
{
	private final IWorkspace workspace;

	private final ProfileManager dtpProfileManager;

	private final LocalProfileListener profileListener;


	public DTPConnectionProfileFactory(IWorkspace workspace) {
		super();
		this.workspace = workspace;
		this.dtpProfileManager = ProfileManager.getInstance();
		this.profileListener = this.buildProfileListener();
		this.dtpProfileManager.addProfileListener(this.profileListener);
	}


	// ********** connection profiles **********

	public synchronized ConnectionProfile buildConnectionProfile(String name, DatabaseIdentifierAdapter adapter) {
		for (IConnectionProfile dtpProfile : this.dtpProfileManager.getProfiles()) {
			if (dtpProfile.getName().equals(name)) {
				return this.buildConnectionProfile(dtpProfile, adapter);
			}
		}
		return null;
	}

	private ConnectionProfile buildConnectionProfile(IConnectionProfile dtpProfile, DatabaseIdentifierAdapter adapter) {
		return new DTPConnectionProfileWrapper(dtpProfile, adapter);
	}

	public ConnectionProfile buildConnectionProfile(String name) {
		return this.buildConnectionProfile(name, DatabaseIdentifierAdapter.Default.instance());
	}

	public Iterable<String> getConnectionProfileNames() {
		return IterableTools.transform(this.getDTPConnectionProfiles(), DTP_CONNECTION_PROFILE_NAME_TRANSFORMER);
	}

	public static final Transformer<IConnectionProfile, String> DTP_CONNECTION_PROFILE_NAME_TRANSFORMER = new DTPConnectionProfileNameTransformer();
	public static class DTPConnectionProfileNameTransformer
		extends TransformerAdapter<IConnectionProfile, String>
	{
		@Override
		public String transform(IConnectionProfile connectionProfile) {
			return connectionProfile.getName();
		}
	}

	private synchronized Iterable<IConnectionProfile> getDTPConnectionProfiles() {
		return IterableTools.iterable(this.dtpProfileManager.getProfiles());
	}


	// ********** listeners **********

	public void addConnectionProfileListener(ConnectionProfileListener listener) {
		this.profileListener.addConnectionProfileListener(listener);
	}

	public void removeConnectionProfileListener(ConnectionProfileListener listener) {
		this.profileListener.removeConnectionProfileListener(listener);
	}

	private LocalProfileListener buildProfileListener() {
		return new LocalProfileListener();
	}


	// ********** misc **********

	public IWorkspace getWorkspace() {
		return this.workspace;
	}

	public IClasspathContainer buildDriverClasspathContainer(String driverName) {
		return new DriverClasspathContainer(driverName);
	}

	/**
	 * @see org.eclipse.jpt.jpa.db.internal.plugin.JptJpaDbPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void dispose() {
		this.dtpProfileManager.removeProfileListener(this.profileListener);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}


	// ********** listener **********

	/**
	 * Forward events to the factory's listeners.
	 */
	private static class LocalProfileListener
		implements IProfileListener1
	{
		private final ListenerList<ConnectionProfileListener> listenerList = new ListenerList<ConnectionProfileListener>(ConnectionProfileListener.class);

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
