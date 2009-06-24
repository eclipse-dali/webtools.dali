/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.resource.persistence;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.persistence.PersistencePackage;
import org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package;

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
 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Factory
 * @model kind="package"
 * @generated
 */
public class Persistence2_0Package extends EPackageImpl
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
	public static final String eNS_URI = "jpt2_0.persistence.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt2_0.core.resource.persistence";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final Persistence2_0Package eINSTANCE = org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistence <em>Xml Persistence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistence
	 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package#getXmlPersistence()
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
	public static final int XML_PERSISTENCE__PERSISTENCE_UNITS = PersistencePackage.XML_PERSISTENCE__PERSISTENCE_UNITS;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE__VERSION = PersistencePackage.XML_PERSISTENCE__VERSION;

	/**
	 * The number of structural features of the '<em>Xml Persistence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_FEATURE_COUNT = PersistencePackage.XML_PERSISTENCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit <em>Xml Persistence Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit
	 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package#getXmlPersistenceUnit()
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
	public static final int XML_PERSISTENCE_UNIT__DESCRIPTION = PersistencePackage.XML_PERSISTENCE_UNIT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__PROVIDER = PersistencePackage.XML_PERSISTENCE_UNIT__PROVIDER;

	/**
	 * The feature id for the '<em><b>Jta Data Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__JTA_DATA_SOURCE = PersistencePackage.XML_PERSISTENCE_UNIT__JTA_DATA_SOURCE;

	/**
	 * The feature id for the '<em><b>Non Jta Data Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE = PersistencePackage.XML_PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE;

	/**
	 * The feature id for the '<em><b>Mapping Files</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__MAPPING_FILES = PersistencePackage.XML_PERSISTENCE_UNIT__MAPPING_FILES;

	/**
	 * The feature id for the '<em><b>Jar Files</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__JAR_FILES = PersistencePackage.XML_PERSISTENCE_UNIT__JAR_FILES;

	/**
	 * The feature id for the '<em><b>Classes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__CLASSES = PersistencePackage.XML_PERSISTENCE_UNIT__CLASSES;

	/**
	 * The feature id for the '<em><b>Exclude Unlisted Classes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES = PersistencePackage.XML_PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__PROPERTIES = PersistencePackage.XML_PERSISTENCE_UNIT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__NAME = PersistencePackage.XML_PERSISTENCE_UNIT__NAME;

	/**
	 * The feature id for the '<em><b>Transaction Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__TRANSACTION_TYPE = PersistencePackage.XML_PERSISTENCE_UNIT__TRANSACTION_TYPE;

	/**
	 * The feature id for the '<em><b>Shared Cache Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__SHARED_CACHE_MODE = PersistencePackage.XML_PERSISTENCE_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Validation Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT__VALIDATION_MODE = PersistencePackage.XML_PERSISTENCE_UNIT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Persistence Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_FEATURE_COUNT = PersistencePackage.XML_PERSISTENCE_UNIT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitCachingType <em>Xml Persistence Unit Caching Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitCachingType
	 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package#getXmlPersistenceUnitCachingType()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_CACHING_TYPE = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitValidationModeType <em>Xml Persistence Unit Validation Mode Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitValidationModeType
	 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package#getXmlPersistenceUnitValidationModeType()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_VALIDATION_MODE_TYPE = 3;

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
	private EEnum xmlPersistenceUnitCachingTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum xmlPersistenceUnitValidationModeTypeEEnum = null;

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
	 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private Persistence2_0Package()
	{
		super(eNS_URI, Persistence2_0Factory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link Persistence2_0Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static Persistence2_0Package init()
	{
		if (isInited) return (Persistence2_0Package)EPackage.Registry.INSTANCE.getEPackage(Persistence2_0Package.eNS_URI);

		// Obtain or create and register package
		Persistence2_0Package thePersistence2_0Package = (Persistence2_0Package)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof Persistence2_0Package ? EPackage.Registry.INSTANCE.get(eNS_URI) : new Persistence2_0Package());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Orm2_0Package theOrm2_0Package = (Orm2_0Package)(EPackage.Registry.INSTANCE.getEPackage(Orm2_0Package.eNS_URI) instanceof Orm2_0Package ? EPackage.Registry.INSTANCE.getEPackage(Orm2_0Package.eNS_URI) : Orm2_0Package.eINSTANCE);
		OrmPackage theOrmPackage = (OrmPackage)(EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) : OrmPackage.eINSTANCE);
		PersistencePackage thePersistencePackage = (PersistencePackage)(EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);

		// Create package meta-data objects
		thePersistence2_0Package.createPackageContents();
		theOrm2_0Package.createPackageContents();
		theOrmPackage.createPackageContents();
		thePersistencePackage.createPackageContents();

		// Initialize created meta-data
		thePersistence2_0Package.initializePackageContents();
		theOrm2_0Package.initializePackageContents();
		theOrmPackage.initializePackageContents();
		thePersistencePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePersistence2_0Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(Persistence2_0Package.eNS_URI, thePersistence2_0Package);
		return thePersistence2_0Package;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistence <em>Xml Persistence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence</em>'.
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistence
	 * @generated
	 */
	public EClass getXmlPersistence()
	{
		return xmlPersistenceEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit <em>Xml Persistence Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence Unit</em>'.
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit
	 * @generated
	 */
	public EClass getXmlPersistenceUnit()
	{
		return xmlPersistenceUnitEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit#getSharedCacheMode <em>Shared Cache Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Shared Cache Mode</em>'.
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit#getSharedCacheMode()
	 * @see #getXmlPersistenceUnit()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnit_SharedCacheMode()
	{
		return (EAttribute)xmlPersistenceUnitEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit#getValidationMode <em>Validation Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Validation Mode</em>'.
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit#getValidationMode()
	 * @see #getXmlPersistenceUnit()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnit_ValidationMode()
	{
		return (EAttribute)xmlPersistenceUnitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitCachingType <em>Xml Persistence Unit Caching Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Xml Persistence Unit Caching Type</em>'.
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitCachingType
	 * @generated
	 */
	public EEnum getXmlPersistenceUnitCachingType()
	{
		return xmlPersistenceUnitCachingTypeEEnum;
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitValidationModeType <em>Xml Persistence Unit Validation Mode Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Xml Persistence Unit Validation Mode Type</em>'.
	 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitValidationModeType
	 * @generated
	 */
	public EEnum getXmlPersistenceUnitValidationModeType()
	{
		return xmlPersistenceUnitValidationModeTypeEEnum;
	}


	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public Persistence2_0Factory getPersistence2_0Factory()
	{
		return (Persistence2_0Factory)getEFactoryInstance();
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

		xmlPersistenceUnitEClass = createEClass(XML_PERSISTENCE_UNIT);
		createEAttribute(xmlPersistenceUnitEClass, XML_PERSISTENCE_UNIT__SHARED_CACHE_MODE);
		createEAttribute(xmlPersistenceUnitEClass, XML_PERSISTENCE_UNIT__VALIDATION_MODE);

		// Create enums
		xmlPersistenceUnitCachingTypeEEnum = createEEnum(XML_PERSISTENCE_UNIT_CACHING_TYPE);
		xmlPersistenceUnitValidationModeTypeEEnum = createEEnum(XML_PERSISTENCE_UNIT_VALIDATION_MODE_TYPE);
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
		PersistencePackage thePersistencePackage = (PersistencePackage)EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		xmlPersistenceEClass.getESuperTypes().add(thePersistencePackage.getXmlPersistence());
		xmlPersistenceUnitEClass.getESuperTypes().add(thePersistencePackage.getXmlPersistenceUnit());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlPersistenceEClass, XmlPersistence.class, "XmlPersistence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPersistenceUnitEClass, XmlPersistenceUnit.class, "XmlPersistenceUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPersistenceUnit_SharedCacheMode(), this.getXmlPersistenceUnitCachingType(), "sharedCacheMode", "JTA", 0, 1, XmlPersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistenceUnit_ValidationMode(), this.getXmlPersistenceUnitValidationModeType(), "validationMode", "JTA", 0, 1, XmlPersistenceUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(xmlPersistenceUnitCachingTypeEEnum, XmlPersistenceUnitCachingType.class, "XmlPersistenceUnitCachingType");
		addEEnumLiteral(xmlPersistenceUnitCachingTypeEEnum, XmlPersistenceUnitCachingType.ALL);
		addEEnumLiteral(xmlPersistenceUnitCachingTypeEEnum, XmlPersistenceUnitCachingType.NONE);
		addEEnumLiteral(xmlPersistenceUnitCachingTypeEEnum, XmlPersistenceUnitCachingType.ENABLE_SELECTIVE);
		addEEnumLiteral(xmlPersistenceUnitCachingTypeEEnum, XmlPersistenceUnitCachingType.DISABLE_SELECTIVE);
		addEEnumLiteral(xmlPersistenceUnitCachingTypeEEnum, XmlPersistenceUnitCachingType.UNSPECIFIED);

		initEEnum(xmlPersistenceUnitValidationModeTypeEEnum, XmlPersistenceUnitValidationModeType.class, "XmlPersistenceUnitValidationModeType");
		addEEnumLiteral(xmlPersistenceUnitValidationModeTypeEEnum, XmlPersistenceUnitValidationModeType.AUTO);
		addEEnumLiteral(xmlPersistenceUnitValidationModeTypeEEnum, XmlPersistenceUnitValidationModeType.CALLBACK);
		addEEnumLiteral(xmlPersistenceUnitValidationModeTypeEEnum, XmlPersistenceUnitValidationModeType.NONE);

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
		 * The meta object literal for the '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistence <em>Xml Persistence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistence
		 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package#getXmlPersistence()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE = eINSTANCE.getXmlPersistence();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit <em>Xml Persistence Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit
		 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package#getXmlPersistenceUnit()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE_UNIT = eINSTANCE.getXmlPersistenceUnit();

		/**
		 * The meta object literal for the '<em><b>Shared Cache Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT__SHARED_CACHE_MODE = eINSTANCE.getXmlPersistenceUnit_SharedCacheMode();

		/**
		 * The meta object literal for the '<em><b>Validation Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT__VALIDATION_MODE = eINSTANCE.getXmlPersistenceUnit_ValidationMode();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitCachingType <em>Xml Persistence Unit Caching Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitCachingType
		 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package#getXmlPersistenceUnitCachingType()
		 * @generated
		 */
		public static final EEnum XML_PERSISTENCE_UNIT_CACHING_TYPE = eINSTANCE.getXmlPersistenceUnitCachingType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitValidationModeType <em>Xml Persistence Unit Validation Mode Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnitValidationModeType
		 * @see org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Package#getXmlPersistenceUnitValidationModeType()
		 * @generated
		 */
		public static final EEnum XML_PERSISTENCE_UNIT_VALIDATION_MODE_TYPE = eINSTANCE.getXmlPersistenceUnitValidationModeType();

	}

} //Persistence2_0Package
