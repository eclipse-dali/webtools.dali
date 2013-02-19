/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm.v2_1;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.jpt.common.core.resource.xml.CommonPackage;

import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;

import org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package;

import org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage;

import org.eclipse.jpt.jpa.core.resource.persistence.v2_0.PersistenceV2_0Package;

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
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Factory
 * @model kind="package"
 * @generated
 */
public class OrmV2_1Package extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "v2_1";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.orm.v2_1.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.jpa.core.resource.orm.v2_1";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final OrmV2_1Package eINSTANCE = org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ColumnResult_2_1 <em>Column Result 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ColumnResult_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getColumnResult_2_1()
	 * @generated
	 */
	public static final int COLUMN_RESULT_21 = 0;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_RESULT_21__CLASS_NAME = 0;

	/**
	 * The number of structural features of the '<em>Column Result 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_RESULT_21_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstructorResult_2_1 <em>Constructor Result 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstructorResult_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getConstructorResult_2_1()
	 * @generated
	 */
	public static final int CONSTRUCTOR_RESULT_21 = 1;

	/**
	 * The feature id for the '<em><b>Target Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CONSTRUCTOR_RESULT_21__TARGET_CLASS = 0;

	/**
	 * The feature id for the '<em><b>Column Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CONSTRUCTOR_RESULT_21__COLUMN_RESULTS = 1;

	/**
	 * The number of structural features of the '<em>Constructor Result 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CONSTRUCTOR_RESULT_21_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlAssociationOverride_2_1 <em>Xml Association Override 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlAssociationOverride_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlAssociationOverride_2_1()
	 * @generated
	 */
	public static final int XML_ASSOCIATION_OVERRIDE_21 = 2;

	/**
	 * The feature id for the '<em><b>Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE_21__FOREIGN_KEY = 0;

	/**
	 * The number of structural features of the '<em>Xml Association Override 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE_21_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlCollectionTable_2_1 <em>Xml Collection Table 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlCollectionTable_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlCollectionTable_2_1()
	 * @generated
	 */
	public static final int XML_COLLECTION_TABLE_21 = 3;

	/**
	 * The feature id for the '<em><b>Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE_21__FOREIGN_KEY = 0;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE_21__INDEXES = 1;

	/**
	 * The number of structural features of the '<em>Xml Collection Table 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE_21_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1 <em>Xml Convert 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConvert_2_1()
	 * @generated
	 */
	public static final int XML_CONVERT_21 = 4;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERT_21__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERT_21__CONVERTER = 1;

	/**
	 * The feature id for the '<em><b>Attribute Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERT_21__ATTRIBUTE_NAME = 2;

	/**
	 * The feature id for the '<em><b>Disable Conversion</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERT_21__DISABLE_CONVERSION = 3;

	/**
	 * The number of structural features of the '<em>Xml Convert 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERT_21_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1 <em>Xml Converter 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConverter_2_1()
	 * @generated
	 */
	public static final int XML_CONVERTER_21 = 5;

	/**
	 * The feature id for the '<em><b>Auto Apply</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_21__AUTO_APPLY = 0;

	/**
	 * The number of structural features of the '<em>Xml Converter 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_21_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1 <em>Xml Converter Container 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConverterContainer_2_1()
	 * @generated
	 */
	public static final int XML_CONVERTER_CONTAINER_21 = 6;

	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_CONTAINER_21__CONVERTERS = 0;

	/**
	 * The number of structural features of the '<em>Xml Converter Container 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_CONTAINER_21_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlElementCollection_2_1 <em>Xml Element Collection 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlElementCollection_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlElementCollection_2_1()
	 * @generated
	 */
	public static final int XML_ELEMENT_COLLECTION_21 = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEmbedded_2_1 <em>Xml Embedded 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEmbedded_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlEmbedded_2_1()
	 * @generated
	 */
	public static final int XML_EMBEDDED_21 = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntityMappings_2_1 <em>Xml Entity Mappings 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntityMappings_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlEntityMappings_2_1()
	 * @generated
	 */
	public static final int XML_ENTITY_MAPPINGS_21 = 11;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1 <em>Xml Foreign Key 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlForeignKey_2_1()
	 * @generated
	 */
	public static final int XML_FOREIGN_KEY_21 = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1 <em>Xml Index 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlIndex_2_1()
	 * @generated
	 */
	public static final int XML_INDEX_21 = 13;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlJoinTable_2_1 <em>Xml Join Table 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlJoinTable_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlJoinTable_2_1()
	 * @generated
	 */
	public static final int XML_JOIN_TABLE_21 = 14;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToMany_2_1 <em>Xml Many To Many 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToMany_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlManyToMany_2_1()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY_21 = 15;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToOne_2_1 <em>Xml Many To One 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToOne_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlManyToOne_2_1()
	 * @generated
	 */
	public static final int XML_MANY_TO_ONE_21 = 16;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1 <em>Xml Named Attribute Node 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlNamedAttributeNode_2_1()
	 * @generated
	 */
	public static final int XML_NAMED_ATTRIBUTE_NODE_21 = 17;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1 <em>Xml Named Entity Graph 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlNamedEntityGraph_2_1()
	 * @generated
	 */
	public static final int XML_NAMED_ENTITY_GRAPH_21 = 18;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlQueryContainer_2_1 <em>Xml Query Container 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlQueryContainer_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlQueryContainer_2_1()
	 * @generated
	 */
	public static final int XML_QUERY_CONTAINER_21 = 23;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntity_2_1 <em>Xml Entity 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntity_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlEntity_2_1()
	 * @generated
	 */
	public static final int XML_ENTITY_21 = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1 <em>Xml Named Stored Procedure Query 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlNamedStoredProcedureQuery_2_1()
	 * @generated
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY_21 = 19;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1 <em>Xml Named Subgraph 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlNamedSubgraph_2_1()
	 * @generated
	 */
	public static final int XML_NAMED_SUBGRAPH_21 = 20;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToMany_2_1 <em>Xml One To Many 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToMany_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlOneToMany_2_1()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY_21 = 21;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToOne_2_1 <em>Xml One To One 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToOne_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlOneToOne_2_1()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE_21 = 22;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSecondaryTable_2_1 <em>Xml Secondary Table 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSecondaryTable_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlSecondaryTable_2_1()
	 * @generated
	 */
	public static final int XML_SECONDARY_TABLE_21 = 24;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1 <em>Xml Stored Procedure Parameter 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlStoredProcedureParameter_2_1()
	 * @generated
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER_21 = 25;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSqlResultSetMapping_2_1 <em>Xml Sql Result Set Mapping 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSqlResultSetMapping_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlSqlResultSetMapping_2_1()
	 * @generated
	 */
	public static final int XML_SQL_RESULT_SET_MAPPING_21 = 26;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTable_2_1 <em>Xml Table 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTable_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlTable_2_1()
	 * @generated
	 */
	public static final int XML_TABLE_21 = 27;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTableGenerator_2_1 <em>Xml Table Generator 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTableGenerator_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlTableGenerator_2_1()
	 * @generated
	 */
	public static final int XML_TABLE_GENERATOR_21 = 28;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvertibleMapping_2_1 <em>Xml Convertible Mapping 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvertibleMapping_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConvertibleMapping_2_1()
	 * @generated
	 */
	public static final int XML_CONVERTIBLE_MAPPING_21 = 7;

	/**
	 * The feature id for the '<em><b>Convert</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING_21__CONVERT = 0;

	/**
	 * The number of structural features of the '<em>Xml Convertible Mapping 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING_21_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Map Key Converts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_21__MAP_KEY_CONVERTS = 0;

	/**
	 * The feature id for the '<em><b>Map Key Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_21__MAP_KEY_FOREIGN_KEY = 1;

	/**
	 * The feature id for the '<em><b>Converts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_21__CONVERTS = 2;

	/**
	 * The number of structural features of the '<em>Xml Element Collection 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_21_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Converts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_21__CONVERTS = 0;

	/**
	 * The number of structural features of the '<em>Xml Embedded 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_21_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Named Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_CONTAINER_21__NAMED_STORED_PROCEDURE_QUERIES = 0;

	/**
	 * The number of structural features of the '<em>Xml Query Container 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_CONTAINER_21_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Named Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_21__NAMED_STORED_PROCEDURE_QUERIES = XML_QUERY_CONTAINER_21__NAMED_STORED_PROCEDURE_QUERIES;

	/**
	 * The feature id for the '<em><b>Primary Key Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_21__PRIMARY_KEY_FOREIGN_KEY = XML_QUERY_CONTAINER_21_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Converts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_21__CONVERTS = XML_QUERY_CONTAINER_21_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Named Entity Graphs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_21__NAMED_ENTITY_GRAPHS = XML_QUERY_CONTAINER_21_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Entity 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_21_FEATURE_COUNT = XML_QUERY_CONTAINER_21_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_21__CONVERTERS = XML_CONVERTER_CONTAINER_21__CONVERTERS;

	/**
	 * The feature id for the '<em><b>Named Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_21__NAMED_STORED_PROCEDURE_QUERIES = XML_CONVERTER_CONTAINER_21_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Entity Mappings 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_21_FEATURE_COUNT = XML_CONVERTER_CONTAINER_21_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FOREIGN_KEY_21__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FOREIGN_KEY_21__NAME = 1;

	/**
	 * The feature id for the '<em><b>Constraint Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FOREIGN_KEY_21__CONSTRAINT_MODE = 2;

	/**
	 * The feature id for the '<em><b>Foreign Key Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FOREIGN_KEY_21__FOREIGN_KEY_DEFINITION = 3;

	/**
	 * The number of structural features of the '<em>Xml Foreign Key 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FOREIGN_KEY_21_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX_21__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX_21__NAME = 1;

	/**
	 * The feature id for the '<em><b>Column List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX_21__COLUMN_LIST = 2;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX_21__UNIQUE = 3;

	/**
	 * The number of structural features of the '<em>Xml Index 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX_21_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE_21__FOREIGN_KEY = 0;

	/**
	 * The feature id for the '<em><b>Inverse Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE_21__INVERSE_FOREIGN_KEY = 1;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE_21__INDEXES = 2;

	/**
	 * The number of structural features of the '<em>Xml Join Table 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE_21_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Map Key Converts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_21__MAP_KEY_CONVERTS = 0;

	/**
	 * The feature id for the '<em><b>Map Key Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_21__MAP_KEY_FOREIGN_KEY = 1;

	/**
	 * The number of structural features of the '<em>Xml Many To Many 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_21_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_21__FOREIGN_KEY = 0;

	/**
	 * The number of structural features of the '<em>Xml Many To One 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_21_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_ATTRIBUTE_NODE_21__NAME = 0;

	/**
	 * The feature id for the '<em><b>Subgraph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_ATTRIBUTE_NODE_21__SUBGRAPH = 1;

	/**
	 * The feature id for the '<em><b>Key Subgraph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_ATTRIBUTE_NODE_21__KEY_SUBGRAPH = 2;

	/**
	 * The number of structural features of the '<em>Xml Named Attribute Node 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_ATTRIBUTE_NODE_21_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Named Attribute Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_ENTITY_GRAPH_21__NAMED_ATTRIBUTE_NODES = 0;

	/**
	 * The feature id for the '<em><b>Subgraphs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_ENTITY_GRAPH_21__SUBGRAPHS = 1;

	/**
	 * The feature id for the '<em><b>Subclass Subgraphs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_ENTITY_GRAPH_21__SUBCLASS_SUBGRAPHS = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_ENTITY_GRAPH_21__NAME = 3;

	/**
	 * The feature id for the '<em><b>Include All Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_ENTITY_GRAPH_21__INCLUDE_ALL_ATTRIBUTES = 4;

	/**
	 * The number of structural features of the '<em>Xml Named Entity Graph 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_ENTITY_GRAPH_21_FEATURE_COUNT = 5;

	/**
	 * The feature id for the '<em><b>Result Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY_21__RESULT_CLASSES = 0;

	/**
	 * The feature id for the '<em><b>Result Set Mappings</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY_21__RESULT_SET_MAPPINGS = 1;

	/**
	 * The feature id for the '<em><b>Procedure Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY_21__PROCEDURE_NAME = 2;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY_21__PARAMETERS = 3;

	/**
	 * The number of structural features of the '<em>Xml Named Stored Procedure Query 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY_21_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>Named Attribute Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_SUBGRAPH_21__NAMED_ATTRIBUTE_NODES = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_SUBGRAPH_21__NAME = 1;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_SUBGRAPH_21__CLASS_NAME = 2;

	/**
	 * The number of structural features of the '<em>Xml Named Subgraph 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_SUBGRAPH_21_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Map Key Converts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_21__MAP_KEY_CONVERTS = 0;

	/**
	 * The feature id for the '<em><b>Map Key Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_21__MAP_KEY_FOREIGN_KEY = 1;

	/**
	 * The feature id for the '<em><b>Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_21__FOREIGN_KEY = 2;

	/**
	 * The number of structural features of the '<em>Xml One To Many 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_21_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Map Key Converts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_21__MAP_KEY_CONVERTS = 0;

	/**
	 * The feature id for the '<em><b>Primary Key Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_21__PRIMARY_KEY_FOREIGN_KEY = 1;

	/**
	 * The feature id for the '<em><b>Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_21__FOREIGN_KEY = 2;

	/**
	 * The number of structural features of the '<em>Xml One To One 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_21_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Primary Key Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE_21__PRIMARY_KEY_FOREIGN_KEY = 0;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE_21__INDEXES = 1;

	/**
	 * The number of structural features of the '<em>Xml Secondary Table 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE_21_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER_21__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER_21__MODE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER_21__NAME = 2;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER_21__CLASS_NAME = 3;

	/**
	 * The number of structural features of the '<em>Xml Stored Procedure Parameter 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER_21_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>Constructor Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SQL_RESULT_SET_MAPPING_21__CONSTRUCTOR_RESULTS = 0;

	/**
	 * The number of structural features of the '<em>Xml Sql Result Set Mapping 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SQL_RESULT_SET_MAPPING_21_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_21__INDEXES = 0;

	/**
	 * The number of structural features of the '<em>Xml Table 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_21_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR_21__INDEXES = 0;

	/**
	 * The number of structural features of the '<em>Xml Table Generator 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR_21_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1 <em>Parameter Mode 21</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getParameterMode_2_1()
	 * @generated
	 */
	public static final int PARAMETER_MODE_21 = 29;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstraintMode_2_1 <em>Constraint Mode 21</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstraintMode_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getConstraintMode_2_1()
	 * @generated
	 */
	public static final int CONSTRAINT_MODE_21 = 30;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass columnResult_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constructorResult_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAssociationOverride_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCollectionTable_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlConvert_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlConverter_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntityMappings_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlForeignKey_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlIndex_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlJoinTable_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlManyToMany_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlManyToOne_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedAttributeNode_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedEntityGraph_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlQueryContainer_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlSecondaryTable_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedStoredProcedureQuery_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedSubgraph_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOneToMany_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOneToOne_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlStoredProcedureParameter_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlSqlResultSetMapping_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTable_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTableGenerator_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlConvertibleMapping_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum parameterMode_2_1EEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum constraintMode_2_1EEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlConverterContainer_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlElementCollection_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEmbedded_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntity_2_1EClass = null;

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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private OrmV2_1Package()
	{
		super(eNS_URI, OrmV2_1Factory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link OrmV2_1Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static OrmV2_1Package init()
	{
		if (isInited) return (OrmV2_1Package)EPackage.Registry.INSTANCE.getEPackage(OrmV2_1Package.eNS_URI);

		// Obtain or create and register package
		OrmV2_1Package theOrmV2_1Package = (OrmV2_1Package)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof OrmV2_1Package ? EPackage.Registry.INSTANCE.get(eNS_URI) : new OrmV2_1Package());

		isInited = true;

		// Initialize simple dependencies
		CommonPackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		OrmPackage theOrmPackage = (OrmPackage)(EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) : OrmPackage.eINSTANCE);
		OrmV2_0Package theOrmV2_0Package = (OrmV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(OrmV2_0Package.eNS_URI) instanceof OrmV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(OrmV2_0Package.eNS_URI) : OrmV2_0Package.eINSTANCE);
		PersistencePackage thePersistencePackage = (PersistencePackage)(EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);
		PersistenceV2_0Package thePersistenceV2_0Package = (PersistenceV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(PersistenceV2_0Package.eNS_URI) instanceof PersistenceV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(PersistenceV2_0Package.eNS_URI) : PersistenceV2_0Package.eINSTANCE);

		// Create package meta-data objects
		theOrmV2_1Package.createPackageContents();
		theOrmPackage.createPackageContents();
		theOrmV2_0Package.createPackageContents();
		thePersistencePackage.createPackageContents();
		thePersistenceV2_0Package.createPackageContents();

		// Initialize created meta-data
		theOrmV2_1Package.initializePackageContents();
		theOrmPackage.initializePackageContents();
		theOrmV2_0Package.initializePackageContents();
		thePersistencePackage.initializePackageContents();
		thePersistenceV2_0Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theOrmV2_1Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(OrmV2_1Package.eNS_URI, theOrmV2_1Package);
		return theOrmV2_1Package;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ColumnResult_2_1 <em>Column Result 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Column Result 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ColumnResult_2_1
	 * @generated
	 */
	public EClass getColumnResult_2_1()
	{
		return columnResult_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ColumnResult_2_1#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ColumnResult_2_1#getClassName()
	 * @see #getColumnResult_2_1()
	 * @generated
	 */
	public EAttribute getColumnResult_2_1_ClassName()
	{
		return (EAttribute)columnResult_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstructorResult_2_1 <em>Constructor Result 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constructor Result 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstructorResult_2_1
	 * @generated
	 */
	public EClass getConstructorResult_2_1()
	{
		return constructorResult_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstructorResult_2_1#getTargetClass <em>Target Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Class</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstructorResult_2_1#getTargetClass()
	 * @see #getConstructorResult_2_1()
	 * @generated
	 */
	public EAttribute getConstructorResult_2_1_TargetClass()
	{
		return (EAttribute)constructorResult_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstructorResult_2_1#getColumnResults <em>Column Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Column Results</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstructorResult_2_1#getColumnResults()
	 * @see #getConstructorResult_2_1()
	 * @generated
	 */
	public EReference getConstructorResult_2_1_ColumnResults()
	{
		return (EReference)constructorResult_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlAssociationOverride_2_1 <em>Xml Association Override 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Association Override 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlAssociationOverride_2_1
	 * @generated
	 */
	public EClass getXmlAssociationOverride_2_1()
	{
		return xmlAssociationOverride_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlAssociationOverride_2_1#getForeignKey <em>Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlAssociationOverride_2_1#getForeignKey()
	 * @see #getXmlAssociationOverride_2_1()
	 * @generated
	 */
	public EReference getXmlAssociationOverride_2_1_ForeignKey()
	{
		return (EReference)xmlAssociationOverride_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlCollectionTable_2_1 <em>Xml Collection Table 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Collection Table 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlCollectionTable_2_1
	 * @generated
	 */
	public EClass getXmlCollectionTable_2_1()
	{
		return xmlCollectionTable_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlCollectionTable_2_1#getForeignKey <em>Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlCollectionTable_2_1#getForeignKey()
	 * @see #getXmlCollectionTable_2_1()
	 * @generated
	 */
	public EReference getXmlCollectionTable_2_1_ForeignKey()
	{
		return (EReference)xmlCollectionTable_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlCollectionTable_2_1#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlCollectionTable_2_1#getIndexes()
	 * @see #getXmlCollectionTable_2_1()
	 * @generated
	 */
	public EReference getXmlCollectionTable_2_1_Indexes()
	{
		return (EReference)xmlCollectionTable_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1 <em>Xml Convert 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Convert 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1
	 * @generated
	 */
	public EClass getXmlConvert_2_1()
	{
		return xmlConvert_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1#getDescription()
	 * @see #getXmlConvert_2_1()
	 * @generated
	 */
	public EAttribute getXmlConvert_2_1_Description()
	{
		return (EAttribute)xmlConvert_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1#getConverter <em>Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Converter</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1#getConverter()
	 * @see #getXmlConvert_2_1()
	 * @generated
	 */
	public EAttribute getXmlConvert_2_1_Converter()
	{
		return (EAttribute)xmlConvert_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1#getAttributeName <em>Attribute Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Name</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1#getAttributeName()
	 * @see #getXmlConvert_2_1()
	 * @generated
	 */
	public EAttribute getXmlConvert_2_1_AttributeName()
	{
		return (EAttribute)xmlConvert_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1#getDisableConversion <em>Disable Conversion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Disable Conversion</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1#getDisableConversion()
	 * @see #getXmlConvert_2_1()
	 * @generated
	 */
	public EAttribute getXmlConvert_2_1_DisableConversion()
	{
		return (EAttribute)xmlConvert_2_1EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1 <em>Xml Converter 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Converter 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1
	 * @generated
	 */
	public EClass getXmlConverter_2_1()
	{
		return xmlConverter_2_1EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1#getAutoApply <em>Auto Apply</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Apply</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1#getAutoApply()
	 * @see #getXmlConverter_2_1()
	 * @generated
	 */
	public EAttribute getXmlConverter_2_1_AutoApply()
	{
		return (EAttribute)xmlConverter_2_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntityMappings_2_1 <em>Xml Entity Mappings 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity Mappings 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntityMappings_2_1
	 * @generated
	 */
	public EClass getXmlEntityMappings_2_1()
	{
		return xmlEntityMappings_2_1EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1 <em>Xml Foreign Key 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Foreign Key 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1
	 * @generated
	 */
	public EClass getXmlForeignKey_2_1()
	{
		return xmlForeignKey_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getDescription()
	 * @see #getXmlForeignKey_2_1()
	 * @generated
	 */
	public EAttribute getXmlForeignKey_2_1_Description()
	{
		return (EAttribute)xmlForeignKey_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getName()
	 * @see #getXmlForeignKey_2_1()
	 * @generated
	 */
	public EAttribute getXmlForeignKey_2_1_Name()
	{
		return (EAttribute)xmlForeignKey_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getConstraintMode <em>Constraint Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Constraint Mode</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getConstraintMode()
	 * @see #getXmlForeignKey_2_1()
	 * @generated
	 */
	public EAttribute getXmlForeignKey_2_1_ConstraintMode()
	{
		return (EAttribute)xmlForeignKey_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getForeignKeyDefinition <em>Foreign Key Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Foreign Key Definition</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getForeignKeyDefinition()
	 * @see #getXmlForeignKey_2_1()
	 * @generated
	 */
	public EAttribute getXmlForeignKey_2_1_ForeignKeyDefinition()
	{
		return (EAttribute)xmlForeignKey_2_1EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1 <em>Xml Index 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Index 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1
	 * @generated
	 */
	public EClass getXmlIndex_2_1()
	{
		return xmlIndex_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1#getDescription()
	 * @see #getXmlIndex_2_1()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_1_Description()
	{
		return (EAttribute)xmlIndex_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1#getName()
	 * @see #getXmlIndex_2_1()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_1_Name()
	{
		return (EAttribute)xmlIndex_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1#getColumnList <em>Column List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column List</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1#getColumnList()
	 * @see #getXmlIndex_2_1()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_1_ColumnList()
	{
		return (EAttribute)xmlIndex_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1#getUnique <em>Unique</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1#getUnique()
	 * @see #getXmlIndex_2_1()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_1_Unique()
	{
		return (EAttribute)xmlIndex_2_1EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlJoinTable_2_1 <em>Xml Join Table 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Join Table 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlJoinTable_2_1
	 * @generated
	 */
	public EClass getXmlJoinTable_2_1()
	{
		return xmlJoinTable_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlJoinTable_2_1#getForeignKey <em>Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlJoinTable_2_1#getForeignKey()
	 * @see #getXmlJoinTable_2_1()
	 * @generated
	 */
	public EReference getXmlJoinTable_2_1_ForeignKey()
	{
		return (EReference)xmlJoinTable_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlJoinTable_2_1#getInverseForeignKey <em>Inverse Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inverse Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlJoinTable_2_1#getInverseForeignKey()
	 * @see #getXmlJoinTable_2_1()
	 * @generated
	 */
	public EReference getXmlJoinTable_2_1_InverseForeignKey()
	{
		return (EReference)xmlJoinTable_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlJoinTable_2_1#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlJoinTable_2_1#getIndexes()
	 * @see #getXmlJoinTable_2_1()
	 * @generated
	 */
	public EReference getXmlJoinTable_2_1_Indexes()
	{
		return (EReference)xmlJoinTable_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToMany_2_1 <em>Xml Many To Many 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToMany_2_1
	 * @generated
	 */
	public EClass getXmlManyToMany_2_1()
	{
		return xmlManyToMany_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToMany_2_1#getMapKeyConverts <em>Map Key Converts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Converts</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToMany_2_1#getMapKeyConverts()
	 * @see #getXmlManyToMany_2_1()
	 * @generated
	 */
	public EReference getXmlManyToMany_2_1_MapKeyConverts()
	{
		return (EReference)xmlManyToMany_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToMany_2_1#getMapKeyForeignKey <em>Map Key Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToMany_2_1#getMapKeyForeignKey()
	 * @see #getXmlManyToMany_2_1()
	 * @generated
	 */
	public EReference getXmlManyToMany_2_1_MapKeyForeignKey()
	{
		return (EReference)xmlManyToMany_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToOne_2_1 <em>Xml Many To One 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To One 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToOne_2_1
	 * @generated
	 */
	public EClass getXmlManyToOne_2_1()
	{
		return xmlManyToOne_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToOne_2_1#getForeignKey <em>Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToOne_2_1#getForeignKey()
	 * @see #getXmlManyToOne_2_1()
	 * @generated
	 */
	public EReference getXmlManyToOne_2_1_ForeignKey()
	{
		return (EReference)xmlManyToOne_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1 <em>Xml Named Attribute Node 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Attribute Node 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1
	 * @generated
	 */
	public EClass getXmlNamedAttributeNode_2_1()
	{
		return xmlNamedAttributeNode_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1#getName()
	 * @see #getXmlNamedAttributeNode_2_1()
	 * @generated
	 */
	public EAttribute getXmlNamedAttributeNode_2_1_Name()
	{
		return (EAttribute)xmlNamedAttributeNode_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1#getSubgraph <em>Subgraph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subgraph</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1#getSubgraph()
	 * @see #getXmlNamedAttributeNode_2_1()
	 * @generated
	 */
	public EAttribute getXmlNamedAttributeNode_2_1_Subgraph()
	{
		return (EAttribute)xmlNamedAttributeNode_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1#getKeySubgraph <em>Key Subgraph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key Subgraph</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1#getKeySubgraph()
	 * @see #getXmlNamedAttributeNode_2_1()
	 * @generated
	 */
	public EAttribute getXmlNamedAttributeNode_2_1_KeySubgraph()
	{
		return (EAttribute)xmlNamedAttributeNode_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1 <em>Xml Named Entity Graph 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Entity Graph 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1
	 * @generated
	 */
	public EClass getXmlNamedEntityGraph_2_1()
	{
		return xmlNamedEntityGraph_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1#getNamedAttributeNodes <em>Named Attribute Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Attribute Nodes</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1#getNamedAttributeNodes()
	 * @see #getXmlNamedEntityGraph_2_1()
	 * @generated
	 */
	public EReference getXmlNamedEntityGraph_2_1_NamedAttributeNodes()
	{
		return (EReference)xmlNamedEntityGraph_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1#getSubgraphs <em>Subgraphs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Subgraphs</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1#getSubgraphs()
	 * @see #getXmlNamedEntityGraph_2_1()
	 * @generated
	 */
	public EReference getXmlNamedEntityGraph_2_1_Subgraphs()
	{
		return (EReference)xmlNamedEntityGraph_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1#getSubclassSubgraphs <em>Subclass Subgraphs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Subclass Subgraphs</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1#getSubclassSubgraphs()
	 * @see #getXmlNamedEntityGraph_2_1()
	 * @generated
	 */
	public EReference getXmlNamedEntityGraph_2_1_SubclassSubgraphs()
	{
		return (EReference)xmlNamedEntityGraph_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1#getName()
	 * @see #getXmlNamedEntityGraph_2_1()
	 * @generated
	 */
	public EAttribute getXmlNamedEntityGraph_2_1_Name()
	{
		return (EAttribute)xmlNamedEntityGraph_2_1EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1#getIncludeAllAttributes <em>Include All Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Include All Attributes</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1#getIncludeAllAttributes()
	 * @see #getXmlNamedEntityGraph_2_1()
	 * @generated
	 */
	public EAttribute getXmlNamedEntityGraph_2_1_IncludeAllAttributes()
	{
		return (EAttribute)xmlNamedEntityGraph_2_1EClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlQueryContainer_2_1 <em>Xml Query Container 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Query Container 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlQueryContainer_2_1
	 * @generated
	 */
	public EClass getXmlQueryContainer_2_1()
	{
		return xmlQueryContainer_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlQueryContainer_2_1#getNamedStoredProcedureQueries <em>Named Stored Procedure Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Stored Procedure Queries</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlQueryContainer_2_1#getNamedStoredProcedureQueries()
	 * @see #getXmlQueryContainer_2_1()
	 * @generated
	 */
	public EReference getXmlQueryContainer_2_1_NamedStoredProcedureQueries()
	{
		return (EReference)xmlQueryContainer_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSecondaryTable_2_1 <em>Xml Secondary Table 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Secondary Table 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSecondaryTable_2_1
	 * @generated
	 */
	public EClass getXmlSecondaryTable_2_1()
	{
		return xmlSecondaryTable_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSecondaryTable_2_1#getPrimaryKeyForeignKey <em>Primary Key Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Primary Key Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSecondaryTable_2_1#getPrimaryKeyForeignKey()
	 * @see #getXmlSecondaryTable_2_1()
	 * @generated
	 */
	public EReference getXmlSecondaryTable_2_1_PrimaryKeyForeignKey()
	{
		return (EReference)xmlSecondaryTable_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSecondaryTable_2_1#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSecondaryTable_2_1#getIndexes()
	 * @see #getXmlSecondaryTable_2_1()
	 * @generated
	 */
	public EReference getXmlSecondaryTable_2_1_Indexes()
	{
		return (EReference)xmlSecondaryTable_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1 <em>Xml Named Stored Procedure Query 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Stored Procedure Query 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1
	 * @generated
	 */
	public EClass getXmlNamedStoredProcedureQuery_2_1()
	{
		return xmlNamedStoredProcedureQuery_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1#getResultClasses <em>Result Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Result Classes</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1#getResultClasses()
	 * @see #getXmlNamedStoredProcedureQuery_2_1()
	 * @generated
	 */
	public EAttribute getXmlNamedStoredProcedureQuery_2_1_ResultClasses()
	{
		return (EAttribute)xmlNamedStoredProcedureQuery_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1#getResultSetMappings <em>Result Set Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Result Set Mappings</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1#getResultSetMappings()
	 * @see #getXmlNamedStoredProcedureQuery_2_1()
	 * @generated
	 */
	public EAttribute getXmlNamedStoredProcedureQuery_2_1_ResultSetMappings()
	{
		return (EAttribute)xmlNamedStoredProcedureQuery_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1#getProcedureName <em>Procedure Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Procedure Name</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1#getProcedureName()
	 * @see #getXmlNamedStoredProcedureQuery_2_1()
	 * @generated
	 */
	public EAttribute getXmlNamedStoredProcedureQuery_2_1_ProcedureName()
	{
		return (EAttribute)xmlNamedStoredProcedureQuery_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1#getParameters()
	 * @see #getXmlNamedStoredProcedureQuery_2_1()
	 * @generated
	 */
	public EReference getXmlNamedStoredProcedureQuery_2_1_Parameters()
	{
		return (EReference)xmlNamedStoredProcedureQuery_2_1EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1 <em>Xml Named Subgraph 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Subgraph 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1
	 * @generated
	 */
	public EClass getXmlNamedSubgraph_2_1()
	{
		return xmlNamedSubgraph_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1#getNamedAttributeNodes <em>Named Attribute Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Attribute Nodes</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1#getNamedAttributeNodes()
	 * @see #getXmlNamedSubgraph_2_1()
	 * @generated
	 */
	public EReference getXmlNamedSubgraph_2_1_NamedAttributeNodes()
	{
		return (EReference)xmlNamedSubgraph_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1#getName()
	 * @see #getXmlNamedSubgraph_2_1()
	 * @generated
	 */
	public EAttribute getXmlNamedSubgraph_2_1_Name()
	{
		return (EAttribute)xmlNamedSubgraph_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1#getClassName()
	 * @see #getXmlNamedSubgraph_2_1()
	 * @generated
	 */
	public EAttribute getXmlNamedSubgraph_2_1_ClassName()
	{
		return (EAttribute)xmlNamedSubgraph_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToMany_2_1 <em>Xml One To Many 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToMany_2_1
	 * @generated
	 */
	public EClass getXmlOneToMany_2_1()
	{
		return xmlOneToMany_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToMany_2_1#getMapKeyConverts <em>Map Key Converts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Converts</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToMany_2_1#getMapKeyConverts()
	 * @see #getXmlOneToMany_2_1()
	 * @generated
	 */
	public EReference getXmlOneToMany_2_1_MapKeyConverts()
	{
		return (EReference)xmlOneToMany_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToMany_2_1#getMapKeyForeignKey <em>Map Key Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToMany_2_1#getMapKeyForeignKey()
	 * @see #getXmlOneToMany_2_1()
	 * @generated
	 */
	public EReference getXmlOneToMany_2_1_MapKeyForeignKey()
	{
		return (EReference)xmlOneToMany_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToMany_2_1#getForeignKey <em>Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToMany_2_1#getForeignKey()
	 * @see #getXmlOneToMany_2_1()
	 * @generated
	 */
	public EReference getXmlOneToMany_2_1_ForeignKey()
	{
		return (EReference)xmlOneToMany_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToOne_2_1 <em>Xml One To One 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToOne_2_1
	 * @generated
	 */
	public EClass getXmlOneToOne_2_1()
	{
		return xmlOneToOne_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToOne_2_1#getMapKeyConverts <em>Map Key Converts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Converts</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToOne_2_1#getMapKeyConverts()
	 * @see #getXmlOneToOne_2_1()
	 * @generated
	 */
	public EReference getXmlOneToOne_2_1_MapKeyConverts()
	{
		return (EReference)xmlOneToOne_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToOne_2_1#getPrimaryKeyForeignKey <em>Primary Key Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Primary Key Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToOne_2_1#getPrimaryKeyForeignKey()
	 * @see #getXmlOneToOne_2_1()
	 * @generated
	 */
	public EReference getXmlOneToOne_2_1_PrimaryKeyForeignKey()
	{
		return (EReference)xmlOneToOne_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToOne_2_1#getForeignKey <em>Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToOne_2_1#getForeignKey()
	 * @see #getXmlOneToOne_2_1()
	 * @generated
	 */
	public EReference getXmlOneToOne_2_1_ForeignKey()
	{
		return (EReference)xmlOneToOne_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1 <em>Xml Stored Procedure Parameter 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Stored Procedure Parameter 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1
	 * @generated
	 */
	public EClass getXmlStoredProcedureParameter_2_1()
	{
		return xmlStoredProcedureParameter_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1#getDescription()
	 * @see #getXmlStoredProcedureParameter_2_1()
	 * @generated
	 */
	public EAttribute getXmlStoredProcedureParameter_2_1_Description()
	{
		return (EAttribute)xmlStoredProcedureParameter_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1#getMode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1#getMode()
	 * @see #getXmlStoredProcedureParameter_2_1()
	 * @generated
	 */
	public EAttribute getXmlStoredProcedureParameter_2_1_Mode()
	{
		return (EAttribute)xmlStoredProcedureParameter_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1#getName()
	 * @see #getXmlStoredProcedureParameter_2_1()
	 * @generated
	 */
	public EAttribute getXmlStoredProcedureParameter_2_1_Name()
	{
		return (EAttribute)xmlStoredProcedureParameter_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1#getClassName()
	 * @see #getXmlStoredProcedureParameter_2_1()
	 * @generated
	 */
	public EAttribute getXmlStoredProcedureParameter_2_1_ClassName()
	{
		return (EAttribute)xmlStoredProcedureParameter_2_1EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSqlResultSetMapping_2_1 <em>Xml Sql Result Set Mapping 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Sql Result Set Mapping 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSqlResultSetMapping_2_1
	 * @generated
	 */
	public EClass getXmlSqlResultSetMapping_2_1()
	{
		return xmlSqlResultSetMapping_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSqlResultSetMapping_2_1#getConstructorResults <em>Constructor Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constructor Results</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSqlResultSetMapping_2_1#getConstructorResults()
	 * @see #getXmlSqlResultSetMapping_2_1()
	 * @generated
	 */
	public EReference getXmlSqlResultSetMapping_2_1_ConstructorResults()
	{
		return (EReference)xmlSqlResultSetMapping_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTable_2_1 <em>Xml Table 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Table 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTable_2_1
	 * @generated
	 */
	public EClass getXmlTable_2_1()
	{
		return xmlTable_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTable_2_1#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTable_2_1#getIndexes()
	 * @see #getXmlTable_2_1()
	 * @generated
	 */
	public EReference getXmlTable_2_1_Indexes()
	{
		return (EReference)xmlTable_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTableGenerator_2_1 <em>Xml Table Generator 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Table Generator 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTableGenerator_2_1
	 * @generated
	 */
	public EClass getXmlTableGenerator_2_1()
	{
		return xmlTableGenerator_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTableGenerator_2_1#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTableGenerator_2_1#getIndexes()
	 * @see #getXmlTableGenerator_2_1()
	 * @generated
	 */
	public EReference getXmlTableGenerator_2_1_Indexes()
	{
		return (EReference)xmlTableGenerator_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvertibleMapping_2_1 <em>Xml Convertible Mapping 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Convertible Mapping 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvertibleMapping_2_1
	 * @generated
	 */
	public EClass getXmlConvertibleMapping_2_1()
	{
		return xmlConvertibleMapping_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvertibleMapping_2_1#getConvert <em>Convert</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Convert</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvertibleMapping_2_1#getConvert()
	 * @see #getXmlConvertibleMapping_2_1()
	 * @generated
	 */
	public EReference getXmlConvertibleMapping_2_1_Convert()
	{
		return (EReference)xmlConvertibleMapping_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1 <em>Parameter Mode 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Parameter Mode 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1
	 * @generated
	 */
	public EEnum getParameterMode_2_1()
	{
		return parameterMode_2_1EEnum;
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstraintMode_2_1 <em>Constraint Mode 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Constraint Mode 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstraintMode_2_1
	 * @generated
	 */
	public EEnum getConstraintMode_2_1()
	{
		return constraintMode_2_1EEnum;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1 <em>Xml Converter Container 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Converter Container 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1
	 * @generated
	 */
	public EClass getXmlConverterContainer_2_1()
	{
		return xmlConverterContainer_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1#getConverters <em>Converters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Converters</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1#getConverters()
	 * @see #getXmlConverterContainer_2_1()
	 * @generated
	 */
	public EReference getXmlConverterContainer_2_1_Converters()
	{
		return (EReference)xmlConverterContainer_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlElementCollection_2_1 <em>Xml Element Collection 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Element Collection 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlElementCollection_2_1
	 * @generated
	 */
	public EClass getXmlElementCollection_2_1()
	{
		return xmlElementCollection_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlElementCollection_2_1#getMapKeyConverts <em>Map Key Converts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Converts</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlElementCollection_2_1#getMapKeyConverts()
	 * @see #getXmlElementCollection_2_1()
	 * @generated
	 */
	public EReference getXmlElementCollection_2_1_MapKeyConverts()
	{
		return (EReference)xmlElementCollection_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlElementCollection_2_1#getMapKeyForeignKey <em>Map Key Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlElementCollection_2_1#getMapKeyForeignKey()
	 * @see #getXmlElementCollection_2_1()
	 * @generated
	 */
	public EReference getXmlElementCollection_2_1_MapKeyForeignKey()
	{
		return (EReference)xmlElementCollection_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlElementCollection_2_1#getConverts <em>Converts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Converts</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlElementCollection_2_1#getConverts()
	 * @see #getXmlElementCollection_2_1()
	 * @generated
	 */
	public EReference getXmlElementCollection_2_1_Converts()
	{
		return (EReference)xmlElementCollection_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEmbedded_2_1 <em>Xml Embedded 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEmbedded_2_1
	 * @generated
	 */
	public EClass getXmlEmbedded_2_1()
	{
		return xmlEmbedded_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEmbedded_2_1#getConverts <em>Converts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Converts</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEmbedded_2_1#getConverts()
	 * @see #getXmlEmbedded_2_1()
	 * @generated
	 */
	public EReference getXmlEmbedded_2_1_Converts()
	{
		return (EReference)xmlEmbedded_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntity_2_1 <em>Xml Entity 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntity_2_1
	 * @generated
	 */
	public EClass getXmlEntity_2_1()
	{
		return xmlEntity_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntity_2_1#getPrimaryKeyForeignKey <em>Primary Key Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Primary Key Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntity_2_1#getPrimaryKeyForeignKey()
	 * @see #getXmlEntity_2_1()
	 * @generated
	 */
	public EReference getXmlEntity_2_1_PrimaryKeyForeignKey()
	{
		return (EReference)xmlEntity_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntity_2_1#getConverts <em>Converts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Converts</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntity_2_1#getConverts()
	 * @see #getXmlEntity_2_1()
	 * @generated
	 */
	public EReference getXmlEntity_2_1_Converts()
	{
		return (EReference)xmlEntity_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntity_2_1#getNamedEntityGraphs <em>Named Entity Graphs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Entity Graphs</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntity_2_1#getNamedEntityGraphs()
	 * @see #getXmlEntity_2_1()
	 * @generated
	 */
	public EReference getXmlEntity_2_1_NamedEntityGraphs()
	{
		return (EReference)xmlEntity_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public OrmV2_1Factory getOrmV2_1Factory()
	{
		return (OrmV2_1Factory)getEFactoryInstance();
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
		columnResult_2_1EClass = createEClass(COLUMN_RESULT_21);
		createEAttribute(columnResult_2_1EClass, COLUMN_RESULT_21__CLASS_NAME);

		constructorResult_2_1EClass = createEClass(CONSTRUCTOR_RESULT_21);
		createEAttribute(constructorResult_2_1EClass, CONSTRUCTOR_RESULT_21__TARGET_CLASS);
		createEReference(constructorResult_2_1EClass, CONSTRUCTOR_RESULT_21__COLUMN_RESULTS);

		xmlAssociationOverride_2_1EClass = createEClass(XML_ASSOCIATION_OVERRIDE_21);
		createEReference(xmlAssociationOverride_2_1EClass, XML_ASSOCIATION_OVERRIDE_21__FOREIGN_KEY);

		xmlCollectionTable_2_1EClass = createEClass(XML_COLLECTION_TABLE_21);
		createEReference(xmlCollectionTable_2_1EClass, XML_COLLECTION_TABLE_21__FOREIGN_KEY);
		createEReference(xmlCollectionTable_2_1EClass, XML_COLLECTION_TABLE_21__INDEXES);

		xmlConvert_2_1EClass = createEClass(XML_CONVERT_21);
		createEAttribute(xmlConvert_2_1EClass, XML_CONVERT_21__DESCRIPTION);
		createEAttribute(xmlConvert_2_1EClass, XML_CONVERT_21__CONVERTER);
		createEAttribute(xmlConvert_2_1EClass, XML_CONVERT_21__ATTRIBUTE_NAME);
		createEAttribute(xmlConvert_2_1EClass, XML_CONVERT_21__DISABLE_CONVERSION);

		xmlConverter_2_1EClass = createEClass(XML_CONVERTER_21);
		createEAttribute(xmlConverter_2_1EClass, XML_CONVERTER_21__AUTO_APPLY);

		xmlConverterContainer_2_1EClass = createEClass(XML_CONVERTER_CONTAINER_21);
		createEReference(xmlConverterContainer_2_1EClass, XML_CONVERTER_CONTAINER_21__CONVERTERS);

		xmlConvertibleMapping_2_1EClass = createEClass(XML_CONVERTIBLE_MAPPING_21);
		createEReference(xmlConvertibleMapping_2_1EClass, XML_CONVERTIBLE_MAPPING_21__CONVERT);

		xmlElementCollection_2_1EClass = createEClass(XML_ELEMENT_COLLECTION_21);
		createEReference(xmlElementCollection_2_1EClass, XML_ELEMENT_COLLECTION_21__MAP_KEY_CONVERTS);
		createEReference(xmlElementCollection_2_1EClass, XML_ELEMENT_COLLECTION_21__MAP_KEY_FOREIGN_KEY);
		createEReference(xmlElementCollection_2_1EClass, XML_ELEMENT_COLLECTION_21__CONVERTS);

		xmlEmbedded_2_1EClass = createEClass(XML_EMBEDDED_21);
		createEReference(xmlEmbedded_2_1EClass, XML_EMBEDDED_21__CONVERTS);

		xmlEntity_2_1EClass = createEClass(XML_ENTITY_21);
		createEReference(xmlEntity_2_1EClass, XML_ENTITY_21__PRIMARY_KEY_FOREIGN_KEY);
		createEReference(xmlEntity_2_1EClass, XML_ENTITY_21__CONVERTS);
		createEReference(xmlEntity_2_1EClass, XML_ENTITY_21__NAMED_ENTITY_GRAPHS);

		xmlEntityMappings_2_1EClass = createEClass(XML_ENTITY_MAPPINGS_21);

		xmlForeignKey_2_1EClass = createEClass(XML_FOREIGN_KEY_21);
		createEAttribute(xmlForeignKey_2_1EClass, XML_FOREIGN_KEY_21__DESCRIPTION);
		createEAttribute(xmlForeignKey_2_1EClass, XML_FOREIGN_KEY_21__NAME);
		createEAttribute(xmlForeignKey_2_1EClass, XML_FOREIGN_KEY_21__CONSTRAINT_MODE);
		createEAttribute(xmlForeignKey_2_1EClass, XML_FOREIGN_KEY_21__FOREIGN_KEY_DEFINITION);

		xmlIndex_2_1EClass = createEClass(XML_INDEX_21);
		createEAttribute(xmlIndex_2_1EClass, XML_INDEX_21__DESCRIPTION);
		createEAttribute(xmlIndex_2_1EClass, XML_INDEX_21__NAME);
		createEAttribute(xmlIndex_2_1EClass, XML_INDEX_21__COLUMN_LIST);
		createEAttribute(xmlIndex_2_1EClass, XML_INDEX_21__UNIQUE);

		xmlJoinTable_2_1EClass = createEClass(XML_JOIN_TABLE_21);
		createEReference(xmlJoinTable_2_1EClass, XML_JOIN_TABLE_21__FOREIGN_KEY);
		createEReference(xmlJoinTable_2_1EClass, XML_JOIN_TABLE_21__INVERSE_FOREIGN_KEY);
		createEReference(xmlJoinTable_2_1EClass, XML_JOIN_TABLE_21__INDEXES);

		xmlManyToMany_2_1EClass = createEClass(XML_MANY_TO_MANY_21);
		createEReference(xmlManyToMany_2_1EClass, XML_MANY_TO_MANY_21__MAP_KEY_CONVERTS);
		createEReference(xmlManyToMany_2_1EClass, XML_MANY_TO_MANY_21__MAP_KEY_FOREIGN_KEY);

		xmlManyToOne_2_1EClass = createEClass(XML_MANY_TO_ONE_21);
		createEReference(xmlManyToOne_2_1EClass, XML_MANY_TO_ONE_21__FOREIGN_KEY);

		xmlNamedAttributeNode_2_1EClass = createEClass(XML_NAMED_ATTRIBUTE_NODE_21);
		createEAttribute(xmlNamedAttributeNode_2_1EClass, XML_NAMED_ATTRIBUTE_NODE_21__NAME);
		createEAttribute(xmlNamedAttributeNode_2_1EClass, XML_NAMED_ATTRIBUTE_NODE_21__SUBGRAPH);
		createEAttribute(xmlNamedAttributeNode_2_1EClass, XML_NAMED_ATTRIBUTE_NODE_21__KEY_SUBGRAPH);

		xmlNamedEntityGraph_2_1EClass = createEClass(XML_NAMED_ENTITY_GRAPH_21);
		createEReference(xmlNamedEntityGraph_2_1EClass, XML_NAMED_ENTITY_GRAPH_21__NAMED_ATTRIBUTE_NODES);
		createEReference(xmlNamedEntityGraph_2_1EClass, XML_NAMED_ENTITY_GRAPH_21__SUBGRAPHS);
		createEReference(xmlNamedEntityGraph_2_1EClass, XML_NAMED_ENTITY_GRAPH_21__SUBCLASS_SUBGRAPHS);
		createEAttribute(xmlNamedEntityGraph_2_1EClass, XML_NAMED_ENTITY_GRAPH_21__NAME);
		createEAttribute(xmlNamedEntityGraph_2_1EClass, XML_NAMED_ENTITY_GRAPH_21__INCLUDE_ALL_ATTRIBUTES);

		xmlNamedStoredProcedureQuery_2_1EClass = createEClass(XML_NAMED_STORED_PROCEDURE_QUERY_21);
		createEAttribute(xmlNamedStoredProcedureQuery_2_1EClass, XML_NAMED_STORED_PROCEDURE_QUERY_21__RESULT_CLASSES);
		createEAttribute(xmlNamedStoredProcedureQuery_2_1EClass, XML_NAMED_STORED_PROCEDURE_QUERY_21__RESULT_SET_MAPPINGS);
		createEAttribute(xmlNamedStoredProcedureQuery_2_1EClass, XML_NAMED_STORED_PROCEDURE_QUERY_21__PROCEDURE_NAME);
		createEReference(xmlNamedStoredProcedureQuery_2_1EClass, XML_NAMED_STORED_PROCEDURE_QUERY_21__PARAMETERS);

		xmlNamedSubgraph_2_1EClass = createEClass(XML_NAMED_SUBGRAPH_21);
		createEReference(xmlNamedSubgraph_2_1EClass, XML_NAMED_SUBGRAPH_21__NAMED_ATTRIBUTE_NODES);
		createEAttribute(xmlNamedSubgraph_2_1EClass, XML_NAMED_SUBGRAPH_21__NAME);
		createEAttribute(xmlNamedSubgraph_2_1EClass, XML_NAMED_SUBGRAPH_21__CLASS_NAME);

		xmlOneToMany_2_1EClass = createEClass(XML_ONE_TO_MANY_21);
		createEReference(xmlOneToMany_2_1EClass, XML_ONE_TO_MANY_21__MAP_KEY_CONVERTS);
		createEReference(xmlOneToMany_2_1EClass, XML_ONE_TO_MANY_21__MAP_KEY_FOREIGN_KEY);
		createEReference(xmlOneToMany_2_1EClass, XML_ONE_TO_MANY_21__FOREIGN_KEY);

		xmlOneToOne_2_1EClass = createEClass(XML_ONE_TO_ONE_21);
		createEReference(xmlOneToOne_2_1EClass, XML_ONE_TO_ONE_21__MAP_KEY_CONVERTS);
		createEReference(xmlOneToOne_2_1EClass, XML_ONE_TO_ONE_21__PRIMARY_KEY_FOREIGN_KEY);
		createEReference(xmlOneToOne_2_1EClass, XML_ONE_TO_ONE_21__FOREIGN_KEY);

		xmlQueryContainer_2_1EClass = createEClass(XML_QUERY_CONTAINER_21);
		createEReference(xmlQueryContainer_2_1EClass, XML_QUERY_CONTAINER_21__NAMED_STORED_PROCEDURE_QUERIES);

		xmlSecondaryTable_2_1EClass = createEClass(XML_SECONDARY_TABLE_21);
		createEReference(xmlSecondaryTable_2_1EClass, XML_SECONDARY_TABLE_21__PRIMARY_KEY_FOREIGN_KEY);
		createEReference(xmlSecondaryTable_2_1EClass, XML_SECONDARY_TABLE_21__INDEXES);

		xmlStoredProcedureParameter_2_1EClass = createEClass(XML_STORED_PROCEDURE_PARAMETER_21);
		createEAttribute(xmlStoredProcedureParameter_2_1EClass, XML_STORED_PROCEDURE_PARAMETER_21__DESCRIPTION);
		createEAttribute(xmlStoredProcedureParameter_2_1EClass, XML_STORED_PROCEDURE_PARAMETER_21__MODE);
		createEAttribute(xmlStoredProcedureParameter_2_1EClass, XML_STORED_PROCEDURE_PARAMETER_21__NAME);
		createEAttribute(xmlStoredProcedureParameter_2_1EClass, XML_STORED_PROCEDURE_PARAMETER_21__CLASS_NAME);

		xmlSqlResultSetMapping_2_1EClass = createEClass(XML_SQL_RESULT_SET_MAPPING_21);
		createEReference(xmlSqlResultSetMapping_2_1EClass, XML_SQL_RESULT_SET_MAPPING_21__CONSTRUCTOR_RESULTS);

		xmlTable_2_1EClass = createEClass(XML_TABLE_21);
		createEReference(xmlTable_2_1EClass, XML_TABLE_21__INDEXES);

		xmlTableGenerator_2_1EClass = createEClass(XML_TABLE_GENERATOR_21);
		createEReference(xmlTableGenerator_2_1EClass, XML_TABLE_GENERATOR_21__INDEXES);

		// Create enums
		parameterMode_2_1EEnum = createEEnum(PARAMETER_MODE_21);
		constraintMode_2_1EEnum = createEEnum(CONSTRAINT_MODE_21);
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
		OrmPackage theOrmPackage = (OrmPackage)EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		xmlEntity_2_1EClass.getESuperTypes().add(this.getXmlQueryContainer_2_1());
		xmlEntityMappings_2_1EClass.getESuperTypes().add(this.getXmlConverterContainer_2_1());
		xmlEntityMappings_2_1EClass.getESuperTypes().add(this.getXmlQueryContainer_2_1());

		// Initialize classes and features; add operations and parameters
		initEClass(columnResult_2_1EClass, ColumnResult_2_1.class, "ColumnResult_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getColumnResult_2_1_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, ColumnResult_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(constructorResult_2_1EClass, ConstructorResult_2_1.class, "ConstructorResult_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConstructorResult_2_1_TargetClass(), theXMLTypePackage.getString(), "targetClass", null, 1, 1, ConstructorResult_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstructorResult_2_1_ColumnResults(), theOrmPackage.getColumnResult(), null, "columnResults", null, 0, -1, ConstructorResult_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlAssociationOverride_2_1EClass, XmlAssociationOverride_2_1.class, "XmlAssociationOverride_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlAssociationOverride_2_1_ForeignKey(), theOrmPackage.getXmlForeignKey(), null, "foreignKey", null, 0, 1, XmlAssociationOverride_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCollectionTable_2_1EClass, XmlCollectionTable_2_1.class, "XmlCollectionTable_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlCollectionTable_2_1_ForeignKey(), theOrmPackage.getXmlForeignKey(), null, "foreignKey", null, 0, 1, XmlCollectionTable_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlCollectionTable_2_1_Indexes(), theOrmPackage.getXmlIndex(), null, "indexes", null, 0, -1, XmlCollectionTable_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConvert_2_1EClass, XmlConvert_2_1.class, "XmlConvert_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlConvert_2_1_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlConvert_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlConvert_2_1_Converter(), theXMLTypePackage.getString(), "converter", null, 0, 1, XmlConvert_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlConvert_2_1_AttributeName(), theXMLTypePackage.getString(), "attributeName", null, 0, 1, XmlConvert_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlConvert_2_1_DisableConversion(), theXMLTypePackage.getBooleanObject(), "disableConversion", null, 0, 1, XmlConvert_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConverter_2_1EClass, XmlConverter_2_1.class, "XmlConverter_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlConverter_2_1_AutoApply(), theXMLTypePackage.getBooleanObject(), "autoApply", null, 0, 1, XmlConverter_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConverterContainer_2_1EClass, XmlConverterContainer_2_1.class, "XmlConverterContainer_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlConverterContainer_2_1_Converters(), theOrmPackage.getXmlConverter(), null, "converters", null, 0, -1, XmlConverterContainer_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConvertibleMapping_2_1EClass, XmlConvertibleMapping_2_1.class, "XmlConvertibleMapping_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlConvertibleMapping_2_1_Convert(), theOrmPackage.getXmlConvert(), null, "convert", null, 0, 1, XmlConvertibleMapping_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlElementCollection_2_1EClass, XmlElementCollection_2_1.class, "XmlElementCollection_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlElementCollection_2_1_MapKeyConverts(), theOrmPackage.getXmlConvert(), null, "mapKeyConverts", null, 0, -1, XmlElementCollection_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_2_1_MapKeyForeignKey(), theOrmPackage.getXmlForeignKey(), null, "mapKeyForeignKey", null, 0, 1, XmlElementCollection_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_2_1_Converts(), theOrmPackage.getXmlConvert(), null, "converts", null, 0, -1, XmlElementCollection_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEmbedded_2_1EClass, XmlEmbedded_2_1.class, "XmlEmbedded_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEmbedded_2_1_Converts(), theOrmPackage.getXmlConvert(), null, "converts", null, 0, -1, XmlEmbedded_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntity_2_1EClass, XmlEntity_2_1.class, "XmlEntity_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntity_2_1_PrimaryKeyForeignKey(), theOrmPackage.getXmlForeignKey(), null, "primaryKeyForeignKey", null, 0, 1, XmlEntity_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_2_1_Converts(), theOrmPackage.getXmlConvert(), null, "converts", null, 0, -1, XmlEntity_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_2_1_NamedEntityGraphs(), theOrmPackage.getXmlNamedEntityGraph(), null, "namedEntityGraphs", null, 0, -1, XmlEntity_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntityMappings_2_1EClass, XmlEntityMappings_2_1.class, "XmlEntityMappings_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlForeignKey_2_1EClass, XmlForeignKey_2_1.class, "XmlForeignKey_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlForeignKey_2_1_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlForeignKey_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlForeignKey_2_1_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlForeignKey_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlForeignKey_2_1_ConstraintMode(), this.getConstraintMode_2_1(), "constraintMode", null, 0, 1, XmlForeignKey_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlForeignKey_2_1_ForeignKeyDefinition(), theXMLTypePackage.getString(), "foreignKeyDefinition", null, 0, 1, XmlForeignKey_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlIndex_2_1EClass, XmlIndex_2_1.class, "XmlIndex_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlIndex_2_1_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlIndex_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlIndex_2_1_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlIndex_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlIndex_2_1_ColumnList(), theXMLTypePackage.getString(), "columnList", null, 1, 1, XmlIndex_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlIndex_2_1_Unique(), theXMLTypePackage.getBooleanObject(), "unique", null, 0, 1, XmlIndex_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlJoinTable_2_1EClass, XmlJoinTable_2_1.class, "XmlJoinTable_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlJoinTable_2_1_ForeignKey(), theOrmPackage.getXmlForeignKey(), null, "foreignKey", null, 0, 1, XmlJoinTable_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlJoinTable_2_1_InverseForeignKey(), theOrmPackage.getXmlForeignKey(), null, "inverseForeignKey", null, 0, 1, XmlJoinTable_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlJoinTable_2_1_Indexes(), this.getXmlIndex_2_1(), null, "indexes", null, 0, -1, XmlJoinTable_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlManyToMany_2_1EClass, XmlManyToMany_2_1.class, "XmlManyToMany_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlManyToMany_2_1_MapKeyConverts(), theOrmPackage.getXmlConvert(), null, "mapKeyConverts", null, 0, -1, XmlManyToMany_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlManyToMany_2_1_MapKeyForeignKey(), theOrmPackage.getXmlForeignKey(), null, "mapKeyForeignKey", null, 0, 1, XmlManyToMany_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlManyToOne_2_1EClass, XmlManyToOne_2_1.class, "XmlManyToOne_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlManyToOne_2_1_ForeignKey(), theOrmPackage.getXmlForeignKey(), null, "foreignKey", null, 0, 1, XmlManyToOne_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNamedAttributeNode_2_1EClass, XmlNamedAttributeNode_2_1.class, "XmlNamedAttributeNode_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlNamedAttributeNode_2_1_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, XmlNamedAttributeNode_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNamedAttributeNode_2_1_Subgraph(), theXMLTypePackage.getString(), "subgraph", null, 0, 1, XmlNamedAttributeNode_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNamedAttributeNode_2_1_KeySubgraph(), theXMLTypePackage.getString(), "keySubgraph", null, 0, 1, XmlNamedAttributeNode_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNamedEntityGraph_2_1EClass, XmlNamedEntityGraph_2_1.class, "XmlNamedEntityGraph_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlNamedEntityGraph_2_1_NamedAttributeNodes(), theOrmPackage.getXmlNamedAttributeNode(), null, "namedAttributeNodes", null, 0, -1, XmlNamedEntityGraph_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlNamedEntityGraph_2_1_Subgraphs(), theOrmPackage.getXmlNamedSubgraph(), null, "subgraphs", null, 0, -1, XmlNamedEntityGraph_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlNamedEntityGraph_2_1_SubclassSubgraphs(), theOrmPackage.getXmlNamedSubgraph(), null, "subclassSubgraphs", null, 0, -1, XmlNamedEntityGraph_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNamedEntityGraph_2_1_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, XmlNamedEntityGraph_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNamedEntityGraph_2_1_IncludeAllAttributes(), theXMLTypePackage.getBooleanObject(), "includeAllAttributes", null, 0, 1, XmlNamedEntityGraph_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNamedStoredProcedureQuery_2_1EClass, XmlNamedStoredProcedureQuery_2_1.class, "XmlNamedStoredProcedureQuery_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlNamedStoredProcedureQuery_2_1_ResultClasses(), theXMLTypePackage.getString(), "resultClasses", null, 0, -1, XmlNamedStoredProcedureQuery_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNamedStoredProcedureQuery_2_1_ResultSetMappings(), theXMLTypePackage.getString(), "resultSetMappings", null, 0, -1, XmlNamedStoredProcedureQuery_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNamedStoredProcedureQuery_2_1_ProcedureName(), theXMLTypePackage.getString(), "procedureName", null, 1, 1, XmlNamedStoredProcedureQuery_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlNamedStoredProcedureQuery_2_1_Parameters(), theOrmPackage.getXmlStoredProcedureParameter(), null, "parameters", null, 0, -1, XmlNamedStoredProcedureQuery_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNamedSubgraph_2_1EClass, XmlNamedSubgraph_2_1.class, "XmlNamedSubgraph_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlNamedSubgraph_2_1_NamedAttributeNodes(), theOrmPackage.getXmlNamedAttributeNode(), null, "namedAttributeNodes", null, 0, -1, XmlNamedSubgraph_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNamedSubgraph_2_1_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, XmlNamedSubgraph_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNamedSubgraph_2_1_ClassName(), theXMLTypePackage.getString(), "className", null, 0, 1, XmlNamedSubgraph_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToMany_2_1EClass, XmlOneToMany_2_1.class, "XmlOneToMany_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlOneToMany_2_1_MapKeyConverts(), theOrmPackage.getXmlConvert(), null, "mapKeyConverts", null, 0, -1, XmlOneToMany_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlOneToMany_2_1_MapKeyForeignKey(), theOrmPackage.getXmlForeignKey(), null, "mapKeyForeignKey", null, 0, 1, XmlOneToMany_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlOneToMany_2_1_ForeignKey(), theOrmPackage.getXmlForeignKey(), null, "foreignKey", null, 0, 1, XmlOneToMany_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToOne_2_1EClass, XmlOneToOne_2_1.class, "XmlOneToOne_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlOneToOne_2_1_MapKeyConverts(), theOrmPackage.getXmlConvert(), null, "mapKeyConverts", null, 0, -1, XmlOneToOne_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlOneToOne_2_1_PrimaryKeyForeignKey(), theOrmPackage.getXmlForeignKey(), null, "primaryKeyForeignKey", null, 0, 1, XmlOneToOne_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlOneToOne_2_1_ForeignKey(), theOrmPackage.getXmlForeignKey(), null, "foreignKey", null, 0, 1, XmlOneToOne_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlQueryContainer_2_1EClass, XmlQueryContainer_2_1.class, "XmlQueryContainer_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlQueryContainer_2_1_NamedStoredProcedureQueries(), theOrmPackage.getXmlNamedStoredProcedureQuery(), null, "namedStoredProcedureQueries", null, 0, -1, XmlQueryContainer_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlSecondaryTable_2_1EClass, XmlSecondaryTable_2_1.class, "XmlSecondaryTable_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlSecondaryTable_2_1_PrimaryKeyForeignKey(), theOrmPackage.getXmlForeignKey(), null, "primaryKeyForeignKey", null, 0, 1, XmlSecondaryTable_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlSecondaryTable_2_1_Indexes(), theOrmPackage.getXmlIndex(), null, "indexes", null, 0, -1, XmlSecondaryTable_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlStoredProcedureParameter_2_1EClass, XmlStoredProcedureParameter_2_1.class, "XmlStoredProcedureParameter_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlStoredProcedureParameter_2_1_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlStoredProcedureParameter_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlStoredProcedureParameter_2_1_Mode(), this.getParameterMode_2_1(), "mode", null, 0, 1, XmlStoredProcedureParameter_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlStoredProcedureParameter_2_1_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlStoredProcedureParameter_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlStoredProcedureParameter_2_1_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, XmlStoredProcedureParameter_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlSqlResultSetMapping_2_1EClass, XmlSqlResultSetMapping_2_1.class, "XmlSqlResultSetMapping_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlSqlResultSetMapping_2_1_ConstructorResults(), theOrmPackage.getConstructorResult(), null, "constructorResults", null, 0, -1, XmlSqlResultSetMapping_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTable_2_1EClass, XmlTable_2_1.class, "XmlTable_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlTable_2_1_Indexes(), theOrmPackage.getXmlIndex(), null, "indexes", null, 0, -1, XmlTable_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTableGenerator_2_1EClass, XmlTableGenerator_2_1.class, "XmlTableGenerator_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlTableGenerator_2_1_Indexes(), theOrmPackage.getXmlIndex(), null, "indexes", null, 0, -1, XmlTableGenerator_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(parameterMode_2_1EEnum, ParameterMode_2_1.class, "ParameterMode_2_1");
		addEEnumLiteral(parameterMode_2_1EEnum, ParameterMode_2_1.IN);
		addEEnumLiteral(parameterMode_2_1EEnum, ParameterMode_2_1.INOUT);
		addEEnumLiteral(parameterMode_2_1EEnum, ParameterMode_2_1.OUT);
		addEEnumLiteral(parameterMode_2_1EEnum, ParameterMode_2_1.REF_CURSOR);

		initEEnum(constraintMode_2_1EEnum, ConstraintMode_2_1.class, "ConstraintMode_2_1");
		addEEnumLiteral(constraintMode_2_1EEnum, ConstraintMode_2_1.CONSTRAINT);
		addEEnumLiteral(constraintMode_2_1EEnum, ConstraintMode_2_1.NO_CONSTRAINT);
		addEEnumLiteral(constraintMode_2_1EEnum, ConstraintMode_2_1.PROVIDER_DEFAULT);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ColumnResult_2_1 <em>Column Result 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ColumnResult_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getColumnResult_2_1()
		 * @generated
		 */
		public static final EClass COLUMN_RESULT_21 = eINSTANCE.getColumnResult_2_1();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN_RESULT_21__CLASS_NAME = eINSTANCE.getColumnResult_2_1_ClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstructorResult_2_1 <em>Constructor Result 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstructorResult_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getConstructorResult_2_1()
		 * @generated
		 */
		public static final EClass CONSTRUCTOR_RESULT_21 = eINSTANCE.getConstructorResult_2_1();

		/**
		 * The meta object literal for the '<em><b>Target Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute CONSTRUCTOR_RESULT_21__TARGET_CLASS = eINSTANCE.getConstructorResult_2_1_TargetClass();

		/**
		 * The meta object literal for the '<em><b>Column Results</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference CONSTRUCTOR_RESULT_21__COLUMN_RESULTS = eINSTANCE.getConstructorResult_2_1_ColumnResults();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlAssociationOverride_2_1 <em>Xml Association Override 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlAssociationOverride_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlAssociationOverride_2_1()
		 * @generated
		 */
		public static final EClass XML_ASSOCIATION_OVERRIDE_21 = eINSTANCE.getXmlAssociationOverride_2_1();

		/**
		 * The meta object literal for the '<em><b>Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ASSOCIATION_OVERRIDE_21__FOREIGN_KEY = eINSTANCE.getXmlAssociationOverride_2_1_ForeignKey();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlCollectionTable_2_1 <em>Xml Collection Table 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlCollectionTable_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlCollectionTable_2_1()
		 * @generated
		 */
		public static final EClass XML_COLLECTION_TABLE_21 = eINSTANCE.getXmlCollectionTable_2_1();

		/**
		 * The meta object literal for the '<em><b>Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_COLLECTION_TABLE_21__FOREIGN_KEY = eINSTANCE.getXmlCollectionTable_2_1_ForeignKey();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_COLLECTION_TABLE_21__INDEXES = eINSTANCE.getXmlCollectionTable_2_1_Indexes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1 <em>Xml Convert 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConvert_2_1()
		 * @generated
		 */
		public static final EClass XML_CONVERT_21 = eINSTANCE.getXmlConvert_2_1();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERT_21__DESCRIPTION = eINSTANCE.getXmlConvert_2_1_Description();

		/**
		 * The meta object literal for the '<em><b>Converter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERT_21__CONVERTER = eINSTANCE.getXmlConvert_2_1_Converter();

		/**
		 * The meta object literal for the '<em><b>Attribute Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERT_21__ATTRIBUTE_NAME = eINSTANCE.getXmlConvert_2_1_AttributeName();

		/**
		 * The meta object literal for the '<em><b>Disable Conversion</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERT_21__DISABLE_CONVERSION = eINSTANCE.getXmlConvert_2_1_DisableConversion();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1 <em>Xml Converter 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConverter_2_1()
		 * @generated
		 */
		public static final EClass XML_CONVERTER_21 = eINSTANCE.getXmlConverter_2_1();

		/**
		 * The meta object literal for the '<em><b>Auto Apply</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERTER_21__AUTO_APPLY = eINSTANCE.getXmlConverter_2_1_AutoApply();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntityMappings_2_1 <em>Xml Entity Mappings 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntityMappings_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlEntityMappings_2_1()
		 * @generated
		 */
		public static final EClass XML_ENTITY_MAPPINGS_21 = eINSTANCE.getXmlEntityMappings_2_1();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1 <em>Xml Foreign Key 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlForeignKey_2_1()
		 * @generated
		 */
		public static final EClass XML_FOREIGN_KEY_21 = eINSTANCE.getXmlForeignKey_2_1();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_FOREIGN_KEY_21__DESCRIPTION = eINSTANCE.getXmlForeignKey_2_1_Description();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_FOREIGN_KEY_21__NAME = eINSTANCE.getXmlForeignKey_2_1_Name();

		/**
		 * The meta object literal for the '<em><b>Constraint Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_FOREIGN_KEY_21__CONSTRAINT_MODE = eINSTANCE.getXmlForeignKey_2_1_ConstraintMode();

		/**
		 * The meta object literal for the '<em><b>Foreign Key Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_FOREIGN_KEY_21__FOREIGN_KEY_DEFINITION = eINSTANCE.getXmlForeignKey_2_1_ForeignKeyDefinition();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1 <em>Xml Index 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlIndex_2_1()
		 * @generated
		 */
		public static final EClass XML_INDEX_21 = eINSTANCE.getXmlIndex_2_1();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_INDEX_21__DESCRIPTION = eINSTANCE.getXmlIndex_2_1_Description();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_INDEX_21__NAME = eINSTANCE.getXmlIndex_2_1_Name();

		/**
		 * The meta object literal for the '<em><b>Column List</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_INDEX_21__COLUMN_LIST = eINSTANCE.getXmlIndex_2_1_ColumnList();

		/**
		 * The meta object literal for the '<em><b>Unique</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_INDEX_21__UNIQUE = eINSTANCE.getXmlIndex_2_1_Unique();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlJoinTable_2_1 <em>Xml Join Table 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlJoinTable_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlJoinTable_2_1()
		 * @generated
		 */
		public static final EClass XML_JOIN_TABLE_21 = eINSTANCE.getXmlJoinTable_2_1();

		/**
		 * The meta object literal for the '<em><b>Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_JOIN_TABLE_21__FOREIGN_KEY = eINSTANCE.getXmlJoinTable_2_1_ForeignKey();

		/**
		 * The meta object literal for the '<em><b>Inverse Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_JOIN_TABLE_21__INVERSE_FOREIGN_KEY = eINSTANCE.getXmlJoinTable_2_1_InverseForeignKey();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_JOIN_TABLE_21__INDEXES = eINSTANCE.getXmlJoinTable_2_1_Indexes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToMany_2_1 <em>Xml Many To Many 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToMany_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlManyToMany_2_1()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_MANY_21 = eINSTANCE.getXmlManyToMany_2_1();

		/**
		 * The meta object literal for the '<em><b>Map Key Converts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_MANY_21__MAP_KEY_CONVERTS = eINSTANCE.getXmlManyToMany_2_1_MapKeyConverts();

		/**
		 * The meta object literal for the '<em><b>Map Key Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_MANY_21__MAP_KEY_FOREIGN_KEY = eINSTANCE.getXmlManyToMany_2_1_MapKeyForeignKey();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToOne_2_1 <em>Xml Many To One 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToOne_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlManyToOne_2_1()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_ONE_21 = eINSTANCE.getXmlManyToOne_2_1();

		/**
		 * The meta object literal for the '<em><b>Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_ONE_21__FOREIGN_KEY = eINSTANCE.getXmlManyToOne_2_1_ForeignKey();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1 <em>Xml Named Attribute Node 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlNamedAttributeNode_2_1()
		 * @generated
		 */
		public static final EClass XML_NAMED_ATTRIBUTE_NODE_21 = eINSTANCE.getXmlNamedAttributeNode_2_1();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_ATTRIBUTE_NODE_21__NAME = eINSTANCE.getXmlNamedAttributeNode_2_1_Name();

		/**
		 * The meta object literal for the '<em><b>Subgraph</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_ATTRIBUTE_NODE_21__SUBGRAPH = eINSTANCE.getXmlNamedAttributeNode_2_1_Subgraph();

		/**
		 * The meta object literal for the '<em><b>Key Subgraph</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_ATTRIBUTE_NODE_21__KEY_SUBGRAPH = eINSTANCE.getXmlNamedAttributeNode_2_1_KeySubgraph();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1 <em>Xml Named Entity Graph 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlNamedEntityGraph_2_1()
		 * @generated
		 */
		public static final EClass XML_NAMED_ENTITY_GRAPH_21 = eINSTANCE.getXmlNamedEntityGraph_2_1();

		/**
		 * The meta object literal for the '<em><b>Named Attribute Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_NAMED_ENTITY_GRAPH_21__NAMED_ATTRIBUTE_NODES = eINSTANCE.getXmlNamedEntityGraph_2_1_NamedAttributeNodes();

		/**
		 * The meta object literal for the '<em><b>Subgraphs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_NAMED_ENTITY_GRAPH_21__SUBGRAPHS = eINSTANCE.getXmlNamedEntityGraph_2_1_Subgraphs();

		/**
		 * The meta object literal for the '<em><b>Subclass Subgraphs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_NAMED_ENTITY_GRAPH_21__SUBCLASS_SUBGRAPHS = eINSTANCE.getXmlNamedEntityGraph_2_1_SubclassSubgraphs();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_ENTITY_GRAPH_21__NAME = eINSTANCE.getXmlNamedEntityGraph_2_1_Name();

		/**
		 * The meta object literal for the '<em><b>Include All Attributes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_ENTITY_GRAPH_21__INCLUDE_ALL_ATTRIBUTES = eINSTANCE.getXmlNamedEntityGraph_2_1_IncludeAllAttributes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlQueryContainer_2_1 <em>Xml Query Container 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlQueryContainer_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlQueryContainer_2_1()
		 * @generated
		 */
		public static final EClass XML_QUERY_CONTAINER_21 = eINSTANCE.getXmlQueryContainer_2_1();

		/**
		 * The meta object literal for the '<em><b>Named Stored Procedure Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_QUERY_CONTAINER_21__NAMED_STORED_PROCEDURE_QUERIES = eINSTANCE.getXmlQueryContainer_2_1_NamedStoredProcedureQueries();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSecondaryTable_2_1 <em>Xml Secondary Table 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSecondaryTable_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlSecondaryTable_2_1()
		 * @generated
		 */
		public static final EClass XML_SECONDARY_TABLE_21 = eINSTANCE.getXmlSecondaryTable_2_1();

		/**
		 * The meta object literal for the '<em><b>Primary Key Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_SECONDARY_TABLE_21__PRIMARY_KEY_FOREIGN_KEY = eINSTANCE.getXmlSecondaryTable_2_1_PrimaryKeyForeignKey();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_SECONDARY_TABLE_21__INDEXES = eINSTANCE.getXmlSecondaryTable_2_1_Indexes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1 <em>Xml Named Stored Procedure Query 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlNamedStoredProcedureQuery_2_1()
		 * @generated
		 */
		public static final EClass XML_NAMED_STORED_PROCEDURE_QUERY_21 = eINSTANCE.getXmlNamedStoredProcedureQuery_2_1();

		/**
		 * The meta object literal for the '<em><b>Result Classes</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_STORED_PROCEDURE_QUERY_21__RESULT_CLASSES = eINSTANCE.getXmlNamedStoredProcedureQuery_2_1_ResultClasses();

		/**
		 * The meta object literal for the '<em><b>Result Set Mappings</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_STORED_PROCEDURE_QUERY_21__RESULT_SET_MAPPINGS = eINSTANCE.getXmlNamedStoredProcedureQuery_2_1_ResultSetMappings();

		/**
		 * The meta object literal for the '<em><b>Procedure Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_STORED_PROCEDURE_QUERY_21__PROCEDURE_NAME = eINSTANCE.getXmlNamedStoredProcedureQuery_2_1_ProcedureName();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_NAMED_STORED_PROCEDURE_QUERY_21__PARAMETERS = eINSTANCE.getXmlNamedStoredProcedureQuery_2_1_Parameters();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1 <em>Xml Named Subgraph 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlNamedSubgraph_2_1()
		 * @generated
		 */
		public static final EClass XML_NAMED_SUBGRAPH_21 = eINSTANCE.getXmlNamedSubgraph_2_1();

		/**
		 * The meta object literal for the '<em><b>Named Attribute Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_NAMED_SUBGRAPH_21__NAMED_ATTRIBUTE_NODES = eINSTANCE.getXmlNamedSubgraph_2_1_NamedAttributeNodes();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_SUBGRAPH_21__NAME = eINSTANCE.getXmlNamedSubgraph_2_1_Name();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_SUBGRAPH_21__CLASS_NAME = eINSTANCE.getXmlNamedSubgraph_2_1_ClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToMany_2_1 <em>Xml One To Many 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToMany_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlOneToMany_2_1()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_MANY_21 = eINSTANCE.getXmlOneToMany_2_1();

		/**
		 * The meta object literal for the '<em><b>Map Key Converts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_MANY_21__MAP_KEY_CONVERTS = eINSTANCE.getXmlOneToMany_2_1_MapKeyConverts();

		/**
		 * The meta object literal for the '<em><b>Map Key Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_MANY_21__MAP_KEY_FOREIGN_KEY = eINSTANCE.getXmlOneToMany_2_1_MapKeyForeignKey();

		/**
		 * The meta object literal for the '<em><b>Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_MANY_21__FOREIGN_KEY = eINSTANCE.getXmlOneToMany_2_1_ForeignKey();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToOne_2_1 <em>Xml One To One 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToOne_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlOneToOne_2_1()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_ONE_21 = eINSTANCE.getXmlOneToOne_2_1();

		/**
		 * The meta object literal for the '<em><b>Map Key Converts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_ONE_21__MAP_KEY_CONVERTS = eINSTANCE.getXmlOneToOne_2_1_MapKeyConverts();

		/**
		 * The meta object literal for the '<em><b>Primary Key Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_ONE_21__PRIMARY_KEY_FOREIGN_KEY = eINSTANCE.getXmlOneToOne_2_1_PrimaryKeyForeignKey();

		/**
		 * The meta object literal for the '<em><b>Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_ONE_21__FOREIGN_KEY = eINSTANCE.getXmlOneToOne_2_1_ForeignKey();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1 <em>Xml Stored Procedure Parameter 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlStoredProcedureParameter_2_1()
		 * @generated
		 */
		public static final EClass XML_STORED_PROCEDURE_PARAMETER_21 = eINSTANCE.getXmlStoredProcedureParameter_2_1();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_STORED_PROCEDURE_PARAMETER_21__DESCRIPTION = eINSTANCE.getXmlStoredProcedureParameter_2_1_Description();

		/**
		 * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_STORED_PROCEDURE_PARAMETER_21__MODE = eINSTANCE.getXmlStoredProcedureParameter_2_1_Mode();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_STORED_PROCEDURE_PARAMETER_21__NAME = eINSTANCE.getXmlStoredProcedureParameter_2_1_Name();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_STORED_PROCEDURE_PARAMETER_21__CLASS_NAME = eINSTANCE.getXmlStoredProcedureParameter_2_1_ClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSqlResultSetMapping_2_1 <em>Xml Sql Result Set Mapping 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSqlResultSetMapping_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlSqlResultSetMapping_2_1()
		 * @generated
		 */
		public static final EClass XML_SQL_RESULT_SET_MAPPING_21 = eINSTANCE.getXmlSqlResultSetMapping_2_1();

		/**
		 * The meta object literal for the '<em><b>Constructor Results</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_SQL_RESULT_SET_MAPPING_21__CONSTRUCTOR_RESULTS = eINSTANCE.getXmlSqlResultSetMapping_2_1_ConstructorResults();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTable_2_1 <em>Xml Table 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTable_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlTable_2_1()
		 * @generated
		 */
		public static final EClass XML_TABLE_21 = eINSTANCE.getXmlTable_2_1();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_TABLE_21__INDEXES = eINSTANCE.getXmlTable_2_1_Indexes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTableGenerator_2_1 <em>Xml Table Generator 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlTableGenerator_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlTableGenerator_2_1()
		 * @generated
		 */
		public static final EClass XML_TABLE_GENERATOR_21 = eINSTANCE.getXmlTableGenerator_2_1();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_TABLE_GENERATOR_21__INDEXES = eINSTANCE.getXmlTableGenerator_2_1_Indexes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvertibleMapping_2_1 <em>Xml Convertible Mapping 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvertibleMapping_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConvertibleMapping_2_1()
		 * @generated
		 */
		public static final EClass XML_CONVERTIBLE_MAPPING_21 = eINSTANCE.getXmlConvertibleMapping_2_1();

		/**
		 * The meta object literal for the '<em><b>Convert</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CONVERTIBLE_MAPPING_21__CONVERT = eINSTANCE.getXmlConvertibleMapping_2_1_Convert();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1 <em>Parameter Mode 21</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getParameterMode_2_1()
		 * @generated
		 */
		public static final EEnum PARAMETER_MODE_21 = eINSTANCE.getParameterMode_2_1();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstraintMode_2_1 <em>Constraint Mode 21</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstraintMode_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getConstraintMode_2_1()
		 * @generated
		 */
		public static final EEnum CONSTRAINT_MODE_21 = eINSTANCE.getConstraintMode_2_1();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1 <em>Xml Converter Container 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConverterContainer_2_1()
		 * @generated
		 */
		public static final EClass XML_CONVERTER_CONTAINER_21 = eINSTANCE.getXmlConverterContainer_2_1();

		/**
		 * The meta object literal for the '<em><b>Converters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CONVERTER_CONTAINER_21__CONVERTERS = eINSTANCE.getXmlConverterContainer_2_1_Converters();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlElementCollection_2_1 <em>Xml Element Collection 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlElementCollection_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlElementCollection_2_1()
		 * @generated
		 */
		public static final EClass XML_ELEMENT_COLLECTION_21 = eINSTANCE.getXmlElementCollection_2_1();

		/**
		 * The meta object literal for the '<em><b>Map Key Converts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION_21__MAP_KEY_CONVERTS = eINSTANCE.getXmlElementCollection_2_1_MapKeyConverts();

		/**
		 * The meta object literal for the '<em><b>Map Key Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION_21__MAP_KEY_FOREIGN_KEY = eINSTANCE.getXmlElementCollection_2_1_MapKeyForeignKey();

		/**
		 * The meta object literal for the '<em><b>Converts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION_21__CONVERTS = eINSTANCE.getXmlElementCollection_2_1_Converts();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEmbedded_2_1 <em>Xml Embedded 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEmbedded_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlEmbedded_2_1()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED_21 = eINSTANCE.getXmlEmbedded_2_1();

		/**
		 * The meta object literal for the '<em><b>Converts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EMBEDDED_21__CONVERTS = eINSTANCE.getXmlEmbedded_2_1_Converts();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntity_2_1 <em>Xml Entity 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntity_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlEntity_2_1()
		 * @generated
		 */
		public static final EClass XML_ENTITY_21 = eINSTANCE.getXmlEntity_2_1();

		/**
		 * The meta object literal for the '<em><b>Primary Key Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_21__PRIMARY_KEY_FOREIGN_KEY = eINSTANCE.getXmlEntity_2_1_PrimaryKeyForeignKey();

		/**
		 * The meta object literal for the '<em><b>Converts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_21__CONVERTS = eINSTANCE.getXmlEntity_2_1_Converts();

		/**
		 * The meta object literal for the '<em><b>Named Entity Graphs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_21__NAMED_ENTITY_GRAPHS = eINSTANCE.getXmlEntity_2_1_NamedEntityGraphs();

	}

} //OrmV2_1Package
