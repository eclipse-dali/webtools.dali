/*******************************************************************************
 *  Copyright (c) 2009, 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.common.core.resource.xml;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

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
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.common.core.resource.xml.CommonFactory
 * @model kind="package"
 * @generated
 */
public class CommonPackage extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "xml";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.common.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.jpa.core.resource.xml";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final CommonPackage eINSTANCE = org.eclipse.jpt.common.core.resource.xml.CommonPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.common.core.resource.xml.ERootObject <em>ERoot Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.common.core.resource.xml.ERootObject
	 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#getERootObject()
	 * @generated
	 */
	public static final int EROOT_OBJECT = 1;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EROOT_OBJECT__VERSION = 0;

	/**
	 * The feature id for the '<em><b>Schema Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EROOT_OBJECT__SCHEMA_LOCATION = 1;

	/**
	 * The feature id for the '<em><b>Implied Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EROOT_OBJECT__IMPLIED_VERSION = 2;

	/**
	 * The number of structural features of the '<em>ERoot Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EROOT_OBJECT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.common.core.resource.xml.ERootObjectImpl <em>ERoot Object Impl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.common.core.resource.xml.ERootObjectImpl
	 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#getERootObjectImpl()
	 * @generated
	 */
	public static final int EROOT_OBJECT_IMPL = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EROOT_OBJECT_IMPL__VERSION = EROOT_OBJECT__VERSION;

	/**
	 * The feature id for the '<em><b>Schema Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EROOT_OBJECT_IMPL__SCHEMA_LOCATION = EROOT_OBJECT__SCHEMA_LOCATION;

	/**
	 * The feature id for the '<em><b>Implied Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EROOT_OBJECT_IMPL__IMPLIED_VERSION = EROOT_OBJECT__IMPLIED_VERSION;

	/**
	 * The number of structural features of the '<em>ERoot Object Impl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EROOT_OBJECT_IMPL_FEATURE_COUNT = EROOT_OBJECT_FEATURE_COUNT + 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eRootObjectImplEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eRootObjectEClass = null;

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
	 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CommonPackage()
	{
		super(eNS_URI, CommonFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CommonPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CommonPackage init()
	{
		if (isInited) return (CommonPackage)EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI);

		// Obtain or create and register package
		CommonPackage theCommonPackage = (CommonPackage)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CommonPackage ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CommonPackage());

		isInited = true;

		// Create package meta-data objects
		theCommonPackage.createPackageContents();

		// Initialize created meta-data
		theCommonPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCommonPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CommonPackage.eNS_URI, theCommonPackage);
		return theCommonPackage;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.common.core.resource.xml.ERootObjectImpl <em>ERoot Object Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ERoot Object Impl</em>'.
	 * @see org.eclipse.jpt.common.core.resource.xml.ERootObjectImpl
	 * @generated
	 */
	public EClass getERootObjectImpl()
	{
		return eRootObjectImplEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.common.core.resource.xml.ERootObject <em>ERoot Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ERoot Object</em>'.
	 * @see org.eclipse.jpt.common.core.resource.xml.ERootObject
	 * @generated
	 */
	public EClass getERootObject()
	{
		return eRootObjectEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.common.core.resource.xml.ERootObject#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.jpt.common.core.resource.xml.ERootObject#getVersion()
	 * @see #getERootObject()
	 * @generated
	 */
	public EAttribute getERootObject_Version()
	{
		return (EAttribute)eRootObjectEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.common.core.resource.xml.ERootObject#getSchemaLocation <em>Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema Location</em>'.
	 * @see org.eclipse.jpt.common.core.resource.xml.ERootObject#getSchemaLocation()
	 * @see #getERootObject()
	 * @generated
	 */
	public EAttribute getERootObject_SchemaLocation()
	{
		return (EAttribute)eRootObjectEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.common.core.resource.xml.ERootObject#getImpliedVersion <em>Implied Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implied Version</em>'.
	 * @see org.eclipse.jpt.common.core.resource.xml.ERootObject#getImpliedVersion()
	 * @see #getERootObject()
	 * @generated
	 */
	public EAttribute getERootObject_ImpliedVersion()
	{
		return (EAttribute)eRootObjectEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public CommonFactory getCommonFactory()
	{
		return (CommonFactory)getEFactoryInstance();
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
		eRootObjectImplEClass = createEClass(EROOT_OBJECT_IMPL);

		eRootObjectEClass = createEClass(EROOT_OBJECT);
		createEAttribute(eRootObjectEClass, EROOT_OBJECT__VERSION);
		createEAttribute(eRootObjectEClass, EROOT_OBJECT__SCHEMA_LOCATION);
		createEAttribute(eRootObjectEClass, EROOT_OBJECT__IMPLIED_VERSION);
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
		eRootObjectImplEClass.getESuperTypes().add(this.getERootObject());

		// Initialize classes and features; add operations and parameters
		initEClass(eRootObjectImplEClass, ERootObjectImpl.class, "ERootObjectImpl", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(eRootObjectEClass, ERootObject.class, "ERootObject", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getERootObject_Version(), ecorePackage.getEString(), "version", null, 0, 1, ERootObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getERootObject_SchemaLocation(), ecorePackage.getEString(), "schemaLocation", null, 1, 1, ERootObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getERootObject_ImpliedVersion(), ecorePackage.getEString(), "impliedVersion", null, 0, 1, ERootObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		 * The meta object literal for the '{@link org.eclipse.jpt.common.core.resource.xml.ERootObjectImpl <em>ERoot Object Impl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.common.core.resource.xml.ERootObjectImpl
		 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#getERootObjectImpl()
		 * @generated
		 */
		public static final EClass EROOT_OBJECT_IMPL = eINSTANCE.getERootObjectImpl();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.common.core.resource.xml.ERootObject <em>ERoot Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.common.core.resource.xml.ERootObject
		 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#getERootObject()
		 * @generated
		 */
		public static final EClass EROOT_OBJECT = eINSTANCE.getERootObject();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EROOT_OBJECT__VERSION = eINSTANCE.getERootObject_Version();

		/**
		 * The meta object literal for the '<em><b>Schema Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EROOT_OBJECT__SCHEMA_LOCATION = eINSTANCE.getERootObject_SchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Implied Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EROOT_OBJECT__IMPLIED_VERSION = eINSTANCE.getERootObject_ImpliedVersion();

	}

} //CommonPackage
