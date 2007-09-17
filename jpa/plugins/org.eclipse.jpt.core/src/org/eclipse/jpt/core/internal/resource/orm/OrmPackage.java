/**
 * <copyright>
 * </copyright>
 *
 * $Id: OrmPackage.java,v 1.1.2.1 2007/09/17 20:49:50 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.emf.common.util.Enumerator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmFactory
 * @model kind="package"
 * @generated
 */
public class OrmPackage extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "orm";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.orm.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.core.resource.orm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final OrmPackage eINSTANCE = org.eclipse.jpt.core.internal.resource.orm.OrmPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings <em>Entity Mappings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings()
	 * @generated
	 */
	public static final int ENTITY_MAPPINGS = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__VERSION = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Persistence Unit Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA = 2;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__PACKAGE = 3;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__SCHEMA = 4;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__CATALOG = 5;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__ACCESS = 6;

	/**
	 * The feature id for the '<em><b>Sequence Generators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__SEQUENCE_GENERATORS = 7;

	/**
	 * The feature id for the '<em><b>Table Generators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__TABLE_GENERATORS = 8;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__NAMED_QUERIES = 9;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES = 10;

	/**
	 * The feature id for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS = 11;

	/**
	 * The feature id for the '<em><b>Mapped Superclasses</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__MAPPED_SUPERCLASSES = 12;

	/**
	 * The feature id for the '<em><b>Entities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__ENTITIES = 13;

	/**
	 * The feature id for the '<em><b>Embeddables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__EMBEDDABLES = 14;

	/**
	 * The number of structural features of the '<em>Entity Mappings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_FEATURE_COUNT = 15;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata <em>Persistence Unit Metadata</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPersistenceUnitMetadata()
	 * @generated
	 */
	public static final int PERSISTENCE_UNIT_METADATA = 1;

	/**
	 * The feature id for the '<em><b>Xml Mapping Metadata Complete</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE = 0;

	/**
	 * The feature id for the '<em><b>Persistence Unit Defaults</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS = 1;

	/**
	 * The number of structural features of the '<em>Persistence Unit Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults <em>Persistence Unit Defaults</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPersistenceUnitDefaults()
	 * @generated
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS = 2;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS__SCHEMA = 0;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS__CATALOG = 1;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS__ACCESS = 2;

	/**
	 * The feature id for the '<em><b>Cascade Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST = 3;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS = 4;

	/**
	 * The number of structural features of the '<em>Persistence Unit Defaults</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass <em>Mapped Superclass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getMappedSuperclass()
	 * @generated
	 */
	public static final int MAPPED_SUPERCLASS = 3;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__CLASS_NAME = 0;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__ACCESS = 1;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__METADATA_COMPLETE = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Id Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__ID_CLASS = 4;

	/**
	 * The feature id for the '<em><b>Exclude Default Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS = 5;

	/**
	 * The feature id for the '<em><b>Exclude Superclass Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS = 6;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__ENTITY_LISTENERS = 7;

	/**
	 * The feature id for the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__PRE_PERSIST = 8;

	/**
	 * The feature id for the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__POST_PERSIST = 9;

	/**
	 * The feature id for the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__PRE_REMOVE = 10;

	/**
	 * The feature id for the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__POST_REMOVE = 11;

	/**
	 * The feature id for the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__PRE_UPDATE = 12;

	/**
	 * The feature id for the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__POST_UPDATE = 13;

	/**
	 * The feature id for the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__POST_LOAD = 14;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__ATTRIBUTES = 15;

	/**
	 * The number of structural features of the '<em>Mapped Superclass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS_FEATURE_COUNT = 16;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity <em>Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity()
	 * @generated
	 */
	public static final int ENTITY = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__CLASS_NAME = 1;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__ACCESS = 2;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__METADATA_COMPLETE = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__DESCRIPTION = 4;

	/**
	 * The feature id for the '<em><b>Tables</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__TABLES = 5;

	/**
	 * The feature id for the '<em><b>Secondary Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__SECONDARY_TABLES = 6;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__PRIMARY_KEY_JOIN_COLUMNS = 7;

	/**
	 * The feature id for the '<em><b>Id Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__ID_CLASS = 8;

	/**
	 * The feature id for the '<em><b>Inheritance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__INHERITANCE = 9;

	/**
	 * The feature id for the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__DISCRIMINATOR_VALUE = 10;

	/**
	 * The feature id for the '<em><b>Discriminator Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__DISCRIMINATOR_COLUMN = 11;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__SEQUENCE_GENERATOR = 12;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__TABLE_GENERATOR = 13;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__NAMED_QUERIES = 14;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__NAMED_NATIVE_QUERIES = 15;

	/**
	 * The feature id for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__SQL_RESULT_SET_MAPPINGS = 16;

	/**
	 * The feature id for the '<em><b>Exclude Default Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__EXCLUDE_DEFAULT_LISTENERS = 17;

	/**
	 * The feature id for the '<em><b>Exclude Superclass Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__EXCLUDE_SUPERCLASS_LISTENERS = 18;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__ENTITY_LISTENERS = 19;

	/**
	 * The feature id for the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__PRE_PERSIST = 20;

	/**
	 * The feature id for the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__POST_PERSIST = 21;

	/**
	 * The feature id for the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__PRE_REMOVE = 22;

	/**
	 * The feature id for the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__POST_REMOVE = 23;

	/**
	 * The feature id for the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__PRE_UPDATE = 24;

	/**
	 * The feature id for the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__POST_UPDATE = 25;

	/**
	 * The feature id for the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__POST_LOAD = 26;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__ATTRIBUTE_OVERRIDES = 27;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__ASSOCIATION_OVERRIDES = 28;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__ATTRIBUTES = 29;

	/**
	 * The number of structural features of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_FEATURE_COUNT = 30;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Embeddable <em>Embeddable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embeddable
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddable()
	 * @generated
	 */
	public static final int EMBEDDABLE = 5;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE__CLASS_NAME = 0;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE__ACCESS = 1;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE__METADATA_COMPLETE = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE__ATTRIBUTES = 4;

	/**
	 * The number of structural features of the '<em>Embeddable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes <em>Attributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes()
	 * @generated
	 */
	public static final int ATTRIBUTES = 6;

	/**
	 * The feature id for the '<em><b>Ids</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__IDS = 0;

	/**
	 * The feature id for the '<em><b>Embedded Ids</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__EMBEDDED_IDS = 1;

	/**
	 * The feature id for the '<em><b>Basics</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__BASICS = 2;

	/**
	 * The feature id for the '<em><b>Versions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__VERSIONS = 3;

	/**
	 * The feature id for the '<em><b>Many To Ones</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__MANY_TO_ONES = 4;

	/**
	 * The feature id for the '<em><b>One To Manies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__ONE_TO_MANIES = 5;

	/**
	 * The feature id for the '<em><b>One To Ones</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__ONE_TO_ONES = 6;

	/**
	 * The feature id for the '<em><b>Many To Manies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__MANY_TO_MANIES = 7;

	/**
	 * The feature id for the '<em><b>Embeddeds</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__EMBEDDEDS = 8;

	/**
	 * The feature id for the '<em><b>Transients</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__TRANSIENTS = 9;

	/**
	 * The number of structural features of the '<em>Attributes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes <em>Embeddable Attributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddableAttributes()
	 * @generated
	 */
	public static final int EMBEDDABLE_ATTRIBUTES = 7;

	/**
	 * The feature id for the '<em><b>Basics</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE_ATTRIBUTES__BASICS = 0;

	/**
	 * The feature id for the '<em><b>Transients</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE_ATTRIBUTES__TRANSIENTS = 1;

	/**
	 * The number of structural features of the '<em>Embeddable Attributes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE_ATTRIBUTES_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Id <em>Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Id
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getId()
	 * @generated
	 */
	public static final int ID = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID__NAME = 0;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID__COLUMN = 1;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID__GENERATED_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID__TEMPORAL = 3;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID__TABLE_GENERATOR = 4;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID__SEQUENCE_GENERATOR = 5;

	/**
	 * The number of structural features of the '<em>Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedId <em>Embedded Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddedId
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddedId()
	 * @generated
	 */
	public static final int EMBEDDED_ID = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_ID__NAME = 0;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_ID__ATTRIBUTE_OVERRIDES = 1;

	/**
	 * The number of structural features of the '<em>Embedded Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_ID_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Basic <em>Basic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Basic
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getBasic()
	 * @generated
	 */
	public static final int BASIC = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__NAME = 0;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__FETCH = 1;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__OPTIONAL = 2;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__COLUMN = 3;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__LOB = 4;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__TEMPORAL = 5;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__ENUMERATED = 6;

	/**
	 * The number of structural features of the '<em>Basic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Version <em>Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Version
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getVersion()
	 * @generated
	 */
	public static final int VERSION = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int VERSION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int VERSION__COLUMN = 1;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int VERSION__TEMPORAL = 2;

	/**
	 * The number of structural features of the '<em>Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int VERSION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOne <em>Many To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOne
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getManyToOne()
	 * @generated
	 */
	public static final int MANY_TO_ONE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__TARGET_ENTITY = 1;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__FETCH = 2;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__OPTIONAL = 3;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__JOIN_COLUMNS = 4;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__JOIN_TABLE = 5;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__CASCADE = 6;

	/**
	 * The number of structural features of the '<em>Many To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany <em>One To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToMany()
	 * @generated
	 */
	public static final int ONE_TO_MANY = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__TARGET_ENTITY = 1;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__FETCH = 2;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__MAPPED_BY = 3;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__ORDER_BY = 4;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__MAP_KEY = 5;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__JOIN_TABLE = 6;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__JOIN_COLUMNS = 7;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__CASCADE = 8;

	/**
	 * The number of structural features of the '<em>One To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne <em>One To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOne()
	 * @generated
	 */
	public static final int ONE_TO_ONE = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__TARGET_ENTITY = 1;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__FETCH = 2;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__OPTIONAL = 3;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__MAPPED_BY = 4;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS = 5;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__JOIN_COLUMNS = 6;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__JOIN_TABLE = 7;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__CASCADE = 8;

	/**
	 * The number of structural features of the '<em>One To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany <em>Many To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToMany
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getManyToMany()
	 * @generated
	 */
	public static final int MANY_TO_MANY = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__TARGET_ENTITY = 1;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__FETCH = 2;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__MAPPED_BY = 3;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__ORDER_BY = 4;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__MAP_KEY = 5;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__JOIN_TABLE = 6;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__CASCADE = 7;

	/**
	 * The number of structural features of the '<em>Many To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Embedded <em>Embedded</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embedded
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbedded()
	 * @generated
	 */
	public static final int EMBEDDED = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED__NAME = 0;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED__ATTRIBUTE_OVERRIDES = 1;

	/**
	 * The number of structural features of the '<em>Embedded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Transient <em>Transient</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Transient
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTransient()
	 * @generated
	 */
	public static final int TRANSIENT = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TRANSIENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Transient</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TRANSIENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.AssociationOverride <em>Association Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.AssociationOverride
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAssociationOverride()
	 * @generated
	 */
	public static final int ASSOCIATION_OVERRIDE = 18;

	/**
	 * The feature id for the '<em><b>Join Column</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ASSOCIATION_OVERRIDE__JOIN_COLUMN = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ASSOCIATION_OVERRIDE__NAME = 1;

	/**
	 * The number of structural features of the '<em>Association Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ASSOCIATION_OVERRIDE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.AttributeOverride <em>Attribute Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.AttributeOverride
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributeOverride()
	 * @generated
	 */
	public static final int ATTRIBUTE_OVERRIDE = 19;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_OVERRIDE__COLUMN = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_OVERRIDE__NAME = 1;

	/**
	 * The number of structural features of the '<em>Attribute Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_OVERRIDE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType <em>Cascade Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getCascadeType()
	 * @generated
	 */
	public static final int CASCADE_TYPE = 20;

	/**
	 * The feature id for the '<em><b>Cascade All</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_ALL = 0;

	/**
	 * The feature id for the '<em><b>Cascade Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_PERSIST = 1;

	/**
	 * The feature id for the '<em><b>Cascade Merge</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_MERGE = 2;

	/**
	 * The feature id for the '<em><b>Cascade Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_REMOVE = 3;

	/**
	 * The feature id for the '<em><b>Cascade Refresh</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_REFRESH = 4;

	/**
	 * The number of structural features of the '<em>Cascade Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Column <em>Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getColumn()
	 * @generated
	 */
	public static final int COLUMN = 21;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__COLUMN_DEFINITION = 0;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__INSERTABLE = 1;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__LENGTH = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__NAME = 3;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__NULLABLE = 4;

	/**
	 * The feature id for the '<em><b>Precision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__PRECISION = 5;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__SCALE = 6;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__TABLE = 7;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__UNIQUE = 8;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__UPDATABLE = 9;

	/**
	 * The number of structural features of the '<em>Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.ColumnResult <em>Column Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.ColumnResult
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getColumnResult()
	 * @generated
	 */
	public static final int COLUMN_RESULT = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_RESULT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Column Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_RESULT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn <em>Discriminator Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getDiscriminatorColumn()
	 * @generated
	 */
	public static final int DISCRIMINATOR_COLUMN = 23;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int DISCRIMINATOR_COLUMN__COLUMN_DEFINITION = 0;

	/**
	 * The feature id for the '<em><b>Discriminator Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int DISCRIMINATOR_COLUMN__LENGTH = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int DISCRIMINATOR_COLUMN__NAME = 3;

	/**
	 * The number of structural features of the '<em>Discriminator Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int DISCRIMINATOR_COLUMN_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListeners <em>Entity Listeners</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListeners
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityListeners()
	 * @generated
	 */
	public static final int ENTITY_LISTENERS = 24;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENERS__ENTITY_LISTENERS = 0;

	/**
	 * The number of structural features of the '<em>Entity Listeners</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENERS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListener <em>Entity Listener</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListener
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityListener()
	 * @generated
	 */
	public static final int ENTITY_LISTENER = 25;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__CLASS_NAME = 0;

	/**
	 * The feature id for the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__PRE_PERSIST = 1;

	/**
	 * The feature id for the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__POST_PERSIST = 2;

	/**
	 * The feature id for the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__PRE_REMOVE = 3;

	/**
	 * The feature id for the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__POST_REMOVE = 4;

	/**
	 * The feature id for the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__PRE_UPDATE = 5;

	/**
	 * The feature id for the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__POST_UPDATE = 6;

	/**
	 * The feature id for the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__POST_LOAD = 7;

	/**
	 * The number of structural features of the '<em>Entity Listener</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityResult <em>Entity Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityResult
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityResult()
	 * @generated
	 */
	public static final int ENTITY_RESULT = 26;

	/**
	 * The feature id for the '<em><b>Discriminator Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_RESULT__DISCRIMINATOR_COLUMN = 0;

	/**
	 * The feature id for the '<em><b>Entity Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_RESULT__ENTITY_CLASS = 1;

	/**
	 * The feature id for the '<em><b>Field Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_RESULT__FIELD_RESULTS = 2;

	/**
	 * The number of structural features of the '<em>Entity Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_RESULT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.FieldResult <em>Field Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.FieldResult
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getFieldResult()
	 * @generated
	 */
	public static final int FIELD_RESULT = 28;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValue <em>Generated Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.GeneratedValue
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGeneratedValue()
	 * @generated
	 */
	public static final int GENERATED_VALUE = 29;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.IdClass <em>Id Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.IdClass
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getIdClass()
	 * @generated
	 */
	public static final int ID_CLASS = 30;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Inheritance <em>Inheritance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Inheritance
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getInheritance()
	 * @generated
	 */
	public static final int INHERITANCE = 31;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn <em>Join Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumn
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getJoinColumn()
	 * @generated
	 */
	public static final int JOIN_COLUMN = 32;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTable <em>Join Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTable
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getJoinTable()
	 * @generated
	 */
	public static final int JOIN_TABLE = 33;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Lob <em>Lob</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Lob
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getLob()
	 * @generated
	 */
	public static final int LOB = 34;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.MapKey <em>Map Key</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.MapKey
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getMapKey()
	 * @generated
	 */
	public static final int MAP_KEY = 35;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EventMethod <em>Event Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EventMethod
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEventMethod()
	 * @generated
	 */
	public static final int EVENT_METHOD = 27;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EVENT_METHOD__METHOD_NAME = 0;

	/**
	 * The number of structural features of the '<em>Event Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EVENT_METHOD_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_RESULT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_RESULT__COLUMN = 1;

	/**
	 * The number of structural features of the '<em>Field Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_RESULT_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int GENERATED_VALUE__GENERATOR = 0;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int GENERATED_VALUE__STRATEGY = 1;

	/**
	 * The number of structural features of the '<em>Generated Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int GENERATED_VALUE_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID_CLASS__CLASS_NAME = 0;

	/**
	 * The number of structural features of the '<em>Id Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID_CLASS_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INHERITANCE__STRATEGY = 0;

	/**
	 * The number of structural features of the '<em>Inheritance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INHERITANCE_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__NAME = 0;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__REFERENCED_COLUMN_NAME = 1;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__UNIQUE = 2;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__NULLABLE = 3;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__INSERTABLE = 4;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__UPDATABLE = 5;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__COLUMN_DEFINITION = 6;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__TABLE = 7;

	/**
	 * The number of structural features of the '<em>Join Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN_FEATURE_COUNT = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE__CATALOG = 1;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE__SCHEMA = 2;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE__JOIN_COLUMNS = 3;

	/**
	 * The feature id for the '<em><b>Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE__INVERSE_JOIN_COLUMNS = 4;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE__UNIQUE_CONSTRAINTS = 5;

	/**
	 * The number of structural features of the '<em>Join Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE_FEATURE_COUNT = 6;

	/**
	 * The number of structural features of the '<em>Lob</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int LOB_FEATURE_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAP_KEY__NAME = 0;

	/**
	 * The number of structural features of the '<em>Map Key</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAP_KEY_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery <em>Named Native Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getNamedNativeQuery()
	 * @generated
	 */
	public static final int NAMED_NATIVE_QUERY = 36;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_NATIVE_QUERY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Result Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_NATIVE_QUERY__RESULT_CLASS = 1;

	/**
	 * The feature id for the '<em><b>Result Set Mapping</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_NATIVE_QUERY__RESULT_SET_MAPPING = 2;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_NATIVE_QUERY__QUERY = 3;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_NATIVE_QUERY__HINTS = 4;

	/**
	 * The number of structural features of the '<em>Named Native Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_NATIVE_QUERY_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.NamedQuery <em>Named Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedQuery
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getNamedQuery()
	 * @generated
	 */
	public static final int NAMED_QUERY = 37;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_QUERY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_QUERY__QUERY = 1;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_QUERY__HINTS = 2;

	/**
	 * The number of structural features of the '<em>Named Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_QUERY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.PostLoad <em>Post Load</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.PostLoad
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPostLoad()
	 * @generated
	 */
	public static final int POST_LOAD = 38;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_LOAD__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Post Load</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_LOAD_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.PostPersist <em>Post Persist</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.PostPersist
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPostPersist()
	 * @generated
	 */
	public static final int POST_PERSIST = 39;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_PERSIST__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Post Persist</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_PERSIST_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.PostRemove <em>Post Remove</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.PostRemove
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPostRemove()
	 * @generated
	 */
	public static final int POST_REMOVE = 40;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_REMOVE__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Post Remove</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_REMOVE_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.PostUpdate <em>Post Update</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.PostUpdate
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPostUpdate()
	 * @generated
	 */
	public static final int POST_UPDATE = 41;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_UPDATE__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Post Update</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_UPDATE_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.PrePersist <em>Pre Persist</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.PrePersist
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPrePersist()
	 * @generated
	 */
	public static final int PRE_PERSIST = 42;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_PERSIST__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Pre Persist</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_PERSIST_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.PreRemove <em>Pre Remove</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.PreRemove
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPreRemove()
	 * @generated
	 */
	public static final int PRE_REMOVE = 43;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_REMOVE__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Pre Remove</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_REMOVE_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.PreUpdate <em>Pre Update</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.PreUpdate
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPreUpdate()
	 * @generated
	 */
	public static final int PRE_UPDATE = 44;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_UPDATE__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Pre Update</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_UPDATE_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.QueryHint <em>Query Hint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.QueryHint
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getQueryHint()
	 * @generated
	 */
	public static final int QUERY_HINT = 46;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping <em>Sql Result Set Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSqlResultSetMapping()
	 * @generated
	 */
	public static final int SQL_RESULT_SET_MAPPING = 49;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn <em>Primary Key Join Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPrimaryKeyJoinColumn()
	 * @generated
	 */
	public static final int PRIMARY_KEY_JOIN_COLUMN = 45;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRIMARY_KEY_JOIN_COLUMN__NAME = 0;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = 1;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION = 2;

	/**
	 * The number of structural features of the '<em>Primary Key Join Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRIMARY_KEY_JOIN_COLUMN_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int QUERY_HINT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int QUERY_HINT__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Query Hint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int QUERY_HINT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.SecondaryTable <em>Secondary Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.SecondaryTable
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSecondaryTable()
	 * @generated
	 */
	public static final int SECONDARY_TABLE = 47;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_TABLE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_TABLE__CATALOG = 1;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_TABLE__SCHEMA = 2;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS = 3;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_TABLE__UNIQUE_CONSTRAINTS = 4;

	/**
	 * The number of structural features of the '<em>Secondary Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_TABLE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator <em>Sequence Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSequenceGenerator()
	 * @generated
	 */
	public static final int SEQUENCE_GENERATOR = 48;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR__NAME = 0;

	/**
	 * The feature id for the '<em><b>Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR__SEQUENCE_NAME = 1;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR__INITIAL_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR__ALLOCATION_SIZE = 3;

	/**
	 * The number of structural features of the '<em>Sequence Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING__NAME = 0;

	/**
	 * The feature id for the '<em><b>Entity Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING__ENTITY_RESULTS = 1;

	/**
	 * The feature id for the '<em><b>Column Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING__COLUMN_RESULTS = 2;

	/**
	 * The number of structural features of the '<em>Sql Result Set Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Table <em>Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Table
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTable()
	 * @generated
	 */
	public static final int TABLE = 50;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE__CATALOG = 1;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE__SCHEMA = 2;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE__UNIQUE_CONSTRAINTS = 3;

	/**
	 * The number of structural features of the '<em>Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator <em>Table Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator()
	 * @generated
	 */
	public static final int TABLE_GENERATOR = 51;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__NAME = 0;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__TABLE = 1;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__CATALOG = 2;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__SCHEMA = 3;

	/**
	 * The feature id for the '<em><b>Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__PK_COLUMN_NAME = 4;

	/**
	 * The feature id for the '<em><b>Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__VALUE_COLUMN_NAME = 5;

	/**
	 * The feature id for the '<em><b>Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__PK_COLUMN_VALUE = 6;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__INITIAL_VALUE = 7;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__ALLOCATION_SIZE = 8;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__UNIQUE_CONSTRAINTS = 9;

	/**
	 * The number of structural features of the '<em>Table Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint <em>Unique Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getUniqueConstraint()
	 * @generated
	 */
	public static final int UNIQUE_CONSTRAINT = 52;

	/**
	 * The feature id for the '<em><b>Column Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int UNIQUE_CONSTRAINT__COLUMN_NAMES = 0;

	/**
	 * The number of structural features of the '<em>Unique Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int UNIQUE_CONSTRAINT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EmptyType <em>Empty Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmptyType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmptyType()
	 * @generated
	 */
	public static final int EMPTY_TYPE = 53;

	/**
	 * The number of structural features of the '<em>Empty Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMPTY_TYPE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.AccessType <em>Access Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.AccessType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAccessType()
	 * @generated
	 */
	public static final int ACCESS_TYPE = 54;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType <em>Discriminator Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getDiscriminatorType()
	 * @generated
	 */
	public static final int DISCRIMINATOR_TYPE = 55;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EnumType <em>Enum Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EnumType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEnumType()
	 * @generated
	 */
	public static final int ENUM_TYPE = 56;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.FetchType <em>Fetch Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.FetchType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getFetchType()
	 * @generated
	 */
	public static final int FETCH_TYPE = 57;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.GenerationType <em>Generation Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.GenerationType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGenerationType()
	 * @generated
	 */
	public static final int GENERATION_TYPE = 58;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.InheritanceType <em>Inheritance Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.InheritanceType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getInheritanceType()
	 * @generated
	 */
	public static final int INHERITANCE_TYPE = 59;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.TemporalType <em>Temporal Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.TemporalType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTemporalType()
	 * @generated
	 */
	public static final int TEMPORAL_TYPE = 60;

	/**
	 * The meta object id for the '<em>Access Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAccessTypeObject()
	 * @generated
	 */
	public static final int ACCESS_TYPE_OBJECT = 61;

	/**
	 * The meta object id for the '<em>Discriminator Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getDiscriminatorTypeObject()
	 * @generated
	 */
	public static final int DISCRIMINATOR_TYPE_OBJECT = 62;

	/**
	 * The meta object id for the '<em>Discriminator Value</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getDiscriminatorValue()
	 * @generated
	 */
	public static final int DISCRIMINATOR_VALUE = 63;

	/**
	 * The meta object id for the '<em>Enumerated</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEnumerated()
	 * @generated
	 */
	public static final int ENUMERATED = 64;

	/**
	 * The meta object id for the '<em>Enum Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEnumTypeObject()
	 * @generated
	 */
	public static final int ENUM_TYPE_OBJECT = 65;

	/**
	 * The meta object id for the '<em>Fetch Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getFetchTypeObject()
	 * @generated
	 */
	public static final int FETCH_TYPE_OBJECT = 66;

	/**
	 * The meta object id for the '<em>Generation Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGenerationTypeObject()
	 * @generated
	 */
	public static final int GENERATION_TYPE_OBJECT = 67;

	/**
	 * The meta object id for the '<em>Inheritance Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getInheritanceTypeObject()
	 * @generated
	 */
	public static final int INHERITANCE_TYPE_OBJECT = 68;

	/**
	 * The meta object id for the '<em>Order By</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOrderBy()
	 * @generated
	 */
	public static final int ORDER_BY = 69;

	/**
	 * The meta object id for the '<em>Temporal</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTemporal()
	 * @generated
	 */
	public static final int TEMPORAL = 70;

	/**
	 * The meta object id for the '<em>Temporal Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTemporalTypeObject()
	 * @generated
	 */
	public static final int TEMPORAL_TYPE_OBJECT = 71;

	/**
	 * The meta object id for the '<em>Version Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getVersionType()
	 * @generated
	 */
	public static final int VERSION_TYPE = 72;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityMappingsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass persistenceUnitMetadataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass persistenceUnitDefaultsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mappedSuperclassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass embeddableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass embeddableAttributesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass idEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass embeddedIdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass basicEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass manyToOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass oneToManyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass oneToOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass manyToManyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass embeddedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transientEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass associationOverrideEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeOverrideEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cascadeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass columnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass columnResultEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass discriminatorColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityListenersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityListenerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityResultEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fieldResultEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass generatedValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass idClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inheritanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass joinColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass joinTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lobEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapKeyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventMethodEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedNativeQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass postLoadEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass postPersistEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass postRemoveEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass postUpdateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass prePersistEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass preRemoveEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass preUpdateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass queryHintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sqlResultSetMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass primaryKeyJoinColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass secondaryTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sequenceGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tableGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uniqueConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass emptyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum accessTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum discriminatorTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum enumTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum fetchTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum generationTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum inheritanceTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum temporalTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType accessTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType discriminatorTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType discriminatorValueEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType enumeratedEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType enumTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType fetchTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType generationTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType inheritanceTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType orderByEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType temporalEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType temporalTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType versionTypeEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private OrmPackage()
	{
		super(eNS_URI, OrmFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static OrmPackage init()
	{
		if (isInited) return (OrmPackage)EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI);

		// Obtain or create and register package
		OrmPackage theOrmPackage = (OrmPackage)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new OrmPackage());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		PersistencePackage thePersistencePackage = (PersistencePackage)(EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);

		// Create package meta-data objects
		theOrmPackage.createPackageContents();
		thePersistencePackage.createPackageContents();

		// Initialize created meta-data
		theOrmPackage.initializePackageContents();
		thePersistencePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theOrmPackage.freeze();

		return theOrmPackage;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings <em>Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Mappings</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings
	 * @generated
	 */
	public EClass getEntityMappings()
	{
		return entityMappingsEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getVersion()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EAttribute getEntityMappings_Version()
	{
		return (EAttribute)entityMappingsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getDescription()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EAttribute getEntityMappings_Description()
	{
		return (EAttribute)entityMappingsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getPersistenceUnitMetadata <em>Persistence Unit Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Persistence Unit Metadata</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getPersistenceUnitMetadata()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EReference getEntityMappings_PersistenceUnitMetadata()
	{
		return (EReference)entityMappingsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getPackage()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EAttribute getEntityMappings_Package()
	{
		return (EAttribute)entityMappingsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getSchema()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EAttribute getEntityMappings_Schema()
	{
		return (EAttribute)entityMappingsEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getCatalog()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EAttribute getEntityMappings_Catalog()
	{
		return (EAttribute)entityMappingsEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getAccess <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getAccess()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EAttribute getEntityMappings_Access()
	{
		return (EAttribute)entityMappingsEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getSequenceGenerators <em>Sequence Generators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sequence Generators</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getSequenceGenerators()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EReference getEntityMappings_SequenceGenerators()
	{
		return (EReference)entityMappingsEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getTableGenerators <em>Table Generators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Table Generators</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getTableGenerators()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EReference getEntityMappings_TableGenerators()
	{
		return (EReference)entityMappingsEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getNamedQueries <em>Named Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Queries</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getNamedQueries()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EReference getEntityMappings_NamedQueries()
	{
		return (EReference)entityMappingsEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getNamedNativeQueries <em>Named Native Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Native Queries</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getNamedNativeQueries()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EReference getEntityMappings_NamedNativeQueries()
	{
		return (EReference)entityMappingsEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getSqlResultSetMappings <em>Sql Result Set Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sql Result Set Mappings</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getSqlResultSetMappings()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EReference getEntityMappings_SqlResultSetMappings()
	{
		return (EReference)entityMappingsEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getMappedSuperclasses <em>Mapped Superclasses</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mapped Superclasses</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getMappedSuperclasses()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EReference getEntityMappings_MappedSuperclasses()
	{
		return (EReference)entityMappingsEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getEntities <em>Entities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entities</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getEntities()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EReference getEntityMappings_Entities()
	{
		return (EReference)entityMappingsEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getEmbeddables <em>Embeddables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Embeddables</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getEmbeddables()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EReference getEntityMappings_Embeddables()
	{
		return (EReference)entityMappingsEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata <em>Persistence Unit Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Persistence Unit Metadata</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata
	 * @generated
	 */
	public EClass getPersistenceUnitMetadata()
	{
		return persistenceUnitMetadataEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata#getXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Mapping Metadata Complete</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata#getXmlMappingMetadataComplete()
	 * @see #getPersistenceUnitMetadata()
	 * @generated
	 */
	public EReference getPersistenceUnitMetadata_XmlMappingMetadataComplete()
	{
		return (EReference)persistenceUnitMetadataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata#getPersistenceUnitDefaults <em>Persistence Unit Defaults</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Persistence Unit Defaults</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata#getPersistenceUnitDefaults()
	 * @see #getPersistenceUnitMetadata()
	 * @generated
	 */
	public EReference getPersistenceUnitMetadata_PersistenceUnitDefaults()
	{
		return (EReference)persistenceUnitMetadataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults <em>Persistence Unit Defaults</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Persistence Unit Defaults</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults
	 * @generated
	 */
	public EClass getPersistenceUnitDefaults()
	{
		return persistenceUnitDefaultsEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getSchema()
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaults_Schema()
	{
		return (EAttribute)persistenceUnitDefaultsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getCatalog()
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaults_Catalog()
	{
		return (EAttribute)persistenceUnitDefaultsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getAccess <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getAccess()
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaults_Access()
	{
		return (EAttribute)persistenceUnitDefaultsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getCascadePersist <em>Cascade Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cascade Persist</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getCascadePersist()
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 */
	public EReference getPersistenceUnitDefaults_CascadePersist()
	{
		return (EReference)persistenceUnitDefaultsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getEntityListeners <em>Entity Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entity Listeners</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getEntityListeners()
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 */
	public EReference getPersistenceUnitDefaults_EntityListeners()
	{
		return (EReference)persistenceUnitDefaultsEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass <em>Mapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapped Superclass</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass
	 * @generated
	 */
	public EClass getMappedSuperclass()
	{
		return mappedSuperclassEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getClassName()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EAttribute getMappedSuperclass_ClassName()
	{
		return (EAttribute)mappedSuperclassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getAccess <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getAccess()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EAttribute getMappedSuperclass_Access()
	{
		return (EAttribute)mappedSuperclassEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#isMetadataComplete <em>Metadata Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metadata Complete</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#isMetadataComplete()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EAttribute getMappedSuperclass_MetadataComplete()
	{
		return (EAttribute)mappedSuperclassEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getDescription()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EAttribute getMappedSuperclass_Description()
	{
		return (EAttribute)mappedSuperclassEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getIdClass <em>Id Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Id Class</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getIdClass()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EReference getMappedSuperclass_IdClass()
	{
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getExcludeDefaultListeners <em>Exclude Default Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exclude Default Listeners</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getExcludeDefaultListeners()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EReference getMappedSuperclass_ExcludeDefaultListeners()
	{
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getExcludeSuperclassListeners <em>Exclude Superclass Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exclude Superclass Listeners</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getExcludeSuperclassListeners()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EReference getMappedSuperclass_ExcludeSuperclassListeners()
	{
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getEntityListeners <em>Entity Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entity Listeners</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getEntityListeners()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EReference getMappedSuperclass_EntityListeners()
	{
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPrePersist <em>Pre Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Persist</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPrePersist()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EReference getMappedSuperclass_PrePersist()
	{
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPostPersist <em>Post Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Persist</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPostPersist()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EReference getMappedSuperclass_PostPersist()
	{
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPreRemove <em>Pre Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Remove</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPreRemove()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EReference getMappedSuperclass_PreRemove()
	{
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPostRemove <em>Post Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Remove</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPostRemove()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EReference getMappedSuperclass_PostRemove()
	{
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPreUpdate <em>Pre Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Update</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPreUpdate()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EReference getMappedSuperclass_PreUpdate()
	{
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPostUpdate <em>Post Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Update</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPostUpdate()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EReference getMappedSuperclass_PostUpdate()
	{
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPostLoad <em>Post Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Load</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getPostLoad()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EReference getMappedSuperclass_PostLoad()
	{
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attributes</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#getAttributes()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EReference getMappedSuperclass_Attributes()
	{
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity
	 * @generated
	 */
	public EClass getEntity()
	{
		return entityEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getName()
	 * @see #getEntity()
	 * @generated
	 */
	public EAttribute getEntity_Name()
	{
		return (EAttribute)entityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getClassName()
	 * @see #getEntity()
	 * @generated
	 */
	public EAttribute getEntity_ClassName()
	{
		return (EAttribute)entityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getAccess <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getAccess()
	 * @see #getEntity()
	 * @generated
	 */
	public EAttribute getEntity_Access()
	{
		return (EAttribute)entityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#isMetadataComplete <em>Metadata Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metadata Complete</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#isMetadataComplete()
	 * @see #getEntity()
	 * @generated
	 */
	public EAttribute getEntity_MetadataComplete()
	{
		return (EAttribute)entityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getDescription()
	 * @see #getEntity()
	 * @generated
	 */
	public EAttribute getEntity_Description()
	{
		return (EAttribute)entityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getTables <em>Tables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tables</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getTables()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_Tables()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getSecondaryTables <em>Secondary Tables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Secondary Tables</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getSecondaryTables()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_SecondaryTables()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Primary Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getPrimaryKeyJoinColumns()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_PrimaryKeyJoinColumns()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getIdClass <em>Id Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Id Class</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getIdClass()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_IdClass()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getInheritance <em>Inheritance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inheritance</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getInheritance()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_Inheritance()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getDiscriminatorValue <em>Discriminator Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator Value</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getDiscriminatorValue()
	 * @see #getEntity()
	 * @generated
	 */
	public EAttribute getEntity_DiscriminatorValue()
	{
		return (EAttribute)entityEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getDiscriminatorColumn <em>Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Discriminator Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getDiscriminatorColumn()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_DiscriminatorColumn()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getSequenceGenerator <em>Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sequence Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getSequenceGenerator()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_SequenceGenerator()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getTableGenerator <em>Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Table Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getTableGenerator()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_TableGenerator()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getNamedQueries <em>Named Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Queries</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getNamedQueries()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_NamedQueries()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getNamedNativeQueries <em>Named Native Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Native Queries</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getNamedNativeQueries()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_NamedNativeQueries()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getSqlResultSetMappings <em>Sql Result Set Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sql Result Set Mappings</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getSqlResultSetMappings()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_SqlResultSetMappings()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getExcludeDefaultListeners <em>Exclude Default Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exclude Default Listeners</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getExcludeDefaultListeners()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_ExcludeDefaultListeners()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getExcludeSuperclassListeners <em>Exclude Superclass Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exclude Superclass Listeners</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getExcludeSuperclassListeners()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_ExcludeSuperclassListeners()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getEntityListeners <em>Entity Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entity Listeners</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getEntityListeners()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_EntityListeners()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPrePersist <em>Pre Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Persist</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getPrePersist()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_PrePersist()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPostPersist <em>Post Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Persist</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getPostPersist()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_PostPersist()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPreRemove <em>Pre Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Remove</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getPreRemove()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_PreRemove()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPostRemove <em>Post Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Remove</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getPostRemove()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_PostRemove()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPreUpdate <em>Pre Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Update</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getPreUpdate()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_PreUpdate()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(24);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPostUpdate <em>Post Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Update</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getPostUpdate()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_PostUpdate()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(25);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPostLoad <em>Post Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Load</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getPostLoad()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_PostLoad()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(26);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getAttributeOverrides <em>Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getAttributeOverrides()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_AttributeOverrides()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(27);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getAssociationOverrides <em>Association Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Association Overrides</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getAssociationOverrides()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_AssociationOverrides()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(28);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attributes</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getAttributes()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_Attributes()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(29);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Embeddable <em>Embeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Embeddable</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embeddable
	 * @generated
	 */
	public EClass getEmbeddable()
	{
		return embeddableEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Embeddable#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embeddable#getClassName()
	 * @see #getEmbeddable()
	 * @generated
	 */
	public EAttribute getEmbeddable_ClassName()
	{
		return (EAttribute)embeddableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Embeddable#getAccess <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embeddable#getAccess()
	 * @see #getEmbeddable()
	 * @generated
	 */
	public EAttribute getEmbeddable_Access()
	{
		return (EAttribute)embeddableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Embeddable#isMetadataComplete <em>Metadata Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metadata Complete</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embeddable#isMetadataComplete()
	 * @see #getEmbeddable()
	 * @generated
	 */
	public EAttribute getEmbeddable_MetadataComplete()
	{
		return (EAttribute)embeddableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Embeddable#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embeddable#getDescription()
	 * @see #getEmbeddable()
	 * @generated
	 */
	public EAttribute getEmbeddable_Description()
	{
		return (EAttribute)embeddableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Embeddable#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attributes</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embeddable#getAttributes()
	 * @see #getEmbeddable()
	 * @generated
	 */
	public EReference getEmbeddable_Attributes()
	{
		return (EReference)embeddableEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attributes</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes
	 * @generated
	 */
	public EClass getAttributes()
	{
		return attributesEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getIds <em>Ids</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ids</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes#getIds()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_Ids()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getEmbeddedIds <em>Embedded Ids</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Embedded Ids</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes#getEmbeddedIds()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_EmbeddedIds()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getBasics <em>Basics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Basics</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes#getBasics()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_Basics()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getVersions <em>Versions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Versions</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes#getVersions()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_Versions()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getManyToOnes <em>Many To Ones</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Many To Ones</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes#getManyToOnes()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_ManyToOnes()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getOneToManies <em>One To Manies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>One To Manies</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes#getOneToManies()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_OneToManies()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getOneToOnes <em>One To Ones</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>One To Ones</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes#getOneToOnes()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_OneToOnes()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getManyToManies <em>Many To Manies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Many To Manies</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes#getManyToManies()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_ManyToManies()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getEmbeddeds <em>Embeddeds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Embeddeds</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes#getEmbeddeds()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_Embeddeds()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getTransients <em>Transients</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transients</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes#getTransients()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_Transients()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes <em>Embeddable Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Embeddable Attributes</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes
	 * @generated
	 */
	public EClass getEmbeddableAttributes()
	{
		return embeddableAttributesEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes#getBasics <em>Basics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Basics</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes#getBasics()
	 * @see #getEmbeddableAttributes()
	 * @generated
	 */
	public EReference getEmbeddableAttributes_Basics()
	{
		return (EReference)embeddableAttributesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes#getTransients <em>Transients</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transients</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes#getTransients()
	 * @see #getEmbeddableAttributes()
	 * @generated
	 */
	public EReference getEmbeddableAttributes_Transients()
	{
		return (EReference)embeddableAttributesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Id <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Id</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Id
	 * @generated
	 */
	public EClass getId()
	{
		return idEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Id#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Id#getName()
	 * @see #getId()
	 * @generated
	 */
	public EAttribute getId_Name()
	{
		return (EAttribute)idEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Id#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Id#getColumn()
	 * @see #getId()
	 * @generated
	 */
	public EReference getId_Column()
	{
		return (EReference)idEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Id#getGeneratedValue <em>Generated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Generated Value</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Id#getGeneratedValue()
	 * @see #getId()
	 * @generated
	 */
	public EReference getId_GeneratedValue()
	{
		return (EReference)idEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Id#getTemporal <em>Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temporal</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Id#getTemporal()
	 * @see #getId()
	 * @generated
	 */
	public EAttribute getId_Temporal()
	{
		return (EAttribute)idEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Id#getTableGenerator <em>Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Table Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Id#getTableGenerator()
	 * @see #getId()
	 * @generated
	 */
	public EReference getId_TableGenerator()
	{
		return (EReference)idEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Id#getSequenceGenerator <em>Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sequence Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Id#getSequenceGenerator()
	 * @see #getId()
	 * @generated
	 */
	public EReference getId_SequenceGenerator()
	{
		return (EReference)idEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedId <em>Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Embedded Id</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddedId
	 * @generated
	 */
	public EClass getEmbeddedId()
	{
		return embeddedIdEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedId#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddedId#getName()
	 * @see #getEmbeddedId()
	 * @generated
	 */
	public EAttribute getEmbeddedId_Name()
	{
		return (EAttribute)embeddedIdEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedId#getAttributeOverrides <em>Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddedId#getAttributeOverrides()
	 * @see #getEmbeddedId()
	 * @generated
	 */
	public EReference getEmbeddedId_AttributeOverrides()
	{
		return (EReference)embeddedIdEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Basic <em>Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Basic
	 * @generated
	 */
	public EClass getBasic()
	{
		return basicEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Basic#getName()
	 * @see #getBasic()
	 * @generated
	 */
	public EAttribute getBasic_Name()
	{
		return (EAttribute)basicEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Basic#getFetch()
	 * @see #getBasic()
	 * @generated
	 */
	public EAttribute getBasic_Fetch()
	{
		return (EAttribute)basicEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#isOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Basic#isOptional()
	 * @see #getBasic()
	 * @generated
	 */
	public EAttribute getBasic_Optional()
	{
		return (EAttribute)basicEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Basic#getColumn()
	 * @see #getBasic()
	 * @generated
	 */
	public EReference getBasic_Column()
	{
		return (EReference)basicEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getLob <em>Lob</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lob</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Basic#getLob()
	 * @see #getBasic()
	 * @generated
	 */
	public EReference getBasic_Lob()
	{
		return (EReference)basicEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getTemporal <em>Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temporal</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Basic#getTemporal()
	 * @see #getBasic()
	 * @generated
	 */
	public EAttribute getBasic_Temporal()
	{
		return (EAttribute)basicEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getEnumerated <em>Enumerated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enumerated</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Basic#getEnumerated()
	 * @see #getBasic()
	 * @generated
	 */
	public EAttribute getBasic_Enumerated()
	{
		return (EAttribute)basicEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Version <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Version
	 * @generated
	 */
	public EClass getVersion()
	{
		return versionEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Version#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Version#getName()
	 * @see #getVersion()
	 * @generated
	 */
	public EAttribute getVersion_Name()
	{
		return (EAttribute)versionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Version#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Version#getColumn()
	 * @see #getVersion()
	 * @generated
	 */
	public EReference getVersion_Column()
	{
		return (EReference)versionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Version#getTemporal <em>Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temporal</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Version#getTemporal()
	 * @see #getVersion()
	 * @generated
	 */
	public EAttribute getVersion_Temporal()
	{
		return (EAttribute)versionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOne <em>Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Many To One</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOne
	 * @generated
	 */
	public EClass getManyToOne()
	{
		return manyToOneEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOne#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOne#getName()
	 * @see #getManyToOne()
	 * @generated
	 */
	public EAttribute getManyToOne_Name()
	{
		return (EAttribute)manyToOneEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOne#getTargetEntity <em>Target Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Entity</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOne#getTargetEntity()
	 * @see #getManyToOne()
	 * @generated
	 */
	public EAttribute getManyToOne_TargetEntity()
	{
		return (EAttribute)manyToOneEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOne#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOne#getFetch()
	 * @see #getManyToOne()
	 * @generated
	 */
	public EAttribute getManyToOne_Fetch()
	{
		return (EAttribute)manyToOneEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOne#isOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOne#isOptional()
	 * @see #getManyToOne()
	 * @generated
	 */
	public EAttribute getManyToOne_Optional()
	{
		return (EAttribute)manyToOneEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOne#getJoinColumns <em>Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOne#getJoinColumns()
	 * @see #getManyToOne()
	 * @generated
	 */
	public EReference getManyToOne_JoinColumns()
	{
		return (EReference)manyToOneEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOne#getJoinTable <em>Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOne#getJoinTable()
	 * @see #getManyToOne()
	 * @generated
	 */
	public EReference getManyToOne_JoinTable()
	{
		return (EReference)manyToOneEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOne#getCascade <em>Cascade</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cascade</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOne#getCascade()
	 * @see #getManyToOne()
	 * @generated
	 */
	public EReference getManyToOne_Cascade()
	{
		return (EReference)manyToOneEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany <em>One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>One To Many</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany
	 * @generated
	 */
	public EClass getOneToMany()
	{
		return oneToManyEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany#getName()
	 * @see #getOneToMany()
	 * @generated
	 */
	public EAttribute getOneToMany_Name()
	{
		return (EAttribute)oneToManyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getTargetEntity <em>Target Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Entity</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany#getTargetEntity()
	 * @see #getOneToMany()
	 * @generated
	 */
	public EAttribute getOneToMany_TargetEntity()
	{
		return (EAttribute)oneToManyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany#getFetch()
	 * @see #getOneToMany()
	 * @generated
	 */
	public EAttribute getOneToMany_Fetch()
	{
		return (EAttribute)oneToManyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getMappedBy <em>Mapped By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mapped By</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany#getMappedBy()
	 * @see #getOneToMany()
	 * @generated
	 */
	public EAttribute getOneToMany_MappedBy()
	{
		return (EAttribute)oneToManyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getOrderBy <em>Order By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Order By</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany#getOrderBy()
	 * @see #getOneToMany()
	 * @generated
	 */
	public EAttribute getOneToMany_OrderBy()
	{
		return (EAttribute)oneToManyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getMapKey <em>Map Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany#getMapKey()
	 * @see #getOneToMany()
	 * @generated
	 */
	public EReference getOneToMany_MapKey()
	{
		return (EReference)oneToManyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getJoinTable <em>Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany#getJoinTable()
	 * @see #getOneToMany()
	 * @generated
	 */
	public EReference getOneToMany_JoinTable()
	{
		return (EReference)oneToManyEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getJoinColumns <em>Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany#getJoinColumns()
	 * @see #getOneToMany()
	 * @generated
	 */
	public EReference getOneToMany_JoinColumns()
	{
		return (EReference)oneToManyEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getCascade <em>Cascade</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cascade</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany#getCascade()
	 * @see #getOneToMany()
	 * @generated
	 */
	public EReference getOneToMany_Cascade()
	{
		return (EReference)oneToManyEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne <em>One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>One To One</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne
	 * @generated
	 */
	public EClass getOneToOne()
	{
		return oneToOneEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne#getName()
	 * @see #getOneToOne()
	 * @generated
	 */
	public EAttribute getOneToOne_Name()
	{
		return (EAttribute)oneToOneEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getTargetEntity <em>Target Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Entity</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne#getTargetEntity()
	 * @see #getOneToOne()
	 * @generated
	 */
	public EAttribute getOneToOne_TargetEntity()
	{
		return (EAttribute)oneToOneEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne#getFetch()
	 * @see #getOneToOne()
	 * @generated
	 */
	public EAttribute getOneToOne_Fetch()
	{
		return (EAttribute)oneToOneEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#isOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne#isOptional()
	 * @see #getOneToOne()
	 * @generated
	 */
	public EAttribute getOneToOne_Optional()
	{
		return (EAttribute)oneToOneEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getMappedBy <em>Mapped By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mapped By</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne#getMappedBy()
	 * @see #getOneToOne()
	 * @generated
	 */
	public EAttribute getOneToOne_MappedBy()
	{
		return (EAttribute)oneToOneEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Primary Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne#getPrimaryKeyJoinColumns()
	 * @see #getOneToOne()
	 * @generated
	 */
	public EReference getOneToOne_PrimaryKeyJoinColumns()
	{
		return (EReference)oneToOneEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getJoinColumns <em>Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne#getJoinColumns()
	 * @see #getOneToOne()
	 * @generated
	 */
	public EReference getOneToOne_JoinColumns()
	{
		return (EReference)oneToOneEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getJoinTable <em>Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne#getJoinTable()
	 * @see #getOneToOne()
	 * @generated
	 */
	public EReference getOneToOne_JoinTable()
	{
		return (EReference)oneToOneEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getCascade <em>Cascade</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cascade</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne#getCascade()
	 * @see #getOneToOne()
	 * @generated
	 */
	public EReference getOneToOne_Cascade()
	{
		return (EReference)oneToOneEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany <em>Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Many To Many</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToMany
	 * @generated
	 */
	public EClass getManyToMany()
	{
		return manyToManyEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getName()
	 * @see #getManyToMany()
	 * @generated
	 */
	public EAttribute getManyToMany_Name()
	{
		return (EAttribute)manyToManyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getTargetEntity <em>Target Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Entity</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getTargetEntity()
	 * @see #getManyToMany()
	 * @generated
	 */
	public EAttribute getManyToMany_TargetEntity()
	{
		return (EAttribute)manyToManyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getFetch()
	 * @see #getManyToMany()
	 * @generated
	 */
	public EAttribute getManyToMany_Fetch()
	{
		return (EAttribute)manyToManyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getMappedBy <em>Mapped By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mapped By</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getMappedBy()
	 * @see #getManyToMany()
	 * @generated
	 */
	public EAttribute getManyToMany_MappedBy()
	{
		return (EAttribute)manyToManyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getOrderBy <em>Order By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Order By</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getOrderBy()
	 * @see #getManyToMany()
	 * @generated
	 */
	public EAttribute getManyToMany_OrderBy()
	{
		return (EAttribute)manyToManyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getMapKey <em>Map Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getMapKey()
	 * @see #getManyToMany()
	 * @generated
	 */
	public EReference getManyToMany_MapKey()
	{
		return (EReference)manyToManyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getJoinTable <em>Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getJoinTable()
	 * @see #getManyToMany()
	 * @generated
	 */
	public EReference getManyToMany_JoinTable()
	{
		return (EReference)manyToManyEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getCascade <em>Cascade</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cascade</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToMany#getCascade()
	 * @see #getManyToMany()
	 * @generated
	 */
	public EReference getManyToMany_Cascade()
	{
		return (EReference)manyToManyEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Embedded <em>Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Embedded</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embedded
	 * @generated
	 */
	public EClass getEmbedded()
	{
		return embeddedEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Embedded#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embedded#getName()
	 * @see #getEmbedded()
	 * @generated
	 */
	public EAttribute getEmbedded_Name()
	{
		return (EAttribute)embeddedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Embedded#getAttributeOverrides <em>Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embedded#getAttributeOverrides()
	 * @see #getEmbedded()
	 * @generated
	 */
	public EReference getEmbedded_AttributeOverrides()
	{
		return (EReference)embeddedEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Transient <em>Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transient</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Transient
	 * @generated
	 */
	public EClass getTransient()
	{
		return transientEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Transient#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Transient#getName()
	 * @see #getTransient()
	 * @generated
	 */
	public EAttribute getTransient_Name()
	{
		return (EAttribute)transientEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.AssociationOverride <em>Association Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Association Override</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AssociationOverride
	 * @generated
	 */
	public EClass getAssociationOverride()
	{
		return associationOverrideEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.AssociationOverride#getJoinColumn <em>Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AssociationOverride#getJoinColumn()
	 * @see #getAssociationOverride()
	 * @generated
	 */
	public EReference getAssociationOverride_JoinColumn()
	{
		return (EReference)associationOverrideEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.AssociationOverride#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AssociationOverride#getName()
	 * @see #getAssociationOverride()
	 * @generated
	 */
	public EAttribute getAssociationOverride_Name()
	{
		return (EAttribute)associationOverrideEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.AttributeOverride <em>Attribute Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Override</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AttributeOverride
	 * @generated
	 */
	public EClass getAttributeOverride()
	{
		return attributeOverrideEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.AttributeOverride#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AttributeOverride#getColumn()
	 * @see #getAttributeOverride()
	 * @generated
	 */
	public EReference getAttributeOverride_Column()
	{
		return (EReference)attributeOverrideEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.AttributeOverride#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AttributeOverride#getName()
	 * @see #getAttributeOverride()
	 * @generated
	 */
	public EAttribute getAttributeOverride_Name()
	{
		return (EAttribute)attributeOverrideEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType <em>Cascade Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cascade Type</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType
	 * @generated
	 */
	public EClass getCascadeType()
	{
		return cascadeTypeEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeAll <em>Cascade All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cascade All</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeAll()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EReference getCascadeType_CascadeAll()
	{
		return (EReference)cascadeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadePersist <em>Cascade Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cascade Persist</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadePersist()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EReference getCascadeType_CascadePersist()
	{
		return (EReference)cascadeTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeMerge <em>Cascade Merge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cascade Merge</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeMerge()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EReference getCascadeType_CascadeMerge()
	{
		return (EReference)cascadeTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeRemove <em>Cascade Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cascade Remove</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeRemove()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EReference getCascadeType_CascadeRemove()
	{
		return (EReference)cascadeTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeRefresh <em>Cascade Refresh</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cascade Refresh</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType#getCascadeRefresh()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EReference getCascadeType_CascadeRefresh()
	{
		return (EReference)cascadeTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Column <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column
	 * @generated
	 */
	public EClass getColumn()
	{
		return columnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Column#getColumnDefinition <em>Column Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column Definition</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column#getColumnDefinition()
	 * @see #getColumn()
	 * @generated
	 */
	public EAttribute getColumn_ColumnDefinition()
	{
		return (EAttribute)columnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Column#isInsertable <em>Insertable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Insertable</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column#isInsertable()
	 * @see #getColumn()
	 * @generated
	 */
	public EAttribute getColumn_Insertable()
	{
		return (EAttribute)columnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Column#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column#getLength()
	 * @see #getColumn()
	 * @generated
	 */
	public EAttribute getColumn_Length()
	{
		return (EAttribute)columnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Column#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column#getName()
	 * @see #getColumn()
	 * @generated
	 */
	public EAttribute getColumn_Name()
	{
		return (EAttribute)columnEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Column#isNullable <em>Nullable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nullable</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column#isNullable()
	 * @see #getColumn()
	 * @generated
	 */
	public EAttribute getColumn_Nullable()
	{
		return (EAttribute)columnEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Column#getPrecision <em>Precision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Precision</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column#getPrecision()
	 * @see #getColumn()
	 * @generated
	 */
	public EAttribute getColumn_Precision()
	{
		return (EAttribute)columnEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Column#getScale <em>Scale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scale</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column#getScale()
	 * @see #getColumn()
	 * @generated
	 */
	public EAttribute getColumn_Scale()
	{
		return (EAttribute)columnEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Column#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column#getTable()
	 * @see #getColumn()
	 * @generated
	 */
	public EAttribute getColumn_Table()
	{
		return (EAttribute)columnEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Column#isUnique <em>Unique</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column#isUnique()
	 * @see #getColumn()
	 * @generated
	 */
	public EAttribute getColumn_Unique()
	{
		return (EAttribute)columnEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Column#isUpdatable <em>Updatable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Updatable</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column#isUpdatable()
	 * @see #getColumn()
	 * @generated
	 */
	public EAttribute getColumn_Updatable()
	{
		return (EAttribute)columnEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.ColumnResult <em>Column Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Column Result</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ColumnResult
	 * @generated
	 */
	public EClass getColumnResult()
	{
		return columnResultEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.ColumnResult#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ColumnResult#getName()
	 * @see #getColumnResult()
	 * @generated
	 */
	public EAttribute getColumnResult_Name()
	{
		return (EAttribute)columnResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn <em>Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Discriminator Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn
	 * @generated
	 */
	public EClass getDiscriminatorColumn()
	{
		return discriminatorColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn#getColumnDefinition <em>Column Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column Definition</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn#getColumnDefinition()
	 * @see #getDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getDiscriminatorColumn_ColumnDefinition()
	{
		return (EAttribute)discriminatorColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn#getDiscriminatorType <em>Discriminator Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator Type</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn#getDiscriminatorType()
	 * @see #getDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getDiscriminatorColumn_DiscriminatorType()
	{
		return (EAttribute)discriminatorColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn#getLength()
	 * @see #getDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getDiscriminatorColumn_Length()
	{
		return (EAttribute)discriminatorColumnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn#getName()
	 * @see #getDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getDiscriminatorColumn_Name()
	{
		return (EAttribute)discriminatorColumnEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListeners <em>Entity Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Listeners</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListeners
	 * @generated
	 */
	public EClass getEntityListeners()
	{
		return entityListenersEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListeners#getEntityListeners <em>Entity Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entity Listeners</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListeners#getEntityListeners()
	 * @see #getEntityListeners()
	 * @generated
	 */
	public EReference getEntityListeners_EntityListeners()
	{
		return (EReference)entityListenersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListener <em>Entity Listener</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Listener</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListener
	 * @generated
	 */
	public EClass getEntityListener()
	{
		return entityListenerEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListener#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListener#getClassName()
	 * @see #getEntityListener()
	 * @generated
	 */
	public EAttribute getEntityListener_ClassName()
	{
		return (EAttribute)entityListenerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPrePersist <em>Pre Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Persist</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPrePersist()
	 * @see #getEntityListener()
	 * @generated
	 */
	public EReference getEntityListener_PrePersist()
	{
		return (EReference)entityListenerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPostPersist <em>Post Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Persist</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPostPersist()
	 * @see #getEntityListener()
	 * @generated
	 */
	public EReference getEntityListener_PostPersist()
	{
		return (EReference)entityListenerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPreRemove <em>Pre Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Remove</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPreRemove()
	 * @see #getEntityListener()
	 * @generated
	 */
	public EReference getEntityListener_PreRemove()
	{
		return (EReference)entityListenerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPostRemove <em>Post Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Remove</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPostRemove()
	 * @see #getEntityListener()
	 * @generated
	 */
	public EReference getEntityListener_PostRemove()
	{
		return (EReference)entityListenerEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPreUpdate <em>Pre Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Update</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPreUpdate()
	 * @see #getEntityListener()
	 * @generated
	 */
	public EReference getEntityListener_PreUpdate()
	{
		return (EReference)entityListenerEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPostUpdate <em>Post Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Update</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPostUpdate()
	 * @see #getEntityListener()
	 * @generated
	 */
	public EReference getEntityListener_PostUpdate()
	{
		return (EReference)entityListenerEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPostLoad <em>Post Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Load</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListener#getPostLoad()
	 * @see #getEntityListener()
	 * @generated
	 */
	public EReference getEntityListener_PostLoad()
	{
		return (EReference)entityListenerEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.EntityResult <em>Entity Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Result</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityResult
	 * @generated
	 */
	public EClass getEntityResult()
	{
		return entityResultEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.EntityResult#getDiscriminatorColumn <em>Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityResult#getDiscriminatorColumn()
	 * @see #getEntityResult()
	 * @generated
	 */
	public EAttribute getEntityResult_DiscriminatorColumn()
	{
		return (EAttribute)entityResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.EntityResult#getEntityClass <em>Entity Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entity Class</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityResult#getEntityClass()
	 * @see #getEntityResult()
	 * @generated
	 */
	public EAttribute getEntityResult_EntityClass()
	{
		return (EAttribute)entityResultEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.EntityResult#getFieldResults <em>Field Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Field Results</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityResult#getFieldResults()
	 * @see #getEntityResult()
	 * @generated
	 */
	public EReference getEntityResult_FieldResults()
	{
		return (EReference)entityResultEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.FieldResult <em>Field Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Field Result</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.FieldResult
	 * @generated
	 */
	public EClass getFieldResult()
	{
		return fieldResultEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.FieldResult#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.FieldResult#getName()
	 * @see #getFieldResult()
	 * @generated
	 */
	public EAttribute getFieldResult_Name()
	{
		return (EAttribute)fieldResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.FieldResult#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.FieldResult#getColumn()
	 * @see #getFieldResult()
	 * @generated
	 */
	public EAttribute getFieldResult_Column()
	{
		return (EAttribute)fieldResultEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValue <em>Generated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generated Value</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.GeneratedValue
	 * @generated
	 */
	public EClass getGeneratedValue()
	{
		return generatedValueEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValue#getGenerator <em>Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.GeneratedValue#getGenerator()
	 * @see #getGeneratedValue()
	 * @generated
	 */
	public EAttribute getGeneratedValue_Generator()
	{
		return (EAttribute)generatedValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValue#getStrategy <em>Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strategy</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.GeneratedValue#getStrategy()
	 * @see #getGeneratedValue()
	 * @generated
	 */
	public EAttribute getGeneratedValue_Strategy()
	{
		return (EAttribute)generatedValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.IdClass <em>Id Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Id Class</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.IdClass
	 * @generated
	 */
	public EClass getIdClass()
	{
		return idClassEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.IdClass#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.IdClass#getClassName()
	 * @see #getIdClass()
	 * @generated
	 */
	public EAttribute getIdClass_ClassName()
	{
		return (EAttribute)idClassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Inheritance <em>Inheritance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inheritance</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Inheritance
	 * @generated
	 */
	public EClass getInheritance()
	{
		return inheritanceEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Inheritance#getStrategy <em>Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strategy</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Inheritance#getStrategy()
	 * @see #getInheritance()
	 * @generated
	 */
	public EAttribute getInheritance_Strategy()
	{
		return (EAttribute)inheritanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn <em>Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Join Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumn
	 * @generated
	 */
	public EClass getJoinColumn()
	{
		return joinColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumn#getName()
	 * @see #getJoinColumn()
	 * @generated
	 */
	public EAttribute getJoinColumn_Name()
	{
		return (EAttribute)joinColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn#getReferencedColumnName <em>Referenced Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Referenced Column Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumn#getReferencedColumnName()
	 * @see #getJoinColumn()
	 * @generated
	 */
	public EAttribute getJoinColumn_ReferencedColumnName()
	{
		return (EAttribute)joinColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn#isUnique <em>Unique</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumn#isUnique()
	 * @see #getJoinColumn()
	 * @generated
	 */
	public EAttribute getJoinColumn_Unique()
	{
		return (EAttribute)joinColumnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn#isNullable <em>Nullable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nullable</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumn#isNullable()
	 * @see #getJoinColumn()
	 * @generated
	 */
	public EAttribute getJoinColumn_Nullable()
	{
		return (EAttribute)joinColumnEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn#isInsertable <em>Insertable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Insertable</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumn#isInsertable()
	 * @see #getJoinColumn()
	 * @generated
	 */
	public EAttribute getJoinColumn_Insertable()
	{
		return (EAttribute)joinColumnEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn#isUpdatable <em>Updatable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Updatable</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumn#isUpdatable()
	 * @see #getJoinColumn()
	 * @generated
	 */
	public EAttribute getJoinColumn_Updatable()
	{
		return (EAttribute)joinColumnEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn#getColumnDefinition <em>Column Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column Definition</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumn#getColumnDefinition()
	 * @see #getJoinColumn()
	 * @generated
	 */
	public EAttribute getJoinColumn_ColumnDefinition()
	{
		return (EAttribute)joinColumnEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumn#getTable()
	 * @see #getJoinColumn()
	 * @generated
	 */
	public EAttribute getJoinColumn_Table()
	{
		return (EAttribute)joinColumnEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTable <em>Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Join Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTable
	 * @generated
	 */
	public EClass getJoinTable()
	{
		return joinTableEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTable#getName()
	 * @see #getJoinTable()
	 * @generated
	 */
	public EAttribute getJoinTable_Name()
	{
		return (EAttribute)joinTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTable#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTable#getCatalog()
	 * @see #getJoinTable()
	 * @generated
	 */
	public EAttribute getJoinTable_Catalog()
	{
		return (EAttribute)joinTableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTable#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTable#getSchema()
	 * @see #getJoinTable()
	 * @generated
	 */
	public EAttribute getJoinTable_Schema()
	{
		return (EAttribute)joinTableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTable#getJoinColumns <em>Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTable#getJoinColumns()
	 * @see #getJoinTable()
	 * @generated
	 */
	public EReference getJoinTable_JoinColumns()
	{
		return (EReference)joinTableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTable#getInverseJoinColumns <em>Inverse Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inverse Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTable#getInverseJoinColumns()
	 * @see #getJoinTable()
	 * @generated
	 */
	public EReference getJoinTable_InverseJoinColumns()
	{
		return (EReference)joinTableEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTable#getUniqueConstraints <em>Unique Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Unique Constraints</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTable#getUniqueConstraints()
	 * @see #getJoinTable()
	 * @generated
	 */
	public EReference getJoinTable_UniqueConstraints()
	{
		return (EReference)joinTableEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Lob <em>Lob</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Lob</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Lob
	 * @generated
	 */
	public EClass getLob()
	{
		return lobEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.MapKey <em>Map Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Key</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MapKey
	 * @generated
	 */
	public EClass getMapKey()
	{
		return mapKeyEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.MapKey#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MapKey#getName()
	 * @see #getMapKey()
	 * @generated
	 */
	public EAttribute getMapKey_Name()
	{
		return (EAttribute)mapKeyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.EventMethod <em>Event Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Method</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EventMethod
	 * @generated
	 */
	public EClass getEventMethod()
	{
		return eventMethodEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.EventMethod#getMethodName <em>Method Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Method Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EventMethod#getMethodName()
	 * @see #getEventMethod()
	 * @generated
	 */
	public EAttribute getEventMethod_MethodName()
	{
		return (EAttribute)eventMethodEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery <em>Named Native Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Native Query</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery
	 * @generated
	 */
	public EClass getNamedNativeQuery()
	{
		return namedNativeQueryEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery#getName()
	 * @see #getNamedNativeQuery()
	 * @generated
	 */
	public EAttribute getNamedNativeQuery_Name()
	{
		return (EAttribute)namedNativeQueryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery#getResultClass <em>Result Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Class</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery#getResultClass()
	 * @see #getNamedNativeQuery()
	 * @generated
	 */
	public EAttribute getNamedNativeQuery_ResultClass()
	{
		return (EAttribute)namedNativeQueryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery#getResultSetMapping <em>Result Set Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Set Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery#getResultSetMapping()
	 * @see #getNamedNativeQuery()
	 * @generated
	 */
	public EAttribute getNamedNativeQuery_ResultSetMapping()
	{
		return (EAttribute)namedNativeQueryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery#getQuery <em>Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Query</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery#getQuery()
	 * @see #getNamedNativeQuery()
	 * @generated
	 */
	public EAttribute getNamedNativeQuery_Query()
	{
		return (EAttribute)namedNativeQueryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery#getHints <em>Hints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Hints</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery#getHints()
	 * @see #getNamedNativeQuery()
	 * @generated
	 */
	public EReference getNamedNativeQuery_Hints()
	{
		return (EReference)namedNativeQueryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.NamedQuery <em>Named Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Query</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedQuery
	 * @generated
	 */
	public EClass getNamedQuery()
	{
		return namedQueryEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.NamedQuery#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedQuery#getName()
	 * @see #getNamedQuery()
	 * @generated
	 */
	public EAttribute getNamedQuery_Name()
	{
		return (EAttribute)namedQueryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.NamedQuery#getQuery <em>Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Query</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedQuery#getQuery()
	 * @see #getNamedQuery()
	 * @generated
	 */
	public EAttribute getNamedQuery_Query()
	{
		return (EAttribute)namedQueryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.NamedQuery#getHints <em>Hints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Hints</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedQuery#getHints()
	 * @see #getNamedQuery()
	 * @generated
	 */
	public EReference getNamedQuery_Hints()
	{
		return (EReference)namedQueryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.PostLoad <em>Post Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Load</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PostLoad
	 * @generated
	 */
	public EClass getPostLoad()
	{
		return postLoadEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.PostPersist <em>Post Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Persist</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PostPersist
	 * @generated
	 */
	public EClass getPostPersist()
	{
		return postPersistEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.PostRemove <em>Post Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Remove</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PostRemove
	 * @generated
	 */
	public EClass getPostRemove()
	{
		return postRemoveEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.PostUpdate <em>Post Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Update</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PostUpdate
	 * @generated
	 */
	public EClass getPostUpdate()
	{
		return postUpdateEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.PrePersist <em>Pre Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pre Persist</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PrePersist
	 * @generated
	 */
	public EClass getPrePersist()
	{
		return prePersistEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.PreRemove <em>Pre Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pre Remove</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PreRemove
	 * @generated
	 */
	public EClass getPreRemove()
	{
		return preRemoveEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.PreUpdate <em>Pre Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pre Update</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PreUpdate
	 * @generated
	 */
	public EClass getPreUpdate()
	{
		return preUpdateEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.QueryHint <em>Query Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Query Hint</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.QueryHint
	 * @generated
	 */
	public EClass getQueryHint()
	{
		return queryHintEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.QueryHint#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.QueryHint#getName()
	 * @see #getQueryHint()
	 * @generated
	 */
	public EAttribute getQueryHint_Name()
	{
		return (EAttribute)queryHintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.QueryHint#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.QueryHint#getValue()
	 * @see #getQueryHint()
	 * @generated
	 */
	public EAttribute getQueryHint_Value()
	{
		return (EAttribute)queryHintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping <em>Sql Result Set Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sql Result Set Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping
	 * @generated
	 */
	public EClass getSqlResultSetMapping()
	{
		return sqlResultSetMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping#getName()
	 * @see #getSqlResultSetMapping()
	 * @generated
	 */
	public EAttribute getSqlResultSetMapping_Name()
	{
		return (EAttribute)sqlResultSetMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping#getEntityResults <em>Entity Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entity Results</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping#getEntityResults()
	 * @see #getSqlResultSetMapping()
	 * @generated
	 */
	public EReference getSqlResultSetMapping_EntityResults()
	{
		return (EReference)sqlResultSetMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping#getColumnResults <em>Column Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Column Results</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping#getColumnResults()
	 * @see #getSqlResultSetMapping()
	 * @generated
	 */
	public EReference getSqlResultSetMapping_ColumnResults()
	{
		return (EReference)sqlResultSetMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn <em>Primary Key Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Primary Key Join Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn
	 * @generated
	 */
	public EClass getPrimaryKeyJoinColumn()
	{
		return primaryKeyJoinColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn#getName()
	 * @see #getPrimaryKeyJoinColumn()
	 * @generated
	 */
	public EAttribute getPrimaryKeyJoinColumn_Name()
	{
		return (EAttribute)primaryKeyJoinColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn#getReferencedColumnName <em>Referenced Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Referenced Column Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn#getReferencedColumnName()
	 * @see #getPrimaryKeyJoinColumn()
	 * @generated
	 */
	public EAttribute getPrimaryKeyJoinColumn_ReferencedColumnName()
	{
		return (EAttribute)primaryKeyJoinColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn#getColumnDefinition <em>Column Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column Definition</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn#getColumnDefinition()
	 * @see #getPrimaryKeyJoinColumn()
	 * @generated
	 */
	public EAttribute getPrimaryKeyJoinColumn_ColumnDefinition()
	{
		return (EAttribute)primaryKeyJoinColumnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.SecondaryTable <em>Secondary Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Secondary Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SecondaryTable
	 * @generated
	 */
	public EClass getSecondaryTable()
	{
		return secondaryTableEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.SecondaryTable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SecondaryTable#getName()
	 * @see #getSecondaryTable()
	 * @generated
	 */
	public EAttribute getSecondaryTable_Name()
	{
		return (EAttribute)secondaryTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.SecondaryTable#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SecondaryTable#getCatalog()
	 * @see #getSecondaryTable()
	 * @generated
	 */
	public EAttribute getSecondaryTable_Catalog()
	{
		return (EAttribute)secondaryTableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.SecondaryTable#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SecondaryTable#getSchema()
	 * @see #getSecondaryTable()
	 * @generated
	 */
	public EAttribute getSecondaryTable_Schema()
	{
		return (EAttribute)secondaryTableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.SecondaryTable#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Primary Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SecondaryTable#getPrimaryKeyJoinColumns()
	 * @see #getSecondaryTable()
	 * @generated
	 */
	public EReference getSecondaryTable_PrimaryKeyJoinColumns()
	{
		return (EReference)secondaryTableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.SecondaryTable#getUniqueConstraints <em>Unique Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Unique Constraints</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SecondaryTable#getUniqueConstraints()
	 * @see #getSecondaryTable()
	 * @generated
	 */
	public EReference getSecondaryTable_UniqueConstraints()
	{
		return (EReference)secondaryTableEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator <em>Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sequence Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator
	 * @generated
	 */
	public EClass getSequenceGenerator()
	{
		return sequenceGeneratorEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getName()
	 * @see #getSequenceGenerator()
	 * @generated
	 */
	public EAttribute getSequenceGenerator_Name()
	{
		return (EAttribute)sequenceGeneratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getSequenceName <em>Sequence Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sequence Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getSequenceName()
	 * @see #getSequenceGenerator()
	 * @generated
	 */
	public EAttribute getSequenceGenerator_SequenceName()
	{
		return (EAttribute)sequenceGeneratorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getInitialValue <em>Initial Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial Value</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getInitialValue()
	 * @see #getSequenceGenerator()
	 * @generated
	 */
	public EAttribute getSequenceGenerator_InitialValue()
	{
		return (EAttribute)sequenceGeneratorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getAllocationSize <em>Allocation Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allocation Size</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getAllocationSize()
	 * @see #getSequenceGenerator()
	 * @generated
	 */
	public EAttribute getSequenceGenerator_AllocationSize()
	{
		return (EAttribute)sequenceGeneratorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Table <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Table
	 * @generated
	 */
	public EClass getTable()
	{
		return tableEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Table#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Table#getName()
	 * @see #getTable()
	 * @generated
	 */
	public EAttribute getTable_Name()
	{
		return (EAttribute)tableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Table#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Table#getCatalog()
	 * @see #getTable()
	 * @generated
	 */
	public EAttribute getTable_Catalog()
	{
		return (EAttribute)tableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Table#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Table#getSchema()
	 * @see #getTable()
	 * @generated
	 */
	public EAttribute getTable_Schema()
	{
		return (EAttribute)tableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Table#getUniqueConstraints <em>Unique Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Unique Constraints</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Table#getUniqueConstraints()
	 * @see #getTable()
	 * @generated
	 */
	public EReference getTable_UniqueConstraints()
	{
		return (EReference)tableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator <em>Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator
	 * @generated
	 */
	public EClass getTableGenerator()
	{
		return tableGeneratorEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getName()
	 * @see #getTableGenerator()
	 * @generated
	 */
	public EAttribute getTableGenerator_Name()
	{
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getTable()
	 * @see #getTableGenerator()
	 * @generated
	 */
	public EAttribute getTableGenerator_Table()
	{
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getCatalog()
	 * @see #getTableGenerator()
	 * @generated
	 */
	public EAttribute getTableGenerator_Catalog()
	{
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getSchema()
	 * @see #getTableGenerator()
	 * @generated
	 */
	public EAttribute getTableGenerator_Schema()
	{
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getPkColumnName <em>Pk Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pk Column Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getPkColumnName()
	 * @see #getTableGenerator()
	 * @generated
	 */
	public EAttribute getTableGenerator_PkColumnName()
	{
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getValueColumnName <em>Value Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Column Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getValueColumnName()
	 * @see #getTableGenerator()
	 * @generated
	 */
	public EAttribute getTableGenerator_ValueColumnName()
	{
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getPkColumnValue <em>Pk Column Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pk Column Value</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getPkColumnValue()
	 * @see #getTableGenerator()
	 * @generated
	 */
	public EAttribute getTableGenerator_PkColumnValue()
	{
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getInitialValue <em>Initial Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial Value</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getInitialValue()
	 * @see #getTableGenerator()
	 * @generated
	 */
	public EAttribute getTableGenerator_InitialValue()
	{
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getAllocationSize <em>Allocation Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allocation Size</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getAllocationSize()
	 * @see #getTableGenerator()
	 * @generated
	 */
	public EAttribute getTableGenerator_AllocationSize()
	{
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getUniqueConstraints <em>Unique Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Unique Constraints</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getUniqueConstraints()
	 * @see #getTableGenerator()
	 * @generated
	 */
	public EReference getTableGenerator_UniqueConstraints()
	{
		return (EReference)tableGeneratorEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint <em>Unique Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unique Constraint</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint
	 * @generated
	 */
	public EClass getUniqueConstraint()
	{
		return uniqueConstraintEClass;
	}

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint#getColumnNames <em>Column Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Column Names</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint#getColumnNames()
	 * @see #getUniqueConstraint()
	 * @generated
	 */
	public EAttribute getUniqueConstraint_ColumnNames()
	{
		return (EAttribute)uniqueConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.EmptyType <em>Empty Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Empty Type</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmptyType
	 * @generated
	 */
	public EClass getEmptyType()
	{
		return emptyTypeEClass;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.resource.orm.AccessType <em>Access Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Access Type</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AccessType
	 * @generated
	 */
	public EEnum getAccessType()
	{
		return accessTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType <em>Discriminator Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Discriminator Type</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType
	 * @generated
	 */
	public EEnum getDiscriminatorType()
	{
		return discriminatorTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.resource.orm.EnumType <em>Enum Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Enum Type</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EnumType
	 * @generated
	 */
	public EEnum getEnumType()
	{
		return enumTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.resource.orm.FetchType <em>Fetch Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Fetch Type</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.FetchType
	 * @generated
	 */
	public EEnum getFetchType()
	{
		return fetchTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.resource.orm.GenerationType <em>Generation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Generation Type</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.GenerationType
	 * @generated
	 */
	public EEnum getGenerationType()
	{
		return generationTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.resource.orm.InheritanceType <em>Inheritance Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Inheritance Type</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.InheritanceType
	 * @generated
	 */
	public EEnum getInheritanceType()
	{
		return inheritanceTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.resource.orm.TemporalType <em>Temporal Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Temporal Type</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TemporalType
	 * @generated
	 */
	public EEnum getTemporalType()
	{
		return temporalTypeEEnum;
	}

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Access Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Access Type Object</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 * @generated
	 */
	public EDataType getAccessTypeObject()
	{
		return accessTypeObjectEDataType;
	}

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Discriminator Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Discriminator Type Object</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 * @generated
	 */
	public EDataType getDiscriminatorTypeObject()
	{
		return discriminatorTypeObjectEDataType;
	}

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Discriminator Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Discriminator Value</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	public EDataType getDiscriminatorValue()
	{
		return discriminatorValueEDataType;
	}

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Enumerated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Enumerated</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 * @generated
	 */
	public EDataType getEnumerated()
	{
		return enumeratedEDataType;
	}

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Enum Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Enum Type Object</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 * @generated
	 */
	public EDataType getEnumTypeObject()
	{
		return enumTypeObjectEDataType;
	}

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Fetch Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Fetch Type Object</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 * @generated
	 */
	public EDataType getFetchTypeObject()
	{
		return fetchTypeObjectEDataType;
	}

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Generation Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Generation Type Object</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 * @generated
	 */
	public EDataType getGenerationTypeObject()
	{
		return generationTypeObjectEDataType;
	}

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Inheritance Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Inheritance Type Object</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 * @generated
	 */
	public EDataType getInheritanceTypeObject()
	{
		return inheritanceTypeObjectEDataType;
	}

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Order By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Order By</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	public EDataType getOrderBy()
	{
		return orderByEDataType;
	}

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Temporal</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 * @generated
	 */
	public EDataType getTemporal()
	{
		return temporalEDataType;
	}

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Temporal Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Temporal Type Object</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 * @generated
	 */
	public EDataType getTemporalTypeObject()
	{
		return temporalTypeObjectEDataType;
	}

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Version Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Version Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	public EDataType getVersionType()
	{
		return versionTypeEDataType;
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public OrmFactory getOrmFactory()
	{
		return (OrmFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents()
	{
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		entityMappingsEClass = createEClass(ENTITY_MAPPINGS);
		createEAttribute(entityMappingsEClass, ENTITY_MAPPINGS__VERSION);
		createEAttribute(entityMappingsEClass, ENTITY_MAPPINGS__DESCRIPTION);
		createEReference(entityMappingsEClass, ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA);
		createEAttribute(entityMappingsEClass, ENTITY_MAPPINGS__PACKAGE);
		createEAttribute(entityMappingsEClass, ENTITY_MAPPINGS__SCHEMA);
		createEAttribute(entityMappingsEClass, ENTITY_MAPPINGS__CATALOG);
		createEAttribute(entityMappingsEClass, ENTITY_MAPPINGS__ACCESS);
		createEReference(entityMappingsEClass, ENTITY_MAPPINGS__SEQUENCE_GENERATORS);
		createEReference(entityMappingsEClass, ENTITY_MAPPINGS__TABLE_GENERATORS);
		createEReference(entityMappingsEClass, ENTITY_MAPPINGS__NAMED_QUERIES);
		createEReference(entityMappingsEClass, ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES);
		createEReference(entityMappingsEClass, ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS);
		createEReference(entityMappingsEClass, ENTITY_MAPPINGS__MAPPED_SUPERCLASSES);
		createEReference(entityMappingsEClass, ENTITY_MAPPINGS__ENTITIES);
		createEReference(entityMappingsEClass, ENTITY_MAPPINGS__EMBEDDABLES);

		persistenceUnitMetadataEClass = createEClass(PERSISTENCE_UNIT_METADATA);
		createEReference(persistenceUnitMetadataEClass, PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE);
		createEReference(persistenceUnitMetadataEClass, PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS);

		persistenceUnitDefaultsEClass = createEClass(PERSISTENCE_UNIT_DEFAULTS);
		createEAttribute(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__SCHEMA);
		createEAttribute(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__CATALOG);
		createEAttribute(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__ACCESS);
		createEReference(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST);
		createEReference(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS);

		mappedSuperclassEClass = createEClass(MAPPED_SUPERCLASS);
		createEAttribute(mappedSuperclassEClass, MAPPED_SUPERCLASS__CLASS_NAME);
		createEAttribute(mappedSuperclassEClass, MAPPED_SUPERCLASS__ACCESS);
		createEAttribute(mappedSuperclassEClass, MAPPED_SUPERCLASS__METADATA_COMPLETE);
		createEAttribute(mappedSuperclassEClass, MAPPED_SUPERCLASS__DESCRIPTION);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__ID_CLASS);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__ENTITY_LISTENERS);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__PRE_PERSIST);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__POST_PERSIST);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__PRE_REMOVE);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__POST_REMOVE);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__PRE_UPDATE);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__POST_UPDATE);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__POST_LOAD);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__ATTRIBUTES);

		entityEClass = createEClass(ENTITY);
		createEAttribute(entityEClass, ENTITY__NAME);
		createEAttribute(entityEClass, ENTITY__CLASS_NAME);
		createEAttribute(entityEClass, ENTITY__ACCESS);
		createEAttribute(entityEClass, ENTITY__METADATA_COMPLETE);
		createEAttribute(entityEClass, ENTITY__DESCRIPTION);
		createEReference(entityEClass, ENTITY__TABLES);
		createEReference(entityEClass, ENTITY__SECONDARY_TABLES);
		createEReference(entityEClass, ENTITY__PRIMARY_KEY_JOIN_COLUMNS);
		createEReference(entityEClass, ENTITY__ID_CLASS);
		createEReference(entityEClass, ENTITY__INHERITANCE);
		createEAttribute(entityEClass, ENTITY__DISCRIMINATOR_VALUE);
		createEReference(entityEClass, ENTITY__DISCRIMINATOR_COLUMN);
		createEReference(entityEClass, ENTITY__SEQUENCE_GENERATOR);
		createEReference(entityEClass, ENTITY__TABLE_GENERATOR);
		createEReference(entityEClass, ENTITY__NAMED_QUERIES);
		createEReference(entityEClass, ENTITY__NAMED_NATIVE_QUERIES);
		createEReference(entityEClass, ENTITY__SQL_RESULT_SET_MAPPINGS);
		createEReference(entityEClass, ENTITY__EXCLUDE_DEFAULT_LISTENERS);
		createEReference(entityEClass, ENTITY__EXCLUDE_SUPERCLASS_LISTENERS);
		createEReference(entityEClass, ENTITY__ENTITY_LISTENERS);
		createEReference(entityEClass, ENTITY__PRE_PERSIST);
		createEReference(entityEClass, ENTITY__POST_PERSIST);
		createEReference(entityEClass, ENTITY__PRE_REMOVE);
		createEReference(entityEClass, ENTITY__POST_REMOVE);
		createEReference(entityEClass, ENTITY__PRE_UPDATE);
		createEReference(entityEClass, ENTITY__POST_UPDATE);
		createEReference(entityEClass, ENTITY__POST_LOAD);
		createEReference(entityEClass, ENTITY__ATTRIBUTE_OVERRIDES);
		createEReference(entityEClass, ENTITY__ASSOCIATION_OVERRIDES);
		createEReference(entityEClass, ENTITY__ATTRIBUTES);

		embeddableEClass = createEClass(EMBEDDABLE);
		createEAttribute(embeddableEClass, EMBEDDABLE__CLASS_NAME);
		createEAttribute(embeddableEClass, EMBEDDABLE__ACCESS);
		createEAttribute(embeddableEClass, EMBEDDABLE__METADATA_COMPLETE);
		createEAttribute(embeddableEClass, EMBEDDABLE__DESCRIPTION);
		createEReference(embeddableEClass, EMBEDDABLE__ATTRIBUTES);

		attributesEClass = createEClass(ATTRIBUTES);
		createEReference(attributesEClass, ATTRIBUTES__IDS);
		createEReference(attributesEClass, ATTRIBUTES__EMBEDDED_IDS);
		createEReference(attributesEClass, ATTRIBUTES__BASICS);
		createEReference(attributesEClass, ATTRIBUTES__VERSIONS);
		createEReference(attributesEClass, ATTRIBUTES__MANY_TO_ONES);
		createEReference(attributesEClass, ATTRIBUTES__ONE_TO_MANIES);
		createEReference(attributesEClass, ATTRIBUTES__ONE_TO_ONES);
		createEReference(attributesEClass, ATTRIBUTES__MANY_TO_MANIES);
		createEReference(attributesEClass, ATTRIBUTES__EMBEDDEDS);
		createEReference(attributesEClass, ATTRIBUTES__TRANSIENTS);

		embeddableAttributesEClass = createEClass(EMBEDDABLE_ATTRIBUTES);
		createEReference(embeddableAttributesEClass, EMBEDDABLE_ATTRIBUTES__BASICS);
		createEReference(embeddableAttributesEClass, EMBEDDABLE_ATTRIBUTES__TRANSIENTS);

		idEClass = createEClass(ID);
		createEAttribute(idEClass, ID__NAME);
		createEReference(idEClass, ID__COLUMN);
		createEReference(idEClass, ID__GENERATED_VALUE);
		createEAttribute(idEClass, ID__TEMPORAL);
		createEReference(idEClass, ID__TABLE_GENERATOR);
		createEReference(idEClass, ID__SEQUENCE_GENERATOR);

		embeddedIdEClass = createEClass(EMBEDDED_ID);
		createEAttribute(embeddedIdEClass, EMBEDDED_ID__NAME);
		createEReference(embeddedIdEClass, EMBEDDED_ID__ATTRIBUTE_OVERRIDES);

		basicEClass = createEClass(BASIC);
		createEAttribute(basicEClass, BASIC__NAME);
		createEAttribute(basicEClass, BASIC__FETCH);
		createEAttribute(basicEClass, BASIC__OPTIONAL);
		createEReference(basicEClass, BASIC__COLUMN);
		createEReference(basicEClass, BASIC__LOB);
		createEAttribute(basicEClass, BASIC__TEMPORAL);
		createEAttribute(basicEClass, BASIC__ENUMERATED);

		versionEClass = createEClass(VERSION);
		createEAttribute(versionEClass, VERSION__NAME);
		createEReference(versionEClass, VERSION__COLUMN);
		createEAttribute(versionEClass, VERSION__TEMPORAL);

		manyToOneEClass = createEClass(MANY_TO_ONE);
		createEAttribute(manyToOneEClass, MANY_TO_ONE__NAME);
		createEAttribute(manyToOneEClass, MANY_TO_ONE__TARGET_ENTITY);
		createEAttribute(manyToOneEClass, MANY_TO_ONE__FETCH);
		createEAttribute(manyToOneEClass, MANY_TO_ONE__OPTIONAL);
		createEReference(manyToOneEClass, MANY_TO_ONE__JOIN_COLUMNS);
		createEReference(manyToOneEClass, MANY_TO_ONE__JOIN_TABLE);
		createEReference(manyToOneEClass, MANY_TO_ONE__CASCADE);

		oneToManyEClass = createEClass(ONE_TO_MANY);
		createEAttribute(oneToManyEClass, ONE_TO_MANY__NAME);
		createEAttribute(oneToManyEClass, ONE_TO_MANY__TARGET_ENTITY);
		createEAttribute(oneToManyEClass, ONE_TO_MANY__FETCH);
		createEAttribute(oneToManyEClass, ONE_TO_MANY__MAPPED_BY);
		createEAttribute(oneToManyEClass, ONE_TO_MANY__ORDER_BY);
		createEReference(oneToManyEClass, ONE_TO_MANY__MAP_KEY);
		createEReference(oneToManyEClass, ONE_TO_MANY__JOIN_TABLE);
		createEReference(oneToManyEClass, ONE_TO_MANY__JOIN_COLUMNS);
		createEReference(oneToManyEClass, ONE_TO_MANY__CASCADE);

		oneToOneEClass = createEClass(ONE_TO_ONE);
		createEAttribute(oneToOneEClass, ONE_TO_ONE__NAME);
		createEAttribute(oneToOneEClass, ONE_TO_ONE__TARGET_ENTITY);
		createEAttribute(oneToOneEClass, ONE_TO_ONE__FETCH);
		createEAttribute(oneToOneEClass, ONE_TO_ONE__OPTIONAL);
		createEAttribute(oneToOneEClass, ONE_TO_ONE__MAPPED_BY);
		createEReference(oneToOneEClass, ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS);
		createEReference(oneToOneEClass, ONE_TO_ONE__JOIN_COLUMNS);
		createEReference(oneToOneEClass, ONE_TO_ONE__JOIN_TABLE);
		createEReference(oneToOneEClass, ONE_TO_ONE__CASCADE);

		manyToManyEClass = createEClass(MANY_TO_MANY);
		createEAttribute(manyToManyEClass, MANY_TO_MANY__NAME);
		createEAttribute(manyToManyEClass, MANY_TO_MANY__TARGET_ENTITY);
		createEAttribute(manyToManyEClass, MANY_TO_MANY__FETCH);
		createEAttribute(manyToManyEClass, MANY_TO_MANY__MAPPED_BY);
		createEAttribute(manyToManyEClass, MANY_TO_MANY__ORDER_BY);
		createEReference(manyToManyEClass, MANY_TO_MANY__MAP_KEY);
		createEReference(manyToManyEClass, MANY_TO_MANY__JOIN_TABLE);
		createEReference(manyToManyEClass, MANY_TO_MANY__CASCADE);

		embeddedEClass = createEClass(EMBEDDED);
		createEAttribute(embeddedEClass, EMBEDDED__NAME);
		createEReference(embeddedEClass, EMBEDDED__ATTRIBUTE_OVERRIDES);

		transientEClass = createEClass(TRANSIENT);
		createEAttribute(transientEClass, TRANSIENT__NAME);

		associationOverrideEClass = createEClass(ASSOCIATION_OVERRIDE);
		createEReference(associationOverrideEClass, ASSOCIATION_OVERRIDE__JOIN_COLUMN);
		createEAttribute(associationOverrideEClass, ASSOCIATION_OVERRIDE__NAME);

		attributeOverrideEClass = createEClass(ATTRIBUTE_OVERRIDE);
		createEReference(attributeOverrideEClass, ATTRIBUTE_OVERRIDE__COLUMN);
		createEAttribute(attributeOverrideEClass, ATTRIBUTE_OVERRIDE__NAME);

		cascadeTypeEClass = createEClass(CASCADE_TYPE);
		createEReference(cascadeTypeEClass, CASCADE_TYPE__CASCADE_ALL);
		createEReference(cascadeTypeEClass, CASCADE_TYPE__CASCADE_PERSIST);
		createEReference(cascadeTypeEClass, CASCADE_TYPE__CASCADE_MERGE);
		createEReference(cascadeTypeEClass, CASCADE_TYPE__CASCADE_REMOVE);
		createEReference(cascadeTypeEClass, CASCADE_TYPE__CASCADE_REFRESH);

		columnEClass = createEClass(COLUMN);
		createEAttribute(columnEClass, COLUMN__COLUMN_DEFINITION);
		createEAttribute(columnEClass, COLUMN__INSERTABLE);
		createEAttribute(columnEClass, COLUMN__LENGTH);
		createEAttribute(columnEClass, COLUMN__NAME);
		createEAttribute(columnEClass, COLUMN__NULLABLE);
		createEAttribute(columnEClass, COLUMN__PRECISION);
		createEAttribute(columnEClass, COLUMN__SCALE);
		createEAttribute(columnEClass, COLUMN__TABLE);
		createEAttribute(columnEClass, COLUMN__UNIQUE);
		createEAttribute(columnEClass, COLUMN__UPDATABLE);

		columnResultEClass = createEClass(COLUMN_RESULT);
		createEAttribute(columnResultEClass, COLUMN_RESULT__NAME);

		discriminatorColumnEClass = createEClass(DISCRIMINATOR_COLUMN);
		createEAttribute(discriminatorColumnEClass, DISCRIMINATOR_COLUMN__COLUMN_DEFINITION);
		createEAttribute(discriminatorColumnEClass, DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE);
		createEAttribute(discriminatorColumnEClass, DISCRIMINATOR_COLUMN__LENGTH);
		createEAttribute(discriminatorColumnEClass, DISCRIMINATOR_COLUMN__NAME);

		entityListenersEClass = createEClass(ENTITY_LISTENERS);
		createEReference(entityListenersEClass, ENTITY_LISTENERS__ENTITY_LISTENERS);

		entityListenerEClass = createEClass(ENTITY_LISTENER);
		createEAttribute(entityListenerEClass, ENTITY_LISTENER__CLASS_NAME);
		createEReference(entityListenerEClass, ENTITY_LISTENER__PRE_PERSIST);
		createEReference(entityListenerEClass, ENTITY_LISTENER__POST_PERSIST);
		createEReference(entityListenerEClass, ENTITY_LISTENER__PRE_REMOVE);
		createEReference(entityListenerEClass, ENTITY_LISTENER__POST_REMOVE);
		createEReference(entityListenerEClass, ENTITY_LISTENER__PRE_UPDATE);
		createEReference(entityListenerEClass, ENTITY_LISTENER__POST_UPDATE);
		createEReference(entityListenerEClass, ENTITY_LISTENER__POST_LOAD);

		entityResultEClass = createEClass(ENTITY_RESULT);
		createEAttribute(entityResultEClass, ENTITY_RESULT__DISCRIMINATOR_COLUMN);
		createEAttribute(entityResultEClass, ENTITY_RESULT__ENTITY_CLASS);
		createEReference(entityResultEClass, ENTITY_RESULT__FIELD_RESULTS);

		eventMethodEClass = createEClass(EVENT_METHOD);
		createEAttribute(eventMethodEClass, EVENT_METHOD__METHOD_NAME);

		fieldResultEClass = createEClass(FIELD_RESULT);
		createEAttribute(fieldResultEClass, FIELD_RESULT__NAME);
		createEAttribute(fieldResultEClass, FIELD_RESULT__COLUMN);

		generatedValueEClass = createEClass(GENERATED_VALUE);
		createEAttribute(generatedValueEClass, GENERATED_VALUE__GENERATOR);
		createEAttribute(generatedValueEClass, GENERATED_VALUE__STRATEGY);

		idClassEClass = createEClass(ID_CLASS);
		createEAttribute(idClassEClass, ID_CLASS__CLASS_NAME);

		inheritanceEClass = createEClass(INHERITANCE);
		createEAttribute(inheritanceEClass, INHERITANCE__STRATEGY);

		joinColumnEClass = createEClass(JOIN_COLUMN);
		createEAttribute(joinColumnEClass, JOIN_COLUMN__NAME);
		createEAttribute(joinColumnEClass, JOIN_COLUMN__REFERENCED_COLUMN_NAME);
		createEAttribute(joinColumnEClass, JOIN_COLUMN__UNIQUE);
		createEAttribute(joinColumnEClass, JOIN_COLUMN__NULLABLE);
		createEAttribute(joinColumnEClass, JOIN_COLUMN__INSERTABLE);
		createEAttribute(joinColumnEClass, JOIN_COLUMN__UPDATABLE);
		createEAttribute(joinColumnEClass, JOIN_COLUMN__COLUMN_DEFINITION);
		createEAttribute(joinColumnEClass, JOIN_COLUMN__TABLE);

		joinTableEClass = createEClass(JOIN_TABLE);
		createEAttribute(joinTableEClass, JOIN_TABLE__NAME);
		createEAttribute(joinTableEClass, JOIN_TABLE__CATALOG);
		createEAttribute(joinTableEClass, JOIN_TABLE__SCHEMA);
		createEReference(joinTableEClass, JOIN_TABLE__JOIN_COLUMNS);
		createEReference(joinTableEClass, JOIN_TABLE__INVERSE_JOIN_COLUMNS);
		createEReference(joinTableEClass, JOIN_TABLE__UNIQUE_CONSTRAINTS);

		lobEClass = createEClass(LOB);

		mapKeyEClass = createEClass(MAP_KEY);
		createEAttribute(mapKeyEClass, MAP_KEY__NAME);

		namedNativeQueryEClass = createEClass(NAMED_NATIVE_QUERY);
		createEAttribute(namedNativeQueryEClass, NAMED_NATIVE_QUERY__NAME);
		createEAttribute(namedNativeQueryEClass, NAMED_NATIVE_QUERY__RESULT_CLASS);
		createEAttribute(namedNativeQueryEClass, NAMED_NATIVE_QUERY__RESULT_SET_MAPPING);
		createEAttribute(namedNativeQueryEClass, NAMED_NATIVE_QUERY__QUERY);
		createEReference(namedNativeQueryEClass, NAMED_NATIVE_QUERY__HINTS);

		namedQueryEClass = createEClass(NAMED_QUERY);
		createEAttribute(namedQueryEClass, NAMED_QUERY__NAME);
		createEAttribute(namedQueryEClass, NAMED_QUERY__QUERY);
		createEReference(namedQueryEClass, NAMED_QUERY__HINTS);

		postLoadEClass = createEClass(POST_LOAD);

		postPersistEClass = createEClass(POST_PERSIST);

		postRemoveEClass = createEClass(POST_REMOVE);

		postUpdateEClass = createEClass(POST_UPDATE);

		prePersistEClass = createEClass(PRE_PERSIST);

		preRemoveEClass = createEClass(PRE_REMOVE);

		preUpdateEClass = createEClass(PRE_UPDATE);

		primaryKeyJoinColumnEClass = createEClass(PRIMARY_KEY_JOIN_COLUMN);
		createEAttribute(primaryKeyJoinColumnEClass, PRIMARY_KEY_JOIN_COLUMN__NAME);
		createEAttribute(primaryKeyJoinColumnEClass, PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME);
		createEAttribute(primaryKeyJoinColumnEClass, PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION);

		queryHintEClass = createEClass(QUERY_HINT);
		createEAttribute(queryHintEClass, QUERY_HINT__NAME);
		createEAttribute(queryHintEClass, QUERY_HINT__VALUE);

		secondaryTableEClass = createEClass(SECONDARY_TABLE);
		createEAttribute(secondaryTableEClass, SECONDARY_TABLE__NAME);
		createEAttribute(secondaryTableEClass, SECONDARY_TABLE__CATALOG);
		createEAttribute(secondaryTableEClass, SECONDARY_TABLE__SCHEMA);
		createEReference(secondaryTableEClass, SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS);
		createEReference(secondaryTableEClass, SECONDARY_TABLE__UNIQUE_CONSTRAINTS);

		sequenceGeneratorEClass = createEClass(SEQUENCE_GENERATOR);
		createEAttribute(sequenceGeneratorEClass, SEQUENCE_GENERATOR__NAME);
		createEAttribute(sequenceGeneratorEClass, SEQUENCE_GENERATOR__SEQUENCE_NAME);
		createEAttribute(sequenceGeneratorEClass, SEQUENCE_GENERATOR__INITIAL_VALUE);
		createEAttribute(sequenceGeneratorEClass, SEQUENCE_GENERATOR__ALLOCATION_SIZE);

		sqlResultSetMappingEClass = createEClass(SQL_RESULT_SET_MAPPING);
		createEAttribute(sqlResultSetMappingEClass, SQL_RESULT_SET_MAPPING__NAME);
		createEReference(sqlResultSetMappingEClass, SQL_RESULT_SET_MAPPING__ENTITY_RESULTS);
		createEReference(sqlResultSetMappingEClass, SQL_RESULT_SET_MAPPING__COLUMN_RESULTS);

		tableEClass = createEClass(TABLE);
		createEAttribute(tableEClass, TABLE__NAME);
		createEAttribute(tableEClass, TABLE__CATALOG);
		createEAttribute(tableEClass, TABLE__SCHEMA);
		createEReference(tableEClass, TABLE__UNIQUE_CONSTRAINTS);

		tableGeneratorEClass = createEClass(TABLE_GENERATOR);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__NAME);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__TABLE);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__CATALOG);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__SCHEMA);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__PK_COLUMN_NAME);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__VALUE_COLUMN_NAME);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__PK_COLUMN_VALUE);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__INITIAL_VALUE);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__ALLOCATION_SIZE);
		createEReference(tableGeneratorEClass, TABLE_GENERATOR__UNIQUE_CONSTRAINTS);

		uniqueConstraintEClass = createEClass(UNIQUE_CONSTRAINT);
		createEAttribute(uniqueConstraintEClass, UNIQUE_CONSTRAINT__COLUMN_NAMES);

		emptyTypeEClass = createEClass(EMPTY_TYPE);

		// Create enums
		accessTypeEEnum = createEEnum(ACCESS_TYPE);
		discriminatorTypeEEnum = createEEnum(DISCRIMINATOR_TYPE);
		enumTypeEEnum = createEEnum(ENUM_TYPE);
		fetchTypeEEnum = createEEnum(FETCH_TYPE);
		generationTypeEEnum = createEEnum(GENERATION_TYPE);
		inheritanceTypeEEnum = createEEnum(INHERITANCE_TYPE);
		temporalTypeEEnum = createEEnum(TEMPORAL_TYPE);

		// Create data types
		accessTypeObjectEDataType = createEDataType(ACCESS_TYPE_OBJECT);
		discriminatorTypeObjectEDataType = createEDataType(DISCRIMINATOR_TYPE_OBJECT);
		discriminatorValueEDataType = createEDataType(DISCRIMINATOR_VALUE);
		enumeratedEDataType = createEDataType(ENUMERATED);
		enumTypeObjectEDataType = createEDataType(ENUM_TYPE_OBJECT);
		fetchTypeObjectEDataType = createEDataType(FETCH_TYPE_OBJECT);
		generationTypeObjectEDataType = createEDataType(GENERATION_TYPE_OBJECT);
		inheritanceTypeObjectEDataType = createEDataType(INHERITANCE_TYPE_OBJECT);
		orderByEDataType = createEDataType(ORDER_BY);
		temporalEDataType = createEDataType(TEMPORAL);
		temporalTypeObjectEDataType = createEDataType(TEMPORAL_TYPE_OBJECT);
		versionTypeEDataType = createEDataType(VERSION_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents()
	{
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		postLoadEClass.getESuperTypes().add(this.getEventMethod());
		postPersistEClass.getESuperTypes().add(this.getEventMethod());
		postRemoveEClass.getESuperTypes().add(this.getEventMethod());
		postUpdateEClass.getESuperTypes().add(this.getEventMethod());
		prePersistEClass.getESuperTypes().add(this.getEventMethod());
		preRemoveEClass.getESuperTypes().add(this.getEventMethod());
		preUpdateEClass.getESuperTypes().add(this.getEventMethod());

		// Initialize classes and features; add operations and parameters
		initEClass(entityMappingsEClass, EntityMappings.class, "EntityMappings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntityMappings_Version(), this.getVersionType(), "version", "1.0", 1, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappings_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_PersistenceUnitMetadata(), this.getPersistenceUnitMetadata(), null, "persistenceUnitMetadata", null, 0, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappings_Package(), theXMLTypePackage.getString(), "package", null, 0, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappings_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappings_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappings_Access(), this.getAccessType(), "access", "PROPERTY", 0, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_SequenceGenerators(), this.getSequenceGenerator(), null, "sequenceGenerators", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_TableGenerators(), this.getTableGenerator(), null, "tableGenerators", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_NamedQueries(), this.getNamedQuery(), null, "namedQueries", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_NamedNativeQueries(), this.getNamedNativeQuery(), null, "namedNativeQueries", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_SqlResultSetMappings(), this.getSqlResultSetMapping(), null, "sqlResultSetMappings", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_MappedSuperclasses(), this.getMappedSuperclass(), null, "mappedSuperclasses", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_Entities(), this.getEntity(), null, "entities", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_Embeddables(), this.getEmbeddable(), null, "embeddables", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(persistenceUnitMetadataEClass, PersistenceUnitMetadata.class, "PersistenceUnitMetadata", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPersistenceUnitMetadata_XmlMappingMetadataComplete(), this.getEmptyType(), null, "xmlMappingMetadataComplete", null, 0, 1, PersistenceUnitMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPersistenceUnitMetadata_PersistenceUnitDefaults(), this.getPersistenceUnitDefaults(), null, "persistenceUnitDefaults", null, 0, 1, PersistenceUnitMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(persistenceUnitDefaultsEClass, PersistenceUnitDefaults.class, "PersistenceUnitDefaults", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPersistenceUnitDefaults_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaults_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaults_Access(), this.getAccessType(), "access", "PROPERTY", 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPersistenceUnitDefaults_CascadePersist(), this.getEmptyType(), null, "cascadePersist", null, 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPersistenceUnitDefaults_EntityListeners(), this.getEntityListeners(), null, "entityListeners", null, 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappedSuperclassEClass, MappedSuperclass.class, "MappedSuperclass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMappedSuperclass_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappedSuperclass_Access(), this.getAccessType(), "access", "PROPERTY", 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappedSuperclass_MetadataComplete(), theXMLTypePackage.getBoolean(), "metadataComplete", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappedSuperclass_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_IdClass(), this.getIdClass(), null, "idClass", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_ExcludeDefaultListeners(), this.getEmptyType(), null, "excludeDefaultListeners", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_ExcludeSuperclassListeners(), this.getEmptyType(), null, "excludeSuperclassListeners", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_EntityListeners(), this.getEntityListeners(), null, "entityListeners", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PrePersist(), this.getPrePersist(), null, "prePersist", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PostPersist(), this.getPostPersist(), null, "postPersist", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PreRemove(), this.getPreRemove(), null, "preRemove", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PostRemove(), this.getPostRemove(), null, "postRemove", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PreUpdate(), this.getPreUpdate(), null, "preUpdate", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PostUpdate(), this.getPostUpdate(), null, "postUpdate", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PostLoad(), this.getPostLoad(), null, "postLoad", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_Attributes(), this.getAttributes(), null, "attributes", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityEClass, Entity.class, "Entity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntity_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_Access(), this.getAccessType(), "access", "PROPERTY", 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_MetadataComplete(), theXMLTypePackage.getBoolean(), "metadataComplete", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_Tables(), this.getTable(), null, "tables", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_SecondaryTables(), this.getSecondaryTable(), null, "secondaryTables", null, 0, -1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_PrimaryKeyJoinColumns(), this.getPrimaryKeyJoinColumn(), null, "primaryKeyJoinColumns", null, 0, -1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_IdClass(), this.getIdClass(), null, "idClass", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_Inheritance(), this.getInheritance(), null, "inheritance", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_DiscriminatorValue(), this.getDiscriminatorValue(), "discriminatorValue", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_DiscriminatorColumn(), this.getDiscriminatorColumn(), null, "discriminatorColumn", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_SequenceGenerator(), this.getSequenceGenerator(), null, "sequenceGenerator", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_TableGenerator(), this.getTableGenerator(), null, "tableGenerator", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_NamedQueries(), this.getNamedQuery(), null, "namedQueries", null, 0, -1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_NamedNativeQueries(), this.getNamedNativeQuery(), null, "namedNativeQueries", null, 0, -1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_SqlResultSetMappings(), this.getSqlResultSetMapping(), null, "sqlResultSetMappings", null, 0, -1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_ExcludeDefaultListeners(), this.getEmptyType(), null, "excludeDefaultListeners", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_ExcludeSuperclassListeners(), this.getEmptyType(), null, "excludeSuperclassListeners", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_EntityListeners(), this.getEntityListeners(), null, "entityListeners", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_PrePersist(), this.getPrePersist(), null, "prePersist", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_PostPersist(), this.getPostPersist(), null, "postPersist", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_PreRemove(), this.getPreRemove(), null, "preRemove", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_PostRemove(), this.getPostRemove(), null, "postRemove", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_PreUpdate(), this.getPreUpdate(), null, "preUpdate", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_PostUpdate(), this.getPostUpdate(), null, "postUpdate", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_PostLoad(), this.getPostLoad(), null, "postLoad", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_AttributeOverrides(), this.getAttributeOverride(), null, "attributeOverrides", null, 0, -1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_AssociationOverrides(), this.getAssociationOverride(), null, "associationOverrides", null, 0, -1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_Attributes(), this.getAttributes(), null, "attributes", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(embeddableEClass, Embeddable.class, "Embeddable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEmbeddable_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, Embeddable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEmbeddable_Access(), this.getAccessType(), "access", "PROPERTY", 0, 1, Embeddable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEmbeddable_MetadataComplete(), theXMLTypePackage.getBoolean(), "metadataComplete", null, 0, 1, Embeddable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEmbeddable_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, Embeddable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEmbeddable_Attributes(), this.getEmbeddableAttributes(), null, "attributes", null, 0, 1, Embeddable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributesEClass, Attributes.class, "Attributes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributes_Ids(), this.getId(), null, "ids", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_EmbeddedIds(), this.getEmbeddedId(), null, "embeddedIds", null, 0, 1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_Basics(), this.getBasic(), null, "basics", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_Versions(), this.getVersion(), null, "versions", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_ManyToOnes(), this.getManyToOne(), null, "manyToOnes", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_OneToManies(), this.getOneToMany(), null, "oneToManies", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_OneToOnes(), this.getOneToOne(), null, "oneToOnes", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_ManyToManies(), this.getManyToMany(), null, "manyToManies", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_Embeddeds(), this.getEmbedded(), null, "embeddeds", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_Transients(), this.getTransient(), null, "transients", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(embeddableAttributesEClass, EmbeddableAttributes.class, "EmbeddableAttributes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEmbeddableAttributes_Basics(), this.getBasic(), null, "basics", null, 0, -1, EmbeddableAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEmbeddableAttributes_Transients(), this.getTransient(), null, "transients", null, 0, -1, EmbeddableAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(idEClass, Id.class, "Id", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getId_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Id.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getId_Column(), this.getColumn(), null, "column", null, 0, 1, Id.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getId_GeneratedValue(), this.getGeneratedValue(), null, "generatedValue", null, 0, 1, Id.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getId_Temporal(), this.getTemporal(), "temporal", null, 0, 1, Id.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getId_TableGenerator(), this.getTableGenerator(), null, "tableGenerator", null, 0, 1, Id.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getId_SequenceGenerator(), this.getSequenceGenerator(), null, "sequenceGenerator", null, 0, 1, Id.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(embeddedIdEClass, EmbeddedId.class, "EmbeddedId", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEmbeddedId_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, EmbeddedId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEmbeddedId_AttributeOverrides(), this.getAttributeOverride(), null, "attributeOverrides", null, 0, -1, EmbeddedId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(basicEClass, Basic.class, "Basic", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBasic_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Basic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBasic_Fetch(), this.getFetchType(), "fetch", "LAZY", 0, 1, Basic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBasic_Optional(), theXMLTypePackage.getBoolean(), "optional", null, 0, 1, Basic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBasic_Column(), this.getColumn(), null, "column", null, 0, 1, Basic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBasic_Lob(), this.getLob(), null, "lob", null, 0, 1, Basic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBasic_Temporal(), this.getTemporal(), "temporal", null, 0, 1, Basic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBasic_Enumerated(), this.getEnumerated(), "enumerated", null, 0, 1, Basic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(versionEClass, Version.class, "Version", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVersion_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVersion_Column(), this.getColumn(), null, "column", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersion_Temporal(), this.getTemporal(), "temporal", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(manyToOneEClass, ManyToOne.class, "ManyToOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getManyToOne_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ManyToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManyToOne_TargetEntity(), theXMLTypePackage.getString(), "targetEntity", null, 0, 1, ManyToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManyToOne_Fetch(), this.getFetchType(), "fetch", "LAZY", 0, 1, ManyToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManyToOne_Optional(), theXMLTypePackage.getBoolean(), "optional", null, 0, 1, ManyToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getManyToOne_JoinColumns(), this.getJoinColumn(), null, "joinColumns", null, 0, -1, ManyToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getManyToOne_JoinTable(), this.getJoinTable(), null, "joinTable", null, 0, 1, ManyToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getManyToOne_Cascade(), this.getCascadeType(), null, "cascade", null, 0, 1, ManyToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(oneToManyEClass, OneToMany.class, "OneToMany", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOneToMany_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, OneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOneToMany_TargetEntity(), theXMLTypePackage.getString(), "targetEntity", null, 0, 1, OneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOneToMany_Fetch(), this.getFetchType(), "fetch", "LAZY", 0, 1, OneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOneToMany_MappedBy(), theXMLTypePackage.getString(), "mappedBy", null, 0, 1, OneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOneToMany_OrderBy(), this.getOrderBy(), "orderBy", null, 0, 1, OneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOneToMany_MapKey(), this.getMapKey(), null, "mapKey", null, 0, 1, OneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOneToMany_JoinTable(), this.getJoinTable(), null, "joinTable", null, 0, 1, OneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOneToMany_JoinColumns(), this.getJoinColumn(), null, "joinColumns", null, 0, -1, OneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOneToMany_Cascade(), this.getCascadeType(), null, "cascade", null, 0, 1, OneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(oneToOneEClass, OneToOne.class, "OneToOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOneToOne_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, OneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOneToOne_TargetEntity(), theXMLTypePackage.getString(), "targetEntity", null, 0, 1, OneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOneToOne_Fetch(), this.getFetchType(), "fetch", "LAZY", 0, 1, OneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOneToOne_Optional(), theXMLTypePackage.getBoolean(), "optional", null, 0, 1, OneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOneToOne_MappedBy(), theXMLTypePackage.getString(), "mappedBy", null, 0, 1, OneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOneToOne_PrimaryKeyJoinColumns(), this.getPrimaryKeyJoinColumn(), null, "primaryKeyJoinColumns", null, 0, -1, OneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOneToOne_JoinColumns(), this.getJoinColumn(), null, "joinColumns", null, 0, -1, OneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOneToOne_JoinTable(), this.getJoinTable(), null, "joinTable", null, 0, 1, OneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOneToOne_Cascade(), this.getCascadeType(), null, "cascade", null, 0, 1, OneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(manyToManyEClass, ManyToMany.class, "ManyToMany", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getManyToMany_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManyToMany_TargetEntity(), theXMLTypePackage.getString(), "targetEntity", null, 0, 1, ManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManyToMany_Fetch(), this.getFetchType(), "fetch", "LAZY", 0, 1, ManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManyToMany_MappedBy(), theXMLTypePackage.getString(), "mappedBy", null, 0, 1, ManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManyToMany_OrderBy(), this.getOrderBy(), "orderBy", null, 0, 1, ManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getManyToMany_MapKey(), this.getMapKey(), null, "mapKey", null, 0, 1, ManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getManyToMany_JoinTable(), this.getJoinTable(), null, "joinTable", null, 0, 1, ManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getManyToMany_Cascade(), this.getCascadeType(), null, "cascade", null, 0, 1, ManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(embeddedEClass, Embedded.class, "Embedded", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEmbedded_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Embedded.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEmbedded_AttributeOverrides(), this.getAttributeOverride(), null, "attributeOverrides", null, 0, -1, Embedded.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(transientEClass, Transient.class, "Transient", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTransient_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Transient.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(associationOverrideEClass, AssociationOverride.class, "AssociationOverride", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAssociationOverride_JoinColumn(), this.getJoinColumn(), null, "joinColumn", null, 1, -1, AssociationOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAssociationOverride_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, AssociationOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeOverrideEClass, AttributeOverride.class, "AttributeOverride", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeOverride_Column(), this.getColumn(), null, "column", null, 1, 1, AttributeOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeOverride_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, AttributeOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cascadeTypeEClass, CascadeType.class, "CascadeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCascadeType_CascadeAll(), this.getEmptyType(), null, "cascadeAll", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCascadeType_CascadePersist(), this.getEmptyType(), null, "cascadePersist", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCascadeType_CascadeMerge(), this.getEmptyType(), null, "cascadeMerge", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCascadeType_CascadeRemove(), this.getEmptyType(), null, "cascadeRemove", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCascadeType_CascadeRefresh(), this.getEmptyType(), null, "cascadeRefresh", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(columnEClass, Column.class, "Column", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getColumn_ColumnDefinition(), theXMLTypePackage.getString(), "columnDefinition", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Insertable(), theXMLTypePackage.getBoolean(), "insertable", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Length(), theXMLTypePackage.getInt(), "length", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Nullable(), theXMLTypePackage.getBoolean(), "nullable", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Precision(), theXMLTypePackage.getInt(), "precision", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Scale(), theXMLTypePackage.getInt(), "scale", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Table(), theXMLTypePackage.getString(), "table", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Unique(), theXMLTypePackage.getBoolean(), "unique", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Updatable(), theXMLTypePackage.getBoolean(), "updatable", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(columnResultEClass, ColumnResult.class, "ColumnResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getColumnResult_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ColumnResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(discriminatorColumnEClass, DiscriminatorColumn.class, "DiscriminatorColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDiscriminatorColumn_ColumnDefinition(), theXMLTypePackage.getString(), "columnDefinition", null, 0, 1, DiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiscriminatorColumn_DiscriminatorType(), this.getDiscriminatorType(), "discriminatorType", "STRING", 0, 1, DiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiscriminatorColumn_Length(), theXMLTypePackage.getInt(), "length", null, 0, 1, DiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiscriminatorColumn_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, DiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityListenersEClass, EntityListeners.class, "EntityListeners", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntityListeners_EntityListeners(), this.getEntityListener(), null, "entityListeners", null, 0, -1, EntityListeners.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityListenerEClass, EntityListener.class, "EntityListener", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntityListener_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, EntityListener.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityListener_PrePersist(), this.getPrePersist(), null, "prePersist", null, 0, 1, EntityListener.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityListener_PostPersist(), this.getPostPersist(), null, "postPersist", null, 0, 1, EntityListener.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityListener_PreRemove(), this.getPreRemove(), null, "preRemove", null, 0, 1, EntityListener.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityListener_PostRemove(), this.getPostRemove(), null, "postRemove", null, 0, 1, EntityListener.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityListener_PreUpdate(), this.getPreUpdate(), null, "preUpdate", null, 0, 1, EntityListener.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityListener_PostUpdate(), this.getPostUpdate(), null, "postUpdate", null, 0, 1, EntityListener.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityListener_PostLoad(), this.getPostLoad(), null, "postLoad", null, 0, 1, EntityListener.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityResultEClass, EntityResult.class, "EntityResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntityResult_DiscriminatorColumn(), theXMLTypePackage.getString(), "discriminatorColumn", null, 0, 1, EntityResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityResult_EntityClass(), theXMLTypePackage.getString(), "entityClass", null, 1, 1, EntityResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityResult_FieldResults(), this.getFieldResult(), null, "fieldResults", null, 0, -1, EntityResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventMethodEClass, EventMethod.class, "EventMethod", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEventMethod_MethodName(), theXMLTypePackage.getString(), "methodName", null, 1, 1, EventMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fieldResultEClass, FieldResult.class, "FieldResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFieldResult_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, FieldResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldResult_Column(), theXMLTypePackage.getString(), "column", null, 1, 1, FieldResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(generatedValueEClass, GeneratedValue.class, "GeneratedValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGeneratedValue_Generator(), theXMLTypePackage.getString(), "generator", null, 0, 1, GeneratedValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGeneratedValue_Strategy(), this.getGenerationType(), "strategy", "TABLE", 0, 1, GeneratedValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(idClassEClass, IdClass.class, "IdClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIdClass_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, IdClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inheritanceEClass, Inheritance.class, "Inheritance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInheritance_Strategy(), this.getInheritanceType(), "strategy", "SINGLE_TABLE", 0, 1, Inheritance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(joinColumnEClass, JoinColumn.class, "JoinColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJoinColumn_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, JoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJoinColumn_ReferencedColumnName(), theXMLTypePackage.getString(), "referencedColumnName", null, 0, 1, JoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJoinColumn_Unique(), theXMLTypePackage.getBoolean(), "unique", null, 0, 1, JoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJoinColumn_Nullable(), theXMLTypePackage.getBoolean(), "nullable", null, 0, 1, JoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJoinColumn_Insertable(), theXMLTypePackage.getBoolean(), "insertable", null, 0, 1, JoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJoinColumn_Updatable(), theXMLTypePackage.getBoolean(), "updatable", null, 0, 1, JoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJoinColumn_ColumnDefinition(), theXMLTypePackage.getString(), "columnDefinition", null, 0, 1, JoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJoinColumn_Table(), theXMLTypePackage.getString(), "table", null, 0, 1, JoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(joinTableEClass, JoinTable.class, "JoinTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJoinTable_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, JoinTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJoinTable_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, JoinTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJoinTable_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, JoinTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJoinTable_JoinColumns(), this.getJoinColumn(), null, "joinColumns", null, 0, -1, JoinTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJoinTable_InverseJoinColumns(), this.getJoinColumn(), null, "inverseJoinColumns", null, 0, -1, JoinTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJoinTable_UniqueConstraints(), this.getUniqueConstraint(), null, "uniqueConstraints", null, 0, -1, JoinTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(lobEClass, Lob.class, "Lob", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mapKeyEClass, MapKey.class, "MapKey", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapKey_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, MapKey.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(namedNativeQueryEClass, NamedNativeQuery.class, "NamedNativeQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamedNativeQuery_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, NamedNativeQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedNativeQuery_ResultClass(), theXMLTypePackage.getString(), "resultClass", null, 0, 1, NamedNativeQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedNativeQuery_ResultSetMapping(), theXMLTypePackage.getString(), "resultSetMapping", null, 0, 1, NamedNativeQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedNativeQuery_Query(), theXMLTypePackage.getString(), "query", null, 1, 1, NamedNativeQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNamedNativeQuery_Hints(), this.getQueryHint(), null, "hints", null, 0, -1, NamedNativeQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(namedQueryEClass, NamedQuery.class, "NamedQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamedQuery_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, NamedQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedQuery_Query(), theXMLTypePackage.getString(), "query", null, 1, 1, NamedQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNamedQuery_Hints(), this.getQueryHint(), null, "hints", null, 0, -1, NamedQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(postLoadEClass, PostLoad.class, "PostLoad", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(postPersistEClass, PostPersist.class, "PostPersist", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(postRemoveEClass, PostRemove.class, "PostRemove", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(postUpdateEClass, PostUpdate.class, "PostUpdate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(prePersistEClass, PrePersist.class, "PrePersist", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(preRemoveEClass, PreRemove.class, "PreRemove", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(preUpdateEClass, PreUpdate.class, "PreUpdate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(primaryKeyJoinColumnEClass, PrimaryKeyJoinColumn.class, "PrimaryKeyJoinColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPrimaryKeyJoinColumn_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, PrimaryKeyJoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrimaryKeyJoinColumn_ReferencedColumnName(), theXMLTypePackage.getString(), "referencedColumnName", null, 0, 1, PrimaryKeyJoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrimaryKeyJoinColumn_ColumnDefinition(), theXMLTypePackage.getString(), "columnDefinition", null, 0, 1, PrimaryKeyJoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(queryHintEClass, QueryHint.class, "QueryHint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQueryHint_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, QueryHint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueryHint_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, QueryHint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(secondaryTableEClass, SecondaryTable.class, "SecondaryTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSecondaryTable_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, SecondaryTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSecondaryTable_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, SecondaryTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSecondaryTable_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, SecondaryTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSecondaryTable_PrimaryKeyJoinColumns(), this.getPrimaryKeyJoinColumn(), null, "primaryKeyJoinColumns", null, 0, -1, SecondaryTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSecondaryTable_UniqueConstraints(), this.getUniqueConstraint(), null, "uniqueConstraints", null, 0, -1, SecondaryTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sequenceGeneratorEClass, SequenceGenerator.class, "SequenceGenerator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSequenceGenerator_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, SequenceGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSequenceGenerator_SequenceName(), theXMLTypePackage.getString(), "sequenceName", null, 0, 1, SequenceGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSequenceGenerator_InitialValue(), theXMLTypePackage.getInt(), "initialValue", null, 0, 1, SequenceGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSequenceGenerator_AllocationSize(), theXMLTypePackage.getInt(), "allocationSize", null, 0, 1, SequenceGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sqlResultSetMappingEClass, SqlResultSetMapping.class, "SqlResultSetMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSqlResultSetMapping_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, SqlResultSetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSqlResultSetMapping_EntityResults(), this.getEntityResult(), null, "entityResults", null, 0, -1, SqlResultSetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSqlResultSetMapping_ColumnResults(), this.getColumnResult(), null, "columnResults", null, 0, -1, SqlResultSetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tableEClass, Table.class, "Table", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTable_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, Table.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTable_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, Table.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTable_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, Table.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTable_UniqueConstraints(), this.getUniqueConstraint(), null, "uniqueConstraints", null, 0, -1, Table.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tableGeneratorEClass, TableGenerator.class, "TableGenerator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTableGenerator_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableGenerator_Table(), theXMLTypePackage.getString(), "table", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableGenerator_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableGenerator_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableGenerator_PkColumnName(), theXMLTypePackage.getString(), "pkColumnName", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableGenerator_ValueColumnName(), theXMLTypePackage.getString(), "valueColumnName", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableGenerator_PkColumnValue(), theXMLTypePackage.getString(), "pkColumnValue", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableGenerator_InitialValue(), theXMLTypePackage.getInt(), "initialValue", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableGenerator_AllocationSize(), theXMLTypePackage.getInt(), "allocationSize", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTableGenerator_UniqueConstraints(), this.getUniqueConstraint(), null, "uniqueConstraints", null, 0, -1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uniqueConstraintEClass, UniqueConstraint.class, "UniqueConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUniqueConstraint_ColumnNames(), theXMLTypePackage.getString(), "columnNames", null, 1, -1, UniqueConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(emptyTypeEClass, EmptyType.class, "EmptyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(accessTypeEEnum, AccessType.class, "AccessType");
		addEEnumLiteral(accessTypeEEnum, AccessType.PROPERTY);
		addEEnumLiteral(accessTypeEEnum, AccessType.FIELD);

		initEEnum(discriminatorTypeEEnum, DiscriminatorType.class, "DiscriminatorType");
		addEEnumLiteral(discriminatorTypeEEnum, DiscriminatorType.STRING);
		addEEnumLiteral(discriminatorTypeEEnum, DiscriminatorType.CHAR);
		addEEnumLiteral(discriminatorTypeEEnum, DiscriminatorType.INTEGER);

		initEEnum(enumTypeEEnum, EnumType.class, "EnumType");
		addEEnumLiteral(enumTypeEEnum, EnumType.ORDINAL);
		addEEnumLiteral(enumTypeEEnum, EnumType.STRING);

		initEEnum(fetchTypeEEnum, FetchType.class, "FetchType");
		addEEnumLiteral(fetchTypeEEnum, FetchType.LAZY);
		addEEnumLiteral(fetchTypeEEnum, FetchType.EAGER);

		initEEnum(generationTypeEEnum, GenerationType.class, "GenerationType");
		addEEnumLiteral(generationTypeEEnum, GenerationType.TABLE);
		addEEnumLiteral(generationTypeEEnum, GenerationType.SEQUENCE);
		addEEnumLiteral(generationTypeEEnum, GenerationType.IDENTITY);
		addEEnumLiteral(generationTypeEEnum, GenerationType.AUTO);

		initEEnum(inheritanceTypeEEnum, InheritanceType.class, "InheritanceType");
		addEEnumLiteral(inheritanceTypeEEnum, InheritanceType.SINGLETABLE);
		addEEnumLiteral(inheritanceTypeEEnum, InheritanceType.JOINED);
		addEEnumLiteral(inheritanceTypeEEnum, InheritanceType.TABLEPERCLASS);

		initEEnum(temporalTypeEEnum, TemporalType.class, "TemporalType");
		addEEnumLiteral(temporalTypeEEnum, TemporalType.DATE);
		addEEnumLiteral(temporalTypeEEnum, TemporalType.TIME);
		addEEnumLiteral(temporalTypeEEnum, TemporalType.TIMESTAMP);

		// Initialize data types
		initEDataType(accessTypeObjectEDataType, Enumerator.class, "AccessTypeObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(discriminatorTypeObjectEDataType, Enumerator.class, "DiscriminatorTypeObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(discriminatorValueEDataType, String.class, "DiscriminatorValue", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(enumeratedEDataType, Enumerator.class, "Enumerated", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(enumTypeObjectEDataType, Enumerator.class, "EnumTypeObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(fetchTypeObjectEDataType, Enumerator.class, "FetchTypeObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(generationTypeObjectEDataType, Enumerator.class, "GenerationTypeObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(inheritanceTypeObjectEDataType, Enumerator.class, "InheritanceTypeObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(orderByEDataType, String.class, "OrderBy", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(temporalEDataType, Enumerator.class, "Temporal", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(temporalTypeObjectEDataType, Enumerator.class, "TemporalTypeObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(versionTypeEDataType, String.class, "VersionType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public interface Literals
	{
		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings <em>Entity Mappings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings()
		 * @generated
		 */
		public static final EClass ENTITY_MAPPINGS = eINSTANCE.getEntityMappings();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS__VERSION = eINSTANCE.getEntityMappings_Version();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS__DESCRIPTION = eINSTANCE.getEntityMappings_Description();

		/**
		 * The meta object literal for the '<em><b>Persistence Unit Metadata</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA = eINSTANCE.getEntityMappings_PersistenceUnitMetadata();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS__PACKAGE = eINSTANCE.getEntityMappings_Package();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS__SCHEMA = eINSTANCE.getEntityMappings_Schema();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS__CATALOG = eINSTANCE.getEntityMappings_Catalog();

		/**
		 * The meta object literal for the '<em><b>Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS__ACCESS = eINSTANCE.getEntityMappings_Access();

		/**
		 * The meta object literal for the '<em><b>Sequence Generators</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS__SEQUENCE_GENERATORS = eINSTANCE.getEntityMappings_SequenceGenerators();

		/**
		 * The meta object literal for the '<em><b>Table Generators</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS__TABLE_GENERATORS = eINSTANCE.getEntityMappings_TableGenerators();

		/**
		 * The meta object literal for the '<em><b>Named Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS__NAMED_QUERIES = eINSTANCE.getEntityMappings_NamedQueries();

		/**
		 * The meta object literal for the '<em><b>Named Native Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES = eINSTANCE.getEntityMappings_NamedNativeQueries();

		/**
		 * The meta object literal for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS = eINSTANCE.getEntityMappings_SqlResultSetMappings();

		/**
		 * The meta object literal for the '<em><b>Mapped Superclasses</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS__MAPPED_SUPERCLASSES = eINSTANCE.getEntityMappings_MappedSuperclasses();

		/**
		 * The meta object literal for the '<em><b>Entities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS__ENTITIES = eINSTANCE.getEntityMappings_Entities();

		/**
		 * The meta object literal for the '<em><b>Embeddables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS__EMBEDDABLES = eINSTANCE.getEntityMappings_Embeddables();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata <em>Persistence Unit Metadata</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPersistenceUnitMetadata()
		 * @generated
		 */
		public static final EClass PERSISTENCE_UNIT_METADATA = eINSTANCE.getPersistenceUnitMetadata();

		/**
		 * The meta object literal for the '<em><b>Xml Mapping Metadata Complete</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE = eINSTANCE.getPersistenceUnitMetadata_XmlMappingMetadataComplete();

		/**
		 * The meta object literal for the '<em><b>Persistence Unit Defaults</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS = eINSTANCE.getPersistenceUnitMetadata_PersistenceUnitDefaults();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults <em>Persistence Unit Defaults</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPersistenceUnitDefaults()
		 * @generated
		 */
		public static final EClass PERSISTENCE_UNIT_DEFAULTS = eINSTANCE.getPersistenceUnitDefaults();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_DEFAULTS__SCHEMA = eINSTANCE.getPersistenceUnitDefaults_Schema();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_DEFAULTS__CATALOG = eINSTANCE.getPersistenceUnitDefaults_Catalog();

		/**
		 * The meta object literal for the '<em><b>Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_DEFAULTS__ACCESS = eINSTANCE.getPersistenceUnitDefaults_Access();

		/**
		 * The meta object literal for the '<em><b>Cascade Persist</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST = eINSTANCE.getPersistenceUnitDefaults_CascadePersist();

		/**
		 * The meta object literal for the '<em><b>Entity Listeners</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS = eINSTANCE.getPersistenceUnitDefaults_EntityListeners();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass <em>Mapped Superclass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getMappedSuperclass()
		 * @generated
		 */
		public static final EClass MAPPED_SUPERCLASS = eINSTANCE.getMappedSuperclass();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MAPPED_SUPERCLASS__CLASS_NAME = eINSTANCE.getMappedSuperclass_ClassName();

		/**
		 * The meta object literal for the '<em><b>Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MAPPED_SUPERCLASS__ACCESS = eINSTANCE.getMappedSuperclass_Access();

		/**
		 * The meta object literal for the '<em><b>Metadata Complete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MAPPED_SUPERCLASS__METADATA_COMPLETE = eINSTANCE.getMappedSuperclass_MetadataComplete();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MAPPED_SUPERCLASS__DESCRIPTION = eINSTANCE.getMappedSuperclass_Description();

		/**
		 * The meta object literal for the '<em><b>Id Class</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MAPPED_SUPERCLASS__ID_CLASS = eINSTANCE.getMappedSuperclass_IdClass();

		/**
		 * The meta object literal for the '<em><b>Exclude Default Listeners</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS = eINSTANCE.getMappedSuperclass_ExcludeDefaultListeners();

		/**
		 * The meta object literal for the '<em><b>Exclude Superclass Listeners</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS = eINSTANCE.getMappedSuperclass_ExcludeSuperclassListeners();

		/**
		 * The meta object literal for the '<em><b>Entity Listeners</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MAPPED_SUPERCLASS__ENTITY_LISTENERS = eINSTANCE.getMappedSuperclass_EntityListeners();

		/**
		 * The meta object literal for the '<em><b>Pre Persist</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MAPPED_SUPERCLASS__PRE_PERSIST = eINSTANCE.getMappedSuperclass_PrePersist();

		/**
		 * The meta object literal for the '<em><b>Post Persist</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MAPPED_SUPERCLASS__POST_PERSIST = eINSTANCE.getMappedSuperclass_PostPersist();

		/**
		 * The meta object literal for the '<em><b>Pre Remove</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MAPPED_SUPERCLASS__PRE_REMOVE = eINSTANCE.getMappedSuperclass_PreRemove();

		/**
		 * The meta object literal for the '<em><b>Post Remove</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MAPPED_SUPERCLASS__POST_REMOVE = eINSTANCE.getMappedSuperclass_PostRemove();

		/**
		 * The meta object literal for the '<em><b>Pre Update</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MAPPED_SUPERCLASS__PRE_UPDATE = eINSTANCE.getMappedSuperclass_PreUpdate();

		/**
		 * The meta object literal for the '<em><b>Post Update</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MAPPED_SUPERCLASS__POST_UPDATE = eINSTANCE.getMappedSuperclass_PostUpdate();

		/**
		 * The meta object literal for the '<em><b>Post Load</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MAPPED_SUPERCLASS__POST_LOAD = eINSTANCE.getMappedSuperclass_PostLoad();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MAPPED_SUPERCLASS__ATTRIBUTES = eINSTANCE.getMappedSuperclass_Attributes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity <em>Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Entity
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity()
		 * @generated
		 */
		public static final EClass ENTITY = eINSTANCE.getEntity();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY__NAME = eINSTANCE.getEntity_Name();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY__CLASS_NAME = eINSTANCE.getEntity_ClassName();

		/**
		 * The meta object literal for the '<em><b>Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY__ACCESS = eINSTANCE.getEntity_Access();

		/**
		 * The meta object literal for the '<em><b>Metadata Complete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY__METADATA_COMPLETE = eINSTANCE.getEntity_MetadataComplete();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY__DESCRIPTION = eINSTANCE.getEntity_Description();

		/**
		 * The meta object literal for the '<em><b>Tables</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__TABLES = eINSTANCE.getEntity_Tables();

		/**
		 * The meta object literal for the '<em><b>Secondary Tables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__SECONDARY_TABLES = eINSTANCE.getEntity_SecondaryTables();

		/**
		 * The meta object literal for the '<em><b>Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getEntity_PrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Id Class</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__ID_CLASS = eINSTANCE.getEntity_IdClass();

		/**
		 * The meta object literal for the '<em><b>Inheritance</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__INHERITANCE = eINSTANCE.getEntity_Inheritance();

		/**
		 * The meta object literal for the '<em><b>Discriminator Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY__DISCRIMINATOR_VALUE = eINSTANCE.getEntity_DiscriminatorValue();

		/**
		 * The meta object literal for the '<em><b>Discriminator Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__DISCRIMINATOR_COLUMN = eINSTANCE.getEntity_DiscriminatorColumn();

		/**
		 * The meta object literal for the '<em><b>Sequence Generator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__SEQUENCE_GENERATOR = eINSTANCE.getEntity_SequenceGenerator();

		/**
		 * The meta object literal for the '<em><b>Table Generator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__TABLE_GENERATOR = eINSTANCE.getEntity_TableGenerator();

		/**
		 * The meta object literal for the '<em><b>Named Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__NAMED_QUERIES = eINSTANCE.getEntity_NamedQueries();

		/**
		 * The meta object literal for the '<em><b>Named Native Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__NAMED_NATIVE_QUERIES = eINSTANCE.getEntity_NamedNativeQueries();

		/**
		 * The meta object literal for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__SQL_RESULT_SET_MAPPINGS = eINSTANCE.getEntity_SqlResultSetMappings();

		/**
		 * The meta object literal for the '<em><b>Exclude Default Listeners</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__EXCLUDE_DEFAULT_LISTENERS = eINSTANCE.getEntity_ExcludeDefaultListeners();

		/**
		 * The meta object literal for the '<em><b>Exclude Superclass Listeners</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__EXCLUDE_SUPERCLASS_LISTENERS = eINSTANCE.getEntity_ExcludeSuperclassListeners();

		/**
		 * The meta object literal for the '<em><b>Entity Listeners</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__ENTITY_LISTENERS = eINSTANCE.getEntity_EntityListeners();

		/**
		 * The meta object literal for the '<em><b>Pre Persist</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__PRE_PERSIST = eINSTANCE.getEntity_PrePersist();

		/**
		 * The meta object literal for the '<em><b>Post Persist</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__POST_PERSIST = eINSTANCE.getEntity_PostPersist();

		/**
		 * The meta object literal for the '<em><b>Pre Remove</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__PRE_REMOVE = eINSTANCE.getEntity_PreRemove();

		/**
		 * The meta object literal for the '<em><b>Post Remove</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__POST_REMOVE = eINSTANCE.getEntity_PostRemove();

		/**
		 * The meta object literal for the '<em><b>Pre Update</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__PRE_UPDATE = eINSTANCE.getEntity_PreUpdate();

		/**
		 * The meta object literal for the '<em><b>Post Update</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__POST_UPDATE = eINSTANCE.getEntity_PostUpdate();

		/**
		 * The meta object literal for the '<em><b>Post Load</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__POST_LOAD = eINSTANCE.getEntity_PostLoad();

		/**
		 * The meta object literal for the '<em><b>Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__ATTRIBUTE_OVERRIDES = eINSTANCE.getEntity_AttributeOverrides();

		/**
		 * The meta object literal for the '<em><b>Association Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__ASSOCIATION_OVERRIDES = eINSTANCE.getEntity_AssociationOverrides();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__ATTRIBUTES = eINSTANCE.getEntity_Attributes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Embeddable <em>Embeddable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Embeddable
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddable()
		 * @generated
		 */
		public static final EClass EMBEDDABLE = eINSTANCE.getEmbeddable();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EMBEDDABLE__CLASS_NAME = eINSTANCE.getEmbeddable_ClassName();

		/**
		 * The meta object literal for the '<em><b>Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EMBEDDABLE__ACCESS = eINSTANCE.getEmbeddable_Access();

		/**
		 * The meta object literal for the '<em><b>Metadata Complete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EMBEDDABLE__METADATA_COMPLETE = eINSTANCE.getEmbeddable_MetadataComplete();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EMBEDDABLE__DESCRIPTION = eINSTANCE.getEmbeddable_Description();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EMBEDDABLE__ATTRIBUTES = eINSTANCE.getEmbeddable_Attributes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes <em>Attributes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes()
		 * @generated
		 */
		public static final EClass ATTRIBUTES = eINSTANCE.getAttributes();

		/**
		 * The meta object literal for the '<em><b>Ids</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__IDS = eINSTANCE.getAttributes_Ids();

		/**
		 * The meta object literal for the '<em><b>Embedded Ids</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__EMBEDDED_IDS = eINSTANCE.getAttributes_EmbeddedIds();

		/**
		 * The meta object literal for the '<em><b>Basics</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__BASICS = eINSTANCE.getAttributes_Basics();

		/**
		 * The meta object literal for the '<em><b>Versions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__VERSIONS = eINSTANCE.getAttributes_Versions();

		/**
		 * The meta object literal for the '<em><b>Many To Ones</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__MANY_TO_ONES = eINSTANCE.getAttributes_ManyToOnes();

		/**
		 * The meta object literal for the '<em><b>One To Manies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__ONE_TO_MANIES = eINSTANCE.getAttributes_OneToManies();

		/**
		 * The meta object literal for the '<em><b>One To Ones</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__ONE_TO_ONES = eINSTANCE.getAttributes_OneToOnes();

		/**
		 * The meta object literal for the '<em><b>Many To Manies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__MANY_TO_MANIES = eINSTANCE.getAttributes_ManyToManies();

		/**
		 * The meta object literal for the '<em><b>Embeddeds</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__EMBEDDEDS = eINSTANCE.getAttributes_Embeddeds();

		/**
		 * The meta object literal for the '<em><b>Transients</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__TRANSIENTS = eINSTANCE.getAttributes_Transients();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes <em>Embeddable Attributes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddableAttributes()
		 * @generated
		 */
		public static final EClass EMBEDDABLE_ATTRIBUTES = eINSTANCE.getEmbeddableAttributes();

		/**
		 * The meta object literal for the '<em><b>Basics</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EMBEDDABLE_ATTRIBUTES__BASICS = eINSTANCE.getEmbeddableAttributes_Basics();

		/**
		 * The meta object literal for the '<em><b>Transients</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EMBEDDABLE_ATTRIBUTES__TRANSIENTS = eINSTANCE.getEmbeddableAttributes_Transients();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Id <em>Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Id
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getId()
		 * @generated
		 */
		public static final EClass ID = eINSTANCE.getId();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ID__NAME = eINSTANCE.getId_Name();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ID__COLUMN = eINSTANCE.getId_Column();

		/**
		 * The meta object literal for the '<em><b>Generated Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ID__GENERATED_VALUE = eINSTANCE.getId_GeneratedValue();

		/**
		 * The meta object literal for the '<em><b>Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ID__TEMPORAL = eINSTANCE.getId_Temporal();

		/**
		 * The meta object literal for the '<em><b>Table Generator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ID__TABLE_GENERATOR = eINSTANCE.getId_TableGenerator();

		/**
		 * The meta object literal for the '<em><b>Sequence Generator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ID__SEQUENCE_GENERATOR = eINSTANCE.getId_SequenceGenerator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedId <em>Embedded Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddedId
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddedId()
		 * @generated
		 */
		public static final EClass EMBEDDED_ID = eINSTANCE.getEmbeddedId();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EMBEDDED_ID__NAME = eINSTANCE.getEmbeddedId_Name();

		/**
		 * The meta object literal for the '<em><b>Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EMBEDDED_ID__ATTRIBUTE_OVERRIDES = eINSTANCE.getEmbeddedId_AttributeOverrides();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Basic <em>Basic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Basic
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getBasic()
		 * @generated
		 */
		public static final EClass BASIC = eINSTANCE.getBasic();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute BASIC__NAME = eINSTANCE.getBasic_Name();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute BASIC__FETCH = eINSTANCE.getBasic_Fetch();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute BASIC__OPTIONAL = eINSTANCE.getBasic_Optional();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference BASIC__COLUMN = eINSTANCE.getBasic_Column();

		/**
		 * The meta object literal for the '<em><b>Lob</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference BASIC__LOB = eINSTANCE.getBasic_Lob();

		/**
		 * The meta object literal for the '<em><b>Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute BASIC__TEMPORAL = eINSTANCE.getBasic_Temporal();

		/**
		 * The meta object literal for the '<em><b>Enumerated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute BASIC__ENUMERATED = eINSTANCE.getBasic_Enumerated();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Version <em>Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Version
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getVersion()
		 * @generated
		 */
		public static final EClass VERSION = eINSTANCE.getVersion();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute VERSION__NAME = eINSTANCE.getVersion_Name();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference VERSION__COLUMN = eINSTANCE.getVersion_Column();

		/**
		 * The meta object literal for the '<em><b>Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute VERSION__TEMPORAL = eINSTANCE.getVersion_Temporal();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOne <em>Many To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOne
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getManyToOne()
		 * @generated
		 */
		public static final EClass MANY_TO_ONE = eINSTANCE.getManyToOne();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MANY_TO_ONE__NAME = eINSTANCE.getManyToOne_Name();

		/**
		 * The meta object literal for the '<em><b>Target Entity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MANY_TO_ONE__TARGET_ENTITY = eINSTANCE.getManyToOne_TargetEntity();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MANY_TO_ONE__FETCH = eINSTANCE.getManyToOne_Fetch();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MANY_TO_ONE__OPTIONAL = eINSTANCE.getManyToOne_Optional();

		/**
		 * The meta object literal for the '<em><b>Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MANY_TO_ONE__JOIN_COLUMNS = eINSTANCE.getManyToOne_JoinColumns();

		/**
		 * The meta object literal for the '<em><b>Join Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MANY_TO_ONE__JOIN_TABLE = eINSTANCE.getManyToOne_JoinTable();

		/**
		 * The meta object literal for the '<em><b>Cascade</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MANY_TO_ONE__CASCADE = eINSTANCE.getManyToOne_Cascade();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany <em>One To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToMany()
		 * @generated
		 */
		public static final EClass ONE_TO_MANY = eINSTANCE.getOneToMany();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ONE_TO_MANY__NAME = eINSTANCE.getOneToMany_Name();

		/**
		 * The meta object literal for the '<em><b>Target Entity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ONE_TO_MANY__TARGET_ENTITY = eINSTANCE.getOneToMany_TargetEntity();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ONE_TO_MANY__FETCH = eINSTANCE.getOneToMany_Fetch();

		/**
		 * The meta object literal for the '<em><b>Mapped By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ONE_TO_MANY__MAPPED_BY = eINSTANCE.getOneToMany_MappedBy();

		/**
		 * The meta object literal for the '<em><b>Order By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ONE_TO_MANY__ORDER_BY = eINSTANCE.getOneToMany_OrderBy();

		/**
		 * The meta object literal for the '<em><b>Map Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ONE_TO_MANY__MAP_KEY = eINSTANCE.getOneToMany_MapKey();

		/**
		 * The meta object literal for the '<em><b>Join Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ONE_TO_MANY__JOIN_TABLE = eINSTANCE.getOneToMany_JoinTable();

		/**
		 * The meta object literal for the '<em><b>Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ONE_TO_MANY__JOIN_COLUMNS = eINSTANCE.getOneToMany_JoinColumns();

		/**
		 * The meta object literal for the '<em><b>Cascade</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ONE_TO_MANY__CASCADE = eINSTANCE.getOneToMany_Cascade();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne <em>One To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOne()
		 * @generated
		 */
		public static final EClass ONE_TO_ONE = eINSTANCE.getOneToOne();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ONE_TO_ONE__NAME = eINSTANCE.getOneToOne_Name();

		/**
		 * The meta object literal for the '<em><b>Target Entity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ONE_TO_ONE__TARGET_ENTITY = eINSTANCE.getOneToOne_TargetEntity();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ONE_TO_ONE__FETCH = eINSTANCE.getOneToOne_Fetch();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ONE_TO_ONE__OPTIONAL = eINSTANCE.getOneToOne_Optional();

		/**
		 * The meta object literal for the '<em><b>Mapped By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ONE_TO_ONE__MAPPED_BY = eINSTANCE.getOneToOne_MappedBy();

		/**
		 * The meta object literal for the '<em><b>Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getOneToOne_PrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ONE_TO_ONE__JOIN_COLUMNS = eINSTANCE.getOneToOne_JoinColumns();

		/**
		 * The meta object literal for the '<em><b>Join Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ONE_TO_ONE__JOIN_TABLE = eINSTANCE.getOneToOne_JoinTable();

		/**
		 * The meta object literal for the '<em><b>Cascade</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ONE_TO_ONE__CASCADE = eINSTANCE.getOneToOne_Cascade();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany <em>Many To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToMany
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getManyToMany()
		 * @generated
		 */
		public static final EClass MANY_TO_MANY = eINSTANCE.getManyToMany();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MANY_TO_MANY__NAME = eINSTANCE.getManyToMany_Name();

		/**
		 * The meta object literal for the '<em><b>Target Entity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MANY_TO_MANY__TARGET_ENTITY = eINSTANCE.getManyToMany_TargetEntity();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MANY_TO_MANY__FETCH = eINSTANCE.getManyToMany_Fetch();

		/**
		 * The meta object literal for the '<em><b>Mapped By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MANY_TO_MANY__MAPPED_BY = eINSTANCE.getManyToMany_MappedBy();

		/**
		 * The meta object literal for the '<em><b>Order By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MANY_TO_MANY__ORDER_BY = eINSTANCE.getManyToMany_OrderBy();

		/**
		 * The meta object literal for the '<em><b>Map Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MANY_TO_MANY__MAP_KEY = eINSTANCE.getManyToMany_MapKey();

		/**
		 * The meta object literal for the '<em><b>Join Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MANY_TO_MANY__JOIN_TABLE = eINSTANCE.getManyToMany_JoinTable();

		/**
		 * The meta object literal for the '<em><b>Cascade</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MANY_TO_MANY__CASCADE = eINSTANCE.getManyToMany_Cascade();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Embedded <em>Embedded</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Embedded
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbedded()
		 * @generated
		 */
		public static final EClass EMBEDDED = eINSTANCE.getEmbedded();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EMBEDDED__NAME = eINSTANCE.getEmbedded_Name();

		/**
		 * The meta object literal for the '<em><b>Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EMBEDDED__ATTRIBUTE_OVERRIDES = eINSTANCE.getEmbedded_AttributeOverrides();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Transient <em>Transient</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Transient
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTransient()
		 * @generated
		 */
		public static final EClass TRANSIENT = eINSTANCE.getTransient();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TRANSIENT__NAME = eINSTANCE.getTransient_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.AssociationOverride <em>Association Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.AssociationOverride
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAssociationOverride()
		 * @generated
		 */
		public static final EClass ASSOCIATION_OVERRIDE = eINSTANCE.getAssociationOverride();

		/**
		 * The meta object literal for the '<em><b>Join Column</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ASSOCIATION_OVERRIDE__JOIN_COLUMN = eINSTANCE.getAssociationOverride_JoinColumn();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ASSOCIATION_OVERRIDE__NAME = eINSTANCE.getAssociationOverride_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.AttributeOverride <em>Attribute Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.AttributeOverride
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributeOverride()
		 * @generated
		 */
		public static final EClass ATTRIBUTE_OVERRIDE = eINSTANCE.getAttributeOverride();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTE_OVERRIDE__COLUMN = eINSTANCE.getAttributeOverride_Column();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ATTRIBUTE_OVERRIDE__NAME = eINSTANCE.getAttributeOverride_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType <em>Cascade Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getCascadeType()
		 * @generated
		 */
		public static final EClass CASCADE_TYPE = eINSTANCE.getCascadeType();

		/**
		 * The meta object literal for the '<em><b>Cascade All</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference CASCADE_TYPE__CASCADE_ALL = eINSTANCE.getCascadeType_CascadeAll();

		/**
		 * The meta object literal for the '<em><b>Cascade Persist</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference CASCADE_TYPE__CASCADE_PERSIST = eINSTANCE.getCascadeType_CascadePersist();

		/**
		 * The meta object literal for the '<em><b>Cascade Merge</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference CASCADE_TYPE__CASCADE_MERGE = eINSTANCE.getCascadeType_CascadeMerge();

		/**
		 * The meta object literal for the '<em><b>Cascade Remove</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference CASCADE_TYPE__CASCADE_REMOVE = eINSTANCE.getCascadeType_CascadeRemove();

		/**
		 * The meta object literal for the '<em><b>Cascade Refresh</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference CASCADE_TYPE__CASCADE_REFRESH = eINSTANCE.getCascadeType_CascadeRefresh();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Column <em>Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Column
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getColumn()
		 * @generated
		 */
		public static final EClass COLUMN = eINSTANCE.getColumn();

		/**
		 * The meta object literal for the '<em><b>Column Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN__COLUMN_DEFINITION = eINSTANCE.getColumn_ColumnDefinition();

		/**
		 * The meta object literal for the '<em><b>Insertable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN__INSERTABLE = eINSTANCE.getColumn_Insertable();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN__LENGTH = eINSTANCE.getColumn_Length();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN__NAME = eINSTANCE.getColumn_Name();

		/**
		 * The meta object literal for the '<em><b>Nullable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN__NULLABLE = eINSTANCE.getColumn_Nullable();

		/**
		 * The meta object literal for the '<em><b>Precision</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN__PRECISION = eINSTANCE.getColumn_Precision();

		/**
		 * The meta object literal for the '<em><b>Scale</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN__SCALE = eINSTANCE.getColumn_Scale();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN__TABLE = eINSTANCE.getColumn_Table();

		/**
		 * The meta object literal for the '<em><b>Unique</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN__UNIQUE = eINSTANCE.getColumn_Unique();

		/**
		 * The meta object literal for the '<em><b>Updatable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN__UPDATABLE = eINSTANCE.getColumn_Updatable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.ColumnResult <em>Column Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.ColumnResult
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getColumnResult()
		 * @generated
		 */
		public static final EClass COLUMN_RESULT = eINSTANCE.getColumnResult();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN_RESULT__NAME = eINSTANCE.getColumnResult_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn <em>Discriminator Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getDiscriminatorColumn()
		 * @generated
		 */
		public static final EClass DISCRIMINATOR_COLUMN = eINSTANCE.getDiscriminatorColumn();

		/**
		 * The meta object literal for the '<em><b>Column Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute DISCRIMINATOR_COLUMN__COLUMN_DEFINITION = eINSTANCE.getDiscriminatorColumn_ColumnDefinition();

		/**
		 * The meta object literal for the '<em><b>Discriminator Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = eINSTANCE.getDiscriminatorColumn_DiscriminatorType();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute DISCRIMINATOR_COLUMN__LENGTH = eINSTANCE.getDiscriminatorColumn_Length();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute DISCRIMINATOR_COLUMN__NAME = eINSTANCE.getDiscriminatorColumn_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListeners <em>Entity Listeners</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListeners
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityListeners()
		 * @generated
		 */
		public static final EClass ENTITY_LISTENERS = eINSTANCE.getEntityListeners();

		/**
		 * The meta object literal for the '<em><b>Entity Listeners</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_LISTENERS__ENTITY_LISTENERS = eINSTANCE.getEntityListeners_EntityListeners();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListener <em>Entity Listener</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListener
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityListener()
		 * @generated
		 */
		public static final EClass ENTITY_LISTENER = eINSTANCE.getEntityListener();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_LISTENER__CLASS_NAME = eINSTANCE.getEntityListener_ClassName();

		/**
		 * The meta object literal for the '<em><b>Pre Persist</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_LISTENER__PRE_PERSIST = eINSTANCE.getEntityListener_PrePersist();

		/**
		 * The meta object literal for the '<em><b>Post Persist</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_LISTENER__POST_PERSIST = eINSTANCE.getEntityListener_PostPersist();

		/**
		 * The meta object literal for the '<em><b>Pre Remove</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_LISTENER__PRE_REMOVE = eINSTANCE.getEntityListener_PreRemove();

		/**
		 * The meta object literal for the '<em><b>Post Remove</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_LISTENER__POST_REMOVE = eINSTANCE.getEntityListener_PostRemove();

		/**
		 * The meta object literal for the '<em><b>Pre Update</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_LISTENER__PRE_UPDATE = eINSTANCE.getEntityListener_PreUpdate();

		/**
		 * The meta object literal for the '<em><b>Post Update</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_LISTENER__POST_UPDATE = eINSTANCE.getEntityListener_PostUpdate();

		/**
		 * The meta object literal for the '<em><b>Post Load</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_LISTENER__POST_LOAD = eINSTANCE.getEntityListener_PostLoad();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityResult <em>Entity Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.EntityResult
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityResult()
		 * @generated
		 */
		public static final EClass ENTITY_RESULT = eINSTANCE.getEntityResult();

		/**
		 * The meta object literal for the '<em><b>Discriminator Column</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_RESULT__DISCRIMINATOR_COLUMN = eINSTANCE.getEntityResult_DiscriminatorColumn();

		/**
		 * The meta object literal for the '<em><b>Entity Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_RESULT__ENTITY_CLASS = eINSTANCE.getEntityResult_EntityClass();

		/**
		 * The meta object literal for the '<em><b>Field Results</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_RESULT__FIELD_RESULTS = eINSTANCE.getEntityResult_FieldResults();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.FieldResult <em>Field Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.FieldResult
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getFieldResult()
		 * @generated
		 */
		public static final EClass FIELD_RESULT = eINSTANCE.getFieldResult();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute FIELD_RESULT__NAME = eINSTANCE.getFieldResult_Name();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute FIELD_RESULT__COLUMN = eINSTANCE.getFieldResult_Column();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValue <em>Generated Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.GeneratedValue
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGeneratedValue()
		 * @generated
		 */
		public static final EClass GENERATED_VALUE = eINSTANCE.getGeneratedValue();

		/**
		 * The meta object literal for the '<em><b>Generator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute GENERATED_VALUE__GENERATOR = eINSTANCE.getGeneratedValue_Generator();

		/**
		 * The meta object literal for the '<em><b>Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute GENERATED_VALUE__STRATEGY = eINSTANCE.getGeneratedValue_Strategy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.IdClass <em>Id Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.IdClass
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getIdClass()
		 * @generated
		 */
		public static final EClass ID_CLASS = eINSTANCE.getIdClass();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ID_CLASS__CLASS_NAME = eINSTANCE.getIdClass_ClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Inheritance <em>Inheritance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Inheritance
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getInheritance()
		 * @generated
		 */
		public static final EClass INHERITANCE = eINSTANCE.getInheritance();

		/**
		 * The meta object literal for the '<em><b>Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute INHERITANCE__STRATEGY = eINSTANCE.getInheritance_Strategy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn <em>Join Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumn
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getJoinColumn()
		 * @generated
		 */
		public static final EClass JOIN_COLUMN = eINSTANCE.getJoinColumn();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JOIN_COLUMN__NAME = eINSTANCE.getJoinColumn_Name();

		/**
		 * The meta object literal for the '<em><b>Referenced Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JOIN_COLUMN__REFERENCED_COLUMN_NAME = eINSTANCE.getJoinColumn_ReferencedColumnName();

		/**
		 * The meta object literal for the '<em><b>Unique</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JOIN_COLUMN__UNIQUE = eINSTANCE.getJoinColumn_Unique();

		/**
		 * The meta object literal for the '<em><b>Nullable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JOIN_COLUMN__NULLABLE = eINSTANCE.getJoinColumn_Nullable();

		/**
		 * The meta object literal for the '<em><b>Insertable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JOIN_COLUMN__INSERTABLE = eINSTANCE.getJoinColumn_Insertable();

		/**
		 * The meta object literal for the '<em><b>Updatable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JOIN_COLUMN__UPDATABLE = eINSTANCE.getJoinColumn_Updatable();

		/**
		 * The meta object literal for the '<em><b>Column Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JOIN_COLUMN__COLUMN_DEFINITION = eINSTANCE.getJoinColumn_ColumnDefinition();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JOIN_COLUMN__TABLE = eINSTANCE.getJoinColumn_Table();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTable <em>Join Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTable
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getJoinTable()
		 * @generated
		 */
		public static final EClass JOIN_TABLE = eINSTANCE.getJoinTable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JOIN_TABLE__NAME = eINSTANCE.getJoinTable_Name();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JOIN_TABLE__CATALOG = eINSTANCE.getJoinTable_Catalog();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JOIN_TABLE__SCHEMA = eINSTANCE.getJoinTable_Schema();

		/**
		 * The meta object literal for the '<em><b>Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference JOIN_TABLE__JOIN_COLUMNS = eINSTANCE.getJoinTable_JoinColumns();

		/**
		 * The meta object literal for the '<em><b>Inverse Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference JOIN_TABLE__INVERSE_JOIN_COLUMNS = eINSTANCE.getJoinTable_InverseJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Unique Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference JOIN_TABLE__UNIQUE_CONSTRAINTS = eINSTANCE.getJoinTable_UniqueConstraints();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Lob <em>Lob</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Lob
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getLob()
		 * @generated
		 */
		public static final EClass LOB = eINSTANCE.getLob();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.MapKey <em>Map Key</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.MapKey
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getMapKey()
		 * @generated
		 */
		public static final EClass MAP_KEY = eINSTANCE.getMapKey();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MAP_KEY__NAME = eINSTANCE.getMapKey_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.EventMethod <em>Event Method</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.EventMethod
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEventMethod()
		 * @generated
		 */
		public static final EClass EVENT_METHOD = eINSTANCE.getEventMethod();

		/**
		 * The meta object literal for the '<em><b>Method Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EVENT_METHOD__METHOD_NAME = eINSTANCE.getEventMethod_MethodName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery <em>Named Native Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getNamedNativeQuery()
		 * @generated
		 */
		public static final EClass NAMED_NATIVE_QUERY = eINSTANCE.getNamedNativeQuery();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute NAMED_NATIVE_QUERY__NAME = eINSTANCE.getNamedNativeQuery_Name();

		/**
		 * The meta object literal for the '<em><b>Result Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute NAMED_NATIVE_QUERY__RESULT_CLASS = eINSTANCE.getNamedNativeQuery_ResultClass();

		/**
		 * The meta object literal for the '<em><b>Result Set Mapping</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute NAMED_NATIVE_QUERY__RESULT_SET_MAPPING = eINSTANCE.getNamedNativeQuery_ResultSetMapping();

		/**
		 * The meta object literal for the '<em><b>Query</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute NAMED_NATIVE_QUERY__QUERY = eINSTANCE.getNamedNativeQuery_Query();

		/**
		 * The meta object literal for the '<em><b>Hints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference NAMED_NATIVE_QUERY__HINTS = eINSTANCE.getNamedNativeQuery_Hints();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.NamedQuery <em>Named Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.NamedQuery
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getNamedQuery()
		 * @generated
		 */
		public static final EClass NAMED_QUERY = eINSTANCE.getNamedQuery();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute NAMED_QUERY__NAME = eINSTANCE.getNamedQuery_Name();

		/**
		 * The meta object literal for the '<em><b>Query</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute NAMED_QUERY__QUERY = eINSTANCE.getNamedQuery_Query();

		/**
		 * The meta object literal for the '<em><b>Hints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference NAMED_QUERY__HINTS = eINSTANCE.getNamedQuery_Hints();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.PostLoad <em>Post Load</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.PostLoad
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPostLoad()
		 * @generated
		 */
		public static final EClass POST_LOAD = eINSTANCE.getPostLoad();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.PostPersist <em>Post Persist</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.PostPersist
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPostPersist()
		 * @generated
		 */
		public static final EClass POST_PERSIST = eINSTANCE.getPostPersist();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.PostRemove <em>Post Remove</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.PostRemove
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPostRemove()
		 * @generated
		 */
		public static final EClass POST_REMOVE = eINSTANCE.getPostRemove();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.PostUpdate <em>Post Update</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.PostUpdate
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPostUpdate()
		 * @generated
		 */
		public static final EClass POST_UPDATE = eINSTANCE.getPostUpdate();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.PrePersist <em>Pre Persist</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.PrePersist
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPrePersist()
		 * @generated
		 */
		public static final EClass PRE_PERSIST = eINSTANCE.getPrePersist();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.PreRemove <em>Pre Remove</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.PreRemove
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPreRemove()
		 * @generated
		 */
		public static final EClass PRE_REMOVE = eINSTANCE.getPreRemove();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.PreUpdate <em>Pre Update</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.PreUpdate
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPreUpdate()
		 * @generated
		 */
		public static final EClass PRE_UPDATE = eINSTANCE.getPreUpdate();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.QueryHint <em>Query Hint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.QueryHint
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getQueryHint()
		 * @generated
		 */
		public static final EClass QUERY_HINT = eINSTANCE.getQueryHint();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute QUERY_HINT__NAME = eINSTANCE.getQueryHint_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute QUERY_HINT__VALUE = eINSTANCE.getQueryHint_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping <em>Sql Result Set Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSqlResultSetMapping()
		 * @generated
		 */
		public static final EClass SQL_RESULT_SET_MAPPING = eINSTANCE.getSqlResultSetMapping();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute SQL_RESULT_SET_MAPPING__NAME = eINSTANCE.getSqlResultSetMapping_Name();

		/**
		 * The meta object literal for the '<em><b>Entity Results</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference SQL_RESULT_SET_MAPPING__ENTITY_RESULTS = eINSTANCE.getSqlResultSetMapping_EntityResults();

		/**
		 * The meta object literal for the '<em><b>Column Results</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference SQL_RESULT_SET_MAPPING__COLUMN_RESULTS = eINSTANCE.getSqlResultSetMapping_ColumnResults();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn <em>Primary Key Join Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPrimaryKeyJoinColumn()
		 * @generated
		 */
		public static final EClass PRIMARY_KEY_JOIN_COLUMN = eINSTANCE.getPrimaryKeyJoinColumn();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PRIMARY_KEY_JOIN_COLUMN__NAME = eINSTANCE.getPrimaryKeyJoinColumn_Name();

		/**
		 * The meta object literal for the '<em><b>Referenced Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = eINSTANCE.getPrimaryKeyJoinColumn_ReferencedColumnName();

		/**
		 * The meta object literal for the '<em><b>Column Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION = eINSTANCE.getPrimaryKeyJoinColumn_ColumnDefinition();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.SecondaryTable <em>Secondary Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.SecondaryTable
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSecondaryTable()
		 * @generated
		 */
		public static final EClass SECONDARY_TABLE = eINSTANCE.getSecondaryTable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute SECONDARY_TABLE__NAME = eINSTANCE.getSecondaryTable_Name();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute SECONDARY_TABLE__CATALOG = eINSTANCE.getSecondaryTable_Catalog();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute SECONDARY_TABLE__SCHEMA = eINSTANCE.getSecondaryTable_Schema();

		/**
		 * The meta object literal for the '<em><b>Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getSecondaryTable_PrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Unique Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference SECONDARY_TABLE__UNIQUE_CONSTRAINTS = eINSTANCE.getSecondaryTable_UniqueConstraints();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator <em>Sequence Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSequenceGenerator()
		 * @generated
		 */
		public static final EClass SEQUENCE_GENERATOR = eINSTANCE.getSequenceGenerator();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute SEQUENCE_GENERATOR__NAME = eINSTANCE.getSequenceGenerator_Name();

		/**
		 * The meta object literal for the '<em><b>Sequence Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute SEQUENCE_GENERATOR__SEQUENCE_NAME = eINSTANCE.getSequenceGenerator_SequenceName();

		/**
		 * The meta object literal for the '<em><b>Initial Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute SEQUENCE_GENERATOR__INITIAL_VALUE = eINSTANCE.getSequenceGenerator_InitialValue();

		/**
		 * The meta object literal for the '<em><b>Allocation Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute SEQUENCE_GENERATOR__ALLOCATION_SIZE = eINSTANCE.getSequenceGenerator_AllocationSize();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Table <em>Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Table
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTable()
		 * @generated
		 */
		public static final EClass TABLE = eINSTANCE.getTable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TABLE__NAME = eINSTANCE.getTable_Name();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TABLE__CATALOG = eINSTANCE.getTable_Catalog();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TABLE__SCHEMA = eINSTANCE.getTable_Schema();

		/**
		 * The meta object literal for the '<em><b>Unique Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference TABLE__UNIQUE_CONSTRAINTS = eINSTANCE.getTable_UniqueConstraints();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator <em>Table Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator()
		 * @generated
		 */
		public static final EClass TABLE_GENERATOR = eINSTANCE.getTableGenerator();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TABLE_GENERATOR__NAME = eINSTANCE.getTableGenerator_Name();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TABLE_GENERATOR__TABLE = eINSTANCE.getTableGenerator_Table();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TABLE_GENERATOR__CATALOG = eINSTANCE.getTableGenerator_Catalog();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TABLE_GENERATOR__SCHEMA = eINSTANCE.getTableGenerator_Schema();

		/**
		 * The meta object literal for the '<em><b>Pk Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TABLE_GENERATOR__PK_COLUMN_NAME = eINSTANCE.getTableGenerator_PkColumnName();

		/**
		 * The meta object literal for the '<em><b>Value Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TABLE_GENERATOR__VALUE_COLUMN_NAME = eINSTANCE.getTableGenerator_ValueColumnName();

		/**
		 * The meta object literal for the '<em><b>Pk Column Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TABLE_GENERATOR__PK_COLUMN_VALUE = eINSTANCE.getTableGenerator_PkColumnValue();

		/**
		 * The meta object literal for the '<em><b>Initial Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TABLE_GENERATOR__INITIAL_VALUE = eINSTANCE.getTableGenerator_InitialValue();

		/**
		 * The meta object literal for the '<em><b>Allocation Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TABLE_GENERATOR__ALLOCATION_SIZE = eINSTANCE.getTableGenerator_AllocationSize();

		/**
		 * The meta object literal for the '<em><b>Unique Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference TABLE_GENERATOR__UNIQUE_CONSTRAINTS = eINSTANCE.getTableGenerator_UniqueConstraints();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint <em>Unique Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getUniqueConstraint()
		 * @generated
		 */
		public static final EClass UNIQUE_CONSTRAINT = eINSTANCE.getUniqueConstraint();

		/**
		 * The meta object literal for the '<em><b>Column Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute UNIQUE_CONSTRAINT__COLUMN_NAMES = eINSTANCE.getUniqueConstraint_ColumnNames();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.EmptyType <em>Empty Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.EmptyType
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmptyType()
		 * @generated
		 */
		public static final EClass EMPTY_TYPE = eINSTANCE.getEmptyType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.AccessType <em>Access Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.AccessType
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAccessType()
		 * @generated
		 */
		public static final EEnum ACCESS_TYPE = eINSTANCE.getAccessType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType <em>Discriminator Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getDiscriminatorType()
		 * @generated
		 */
		public static final EEnum DISCRIMINATOR_TYPE = eINSTANCE.getDiscriminatorType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.EnumType <em>Enum Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.EnumType
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEnumType()
		 * @generated
		 */
		public static final EEnum ENUM_TYPE = eINSTANCE.getEnumType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.FetchType <em>Fetch Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.FetchType
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getFetchType()
		 * @generated
		 */
		public static final EEnum FETCH_TYPE = eINSTANCE.getFetchType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.GenerationType <em>Generation Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.GenerationType
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGenerationType()
		 * @generated
		 */
		public static final EEnum GENERATION_TYPE = eINSTANCE.getGenerationType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.InheritanceType <em>Inheritance Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.InheritanceType
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getInheritanceType()
		 * @generated
		 */
		public static final EEnum INHERITANCE_TYPE = eINSTANCE.getInheritanceType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.TemporalType <em>Temporal Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.TemporalType
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTemporalType()
		 * @generated
		 */
		public static final EEnum TEMPORAL_TYPE = eINSTANCE.getTemporalType();

		/**
		 * The meta object literal for the '<em>Access Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAccessTypeObject()
		 * @generated
		 */
		public static final EDataType ACCESS_TYPE_OBJECT = eINSTANCE.getAccessTypeObject();

		/**
		 * The meta object literal for the '<em>Discriminator Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getDiscriminatorTypeObject()
		 * @generated
		 */
		public static final EDataType DISCRIMINATOR_TYPE_OBJECT = eINSTANCE.getDiscriminatorTypeObject();

		/**
		 * The meta object literal for the '<em>Discriminator Value</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getDiscriminatorValue()
		 * @generated
		 */
		public static final EDataType DISCRIMINATOR_VALUE = eINSTANCE.getDiscriminatorValue();

		/**
		 * The meta object literal for the '<em>Enumerated</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEnumerated()
		 * @generated
		 */
		public static final EDataType ENUMERATED = eINSTANCE.getEnumerated();

		/**
		 * The meta object literal for the '<em>Enum Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEnumTypeObject()
		 * @generated
		 */
		public static final EDataType ENUM_TYPE_OBJECT = eINSTANCE.getEnumTypeObject();

		/**
		 * The meta object literal for the '<em>Fetch Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getFetchTypeObject()
		 * @generated
		 */
		public static final EDataType FETCH_TYPE_OBJECT = eINSTANCE.getFetchTypeObject();

		/**
		 * The meta object literal for the '<em>Generation Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGenerationTypeObject()
		 * @generated
		 */
		public static final EDataType GENERATION_TYPE_OBJECT = eINSTANCE.getGenerationTypeObject();

		/**
		 * The meta object literal for the '<em>Inheritance Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getInheritanceTypeObject()
		 * @generated
		 */
		public static final EDataType INHERITANCE_TYPE_OBJECT = eINSTANCE.getInheritanceTypeObject();

		/**
		 * The meta object literal for the '<em>Order By</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOrderBy()
		 * @generated
		 */
		public static final EDataType ORDER_BY = eINSTANCE.getOrderBy();

		/**
		 * The meta object literal for the '<em>Temporal</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTemporal()
		 * @generated
		 */
		public static final EDataType TEMPORAL = eINSTANCE.getTemporal();

		/**
		 * The meta object literal for the '<em>Temporal Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTemporalTypeObject()
		 * @generated
		 */
		public static final EDataType TEMPORAL_TYPE_OBJECT = eINSTANCE.getTemporalTypeObject();

		/**
		 * The meta object literal for the '<em>Version Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getVersionType()
		 * @generated
		 */
		public static final EDataType VERSION_TYPE = eINSTANCE.getVersionType();

	}

} //OrmPackage
