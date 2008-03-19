/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
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
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
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
			case OrmPackage.XML_ENTITY_MAPPINGS: return (EObject)createXmlEntityMappings();
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA: return (EObject)createXmlPersistenceUnitMetadata();
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS: return (EObject)createXmlPersistenceUnitDefaults();
			case OrmPackage.XML_MAPPED_SUPERCLASS: return (EObject)createXmlMappedSuperclass();
			case OrmPackage.XML_ENTITY: return (EObject)createXmlEntity();
			case OrmPackage.XML_EMBEDDABLE: return (EObject)createXmlEmbeddable();
			case OrmPackage.ATTRIBUTES: return (EObject)createAttributes();
			case OrmPackage.XML_ID_IMPL: return (EObject)createXmlIdImpl();
			case OrmPackage.XML_EMBEDDED_ID_IMPL: return (EObject)createXmlEmbeddedIdImpl();
			case OrmPackage.XML_EMBEDDED_IMPL: return (EObject)createXmlEmbeddedImpl();
			case OrmPackage.XML_BASIC_IMPL: return (EObject)createXmlBasicImpl();
			case OrmPackage.XML_VERSION_IMPL: return (EObject)createXmlVersionImpl();
			case OrmPackage.XML_MANY_TO_ONE_IMPL: return (EObject)createXmlManyToOneImpl();
			case OrmPackage.XML_ONE_TO_MANY_IMPL: return (EObject)createXmlOneToManyImpl();
			case OrmPackage.XML_ONE_TO_ONE_IMPL: return (EObject)createXmlOneToOneImpl();
			case OrmPackage.XML_MANY_TO_MANY_IMPL: return (EObject)createXmlManyToManyImpl();
			case OrmPackage.XML_TRANSIENT_IMPL: return (EObject)createXmlTransientImpl();
			case OrmPackage.XML_ASSOCIATION_OVERRIDE_IMPL: return (EObject)createXmlAssociationOverrideImpl();
			case OrmPackage.XML_ATTRIBUTE_OVERRIDE_IMPL: return (EObject)createXmlAttributeOverrideImpl();
			case OrmPackage.CASCADE_TYPE_IMPL: return (EObject)createCascadeTypeImpl();
			case OrmPackage.XML_COLUMN_IMPL: return (EObject)createXmlColumnImpl();
			case OrmPackage.COLUMN_RESULT: return (EObject)createColumnResult();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN: return (EObject)createXmlDiscriminatorColumn();
			case OrmPackage.ENTITY_LISTENERS: return (EObject)createEntityListeners();
			case OrmPackage.ENTITY_LISTENER: return (EObject)createEntityListener();
			case OrmPackage.ENTITY_RESULT: return (EObject)createEntityResult();
			case OrmPackage.EVENT_METHOD: return (EObject)createEventMethod();
			case OrmPackage.FIELD_RESULT: return (EObject)createFieldResult();
			case OrmPackage.XML_GENERATED_VALUE_IMPL: return (EObject)createXmlGeneratedValueImpl();
			case OrmPackage.ID_CLASS: return (EObject)createIdClass();
			case OrmPackage.INHERITANCE: return (EObject)createInheritance();
			case OrmPackage.XML_JOIN_COLUMN_IMPL: return (EObject)createXmlJoinColumnImpl();
			case OrmPackage.XML_JOIN_TABLE_IMPL: return (EObject)createXmlJoinTableImpl();
			case OrmPackage.LOB: return (EObject)createLob();
			case OrmPackage.MAP_KEY_IMPL: return (EObject)createMapKeyImpl();
			case OrmPackage.XML_NAMED_NATIVE_QUERY: return (EObject)createXmlNamedNativeQuery();
			case OrmPackage.XML_NAMED_QUERY: return (EObject)createXmlNamedQuery();
			case OrmPackage.POST_LOAD: return (EObject)createPostLoad();
			case OrmPackage.POST_PERSIST: return (EObject)createPostPersist();
			case OrmPackage.POST_REMOVE: return (EObject)createPostRemove();
			case OrmPackage.POST_UPDATE: return (EObject)createPostUpdate();
			case OrmPackage.PRE_PERSIST: return (EObject)createPrePersist();
			case OrmPackage.PRE_REMOVE: return (EObject)createPreRemove();
			case OrmPackage.PRE_UPDATE: return (EObject)createPreUpdate();
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN_IMPL: return (EObject)createXmlPrimaryKeyJoinColumnImpl();
			case OrmPackage.XML_QUERY_HINT: return (EObject)createXmlQueryHint();
			case OrmPackage.XML_TABLE: return (EObject)createXmlTable();
			case OrmPackage.XML_SECONDARY_TABLE_IMPL: return (EObject)createXmlSecondaryTableImpl();
			case OrmPackage.XML_SEQUENCE_GENERATOR_IMPL: return (EObject)createXmlSequenceGeneratorImpl();
			case OrmPackage.SQL_RESULT_SET_MAPPING: return (EObject)createSqlResultSetMapping();
			case OrmPackage.XML_TABLE_GENERATOR_IMPL: return (EObject)createXmlTableGeneratorImpl();
			case OrmPackage.UNIQUE_CONSTRAINT: return (EObject)createUniqueConstraint();
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
			case OrmPackage.VERSION_TYPE:
				return createVersionTypeFromString(eDataType, initialValue);
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
			case OrmPackage.VERSION_TYPE:
				return convertVersionTypeToString(eDataType, instanceValue);
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
	public XmlEmbeddedIdImpl createXmlEmbeddedIdImpl()
	{
		XmlEmbeddedIdImpl xmlEmbeddedIdImpl = new XmlEmbeddedIdImpl();
		return xmlEmbeddedIdImpl;
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
	public XmlEmbeddedImpl createXmlEmbeddedImpl()
	{
		XmlEmbeddedImpl xmlEmbeddedImpl = new XmlEmbeddedImpl();
		return xmlEmbeddedImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTransientImpl createXmlTransientImpl()
	{
		XmlTransientImpl xmlTransientImpl = new XmlTransientImpl();
		return xmlTransientImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlAssociationOverrideImpl createXmlAssociationOverrideImpl()
	{
		XmlAssociationOverrideImpl xmlAssociationOverrideImpl = new XmlAssociationOverrideImpl();
		return xmlAssociationOverrideImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlAttributeOverrideImpl createXmlAttributeOverrideImpl()
	{
		XmlAttributeOverrideImpl xmlAttributeOverrideImpl = new XmlAttributeOverrideImpl();
		return xmlAttributeOverrideImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CascadeTypeImpl createCascadeTypeImpl()
	{
		CascadeTypeImpl cascadeTypeImpl = new CascadeTypeImpl();
		return cascadeTypeImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlColumnImpl createXmlColumnImpl()
	{
		XmlColumnImpl xmlColumnImpl = new XmlColumnImpl();
		return xmlColumnImpl;
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
	public XmlGeneratedValueImpl createXmlGeneratedValueImpl()
	{
		XmlGeneratedValueImpl xmlGeneratedValueImpl = new XmlGeneratedValueImpl();
		return xmlGeneratedValueImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdClass createIdClass()
	{
		IdClass idClass = new IdClass();
		return idClass;
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
	public XmlJoinColumnImpl createXmlJoinColumnImpl()
	{
		XmlJoinColumnImpl xmlJoinColumnImpl = new XmlJoinColumnImpl();
		return xmlJoinColumnImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlJoinTableImpl createXmlJoinTableImpl()
	{
		XmlJoinTableImpl xmlJoinTableImpl = new XmlJoinTableImpl();
		return xmlJoinTableImpl;
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
	public MapKeyImpl createMapKeyImpl()
	{
		MapKeyImpl mapKeyImpl = new MapKeyImpl();
		return mapKeyImpl;
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
	public EventMethod createEventMethod()
	{
		EventMethod eventMethod = new EventMethod();
		return eventMethod;
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
	public XmlPrimaryKeyJoinColumnImpl createXmlPrimaryKeyJoinColumnImpl()
	{
		XmlPrimaryKeyJoinColumnImpl xmlPrimaryKeyJoinColumnImpl = new XmlPrimaryKeyJoinColumnImpl();
		return xmlPrimaryKeyJoinColumnImpl;
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
	public XmlSecondaryTableImpl createXmlSecondaryTableImpl()
	{
		XmlSecondaryTableImpl xmlSecondaryTableImpl = new XmlSecondaryTableImpl();
		return xmlSecondaryTableImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlSequenceGeneratorImpl createXmlSequenceGeneratorImpl()
	{
		XmlSequenceGeneratorImpl xmlSequenceGeneratorImpl = new XmlSequenceGeneratorImpl();
		return xmlSequenceGeneratorImpl;
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
	public XmlTableGeneratorImpl createXmlTableGeneratorImpl()
	{
		XmlTableGeneratorImpl xmlTableGeneratorImpl = new XmlTableGeneratorImpl();
		return xmlTableGeneratorImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UniqueConstraint createUniqueConstraint()
	{
		UniqueConstraint uniqueConstraint = new UniqueConstraint();
		return uniqueConstraint;
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
	public String createVersionTypeFromString(EDataType eDataType, String initialValue)
	{
		return (String)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVersionTypeToString(EDataType eDataType, Object instanceValue)
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
