/**
 * <copyright>
 * </copyright>
 *
 * $Id: OrmFactory.java,v 1.1.2.1 2007/09/17 20:49:52 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

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
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage
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
			case OrmPackage.ENTITY_MAPPINGS: return createEntityMappings();
			case OrmPackage.PERSISTENCE_UNIT_METADATA: return createPersistenceUnitMetadata();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS: return createPersistenceUnitDefaults();
			case OrmPackage.MAPPED_SUPERCLASS: return createMappedSuperclass();
			case OrmPackage.ENTITY: return createEntity();
			case OrmPackage.EMBEDDABLE: return createEmbeddable();
			case OrmPackage.ATTRIBUTES: return createAttributes();
			case OrmPackage.EMBEDDABLE_ATTRIBUTES: return createEmbeddableAttributes();
			case OrmPackage.ID: return createId();
			case OrmPackage.EMBEDDED_ID: return createEmbeddedId();
			case OrmPackage.BASIC: return createBasic();
			case OrmPackage.VERSION: return createVersion();
			case OrmPackage.MANY_TO_ONE: return createManyToOne();
			case OrmPackage.ONE_TO_MANY: return createOneToMany();
			case OrmPackage.ONE_TO_ONE: return createOneToOne();
			case OrmPackage.MANY_TO_MANY: return createManyToMany();
			case OrmPackage.EMBEDDED: return createEmbedded();
			case OrmPackage.TRANSIENT: return createTransient();
			case OrmPackage.ASSOCIATION_OVERRIDE: return createAssociationOverride();
			case OrmPackage.ATTRIBUTE_OVERRIDE: return createAttributeOverride();
			case OrmPackage.CASCADE_TYPE: return createCascadeType();
			case OrmPackage.COLUMN: return createColumn();
			case OrmPackage.COLUMN_RESULT: return createColumnResult();
			case OrmPackage.DISCRIMINATOR_COLUMN: return createDiscriminatorColumn();
			case OrmPackage.ENTITY_LISTENERS: return createEntityListeners();
			case OrmPackage.ENTITY_LISTENER: return createEntityListener();
			case OrmPackage.ENTITY_RESULT: return createEntityResult();
			case OrmPackage.EVENT_METHOD: return createEventMethod();
			case OrmPackage.FIELD_RESULT: return createFieldResult();
			case OrmPackage.GENERATED_VALUE: return createGeneratedValue();
			case OrmPackage.ID_CLASS: return createIdClass();
			case OrmPackage.INHERITANCE: return createInheritance();
			case OrmPackage.JOIN_COLUMN: return createJoinColumn();
			case OrmPackage.JOIN_TABLE: return createJoinTable();
			case OrmPackage.LOB: return createLob();
			case OrmPackage.MAP_KEY: return createMapKey();
			case OrmPackage.NAMED_NATIVE_QUERY: return createNamedNativeQuery();
			case OrmPackage.NAMED_QUERY: return createNamedQuery();
			case OrmPackage.POST_LOAD: return createPostLoad();
			case OrmPackage.POST_PERSIST: return createPostPersist();
			case OrmPackage.POST_REMOVE: return createPostRemove();
			case OrmPackage.POST_UPDATE: return createPostUpdate();
			case OrmPackage.PRE_PERSIST: return createPrePersist();
			case OrmPackage.PRE_REMOVE: return createPreRemove();
			case OrmPackage.PRE_UPDATE: return createPreUpdate();
			case OrmPackage.PRIMARY_KEY_JOIN_COLUMN: return createPrimaryKeyJoinColumn();
			case OrmPackage.QUERY_HINT: return createQueryHint();
			case OrmPackage.SECONDARY_TABLE: return createSecondaryTable();
			case OrmPackage.SEQUENCE_GENERATOR: return createSequenceGenerator();
			case OrmPackage.SQL_RESULT_SET_MAPPING: return createSqlResultSetMapping();
			case OrmPackage.TABLE: return createTable();
			case OrmPackage.TABLE_GENERATOR: return createTableGenerator();
			case OrmPackage.UNIQUE_CONSTRAINT: return createUniqueConstraint();
			case OrmPackage.EMPTY_TYPE: return createEmptyType();
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
			case OrmPackage.ACCESS_TYPE_OBJECT:
				return createAccessTypeObjectFromString(eDataType, initialValue);
			case OrmPackage.DISCRIMINATOR_TYPE_OBJECT:
				return createDiscriminatorTypeObjectFromString(eDataType, initialValue);
			case OrmPackage.DISCRIMINATOR_VALUE:
				return createDiscriminatorValueFromString(eDataType, initialValue);
			case OrmPackage.ENUMERATED:
				return createEnumeratedFromString(eDataType, initialValue);
			case OrmPackage.ENUM_TYPE_OBJECT:
				return createEnumTypeObjectFromString(eDataType, initialValue);
			case OrmPackage.FETCH_TYPE_OBJECT:
				return createFetchTypeObjectFromString(eDataType, initialValue);
			case OrmPackage.GENERATION_TYPE_OBJECT:
				return createGenerationTypeObjectFromString(eDataType, initialValue);
			case OrmPackage.INHERITANCE_TYPE_OBJECT:
				return createInheritanceTypeObjectFromString(eDataType, initialValue);
			case OrmPackage.ORDER_BY:
				return createOrderByFromString(eDataType, initialValue);
			case OrmPackage.TEMPORAL:
				return createTemporalFromString(eDataType, initialValue);
			case OrmPackage.TEMPORAL_TYPE_OBJECT:
				return createTemporalTypeObjectFromString(eDataType, initialValue);
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
			case OrmPackage.ACCESS_TYPE_OBJECT:
				return convertAccessTypeObjectToString(eDataType, instanceValue);
			case OrmPackage.DISCRIMINATOR_TYPE_OBJECT:
				return convertDiscriminatorTypeObjectToString(eDataType, instanceValue);
			case OrmPackage.DISCRIMINATOR_VALUE:
				return convertDiscriminatorValueToString(eDataType, instanceValue);
			case OrmPackage.ENUMERATED:
				return convertEnumeratedToString(eDataType, instanceValue);
			case OrmPackage.ENUM_TYPE_OBJECT:
				return convertEnumTypeObjectToString(eDataType, instanceValue);
			case OrmPackage.FETCH_TYPE_OBJECT:
				return convertFetchTypeObjectToString(eDataType, instanceValue);
			case OrmPackage.GENERATION_TYPE_OBJECT:
				return convertGenerationTypeObjectToString(eDataType, instanceValue);
			case OrmPackage.INHERITANCE_TYPE_OBJECT:
				return convertInheritanceTypeObjectToString(eDataType, instanceValue);
			case OrmPackage.ORDER_BY:
				return convertOrderByToString(eDataType, instanceValue);
			case OrmPackage.TEMPORAL:
				return convertTemporalToString(eDataType, instanceValue);
			case OrmPackage.TEMPORAL_TYPE_OBJECT:
				return convertTemporalTypeObjectToString(eDataType, instanceValue);
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
	public EntityMappings createEntityMappings()
	{
		EntityMappings entityMappings = new EntityMappings();
		return entityMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersistenceUnitMetadata createPersistenceUnitMetadata()
	{
		PersistenceUnitMetadata persistenceUnitMetadata = new PersistenceUnitMetadata();
		return persistenceUnitMetadata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersistenceUnitDefaults createPersistenceUnitDefaults()
	{
		PersistenceUnitDefaults persistenceUnitDefaults = new PersistenceUnitDefaults();
		return persistenceUnitDefaults;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MappedSuperclass createMappedSuperclass()
	{
		MappedSuperclass mappedSuperclass = new MappedSuperclass();
		return mappedSuperclass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity createEntity()
	{
		Entity entity = new Entity();
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Embeddable createEmbeddable()
	{
		Embeddable embeddable = new Embeddable();
		return embeddable;
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
	public EmbeddableAttributes createEmbeddableAttributes()
	{
		EmbeddableAttributes embeddableAttributes = new EmbeddableAttributes();
		return embeddableAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Id createId()
	{
		Id id = new Id();
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmbeddedId createEmbeddedId()
	{
		EmbeddedId embeddedId = new EmbeddedId();
		return embeddedId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Basic createBasic()
	{
		Basic basic = new Basic();
		return basic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Version createVersion()
	{
		Version version = new Version();
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManyToOne createManyToOne()
	{
		ManyToOne manyToOne = new ManyToOne();
		return manyToOne;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OneToMany createOneToMany()
	{
		OneToMany oneToMany = new OneToMany();
		return oneToMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OneToOne createOneToOne()
	{
		OneToOne oneToOne = new OneToOne();
		return oneToOne;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManyToMany createManyToMany()
	{
		ManyToMany manyToMany = new ManyToMany();
		return manyToMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Embedded createEmbedded()
	{
		Embedded embedded = new Embedded();
		return embedded;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transient createTransient()
	{
		Transient transient_ = new Transient();
		return transient_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssociationOverride createAssociationOverride()
	{
		AssociationOverride associationOverride = new AssociationOverride();
		return associationOverride;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeOverride createAttributeOverride()
	{
		AttributeOverride attributeOverride = new AttributeOverride();
		return attributeOverride;
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
	public Column createColumn()
	{
		Column column = new Column();
		return column;
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
	public DiscriminatorColumn createDiscriminatorColumn()
	{
		DiscriminatorColumn discriminatorColumn = new DiscriminatorColumn();
		return discriminatorColumn;
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
	public GeneratedValue createGeneratedValue()
	{
		GeneratedValue generatedValue = new GeneratedValue();
		return generatedValue;
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
	public JoinColumn createJoinColumn()
	{
		JoinColumn joinColumn = new JoinColumn();
		return joinColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JoinTable createJoinTable()
	{
		JoinTable joinTable = new JoinTable();
		return joinTable;
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
	public NamedNativeQuery createNamedNativeQuery()
	{
		NamedNativeQuery namedNativeQuery = new NamedNativeQuery();
		return namedNativeQuery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedQuery createNamedQuery()
	{
		NamedQuery namedQuery = new NamedQuery();
		return namedQuery;
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
	public QueryHint createQueryHint()
	{
		QueryHint queryHint = new QueryHint();
		return queryHint;
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
	public PrimaryKeyJoinColumn createPrimaryKeyJoinColumn()
	{
		PrimaryKeyJoinColumn primaryKeyJoinColumn = new PrimaryKeyJoinColumn();
		return primaryKeyJoinColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecondaryTable createSecondaryTable()
	{
		SecondaryTable secondaryTable = new SecondaryTable();
		return secondaryTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SequenceGenerator createSequenceGenerator()
	{
		SequenceGenerator sequenceGenerator = new SequenceGenerator();
		return sequenceGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Table createTable()
	{
		Table table = new Table();
		return table;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TableGenerator createTableGenerator()
	{
		TableGenerator tableGenerator = new TableGenerator();
		return tableGenerator;
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
	public EmptyType createEmptyType()
	{
		EmptyType emptyType = new EmptyType();
		return emptyType;
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
	public Enumerator createAccessTypeObjectFromString(EDataType eDataType, String initialValue)
	{
		return (Enumerator)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAccessTypeObjectToString(EDataType eDataType, Object instanceValue)
	{
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createDiscriminatorTypeObjectFromString(EDataType eDataType, String initialValue)
	{
		return (Enumerator)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDiscriminatorTypeObjectToString(EDataType eDataType, Object instanceValue)
	{
		return super.convertToString(eDataType, instanceValue);
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
	public Enumerator createEnumTypeObjectFromString(EDataType eDataType, String initialValue)
	{
		return (Enumerator)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEnumTypeObjectToString(EDataType eDataType, Object instanceValue)
	{
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createFetchTypeObjectFromString(EDataType eDataType, String initialValue)
	{
		return (Enumerator)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFetchTypeObjectToString(EDataType eDataType, Object instanceValue)
	{
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createGenerationTypeObjectFromString(EDataType eDataType, String initialValue)
	{
		return (Enumerator)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGenerationTypeObjectToString(EDataType eDataType, Object instanceValue)
	{
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createInheritanceTypeObjectFromString(EDataType eDataType, String initialValue)
	{
		return (Enumerator)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertInheritanceTypeObjectToString(EDataType eDataType, Object instanceValue)
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
	public Enumerator createTemporalFromString(EDataType eDataType, String initialValue)
	{
		return (Enumerator)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTemporalToString(EDataType eDataType, Object instanceValue)
	{
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator createTemporalTypeObjectFromString(EDataType eDataType, String initialValue)
	{
		return (Enumerator)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTemporalTypeObjectToString(EDataType eDataType, Object instanceValue)
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
