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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.jpt.core.jpa2.resource.persistence.Persistence2_0Package;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.persistence.PersistencePackage;

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
 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Factory
 * @model kind="package"
 * @generated
 */
public class Orm2_0Package extends EPackageImpl
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
	public static final String eNS_URI = "jpt2_0.orm.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.core.jpa2.resource.orm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final Orm2_0Package eINSTANCE = org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEntityMappings
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlEntityMappings()
	 * @generated
	 */
	public static final int XML_ENTITY_MAPPINGS = 0;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__NAMED_QUERIES = OrmPackage.XML_ENTITY_MAPPINGS__NAMED_QUERIES;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES = OrmPackage.XML_ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__ACCESS = OrmPackage.XML_ENTITY_MAPPINGS__ACCESS;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__VERSION = OrmPackage.XML_ENTITY_MAPPINGS__VERSION;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__DESCRIPTION = OrmPackage.XML_ENTITY_MAPPINGS__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Persistence Unit Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA = OrmPackage.XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__PACKAGE = OrmPackage.XML_ENTITY_MAPPINGS__PACKAGE;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__SCHEMA = OrmPackage.XML_ENTITY_MAPPINGS__SCHEMA;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__CATALOG = OrmPackage.XML_ENTITY_MAPPINGS__CATALOG;

	/**
	 * The feature id for the '<em><b>Sequence Generators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__SEQUENCE_GENERATORS = OrmPackage.XML_ENTITY_MAPPINGS__SEQUENCE_GENERATORS;

	/**
	 * The feature id for the '<em><b>Table Generators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__TABLE_GENERATORS = OrmPackage.XML_ENTITY_MAPPINGS__TABLE_GENERATORS;

	/**
	 * The feature id for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS = OrmPackage.XML_ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS;

	/**
	 * The feature id for the '<em><b>Mapped Superclasses</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__MAPPED_SUPERCLASSES = OrmPackage.XML_ENTITY_MAPPINGS__MAPPED_SUPERCLASSES;

	/**
	 * The feature id for the '<em><b>Entities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__ENTITIES = OrmPackage.XML_ENTITY_MAPPINGS__ENTITIES;

	/**
	 * The feature id for the '<em><b>Embeddables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__EMBEDDABLES = OrmPackage.XML_ENTITY_MAPPINGS__EMBEDDABLES;

	/**
	 * The number of structural features of the '<em>Xml Entity Mappings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_FEATURE_COUNT = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitMetadata <em>Xml Persistence Unit Metadata</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitMetadata
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlPersistenceUnitMetadata()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA = 1;

	/**
	 * The feature id for the '<em><b>Xml Mapping Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE = OrmPackage.XML_PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Persistence Unit Defaults</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS = OrmPackage.XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA__DESCRIPTION = OrmPackage.XML_PERSISTENCE_UNIT_METADATA_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Persistence Unit Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA_FEATURE_COUNT = OrmPackage.XML_PERSISTENCE_UNIT_METADATA_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitDefaults <em>Xml Persistence Unit Defaults</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitDefaults
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlPersistenceUnitDefaults()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS = 2;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG;

	/**
	 * The feature id for the '<em><b>Cascade Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__DESCRIPTION = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Delimited Identifiers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__DELIMITED_IDENTIFIERS = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Persistence Unit Defaults</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS_FEATURE_COUNT = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEntity <em>Xml Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEntity
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlEntity()
	 * @generated
	 */
	public static final int XML_ENTITY = 3;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ACCESS = OrmPackage.XML_ENTITY__ACCESS;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CLASS_NAME = OrmPackage.XML_ENTITY__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__METADATA_COMPLETE = OrmPackage.XML_ENTITY__METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__DESCRIPTION = OrmPackage.XML_ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ATTRIBUTES = OrmPackage.XML_ENTITY__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__NAMED_QUERIES = OrmPackage.XML_ENTITY__NAMED_QUERIES;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__NAMED_NATIVE_QUERIES = OrmPackage.XML_ENTITY__NAMED_NATIVE_QUERIES;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SEQUENCE_GENERATOR = OrmPackage.XML_ENTITY__SEQUENCE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__TABLE_GENERATOR = OrmPackage.XML_ENTITY__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PRE_PERSIST = OrmPackage.XML_ENTITY__PRE_PERSIST;

	/**
	 * The feature id for the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__POST_PERSIST = OrmPackage.XML_ENTITY__POST_PERSIST;

	/**
	 * The feature id for the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PRE_REMOVE = OrmPackage.XML_ENTITY__PRE_REMOVE;

	/**
	 * The feature id for the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__POST_REMOVE = OrmPackage.XML_ENTITY__POST_REMOVE;

	/**
	 * The feature id for the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PRE_UPDATE = OrmPackage.XML_ENTITY__PRE_UPDATE;

	/**
	 * The feature id for the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__POST_UPDATE = OrmPackage.XML_ENTITY__POST_UPDATE;

	/**
	 * The feature id for the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__POST_LOAD = OrmPackage.XML_ENTITY__POST_LOAD;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__NAME = OrmPackage.XML_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__TABLE = OrmPackage.XML_ENTITY__TABLE;

	/**
	 * The feature id for the '<em><b>Secondary Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SECONDARY_TABLES = OrmPackage.XML_ENTITY__SECONDARY_TABLES;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PRIMARY_KEY_JOIN_COLUMNS = OrmPackage.XML_ENTITY__PRIMARY_KEY_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Id Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ID_CLASS = OrmPackage.XML_ENTITY__ID_CLASS;

	/**
	 * The feature id for the '<em><b>Inheritance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__INHERITANCE = OrmPackage.XML_ENTITY__INHERITANCE;

	/**
	 * The feature id for the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__DISCRIMINATOR_VALUE = OrmPackage.XML_ENTITY__DISCRIMINATOR_VALUE;

	/**
	 * The feature id for the '<em><b>Discriminator Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__DISCRIMINATOR_COLUMN = OrmPackage.XML_ENTITY__DISCRIMINATOR_COLUMN;

	/**
	 * The feature id for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SQL_RESULT_SET_MAPPINGS = OrmPackage.XML_ENTITY__SQL_RESULT_SET_MAPPINGS;

	/**
	 * The feature id for the '<em><b>Exclude Default Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__EXCLUDE_DEFAULT_LISTENERS = OrmPackage.XML_ENTITY__EXCLUDE_DEFAULT_LISTENERS;

	/**
	 * The feature id for the '<em><b>Exclude Superclass Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__EXCLUDE_SUPERCLASS_LISTENERS = OrmPackage.XML_ENTITY__EXCLUDE_SUPERCLASS_LISTENERS;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ENTITY_LISTENERS = OrmPackage.XML_ENTITY__ENTITY_LISTENERS;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ATTRIBUTE_OVERRIDES = OrmPackage.XML_ENTITY__ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ASSOCIATION_OVERRIDES = OrmPackage.XML_ENTITY__ASSOCIATION_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CACHEABLE = OrmPackage.XML_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_FEATURE_COUNT = OrmPackage.XML_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlMappedSuperclass
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlMappedSuperclass()
	 * @generated
	 */
	public static final int XML_MAPPED_SUPERCLASS = 4;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ACCESS = OrmPackage.XML_MAPPED_SUPERCLASS__ACCESS;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CLASS_NAME = OrmPackage.XML_MAPPED_SUPERCLASS__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__METADATA_COMPLETE = OrmPackage.XML_MAPPED_SUPERCLASS__METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__DESCRIPTION = OrmPackage.XML_MAPPED_SUPERCLASS__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ATTRIBUTES = OrmPackage.XML_MAPPED_SUPERCLASS__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PRE_PERSIST = OrmPackage.XML_MAPPED_SUPERCLASS__PRE_PERSIST;

	/**
	 * The feature id for the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__POST_PERSIST = OrmPackage.XML_MAPPED_SUPERCLASS__POST_PERSIST;

	/**
	 * The feature id for the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PRE_REMOVE = OrmPackage.XML_MAPPED_SUPERCLASS__PRE_REMOVE;

	/**
	 * The feature id for the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__POST_REMOVE = OrmPackage.XML_MAPPED_SUPERCLASS__POST_REMOVE;

	/**
	 * The feature id for the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PRE_UPDATE = OrmPackage.XML_MAPPED_SUPERCLASS__PRE_UPDATE;

	/**
	 * The feature id for the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__POST_UPDATE = OrmPackage.XML_MAPPED_SUPERCLASS__POST_UPDATE;

	/**
	 * The feature id for the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__POST_LOAD = OrmPackage.XML_MAPPED_SUPERCLASS__POST_LOAD;

	/**
	 * The feature id for the '<em><b>Id Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ID_CLASS = OrmPackage.XML_MAPPED_SUPERCLASS__ID_CLASS;

	/**
	 * The feature id for the '<em><b>Exclude Default Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS = OrmPackage.XML_MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS;

	/**
	 * The feature id for the '<em><b>Exclude Superclass Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS = OrmPackage.XML_MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS = OrmPackage.XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS;

	/**
	 * The number of structural features of the '<em>Xml Mapped Superclass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_FEATURE_COUNT = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddable <em>Xml Embeddable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddable
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlEmbeddable()
	 * @generated
	 */
	public static final int XML_EMBEDDABLE = 5;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__ACCESS = OrmPackage.XML_EMBEDDABLE__ACCESS;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__CLASS_NAME = OrmPackage.XML_EMBEDDABLE__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__METADATA_COMPLETE = OrmPackage.XML_EMBEDDABLE__METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__DESCRIPTION = OrmPackage.XML_EMBEDDABLE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__ATTRIBUTES = OrmPackage.XML_EMBEDDABLE__ATTRIBUTES;

	/**
	 * The number of structural features of the '<em>Xml Embeddable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_FEATURE_COUNT = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.Attributes <em>Attributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Attributes
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getAttributes()
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
	public static final int ATTRIBUTES__IDS = OrmPackage.ATTRIBUTES__IDS;

	/**
	 * The feature id for the '<em><b>Embedded Ids</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__EMBEDDED_IDS = OrmPackage.ATTRIBUTES__EMBEDDED_IDS;

	/**
	 * The feature id for the '<em><b>Basics</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__BASICS = OrmPackage.ATTRIBUTES__BASICS;

	/**
	 * The feature id for the '<em><b>Versions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__VERSIONS = OrmPackage.ATTRIBUTES__VERSIONS;

	/**
	 * The feature id for the '<em><b>Many To Ones</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__MANY_TO_ONES = OrmPackage.ATTRIBUTES__MANY_TO_ONES;

	/**
	 * The feature id for the '<em><b>One To Manys</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__ONE_TO_MANYS = OrmPackage.ATTRIBUTES__ONE_TO_MANYS;

	/**
	 * The feature id for the '<em><b>One To Ones</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__ONE_TO_ONES = OrmPackage.ATTRIBUTES__ONE_TO_ONES;

	/**
	 * The feature id for the '<em><b>Many To Manys</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__MANY_TO_MANYS = OrmPackage.ATTRIBUTES__MANY_TO_MANYS;

	/**
	 * The feature id for the '<em><b>Embeddeds</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__EMBEDDEDS = OrmPackage.ATTRIBUTES__EMBEDDEDS;

	/**
	 * The feature id for the '<em><b>Transients</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__TRANSIENTS = OrmPackage.ATTRIBUTES__TRANSIENTS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__DESCRIPTION = OrmPackage.ATTRIBUTES_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element Collections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__ELEMENT_COLLECTIONS = OrmPackage.ATTRIBUTES_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Attributes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES_FEATURE_COUNT = OrmPackage.ATTRIBUTES_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeMapping
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlAttributeMapping()
	 * @generated
	 */
	public static final int XML_ATTRIBUTE_MAPPING = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING__NAME = OrmPackage.XML_ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING__ACCESS = OrmPackage.XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Attribute Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING_FEATURE_COUNT = OrmPackage.XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection <em>Xml Element Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlElementCollection()
	 * @generated
	 */
	public static final int XML_ELEMENT_COLLECTION = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__NAME = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ACCESS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__TARGET_CLASS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__FETCH = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ORDER_BY = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ORDER_COLUMN = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_CLASS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Map Key Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__COLUMN = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__TEMPORAL = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ENUMERATED = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__LOB = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Collection Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__COLLECTION_TABLE = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 18;

	/**
	 * The number of structural features of the '<em>Xml Element Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_FEATURE_COUNT = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 19;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlId <em>Xml Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlId
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlId()
	 * @generated
	 */
	public static final int XML_ID = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__NAME = OrmPackage.XML_ID__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__COLUMN = OrmPackage.XML_ID__COLUMN;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__LOB = OrmPackage.XML_ID__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__TEMPORAL = OrmPackage.XML_ID__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__ENUMERATED = OrmPackage.XML_ID__ENUMERATED;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__SEQUENCE_GENERATOR = OrmPackage.XML_ID__SEQUENCE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__TABLE_GENERATOR = OrmPackage.XML_ID__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__GENERATED_VALUE = OrmPackage.XML_ID__GENERATED_VALUE;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__ACCESS = OrmPackage.XML_ID_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_FEATURE_COUNT = OrmPackage.XML_ID_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddedId
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlEmbeddedId()
	 * @generated
	 */
	public static final int XML_EMBEDDED_ID = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__NAME = OrmPackage.XML_EMBEDDED_ID__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__ATTRIBUTE_OVERRIDES = OrmPackage.XML_EMBEDDED_ID__ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__ACCESS = OrmPackage.XML_EMBEDDED_ID_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Embedded Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID_FEATURE_COUNT = OrmPackage.XML_EMBEDDED_ID_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbedded <em>Xml Embedded</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbedded
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlEmbedded()
	 * @generated
	 */
	public static final int XML_EMBEDDED = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__NAME = OrmPackage.XML_EMBEDDED__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ATTRIBUTE_OVERRIDES = OrmPackage.XML_EMBEDDED__ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ACCESS = OrmPackage.XML_EMBEDDED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ASSOCIATION_OVERRIDES = OrmPackage.XML_EMBEDDED_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Embedded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_FEATURE_COUNT = OrmPackage.XML_EMBEDDED_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlBasic <em>Xml Basic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlBasic
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlBasic()
	 * @generated
	 */
	public static final int XML_BASIC = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__NAME = OrmPackage.XML_BASIC__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__COLUMN = OrmPackage.XML_BASIC__COLUMN;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__LOB = OrmPackage.XML_BASIC__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__TEMPORAL = OrmPackage.XML_BASIC__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__ENUMERATED = OrmPackage.XML_BASIC__ENUMERATED;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__FETCH = OrmPackage.XML_BASIC__FETCH;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__OPTIONAL = OrmPackage.XML_BASIC__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__ACCESS = OrmPackage.XML_BASIC_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Basic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_FEATURE_COUNT = OrmPackage.XML_BASIC_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlVersion <em>Xml Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlVersion
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlVersion()
	 * @generated
	 */
	public static final int XML_VERSION = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__NAME = OrmPackage.XML_VERSION__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__COLUMN = OrmPackage.XML_VERSION__COLUMN;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__LOB = OrmPackage.XML_VERSION__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__TEMPORAL = OrmPackage.XML_VERSION__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__ENUMERATED = OrmPackage.XML_VERSION__ENUMERATED;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__ACCESS = OrmPackage.XML_VERSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_FEATURE_COUNT = OrmPackage.XML_VERSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne <em>Xml Many To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlManyToOne()
	 * @generated
	 */
	public static final int XML_MANY_TO_ONE = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__NAME = OrmPackage.XML_MANY_TO_ONE__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__TARGET_ENTITY = OrmPackage.XML_MANY_TO_ONE__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__FETCH = OrmPackage.XML_MANY_TO_ONE__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__CASCADE = OrmPackage.XML_MANY_TO_ONE__CASCADE;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__JOIN_TABLE = OrmPackage.XML_MANY_TO_ONE__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__JOIN_COLUMNS = OrmPackage.XML_MANY_TO_ONE__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__OPTIONAL = OrmPackage.XML_MANY_TO_ONE__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__ACCESS = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mapped By Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__MAPPED_BY_ID = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__ID = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Many To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_FEATURE_COUNT = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany <em>Xml One To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlOneToMany()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__NAME = OrmPackage.XML_ONE_TO_MANY__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__TARGET_ENTITY = OrmPackage.XML_ONE_TO_MANY__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__FETCH = OrmPackage.XML_ONE_TO_MANY__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__CASCADE = OrmPackage.XML_ONE_TO_MANY__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAPPED_BY = OrmPackage.XML_ONE_TO_MANY__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__JOIN_TABLE = OrmPackage.XML_ONE_TO_MANY__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ORDER_BY = OrmPackage.XML_ONE_TO_MANY__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY = OrmPackage.XML_ONE_TO_MANY__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__JOIN_COLUMNS = OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ACCESS = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ORDER_COLUMN = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_CLASS = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Map Key Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_TEMPORAL = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_ENUMERATED = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_COLUMN = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_JOIN_COLUMNS = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Orphan Removal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ORPHAN_REMOVAL = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Xml One To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_FEATURE_COUNT = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne <em>Xml One To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlOneToOne()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__NAME = OrmPackage.XML_ONE_TO_ONE__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__TARGET_ENTITY = OrmPackage.XML_ONE_TO_ONE__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__FETCH = OrmPackage.XML_ONE_TO_ONE__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__CASCADE = OrmPackage.XML_ONE_TO_ONE__CASCADE;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__JOIN_TABLE = OrmPackage.XML_ONE_TO_ONE__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__JOIN_COLUMNS = OrmPackage.XML_ONE_TO_ONE__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__OPTIONAL = OrmPackage.XML_ONE_TO_ONE__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__MAPPED_BY = OrmPackage.XML_ONE_TO_ONE__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS = OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__ACCESS = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Orphan Removal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__ORPHAN_REMOVAL = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Mapped By Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__MAPPED_BY_ID = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__ID = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Xml One To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_FEATURE_COUNT = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlManyToMany()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__NAME = OrmPackage.XML_MANY_TO_MANY__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__TARGET_ENTITY = OrmPackage.XML_MANY_TO_MANY__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__FETCH = OrmPackage.XML_MANY_TO_MANY__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__CASCADE = OrmPackage.XML_MANY_TO_MANY__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAPPED_BY = OrmPackage.XML_MANY_TO_MANY__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__JOIN_TABLE = OrmPackage.XML_MANY_TO_MANY__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ORDER_BY = OrmPackage.XML_MANY_TO_MANY__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY = OrmPackage.XML_MANY_TO_MANY__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ACCESS = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ORDER_COLUMN = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_CLASS = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Map Key Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_TEMPORAL = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_ENUMERATED = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_COLUMN = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Xml Many To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_FEATURE_COUNT = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlTransient <em>Xml Transient</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlTransient
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlTransient()
	 * @generated
	 */
	public static final int XML_TRANSIENT = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT__NAME = OrmPackage.XML_TRANSIENT__NAME;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT__ACCESS = OrmPackage.XML_TRANSIENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Transient</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT_FEATURE_COUNT = OrmPackage.XML_TRANSIENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride <em>Xml Association Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlAssociationOverride()
	 * @generated
	 */
	public static final int XML_ASSOCIATION_OVERRIDE = 19;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS = OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE__NAME = OrmPackage.XML_ASSOCIATION_OVERRIDE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE__DESCRIPTION = OrmPackage.XML_ASSOCIATION_OVERRIDE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE__JOIN_TABLE = OrmPackage.XML_ASSOCIATION_OVERRIDE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Association Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE_FEATURE_COUNT = OrmPackage.XML_ASSOCIATION_OVERRIDE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeOverride <em>Xml Attribute Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeOverride
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlAttributeOverride()
	 * @generated
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE = 20;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE__COLUMN = OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE__NAME = OrmPackage.XML_ATTRIBUTE_OVERRIDE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE__DESCRIPTION = OrmPackage.XML_ATTRIBUTE_OVERRIDE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Attribute Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE_FEATURE_COUNT = OrmPackage.XML_ATTRIBUTE_OVERRIDE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.EntityListeners <em>Entity Listeners</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.EntityListeners
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getEntityListeners()
	 * @generated
	 */
	public static final int ENTITY_LISTENERS = 21;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENERS__ENTITY_LISTENERS = OrmPackage.ENTITY_LISTENERS__ENTITY_LISTENERS;

	/**
	 * The number of structural features of the '<em>Entity Listeners</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENERS_FEATURE_COUNT = OrmPackage.ENTITY_LISTENERS_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.EntityListener <em>Entity Listener</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.EntityListener
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getEntityListener()
	 * @generated
	 */
	public static final int ENTITY_LISTENER = 22;

	/**
	 * The feature id for the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__PRE_PERSIST = OrmPackage.ENTITY_LISTENER__PRE_PERSIST;

	/**
	 * The feature id for the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__POST_PERSIST = OrmPackage.ENTITY_LISTENER__POST_PERSIST;

	/**
	 * The feature id for the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__PRE_REMOVE = OrmPackage.ENTITY_LISTENER__PRE_REMOVE;

	/**
	 * The feature id for the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__POST_REMOVE = OrmPackage.ENTITY_LISTENER__POST_REMOVE;

	/**
	 * The feature id for the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__PRE_UPDATE = OrmPackage.ENTITY_LISTENER__PRE_UPDATE;

	/**
	 * The feature id for the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__POST_UPDATE = OrmPackage.ENTITY_LISTENER__POST_UPDATE;

	/**
	 * The feature id for the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__POST_LOAD = OrmPackage.ENTITY_LISTENER__POST_LOAD;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__CLASS_NAME = OrmPackage.ENTITY_LISTENER__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__DESCRIPTION = OrmPackage.ENTITY_LISTENER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Entity Listener</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER_FEATURE_COUNT = OrmPackage.ENTITY_LISTENER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlQuery <em>Xml Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlQuery
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlQuery()
	 * @generated
	 */
	public static final int XML_QUERY = 23;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY__NAME = OrmPackage.XML_QUERY__NAME;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY__QUERY = OrmPackage.XML_QUERY__QUERY;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY__HINTS = OrmPackage.XML_QUERY__HINTS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY__DESCRIPTION = OrmPackage.XML_QUERY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_FEATURE_COUNT = OrmPackage.XML_QUERY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedNativeQuery <em>Xml Named Native Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedNativeQuery
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlNamedNativeQuery()
	 * @generated
	 */
	public static final int XML_NAMED_NATIVE_QUERY = 24;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__NAME = OrmPackage.XML_NAMED_NATIVE_QUERY__NAME;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__QUERY = OrmPackage.XML_NAMED_NATIVE_QUERY__QUERY;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__HINTS = OrmPackage.XML_NAMED_NATIVE_QUERY__HINTS;

	/**
	 * The feature id for the '<em><b>Result Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__RESULT_CLASS = OrmPackage.XML_NAMED_NATIVE_QUERY__RESULT_CLASS;

	/**
	 * The feature id for the '<em><b>Result Set Mapping</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING = OrmPackage.XML_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__DESCRIPTION = OrmPackage.XML_NAMED_NATIVE_QUERY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Named Native Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY_FEATURE_COUNT = OrmPackage.XML_NAMED_NATIVE_QUERY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedQuery <em>Xml Named Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedQuery
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlNamedQuery()
	 * @generated
	 */
	public static final int XML_NAMED_QUERY = 25;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY__NAME = OrmPackage.XML_NAMED_QUERY__NAME;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY__QUERY = OrmPackage.XML_NAMED_QUERY__QUERY;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY__HINTS = OrmPackage.XML_NAMED_QUERY__HINTS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY__DESCRIPTION = OrmPackage.XML_NAMED_QUERY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Lock Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY__LOCK_MODE = OrmPackage.XML_NAMED_QUERY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Named Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY_FEATURE_COUNT = OrmPackage.XML_NAMED_QUERY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlGenerator <em>Xml Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlGenerator
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlGenerator()
	 * @generated
	 */
	public static final int XML_GENERATOR = 26;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__NAME = OrmPackage.XML_GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__INITIAL_VALUE = OrmPackage.XML_GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__ALLOCATION_SIZE = OrmPackage.XML_GENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__DESCRIPTION = OrmPackage.XML_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR_FEATURE_COUNT = OrmPackage.XML_GENERATOR_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator <em>Xml Sequence Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlSequenceGenerator()
	 * @generated
	 */
	public static final int XML_SEQUENCE_GENERATOR = 27;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__NAME = OrmPackage.XML_SEQUENCE_GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__INITIAL_VALUE = OrmPackage.XML_SEQUENCE_GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__ALLOCATION_SIZE = OrmPackage.XML_SEQUENCE_GENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__SEQUENCE_NAME = OrmPackage.XML_SEQUENCE_GENERATOR__SEQUENCE_NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__DESCRIPTION = OrmPackage.XML_SEQUENCE_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__CATALOG = OrmPackage.XML_SEQUENCE_GENERATOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__SCHEMA = OrmPackage.XML_SEQUENCE_GENERATOR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Sequence Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR_FEATURE_COUNT = OrmPackage.XML_SEQUENCE_GENERATOR_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlTableGenerator <em>Xml Table Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlTableGenerator
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlTableGenerator()
	 * @generated
	 */
	public static final int XML_TABLE_GENERATOR = 28;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__NAME = OrmPackage.XML_TABLE_GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__INITIAL_VALUE = OrmPackage.XML_TABLE_GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__ALLOCATION_SIZE = OrmPackage.XML_TABLE_GENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__TABLE = OrmPackage.XML_TABLE_GENERATOR__TABLE;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__CATALOG = OrmPackage.XML_TABLE_GENERATOR__CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__SCHEMA = OrmPackage.XML_TABLE_GENERATOR__SCHEMA;

	/**
	 * The feature id for the '<em><b>Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__PK_COLUMN_NAME = OrmPackage.XML_TABLE_GENERATOR__PK_COLUMN_NAME;

	/**
	 * The feature id for the '<em><b>Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__VALUE_COLUMN_NAME = OrmPackage.XML_TABLE_GENERATOR__VALUE_COLUMN_NAME;

	/**
	 * The feature id for the '<em><b>Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__PK_COLUMN_VALUE = OrmPackage.XML_TABLE_GENERATOR__PK_COLUMN_VALUE;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__UNIQUE_CONSTRAINTS = OrmPackage.XML_TABLE_GENERATOR__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__DESCRIPTION = OrmPackage.XML_TABLE_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Table Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR_FEATURE_COUNT = OrmPackage.XML_TABLE_GENERATOR_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.SqlResultSetMapping <em>Sql Result Set Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.SqlResultSetMapping
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getSqlResultSetMapping()
	 * @generated
	 */
	public static final int SQL_RESULT_SET_MAPPING = 29;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING__NAME = OrmPackage.SQL_RESULT_SET_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Entity Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING__ENTITY_RESULTS = OrmPackage.SQL_RESULT_SET_MAPPING__ENTITY_RESULTS;

	/**
	 * The feature id for the '<em><b>Column Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING__COLUMN_RESULTS = OrmPackage.SQL_RESULT_SET_MAPPING__COLUMN_RESULTS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING__DESCRIPTION = OrmPackage.SQL_RESULT_SET_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sql Result Set Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING_FEATURE_COUNT = OrmPackage.SQL_RESULT_SET_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.EventMethod <em>Event Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.EventMethod
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getEventMethod()
	 * @generated
	 */
	public static final int EVENT_METHOD = 30;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EVENT_METHOD__METHOD_NAME = OrmPackage.EVENT_METHOD__METHOD_NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EVENT_METHOD__DESCRIPTION = OrmPackage.EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Event Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EVENT_METHOD_FEATURE_COUNT = OrmPackage.EVENT_METHOD_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PostLoad <em>Post Load</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PostLoad
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPostLoad()
	 * @generated
	 */
	public static final int POST_LOAD = 31;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_LOAD__METHOD_NAME = OrmPackage.POST_LOAD__METHOD_NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_LOAD__DESCRIPTION = OrmPackage.POST_LOAD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Post Load</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_LOAD_FEATURE_COUNT = OrmPackage.POST_LOAD_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PostPersist <em>Post Persist</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PostPersist
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPostPersist()
	 * @generated
	 */
	public static final int POST_PERSIST = 32;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_PERSIST__METHOD_NAME = OrmPackage.POST_PERSIST__METHOD_NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_PERSIST__DESCRIPTION = OrmPackage.POST_PERSIST_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Post Persist</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_PERSIST_FEATURE_COUNT = OrmPackage.POST_PERSIST_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PostRemove <em>Post Remove</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PostRemove
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPostRemove()
	 * @generated
	 */
	public static final int POST_REMOVE = 33;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_REMOVE__METHOD_NAME = OrmPackage.POST_REMOVE__METHOD_NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_REMOVE__DESCRIPTION = OrmPackage.POST_REMOVE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Post Remove</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_REMOVE_FEATURE_COUNT = OrmPackage.POST_REMOVE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PostUpdate <em>Post Update</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PostUpdate
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPostUpdate()
	 * @generated
	 */
	public static final int POST_UPDATE = 34;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_UPDATE__METHOD_NAME = OrmPackage.POST_UPDATE__METHOD_NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_UPDATE__DESCRIPTION = OrmPackage.POST_UPDATE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Post Update</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_UPDATE_FEATURE_COUNT = OrmPackage.POST_UPDATE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PrePersist <em>Pre Persist</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PrePersist
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPrePersist()
	 * @generated
	 */
	public static final int PRE_PERSIST = 35;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_PERSIST__METHOD_NAME = OrmPackage.PRE_PERSIST__METHOD_NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_PERSIST__DESCRIPTION = OrmPackage.PRE_PERSIST_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Pre Persist</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_PERSIST_FEATURE_COUNT = OrmPackage.PRE_PERSIST_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PreRemove <em>Pre Remove</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PreRemove
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPreRemove()
	 * @generated
	 */
	public static final int PRE_REMOVE = 36;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_REMOVE__METHOD_NAME = OrmPackage.PRE_REMOVE__METHOD_NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_REMOVE__DESCRIPTION = OrmPackage.PRE_REMOVE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Pre Remove</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_REMOVE_FEATURE_COUNT = OrmPackage.PRE_REMOVE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PreUpdate <em>Pre Update</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PreUpdate
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPreUpdate()
	 * @generated
	 */
	public static final int PRE_UPDATE = 37;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_UPDATE__METHOD_NAME = OrmPackage.PRE_UPDATE__METHOD_NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_UPDATE__DESCRIPTION = OrmPackage.PRE_UPDATE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Pre Update</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_UPDATE_FEATURE_COUNT = OrmPackage.PRE_UPDATE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlQueryHint <em>Xml Query Hint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlQueryHint
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlQueryHint()
	 * @generated
	 */
	public static final int XML_QUERY_HINT = 38;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_HINT__NAME = OrmPackage.XML_QUERY_HINT__NAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_HINT__VALUE = OrmPackage.XML_QUERY_HINT__VALUE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_HINT__DESCRIPTION = OrmPackage.XML_QUERY_HINT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Query Hint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_HINT_FEATURE_COUNT = OrmPackage.XML_QUERY_HINT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlCollectionTable <em>Xml Collection Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlCollectionTable
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlCollectionTable()
	 * @generated
	 */
	public static final int XML_COLLECTION_TABLE = 39;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__NAME = OrmPackage.ABSTRACT_XML_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__CATALOG = OrmPackage.ABSTRACT_XML_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__SCHEMA = OrmPackage.ABSTRACT_XML_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__UNIQUE_CONSTRAINTS = OrmPackage.ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__JOIN_COLUMNS = OrmPackage.ABSTRACT_XML_TABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Collection Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE_FEATURE_COUNT = OrmPackage.ABSTRACT_XML_TABLE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn <em>Xml Order Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlOrderColumn()
	 * @generated
	 */
	public static final int XML_ORDER_COLUMN = 40;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__COLUMN_DEFINITION = OrmPackage.ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__NAME = OrmPackage.ABSTRACT_XML_NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__INSERTABLE = OrmPackage.ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__NULLABLE = OrmPackage.ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__UPDATABLE = OrmPackage.ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__TABLE = OrmPackage.ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Xml Order Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN_FEATURE_COUNT = OrmPackage.ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyColumn <em>Xml Map Key Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyColumn
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlMapKeyColumn()
	 * @generated
	 */
	public static final int XML_MAP_KEY_COLUMN = 41;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_COLUMN__COLUMN_DEFINITION = OrmPackage.XML_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_COLUMN__NAME = OrmPackage.XML_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_COLUMN__INSERTABLE = OrmPackage.XML_COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_COLUMN__NULLABLE = OrmPackage.XML_COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_COLUMN__TABLE = OrmPackage.XML_COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_COLUMN__UNIQUE = OrmPackage.XML_COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_COLUMN__UPDATABLE = OrmPackage.XML_COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_COLUMN__LENGTH = OrmPackage.XML_COLUMN__LENGTH;

	/**
	 * The feature id for the '<em><b>Precision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_COLUMN__PRECISION = OrmPackage.XML_COLUMN__PRECISION;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_COLUMN__SCALE = OrmPackage.XML_COLUMN__SCALE;

	/**
	 * The number of structural features of the '<em>Xml Map Key Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_COLUMN_FEATURE_COUNT = OrmPackage.XML_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyJoinColumn <em>Xml Map Key Join Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyJoinColumn
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlMapKeyJoinColumn()
	 * @generated
	 */
	public static final int XML_MAP_KEY_JOIN_COLUMN = 42;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_JOIN_COLUMN__COLUMN_DEFINITION = OrmPackage.XML_JOIN_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_JOIN_COLUMN__NAME = OrmPackage.XML_JOIN_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_JOIN_COLUMN__INSERTABLE = OrmPackage.XML_JOIN_COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_JOIN_COLUMN__NULLABLE = OrmPackage.XML_JOIN_COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_JOIN_COLUMN__TABLE = OrmPackage.XML_JOIN_COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_JOIN_COLUMN__UNIQUE = OrmPackage.XML_JOIN_COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_JOIN_COLUMN__UPDATABLE = OrmPackage.XML_JOIN_COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = OrmPackage.XML_JOIN_COLUMN__REFERENCED_COLUMN_NAME;

	/**
	 * The number of structural features of the '<em>Xml Map Key Join Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_JOIN_COLUMN_FEATURE_COUNT = OrmPackage.XML_JOIN_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyClass <em>Xml Map Key Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyClass
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlMapKeyClass()
	 * @generated
	 */
	public static final int XML_MAP_KEY_CLASS = 43;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_CLASS__CLASS_NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Map Key Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_CLASS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlUniqueConstraint <em>Xml Unique Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlUniqueConstraint
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlUniqueConstraint()
	 * @generated
	 */
	public static final int XML_UNIQUE_CONSTRAINT = 44;

	/**
	 * The feature id for the '<em><b>Column Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNIQUE_CONSTRAINT__COLUMN_NAMES = OrmPackage.XML_UNIQUE_CONSTRAINT__COLUMN_NAMES;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNIQUE_CONSTRAINT__NAME = OrmPackage.XML_UNIQUE_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Unique Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNIQUE_CONSTRAINT_FEATURE_COUNT = OrmPackage.XML_UNIQUE_CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.LockModeType <em>Lock Mode Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.LockModeType
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getLockModeType()
	 * @generated
	 */
	public static final int LOCK_MODE_TYPE = 45;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntityMappingsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPersistenceUnitMetadataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPersistenceUnitDefaultsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMappedSuperclassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEmbeddableEClass = null;

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
	private EClass xmlAttributeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlElementCollectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlIdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEmbeddedIdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEmbeddedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBasicEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlVersionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlManyToOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOneToManyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOneToOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlManyToManyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTransientEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAssociationOverrideEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAttributeOverrideEClass = null;

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
	private EClass xmlQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedNativeQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlSequenceGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTableGeneratorEClass = null;

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
	private EClass eventMethodEClass = null;

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
	private EClass xmlQueryHintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCollectionTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOrderColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMapKeyColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMapKeyJoinColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMapKeyClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlUniqueConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum lockModeTypeEEnum = null;

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
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private Orm2_0Package()
	{
		super(eNS_URI, Orm2_0Factory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link Orm2_0Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static Orm2_0Package init()
	{
		if (isInited) return (Orm2_0Package)EPackage.Registry.INSTANCE.getEPackage(Orm2_0Package.eNS_URI);

		// Obtain or create and register package
		Orm2_0Package theOrm2_0Package = (Orm2_0Package)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof Orm2_0Package ? EPackage.Registry.INSTANCE.get(eNS_URI) : new Orm2_0Package());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Persistence2_0Package thePersistence2_0Package = (Persistence2_0Package)(EPackage.Registry.INSTANCE.getEPackage(Persistence2_0Package.eNS_URI) instanceof Persistence2_0Package ? EPackage.Registry.INSTANCE.getEPackage(Persistence2_0Package.eNS_URI) : Persistence2_0Package.eINSTANCE);
		OrmPackage theOrmPackage = (OrmPackage)(EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) : OrmPackage.eINSTANCE);
		PersistencePackage thePersistencePackage = (PersistencePackage)(EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);

		// Create package meta-data objects
		theOrm2_0Package.createPackageContents();
		thePersistence2_0Package.createPackageContents();
		theOrmPackage.createPackageContents();
		thePersistencePackage.createPackageContents();

		// Initialize created meta-data
		theOrm2_0Package.initializePackageContents();
		thePersistence2_0Package.initializePackageContents();
		theOrmPackage.initializePackageContents();
		thePersistencePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theOrm2_0Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(Orm2_0Package.eNS_URI, theOrm2_0Package);
		return theOrm2_0Package;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity Mappings</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEntityMappings
	 * @generated
	 */
	public EClass getXmlEntityMappings()
	{
		return xmlEntityMappingsEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitMetadata <em>Xml Persistence Unit Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence Unit Metadata</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitMetadata
	 * @generated
	 */
	public EClass getXmlPersistenceUnitMetadata()
	{
		return xmlPersistenceUnitMetadataEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitMetadata#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitMetadata#getDescription()
	 * @see #getXmlPersistenceUnitMetadata()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnitMetadata_Description()
	{
		return (EAttribute)xmlPersistenceUnitMetadataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitDefaults <em>Xml Persistence Unit Defaults</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence Unit Defaults</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitDefaults
	 * @generated
	 */
	public EClass getXmlPersistenceUnitDefaults()
	{
		return xmlPersistenceUnitDefaultsEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitDefaults#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitDefaults#getDescription()
	 * @see #getXmlPersistenceUnitDefaults()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnitDefaults_Description()
	{
		return (EAttribute)xmlPersistenceUnitDefaultsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitDefaults#isDelimitedIdentifiers <em>Delimited Identifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delimited Identifiers</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitDefaults#isDelimitedIdentifiers()
	 * @see #getXmlPersistenceUnitDefaults()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnitDefaults_DelimitedIdentifiers()
	{
		return (EAttribute)xmlPersistenceUnitDefaultsEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEntity <em>Xml Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEntity
	 * @generated
	 */
	public EClass getXmlEntity()
	{
		return xmlEntityEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEntity#isCacheable <em>Cacheable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cacheable</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEntity#isCacheable()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EAttribute getXmlEntity_Cacheable()
	{
		return (EAttribute)xmlEntityEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mapped Superclass</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlMappedSuperclass
	 * @generated
	 */
	public EClass getXmlMappedSuperclass()
	{
		return xmlMappedSuperclassEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddable <em>Xml Embeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embeddable</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddable
	 * @generated
	 */
	public EClass getXmlEmbeddable()
	{
		return xmlEmbeddableEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.Attributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attributes</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Attributes
	 * @generated
	 */
	public EClass getAttributes()
	{
		return attributesEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.Attributes#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Attributes#getDescription()
	 * @see #getAttributes()
	 * @generated
	 */
	public EAttribute getAttributes_Description()
	{
		return (EAttribute)attributesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.jpa2.resource.orm.Attributes#getElementCollections <em>Element Collections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Element Collections</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Attributes#getElementCollections()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_ElementCollections()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Attribute Mapping</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeMapping
	 * @generated
	 */
	public EClass getXmlAttributeMapping()
	{
		return xmlAttributeMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection <em>Xml Element Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Element Collection</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection
	 * @generated
	 */
	public EClass getXmlElementCollection()
	{
		return xmlElementCollectionEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getTargetClass <em>Target Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Class</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getTargetClass()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_TargetClass()
	{
		return (EAttribute)xmlElementCollectionEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getFetch()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_Fetch()
	{
		return (EAttribute)xmlElementCollectionEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getOrderBy <em>Order By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Order By</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getOrderBy()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_OrderBy()
	{
		return (EAttribute)xmlElementCollectionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getOrderColumn <em>Order Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Order Column</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getOrderColumn()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EReference getXmlElementCollection_OrderColumn()
	{
		return (EReference)xmlElementCollectionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKey <em>Map Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKey()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EReference getXmlElementCollection_MapKey()
	{
		return (EReference)xmlElementCollectionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKeyClass <em>Map Key Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Class</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKeyClass()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EReference getXmlElementCollection_MapKeyClass()
	{
		return (EReference)xmlElementCollectionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKeyTemporal <em>Map Key Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Key Temporal</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKeyTemporal()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_MapKeyTemporal()
	{
		return (EAttribute)xmlElementCollectionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKeyEnumerated <em>Map Key Enumerated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Key Enumerated</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKeyEnumerated()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_MapKeyEnumerated()
	{
		return (EAttribute)xmlElementCollectionEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKeyAttributeOverrides <em>Map Key Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKeyAttributeOverrides()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EReference getXmlElementCollection_MapKeyAttributeOverrides()
	{
		return (EReference)xmlElementCollectionEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKeyColumn <em>Map Key Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Column</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKeyColumn()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EReference getXmlElementCollection_MapKeyColumn()
	{
		return (EReference)xmlElementCollectionEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKeyJoinColumns <em>Map Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getMapKeyJoinColumns()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EReference getXmlElementCollection_MapKeyJoinColumns()
	{
		return (EReference)xmlElementCollectionEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getColumn()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EReference getXmlElementCollection_Column()
	{
		return (EReference)xmlElementCollectionEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getTemporal <em>Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temporal</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getTemporal()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_Temporal()
	{
		return (EAttribute)xmlElementCollectionEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getEnumerated <em>Enumerated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enumerated</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getEnumerated()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_Enumerated()
	{
		return (EAttribute)xmlElementCollectionEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#isLob <em>Lob</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lob</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#isLob()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_Lob()
	{
		return (EAttribute)xmlElementCollectionEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getAttributeOverrides <em>Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getAttributeOverrides()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EReference getXmlElementCollection_AttributeOverrides()
	{
		return (EReference)xmlElementCollectionEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getAssociationOverrides <em>Association Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Association Overrides</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getAssociationOverrides()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EReference getXmlElementCollection_AssociationOverrides()
	{
		return (EReference)xmlElementCollectionEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getCollectionTable <em>Collection Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Collection Table</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection#getCollectionTable()
	 * @see #getXmlElementCollection()
	 * @generated
	 */
	public EReference getXmlElementCollection_CollectionTable()
	{
		return (EReference)xmlElementCollectionEClass.getEStructuralFeatures().get(17);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlId <em>Xml Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlId
	 * @generated
	 */
	public EClass getXmlId()
	{
		return xmlIdEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded Id</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddedId
	 * @generated
	 */
	public EClass getXmlEmbeddedId()
	{
		return xmlEmbeddedIdEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbedded <em>Xml Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbedded
	 * @generated
	 */
	public EClass getXmlEmbedded()
	{
		return xmlEmbeddedEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbedded#getAssociationOverrides <em>Association Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Association Overrides</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbedded#getAssociationOverrides()
	 * @see #getXmlEmbedded()
	 * @generated
	 */
	public EReference getXmlEmbedded_AssociationOverrides()
	{
		return (EReference)xmlEmbeddedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlBasic <em>Xml Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlBasic
	 * @generated
	 */
	public EClass getXmlBasic()
	{
		return xmlBasicEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Version</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlVersion
	 * @generated
	 */
	public EClass getXmlVersion()
	{
		return xmlVersionEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne <em>Xml Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To One</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne
	 * @generated
	 */
	public EClass getXmlManyToOne()
	{
		return xmlManyToOneEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne#getMappedById <em>Mapped By Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mapped By Id</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne#getMappedById()
	 * @see #getXmlManyToOne()
	 * @generated
	 */
	public EAttribute getXmlManyToOne_MappedById()
	{
		return (EAttribute)xmlManyToOneEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne#isId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne#isId()
	 * @see #getXmlManyToOne()
	 * @generated
	 */
	public EAttribute getXmlManyToOne_Id()
	{
		return (EAttribute)xmlManyToOneEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany <em>Xml One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany
	 * @generated
	 */
	public EClass getXmlOneToMany()
	{
		return xmlOneToManyEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getOrderColumn <em>Order Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Order Column</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getOrderColumn()
	 * @see #getXmlOneToMany()
	 * @generated
	 */
	public EReference getXmlOneToMany_OrderColumn()
	{
		return (EReference)xmlOneToManyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getMapKeyClass <em>Map Key Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Class</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getMapKeyClass()
	 * @see #getXmlOneToMany()
	 * @generated
	 */
	public EReference getXmlOneToMany_MapKeyClass()
	{
		return (EReference)xmlOneToManyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getMapKeyTemporal <em>Map Key Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Key Temporal</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getMapKeyTemporal()
	 * @see #getXmlOneToMany()
	 * @generated
	 */
	public EAttribute getXmlOneToMany_MapKeyTemporal()
	{
		return (EAttribute)xmlOneToManyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getMapKeyEnumerated <em>Map Key Enumerated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Key Enumerated</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getMapKeyEnumerated()
	 * @see #getXmlOneToMany()
	 * @generated
	 */
	public EAttribute getXmlOneToMany_MapKeyEnumerated()
	{
		return (EAttribute)xmlOneToManyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getMapKeyAttributeOverrides <em>Map Key Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getMapKeyAttributeOverrides()
	 * @see #getXmlOneToMany()
	 * @generated
	 */
	public EReference getXmlOneToMany_MapKeyAttributeOverrides()
	{
		return (EReference)xmlOneToManyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getMapKeyColumn <em>Map Key Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Column</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getMapKeyColumn()
	 * @see #getXmlOneToMany()
	 * @generated
	 */
	public EReference getXmlOneToMany_MapKeyColumn()
	{
		return (EReference)xmlOneToManyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getMapKeyJoinColumns <em>Map Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#getMapKeyJoinColumns()
	 * @see #getXmlOneToMany()
	 * @generated
	 */
	public EReference getXmlOneToMany_MapKeyJoinColumns()
	{
		return (EReference)xmlOneToManyEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#isOrphanRemoval <em>Orphan Removal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Orphan Removal</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany#isOrphanRemoval()
	 * @see #getXmlOneToMany()
	 * @generated
	 */
	public EAttribute getXmlOneToMany_OrphanRemoval()
	{
		return (EAttribute)xmlOneToManyEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne <em>Xml One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne
	 * @generated
	 */
	public EClass getXmlOneToOne()
	{
		return xmlOneToOneEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne#isOrphanRemoval <em>Orphan Removal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Orphan Removal</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne#isOrphanRemoval()
	 * @see #getXmlOneToOne()
	 * @generated
	 */
	public EAttribute getXmlOneToOne_OrphanRemoval()
	{
		return (EAttribute)xmlOneToOneEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne#getMappedById <em>Mapped By Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mapped By Id</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne#getMappedById()
	 * @see #getXmlOneToOne()
	 * @generated
	 */
	public EAttribute getXmlOneToOne_MappedById()
	{
		return (EAttribute)xmlOneToOneEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne#isId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne#isId()
	 * @see #getXmlOneToOne()
	 * @generated
	 */
	public EAttribute getXmlOneToOne_Id()
	{
		return (EAttribute)xmlOneToOneEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany
	 * @generated
	 */
	public EClass getXmlManyToMany()
	{
		return xmlManyToManyEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getOrderColumn <em>Order Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Order Column</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getOrderColumn()
	 * @see #getXmlManyToMany()
	 * @generated
	 */
	public EReference getXmlManyToMany_OrderColumn()
	{
		return (EReference)xmlManyToManyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getMapKeyClass <em>Map Key Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Class</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getMapKeyClass()
	 * @see #getXmlManyToMany()
	 * @generated
	 */
	public EReference getXmlManyToMany_MapKeyClass()
	{
		return (EReference)xmlManyToManyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getMapKeyTemporal <em>Map Key Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Key Temporal</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getMapKeyTemporal()
	 * @see #getXmlManyToMany()
	 * @generated
	 */
	public EAttribute getXmlManyToMany_MapKeyTemporal()
	{
		return (EAttribute)xmlManyToManyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getMapKeyEnumerated <em>Map Key Enumerated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Key Enumerated</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getMapKeyEnumerated()
	 * @see #getXmlManyToMany()
	 * @generated
	 */
	public EAttribute getXmlManyToMany_MapKeyEnumerated()
	{
		return (EAttribute)xmlManyToManyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getMapKeyAttributeOverrides <em>Map Key Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getMapKeyAttributeOverrides()
	 * @see #getXmlManyToMany()
	 * @generated
	 */
	public EReference getXmlManyToMany_MapKeyAttributeOverrides()
	{
		return (EReference)xmlManyToManyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getMapKeyColumn <em>Map Key Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Column</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getMapKeyColumn()
	 * @see #getXmlManyToMany()
	 * @generated
	 */
	public EReference getXmlManyToMany_MapKeyColumn()
	{
		return (EReference)xmlManyToManyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getMapKeyJoinColumns <em>Map Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany#getMapKeyJoinColumns()
	 * @see #getXmlManyToMany()
	 * @generated
	 */
	public EReference getXmlManyToMany_MapKeyJoinColumns()
	{
		return (EReference)xmlManyToManyEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlTransient <em>Xml Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Transient</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlTransient
	 * @generated
	 */
	public EClass getXmlTransient()
	{
		return xmlTransientEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride <em>Xml Association Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Association Override</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride
	 * @generated
	 */
	public EClass getXmlAssociationOverride()
	{
		return xmlAssociationOverrideEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride#getDescription()
	 * @see #getXmlAssociationOverride()
	 * @generated
	 */
	public EAttribute getXmlAssociationOverride_Description()
	{
		return (EAttribute)xmlAssociationOverrideEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride#getJoinTable <em>Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Table</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride#getJoinTable()
	 * @see #getXmlAssociationOverride()
	 * @generated
	 */
	public EReference getXmlAssociationOverride_JoinTable()
	{
		return (EReference)xmlAssociationOverrideEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeOverride <em>Xml Attribute Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Attribute Override</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeOverride
	 * @generated
	 */
	public EClass getXmlAttributeOverride()
	{
		return xmlAttributeOverrideEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeOverride#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeOverride#getDescription()
	 * @see #getXmlAttributeOverride()
	 * @generated
	 */
	public EAttribute getXmlAttributeOverride_Description()
	{
		return (EAttribute)xmlAttributeOverrideEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.EntityListeners <em>Entity Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Listeners</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.EntityListeners
	 * @generated
	 */
	public EClass getEntityListeners()
	{
		return entityListenersEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.EntityListener <em>Entity Listener</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Listener</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.EntityListener
	 * @generated
	 */
	public EClass getEntityListener()
	{
		return entityListenerEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.EntityListener#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.EntityListener#getDescription()
	 * @see #getEntityListener()
	 * @generated
	 */
	public EAttribute getEntityListener_Description()
	{
		return (EAttribute)entityListenerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlQuery <em>Xml Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Query</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlQuery
	 * @generated
	 */
	public EClass getXmlQuery()
	{
		return xmlQueryEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlQuery#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlQuery#getDescription()
	 * @see #getXmlQuery()
	 * @generated
	 */
	public EAttribute getXmlQuery_Description()
	{
		return (EAttribute)xmlQueryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedNativeQuery <em>Xml Named Native Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Native Query</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedNativeQuery
	 * @generated
	 */
	public EClass getXmlNamedNativeQuery()
	{
		return xmlNamedNativeQueryEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedQuery <em>Xml Named Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Query</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedQuery
	 * @generated
	 */
	public EClass getXmlNamedQuery()
	{
		return xmlNamedQueryEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedQuery#getLockMode <em>Lock Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lock Mode</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedQuery#getLockMode()
	 * @see #getXmlNamedQuery()
	 * @generated
	 */
	public EAttribute getXmlNamedQuery_LockMode()
	{
		return (EAttribute)xmlNamedQueryEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlGenerator <em>Xml Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Generator</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlGenerator
	 * @generated
	 */
	public EClass getXmlGenerator()
	{
		return xmlGeneratorEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlGenerator#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlGenerator#getDescription()
	 * @see #getXmlGenerator()
	 * @generated
	 */
	public EAttribute getXmlGenerator_Description()
	{
		return (EAttribute)xmlGeneratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator <em>Xml Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Sequence Generator</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator
	 * @generated
	 */
	public EClass getXmlSequenceGenerator()
	{
		return xmlSequenceGeneratorEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator#getCatalog()
	 * @see #getXmlSequenceGenerator()
	 * @generated
	 */
	public EAttribute getXmlSequenceGenerator_Catalog()
	{
		return (EAttribute)xmlSequenceGeneratorEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator#getSchema()
	 * @see #getXmlSequenceGenerator()
	 * @generated
	 */
	public EAttribute getXmlSequenceGenerator_Schema()
	{
		return (EAttribute)xmlSequenceGeneratorEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlTableGenerator <em>Xml Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Table Generator</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlTableGenerator
	 * @generated
	 */
	public EClass getXmlTableGenerator()
	{
		return xmlTableGeneratorEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.SqlResultSetMapping <em>Sql Result Set Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sql Result Set Mapping</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.SqlResultSetMapping
	 * @generated
	 */
	public EClass getSqlResultSetMapping()
	{
		return sqlResultSetMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.SqlResultSetMapping#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.SqlResultSetMapping#getDescription()
	 * @see #getSqlResultSetMapping()
	 * @generated
	 */
	public EAttribute getSqlResultSetMapping_Description()
	{
		return (EAttribute)sqlResultSetMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.EventMethod <em>Event Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Method</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.EventMethod
	 * @generated
	 */
	public EClass getEventMethod()
	{
		return eventMethodEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.EventMethod#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.EventMethod#getDescription()
	 * @see #getEventMethod()
	 * @generated
	 */
	public EAttribute getEventMethod_Description()
	{
		return (EAttribute)eventMethodEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.PostLoad <em>Post Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Load</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PostLoad
	 * @generated
	 */
	public EClass getPostLoad()
	{
		return postLoadEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.PostPersist <em>Post Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Persist</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PostPersist
	 * @generated
	 */
	public EClass getPostPersist()
	{
		return postPersistEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.PostRemove <em>Post Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Remove</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PostRemove
	 * @generated
	 */
	public EClass getPostRemove()
	{
		return postRemoveEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.PostUpdate <em>Post Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Update</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PostUpdate
	 * @generated
	 */
	public EClass getPostUpdate()
	{
		return postUpdateEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.PrePersist <em>Pre Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pre Persist</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PrePersist
	 * @generated
	 */
	public EClass getPrePersist()
	{
		return prePersistEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.PreRemove <em>Pre Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pre Remove</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PreRemove
	 * @generated
	 */
	public EClass getPreRemove()
	{
		return preRemoveEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.PreUpdate <em>Pre Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pre Update</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.PreUpdate
	 * @generated
	 */
	public EClass getPreUpdate()
	{
		return preUpdateEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlQueryHint <em>Xml Query Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Query Hint</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlQueryHint
	 * @generated
	 */
	public EClass getXmlQueryHint()
	{
		return xmlQueryHintEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlQueryHint#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlQueryHint#getDescription()
	 * @see #getXmlQueryHint()
	 * @generated
	 */
	public EAttribute getXmlQueryHint_Description()
	{
		return (EAttribute)xmlQueryHintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlCollectionTable <em>Xml Collection Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Collection Table</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlCollectionTable
	 * @generated
	 */
	public EClass getXmlCollectionTable()
	{
		return xmlCollectionTableEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlCollectionTable#getJoinColumns <em>Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Columns</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlCollectionTable#getJoinColumns()
	 * @see #getXmlCollectionTable()
	 * @generated
	 */
	public EReference getXmlCollectionTable_JoinColumns()
	{
		return (EReference)xmlCollectionTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn <em>Xml Order Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Order Column</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn
	 * @generated
	 */
	public EClass getXmlOrderColumn()
	{
		return xmlOrderColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn#getInsertable <em>Insertable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Insertable</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn#getInsertable()
	 * @see #getXmlOrderColumn()
	 * @generated
	 */
	public EAttribute getXmlOrderColumn_Insertable()
	{
		return (EAttribute)xmlOrderColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn#getNullable <em>Nullable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nullable</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn#getNullable()
	 * @see #getXmlOrderColumn()
	 * @generated
	 */
	public EAttribute getXmlOrderColumn_Nullable()
	{
		return (EAttribute)xmlOrderColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn#getUpdatable <em>Updatable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Updatable</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn#getUpdatable()
	 * @see #getXmlOrderColumn()
	 * @generated
	 */
	public EAttribute getXmlOrderColumn_Updatable()
	{
		return (EAttribute)xmlOrderColumnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Table</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn#getTable()
	 * @see #getXmlOrderColumn()
	 * @generated
	 */
	public EAttribute getXmlOrderColumn_Table()
	{
		return (EAttribute)xmlOrderColumnEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyColumn <em>Xml Map Key Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Map Key Column</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyColumn
	 * @generated
	 */
	public EClass getXmlMapKeyColumn()
	{
		return xmlMapKeyColumnEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyJoinColumn <em>Xml Map Key Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Map Key Join Column</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyJoinColumn
	 * @generated
	 */
	public EClass getXmlMapKeyJoinColumn()
	{
		return xmlMapKeyJoinColumnEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyClass <em>Xml Map Key Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Map Key Class</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyClass
	 * @generated
	 */
	public EClass getXmlMapKeyClass()
	{
		return xmlMapKeyClassEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyClass#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyClass#getClassName()
	 * @see #getXmlMapKeyClass()
	 * @generated
	 */
	public EAttribute getXmlMapKeyClass_ClassName()
	{
		return (EAttribute)xmlMapKeyClassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlUniqueConstraint <em>Xml Unique Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Unique Constraint</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlUniqueConstraint
	 * @generated
	 */
	public EClass getXmlUniqueConstraint()
	{
		return xmlUniqueConstraintEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlUniqueConstraint#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlUniqueConstraint#getName()
	 * @see #getXmlUniqueConstraint()
	 * @generated
	 */
	public EAttribute getXmlUniqueConstraint_Name()
	{
		return (EAttribute)xmlUniqueConstraintEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.jpa2.resource.orm.LockModeType <em>Lock Mode Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Lock Mode Type</em>'.
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.LockModeType
	 * @generated
	 */
	public EEnum getLockModeType()
	{
		return lockModeTypeEEnum;
	}


	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public Orm2_0Factory getOrm2_0Factory()
	{
		return (Orm2_0Factory)getEFactoryInstance();
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
		xmlEntityMappingsEClass = createEClass(XML_ENTITY_MAPPINGS);

		xmlPersistenceUnitMetadataEClass = createEClass(XML_PERSISTENCE_UNIT_METADATA);
		createEAttribute(xmlPersistenceUnitMetadataEClass, XML_PERSISTENCE_UNIT_METADATA__DESCRIPTION);

		xmlPersistenceUnitDefaultsEClass = createEClass(XML_PERSISTENCE_UNIT_DEFAULTS);
		createEAttribute(xmlPersistenceUnitDefaultsEClass, XML_PERSISTENCE_UNIT_DEFAULTS__DESCRIPTION);
		createEAttribute(xmlPersistenceUnitDefaultsEClass, XML_PERSISTENCE_UNIT_DEFAULTS__DELIMITED_IDENTIFIERS);

		xmlEntityEClass = createEClass(XML_ENTITY);
		createEAttribute(xmlEntityEClass, XML_ENTITY__CACHEABLE);

		xmlMappedSuperclassEClass = createEClass(XML_MAPPED_SUPERCLASS);

		xmlEmbeddableEClass = createEClass(XML_EMBEDDABLE);

		attributesEClass = createEClass(ATTRIBUTES);
		createEAttribute(attributesEClass, ATTRIBUTES__DESCRIPTION);
		createEReference(attributesEClass, ATTRIBUTES__ELEMENT_COLLECTIONS);

		xmlAttributeMappingEClass = createEClass(XML_ATTRIBUTE_MAPPING);

		xmlElementCollectionEClass = createEClass(XML_ELEMENT_COLLECTION);
		createEAttribute(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__TARGET_CLASS);
		createEAttribute(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__FETCH);
		createEAttribute(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__ORDER_BY);
		createEReference(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__ORDER_COLUMN);
		createEReference(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__MAP_KEY);
		createEReference(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__MAP_KEY_CLASS);
		createEAttribute(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL);
		createEAttribute(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED);
		createEReference(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES);
		createEReference(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN);
		createEReference(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS);
		createEReference(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__COLUMN);
		createEAttribute(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__TEMPORAL);
		createEAttribute(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__ENUMERATED);
		createEAttribute(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__LOB);
		createEReference(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES);
		createEReference(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES);
		createEReference(xmlElementCollectionEClass, XML_ELEMENT_COLLECTION__COLLECTION_TABLE);

		xmlIdEClass = createEClass(XML_ID);

		xmlEmbeddedIdEClass = createEClass(XML_EMBEDDED_ID);

		xmlEmbeddedEClass = createEClass(XML_EMBEDDED);
		createEReference(xmlEmbeddedEClass, XML_EMBEDDED__ASSOCIATION_OVERRIDES);

		xmlBasicEClass = createEClass(XML_BASIC);

		xmlVersionEClass = createEClass(XML_VERSION);

		xmlManyToOneEClass = createEClass(XML_MANY_TO_ONE);
		createEAttribute(xmlManyToOneEClass, XML_MANY_TO_ONE__MAPPED_BY_ID);
		createEAttribute(xmlManyToOneEClass, XML_MANY_TO_ONE__ID);

		xmlOneToManyEClass = createEClass(XML_ONE_TO_MANY);
		createEReference(xmlOneToManyEClass, XML_ONE_TO_MANY__ORDER_COLUMN);
		createEReference(xmlOneToManyEClass, XML_ONE_TO_MANY__MAP_KEY_CLASS);
		createEAttribute(xmlOneToManyEClass, XML_ONE_TO_MANY__MAP_KEY_TEMPORAL);
		createEAttribute(xmlOneToManyEClass, XML_ONE_TO_MANY__MAP_KEY_ENUMERATED);
		createEReference(xmlOneToManyEClass, XML_ONE_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES);
		createEReference(xmlOneToManyEClass, XML_ONE_TO_MANY__MAP_KEY_COLUMN);
		createEReference(xmlOneToManyEClass, XML_ONE_TO_MANY__MAP_KEY_JOIN_COLUMNS);
		createEAttribute(xmlOneToManyEClass, XML_ONE_TO_MANY__ORPHAN_REMOVAL);

		xmlOneToOneEClass = createEClass(XML_ONE_TO_ONE);
		createEAttribute(xmlOneToOneEClass, XML_ONE_TO_ONE__ORPHAN_REMOVAL);
		createEAttribute(xmlOneToOneEClass, XML_ONE_TO_ONE__MAPPED_BY_ID);
		createEAttribute(xmlOneToOneEClass, XML_ONE_TO_ONE__ID);

		xmlManyToManyEClass = createEClass(XML_MANY_TO_MANY);
		createEReference(xmlManyToManyEClass, XML_MANY_TO_MANY__ORDER_COLUMN);
		createEReference(xmlManyToManyEClass, XML_MANY_TO_MANY__MAP_KEY_CLASS);
		createEAttribute(xmlManyToManyEClass, XML_MANY_TO_MANY__MAP_KEY_TEMPORAL);
		createEAttribute(xmlManyToManyEClass, XML_MANY_TO_MANY__MAP_KEY_ENUMERATED);
		createEReference(xmlManyToManyEClass, XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES);
		createEReference(xmlManyToManyEClass, XML_MANY_TO_MANY__MAP_KEY_COLUMN);
		createEReference(xmlManyToManyEClass, XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS);

		xmlTransientEClass = createEClass(XML_TRANSIENT);

		xmlAssociationOverrideEClass = createEClass(XML_ASSOCIATION_OVERRIDE);
		createEAttribute(xmlAssociationOverrideEClass, XML_ASSOCIATION_OVERRIDE__DESCRIPTION);
		createEReference(xmlAssociationOverrideEClass, XML_ASSOCIATION_OVERRIDE__JOIN_TABLE);

		xmlAttributeOverrideEClass = createEClass(XML_ATTRIBUTE_OVERRIDE);
		createEAttribute(xmlAttributeOverrideEClass, XML_ATTRIBUTE_OVERRIDE__DESCRIPTION);

		entityListenersEClass = createEClass(ENTITY_LISTENERS);

		entityListenerEClass = createEClass(ENTITY_LISTENER);
		createEAttribute(entityListenerEClass, ENTITY_LISTENER__DESCRIPTION);

		xmlQueryEClass = createEClass(XML_QUERY);
		createEAttribute(xmlQueryEClass, XML_QUERY__DESCRIPTION);

		xmlNamedNativeQueryEClass = createEClass(XML_NAMED_NATIVE_QUERY);

		xmlNamedQueryEClass = createEClass(XML_NAMED_QUERY);
		createEAttribute(xmlNamedQueryEClass, XML_NAMED_QUERY__LOCK_MODE);

		xmlGeneratorEClass = createEClass(XML_GENERATOR);
		createEAttribute(xmlGeneratorEClass, XML_GENERATOR__DESCRIPTION);

		xmlSequenceGeneratorEClass = createEClass(XML_SEQUENCE_GENERATOR);
		createEAttribute(xmlSequenceGeneratorEClass, XML_SEQUENCE_GENERATOR__CATALOG);
		createEAttribute(xmlSequenceGeneratorEClass, XML_SEQUENCE_GENERATOR__SCHEMA);

		xmlTableGeneratorEClass = createEClass(XML_TABLE_GENERATOR);

		sqlResultSetMappingEClass = createEClass(SQL_RESULT_SET_MAPPING);
		createEAttribute(sqlResultSetMappingEClass, SQL_RESULT_SET_MAPPING__DESCRIPTION);

		eventMethodEClass = createEClass(EVENT_METHOD);
		createEAttribute(eventMethodEClass, EVENT_METHOD__DESCRIPTION);

		postLoadEClass = createEClass(POST_LOAD);

		postPersistEClass = createEClass(POST_PERSIST);

		postRemoveEClass = createEClass(POST_REMOVE);

		postUpdateEClass = createEClass(POST_UPDATE);

		prePersistEClass = createEClass(PRE_PERSIST);

		preRemoveEClass = createEClass(PRE_REMOVE);

		preUpdateEClass = createEClass(PRE_UPDATE);

		xmlQueryHintEClass = createEClass(XML_QUERY_HINT);
		createEAttribute(xmlQueryHintEClass, XML_QUERY_HINT__DESCRIPTION);

		xmlCollectionTableEClass = createEClass(XML_COLLECTION_TABLE);
		createEReference(xmlCollectionTableEClass, XML_COLLECTION_TABLE__JOIN_COLUMNS);

		xmlOrderColumnEClass = createEClass(XML_ORDER_COLUMN);
		createEAttribute(xmlOrderColumnEClass, XML_ORDER_COLUMN__INSERTABLE);
		createEAttribute(xmlOrderColumnEClass, XML_ORDER_COLUMN__NULLABLE);
		createEAttribute(xmlOrderColumnEClass, XML_ORDER_COLUMN__UPDATABLE);
		createEAttribute(xmlOrderColumnEClass, XML_ORDER_COLUMN__TABLE);

		xmlMapKeyColumnEClass = createEClass(XML_MAP_KEY_COLUMN);

		xmlMapKeyJoinColumnEClass = createEClass(XML_MAP_KEY_JOIN_COLUMN);

		xmlMapKeyClassEClass = createEClass(XML_MAP_KEY_CLASS);
		createEAttribute(xmlMapKeyClassEClass, XML_MAP_KEY_CLASS__CLASS_NAME);

		xmlUniqueConstraintEClass = createEClass(XML_UNIQUE_CONSTRAINT);
		createEAttribute(xmlUniqueConstraintEClass, XML_UNIQUE_CONSTRAINT__NAME);

		// Create enums
		lockModeTypeEEnum = createEEnum(LOCK_MODE_TYPE);
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
		OrmPackage theOrmPackage = (OrmPackage)EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		xmlEntityMappingsEClass.getESuperTypes().add(theOrmPackage.getXmlEntityMappings());
		xmlPersistenceUnitMetadataEClass.getESuperTypes().add(theOrmPackage.getXmlPersistenceUnitMetadata());
		xmlPersistenceUnitDefaultsEClass.getESuperTypes().add(theOrmPackage.getXmlPersistenceUnitDefaults());
		xmlEntityEClass.getESuperTypes().add(theOrmPackage.getXmlEntity());
		xmlMappedSuperclassEClass.getESuperTypes().add(theOrmPackage.getXmlMappedSuperclass());
		xmlEmbeddableEClass.getESuperTypes().add(theOrmPackage.getXmlEmbeddable());
		attributesEClass.getESuperTypes().add(theOrmPackage.getAttributes());
		xmlAttributeMappingEClass.getESuperTypes().add(theOrmPackage.getXmlAttributeMapping());
		xmlAttributeMappingEClass.getESuperTypes().add(theOrmPackage.getXmlAccessHolder());
		xmlElementCollectionEClass.getESuperTypes().add(theOrmPackage.getAbstractXmlAttributeMapping());
		xmlElementCollectionEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlIdEClass.getESuperTypes().add(theOrmPackage.getXmlId());
		xmlIdEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlEmbeddedIdEClass.getESuperTypes().add(theOrmPackage.getXmlEmbeddedId());
		xmlEmbeddedIdEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlEmbeddedEClass.getESuperTypes().add(theOrmPackage.getXmlEmbedded());
		xmlEmbeddedEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlBasicEClass.getESuperTypes().add(theOrmPackage.getXmlBasic());
		xmlBasicEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlVersionEClass.getESuperTypes().add(theOrmPackage.getXmlVersion());
		xmlVersionEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlManyToOneEClass.getESuperTypes().add(theOrmPackage.getXmlManyToOne());
		xmlManyToOneEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlOneToManyEClass.getESuperTypes().add(theOrmPackage.getXmlOneToMany());
		xmlOneToManyEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlOneToOneEClass.getESuperTypes().add(theOrmPackage.getXmlOneToOne());
		xmlOneToOneEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlManyToManyEClass.getESuperTypes().add(theOrmPackage.getXmlManyToMany());
		xmlManyToManyEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlTransientEClass.getESuperTypes().add(theOrmPackage.getXmlTransient());
		xmlTransientEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlAssociationOverrideEClass.getESuperTypes().add(theOrmPackage.getXmlAssociationOverride());
		xmlAttributeOverrideEClass.getESuperTypes().add(theOrmPackage.getXmlAttributeOverride());
		entityListenersEClass.getESuperTypes().add(theOrmPackage.getEntityListeners());
		entityListenerEClass.getESuperTypes().add(theOrmPackage.getEntityListener());
		xmlQueryEClass.getESuperTypes().add(theOrmPackage.getXmlQuery());
		xmlNamedNativeQueryEClass.getESuperTypes().add(theOrmPackage.getXmlNamedNativeQuery());
		xmlNamedNativeQueryEClass.getESuperTypes().add(this.getXmlQuery());
		xmlNamedQueryEClass.getESuperTypes().add(theOrmPackage.getXmlNamedQuery());
		xmlNamedQueryEClass.getESuperTypes().add(this.getXmlQuery());
		xmlGeneratorEClass.getESuperTypes().add(theOrmPackage.getXmlGenerator());
		xmlSequenceGeneratorEClass.getESuperTypes().add(theOrmPackage.getXmlSequenceGenerator());
		xmlSequenceGeneratorEClass.getESuperTypes().add(this.getXmlGenerator());
		xmlTableGeneratorEClass.getESuperTypes().add(theOrmPackage.getXmlTableGenerator());
		xmlTableGeneratorEClass.getESuperTypes().add(this.getXmlGenerator());
		sqlResultSetMappingEClass.getESuperTypes().add(theOrmPackage.getSqlResultSetMapping());
		eventMethodEClass.getESuperTypes().add(theOrmPackage.getEventMethod());
		postLoadEClass.getESuperTypes().add(theOrmPackage.getPostLoad());
		postLoadEClass.getESuperTypes().add(this.getEventMethod());
		postPersistEClass.getESuperTypes().add(theOrmPackage.getPostPersist());
		postPersistEClass.getESuperTypes().add(this.getEventMethod());
		postRemoveEClass.getESuperTypes().add(theOrmPackage.getPostRemove());
		postRemoveEClass.getESuperTypes().add(this.getEventMethod());
		postUpdateEClass.getESuperTypes().add(theOrmPackage.getPostUpdate());
		postUpdateEClass.getESuperTypes().add(this.getEventMethod());
		prePersistEClass.getESuperTypes().add(theOrmPackage.getPrePersist());
		prePersistEClass.getESuperTypes().add(this.getEventMethod());
		preRemoveEClass.getESuperTypes().add(theOrmPackage.getPreRemove());
		preRemoveEClass.getESuperTypes().add(this.getEventMethod());
		preUpdateEClass.getESuperTypes().add(theOrmPackage.getPreUpdate());
		preUpdateEClass.getESuperTypes().add(this.getEventMethod());
		xmlQueryHintEClass.getESuperTypes().add(theOrmPackage.getXmlQueryHint());
		xmlCollectionTableEClass.getESuperTypes().add(theOrmPackage.getAbstractXmlTable());
		xmlOrderColumnEClass.getESuperTypes().add(theOrmPackage.getAbstractXmlNamedColumn());
		xmlMapKeyColumnEClass.getESuperTypes().add(theOrmPackage.getXmlColumn());
		xmlMapKeyJoinColumnEClass.getESuperTypes().add(theOrmPackage.getXmlJoinColumn());
		xmlUniqueConstraintEClass.getESuperTypes().add(theOrmPackage.getXmlUniqueConstraint());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlEntityMappingsEClass, XmlEntityMappings.class, "XmlEntityMappings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPersistenceUnitMetadataEClass, XmlPersistenceUnitMetadata.class, "XmlPersistenceUnitMetadata", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPersistenceUnitMetadata_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlPersistenceUnitMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPersistenceUnitDefaultsEClass, XmlPersistenceUnitDefaults.class, "XmlPersistenceUnitDefaults", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPersistenceUnitDefaults_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlPersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistenceUnitDefaults_DelimitedIdentifiers(), theXMLTypePackage.getBoolean(), "delimitedIdentifiers", null, 0, 1, XmlPersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntityEClass, XmlEntity.class, "XmlEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlEntity_Cacheable(), theXMLTypePackage.getBoolean(), "cacheable", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMappedSuperclassEClass, XmlMappedSuperclass.class, "XmlMappedSuperclass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEmbeddableEClass, XmlEmbeddable.class, "XmlEmbeddable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(attributesEClass, Attributes.class, "Attributes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributes_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_ElementCollections(), this.getXmlElementCollection(), null, "elementCollections", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlAttributeMappingEClass, XmlAttributeMapping.class, "XmlAttributeMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlElementCollectionEClass, XmlElementCollection.class, "XmlElementCollection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlElementCollection_TargetClass(), theXMLTypePackage.getString(), "targetClass", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlElementCollection_Fetch(), theOrmPackage.getFetchType(), "fetch", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlElementCollection_OrderBy(), theOrmPackage.getOrderBy(), "orderBy", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_OrderColumn(), this.getXmlOrderColumn(), null, "orderColumn", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_MapKey(), theOrmPackage.getMapKey(), null, "mapKey", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_MapKeyClass(), this.getXmlMapKeyClass(), null, "mapKeyClass", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlElementCollection_MapKeyTemporal(), theOrmPackage.getTemporalType(), "mapKeyTemporal", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlElementCollection_MapKeyEnumerated(), theOrmPackage.getEnumType(), "mapKeyEnumerated", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_MapKeyAttributeOverrides(), theOrmPackage.getXmlAttributeOverride(), null, "mapKeyAttributeOverrides", null, 0, -1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_MapKeyColumn(), this.getXmlMapKeyColumn(), null, "mapKeyColumn", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_MapKeyJoinColumns(), this.getXmlMapKeyJoinColumn(), null, "mapKeyJoinColumns", null, 0, -1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_Column(), theOrmPackage.getXmlColumn(), null, "column", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlElementCollection_Temporal(), theOrmPackage.getTemporalType(), "temporal", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlElementCollection_Enumerated(), theOrmPackage.getEnumType(), "enumerated", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlElementCollection_Lob(), theXMLTypePackage.getBoolean(), "lob", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_AttributeOverrides(), theOrmPackage.getXmlAttributeOverride(), null, "attributeOverrides", null, 0, -1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_AssociationOverrides(), theOrmPackage.getXmlAssociationOverride(), null, "associationOverrides", null, 0, -1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_CollectionTable(), this.getXmlCollectionTable(), null, "collectionTable", null, 0, 1, XmlElementCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlIdEClass, XmlId.class, "XmlId", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEmbeddedIdEClass, XmlEmbeddedId.class, "XmlEmbeddedId", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEmbeddedEClass, XmlEmbedded.class, "XmlEmbedded", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEmbedded_AssociationOverrides(), theOrmPackage.getXmlAssociationOverride(), null, "associationOverrides", null, 0, -1, XmlEmbedded.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlBasicEClass, XmlBasic.class, "XmlBasic", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlVersionEClass, XmlVersion.class, "XmlVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlManyToOneEClass, XmlManyToOne.class, "XmlManyToOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlManyToOne_MappedById(), theXMLTypePackage.getString(), "mappedById", null, 0, 1, XmlManyToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlManyToOne_Id(), theXMLTypePackage.getBoolean(), "id", null, 0, 1, XmlManyToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToManyEClass, XmlOneToMany.class, "XmlOneToMany", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlOneToMany_OrderColumn(), this.getXmlOrderColumn(), null, "orderColumn", null, 0, 1, XmlOneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlOneToMany_MapKeyClass(), this.getXmlMapKeyClass(), null, "mapKeyClass", null, 0, 1, XmlOneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOneToMany_MapKeyTemporal(), theOrmPackage.getTemporalType(), "mapKeyTemporal", null, 0, 1, XmlOneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOneToMany_MapKeyEnumerated(), theOrmPackage.getEnumType(), "mapKeyEnumerated", null, 0, 1, XmlOneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlOneToMany_MapKeyAttributeOverrides(), theOrmPackage.getXmlAttributeOverride(), null, "mapKeyAttributeOverrides", null, 0, -1, XmlOneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlOneToMany_MapKeyColumn(), this.getXmlMapKeyColumn(), null, "mapKeyColumn", null, 0, 1, XmlOneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlOneToMany_MapKeyJoinColumns(), this.getXmlMapKeyJoinColumn(), null, "mapKeyJoinColumns", null, 0, -1, XmlOneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOneToMany_OrphanRemoval(), theXMLTypePackage.getBoolean(), "orphanRemoval", null, 0, 1, XmlOneToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToOneEClass, XmlOneToOne.class, "XmlOneToOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlOneToOne_OrphanRemoval(), theXMLTypePackage.getBoolean(), "orphanRemoval", null, 0, 1, XmlOneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOneToOne_MappedById(), theXMLTypePackage.getString(), "mappedById", null, 0, 1, XmlOneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOneToOne_Id(), theXMLTypePackage.getBoolean(), "id", null, 0, 1, XmlOneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlManyToManyEClass, XmlManyToMany.class, "XmlManyToMany", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlManyToMany_OrderColumn(), this.getXmlOrderColumn(), null, "orderColumn", null, 0, 1, XmlManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlManyToMany_MapKeyClass(), this.getXmlMapKeyClass(), null, "mapKeyClass", null, 0, 1, XmlManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlManyToMany_MapKeyTemporal(), theOrmPackage.getTemporalType(), "mapKeyTemporal", null, 0, 1, XmlManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlManyToMany_MapKeyEnumerated(), theOrmPackage.getEnumType(), "mapKeyEnumerated", null, 0, 1, XmlManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlManyToMany_MapKeyAttributeOverrides(), theOrmPackage.getXmlAttributeOverride(), null, "mapKeyAttributeOverrides", null, 0, -1, XmlManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlManyToMany_MapKeyColumn(), this.getXmlMapKeyColumn(), null, "mapKeyColumn", null, 0, 1, XmlManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlManyToMany_MapKeyJoinColumns(), this.getXmlMapKeyJoinColumn(), null, "mapKeyJoinColumns", null, 0, -1, XmlManyToMany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTransientEClass, XmlTransient.class, "XmlTransient", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlAssociationOverrideEClass, XmlAssociationOverride.class, "XmlAssociationOverride", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlAssociationOverride_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlAssociationOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlAssociationOverride_JoinTable(), theOrmPackage.getXmlJoinTable(), null, "joinTable", null, 0, 1, XmlAssociationOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlAttributeOverrideEClass, XmlAttributeOverride.class, "XmlAttributeOverride", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlAttributeOverride_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlAttributeOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityListenersEClass, EntityListeners.class, "EntityListeners", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(entityListenerEClass, EntityListener.class, "EntityListener", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntityListener_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, EntityListener.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlQueryEClass, XmlQuery.class, "XmlQuery", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlQuery_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNamedNativeQueryEClass, XmlNamedNativeQuery.class, "XmlNamedNativeQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlNamedQueryEClass, XmlNamedQuery.class, "XmlNamedQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlNamedQuery_LockMode(), this.getLockModeType(), "lockMode", null, 0, 1, XmlNamedQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlGeneratorEClass, XmlGenerator.class, "XmlGenerator", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlGenerator_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlSequenceGeneratorEClass, XmlSequenceGenerator.class, "XmlSequenceGenerator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlSequenceGenerator_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, XmlSequenceGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlSequenceGenerator_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, XmlSequenceGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTableGeneratorEClass, XmlTableGenerator.class, "XmlTableGenerator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(sqlResultSetMappingEClass, SqlResultSetMapping.class, "SqlResultSetMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSqlResultSetMapping_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, SqlResultSetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventMethodEClass, EventMethod.class, "EventMethod", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEventMethod_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, EventMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(postLoadEClass, PostLoad.class, "PostLoad", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(postPersistEClass, PostPersist.class, "PostPersist", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(postRemoveEClass, PostRemove.class, "PostRemove", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(postUpdateEClass, PostUpdate.class, "PostUpdate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(prePersistEClass, PrePersist.class, "PrePersist", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(preRemoveEClass, PreRemove.class, "PreRemove", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(preUpdateEClass, PreUpdate.class, "PreUpdate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlQueryHintEClass, XmlQueryHint.class, "XmlQueryHint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlQueryHint_Description(), theXMLTypePackage.getString(), "description", "", 0, 1, XmlQueryHint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCollectionTableEClass, XmlCollectionTable.class, "XmlCollectionTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlCollectionTable_JoinColumns(), theOrmPackage.getXmlJoinColumn(), null, "joinColumns", null, 0, -1, XmlCollectionTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOrderColumnEClass, XmlOrderColumn.class, "XmlOrderColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlOrderColumn_Insertable(), theXMLTypePackage.getBooleanObject(), "insertable", null, 0, 1, XmlOrderColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOrderColumn_Nullable(), theXMLTypePackage.getBooleanObject(), "nullable", null, 0, 1, XmlOrderColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOrderColumn_Updatable(), theXMLTypePackage.getBooleanObject(), "updatable", null, 0, 1, XmlOrderColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOrderColumn_Table(), theXMLTypePackage.getString(), "table", null, 0, 1, XmlOrderColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMapKeyColumnEClass, XmlMapKeyColumn.class, "XmlMapKeyColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlMapKeyJoinColumnEClass, XmlMapKeyJoinColumn.class, "XmlMapKeyJoinColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlMapKeyClassEClass, XmlMapKeyClass.class, "XmlMapKeyClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlMapKeyClass_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, XmlMapKeyClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlUniqueConstraintEClass, XmlUniqueConstraint.class, "XmlUniqueConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlUniqueConstraint_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlUniqueConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(lockModeTypeEEnum, LockModeType.class, "LockModeType");
		addEEnumLiteral(lockModeTypeEEnum, LockModeType.READ);
		addEEnumLiteral(lockModeTypeEEnum, LockModeType.WRITE);
		addEEnumLiteral(lockModeTypeEEnum, LockModeType.OPTIMISTIC);
		addEEnumLiteral(lockModeTypeEEnum, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		addEEnumLiteral(lockModeTypeEEnum, LockModeType.PESSIMISTIC_READ);
		addEEnumLiteral(lockModeTypeEEnum, LockModeType.PESSIMISTIC_WRITE);
		addEEnumLiteral(lockModeTypeEEnum, LockModeType.PESSIMISTIC_FORCE_INCREMENT);

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEntityMappings
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlEntityMappings()
		 * @generated
		 */
		public static final EClass XML_ENTITY_MAPPINGS = eINSTANCE.getXmlEntityMappings();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitMetadata <em>Xml Persistence Unit Metadata</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitMetadata
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlPersistenceUnitMetadata()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE_UNIT_METADATA = eINSTANCE.getXmlPersistenceUnitMetadata();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT_METADATA__DESCRIPTION = eINSTANCE.getXmlPersistenceUnitMetadata_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitDefaults <em>Xml Persistence Unit Defaults</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitDefaults
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlPersistenceUnitDefaults()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE_UNIT_DEFAULTS = eINSTANCE.getXmlPersistenceUnitDefaults();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT_DEFAULTS__DESCRIPTION = eINSTANCE.getXmlPersistenceUnitDefaults_Description();

		/**
		 * The meta object literal for the '<em><b>Delimited Identifiers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT_DEFAULTS__DELIMITED_IDENTIFIERS = eINSTANCE.getXmlPersistenceUnitDefaults_DelimitedIdentifiers();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEntity <em>Xml Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEntity
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlEntity()
		 * @generated
		 */
		public static final EClass XML_ENTITY = eINSTANCE.getXmlEntity();

		/**
		 * The meta object literal for the '<em><b>Cacheable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ENTITY__CACHEABLE = eINSTANCE.getXmlEntity_Cacheable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlMappedSuperclass
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlMappedSuperclass()
		 * @generated
		 */
		public static final EClass XML_MAPPED_SUPERCLASS = eINSTANCE.getXmlMappedSuperclass();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddable <em>Xml Embeddable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddable
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlEmbeddable()
		 * @generated
		 */
		public static final EClass XML_EMBEDDABLE = eINSTANCE.getXmlEmbeddable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.Attributes <em>Attributes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Attributes
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getAttributes()
		 * @generated
		 */
		public static final EClass ATTRIBUTES = eINSTANCE.getAttributes();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ATTRIBUTES__DESCRIPTION = eINSTANCE.getAttributes_Description();

		/**
		 * The meta object literal for the '<em><b>Element Collections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__ELEMENT_COLLECTIONS = eINSTANCE.getAttributes_ElementCollections();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeMapping
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlAttributeMapping()
		 * @generated
		 */
		public static final EClass XML_ATTRIBUTE_MAPPING = eINSTANCE.getXmlAttributeMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection <em>Xml Element Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlElementCollection
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlElementCollection()
		 * @generated
		 */
		public static final EClass XML_ELEMENT_COLLECTION = eINSTANCE.getXmlElementCollection();

		/**
		 * The meta object literal for the '<em><b>Target Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION__TARGET_CLASS = eINSTANCE.getXmlElementCollection_TargetClass();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION__FETCH = eINSTANCE.getXmlElementCollection_Fetch();

		/**
		 * The meta object literal for the '<em><b>Order By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION__ORDER_BY = eINSTANCE.getXmlElementCollection_OrderBy();

		/**
		 * The meta object literal for the '<em><b>Order Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION__ORDER_COLUMN = eINSTANCE.getXmlElementCollection_OrderColumn();

		/**
		 * The meta object literal for the '<em><b>Map Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION__MAP_KEY = eINSTANCE.getXmlElementCollection_MapKey();

		/**
		 * The meta object literal for the '<em><b>Map Key Class</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION__MAP_KEY_CLASS = eINSTANCE.getXmlElementCollection_MapKeyClass();

		/**
		 * The meta object literal for the '<em><b>Map Key Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL = eINSTANCE.getXmlElementCollection_MapKeyTemporal();

		/**
		 * The meta object literal for the '<em><b>Map Key Enumerated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED = eINSTANCE.getXmlElementCollection_MapKeyEnumerated();

		/**
		 * The meta object literal for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES = eINSTANCE.getXmlElementCollection_MapKeyAttributeOverrides();

		/**
		 * The meta object literal for the '<em><b>Map Key Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN = eINSTANCE.getXmlElementCollection_MapKeyColumn();

		/**
		 * The meta object literal for the '<em><b>Map Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS = eINSTANCE.getXmlElementCollection_MapKeyJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION__COLUMN = eINSTANCE.getXmlElementCollection_Column();

		/**
		 * The meta object literal for the '<em><b>Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION__TEMPORAL = eINSTANCE.getXmlElementCollection_Temporal();

		/**
		 * The meta object literal for the '<em><b>Enumerated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION__ENUMERATED = eINSTANCE.getXmlElementCollection_Enumerated();

		/**
		 * The meta object literal for the '<em><b>Lob</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION__LOB = eINSTANCE.getXmlElementCollection_Lob();

		/**
		 * The meta object literal for the '<em><b>Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES = eINSTANCE.getXmlElementCollection_AttributeOverrides();

		/**
		 * The meta object literal for the '<em><b>Association Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES = eINSTANCE.getXmlElementCollection_AssociationOverrides();

		/**
		 * The meta object literal for the '<em><b>Collection Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION__COLLECTION_TABLE = eINSTANCE.getXmlElementCollection_CollectionTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlId <em>Xml Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlId
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlId()
		 * @generated
		 */
		public static final EClass XML_ID = eINSTANCE.getXmlId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddedId
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlEmbeddedId()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED_ID = eINSTANCE.getXmlEmbeddedId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbedded <em>Xml Embedded</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbedded
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlEmbedded()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED = eINSTANCE.getXmlEmbedded();

		/**
		 * The meta object literal for the '<em><b>Association Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EMBEDDED__ASSOCIATION_OVERRIDES = eINSTANCE.getXmlEmbedded_AssociationOverrides();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlBasic <em>Xml Basic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlBasic
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlBasic()
		 * @generated
		 */
		public static final EClass XML_BASIC = eINSTANCE.getXmlBasic();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlVersion <em>Xml Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlVersion
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlVersion()
		 * @generated
		 */
		public static final EClass XML_VERSION = eINSTANCE.getXmlVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne <em>Xml Many To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlManyToOne()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_ONE = eINSTANCE.getXmlManyToOne();

		/**
		 * The meta object literal for the '<em><b>Mapped By Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MANY_TO_ONE__MAPPED_BY_ID = eINSTANCE.getXmlManyToOne_MappedById();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MANY_TO_ONE__ID = eINSTANCE.getXmlManyToOne_Id();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany <em>Xml One To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlOneToMany()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_MANY = eINSTANCE.getXmlOneToMany();

		/**
		 * The meta object literal for the '<em><b>Order Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_MANY__ORDER_COLUMN = eINSTANCE.getXmlOneToMany_OrderColumn();

		/**
		 * The meta object literal for the '<em><b>Map Key Class</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_MANY__MAP_KEY_CLASS = eINSTANCE.getXmlOneToMany_MapKeyClass();

		/**
		 * The meta object literal for the '<em><b>Map Key Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_MANY__MAP_KEY_TEMPORAL = eINSTANCE.getXmlOneToMany_MapKeyTemporal();

		/**
		 * The meta object literal for the '<em><b>Map Key Enumerated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_MANY__MAP_KEY_ENUMERATED = eINSTANCE.getXmlOneToMany_MapKeyEnumerated();

		/**
		 * The meta object literal for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES = eINSTANCE.getXmlOneToMany_MapKeyAttributeOverrides();

		/**
		 * The meta object literal for the '<em><b>Map Key Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_MANY__MAP_KEY_COLUMN = eINSTANCE.getXmlOneToMany_MapKeyColumn();

		/**
		 * The meta object literal for the '<em><b>Map Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_MANY__MAP_KEY_JOIN_COLUMNS = eINSTANCE.getXmlOneToMany_MapKeyJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Orphan Removal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_MANY__ORPHAN_REMOVAL = eINSTANCE.getXmlOneToMany_OrphanRemoval();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne <em>Xml One To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlOneToOne()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_ONE = eINSTANCE.getXmlOneToOne();

		/**
		 * The meta object literal for the '<em><b>Orphan Removal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_ONE__ORPHAN_REMOVAL = eINSTANCE.getXmlOneToOne_OrphanRemoval();

		/**
		 * The meta object literal for the '<em><b>Mapped By Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_ONE__MAPPED_BY_ID = eINSTANCE.getXmlOneToOne_MappedById();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_ONE__ID = eINSTANCE.getXmlOneToOne_Id();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlManyToMany()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_MANY = eINSTANCE.getXmlManyToMany();

		/**
		 * The meta object literal for the '<em><b>Order Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_MANY__ORDER_COLUMN = eINSTANCE.getXmlManyToMany_OrderColumn();

		/**
		 * The meta object literal for the '<em><b>Map Key Class</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_MANY__MAP_KEY_CLASS = eINSTANCE.getXmlManyToMany_MapKeyClass();

		/**
		 * The meta object literal for the '<em><b>Map Key Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MANY_TO_MANY__MAP_KEY_TEMPORAL = eINSTANCE.getXmlManyToMany_MapKeyTemporal();

		/**
		 * The meta object literal for the '<em><b>Map Key Enumerated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MANY_TO_MANY__MAP_KEY_ENUMERATED = eINSTANCE.getXmlManyToMany_MapKeyEnumerated();

		/**
		 * The meta object literal for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES = eINSTANCE.getXmlManyToMany_MapKeyAttributeOverrides();

		/**
		 * The meta object literal for the '<em><b>Map Key Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_MANY__MAP_KEY_COLUMN = eINSTANCE.getXmlManyToMany_MapKeyColumn();

		/**
		 * The meta object literal for the '<em><b>Map Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS = eINSTANCE.getXmlManyToMany_MapKeyJoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlTransient <em>Xml Transient</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlTransient
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlTransient()
		 * @generated
		 */
		public static final EClass XML_TRANSIENT = eINSTANCE.getXmlTransient();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride <em>Xml Association Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlAssociationOverride()
		 * @generated
		 */
		public static final EClass XML_ASSOCIATION_OVERRIDE = eINSTANCE.getXmlAssociationOverride();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ASSOCIATION_OVERRIDE__DESCRIPTION = eINSTANCE.getXmlAssociationOverride_Description();

		/**
		 * The meta object literal for the '<em><b>Join Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ASSOCIATION_OVERRIDE__JOIN_TABLE = eINSTANCE.getXmlAssociationOverride_JoinTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeOverride <em>Xml Attribute Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeOverride
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlAttributeOverride()
		 * @generated
		 */
		public static final EClass XML_ATTRIBUTE_OVERRIDE = eINSTANCE.getXmlAttributeOverride();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ATTRIBUTE_OVERRIDE__DESCRIPTION = eINSTANCE.getXmlAttributeOverride_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.EntityListeners <em>Entity Listeners</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.EntityListeners
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getEntityListeners()
		 * @generated
		 */
		public static final EClass ENTITY_LISTENERS = eINSTANCE.getEntityListeners();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.EntityListener <em>Entity Listener</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.EntityListener
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getEntityListener()
		 * @generated
		 */
		public static final EClass ENTITY_LISTENER = eINSTANCE.getEntityListener();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_LISTENER__DESCRIPTION = eINSTANCE.getEntityListener_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlQuery <em>Xml Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlQuery
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlQuery()
		 * @generated
		 */
		public static final EClass XML_QUERY = eINSTANCE.getXmlQuery();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_QUERY__DESCRIPTION = eINSTANCE.getXmlQuery_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedNativeQuery <em>Xml Named Native Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedNativeQuery
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlNamedNativeQuery()
		 * @generated
		 */
		public static final EClass XML_NAMED_NATIVE_QUERY = eINSTANCE.getXmlNamedNativeQuery();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedQuery <em>Xml Named Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedQuery
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlNamedQuery()
		 * @generated
		 */
		public static final EClass XML_NAMED_QUERY = eINSTANCE.getXmlNamedQuery();

		/**
		 * The meta object literal for the '<em><b>Lock Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_QUERY__LOCK_MODE = eINSTANCE.getXmlNamedQuery_LockMode();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlGenerator <em>Xml Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlGenerator
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlGenerator()
		 * @generated
		 */
		public static final EClass XML_GENERATOR = eINSTANCE.getXmlGenerator();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_GENERATOR__DESCRIPTION = eINSTANCE.getXmlGenerator_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator <em>Xml Sequence Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlSequenceGenerator()
		 * @generated
		 */
		public static final EClass XML_SEQUENCE_GENERATOR = eINSTANCE.getXmlSequenceGenerator();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_SEQUENCE_GENERATOR__CATALOG = eINSTANCE.getXmlSequenceGenerator_Catalog();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_SEQUENCE_GENERATOR__SCHEMA = eINSTANCE.getXmlSequenceGenerator_Schema();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlTableGenerator <em>Xml Table Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlTableGenerator
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlTableGenerator()
		 * @generated
		 */
		public static final EClass XML_TABLE_GENERATOR = eINSTANCE.getXmlTableGenerator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.SqlResultSetMapping <em>Sql Result Set Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.SqlResultSetMapping
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getSqlResultSetMapping()
		 * @generated
		 */
		public static final EClass SQL_RESULT_SET_MAPPING = eINSTANCE.getSqlResultSetMapping();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute SQL_RESULT_SET_MAPPING__DESCRIPTION = eINSTANCE.getSqlResultSetMapping_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.EventMethod <em>Event Method</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.EventMethod
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getEventMethod()
		 * @generated
		 */
		public static final EClass EVENT_METHOD = eINSTANCE.getEventMethod();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EVENT_METHOD__DESCRIPTION = eINSTANCE.getEventMethod_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PostLoad <em>Post Load</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.PostLoad
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPostLoad()
		 * @generated
		 */
		public static final EClass POST_LOAD = eINSTANCE.getPostLoad();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PostPersist <em>Post Persist</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.PostPersist
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPostPersist()
		 * @generated
		 */
		public static final EClass POST_PERSIST = eINSTANCE.getPostPersist();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PostRemove <em>Post Remove</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.PostRemove
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPostRemove()
		 * @generated
		 */
		public static final EClass POST_REMOVE = eINSTANCE.getPostRemove();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PostUpdate <em>Post Update</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.PostUpdate
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPostUpdate()
		 * @generated
		 */
		public static final EClass POST_UPDATE = eINSTANCE.getPostUpdate();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PrePersist <em>Pre Persist</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.PrePersist
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPrePersist()
		 * @generated
		 */
		public static final EClass PRE_PERSIST = eINSTANCE.getPrePersist();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PreRemove <em>Pre Remove</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.PreRemove
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPreRemove()
		 * @generated
		 */
		public static final EClass PRE_REMOVE = eINSTANCE.getPreRemove();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.PreUpdate <em>Pre Update</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.PreUpdate
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getPreUpdate()
		 * @generated
		 */
		public static final EClass PRE_UPDATE = eINSTANCE.getPreUpdate();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlQueryHint <em>Xml Query Hint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlQueryHint
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlQueryHint()
		 * @generated
		 */
		public static final EClass XML_QUERY_HINT = eINSTANCE.getXmlQueryHint();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_QUERY_HINT__DESCRIPTION = eINSTANCE.getXmlQueryHint_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlCollectionTable <em>Xml Collection Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlCollectionTable
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlCollectionTable()
		 * @generated
		 */
		public static final EClass XML_COLLECTION_TABLE = eINSTANCE.getXmlCollectionTable();

		/**
		 * The meta object literal for the '<em><b>Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_COLLECTION_TABLE__JOIN_COLUMNS = eINSTANCE.getXmlCollectionTable_JoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn <em>Xml Order Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlOrderColumn()
		 * @generated
		 */
		public static final EClass XML_ORDER_COLUMN = eINSTANCE.getXmlOrderColumn();

		/**
		 * The meta object literal for the '<em><b>Insertable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ORDER_COLUMN__INSERTABLE = eINSTANCE.getXmlOrderColumn_Insertable();

		/**
		 * The meta object literal for the '<em><b>Nullable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ORDER_COLUMN__NULLABLE = eINSTANCE.getXmlOrderColumn_Nullable();

		/**
		 * The meta object literal for the '<em><b>Updatable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ORDER_COLUMN__UPDATABLE = eINSTANCE.getXmlOrderColumn_Updatable();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ORDER_COLUMN__TABLE = eINSTANCE.getXmlOrderColumn_Table();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyColumn <em>Xml Map Key Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyColumn
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlMapKeyColumn()
		 * @generated
		 */
		public static final EClass XML_MAP_KEY_COLUMN = eINSTANCE.getXmlMapKeyColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyJoinColumn <em>Xml Map Key Join Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyJoinColumn
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlMapKeyJoinColumn()
		 * @generated
		 */
		public static final EClass XML_MAP_KEY_JOIN_COLUMN = eINSTANCE.getXmlMapKeyJoinColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyClass <em>Xml Map Key Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyClass
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlMapKeyClass()
		 * @generated
		 */
		public static final EClass XML_MAP_KEY_CLASS = eINSTANCE.getXmlMapKeyClass();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MAP_KEY_CLASS__CLASS_NAME = eINSTANCE.getXmlMapKeyClass_ClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlUniqueConstraint <em>Xml Unique Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.XmlUniqueConstraint
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlUniqueConstraint()
		 * @generated
		 */
		public static final EClass XML_UNIQUE_CONSTRAINT = eINSTANCE.getXmlUniqueConstraint();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_UNIQUE_CONSTRAINT__NAME = eINSTANCE.getXmlUniqueConstraint_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.jpa2.resource.orm.LockModeType <em>Lock Mode Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.LockModeType
		 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getLockModeType()
		 * @generated
		 */
		public static final EEnum LOCK_MODE_TYPE = eINSTANCE.getLockModeType();

	}

} //Orm2_0Package
