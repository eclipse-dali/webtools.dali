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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.ConnectionProfileRepository;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.ProfileListener;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Jpa Data Source</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.JpaDataSource#getConnectionProfileName <em>Connection Profile Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaDataSource()
 * @model kind="class"
 * @generated
 */
public class JpaDataSource extends JpaEObject implements IJpaDataSource
{
	// temporary bridge until we remove EMF stuff
	private IJpaProject jpaProject;

	/**
	 * The default value of the '{@link #getConnectionProfileName() <em>Connection Profile Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectionProfileName()
	 * @generated
	 * @ordered
	 */
	protected static final String CONNECTION_PROFILE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConnectionProfileName() <em>Connection Profile Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectionProfileName()
	 * @generated
	 * @ordered
	 */
	protected String connectionProfileName = CONNECTION_PROFILE_NAME_EDEFAULT;
	// cache the connection profile name so we can detect when it changes and fire events

	// this should never be null
	protected transient ConnectionProfile connectionProfile;

	protected final ProfileListener profileListener;

	protected final ConnectionListener connectionListener;

	// ********** constructor/initialization **********
	protected JpaDataSource() {
		super();
		this.profileListener = this.buildProfileListener();
		ConnectionProfileRepository.instance().addProfileListener(this.profileListener);
		this.connectionListener = this.buildConnectionListener();
	}

	protected JpaDataSource(IJpaProject jpaProject, String connectionProfileName) {
		this();
		this.jpaProject = jpaProject;
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

	// ********** EMF stuff **********
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaCorePackage.Literals.JPA_DATA_SOURCE;
	}

	/**
	 * Returns the value of the '<em><b>Connection Profile Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connection Profile Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connection Profile Name</em>' attribute.
	 * @see #setConnectionProfileName(String)
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaDataSource_ConnectionProfileName()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	public String getConnectionProfileName() {
		return connectionProfileName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.JpaDataSource#getConnectionProfileName <em>Connection Profile Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connection Profile Name</em>' attribute.
	 * @see #getConnectionProfileName()
	 * @generated
	 */
	public void setConnectionProfileNameGen(String newConnectionProfileName) {
		String oldConnectionProfileName = connectionProfileName;
		connectionProfileName = newConnectionProfileName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaCorePackage.JPA_DATA_SOURCE__CONNECTION_PROFILE_NAME, oldConnectionProfileName, connectionProfileName));
	}

	/**
	 * set the connection profile when the name changes
	 */
	public void setConnectionProfileName(String connectionProfileName) {
		if (!connectionProfileName.equals(this.connectionProfileName)) {
			this.setConnectionProfileNameGen(connectionProfileName);
			this.setConnectionProfile(this.profileNamed(connectionProfileName));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaCorePackage.JPA_DATA_SOURCE__CONNECTION_PROFILE_NAME :
				return getConnectionProfileName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JpaCorePackage.JPA_DATA_SOURCE__CONNECTION_PROFILE_NAME :
				setConnectionProfileName((String) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case JpaCorePackage.JPA_DATA_SOURCE__CONNECTION_PROFILE_NAME :
				setConnectionProfileName(CONNECTION_PROFILE_NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case JpaCorePackage.JPA_DATA_SOURCE__CONNECTION_PROFILE_NAME :
				return CONNECTION_PROFILE_NAME_EDEFAULT == null ? connectionProfileName != null : !CONNECTION_PROFILE_NAME_EDEFAULT.equals(connectionProfileName);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();
		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (connectionProfileName: ");
		result.append(connectionProfileName);
		result.append(')');
		return result.toString();
	}

	// ********** non-EMF stuff **********
	public IJpaProject getProject() {
		return this.jpaProject;
	}

	public ConnectionProfile getConnectionProfile() {
		return this.connectionProfile;
	}

	private ConnectionProfile profileNamed(String name) {
		return ConnectionProfileRepository.instance().profileNamed(name);
	}

	void setConnectionProfile(ConnectionProfile profile) {
		if (this.connectionProfile != profile) {
			this.connectionProfile.removeConnectionListener(this.connectionListener);
			this.connectionProfile = profile;
			this.connectionProfile.addConnectionListener(this.connectionListener);
			this.getProject().update();
		}
	}

	@Override
	public boolean isConnected() {
		return this.connectionProfile.isConnected();
	}

	public boolean hasAConnection() {
		return this.connectionProfile.isNull();
	}

	public void dispose() {
		this.connectionProfile.removeConnectionListener(this.connectionListener);
		ConnectionProfileRepository.instance().removeProfileListener(this.profileListener);
	}

	// ********** member class **********

	/**
	 * Listen for a connection profile with our name being added or removed.
	 * Also listen for our connection's name begin changed.
	 */
	protected class LocalProfileListener implements ProfileListener {

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
	protected class LocalConnectionListener implements ConnectionListener {

		protected LocalConnectionListener() {
			super();
		}

		public void opened(ConnectionProfile profile) {
			JpaDataSource.this.getProject().update();
		}

		public void aboutToClose(ConnectionProfile profile) {
			// do nothing
		}

		public boolean okToClose(ConnectionProfile profile) {
			return true;
		}

		public void closed(ConnectionProfile profile) {
			JpaDataSource.this.getProject().update();
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
