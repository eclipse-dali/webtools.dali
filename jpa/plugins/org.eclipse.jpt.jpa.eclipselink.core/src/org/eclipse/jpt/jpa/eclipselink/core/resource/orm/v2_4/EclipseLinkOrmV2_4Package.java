/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
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

import org.eclipse.jpt.jpa.core.resource.xml.CommonPackage;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage;

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
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4 <em>Xml Multitenant 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlMultitenant_2_4()
	 * @generated
	 */
	public static final int XML_MULTITENANT_24 = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4 <em>Xml Element Collection 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlElementCollection_2_4()
	 * @generated
	 */
	public static final int XML_ELEMENT_COLLECTION_24 = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4 <em>Xml One To Many 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlOneToMany_2_4()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY_24 = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4 <em>Xml Entity 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlEntity_2_4()
	 * @generated
	 */
	public static final int XML_ENTITY_24 = 4;

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
	 * The feature id for the '<em><b>Cache Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_24__CACHE_INDEX = 0;

	/**
	 * The number of structural features of the '<em>Xml Basic 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_24_FEATURE_COUNT = 1;

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
	 * The feature id for the '<em><b>Delete All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_24__DELETE_ALL = 0;

	/**
	 * The number of structural features of the '<em>Xml Element Collection 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_24_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Cache Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_24__CACHE_INDEX = 0;

	/**
	 * The number of structural features of the '<em>Xml Entity 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_24_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4 <em>Xml Id 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlId_2_4()
	 * @generated
	 */
	public static final int XML_ID_24 = 5;

	/**
	 * The feature id for the '<em><b>Cache Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_24__CACHE_INDEX = 0;

	/**
	 * The number of structural features of the '<em>Xml Id 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_24_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMappedSuperclass_2_4 <em>Xml Mapped Superclass 24</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMappedSuperclass_2_4
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlMappedSuperclass_2_4()
	 * @generated
	 */
	public static final int XML_MAPPED_SUPERCLASS_24 = 6;

	/**
	 * The feature id for the '<em><b>Cache Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_24__CACHE_INDEX = 0;

	/**
	 * The number of structural features of the '<em>Xml Mapped Superclass 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_24_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Include Criteria</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTITENANT_24__INCLUDE_CRITERIA = 0;

	/**
	 * The number of structural features of the '<em>Xml Multitenant 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MULTITENANT_24_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Delete All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_24__DELETE_ALL = 0;

	/**
	 * The number of structural features of the '<em>Xml One To Many 24</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_24_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DatabaseChangeNotificationType <em>Database Change Notification Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.DatabaseChangeNotificationType
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getDatabaseChangeNotificationType()
	 * @generated
	 */
	public static final int DATABASE_CHANGE_NOTIFICATION_TYPE = 9;

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
	private EClass xmlElementCollection_2_4EClass = null;

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
	private EClass xmlEntity_2_4EClass = null;

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
	private EClass xmlId_2_4EClass = null;

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
		CommonPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		EclipseLinkOrmPackage theEclipseLinkOrmPackage = (EclipseLinkOrmPackage)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) instanceof EclipseLinkOrmPackage ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) : EclipseLinkOrmPackage.eINSTANCE);
		EclipseLinkOrmV1_1Package theEclipseLinkOrmV1_1Package = (EclipseLinkOrmV1_1Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI) instanceof EclipseLinkOrmV1_1Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI) : EclipseLinkOrmV1_1Package.eINSTANCE);
		EclipseLinkOrmV2_0Package theEclipseLinkOrmV2_0Package = (EclipseLinkOrmV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) instanceof EclipseLinkOrmV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) : EclipseLinkOrmV2_0Package.eINSTANCE);
		EclipseLinkOrmV2_1Package theEclipseLinkOrmV2_1Package = (EclipseLinkOrmV2_1Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI) instanceof EclipseLinkOrmV2_1Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI) : EclipseLinkOrmV2_1Package.eINSTANCE);
		EclipseLinkOrmV2_2Package theEclipseLinkOrmV2_2Package = (EclipseLinkOrmV2_2Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) instanceof EclipseLinkOrmV2_2Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) : EclipseLinkOrmV2_2Package.eINSTANCE);
		EclipseLinkOrmV2_3Package theEclipseLinkOrmV2_3Package = (EclipseLinkOrmV2_3Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_3Package.eNS_URI) instanceof EclipseLinkOrmV2_3Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_3Package.eNS_URI) : EclipseLinkOrmV2_3Package.eINSTANCE);

		// Create package meta-data objects
		theEclipseLinkOrmV2_4Package.createPackageContents();
		theEclipseLinkOrmPackage.createPackageContents();
		theEclipseLinkOrmV1_1Package.createPackageContents();
		theEclipseLinkOrmV2_0Package.createPackageContents();
		theEclipseLinkOrmV2_1Package.createPackageContents();
		theEclipseLinkOrmV2_2Package.createPackageContents();
		theEclipseLinkOrmV2_3Package.createPackageContents();

		// Initialize created meta-data
		theEclipseLinkOrmV2_4Package.initializePackageContents();
		theEclipseLinkOrmPackage.initializePackageContents();
		theEclipseLinkOrmV1_1Package.initializePackageContents();
		theEclipseLinkOrmV2_0Package.initializePackageContents();
		theEclipseLinkOrmV2_1Package.initializePackageContents();
		theEclipseLinkOrmV2_2Package.initializePackageContents();
		theEclipseLinkOrmV2_3Package.initializePackageContents();

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
		return (EAttribute)xmlElementCollection_2_4EClass.getEStructuralFeatures().get(0);
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
		return (EAttribute)xmlOneToMany_2_4EClass.getEStructuralFeatures().get(0);
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
		return (EReference)xmlBasic_2_4EClass.getEStructuralFeatures().get(0);
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
		return (EReference)xmlId_2_4EClass.getEStructuralFeatures().get(0);
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
		createEReference(xmlBasic_2_4EClass, XML_BASIC_24__CACHE_INDEX);

		xmlCache_2_4EClass = createEClass(XML_CACHE_24);
		createEAttribute(xmlCache_2_4EClass, XML_CACHE_24__DATABASE_CHANGE_NOTIFICATION_TYPE);

		xmlCacheIndex_2_4EClass = createEClass(XML_CACHE_INDEX_24);
		createEAttribute(xmlCacheIndex_2_4EClass, XML_CACHE_INDEX_24__COLUMN_NAMES);

		xmlElementCollection_2_4EClass = createEClass(XML_ELEMENT_COLLECTION_24);
		createEAttribute(xmlElementCollection_2_4EClass, XML_ELEMENT_COLLECTION_24__DELETE_ALL);

		xmlEntity_2_4EClass = createEClass(XML_ENTITY_24);
		createEReference(xmlEntity_2_4EClass, XML_ENTITY_24__CACHE_INDEX);

		xmlId_2_4EClass = createEClass(XML_ID_24);
		createEReference(xmlId_2_4EClass, XML_ID_24__CACHE_INDEX);

		xmlMappedSuperclass_2_4EClass = createEClass(XML_MAPPED_SUPERCLASS_24);
		createEReference(xmlMappedSuperclass_2_4EClass, XML_MAPPED_SUPERCLASS_24__CACHE_INDEX);

		xmlMultitenant_2_4EClass = createEClass(XML_MULTITENANT_24);
		createEAttribute(xmlMultitenant_2_4EClass, XML_MULTITENANT_24__INCLUDE_CRITERIA);

		xmlOneToMany_2_4EClass = createEClass(XML_ONE_TO_MANY_24);
		createEAttribute(xmlOneToMany_2_4EClass, XML_ONE_TO_MANY_24__DELETE_ALL);

		// Create enums
		databaseChangeNotificationTypeEEnum = createEEnum(DATABASE_CHANGE_NOTIFICATION_TYPE);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(xmlBasic_2_4EClass, XmlBasic_2_4.class, "XmlBasic_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlBasic_2_4_CacheIndex(), this.getXmlCacheIndex_2_4(), null, "cacheIndex", null, 0, 1, XmlBasic_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCache_2_4EClass, XmlCache_2_4.class, "XmlCache_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlCache_2_4_DatabaseChangeNotificationType(), this.getDatabaseChangeNotificationType(), "databaseChangeNotificationType", "", 0, 1, XmlCache_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCacheIndex_2_4EClass, XmlCacheIndex_2_4.class, "XmlCacheIndex_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlCacheIndex_2_4_ColumnNames(), theXMLTypePackage.getString(), "columnNames", null, 0, -1, XmlCacheIndex_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlElementCollection_2_4EClass, XmlElementCollection_2_4.class, "XmlElementCollection_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlElementCollection_2_4_DeleteAll(), theXMLTypePackage.getBoolean(), "deleteAll", null, 0, 1, XmlElementCollection_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntity_2_4EClass, XmlEntity_2_4.class, "XmlEntity_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntity_2_4_CacheIndex(), this.getXmlCacheIndex_2_4(), null, "cacheIndex", null, 0, 1, XmlEntity_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlId_2_4EClass, XmlId_2_4.class, "XmlId_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlId_2_4_CacheIndex(), this.getXmlCacheIndex_2_4(), null, "cacheIndex", null, 0, 1, XmlId_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMappedSuperclass_2_4EClass, XmlMappedSuperclass_2_4.class, "XmlMappedSuperclass_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlMappedSuperclass_2_4_CacheIndex(), this.getXmlCacheIndex_2_4(), null, "cacheIndex", null, 0, 1, XmlMappedSuperclass_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMultitenant_2_4EClass, XmlMultitenant_2_4.class, "XmlMultitenant_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlMultitenant_2_4_IncludeCriteria(), theXMLTypePackage.getBooleanObject(), "includeCriteria", null, 0, 1, XmlMultitenant_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOneToMany_2_4EClass, XmlOneToMany_2_4.class, "XmlOneToMany_2_4", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlOneToMany_2_4_DeleteAll(), theXMLTypePackage.getBoolean(), "deleteAll", null, 0, 1, XmlOneToMany_2_4.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(databaseChangeNotificationTypeEEnum, DatabaseChangeNotificationType.class, "DatabaseChangeNotificationType");
		addEEnumLiteral(databaseChangeNotificationTypeEEnum, DatabaseChangeNotificationType.NONE);
		addEEnumLiteral(databaseChangeNotificationTypeEEnum, DatabaseChangeNotificationType.INVALIDATION);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4 <em>Xml Element Collection 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlElementCollection_2_4()
		 * @generated
		 */
		public static final EClass XML_ELEMENT_COLLECTION_24 = eINSTANCE.getXmlElementCollection_2_4();

		/**
		 * The meta object literal for the '<em><b>Delete All</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ELEMENT_COLLECTION_24__DELETE_ALL = eINSTANCE.getXmlElementCollection_2_4_DeleteAll();

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
		 * The meta object literal for the '<em><b>Delete All</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ONE_TO_MANY_24__DELETE_ALL = eINSTANCE.getXmlOneToMany_2_4_DeleteAll();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlBasic_2_4 <em>Xml Basic 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlBasic_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlBasic_2_4()
		 * @generated
		 */
		public static final EClass XML_BASIC_24 = eINSTANCE.getXmlBasic_2_4();

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
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4 <em>Xml Id 24</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlId_2_4
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlId_2_4()
		 * @generated
		 */
		public static final EClass XML_ID_24 = eINSTANCE.getXmlId_2_4();

		/**
		 * The meta object literal for the '<em><b>Cache Index</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ID_24__CACHE_INDEX = eINSTANCE.getXmlId_2_4_CacheIndex();

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

	}

} //EclipseLinkOrmV2_4Package
