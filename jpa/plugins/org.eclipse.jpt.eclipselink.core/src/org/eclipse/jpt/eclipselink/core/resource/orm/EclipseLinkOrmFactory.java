/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm;

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
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage
 * @generated
 */
public class EclipseLinkOrmFactory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLinkOrmFactory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EclipseLinkOrmFactory init()
	{
		try
		{
			EclipseLinkOrmFactory theEclipseLinkOrmFactory = (EclipseLinkOrmFactory)EPackage.Registry.INSTANCE.getEFactory("jpt.eclipselink.orm.xmi"); 
			if (theEclipseLinkOrmFactory != null)
			{
				return theEclipseLinkOrmFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EclipseLinkOrmFactory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseLinkOrmFactory()
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
			case EclipseLinkOrmPackage.XML_CUSTOMIZER: return (EObject)createXmlCustomizer();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE: return (EObject)createXmlEmbeddable();
			case EclipseLinkOrmPackage.XML_ENTITY: return (EObject)createXmlEntity();
			case EclipseLinkOrmPackage.XML_MAPPED_SUPERCLASS: return (EObject)createXmlMappedSuperclass();
			case EclipseLinkOrmPackage.XML_ID: return (EObject)createXmlId();
			case EclipseLinkOrmPackage.XML_BASIC: return (EObject)createXmlBasic();
			case EclipseLinkOrmPackage.XML_VERSION: return (EObject)createXmlVersion();
			case EclipseLinkOrmPackage.XML_ONE_TO_ONE: return (EObject)createXmlOneToOne();
			case EclipseLinkOrmPackage.XML_ONE_TO_MANY: return (EObject)createXmlOneToMany();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE: return (EObject)createXmlManyToOne();
			case EclipseLinkOrmPackage.XML_MANY_TO_MANY: return (EObject)createXmlManyToMany();
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY: return (EObject)createXmlTimeOfDay();
			case EclipseLinkOrmPackage.XML_CACHE: return (EObject)createXmlCache();
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
			case EclipseLinkOrmPackage.XML_JOIN_FETCH_TYPE:
				return createXmlJoinFetchTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.CACHE_TYPE:
				return createCacheTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.COORDINATION_TYPE:
				return createCoordinationTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.EXISTENCE_TYPE:
				return createExistenceTypeFromString(eDataType, initialValue);
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
			case EclipseLinkOrmPackage.XML_JOIN_FETCH_TYPE:
				return convertXmlJoinFetchTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.CACHE_TYPE:
				return convertCacheTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.COORDINATION_TYPE:
				return convertCoordinationTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.EXISTENCE_TYPE:
				return convertExistenceTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlCustomizer createXmlCustomizer()
	{
		XmlCustomizer xmlCustomizer = new XmlCustomizer();
		return xmlCustomizer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEmbeddable createXmlEmbeddable()
	{
		XmlEmbeddable xmlEmbeddable = new XmlEmbeddable();
		return xmlEmbeddable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEntity createXmlEntity()
	{
		XmlEntity xmlEntity = new XmlEntity();
		return xmlEntity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlMappedSuperclass createXmlMappedSuperclass()
	{
		XmlMappedSuperclass xmlMappedSuperclass = new XmlMappedSuperclass();
		return xmlMappedSuperclass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlId createXmlId()
	{
		XmlId xmlId = new XmlId();
		return xmlId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlBasic createXmlBasic()
	{
		XmlBasic xmlBasic = new XmlBasic();
		return xmlBasic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlVersion createXmlVersion()
	{
		XmlVersion xmlVersion = new XmlVersion();
		return xmlVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlOneToOne createXmlOneToOne()
	{
		XmlOneToOne xmlOneToOne = new XmlOneToOne();
		return xmlOneToOne;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlOneToMany createXmlOneToMany()
	{
		XmlOneToMany xmlOneToMany = new XmlOneToMany();
		return xmlOneToMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlManyToOne createXmlManyToOne()
	{
		XmlManyToOne xmlManyToOne = new XmlManyToOne();
		return xmlManyToOne;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlManyToMany createXmlManyToMany()
	{
		XmlManyToMany xmlManyToMany = new XmlManyToMany();
		return xmlManyToMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTimeOfDay createXmlTimeOfDay()
	{
		XmlTimeOfDay xmlTimeOfDay = new XmlTimeOfDay();
		return xmlTimeOfDay;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlCache createXmlCache()
	{
		XmlCache xmlCache = new XmlCache();
		return xmlCache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlJoinFetchType createXmlJoinFetchTypeFromString(EDataType eDataType, String initialValue)
	{
		XmlJoinFetchType result = XmlJoinFetchType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertXmlJoinFetchTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CacheType createCacheTypeFromString(EDataType eDataType, String initialValue)
	{
		CacheType result = CacheType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCacheTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CoordinationType createCoordinationTypeFromString(EDataType eDataType, String initialValue)
	{
		CoordinationType result = CoordinationType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCoordinationTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExistenceType createExistenceTypeFromString(EDataType eDataType, String initialValue)
	{
		ExistenceType result = ExistenceType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertExistenceTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseLinkOrmPackage getEclipseLinkOrmPackage()
	{
		return (EclipseLinkOrmPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EclipseLinkOrmPackage getPackage()
	{
		return EclipseLinkOrmPackage.eINSTANCE;
	}

} //EclipseLinkOrmFactory
