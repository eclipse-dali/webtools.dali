/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4;

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
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package
 * @generated
 */
public class EclipseLinkOrmV2_4Factory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLinkOrmV2_4Factory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EclipseLinkOrmV2_4Factory init()
	{
		try
		{
			EclipseLinkOrmV2_4Factory theEclipseLinkOrmV2_4Factory = (EclipseLinkOrmV2_4Factory)EPackage.Registry.INSTANCE.getEFactory("jpt.eclipselink.orm.v2_4.xmi"); 
			if (theEclipseLinkOrmV2_4Factory != null)
			{
				return theEclipseLinkOrmV2_4Factory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EclipseLinkOrmV2_4Factory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseLinkOrmV2_4Factory()
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
			case EclipseLinkOrmV2_4Package.DATABASE_CHANGE_NOTIFICATION_TYPE:
				return createDatabaseChangeNotificationTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmV2_4Package.TENANT_TABLE_DISCRIMINATOR_TYPE:
				return createTenantTableDiscriminatorTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmV2_4Package.DATA_FORMAT_TYPE:
				return createDataFormatTypeFromString(eDataType, initialValue);
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
			case EclipseLinkOrmV2_4Package.DATABASE_CHANGE_NOTIFICATION_TYPE:
				return convertDatabaseChangeNotificationTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmV2_4Package.TENANT_TABLE_DISCRIMINATOR_TYPE:
				return convertTenantTableDiscriminatorTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmV2_4Package.DATA_FORMAT_TYPE:
				return convertDataFormatTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DatabaseChangeNotificationType createDatabaseChangeNotificationTypeFromString(EDataType eDataType, String initialValue)
	{
		DatabaseChangeNotificationType result = DatabaseChangeNotificationType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDatabaseChangeNotificationTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TenantTableDiscriminatorType createTenantTableDiscriminatorTypeFromString(EDataType eDataType, String initialValue)
	{
		TenantTableDiscriminatorType result = TenantTableDiscriminatorType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTenantTableDiscriminatorTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataFormatType createDataFormatTypeFromString(EDataType eDataType, String initialValue)
	{
		DataFormatType result = DataFormatType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataFormatTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseLinkOrmV2_4Package getEclipseLinkOrmV2_4Package()
	{
		return (EclipseLinkOrmV2_4Package)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EclipseLinkOrmV2_4Package getPackage()
	{
		return EclipseLinkOrmV2_4Package.eINSTANCE;
	}

} //EclipseLinkOrmV2_4Factory
