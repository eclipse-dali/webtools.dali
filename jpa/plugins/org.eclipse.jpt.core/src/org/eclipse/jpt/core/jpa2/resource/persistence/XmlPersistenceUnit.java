/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.resource.persistence;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.persistence.PersistencePackage;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlProperties;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Persistence Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.jpa2.resource.persistence.XmlPersistenceUnit#getSharedCacheMode <em>Shared Cache Mode</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.jpa2.resource.persistence.XmlPersistenceUnit#getValidationMode <em>Validation Mode</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.jpa2.resource.persistence.Persistence2_0Package#getXmlPersistenceUnit()
 * @model kind="class"
 * @generated
 */
public class XmlPersistenceUnit extends org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit
{
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final XmlPersistenceUnitCachingType SHARED_CACHE_MODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSharedCacheMode() <em>Shared Cache Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSharedCacheMode()
	 * @generated
	 * @ordered
	 */
	protected XmlPersistenceUnitCachingType sharedCacheMode = SHARED_CACHE_MODE_EDEFAULT;


	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final XmlPersistenceUnitValidationModeType VALIDATION_MODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValidationMode() <em>Validation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidationMode()
	 * @generated
	 * @ordered
	 */
	protected XmlPersistenceUnitValidationModeType validationMode = VALIDATION_MODE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlPersistenceUnit()
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
		return Persistence2_0Package.Literals.XML_PERSISTENCE_UNIT;
	}

	/**
	 * Returns the value of the '<em><b>Shared Cache Mode</b></em>' attribute.
	 * The default value is <code>"JTA"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.jpa2.resource.persistence.XmlPersistenceUnitCachingType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Cache Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Cache Mode</em>' attribute.
	 * @see org.eclipse.jpt.core.jpa2.resource.persistence.XmlPersistenceUnitCachingType
	 * @see #setSharedCacheMode(XmlPersistenceUnitCachingType)
	 * @see org.eclipse.jpt.core.jpa2.resource.persistence.Persistence2_0Package#getXmlPersistenceUnit_SharedCacheMode()
	 * @model default="JTA" unique="false"
	 * @generated
	 */
	public XmlPersistenceUnitCachingType getSharedCacheMode()
	{
		return sharedCacheMode;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.jpa2.resource.persistence.XmlPersistenceUnit#getSharedCacheMode <em>Shared Cache Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shared Cache Mode</em>' attribute.
	 * @see org.eclipse.jpt.core.jpa2.resource.persistence.XmlPersistenceUnitCachingType
	 * @see #getSharedCacheMode()
	 * @generated
	 */
	public void setSharedCacheMode(XmlPersistenceUnitCachingType newSharedCacheMode)
	{
		XmlPersistenceUnitCachingType oldSharedCacheMode = sharedCacheMode;
		sharedCacheMode = newSharedCacheMode == null ? SHARED_CACHE_MODE_EDEFAULT : newSharedCacheMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Persistence2_0Package.XML_PERSISTENCE_UNIT__SHARED_CACHE_MODE, oldSharedCacheMode, sharedCacheMode));
	}

	/**
	 * Returns the value of the '<em><b>Validation Mode</b></em>' attribute.
	 * The default value is <code>"JTA"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.jpa2.resource.persistence.XmlPersistenceUnitValidationModeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validation Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validation Mode</em>' attribute.
	 * @see org.eclipse.jpt.core.jpa2.resource.persistence.XmlPersistenceUnitValidationModeType
	 * @see #setValidationMode(XmlPersistenceUnitValidationModeType)
	 * @see org.eclipse.jpt.core.jpa2.resource.persistence.Persistence2_0Package#getXmlPersistenceUnit_ValidationMode()
	 * @model default="JTA" unique="false"
	 * @generated
	 */
	public XmlPersistenceUnitValidationModeType getValidationMode()
	{
		return validationMode;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.jpa2.resource.persistence.XmlPersistenceUnit#getValidationMode <em>Validation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validation Mode</em>' attribute.
	 * @see org.eclipse.jpt.core.jpa2.resource.persistence.XmlPersistenceUnitValidationModeType
	 * @see #getValidationMode()
	 * @generated
	 */
	public void setValidationMode(XmlPersistenceUnitValidationModeType newValidationMode)
	{
		XmlPersistenceUnitValidationModeType oldValidationMode = validationMode;
		validationMode = newValidationMode == null ? VALIDATION_MODE_EDEFAULT : newValidationMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Persistence2_0Package.XML_PERSISTENCE_UNIT__VALIDATION_MODE, oldValidationMode, validationMode));
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
			case Persistence2_0Package.XML_PERSISTENCE_UNIT__SHARED_CACHE_MODE:
				return getSharedCacheMode();
			case Persistence2_0Package.XML_PERSISTENCE_UNIT__VALIDATION_MODE:
				return getValidationMode();
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
			case Persistence2_0Package.XML_PERSISTENCE_UNIT__SHARED_CACHE_MODE:
				setSharedCacheMode((XmlPersistenceUnitCachingType)newValue);
				return;
			case Persistence2_0Package.XML_PERSISTENCE_UNIT__VALIDATION_MODE:
				setValidationMode((XmlPersistenceUnitValidationModeType)newValue);
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
			case Persistence2_0Package.XML_PERSISTENCE_UNIT__SHARED_CACHE_MODE:
				setSharedCacheMode(SHARED_CACHE_MODE_EDEFAULT);
				return;
			case Persistence2_0Package.XML_PERSISTENCE_UNIT__VALIDATION_MODE:
				setValidationMode(VALIDATION_MODE_EDEFAULT);
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
			case Persistence2_0Package.XML_PERSISTENCE_UNIT__SHARED_CACHE_MODE:
				return sharedCacheMode != SHARED_CACHE_MODE_EDEFAULT;
			case Persistence2_0Package.XML_PERSISTENCE_UNIT__VALIDATION_MODE:
				return validationMode != VALIDATION_MODE_EDEFAULT;
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
		result.append(" (sharedCacheMode: ");
		result.append(sharedCacheMode);
		result.append(", validationMode: ");
		result.append(validationMode);
		result.append(')');
		return result.toString();
	}

	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			Persistence2_0Package.eINSTANCE.getXmlPersistenceUnit(), 
			buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		PersistencePackage pkg = PersistencePackage.eINSTANCE;
		return new Translator[] {
				buildNameTranslator(),
				buildTransactionTypeTranslator(),
				buildDescriptionTranslator(),
				buildProviderTranslator(),
				buildJtaDataSourceTranslator(),
				buildNonJtaDataSourceTranslator(),
				XmlMappingFileRef.buildTranslator(JPA2_0.MAPPING_FILE, pkg.getXmlPersistenceUnit_MappingFiles()),
				XmlJarFileRef.buildTranslator(JPA2_0.JAR_FILE, pkg.getXmlPersistenceUnit_JarFiles()),
				XmlJavaClassRef.buildTranslator(JPA2_0.CLASS, pkg.getXmlPersistenceUnit_Classes()),
				buildExcludeUnlistedClassesTranslator(),
				buildCachingTranslator(),
				buildValidationModeTranslator(),
				XmlProperties.buildTranslator(JPA2_0.PROPERTIES, pkg.getXmlPersistenceUnit_Properties())
			};
	}

	protected static Translator buildCachingTranslator() {
		return new Translator(
				JPA2_0.PERSISTENCE_UNIT__SHARED_CACHE_MODE,
				Persistence2_0Package.eINSTANCE.getXmlPersistenceUnit_SharedCacheMode()
			);
	}

	protected static Translator buildValidationModeTranslator() {
		return new Translator(
				JPA2_0.PERSISTENCE_UNIT__VALIDATION_MODE,
				Persistence2_0Package.eINSTANCE.getXmlPersistenceUnit_ValidationMode()
			);
	}
} // XmlPersistenceUnit
