/**
 * <copyright>
 * </copyright>
 *
 * $Id: EclipseLinkOrmFactory.java,v 1.1 2008/10/02 21:11:39 pfullbright Exp $
 */
package org.eclipse.jpt.eclipselink.core.resource.orm;

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
			EclipseLinkOrmFactory theEclipseLinkOrmFactory = (EclipseLinkOrmFactory)EPackage.Registry.INSTANCE.getEFactory("jpt.elorm.xmi"); 
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
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_METADATA: return (EObject)createXmlPersistenceUnitMetadata();
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS: return (EObject)createXmlPersistenceUnitDefaults();
			case EclipseLinkOrmPackage.XML_MAPPED_SUPERCLASS: return (EObject)createXmlMappedSuperclass();
			case EclipseLinkOrmPackage.XML_ENTITY: return (EObject)createXmlEntity();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE: return (EObject)createXmlEmbeddable();
			case EclipseLinkOrmPackage.ATTRIBUTES: return (EObject)createAttributes();
			case EclipseLinkOrmPackage.XML_ID_IMPL: return (EObject)createXmlIdImpl();
			case EclipseLinkOrmPackage.XML_EMBEDDED_ID_IMPL: return (EObject)createXmlEmbeddedIdImpl();
			case EclipseLinkOrmPackage.XML_EMBEDDED_IMPL: return (EObject)createXmlEmbeddedImpl();
			case EclipseLinkOrmPackage.XML_BASIC_IMPL: return (EObject)createXmlBasicImpl();
			case EclipseLinkOrmPackage.XML_VERSION_IMPL: return (EObject)createXmlVersionImpl();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL: return (EObject)createXmlManyToOneImpl();
			case EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL: return (EObject)createXmlOneToManyImpl();
			case EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL: return (EObject)createXmlOneToOneImpl();
			case EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL: return (EObject)createXmlManyToManyImpl();
			case EclipseLinkOrmPackage.XML_TRANSIENT_IMPL: return (EObject)createXmlTransientImpl();
			case EclipseLinkOrmPackage.XML_ASSOCIATION_OVERRIDE_IMPL: return (EObject)createXmlAssociationOverrideImpl();
			case EclipseLinkOrmPackage.XML_ATTRIBUTE_OVERRIDE_IMPL: return (EObject)createXmlAttributeOverrideImpl();
			case EclipseLinkOrmPackage.CASCADE_TYPE_IMPL: return (EObject)createCascadeTypeImpl();
			case EclipseLinkOrmPackage.XML_COLUMN_IMPL: return (EObject)createXmlColumnImpl();
			case EclipseLinkOrmPackage.COLUMN_RESULT: return (EObject)createColumnResult();
			case EclipseLinkOrmPackage.XML_DISCRIMINATOR_COLUMN: return (EObject)createXmlDiscriminatorColumn();
			case EclipseLinkOrmPackage.ENTITY_LISTENERS: return (EObject)createEntityListeners();
			case EclipseLinkOrmPackage.ENTITY_LISTENER: return (EObject)createEntityListener();
			case EclipseLinkOrmPackage.ENTITY_RESULT: return (EObject)createEntityResult();
			case EclipseLinkOrmPackage.EVENT_METHOD: return (EObject)createEventMethod();
			case EclipseLinkOrmPackage.FIELD_RESULT: return (EObject)createFieldResult();
			case EclipseLinkOrmPackage.XML_GENERATED_VALUE_IMPL: return (EObject)createXmlGeneratedValueImpl();
			case EclipseLinkOrmPackage.XML_ID_CLASS: return (EObject)createXmlIdClass();
			case EclipseLinkOrmPackage.INHERITANCE: return (EObject)createInheritance();
			case EclipseLinkOrmPackage.XML_JOIN_COLUMN_IMPL: return (EObject)createXmlJoinColumnImpl();
			case EclipseLinkOrmPackage.XML_JOIN_TABLE_IMPL: return (EObject)createXmlJoinTableImpl();
			case EclipseLinkOrmPackage.LOB: return (EObject)createLob();
			case EclipseLinkOrmPackage.MAP_KEY_IMPL: return (EObject)createMapKeyImpl();
			case EclipseLinkOrmPackage.XML_NAMED_NATIVE_QUERY: return (EObject)createXmlNamedNativeQuery();
			case EclipseLinkOrmPackage.XML_NAMED_QUERY: return (EObject)createXmlNamedQuery();
			case EclipseLinkOrmPackage.POST_LOAD: return (EObject)createPostLoad();
			case EclipseLinkOrmPackage.POST_PERSIST: return (EObject)createPostPersist();
			case EclipseLinkOrmPackage.POST_REMOVE: return (EObject)createPostRemove();
			case EclipseLinkOrmPackage.POST_UPDATE: return (EObject)createPostUpdate();
			case EclipseLinkOrmPackage.PRE_PERSIST: return (EObject)createPrePersist();
			case EclipseLinkOrmPackage.PRE_REMOVE: return (EObject)createPreRemove();
			case EclipseLinkOrmPackage.PRE_UPDATE: return (EObject)createPreUpdate();
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN_IMPL: return (EObject)createXmlPrimaryKeyJoinColumnImpl();
			case EclipseLinkOrmPackage.XML_QUERY_HINT: return (EObject)createXmlQueryHint();
			case EclipseLinkOrmPackage.XML_TABLE: return (EObject)createXmlTable();
			case EclipseLinkOrmPackage.XML_SECONDARY_TABLE_IMPL: return (EObject)createXmlSecondaryTableImpl();
			case EclipseLinkOrmPackage.XML_SEQUENCE_GENERATOR_IMPL: return (EObject)createXmlSequenceGeneratorImpl();
			case EclipseLinkOrmPackage.SQL_RESULT_SET_MAPPING: return (EObject)createSqlResultSetMapping();
			case EclipseLinkOrmPackage.XML_TABLE_GENERATOR_IMPL: return (EObject)createXmlTableGeneratorImpl();
			case EclipseLinkOrmPackage.XML_UNIQUE_CONSTRAINT_IMPL: return (EObject)createXmlUniqueConstraintImpl();
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
			case EclipseLinkOrmPackage.ACCESS_TYPE:
				return createAccessTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.DISCRIMINATOR_TYPE:
				return createDiscriminatorTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.ENUM_TYPE:
				return createEnumTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.FETCH_TYPE:
				return createFetchTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.GENERATION_TYPE:
				return createGenerationTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.INHERITANCE_TYPE:
				return createInheritanceTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.TEMPORAL_TYPE:
				return createTemporalTypeFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.DISCRIMINATOR_VALUE:
				return createDiscriminatorValueFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.ENUMERATED:
				return createEnumeratedFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.ORDER_BY:
				return createOrderByFromString(eDataType, initialValue);
			case EclipseLinkOrmPackage.VERSION_TYPE:
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
			case EclipseLinkOrmPackage.ACCESS_TYPE:
				return convertAccessTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.DISCRIMINATOR_TYPE:
				return convertDiscriminatorTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.ENUM_TYPE:
				return convertEnumTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.FETCH_TYPE:
				return convertFetchTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.GENERATION_TYPE:
				return convertGenerationTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.INHERITANCE_TYPE:
				return convertInheritanceTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.TEMPORAL_TYPE:
				return convertTemporalTypeToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.DISCRIMINATOR_VALUE:
				return convertDiscriminatorValueToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.ENUMERATED:
				return convertEnumeratedToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.ORDER_BY:
				return convertOrderByToString(eDataType, instanceValue);
			case EclipseLinkOrmPackage.VERSION_TYPE:
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
	public XmlUniqueConstraintImpl createXmlUniqueConstraintImpl()
	{
		XmlUniqueConstraintImpl xmlUniqueConstraintImpl = new XmlUniqueConstraintImpl();
		return xmlUniqueConstraintImpl;
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
