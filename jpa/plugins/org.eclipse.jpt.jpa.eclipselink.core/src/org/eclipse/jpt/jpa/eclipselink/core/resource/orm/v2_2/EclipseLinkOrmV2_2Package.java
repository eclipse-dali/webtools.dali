/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Factory
 * @model kind="package"
 * @generated
 */
public class EclipseLinkOrmV2_2Package extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "v2_2";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.eclipselink.orm.v2_2.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLinkOrmV2_2Package eINSTANCE = org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2 <em>Xml Additional Criteria 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlAdditionalCriteria_2_2()
	 * @generated
	 */
	public static final int XML_ADDITIONAL_CRITERIA_22 = 0;

	/**
	 * The feature id for the '<em><b>Criteria</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ADDITIONAL_CRITERIA_22__CRITERIA = 0;

	/**
	 * The number of structural features of the '<em>Xml Additional Criteria 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ADDITIONAL_CRITERIA_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2 <em>Xml Basic 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasic_2_2()
	 * @generated
	 */
	public static final int XML_BASIC_22 = 1;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_22__INDEX = 0;

	/**
	 * The number of structural features of the '<em>Xml Basic 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2 <em>Xml Basic Collection 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasicCollection_2_2()
	 * @generated
	 */
	public static final int XML_BASIC_COLLECTION_22 = 2;

	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION_22__CASCADE_ON_DELETE = 0;

	/**
	 * The number of structural features of the '<em>Xml Basic Collection 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2 <em>Xml Basic Map 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasicMap_2_2()
	 * @generated
	 */
	public static final int XML_BASIC_MAP_22 = 3;

	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP_22__CASCADE_ON_DELETE = 0;

	/**
	 * The number of structural features of the '<em>Xml Basic Map 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlCollectionTable_2_2 <em>Xml Collection Table 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlCollectionTable_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlCollectionTable_2_2()
	 * @generated
	 */
	public static final int XML_COLLECTION_TABLE_22 = 4;

	/**
	 * The feature id for the '<em><b>Creation Suffix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE_22__CREATION_SUFFIX = 0;

	/**
	 * The number of structural features of the '<em>Xml Collection Table 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2 <em>Xml Partitioning Group 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioningGroup_2_2()
	 * @generated
	 */
	public static final int XML_PARTITIONING_GROUP_22 = 19;

	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING_GROUP_22__PARTITIONING = 0;

	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING = 1;

	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING = 2;

	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING = 3;

	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING = 4;

	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING = 5;

	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING_GROUP_22__HASH_PARTITIONING = 6;

	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING_GROUP_22__UNION_PARTITIONING = 7;

	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING_GROUP_22__PARTITIONED = 8;

	/**
	 * The number of structural features of the '<em>Xml Partitioning Group 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING_GROUP_22_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2 <em>Xml Element Collection 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlElementCollection_2_2()
	 * @generated
	 */
	public static final int XML_ELEMENT_COLLECTION_22 = 5;

	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22__PARTITIONING = XML_PARTITIONING_GROUP_22__PARTITIONING;

	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22__REPLICATION_PARTITIONING = XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22__ROUND_ROBIN_PARTITIONING = XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22__PINNED_PARTITIONING = XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22__RANGE_PARTITIONING = XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22__VALUE_PARTITIONING = XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22__HASH_PARTITIONING = XML_PARTITIONING_GROUP_22__HASH_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22__UNION_PARTITIONING = XML_PARTITIONING_GROUP_22__UNION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22__PARTITIONED = XML_PARTITIONING_GROUP_22__PARTITIONED;

	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22__CASCADE_ON_DELETE = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22__NON_CACHEABLE = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Element Collection 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22_FEATURE_COUNT = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2 <em>Xml Entity 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntity_2_2()
	 * @generated
	 */
	public static final int XML_ENTITY_22 = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2 <em>Xml Entity Mappings 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntityMappings_2_2()
	 * @generated
	 */
	public static final int XML_ENTITY_MAPPINGS_22 = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlHashPartitioning_2_2 <em>Xml Hash Partitioning 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlHashPartitioning_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlHashPartitioning_2_2()
	 * @generated
	 */
	public static final int XML_HASH_PARTITIONING_22 = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2 <em>Xml Many To Many 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlManyToMany_2_2()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY_22 = 13;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToOne_2_2 <em>Xml Many To One 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToOne_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlManyToOne_2_2()
	 * @generated
	 */
	public static final int XML_MANY_TO_ONE_22 = 14;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2 <em>Xml One To One 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlOneToOne_2_2()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE_22 = 16;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2 <em>Xml One To Many 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlOneToMany_2_2()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY_22 = 17;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioning_2_2 <em>Xml Partitioning 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioning_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioning_2_2()
	 * @generated
	 */
	public static final int XML_PARTITIONING_22 = 18;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPinnedPartitioning_2_2 <em>Xml Pinned Partitioning 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPinnedPartitioning_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPinnedPartitioning_2_2()
	 * @generated
	 */
	public static final int XML_PINNED_PARTITIONING_22 = 20;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRangePartitioning_2_2 <em>Xml Range Partitioning 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRangePartitioning_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlRangePartitioning_2_2()
	 * @generated
	 */
	public static final int XML_RANGE_PARTITIONING_22 = 21;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlReplicationPartitioning_2_2 <em>Xml Replication Partitioning 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlReplicationPartitioning_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlReplicationPartitioning_2_2()
	 * @generated
	 */
	public static final int XML_REPLICATION_PARTITIONING_22 = 22;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRoundRobinPartitioning_2_2 <em>Xml Round Robin Partitioning 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRoundRobinPartitioning_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlRoundRobinPartitioning_2_2()
	 * @generated
	 */
	public static final int XML_ROUND_ROBIN_PARTITIONING_22 = 23;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2 <em>Xml Embeddable 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEmbeddable_2_2()
	 * @generated
	 */
	public static final int XML_EMBEDDABLE_22 = 6;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_22__ATTRIBUTE_OVERRIDES = OrmPackage.XML_ATTRIBUTE_OVERRIDE_CONTAINER__ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_22__ASSOCIATION_OVERRIDES = OrmPackage.XML_ATTRIBUTE_OVERRIDE_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parent Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_22__PARENT_CLASS = OrmPackage.XML_ATTRIBUTE_OVERRIDE_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Embeddable 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_22_FEATURE_COUNT = OrmPackage.XML_ATTRIBUTE_OVERRIDE_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__PARTITIONING = XML_PARTITIONING_GROUP_22__PARTITIONING;

	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__REPLICATION_PARTITIONING = XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__ROUND_ROBIN_PARTITIONING = XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__PINNED_PARTITIONING = XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__RANGE_PARTITIONING = XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__VALUE_PARTITIONING = XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__HASH_PARTITIONING = XML_PARTITIONING_GROUP_22__HASH_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__UNION_PARTITIONING = XML_PARTITIONING_GROUP_22__UNION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__PARTITIONED = XML_PARTITIONING_GROUP_22__PARTITIONED;

	/**
	 * The feature id for the '<em><b>Additional Criteria</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__ADDITIONAL_CRITERIA = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__CASCADE_ON_DELETE = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__INDEX = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Entity 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22_FEATURE_COUNT = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_22__PARTITIONING = 0;

	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_22__REPLICATION_PARTITIONING = 1;

	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_22__ROUND_ROBIN_PARTITIONING = 2;

	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_22__PINNED_PARTITIONING = 3;

	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_22__RANGE_PARTITIONING = 4;

	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_22__VALUE_PARTITIONING = 5;

	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_22__HASH_PARTITIONING = 6;

	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_22__UNION_PARTITIONING = 7;

	/**
	 * The number of structural features of the '<em>Xml Entity Mappings 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_22_FEATURE_COUNT = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_HASH_PARTITIONING_22__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Hash Partitioning 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_HASH_PARTITIONING_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlId_2_2 <em>Xml Id 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlId_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlId_2_2()
	 * @generated
	 */
	public static final int XML_ID_22 = 10;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_22__INDEX = 0;

	/**
	 * The number of structural features of the '<em>Xml Id 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2 <em>Xml Index 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlIndex_2_2()
	 * @generated
	 */
	public static final int XML_INDEX_22 = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX_22__NAME = 0;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX_22__SCHEMA = 1;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX_22__CATALOG = 2;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX_22__TABLE = 3;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX_22__UNIQUE = 4;

	/**
	 * The feature id for the '<em><b>Column Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX_22__COLUMN_NAMES = 5;

	/**
	 * The number of structural features of the '<em>Xml Index 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX_22_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlJoinTable_2_2 <em>Xml Join Table 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlJoinTable_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlJoinTable_2_2()
	 * @generated
	 */
	public static final int XML_JOIN_TABLE_22 = 12;

	/**
	 * The feature id for the '<em><b>Creation Suffix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE_22__CREATION_SUFFIX = 0;

	/**
	 * The number of structural features of the '<em>Xml Join Table 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE_22_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22__PARTITIONING = XML_PARTITIONING_GROUP_22__PARTITIONING;

	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22__REPLICATION_PARTITIONING = XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22__ROUND_ROBIN_PARTITIONING = XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22__PINNED_PARTITIONING = XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22__RANGE_PARTITIONING = XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22__VALUE_PARTITIONING = XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22__HASH_PARTITIONING = XML_PARTITIONING_GROUP_22__HASH_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22__UNION_PARTITIONING = XML_PARTITIONING_GROUP_22__UNION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22__PARTITIONED = XML_PARTITIONING_GROUP_22__PARTITIONED;

	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22__CASCADE_ON_DELETE = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22__NON_CACHEABLE = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Many To Many 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22_FEATURE_COUNT = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_22__PARTITIONING = XML_PARTITIONING_GROUP_22__PARTITIONING;

	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_22__REPLICATION_PARTITIONING = XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_22__ROUND_ROBIN_PARTITIONING = XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_22__PINNED_PARTITIONING = XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_22__RANGE_PARTITIONING = XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_22__VALUE_PARTITIONING = XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_22__HASH_PARTITIONING = XML_PARTITIONING_GROUP_22__HASH_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_22__UNION_PARTITIONING = XML_PARTITIONING_GROUP_22__UNION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_22__PARTITIONED = XML_PARTITIONING_GROUP_22__PARTITIONED;

	/**
	 * The feature id for the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_22__NON_CACHEABLE = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Many To One 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_22_FEATURE_COUNT = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2 <em>Xml Mapped Superclass 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlMappedSuperclass_2_2()
	 * @generated
	 */
	public static final int XML_MAPPED_SUPERCLASS_22 = 15;

	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_22__PARTITIONING = XML_PARTITIONING_GROUP_22__PARTITIONING;

	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_22__REPLICATION_PARTITIONING = XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_22__ROUND_ROBIN_PARTITIONING = XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_22__PINNED_PARTITIONING = XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_22__RANGE_PARTITIONING = XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_22__VALUE_PARTITIONING = XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_22__HASH_PARTITIONING = XML_PARTITIONING_GROUP_22__HASH_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_22__UNION_PARTITIONING = XML_PARTITIONING_GROUP_22__UNION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_22__PARTITIONED = XML_PARTITIONING_GROUP_22__PARTITIONED;

	/**
	 * The feature id for the '<em><b>Additional Criteria</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_22__ADDITIONAL_CRITERIA = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Mapped Superclass 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_22_FEATURE_COUNT = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22__PARTITIONING = XML_PARTITIONING_GROUP_22__PARTITIONING;

	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22__REPLICATION_PARTITIONING = XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22__ROUND_ROBIN_PARTITIONING = XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22__PINNED_PARTITIONING = XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22__RANGE_PARTITIONING = XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22__VALUE_PARTITIONING = XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22__HASH_PARTITIONING = XML_PARTITIONING_GROUP_22__HASH_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22__UNION_PARTITIONING = XML_PARTITIONING_GROUP_22__UNION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22__PARTITIONED = XML_PARTITIONING_GROUP_22__PARTITIONED;

	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22__CASCADE_ON_DELETE = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22__NON_CACHEABLE = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml One To One 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22_FEATURE_COUNT = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22__PARTITIONING = XML_PARTITIONING_GROUP_22__PARTITIONING;

	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22__REPLICATION_PARTITIONING = XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22__ROUND_ROBIN_PARTITIONING = XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22__PINNED_PARTITIONING = XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22__RANGE_PARTITIONING = XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22__VALUE_PARTITIONING = XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22__HASH_PARTITIONING = XML_PARTITIONING_GROUP_22__HASH_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22__UNION_PARTITIONING = XML_PARTITIONING_GROUP_22__UNION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22__PARTITIONED = XML_PARTITIONING_GROUP_22__PARTITIONED;

	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22__CASCADE_ON_DELETE = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22__NON_CACHEABLE = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml One To Many 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22_FEATURE_COUNT = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING_22__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Partitioning 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING_22_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PINNED_PARTITIONING_22__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Pinned Partitioning 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PINNED_PARTITIONING_22_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RANGE_PARTITIONING_22__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Range Partitioning 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RANGE_PARTITIONING_22_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_REPLICATION_PARTITIONING_22__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Replication Partitioning 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_REPLICATION_PARTITIONING_22_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ROUND_ROBIN_PARTITIONING_22__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Round Robin Partitioning 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ROUND_ROBIN_PARTITIONING_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlSecondaryTable_2_2 <em>Xml Secondary Table 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlSecondaryTable_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlSecondaryTable_2_2()
	 * @generated
	 */
	public static final int XML_SECONDARY_TABLE_22 = 24;

	/**
	 * The feature id for the '<em><b>Creation Suffix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE_22__CREATION_SUFFIX = 0;

	/**
	 * The number of structural features of the '<em>Xml Secondary Table 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTable_2_2 <em>Xml Table 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTable_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlTable_2_2()
	 * @generated
	 */
	public static final int XML_TABLE_22 = 25;

	/**
	 * The feature id for the '<em><b>Creation Suffix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_22__CREATION_SUFFIX = 0;

	/**
	 * The number of structural features of the '<em>Xml Table 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTableGenerator_2_2 <em>Xml Table Generator 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTableGenerator_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlTableGenerator_2_2()
	 * @generated
	 */
	public static final int XML_TABLE_GENERATOR_22 = 26;

	/**
	 * The feature id for the '<em><b>Creation Suffix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR_22__CREATION_SUFFIX = 0;

	/**
	 * The number of structural features of the '<em>Xml Table Generator 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlUnionPartitioning_2_2 <em>Xml Union Partitioning 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlUnionPartitioning_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlUnionPartitioning_2_2()
	 * @generated
	 */
	public static final int XML_UNION_PARTITIONING_22 = 27;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNION_PARTITIONING_22__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Union Partitioning 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNION_PARTITIONING_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlValuePartitioning_2_2 <em>Xml Value Partitioning 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlValuePartitioning_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlValuePartitioning_2_2()
	 * @generated
	 */
	public static final int XML_VALUE_PARTITIONING_22 = 28;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VALUE_PARTITIONING_22__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Value Partitioning 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VALUE_PARTITIONING_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVariableOneToOne_2_2 <em>Xml Variable One To One 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVariableOneToOne_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlVariableOneToOne_2_2()
	 * @generated
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_22 = 29;

	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_22__PARTITIONING = XML_PARTITIONING_GROUP_22__PARTITIONING;

	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_22__REPLICATION_PARTITIONING = XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_22__ROUND_ROBIN_PARTITIONING = XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_22__PINNED_PARTITIONING = XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_22__RANGE_PARTITIONING = XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_22__VALUE_PARTITIONING = XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_22__HASH_PARTITIONING = XML_PARTITIONING_GROUP_22__HASH_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_22__UNION_PARTITIONING = XML_PARTITIONING_GROUP_22__UNION_PARTITIONING;

	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_22__PARTITIONED = XML_PARTITIONING_GROUP_22__PARTITIONED;

	/**
	 * The feature id for the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_22__NON_CACHEABLE = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Variable One To One 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_22_FEATURE_COUNT = XML_PARTITIONING_GROUP_22_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2 <em>Xml Version 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlVersion_2_2()
	 * @generated
	 */
	public static final int XML_VERSION_22 = 30;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_22__INDEX = 0;

	/**
	 * The number of structural features of the '<em>Xml Version 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_22_FEATURE_COUNT = 1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAdditionalCriteria_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBasic_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBasicCollection_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBasicMap_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCollectionTable_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlElementCollection_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntity_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntityMappings_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlHashPartitioning_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlManyToMany_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlManyToOne_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOneToOne_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOneToMany_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPartitioning_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPartitioningGroup_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPinnedPartitioning_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlRangePartitioning_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlReplicationPartitioning_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlRoundRobinPartitioning_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEmbeddable_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlId_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlIndex_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlJoinTable_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMappedSuperclass_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlSecondaryTable_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTable_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTableGenerator_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlUnionPartitioning_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlValuePartitioning_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlVariableOneToOne_2_2EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlVersion_2_2EClass = null;

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EclipseLinkOrmV2_2Package()
	{
		super(eNS_URI, EclipseLinkOrmV2_2Factory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EclipseLinkOrmV2_2Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EclipseLinkOrmV2_2Package init()
	{
		if (isInited) return (EclipseLinkOrmV2_2Package)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI);

		// Obtain or create and register package
		EclipseLinkOrmV2_2Package theEclipseLinkOrmV2_2Package = (EclipseLinkOrmV2_2Package)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EclipseLinkOrmV2_2Package ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EclipseLinkOrmV2_2Package());

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

		// Create package meta-data objects
		theEclipseLinkOrmV2_2Package.createPackageContents();
		theEclipseLinkOrmPackage.createPackageContents();
		theEclipseLinkOrmV1_1Package.createPackageContents();
		theEclipseLinkOrmV2_0Package.createPackageContents();
		theEclipseLinkOrmV2_1Package.createPackageContents();

		// Initialize created meta-data
		theEclipseLinkOrmV2_2Package.initializePackageContents();
		theEclipseLinkOrmPackage.initializePackageContents();
		theEclipseLinkOrmV1_1Package.initializePackageContents();
		theEclipseLinkOrmV2_0Package.initializePackageContents();
		theEclipseLinkOrmV2_1Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEclipseLinkOrmV2_2Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EclipseLinkOrmV2_2Package.eNS_URI, theEclipseLinkOrmV2_2Package);
		return theEclipseLinkOrmV2_2Package;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2 <em>Xml Additional Criteria 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Additional Criteria 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2
	 * @generated
	 */
	public EClass getXmlAdditionalCriteria_2_2()
	{
		return xmlAdditionalCriteria_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2#getCriteria <em>Criteria</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Criteria</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2#getCriteria()
	 * @see #getXmlAdditionalCriteria_2_2()
	 * @generated
	 */
	public EAttribute getXmlAdditionalCriteria_2_2_Criteria()
	{
		return (EAttribute)xmlAdditionalCriteria_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2 <em>Xml Basic 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2
	 * @generated
	 */
	public EClass getXmlBasic_2_2()
	{
		return xmlBasic_2_2EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2#getIndex()
	 * @see #getXmlBasic_2_2()
	 * @generated
	 */
	public EReference getXmlBasic_2_2_Index()
	{
		return (EReference)xmlBasic_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2 <em>Xml Basic Collection 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic Collection 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2
	 * @generated
	 */
	public EClass getXmlBasicCollection_2_2()
	{
		return xmlBasicCollection_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2#getCascadeOnDelete()
	 * @see #getXmlBasicCollection_2_2()
	 * @generated
	 */
	public EAttribute getXmlBasicCollection_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlBasicCollection_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2 <em>Xml Basic Map 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic Map 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2
	 * @generated
	 */
	public EClass getXmlBasicMap_2_2()
	{
		return xmlBasicMap_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2#getCascadeOnDelete()
	 * @see #getXmlBasicMap_2_2()
	 * @generated
	 */
	public EAttribute getXmlBasicMap_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlBasicMap_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlCollectionTable_2_2 <em>Xml Collection Table 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Collection Table 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlCollectionTable_2_2
	 * @generated
	 */
	public EClass getXmlCollectionTable_2_2()
	{
		return xmlCollectionTable_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlCollectionTable_2_2#getCreationSuffix <em>Creation Suffix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Suffix</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlCollectionTable_2_2#getCreationSuffix()
	 * @see #getXmlCollectionTable_2_2()
	 * @generated
	 */
	public EAttribute getXmlCollectionTable_2_2_CreationSuffix()
	{
		return (EAttribute)xmlCollectionTable_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2 <em>Xml Element Collection 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Element Collection 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2
	 * @generated
	 */
	public EClass getXmlElementCollection_2_2()
	{
		return xmlElementCollection_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2#getCascadeOnDelete()
	 * @see #getXmlElementCollection_2_2()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlElementCollection_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2#isNonCacheable <em>Non Cacheable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Non Cacheable</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2#isNonCacheable()
	 * @see #getXmlElementCollection_2_2()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_2_2_NonCacheable()
	{
		return (EAttribute)xmlElementCollection_2_2EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2 <em>Xml Entity 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2
	 * @generated
	 */
	public EClass getXmlEntity_2_2()
	{
		return xmlEntity_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getCascadeOnDelete()
	 * @see #getXmlEntity_2_2()
	 * @generated
	 */
	public EAttribute getXmlEntity_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlEntity_2_2EClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getIndex()
	 * @see #getXmlEntity_2_2()
	 * @generated
	 */
	public EReference getXmlEntity_2_2_Index()
	{
		return (EReference)xmlEntity_2_2EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2 <em>Xml Entity Mappings 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity Mappings 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2
	 * @generated
	 */
	public EClass getXmlEntityMappings_2_2()
	{
		return xmlEntityMappings_2_2EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getPartitioning <em>Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getPartitioning()
	 * @see #getXmlEntityMappings_2_2()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_2_Partitioning()
	{
		return (EReference)xmlEntityMappings_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getReplicationPartitioning <em>Replication Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Replication Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getReplicationPartitioning()
	 * @see #getXmlEntityMappings_2_2()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_2_ReplicationPartitioning()
	{
		return (EReference)xmlEntityMappings_2_2EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getRoundRobinPartitioning <em>Round Robin Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Round Robin Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getRoundRobinPartitioning()
	 * @see #getXmlEntityMappings_2_2()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_2_RoundRobinPartitioning()
	{
		return (EReference)xmlEntityMappings_2_2EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getPinnedPartitioning <em>Pinned Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pinned Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getPinnedPartitioning()
	 * @see #getXmlEntityMappings_2_2()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_2_PinnedPartitioning()
	{
		return (EReference)xmlEntityMappings_2_2EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getRangePartitioning <em>Range Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Range Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getRangePartitioning()
	 * @see #getXmlEntityMappings_2_2()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_2_RangePartitioning()
	{
		return (EReference)xmlEntityMappings_2_2EClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getValuePartitioning <em>Value Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getValuePartitioning()
	 * @see #getXmlEntityMappings_2_2()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_2_ValuePartitioning()
	{
		return (EReference)xmlEntityMappings_2_2EClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getHashPartitioning <em>Hash Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Hash Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getHashPartitioning()
	 * @see #getXmlEntityMappings_2_2()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_2_HashPartitioning()
	{
		return (EReference)xmlEntityMappings_2_2EClass.getEStructuralFeatures().get(6);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getUnionPartitioning <em>Union Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Union Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2#getUnionPartitioning()
	 * @see #getXmlEntityMappings_2_2()
	 * @generated
	 */
	public EReference getXmlEntityMappings_2_2_UnionPartitioning()
	{
		return (EReference)xmlEntityMappings_2_2EClass.getEStructuralFeatures().get(7);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlHashPartitioning_2_2 <em>Xml Hash Partitioning 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Hash Partitioning 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlHashPartitioning_2_2
	 * @generated
	 */
	public EClass getXmlHashPartitioning_2_2()
	{
		return xmlHashPartitioning_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlHashPartitioning_2_2#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlHashPartitioning_2_2#getName()
	 * @see #getXmlHashPartitioning_2_2()
	 * @generated
	 */
	public EAttribute getXmlHashPartitioning_2_2_Name()
	{
		return (EAttribute)xmlHashPartitioning_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getAdditionalCriteria <em>Additional Criteria</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Additional Criteria</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getAdditionalCriteria()
	 * @see #getXmlEntity_2_2()
	 * @generated
	 */
	public EReference getXmlEntity_2_2_AdditionalCriteria()
	{
		return (EReference)xmlEntity_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2 <em>Xml Many To Many 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2
	 * @generated
	 */
	public EClass getXmlManyToMany_2_2()
	{
		return xmlManyToMany_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2#getCascadeOnDelete()
	 * @see #getXmlManyToMany_2_2()
	 * @generated
	 */
	public EAttribute getXmlManyToMany_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlManyToMany_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2#isNonCacheable <em>Non Cacheable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Non Cacheable</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2#isNonCacheable()
	 * @see #getXmlManyToMany_2_2()
	 * @generated
	 */
	public EAttribute getXmlManyToMany_2_2_NonCacheable()
	{
		return (EAttribute)xmlManyToMany_2_2EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToOne_2_2 <em>Xml Many To One 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To One 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToOne_2_2
	 * @generated
	 */
	public EClass getXmlManyToOne_2_2()
	{
		return xmlManyToOne_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToOne_2_2#isNonCacheable <em>Non Cacheable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Non Cacheable</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToOne_2_2#isNonCacheable()
	 * @see #getXmlManyToOne_2_2()
	 * @generated
	 */
	public EAttribute getXmlManyToOne_2_2_NonCacheable()
	{
		return (EAttribute)xmlManyToOne_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2 <em>Xml One To One 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2
	 * @generated
	 */
	public EClass getXmlOneToOne_2_2()
	{
		return xmlOneToOne_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2#getCascadeOnDelete()
	 * @see #getXmlOneToOne_2_2()
	 * @generated
	 */
	public EAttribute getXmlOneToOne_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlOneToOne_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2#isNonCacheable <em>Non Cacheable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Non Cacheable</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2#isNonCacheable()
	 * @see #getXmlOneToOne_2_2()
	 * @generated
	 */
	public EAttribute getXmlOneToOne_2_2_NonCacheable()
	{
		return (EAttribute)xmlOneToOne_2_2EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2 <em>Xml One To Many 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2
	 * @generated
	 */
	public EClass getXmlOneToMany_2_2()
	{
		return xmlOneToMany_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2#getCascadeOnDelete()
	 * @see #getXmlOneToMany_2_2()
	 * @generated
	 */
	public EAttribute getXmlOneToMany_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlOneToMany_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2#isNonCacheable <em>Non Cacheable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Non Cacheable</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2#isNonCacheable()
	 * @see #getXmlOneToMany_2_2()
	 * @generated
	 */
	public EAttribute getXmlOneToMany_2_2_NonCacheable()
	{
		return (EAttribute)xmlOneToMany_2_2EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioning_2_2 <em>Xml Partitioning 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Partitioning 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioning_2_2
	 * @generated
	 */
	public EClass getXmlPartitioning_2_2()
	{
		return xmlPartitioning_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioning_2_2#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioning_2_2#getName()
	 * @see #getXmlPartitioning_2_2()
	 * @generated
	 */
	public EAttribute getXmlPartitioning_2_2_Name()
	{
		return (EAttribute)xmlPartitioning_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2 <em>Xml Partitioning Group 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Partitioning Group 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2
	 * @generated
	 */
	public EClass getXmlPartitioningGroup_2_2()
	{
		return xmlPartitioningGroup_2_2EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getPartitioning <em>Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getPartitioning()
	 * @see #getXmlPartitioningGroup_2_2()
	 * @generated
	 */
	public EReference getXmlPartitioningGroup_2_2_Partitioning()
	{
		return (EReference)xmlPartitioningGroup_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getReplicationPartitioning <em>Replication Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Replication Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getReplicationPartitioning()
	 * @see #getXmlPartitioningGroup_2_2()
	 * @generated
	 */
	public EReference getXmlPartitioningGroup_2_2_ReplicationPartitioning()
	{
		return (EReference)xmlPartitioningGroup_2_2EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getRoundRobinPartitioning <em>Round Robin Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Round Robin Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getRoundRobinPartitioning()
	 * @see #getXmlPartitioningGroup_2_2()
	 * @generated
	 */
	public EReference getXmlPartitioningGroup_2_2_RoundRobinPartitioning()
	{
		return (EReference)xmlPartitioningGroup_2_2EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getPinnedPartitioning <em>Pinned Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pinned Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getPinnedPartitioning()
	 * @see #getXmlPartitioningGroup_2_2()
	 * @generated
	 */
	public EReference getXmlPartitioningGroup_2_2_PinnedPartitioning()
	{
		return (EReference)xmlPartitioningGroup_2_2EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getRangePartitioning <em>Range Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Range Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getRangePartitioning()
	 * @see #getXmlPartitioningGroup_2_2()
	 * @generated
	 */
	public EReference getXmlPartitioningGroup_2_2_RangePartitioning()
	{
		return (EReference)xmlPartitioningGroup_2_2EClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getValuePartitioning <em>Value Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getValuePartitioning()
	 * @see #getXmlPartitioningGroup_2_2()
	 * @generated
	 */
	public EReference getXmlPartitioningGroup_2_2_ValuePartitioning()
	{
		return (EReference)xmlPartitioningGroup_2_2EClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getHashPartitioning <em>Hash Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Hash Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getHashPartitioning()
	 * @see #getXmlPartitioningGroup_2_2()
	 * @generated
	 */
	public EReference getXmlPartitioningGroup_2_2_HashPartitioning()
	{
		return (EReference)xmlPartitioningGroup_2_2EClass.getEStructuralFeatures().get(6);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getUnionPartitioning <em>Union Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Union Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getUnionPartitioning()
	 * @see #getXmlPartitioningGroup_2_2()
	 * @generated
	 */
	public EReference getXmlPartitioningGroup_2_2_UnionPartitioning()
	{
		return (EReference)xmlPartitioningGroup_2_2EClass.getEStructuralFeatures().get(7);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getPartitioned <em>Partitioned</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Partitioned</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2#getPartitioned()
	 * @see #getXmlPartitioningGroup_2_2()
	 * @generated
	 */
	public EAttribute getXmlPartitioningGroup_2_2_Partitioned()
	{
		return (EAttribute)xmlPartitioningGroup_2_2EClass.getEStructuralFeatures().get(8);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPinnedPartitioning_2_2 <em>Xml Pinned Partitioning 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Pinned Partitioning 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPinnedPartitioning_2_2
	 * @generated
	 */
	public EClass getXmlPinnedPartitioning_2_2()
	{
		return xmlPinnedPartitioning_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPinnedPartitioning_2_2#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPinnedPartitioning_2_2#getName()
	 * @see #getXmlPinnedPartitioning_2_2()
	 * @generated
	 */
	public EAttribute getXmlPinnedPartitioning_2_2_Name()
	{
		return (EAttribute)xmlPinnedPartitioning_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRangePartitioning_2_2 <em>Xml Range Partitioning 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Range Partitioning 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRangePartitioning_2_2
	 * @generated
	 */
	public EClass getXmlRangePartitioning_2_2()
	{
		return xmlRangePartitioning_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRangePartitioning_2_2#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRangePartitioning_2_2#getName()
	 * @see #getXmlRangePartitioning_2_2()
	 * @generated
	 */
	public EAttribute getXmlRangePartitioning_2_2_Name()
	{
		return (EAttribute)xmlRangePartitioning_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlReplicationPartitioning_2_2 <em>Xml Replication Partitioning 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Replication Partitioning 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlReplicationPartitioning_2_2
	 * @generated
	 */
	public EClass getXmlReplicationPartitioning_2_2()
	{
		return xmlReplicationPartitioning_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlReplicationPartitioning_2_2#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlReplicationPartitioning_2_2#getName()
	 * @see #getXmlReplicationPartitioning_2_2()
	 * @generated
	 */
	public EAttribute getXmlReplicationPartitioning_2_2_Name()
	{
		return (EAttribute)xmlReplicationPartitioning_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRoundRobinPartitioning_2_2 <em>Xml Round Robin Partitioning 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Round Robin Partitioning 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRoundRobinPartitioning_2_2
	 * @generated
	 */
	public EClass getXmlRoundRobinPartitioning_2_2()
	{
		return xmlRoundRobinPartitioning_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRoundRobinPartitioning_2_2#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRoundRobinPartitioning_2_2#getName()
	 * @see #getXmlRoundRobinPartitioning_2_2()
	 * @generated
	 */
	public EAttribute getXmlRoundRobinPartitioning_2_2_Name()
	{
		return (EAttribute)xmlRoundRobinPartitioning_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2 <em>Xml Embeddable 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embeddable 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2
	 * @generated
	 */
	public EClass getXmlEmbeddable_2_2()
	{
		return xmlEmbeddable_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2#getParentClass <em>Parent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parent Class</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2#getParentClass()
	 * @see #getXmlEmbeddable_2_2()
	 * @generated
	 */
	public EAttribute getXmlEmbeddable_2_2_ParentClass()
	{
		return (EAttribute)xmlEmbeddable_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlId_2_2 <em>Xml Id 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlId_2_2
	 * @generated
	 */
	public EClass getXmlId_2_2()
	{
		return xmlId_2_2EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlId_2_2#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlId_2_2#getIndex()
	 * @see #getXmlId_2_2()
	 * @generated
	 */
	public EReference getXmlId_2_2_Index()
	{
		return (EReference)xmlId_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2 <em>Xml Index 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Index 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2
	 * @generated
	 */
	public EClass getXmlIndex_2_2()
	{
		return xmlIndex_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getName()
	 * @see #getXmlIndex_2_2()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_2_Name()
	{
		return (EAttribute)xmlIndex_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getSchema()
	 * @see #getXmlIndex_2_2()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_2_Schema()
	{
		return (EAttribute)xmlIndex_2_2EClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getCatalog()
	 * @see #getXmlIndex_2_2()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_2_Catalog()
	{
		return (EAttribute)xmlIndex_2_2EClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Table</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getTable()
	 * @see #getXmlIndex_2_2()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_2_Table()
	{
		return (EAttribute)xmlIndex_2_2EClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getUnique <em>Unique</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getUnique()
	 * @see #getXmlIndex_2_2()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_2_Unique()
	{
		return (EAttribute)xmlIndex_2_2EClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getColumnNames <em>Column Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Column Names</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getColumnNames()
	 * @see #getXmlIndex_2_2()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_2_ColumnNames()
	{
		return (EAttribute)xmlIndex_2_2EClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlJoinTable_2_2 <em>Xml Join Table 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Join Table 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlJoinTable_2_2
	 * @generated
	 */
	public EClass getXmlJoinTable_2_2()
	{
		return xmlJoinTable_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlJoinTable_2_2#getCreationSuffix <em>Creation Suffix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Suffix</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlJoinTable_2_2#getCreationSuffix()
	 * @see #getXmlJoinTable_2_2()
	 * @generated
	 */
	public EAttribute getXmlJoinTable_2_2_CreationSuffix()
	{
		return (EAttribute)xmlJoinTable_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2 <em>Xml Mapped Superclass 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mapped Superclass 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2
	 * @generated
	 */
	public EClass getXmlMappedSuperclass_2_2()
	{
		return xmlMappedSuperclass_2_2EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2#getAdditionalCriteria <em>Additional Criteria</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Additional Criteria</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2#getAdditionalCriteria()
	 * @see #getXmlMappedSuperclass_2_2()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_2_2_AdditionalCriteria()
	{
		return (EReference)xmlMappedSuperclass_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlSecondaryTable_2_2 <em>Xml Secondary Table 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Secondary Table 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlSecondaryTable_2_2
	 * @generated
	 */
	public EClass getXmlSecondaryTable_2_2()
	{
		return xmlSecondaryTable_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlSecondaryTable_2_2#getCreationSuffix <em>Creation Suffix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Suffix</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlSecondaryTable_2_2#getCreationSuffix()
	 * @see #getXmlSecondaryTable_2_2()
	 * @generated
	 */
	public EAttribute getXmlSecondaryTable_2_2_CreationSuffix()
	{
		return (EAttribute)xmlSecondaryTable_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTable_2_2 <em>Xml Table 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Table 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTable_2_2
	 * @generated
	 */
	public EClass getXmlTable_2_2()
	{
		return xmlTable_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTable_2_2#getCreationSuffix <em>Creation Suffix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Suffix</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTable_2_2#getCreationSuffix()
	 * @see #getXmlTable_2_2()
	 * @generated
	 */
	public EAttribute getXmlTable_2_2_CreationSuffix()
	{
		return (EAttribute)xmlTable_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTableGenerator_2_2 <em>Xml Table Generator 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Table Generator 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTableGenerator_2_2
	 * @generated
	 */
	public EClass getXmlTableGenerator_2_2()
	{
		return xmlTableGenerator_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTableGenerator_2_2#getCreationSuffix <em>Creation Suffix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Suffix</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTableGenerator_2_2#getCreationSuffix()
	 * @see #getXmlTableGenerator_2_2()
	 * @generated
	 */
	public EAttribute getXmlTableGenerator_2_2_CreationSuffix()
	{
		return (EAttribute)xmlTableGenerator_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlUnionPartitioning_2_2 <em>Xml Union Partitioning 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Union Partitioning 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlUnionPartitioning_2_2
	 * @generated
	 */
	public EClass getXmlUnionPartitioning_2_2()
	{
		return xmlUnionPartitioning_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlUnionPartitioning_2_2#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlUnionPartitioning_2_2#getName()
	 * @see #getXmlUnionPartitioning_2_2()
	 * @generated
	 */
	public EAttribute getXmlUnionPartitioning_2_2_Name()
	{
		return (EAttribute)xmlUnionPartitioning_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlValuePartitioning_2_2 <em>Xml Value Partitioning 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Value Partitioning 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlValuePartitioning_2_2
	 * @generated
	 */
	public EClass getXmlValuePartitioning_2_2()
	{
		return xmlValuePartitioning_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlValuePartitioning_2_2#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlValuePartitioning_2_2#getName()
	 * @see #getXmlValuePartitioning_2_2()
	 * @generated
	 */
	public EAttribute getXmlValuePartitioning_2_2_Name()
	{
		return (EAttribute)xmlValuePartitioning_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVariableOneToOne_2_2 <em>Xml Variable One To One 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Variable One To One 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVariableOneToOne_2_2
	 * @generated
	 */
	public EClass getXmlVariableOneToOne_2_2()
	{
		return xmlVariableOneToOne_2_2EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVariableOneToOne_2_2#isNonCacheable <em>Non Cacheable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Non Cacheable</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVariableOneToOne_2_2#isNonCacheable()
	 * @see #getXmlVariableOneToOne_2_2()
	 * @generated
	 */
	public EAttribute getXmlVariableOneToOne_2_2_NonCacheable()
	{
		return (EAttribute)xmlVariableOneToOne_2_2EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2 <em>Xml Version 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Version 22</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2
	 * @generated
	 */
	public EClass getXmlVersion_2_2()
	{
		return xmlVersion_2_2EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2#getIndex()
	 * @see #getXmlVersion_2_2()
	 * @generated
	 */
	public EReference getXmlVersion_2_2_Index()
	{
		return (EReference)xmlVersion_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public EclipseLinkOrmV2_2Factory getEclipseLinkOrmV2_2Factory()
	{
		return (EclipseLinkOrmV2_2Factory)getEFactoryInstance();
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
		xmlAdditionalCriteria_2_2EClass = createEClass(XML_ADDITIONAL_CRITERIA_22);
		createEAttribute(xmlAdditionalCriteria_2_2EClass, XML_ADDITIONAL_CRITERIA_22__CRITERIA);

		xmlBasic_2_2EClass = createEClass(XML_BASIC_22);
		createEReference(xmlBasic_2_2EClass, XML_BASIC_22__INDEX);

		xmlBasicCollection_2_2EClass = createEClass(XML_BASIC_COLLECTION_22);
		createEAttribute(xmlBasicCollection_2_2EClass, XML_BASIC_COLLECTION_22__CASCADE_ON_DELETE);

		xmlBasicMap_2_2EClass = createEClass(XML_BASIC_MAP_22);
		createEAttribute(xmlBasicMap_2_2EClass, XML_BASIC_MAP_22__CASCADE_ON_DELETE);

		xmlCollectionTable_2_2EClass = createEClass(XML_COLLECTION_TABLE_22);
		createEAttribute(xmlCollectionTable_2_2EClass, XML_COLLECTION_TABLE_22__CREATION_SUFFIX);

		xmlElementCollection_2_2EClass = createEClass(XML_ELEMENT_COLLECTION_22);
		createEAttribute(xmlElementCollection_2_2EClass, XML_ELEMENT_COLLECTION_22__CASCADE_ON_DELETE);
		createEAttribute(xmlElementCollection_2_2EClass, XML_ELEMENT_COLLECTION_22__NON_CACHEABLE);

		xmlEmbeddable_2_2EClass = createEClass(XML_EMBEDDABLE_22);
		createEAttribute(xmlEmbeddable_2_2EClass, XML_EMBEDDABLE_22__PARENT_CLASS);

		xmlEntity_2_2EClass = createEClass(XML_ENTITY_22);
		createEReference(xmlEntity_2_2EClass, XML_ENTITY_22__ADDITIONAL_CRITERIA);
		createEAttribute(xmlEntity_2_2EClass, XML_ENTITY_22__CASCADE_ON_DELETE);
		createEReference(xmlEntity_2_2EClass, XML_ENTITY_22__INDEX);

		xmlEntityMappings_2_2EClass = createEClass(XML_ENTITY_MAPPINGS_22);
		createEReference(xmlEntityMappings_2_2EClass, XML_ENTITY_MAPPINGS_22__PARTITIONING);
		createEReference(xmlEntityMappings_2_2EClass, XML_ENTITY_MAPPINGS_22__REPLICATION_PARTITIONING);
		createEReference(xmlEntityMappings_2_2EClass, XML_ENTITY_MAPPINGS_22__ROUND_ROBIN_PARTITIONING);
		createEReference(xmlEntityMappings_2_2EClass, XML_ENTITY_MAPPINGS_22__PINNED_PARTITIONING);
		createEReference(xmlEntityMappings_2_2EClass, XML_ENTITY_MAPPINGS_22__RANGE_PARTITIONING);
		createEReference(xmlEntityMappings_2_2EClass, XML_ENTITY_MAPPINGS_22__VALUE_PARTITIONING);
		createEReference(xmlEntityMappings_2_2EClass, XML_ENTITY_MAPPINGS_22__HASH_PARTITIONING);
		createEReference(xmlEntityMappings_2_2EClass, XML_ENTITY_MAPPINGS_22__UNION_PARTITIONING);

		xmlHashPartitioning_2_2EClass = createEClass(XML_HASH_PARTITIONING_22);
		createEAttribute(xmlHashPartitioning_2_2EClass, XML_HASH_PARTITIONING_22__NAME);

		xmlId_2_2EClass = createEClass(XML_ID_22);
		createEReference(xmlId_2_2EClass, XML_ID_22__INDEX);

		xmlIndex_2_2EClass = createEClass(XML_INDEX_22);
		createEAttribute(xmlIndex_2_2EClass, XML_INDEX_22__NAME);
		createEAttribute(xmlIndex_2_2EClass, XML_INDEX_22__SCHEMA);
		createEAttribute(xmlIndex_2_2EClass, XML_INDEX_22__CATALOG);
		createEAttribute(xmlIndex_2_2EClass, XML_INDEX_22__TABLE);
		createEAttribute(xmlIndex_2_2EClass, XML_INDEX_22__UNIQUE);
		createEAttribute(xmlIndex_2_2EClass, XML_INDEX_22__COLUMN_NAMES);

		xmlJoinTable_2_2EClass = createEClass(XML_JOIN_TABLE_22);
		createEAttribute(xmlJoinTable_2_2EClass, XML_JOIN_TABLE_22__CREATION_SUFFIX);

		xmlManyToMany_2_2EClass = createEClass(XML_MANY_TO_MANY_22);
		createEAttribute(xmlManyToMany_2_2EClass, XML_MANY_TO_MANY_22__CASCADE_ON_DELETE);
		createEAttribute(xmlManyToMany_2_2EClass, XML_MANY_TO_MANY_22__NON_CACHEABLE);

		xmlManyToOne_2_2EClass = createEClass(XML_MANY_TO_ONE_22);
		createEAttribute(xmlManyToOne_2_2EClass, XML_MANY_TO_ONE_22__NON_CACHEABLE);

		xmlMappedSuperclass_2_2EClass = createEClass(XML_MAPPED_SUPERCLASS_22);
		createEReference(xmlMappedSuperclass_2_2EClass, XML_MAPPED_SUPERCLASS_22__ADDITIONAL_CRITERIA);

		xmlOneToOne_2_2EClass = createEClass(XML_ONE_TO_ONE_22);
		createEAttribute(xmlOneToOne_2_2EClass, XML_ONE_TO_ONE_22__CASCADE_ON_DELETE);
		createEAttribute(xmlOneToOne_2_2EClass, XML_ONE_TO_ONE_22__NON_CACHEABLE);

		xmlOneToMany_2_2EClass = createEClass(XML_ONE_TO_MANY_22);
		createEAttribute(xmlOneToMany_2_2EClass, XML_ONE_TO_MANY_22__CASCADE_ON_DELETE);
		createEAttribute(xmlOneToMany_2_2EClass, XML_ONE_TO_MANY_22__NON_CACHEABLE);

		xmlPartitioning_2_2EClass = createEClass(XML_PARTITIONING_22);
		createEAttribute(xmlPartitioning_2_2EClass, XML_PARTITIONING_22__NAME);

		xmlPartitioningGroup_2_2EClass = createEClass(XML_PARTITIONING_GROUP_22);
		createEReference(xmlPartitioningGroup_2_2EClass, XML_PARTITIONING_GROUP_22__PARTITIONING);
		createEReference(xmlPartitioningGroup_2_2EClass, XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING);
		createEReference(xmlPartitioningGroup_2_2EClass, XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING);
		createEReference(xmlPartitioningGroup_2_2EClass, XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING);
		createEReference(xmlPartitioningGroup_2_2EClass, XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING);
		createEReference(xmlPartitioningGroup_2_2EClass, XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING);
		createEReference(xmlPartitioningGroup_2_2EClass, XML_PARTITIONING_GROUP_22__HASH_PARTITIONING);
		createEReference(xmlPartitioningGroup_2_2EClass, XML_PARTITIONING_GROUP_22__UNION_PARTITIONING);
		createEAttribute(xmlPartitioningGroup_2_2EClass, XML_PARTITIONING_GROUP_22__PARTITIONED);

		xmlPinnedPartitioning_2_2EClass = createEClass(XML_PINNED_PARTITIONING_22);
		createEAttribute(xmlPinnedPartitioning_2_2EClass, XML_PINNED_PARTITIONING_22__NAME);

		xmlRangePartitioning_2_2EClass = createEClass(XML_RANGE_PARTITIONING_22);
		createEAttribute(xmlRangePartitioning_2_2EClass, XML_RANGE_PARTITIONING_22__NAME);

		xmlReplicationPartitioning_2_2EClass = createEClass(XML_REPLICATION_PARTITIONING_22);
		createEAttribute(xmlReplicationPartitioning_2_2EClass, XML_REPLICATION_PARTITIONING_22__NAME);

		xmlRoundRobinPartitioning_2_2EClass = createEClass(XML_ROUND_ROBIN_PARTITIONING_22);
		createEAttribute(xmlRoundRobinPartitioning_2_2EClass, XML_ROUND_ROBIN_PARTITIONING_22__NAME);

		xmlSecondaryTable_2_2EClass = createEClass(XML_SECONDARY_TABLE_22);
		createEAttribute(xmlSecondaryTable_2_2EClass, XML_SECONDARY_TABLE_22__CREATION_SUFFIX);

		xmlTable_2_2EClass = createEClass(XML_TABLE_22);
		createEAttribute(xmlTable_2_2EClass, XML_TABLE_22__CREATION_SUFFIX);

		xmlTableGenerator_2_2EClass = createEClass(XML_TABLE_GENERATOR_22);
		createEAttribute(xmlTableGenerator_2_2EClass, XML_TABLE_GENERATOR_22__CREATION_SUFFIX);

		xmlUnionPartitioning_2_2EClass = createEClass(XML_UNION_PARTITIONING_22);
		createEAttribute(xmlUnionPartitioning_2_2EClass, XML_UNION_PARTITIONING_22__NAME);

		xmlValuePartitioning_2_2EClass = createEClass(XML_VALUE_PARTITIONING_22);
		createEAttribute(xmlValuePartitioning_2_2EClass, XML_VALUE_PARTITIONING_22__NAME);

		xmlVariableOneToOne_2_2EClass = createEClass(XML_VARIABLE_ONE_TO_ONE_22);
		createEAttribute(xmlVariableOneToOne_2_2EClass, XML_VARIABLE_ONE_TO_ONE_22__NON_CACHEABLE);

		xmlVersion_2_2EClass = createEClass(XML_VERSION_22);
		createEReference(xmlVersion_2_2EClass, XML_VERSION_22__INDEX);
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
		xmlElementCollection_2_2EClass.getESuperTypes().add(this.getXmlPartitioningGroup_2_2());
		xmlEmbeddable_2_2EClass.getESuperTypes().add(theOrmPackage.getXmlAttributeOverrideContainer());
		xmlEmbeddable_2_2EClass.getESuperTypes().add(theOrmPackage.getXmlAssociationOverrideContainer());
		xmlEntity_2_2EClass.getESuperTypes().add(this.getXmlPartitioningGroup_2_2());
		xmlManyToMany_2_2EClass.getESuperTypes().add(this.getXmlPartitioningGroup_2_2());
		xmlManyToOne_2_2EClass.getESuperTypes().add(this.getXmlPartitioningGroup_2_2());
		xmlMappedSuperclass_2_2EClass.getESuperTypes().add(this.getXmlPartitioningGroup_2_2());
		xmlOneToOne_2_2EClass.getESuperTypes().add(this.getXmlPartitioningGroup_2_2());
		xmlOneToMany_2_2EClass.getESuperTypes().add(this.getXmlPartitioningGroup_2_2());
		xmlVariableOneToOne_2_2EClass.getESuperTypes().add(this.getXmlPartitioningGroup_2_2());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlAdditionalCriteria_2_2EClass, XmlAdditionalCriteria_2_2.class, "XmlAdditionalCriteria_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlAdditionalCriteria_2_2_Criteria(), theXMLTypePackage.getString(), "criteria", null, 0, 1, XmlAdditionalCriteria_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlBasic_2_2EClass, XmlBasic_2_2.class, "XmlBasic_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlBasic_2_2_Index(), this.getXmlIndex_2_2(), null, "index", null, 0, 1, XmlBasic_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlBasicCollection_2_2EClass, XmlBasicCollection_2_2.class, "XmlBasicCollection_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlBasicCollection_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlBasicCollection_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlBasicMap_2_2EClass, XmlBasicMap_2_2.class, "XmlBasicMap_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlBasicMap_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlBasicMap_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCollectionTable_2_2EClass, XmlCollectionTable_2_2.class, "XmlCollectionTable_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlCollectionTable_2_2_CreationSuffix(), theXMLTypePackage.getString(), "creationSuffix", null, 0, 1, XmlCollectionTable_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlElementCollection_2_2EClass, XmlElementCollection_2_2.class, "XmlElementCollection_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlElementCollection_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlElementCollection_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlElementCollection_2_2_NonCacheable(), theXMLTypePackage.getBoolean(), "nonCacheable", null, 0, 1, XmlElementCollection_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEmbeddable_2_2EClass, XmlEmbeddable_2_2.class, "XmlEmbeddable_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlEmbeddable_2_2_ParentClass(), theXMLTypePackage.getString(), "parentClass", null, 0, 1, XmlEmbeddable_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntity_2_2EClass, XmlEntity_2_2.class, "XmlEntity_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntity_2_2_AdditionalCriteria(), this.getXmlAdditionalCriteria_2_2(), null, "additionalCriteria", null, 0, 1, XmlEntity_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlEntity_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlEntity_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_2_2_Index(), this.getXmlIndex_2_2(), null, "index", null, 0, 1, XmlEntity_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntityMappings_2_2EClass, XmlEntityMappings_2_2.class, "XmlEntityMappings_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntityMappings_2_2_Partitioning(), this.getXmlPartitioning_2_2(), null, "partitioning", null, 0, -1, XmlEntityMappings_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_2_2_ReplicationPartitioning(), this.getXmlReplicationPartitioning_2_2(), null, "replicationPartitioning", null, 0, -1, XmlEntityMappings_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_2_2_RoundRobinPartitioning(), this.getXmlRoundRobinPartitioning_2_2(), null, "roundRobinPartitioning", null, 0, -1, XmlEntityMappings_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_2_2_PinnedPartitioning(), this.getXmlPinnedPartitioning_2_2(), null, "pinnedPartitioning", null, 0, -1, XmlEntityMappings_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_2_2_RangePartitioning(), this.getXmlRangePartitioning_2_2(), null, "rangePartitioning", null, 0, -1, XmlEntityMappings_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_2_2_ValuePartitioning(), this.getXmlValuePartitioning_2_2(), null, "valuePartitioning", null, 0, -1, XmlEntityMappings_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_2_2_HashPartitioning(), this.getXmlHashPartitioning_2_2(), null, "hashPartitioning", null, 0, -1, XmlEntityMappings_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_2_2_UnionPartitioning(), this.getXmlUnionPartitioning_2_2(), null, "unionPartitioning", null, 0, -1, XmlEntityMappings_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlHashPartitioning_2_2EClass, XmlHashPartitioning_2_2.class, "XmlHashPartitioning_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlHashPartitioning_2_2_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlHashPartitioning_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlId_2_2EClass, XmlId_2_2.class, "XmlId_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlId_2_2_Index(), this.getXmlIndex_2_2(), null, "index", null, 0, 1, XmlId_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlIndex_2_2EClass, XmlIndex_2_2.class, "XmlIndex_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlIndex_2_2_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlIndex_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlIndex_2_2_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, XmlIndex_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlIndex_2_2_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, XmlIndex_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlIndex_2_2_Table(), theXMLTypePackage.getString(), "table", null, 0, 1, XmlIndex_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlIndex_2_2_Unique(), theXMLTypePackage.getBooleanObject(), "unique", null, 0, 1, XmlIndex_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlIndex_2_2_ColumnNames(), theXMLTypePackage.getString(), "columnNames", null, 0, -1, XmlIndex_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlJoinTable_2_2EClass, XmlJoinTable_2_2.class, "XmlJoinTable_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlJoinTable_2_2_CreationSuffix(), theXMLTypePackage.getString(), "creationSuffix", null, 0, 1, XmlJoinTable_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlManyToMany_2_2EClass, XmlManyToMany_2_2.class, "XmlManyToMany_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlManyToMany_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlManyToMany_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlManyToMany_2_2_NonCacheable(), theXMLTypePackage.getBoolean(), "nonCacheable", null, 0, 1, XmlManyToMany_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlManyToOne_2_2EClass, XmlManyToOne_2_2.class, "XmlManyToOne_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlManyToOne_2_2_NonCacheable(), theXMLTypePackage.getBoolean(), "nonCacheable", null, 0, 1, XmlManyToOne_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMappedSuperclass_2_2EClass, XmlMappedSuperclass_2_2.class, "XmlMappedSuperclass_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlMappedSuperclass_2_2_AdditionalCriteria(), this.getXmlAdditionalCriteria_2_2(), null, "additionalCriteria", null, 0, 1, XmlMappedSuperclass_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToOne_2_2EClass, XmlOneToOne_2_2.class, "XmlOneToOne_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlOneToOne_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlOneToOne_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOneToOne_2_2_NonCacheable(), theXMLTypePackage.getBoolean(), "nonCacheable", null, 0, 1, XmlOneToOne_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToMany_2_2EClass, XmlOneToMany_2_2.class, "XmlOneToMany_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlOneToMany_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlOneToMany_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOneToMany_2_2_NonCacheable(), theXMLTypePackage.getBoolean(), "nonCacheable", null, 0, 1, XmlOneToMany_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPartitioning_2_2EClass, XmlPartitioning_2_2.class, "XmlPartitioning_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPartitioning_2_2_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlPartitioning_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPartitioningGroup_2_2EClass, XmlPartitioningGroup_2_2.class, "XmlPartitioningGroup_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlPartitioningGroup_2_2_Partitioning(), this.getXmlPartitioning_2_2(), null, "partitioning", null, 0, 1, XmlPartitioningGroup_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPartitioningGroup_2_2_ReplicationPartitioning(), this.getXmlReplicationPartitioning_2_2(), null, "replicationPartitioning", null, 0, 1, XmlPartitioningGroup_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPartitioningGroup_2_2_RoundRobinPartitioning(), this.getXmlRoundRobinPartitioning_2_2(), null, "roundRobinPartitioning", null, 0, 1, XmlPartitioningGroup_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPartitioningGroup_2_2_PinnedPartitioning(), this.getXmlPinnedPartitioning_2_2(), null, "pinnedPartitioning", null, 0, 1, XmlPartitioningGroup_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPartitioningGroup_2_2_RangePartitioning(), this.getXmlRangePartitioning_2_2(), null, "rangePartitioning", null, 0, 1, XmlPartitioningGroup_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPartitioningGroup_2_2_ValuePartitioning(), this.getXmlValuePartitioning_2_2(), null, "valuePartitioning", null, 0, 1, XmlPartitioningGroup_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPartitioningGroup_2_2_HashPartitioning(), this.getXmlHashPartitioning_2_2(), null, "hashPartitioning", null, 0, 1, XmlPartitioningGroup_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPartitioningGroup_2_2_UnionPartitioning(), this.getXmlUnionPartitioning_2_2(), null, "unionPartitioning", null, 0, 1, XmlPartitioningGroup_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPartitioningGroup_2_2_Partitioned(), theXMLTypePackage.getString(), "partitioned", null, 0, 1, XmlPartitioningGroup_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPinnedPartitioning_2_2EClass, XmlPinnedPartitioning_2_2.class, "XmlPinnedPartitioning_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPinnedPartitioning_2_2_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlPinnedPartitioning_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlRangePartitioning_2_2EClass, XmlRangePartitioning_2_2.class, "XmlRangePartitioning_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlRangePartitioning_2_2_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlRangePartitioning_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlReplicationPartitioning_2_2EClass, XmlReplicationPartitioning_2_2.class, "XmlReplicationPartitioning_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlReplicationPartitioning_2_2_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlReplicationPartitioning_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlRoundRobinPartitioning_2_2EClass, XmlRoundRobinPartitioning_2_2.class, "XmlRoundRobinPartitioning_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlRoundRobinPartitioning_2_2_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlRoundRobinPartitioning_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlSecondaryTable_2_2EClass, XmlSecondaryTable_2_2.class, "XmlSecondaryTable_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlSecondaryTable_2_2_CreationSuffix(), theXMLTypePackage.getString(), "creationSuffix", null, 0, 1, XmlSecondaryTable_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTable_2_2EClass, XmlTable_2_2.class, "XmlTable_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlTable_2_2_CreationSuffix(), theXMLTypePackage.getString(), "creationSuffix", null, 0, 1, XmlTable_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTableGenerator_2_2EClass, XmlTableGenerator_2_2.class, "XmlTableGenerator_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlTableGenerator_2_2_CreationSuffix(), theXMLTypePackage.getString(), "creationSuffix", null, 0, 1, XmlTableGenerator_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlUnionPartitioning_2_2EClass, XmlUnionPartitioning_2_2.class, "XmlUnionPartitioning_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlUnionPartitioning_2_2_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlUnionPartitioning_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlValuePartitioning_2_2EClass, XmlValuePartitioning_2_2.class, "XmlValuePartitioning_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlValuePartitioning_2_2_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlValuePartitioning_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlVariableOneToOne_2_2EClass, XmlVariableOneToOne_2_2.class, "XmlVariableOneToOne_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlVariableOneToOne_2_2_NonCacheable(), theXMLTypePackage.getBoolean(), "nonCacheable", null, 0, 1, XmlVariableOneToOne_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlVersion_2_2EClass, XmlVersion_2_2.class, "XmlVersion_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlVersion_2_2_Index(), this.getXmlIndex_2_2(), null, "index", null, 0, 1, XmlVersion_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2 <em>Xml Additional Criteria 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlAdditionalCriteria_2_2()
		 * @generated
		 */
		public static final EClass XML_ADDITIONAL_CRITERIA_22 = eINSTANCE.getXmlAdditionalCriteria_2_2();

		/**
		 * The meta object literal for the '<em><b>Criteria</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ADDITIONAL_CRITERIA_22__CRITERIA = eINSTANCE.getXmlAdditionalCriteria_2_2_Criteria();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2 <em>Xml Basic 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasic_2_2()
		 * @generated
		 */
		public static final EClass XML_BASIC_22 = eINSTANCE.getXmlBasic_2_2();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_BASIC_22__INDEX = eINSTANCE.getXmlBasic_2_2_Index();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2 <em>Xml Basic Collection 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasicCollection_2_2()
		 * @generated
		 */
		public static final EClass XML_BASIC_COLLECTION_22 = eINSTANCE.getXmlBasicCollection_2_2();

		/**
		 * The meta object literal for the '<em><b>Cascade On Delete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_BASIC_COLLECTION_22__CASCADE_ON_DELETE = eINSTANCE.getXmlBasicCollection_2_2_CascadeOnDelete();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2 <em>Xml Basic Map 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasicMap_2_2()
		 * @generated
		 */
		public static final EClass XML_BASIC_MAP_22 = eINSTANCE.getXmlBasicMap_2_2();

		/**
		 * The meta object literal for the '<em><b>Cascade On Delete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_BASIC_MAP_22__CASCADE_ON_DELETE = eINSTANCE.getXmlBasicMap_2_2_CascadeOnDelete();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlCollectionTable_2_2 <em>Xml Collection Table 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlCollectionTable_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlCollectionTable_2_2()
		 * @generated
		 */
		public static final EClass XML_COLLECTION_TABLE_22 = eINSTANCE.getXmlCollectionTable_2_2();

		/**
		 * The meta object literal for the '<em><b>Creation Suffix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_COLLECTION_TABLE_22__CREATION_SUFFIX = eINSTANCE.getXmlCollectionTable_2_2_CreationSuffix();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2 <em>Xml Element Collection 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlElementCollection_2_2()
		 * @generated
		 */
		public static final EClass XML_ELEMENT_COLLECTION_22 = eINSTANCE.getXmlElementCollection_2_2();

		/**
		 * The meta object literal for the '<em><b>Cascade On Delete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION_22__CASCADE_ON_DELETE = eINSTANCE.getXmlElementCollection_2_2_CascadeOnDelete();

		/**
		 * The meta object literal for the '<em><b>Non Cacheable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION_22__NON_CACHEABLE = eINSTANCE.getXmlElementCollection_2_2_NonCacheable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2 <em>Xml Entity 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntity_2_2()
		 * @generated
		 */
		public static final EClass XML_ENTITY_22 = eINSTANCE.getXmlEntity_2_2();

		/**
		 * The meta object literal for the '<em><b>Cascade On Delete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ENTITY_22__CASCADE_ON_DELETE = eINSTANCE.getXmlEntity_2_2_CascadeOnDelete();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_22__INDEX = eINSTANCE.getXmlEntity_2_2_Index();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2 <em>Xml Entity Mappings 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEntityMappings_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntityMappings_2_2()
		 * @generated
		 */
		public static final EClass XML_ENTITY_MAPPINGS_22 = eINSTANCE.getXmlEntityMappings_2_2();

		/**
		 * The meta object literal for the '<em><b>Partitioning</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_22__PARTITIONING = eINSTANCE.getXmlEntityMappings_2_2_Partitioning();

		/**
		 * The meta object literal for the '<em><b>Replication Partitioning</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_22__REPLICATION_PARTITIONING = eINSTANCE.getXmlEntityMappings_2_2_ReplicationPartitioning();

		/**
		 * The meta object literal for the '<em><b>Round Robin Partitioning</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_22__ROUND_ROBIN_PARTITIONING = eINSTANCE.getXmlEntityMappings_2_2_RoundRobinPartitioning();

		/**
		 * The meta object literal for the '<em><b>Pinned Partitioning</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_22__PINNED_PARTITIONING = eINSTANCE.getXmlEntityMappings_2_2_PinnedPartitioning();

		/**
		 * The meta object literal for the '<em><b>Range Partitioning</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_22__RANGE_PARTITIONING = eINSTANCE.getXmlEntityMappings_2_2_RangePartitioning();

		/**
		 * The meta object literal for the '<em><b>Value Partitioning</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_22__VALUE_PARTITIONING = eINSTANCE.getXmlEntityMappings_2_2_ValuePartitioning();

		/**
		 * The meta object literal for the '<em><b>Hash Partitioning</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_22__HASH_PARTITIONING = eINSTANCE.getXmlEntityMappings_2_2_HashPartitioning();

		/**
		 * The meta object literal for the '<em><b>Union Partitioning</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS_22__UNION_PARTITIONING = eINSTANCE.getXmlEntityMappings_2_2_UnionPartitioning();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlHashPartitioning_2_2 <em>Xml Hash Partitioning 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlHashPartitioning_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlHashPartitioning_2_2()
		 * @generated
		 */
		public static final EClass XML_HASH_PARTITIONING_22 = eINSTANCE.getXmlHashPartitioning_2_2();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_HASH_PARTITIONING_22__NAME = eINSTANCE.getXmlHashPartitioning_2_2_Name();

		/**
		 * The meta object literal for the '<em><b>Additional Criteria</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_22__ADDITIONAL_CRITERIA = eINSTANCE.getXmlEntity_2_2_AdditionalCriteria();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2 <em>Xml Many To Many 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlManyToMany_2_2()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_MANY_22 = eINSTANCE.getXmlManyToMany_2_2();

		/**
		 * The meta object literal for the '<em><b>Cascade On Delete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MANY_TO_MANY_22__CASCADE_ON_DELETE = eINSTANCE.getXmlManyToMany_2_2_CascadeOnDelete();

		/**
		 * The meta object literal for the '<em><b>Non Cacheable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MANY_TO_MANY_22__NON_CACHEABLE = eINSTANCE.getXmlManyToMany_2_2_NonCacheable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToOne_2_2 <em>Xml Many To One 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlManyToOne_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlManyToOne_2_2()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_ONE_22 = eINSTANCE.getXmlManyToOne_2_2();

		/**
		 * The meta object literal for the '<em><b>Non Cacheable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MANY_TO_ONE_22__NON_CACHEABLE = eINSTANCE.getXmlManyToOne_2_2_NonCacheable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2 <em>Xml One To One 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlOneToOne_2_2()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_ONE_22 = eINSTANCE.getXmlOneToOne_2_2();

		/**
		 * The meta object literal for the '<em><b>Cascade On Delete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_ONE_22__CASCADE_ON_DELETE = eINSTANCE.getXmlOneToOne_2_2_CascadeOnDelete();

		/**
		 * The meta object literal for the '<em><b>Non Cacheable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_ONE_22__NON_CACHEABLE = eINSTANCE.getXmlOneToOne_2_2_NonCacheable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2 <em>Xml One To Many 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlOneToMany_2_2()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_MANY_22 = eINSTANCE.getXmlOneToMany_2_2();

		/**
		 * The meta object literal for the '<em><b>Cascade On Delete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_MANY_22__CASCADE_ON_DELETE = eINSTANCE.getXmlOneToMany_2_2_CascadeOnDelete();

		/**
		 * The meta object literal for the '<em><b>Non Cacheable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_MANY_22__NON_CACHEABLE = eINSTANCE.getXmlOneToMany_2_2_NonCacheable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioning_2_2 <em>Xml Partitioning 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioning_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioning_2_2()
		 * @generated
		 */
		public static final EClass XML_PARTITIONING_22 = eINSTANCE.getXmlPartitioning_2_2();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PARTITIONING_22__NAME = eINSTANCE.getXmlPartitioning_2_2_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2 <em>Xml Partitioning Group 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPartitioningGroup_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPartitioningGroup_2_2()
		 * @generated
		 */
		public static final EClass XML_PARTITIONING_GROUP_22 = eINSTANCE.getXmlPartitioningGroup_2_2();

		/**
		 * The meta object literal for the '<em><b>Partitioning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PARTITIONING_GROUP_22__PARTITIONING = eINSTANCE.getXmlPartitioningGroup_2_2_Partitioning();

		/**
		 * The meta object literal for the '<em><b>Replication Partitioning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PARTITIONING_GROUP_22__REPLICATION_PARTITIONING = eINSTANCE.getXmlPartitioningGroup_2_2_ReplicationPartitioning();

		/**
		 * The meta object literal for the '<em><b>Round Robin Partitioning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PARTITIONING_GROUP_22__ROUND_ROBIN_PARTITIONING = eINSTANCE.getXmlPartitioningGroup_2_2_RoundRobinPartitioning();

		/**
		 * The meta object literal for the '<em><b>Pinned Partitioning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PARTITIONING_GROUP_22__PINNED_PARTITIONING = eINSTANCE.getXmlPartitioningGroup_2_2_PinnedPartitioning();

		/**
		 * The meta object literal for the '<em><b>Range Partitioning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PARTITIONING_GROUP_22__RANGE_PARTITIONING = eINSTANCE.getXmlPartitioningGroup_2_2_RangePartitioning();

		/**
		 * The meta object literal for the '<em><b>Value Partitioning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PARTITIONING_GROUP_22__VALUE_PARTITIONING = eINSTANCE.getXmlPartitioningGroup_2_2_ValuePartitioning();

		/**
		 * The meta object literal for the '<em><b>Hash Partitioning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PARTITIONING_GROUP_22__HASH_PARTITIONING = eINSTANCE.getXmlPartitioningGroup_2_2_HashPartitioning();

		/**
		 * The meta object literal for the '<em><b>Union Partitioning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PARTITIONING_GROUP_22__UNION_PARTITIONING = eINSTANCE.getXmlPartitioningGroup_2_2_UnionPartitioning();

		/**
		 * The meta object literal for the '<em><b>Partitioned</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PARTITIONING_GROUP_22__PARTITIONED = eINSTANCE.getXmlPartitioningGroup_2_2_Partitioned();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPinnedPartitioning_2_2 <em>Xml Pinned Partitioning 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlPinnedPartitioning_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlPinnedPartitioning_2_2()
		 * @generated
		 */
		public static final EClass XML_PINNED_PARTITIONING_22 = eINSTANCE.getXmlPinnedPartitioning_2_2();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PINNED_PARTITIONING_22__NAME = eINSTANCE.getXmlPinnedPartitioning_2_2_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRangePartitioning_2_2 <em>Xml Range Partitioning 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRangePartitioning_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlRangePartitioning_2_2()
		 * @generated
		 */
		public static final EClass XML_RANGE_PARTITIONING_22 = eINSTANCE.getXmlRangePartitioning_2_2();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_RANGE_PARTITIONING_22__NAME = eINSTANCE.getXmlRangePartitioning_2_2_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlReplicationPartitioning_2_2 <em>Xml Replication Partitioning 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlReplicationPartitioning_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlReplicationPartitioning_2_2()
		 * @generated
		 */
		public static final EClass XML_REPLICATION_PARTITIONING_22 = eINSTANCE.getXmlReplicationPartitioning_2_2();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_REPLICATION_PARTITIONING_22__NAME = eINSTANCE.getXmlReplicationPartitioning_2_2_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRoundRobinPartitioning_2_2 <em>Xml Round Robin Partitioning 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlRoundRobinPartitioning_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlRoundRobinPartitioning_2_2()
		 * @generated
		 */
		public static final EClass XML_ROUND_ROBIN_PARTITIONING_22 = eINSTANCE.getXmlRoundRobinPartitioning_2_2();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ROUND_ROBIN_PARTITIONING_22__NAME = eINSTANCE.getXmlRoundRobinPartitioning_2_2_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2 <em>Xml Embeddable 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEmbeddable_2_2()
		 * @generated
		 */
		public static final EClass XML_EMBEDDABLE_22 = eINSTANCE.getXmlEmbeddable_2_2();

		/**
		 * The meta object literal for the '<em><b>Parent Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_EMBEDDABLE_22__PARENT_CLASS = eINSTANCE.getXmlEmbeddable_2_2_ParentClass();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlId_2_2 <em>Xml Id 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlId_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlId_2_2()
		 * @generated
		 */
		public static final EClass XML_ID_22 = eINSTANCE.getXmlId_2_2();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ID_22__INDEX = eINSTANCE.getXmlId_2_2_Index();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2 <em>Xml Index 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlIndex_2_2()
		 * @generated
		 */
		public static final EClass XML_INDEX_22 = eINSTANCE.getXmlIndex_2_2();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_INDEX_22__NAME = eINSTANCE.getXmlIndex_2_2_Name();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_INDEX_22__SCHEMA = eINSTANCE.getXmlIndex_2_2_Schema();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_INDEX_22__CATALOG = eINSTANCE.getXmlIndex_2_2_Catalog();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_INDEX_22__TABLE = eINSTANCE.getXmlIndex_2_2_Table();

		/**
		 * The meta object literal for the '<em><b>Unique</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_INDEX_22__UNIQUE = eINSTANCE.getXmlIndex_2_2_Unique();

		/**
		 * The meta object literal for the '<em><b>Column Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_INDEX_22__COLUMN_NAMES = eINSTANCE.getXmlIndex_2_2_ColumnNames();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlJoinTable_2_2 <em>Xml Join Table 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlJoinTable_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlJoinTable_2_2()
		 * @generated
		 */
		public static final EClass XML_JOIN_TABLE_22 = eINSTANCE.getXmlJoinTable_2_2();

		/**
		 * The meta object literal for the '<em><b>Creation Suffix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_JOIN_TABLE_22__CREATION_SUFFIX = eINSTANCE.getXmlJoinTable_2_2_CreationSuffix();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2 <em>Xml Mapped Superclass 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlMappedSuperclass_2_2()
		 * @generated
		 */
		public static final EClass XML_MAPPED_SUPERCLASS_22 = eINSTANCE.getXmlMappedSuperclass_2_2();

		/**
		 * The meta object literal for the '<em><b>Additional Criteria</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS_22__ADDITIONAL_CRITERIA = eINSTANCE.getXmlMappedSuperclass_2_2_AdditionalCriteria();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlSecondaryTable_2_2 <em>Xml Secondary Table 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlSecondaryTable_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlSecondaryTable_2_2()
		 * @generated
		 */
		public static final EClass XML_SECONDARY_TABLE_22 = eINSTANCE.getXmlSecondaryTable_2_2();

		/**
		 * The meta object literal for the '<em><b>Creation Suffix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_SECONDARY_TABLE_22__CREATION_SUFFIX = eINSTANCE.getXmlSecondaryTable_2_2_CreationSuffix();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTable_2_2 <em>Xml Table 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTable_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlTable_2_2()
		 * @generated
		 */
		public static final EClass XML_TABLE_22 = eINSTANCE.getXmlTable_2_2();

		/**
		 * The meta object literal for the '<em><b>Creation Suffix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TABLE_22__CREATION_SUFFIX = eINSTANCE.getXmlTable_2_2_CreationSuffix();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTableGenerator_2_2 <em>Xml Table Generator 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlTableGenerator_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlTableGenerator_2_2()
		 * @generated
		 */
		public static final EClass XML_TABLE_GENERATOR_22 = eINSTANCE.getXmlTableGenerator_2_2();

		/**
		 * The meta object literal for the '<em><b>Creation Suffix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TABLE_GENERATOR_22__CREATION_SUFFIX = eINSTANCE.getXmlTableGenerator_2_2_CreationSuffix();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlUnionPartitioning_2_2 <em>Xml Union Partitioning 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlUnionPartitioning_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlUnionPartitioning_2_2()
		 * @generated
		 */
		public static final EClass XML_UNION_PARTITIONING_22 = eINSTANCE.getXmlUnionPartitioning_2_2();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_UNION_PARTITIONING_22__NAME = eINSTANCE.getXmlUnionPartitioning_2_2_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlValuePartitioning_2_2 <em>Xml Value Partitioning 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlValuePartitioning_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlValuePartitioning_2_2()
		 * @generated
		 */
		public static final EClass XML_VALUE_PARTITIONING_22 = eINSTANCE.getXmlValuePartitioning_2_2();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_VALUE_PARTITIONING_22__NAME = eINSTANCE.getXmlValuePartitioning_2_2_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVariableOneToOne_2_2 <em>Xml Variable One To One 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVariableOneToOne_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlVariableOneToOne_2_2()
		 * @generated
		 */
		public static final EClass XML_VARIABLE_ONE_TO_ONE_22 = eINSTANCE.getXmlVariableOneToOne_2_2();

		/**
		 * The meta object literal for the '<em><b>Non Cacheable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_VARIABLE_ONE_TO_ONE_22__NON_CACHEABLE = eINSTANCE.getXmlVariableOneToOne_2_2_NonCacheable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2 <em>Xml Version 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlVersion_2_2()
		 * @generated
		 */
		public static final EClass XML_VERSION_22 = eINSTANCE.getXmlVersion_2_2();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_VERSION_22__INDEX = eINSTANCE.getXmlVersion_2_2_Index();

	}

} //EclipseLinkOrmV2_2Package
