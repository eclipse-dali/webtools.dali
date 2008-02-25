/*******************************************************************************
 *  Copyright (c) 2006, 2007  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.resource.persistence;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.jpt.core.resource.orm.OrmPackage;

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
 * @see org.eclipse.jpt.core.resource.persistence.PersistenceFactory
 * @model kind="package"
 * @generated
 */
public class PersistencePackage extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "persistence";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.persistence.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.core.resource.persistence";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final PersistencePackage eINSTANCE = org.eclipse.jpt.core.resource.persistence.PersistencePackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistence <em>Xml Persistence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistence
	 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlPersistence()
	 * @generated
	 */
	public static final int XML_PERSISTENCE = 0;

	/**
	 * The feature id for the '<em><b>Persistence Units</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE__PERSISTENCE_UNITS = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE__VERSION = 1;

	/**
	 * The number of structural features of the '<em>Xml Persistence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit <em>Xml Persistence Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit
	 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__PROVIDER = 1;

	/**
	 * The feature id for the '<em><b>Jta Data Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__JTA_DATA_SOURCE = 2;

	/**
	 * The feature id for the '<em><b>Non Jta Data Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE = 3;

	/**
	 * The feature id for the '<em><b>Mapping Files</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__MAPPING_FILES = 4;

	/**
	 * The feature id for the '<em><b>Jar Files</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__JAR_FILES = 5;

	/**
	 * The feature id for the '<em><b>Classes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__CLASSES = 6;

	/**
	 * The feature id for the '<em><b>Exclude Unlisted Classes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES = 7;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__PROPERTIES = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__NAME = 9;

	/**
	 * The feature id for the '<em><b>Transaction Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__TRANSACTION_TYPE = 10;

	/**
	 * The number of structural features of the '<em>Xml Persistence Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_FEATURE_COUNT = 11;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef <em>Xml Mapping File Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef
	 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlMappingFileRef()
	 * @generated
	 */
	public static final int XML_MAPPING_FILE_REF = 2;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPING_FILE_REF__FILE_NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Mapping File Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPING_FILE_REF_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef <em>Xml Java Class Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef
	 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlJavaClassRef()
	 * @generated
	 */
	public static final int XML_JAVA_CLASS_REF = 3;

	/**
	 * The feature id for the '<em><b>Java Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JAVA_CLASS_REF__JAVA_CLASS = 0;

	/**
	 * The number of structural features of the '<em>Xml Java Class Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JAVA_CLASS_REF_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.persistence.XmlProperties <em>Xml Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.persistence.XmlProperties
	 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlProperties()
	 * @generated
	 */
	public static final int XML_PROPERTIES = 4;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PROPERTIES__PROPERTIES = 0;

	/**
	 * The number of structural features of the '<em>Xml Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PROPERTIES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.persistence.XmlProperty <em>Xml Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.persistence.XmlProperty
	 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlProperty()
	 * @generated
	 */
	public static final int XML_PROPERTY = 5;

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
	 * The number of structural features of the '<em>Xml Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PROPERTY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnitTransactionType <em>Xml Persistence Unit Transaction Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnitTransactionType
	 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlPersistenceUnitTransactionType()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_TRANSACTION_TYPE = 6;

	/**
	 * The meta object id for the '<em>Xml Persistence Unit Transaction Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlPersistenceUnitTransactionTypeObject()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_TRANSACTION_TYPE_OBJECT = 7;

	/**
	 * The meta object id for the '<em>Xml Version</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlVersion()
	 * @generated
	 */
	public static final int XML_VERSION = 8;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPersistenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPersistenceUnitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMappingFileRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlJavaClassRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPropertiesEClass = null;

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
	private EEnum xmlPersistenceUnitTransactionTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType xmlPersistenceUnitTransactionTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType xmlVersionEDataType = null;

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
	 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PersistencePackage()
	{
		super(eNS_URI, PersistenceFactory.eINSTANCE);
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
	public static PersistencePackage init()
	{
		if (isInited) return (PersistencePackage)EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI);

		// Obtain or create and register package
		PersistencePackage thePersistencePackage = (PersistencePackage)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new PersistencePackage());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		OrmPackage theOrmPackage = (OrmPackage)(EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) : OrmPackage.eINSTANCE);

		// Create package meta-data objects
		thePersistencePackage.createPackageContents();
		theOrmPackage.createPackageContents();

		// Initialize created meta-data
		thePersistencePackage.initializePackageContents();
		theOrmPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePersistencePackage.freeze();

		return thePersistencePackage;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistence <em>Xml Persistence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistence
	 * @generated
	 */
	public EClass getXmlPersistence()
	{
		return xmlPersistenceEClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistence#getPersistenceUnits <em>Persistence Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Persistence Units</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistence#getPersistenceUnits()
	 * @see #getXmlPersistence()
	 * @generated
	 */
	public EReference getXmlPersistence_PersistenceUnits()
	{
		return (EReference)xmlPersistenceEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistence#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistence#getVersion()
	 * @see #getXmlPersistence()
	 * @generated
	 */
	public EAttribute getXmlPersistence_Version()
	{
		return (EAttribute)xmlPersistenceEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit <em>Xml Persistence Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence Unit</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit
	 * @generated
	 */
	public EClass getXmlPersistenceUnit()
	{
		return xmlPersistenceUnitEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getDescription()
	 * @see #getXmlPersistenceUnit()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnit_Description()
	{
		return (EAttribute)xmlPersistenceUnitEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Provider</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getProvider()
	 * @see #getXmlPersistenceUnit()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnit_Provider()
	{
		return (EAttribute)xmlPersistenceUnitEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getJtaDataSource <em>Jta Data Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Jta Data Source</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getJtaDataSource()
	 * @see #getXmlPersistenceUnit()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnit_JtaDataSource()
	{
		return (EAttribute)xmlPersistenceUnitEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getNonJtaDataSource <em>Non Jta Data Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Non Jta Data Source</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getNonJtaDataSource()
	 * @see #getXmlPersistenceUnit()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnit_NonJtaDataSource()
	{
		return (EAttribute)xmlPersistenceUnitEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getMappingFiles <em>Mapping Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mapping Files</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getMappingFiles()
	 * @see #getXmlPersistenceUnit()
	 * @generated
	 */
	public EReference getXmlPersistenceUnit_MappingFiles()
	{
		return (EReference)xmlPersistenceUnitEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getJarFiles <em>Jar Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Jar Files</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getJarFiles()
	 * @see #getXmlPersistenceUnit()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnit_JarFiles()
	{
		return (EAttribute)xmlPersistenceUnitEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getClasses <em>Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Classes</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getClasses()
	 * @see #getXmlPersistenceUnit()
	 * @generated
	 */
	public EReference getXmlPersistenceUnit_Classes()
	{
		return (EReference)xmlPersistenceUnitEClass.getEStructuralFeatures().get(6);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#isExcludeUnlistedClasses <em>Exclude Unlisted Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Unlisted Classes</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#isExcludeUnlistedClasses()
	 * @see #getXmlPersistenceUnit()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnit_ExcludeUnlistedClasses()
	{
		return (EAttribute)xmlPersistenceUnitEClass.getEStructuralFeatures().get(7);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Properties</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getProperties()
	 * @see #getXmlPersistenceUnit()
	 * @generated
	 */
	public EReference getXmlPersistenceUnit_Properties()
	{
		return (EReference)xmlPersistenceUnitEClass.getEStructuralFeatures().get(8);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getName()
	 * @see #getXmlPersistenceUnit()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnit_Name()
	{
		return (EAttribute)xmlPersistenceUnitEClass.getEStructuralFeatures().get(9);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getTransactionType <em>Transaction Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transaction Type</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit#getTransactionType()
	 * @see #getXmlPersistenceUnit()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnit_TransactionType()
	{
		return (EAttribute)xmlPersistenceUnitEClass.getEStructuralFeatures().get(10);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef <em>Xml Mapping File Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mapping File Ref</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef
	 * @generated
	 */
	public EClass getXmlMappingFileRef()
	{
		return xmlMappingFileRefEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef#getFileName <em>File Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Name</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef#getFileName()
	 * @see #getXmlMappingFileRef()
	 * @generated
	 */
	public EAttribute getXmlMappingFileRef_FileName()
	{
		return (EAttribute)xmlMappingFileRefEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef <em>Xml Java Class Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Java Class Ref</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef
	 * @generated
	 */
	public EClass getXmlJavaClassRef()
	{
		return xmlJavaClassRefEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef#getJavaClass <em>Java Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Java Class</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef#getJavaClass()
	 * @see #getXmlJavaClassRef()
	 * @generated
	 */
	public EAttribute getXmlJavaClassRef_JavaClass()
	{
		return (EAttribute)xmlJavaClassRefEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.persistence.XmlProperties <em>Xml Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Properties</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlProperties
	 * @generated
	 */
	public EClass getXmlProperties()
	{
		return xmlPropertiesEClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.persistence.XmlProperties#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlProperties#getProperties()
	 * @see #getXmlProperties()
	 * @generated
	 */
	public EReference getXmlProperties_Properties()
	{
		return (EReference)xmlPropertiesEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.persistence.XmlProperty <em>Xml Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Property</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlProperty
	 * @generated
	 */
	public EClass getXmlProperty()
	{
		return xmlPropertyEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.XmlProperty#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlProperty#getName()
	 * @see #getXmlProperty()
	 * @generated
	 */
	public EAttribute getXmlProperty_Name()
	{
		return (EAttribute)xmlPropertyEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.XmlProperty#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlProperty#getValue()
	 * @see #getXmlProperty()
	 * @generated
	 */
	public EAttribute getXmlProperty_Value()
	{
		return (EAttribute)xmlPropertyEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnitTransactionType <em>Xml Persistence Unit Transaction Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Xml Persistence Unit Transaction Type</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnitTransactionType
	 * @generated
	 */
	public EEnum getXmlPersistenceUnitTransactionType()
	{
		return xmlPersistenceUnitTransactionTypeEEnum;
	}


	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Xml Persistence Unit Transaction Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Xml Persistence Unit Transaction Type Object</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 * @generated
	 */
	public EDataType getXmlPersistenceUnitTransactionTypeObject()
	{
		return xmlPersistenceUnitTransactionTypeObjectEDataType;
	}


	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Xml Version</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	public EDataType getXmlVersion()
	{
		return xmlVersionEDataType;
	}


	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public PersistenceFactory getPersistenceFactory()
	{
		return (PersistenceFactory)getEFactoryInstance();
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
		xmlPersistenceEClass = createEClass(XML_PERSISTENCE);
		createEReference(xmlPersistenceEClass, XML_PERSISTENCE__PERSISTENCE_UNITS);
		createEAttribute(xmlPersistenceEClass, XML_PERSISTENCE__VERSION);

		xmlPersistenceUnitEClass = createEClass(XML_PERSISTENCE_UNIT);
		createEAttribute(xmlPersistenceUnitEClass, XML_PERSISTENCE_UNIT__DESCRIPTION);
		createEAttribute(xmlPersistenceUnitEClass, XML_PERSISTENCE_UNIT__PROVIDER);
		createEAttribute(xmlPersistenceUnitEClass, XML_PERSISTENCE_UNIT__JTA_DATA_SOURCE);
		createEAttribute(xmlPersistenceUnitEClass, XML_PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE);
		createEReference(xmlPersistenceUnitEClass, XML_PERSISTENCE_UNIT__MAPPING_FILES);
		createEAttribute(xmlPersistenceUnitEClass, XML_PERSISTENCE_UNIT__JAR_FILES);
		createEReference(xmlPersistenceUnitEClass, XML_PERSISTENCE_UNIT__CLASSES);
		createEAttribute(xmlPersistenceUnitEClass, XML_PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES);
		createEReference(xmlPersistenceUnitEClass, XML_PERSISTENCE_UNIT__PROPERTIES);
		createEAttribute(xmlPersistenceUnitEClass, XML_PERSISTENCE_UNIT__NAME);
		createEAttribute(xmlPersistenceUnitEClass, XML_PERSISTENCE_UNIT__TRANSACTION_TYPE);

		xmlMappingFileRefEClass = createEClass(XML_MAPPING_FILE_REF);
		createEAttribute(xmlMappingFileRefEClass, XML_MAPPING_FILE_REF__FILE_NAME);

		xmlJavaClassRefEClass = createEClass(XML_JAVA_CLASS_REF);
		createEAttribute(xmlJavaClassRefEClass, XML_JAVA_CLASS_REF__JAVA_CLASS);

		xmlPropertiesEClass = createEClass(XML_PROPERTIES);
		createEReference(xmlPropertiesEClass, XML_PROPERTIES__PROPERTIES);

		xmlPropertyEClass = createEClass(XML_PROPERTY);
		createEAttribute(xmlPropertyEClass, XML_PROPERTY__NAME);
		createEAttribute(xmlPropertyEClass, XML_PROPERTY__VALUE);

		// Create enums
		xmlPersistenceUnitTransactionTypeEEnum = createEEnum(XML_PERSISTENCE_UNIT_TRANSACTION_TYPE);

		// Create data types
		xmlPersistenceUnitTransactionTypeObjectEDataType = createEDataType(XML_PERSISTENCE_UNIT_TRANSACTION_TYPE_OBJECT);
		xmlVersionEDataType = createEDataType(XML_VERSION);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(xmlPersistenceEClass, XmlPersistence.class, "XmlPersistence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlPersistence_PersistenceUnits(), this.getXmlPersistenceUnit(), null, "persistenceUnits", null, 0, -1, XmlPersistence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistence_Version(), this.getXmlVersion(), "version", null, 1, 1, XmlPersistence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPersistenceUnitEClass, XmlPersistenceUnit.class, "XmlPersistenceUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPersistenceUnit_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlPersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistenceUnit_Provider(), theXMLTypePackage.getString(), "provider", null, 0, 1, XmlPersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistenceUnit_JtaDataSource(), theXMLTypePackage.getString(), "jtaDataSource", null, 0, 1, XmlPersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistenceUnit_NonJtaDataSource(), theXMLTypePackage.getString(), "nonJtaDataSource", null, 0, 1, XmlPersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPersistenceUnit_MappingFiles(), this.getXmlMappingFileRef(), null, "mappingFiles", null, 0, -1, XmlPersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistenceUnit_JarFiles(), theXMLTypePackage.getString(), "jarFiles", null, 0, -1, XmlPersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPersistenceUnit_Classes(), this.getXmlJavaClassRef(), null, "classes", null, 0, -1, XmlPersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistenceUnit_ExcludeUnlistedClasses(), theXMLTypePackage.getBoolean(), "excludeUnlistedClasses", "false", 0, 1, XmlPersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPersistenceUnit_Properties(), this.getXmlProperties(), null, "properties", null, 0, 1, XmlPersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistenceUnit_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, XmlPersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistenceUnit_TransactionType(), this.getXmlPersistenceUnitTransactionType(), "transactionType", "JTA", 0, 1, XmlPersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMappingFileRefEClass, XmlMappingFileRef.class, "XmlMappingFileRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlMappingFileRef_FileName(), theEcorePackage.getEString(), "fileName", "", 0, 1, XmlMappingFileRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(xmlJavaClassRefEClass, XmlJavaClassRef.class, "XmlJavaClassRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlJavaClassRef_JavaClass(), theEcorePackage.getEString(), "javaClass", null, 0, 1, XmlJavaClassRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(xmlPropertiesEClass, XmlProperties.class, "XmlProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlProperties_Properties(), this.getXmlProperty(), null, "properties", null, 0, -1, XmlProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPropertyEClass, XmlProperty.class, "XmlProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlProperty_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, XmlProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlProperty_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, XmlProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(xmlPersistenceUnitTransactionTypeEEnum, XmlPersistenceUnitTransactionType.class, "XmlPersistenceUnitTransactionType");
		addEEnumLiteral(xmlPersistenceUnitTransactionTypeEEnum, XmlPersistenceUnitTransactionType.JTA);
		addEEnumLiteral(xmlPersistenceUnitTransactionTypeEEnum, XmlPersistenceUnitTransactionType.RESOURCE_LOCAL);

		// Initialize data types
		initEDataType(xmlPersistenceUnitTransactionTypeObjectEDataType, Enumerator.class, "XmlPersistenceUnitTransactionTypeObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(xmlVersionEDataType, String.class, "XmlVersion", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistence <em>Xml Persistence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistence
		 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlPersistence()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE = eINSTANCE.getXmlPersistence();

		/**
		 * The meta object literal for the '<em><b>Persistence Units</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PERSISTENCE__PERSISTENCE_UNITS = eINSTANCE.getXmlPersistence_PersistenceUnits();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE__VERSION = eINSTANCE.getXmlPersistence_Version();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit <em>Xml Persistence Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit
		 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE_UNIT = eINSTANCE.getXmlPersistenceUnit();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT__DESCRIPTION = eINSTANCE.getXmlPersistenceUnit_Description();

		/**
		 * The meta object literal for the '<em><b>Provider</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT__PROVIDER = eINSTANCE.getXmlPersistenceUnit_Provider();

		/**
		 * The meta object literal for the '<em><b>Jta Data Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT__JTA_DATA_SOURCE = eINSTANCE.getXmlPersistenceUnit_JtaDataSource();

		/**
		 * The meta object literal for the '<em><b>Non Jta Data Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE = eINSTANCE.getXmlPersistenceUnit_NonJtaDataSource();

		/**
		 * The meta object literal for the '<em><b>Mapping Files</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PERSISTENCE_UNIT__MAPPING_FILES = eINSTANCE.getXmlPersistenceUnit_MappingFiles();

		/**
		 * The meta object literal for the '<em><b>Jar Files</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT__JAR_FILES = eINSTANCE.getXmlPersistenceUnit_JarFiles();

		/**
		 * The meta object literal for the '<em><b>Classes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PERSISTENCE_UNIT__CLASSES = eINSTANCE.getXmlPersistenceUnit_Classes();

		/**
		 * The meta object literal for the '<em><b>Exclude Unlisted Classes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES = eINSTANCE.getXmlPersistenceUnit_ExcludeUnlistedClasses();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PERSISTENCE_UNIT__PROPERTIES = eINSTANCE.getXmlPersistenceUnit_Properties();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT__NAME = eINSTANCE.getXmlPersistenceUnit_Name();

		/**
		 * The meta object literal for the '<em><b>Transaction Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT__TRANSACTION_TYPE = eINSTANCE.getXmlPersistenceUnit_TransactionType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef <em>Xml Mapping File Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef
		 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlMappingFileRef()
		 * @generated
		 */
		public static final EClass XML_MAPPING_FILE_REF = eINSTANCE.getXmlMappingFileRef();

		/**
		 * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MAPPING_FILE_REF__FILE_NAME = eINSTANCE.getXmlMappingFileRef_FileName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef <em>Xml Java Class Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef
		 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlJavaClassRef()
		 * @generated
		 */
		public static final EClass XML_JAVA_CLASS_REF = eINSTANCE.getXmlJavaClassRef();

		/**
		 * The meta object literal for the '<em><b>Java Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_JAVA_CLASS_REF__JAVA_CLASS = eINSTANCE.getXmlJavaClassRef_JavaClass();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.persistence.XmlProperties <em>Xml Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.persistence.XmlProperties
		 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlProperties()
		 * @generated
		 */
		public static final EClass XML_PROPERTIES = eINSTANCE.getXmlProperties();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PROPERTIES__PROPERTIES = eINSTANCE.getXmlProperties_Properties();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.persistence.XmlProperty <em>Xml Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.persistence.XmlProperty
		 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlProperty()
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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnitTransactionType <em>Xml Persistence Unit Transaction Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnitTransactionType
		 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlPersistenceUnitTransactionType()
		 * @generated
		 */
		public static final EEnum XML_PERSISTENCE_UNIT_TRANSACTION_TYPE = eINSTANCE.getXmlPersistenceUnitTransactionType();

		/**
		 * The meta object literal for the '<em>Xml Persistence Unit Transaction Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlPersistenceUnitTransactionTypeObject()
		 * @generated
		 */
		public static final EDataType XML_PERSISTENCE_UNIT_TRANSACTION_TYPE_OBJECT = eINSTANCE.getXmlPersistenceUnitTransactionTypeObject();

		/**
		 * The meta object literal for the '<em>Xml Version</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlVersion()
		 * @generated
		 */
		public static final EDataType XML_VERSION = eINSTANCE.getXmlVersion();

	}

} //PersistencePackage
