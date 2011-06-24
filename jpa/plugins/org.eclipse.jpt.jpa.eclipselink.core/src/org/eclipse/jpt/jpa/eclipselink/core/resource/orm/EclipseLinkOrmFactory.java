/*******************************************************************************
 *  Copyright (c) 2008, 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

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
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage
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
			case EclipseLinkOrmPackage.XML_ACCESS_METHODS: return (EObject)createXmlAccessMethods();
			case EclipseLinkOrmPackage.XML_ADDITIONAL_CRITERIA: return (EObject)createXmlAdditionalCriteria();
			case EclipseLinkOrmPackage.XML_ARRAY: return (EObject)createXmlArray();
			case EclipseLinkOrmPackage.ATTRIBUTES: return (EObject)createAttributes();
			case EclipseLinkOrmPackage.XML_BASIC: return (EObject)createXmlBasic();
			case EclipseLinkOrmPackage.XML_BASIC_COLLECTION: return (EObject)createXmlBasicCollection();
			case EclipseLinkOrmPackage.XML_BASIC_MAP: return (EObject)createXmlBasicMap();
			case EclipseLinkOrmPackage.XML_BATCH_FETCH: return (EObject)createXmlBatchFetch();
			case EclipseLinkOrmPackage.XML_CACHE: return (EObject)createXmlCache();
			case EclipseLinkOrmPackage.XML_CHANGE_TRACKING: return (EObject)createXmlChangeTracking();
			case EclipseLinkOrmPackage.XML_CLONE_COPY_POLICY: return (EObject)createXmlCloneCopyPolicy();
			case EclipseLinkOrmPackage.XML_COLLECTION_TABLE: return (EObject)createXmlCollectionTable();
			case EclipseLinkOrmPackage.XML_CONVERSION_VALUE: return (EObject)createXmlConversionValue();
			case EclipseLinkOrmPackage.XML_CONVERTER: return (EObject)createXmlConverter();
			case EclipseLinkOrmPackage.XML_COPY_POLICY: return (EObject)createXmlCopyPolicy();
			case EclipseLinkOrmPackage.XML_CUSTOMIZER: return (EObject)createXmlCustomizer();
			case EclipseLinkOrmPackage.XML_ELEMENT_COLLECTION: return (EObject)createXmlElementCollection();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE: return (EObject)createXmlEmbeddable();
			case EclipseLinkOrmPackage.XML_EMBEDDED: return (EObject)createXmlEmbedded();
			case EclipseLinkOrmPackage.XML_EMBEDDED_ID: return (EObject)createXmlEmbeddedId();
			case EclipseLinkOrmPackage.XML_ENTITY: return (EObject)createXmlEntity();
			case EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS: return (EObject)createXmlEntityMappings();
			case EclipseLinkOrmPackage.XML_FETCH_ATTRIBUTE: return (EObject)createXmlFetchAttribute();
			case EclipseLinkOrmPackage.XML_FETCH_GROUP: return (EObject)createXmlFetchGroup();
			case EclipseLinkOrmPackage.XML_HASH_PARTITIONING: return (EObject)createXmlHashPartitioning();
			case EclipseLinkOrmPackage.XML_ID: return (EObject)createXmlId();
			case EclipseLinkOrmPackage.XML_INDEX: return (EObject)createXmlIndex();
			case EclipseLinkOrmPackage.XML_INSTANTIATION_COPY_POLICY: return (EObject)createXmlInstantiationCopyPolicy();
			case EclipseLinkOrmPackage.XML_JOIN_TABLE: return (EObject)createXmlJoinTable();
			case EclipseLinkOrmPackage.XML_MANY_TO_MANY: return (EObject)createXmlManyToMany();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE: return (EObject)createXmlManyToOne();
			case EclipseLinkOrmPackage.XML_MAPPED_SUPERCLASS: return (EObject)createXmlMappedSuperclass();
			case EclipseLinkOrmPackage.XML_MULTITENANT: return (EObject)createXmlMultitenant();
			case EclipseLinkOrmPackage.XML_NAMED_CONVERTER: return (EObject)createXmlNamedConverter();
			case EclipseLinkOrmPackage.XML_NAMED_PLSQL_STORED_FUNCTION_QUERY: return (EObject)createXmlNamedPlsqlStoredFunctionQuery();
			case EclipseLinkOrmPackage.XML_NAMED_PLSQL_STORED_PROCEDURE_QUERY: return (EObject)createXmlNamedPlsqlStoredProcedureQuery();
			case EclipseLinkOrmPackage.XML_NAMED_STORED_FUNCTION_QUERY: return (EObject)createXmlNamedStoredFunctionQuery();
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY: return (EObject)createXmlNamedStoredProcedureQuery();
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER: return (EObject)createXmlObjectTypeConverter();
			case EclipseLinkOrmPackage.XML_ONE_TO_MANY: return (EObject)createXmlOneToMany();
			case EclipseLinkOrmPackage.XML_ONE_TO_ONE: return (EObject)createXmlOneToOne();
			case EclipseLinkOrmPackage.XML_OPTIMISTIC_LOCKING: return (EObject)createXmlOptimisticLocking();
			case EclipseLinkOrmPackage.XML_ORDER_COLUMN: return (EObject)createXmlOrderColumn();
			case EclipseLinkOrmPackage.XML_PARTITIONING: return (EObject)createXmlPartitioning();
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS: return (EObject)createXmlPersistenceUnitDefaults();
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_METADATA: return (EObject)createXmlPersistenceUnitMetadata();
			case EclipseLinkOrmPackage.XML_PINNED_PARTITIONING: return (EObject)createXmlPinnedPartitioning();
			case EclipseLinkOrmPackage.XML_PLSQL_RECORD: return (EObject)createXmlPlsqlRecord();
			case EclipseLinkOrmPackage.XML_PLSQL_TABLE: return (EObject)createXmlPlsqlTable();
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY: return (EObject)createXmlPrimaryKey();
			case EclipseLinkOrmPackage.XML_PROPERTY: return (EObject)createXmlProperty();
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS: return (EObject)createXmlQueryRedirectors();
			case EclipseLinkOrmPackage.XML_RANGE_PARTITIONING: return (EObject)createXmlRangePartitioning();
			case EclipseLinkOrmPackage.XML_REPLICATION_PARTITIONING: return (EObject)createXmlReplicationPartitioning();
			case EclipseLinkOrmPackage.XML_RETURN_INSERT: return (EObject)createXmlReturnInsert();
			case EclipseLinkOrmPackage.XML_ROUND_ROBIN_PARTITIONING: return (EObject)createXmlRoundRobinPartitioning();
			case EclipseLinkOrmPackage.XML_SECONDARY_TABLE: return (EObject)createXmlSecondaryTable();
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER: return (EObject)createXmlStoredProcedureParameter();
			case EclipseLinkOrmPackage.XML_STRUCT: return (EObject)createXmlStruct();
			case EclipseLinkOrmPackage.XML_STRUCT_CONVERTER: return (EObject)createXmlStructConverter();
			case EclipseLinkOrmPackage.XML_STRUCTURE: return (EObject)createXmlStructure();
			case EclipseLinkOrmPackage.XML_TABLE: return (EObject)createXmlTable();
			case EclipseLinkOrmPackage.XML_TABLE_GENERATOR: return (EObject)createXmlTableGenerator();
			case EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR: return (EObject)createXmlTenantDiscriminator();
			case EclipseLinkOrmPackage.XML_TIME_OF_DAY: return (EObject)createXmlTimeOfDay();
			case EclipseLinkOrmPackage.XML_TRANSFORMATION: return (EObject)createXmlTransformation();
			case EclipseLinkOrmPackage.XML_TRANSIENT: return (EObject)createXmlTransient();
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER: return (EObject)createXmlTypeConverter();
			case EclipseLinkOrmPackage.XML_UNION_PARTITIONING: return (EObject)createXmlUnionPartitioning();
			case EclipseLinkOrmPackage.XML_VALUE_PARTITIONING: return (EObject)createXmlValuePartitioning();
			case EclipseLinkOrmPackage.XML_VARIABLE_ONE_TO_ONE: return (EObject)createXmlVariableOneToOne();
			case EclipseLinkOrmPackage.XML_VERSION: return (EObject)createXmlVersion();
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
			case EclipseLinkOrmPackage.CACHE_COORDINATION_TYPE:
				return createCacheCoordinationTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.CACHE_TYPE:
				return createCacheTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.XML_CHANGE_TRACKING_TYPE:
				return createXmlChangeTrackingTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.XML_DIRECTION:
				return createXmlDirectionFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.EXISTENCE_TYPE:
				return createExistenceTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.XML_JOIN_FETCH_TYPE:
				return createXmlJoinFetchTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.XML_OPTIMISTIC_LOCKING_TYPE:
				return createXmlOptimisticLockingTypeFromString(eDataType, initialValue);
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
			case EclipseLinkOrmPackage.CACHE_COORDINATION_TYPE:
				return convertCacheCoordinationTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.CACHE_TYPE:
				return convertCacheTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.XML_CHANGE_TRACKING_TYPE:
				return convertXmlChangeTrackingTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.XML_DIRECTION:
				return convertXmlDirectionToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.EXISTENCE_TYPE:
				return convertExistenceTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.XML_JOIN_FETCH_TYPE:
				return convertXmlJoinFetchTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.XML_OPTIMISTIC_LOCKING_TYPE:
				return convertXmlOptimisticLockingTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlAccessMethods createXmlAccessMethods()
	{
		XmlAccessMethods xmlAccessMethods = new XmlAccessMethods();
		return xmlAccessMethods;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlAdditionalCriteria createXmlAdditionalCriteria()
	{
		XmlAdditionalCriteria xmlAdditionalCriteria = new XmlAdditionalCriteria();
		return xmlAdditionalCriteria;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlArray createXmlArray()
	{
		XmlArray xmlArray = new XmlArray();
		return xmlArray;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attributes createAttributes()
	{
		Attributes attributes = new Attributes();
		return attributes;
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
	public XmlBasicCollection createXmlBasicCollection()
	{
		XmlBasicCollection xmlBasicCollection = new XmlBasicCollection();
		return xmlBasicCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlBasicMap createXmlBasicMap()
	{
		XmlBasicMap xmlBasicMap = new XmlBasicMap();
		return xmlBasicMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlBatchFetch createXmlBatchFetch()
	{
		XmlBatchFetch xmlBatchFetch = new XmlBatchFetch();
		return xmlBatchFetch;
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
	public XmlCloneCopyPolicy createXmlCloneCopyPolicy()
	{
		XmlCloneCopyPolicy xmlCloneCopyPolicy = new XmlCloneCopyPolicy();
		return xmlCloneCopyPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlCollectionTable createXmlCollectionTable()
	{
		XmlCollectionTable xmlCollectionTable = new XmlCollectionTable();
		return xmlCollectionTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlConversionValue createXmlConversionValue()
	{
		XmlConversionValue xmlConversionValue = new XmlConversionValue();
		return xmlConversionValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlConverter createXmlConverter()
	{
		XmlConverter xmlConverter = new XmlConverter();
		return xmlConverter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlCopyPolicy createXmlCopyPolicy()
	{
		XmlCopyPolicy xmlCopyPolicy = new XmlCopyPolicy();
		return xmlCopyPolicy;
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
	public XmlElementCollection createXmlElementCollection()
	{
		XmlElementCollection xmlElementCollection = new XmlElementCollection();
		return xmlElementCollection;
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
	public XmlEmbedded createXmlEmbedded()
	{
		XmlEmbedded xmlEmbedded = new XmlEmbedded();
		return xmlEmbedded;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEmbeddedId createXmlEmbeddedId()
	{
		XmlEmbeddedId xmlEmbeddedId = new XmlEmbeddedId();
		return xmlEmbeddedId;
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
	public XmlFetchAttribute createXmlFetchAttribute()
	{
		XmlFetchAttribute xmlFetchAttribute = new XmlFetchAttribute();
		return xmlFetchAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlFetchGroup createXmlFetchGroup()
	{
		XmlFetchGroup xmlFetchGroup = new XmlFetchGroup();
		return xmlFetchGroup;
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
	public XmlIndex createXmlIndex()
	{
		XmlIndex xmlIndex = new XmlIndex();
		return xmlIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlInstantiationCopyPolicy createXmlInstantiationCopyPolicy()
	{
		XmlInstantiationCopyPolicy xmlInstantiationCopyPolicy = new XmlInstantiationCopyPolicy();
		return xmlInstantiationCopyPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlJoinTable createXmlJoinTable()
	{
		XmlJoinTable xmlJoinTable = new XmlJoinTable();
		return xmlJoinTable;
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
	public XmlMultitenant createXmlMultitenant()
	{
		XmlMultitenant xmlMultitenant = new XmlMultitenant();
		return xmlMultitenant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlNamedConverter createXmlNamedConverter()
	{
		XmlNamedConverter xmlNamedConverter = new XmlNamedConverter();
		return xmlNamedConverter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlNamedPlsqlStoredFunctionQuery createXmlNamedPlsqlStoredFunctionQuery()
	{
		XmlNamedPlsqlStoredFunctionQuery xmlNamedPlsqlStoredFunctionQuery = new XmlNamedPlsqlStoredFunctionQuery();
		return xmlNamedPlsqlStoredFunctionQuery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlNamedPlsqlStoredProcedureQuery createXmlNamedPlsqlStoredProcedureQuery()
	{
		XmlNamedPlsqlStoredProcedureQuery xmlNamedPlsqlStoredProcedureQuery = new XmlNamedPlsqlStoredProcedureQuery();
		return xmlNamedPlsqlStoredProcedureQuery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlNamedStoredFunctionQuery createXmlNamedStoredFunctionQuery()
	{
		XmlNamedStoredFunctionQuery xmlNamedStoredFunctionQuery = new XmlNamedStoredFunctionQuery();
		return xmlNamedStoredFunctionQuery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlNamedStoredProcedureQuery createXmlNamedStoredProcedureQuery()
	{
		XmlNamedStoredProcedureQuery xmlNamedStoredProcedureQuery = new XmlNamedStoredProcedureQuery();
		return xmlNamedStoredProcedureQuery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlObjectTypeConverter createXmlObjectTypeConverter()
	{
		XmlObjectTypeConverter xmlObjectTypeConverter = new XmlObjectTypeConverter();
		return xmlObjectTypeConverter;
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
	public XmlOptimisticLocking createXmlOptimisticLocking()
	{
		XmlOptimisticLocking xmlOptimisticLocking = new XmlOptimisticLocking();
		return xmlOptimisticLocking;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlOrderColumn createXmlOrderColumn()
	{
		XmlOrderColumn xmlOrderColumn = new XmlOrderColumn();
		return xmlOrderColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPersistenceUnitDefaults createXmlPersistenceUnitDefaults()
	{
		XmlPersistenceUnitDefaults xmlPersistenceUnitDefaults = new XmlPersistenceUnitDefaults();
		return xmlPersistenceUnitDefaults;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPersistenceUnitMetadata createXmlPersistenceUnitMetadata()
	{
		XmlPersistenceUnitMetadata xmlPersistenceUnitMetadata = new XmlPersistenceUnitMetadata();
		return xmlPersistenceUnitMetadata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPrimaryKey createXmlPrimaryKey()
	{
		XmlPrimaryKey xmlPrimaryKey = new XmlPrimaryKey();
		return xmlPrimaryKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlProperty createXmlProperty()
	{
		XmlProperty xmlProperty = new XmlProperty();
		return xmlProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlQueryRedirectors createXmlQueryRedirectors()
	{
		XmlQueryRedirectors xmlQueryRedirectors = new XmlQueryRedirectors();
		return xmlQueryRedirectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlReturnInsert createXmlReturnInsert()
	{
		XmlReturnInsert xmlReturnInsert = new XmlReturnInsert();
		return xmlReturnInsert;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlSecondaryTable createXmlSecondaryTable()
	{
		XmlSecondaryTable xmlSecondaryTable = new XmlSecondaryTable();
		return xmlSecondaryTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlStoredProcedureParameter createXmlStoredProcedureParameter()
	{
		XmlStoredProcedureParameter xmlStoredProcedureParameter = new XmlStoredProcedureParameter();
		return xmlStoredProcedureParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlStruct createXmlStruct()
	{
		XmlStruct xmlStruct = new XmlStruct();
		return xmlStruct;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlStructConverter createXmlStructConverter()
	{
		XmlStructConverter xmlStructConverter = new XmlStructConverter();
		return xmlStructConverter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlStructure createXmlStructure()
	{
		XmlStructure xmlStructure = new XmlStructure();
		return xmlStructure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTable createXmlTable()
	{
		XmlTable xmlTable = new XmlTable();
		return xmlTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTableGenerator createXmlTableGenerator()
	{
		XmlTableGenerator xmlTableGenerator = new XmlTableGenerator();
		return xmlTableGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTenantDiscriminator createXmlTenantDiscriminator()
	{
		XmlTenantDiscriminator xmlTenantDiscriminator = new XmlTenantDiscriminator();
		return xmlTenantDiscriminator;
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
	public XmlTransformation createXmlTransformation()
	{
		XmlTransformation xmlTransformation = new XmlTransformation();
		return xmlTransformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTransient createXmlTransient()
	{
		XmlTransient xmlTransient = new XmlTransient();
		return xmlTransient;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTypeConverter createXmlTypeConverter()
	{
		XmlTypeConverter xmlTypeConverter = new XmlTypeConverter();
		return xmlTypeConverter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlVariableOneToOne createXmlVariableOneToOne()
	{
		XmlVariableOneToOne xmlVariableOneToOne = new XmlVariableOneToOne();
		return xmlVariableOneToOne;
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
	public XmlHashPartitioning createXmlHashPartitioning()
	{
		XmlHashPartitioning xmlHashPartitioning = new XmlHashPartitioning();
		return xmlHashPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPartitioning createXmlPartitioning()
	{
		XmlPartitioning xmlPartitioning = new XmlPartitioning();
		return xmlPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPinnedPartitioning createXmlPinnedPartitioning()
	{
		XmlPinnedPartitioning xmlPinnedPartitioning = new XmlPinnedPartitioning();
		return xmlPinnedPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPlsqlRecord createXmlPlsqlRecord()
	{
		XmlPlsqlRecord xmlPlsqlRecord = new XmlPlsqlRecord();
		return xmlPlsqlRecord;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPlsqlTable createXmlPlsqlTable()
	{
		XmlPlsqlTable xmlPlsqlTable = new XmlPlsqlTable();
		return xmlPlsqlTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlRangePartitioning createXmlRangePartitioning()
	{
		XmlRangePartitioning xmlRangePartitioning = new XmlRangePartitioning();
		return xmlRangePartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlReplicationPartitioning createXmlReplicationPartitioning()
	{
		XmlReplicationPartitioning xmlReplicationPartitioning = new XmlReplicationPartitioning();
		return xmlReplicationPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlRoundRobinPartitioning createXmlRoundRobinPartitioning()
	{
		XmlRoundRobinPartitioning xmlRoundRobinPartitioning = new XmlRoundRobinPartitioning();
		return xmlRoundRobinPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlUnionPartitioning createXmlUnionPartitioning()
	{
		XmlUnionPartitioning xmlUnionPartitioning = new XmlUnionPartitioning();
		return xmlUnionPartitioning;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlValuePartitioning createXmlValuePartitioning()
	{
		XmlValuePartitioning xmlValuePartitioning = new XmlValuePartitioning();
		return xmlValuePartitioning;
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
	public XmlDirection createXmlDirectionFromString(EDataType eDataType, String initialValue)
	{
		XmlDirection result = XmlDirection.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertXmlDirectionToString(EDataType eDataType, Object instanceValue)
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
	public XmlOptimisticLockingType createXmlOptimisticLockingTypeFromString(EDataType eDataType, String initialValue)
	{
		XmlOptimisticLockingType result = XmlOptimisticLockingType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertXmlOptimisticLockingTypeToString(EDataType eDataType, Object instanceValue)
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
	
}
