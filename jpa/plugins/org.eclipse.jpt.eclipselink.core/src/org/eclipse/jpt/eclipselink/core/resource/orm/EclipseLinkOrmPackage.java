/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.jpt.core.resource.orm.OrmPackage;

/**
 * <!-- begin-user-doc -->
 * 
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
 * 
 * @version 2.1
 * @since 2.1
 * 
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory
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
	public static final String eNS_PREFIX = "org.eclipse.jpt.eclipselink.core.resource.orm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLinkOrmPackage eINSTANCE = org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter <em>Xml Named Converter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedConverter()
	 * @generated
	 */
	public static final int XML_NAMED_CONVERTER = 0;

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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizer <em>Xml Customizer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizer
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCustomizer()
	 * @generated
	 */
	public static final int XML_CUSTOMIZER = 13;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder <em>Xml Customizer Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCustomizerHolder()
	 * @generated
	 */
	public static final int XML_CUSTOMIZER_HOLDER = 14;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder <em>Xml Converters Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertersHolder()
	 * @generated
	 */
	public static final int XML_CONVERTERS_HOLDER = 11;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntityMappings()
	 * @generated
	 */
	public static final int XML_ENTITY_MAPPINGS = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTracking <em>Xml Change Tracking</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTracking
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTracking()
	 * @generated
	 */
	public static final int XML_CHANGE_TRACKING = 15;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder <em>Xml Change Tracking Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTrackingHolder()
	 * @generated
	 */
	public static final int XML_CHANGE_TRACKING_HOLDER = 16;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddable <em>Xml Embeddable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddable
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddable()
	 * @generated
	 */
	public static final int XML_EMBEDDABLE = 17;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly <em>Xml Read Only</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlReadOnly()
	 * @generated
	 */
	public static final int XML_READ_ONLY = 18;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay <em>Xml Time Of Day</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTimeOfDay()
	 * @generated
	 */
	public static final int XML_TIME_OF_DAY = 19;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache <em>Xml Cache</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache()
	 * @generated
	 */
	public static final int XML_CACHE = 20;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder <em>Xml Cache Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCacheHolder()
	 * @generated
	 */
	public static final int XML_CACHE_HOLDER = 21;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity <em>Xml Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity()
	 * @generated
	 */
	public static final int XML_ENTITY = 22;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlMappedSuperclass
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMappedSuperclass()
	 * @generated
	 */
	public static final int XML_MAPPED_SUPERCLASS = 23;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable <em>Xml Mutable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMutable()
	 * @generated
	 */
	public static final int XML_MUTABLE = 24;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping <em>Xml Convertible Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertibleMapping()
	 * @generated
	 */
	public static final int XML_CONVERTIBLE_MAPPING = 26;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlId <em>Xml Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlId
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlId()
	 * @generated
	 */
	public static final int XML_ID = 27;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlIdImpl <em>Xml Id Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlIdImpl
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlIdImpl()
	 * @generated
	 */
	public static final int XML_ID_IMPL = 28;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic <em>Xml Basic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBasic()
	 * @generated
	 */
	public static final int XML_BASIC = 29;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicImpl <em>Xml Basic Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicImpl
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBasicImpl()
	 * @generated
	 */
	public static final int XML_BASIC_IMPL = 30;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion <em>Xml Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlVersion()
	 * @generated
	 */
	public static final int XML_VERSION = 31;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersionImpl <em>Xml Version Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersionImpl
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlVersionImpl()
	 * @generated
	 */
	public static final int XML_VERSION_IMPL = 32;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned <em>Xml Private Owned</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrivateOwned()
	 * @generated
	 */
	public static final int XML_PRIVATE_OWNED = 33;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch <em>Xml Join Fetch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetch()
	 * @generated
	 */
	public static final int XML_JOIN_FETCH = 34;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOneToOne()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE = 35;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOneImpl <em>Xml One To One Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOneImpl
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOneToOneImpl()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE_IMPL = 36;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOneToMany()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY = 37;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToManyImpl <em>Xml One To Many Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToManyImpl
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOneToManyImpl()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY_IMPL = 38;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToOne()
	 * @generated
	 */
	public static final int XML_MANY_TO_ONE = 39;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOneImpl <em>Xml Many To One Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOneImpl
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToOneImpl()
	 * @generated
	 */
	public static final int XML_MANY_TO_ONE_IMPL = 40;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToMany()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY = 41;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToManyImpl <em>Xml Many To Many Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToManyImpl
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToManyImpl()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY_IMPL = 42;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter <em>Xml Converter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverter()
	 * @generated
	 */
	public static final int XML_CONVERTER = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter <em>Xml Type Converter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTypeConverter()
	 * @generated
	 */
	public static final int XML_TYPE_CONVERTER = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue <em>Xml Conversion Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConversionValue()
	 * @generated
	 */
	public static final int XML_CONVERSION_VALUE = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter <em>Xml Object Type Converter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlObjectTypeConverter()
	 * @generated
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter <em>Xml Struct Converter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStructConverter()
	 * @generated
	 */
	public static final int XML_STRUCT_CONVERTER = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder <em>Xml Converter Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverterHolder()
	 * @generated
	 */
	public static final int XML_CONVERTER_HOLDER = 25;

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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterImpl <em>Xml Converter Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterImpl
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverterImpl()
	 * @generated
	 */
	public static final int XML_CONVERTER_IMPL = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_IMPL__NAME = XML_CONVERTER__NAME;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_IMPL__CLASS_NAME = XML_CONVERTER__CLASS_NAME;

	/**
	 * The number of structural features of the '<em>Xml Converter Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_IMPL_FEATURE_COUNT = XML_CONVERTER_FEATURE_COUNT + 0;

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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverterImpl <em>Xml Type Converter Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverterImpl
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTypeConverterImpl()
	 * @generated
	 */
	public static final int XML_TYPE_CONVERTER_IMPL = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_CONVERTER_IMPL__NAME = XML_TYPE_CONVERTER__NAME;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_CONVERTER_IMPL__DATA_TYPE = XML_TYPE_CONVERTER__DATA_TYPE;

	/**
	 * The feature id for the '<em><b>Object Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_CONVERTER_IMPL__OBJECT_TYPE = XML_TYPE_CONVERTER__OBJECT_TYPE;

	/**
	 * The number of structural features of the '<em>Xml Type Converter Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_CONVERTER_IMPL_FEATURE_COUNT = XML_TYPE_CONVERTER_FEATURE_COUNT + 0;

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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValueImpl <em>Xml Conversion Value Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValueImpl
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConversionValueImpl()
	 * @generated
	 */
	public static final int XML_CONVERSION_VALUE_IMPL = 6;

	/**
	 * The feature id for the '<em><b>Data Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERSION_VALUE_IMPL__DATA_VALUE = XML_CONVERSION_VALUE__DATA_VALUE;

	/**
	 * The feature id for the '<em><b>Object Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERSION_VALUE_IMPL__OBJECT_VALUE = XML_CONVERSION_VALUE__OBJECT_VALUE;

	/**
	 * The number of structural features of the '<em>Xml Conversion Value Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERSION_VALUE_IMPL_FEATURE_COUNT = XML_CONVERSION_VALUE_FEATURE_COUNT + 0;

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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverterImpl <em>Xml Object Type Converter Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverterImpl
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlObjectTypeConverterImpl()
	 * @generated
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER_IMPL = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER_IMPL__NAME = XML_OBJECT_TYPE_CONVERTER__NAME;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER_IMPL__DATA_TYPE = XML_OBJECT_TYPE_CONVERTER__DATA_TYPE;

	/**
	 * The feature id for the '<em><b>Object Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER_IMPL__OBJECT_TYPE = XML_OBJECT_TYPE_CONVERTER__OBJECT_TYPE;

	/**
	 * The feature id for the '<em><b>Conversion Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER_IMPL__CONVERSION_VALUES = XML_OBJECT_TYPE_CONVERTER__CONVERSION_VALUES;

	/**
	 * The feature id for the '<em><b>Default Object Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER_IMPL__DEFAULT_OBJECT_VALUE = XML_OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE;

	/**
	 * The number of structural features of the '<em>Xml Object Type Converter Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_OBJECT_TYPE_CONVERTER_IMPL_FEATURE_COUNT = XML_OBJECT_TYPE_CONVERTER_FEATURE_COUNT + 0;

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
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverterImpl <em>Xml Struct Converter Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverterImpl
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStructConverterImpl()
	 * @generated
	 */
	public static final int XML_STRUCT_CONVERTER_IMPL = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCT_CONVERTER_IMPL__NAME = XML_STRUCT_CONVERTER__NAME;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCT_CONVERTER_IMPL__CONVERTER = XML_STRUCT_CONVERTER__CONVERTER;

	/**
	 * The number of structural features of the '<em>Xml Struct Converter Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_STRUCT_CONVERTER_IMPL_FEATURE_COUNT = XML_STRUCT_CONVERTER_FEATURE_COUNT + 0;

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
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__VERSION = OrmPackage.XML_ENTITY_MAPPINGS__VERSION;

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
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__ACCESS = OrmPackage.XML_ENTITY_MAPPINGS__ACCESS;

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
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__CONVERTERS = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__TYPE_CONVERTERS = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__OBJECT_TYPE_CONVERTERS = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Struct Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__STRUCT_CONVERTERS = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Xml Entity Mappings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_FEATURE_COUNT = OrmPackage.XML_ENTITY_MAPPINGS_FEATURE_COUNT + 4;

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
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__CLASS_NAME = OrmPackage.XML_EMBEDDABLE__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__ACCESS = OrmPackage.XML_EMBEDDABLE__ACCESS;

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
	 * The feature id for the '<em><b>Customizer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__CUSTOMIZER = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Change Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__CHANGE_TRACKING = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__CONVERTERS = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__TYPE_CONVERTERS = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__OBJECT_TYPE_CONVERTERS = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Struct Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__STRUCT_CONVERTERS = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Xml Embeddable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_FEATURE_COUNT = OrmPackage.XML_EMBEDDABLE_FEATURE_COUNT + 6;

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
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CLASS_NAME = OrmPackage.XML_ENTITY__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ACCESS = OrmPackage.XML_ENTITY__ACCESS;

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
	 * The feature id for the '<em><b>Id Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ID_CLASS = OrmPackage.XML_ENTITY__ID_CLASS;

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
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__READ_ONLY = OrmPackage.XML_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Customizer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CUSTOMIZER = OrmPackage.XML_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Change Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CHANGE_TRACKING = OrmPackage.XML_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CACHE = OrmPackage.XML_ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Existence Checking</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__EXISTENCE_CHECKING = OrmPackage.XML_ENTITY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CONVERTERS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__TYPE_CONVERTERS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__OBJECT_TYPE_CONVERTERS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Struct Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__STRUCT_CONVERTERS = OrmPackage.XML_ENTITY_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Xml Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_FEATURE_COUNT = OrmPackage.XML_ENTITY_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CLASS_NAME = OrmPackage.XML_MAPPED_SUPERCLASS__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ACCESS = OrmPackage.XML_MAPPED_SUPERCLASS__ACCESS;

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
	 * The feature id for the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PRE_PERSIST = OrmPackage.XML_MAPPED_SUPERCLASS__PRE_PERSIST;

	/**
	 * The feature id for the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__POST_PERSIST = OrmPackage.XML_MAPPED_SUPERCLASS__POST_PERSIST;

	/**
	 * The feature id for the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PRE_REMOVE = OrmPackage.XML_MAPPED_SUPERCLASS__PRE_REMOVE;

	/**
	 * The feature id for the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__POST_REMOVE = OrmPackage.XML_MAPPED_SUPERCLASS__POST_REMOVE;

	/**
	 * The feature id for the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__PRE_UPDATE = OrmPackage.XML_MAPPED_SUPERCLASS__PRE_UPDATE;

	/**
	 * The feature id for the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__POST_UPDATE = OrmPackage.XML_MAPPED_SUPERCLASS__POST_UPDATE;

	/**
	 * The feature id for the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__POST_LOAD = OrmPackage.XML_MAPPED_SUPERCLASS__POST_LOAD;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__READ_ONLY = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Customizer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CUSTOMIZER = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Change Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CHANGE_TRACKING = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CACHE = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Existence Checking</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__EXISTENCE_CHECKING = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CONVERTERS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__TYPE_CONVERTERS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__OBJECT_TYPE_CONVERTERS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Struct Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__STRUCT_CONVERTERS = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Xml Mapped Superclass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_FEATURE_COUNT = OrmPackage.XML_MAPPED_SUPERCLASS_FEATURE_COUNT + 9;

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
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__GENERATED_VALUE = OrmPackage.XML_ID__GENERATED_VALUE;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__TABLE_GENERATOR = OrmPackage.XML_ID__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__SEQUENCE_GENERATOR = OrmPackage.XML_ID__SEQUENCE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__MUTABLE = OrmPackage.XML_ID_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__CONVERTER = OrmPackage.XML_ID_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__TYPE_CONVERTER = OrmPackage.XML_ID_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__OBJECT_TYPE_CONVERTER = OrmPackage.XML_ID_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__STRUCT_CONVERTER = OrmPackage.XML_ID_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__CONVERT = OrmPackage.XML_ID_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Xml Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_FEATURE_COUNT = OrmPackage.XML_ID_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__NAME = OrmPackage.XML_ID_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__COLUMN = OrmPackage.XML_ID_IMPL__COLUMN;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__LOB = OrmPackage.XML_ID_IMPL__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__TEMPORAL = OrmPackage.XML_ID_IMPL__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__ENUMERATED = OrmPackage.XML_ID_IMPL__ENUMERATED;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__GENERATED_VALUE = OrmPackage.XML_ID_IMPL__GENERATED_VALUE;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__TABLE_GENERATOR = OrmPackage.XML_ID_IMPL__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__SEQUENCE_GENERATOR = OrmPackage.XML_ID_IMPL__SEQUENCE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__MUTABLE = OrmPackage.XML_ID_IMPL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__CONVERTER = OrmPackage.XML_ID_IMPL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__TYPE_CONVERTER = OrmPackage.XML_ID_IMPL_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__OBJECT_TYPE_CONVERTER = OrmPackage.XML_ID_IMPL_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__STRUCT_CONVERTER = OrmPackage.XML_ID_IMPL_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL__CONVERT = OrmPackage.XML_ID_IMPL_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Xml Id Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_IMPL_FEATURE_COUNT = OrmPackage.XML_ID_IMPL_FEATURE_COUNT + 6;

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
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__MUTABLE = OrmPackage.XML_BASIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__CONVERTER = OrmPackage.XML_BASIC_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__TYPE_CONVERTER = OrmPackage.XML_BASIC_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__OBJECT_TYPE_CONVERTER = OrmPackage.XML_BASIC_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__STRUCT_CONVERTER = OrmPackage.XML_BASIC_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__CONVERT = OrmPackage.XML_BASIC_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Xml Basic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_FEATURE_COUNT = OrmPackage.XML_BASIC_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__NAME = OrmPackage.XML_BASIC_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__COLUMN = OrmPackage.XML_BASIC_IMPL__COLUMN;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__LOB = OrmPackage.XML_BASIC_IMPL__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__TEMPORAL = OrmPackage.XML_BASIC_IMPL__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__ENUMERATED = OrmPackage.XML_BASIC_IMPL__ENUMERATED;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__FETCH = OrmPackage.XML_BASIC_IMPL__FETCH;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__OPTIONAL = OrmPackage.XML_BASIC_IMPL__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__MUTABLE = OrmPackage.XML_BASIC_IMPL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__CONVERTER = OrmPackage.XML_BASIC_IMPL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__TYPE_CONVERTER = OrmPackage.XML_BASIC_IMPL_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__OBJECT_TYPE_CONVERTER = OrmPackage.XML_BASIC_IMPL_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__STRUCT_CONVERTER = OrmPackage.XML_BASIC_IMPL_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL__CONVERT = OrmPackage.XML_BASIC_IMPL_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Xml Basic Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_IMPL_FEATURE_COUNT = OrmPackage.XML_BASIC_IMPL_FEATURE_COUNT + 6;

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
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__MUTABLE = OrmPackage.XML_VERSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__CONVERTER = OrmPackage.XML_VERSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__TYPE_CONVERTER = OrmPackage.XML_VERSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__OBJECT_TYPE_CONVERTER = OrmPackage.XML_VERSION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__STRUCT_CONVERTER = OrmPackage.XML_VERSION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__CONVERT = OrmPackage.XML_VERSION_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Xml Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_FEATURE_COUNT = OrmPackage.XML_VERSION_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__NAME = OrmPackage.XML_VERSION_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__COLUMN = OrmPackage.XML_VERSION_IMPL__COLUMN;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__LOB = OrmPackage.XML_VERSION_IMPL__LOB;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__TEMPORAL = OrmPackage.XML_VERSION_IMPL__TEMPORAL;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__ENUMERATED = OrmPackage.XML_VERSION_IMPL__ENUMERATED;

	/**
	 * The feature id for the '<em><b>Mutable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__MUTABLE = OrmPackage.XML_VERSION_IMPL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__CONVERTER = OrmPackage.XML_VERSION_IMPL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__TYPE_CONVERTER = OrmPackage.XML_VERSION_IMPL_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__OBJECT_TYPE_CONVERTER = OrmPackage.XML_VERSION_IMPL_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__STRUCT_CONVERTER = OrmPackage.XML_VERSION_IMPL_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL__CONVERT = OrmPackage.XML_VERSION_IMPL_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Xml Version Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_IMPL_FEATURE_COUNT = OrmPackage.XML_VERSION_IMPL_FEATURE_COUNT + 6;

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
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__JOIN_TABLE = OrmPackage.XML_ONE_TO_ONE__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__CASCADE = OrmPackage.XML_ONE_TO_ONE__CASCADE;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__OPTIONAL = OrmPackage.XML_ONE_TO_ONE__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__JOIN_COLUMNS = OrmPackage.XML_ONE_TO_ONE__JOIN_COLUMNS;

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
	 * The feature id for the '<em><b>Private Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__PRIVATE_OWNED = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__JOIN_FETCH = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml One To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_FEATURE_COUNT = OrmPackage.XML_ONE_TO_ONE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__NAME = OrmPackage.XML_ONE_TO_ONE_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__TARGET_ENTITY = OrmPackage.XML_ONE_TO_ONE_IMPL__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__FETCH = OrmPackage.XML_ONE_TO_ONE_IMPL__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__JOIN_TABLE = OrmPackage.XML_ONE_TO_ONE_IMPL__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__CASCADE = OrmPackage.XML_ONE_TO_ONE_IMPL__CASCADE;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__OPTIONAL = OrmPackage.XML_ONE_TO_ONE_IMPL__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__JOIN_COLUMNS = OrmPackage.XML_ONE_TO_ONE_IMPL__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__MAPPED_BY = OrmPackage.XML_ONE_TO_ONE_IMPL__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__PRIMARY_KEY_JOIN_COLUMNS = OrmPackage.XML_ONE_TO_ONE_IMPL__PRIMARY_KEY_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Private Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__PRIVATE_OWNED = OrmPackage.XML_ONE_TO_ONE_IMPL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL__JOIN_FETCH = OrmPackage.XML_ONE_TO_ONE_IMPL_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml One To One Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_IMPL_FEATURE_COUNT = OrmPackage.XML_ONE_TO_ONE_IMPL_FEATURE_COUNT + 2;

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
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__JOIN_TABLE = OrmPackage.XML_ONE_TO_MANY__JOIN_TABLE;

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
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ORDER_BY = OrmPackage.XML_ONE_TO_MANY__ORDER_BY;

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
	 * The feature id for the '<em><b>Private Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__PRIVATE_OWNED = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__JOIN_FETCH = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml One To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_FEATURE_COUNT = OrmPackage.XML_ONE_TO_MANY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__NAME = OrmPackage.XML_ONE_TO_MANY_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__TARGET_ENTITY = OrmPackage.XML_ONE_TO_MANY_IMPL__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__FETCH = OrmPackage.XML_ONE_TO_MANY_IMPL__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__JOIN_TABLE = OrmPackage.XML_ONE_TO_MANY_IMPL__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__CASCADE = OrmPackage.XML_ONE_TO_MANY_IMPL__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__MAPPED_BY = OrmPackage.XML_ONE_TO_MANY_IMPL__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__ORDER_BY = OrmPackage.XML_ONE_TO_MANY_IMPL__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__MAP_KEY = OrmPackage.XML_ONE_TO_MANY_IMPL__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__JOIN_COLUMNS = OrmPackage.XML_ONE_TO_MANY_IMPL__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Private Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__PRIVATE_OWNED = OrmPackage.XML_ONE_TO_MANY_IMPL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL__JOIN_FETCH = OrmPackage.XML_ONE_TO_MANY_IMPL_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml One To Many Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_IMPL_FEATURE_COUNT = OrmPackage.XML_ONE_TO_MANY_IMPL_FEATURE_COUNT + 2;

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
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__JOIN_TABLE = OrmPackage.XML_MANY_TO_ONE__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__CASCADE = OrmPackage.XML_MANY_TO_ONE__CASCADE;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__OPTIONAL = OrmPackage.XML_MANY_TO_ONE__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__JOIN_COLUMNS = OrmPackage.XML_MANY_TO_ONE__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__JOIN_FETCH = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Many To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_FEATURE_COUNT = OrmPackage.XML_MANY_TO_ONE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__NAME = OrmPackage.XML_MANY_TO_ONE_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__TARGET_ENTITY = OrmPackage.XML_MANY_TO_ONE_IMPL__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__FETCH = OrmPackage.XML_MANY_TO_ONE_IMPL__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__JOIN_TABLE = OrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__CASCADE = OrmPackage.XML_MANY_TO_ONE_IMPL__CASCADE;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__OPTIONAL = OrmPackage.XML_MANY_TO_ONE_IMPL__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__JOIN_COLUMNS = OrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL__JOIN_FETCH = OrmPackage.XML_MANY_TO_ONE_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Many To One Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_IMPL_FEATURE_COUNT = OrmPackage.XML_MANY_TO_ONE_IMPL_FEATURE_COUNT + 1;

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
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__JOIN_TABLE = OrmPackage.XML_MANY_TO_MANY__JOIN_TABLE;

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
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ORDER_BY = OrmPackage.XML_MANY_TO_MANY__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY = OrmPackage.XML_MANY_TO_MANY__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__JOIN_FETCH = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Many To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_FEATURE_COUNT = OrmPackage.XML_MANY_TO_MANY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__NAME = OrmPackage.XML_MANY_TO_MANY_IMPL__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__TARGET_ENTITY = OrmPackage.XML_MANY_TO_MANY_IMPL__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__FETCH = OrmPackage.XML_MANY_TO_MANY_IMPL__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__JOIN_TABLE = OrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__CASCADE = OrmPackage.XML_MANY_TO_MANY_IMPL__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__MAPPED_BY = OrmPackage.XML_MANY_TO_MANY_IMPL__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__ORDER_BY = OrmPackage.XML_MANY_TO_MANY_IMPL__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__MAP_KEY = OrmPackage.XML_MANY_TO_MANY_IMPL__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Join Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL__JOIN_FETCH = OrmPackage.XML_MANY_TO_MANY_IMPL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Many To Many Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_IMPL_FEATURE_COUNT = OrmPackage.XML_MANY_TO_MANY_IMPL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingType <em>Xml Change Tracking Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingType
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTrackingType()
	 * @generated
	 */
	public static final int XML_CHANGE_TRACKING_TYPE = 43;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.CacheType <em>Cache Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.CacheType
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getCacheType()
	 * @generated
	 */
	public static final int CACHE_TYPE = 44;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.CacheCoordinationType <em>Cache Coordination Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.CacheCoordinationType
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getCacheCoordinationType()
	 * @generated
	 */
	public static final int CACHE_COORDINATION_TYPE = 45;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType <em>Xml Join Fetch Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetchType()
	 * @generated
	 */
	public static final int XML_JOIN_FETCH_TYPE = 47;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedConverterEClass = null;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.ExistenceType <em>Existence Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.ExistenceType
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getExistenceType()
	 * @generated
	 */
	public static final int EXISTENCE_TYPE = 46;

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
	private EClass xmlConvertersHolderEClass = null;

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
	private EClass xmlEmbeddableEClass = null;

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
	private EClass xmlTimeOfDayEClass = null;

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
	private EClass xmlEntityEClass = null;

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
	private EClass xmlMutableEClass = null;

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
	private EClass xmlPrivateOwnedEClass = null;

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
	private EEnum xmlChangeTrackingTypeEEnum = null;

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
	private EClass xmlConverterImplEClass = null;

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
	private EClass xmlTypeConverterImplEClass = null;

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
	private EClass xmlConversionValueImplEClass = null;

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
	private EClass xmlObjectTypeConverterImplEClass = null;

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
	private EClass xmlStructConverterImplEClass = null;

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
	private EEnum cacheTypeEEnum = null;

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
	private EEnum xmlJoinFetchTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum existenceTypeEEnum = null;

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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#eNS_URI
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
	public static EclipseLinkOrmPackage init()
	{
		if (isInited) return (EclipseLinkOrmPackage)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI);

		// Obtain or create and register package
		EclipseLinkOrmPackage theEclipseLinkOrmPackage = (EclipseLinkOrmPackage)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof EclipseLinkOrmPackage ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new EclipseLinkOrmPackage());

		isInited = true;

		// Initialize simple dependencies
		OrmPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theEclipseLinkOrmPackage.createPackageContents();

		// Initialize created meta-data
		theEclipseLinkOrmPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEclipseLinkOrmPackage.freeze();

		return theEclipseLinkOrmPackage;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter <em>Xml Named Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Converter</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter
	 * @generated
	 */
	public EClass getXmlNamedConverter()
	{
		return xmlNamedConverterEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter#getName()
	 * @see #getXmlNamedConverter()
	 * @generated
	 */
	public EAttribute getXmlNamedConverter_Name()
	{
		return (EAttribute)xmlNamedConverterEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizer <em>Xml Customizer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Customizer</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizer
	 * @generated
	 */
	public EClass getXmlCustomizer()
	{
		return xmlCustomizerEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizer#getCustomizerClassName <em>Customizer Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Customizer Class Name</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizer#getCustomizerClassName()
	 * @see #getXmlCustomizer()
	 * @generated
	 */
	public EAttribute getXmlCustomizer_CustomizerClassName()
	{
		return (EAttribute)xmlCustomizerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder <em>Xml Customizer Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Customizer Holder</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder
	 * @generated
	 */
	public EClass getXmlCustomizerHolder()
	{
		return xmlCustomizerHolderEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder#getCustomizer <em>Customizer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Customizer</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder#getCustomizer()
	 * @see #getXmlCustomizerHolder()
	 * @generated
	 */
	public EReference getXmlCustomizerHolder_Customizer()
	{
		return (EReference)xmlCustomizerHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTracking <em>Xml Change Tracking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Change Tracking</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTracking
	 * @generated
	 */
	public EClass getXmlChangeTracking()
	{
		return xmlChangeTrackingEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTracking#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTracking#getType()
	 * @see #getXmlChangeTracking()
	 * @generated
	 */
	public EAttribute getXmlChangeTracking_Type()
	{
		return (EAttribute)xmlChangeTrackingEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder <em>Xml Change Tracking Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Change Tracking Holder</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder
	 * @generated
	 */
	public EClass getXmlChangeTrackingHolder()
	{
		return xmlChangeTrackingHolderEClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder#getChangeTracking <em>Change Tracking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Change Tracking</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder#getChangeTracking()
	 * @see #getXmlChangeTrackingHolder()
	 * @generated
	 */
	public EReference getXmlChangeTrackingHolder_ChangeTracking()
	{
		return (EReference)xmlChangeTrackingHolderEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder <em>Xml Converters Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Converters Holder</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder
	 * @generated
	 */
	public EClass getXmlConvertersHolder()
	{
		return xmlConvertersHolderEClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder#getConverters <em>Converters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Converters</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder#getConverters()
	 * @see #getXmlConvertersHolder()
	 * @generated
	 */
	public EReference getXmlConvertersHolder_Converters()
	{
		return (EReference)xmlConvertersHolderEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder#getTypeConverters <em>Type Converters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Type Converters</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder#getTypeConverters()
	 * @see #getXmlConvertersHolder()
	 * @generated
	 */
	public EReference getXmlConvertersHolder_TypeConverters()
	{
		return (EReference)xmlConvertersHolderEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder#getObjectTypeConverters <em>Object Type Converters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Object Type Converters</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder#getObjectTypeConverters()
	 * @see #getXmlConvertersHolder()
	 * @generated
	 */
	public EReference getXmlConvertersHolder_ObjectTypeConverters()
	{
		return (EReference)xmlConvertersHolderEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder#getStructConverters <em>Struct Converters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Struct Converters</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder#getStructConverters()
	 * @see #getXmlConvertersHolder()
	 * @generated
	 */
	public EReference getXmlConvertersHolder_StructConverters()
	{
		return (EReference)xmlConvertersHolderEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity Mappings</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings
	 * @generated
	 */
	public EClass getXmlEntityMappings()
	{
		return xmlEntityMappingsEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddable <em>Xml Embeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embeddable</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddable
	 * @generated
	 */
	public EClass getXmlEmbeddable()
	{
		return xmlEmbeddableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly <em>Xml Read Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Read Only</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly
	 * @generated
	 */
	public EClass getXmlReadOnly()
	{
		return xmlReadOnlyEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly#getReadOnly <em>Read Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Read Only</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly#getReadOnly()
	 * @see #getXmlReadOnly()
	 * @generated
	 */
	public EAttribute getXmlReadOnly_ReadOnly()
	{
		return (EAttribute)xmlReadOnlyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay <em>Xml Time Of Day</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Time Of Day</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay
	 * @generated
	 */
	public EClass getXmlTimeOfDay()
	{
		return xmlTimeOfDayEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getHour <em>Hour</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hour</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getHour()
	 * @see #getXmlTimeOfDay()
	 * @generated
	 */
	public EAttribute getXmlTimeOfDay_Hour()
	{
		return (EAttribute)xmlTimeOfDayEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getMinute <em>Minute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Minute</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getMinute()
	 * @see #getXmlTimeOfDay()
	 * @generated
	 */
	public EAttribute getXmlTimeOfDay_Minute()
	{
		return (EAttribute)xmlTimeOfDayEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getSecond <em>Second</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Second</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getSecond()
	 * @see #getXmlTimeOfDay()
	 * @generated
	 */
	public EAttribute getXmlTimeOfDay_Second()
	{
		return (EAttribute)xmlTimeOfDayEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getMillisecond <em>Millisecond</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Millisecond</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay#getMillisecond()
	 * @see #getXmlTimeOfDay()
	 * @generated
	 */
	public EAttribute getXmlTimeOfDay_Millisecond()
	{
		return (EAttribute)xmlTimeOfDayEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache <em>Xml Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Cache</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache
	 * @generated
	 */
	public EClass getXmlCache()
	{
		return xmlCacheEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getExpiry <em>Expiry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expiry</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getExpiry()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_Expiry()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getSize()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_Size()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getShared <em>Shared</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Shared</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getShared()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_Shared()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getType()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_Type()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getAlwaysRefresh <em>Always Refresh</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Always Refresh</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getAlwaysRefresh()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_AlwaysRefresh()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getRefreshOnlyIfNewer <em>Refresh Only If Newer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Refresh Only If Newer</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getRefreshOnlyIfNewer()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_RefreshOnlyIfNewer()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getDisableHits <em>Disable Hits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Disable Hits</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getDisableHits()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_DisableHits()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getCoordinationType <em>Coordination Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Coordination Type</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getCoordinationType()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EAttribute getXmlCache_CoordinationType()
	{
		return (EAttribute)xmlCacheEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getExpiryTimeOfDay <em>Expiry Time Of Day</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expiry Time Of Day</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache#getExpiryTimeOfDay()
	 * @see #getXmlCache()
	 * @generated
	 */
	public EReference getXmlCache_ExpiryTimeOfDay()
	{
		return (EReference)xmlCacheEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder <em>Xml Cache Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Cache Holder</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder
	 * @generated
	 */
	public EClass getXmlCacheHolder()
	{
		return xmlCacheHolderEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder#getCache <em>Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder#getCache()
	 * @see #getXmlCacheHolder()
	 * @generated
	 */
	public EReference getXmlCacheHolder_Cache()
	{
		return (EReference)xmlCacheHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder#getExistenceChecking <em>Existence Checking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Existence Checking</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder#getExistenceChecking()
	 * @see #getXmlCacheHolder()
	 * @generated
	 */
	public EAttribute getXmlCacheHolder_ExistenceChecking()
	{
		return (EAttribute)xmlCacheHolderEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity <em>Xml Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity
	 * @generated
	 */
	public EClass getXmlEntity()
	{
		return xmlEntityEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mapped Superclass</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlMappedSuperclass
	 * @generated
	 */
	public EClass getXmlMappedSuperclass()
	{
		return xmlMappedSuperclassEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable <em>Xml Mutable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mutable</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable
	 * @generated
	 */
	public EClass getXmlMutable()
	{
		return xmlMutableEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable#getMutable <em>Mutable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mutable</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable#getMutable()
	 * @see #getXmlMutable()
	 * @generated
	 */
	public EAttribute getXmlMutable_Mutable()
	{
		return (EAttribute)xmlMutableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping <em>Xml Convertible Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Convertible Mapping</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping
	 * @generated
	 */
	public EClass getXmlConvertibleMapping()
	{
		return xmlConvertibleMappingEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping#getConvert <em>Convert</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Convert</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping#getConvert()
	 * @see #getXmlConvertibleMapping()
	 * @generated
	 */
	public EAttribute getXmlConvertibleMapping_Convert()
	{
		return (EAttribute)xmlConvertibleMappingEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlId <em>Xml Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlId
	 * @generated
	 */
	public EClass getXmlId()
	{
		return xmlIdEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlIdImpl <em>Xml Id Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlIdImpl
	 * @generated
	 */
	public EClass getXmlIdImpl()
	{
		return xmlIdImplEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic <em>Xml Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic
	 * @generated
	 */
	public EClass getXmlBasic()
	{
		return xmlBasicEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicImpl <em>Xml Basic Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicImpl
	 * @generated
	 */
	public EClass getXmlBasicImpl()
	{
		return xmlBasicImplEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Version</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion
	 * @generated
	 */
	public EClass getXmlVersion()
	{
		return xmlVersionEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersionImpl <em>Xml Version Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Version Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersionImpl
	 * @generated
	 */
	public EClass getXmlVersionImpl()
	{
		return xmlVersionImplEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned <em>Xml Private Owned</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Private Owned</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned
	 * @generated
	 */
	public EClass getXmlPrivateOwned()
	{
		return xmlPrivateOwnedEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned#isPrivateOwned <em>Private Owned</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Private Owned</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned#isPrivateOwned()
	 * @see #getXmlPrivateOwned()
	 * @generated
	 */
	public EAttribute getXmlPrivateOwned_PrivateOwned()
	{
		return (EAttribute)xmlPrivateOwnedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch <em>Xml Join Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Join Fetch</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch
	 * @generated
	 */
	public EClass getXmlJoinFetch()
	{
		return xmlJoinFetchEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch#getJoinFetch <em>Join Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Join Fetch</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch#getJoinFetch()
	 * @see #getXmlJoinFetch()
	 * @generated
	 */
	public EAttribute getXmlJoinFetch_JoinFetch()
	{
		return (EAttribute)xmlJoinFetchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne
	 * @generated
	 */
	public EClass getXmlOneToOne()
	{
		return xmlOneToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOneImpl <em>Xml One To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOneImpl
	 * @generated
	 */
	public EClass getXmlOneToOneImpl()
	{
		return xmlOneToOneImplEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany
	 * @generated
	 */
	public EClass getXmlOneToMany()
	{
		return xmlOneToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToManyImpl <em>Xml One To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToManyImpl
	 * @generated
	 */
	public EClass getXmlOneToManyImpl()
	{
		return xmlOneToManyImplEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To One</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne
	 * @generated
	 */
	public EClass getXmlManyToOne()
	{
		return xmlManyToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOneImpl <em>Xml Many To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To One Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOneImpl
	 * @generated
	 */
	public EClass getXmlManyToOneImpl()
	{
		return xmlManyToOneImplEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany
	 * @generated
	 */
	public EClass getXmlManyToMany()
	{
		return xmlManyToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToManyImpl <em>Xml Many To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToManyImpl
	 * @generated
	 */
	public EClass getXmlManyToManyImpl()
	{
		return xmlManyToManyImplEClass;
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingType <em>Xml Change Tracking Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Xml Change Tracking Type</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingType
	 * @generated
	 */
	public EEnum getXmlChangeTrackingType()
	{
		return xmlChangeTrackingTypeEEnum;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter <em>Xml Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Converter</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter
	 * @generated
	 */
	public EClass getXmlConverter()
	{
		return xmlConverterEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter#getClassName()
	 * @see #getXmlConverter()
	 * @generated
	 */
	public EAttribute getXmlConverter_ClassName()
	{
		return (EAttribute)xmlConverterEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterImpl <em>Xml Converter Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Converter Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterImpl
	 * @generated
	 */
	public EClass getXmlConverterImpl()
	{
		return xmlConverterImplEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter <em>Xml Type Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Type Converter</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter
	 * @generated
	 */
	public EClass getXmlTypeConverter()
	{
		return xmlTypeConverterEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter#getDataType()
	 * @see #getXmlTypeConverter()
	 * @generated
	 */
	public EAttribute getXmlTypeConverter_DataType()
	{
		return (EAttribute)xmlTypeConverterEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter#getObjectType <em>Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Type</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter#getObjectType()
	 * @see #getXmlTypeConverter()
	 * @generated
	 */
	public EAttribute getXmlTypeConverter_ObjectType()
	{
		return (EAttribute)xmlTypeConverterEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverterImpl <em>Xml Type Converter Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Type Converter Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverterImpl
	 * @generated
	 */
	public EClass getXmlTypeConverterImpl()
	{
		return xmlTypeConverterImplEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue <em>Xml Conversion Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Conversion Value</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue
	 * @generated
	 */
	public EClass getXmlConversionValue()
	{
		return xmlConversionValueEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue#getDataValue <em>Data Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Value</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue#getDataValue()
	 * @see #getXmlConversionValue()
	 * @generated
	 */
	public EAttribute getXmlConversionValue_DataValue()
	{
		return (EAttribute)xmlConversionValueEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue#getObjectValue <em>Object Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Value</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue#getObjectValue()
	 * @see #getXmlConversionValue()
	 * @generated
	 */
	public EAttribute getXmlConversionValue_ObjectValue()
	{
		return (EAttribute)xmlConversionValueEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValueImpl <em>Xml Conversion Value Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Conversion Value Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValueImpl
	 * @generated
	 */
	public EClass getXmlConversionValueImpl()
	{
		return xmlConversionValueImplEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter <em>Xml Object Type Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Object Type Converter</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter
	 * @generated
	 */
	public EClass getXmlObjectTypeConverter()
	{
		return xmlObjectTypeConverterEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter#getDataType()
	 * @see #getXmlObjectTypeConverter()
	 * @generated
	 */
	public EAttribute getXmlObjectTypeConverter_DataType()
	{
		return (EAttribute)xmlObjectTypeConverterEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter#getObjectType <em>Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Type</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter#getObjectType()
	 * @see #getXmlObjectTypeConverter()
	 * @generated
	 */
	public EAttribute getXmlObjectTypeConverter_ObjectType()
	{
		return (EAttribute)xmlObjectTypeConverterEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverterImpl <em>Xml Object Type Converter Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Object Type Converter Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverterImpl
	 * @generated
	 */
	public EClass getXmlObjectTypeConverterImpl()
	{
		return xmlObjectTypeConverterImplEClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter#getConversionValues <em>Conversion Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Conversion Values</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter#getConversionValues()
	 * @see #getXmlObjectTypeConverter()
	 * @generated
	 */
	public EReference getXmlObjectTypeConverter_ConversionValues()
	{
		return (EReference)xmlObjectTypeConverterEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter#getDefaultObjectValue <em>Default Object Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Object Value</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter#getDefaultObjectValue()
	 * @see #getXmlObjectTypeConverter()
	 * @generated
	 */
	public EAttribute getXmlObjectTypeConverter_DefaultObjectValue()
	{
		return (EAttribute)xmlObjectTypeConverterEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter <em>Xml Struct Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Struct Converter</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter
	 * @generated
	 */
	public EClass getXmlStructConverter()
	{
		return xmlStructConverterEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter#getConverter <em>Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Converter</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter#getConverter()
	 * @see #getXmlStructConverter()
	 * @generated
	 */
	public EAttribute getXmlStructConverter_Converter()
	{
		return (EAttribute)xmlStructConverterEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverterImpl <em>Xml Struct Converter Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Struct Converter Impl</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverterImpl
	 * @generated
	 */
	public EClass getXmlStructConverterImpl()
	{
		return xmlStructConverterImplEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder <em>Xml Converter Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Converter Holder</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder
	 * @generated
	 */
	public EClass getXmlConverterHolder()
	{
		return xmlConverterHolderEClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder#getConverter <em>Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Converter</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder#getConverter()
	 * @see #getXmlConverterHolder()
	 * @generated
	 */
	public EReference getXmlConverterHolder_Converter()
	{
		return (EReference)xmlConverterHolderEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder#getTypeConverter <em>Type Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type Converter</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder#getTypeConverter()
	 * @see #getXmlConverterHolder()
	 * @generated
	 */
	public EReference getXmlConverterHolder_TypeConverter()
	{
		return (EReference)xmlConverterHolderEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder#getObjectTypeConverter <em>Object Type Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Object Type Converter</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder#getObjectTypeConverter()
	 * @see #getXmlConverterHolder()
	 * @generated
	 */
	public EReference getXmlConverterHolder_ObjectTypeConverter()
	{
		return (EReference)xmlConverterHolderEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder#getStructConverter <em>Struct Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Struct Converter</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder#getStructConverter()
	 * @see #getXmlConverterHolder()
	 * @generated
	 */
	public EReference getXmlConverterHolder_StructConverter()
	{
		return (EReference)xmlConverterHolderEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.eclipselink.core.resource.orm.CacheType <em>Cache Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Cache Type</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.CacheType
	 * @generated
	 */
	public EEnum getCacheType()
	{
		return cacheTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.eclipselink.core.resource.orm.CacheCoordinationType <em>Cache Coordination Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Cache Coordination Type</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.CacheCoordinationType
	 * @generated
	 */
	public EEnum getCacheCoordinationType()
	{
		return cacheCoordinationTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType <em>Xml Join Fetch Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Xml Join Fetch Type</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType
	 * @generated
	 */
	public EEnum getXmlJoinFetchType()
	{
		return xmlJoinFetchTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.eclipselink.core.resource.orm.ExistenceType <em>Existence Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Existence Type</em>'.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.ExistenceType
	 * @generated
	 */
	public EEnum getExistenceType()
	{
		return existenceTypeEEnum;
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
		xmlNamedConverterEClass = createEClass(XML_NAMED_CONVERTER);
		createEAttribute(xmlNamedConverterEClass, XML_NAMED_CONVERTER__NAME);

		xmlConverterEClass = createEClass(XML_CONVERTER);
		createEAttribute(xmlConverterEClass, XML_CONVERTER__CLASS_NAME);

		xmlConverterImplEClass = createEClass(XML_CONVERTER_IMPL);

		xmlTypeConverterEClass = createEClass(XML_TYPE_CONVERTER);
		createEAttribute(xmlTypeConverterEClass, XML_TYPE_CONVERTER__DATA_TYPE);
		createEAttribute(xmlTypeConverterEClass, XML_TYPE_CONVERTER__OBJECT_TYPE);

		xmlTypeConverterImplEClass = createEClass(XML_TYPE_CONVERTER_IMPL);

		xmlConversionValueEClass = createEClass(XML_CONVERSION_VALUE);
		createEAttribute(xmlConversionValueEClass, XML_CONVERSION_VALUE__DATA_VALUE);
		createEAttribute(xmlConversionValueEClass, XML_CONVERSION_VALUE__OBJECT_VALUE);

		xmlConversionValueImplEClass = createEClass(XML_CONVERSION_VALUE_IMPL);

		xmlObjectTypeConverterEClass = createEClass(XML_OBJECT_TYPE_CONVERTER);
		createEAttribute(xmlObjectTypeConverterEClass, XML_OBJECT_TYPE_CONVERTER__DATA_TYPE);
		createEAttribute(xmlObjectTypeConverterEClass, XML_OBJECT_TYPE_CONVERTER__OBJECT_TYPE);
		createEReference(xmlObjectTypeConverterEClass, XML_OBJECT_TYPE_CONVERTER__CONVERSION_VALUES);
		createEAttribute(xmlObjectTypeConverterEClass, XML_OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE);

		xmlObjectTypeConverterImplEClass = createEClass(XML_OBJECT_TYPE_CONVERTER_IMPL);

		xmlStructConverterEClass = createEClass(XML_STRUCT_CONVERTER);
		createEAttribute(xmlStructConverterEClass, XML_STRUCT_CONVERTER__CONVERTER);

		xmlStructConverterImplEClass = createEClass(XML_STRUCT_CONVERTER_IMPL);

		xmlConvertersHolderEClass = createEClass(XML_CONVERTERS_HOLDER);
		createEReference(xmlConvertersHolderEClass, XML_CONVERTERS_HOLDER__CONVERTERS);
		createEReference(xmlConvertersHolderEClass, XML_CONVERTERS_HOLDER__TYPE_CONVERTERS);
		createEReference(xmlConvertersHolderEClass, XML_CONVERTERS_HOLDER__OBJECT_TYPE_CONVERTERS);
		createEReference(xmlConvertersHolderEClass, XML_CONVERTERS_HOLDER__STRUCT_CONVERTERS);

		xmlEntityMappingsEClass = createEClass(XML_ENTITY_MAPPINGS);

		xmlCustomizerEClass = createEClass(XML_CUSTOMIZER);
		createEAttribute(xmlCustomizerEClass, XML_CUSTOMIZER__CUSTOMIZER_CLASS_NAME);

		xmlCustomizerHolderEClass = createEClass(XML_CUSTOMIZER_HOLDER);
		createEReference(xmlCustomizerHolderEClass, XML_CUSTOMIZER_HOLDER__CUSTOMIZER);

		xmlChangeTrackingEClass = createEClass(XML_CHANGE_TRACKING);
		createEAttribute(xmlChangeTrackingEClass, XML_CHANGE_TRACKING__TYPE);

		xmlChangeTrackingHolderEClass = createEClass(XML_CHANGE_TRACKING_HOLDER);
		createEReference(xmlChangeTrackingHolderEClass, XML_CHANGE_TRACKING_HOLDER__CHANGE_TRACKING);

		xmlEmbeddableEClass = createEClass(XML_EMBEDDABLE);

		xmlReadOnlyEClass = createEClass(XML_READ_ONLY);
		createEAttribute(xmlReadOnlyEClass, XML_READ_ONLY__READ_ONLY);

		xmlTimeOfDayEClass = createEClass(XML_TIME_OF_DAY);
		createEAttribute(xmlTimeOfDayEClass, XML_TIME_OF_DAY__HOUR);
		createEAttribute(xmlTimeOfDayEClass, XML_TIME_OF_DAY__MINUTE);
		createEAttribute(xmlTimeOfDayEClass, XML_TIME_OF_DAY__SECOND);
		createEAttribute(xmlTimeOfDayEClass, XML_TIME_OF_DAY__MILLISECOND);

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

		xmlEntityEClass = createEClass(XML_ENTITY);

		xmlMappedSuperclassEClass = createEClass(XML_MAPPED_SUPERCLASS);

		xmlMutableEClass = createEClass(XML_MUTABLE);
		createEAttribute(xmlMutableEClass, XML_MUTABLE__MUTABLE);

		xmlConverterHolderEClass = createEClass(XML_CONVERTER_HOLDER);
		createEReference(xmlConverterHolderEClass, XML_CONVERTER_HOLDER__CONVERTER);
		createEReference(xmlConverterHolderEClass, XML_CONVERTER_HOLDER__TYPE_CONVERTER);
		createEReference(xmlConverterHolderEClass, XML_CONVERTER_HOLDER__OBJECT_TYPE_CONVERTER);
		createEReference(xmlConverterHolderEClass, XML_CONVERTER_HOLDER__STRUCT_CONVERTER);

		xmlConvertibleMappingEClass = createEClass(XML_CONVERTIBLE_MAPPING);
		createEAttribute(xmlConvertibleMappingEClass, XML_CONVERTIBLE_MAPPING__CONVERT);

		xmlIdEClass = createEClass(XML_ID);

		xmlIdImplEClass = createEClass(XML_ID_IMPL);

		xmlBasicEClass = createEClass(XML_BASIC);

		xmlBasicImplEClass = createEClass(XML_BASIC_IMPL);

		xmlVersionEClass = createEClass(XML_VERSION);

		xmlVersionImplEClass = createEClass(XML_VERSION_IMPL);

		xmlPrivateOwnedEClass = createEClass(XML_PRIVATE_OWNED);
		createEAttribute(xmlPrivateOwnedEClass, XML_PRIVATE_OWNED__PRIVATE_OWNED);

		xmlJoinFetchEClass = createEClass(XML_JOIN_FETCH);
		createEAttribute(xmlJoinFetchEClass, XML_JOIN_FETCH__JOIN_FETCH);

		xmlOneToOneEClass = createEClass(XML_ONE_TO_ONE);

		xmlOneToOneImplEClass = createEClass(XML_ONE_TO_ONE_IMPL);

		xmlOneToManyEClass = createEClass(XML_ONE_TO_MANY);

		xmlOneToManyImplEClass = createEClass(XML_ONE_TO_MANY_IMPL);

		xmlManyToOneEClass = createEClass(XML_MANY_TO_ONE);

		xmlManyToOneImplEClass = createEClass(XML_MANY_TO_ONE_IMPL);

		xmlManyToManyEClass = createEClass(XML_MANY_TO_MANY);

		xmlManyToManyImplEClass = createEClass(XML_MANY_TO_MANY_IMPL);

		// Create enums
		xmlChangeTrackingTypeEEnum = createEEnum(XML_CHANGE_TRACKING_TYPE);
		cacheTypeEEnum = createEEnum(CACHE_TYPE);
		cacheCoordinationTypeEEnum = createEEnum(CACHE_COORDINATION_TYPE);
		existenceTypeEEnum = createEEnum(EXISTENCE_TYPE);
		xmlJoinFetchTypeEEnum = createEEnum(XML_JOIN_FETCH_TYPE);
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
		xmlConverterEClass.getESuperTypes().add(this.getXmlNamedConverter());
		xmlConverterImplEClass.getESuperTypes().add(this.getXmlConverter());
		xmlTypeConverterEClass.getESuperTypes().add(this.getXmlNamedConverter());
		xmlTypeConverterImplEClass.getESuperTypes().add(this.getXmlTypeConverter());
		xmlConversionValueImplEClass.getESuperTypes().add(this.getXmlConversionValue());
		xmlObjectTypeConverterEClass.getESuperTypes().add(this.getXmlNamedConverter());
		xmlObjectTypeConverterImplEClass.getESuperTypes().add(this.getXmlObjectTypeConverter());
		xmlStructConverterEClass.getESuperTypes().add(this.getXmlNamedConverter());
		xmlStructConverterImplEClass.getESuperTypes().add(this.getXmlStructConverter());
		xmlEntityMappingsEClass.getESuperTypes().add(theOrmPackage.getXmlEntityMappings());
		xmlEntityMappingsEClass.getESuperTypes().add(this.getXmlConvertersHolder());
		xmlEmbeddableEClass.getESuperTypes().add(theOrmPackage.getXmlEmbeddable());
		xmlEmbeddableEClass.getESuperTypes().add(this.getXmlCustomizerHolder());
		xmlEmbeddableEClass.getESuperTypes().add(this.getXmlChangeTrackingHolder());
		xmlEmbeddableEClass.getESuperTypes().add(this.getXmlConvertersHolder());
		xmlEntityEClass.getESuperTypes().add(theOrmPackage.getXmlEntity());
		xmlEntityEClass.getESuperTypes().add(this.getXmlReadOnly());
		xmlEntityEClass.getESuperTypes().add(this.getXmlCustomizerHolder());
		xmlEntityEClass.getESuperTypes().add(this.getXmlChangeTrackingHolder());
		xmlEntityEClass.getESuperTypes().add(this.getXmlCacheHolder());
		xmlEntityEClass.getESuperTypes().add(this.getXmlConvertersHolder());
		xmlMappedSuperclassEClass.getESuperTypes().add(theOrmPackage.getXmlMappedSuperclass());
		xmlMappedSuperclassEClass.getESuperTypes().add(this.getXmlReadOnly());
		xmlMappedSuperclassEClass.getESuperTypes().add(this.getXmlCustomizerHolder());
		xmlMappedSuperclassEClass.getESuperTypes().add(this.getXmlChangeTrackingHolder());
		xmlMappedSuperclassEClass.getESuperTypes().add(this.getXmlCacheHolder());
		xmlMappedSuperclassEClass.getESuperTypes().add(this.getXmlConvertersHolder());
		xmlConvertibleMappingEClass.getESuperTypes().add(theOrmPackage.getXmlConvertibleMapping());
		xmlConvertibleMappingEClass.getESuperTypes().add(this.getXmlConverterHolder());
		xmlIdEClass.getESuperTypes().add(theOrmPackage.getXmlId());
		xmlIdEClass.getESuperTypes().add(this.getXmlMutable());
		xmlIdEClass.getESuperTypes().add(this.getXmlConvertibleMapping());
		xmlIdImplEClass.getESuperTypes().add(theOrmPackage.getXmlIdImpl());
		xmlIdImplEClass.getESuperTypes().add(this.getXmlId());
		xmlBasicEClass.getESuperTypes().add(theOrmPackage.getXmlBasic());
		xmlBasicEClass.getESuperTypes().add(this.getXmlMutable());
		xmlBasicEClass.getESuperTypes().add(this.getXmlConvertibleMapping());
		xmlBasicImplEClass.getESuperTypes().add(theOrmPackage.getXmlBasicImpl());
		xmlBasicImplEClass.getESuperTypes().add(this.getXmlBasic());
		xmlVersionEClass.getESuperTypes().add(theOrmPackage.getXmlVersion());
		xmlVersionEClass.getESuperTypes().add(this.getXmlMutable());
		xmlVersionEClass.getESuperTypes().add(this.getXmlConvertibleMapping());
		xmlVersionImplEClass.getESuperTypes().add(theOrmPackage.getXmlVersionImpl());
		xmlVersionImplEClass.getESuperTypes().add(this.getXmlVersion());
		xmlOneToOneEClass.getESuperTypes().add(theOrmPackage.getXmlOneToOne());
		xmlOneToOneEClass.getESuperTypes().add(this.getXmlPrivateOwned());
		xmlOneToOneEClass.getESuperTypes().add(this.getXmlJoinFetch());
		xmlOneToOneImplEClass.getESuperTypes().add(theOrmPackage.getXmlOneToOneImpl());
		xmlOneToOneImplEClass.getESuperTypes().add(this.getXmlOneToOne());
		xmlOneToManyEClass.getESuperTypes().add(theOrmPackage.getXmlOneToMany());
		xmlOneToManyEClass.getESuperTypes().add(this.getXmlPrivateOwned());
		xmlOneToManyEClass.getESuperTypes().add(this.getXmlJoinFetch());
		xmlOneToManyImplEClass.getESuperTypes().add(theOrmPackage.getXmlOneToManyImpl());
		xmlOneToManyImplEClass.getESuperTypes().add(this.getXmlOneToMany());
		xmlManyToOneEClass.getESuperTypes().add(theOrmPackage.getXmlManyToOne());
		xmlManyToOneEClass.getESuperTypes().add(this.getXmlJoinFetch());
		xmlManyToOneImplEClass.getESuperTypes().add(theOrmPackage.getXmlManyToOneImpl());
		xmlManyToOneImplEClass.getESuperTypes().add(this.getXmlManyToOne());
		xmlManyToManyEClass.getESuperTypes().add(theOrmPackage.getXmlManyToMany());
		xmlManyToManyEClass.getESuperTypes().add(this.getXmlJoinFetch());
		xmlManyToManyImplEClass.getESuperTypes().add(theOrmPackage.getXmlManyToManyImpl());
		xmlManyToManyImplEClass.getESuperTypes().add(this.getXmlManyToMany());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlNamedConverterEClass, XmlNamedConverter.class, "XmlNamedConverter", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlNamedConverter_Name(), ecorePackage.getEString(), "name", null, 0, 1, XmlNamedConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConverterEClass, XmlConverter.class, "XmlConverter", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlConverter_ClassName(), ecorePackage.getEString(), "className", null, 0, 1, XmlConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConverterImplEClass, XmlConverterImpl.class, "XmlConverterImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlTypeConverterEClass, XmlTypeConverter.class, "XmlTypeConverter", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlTypeConverter_DataType(), ecorePackage.getEString(), "dataType", null, 0, 1, XmlTypeConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTypeConverter_ObjectType(), ecorePackage.getEString(), "objectType", null, 0, 1, XmlTypeConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTypeConverterImplEClass, XmlTypeConverterImpl.class, "XmlTypeConverterImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlConversionValueEClass, XmlConversionValue.class, "XmlConversionValue", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlConversionValue_DataValue(), ecorePackage.getEString(), "dataValue", null, 0, 1, XmlConversionValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlConversionValue_ObjectValue(), ecorePackage.getEString(), "objectValue", null, 0, 1, XmlConversionValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConversionValueImplEClass, XmlConversionValueImpl.class, "XmlConversionValueImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlObjectTypeConverterEClass, XmlObjectTypeConverter.class, "XmlObjectTypeConverter", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlObjectTypeConverter_DataType(), ecorePackage.getEString(), "dataType", null, 0, 1, XmlObjectTypeConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlObjectTypeConverter_ObjectType(), ecorePackage.getEString(), "objectType", null, 0, 1, XmlObjectTypeConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlObjectTypeConverter_ConversionValues(), this.getXmlConversionValue(), null, "conversionValues", null, 0, -1, XmlObjectTypeConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlObjectTypeConverter_DefaultObjectValue(), ecorePackage.getEString(), "defaultObjectValue", null, 0, 1, XmlObjectTypeConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlObjectTypeConverterImplEClass, XmlObjectTypeConverterImpl.class, "XmlObjectTypeConverterImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlStructConverterEClass, XmlStructConverter.class, "XmlStructConverter", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlStructConverter_Converter(), ecorePackage.getEString(), "converter", null, 0, 1, XmlStructConverter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlStructConverterImplEClass, XmlStructConverterImpl.class, "XmlStructConverterImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlConvertersHolderEClass, XmlConvertersHolder.class, "XmlConvertersHolder", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlConvertersHolder_Converters(), this.getXmlConverter(), null, "converters", null, 0, -1, XmlConvertersHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlConvertersHolder_TypeConverters(), this.getXmlTypeConverter(), null, "typeConverters", null, 0, -1, XmlConvertersHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlConvertersHolder_ObjectTypeConverters(), this.getXmlObjectTypeConverter(), null, "objectTypeConverters", null, 0, -1, XmlConvertersHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlConvertersHolder_StructConverters(), this.getXmlStructConverter(), null, "structConverters", null, 0, -1, XmlConvertersHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntityMappingsEClass, XmlEntityMappings.class, "XmlEntityMappings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlCustomizerEClass, XmlCustomizer.class, "XmlCustomizer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlCustomizer_CustomizerClassName(), ecorePackage.getEString(), "customizerClassName", null, 0, 1, XmlCustomizer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCustomizerHolderEClass, XmlCustomizerHolder.class, "XmlCustomizerHolder", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlCustomizerHolder_Customizer(), this.getXmlCustomizer(), null, "customizer", null, 0, 1, XmlCustomizerHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlChangeTrackingEClass, XmlChangeTracking.class, "XmlChangeTracking", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlChangeTracking_Type(), this.getXmlChangeTrackingType(), "type", null, 0, 1, XmlChangeTracking.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlChangeTrackingHolderEClass, XmlChangeTrackingHolder.class, "XmlChangeTrackingHolder", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlChangeTrackingHolder_ChangeTracking(), this.getXmlChangeTracking(), null, "changeTracking", null, 0, 1, XmlChangeTrackingHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEmbeddableEClass, XmlEmbeddable.class, "XmlEmbeddable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlReadOnlyEClass, XmlReadOnly.class, "XmlReadOnly", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlReadOnly_ReadOnly(), theXMLTypePackage.getBooleanObject(), "readOnly", null, 0, 1, XmlReadOnly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTimeOfDayEClass, XmlTimeOfDay.class, "XmlTimeOfDay", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlTimeOfDay_Hour(), theXMLTypePackage.getIntObject(), "hour", null, 0, 1, XmlTimeOfDay.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTimeOfDay_Minute(), theXMLTypePackage.getIntObject(), "minute", null, 0, 1, XmlTimeOfDay.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTimeOfDay_Second(), theXMLTypePackage.getIntObject(), "second", null, 0, 1, XmlTimeOfDay.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTimeOfDay_Millisecond(), theXMLTypePackage.getIntObject(), "millisecond", null, 0, 1, XmlTimeOfDay.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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

		initEClass(xmlEntityEClass, XmlEntity.class, "XmlEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlMappedSuperclassEClass, XmlMappedSuperclass.class, "XmlMappedSuperclass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlMutableEClass, XmlMutable.class, "XmlMutable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlMutable_Mutable(), theXMLTypePackage.getBooleanObject(), "mutable", null, 0, 1, XmlMutable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConverterHolderEClass, XmlConverterHolder.class, "XmlConverterHolder", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlConverterHolder_Converter(), this.getXmlConverter(), null, "converter", null, 0, 1, XmlConverterHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlConverterHolder_TypeConverter(), this.getXmlTypeConverter(), null, "typeConverter", null, 0, 1, XmlConverterHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlConverterHolder_ObjectTypeConverter(), this.getXmlObjectTypeConverter(), null, "objectTypeConverter", null, 0, 1, XmlConverterHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlConverterHolder_StructConverter(), this.getXmlStructConverter(), null, "structConverter", null, 0, 1, XmlConverterHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConvertibleMappingEClass, XmlConvertibleMapping.class, "XmlConvertibleMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlConvertibleMapping_Convert(), ecorePackage.getEString(), "convert", null, 0, 1, XmlConvertibleMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlIdEClass, XmlId.class, "XmlId", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlIdImplEClass, XmlIdImpl.class, "XmlIdImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlBasicEClass, XmlBasic.class, "XmlBasic", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlBasicImplEClass, XmlBasicImpl.class, "XmlBasicImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlVersionEClass, XmlVersion.class, "XmlVersion", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlVersionImplEClass, XmlVersionImpl.class, "XmlVersionImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPrivateOwnedEClass, XmlPrivateOwned.class, "XmlPrivateOwned", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPrivateOwned_PrivateOwned(), theXMLTypePackage.getBoolean(), "privateOwned", null, 0, 1, XmlPrivateOwned.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlJoinFetchEClass, XmlJoinFetch.class, "XmlJoinFetch", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlJoinFetch_JoinFetch(), this.getXmlJoinFetchType(), "joinFetch", null, 0, 1, XmlJoinFetch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToOneEClass, XmlOneToOne.class, "XmlOneToOne", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlOneToOneImplEClass, XmlOneToOneImpl.class, "XmlOneToOneImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlOneToManyEClass, XmlOneToMany.class, "XmlOneToMany", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlOneToManyImplEClass, XmlOneToManyImpl.class, "XmlOneToManyImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlManyToOneEClass, XmlManyToOne.class, "XmlManyToOne", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlManyToOneImplEClass, XmlManyToOneImpl.class, "XmlManyToOneImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlManyToManyEClass, XmlManyToMany.class, "XmlManyToMany", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlManyToManyImplEClass, XmlManyToManyImpl.class, "XmlManyToManyImpl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(xmlChangeTrackingTypeEEnum, XmlChangeTrackingType.class, "XmlChangeTrackingType");
		addEEnumLiteral(xmlChangeTrackingTypeEEnum, XmlChangeTrackingType.ATTRIBUTE);
		addEEnumLiteral(xmlChangeTrackingTypeEEnum, XmlChangeTrackingType.OBJECT);
		addEEnumLiteral(xmlChangeTrackingTypeEEnum, XmlChangeTrackingType.DEFERRED);
		addEEnumLiteral(xmlChangeTrackingTypeEEnum, XmlChangeTrackingType.AUTO);

		initEEnum(cacheTypeEEnum, CacheType.class, "CacheType");
		addEEnumLiteral(cacheTypeEEnum, CacheType.FULL);
		addEEnumLiteral(cacheTypeEEnum, CacheType.WEAK);
		addEEnumLiteral(cacheTypeEEnum, CacheType.SOFT);
		addEEnumLiteral(cacheTypeEEnum, CacheType.SOFT_WEAK);
		addEEnumLiteral(cacheTypeEEnum, CacheType.HARD_WEAK);
		addEEnumLiteral(cacheTypeEEnum, CacheType.CACHE);
		addEEnumLiteral(cacheTypeEEnum, CacheType.NONE);

		initEEnum(cacheCoordinationTypeEEnum, CacheCoordinationType.class, "CacheCoordinationType");
		addEEnumLiteral(cacheCoordinationTypeEEnum, CacheCoordinationType.SEND_OBJECT_CHANGES);
		addEEnumLiteral(cacheCoordinationTypeEEnum, CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		addEEnumLiteral(cacheCoordinationTypeEEnum, CacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES);
		addEEnumLiteral(cacheCoordinationTypeEEnum, CacheCoordinationType.NONE);

		initEEnum(existenceTypeEEnum, ExistenceType.class, "ExistenceType");
		addEEnumLiteral(existenceTypeEEnum, ExistenceType.CHECK_CACHE);
		addEEnumLiteral(existenceTypeEEnum, ExistenceType.CHECK_DATABASE);
		addEEnumLiteral(existenceTypeEEnum, ExistenceType.ASSUME_EXISTENCE);
		addEEnumLiteral(existenceTypeEEnum, ExistenceType.ASSUME_NON_EXISTENCE);

		initEEnum(xmlJoinFetchTypeEEnum, XmlJoinFetchType.class, "XmlJoinFetchType");
		addEEnumLiteral(xmlJoinFetchTypeEEnum, XmlJoinFetchType.INNER);
		addEEnumLiteral(xmlJoinFetchTypeEEnum, XmlJoinFetchType.OUTER);

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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter <em>Xml Named Converter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedConverter()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizer <em>Xml Customizer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizer
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCustomizer()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder <em>Xml Customizer Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCustomizerHolder()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTracking <em>Xml Change Tracking</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTracking
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTracking()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder <em>Xml Change Tracking Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTrackingHolder()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder <em>Xml Converters Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertersHolder()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntityMappings()
		 * @generated
		 */
		public static final EClass XML_ENTITY_MAPPINGS = eINSTANCE.getXmlEntityMappings();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddable <em>Xml Embeddable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddable
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddable()
		 * @generated
		 */
		public static final EClass XML_EMBEDDABLE = eINSTANCE.getXmlEmbeddable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly <em>Xml Read Only</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlReadOnly()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay <em>Xml Time Of Day</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTimeOfDay()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache <em>Xml Cache</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCache()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder <em>Xml Cache Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCacheHolder()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity <em>Xml Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEntity()
		 * @generated
		 */
		public static final EClass XML_ENTITY = eINSTANCE.getXmlEntity();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlMappedSuperclass
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMappedSuperclass()
		 * @generated
		 */
		public static final EClass XML_MAPPED_SUPERCLASS = eINSTANCE.getXmlMappedSuperclass();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable <em>Xml Mutable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMutable()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping <em>Xml Convertible Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertibleMapping()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlId <em>Xml Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlId
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlId()
		 * @generated
		 */
		public static final EClass XML_ID = eINSTANCE.getXmlId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlIdImpl <em>Xml Id Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlIdImpl
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlIdImpl()
		 * @generated
		 */
		public static final EClass XML_ID_IMPL = eINSTANCE.getXmlIdImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic <em>Xml Basic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBasic()
		 * @generated
		 */
		public static final EClass XML_BASIC = eINSTANCE.getXmlBasic();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicImpl <em>Xml Basic Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicImpl
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBasicImpl()
		 * @generated
		 */
		public static final EClass XML_BASIC_IMPL = eINSTANCE.getXmlBasicImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion <em>Xml Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlVersion()
		 * @generated
		 */
		public static final EClass XML_VERSION = eINSTANCE.getXmlVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersionImpl <em>Xml Version Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersionImpl
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlVersionImpl()
		 * @generated
		 */
		public static final EClass XML_VERSION_IMPL = eINSTANCE.getXmlVersionImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned <em>Xml Private Owned</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrivateOwned()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch <em>Xml Join Fetch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetch()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOneToOne()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_ONE = eINSTANCE.getXmlOneToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOneImpl <em>Xml One To One Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOneImpl
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOneToOneImpl()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_ONE_IMPL = eINSTANCE.getXmlOneToOneImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOneToMany()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_MANY = eINSTANCE.getXmlOneToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToManyImpl <em>Xml One To Many Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToManyImpl
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOneToManyImpl()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_MANY_IMPL = eINSTANCE.getXmlOneToManyImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToOne()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_ONE = eINSTANCE.getXmlManyToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOneImpl <em>Xml Many To One Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOneImpl
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToOneImpl()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_ONE_IMPL = eINSTANCE.getXmlManyToOneImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToMany()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_MANY = eINSTANCE.getXmlManyToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToManyImpl <em>Xml Many To Many Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToManyImpl
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlManyToManyImpl()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_MANY_IMPL = eINSTANCE.getXmlManyToManyImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingType <em>Xml Change Tracking Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingType
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTrackingType()
		 * @generated
		 */
		public static final EEnum XML_CHANGE_TRACKING_TYPE = eINSTANCE.getXmlChangeTrackingType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter <em>Xml Converter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverter()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterImpl <em>Xml Converter Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterImpl
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverterImpl()
		 * @generated
		 */
		public static final EClass XML_CONVERTER_IMPL = eINSTANCE.getXmlConverterImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter <em>Xml Type Converter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTypeConverter()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverterImpl <em>Xml Type Converter Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverterImpl
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTypeConverterImpl()
		 * @generated
		 */
		public static final EClass XML_TYPE_CONVERTER_IMPL = eINSTANCE.getXmlTypeConverterImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue <em>Xml Conversion Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConversionValue()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValueImpl <em>Xml Conversion Value Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValueImpl
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConversionValueImpl()
		 * @generated
		 */
		public static final EClass XML_CONVERSION_VALUE_IMPL = eINSTANCE.getXmlConversionValueImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter <em>Xml Object Type Converter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlObjectTypeConverter()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverterImpl <em>Xml Object Type Converter Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverterImpl
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlObjectTypeConverterImpl()
		 * @generated
		 */
		public static final EClass XML_OBJECT_TYPE_CONVERTER_IMPL = eINSTANCE.getXmlObjectTypeConverterImpl();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter <em>Xml Struct Converter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStructConverter()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverterImpl <em>Xml Struct Converter Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverterImpl
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStructConverterImpl()
		 * @generated
		 */
		public static final EClass XML_STRUCT_CONVERTER_IMPL = eINSTANCE.getXmlStructConverterImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder <em>Xml Converter Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConverterHolder()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.CacheType <em>Cache Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.CacheType
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getCacheType()
		 * @generated
		 */
		public static final EEnum CACHE_TYPE = eINSTANCE.getCacheType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.CacheCoordinationType <em>Cache Coordination Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.CacheCoordinationType
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getCacheCoordinationType()
		 * @generated
		 */
		public static final EEnum CACHE_COORDINATION_TYPE = eINSTANCE.getCacheCoordinationType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType <em>Xml Join Fetch Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetchType()
		 * @generated
		 */
		public static final EEnum XML_JOIN_FETCH_TYPE = eINSTANCE.getXmlJoinFetchType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.ExistenceType <em>Existence Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.ExistenceType
		 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getExistenceType()
		 * @generated
		 */
		public static final EEnum EXISTENCE_TYPE = eINSTANCE.getExistenceType();

	}

} //EclipseLinkOrmPackage
