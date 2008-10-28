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
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS: return (EObject)createXmlEntityMappings();
			case EclipseLinkOrmPackage.XML_CUSTOMIZER: return (EObject)createXmlCustomizer();
			case EclipseLinkOrmPackage.XML_CHANGE_TRACKING: return (EObject)createXmlChangeTracking();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE: return (EObject)createXmlEmbeddable();
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY: return (EObject)createXmlTimeOfDay();
			case EclipseLinkOrmPackage.XML_CACHE: return (EObject)createXmlCache();
			case EclipseLinkOrmPackage.XML_ENTITY: return (EObject)createXmlEntity();
			case EclipseLinkOrmPackage.XML_MAPPED_SUPERCLASS: return (EObject)createXmlMappedSuperclass();
			case EclipseLinkOrmPackage.XML_CONVERTER_IMPL: return (EObject)createXmlConverterImpl();
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER_IMPL: return (EObject)createXmlTypeConverterImpl();
			case EclipseLinkOrmPackage.XML_CONVERSION_VALUE_IMPL: return (EObject)createXmlConversionValueImpl();
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL: return (EObject)createXmlObjectTypeConverterImpl();
			case EclipseLinkOrmPackage.XML_STRUCT_CONVERTER_IMPL: return (EObject)createXmlStructConverterImpl();
			case EclipseLinkOrmPackage.XML_ID_IMPL: return (EObject)createXmlIdImpl();
			case EclipseLinkOrmPackage.XML_BASIC_IMPL: return (EObject)createXmlBasicImpl();
			case EclipseLinkOrmPackage.XML_VERSION_IMPL: return (EObject)createXmlVersionImpl();
			case EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL: return (EObject)createXmlOneToOneImpl();
			case EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL: return (EObject)createXmlOneToManyImpl();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL: return (EObject)createXmlManyToOneImpl();
			case EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL: return (EObject)createXmlManyToManyImpl();
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
			case EclipseLinkOrmPackage.XML_CHANGE_TRACKING_TYPE:
				return createXmlChangeTrackingTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.CACHE_TYPE:
				return createCacheTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.CACHE_COORDINATION_TYPE:
				return createCacheCoordinationTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.EXISTENCE_TYPE:
				return createExistenceTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.XML_JOIN_FETCH_TYPE:
				return createXmlJoinFetchTypeFromString(eDataType, initialValue);
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
			case EclipseLinkOrmPackage.XML_CHANGE_TRACKING_TYPE:
				return convertXmlChangeTrackingTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.CACHE_TYPE:
				return convertCacheTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.CACHE_COORDINATION_TYPE:
				return convertCacheCoordinationTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.EXISTENCE_TYPE:
				return convertExistenceTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.XML_JOIN_FETCH_TYPE:
				return convertXmlJoinFetchTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEntityMappings createXmlEntityMappings()
	{
		XmlEntityMappings xmlEntityMappings = new XmlEntityMappings();
		return xmlEntityMappings;
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
	public XmlChangeTracking createXmlChangeTracking()
	{
		XmlChangeTracking xmlChangeTracking = new XmlChangeTracking();
		return xmlChangeTracking;
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
	public XmlConverterImpl createXmlConverterImpl()
	{
		XmlConverterImpl xmlConverterImpl = new XmlConverterImpl();
		return xmlConverterImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTypeConverterImpl createXmlTypeConverterImpl()
	{
		XmlTypeConverterImpl xmlTypeConverterImpl = new XmlTypeConverterImpl();
		return xmlTypeConverterImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlConversionValueImpl createXmlConversionValueImpl()
	{
		XmlConversionValueImpl xmlConversionValueImpl = new XmlConversionValueImpl();
		return xmlConversionValueImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlObjectTypeConverterImpl createXmlObjectTypeConverterImpl()
	{
		XmlObjectTypeConverterImpl xmlObjectTypeConverterImpl = new XmlObjectTypeConverterImpl();
		return xmlObjectTypeConverterImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlStructConverterImpl createXmlStructConverterImpl()
	{
		XmlStructConverterImpl xmlStructConverterImpl = new XmlStructConverterImpl();
		return xmlStructConverterImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlIdImpl createXmlIdImpl()
	{
		XmlIdImpl xmlIdImpl = new XmlIdImpl();
		return xmlIdImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlBasicImpl createXmlBasicImpl()
	{
		XmlBasicImpl xmlBasicImpl = new XmlBasicImpl();
		return xmlBasicImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlVersionImpl createXmlVersionImpl()
	{
		XmlVersionImpl xmlVersionImpl = new XmlVersionImpl();
		return xmlVersionImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlOneToOneImpl createXmlOneToOneImpl()
	{
		XmlOneToOneImpl xmlOneToOneImpl = new XmlOneToOneImpl();
		return xmlOneToOneImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlOneToManyImpl createXmlOneToManyImpl()
	{
		XmlOneToManyImpl xmlOneToManyImpl = new XmlOneToManyImpl();
		return xmlOneToManyImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlManyToOneImpl createXmlManyToOneImpl()
	{
		XmlManyToOneImpl xmlManyToOneImpl = new XmlManyToOneImpl();
		return xmlManyToOneImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlManyToManyImpl createXmlManyToManyImpl()
	{
		XmlManyToManyImpl xmlManyToManyImpl = new XmlManyToManyImpl();
		return xmlManyToManyImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlChangeTrackingType createXmlChangeTrackingTypeFromString(EDataType eDataType, String initialValue)
	{
		XmlChangeTrackingType result = XmlChangeTrackingType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertXmlChangeTrackingTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
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
	public CacheCoordinationType createCacheCoordinationTypeFromString(EDataType eDataType, String initialValue)
	{
		CacheCoordinationType result = CacheCoordinationType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCacheCoordinationTypeToString(EDataType eDataType, Object instanceValue)
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
