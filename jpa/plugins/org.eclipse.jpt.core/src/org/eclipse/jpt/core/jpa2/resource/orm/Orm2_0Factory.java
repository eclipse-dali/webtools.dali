/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.resource.orm;

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
 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package
 * @generated
 */
public class Orm2_0Factory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final Orm2_0Factory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Orm2_0Factory init()
	{
		try
		{
			Orm2_0Factory theOrm2_0Factory = (Orm2_0Factory)EPackage.Registry.INSTANCE.getEFactory("jpt2_0.orm.xmi"); 
			if (theOrm2_0Factory != null)
			{
				return theOrm2_0Factory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new Orm2_0Factory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Orm2_0Factory()
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
			case Orm2_0Package.XML_ENTITY_MAPPINGS: return (EObject)createXmlEntityMappings();
			case Orm2_0Package.XML_PERSISTENCE_UNIT_METADATA: return (EObject)createXmlPersistenceUnitMetadata();
			case Orm2_0Package.XML_PERSISTENCE_UNIT_DEFAULTS: return (EObject)createXmlPersistenceUnitDefaults();
			case Orm2_0Package.XML_ENTITY: return (EObject)createXmlEntity();
			case Orm2_0Package.XML_EMBEDDABLE: return (EObject)createXmlEmbeddable();
			case Orm2_0Package.XML_MAPPED_SUPERCLASS: return (EObject)createXmlMappedSuperclass();
			case Orm2_0Package.ATTRIBUTES: return (EObject)createAttributes();
			case Orm2_0Package.XML_ELEMENT_COLLECTION: return (EObject)createXmlElementCollection();
			case Orm2_0Package.XML_ID: return (EObject)createXmlId();
			case Orm2_0Package.XML_EMBEDDED_ID: return (EObject)createXmlEmbeddedId();
			case Orm2_0Package.XML_EMBEDDED: return (EObject)createXmlEmbedded();
			case Orm2_0Package.XML_BASIC: return (EObject)createXmlBasic();
			case Orm2_0Package.XML_VERSION: return (EObject)createXmlVersion();
			case Orm2_0Package.XML_MANY_TO_ONE: return (EObject)createXmlManyToOne();
			case Orm2_0Package.XML_ONE_TO_MANY: return (EObject)createXmlOneToMany();
			case Orm2_0Package.XML_ONE_TO_ONE: return (EObject)createXmlOneToOne();
			case Orm2_0Package.XML_MANY_TO_MANY: return (EObject)createXmlManyToMany();
			case Orm2_0Package.XML_TRANSIENT: return (EObject)createXmlTransient();
			case Orm2_0Package.XML_ASSOCIATION_OVERRIDE: return (EObject)createXmlAssociationOverride();
			case Orm2_0Package.XML_ATTRIBUTE_OVERRIDE: return (EObject)createXmlAttributeOverride();
			case Orm2_0Package.ENTITY_LISTENERS: return (EObject)createEntityListeners();
			case Orm2_0Package.ENTITY_LISTENER: return (EObject)createEntityListener();
			case Orm2_0Package.XML_NAMED_NATIVE_QUERY: return (EObject)createXmlNamedNativeQuery();
			case Orm2_0Package.XML_NAMED_QUERY: return (EObject)createXmlNamedQuery();
			case Orm2_0Package.XML_SEQUENCE_GENERATOR: return (EObject)createXmlSequenceGenerator();
			case Orm2_0Package.XML_TABLE_GENERATOR: return (EObject)createXmlTableGenerator();
			case Orm2_0Package.SQL_RESULT_SET_MAPPING: return (EObject)createSqlResultSetMapping();
			case Orm2_0Package.POST_LOAD: return (EObject)createPostLoad();
			case Orm2_0Package.POST_PERSIST: return (EObject)createPostPersist();
			case Orm2_0Package.POST_REMOVE: return (EObject)createPostRemove();
			case Orm2_0Package.POST_UPDATE: return (EObject)createPostUpdate();
			case Orm2_0Package.PRE_PERSIST: return (EObject)createPrePersist();
			case Orm2_0Package.PRE_REMOVE: return (EObject)createPreRemove();
			case Orm2_0Package.PRE_UPDATE: return (EObject)createPreUpdate();
			case Orm2_0Package.XML_QUERY_HINT: return (EObject)createXmlQueryHint();
			case Orm2_0Package.XML_COLLECTION_TABLE: return (EObject)createXmlCollectionTable();
			case Orm2_0Package.XML_ORDER_COLUMN: return (EObject)createXmlOrderColumn();
			case Orm2_0Package.XML_MAP_KEY_COLUMN: return (EObject)createXmlMapKeyColumn();
			case Orm2_0Package.XML_MAP_KEY_JOIN_COLUMN: return (EObject)createXmlMapKeyJoinColumn();
			case Orm2_0Package.XML_MAP_KEY_CLASS: return (EObject)createXmlMapKeyClass();
			case Orm2_0Package.XML_UNIQUE_CONSTRAINT: return (EObject)createXmlUniqueConstraint();
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
			case Orm2_0Package.LOCK_MODE_TYPE:
				return createLockModeTypeFromString(eDataType, initialValue);
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
			case Orm2_0Package.LOCK_MODE_TYPE:
				return convertLockModeTypeToString(eDataType, instanceValue);
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
	public XmlMapKeyColumn createXmlMapKeyColumn()
	{
		XmlMapKeyColumn xmlMapKeyColumn = new XmlMapKeyColumn();
		return xmlMapKeyColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlMapKeyJoinColumn createXmlMapKeyJoinColumn()
	{
		XmlMapKeyJoinColumn xmlMapKeyJoinColumn = new XmlMapKeyJoinColumn();
		return xmlMapKeyJoinColumn;
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
	public LockModeType createLockModeTypeFromString(EDataType eDataType, String initialValue)
	{
		LockModeType result = LockModeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLockModeTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Orm2_0Package getOrm2_0Package()
	{
		return (Orm2_0Package)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static Orm2_0Package getPackage()
	{
		return Orm2_0Package.eINSTANCE;
	}

} //Orm2_0Factory
