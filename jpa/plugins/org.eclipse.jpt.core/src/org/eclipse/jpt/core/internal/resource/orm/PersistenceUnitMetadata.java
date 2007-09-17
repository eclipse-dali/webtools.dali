/**
 * <copyright>
 * </copyright>
 *
 * $Id: PersistenceUnitMetadata.java,v 1.1.2.1 2007/09/17 20:49:52 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.core.internal.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Persistence Unit Metadata</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata#getXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata#getPersistenceUnitDefaults <em>Persistence Unit Defaults</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPersistenceUnitMetadata()
 * @model kind="class"
 * @generated
 */
public class PersistenceUnitMetadata extends JpaEObject implements EObject
{
	/**
	 * The cached value of the '{@link #getXmlMappingMetadataComplete() <em>Xml Mapping Metadata Complete</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlMappingMetadataComplete()
	 * @generated
	 * @ordered
	 */
	protected EmptyType xmlMappingMetadataComplete;

	/**
	 * The cached value of the '{@link #getPersistenceUnitDefaults() <em>Persistence Unit Defaults</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 * @ordered
	 */
	protected PersistenceUnitDefaults persistenceUnitDefaults;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PersistenceUnitMetadata()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return OrmPackage.Literals.PERSISTENCE_UNIT_METADATA;
	}

	/**
	 * Returns the value of the '<em><b>Xml Mapping Metadata Complete</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Mapping Metadata Complete</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Mapping Metadata Complete</em>' containment reference.
	 * @see #setXmlMappingMetadataComplete(EmptyType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPersistenceUnitMetadata_XmlMappingMetadataComplete()
	 * @model containment="true"
	 * @generated
	 */
	public EmptyType getXmlMappingMetadataComplete()
	{
		return xmlMappingMetadataComplete;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetXmlMappingMetadataComplete(EmptyType newXmlMappingMetadataComplete, NotificationChain msgs)
	{
		EmptyType oldXmlMappingMetadataComplete = xmlMappingMetadataComplete;
		xmlMappingMetadataComplete = newXmlMappingMetadataComplete;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE, oldXmlMappingMetadataComplete, newXmlMappingMetadataComplete);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata#getXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Mapping Metadata Complete</em>' containment reference.
	 * @see #getXmlMappingMetadataComplete()
	 * @generated
	 */
	public void setXmlMappingMetadataComplete(EmptyType newXmlMappingMetadataComplete)
	{
		if (newXmlMappingMetadataComplete != xmlMappingMetadataComplete)
		{
			NotificationChain msgs = null;
			if (xmlMappingMetadataComplete != null)
				msgs = ((InternalEObject)xmlMappingMetadataComplete).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE, null, msgs);
			if (newXmlMappingMetadataComplete != null)
				msgs = ((InternalEObject)newXmlMappingMetadataComplete).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE, null, msgs);
			msgs = basicSetXmlMappingMetadataComplete(newXmlMappingMetadataComplete, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE, newXmlMappingMetadataComplete, newXmlMappingMetadataComplete));
	}

	/**
	 * Returns the value of the '<em><b>Persistence Unit Defaults</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistence Unit Defaults</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Unit Defaults</em>' containment reference.
	 * @see #setPersistenceUnitDefaults(PersistenceUnitDefaults)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPersistenceUnitMetadata_PersistenceUnitDefaults()
	 * @model containment="true"
	 * @generated
	 */
	public PersistenceUnitDefaults getPersistenceUnitDefaults()
	{
		return persistenceUnitDefaults;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistenceUnitDefaults(PersistenceUnitDefaults newPersistenceUnitDefaults, NotificationChain msgs)
	{
		PersistenceUnitDefaults oldPersistenceUnitDefaults = persistenceUnitDefaults;
		persistenceUnitDefaults = newPersistenceUnitDefaults;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS, oldPersistenceUnitDefaults, newPersistenceUnitDefaults);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata#getPersistenceUnitDefaults <em>Persistence Unit Defaults</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistence Unit Defaults</em>' containment reference.
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 */
	public void setPersistenceUnitDefaults(PersistenceUnitDefaults newPersistenceUnitDefaults)
	{
		if (newPersistenceUnitDefaults != persistenceUnitDefaults)
		{
			NotificationChain msgs = null;
			if (persistenceUnitDefaults != null)
				msgs = ((InternalEObject)persistenceUnitDefaults).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS, null, msgs);
			if (newPersistenceUnitDefaults != null)
				msgs = ((InternalEObject)newPersistenceUnitDefaults).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS, null, msgs);
			msgs = basicSetPersistenceUnitDefaults(newPersistenceUnitDefaults, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS, newPersistenceUnitDefaults, newPersistenceUnitDefaults));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case OrmPackage.PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE:
				return basicSetXmlMappingMetadataComplete(null, msgs);
			case OrmPackage.PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS:
				return basicSetPersistenceUnitDefaults(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case OrmPackage.PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE:
				return getXmlMappingMetadataComplete();
			case OrmPackage.PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS:
				return getPersistenceUnitDefaults();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case OrmPackage.PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE:
				setXmlMappingMetadataComplete((EmptyType)newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS:
				setPersistenceUnitDefaults((PersistenceUnitDefaults)newValue);
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
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case OrmPackage.PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE:
				setXmlMappingMetadataComplete((EmptyType)null);
				return;
			case OrmPackage.PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS:
				setPersistenceUnitDefaults((PersistenceUnitDefaults)null);
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case OrmPackage.PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE:
				return xmlMappingMetadataComplete != null;
			case OrmPackage.PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS:
				return persistenceUnitDefaults != null;
		}
		return super.eIsSet(featureID);
	}

} // PersistenceUnitMetadata
