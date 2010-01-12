/*******************************************************************************
 *  Copyright (c) 2009, 2010 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.resource.orm.v2_0;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.persistence.PersistencePackage;
import org.eclipse.jpt.core.resource.persistence.v2_0.PersistenceV2_0Package;
import org.eclipse.jpt.core.resource.xml.CommonPackage;

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
 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Factory
 * @model kind="package"
 * @generated
 */
public class OrmV2_0Package extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "v2_0";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.orm.v2_0.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.core.resource.orm.v2_0";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final OrmV2_0Package eINSTANCE = org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeMapping_2_0 <em>Xml Attribute Mapping 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeMapping_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlAttributeMapping_2_0()
	 * @generated
	 */
	public static final int XML_ATTRIBUTE_MAPPING_20 = 0;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING_20__ACCESS = OrmPackage.XML_ACCESS_HOLDER__ACCESS;

	/**
	 * The number of structural features of the '<em>Xml Attribute Mapping 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING_20_FEATURE_COUNT = OrmPackage.XML_ACCESS_HOLDER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAssociationOverride_2_0 <em>Xml Association Override 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAssociationOverride_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlAssociationOverride_2_0()
	 * @generated
	 */
	public static final int XML_ASSOCIATION_OVERRIDE_20 = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE_20__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE_20__JOIN_TABLE = 1;

	/**
	 * The number of structural features of the '<em>Xml Association Override 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE_20_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeOverride_2_0 <em>Xml Attribute Override 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeOverride_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlAttributeOverride_2_0()
	 * @generated
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE_20 = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE_20__DESCRIPTION = 0;

	/**
	 * The number of structural features of the '<em>Xml Attribute Override 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributes_2_0 <em>Xml Attributes 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributes_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlAttributes_2_0()
	 * @generated
	 */
	public static final int XML_ATTRIBUTES_20 = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTES_20__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Element Collections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTES_20__ELEMENT_COLLECTIONS = 1;

	/**
	 * The number of structural features of the '<em>Xml Attributes 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTES_20_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlCollectionTable_2_0 <em>Xml Collection Table 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlCollectionTable_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlCollectionTable_2_0()
	 * @generated
	 */
	public static final int XML_COLLECTION_TABLE_20 = 4;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE_20__JOIN_COLUMNS = OrmPackage.XML_REFERENCE_TABLE__JOIN_COLUMNS;

	/**
	 * The number of structural features of the '<em>Xml Collection Table 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE_20_FEATURE_COUNT = OrmPackage.XML_REFERENCE_TABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0 <em>Xml Derived Id 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlDerivedId_2_0()
	 * @generated
	 */
	public static final int XML_DERIVED_ID_20 = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DERIVED_ID_20__ID = 0;

	/**
	 * The number of structural features of the '<em>Xml Derived Id 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DERIVED_ID_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0 <em>Xml Element Collection 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0()
	 * @generated
	 */
	public static final int XML_ELEMENT_COLLECTION_20 = 6;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__LOB = OrmPackage.XML_CONVERTIBLE_MAPPING__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__TEMPORAL = OrmPackage.XML_CONVERTIBLE_MAPPING__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__ENUMERATED = OrmPackage.XML_CONVERTIBLE_MAPPING__ENUMERATED;

	/**
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__ORDER_COLUMN = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__ORDER_BY = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__ATTRIBUTE_OVERRIDES = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__ASSOCIATION_OVERRIDES = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Target Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__TARGET_CLASS = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__FETCH = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__MAP_KEY = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__MAP_KEY_CLASS = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Map Key Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__MAP_KEY_TEMPORAL = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__MAP_KEY_ENUMERATED = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__MAP_KEY_ATTRIBUTE_OVERRIDES = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__MAP_KEY_COLUMN = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__MAP_KEY_JOIN_COLUMNS = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__COLUMN = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Collection Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20__COLLECTION_TABLE = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 14;

	/**
	 * The number of structural features of the '<em>Xml Element Collection 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_20_FEATURE_COUNT = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 15;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEmbedded_2_0 <em>Xml Embedded 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEmbedded_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlEmbedded_2_0()
	 * @generated
	 */
	public static final int XML_EMBEDDED_20 = 7;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_20__ASSOCIATION_OVERRIDES = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER__ASSOCIATION_OVERRIDES;

	/**
	 * The number of structural features of the '<em>Xml Embedded 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_20_FEATURE_COUNT = OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0 <em>Xml Cacheable 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlCacheable_2_0()
	 * @generated
	 */
	public static final int XML_CACHEABLE_20 = 27;

	/**
	 * The feature id for the '<em><b>Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHEABLE_20__CACHEABLE = 0;

	/**
	 * The number of structural features of the '<em>Xml Cacheable 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHEABLE_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEntity_2_0 <em>Xml Entity 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEntity_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlEntity_2_0()
	 * @generated
	 */
	public static final int XML_ENTITY_20 = 8;

	/**
	 * The feature id for the '<em><b>Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_20__CACHEABLE = XML_CACHEABLE_20__CACHEABLE;

	/**
	 * The number of structural features of the '<em>Xml Entity 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_20_FEATURE_COUNT = XML_CACHEABLE_20_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEntityListener_2_0 <em>Xml Entity Listener 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEntityListener_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlEntityListener_2_0()
	 * @generated
	 */
	public static final int XML_ENTITY_LISTENER_20 = 9;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_LISTENER_20__DESCRIPTION = 0;

	/**
	 * The number of structural features of the '<em>Xml Entity Listener 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_LISTENER_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEventMethod_2_0 <em>Xml Event Method 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEventMethod_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlEventMethod_2_0()
	 * @generated
	 */
	public static final int XML_EVENT_METHOD_20 = 10;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EVENT_METHOD_20__DESCRIPTION = 0;

	/**
	 * The number of structural features of the '<em>Xml Event Method 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EVENT_METHOD_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlGenerator_2_0 <em>Xml Generator 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlGenerator_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlGenerator_2_0()
	 * @generated
	 */
	public static final int XML_GENERATOR_20 = 11;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR_20__DESCRIPTION = 0;

	/**
	 * The number of structural features of the '<em>Xml Generator 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderable_2_0 <em>Xml Orderable 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderable_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlOrderable_2_0()
	 * @generated
	 */
	public static final int XML_ORDERABLE_20 = 29;

	/**
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDERABLE_20__ORDER_COLUMN = 0;

	/**
	 * The number of structural features of the '<em>Xml Orderable 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDERABLE_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0 <em>Xml Many To Many 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlManyToMany_2_0()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY_20 = 12;

	/**
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_20__ORDER_COLUMN = XML_ORDERABLE_20__ORDER_COLUMN;

	/**
	 * The feature id for the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_20__MAP_KEY_CLASS = XML_ORDERABLE_20_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Map Key Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_20__MAP_KEY_TEMPORAL = XML_ORDERABLE_20_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_20__MAP_KEY_ENUMERATED = XML_ORDERABLE_20_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_20__MAP_KEY_ATTRIBUTE_OVERRIDES = XML_ORDERABLE_20_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_20__MAP_KEY_COLUMN = XML_ORDERABLE_20_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_20__MAP_KEY_JOIN_COLUMNS = XML_ORDERABLE_20_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Xml Many To Many 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_20_FEATURE_COUNT = XML_ORDERABLE_20_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlMapKeyClass_2_0 <em>Xml Map Key Class 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlMapKeyClass_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlMapKeyClass_2_0()
	 * @generated
	 */
	public static final int XML_MAP_KEY_CLASS_20 = 13;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_CLASS_20__CLASS_NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Map Key Class 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_CLASS_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0 <em>Xml Maps Id 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlMapsId_2_0()
	 * @generated
	 */
	public static final int XML_MAPS_ID_20 = 14;

	/**
	 * The feature id for the '<em><b>Maps Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPS_ID_20__MAPS_ID = 0;

	/**
	 * The number of structural features of the '<em>Xml Maps Id 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPS_ID_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlNamedQuery_2_0 <em>Xml Named Query 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlNamedQuery_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlNamedQuery_2_0()
	 * @generated
	 */
	public static final int XML_NAMED_QUERY_20 = 15;

	/**
	 * The feature id for the '<em><b>Lock Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY_20__LOCK_MODE = 0;

	/**
	 * The number of structural features of the '<em>Xml Named Query 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0 <em>Xml Orphan Removable 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlOrphanRemovable_2_0()
	 * @generated
	 */
	public static final int XML_ORPHAN_REMOVABLE_20 = 28;

	/**
	 * The feature id for the '<em><b>Orphan Removal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORPHAN_REMOVABLE_20__ORPHAN_REMOVAL = 0;

	/**
	 * The number of structural features of the '<em>Xml Orphan Removable 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORPHAN_REMOVABLE_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0 <em>Xml One To Many 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlOneToMany_2_0()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY_20 = 16;

	/**
	 * The feature id for the '<em><b>Orphan Removal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_20__ORPHAN_REMOVAL = XML_ORPHAN_REMOVABLE_20__ORPHAN_REMOVAL;

	/**
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_20__ORDER_COLUMN = XML_ORPHAN_REMOVABLE_20_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_20__MAP_KEY_CLASS = XML_ORPHAN_REMOVABLE_20_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Map Key Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_20__MAP_KEY_TEMPORAL = XML_ORPHAN_REMOVABLE_20_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_20__MAP_KEY_ENUMERATED = XML_ORPHAN_REMOVABLE_20_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_20__MAP_KEY_ATTRIBUTE_OVERRIDES = XML_ORPHAN_REMOVABLE_20_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_20__MAP_KEY_COLUMN = XML_ORPHAN_REMOVABLE_20_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_20__MAP_KEY_JOIN_COLUMNS = XML_ORPHAN_REMOVABLE_20_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Xml One To Many 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_20_FEATURE_COUNT = XML_ORPHAN_REMOVABLE_20_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0 <em>Xml Single Relationship Mapping 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlSingleRelationshipMapping_2_0()
	 * @generated
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING_20 = 24;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING_20__ID = XML_DERIVED_ID_20__ID;

	/**
	 * The feature id for the '<em><b>Maps Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING_20__MAPS_ID = XML_DERIVED_ID_20_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Single Relationship Mapping 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SINGLE_RELATIONSHIP_MAPPING_20_FEATURE_COUNT = XML_DERIVED_ID_20_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToOne_2_0 <em>Xml One To One 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToOne_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlOneToOne_2_0()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE_20 = 17;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_20__ID = XML_SINGLE_RELATIONSHIP_MAPPING_20__ID;

	/**
	 * The feature id for the '<em><b>Maps Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_20__MAPS_ID = XML_SINGLE_RELATIONSHIP_MAPPING_20__MAPS_ID;

	/**
	 * The feature id for the '<em><b>Orphan Removal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_20__ORPHAN_REMOVAL = XML_SINGLE_RELATIONSHIP_MAPPING_20_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml One To One 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_20_FEATURE_COUNT = XML_SINGLE_RELATIONSHIP_MAPPING_20_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderColumn_2_0 <em>Xml Order Column 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderColumn_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlOrderColumn_2_0()
	 * @generated
	 */
	public static final int XML_ORDER_COLUMN_20 = 18;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN_20__NULLABLE = 0;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN_20__INSERTABLE = 1;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN_20__UPDATABLE = 2;

	/**
	 * The number of structural features of the '<em>Xml Order Column 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN_20_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitDefaults_2_0 <em>Xml Persistence Unit Defaults 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitDefaults_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlPersistenceUnitDefaults_2_0()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS_20 = 19;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS_20__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Delimited Identifiers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS_20__DELIMITED_IDENTIFIERS = 1;

	/**
	 * The number of structural features of the '<em>Xml Persistence Unit Defaults 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS_20_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitMetadata_2_0 <em>Xml Persistence Unit Metadata 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitMetadata_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlPersistenceUnitMetadata_2_0()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA_20 = 20;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA_20__DESCRIPTION = 0;

	/**
	 * The number of structural features of the '<em>Xml Persistence Unit Metadata 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlQuery_2_0 <em>Xml Query 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlQuery_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlQuery_2_0()
	 * @generated
	 */
	public static final int XML_QUERY_20 = 21;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_20__DESCRIPTION = 0;

	/**
	 * The number of structural features of the '<em>Xml Query 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlQueryHint_2_0 <em>Xml Query Hint 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlQueryHint_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlQueryHint_2_0()
	 * @generated
	 */
	public static final int XML_QUERY_HINT_20 = 22;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_HINT_20__DESCRIPTION = 0;

	/**
	 * The number of structural features of the '<em>Xml Query Hint 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_HINT_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlSequenceGenerator_2_0 <em>Xml Sequence Generator 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlSequenceGenerator_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlSequenceGenerator_2_0()
	 * @generated
	 */
	public static final int XML_SEQUENCE_GENERATOR_20 = 23;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR_20__CATALOG = 0;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR_20__SCHEMA = 1;

	/**
	 * The number of structural features of the '<em>Xml Sequence Generator 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR_20_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlSqlResultSetMapping_2_0 <em>Xml Sql Result Set Mapping 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlSqlResultSetMapping_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlSqlResultSetMapping_2_0()
	 * @generated
	 */
	public static final int XML_SQL_RESULT_SET_MAPPING_20 = 25;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SQL_RESULT_SET_MAPPING_20__DESCRIPTION = 0;

	/**
	 * The number of structural features of the '<em>Xml Sql Result Set Mapping 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SQL_RESULT_SET_MAPPING_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlUniqueConstraint_2_0 <em>Xml Unique Constraint 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlUniqueConstraint_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlUniqueConstraint_2_0()
	 * @generated
	 */
	public static final int XML_UNIQUE_CONSTRAINT_20 = 26;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNIQUE_CONSTRAINT_20__NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Unique Constraint 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNIQUE_CONSTRAINT_20_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.LockModeType_2_0 <em>Lock Mode Type 20</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.LockModeType_2_0
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getLockModeType_2_0()
	 * @generated
	 */
	public static final int LOCK_MODE_TYPE_20 = 30;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAttributeMapping_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAssociationOverride_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAttributeOverride_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAttributes_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCollectionTable_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlDerivedId_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlElementCollection_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEmbedded_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntity_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntityListener_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEventMethod_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlGenerator_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlManyToMany_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMapKeyClass_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMapsId_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedQuery_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOneToMany_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOneToOne_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOrderColumn_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPersistenceUnitDefaults_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPersistenceUnitMetadata_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlQuery_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlQueryHint_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlSequenceGenerator_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlSingleRelationshipMapping_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlSqlResultSetMapping_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlUniqueConstraint_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCacheable_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOrphanRemovable_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOrderable_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum lockModeType_2_0EEnum = null;

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
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private OrmV2_0Package()
	{
		super(eNS_URI, OrmV2_0Factory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link OrmV2_0Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static OrmV2_0Package init()
	{
		if (isInited) return (OrmV2_0Package)EPackage.Registry.INSTANCE.getEPackage(OrmV2_0Package.eNS_URI);

		// Obtain or create and register package
		OrmV2_0Package theOrmV2_0Package = (OrmV2_0Package)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof OrmV2_0Package ? EPackage.Registry.INSTANCE.get(eNS_URI) : new OrmV2_0Package());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		CommonPackage theCommonPackage = (CommonPackage)(EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) instanceof CommonPackage ? EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) : CommonPackage.eINSTANCE);
		OrmPackage theOrmPackage = (OrmPackage)(EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) : OrmPackage.eINSTANCE);
		PersistencePackage thePersistencePackage = (PersistencePackage)(EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);
		PersistenceV2_0Package thePersistenceV2_0Package = (PersistenceV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(PersistenceV2_0Package.eNS_URI) instanceof PersistenceV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(PersistenceV2_0Package.eNS_URI) : PersistenceV2_0Package.eINSTANCE);

		// Create package meta-data objects
		theOrmV2_0Package.createPackageContents();
		theCommonPackage.createPackageContents();
		theOrmPackage.createPackageContents();
		thePersistencePackage.createPackageContents();
		thePersistenceV2_0Package.createPackageContents();

		// Initialize created meta-data
		theOrmV2_0Package.initializePackageContents();
		theCommonPackage.initializePackageContents();
		theOrmPackage.initializePackageContents();
		thePersistencePackage.initializePackageContents();
		thePersistenceV2_0Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theOrmV2_0Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(OrmV2_0Package.eNS_URI, theOrmV2_0Package);
		return theOrmV2_0Package;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeMapping_2_0 <em>Xml Attribute Mapping 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Attribute Mapping 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeMapping_2_0
	 * @generated
	 */
	public EClass getXmlAttributeMapping_2_0()
	{
		return xmlAttributeMapping_2_0EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAssociationOverride_2_0 <em>Xml Association Override 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Association Override 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAssociationOverride_2_0
	 * @generated
	 */
	public EClass getXmlAssociationOverride_2_0()
	{
		return xmlAssociationOverride_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAssociationOverride_2_0#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAssociationOverride_2_0#getDescription()
	 * @see #getXmlAssociationOverride_2_0()
	 * @generated
	 */
	public EAttribute getXmlAssociationOverride_2_0_Description()
	{
		return (EAttribute)xmlAssociationOverride_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAssociationOverride_2_0#getJoinTable <em>Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Table</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAssociationOverride_2_0#getJoinTable()
	 * @see #getXmlAssociationOverride_2_0()
	 * @generated
	 */
	public EReference getXmlAssociationOverride_2_0_JoinTable()
	{
		return (EReference)xmlAssociationOverride_2_0EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeOverride_2_0 <em>Xml Attribute Override 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Attribute Override 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeOverride_2_0
	 * @generated
	 */
	public EClass getXmlAttributeOverride_2_0()
	{
		return xmlAttributeOverride_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeOverride_2_0#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeOverride_2_0#getDescription()
	 * @see #getXmlAttributeOverride_2_0()
	 * @generated
	 */
	public EAttribute getXmlAttributeOverride_2_0_Description()
	{
		return (EAttribute)xmlAttributeOverride_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributes_2_0 <em>Xml Attributes 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Attributes 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributes_2_0
	 * @generated
	 */
	public EClass getXmlAttributes_2_0()
	{
		return xmlAttributes_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributes_2_0#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributes_2_0#getDescription()
	 * @see #getXmlAttributes_2_0()
	 * @generated
	 */
	public EAttribute getXmlAttributes_2_0_Description()
	{
		return (EAttribute)xmlAttributes_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributes_2_0#getElementCollections <em>Element Collections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Element Collections</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributes_2_0#getElementCollections()
	 * @see #getXmlAttributes_2_0()
	 * @generated
	 */
	public EReference getXmlAttributes_2_0_ElementCollections()
	{
		return (EReference)xmlAttributes_2_0EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlCollectionTable_2_0 <em>Xml Collection Table 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Collection Table 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlCollectionTable_2_0
	 * @generated
	 */
	public EClass getXmlCollectionTable_2_0()
	{
		return xmlCollectionTable_2_0EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0 <em>Xml Derived Id 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Derived Id 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0
	 * @generated
	 */
	public EClass getXmlDerivedId_2_0()
	{
		return xmlDerivedId_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0#getId()
	 * @see #getXmlDerivedId_2_0()
	 * @generated
	 */
	public EAttribute getXmlDerivedId_2_0_Id()
	{
		return (EAttribute)xmlDerivedId_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0 <em>Xml Element Collection 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Element Collection 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0
	 * @generated
	 */
	public EClass getXmlElementCollection_2_0()
	{
		return xmlElementCollection_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getTargetClass <em>Target Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Class</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getTargetClass()
	 * @see #getXmlElementCollection_2_0()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_2_0_TargetClass()
	{
		return (EAttribute)xmlElementCollection_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getFetch()
	 * @see #getXmlElementCollection_2_0()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_2_0_Fetch()
	{
		return (EAttribute)xmlElementCollection_2_0EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKey <em>Map Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKey()
	 * @see #getXmlElementCollection_2_0()
	 * @generated
	 */
	public EReference getXmlElementCollection_2_0_MapKey()
	{
		return (EReference)xmlElementCollection_2_0EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyClass <em>Map Key Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Class</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyClass()
	 * @see #getXmlElementCollection_2_0()
	 * @generated
	 */
	public EReference getXmlElementCollection_2_0_MapKeyClass()
	{
		return (EReference)xmlElementCollection_2_0EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyTemporal <em>Map Key Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Key Temporal</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyTemporal()
	 * @see #getXmlElementCollection_2_0()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_2_0_MapKeyTemporal()
	{
		return (EAttribute)xmlElementCollection_2_0EClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyEnumerated <em>Map Key Enumerated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Key Enumerated</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyEnumerated()
	 * @see #getXmlElementCollection_2_0()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_2_0_MapKeyEnumerated()
	{
		return (EAttribute)xmlElementCollection_2_0EClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyAttributeOverrides <em>Map Key Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyAttributeOverrides()
	 * @see #getXmlElementCollection_2_0()
	 * @generated
	 */
	public EReference getXmlElementCollection_2_0_MapKeyAttributeOverrides()
	{
		return (EReference)xmlElementCollection_2_0EClass.getEStructuralFeatures().get(6);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyColumn <em>Map Key Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyColumn()
	 * @see #getXmlElementCollection_2_0()
	 * @generated
	 */
	public EReference getXmlElementCollection_2_0_MapKeyColumn()
	{
		return (EReference)xmlElementCollection_2_0EClass.getEStructuralFeatures().get(7);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyJoinColumns <em>Map Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyJoinColumns()
	 * @see #getXmlElementCollection_2_0()
	 * @generated
	 */
	public EReference getXmlElementCollection_2_0_MapKeyJoinColumns()
	{
		return (EReference)xmlElementCollection_2_0EClass.getEStructuralFeatures().get(8);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getColumn()
	 * @see #getXmlElementCollection_2_0()
	 * @generated
	 */
	public EReference getXmlElementCollection_2_0_Column()
	{
		return (EReference)xmlElementCollection_2_0EClass.getEStructuralFeatures().get(9);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getCollectionTable <em>Collection Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Collection Table</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getCollectionTable()
	 * @see #getXmlElementCollection_2_0()
	 * @generated
	 */
	public EReference getXmlElementCollection_2_0_CollectionTable()
	{
		return (EReference)xmlElementCollection_2_0EClass.getEStructuralFeatures().get(10);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEmbedded_2_0 <em>Xml Embedded 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEmbedded_2_0
	 * @generated
	 */
	public EClass getXmlEmbedded_2_0()
	{
		return xmlEmbedded_2_0EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEntity_2_0 <em>Xml Entity 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEntity_2_0
	 * @generated
	 */
	public EClass getXmlEntity_2_0()
	{
		return xmlEntity_2_0EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEntityListener_2_0 <em>Xml Entity Listener 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity Listener 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEntityListener_2_0
	 * @generated
	 */
	public EClass getXmlEntityListener_2_0()
	{
		return xmlEntityListener_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEntityListener_2_0#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEntityListener_2_0#getDescription()
	 * @see #getXmlEntityListener_2_0()
	 * @generated
	 */
	public EAttribute getXmlEntityListener_2_0_Description()
	{
		return (EAttribute)xmlEntityListener_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEventMethod_2_0 <em>Xml Event Method 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Event Method 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEventMethod_2_0
	 * @generated
	 */
	public EClass getXmlEventMethod_2_0()
	{
		return xmlEventMethod_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEventMethod_2_0#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEventMethod_2_0#getDescription()
	 * @see #getXmlEventMethod_2_0()
	 * @generated
	 */
	public EAttribute getXmlEventMethod_2_0_Description()
	{
		return (EAttribute)xmlEventMethod_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlGenerator_2_0 <em>Xml Generator 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Generator 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlGenerator_2_0
	 * @generated
	 */
	public EClass getXmlGenerator_2_0()
	{
		return xmlGenerator_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlGenerator_2_0#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlGenerator_2_0#getDescription()
	 * @see #getXmlGenerator_2_0()
	 * @generated
	 */
	public EAttribute getXmlGenerator_2_0_Description()
	{
		return (EAttribute)xmlGenerator_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0 <em>Xml Many To Many 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0
	 * @generated
	 */
	public EClass getXmlManyToMany_2_0()
	{
		return xmlManyToMany_2_0EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0#getMapKeyClass <em>Map Key Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Class</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0#getMapKeyClass()
	 * @see #getXmlManyToMany_2_0()
	 * @generated
	 */
	public EReference getXmlManyToMany_2_0_MapKeyClass()
	{
		return (EReference)xmlManyToMany_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0#getMapKeyTemporal <em>Map Key Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Key Temporal</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0#getMapKeyTemporal()
	 * @see #getXmlManyToMany_2_0()
	 * @generated
	 */
	public EAttribute getXmlManyToMany_2_0_MapKeyTemporal()
	{
		return (EAttribute)xmlManyToMany_2_0EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0#getMapKeyEnumerated <em>Map Key Enumerated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Key Enumerated</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0#getMapKeyEnumerated()
	 * @see #getXmlManyToMany_2_0()
	 * @generated
	 */
	public EAttribute getXmlManyToMany_2_0_MapKeyEnumerated()
	{
		return (EAttribute)xmlManyToMany_2_0EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0#getMapKeyAttributeOverrides <em>Map Key Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0#getMapKeyAttributeOverrides()
	 * @see #getXmlManyToMany_2_0()
	 * @generated
	 */
	public EReference getXmlManyToMany_2_0_MapKeyAttributeOverrides()
	{
		return (EReference)xmlManyToMany_2_0EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0#getMapKeyColumn <em>Map Key Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0#getMapKeyColumn()
	 * @see #getXmlManyToMany_2_0()
	 * @generated
	 */
	public EReference getXmlManyToMany_2_0_MapKeyColumn()
	{
		return (EReference)xmlManyToMany_2_0EClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0#getMapKeyJoinColumns <em>Map Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0#getMapKeyJoinColumns()
	 * @see #getXmlManyToMany_2_0()
	 * @generated
	 */
	public EReference getXmlManyToMany_2_0_MapKeyJoinColumns()
	{
		return (EReference)xmlManyToMany_2_0EClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlMapKeyClass_2_0 <em>Xml Map Key Class 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Map Key Class 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlMapKeyClass_2_0
	 * @generated
	 */
	public EClass getXmlMapKeyClass_2_0()
	{
		return xmlMapKeyClass_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlMapKeyClass_2_0#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlMapKeyClass_2_0#getClassName()
	 * @see #getXmlMapKeyClass_2_0()
	 * @generated
	 */
	public EAttribute getXmlMapKeyClass_2_0_ClassName()
	{
		return (EAttribute)xmlMapKeyClass_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0 <em>Xml Maps Id 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Maps Id 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0
	 * @generated
	 */
	public EClass getXmlMapsId_2_0()
	{
		return xmlMapsId_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0#getMapsId <em>Maps Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Maps Id</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0#getMapsId()
	 * @see #getXmlMapsId_2_0()
	 * @generated
	 */
	public EAttribute getXmlMapsId_2_0_MapsId()
	{
		return (EAttribute)xmlMapsId_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlNamedQuery_2_0 <em>Xml Named Query 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Query 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlNamedQuery_2_0
	 * @generated
	 */
	public EClass getXmlNamedQuery_2_0()
	{
		return xmlNamedQuery_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlNamedQuery_2_0#getLockMode <em>Lock Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lock Mode</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlNamedQuery_2_0#getLockMode()
	 * @see #getXmlNamedQuery_2_0()
	 * @generated
	 */
	public EAttribute getXmlNamedQuery_2_0_LockMode()
	{
		return (EAttribute)xmlNamedQuery_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0 <em>Xml One To Many 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0
	 * @generated
	 */
	public EClass getXmlOneToMany_2_0()
	{
		return xmlOneToMany_2_0EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0#getMapKeyClass <em>Map Key Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Class</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0#getMapKeyClass()
	 * @see #getXmlOneToMany_2_0()
	 * @generated
	 */
	public EReference getXmlOneToMany_2_0_MapKeyClass()
	{
		return (EReference)xmlOneToMany_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0#getMapKeyTemporal <em>Map Key Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Key Temporal</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0#getMapKeyTemporal()
	 * @see #getXmlOneToMany_2_0()
	 * @generated
	 */
	public EAttribute getXmlOneToMany_2_0_MapKeyTemporal()
	{
		return (EAttribute)xmlOneToMany_2_0EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0#getMapKeyEnumerated <em>Map Key Enumerated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Key Enumerated</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0#getMapKeyEnumerated()
	 * @see #getXmlOneToMany_2_0()
	 * @generated
	 */
	public EAttribute getXmlOneToMany_2_0_MapKeyEnumerated()
	{
		return (EAttribute)xmlOneToMany_2_0EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0#getMapKeyAttributeOverrides <em>Map Key Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0#getMapKeyAttributeOverrides()
	 * @see #getXmlOneToMany_2_0()
	 * @generated
	 */
	public EReference getXmlOneToMany_2_0_MapKeyAttributeOverrides()
	{
		return (EReference)xmlOneToMany_2_0EClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0#getMapKeyColumn <em>Map Key Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0#getMapKeyColumn()
	 * @see #getXmlOneToMany_2_0()
	 * @generated
	 */
	public EReference getXmlOneToMany_2_0_MapKeyColumn()
	{
		return (EReference)xmlOneToMany_2_0EClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0#getMapKeyJoinColumns <em>Map Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0#getMapKeyJoinColumns()
	 * @see #getXmlOneToMany_2_0()
	 * @generated
	 */
	public EReference getXmlOneToMany_2_0_MapKeyJoinColumns()
	{
		return (EReference)xmlOneToMany_2_0EClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToOne_2_0 <em>Xml One To One 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToOne_2_0
	 * @generated
	 */
	public EClass getXmlOneToOne_2_0()
	{
		return xmlOneToOne_2_0EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderColumn_2_0 <em>Xml Order Column 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Order Column 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderColumn_2_0
	 * @generated
	 */
	public EClass getXmlOrderColumn_2_0()
	{
		return xmlOrderColumn_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderColumn_2_0#getNullable <em>Nullable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nullable</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderColumn_2_0#getNullable()
	 * @see #getXmlOrderColumn_2_0()
	 * @generated
	 */
	public EAttribute getXmlOrderColumn_2_0_Nullable()
	{
		return (EAttribute)xmlOrderColumn_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderColumn_2_0#getInsertable <em>Insertable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Insertable</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderColumn_2_0#getInsertable()
	 * @see #getXmlOrderColumn_2_0()
	 * @generated
	 */
	public EAttribute getXmlOrderColumn_2_0_Insertable()
	{
		return (EAttribute)xmlOrderColumn_2_0EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderColumn_2_0#getUpdatable <em>Updatable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Updatable</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderColumn_2_0#getUpdatable()
	 * @see #getXmlOrderColumn_2_0()
	 * @generated
	 */
	public EAttribute getXmlOrderColumn_2_0_Updatable()
	{
		return (EAttribute)xmlOrderColumn_2_0EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitDefaults_2_0 <em>Xml Persistence Unit Defaults 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence Unit Defaults 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitDefaults_2_0
	 * @generated
	 */
	public EClass getXmlPersistenceUnitDefaults_2_0()
	{
		return xmlPersistenceUnitDefaults_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitDefaults_2_0#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitDefaults_2_0#getDescription()
	 * @see #getXmlPersistenceUnitDefaults_2_0()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnitDefaults_2_0_Description()
	{
		return (EAttribute)xmlPersistenceUnitDefaults_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitDefaults_2_0#isDelimitedIdentifiers <em>Delimited Identifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delimited Identifiers</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitDefaults_2_0#isDelimitedIdentifiers()
	 * @see #getXmlPersistenceUnitDefaults_2_0()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnitDefaults_2_0_DelimitedIdentifiers()
	{
		return (EAttribute)xmlPersistenceUnitDefaults_2_0EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitMetadata_2_0 <em>Xml Persistence Unit Metadata 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence Unit Metadata 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitMetadata_2_0
	 * @generated
	 */
	public EClass getXmlPersistenceUnitMetadata_2_0()
	{
		return xmlPersistenceUnitMetadata_2_0EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitMetadata_2_0#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitMetadata_2_0#getDescription()
	 * @see #getXmlPersistenceUnitMetadata_2_0()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnitMetadata_2_0_Description()
	{
		return (EAttribute)xmlPersistenceUnitMetadata_2_0EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlQuery_2_0 <em>Xml Query 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Query 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlQuery_2_0
	 * @generated
	 */
	public EClass getXmlQuery_2_0()
	{
		return xmlQuery_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlQuery_2_0#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlQuery_2_0#getDescription()
	 * @see #getXmlQuery_2_0()
	 * @generated
	 */
	public EAttribute getXmlQuery_2_0_Description()
	{
		return (EAttribute)xmlQuery_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlQueryHint_2_0 <em>Xml Query Hint 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Query Hint 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlQueryHint_2_0
	 * @generated
	 */
	public EClass getXmlQueryHint_2_0()
	{
		return xmlQueryHint_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlQueryHint_2_0#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlQueryHint_2_0#getDescription()
	 * @see #getXmlQueryHint_2_0()
	 * @generated
	 */
	public EAttribute getXmlQueryHint_2_0_Description()
	{
		return (EAttribute)xmlQueryHint_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlSequenceGenerator_2_0 <em>Xml Sequence Generator 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Sequence Generator 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlSequenceGenerator_2_0
	 * @generated
	 */
	public EClass getXmlSequenceGenerator_2_0()
	{
		return xmlSequenceGenerator_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlSequenceGenerator_2_0#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlSequenceGenerator_2_0#getCatalog()
	 * @see #getXmlSequenceGenerator_2_0()
	 * @generated
	 */
	public EAttribute getXmlSequenceGenerator_2_0_Catalog()
	{
		return (EAttribute)xmlSequenceGenerator_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlSequenceGenerator_2_0#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlSequenceGenerator_2_0#getSchema()
	 * @see #getXmlSequenceGenerator_2_0()
	 * @generated
	 */
	public EAttribute getXmlSequenceGenerator_2_0_Schema()
	{
		return (EAttribute)xmlSequenceGenerator_2_0EClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0 <em>Xml Single Relationship Mapping 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Single Relationship Mapping 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0
	 * @generated
	 */
	public EClass getXmlSingleRelationshipMapping_2_0()
	{
		return xmlSingleRelationshipMapping_2_0EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlSqlResultSetMapping_2_0 <em>Xml Sql Result Set Mapping 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Sql Result Set Mapping 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlSqlResultSetMapping_2_0
	 * @generated
	 */
	public EClass getXmlSqlResultSetMapping_2_0()
	{
		return xmlSqlResultSetMapping_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlSqlResultSetMapping_2_0#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlSqlResultSetMapping_2_0#getDescription()
	 * @see #getXmlSqlResultSetMapping_2_0()
	 * @generated
	 */
	public EAttribute getXmlSqlResultSetMapping_2_0_Description()
	{
		return (EAttribute)xmlSqlResultSetMapping_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlUniqueConstraint_2_0 <em>Xml Unique Constraint 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Unique Constraint 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlUniqueConstraint_2_0
	 * @generated
	 */
	public EClass getXmlUniqueConstraint_2_0()
	{
		return xmlUniqueConstraint_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlUniqueConstraint_2_0#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlUniqueConstraint_2_0#getName()
	 * @see #getXmlUniqueConstraint_2_0()
	 * @generated
	 */
	public EAttribute getXmlUniqueConstraint_2_0_Name()
	{
		return (EAttribute)xmlUniqueConstraint_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0 <em>Xml Cacheable 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Cacheable 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0
	 * @generated
	 */
	public EClass getXmlCacheable_2_0()
	{
		return xmlCacheable_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0#getCacheable <em>Cacheable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cacheable</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0#getCacheable()
	 * @see #getXmlCacheable_2_0()
	 * @generated
	 */
	public EAttribute getXmlCacheable_2_0_Cacheable()
	{
		return (EAttribute)xmlCacheable_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0 <em>Xml Orphan Removable 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Orphan Removable 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0
	 * @generated
	 */
	public EClass getXmlOrphanRemovable_2_0()
	{
		return xmlOrphanRemovable_2_0EClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0#getOrphanRemoval <em>Orphan Removal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Orphan Removal</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0#getOrphanRemoval()
	 * @see #getXmlOrphanRemovable_2_0()
	 * @generated
	 */
	public EAttribute getXmlOrphanRemovable_2_0_OrphanRemoval()
	{
		return (EAttribute)xmlOrphanRemovable_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderable_2_0 <em>Xml Orderable 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Orderable 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderable_2_0
	 * @generated
	 */
	public EClass getXmlOrderable_2_0()
	{
		return xmlOrderable_2_0EClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderable_2_0#getOrderColumn <em>Order Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Order Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderable_2_0#getOrderColumn()
	 * @see #getXmlOrderable_2_0()
	 * @generated
	 */
	public EReference getXmlOrderable_2_0_OrderColumn()
	{
		return (EReference)xmlOrderable_2_0EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.resource.orm.v2_0.LockModeType_2_0 <em>Lock Mode Type 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Lock Mode Type 20</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.LockModeType_2_0
	 * @generated
	 */
	public EEnum getLockModeType_2_0()
	{
		return lockModeType_2_0EEnum;
	}


	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public OrmV2_0Factory getOrmV2_0Factory()
	{
		return (OrmV2_0Factory)getEFactoryInstance();
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
		xmlAttributeMapping_2_0EClass = createEClass(XML_ATTRIBUTE_MAPPING_20);

		xmlAssociationOverride_2_0EClass = createEClass(XML_ASSOCIATION_OVERRIDE_20);
		createEAttribute(xmlAssociationOverride_2_0EClass, XML_ASSOCIATION_OVERRIDE_20__DESCRIPTION);
		createEReference(xmlAssociationOverride_2_0EClass, XML_ASSOCIATION_OVERRIDE_20__JOIN_TABLE);

		xmlAttributeOverride_2_0EClass = createEClass(XML_ATTRIBUTE_OVERRIDE_20);
		createEAttribute(xmlAttributeOverride_2_0EClass, XML_ATTRIBUTE_OVERRIDE_20__DESCRIPTION);

		xmlAttributes_2_0EClass = createEClass(XML_ATTRIBUTES_20);
		createEAttribute(xmlAttributes_2_0EClass, XML_ATTRIBUTES_20__DESCRIPTION);
		createEReference(xmlAttributes_2_0EClass, XML_ATTRIBUTES_20__ELEMENT_COLLECTIONS);

		xmlCollectionTable_2_0EClass = createEClass(XML_COLLECTION_TABLE_20);

		xmlDerivedId_2_0EClass = createEClass(XML_DERIVED_ID_20);
		createEAttribute(xmlDerivedId_2_0EClass, XML_DERIVED_ID_20__ID);

		xmlElementCollection_2_0EClass = createEClass(XML_ELEMENT_COLLECTION_20);
		createEAttribute(xmlElementCollection_2_0EClass, XML_ELEMENT_COLLECTION_20__TARGET_CLASS);
		createEAttribute(xmlElementCollection_2_0EClass, XML_ELEMENT_COLLECTION_20__FETCH);
		createEReference(xmlElementCollection_2_0EClass, XML_ELEMENT_COLLECTION_20__MAP_KEY);
		createEReference(xmlElementCollection_2_0EClass, XML_ELEMENT_COLLECTION_20__MAP_KEY_CLASS);
		createEAttribute(xmlElementCollection_2_0EClass, XML_ELEMENT_COLLECTION_20__MAP_KEY_TEMPORAL);
		createEAttribute(xmlElementCollection_2_0EClass, XML_ELEMENT_COLLECTION_20__MAP_KEY_ENUMERATED);
		createEReference(xmlElementCollection_2_0EClass, XML_ELEMENT_COLLECTION_20__MAP_KEY_ATTRIBUTE_OVERRIDES);
		createEReference(xmlElementCollection_2_0EClass, XML_ELEMENT_COLLECTION_20__MAP_KEY_COLUMN);
		createEReference(xmlElementCollection_2_0EClass, XML_ELEMENT_COLLECTION_20__MAP_KEY_JOIN_COLUMNS);
		createEReference(xmlElementCollection_2_0EClass, XML_ELEMENT_COLLECTION_20__COLUMN);
		createEReference(xmlElementCollection_2_0EClass, XML_ELEMENT_COLLECTION_20__COLLECTION_TABLE);

		xmlEmbedded_2_0EClass = createEClass(XML_EMBEDDED_20);

		xmlEntity_2_0EClass = createEClass(XML_ENTITY_20);

		xmlEntityListener_2_0EClass = createEClass(XML_ENTITY_LISTENER_20);
		createEAttribute(xmlEntityListener_2_0EClass, XML_ENTITY_LISTENER_20__DESCRIPTION);

		xmlEventMethod_2_0EClass = createEClass(XML_EVENT_METHOD_20);
		createEAttribute(xmlEventMethod_2_0EClass, XML_EVENT_METHOD_20__DESCRIPTION);

		xmlGenerator_2_0EClass = createEClass(XML_GENERATOR_20);
		createEAttribute(xmlGenerator_2_0EClass, XML_GENERATOR_20__DESCRIPTION);

		xmlManyToMany_2_0EClass = createEClass(XML_MANY_TO_MANY_20);
		createEReference(xmlManyToMany_2_0EClass, XML_MANY_TO_MANY_20__MAP_KEY_CLASS);
		createEAttribute(xmlManyToMany_2_0EClass, XML_MANY_TO_MANY_20__MAP_KEY_TEMPORAL);
		createEAttribute(xmlManyToMany_2_0EClass, XML_MANY_TO_MANY_20__MAP_KEY_ENUMERATED);
		createEReference(xmlManyToMany_2_0EClass, XML_MANY_TO_MANY_20__MAP_KEY_ATTRIBUTE_OVERRIDES);
		createEReference(xmlManyToMany_2_0EClass, XML_MANY_TO_MANY_20__MAP_KEY_COLUMN);
		createEReference(xmlManyToMany_2_0EClass, XML_MANY_TO_MANY_20__MAP_KEY_JOIN_COLUMNS);

		xmlMapKeyClass_2_0EClass = createEClass(XML_MAP_KEY_CLASS_20);
		createEAttribute(xmlMapKeyClass_2_0EClass, XML_MAP_KEY_CLASS_20__CLASS_NAME);

		xmlMapsId_2_0EClass = createEClass(XML_MAPS_ID_20);
		createEAttribute(xmlMapsId_2_0EClass, XML_MAPS_ID_20__MAPS_ID);

		xmlNamedQuery_2_0EClass = createEClass(XML_NAMED_QUERY_20);
		createEAttribute(xmlNamedQuery_2_0EClass, XML_NAMED_QUERY_20__LOCK_MODE);

		xmlOneToMany_2_0EClass = createEClass(XML_ONE_TO_MANY_20);
		createEReference(xmlOneToMany_2_0EClass, XML_ONE_TO_MANY_20__MAP_KEY_CLASS);
		createEAttribute(xmlOneToMany_2_0EClass, XML_ONE_TO_MANY_20__MAP_KEY_TEMPORAL);
		createEAttribute(xmlOneToMany_2_0EClass, XML_ONE_TO_MANY_20__MAP_KEY_ENUMERATED);
		createEReference(xmlOneToMany_2_0EClass, XML_ONE_TO_MANY_20__MAP_KEY_ATTRIBUTE_OVERRIDES);
		createEReference(xmlOneToMany_2_0EClass, XML_ONE_TO_MANY_20__MAP_KEY_COLUMN);
		createEReference(xmlOneToMany_2_0EClass, XML_ONE_TO_MANY_20__MAP_KEY_JOIN_COLUMNS);

		xmlOneToOne_2_0EClass = createEClass(XML_ONE_TO_ONE_20);

		xmlOrderColumn_2_0EClass = createEClass(XML_ORDER_COLUMN_20);
		createEAttribute(xmlOrderColumn_2_0EClass, XML_ORDER_COLUMN_20__NULLABLE);
		createEAttribute(xmlOrderColumn_2_0EClass, XML_ORDER_COLUMN_20__INSERTABLE);
		createEAttribute(xmlOrderColumn_2_0EClass, XML_ORDER_COLUMN_20__UPDATABLE);

		xmlPersistenceUnitDefaults_2_0EClass = createEClass(XML_PERSISTENCE_UNIT_DEFAULTS_20);
		createEAttribute(xmlPersistenceUnitDefaults_2_0EClass, XML_PERSISTENCE_UNIT_DEFAULTS_20__DESCRIPTION);
		createEAttribute(xmlPersistenceUnitDefaults_2_0EClass, XML_PERSISTENCE_UNIT_DEFAULTS_20__DELIMITED_IDENTIFIERS);

		xmlPersistenceUnitMetadata_2_0EClass = createEClass(XML_PERSISTENCE_UNIT_METADATA_20);
		createEAttribute(xmlPersistenceUnitMetadata_2_0EClass, XML_PERSISTENCE_UNIT_METADATA_20__DESCRIPTION);

		xmlQuery_2_0EClass = createEClass(XML_QUERY_20);
		createEAttribute(xmlQuery_2_0EClass, XML_QUERY_20__DESCRIPTION);

		xmlQueryHint_2_0EClass = createEClass(XML_QUERY_HINT_20);
		createEAttribute(xmlQueryHint_2_0EClass, XML_QUERY_HINT_20__DESCRIPTION);

		xmlSequenceGenerator_2_0EClass = createEClass(XML_SEQUENCE_GENERATOR_20);
		createEAttribute(xmlSequenceGenerator_2_0EClass, XML_SEQUENCE_GENERATOR_20__CATALOG);
		createEAttribute(xmlSequenceGenerator_2_0EClass, XML_SEQUENCE_GENERATOR_20__SCHEMA);

		xmlSingleRelationshipMapping_2_0EClass = createEClass(XML_SINGLE_RELATIONSHIP_MAPPING_20);

		xmlSqlResultSetMapping_2_0EClass = createEClass(XML_SQL_RESULT_SET_MAPPING_20);
		createEAttribute(xmlSqlResultSetMapping_2_0EClass, XML_SQL_RESULT_SET_MAPPING_20__DESCRIPTION);

		xmlUniqueConstraint_2_0EClass = createEClass(XML_UNIQUE_CONSTRAINT_20);
		createEAttribute(xmlUniqueConstraint_2_0EClass, XML_UNIQUE_CONSTRAINT_20__NAME);

		xmlCacheable_2_0EClass = createEClass(XML_CACHEABLE_20);
		createEAttribute(xmlCacheable_2_0EClass, XML_CACHEABLE_20__CACHEABLE);

		xmlOrphanRemovable_2_0EClass = createEClass(XML_ORPHAN_REMOVABLE_20);
		createEAttribute(xmlOrphanRemovable_2_0EClass, XML_ORPHAN_REMOVABLE_20__ORPHAN_REMOVAL);

		xmlOrderable_2_0EClass = createEClass(XML_ORDERABLE_20);
		createEReference(xmlOrderable_2_0EClass, XML_ORDERABLE_20__ORDER_COLUMN);

		// Create enums
		lockModeType_2_0EEnum = createEEnum(LOCK_MODE_TYPE_20);
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
		xmlAttributeMapping_2_0EClass.getESuperTypes().add(theOrmPackage.getXmlAccessHolder());
		xmlCollectionTable_2_0EClass.getESuperTypes().add(theOrmPackage.getXmlReferenceTable());
		xmlElementCollection_2_0EClass.getESuperTypes().add(theOrmPackage.getXmlConvertibleMapping());
		xmlElementCollection_2_0EClass.getESuperTypes().add(theOrmPackage.getXmlOrderable());
		xmlElementCollection_2_0EClass.getESuperTypes().add(theOrmPackage.getXmlAttributeOverrideContainer());
		xmlElementCollection_2_0EClass.getESuperTypes().add(theOrmPackage.getXmlAssociationOverrideContainer());
		xmlEmbedded_2_0EClass.getESuperTypes().add(theOrmPackage.getXmlAssociationOverrideContainer());
		xmlEntity_2_0EClass.getESuperTypes().add(this.getXmlCacheable_2_0());
		xmlManyToMany_2_0EClass.getESuperTypes().add(this.getXmlOrderable_2_0());
		xmlOneToMany_2_0EClass.getESuperTypes().add(this.getXmlOrphanRemovable_2_0());
		xmlOneToMany_2_0EClass.getESuperTypes().add(this.getXmlOrderable_2_0());
		xmlOneToOne_2_0EClass.getESuperTypes().add(this.getXmlSingleRelationshipMapping_2_0());
		xmlOneToOne_2_0EClass.getESuperTypes().add(this.getXmlOrphanRemovable_2_0());
		xmlSingleRelationshipMapping_2_0EClass.getESuperTypes().add(this.getXmlDerivedId_2_0());
		xmlSingleRelationshipMapping_2_0EClass.getESuperTypes().add(this.getXmlMapsId_2_0());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlAttributeMapping_2_0EClass, XmlAttributeMapping_2_0.class, "XmlAttributeMapping_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlAssociationOverride_2_0EClass, XmlAssociationOverride_2_0.class, "XmlAssociationOverride_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlAssociationOverride_2_0_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlAssociationOverride_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlAssociationOverride_2_0_JoinTable(), theOrmPackage.getXmlJoinTable(), null, "joinTable", null, 0, 1, XmlAssociationOverride_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlAttributeOverride_2_0EClass, XmlAttributeOverride_2_0.class, "XmlAttributeOverride_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlAttributeOverride_2_0_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlAttributeOverride_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlAttributes_2_0EClass, XmlAttributes_2_0.class, "XmlAttributes_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlAttributes_2_0_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlAttributes_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlAttributes_2_0_ElementCollections(), theOrmPackage.getXmlElementCollection(), null, "elementCollections", null, 0, -1, XmlAttributes_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCollectionTable_2_0EClass, XmlCollectionTable_2_0.class, "XmlCollectionTable_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlDerivedId_2_0EClass, XmlDerivedId_2_0.class, "XmlDerivedId_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlDerivedId_2_0_Id(), theXMLTypePackage.getBooleanObject(), "id", null, 0, 1, XmlDerivedId_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlElementCollection_2_0EClass, XmlElementCollection_2_0.class, "XmlElementCollection_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlElementCollection_2_0_TargetClass(), theXMLTypePackage.getString(), "targetClass", null, 0, 1, XmlElementCollection_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlElementCollection_2_0_Fetch(), theOrmPackage.getFetchType(), "fetch", null, 0, 1, XmlElementCollection_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_2_0_MapKey(), theOrmPackage.getMapKey(), null, "mapKey", null, 0, 1, XmlElementCollection_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_2_0_MapKeyClass(), theOrmPackage.getXmlMapKeyClass(), null, "mapKeyClass", null, 0, 1, XmlElementCollection_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlElementCollection_2_0_MapKeyTemporal(), theOrmPackage.getTemporalType(), "mapKeyTemporal", null, 0, 1, XmlElementCollection_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlElementCollection_2_0_MapKeyEnumerated(), theOrmPackage.getEnumType(), "mapKeyEnumerated", null, 0, 1, XmlElementCollection_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_2_0_MapKeyAttributeOverrides(), theOrmPackage.getXmlAttributeOverride(), null, "mapKeyAttributeOverrides", null, 0, -1, XmlElementCollection_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_2_0_MapKeyColumn(), theOrmPackage.getXmlColumn(), null, "mapKeyColumn", null, 0, 1, XmlElementCollection_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_2_0_MapKeyJoinColumns(), theOrmPackage.getXmlJoinColumn(), null, "mapKeyJoinColumns", null, 0, -1, XmlElementCollection_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_2_0_Column(), theOrmPackage.getXmlColumn(), null, "column", null, 0, 1, XmlElementCollection_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlElementCollection_2_0_CollectionTable(), theOrmPackage.getXmlCollectionTable(), null, "collectionTable", null, 0, 1, XmlElementCollection_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEmbedded_2_0EClass, XmlEmbedded_2_0.class, "XmlEmbedded_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEntity_2_0EClass, XmlEntity_2_0.class, "XmlEntity_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEntityListener_2_0EClass, XmlEntityListener_2_0.class, "XmlEntityListener_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlEntityListener_2_0_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlEntityListener_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEventMethod_2_0EClass, XmlEventMethod_2_0.class, "XmlEventMethod_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlEventMethod_2_0_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlEventMethod_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlGenerator_2_0EClass, XmlGenerator_2_0.class, "XmlGenerator_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlGenerator_2_0_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlGenerator_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlManyToMany_2_0EClass, XmlManyToMany_2_0.class, "XmlManyToMany_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlManyToMany_2_0_MapKeyClass(), theOrmPackage.getXmlMapKeyClass(), null, "mapKeyClass", null, 0, 1, XmlManyToMany_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlManyToMany_2_0_MapKeyTemporal(), theOrmPackage.getTemporalType(), "mapKeyTemporal", null, 0, 1, XmlManyToMany_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlManyToMany_2_0_MapKeyEnumerated(), theOrmPackage.getEnumType(), "mapKeyEnumerated", null, 0, 1, XmlManyToMany_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlManyToMany_2_0_MapKeyAttributeOverrides(), theOrmPackage.getXmlAttributeOverride(), null, "mapKeyAttributeOverrides", null, 0, -1, XmlManyToMany_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlManyToMany_2_0_MapKeyColumn(), theOrmPackage.getXmlColumn(), null, "mapKeyColumn", null, 0, 1, XmlManyToMany_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlManyToMany_2_0_MapKeyJoinColumns(), theOrmPackage.getXmlJoinColumn(), null, "mapKeyJoinColumns", null, 0, -1, XmlManyToMany_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMapKeyClass_2_0EClass, XmlMapKeyClass_2_0.class, "XmlMapKeyClass_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlMapKeyClass_2_0_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, XmlMapKeyClass_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMapsId_2_0EClass, XmlMapsId_2_0.class, "XmlMapsId_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlMapsId_2_0_MapsId(), theXMLTypePackage.getString(), "mapsId", null, 0, 1, XmlMapsId_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNamedQuery_2_0EClass, XmlNamedQuery_2_0.class, "XmlNamedQuery_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlNamedQuery_2_0_LockMode(), this.getLockModeType_2_0(), "lockMode", null, 0, 1, XmlNamedQuery_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToMany_2_0EClass, XmlOneToMany_2_0.class, "XmlOneToMany_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlOneToMany_2_0_MapKeyClass(), theOrmPackage.getXmlMapKeyClass(), null, "mapKeyClass", null, 0, 1, XmlOneToMany_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOneToMany_2_0_MapKeyTemporal(), theOrmPackage.getTemporalType(), "mapKeyTemporal", null, 0, 1, XmlOneToMany_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOneToMany_2_0_MapKeyEnumerated(), theOrmPackage.getEnumType(), "mapKeyEnumerated", null, 0, 1, XmlOneToMany_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlOneToMany_2_0_MapKeyAttributeOverrides(), theOrmPackage.getXmlAttributeOverride(), null, "mapKeyAttributeOverrides", null, 0, -1, XmlOneToMany_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlOneToMany_2_0_MapKeyColumn(), theOrmPackage.getXmlColumn(), null, "mapKeyColumn", null, 0, 1, XmlOneToMany_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlOneToMany_2_0_MapKeyJoinColumns(), theOrmPackage.getXmlJoinColumn(), null, "mapKeyJoinColumns", null, 0, -1, XmlOneToMany_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToOne_2_0EClass, XmlOneToOne_2_0.class, "XmlOneToOne_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlOrderColumn_2_0EClass, XmlOrderColumn_2_0.class, "XmlOrderColumn_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlOrderColumn_2_0_Nullable(), theXMLTypePackage.getBooleanObject(), "nullable", null, 0, 1, XmlOrderColumn_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOrderColumn_2_0_Insertable(), theXMLTypePackage.getBooleanObject(), "insertable", null, 0, 1, XmlOrderColumn_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOrderColumn_2_0_Updatable(), theXMLTypePackage.getBooleanObject(), "updatable", null, 0, 1, XmlOrderColumn_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPersistenceUnitDefaults_2_0EClass, XmlPersistenceUnitDefaults_2_0.class, "XmlPersistenceUnitDefaults_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPersistenceUnitDefaults_2_0_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlPersistenceUnitDefaults_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistenceUnitDefaults_2_0_DelimitedIdentifiers(), theXMLTypePackage.getBoolean(), "delimitedIdentifiers", null, 0, 1, XmlPersistenceUnitDefaults_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPersistenceUnitMetadata_2_0EClass, XmlPersistenceUnitMetadata_2_0.class, "XmlPersistenceUnitMetadata_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPersistenceUnitMetadata_2_0_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlPersistenceUnitMetadata_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlQuery_2_0EClass, XmlQuery_2_0.class, "XmlQuery_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlQuery_2_0_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlQuery_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlQueryHint_2_0EClass, XmlQueryHint_2_0.class, "XmlQueryHint_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlQueryHint_2_0_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlQueryHint_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlSequenceGenerator_2_0EClass, XmlSequenceGenerator_2_0.class, "XmlSequenceGenerator_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlSequenceGenerator_2_0_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, XmlSequenceGenerator_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlSequenceGenerator_2_0_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, XmlSequenceGenerator_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlSingleRelationshipMapping_2_0EClass, XmlSingleRelationshipMapping_2_0.class, "XmlSingleRelationshipMapping_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlSqlResultSetMapping_2_0EClass, XmlSqlResultSetMapping_2_0.class, "XmlSqlResultSetMapping_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlSqlResultSetMapping_2_0_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlSqlResultSetMapping_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlUniqueConstraint_2_0EClass, XmlUniqueConstraint_2_0.class, "XmlUniqueConstraint_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlUniqueConstraint_2_0_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlUniqueConstraint_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCacheable_2_0EClass, XmlCacheable_2_0.class, "XmlCacheable_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlCacheable_2_0_Cacheable(), theXMLTypePackage.getBooleanObject(), "cacheable", null, 0, 1, XmlCacheable_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOrphanRemovable_2_0EClass, XmlOrphanRemovable_2_0.class, "XmlOrphanRemovable_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlOrphanRemovable_2_0_OrphanRemoval(), theXMLTypePackage.getBooleanObject(), "orphanRemoval", null, 0, 1, XmlOrphanRemovable_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOrderable_2_0EClass, XmlOrderable_2_0.class, "XmlOrderable_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlOrderable_2_0_OrderColumn(), theOrmPackage.getXmlOrderColumn(), null, "orderColumn", null, 0, 1, XmlOrderable_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(lockModeType_2_0EEnum, LockModeType_2_0.class, "LockModeType_2_0");
		addEEnumLiteral(lockModeType_2_0EEnum, LockModeType_2_0.NONE);
		addEEnumLiteral(lockModeType_2_0EEnum, LockModeType_2_0.READ);
		addEEnumLiteral(lockModeType_2_0EEnum, LockModeType_2_0.WRITE);
		addEEnumLiteral(lockModeType_2_0EEnum, LockModeType_2_0.OPTIMISTIC);
		addEEnumLiteral(lockModeType_2_0EEnum, LockModeType_2_0.OPTIMISTIC_FORCE_INCREMENT);
		addEEnumLiteral(lockModeType_2_0EEnum, LockModeType_2_0.PESSIMISTIC_READ);
		addEEnumLiteral(lockModeType_2_0EEnum, LockModeType_2_0.PESSIMISTIC_WRITE);
		addEEnumLiteral(lockModeType_2_0EEnum, LockModeType_2_0.PESSIMISTIC_FORCE_INCREMENT);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeMapping_2_0 <em>Xml Attribute Mapping 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeMapping_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlAttributeMapping_2_0()
		 * @generated
		 */
		public static final EClass XML_ATTRIBUTE_MAPPING_20 = eINSTANCE.getXmlAttributeMapping_2_0();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAssociationOverride_2_0 <em>Xml Association Override 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAssociationOverride_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlAssociationOverride_2_0()
		 * @generated
		 */
		public static final EClass XML_ASSOCIATION_OVERRIDE_20 = eINSTANCE.getXmlAssociationOverride_2_0();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ASSOCIATION_OVERRIDE_20__DESCRIPTION = eINSTANCE.getXmlAssociationOverride_2_0_Description();

		/**
		 * The meta object literal for the '<em><b>Join Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ASSOCIATION_OVERRIDE_20__JOIN_TABLE = eINSTANCE.getXmlAssociationOverride_2_0_JoinTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeOverride_2_0 <em>Xml Attribute Override 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributeOverride_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlAttributeOverride_2_0()
		 * @generated
		 */
		public static final EClass XML_ATTRIBUTE_OVERRIDE_20 = eINSTANCE.getXmlAttributeOverride_2_0();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ATTRIBUTE_OVERRIDE_20__DESCRIPTION = eINSTANCE.getXmlAttributeOverride_2_0_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributes_2_0 <em>Xml Attributes 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlAttributes_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlAttributes_2_0()
		 * @generated
		 */
		public static final EClass XML_ATTRIBUTES_20 = eINSTANCE.getXmlAttributes_2_0();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ATTRIBUTES_20__DESCRIPTION = eINSTANCE.getXmlAttributes_2_0_Description();

		/**
		 * The meta object literal for the '<em><b>Element Collections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ATTRIBUTES_20__ELEMENT_COLLECTIONS = eINSTANCE.getXmlAttributes_2_0_ElementCollections();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlCollectionTable_2_0 <em>Xml Collection Table 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlCollectionTable_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlCollectionTable_2_0()
		 * @generated
		 */
		public static final EClass XML_COLLECTION_TABLE_20 = eINSTANCE.getXmlCollectionTable_2_0();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0 <em>Xml Derived Id 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlDerivedId_2_0()
		 * @generated
		 */
		public static final EClass XML_DERIVED_ID_20 = eINSTANCE.getXmlDerivedId_2_0();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_DERIVED_ID_20__ID = eINSTANCE.getXmlDerivedId_2_0_Id();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0 <em>Xml Element Collection 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0()
		 * @generated
		 */
		public static final EClass XML_ELEMENT_COLLECTION_20 = eINSTANCE.getXmlElementCollection_2_0();

		/**
		 * The meta object literal for the '<em><b>Target Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION_20__TARGET_CLASS = eINSTANCE.getXmlElementCollection_2_0_TargetClass();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION_20__FETCH = eINSTANCE.getXmlElementCollection_2_0_Fetch();

		/**
		 * The meta object literal for the '<em><b>Map Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION_20__MAP_KEY = eINSTANCE.getXmlElementCollection_2_0_MapKey();

		/**
		 * The meta object literal for the '<em><b>Map Key Class</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION_20__MAP_KEY_CLASS = eINSTANCE.getXmlElementCollection_2_0_MapKeyClass();

		/**
		 * The meta object literal for the '<em><b>Map Key Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION_20__MAP_KEY_TEMPORAL = eINSTANCE.getXmlElementCollection_2_0_MapKeyTemporal();

		/**
		 * The meta object literal for the '<em><b>Map Key Enumerated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION_20__MAP_KEY_ENUMERATED = eINSTANCE.getXmlElementCollection_2_0_MapKeyEnumerated();

		/**
		 * The meta object literal for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION_20__MAP_KEY_ATTRIBUTE_OVERRIDES = eINSTANCE.getXmlElementCollection_2_0_MapKeyAttributeOverrides();

		/**
		 * The meta object literal for the '<em><b>Map Key Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION_20__MAP_KEY_COLUMN = eINSTANCE.getXmlElementCollection_2_0_MapKeyColumn();

		/**
		 * The meta object literal for the '<em><b>Map Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION_20__MAP_KEY_JOIN_COLUMNS = eINSTANCE.getXmlElementCollection_2_0_MapKeyJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION_20__COLUMN = eINSTANCE.getXmlElementCollection_2_0_Column();

		/**
		 * The meta object literal for the '<em><b>Collection Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ELEMENT_COLLECTION_20__COLLECTION_TABLE = eINSTANCE.getXmlElementCollection_2_0_CollectionTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEmbedded_2_0 <em>Xml Embedded 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEmbedded_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlEmbedded_2_0()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED_20 = eINSTANCE.getXmlEmbedded_2_0();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEntity_2_0 <em>Xml Entity 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEntity_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlEntity_2_0()
		 * @generated
		 */
		public static final EClass XML_ENTITY_20 = eINSTANCE.getXmlEntity_2_0();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEntityListener_2_0 <em>Xml Entity Listener 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEntityListener_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlEntityListener_2_0()
		 * @generated
		 */
		public static final EClass XML_ENTITY_LISTENER_20 = eINSTANCE.getXmlEntityListener_2_0();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ENTITY_LISTENER_20__DESCRIPTION = eINSTANCE.getXmlEntityListener_2_0_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlEventMethod_2_0 <em>Xml Event Method 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlEventMethod_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlEventMethod_2_0()
		 * @generated
		 */
		public static final EClass XML_EVENT_METHOD_20 = eINSTANCE.getXmlEventMethod_2_0();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_EVENT_METHOD_20__DESCRIPTION = eINSTANCE.getXmlEventMethod_2_0_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlGenerator_2_0 <em>Xml Generator 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlGenerator_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlGenerator_2_0()
		 * @generated
		 */
		public static final EClass XML_GENERATOR_20 = eINSTANCE.getXmlGenerator_2_0();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_GENERATOR_20__DESCRIPTION = eINSTANCE.getXmlGenerator_2_0_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0 <em>Xml Many To Many 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlManyToMany_2_0()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_MANY_20 = eINSTANCE.getXmlManyToMany_2_0();

		/**
		 * The meta object literal for the '<em><b>Map Key Class</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_MANY_20__MAP_KEY_CLASS = eINSTANCE.getXmlManyToMany_2_0_MapKeyClass();

		/**
		 * The meta object literal for the '<em><b>Map Key Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MANY_TO_MANY_20__MAP_KEY_TEMPORAL = eINSTANCE.getXmlManyToMany_2_0_MapKeyTemporal();

		/**
		 * The meta object literal for the '<em><b>Map Key Enumerated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MANY_TO_MANY_20__MAP_KEY_ENUMERATED = eINSTANCE.getXmlManyToMany_2_0_MapKeyEnumerated();

		/**
		 * The meta object literal for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_MANY_20__MAP_KEY_ATTRIBUTE_OVERRIDES = eINSTANCE.getXmlManyToMany_2_0_MapKeyAttributeOverrides();

		/**
		 * The meta object literal for the '<em><b>Map Key Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_MANY_20__MAP_KEY_COLUMN = eINSTANCE.getXmlManyToMany_2_0_MapKeyColumn();

		/**
		 * The meta object literal for the '<em><b>Map Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_MANY_20__MAP_KEY_JOIN_COLUMNS = eINSTANCE.getXmlManyToMany_2_0_MapKeyJoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlMapKeyClass_2_0 <em>Xml Map Key Class 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlMapKeyClass_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlMapKeyClass_2_0()
		 * @generated
		 */
		public static final EClass XML_MAP_KEY_CLASS_20 = eINSTANCE.getXmlMapKeyClass_2_0();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MAP_KEY_CLASS_20__CLASS_NAME = eINSTANCE.getXmlMapKeyClass_2_0_ClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0 <em>Xml Maps Id 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlMapsId_2_0()
		 * @generated
		 */
		public static final EClass XML_MAPS_ID_20 = eINSTANCE.getXmlMapsId_2_0();

		/**
		 * The meta object literal for the '<em><b>Maps Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MAPS_ID_20__MAPS_ID = eINSTANCE.getXmlMapsId_2_0_MapsId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlNamedQuery_2_0 <em>Xml Named Query 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlNamedQuery_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlNamedQuery_2_0()
		 * @generated
		 */
		public static final EClass XML_NAMED_QUERY_20 = eINSTANCE.getXmlNamedQuery_2_0();

		/**
		 * The meta object literal for the '<em><b>Lock Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_QUERY_20__LOCK_MODE = eINSTANCE.getXmlNamedQuery_2_0_LockMode();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0 <em>Xml One To Many 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlOneToMany_2_0()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_MANY_20 = eINSTANCE.getXmlOneToMany_2_0();

		/**
		 * The meta object literal for the '<em><b>Map Key Class</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_MANY_20__MAP_KEY_CLASS = eINSTANCE.getXmlOneToMany_2_0_MapKeyClass();

		/**
		 * The meta object literal for the '<em><b>Map Key Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_MANY_20__MAP_KEY_TEMPORAL = eINSTANCE.getXmlOneToMany_2_0_MapKeyTemporal();

		/**
		 * The meta object literal for the '<em><b>Map Key Enumerated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_MANY_20__MAP_KEY_ENUMERATED = eINSTANCE.getXmlOneToMany_2_0_MapKeyEnumerated();

		/**
		 * The meta object literal for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_MANY_20__MAP_KEY_ATTRIBUTE_OVERRIDES = eINSTANCE.getXmlOneToMany_2_0_MapKeyAttributeOverrides();

		/**
		 * The meta object literal for the '<em><b>Map Key Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_MANY_20__MAP_KEY_COLUMN = eINSTANCE.getXmlOneToMany_2_0_MapKeyColumn();

		/**
		 * The meta object literal for the '<em><b>Map Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_MANY_20__MAP_KEY_JOIN_COLUMNS = eINSTANCE.getXmlOneToMany_2_0_MapKeyJoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToOne_2_0 <em>Xml One To One 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToOne_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlOneToOne_2_0()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_ONE_20 = eINSTANCE.getXmlOneToOne_2_0();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderColumn_2_0 <em>Xml Order Column 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderColumn_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlOrderColumn_2_0()
		 * @generated
		 */
		public static final EClass XML_ORDER_COLUMN_20 = eINSTANCE.getXmlOrderColumn_2_0();

		/**
		 * The meta object literal for the '<em><b>Nullable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ORDER_COLUMN_20__NULLABLE = eINSTANCE.getXmlOrderColumn_2_0_Nullable();

		/**
		 * The meta object literal for the '<em><b>Insertable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ORDER_COLUMN_20__INSERTABLE = eINSTANCE.getXmlOrderColumn_2_0_Insertable();

		/**
		 * The meta object literal for the '<em><b>Updatable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ORDER_COLUMN_20__UPDATABLE = eINSTANCE.getXmlOrderColumn_2_0_Updatable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitDefaults_2_0 <em>Xml Persistence Unit Defaults 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitDefaults_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlPersistenceUnitDefaults_2_0()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE_UNIT_DEFAULTS_20 = eINSTANCE.getXmlPersistenceUnitDefaults_2_0();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT_DEFAULTS_20__DESCRIPTION = eINSTANCE.getXmlPersistenceUnitDefaults_2_0_Description();

		/**
		 * The meta object literal for the '<em><b>Delimited Identifiers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT_DEFAULTS_20__DELIMITED_IDENTIFIERS = eINSTANCE.getXmlPersistenceUnitDefaults_2_0_DelimitedIdentifiers();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitMetadata_2_0 <em>Xml Persistence Unit Metadata 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitMetadata_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlPersistenceUnitMetadata_2_0()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE_UNIT_METADATA_20 = eINSTANCE.getXmlPersistenceUnitMetadata_2_0();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT_METADATA_20__DESCRIPTION = eINSTANCE.getXmlPersistenceUnitMetadata_2_0_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlQuery_2_0 <em>Xml Query 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlQuery_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlQuery_2_0()
		 * @generated
		 */
		public static final EClass XML_QUERY_20 = eINSTANCE.getXmlQuery_2_0();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_QUERY_20__DESCRIPTION = eINSTANCE.getXmlQuery_2_0_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlQueryHint_2_0 <em>Xml Query Hint 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlQueryHint_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlQueryHint_2_0()
		 * @generated
		 */
		public static final EClass XML_QUERY_HINT_20 = eINSTANCE.getXmlQueryHint_2_0();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_QUERY_HINT_20__DESCRIPTION = eINSTANCE.getXmlQueryHint_2_0_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlSequenceGenerator_2_0 <em>Xml Sequence Generator 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlSequenceGenerator_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlSequenceGenerator_2_0()
		 * @generated
		 */
		public static final EClass XML_SEQUENCE_GENERATOR_20 = eINSTANCE.getXmlSequenceGenerator_2_0();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_SEQUENCE_GENERATOR_20__CATALOG = eINSTANCE.getXmlSequenceGenerator_2_0_Catalog();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_SEQUENCE_GENERATOR_20__SCHEMA = eINSTANCE.getXmlSequenceGenerator_2_0_Schema();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0 <em>Xml Single Relationship Mapping 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlSingleRelationshipMapping_2_0()
		 * @generated
		 */
		public static final EClass XML_SINGLE_RELATIONSHIP_MAPPING_20 = eINSTANCE.getXmlSingleRelationshipMapping_2_0();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlSqlResultSetMapping_2_0 <em>Xml Sql Result Set Mapping 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlSqlResultSetMapping_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlSqlResultSetMapping_2_0()
		 * @generated
		 */
		public static final EClass XML_SQL_RESULT_SET_MAPPING_20 = eINSTANCE.getXmlSqlResultSetMapping_2_0();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_SQL_RESULT_SET_MAPPING_20__DESCRIPTION = eINSTANCE.getXmlSqlResultSetMapping_2_0_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlUniqueConstraint_2_0 <em>Xml Unique Constraint 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlUniqueConstraint_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlUniqueConstraint_2_0()
		 * @generated
		 */
		public static final EClass XML_UNIQUE_CONSTRAINT_20 = eINSTANCE.getXmlUniqueConstraint_2_0();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_UNIQUE_CONSTRAINT_20__NAME = eINSTANCE.getXmlUniqueConstraint_2_0_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0 <em>Xml Cacheable 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlCacheable_2_0()
		 * @generated
		 */
		public static final EClass XML_CACHEABLE_20 = eINSTANCE.getXmlCacheable_2_0();

		/**
		 * The meta object literal for the '<em><b>Cacheable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CACHEABLE_20__CACHEABLE = eINSTANCE.getXmlCacheable_2_0_Cacheable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0 <em>Xml Orphan Removable 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlOrphanRemovable_2_0()
		 * @generated
		 */
		public static final EClass XML_ORPHAN_REMOVABLE_20 = eINSTANCE.getXmlOrphanRemovable_2_0();

		/**
		 * The meta object literal for the '<em><b>Orphan Removal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ORPHAN_REMOVABLE_20__ORPHAN_REMOVAL = eINSTANCE.getXmlOrphanRemovable_2_0_OrphanRemoval();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderable_2_0 <em>Xml Orderable 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderable_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlOrderable_2_0()
		 * @generated
		 */
		public static final EClass XML_ORDERABLE_20 = eINSTANCE.getXmlOrderable_2_0();

		/**
		 * The meta object literal for the '<em><b>Order Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ORDERABLE_20__ORDER_COLUMN = eINSTANCE.getXmlOrderable_2_0_OrderColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.v2_0.LockModeType_2_0 <em>Lock Mode Type 20</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.LockModeType_2_0
		 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getLockModeType_2_0()
		 * @generated
		 */
		public static final EEnum LOCK_MODE_TYPE_20 = eINSTANCE.getLockModeType_2_0();

	}

} //OrmV2_0Package
