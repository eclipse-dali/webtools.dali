/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.resource.persistence.v2_0;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.core.resource.persistence.PersistencePackage;
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
 * @see org.eclipse.jpt.core.resource.persistence.v2_0.PersistenceV2_0Factory
 * @model kind="package"
 * @generated
 */
public class PersistenceV2_0Package extends EPackageImpl
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
	public static final String eNS_URI = "jpt.persistence.v2_0.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.core.resource.persistence.v2_0";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final PersistenceV2_0Package eINSTANCE = org.eclipse.jpt.core.resource.persistence.v2_0.PersistenceV2_0Package.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnit_2_0 <em>Xml Persistence Unit 20</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnit_2_0
	 * @see org.eclipse.jpt.core.resource.persistence.v2_0.PersistenceV2_0Package#getXmlPersistenceUnit_2_0()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_20 = 0;

	/**
	 * The feature id for the '<em><b>Shared Cache Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_20__SHARED_CACHE_MODE = 0;

	/**
	 * The feature id for the '<em><b>Validation Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_20__VALIDATION_MODE = 1;

	/**
	 * The number of structural features of the '<em>Xml Persistence Unit 20</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_20_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitCachingType_2_0 <em>Xml Persistence Unit Caching Type 20</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitCachingType_2_0
	 * @see org.eclipse.jpt.core.resource.persistence.v2_0.PersistenceV2_0Package#getXmlPersistenceUnitCachingType_2_0()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_CACHING_TYPE_20 = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitValidationModeType_2_0 <em>Xml Persistence Unit Validation Mode Type 20</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitValidationModeType_2_0
	 * @see org.eclipse.jpt.core.resource.persistence.v2_0.PersistenceV2_0Package#getXmlPersistenceUnitValidationModeType_2_0()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_VALIDATION_MODE_TYPE_20 = 2;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPersistenceUnit_2_0EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum xmlPersistenceUnitCachingType_2_0EEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum xmlPersistenceUnitValidationModeType_2_0EEnum = null;

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
	 * @see org.eclipse.jpt.core.resource.persistence.v2_0.PersistenceV2_0Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PersistenceV2_0Package()
	{
		super(eNS_URI, PersistenceV2_0Factory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link PersistenceV2_0Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PersistenceV2_0Package init()
	{
		if (isInited) return (PersistenceV2_0Package)EPackage.Registry.INSTANCE.getEPackage(PersistenceV2_0Package.eNS_URI);

		// Obtain or create and register package
		PersistenceV2_0Package thePersistenceV2_0Package = (PersistenceV2_0Package)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PersistenceV2_0Package ? EPackage.Registry.INSTANCE.get(eNS_URI) : new PersistenceV2_0Package());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		CommonPackage theCommonPackage = (CommonPackage)(EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) instanceof CommonPackage ? EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) : CommonPackage.eINSTANCE);
		OrmPackage theOrmPackage = (OrmPackage)(EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) : OrmPackage.eINSTANCE);
		OrmV2_0Package theOrmV2_0Package = (OrmV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(OrmV2_0Package.eNS_URI) instanceof OrmV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(OrmV2_0Package.eNS_URI) : OrmV2_0Package.eINSTANCE);
		PersistencePackage thePersistencePackage = (PersistencePackage)(EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);

		// Create package meta-data objects
		thePersistenceV2_0Package.createPackageContents();
		theCommonPackage.createPackageContents();
		theOrmPackage.createPackageContents();
		theOrmV2_0Package.createPackageContents();
		thePersistencePackage.createPackageContents();

		// Initialize created meta-data
		thePersistenceV2_0Package.initializePackageContents();
		theCommonPackage.initializePackageContents();
		theOrmPackage.initializePackageContents();
		theOrmV2_0Package.initializePackageContents();
		thePersistencePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePersistenceV2_0Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PersistenceV2_0Package.eNS_URI, thePersistenceV2_0Package);
		return thePersistenceV2_0Package;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnit_2_0 <em>Xml Persistence Unit 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence Unit 20</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnit_2_0
	 * @generated
	 */
	public EClass getXmlPersistenceUnit_2_0()
	{
		return xmlPersistenceUnit_2_0EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnit_2_0#getSharedCacheMode <em>Shared Cache Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Shared Cache Mode</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnit_2_0#getSharedCacheMode()
	 * @see #getXmlPersistenceUnit_2_0()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnit_2_0_SharedCacheMode()
	{
		return (EAttribute)xmlPersistenceUnit_2_0EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnit_2_0#getValidationMode <em>Validation Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Validation Mode</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnit_2_0#getValidationMode()
	 * @see #getXmlPersistenceUnit_2_0()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnit_2_0_ValidationMode()
	{
		return (EAttribute)xmlPersistenceUnit_2_0EClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitCachingType_2_0 <em>Xml Persistence Unit Caching Type 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Xml Persistence Unit Caching Type 20</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitCachingType_2_0
	 * @generated
	 */
	public EEnum getXmlPersistenceUnitCachingType_2_0()
	{
		return xmlPersistenceUnitCachingType_2_0EEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitValidationModeType_2_0 <em>Xml Persistence Unit Validation Mode Type 20</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Xml Persistence Unit Validation Mode Type 20</em>'.
	 * @see org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitValidationModeType_2_0
	 * @generated
	 */
	public EEnum getXmlPersistenceUnitValidationModeType_2_0()
	{
		return xmlPersistenceUnitValidationModeType_2_0EEnum;
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public PersistenceV2_0Factory getPersistenceV2_0Factory()
	{
		return (PersistenceV2_0Factory)getEFactoryInstance();
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
		xmlPersistenceUnit_2_0EClass = createEClass(XML_PERSISTENCE_UNIT_20);
		createEAttribute(xmlPersistenceUnit_2_0EClass, XML_PERSISTENCE_UNIT_20__SHARED_CACHE_MODE);
		createEAttribute(xmlPersistenceUnit_2_0EClass, XML_PERSISTENCE_UNIT_20__VALIDATION_MODE);

		// Create enums
		xmlPersistenceUnitCachingType_2_0EEnum = createEEnum(XML_PERSISTENCE_UNIT_CACHING_TYPE_20);
		xmlPersistenceUnitValidationModeType_2_0EEnum = createEEnum(XML_PERSISTENCE_UNIT_VALIDATION_MODE_TYPE_20);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(xmlPersistenceUnit_2_0EClass, XmlPersistenceUnit_2_0.class, "XmlPersistenceUnit_2_0", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPersistenceUnit_2_0_SharedCacheMode(), this.getXmlPersistenceUnitCachingType_2_0(), "sharedCacheMode", "UNSPECIFIED", 0, 1, XmlPersistenceUnit_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistenceUnit_2_0_ValidationMode(), this.getXmlPersistenceUnitValidationModeType_2_0(), "validationMode", "AUTO", 0, 1, XmlPersistenceUnit_2_0.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(xmlPersistenceUnitCachingType_2_0EEnum, XmlPersistenceUnitCachingType_2_0.class, "XmlPersistenceUnitCachingType_2_0");
		addEEnumLiteral(xmlPersistenceUnitCachingType_2_0EEnum, XmlPersistenceUnitCachingType_2_0.ALL);
		addEEnumLiteral(xmlPersistenceUnitCachingType_2_0EEnum, XmlPersistenceUnitCachingType_2_0.NONE);
		addEEnumLiteral(xmlPersistenceUnitCachingType_2_0EEnum, XmlPersistenceUnitCachingType_2_0.ENABLE_SELECTIVE);
		addEEnumLiteral(xmlPersistenceUnitCachingType_2_0EEnum, XmlPersistenceUnitCachingType_2_0.DISABLE_SELECTIVE);
		addEEnumLiteral(xmlPersistenceUnitCachingType_2_0EEnum, XmlPersistenceUnitCachingType_2_0.UNSPECIFIED);

		initEEnum(xmlPersistenceUnitValidationModeType_2_0EEnum, XmlPersistenceUnitValidationModeType_2_0.class, "XmlPersistenceUnitValidationModeType_2_0");
		addEEnumLiteral(xmlPersistenceUnitValidationModeType_2_0EEnum, XmlPersistenceUnitValidationModeType_2_0.AUTO);
		addEEnumLiteral(xmlPersistenceUnitValidationModeType_2_0EEnum, XmlPersistenceUnitValidationModeType_2_0.CALLBACK);
		addEEnumLiteral(xmlPersistenceUnitValidationModeType_2_0EEnum, XmlPersistenceUnitValidationModeType_2_0.NONE);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnit_2_0 <em>Xml Persistence Unit 20</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnit_2_0
		 * @see org.eclipse.jpt.core.resource.persistence.v2_0.PersistenceV2_0Package#getXmlPersistenceUnit_2_0()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE_UNIT_20 = eINSTANCE.getXmlPersistenceUnit_2_0();

		/**
		 * The meta object literal for the '<em><b>Shared Cache Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT_20__SHARED_CACHE_MODE = eINSTANCE.getXmlPersistenceUnit_2_0_SharedCacheMode();

		/**
		 * The meta object literal for the '<em><b>Validation Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT_20__VALIDATION_MODE = eINSTANCE.getXmlPersistenceUnit_2_0_ValidationMode();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitCachingType_2_0 <em>Xml Persistence Unit Caching Type 20</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitCachingType_2_0
		 * @see org.eclipse.jpt.core.resource.persistence.v2_0.PersistenceV2_0Package#getXmlPersistenceUnitCachingType_2_0()
		 * @generated
		 */
		public static final EEnum XML_PERSISTENCE_UNIT_CACHING_TYPE_20 = eINSTANCE.getXmlPersistenceUnitCachingType_2_0();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitValidationModeType_2_0 <em>Xml Persistence Unit Validation Mode Type 20</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitValidationModeType_2_0
		 * @see org.eclipse.jpt.core.resource.persistence.v2_0.PersistenceV2_0Package#getXmlPersistenceUnitValidationModeType_2_0()
		 * @generated
		 */
		public static final EEnum XML_PERSISTENCE_UNIT_VALIDATION_MODE_TYPE_20 = eINSTANCE.getXmlPersistenceUnitValidationModeType_2_0();

	}

} //PersistenceV2_0Package
