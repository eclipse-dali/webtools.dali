/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.resource.orm;

import org.eclipse.emf.common.util.Enumerator;
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
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage
 * @generated
 */
public class OrmFactory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final OrmFactory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OrmFactory init()
	{
		try
		{
			OrmFactory theOrmFactory = (OrmFactory)EPackage.Registry.INSTANCE.getEFactory("jpt.orm.xmi"); 
			if (theOrmFactory != null)
			{
				return theOrmFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new OrmFactory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrmFactory()
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
			case OrmPackage.XML_ASSOCIATION_OVERRIDE: return (EObject)createXmlAssociationOverride();
			case OrmPackage.XML_ATTRIBUTE_OVERRIDE: return (EObject)createXmlAttributeOverride();
			case OrmPackage.ATTRIBUTES: return (EObject)createAttributes();
			case OrmPackage.XML_BASIC: return (EObject)createXmlBasic();
			case OrmPackage.CASCADE_TYPE: return (EObject)createCascadeType();
			case OrmPackage.XML_COLLECTION_TABLE: return (EObject)createXmlCollectionTable();
			case OrmPackage.XML_COLUMN: return (EObject)createXmlColumn();
			case OrmPackage.COLUMN_RESULT: return (EObject)createColumnResult();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN: return (EObject)createXmlDiscriminatorColumn();
			case OrmPackage.XML_ELEMENT_COLLECTION: return (EObject)createXmlElementCollection();
			case OrmPackage.XML_EMBEDDABLE: return (EObject)createXmlEmbeddable();
			case OrmPackage.XML_EMBEDDED: return (EObject)createXmlEmbedded();
			case OrmPackage.XML_EMBEDDED_ID: return (EObject)createXmlEmbeddedId();
			case OrmPackage.XML_ENTITY: return (EObject)createXmlEntity();
			case OrmPackage.ENTITY_LISTENER: return (EObject)createEntityListener();
			case OrmPackage.ENTITY_LISTENERS: return (EObject)createEntityListeners();
			case OrmPackage.XML_ENTITY_MAPPINGS: return (EObject)createXmlEntityMappings();
			case OrmPackage.ENTITY_RESULT: return (EObject)createEntityResult();
			case OrmPackage.FIELD_RESULT: return (EObject)createFieldResult();
			case OrmPackage.XML_GENERATED_VALUE: return (EObject)createXmlGeneratedValue();
			case OrmPackage.XML_ID: return (EObject)createXmlId();
			case OrmPackage.XML_ID_CLASS: return (EObject)createXmlIdClass();
			case OrmPackage.INHERITANCE: return (EObject)createInheritance();
			case OrmPackage.XML_JOIN_COLUMN: return (EObject)createXmlJoinColumn();
			case OrmPackage.XML_JOIN_TABLE: return (EObject)createXmlJoinTable();
			case OrmPackage.LOB: return (EObject)createLob();
			case OrmPackage.XML_MANY_TO_MANY: return (EObject)createXmlManyToMany();
			case OrmPackage.XML_MANY_TO_ONE: return (EObject)createXmlManyToOne();
			case OrmPackage.MAP_KEY: return (EObject)createMapKey();
			case OrmPackage.XML_MAP_KEY_CLASS: return (EObject)createXmlMapKeyClass();
			case OrmPackage.XML_MAPPED_SUPERCLASS: return (EObject)createXmlMappedSuperclass();
			case OrmPackage.XML_NAMED_NATIVE_QUERY: return (EObject)createXmlNamedNativeQuery();
			case OrmPackage.XML_NAMED_QUERY: return (EObject)createXmlNamedQuery();
			case OrmPackage.XML_ONE_TO_MANY: return (EObject)createXmlOneToMany();
			case OrmPackage.XML_ONE_TO_ONE: return (EObject)createXmlOneToOne();
			case OrmPackage.XML_ORDER_COLUMN: return (EObject)createXmlOrderColumn();
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS: return (EObject)createXmlPersistenceUnitDefaults();
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA: return (EObject)createXmlPersistenceUnitMetadata();
			case OrmPackage.POST_LOAD: return (EObject)createPostLoad();
			case OrmPackage.POST_PERSIST: return (EObject)createPostPersist();
			case OrmPackage.POST_REMOVE: return (EObject)createPostRemove();
			case OrmPackage.POST_UPDATE: return (EObject)createPostUpdate();
			case OrmPackage.PRE_PERSIST: return (EObject)createPrePersist();
			case OrmPackage.PRE_REMOVE: return (EObject)createPreRemove();
			case OrmPackage.PRE_UPDATE: return (EObject)createPreUpdate();
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN: return (EObject)createXmlPrimaryKeyJoinColumn();
			case OrmPackage.XML_QUERY_HINT: return (EObject)createXmlQueryHint();
			case OrmPackage.XML_SECONDARY_TABLE: return (EObject)createXmlSecondaryTable();
			case OrmPackage.XML_SEQUENCE_GENERATOR: return (EObject)createXmlSequenceGenerator();
			case OrmPackage.SQL_RESULT_SET_MAPPING: return (EObject)createSqlResultSetMapping();
			case OrmPackage.XML_TABLE: return (EObject)createXmlTable();
			case OrmPackage.XML_TABLE_GENERATOR: return (EObject)createXmlTableGenerator();
			case OrmPackage.XML_TRANSIENT: return (EObject)createXmlTransient();
			case OrmPackage.XML_UNIQUE_CONSTRAINT: return (EObject)createXmlUniqueConstraint();
			case OrmPackage.XML_VERSION: return (EObject)createXmlVersion();
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
			case OrmPackage.ACCESS_TYPE:
				return createAccessTypeFromString(eDataType, initialValue);
			case OrmPackage.DISCRIMINATOR_TYPE:
				return createDiscriminatorTypeFromString(eDataType, initialValue);
			case OrmPackage.ENUM_TYPE:
				return createEnumTypeFromString(eDataType, initialValue);
			case OrmPackage.FETCH_TYPE:
				return createFetchTypeFromString(eDataType, initialValue);
			case OrmPackage.GENERATION_TYPE:
				return createGenerationTypeFromString(eDataType, initialValue);
			case OrmPackage.INHERITANCE_TYPE:
				return createInheritanceTypeFromString(eDataType, initialValue);
			case OrmPackage.TEMPORAL_TYPE:
				return createTemporalTypeFromString(eDataType, initialValue);
			case OrmPackage.DISCRIMINATOR_VALUE:
				return createDiscriminatorValueFromString(eDataType, initialValue);
			case OrmPackage.ENUMERATED:
				return createEnumeratedFromString(eDataType, initialValue);
			case OrmPackage.ORDER_BY:
				return createOrderByFromString(eDataType, initialValue);
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
			case OrmPackage.ACCESS_TYPE:
				return convertAccessTypeToString(eDataType, instanceValue);
			case OrmPackage.DISCRIMINATOR_TYPE:
				return convertDiscriminatorTypeToString(eDataType, instanceValue);
			case OrmPackage.ENUM_TYPE:
				return convertEnumTypeToString(eDataType, instanceValue);
			case OrmPackage.FETCH_TYPE:
				return convertFetchTypeToString(eDataType, instanceValue);
			case OrmPackage.GENERATION_TYPE:
				return convertGenerationTypeToString(eDataType, instanceValue);
			case OrmPackage.INHERITANCE_TYPE:
				return convertInheritanceTypeToString(eDataType, instanceValue);
			case OrmPackage.TEMPORAL_TYPE:
				return convertTemporalTypeToString(eDataType, instanceValue);
			case OrmPackage.DISCRIMINATOR_VALUE:
				return convertDiscriminatorValueToString(eDataType, instanceValue);
			case OrmPackage.ENUMERATED:
				return convertEnumeratedToString(eDataType, instanceValue);
			case OrmPackage.ORDER_BY:
				return convertOrderByToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlAssociationOverride createXmlAssociationOverride()
	{
		XmlAssociationOverride xmlAssociationOverride = new XmlAssociationOverride();
		return xmlAssociationOverride;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlAttributeOverride createXmlAttributeOverride()
	{
		XmlAttributeOverride xmlAttributeOverride = new XmlAttributeOverride();
		return xmlAttributeOverride;
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
	public CascadeType createCascadeType()
	{
		CascadeType cascadeType = new CascadeType();
		return cascadeType;
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
	public XmlColumn createXmlColumn()
	{
		XmlColumn xmlColumn = new XmlColumn();
		return xmlColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ColumnResult createColumnResult()
	{
		ColumnResult columnResult = new ColumnResult();
		return columnResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlDiscriminatorColumn createXmlDiscriminatorColumn()
	{
		XmlDiscriminatorColumn xmlDiscriminatorColumn = new XmlDiscriminatorColumn();
		return xmlDiscriminatorColumn;
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
	public EntityListener createEntityListener()
	{
		EntityListener entityListener = new EntityListener();
		return entityListener;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityListeners createEntityListeners()
	{
		EntityListeners entityListeners = new EntityListeners();
		return entityListeners;
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
	public EntityResult createEntityResult()
	{
		EntityResult entityResult = new EntityResult();
		return entityResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldResult createFieldResult()
	{
		FieldResult fieldResult = new FieldResult();
		return fieldResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlGeneratedValue createXmlGeneratedValue()
	{
		XmlGeneratedValue xmlGeneratedValue = new XmlGeneratedValue();
		return xmlGeneratedValue;
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
	public XmlIdClass createXmlIdClass()
	{
		XmlIdClass xmlIdClass = new XmlIdClass();
		return xmlIdClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Inheritance createInheritance()
	{
		Inheritance inheritance = new Inheritance();
		return inheritance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlJoinColumn createXmlJoinColumn()
	{
		XmlJoinColumn xmlJoinColumn = new XmlJoinColumn();
		return xmlJoinColumn;
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
	public Lob createLob()
	{
		Lob lob = new Lob();
		return lob;
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
	public MapKey createMapKey()
	{
		MapKey mapKey = new MapKey();
		return mapKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlMapKeyClass createXmlMapKeyClass()
	{
		XmlMapKeyClass xmlMapKeyClass = new XmlMapKeyClass();
		return xmlMapKeyClass;
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
	public XmlNamedNativeQuery createXmlNamedNativeQuery()
	{
		XmlNamedNativeQuery xmlNamedNativeQuery = new XmlNamedNativeQuery();
		return xmlNamedNativeQuery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlNamedQuery createXmlNamedQuery()
	{
		XmlNamedQuery xmlNamedQuery = new XmlNamedQuery();
		return xmlNamedQuery;
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
	public PostLoad createPostLoad()
	{
		PostLoad postLoad = new PostLoad();
		return postLoad;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PostPersist createPostPersist()
	{
		PostPersist postPersist = new PostPersist();
		return postPersist;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PostRemove createPostRemove()
	{
		PostRemove postRemove = new PostRemove();
		return postRemove;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PostUpdate createPostUpdate()
	{
		PostUpdate postUpdate = new PostUpdate();
		return postUpdate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrePersist createPrePersist()
	{
		PrePersist prePersist = new PrePersist();
		return prePersist;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreRemove createPreRemove()
	{
		PreRemove preRemove = new PreRemove();
		return preRemove;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreUpdate createPreUpdate()
	{
		PreUpdate preUpdate = new PreUpdate();
		return preUpdate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPrimaryKeyJoinColumn createXmlPrimaryKeyJoinColumn()
	{
		XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = new XmlPrimaryKeyJoinColumn();
		return xmlPrimaryKeyJoinColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlQueryHint createXmlQueryHint()
	{
		XmlQueryHint xmlQueryHint = new XmlQueryHint();
		return xmlQueryHint;
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
	public XmlSequenceGenerator createXmlSequenceGenerator()
	{
		XmlSequenceGenerator xmlSequenceGenerator = new XmlSequenceGenerator();
		return xmlSequenceGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SqlResultSetMapping createSqlResultSetMapping()
	{
		SqlResultSetMapping sqlResultSetMapping = new SqlResultSetMapping();
		return sqlResultSetMapping;
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
	public XmlUniqueConstraint createXmlUniqueConstraint()
	{
		XmlUniqueConstraint xmlUniqueConstraint = new XmlUniqueConstraint();
		return xmlUniqueConstraint;
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
	public AccessType createAccessTypeFromString(EDataType eDataType, String initialValue)
	{
		AccessType result = AccessType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAccessTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiscriminatorType createDiscriminatorTypeFromString(EDataType eDataType, String initialValue)
	{
		DiscriminatorType result = DiscriminatorType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDiscriminatorTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumType createEnumTypeFromString(EDataType eDataType, String initialValue)
	{
		EnumType result = EnumType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEnumTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FetchType createFetchTypeFromString(EDataType eDataType, String initialValue)
	{
		FetchType result = FetchType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFetchTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenerationType createGenerationTypeFromString(EDataType eDataType, String initialValue)
	{
		GenerationType result = GenerationType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGenerationTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InheritanceType createInheritanceTypeFromString(EDataType eDataType, String initialValue)
	{
		InheritanceType result = InheritanceType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertInheritanceTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemporalType createTemporalTypeFromString(EDataType eDataType, String initialValue)
	{
		TemporalType result = TemporalType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTemporalTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createDiscriminatorValueFromString(EDataType eDataType, String initialValue)
	{
		return (String)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDiscriminatorValueToString(EDataType eDataType, Object instanceValue)
	{
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createEnumeratedFromString(EDataType eDataType, String initialValue)
	{
		return (Enumerator)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEnumeratedToString(EDataType eDataType, Object instanceValue)
	{
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createOrderByFromString(EDataType eDataType, String initialValue)
	{
		return (String)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOrderByToString(EDataType eDataType, Object instanceValue)
	{
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrmPackage getOrmPackage()
	{
		return (OrmPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static OrmPackage getPackage()
	{
		return OrmPackage.eINSTANCE;
	}

} //OrmFactory
