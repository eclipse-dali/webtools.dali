/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;

import org.eclipse.jpt.jpa.core.resource.xml.CommonPackage;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package;

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
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Factory
 * @model kind="package"
 * @generated
 */
public class EclipseLinkOrmV2_3Package extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "v2_3";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.eclipselink.orm.v2_3.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLinkOrmV2_3Package eINSTANCE = org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3 <em>Xml Embeddable 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEmbeddable_2_3()
	 * @generated
	 */
	public static final int XML_EMBEDDABLE_23 = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenant_2_3 <em>Xml Multitenant 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenant_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMultitenant_2_3()
	 * @generated
	 */
	public static final int XML_MULTITENANT_23 = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3 <em>Xml Entity 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEntity_2_3()
	 * @generated
	 */
	public static final int XML_ENTITY_23 = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3 <em>Xml Mapped Superclass 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMappedSuperclass_2_3()
	 * @generated
	 */
	public static final int XML_MAPPED_SUPERCLASS_23 = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3 <em>Xml Entity Mappings 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEntityMappings_2_3()
	 * @generated
	 */
	public static final int XML_ENTITY_MAPPINGS_23 = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredFunctionQuery_2_3 <em>Xml Named Plsql Stored Function Query 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredFunctionQuery_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlNamedPlsqlStoredFunctionQuery_2_3()
	 * @generated
	 */
	public static final int XML_NAMED_PLSQL_STORED_FUNCTION_QUERY_23 = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredProcedureQuery_2_3 <em>Xml Named Plsql Stored Procedure Query 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredProcedureQuery_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlNamedPlsqlStoredProcedureQuery_2_3()
	 * @generated
	 */
	public static final int XML_NAMED_PLSQL_STORED_PROCEDURE_QUERY_23 = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedStoredFunctionQuery_2_3 <em>Xml Named Stored Function Query 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedStoredFunctionQuery_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlNamedStoredFunctionQuery_2_3()
	 * @generated
	 */
	public static final int XML_NAMED_STORED_FUNCTION_QUERY_23 = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPersistenceUnitDefaults_2_3 <em>Xml Persistence Unit Defaults 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPersistenceUnitDefaults_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlPersistenceUnitDefaults_2_3()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS_23 = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlRecord_2_3 <em>Xml Plsql Record 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlRecord_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlPlsqlRecord_2_3()
	 * @generated
	 */
	public static final int XML_PLSQL_RECORD_23 = 11;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlTable_2_3 <em>Xml Plsql Table 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlTable_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlPlsqlTable_2_3()
	 * @generated
	 */
	public static final int XML_PLSQL_TABLE_23 = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStruct_2_3 <em>Xml Struct 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStruct_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlStruct_2_3()
	 * @generated
	 */
	public static final int XML_STRUCT_23 = 13;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3 <em>Xml Attributes 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlAttributes_2_3()
	 * @generated
	 */
	public static final int XML_ATTRIBUTES_23 = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStructure_2_3 <em>Xml Structure 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStructure_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlStructure_2_3()
	 * @generated
	 */
	public static final int XML_STRUCTURE_23 = 15;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlArray_2_3 <em>Xml Array 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlArray_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlArray_2_3()
	 * @generated
	 */
	public static final int XML_ARRAY_23 = 0;

	/**
	 * The number of structural features of the '<em>Xml Array 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ARRAY_23_FEATURE_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Structures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTES_23__STRUCTURES = 0;

	/**
	 * The feature id for the '<em><b>Arrays</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTES_23__ARRAYS = 1;

	/**
	 * The number of structural features of the '<em>Xml Attributes 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTES_23_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Plsql Records</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_23__PLSQL_RECORDS = 0;

	/**
	 * The feature id for the '<em><b>Plsql Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_23__PLSQL_TABLES = 1;

	/**
	 * The feature id for the '<em><b>Struct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_23__STRUCT = 2;

	/**
	 * The number of structural features of the '<em>Xml Embeddable 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_23_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Multitenant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_23__MULTITENANT = 0;

	/**
	 * The feature id for the '<em><b>Named Stored Function Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_23__NAMED_STORED_FUNCTION_QUERIES = 1;

	/**
	 * The feature id for the '<em><b>Named Plsql Stored Function Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_23__NAMED_PLSQL_STORED_FUNCTION_QUERIES = 2;

	/**
	 * The feature id for the '<em><b>Named Plsql Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_23__NAMED_PLSQL_STORED_PROCEDURE_QUERIES = 3;

	/**
	 * The feature id for the '<em><b>Plsql Records</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_23__PLSQL_RECORDS = 4;

	/**
	 * The feature id for the '<em><b>Plsql Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_23__PLSQL_TABLES = 5;

	/**
	 * The feature id for the '<em><b>Struct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_23__STRUCT = 6;

	/**
	 * The number of structural features of the '<em>Xml Entity 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_23_FEATURE_COUNT = 7;

	/**
	 * The feature id for the '<em><b>Tenant Discriminator Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_23__TENANT_DISCRIMINATOR_COLUMNS = 0;

	/**
	 * The feature id for the '<em><b>Named Stored Function Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_23__NAMED_STORED_FUNCTION_QUERIES = 1;

	/**
	 * The feature id for the '<em><b>Named Plsql Stored Function Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_23__NAMED_PLSQL_STORED_FUNCTION_QUERIES = 2;

	/**
	 * The feature id for the '<em><b>Named Plsql Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_23__NAMED_PLSQL_STORED_PROCEDURE_QUERIES = 3;

	/**
	 * The feature id for the '<em><b>Plsql Records</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_23__PLSQL_RECORDS = 4;

	/**
	 * The feature id for the '<em><b>Plsql Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_23__PLSQL_TABLES = 5;

	/**
	 * The number of structural features of the '<em>Xml Entity Mappings 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_23_FEATURE_COUNT = 6;

	/**
	 * The feature id for the '<em><b>Multitenant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_23__MULTITENANT = 0;

	/**
	 * The feature id for the '<em><b>Named Stored Function Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_23__NAMED_STORED_FUNCTION_QUERIES = 1;

	/**
	 * The feature id for the '<em><b>Named Plsql Stored Function Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_23__NAMED_PLSQL_STORED_FUNCTION_QUERIES = 2;

	/**
	 * The feature id for the '<em><b>Named Plsql Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_23__NAMED_PLSQL_STORED_PROCEDURE_QUERIES = 3;

	/**
	 * The feature id for the '<em><b>Plsql Records</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_23__PLSQL_RECORDS = 4;

	/**
	 * The feature id for the '<em><b>Plsql Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_23__PLSQL_TABLES = 5;

	/**
	 * The number of structural features of the '<em>Xml Mapped Superclass 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_23_FEATURE_COUNT = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTITENANT_23__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Tenant Discriminator Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTITENANT_23__TENANT_DISCRIMINATOR_COLUMNS = 1;

	/**
	 * The number of structural features of the '<em>Xml Multitenant 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTITENANT_23_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_PLSQL_STORED_FUNCTION_QUERY_23__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Named Plsql Stored Function Query 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_PLSQL_STORED_FUNCTION_QUERY_23_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_PLSQL_STORED_PROCEDURE_QUERY_23__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Named Plsql Stored Procedure Query 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_PLSQL_STORED_PROCEDURE_QUERY_23_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_FUNCTION_QUERY_23__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Named Stored Function Query 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_FUNCTION_QUERY_23_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Tenant Discriminator Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS_23__TENANT_DISCRIMINATOR_COLUMNS = 0;

	/**
	 * The number of structural features of the '<em>Xml Persistence Unit Defaults 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS_23_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PLSQL_RECORD_23__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Plsql Record 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PLSQL_RECORD_23_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PLSQL_TABLE_23__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Plsql Table 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PLSQL_TABLE_23_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCT_23__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Struct 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCT_23_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3 <em>Xml Tenant Discriminator Column 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlTenantDiscriminatorColumn_2_3()
	 * @generated
	 */
	public static final int XML_TENANT_DISCRIMINATOR_COLUMN_23 = 14;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TENANT_DISCRIMINATOR_COLUMN_23__COLUMN_DEFINITION = OrmPackage.ABSTRACT_XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TENANT_DISCRIMINATOR_COLUMN_23__NAME = OrmPackage.ABSTRACT_XML_DISCRIMINATOR_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Discriminator Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TENANT_DISCRIMINATOR_COLUMN_23__DISCRIMINATOR_TYPE = OrmPackage.ABSTRACT_XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TENANT_DISCRIMINATOR_COLUMN_23__LENGTH = OrmPackage.ABSTRACT_XML_DISCRIMINATOR_COLUMN__LENGTH;

	/**
	 * The feature id for the '<em><b>Context Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TENANT_DISCRIMINATOR_COLUMN_23__CONTEXT_PROPERTY = OrmPackage.ABSTRACT_XML_DISCRIMINATOR_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TENANT_DISCRIMINATOR_COLUMN_23__TABLE = OrmPackage.ABSTRACT_XML_DISCRIMINATOR_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Primary Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TENANT_DISCRIMINATOR_COLUMN_23__PRIMARY_KEY = OrmPackage.ABSTRACT_XML_DISCRIMINATOR_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Tenant Discriminator Column 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TENANT_DISCRIMINATOR_COLUMN_23_FEATURE_COUNT = OrmPackage.ABSTRACT_XML_DISCRIMINATOR_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Xml Structure 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCTURE_23_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlElementCollection_2_3 <em>Xml Element Collection 23</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlElementCollection_2_3
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlElementCollection_2_3()
	 * @generated
	 */
	public static final int XML_ELEMENT_COLLECTION_23 = 16;

	/**
	 * The feature id for the '<em><b>Composite Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_23__COMPOSITE_MEMBER = 0;

	/**
	 * The number of structural features of the '<em>Xml Element Collection 23</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_23_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenantType <em>Xml Multitenant Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenantType
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMultitenantType()
	 * @generated
	 */
	public static final int XML_MULTITENANT_TYPE = 17;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEmbeddable_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMultitenant_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedPlsqlStoredFunctionQuery_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedPlsqlStoredProcedureQuery_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedStoredFunctionQuery_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntity_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMappedSuperclass_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntityMappings_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPersistenceUnitDefaults_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPlsqlRecord_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPlsqlTable_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlStruct_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTenantDiscriminatorColumn_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAttributes_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlStructure_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlArray_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlElementCollection_2_3EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum xmlMultitenantTypeEEnum = null;

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EclipseLinkOrmV2_3Package()
	{
		super(eNS_URI, EclipseLinkOrmV2_3Factory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EclipseLinkOrmV2_3Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EclipseLinkOrmV2_3Package init()
	{
		if (isInited) return (EclipseLinkOrmV2_3Package)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_3Package.eNS_URI);

		// Obtain or create and register package
		EclipseLinkOrmV2_3Package theEclipseLinkOrmV2_3Package = (EclipseLinkOrmV2_3Package)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EclipseLinkOrmV2_3Package ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EclipseLinkOrmV2_3Package());

		isInited = true;

		// Initialize simple dependencies
		OrmPackage.eINSTANCE.eClass();
		CommonPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		EclipseLinkOrmPackage theEclipseLinkOrmPackage = (EclipseLinkOrmPackage)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) instanceof EclipseLinkOrmPackage ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) : EclipseLinkOrmPackage.eINSTANCE);
		EclipseLinkOrmV1_1Package theEclipseLinkOrmV1_1Package = (EclipseLinkOrmV1_1Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI) instanceof EclipseLinkOrmV1_1Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI) : EclipseLinkOrmV1_1Package.eINSTANCE);
		EclipseLinkOrmV2_0Package theEclipseLinkOrmV2_0Package = (EclipseLinkOrmV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) instanceof EclipseLinkOrmV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) : EclipseLinkOrmV2_0Package.eINSTANCE);
		EclipseLinkOrmV2_1Package theEclipseLinkOrmV2_1Package = (EclipseLinkOrmV2_1Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI) instanceof EclipseLinkOrmV2_1Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI) : EclipseLinkOrmV2_1Package.eINSTANCE);
		EclipseLinkOrmV2_2Package theEclipseLinkOrmV2_2Package = (EclipseLinkOrmV2_2Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) instanceof EclipseLinkOrmV2_2Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) : EclipseLinkOrmV2_2Package.eINSTANCE);

		// Create package meta-data objects
		theEclipseLinkOrmV2_3Package.createPackageContents();
		theEclipseLinkOrmPackage.createPackageContents();
		theEclipseLinkOrmV1_1Package.createPackageContents();
		theEclipseLinkOrmV2_0Package.createPackageContents();
		theEclipseLinkOrmV2_1Package.createPackageContents();
		theEclipseLinkOrmV2_2Package.createPackageContents();

		// Initialize created meta-data
		theEclipseLinkOrmV2_3Package.initializePackageContents();
		theEclipseLinkOrmPackage.initializePackageContents();
		theEclipseLinkOrmV1_1Package.initializePackageContents();
		theEclipseLinkOrmV2_0Package.initializePackageContents();
		theEclipseLinkOrmV2_1Package.initializePackageContents();
		theEclipseLinkOrmV2_2Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEclipseLinkOrmV2_3Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EclipseLinkOrmV2_3Package.eNS_URI, theEclipseLinkOrmV2_3Package);
		return theEclipseLinkOrmV2_3Package;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3 <em>Xml Embeddable 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embeddable 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3
	 * @generated
	 */
	public EClass getXmlEmbeddable_2_3()
	{
		return xmlEmbeddable_2_3EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3#getPlsqlRecords <em>Plsql Records</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plsql Records</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3#getPlsqlRecords()
	 * @see #getXmlEmbeddable_2_3()
	 * @generated
	 */
	public EReference getXmlEmbeddable_2_3_PlsqlRecords()
	{
		return (EReference)xmlEmbeddable_2_3EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3#getPlsqlTables <em>Plsql Tables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plsql Tables</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3#getPlsqlTables()
	 * @see #getXmlEmbeddable_2_3()
	 * @generated
	 */
	public EReference getXmlEmbeddable_2_3_PlsqlTables()
	{
		return (EReference)xmlEmbeddable_2_3EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3#getStruct <em>Struct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Struct</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3#getStruct()
	 * @see #getXmlEmbeddable_2_3()
	 * @generated
	 */
	public EReference getXmlEmbeddable_2_3_Struct()
	{
		return (EReference)xmlEmbeddable_2_3EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenant_2_3 <em>Xml Multitenant 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Multitenant 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenant_2_3
	 * @generated
	 */
	public EClass getXmlMultitenant_2_3()
	{
		return xmlMultitenant_2_3EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenant_2_3#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenant_2_3#getType()
	 * @see #getXmlMultitenant_2_3()
	 * @generated
	 */
	public EAttribute getXmlMultitenant_2_3_Type()
	{
		return (EAttribute)xmlMultitenant_2_3EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenant_2_3#getTenantDiscriminatorColumns <em>Tenant Discriminator Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tenant Discriminator Columns</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenant_2_3#getTenantDiscriminatorColumns()
	 * @see #getXmlMultitenant_2_3()
	 * @generated
	 */
	public EReference getXmlMultitenant_2_3_TenantDiscriminatorColumns()
	{
		return (EReference)xmlMultitenant_2_3EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredFunctionQuery_2_3 <em>Xml Named Plsql Stored Function Query 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Plsql Stored Function Query 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredFunctionQuery_2_3
	 * @generated
	 */
	public EClass getXmlNamedPlsqlStoredFunctionQuery_2_3()
	{
		return xmlNamedPlsqlStoredFunctionQuery_2_3EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredFunctionQuery_2_3#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredFunctionQuery_2_3#getName()
	 * @see #getXmlNamedPlsqlStoredFunctionQuery_2_3()
	 * @generated
	 */
	public EAttribute getXmlNamedPlsqlStoredFunctionQuery_2_3_Name()
	{
		return (EAttribute)xmlNamedPlsqlStoredFunctionQuery_2_3EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredProcedureQuery_2_3 <em>Xml Named Plsql Stored Procedure Query 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Plsql Stored Procedure Query 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredProcedureQuery_2_3
	 * @generated
	 */
	public EClass getXmlNamedPlsqlStoredProcedureQuery_2_3()
	{
		return xmlNamedPlsqlStoredProcedureQuery_2_3EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredProcedureQuery_2_3#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredProcedureQuery_2_3#getName()
	 * @see #getXmlNamedPlsqlStoredProcedureQuery_2_3()
	 * @generated
	 */
	public EAttribute getXmlNamedPlsqlStoredProcedureQuery_2_3_Name()
	{
		return (EAttribute)xmlNamedPlsqlStoredProcedureQuery_2_3EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedStoredFunctionQuery_2_3 <em>Xml Named Stored Function Query 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Stored Function Query 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedStoredFunctionQuery_2_3
	 * @generated
	 */
	public EClass getXmlNamedStoredFunctionQuery_2_3()
	{
		return xmlNamedStoredFunctionQuery_2_3EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedStoredFunctionQuery_2_3#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedStoredFunctionQuery_2_3#getName()
	 * @see #getXmlNamedStoredFunctionQuery_2_3()
	 * @generated
	 */
	public EAttribute getXmlNamedStoredFunctionQuery_2_3_Name()
	{
		return (EAttribute)xmlNamedStoredFunctionQuery_2_3EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3 <em>Xml Entity 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3
	 * @generated
	 */
	public EClass getXmlEntity_2_3()
	{
		return xmlEntity_2_3EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getMultitenant <em>Multitenant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Multitenant</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getMultitenant()
	 * @see #getXmlEntity_2_3()
	 * @generated
	 */
	public EReference getXmlEntity_2_3_Multitenant()
	{
		return (EReference)xmlEntity_2_3EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getNamedStoredFunctionQueries <em>Named Stored Function Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Stored Function Queries</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getNamedStoredFunctionQueries()
	 * @see #getXmlEntity_2_3()
	 * @generated
	 */
	public EReference getXmlEntity_2_3_NamedStoredFunctionQueries()
	{
		return (EReference)xmlEntity_2_3EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getNamedPlsqlStoredFunctionQueries <em>Named Plsql Stored Function Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Plsql Stored Function Queries</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getNamedPlsqlStoredFunctionQueries()
	 * @see #getXmlEntity_2_3()
	 * @generated
	 */
	public EReference getXmlEntity_2_3_NamedPlsqlStoredFunctionQueries()
	{
		return (EReference)xmlEntity_2_3EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getNamedPlsqlStoredProcedureQueries <em>Named Plsql Stored Procedure Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Plsql Stored Procedure Queries</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getNamedPlsqlStoredProcedureQueries()
	 * @see #getXmlEntity_2_3()
	 * @generated
	 */
	public EReference getXmlEntity_2_3_NamedPlsqlStoredProcedureQueries()
	{
		return (EReference)xmlEntity_2_3EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getPlsqlRecords <em>Plsql Records</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plsql Records</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getPlsqlRecords()
	 * @see #getXmlEntity_2_3()
	 * @generated
	 */
	public EReference getXmlEntity_2_3_PlsqlRecords()
	{
		return (EReference)xmlEntity_2_3EClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getPlsqlTables <em>Plsql Tables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plsql Tables</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getPlsqlTables()
	 * @see #getXmlEntity_2_3()
	 * @generated
	 */
	public EReference getXmlEntity_2_3_PlsqlTables()
	{
		return (EReference)xmlEntity_2_3EClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getStruct <em>Struct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Struct</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3#getStruct()
	 * @see #getXmlEntity_2_3()
	 * @generated
	 */
	public EReference getXmlEntity_2_3_Struct()
	{
		return (EReference)xmlEntity_2_3EClass.getEStructuralFeatures().get(6);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3 <em>Xml Mapped Superclass 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mapped Superclass 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3
	 * @generated
	 */
	public EClass getXmlMappedSuperclass_2_3()
	{
		return xmlMappedSuperclass_2_3EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getMultitenant <em>Multitenant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Multitenant</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getMultitenant()
	 * @see #getXmlMappedSuperclass_2_3()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_2_3_Multitenant()
	{
		return (EReference)xmlMappedSuperclass_2_3EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getNamedStoredFunctionQueries <em>Named Stored Function Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Stored Function Queries</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getNamedStoredFunctionQueries()
	 * @see #getXmlMappedSuperclass_2_3()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_2_3_NamedStoredFunctionQueries()
	{
		return (EReference)xmlMappedSuperclass_2_3EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getNamedPlsqlStoredFunctionQueries <em>Named Plsql Stored Function Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Plsql Stored Function Queries</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getNamedPlsqlStoredFunctionQueries()
	 * @see #getXmlMappedSuperclass_2_3()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_2_3_NamedPlsqlStoredFunctionQueries()
	{
		return (EReference)xmlMappedSuperclass_2_3EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getNamedPlsqlStoredProcedureQueries <em>Named Plsql Stored Procedure Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Plsql Stored Procedure Queries</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getNamedPlsqlStoredProcedureQueries()
	 * @see #getXmlMappedSuperclass_2_3()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_2_3_NamedPlsqlStoredProcedureQueries()
	{
		return (EReference)xmlMappedSuperclass_2_3EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getPlsqlRecords <em>Plsql Records</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plsql Records</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getPlsqlRecords()
	 * @see #getXmlMappedSuperclass_2_3()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_2_3_PlsqlRecords()
	{
		return (EReference)xmlMappedSuperclass_2_3EClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getPlsqlTables <em>Plsql Tables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plsql Tables</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getPlsqlTables()
	 * @see #getXmlMappedSuperclass_2_3()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_2_3_PlsqlTables()
	{
		return (EReference)xmlMappedSuperclass_2_3EClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3 <em>Xml Entity Mappings 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity Mappings 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3
	 * @generated
	 */
	public EClass getXmlEntityMappings_2_3()
	{
		return xmlEntityMappings_2_3EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getTenantDiscriminatorColumns <em>Tenant Discriminator Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tenant Discriminator Columns</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getTenantDiscriminatorColumns()
	 * @see #getXmlEntityMappings_2_3()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_3_TenantDiscriminatorColumns()
	{
		return (EReference)xmlEntityMappings_2_3EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getNamedStoredFunctionQueries <em>Named Stored Function Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Stored Function Queries</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getNamedStoredFunctionQueries()
	 * @see #getXmlEntityMappings_2_3()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_3_NamedStoredFunctionQueries()
	{
		return (EReference)xmlEntityMappings_2_3EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getNamedPlsqlStoredFunctionQueries <em>Named Plsql Stored Function Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Plsql Stored Function Queries</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getNamedPlsqlStoredFunctionQueries()
	 * @see #getXmlEntityMappings_2_3()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_3_NamedPlsqlStoredFunctionQueries()
	{
		return (EReference)xmlEntityMappings_2_3EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getNamedPlsqlStoredProcedureQueries <em>Named Plsql Stored Procedure Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Plsql Stored Procedure Queries</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getNamedPlsqlStoredProcedureQueries()
	 * @see #getXmlEntityMappings_2_3()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_3_NamedPlsqlStoredProcedureQueries()
	{
		return (EReference)xmlEntityMappings_2_3EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getPlsqlRecords <em>Plsql Records</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plsql Records</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getPlsqlRecords()
	 * @see #getXmlEntityMappings_2_3()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_3_PlsqlRecords()
	{
		return (EReference)xmlEntityMappings_2_3EClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getPlsqlTables <em>Plsql Tables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plsql Tables</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getPlsqlTables()
	 * @see #getXmlEntityMappings_2_3()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_3_PlsqlTables()
	{
		return (EReference)xmlEntityMappings_2_3EClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPersistenceUnitDefaults_2_3 <em>Xml Persistence Unit Defaults 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence Unit Defaults 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPersistenceUnitDefaults_2_3
	 * @generated
	 */
	public EClass getXmlPersistenceUnitDefaults_2_3()
	{
		return xmlPersistenceUnitDefaults_2_3EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPersistenceUnitDefaults_2_3#getTenantDiscriminatorColumns <em>Tenant Discriminator Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tenant Discriminator Columns</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPersistenceUnitDefaults_2_3#getTenantDiscriminatorColumns()
	 * @see #getXmlPersistenceUnitDefaults_2_3()
	 * @generated
	 */
	public EReference getXmlPersistenceUnitDefaults_2_3_TenantDiscriminatorColumns()
	{
		return (EReference)xmlPersistenceUnitDefaults_2_3EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlRecord_2_3 <em>Xml Plsql Record 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Plsql Record 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlRecord_2_3
	 * @generated
	 */
	public EClass getXmlPlsqlRecord_2_3()
	{
		return xmlPlsqlRecord_2_3EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlRecord_2_3#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlRecord_2_3#getName()
	 * @see #getXmlPlsqlRecord_2_3()
	 * @generated
	 */
	public EAttribute getXmlPlsqlRecord_2_3_Name()
	{
		return (EAttribute)xmlPlsqlRecord_2_3EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlTable_2_3 <em>Xml Plsql Table 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Plsql Table 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlTable_2_3
	 * @generated
	 */
	public EClass getXmlPlsqlTable_2_3()
	{
		return xmlPlsqlTable_2_3EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlTable_2_3#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlTable_2_3#getName()
	 * @see #getXmlPlsqlTable_2_3()
	 * @generated
	 */
	public EAttribute getXmlPlsqlTable_2_3_Name()
	{
		return (EAttribute)xmlPlsqlTable_2_3EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStruct_2_3 <em>Xml Struct 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Struct 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStruct_2_3
	 * @generated
	 */
	public EClass getXmlStruct_2_3()
	{
		return xmlStruct_2_3EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStruct_2_3#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStruct_2_3#getName()
	 * @see #getXmlStruct_2_3()
	 * @generated
	 */
	public EAttribute getXmlStruct_2_3_Name()
	{
		return (EAttribute)xmlStruct_2_3EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3 <em>Xml Tenant Discriminator Column 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Tenant Discriminator Column 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3
	 * @generated
	 */
	public EClass getXmlTenantDiscriminatorColumn_2_3()
	{
		return xmlTenantDiscriminatorColumn_2_3EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3#getContextProperty <em>Context Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Context Property</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3#getContextProperty()
	 * @see #getXmlTenantDiscriminatorColumn_2_3()
	 * @generated
	 */
	public EAttribute getXmlTenantDiscriminatorColumn_2_3_ContextProperty()
	{
		return (EAttribute)xmlTenantDiscriminatorColumn_2_3EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Table</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3#getTable()
	 * @see #getXmlTenantDiscriminatorColumn_2_3()
	 * @generated
	 */
	public EAttribute getXmlTenantDiscriminatorColumn_2_3_Table()
	{
		return (EAttribute)xmlTenantDiscriminatorColumn_2_3EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3#getPrimaryKey <em>Primary Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primary Key</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3#getPrimaryKey()
	 * @see #getXmlTenantDiscriminatorColumn_2_3()
	 * @generated
	 */
	public EAttribute getXmlTenantDiscriminatorColumn_2_3_PrimaryKey()
	{
		return (EAttribute)xmlTenantDiscriminatorColumn_2_3EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3 <em>Xml Attributes 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Attributes 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3
	 * @generated
	 */
	public EClass getXmlAttributes_2_3()
	{
		return xmlAttributes_2_3EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3#getStructures <em>Structures</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Structures</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3#getStructures()
	 * @see #getXmlAttributes_2_3()
	 * @generated
	 */
	public EReference getXmlAttributes_2_3_Structures()
	{
		return (EReference)xmlAttributes_2_3EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3#getArrays <em>Arrays</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Arrays</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3#getArrays()
	 * @see #getXmlAttributes_2_3()
	 * @generated
	 */
	public EReference getXmlAttributes_2_3_Arrays()
	{
		return (EReference)xmlAttributes_2_3EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStructure_2_3 <em>Xml Structure 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Structure 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStructure_2_3
	 * @generated
	 */
	public EClass getXmlStructure_2_3()
	{
		return xmlStructure_2_3EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlArray_2_3 <em>Xml Array 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Array 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlArray_2_3
	 * @generated
	 */
	public EClass getXmlArray_2_3()
	{
		return xmlArray_2_3EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlElementCollection_2_3 <em>Xml Element Collection 23</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Element Collection 23</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlElementCollection_2_3
	 * @generated
	 */
	public EClass getXmlElementCollection_2_3()
	{
		return xmlElementCollection_2_3EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlElementCollection_2_3#getCompositeMember <em>Composite Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Composite Member</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlElementCollection_2_3#getCompositeMember()
	 * @see #getXmlElementCollection_2_3()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_2_3_CompositeMember()
	{
		return (EAttribute)xmlElementCollection_2_3EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenantType <em>Xml Multitenant Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Xml Multitenant Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenantType
	 * @generated
	 */
	public EEnum getXmlMultitenantType()
	{
		return xmlMultitenantTypeEEnum;
	}


	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public EclipseLinkOrmV2_3Factory getEclipseLinkOrmV2_3Factory()
	{
		return (EclipseLinkOrmV2_3Factory)getEFactoryInstance();
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
		xmlArray_2_3EClass = createEClass(XML_ARRAY_23);

		xmlAttributes_2_3EClass = createEClass(XML_ATTRIBUTES_23);
		createEReference(xmlAttributes_2_3EClass, XML_ATTRIBUTES_23__STRUCTURES);
		createEReference(xmlAttributes_2_3EClass, XML_ATTRIBUTES_23__ARRAYS);

		xmlEmbeddable_2_3EClass = createEClass(XML_EMBEDDABLE_23);
		createEReference(xmlEmbeddable_2_3EClass, XML_EMBEDDABLE_23__PLSQL_RECORDS);
		createEReference(xmlEmbeddable_2_3EClass, XML_EMBEDDABLE_23__PLSQL_TABLES);
		createEReference(xmlEmbeddable_2_3EClass, XML_EMBEDDABLE_23__STRUCT);

		xmlEntity_2_3EClass = createEClass(XML_ENTITY_23);
		createEReference(xmlEntity_2_3EClass, XML_ENTITY_23__MULTITENANT);
		createEReference(xmlEntity_2_3EClass, XML_ENTITY_23__NAMED_STORED_FUNCTION_QUERIES);
		createEReference(xmlEntity_2_3EClass, XML_ENTITY_23__NAMED_PLSQL_STORED_FUNCTION_QUERIES);
		createEReference(xmlEntity_2_3EClass, XML_ENTITY_23__NAMED_PLSQL_STORED_PROCEDURE_QUERIES);
		createEReference(xmlEntity_2_3EClass, XML_ENTITY_23__PLSQL_RECORDS);
		createEReference(xmlEntity_2_3EClass, XML_ENTITY_23__PLSQL_TABLES);
		createEReference(xmlEntity_2_3EClass, XML_ENTITY_23__STRUCT);

		xmlEntityMappings_2_3EClass = createEClass(XML_ENTITY_MAPPINGS_23);
		createEReference(xmlEntityMappings_2_3EClass, XML_ENTITY_MAPPINGS_23__TENANT_DISCRIMINATOR_COLUMNS);
		createEReference(xmlEntityMappings_2_3EClass, XML_ENTITY_MAPPINGS_23__NAMED_STORED_FUNCTION_QUERIES);
		createEReference(xmlEntityMappings_2_3EClass, XML_ENTITY_MAPPINGS_23__NAMED_PLSQL_STORED_FUNCTION_QUERIES);
		createEReference(xmlEntityMappings_2_3EClass, XML_ENTITY_MAPPINGS_23__NAMED_PLSQL_STORED_PROCEDURE_QUERIES);
		createEReference(xmlEntityMappings_2_3EClass, XML_ENTITY_MAPPINGS_23__PLSQL_RECORDS);
		createEReference(xmlEntityMappings_2_3EClass, XML_ENTITY_MAPPINGS_23__PLSQL_TABLES);

		xmlMappedSuperclass_2_3EClass = createEClass(XML_MAPPED_SUPERCLASS_23);
		createEReference(xmlMappedSuperclass_2_3EClass, XML_MAPPED_SUPERCLASS_23__MULTITENANT);
		createEReference(xmlMappedSuperclass_2_3EClass, XML_MAPPED_SUPERCLASS_23__NAMED_STORED_FUNCTION_QUERIES);
		createEReference(xmlMappedSuperclass_2_3EClass, XML_MAPPED_SUPERCLASS_23__NAMED_PLSQL_STORED_FUNCTION_QUERIES);
		createEReference(xmlMappedSuperclass_2_3EClass, XML_MAPPED_SUPERCLASS_23__NAMED_PLSQL_STORED_PROCEDURE_QUERIES);
		createEReference(xmlMappedSuperclass_2_3EClass, XML_MAPPED_SUPERCLASS_23__PLSQL_RECORDS);
		createEReference(xmlMappedSuperclass_2_3EClass, XML_MAPPED_SUPERCLASS_23__PLSQL_TABLES);

		xmlMultitenant_2_3EClass = createEClass(XML_MULTITENANT_23);
		createEAttribute(xmlMultitenant_2_3EClass, XML_MULTITENANT_23__TYPE);
		createEReference(xmlMultitenant_2_3EClass, XML_MULTITENANT_23__TENANT_DISCRIMINATOR_COLUMNS);

		xmlNamedPlsqlStoredFunctionQuery_2_3EClass = createEClass(XML_NAMED_PLSQL_STORED_FUNCTION_QUERY_23);
		createEAttribute(xmlNamedPlsqlStoredFunctionQuery_2_3EClass, XML_NAMED_PLSQL_STORED_FUNCTION_QUERY_23__NAME);

		xmlNamedPlsqlStoredProcedureQuery_2_3EClass = createEClass(XML_NAMED_PLSQL_STORED_PROCEDURE_QUERY_23);
		createEAttribute(xmlNamedPlsqlStoredProcedureQuery_2_3EClass, XML_NAMED_PLSQL_STORED_PROCEDURE_QUERY_23__NAME);

		xmlNamedStoredFunctionQuery_2_3EClass = createEClass(XML_NAMED_STORED_FUNCTION_QUERY_23);
		createEAttribute(xmlNamedStoredFunctionQuery_2_3EClass, XML_NAMED_STORED_FUNCTION_QUERY_23__NAME);

		xmlPersistenceUnitDefaults_2_3EClass = createEClass(XML_PERSISTENCE_UNIT_DEFAULTS_23);
		createEReference(xmlPersistenceUnitDefaults_2_3EClass, XML_PERSISTENCE_UNIT_DEFAULTS_23__TENANT_DISCRIMINATOR_COLUMNS);

		xmlPlsqlRecord_2_3EClass = createEClass(XML_PLSQL_RECORD_23);
		createEAttribute(xmlPlsqlRecord_2_3EClass, XML_PLSQL_RECORD_23__NAME);

		xmlPlsqlTable_2_3EClass = createEClass(XML_PLSQL_TABLE_23);
		createEAttribute(xmlPlsqlTable_2_3EClass, XML_PLSQL_TABLE_23__NAME);

		xmlStruct_2_3EClass = createEClass(XML_STRUCT_23);
		createEAttribute(xmlStruct_2_3EClass, XML_STRUCT_23__NAME);

		xmlTenantDiscriminatorColumn_2_3EClass = createEClass(XML_TENANT_DISCRIMINATOR_COLUMN_23);
		createEAttribute(xmlTenantDiscriminatorColumn_2_3EClass, XML_TENANT_DISCRIMINATOR_COLUMN_23__CONTEXT_PROPERTY);
		createEAttribute(xmlTenantDiscriminatorColumn_2_3EClass, XML_TENANT_DISCRIMINATOR_COLUMN_23__TABLE);
		createEAttribute(xmlTenantDiscriminatorColumn_2_3EClass, XML_TENANT_DISCRIMINATOR_COLUMN_23__PRIMARY_KEY);

		xmlStructure_2_3EClass = createEClass(XML_STRUCTURE_23);

		xmlElementCollection_2_3EClass = createEClass(XML_ELEMENT_COLLECTION_23);
		createEAttribute(xmlElementCollection_2_3EClass, XML_ELEMENT_COLLECTION_23__COMPOSITE_MEMBER);

		// Create enums
		xmlMultitenantTypeEEnum = createEEnum(XML_MULTITENANT_TYPE);
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
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
		OrmPackage theOrmPackage = (OrmPackage)EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		xmlTenantDiscriminatorColumn_2_3EClass.getESuperTypes().add(theOrmPackage.getAbstractXmlDiscriminatorColumn());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlArray_2_3EClass, XmlArray_2_3.class, "XmlArray_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlAttributes_2_3EClass, XmlAttributes_2_3.class, "XmlAttributes_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlAttributes_2_3_Structures(), this.getXmlStructure_2_3(), null, "structures", null, 0, -1, XmlAttributes_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlAttributes_2_3_Arrays(), this.getXmlArray_2_3(), null, "arrays", null, 0, -1, XmlAttributes_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEmbeddable_2_3EClass, XmlEmbeddable_2_3.class, "XmlEmbeddable_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEmbeddable_2_3_PlsqlRecords(), this.getXmlPlsqlRecord_2_3(), null, "plsqlRecords", null, 0, -1, XmlEmbeddable_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEmbeddable_2_3_PlsqlTables(), theEclipseLinkOrmPackage.getXmlPlsqlTable(), null, "plsqlTables", null, 0, -1, XmlEmbeddable_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEmbeddable_2_3_Struct(), this.getXmlStruct_2_3(), null, "struct", null, 0, 1, XmlEmbeddable_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntity_2_3EClass, XmlEntity_2_3.class, "XmlEntity_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntity_2_3_Multitenant(), this.getXmlMultitenant_2_3(), null, "multitenant", null, 0, 1, XmlEntity_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_2_3_NamedStoredFunctionQueries(), this.getXmlNamedStoredFunctionQuery_2_3(), null, "namedStoredFunctionQueries", null, 0, -1, XmlEntity_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_2_3_NamedPlsqlStoredFunctionQueries(), this.getXmlNamedPlsqlStoredFunctionQuery_2_3(), null, "namedPlsqlStoredFunctionQueries", null, 0, -1, XmlEntity_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_2_3_NamedPlsqlStoredProcedureQueries(), this.getXmlNamedPlsqlStoredProcedureQuery_2_3(), null, "namedPlsqlStoredProcedureQueries", null, 0, -1, XmlEntity_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_2_3_PlsqlRecords(), this.getXmlPlsqlRecord_2_3(), null, "plsqlRecords", null, 0, -1, XmlEntity_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_2_3_PlsqlTables(), theEclipseLinkOrmPackage.getXmlPlsqlTable(), null, "plsqlTables", null, 0, -1, XmlEntity_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_2_3_Struct(), this.getXmlStruct_2_3(), null, "struct", null, 0, 1, XmlEntity_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntityMappings_2_3EClass, XmlEntityMappings_2_3.class, "XmlEntityMappings_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntityMappings_2_3_TenantDiscriminatorColumns(), this.getXmlTenantDiscriminatorColumn_2_3(), null, "tenantDiscriminatorColumns", null, 0, -1, XmlEntityMappings_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_2_3_NamedStoredFunctionQueries(), this.getXmlNamedStoredFunctionQuery_2_3(), null, "namedStoredFunctionQueries", null, 0, -1, XmlEntityMappings_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_2_3_NamedPlsqlStoredFunctionQueries(), this.getXmlNamedPlsqlStoredFunctionQuery_2_3(), null, "namedPlsqlStoredFunctionQueries", null, 0, -1, XmlEntityMappings_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_2_3_NamedPlsqlStoredProcedureQueries(), this.getXmlNamedPlsqlStoredProcedureQuery_2_3(), null, "namedPlsqlStoredProcedureQueries", null, 0, -1, XmlEntityMappings_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_2_3_PlsqlRecords(), this.getXmlPlsqlRecord_2_3(), null, "plsqlRecords", null, 0, -1, XmlEntityMappings_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_2_3_PlsqlTables(), theEclipseLinkOrmPackage.getXmlPlsqlTable(), null, "plsqlTables", null, 0, -1, XmlEntityMappings_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMappedSuperclass_2_3EClass, XmlMappedSuperclass_2_3.class, "XmlMappedSuperclass_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlMappedSuperclass_2_3_Multitenant(), this.getXmlMultitenant_2_3(), null, "multitenant", null, 0, 1, XmlMappedSuperclass_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlMappedSuperclass_2_3_NamedStoredFunctionQueries(), this.getXmlNamedStoredFunctionQuery_2_3(), null, "namedStoredFunctionQueries", null, 0, -1, XmlMappedSuperclass_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlMappedSuperclass_2_3_NamedPlsqlStoredFunctionQueries(), this.getXmlNamedPlsqlStoredFunctionQuery_2_3(), null, "namedPlsqlStoredFunctionQueries", null, 0, -1, XmlMappedSuperclass_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlMappedSuperclass_2_3_NamedPlsqlStoredProcedureQueries(), this.getXmlNamedPlsqlStoredProcedureQuery_2_3(), null, "namedPlsqlStoredProcedureQueries", null, 0, -1, XmlMappedSuperclass_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlMappedSuperclass_2_3_PlsqlRecords(), this.getXmlPlsqlRecord_2_3(), null, "plsqlRecords", null, 0, -1, XmlMappedSuperclass_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlMappedSuperclass_2_3_PlsqlTables(), theEclipseLinkOrmPackage.getXmlPlsqlTable(), null, "plsqlTables", null, 0, -1, XmlMappedSuperclass_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMultitenant_2_3EClass, XmlMultitenant_2_3.class, "XmlMultitenant_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlMultitenant_2_3_Type(), this.getXmlMultitenantType(), "type", null, 0, 1, XmlMultitenant_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlMultitenant_2_3_TenantDiscriminatorColumns(), this.getXmlTenantDiscriminatorColumn_2_3(), null, "tenantDiscriminatorColumns", null, 0, -1, XmlMultitenant_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNamedPlsqlStoredFunctionQuery_2_3EClass, XmlNamedPlsqlStoredFunctionQuery_2_3.class, "XmlNamedPlsqlStoredFunctionQuery_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlNamedPlsqlStoredFunctionQuery_2_3_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlNamedPlsqlStoredFunctionQuery_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNamedPlsqlStoredProcedureQuery_2_3EClass, XmlNamedPlsqlStoredProcedureQuery_2_3.class, "XmlNamedPlsqlStoredProcedureQuery_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlNamedPlsqlStoredProcedureQuery_2_3_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlNamedPlsqlStoredProcedureQuery_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNamedStoredFunctionQuery_2_3EClass, XmlNamedStoredFunctionQuery_2_3.class, "XmlNamedStoredFunctionQuery_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlNamedStoredFunctionQuery_2_3_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlNamedStoredFunctionQuery_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPersistenceUnitDefaults_2_3EClass, XmlPersistenceUnitDefaults_2_3.class, "XmlPersistenceUnitDefaults_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlPersistenceUnitDefaults_2_3_TenantDiscriminatorColumns(), this.getXmlTenantDiscriminatorColumn_2_3(), null, "tenantDiscriminatorColumns", null, 0, -1, XmlPersistenceUnitDefaults_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPlsqlRecord_2_3EClass, XmlPlsqlRecord_2_3.class, "XmlPlsqlRecord_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPlsqlRecord_2_3_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlPlsqlRecord_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPlsqlTable_2_3EClass, XmlPlsqlTable_2_3.class, "XmlPlsqlTable_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPlsqlTable_2_3_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlPlsqlTable_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlStruct_2_3EClass, XmlStruct_2_3.class, "XmlStruct_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlStruct_2_3_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlStruct_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTenantDiscriminatorColumn_2_3EClass, XmlTenantDiscriminatorColumn_2_3.class, "XmlTenantDiscriminatorColumn_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlTenantDiscriminatorColumn_2_3_ContextProperty(), theXMLTypePackage.getString(), "contextProperty", null, 0, 1, XmlTenantDiscriminatorColumn_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTenantDiscriminatorColumn_2_3_Table(), theXMLTypePackage.getString(), "table", null, 0, 1, XmlTenantDiscriminatorColumn_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTenantDiscriminatorColumn_2_3_PrimaryKey(), ecorePackage.getEBooleanObject(), "primaryKey", null, 0, 1, XmlTenantDiscriminatorColumn_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlStructure_2_3EClass, XmlStructure_2_3.class, "XmlStructure_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlElementCollection_2_3EClass, XmlElementCollection_2_3.class, "XmlElementCollection_2_3", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlElementCollection_2_3_CompositeMember(), theXMLTypePackage.getString(), "compositeMember", null, 0, 1, XmlElementCollection_2_3.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(xmlMultitenantTypeEEnum, XmlMultitenantType.class, "XmlMultitenantType");
		addEEnumLiteral(xmlMultitenantTypeEEnum, XmlMultitenantType.SINGLE_TABLE);
		addEEnumLiteral(xmlMultitenantTypeEEnum, XmlMultitenantType.TABLE_PER_TENANT);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3 <em>Xml Embeddable 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEmbeddable_2_3()
		 * @generated
		 */
		public static final EClass XML_EMBEDDABLE_23 = eINSTANCE.getXmlEmbeddable_2_3();

		/**
		 * The meta object literal for the '<em><b>Plsql Records</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EMBEDDABLE_23__PLSQL_RECORDS = eINSTANCE.getXmlEmbeddable_2_3_PlsqlRecords();

		/**
		 * The meta object literal for the '<em><b>Plsql Tables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EMBEDDABLE_23__PLSQL_TABLES = eINSTANCE.getXmlEmbeddable_2_3_PlsqlTables();

		/**
		 * The meta object literal for the '<em><b>Struct</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EMBEDDABLE_23__STRUCT = eINSTANCE.getXmlEmbeddable_2_3_Struct();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenant_2_3 <em>Xml Multitenant 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenant_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMultitenant_2_3()
		 * @generated
		 */
		public static final EClass XML_MULTITENANT_23 = eINSTANCE.getXmlMultitenant_2_3();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MULTITENANT_23__TYPE = eINSTANCE.getXmlMultitenant_2_3_Type();

		/**
		 * The meta object literal for the '<em><b>Tenant Discriminator Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MULTITENANT_23__TENANT_DISCRIMINATOR_COLUMNS = eINSTANCE.getXmlMultitenant_2_3_TenantDiscriminatorColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredFunctionQuery_2_3 <em>Xml Named Plsql Stored Function Query 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredFunctionQuery_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlNamedPlsqlStoredFunctionQuery_2_3()
		 * @generated
		 */
		public static final EClass XML_NAMED_PLSQL_STORED_FUNCTION_QUERY_23 = eINSTANCE.getXmlNamedPlsqlStoredFunctionQuery_2_3();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_PLSQL_STORED_FUNCTION_QUERY_23__NAME = eINSTANCE.getXmlNamedPlsqlStoredFunctionQuery_2_3_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredProcedureQuery_2_3 <em>Xml Named Plsql Stored Procedure Query 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredProcedureQuery_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlNamedPlsqlStoredProcedureQuery_2_3()
		 * @generated
		 */
		public static final EClass XML_NAMED_PLSQL_STORED_PROCEDURE_QUERY_23 = eINSTANCE.getXmlNamedPlsqlStoredProcedureQuery_2_3();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_PLSQL_STORED_PROCEDURE_QUERY_23__NAME = eINSTANCE.getXmlNamedPlsqlStoredProcedureQuery_2_3_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedStoredFunctionQuery_2_3 <em>Xml Named Stored Function Query 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedStoredFunctionQuery_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlNamedStoredFunctionQuery_2_3()
		 * @generated
		 */
		public static final EClass XML_NAMED_STORED_FUNCTION_QUERY_23 = eINSTANCE.getXmlNamedStoredFunctionQuery_2_3();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_STORED_FUNCTION_QUERY_23__NAME = eINSTANCE.getXmlNamedStoredFunctionQuery_2_3_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3 <em>Xml Entity 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntity_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEntity_2_3()
		 * @generated
		 */
		public static final EClass XML_ENTITY_23 = eINSTANCE.getXmlEntity_2_3();

		/**
		 * The meta object literal for the '<em><b>Multitenant</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_23__MULTITENANT = eINSTANCE.getXmlEntity_2_3_Multitenant();

		/**
		 * The meta object literal for the '<em><b>Named Stored Function Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_23__NAMED_STORED_FUNCTION_QUERIES = eINSTANCE.getXmlEntity_2_3_NamedStoredFunctionQueries();

		/**
		 * The meta object literal for the '<em><b>Named Plsql Stored Function Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_23__NAMED_PLSQL_STORED_FUNCTION_QUERIES = eINSTANCE.getXmlEntity_2_3_NamedPlsqlStoredFunctionQueries();

		/**
		 * The meta object literal for the '<em><b>Named Plsql Stored Procedure Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_23__NAMED_PLSQL_STORED_PROCEDURE_QUERIES = eINSTANCE.getXmlEntity_2_3_NamedPlsqlStoredProcedureQueries();

		/**
		 * The meta object literal for the '<em><b>Plsql Records</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_23__PLSQL_RECORDS = eINSTANCE.getXmlEntity_2_3_PlsqlRecords();

		/**
		 * The meta object literal for the '<em><b>Plsql Tables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_23__PLSQL_TABLES = eINSTANCE.getXmlEntity_2_3_PlsqlTables();

		/**
		 * The meta object literal for the '<em><b>Struct</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_23__STRUCT = eINSTANCE.getXmlEntity_2_3_Struct();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3 <em>Xml Mapped Superclass 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMappedSuperclass_2_3()
		 * @generated
		 */
		public static final EClass XML_MAPPED_SUPERCLASS_23 = eINSTANCE.getXmlMappedSuperclass_2_3();

		/**
		 * The meta object literal for the '<em><b>Multitenant</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS_23__MULTITENANT = eINSTANCE.getXmlMappedSuperclass_2_3_Multitenant();

		/**
		 * The meta object literal for the '<em><b>Named Stored Function Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS_23__NAMED_STORED_FUNCTION_QUERIES = eINSTANCE.getXmlMappedSuperclass_2_3_NamedStoredFunctionQueries();

		/**
		 * The meta object literal for the '<em><b>Named Plsql Stored Function Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS_23__NAMED_PLSQL_STORED_FUNCTION_QUERIES = eINSTANCE.getXmlMappedSuperclass_2_3_NamedPlsqlStoredFunctionQueries();

		/**
		 * The meta object literal for the '<em><b>Named Plsql Stored Procedure Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS_23__NAMED_PLSQL_STORED_PROCEDURE_QUERIES = eINSTANCE.getXmlMappedSuperclass_2_3_NamedPlsqlStoredProcedureQueries();

		/**
		 * The meta object literal for the '<em><b>Plsql Records</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS_23__PLSQL_RECORDS = eINSTANCE.getXmlMappedSuperclass_2_3_PlsqlRecords();

		/**
		 * The meta object literal for the '<em><b>Plsql Tables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS_23__PLSQL_TABLES = eINSTANCE.getXmlMappedSuperclass_2_3_PlsqlTables();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3 <em>Xml Entity Mappings 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEntityMappings_2_3()
		 * @generated
		 */
		public static final EClass XML_ENTITY_MAPPINGS_23 = eINSTANCE.getXmlEntityMappings_2_3();

		/**
		 * The meta object literal for the '<em><b>Tenant Discriminator Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_23__TENANT_DISCRIMINATOR_COLUMNS = eINSTANCE.getXmlEntityMappings_2_3_TenantDiscriminatorColumns();

		/**
		 * The meta object literal for the '<em><b>Named Stored Function Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_23__NAMED_STORED_FUNCTION_QUERIES = eINSTANCE.getXmlEntityMappings_2_3_NamedStoredFunctionQueries();

		/**
		 * The meta object literal for the '<em><b>Named Plsql Stored Function Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_23__NAMED_PLSQL_STORED_FUNCTION_QUERIES = eINSTANCE.getXmlEntityMappings_2_3_NamedPlsqlStoredFunctionQueries();

		/**
		 * The meta object literal for the '<em><b>Named Plsql Stored Procedure Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_23__NAMED_PLSQL_STORED_PROCEDURE_QUERIES = eINSTANCE.getXmlEntityMappings_2_3_NamedPlsqlStoredProcedureQueries();

		/**
		 * The meta object literal for the '<em><b>Plsql Records</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_23__PLSQL_RECORDS = eINSTANCE.getXmlEntityMappings_2_3_PlsqlRecords();

		/**
		 * The meta object literal for the '<em><b>Plsql Tables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_23__PLSQL_TABLES = eINSTANCE.getXmlEntityMappings_2_3_PlsqlTables();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPersistenceUnitDefaults_2_3 <em>Xml Persistence Unit Defaults 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPersistenceUnitDefaults_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlPersistenceUnitDefaults_2_3()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE_UNIT_DEFAULTS_23 = eINSTANCE.getXmlPersistenceUnitDefaults_2_3();

		/**
		 * The meta object literal for the '<em><b>Tenant Discriminator Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PERSISTENCE_UNIT_DEFAULTS_23__TENANT_DISCRIMINATOR_COLUMNS = eINSTANCE.getXmlPersistenceUnitDefaults_2_3_TenantDiscriminatorColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlRecord_2_3 <em>Xml Plsql Record 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlRecord_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlPlsqlRecord_2_3()
		 * @generated
		 */
		public static final EClass XML_PLSQL_RECORD_23 = eINSTANCE.getXmlPlsqlRecord_2_3();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PLSQL_RECORD_23__NAME = eINSTANCE.getXmlPlsqlRecord_2_3_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlTable_2_3 <em>Xml Plsql Table 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlTable_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlPlsqlTable_2_3()
		 * @generated
		 */
		public static final EClass XML_PLSQL_TABLE_23 = eINSTANCE.getXmlPlsqlTable_2_3();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PLSQL_TABLE_23__NAME = eINSTANCE.getXmlPlsqlTable_2_3_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStruct_2_3 <em>Xml Struct 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStruct_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlStruct_2_3()
		 * @generated
		 */
		public static final EClass XML_STRUCT_23 = eINSTANCE.getXmlStruct_2_3();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_STRUCT_23__NAME = eINSTANCE.getXmlStruct_2_3_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3 <em>Xml Tenant Discriminator Column 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlTenantDiscriminatorColumn_2_3()
		 * @generated
		 */
		public static final EClass XML_TENANT_DISCRIMINATOR_COLUMN_23 = eINSTANCE.getXmlTenantDiscriminatorColumn_2_3();

		/**
		 * The meta object literal for the '<em><b>Context Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TENANT_DISCRIMINATOR_COLUMN_23__CONTEXT_PROPERTY = eINSTANCE.getXmlTenantDiscriminatorColumn_2_3_ContextProperty();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TENANT_DISCRIMINATOR_COLUMN_23__TABLE = eINSTANCE.getXmlTenantDiscriminatorColumn_2_3_Table();

		/**
		 * The meta object literal for the '<em><b>Primary Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TENANT_DISCRIMINATOR_COLUMN_23__PRIMARY_KEY = eINSTANCE.getXmlTenantDiscriminatorColumn_2_3_PrimaryKey();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3 <em>Xml Attributes 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlAttributes_2_3()
		 * @generated
		 */
		public static final EClass XML_ATTRIBUTES_23 = eINSTANCE.getXmlAttributes_2_3();

		/**
		 * The meta object literal for the '<em><b>Structures</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ATTRIBUTES_23__STRUCTURES = eINSTANCE.getXmlAttributes_2_3_Structures();

		/**
		 * The meta object literal for the '<em><b>Arrays</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ATTRIBUTES_23__ARRAYS = eINSTANCE.getXmlAttributes_2_3_Arrays();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStructure_2_3 <em>Xml Structure 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStructure_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlStructure_2_3()
		 * @generated
		 */
		public static final EClass XML_STRUCTURE_23 = eINSTANCE.getXmlStructure_2_3();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlArray_2_3 <em>Xml Array 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlArray_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlArray_2_3()
		 * @generated
		 */
		public static final EClass XML_ARRAY_23 = eINSTANCE.getXmlArray_2_3();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlElementCollection_2_3 <em>Xml Element Collection 23</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlElementCollection_2_3
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlElementCollection_2_3()
		 * @generated
		 */
		public static final EClass XML_ELEMENT_COLLECTION_23 = eINSTANCE.getXmlElementCollection_2_3();

		/**
		 * The meta object literal for the '<em><b>Composite Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION_23__COMPOSITE_MEMBER = eINSTANCE.getXmlElementCollection_2_3_CompositeMember();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenantType <em>Xml Multitenant Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenantType
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMultitenantType()
		 * @generated
		 */
		public static final EEnum XML_MULTITENANT_TYPE = eINSTANCE.getXmlMultitenantType();

	}

} //EclipseLinkOrmV2_3Package
