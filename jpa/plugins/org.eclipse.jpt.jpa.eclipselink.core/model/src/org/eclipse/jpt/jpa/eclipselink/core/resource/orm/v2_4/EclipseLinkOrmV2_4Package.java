/*******************************************************************************
 *  Copyright (c) 2011, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.EclipseLinkOrmV2_5Package;

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
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Factory
 * @model kind="package"
 * @generated
 */
public class EclipseLinkOrmV2_4Package extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "v2_4";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.eclipselink.orm.v2_4.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLinkOrmV2_4Package eINSTANCE = org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4 <em>Xml Generator Container2 4</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlGeneratorContainer2_4()
	 * @generated
	 */
	public static final int XML_GENERATOR_CONTAINER2_4 = 3;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR_CONTAINER2_4__SEQUENCE_GENERATOR = OrmPackage.XML_GENERATOR_CONTAINER__SEQUENCE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR_CONTAINER2_4__TABLE_GENERATOR = OrmPackage.XML_GENERATOR_CONTAINER__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Uuid Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR_CONTAINER2_4__UUID_GENERATOR = OrmPackage.XML_GENERATOR_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Generator Container2 4</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR_CONTAINER2_4_FEATURE_COUNT = OrmPackage.XML_GENERATOR_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4 <em>Xml Multitenant 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlMultitenant_2_4()
	 * @generated
	 */
	public static final int XML_MULTITENANT_24 = 15;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4 <em>Xml Element Collection 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlElementCollection_2_4()
	 * @generated
	 */
	public static final int XML_ELEMENT_COLLECTION_24 = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4 <em>Xml One To Many 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlOneToMany_2_4()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY_24 = 17;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4 <em>Xml Entity 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlEntity_2_4()
	 * @generated
	 */
	public static final int XML_ENTITY_24 = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlBasic_2_4 <em>Xml Basic 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlBasic_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlBasic_2_4()
	 * @generated
	 */
	public static final int XML_BASIC_24 = 0;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_24__SEQUENCE_GENERATOR = XML_GENERATOR_CONTAINER2_4__SEQUENCE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_24__TABLE_GENERATOR = XML_GENERATOR_CONTAINER2_4__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Uuid Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_24__UUID_GENERATOR = XML_GENERATOR_CONTAINER2_4__UUID_GENERATOR;

	/**
	 * The feature id for the '<em><b>Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_24__FIELD = XML_GENERATOR_CONTAINER2_4_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cache Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_24__CACHE_INDEX = XML_GENERATOR_CONTAINER2_4_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Basic 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_24_FEATURE_COUNT = XML_GENERATOR_CONTAINER2_4_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCache_2_4 <em>Xml Cache 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCache_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlCache_2_4()
	 * @generated
	 */
	public static final int XML_CACHE_24 = 1;

	/**
	 * The feature id for the '<em><b>Database Change Notification Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE_24__DATABASE_CHANGE_NOTIFICATION_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Xml Cache 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE_24_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCacheIndex_2_4 <em>Xml Cache Index 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCacheIndex_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlCacheIndex_2_4()
	 * @generated
	 */
	public static final int XML_CACHE_INDEX_24 = 2;

	/**
	 * The feature id for the '<em><b>Column Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE_INDEX_24__COLUMN_NAMES = 0;

	/**
	 * The number of structural features of the '<em>Xml Cache Index 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE_INDEX_24_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_24__FIELD = 0;

	/**
	 * The feature id for the '<em><b>Delete All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_24__DELETE_ALL = 1;

	/**
	 * The number of structural features of the '<em>Xml Element Collection 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_24_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbeddable_2_4 <em>Xml Embeddable 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbeddable_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlEmbeddable_2_4()
	 * @generated
	 */
	public static final int XML_EMBEDDABLE_24 = 5;

	/**
	 * The feature id for the '<em><b>No Sql</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_24__NO_SQL = 0;

	/**
	 * The number of structural features of the '<em>Xml Embeddable 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_24_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbedded_2_4 <em>Xml Embedded 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbedded_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlEmbedded_2_4()
	 * @generated
	 */
	public static final int XML_EMBEDDED_24 = 6;

	/**
	 * The feature id for the '<em><b>Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_24__FIELD = 0;

	/**
	 * The number of structural features of the '<em>Xml Embedded 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_24_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_24__SEQUENCE_GENERATOR = XML_GENERATOR_CONTAINER2_4__SEQUENCE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_24__TABLE_GENERATOR = XML_GENERATOR_CONTAINER2_4__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Uuid Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_24__UUID_GENERATOR = XML_GENERATOR_CONTAINER2_4__UUID_GENERATOR;

	/**
	 * The feature id for the '<em><b>Cache Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_24__CACHE_INDEX = XML_GENERATOR_CONTAINER2_4_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>No Sql</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_24__NO_SQL = XML_GENERATOR_CONTAINER2_4_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Entity 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_24_FEATURE_COUNT = XML_GENERATOR_CONTAINER2_4_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntityMappings_2_4 <em>Xml Entity Mappings 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntityMappings_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlEntityMappings_2_4()
	 * @generated
	 */
	public static final int XML_ENTITY_MAPPINGS_24 = 8;

	/**
	 * The feature id for the '<em><b>Uuid Generators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_24__UUID_GENERATORS = 0;

	/**
	 * The number of structural features of the '<em>Xml Entity Mappings 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_24_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlField_2_4 <em>Xml Field 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlField_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlField_2_4()
	 * @generated
	 */
	public static final int XML_FIELD_24 = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FIELD_24__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Field 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FIELD_24_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4 <em>Xml Id 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlId_2_4()
	 * @generated
	 */
	public static final int XML_ID_24 = 10;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_24__SEQUENCE_GENERATOR = XML_GENERATOR_CONTAINER2_4__SEQUENCE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_24__TABLE_GENERATOR = XML_GENERATOR_CONTAINER2_4__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Uuid Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_24__UUID_GENERATOR = XML_GENERATOR_CONTAINER2_4__UUID_GENERATOR;

	/**
	 * The feature id for the '<em><b>Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_24__FIELD = XML_GENERATOR_CONTAINER2_4_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cache Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_24__CACHE_INDEX = XML_GENERATOR_CONTAINER2_4_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Id 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_24_FEATURE_COUNT = XML_GENERATOR_CONTAINER2_4_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlJoinField_2_4 <em>Xml Join Field 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlJoinField_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlJoinField_2_4()
	 * @generated
	 */
	public static final int XML_JOIN_FIELD_24 = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_FIELD_24__NAME = 0;

	/**
	 * The feature id for the '<em><b>Referenced Field Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_FIELD_24__REFERENCED_FIELD_NAME = 1;

	/**
	 * The number of structural features of the '<em>Xml Join Field 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_FIELD_24_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToMany_2_4 <em>Xml Many To Many 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToMany_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlManyToMany_2_4()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY_24 = 12;

	/**
	 * The feature id for the '<em><b>Join Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_24__JOIN_FIELDS = 0;

	/**
	 * The number of structural features of the '<em>Xml Many To Many 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_24_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToOne_2_4 <em>Xml Many To One 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToOne_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlManyToOne_2_4()
	 * @generated
	 */
	public static final int XML_MANY_TO_ONE_24 = 13;

	/**
	 * The feature id for the '<em><b>Join Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_24__JOIN_FIELDS = 0;

	/**
	 * The number of structural features of the '<em>Xml Many To One 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_24_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMappedSuperclass_2_4 <em>Xml Mapped Superclass 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMappedSuperclass_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlMappedSuperclass_2_4()
	 * @generated
	 */
	public static final int XML_MAPPED_SUPERCLASS_24 = 14;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_24__SEQUENCE_GENERATOR = XML_GENERATOR_CONTAINER2_4__SEQUENCE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_24__TABLE_GENERATOR = XML_GENERATOR_CONTAINER2_4__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Uuid Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_24__UUID_GENERATOR = XML_GENERATOR_CONTAINER2_4__UUID_GENERATOR;

	/**
	 * The feature id for the '<em><b>Cache Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_24__CACHE_INDEX = XML_GENERATOR_CONTAINER2_4_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Mapped Superclass 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_24_FEATURE_COUNT = XML_GENERATOR_CONTAINER2_4_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Include Criteria</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTITENANT_24__INCLUDE_CRITERIA = 0;

	/**
	 * The feature id for the '<em><b>Tenant Table Discriminator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTITENANT_24__TENANT_TABLE_DISCRIMINATOR = 1;

	/**
	 * The number of structural features of the '<em>Xml Multitenant 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTITENANT_24_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlNoSql_2_4 <em>Xml No Sql 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlNoSql_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlNoSql_2_4()
	 * @generated
	 */
	public static final int XML_NO_SQL_24 = 16;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NO_SQL_24__DATA_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Data Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NO_SQL_24__DATA_FORMAT = 1;

	/**
	 * The number of structural features of the '<em>Xml No Sql 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NO_SQL_24_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Join Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_24__JOIN_FIELDS = 0;

	/**
	 * The feature id for the '<em><b>Delete All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_24__DELETE_ALL = 1;

	/**
	 * The number of structural features of the '<em>Xml One To Many 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_24_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToOne_2_4 <em>Xml One To One 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToOne_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlOneToOne_2_4()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE_24 = 18;

	/**
	 * The feature id for the '<em><b>Join Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_24__JOIN_FIELDS = 0;

	/**
	 * The number of structural features of the '<em>Xml One To One 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_24_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlTenantTableDiscriminator_2_4 <em>Xml Tenant Table Discriminator 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlTenantTableDiscriminator_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlTenantTableDiscriminator_2_4()
	 * @generated
	 */
	public static final int XML_TENANT_TABLE_DISCRIMINATOR_24 = 19;

	/**
	 * The feature id for the '<em><b>Context Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TENANT_TABLE_DISCRIMINATOR_24__CONTEXT_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TENANT_TABLE_DISCRIMINATOR_24__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Xml Tenant Table Discriminator 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TENANT_TABLE_DISCRIMINATOR_24_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlUuidGenerator_2_4 <em>Xml Uuid Generator 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlUuidGenerator_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlUuidGenerator_2_4()
	 * @generated
	 */
	public static final int XML_UUID_GENERATOR_24 = 20;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UUID_GENERATOR_24__DESCRIPTION = OrmPackage.XML_GENERATOR__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UUID_GENERATOR_24__NAME = OrmPackage.XML_GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UUID_GENERATOR_24__INITIAL_VALUE = OrmPackage.XML_GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UUID_GENERATOR_24__ALLOCATION_SIZE = OrmPackage.XML_GENERATOR__ALLOCATION_SIZE;

	/**
	 * The number of structural features of the '<em>Xml Uuid Generator 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UUID_GENERATOR_24_FEATURE_COUNT = OrmPackage.XML_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DatabaseChangeNotificationType <em>Database Change Notification Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DatabaseChangeNotificationType
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getDatabaseChangeNotificationType()
	 * @generated
	 */
	public static final int DATABASE_CHANGE_NOTIFICATION_TYPE = 21;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.TenantTableDiscriminatorType <em>Tenant Table Discriminator Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.TenantTableDiscriminatorType
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getTenantTableDiscriminatorType()
	 * @generated
	 */
	public static final int TENANT_TABLE_DISCRIMINATOR_TYPE = 22;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DataFormatType <em>Data Format Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DataFormatType
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getDataFormatType()
	 * @generated
	 */
	public static final int DATA_FORMAT_TYPE = 23;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMultitenant_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNoSql_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlElementCollection_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEmbeddable_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEmbedded_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOneToMany_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOneToOne_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTenantTableDiscriminator_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlUuidGenerator_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntity_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntityMappings_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlField_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBasic_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCache_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCacheIndex_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlGeneratorContainer2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlId_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlJoinField_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlManyToMany_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlManyToOne_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMappedSuperclass_2_4EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum databaseChangeNotificationTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum tenantTableDiscriminatorTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dataFormatTypeEEnum = null;

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EclipseLinkOrmV2_4Package()
	{
		super(eNS_URI, EclipseLinkOrmV2_4Factory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EclipseLinkOrmV2_4Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EclipseLinkOrmV2_4Package init()
	{
		if (isInited) return (EclipseLinkOrmV2_4Package)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_4Package.eNS_URI);

		// Obtain or create and register package
		EclipseLinkOrmV2_4Package theEclipseLinkOrmV2_4Package = (EclipseLinkOrmV2_4Package)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EclipseLinkOrmV2_4Package ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EclipseLinkOrmV2_4Package());

		isInited = true;

		// Initialize simple dependencies
		OrmPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		EclipseLinkOrmPackage theEclipseLinkOrmPackage = (EclipseLinkOrmPackage)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) instanceof EclipseLinkOrmPackage ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) : EclipseLinkOrmPackage.eINSTANCE);
		EclipseLinkOrmV1_1Package theEclipseLinkOrmV1_1Package = (EclipseLinkOrmV1_1Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI) instanceof EclipseLinkOrmV1_1Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI) : EclipseLinkOrmV1_1Package.eINSTANCE);
		EclipseLinkOrmV2_0Package theEclipseLinkOrmV2_0Package = (EclipseLinkOrmV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) instanceof EclipseLinkOrmV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) : EclipseLinkOrmV2_0Package.eINSTANCE);
		EclipseLinkOrmV2_1Package theEclipseLinkOrmV2_1Package = (EclipseLinkOrmV2_1Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI) instanceof EclipseLinkOrmV2_1Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI) : EclipseLinkOrmV2_1Package.eINSTANCE);
		EclipseLinkOrmV2_2Package theEclipseLinkOrmV2_2Package = (EclipseLinkOrmV2_2Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) instanceof EclipseLinkOrmV2_2Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) : EclipseLinkOrmV2_2Package.eINSTANCE);
		EclipseLinkOrmV2_3Package theEclipseLinkOrmV2_3Package = (EclipseLinkOrmV2_3Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_3Package.eNS_URI) instanceof EclipseLinkOrmV2_3Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_3Package.eNS_URI) : EclipseLinkOrmV2_3Package.eINSTANCE);
		EclipseLinkOrmV2_5Package theEclipseLinkOrmV2_5Package = (EclipseLinkOrmV2_5Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_5Package.eNS_URI) instanceof EclipseLinkOrmV2_5Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_5Package.eNS_URI) : EclipseLinkOrmV2_5Package.eINSTANCE);

		// Create package meta-data objects
		theEclipseLinkOrmV2_4Package.createPackageContents();
		theEclipseLinkOrmPackage.createPackageContents();
		theEclipseLinkOrmV1_1Package.createPackageContents();
		theEclipseLinkOrmV2_0Package.createPackageContents();
		theEclipseLinkOrmV2_1Package.createPackageContents();
		theEclipseLinkOrmV2_2Package.createPackageContents();
		theEclipseLinkOrmV2_3Package.createPackageContents();
		theEclipseLinkOrmV2_5Package.createPackageContents();

		// Initialize created meta-data
		theEclipseLinkOrmV2_4Package.initializePackageContents();
		theEclipseLinkOrmPackage.initializePackageContents();
		theEclipseLinkOrmV1_1Package.initializePackageContents();
		theEclipseLinkOrmV2_0Package.initializePackageContents();
		theEclipseLinkOrmV2_1Package.initializePackageContents();
		theEclipseLinkOrmV2_2Package.initializePackageContents();
		theEclipseLinkOrmV2_3Package.initializePackageContents();
		theEclipseLinkOrmV2_5Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEclipseLinkOrmV2_4Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EclipseLinkOrmV2_4Package.eNS_URI, theEclipseLinkOrmV2_4Package);
		return theEclipseLinkOrmV2_4Package;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4 <em>Xml Multitenant 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Multitenant 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4
	 * @generated
	 */
	public EClass getXmlMultitenant_2_4()
	{
		return xmlMultitenant_2_4EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4#getIncludeCriteria <em>Include Criteria</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Include Criteria</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4#getIncludeCriteria()
	 * @see #getXmlMultitenant_2_4()
	 * @generated
	 */
	public EAttribute getXmlMultitenant_2_4_IncludeCriteria()
	{
		return (EAttribute)xmlMultitenant_2_4EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4#getTenantTableDiscriminator <em>Tenant Table Discriminator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tenant Table Discriminator</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4#getTenantTableDiscriminator()
	 * @see #getXmlMultitenant_2_4()
	 * @generated
	 */
	public EReference getXmlMultitenant_2_4_TenantTableDiscriminator()
	{
		return (EReference)xmlMultitenant_2_4EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlNoSql_2_4 <em>Xml No Sql 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml No Sql 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlNoSql_2_4
	 * @generated
	 */
	public EClass getXmlNoSql_2_4()
	{
		return xmlNoSql_2_4EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlNoSql_2_4#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlNoSql_2_4#getDataType()
	 * @see #getXmlNoSql_2_4()
	 * @generated
	 */
	public EAttribute getXmlNoSql_2_4_DataType()
	{
		return (EAttribute)xmlNoSql_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlNoSql_2_4#getDataFormat <em>Data Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Format</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlNoSql_2_4#getDataFormat()
	 * @see #getXmlNoSql_2_4()
	 * @generated
	 */
	public EAttribute getXmlNoSql_2_4_DataFormat()
	{
		return (EAttribute)xmlNoSql_2_4EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4 <em>Xml Element Collection 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Element Collection 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4
	 * @generated
	 */
	public EClass getXmlElementCollection_2_4()
	{
		return xmlElementCollection_2_4EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4#getField <em>Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Field</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4#getField()
	 * @see #getXmlElementCollection_2_4()
	 * @generated
	 */
	public EReference getXmlElementCollection_2_4_Field()
	{
		return (EReference)xmlElementCollection_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4#isDeleteAll <em>Delete All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delete All</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4#isDeleteAll()
	 * @see #getXmlElementCollection_2_4()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_2_4_DeleteAll()
	{
		return (EAttribute)xmlElementCollection_2_4EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbeddable_2_4 <em>Xml Embeddable 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embeddable 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbeddable_2_4
	 * @generated
	 */
	public EClass getXmlEmbeddable_2_4()
	{
		return xmlEmbeddable_2_4EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbeddable_2_4#getNoSql <em>No Sql</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>No Sql</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbeddable_2_4#getNoSql()
	 * @see #getXmlEmbeddable_2_4()
	 * @generated
	 */
	public EReference getXmlEmbeddable_2_4_NoSql()
	{
		return (EReference)xmlEmbeddable_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbedded_2_4 <em>Xml Embedded 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbedded_2_4
	 * @generated
	 */
	public EClass getXmlEmbedded_2_4()
	{
		return xmlEmbedded_2_4EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbedded_2_4#getField <em>Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Field</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbedded_2_4#getField()
	 * @see #getXmlEmbedded_2_4()
	 * @generated
	 */
	public EReference getXmlEmbedded_2_4_Field()
	{
		return (EReference)xmlEmbedded_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4 <em>Xml One To Many 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4
	 * @generated
	 */
	public EClass getXmlOneToMany_2_4()
	{
		return xmlOneToMany_2_4EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4#getJoinFields <em>Join Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Fields</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4#getJoinFields()
	 * @see #getXmlOneToMany_2_4()
	 * @generated
	 */
	public EReference getXmlOneToMany_2_4_JoinFields()
	{
		return (EReference)xmlOneToMany_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4#isDeleteAll <em>Delete All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delete All</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4#isDeleteAll()
	 * @see #getXmlOneToMany_2_4()
	 * @generated
	 */
	public EAttribute getXmlOneToMany_2_4_DeleteAll()
	{
		return (EAttribute)xmlOneToMany_2_4EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToOne_2_4 <em>Xml One To One 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToOne_2_4
	 * @generated
	 */
	public EClass getXmlOneToOne_2_4()
	{
		return xmlOneToOne_2_4EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToOne_2_4#getJoinFields <em>Join Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Fields</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToOne_2_4#getJoinFields()
	 * @see #getXmlOneToOne_2_4()
	 * @generated
	 */
	public EReference getXmlOneToOne_2_4_JoinFields()
	{
		return (EReference)xmlOneToOne_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlTenantTableDiscriminator_2_4 <em>Xml Tenant Table Discriminator 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Tenant Table Discriminator 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlTenantTableDiscriminator_2_4
	 * @generated
	 */
	public EClass getXmlTenantTableDiscriminator_2_4()
	{
		return xmlTenantTableDiscriminator_2_4EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlTenantTableDiscriminator_2_4#getContextProperty <em>Context Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Context Property</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlTenantTableDiscriminator_2_4#getContextProperty()
	 * @see #getXmlTenantTableDiscriminator_2_4()
	 * @generated
	 */
	public EAttribute getXmlTenantTableDiscriminator_2_4_ContextProperty()
	{
		return (EAttribute)xmlTenantTableDiscriminator_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlTenantTableDiscriminator_2_4#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlTenantTableDiscriminator_2_4#getType()
	 * @see #getXmlTenantTableDiscriminator_2_4()
	 * @generated
	 */
	public EAttribute getXmlTenantTableDiscriminator_2_4_Type()
	{
		return (EAttribute)xmlTenantTableDiscriminator_2_4EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlUuidGenerator_2_4 <em>Xml Uuid Generator 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Uuid Generator 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlUuidGenerator_2_4
	 * @generated
	 */
	public EClass getXmlUuidGenerator_2_4()
	{
		return xmlUuidGenerator_2_4EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4 <em>Xml Entity 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4
	 * @generated
	 */
	public EClass getXmlEntity_2_4()
	{
		return xmlEntity_2_4EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4#getCacheIndex <em>Cache Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Index</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4#getCacheIndex()
	 * @see #getXmlEntity_2_4()
	 * @generated
	 */
	public EReference getXmlEntity_2_4_CacheIndex()
	{
		return (EReference)xmlEntity_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4#getNoSql <em>No Sql</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>No Sql</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4#getNoSql()
	 * @see #getXmlEntity_2_4()
	 * @generated
	 */
	public EReference getXmlEntity_2_4_NoSql()
	{
		return (EReference)xmlEntity_2_4EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntityMappings_2_4 <em>Xml Entity Mappings 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity Mappings 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntityMappings_2_4
	 * @generated
	 */
	public EClass getXmlEntityMappings_2_4()
	{
		return xmlEntityMappings_2_4EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntityMappings_2_4#getUuidGenerators <em>Uuid Generators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Uuid Generators</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntityMappings_2_4#getUuidGenerators()
	 * @see #getXmlEntityMappings_2_4()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_4_UuidGenerators()
	{
		return (EReference)xmlEntityMappings_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlField_2_4 <em>Xml Field 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Field 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlField_2_4
	 * @generated
	 */
	public EClass getXmlField_2_4()
	{
		return xmlField_2_4EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlField_2_4#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlField_2_4#getName()
	 * @see #getXmlField_2_4()
	 * @generated
	 */
	public EAttribute getXmlField_2_4_Name()
	{
		return (EAttribute)xmlField_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlBasic_2_4 <em>Xml Basic 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlBasic_2_4
	 * @generated
	 */
	public EClass getXmlBasic_2_4()
	{
		return xmlBasic_2_4EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlBasic_2_4#getField <em>Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Field</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlBasic_2_4#getField()
	 * @see #getXmlBasic_2_4()
	 * @generated
	 */
	public EReference getXmlBasic_2_4_Field()
	{
		return (EReference)xmlBasic_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlBasic_2_4#getCacheIndex <em>Cache Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Index</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlBasic_2_4#getCacheIndex()
	 * @see #getXmlBasic_2_4()
	 * @generated
	 */
	public EReference getXmlBasic_2_4_CacheIndex()
	{
		return (EReference)xmlBasic_2_4EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCache_2_4 <em>Xml Cache 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Cache 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCache_2_4
	 * @generated
	 */
	public EClass getXmlCache_2_4()
	{
		return xmlCache_2_4EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCache_2_4#getDatabaseChangeNotificationType <em>Database Change Notification Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Database Change Notification Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCache_2_4#getDatabaseChangeNotificationType()
	 * @see #getXmlCache_2_4()
	 * @generated
	 */
	public EAttribute getXmlCache_2_4_DatabaseChangeNotificationType()
	{
		return (EAttribute)xmlCache_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCacheIndex_2_4 <em>Xml Cache Index 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Cache Index 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCacheIndex_2_4
	 * @generated
	 */
	public EClass getXmlCacheIndex_2_4()
	{
		return xmlCacheIndex_2_4EClass;
	}


	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCacheIndex_2_4#getColumnNames <em>Column Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Column Names</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCacheIndex_2_4#getColumnNames()
	 * @see #getXmlCacheIndex_2_4()
	 * @generated
	 */
	public EAttribute getXmlCacheIndex_2_4_ColumnNames()
	{
		return (EAttribute)xmlCacheIndex_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4 <em>Xml Generator Container2 4</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Generator Container2 4</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4
	 * @generated
	 */
	public EClass getXmlGeneratorContainer2_4()
	{
		return xmlGeneratorContainer2_4EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4#getUuidGenerator <em>Uuid Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Uuid Generator</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4#getUuidGenerator()
	 * @see #getXmlGeneratorContainer2_4()
	 * @generated
	 */
	public EReference getXmlGeneratorContainer2_4_UuidGenerator()
	{
		return (EReference)xmlGeneratorContainer2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4 <em>Xml Id 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4
	 * @generated
	 */
	public EClass getXmlId_2_4()
	{
		return xmlId_2_4EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4#getField <em>Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Field</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4#getField()
	 * @see #getXmlId_2_4()
	 * @generated
	 */
	public EReference getXmlId_2_4_Field()
	{
		return (EReference)xmlId_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4#getCacheIndex <em>Cache Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Index</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4#getCacheIndex()
	 * @see #getXmlId_2_4()
	 * @generated
	 */
	public EReference getXmlId_2_4_CacheIndex()
	{
		return (EReference)xmlId_2_4EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlJoinField_2_4 <em>Xml Join Field 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Join Field 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlJoinField_2_4
	 * @generated
	 */
	public EClass getXmlJoinField_2_4()
	{
		return xmlJoinField_2_4EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlJoinField_2_4#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlJoinField_2_4#getName()
	 * @see #getXmlJoinField_2_4()
	 * @generated
	 */
	public EAttribute getXmlJoinField_2_4_Name()
	{
		return (EAttribute)xmlJoinField_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlJoinField_2_4#getReferencedFieldName <em>Referenced Field Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Referenced Field Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlJoinField_2_4#getReferencedFieldName()
	 * @see #getXmlJoinField_2_4()
	 * @generated
	 */
	public EAttribute getXmlJoinField_2_4_ReferencedFieldName()
	{
		return (EAttribute)xmlJoinField_2_4EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToMany_2_4 <em>Xml Many To Many 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToMany_2_4
	 * @generated
	 */
	public EClass getXmlManyToMany_2_4()
	{
		return xmlManyToMany_2_4EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToMany_2_4#getJoinFields <em>Join Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Fields</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToMany_2_4#getJoinFields()
	 * @see #getXmlManyToMany_2_4()
	 * @generated
	 */
	public EReference getXmlManyToMany_2_4_JoinFields()
	{
		return (EReference)xmlManyToMany_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToOne_2_4 <em>Xml Many To One 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To One 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToOne_2_4
	 * @generated
	 */
	public EClass getXmlManyToOne_2_4()
	{
		return xmlManyToOne_2_4EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToOne_2_4#getJoinFields <em>Join Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Fields</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToOne_2_4#getJoinFields()
	 * @see #getXmlManyToOne_2_4()
	 * @generated
	 */
	public EReference getXmlManyToOne_2_4_JoinFields()
	{
		return (EReference)xmlManyToOne_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMappedSuperclass_2_4 <em>Xml Mapped Superclass 24</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mapped Superclass 24</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMappedSuperclass_2_4
	 * @generated
	 */
	public EClass getXmlMappedSuperclass_2_4()
	{
		return xmlMappedSuperclass_2_4EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMappedSuperclass_2_4#getCacheIndex <em>Cache Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Index</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMappedSuperclass_2_4#getCacheIndex()
	 * @see #getXmlMappedSuperclass_2_4()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_2_4_CacheIndex()
	{
		return (EReference)xmlMappedSuperclass_2_4EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DatabaseChangeNotificationType <em>Database Change Notification Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Database Change Notification Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DatabaseChangeNotificationType
	 * @generated
	 */
	public EEnum getDatabaseChangeNotificationType()
	{
		return databaseChangeNotificationTypeEEnum;
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.TenantTableDiscriminatorType <em>Tenant Table Discriminator Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Tenant Table Discriminator Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.TenantTableDiscriminatorType
	 * @generated
	 */
	public EEnum getTenantTableDiscriminatorType()
	{
		return tenantTableDiscriminatorTypeEEnum;
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DataFormatType <em>Data Format Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Data Format Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DataFormatType
	 * @generated
	 */
	public EEnum getDataFormatType()
	{
		return dataFormatTypeEEnum;
	}


	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public EclipseLinkOrmV2_4Factory getEclipseLinkOrmV2_4Factory()
	{
		return (EclipseLinkOrmV2_4Factory)getEFactoryInstance();
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
		xmlBasic_2_4EClass = createEClass(XML_BASIC_24);
		createEReference(xmlBasic_2_4EClass, XML_BASIC_24__FIELD);
		createEReference(xmlBasic_2_4EClass, XML_BASIC_24__CACHE_INDEX);

		xmlCache_2_4EClass = createEClass(XML_CACHE_24);
		createEAttribute(xmlCache_2_4EClass, XML_CACHE_24__DATABASE_CHANGE_NOTIFICATION_TYPE);

		xmlCacheIndex_2_4EClass = createEClass(XML_CACHE_INDEX_24);
		createEAttribute(xmlCacheIndex_2_4EClass, XML_CACHE_INDEX_24__COLUMN_NAMES);

		xmlGeneratorContainer2_4EClass = createEClass(XML_GENERATOR_CONTAINER2_4);
		createEReference(xmlGeneratorContainer2_4EClass, XML_GENERATOR_CONTAINER2_4__UUID_GENERATOR);

		xmlElementCollection_2_4EClass = createEClass(XML_ELEMENT_COLLECTION_24);
		createEReference(xmlElementCollection_2_4EClass, XML_ELEMENT_COLLECTION_24__FIELD);
		createEAttribute(xmlElementCollection_2_4EClass, XML_ELEMENT_COLLECTION_24__DELETE_ALL);

		xmlEmbeddable_2_4EClass = createEClass(XML_EMBEDDABLE_24);
		createEReference(xmlEmbeddable_2_4EClass, XML_EMBEDDABLE_24__NO_SQL);

		xmlEmbedded_2_4EClass = createEClass(XML_EMBEDDED_24);
		createEReference(xmlEmbedded_2_4EClass, XML_EMBEDDED_24__FIELD);

		xmlEntity_2_4EClass = createEClass(XML_ENTITY_24);
		createEReference(xmlEntity_2_4EClass, XML_ENTITY_24__CACHE_INDEX);
		createEReference(xmlEntity_2_4EClass, XML_ENTITY_24__NO_SQL);

		xmlEntityMappings_2_4EClass = createEClass(XML_ENTITY_MAPPINGS_24);
		createEReference(xmlEntityMappings_2_4EClass, XML_ENTITY_MAPPINGS_24__UUID_GENERATORS);

		xmlField_2_4EClass = createEClass(XML_FIELD_24);
		createEAttribute(xmlField_2_4EClass, XML_FIELD_24__NAME);

		xmlId_2_4EClass = createEClass(XML_ID_24);
		createEReference(xmlId_2_4EClass, XML_ID_24__FIELD);
		createEReference(xmlId_2_4EClass, XML_ID_24__CACHE_INDEX);

		xmlJoinField_2_4EClass = createEClass(XML_JOIN_FIELD_24);
		createEAttribute(xmlJoinField_2_4EClass, XML_JOIN_FIELD_24__NAME);
		createEAttribute(xmlJoinField_2_4EClass, XML_JOIN_FIELD_24__REFERENCED_FIELD_NAME);

		xmlManyToMany_2_4EClass = createEClass(XML_MANY_TO_MANY_24);
		createEReference(xmlManyToMany_2_4EClass, XML_MANY_TO_MANY_24__JOIN_FIELDS);

		xmlManyToOne_2_4EClass = createEClass(XML_MANY_TO_ONE_24);
		createEReference(xmlManyToOne_2_4EClass, XML_MANY_TO_ONE_24__JOIN_FIELDS);

		xmlMappedSuperclass_2_4EClass = createEClass(XML_MAPPED_SUPERCLASS_24);
		createEReference(xmlMappedSuperclass_2_4EClass, XML_MAPPED_SUPERCLASS_24__CACHE_INDEX);

		xmlMultitenant_2_4EClass = createEClass(XML_MULTITENANT_24);
		createEAttribute(xmlMultitenant_2_4EClass, XML_MULTITENANT_24__INCLUDE_CRITERIA);
		createEReference(xmlMultitenant_2_4EClass, XML_MULTITENANT_24__TENANT_TABLE_DISCRIMINATOR);

		xmlNoSql_2_4EClass = createEClass(XML_NO_SQL_24);
		createEAttribute(xmlNoSql_2_4EClass, XML_NO_SQL_24__DATA_TYPE);
		createEAttribute(xmlNoSql_2_4EClass, XML_NO_SQL_24__DATA_FORMAT);

		xmlOneToMany_2_4EClass = createEClass(XML_ONE_TO_MANY_24);
		createEReference(xmlOneToMany_2_4EClass, XML_ONE_TO_MANY_24__JOIN_FIELDS);
		createEAttribute(xmlOneToMany_2_4EClass, XML_ONE_TO_MANY_24__DELETE_ALL);

		xmlOneToOne_2_4EClass = createEClass(XML_ONE_TO_ONE_24);
		createEReference(xmlOneToOne_2_4EClass, XML_ONE_TO_ONE_24__JOIN_FIELDS);

		xmlTenantTableDiscriminator_2_4EClass = createEClass(XML_TENANT_TABLE_DISCRIMINATOR_24);
		createEAttribute(xmlTenantTableDiscriminator_2_4EClass, XML_TENANT_TABLE_DISCRIMINATOR_24__CONTEXT_PROPERTY);
		createEAttribute(xmlTenantTableDiscriminator_2_4EClass, XML_TENANT_TABLE_DISCRIMINATOR_24__TYPE);

		xmlUuidGenerator_2_4EClass = createEClass(XML_UUID_GENERATOR_24);

		// Create enums
		databaseChangeNotificationTypeEEnum = createEEnum(DATABASE_CHANGE_NOTIFICATION_TYPE);
		tenantTableDiscriminatorTypeEEnum = createEEnum(TENANT_TABLE_DISCRIMINATOR_TYPE);
		dataFormatTypeEEnum = createEEnum(DATA_FORMAT_TYPE);
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
		xmlBasic_2_4EClass.getESuperTypes().add(this.getXmlGeneratorContainer2_4());
		xmlGeneratorContainer2_4EClass.getESuperTypes().add(theOrmPackage.getXmlGeneratorContainer());
		xmlEntity_2_4EClass.getESuperTypes().add(this.getXmlGeneratorContainer2_4());
		xmlId_2_4EClass.getESuperTypes().add(this.getXmlGeneratorContainer2_4());
		xmlMappedSuperclass_2_4EClass.getESuperTypes().add(this.getXmlGeneratorContainer2_4());
		xmlUuidGenerator_2_4EClass.getESuperTypes().add(theOrmPackage.getXmlGenerator());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlBasic_2_4EClass, XmlBasic_2_4.class, "XmlBasic_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlBasic_2_4_Field(), theEclipseLinkOrmPackage.getXmlField(), null, "field", null, 0, 1, XmlBasic_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlBasic_2_4_CacheIndex(), theEclipseLinkOrmPackage.getXmlCacheIndex(), null, "cacheIndex", null, 0, 1, XmlBasic_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCache_2_4EClass, XmlCache_2_4.class, "XmlCache_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlCache_2_4_DatabaseChangeNotificationType(), this.getDatabaseChangeNotificationType(), "databaseChangeNotificationType", "", 0, 1, XmlCache_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCacheIndex_2_4EClass, XmlCacheIndex_2_4.class, "XmlCacheIndex_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlCacheIndex_2_4_ColumnNames(), theXMLTypePackage.getString(), "columnNames", null, 0, -1, XmlCacheIndex_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlGeneratorContainer2_4EClass, XmlGeneratorContainer2_4.class, "XmlGeneratorContainer2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlGeneratorContainer2_4_UuidGenerator(), theEclipseLinkOrmPackage.getXmlUuidGenerator(), null, "uuidGenerator", null, 0, 1, XmlGeneratorContainer2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlElementCollection_2_4EClass, XmlElementCollection_2_4.class, "XmlElementCollection_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlElementCollection_2_4_Field(), theEclipseLinkOrmPackage.getXmlField(), null, "field", null, 0, 1, XmlElementCollection_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlElementCollection_2_4_DeleteAll(), theXMLTypePackage.getBoolean(), "deleteAll", null, 0, 1, XmlElementCollection_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEmbeddable_2_4EClass, XmlEmbeddable_2_4.class, "XmlEmbeddable_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEmbeddable_2_4_NoSql(), theEclipseLinkOrmPackage.getXmlNoSql(), null, "noSql", null, 0, 1, XmlEmbeddable_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEmbedded_2_4EClass, XmlEmbedded_2_4.class, "XmlEmbedded_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEmbedded_2_4_Field(), theEclipseLinkOrmPackage.getXmlField(), null, "field", null, 0, 1, XmlEmbedded_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntity_2_4EClass, XmlEntity_2_4.class, "XmlEntity_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntity_2_4_CacheIndex(), theEclipseLinkOrmPackage.getXmlCacheIndex(), null, "cacheIndex", null, 0, 1, XmlEntity_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_2_4_NoSql(), theEclipseLinkOrmPackage.getXmlNoSql(), null, "noSql", null, 0, 1, XmlEntity_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntityMappings_2_4EClass, XmlEntityMappings_2_4.class, "XmlEntityMappings_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntityMappings_2_4_UuidGenerators(), this.getXmlUuidGenerator_2_4(), null, "uuidGenerators", null, 0, -1, XmlEntityMappings_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlField_2_4EClass, XmlField_2_4.class, "XmlField_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlField_2_4_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlField_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlId_2_4EClass, XmlId_2_4.class, "XmlId_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlId_2_4_Field(), theEclipseLinkOrmPackage.getXmlField(), null, "field", null, 0, 1, XmlId_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlId_2_4_CacheIndex(), theEclipseLinkOrmPackage.getXmlCacheIndex(), null, "cacheIndex", null, 0, 1, XmlId_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlJoinField_2_4EClass, XmlJoinField_2_4.class, "XmlJoinField_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlJoinField_2_4_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlJoinField_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlJoinField_2_4_ReferencedFieldName(), theXMLTypePackage.getString(), "referencedFieldName", null, 0, 1, XmlJoinField_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlManyToMany_2_4EClass, XmlManyToMany_2_4.class, "XmlManyToMany_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlManyToMany_2_4_JoinFields(), theEclipseLinkOrmPackage.getXmlJoinField(), null, "joinFields", null, 0, -1, XmlManyToMany_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlManyToOne_2_4EClass, XmlManyToOne_2_4.class, "XmlManyToOne_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlManyToOne_2_4_JoinFields(), theEclipseLinkOrmPackage.getXmlJoinField(), null, "joinFields", null, 0, -1, XmlManyToOne_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMappedSuperclass_2_4EClass, XmlMappedSuperclass_2_4.class, "XmlMappedSuperclass_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlMappedSuperclass_2_4_CacheIndex(), theEclipseLinkOrmPackage.getXmlCacheIndex(), null, "cacheIndex", null, 0, 1, XmlMappedSuperclass_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMultitenant_2_4EClass, XmlMultitenant_2_4.class, "XmlMultitenant_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlMultitenant_2_4_IncludeCriteria(), theXMLTypePackage.getBooleanObject(), "includeCriteria", null, 0, 1, XmlMultitenant_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlMultitenant_2_4_TenantTableDiscriminator(), theEclipseLinkOrmPackage.getXmlTenantTableDiscriminator(), null, "tenantTableDiscriminator", null, 0, 1, XmlMultitenant_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNoSql_2_4EClass, XmlNoSql_2_4.class, "XmlNoSql_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlNoSql_2_4_DataType(), theXMLTypePackage.getString(), "dataType", null, 0, 1, XmlNoSql_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNoSql_2_4_DataFormat(), this.getDataFormatType(), "dataFormat", "", 0, 1, XmlNoSql_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToMany_2_4EClass, XmlOneToMany_2_4.class, "XmlOneToMany_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlOneToMany_2_4_JoinFields(), theEclipseLinkOrmPackage.getXmlJoinField(), null, "joinFields", null, 0, -1, XmlOneToMany_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOneToMany_2_4_DeleteAll(), theXMLTypePackage.getBoolean(), "deleteAll", null, 0, 1, XmlOneToMany_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToOne_2_4EClass, XmlOneToOne_2_4.class, "XmlOneToOne_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlOneToOne_2_4_JoinFields(), theEclipseLinkOrmPackage.getXmlJoinField(), null, "joinFields", null, 0, -1, XmlOneToOne_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTenantTableDiscriminator_2_4EClass, XmlTenantTableDiscriminator_2_4.class, "XmlTenantTableDiscriminator_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlTenantTableDiscriminator_2_4_ContextProperty(), theXMLTypePackage.getString(), "contextProperty", null, 0, 1, XmlTenantTableDiscriminator_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTenantTableDiscriminator_2_4_Type(), this.getTenantTableDiscriminatorType(), "type", null, 0, 1, XmlTenantTableDiscriminator_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlUuidGenerator_2_4EClass, XmlUuidGenerator_2_4.class, "XmlUuidGenerator_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(databaseChangeNotificationTypeEEnum, DatabaseChangeNotificationType.class, "DatabaseChangeNotificationType");
		addEEnumLiteral(databaseChangeNotificationTypeEEnum, DatabaseChangeNotificationType.NONE);
		addEEnumLiteral(databaseChangeNotificationTypeEEnum, DatabaseChangeNotificationType.INVALIDATION);

		initEEnum(tenantTableDiscriminatorTypeEEnum, TenantTableDiscriminatorType.class, "TenantTableDiscriminatorType");
		addEEnumLiteral(tenantTableDiscriminatorTypeEEnum, TenantTableDiscriminatorType.SCHEMA);
		addEEnumLiteral(tenantTableDiscriminatorTypeEEnum, TenantTableDiscriminatorType.SUFFIX);
		addEEnumLiteral(tenantTableDiscriminatorTypeEEnum, TenantTableDiscriminatorType.PREFIX);

		initEEnum(dataFormatTypeEEnum, DataFormatType.class, "DataFormatType");
		addEEnumLiteral(dataFormatTypeEEnum, DataFormatType.XML);
		addEEnumLiteral(dataFormatTypeEEnum, DataFormatType.INDEXED);
		addEEnumLiteral(dataFormatTypeEEnum, DataFormatType.MAPPED);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4 <em>Xml Multitenant 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlMultitenant_2_4()
		 * @generated
		 */
		public static final EClass XML_MULTITENANT_24 = eINSTANCE.getXmlMultitenant_2_4();

		/**
		 * The meta object literal for the '<em><b>Include Criteria</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MULTITENANT_24__INCLUDE_CRITERIA = eINSTANCE.getXmlMultitenant_2_4_IncludeCriteria();

		/**
		 * The meta object literal for the '<em><b>Tenant Table Discriminator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MULTITENANT_24__TENANT_TABLE_DISCRIMINATOR = eINSTANCE.getXmlMultitenant_2_4_TenantTableDiscriminator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlNoSql_2_4 <em>Xml No Sql 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlNoSql_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlNoSql_2_4()
		 * @generated
		 */
		public static final EClass XML_NO_SQL_24 = eINSTANCE.getXmlNoSql_2_4();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NO_SQL_24__DATA_TYPE = eINSTANCE.getXmlNoSql_2_4_DataType();

		/**
		 * The meta object literal for the '<em><b>Data Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NO_SQL_24__DATA_FORMAT = eINSTANCE.getXmlNoSql_2_4_DataFormat();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4 <em>Xml Element Collection 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlElementCollection_2_4()
		 * @generated
		 */
		public static final EClass XML_ELEMENT_COLLECTION_24 = eINSTANCE.getXmlElementCollection_2_4();

		/**
		 * The meta object literal for the '<em><b>Field</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION_24__FIELD = eINSTANCE.getXmlElementCollection_2_4_Field();

		/**
		 * The meta object literal for the '<em><b>Delete All</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION_24__DELETE_ALL = eINSTANCE.getXmlElementCollection_2_4_DeleteAll();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbeddable_2_4 <em>Xml Embeddable 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbeddable_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlEmbeddable_2_4()
		 * @generated
		 */
		public static final EClass XML_EMBEDDABLE_24 = eINSTANCE.getXmlEmbeddable_2_4();

		/**
		 * The meta object literal for the '<em><b>No Sql</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EMBEDDABLE_24__NO_SQL = eINSTANCE.getXmlEmbeddable_2_4_NoSql();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbedded_2_4 <em>Xml Embedded 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEmbedded_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlEmbedded_2_4()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED_24 = eINSTANCE.getXmlEmbedded_2_4();

		/**
		 * The meta object literal for the '<em><b>Field</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EMBEDDED_24__FIELD = eINSTANCE.getXmlEmbedded_2_4_Field();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4 <em>Xml One To Many 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlOneToMany_2_4()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_MANY_24 = eINSTANCE.getXmlOneToMany_2_4();

		/**
		 * The meta object literal for the '<em><b>Join Fields</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_MANY_24__JOIN_FIELDS = eINSTANCE.getXmlOneToMany_2_4_JoinFields();

		/**
		 * The meta object literal for the '<em><b>Delete All</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_MANY_24__DELETE_ALL = eINSTANCE.getXmlOneToMany_2_4_DeleteAll();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToOne_2_4 <em>Xml One To One 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToOne_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlOneToOne_2_4()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_ONE_24 = eINSTANCE.getXmlOneToOne_2_4();

		/**
		 * The meta object literal for the '<em><b>Join Fields</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_ONE_24__JOIN_FIELDS = eINSTANCE.getXmlOneToOne_2_4_JoinFields();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlTenantTableDiscriminator_2_4 <em>Xml Tenant Table Discriminator 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlTenantTableDiscriminator_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlTenantTableDiscriminator_2_4()
		 * @generated
		 */
		public static final EClass XML_TENANT_TABLE_DISCRIMINATOR_24 = eINSTANCE.getXmlTenantTableDiscriminator_2_4();

		/**
		 * The meta object literal for the '<em><b>Context Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TENANT_TABLE_DISCRIMINATOR_24__CONTEXT_PROPERTY = eINSTANCE.getXmlTenantTableDiscriminator_2_4_ContextProperty();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TENANT_TABLE_DISCRIMINATOR_24__TYPE = eINSTANCE.getXmlTenantTableDiscriminator_2_4_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlUuidGenerator_2_4 <em>Xml Uuid Generator 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlUuidGenerator_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlUuidGenerator_2_4()
		 * @generated
		 */
		public static final EClass XML_UUID_GENERATOR_24 = eINSTANCE.getXmlUuidGenerator_2_4();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4 <em>Xml Entity 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlEntity_2_4()
		 * @generated
		 */
		public static final EClass XML_ENTITY_24 = eINSTANCE.getXmlEntity_2_4();

		/**
		 * The meta object literal for the '<em><b>Cache Index</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_24__CACHE_INDEX = eINSTANCE.getXmlEntity_2_4_CacheIndex();

		/**
		 * The meta object literal for the '<em><b>No Sql</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_24__NO_SQL = eINSTANCE.getXmlEntity_2_4_NoSql();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntityMappings_2_4 <em>Xml Entity Mappings 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntityMappings_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlEntityMappings_2_4()
		 * @generated
		 */
		public static final EClass XML_ENTITY_MAPPINGS_24 = eINSTANCE.getXmlEntityMappings_2_4();

		/**
		 * The meta object literal for the '<em><b>Uuid Generators</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_24__UUID_GENERATORS = eINSTANCE.getXmlEntityMappings_2_4_UuidGenerators();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlField_2_4 <em>Xml Field 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlField_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlField_2_4()
		 * @generated
		 */
		public static final EClass XML_FIELD_24 = eINSTANCE.getXmlField_2_4();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_FIELD_24__NAME = eINSTANCE.getXmlField_2_4_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlBasic_2_4 <em>Xml Basic 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlBasic_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlBasic_2_4()
		 * @generated
		 */
		public static final EClass XML_BASIC_24 = eINSTANCE.getXmlBasic_2_4();

		/**
		 * The meta object literal for the '<em><b>Field</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_BASIC_24__FIELD = eINSTANCE.getXmlBasic_2_4_Field();

		/**
		 * The meta object literal for the '<em><b>Cache Index</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_BASIC_24__CACHE_INDEX = eINSTANCE.getXmlBasic_2_4_CacheIndex();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCache_2_4 <em>Xml Cache 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCache_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlCache_2_4()
		 * @generated
		 */
		public static final EClass XML_CACHE_24 = eINSTANCE.getXmlCache_2_4();

		/**
		 * The meta object literal for the '<em><b>Database Change Notification Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CACHE_24__DATABASE_CHANGE_NOTIFICATION_TYPE = eINSTANCE.getXmlCache_2_4_DatabaseChangeNotificationType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCacheIndex_2_4 <em>Xml Cache Index 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlCacheIndex_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlCacheIndex_2_4()
		 * @generated
		 */
		public static final EClass XML_CACHE_INDEX_24 = eINSTANCE.getXmlCacheIndex_2_4();

		/**
		 * The meta object literal for the '<em><b>Column Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CACHE_INDEX_24__COLUMN_NAMES = eINSTANCE.getXmlCacheIndex_2_4_ColumnNames();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4 <em>Xml Generator Container2 4</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlGeneratorContainer2_4()
		 * @generated
		 */
		public static final EClass XML_GENERATOR_CONTAINER2_4 = eINSTANCE.getXmlGeneratorContainer2_4();

		/**
		 * The meta object literal for the '<em><b>Uuid Generator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_GENERATOR_CONTAINER2_4__UUID_GENERATOR = eINSTANCE.getXmlGeneratorContainer2_4_UuidGenerator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4 <em>Xml Id 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlId_2_4()
		 * @generated
		 */
		public static final EClass XML_ID_24 = eINSTANCE.getXmlId_2_4();

		/**
		 * The meta object literal for the '<em><b>Field</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ID_24__FIELD = eINSTANCE.getXmlId_2_4_Field();

		/**
		 * The meta object literal for the '<em><b>Cache Index</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ID_24__CACHE_INDEX = eINSTANCE.getXmlId_2_4_CacheIndex();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlJoinField_2_4 <em>Xml Join Field 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlJoinField_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlJoinField_2_4()
		 * @generated
		 */
		public static final EClass XML_JOIN_FIELD_24 = eINSTANCE.getXmlJoinField_2_4();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_JOIN_FIELD_24__NAME = eINSTANCE.getXmlJoinField_2_4_Name();

		/**
		 * The meta object literal for the '<em><b>Referenced Field Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_JOIN_FIELD_24__REFERENCED_FIELD_NAME = eINSTANCE.getXmlJoinField_2_4_ReferencedFieldName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToMany_2_4 <em>Xml Many To Many 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToMany_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlManyToMany_2_4()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_MANY_24 = eINSTANCE.getXmlManyToMany_2_4();

		/**
		 * The meta object literal for the '<em><b>Join Fields</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_MANY_24__JOIN_FIELDS = eINSTANCE.getXmlManyToMany_2_4_JoinFields();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToOne_2_4 <em>Xml Many To One 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlManyToOne_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlManyToOne_2_4()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_ONE_24 = eINSTANCE.getXmlManyToOne_2_4();

		/**
		 * The meta object literal for the '<em><b>Join Fields</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_ONE_24__JOIN_FIELDS = eINSTANCE.getXmlManyToOne_2_4_JoinFields();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMappedSuperclass_2_4 <em>Xml Mapped Superclass 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMappedSuperclass_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlMappedSuperclass_2_4()
		 * @generated
		 */
		public static final EClass XML_MAPPED_SUPERCLASS_24 = eINSTANCE.getXmlMappedSuperclass_2_4();

		/**
		 * The meta object literal for the '<em><b>Cache Index</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS_24__CACHE_INDEX = eINSTANCE.getXmlMappedSuperclass_2_4_CacheIndex();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DatabaseChangeNotificationType <em>Database Change Notification Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DatabaseChangeNotificationType
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getDatabaseChangeNotificationType()
		 * @generated
		 */
		public static final EEnum DATABASE_CHANGE_NOTIFICATION_TYPE = eINSTANCE.getDatabaseChangeNotificationType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.TenantTableDiscriminatorType <em>Tenant Table Discriminator Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.TenantTableDiscriminatorType
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getTenantTableDiscriminatorType()
		 * @generated
		 */
		public static final EEnum TENANT_TABLE_DISCRIMINATOR_TYPE = eINSTANCE.getTenantTableDiscriminatorType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DataFormatType <em>Data Format Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DataFormatType
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getDataFormatType()
		 * @generated
		 */
		public static final EEnum DATA_FORMAT_TYPE = eINSTANCE.getDataFormatType();

	}

} //EclipseLinkOrmV2_4Package
