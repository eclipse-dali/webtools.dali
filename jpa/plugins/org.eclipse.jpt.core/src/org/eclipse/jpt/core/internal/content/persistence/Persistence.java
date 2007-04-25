/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.persistence;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.XmlEObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Persistence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.Persistence#getPersistenceUnits <em>Persistence Units</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.Persistence#getVersion <em>Version</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.Persistence#getRoot <em>Root</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistence()
 * @model kind="class"
 * @generated
 */
public class Persistence extends XmlEObject
{
	/**
	 * The cached value of the '{@link #getPersistenceUnits() <em>Persistence Units</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistenceUnits()
	 * @generated
	 * @ordered
	 */
	protected EList<PersistenceUnit> persistenceUnits;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRoot() <em>Root</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoot()
	 * @generated
	 * @ordered
	 */
	protected PersistenceXmlRootContentNode root;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Persistence() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PersistencePackage.Literals.PERSISTENCE;
	}

	/**
	 * Returns the value of the '<em><b>Persistence Units</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Units</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistence_PersistenceUnits()
	 * @model type="org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit" containment="true"
	 * @generated
	 */
	public EList<PersistenceUnit> getPersistenceUnits() {
		if (persistenceUnits == null) {
			persistenceUnits = new EObjectContainmentEList<PersistenceUnit>(PersistenceUnit.class, this, PersistencePackage.PERSISTENCE__PERSISTENCE_UNITS);
		}
		return persistenceUnits;
	}

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistence_Version()
	 * @model unique="false" dataType="org.eclipse.jpt.core.internal.content.persistence.Version" required="true"
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.Persistence#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE__VERSION, oldVersion, version));
	}

	/**
	 * Returns the value of the '<em><b>Root</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceXmlRootContentNode#getPersistence <em>Persistence</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root</em>' reference.
	 * @see #setRoot(PersistenceXmlRootContentNode)
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistence_Root()
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistenceXmlRootContentNode#getPersistence
	 * @model opposite="persistence" resolveProxies="false" required="true" ordered="false"
	 * @generated
	 */
	public PersistenceXmlRootContentNode getRoot() {
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRoot(PersistenceXmlRootContentNode newRoot, NotificationChain msgs) {
		PersistenceXmlRootContentNode oldRoot = root;
		root = newRoot;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE__ROOT, oldRoot, newRoot);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.Persistence#getRoot <em>Root</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root</em>' reference.
	 * @see #getRoot()
	 * @generated
	 */
	public void setRoot(PersistenceXmlRootContentNode newRoot) {
		if (newRoot != root) {
			NotificationChain msgs = null;
			if (root != null)
				msgs = ((InternalEObject) root).eInverseRemove(this, PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__PERSISTENCE, PersistenceXmlRootContentNode.class, msgs);
			if (newRoot != null)
				msgs = ((InternalEObject) newRoot).eInverseAdd(this, PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__PERSISTENCE, PersistenceXmlRootContentNode.class, msgs);
			msgs = basicSetRoot(newRoot, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE__ROOT, newRoot, newRoot));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PersistencePackage.PERSISTENCE__ROOT :
				if (root != null)
					msgs = ((InternalEObject) root).eInverseRemove(this, PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__PERSISTENCE, PersistenceXmlRootContentNode.class, msgs);
				return basicSetRoot((PersistenceXmlRootContentNode) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PersistencePackage.PERSISTENCE__PERSISTENCE_UNITS :
				return ((InternalEList<?>) getPersistenceUnits()).basicRemove(otherEnd, msgs);
			case PersistencePackage.PERSISTENCE__ROOT :
				return basicSetRoot(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PersistencePackage.PERSISTENCE__PERSISTENCE_UNITS :
				return getPersistenceUnits();
			case PersistencePackage.PERSISTENCE__VERSION :
				return getVersion();
			case PersistencePackage.PERSISTENCE__ROOT :
				return getRoot();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PersistencePackage.PERSISTENCE__PERSISTENCE_UNITS :
				getPersistenceUnits().clear();
				getPersistenceUnits().addAll((Collection<? extends PersistenceUnit>) newValue);
				return;
			case PersistencePackage.PERSISTENCE__VERSION :
				setVersion((String) newValue);
				return;
			case PersistencePackage.PERSISTENCE__ROOT :
				setRoot((PersistenceXmlRootContentNode) newValue);
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
			case PersistencePackage.PERSISTENCE__PERSISTENCE_UNITS :
				getPersistenceUnits().clear();
				return;
			case PersistencePackage.PERSISTENCE__VERSION :
				setVersion(VERSION_EDEFAULT);
				return;
			case PersistencePackage.PERSISTENCE__ROOT :
				setRoot((PersistenceXmlRootContentNode) null);
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
			case PersistencePackage.PERSISTENCE__PERSISTENCE_UNITS :
				return persistenceUnits != null && !persistenceUnits.isEmpty();
			case PersistencePackage.PERSISTENCE__VERSION :
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case PersistencePackage.PERSISTENCE__ROOT :
				return root != null;
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
		result.append(" (version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

	/**
	 * Override this because Persistence does not have an eContainer()
	 * This is because persistence is the "root" feature of the doc for xml Translators
	 * and thus cannot be "contained"
	 */
	@Override
	public IJpaProject getJpaProject() {
		IJpaFile file = getJpaFile();
		return (file == null) ? null : file.getJpaProject();
	}
}