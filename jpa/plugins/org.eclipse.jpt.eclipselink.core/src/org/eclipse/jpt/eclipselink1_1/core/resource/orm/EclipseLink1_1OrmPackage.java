/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.resource.orm;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.jpt.core.resource.orm.OrmPackage;

import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage;

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
 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmFactory
 * @model kind="package"
 * @generated
 */
public class EclipseLink1_1OrmPackage extends EPackageImpl
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
	public static final String eNS_URI = "jpt.eclipselink1_1.orm.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.eclipselink1_1.core.resource.orm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLink1_1OrmPackage eINSTANCE = org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEntityMappings
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlEntityMappings()
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
	public static final int XML_ENTITY_MAPPINGS__NAMED_QUERIES = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__NAMED_QUERIES;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__VERSION = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__VERSION;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__DESCRIPTION = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Persistence Unit Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__PACKAGE = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__PACKAGE;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__SCHEMA = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__SCHEMA;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__CATALOG = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__CATALOG;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__ACCESS = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ACCESS;

	/**
	 * The feature id for the '<em><b>Sequence Generators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__SEQUENCE_GENERATORS = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__SEQUENCE_GENERATORS;

	/**
	 * The feature id for the '<em><b>Table Generators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__TABLE_GENERATORS = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__TABLE_GENERATORS;

	/**
	 * The feature id for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS;

	/**
	 * The feature id for the '<em><b>Mapped Superclasses</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__MAPPED_SUPERCLASSES = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__MAPPED_SUPERCLASSES;

	/**
	 * The feature id for the '<em><b>Entities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__ENTITIES = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__ENTITIES;

	/**
	 * The feature id for the '<em><b>Embeddables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__EMBEDDABLES = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__EMBEDDABLES;

	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__CONVERTERS = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__CONVERTERS;

	/**
	 * The feature id for the '<em><b>Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__TYPE_CONVERTERS = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__TYPE_CONVERTERS;

	/**
	 * The feature id for the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__OBJECT_TYPE_CONVERTERS = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__OBJECT_TYPE_CONVERTERS;

	/**
	 * The feature id for the '<em><b>Struct Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__STRUCT_CONVERTERS = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__STRUCT_CONVERTERS;

	/**
	 * The feature id for the '<em><b>Named Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__NAMED_STORED_PROCEDURE_QUERIES = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS__NAMED_STORED_PROCEDURE_QUERIES;

	/**
	 * The number of structural features of the '<em>Xml Entity Mappings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_FEATURE_COUNT = EclipseLinkOrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlAttributeMapping
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlAttributeMapping()
	 * @generated
	 */
	public static final int XML_ATTRIBUTE_MAPPING = 1;

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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlId <em>Xml Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlId
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlId()
	 * @generated
	 */
	public static final int XML_ID = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__NAME = EclipseLinkOrmPackage.XML_ID__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__COLUMN = EclipseLinkOrmPackage.XML_ID__COLUMN;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__LOB = EclipseLinkOrmPackage.XML_ID__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__TEMPORAL = EclipseLinkOrmPackage.XML_ID__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__ENUMERATED = EclipseLinkOrmPackage.XML_ID__ENUMERATED;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__SEQUENCE_GENERATOR = EclipseLinkOrmPackage.XML_ID__SEQUENCE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__TABLE_GENERATOR = EclipseLinkOrmPackage.XML_ID__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__GENERATED_VALUE = EclipseLinkOrmPackage.XML_ID__GENERATED_VALUE;

	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__MUTABLE = EclipseLinkOrmPackage.XML_ID__MUTABLE;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__CONVERTER = EclipseLinkOrmPackage.XML_ID__CONVERTER;

	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__TYPE_CONVERTER = EclipseLinkOrmPackage.XML_ID__TYPE_CONVERTER;

	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__OBJECT_TYPE_CONVERTER = EclipseLinkOrmPackage.XML_ID__OBJECT_TYPE_CONVERTER;

	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__STRUCT_CONVERTER = EclipseLinkOrmPackage.XML_ID__STRUCT_CONVERTER;

	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__CONVERT = EclipseLinkOrmPackage.XML_ID__CONVERT;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__ACCESS_METHODS = EclipseLinkOrmPackage.XML_ID__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__PROPERTIES = EclipseLinkOrmPackage.XML_ID__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__ACCESS = EclipseLinkOrmPackage.XML_ID_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_FEATURE_COUNT = EclipseLinkOrmPackage.XML_ID_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlIdImpl <em>Xml Id Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlIdImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlIdImpl()
	 * @generated
	 */
	public static final int XML_ID_IMPL = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__NAME = EclipseLinkOrmPackage.XML_ID_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__COLUMN = EclipseLinkOrmPackage.XML_ID_IMPL__COLUMN;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__LOB = EclipseLinkOrmPackage.XML_ID_IMPL__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__TEMPORAL = EclipseLinkOrmPackage.XML_ID_IMPL__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__ENUMERATED = EclipseLinkOrmPackage.XML_ID_IMPL__ENUMERATED;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__SEQUENCE_GENERATOR = EclipseLinkOrmPackage.XML_ID_IMPL__SEQUENCE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__TABLE_GENERATOR = EclipseLinkOrmPackage.XML_ID_IMPL__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__GENERATED_VALUE = EclipseLinkOrmPackage.XML_ID_IMPL__GENERATED_VALUE;

	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__MUTABLE = EclipseLinkOrmPackage.XML_ID_IMPL__MUTABLE;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__CONVERTER = EclipseLinkOrmPackage.XML_ID_IMPL__CONVERTER;

	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__TYPE_CONVERTER = EclipseLinkOrmPackage.XML_ID_IMPL__TYPE_CONVERTER;

	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__OBJECT_TYPE_CONVERTER = EclipseLinkOrmPackage.XML_ID_IMPL__OBJECT_TYPE_CONVERTER;

	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__STRUCT_CONVERTER = EclipseLinkOrmPackage.XML_ID_IMPL__STRUCT_CONVERTER;

	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__CONVERT = EclipseLinkOrmPackage.XML_ID_IMPL__CONVERT;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__ACCESS_METHODS = EclipseLinkOrmPackage.XML_ID_IMPL__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__PROPERTIES = EclipseLinkOrmPackage.XML_ID_IMPL__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__ACCESS = EclipseLinkOrmPackage.XML_ID_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Id Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL_FEATURE_COUNT = EclipseLinkOrmPackage.XML_ID_IMPL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedId
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlEmbeddedId()
	 * @generated
	 */
	public static final int XML_EMBEDDED_ID = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__NAME = EclipseLinkOrmPackage.XML_EMBEDDED_ID__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__ATTRIBUTE_OVERRIDES = EclipseLinkOrmPackage.XML_EMBEDDED_ID__ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__ACCESS_METHODS = EclipseLinkOrmPackage.XML_EMBEDDED_ID__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__PROPERTIES = EclipseLinkOrmPackage.XML_EMBEDDED_ID__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__ACCESS = EclipseLinkOrmPackage.XML_EMBEDDED_ID_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Embedded Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID_FEATURE_COUNT = EclipseLinkOrmPackage.XML_EMBEDDED_ID_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedIdImpl <em>Xml Embedded Id Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedIdImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlEmbeddedIdImpl()
	 * @generated
	 */
	public static final int XML_EMBEDDED_ID_IMPL = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID_IMPL__NAME = EclipseLinkOrmPackage.XML_EMBEDDED_ID_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID_IMPL__ATTRIBUTE_OVERRIDES = EclipseLinkOrmPackage.XML_EMBEDDED_ID_IMPL__ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID_IMPL__ACCESS_METHODS = EclipseLinkOrmPackage.XML_EMBEDDED_ID_IMPL__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID_IMPL__PROPERTIES = EclipseLinkOrmPackage.XML_EMBEDDED_ID_IMPL__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID_IMPL__ACCESS = EclipseLinkOrmPackage.XML_EMBEDDED_ID_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Embedded Id Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID_IMPL_FEATURE_COUNT = EclipseLinkOrmPackage.XML_EMBEDDED_ID_IMPL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbedded <em>Xml Embedded</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbedded
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlEmbedded()
	 * @generated
	 */
	public static final int XML_EMBEDDED = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__NAME = EclipseLinkOrmPackage.XML_EMBEDDED__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ATTRIBUTE_OVERRIDES = EclipseLinkOrmPackage.XML_EMBEDDED__ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ACCESS_METHODS = EclipseLinkOrmPackage.XML_EMBEDDED__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__PROPERTIES = EclipseLinkOrmPackage.XML_EMBEDDED__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ACCESS = EclipseLinkOrmPackage.XML_EMBEDDED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Embedded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_FEATURE_COUNT = EclipseLinkOrmPackage.XML_EMBEDDED_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedImpl <em>Xml Embedded Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlEmbeddedImpl()
	 * @generated
	 */
	public static final int XML_EMBEDDED_IMPL = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_IMPL__NAME = EclipseLinkOrmPackage.XML_EMBEDDED_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_IMPL__ATTRIBUTE_OVERRIDES = EclipseLinkOrmPackage.XML_EMBEDDED_IMPL__ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_IMPL__ACCESS_METHODS = EclipseLinkOrmPackage.XML_EMBEDDED_IMPL__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_IMPL__PROPERTIES = EclipseLinkOrmPackage.XML_EMBEDDED_IMPL__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_IMPL__ACCESS = EclipseLinkOrmPackage.XML_EMBEDDED_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Embedded Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_IMPL_FEATURE_COUNT = EclipseLinkOrmPackage.XML_EMBEDDED_IMPL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasic <em>Xml Basic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasic
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasic()
	 * @generated
	 */
	public static final int XML_BASIC = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__NAME = EclipseLinkOrmPackage.XML_BASIC__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__COLUMN = EclipseLinkOrmPackage.XML_BASIC__COLUMN;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__LOB = EclipseLinkOrmPackage.XML_BASIC__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__TEMPORAL = EclipseLinkOrmPackage.XML_BASIC__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__ENUMERATED = EclipseLinkOrmPackage.XML_BASIC__ENUMERATED;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__FETCH = EclipseLinkOrmPackage.XML_BASIC__FETCH;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__OPTIONAL = EclipseLinkOrmPackage.XML_BASIC__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__MUTABLE = EclipseLinkOrmPackage.XML_BASIC__MUTABLE;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__CONVERTER = EclipseLinkOrmPackage.XML_BASIC__CONVERTER;

	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__TYPE_CONVERTER = EclipseLinkOrmPackage.XML_BASIC__TYPE_CONVERTER;

	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__OBJECT_TYPE_CONVERTER = EclipseLinkOrmPackage.XML_BASIC__OBJECT_TYPE_CONVERTER;

	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__STRUCT_CONVERTER = EclipseLinkOrmPackage.XML_BASIC__STRUCT_CONVERTER;

	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__CONVERT = EclipseLinkOrmPackage.XML_BASIC__CONVERT;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__ACCESS_METHODS = EclipseLinkOrmPackage.XML_BASIC__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__PROPERTIES = EclipseLinkOrmPackage.XML_BASIC__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__ACCESS = EclipseLinkOrmPackage.XML_BASIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__SEQUENCE_GENERATOR = EclipseLinkOrmPackage.XML_BASIC_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__TABLE_GENERATOR = EclipseLinkOrmPackage.XML_BASIC_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__GENERATED_VALUE = EclipseLinkOrmPackage.XML_BASIC_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Xml Basic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_FEATURE_COUNT = EclipseLinkOrmPackage.XML_BASIC_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicImpl <em>Xml Basic Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasicImpl()
	 * @generated
	 */
	public static final int XML_BASIC_IMPL = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__NAME = EclipseLinkOrmPackage.XML_BASIC_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__COLUMN = EclipseLinkOrmPackage.XML_BASIC_IMPL__COLUMN;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__LOB = EclipseLinkOrmPackage.XML_BASIC_IMPL__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__TEMPORAL = EclipseLinkOrmPackage.XML_BASIC_IMPL__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__ENUMERATED = EclipseLinkOrmPackage.XML_BASIC_IMPL__ENUMERATED;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__FETCH = EclipseLinkOrmPackage.XML_BASIC_IMPL__FETCH;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__OPTIONAL = EclipseLinkOrmPackage.XML_BASIC_IMPL__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__MUTABLE = EclipseLinkOrmPackage.XML_BASIC_IMPL__MUTABLE;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__CONVERTER = EclipseLinkOrmPackage.XML_BASIC_IMPL__CONVERTER;

	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__TYPE_CONVERTER = EclipseLinkOrmPackage.XML_BASIC_IMPL__TYPE_CONVERTER;

	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__OBJECT_TYPE_CONVERTER = EclipseLinkOrmPackage.XML_BASIC_IMPL__OBJECT_TYPE_CONVERTER;

	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__STRUCT_CONVERTER = EclipseLinkOrmPackage.XML_BASIC_IMPL__STRUCT_CONVERTER;

	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__CONVERT = EclipseLinkOrmPackage.XML_BASIC_IMPL__CONVERT;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__ACCESS_METHODS = EclipseLinkOrmPackage.XML_BASIC_IMPL__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__PROPERTIES = EclipseLinkOrmPackage.XML_BASIC_IMPL__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__ACCESS = EclipseLinkOrmPackage.XML_BASIC_IMPL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__SEQUENCE_GENERATOR = EclipseLinkOrmPackage.XML_BASIC_IMPL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__TABLE_GENERATOR = EclipseLinkOrmPackage.XML_BASIC_IMPL_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__GENERATED_VALUE = EclipseLinkOrmPackage.XML_BASIC_IMPL_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Xml Basic Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL_FEATURE_COUNT = EclipseLinkOrmPackage.XML_BASIC_IMPL_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersion <em>Xml Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersion
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlVersion()
	 * @generated
	 */
	public static final int XML_VERSION = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__NAME = EclipseLinkOrmPackage.XML_VERSION__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__COLUMN = EclipseLinkOrmPackage.XML_VERSION__COLUMN;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__LOB = EclipseLinkOrmPackage.XML_VERSION__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__TEMPORAL = EclipseLinkOrmPackage.XML_VERSION__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__ENUMERATED = EclipseLinkOrmPackage.XML_VERSION__ENUMERATED;

	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__MUTABLE = EclipseLinkOrmPackage.XML_VERSION__MUTABLE;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__CONVERTER = EclipseLinkOrmPackage.XML_VERSION__CONVERTER;

	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__TYPE_CONVERTER = EclipseLinkOrmPackage.XML_VERSION__TYPE_CONVERTER;

	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__OBJECT_TYPE_CONVERTER = EclipseLinkOrmPackage.XML_VERSION__OBJECT_TYPE_CONVERTER;

	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__STRUCT_CONVERTER = EclipseLinkOrmPackage.XML_VERSION__STRUCT_CONVERTER;

	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__CONVERT = EclipseLinkOrmPackage.XML_VERSION__CONVERT;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__ACCESS_METHODS = EclipseLinkOrmPackage.XML_VERSION__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__PROPERTIES = EclipseLinkOrmPackage.XML_VERSION__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__ACCESS = EclipseLinkOrmPackage.XML_VERSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_FEATURE_COUNT = EclipseLinkOrmPackage.XML_VERSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersionImpl <em>Xml Version Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersionImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlVersionImpl()
	 * @generated
	 */
	public static final int XML_VERSION_IMPL = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__NAME = EclipseLinkOrmPackage.XML_VERSION_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__COLUMN = EclipseLinkOrmPackage.XML_VERSION_IMPL__COLUMN;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__LOB = EclipseLinkOrmPackage.XML_VERSION_IMPL__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__TEMPORAL = EclipseLinkOrmPackage.XML_VERSION_IMPL__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__ENUMERATED = EclipseLinkOrmPackage.XML_VERSION_IMPL__ENUMERATED;

	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__MUTABLE = EclipseLinkOrmPackage.XML_VERSION_IMPL__MUTABLE;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__CONVERTER = EclipseLinkOrmPackage.XML_VERSION_IMPL__CONVERTER;

	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__TYPE_CONVERTER = EclipseLinkOrmPackage.XML_VERSION_IMPL__TYPE_CONVERTER;

	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__OBJECT_TYPE_CONVERTER = EclipseLinkOrmPackage.XML_VERSION_IMPL__OBJECT_TYPE_CONVERTER;

	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__STRUCT_CONVERTER = EclipseLinkOrmPackage.XML_VERSION_IMPL__STRUCT_CONVERTER;

	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__CONVERT = EclipseLinkOrmPackage.XML_VERSION_IMPL__CONVERT;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__ACCESS_METHODS = EclipseLinkOrmPackage.XML_VERSION_IMPL__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__PROPERTIES = EclipseLinkOrmPackage.XML_VERSION_IMPL__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__ACCESS = EclipseLinkOrmPackage.XML_VERSION_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Version Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL_FEATURE_COUNT = EclipseLinkOrmPackage.XML_VERSION_IMPL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOne
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlOneToOne()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__NAME = EclipseLinkOrmPackage.XML_ONE_TO_ONE__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__TARGET_ENTITY = EclipseLinkOrmPackage.XML_ONE_TO_ONE__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__FETCH = EclipseLinkOrmPackage.XML_ONE_TO_ONE__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__CASCADE = EclipseLinkOrmPackage.XML_ONE_TO_ONE__CASCADE;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__JOIN_TABLE = EclipseLinkOrmPackage.XML_ONE_TO_ONE__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__JOIN_COLUMNS = EclipseLinkOrmPackage.XML_ONE_TO_ONE__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__OPTIONAL = EclipseLinkOrmPackage.XML_ONE_TO_ONE__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__MAPPED_BY = EclipseLinkOrmPackage.XML_ONE_TO_ONE__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS = EclipseLinkOrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Private Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__PRIVATE_OWNED = EclipseLinkOrmPackage.XML_ONE_TO_ONE__PRIVATE_OWNED;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__JOIN_FETCH = EclipseLinkOrmPackage.XML_ONE_TO_ONE__JOIN_FETCH;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__ACCESS_METHODS = EclipseLinkOrmPackage.XML_ONE_TO_ONE__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__PROPERTIES = EclipseLinkOrmPackage.XML_ONE_TO_ONE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__ACCESS = EclipseLinkOrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml One To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_FEATURE_COUNT = EclipseLinkOrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOneImpl <em>Xml One To One Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOneImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlOneToOneImpl()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE_IMPL = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__NAME = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__TARGET_ENTITY = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__FETCH = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__CASCADE = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL__CASCADE;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__JOIN_TABLE = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__JOIN_COLUMNS = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__OPTIONAL = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__MAPPED_BY = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__PRIMARY_KEY_JOIN_COLUMNS = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL__PRIMARY_KEY_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Private Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__PRIVATE_OWNED = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL__PRIVATE_OWNED;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__JOIN_FETCH = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL__JOIN_FETCH;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__ACCESS_METHODS = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__PROPERTIES = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__ACCESS = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml One To One Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL_FEATURE_COUNT = EclipseLinkOrmPackage.XML_ONE_TO_ONE_IMPL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToMany
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlOneToMany()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__NAME = EclipseLinkOrmPackage.XML_ONE_TO_MANY__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__TARGET_ENTITY = EclipseLinkOrmPackage.XML_ONE_TO_MANY__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__FETCH = EclipseLinkOrmPackage.XML_ONE_TO_MANY__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__CASCADE = EclipseLinkOrmPackage.XML_ONE_TO_MANY__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAPPED_BY = EclipseLinkOrmPackage.XML_ONE_TO_MANY__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__JOIN_TABLE = EclipseLinkOrmPackage.XML_ONE_TO_MANY__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ORDER_BY = EclipseLinkOrmPackage.XML_ONE_TO_MANY__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY = EclipseLinkOrmPackage.XML_ONE_TO_MANY__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__JOIN_COLUMNS = EclipseLinkOrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Private Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__PRIVATE_OWNED = EclipseLinkOrmPackage.XML_ONE_TO_MANY__PRIVATE_OWNED;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__JOIN_FETCH = EclipseLinkOrmPackage.XML_ONE_TO_MANY__JOIN_FETCH;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ACCESS_METHODS = EclipseLinkOrmPackage.XML_ONE_TO_MANY__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__PROPERTIES = EclipseLinkOrmPackage.XML_ONE_TO_MANY__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ACCESS = EclipseLinkOrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml One To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_FEATURE_COUNT = EclipseLinkOrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToManyImpl <em>Xml One To Many Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToManyImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlOneToManyImpl()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY_IMPL = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__NAME = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__TARGET_ENTITY = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__FETCH = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__CASCADE = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__MAPPED_BY = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__JOIN_TABLE = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__ORDER_BY = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__MAP_KEY = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__JOIN_COLUMNS = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Private Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__PRIVATE_OWNED = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL__PRIVATE_OWNED;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__JOIN_FETCH = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL__JOIN_FETCH;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__ACCESS_METHODS = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__PROPERTIES = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__ACCESS = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml One To Many Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL_FEATURE_COUNT = EclipseLinkOrmPackage.XML_ONE_TO_MANY_IMPL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOne
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlManyToOne()
	 * @generated
	 */
	public static final int XML_MANY_TO_ONE = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__NAME = EclipseLinkOrmPackage.XML_MANY_TO_ONE__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__TARGET_ENTITY = EclipseLinkOrmPackage.XML_MANY_TO_ONE__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__FETCH = EclipseLinkOrmPackage.XML_MANY_TO_ONE__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__CASCADE = EclipseLinkOrmPackage.XML_MANY_TO_ONE__CASCADE;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__JOIN_TABLE = EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__JOIN_COLUMNS = EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__OPTIONAL = EclipseLinkOrmPackage.XML_MANY_TO_ONE__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__JOIN_FETCH = EclipseLinkOrmPackage.XML_MANY_TO_ONE__JOIN_FETCH;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__ACCESS_METHODS = EclipseLinkOrmPackage.XML_MANY_TO_ONE__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__PROPERTIES = EclipseLinkOrmPackage.XML_MANY_TO_ONE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__ACCESS = EclipseLinkOrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Many To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_FEATURE_COUNT = EclipseLinkOrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOneImpl <em>Xml Many To One Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOneImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlManyToOneImpl()
	 * @generated
	 */
	public static final int XML_MANY_TO_ONE_IMPL = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__NAME = EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__TARGET_ENTITY = EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__FETCH = EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__CASCADE = EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__CASCADE;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__JOIN_TABLE = EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__JOIN_COLUMNS = EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__OPTIONAL = EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__JOIN_FETCH = EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_FETCH;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__ACCESS_METHODS = EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__PROPERTIES = EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__ACCESS = EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Many To One Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL_FEATURE_COUNT = EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToMany
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlManyToMany()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__NAME = EclipseLinkOrmPackage.XML_MANY_TO_MANY__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__TARGET_ENTITY = EclipseLinkOrmPackage.XML_MANY_TO_MANY__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__FETCH = EclipseLinkOrmPackage.XML_MANY_TO_MANY__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__CASCADE = EclipseLinkOrmPackage.XML_MANY_TO_MANY__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAPPED_BY = EclipseLinkOrmPackage.XML_MANY_TO_MANY__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__JOIN_TABLE = EclipseLinkOrmPackage.XML_MANY_TO_MANY__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ORDER_BY = EclipseLinkOrmPackage.XML_MANY_TO_MANY__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY = EclipseLinkOrmPackage.XML_MANY_TO_MANY__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__JOIN_FETCH = EclipseLinkOrmPackage.XML_MANY_TO_MANY__JOIN_FETCH;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ACCESS_METHODS = EclipseLinkOrmPackage.XML_MANY_TO_MANY__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__PROPERTIES = EclipseLinkOrmPackage.XML_MANY_TO_MANY__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ACCESS = EclipseLinkOrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Many To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_FEATURE_COUNT = EclipseLinkOrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToManyImpl <em>Xml Many To Many Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToManyImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlManyToManyImpl()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY_IMPL = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__NAME = EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__TARGET_ENTITY = EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__FETCH = EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__CASCADE = EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__MAPPED_BY = EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__JOIN_TABLE = EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__ORDER_BY = EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__MAP_KEY = EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__JOIN_FETCH = EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_FETCH;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__ACCESS_METHODS = EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__PROPERTIES = EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__ACCESS = EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Many To Many Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL_FEATURE_COUNT = EclipseLinkOrmPackage.XML_MANY_TO_MANY_IMPL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollection <em>Xml Basic Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollection
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasicCollection()
	 * @generated
	 */
	public static final int XML_BASIC_COLLECTION = 20;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION__NAME = EclipseLinkOrmPackage.XML_BASIC_COLLECTION__NAME;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION__ACCESS_METHODS = EclipseLinkOrmPackage.XML_BASIC_COLLECTION__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION__PROPERTIES = EclipseLinkOrmPackage.XML_BASIC_COLLECTION__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION__ACCESS = EclipseLinkOrmPackage.XML_BASIC_COLLECTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Basic Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION_FEATURE_COUNT = EclipseLinkOrmPackage.XML_BASIC_COLLECTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollectionImpl <em>Xml Basic Collection Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollectionImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasicCollectionImpl()
	 * @generated
	 */
	public static final int XML_BASIC_COLLECTION_IMPL = 21;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION_IMPL__NAME = EclipseLinkOrmPackage.XML_BASIC_COLLECTION_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION_IMPL__ACCESS_METHODS = EclipseLinkOrmPackage.XML_BASIC_COLLECTION_IMPL__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION_IMPL__PROPERTIES = EclipseLinkOrmPackage.XML_BASIC_COLLECTION_IMPL__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION_IMPL__ACCESS = EclipseLinkOrmPackage.XML_BASIC_COLLECTION_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Basic Collection Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION_IMPL_FEATURE_COUNT = EclipseLinkOrmPackage.XML_BASIC_COLLECTION_IMPL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMap <em>Xml Basic Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMap
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasicMap()
	 * @generated
	 */
	public static final int XML_BASIC_MAP = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP__NAME = EclipseLinkOrmPackage.XML_BASIC_MAP__NAME;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP__ACCESS_METHODS = EclipseLinkOrmPackage.XML_BASIC_MAP__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP__PROPERTIES = EclipseLinkOrmPackage.XML_BASIC_MAP__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP__ACCESS = EclipseLinkOrmPackage.XML_BASIC_MAP_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Basic Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP_FEATURE_COUNT = EclipseLinkOrmPackage.XML_BASIC_MAP_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMapImpl <em>Xml Basic Map Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMapImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasicMapImpl()
	 * @generated
	 */
	public static final int XML_BASIC_MAP_IMPL = 23;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP_IMPL__NAME = EclipseLinkOrmPackage.XML_BASIC_MAP_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP_IMPL__ACCESS_METHODS = EclipseLinkOrmPackage.XML_BASIC_MAP_IMPL__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP_IMPL__PROPERTIES = EclipseLinkOrmPackage.XML_BASIC_MAP_IMPL__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP_IMPL__ACCESS = EclipseLinkOrmPackage.XML_BASIC_MAP_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Basic Map Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP_IMPL_FEATURE_COUNT = EclipseLinkOrmPackage.XML_BASIC_MAP_IMPL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformation <em>Xml Transformation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformation
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlTransformation()
	 * @generated
	 */
	public static final int XML_TRANSFORMATION = 24;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION__NAME = EclipseLinkOrmPackage.XML_TRANSFORMATION__NAME;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION__ACCESS_METHODS = EclipseLinkOrmPackage.XML_TRANSFORMATION__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION__PROPERTIES = EclipseLinkOrmPackage.XML_TRANSFORMATION__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION__ACCESS = EclipseLinkOrmPackage.XML_TRANSFORMATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Transformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION_FEATURE_COUNT = EclipseLinkOrmPackage.XML_TRANSFORMATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformationImpl <em>Xml Transformation Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformationImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlTransformationImpl()
	 * @generated
	 */
	public static final int XML_TRANSFORMATION_IMPL = 25;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION_IMPL__NAME = EclipseLinkOrmPackage.XML_TRANSFORMATION_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION_IMPL__ACCESS_METHODS = EclipseLinkOrmPackage.XML_TRANSFORMATION_IMPL__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION_IMPL__PROPERTIES = EclipseLinkOrmPackage.XML_TRANSFORMATION_IMPL__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION_IMPL__ACCESS = EclipseLinkOrmPackage.XML_TRANSFORMATION_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Transformation Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION_IMPL_FEATURE_COUNT = EclipseLinkOrmPackage.XML_TRANSFORMATION_IMPL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOne <em>Xml Variable One To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOne
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlVariableOneToOne()
	 * @generated
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE = 26;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__NAME = EclipseLinkOrmPackage.XML_VARIABLE_ONE_TO_ONE__NAME;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__ACCESS_METHODS = EclipseLinkOrmPackage.XML_VARIABLE_ONE_TO_ONE__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__PROPERTIES = EclipseLinkOrmPackage.XML_VARIABLE_ONE_TO_ONE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__ACCESS = EclipseLinkOrmPackage.XML_VARIABLE_ONE_TO_ONE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Variable One To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_FEATURE_COUNT = EclipseLinkOrmPackage.XML_VARIABLE_ONE_TO_ONE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOneImpl <em>Xml Variable One To One Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOneImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlVariableOneToOneImpl()
	 * @generated
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_IMPL = 27;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_IMPL__NAME = EclipseLinkOrmPackage.XML_VARIABLE_ONE_TO_ONE_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_IMPL__ACCESS_METHODS = EclipseLinkOrmPackage.XML_VARIABLE_ONE_TO_ONE_IMPL__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_IMPL__PROPERTIES = EclipseLinkOrmPackage.XML_VARIABLE_ONE_TO_ONE_IMPL__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_IMPL__ACCESS = EclipseLinkOrmPackage.XML_VARIABLE_ONE_TO_ONE_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Variable One To One Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_IMPL_FEATURE_COUNT = EclipseLinkOrmPackage.XML_VARIABLE_ONE_TO_ONE_IMPL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransient <em>Xml Transient</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransient
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlTransient()
	 * @generated
	 */
	public static final int XML_TRANSIENT = 28;

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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransientImpl <em>Xml Transient Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransientImpl
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlTransientImpl()
	 * @generated
	 */
	public static final int XML_TRANSIENT_IMPL = 29;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT_IMPL__NAME = OrmPackage.XML_TRANSIENT_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT_IMPL__ACCESS = OrmPackage.XML_TRANSIENT_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Transient Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT_IMPL_FEATURE_COUNT = OrmPackage.XML_TRANSIENT_IMPL_FEATURE_COUNT + 1;

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
	private EClass xmlAttributeMappingEClass = null;

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
	private EClass xmlIdImplEClass = null;

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
	private EClass xmlEmbeddedIdImplEClass = null;

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
	private EClass xmlEmbeddedImplEClass = null;

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
	private EClass xmlBasicImplEClass = null;

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
	private EClass xmlVersionImplEClass = null;

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
	private EClass xmlOneToOneImplEClass = null;

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
	private EClass xmlOneToManyImplEClass = null;

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
	private EClass xmlManyToOneImplEClass = null;

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
	private EClass xmlManyToManyImplEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBasicCollectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBasicCollectionImplEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBasicMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBasicMapImplEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTransformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTransformationImplEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlVariableOneToOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlVariableOneToOneImplEClass = null;

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
	private EClass xmlTransientImplEClass = null;

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
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EclipseLink1_1OrmPackage()
	{
		super(eNS_URI, EclipseLink1_1OrmFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EclipseLink1_1OrmPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EclipseLink1_1OrmPackage init()
	{
		if (isInited) return (EclipseLink1_1OrmPackage)EPackage.Registry.INSTANCE.getEPackage(EclipseLink1_1OrmPackage.eNS_URI);

		// Obtain or create and register package
		EclipseLink1_1OrmPackage theEclipseLink1_1OrmPackage = (EclipseLink1_1OrmPackage)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EclipseLink1_1OrmPackage ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EclipseLink1_1OrmPackage());

		isInited = true;

		// Initialize simple dependencies
		OrmPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		EclipseLinkOrmPackage theEclipseLinkOrmPackage = (EclipseLinkOrmPackage)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) instanceof EclipseLinkOrmPackage ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) : EclipseLinkOrmPackage.eINSTANCE);

		// Create package meta-data objects
		theEclipseLink1_1OrmPackage.createPackageContents();
		theEclipseLinkOrmPackage.createPackageContents();

		// Initialize created meta-data
		theEclipseLink1_1OrmPackage.initializePackageContents();
		theEclipseLinkOrmPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEclipseLink1_1OrmPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EclipseLink1_1OrmPackage.eNS_URI, theEclipseLink1_1OrmPackage);
		return theEclipseLink1_1OrmPackage;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity Mappings</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEntityMappings
	 * @generated
	 */
	public EClass getXmlEntityMappings()
	{
		return xmlEntityMappingsEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Attribute Mapping</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlAttributeMapping
	 * @generated
	 */
	public EClass getXmlAttributeMapping()
	{
		return xmlAttributeMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlAttributeMapping#getAccess <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlAttributeMapping#getAccess()
	 * @see #getXmlAttributeMapping()
	 * @generated
	 */
	public EAttribute getXmlAttributeMapping_Access()
	{
		return (EAttribute)xmlAttributeMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlId <em>Xml Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlId
	 * @generated
	 */
	public EClass getXmlId()
	{
		return xmlIdEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlIdImpl <em>Xml Id Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlIdImpl
	 * @generated
	 */
	public EClass getXmlIdImpl()
	{
		return xmlIdImplEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded Id</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedId
	 * @generated
	 */
	public EClass getXmlEmbeddedId()
	{
		return xmlEmbeddedIdEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedIdImpl <em>Xml Embedded Id Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded Id Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedIdImpl
	 * @generated
	 */
	public EClass getXmlEmbeddedIdImpl()
	{
		return xmlEmbeddedIdImplEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbedded <em>Xml Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbedded
	 * @generated
	 */
	public EClass getXmlEmbedded()
	{
		return xmlEmbeddedEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedImpl <em>Xml Embedded Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedImpl
	 * @generated
	 */
	public EClass getXmlEmbeddedImpl()
	{
		return xmlEmbeddedImplEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasic <em>Xml Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasic
	 * @generated
	 */
	public EClass getXmlBasic()
	{
		return xmlBasicEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasic#getGeneratedValue <em>Generated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Generated Value</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasic#getGeneratedValue()
	 * @see #getXmlBasic()
	 * @generated
	 */
	public EReference getXmlBasic_GeneratedValue()
	{
		return (EReference)xmlBasicEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicImpl <em>Xml Basic Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicImpl
	 * @generated
	 */
	public EClass getXmlBasicImpl()
	{
		return xmlBasicImplEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Version</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersion
	 * @generated
	 */
	public EClass getXmlVersion()
	{
		return xmlVersionEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersionImpl <em>Xml Version Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Version Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersionImpl
	 * @generated
	 */
	public EClass getXmlVersionImpl()
	{
		return xmlVersionImplEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOne
	 * @generated
	 */
	public EClass getXmlOneToOne()
	{
		return xmlOneToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOneImpl <em>Xml One To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOneImpl
	 * @generated
	 */
	public EClass getXmlOneToOneImpl()
	{
		return xmlOneToOneImplEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToMany
	 * @generated
	 */
	public EClass getXmlOneToMany()
	{
		return xmlOneToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToManyImpl <em>Xml One To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToManyImpl
	 * @generated
	 */
	public EClass getXmlOneToManyImpl()
	{
		return xmlOneToManyImplEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To One</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOne
	 * @generated
	 */
	public EClass getXmlManyToOne()
	{
		return xmlManyToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOneImpl <em>Xml Many To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To One Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOneImpl
	 * @generated
	 */
	public EClass getXmlManyToOneImpl()
	{
		return xmlManyToOneImplEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToMany
	 * @generated
	 */
	public EClass getXmlManyToMany()
	{
		return xmlManyToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToManyImpl <em>Xml Many To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToManyImpl
	 * @generated
	 */
	public EClass getXmlManyToManyImpl()
	{
		return xmlManyToManyImplEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollection <em>Xml Basic Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic Collection</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollection
	 * @generated
	 */
	public EClass getXmlBasicCollection()
	{
		return xmlBasicCollectionEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollectionImpl <em>Xml Basic Collection Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic Collection Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollectionImpl
	 * @generated
	 */
	public EClass getXmlBasicCollectionImpl()
	{
		return xmlBasicCollectionImplEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMap <em>Xml Basic Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic Map</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMap
	 * @generated
	 */
	public EClass getXmlBasicMap()
	{
		return xmlBasicMapEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMapImpl <em>Xml Basic Map Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic Map Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMapImpl
	 * @generated
	 */
	public EClass getXmlBasicMapImpl()
	{
		return xmlBasicMapImplEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformation <em>Xml Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Transformation</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformation
	 * @generated
	 */
	public EClass getXmlTransformation()
	{
		return xmlTransformationEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformationImpl <em>Xml Transformation Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Transformation Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformationImpl
	 * @generated
	 */
	public EClass getXmlTransformationImpl()
	{
		return xmlTransformationImplEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOne <em>Xml Variable One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Variable One To One</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOne
	 * @generated
	 */
	public EClass getXmlVariableOneToOne()
	{
		return xmlVariableOneToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOneImpl <em>Xml Variable One To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Variable One To One Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOneImpl
	 * @generated
	 */
	public EClass getXmlVariableOneToOneImpl()
	{
		return xmlVariableOneToOneImplEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransient <em>Xml Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Transient</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransient
	 * @generated
	 */
	public EClass getXmlTransient()
	{
		return xmlTransientEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransientImpl <em>Xml Transient Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Transient Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransientImpl
	 * @generated
	 */
	public EClass getXmlTransientImpl()
	{
		return xmlTransientImplEClass;
	}


	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public EclipseLink1_1OrmFactory getEclipseLink1_1OrmFactory()
	{
		return (EclipseLink1_1OrmFactory)getEFactoryInstance();
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

		xmlAttributeMappingEClass = createEClass(XML_ATTRIBUTE_MAPPING);
		createEAttribute(xmlAttributeMappingEClass, XML_ATTRIBUTE_MAPPING__ACCESS);

		xmlIdEClass = createEClass(XML_ID);

		xmlIdImplEClass = createEClass(XML_ID_IMPL);

		xmlEmbeddedIdEClass = createEClass(XML_EMBEDDED_ID);

		xmlEmbeddedIdImplEClass = createEClass(XML_EMBEDDED_ID_IMPL);

		xmlEmbeddedEClass = createEClass(XML_EMBEDDED);

		xmlEmbeddedImplEClass = createEClass(XML_EMBEDDED_IMPL);

		xmlBasicEClass = createEClass(XML_BASIC);
		createEReference(xmlBasicEClass, XML_BASIC__GENERATED_VALUE);

		xmlBasicImplEClass = createEClass(XML_BASIC_IMPL);

		xmlVersionEClass = createEClass(XML_VERSION);

		xmlVersionImplEClass = createEClass(XML_VERSION_IMPL);

		xmlOneToOneEClass = createEClass(XML_ONE_TO_ONE);

		xmlOneToOneImplEClass = createEClass(XML_ONE_TO_ONE_IMPL);

		xmlOneToManyEClass = createEClass(XML_ONE_TO_MANY);

		xmlOneToManyImplEClass = createEClass(XML_ONE_TO_MANY_IMPL);

		xmlManyToOneEClass = createEClass(XML_MANY_TO_ONE);

		xmlManyToOneImplEClass = createEClass(XML_MANY_TO_ONE_IMPL);

		xmlManyToManyEClass = createEClass(XML_MANY_TO_MANY);

		xmlManyToManyImplEClass = createEClass(XML_MANY_TO_MANY_IMPL);

		xmlBasicCollectionEClass = createEClass(XML_BASIC_COLLECTION);

		xmlBasicCollectionImplEClass = createEClass(XML_BASIC_COLLECTION_IMPL);

		xmlBasicMapEClass = createEClass(XML_BASIC_MAP);

		xmlBasicMapImplEClass = createEClass(XML_BASIC_MAP_IMPL);

		xmlTransformationEClass = createEClass(XML_TRANSFORMATION);

		xmlTransformationImplEClass = createEClass(XML_TRANSFORMATION_IMPL);

		xmlVariableOneToOneEClass = createEClass(XML_VARIABLE_ONE_TO_ONE);

		xmlVariableOneToOneImplEClass = createEClass(XML_VARIABLE_ONE_TO_ONE_IMPL);

		xmlTransientEClass = createEClass(XML_TRANSIENT);

		xmlTransientImplEClass = createEClass(XML_TRANSIENT_IMPL);
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
		EclipseLinkOrmPackage theEclipseLinkOrmPackage = (EclipseLinkOrmPackage)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI);
		OrmPackage theOrmPackage = (OrmPackage)EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		xmlEntityMappingsEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlEntityMappings());
		xmlAttributeMappingEClass.getESuperTypes().add(theOrmPackage.getXmlAttributeMapping());
		xmlIdEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlId());
		xmlIdEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlIdImplEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlIdImpl());
		xmlIdImplEClass.getESuperTypes().add(this.getXmlId());
		xmlEmbeddedIdEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlEmbeddedId());
		xmlEmbeddedIdEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlEmbeddedIdImplEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlEmbeddedIdImpl());
		xmlEmbeddedIdImplEClass.getESuperTypes().add(this.getXmlEmbeddedId());
		xmlEmbeddedEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlEmbedded());
		xmlEmbeddedEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlEmbeddedImplEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlEmbeddedImpl());
		xmlEmbeddedImplEClass.getESuperTypes().add(this.getXmlEmbedded());
		xmlBasicEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlBasic());
		xmlBasicEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlBasicEClass.getESuperTypes().add(theOrmPackage.getXmlGeneratorContainer());
		xmlBasicImplEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlBasicImpl());
		xmlBasicImplEClass.getESuperTypes().add(this.getXmlBasic());
		xmlVersionEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlVersion());
		xmlVersionEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlVersionImplEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlVersionImpl());
		xmlVersionImplEClass.getESuperTypes().add(this.getXmlVersion());
		xmlOneToOneEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlOneToOne());
		xmlOneToOneEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlOneToOneImplEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlOneToOneImpl());
		xmlOneToOneImplEClass.getESuperTypes().add(this.getXmlOneToOne());
		xmlOneToManyEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlOneToMany());
		xmlOneToManyEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlOneToManyImplEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlOneToManyImpl());
		xmlOneToManyImplEClass.getESuperTypes().add(this.getXmlOneToMany());
		xmlManyToOneEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlManyToOne());
		xmlManyToOneEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlManyToOneImplEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlManyToOneImpl());
		xmlManyToOneImplEClass.getESuperTypes().add(this.getXmlManyToOne());
		xmlManyToManyEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlManyToMany());
		xmlManyToManyEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlManyToManyImplEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlManyToManyImpl());
		xmlManyToManyImplEClass.getESuperTypes().add(this.getXmlManyToMany());
		xmlBasicCollectionEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlBasicCollection());
		xmlBasicCollectionEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlBasicCollectionImplEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlBasicCollectionImpl());
		xmlBasicCollectionImplEClass.getESuperTypes().add(this.getXmlBasicCollection());
		xmlBasicMapEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlBasicMap());
		xmlBasicMapEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlBasicMapImplEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlBasicMapImpl());
		xmlBasicMapImplEClass.getESuperTypes().add(this.getXmlBasicMap());
		xmlTransformationEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlTransformation());
		xmlTransformationEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlTransformationImplEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlTransformationImpl());
		xmlTransformationImplEClass.getESuperTypes().add(this.getXmlTransformation());
		xmlVariableOneToOneEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlVariableOneToOne());
		xmlVariableOneToOneEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlVariableOneToOneImplEClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlVariableOneToOneImpl());
		xmlVariableOneToOneImplEClass.getESuperTypes().add(this.getXmlVariableOneToOne());
		xmlTransientEClass.getESuperTypes().add(theOrmPackage.getXmlTransient());
		xmlTransientEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlTransientImplEClass.getESuperTypes().add(theOrmPackage.getXmlTransientImpl());
		xmlTransientImplEClass.getESuperTypes().add(this.getXmlTransient());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlEntityMappingsEClass, XmlEntityMappings.class, "XmlEntityMappings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlAttributeMappingEClass, XmlAttributeMapping.class, "XmlAttributeMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlAttributeMapping_Access(), theOrmPackage.getAccessType(), "access", "PROPERTY", 0, 1, XmlAttributeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlIdEClass, XmlId.class, "XmlId", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlIdImplEClass, XmlIdImpl.class, "XmlIdImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEmbeddedIdEClass, XmlEmbeddedId.class, "XmlEmbeddedId", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEmbeddedIdImplEClass, XmlEmbeddedIdImpl.class, "XmlEmbeddedIdImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEmbeddedEClass, XmlEmbedded.class, "XmlEmbedded", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEmbeddedImplEClass, XmlEmbeddedImpl.class, "XmlEmbeddedImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlBasicEClass, XmlBasic.class, "XmlBasic", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlBasic_GeneratedValue(), theOrmPackage.getXmlGeneratedValue(), null, "generatedValue", null, 0, 1, XmlBasic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlBasicImplEClass, XmlBasicImpl.class, "XmlBasicImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlVersionEClass, XmlVersion.class, "XmlVersion", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlVersionImplEClass, XmlVersionImpl.class, "XmlVersionImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlOneToOneEClass, XmlOneToOne.class, "XmlOneToOne", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlOneToOneImplEClass, XmlOneToOneImpl.class, "XmlOneToOneImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlOneToManyEClass, XmlOneToMany.class, "XmlOneToMany", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlOneToManyImplEClass, XmlOneToManyImpl.class, "XmlOneToManyImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlManyToOneEClass, XmlManyToOne.class, "XmlManyToOne", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlManyToOneImplEClass, XmlManyToOneImpl.class, "XmlManyToOneImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlManyToManyEClass, XmlManyToMany.class, "XmlManyToMany", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlManyToManyImplEClass, XmlManyToManyImpl.class, "XmlManyToManyImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlBasicCollectionEClass, XmlBasicCollection.class, "XmlBasicCollection", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlBasicCollectionImplEClass, XmlBasicCollectionImpl.class, "XmlBasicCollectionImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlBasicMapEClass, XmlBasicMap.class, "XmlBasicMap", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlBasicMapImplEClass, XmlBasicMapImpl.class, "XmlBasicMapImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlTransformationEClass, XmlTransformation.class, "XmlTransformation", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlTransformationImplEClass, XmlTransformationImpl.class, "XmlTransformationImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlVariableOneToOneEClass, XmlVariableOneToOne.class, "XmlVariableOneToOne", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlVariableOneToOneImplEClass, XmlVariableOneToOneImpl.class, "XmlVariableOneToOneImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlTransientEClass, XmlTransient.class, "XmlTransient", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlTransientImplEClass, XmlTransientImpl.class, "XmlTransientImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEntityMappings
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlEntityMappings()
		 * @generated
		 */
		public static final EClass XML_ENTITY_MAPPINGS = eINSTANCE.getXmlEntityMappings();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlAttributeMapping
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlAttributeMapping()
		 * @generated
		 */
		public static final EClass XML_ATTRIBUTE_MAPPING = eINSTANCE.getXmlAttributeMapping();

		/**
		 * The meta object literal for the '<em><b>Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ATTRIBUTE_MAPPING__ACCESS = eINSTANCE.getXmlAttributeMapping_Access();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlId <em>Xml Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlId
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlId()
		 * @generated
		 */
		public static final EClass XML_ID = eINSTANCE.getXmlId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlIdImpl <em>Xml Id Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlIdImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlIdImpl()
		 * @generated
		 */
		public static final EClass XML_ID_IMPL = eINSTANCE.getXmlIdImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedId
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlEmbeddedId()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED_ID = eINSTANCE.getXmlEmbeddedId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedIdImpl <em>Xml Embedded Id Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedIdImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlEmbeddedIdImpl()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED_ID_IMPL = eINSTANCE.getXmlEmbeddedIdImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbedded <em>Xml Embedded</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbedded
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlEmbedded()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED = eINSTANCE.getXmlEmbedded();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedImpl <em>Xml Embedded Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlEmbeddedImpl()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED_IMPL = eINSTANCE.getXmlEmbeddedImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasic <em>Xml Basic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasic
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasic()
		 * @generated
		 */
		public static final EClass XML_BASIC = eINSTANCE.getXmlBasic();

		/**
		 * The meta object literal for the '<em><b>Generated Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_BASIC__GENERATED_VALUE = eINSTANCE.getXmlBasic_GeneratedValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicImpl <em>Xml Basic Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasicImpl()
		 * @generated
		 */
		public static final EClass XML_BASIC_IMPL = eINSTANCE.getXmlBasicImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersion <em>Xml Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersion
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlVersion()
		 * @generated
		 */
		public static final EClass XML_VERSION = eINSTANCE.getXmlVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersionImpl <em>Xml Version Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersionImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlVersionImpl()
		 * @generated
		 */
		public static final EClass XML_VERSION_IMPL = eINSTANCE.getXmlVersionImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOne
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlOneToOne()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_ONE = eINSTANCE.getXmlOneToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOneImpl <em>Xml One To One Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOneImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlOneToOneImpl()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_ONE_IMPL = eINSTANCE.getXmlOneToOneImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToMany
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlOneToMany()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_MANY = eINSTANCE.getXmlOneToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToManyImpl <em>Xml One To Many Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToManyImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlOneToManyImpl()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_MANY_IMPL = eINSTANCE.getXmlOneToManyImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOne
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlManyToOne()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_ONE = eINSTANCE.getXmlManyToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOneImpl <em>Xml Many To One Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOneImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlManyToOneImpl()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_ONE_IMPL = eINSTANCE.getXmlManyToOneImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToMany
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlManyToMany()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_MANY = eINSTANCE.getXmlManyToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToManyImpl <em>Xml Many To Many Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToManyImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlManyToManyImpl()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_MANY_IMPL = eINSTANCE.getXmlManyToManyImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollection <em>Xml Basic Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollection
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasicCollection()
		 * @generated
		 */
		public static final EClass XML_BASIC_COLLECTION = eINSTANCE.getXmlBasicCollection();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollectionImpl <em>Xml Basic Collection Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollectionImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasicCollectionImpl()
		 * @generated
		 */
		public static final EClass XML_BASIC_COLLECTION_IMPL = eINSTANCE.getXmlBasicCollectionImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMap <em>Xml Basic Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMap
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasicMap()
		 * @generated
		 */
		public static final EClass XML_BASIC_MAP = eINSTANCE.getXmlBasicMap();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMapImpl <em>Xml Basic Map Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMapImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlBasicMapImpl()
		 * @generated
		 */
		public static final EClass XML_BASIC_MAP_IMPL = eINSTANCE.getXmlBasicMapImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformation <em>Xml Transformation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformation
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlTransformation()
		 * @generated
		 */
		public static final EClass XML_TRANSFORMATION = eINSTANCE.getXmlTransformation();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformationImpl <em>Xml Transformation Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformationImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlTransformationImpl()
		 * @generated
		 */
		public static final EClass XML_TRANSFORMATION_IMPL = eINSTANCE.getXmlTransformationImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOne <em>Xml Variable One To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOne
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlVariableOneToOne()
		 * @generated
		 */
		public static final EClass XML_VARIABLE_ONE_TO_ONE = eINSTANCE.getXmlVariableOneToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOneImpl <em>Xml Variable One To One Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOneImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlVariableOneToOneImpl()
		 * @generated
		 */
		public static final EClass XML_VARIABLE_ONE_TO_ONE_IMPL = eINSTANCE.getXmlVariableOneToOneImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransient <em>Xml Transient</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransient
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlTransient()
		 * @generated
		 */
		public static final EClass XML_TRANSIENT = eINSTANCE.getXmlTransient();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransientImpl <em>Xml Transient Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransientImpl
		 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlTransientImpl()
		 * @generated
		 */
		public static final EClass XML_TRANSIENT_IMPL = eINSTANCE.getXmlTransientImpl();

	}

} //EclipseLink1_1OrmPackage
