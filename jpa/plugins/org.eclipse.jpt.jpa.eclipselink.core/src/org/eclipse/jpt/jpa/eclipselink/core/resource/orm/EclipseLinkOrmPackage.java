/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.xml.CommonPackage;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package;

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
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory
 * @model kind="package"
 * @generated
 */
public class EclipseLinkOrmPackage extends EPackageImpl
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
	public static final String eNS_URI = "jpt.eclipselink.orm.xmi";
	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.jpa.eclipselink.core.resource.orm";
	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLinkOrmPackage eINSTANCE = org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage.init();
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods <em>Xml Access Methods</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAccessMethods()
	 * @generated
	 */
	public static final int XML_ACCESS_METHODS = 0;
	/**
	 * The feature id for the '<em><b>Get Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ACCESS_METHODS__GET_METHOD = 0;
	/**
	 * The feature id for the '<em><b>Set Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ACCESS_METHODS__SET_METHOD = 1;
	/**
	 * The number of structural features of the '<em>Xml Access Methods</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ACCESS_METHODS_FEATURE_COUNT = 2;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethodsHolder <em>Xml Access Methods Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethodsHolder
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAccessMethodsHolder()
	 * @generated
	 */
	public static final int XML_ACCESS_METHODS_HOLDER = 1;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS = 0;
	/**
	 * The number of structural features of the '<em>Xml Access Methods Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ACCESS_METHODS_HOLDER_FEATURE_COUNT = 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAdditionalCriteria <em>Xml Additional Criteria</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAdditionalCriteria
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAdditionalCriteria()
	 * @generated
	 */
	public static final int XML_ADDITIONAL_CRITERIA = 2;
	/**
	 * The feature id for the '<em><b>Criteria</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ADDITIONAL_CRITERIA__CRITERIA = EclipseLinkOrmV2_2Package.XML_ADDITIONAL_CRITERIA_22__CRITERIA;
	/**
	 * The number of structural features of the '<em>Xml Additional Criteria</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ADDITIONAL_CRITERIA_FEATURE_COUNT = EclipseLinkOrmV2_2Package.XML_ADDITIONAL_CRITERIA_22_FEATURE_COUNT + 0;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlArray <em>Xml Array</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlArray
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlArray()
	 * @generated
	 */
	public static final int XML_ARRAY = 3;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ARRAY__ACCESS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ARRAY__NAME = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ARRAY__ACCESS_METHODS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ARRAY__PROPERTIES = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;
	/**
	 * The number of structural features of the '<em>Xml Array</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ARRAY_FEATURE_COUNT = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAttributeMapping()
	 * @generated
	 */
	public static final int XML_ATTRIBUTE_MAPPING = 4;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING__ACCESS = OrmPackage.XML_ATTRIBUTE_MAPPING__ACCESS;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING__NAME = OrmPackage.XML_ATTRIBUTE_MAPPING__NAME;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING__ACCESS_METHODS = OrmPackage.XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING__PROPERTIES = OrmPackage.XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;
	/**
	 * The number of structural features of the '<em>Xml Attribute Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING_FEATURE_COUNT = OrmPackage.XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes <em>Attributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getAttributes()
	 * @generated
	 */
	public static final int ATTRIBUTES = 5;
	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__DESCRIPTION = OrmPackage.ATTRIBUTES__DESCRIPTION;
	/**
	 * The feature id for the '<em><b>Element Collections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__ELEMENT_COLLECTIONS = OrmPackage.ATTRIBUTES__ELEMENT_COLLECTIONS;
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
	 * The feature id for the '<em><b>Structures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__STRUCTURES = OrmPackage.ATTRIBUTES_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Arrays</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__ARRAYS = OrmPackage.ATTRIBUTES_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Basic Collections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__BASIC_COLLECTIONS = OrmPackage.ATTRIBUTES_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Basic Maps</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__BASIC_MAPS = OrmPackage.ATTRIBUTES_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Transformations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__TRANSFORMATIONS = OrmPackage.ATTRIBUTES_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Variable One To Ones</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__VARIABLE_ONE_TO_ONES = OrmPackage.ATTRIBUTES_FEATURE_COUNT + 5;
	/**
	 * The number of structural features of the '<em>Attributes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES_FEATURE_COUNT = OrmPackage.ATTRIBUTES_FEATURE_COUNT + 6;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic <em>Xml Basic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBasic()
	 * @generated
	 */
	public static final int XML_BASIC = 6;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__ACCESS = OrmPackage.XML_BASIC__ACCESS;
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
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__SEQUENCE_GENERATOR = OrmPackage.XML_BASIC_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__TABLE_GENERATOR = OrmPackage.XML_BASIC_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__GENERATED_VALUE = OrmPackage.XML_BASIC_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Return Insert</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__RETURN_INSERT = OrmPackage.XML_BASIC_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Return Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__RETURN_UPDATE = OrmPackage.XML_BASIC_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__ATTRIBUTE_TYPE = OrmPackage.XML_BASIC_FEATURE_COUNT + 5;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__ACCESS_METHODS = OrmPackage.XML_BASIC_FEATURE_COUNT + 6;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__PROPERTIES = OrmPackage.XML_BASIC_FEATURE_COUNT + 7;
	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__MUTABLE = OrmPackage.XML_BASIC_FEATURE_COUNT + 8;
	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__CONVERTER = OrmPackage.XML_BASIC_FEATURE_COUNT + 9;
	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__TYPE_CONVERTER = OrmPackage.XML_BASIC_FEATURE_COUNT + 10;
	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__OBJECT_TYPE_CONVERTER = OrmPackage.XML_BASIC_FEATURE_COUNT + 11;
	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__STRUCT_CONVERTER = OrmPackage.XML_BASIC_FEATURE_COUNT + 12;
	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__CONVERT = OrmPackage.XML_BASIC_FEATURE_COUNT + 13;
	/**
	 * The number of structural features of the '<em>Xml Basic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_FEATURE_COUNT = OrmPackage.XML_BASIC_FEATURE_COUNT + 14;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection <em>Xml Basic Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBasicCollection()
	 * @generated
	 */
	public static final int XML_BASIC_COLLECTION = 7;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION__ACCESS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION__NAME = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION__ACCESS_METHODS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION__PROPERTIES = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION__CASCADE_ON_DELETE = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;
	/**
	 * The number of structural features of the '<em>Xml Basic Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_COLLECTION_FEATURE_COUNT = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicMap <em>Xml Basic Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicMap
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBasicMap()
	 * @generated
	 */
	public static final int XML_BASIC_MAP = 8;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP__ACCESS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP__NAME = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP__ACCESS_METHODS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP__PROPERTIES = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP__CASCADE_ON_DELETE = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;
	/**
	 * The number of structural features of the '<em>Xml Basic Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_MAP_FEATURE_COUNT = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetch <em>Xml Batch Fetch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetch
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBatchFetch()
	 * @generated
	 */
	public static final int XML_BATCH_FETCH = 9;
	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BATCH_FETCH__SIZE = EclipseLinkOrmV2_1Package.XML_BATCH_FETCH_21__SIZE;
	/**
	 * The feature id for the '<em><b>Batch Fetch Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BATCH_FETCH__BATCH_FETCH_TYPE = EclipseLinkOrmV2_1Package.XML_BATCH_FETCH_21__BATCH_FETCH_TYPE;
	/**
	 * The number of structural features of the '<em>Xml Batch Fetch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BATCH_FETCH_FEATURE_COUNT = EclipseLinkOrmV2_1Package.XML_BATCH_FETCH_21_FEATURE_COUNT + 0;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetchHolder <em>Xml Batch Fetch Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetchHolder
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBatchFetchHolder()
	 * @generated
	 */
	public static final int XML_BATCH_FETCH_HOLDER = 10;
	/**
	 * The feature id for the '<em><b>Batch Fetch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BATCH_FETCH_HOLDER__BATCH_FETCH = 0;
	/**
	 * The number of structural features of the '<em>Xml Batch Fetch Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BATCH_FETCH_HOLDER_FEATURE_COUNT = 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache <em>Xml Cache</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache()
	 * @generated
	 */
	public static final int XML_CACHE = 11;
	/**
	 * The feature id for the '<em><b>Expiry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE__EXPIRY = 0;
	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE__SIZE = 1;
	/**
	 * The feature id for the '<em><b>Shared</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE__SHARED = 2;
	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE__TYPE = 3;
	/**
	 * The feature id for the '<em><b>Always Refresh</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE__ALWAYS_REFRESH = 4;
	/**
	 * The feature id for the '<em><b>Refresh Only If Newer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE__REFRESH_ONLY_IF_NEWER = 5;
	/**
	 * The feature id for the '<em><b>Disable Hits</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE__DISABLE_HITS = 6;
	/**
	 * The feature id for the '<em><b>Coordination Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE__COORDINATION_TYPE = 7;
	/**
	 * The feature id for the '<em><b>Expiry Time Of Day</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE__EXPIRY_TIME_OF_DAY = 8;
	/**
	 * The number of structural features of the '<em>Xml Cache</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE_FEATURE_COUNT = 9;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCacheHolder <em>Xml Cache Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCacheHolder
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCacheHolder()
	 * @generated
	 */
	public static final int XML_CACHE_HOLDER = 12;
	/**
	 * The feature id for the '<em><b>Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE_HOLDER__CACHE = 0;
	/**
	 * The feature id for the '<em><b>Existence Checking</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE_HOLDER__EXISTENCE_CHECKING = 1;
	/**
	 * The number of structural features of the '<em>Xml Cache Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CACHE_HOLDER_FEATURE_COUNT = 2;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTracking <em>Xml Change Tracking</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTracking
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTracking()
	 * @generated
	 */
	public static final int XML_CHANGE_TRACKING = 13;
	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CHANGE_TRACKING__TYPE = 0;
	/**
	 * The number of structural features of the '<em>Xml Change Tracking</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CHANGE_TRACKING_FEATURE_COUNT = 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingHolder <em>Xml Change Tracking Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingHolder
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTrackingHolder()
	 * @generated
	 */
	public static final int XML_CHANGE_TRACKING_HOLDER = 14;
	/**
	 * The feature id for the '<em><b>Change Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CHANGE_TRACKING_HOLDER__CHANGE_TRACKING = 0;
	/**
	 * The number of structural features of the '<em>Xml Change Tracking Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CHANGE_TRACKING_HOLDER_FEATURE_COUNT = 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCloneCopyPolicy <em>Xml Clone Copy Policy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCloneCopyPolicy
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCloneCopyPolicy()
	 * @generated
	 */
	public static final int XML_CLONE_COPY_POLICY = 15;
	/**
	 * The feature id for the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CLONE_COPY_POLICY__METHOD = 0;
	/**
	 * The feature id for the '<em><b>Working Copy Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CLONE_COPY_POLICY__WORKING_COPY_METHOD = 1;
	/**
	 * The number of structural features of the '<em>Xml Clone Copy Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CLONE_COPY_POLICY_FEATURE_COUNT = 2;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCollectionTable <em>Xml Collection Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCollectionTable
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCollectionTable()
	 * @generated
	 */
	public static final int XML_COLLECTION_TABLE = 16;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__NAME = OrmPackage.XML_COLLECTION_TABLE__NAME;
	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__CATALOG = OrmPackage.XML_COLLECTION_TABLE__CATALOG;
	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__SCHEMA = OrmPackage.XML_COLLECTION_TABLE__SCHEMA;
	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__UNIQUE_CONSTRAINTS = OrmPackage.XML_COLLECTION_TABLE__UNIQUE_CONSTRAINTS;
	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__JOIN_COLUMNS = OrmPackage.XML_COLLECTION_TABLE__JOIN_COLUMNS;
	/**
	 * The feature id for the '<em><b>Creation Suffix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__CREATION_SUFFIX = OrmPackage.XML_COLLECTION_TABLE_FEATURE_COUNT + 0;
	/**
	 * The number of structural features of the '<em>Xml Collection Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE_FEATURE_COUNT = OrmPackage.XML_COLLECTION_TABLE_FEATURE_COUNT + 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConversionValue <em>Xml Conversion Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConversionValue
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConversionValue()
	 * @generated
	 */
	public static final int XML_CONVERSION_VALUE = 17;
	/**
	 * The feature id for the '<em><b>Data Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERSION_VALUE__DATA_VALUE = 0;
	/**
	 * The feature id for the '<em><b>Object Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERSION_VALUE__OBJECT_VALUE = 1;
	/**
	 * The number of structural features of the '<em>Xml Conversion Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERSION_VALUE_FEATURE_COUNT = 2;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter <em>Xml Named Converter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedConverter()
	 * @generated
	 */
	public static final int XML_NAMED_CONVERTER = 44;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_CONVERTER__NAME = 0;
	/**
	 * The number of structural features of the '<em>Xml Named Converter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_CONVERTER_FEATURE_COUNT = 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter <em>Xml Converter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverter()
	 * @generated
	 */
	public static final int XML_CONVERTER = 18;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER__NAME = XML_NAMED_CONVERTER__NAME;
	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER__CLASS_NAME = XML_NAMED_CONVERTER_FEATURE_COUNT + 0;
	/**
	 * The number of structural features of the '<em>Xml Converter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_FEATURE_COUNT = XML_NAMED_CONVERTER_FEATURE_COUNT + 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder <em>Xml Converter Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverterHolder()
	 * @generated
	 */
	public static final int XML_CONVERTER_HOLDER = 19;
	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_HOLDER__CONVERTER = 0;
	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_HOLDER__TYPE_CONVERTER = 1;
	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_HOLDER__OBJECT_TYPE_CONVERTER = 2;
	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_HOLDER__STRUCT_CONVERTER = 3;
	/**
	 * The number of structural features of the '<em>Xml Converter Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_HOLDER_FEATURE_COUNT = 4;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder <em>Xml Converters Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertersHolder()
	 * @generated
	 */
	public static final int XML_CONVERTERS_HOLDER = 20;
	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTERS_HOLDER__CONVERTERS = 0;
	/**
	 * The feature id for the '<em><b>Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTERS_HOLDER__TYPE_CONVERTERS = 1;
	/**
	 * The feature id for the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTERS_HOLDER__OBJECT_TYPE_CONVERTERS = 2;
	/**
	 * The feature id for the '<em><b>Struct Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTERS_HOLDER__STRUCT_CONVERTERS = 3;
	/**
	 * The number of structural features of the '<em>Xml Converters Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTERS_HOLDER_FEATURE_COUNT = 4;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertibleMapping <em>Xml Convertible Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertibleMapping
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertibleMapping()
	 * @generated
	 */
	public static final int XML_CONVERTIBLE_MAPPING = 21;
	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING__LOB = OrmPackage.XML_CONVERTIBLE_MAPPING__LOB;
	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING__TEMPORAL = OrmPackage.XML_CONVERTIBLE_MAPPING__TEMPORAL;
	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING__ENUMERATED = OrmPackage.XML_CONVERTIBLE_MAPPING__ENUMERATED;
	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING__CONVERTER = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING__TYPE_CONVERTER = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING__OBJECT_TYPE_CONVERTER = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING__STRUCT_CONVERTER = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING__CONVERT = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 4;
	/**
	 * The number of structural features of the '<em>Xml Convertible Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING_FEATURE_COUNT = OrmPackage.XML_CONVERTIBLE_MAPPING_FEATURE_COUNT + 5;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCopyPolicy <em>Xml Copy Policy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCopyPolicy
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCopyPolicy()
	 * @generated
	 */
	public static final int XML_COPY_POLICY = 22;
	/**
	 * The feature id for the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COPY_POLICY__CLASS = 0;
	/**
	 * The number of structural features of the '<em>Xml Copy Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COPY_POLICY_FEATURE_COUNT = 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizer <em>Xml Customizer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizer
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCustomizer()
	 * @generated
	 */
	public static final int XML_CUSTOMIZER = 23;
	/**
	 * The feature id for the '<em><b>Customizer Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CUSTOMIZER__CUSTOMIZER_CLASS_NAME = 0;
	/**
	 * The number of structural features of the '<em>Xml Customizer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CUSTOMIZER_FEATURE_COUNT = 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizerHolder <em>Xml Customizer Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizerHolder
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCustomizerHolder()
	 * @generated
	 */
	public static final int XML_CUSTOMIZER_HOLDER = 24;
	/**
	 * The feature id for the '<em><b>Customizer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CUSTOMIZER_HOLDER__CUSTOMIZER = 0;
	/**
	 * The number of structural features of the '<em>Xml Customizer Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CUSTOMIZER_HOLDER_FEATURE_COUNT = 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlElementCollection <em>Xml Element Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlElementCollection
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlElementCollection()
	 * @generated
	 */
	public static final int XML_ELEMENT_COLLECTION = 25;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ACCESS = OrmPackage.XML_ELEMENT_COLLECTION__ACCESS;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__NAME = OrmPackage.XML_ELEMENT_COLLECTION__NAME;
	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__LOB = OrmPackage.XML_ELEMENT_COLLECTION__LOB;
	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__TEMPORAL = OrmPackage.XML_ELEMENT_COLLECTION__TEMPORAL;
	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ENUMERATED = OrmPackage.XML_ELEMENT_COLLECTION__ENUMERATED;
	/**
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ORDER_COLUMN = OrmPackage.XML_ELEMENT_COLLECTION__ORDER_COLUMN;
	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ORDER_BY = OrmPackage.XML_ELEMENT_COLLECTION__ORDER_BY;
	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES = OrmPackage.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES;
	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES = OrmPackage.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES;
	/**
	 * The feature id for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES = OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES;
	/**
	 * The feature id for the '<em><b>Target Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__TARGET_CLASS = OrmPackage.XML_ELEMENT_COLLECTION__TARGET_CLASS;
	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__FETCH = OrmPackage.XML_ELEMENT_COLLECTION__FETCH;
	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY = OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY;
	/**
	 * The feature id for the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_CLASS = OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS;
	/**
	 * The feature id for the '<em><b>Map Key Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL = OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL;
	/**
	 * The feature id for the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED = OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED;
	/**
	 * The feature id for the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN = OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN;
	/**
	 * The feature id for the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS = OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS;
	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__COLUMN = OrmPackage.XML_ELEMENT_COLLECTION__COLUMN;
	/**
	 * The feature id for the '<em><b>Collection Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__COLLECTION_TABLE = OrmPackage.XML_ELEMENT_COLLECTION__COLLECTION_TABLE;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ACCESS_METHODS = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__PROPERTIES = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__CONVERTER = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__TYPE_CONVERTER = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__OBJECT_TYPE_CONVERTER = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__STRUCT_CONVERTER = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 5;
	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__CONVERT = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 6;
	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__CONVERTERS = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 7;
	/**
	 * The feature id for the '<em><b>Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__TYPE_CONVERTERS = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 8;
	/**
	 * The feature id for the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__OBJECT_TYPE_CONVERTERS = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 9;
	/**
	 * The feature id for the '<em><b>Struct Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__STRUCT_CONVERTERS = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 10;
	/**
	 * The feature id for the '<em><b>Map Key Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_ASSOCIATION_OVERRIDES = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 11;
	/**
	 * The feature id for the '<em><b>Map Key Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_CONVERT = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 12;
	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__JOIN_FETCH = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 13;
	/**
	 * The feature id for the '<em><b>Batch Fetch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__BATCH_FETCH = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 14;
	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ATTRIBUTE_TYPE = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 15;
	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__PARTITIONING = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 16;
	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__REPLICATION_PARTITIONING = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 17;
	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ROUND_ROBIN_PARTITIONING = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 18;
	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__PINNED_PARTITIONING = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 19;
	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__RANGE_PARTITIONING = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 20;
	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__VALUE_PARTITIONING = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 21;
	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__HASH_PARTITIONING = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 22;
	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__UNION_PARTITIONING = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 23;
	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__PARTITIONED = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 24;
	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__CASCADE_ON_DELETE = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 25;
	/**
	 * The feature id for the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__NON_CACHEABLE = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 26;
	/**
	 * The feature id for the '<em><b>Composite Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__COMPOSITE_MEMBER = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 27;
	/**
	 * The number of structural features of the '<em>Xml Element Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_FEATURE_COUNT = OrmPackage.XML_ELEMENT_COLLECTION_FEATURE_COUNT + 28;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable <em>Xml Embeddable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddable()
	 * @generated
	 */
	public static final int XML_EMBEDDABLE = 26;
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
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__ACCESS_METHODS = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__ATTRIBUTE_OVERRIDES = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__ASSOCIATION_OVERRIDES = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Parent Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__PARENT_CLASS = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Plsql Records</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__PLSQL_RECORDS = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Plsql Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__PLSQL_TABLES = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 5;
	/**
	 * The feature id for the '<em><b>Struct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__STRUCT = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 6;
	/**
	 * The feature id for the '<em><b>Customizer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__CUSTOMIZER = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 7;
	/**
	 * The feature id for the '<em><b>Change Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__CHANGE_TRACKING = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 8;
	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__CONVERTERS = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 9;
	/**
	 * The feature id for the '<em><b>Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__TYPE_CONVERTERS = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 10;
	/**
	 * The feature id for the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__OBJECT_TYPE_CONVERTERS = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 11;
	/**
	 * The feature id for the '<em><b>Struct Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__STRUCT_CONVERTERS = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 12;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__PROPERTIES = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 13;
	/**
	 * The feature id for the '<em><b>Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__COPY_POLICY = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 14;
	/**
	 * The feature id for the '<em><b>Instantiation Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__INSTANTIATION_COPY_POLICY = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 15;
	/**
	 * The feature id for the '<em><b>Clone Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__CLONE_COPY_POLICY = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 16;
	/**
	 * The feature id for the '<em><b>Exclude Default Mappings</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__EXCLUDE_DEFAULT_MAPPINGS = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 17;
	/**
	 * The number of structural features of the '<em>Xml Embeddable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_FEATURE_COUNT = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 18;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbedded <em>Xml Embedded</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbedded
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbedded()
	 * @generated
	 */
	public static final int XML_EMBEDDED = 27;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ACCESS = OrmPackage.XML_EMBEDDED__ACCESS;
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
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ASSOCIATION_OVERRIDES = OrmPackage.XML_EMBEDDED__ASSOCIATION_OVERRIDES;
	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ATTRIBUTE_TYPE = OrmPackage.XML_EMBEDDED_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ACCESS_METHODS = OrmPackage.XML_EMBEDDED_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__PROPERTIES = OrmPackage.XML_EMBEDDED_FEATURE_COUNT + 2;
	/**
	 * The number of structural features of the '<em>Xml Embedded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_FEATURE_COUNT = OrmPackage.XML_EMBEDDED_FEATURE_COUNT + 3;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddedId
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddedId()
	 * @generated
	 */
	public static final int XML_EMBEDDED_ID = 28;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__ACCESS = OrmPackage.XML_EMBEDDED_ID__ACCESS;
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
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__ATTRIBUTE_TYPE = OrmPackage.XML_EMBEDDED_ID_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__ACCESS_METHODS = OrmPackage.XML_EMBEDDED_ID_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__PROPERTIES = OrmPackage.XML_EMBEDDED_ID_FEATURE_COUNT + 2;
	/**
	 * The number of structural features of the '<em>Xml Embedded Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID_FEATURE_COUNT = OrmPackage.XML_EMBEDDED_ID_FEATURE_COUNT + 3;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity <em>Xml Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity()
	 * @generated
	 */
	public static final int XML_ENTITY = 29;
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
	public static final int XML_ENTITY__CACHEABLE = OrmPackage.XML_ENTITY__CACHEABLE;
	/**
	 * The feature id for the '<em><b>Id Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ID_CLASS = OrmPackage.XML_ENTITY__ID_CLASS;
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
	 * The feature id for the '<em><b>Primary Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PRIMARY_KEY = OrmPackage.XML_ENTITY_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Cache Interceptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CACHE_INTERCEPTOR = OrmPackage.XML_ENTITY_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Query Redirectors</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__QUERY_REDIRECTORS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ACCESS_METHODS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Fetch Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__FETCH_GROUPS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Class Extractor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CLASS_EXTRACTOR = OrmPackage.XML_ENTITY_FEATURE_COUNT + 5;
	/**
	 * The feature id for the '<em><b>Parent Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PARENT_CLASS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 6;
	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PARTITIONING = OrmPackage.XML_ENTITY_FEATURE_COUNT + 7;
	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__REPLICATION_PARTITIONING = OrmPackage.XML_ENTITY_FEATURE_COUNT + 8;
	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ROUND_ROBIN_PARTITIONING = OrmPackage.XML_ENTITY_FEATURE_COUNT + 9;
	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PINNED_PARTITIONING = OrmPackage.XML_ENTITY_FEATURE_COUNT + 10;
	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__RANGE_PARTITIONING = OrmPackage.XML_ENTITY_FEATURE_COUNT + 11;
	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__VALUE_PARTITIONING = OrmPackage.XML_ENTITY_FEATURE_COUNT + 12;
	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__HASH_PARTITIONING = OrmPackage.XML_ENTITY_FEATURE_COUNT + 13;
	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__UNION_PARTITIONING = OrmPackage.XML_ENTITY_FEATURE_COUNT + 14;
	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PARTITIONED = OrmPackage.XML_ENTITY_FEATURE_COUNT + 15;
	/**
	 * The feature id for the '<em><b>Additional Criteria</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ADDITIONAL_CRITERIA = OrmPackage.XML_ENTITY_FEATURE_COUNT + 16;
	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CASCADE_ON_DELETE = OrmPackage.XML_ENTITY_FEATURE_COUNT + 17;
	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__INDEX = OrmPackage.XML_ENTITY_FEATURE_COUNT + 18;
	/**
	 * The feature id for the '<em><b>Multitenant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__MULTITENANT = OrmPackage.XML_ENTITY_FEATURE_COUNT + 19;
	/**
	 * The feature id for the '<em><b>Named Stored Function Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__NAMED_STORED_FUNCTION_QUERIES = OrmPackage.XML_ENTITY_FEATURE_COUNT + 20;
	/**
	 * The feature id for the '<em><b>Named Plsql Stored Function Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__NAMED_PLSQL_STORED_FUNCTION_QUERIES = OrmPackage.XML_ENTITY_FEATURE_COUNT + 21;
	/**
	 * The feature id for the '<em><b>Named Plsql Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__NAMED_PLSQL_STORED_PROCEDURE_QUERIES = OrmPackage.XML_ENTITY_FEATURE_COUNT + 22;
	/**
	 * The feature id for the '<em><b>Plsql Records</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PLSQL_RECORDS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 23;
	/**
	 * The feature id for the '<em><b>Plsql Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PLSQL_TABLES = OrmPackage.XML_ENTITY_FEATURE_COUNT + 24;
	/**
	 * The feature id for the '<em><b>Struct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__STRUCT = OrmPackage.XML_ENTITY_FEATURE_COUNT + 25;
	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__READ_ONLY = OrmPackage.XML_ENTITY_FEATURE_COUNT + 26;
	/**
	 * The feature id for the '<em><b>Customizer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CUSTOMIZER = OrmPackage.XML_ENTITY_FEATURE_COUNT + 27;
	/**
	 * The feature id for the '<em><b>Change Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CHANGE_TRACKING = OrmPackage.XML_ENTITY_FEATURE_COUNT + 28;
	/**
	 * The feature id for the '<em><b>Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CACHE = OrmPackage.XML_ENTITY_FEATURE_COUNT + 29;
	/**
	 * The feature id for the '<em><b>Existence Checking</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__EXISTENCE_CHECKING = OrmPackage.XML_ENTITY_FEATURE_COUNT + 30;
	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CONVERTERS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 31;
	/**
	 * The feature id for the '<em><b>Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__TYPE_CONVERTERS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 32;
	/**
	 * The feature id for the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__OBJECT_TYPE_CONVERTERS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 33;
	/**
	 * The feature id for the '<em><b>Struct Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__STRUCT_CONVERTERS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 34;
	/**
	 * The feature id for the '<em><b>Named Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES = OrmPackage.XML_ENTITY_FEATURE_COUNT + 35;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PROPERTIES = OrmPackage.XML_ENTITY_FEATURE_COUNT + 36;
	/**
	 * The feature id for the '<em><b>Optimistic Locking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__OPTIMISTIC_LOCKING = OrmPackage.XML_ENTITY_FEATURE_COUNT + 37;
	/**
	 * The feature id for the '<em><b>Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__COPY_POLICY = OrmPackage.XML_ENTITY_FEATURE_COUNT + 38;
	/**
	 * The feature id for the '<em><b>Instantiation Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__INSTANTIATION_COPY_POLICY = OrmPackage.XML_ENTITY_FEATURE_COUNT + 39;
	/**
	 * The feature id for the '<em><b>Clone Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CLONE_COPY_POLICY = OrmPackage.XML_ENTITY_FEATURE_COUNT + 40;
	/**
	 * The feature id for the '<em><b>Exclude Default Mappings</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__EXCLUDE_DEFAULT_MAPPINGS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 41;
	/**
	 * The number of structural features of the '<em>Xml Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_FEATURE_COUNT = OrmPackage.XML_ENTITY_FEATURE_COUNT + 42;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntityMappings()
	 * @generated
	 */
	public static final int XML_ENTITY_MAPPINGS = 30;
	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__VERSION = OrmPackage.XML_ENTITY_MAPPINGS__VERSION;
	/**
	 * The feature id for the '<em><b>Schema Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__SCHEMA_LOCATION = OrmPackage.XML_ENTITY_MAPPINGS__SCHEMA_LOCATION;
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
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__ACCESS_METHODS = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__PARTITIONING = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__REPLICATION_PARTITIONING = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__ROUND_ROBIN_PARTITIONING = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__PINNED_PARTITIONING = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__RANGE_PARTITIONING = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 5;
	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__VALUE_PARTITIONING = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 6;
	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__HASH_PARTITIONING = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 7;
	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__UNION_PARTITIONING = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 8;
	/**
	 * The feature id for the '<em><b>Tenant Discriminator Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__TENANT_DISCRIMINATOR_COLUMNS = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 9;
	/**
	 * The feature id for the '<em><b>Named Stored Function Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__NAMED_STORED_FUNCTION_QUERIES = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 10;
	/**
	 * The feature id for the '<em><b>Named Plsql Stored Function Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__NAMED_PLSQL_STORED_FUNCTION_QUERIES = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 11;
	/**
	 * The feature id for the '<em><b>Named Plsql Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__NAMED_PLSQL_STORED_PROCEDURE_QUERIES = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 12;
	/**
	 * The feature id for the '<em><b>Plsql Records</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__PLSQL_RECORDS = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 13;
	/**
	 * The feature id for the '<em><b>Plsql Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__PLSQL_TABLES = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 14;
	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__CONVERTERS = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 15;
	/**
	 * The feature id for the '<em><b>Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__TYPE_CONVERTERS = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 16;
	/**
	 * The feature id for the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__OBJECT_TYPE_CONVERTERS = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 17;
	/**
	 * The feature id for the '<em><b>Struct Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__STRUCT_CONVERTERS = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 18;
	/**
	 * The feature id for the '<em><b>Named Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__NAMED_STORED_PROCEDURE_QUERIES = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 19;
	/**
	 * The number of structural features of the '<em>Xml Entity Mappings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_FEATURE_COUNT = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 20;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchAttribute <em>Xml Fetch Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchAttribute
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlFetchAttribute()
	 * @generated
	 */
	public static final int XML_FETCH_ATTRIBUTE = 31;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_ATTRIBUTE__NAME = EclipseLinkOrmV2_1Package.XML_FETCH_ATTRIBUTE_21__NAME;
	/**
	 * The number of structural features of the '<em>Xml Fetch Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_ATTRIBUTE_FEATURE_COUNT = EclipseLinkOrmV2_1Package.XML_FETCH_ATTRIBUTE_21_FEATURE_COUNT + 0;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchGroup <em>Xml Fetch Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchGroup
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlFetchGroup()
	 * @generated
	 */
	public static final int XML_FETCH_GROUP = 32;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_GROUP__NAME = EclipseLinkOrmV2_1Package.XML_FETCH_GROUP_21__NAME;
	/**
	 * The feature id for the '<em><b>Load</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_GROUP__LOAD = EclipseLinkOrmV2_1Package.XML_FETCH_GROUP_21__LOAD;
	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_GROUP__ATTRIBUTES = EclipseLinkOrmV2_1Package.XML_FETCH_GROUP_21__ATTRIBUTES;
	/**
	 * The number of structural features of the '<em>Xml Fetch Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_FETCH_GROUP_FEATURE_COUNT = EclipseLinkOrmV2_1Package.XML_FETCH_GROUP_21_FEATURE_COUNT + 0;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId <em>Xml Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlId()
	 * @generated
	 */
	public static final int XML_ID = 34;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlIndex <em>Xml Index</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlIndex
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlIndex()
	 * @generated
	 */
	public static final int XML_INDEX = 35;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlInstantiationCopyPolicy <em>Xml Instantiation Copy Policy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlInstantiationCopyPolicy
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlInstantiationCopyPolicy()
	 * @generated
	 */
	public static final int XML_INSTANTIATION_COPY_POLICY = 36;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetch <em>Xml Join Fetch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetch
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetch()
	 * @generated
	 */
	public static final int XML_JOIN_FETCH = 37;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinTable <em>Xml Join Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinTable
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinTable()
	 * @generated
	 */
	public static final int XML_JOIN_TABLE = 38;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToMany
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToMany()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY = 39;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToOne()
	 * @generated
	 */
	public static final int XML_MANY_TO_ONE = 40;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMappedSuperclass()
	 * @generated
	 */
	public static final int XML_MAPPED_SUPERCLASS = 41;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMutable <em>Xml Mutable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMutable
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMutable()
	 * @generated
	 */
	public static final int XML_MUTABLE = 43;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery <em>Xml Named Stored Procedure Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery()
	 * @generated
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY = 48;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter <em>Xml Object Type Converter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlObjectTypeConverter()
	 * @generated
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER = 49;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToMany
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOneToMany()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY = 50;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToOne
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOneToOne()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE = 51;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLocking <em>Xml Optimistic Locking</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLocking
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOptimisticLocking()
	 * @generated
	 */
	public static final int XML_OPTIMISTIC_LOCKING = 52;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOrderColumn <em>Xml Order Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOrderColumn
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOrderColumn()
	 * @generated
	 */
	public static final int XML_ORDER_COLUMN = 53;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitDefaults <em>Xml Persistence Unit Defaults</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitDefaults
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPersistenceUnitDefaults()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS = 55;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitMetadata <em>Xml Persistence Unit Metadata</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitMetadata
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPersistenceUnitMetadata()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA = 56;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrimaryKey <em>Xml Primary Key</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrimaryKey
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrimaryKey()
	 * @generated
	 */
	public static final int XML_PRIMARY_KEY = 60;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrivateOwned <em>Xml Private Owned</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrivateOwned
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrivateOwned()
	 * @generated
	 */
	public static final int XML_PRIVATE_OWNED = 61;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty <em>Xml Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlProperty()
	 * @generated
	 */
	public static final int XML_PROPERTY = 62;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPropertyContainer <em>Xml Property Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPropertyContainer
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPropertyContainer()
	 * @generated
	 */
	public static final int XML_PROPERTY_CONTAINER = 63;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryContainer <em>Xml Query Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryContainer
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlQueryContainer()
	 * @generated
	 */
	public static final int XML_QUERY_CONTAINER = 64;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryRedirectors <em>Xml Query Redirectors</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryRedirectors
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlQueryRedirectors()
	 * @generated
	 */
	public static final int XML_QUERY_REDIRECTORS = 65;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReadOnly <em>Xml Read Only</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReadOnly
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlReadOnly()
	 * @generated
	 */
	public static final int XML_READ_ONLY = 67;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReturnInsert <em>Xml Return Insert</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReturnInsert
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlReturnInsert()
	 * @generated
	 */
	public static final int XML_RETURN_INSERT = 69;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlSecondaryTable <em>Xml Secondary Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlSecondaryTable
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlSecondaryTable()
	 * @generated
	 */
	public static final int XML_SECONDARY_TABLE = 71;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter <em>Xml Stored Procedure Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStoredProcedureParameter()
	 * @generated
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER = 72;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter <em>Xml Struct Converter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStructConverter()
	 * @generated
	 */
	public static final int XML_STRUCT_CONVERTER = 74;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTable <em>Xml Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTable
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTable()
	 * @generated
	 */
	public static final int XML_TABLE = 76;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTableGenerator <em>Xml Table Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTableGenerator
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTableGenerator()
	 * @generated
	 */
	public static final int XML_TABLE_GENERATOR = 77;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay <em>Xml Time Of Day</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTimeOfDay()
	 * @generated
	 */
	public static final int XML_TIME_OF_DAY = 79;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransformation <em>Xml Transformation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransformation
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTransformation()
	 * @generated
	 */
	public static final int XML_TRANSFORMATION = 80;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransient <em>Xml Transient</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransient
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTransient()
	 * @generated
	 */
	public static final int XML_TRANSIENT = 81;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter <em>Xml Type Converter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTypeConverter()
	 * @generated
	 */
	public static final int XML_TYPE_CONVERTER = 82;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVariableOneToOne <em>Xml Variable One To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVariableOneToOne
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlVariableOneToOne()
	 * @generated
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE = 85;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVersion <em>Xml Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVersion
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlVersion()
	 * @generated
	 */
	public static final int XML_VERSION = 86;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlHashPartitioning <em>Xml Hash Partitioning</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlHashPartitioning
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlHashPartitioning()
	 * @generated
	 */
	public static final int XML_HASH_PARTITIONING = 33;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_HASH_PARTITIONING__NAME = EclipseLinkOrmV2_2Package.XML_HASH_PARTITIONING_22__NAME;
	/**
	 * The number of structural features of the '<em>Xml Hash Partitioning</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_HASH_PARTITIONING_FEATURE_COUNT = EclipseLinkOrmV2_2Package.XML_HASH_PARTITIONING_22_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__ACCESS = OrmPackage.XML_ID__ACCESS;
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
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__ATTRIBUTE_TYPE = OrmPackage.XML_ID_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__INDEX = OrmPackage.XML_ID_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__ACCESS_METHODS = OrmPackage.XML_ID_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__PROPERTIES = OrmPackage.XML_ID_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__MUTABLE = OrmPackage.XML_ID_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__CONVERTER = OrmPackage.XML_ID_FEATURE_COUNT + 5;
	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__TYPE_CONVERTER = OrmPackage.XML_ID_FEATURE_COUNT + 6;
	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__OBJECT_TYPE_CONVERTER = OrmPackage.XML_ID_FEATURE_COUNT + 7;
	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__STRUCT_CONVERTER = OrmPackage.XML_ID_FEATURE_COUNT + 8;
	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__CONVERT = OrmPackage.XML_ID_FEATURE_COUNT + 9;
	/**
	 * The number of structural features of the '<em>Xml Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_FEATURE_COUNT = OrmPackage.XML_ID_FEATURE_COUNT + 10;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX__NAME = EclipseLinkOrmV2_2Package.XML_INDEX_22__NAME;
	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX__SCHEMA = EclipseLinkOrmV2_2Package.XML_INDEX_22__SCHEMA;
	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX__CATALOG = EclipseLinkOrmV2_2Package.XML_INDEX_22__CATALOG;
	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX__TABLE = EclipseLinkOrmV2_2Package.XML_INDEX_22__TABLE;
	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX__UNIQUE = EclipseLinkOrmV2_2Package.XML_INDEX_22__UNIQUE;
	/**
	 * The feature id for the '<em><b>Column Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX__COLUMN_NAMES = EclipseLinkOrmV2_2Package.XML_INDEX_22__COLUMN_NAMES;
	/**
	 * The number of structural features of the '<em>Xml Index</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INDEX_FEATURE_COUNT = EclipseLinkOrmV2_2Package.XML_INDEX_22_FEATURE_COUNT + 0;
	/**
	 * The number of structural features of the '<em>Xml Instantiation Copy Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_INSTANTIATION_COPY_POLICY_FEATURE_COUNT = 0;
	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_FETCH__JOIN_FETCH = 0;
	/**
	 * The number of structural features of the '<em>Xml Join Fetch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_FETCH_FEATURE_COUNT = 1;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__NAME = OrmPackage.XML_JOIN_TABLE__NAME;
	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__CATALOG = OrmPackage.XML_JOIN_TABLE__CATALOG;
	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__SCHEMA = OrmPackage.XML_JOIN_TABLE__SCHEMA;
	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__UNIQUE_CONSTRAINTS = OrmPackage.XML_JOIN_TABLE__UNIQUE_CONSTRAINTS;
	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__JOIN_COLUMNS = OrmPackage.XML_JOIN_TABLE__JOIN_COLUMNS;
	/**
	 * The feature id for the '<em><b>Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS = OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS;
	/**
	 * The feature id for the '<em><b>Creation Suffix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__CREATION_SUFFIX = OrmPackage.XML_JOIN_TABLE_FEATURE_COUNT + 0;
	/**
	 * The number of structural features of the '<em>Xml Join Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE_FEATURE_COUNT = OrmPackage.XML_JOIN_TABLE_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ACCESS = OrmPackage.XML_MANY_TO_MANY__ACCESS;
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
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ORDER_COLUMN = OrmPackage.XML_MANY_TO_MANY__ORDER_COLUMN;
	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ORDER_BY = OrmPackage.XML_MANY_TO_MANY__ORDER_BY;
	/**
	 * The feature id for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES = OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES;
	/**
	 * The feature id for the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_CLASS = OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CLASS;
	/**
	 * The feature id for the '<em><b>Map Key Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_TEMPORAL = OrmPackage.XML_MANY_TO_MANY__MAP_KEY_TEMPORAL;
	/**
	 * The feature id for the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_ENUMERATED = OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ENUMERATED;
	/**
	 * The feature id for the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_COLUMN = OrmPackage.XML_MANY_TO_MANY__MAP_KEY_COLUMN;
	/**
	 * The feature id for the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS = OrmPackage.XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS;
	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY = OrmPackage.XML_MANY_TO_MANY__MAP_KEY;
	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__CONVERTER = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__TYPE_CONVERTER = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__OBJECT_TYPE_CONVERTER = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__STRUCT_CONVERTER = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Map Key Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_ASSOCIATION_OVERRIDES = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Map Key Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_CONVERT = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 5;
	/**
	 * The feature id for the '<em><b>Batch Fetch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__BATCH_FETCH = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 6;
	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ATTRIBUTE_TYPE = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 7;
	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__PARTITIONING = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 8;
	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__REPLICATION_PARTITIONING = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 9;
	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ROUND_ROBIN_PARTITIONING = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 10;
	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__PINNED_PARTITIONING = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 11;
	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__RANGE_PARTITIONING = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 12;
	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__VALUE_PARTITIONING = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 13;
	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__HASH_PARTITIONING = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 14;
	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__UNION_PARTITIONING = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 15;
	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__PARTITIONED = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 16;
	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__CASCADE_ON_DELETE = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 17;
	/**
	 * The feature id for the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__NON_CACHEABLE = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 18;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ACCESS_METHODS = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 19;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__PROPERTIES = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 20;
	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__JOIN_FETCH = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 21;
	/**
	 * The number of structural features of the '<em>Xml Many To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_FEATURE_COUNT = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 22;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__ACCESS = OrmPackage.XML_MANY_TO_ONE__ACCESS;
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
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__ID = OrmPackage.XML_MANY_TO_ONE__ID;
	/**
	 * The feature id for the '<em><b>Maps Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__MAPS_ID = OrmPackage.XML_MANY_TO_ONE__MAPS_ID;
	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__OPTIONAL = OrmPackage.XML_MANY_TO_ONE__OPTIONAL;
	/**
	 * The feature id for the '<em><b>Batch Fetch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__BATCH_FETCH = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__PARTITIONING = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__REPLICATION_PARTITIONING = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__ROUND_ROBIN_PARTITIONING = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__PINNED_PARTITIONING = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__RANGE_PARTITIONING = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 5;
	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__VALUE_PARTITIONING = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 6;
	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__HASH_PARTITIONING = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 7;
	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__UNION_PARTITIONING = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 8;
	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__PARTITIONED = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 9;
	/**
	 * The feature id for the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__NON_CACHEABLE = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 10;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__ACCESS_METHODS = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 11;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__PROPERTIES = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 12;
	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__JOIN_FETCH = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 13;
	/**
	 * The number of structural features of the '<em>Xml Many To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_FEATURE_COUNT = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 14;
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
	 * The feature id for the '<em><b>Primary Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PRIMARY_KEY = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CACHEABLE = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Cache Interceptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CACHE_INTERCEPTOR = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ASSOCIATION_OVERRIDES = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ATTRIBUTE_OVERRIDES = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Fetch Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__FETCH_GROUPS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 5;
	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__SEQUENCE_GENERATOR = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 6;
	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__TABLE_GENERATOR = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 7;
	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__NAMED_QUERIES = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 8;
	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__NAMED_NATIVE_QUERIES = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 9;
	/**
	 * The feature id for the '<em><b>Named Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__NAMED_STORED_PROCEDURE_QUERIES = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 10;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ACCESS_METHODS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 11;
	/**
	 * The feature id for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__SQL_RESULT_SET_MAPPINGS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 12;
	/**
	 * The feature id for the '<em><b>Query Redirectors</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__QUERY_REDIRECTORS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 13;
	/**
	 * The feature id for the '<em><b>Parent Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PARENT_CLASS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 14;
	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PARTITIONING = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 15;
	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__REPLICATION_PARTITIONING = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 16;
	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ROUND_ROBIN_PARTITIONING = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 17;
	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PINNED_PARTITIONING = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 18;
	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__RANGE_PARTITIONING = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 19;
	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__VALUE_PARTITIONING = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 20;
	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__HASH_PARTITIONING = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 21;
	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__UNION_PARTITIONING = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 22;
	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PARTITIONED = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 23;
	/**
	 * The feature id for the '<em><b>Additional Criteria</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ADDITIONAL_CRITERIA = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 24;
	/**
	 * The feature id for the '<em><b>Multitenant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__MULTITENANT = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 25;
	/**
	 * The feature id for the '<em><b>Named Stored Function Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__NAMED_STORED_FUNCTION_QUERIES = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 26;
	/**
	 * The feature id for the '<em><b>Named Plsql Stored Function Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__NAMED_PLSQL_STORED_FUNCTION_QUERIES = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 27;
	/**
	 * The feature id for the '<em><b>Named Plsql Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__NAMED_PLSQL_STORED_PROCEDURE_QUERIES = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 28;
	/**
	 * The feature id for the '<em><b>Plsql Records</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PLSQL_RECORDS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 29;
	/**
	 * The feature id for the '<em><b>Plsql Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PLSQL_TABLES = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 30;
	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__READ_ONLY = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 31;
	/**
	 * The feature id for the '<em><b>Customizer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CUSTOMIZER = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 32;
	/**
	 * The feature id for the '<em><b>Change Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CHANGE_TRACKING = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 33;
	/**
	 * The feature id for the '<em><b>Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CACHE = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 34;
	/**
	 * The feature id for the '<em><b>Existence Checking</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__EXISTENCE_CHECKING = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 35;
	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CONVERTERS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 36;
	/**
	 * The feature id for the '<em><b>Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__TYPE_CONVERTERS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 37;
	/**
	 * The feature id for the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__OBJECT_TYPE_CONVERTERS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 38;
	/**
	 * The feature id for the '<em><b>Struct Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__STRUCT_CONVERTERS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 39;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PROPERTIES = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 40;
	/**
	 * The feature id for the '<em><b>Optimistic Locking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__OPTIMISTIC_LOCKING = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 41;
	/**
	 * The feature id for the '<em><b>Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__COPY_POLICY = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 42;
	/**
	 * The feature id for the '<em><b>Instantiation Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__INSTANTIATION_COPY_POLICY = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 43;
	/**
	 * The feature id for the '<em><b>Clone Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CLONE_COPY_POLICY = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 44;
	/**
	 * The feature id for the '<em><b>Exclude Default Mappings</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_MAPPINGS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 45;
	/**
	 * The number of structural features of the '<em>Xml Mapped Superclass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_FEATURE_COUNT = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 46;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenant <em>Xml Multitenant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenant
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMultitenant()
	 * @generated
	 */
	public static final int XML_MULTITENANT = 42;
	/**
	 * The number of structural features of the '<em>Xml Multitenant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTITENANT_FEATURE_COUNT = EclipseLinkOrmV2_3Package.XML_MULTITENANT_23_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MUTABLE__MUTABLE = 0;
	/**
	 * The number of structural features of the '<em>Xml Mutable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MUTABLE_FEATURE_COUNT = 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredFunctionQuery <em>Xml Named Plsql Stored Function Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredFunctionQuery
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedPlsqlStoredFunctionQuery()
	 * @generated
	 */
	public static final int XML_NAMED_PLSQL_STORED_FUNCTION_QUERY = 45;
	/**
	 * The number of structural features of the '<em>Xml Named Plsql Stored Function Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_PLSQL_STORED_FUNCTION_QUERY_FEATURE_COUNT = EclipseLinkOrmV2_3Package.XML_NAMED_PLSQL_STORED_FUNCTION_QUERY_23_FEATURE_COUNT + 0;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredProcedureQuery <em>Xml Named Plsql Stored Procedure Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredProcedureQuery
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedPlsqlStoredProcedureQuery()
	 * @generated
	 */
	public static final int XML_NAMED_PLSQL_STORED_PROCEDURE_QUERY = 46;
	/**
	 * The number of structural features of the '<em>Xml Named Plsql Stored Procedure Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_PLSQL_STORED_PROCEDURE_QUERY_FEATURE_COUNT = EclipseLinkOrmV2_3Package.XML_NAMED_PLSQL_STORED_PROCEDURE_QUERY_23_FEATURE_COUNT + 0;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredFunctionQuery <em>Xml Named Stored Function Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredFunctionQuery
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredFunctionQuery()
	 * @generated
	 */
	public static final int XML_NAMED_STORED_FUNCTION_QUERY = 47;
	/**
	 * The number of structural features of the '<em>Xml Named Stored Function Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_FUNCTION_QUERY_FEATURE_COUNT = EclipseLinkOrmV2_3Package.XML_NAMED_STORED_FUNCTION_QUERY_23_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY__NAME = 0;
	/**
	 * The feature id for the '<em><b>Result Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASS = 1;
	/**
	 * The feature id for the '<em><b>Result Set Mapping</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPING = 2;
	/**
	 * The feature id for the '<em><b>Procedure Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME = 3;
	/**
	 * The feature id for the '<em><b>Returns Result Set</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET = 4;
	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY__HINTS = 5;
	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS = 6;
	/**
	 * The number of structural features of the '<em>Xml Named Stored Procedure Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_STORED_PROCEDURE_QUERY_FEATURE_COUNT = 7;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER__NAME = XML_NAMED_CONVERTER__NAME;
	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER__DATA_TYPE = XML_NAMED_CONVERTER_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Object Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER__OBJECT_TYPE = XML_NAMED_CONVERTER_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Conversion Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER__CONVERSION_VALUES = XML_NAMED_CONVERTER_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Default Object Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE = XML_NAMED_CONVERTER_FEATURE_COUNT + 3;
	/**
	 * The number of structural features of the '<em>Xml Object Type Converter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER_FEATURE_COUNT = XML_NAMED_CONVERTER_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ACCESS = OrmPackage.XML_ONE_TO_MANY__ACCESS;
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
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ORDER_COLUMN = OrmPackage.XML_ONE_TO_MANY__ORDER_COLUMN;
	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ORDER_BY = OrmPackage.XML_ONE_TO_MANY__ORDER_BY;
	/**
	 * The feature id for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES = OrmPackage.XML_ONE_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES;
	/**
	 * The feature id for the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_CLASS = OrmPackage.XML_ONE_TO_MANY__MAP_KEY_CLASS;
	/**
	 * The feature id for the '<em><b>Map Key Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_TEMPORAL = OrmPackage.XML_ONE_TO_MANY__MAP_KEY_TEMPORAL;
	/**
	 * The feature id for the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_ENUMERATED = OrmPackage.XML_ONE_TO_MANY__MAP_KEY_ENUMERATED;
	/**
	 * The feature id for the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_COLUMN = OrmPackage.XML_ONE_TO_MANY__MAP_KEY_COLUMN;
	/**
	 * The feature id for the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_JOIN_COLUMNS = OrmPackage.XML_ONE_TO_MANY__MAP_KEY_JOIN_COLUMNS;
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
	 * The feature id for the '<em><b>Orphan Removal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ORPHAN_REMOVAL = OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL;
	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__CONVERTER = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__TYPE_CONVERTER = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__OBJECT_TYPE_CONVERTER = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__STRUCT_CONVERTER = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Map Key Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_ASSOCIATION_OVERRIDES = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Map Key Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_CONVERT = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 5;
	/**
	 * The feature id for the '<em><b>Batch Fetch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__BATCH_FETCH = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 6;
	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ATTRIBUTE_TYPE = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 7;
	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__PARTITIONING = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 8;
	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__REPLICATION_PARTITIONING = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 9;
	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ROUND_ROBIN_PARTITIONING = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 10;
	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__PINNED_PARTITIONING = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 11;
	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__RANGE_PARTITIONING = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 12;
	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__VALUE_PARTITIONING = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 13;
	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__HASH_PARTITIONING = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 14;
	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__UNION_PARTITIONING = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 15;
	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__PARTITIONED = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 16;
	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__CASCADE_ON_DELETE = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 17;
	/**
	 * The feature id for the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__NON_CACHEABLE = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 18;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ACCESS_METHODS = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 19;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__PROPERTIES = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 20;
	/**
	 * The feature id for the '<em><b>Private Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__PRIVATE_OWNED = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 21;
	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__JOIN_FETCH = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 22;
	/**
	 * The number of structural features of the '<em>Xml One To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_FEATURE_COUNT = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 23;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__ACCESS = OrmPackage.XML_ONE_TO_ONE__ACCESS;
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
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__ID = OrmPackage.XML_ONE_TO_ONE__ID;
	/**
	 * The feature id for the '<em><b>Maps Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__MAPS_ID = OrmPackage.XML_ONE_TO_ONE__MAPS_ID;
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
	 * The feature id for the '<em><b>Orphan Removal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__ORPHAN_REMOVAL = OrmPackage.XML_ONE_TO_ONE__ORPHAN_REMOVAL;
	/**
	 * The feature id for the '<em><b>Batch Fetch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__BATCH_FETCH = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__PARTITIONING = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__REPLICATION_PARTITIONING = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__ROUND_ROBIN_PARTITIONING = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__PINNED_PARTITIONING = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__RANGE_PARTITIONING = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 5;
	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__VALUE_PARTITIONING = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 6;
	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__HASH_PARTITIONING = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 7;
	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__UNION_PARTITIONING = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 8;
	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__PARTITIONED = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 9;
	/**
	 * The feature id for the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__CASCADE_ON_DELETE = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 10;
	/**
	 * The feature id for the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__NON_CACHEABLE = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 11;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__ACCESS_METHODS = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 12;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__PROPERTIES = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 13;
	/**
	 * The feature id for the '<em><b>Private Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__PRIVATE_OWNED = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 14;
	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__JOIN_FETCH = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 15;
	/**
	 * The number of structural features of the '<em>Xml One To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_FEATURE_COUNT = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 16;
	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OPTIMISTIC_LOCKING__TYPE = 0;
	/**
	 * The feature id for the '<em><b>Cascade</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OPTIMISTIC_LOCKING__CASCADE = 1;
	/**
	 * The feature id for the '<em><b>Selected Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OPTIMISTIC_LOCKING__SELECTED_COLUMNS = 2;
	/**
	 * The number of structural features of the '<em>Xml Optimistic Locking</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OPTIMISTIC_LOCKING_FEATURE_COUNT = 3;
	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__COLUMN_DEFINITION = OrmPackage.XML_ORDER_COLUMN__COLUMN_DEFINITION;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__NAME = OrmPackage.XML_ORDER_COLUMN__NAME;
	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__NULLABLE = OrmPackage.XML_ORDER_COLUMN__NULLABLE;
	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__INSERTABLE = OrmPackage.XML_ORDER_COLUMN__INSERTABLE;
	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__UPDATABLE = OrmPackage.XML_ORDER_COLUMN__UPDATABLE;
	/**
	 * The feature id for the '<em><b>Correction Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__CORRECTION_TYPE = OrmPackage.XML_ORDER_COLUMN_FEATURE_COUNT + 0;
	/**
	 * The number of structural features of the '<em>Xml Order Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN_FEATURE_COUNT = OrmPackage.XML_ORDER_COLUMN_FEATURE_COUNT + 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPartitioning <em>Xml Partitioning</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPartitioning
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioning()
	 * @generated
	 */
	public static final int XML_PARTITIONING = 54;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING__NAME = EclipseLinkOrmV2_2Package.XML_PARTITIONING_22__NAME;
	/**
	 * The number of structural features of the '<em>Xml Partitioning</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PARTITIONING_FEATURE_COUNT = EclipseLinkOrmV2_2Package.XML_PARTITIONING_22_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS;
	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__DESCRIPTION = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DESCRIPTION;
	/**
	 * The feature id for the '<em><b>Delimited Identifiers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__DELIMITED_IDENTIFIERS = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DELIMITED_IDENTIFIERS;
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
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS_METHODS = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Tenant Discriminator Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__TENANT_DISCRIMINATOR_COLUMNS = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS_FEATURE_COUNT + 1;
	/**
	 * The number of structural features of the '<em>Xml Persistence Unit Defaults</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS_FEATURE_COUNT = OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA__DESCRIPTION = OrmPackage.XML_PERSISTENCE_UNIT_METADATA__DESCRIPTION;
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
	 * The feature id for the '<em><b>Exclude Default Mappings</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA__EXCLUDE_DEFAULT_MAPPINGS = OrmPackage.XML_PERSISTENCE_UNIT_METADATA_FEATURE_COUNT + 0;
	/**
	 * The number of structural features of the '<em>Xml Persistence Unit Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA_FEATURE_COUNT = OrmPackage.XML_PERSISTENCE_UNIT_METADATA_FEATURE_COUNT + 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPinnedPartitioning <em>Xml Pinned Partitioning</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPinnedPartitioning
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPinnedPartitioning()
	 * @generated
	 */
	public static final int XML_PINNED_PARTITIONING = 57;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PINNED_PARTITIONING__NAME = EclipseLinkOrmV2_2Package.XML_PINNED_PARTITIONING_22__NAME;
	/**
	 * The number of structural features of the '<em>Xml Pinned Partitioning</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PINNED_PARTITIONING_FEATURE_COUNT = EclipseLinkOrmV2_2Package.XML_PINNED_PARTITIONING_22_FEATURE_COUNT + 0;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlRecord <em>Xml Plsql Record</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlRecord
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPlsqlRecord()
	 * @generated
	 */
	public static final int XML_PLSQL_RECORD = 58;
	/**
	 * The number of structural features of the '<em>Xml Plsql Record</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PLSQL_RECORD_FEATURE_COUNT = EclipseLinkOrmV2_3Package.XML_PLSQL_RECORD_23_FEATURE_COUNT + 0;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlTable <em>Xml Plsql Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlTable
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPlsqlTable()
	 * @generated
	 */
	public static final int XML_PLSQL_TABLE = 59;
	/**
	 * The number of structural features of the '<em>Xml Plsql Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PLSQL_TABLE_FEATURE_COUNT = EclipseLinkOrmV2_3Package.XML_PLSQL_TABLE_23_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Validation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY__VALIDATION = EclipseLinkOrmV1_1Package.XML_PRIMARY_KEY_11__VALIDATION;
	/**
	 * The feature id for the '<em><b>Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY__COLUMNS = EclipseLinkOrmV1_1Package.XML_PRIMARY_KEY_11__COLUMNS;
	/**
	 * The feature id for the '<em><b>Cache Key Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY__CACHE_KEY_TYPE = EclipseLinkOrmV1_1Package.XML_PRIMARY_KEY_11_FEATURE_COUNT + 0;
	/**
	 * The number of structural features of the '<em>Xml Primary Key</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_FEATURE_COUNT = EclipseLinkOrmV1_1Package.XML_PRIMARY_KEY_11_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Private Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIVATE_OWNED__PRIVATE_OWNED = 0;
	/**
	 * The number of structural features of the '<em>Xml Private Owned</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIVATE_OWNED_FEATURE_COUNT = 1;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PROPERTY__NAME = 0;
	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PROPERTY__VALUE = 1;
	/**
	 * The feature id for the '<em><b>Value Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PROPERTY__VALUE_TYPE = 2;
	/**
	 * The number of structural features of the '<em>Xml Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PROPERTY_FEATURE_COUNT = 3;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PROPERTY_CONTAINER__PROPERTIES = 0;
	/**
	 * The number of structural features of the '<em>Xml Property Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PROPERTY_CONTAINER_FEATURE_COUNT = 1;
	/**
	 * The feature id for the '<em><b>Named Stored Procedure Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_CONTAINER__NAMED_STORED_PROCEDURE_QUERIES = 0;
	/**
	 * The number of structural features of the '<em>Xml Query Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_CONTAINER_FEATURE_COUNT = 1;
	/**
	 * The feature id for the '<em><b>All Queries</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_REDIRECTORS__ALL_QUERIES = EclipseLinkOrmV2_0Package.XML_QUERY_REDIRECTORS_20__ALL_QUERIES;
	/**
	 * The feature id for the '<em><b>Read All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_REDIRECTORS__READ_ALL = EclipseLinkOrmV2_0Package.XML_QUERY_REDIRECTORS_20__READ_ALL;
	/**
	 * The feature id for the '<em><b>Read Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_REDIRECTORS__READ_OBJECT = EclipseLinkOrmV2_0Package.XML_QUERY_REDIRECTORS_20__READ_OBJECT;
	/**
	 * The feature id for the '<em><b>Report</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_REDIRECTORS__REPORT = EclipseLinkOrmV2_0Package.XML_QUERY_REDIRECTORS_20__REPORT;
	/**
	 * The feature id for the '<em><b>Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_REDIRECTORS__UPDATE = EclipseLinkOrmV2_0Package.XML_QUERY_REDIRECTORS_20__UPDATE;
	/**
	 * The feature id for the '<em><b>Insert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_REDIRECTORS__INSERT = EclipseLinkOrmV2_0Package.XML_QUERY_REDIRECTORS_20__INSERT;
	/**
	 * The feature id for the '<em><b>Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_REDIRECTORS__DELETE = EclipseLinkOrmV2_0Package.XML_QUERY_REDIRECTORS_20__DELETE;
	/**
	 * The number of structural features of the '<em>Xml Query Redirectors</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_REDIRECTORS_FEATURE_COUNT = EclipseLinkOrmV2_0Package.XML_QUERY_REDIRECTORS_20_FEATURE_COUNT + 0;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlRangePartitioning <em>Xml Range Partitioning</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlRangePartitioning
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlRangePartitioning()
	 * @generated
	 */
	public static final int XML_RANGE_PARTITIONING = 66;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RANGE_PARTITIONING__NAME = EclipseLinkOrmV2_2Package.XML_RANGE_PARTITIONING_22__NAME;
	/**
	 * The number of structural features of the '<em>Xml Range Partitioning</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RANGE_PARTITIONING_FEATURE_COUNT = EclipseLinkOrmV2_2Package.XML_RANGE_PARTITIONING_22_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_READ_ONLY__READ_ONLY = 0;
	/**
	 * The number of structural features of the '<em>Xml Read Only</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_READ_ONLY_FEATURE_COUNT = 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReplicationPartitioning <em>Xml Replication Partitioning</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReplicationPartitioning
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlReplicationPartitioning()
	 * @generated
	 */
	public static final int XML_REPLICATION_PARTITIONING = 68;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_REPLICATION_PARTITIONING__NAME = EclipseLinkOrmV2_2Package.XML_REPLICATION_PARTITIONING_22__NAME;
	/**
	 * The number of structural features of the '<em>Xml Replication Partitioning</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_REPLICATION_PARTITIONING_FEATURE_COUNT = EclipseLinkOrmV2_2Package.XML_REPLICATION_PARTITIONING_22_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Return Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RETURN_INSERT__RETURN_ONLY = EclipseLinkOrmV2_1Package.XML_RETURN_INSERT_21__RETURN_ONLY;
	/**
	 * The number of structural features of the '<em>Xml Return Insert</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_RETURN_INSERT_FEATURE_COUNT = EclipseLinkOrmV2_1Package.XML_RETURN_INSERT_21_FEATURE_COUNT + 0;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlRoundRobinPartitioning <em>Xml Round Robin Partitioning</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlRoundRobinPartitioning
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlRoundRobinPartitioning()
	 * @generated
	 */
	public static final int XML_ROUND_ROBIN_PARTITIONING = 70;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ROUND_ROBIN_PARTITIONING__NAME = EclipseLinkOrmV2_2Package.XML_ROUND_ROBIN_PARTITIONING_22__NAME;
	/**
	 * The number of structural features of the '<em>Xml Round Robin Partitioning</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ROUND_ROBIN_PARTITIONING_FEATURE_COUNT = EclipseLinkOrmV2_2Package.XML_ROUND_ROBIN_PARTITIONING_22_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__NAME = OrmPackage.XML_SECONDARY_TABLE__NAME;
	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__CATALOG = OrmPackage.XML_SECONDARY_TABLE__CATALOG;
	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__SCHEMA = OrmPackage.XML_SECONDARY_TABLE__SCHEMA;
	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__UNIQUE_CONSTRAINTS = OrmPackage.XML_SECONDARY_TABLE__UNIQUE_CONSTRAINTS;
	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS = OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS;
	/**
	 * The feature id for the '<em><b>Creation Suffix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__CREATION_SUFFIX = OrmPackage.XML_SECONDARY_TABLE_FEATURE_COUNT + 0;
	/**
	 * The number of structural features of the '<em>Xml Secondary Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE_FEATURE_COUNT = OrmPackage.XML_SECONDARY_TABLE_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER__DIRECTION = 0;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER__NAME = 1;
	/**
	 * The feature id for the '<em><b>Query Parameter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER__QUERY_PARAMETER = 2;
	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER__TYPE = 3;
	/**
	 * The feature id for the '<em><b>Jdbc Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE = 4;
	/**
	 * The feature id for the '<em><b>Jdbc Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE_NAME = 5;
	/**
	 * The number of structural features of the '<em>Xml Stored Procedure Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STORED_PROCEDURE_PARAMETER_FEATURE_COUNT = 6;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStruct <em>Xml Struct</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStruct
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStruct()
	 * @generated
	 */
	public static final int XML_STRUCT = 73;
	/**
	 * The number of structural features of the '<em>Xml Struct</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCT_FEATURE_COUNT = EclipseLinkOrmV2_3Package.XML_STRUCT_23_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCT_CONVERTER__NAME = XML_NAMED_CONVERTER__NAME;
	/**
	 * The feature id for the '<em><b>Converter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCT_CONVERTER__CONVERTER = XML_NAMED_CONVERTER_FEATURE_COUNT + 0;
	/**
	 * The number of structural features of the '<em>Xml Struct Converter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCT_CONVERTER_FEATURE_COUNT = XML_NAMED_CONVERTER_FEATURE_COUNT + 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructure <em>Xml Structure</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructure
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStructure()
	 * @generated
	 */
	public static final int XML_STRUCTURE = 75;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCTURE__ACCESS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCTURE__NAME = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCTURE__ACCESS_METHODS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCTURE__PROPERTIES = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;
	/**
	 * The number of structural features of the '<em>Xml Structure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCTURE_FEATURE_COUNT = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__NAME = OrmPackage.XML_TABLE__NAME;
	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__CATALOG = OrmPackage.XML_TABLE__CATALOG;
	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__SCHEMA = OrmPackage.XML_TABLE__SCHEMA;
	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__UNIQUE_CONSTRAINTS = OrmPackage.XML_TABLE__UNIQUE_CONSTRAINTS;
	/**
	 * The feature id for the '<em><b>Creation Suffix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__CREATION_SUFFIX = OrmPackage.XML_TABLE_FEATURE_COUNT + 0;
	/**
	 * The number of structural features of the '<em>Xml Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_FEATURE_COUNT = OrmPackage.XML_TABLE_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__DESCRIPTION = OrmPackage.XML_TABLE_GENERATOR__DESCRIPTION;
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
	 * The feature id for the '<em><b>Creation Suffix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__CREATION_SUFFIX = OrmPackage.XML_TABLE_GENERATOR_FEATURE_COUNT + 0;
	/**
	 * The number of structural features of the '<em>Xml Table Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR_FEATURE_COUNT = OrmPackage.XML_TABLE_GENERATOR_FEATURE_COUNT + 1;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn <em>Xml Tenant Discriminator Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTenantDiscriminatorColumn()
	 * @generated
	 */
	public static final int XML_TENANT_DISCRIMINATOR_COLUMN = 78;
	/**
	 * The number of structural features of the '<em>Xml Tenant Discriminator Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TENANT_DISCRIMINATOR_COLUMN_FEATURE_COUNT = EclipseLinkOrmV2_3Package.XML_TENANT_DISCRIMINATOR_COLUMN_23_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Hour</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TIME_OF_DAY__HOUR = 0;
	/**
	 * The feature id for the '<em><b>Minute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TIME_OF_DAY__MINUTE = 1;
	/**
	 * The feature id for the '<em><b>Second</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TIME_OF_DAY__SECOND = 2;
	/**
	 * The feature id for the '<em><b>Millisecond</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TIME_OF_DAY__MILLISECOND = 3;
	/**
	 * The number of structural features of the '<em>Xml Time Of Day</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TIME_OF_DAY_FEATURE_COUNT = 4;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION__ACCESS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION__NAME = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;
	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION__ATTRIBUTE_TYPE = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION__ACCESS_METHODS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION__PROPERTIES = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;
	/**
	 * The number of structural features of the '<em>Xml Transformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSFORMATION_FEATURE_COUNT = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT__ACCESS = OrmPackage.XML_TRANSIENT__ACCESS;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT__NAME = OrmPackage.XML_TRANSIENT__NAME;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT__ACCESS_METHODS = OrmPackage.XML_TRANSIENT_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT__PROPERTIES = OrmPackage.XML_TRANSIENT_FEATURE_COUNT + 1;
	/**
	 * The number of structural features of the '<em>Xml Transient</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT_FEATURE_COUNT = OrmPackage.XML_TRANSIENT_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_CONVERTER__NAME = XML_NAMED_CONVERTER__NAME;
	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_CONVERTER__DATA_TYPE = XML_NAMED_CONVERTER_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Object Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_CONVERTER__OBJECT_TYPE = XML_NAMED_CONVERTER_FEATURE_COUNT + 1;
	/**
	 * The number of structural features of the '<em>Xml Type Converter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_CONVERTER_FEATURE_COUNT = XML_NAMED_CONVERTER_FEATURE_COUNT + 2;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlUnionPartitioning <em>Xml Union Partitioning</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlUnionPartitioning
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlUnionPartitioning()
	 * @generated
	 */
	public static final int XML_UNION_PARTITIONING = 83;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNION_PARTITIONING__NAME = EclipseLinkOrmV2_2Package.XML_UNION_PARTITIONING_22__NAME;
	/**
	 * The number of structural features of the '<em>Xml Union Partitioning</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNION_PARTITIONING_FEATURE_COUNT = EclipseLinkOrmV2_2Package.XML_UNION_PARTITIONING_22_FEATURE_COUNT + 0;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlValuePartitioning <em>Xml Value Partitioning</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlValuePartitioning
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlValuePartitioning()
	 * @generated
	 */
	public static final int XML_VALUE_PARTITIONING = 84;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VALUE_PARTITIONING__NAME = EclipseLinkOrmV2_2Package.XML_VALUE_PARTITIONING_22__NAME;
	/**
	 * The number of structural features of the '<em>Xml Value Partitioning</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VALUE_PARTITIONING_FEATURE_COUNT = EclipseLinkOrmV2_2Package.XML_VALUE_PARTITIONING_22_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__ACCESS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;
	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__NAME = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__ACCESS_METHODS = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__PROPERTIES = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__PARTITIONING = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Replication Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__REPLICATION_PARTITIONING = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Round Robin Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__ROUND_ROBIN_PARTITIONING = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Pinned Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__PINNED_PARTITIONING = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 5;
	/**
	 * The feature id for the '<em><b>Range Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__RANGE_PARTITIONING = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 6;
	/**
	 * The feature id for the '<em><b>Value Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__VALUE_PARTITIONING = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 7;
	/**
	 * The feature id for the '<em><b>Hash Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__HASH_PARTITIONING = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 8;
	/**
	 * The feature id for the '<em><b>Union Partitioning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__UNION_PARTITIONING = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 9;
	/**
	 * The feature id for the '<em><b>Partitioned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__PARTITIONED = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 10;
	/**
	 * The feature id for the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE__NON_CACHEABLE = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 11;
	/**
	 * The number of structural features of the '<em>Xml Variable One To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VARIABLE_ONE_TO_ONE_FEATURE_COUNT = OrmPackage.ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 12;
	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__ACCESS = OrmPackage.XML_VERSION__ACCESS;
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
	 * The feature id for the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__ATTRIBUTE_TYPE = OrmPackage.XML_VERSION_FEATURE_COUNT + 0;
	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__INDEX = OrmPackage.XML_VERSION_FEATURE_COUNT + 1;
	/**
	 * The feature id for the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__ACCESS_METHODS = OrmPackage.XML_VERSION_FEATURE_COUNT + 2;
	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__PROPERTIES = OrmPackage.XML_VERSION_FEATURE_COUNT + 3;
	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__MUTABLE = OrmPackage.XML_VERSION_FEATURE_COUNT + 4;
	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__CONVERTER = OrmPackage.XML_VERSION_FEATURE_COUNT + 5;
	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__TYPE_CONVERTER = OrmPackage.XML_VERSION_FEATURE_COUNT + 6;
	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__OBJECT_TYPE_CONVERTER = OrmPackage.XML_VERSION_FEATURE_COUNT + 7;
	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__STRUCT_CONVERTER = OrmPackage.XML_VERSION_FEATURE_COUNT + 8;
	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__CONVERT = OrmPackage.XML_VERSION_FEATURE_COUNT + 9;
	/**
	 * The number of structural features of the '<em>Xml Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_FEATURE_COUNT = OrmPackage.XML_VERSION_FEATURE_COUNT + 10;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType <em>Cache Coordination Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getCacheCoordinationType()
	 * @generated
	 */
	public static final int CACHE_COORDINATION_TYPE = 87;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheType <em>Cache Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheType
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getCacheType()
	 * @generated
	 */
	public static final int CACHE_TYPE = 88;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingType <em>Xml Change Tracking Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingType
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTrackingType()
	 * @generated
	 */
	public static final int XML_CHANGE_TRACKING_TYPE = 89;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlDirection <em>Xml Direction</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlDirection
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlDirection()
	 * @generated
	 */
	public static final int XML_DIRECTION = 90;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.ExistenceType <em>Existence Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.ExistenceType
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getExistenceType()
	 * @generated
	 */
	public static final int EXISTENCE_TYPE = 91;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetchType <em>Xml Join Fetch Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetchType
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetchType()
	 * @generated
	 */
	public static final int XML_JOIN_FETCH_TYPE = 92;
	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLockingType <em>Xml Optimistic Locking Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLockingType
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOptimisticLockingType()
	 * @generated
	 */
	public static final int XML_OPTIMISTIC_LOCKING_TYPE = 93;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAccessMethodsEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAccessMethodsHolderEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAdditionalCriteriaEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlArrayEClass = null;
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
	private EClass attributesEClass = null;
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
	private EClass xmlBasicCollectionEClass = null;
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
	private EClass xmlBatchFetchEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBatchFetchHolderEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCacheEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCacheHolderEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlChangeTrackingEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlChangeTrackingHolderEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCloneCopyPolicyEClass = null;
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
	private EClass xmlConversionValueEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlConverterEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlConverterHolderEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlConvertersHolderEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlConvertibleMappingEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCopyPolicyEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCustomizerEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCustomizerHolderEClass = null;
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
	private EClass xmlEmbeddableEClass = null;
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
	private EClass xmlEntityEClass = null;
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
	private EClass xmlFetchAttributeEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlFetchGroupEClass = null;
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
	private EClass xmlIndexEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlInstantiationCopyPolicyEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlJoinFetchEClass = null;
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
	private EClass xmlManyToManyEClass = null;
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
	private EClass xmlMappedSuperclassEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMultitenantEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMutableEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedConverterEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedPlsqlStoredFunctionQueryEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedPlsqlStoredProcedureQueryEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedStoredFunctionQueryEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedStoredProcedureQueryEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlObjectTypeConverterEClass = null;
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
	private EClass xmlOptimisticLockingEClass = null;
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
	private EClass xmlPersistenceUnitDefaultsEClass = null;
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
	private EClass xmlPrimaryKeyEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPrivateOwnedEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPropertyEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPropertyContainerEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlQueryContainerEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlQueryRedirectorsEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlReadOnlyEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlReturnInsertEClass = null;
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
	private EClass xmlStoredProcedureParameterEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlStructEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlStructConverterEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlStructureEClass = null;
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
	private EClass xmlTableGeneratorEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTenantDiscriminatorColumnEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTimeOfDayEClass = null;
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
	private EClass xmlTransientEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTypeConverterEClass = null;
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
	private EClass xmlVersionEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlHashPartitioningEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPartitioningEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPinnedPartitioningEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPlsqlRecordEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPlsqlTableEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlRangePartitioningEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlReplicationPartitioningEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlRoundRobinPartitioningEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlUnionPartitioningEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlValuePartitioningEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum cacheCoordinationTypeEEnum = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum cacheTypeEEnum = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum xmlChangeTrackingTypeEEnum = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum xmlDirectionEEnum = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum existenceTypeEEnum = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum xmlJoinFetchTypeEEnum = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum xmlOptimisticLockingTypeEEnum = null;

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EclipseLinkOrmPackage()
	{
		super(eNS_URI, EclipseLinkOrmFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EclipseLinkOrmPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EclipseLinkOrmPackage init()
	{
		if (isInited) return (EclipseLinkOrmPackage)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI);

		// Obtain or create and register package
		EclipseLinkOrmPackage theEclipseLinkOrmPackage = (EclipseLinkOrmPackage)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EclipseLinkOrmPackage ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EclipseLinkOrmPackage());

		isInited = true;

		// Initialize simple dependencies
		OrmPackage.eINSTANCE.eClass();
		CommonPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		EclipseLinkOrmV1_1Package theEclipseLinkOrmV1_1Package = (EclipseLinkOrmV1_1Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI) instanceof EclipseLinkOrmV1_1Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI) : EclipseLinkOrmV1_1Package.eINSTANCE);
		EclipseLinkOrmV2_0Package theEclipseLinkOrmV2_0Package = (EclipseLinkOrmV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) instanceof EclipseLinkOrmV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) : EclipseLinkOrmV2_0Package.eINSTANCE);
		EclipseLinkOrmV2_1Package theEclipseLinkOrmV2_1Package = (EclipseLinkOrmV2_1Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI) instanceof EclipseLinkOrmV2_1Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI) : EclipseLinkOrmV2_1Package.eINSTANCE);
		EclipseLinkOrmV2_2Package theEclipseLinkOrmV2_2Package = (EclipseLinkOrmV2_2Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) instanceof EclipseLinkOrmV2_2Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) : EclipseLinkOrmV2_2Package.eINSTANCE);
		EclipseLinkOrmV2_3Package theEclipseLinkOrmV2_3Package = (EclipseLinkOrmV2_3Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_3Package.eNS_URI) instanceof EclipseLinkOrmV2_3Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_3Package.eNS_URI) : EclipseLinkOrmV2_3Package.eINSTANCE);

		// Create package meta-data objects
		theEclipseLinkOrmPackage.createPackageContents();
		theEclipseLinkOrmV1_1Package.createPackageContents();
		theEclipseLinkOrmV2_0Package.createPackageContents();
		theEclipseLinkOrmV2_1Package.createPackageContents();
		theEclipseLinkOrmV2_2Package.createPackageContents();
		theEclipseLinkOrmV2_3Package.createPackageContents();

		// Initialize created meta-data
		theEclipseLinkOrmPackage.initializePackageContents();
		theEclipseLinkOrmV1_1Package.initializePackageContents();
		theEclipseLinkOrmV2_0Package.initializePackageContents();
		theEclipseLinkOrmV2_1Package.initializePackageContents();
		theEclipseLinkOrmV2_2Package.initializePackageContents();
		theEclipseLinkOrmV2_3Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEclipseLinkOrmPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EclipseLinkOrmPackage.eNS_URI, theEclipseLinkOrmPackage);
		return theEclipseLinkOrmPackage;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods <em>Xml Access Methods</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Access Methods</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods
	 * @generated
	 */
	public EClass getXmlAccessMethods()
	{
		return xmlAccessMethodsEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods#getGetMethod <em>Get Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Get Method</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods#getGetMethod()
	 * @see #getXmlAccessMethods()
	 * @generated
	 */
	public EAttribute getXmlAccessMethods_GetMethod()
	{
		return (EAttribute)xmlAccessMethodsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods#getSetMethod <em>Set Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Set Method</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods#getSetMethod()
	 * @see #getXmlAccessMethods()
	 * @generated
	 */
	public EAttribute getXmlAccessMethods_SetMethod()
	{
		return (EAttribute)xmlAccessMethodsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethodsHolder <em>Xml Access Methods Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Access Methods Holder</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethodsHolder
	 * @generated
	 */
	public EClass getXmlAccessMethodsHolder()
	{
		return xmlAccessMethodsHolderEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethodsHolder#getAccessMethods <em>Access Methods</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Access Methods</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethodsHolder#getAccessMethods()
	 * @see #getXmlAccessMethodsHolder()
	 * @generated
	 */
	public EReference getXmlAccessMethodsHolder_AccessMethods()
	{
		return (EReference)xmlAccessMethodsHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAdditionalCriteria <em>Xml Additional Criteria</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Additional Criteria</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAdditionalCriteria
	 * @generated
	 */
	public EClass getXmlAdditionalCriteria()
	{
		return xmlAdditionalCriteriaEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlArray <em>Xml Array</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Array</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlArray
	 * @generated
	 */
	public EClass getXmlArray()
	{
		return xmlArrayEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Attribute Mapping</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping
	 * @generated
	 */
	public EClass getXmlAttributeMapping()
	{
		return xmlAttributeMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attributes</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes
	 * @generated
	 */
	public EClass getAttributes()
	{
		return attributesEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes#getBasicCollections <em>Basic Collections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Basic Collections</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes#getBasicCollections()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_BasicCollections()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes#getBasicMaps <em>Basic Maps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Basic Maps</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes#getBasicMaps()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_BasicMaps()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes#getTransformations <em>Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transformations</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes#getTransformations()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_Transformations()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes#getVariableOneToOnes <em>Variable One To Ones</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variable One To Ones</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes#getVariableOneToOnes()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_VariableOneToOnes()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic <em>Xml Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic
	 * @generated
	 */
	public EClass getXmlBasic()
	{
		return xmlBasicEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection <em>Xml Basic Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic Collection</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection
	 * @generated
	 */
	public EClass getXmlBasicCollection()
	{
		return xmlBasicCollectionEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicMap <em>Xml Basic Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic Map</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicMap
	 * @generated
	 */
	public EClass getXmlBasicMap()
	{
		return xmlBasicMapEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetch <em>Xml Batch Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Batch Fetch</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetch
	 * @generated
	 */
	public EClass getXmlBatchFetch()
	{
		return xmlBatchFetchEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetchHolder <em>Xml Batch Fetch Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Batch Fetch Holder</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetchHolder
	 * @generated
	 */
	public EClass getXmlBatchFetchHolder()
	{
		return xmlBatchFetchHolderEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetchHolder#getBatchFetch <em>Batch Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Batch Fetch</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetchHolder#getBatchFetch()
	 * @see #getXmlBatchFetchHolder()
	 * @generated
	 */
	public EReference getXmlBatchFetchHolder_BatchFetch()
	{
		return (EReference)xmlBatchFetchHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache <em>Xml Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Cache</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache
	 * @generated
	 */
	public EClass getXmlCache()
	{
		return xmlCacheEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getExpiry <em>Expiry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expiry</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getExpiry()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_Expiry()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getSize()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_Size()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getShared <em>Shared</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Shared</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getShared()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_Shared()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getType()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_Type()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getAlwaysRefresh <em>Always Refresh</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Always Refresh</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getAlwaysRefresh()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_AlwaysRefresh()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getRefreshOnlyIfNewer <em>Refresh Only If Newer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Refresh Only If Newer</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getRefreshOnlyIfNewer()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_RefreshOnlyIfNewer()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getDisableHits <em>Disable Hits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Disable Hits</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getDisableHits()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_DisableHits()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getCoordinationType <em>Coordination Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Coordination Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getCoordinationType()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_CoordinationType()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getExpiryTimeOfDay <em>Expiry Time Of Day</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expiry Time Of Day</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache#getExpiryTimeOfDay()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EReference getXmlCache_ExpiryTimeOfDay()
	{
		return (EReference)xmlCacheEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCacheHolder <em>Xml Cache Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Cache Holder</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCacheHolder
	 * @generated
	 */
	public EClass getXmlCacheHolder()
	{
		return xmlCacheHolderEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCacheHolder#getCache <em>Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCacheHolder#getCache()
	 * @see #getXmlCacheHolder()
	 * @generated
	 */
	public EReference getXmlCacheHolder_Cache()
	{
		return (EReference)xmlCacheHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCacheHolder#getExistenceChecking <em>Existence Checking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Existence Checking</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCacheHolder#getExistenceChecking()
	 * @see #getXmlCacheHolder()
	 * @generated
	 */
	public EAttribute getXmlCacheHolder_ExistenceChecking()
	{
		return (EAttribute)xmlCacheHolderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTracking <em>Xml Change Tracking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Change Tracking</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTracking
	 * @generated
	 */
	public EClass getXmlChangeTracking()
	{
		return xmlChangeTrackingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTracking#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTracking#getType()
	 * @see #getXmlChangeTracking()
	 * @generated
	 */
	public EAttribute getXmlChangeTracking_Type()
	{
		return (EAttribute)xmlChangeTrackingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingHolder <em>Xml Change Tracking Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Change Tracking Holder</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingHolder
	 * @generated
	 */
	public EClass getXmlChangeTrackingHolder()
	{
		return xmlChangeTrackingHolderEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingHolder#getChangeTracking <em>Change Tracking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Change Tracking</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingHolder#getChangeTracking()
	 * @see #getXmlChangeTrackingHolder()
	 * @generated
	 */
	public EReference getXmlChangeTrackingHolder_ChangeTracking()
	{
		return (EReference)xmlChangeTrackingHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCloneCopyPolicy <em>Xml Clone Copy Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Clone Copy Policy</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCloneCopyPolicy
	 * @generated
	 */
	public EClass getXmlCloneCopyPolicy()
	{
		return xmlCloneCopyPolicyEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCloneCopyPolicy#getMethod <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Method</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCloneCopyPolicy#getMethod()
	 * @see #getXmlCloneCopyPolicy()
	 * @generated
	 */
	public EAttribute getXmlCloneCopyPolicy_Method()
	{
		return (EAttribute)xmlCloneCopyPolicyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCloneCopyPolicy#getWorkingCopyMethod <em>Working Copy Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Working Copy Method</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCloneCopyPolicy#getWorkingCopyMethod()
	 * @see #getXmlCloneCopyPolicy()
	 * @generated
	 */
	public EAttribute getXmlCloneCopyPolicy_WorkingCopyMethod()
	{
		return (EAttribute)xmlCloneCopyPolicyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCollectionTable <em>Xml Collection Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Collection Table</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCollectionTable
	 * @generated
	 */
	public EClass getXmlCollectionTable()
	{
		return xmlCollectionTableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConversionValue <em>Xml Conversion Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Conversion Value</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConversionValue
	 * @generated
	 */
	public EClass getXmlConversionValue()
	{
		return xmlConversionValueEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConversionValue#getDataValue <em>Data Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Value</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConversionValue#getDataValue()
	 * @see #getXmlConversionValue()
	 * @generated
	 */
	public EAttribute getXmlConversionValue_DataValue()
	{
		return (EAttribute)xmlConversionValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConversionValue#getObjectValue <em>Object Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Value</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConversionValue#getObjectValue()
	 * @see #getXmlConversionValue()
	 * @generated
	 */
	public EAttribute getXmlConversionValue_ObjectValue()
	{
		return (EAttribute)xmlConversionValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter <em>Xml Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Converter</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter
	 * @generated
	 */
	public EClass getXmlConverter()
	{
		return xmlConverterEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter#getClassName()
	 * @see #getXmlConverter()
	 * @generated
	 */
	public EAttribute getXmlConverter_ClassName()
	{
		return (EAttribute)xmlConverterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder <em>Xml Converter Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Converter Holder</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder
	 * @generated
	 */
	public EClass getXmlConverterHolder()
	{
		return xmlConverterHolderEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder#getConverter <em>Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Converter</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder#getConverter()
	 * @see #getXmlConverterHolder()
	 * @generated
	 */
	public EReference getXmlConverterHolder_Converter()
	{
		return (EReference)xmlConverterHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder#getTypeConverter <em>Type Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type Converter</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder#getTypeConverter()
	 * @see #getXmlConverterHolder()
	 * @generated
	 */
	public EReference getXmlConverterHolder_TypeConverter()
	{
		return (EReference)xmlConverterHolderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder#getObjectTypeConverter <em>Object Type Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Object Type Converter</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder#getObjectTypeConverter()
	 * @see #getXmlConverterHolder()
	 * @generated
	 */
	public EReference getXmlConverterHolder_ObjectTypeConverter()
	{
		return (EReference)xmlConverterHolderEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder#getStructConverter <em>Struct Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Struct Converter</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder#getStructConverter()
	 * @see #getXmlConverterHolder()
	 * @generated
	 */
	public EReference getXmlConverterHolder_StructConverter()
	{
		return (EReference)xmlConverterHolderEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder <em>Xml Converters Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Converters Holder</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder
	 * @generated
	 */
	public EClass getXmlConvertersHolder()
	{
		return xmlConvertersHolderEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder#getConverters <em>Converters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Converters</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder#getConverters()
	 * @see #getXmlConvertersHolder()
	 * @generated
	 */
	public EReference getXmlConvertersHolder_Converters()
	{
		return (EReference)xmlConvertersHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder#getTypeConverters <em>Type Converters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Type Converters</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder#getTypeConverters()
	 * @see #getXmlConvertersHolder()
	 * @generated
	 */
	public EReference getXmlConvertersHolder_TypeConverters()
	{
		return (EReference)xmlConvertersHolderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder#getObjectTypeConverters <em>Object Type Converters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Object Type Converters</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder#getObjectTypeConverters()
	 * @see #getXmlConvertersHolder()
	 * @generated
	 */
	public EReference getXmlConvertersHolder_ObjectTypeConverters()
	{
		return (EReference)xmlConvertersHolderEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder#getStructConverters <em>Struct Converters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Struct Converters</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder#getStructConverters()
	 * @see #getXmlConvertersHolder()
	 * @generated
	 */
	public EReference getXmlConvertersHolder_StructConverters()
	{
		return (EReference)xmlConvertersHolderEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertibleMapping <em>Xml Convertible Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Convertible Mapping</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertibleMapping
	 * @generated
	 */
	public EClass getXmlConvertibleMapping()
	{
		return xmlConvertibleMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertibleMapping#getConvert <em>Convert</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Convert</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertibleMapping#getConvert()
	 * @see #getXmlConvertibleMapping()
	 * @generated
	 */
	public EAttribute getXmlConvertibleMapping_Convert()
	{
		return (EAttribute)xmlConvertibleMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCopyPolicy <em>Xml Copy Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Copy Policy</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCopyPolicy
	 * @generated
	 */
	public EClass getXmlCopyPolicy()
	{
		return xmlCopyPolicyEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCopyPolicy#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCopyPolicy#getClass_()
	 * @see #getXmlCopyPolicy()
	 * @generated
	 */
	public EAttribute getXmlCopyPolicy_Class()
	{
		return (EAttribute)xmlCopyPolicyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizer <em>Xml Customizer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Customizer</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizer
	 * @generated
	 */
	public EClass getXmlCustomizer()
	{
		return xmlCustomizerEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizer#getCustomizerClassName <em>Customizer Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Customizer Class Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizer#getCustomizerClassName()
	 * @see #getXmlCustomizer()
	 * @generated
	 */
	public EAttribute getXmlCustomizer_CustomizerClassName()
	{
		return (EAttribute)xmlCustomizerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizerHolder <em>Xml Customizer Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Customizer Holder</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizerHolder
	 * @generated
	 */
	public EClass getXmlCustomizerHolder()
	{
		return xmlCustomizerHolderEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizerHolder#getCustomizer <em>Customizer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Customizer</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizerHolder#getCustomizer()
	 * @see #getXmlCustomizerHolder()
	 * @generated
	 */
	public EReference getXmlCustomizerHolder_Customizer()
	{
		return (EReference)xmlCustomizerHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlElementCollection <em>Xml Element Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Element Collection</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlElementCollection
	 * @generated
	 */
	public EClass getXmlElementCollection()
	{
		return xmlElementCollectionEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable <em>Xml Embeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embeddable</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable
	 * @generated
	 */
	public EClass getXmlEmbeddable()
	{
		return xmlEmbeddableEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getCopyPolicy <em>Copy Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Copy Policy</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getCopyPolicy()
	 * @see #getXmlEmbeddable()
	 * @generated
	 */
	public EReference getXmlEmbeddable_CopyPolicy()
	{
		return (EReference)xmlEmbeddableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getInstantiationCopyPolicy <em>Instantiation Copy Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Instantiation Copy Policy</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getInstantiationCopyPolicy()
	 * @see #getXmlEmbeddable()
	 * @generated
	 */
	public EReference getXmlEmbeddable_InstantiationCopyPolicy()
	{
		return (EReference)xmlEmbeddableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getCloneCopyPolicy <em>Clone Copy Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Clone Copy Policy</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getCloneCopyPolicy()
	 * @see #getXmlEmbeddable()
	 * @generated
	 */
	public EReference getXmlEmbeddable_CloneCopyPolicy()
	{
		return (EReference)xmlEmbeddableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getExcludeDefaultMappings <em>Exclude Default Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Default Mappings</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getExcludeDefaultMappings()
	 * @see #getXmlEmbeddable()
	 * @generated
	 */
	public EAttribute getXmlEmbeddable_ExcludeDefaultMappings()
	{
		return (EAttribute)xmlEmbeddableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbedded <em>Xml Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbedded
	 * @generated
	 */
	public EClass getXmlEmbedded()
	{
		return xmlEmbeddedEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded Id</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddedId
	 * @generated
	 */
	public EClass getXmlEmbeddedId()
	{
		return xmlEmbeddedIdEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity <em>Xml Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity
	 * @generated
	 */
	public EClass getXmlEntity()
	{
		return xmlEntityEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity#getOptimisticLocking <em>Optimistic Locking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Optimistic Locking</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity#getOptimisticLocking()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_OptimisticLocking()
	{
		return (EReference)xmlEntityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity#getCopyPolicy <em>Copy Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Copy Policy</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity#getCopyPolicy()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_CopyPolicy()
	{
		return (EReference)xmlEntityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity#getInstantiationCopyPolicy <em>Instantiation Copy Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Instantiation Copy Policy</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity#getInstantiationCopyPolicy()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_InstantiationCopyPolicy()
	{
		return (EReference)xmlEntityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity#getCloneCopyPolicy <em>Clone Copy Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Clone Copy Policy</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity#getCloneCopyPolicy()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_CloneCopyPolicy()
	{
		return (EReference)xmlEntityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity#getExcludeDefaultMappings <em>Exclude Default Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Default Mappings</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity#getExcludeDefaultMappings()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EAttribute getXmlEntity_ExcludeDefaultMappings()
	{
		return (EAttribute)xmlEntityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity Mappings</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings
	 * @generated
	 */
	public EClass getXmlEntityMappings()
	{
		return xmlEntityMappingsEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchAttribute <em>Xml Fetch Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Fetch Attribute</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchAttribute
	 * @generated
	 */
	public EClass getXmlFetchAttribute()
	{
		return xmlFetchAttributeEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchGroup <em>Xml Fetch Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Fetch Group</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchGroup
	 * @generated
	 */
	public EClass getXmlFetchGroup()
	{
		return xmlFetchGroupEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId <em>Xml Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId
	 * @generated
	 */
	public EClass getXmlId()
	{
		return xmlIdEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlIndex <em>Xml Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Index</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlIndex
	 * @generated
	 */
	public EClass getXmlIndex()
	{
		return xmlIndexEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlInstantiationCopyPolicy <em>Xml Instantiation Copy Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Instantiation Copy Policy</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlInstantiationCopyPolicy
	 * @generated
	 */
	public EClass getXmlInstantiationCopyPolicy()
	{
		return xmlInstantiationCopyPolicyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetch <em>Xml Join Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Join Fetch</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetch
	 * @generated
	 */
	public EClass getXmlJoinFetch()
	{
		return xmlJoinFetchEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetch#getJoinFetch <em>Join Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Join Fetch</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetch#getJoinFetch()
	 * @see #getXmlJoinFetch()
	 * @generated
	 */
	public EAttribute getXmlJoinFetch_JoinFetch()
	{
		return (EAttribute)xmlJoinFetchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinTable <em>Xml Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Join Table</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinTable
	 * @generated
	 */
	public EClass getXmlJoinTable()
	{
		return xmlJoinTableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToMany
	 * @generated
	 */
	public EClass getXmlManyToMany()
	{
		return xmlManyToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To One</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne
	 * @generated
	 */
	public EClass getXmlManyToOne()
	{
		return xmlManyToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mapped Superclass</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass
	 * @generated
	 */
	public EClass getXmlMappedSuperclass()
	{
		return xmlMappedSuperclassEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass#getOptimisticLocking <em>Optimistic Locking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Optimistic Locking</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass#getOptimisticLocking()
	 * @see #getXmlMappedSuperclass()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_OptimisticLocking()
	{
		return (EReference)xmlMappedSuperclassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass#getCopyPolicy <em>Copy Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Copy Policy</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass#getCopyPolicy()
	 * @see #getXmlMappedSuperclass()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_CopyPolicy()
	{
		return (EReference)xmlMappedSuperclassEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass#getInstantiationCopyPolicy <em>Instantiation Copy Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Instantiation Copy Policy</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass#getInstantiationCopyPolicy()
	 * @see #getXmlMappedSuperclass()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_InstantiationCopyPolicy()
	{
		return (EReference)xmlMappedSuperclassEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass#getCloneCopyPolicy <em>Clone Copy Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Clone Copy Policy</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass#getCloneCopyPolicy()
	 * @see #getXmlMappedSuperclass()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_CloneCopyPolicy()
	{
		return (EReference)xmlMappedSuperclassEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass#getExcludeDefaultMappings <em>Exclude Default Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Default Mappings</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass#getExcludeDefaultMappings()
	 * @see #getXmlMappedSuperclass()
	 * @generated
	 */
	public EAttribute getXmlMappedSuperclass_ExcludeDefaultMappings()
	{
		return (EAttribute)xmlMappedSuperclassEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenant <em>Xml Multitenant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Multitenant</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenant
	 * @generated
	 */
	public EClass getXmlMultitenant()
	{
		return xmlMultitenantEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMutable <em>Xml Mutable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mutable</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMutable
	 * @generated
	 */
	public EClass getXmlMutable()
	{
		return xmlMutableEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMutable#getMutable <em>Mutable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mutable</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMutable#getMutable()
	 * @see #getXmlMutable()
	 * @generated
	 */
	public EAttribute getXmlMutable_Mutable()
	{
		return (EAttribute)xmlMutableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter <em>Xml Named Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Converter</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter
	 * @generated
	 */
	public EClass getXmlNamedConverter()
	{
		return xmlNamedConverterEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter#getName()
	 * @see #getXmlNamedConverter()
	 * @generated
	 */
	public EAttribute getXmlNamedConverter_Name()
	{
		return (EAttribute)xmlNamedConverterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredFunctionQuery <em>Xml Named Plsql Stored Function Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Plsql Stored Function Query</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredFunctionQuery
	 * @generated
	 */
	public EClass getXmlNamedPlsqlStoredFunctionQuery()
	{
		return xmlNamedPlsqlStoredFunctionQueryEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredProcedureQuery <em>Xml Named Plsql Stored Procedure Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Plsql Stored Procedure Query</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredProcedureQuery
	 * @generated
	 */
	public EClass getXmlNamedPlsqlStoredProcedureQuery()
	{
		return xmlNamedPlsqlStoredProcedureQueryEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredFunctionQuery <em>Xml Named Stored Function Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Stored Function Query</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredFunctionQuery
	 * @generated
	 */
	public EClass getXmlNamedStoredFunctionQuery()
	{
		return xmlNamedStoredFunctionQueryEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery <em>Xml Named Stored Procedure Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Stored Procedure Query</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery
	 * @generated
	 */
	public EClass getXmlNamedStoredProcedureQuery()
	{
		return xmlNamedStoredProcedureQueryEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getName()
	 * @see #getXmlNamedStoredProcedureQuery()
	 * @generated
	 */
	public EAttribute getXmlNamedStoredProcedureQuery_Name()
	{
		return (EAttribute)xmlNamedStoredProcedureQueryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getResultClass <em>Result Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Class</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getResultClass()
	 * @see #getXmlNamedStoredProcedureQuery()
	 * @generated
	 */
	public EAttribute getXmlNamedStoredProcedureQuery_ResultClass()
	{
		return (EAttribute)xmlNamedStoredProcedureQueryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getResultSetMapping <em>Result Set Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Set Mapping</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getResultSetMapping()
	 * @see #getXmlNamedStoredProcedureQuery()
	 * @generated
	 */
	public EAttribute getXmlNamedStoredProcedureQuery_ResultSetMapping()
	{
		return (EAttribute)xmlNamedStoredProcedureQueryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getProcedureName <em>Procedure Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Procedure Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getProcedureName()
	 * @see #getXmlNamedStoredProcedureQuery()
	 * @generated
	 */
	public EAttribute getXmlNamedStoredProcedureQuery_ProcedureName()
	{
		return (EAttribute)xmlNamedStoredProcedureQueryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getReturnsResultSet <em>Returns Result Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Returns Result Set</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getReturnsResultSet()
	 * @see #getXmlNamedStoredProcedureQuery()
	 * @generated
	 */
	public EAttribute getXmlNamedStoredProcedureQuery_ReturnsResultSet()
	{
		return (EAttribute)xmlNamedStoredProcedureQueryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getHints <em>Hints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Hints</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getHints()
	 * @see #getXmlNamedStoredProcedureQuery()
	 * @generated
	 */
	public EReference getXmlNamedStoredProcedureQuery_Hints()
	{
		return (EReference)xmlNamedStoredProcedureQueryEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getParameters()
	 * @see #getXmlNamedStoredProcedureQuery()
	 * @generated
	 */
	public EReference getXmlNamedStoredProcedureQuery_Parameters()
	{
		return (EReference)xmlNamedStoredProcedureQueryEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter <em>Xml Object Type Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Object Type Converter</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter
	 * @generated
	 */
	public EClass getXmlObjectTypeConverter()
	{
		return xmlObjectTypeConverterEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter#getDataType()
	 * @see #getXmlObjectTypeConverter()
	 * @generated
	 */
	public EAttribute getXmlObjectTypeConverter_DataType()
	{
		return (EAttribute)xmlObjectTypeConverterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter#getObjectType <em>Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter#getObjectType()
	 * @see #getXmlObjectTypeConverter()
	 * @generated
	 */
	public EAttribute getXmlObjectTypeConverter_ObjectType()
	{
		return (EAttribute)xmlObjectTypeConverterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter#getConversionValues <em>Conversion Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Conversion Values</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter#getConversionValues()
	 * @see #getXmlObjectTypeConverter()
	 * @generated
	 */
	public EReference getXmlObjectTypeConverter_ConversionValues()
	{
		return (EReference)xmlObjectTypeConverterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter#getDefaultObjectValue <em>Default Object Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Object Value</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter#getDefaultObjectValue()
	 * @see #getXmlObjectTypeConverter()
	 * @generated
	 */
	public EAttribute getXmlObjectTypeConverter_DefaultObjectValue()
	{
		return (EAttribute)xmlObjectTypeConverterEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToMany
	 * @generated
	 */
	public EClass getXmlOneToMany()
	{
		return xmlOneToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToOne
	 * @generated
	 */
	public EClass getXmlOneToOne()
	{
		return xmlOneToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLocking <em>Xml Optimistic Locking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Optimistic Locking</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLocking
	 * @generated
	 */
	public EClass getXmlOptimisticLocking()
	{
		return xmlOptimisticLockingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLocking#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLocking#getType()
	 * @see #getXmlOptimisticLocking()
	 * @generated
	 */
	public EAttribute getXmlOptimisticLocking_Type()
	{
		return (EAttribute)xmlOptimisticLockingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLocking#getCascade <em>Cascade</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLocking#getCascade()
	 * @see #getXmlOptimisticLocking()
	 * @generated
	 */
	public EAttribute getXmlOptimisticLocking_Cascade()
	{
		return (EAttribute)xmlOptimisticLockingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLocking#getSelectedColumns <em>Selected Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Selected Columns</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLocking#getSelectedColumns()
	 * @see #getXmlOptimisticLocking()
	 * @generated
	 */
	public EReference getXmlOptimisticLocking_SelectedColumns()
	{
		return (EReference)xmlOptimisticLockingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOrderColumn <em>Xml Order Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Order Column</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOrderColumn
	 * @generated
	 */
	public EClass getXmlOrderColumn()
	{
		return xmlOrderColumnEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitDefaults <em>Xml Persistence Unit Defaults</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence Unit Defaults</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitDefaults
	 * @generated
	 */
	public EClass getXmlPersistenceUnitDefaults()
	{
		return xmlPersistenceUnitDefaultsEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitMetadata <em>Xml Persistence Unit Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence Unit Metadata</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitMetadata
	 * @generated
	 */
	public EClass getXmlPersistenceUnitMetadata()
	{
		return xmlPersistenceUnitMetadataEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitMetadata#isExcludeDefaultMappings <em>Exclude Default Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Default Mappings</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitMetadata#isExcludeDefaultMappings()
	 * @see #getXmlPersistenceUnitMetadata()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnitMetadata_ExcludeDefaultMappings()
	{
		return (EAttribute)xmlPersistenceUnitMetadataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrimaryKey <em>Xml Primary Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Primary Key</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrimaryKey
	 * @generated
	 */
	public EClass getXmlPrimaryKey()
	{
		return xmlPrimaryKeyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrivateOwned <em>Xml Private Owned</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Private Owned</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrivateOwned
	 * @generated
	 */
	public EClass getXmlPrivateOwned()
	{
		return xmlPrivateOwnedEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrivateOwned#isPrivateOwned <em>Private Owned</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Private Owned</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrivateOwned#isPrivateOwned()
	 * @see #getXmlPrivateOwned()
	 * @generated
	 */
	public EAttribute getXmlPrivateOwned_PrivateOwned()
	{
		return (EAttribute)xmlPrivateOwnedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty <em>Xml Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Property</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty
	 * @generated
	 */
	public EClass getXmlProperty()
	{
		return xmlPropertyEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty#getName()
	 * @see #getXmlProperty()
	 * @generated
	 */
	public EAttribute getXmlProperty_Name()
	{
		return (EAttribute)xmlPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty#getValue()
	 * @see #getXmlProperty()
	 * @generated
	 */
	public EAttribute getXmlProperty_Value()
	{
		return (EAttribute)xmlPropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty#getValueType <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty#getValueType()
	 * @see #getXmlProperty()
	 * @generated
	 */
	public EAttribute getXmlProperty_ValueType()
	{
		return (EAttribute)xmlPropertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPropertyContainer <em>Xml Property Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Property Container</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPropertyContainer
	 * @generated
	 */
	public EClass getXmlPropertyContainer()
	{
		return xmlPropertyContainerEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPropertyContainer#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPropertyContainer#getProperties()
	 * @see #getXmlPropertyContainer()
	 * @generated
	 */
	public EReference getXmlPropertyContainer_Properties()
	{
		return (EReference)xmlPropertyContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryContainer <em>Xml Query Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Query Container</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryContainer
	 * @generated
	 */
	public EClass getXmlQueryContainer()
	{
		return xmlQueryContainerEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryContainer#getNamedStoredProcedureQueries <em>Named Stored Procedure Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Stored Procedure Queries</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryContainer#getNamedStoredProcedureQueries()
	 * @see #getXmlQueryContainer()
	 * @generated
	 */
	public EReference getXmlQueryContainer_NamedStoredProcedureQueries()
	{
		return (EReference)xmlQueryContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryRedirectors <em>Xml Query Redirectors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Query Redirectors</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryRedirectors
	 * @generated
	 */
	public EClass getXmlQueryRedirectors()
	{
		return xmlQueryRedirectorsEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReadOnly <em>Xml Read Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Read Only</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReadOnly
	 * @generated
	 */
	public EClass getXmlReadOnly()
	{
		return xmlReadOnlyEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReadOnly#getReadOnly <em>Read Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Read Only</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReadOnly#getReadOnly()
	 * @see #getXmlReadOnly()
	 * @generated
	 */
	public EAttribute getXmlReadOnly_ReadOnly()
	{
		return (EAttribute)xmlReadOnlyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReturnInsert <em>Xml Return Insert</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Return Insert</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReturnInsert
	 * @generated
	 */
	public EClass getXmlReturnInsert()
	{
		return xmlReturnInsertEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlSecondaryTable <em>Xml Secondary Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Secondary Table</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlSecondaryTable
	 * @generated
	 */
	public EClass getXmlSecondaryTable()
	{
		return xmlSecondaryTableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter <em>Xml Stored Procedure Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Stored Procedure Parameter</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter
	 * @generated
	 */
	public EClass getXmlStoredProcedureParameter()
	{
		return xmlStoredProcedureParameterEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getDirection()
	 * @see #getXmlStoredProcedureParameter()
	 * @generated
	 */
	public EAttribute getXmlStoredProcedureParameter_Direction()
	{
		return (EAttribute)xmlStoredProcedureParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getName()
	 * @see #getXmlStoredProcedureParameter()
	 * @generated
	 */
	public EAttribute getXmlStoredProcedureParameter_Name()
	{
		return (EAttribute)xmlStoredProcedureParameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getQueryParameter <em>Query Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Query Parameter</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getQueryParameter()
	 * @see #getXmlStoredProcedureParameter()
	 * @generated
	 */
	public EAttribute getXmlStoredProcedureParameter_QueryParameter()
	{
		return (EAttribute)xmlStoredProcedureParameterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getType()
	 * @see #getXmlStoredProcedureParameter()
	 * @generated
	 */
	public EAttribute getXmlStoredProcedureParameter_Type()
	{
		return (EAttribute)xmlStoredProcedureParameterEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getJdbcType <em>Jdbc Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Jdbc Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getJdbcType()
	 * @see #getXmlStoredProcedureParameter()
	 * @generated
	 */
	public EAttribute getXmlStoredProcedureParameter_JdbcType()
	{
		return (EAttribute)xmlStoredProcedureParameterEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getJdbcTypeName <em>Jdbc Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Jdbc Type Name</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getJdbcTypeName()
	 * @see #getXmlStoredProcedureParameter()
	 * @generated
	 */
	public EAttribute getXmlStoredProcedureParameter_JdbcTypeName()
	{
		return (EAttribute)xmlStoredProcedureParameterEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStruct <em>Xml Struct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Struct</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStruct
	 * @generated
	 */
	public EClass getXmlStruct()
	{
		return xmlStructEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter <em>Xml Struct Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Struct Converter</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter
	 * @generated
	 */
	public EClass getXmlStructConverter()
	{
		return xmlStructConverterEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter#getConverter <em>Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Converter</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter#getConverter()
	 * @see #getXmlStructConverter()
	 * @generated
	 */
	public EAttribute getXmlStructConverter_Converter()
	{
		return (EAttribute)xmlStructConverterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructure <em>Xml Structure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Structure</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructure
	 * @generated
	 */
	public EClass getXmlStructure()
	{
		return xmlStructureEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTable <em>Xml Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Table</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTable
	 * @generated
	 */
	public EClass getXmlTable()
	{
		return xmlTableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTableGenerator <em>Xml Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Table Generator</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTableGenerator
	 * @generated
	 */
	public EClass getXmlTableGenerator()
	{
		return xmlTableGeneratorEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn <em>Xml Tenant Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Tenant Discriminator Column</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn
	 * @generated
	 */
	public EClass getXmlTenantDiscriminatorColumn() {
		return xmlTenantDiscriminatorColumnEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay <em>Xml Time Of Day</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Time Of Day</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay
	 * @generated
	 */
	public EClass getXmlTimeOfDay()
	{
		return xmlTimeOfDayEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay#getHour <em>Hour</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hour</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay#getHour()
	 * @see #getXmlTimeOfDay()
	 * @generated
	 */
	public EAttribute getXmlTimeOfDay_Hour()
	{
		return (EAttribute)xmlTimeOfDayEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay#getMinute <em>Minute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Minute</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay#getMinute()
	 * @see #getXmlTimeOfDay()
	 * @generated
	 */
	public EAttribute getXmlTimeOfDay_Minute()
	{
		return (EAttribute)xmlTimeOfDayEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay#getSecond <em>Second</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Second</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay#getSecond()
	 * @see #getXmlTimeOfDay()
	 * @generated
	 */
	public EAttribute getXmlTimeOfDay_Second()
	{
		return (EAttribute)xmlTimeOfDayEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay#getMillisecond <em>Millisecond</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Millisecond</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay#getMillisecond()
	 * @see #getXmlTimeOfDay()
	 * @generated
	 */
	public EAttribute getXmlTimeOfDay_Millisecond()
	{
		return (EAttribute)xmlTimeOfDayEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransformation <em>Xml Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Transformation</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransformation
	 * @generated
	 */
	public EClass getXmlTransformation()
	{
		return xmlTransformationEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransient <em>Xml Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Transient</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransient
	 * @generated
	 */
	public EClass getXmlTransient()
	{
		return xmlTransientEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter <em>Xml Type Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Type Converter</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter
	 * @generated
	 */
	public EClass getXmlTypeConverter()
	{
		return xmlTypeConverterEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter#getDataType()
	 * @see #getXmlTypeConverter()
	 * @generated
	 */
	public EAttribute getXmlTypeConverter_DataType()
	{
		return (EAttribute)xmlTypeConverterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter#getObjectType <em>Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter#getObjectType()
	 * @see #getXmlTypeConverter()
	 * @generated
	 */
	public EAttribute getXmlTypeConverter_ObjectType()
	{
		return (EAttribute)xmlTypeConverterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVariableOneToOne <em>Xml Variable One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Variable One To One</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVariableOneToOne
	 * @generated
	 */
	public EClass getXmlVariableOneToOne()
	{
		return xmlVariableOneToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Version</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVersion
	 * @generated
	 */
	public EClass getXmlVersion()
	{
		return xmlVersionEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlHashPartitioning <em>Xml Hash Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Hash Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlHashPartitioning
	 * @generated
	 */
	public EClass getXmlHashPartitioning()
	{
		return xmlHashPartitioningEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPartitioning <em>Xml Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPartitioning
	 * @generated
	 */
	public EClass getXmlPartitioning()
	{
		return xmlPartitioningEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPinnedPartitioning <em>Xml Pinned Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Pinned Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPinnedPartitioning
	 * @generated
	 */
	public EClass getXmlPinnedPartitioning()
	{
		return xmlPinnedPartitioningEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlRecord <em>Xml Plsql Record</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Plsql Record</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlRecord
	 * @generated
	 */
	public EClass getXmlPlsqlRecord()
	{
		return xmlPlsqlRecordEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlTable <em>Xml Plsql Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Plsql Table</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlTable
	 * @generated
	 */
	public EClass getXmlPlsqlTable()
	{
		return xmlPlsqlTableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlRangePartitioning <em>Xml Range Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Range Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlRangePartitioning
	 * @generated
	 */
	public EClass getXmlRangePartitioning()
	{
		return xmlRangePartitioningEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReplicationPartitioning <em>Xml Replication Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Replication Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReplicationPartitioning
	 * @generated
	 */
	public EClass getXmlReplicationPartitioning()
	{
		return xmlReplicationPartitioningEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlRoundRobinPartitioning <em>Xml Round Robin Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Round Robin Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlRoundRobinPartitioning
	 * @generated
	 */
	public EClass getXmlRoundRobinPartitioning()
	{
		return xmlRoundRobinPartitioningEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlUnionPartitioning <em>Xml Union Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Union Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlUnionPartitioning
	 * @generated
	 */
	public EClass getXmlUnionPartitioning()
	{
		return xmlUnionPartitioningEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlValuePartitioning <em>Xml Value Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Value Partitioning</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlValuePartitioning
	 * @generated
	 */
	public EClass getXmlValuePartitioning()
	{
		return xmlValuePartitioningEClass;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType <em>Cache Coordination Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Cache Coordination Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType
	 * @generated
	 */
	public EEnum getCacheCoordinationType()
	{
		return cacheCoordinationTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheType <em>Cache Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Cache Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheType
	 * @generated
	 */
	public EEnum getCacheType()
	{
		return cacheTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingType <em>Xml Change Tracking Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Xml Change Tracking Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingType
	 * @generated
	 */
	public EEnum getXmlChangeTrackingType()
	{
		return xmlChangeTrackingTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlDirection <em>Xml Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Xml Direction</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlDirection
	 * @generated
	 */
	public EEnum getXmlDirection()
	{
		return xmlDirectionEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.ExistenceType <em>Existence Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Existence Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.ExistenceType
	 * @generated
	 */
	public EEnum getExistenceType()
	{
		return existenceTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetchType <em>Xml Join Fetch Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Xml Join Fetch Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetchType
	 * @generated
	 */
	public EEnum getXmlJoinFetchType()
	{
		return xmlJoinFetchTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLockingType <em>Xml Optimistic Locking Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Xml Optimistic Locking Type</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLockingType
	 * @generated
	 */
	public EEnum getXmlOptimisticLockingType()
	{
		return xmlOptimisticLockingTypeEEnum;
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public EclipseLinkOrmFactory getEclipseLinkOrmFactory()
	{
		return (EclipseLinkOrmFactory)getEFactoryInstance();
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
		xmlAccessMethodsEClass = createEClass(XML_ACCESS_METHODS);
		createEAttribute(xmlAccessMethodsEClass, XML_ACCESS_METHODS__GET_METHOD);
		createEAttribute(xmlAccessMethodsEClass, XML_ACCESS_METHODS__SET_METHOD);

		xmlAccessMethodsHolderEClass = createEClass(XML_ACCESS_METHODS_HOLDER);
		createEReference(xmlAccessMethodsHolderEClass, XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS);

		xmlAdditionalCriteriaEClass = createEClass(XML_ADDITIONAL_CRITERIA);

		xmlArrayEClass = createEClass(XML_ARRAY);

		xmlAttributeMappingEClass = createEClass(XML_ATTRIBUTE_MAPPING);

		attributesEClass = createEClass(ATTRIBUTES);
		createEReference(attributesEClass, ATTRIBUTES__BASIC_COLLECTIONS);
		createEReference(attributesEClass, ATTRIBUTES__BASIC_MAPS);
		createEReference(attributesEClass, ATTRIBUTES__TRANSFORMATIONS);
		createEReference(attributesEClass, ATTRIBUTES__VARIABLE_ONE_TO_ONES);

		xmlBasicEClass = createEClass(XML_BASIC);

		xmlBasicCollectionEClass = createEClass(XML_BASIC_COLLECTION);

		xmlBasicMapEClass = createEClass(XML_BASIC_MAP);

		xmlBatchFetchEClass = createEClass(XML_BATCH_FETCH);

		xmlBatchFetchHolderEClass = createEClass(XML_BATCH_FETCH_HOLDER);
		createEReference(xmlBatchFetchHolderEClass, XML_BATCH_FETCH_HOLDER__BATCH_FETCH);

		xmlCacheEClass = createEClass(XML_CACHE);
		createEAttribute(xmlCacheEClass, XML_CACHE__EXPIRY);
		createEAttribute(xmlCacheEClass, XML_CACHE__SIZE);
		createEAttribute(xmlCacheEClass, XML_CACHE__SHARED);
		createEAttribute(xmlCacheEClass, XML_CACHE__TYPE);
		createEAttribute(xmlCacheEClass, XML_CACHE__ALWAYS_REFRESH);
		createEAttribute(xmlCacheEClass, XML_CACHE__REFRESH_ONLY_IF_NEWER);
		createEAttribute(xmlCacheEClass, XML_CACHE__DISABLE_HITS);
		createEAttribute(xmlCacheEClass, XML_CACHE__COORDINATION_TYPE);
		createEReference(xmlCacheEClass, XML_CACHE__EXPIRY_TIME_OF_DAY);

		xmlCacheHolderEClass = createEClass(XML_CACHE_HOLDER);
		createEReference(xmlCacheHolderEClass, XML_CACHE_HOLDER__CACHE);
		createEAttribute(xmlCacheHolderEClass, XML_CACHE_HOLDER__EXISTENCE_CHECKING);

		xmlChangeTrackingEClass = createEClass(XML_CHANGE_TRACKING);
		createEAttribute(xmlChangeTrackingEClass, XML_CHANGE_TRACKING__TYPE);

		xmlChangeTrackingHolderEClass = createEClass(XML_CHANGE_TRACKING_HOLDER);
		createEReference(xmlChangeTrackingHolderEClass, XML_CHANGE_TRACKING_HOLDER__CHANGE_TRACKING);

		xmlCloneCopyPolicyEClass = createEClass(XML_CLONE_COPY_POLICY);
		createEAttribute(xmlCloneCopyPolicyEClass, XML_CLONE_COPY_POLICY__METHOD);
		createEAttribute(xmlCloneCopyPolicyEClass, XML_CLONE_COPY_POLICY__WORKING_COPY_METHOD);

		xmlCollectionTableEClass = createEClass(XML_COLLECTION_TABLE);

		xmlConversionValueEClass = createEClass(XML_CONVERSION_VALUE);
		createEAttribute(xmlConversionValueEClass, XML_CONVERSION_VALUE__DATA_VALUE);
		createEAttribute(xmlConversionValueEClass, XML_CONVERSION_VALUE__OBJECT_VALUE);

		xmlConverterEClass = createEClass(XML_CONVERTER);
		createEAttribute(xmlConverterEClass, XML_CONVERTER__CLASS_NAME);

		xmlConverterHolderEClass = createEClass(XML_CONVERTER_HOLDER);
		createEReference(xmlConverterHolderEClass, XML_CONVERTER_HOLDER__CONVERTER);
		createEReference(xmlConverterHolderEClass, XML_CONVERTER_HOLDER__TYPE_CONVERTER);
		createEReference(xmlConverterHolderEClass, XML_CONVERTER_HOLDER__OBJECT_TYPE_CONVERTER);
		createEReference(xmlConverterHolderEClass, XML_CONVERTER_HOLDER__STRUCT_CONVERTER);

		xmlConvertersHolderEClass = createEClass(XML_CONVERTERS_HOLDER);
		createEReference(xmlConvertersHolderEClass, XML_CONVERTERS_HOLDER__CONVERTERS);
		createEReference(xmlConvertersHolderEClass, XML_CONVERTERS_HOLDER__TYPE_CONVERTERS);
		createEReference(xmlConvertersHolderEClass, XML_CONVERTERS_HOLDER__OBJECT_TYPE_CONVERTERS);
		createEReference(xmlConvertersHolderEClass, XML_CONVERTERS_HOLDER__STRUCT_CONVERTERS);

		xmlConvertibleMappingEClass = createEClass(XML_CONVERTIBLE_MAPPING);
		createEAttribute(xmlConvertibleMappingEClass, XML_CONVERTIBLE_MAPPING__CONVERT);

		xmlCopyPolicyEClass = createEClass(XML_COPY_POLICY);
		createEAttribute(xmlCopyPolicyEClass, XML_COPY_POLICY__CLASS);

		xmlCustomizerEClass = createEClass(XML_CUSTOMIZER);
		createEAttribute(xmlCustomizerEClass, XML_CUSTOMIZER__CUSTOMIZER_CLASS_NAME);

		xmlCustomizerHolderEClass = createEClass(XML_CUSTOMIZER_HOLDER);
		createEReference(xmlCustomizerHolderEClass, XML_CUSTOMIZER_HOLDER__CUSTOMIZER);

		xmlElementCollectionEClass = createEClass(XML_ELEMENT_COLLECTION);

		xmlEmbeddableEClass = createEClass(XML_EMBEDDABLE);
		createEReference(xmlEmbeddableEClass, XML_EMBEDDABLE__COPY_POLICY);
		createEReference(xmlEmbeddableEClass, XML_EMBEDDABLE__INSTANTIATION_COPY_POLICY);
		createEReference(xmlEmbeddableEClass, XML_EMBEDDABLE__CLONE_COPY_POLICY);
		createEAttribute(xmlEmbeddableEClass, XML_EMBEDDABLE__EXCLUDE_DEFAULT_MAPPINGS);

		xmlEmbeddedEClass = createEClass(XML_EMBEDDED);

		xmlEmbeddedIdEClass = createEClass(XML_EMBEDDED_ID);

		xmlEntityEClass = createEClass(XML_ENTITY);
		createEReference(xmlEntityEClass, XML_ENTITY__OPTIMISTIC_LOCKING);
		createEReference(xmlEntityEClass, XML_ENTITY__COPY_POLICY);
		createEReference(xmlEntityEClass, XML_ENTITY__INSTANTIATION_COPY_POLICY);
		createEReference(xmlEntityEClass, XML_ENTITY__CLONE_COPY_POLICY);
		createEAttribute(xmlEntityEClass, XML_ENTITY__EXCLUDE_DEFAULT_MAPPINGS);

		xmlEntityMappingsEClass = createEClass(XML_ENTITY_MAPPINGS);

		xmlFetchAttributeEClass = createEClass(XML_FETCH_ATTRIBUTE);

		xmlFetchGroupEClass = createEClass(XML_FETCH_GROUP);

		xmlHashPartitioningEClass = createEClass(XML_HASH_PARTITIONING);

		xmlIdEClass = createEClass(XML_ID);

		xmlIndexEClass = createEClass(XML_INDEX);

		xmlInstantiationCopyPolicyEClass = createEClass(XML_INSTANTIATION_COPY_POLICY);

		xmlJoinFetchEClass = createEClass(XML_JOIN_FETCH);
		createEAttribute(xmlJoinFetchEClass, XML_JOIN_FETCH__JOIN_FETCH);

		xmlJoinTableEClass = createEClass(XML_JOIN_TABLE);

		xmlManyToManyEClass = createEClass(XML_MANY_TO_MANY);

		xmlManyToOneEClass = createEClass(XML_MANY_TO_ONE);

		xmlMappedSuperclassEClass = createEClass(XML_MAPPED_SUPERCLASS);
		createEReference(xmlMappedSuperclassEClass, XML_MAPPED_SUPERCLASS__OPTIMISTIC_LOCKING);
		createEReference(xmlMappedSuperclassEClass, XML_MAPPED_SUPERCLASS__COPY_POLICY);
		createEReference(xmlMappedSuperclassEClass, XML_MAPPED_SUPERCLASS__INSTANTIATION_COPY_POLICY);
		createEReference(xmlMappedSuperclassEClass, XML_MAPPED_SUPERCLASS__CLONE_COPY_POLICY);
		createEAttribute(xmlMappedSuperclassEClass, XML_MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_MAPPINGS);

		xmlMultitenantEClass = createEClass(XML_MULTITENANT);

		xmlMutableEClass = createEClass(XML_MUTABLE);
		createEAttribute(xmlMutableEClass, XML_MUTABLE__MUTABLE);

		xmlNamedConverterEClass = createEClass(XML_NAMED_CONVERTER);
		createEAttribute(xmlNamedConverterEClass, XML_NAMED_CONVERTER__NAME);

		xmlNamedPlsqlStoredFunctionQueryEClass = createEClass(XML_NAMED_PLSQL_STORED_FUNCTION_QUERY);

		xmlNamedPlsqlStoredProcedureQueryEClass = createEClass(XML_NAMED_PLSQL_STORED_PROCEDURE_QUERY);

		xmlNamedStoredFunctionQueryEClass = createEClass(XML_NAMED_STORED_FUNCTION_QUERY);

		xmlNamedStoredProcedureQueryEClass = createEClass(XML_NAMED_STORED_PROCEDURE_QUERY);
		createEAttribute(xmlNamedStoredProcedureQueryEClass, XML_NAMED_STORED_PROCEDURE_QUERY__NAME);
		createEAttribute(xmlNamedStoredProcedureQueryEClass, XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASS);
		createEAttribute(xmlNamedStoredProcedureQueryEClass, XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPING);
		createEAttribute(xmlNamedStoredProcedureQueryEClass, XML_NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME);
		createEAttribute(xmlNamedStoredProcedureQueryEClass, XML_NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET);
		createEReference(xmlNamedStoredProcedureQueryEClass, XML_NAMED_STORED_PROCEDURE_QUERY__HINTS);
		createEReference(xmlNamedStoredProcedureQueryEClass, XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS);

		xmlObjectTypeConverterEClass = createEClass(XML_OBJECT_TYPE_CONVERTER);
		createEAttribute(xmlObjectTypeConverterEClass, XML_OBJECT_TYPE_CONVERTER__DATA_TYPE);
		createEAttribute(xmlObjectTypeConverterEClass, XML_OBJECT_TYPE_CONVERTER__OBJECT_TYPE);
		createEReference(xmlObjectTypeConverterEClass, XML_OBJECT_TYPE_CONVERTER__CONVERSION_VALUES);
		createEAttribute(xmlObjectTypeConverterEClass, XML_OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE);

		xmlOneToManyEClass = createEClass(XML_ONE_TO_MANY);

		xmlOneToOneEClass = createEClass(XML_ONE_TO_ONE);

		xmlOptimisticLockingEClass = createEClass(XML_OPTIMISTIC_LOCKING);
		createEAttribute(xmlOptimisticLockingEClass, XML_OPTIMISTIC_LOCKING__TYPE);
		createEAttribute(xmlOptimisticLockingEClass, XML_OPTIMISTIC_LOCKING__CASCADE);
		createEReference(xmlOptimisticLockingEClass, XML_OPTIMISTIC_LOCKING__SELECTED_COLUMNS);

		xmlOrderColumnEClass = createEClass(XML_ORDER_COLUMN);

		xmlPartitioningEClass = createEClass(XML_PARTITIONING);

		xmlPersistenceUnitDefaultsEClass = createEClass(XML_PERSISTENCE_UNIT_DEFAULTS);

		xmlPersistenceUnitMetadataEClass = createEClass(XML_PERSISTENCE_UNIT_METADATA);
		createEAttribute(xmlPersistenceUnitMetadataEClass, XML_PERSISTENCE_UNIT_METADATA__EXCLUDE_DEFAULT_MAPPINGS);

		xmlPinnedPartitioningEClass = createEClass(XML_PINNED_PARTITIONING);

		xmlPlsqlRecordEClass = createEClass(XML_PLSQL_RECORD);

		xmlPlsqlTableEClass = createEClass(XML_PLSQL_TABLE);

		xmlPrimaryKeyEClass = createEClass(XML_PRIMARY_KEY);

		xmlPrivateOwnedEClass = createEClass(XML_PRIVATE_OWNED);
		createEAttribute(xmlPrivateOwnedEClass, XML_PRIVATE_OWNED__PRIVATE_OWNED);

		xmlPropertyEClass = createEClass(XML_PROPERTY);
		createEAttribute(xmlPropertyEClass, XML_PROPERTY__NAME);
		createEAttribute(xmlPropertyEClass, XML_PROPERTY__VALUE);
		createEAttribute(xmlPropertyEClass, XML_PROPERTY__VALUE_TYPE);

		xmlPropertyContainerEClass = createEClass(XML_PROPERTY_CONTAINER);
		createEReference(xmlPropertyContainerEClass, XML_PROPERTY_CONTAINER__PROPERTIES);

		xmlQueryContainerEClass = createEClass(XML_QUERY_CONTAINER);
		createEReference(xmlQueryContainerEClass, XML_QUERY_CONTAINER__NAMED_STORED_PROCEDURE_QUERIES);

		xmlQueryRedirectorsEClass = createEClass(XML_QUERY_REDIRECTORS);

		xmlRangePartitioningEClass = createEClass(XML_RANGE_PARTITIONING);

		xmlReadOnlyEClass = createEClass(XML_READ_ONLY);
		createEAttribute(xmlReadOnlyEClass, XML_READ_ONLY__READ_ONLY);

		xmlReplicationPartitioningEClass = createEClass(XML_REPLICATION_PARTITIONING);

		xmlReturnInsertEClass = createEClass(XML_RETURN_INSERT);

		xmlRoundRobinPartitioningEClass = createEClass(XML_ROUND_ROBIN_PARTITIONING);

		xmlSecondaryTableEClass = createEClass(XML_SECONDARY_TABLE);

		xmlStoredProcedureParameterEClass = createEClass(XML_STORED_PROCEDURE_PARAMETER);
		createEAttribute(xmlStoredProcedureParameterEClass, XML_STORED_PROCEDURE_PARAMETER__DIRECTION);
		createEAttribute(xmlStoredProcedureParameterEClass, XML_STORED_PROCEDURE_PARAMETER__NAME);
		createEAttribute(xmlStoredProcedureParameterEClass, XML_STORED_PROCEDURE_PARAMETER__QUERY_PARAMETER);
		createEAttribute(xmlStoredProcedureParameterEClass, XML_STORED_PROCEDURE_PARAMETER__TYPE);
		createEAttribute(xmlStoredProcedureParameterEClass, XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE);
		createEAttribute(xmlStoredProcedureParameterEClass, XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE_NAME);

		xmlStructEClass = createEClass(XML_STRUCT);

		xmlStructConverterEClass = createEClass(XML_STRUCT_CONVERTER);
		createEAttribute(xmlStructConverterEClass, XML_STRUCT_CONVERTER__CONVERTER);

		xmlStructureEClass = createEClass(XML_STRUCTURE);

		xmlTableEClass = createEClass(XML_TABLE);

		xmlTableGeneratorEClass = createEClass(XML_TABLE_GENERATOR);

		xmlTenantDiscriminatorColumnEClass = createEClass(XML_TENANT_DISCRIMINATOR_COLUMN);

		xmlTimeOfDayEClass = createEClass(XML_TIME_OF_DAY);
		createEAttribute(xmlTimeOfDayEClass, XML_TIME_OF_DAY__HOUR);
		createEAttribute(xmlTimeOfDayEClass, XML_TIME_OF_DAY__MINUTE);
		createEAttribute(xmlTimeOfDayEClass, XML_TIME_OF_DAY__SECOND);
		createEAttribute(xmlTimeOfDayEClass, XML_TIME_OF_DAY__MILLISECOND);

		xmlTransformationEClass = createEClass(XML_TRANSFORMATION);

		xmlTransientEClass = createEClass(XML_TRANSIENT);

		xmlTypeConverterEClass = createEClass(XML_TYPE_CONVERTER);
		createEAttribute(xmlTypeConverterEClass, XML_TYPE_CONVERTER__DATA_TYPE);
		createEAttribute(xmlTypeConverterEClass, XML_TYPE_CONVERTER__OBJECT_TYPE);

		xmlUnionPartitioningEClass = createEClass(XML_UNION_PARTITIONING);

		xmlValuePartitioningEClass = createEClass(XML_VALUE_PARTITIONING);

		xmlVariableOneToOneEClass = createEClass(XML_VARIABLE_ONE_TO_ONE);

		xmlVersionEClass = createEClass(XML_VERSION);

		// Create enums
		cacheCoordinationTypeEEnum = createEEnum(CACHE_COORDINATION_TYPE);
		cacheTypeEEnum = createEEnum(CACHE_TYPE);
		xmlChangeTrackingTypeEEnum = createEEnum(XML_CHANGE_TRACKING_TYPE);
		xmlDirectionEEnum = createEEnum(XML_DIRECTION);
		existenceTypeEEnum = createEEnum(EXISTENCE_TYPE);
		xmlJoinFetchTypeEEnum = createEEnum(XML_JOIN_FETCH_TYPE);
		xmlOptimisticLockingTypeEEnum = createEEnum(XML_OPTIMISTIC_LOCKING_TYPE);
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
		EclipseLinkOrmV1_1Package theEclipseLinkOrmV1_1Package = (EclipseLinkOrmV1_1Package)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI);
		EclipseLinkOrmV2_0Package theEclipseLinkOrmV2_0Package = (EclipseLinkOrmV2_0Package)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI);
		EclipseLinkOrmV2_1Package theEclipseLinkOrmV2_1Package = (EclipseLinkOrmV2_1Package)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI);
		EclipseLinkOrmV2_2Package theEclipseLinkOrmV2_2Package = (EclipseLinkOrmV2_2Package)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI);
		EclipseLinkOrmV2_3Package theEclipseLinkOrmV2_3Package = (EclipseLinkOrmV2_3Package)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_3Package.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
		OrmPackage theOrmPackage = (OrmPackage)EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theEclipseLinkOrmV1_1Package);
		getESubpackages().add(theEclipseLinkOrmV2_0Package);
		getESubpackages().add(theEclipseLinkOrmV2_1Package);
		getESubpackages().add(theEclipseLinkOrmV2_2Package);
		getESubpackages().add(theEclipseLinkOrmV2_3Package);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		xmlAdditionalCriteriaEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlAdditionalCriteria_2_2());
		xmlArrayEClass.getESuperTypes().add(theOrmPackage.getAbstractXmlAttributeMapping());
		xmlArrayEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlArrayEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlArray_2_3());
		xmlAttributeMappingEClass.getESuperTypes().add(theOrmPackage.getXmlAttributeMapping());
		xmlAttributeMappingEClass.getESuperTypes().add(this.getXmlAccessMethodsHolder());
		xmlAttributeMappingEClass.getESuperTypes().add(this.getXmlPropertyContainer());
		attributesEClass.getESuperTypes().add(theOrmPackage.getAttributes());
		attributesEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlAttributes_2_3());
		xmlBasicEClass.getESuperTypes().add(theOrmPackage.getXmlBasic());
		xmlBasicEClass.getESuperTypes().add(theEclipseLinkOrmV1_1Package.getXmlBasic_1_1());
		xmlBasicEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlBasic_2_1());
		xmlBasicEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlBasicEClass.getESuperTypes().add(this.getXmlMutable());
		xmlBasicEClass.getESuperTypes().add(this.getXmlConvertibleMapping());
		xmlBasicCollectionEClass.getESuperTypes().add(theOrmPackage.getAbstractXmlAttributeMapping());
		xmlBasicCollectionEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlBasicCollectionEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlBasicCollection_2_2());
		xmlBasicMapEClass.getESuperTypes().add(theOrmPackage.getAbstractXmlAttributeMapping());
		xmlBasicMapEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlBasicMapEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlBasicMap_2_2());
		xmlBatchFetchEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlBatchFetch_2_1());
		xmlCollectionTableEClass.getESuperTypes().add(theOrmPackage.getXmlCollectionTable());
		xmlCollectionTableEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlCollectionTable_2_2());
		xmlConverterEClass.getESuperTypes().add(this.getXmlNamedConverter());
		xmlConvertibleMappingEClass.getESuperTypes().add(theOrmPackage.getXmlConvertibleMapping());
		xmlConvertibleMappingEClass.getESuperTypes().add(this.getXmlConverterHolder());
		xmlElementCollectionEClass.getESuperTypes().add(theOrmPackage.getXmlElementCollection());
		xmlElementCollectionEClass.getESuperTypes().add(theEclipseLinkOrmV2_0Package.getXmlElementCollection_2_0());
		xmlElementCollectionEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlElementCollection_2_1());
		xmlElementCollectionEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlElementCollection_2_2());
		xmlElementCollectionEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlElementCollection_2_3());
		xmlEmbeddableEClass.getESuperTypes().add(theOrmPackage.getXmlEmbeddable());
		xmlEmbeddableEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlEmbeddable_2_1());
		xmlEmbeddableEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlEmbeddable_2_2());
		xmlEmbeddableEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlEmbeddable_2_3());
		xmlEmbeddableEClass.getESuperTypes().add(this.getXmlCustomizerHolder());
		xmlEmbeddableEClass.getESuperTypes().add(this.getXmlChangeTrackingHolder());
		xmlEmbeddableEClass.getESuperTypes().add(this.getXmlConvertersHolder());
		xmlEmbeddableEClass.getESuperTypes().add(this.getXmlPropertyContainer());
		xmlEmbeddedEClass.getESuperTypes().add(theOrmPackage.getXmlEmbedded());
		xmlEmbeddedEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlEmbedded_2_1());
		xmlEmbeddedEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlEmbeddedIdEClass.getESuperTypes().add(theOrmPackage.getXmlEmbeddedId());
		xmlEmbeddedIdEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlEmbeddedId_2_1());
		xmlEmbeddedIdEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlEntityEClass.getESuperTypes().add(theOrmPackage.getXmlEntity());
		xmlEntityEClass.getESuperTypes().add(theEclipseLinkOrmV1_1Package.getXmlEntity_1_1());
		xmlEntityEClass.getESuperTypes().add(theEclipseLinkOrmV2_0Package.getXmlEntity_2_0());
		xmlEntityEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlEntity_2_1());
		xmlEntityEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlEntity_2_2());
		xmlEntityEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlEntity_2_3());
		xmlEntityEClass.getESuperTypes().add(this.getXmlReadOnly());
		xmlEntityEClass.getESuperTypes().add(this.getXmlCustomizerHolder());
		xmlEntityEClass.getESuperTypes().add(this.getXmlChangeTrackingHolder());
		xmlEntityEClass.getESuperTypes().add(this.getXmlCacheHolder());
		xmlEntityEClass.getESuperTypes().add(this.getXmlConvertersHolder());
		xmlEntityEClass.getESuperTypes().add(this.getXmlQueryContainer());
		xmlEntityEClass.getESuperTypes().add(this.getXmlPropertyContainer());
		xmlEntityMappingsEClass.getESuperTypes().add(theOrmPackage.getXmlEntityMappings());
		xmlEntityMappingsEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlEntityMappings_2_1());
		xmlEntityMappingsEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlEntityMappings_2_2());
		xmlEntityMappingsEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlEntityMappings_2_3());
		xmlEntityMappingsEClass.getESuperTypes().add(this.getXmlConvertersHolder());
		xmlEntityMappingsEClass.getESuperTypes().add(this.getXmlQueryContainer());
		xmlFetchAttributeEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlFetchAttribute_2_1());
		xmlFetchGroupEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlFetchGroup_2_1());
		xmlHashPartitioningEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlHashPartitioning_2_2());
		xmlIdEClass.getESuperTypes().add(theOrmPackage.getXmlId());
		xmlIdEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlId_2_1());
		xmlIdEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlId_2_2());
		xmlIdEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlIdEClass.getESuperTypes().add(this.getXmlMutable());
		xmlIdEClass.getESuperTypes().add(this.getXmlConvertibleMapping());
		xmlIndexEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlIndex_2_2());
		xmlJoinTableEClass.getESuperTypes().add(theOrmPackage.getXmlJoinTable());
		xmlJoinTableEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlJoinTable_2_2());
		xmlManyToManyEClass.getESuperTypes().add(theOrmPackage.getXmlManyToMany());
		xmlManyToManyEClass.getESuperTypes().add(theEclipseLinkOrmV2_0Package.getXmlManyToMany_2_0());
		xmlManyToManyEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlManyToMany_2_1());
		xmlManyToManyEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlManyToMany_2_2());
		xmlManyToManyEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlManyToManyEClass.getESuperTypes().add(this.getXmlJoinFetch());
		xmlManyToOneEClass.getESuperTypes().add(theOrmPackage.getXmlManyToOne());
		xmlManyToOneEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlManyToOne_2_1());
		xmlManyToOneEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlManyToOne_2_2());
		xmlManyToOneEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlManyToOneEClass.getESuperTypes().add(this.getXmlJoinFetch());
		xmlMappedSuperclassEClass.getESuperTypes().add(theOrmPackage.getXmlMappedSuperclass());
		xmlMappedSuperclassEClass.getESuperTypes().add(theEclipseLinkOrmV1_1Package.getXmlMappedSuperclass_1_1());
		xmlMappedSuperclassEClass.getESuperTypes().add(theEclipseLinkOrmV2_0Package.getXmlMappedSuperclass_2_0());
		xmlMappedSuperclassEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlMappedSuperclass_2_1());
		xmlMappedSuperclassEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlMappedSuperclass_2_2());
		xmlMappedSuperclassEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlMappedSuperclass_2_3());
		xmlMappedSuperclassEClass.getESuperTypes().add(this.getXmlReadOnly());
		xmlMappedSuperclassEClass.getESuperTypes().add(this.getXmlCustomizerHolder());
		xmlMappedSuperclassEClass.getESuperTypes().add(this.getXmlChangeTrackingHolder());
		xmlMappedSuperclassEClass.getESuperTypes().add(this.getXmlCacheHolder());
		xmlMappedSuperclassEClass.getESuperTypes().add(this.getXmlConvertersHolder());
		xmlMappedSuperclassEClass.getESuperTypes().add(this.getXmlPropertyContainer());
		xmlMultitenantEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlMultitenant_2_3());
		xmlNamedPlsqlStoredFunctionQueryEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlNamedPlsqlStoredFunctionQuery_2_3());
		xmlNamedPlsqlStoredProcedureQueryEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlNamedPlsqlStoredProcedureQuery_2_3());
		xmlNamedStoredFunctionQueryEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlNamedStoredFunctionQuery_2_3());
		xmlObjectTypeConverterEClass.getESuperTypes().add(this.getXmlNamedConverter());
		xmlOneToManyEClass.getESuperTypes().add(theOrmPackage.getXmlOneToMany());
		xmlOneToManyEClass.getESuperTypes().add(theEclipseLinkOrmV2_0Package.getXmlOneToMany_2_0());
		xmlOneToManyEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlOneToMany_2_1());
		xmlOneToManyEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlOneToMany_2_2());
		xmlOneToManyEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlOneToManyEClass.getESuperTypes().add(this.getXmlPrivateOwned());
		xmlOneToManyEClass.getESuperTypes().add(this.getXmlJoinFetch());
		xmlOneToOneEClass.getESuperTypes().add(theOrmPackage.getXmlOneToOne());
		xmlOneToOneEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlOneToOne_2_1());
		xmlOneToOneEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlOneToOne_2_2());
		xmlOneToOneEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlOneToOneEClass.getESuperTypes().add(this.getXmlPrivateOwned());
		xmlOneToOneEClass.getESuperTypes().add(this.getXmlJoinFetch());
		xmlOrderColumnEClass.getESuperTypes().add(theOrmPackage.getXmlOrderColumn());
		xmlOrderColumnEClass.getESuperTypes().add(theEclipseLinkOrmV2_0Package.getXmlOrderColumn_2_0());
		xmlPartitioningEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlPartitioning_2_2());
		xmlPersistenceUnitDefaultsEClass.getESuperTypes().add(theOrmPackage.getXmlPersistenceUnitDefaults());
		xmlPersistenceUnitDefaultsEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlPersistenceUnitDefaults_2_1());
		xmlPersistenceUnitDefaultsEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlPersistenceUnitDefaults_2_3());
		xmlPersistenceUnitMetadataEClass.getESuperTypes().add(theOrmPackage.getXmlPersistenceUnitMetadata());
		xmlPinnedPartitioningEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlPinnedPartitioning_2_2());
		xmlPlsqlRecordEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlPlsqlRecord_2_3());
		xmlPlsqlTableEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlPlsqlTable_2_3());
		xmlPrimaryKeyEClass.getESuperTypes().add(theEclipseLinkOrmV1_1Package.getXmlPrimaryKey_1_1());
		xmlPrimaryKeyEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlPrimaryKey_2_1());
		xmlQueryRedirectorsEClass.getESuperTypes().add(theEclipseLinkOrmV2_0Package.getXmlQueryRedirectors_2_0());
		xmlRangePartitioningEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlRangePartitioning_2_2());
		xmlReplicationPartitioningEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlReplicationPartitioning_2_2());
		xmlReturnInsertEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlReturnInsert_2_1());
		xmlRoundRobinPartitioningEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlRoundRobinPartitioning_2_2());
		xmlSecondaryTableEClass.getESuperTypes().add(theOrmPackage.getXmlSecondaryTable());
		xmlSecondaryTableEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlSecondaryTable_2_2());
		xmlStructEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlStruct_2_3());
		xmlStructConverterEClass.getESuperTypes().add(this.getXmlNamedConverter());
		xmlStructureEClass.getESuperTypes().add(theOrmPackage.getAbstractXmlAttributeMapping());
		xmlStructureEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlStructureEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlStructure_2_3());
		xmlTableEClass.getESuperTypes().add(theOrmPackage.getXmlTable());
		xmlTableEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlTable_2_2());
		xmlTableGeneratorEClass.getESuperTypes().add(theOrmPackage.getXmlTableGenerator());
		xmlTableGeneratorEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlTableGenerator_2_2());
		xmlTenantDiscriminatorColumnEClass.getESuperTypes().add(theEclipseLinkOrmV2_3Package.getXmlTenantDiscriminatorColumn_2_3());
		xmlTransformationEClass.getESuperTypes().add(theOrmPackage.getAbstractXmlAttributeMapping());
		xmlTransformationEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlTransformation_2_1());
		xmlTransformationEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlTransientEClass.getESuperTypes().add(theOrmPackage.getXmlTransient());
		xmlTransientEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlTypeConverterEClass.getESuperTypes().add(this.getXmlNamedConverter());
		xmlUnionPartitioningEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlUnionPartitioning_2_2());
		xmlValuePartitioningEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlValuePartitioning_2_2());
		xmlVariableOneToOneEClass.getESuperTypes().add(theOrmPackage.getAbstractXmlAttributeMapping());
		xmlVariableOneToOneEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlVariableOneToOneEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlVariableOneToOne_2_2());
		xmlVersionEClass.getESuperTypes().add(theOrmPackage.getXmlVersion());
		xmlVersionEClass.getESuperTypes().add(theEclipseLinkOrmV2_1Package.getXmlVersion_2_1());
		xmlVersionEClass.getESuperTypes().add(theEclipseLinkOrmV2_2Package.getXmlVersion_2_2());
		xmlVersionEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		xmlVersionEClass.getESuperTypes().add(this.getXmlMutable());
		xmlVersionEClass.getESuperTypes().add(this.getXmlConvertibleMapping());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlAccessMethodsEClass, XmlAccessMethods.class, "XmlAccessMethods", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlAccessMethods_GetMethod(), theXMLTypePackage.getString(), "getMethod", null, 0, 1, XmlAccessMethods.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlAccessMethods_SetMethod(), theXMLTypePackage.getString(), "setMethod", null, 0, 1, XmlAccessMethods.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlAccessMethodsHolderEClass, XmlAccessMethodsHolder.class, "XmlAccessMethodsHolder", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlAccessMethodsHolder_AccessMethods(), this.getXmlAccessMethods(), null, "accessMethods", null, 0, 1, XmlAccessMethodsHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlAdditionalCriteriaEClass, XmlAdditionalCriteria.class, "XmlAdditionalCriteria", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlArrayEClass, XmlArray.class, "XmlArray", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlAttributeMappingEClass, XmlAttributeMapping.class, "XmlAttributeMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(attributesEClass, Attributes.class, "Attributes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributes_BasicCollections(), this.getXmlBasicCollection(), null, "basicCollections", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_BasicMaps(), this.getXmlBasicMap(), null, "basicMaps", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_Transformations(), this.getXmlTransformation(), null, "transformations", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_VariableOneToOnes(), this.getXmlVariableOneToOne(), null, "variableOneToOnes", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlBasicEClass, XmlBasic.class, "XmlBasic", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlBasicCollectionEClass, XmlBasicCollection.class, "XmlBasicCollection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlBasicMapEClass, XmlBasicMap.class, "XmlBasicMap", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlBatchFetchEClass, XmlBatchFetch.class, "XmlBatchFetch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlBatchFetchHolderEClass, XmlBatchFetchHolder.class, "XmlBatchFetchHolder", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlBatchFetchHolder_BatchFetch(), this.getXmlBatchFetch(), null, "batchFetch", null, 0, 1, XmlBatchFetchHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCacheEClass, XmlCache.class, "XmlCache", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlCache_Expiry(), theXMLTypePackage.getIntObject(), "expiry", null, 0, 1, XmlCache.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlCache_Size(), theXMLTypePackage.getIntObject(), "size", null, 0, 1, XmlCache.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlCache_Shared(), theXMLTypePackage.getBooleanObject(), "shared", null, 0, 1, XmlCache.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlCache_Type(), this.getCacheType(), "type", null, 0, 1, XmlCache.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlCache_AlwaysRefresh(), theXMLTypePackage.getBooleanObject(), "alwaysRefresh", null, 0, 1, XmlCache.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlCache_RefreshOnlyIfNewer(), theXMLTypePackage.getBooleanObject(), "refreshOnlyIfNewer", null, 0, 1, XmlCache.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlCache_DisableHits(), theXMLTypePackage.getBooleanObject(), "disableHits", null, 0, 1, XmlCache.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlCache_CoordinationType(), this.getCacheCoordinationType(), "coordinationType", "", 0, 1, XmlCache.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlCache_ExpiryTimeOfDay(), this.getXmlTimeOfDay(), null, "expiryTimeOfDay", null, 0, 1, XmlCache.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCacheHolderEClass, XmlCacheHolder.class, "XmlCacheHolder", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlCacheHolder_Cache(), this.getXmlCache(), null, "cache", null, 0, 1, XmlCacheHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlCacheHolder_ExistenceChecking(), this.getExistenceType(), "existenceChecking", "", 0, 1, XmlCacheHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlChangeTrackingEClass, XmlChangeTracking.class, "XmlChangeTracking", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlChangeTracking_Type(), this.getXmlChangeTrackingType(), "type", null, 0, 1, XmlChangeTracking.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlChangeTrackingHolderEClass, XmlChangeTrackingHolder.class, "XmlChangeTrackingHolder", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlChangeTrackingHolder_ChangeTracking(), this.getXmlChangeTracking(), null, "changeTracking", null, 0, 1, XmlChangeTrackingHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCloneCopyPolicyEClass, XmlCloneCopyPolicy.class, "XmlCloneCopyPolicy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlCloneCopyPolicy_Method(), theXMLTypePackage.getString(), "method", null, 0, 1, XmlCloneCopyPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlCloneCopyPolicy_WorkingCopyMethod(), theXMLTypePackage.getString(), "workingCopyMethod", null, 0, 1, XmlCloneCopyPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCollectionTableEClass, XmlCollectionTable.class, "XmlCollectionTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlConversionValueEClass, XmlConversionValue.class, "XmlConversionValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlConversionValue_DataValue(), theXMLTypePackage.getString(), "dataValue", null, 0, 1, XmlConversionValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlConversionValue_ObjectValue(), theXMLTypePackage.getString(), "objectValue", null, 0, 1, XmlConversionValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConverterEClass, XmlConverter.class, "XmlConverter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlConverter_ClassName(), theXMLTypePackage.getString(), "className", null, 0, 1, XmlConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConverterHolderEClass, XmlConverterHolder.class, "XmlConverterHolder", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlConverterHolder_Converter(), this.getXmlConverter(), null, "converter", null, 0, 1, XmlConverterHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlConverterHolder_TypeConverter(), this.getXmlTypeConverter(), null, "typeConverter", null, 0, 1, XmlConverterHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlConverterHolder_ObjectTypeConverter(), this.getXmlObjectTypeConverter(), null, "objectTypeConverter", null, 0, 1, XmlConverterHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlConverterHolder_StructConverter(), this.getXmlStructConverter(), null, "structConverter", null, 0, 1, XmlConverterHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConvertersHolderEClass, XmlConvertersHolder.class, "XmlConvertersHolder", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlConvertersHolder_Converters(), this.getXmlConverter(), null, "converters", null, 0, -1, XmlConvertersHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlConvertersHolder_TypeConverters(), this.getXmlTypeConverter(), null, "typeConverters", null, 0, -1, XmlConvertersHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlConvertersHolder_ObjectTypeConverters(), this.getXmlObjectTypeConverter(), null, "objectTypeConverters", null, 0, -1, XmlConvertersHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlConvertersHolder_StructConverters(), this.getXmlStructConverter(), null, "structConverters", null, 0, -1, XmlConvertersHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConvertibleMappingEClass, XmlConvertibleMapping.class, "XmlConvertibleMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlConvertibleMapping_Convert(), theXMLTypePackage.getString(), "convert", null, 0, 1, XmlConvertibleMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCopyPolicyEClass, XmlCopyPolicy.class, "XmlCopyPolicy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlCopyPolicy_Class(), theXMLTypePackage.getString(), "class", null, 0, 1, XmlCopyPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCustomizerEClass, XmlCustomizer.class, "XmlCustomizer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlCustomizer_CustomizerClassName(), theXMLTypePackage.getString(), "customizerClassName", null, 0, 1, XmlCustomizer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCustomizerHolderEClass, XmlCustomizerHolder.class, "XmlCustomizerHolder", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlCustomizerHolder_Customizer(), theOrmPackage.getXmlClassReference(), null, "customizer", null, 0, 1, XmlCustomizerHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlElementCollectionEClass, XmlElementCollection.class, "XmlElementCollection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEmbeddableEClass, XmlEmbeddable.class, "XmlEmbeddable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEmbeddable_CopyPolicy(), this.getXmlCopyPolicy(), null, "copyPolicy", null, 0, 1, XmlEmbeddable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEmbeddable_InstantiationCopyPolicy(), this.getXmlInstantiationCopyPolicy(), null, "instantiationCopyPolicy", null, 0, 1, XmlEmbeddable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEmbeddable_CloneCopyPolicy(), this.getXmlCloneCopyPolicy(), null, "cloneCopyPolicy", null, 0, 1, XmlEmbeddable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlEmbeddable_ExcludeDefaultMappings(), theXMLTypePackage.getBooleanObject(), "excludeDefaultMappings", null, 0, 1, XmlEmbeddable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEmbeddedEClass, XmlEmbedded.class, "XmlEmbedded", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEmbeddedIdEClass, XmlEmbeddedId.class, "XmlEmbeddedId", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEntityEClass, XmlEntity.class, "XmlEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntity_OptimisticLocking(), this.getXmlOptimisticLocking(), null, "optimisticLocking", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_CopyPolicy(), this.getXmlCopyPolicy(), null, "copyPolicy", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_InstantiationCopyPolicy(), this.getXmlInstantiationCopyPolicy(), null, "instantiationCopyPolicy", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_CloneCopyPolicy(), this.getXmlCloneCopyPolicy(), null, "cloneCopyPolicy", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlEntity_ExcludeDefaultMappings(), theXMLTypePackage.getBooleanObject(), "excludeDefaultMappings", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntityMappingsEClass, XmlEntityMappings.class, "XmlEntityMappings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlFetchAttributeEClass, XmlFetchAttribute.class, "XmlFetchAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlFetchGroupEClass, XmlFetchGroup.class, "XmlFetchGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlHashPartitioningEClass, XmlHashPartitioning.class, "XmlHashPartitioning", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlIdEClass, XmlId.class, "XmlId", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlIndexEClass, XmlIndex.class, "XmlIndex", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlInstantiationCopyPolicyEClass, XmlInstantiationCopyPolicy.class, "XmlInstantiationCopyPolicy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlJoinFetchEClass, XmlJoinFetch.class, "XmlJoinFetch", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlJoinFetch_JoinFetch(), this.getXmlJoinFetchType(), "joinFetch", null, 0, 1, XmlJoinFetch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlJoinTableEClass, XmlJoinTable.class, "XmlJoinTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlManyToManyEClass, XmlManyToMany.class, "XmlManyToMany", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlManyToOneEClass, XmlManyToOne.class, "XmlManyToOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlMappedSuperclassEClass, XmlMappedSuperclass.class, "XmlMappedSuperclass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlMappedSuperclass_OptimisticLocking(), this.getXmlOptimisticLocking(), null, "optimisticLocking", null, 0, 1, XmlMappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlMappedSuperclass_CopyPolicy(), this.getXmlCopyPolicy(), null, "copyPolicy", null, 0, 1, XmlMappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlMappedSuperclass_InstantiationCopyPolicy(), this.getXmlInstantiationCopyPolicy(), null, "instantiationCopyPolicy", null, 0, 1, XmlMappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlMappedSuperclass_CloneCopyPolicy(), this.getXmlCloneCopyPolicy(), null, "cloneCopyPolicy", null, 0, 1, XmlMappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlMappedSuperclass_ExcludeDefaultMappings(), theXMLTypePackage.getBooleanObject(), "excludeDefaultMappings", null, 0, 1, XmlMappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMultitenantEClass, XmlMultitenant.class, "XmlMultitenant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlMutableEClass, XmlMutable.class, "XmlMutable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlMutable_Mutable(), theXMLTypePackage.getBooleanObject(), "mutable", null, 0, 1, XmlMutable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNamedConverterEClass, XmlNamedConverter.class, "XmlNamedConverter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlNamedConverter_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlNamedConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNamedPlsqlStoredFunctionQueryEClass, XmlNamedPlsqlStoredFunctionQuery.class, "XmlNamedPlsqlStoredFunctionQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlNamedPlsqlStoredProcedureQueryEClass, XmlNamedPlsqlStoredProcedureQuery.class, "XmlNamedPlsqlStoredProcedureQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlNamedStoredFunctionQueryEClass, XmlNamedStoredFunctionQuery.class, "XmlNamedStoredFunctionQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlNamedStoredProcedureQueryEClass, XmlNamedStoredProcedureQuery.class, "XmlNamedStoredProcedureQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlNamedStoredProcedureQuery_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlNamedStoredProcedureQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNamedStoredProcedureQuery_ResultClass(), theXMLTypePackage.getString(), "resultClass", null, 0, 1, XmlNamedStoredProcedureQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNamedStoredProcedureQuery_ResultSetMapping(), theXMLTypePackage.getString(), "resultSetMapping", null, 0, 1, XmlNamedStoredProcedureQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNamedStoredProcedureQuery_ProcedureName(), theXMLTypePackage.getString(), "procedureName", null, 0, 1, XmlNamedStoredProcedureQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNamedStoredProcedureQuery_ReturnsResultSet(), theXMLTypePackage.getBooleanObject(), "returnsResultSet", null, 0, 1, XmlNamedStoredProcedureQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlNamedStoredProcedureQuery_Hints(), theOrmPackage.getXmlQueryHint(), null, "hints", null, 0, -1, XmlNamedStoredProcedureQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlNamedStoredProcedureQuery_Parameters(), this.getXmlStoredProcedureParameter(), null, "parameters", null, 0, -1, XmlNamedStoredProcedureQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlObjectTypeConverterEClass, XmlObjectTypeConverter.class, "XmlObjectTypeConverter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlObjectTypeConverter_DataType(), theXMLTypePackage.getString(), "dataType", null, 0, 1, XmlObjectTypeConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlObjectTypeConverter_ObjectType(), theXMLTypePackage.getString(), "objectType", null, 0, 1, XmlObjectTypeConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlObjectTypeConverter_ConversionValues(), this.getXmlConversionValue(), null, "conversionValues", null, 0, -1, XmlObjectTypeConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlObjectTypeConverter_DefaultObjectValue(), theXMLTypePackage.getString(), "defaultObjectValue", null, 0, 1, XmlObjectTypeConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToManyEClass, XmlOneToMany.class, "XmlOneToMany", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlOneToOneEClass, XmlOneToOne.class, "XmlOneToOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlOptimisticLockingEClass, XmlOptimisticLocking.class, "XmlOptimisticLocking", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlOptimisticLocking_Type(), this.getXmlOptimisticLockingType(), "type", null, 0, 1, XmlOptimisticLocking.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlOptimisticLocking_Cascade(), theXMLTypePackage.getBooleanObject(), "cascade", null, 0, 1, XmlOptimisticLocking.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlOptimisticLocking_SelectedColumns(), theOrmPackage.getXmlColumn(), null, "selectedColumns", null, 0, -1, XmlOptimisticLocking.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOrderColumnEClass, XmlOrderColumn.class, "XmlOrderColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPartitioningEClass, XmlPartitioning.class, "XmlPartitioning", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPersistenceUnitDefaultsEClass, XmlPersistenceUnitDefaults.class, "XmlPersistenceUnitDefaults", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPersistenceUnitMetadataEClass, XmlPersistenceUnitMetadata.class, "XmlPersistenceUnitMetadata", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPersistenceUnitMetadata_ExcludeDefaultMappings(), theXMLTypePackage.getBoolean(), "excludeDefaultMappings", null, 0, 1, XmlPersistenceUnitMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPinnedPartitioningEClass, XmlPinnedPartitioning.class, "XmlPinnedPartitioning", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPlsqlRecordEClass, XmlPlsqlRecord.class, "XmlPlsqlRecord", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPlsqlTableEClass, XmlPlsqlTable.class, "XmlPlsqlTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPrimaryKeyEClass, XmlPrimaryKey.class, "XmlPrimaryKey", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPrivateOwnedEClass, XmlPrivateOwned.class, "XmlPrivateOwned", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPrivateOwned_PrivateOwned(), theXMLTypePackage.getBoolean(), "privateOwned", null, 0, 1, XmlPrivateOwned.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPropertyEClass, XmlProperty.class, "XmlProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlProperty_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlProperty_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, XmlProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlProperty_ValueType(), theXMLTypePackage.getString(), "valueType", null, 0, 1, XmlProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPropertyContainerEClass, XmlPropertyContainer.class, "XmlPropertyContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlPropertyContainer_Properties(), this.getXmlProperty(), null, "properties", null, 0, -1, XmlPropertyContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlQueryContainerEClass, XmlQueryContainer.class, "XmlQueryContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlQueryContainer_NamedStoredProcedureQueries(), this.getXmlNamedStoredProcedureQuery(), null, "namedStoredProcedureQueries", null, 0, -1, XmlQueryContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlQueryRedirectorsEClass, XmlQueryRedirectors.class, "XmlQueryRedirectors", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlRangePartitioningEClass, XmlRangePartitioning.class, "XmlRangePartitioning", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlReadOnlyEClass, XmlReadOnly.class, "XmlReadOnly", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlReadOnly_ReadOnly(), theXMLTypePackage.getBooleanObject(), "readOnly", null, 0, 1, XmlReadOnly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlReplicationPartitioningEClass, XmlReplicationPartitioning.class, "XmlReplicationPartitioning", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlReturnInsertEClass, XmlReturnInsert.class, "XmlReturnInsert", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlRoundRobinPartitioningEClass, XmlRoundRobinPartitioning.class, "XmlRoundRobinPartitioning", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlSecondaryTableEClass, XmlSecondaryTable.class, "XmlSecondaryTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlStoredProcedureParameterEClass, XmlStoredProcedureParameter.class, "XmlStoredProcedureParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlStoredProcedureParameter_Direction(), this.getXmlDirection(), "direction", null, 0, 1, XmlStoredProcedureParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlStoredProcedureParameter_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlStoredProcedureParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlStoredProcedureParameter_QueryParameter(), theXMLTypePackage.getString(), "queryParameter", null, 0, 1, XmlStoredProcedureParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlStoredProcedureParameter_Type(), theXMLTypePackage.getString(), "type", null, 0, 1, XmlStoredProcedureParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlStoredProcedureParameter_JdbcType(), theXMLTypePackage.getIntObject(), "jdbcType", null, 0, 1, XmlStoredProcedureParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlStoredProcedureParameter_JdbcTypeName(), theXMLTypePackage.getString(), "jdbcTypeName", null, 0, 1, XmlStoredProcedureParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlStructEClass, XmlStruct.class, "XmlStruct", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlStructConverterEClass, XmlStructConverter.class, "XmlStructConverter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlStructConverter_Converter(), theXMLTypePackage.getString(), "converter", null, 0, 1, XmlStructConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlStructureEClass, XmlStructure.class, "XmlStructure", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlTableEClass, XmlTable.class, "XmlTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlTableGeneratorEClass, XmlTableGenerator.class, "XmlTableGenerator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlTenantDiscriminatorColumnEClass, XmlTenantDiscriminatorColumn.class, "XmlTenantDiscriminatorColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlTimeOfDayEClass, XmlTimeOfDay.class, "XmlTimeOfDay", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlTimeOfDay_Hour(), theXMLTypePackage.getIntObject(), "hour", null, 0, 1, XmlTimeOfDay.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTimeOfDay_Minute(), theXMLTypePackage.getIntObject(), "minute", null, 0, 1, XmlTimeOfDay.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTimeOfDay_Second(), theXMLTypePackage.getIntObject(), "second", null, 0, 1, XmlTimeOfDay.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTimeOfDay_Millisecond(), theXMLTypePackage.getIntObject(), "millisecond", null, 0, 1, XmlTimeOfDay.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTransformationEClass, XmlTransformation.class, "XmlTransformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlTransientEClass, XmlTransient.class, "XmlTransient", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlTypeConverterEClass, XmlTypeConverter.class, "XmlTypeConverter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlTypeConverter_DataType(), theXMLTypePackage.getString(), "dataType", null, 0, 1, XmlTypeConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTypeConverter_ObjectType(), theXMLTypePackage.getString(), "objectType", null, 0, 1, XmlTypeConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlUnionPartitioningEClass, XmlUnionPartitioning.class, "XmlUnionPartitioning", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlValuePartitioningEClass, XmlValuePartitioning.class, "XmlValuePartitioning", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlVariableOneToOneEClass, XmlVariableOneToOne.class, "XmlVariableOneToOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlVersionEClass, XmlVersion.class, "XmlVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(cacheCoordinationTypeEEnum, CacheCoordinationType.class, "CacheCoordinationType");
		addEEnumLiteral(cacheCoordinationTypeEEnum, CacheCoordinationType.SEND_OBJECT_CHANGES);
		addEEnumLiteral(cacheCoordinationTypeEEnum, CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		addEEnumLiteral(cacheCoordinationTypeEEnum, CacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES);
		addEEnumLiteral(cacheCoordinationTypeEEnum, CacheCoordinationType.NONE);

		initEEnum(cacheTypeEEnum, CacheType.class, "CacheType");
		addEEnumLiteral(cacheTypeEEnum, CacheType.FULL);
		addEEnumLiteral(cacheTypeEEnum, CacheType.WEAK);
		addEEnumLiteral(cacheTypeEEnum, CacheType.SOFT);
		addEEnumLiteral(cacheTypeEEnum, CacheType.SOFT_WEAK);
		addEEnumLiteral(cacheTypeEEnum, CacheType.HARD_WEAK);
		addEEnumLiteral(cacheTypeEEnum, CacheType.CACHE);
		addEEnumLiteral(cacheTypeEEnum, CacheType.NONE);

		initEEnum(xmlChangeTrackingTypeEEnum, XmlChangeTrackingType.class, "XmlChangeTrackingType");
		addEEnumLiteral(xmlChangeTrackingTypeEEnum, XmlChangeTrackingType.ATTRIBUTE);
		addEEnumLiteral(xmlChangeTrackingTypeEEnum, XmlChangeTrackingType.OBJECT);
		addEEnumLiteral(xmlChangeTrackingTypeEEnum, XmlChangeTrackingType.DEFERRED);
		addEEnumLiteral(xmlChangeTrackingTypeEEnum, XmlChangeTrackingType.AUTO);

		initEEnum(xmlDirectionEEnum, XmlDirection.class, "XmlDirection");
		addEEnumLiteral(xmlDirectionEEnum, XmlDirection.IN);
		addEEnumLiteral(xmlDirectionEEnum, XmlDirection.OUT);
		addEEnumLiteral(xmlDirectionEEnum, XmlDirection.IN_OUT);
		addEEnumLiteral(xmlDirectionEEnum, XmlDirection.OUT_CURSOR);

		initEEnum(existenceTypeEEnum, ExistenceType.class, "ExistenceType");
		addEEnumLiteral(existenceTypeEEnum, ExistenceType.CHECK_CACHE);
		addEEnumLiteral(existenceTypeEEnum, ExistenceType.CHECK_DATABASE);
		addEEnumLiteral(existenceTypeEEnum, ExistenceType.ASSUME_EXISTENCE);
		addEEnumLiteral(existenceTypeEEnum, ExistenceType.ASSUME_NON_EXISTENCE);

		initEEnum(xmlJoinFetchTypeEEnum, XmlJoinFetchType.class, "XmlJoinFetchType");
		addEEnumLiteral(xmlJoinFetchTypeEEnum, XmlJoinFetchType.INNER);
		addEEnumLiteral(xmlJoinFetchTypeEEnum, XmlJoinFetchType.OUTER);

		initEEnum(xmlOptimisticLockingTypeEEnum, XmlOptimisticLockingType.class, "XmlOptimisticLockingType");
		addEEnumLiteral(xmlOptimisticLockingTypeEEnum, XmlOptimisticLockingType.ALL_COLUMNS);
		addEEnumLiteral(xmlOptimisticLockingTypeEEnum, XmlOptimisticLockingType.CHANGED_COLUMNS);
		addEEnumLiteral(xmlOptimisticLockingTypeEEnum, XmlOptimisticLockingType.SELECTED_COLUMNS);
		addEEnumLiteral(xmlOptimisticLockingTypeEEnum, XmlOptimisticLockingType.VERSION_COLUMN);

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
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods <em>Xml Access Methods</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAccessMethods()
		 * @generated
		 */
		public static final EClass XML_ACCESS_METHODS = eINSTANCE.getXmlAccessMethods();

		/**
		 * The meta object literal for the '<em><b>Get Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ACCESS_METHODS__GET_METHOD = eINSTANCE.getXmlAccessMethods_GetMethod();

		/**
		 * The meta object literal for the '<em><b>Set Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ACCESS_METHODS__SET_METHOD = eINSTANCE.getXmlAccessMethods_SetMethod();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethodsHolder <em>Xml Access Methods Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethodsHolder
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAccessMethodsHolder()
		 * @generated
		 */
		public static final EClass XML_ACCESS_METHODS_HOLDER = eINSTANCE.getXmlAccessMethodsHolder();

		/**
		 * The meta object literal for the '<em><b>Access Methods</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS = eINSTANCE.getXmlAccessMethodsHolder_AccessMethods();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAdditionalCriteria <em>Xml Additional Criteria</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAdditionalCriteria
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAdditionalCriteria()
		 * @generated
		 */
		public static final EClass XML_ADDITIONAL_CRITERIA = eINSTANCE.getXmlAdditionalCriteria();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlArray <em>Xml Array</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlArray
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlArray()
		 * @generated
		 */
		public static final EClass XML_ARRAY = eINSTANCE.getXmlArray();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAttributeMapping()
		 * @generated
		 */
		public static final EClass XML_ATTRIBUTE_MAPPING = eINSTANCE.getXmlAttributeMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes <em>Attributes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getAttributes()
		 * @generated
		 */
		public static final EClass ATTRIBUTES = eINSTANCE.getAttributes();

		/**
		 * The meta object literal for the '<em><b>Basic Collections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__BASIC_COLLECTIONS = eINSTANCE.getAttributes_BasicCollections();

		/**
		 * The meta object literal for the '<em><b>Basic Maps</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__BASIC_MAPS = eINSTANCE.getAttributes_BasicMaps();

		/**
		 * The meta object literal for the '<em><b>Transformations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__TRANSFORMATIONS = eINSTANCE.getAttributes_Transformations();

		/**
		 * The meta object literal for the '<em><b>Variable One To Ones</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__VARIABLE_ONE_TO_ONES = eINSTANCE.getAttributes_VariableOneToOnes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic <em>Xml Basic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBasic()
		 * @generated
		 */
		public static final EClass XML_BASIC = eINSTANCE.getXmlBasic();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection <em>Xml Basic Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBasicCollection()
		 * @generated
		 */
		public static final EClass XML_BASIC_COLLECTION = eINSTANCE.getXmlBasicCollection();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicMap <em>Xml Basic Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicMap
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBasicMap()
		 * @generated
		 */
		public static final EClass XML_BASIC_MAP = eINSTANCE.getXmlBasicMap();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetch <em>Xml Batch Fetch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetch
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBatchFetch()
		 * @generated
		 */
		public static final EClass XML_BATCH_FETCH = eINSTANCE.getXmlBatchFetch();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetchHolder <em>Xml Batch Fetch Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetchHolder
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBatchFetchHolder()
		 * @generated
		 */
		public static final EClass XML_BATCH_FETCH_HOLDER = eINSTANCE.getXmlBatchFetchHolder();

		/**
		 * The meta object literal for the '<em><b>Batch Fetch</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_BATCH_FETCH_HOLDER__BATCH_FETCH = eINSTANCE.getXmlBatchFetchHolder_BatchFetch();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache <em>Xml Cache</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache()
		 * @generated
		 */
		public static final EClass XML_CACHE = eINSTANCE.getXmlCache();

		/**
		 * The meta object literal for the '<em><b>Expiry</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CACHE__EXPIRY = eINSTANCE.getXmlCache_Expiry();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CACHE__SIZE = eINSTANCE.getXmlCache_Size();

		/**
		 * The meta object literal for the '<em><b>Shared</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CACHE__SHARED = eINSTANCE.getXmlCache_Shared();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CACHE__TYPE = eINSTANCE.getXmlCache_Type();

		/**
		 * The meta object literal for the '<em><b>Always Refresh</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CACHE__ALWAYS_REFRESH = eINSTANCE.getXmlCache_AlwaysRefresh();

		/**
		 * The meta object literal for the '<em><b>Refresh Only If Newer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CACHE__REFRESH_ONLY_IF_NEWER = eINSTANCE.getXmlCache_RefreshOnlyIfNewer();

		/**
		 * The meta object literal for the '<em><b>Disable Hits</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CACHE__DISABLE_HITS = eINSTANCE.getXmlCache_DisableHits();

		/**
		 * The meta object literal for the '<em><b>Coordination Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CACHE__COORDINATION_TYPE = eINSTANCE.getXmlCache_CoordinationType();

		/**
		 * The meta object literal for the '<em><b>Expiry Time Of Day</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CACHE__EXPIRY_TIME_OF_DAY = eINSTANCE.getXmlCache_ExpiryTimeOfDay();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCacheHolder <em>Xml Cache Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCacheHolder
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCacheHolder()
		 * @generated
		 */
		public static final EClass XML_CACHE_HOLDER = eINSTANCE.getXmlCacheHolder();

		/**
		 * The meta object literal for the '<em><b>Cache</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CACHE_HOLDER__CACHE = eINSTANCE.getXmlCacheHolder_Cache();

		/**
		 * The meta object literal for the '<em><b>Existence Checking</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CACHE_HOLDER__EXISTENCE_CHECKING = eINSTANCE.getXmlCacheHolder_ExistenceChecking();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTracking <em>Xml Change Tracking</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTracking
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTracking()
		 * @generated
		 */
		public static final EClass XML_CHANGE_TRACKING = eINSTANCE.getXmlChangeTracking();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CHANGE_TRACKING__TYPE = eINSTANCE.getXmlChangeTracking_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingHolder <em>Xml Change Tracking Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingHolder
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTrackingHolder()
		 * @generated
		 */
		public static final EClass XML_CHANGE_TRACKING_HOLDER = eINSTANCE.getXmlChangeTrackingHolder();

		/**
		 * The meta object literal for the '<em><b>Change Tracking</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CHANGE_TRACKING_HOLDER__CHANGE_TRACKING = eINSTANCE.getXmlChangeTrackingHolder_ChangeTracking();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCloneCopyPolicy <em>Xml Clone Copy Policy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCloneCopyPolicy
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCloneCopyPolicy()
		 * @generated
		 */
		public static final EClass XML_CLONE_COPY_POLICY = eINSTANCE.getXmlCloneCopyPolicy();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CLONE_COPY_POLICY__METHOD = eINSTANCE.getXmlCloneCopyPolicy_Method();

		/**
		 * The meta object literal for the '<em><b>Working Copy Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CLONE_COPY_POLICY__WORKING_COPY_METHOD = eINSTANCE.getXmlCloneCopyPolicy_WorkingCopyMethod();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCollectionTable <em>Xml Collection Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCollectionTable
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCollectionTable()
		 * @generated
		 */
		public static final EClass XML_COLLECTION_TABLE = eINSTANCE.getXmlCollectionTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConversionValue <em>Xml Conversion Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConversionValue
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConversionValue()
		 * @generated
		 */
		public static final EClass XML_CONVERSION_VALUE = eINSTANCE.getXmlConversionValue();

		/**
		 * The meta object literal for the '<em><b>Data Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERSION_VALUE__DATA_VALUE = eINSTANCE.getXmlConversionValue_DataValue();

		/**
		 * The meta object literal for the '<em><b>Object Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERSION_VALUE__OBJECT_VALUE = eINSTANCE.getXmlConversionValue_ObjectValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter <em>Xml Converter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverter()
		 * @generated
		 */
		public static final EClass XML_CONVERTER = eINSTANCE.getXmlConverter();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERTER__CLASS_NAME = eINSTANCE.getXmlConverter_ClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder <em>Xml Converter Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverterHolder()
		 * @generated
		 */
		public static final EClass XML_CONVERTER_HOLDER = eINSTANCE.getXmlConverterHolder();

		/**
		 * The meta object literal for the '<em><b>Converter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CONVERTER_HOLDER__CONVERTER = eINSTANCE.getXmlConverterHolder_Converter();

		/**
		 * The meta object literal for the '<em><b>Type Converter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CONVERTER_HOLDER__TYPE_CONVERTER = eINSTANCE.getXmlConverterHolder_TypeConverter();

		/**
		 * The meta object literal for the '<em><b>Object Type Converter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CONVERTER_HOLDER__OBJECT_TYPE_CONVERTER = eINSTANCE.getXmlConverterHolder_ObjectTypeConverter();

		/**
		 * The meta object literal for the '<em><b>Struct Converter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CONVERTER_HOLDER__STRUCT_CONVERTER = eINSTANCE.getXmlConverterHolder_StructConverter();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder <em>Xml Converters Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertersHolder()
		 * @generated
		 */
		public static final EClass XML_CONVERTERS_HOLDER = eINSTANCE.getXmlConvertersHolder();

		/**
		 * The meta object literal for the '<em><b>Converters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CONVERTERS_HOLDER__CONVERTERS = eINSTANCE.getXmlConvertersHolder_Converters();

		/**
		 * The meta object literal for the '<em><b>Type Converters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CONVERTERS_HOLDER__TYPE_CONVERTERS = eINSTANCE.getXmlConvertersHolder_TypeConverters();

		/**
		 * The meta object literal for the '<em><b>Object Type Converters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CONVERTERS_HOLDER__OBJECT_TYPE_CONVERTERS = eINSTANCE.getXmlConvertersHolder_ObjectTypeConverters();

		/**
		 * The meta object literal for the '<em><b>Struct Converters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CONVERTERS_HOLDER__STRUCT_CONVERTERS = eINSTANCE.getXmlConvertersHolder_StructConverters();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertibleMapping <em>Xml Convertible Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertibleMapping
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertibleMapping()
		 * @generated
		 */
		public static final EClass XML_CONVERTIBLE_MAPPING = eINSTANCE.getXmlConvertibleMapping();

		/**
		 * The meta object literal for the '<em><b>Convert</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERTIBLE_MAPPING__CONVERT = eINSTANCE.getXmlConvertibleMapping_Convert();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCopyPolicy <em>Xml Copy Policy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCopyPolicy
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCopyPolicy()
		 * @generated
		 */
		public static final EClass XML_COPY_POLICY = eINSTANCE.getXmlCopyPolicy();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_COPY_POLICY__CLASS = eINSTANCE.getXmlCopyPolicy_Class();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizer <em>Xml Customizer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizer
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCustomizer()
		 * @generated
		 */
		public static final EClass XML_CUSTOMIZER = eINSTANCE.getXmlCustomizer();

		/**
		 * The meta object literal for the '<em><b>Customizer Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CUSTOMIZER__CUSTOMIZER_CLASS_NAME = eINSTANCE.getXmlCustomizer_CustomizerClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizerHolder <em>Xml Customizer Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizerHolder
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCustomizerHolder()
		 * @generated
		 */
		public static final EClass XML_CUSTOMIZER_HOLDER = eINSTANCE.getXmlCustomizerHolder();

		/**
		 * The meta object literal for the '<em><b>Customizer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CUSTOMIZER_HOLDER__CUSTOMIZER = eINSTANCE.getXmlCustomizerHolder_Customizer();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlElementCollection <em>Xml Element Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlElementCollection
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlElementCollection()
		 * @generated
		 */
		public static final EClass XML_ELEMENT_COLLECTION = eINSTANCE.getXmlElementCollection();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable <em>Xml Embeddable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddable()
		 * @generated
		 */
		public static final EClass XML_EMBEDDABLE = eINSTANCE.getXmlEmbeddable();

		/**
		 * The meta object literal for the '<em><b>Copy Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EMBEDDABLE__COPY_POLICY = eINSTANCE.getXmlEmbeddable_CopyPolicy();

		/**
		 * The meta object literal for the '<em><b>Instantiation Copy Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EMBEDDABLE__INSTANTIATION_COPY_POLICY = eINSTANCE.getXmlEmbeddable_InstantiationCopyPolicy();

		/**
		 * The meta object literal for the '<em><b>Clone Copy Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EMBEDDABLE__CLONE_COPY_POLICY = eINSTANCE.getXmlEmbeddable_CloneCopyPolicy();

		/**
		 * The meta object literal for the '<em><b>Exclude Default Mappings</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_EMBEDDABLE__EXCLUDE_DEFAULT_MAPPINGS = eINSTANCE.getXmlEmbeddable_ExcludeDefaultMappings();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbedded <em>Xml Embedded</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbedded
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbedded()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED = eINSTANCE.getXmlEmbedded();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddedId
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddedId()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED_ID = eINSTANCE.getXmlEmbeddedId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity <em>Xml Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity()
		 * @generated
		 */
		public static final EClass XML_ENTITY = eINSTANCE.getXmlEntity();

		/**
		 * The meta object literal for the '<em><b>Optimistic Locking</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__OPTIMISTIC_LOCKING = eINSTANCE.getXmlEntity_OptimisticLocking();

		/**
		 * The meta object literal for the '<em><b>Copy Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__COPY_POLICY = eINSTANCE.getXmlEntity_CopyPolicy();

		/**
		 * The meta object literal for the '<em><b>Instantiation Copy Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__INSTANTIATION_COPY_POLICY = eINSTANCE.getXmlEntity_InstantiationCopyPolicy();

		/**
		 * The meta object literal for the '<em><b>Clone Copy Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__CLONE_COPY_POLICY = eINSTANCE.getXmlEntity_CloneCopyPolicy();

		/**
		 * The meta object literal for the '<em><b>Exclude Default Mappings</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ENTITY__EXCLUDE_DEFAULT_MAPPINGS = eINSTANCE.getXmlEntity_ExcludeDefaultMappings();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntityMappings()
		 * @generated
		 */
		public static final EClass XML_ENTITY_MAPPINGS = eINSTANCE.getXmlEntityMappings();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchAttribute <em>Xml Fetch Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchAttribute
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlFetchAttribute()
		 * @generated
		 */
		public static final EClass XML_FETCH_ATTRIBUTE = eINSTANCE.getXmlFetchAttribute();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchGroup <em>Xml Fetch Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchGroup
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlFetchGroup()
		 * @generated
		 */
		public static final EClass XML_FETCH_GROUP = eINSTANCE.getXmlFetchGroup();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId <em>Xml Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlId()
		 * @generated
		 */
		public static final EClass XML_ID = eINSTANCE.getXmlId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlIndex <em>Xml Index</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlIndex
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlIndex()
		 * @generated
		 */
		public static final EClass XML_INDEX = eINSTANCE.getXmlIndex();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlInstantiationCopyPolicy <em>Xml Instantiation Copy Policy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlInstantiationCopyPolicy
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlInstantiationCopyPolicy()
		 * @generated
		 */
		public static final EClass XML_INSTANTIATION_COPY_POLICY = eINSTANCE.getXmlInstantiationCopyPolicy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetch <em>Xml Join Fetch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetch
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetch()
		 * @generated
		 */
		public static final EClass XML_JOIN_FETCH = eINSTANCE.getXmlJoinFetch();

		/**
		 * The meta object literal for the '<em><b>Join Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_JOIN_FETCH__JOIN_FETCH = eINSTANCE.getXmlJoinFetch_JoinFetch();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinTable <em>Xml Join Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinTable
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinTable()
		 * @generated
		 */
		public static final EClass XML_JOIN_TABLE = eINSTANCE.getXmlJoinTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToMany
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToMany()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_MANY = eINSTANCE.getXmlManyToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToOne()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_ONE = eINSTANCE.getXmlManyToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMappedSuperclass()
		 * @generated
		 */
		public static final EClass XML_MAPPED_SUPERCLASS = eINSTANCE.getXmlMappedSuperclass();

		/**
		 * The meta object literal for the '<em><b>Optimistic Locking</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS__OPTIMISTIC_LOCKING = eINSTANCE.getXmlMappedSuperclass_OptimisticLocking();

		/**
		 * The meta object literal for the '<em><b>Copy Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS__COPY_POLICY = eINSTANCE.getXmlMappedSuperclass_CopyPolicy();

		/**
		 * The meta object literal for the '<em><b>Instantiation Copy Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS__INSTANTIATION_COPY_POLICY = eINSTANCE.getXmlMappedSuperclass_InstantiationCopyPolicy();

		/**
		 * The meta object literal for the '<em><b>Clone Copy Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS__CLONE_COPY_POLICY = eINSTANCE.getXmlMappedSuperclass_CloneCopyPolicy();

		/**
		 * The meta object literal for the '<em><b>Exclude Default Mappings</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_MAPPINGS = eINSTANCE.getXmlMappedSuperclass_ExcludeDefaultMappings();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenant <em>Xml Multitenant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenant
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMultitenant()
		 * @generated
		 */
		public static final EClass XML_MULTITENANT = eINSTANCE.getXmlMultitenant();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMutable <em>Xml Mutable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMutable
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMutable()
		 * @generated
		 */
		public static final EClass XML_MUTABLE = eINSTANCE.getXmlMutable();

		/**
		 * The meta object literal for the '<em><b>Mutable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MUTABLE__MUTABLE = eINSTANCE.getXmlMutable_Mutable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter <em>Xml Named Converter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedConverter()
		 * @generated
		 */
		public static final EClass XML_NAMED_CONVERTER = eINSTANCE.getXmlNamedConverter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_CONVERTER__NAME = eINSTANCE.getXmlNamedConverter_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredFunctionQuery <em>Xml Named Plsql Stored Function Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredFunctionQuery
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedPlsqlStoredFunctionQuery()
		 * @generated
		 */
		public static final EClass XML_NAMED_PLSQL_STORED_FUNCTION_QUERY = eINSTANCE.getXmlNamedPlsqlStoredFunctionQuery();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredProcedureQuery <em>Xml Named Plsql Stored Procedure Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredProcedureQuery
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedPlsqlStoredProcedureQuery()
		 * @generated
		 */
		public static final EClass XML_NAMED_PLSQL_STORED_PROCEDURE_QUERY = eINSTANCE.getXmlNamedPlsqlStoredProcedureQuery();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredFunctionQuery <em>Xml Named Stored Function Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredFunctionQuery
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredFunctionQuery()
		 * @generated
		 */
		public static final EClass XML_NAMED_STORED_FUNCTION_QUERY = eINSTANCE.getXmlNamedStoredFunctionQuery();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery <em>Xml Named Stored Procedure Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery()
		 * @generated
		 */
		public static final EClass XML_NAMED_STORED_PROCEDURE_QUERY = eINSTANCE.getXmlNamedStoredProcedureQuery();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_STORED_PROCEDURE_QUERY__NAME = eINSTANCE.getXmlNamedStoredProcedureQuery_Name();

		/**
		 * The meta object literal for the '<em><b>Result Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASS = eINSTANCE.getXmlNamedStoredProcedureQuery_ResultClass();

		/**
		 * The meta object literal for the '<em><b>Result Set Mapping</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPING = eINSTANCE.getXmlNamedStoredProcedureQuery_ResultSetMapping();

		/**
		 * The meta object literal for the '<em><b>Procedure Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME = eINSTANCE.getXmlNamedStoredProcedureQuery_ProcedureName();

		/**
		 * The meta object literal for the '<em><b>Returns Result Set</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET = eINSTANCE.getXmlNamedStoredProcedureQuery_ReturnsResultSet();

		/**
		 * The meta object literal for the '<em><b>Hints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_NAMED_STORED_PROCEDURE_QUERY__HINTS = eINSTANCE.getXmlNamedStoredProcedureQuery_Hints();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS = eINSTANCE.getXmlNamedStoredProcedureQuery_Parameters();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter <em>Xml Object Type Converter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlObjectTypeConverter()
		 * @generated
		 */
		public static final EClass XML_OBJECT_TYPE_CONVERTER = eINSTANCE.getXmlObjectTypeConverter();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_OBJECT_TYPE_CONVERTER__DATA_TYPE = eINSTANCE.getXmlObjectTypeConverter_DataType();

		/**
		 * The meta object literal for the '<em><b>Object Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_OBJECT_TYPE_CONVERTER__OBJECT_TYPE = eINSTANCE.getXmlObjectTypeConverter_ObjectType();

		/**
		 * The meta object literal for the '<em><b>Conversion Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_OBJECT_TYPE_CONVERTER__CONVERSION_VALUES = eINSTANCE.getXmlObjectTypeConverter_ConversionValues();

		/**
		 * The meta object literal for the '<em><b>Default Object Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE = eINSTANCE.getXmlObjectTypeConverter_DefaultObjectValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToMany
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOneToMany()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_MANY = eINSTANCE.getXmlOneToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToOne
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOneToOne()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_ONE = eINSTANCE.getXmlOneToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLocking <em>Xml Optimistic Locking</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLocking
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOptimisticLocking()
		 * @generated
		 */
		public static final EClass XML_OPTIMISTIC_LOCKING = eINSTANCE.getXmlOptimisticLocking();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_OPTIMISTIC_LOCKING__TYPE = eINSTANCE.getXmlOptimisticLocking_Type();

		/**
		 * The meta object literal for the '<em><b>Cascade</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_OPTIMISTIC_LOCKING__CASCADE = eINSTANCE.getXmlOptimisticLocking_Cascade();

		/**
		 * The meta object literal for the '<em><b>Selected Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_OPTIMISTIC_LOCKING__SELECTED_COLUMNS = eINSTANCE.getXmlOptimisticLocking_SelectedColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOrderColumn <em>Xml Order Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOrderColumn
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOrderColumn()
		 * @generated
		 */
		public static final EClass XML_ORDER_COLUMN = eINSTANCE.getXmlOrderColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitDefaults <em>Xml Persistence Unit Defaults</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitDefaults
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPersistenceUnitDefaults()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE_UNIT_DEFAULTS = eINSTANCE.getXmlPersistenceUnitDefaults();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitMetadata <em>Xml Persistence Unit Metadata</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitMetadata
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPersistenceUnitMetadata()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE_UNIT_METADATA = eINSTANCE.getXmlPersistenceUnitMetadata();

		/**
		 * The meta object literal for the '<em><b>Exclude Default Mappings</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT_METADATA__EXCLUDE_DEFAULT_MAPPINGS = eINSTANCE.getXmlPersistenceUnitMetadata_ExcludeDefaultMappings();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrimaryKey <em>Xml Primary Key</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrimaryKey
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrimaryKey()
		 * @generated
		 */
		public static final EClass XML_PRIMARY_KEY = eINSTANCE.getXmlPrimaryKey();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrivateOwned <em>Xml Private Owned</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrivateOwned
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrivateOwned()
		 * @generated
		 */
		public static final EClass XML_PRIVATE_OWNED = eINSTANCE.getXmlPrivateOwned();

		/**
		 * The meta object literal for the '<em><b>Private Owned</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PRIVATE_OWNED__PRIVATE_OWNED = eINSTANCE.getXmlPrivateOwned_PrivateOwned();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty <em>Xml Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlProperty()
		 * @generated
		 */
		public static final EClass XML_PROPERTY = eINSTANCE.getXmlProperty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PROPERTY__NAME = eINSTANCE.getXmlProperty_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PROPERTY__VALUE = eINSTANCE.getXmlProperty_Value();

		/**
		 * The meta object literal for the '<em><b>Value Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PROPERTY__VALUE_TYPE = eINSTANCE.getXmlProperty_ValueType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPropertyContainer <em>Xml Property Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPropertyContainer
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPropertyContainer()
		 * @generated
		 */
		public static final EClass XML_PROPERTY_CONTAINER = eINSTANCE.getXmlPropertyContainer();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PROPERTY_CONTAINER__PROPERTIES = eINSTANCE.getXmlPropertyContainer_Properties();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryContainer <em>Xml Query Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryContainer
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlQueryContainer()
		 * @generated
		 */
		public static final EClass XML_QUERY_CONTAINER = eINSTANCE.getXmlQueryContainer();

		/**
		 * The meta object literal for the '<em><b>Named Stored Procedure Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_QUERY_CONTAINER__NAMED_STORED_PROCEDURE_QUERIES = eINSTANCE.getXmlQueryContainer_NamedStoredProcedureQueries();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryRedirectors <em>Xml Query Redirectors</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryRedirectors
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlQueryRedirectors()
		 * @generated
		 */
		public static final EClass XML_QUERY_REDIRECTORS = eINSTANCE.getXmlQueryRedirectors();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReadOnly <em>Xml Read Only</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReadOnly
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlReadOnly()
		 * @generated
		 */
		public static final EClass XML_READ_ONLY = eINSTANCE.getXmlReadOnly();

		/**
		 * The meta object literal for the '<em><b>Read Only</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_READ_ONLY__READ_ONLY = eINSTANCE.getXmlReadOnly_ReadOnly();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReturnInsert <em>Xml Return Insert</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReturnInsert
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlReturnInsert()
		 * @generated
		 */
		public static final EClass XML_RETURN_INSERT = eINSTANCE.getXmlReturnInsert();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlSecondaryTable <em>Xml Secondary Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlSecondaryTable
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlSecondaryTable()
		 * @generated
		 */
		public static final EClass XML_SECONDARY_TABLE = eINSTANCE.getXmlSecondaryTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter <em>Xml Stored Procedure Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStoredProcedureParameter()
		 * @generated
		 */
		public static final EClass XML_STORED_PROCEDURE_PARAMETER = eINSTANCE.getXmlStoredProcedureParameter();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_STORED_PROCEDURE_PARAMETER__DIRECTION = eINSTANCE.getXmlStoredProcedureParameter_Direction();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_STORED_PROCEDURE_PARAMETER__NAME = eINSTANCE.getXmlStoredProcedureParameter_Name();

		/**
		 * The meta object literal for the '<em><b>Query Parameter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_STORED_PROCEDURE_PARAMETER__QUERY_PARAMETER = eINSTANCE.getXmlStoredProcedureParameter_QueryParameter();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_STORED_PROCEDURE_PARAMETER__TYPE = eINSTANCE.getXmlStoredProcedureParameter_Type();

		/**
		 * The meta object literal for the '<em><b>Jdbc Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE = eINSTANCE.getXmlStoredProcedureParameter_JdbcType();

		/**
		 * The meta object literal for the '<em><b>Jdbc Type Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE_NAME = eINSTANCE.getXmlStoredProcedureParameter_JdbcTypeName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStruct <em>Xml Struct</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStruct
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStruct()
		 * @generated
		 */
		public static final EClass XML_STRUCT = eINSTANCE.getXmlStruct();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter <em>Xml Struct Converter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStructConverter()
		 * @generated
		 */
		public static final EClass XML_STRUCT_CONVERTER = eINSTANCE.getXmlStructConverter();

		/**
		 * The meta object literal for the '<em><b>Converter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_STRUCT_CONVERTER__CONVERTER = eINSTANCE.getXmlStructConverter_Converter();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructure <em>Xml Structure</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructure
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStructure()
		 * @generated
		 */
		public static final EClass XML_STRUCTURE = eINSTANCE.getXmlStructure();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTable <em>Xml Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTable
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTable()
		 * @generated
		 */
		public static final EClass XML_TABLE = eINSTANCE.getXmlTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTableGenerator <em>Xml Table Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTableGenerator
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTableGenerator()
		 * @generated
		 */
		public static final EClass XML_TABLE_GENERATOR = eINSTANCE.getXmlTableGenerator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn <em>Xml Tenant Discriminator Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTenantDiscriminatorColumn()
		 * @generated
		 */
		public static final EClass XML_TENANT_DISCRIMINATOR_COLUMN = eINSTANCE.getXmlTenantDiscriminatorColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay <em>Xml Time Of Day</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTimeOfDay()
		 * @generated
		 */
		public static final EClass XML_TIME_OF_DAY = eINSTANCE.getXmlTimeOfDay();

		/**
		 * The meta object literal for the '<em><b>Hour</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TIME_OF_DAY__HOUR = eINSTANCE.getXmlTimeOfDay_Hour();

		/**
		 * The meta object literal for the '<em><b>Minute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TIME_OF_DAY__MINUTE = eINSTANCE.getXmlTimeOfDay_Minute();

		/**
		 * The meta object literal for the '<em><b>Second</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TIME_OF_DAY__SECOND = eINSTANCE.getXmlTimeOfDay_Second();

		/**
		 * The meta object literal for the '<em><b>Millisecond</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TIME_OF_DAY__MILLISECOND = eINSTANCE.getXmlTimeOfDay_Millisecond();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransformation <em>Xml Transformation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransformation
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTransformation()
		 * @generated
		 */
		public static final EClass XML_TRANSFORMATION = eINSTANCE.getXmlTransformation();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransient <em>Xml Transient</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransient
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTransient()
		 * @generated
		 */
		public static final EClass XML_TRANSIENT = eINSTANCE.getXmlTransient();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter <em>Xml Type Converter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTypeConverter()
		 * @generated
		 */
		public static final EClass XML_TYPE_CONVERTER = eINSTANCE.getXmlTypeConverter();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TYPE_CONVERTER__DATA_TYPE = eINSTANCE.getXmlTypeConverter_DataType();

		/**
		 * The meta object literal for the '<em><b>Object Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TYPE_CONVERTER__OBJECT_TYPE = eINSTANCE.getXmlTypeConverter_ObjectType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVariableOneToOne <em>Xml Variable One To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVariableOneToOne
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlVariableOneToOne()
		 * @generated
		 */
		public static final EClass XML_VARIABLE_ONE_TO_ONE = eINSTANCE.getXmlVariableOneToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVersion <em>Xml Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVersion
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlVersion()
		 * @generated
		 */
		public static final EClass XML_VERSION = eINSTANCE.getXmlVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlHashPartitioning <em>Xml Hash Partitioning</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlHashPartitioning
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlHashPartitioning()
		 * @generated
		 */
		public static final EClass XML_HASH_PARTITIONING = eINSTANCE.getXmlHashPartitioning();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPartitioning <em>Xml Partitioning</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPartitioning
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPartitioning()
		 * @generated
		 */
		public static final EClass XML_PARTITIONING = eINSTANCE.getXmlPartitioning();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPinnedPartitioning <em>Xml Pinned Partitioning</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPinnedPartitioning
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPinnedPartitioning()
		 * @generated
		 */
		public static final EClass XML_PINNED_PARTITIONING = eINSTANCE.getXmlPinnedPartitioning();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlRecord <em>Xml Plsql Record</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlRecord
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPlsqlRecord()
		 * @generated
		 */
		public static final EClass XML_PLSQL_RECORD = eINSTANCE.getXmlPlsqlRecord();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlTable <em>Xml Plsql Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlTable
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPlsqlTable()
		 * @generated
		 */
		public static final EClass XML_PLSQL_TABLE = eINSTANCE.getXmlPlsqlTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlRangePartitioning <em>Xml Range Partitioning</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlRangePartitioning
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlRangePartitioning()
		 * @generated
		 */
		public static final EClass XML_RANGE_PARTITIONING = eINSTANCE.getXmlRangePartitioning();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReplicationPartitioning <em>Xml Replication Partitioning</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReplicationPartitioning
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlReplicationPartitioning()
		 * @generated
		 */
		public static final EClass XML_REPLICATION_PARTITIONING = eINSTANCE.getXmlReplicationPartitioning();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlRoundRobinPartitioning <em>Xml Round Robin Partitioning</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlRoundRobinPartitioning
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlRoundRobinPartitioning()
		 * @generated
		 */
		public static final EClass XML_ROUND_ROBIN_PARTITIONING = eINSTANCE.getXmlRoundRobinPartitioning();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlUnionPartitioning <em>Xml Union Partitioning</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlUnionPartitioning
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlUnionPartitioning()
		 * @generated
		 */
		public static final EClass XML_UNION_PARTITIONING = eINSTANCE.getXmlUnionPartitioning();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlValuePartitioning <em>Xml Value Partitioning</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlValuePartitioning
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlValuePartitioning()
		 * @generated
		 */
		public static final EClass XML_VALUE_PARTITIONING = eINSTANCE.getXmlValuePartitioning();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType <em>Cache Coordination Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getCacheCoordinationType()
		 * @generated
		 */
		public static final EEnum CACHE_COORDINATION_TYPE = eINSTANCE.getCacheCoordinationType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheType <em>Cache Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheType
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getCacheType()
		 * @generated
		 */
		public static final EEnum CACHE_TYPE = eINSTANCE.getCacheType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingType <em>Xml Change Tracking Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingType
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTrackingType()
		 * @generated
		 */
		public static final EEnum XML_CHANGE_TRACKING_TYPE = eINSTANCE.getXmlChangeTrackingType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlDirection <em>Xml Direction</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlDirection
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlDirection()
		 * @generated
		 */
		public static final EEnum XML_DIRECTION = eINSTANCE.getXmlDirection();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.ExistenceType <em>Existence Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.ExistenceType
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getExistenceType()
		 * @generated
		 */
		public static final EEnum EXISTENCE_TYPE = eINSTANCE.getExistenceType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetchType <em>Xml Join Fetch Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetchType
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetchType()
		 * @generated
		 */
		public static final EEnum XML_JOIN_FETCH_TYPE = eINSTANCE.getXmlJoinFetchType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLockingType <em>Xml Optimistic Locking Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOptimisticLockingType
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOptimisticLockingType()
		 * @generated
		 */
		public static final EEnum XML_OPTIMISTIC_LOCKING_TYPE = eINSTANCE.getXmlOptimisticLockingType();

	}
}
