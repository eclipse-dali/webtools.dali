/*******************************************************************************
 *  Copyright (c) 2009, 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License 2.0 which 
 *  accompanies this distribution, and is available at 
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.jpa.core.resource.persistence.v2_0;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.jpa.core.resource.persistence.v2_0.PersistenceV2_0Package
 * @generated
 */
public class PersistenceV2_0Factory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final PersistenceV2_0Factory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PersistenceV2_0Factory init()
	{
		try
		{
			PersistenceV2_0Factory thePersistenceV2_0Factory = (PersistenceV2_0Factory)EPackage.Registry.INSTANCE.getEFactory("jpt.persistence.v2_0.xmi"); 
			if (thePersistenceV2_0Factory != null)
			{
				return thePersistenceV2_0Factory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PersistenceV2_0Factory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersistenceV2_0Factory()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass)
	{
		switch (eClass.getClassifierID())
		{
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue)
	{
		switch (eDataType.getClassifierID())
		{
			case PersistenceV2_0Package.XML_PERSISTENCE_UNIT_CACHING_TYPE_20:
				return createXmlPersistenceUnitCachingType_2_0FromString(eDataType, initialValue);
			case PersistenceV2_0Package.XML_PERSISTENCE_UNIT_VALIDATION_MODE_TYPE_20:
				return createXmlPersistenceUnitValidationModeType_2_0FromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue)
	{
		switch (eDataType.getClassifierID())
		{
			case PersistenceV2_0Package.XML_PERSISTENCE_UNIT_CACHING_TYPE_20:
				return convertXmlPersistenceUnitCachingType_2_0ToString(eDataType, instanceValue);
			case PersistenceV2_0Package.XML_PERSISTENCE_UNIT_VALIDATION_MODE_TYPE_20:
				return convertXmlPersistenceUnitValidationModeType_2_0ToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPersistenceUnitCachingType_2_0 createXmlPersistenceUnitCachingType_2_0FromString(EDataType eDataType, String initialValue)
	{
		XmlPersistenceUnitCachingType_2_0 result = XmlPersistenceUnitCachingType_2_0.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertXmlPersistenceUnitCachingType_2_0ToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPersistenceUnitValidationModeType_2_0 createXmlPersistenceUnitValidationModeType_2_0FromString(EDataType eDataType, String initialValue)
	{
		XmlPersistenceUnitValidationModeType_2_0 result = XmlPersistenceUnitValidationModeType_2_0.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertXmlPersistenceUnitValidationModeType_2_0ToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersistenceV2_0Package getPersistenceV2_0Package()
	{
		return (PersistenceV2_0Package)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PersistenceV2_0Package getPackage()
	{
		return PersistenceV2_0Package.eINSTANCE;
	}

} //PersistenceV2_0Factory
