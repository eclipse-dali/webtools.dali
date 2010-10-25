/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm.v2_2;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.xml.CommonPackage;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package;

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
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Factory
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
	public static final String eNS_PREFIX = "org.eclipse.jpt.eclipselink.core.resource.orm.v2_2";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLinkOrmV2_2Package eINSTANCE = org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2 <em>Xml Additional Criteria 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlAdditionalCriteria_2_2()
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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2 <em>Xml Basic 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasic_2_2()
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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2 <em>Xml Basic Collection 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasicCollection_2_2()
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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2 <em>Xml Basic Map 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasicMap_2_2()
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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2 <em>Xml Element Collection 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlElementCollection_2_2()
	 * @generated
	 */
	public static final int XML_ELEMENT_COLLECTION_22 = 4;

	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22__CASCADE_ON_DELETE = 0;

	/**
	 * The number of structural features of the '<em>Xml Element Collection 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2 <em>Xml Entity 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntity_2_2()
	 * @generated
	 */
	public static final int XML_ENTITY_22 = 5;

	/**
	 * The feature id for the '<em><b>Additional Criteria</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__ADDITIONAL_CRITERIA = 0;

	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__CASCADE_ON_DELETE = 1;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22__INDEX = 2;

	/**
	 * The number of structural features of the '<em>Xml Entity 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_22_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2 <em>Xml Many To Many 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlManyToMany_2_2()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY_22 = 6;

	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22__CASCADE_ON_DELETE = 0;

	/**
	 * The number of structural features of the '<em>Xml Many To Many 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2 <em>Xml One To One 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlOneToOne_2_2()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE_22 = 7;

	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22__CASCADE_ON_DELETE = 0;

	/**
	 * The number of structural features of the '<em>Xml One To One 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2 <em>Xml One To Many 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlOneToMany_2_2()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY_22 = 8;

	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22__CASCADE_ON_DELETE = 0;

	/**
	 * The number of structural features of the '<em>Xml One To Many 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2 <em>Xml Embeddable 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEmbeddable_2_2()
	 * @generated
	 */
	public static final int XML_EMBEDDABLE_22 = 9;

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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlId_2_2 <em>Xml Id 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlId_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlId_2_2()
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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2 <em>Xml Index 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlIndex_2_2()
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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2 <em>Xml Mapped Superclass 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlMappedSuperclass_2_2()
	 * @generated
	 */
	public static final int XML_MAPPED_SUPERCLASS_22 = 12;

	/**
	 * The feature id for the '<em><b>Additional Criteria</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_22__ADDITIONAL_CRITERIA = 0;

	/**
	 * The number of structural features of the '<em>Xml Mapped Superclass 22</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_22_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2 <em>Xml Version 22</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlVersion_2_2()
	 * @generated
	 */
	public static final int XML_VERSION_22 = 13;

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
	private EClass xmlManyToMany_2_2EClass = null;

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
	private EClass xmlMappedSuperclass_2_2EClass = null;

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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#eNS_URI
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
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2 <em>Xml Additional Criteria 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Additional Criteria 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2
	 * @generated
	 */
	public EClass getXmlAdditionalCriteria_2_2()
	{
		return xmlAdditionalCriteria_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2#getCriteria <em>Criteria</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Criteria</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2#getCriteria()
	 * @see #getXmlAdditionalCriteria_2_2()
	 * @generated
	 */
	public EAttribute getXmlAdditionalCriteria_2_2_Criteria()
	{
		return (EAttribute)xmlAdditionalCriteria_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2 <em>Xml Basic 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2
	 * @generated
	 */
	public EClass getXmlBasic_2_2()
	{
		return xmlBasic_2_2EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2#getIndex()
	 * @see #getXmlBasic_2_2()
	 * @generated
	 */
	public EReference getXmlBasic_2_2_Index()
	{
		return (EReference)xmlBasic_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2 <em>Xml Basic Collection 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic Collection 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2
	 * @generated
	 */
	public EClass getXmlBasicCollection_2_2()
	{
		return xmlBasicCollection_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2#getCascadeOnDelete()
	 * @see #getXmlBasicCollection_2_2()
	 * @generated
	 */
	public EAttribute getXmlBasicCollection_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlBasicCollection_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2 <em>Xml Basic Map 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic Map 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2
	 * @generated
	 */
	public EClass getXmlBasicMap_2_2()
	{
		return xmlBasicMap_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2#getCascadeOnDelete()
	 * @see #getXmlBasicMap_2_2()
	 * @generated
	 */
	public EAttribute getXmlBasicMap_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlBasicMap_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2 <em>Xml Element Collection 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Element Collection 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2
	 * @generated
	 */
	public EClass getXmlElementCollection_2_2()
	{
		return xmlElementCollection_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2#getCascadeOnDelete()
	 * @see #getXmlElementCollection_2_2()
	 * @generated
	 */
	public EAttribute getXmlElementCollection_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlElementCollection_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2 <em>Xml Entity 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2
	 * @generated
	 */
	public EClass getXmlEntity_2_2()
	{
		return xmlEntity_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getCascadeOnDelete()
	 * @see #getXmlEntity_2_2()
	 * @generated
	 */
	public EAttribute getXmlEntity_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlEntity_2_2EClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getIndex()
	 * @see #getXmlEntity_2_2()
	 * @generated
	 */
	public EReference getXmlEntity_2_2_Index()
	{
		return (EReference)xmlEntity_2_2EClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getAdditionalCriteria <em>Additional Criteria</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Additional Criteria</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getAdditionalCriteria()
	 * @see #getXmlEntity_2_2()
	 * @generated
	 */
	public EReference getXmlEntity_2_2_AdditionalCriteria()
	{
		return (EReference)xmlEntity_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2 <em>Xml Many To Many 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2
	 * @generated
	 */
	public EClass getXmlManyToMany_2_2()
	{
		return xmlManyToMany_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2#getCascadeOnDelete()
	 * @see #getXmlManyToMany_2_2()
	 * @generated
	 */
	public EAttribute getXmlManyToMany_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlManyToMany_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2 <em>Xml One To One 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2
	 * @generated
	 */
	public EClass getXmlOneToOne_2_2()
	{
		return xmlOneToOne_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2#getCascadeOnDelete()
	 * @see #getXmlOneToOne_2_2()
	 * @generated
	 */
	public EAttribute getXmlOneToOne_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlOneToOne_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2 <em>Xml One To Many 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2
	 * @generated
	 */
	public EClass getXmlOneToMany_2_2()
	{
		return xmlOneToMany_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade On Delete</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2#getCascadeOnDelete()
	 * @see #getXmlOneToMany_2_2()
	 * @generated
	 */
	public EAttribute getXmlOneToMany_2_2_CascadeOnDelete()
	{
		return (EAttribute)xmlOneToMany_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2 <em>Xml Embeddable 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embeddable 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2
	 * @generated
	 */
	public EClass getXmlEmbeddable_2_2()
	{
		return xmlEmbeddable_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2#getParentClass <em>Parent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parent Class</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2#getParentClass()
	 * @see #getXmlEmbeddable_2_2()
	 * @generated
	 */
	public EAttribute getXmlEmbeddable_2_2_ParentClass()
	{
		return (EAttribute)xmlEmbeddable_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlId_2_2 <em>Xml Id 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlId_2_2
	 * @generated
	 */
	public EClass getXmlId_2_2()
	{
		return xmlId_2_2EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlId_2_2#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlId_2_2#getIndex()
	 * @see #getXmlId_2_2()
	 * @generated
	 */
	public EReference getXmlId_2_2_Index()
	{
		return (EReference)xmlId_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2 <em>Xml Index 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Index 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2
	 * @generated
	 */
	public EClass getXmlIndex_2_2()
	{
		return xmlIndex_2_2EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getName()
	 * @see #getXmlIndex_2_2()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_2_Name()
	{
		return (EAttribute)xmlIndex_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getSchema()
	 * @see #getXmlIndex_2_2()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_2_Schema()
	{
		return (EAttribute)xmlIndex_2_2EClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getCatalog()
	 * @see #getXmlIndex_2_2()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_2_Catalog()
	{
		return (EAttribute)xmlIndex_2_2EClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Table</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getTable()
	 * @see #getXmlIndex_2_2()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_2_Table()
	{
		return (EAttribute)xmlIndex_2_2EClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getUnique <em>Unique</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getUnique()
	 * @see #getXmlIndex_2_2()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_2_Unique()
	{
		return (EAttribute)xmlIndex_2_2EClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getColumnNames <em>Column Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Column Names</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getColumnNames()
	 * @see #getXmlIndex_2_2()
	 * @generated
	 */
	public EAttribute getXmlIndex_2_2_ColumnNames()
	{
		return (EAttribute)xmlIndex_2_2EClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2 <em>Xml Mapped Superclass 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mapped Superclass 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2
	 * @generated
	 */
	public EClass getXmlMappedSuperclass_2_2()
	{
		return xmlMappedSuperclass_2_2EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2#getAdditionalCriteria <em>Additional Criteria</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Additional Criteria</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2#getAdditionalCriteria()
	 * @see #getXmlMappedSuperclass_2_2()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_2_2_AdditionalCriteria()
	{
		return (EReference)xmlMappedSuperclass_2_2EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2 <em>Xml Version 22</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Version 22</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2
	 * @generated
	 */
	public EClass getXmlVersion_2_2()
	{
		return xmlVersion_2_2EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2#getIndex()
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

		xmlElementCollection_2_2EClass = createEClass(XML_ELEMENT_COLLECTION_22);
		createEAttribute(xmlElementCollection_2_2EClass, XML_ELEMENT_COLLECTION_22__CASCADE_ON_DELETE);

		xmlEntity_2_2EClass = createEClass(XML_ENTITY_22);
		createEReference(xmlEntity_2_2EClass, XML_ENTITY_22__ADDITIONAL_CRITERIA);
		createEAttribute(xmlEntity_2_2EClass, XML_ENTITY_22__CASCADE_ON_DELETE);
		createEReference(xmlEntity_2_2EClass, XML_ENTITY_22__INDEX);

		xmlManyToMany_2_2EClass = createEClass(XML_MANY_TO_MANY_22);
		createEAttribute(xmlManyToMany_2_2EClass, XML_MANY_TO_MANY_22__CASCADE_ON_DELETE);

		xmlOneToOne_2_2EClass = createEClass(XML_ONE_TO_ONE_22);
		createEAttribute(xmlOneToOne_2_2EClass, XML_ONE_TO_ONE_22__CASCADE_ON_DELETE);

		xmlOneToMany_2_2EClass = createEClass(XML_ONE_TO_MANY_22);
		createEAttribute(xmlOneToMany_2_2EClass, XML_ONE_TO_MANY_22__CASCADE_ON_DELETE);

		xmlEmbeddable_2_2EClass = createEClass(XML_EMBEDDABLE_22);
		createEAttribute(xmlEmbeddable_2_2EClass, XML_EMBEDDABLE_22__PARENT_CLASS);

		xmlId_2_2EClass = createEClass(XML_ID_22);
		createEReference(xmlId_2_2EClass, XML_ID_22__INDEX);

		xmlIndex_2_2EClass = createEClass(XML_INDEX_22);
		createEAttribute(xmlIndex_2_2EClass, XML_INDEX_22__NAME);
		createEAttribute(xmlIndex_2_2EClass, XML_INDEX_22__SCHEMA);
		createEAttribute(xmlIndex_2_2EClass, XML_INDEX_22__CATALOG);
		createEAttribute(xmlIndex_2_2EClass, XML_INDEX_22__TABLE);
		createEAttribute(xmlIndex_2_2EClass, XML_INDEX_22__UNIQUE);
		createEAttribute(xmlIndex_2_2EClass, XML_INDEX_22__COLUMN_NAMES);

		xmlMappedSuperclass_2_2EClass = createEClass(XML_MAPPED_SUPERCLASS_22);
		createEReference(xmlMappedSuperclass_2_2EClass, XML_MAPPED_SUPERCLASS_22__ADDITIONAL_CRITERIA);

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
		xmlEmbeddable_2_2EClass.getESuperTypes().add(theOrmPackage.getXmlAttributeOverrideContainer());
		xmlEmbeddable_2_2EClass.getESuperTypes().add(theOrmPackage.getXmlAssociationOverrideContainer());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlAdditionalCriteria_2_2EClass, XmlAdditionalCriteria_2_2.class, "XmlAdditionalCriteria_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlAdditionalCriteria_2_2_Criteria(), theXMLTypePackage.getString(), "criteria", null, 0, 1, XmlAdditionalCriteria_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlBasic_2_2EClass, XmlBasic_2_2.class, "XmlBasic_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlBasic_2_2_Index(), this.getXmlIndex_2_2(), null, "index", null, 0, 1, XmlBasic_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlBasicCollection_2_2EClass, XmlBasicCollection_2_2.class, "XmlBasicCollection_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlBasicCollection_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlBasicCollection_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlBasicMap_2_2EClass, XmlBasicMap_2_2.class, "XmlBasicMap_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlBasicMap_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlBasicMap_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlElementCollection_2_2EClass, XmlElementCollection_2_2.class, "XmlElementCollection_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlElementCollection_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlElementCollection_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntity_2_2EClass, XmlEntity_2_2.class, "XmlEntity_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntity_2_2_AdditionalCriteria(), this.getXmlAdditionalCriteria_2_2(), null, "additionalCriteria", null, 0, 1, XmlEntity_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlEntity_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlEntity_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_2_2_Index(), this.getXmlIndex_2_2(), null, "index", null, 0, 1, XmlEntity_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlManyToMany_2_2EClass, XmlManyToMany_2_2.class, "XmlManyToMany_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlManyToMany_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlManyToMany_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToOne_2_2EClass, XmlOneToOne_2_2.class, "XmlOneToOne_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlOneToOne_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlOneToOne_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToMany_2_2EClass, XmlOneToMany_2_2.class, "XmlOneToMany_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlOneToMany_2_2_CascadeOnDelete(), theXMLTypePackage.getBooleanObject(), "cascadeOnDelete", null, 0, 1, XmlOneToMany_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEmbeddable_2_2EClass, XmlEmbeddable_2_2.class, "XmlEmbeddable_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlEmbeddable_2_2_ParentClass(), theXMLTypePackage.getString(), "parentClass", null, 0, 1, XmlEmbeddable_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlId_2_2EClass, XmlId_2_2.class, "XmlId_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlId_2_2_Index(), this.getXmlIndex_2_2(), null, "index", null, 0, 1, XmlId_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlIndex_2_2EClass, XmlIndex_2_2.class, "XmlIndex_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlIndex_2_2_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlIndex_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlIndex_2_2_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, XmlIndex_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlIndex_2_2_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, XmlIndex_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlIndex_2_2_Table(), theXMLTypePackage.getString(), "table", null, 0, 1, XmlIndex_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlIndex_2_2_Unique(), theXMLTypePackage.getBooleanObject(), "unique", null, 0, 1, XmlIndex_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlIndex_2_2_ColumnNames(), theXMLTypePackage.getString(), "columnNames", null, 0, -1, XmlIndex_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMappedSuperclass_2_2EClass, XmlMappedSuperclass_2_2.class, "XmlMappedSuperclass_2_2", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlMappedSuperclass_2_2_AdditionalCriteria(), this.getXmlAdditionalCriteria_2_2(), null, "additionalCriteria", null, 0, 1, XmlMappedSuperclass_2_2.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2 <em>Xml Additional Criteria 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlAdditionalCriteria_2_2()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2 <em>Xml Basic 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasic_2_2()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2 <em>Xml Basic Collection 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicCollection_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasicCollection_2_2()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2 <em>Xml Basic Map 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlBasicMap_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasicMap_2_2()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2 <em>Xml Element Collection 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlElementCollection_2_2()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2 <em>Xml Entity 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntity_2_2()
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
		 * The meta object literal for the '<em><b>Additional Criteria</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_22__ADDITIONAL_CRITERIA = eINSTANCE.getXmlEntity_2_2_AdditionalCriteria();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2 <em>Xml Many To Many 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlManyToMany_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlManyToMany_2_2()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2 <em>Xml One To One 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToOne_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlOneToOne_2_2()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2 <em>Xml One To Many 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlOneToMany_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlOneToMany_2_2()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2 <em>Xml Embeddable 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEmbeddable_2_2()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlId_2_2 <em>Xml Id 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlId_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlId_2_2()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2 <em>Xml Index 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlIndex_2_2()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2 <em>Xml Mapped Superclass 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlMappedSuperclass_2_2()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2 <em>Xml Version 22</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlVersion_2_2
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlVersion_2_2()
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
