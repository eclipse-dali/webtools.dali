/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.persistence;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.JpaFile;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.content.persistence.resource.IPersistenceXmlContentNodes;
import org.eclipse.jpt.core.internal.content.persistence.resource.PersistenceResource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Persistence Xml Root Content Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceXmlRootContentNode#getPersistence <em>Persistence</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceXmlRootContentNode()
 * @model kind="class"
 * @generated
 */
public class PersistenceXmlRootContentNode extends XmlEObject
	implements IJpaRootContentNode
{
	/**
	 * The cached value of the '{@link #getPersistence() <em>Persistence</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistence()
	 * @generated
	 * @ordered
	 */
	protected Persistence persistence;
	
	private PersistenceResource resource;
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PersistenceXmlRootContentNode() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PersistencePackage.Literals.PERSISTENCE_XML_ROOT_CONTENT_NODE;
	}

	/**
	 * Returns the value of the '<em><b>Jpa File</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.jpt.core.internal.JpaFile#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jpa File</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jpa File</em>' container reference.
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getIJpaRootContentNode_JpaFile()
	 * @see org.eclipse.jpt.core.internal.JpaFile#getContent
	 * @model opposite="content" transient="false" changeable="false"
	 * @generated
	 */
	public IJpaFile getJpaFile() {
		if (eContainerFeatureID != PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__JPA_FILE)
			return null;
		return (IJpaFile) eContainer();
	}

	public IResource getResource() {
		return getJpaFile().getResource();
	}

	/**
	 * Returns the value of the '<em><b>Persistence</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.jpt.core.internal.content.persistence.Persistence#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistence</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence</em>' reference.
	 * @see #setPersistence(Persistence)
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceXmlRootContentNode_Persistence()
	 * @see org.eclipse.jpt.core.internal.content.persistence.Persistence#getRoot
	 * @model opposite="root"
	 * @generated
	 */
	public Persistence getPersistence() {
		if (persistence != null && persistence.eIsProxy()) {
			InternalEObject oldPersistence = (InternalEObject) persistence;
			persistence = (Persistence) eResolveProxy(oldPersistence);
			if (persistence != oldPersistence) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__PERSISTENCE, oldPersistence, persistence));
			}
		}
		return persistence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Persistence basicGetPersistence() {
		return persistence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistence(Persistence newPersistence, NotificationChain msgs) {
		Persistence oldPersistence = persistence;
		persistence = newPersistence;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__PERSISTENCE, oldPersistence, newPersistence);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceXmlRootContentNode#getPersistence <em>Persistence</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistence</em>' reference.
	 * @see #getPersistence()
	 * @generated
	 */
	public void setPersistence(Persistence newPersistence) {
		if (newPersistence != persistence) {
			NotificationChain msgs = null;
			if (persistence != null)
				msgs = ((InternalEObject) persistence).eInverseRemove(this, PersistencePackage.PERSISTENCE__ROOT, Persistence.class, msgs);
			if (newPersistence != null)
				msgs = ((InternalEObject) newPersistence).eInverseAdd(this, PersistencePackage.PERSISTENCE__ROOT, Persistence.class, msgs);
			msgs = basicSetPersistence(newPersistence, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__PERSISTENCE, newPersistence, newPersistence));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__JPA_FILE :
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__JPA_FILE, msgs);
			case PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__PERSISTENCE :
				if (persistence != null)
					msgs = ((InternalEObject) persistence).eInverseRemove(this, PersistencePackage.PERSISTENCE__ROOT, Persistence.class, msgs);
				return basicSetPersistence((Persistence) otherEnd, msgs);
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
			case PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__JPA_FILE :
				return eBasicSetContainer(null, PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__JPA_FILE, msgs);
			case PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__PERSISTENCE :
				return basicSetPersistence(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID) {
			case PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__JPA_FILE :
				return eInternalContainer().eInverseRemove(this, JpaCorePackage.JPA_FILE__CONTENT, JpaFile.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__JPA_FILE :
				return getJpaFile();
			case PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__PERSISTENCE :
				if (resolve)
					return getPersistence();
				return basicGetPersistence();
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
			case PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__PERSISTENCE :
				setPersistence((Persistence) newValue);
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
			case PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__PERSISTENCE :
				setPersistence((Persistence) null);
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
			case PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__JPA_FILE :
				return getJpaFile() != null;
			case PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__PERSISTENCE :
				return persistence != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == IJpaContentNode.class) {
			switch (derivedFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IJpaRootContentNode.class) {
			switch (derivedFeatureID) {
				case PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__JPA_FILE :
					return JpaCorePackage.IJPA_ROOT_CONTENT_NODE__JPA_FILE;
				default :
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == IJpaContentNode.class) {
			switch (baseFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IJpaRootContentNode.class) {
			switch (baseFeatureID) {
				case JpaCorePackage.IJPA_ROOT_CONTENT_NODE__JPA_FILE :
					return PersistencePackage.PERSISTENCE_XML_ROOT_CONTENT_NODE__JPA_FILE;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}
	
	public void dispose() {
		if (resource != null) {
			resource.releaseFromWrite();
		}
	}
	
	@Override
	public ITextRange fullTextRange() {
		return ITextRange.Empty.instance();
	}
	
	public IJpaContentNode getContentNode(int offset) {
		if (getPersistence() == null || !getPersistence().getNode().contains(offset)) {
			return this;
		}
		return getPersistence().getContentNode(offset);
	}
	
	public Object getId() {
		return IPersistenceXmlContentNodes.PERSISTENCEXML_ROOT_ID;
	}
	
	public void handleJavaElementChangedEvent(ElementChangedEvent event) {
	// TODO Auto-generated method stub
	}
	
	public void setResource(PersistenceResource persistenceResource) {
		resource = persistenceResource;
	}
}