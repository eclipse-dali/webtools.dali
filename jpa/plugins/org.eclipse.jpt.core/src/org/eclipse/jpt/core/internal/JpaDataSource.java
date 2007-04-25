/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.db.internal.Connection;
import org.eclipse.jpt.db.internal.ConnectionProfileRepository;

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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JpaDataSource() {
		super();
	}

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
	public void setConnectionProfileName(String newConnectionProfileName) {
		String oldConnectionProfileName = connectionProfileName;
		connectionProfileName = newConnectionProfileName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaCorePackage.JPA_DATA_SOURCE__CONNECTION_PROFILE_NAME, oldConnectionProfileName, connectionProfileName));
	}

	public Connection getConnection() {
		return ConnectionProfileRepository.instance().getConnectionWithProfileNamed(getConnectionProfileName());
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
} // JpaDataSource
