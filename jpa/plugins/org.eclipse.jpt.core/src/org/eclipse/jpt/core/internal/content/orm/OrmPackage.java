/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.content.java.JpaJavaPackage;
import org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage;
import org.eclipse.jpt.core.internal.content.persistence.PersistencePackage;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

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
 * @see org.eclipse.jpt.core.internal.content.orm.OrmFactory
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
	public static final String eNS_PREFIX = "org.eclipse.jpt.core.content.orm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final OrmPackage eINSTANCE = org.eclipse.jpt.core.internal.content.orm.OrmPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode <em>Xml Root Content Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlRootContentNode()
	 * @generated
	 */
	public static final int XML_ROOT_CONTENT_NODE = 0;

	/**
	 * The feature id for the '<em><b>Jpa File</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ROOT_CONTENT_NODE__JPA_FILE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entity Mappings</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Root Content Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ROOT_CONTENT_NODE_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal <em>Entity Mappings Internal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal()
	 * @generated
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL = 1;

	/**
	 * The feature id for the '<em><b>Persistence Unit Metadata For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_FOR_XML = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Package For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__PACKAGE_FOR_XML = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Persistence Unit Metadata</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__PACKAGE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Root</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__ROOT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__VERSION = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__DESCRIPTION = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Persistence Unit Metadata Internal</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_INTERNAL = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Package Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__PACKAGE_INTERNAL = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__DEFAULT_SCHEMA = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__SPECIFIED_SCHEMA = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__SCHEMA = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__DEFAULT_CATALOG = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__SPECIFIED_CATALOG = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__CATALOG = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Default Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__DEFAULT_ACCESS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Specified Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__SPECIFIED_ACCESS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__ACCESS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Type Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__TYPE_MAPPINGS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Persistent Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__PERSISTENT_TYPES = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>Sequence Generators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__SEQUENCE_GENERATORS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 20;

	/**
	 * The feature id for the '<em><b>Table Generators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__TABLE_GENERATORS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 21;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__NAMED_QUERIES = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 22;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL__NAMED_NATIVE_QUERIES = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 23;

	/**
	 * The number of structural features of the '<em>Entity Mappings Internal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_INTERNAL_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 24;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappings <em>Entity Mappings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappings
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappings()
	 * @generated
	 */
	public static final int ENTITY_MAPPINGS = 2;

	/**
	 * The feature id for the '<em><b>Persistence Unit Metadata</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA = 0;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS__PACKAGE = 1;

	/**
	 * The number of structural features of the '<em>Entity Mappings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml <em>Entity Mappings For Xml</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsForXml()
	 * @generated
	 */
	public static final int ENTITY_MAPPINGS_FOR_XML = 3;

	/**
	 * The feature id for the '<em><b>Persistence Unit Metadata For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_FOR_XML__PERSISTENCE_UNIT_METADATA_FOR_XML = 0;

	/**
	 * The feature id for the '<em><b>Package For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_FOR_XML__PACKAGE_FOR_XML = 1;

	/**
	 * The number of structural features of the '<em>Entity Mappings For Xml</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_MAPPINGS_FOR_XML_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping <em>Xml Type Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTypeMapping()
	 * @generated
	 */
	public static final int XML_TYPE_MAPPING = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING__NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING__TABLE_NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING__DEFAULT_ACCESS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Specified Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING__SPECIFIED_ACCESS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING__ACCESS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING__METADATA_COMPLETE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Persistent Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING__PERSISTENT_TYPE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Xml Type Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType <em>Xml Persistent Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentType
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPersistentType()
	 * @generated
	 */
	public static final int XML_PERSISTENT_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Mapping Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENT_TYPE__MAPPING_KEY = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENT_TYPE__CLASS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attribute Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENT_TYPE__ATTRIBUTE_MAPPINGS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Specified Attribute Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENT_TYPE__SPECIFIED_ATTRIBUTE_MAPPINGS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Virtual Attribute Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENT_TYPE__VIRTUAL_ATTRIBUTE_MAPPINGS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Persistent Attributes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENT_TYPE__PERSISTENT_ATTRIBUTES = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Specified Persistent Attributes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENT_TYPE__SPECIFIED_PERSISTENT_ATTRIBUTES = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Virtual Persistent Attributes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENT_TYPE__VIRTUAL_PERSISTENT_ATTRIBUTES = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Xml Persistent Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENT_TYPE_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMappedSuperclass
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMappedSuperclass()
	 * @generated
	 */
	public static final int XML_MAPPED_SUPERCLASS = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__NAME = XML_TYPE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__TABLE_NAME = XML_TYPE_MAPPING__TABLE_NAME;

	/**
	 * The feature id for the '<em><b>Default Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__DEFAULT_ACCESS = XML_TYPE_MAPPING__DEFAULT_ACCESS;

	/**
	 * The feature id for the '<em><b>Specified Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__SPECIFIED_ACCESS = XML_TYPE_MAPPING__SPECIFIED_ACCESS;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ACCESS = XML_TYPE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__METADATA_COMPLETE = XML_TYPE_MAPPING__METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Persistent Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PERSISTENT_TYPE = XML_TYPE_MAPPING__PERSISTENT_TYPE;

	/**
	 * The feature id for the '<em><b>Id Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ID_CLASS = XML_TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id Class For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ID_CLASS_FOR_XML = XML_TYPE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Mapped Superclass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_FEATURE_COUNT = XML_TYPE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal <em>Xml Entity Internal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityInternal()
	 * @generated
	 */
	public static final int XML_ENTITY_INTERNAL = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__NAME = XML_TYPE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__TABLE_NAME = XML_TYPE_MAPPING__TABLE_NAME;

	/**
	 * The feature id for the '<em><b>Default Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__DEFAULT_ACCESS = XML_TYPE_MAPPING__DEFAULT_ACCESS;

	/**
	 * The feature id for the '<em><b>Specified Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__SPECIFIED_ACCESS = XML_TYPE_MAPPING__SPECIFIED_ACCESS;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__ACCESS = XML_TYPE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__METADATA_COMPLETE = XML_TYPE_MAPPING__METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Persistent Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__PERSISTENT_TYPE = XML_TYPE_MAPPING__PERSISTENT_TYPE;

	/**
	 * The feature id for the '<em><b>Table For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__TABLE_FOR_XML = XML_TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Discriminator Column For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN_FOR_XML = XML_TYPE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id Class For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML = XML_TYPE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Inheritance For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML = XML_TYPE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__SPECIFIED_NAME = XML_TYPE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__DEFAULT_NAME = XML_TYPE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__TABLE = XML_TYPE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Specified Secondary Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__SPECIFIED_SECONDARY_TABLES = XML_TYPE_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__PRIMARY_KEY_JOIN_COLUMNS = XML_TYPE_MAPPING_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Specified Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS = XML_TYPE_MAPPING_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Default Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS = XML_TYPE_MAPPING_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Inheritance Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__INHERITANCE_STRATEGY = XML_TYPE_MAPPING_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Default Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__DEFAULT_DISCRIMINATOR_VALUE = XML_TYPE_MAPPING_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Specified Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__SPECIFIED_DISCRIMINATOR_VALUE = XML_TYPE_MAPPING_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__DISCRIMINATOR_VALUE = XML_TYPE_MAPPING_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Discriminator Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN = XML_TYPE_MAPPING_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR = XML_TYPE_MAPPING_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__TABLE_GENERATOR = XML_TYPE_MAPPING_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__ATTRIBUTE_OVERRIDES = XML_TYPE_MAPPING_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Specified Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__SPECIFIED_ATTRIBUTE_OVERRIDES = XML_TYPE_MAPPING_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>Default Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__DEFAULT_ATTRIBUTE_OVERRIDES = XML_TYPE_MAPPING_FEATURE_COUNT + 20;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__ASSOCIATION_OVERRIDES = XML_TYPE_MAPPING_FEATURE_COUNT + 21;

	/**
	 * The feature id for the '<em><b>Specified Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__SPECIFIED_ASSOCIATION_OVERRIDES = XML_TYPE_MAPPING_FEATURE_COUNT + 22;

	/**
	 * The feature id for the '<em><b>Default Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__DEFAULT_ASSOCIATION_OVERRIDES = XML_TYPE_MAPPING_FEATURE_COUNT + 23;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__NAMED_QUERIES = XML_TYPE_MAPPING_FEATURE_COUNT + 24;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__NAMED_NATIVE_QUERIES = XML_TYPE_MAPPING_FEATURE_COUNT + 25;

	/**
	 * The feature id for the '<em><b>Id Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__ID_CLASS = XML_TYPE_MAPPING_FEATURE_COUNT + 26;

	/**
	 * The feature id for the '<em><b>Secondary Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__SECONDARY_TABLES = XML_TYPE_MAPPING_FEATURE_COUNT + 27;

	/**
	 * The feature id for the '<em><b>Virtual Secondary Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL__VIRTUAL_SECONDARY_TABLES = XML_TYPE_MAPPING_FEATURE_COUNT + 28;

	/**
	 * The number of structural features of the '<em>Xml Entity Internal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_INTERNAL_FEATURE_COUNT = XML_TYPE_MAPPING_FEATURE_COUNT + 29;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml <em>Xml Entity For Xml</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityForXml()
	 * @generated
	 */
	public static final int XML_ENTITY_FOR_XML = 8;

	/**
	 * The feature id for the '<em><b>Table For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_FOR_XML__TABLE_FOR_XML = 0;

	/**
	 * The feature id for the '<em><b>Discriminator Column For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_FOR_XML__DISCRIMINATOR_COLUMN_FOR_XML = 1;

	/**
	 * The feature id for the '<em><b>Id Class For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_FOR_XML__ID_CLASS_FOR_XML = 2;

	/**
	 * The feature id for the '<em><b>Inheritance For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_FOR_XML__INHERITANCE_FOR_XML = 3;

	/**
	 * The number of structural features of the '<em>Xml Entity For Xml</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_FOR_XML_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntity <em>Xml Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntity
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntity()
	 * @generated
	 */
	public static final int XML_ENTITY = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__NAME = JpaCoreMappingsPackage.IENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__TABLE_NAME = JpaCoreMappingsPackage.IENTITY__TABLE_NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SPECIFIED_NAME = JpaCoreMappingsPackage.IENTITY__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__DEFAULT_NAME = JpaCoreMappingsPackage.IENTITY__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__TABLE = JpaCoreMappingsPackage.IENTITY__TABLE;

	/**
	 * The feature id for the '<em><b>Specified Secondary Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SPECIFIED_SECONDARY_TABLES = JpaCoreMappingsPackage.IENTITY__SPECIFIED_SECONDARY_TABLES;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PRIMARY_KEY_JOIN_COLUMNS = JpaCoreMappingsPackage.IENTITY__PRIMARY_KEY_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Specified Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS = JpaCoreMappingsPackage.IENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Default Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS = JpaCoreMappingsPackage.IENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Inheritance Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__INHERITANCE_STRATEGY = JpaCoreMappingsPackage.IENTITY__INHERITANCE_STRATEGY;

	/**
	 * The feature id for the '<em><b>Default Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__DEFAULT_DISCRIMINATOR_VALUE = JpaCoreMappingsPackage.IENTITY__DEFAULT_DISCRIMINATOR_VALUE;

	/**
	 * The feature id for the '<em><b>Specified Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SPECIFIED_DISCRIMINATOR_VALUE = JpaCoreMappingsPackage.IENTITY__SPECIFIED_DISCRIMINATOR_VALUE;

	/**
	 * The feature id for the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__DISCRIMINATOR_VALUE = JpaCoreMappingsPackage.IENTITY__DISCRIMINATOR_VALUE;

	/**
	 * The feature id for the '<em><b>Discriminator Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__DISCRIMINATOR_COLUMN = JpaCoreMappingsPackage.IENTITY__DISCRIMINATOR_COLUMN;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SEQUENCE_GENERATOR = JpaCoreMappingsPackage.IENTITY__SEQUENCE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__TABLE_GENERATOR = JpaCoreMappingsPackage.IENTITY__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ATTRIBUTE_OVERRIDES = JpaCoreMappingsPackage.IENTITY__ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Specified Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES = JpaCoreMappingsPackage.IENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Default Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__DEFAULT_ATTRIBUTE_OVERRIDES = JpaCoreMappingsPackage.IENTITY__DEFAULT_ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ASSOCIATION_OVERRIDES = JpaCoreMappingsPackage.IENTITY__ASSOCIATION_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Specified Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SPECIFIED_ASSOCIATION_OVERRIDES = JpaCoreMappingsPackage.IENTITY__SPECIFIED_ASSOCIATION_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Default Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__DEFAULT_ASSOCIATION_OVERRIDES = JpaCoreMappingsPackage.IENTITY__DEFAULT_ASSOCIATION_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__NAMED_QUERIES = JpaCoreMappingsPackage.IENTITY__NAMED_QUERIES;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__NAMED_NATIVE_QUERIES = JpaCoreMappingsPackage.IENTITY__NAMED_NATIVE_QUERIES;

	/**
	 * The feature id for the '<em><b>Id Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ID_CLASS = JpaCoreMappingsPackage.IENTITY__ID_CLASS;

	/**
	 * The feature id for the '<em><b>Secondary Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SECONDARY_TABLES = JpaCoreMappingsPackage.IENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Virtual Secondary Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__VIRTUAL_SECONDARY_TABLES = JpaCoreMappingsPackage.IENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_FEATURE_COUNT = JpaCoreMappingsPackage.IENTITY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEmbeddable <em>Xml Embeddable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEmbeddable
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEmbeddable()
	 * @generated
	 */
	public static final int XML_EMBEDDABLE = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__NAME = XML_TYPE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__TABLE_NAME = XML_TYPE_MAPPING__TABLE_NAME;

	/**
	 * The feature id for the '<em><b>Default Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__DEFAULT_ACCESS = XML_TYPE_MAPPING__DEFAULT_ACCESS;

	/**
	 * The feature id for the '<em><b>Specified Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__SPECIFIED_ACCESS = XML_TYPE_MAPPING__SPECIFIED_ACCESS;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__ACCESS = XML_TYPE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__METADATA_COMPLETE = XML_TYPE_MAPPING__METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Persistent Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__PERSISTENT_TYPE = XML_TYPE_MAPPING__PERSISTENT_TYPE;

	/**
	 * The number of structural features of the '<em>Xml Embeddable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_FEATURE_COUNT = XML_TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlAttributeMapping()
	 * @generated
	 */
	public static final int XML_ATTRIBUTE_MAPPING = 11;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Attribute Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlNullAttributeMapping <em>Xml Null Attribute Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlNullAttributeMapping
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlNullAttributeMapping()
	 * @generated
	 */
	public static final int XML_NULL_ATTRIBUTE_MAPPING = 12;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NULL_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE = XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE;

	/**
	 * The number of structural features of the '<em>Xml Null Attribute Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NULL_ATTRIBUTE_MAPPING_FEATURE_COUNT = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlBasic <em>Xml Basic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlBasic
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlBasic()
	 * @generated
	 */
	public static final int XML_BASIC = 13;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__PERSISTENT_ATTRIBUTE = XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__FETCH = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__OPTIONAL = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__COLUMN = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__LOB = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__TEMPORAL = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__ENUMERATED = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Column For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__COLUMN_FOR_XML = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Xml Basic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_FEATURE_COUNT = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlId <em>Xml Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlId
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlId()
	 * @generated
	 */
	public static final int XML_ID = 14;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__PERSISTENT_ATTRIBUTE = XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__COLUMN = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__GENERATED_VALUE = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__TEMPORAL = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__TABLE_GENERATOR = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__SEQUENCE_GENERATOR = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Column For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__COLUMN_FOR_XML = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Xml Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_FEATURE_COUNT = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlTransient <em>Xml Transient</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTransient
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTransient()
	 * @generated
	 */
	public static final int XML_TRANSIENT = 15;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT__PERSISTENT_ATTRIBUTE = XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE;

	/**
	 * The number of structural features of the '<em>Xml Transient</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT_FEATURE_COUNT = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEmbedded <em>Xml Embedded</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEmbedded
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEmbedded()
	 * @generated
	 */
	public static final int XML_EMBEDDED = 16;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__PERSISTENT_ATTRIBUTE = XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ATTRIBUTE_OVERRIDES = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Embedded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_FEATURE_COUNT = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEmbeddedId
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEmbeddedId()
	 * @generated
	 */
	public static final int XML_EMBEDDED_ID = 17;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__PERSISTENT_ATTRIBUTE = XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE;

	/**
	 * The number of structural features of the '<em>Xml Embedded Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID_FEATURE_COUNT = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlVersion <em>Xml Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlVersion
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlVersion()
	 * @generated
	 */
	public static final int XML_VERSION = 18;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__PERSISTENT_ATTRIBUTE = XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__COLUMN = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__TEMPORAL = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Column For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__COLUMN_FOR_XML = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_FEATURE_COUNT = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping <em>Xml Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlRelationshipMapping()
	 * @generated
	 */
	public static final int XML_RELATIONSHIP_MAPPING = 40;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RELATIONSHIP_MAPPING__PERSISTENT_ATTRIBUTE = XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RELATIONSHIP_MAPPING__TARGET_ENTITY = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RELATIONSHIP_MAPPING__CASCADE = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Xml Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RELATIONSHIP_MAPPING_FEATURE_COUNT = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal <em>Xml Multi Relationship Mapping Internal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMultiRelationshipMappingInternal()
	 * @generated
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL = 19;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__PERSISTENT_ATTRIBUTE = XML_RELATIONSHIP_MAPPING__PERSISTENT_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__TARGET_ENTITY = XML_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__SPECIFIED_TARGET_ENTITY = XML_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__DEFAULT_TARGET_ENTITY = XML_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__RESOLVED_TARGET_ENTITY = XML_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__CASCADE = XML_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAPPED_BY = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__ORDER_BY = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__FETCH = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Join Table For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE_FOR_XML = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Map Key For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Xml Multi Relationship Mapping Internal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL_FEATURE_COUNT = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml <em>Xml Multi Relationship Mapping For Xml</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMultiRelationshipMappingForXml()
	 * @generated
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML = 20;

	/**
	 * The feature id for the '<em><b>Join Table For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML__JOIN_TABLE_FOR_XML = 0;

	/**
	 * The feature id for the '<em><b>Map Key For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML__MAP_KEY_FOR_XML = 1;

	/**
	 * The number of structural features of the '<em>Xml Multi Relationship Mapping For Xml</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMapping <em>Xml Multi Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMapping
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMultiRelationshipMapping()
	 * @generated
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING = 21;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY = JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY = JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY = JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY = JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING__CASCADE = JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY = JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING__ORDER_BY = JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING__FETCH = JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE = JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY = JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__MAP_KEY;

	/**
	 * The number of structural features of the '<em>Xml Multi Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT = JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlOneToMany <em>Xml One To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlOneToMany
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlOneToMany()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY = 22;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__PERSISTENT_ATTRIBUTE = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__PERSISTENT_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__TARGET_ENTITY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__SPECIFIED_TARGET_ENTITY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__DEFAULT_TARGET_ENTITY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__RESOLVED_TARGET_ENTITY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__CASCADE = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAPPED_BY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ORDER_BY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__FETCH = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__JOIN_TABLE = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Join Table For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__JOIN_TABLE_FOR_XML = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE_FOR_XML;

	/**
	 * The feature id for the '<em><b>Map Key For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_FOR_XML = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML;

	/**
	 * The number of structural features of the '<em>Xml One To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_FEATURE_COUNT = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlManyToMany <em>Xml Many To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlManyToMany
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlManyToMany()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY = 23;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__PERSISTENT_ATTRIBUTE = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__PERSISTENT_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__TARGET_ENTITY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__SPECIFIED_TARGET_ENTITY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__DEFAULT_TARGET_ENTITY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__RESOLVED_TARGET_ENTITY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__CASCADE = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAPPED_BY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ORDER_BY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__FETCH = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__JOIN_TABLE = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Join Table For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__JOIN_TABLE_FOR_XML = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE_FOR_XML;

	/**
	 * The feature id for the '<em><b>Map Key For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_FOR_XML = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML;

	/**
	 * The number of structural features of the '<em>Xml Many To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_FEATURE_COUNT = XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute <em>Xml Persistent Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPersistentAttribute()
	 * @generated
	 */
	public static final int XML_PERSISTENT_ATTRIBUTE = 24;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENT_ATTRIBUTE__MAPPING = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENT_ATTRIBUTE__NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Persistent Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENT_ATTRIBUTE_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal <em>Persistence Unit Metadata Internal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadataInternal()
	 * @generated
	 */
	public static final int PERSISTENCE_UNIT_METADATA_INTERNAL = 25;

	/**
	 * The feature id for the '<em><b>Xml Mapping Metadata Complete For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_FOR_XML = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Persistence Unit Defaults For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_FOR_XML = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Xml Mapping Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Persistence Unit Defaults</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Xml Mapping Metadata Complete Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_INTERNAL = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Persistence Unit Defaults Internal</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_INTERNAL = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Persistence Unit Metadata Internal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA_INTERNAL_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata <em>Persistence Unit Metadata</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadata()
	 * @generated
	 */
	public static final int PERSISTENCE_UNIT_METADATA = 26;

	/**
	 * The feature id for the '<em><b>Xml Mapping Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Persistence Unit Defaults</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Persistence Unit Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA_FEATURE_COUNT = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml <em>Persistence Unit Metadata For Xml</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadataForXml()
	 * @generated
	 */
	public static final int PERSISTENCE_UNIT_METADATA_FOR_XML = 27;

	/**
	 * The feature id for the '<em><b>Xml Mapping Metadata Complete For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA_FOR_XML__XML_MAPPING_METADATA_COMPLETE_FOR_XML = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Persistence Unit Defaults For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA_FOR_XML__PERSISTENCE_UNIT_DEFAULTS_FOR_XML = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Persistence Unit Metadata For Xml</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_METADATA_FOR_XML_FEATURE_COUNT = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal <em>Persistence Unit Defaults Internal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsInternal()
	 * @generated
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL = 28;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Cascade Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Schema For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_FOR_XML = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Catalog For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_FOR_XML = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Access For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_FOR_XML = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Cascade Persist For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_FOR_XML = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Schema Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_INTERNAL = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Catalog Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_INTERNAL = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Access Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_INTERNAL = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Cascade Persist Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_INTERNAL = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>Persistence Unit Defaults Internal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_INTERNAL_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 12;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults <em>Persistence Unit Defaults</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaults()
	 * @generated
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS = 29;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS__SCHEMA = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS__CATALOG = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS__ACCESS = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Cascade Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Persistence Unit Defaults</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_FEATURE_COUNT = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml <em>Persistence Unit Defaults For Xml</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsForXml()
	 * @generated
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_FOR_XML = 30;

	/**
	 * The feature id for the '<em><b>Schema For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_FOR_XML__SCHEMA_FOR_XML = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Catalog For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_FOR_XML__CATALOG_FOR_XML = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Access For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_FOR_XML__ACCESS_FOR_XML = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Cascade Persist For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_FOR_XML__CASCADE_PERSIST_FOR_XML = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Persistence Unit Defaults For Xml</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_DEFAULTS_FOR_XML_FEATURE_COUNT = JpaCorePackage.IXML_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable <em>Abstract Xml Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlTable()
	 * @generated
	 */
	public static final int ABSTRACT_XML_TABLE = 42;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__SPECIFIED_NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__DEFAULT_NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__CATALOG = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__SPECIFIED_CATALOG = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__DEFAULT_CATALOG = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__SCHEMA = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__DEFAULT_SCHEMA = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Specified Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__SPECIFIED_NAME_FOR_XML = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Specified Catalog For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__SPECIFIED_CATALOG_FOR_XML = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Specified Schema For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA_FOR_XML = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 12;

	/**
	 * The number of structural features of the '<em>Abstract Xml Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 13;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlTable <em>Xml Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTable
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTable()
	 * @generated
	 */
	public static final int XML_TABLE = 31;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__NAME = ABSTRACT_XML_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__SPECIFIED_NAME = ABSTRACT_XML_TABLE__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__DEFAULT_NAME = ABSTRACT_XML_TABLE__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__CATALOG = ABSTRACT_XML_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__SPECIFIED_CATALOG = ABSTRACT_XML_TABLE__SPECIFIED_CATALOG;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__DEFAULT_CATALOG = ABSTRACT_XML_TABLE__DEFAULT_CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__SCHEMA = ABSTRACT_XML_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__SPECIFIED_SCHEMA = ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__DEFAULT_SCHEMA = ABSTRACT_XML_TABLE__DEFAULT_SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Specified Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__SPECIFIED_NAME_FOR_XML = ABSTRACT_XML_TABLE__SPECIFIED_NAME_FOR_XML;

	/**
	 * The feature id for the '<em><b>Specified Catalog For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__SPECIFIED_CATALOG_FOR_XML = ABSTRACT_XML_TABLE__SPECIFIED_CATALOG_FOR_XML;

	/**
	 * The feature id for the '<em><b>Specified Schema For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__SPECIFIED_SCHEMA_FOR_XML = ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA_FOR_XML;

	/**
	 * The number of structural features of the '<em>Xml Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_FEATURE_COUNT = ABSTRACT_XML_TABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn <em>Abstract Xml Named Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlNamedColumn()
	 * @generated
	 */
	public static final int ABSTRACT_XML_NAMED_COLUMN = 32;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_NAMED_COLUMN__NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_NAMED_COLUMN__DEFAULT_NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Specified Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME_FOR_XML = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Column Definition For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION_FOR_XML = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Abstract Xml Named Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn <em>Abstract Xml Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlColumn()
	 * @generated
	 */
	public static final int ABSTRACT_XML_COLUMN = 33;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__NAME = ABSTRACT_XML_NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__SPECIFIED_NAME = ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__DEFAULT_NAME = ABSTRACT_XML_NAMED_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__COLUMN_DEFINITION = ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Specified Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__SPECIFIED_NAME_FOR_XML = ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME_FOR_XML;

	/**
	 * The feature id for the '<em><b>Column Definition For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__COLUMN_DEFINITION_FOR_XML = ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION_FOR_XML;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__UNIQUE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__NULLABLE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__INSERTABLE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__UPDATABLE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__TABLE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__SPECIFIED_TABLE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__DEFAULT_TABLE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Unique For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__UNIQUE_FOR_XML = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Nullable For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__NULLABLE_FOR_XML = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Insertable For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__INSERTABLE_FOR_XML = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Updatable For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__UPDATABLE_FOR_XML = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Specified Table For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__SPECIFIED_TABLE_FOR_XML = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>Abstract Xml Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN_FEATURE_COUNT = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 12;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn <em>Xml Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlColumn
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlColumn()
	 * @generated
	 */
	public static final int XML_COLUMN = 34;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__NAME = ABSTRACT_XML_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__SPECIFIED_NAME = ABSTRACT_XML_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__DEFAULT_NAME = ABSTRACT_XML_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__COLUMN_DEFINITION = ABSTRACT_XML_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Specified Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__SPECIFIED_NAME_FOR_XML = ABSTRACT_XML_COLUMN__SPECIFIED_NAME_FOR_XML;

	/**
	 * The feature id for the '<em><b>Column Definition For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__COLUMN_DEFINITION_FOR_XML = ABSTRACT_XML_COLUMN__COLUMN_DEFINITION_FOR_XML;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__UNIQUE = ABSTRACT_XML_COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__NULLABLE = ABSTRACT_XML_COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__INSERTABLE = ABSTRACT_XML_COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__UPDATABLE = ABSTRACT_XML_COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__TABLE = ABSTRACT_XML_COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__SPECIFIED_TABLE = ABSTRACT_XML_COLUMN__SPECIFIED_TABLE;

	/**
	 * The feature id for the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__DEFAULT_TABLE = ABSTRACT_XML_COLUMN__DEFAULT_TABLE;

	/**
	 * The feature id for the '<em><b>Unique For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__UNIQUE_FOR_XML = ABSTRACT_XML_COLUMN__UNIQUE_FOR_XML;

	/**
	 * The feature id for the '<em><b>Nullable For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__NULLABLE_FOR_XML = ABSTRACT_XML_COLUMN__NULLABLE_FOR_XML;

	/**
	 * The feature id for the '<em><b>Insertable For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__INSERTABLE_FOR_XML = ABSTRACT_XML_COLUMN__INSERTABLE_FOR_XML;

	/**
	 * The feature id for the '<em><b>Updatable For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__UPDATABLE_FOR_XML = ABSTRACT_XML_COLUMN__UPDATABLE_FOR_XML;

	/**
	 * The feature id for the '<em><b>Specified Table For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__SPECIFIED_TABLE_FOR_XML = ABSTRACT_XML_COLUMN__SPECIFIED_TABLE_FOR_XML;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__LENGTH = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__SPECIFIED_LENGTH = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Precision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__PRECISION = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Specified Precision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__SPECIFIED_PRECISION = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__SCALE = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Specified Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__SPECIFIED_SCALE = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Length For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__LENGTH_FOR_XML = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Precision For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__PRECISION_FOR_XML = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Scale For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__SCALE_FOR_XML = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Xml Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN_FEATURE_COUNT = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlJoinColumn <em>Xml Join Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlJoinColumn
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlJoinColumn()
	 * @generated
	 */
	public static final int XML_JOIN_COLUMN = 35;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__NAME = ABSTRACT_XML_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__SPECIFIED_NAME = ABSTRACT_XML_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__DEFAULT_NAME = ABSTRACT_XML_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__COLUMN_DEFINITION = ABSTRACT_XML_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Specified Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__SPECIFIED_NAME_FOR_XML = ABSTRACT_XML_COLUMN__SPECIFIED_NAME_FOR_XML;

	/**
	 * The feature id for the '<em><b>Column Definition For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__COLUMN_DEFINITION_FOR_XML = ABSTRACT_XML_COLUMN__COLUMN_DEFINITION_FOR_XML;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__UNIQUE = ABSTRACT_XML_COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__NULLABLE = ABSTRACT_XML_COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__INSERTABLE = ABSTRACT_XML_COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__UPDATABLE = ABSTRACT_XML_COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__TABLE = ABSTRACT_XML_COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__SPECIFIED_TABLE = ABSTRACT_XML_COLUMN__SPECIFIED_TABLE;

	/**
	 * The feature id for the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__DEFAULT_TABLE = ABSTRACT_XML_COLUMN__DEFAULT_TABLE;

	/**
	 * The feature id for the '<em><b>Unique For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__UNIQUE_FOR_XML = ABSTRACT_XML_COLUMN__UNIQUE_FOR_XML;

	/**
	 * The feature id for the '<em><b>Nullable For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__NULLABLE_FOR_XML = ABSTRACT_XML_COLUMN__NULLABLE_FOR_XML;

	/**
	 * The feature id for the '<em><b>Insertable For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__INSERTABLE_FOR_XML = ABSTRACT_XML_COLUMN__INSERTABLE_FOR_XML;

	/**
	 * The feature id for the '<em><b>Updatable For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__UPDATABLE_FOR_XML = ABSTRACT_XML_COLUMN__UPDATABLE_FOR_XML;

	/**
	 * The feature id for the '<em><b>Specified Table For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__SPECIFIED_TABLE_FOR_XML = ABSTRACT_XML_COLUMN__SPECIFIED_TABLE_FOR_XML;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__REFERENCED_COLUMN_NAME = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Specified Referenced Column Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Xml Join Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN_FEATURE_COUNT = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.IXmlColumnMapping <em>IXml Column Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.IXmlColumnMapping
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIXmlColumnMapping()
	 * @generated
	 */
	public static final int IXML_COLUMN_MAPPING = 36;

	/**
	 * The feature id for the '<em><b>Column For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IXML_COLUMN_MAPPING__COLUMN_FOR_XML = JpaCoreMappingsPackage.ICOLUMN_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IXml Column Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IXML_COLUMN_MAPPING_FEATURE_COUNT = JpaCoreMappingsPackage.ICOLUMN_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlSingleRelationshipMapping <em>Xml Single Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlSingleRelationshipMapping
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlSingleRelationshipMapping()
	 * @generated
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING = 39;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING__PERSISTENT_ATTRIBUTE = XML_RELATIONSHIP_MAPPING__PERSISTENT_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY = XML_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY = XML_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY = XML_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY = XML_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING__CASCADE = XML_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING__FETCH = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Xml Single Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT = XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlManyToOne <em>Xml Many To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlManyToOne
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlManyToOne()
	 * @generated
	 */
	public static final int XML_MANY_TO_ONE = 37;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__PERSISTENT_ATTRIBUTE = XML_SINGLE_RELATIONSHIP_MAPPING__PERSISTENT_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__TARGET_ENTITY = XML_SINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__SPECIFIED_TARGET_ENTITY = XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__DEFAULT_TARGET_ENTITY = XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__RESOLVED_TARGET_ENTITY = XML_SINGLE_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__CASCADE = XML_SINGLE_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__FETCH = XML_SINGLE_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__JOIN_COLUMNS = XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__SPECIFIED_JOIN_COLUMNS = XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__DEFAULT_JOIN_COLUMNS = XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__OPTIONAL = XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL;

	/**
	 * The number of structural features of the '<em>Xml Many To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_FEATURE_COUNT = XML_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlOneToOne <em>Xml One To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlOneToOne
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlOneToOne()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE = 38;

	/**
	 * The feature id for the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__PERSISTENT_ATTRIBUTE = XML_SINGLE_RELATIONSHIP_MAPPING__PERSISTENT_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__TARGET_ENTITY = XML_SINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__SPECIFIED_TARGET_ENTITY = XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__DEFAULT_TARGET_ENTITY = XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__RESOLVED_TARGET_ENTITY = XML_SINGLE_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__CASCADE = XML_SINGLE_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__FETCH = XML_SINGLE_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__JOIN_COLUMNS = XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__SPECIFIED_JOIN_COLUMNS = XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__DEFAULT_JOIN_COLUMNS = XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__OPTIONAL = XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__MAPPED_BY = XML_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml One To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_FEATURE_COUNT = XML_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlJoinTable <em>Xml Join Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlJoinTable
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlJoinTable()
	 * @generated
	 */
	public static final int XML_JOIN_TABLE = 41;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__NAME = ABSTRACT_XML_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__SPECIFIED_NAME = ABSTRACT_XML_TABLE__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__DEFAULT_NAME = ABSTRACT_XML_TABLE__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__CATALOG = ABSTRACT_XML_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__SPECIFIED_CATALOG = ABSTRACT_XML_TABLE__SPECIFIED_CATALOG;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__DEFAULT_CATALOG = ABSTRACT_XML_TABLE__DEFAULT_CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__SCHEMA = ABSTRACT_XML_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__SPECIFIED_SCHEMA = ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__DEFAULT_SCHEMA = ABSTRACT_XML_TABLE__DEFAULT_SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Specified Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__SPECIFIED_NAME_FOR_XML = ABSTRACT_XML_TABLE__SPECIFIED_NAME_FOR_XML;

	/**
	 * The feature id for the '<em><b>Specified Catalog For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__SPECIFIED_CATALOG_FOR_XML = ABSTRACT_XML_TABLE__SPECIFIED_CATALOG_FOR_XML;

	/**
	 * The feature id for the '<em><b>Specified Schema For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__SPECIFIED_SCHEMA_FOR_XML = ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA_FOR_XML;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__JOIN_COLUMNS = ABSTRACT_XML_TABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS = ABSTRACT_XML_TABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__DEFAULT_JOIN_COLUMNS = ABSTRACT_XML_TABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS = ABSTRACT_XML_TABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Specified Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS = ABSTRACT_XML_TABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Default Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS = ABSTRACT_XML_TABLE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Xml Join Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE_FEATURE_COUNT = ABSTRACT_XML_TABLE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlOverride <em>Xml Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlOverride
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlOverride()
	 * @generated
	 */
	public static final int XML_OVERRIDE = 43;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OVERRIDE__NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OVERRIDE_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeOverride <em>Xml Attribute Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlAttributeOverride
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlAttributeOverride()
	 * @generated
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE = 44;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE__NAME = XML_OVERRIDE__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE__COLUMN = XML_OVERRIDE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Column For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE__COLUMN_FOR_XML = XML_OVERRIDE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Attribute Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE_FEATURE_COUNT = XML_OVERRIDE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlAssociationOverride <em>Xml Association Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlAssociationOverride
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlAssociationOverride()
	 * @generated
	 */
	public static final int XML_ASSOCIATION_OVERRIDE = 45;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE__NAME = XML_OVERRIDE__NAME;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS = XML_OVERRIDE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS = XML_OVERRIDE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS = XML_OVERRIDE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Association Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE_FEATURE_COUNT = XML_OVERRIDE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn <em>Xml Discriminator Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlDiscriminatorColumn()
	 * @generated
	 */
	public static final int XML_DISCRIMINATOR_COLUMN = 46;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__NAME = ABSTRACT_XML_NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME = ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__DEFAULT_NAME = ABSTRACT_XML_NAMED_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION = ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Specified Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME_FOR_XML = ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME_FOR_XML;

	/**
	 * The feature id for the '<em><b>Column Definition For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION_FOR_XML = ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION_FOR_XML;

	/**
	 * The feature id for the '<em><b>Discriminator Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Specified Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__LENGTH = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Discriminator Type For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE_FOR_XML = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Specified Length For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH_FOR_XML = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Xml Discriminator Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN_FEATURE_COUNT = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlSecondaryTable <em>Xml Secondary Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlSecondaryTable
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlSecondaryTable()
	 * @generated
	 */
	public static final int XML_SECONDARY_TABLE = 47;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__NAME = ABSTRACT_XML_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__SPECIFIED_NAME = ABSTRACT_XML_TABLE__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__DEFAULT_NAME = ABSTRACT_XML_TABLE__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__CATALOG = ABSTRACT_XML_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__SPECIFIED_CATALOG = ABSTRACT_XML_TABLE__SPECIFIED_CATALOG;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__DEFAULT_CATALOG = ABSTRACT_XML_TABLE__DEFAULT_CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__SCHEMA = ABSTRACT_XML_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__SPECIFIED_SCHEMA = ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__DEFAULT_SCHEMA = ABSTRACT_XML_TABLE__DEFAULT_SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Specified Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__SPECIFIED_NAME_FOR_XML = ABSTRACT_XML_TABLE__SPECIFIED_NAME_FOR_XML;

	/**
	 * The feature id for the '<em><b>Specified Catalog For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__SPECIFIED_CATALOG_FOR_XML = ABSTRACT_XML_TABLE__SPECIFIED_CATALOG_FOR_XML;

	/**
	 * The feature id for the '<em><b>Specified Schema For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__SPECIFIED_SCHEMA_FOR_XML = ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA_FOR_XML;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS = ABSTRACT_XML_TABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS = ABSTRACT_XML_TABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS = ABSTRACT_XML_TABLE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Secondary Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE_FEATURE_COUNT = ABSTRACT_XML_TABLE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn <em>Xml Primary Key Join Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPrimaryKeyJoinColumn()
	 * @generated
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN = 48;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN__NAME = ABSTRACT_XML_NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_NAME = ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_NAME = ABSTRACT_XML_NAMED_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION = ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Specified Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_NAME_FOR_XML = ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME_FOR_XML;

	/**
	 * The feature id for the '<em><b>Column Definition For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION_FOR_XML = ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION_FOR_XML;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Specified Referenced Column Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Xml Primary Key Join Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN_FEATURE_COUNT = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlGeneratedValue <em>Xml Generated Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlGeneratedValue
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlGeneratedValue()
	 * @generated
	 */
	public static final int XML_GENERATED_VALUE = 49;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATED_VALUE__STRATEGY = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATED_VALUE__GENERATOR = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Generated Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATED_VALUE_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlGenerator <em>Xml Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlGenerator
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlGenerator()
	 * @generated
	 */
	public static final int XML_GENERATOR = 50;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__INITIAL_VALUE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Specified Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__SPECIFIED_INITIAL_VALUE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Default Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__DEFAULT_INITIAL_VALUE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__ALLOCATION_SIZE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Specified Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__SPECIFIED_ALLOCATION_SIZE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Default Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__DEFAULT_ALLOCATION_SIZE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Xml Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlSequenceGenerator <em>Xml Sequence Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlSequenceGenerator
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlSequenceGenerator()
	 * @generated
	 */
	public static final int XML_SEQUENCE_GENERATOR = 51;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__NAME = XML_GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__INITIAL_VALUE = XML_GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Specified Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__SPECIFIED_INITIAL_VALUE = XML_GENERATOR__SPECIFIED_INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Default Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__DEFAULT_INITIAL_VALUE = XML_GENERATOR__DEFAULT_INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__ALLOCATION_SIZE = XML_GENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Specified Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__SPECIFIED_ALLOCATION_SIZE = XML_GENERATOR__SPECIFIED_ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Default Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__DEFAULT_ALLOCATION_SIZE = XML_GENERATOR__DEFAULT_ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__SEQUENCE_NAME = XML_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME = XML_GENERATOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME = XML_GENERATOR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Sequence Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR_FEATURE_COUNT = XML_GENERATOR_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlTableGenerator <em>Xml Table Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTableGenerator
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTableGenerator()
	 * @generated
	 */
	public static final int XML_TABLE_GENERATOR = 52;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__NAME = XML_GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__INITIAL_VALUE = XML_GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Specified Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__SPECIFIED_INITIAL_VALUE = XML_GENERATOR__SPECIFIED_INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Default Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__DEFAULT_INITIAL_VALUE = XML_GENERATOR__DEFAULT_INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__ALLOCATION_SIZE = XML_GENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Specified Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__SPECIFIED_ALLOCATION_SIZE = XML_GENERATOR__SPECIFIED_ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Default Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__DEFAULT_ALLOCATION_SIZE = XML_GENERATOR__DEFAULT_ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__TABLE = XML_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__SPECIFIED_TABLE = XML_GENERATOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__DEFAULT_TABLE = XML_GENERATOR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__CATALOG = XML_GENERATOR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__SPECIFIED_CATALOG = XML_GENERATOR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__DEFAULT_CATALOG = XML_GENERATOR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__SCHEMA = XML_GENERATOR_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__SPECIFIED_SCHEMA = XML_GENERATOR_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__DEFAULT_SCHEMA = XML_GENERATOR_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__PK_COLUMN_NAME = XML_GENERATOR_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Specified Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME = XML_GENERATOR_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Default Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__DEFAULT_PK_COLUMN_NAME = XML_GENERATOR_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__VALUE_COLUMN_NAME = XML_GENERATOR_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Specified Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME = XML_GENERATOR_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Default Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__DEFAULT_VALUE_COLUMN_NAME = XML_GENERATOR_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__PK_COLUMN_VALUE = XML_GENERATOR_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Specified Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE = XML_GENERATOR_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Default Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__DEFAULT_PK_COLUMN_VALUE = XML_GENERATOR_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__UNIQUE_CONSTRAINTS = XML_GENERATOR_FEATURE_COUNT + 18;

	/**
	 * The number of structural features of the '<em>Xml Table Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR_FEATURE_COUNT = XML_GENERATOR_FEATURE_COUNT + 19;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlQuery <em>Abstract Xml Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlQuery
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlQuery()
	 * @generated
	 */
	public static final int ABSTRACT_XML_QUERY = 53;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_QUERY__NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_QUERY__QUERY = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_QUERY__HINTS = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Abstract Xml Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_QUERY_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlNamedQuery <em>Xml Named Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlNamedQuery
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlNamedQuery()
	 * @generated
	 */
	public static final int XML_NAMED_QUERY = 54;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY__NAME = ABSTRACT_XML_QUERY__NAME;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY__QUERY = ABSTRACT_XML_QUERY__QUERY;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY__HINTS = ABSTRACT_XML_QUERY__HINTS;

	/**
	 * The number of structural features of the '<em>Xml Named Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY_FEATURE_COUNT = ABSTRACT_XML_QUERY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlNamedNativeQuery <em>Xml Named Native Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlNamedNativeQuery
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlNamedNativeQuery()
	 * @generated
	 */
	public static final int XML_NAMED_NATIVE_QUERY = 55;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__NAME = ABSTRACT_XML_QUERY__NAME;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__QUERY = ABSTRACT_XML_QUERY__QUERY;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__HINTS = ABSTRACT_XML_QUERY__HINTS;

	/**
	 * The feature id for the '<em><b>Result Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__RESULT_CLASS = ABSTRACT_XML_QUERY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Result Set Mapping</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING = ABSTRACT_XML_QUERY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Named Native Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY_FEATURE_COUNT = ABSTRACT_XML_QUERY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlQueryHint <em>Xml Query Hint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlQueryHint
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlQueryHint()
	 * @generated
	 */
	public static final int XML_QUERY_HINT = 56;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_HINT__NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_HINT__VALUE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Query Hint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_HINT_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlUniqueConstraint <em>Xml Unique Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlUniqueConstraint
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlUniqueConstraint()
	 * @generated
	 */
	public static final int XML_UNIQUE_CONSTRAINT = 57;

	/**
	 * The feature id for the '<em><b>Column Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNIQUE_CONSTRAINT__COLUMN_NAMES = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Unique Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNIQUE_CONSTRAINT_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlCascade <em>Xml Cascade</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlCascade
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlCascade()
	 * @generated
	 */
	public static final int XML_CASCADE = 58;

	/**
	 * The feature id for the '<em><b>All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CASCADE__ALL = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CASCADE__PERSIST = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Merge</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CASCADE__MERGE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Remove</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CASCADE__REMOVE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Refresh</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CASCADE__REFRESH = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Xml Cascade</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CASCADE_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlIdClass <em>Xml Id Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlIdClass
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlIdClass()
	 * @generated
	 */
	public static final int XML_ID_CLASS = 59;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_CLASS__VALUE = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Id Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_CLASS_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlInheritance <em>Xml Inheritance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlInheritance
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlInheritance()
	 * @generated
	 */
	public static final int XML_INHERITANCE = 60;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INHERITANCE__STRATEGY = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Inheritance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INHERITANCE_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMapKey <em>Xml Map Key</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMapKey
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMapKey()
	 * @generated
	 */
	public static final int XML_MAP_KEY = 61;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY__NAME = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Map Key</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_FEATURE_COUNT = JpaCorePackage.XML_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlRootContentNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityMappingsInternalEClass = null;

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
	private EClass entityMappingsForXmlEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTypeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPersistentTypeEClass = null;

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
	private EClass xmlEntityInternalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntityForXmlEClass = null;

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
	private EClass xmlEmbeddableEClass = null;

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
	private EClass xmlNullAttributeMappingEClass = null;

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
	private EClass xmlIdEClass = null;

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
	private EClass xmlEmbeddedEClass = null;

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
	private EClass xmlVersionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMultiRelationshipMappingInternalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMultiRelationshipMappingForXmlEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMultiRelationshipMappingEClass = null;

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
	private EClass xmlManyToManyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPersistentAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass persistenceUnitMetadataInternalEClass = null;

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
	private EClass persistenceUnitMetadataForXmlEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass persistenceUnitDefaultsInternalEClass = null;

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
	private EClass persistenceUnitDefaultsForXmlEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractXmlNamedColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractXmlColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlJoinColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iXmlColumnMappingEClass = null;

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
	private EClass xmlOneToOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlSingleRelationshipMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlRelationshipMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlJoinTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractXmlTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOverrideEClass = null;

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
	private EClass xmlAssociationOverrideEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlDiscriminatorColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlSecondaryTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPrimaryKeyJoinColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlGeneratedValueEClass = null;

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
	private EClass abstractXmlQueryEClass = null;

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
	private EClass xmlNamedNativeQueryEClass = null;

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
	private EClass xmlUniqueConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCascadeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlIdClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlInheritanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMapKeyEClass = null;

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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private OrmPackage() {
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
	public static OrmPackage init() {
		if (isInited)
			return (OrmPackage) EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI);
		// Obtain or create and register package
		OrmPackage theOrmPackage = (OrmPackage) (EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new OrmPackage());
		isInited = true;
		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		// Obtain or create and register interdependencies
		JpaCorePackage theJpaCorePackage = (JpaCorePackage) (EPackage.Registry.INSTANCE.getEPackage(JpaCorePackage.eNS_URI) instanceof JpaCorePackage ? EPackage.Registry.INSTANCE.getEPackage(JpaCorePackage.eNS_URI) : JpaCorePackage.eINSTANCE);
		JpaCoreMappingsPackage theJpaCoreMappingsPackage = (JpaCoreMappingsPackage) (EPackage.Registry.INSTANCE.getEPackage(JpaCoreMappingsPackage.eNS_URI) instanceof JpaCoreMappingsPackage ? EPackage.Registry.INSTANCE.getEPackage(JpaCoreMappingsPackage.eNS_URI) : JpaCoreMappingsPackage.eINSTANCE);
		JpaJavaPackage theJpaJavaPackage = (JpaJavaPackage) (EPackage.Registry.INSTANCE.getEPackage(JpaJavaPackage.eNS_URI) instanceof JpaJavaPackage ? EPackage.Registry.INSTANCE.getEPackage(JpaJavaPackage.eNS_URI) : JpaJavaPackage.eINSTANCE);
		JpaJavaMappingsPackage theJpaJavaMappingsPackage = (JpaJavaMappingsPackage) (EPackage.Registry.INSTANCE.getEPackage(JpaJavaMappingsPackage.eNS_URI) instanceof JpaJavaMappingsPackage ? EPackage.Registry.INSTANCE.getEPackage(JpaJavaMappingsPackage.eNS_URI) : JpaJavaMappingsPackage.eINSTANCE);
		PersistencePackage thePersistencePackage = (PersistencePackage) (EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);
		// Create package meta-data objects
		theOrmPackage.createPackageContents();
		theJpaCorePackage.createPackageContents();
		theJpaCoreMappingsPackage.createPackageContents();
		theJpaJavaPackage.createPackageContents();
		theJpaJavaMappingsPackage.createPackageContents();
		thePersistencePackage.createPackageContents();
		// Initialize created meta-data
		theOrmPackage.initializePackageContents();
		theJpaCorePackage.initializePackageContents();
		theJpaCoreMappingsPackage.initializePackageContents();
		theJpaJavaPackage.initializePackageContents();
		theJpaJavaMappingsPackage.initializePackageContents();
		thePersistencePackage.initializePackageContents();
		// Mark meta-data to indicate it can't be changed
		theOrmPackage.freeze();
		return theOrmPackage;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode <em>Xml Root Content Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Root Content Node</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode
	 * @generated
	 */
	public EClass getXmlRootContentNode() {
		return xmlRootContentNodeEClass;
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode#getEntityMappings <em>Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entity Mappings</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode#getEntityMappings()
	 * @see #getXmlRootContentNode()
	 * @generated
	 */
	public EReference getXmlRootContentNode_EntityMappings() {
		return (EReference) xmlRootContentNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal <em>Entity Mappings Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Mappings Internal</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal
	 * @generated
	 */
	public EClass getEntityMappingsInternal() {
		return entityMappingsInternalEClass;
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Root</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getRoot()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EReference getEntityMappingsInternal_Root() {
		return (EReference) entityMappingsInternalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getVersion()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EAttribute getEntityMappingsInternal_Version() {
		return (EAttribute) entityMappingsInternalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDescription()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EAttribute getEntityMappingsInternal_Description() {
		return (EAttribute) entityMappingsInternalEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getPersistenceUnitMetadataInternal <em>Persistence Unit Metadata Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Persistence Unit Metadata Internal</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getPersistenceUnitMetadataInternal()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EReference getEntityMappingsInternal_PersistenceUnitMetadataInternal() {
		return (EReference) entityMappingsInternalEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getPackageInternal <em>Package Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package Internal</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getPackageInternal()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EAttribute getEntityMappingsInternal_PackageInternal() {
		return (EAttribute) entityMappingsInternalEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDefaultSchema <em>Default Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDefaultSchema()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EAttribute getEntityMappingsInternal_DefaultSchema() {
		return (EAttribute) entityMappingsInternalEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSpecifiedSchema <em>Specified Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSpecifiedSchema()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EAttribute getEntityMappingsInternal_SpecifiedSchema() {
		return (EAttribute) entityMappingsInternalEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSchema()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EAttribute getEntityMappingsInternal_Schema() {
		return (EAttribute) entityMappingsInternalEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDefaultCatalog <em>Default Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDefaultCatalog()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EAttribute getEntityMappingsInternal_DefaultCatalog() {
		return (EAttribute) entityMappingsInternalEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSpecifiedCatalog <em>Specified Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSpecifiedCatalog()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EAttribute getEntityMappingsInternal_SpecifiedCatalog() {
		return (EAttribute) entityMappingsInternalEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getCatalog()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EAttribute getEntityMappingsInternal_Catalog() {
		return (EAttribute) entityMappingsInternalEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDefaultAccess <em>Default Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Access</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDefaultAccess()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EAttribute getEntityMappingsInternal_DefaultAccess() {
		return (EAttribute) entityMappingsInternalEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSpecifiedAccess <em>Specified Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Access</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSpecifiedAccess()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EAttribute getEntityMappingsInternal_SpecifiedAccess() {
		return (EAttribute) entityMappingsInternalEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getAccess <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getAccess()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EAttribute getEntityMappingsInternal_Access() {
		return (EAttribute) entityMappingsInternalEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getTypeMappings <em>Type Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Type Mappings</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getTypeMappings()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EReference getEntityMappingsInternal_TypeMappings() {
		return (EReference) entityMappingsInternalEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getPersistentTypes <em>Persistent Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Persistent Types</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getPersistentTypes()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EReference getEntityMappingsInternal_PersistentTypes() {
		return (EReference) entityMappingsInternalEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSequenceGenerators <em>Sequence Generators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sequence Generators</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSequenceGenerators()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EReference getEntityMappingsInternal_SequenceGenerators() {
		return (EReference) entityMappingsInternalEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getTableGenerators <em>Table Generators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Table Generators</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getTableGenerators()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EReference getEntityMappingsInternal_TableGenerators() {
		return (EReference) entityMappingsInternalEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getNamedQueries <em>Named Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Queries</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getNamedQueries()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EReference getEntityMappingsInternal_NamedQueries() {
		return (EReference) entityMappingsInternalEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getNamedNativeQueries <em>Named Native Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Native Queries</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getNamedNativeQueries()
	 * @see #getEntityMappingsInternal()
	 * @generated
	 */
	public EReference getEntityMappingsInternal_NamedNativeQueries() {
		return (EReference) entityMappingsInternalEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappings <em>Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Mappings</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappings
	 * @generated
	 */
	public EClass getEntityMappings() {
		return entityMappingsEClass;
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappings#getPersistenceUnitMetadata <em>Persistence Unit Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Persistence Unit Metadata</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappings#getPersistenceUnitMetadata()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EReference getEntityMappings_PersistenceUnitMetadata() {
		return (EReference) entityMappingsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappings#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappings#getPackage()
	 * @see #getEntityMappings()
	 * @generated
	 */
	public EAttribute getEntityMappings_Package() {
		return (EAttribute) entityMappingsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml <em>Entity Mappings For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Mappings For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml
	 * @generated
	 */
	public EClass getEntityMappingsForXml() {
		return entityMappingsForXmlEClass;
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml#getPersistenceUnitMetadataForXml <em>Persistence Unit Metadata For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Persistence Unit Metadata For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml#getPersistenceUnitMetadataForXml()
	 * @see #getEntityMappingsForXml()
	 * @generated
	 */
	public EReference getEntityMappingsForXml_PersistenceUnitMetadataForXml() {
		return (EReference) entityMappingsForXmlEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml#getPackageForXml <em>Package For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml#getPackageForXml()
	 * @see #getEntityMappingsForXml()
	 * @generated
	 */
	public EAttribute getEntityMappingsForXml_PackageForXml() {
		return (EAttribute) entityMappingsForXmlEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping <em>Xml Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Type Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping
	 * @generated
	 */
	public EClass getXmlTypeMapping() {
		return xmlTypeMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getDefaultAccess <em>Default Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Access</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getDefaultAccess()
	 * @see #getXmlTypeMapping()
	 * @generated
	 */
	public EAttribute getXmlTypeMapping_DefaultAccess() {
		return (EAttribute) xmlTypeMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getSpecifiedAccess <em>Specified Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Access</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getSpecifiedAccess()
	 * @see #getXmlTypeMapping()
	 * @generated
	 */
	public EAttribute getXmlTypeMapping_SpecifiedAccess() {
		return (EAttribute) xmlTypeMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getAccess <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getAccess()
	 * @see #getXmlTypeMapping()
	 * @generated
	 */
	public EAttribute getXmlTypeMapping_Access() {
		return (EAttribute) xmlTypeMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getMetadataComplete <em>Metadata Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metadata Complete</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getMetadataComplete()
	 * @see #getXmlTypeMapping()
	 * @generated
	 */
	public EAttribute getXmlTypeMapping_MetadataComplete() {
		return (EAttribute) xmlTypeMappingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getPersistentType <em>Persistent Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Persistent Type</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getPersistentType()
	 * @see #getXmlTypeMapping()
	 * @generated
	 */
	public EReference getXmlTypeMapping_PersistentType() {
		return (EReference) xmlTypeMappingEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType <em>Xml Persistent Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistent Type</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentType
	 * @generated
	 */
	public EClass getXmlPersistentType() {
		return xmlPersistentTypeEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getClass_()
	 * @see #getXmlPersistentType()
	 * @generated
	 */
	public EAttribute getXmlPersistentType_Class() {
		return (EAttribute) xmlPersistentTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getAttributeMappings <em>Attribute Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Mappings</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getAttributeMappings()
	 * @see #getXmlPersistentType()
	 * @generated
	 */
	public EReference getXmlPersistentType_AttributeMappings() {
		return (EReference) xmlPersistentTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getSpecifiedAttributeMappings <em>Specified Attribute Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Specified Attribute Mappings</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getSpecifiedAttributeMappings()
	 * @see #getXmlPersistentType()
	 * @generated
	 */
	public EReference getXmlPersistentType_SpecifiedAttributeMappings() {
		return (EReference) xmlPersistentTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getVirtualAttributeMappings <em>Virtual Attribute Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Virtual Attribute Mappings</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getVirtualAttributeMappings()
	 * @see #getXmlPersistentType()
	 * @generated
	 */
	public EReference getXmlPersistentType_VirtualAttributeMappings() {
		return (EReference) xmlPersistentTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getPersistentAttributes <em>Persistent Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Persistent Attributes</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getPersistentAttributes()
	 * @see #getXmlPersistentType()
	 * @generated
	 */
	public EReference getXmlPersistentType_PersistentAttributes() {
		return (EReference) xmlPersistentTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getSpecifiedPersistentAttributes <em>Specified Persistent Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Specified Persistent Attributes</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getSpecifiedPersistentAttributes()
	 * @see #getXmlPersistentType()
	 * @generated
	 */
	public EReference getXmlPersistentType_SpecifiedPersistentAttributes() {
		return (EReference) xmlPersistentTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getVirtualPersistentAttributes <em>Virtual Persistent Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Virtual Persistent Attributes</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getVirtualPersistentAttributes()
	 * @see #getXmlPersistentType()
	 * @generated
	 */
	public EReference getXmlPersistentType_VirtualPersistentAttributes() {
		return (EReference) xmlPersistentTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mapped Superclass</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMappedSuperclass
	 * @generated
	 */
	public EClass getXmlMappedSuperclass() {
		return xmlMappedSuperclassEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.content.orm.XmlMappedSuperclass#getIdClassForXml <em>Id Class For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Id Class For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMappedSuperclass#getIdClassForXml()
	 * @see #getXmlMappedSuperclass()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_IdClassForXml() {
		return (EReference) xmlMappedSuperclassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal <em>Xml Entity Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity Internal</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal
	 * @generated
	 */
	public EClass getXmlEntityInternal() {
		return xmlEntityInternalEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml <em>Xml Entity For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml
	 * @generated
	 */
	public EClass getXmlEntityForXml() {
		return xmlEntityForXmlEClass;
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getTableForXml <em>Table For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Table For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getTableForXml()
	 * @see #getXmlEntityForXml()
	 * @generated
	 */
	public EReference getXmlEntityForXml_TableForXml() {
		return (EReference) xmlEntityForXmlEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getDiscriminatorColumnForXml <em>Discriminator Column For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Discriminator Column For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getDiscriminatorColumnForXml()
	 * @see #getXmlEntityForXml()
	 * @generated
	 */
	public EReference getXmlEntityForXml_DiscriminatorColumnForXml() {
		return (EReference) xmlEntityForXmlEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getIdClassForXml <em>Id Class For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Id Class For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getIdClassForXml()
	 * @see #getXmlEntityForXml()
	 * @generated
	 */
	public EReference getXmlEntityForXml_IdClassForXml() {
		return (EReference) xmlEntityForXmlEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getInheritanceForXml <em>Inheritance For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inheritance For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getInheritanceForXml()
	 * @see #getXmlEntityForXml()
	 * @generated
	 */
	public EReference getXmlEntityForXml_InheritanceForXml() {
		return (EReference) xmlEntityForXmlEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntity <em>Xml Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntity
	 * @generated
	 */
	public EClass getXmlEntity() {
		return xmlEntityEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntity#getSecondaryTables <em>Secondary Tables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Secondary Tables</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntity#getSecondaryTables()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_SecondaryTables() {
		return (EReference) xmlEntityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntity#getVirtualSecondaryTables <em>Virtual Secondary Tables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Virtual Secondary Tables</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntity#getVirtualSecondaryTables()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_VirtualSecondaryTables() {
		return (EReference) xmlEntityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlEmbeddable <em>Xml Embeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embeddable</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEmbeddable
	 * @generated
	 */
	public EClass getXmlEmbeddable() {
		return xmlEmbeddableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Attribute Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping
	 * @generated
	 */
	public EClass getXmlAttributeMapping() {
		return xmlAttributeMappingEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping#getPersistentAttribute <em>Persistent Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Persistent Attribute</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping#getPersistentAttribute()
	 * @see #getXmlAttributeMapping()
	 * @generated
	 */
	public EReference getXmlAttributeMapping_PersistentAttribute() {
		return (EReference) xmlAttributeMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlNullAttributeMapping <em>Xml Null Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Null Attribute Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlNullAttributeMapping
	 * @generated
	 */
	public EClass getXmlNullAttributeMapping() {
		return xmlNullAttributeMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlBasic <em>Xml Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlBasic
	 * @generated
	 */
	public EClass getXmlBasic() {
		return xmlBasicEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlId <em>Xml Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlId
	 * @generated
	 */
	public EClass getXmlId() {
		return xmlIdEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlTransient <em>Xml Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Transient</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTransient
	 * @generated
	 */
	public EClass getXmlTransient() {
		return xmlTransientEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlEmbedded <em>Xml Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEmbedded
	 * @generated
	 */
	public EClass getXmlEmbedded() {
		return xmlEmbeddedEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded Id</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEmbeddedId
	 * @generated
	 */
	public EClass getXmlEmbeddedId() {
		return xmlEmbeddedIdEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Version</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlVersion
	 * @generated
	 */
	public EClass getXmlVersion() {
		return xmlVersionEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal <em>Xml Multi Relationship Mapping Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Multi Relationship Mapping Internal</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal
	 * @generated
	 */
	public EClass getXmlMultiRelationshipMappingInternal() {
		return xmlMultiRelationshipMappingInternalEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml <em>Xml Multi Relationship Mapping For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Multi Relationship Mapping For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml
	 * @generated
	 */
	public EClass getXmlMultiRelationshipMappingForXml() {
		return xmlMultiRelationshipMappingForXmlEClass;
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml#getJoinTableForXml <em>Join Table For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Join Table For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml#getJoinTableForXml()
	 * @see #getXmlMultiRelationshipMappingForXml()
	 * @generated
	 */
	public EReference getXmlMultiRelationshipMappingForXml_JoinTableForXml() {
		return (EReference) xmlMultiRelationshipMappingForXmlEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml#getMapKeyForXml <em>Map Key For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml#getMapKeyForXml()
	 * @see #getXmlMultiRelationshipMappingForXml()
	 * @generated
	 */
	public EReference getXmlMultiRelationshipMappingForXml_MapKeyForXml() {
		return (EReference) xmlMultiRelationshipMappingForXmlEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMapping <em>Xml Multi Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Multi Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMapping
	 * @generated
	 */
	public EClass getXmlMultiRelationshipMapping() {
		return xmlMultiRelationshipMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlOneToMany <em>Xml One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlOneToMany
	 * @generated
	 */
	public EClass getXmlOneToMany() {
		return xmlOneToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlManyToMany <em>Xml Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlManyToMany
	 * @generated
	 */
	public EClass getXmlManyToMany() {
		return xmlManyToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute <em>Xml Persistent Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistent Attribute</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute
	 * @generated
	 */
	public EClass getXmlPersistentAttribute() {
		return xmlPersistentAttributeEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute#getName()
	 * @see #getXmlPersistentAttribute()
	 * @generated
	 */
	public EAttribute getXmlPersistentAttribute_Name() {
		return (EAttribute) xmlPersistentAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal <em>Persistence Unit Metadata Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Persistence Unit Metadata Internal</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal
	 * @generated
	 */
	public EClass getPersistenceUnitMetadataInternal() {
		return persistenceUnitMetadataInternalEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal#isXmlMappingMetadataCompleteInternal <em>Xml Mapping Metadata Complete Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Mapping Metadata Complete Internal</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal#isXmlMappingMetadataCompleteInternal()
	 * @see #getPersistenceUnitMetadataInternal()
	 * @generated
	 */
	public EAttribute getPersistenceUnitMetadataInternal_XmlMappingMetadataCompleteInternal() {
		return (EAttribute) persistenceUnitMetadataInternalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal#getPersistenceUnitDefaultsInternal <em>Persistence Unit Defaults Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Persistence Unit Defaults Internal</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal#getPersistenceUnitDefaultsInternal()
	 * @see #getPersistenceUnitMetadataInternal()
	 * @generated
	 */
	public EReference getPersistenceUnitMetadataInternal_PersistenceUnitDefaultsInternal() {
		return (EReference) persistenceUnitMetadataInternalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata <em>Persistence Unit Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Persistence Unit Metadata</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata
	 * @generated
	 */
	public EClass getPersistenceUnitMetadata() {
		return persistenceUnitMetadataEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata#isXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Mapping Metadata Complete</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata#isXmlMappingMetadataComplete()
	 * @see #getPersistenceUnitMetadata()
	 * @generated
	 */
	public EAttribute getPersistenceUnitMetadata_XmlMappingMetadataComplete() {
		return (EAttribute) persistenceUnitMetadataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata#getPersistenceUnitDefaults <em>Persistence Unit Defaults</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Persistence Unit Defaults</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata#getPersistenceUnitDefaults()
	 * @see #getPersistenceUnitMetadata()
	 * @generated
	 */
	public EReference getPersistenceUnitMetadata_PersistenceUnitDefaults() {
		return (EReference) persistenceUnitMetadataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml <em>Persistence Unit Metadata For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Persistence Unit Metadata For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml
	 * @generated
	 */
	public EClass getPersistenceUnitMetadataForXml() {
		return persistenceUnitMetadataForXmlEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml#isXmlMappingMetadataCompleteForXml <em>Xml Mapping Metadata Complete For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Mapping Metadata Complete For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml#isXmlMappingMetadataCompleteForXml()
	 * @see #getPersistenceUnitMetadataForXml()
	 * @generated
	 */
	public EAttribute getPersistenceUnitMetadataForXml_XmlMappingMetadataCompleteForXml() {
		return (EAttribute) persistenceUnitMetadataForXmlEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml#getPersistenceUnitDefaultsForXml <em>Persistence Unit Defaults For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Persistence Unit Defaults For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml#getPersistenceUnitDefaultsForXml()
	 * @see #getPersistenceUnitMetadataForXml()
	 * @generated
	 */
	public EReference getPersistenceUnitMetadataForXml_PersistenceUnitDefaultsForXml() {
		return (EReference) persistenceUnitMetadataForXmlEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal <em>Persistence Unit Defaults Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Persistence Unit Defaults Internal</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal
	 * @generated
	 */
	public EClass getPersistenceUnitDefaultsInternal() {
		return persistenceUnitDefaultsInternalEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getSchemaInternal <em>Schema Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema Internal</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getSchemaInternal()
	 * @see #getPersistenceUnitDefaultsInternal()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaultsInternal_SchemaInternal() {
		return (EAttribute) persistenceUnitDefaultsInternalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getCatalogInternal <em>Catalog Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog Internal</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getCatalogInternal()
	 * @see #getPersistenceUnitDefaultsInternal()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaultsInternal_CatalogInternal() {
		return (EAttribute) persistenceUnitDefaultsInternalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getAccessInternal <em>Access Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access Internal</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getAccessInternal()
	 * @see #getPersistenceUnitDefaultsInternal()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaultsInternal_AccessInternal() {
		return (EAttribute) persistenceUnitDefaultsInternalEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#isCascadePersistInternal <em>Cascade Persist Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade Persist Internal</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#isCascadePersistInternal()
	 * @see #getPersistenceUnitDefaultsInternal()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaultsInternal_CascadePersistInternal() {
		return (EAttribute) persistenceUnitDefaultsInternalEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults <em>Persistence Unit Defaults</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Persistence Unit Defaults</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults
	 * @generated
	 */
	public EClass getPersistenceUnitDefaults() {
		return persistenceUnitDefaultsEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults#getSchema()
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaults_Schema() {
		return (EAttribute) persistenceUnitDefaultsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults#getCatalog()
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaults_Catalog() {
		return (EAttribute) persistenceUnitDefaultsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults#getAccess <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults#getAccess()
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaults_Access() {
		return (EAttribute) persistenceUnitDefaultsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults#isCascadePersist <em>Cascade Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade Persist</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults#isCascadePersist()
	 * @see #getPersistenceUnitDefaults()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaults_CascadePersist() {
		return (EAttribute) persistenceUnitDefaultsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml <em>Persistence Unit Defaults For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Persistence Unit Defaults For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml
	 * @generated
	 */
	public EClass getPersistenceUnitDefaultsForXml() {
		return persistenceUnitDefaultsForXmlEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#getSchemaForXml <em>Schema For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#getSchemaForXml()
	 * @see #getPersistenceUnitDefaultsForXml()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaultsForXml_SchemaForXml() {
		return (EAttribute) persistenceUnitDefaultsForXmlEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#getCatalogForXml <em>Catalog For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#getCatalogForXml()
	 * @see #getPersistenceUnitDefaultsForXml()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaultsForXml_CatalogForXml() {
		return (EAttribute) persistenceUnitDefaultsForXmlEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#getAccessForXml <em>Access For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#getAccessForXml()
	 * @see #getPersistenceUnitDefaultsForXml()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaultsForXml_AccessForXml() {
		return (EAttribute) persistenceUnitDefaultsForXmlEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#isCascadePersistForXml <em>Cascade Persist For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade Persist For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#isCascadePersistForXml()
	 * @see #getPersistenceUnitDefaultsForXml()
	 * @generated
	 */
	public EAttribute getPersistenceUnitDefaultsForXml_CascadePersistForXml() {
		return (EAttribute) persistenceUnitDefaultsForXmlEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlTable <em>Xml Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Table</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTable
	 * @generated
	 */
	public EClass getXmlTable() {
		return xmlTableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn <em>Abstract Xml Named Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Named Column</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn
	 * @generated
	 */
	public EClass getAbstractXmlNamedColumn() {
		return abstractXmlNamedColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn#getSpecifiedNameForXml <em>Specified Name For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Name For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn#getSpecifiedNameForXml()
	 * @see #getAbstractXmlNamedColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlNamedColumn_SpecifiedNameForXml() {
		return (EAttribute) abstractXmlNamedColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn#getColumnDefinitionForXml <em>Column Definition For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column Definition For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn#getColumnDefinitionForXml()
	 * @see #getAbstractXmlNamedColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlNamedColumn_ColumnDefinitionForXml() {
		return (EAttribute) abstractXmlNamedColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn <em>Abstract Xml Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Column</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn
	 * @generated
	 */
	public EClass getAbstractXmlColumn() {
		return abstractXmlColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getUniqueForXml <em>Unique For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getUniqueForXml()
	 * @see #getAbstractXmlColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlColumn_UniqueForXml() {
		return (EAttribute) abstractXmlColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getNullableForXml <em>Nullable For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nullable For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getNullableForXml()
	 * @see #getAbstractXmlColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlColumn_NullableForXml() {
		return (EAttribute) abstractXmlColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getInsertableForXml <em>Insertable For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Insertable For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getInsertableForXml()
	 * @see #getAbstractXmlColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlColumn_InsertableForXml() {
		return (EAttribute) abstractXmlColumnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getUpdatableForXml <em>Updatable For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Updatable For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getUpdatableForXml()
	 * @see #getAbstractXmlColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlColumn_UpdatableForXml() {
		return (EAttribute) abstractXmlColumnEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getSpecifiedTableForXml <em>Specified Table For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Table For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getSpecifiedTableForXml()
	 * @see #getAbstractXmlColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlColumn_SpecifiedTableForXml() {
		return (EAttribute) abstractXmlColumnEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn <em>Xml Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Column</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlColumn
	 * @generated
	 */
	public EClass getXmlColumn() {
		return xmlColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn#getLengthForXml <em>Length For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlColumn#getLengthForXml()
	 * @see #getXmlColumn()
	 * @generated
	 */
	public EAttribute getXmlColumn_LengthForXml() {
		return (EAttribute) xmlColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn#getPrecisionForXml <em>Precision For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Precision For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlColumn#getPrecisionForXml()
	 * @see #getXmlColumn()
	 * @generated
	 */
	public EAttribute getXmlColumn_PrecisionForXml() {
		return (EAttribute) xmlColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn#getScaleForXml <em>Scale For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scale For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlColumn#getScaleForXml()
	 * @see #getXmlColumn()
	 * @generated
	 */
	public EAttribute getXmlColumn_ScaleForXml() {
		return (EAttribute) xmlColumnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlJoinColumn <em>Xml Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Join Column</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlJoinColumn
	 * @generated
	 */
	public EClass getXmlJoinColumn() {
		return xmlJoinColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlJoinColumn#getSpecifiedReferencedColumnNameForXml <em>Specified Referenced Column Name For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Referenced Column Name For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlJoinColumn#getSpecifiedReferencedColumnNameForXml()
	 * @see #getXmlJoinColumn()
	 * @generated
	 */
	public EAttribute getXmlJoinColumn_SpecifiedReferencedColumnNameForXml() {
		return (EAttribute) xmlJoinColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.IXmlColumnMapping <em>IXml Column Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IXml Column Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.IXmlColumnMapping
	 * @generated
	 */
	public EClass getIXmlColumnMapping() {
		return iXmlColumnMappingEClass;
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.jpt.core.internal.content.orm.IXmlColumnMapping#getColumnForXml <em>Column For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Column For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.IXmlColumnMapping#getColumnForXml()
	 * @see #getIXmlColumnMapping()
	 * @generated
	 */
	public EReference getIXmlColumnMapping_ColumnForXml() {
		return (EReference) iXmlColumnMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlManyToOne <em>Xml Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To One</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlManyToOne
	 * @generated
	 */
	public EClass getXmlManyToOne() {
		return xmlManyToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlOneToOne <em>Xml One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlOneToOne
	 * @generated
	 */
	public EClass getXmlOneToOne() {
		return xmlOneToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlSingleRelationshipMapping <em>Xml Single Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Single Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlSingleRelationshipMapping
	 * @generated
	 */
	public EClass getXmlSingleRelationshipMapping() {
		return xmlSingleRelationshipMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping <em>Xml Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping
	 * @generated
	 */
	public EClass getXmlRelationshipMapping() {
		return xmlRelationshipMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlJoinTable <em>Xml Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Join Table</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlJoinTable
	 * @generated
	 */
	public EClass getXmlJoinTable() {
		return xmlJoinTableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable <em>Abstract Xml Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Table</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable
	 * @generated
	 */
	public EClass getAbstractXmlTable() {
		return abstractXmlTableEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedNameForXml <em>Specified Name For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Name For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedNameForXml()
	 * @see #getAbstractXmlTable()
	 * @generated
	 */
	public EAttribute getAbstractXmlTable_SpecifiedNameForXml() {
		return (EAttribute) abstractXmlTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedCatalogForXml <em>Specified Catalog For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Catalog For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedCatalogForXml()
	 * @see #getAbstractXmlTable()
	 * @generated
	 */
	public EAttribute getAbstractXmlTable_SpecifiedCatalogForXml() {
		return (EAttribute) abstractXmlTableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedSchemaForXml <em>Specified Schema For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Schema For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedSchemaForXml()
	 * @see #getAbstractXmlTable()
	 * @generated
	 */
	public EAttribute getAbstractXmlTable_SpecifiedSchemaForXml() {
		return (EAttribute) abstractXmlTableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlOverride <em>Xml Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Override</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlOverride
	 * @generated
	 */
	public EClass getXmlOverride() {
		return xmlOverrideEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeOverride <em>Xml Attribute Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Attribute Override</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlAttributeOverride
	 * @generated
	 */
	public EClass getXmlAttributeOverride() {
		return xmlAttributeOverrideEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlAssociationOverride <em>Xml Association Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Association Override</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlAssociationOverride
	 * @generated
	 */
	public EClass getXmlAssociationOverride() {
		return xmlAssociationOverrideEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn <em>Xml Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Discriminator Column</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn
	 * @generated
	 */
	public EClass getXmlDiscriminatorColumn() {
		return xmlDiscriminatorColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getDiscriminatorTypeForXml <em>Discriminator Type For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator Type For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getDiscriminatorTypeForXml()
	 * @see #getXmlDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getXmlDiscriminatorColumn_DiscriminatorTypeForXml() {
		return (EAttribute) xmlDiscriminatorColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getSpecifiedLengthForXml <em>Specified Length For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Length For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getSpecifiedLengthForXml()
	 * @see #getXmlDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getXmlDiscriminatorColumn_SpecifiedLengthForXml() {
		return (EAttribute) xmlDiscriminatorColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlSecondaryTable <em>Xml Secondary Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Secondary Table</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlSecondaryTable
	 * @generated
	 */
	public EClass getXmlSecondaryTable() {
		return xmlSecondaryTableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn <em>Xml Primary Key Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Primary Key Join Column</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn
	 * @generated
	 */
	public EClass getXmlPrimaryKeyJoinColumn() {
		return xmlPrimaryKeyJoinColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn#getSpecifiedReferencedColumnNameForXml <em>Specified Referenced Column Name For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Referenced Column Name For Xml</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn#getSpecifiedReferencedColumnNameForXml()
	 * @see #getXmlPrimaryKeyJoinColumn()
	 * @generated
	 */
	public EAttribute getXmlPrimaryKeyJoinColumn_SpecifiedReferencedColumnNameForXml() {
		return (EAttribute) xmlPrimaryKeyJoinColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlGeneratedValue <em>Xml Generated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Generated Value</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlGeneratedValue
	 * @generated
	 */
	public EClass getXmlGeneratedValue() {
		return xmlGeneratedValueEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlGenerator <em>Xml Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlGenerator
	 * @generated
	 */
	public EClass getXmlGenerator() {
		return xmlGeneratorEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlSequenceGenerator <em>Xml Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Sequence Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlSequenceGenerator
	 * @generated
	 */
	public EClass getXmlSequenceGenerator() {
		return xmlSequenceGeneratorEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlTableGenerator <em>Xml Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Table Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTableGenerator
	 * @generated
	 */
	public EClass getXmlTableGenerator() {
		return xmlTableGeneratorEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlQuery <em>Abstract Xml Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Query</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlQuery
	 * @generated
	 */
	public EClass getAbstractXmlQuery() {
		return abstractXmlQueryEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlNamedQuery <em>Xml Named Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Query</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlNamedQuery
	 * @generated
	 */
	public EClass getXmlNamedQuery() {
		return xmlNamedQueryEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlNamedNativeQuery <em>Xml Named Native Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Native Query</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlNamedNativeQuery
	 * @generated
	 */
	public EClass getXmlNamedNativeQuery() {
		return xmlNamedNativeQueryEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlQueryHint <em>Xml Query Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Query Hint</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlQueryHint
	 * @generated
	 */
	public EClass getXmlQueryHint() {
		return xmlQueryHintEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlUniqueConstraint <em>Xml Unique Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Unique Constraint</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlUniqueConstraint
	 * @generated
	 */
	public EClass getXmlUniqueConstraint() {
		return xmlUniqueConstraintEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlCascade <em>Xml Cascade</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Cascade</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlCascade
	 * @generated
	 */
	public EClass getXmlCascade() {
		return xmlCascadeEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlIdClass <em>Xml Id Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id Class</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlIdClass
	 * @generated
	 */
	public EClass getXmlIdClass() {
		return xmlIdClassEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlIdClass#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlIdClass#getValue()
	 * @see #getXmlIdClass()
	 * @generated
	 */
	public EAttribute getXmlIdClass_Value() {
		return (EAttribute) xmlIdClassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlInheritance <em>Xml Inheritance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Inheritance</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlInheritance
	 * @generated
	 */
	public EClass getXmlInheritance() {
		return xmlInheritanceEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlInheritance#getStrategy <em>Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strategy</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlInheritance#getStrategy()
	 * @see #getXmlInheritance()
	 * @generated
	 */
	public EAttribute getXmlInheritance_Strategy() {
		return (EAttribute) xmlInheritanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.orm.XmlMapKey <em>Xml Map Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Map Key</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMapKey
	 * @generated
	 */
	public EClass getXmlMapKey() {
		return xmlMapKeyEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.orm.XmlMapKey#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMapKey#getName()
	 * @see #getXmlMapKey()
	 * @generated
	 */
	public EAttribute getXmlMapKey_Name() {
		return (EAttribute) xmlMapKeyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public OrmFactory getOrmFactory() {
		return (OrmFactory) getEFactoryInstance();
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
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;
		// Create classes and their features
		xmlRootContentNodeEClass = createEClass(XML_ROOT_CONTENT_NODE);
		createEReference(xmlRootContentNodeEClass, XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS);
		entityMappingsInternalEClass = createEClass(ENTITY_MAPPINGS_INTERNAL);
		createEReference(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__ROOT);
		createEAttribute(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__VERSION);
		createEAttribute(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__DESCRIPTION);
		createEReference(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_INTERNAL);
		createEAttribute(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__PACKAGE_INTERNAL);
		createEAttribute(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__DEFAULT_SCHEMA);
		createEAttribute(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__SPECIFIED_SCHEMA);
		createEAttribute(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__SCHEMA);
		createEAttribute(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__DEFAULT_CATALOG);
		createEAttribute(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__SPECIFIED_CATALOG);
		createEAttribute(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__CATALOG);
		createEAttribute(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__DEFAULT_ACCESS);
		createEAttribute(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__SPECIFIED_ACCESS);
		createEAttribute(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__ACCESS);
		createEReference(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__TYPE_MAPPINGS);
		createEReference(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__PERSISTENT_TYPES);
		createEReference(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__SEQUENCE_GENERATORS);
		createEReference(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__TABLE_GENERATORS);
		createEReference(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__NAMED_QUERIES);
		createEReference(entityMappingsInternalEClass, ENTITY_MAPPINGS_INTERNAL__NAMED_NATIVE_QUERIES);
		entityMappingsEClass = createEClass(ENTITY_MAPPINGS);
		createEReference(entityMappingsEClass, ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA);
		createEAttribute(entityMappingsEClass, ENTITY_MAPPINGS__PACKAGE);
		entityMappingsForXmlEClass = createEClass(ENTITY_MAPPINGS_FOR_XML);
		createEReference(entityMappingsForXmlEClass, ENTITY_MAPPINGS_FOR_XML__PERSISTENCE_UNIT_METADATA_FOR_XML);
		createEAttribute(entityMappingsForXmlEClass, ENTITY_MAPPINGS_FOR_XML__PACKAGE_FOR_XML);
		xmlTypeMappingEClass = createEClass(XML_TYPE_MAPPING);
		createEAttribute(xmlTypeMappingEClass, XML_TYPE_MAPPING__DEFAULT_ACCESS);
		createEAttribute(xmlTypeMappingEClass, XML_TYPE_MAPPING__SPECIFIED_ACCESS);
		createEAttribute(xmlTypeMappingEClass, XML_TYPE_MAPPING__ACCESS);
		createEAttribute(xmlTypeMappingEClass, XML_TYPE_MAPPING__METADATA_COMPLETE);
		createEReference(xmlTypeMappingEClass, XML_TYPE_MAPPING__PERSISTENT_TYPE);
		xmlPersistentTypeEClass = createEClass(XML_PERSISTENT_TYPE);
		createEAttribute(xmlPersistentTypeEClass, XML_PERSISTENT_TYPE__CLASS);
		createEReference(xmlPersistentTypeEClass, XML_PERSISTENT_TYPE__ATTRIBUTE_MAPPINGS);
		createEReference(xmlPersistentTypeEClass, XML_PERSISTENT_TYPE__SPECIFIED_ATTRIBUTE_MAPPINGS);
		createEReference(xmlPersistentTypeEClass, XML_PERSISTENT_TYPE__VIRTUAL_ATTRIBUTE_MAPPINGS);
		createEReference(xmlPersistentTypeEClass, XML_PERSISTENT_TYPE__PERSISTENT_ATTRIBUTES);
		createEReference(xmlPersistentTypeEClass, XML_PERSISTENT_TYPE__SPECIFIED_PERSISTENT_ATTRIBUTES);
		createEReference(xmlPersistentTypeEClass, XML_PERSISTENT_TYPE__VIRTUAL_PERSISTENT_ATTRIBUTES);
		xmlMappedSuperclassEClass = createEClass(XML_MAPPED_SUPERCLASS);
		createEReference(xmlMappedSuperclassEClass, XML_MAPPED_SUPERCLASS__ID_CLASS_FOR_XML);
		xmlEntityInternalEClass = createEClass(XML_ENTITY_INTERNAL);
		xmlEntityForXmlEClass = createEClass(XML_ENTITY_FOR_XML);
		createEReference(xmlEntityForXmlEClass, XML_ENTITY_FOR_XML__TABLE_FOR_XML);
		createEReference(xmlEntityForXmlEClass, XML_ENTITY_FOR_XML__DISCRIMINATOR_COLUMN_FOR_XML);
		createEReference(xmlEntityForXmlEClass, XML_ENTITY_FOR_XML__ID_CLASS_FOR_XML);
		createEReference(xmlEntityForXmlEClass, XML_ENTITY_FOR_XML__INHERITANCE_FOR_XML);
		xmlEntityEClass = createEClass(XML_ENTITY);
		createEReference(xmlEntityEClass, XML_ENTITY__SECONDARY_TABLES);
		createEReference(xmlEntityEClass, XML_ENTITY__VIRTUAL_SECONDARY_TABLES);
		xmlEmbeddableEClass = createEClass(XML_EMBEDDABLE);
		xmlAttributeMappingEClass = createEClass(XML_ATTRIBUTE_MAPPING);
		createEReference(xmlAttributeMappingEClass, XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE);
		xmlNullAttributeMappingEClass = createEClass(XML_NULL_ATTRIBUTE_MAPPING);
		xmlBasicEClass = createEClass(XML_BASIC);
		xmlIdEClass = createEClass(XML_ID);
		xmlTransientEClass = createEClass(XML_TRANSIENT);
		xmlEmbeddedEClass = createEClass(XML_EMBEDDED);
		xmlEmbeddedIdEClass = createEClass(XML_EMBEDDED_ID);
		xmlVersionEClass = createEClass(XML_VERSION);
		xmlMultiRelationshipMappingInternalEClass = createEClass(XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL);
		xmlMultiRelationshipMappingForXmlEClass = createEClass(XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML);
		createEReference(xmlMultiRelationshipMappingForXmlEClass, XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML__JOIN_TABLE_FOR_XML);
		createEReference(xmlMultiRelationshipMappingForXmlEClass, XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML__MAP_KEY_FOR_XML);
		xmlMultiRelationshipMappingEClass = createEClass(XML_MULTI_RELATIONSHIP_MAPPING);
		xmlOneToManyEClass = createEClass(XML_ONE_TO_MANY);
		xmlManyToManyEClass = createEClass(XML_MANY_TO_MANY);
		xmlPersistentAttributeEClass = createEClass(XML_PERSISTENT_ATTRIBUTE);
		createEAttribute(xmlPersistentAttributeEClass, XML_PERSISTENT_ATTRIBUTE__NAME);
		persistenceUnitMetadataInternalEClass = createEClass(PERSISTENCE_UNIT_METADATA_INTERNAL);
		createEAttribute(persistenceUnitMetadataInternalEClass, PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_INTERNAL);
		createEReference(persistenceUnitMetadataInternalEClass, PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_INTERNAL);
		persistenceUnitMetadataEClass = createEClass(PERSISTENCE_UNIT_METADATA);
		createEAttribute(persistenceUnitMetadataEClass, PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE);
		createEReference(persistenceUnitMetadataEClass, PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS);
		persistenceUnitMetadataForXmlEClass = createEClass(PERSISTENCE_UNIT_METADATA_FOR_XML);
		createEAttribute(persistenceUnitMetadataForXmlEClass, PERSISTENCE_UNIT_METADATA_FOR_XML__XML_MAPPING_METADATA_COMPLETE_FOR_XML);
		createEReference(persistenceUnitMetadataForXmlEClass, PERSISTENCE_UNIT_METADATA_FOR_XML__PERSISTENCE_UNIT_DEFAULTS_FOR_XML);
		persistenceUnitDefaultsInternalEClass = createEClass(PERSISTENCE_UNIT_DEFAULTS_INTERNAL);
		createEAttribute(persistenceUnitDefaultsInternalEClass, PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_INTERNAL);
		createEAttribute(persistenceUnitDefaultsInternalEClass, PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_INTERNAL);
		createEAttribute(persistenceUnitDefaultsInternalEClass, PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_INTERNAL);
		createEAttribute(persistenceUnitDefaultsInternalEClass, PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_INTERNAL);
		persistenceUnitDefaultsEClass = createEClass(PERSISTENCE_UNIT_DEFAULTS);
		createEAttribute(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__SCHEMA);
		createEAttribute(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__CATALOG);
		createEAttribute(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__ACCESS);
		createEAttribute(persistenceUnitDefaultsEClass, PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST);
		persistenceUnitDefaultsForXmlEClass = createEClass(PERSISTENCE_UNIT_DEFAULTS_FOR_XML);
		createEAttribute(persistenceUnitDefaultsForXmlEClass, PERSISTENCE_UNIT_DEFAULTS_FOR_XML__SCHEMA_FOR_XML);
		createEAttribute(persistenceUnitDefaultsForXmlEClass, PERSISTENCE_UNIT_DEFAULTS_FOR_XML__CATALOG_FOR_XML);
		createEAttribute(persistenceUnitDefaultsForXmlEClass, PERSISTENCE_UNIT_DEFAULTS_FOR_XML__ACCESS_FOR_XML);
		createEAttribute(persistenceUnitDefaultsForXmlEClass, PERSISTENCE_UNIT_DEFAULTS_FOR_XML__CASCADE_PERSIST_FOR_XML);
		xmlTableEClass = createEClass(XML_TABLE);
		abstractXmlNamedColumnEClass = createEClass(ABSTRACT_XML_NAMED_COLUMN);
		createEAttribute(abstractXmlNamedColumnEClass, ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME_FOR_XML);
		createEAttribute(abstractXmlNamedColumnEClass, ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION_FOR_XML);
		abstractXmlColumnEClass = createEClass(ABSTRACT_XML_COLUMN);
		createEAttribute(abstractXmlColumnEClass, ABSTRACT_XML_COLUMN__UNIQUE_FOR_XML);
		createEAttribute(abstractXmlColumnEClass, ABSTRACT_XML_COLUMN__NULLABLE_FOR_XML);
		createEAttribute(abstractXmlColumnEClass, ABSTRACT_XML_COLUMN__INSERTABLE_FOR_XML);
		createEAttribute(abstractXmlColumnEClass, ABSTRACT_XML_COLUMN__UPDATABLE_FOR_XML);
		createEAttribute(abstractXmlColumnEClass, ABSTRACT_XML_COLUMN__SPECIFIED_TABLE_FOR_XML);
		xmlColumnEClass = createEClass(XML_COLUMN);
		createEAttribute(xmlColumnEClass, XML_COLUMN__LENGTH_FOR_XML);
		createEAttribute(xmlColumnEClass, XML_COLUMN__PRECISION_FOR_XML);
		createEAttribute(xmlColumnEClass, XML_COLUMN__SCALE_FOR_XML);
		xmlJoinColumnEClass = createEClass(XML_JOIN_COLUMN);
		createEAttribute(xmlJoinColumnEClass, XML_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML);
		iXmlColumnMappingEClass = createEClass(IXML_COLUMN_MAPPING);
		createEReference(iXmlColumnMappingEClass, IXML_COLUMN_MAPPING__COLUMN_FOR_XML);
		xmlManyToOneEClass = createEClass(XML_MANY_TO_ONE);
		xmlOneToOneEClass = createEClass(XML_ONE_TO_ONE);
		xmlSingleRelationshipMappingEClass = createEClass(XML_SINGLE_RELATIONSHIP_MAPPING);
		xmlRelationshipMappingEClass = createEClass(XML_RELATIONSHIP_MAPPING);
		xmlJoinTableEClass = createEClass(XML_JOIN_TABLE);
		abstractXmlTableEClass = createEClass(ABSTRACT_XML_TABLE);
		createEAttribute(abstractXmlTableEClass, ABSTRACT_XML_TABLE__SPECIFIED_NAME_FOR_XML);
		createEAttribute(abstractXmlTableEClass, ABSTRACT_XML_TABLE__SPECIFIED_CATALOG_FOR_XML);
		createEAttribute(abstractXmlTableEClass, ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA_FOR_XML);
		xmlOverrideEClass = createEClass(XML_OVERRIDE);
		xmlAttributeOverrideEClass = createEClass(XML_ATTRIBUTE_OVERRIDE);
		xmlAssociationOverrideEClass = createEClass(XML_ASSOCIATION_OVERRIDE);
		xmlDiscriminatorColumnEClass = createEClass(XML_DISCRIMINATOR_COLUMN);
		createEAttribute(xmlDiscriminatorColumnEClass, XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE_FOR_XML);
		createEAttribute(xmlDiscriminatorColumnEClass, XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH_FOR_XML);
		xmlSecondaryTableEClass = createEClass(XML_SECONDARY_TABLE);
		xmlPrimaryKeyJoinColumnEClass = createEClass(XML_PRIMARY_KEY_JOIN_COLUMN);
		createEAttribute(xmlPrimaryKeyJoinColumnEClass, XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML);
		xmlGeneratedValueEClass = createEClass(XML_GENERATED_VALUE);
		xmlGeneratorEClass = createEClass(XML_GENERATOR);
		xmlSequenceGeneratorEClass = createEClass(XML_SEQUENCE_GENERATOR);
		xmlTableGeneratorEClass = createEClass(XML_TABLE_GENERATOR);
		abstractXmlQueryEClass = createEClass(ABSTRACT_XML_QUERY);
		xmlNamedQueryEClass = createEClass(XML_NAMED_QUERY);
		xmlNamedNativeQueryEClass = createEClass(XML_NAMED_NATIVE_QUERY);
		xmlQueryHintEClass = createEClass(XML_QUERY_HINT);
		xmlUniqueConstraintEClass = createEClass(XML_UNIQUE_CONSTRAINT);
		xmlCascadeEClass = createEClass(XML_CASCADE);
		xmlIdClassEClass = createEClass(XML_ID_CLASS);
		createEAttribute(xmlIdClassEClass, XML_ID_CLASS__VALUE);
		xmlInheritanceEClass = createEClass(XML_INHERITANCE);
		createEAttribute(xmlInheritanceEClass, XML_INHERITANCE__STRATEGY);
		xmlMapKeyEClass = createEClass(XML_MAP_KEY);
		createEAttribute(xmlMapKeyEClass, XML_MAP_KEY__NAME);
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
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;
		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);
		// Obtain other dependent packages
		JpaCorePackage theJpaCorePackage = (JpaCorePackage) EPackage.Registry.INSTANCE.getEPackage(JpaCorePackage.eNS_URI);
		JpaCoreMappingsPackage theJpaCoreMappingsPackage = (JpaCoreMappingsPackage) EPackage.Registry.INSTANCE.getEPackage(JpaCoreMappingsPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		// Create type parameters
		// Set bounds for type parameters
		// Add supertypes to classes
		xmlRootContentNodeEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		xmlRootContentNodeEClass.getESuperTypes().add(theJpaCorePackage.getIJpaRootContentNode());
		entityMappingsInternalEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		entityMappingsInternalEClass.getESuperTypes().add(theJpaCorePackage.getIJpaContentNode());
		entityMappingsInternalEClass.getESuperTypes().add(this.getEntityMappingsForXml());
		entityMappingsInternalEClass.getESuperTypes().add(this.getEntityMappings());
		xmlTypeMappingEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		xmlTypeMappingEClass.getESuperTypes().add(theJpaCorePackage.getITypeMapping());
		xmlPersistentTypeEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		xmlPersistentTypeEClass.getESuperTypes().add(theJpaCorePackage.getIPersistentType());
		xmlMappedSuperclassEClass.getESuperTypes().add(this.getXmlTypeMapping());
		xmlMappedSuperclassEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIMappedSuperclass());
		xmlEntityInternalEClass.getESuperTypes().add(this.getXmlTypeMapping());
		xmlEntityInternalEClass.getESuperTypes().add(this.getXmlEntityForXml());
		xmlEntityInternalEClass.getESuperTypes().add(this.getXmlEntity());
		xmlEntityEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIEntity());
		xmlEmbeddableEClass.getESuperTypes().add(this.getXmlTypeMapping());
		xmlEmbeddableEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIEmbeddable());
		xmlAttributeMappingEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		xmlAttributeMappingEClass.getESuperTypes().add(theJpaCorePackage.getIAttributeMapping());
		xmlNullAttributeMappingEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlBasicEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlBasicEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIBasic());
		xmlBasicEClass.getESuperTypes().add(this.getIXmlColumnMapping());
		xmlIdEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlIdEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIId());
		xmlIdEClass.getESuperTypes().add(this.getIXmlColumnMapping());
		xmlTransientEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlTransientEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getITransient());
		xmlEmbeddedEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlEmbeddedEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIEmbedded());
		xmlEmbeddedIdEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlEmbeddedIdEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIEmbeddedId());
		xmlVersionEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlVersionEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIVersion());
		xmlVersionEClass.getESuperTypes().add(this.getIXmlColumnMapping());
		xmlMultiRelationshipMappingInternalEClass.getESuperTypes().add(this.getXmlRelationshipMapping());
		xmlMultiRelationshipMappingInternalEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIMultiRelationshipMapping());
		xmlMultiRelationshipMappingInternalEClass.getESuperTypes().add(this.getXmlMultiRelationshipMappingForXml());
		xmlMultiRelationshipMappingInternalEClass.getESuperTypes().add(this.getXmlMultiRelationshipMapping());
		xmlMultiRelationshipMappingEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIMultiRelationshipMapping());
		xmlOneToManyEClass.getESuperTypes().add(this.getXmlMultiRelationshipMappingInternal());
		xmlOneToManyEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIOneToMany());
		xmlManyToManyEClass.getESuperTypes().add(this.getXmlMultiRelationshipMappingInternal());
		xmlManyToManyEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIManyToMany());
		xmlPersistentAttributeEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		xmlPersistentAttributeEClass.getESuperTypes().add(theJpaCorePackage.getIPersistentAttribute());
		persistenceUnitMetadataInternalEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		persistenceUnitMetadataInternalEClass.getESuperTypes().add(this.getPersistenceUnitMetadataForXml());
		persistenceUnitMetadataInternalEClass.getESuperTypes().add(this.getPersistenceUnitMetadata());
		persistenceUnitMetadataEClass.getESuperTypes().add(theJpaCorePackage.getIXmlEObject());
		persistenceUnitMetadataForXmlEClass.getESuperTypes().add(theJpaCorePackage.getIXmlEObject());
		persistenceUnitDefaultsInternalEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		persistenceUnitDefaultsInternalEClass.getESuperTypes().add(this.getPersistenceUnitDefaults());
		persistenceUnitDefaultsInternalEClass.getESuperTypes().add(this.getPersistenceUnitDefaultsForXml());
		persistenceUnitDefaultsEClass.getESuperTypes().add(theJpaCorePackage.getIXmlEObject());
		persistenceUnitDefaultsForXmlEClass.getESuperTypes().add(theJpaCorePackage.getIXmlEObject());
		xmlTableEClass.getESuperTypes().add(this.getAbstractXmlTable());
		xmlTableEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getITable());
		abstractXmlNamedColumnEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		abstractXmlNamedColumnEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getINamedColumn());
		abstractXmlColumnEClass.getESuperTypes().add(this.getAbstractXmlNamedColumn());
		abstractXmlColumnEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIAbstractColumn());
		xmlColumnEClass.getESuperTypes().add(this.getAbstractXmlColumn());
		xmlColumnEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIColumn());
		xmlJoinColumnEClass.getESuperTypes().add(this.getAbstractXmlColumn());
		xmlJoinColumnEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIJoinColumn());
		iXmlColumnMappingEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIColumnMapping());
		xmlManyToOneEClass.getESuperTypes().add(this.getXmlSingleRelationshipMapping());
		xmlManyToOneEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIManyToOne());
		xmlOneToOneEClass.getESuperTypes().add(this.getXmlSingleRelationshipMapping());
		xmlOneToOneEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIOneToOne());
		xmlSingleRelationshipMappingEClass.getESuperTypes().add(this.getXmlRelationshipMapping());
		xmlSingleRelationshipMappingEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getISingleRelationshipMapping());
		xmlRelationshipMappingEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlRelationshipMappingEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIRelationshipMapping());
		xmlJoinTableEClass.getESuperTypes().add(this.getAbstractXmlTable());
		xmlJoinTableEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIJoinTable());
		abstractXmlTableEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		abstractXmlTableEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getITable());
		xmlOverrideEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		xmlOverrideEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIOverride());
		xmlAttributeOverrideEClass.getESuperTypes().add(this.getXmlOverride());
		xmlAttributeOverrideEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIAttributeOverride());
		xmlAttributeOverrideEClass.getESuperTypes().add(this.getIXmlColumnMapping());
		xmlAssociationOverrideEClass.getESuperTypes().add(this.getXmlOverride());
		xmlAssociationOverrideEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIAssociationOverride());
		xmlDiscriminatorColumnEClass.getESuperTypes().add(this.getAbstractXmlNamedColumn());
		xmlDiscriminatorColumnEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIDiscriminatorColumn());
		xmlSecondaryTableEClass.getESuperTypes().add(this.getAbstractXmlTable());
		xmlSecondaryTableEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getISecondaryTable());
		xmlPrimaryKeyJoinColumnEClass.getESuperTypes().add(this.getAbstractXmlNamedColumn());
		xmlPrimaryKeyJoinColumnEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIPrimaryKeyJoinColumn());
		xmlGeneratedValueEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		xmlGeneratedValueEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIGeneratedValue());
		xmlGeneratorEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		xmlGeneratorEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIGenerator());
		xmlSequenceGeneratorEClass.getESuperTypes().add(this.getXmlGenerator());
		xmlSequenceGeneratorEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getISequenceGenerator());
		xmlTableGeneratorEClass.getESuperTypes().add(this.getXmlGenerator());
		xmlTableGeneratorEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getITableGenerator());
		abstractXmlQueryEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		abstractXmlQueryEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIQuery());
		xmlNamedQueryEClass.getESuperTypes().add(this.getAbstractXmlQuery());
		xmlNamedQueryEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getINamedQuery());
		xmlNamedNativeQueryEClass.getESuperTypes().add(this.getAbstractXmlQuery());
		xmlNamedNativeQueryEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getINamedNativeQuery());
		xmlQueryHintEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		xmlQueryHintEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIQueryHint());
		xmlUniqueConstraintEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		xmlUniqueConstraintEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIUniqueConstraint());
		xmlCascadeEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		xmlCascadeEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getICascade());
		xmlIdClassEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		xmlInheritanceEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		xmlMapKeyEClass.getESuperTypes().add(theJpaCorePackage.getXmlEObject());
		// Initialize classes and features; add operations and parameters
		initEClass(xmlRootContentNodeEClass, XmlRootContentNode.class, "XmlRootContentNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlRootContentNode_EntityMappings(), this.getEntityMappingsInternal(), this.getEntityMappingsInternal_Root(), "entityMappings", null, 1, 1, XmlRootContentNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEClass(entityMappingsInternalEClass, EntityMappingsInternal.class, "EntityMappingsInternal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntityMappingsInternal_Root(), this.getXmlRootContentNode(), this.getXmlRootContentNode_EntityMappings(), "root", null, 1, 1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getEntityMappingsInternal_Version(), ecorePackage.getEString(), "version", null, 0, 1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappingsInternal_Description(), ecorePackage.getEString(), "description", null, 0, 1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappingsInternal_PersistenceUnitMetadataInternal(), this.getPersistenceUnitMetadataInternal(), null, "persistenceUnitMetadataInternal", null, 1, 1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappingsInternal_PackageInternal(), ecorePackage.getEString(), "packageInternal", null, 0, 1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappingsInternal_DefaultSchema(), ecorePackage.getEString(), "defaultSchema", null, 0, 1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappingsInternal_SpecifiedSchema(), ecorePackage.getEString(), "specifiedSchema", null, 0, 1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappingsInternal_Schema(), ecorePackage.getEString(), "schema", null, 0, 1, EntityMappingsInternal.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappingsInternal_DefaultCatalog(), ecorePackage.getEString(), "defaultCatalog", null, 0, 1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappingsInternal_SpecifiedCatalog(), ecorePackage.getEString(), "specifiedCatalog", null, 0, 1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappingsInternal_Catalog(), ecorePackage.getEString(), "catalog", null, 0, 1, EntityMappingsInternal.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappingsInternal_DefaultAccess(), theJpaCorePackage.getAccessType(), "defaultAccess", null, 0, 1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappingsInternal_SpecifiedAccess(), theJpaCorePackage.getAccessType(), "specifiedAccess", null, 0, 1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappingsInternal_Access(), theJpaCorePackage.getAccessType(), "access", null, 0, 1, EntityMappingsInternal.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappingsInternal_TypeMappings(), this.getXmlTypeMapping(), null, "typeMappings", null, 0, -1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappingsInternal_PersistentTypes(), this.getXmlPersistentType(), null, "persistentTypes", null, 0, -1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappingsInternal_SequenceGenerators(), this.getXmlSequenceGenerator(), null, "sequenceGenerators", null, 0, -1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappingsInternal_TableGenerators(), this.getXmlTableGenerator(), null, "tableGenerators", null, 0, -1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappingsInternal_NamedQueries(), this.getXmlNamedQuery(), null, "namedQueries", null, 0, -1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMappingsInternal_NamedNativeQueries(), this.getXmlNamedNativeQuery(), null, "namedNativeQueries", null, 0, -1, EntityMappingsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(entityMappingsEClass, EntityMappings.class, "EntityMappings", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntityMappings_PersistenceUnitMetadata(), this.getPersistenceUnitMetadata(), null, "persistenceUnitMetadata", null, 0, 1, EntityMappings.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappings_Package(), ecorePackage.getEString(), "package", null, 0, 1, EntityMappings.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(entityMappingsForXmlEClass, EntityMappingsForXml.class, "EntityMappingsForXml", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntityMappingsForXml_PersistenceUnitMetadataForXml(), this.getPersistenceUnitMetadataForXml(), null, "persistenceUnitMetadataForXml", null, 0, 1, EntityMappingsForXml.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMappingsForXml_PackageForXml(), ecorePackage.getEString(), "packageForXml", null, 0, 1, EntityMappingsForXml.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlTypeMappingEClass, XmlTypeMapping.class, "XmlTypeMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlTypeMapping_DefaultAccess(), theJpaCorePackage.getAccessType(), "defaultAccess", null, 0, 1, XmlTypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTypeMapping_SpecifiedAccess(), theJpaCorePackage.getAccessType(), "specifiedAccess", null, 0, 1, XmlTypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTypeMapping_Access(), theJpaCorePackage.getAccessType(), "access", null, 0, 1, XmlTypeMapping.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTypeMapping_MetadataComplete(), theJpaCoreMappingsPackage.getDefaultFalseBoolean(), "metadataComplete", null, 0, 1, XmlTypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlTypeMapping_PersistentType(), this.getXmlPersistentType(), null, "persistentType", null, 1, 1, XmlTypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlPersistentTypeEClass, XmlPersistentType.class, "XmlPersistentType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPersistentType_Class(), theEcorePackage.getEString(), "class", null, 0, 1, XmlPersistentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPersistentType_AttributeMappings(), this.getXmlAttributeMapping(), null, "attributeMappings", null, 0, -1, XmlPersistentType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPersistentType_SpecifiedAttributeMappings(), this.getXmlAttributeMapping(), null, "specifiedAttributeMappings", null, 0, -1, XmlPersistentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPersistentType_VirtualAttributeMappings(), this.getXmlAttributeMapping(), null, "virtualAttributeMappings", null, 0, -1, XmlPersistentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPersistentType_PersistentAttributes(), this.getXmlPersistentAttribute(), null, "persistentAttributes", null, 0, -1, XmlPersistentType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPersistentType_SpecifiedPersistentAttributes(), this.getXmlPersistentAttribute(), null, "specifiedPersistentAttributes", null, 0, -1, XmlPersistentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPersistentType_VirtualPersistentAttributes(), this.getXmlPersistentAttribute(), null, "virtualPersistentAttributes", null, 0, -1, XmlPersistentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlMappedSuperclassEClass, XmlMappedSuperclass.class, "XmlMappedSuperclass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlMappedSuperclass_IdClassForXml(), this.getXmlIdClass(), null, "idClassForXml", null, 0, 1, XmlMappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlEntityInternalEClass, XmlEntityInternal.class, "XmlEntityInternal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlEntityForXmlEClass, XmlEntityForXml.class, "XmlEntityForXml", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntityForXml_TableForXml(), this.getXmlTable(), null, "tableForXml", null, 0, 1, XmlEntityForXml.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityForXml_DiscriminatorColumnForXml(), this.getXmlDiscriminatorColumn(), null, "discriminatorColumnForXml", null, 0, 1, XmlEntityForXml.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityForXml_IdClassForXml(), this.getXmlIdClass(), null, "idClassForXml", null, 0, 1, XmlEntityForXml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityForXml_InheritanceForXml(), this.getXmlInheritance(), null, "inheritanceForXml", null, 0, 1, XmlEntityForXml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlEntityEClass, XmlEntity.class, "XmlEntity", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntity_SecondaryTables(), theJpaCoreMappingsPackage.getISecondaryTable(), null, "secondaryTables", null, 0, -1, XmlEntity.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_VirtualSecondaryTables(), theJpaCoreMappingsPackage.getISecondaryTable(), null, "virtualSecondaryTables", null, 0, -1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlEmbeddableEClass, XmlEmbeddable.class, "XmlEmbeddable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlAttributeMappingEClass, XmlAttributeMapping.class, "XmlAttributeMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlAttributeMapping_PersistentAttribute(), this.getXmlPersistentAttribute(), null, "persistentAttribute", null, 1, 1, XmlAttributeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlNullAttributeMappingEClass, XmlNullAttributeMapping.class, "XmlNullAttributeMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlBasicEClass, XmlBasic.class, "XmlBasic", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlIdEClass, XmlId.class, "XmlId", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlTransientEClass, XmlTransient.class, "XmlTransient", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlEmbeddedEClass, XmlEmbedded.class, "XmlEmbedded", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlEmbeddedIdEClass, XmlEmbeddedId.class, "XmlEmbeddedId", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlVersionEClass, XmlVersion.class, "XmlVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlMultiRelationshipMappingInternalEClass, XmlMultiRelationshipMappingInternal.class, "XmlMultiRelationshipMappingInternal", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlMultiRelationshipMappingForXmlEClass, XmlMultiRelationshipMappingForXml.class, "XmlMultiRelationshipMappingForXml", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlMultiRelationshipMappingForXml_JoinTableForXml(), this.getXmlJoinTable(), null, "joinTableForXml", null, 0, 1, XmlMultiRelationshipMappingForXml.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlMultiRelationshipMappingForXml_MapKeyForXml(), this.getXmlMapKey(), null, "mapKeyForXml", null, 0, 1, XmlMultiRelationshipMappingForXml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlMultiRelationshipMappingEClass, XmlMultiRelationshipMapping.class, "XmlMultiRelationshipMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlOneToManyEClass, XmlOneToMany.class, "XmlOneToMany", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlManyToManyEClass, XmlManyToMany.class, "XmlManyToMany", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlPersistentAttributeEClass, XmlPersistentAttribute.class, "XmlPersistentAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPersistentAttribute_Name(), ecorePackage.getEString(), "name", null, 0, 1, XmlPersistentAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(persistenceUnitMetadataInternalEClass, PersistenceUnitMetadataInternal.class, "PersistenceUnitMetadataInternal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPersistenceUnitMetadataInternal_XmlMappingMetadataCompleteInternal(), ecorePackage.getEBoolean(), "xmlMappingMetadataCompleteInternal", null, 0, 1, PersistenceUnitMetadataInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPersistenceUnitMetadataInternal_PersistenceUnitDefaultsInternal(), this.getPersistenceUnitDefaultsInternal(), null, "persistenceUnitDefaultsInternal", null, 1, 1, PersistenceUnitMetadataInternal.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(persistenceUnitMetadataEClass, PersistenceUnitMetadata.class, "PersistenceUnitMetadata", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPersistenceUnitMetadata_XmlMappingMetadataComplete(), ecorePackage.getEBoolean(), "xmlMappingMetadataComplete", null, 0, 1, PersistenceUnitMetadata.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPersistenceUnitMetadata_PersistenceUnitDefaults(), this.getPersistenceUnitDefaults(), null, "persistenceUnitDefaults", null, 0, 1, PersistenceUnitMetadata.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(persistenceUnitMetadataForXmlEClass, PersistenceUnitMetadataForXml.class, "PersistenceUnitMetadataForXml", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPersistenceUnitMetadataForXml_XmlMappingMetadataCompleteForXml(), ecorePackage.getEBoolean(), "xmlMappingMetadataCompleteForXml", null, 0, 1, PersistenceUnitMetadataForXml.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPersistenceUnitMetadataForXml_PersistenceUnitDefaultsForXml(), this.getPersistenceUnitDefaultsForXml(), null, "persistenceUnitDefaultsForXml", null, 0, 1, PersistenceUnitMetadataForXml.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(persistenceUnitDefaultsInternalEClass, PersistenceUnitDefaultsInternal.class, "PersistenceUnitDefaultsInternal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPersistenceUnitDefaultsInternal_SchemaInternal(), ecorePackage.getEString(), "schemaInternal", null, 0, 1, PersistenceUnitDefaultsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaultsInternal_CatalogInternal(), ecorePackage.getEString(), "catalogInternal", null, 0, 1, PersistenceUnitDefaultsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaultsInternal_AccessInternal(), theJpaCorePackage.getAccessType(), "accessInternal", null, 0, 1, PersistenceUnitDefaultsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaultsInternal_CascadePersistInternal(), ecorePackage.getEBoolean(), "cascadePersistInternal", null, 0, 1, PersistenceUnitDefaultsInternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(persistenceUnitDefaultsEClass, PersistenceUnitDefaults.class, "PersistenceUnitDefaults", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPersistenceUnitDefaults_Schema(), ecorePackage.getEString(), "schema", null, 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaults_Catalog(), ecorePackage.getEString(), "catalog", null, 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaults_Access(), theJpaCorePackage.getAccessType(), "access", null, 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaults_CascadePersist(), ecorePackage.getEBoolean(), "cascadePersist", null, 0, 1, PersistenceUnitDefaults.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(persistenceUnitDefaultsForXmlEClass, PersistenceUnitDefaultsForXml.class, "PersistenceUnitDefaultsForXml", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPersistenceUnitDefaultsForXml_SchemaForXml(), ecorePackage.getEString(), "schemaForXml", null, 0, 1, PersistenceUnitDefaultsForXml.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaultsForXml_CatalogForXml(), ecorePackage.getEString(), "catalogForXml", null, 0, 1, PersistenceUnitDefaultsForXml.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaultsForXml_AccessForXml(), theJpaCorePackage.getAccessType(), "accessForXml", null, 0, 1, PersistenceUnitDefaultsForXml.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnitDefaultsForXml_CascadePersistForXml(), ecorePackage.getEBoolean(), "cascadePersistForXml", null, 0, 1, PersistenceUnitDefaultsForXml.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlTableEClass, XmlTable.class, "XmlTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(abstractXmlNamedColumnEClass, AbstractXmlNamedColumn.class, "AbstractXmlNamedColumn", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractXmlNamedColumn_SpecifiedNameForXml(), ecorePackage.getEString(), "specifiedNameForXml", null, 0, 1, AbstractXmlNamedColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlNamedColumn_ColumnDefinitionForXml(), ecorePackage.getEString(), "columnDefinitionForXml", null, 0, 1, AbstractXmlNamedColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(abstractXmlColumnEClass, AbstractXmlColumn.class, "AbstractXmlColumn", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractXmlColumn_UniqueForXml(), theJpaCoreMappingsPackage.getDefaultFalseBoolean(), "uniqueForXml", null, 0, 1, AbstractXmlColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlColumn_NullableForXml(), theJpaCoreMappingsPackage.getDefaultTrueBoolean(), "nullableForXml", null, 0, 1, AbstractXmlColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlColumn_InsertableForXml(), theJpaCoreMappingsPackage.getDefaultTrueBoolean(), "insertableForXml", null, 0, 1, AbstractXmlColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlColumn_UpdatableForXml(), theJpaCoreMappingsPackage.getDefaultTrueBoolean(), "updatableForXml", null, 0, 1, AbstractXmlColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlColumn_SpecifiedTableForXml(), ecorePackage.getEString(), "specifiedTableForXml", null, 0, 1, AbstractXmlColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlColumnEClass, XmlColumn.class, "XmlColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlColumn_LengthForXml(), ecorePackage.getEInt(), "lengthForXml", "255", 0, 1, XmlColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlColumn_PrecisionForXml(), ecorePackage.getEInt(), "precisionForXml", null, 0, 1, XmlColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlColumn_ScaleForXml(), ecorePackage.getEInt(), "scaleForXml", null, 0, 1, XmlColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlJoinColumnEClass, XmlJoinColumn.class, "XmlJoinColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlJoinColumn_SpecifiedReferencedColumnNameForXml(), ecorePackage.getEString(), "specifiedReferencedColumnNameForXml", null, 0, 1, XmlJoinColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iXmlColumnMappingEClass, IXmlColumnMapping.class, "IXmlColumnMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIXmlColumnMapping_ColumnForXml(), this.getXmlColumn(), null, "columnForXml", null, 0, 1, IXmlColumnMapping.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		addEOperation(iXmlColumnMappingEClass, null, "makeColumnForXmlNonNull", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEOperation(iXmlColumnMappingEClass, null, "makeColumnForXmlNull", 0, 1, IS_UNIQUE, IS_ORDERED);
		initEClass(xmlManyToOneEClass, XmlManyToOne.class, "XmlManyToOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlOneToOneEClass, XmlOneToOne.class, "XmlOneToOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlSingleRelationshipMappingEClass, XmlSingleRelationshipMapping.class, "XmlSingleRelationshipMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlRelationshipMappingEClass, XmlRelationshipMapping.class, "XmlRelationshipMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlJoinTableEClass, XmlJoinTable.class, "XmlJoinTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(abstractXmlTableEClass, AbstractXmlTable.class, "AbstractXmlTable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractXmlTable_SpecifiedNameForXml(), ecorePackage.getEString(), "specifiedNameForXml", null, 0, 1, AbstractXmlTable.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlTable_SpecifiedCatalogForXml(), ecorePackage.getEString(), "specifiedCatalogForXml", null, 0, 1, AbstractXmlTable.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlTable_SpecifiedSchemaForXml(), ecorePackage.getEString(), "specifiedSchemaForXml", null, 0, 1, AbstractXmlTable.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlOverrideEClass, XmlOverride.class, "XmlOverride", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlAttributeOverrideEClass, XmlAttributeOverride.class, "XmlAttributeOverride", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlAssociationOverrideEClass, XmlAssociationOverride.class, "XmlAssociationOverride", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlDiscriminatorColumnEClass, XmlDiscriminatorColumn.class, "XmlDiscriminatorColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlDiscriminatorColumn_DiscriminatorTypeForXml(), theJpaCoreMappingsPackage.getDiscriminatorType(), "discriminatorTypeForXml", null, 0, 1, XmlDiscriminatorColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlDiscriminatorColumn_SpecifiedLengthForXml(), theEcorePackage.getEInt(), "specifiedLengthForXml", "-1", 0, 1, XmlDiscriminatorColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlSecondaryTableEClass, XmlSecondaryTable.class, "XmlSecondaryTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlPrimaryKeyJoinColumnEClass, XmlPrimaryKeyJoinColumn.class, "XmlPrimaryKeyJoinColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPrimaryKeyJoinColumn_SpecifiedReferencedColumnNameForXml(), ecorePackage.getEString(), "specifiedReferencedColumnNameForXml", null, 0, 1, XmlPrimaryKeyJoinColumn.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlGeneratedValueEClass, XmlGeneratedValue.class, "XmlGeneratedValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlGeneratorEClass, XmlGenerator.class, "XmlGenerator", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlSequenceGeneratorEClass, XmlSequenceGenerator.class, "XmlSequenceGenerator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlTableGeneratorEClass, XmlTableGenerator.class, "XmlTableGenerator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(abstractXmlQueryEClass, AbstractXmlQuery.class, "AbstractXmlQuery", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlNamedQueryEClass, XmlNamedQuery.class, "XmlNamedQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlNamedNativeQueryEClass, XmlNamedNativeQuery.class, "XmlNamedNativeQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlQueryHintEClass, XmlQueryHint.class, "XmlQueryHint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlUniqueConstraintEClass, XmlUniqueConstraint.class, "XmlUniqueConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlCascadeEClass, XmlCascade.class, "XmlCascade", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlIdClassEClass, XmlIdClass.class, "XmlIdClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlIdClass_Value(), theEcorePackage.getEString(), "value", null, 0, 1, XmlIdClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlInheritanceEClass, XmlInheritance.class, "XmlInheritance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlInheritance_Strategy(), theJpaCoreMappingsPackage.getInheritanceType(), "strategy", null, 0, 1, XmlInheritance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(xmlMapKeyEClass, XmlMapKey.class, "XmlMapKey", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlMapKey_Name(), ecorePackage.getEString(), "name", null, 0, 1, XmlMapKey.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode <em>Xml Root Content Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlRootContentNode()
		 * @generated
		 */
		public static final EClass XML_ROOT_CONTENT_NODE = eINSTANCE.getXmlRootContentNode();

		/**
		 * The meta object literal for the '<em><b>Entity Mappings</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS = eINSTANCE.getXmlRootContentNode_EntityMappings();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal <em>Entity Mappings Internal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal()
		 * @generated
		 */
		public static final EClass ENTITY_MAPPINGS_INTERNAL = eINSTANCE.getEntityMappingsInternal();

		/**
		 * The meta object literal for the '<em><b>Root</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS_INTERNAL__ROOT = eINSTANCE.getEntityMappingsInternal_Root();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS_INTERNAL__VERSION = eINSTANCE.getEntityMappingsInternal_Version();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS_INTERNAL__DESCRIPTION = eINSTANCE.getEntityMappingsInternal_Description();

		/**
		 * The meta object literal for the '<em><b>Persistence Unit Metadata Internal</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_INTERNAL = eINSTANCE.getEntityMappingsInternal_PersistenceUnitMetadataInternal();

		/**
		 * The meta object literal for the '<em><b>Package Internal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS_INTERNAL__PACKAGE_INTERNAL = eINSTANCE.getEntityMappingsInternal_PackageInternal();

		/**
		 * The meta object literal for the '<em><b>Default Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS_INTERNAL__DEFAULT_SCHEMA = eINSTANCE.getEntityMappingsInternal_DefaultSchema();

		/**
		 * The meta object literal for the '<em><b>Specified Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS_INTERNAL__SPECIFIED_SCHEMA = eINSTANCE.getEntityMappingsInternal_SpecifiedSchema();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS_INTERNAL__SCHEMA = eINSTANCE.getEntityMappingsInternal_Schema();

		/**
		 * The meta object literal for the '<em><b>Default Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS_INTERNAL__DEFAULT_CATALOG = eINSTANCE.getEntityMappingsInternal_DefaultCatalog();

		/**
		 * The meta object literal for the '<em><b>Specified Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS_INTERNAL__SPECIFIED_CATALOG = eINSTANCE.getEntityMappingsInternal_SpecifiedCatalog();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS_INTERNAL__CATALOG = eINSTANCE.getEntityMappingsInternal_Catalog();

		/**
		 * The meta object literal for the '<em><b>Default Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS_INTERNAL__DEFAULT_ACCESS = eINSTANCE.getEntityMappingsInternal_DefaultAccess();

		/**
		 * The meta object literal for the '<em><b>Specified Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS_INTERNAL__SPECIFIED_ACCESS = eINSTANCE.getEntityMappingsInternal_SpecifiedAccess();

		/**
		 * The meta object literal for the '<em><b>Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS_INTERNAL__ACCESS = eINSTANCE.getEntityMappingsInternal_Access();

		/**
		 * The meta object literal for the '<em><b>Type Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS_INTERNAL__TYPE_MAPPINGS = eINSTANCE.getEntityMappingsInternal_TypeMappings();

		/**
		 * The meta object literal for the '<em><b>Persistent Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS_INTERNAL__PERSISTENT_TYPES = eINSTANCE.getEntityMappingsInternal_PersistentTypes();

		/**
		 * The meta object literal for the '<em><b>Sequence Generators</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS_INTERNAL__SEQUENCE_GENERATORS = eINSTANCE.getEntityMappingsInternal_SequenceGenerators();

		/**
		 * The meta object literal for the '<em><b>Table Generators</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS_INTERNAL__TABLE_GENERATORS = eINSTANCE.getEntityMappingsInternal_TableGenerators();

		/**
		 * The meta object literal for the '<em><b>Named Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS_INTERNAL__NAMED_QUERIES = eINSTANCE.getEntityMappingsInternal_NamedQueries();

		/**
		 * The meta object literal for the '<em><b>Named Native Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS_INTERNAL__NAMED_NATIVE_QUERIES = eINSTANCE.getEntityMappingsInternal_NamedNativeQueries();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappings <em>Entity Mappings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappings
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappings()
		 * @generated
		 */
		public static final EClass ENTITY_MAPPINGS = eINSTANCE.getEntityMappings();

		/**
		 * The meta object literal for the '<em><b>Persistence Unit Metadata</b></em>' reference feature.
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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml <em>Entity Mappings For Xml</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsForXml()
		 * @generated
		 */
		public static final EClass ENTITY_MAPPINGS_FOR_XML = eINSTANCE.getEntityMappingsForXml();

		/**
		 * The meta object literal for the '<em><b>Persistence Unit Metadata For Xml</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_MAPPINGS_FOR_XML__PERSISTENCE_UNIT_METADATA_FOR_XML = eINSTANCE.getEntityMappingsForXml_PersistenceUnitMetadataForXml();

		/**
		 * The meta object literal for the '<em><b>Package For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_MAPPINGS_FOR_XML__PACKAGE_FOR_XML = eINSTANCE.getEntityMappingsForXml_PackageForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping <em>Xml Type Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTypeMapping()
		 * @generated
		 */
		public static final EClass XML_TYPE_MAPPING = eINSTANCE.getXmlTypeMapping();

		/**
		 * The meta object literal for the '<em><b>Default Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TYPE_MAPPING__DEFAULT_ACCESS = eINSTANCE.getXmlTypeMapping_DefaultAccess();

		/**
		 * The meta object literal for the '<em><b>Specified Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TYPE_MAPPING__SPECIFIED_ACCESS = eINSTANCE.getXmlTypeMapping_SpecifiedAccess();

		/**
		 * The meta object literal for the '<em><b>Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TYPE_MAPPING__ACCESS = eINSTANCE.getXmlTypeMapping_Access();

		/**
		 * The meta object literal for the '<em><b>Metadata Complete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TYPE_MAPPING__METADATA_COMPLETE = eINSTANCE.getXmlTypeMapping_MetadataComplete();

		/**
		 * The meta object literal for the '<em><b>Persistent Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_TYPE_MAPPING__PERSISTENT_TYPE = eINSTANCE.getXmlTypeMapping_PersistentType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType <em>Xml Persistent Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentType
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPersistentType()
		 * @generated
		 */
		public static final EClass XML_PERSISTENT_TYPE = eINSTANCE.getXmlPersistentType();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENT_TYPE__CLASS = eINSTANCE.getXmlPersistentType_Class();

		/**
		 * The meta object literal for the '<em><b>Attribute Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PERSISTENT_TYPE__ATTRIBUTE_MAPPINGS = eINSTANCE.getXmlPersistentType_AttributeMappings();

		/**
		 * The meta object literal for the '<em><b>Specified Attribute Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PERSISTENT_TYPE__SPECIFIED_ATTRIBUTE_MAPPINGS = eINSTANCE.getXmlPersistentType_SpecifiedAttributeMappings();

		/**
		 * The meta object literal for the '<em><b>Virtual Attribute Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PERSISTENT_TYPE__VIRTUAL_ATTRIBUTE_MAPPINGS = eINSTANCE.getXmlPersistentType_VirtualAttributeMappings();

		/**
		 * The meta object literal for the '<em><b>Persistent Attributes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PERSISTENT_TYPE__PERSISTENT_ATTRIBUTES = eINSTANCE.getXmlPersistentType_PersistentAttributes();

		/**
		 * The meta object literal for the '<em><b>Specified Persistent Attributes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PERSISTENT_TYPE__SPECIFIED_PERSISTENT_ATTRIBUTES = eINSTANCE.getXmlPersistentType_SpecifiedPersistentAttributes();

		/**
		 * The meta object literal for the '<em><b>Virtual Persistent Attributes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PERSISTENT_TYPE__VIRTUAL_PERSISTENT_ATTRIBUTES = eINSTANCE.getXmlPersistentType_VirtualPersistentAttributes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlMappedSuperclass
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMappedSuperclass()
		 * @generated
		 */
		public static final EClass XML_MAPPED_SUPERCLASS = eINSTANCE.getXmlMappedSuperclass();

		/**
		 * The meta object literal for the '<em><b>Id Class For Xml</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS__ID_CLASS_FOR_XML = eINSTANCE.getXmlMappedSuperclass_IdClassForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal <em>Xml Entity Internal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityInternal()
		 * @generated
		 */
		public static final EClass XML_ENTITY_INTERNAL = eINSTANCE.getXmlEntityInternal();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml <em>Xml Entity For Xml</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityForXml()
		 * @generated
		 */
		public static final EClass XML_ENTITY_FOR_XML = eINSTANCE.getXmlEntityForXml();

		/**
		 * The meta object literal for the '<em><b>Table For Xml</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_FOR_XML__TABLE_FOR_XML = eINSTANCE.getXmlEntityForXml_TableForXml();

		/**
		 * The meta object literal for the '<em><b>Discriminator Column For Xml</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_FOR_XML__DISCRIMINATOR_COLUMN_FOR_XML = eINSTANCE.getXmlEntityForXml_DiscriminatorColumnForXml();

		/**
		 * The meta object literal for the '<em><b>Id Class For Xml</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_FOR_XML__ID_CLASS_FOR_XML = eINSTANCE.getXmlEntityForXml_IdClassForXml();

		/**
		 * The meta object literal for the '<em><b>Inheritance For Xml</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_FOR_XML__INHERITANCE_FOR_XML = eINSTANCE.getXmlEntityForXml_InheritanceForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntity <em>Xml Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntity
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntity()
		 * @generated
		 */
		public static final EClass XML_ENTITY = eINSTANCE.getXmlEntity();

		/**
		 * The meta object literal for the '<em><b>Secondary Tables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__SECONDARY_TABLES = eINSTANCE.getXmlEntity_SecondaryTables();

		/**
		 * The meta object literal for the '<em><b>Virtual Secondary Tables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__VIRTUAL_SECONDARY_TABLES = eINSTANCE.getXmlEntity_VirtualSecondaryTables();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEmbeddable <em>Xml Embeddable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlEmbeddable
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEmbeddable()
		 * @generated
		 */
		public static final EClass XML_EMBEDDABLE = eINSTANCE.getXmlEmbeddable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlAttributeMapping()
		 * @generated
		 */
		public static final EClass XML_ATTRIBUTE_MAPPING = eINSTANCE.getXmlAttributeMapping();

		/**
		 * The meta object literal for the '<em><b>Persistent Attribute</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE = eINSTANCE.getXmlAttributeMapping_PersistentAttribute();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlNullAttributeMapping <em>Xml Null Attribute Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlNullAttributeMapping
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlNullAttributeMapping()
		 * @generated
		 */
		public static final EClass XML_NULL_ATTRIBUTE_MAPPING = eINSTANCE.getXmlNullAttributeMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlBasic <em>Xml Basic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlBasic
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlBasic()
		 * @generated
		 */
		public static final EClass XML_BASIC = eINSTANCE.getXmlBasic();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlId <em>Xml Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlId
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlId()
		 * @generated
		 */
		public static final EClass XML_ID = eINSTANCE.getXmlId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlTransient <em>Xml Transient</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlTransient
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTransient()
		 * @generated
		 */
		public static final EClass XML_TRANSIENT = eINSTANCE.getXmlTransient();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEmbedded <em>Xml Embedded</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlEmbedded
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEmbedded()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED = eINSTANCE.getXmlEmbedded();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlEmbeddedId
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEmbeddedId()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED_ID = eINSTANCE.getXmlEmbeddedId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlVersion <em>Xml Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlVersion
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlVersion()
		 * @generated
		 */
		public static final EClass XML_VERSION = eINSTANCE.getXmlVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal <em>Xml Multi Relationship Mapping Internal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMultiRelationshipMappingInternal()
		 * @generated
		 */
		public static final EClass XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL = eINSTANCE.getXmlMultiRelationshipMappingInternal();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml <em>Xml Multi Relationship Mapping For Xml</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMultiRelationshipMappingForXml()
		 * @generated
		 */
		public static final EClass XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML = eINSTANCE.getXmlMultiRelationshipMappingForXml();

		/**
		 * The meta object literal for the '<em><b>Join Table For Xml</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML__JOIN_TABLE_FOR_XML = eINSTANCE.getXmlMultiRelationshipMappingForXml_JoinTableForXml();

		/**
		 * The meta object literal for the '<em><b>Map Key For Xml</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML__MAP_KEY_FOR_XML = eINSTANCE.getXmlMultiRelationshipMappingForXml_MapKeyForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMapping <em>Xml Multi Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMapping
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMultiRelationshipMapping()
		 * @generated
		 */
		public static final EClass XML_MULTI_RELATIONSHIP_MAPPING = eINSTANCE.getXmlMultiRelationshipMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlOneToMany <em>Xml One To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlOneToMany
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlOneToMany()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_MANY = eINSTANCE.getXmlOneToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlManyToMany <em>Xml Many To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlManyToMany
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlManyToMany()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_MANY = eINSTANCE.getXmlManyToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute <em>Xml Persistent Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPersistentAttribute()
		 * @generated
		 */
		public static final EClass XML_PERSISTENT_ATTRIBUTE = eINSTANCE.getXmlPersistentAttribute();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENT_ATTRIBUTE__NAME = eINSTANCE.getXmlPersistentAttribute_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal <em>Persistence Unit Metadata Internal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadataInternal()
		 * @generated
		 */
		public static final EClass PERSISTENCE_UNIT_METADATA_INTERNAL = eINSTANCE.getPersistenceUnitMetadataInternal();

		/**
		 * The meta object literal for the '<em><b>Xml Mapping Metadata Complete Internal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_INTERNAL = eINSTANCE.getPersistenceUnitMetadataInternal_XmlMappingMetadataCompleteInternal();

		/**
		 * The meta object literal for the '<em><b>Persistence Unit Defaults Internal</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_INTERNAL = eINSTANCE.getPersistenceUnitMetadataInternal_PersistenceUnitDefaultsInternal();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata <em>Persistence Unit Metadata</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadata()
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
		 * The meta object literal for the '<em><b>Persistence Unit Defaults</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS = eINSTANCE.getPersistenceUnitMetadata_PersistenceUnitDefaults();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml <em>Persistence Unit Metadata For Xml</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadataForXml()
		 * @generated
		 */
		public static final EClass PERSISTENCE_UNIT_METADATA_FOR_XML = eINSTANCE.getPersistenceUnitMetadataForXml();

		/**
		 * The meta object literal for the '<em><b>Xml Mapping Metadata Complete For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_METADATA_FOR_XML__XML_MAPPING_METADATA_COMPLETE_FOR_XML = eINSTANCE.getPersistenceUnitMetadataForXml_XmlMappingMetadataCompleteForXml();

		/**
		 * The meta object literal for the '<em><b>Persistence Unit Defaults For Xml</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PERSISTENCE_UNIT_METADATA_FOR_XML__PERSISTENCE_UNIT_DEFAULTS_FOR_XML = eINSTANCE.getPersistenceUnitMetadataForXml_PersistenceUnitDefaultsForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal <em>Persistence Unit Defaults Internal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsInternal()
		 * @generated
		 */
		public static final EClass PERSISTENCE_UNIT_DEFAULTS_INTERNAL = eINSTANCE.getPersistenceUnitDefaultsInternal();

		/**
		 * The meta object literal for the '<em><b>Schema Internal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_INTERNAL = eINSTANCE.getPersistenceUnitDefaultsInternal_SchemaInternal();

		/**
		 * The meta object literal for the '<em><b>Catalog Internal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_INTERNAL = eINSTANCE.getPersistenceUnitDefaultsInternal_CatalogInternal();

		/**
		 * The meta object literal for the '<em><b>Access Internal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_INTERNAL = eINSTANCE.getPersistenceUnitDefaultsInternal_AccessInternal();

		/**
		 * The meta object literal for the '<em><b>Cascade Persist Internal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_INTERNAL = eINSTANCE.getPersistenceUnitDefaultsInternal_CascadePersistInternal();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults <em>Persistence Unit Defaults</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaults()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml <em>Persistence Unit Defaults For Xml</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsForXml()
		 * @generated
		 */
		public static final EClass PERSISTENCE_UNIT_DEFAULTS_FOR_XML = eINSTANCE.getPersistenceUnitDefaultsForXml();

		/**
		 * The meta object literal for the '<em><b>Schema For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_DEFAULTS_FOR_XML__SCHEMA_FOR_XML = eINSTANCE.getPersistenceUnitDefaultsForXml_SchemaForXml();

		/**
		 * The meta object literal for the '<em><b>Catalog For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_DEFAULTS_FOR_XML__CATALOG_FOR_XML = eINSTANCE.getPersistenceUnitDefaultsForXml_CatalogForXml();

		/**
		 * The meta object literal for the '<em><b>Access For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_DEFAULTS_FOR_XML__ACCESS_FOR_XML = eINSTANCE.getPersistenceUnitDefaultsForXml_AccessForXml();

		/**
		 * The meta object literal for the '<em><b>Cascade Persist For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT_DEFAULTS_FOR_XML__CASCADE_PERSIST_FOR_XML = eINSTANCE.getPersistenceUnitDefaultsForXml_CascadePersistForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlTable <em>Xml Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlTable
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTable()
		 * @generated
		 */
		public static final EClass XML_TABLE = eINSTANCE.getXmlTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn <em>Abstract Xml Named Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlNamedColumn()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_NAMED_COLUMN = eINSTANCE.getAbstractXmlNamedColumn();

		/**
		 * The meta object literal for the '<em><b>Specified Name For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME_FOR_XML = eINSTANCE.getAbstractXmlNamedColumn_SpecifiedNameForXml();

		/**
		 * The meta object literal for the '<em><b>Column Definition For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION_FOR_XML = eINSTANCE.getAbstractXmlNamedColumn_ColumnDefinitionForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn <em>Abstract Xml Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlColumn()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_COLUMN = eINSTANCE.getAbstractXmlColumn();

		/**
		 * The meta object literal for the '<em><b>Unique For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_COLUMN__UNIQUE_FOR_XML = eINSTANCE.getAbstractXmlColumn_UniqueForXml();

		/**
		 * The meta object literal for the '<em><b>Nullable For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_COLUMN__NULLABLE_FOR_XML = eINSTANCE.getAbstractXmlColumn_NullableForXml();

		/**
		 * The meta object literal for the '<em><b>Insertable For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_COLUMN__INSERTABLE_FOR_XML = eINSTANCE.getAbstractXmlColumn_InsertableForXml();

		/**
		 * The meta object literal for the '<em><b>Updatable For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_COLUMN__UPDATABLE_FOR_XML = eINSTANCE.getAbstractXmlColumn_UpdatableForXml();

		/**
		 * The meta object literal for the '<em><b>Specified Table For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_COLUMN__SPECIFIED_TABLE_FOR_XML = eINSTANCE.getAbstractXmlColumn_SpecifiedTableForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn <em>Xml Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlColumn
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlColumn()
		 * @generated
		 */
		public static final EClass XML_COLUMN = eINSTANCE.getXmlColumn();

		/**
		 * The meta object literal for the '<em><b>Length For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_COLUMN__LENGTH_FOR_XML = eINSTANCE.getXmlColumn_LengthForXml();

		/**
		 * The meta object literal for the '<em><b>Precision For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_COLUMN__PRECISION_FOR_XML = eINSTANCE.getXmlColumn_PrecisionForXml();

		/**
		 * The meta object literal for the '<em><b>Scale For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_COLUMN__SCALE_FOR_XML = eINSTANCE.getXmlColumn_ScaleForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlJoinColumn <em>Xml Join Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlJoinColumn
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlJoinColumn()
		 * @generated
		 */
		public static final EClass XML_JOIN_COLUMN = eINSTANCE.getXmlJoinColumn();

		/**
		 * The meta object literal for the '<em><b>Specified Referenced Column Name For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML = eINSTANCE.getXmlJoinColumn_SpecifiedReferencedColumnNameForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.IXmlColumnMapping <em>IXml Column Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.IXmlColumnMapping
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIXmlColumnMapping()
		 * @generated
		 */
		public static final EClass IXML_COLUMN_MAPPING = eINSTANCE.getIXmlColumnMapping();

		/**
		 * The meta object literal for the '<em><b>Column For Xml</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IXML_COLUMN_MAPPING__COLUMN_FOR_XML = eINSTANCE.getIXmlColumnMapping_ColumnForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlManyToOne <em>Xml Many To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlManyToOne
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlManyToOne()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_ONE = eINSTANCE.getXmlManyToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlOneToOne <em>Xml One To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlOneToOne
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlOneToOne()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_ONE = eINSTANCE.getXmlOneToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlSingleRelationshipMapping <em>Xml Single Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlSingleRelationshipMapping
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlSingleRelationshipMapping()
		 * @generated
		 */
		public static final EClass XML_SINGLE_RELATIONSHIP_MAPPING = eINSTANCE.getXmlSingleRelationshipMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping <em>Xml Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlRelationshipMapping()
		 * @generated
		 */
		public static final EClass XML_RELATIONSHIP_MAPPING = eINSTANCE.getXmlRelationshipMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlJoinTable <em>Xml Join Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlJoinTable
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlJoinTable()
		 * @generated
		 */
		public static final EClass XML_JOIN_TABLE = eINSTANCE.getXmlJoinTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable <em>Abstract Xml Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlTable()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_TABLE = eINSTANCE.getAbstractXmlTable();

		/**
		 * The meta object literal for the '<em><b>Specified Name For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_TABLE__SPECIFIED_NAME_FOR_XML = eINSTANCE.getAbstractXmlTable_SpecifiedNameForXml();

		/**
		 * The meta object literal for the '<em><b>Specified Catalog For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_TABLE__SPECIFIED_CATALOG_FOR_XML = eINSTANCE.getAbstractXmlTable_SpecifiedCatalogForXml();

		/**
		 * The meta object literal for the '<em><b>Specified Schema For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA_FOR_XML = eINSTANCE.getAbstractXmlTable_SpecifiedSchemaForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlOverride <em>Xml Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlOverride
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlOverride()
		 * @generated
		 */
		public static final EClass XML_OVERRIDE = eINSTANCE.getXmlOverride();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeOverride <em>Xml Attribute Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlAttributeOverride
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlAttributeOverride()
		 * @generated
		 */
		public static final EClass XML_ATTRIBUTE_OVERRIDE = eINSTANCE.getXmlAttributeOverride();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlAssociationOverride <em>Xml Association Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlAssociationOverride
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlAssociationOverride()
		 * @generated
		 */
		public static final EClass XML_ASSOCIATION_OVERRIDE = eINSTANCE.getXmlAssociationOverride();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn <em>Xml Discriminator Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlDiscriminatorColumn()
		 * @generated
		 */
		public static final EClass XML_DISCRIMINATOR_COLUMN = eINSTANCE.getXmlDiscriminatorColumn();

		/**
		 * The meta object literal for the '<em><b>Discriminator Type For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE_FOR_XML = eINSTANCE.getXmlDiscriminatorColumn_DiscriminatorTypeForXml();

		/**
		 * The meta object literal for the '<em><b>Specified Length For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH_FOR_XML = eINSTANCE.getXmlDiscriminatorColumn_SpecifiedLengthForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlSecondaryTable <em>Xml Secondary Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlSecondaryTable
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlSecondaryTable()
		 * @generated
		 */
		public static final EClass XML_SECONDARY_TABLE = eINSTANCE.getXmlSecondaryTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn <em>Xml Primary Key Join Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPrimaryKeyJoinColumn()
		 * @generated
		 */
		public static final EClass XML_PRIMARY_KEY_JOIN_COLUMN = eINSTANCE.getXmlPrimaryKeyJoinColumn();

		/**
		 * The meta object literal for the '<em><b>Specified Referenced Column Name For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML = eINSTANCE.getXmlPrimaryKeyJoinColumn_SpecifiedReferencedColumnNameForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlGeneratedValue <em>Xml Generated Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlGeneratedValue
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlGeneratedValue()
		 * @generated
		 */
		public static final EClass XML_GENERATED_VALUE = eINSTANCE.getXmlGeneratedValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlGenerator <em>Xml Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlGenerator
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlGenerator()
		 * @generated
		 */
		public static final EClass XML_GENERATOR = eINSTANCE.getXmlGenerator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlSequenceGenerator <em>Xml Sequence Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlSequenceGenerator
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlSequenceGenerator()
		 * @generated
		 */
		public static final EClass XML_SEQUENCE_GENERATOR = eINSTANCE.getXmlSequenceGenerator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlTableGenerator <em>Xml Table Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlTableGenerator
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTableGenerator()
		 * @generated
		 */
		public static final EClass XML_TABLE_GENERATOR = eINSTANCE.getXmlTableGenerator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlQuery <em>Abstract Xml Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlQuery
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlQuery()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_QUERY = eINSTANCE.getAbstractXmlQuery();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlNamedQuery <em>Xml Named Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlNamedQuery
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlNamedQuery()
		 * @generated
		 */
		public static final EClass XML_NAMED_QUERY = eINSTANCE.getXmlNamedQuery();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlNamedNativeQuery <em>Xml Named Native Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlNamedNativeQuery
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlNamedNativeQuery()
		 * @generated
		 */
		public static final EClass XML_NAMED_NATIVE_QUERY = eINSTANCE.getXmlNamedNativeQuery();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlQueryHint <em>Xml Query Hint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlQueryHint
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlQueryHint()
		 * @generated
		 */
		public static final EClass XML_QUERY_HINT = eINSTANCE.getXmlQueryHint();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlUniqueConstraint <em>Xml Unique Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlUniqueConstraint
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlUniqueConstraint()
		 * @generated
		 */
		public static final EClass XML_UNIQUE_CONSTRAINT = eINSTANCE.getXmlUniqueConstraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlCascade <em>Xml Cascade</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlCascade
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlCascade()
		 * @generated
		 */
		public static final EClass XML_CASCADE = eINSTANCE.getXmlCascade();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlIdClass <em>Xml Id Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlIdClass
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlIdClass()
		 * @generated
		 */
		public static final EClass XML_ID_CLASS = eINSTANCE.getXmlIdClass();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ID_CLASS__VALUE = eINSTANCE.getXmlIdClass_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlInheritance <em>Xml Inheritance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlInheritance
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlInheritance()
		 * @generated
		 */
		public static final EClass XML_INHERITANCE = eINSTANCE.getXmlInheritance();

		/**
		 * The meta object literal for the '<em><b>Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_INHERITANCE__STRATEGY = eINSTANCE.getXmlInheritance_Strategy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMapKey <em>Xml Map Key</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.orm.XmlMapKey
		 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMapKey()
		 * @generated
		 */
		public static final EClass XML_MAP_KEY = eINSTANCE.getXmlMapKey();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MAP_KEY__NAME = eINSTANCE.getXmlMapKey_Name();
	}
} //OrmPackage
