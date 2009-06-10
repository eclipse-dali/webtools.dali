/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.resource.persistence;

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
 *   <li>{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit#getCaching <em>Caching</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit#getValidationMode <em>Validation Mode</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package#getXmlPersistenceUnit()
 * @model kind="class"
 * @generated
 */
public class XmlPersistenceUnit extends org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit
{
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final XmlPersistenceUnitCachingType CACHING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCaching() <em>Caching</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCaching()
	 * @generated
	 * @ordered
	 */
	protected XmlPersistenceUnitCachingType caching = CACHING_EDEFAULT;

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
	 * Returns the value of the '<em><b>Caching</b></em>' attribute.
	 * The default value is <code>"JTA"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitCachingType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Caching</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Caching</em>' attribute.
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitCachingType
	 * @see #setCaching(XmlPersistenceUnitCachingType)
	 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package#getXmlPersistenceUnit_Caching()
	 * @model default="JTA" unique="false"
	 * @generated
	 */
	public XmlPersistenceUnitCachingType getCaching()
	{
		return caching;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit#getCaching <em>Caching</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Caching</em>' attribute.
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitCachingType
	 * @see #getCaching()
	 * @generated
	 */
	public void setCaching(XmlPersistenceUnitCachingType newCaching)
	{
		XmlPersistenceUnitCachingType oldCaching = caching;
		caching = newCaching == null ? CACHING_EDEFAULT : newCaching;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Persistence2_0Package.XML_PERSISTENCE_UNIT__CACHING, oldCaching, caching));
	}

	/**
	 * Returns the value of the '<em><b>Validation Mode</b></em>' attribute.
	 * The default value is <code>"JTA"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitValidationModeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validation Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validation Mode</em>' attribute.
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitValidationModeType
	 * @see #setValidationMode(XmlPersistenceUnitValidationModeType)
	 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package#getXmlPersistenceUnit_ValidationMode()
	 * @model default="JTA" unique="false"
	 * @generated
	 */
	public XmlPersistenceUnitValidationModeType getValidationMode()
	{
		return validationMode;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit#getValidationMode <em>Validation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validation Mode</em>' attribute.
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitValidationModeType
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
			case Persistence2_0Package.XML_PERSISTENCE_UNIT__CACHING:
				return getCaching();
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
			case Persistence2_0Package.XML_PERSISTENCE_UNIT__CACHING:
				setCaching((XmlPersistenceUnitCachingType)newValue);
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
			case Persistence2_0Package.XML_PERSISTENCE_UNIT__CACHING:
				setCaching(CACHING_EDEFAULT);
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
			case Persistence2_0Package.XML_PERSISTENCE_UNIT__CACHING:
				return caching != CACHING_EDEFAULT;
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
		result.append(" (caching: ");
		result.append(caching);
		result.append(", validationMode: ");
		result.append(validationMode);
		result.append(')');
		return result.toString();
	}

	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
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
				XmlMappingFileRef.buildTranslator(JPA.MAPPING_FILE, pkg.getXmlPersistenceUnit_MappingFiles()),
				XmlJarFileRef.buildTranslator(JPA.JAR_FILE, pkg.getXmlPersistenceUnit_JarFiles()),
				XmlJavaClassRef.buildTranslator(JPA.CLASS, pkg.getXmlPersistenceUnit_Classes()),
				buildExcludeUnlistedClassesTranslator(),
				buildCachingTranslator(),
				buildValidationModeTranslator(),
				XmlProperties.buildTranslator(JPA.PROPERTIES, pkg.getXmlPersistenceUnit_Properties())
			};
	}

	protected static Translator buildCachingTranslator() {
		return new Translator(
				JPA.PERSISTENCE_UNIT__CACHING,
				Persistence2_0Package.eINSTANCE.getXmlPersistenceUnit_Caching()
			);
	}

	protected static Translator buildValidationModeTranslator() {
		return new Translator(
				JPA.PERSISTENCE_UNIT__VALIDATION_MODE,
				Persistence2_0Package.eINSTANCE.getXmlPersistenceUnit_ValidationMode()
			);
	}

} // XmlPersistenceUnit
