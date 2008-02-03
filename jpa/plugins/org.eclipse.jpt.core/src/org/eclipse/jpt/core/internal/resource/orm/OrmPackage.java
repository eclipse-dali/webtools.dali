/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
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
	 * The feature id for the '<em><b>Xml Mapping Metadata Complete</b></em>' attribute.
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
	 * The feature id for the '<em><b>Cascade Persist</b></em>' attribute.
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
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.TypeMapping <em>Type Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.TypeMapping
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTypeMapping()
	 * @generated
	 */
	public static final int TYPE_MAPPING = 3;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TYPE_MAPPING__CLASS_NAME = 0;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TYPE_MAPPING__ACCESS = 1;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TYPE_MAPPING__METADATA_COMPLETE = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TYPE_MAPPING__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TYPE_MAPPING__ATTRIBUTES = 4;

	/**
	 * The number of structural features of the '<em>Type Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TYPE_MAPPING_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass <em>Mapped Superclass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getMappedSuperclass()
	 * @generated
	 */
	public static final int MAPPED_SUPERCLASS = 4;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__CLASS_NAME = TYPE_MAPPING__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__ACCESS = TYPE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__METADATA_COMPLETE = TYPE_MAPPING__METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__DESCRIPTION = TYPE_MAPPING__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__ATTRIBUTES = TYPE_MAPPING__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Id Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__ID_CLASS = TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Exclude Default Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS = TYPE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Exclude Superclass Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS = TYPE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__ENTITY_LISTENERS = TYPE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__PRE_PERSIST = TYPE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__POST_PERSIST = TYPE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__PRE_REMOVE = TYPE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__POST_REMOVE = TYPE_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__PRE_UPDATE = TYPE_MAPPING_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__POST_UPDATE = TYPE_MAPPING_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS__POST_LOAD = TYPE_MAPPING_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Mapped Superclass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPED_SUPERCLASS_FEATURE_COUNT = TYPE_MAPPING_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity <em>Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity()
	 * @generated
	 */
	public static final int ENTITY = 5;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__CLASS_NAME = TYPE_MAPPING__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__ACCESS = TYPE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__METADATA_COMPLETE = TYPE_MAPPING__METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__DESCRIPTION = TYPE_MAPPING__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__ATTRIBUTES = TYPE_MAPPING__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__NAME = TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__TABLE = TYPE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Secondary Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__SECONDARY_TABLES = TYPE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__PRIMARY_KEY_JOIN_COLUMNS = TYPE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Id Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__ID_CLASS = TYPE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Inheritance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__INHERITANCE = TYPE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__DISCRIMINATOR_VALUE = TYPE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Discriminator Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__DISCRIMINATOR_COLUMN = TYPE_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__SEQUENCE_GENERATOR = TYPE_MAPPING_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__TABLE_GENERATOR = TYPE_MAPPING_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__NAMED_QUERIES = TYPE_MAPPING_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__NAMED_NATIVE_QUERIES = TYPE_MAPPING_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__SQL_RESULT_SET_MAPPINGS = TYPE_MAPPING_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Exclude Default Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__EXCLUDE_DEFAULT_LISTENERS = TYPE_MAPPING_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Exclude Superclass Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__EXCLUDE_SUPERCLASS_LISTENERS = TYPE_MAPPING_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__ENTITY_LISTENERS = TYPE_MAPPING_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__PRE_PERSIST = TYPE_MAPPING_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__POST_PERSIST = TYPE_MAPPING_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__PRE_REMOVE = TYPE_MAPPING_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__POST_REMOVE = TYPE_MAPPING_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__PRE_UPDATE = TYPE_MAPPING_FEATURE_COUNT + 20;

	/**
	 * The feature id for the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__POST_UPDATE = TYPE_MAPPING_FEATURE_COUNT + 21;

	/**
	 * The feature id for the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__POST_LOAD = TYPE_MAPPING_FEATURE_COUNT + 22;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__ATTRIBUTE_OVERRIDES = TYPE_MAPPING_FEATURE_COUNT + 23;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY__ASSOCIATION_OVERRIDES = TYPE_MAPPING_FEATURE_COUNT + 24;

	/**
	 * The number of structural features of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_FEATURE_COUNT = TYPE_MAPPING_FEATURE_COUNT + 25;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Embeddable <em>Embeddable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embeddable
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddable()
	 * @generated
	 */
	public static final int EMBEDDABLE = 6;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE__CLASS_NAME = TYPE_MAPPING__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE__ACCESS = TYPE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE__METADATA_COMPLETE = TYPE_MAPPING__METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE__DESCRIPTION = TYPE_MAPPING__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE__ATTRIBUTES = TYPE_MAPPING__ATTRIBUTES;

	/**
	 * The number of structural features of the '<em>Embeddable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDABLE_FEATURE_COUNT = TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes <em>Attributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes()
	 * @generated
	 */
	public static final int ATTRIBUTES = 7;

	/**
	 * The feature id for the '<em><b>Ids</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__IDS = 0;

	/**
	 * The feature id for the '<em><b>Embedded Ids</b></em>' containment reference list.
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
	 * The feature id for the '<em><b>One To Manys</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__ONE_TO_MANYS = 5;

	/**
	 * The feature id for the '<em><b>One To Ones</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__ONE_TO_ONES = 6;

	/**
	 * The feature id for the '<em><b>Many To Manys</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__MANY_TO_MANYS = 7;

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
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.AttributeMapping <em>Attribute Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.AttributeMapping
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributeMapping()
	 * @generated
	 */
	public static final int ATTRIBUTE_MAPPING = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_MAPPING__NAME = 0;

	/**
	 * The number of structural features of the '<em>Attribute Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_MAPPING_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.ColumnMapping <em>Column Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.ColumnMapping
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getColumnMapping()
	 * @generated
	 */
	public static final int COLUMN_MAPPING = 9;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_MAPPING__COLUMN = 0;

	/**
	 * The number of structural features of the '<em>Column Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_MAPPING_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping <em>Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getRelationshipMapping()
	 * @generated
	 */
	public static final int RELATIONSHIP_MAPPING = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int RELATIONSHIP_MAPPING__NAME = ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int RELATIONSHIP_MAPPING__TARGET_ENTITY = ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int RELATIONSHIP_MAPPING__FETCH = ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int RELATIONSHIP_MAPPING__JOIN_TABLE = ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int RELATIONSHIP_MAPPING__CASCADE = ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int RELATIONSHIP_MAPPING_FEATURE_COUNT = ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping <em>Multi Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getMultiRelationshipMapping()
	 * @generated
	 */
	public static final int MULTI_RELATIONSHIP_MAPPING = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MULTI_RELATIONSHIP_MAPPING__NAME = RELATIONSHIP_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY = RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MULTI_RELATIONSHIP_MAPPING__FETCH = RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE = RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MULTI_RELATIONSHIP_MAPPING__CASCADE = RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MULTI_RELATIONSHIP_MAPPING__MAPPED_BY = RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MULTI_RELATIONSHIP_MAPPING__ORDER_BY = RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MULTI_RELATIONSHIP_MAPPING__MAP_KEY = RELATIONSHIP_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Multi Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT = RELATIONSHIP_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.SingleRelationshipMapping <em>Single Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.SingleRelationshipMapping
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSingleRelationshipMapping()
	 * @generated
	 */
	public static final int SINGLE_RELATIONSHIP_MAPPING = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SINGLE_RELATIONSHIP_MAPPING__NAME = RELATIONSHIP_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY = RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SINGLE_RELATIONSHIP_MAPPING__FETCH = RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE = RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SINGLE_RELATIONSHIP_MAPPING__CASCADE = RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SINGLE_RELATIONSHIP_MAPPING__OPTIONAL = RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS = RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Single Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT = RELATIONSHIP_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Id <em>Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Id
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getId()
	 * @generated
	 */
	public static final int ID = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID__NAME = ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID__COLUMN = ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID__GENERATED_VALUE = ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID__TEMPORAL = ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID__TABLE_GENERATOR = ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID__SEQUENCE_GENERATOR = ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID_FEATURE_COUNT = ATTRIBUTE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.IdImpl <em>Id Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.IdImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getIdImpl()
	 * @generated
	 */
	public static final int ID_IMPL = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID_IMPL__NAME = ID__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID_IMPL__COLUMN = ID__COLUMN;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID_IMPL__GENERATED_VALUE = ID__GENERATED_VALUE;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID_IMPL__TEMPORAL = ID__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID_IMPL__TABLE_GENERATOR = ID__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID_IMPL__SEQUENCE_GENERATOR = ID__SEQUENCE_GENERATOR;

	/**
	 * The number of structural features of the '<em>Id Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ID_IMPL_FEATURE_COUNT = ID_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedId <em>Embedded Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddedId
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddedId()
	 * @generated
	 */
	public static final int EMBEDDED_ID = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_ID__NAME = ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_ID__ATTRIBUTE_OVERRIDES = ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Embedded Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_ID_FEATURE_COUNT = ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedIdImpl <em>Embedded Id Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddedIdImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddedIdImpl()
	 * @generated
	 */
	public static final int EMBEDDED_ID_IMPL = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_ID_IMPL__NAME = EMBEDDED_ID__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_ID_IMPL__ATTRIBUTE_OVERRIDES = EMBEDDED_ID__ATTRIBUTE_OVERRIDES;

	/**
	 * The number of structural features of the '<em>Embedded Id Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_ID_IMPL_FEATURE_COUNT = EMBEDDED_ID_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Basic <em>Basic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Basic
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getBasic()
	 * @generated
	 */
	public static final int BASIC = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__NAME = ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__COLUMN = ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__FETCH = ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__OPTIONAL = ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__LOB = ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__TEMPORAL = ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC__ENUMERATED = ATTRIBUTE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Basic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC_FEATURE_COUNT = ATTRIBUTE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.BasicImpl <em>Basic Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.BasicImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getBasicImpl()
	 * @generated
	 */
	public static final int BASIC_IMPL = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC_IMPL__NAME = BASIC__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC_IMPL__COLUMN = BASIC__COLUMN;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC_IMPL__FETCH = BASIC__FETCH;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC_IMPL__OPTIONAL = BASIC__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC_IMPL__LOB = BASIC__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC_IMPL__TEMPORAL = BASIC__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC_IMPL__ENUMERATED = BASIC__ENUMERATED;

	/**
	 * The number of structural features of the '<em>Basic Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int BASIC_IMPL_FEATURE_COUNT = BASIC_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Version <em>Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Version
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getVersion()
	 * @generated
	 */
	public static final int VERSION = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int VERSION__NAME = ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int VERSION__COLUMN = ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int VERSION__TEMPORAL = ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int VERSION_FEATURE_COUNT = ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.VersionImpl <em>Version Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.VersionImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getVersionImpl()
	 * @generated
	 */
	public static final int VERSION_IMPL = 20;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int VERSION_IMPL__NAME = VERSION__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int VERSION_IMPL__COLUMN = VERSION__COLUMN;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int VERSION_IMPL__TEMPORAL = VERSION__TEMPORAL;

	/**
	 * The number of structural features of the '<em>Version Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int VERSION_IMPL_FEATURE_COUNT = VERSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOne <em>Many To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOne
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getManyToOne()
	 * @generated
	 */
	public static final int MANY_TO_ONE = 21;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__NAME = SINGLE_RELATIONSHIP_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__TARGET_ENTITY = SINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__FETCH = SINGLE_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__JOIN_TABLE = SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__CASCADE = SINGLE_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__OPTIONAL = SINGLE_RELATIONSHIP_MAPPING__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE__JOIN_COLUMNS = SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;

	/**
	 * The number of structural features of the '<em>Many To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE_FEATURE_COUNT = SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOneImpl <em>Many To One Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOneImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getManyToOneImpl()
	 * @generated
	 */
	public static final int MANY_TO_ONE_IMPL = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE_IMPL__NAME = MANY_TO_ONE__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE_IMPL__TARGET_ENTITY = MANY_TO_ONE__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE_IMPL__FETCH = MANY_TO_ONE__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE_IMPL__JOIN_TABLE = MANY_TO_ONE__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE_IMPL__CASCADE = MANY_TO_ONE__CASCADE;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE_IMPL__OPTIONAL = MANY_TO_ONE__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE_IMPL__JOIN_COLUMNS = MANY_TO_ONE__JOIN_COLUMNS;

	/**
	 * The number of structural features of the '<em>Many To One Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_ONE_IMPL_FEATURE_COUNT = MANY_TO_ONE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany <em>One To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToMany()
	 * @generated
	 */
	public static final int ONE_TO_MANY = 23;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__NAME = MULTI_RELATIONSHIP_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__TARGET_ENTITY = MULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__FETCH = MULTI_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__JOIN_TABLE = MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__CASCADE = MULTI_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__MAPPED_BY = MULTI_RELATIONSHIP_MAPPING__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__ORDER_BY = MULTI_RELATIONSHIP_MAPPING__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__MAP_KEY = MULTI_RELATIONSHIP_MAPPING__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY__JOIN_COLUMNS = MULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>One To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY_FEATURE_COUNT = MULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToManyImpl <em>One To Many Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToManyImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToManyImpl()
	 * @generated
	 */
	public static final int ONE_TO_MANY_IMPL = 24;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY_IMPL__NAME = ONE_TO_MANY__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY_IMPL__TARGET_ENTITY = ONE_TO_MANY__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY_IMPL__FETCH = ONE_TO_MANY__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY_IMPL__JOIN_TABLE = ONE_TO_MANY__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY_IMPL__CASCADE = ONE_TO_MANY__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY_IMPL__MAPPED_BY = ONE_TO_MANY__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY_IMPL__ORDER_BY = ONE_TO_MANY__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY_IMPL__MAP_KEY = ONE_TO_MANY__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY_IMPL__JOIN_COLUMNS = ONE_TO_MANY__JOIN_COLUMNS;

	/**
	 * The number of structural features of the '<em>One To Many Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_MANY_IMPL_FEATURE_COUNT = ONE_TO_MANY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne <em>One To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOne()
	 * @generated
	 */
	public static final int ONE_TO_ONE = 25;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__NAME = SINGLE_RELATIONSHIP_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__TARGET_ENTITY = SINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__FETCH = SINGLE_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__JOIN_TABLE = SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__CASCADE = SINGLE_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__OPTIONAL = SINGLE_RELATIONSHIP_MAPPING__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__JOIN_COLUMNS = SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__MAPPED_BY = SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS = SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>One To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE_FEATURE_COUNT = SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOneImpl <em>One To One Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOneImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOneImpl()
	 * @generated
	 */
	public static final int ONE_TO_ONE_IMPL = 26;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE_IMPL__NAME = ONE_TO_ONE__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE_IMPL__TARGET_ENTITY = ONE_TO_ONE__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE_IMPL__FETCH = ONE_TO_ONE__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE_IMPL__JOIN_TABLE = ONE_TO_ONE__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE_IMPL__CASCADE = ONE_TO_ONE__CASCADE;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE_IMPL__OPTIONAL = ONE_TO_ONE__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE_IMPL__JOIN_COLUMNS = ONE_TO_ONE__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE_IMPL__MAPPED_BY = ONE_TO_ONE__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE_IMPL__PRIMARY_KEY_JOIN_COLUMNS = ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS;

	/**
	 * The number of structural features of the '<em>One To One Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TO_ONE_IMPL_FEATURE_COUNT = ONE_TO_ONE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany <em>Many To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToMany
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getManyToMany()
	 * @generated
	 */
	public static final int MANY_TO_MANY = 27;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__NAME = MULTI_RELATIONSHIP_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__TARGET_ENTITY = MULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__FETCH = MULTI_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__JOIN_TABLE = MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__CASCADE = MULTI_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__MAPPED_BY = MULTI_RELATIONSHIP_MAPPING__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__ORDER_BY = MULTI_RELATIONSHIP_MAPPING__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY__MAP_KEY = MULTI_RELATIONSHIP_MAPPING__MAP_KEY;

	/**
	 * The number of structural features of the '<em>Many To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY_FEATURE_COUNT = MULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToManyImpl <em>Many To Many Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToManyImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getManyToManyImpl()
	 * @generated
	 */
	public static final int MANY_TO_MANY_IMPL = 28;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY_IMPL__NAME = MANY_TO_MANY__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY_IMPL__TARGET_ENTITY = MANY_TO_MANY__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY_IMPL__FETCH = MANY_TO_MANY__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY_IMPL__JOIN_TABLE = MANY_TO_MANY__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY_IMPL__CASCADE = MANY_TO_MANY__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY_IMPL__MAPPED_BY = MANY_TO_MANY__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY_IMPL__ORDER_BY = MANY_TO_MANY__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY_IMPL__MAP_KEY = MANY_TO_MANY__MAP_KEY;

	/**
	 * The number of structural features of the '<em>Many To Many Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MANY_TO_MANY_IMPL_FEATURE_COUNT = MANY_TO_MANY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Embedded <em>Embedded</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embedded
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbedded()
	 * @generated
	 */
	public static final int EMBEDDED = 29;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED__NAME = ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED__ATTRIBUTE_OVERRIDES = ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Embedded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_FEATURE_COUNT = ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedImpl <em>Embedded Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddedImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddedImpl()
	 * @generated
	 */
	public static final int EMBEDDED_IMPL = 30;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_IMPL__NAME = EMBEDDED__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_IMPL__ATTRIBUTE_OVERRIDES = EMBEDDED__ATTRIBUTE_OVERRIDES;

	/**
	 * The number of structural features of the '<em>Embedded Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_IMPL_FEATURE_COUNT = EMBEDDED_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Transient <em>Transient</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Transient
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTransient()
	 * @generated
	 */
	public static final int TRANSIENT = 31;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TRANSIENT__NAME = ATTRIBUTE_MAPPING__NAME;

	/**
	 * The number of structural features of the '<em>Transient</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TRANSIENT_FEATURE_COUNT = ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.TransientImpl <em>Transient Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.TransientImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTransientImpl()
	 * @generated
	 */
	public static final int TRANSIENT_IMPL = 32;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TRANSIENT_IMPL__NAME = TRANSIENT__NAME;

	/**
	 * The number of structural features of the '<em>Transient Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TRANSIENT_IMPL_FEATURE_COUNT = TRANSIENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.AssociationOverride <em>Association Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.AssociationOverride
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAssociationOverride()
	 * @generated
	 */
	public static final int ASSOCIATION_OVERRIDE = 33;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ASSOCIATION_OVERRIDE__JOIN_COLUMNS = 0;

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
	public static final int ATTRIBUTE_OVERRIDE = 34;

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
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.AttributeOverrideImpl <em>Attribute Override Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.AttributeOverrideImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributeOverrideImpl()
	 * @generated
	 */
	public static final int ATTRIBUTE_OVERRIDE_IMPL = 35;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_OVERRIDE_IMPL__COLUMN = ATTRIBUTE_OVERRIDE__COLUMN;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_OVERRIDE_IMPL__NAME = ATTRIBUTE_OVERRIDE__NAME;

	/**
	 * The number of structural features of the '<em>Attribute Override Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_OVERRIDE_IMPL_FEATURE_COUNT = ATTRIBUTE_OVERRIDE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType <em>Cascade Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getCascadeType()
	 * @generated
	 */
	public static final int CASCADE_TYPE = 36;

	/**
	 * The feature id for the '<em><b>Cascade All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_ALL = 0;

	/**
	 * The feature id for the '<em><b>Cascade Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_PERSIST = 1;

	/**
	 * The feature id for the '<em><b>Cascade Merge</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_MERGE = 2;

	/**
	 * The feature id for the '<em><b>Cascade Remove</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_REMOVE = 3;

	/**
	 * The feature id for the '<em><b>Cascade Refresh</b></em>' attribute.
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
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeTypeImpl <em>Cascade Type Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeTypeImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getCascadeTypeImpl()
	 * @generated
	 */
	public static final int CASCADE_TYPE_IMPL = 37;

	/**
	 * The feature id for the '<em><b>Cascade All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE_IMPL__CASCADE_ALL = CASCADE_TYPE__CASCADE_ALL;

	/**
	 * The feature id for the '<em><b>Cascade Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE_IMPL__CASCADE_PERSIST = CASCADE_TYPE__CASCADE_PERSIST;

	/**
	 * The feature id for the '<em><b>Cascade Merge</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE_IMPL__CASCADE_MERGE = CASCADE_TYPE__CASCADE_MERGE;

	/**
	 * The feature id for the '<em><b>Cascade Remove</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE_IMPL__CASCADE_REMOVE = CASCADE_TYPE__CASCADE_REMOVE;

	/**
	 * The feature id for the '<em><b>Cascade Refresh</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE_IMPL__CASCADE_REFRESH = CASCADE_TYPE__CASCADE_REFRESH;

	/**
	 * The number of structural features of the '<em>Cascade Type Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE_IMPL_FEATURE_COUNT = CASCADE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.NamedColumn <em>Named Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedColumn
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getNamedColumn()
	 * @generated
	 */
	public static final int NAMED_COLUMN = 38;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_COLUMN__COLUMN_DEFINITION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_COLUMN__NAME = 1;

	/**
	 * The number of structural features of the '<em>Named Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_COLUMN_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractColumn <em>Abstract Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractColumn
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAbstractColumn()
	 * @generated
	 */
	public static final int ABSTRACT_COLUMN = 39;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_COLUMN__COLUMN_DEFINITION = NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_COLUMN__NAME = NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_COLUMN__INSERTABLE = NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_COLUMN__NULLABLE = NAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_COLUMN__TABLE = NAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_COLUMN__UNIQUE = NAMED_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_COLUMN__UPDATABLE = NAMED_COLUMN_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Abstract Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_COLUMN_FEATURE_COUNT = NAMED_COLUMN_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Column <em>Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getColumn()
	 * @generated
	 */
	public static final int COLUMN = 40;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__COLUMN_DEFINITION = ABSTRACT_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__NAME = ABSTRACT_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__INSERTABLE = ABSTRACT_COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__NULLABLE = ABSTRACT_COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__TABLE = ABSTRACT_COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__UNIQUE = ABSTRACT_COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__UPDATABLE = ABSTRACT_COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__LENGTH = ABSTRACT_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Precision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__PRECISION = ABSTRACT_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN__SCALE = ABSTRACT_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_FEATURE_COUNT = ABSTRACT_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.ColumnImpl <em>Column Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.ColumnImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getColumnImpl()
	 * @generated
	 */
	public static final int COLUMN_IMPL = 41;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_IMPL__COLUMN_DEFINITION = COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_IMPL__NAME = COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_IMPL__INSERTABLE = COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_IMPL__NULLABLE = COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_IMPL__TABLE = COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_IMPL__UNIQUE = COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_IMPL__UPDATABLE = COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_IMPL__LENGTH = COLUMN__LENGTH;

	/**
	 * The feature id for the '<em><b>Precision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_IMPL__PRECISION = COLUMN__PRECISION;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_IMPL__SCALE = COLUMN__SCALE;

	/**
	 * The number of structural features of the '<em>Column Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_IMPL_FEATURE_COUNT = COLUMN_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.ColumnResult <em>Column Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.ColumnResult
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getColumnResult()
	 * @generated
	 */
	public static final int COLUMN_RESULT = 42;

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
	public static final int DISCRIMINATOR_COLUMN = 43;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int DISCRIMINATOR_COLUMN__COLUMN_DEFINITION = NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int DISCRIMINATOR_COLUMN__NAME = NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Discriminator Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int DISCRIMINATOR_COLUMN__LENGTH = NAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Discriminator Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int DISCRIMINATOR_COLUMN_FEATURE_COUNT = NAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListeners <em>Entity Listeners</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListeners
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityListeners()
	 * @generated
	 */
	public static final int ENTITY_LISTENERS = 44;

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
	public static final int ENTITY_LISTENER = 45;

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
	public static final int ENTITY_RESULT = 46;

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
	public static final int FIELD_RESULT = 48;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValue <em>Generated Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.GeneratedValue
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGeneratedValue()
	 * @generated
	 */
	public static final int GENERATED_VALUE = 49;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.IdClass <em>Id Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.IdClass
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getIdClass()
	 * @generated
	 */
	public static final int ID_CLASS = 51;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Inheritance <em>Inheritance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Inheritance
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getInheritance()
	 * @generated
	 */
	public static final int INHERITANCE = 52;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn <em>Join Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumn
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getJoinColumn()
	 * @generated
	 */
	public static final int JOIN_COLUMN = 53;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTable <em>Join Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTable
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getJoinTable()
	 * @generated
	 */
	public static final int JOIN_TABLE = 55;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Lob <em>Lob</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Lob
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getLob()
	 * @generated
	 */
	public static final int LOB = 57;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.MapKey <em>Map Key</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.MapKey
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getMapKey()
	 * @generated
	 */
	public static final int MAP_KEY = 58;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EventMethod <em>Event Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EventMethod
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEventMethod()
	 * @generated
	 */
	public static final int EVENT_METHOD = 47;

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
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValueImpl <em>Generated Value Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.GeneratedValueImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGeneratedValueImpl()
	 * @generated
	 */
	public static final int GENERATED_VALUE_IMPL = 50;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int GENERATED_VALUE_IMPL__GENERATOR = GENERATED_VALUE__GENERATOR;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int GENERATED_VALUE_IMPL__STRATEGY = GENERATED_VALUE__STRATEGY;

	/**
	 * The number of structural features of the '<em>Generated Value Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int GENERATED_VALUE_IMPL_FEATURE_COUNT = GENERATED_VALUE_FEATURE_COUNT + 0;

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
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__COLUMN_DEFINITION = ABSTRACT_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__NAME = ABSTRACT_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__INSERTABLE = ABSTRACT_COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__NULLABLE = ABSTRACT_COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__TABLE = ABSTRACT_COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__UNIQUE = ABSTRACT_COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__UPDATABLE = ABSTRACT_COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN__REFERENCED_COLUMN_NAME = ABSTRACT_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Join Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN_FEATURE_COUNT = ABSTRACT_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumnImpl <em>Join Column Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumnImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getJoinColumnImpl()
	 * @generated
	 */
	public static final int JOIN_COLUMN_IMPL = 54;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN_IMPL__COLUMN_DEFINITION = JOIN_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN_IMPL__NAME = JOIN_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN_IMPL__INSERTABLE = JOIN_COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN_IMPL__NULLABLE = JOIN_COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN_IMPL__TABLE = JOIN_COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN_IMPL__UNIQUE = JOIN_COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN_IMPL__UPDATABLE = JOIN_COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN_IMPL__REFERENCED_COLUMN_NAME = JOIN_COLUMN__REFERENCED_COLUMN_NAME;

	/**
	 * The number of structural features of the '<em>Join Column Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_COLUMN_IMPL_FEATURE_COUNT = JOIN_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractTable <em>Abstract Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractTable
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAbstractTable()
	 * @generated
	 */
	public static final int ABSTRACT_TABLE = 72;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_TABLE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_TABLE__CATALOG = 1;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_TABLE__SCHEMA = 2;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_TABLE__UNIQUE_CONSTRAINTS = 3;

	/**
	 * The number of structural features of the '<em>Abstract Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_TABLE_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE__NAME = ABSTRACT_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE__CATALOG = ABSTRACT_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE__SCHEMA = ABSTRACT_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE__JOIN_COLUMNS = ABSTRACT_TABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE__INVERSE_JOIN_COLUMNS = ABSTRACT_TABLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Join Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE_FEATURE_COUNT = ABSTRACT_TABLE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTableImpl <em>Join Table Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTableImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getJoinTableImpl()
	 * @generated
	 */
	public static final int JOIN_TABLE_IMPL = 56;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE_IMPL__NAME = JOIN_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE_IMPL__CATALOG = JOIN_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE_IMPL__SCHEMA = JOIN_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE_IMPL__UNIQUE_CONSTRAINTS = JOIN_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE_IMPL__JOIN_COLUMNS = JOIN_TABLE__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE_IMPL__INVERSE_JOIN_COLUMNS = JOIN_TABLE__INVERSE_JOIN_COLUMNS;

	/**
	 * The number of structural features of the '<em>Join Table Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JOIN_TABLE_IMPL_FEATURE_COUNT = JOIN_TABLE_FEATURE_COUNT + 0;

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
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.MapKeyImpl <em>Map Key Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.MapKeyImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getMapKeyImpl()
	 * @generated
	 */
	public static final int MAP_KEY_IMPL = 59;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAP_KEY_IMPL__NAME = MAP_KEY__NAME;

	/**
	 * The number of structural features of the '<em>Map Key Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAP_KEY_IMPL_FEATURE_COUNT = MAP_KEY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Query <em>Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Query
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getQuery()
	 * @generated
	 */
	public static final int QUERY = 60;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int QUERY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int QUERY__QUERY = 1;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int QUERY__HINTS = 2;

	/**
	 * The number of structural features of the '<em>Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int QUERY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery <em>Named Native Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getNamedNativeQuery()
	 * @generated
	 */
	public static final int NAMED_NATIVE_QUERY = 61;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_NATIVE_QUERY__NAME = QUERY__NAME;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_NATIVE_QUERY__QUERY = QUERY__QUERY;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_NATIVE_QUERY__HINTS = QUERY__HINTS;

	/**
	 * The feature id for the '<em><b>Result Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_NATIVE_QUERY__RESULT_CLASS = QUERY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Result Set Mapping</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_NATIVE_QUERY__RESULT_SET_MAPPING = QUERY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Named Native Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_NATIVE_QUERY_FEATURE_COUNT = QUERY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.NamedQuery <em>Named Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedQuery
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getNamedQuery()
	 * @generated
	 */
	public static final int NAMED_QUERY = 62;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_QUERY__NAME = QUERY__NAME;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_QUERY__QUERY = QUERY__QUERY;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_QUERY__HINTS = QUERY__HINTS;

	/**
	 * The number of structural features of the '<em>Named Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NAMED_QUERY_FEATURE_COUNT = QUERY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.PostLoad <em>Post Load</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.PostLoad
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPostLoad()
	 * @generated
	 */
	public static final int POST_LOAD = 63;

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
	public static final int POST_PERSIST = 64;

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
	public static final int POST_REMOVE = 65;

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
	public static final int POST_UPDATE = 66;

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
	public static final int PRE_PERSIST = 67;

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
	public static final int PRE_REMOVE = 68;

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
	public static final int PRE_UPDATE = 69;

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
	public static final int QUERY_HINT = 71;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping <em>Sql Result Set Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSqlResultSetMapping()
	 * @generated
	 */
	public static final int SQL_RESULT_SET_MAPPING = 78;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn <em>Primary Key Join Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPrimaryKeyJoinColumn()
	 * @generated
	 */
	public static final int PRIMARY_KEY_JOIN_COLUMN = 70;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION = NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRIMARY_KEY_JOIN_COLUMN__NAME = NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Primary Key Join Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRIMARY_KEY_JOIN_COLUMN_FEATURE_COUNT = NAMED_COLUMN_FEATURE_COUNT + 1;

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
	public static final int SECONDARY_TABLE = 74;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator <em>Sequence Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSequenceGenerator()
	 * @generated
	 */
	public static final int SEQUENCE_GENERATOR = 76;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Table <em>Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Table
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTable()
	 * @generated
	 */
	public static final int TABLE = 73;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE__NAME = ABSTRACT_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE__CATALOG = ABSTRACT_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE__SCHEMA = ABSTRACT_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The number of structural features of the '<em>Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_FEATURE_COUNT = ABSTRACT_TABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_TABLE__NAME = ABSTRACT_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_TABLE__CATALOG = ABSTRACT_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_TABLE__SCHEMA = ABSTRACT_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS = ABSTRACT_TABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Secondary Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_TABLE_FEATURE_COUNT = ABSTRACT_TABLE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.Generator <em>Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.Generator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGenerator()
	 * @generated
	 */
	public static final int GENERATOR = 75;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int GENERATOR__NAME = 0;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int GENERATOR__INITIAL_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int GENERATOR__ALLOCATION_SIZE = 2;

	/**
	 * The number of structural features of the '<em>Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int GENERATOR_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR__NAME = GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR__INITIAL_VALUE = GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR__ALLOCATION_SIZE = GENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR__SEQUENCE_NAME = GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sequence Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR_FEATURE_COUNT = GENERATOR_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGeneratorImpl <em>Sequence Generator Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.SequenceGeneratorImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSequenceGeneratorImpl()
	 * @generated
	 */
	public static final int SEQUENCE_GENERATOR_IMPL = 77;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR_IMPL__NAME = SEQUENCE_GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR_IMPL__INITIAL_VALUE = SEQUENCE_GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR_IMPL__ALLOCATION_SIZE = SEQUENCE_GENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR_IMPL__SEQUENCE_NAME = SEQUENCE_GENERATOR__SEQUENCE_NAME;

	/**
	 * The number of structural features of the '<em>Sequence Generator Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SEQUENCE_GENERATOR_IMPL_FEATURE_COUNT = SEQUENCE_GENERATOR_FEATURE_COUNT + 0;

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
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator <em>Table Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator()
	 * @generated
	 */
	public static final int TABLE_GENERATOR = 79;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__NAME = GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__INITIAL_VALUE = GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__ALLOCATION_SIZE = GENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__TABLE = GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__CATALOG = GENERATOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__SCHEMA = GENERATOR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__PK_COLUMN_NAME = GENERATOR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__VALUE_COLUMN_NAME = GENERATOR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__PK_COLUMN_VALUE = GENERATOR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR__UNIQUE_CONSTRAINTS = GENERATOR_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Table Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR_FEATURE_COUNT = GENERATOR_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGeneratorImpl <em>Table Generator Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGeneratorImpl
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGeneratorImpl()
	 * @generated
	 */
	public static final int TABLE_GENERATOR_IMPL = 80;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR_IMPL__NAME = TABLE_GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR_IMPL__INITIAL_VALUE = TABLE_GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR_IMPL__ALLOCATION_SIZE = TABLE_GENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR_IMPL__TABLE = TABLE_GENERATOR__TABLE;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR_IMPL__CATALOG = TABLE_GENERATOR__CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR_IMPL__SCHEMA = TABLE_GENERATOR__SCHEMA;

	/**
	 * The feature id for the '<em><b>Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR_IMPL__PK_COLUMN_NAME = TABLE_GENERATOR__PK_COLUMN_NAME;

	/**
	 * The feature id for the '<em><b>Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR_IMPL__VALUE_COLUMN_NAME = TABLE_GENERATOR__VALUE_COLUMN_NAME;

	/**
	 * The feature id for the '<em><b>Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR_IMPL__PK_COLUMN_VALUE = TABLE_GENERATOR__PK_COLUMN_VALUE;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR_IMPL__UNIQUE_CONSTRAINTS = TABLE_GENERATOR__UNIQUE_CONSTRAINTS;

	/**
	 * The number of structural features of the '<em>Table Generator Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_GENERATOR_IMPL_FEATURE_COUNT = TABLE_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint <em>Unique Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getUniqueConstraint()
	 * @generated
	 */
	public static final int UNIQUE_CONSTRAINT = 81;

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
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.AccessType <em>Access Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.AccessType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAccessType()
	 * @generated
	 */
	public static final int ACCESS_TYPE = 82;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType <em>Discriminator Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getDiscriminatorType()
	 * @generated
	 */
	public static final int DISCRIMINATOR_TYPE = 83;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.EnumType <em>Enum Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.EnumType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEnumType()
	 * @generated
	 */
	public static final int ENUM_TYPE = 84;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.FetchType <em>Fetch Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.FetchType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getFetchType()
	 * @generated
	 */
	public static final int FETCH_TYPE = 85;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.GenerationType <em>Generation Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.GenerationType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGenerationType()
	 * @generated
	 */
	public static final int GENERATION_TYPE = 86;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.InheritanceType <em>Inheritance Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.InheritanceType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getInheritanceType()
	 * @generated
	 */
	public static final int INHERITANCE_TYPE = 87;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.orm.TemporalType <em>Temporal Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.orm.TemporalType
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTemporalType()
	 * @generated
	 */
	public static final int TEMPORAL_TYPE = 88;

	/**
	 * The meta object id for the '<em>Discriminator Value</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getDiscriminatorValue()
	 * @generated
	 */
	public static final int DISCRIMINATOR_VALUE = 89;

	/**
	 * The meta object id for the '<em>Enumerated</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEnumerated()
	 * @generated
	 */
	public static final int ENUMERATED = 90;

	/**
	 * The meta object id for the '<em>Order By</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOrderBy()
	 * @generated
	 */
	public static final int ORDER_BY = 91;

	/**
	 * The meta object id for the '<em>Version Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getVersionType()
	 * @generated
	 */
	public static final int VERSION_TYPE = 92;

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
	private EClass typeMappingEClass = null;

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
	private EClass attributeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass columnMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relationshipMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiRelationshipMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass singleRelationshipMappingEClass = null;

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
	private EClass idImplEClass = null;

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
	private EClass embeddedIdImplEClass = null;

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
	private EClass basicImplEClass = null;

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
	private EClass versionImplEClass = null;

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
	private EClass manyToOneImplEClass = null;

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
	private EClass oneToManyImplEClass = null;

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
	private EClass oneToOneImplEClass = null;

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
	private EClass manyToManyImplEClass = null;

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
	private EClass embeddedImplEClass = null;

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
	private EClass transientImplEClass = null;

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
	private EClass attributeOverrideImplEClass = null;

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
	private EClass cascadeTypeImplEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractColumnEClass = null;

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
	private EClass columnImplEClass = null;

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
	private EClass generatedValueImplEClass = null;

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
	private EClass joinColumnImplEClass = null;

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
	private EClass joinTableImplEClass = null;

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
	private EClass mapKeyImplEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass queryEClass = null;

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
	private EClass abstractTableEClass = null;

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
	private EClass generatorEClass = null;

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
	private EClass sequenceGeneratorImplEClass = null;

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
	private EClass tableGeneratorImplEClass = null;

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
	private EDataType orderByEDataType = null;

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
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata#isXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Mapping Metadata Complete</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata#isXmlMappingMetadataComplete()
	 * @see #getPersistenceUnitMetadata()
	 * @generated
	 */
	public EAttribute getPersistenceUnitMetadata_XmlMappingMetadataComplete()
	{
		return (EAttribute)persistenceUnitMetadataEClass.getEStructuralFeatures().get(0);
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
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#isCascadePersist <em>Cascade Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade Persist</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#isCascadePersist()
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaults_CascadePersist()
	{
		return (EAttribute)persistenceUnitDefaultsEClass.getEStructuralFeatures().get(3);
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
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.TypeMapping <em>Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TypeMapping
	 * @generated
	 */
	public EClass getTypeMapping()
	{
		return typeMappingEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.TypeMapping#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TypeMapping#getClassName()
	 * @see #getTypeMapping()
	 * @generated
	 */
	public EAttribute getTypeMapping_ClassName()
	{
		return (EAttribute)typeMappingEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.TypeMapping#getAccess <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TypeMapping#getAccess()
	 * @see #getTypeMapping()
	 * @generated
	 */
	public EAttribute getTypeMapping_Access()
	{
		return (EAttribute)typeMappingEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.TypeMapping#getMetadataComplete <em>Metadata Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metadata Complete</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TypeMapping#getMetadataComplete()
	 * @see #getTypeMapping()
	 * @generated
	 */
	public EAttribute getTypeMapping_MetadataComplete()
	{
		return (EAttribute)typeMappingEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.TypeMapping#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TypeMapping#getDescription()
	 * @see #getTypeMapping()
	 * @generated
	 */
	public EAttribute getTypeMapping_Description()
	{
		return (EAttribute)typeMappingEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.TypeMapping#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attributes</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TypeMapping#getAttributes()
	 * @see #getTypeMapping()
	 * @generated
	 */
	public EReference getTypeMapping_Attributes()
	{
		return (EReference)typeMappingEClass.getEStructuralFeatures().get(4);
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
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#isExcludeDefaultListeners <em>Exclude Default Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Default Listeners</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#isExcludeDefaultListeners()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EAttribute getMappedSuperclass_ExcludeDefaultListeners()
	{
		return (EAttribute)mappedSuperclassEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#isExcludeSuperclassListeners <em>Exclude Superclass Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Superclass Listeners</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass#isExcludeSuperclassListeners()
	 * @see #getMappedSuperclass()
	 * @generated
	 */
	public EAttribute getMappedSuperclass_ExcludeSuperclassListeners()
	{
		return (EAttribute)mappedSuperclassEClass.getEStructuralFeatures().get(2);
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
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(3);
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
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(4);
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
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(5);
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
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(6);
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
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(7);
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
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(8);
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
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(9);
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
		return (EReference)mappedSuperclassEClass.getEStructuralFeatures().get(10);
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
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#getTable()
	 * @see #getEntity()
	 * @generated
	 */
	public EReference getEntity_Table()
	{
		return (EReference)entityEClass.getEStructuralFeatures().get(1);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(2);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(3);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(4);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(5);
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
		return (EAttribute)entityEClass.getEStructuralFeatures().get(6);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(7);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(8);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(9);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(10);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(11);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#isExcludeDefaultListeners <em>Exclude Default Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Default Listeners</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#isExcludeDefaultListeners()
	 * @see #getEntity()
	 * @generated
	 */
	public EAttribute getEntity_ExcludeDefaultListeners()
	{
		return (EAttribute)entityEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#isExcludeSuperclassListeners <em>Exclude Superclass Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Superclass Listeners</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity#isExcludeSuperclassListeners()
	 * @see #getEntity()
	 * @generated
	 */
	public EAttribute getEntity_ExcludeSuperclassListeners()
	{
		return (EAttribute)entityEClass.getEStructuralFeatures().get(14);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(15);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(16);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(17);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(18);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(19);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(20);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(21);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(22);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(23);
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
		return (EReference)entityEClass.getEStructuralFeatures().get(24);
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
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getEmbeddedIds <em>Embedded Ids</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Embedded Ids</em>'.
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
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getOneToManys <em>One To Manys</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>One To Manys</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes#getOneToManys()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_OneToManys()
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
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getManyToManys <em>Many To Manys</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Many To Manys</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes#getManyToManys()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_ManyToManys()
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
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.AttributeMapping <em>Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AttributeMapping
	 * @generated
	 */
	public EClass getAttributeMapping()
	{
		return attributeMappingEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.AttributeMapping#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AttributeMapping#getName()
	 * @see #getAttributeMapping()
	 * @generated
	 */
	public EAttribute getAttributeMapping_Name()
	{
		return (EAttribute)attributeMappingEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.ColumnMapping <em>Column Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Column Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ColumnMapping
	 * @generated
	 */
	public EClass getColumnMapping()
	{
		return columnMappingEClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.ColumnMapping#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ColumnMapping#getColumn()
	 * @see #getColumnMapping()
	 * @generated
	 */
	public EReference getColumnMapping_Column()
	{
		return (EReference)columnMappingEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping <em>Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping
	 * @generated
	 */
	public EClass getRelationshipMapping()
	{
		return relationshipMappingEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping#getTargetEntity <em>Target Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Entity</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping#getTargetEntity()
	 * @see #getRelationshipMapping()
	 * @generated
	 */
	public EAttribute getRelationshipMapping_TargetEntity()
	{
		return (EAttribute)relationshipMappingEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping#getFetch()
	 * @see #getRelationshipMapping()
	 * @generated
	 */
	public EAttribute getRelationshipMapping_Fetch()
	{
		return (EAttribute)relationshipMappingEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping#getCascade <em>Cascade</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cascade</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping#getCascade()
	 * @see #getRelationshipMapping()
	 * @generated
	 */
	public EReference getRelationshipMapping_Cascade()
	{
		return (EReference)relationshipMappingEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping#getJoinTable <em>Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping#getJoinTable()
	 * @see #getRelationshipMapping()
	 * @generated
	 */
	public EReference getRelationshipMapping_JoinTable()
	{
		return (EReference)relationshipMappingEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping <em>Multi Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multi Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping
	 * @generated
	 */
	public EClass getMultiRelationshipMapping()
	{
		return multiRelationshipMappingEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping#getOrderBy <em>Order By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Order By</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping#getOrderBy()
	 * @see #getMultiRelationshipMapping()
	 * @generated
	 */
	public EAttribute getMultiRelationshipMapping_OrderBy()
	{
		return (EAttribute)multiRelationshipMappingEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping#getMapKey <em>Map Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping#getMapKey()
	 * @see #getMultiRelationshipMapping()
	 * @generated
	 */
	public EReference getMultiRelationshipMapping_MapKey()
	{
		return (EReference)multiRelationshipMappingEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping#getMappedBy <em>Mapped By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mapped By</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping#getMappedBy()
	 * @see #getMultiRelationshipMapping()
	 * @generated
	 */
	public EAttribute getMultiRelationshipMapping_MappedBy()
	{
		return (EAttribute)multiRelationshipMappingEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.SingleRelationshipMapping <em>Single Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Single Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SingleRelationshipMapping
	 * @generated
	 */
	public EClass getSingleRelationshipMapping()
	{
		return singleRelationshipMappingEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.SingleRelationshipMapping#getOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SingleRelationshipMapping#getOptional()
	 * @see #getSingleRelationshipMapping()
	 * @generated
	 */
	public EAttribute getSingleRelationshipMapping_Optional()
	{
		return (EAttribute)singleRelationshipMappingEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.SingleRelationshipMapping#getJoinColumns <em>Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SingleRelationshipMapping#getJoinColumns()
	 * @see #getSingleRelationshipMapping()
	 * @generated
	 */
	public EReference getSingleRelationshipMapping_JoinColumns()
	{
		return (EReference)singleRelationshipMappingEClass.getEStructuralFeatures().get(1);
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
		return (EReference)idEClass.getEStructuralFeatures().get(0);
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
		return (EAttribute)idEClass.getEStructuralFeatures().get(1);
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
		return (EReference)idEClass.getEStructuralFeatures().get(2);
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
		return (EReference)idEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.IdImpl <em>Id Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Id Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.IdImpl
	 * @generated
	 */
	public EClass getIdImpl()
	{
		return idImplEClass;
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
		return (EReference)embeddedIdEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedIdImpl <em>Embedded Id Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Embedded Id Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddedIdImpl
	 * @generated
	 */
	public EClass getEmbeddedIdImpl()
	{
		return embeddedIdImplEClass;
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
		return (EAttribute)basicEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Basic#getOptional()
	 * @see #getBasic()
	 * @generated
	 */
	public EAttribute getBasic_Optional()
	{
		return (EAttribute)basicEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#isLob <em>Lob</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lob</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Basic#isLob()
	 * @see #getBasic()
	 * @generated
	 */
	public EAttribute getBasic_Lob()
	{
		return (EAttribute)basicEClass.getEStructuralFeatures().get(2);
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
		return (EAttribute)basicEClass.getEStructuralFeatures().get(3);
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
		return (EAttribute)basicEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.BasicImpl <em>Basic Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.BasicImpl
	 * @generated
	 */
	public EClass getBasicImpl()
	{
		return basicImplEClass;
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
		return (EAttribute)versionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.VersionImpl <em>Version Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.VersionImpl
	 * @generated
	 */
	public EClass getVersionImpl()
	{
		return versionImplEClass;
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
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOneImpl <em>Many To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Many To One Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOneImpl
	 * @generated
	 */
	public EClass getManyToOneImpl()
	{
		return manyToOneImplEClass;
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
		return (EReference)oneToManyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.OneToManyImpl <em>One To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>One To Many Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToManyImpl
	 * @generated
	 */
	public EClass getOneToManyImpl()
	{
		return oneToManyImplEClass;
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
		return (EReference)oneToOneEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOneImpl <em>One To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>One To One Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOneImpl
	 * @generated
	 */
	public EClass getOneToOneImpl()
	{
		return oneToOneImplEClass;
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
		return (EAttribute)oneToOneEClass.getEStructuralFeatures().get(0);
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
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToManyImpl <em>Many To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Many To Many Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToManyImpl
	 * @generated
	 */
	public EClass getManyToManyImpl()
	{
		return manyToManyImplEClass;
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
		return (EReference)embeddedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedImpl <em>Embedded Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Embedded Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddedImpl
	 * @generated
	 */
	public EClass getEmbeddedImpl()
	{
		return embeddedImplEClass;
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
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.TransientImpl <em>Transient Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transient Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TransientImpl
	 * @generated
	 */
	public EClass getTransientImpl()
	{
		return transientImplEClass;
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
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.AssociationOverride#getJoinColumns <em>Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AssociationOverride#getJoinColumns()
	 * @see #getAssociationOverride()
	 * @generated
	 */
	public EReference getAssociationOverride_JoinColumns()
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
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.AttributeOverrideImpl <em>Attribute Override Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Override Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AttributeOverrideImpl
	 * @generated
	 */
	public EClass getAttributeOverrideImpl()
	{
		return attributeOverrideImplEClass;
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
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#isCascadeAll <em>Cascade All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade All</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType#isCascadeAll()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EAttribute getCascadeType_CascadeAll()
	{
		return (EAttribute)cascadeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#isCascadePersist <em>Cascade Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade Persist</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType#isCascadePersist()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EAttribute getCascadeType_CascadePersist()
	{
		return (EAttribute)cascadeTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#isCascadeMerge <em>Cascade Merge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade Merge</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType#isCascadeMerge()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EAttribute getCascadeType_CascadeMerge()
	{
		return (EAttribute)cascadeTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#isCascadeRemove <em>Cascade Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade Remove</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType#isCascadeRemove()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EAttribute getCascadeType_CascadeRemove()
	{
		return (EAttribute)cascadeTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType#isCascadeRefresh <em>Cascade Refresh</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade Refresh</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType#isCascadeRefresh()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EAttribute getCascadeType_CascadeRefresh()
	{
		return (EAttribute)cascadeTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeTypeImpl <em>Cascade Type Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cascade Type Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeTypeImpl
	 * @generated
	 */
	public EClass getCascadeTypeImpl()
	{
		return cascadeTypeImplEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.NamedColumn <em>Named Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedColumn
	 * @generated
	 */
	public EClass getNamedColumn()
	{
		return namedColumnEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.NamedColumn#getColumnDefinition <em>Column Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column Definition</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedColumn#getColumnDefinition()
	 * @see #getNamedColumn()
	 * @generated
	 */
	public EAttribute getNamedColumn_ColumnDefinition()
	{
		return (EAttribute)namedColumnEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.NamedColumn#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedColumn#getName()
	 * @see #getNamedColumn()
	 * @generated
	 */
	public EAttribute getNamedColumn_Name()
	{
		return (EAttribute)namedColumnEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractColumn <em>Abstract Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Column</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractColumn
	 * @generated
	 */
	public EClass getAbstractColumn()
	{
		return abstractColumnEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractColumn#getInsertable <em>Insertable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Insertable</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractColumn#getInsertable()
	 * @see #getAbstractColumn()
	 * @generated
	 */
	public EAttribute getAbstractColumn_Insertable()
	{
		return (EAttribute)abstractColumnEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractColumn#getNullable <em>Nullable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nullable</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractColumn#getNullable()
	 * @see #getAbstractColumn()
	 * @generated
	 */
	public EAttribute getAbstractColumn_Nullable()
	{
		return (EAttribute)abstractColumnEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractColumn#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractColumn#getTable()
	 * @see #getAbstractColumn()
	 * @generated
	 */
	public EAttribute getAbstractColumn_Table()
	{
		return (EAttribute)abstractColumnEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractColumn#getUnique <em>Unique</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractColumn#getUnique()
	 * @see #getAbstractColumn()
	 * @generated
	 */
	public EAttribute getAbstractColumn_Unique()
	{
		return (EAttribute)abstractColumnEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractColumn#getUpdatable <em>Updatable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Updatable</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractColumn#getUpdatable()
	 * @see #getAbstractColumn()
	 * @generated
	 */
	public EAttribute getAbstractColumn_Updatable()
	{
		return (EAttribute)abstractColumnEClass.getEStructuralFeatures().get(4);
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
		return (EAttribute)columnEClass.getEStructuralFeatures().get(0);
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
		return (EAttribute)columnEClass.getEStructuralFeatures().get(1);
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
		return (EAttribute)columnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.ColumnImpl <em>Column Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Column Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ColumnImpl
	 * @generated
	 */
	public EClass getColumnImpl()
	{
		return columnImplEClass;
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
		return (EAttribute)discriminatorColumnEClass.getEStructuralFeatures().get(0);
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
		return (EAttribute)discriminatorColumnEClass.getEStructuralFeatures().get(1);
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
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValueImpl <em>Generated Value Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generated Value Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.GeneratedValueImpl
	 * @generated
	 */
	public EClass getGeneratedValueImpl()
	{
		return generatedValueImplEClass;
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
		return (EAttribute)joinColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumnImpl <em>Join Column Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Join Column Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumnImpl
	 * @generated
	 */
	public EClass getJoinColumnImpl()
	{
		return joinColumnImplEClass;
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
		return (EReference)joinTableEClass.getEStructuralFeatures().get(0);
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
		return (EReference)joinTableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTableImpl <em>Join Table Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Join Table Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTableImpl
	 * @generated
	 */
	public EClass getJoinTableImpl()
	{
		return joinTableImplEClass;
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
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.MapKeyImpl <em>Map Key Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Key Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MapKeyImpl
	 * @generated
	 */
	public EClass getMapKeyImpl()
	{
		return mapKeyImplEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Query <em>Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Query</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Query
	 * @generated
	 */
	public EClass getQuery()
	{
		return queryEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Query#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Query#getName()
	 * @see #getQuery()
	 * @generated
	 */
	public EAttribute getQuery_Name()
	{
		return (EAttribute)queryEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Query#getQuery <em>Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Query</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Query#getQuery()
	 * @see #getQuery()
	 * @generated
	 */
	public EAttribute getQuery_Query()
	{
		return (EAttribute)queryEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.Query#getHints <em>Hints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Hints</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Query#getHints()
	 * @see #getQuery()
	 * @generated
	 */
	public EReference getQuery_Hints()
	{
		return (EReference)queryEClass.getEStructuralFeatures().get(2);
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
		return (EAttribute)namedNativeQueryEClass.getEStructuralFeatures().get(0);
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
		return (EAttribute)namedNativeQueryEClass.getEStructuralFeatures().get(1);
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
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractTable <em>Abstract Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Table</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractTable
	 * @generated
	 */
	public EClass getAbstractTable()
	{
		return abstractTableEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractTable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractTable#getName()
	 * @see #getAbstractTable()
	 * @generated
	 */
	public EAttribute getAbstractTable_Name()
	{
		return (EAttribute)abstractTableEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractTable#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractTable#getCatalog()
	 * @see #getAbstractTable()
	 * @generated
	 */
	public EAttribute getAbstractTable_Catalog()
	{
		return (EAttribute)abstractTableEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractTable#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractTable#getSchema()
	 * @see #getAbstractTable()
	 * @generated
	 */
	public EAttribute getAbstractTable_Schema()
	{
		return (EAttribute)abstractTableEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractTable#getUniqueConstraints <em>Unique Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Unique Constraints</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractTable#getUniqueConstraints()
	 * @see #getAbstractTable()
	 * @generated
	 */
	public EReference getAbstractTable_UniqueConstraints()
	{
		return (EReference)abstractTableEClass.getEStructuralFeatures().get(3);
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
		return (EAttribute)primaryKeyJoinColumnEClass.getEStructuralFeatures().get(0);
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
		return (EReference)secondaryTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.Generator <em>Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Generator
	 * @generated
	 */
	public EClass getGenerator()
	{
		return generatorEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Generator#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Generator#getName()
	 * @see #getGenerator()
	 * @generated
	 */
	public EAttribute getGenerator_Name()
	{
		return (EAttribute)generatorEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Generator#getInitialValue <em>Initial Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial Value</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Generator#getInitialValue()
	 * @see #getGenerator()
	 * @generated
	 */
	public EAttribute getGenerator_InitialValue()
	{
		return (EAttribute)generatorEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.orm.Generator#getAllocationSize <em>Allocation Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allocation Size</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Generator#getAllocationSize()
	 * @see #getGenerator()
	 * @generated
	 */
	public EAttribute getGenerator_AllocationSize()
	{
		return (EAttribute)generatorEClass.getEStructuralFeatures().get(2);
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
		return (EAttribute)sequenceGeneratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGeneratorImpl <em>Sequence Generator Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sequence Generator Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SequenceGeneratorImpl
	 * @generated
	 */
	public EClass getSequenceGeneratorImpl()
	{
		return sequenceGeneratorImplEClass;
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
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(0);
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
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(1);
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
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(2);
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
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(3);
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
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(4);
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
		return (EAttribute)tableGeneratorEClass.getEStructuralFeatures().get(5);
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
		return (EReference)tableGeneratorEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.orm.TableGeneratorImpl <em>Table Generator Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table Generator Impl</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGeneratorImpl
	 * @generated
	 */
	public EClass getTableGeneratorImpl()
	{
		return tableGeneratorImplEClass;
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
		createEAttribute(persistenceUnitMetadataEClass, PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE);
		createEReference(persistenceUnitMetadataEClass, PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS);

		persistenceUnitDefaultsEClass = createEClass(PERSISTENCE_UNIT_DEFAULTS);
		createEAttribute(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__SCHEMA);
		createEAttribute(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__CATALOG);
		createEAttribute(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__ACCESS);
		createEAttribute(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST);
		createEReference(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS);

		typeMappingEClass = createEClass(TYPE_MAPPING);
		createEAttribute(typeMappingEClass, TYPE_MAPPING__CLASS_NAME);
		createEAttribute(typeMappingEClass, TYPE_MAPPING__ACCESS);
		createEAttribute(typeMappingEClass, TYPE_MAPPING__METADATA_COMPLETE);
		createEAttribute(typeMappingEClass, TYPE_MAPPING__DESCRIPTION);
		createEReference(typeMappingEClass, TYPE_MAPPING__ATTRIBUTES);

		mappedSuperclassEClass = createEClass(MAPPED_SUPERCLASS);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__ID_CLASS);
		createEAttribute(mappedSuperclassEClass, MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS);
		createEAttribute(mappedSuperclassEClass, MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__ENTITY_LISTENERS);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__PRE_PERSIST);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__POST_PERSIST);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__PRE_REMOVE);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__POST_REMOVE);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__PRE_UPDATE);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__POST_UPDATE);
		createEReference(mappedSuperclassEClass, MAPPED_SUPERCLASS__POST_LOAD);

		entityEClass = createEClass(ENTITY);
		createEAttribute(entityEClass, ENTITY__NAME);
		createEReference(entityEClass, ENTITY__TABLE);
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
		createEAttribute(entityEClass, ENTITY__EXCLUDE_DEFAULT_LISTENERS);
		createEAttribute(entityEClass, ENTITY__EXCLUDE_SUPERCLASS_LISTENERS);
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

		embeddableEClass = createEClass(EMBEDDABLE);

		attributesEClass = createEClass(ATTRIBUTES);
		createEReference(attributesEClass, ATTRIBUTES__IDS);
		createEReference(attributesEClass, ATTRIBUTES__EMBEDDED_IDS);
		createEReference(attributesEClass, ATTRIBUTES__BASICS);
		createEReference(attributesEClass, ATTRIBUTES__VERSIONS);
		createEReference(attributesEClass, ATTRIBUTES__MANY_TO_ONES);
		createEReference(attributesEClass, ATTRIBUTES__ONE_TO_MANYS);
		createEReference(attributesEClass, ATTRIBUTES__ONE_TO_ONES);
		createEReference(attributesEClass, ATTRIBUTES__MANY_TO_MANYS);
		createEReference(attributesEClass, ATTRIBUTES__EMBEDDEDS);
		createEReference(attributesEClass, ATTRIBUTES__TRANSIENTS);

		attributeMappingEClass = createEClass(ATTRIBUTE_MAPPING);
		createEAttribute(attributeMappingEClass, ATTRIBUTE_MAPPING__NAME);

		columnMappingEClass = createEClass(COLUMN_MAPPING);
		createEReference(columnMappingEClass, COLUMN_MAPPING__COLUMN);

		relationshipMappingEClass = createEClass(RELATIONSHIP_MAPPING);
		createEAttribute(relationshipMappingEClass, RELATIONSHIP_MAPPING__TARGET_ENTITY);
		createEAttribute(relationshipMappingEClass, RELATIONSHIP_MAPPING__FETCH);
		createEReference(relationshipMappingEClass, RELATIONSHIP_MAPPING__JOIN_TABLE);
		createEReference(relationshipMappingEClass, RELATIONSHIP_MAPPING__CASCADE);

		multiRelationshipMappingEClass = createEClass(MULTI_RELATIONSHIP_MAPPING);
		createEAttribute(multiRelationshipMappingEClass, MULTI_RELATIONSHIP_MAPPING__MAPPED_BY);
		createEAttribute(multiRelationshipMappingEClass, MULTI_RELATIONSHIP_MAPPING__ORDER_BY);
		createEReference(multiRelationshipMappingEClass, MULTI_RELATIONSHIP_MAPPING__MAP_KEY);

		singleRelationshipMappingEClass = createEClass(SINGLE_RELATIONSHIP_MAPPING);
		createEAttribute(singleRelationshipMappingEClass, SINGLE_RELATIONSHIP_MAPPING__OPTIONAL);
		createEReference(singleRelationshipMappingEClass, SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS);

		idEClass = createEClass(ID);
		createEReference(idEClass, ID__GENERATED_VALUE);
		createEAttribute(idEClass, ID__TEMPORAL);
		createEReference(idEClass, ID__TABLE_GENERATOR);
		createEReference(idEClass, ID__SEQUENCE_GENERATOR);

		idImplEClass = createEClass(ID_IMPL);

		embeddedIdEClass = createEClass(EMBEDDED_ID);
		createEReference(embeddedIdEClass, EMBEDDED_ID__ATTRIBUTE_OVERRIDES);

		embeddedIdImplEClass = createEClass(EMBEDDED_ID_IMPL);

		basicEClass = createEClass(BASIC);
		createEAttribute(basicEClass, BASIC__FETCH);
		createEAttribute(basicEClass, BASIC__OPTIONAL);
		createEAttribute(basicEClass, BASIC__LOB);
		createEAttribute(basicEClass, BASIC__TEMPORAL);
		createEAttribute(basicEClass, BASIC__ENUMERATED);

		basicImplEClass = createEClass(BASIC_IMPL);

		versionEClass = createEClass(VERSION);
		createEAttribute(versionEClass, VERSION__TEMPORAL);

		versionImplEClass = createEClass(VERSION_IMPL);

		manyToOneEClass = createEClass(MANY_TO_ONE);

		manyToOneImplEClass = createEClass(MANY_TO_ONE_IMPL);

		oneToManyEClass = createEClass(ONE_TO_MANY);
		createEReference(oneToManyEClass, ONE_TO_MANY__JOIN_COLUMNS);

		oneToManyImplEClass = createEClass(ONE_TO_MANY_IMPL);

		oneToOneEClass = createEClass(ONE_TO_ONE);
		createEAttribute(oneToOneEClass, ONE_TO_ONE__MAPPED_BY);
		createEReference(oneToOneEClass, ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS);

		oneToOneImplEClass = createEClass(ONE_TO_ONE_IMPL);

		manyToManyEClass = createEClass(MANY_TO_MANY);

		manyToManyImplEClass = createEClass(MANY_TO_MANY_IMPL);

		embeddedEClass = createEClass(EMBEDDED);
		createEReference(embeddedEClass, EMBEDDED__ATTRIBUTE_OVERRIDES);

		embeddedImplEClass = createEClass(EMBEDDED_IMPL);

		transientEClass = createEClass(TRANSIENT);

		transientImplEClass = createEClass(TRANSIENT_IMPL);

		associationOverrideEClass = createEClass(ASSOCIATION_OVERRIDE);
		createEReference(associationOverrideEClass, ASSOCIATION_OVERRIDE__JOIN_COLUMNS);
		createEAttribute(associationOverrideEClass, ASSOCIATION_OVERRIDE__NAME);

		attributeOverrideEClass = createEClass(ATTRIBUTE_OVERRIDE);
		createEReference(attributeOverrideEClass, ATTRIBUTE_OVERRIDE__COLUMN);
		createEAttribute(attributeOverrideEClass, ATTRIBUTE_OVERRIDE__NAME);

		attributeOverrideImplEClass = createEClass(ATTRIBUTE_OVERRIDE_IMPL);

		cascadeTypeEClass = createEClass(CASCADE_TYPE);
		createEAttribute(cascadeTypeEClass, CASCADE_TYPE__CASCADE_ALL);
		createEAttribute(cascadeTypeEClass, CASCADE_TYPE__CASCADE_PERSIST);
		createEAttribute(cascadeTypeEClass, CASCADE_TYPE__CASCADE_MERGE);
		createEAttribute(cascadeTypeEClass, CASCADE_TYPE__CASCADE_REMOVE);
		createEAttribute(cascadeTypeEClass, CASCADE_TYPE__CASCADE_REFRESH);

		cascadeTypeImplEClass = createEClass(CASCADE_TYPE_IMPL);

		namedColumnEClass = createEClass(NAMED_COLUMN);
		createEAttribute(namedColumnEClass, NAMED_COLUMN__COLUMN_DEFINITION);
		createEAttribute(namedColumnEClass, NAMED_COLUMN__NAME);

		abstractColumnEClass = createEClass(ABSTRACT_COLUMN);
		createEAttribute(abstractColumnEClass, ABSTRACT_COLUMN__INSERTABLE);
		createEAttribute(abstractColumnEClass, ABSTRACT_COLUMN__NULLABLE);
		createEAttribute(abstractColumnEClass, ABSTRACT_COLUMN__TABLE);
		createEAttribute(abstractColumnEClass, ABSTRACT_COLUMN__UNIQUE);
		createEAttribute(abstractColumnEClass, ABSTRACT_COLUMN__UPDATABLE);

		columnEClass = createEClass(COLUMN);
		createEAttribute(columnEClass, COLUMN__LENGTH);
		createEAttribute(columnEClass, COLUMN__PRECISION);
		createEAttribute(columnEClass, COLUMN__SCALE);

		columnImplEClass = createEClass(COLUMN_IMPL);

		columnResultEClass = createEClass(COLUMN_RESULT);
		createEAttribute(columnResultEClass, COLUMN_RESULT__NAME);

		discriminatorColumnEClass = createEClass(DISCRIMINATOR_COLUMN);
		createEAttribute(discriminatorColumnEClass, DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE);
		createEAttribute(discriminatorColumnEClass, DISCRIMINATOR_COLUMN__LENGTH);

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

		generatedValueImplEClass = createEClass(GENERATED_VALUE_IMPL);

		idClassEClass = createEClass(ID_CLASS);
		createEAttribute(idClassEClass, ID_CLASS__CLASS_NAME);

		inheritanceEClass = createEClass(INHERITANCE);
		createEAttribute(inheritanceEClass, INHERITANCE__STRATEGY);

		joinColumnEClass = createEClass(JOIN_COLUMN);
		createEAttribute(joinColumnEClass, JOIN_COLUMN__REFERENCED_COLUMN_NAME);

		joinColumnImplEClass = createEClass(JOIN_COLUMN_IMPL);

		joinTableEClass = createEClass(JOIN_TABLE);
		createEReference(joinTableEClass, JOIN_TABLE__JOIN_COLUMNS);
		createEReference(joinTableEClass, JOIN_TABLE__INVERSE_JOIN_COLUMNS);

		joinTableImplEClass = createEClass(JOIN_TABLE_IMPL);

		lobEClass = createEClass(LOB);

		mapKeyEClass = createEClass(MAP_KEY);
		createEAttribute(mapKeyEClass, MAP_KEY__NAME);

		mapKeyImplEClass = createEClass(MAP_KEY_IMPL);

		queryEClass = createEClass(QUERY);
		createEAttribute(queryEClass, QUERY__NAME);
		createEAttribute(queryEClass, QUERY__QUERY);
		createEReference(queryEClass, QUERY__HINTS);

		namedNativeQueryEClass = createEClass(NAMED_NATIVE_QUERY);
		createEAttribute(namedNativeQueryEClass, NAMED_NATIVE_QUERY__RESULT_CLASS);
		createEAttribute(namedNativeQueryEClass, NAMED_NATIVE_QUERY__RESULT_SET_MAPPING);

		namedQueryEClass = createEClass(NAMED_QUERY);

		postLoadEClass = createEClass(POST_LOAD);

		postPersistEClass = createEClass(POST_PERSIST);

		postRemoveEClass = createEClass(POST_REMOVE);

		postUpdateEClass = createEClass(POST_UPDATE);

		prePersistEClass = createEClass(PRE_PERSIST);

		preRemoveEClass = createEClass(PRE_REMOVE);

		preUpdateEClass = createEClass(PRE_UPDATE);

		primaryKeyJoinColumnEClass = createEClass(PRIMARY_KEY_JOIN_COLUMN);
		createEAttribute(primaryKeyJoinColumnEClass, PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME);

		queryHintEClass = createEClass(QUERY_HINT);
		createEAttribute(queryHintEClass, QUERY_HINT__NAME);
		createEAttribute(queryHintEClass, QUERY_HINT__VALUE);

		abstractTableEClass = createEClass(ABSTRACT_TABLE);
		createEAttribute(abstractTableEClass, ABSTRACT_TABLE__NAME);
		createEAttribute(abstractTableEClass, ABSTRACT_TABLE__CATALOG);
		createEAttribute(abstractTableEClass, ABSTRACT_TABLE__SCHEMA);
		createEReference(abstractTableEClass, ABSTRACT_TABLE__UNIQUE_CONSTRAINTS);

		tableEClass = createEClass(TABLE);

		secondaryTableEClass = createEClass(SECONDARY_TABLE);
		createEReference(secondaryTableEClass, SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS);

		generatorEClass = createEClass(GENERATOR);
		createEAttribute(generatorEClass, GENERATOR__NAME);
		createEAttribute(generatorEClass, GENERATOR__INITIAL_VALUE);
		createEAttribute(generatorEClass, GENERATOR__ALLOCATION_SIZE);

		sequenceGeneratorEClass = createEClass(SEQUENCE_GENERATOR);
		createEAttribute(sequenceGeneratorEClass, SEQUENCE_GENERATOR__SEQUENCE_NAME);

		sequenceGeneratorImplEClass = createEClass(SEQUENCE_GENERATOR_IMPL);

		sqlResultSetMappingEClass = createEClass(SQL_RESULT_SET_MAPPING);
		createEAttribute(sqlResultSetMappingEClass, SQL_RESULT_SET_MAPPING__NAME);
		createEReference(sqlResultSetMappingEClass, SQL_RESULT_SET_MAPPING__ENTITY_RESULTS);
		createEReference(sqlResultSetMappingEClass, SQL_RESULT_SET_MAPPING__COLUMN_RESULTS);

		tableGeneratorEClass = createEClass(TABLE_GENERATOR);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__TABLE);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__CATALOG);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__SCHEMA);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__PK_COLUMN_NAME);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__VALUE_COLUMN_NAME);
		createEAttribute(tableGeneratorEClass, TABLE_GENERATOR__PK_COLUMN_VALUE);
		createEReference(tableGeneratorEClass, TABLE_GENERATOR__UNIQUE_CONSTRAINTS);

		tableGeneratorImplEClass = createEClass(TABLE_GENERATOR_IMPL);

		uniqueConstraintEClass = createEClass(UNIQUE_CONSTRAINT);
		createEAttribute(uniqueConstraintEClass, UNIQUE_CONSTRAINT__COLUMN_NAMES);

		// Create enums
		accessTypeEEnum = createEEnum(ACCESS_TYPE);
		discriminatorTypeEEnum = createEEnum(DISCRIMINATOR_TYPE);
		enumTypeEEnum = createEEnum(ENUM_TYPE);
		fetchTypeEEnum = createEEnum(FETCH_TYPE);
		generationTypeEEnum = createEEnum(GENERATION_TYPE);
		inheritanceTypeEEnum = createEEnum(INHERITANCE_TYPE);
		temporalTypeEEnum = createEEnum(TEMPORAL_TYPE);

		// Create data types
		discriminatorValueEDataType = createEDataType(DISCRIMINATOR_VALUE);
		enumeratedEDataType = createEDataType(ENUMERATED);
		orderByEDataType = createEDataType(ORDER_BY);
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
		mappedSuperclassEClass.getESuperTypes().add(this.getTypeMapping());
		entityEClass.getESuperTypes().add(this.getTypeMapping());
		embeddableEClass.getESuperTypes().add(this.getTypeMapping());
		relationshipMappingEClass.getESuperTypes().add(this.getAttributeMapping());
		multiRelationshipMappingEClass.getESuperTypes().add(this.getRelationshipMapping());
		singleRelationshipMappingEClass.getESuperTypes().add(this.getRelationshipMapping());
		idEClass.getESuperTypes().add(this.getAttributeMapping());
		idEClass.getESuperTypes().add(this.getColumnMapping());
		idImplEClass.getESuperTypes().add(this.getId());
		embeddedIdEClass.getESuperTypes().add(this.getAttributeMapping());
		embeddedIdImplEClass.getESuperTypes().add(this.getEmbeddedId());
		basicEClass.getESuperTypes().add(this.getAttributeMapping());
		basicEClass.getESuperTypes().add(this.getColumnMapping());
		basicImplEClass.getESuperTypes().add(this.getBasic());
		versionEClass.getESuperTypes().add(this.getAttributeMapping());
		versionEClass.getESuperTypes().add(this.getColumnMapping());
		versionImplEClass.getESuperTypes().add(this.getVersion());
		manyToOneEClass.getESuperTypes().add(this.getSingleRelationshipMapping());
		manyToOneImplEClass.getESuperTypes().add(this.getManyToOne());
		oneToManyEClass.getESuperTypes().add(this.getMultiRelationshipMapping());
		oneToManyImplEClass.getESuperTypes().add(this.getOneToMany());
		oneToOneEClass.getESuperTypes().add(this.getSingleRelationshipMapping());
		oneToOneImplEClass.getESuperTypes().add(this.getOneToOne());
		manyToManyEClass.getESuperTypes().add(this.getMultiRelationshipMapping());
		manyToManyImplEClass.getESuperTypes().add(this.getManyToMany());
		embeddedEClass.getESuperTypes().add(this.getAttributeMapping());
		embeddedImplEClass.getESuperTypes().add(this.getEmbedded());
		transientEClass.getESuperTypes().add(this.getAttributeMapping());
		transientImplEClass.getESuperTypes().add(this.getTransient());
		attributeOverrideImplEClass.getESuperTypes().add(this.getAttributeOverride());
		cascadeTypeImplEClass.getESuperTypes().add(this.getCascadeType());
		abstractColumnEClass.getESuperTypes().add(this.getNamedColumn());
		columnEClass.getESuperTypes().add(this.getAbstractColumn());
		columnImplEClass.getESuperTypes().add(this.getColumn());
		discriminatorColumnEClass.getESuperTypes().add(this.getNamedColumn());
		generatedValueImplEClass.getESuperTypes().add(this.getGeneratedValue());
		joinColumnEClass.getESuperTypes().add(this.getAbstractColumn());
		joinColumnImplEClass.getESuperTypes().add(this.getJoinColumn());
		joinTableEClass.getESuperTypes().add(this.getAbstractTable());
		joinTableImplEClass.getESuperTypes().add(this.getJoinTable());
		mapKeyImplEClass.getESuperTypes().add(this.getMapKey());
		namedNativeQueryEClass.getESuperTypes().add(this.getQuery());
		namedQueryEClass.getESuperTypes().add(this.getQuery());
		postLoadEClass.getESuperTypes().add(this.getEventMethod());
		postPersistEClass.getESuperTypes().add(this.getEventMethod());
		postRemoveEClass.getESuperTypes().add(this.getEventMethod());
		postUpdateEClass.getESuperTypes().add(this.getEventMethod());
		prePersistEClass.getESuperTypes().add(this.getEventMethod());
		preRemoveEClass.getESuperTypes().add(this.getEventMethod());
		preUpdateEClass.getESuperTypes().add(this.getEventMethod());
		primaryKeyJoinColumnEClass.getESuperTypes().add(this.getNamedColumn());
		tableEClass.getESuperTypes().add(this.getAbstractTable());
		secondaryTableEClass.getESuperTypes().add(this.getAbstractTable());
		sequenceGeneratorEClass.getESuperTypes().add(this.getGenerator());
		sequenceGeneratorImplEClass.getESuperTypes().add(this.getSequenceGenerator());
		tableGeneratorEClass.getESuperTypes().add(this.getGenerator());
		tableGeneratorImplEClass.getESuperTypes().add(this.getTableGenerator());

		// Initialize classes and features; add operations and parameters
		initEClass(entityMappingsEClass, EntityMappings.class, "EntityMappings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntityMappings_Version(), this.getVersionType(), "version", "1.0", 1, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappings_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_PersistenceUnitMetadata(), this.getPersistenceUnitMetadata(), null, "persistenceUnitMetadata", null, 0, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappings_Package(), theXMLTypePackage.getString(), "package", null, 0, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappings_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappings_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappings_Access(), this.getAccessType(), "access", "PROPERTY", 0, 1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_SequenceGenerators(), this.getSequenceGenerator(), null, "sequenceGenerators", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_TableGenerators(), this.getTableGenerator(), null, "tableGenerators", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_NamedQueries(), this.getNamedQuery(), null, "namedQueries", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_NamedNativeQueries(), this.getNamedNativeQuery(), null, "namedNativeQueries", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_SqlResultSetMappings(), this.getSqlResultSetMapping(), null, "sqlResultSetMappings", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_MappedSuperclasses(), this.getMappedSuperclass(), null, "mappedSuperclasses", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_Entities(), this.getEntity(), null, "entities", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappings_Embeddables(), this.getEmbeddable(), null, "embeddables", null, 0, -1, EntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(persistenceUnitMetadataEClass, PersistenceUnitMetadata.class, "PersistenceUnitMetadata", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPersistenceUnitMetadata_XmlMappingMetadataComplete(), theXMLTypePackage.getBoolean(), "xmlMappingMetadataComplete", null, 0, 1, PersistenceUnitMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPersistenceUnitMetadata_PersistenceUnitDefaults(), this.getPersistenceUnitDefaults(), null, "persistenceUnitDefaults", null, 0, 1, PersistenceUnitMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(persistenceUnitDefaultsEClass, PersistenceUnitDefaults.class, "PersistenceUnitDefaults", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPersistenceUnitDefaults_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaults_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaults_Access(), this.getAccessType(), "access", "PROPERTY", 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaults_CascadePersist(), theXMLTypePackage.getBoolean(), "cascadePersist", null, 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPersistenceUnitDefaults_EntityListeners(), this.getEntityListeners(), null, "entityListeners", null, 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeMappingEClass, TypeMapping.class, "TypeMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeMapping_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, TypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTypeMapping_Access(), this.getAccessType(), "access", "PROPERTY", 0, 1, TypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTypeMapping_MetadataComplete(), theXMLTypePackage.getBooleanObject(), "metadataComplete", null, 0, 1, TypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTypeMapping_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, TypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTypeMapping_Attributes(), this.getAttributes(), null, "attributes", null, 0, 1, TypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappedSuperclassEClass, MappedSuperclass.class, "MappedSuperclass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMappedSuperclass_IdClass(), this.getIdClass(), null, "idClass", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappedSuperclass_ExcludeDefaultListeners(), theXMLTypePackage.getBoolean(), "excludeDefaultListeners", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappedSuperclass_ExcludeSuperclassListeners(), theXMLTypePackage.getBoolean(), "excludeSuperclassListeners", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_EntityListeners(), this.getEntityListeners(), null, "entityListeners", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PrePersist(), this.getPrePersist(), null, "prePersist", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PostPersist(), this.getPostPersist(), null, "postPersist", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PreRemove(), this.getPreRemove(), null, "preRemove", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PostRemove(), this.getPostRemove(), null, "postRemove", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PreUpdate(), this.getPreUpdate(), null, "preUpdate", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PostUpdate(), this.getPostUpdate(), null, "postUpdate", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappedSuperclass_PostLoad(), this.getPostLoad(), null, "postLoad", null, 0, 1, MappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityEClass, Entity.class, "Entity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntity_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntity_Table(), this.getTable(), null, "table", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
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
		initEAttribute(getEntity_ExcludeDefaultListeners(), theXMLTypePackage.getBoolean(), "excludeDefaultListeners", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntity_ExcludeSuperclassListeners(), theXMLTypePackage.getBoolean(), "excludeSuperclassListeners", null, 0, 1, Entity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
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

		initEClass(embeddableEClass, Embeddable.class, "Embeddable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(attributesEClass, Attributes.class, "Attributes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributes_Ids(), this.getIdImpl(), null, "ids", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_EmbeddedIds(), this.getEmbeddedIdImpl(), null, "embeddedIds", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_Basics(), this.getBasicImpl(), null, "basics", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_Versions(), this.getVersionImpl(), null, "versions", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_ManyToOnes(), this.getManyToOneImpl(), null, "manyToOnes", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_OneToManys(), this.getOneToManyImpl(), null, "oneToManys", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_OneToOnes(), this.getOneToOneImpl(), null, "oneToOnes", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_ManyToManys(), this.getManyToManyImpl(), null, "manyToManys", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_Embeddeds(), this.getEmbeddedImpl(), null, "embeddeds", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_Transients(), this.getTransientImpl(), null, "transients", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeMappingEClass, AttributeMapping.class, "AttributeMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributeMapping_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, AttributeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(columnMappingEClass, ColumnMapping.class, "ColumnMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getColumnMapping_Column(), this.getColumn(), null, "column", null, 0, 1, ColumnMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(relationshipMappingEClass, RelationshipMapping.class, "RelationshipMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRelationshipMapping_TargetEntity(), theXMLTypePackage.getString(), "targetEntity", null, 0, 1, RelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRelationshipMapping_Fetch(), this.getFetchType(), "fetch", "LAZY", 0, 1, RelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelationshipMapping_JoinTable(), this.getJoinTable(), null, "joinTable", null, 0, 1, RelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelationshipMapping_Cascade(), this.getCascadeType(), null, "cascade", null, 0, 1, RelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(multiRelationshipMappingEClass, MultiRelationshipMapping.class, "MultiRelationshipMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMultiRelationshipMapping_MappedBy(), theXMLTypePackage.getString(), "mappedBy", null, 0, 1, MultiRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMultiRelationshipMapping_OrderBy(), this.getOrderBy(), "orderBy", null, 0, 1, MultiRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMultiRelationshipMapping_MapKey(), this.getMapKey(), null, "mapKey", null, 0, 1, MultiRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(singleRelationshipMappingEClass, SingleRelationshipMapping.class, "SingleRelationshipMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSingleRelationshipMapping_Optional(), theXMLTypePackage.getBooleanObject(), "optional", null, 0, 1, SingleRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSingleRelationshipMapping_JoinColumns(), this.getJoinColumn(), null, "joinColumns", null, 0, -1, SingleRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(idEClass, Id.class, "Id", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getId_GeneratedValue(), this.getGeneratedValue(), null, "generatedValue", null, 0, 1, Id.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getId_Temporal(), this.getTemporalType(), "temporal", null, 0, 1, Id.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getId_TableGenerator(), this.getTableGenerator(), null, "tableGenerator", null, 0, 1, Id.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getId_SequenceGenerator(), this.getSequenceGenerator(), null, "sequenceGenerator", null, 0, 1, Id.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(idImplEClass, IdImpl.class, "IdImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(embeddedIdEClass, EmbeddedId.class, "EmbeddedId", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEmbeddedId_AttributeOverrides(), this.getAttributeOverride(), null, "attributeOverrides", null, 0, -1, EmbeddedId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(embeddedIdImplEClass, EmbeddedIdImpl.class, "EmbeddedIdImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(basicEClass, Basic.class, "Basic", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBasic_Fetch(), this.getFetchType(), "fetch", "LAZY", 0, 1, Basic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBasic_Optional(), theXMLTypePackage.getBooleanObject(), "optional", null, 0, 1, Basic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBasic_Lob(), theXMLTypePackage.getBoolean(), "lob", null, 0, 1, Basic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBasic_Temporal(), this.getTemporalType(), "temporal", null, 0, 1, Basic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBasic_Enumerated(), this.getEnumType(), "enumerated", null, 0, 1, Basic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(basicImplEClass, BasicImpl.class, "BasicImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(versionEClass, Version.class, "Version", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVersion_Temporal(), this.getTemporalType(), "temporal", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(versionImplEClass, VersionImpl.class, "VersionImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(manyToOneEClass, ManyToOne.class, "ManyToOne", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(manyToOneImplEClass, ManyToOneImpl.class, "ManyToOneImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(oneToManyEClass, OneToMany.class, "OneToMany", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOneToMany_JoinColumns(), this.getJoinColumn(), null, "joinColumns", null, 0, -1, OneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(oneToManyImplEClass, OneToManyImpl.class, "OneToManyImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(oneToOneEClass, OneToOne.class, "OneToOne", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOneToOne_MappedBy(), theXMLTypePackage.getString(), "mappedBy", null, 0, 1, OneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOneToOne_PrimaryKeyJoinColumns(), this.getPrimaryKeyJoinColumn(), null, "primaryKeyJoinColumns", null, 0, -1, OneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(oneToOneImplEClass, OneToOneImpl.class, "OneToOneImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(manyToManyEClass, ManyToMany.class, "ManyToMany", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(manyToManyImplEClass, ManyToManyImpl.class, "ManyToManyImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(embeddedEClass, Embedded.class, "Embedded", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEmbedded_AttributeOverrides(), this.getAttributeOverride(), null, "attributeOverrides", null, 0, -1, Embedded.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(embeddedImplEClass, EmbeddedImpl.class, "EmbeddedImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(transientEClass, Transient.class, "Transient", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(transientImplEClass, TransientImpl.class, "TransientImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(associationOverrideEClass, AssociationOverride.class, "AssociationOverride", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAssociationOverride_JoinColumns(), this.getJoinColumn(), null, "joinColumns", null, 1, -1, AssociationOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAssociationOverride_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, AssociationOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeOverrideEClass, AttributeOverride.class, "AttributeOverride", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeOverride_Column(), this.getColumn(), null, "column", null, 1, 1, AttributeOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeOverride_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, AttributeOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeOverrideImplEClass, AttributeOverrideImpl.class, "AttributeOverrideImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(cascadeTypeEClass, CascadeType.class, "CascadeType", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCascadeType_CascadeAll(), theXMLTypePackage.getBoolean(), "cascadeAll", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCascadeType_CascadePersist(), theXMLTypePackage.getBoolean(), "cascadePersist", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCascadeType_CascadeMerge(), theXMLTypePackage.getBoolean(), "cascadeMerge", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCascadeType_CascadeRemove(), theXMLTypePackage.getBoolean(), "cascadeRemove", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCascadeType_CascadeRefresh(), theXMLTypePackage.getBoolean(), "cascadeRefresh", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cascadeTypeImplEClass, CascadeTypeImpl.class, "CascadeTypeImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(namedColumnEClass, NamedColumn.class, "NamedColumn", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamedColumn_ColumnDefinition(), theXMLTypePackage.getString(), "columnDefinition", null, 0, 1, NamedColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedColumn_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, NamedColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractColumnEClass, AbstractColumn.class, "AbstractColumn", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractColumn_Insertable(), theXMLTypePackage.getBooleanObject(), "insertable", null, 0, 1, AbstractColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractColumn_Nullable(), theXMLTypePackage.getBooleanObject(), "nullable", null, 0, 1, AbstractColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractColumn_Table(), theXMLTypePackage.getString(), "table", null, 0, 1, AbstractColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractColumn_Unique(), theXMLTypePackage.getBooleanObject(), "unique", null, 0, 1, AbstractColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractColumn_Updatable(), theXMLTypePackage.getBooleanObject(), "updatable", null, 0, 1, AbstractColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(columnEClass, Column.class, "Column", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getColumn_Length(), theXMLTypePackage.getIntObject(), "length", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Precision(), theXMLTypePackage.getIntObject(), "precision", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Scale(), theXMLTypePackage.getIntObject(), "scale", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(columnImplEClass, ColumnImpl.class, "ColumnImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(columnResultEClass, ColumnResult.class, "ColumnResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getColumnResult_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ColumnResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(discriminatorColumnEClass, DiscriminatorColumn.class, "DiscriminatorColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDiscriminatorColumn_DiscriminatorType(), this.getDiscriminatorType(), "discriminatorType", "STRING", 0, 1, DiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiscriminatorColumn_Length(), theXMLTypePackage.getIntObject(), "length", null, 0, 1, DiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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

		initEClass(generatedValueEClass, GeneratedValue.class, "GeneratedValue", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGeneratedValue_Generator(), theXMLTypePackage.getString(), "generator", null, 0, 1, GeneratedValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGeneratedValue_Strategy(), this.getGenerationType(), "strategy", "TABLE", 0, 1, GeneratedValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(generatedValueImplEClass, GeneratedValueImpl.class, "GeneratedValueImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(idClassEClass, IdClass.class, "IdClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIdClass_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, IdClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inheritanceEClass, Inheritance.class, "Inheritance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInheritance_Strategy(), this.getInheritanceType(), "strategy", "SINGLE_TABLE", 0, 1, Inheritance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(joinColumnEClass, JoinColumn.class, "JoinColumn", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJoinColumn_ReferencedColumnName(), theXMLTypePackage.getString(), "referencedColumnName", null, 0, 1, JoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(joinColumnImplEClass, JoinColumnImpl.class, "JoinColumnImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(joinTableEClass, JoinTable.class, "JoinTable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJoinTable_JoinColumns(), this.getJoinColumn(), null, "joinColumns", null, 0, -1, JoinTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJoinTable_InverseJoinColumns(), this.getJoinColumn(), null, "inverseJoinColumns", null, 0, -1, JoinTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(joinTableImplEClass, JoinTableImpl.class, "JoinTableImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(lobEClass, Lob.class, "Lob", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mapKeyEClass, MapKey.class, "MapKey", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapKey_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, MapKey.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapKeyImplEClass, MapKeyImpl.class, "MapKeyImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(queryEClass, Query.class, "Query", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQuery_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQuery_Query(), theXMLTypePackage.getString(), "query", null, 1, 1, Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQuery_Hints(), this.getQueryHint(), null, "hints", null, 0, -1, Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(namedNativeQueryEClass, NamedNativeQuery.class, "NamedNativeQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamedNativeQuery_ResultClass(), theXMLTypePackage.getString(), "resultClass", null, 0, 1, NamedNativeQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedNativeQuery_ResultSetMapping(), theXMLTypePackage.getString(), "resultSetMapping", null, 0, 1, NamedNativeQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(namedQueryEClass, NamedQuery.class, "NamedQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(postLoadEClass, PostLoad.class, "PostLoad", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(postPersistEClass, PostPersist.class, "PostPersist", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(postRemoveEClass, PostRemove.class, "PostRemove", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(postUpdateEClass, PostUpdate.class, "PostUpdate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(prePersistEClass, PrePersist.class, "PrePersist", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(preRemoveEClass, PreRemove.class, "PreRemove", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(preUpdateEClass, PreUpdate.class, "PreUpdate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(primaryKeyJoinColumnEClass, PrimaryKeyJoinColumn.class, "PrimaryKeyJoinColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPrimaryKeyJoinColumn_ReferencedColumnName(), theXMLTypePackage.getString(), "referencedColumnName", null, 0, 1, PrimaryKeyJoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(queryHintEClass, QueryHint.class, "QueryHint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQueryHint_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, QueryHint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueryHint_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, QueryHint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractTableEClass, AbstractTable.class, "AbstractTable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractTable_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, AbstractTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractTable_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, AbstractTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractTable_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, AbstractTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractTable_UniqueConstraints(), this.getUniqueConstraint(), null, "uniqueConstraints", null, 0, -1, AbstractTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tableEClass, Table.class, "Table", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(secondaryTableEClass, SecondaryTable.class, "SecondaryTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSecondaryTable_PrimaryKeyJoinColumns(), this.getPrimaryKeyJoinColumn(), null, "primaryKeyJoinColumns", null, 0, -1, SecondaryTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(generatorEClass, Generator.class, "Generator", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGenerator_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Generator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenerator_InitialValue(), theXMLTypePackage.getIntObject(), "initialValue", null, 0, 1, Generator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenerator_AllocationSize(), theXMLTypePackage.getIntObject(), "allocationSize", null, 0, 1, Generator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sequenceGeneratorEClass, SequenceGenerator.class, "SequenceGenerator", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSequenceGenerator_SequenceName(), theXMLTypePackage.getString(), "sequenceName", null, 0, 1, SequenceGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sequenceGeneratorImplEClass, SequenceGeneratorImpl.class, "SequenceGeneratorImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(sqlResultSetMappingEClass, SqlResultSetMapping.class, "SqlResultSetMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSqlResultSetMapping_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, SqlResultSetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSqlResultSetMapping_EntityResults(), this.getEntityResult(), null, "entityResults", null, 0, -1, SqlResultSetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSqlResultSetMapping_ColumnResults(), this.getColumnResult(), null, "columnResults", null, 0, -1, SqlResultSetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tableGeneratorEClass, TableGenerator.class, "TableGenerator", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTableGenerator_Table(), theXMLTypePackage.getString(), "table", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableGenerator_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableGenerator_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableGenerator_PkColumnName(), theXMLTypePackage.getString(), "pkColumnName", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableGenerator_ValueColumnName(), theXMLTypePackage.getString(), "valueColumnName", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableGenerator_PkColumnValue(), theXMLTypePackage.getString(), "pkColumnValue", null, 0, 1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTableGenerator_UniqueConstraints(), this.getUniqueConstraint(), null, "uniqueConstraints", null, 0, -1, TableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tableGeneratorImplEClass, TableGeneratorImpl.class, "TableGeneratorImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(uniqueConstraintEClass, UniqueConstraint.class, "UniqueConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUniqueConstraint_ColumnNames(), theXMLTypePackage.getString(), "columnNames", null, 1, -1, UniqueConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		addEEnumLiteral(inheritanceTypeEEnum, InheritanceType.SINGLE_TABLE);
		addEEnumLiteral(inheritanceTypeEEnum, InheritanceType.JOINED);
		addEEnumLiteral(inheritanceTypeEEnum, InheritanceType.TABLE_PER_CLASS);

		initEEnum(temporalTypeEEnum, TemporalType.class, "TemporalType");
		addEEnumLiteral(temporalTypeEEnum, TemporalType.DATE);
		addEEnumLiteral(temporalTypeEEnum, TemporalType.TIME);
		addEEnumLiteral(temporalTypeEEnum, TemporalType.TIMESTAMP);

		// Initialize data types
		initEDataType(discriminatorValueEDataType, String.class, "DiscriminatorValue", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(enumeratedEDataType, Enumerator.class, "Enumerated", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(orderByEDataType, String.class, "OrderBy", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
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
		 * The meta object literal for the '<em><b>Xml Mapping Metadata Complete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE = eINSTANCE.getPersistenceUnitMetadata_XmlMappingMetadataComplete();

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
		 * The meta object literal for the '<em><b>Cascade Persist</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST = eINSTANCE.getPersistenceUnitDefaults_CascadePersist();

		/**
		 * The meta object literal for the '<em><b>Entity Listeners</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS = eINSTANCE.getPersistenceUnitDefaults_EntityListeners();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.TypeMapping <em>Type Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.TypeMapping
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTypeMapping()
		 * @generated
		 */
		public static final EClass TYPE_MAPPING = eINSTANCE.getTypeMapping();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TYPE_MAPPING__CLASS_NAME = eINSTANCE.getTypeMapping_ClassName();

		/**
		 * The meta object literal for the '<em><b>Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TYPE_MAPPING__ACCESS = eINSTANCE.getTypeMapping_Access();

		/**
		 * The meta object literal for the '<em><b>Metadata Complete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TYPE_MAPPING__METADATA_COMPLETE = eINSTANCE.getTypeMapping_MetadataComplete();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute TYPE_MAPPING__DESCRIPTION = eINSTANCE.getTypeMapping_Description();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference TYPE_MAPPING__ATTRIBUTES = eINSTANCE.getTypeMapping_Attributes();

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
		 * The meta object literal for the '<em><b>Id Class</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MAPPED_SUPERCLASS__ID_CLASS = eINSTANCE.getMappedSuperclass_IdClass();

		/**
		 * The meta object literal for the '<em><b>Exclude Default Listeners</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS = eINSTANCE.getMappedSuperclass_ExcludeDefaultListeners();

		/**
		 * The meta object literal for the '<em><b>Exclude Superclass Listeners</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS = eINSTANCE.getMappedSuperclass_ExcludeSuperclassListeners();

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
		 * The meta object literal for the '<em><b>Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY__TABLE = eINSTANCE.getEntity_Table();

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
		 * The meta object literal for the '<em><b>Exclude Default Listeners</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY__EXCLUDE_DEFAULT_LISTENERS = eINSTANCE.getEntity_ExcludeDefaultListeners();

		/**
		 * The meta object literal for the '<em><b>Exclude Superclass Listeners</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY__EXCLUDE_SUPERCLASS_LISTENERS = eINSTANCE.getEntity_ExcludeSuperclassListeners();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Embeddable <em>Embeddable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Embeddable
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddable()
		 * @generated
		 */
		public static final EClass EMBEDDABLE = eINSTANCE.getEmbeddable();

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
		 * The meta object literal for the '<em><b>Embedded Ids</b></em>' containment reference list feature.
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
		 * The meta object literal for the '<em><b>One To Manys</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__ONE_TO_MANYS = eINSTANCE.getAttributes_OneToManys();

		/**
		 * The meta object literal for the '<em><b>One To Ones</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__ONE_TO_ONES = eINSTANCE.getAttributes_OneToOnes();

		/**
		 * The meta object literal for the '<em><b>Many To Manys</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__MANY_TO_MANYS = eINSTANCE.getAttributes_ManyToManys();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.AttributeMapping <em>Attribute Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.AttributeMapping
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributeMapping()
		 * @generated
		 */
		public static final EClass ATTRIBUTE_MAPPING = eINSTANCE.getAttributeMapping();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ATTRIBUTE_MAPPING__NAME = eINSTANCE.getAttributeMapping_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.ColumnMapping <em>Column Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.ColumnMapping
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getColumnMapping()
		 * @generated
		 */
		public static final EClass COLUMN_MAPPING = eINSTANCE.getColumnMapping();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference COLUMN_MAPPING__COLUMN = eINSTANCE.getColumnMapping_Column();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping <em>Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getRelationshipMapping()
		 * @generated
		 */
		public static final EClass RELATIONSHIP_MAPPING = eINSTANCE.getRelationshipMapping();

		/**
		 * The meta object literal for the '<em><b>Target Entity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute RELATIONSHIP_MAPPING__TARGET_ENTITY = eINSTANCE.getRelationshipMapping_TargetEntity();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute RELATIONSHIP_MAPPING__FETCH = eINSTANCE.getRelationshipMapping_Fetch();

		/**
		 * The meta object literal for the '<em><b>Cascade</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference RELATIONSHIP_MAPPING__CASCADE = eINSTANCE.getRelationshipMapping_Cascade();

		/**
		 * The meta object literal for the '<em><b>Join Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference RELATIONSHIP_MAPPING__JOIN_TABLE = eINSTANCE.getRelationshipMapping_JoinTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping <em>Multi Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getMultiRelationshipMapping()
		 * @generated
		 */
		public static final EClass MULTI_RELATIONSHIP_MAPPING = eINSTANCE.getMultiRelationshipMapping();

		/**
		 * The meta object literal for the '<em><b>Order By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MULTI_RELATIONSHIP_MAPPING__ORDER_BY = eINSTANCE.getMultiRelationshipMapping_OrderBy();

		/**
		 * The meta object literal for the '<em><b>Map Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference MULTI_RELATIONSHIP_MAPPING__MAP_KEY = eINSTANCE.getMultiRelationshipMapping_MapKey();

		/**
		 * The meta object literal for the '<em><b>Mapped By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MULTI_RELATIONSHIP_MAPPING__MAPPED_BY = eINSTANCE.getMultiRelationshipMapping_MappedBy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.SingleRelationshipMapping <em>Single Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.SingleRelationshipMapping
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSingleRelationshipMapping()
		 * @generated
		 */
		public static final EClass SINGLE_RELATIONSHIP_MAPPING = eINSTANCE.getSingleRelationshipMapping();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute SINGLE_RELATIONSHIP_MAPPING__OPTIONAL = eINSTANCE.getSingleRelationshipMapping_Optional();

		/**
		 * The meta object literal for the '<em><b>Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS = eINSTANCE.getSingleRelationshipMapping_JoinColumns();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.IdImpl <em>Id Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.IdImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getIdImpl()
		 * @generated
		 */
		public static final EClass ID_IMPL = eINSTANCE.getIdImpl();

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
		 * The meta object literal for the '<em><b>Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EMBEDDED_ID__ATTRIBUTE_OVERRIDES = eINSTANCE.getEmbeddedId_AttributeOverrides();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedIdImpl <em>Embedded Id Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddedIdImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddedIdImpl()
		 * @generated
		 */
		public static final EClass EMBEDDED_ID_IMPL = eINSTANCE.getEmbeddedIdImpl();

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
		 * The meta object literal for the '<em><b>Lob</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute BASIC__LOB = eINSTANCE.getBasic_Lob();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.BasicImpl <em>Basic Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.BasicImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getBasicImpl()
		 * @generated
		 */
		public static final EClass BASIC_IMPL = eINSTANCE.getBasicImpl();

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
		 * The meta object literal for the '<em><b>Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute VERSION__TEMPORAL = eINSTANCE.getVersion_Temporal();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.VersionImpl <em>Version Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.VersionImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getVersionImpl()
		 * @generated
		 */
		public static final EClass VERSION_IMPL = eINSTANCE.getVersionImpl();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOneImpl <em>Many To One Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOneImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getManyToOneImpl()
		 * @generated
		 */
		public static final EClass MANY_TO_ONE_IMPL = eINSTANCE.getManyToOneImpl();

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
		 * The meta object literal for the '<em><b>Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ONE_TO_MANY__JOIN_COLUMNS = eINSTANCE.getOneToMany_JoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToManyImpl <em>One To Many Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.OneToManyImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToManyImpl()
		 * @generated
		 */
		public static final EClass ONE_TO_MANY_IMPL = eINSTANCE.getOneToManyImpl();

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
		 * The meta object literal for the '<em><b>Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getOneToOne_PrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOneImpl <em>One To One Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOneImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOneImpl()
		 * @generated
		 */
		public static final EClass ONE_TO_ONE_IMPL = eINSTANCE.getOneToOneImpl();

		/**
		 * The meta object literal for the '<em><b>Mapped By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ONE_TO_ONE__MAPPED_BY = eINSTANCE.getOneToOne_MappedBy();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToManyImpl <em>Many To Many Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToManyImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getManyToManyImpl()
		 * @generated
		 */
		public static final EClass MANY_TO_MANY_IMPL = eINSTANCE.getManyToManyImpl();

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
		 * The meta object literal for the '<em><b>Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EMBEDDED__ATTRIBUTE_OVERRIDES = eINSTANCE.getEmbedded_AttributeOverrides();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedImpl <em>Embedded Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddedImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddedImpl()
		 * @generated
		 */
		public static final EClass EMBEDDED_IMPL = eINSTANCE.getEmbeddedImpl();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.TransientImpl <em>Transient Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.TransientImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTransientImpl()
		 * @generated
		 */
		public static final EClass TRANSIENT_IMPL = eINSTANCE.getTransientImpl();

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
		 * The meta object literal for the '<em><b>Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ASSOCIATION_OVERRIDE__JOIN_COLUMNS = eINSTANCE.getAssociationOverride_JoinColumns();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.AttributeOverrideImpl <em>Attribute Override Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.AttributeOverrideImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributeOverrideImpl()
		 * @generated
		 */
		public static final EClass ATTRIBUTE_OVERRIDE_IMPL = eINSTANCE.getAttributeOverrideImpl();

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
		 * The meta object literal for the '<em><b>Cascade All</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute CASCADE_TYPE__CASCADE_ALL = eINSTANCE.getCascadeType_CascadeAll();

		/**
		 * The meta object literal for the '<em><b>Cascade Persist</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute CASCADE_TYPE__CASCADE_PERSIST = eINSTANCE.getCascadeType_CascadePersist();

		/**
		 * The meta object literal for the '<em><b>Cascade Merge</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute CASCADE_TYPE__CASCADE_MERGE = eINSTANCE.getCascadeType_CascadeMerge();

		/**
		 * The meta object literal for the '<em><b>Cascade Remove</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute CASCADE_TYPE__CASCADE_REMOVE = eINSTANCE.getCascadeType_CascadeRemove();

		/**
		 * The meta object literal for the '<em><b>Cascade Refresh</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute CASCADE_TYPE__CASCADE_REFRESH = eINSTANCE.getCascadeType_CascadeRefresh();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeTypeImpl <em>Cascade Type Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeTypeImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getCascadeTypeImpl()
		 * @generated
		 */
		public static final EClass CASCADE_TYPE_IMPL = eINSTANCE.getCascadeTypeImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.NamedColumn <em>Named Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.NamedColumn
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getNamedColumn()
		 * @generated
		 */
		public static final EClass NAMED_COLUMN = eINSTANCE.getNamedColumn();

		/**
		 * The meta object literal for the '<em><b>Column Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute NAMED_COLUMN__COLUMN_DEFINITION = eINSTANCE.getNamedColumn_ColumnDefinition();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute NAMED_COLUMN__NAME = eINSTANCE.getNamedColumn_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractColumn <em>Abstract Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractColumn
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAbstractColumn()
		 * @generated
		 */
		public static final EClass ABSTRACT_COLUMN = eINSTANCE.getAbstractColumn();

		/**
		 * The meta object literal for the '<em><b>Insertable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_COLUMN__INSERTABLE = eINSTANCE.getAbstractColumn_Insertable();

		/**
		 * The meta object literal for the '<em><b>Nullable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_COLUMN__NULLABLE = eINSTANCE.getAbstractColumn_Nullable();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_COLUMN__TABLE = eINSTANCE.getAbstractColumn_Table();

		/**
		 * The meta object literal for the '<em><b>Unique</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_COLUMN__UNIQUE = eINSTANCE.getAbstractColumn_Unique();

		/**
		 * The meta object literal for the '<em><b>Updatable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_COLUMN__UPDATABLE = eINSTANCE.getAbstractColumn_Updatable();

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
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN__LENGTH = eINSTANCE.getColumn_Length();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.ColumnImpl <em>Column Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.ColumnImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getColumnImpl()
		 * @generated
		 */
		public static final EClass COLUMN_IMPL = eINSTANCE.getColumnImpl();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValueImpl <em>Generated Value Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.GeneratedValueImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGeneratedValueImpl()
		 * @generated
		 */
		public static final EClass GENERATED_VALUE_IMPL = eINSTANCE.getGeneratedValueImpl();

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
		 * The meta object literal for the '<em><b>Referenced Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JOIN_COLUMN__REFERENCED_COLUMN_NAME = eINSTANCE.getJoinColumn_ReferencedColumnName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumnImpl <em>Join Column Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumnImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getJoinColumnImpl()
		 * @generated
		 */
		public static final EClass JOIN_COLUMN_IMPL = eINSTANCE.getJoinColumnImpl();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTableImpl <em>Join Table Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTableImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getJoinTableImpl()
		 * @generated
		 */
		public static final EClass JOIN_TABLE_IMPL = eINSTANCE.getJoinTableImpl();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.MapKeyImpl <em>Map Key Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.MapKeyImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getMapKeyImpl()
		 * @generated
		 */
		public static final EClass MAP_KEY_IMPL = eINSTANCE.getMapKeyImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Query <em>Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Query
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getQuery()
		 * @generated
		 */
		public static final EClass QUERY = eINSTANCE.getQuery();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute QUERY__NAME = eINSTANCE.getQuery_Name();

		/**
		 * The meta object literal for the '<em><b>Query</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute QUERY__QUERY = eINSTANCE.getQuery_Query();

		/**
		 * The meta object literal for the '<em><b>Hints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference QUERY__HINTS = eINSTANCE.getQuery_Hints();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.NamedQuery <em>Named Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.NamedQuery
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getNamedQuery()
		 * @generated
		 */
		public static final EClass NAMED_QUERY = eINSTANCE.getNamedQuery();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.AbstractTable <em>Abstract Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.AbstractTable
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAbstractTable()
		 * @generated
		 */
		public static final EClass ABSTRACT_TABLE = eINSTANCE.getAbstractTable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_TABLE__NAME = eINSTANCE.getAbstractTable_Name();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_TABLE__CATALOG = eINSTANCE.getAbstractTable_Catalog();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_TABLE__SCHEMA = eINSTANCE.getAbstractTable_Schema();

		/**
		 * The meta object literal for the '<em><b>Unique Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ABSTRACT_TABLE__UNIQUE_CONSTRAINTS = eINSTANCE.getAbstractTable_UniqueConstraints();

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
		 * The meta object literal for the '<em><b>Referenced Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = eINSTANCE.getPrimaryKeyJoinColumn_ReferencedColumnName();

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
		 * The meta object literal for the '<em><b>Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getSecondaryTable_PrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.Generator <em>Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.Generator
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getGenerator()
		 * @generated
		 */
		public static final EClass GENERATOR = eINSTANCE.getGenerator();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute GENERATOR__NAME = eINSTANCE.getGenerator_Name();

		/**
		 * The meta object literal for the '<em><b>Initial Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute GENERATOR__INITIAL_VALUE = eINSTANCE.getGenerator_InitialValue();

		/**
		 * The meta object literal for the '<em><b>Allocation Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute GENERATOR__ALLOCATION_SIZE = eINSTANCE.getGenerator_AllocationSize();

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
		 * The meta object literal for the '<em><b>Sequence Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute SEQUENCE_GENERATOR__SEQUENCE_NAME = eINSTANCE.getSequenceGenerator_SequenceName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGeneratorImpl <em>Sequence Generator Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.SequenceGeneratorImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSequenceGeneratorImpl()
		 * @generated
		 */
		public static final EClass SEQUENCE_GENERATOR_IMPL = eINSTANCE.getSequenceGeneratorImpl();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator <em>Table Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator()
		 * @generated
		 */
		public static final EClass TABLE_GENERATOR = eINSTANCE.getTableGenerator();

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
		 * The meta object literal for the '<em><b>Unique Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference TABLE_GENERATOR__UNIQUE_CONSTRAINTS = eINSTANCE.getTableGenerator_UniqueConstraints();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGeneratorImpl <em>Table Generator Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.orm.TableGeneratorImpl
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGeneratorImpl()
		 * @generated
		 */
		public static final EClass TABLE_GENERATOR_IMPL = eINSTANCE.getTableGeneratorImpl();

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
		 * The meta object literal for the '<em>Order By</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOrderBy()
		 * @generated
		 */
		public static final EDataType ORDER_BY = eINSTANCE.getOrderBy();

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
