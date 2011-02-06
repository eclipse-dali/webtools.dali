/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.jpa.core.resource.xml;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage;
import org.eclipse.jpt.jpa.core.resource.persistence.v2_0.PersistenceV2_0Package;

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
 * @see org.eclipse.jpt.jpa.core.resource.xml.CommonFactory
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
	public static final CommonPackage eINSTANCE = org.eclipse.jpt.jpa.core.resource.xml.CommonPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.xml.JpaRootEObject <em>Jpa Root EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.xml.JpaRootEObject
	 * @see org.eclipse.jpt.jpa.core.resource.xml.CommonPackage#getJpaRootEObject()
	 * @generated
	 */
	public static final int JPA_ROOT_EOBJECT = 1;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JPA_ROOT_EOBJECT__VERSION = 0;

	/**
	 * The feature id for the '<em><b>Schema Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JPA_ROOT_EOBJECT__SCHEMA_LOCATION = 1;

	/**
	 * The number of structural features of the '<em>Jpa Root EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JPA_ROOT_EOBJECT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaRootEObject <em>Abstract Jpa Root EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaRootEObject
	 * @see org.eclipse.jpt.jpa.core.resource.xml.CommonPackage#getAbstractJpaRootEObject()
	 * @generated
	 */
	public static final int ABSTRACT_JPA_ROOT_EOBJECT = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JPA_ROOT_EOBJECT__VERSION = JPA_ROOT_EOBJECT__VERSION;

	/**
	 * The feature id for the '<em><b>Schema Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JPA_ROOT_EOBJECT__SCHEMA_LOCATION = JPA_ROOT_EOBJECT__SCHEMA_LOCATION;

	/**
	 * The number of structural features of the '<em>Abstract Jpa Root EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT = JPA_ROOT_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractJpaRootEObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jpaRootEObjectEClass = null;

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
	 * @see org.eclipse.jpt.jpa.core.resource.xml.CommonPackage#eNS_URI
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

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		OrmPackage theOrmPackage = (OrmPackage)(EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) : OrmPackage.eINSTANCE);
		OrmV2_0Package theOrmV2_0Package = (OrmV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(OrmV2_0Package.eNS_URI) instanceof OrmV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(OrmV2_0Package.eNS_URI) : OrmV2_0Package.eINSTANCE);
		PersistencePackage thePersistencePackage = (PersistencePackage)(EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);
		PersistenceV2_0Package thePersistenceV2_0Package = (PersistenceV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(PersistenceV2_0Package.eNS_URI) instanceof PersistenceV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(PersistenceV2_0Package.eNS_URI) : PersistenceV2_0Package.eINSTANCE);

		// Create package meta-data objects
		theCommonPackage.createPackageContents();
		theOrmPackage.createPackageContents();
		theOrmV2_0Package.createPackageContents();
		thePersistencePackage.createPackageContents();
		thePersistenceV2_0Package.createPackageContents();

		// Initialize created meta-data
		theCommonPackage.initializePackageContents();
		theOrmPackage.initializePackageContents();
		theOrmV2_0Package.initializePackageContents();
		thePersistencePackage.initializePackageContents();
		thePersistenceV2_0Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCommonPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CommonPackage.eNS_URI, theCommonPackage);
		return theCommonPackage;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaRootEObject <em>Abstract Jpa Root EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Jpa Root EObject</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaRootEObject
	 * @generated
	 */
	public EClass getAbstractJpaRootEObject()
	{
		return abstractJpaRootEObjectEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.xml.JpaRootEObject <em>Jpa Root EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Jpa Root EObject</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.xml.JpaRootEObject
	 * @generated
	 */
	public EClass getJpaRootEObject()
	{
		return jpaRootEObjectEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.xml.JpaRootEObject#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.xml.JpaRootEObject#getVersion()
	 * @see #getJpaRootEObject()
	 * @generated
	 */
	public EAttribute getJpaRootEObject_Version()
	{
		return (EAttribute)jpaRootEObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.xml.JpaRootEObject#getSchemaLocation <em>Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema Location</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.xml.JpaRootEObject#getSchemaLocation()
	 * @see #getJpaRootEObject()
	 * @generated
	 */
	public EAttribute getJpaRootEObject_SchemaLocation()
	{
		return (EAttribute)jpaRootEObjectEClass.getEStructuralFeatures().get(1);
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
		abstractJpaRootEObjectEClass = createEClass(ABSTRACT_JPA_ROOT_EOBJECT);

		jpaRootEObjectEClass = createEClass(JPA_ROOT_EOBJECT);
		createEAttribute(jpaRootEObjectEClass, JPA_ROOT_EOBJECT__VERSION);
		createEAttribute(jpaRootEObjectEClass, JPA_ROOT_EOBJECT__SCHEMA_LOCATION);
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
		abstractJpaRootEObjectEClass.getESuperTypes().add(this.getJpaRootEObject());

		// Initialize classes and features; add operations and parameters
		initEClass(abstractJpaRootEObjectEClass, AbstractJpaRootEObject.class, "AbstractJpaRootEObject", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(jpaRootEObjectEClass, JpaRootEObject.class, "JpaRootEObject", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJpaRootEObject_Version(), ecorePackage.getEString(), "version", null, 1, 1, JpaRootEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJpaRootEObject_SchemaLocation(), ecorePackage.getEString(), "schemaLocation", null, 1, 1, JpaRootEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaRootEObject <em>Abstract Jpa Root EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaRootEObject
		 * @see org.eclipse.jpt.jpa.core.resource.xml.CommonPackage#getAbstractJpaRootEObject()
		 * @generated
		 */
		public static final EClass ABSTRACT_JPA_ROOT_EOBJECT = eINSTANCE.getAbstractJpaRootEObject();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.xml.JpaRootEObject <em>Jpa Root EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.xml.JpaRootEObject
		 * @see org.eclipse.jpt.jpa.core.resource.xml.CommonPackage#getJpaRootEObject()
		 * @generated
		 */
		public static final EClass JPA_ROOT_EOBJECT = eINSTANCE.getJpaRootEObject();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JPA_ROOT_EOBJECT__VERSION = eINSTANCE.getJpaRootEObject_Version();

		/**
		 * The meta object literal for the '<em><b>Schema Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JPA_ROOT_EOBJECT__SCHEMA_LOCATION = eINSTANCE.getJpaRootEObject_SchemaLocation();

	}

} //CommonPackage
