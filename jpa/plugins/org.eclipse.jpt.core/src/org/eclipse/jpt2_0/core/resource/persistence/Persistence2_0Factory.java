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
 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package
 * @generated
 */
public class Persistence2_0Factory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final Persistence2_0Factory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Persistence2_0Factory init()
	{
		try
		{
			Persistence2_0Factory thePersistence2_0Factory = (Persistence2_0Factory)EPackage.Registry.INSTANCE.getEFactory("jpt2_0.persistence.xmi"); 
			if (thePersistence2_0Factory != null)
			{
				return thePersistence2_0Factory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new Persistence2_0Factory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Persistence2_0Factory()
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
			case Persistence2_0Package.XML_PERSISTENCE: return (EObject)createXmlPersistence();
			case Persistence2_0Package.XML_PERSISTENCE_UNIT: return (EObject)createXmlPersistenceUnit();
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
			case Persistence2_0Package.XML_PERSISTENCE_UNIT_CACHING_TYPE:
				return createXmlPersistenceUnitCachingTypeFromString(eDataType, initialValue);
			case Persistence2_0Package.XML_PERSISTENCE_UNIT_VALIDATION_MODE_TYPE:
				return createXmlPersistenceUnitValidationModeTypeFromString(eDataType, initialValue);
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
			case Persistence2_0Package.XML_PERSISTENCE_UNIT_CACHING_TYPE:
				return convertXmlPersistenceUnitCachingTypeToString(eDataType, instanceValue);
			case Persistence2_0Package.XML_PERSISTENCE_UNIT_VALIDATION_MODE_TYPE:
				return convertXmlPersistenceUnitValidationModeTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPersistence createXmlPersistence()
	{
		XmlPersistence xmlPersistence = new XmlPersistence();
		return xmlPersistence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPersistenceUnit createXmlPersistenceUnit()
	{
		XmlPersistenceUnit xmlPersistenceUnit = new XmlPersistenceUnit();
		return xmlPersistenceUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPersistenceUnitCachingType createXmlPersistenceUnitCachingTypeFromString(EDataType eDataType, String initialValue)
	{
		XmlPersistenceUnitCachingType result = XmlPersistenceUnitCachingType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertXmlPersistenceUnitCachingTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPersistenceUnitValidationModeType createXmlPersistenceUnitValidationModeTypeFromString(EDataType eDataType, String initialValue)
	{
		XmlPersistenceUnitValidationModeType result = XmlPersistenceUnitValidationModeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertXmlPersistenceUnitValidationModeTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Persistence2_0Package getPersistence2_0Package()
	{
		return (Persistence2_0Package)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static Persistence2_0Package getPackage()
	{
		return Persistence2_0Package.eINSTANCE;
	}

} //Persistence2_0Factory
