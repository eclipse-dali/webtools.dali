/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.EmptyTagBooleanTranslator;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlPersistenceUnitMetadata_2_0;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>XmlPersistence Unit Metadata</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata#isXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata#getPersistenceUnitDefaults <em>Persistence Unit Defaults</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlPersistenceUnitMetadata()
 * @model kind="class"
 * @generated
 */
public class XmlPersistenceUnitMetadata extends EBaseObjectImpl implements XmlPersistenceUnitMetadata_2_0
{
	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #isXmlMappingMetadataComplete() <em>Xml Mapping Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlMappingMetadataComplete()
	 * @generated
	 * @ordered
	 */
	protected static final boolean XML_MAPPING_METADATA_COMPLETE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isXmlMappingMetadataComplete() <em>Xml Mapping Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlMappingMetadataComplete()
	 * @generated
	 * @ordered
	 */
	protected boolean xmlMappingMetadataComplete = XML_MAPPING_METADATA_COMPLETE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPersistenceUnitDefaults() <em>Persistence Unit Defaults</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 * @ordered
	 */
	protected XmlPersistenceUnitDefaults persistenceUnitDefaults;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlPersistenceUnitMetadata()
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
		return OrmPackage.Literals.XML_PERSISTENCE_UNIT_METADATA;
	}

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlPersistenceUnitMetadata_2_0_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	public void setDescription(String newDescription)
	{
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENCE_UNIT_METADATA__DESCRIPTION, oldDescription, description));
	}

	/**
	 * Returns the value of the '<em><b>Xml Mapping Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Mapping Metadata Complete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Mapping Metadata Complete</em>' attribute.
	 * @see #setXmlMappingMetadataComplete(boolean)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlPersistenceUnitMetadata_XmlMappingMetadataComplete()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isXmlMappingMetadataComplete()
	{
		return xmlMappingMetadataComplete;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata#isXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Mapping Metadata Complete</em>' attribute.
	 * @see #isXmlMappingMetadataComplete()
	 * @generated
	 */
	public void setXmlMappingMetadataComplete(boolean newXmlMappingMetadataComplete)
	{
		boolean oldXmlMappingMetadataComplete = xmlMappingMetadataComplete;
		xmlMappingMetadataComplete = newXmlMappingMetadataComplete;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE, oldXmlMappingMetadataComplete, xmlMappingMetadataComplete));
	}

	/**
	 * Returns the value of the '<em><b>Persistence Unit Defaults</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XmlPersistence Unit Defaults</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Unit Defaults</em>' containment reference.
	 * @see #setPersistenceUnitDefaults(XmlPersistenceUnitDefaults)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlPersistenceUnitMetadata_PersistenceUnitDefaults()
	 * @model containment="true"
	 * @generated
	 */
	public XmlPersistenceUnitDefaults getPersistenceUnitDefaults()
	{
		return persistenceUnitDefaults;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistenceUnitDefaults(XmlPersistenceUnitDefaults newPersistenceUnitDefaults, NotificationChain msgs)
	{
		XmlPersistenceUnitDefaults oldPersistenceUnitDefaults = persistenceUnitDefaults;
		persistenceUnitDefaults = newPersistenceUnitDefaults;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS, oldPersistenceUnitDefaults, newPersistenceUnitDefaults);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata#getPersistenceUnitDefaults <em>Persistence Unit Defaults</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistence Unit Defaults</em>' containment reference.
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 */
	public void setPersistenceUnitDefaults(XmlPersistenceUnitDefaults newPersistenceUnitDefaults)
	{
		if (newPersistenceUnitDefaults != persistenceUnitDefaults)
		{
			NotificationChain msgs = null;
			if (persistenceUnitDefaults != null)
				msgs = ((InternalEObject)persistenceUnitDefaults).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS, null, msgs);
			if (newPersistenceUnitDefaults != null)
				msgs = ((InternalEObject)newPersistenceUnitDefaults).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS, null, msgs);
			msgs = basicSetPersistenceUnitDefaults(newPersistenceUnitDefaults, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS, newPersistenceUnitDefaults, newPersistenceUnitDefaults));
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
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS:
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
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA__DESCRIPTION:
				return getDescription();
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE:
				return isXmlMappingMetadataComplete();
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS:
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
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE:
				setXmlMappingMetadataComplete((Boolean)newValue);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS:
				setPersistenceUnitDefaults((XmlPersistenceUnitDefaults)newValue);
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
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE:
				setXmlMappingMetadataComplete(XML_MAPPING_METADATA_COMPLETE_EDEFAULT);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS:
				setPersistenceUnitDefaults((XmlPersistenceUnitDefaults)null);
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
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE:
				return xmlMappingMetadataComplete != XML_MAPPING_METADATA_COMPLETE_EDEFAULT;
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS:
				return persistenceUnitDefaults != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (description: ");
		result.append(description);
		result.append(", xmlMappingMetadataComplete: ");
		result.append(xmlMappingMetadataComplete);
		result.append(')');
		return result.toString();
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildDescriptionTranslator(),
			buildXmlMappingMetadataCompleteTranslator(),
			XmlPersistenceUnitDefaults.buildTranslator(JPA.PERSISTENCE_UNIT_DEFAULTS, OrmPackage.eINSTANCE.getXmlPersistenceUnitMetadata_PersistenceUnitDefaults())
		};
	}
	
	protected static Translator buildDescriptionTranslator() {
		return new Translator(JPA.DESCRIPTION, OrmV2_0Package.eINSTANCE.getXmlPersistenceUnitMetadata_2_0_Description());
	}

	protected static Translator buildXmlMappingMetadataCompleteTranslator() {
		return new EmptyTagBooleanTranslator(JPA.XML_MAPPING_METADATA_COMPLETE, OrmPackage.eINSTANCE.getXmlPersistenceUnitMetadata_XmlMappingMetadataComplete());
	}
}
