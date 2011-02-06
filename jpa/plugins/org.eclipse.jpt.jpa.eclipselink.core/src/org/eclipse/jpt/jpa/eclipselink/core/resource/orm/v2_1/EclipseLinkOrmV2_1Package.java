/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1;

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
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Factory
 * @model kind="package"
 * @generated
 */
public class EclipseLinkOrmV2_1Package extends EPackageImpl
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
	public static final String eNS_URI = "jpt.eclipselink.orm.v2_1.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLinkOrmV2_1Package eINSTANCE = org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1 <em>Xml Basic 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlBasic_2_1()
	 * @generated
	 */
	public static final int XML_BASIC_21 = 0;

	/**
	 * The feature id for the '<em><b>Return Insert</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_21__RETURN_INSERT = 0;

	/**
	 * The feature id for the '<em><b>Return Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_21__RETURN_UPDATE = 1;

	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_21__ATTRIBUTE_TYPE = 2;

	/**
	 * The number of structural features of the '<em>Xml Basic 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_21_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBatchFetch_2_1 <em>Xml Batch Fetch 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBatchFetch_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlBatchFetch_2_1()
	 * @generated
	 */
	public static final int XML_BATCH_FETCH_21 = 1;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BATCH_FETCH_21__SIZE = 0;

	/**
	 * The feature id for the '<em><b>Batch Fetch Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BATCH_FETCH_21__BATCH_FETCH_TYPE = 1;

	/**
	 * The number of structural features of the '<em>Xml Batch Fetch 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BATCH_FETCH_21_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlElementCollection_2_1 <em>Xml Element Collection 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlElementCollection_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlElementCollection_2_1()
	 * @generated
	 */
	public static final int XML_ELEMENT_COLLECTION_21 = 2;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_21__JOIN_FETCH = EclipseLinkOrmPackage.XML_JOIN_FETCH__JOIN_FETCH;

	/**
	 * The feature id for the '<em><b>Batch Fetch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_21__BATCH_FETCH = EclipseLinkOrmPackage.XML_JOIN_FETCH_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_21__ATTRIBUTE_TYPE = EclipseLinkOrmPackage.XML_JOIN_FETCH_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Element Collection 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_21_FEATURE_COUNT = EclipseLinkOrmPackage.XML_JOIN_FETCH_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddable_2_1 <em>Xml Embeddable 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddable_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlEmbeddable_2_1()
	 * @generated
	 */
	public static final int XML_EMBEDDABLE_21 = 3;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_21__ACCESS_METHODS = EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS;

	/**
	 * The number of structural features of the '<em>Xml Embeddable 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_21_FEATURE_COUNT = EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbedded_2_1 <em>Xml Embedded 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbedded_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlEmbedded_2_1()
	 * @generated
	 */
	public static final int XML_EMBEDDED_21 = 4;

	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_21__ATTRIBUTE_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Xml Embedded 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_21_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddedId_2_1 <em>Xml Embedded Id 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddedId_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlEmbeddedId_2_1()
	 * @generated
	 */
	public static final int XML_EMBEDDED_ID_21 = 5;

	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID_21__ATTRIBUTE_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Xml Embedded Id 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID_21_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroupContainer_2_1 <em>Xml Fetch Group Container 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroupContainer_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlFetchGroupContainer_2_1()
	 * @generated
	 */
	public static final int XML_FETCH_GROUP_CONTAINER_21 = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntity_2_1 <em>Xml Entity 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntity_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlEntity_2_1()
	 * @generated
	 */
	public static final int XML_ENTITY_21 = 6;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_21__ACCESS_METHODS = EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS;

	/**
	 * The feature id for the '<em><b>Fetch Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_21__FETCH_GROUPS = EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Class Extractor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_21__CLASS_EXTRACTOR = EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parent Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_21__PARENT_CLASS = EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Entity 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_21_FEATURE_COUNT = EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntityMappings_2_1 <em>Xml Entity Mappings 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntityMappings_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlEntityMappings_2_1()
	 * @generated
	 */
	public static final int XML_ENTITY_MAPPINGS_21 = 7;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_21__ACCESS_METHODS = EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS;

	/**
	 * The number of structural features of the '<em>Xml Entity Mappings 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_21_FEATURE_COUNT = EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchAttribute_2_1 <em>Xml Fetch Attribute 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchAttribute_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlFetchAttribute_2_1()
	 * @generated
	 */
	public static final int XML_FETCH_ATTRIBUTE_21 = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_ATTRIBUTE_21__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Fetch Attribute 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_ATTRIBUTE_21_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1 <em>Xml Fetch Group 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlFetchGroup_2_1()
	 * @generated
	 */
	public static final int XML_FETCH_GROUP_21 = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_GROUP_21__NAME = 0;

	/**
	 * The feature id for the '<em><b>Load</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_GROUP_21__LOAD = 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_GROUP_21__ATTRIBUTES = 2;

	/**
	 * The number of structural features of the '<em>Xml Fetch Group 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_GROUP_21_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Fetch Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_GROUP_CONTAINER_21__FETCH_GROUPS = 0;

	/**
	 * The number of structural features of the '<em>Xml Fetch Group Container 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_GROUP_CONTAINER_21_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlId_2_1 <em>Xml Id 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlId_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlId_2_1()
	 * @generated
	 */
	public static final int XML_ID_21 = 11;

	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_21__ATTRIBUTE_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Xml Id 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_21_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToMany_2_1 <em>Xml Many To Many 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToMany_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlManyToMany_2_1()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY_21 = 12;

	/**
	 * The feature id for the '<em><b>Batch Fetch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_21__BATCH_FETCH = EclipseLinkOrmPackage.XML_BATCH_FETCH_HOLDER__BATCH_FETCH;

	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_21__ATTRIBUTE_TYPE = EclipseLinkOrmPackage.XML_BATCH_FETCH_HOLDER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Many To Many 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_21_FEATURE_COUNT = EclipseLinkOrmPackage.XML_BATCH_FETCH_HOLDER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToOne_2_1 <em>Xml Many To One 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToOne_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlManyToOne_2_1()
	 * @generated
	 */
	public static final int XML_MANY_TO_ONE_21 = 13;

	/**
	 * The feature id for the '<em><b>Batch Fetch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_21__BATCH_FETCH = EclipseLinkOrmPackage.XML_BATCH_FETCH_HOLDER__BATCH_FETCH;

	/**
	 * The number of structural features of the '<em>Xml Many To One 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_21_FEATURE_COUNT = EclipseLinkOrmPackage.XML_BATCH_FETCH_HOLDER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1 <em>Xml Mapped Superclass 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlMappedSuperclass_2_1()
	 * @generated
	 */
	public static final int XML_MAPPED_SUPERCLASS_21 = 14;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_21__ASSOCIATION_OVERRIDES = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER__ASSOCIATION_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_21__ATTRIBUTE_OVERRIDES = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fetch Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_21__FETCH_GROUPS = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_21__SEQUENCE_GENERATOR = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_21__TABLE_GENERATOR = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_21__NAMED_QUERIES = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_21__NAMED_NATIVE_QUERIES = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Named Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_21__NAMED_STORED_PROCEDURE_QUERIES = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_21__ACCESS_METHODS = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_21__SQL_RESULT_SET_MAPPINGS = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Query Redirectors</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_21__QUERY_REDIRECTORS = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Parent Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_21__PARENT_CLASS = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Xml Mapped Superclass 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_21_FEATURE_COUNT = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToMany_2_1 <em>Xml One To Many 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToMany_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlOneToMany_2_1()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY_21 = 15;

	/**
	 * The feature id for the '<em><b>Batch Fetch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_21__BATCH_FETCH = EclipseLinkOrmPackage.XML_BATCH_FETCH_HOLDER__BATCH_FETCH;

	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_21__ATTRIBUTE_TYPE = EclipseLinkOrmPackage.XML_BATCH_FETCH_HOLDER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml One To Many 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_21_FEATURE_COUNT = EclipseLinkOrmPackage.XML_BATCH_FETCH_HOLDER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToOne_2_1 <em>Xml One To One 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToOne_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlOneToOne_2_1()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE_21 = 16;

	/**
	 * The feature id for the '<em><b>Batch Fetch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_21__BATCH_FETCH = EclipseLinkOrmPackage.XML_BATCH_FETCH_HOLDER__BATCH_FETCH;

	/**
	 * The number of structural features of the '<em>Xml One To One 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_21_FEATURE_COUNT = EclipseLinkOrmPackage.XML_BATCH_FETCH_HOLDER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPersistenceUnitDefaults_2_1 <em>Xml Persistence Unit Defaults 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPersistenceUnitDefaults_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlPersistenceUnitDefaults_2_1()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS_21 = 17;

	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS_21__ACCESS_METHODS = EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS;

	/**
	 * The number of structural features of the '<em>Xml Persistence Unit Defaults 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS_21_FEATURE_COUNT = EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlReturnInsert_2_1 <em>Xml Return Insert 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlReturnInsert_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlReturnInsert_2_1()
	 * @generated
	 */
	public static final int XML_RETURN_INSERT_21 = 19;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlTransformation_2_1 <em>Xml Transformation 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlTransformation_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlTransformation_2_1()
	 * @generated
	 */
	public static final int XML_TRANSFORMATION_21 = 20;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlVersion_2_1 <em>Xml Version 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlVersion_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlVersion_2_1()
	 * @generated
	 */
	public static final int XML_VERSION_21 = 21;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPrimaryKey_2_1 <em>Xml Primary Key 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPrimaryKey_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlPrimaryKey_2_1()
	 * @generated
	 */
	public static final int XML_PRIMARY_KEY_21 = 18;

	/**
	 * The feature id for the '<em><b>Cache Key Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_21__CACHE_KEY_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Xml Primary Key 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_21_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Return Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RETURN_INSERT_21__RETURN_ONLY = 0;

	/**
	 * The number of structural features of the '<em>Xml Return Insert 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RETURN_INSERT_21_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION_21__ATTRIBUTE_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Xml Transformation 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION_21_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_21__ATTRIBUTE_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Xml Version 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_21_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.CacheKeyType_2_1 <em>Cache Key Type 21</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.CacheKeyType_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getCacheKeyType_2_1()
	 * @generated
	 */
	public static final int CACHE_KEY_TYPE_21 = 22;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.BatchFetchType_2_1 <em>Batch Fetch Type 21</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.BatchFetchType_2_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getBatchFetchType_2_1()
	 * @generated
	 */
	public static final int BATCH_FETCH_TYPE_21 = 23;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBasic_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBatchFetch_2_1EClass = null;

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
	private EClass xmlEmbeddable_2_1EClass = null;

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
	private EClass xmlEmbeddedId_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntity_2_1EClass = null;

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
	private EClass xmlFetchAttribute_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlFetchGroup_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlFetchGroupContainer_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlId_2_1EClass = null;

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
	private EClass xmlMappedSuperclass_2_1EClass = null;

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
	private EClass xmlPersistenceUnitDefaults_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlReturnInsert_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTransformation_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlVersion_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPrimaryKey_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum cacheKeyType_2_1EEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum batchFetchType_2_1EEnum = null;

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EclipseLinkOrmV2_1Package()
	{
		super(eNS_URI, EclipseLinkOrmV2_1Factory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EclipseLinkOrmV2_1Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EclipseLinkOrmV2_1Package init()
	{
		if (isInited) return (EclipseLinkOrmV2_1Package)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI);

		// Obtain or create and register package
		EclipseLinkOrmV2_1Package theEclipseLinkOrmV2_1Package = (EclipseLinkOrmV2_1Package)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EclipseLinkOrmV2_1Package ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EclipseLinkOrmV2_1Package());

		isInited = true;

		// Initialize simple dependencies
		OrmPackage.eINSTANCE.eClass();
		CommonPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		EclipseLinkOrmPackage theEclipseLinkOrmPackage = (EclipseLinkOrmPackage)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) instanceof EclipseLinkOrmPackage ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) : EclipseLinkOrmPackage.eINSTANCE);
		EclipseLinkOrmV1_1Package theEclipseLinkOrmV1_1Package = (EclipseLinkOrmV1_1Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI) instanceof EclipseLinkOrmV1_1Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI) : EclipseLinkOrmV1_1Package.eINSTANCE);
		EclipseLinkOrmV2_0Package theEclipseLinkOrmV2_0Package = (EclipseLinkOrmV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) instanceof EclipseLinkOrmV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) : EclipseLinkOrmV2_0Package.eINSTANCE);
		EclipseLinkOrmV2_2Package theEclipseLinkOrmV2_2Package = (EclipseLinkOrmV2_2Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) instanceof EclipseLinkOrmV2_2Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) : EclipseLinkOrmV2_2Package.eINSTANCE);

		// Create package meta-data objects
		theEclipseLinkOrmV2_1Package.createPackageContents();
		theEclipseLinkOrmPackage.createPackageContents();
		theEclipseLinkOrmV1_1Package.createPackageContents();
		theEclipseLinkOrmV2_0Package.createPackageContents();
		theEclipseLinkOrmV2_2Package.createPackageContents();

		// Initialize created meta-data
		theEclipseLinkOrmV2_1Package.initializePackageContents();
		theEclipseLinkOrmPackage.initializePackageContents();
		theEclipseLinkOrmV1_1Package.initializePackageContents();
		theEclipseLinkOrmV2_0Package.initializePackageContents();
		theEclipseLinkOrmV2_2Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEclipseLinkOrmV2_1Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EclipseLinkOrmV2_1Package.eNS_URI, theEclipseLinkOrmV2_1Package);
		return theEclipseLinkOrmV2_1Package;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1 <em>Xml Basic 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1
	 * @generated
	 */
	public EClass getXmlBasic_2_1()
	{
		return xmlBasic_2_1EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1#getReturnInsert <em>Return Insert</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Return Insert</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1#getReturnInsert()
	 * @see #getXmlBasic_2_1()
	 * @generated
	 */
	public EReference getXmlBasic_2_1_ReturnInsert()
	{
		return (EReference)xmlBasic_2_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1#getReturnUpdate <em>Return Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Return Update</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1#getReturnUpdate()
	 * @see #getXmlBasic_2_1()
	 * @generated
	 */
	public EAttribute getXmlBasic_2_1_ReturnUpdate()
	{
		return (EAttribute)xmlBasic_2_1EClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1#getAttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1#getAttributeType()
	 * @see #getXmlBasic_2_1()
	 * @generated
	 */
	public EAttribute getXmlBasic_2_1_AttributeType()
	{
		return (EAttribute)xmlBasic_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBatchFetch_2_1 <em>Xml Batch Fetch 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Batch Fetch 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBatchFetch_2_1
	 * @generated
	 */
	public EClass getXmlBatchFetch_2_1()
	{
		return xmlBatchFetch_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBatchFetch_2_1#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBatchFetch_2_1#getSize()
	 * @see #getXmlBatchFetch_2_1()
	 * @generated
	 */
	public EAttribute getXmlBatchFetch_2_1_Size()
	{
		return (EAttribute)xmlBatchFetch_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBatchFetch_2_1#getBatchFetchType <em>Batch Fetch Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Batch Fetch Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBatchFetch_2_1#getBatchFetchType()
	 * @see #getXmlBatchFetch_2_1()
	 * @generated
	 */
	public EAttribute getXmlBatchFetch_2_1_BatchFetchType()
	{
		return (EAttribute)xmlBatchFetch_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlElementCollection_2_1 <em>Xml Element Collection 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Element Collection 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlElementCollection_2_1
	 * @generated
	 */
	public EClass getXmlElementCollection_2_1()
	{
		return xmlElementCollection_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlElementCollection_2_1#getAttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlElementCollection_2_1#getAttributeType()
	 * @see #getXmlElementCollection_2_1()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_2_1_AttributeType()
	{
		return (EAttribute)xmlElementCollection_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddable_2_1 <em>Xml Embeddable 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embeddable 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddable_2_1
	 * @generated
	 */
	public EClass getXmlEmbeddable_2_1()
	{
		return xmlEmbeddable_2_1EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbedded_2_1 <em>Xml Embedded 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbedded_2_1
	 * @generated
	 */
	public EClass getXmlEmbedded_2_1()
	{
		return xmlEmbedded_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbedded_2_1#getAttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbedded_2_1#getAttributeType()
	 * @see #getXmlEmbedded_2_1()
	 * @generated
	 */
	public EAttribute getXmlEmbedded_2_1_AttributeType()
	{
		return (EAttribute)xmlEmbedded_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddedId_2_1 <em>Xml Embedded Id 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded Id 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddedId_2_1
	 * @generated
	 */
	public EClass getXmlEmbeddedId_2_1()
	{
		return xmlEmbeddedId_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddedId_2_1#getAttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddedId_2_1#getAttributeType()
	 * @see #getXmlEmbeddedId_2_1()
	 * @generated
	 */
	public EAttribute getXmlEmbeddedId_2_1_AttributeType()
	{
		return (EAttribute)xmlEmbeddedId_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntity_2_1 <em>Xml Entity 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntity_2_1
	 * @generated
	 */
	public EClass getXmlEntity_2_1()
	{
		return xmlEntity_2_1EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntity_2_1#getClassExtractor <em>Class Extractor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Class Extractor</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntity_2_1#getClassExtractor()
	 * @see #getXmlEntity_2_1()
	 * @generated
	 */
	public EReference getXmlEntity_2_1_ClassExtractor()
	{
		return (EReference)xmlEntity_2_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntity_2_1#getParentClass <em>Parent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parent Class</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntity_2_1#getParentClass()
	 * @see #getXmlEntity_2_1()
	 * @generated
	 */
	public EAttribute getXmlEntity_2_1_ParentClass()
	{
		return (EAttribute)xmlEntity_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntityMappings_2_1 <em>Xml Entity Mappings 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity Mappings 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntityMappings_2_1
	 * @generated
	 */
	public EClass getXmlEntityMappings_2_1()
	{
		return xmlEntityMappings_2_1EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchAttribute_2_1 <em>Xml Fetch Attribute 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Fetch Attribute 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchAttribute_2_1
	 * @generated
	 */
	public EClass getXmlFetchAttribute_2_1()
	{
		return xmlFetchAttribute_2_1EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchAttribute_2_1#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchAttribute_2_1#getName()
	 * @see #getXmlFetchAttribute_2_1()
	 * @generated
	 */
	public EAttribute getXmlFetchAttribute_2_1_Name()
	{
		return (EAttribute)xmlFetchAttribute_2_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1 <em>Xml Fetch Group 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Fetch Group 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1
	 * @generated
	 */
	public EClass getXmlFetchGroup_2_1()
	{
		return xmlFetchGroup_2_1EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1#getName()
	 * @see #getXmlFetchGroup_2_1()
	 * @generated
	 */
	public EAttribute getXmlFetchGroup_2_1_Name()
	{
		return (EAttribute)xmlFetchGroup_2_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1#getAttributes()
	 * @see #getXmlFetchGroup_2_1()
	 * @generated
	 */
	public EReference getXmlFetchGroup_2_1_Attributes()
	{
		return (EReference)xmlFetchGroup_2_1EClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1#getLoad <em>Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Load</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1#getLoad()
	 * @see #getXmlFetchGroup_2_1()
	 * @generated
	 */
	public EAttribute getXmlFetchGroup_2_1_Load()
	{
		return (EAttribute)xmlFetchGroup_2_1EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroupContainer_2_1 <em>Xml Fetch Group Container 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Fetch Group Container 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroupContainer_2_1
	 * @generated
	 */
	public EClass getXmlFetchGroupContainer_2_1()
	{
		return xmlFetchGroupContainer_2_1EClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroupContainer_2_1#getFetchGroups <em>Fetch Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Fetch Groups</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroupContainer_2_1#getFetchGroups()
	 * @see #getXmlFetchGroupContainer_2_1()
	 * @generated
	 */
	public EReference getXmlFetchGroupContainer_2_1_FetchGroups()
	{
		return (EReference)xmlFetchGroupContainer_2_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlId_2_1 <em>Xml Id 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlId_2_1
	 * @generated
	 */
	public EClass getXmlId_2_1()
	{
		return xmlId_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlId_2_1#getAttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlId_2_1#getAttributeType()
	 * @see #getXmlId_2_1()
	 * @generated
	 */
	public EAttribute getXmlId_2_1_AttributeType()
	{
		return (EAttribute)xmlId_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToMany_2_1 <em>Xml Many To Many 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToMany_2_1
	 * @generated
	 */
	public EClass getXmlManyToMany_2_1()
	{
		return xmlManyToMany_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToMany_2_1#getAttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToMany_2_1#getAttributeType()
	 * @see #getXmlManyToMany_2_1()
	 * @generated
	 */
	public EAttribute getXmlManyToMany_2_1_AttributeType()
	{
		return (EAttribute)xmlManyToMany_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToOne_2_1 <em>Xml Many To One 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To One 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToOne_2_1
	 * @generated
	 */
	public EClass getXmlManyToOne_2_1()
	{
		return xmlManyToOne_2_1EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1 <em>Xml Mapped Superclass 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mapped Superclass 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1
	 * @generated
	 */
	public EClass getXmlMappedSuperclass_2_1()
	{
		return xmlMappedSuperclass_2_1EClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1#getSqlResultSetMappings <em>Sql Result Set Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sql Result Set Mappings</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1#getSqlResultSetMappings()
	 * @see #getXmlMappedSuperclass_2_1()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_2_1_SqlResultSetMappings()
	{
		return (EReference)xmlMappedSuperclass_2_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1#getQueryRedirectors <em>Query Redirectors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Query Redirectors</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1#getQueryRedirectors()
	 * @see #getXmlMappedSuperclass_2_1()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_2_1_QueryRedirectors()
	{
		return (EReference)xmlMappedSuperclass_2_1EClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1#getParentClass <em>Parent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parent Class</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1#getParentClass()
	 * @see #getXmlMappedSuperclass_2_1()
	 * @generated
	 */
	public EAttribute getXmlMappedSuperclass_2_1_ParentClass()
	{
		return (EAttribute)xmlMappedSuperclass_2_1EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToMany_2_1 <em>Xml One To Many 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToMany_2_1
	 * @generated
	 */
	public EClass getXmlOneToMany_2_1()
	{
		return xmlOneToMany_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToMany_2_1#getAttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToMany_2_1#getAttributeType()
	 * @see #getXmlOneToMany_2_1()
	 * @generated
	 */
	public EAttribute getXmlOneToMany_2_1_AttributeType()
	{
		return (EAttribute)xmlOneToMany_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToOne_2_1 <em>Xml One To One 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToOne_2_1
	 * @generated
	 */
	public EClass getXmlOneToOne_2_1()
	{
		return xmlOneToOne_2_1EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPersistenceUnitDefaults_2_1 <em>Xml Persistence Unit Defaults 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence Unit Defaults 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPersistenceUnitDefaults_2_1
	 * @generated
	 */
	public EClass getXmlPersistenceUnitDefaults_2_1()
	{
		return xmlPersistenceUnitDefaults_2_1EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlReturnInsert_2_1 <em>Xml Return Insert 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Return Insert 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlReturnInsert_2_1
	 * @generated
	 */
	public EClass getXmlReturnInsert_2_1()
	{
		return xmlReturnInsert_2_1EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlReturnInsert_2_1#getReturnOnly <em>Return Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Return Only</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlReturnInsert_2_1#getReturnOnly()
	 * @see #getXmlReturnInsert_2_1()
	 * @generated
	 */
	public EAttribute getXmlReturnInsert_2_1_ReturnOnly()
	{
		return (EAttribute)xmlReturnInsert_2_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlTransformation_2_1 <em>Xml Transformation 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Transformation 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlTransformation_2_1
	 * @generated
	 */
	public EClass getXmlTransformation_2_1()
	{
		return xmlTransformation_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlTransformation_2_1#getAttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlTransformation_2_1#getAttributeType()
	 * @see #getXmlTransformation_2_1()
	 * @generated
	 */
	public EAttribute getXmlTransformation_2_1_AttributeType()
	{
		return (EAttribute)xmlTransformation_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlVersion_2_1 <em>Xml Version 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Version 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlVersion_2_1
	 * @generated
	 */
	public EClass getXmlVersion_2_1()
	{
		return xmlVersion_2_1EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlVersion_2_1#getAttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlVersion_2_1#getAttributeType()
	 * @see #getXmlVersion_2_1()
	 * @generated
	 */
	public EAttribute getXmlVersion_2_1_AttributeType()
	{
		return (EAttribute)xmlVersion_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPrimaryKey_2_1 <em>Xml Primary Key 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Primary Key 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPrimaryKey_2_1
	 * @generated
	 */
	public EClass getXmlPrimaryKey_2_1()
	{
		return xmlPrimaryKey_2_1EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPrimaryKey_2_1#getCacheKeyType <em>Cache Key Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cache Key Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPrimaryKey_2_1#getCacheKeyType()
	 * @see #getXmlPrimaryKey_2_1()
	 * @generated
	 */
	public EAttribute getXmlPrimaryKey_2_1_CacheKeyType()
	{
		return (EAttribute)xmlPrimaryKey_2_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.CacheKeyType_2_1 <em>Cache Key Type 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Cache Key Type 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.CacheKeyType_2_1
	 * @generated
	 */
	public EEnum getCacheKeyType_2_1()
	{
		return cacheKeyType_2_1EEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.BatchFetchType_2_1 <em>Batch Fetch Type 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Batch Fetch Type 21</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.BatchFetchType_2_1
	 * @generated
	 */
	public EEnum getBatchFetchType_2_1()
	{
		return batchFetchType_2_1EEnum;
	}


	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public EclipseLinkOrmV2_1Factory getEclipseLinkOrmV2_1Factory()
	{
		return (EclipseLinkOrmV2_1Factory)getEFactoryInstance();
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
		xmlBasic_2_1EClass = createEClass(XML_BASIC_21);
		createEReference(xmlBasic_2_1EClass, XML_BASIC_21__RETURN_INSERT);
		createEAttribute(xmlBasic_2_1EClass, XML_BASIC_21__RETURN_UPDATE);
		createEAttribute(xmlBasic_2_1EClass, XML_BASIC_21__ATTRIBUTE_TYPE);

		xmlBatchFetch_2_1EClass = createEClass(XML_BATCH_FETCH_21);
		createEAttribute(xmlBatchFetch_2_1EClass, XML_BATCH_FETCH_21__SIZE);
		createEAttribute(xmlBatchFetch_2_1EClass, XML_BATCH_FETCH_21__BATCH_FETCH_TYPE);

		xmlElementCollection_2_1EClass = createEClass(XML_ELEMENT_COLLECTION_21);
		createEAttribute(xmlElementCollection_2_1EClass, XML_ELEMENT_COLLECTION_21__ATTRIBUTE_TYPE);

		xmlEmbeddable_2_1EClass = createEClass(XML_EMBEDDABLE_21);

		xmlEmbedded_2_1EClass = createEClass(XML_EMBEDDED_21);
		createEAttribute(xmlEmbedded_2_1EClass, XML_EMBEDDED_21__ATTRIBUTE_TYPE);

		xmlEmbeddedId_2_1EClass = createEClass(XML_EMBEDDED_ID_21);
		createEAttribute(xmlEmbeddedId_2_1EClass, XML_EMBEDDED_ID_21__ATTRIBUTE_TYPE);

		xmlEntity_2_1EClass = createEClass(XML_ENTITY_21);
		createEReference(xmlEntity_2_1EClass, XML_ENTITY_21__CLASS_EXTRACTOR);
		createEAttribute(xmlEntity_2_1EClass, XML_ENTITY_21__PARENT_CLASS);

		xmlEntityMappings_2_1EClass = createEClass(XML_ENTITY_MAPPINGS_21);

		xmlFetchAttribute_2_1EClass = createEClass(XML_FETCH_ATTRIBUTE_21);
		createEAttribute(xmlFetchAttribute_2_1EClass, XML_FETCH_ATTRIBUTE_21__NAME);

		xmlFetchGroup_2_1EClass = createEClass(XML_FETCH_GROUP_21);
		createEAttribute(xmlFetchGroup_2_1EClass, XML_FETCH_GROUP_21__NAME);
		createEAttribute(xmlFetchGroup_2_1EClass, XML_FETCH_GROUP_21__LOAD);
		createEReference(xmlFetchGroup_2_1EClass, XML_FETCH_GROUP_21__ATTRIBUTES);

		xmlFetchGroupContainer_2_1EClass = createEClass(XML_FETCH_GROUP_CONTAINER_21);
		createEReference(xmlFetchGroupContainer_2_1EClass, XML_FETCH_GROUP_CONTAINER_21__FETCH_GROUPS);

		xmlId_2_1EClass = createEClass(XML_ID_21);
		createEAttribute(xmlId_2_1EClass, XML_ID_21__ATTRIBUTE_TYPE);

		xmlManyToMany_2_1EClass = createEClass(XML_MANY_TO_MANY_21);
		createEAttribute(xmlManyToMany_2_1EClass, XML_MANY_TO_MANY_21__ATTRIBUTE_TYPE);

		xmlManyToOne_2_1EClass = createEClass(XML_MANY_TO_ONE_21);

		xmlMappedSuperclass_2_1EClass = createEClass(XML_MAPPED_SUPERCLASS_21);
		createEReference(xmlMappedSuperclass_2_1EClass, XML_MAPPED_SUPERCLASS_21__SQL_RESULT_SET_MAPPINGS);
		createEReference(xmlMappedSuperclass_2_1EClass, XML_MAPPED_SUPERCLASS_21__QUERY_REDIRECTORS);
		createEAttribute(xmlMappedSuperclass_2_1EClass, XML_MAPPED_SUPERCLASS_21__PARENT_CLASS);

		xmlOneToMany_2_1EClass = createEClass(XML_ONE_TO_MANY_21);
		createEAttribute(xmlOneToMany_2_1EClass, XML_ONE_TO_MANY_21__ATTRIBUTE_TYPE);

		xmlOneToOne_2_1EClass = createEClass(XML_ONE_TO_ONE_21);

		xmlPersistenceUnitDefaults_2_1EClass = createEClass(XML_PERSISTENCE_UNIT_DEFAULTS_21);

		xmlPrimaryKey_2_1EClass = createEClass(XML_PRIMARY_KEY_21);
		createEAttribute(xmlPrimaryKey_2_1EClass, XML_PRIMARY_KEY_21__CACHE_KEY_TYPE);

		xmlReturnInsert_2_1EClass = createEClass(XML_RETURN_INSERT_21);
		createEAttribute(xmlReturnInsert_2_1EClass, XML_RETURN_INSERT_21__RETURN_ONLY);

		xmlTransformation_2_1EClass = createEClass(XML_TRANSFORMATION_21);
		createEAttribute(xmlTransformation_2_1EClass, XML_TRANSFORMATION_21__ATTRIBUTE_TYPE);

		xmlVersion_2_1EClass = createEClass(XML_VERSION_21);
		createEAttribute(xmlVersion_2_1EClass, XML_VERSION_21__ATTRIBUTE_TYPE);

		// Create enums
		cacheKeyType_2_1EEnum = createEEnum(CACHE_KEY_TYPE_21);
		batchFetchType_2_1EEnum = createEEnum(BATCH_FETCH_TYPE_21);
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
		xmlElementCollection_2_1EClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlJoinFetch());
		xmlElementCollection_2_1EClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlBatchFetchHolder());
		xmlEmbeddable_2_1EClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlAccessMethodsHolder());
		xmlEntity_2_1EClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlAccessMethodsHolder());
		xmlEntity_2_1EClass.getESuperTypes().add(this.getXmlFetchGroupContainer_2_1());
		xmlEntityMappings_2_1EClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlAccessMethodsHolder());
		xmlManyToMany_2_1EClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlBatchFetchHolder());
		xmlManyToOne_2_1EClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlBatchFetchHolder());
		xmlMappedSuperclass_2_1EClass.getESuperTypes().add(theOrmPackage.getXmlAssociationOverrideContainer());
		xmlMappedSuperclass_2_1EClass.getESuperTypes().add(theOrmPackage.getXmlAttributeOverrideContainer());
		xmlMappedSuperclass_2_1EClass.getESuperTypes().add(this.getXmlFetchGroupContainer_2_1());
		xmlMappedSuperclass_2_1EClass.getESuperTypes().add(theOrmPackage.getXmlGeneratorContainer());
		xmlMappedSuperclass_2_1EClass.getESuperTypes().add(theOrmPackage.getXmlQueryContainer());
		xmlMappedSuperclass_2_1EClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlQueryContainer());
		xmlMappedSuperclass_2_1EClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlAccessMethodsHolder());
		xmlOneToMany_2_1EClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlBatchFetchHolder());
		xmlOneToOne_2_1EClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlBatchFetchHolder());
		xmlPersistenceUnitDefaults_2_1EClass.getESuperTypes().add(theEclipseLinkOrmPackage.getXmlAccessMethodsHolder());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlBasic_2_1EClass, XmlBasic_2_1.class, "XmlBasic_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlBasic_2_1_ReturnInsert(), theEclipseLinkOrmPackage.getXmlReturnInsert(), null, "returnInsert", null, 0, 1, XmlBasic_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlBasic_2_1_ReturnUpdate(), theXMLTypePackage.getBooleanObject(), "returnUpdate", null, 0, 1, XmlBasic_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlBasic_2_1_AttributeType(), theXMLTypePackage.getString(), "attributeType", null, 0, 1, XmlBasic_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlBatchFetch_2_1EClass, XmlBatchFetch_2_1.class, "XmlBatchFetch_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlBatchFetch_2_1_Size(), theXMLTypePackage.getIntObject(), "size", null, 0, 1, XmlBatchFetch_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlBatchFetch_2_1_BatchFetchType(), this.getBatchFetchType_2_1(), "batchFetchType", null, 0, 1, XmlBatchFetch_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlElementCollection_2_1EClass, XmlElementCollection_2_1.class, "XmlElementCollection_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlElementCollection_2_1_AttributeType(), theXMLTypePackage.getString(), "attributeType", null, 0, 1, XmlElementCollection_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEmbeddable_2_1EClass, XmlEmbeddable_2_1.class, "XmlEmbeddable_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEmbedded_2_1EClass, XmlEmbedded_2_1.class, "XmlEmbedded_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlEmbedded_2_1_AttributeType(), theXMLTypePackage.getString(), "attributeType", null, 0, 1, XmlEmbedded_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEmbeddedId_2_1EClass, XmlEmbeddedId_2_1.class, "XmlEmbeddedId_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlEmbeddedId_2_1_AttributeType(), theXMLTypePackage.getString(), "attributeType", null, 0, 1, XmlEmbeddedId_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntity_2_1EClass, XmlEntity_2_1.class, "XmlEntity_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntity_2_1_ClassExtractor(), theOrmPackage.getXmlClassReference(), null, "classExtractor", null, 0, 1, XmlEntity_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlEntity_2_1_ParentClass(), theXMLTypePackage.getString(), "parentClass", null, 0, 1, XmlEntity_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntityMappings_2_1EClass, XmlEntityMappings_2_1.class, "XmlEntityMappings_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlFetchAttribute_2_1EClass, XmlFetchAttribute_2_1.class, "XmlFetchAttribute_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlFetchAttribute_2_1_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlFetchAttribute_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlFetchGroup_2_1EClass, XmlFetchGroup_2_1.class, "XmlFetchGroup_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlFetchGroup_2_1_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlFetchGroup_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlFetchGroup_2_1_Load(), theXMLTypePackage.getBooleanObject(), "load", null, 0, 1, XmlFetchGroup_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlFetchGroup_2_1_Attributes(), theEclipseLinkOrmPackage.getXmlFetchAttribute(), null, "attributes", null, 0, -1, XmlFetchGroup_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlFetchGroupContainer_2_1EClass, XmlFetchGroupContainer_2_1.class, "XmlFetchGroupContainer_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlFetchGroupContainer_2_1_FetchGroups(), theEclipseLinkOrmPackage.getXmlFetchGroup(), null, "fetchGroups", null, 0, -1, XmlFetchGroupContainer_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlId_2_1EClass, XmlId_2_1.class, "XmlId_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlId_2_1_AttributeType(), theXMLTypePackage.getString(), "attributeType", null, 0, 1, XmlId_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlManyToMany_2_1EClass, XmlManyToMany_2_1.class, "XmlManyToMany_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlManyToMany_2_1_AttributeType(), theXMLTypePackage.getString(), "attributeType", null, 0, 1, XmlManyToMany_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlManyToOne_2_1EClass, XmlManyToOne_2_1.class, "XmlManyToOne_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlMappedSuperclass_2_1EClass, XmlMappedSuperclass_2_1.class, "XmlMappedSuperclass_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlMappedSuperclass_2_1_SqlResultSetMappings(), theOrmPackage.getSqlResultSetMapping(), null, "sqlResultSetMappings", null, 0, -1, XmlMappedSuperclass_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlMappedSuperclass_2_1_QueryRedirectors(), theEclipseLinkOrmPackage.getXmlQueryRedirectors(), null, "queryRedirectors", null, 0, 1, XmlMappedSuperclass_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlMappedSuperclass_2_1_ParentClass(), theXMLTypePackage.getString(), "parentClass", null, 0, 1, XmlMappedSuperclass_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToMany_2_1EClass, XmlOneToMany_2_1.class, "XmlOneToMany_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlOneToMany_2_1_AttributeType(), theXMLTypePackage.getString(), "attributeType", null, 0, 1, XmlOneToMany_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToOne_2_1EClass, XmlOneToOne_2_1.class, "XmlOneToOne_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPersistenceUnitDefaults_2_1EClass, XmlPersistenceUnitDefaults_2_1.class, "XmlPersistenceUnitDefaults_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPrimaryKey_2_1EClass, XmlPrimaryKey_2_1.class, "XmlPrimaryKey_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPrimaryKey_2_1_CacheKeyType(), this.getCacheKeyType_2_1(), "cacheKeyType", null, 0, 1, XmlPrimaryKey_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlReturnInsert_2_1EClass, XmlReturnInsert_2_1.class, "XmlReturnInsert_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlReturnInsert_2_1_ReturnOnly(), theXMLTypePackage.getBooleanObject(), "returnOnly", null, 0, 1, XmlReturnInsert_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTransformation_2_1EClass, XmlTransformation_2_1.class, "XmlTransformation_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlTransformation_2_1_AttributeType(), theXMLTypePackage.getString(), "attributeType", null, 0, 1, XmlTransformation_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlVersion_2_1EClass, XmlVersion_2_1.class, "XmlVersion_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlVersion_2_1_AttributeType(), theXMLTypePackage.getString(), "attributeType", null, 0, 1, XmlVersion_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(cacheKeyType_2_1EEnum, CacheKeyType_2_1.class, "CacheKeyType_2_1");
		addEEnumLiteral(cacheKeyType_2_1EEnum, CacheKeyType_2_1.ID_VALUE);
		addEEnumLiteral(cacheKeyType_2_1EEnum, CacheKeyType_2_1.CACHE_KEY);
		addEEnumLiteral(cacheKeyType_2_1EEnum, CacheKeyType_2_1.AUTO);

		initEEnum(batchFetchType_2_1EEnum, BatchFetchType_2_1.class, "BatchFetchType_2_1");
		addEEnumLiteral(batchFetchType_2_1EEnum, BatchFetchType_2_1.JOIN);
		addEEnumLiteral(batchFetchType_2_1EEnum, BatchFetchType_2_1.EXISTS);
		addEEnumLiteral(batchFetchType_2_1EEnum, BatchFetchType_2_1.IN);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1 <em>Xml Basic 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlBasic_2_1()
		 * @generated
		 */
		public static final EClass XML_BASIC_21 = eINSTANCE.getXmlBasic_2_1();

		/**
		 * The meta object literal for the '<em><b>Return Insert</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_BASIC_21__RETURN_INSERT = eINSTANCE.getXmlBasic_2_1_ReturnInsert();

		/**
		 * The meta object literal for the '<em><b>Return Update</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_BASIC_21__RETURN_UPDATE = eINSTANCE.getXmlBasic_2_1_ReturnUpdate();

		/**
		 * The meta object literal for the '<em><b>Attribute Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_BASIC_21__ATTRIBUTE_TYPE = eINSTANCE.getXmlBasic_2_1_AttributeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBatchFetch_2_1 <em>Xml Batch Fetch 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlBatchFetch_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlBatchFetch_2_1()
		 * @generated
		 */
		public static final EClass XML_BATCH_FETCH_21 = eINSTANCE.getXmlBatchFetch_2_1();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_BATCH_FETCH_21__SIZE = eINSTANCE.getXmlBatchFetch_2_1_Size();

		/**
		 * The meta object literal for the '<em><b>Batch Fetch Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_BATCH_FETCH_21__BATCH_FETCH_TYPE = eINSTANCE.getXmlBatchFetch_2_1_BatchFetchType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlElementCollection_2_1 <em>Xml Element Collection 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlElementCollection_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlElementCollection_2_1()
		 * @generated
		 */
		public static final EClass XML_ELEMENT_COLLECTION_21 = eINSTANCE.getXmlElementCollection_2_1();

		/**
		 * The meta object literal for the '<em><b>Attribute Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION_21__ATTRIBUTE_TYPE = eINSTANCE.getXmlElementCollection_2_1_AttributeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddable_2_1 <em>Xml Embeddable 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddable_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlEmbeddable_2_1()
		 * @generated
		 */
		public static final EClass XML_EMBEDDABLE_21 = eINSTANCE.getXmlEmbeddable_2_1();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbedded_2_1 <em>Xml Embedded 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbedded_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlEmbedded_2_1()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED_21 = eINSTANCE.getXmlEmbedded_2_1();

		/**
		 * The meta object literal for the '<em><b>Attribute Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_EMBEDDED_21__ATTRIBUTE_TYPE = eINSTANCE.getXmlEmbedded_2_1_AttributeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddedId_2_1 <em>Xml Embedded Id 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddedId_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlEmbeddedId_2_1()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED_ID_21 = eINSTANCE.getXmlEmbeddedId_2_1();

		/**
		 * The meta object literal for the '<em><b>Attribute Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_EMBEDDED_ID_21__ATTRIBUTE_TYPE = eINSTANCE.getXmlEmbeddedId_2_1_AttributeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntity_2_1 <em>Xml Entity 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntity_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlEntity_2_1()
		 * @generated
		 */
		public static final EClass XML_ENTITY_21 = eINSTANCE.getXmlEntity_2_1();

		/**
		 * The meta object literal for the '<em><b>Class Extractor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_21__CLASS_EXTRACTOR = eINSTANCE.getXmlEntity_2_1_ClassExtractor();

		/**
		 * The meta object literal for the '<em><b>Parent Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ENTITY_21__PARENT_CLASS = eINSTANCE.getXmlEntity_2_1_ParentClass();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntityMappings_2_1 <em>Xml Entity Mappings 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEntityMappings_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlEntityMappings_2_1()
		 * @generated
		 */
		public static final EClass XML_ENTITY_MAPPINGS_21 = eINSTANCE.getXmlEntityMappings_2_1();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchAttribute_2_1 <em>Xml Fetch Attribute 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchAttribute_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlFetchAttribute_2_1()
		 * @generated
		 */
		public static final EClass XML_FETCH_ATTRIBUTE_21 = eINSTANCE.getXmlFetchAttribute_2_1();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_FETCH_ATTRIBUTE_21__NAME = eINSTANCE.getXmlFetchAttribute_2_1_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1 <em>Xml Fetch Group 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlFetchGroup_2_1()
		 * @generated
		 */
		public static final EClass XML_FETCH_GROUP_21 = eINSTANCE.getXmlFetchGroup_2_1();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_FETCH_GROUP_21__NAME = eINSTANCE.getXmlFetchGroup_2_1_Name();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_FETCH_GROUP_21__ATTRIBUTES = eINSTANCE.getXmlFetchGroup_2_1_Attributes();

		/**
		 * The meta object literal for the '<em><b>Load</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_FETCH_GROUP_21__LOAD = eINSTANCE.getXmlFetchGroup_2_1_Load();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroupContainer_2_1 <em>Xml Fetch Group Container 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroupContainer_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlFetchGroupContainer_2_1()
		 * @generated
		 */
		public static final EClass XML_FETCH_GROUP_CONTAINER_21 = eINSTANCE.getXmlFetchGroupContainer_2_1();

		/**
		 * The meta object literal for the '<em><b>Fetch Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_FETCH_GROUP_CONTAINER_21__FETCH_GROUPS = eINSTANCE.getXmlFetchGroupContainer_2_1_FetchGroups();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlId_2_1 <em>Xml Id 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlId_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlId_2_1()
		 * @generated
		 */
		public static final EClass XML_ID_21 = eINSTANCE.getXmlId_2_1();

		/**
		 * The meta object literal for the '<em><b>Attribute Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ID_21__ATTRIBUTE_TYPE = eINSTANCE.getXmlId_2_1_AttributeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToMany_2_1 <em>Xml Many To Many 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToMany_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlManyToMany_2_1()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_MANY_21 = eINSTANCE.getXmlManyToMany_2_1();

		/**
		 * The meta object literal for the '<em><b>Attribute Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MANY_TO_MANY_21__ATTRIBUTE_TYPE = eINSTANCE.getXmlManyToMany_2_1_AttributeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToOne_2_1 <em>Xml Many To One 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlManyToOne_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlManyToOne_2_1()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_ONE_21 = eINSTANCE.getXmlManyToOne_2_1();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1 <em>Xml Mapped Superclass 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlMappedSuperclass_2_1()
		 * @generated
		 */
		public static final EClass XML_MAPPED_SUPERCLASS_21 = eINSTANCE.getXmlMappedSuperclass_2_1();

		/**
		 * The meta object literal for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS_21__SQL_RESULT_SET_MAPPINGS = eINSTANCE.getXmlMappedSuperclass_2_1_SqlResultSetMappings();

		/**
		 * The meta object literal for the '<em><b>Query Redirectors</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS_21__QUERY_REDIRECTORS = eINSTANCE.getXmlMappedSuperclass_2_1_QueryRedirectors();

		/**
		 * The meta object literal for the '<em><b>Parent Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MAPPED_SUPERCLASS_21__PARENT_CLASS = eINSTANCE.getXmlMappedSuperclass_2_1_ParentClass();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToMany_2_1 <em>Xml One To Many 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToMany_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlOneToMany_2_1()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_MANY_21 = eINSTANCE.getXmlOneToMany_2_1();

		/**
		 * The meta object literal for the '<em><b>Attribute Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_MANY_21__ATTRIBUTE_TYPE = eINSTANCE.getXmlOneToMany_2_1_AttributeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToOne_2_1 <em>Xml One To One 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlOneToOne_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlOneToOne_2_1()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_ONE_21 = eINSTANCE.getXmlOneToOne_2_1();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPersistenceUnitDefaults_2_1 <em>Xml Persistence Unit Defaults 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPersistenceUnitDefaults_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlPersistenceUnitDefaults_2_1()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE_UNIT_DEFAULTS_21 = eINSTANCE.getXmlPersistenceUnitDefaults_2_1();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlReturnInsert_2_1 <em>Xml Return Insert 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlReturnInsert_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlReturnInsert_2_1()
		 * @generated
		 */
		public static final EClass XML_RETURN_INSERT_21 = eINSTANCE.getXmlReturnInsert_2_1();

		/**
		 * The meta object literal for the '<em><b>Return Only</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_RETURN_INSERT_21__RETURN_ONLY = eINSTANCE.getXmlReturnInsert_2_1_ReturnOnly();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlTransformation_2_1 <em>Xml Transformation 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlTransformation_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlTransformation_2_1()
		 * @generated
		 */
		public static final EClass XML_TRANSFORMATION_21 = eINSTANCE.getXmlTransformation_2_1();

		/**
		 * The meta object literal for the '<em><b>Attribute Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TRANSFORMATION_21__ATTRIBUTE_TYPE = eINSTANCE.getXmlTransformation_2_1_AttributeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlVersion_2_1 <em>Xml Version 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlVersion_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlVersion_2_1()
		 * @generated
		 */
		public static final EClass XML_VERSION_21 = eINSTANCE.getXmlVersion_2_1();

		/**
		 * The meta object literal for the '<em><b>Attribute Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_VERSION_21__ATTRIBUTE_TYPE = eINSTANCE.getXmlVersion_2_1_AttributeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPrimaryKey_2_1 <em>Xml Primary Key 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPrimaryKey_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlPrimaryKey_2_1()
		 * @generated
		 */
		public static final EClass XML_PRIMARY_KEY_21 = eINSTANCE.getXmlPrimaryKey_2_1();

		/**
		 * The meta object literal for the '<em><b>Cache Key Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PRIMARY_KEY_21__CACHE_KEY_TYPE = eINSTANCE.getXmlPrimaryKey_2_1_CacheKeyType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.CacheKeyType_2_1 <em>Cache Key Type 21</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.CacheKeyType_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getCacheKeyType_2_1()
		 * @generated
		 */
		public static final EEnum CACHE_KEY_TYPE_21 = eINSTANCE.getCacheKeyType_2_1();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.BatchFetchType_2_1 <em>Batch Fetch Type 21</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.BatchFetchType_2_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getBatchFetchType_2_1()
		 * @generated
		 */
		public static final EEnum BATCH_FETCH_TYPE_21 = eINSTANCE.getBatchFetchType_2_1();

	}

} //EclipseLinkOrmV2_1Package
