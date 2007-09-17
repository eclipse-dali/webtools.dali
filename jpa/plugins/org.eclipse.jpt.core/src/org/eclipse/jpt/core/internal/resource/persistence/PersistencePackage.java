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

package org.eclipse.jpt.core.internal.resource.persistence;

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
import org.eclipse.jpt.core.internal.resource.orm.OrmPackage;

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
 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory
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
	public static final PersistencePackage eINSTANCE = org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.persistence.Persistence <em>Persistence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.persistence.Persistence
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getPersistence()
	 * @generated
	 */
	public static final int PERSISTENCE = 0;

	/**
	 * The feature id for the '<em><b>Persistence Units</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE__PERSISTENCE_UNITS = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE__VERSION = 1;

	/**
	 * The number of structural features of the '<em>Persistence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit <em>Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getPersistenceUnit()
	 * @generated
	 */
	public static final int PERSISTENCE_UNIT = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT__PROVIDER = 1;

	/**
	 * The feature id for the '<em><b>Jta Data Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT__JTA_DATA_SOURCE = 2;

	/**
	 * The feature id for the '<em><b>Non Jta Data Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE = 3;

	/**
	 * The feature id for the '<em><b>Mapping Files</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT__MAPPING_FILES = 4;

	/**
	 * The feature id for the '<em><b>Jar Files</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT__JAR_FILES = 5;

	/**
	 * The feature id for the '<em><b>Classes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT__CLASSES = 6;

	/**
	 * The feature id for the '<em><b>Exclude Unlisted Classes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES = 7;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT__PROPERTIES = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT__NAME = 9;

	/**
	 * The feature id for the '<em><b>Transaction Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT__TRANSACTION_TYPE = 10;

	/**
	 * The number of structural features of the '<em>Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_UNIT_FEATURE_COUNT = 11;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.persistence.MappingFileRef <em>Mapping File Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.persistence.MappingFileRef
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getMappingFileRef()
	 * @generated
	 */
	public static final int MAPPING_FILE_REF = 2;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPING_FILE_REF__FILE_NAME = 0;

	/**
	 * The number of structural features of the '<em>Mapping File Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAPPING_FILE_REF_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.persistence.JavaClassRef <em>Java Class Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.persistence.JavaClassRef
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getJavaClassRef()
	 * @generated
	 */
	public static final int JAVA_CLASS_REF = 3;

	/**
	 * The feature id for the '<em><b>Java Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_CLASS_REF__JAVA_CLASS = 0;

	/**
	 * The number of structural features of the '<em>Java Class Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_CLASS_REF_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.persistence.Properties <em>Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.persistence.Properties
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getProperties()
	 * @generated
	 */
	public static final int PROPERTIES = 4;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTIES__PROPERTIES = 0;

	/**
	 * The number of structural features of the '<em>Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTIES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.persistence.Property <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.persistence.Property
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getProperty()
	 * @generated
	 */
	public static final int PROPERTY = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnitTransactionType <em>Unit Transaction Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnitTransactionType
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getPersistenceUnitTransactionType()
	 * @generated
	 */
	public static final int PERSISTENCE_UNIT_TRANSACTION_TYPE = 6;

	/**
	 * The meta object id for the '<em>Unit Transaction Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getPersistenceUnitTransactionTypeObject()
	 * @generated
	 */
	public static final int PERSISTENCE_UNIT_TRANSACTION_TYPE_OBJECT = 7;

	/**
	 * The meta object id for the '<em>Version</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getVersion()
	 * @generated
	 */
	public static final int VERSION = 8;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass persistenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass persistenceUnitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mappingFileRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaClassRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum persistenceUnitTransactionTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType persistenceUnitTransactionTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType versionEDataType = null;

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
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#eNS_URI
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
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.persistence.Persistence <em>Persistence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Persistence</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.Persistence
	 * @generated
	 */
	public EClass getPersistence()
	{
		return persistenceEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.persistence.Persistence#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.Persistence#getVersion()
	 * @see #getPersistence()
	 * @generated
	 */
	public EAttribute getPersistence_Version()
	{
		return (EAttribute)persistenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.persistence.Persistence#getPersistenceUnits <em>Persistence Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Persistence Units</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.Persistence#getPersistenceUnits()
	 * @see #getPersistence()
	 * @generated
	 */
	public EReference getPersistence_PersistenceUnits()
	{
		return (EReference)persistenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unit</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit
	 * @generated
	 */
	public EClass getPersistenceUnit()
	{
		return persistenceUnitEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getName()
	 * @see #getPersistenceUnit()
	 * @generated
	 */
	public EAttribute getPersistenceUnit_Name()
	{
		return (EAttribute)persistenceUnitEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getTransactionType <em>Transaction Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transaction Type</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getTransactionType()
	 * @see #getPersistenceUnit()
	 * @generated
	 */
	public EAttribute getPersistenceUnit_TransactionType()
	{
		return (EAttribute)persistenceUnitEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getDescription()
	 * @see #getPersistenceUnit()
	 * @generated
	 */
	public EAttribute getPersistenceUnit_Description()
	{
		return (EAttribute)persistenceUnitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Provider</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getProvider()
	 * @see #getPersistenceUnit()
	 * @generated
	 */
	public EAttribute getPersistenceUnit_Provider()
	{
		return (EAttribute)persistenceUnitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getJtaDataSource <em>Jta Data Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Jta Data Source</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getJtaDataSource()
	 * @see #getPersistenceUnit()
	 * @generated
	 */
	public EAttribute getPersistenceUnit_JtaDataSource()
	{
		return (EAttribute)persistenceUnitEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getNonJtaDataSource <em>Non Jta Data Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Non Jta Data Source</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getNonJtaDataSource()
	 * @see #getPersistenceUnit()
	 * @generated
	 */
	public EAttribute getPersistenceUnit_NonJtaDataSource()
	{
		return (EAttribute)persistenceUnitEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getMappingFiles <em>Mapping Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mapping Files</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getMappingFiles()
	 * @see #getPersistenceUnit()
	 * @generated
	 */
	public EReference getPersistenceUnit_MappingFiles()
	{
		return (EReference)persistenceUnitEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getJarFiles <em>Jar Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Jar Files</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getJarFiles()
	 * @see #getPersistenceUnit()
	 * @generated
	 */
	public EAttribute getPersistenceUnit_JarFiles()
	{
		return (EAttribute)persistenceUnitEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getClasses <em>Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Classes</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getClasses()
	 * @see #getPersistenceUnit()
	 * @generated
	 */
	public EReference getPersistenceUnit_Classes()
	{
		return (EReference)persistenceUnitEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#isExcludeUnlistedClasses <em>Exclude Unlisted Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Unlisted Classes</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#isExcludeUnlistedClasses()
	 * @see #getPersistenceUnit()
	 * @generated
	 */
	public EAttribute getPersistenceUnit_ExcludeUnlistedClasses()
	{
		return (EAttribute)persistenceUnitEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Properties</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit#getProperties()
	 * @see #getPersistenceUnit()
	 * @generated
	 */
	public EReference getPersistenceUnit_Properties()
	{
		return (EReference)persistenceUnitEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.persistence.MappingFileRef <em>Mapping File Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping File Ref</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.MappingFileRef
	 * @generated
	 */
	public EClass getMappingFileRef()
	{
		return mappingFileRefEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.persistence.MappingFileRef#getFileName <em>File Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.MappingFileRef#getFileName()
	 * @see #getMappingFileRef()
	 * @generated
	 */
	public EAttribute getMappingFileRef_FileName()
	{
		return (EAttribute)mappingFileRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.persistence.JavaClassRef <em>Java Class Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Class Ref</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.JavaClassRef
	 * @generated
	 */
	public EClass getJavaClassRef()
	{
		return javaClassRefEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.persistence.JavaClassRef#getJavaClass <em>Java Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Java Class</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.JavaClassRef#getJavaClass()
	 * @see #getJavaClassRef()
	 * @generated
	 */
	public EAttribute getJavaClassRef_JavaClass()
	{
		return (EAttribute)javaClassRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.persistence.Properties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Properties</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.Properties
	 * @generated
	 */
	public EClass getProperties()
	{
		return propertiesEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.resource.persistence.Properties#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.Properties#getProperties()
	 * @see #getProperties()
	 * @generated
	 */
	public EReference getProperties_Properties()
	{
		return (EReference)propertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.resource.persistence.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.Property
	 * @generated
	 */
	public EClass getProperty()
	{
		return propertyEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.persistence.Property#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.Property#getName()
	 * @see #getProperty()
	 * @generated
	 */
	public EAttribute getProperty_Name()
	{
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.resource.persistence.Property#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.Property#getValue()
	 * @see #getProperty()
	 * @generated
	 */
	public EAttribute getProperty_Value()
	{
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnitTransactionType <em>Unit Transaction Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Unit Transaction Type</em>'.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnitTransactionType
	 * @generated
	 */
	public EEnum getPersistenceUnitTransactionType()
	{
		return persistenceUnitTransactionTypeEEnum;
	}

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Unit Transaction Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Unit Transaction Type Object</em>'.
	 * @see org.eclipse.emf.common.util.Enumerator
	 * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
	 * @generated
	 */
	public EDataType getPersistenceUnitTransactionTypeObject()
	{
		return persistenceUnitTransactionTypeObjectEDataType;
	}

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Version</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	public EDataType getVersion()
	{
		return versionEDataType;
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
		persistenceEClass = createEClass(PERSISTENCE);
		createEReference(persistenceEClass, PERSISTENCE__PERSISTENCE_UNITS);
		createEAttribute(persistenceEClass, PERSISTENCE__VERSION);

		persistenceUnitEClass = createEClass(PERSISTENCE_UNIT);
		createEAttribute(persistenceUnitEClass, PERSISTENCE_UNIT__DESCRIPTION);
		createEAttribute(persistenceUnitEClass, PERSISTENCE_UNIT__PROVIDER);
		createEAttribute(persistenceUnitEClass, PERSISTENCE_UNIT__JTA_DATA_SOURCE);
		createEAttribute(persistenceUnitEClass, PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE);
		createEReference(persistenceUnitEClass, PERSISTENCE_UNIT__MAPPING_FILES);
		createEAttribute(persistenceUnitEClass, PERSISTENCE_UNIT__JAR_FILES);
		createEReference(persistenceUnitEClass, PERSISTENCE_UNIT__CLASSES);
		createEAttribute(persistenceUnitEClass, PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES);
		createEReference(persistenceUnitEClass, PERSISTENCE_UNIT__PROPERTIES);
		createEAttribute(persistenceUnitEClass, PERSISTENCE_UNIT__NAME);
		createEAttribute(persistenceUnitEClass, PERSISTENCE_UNIT__TRANSACTION_TYPE);

		mappingFileRefEClass = createEClass(MAPPING_FILE_REF);
		createEAttribute(mappingFileRefEClass, MAPPING_FILE_REF__FILE_NAME);

		javaClassRefEClass = createEClass(JAVA_CLASS_REF);
		createEAttribute(javaClassRefEClass, JAVA_CLASS_REF__JAVA_CLASS);

		propertiesEClass = createEClass(PROPERTIES);
		createEReference(propertiesEClass, PROPERTIES__PROPERTIES);

		propertyEClass = createEClass(PROPERTY);
		createEAttribute(propertyEClass, PROPERTY__NAME);
		createEAttribute(propertyEClass, PROPERTY__VALUE);

		// Create enums
		persistenceUnitTransactionTypeEEnum = createEEnum(PERSISTENCE_UNIT_TRANSACTION_TYPE);

		// Create data types
		persistenceUnitTransactionTypeObjectEDataType = createEDataType(PERSISTENCE_UNIT_TRANSACTION_TYPE_OBJECT);
		versionEDataType = createEDataType(VERSION);
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
		initEClass(persistenceEClass, Persistence.class, "Persistence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPersistence_PersistenceUnits(), this.getPersistenceUnit(), null, "persistenceUnits", null, 0, -1, Persistence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistence_Version(), this.getVersion(), "version", null, 1, 1, Persistence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(persistenceUnitEClass, PersistenceUnit.class, "PersistenceUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPersistenceUnit_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, PersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnit_Provider(), theXMLTypePackage.getString(), "provider", null, 0, 1, PersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnit_JtaDataSource(), theXMLTypePackage.getString(), "jtaDataSource", null, 0, 1, PersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnit_NonJtaDataSource(), theXMLTypePackage.getString(), "nonJtaDataSource", null, 0, 1, PersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPersistenceUnit_MappingFiles(), this.getMappingFileRef(), null, "mappingFiles", null, 0, -1, PersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnit_JarFiles(), theXMLTypePackage.getString(), "jarFiles", null, 0, -1, PersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPersistenceUnit_Classes(), this.getJavaClassRef(), null, "classes", null, 0, -1, PersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnit_ExcludeUnlistedClasses(), theXMLTypePackage.getBoolean(), "excludeUnlistedClasses", "false", 0, 1, PersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPersistenceUnit_Properties(), this.getProperties(), null, "properties", null, 0, 1, PersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnit_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, PersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPersistenceUnit_TransactionType(), this.getPersistenceUnitTransactionType(), "transactionType", "JTA", 0, 1, PersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingFileRefEClass, MappingFileRef.class, "MappingFileRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMappingFileRef_FileName(), theEcorePackage.getEString(), "fileName", "", 0, 1, MappingFileRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(javaClassRefEClass, JavaClassRef.class, "JavaClassRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJavaClassRef_JavaClass(), theEcorePackage.getEString(), "javaClass", null, 0, 1, JavaClassRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(propertiesEClass, Properties.class, "Properties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProperties_Properties(), this.getProperty(), null, "properties", null, 0, -1, Properties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProperty_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(persistenceUnitTransactionTypeEEnum, PersistenceUnitTransactionType.class, "PersistenceUnitTransactionType");
		addEEnumLiteral(persistenceUnitTransactionTypeEEnum, PersistenceUnitTransactionType.JTA);
		addEEnumLiteral(persistenceUnitTransactionTypeEEnum, PersistenceUnitTransactionType.RESOURCE_LOCAL);

		// Initialize data types
		initEDataType(persistenceUnitTransactionTypeObjectEDataType, Enumerator.class, "PersistenceUnitTransactionTypeObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(versionEDataType, String.class, "Version", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.persistence.Persistence <em>Persistence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.persistence.Persistence
		 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getPersistence()
		 * @generated
		 */
		public static final EClass PERSISTENCE = eINSTANCE.getPersistence();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE__VERSION = eINSTANCE.getPersistence_Version();

		/**
		 * The meta object literal for the '<em><b>Persistence Units</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PERSISTENCE__PERSISTENCE_UNITS = eINSTANCE.getPersistence_PersistenceUnits();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit <em>Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit
		 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getPersistenceUnit()
		 * @generated
		 */
		public static final EClass PERSISTENCE_UNIT = eINSTANCE.getPersistenceUnit();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT__NAME = eINSTANCE.getPersistenceUnit_Name();

		/**
		 * The meta object literal for the '<em><b>Transaction Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT__TRANSACTION_TYPE = eINSTANCE.getPersistenceUnit_TransactionType();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT__DESCRIPTION = eINSTANCE.getPersistenceUnit_Description();

		/**
		 * The meta object literal for the '<em><b>Provider</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT__PROVIDER = eINSTANCE.getPersistenceUnit_Provider();

		/**
		 * The meta object literal for the '<em><b>Jta Data Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT__JTA_DATA_SOURCE = eINSTANCE.getPersistenceUnit_JtaDataSource();

		/**
		 * The meta object literal for the '<em><b>Non Jta Data Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE = eINSTANCE.getPersistenceUnit_NonJtaDataSource();

		/**
		 * The meta object literal for the '<em><b>Mapping Files</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PERSISTENCE_UNIT__MAPPING_FILES = eINSTANCE.getPersistenceUnit_MappingFiles();

		/**
		 * The meta object literal for the '<em><b>Jar Files</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT__JAR_FILES = eINSTANCE.getPersistenceUnit_JarFiles();

		/**
		 * The meta object literal for the '<em><b>Classes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PERSISTENCE_UNIT__CLASSES = eINSTANCE.getPersistenceUnit_Classes();

		/**
		 * The meta object literal for the '<em><b>Exclude Unlisted Classes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES = eINSTANCE.getPersistenceUnit_ExcludeUnlistedClasses();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PERSISTENCE_UNIT__PROPERTIES = eINSTANCE.getPersistenceUnit_Properties();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.persistence.MappingFileRef <em>Mapping File Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.persistence.MappingFileRef
		 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getMappingFileRef()
		 * @generated
		 */
		public static final EClass MAPPING_FILE_REF = eINSTANCE.getMappingFileRef();

		/**
		 * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MAPPING_FILE_REF__FILE_NAME = eINSTANCE.getMappingFileRef_FileName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.persistence.JavaClassRef <em>Java Class Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.persistence.JavaClassRef
		 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getJavaClassRef()
		 * @generated
		 */
		public static final EClass JAVA_CLASS_REF = eINSTANCE.getJavaClassRef();

		/**
		 * The meta object literal for the '<em><b>Java Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JAVA_CLASS_REF__JAVA_CLASS = eINSTANCE.getJavaClassRef_JavaClass();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.persistence.Properties <em>Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.persistence.Properties
		 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getProperties()
		 * @generated
		 */
		public static final EClass PROPERTIES = eINSTANCE.getProperties();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PROPERTIES__PROPERTIES = eINSTANCE.getProperties_Properties();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.persistence.Property <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.persistence.Property
		 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getProperty()
		 * @generated
		 */
		public static final EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PROPERTY__NAME = eINSTANCE.getProperty_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute PROPERTY__VALUE = eINSTANCE.getProperty_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnitTransactionType <em>Unit Transaction Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnitTransactionType
		 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getPersistenceUnitTransactionType()
		 * @generated
		 */
		public static final EEnum PERSISTENCE_UNIT_TRANSACTION_TYPE = eINSTANCE.getPersistenceUnitTransactionType();

		/**
		 * The meta object literal for the '<em>Unit Transaction Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.Enumerator
		 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getPersistenceUnitTransactionTypeObject()
		 * @generated
		 */
		public static final EDataType PERSISTENCE_UNIT_TRANSACTION_TYPE_OBJECT = eINSTANCE.getPersistenceUnitTransactionTypeObject();

		/**
		 * The meta object literal for the '<em>Version</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getVersion()
		 * @generated
		 */
		public static final EDataType VERSION = eINSTANCE.getVersion();

	}

} //PersistencePackage
