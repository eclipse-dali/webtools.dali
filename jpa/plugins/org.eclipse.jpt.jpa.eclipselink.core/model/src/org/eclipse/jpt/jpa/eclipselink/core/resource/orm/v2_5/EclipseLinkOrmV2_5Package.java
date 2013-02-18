/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;

import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package;

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
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.EclipseLinkOrmV2_5Factory
 * @model kind="package"
 * @generated
 */
public class EclipseLinkOrmV2_5Package extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "v2_5";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.eclipselink.orm.v2_5.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLinkOrmV2_5Package eINSTANCE = org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.EclipseLinkOrmV2_5Package.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.XmlManyToOne_2_5 <em>Xml Many To One 25</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.XmlManyToOne_2_5
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.EclipseLinkOrmV2_5Package#getXmlManyToOne_2_5()
	 * @generated
	 */
	public static final int XML_MANY_TO_ONE_25 = 0;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_25__PRIMARY_KEY_JOIN_COLUMNS = OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN_CONTAINER__PRIMARY_KEY_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Primary Key Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_25__PRIMARY_KEY_FOREIGN_KEY = OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Many To One 25</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_25_FEATURE_COUNT = OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlManyToOne_2_5EClass = null;

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.EclipseLinkOrmV2_5Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EclipseLinkOrmV2_5Package()
	{
		super(eNS_URI, EclipseLinkOrmV2_5Factory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EclipseLinkOrmV2_5Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EclipseLinkOrmV2_5Package init()
	{
		if (isInited) return (EclipseLinkOrmV2_5Package)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_5Package.eNS_URI);

		// Obtain or create and register package
		EclipseLinkOrmV2_5Package theEclipseLinkOrmV2_5Package = (EclipseLinkOrmV2_5Package)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EclipseLinkOrmV2_5Package ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EclipseLinkOrmV2_5Package());

		isInited = true;

		// Initialize simple dependencies
		OrmPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		EclipseLinkOrmPackage theEclipseLinkOrmPackage = (EclipseLinkOrmPackage)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) instanceof EclipseLinkOrmPackage ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) : EclipseLinkOrmPackage.eINSTANCE);
		EclipseLinkOrmV1_1Package theEclipseLinkOrmV1_1Package = (EclipseLinkOrmV1_1Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI) instanceof EclipseLinkOrmV1_1Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI) : EclipseLinkOrmV1_1Package.eINSTANCE);
		EclipseLinkOrmV2_0Package theEclipseLinkOrmV2_0Package = (EclipseLinkOrmV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) instanceof EclipseLinkOrmV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) : EclipseLinkOrmV2_0Package.eINSTANCE);
		EclipseLinkOrmV2_1Package theEclipseLinkOrmV2_1Package = (EclipseLinkOrmV2_1Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI) instanceof EclipseLinkOrmV2_1Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI) : EclipseLinkOrmV2_1Package.eINSTANCE);
		EclipseLinkOrmV2_2Package theEclipseLinkOrmV2_2Package = (EclipseLinkOrmV2_2Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) instanceof EclipseLinkOrmV2_2Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) : EclipseLinkOrmV2_2Package.eINSTANCE);
		EclipseLinkOrmV2_3Package theEclipseLinkOrmV2_3Package = (EclipseLinkOrmV2_3Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_3Package.eNS_URI) instanceof EclipseLinkOrmV2_3Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_3Package.eNS_URI) : EclipseLinkOrmV2_3Package.eINSTANCE);
		EclipseLinkOrmV2_4Package theEclipseLinkOrmV2_4Package = (EclipseLinkOrmV2_4Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_4Package.eNS_URI) instanceof EclipseLinkOrmV2_4Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_4Package.eNS_URI) : EclipseLinkOrmV2_4Package.eINSTANCE);

		// Create package meta-data objects
		theEclipseLinkOrmV2_5Package.createPackageContents();
		theEclipseLinkOrmPackage.createPackageContents();
		theEclipseLinkOrmV1_1Package.createPackageContents();
		theEclipseLinkOrmV2_0Package.createPackageContents();
		theEclipseLinkOrmV2_1Package.createPackageContents();
		theEclipseLinkOrmV2_2Package.createPackageContents();
		theEclipseLinkOrmV2_3Package.createPackageContents();
		theEclipseLinkOrmV2_4Package.createPackageContents();

		// Initialize created meta-data
		theEclipseLinkOrmV2_5Package.initializePackageContents();
		theEclipseLinkOrmPackage.initializePackageContents();
		theEclipseLinkOrmV1_1Package.initializePackageContents();
		theEclipseLinkOrmV2_0Package.initializePackageContents();
		theEclipseLinkOrmV2_1Package.initializePackageContents();
		theEclipseLinkOrmV2_2Package.initializePackageContents();
		theEclipseLinkOrmV2_3Package.initializePackageContents();
		theEclipseLinkOrmV2_4Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEclipseLinkOrmV2_5Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EclipseLinkOrmV2_5Package.eNS_URI, theEclipseLinkOrmV2_5Package);
		return theEclipseLinkOrmV2_5Package;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.XmlManyToOne_2_5 <em>Xml Many To One 25</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To One 25</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.XmlManyToOne_2_5
	 * @generated
	 */
	public EClass getXmlManyToOne_2_5()
	{
		return xmlManyToOne_2_5EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.XmlManyToOne_2_5#getPrimaryKeyForeignKey <em>Primary Key Foreign Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Primary Key Foreign Key</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.XmlManyToOne_2_5#getPrimaryKeyForeignKey()
	 * @see #getXmlManyToOne_2_5()
	 * @generated
	 */
	public EReference getXmlManyToOne_2_5_PrimaryKeyForeignKey()
	{
		return (EReference)xmlManyToOne_2_5EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public EclipseLinkOrmV2_5Factory getEclipseLinkOrmV2_5Factory()
	{
		return (EclipseLinkOrmV2_5Factory)getEFactoryInstance();
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
		xmlManyToOne_2_5EClass = createEClass(XML_MANY_TO_ONE_25);
		createEReference(xmlManyToOne_2_5EClass, XML_MANY_TO_ONE_25__PRIMARY_KEY_FOREIGN_KEY);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		xmlManyToOne_2_5EClass.getESuperTypes().add(theOrmPackage.getXmlPrimaryKeyJoinColumnContainer());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlManyToOne_2_5EClass, XmlManyToOne_2_5.class, "XmlManyToOne_2_5", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlManyToOne_2_5_PrimaryKeyForeignKey(), theOrmPackage.getXmlForeignKey(), null, "primaryKeyForeignKey", null, 0, 1, XmlManyToOne_2_5.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.XmlManyToOne_2_5 <em>Xml Many To One 25</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.XmlManyToOne_2_5
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.EclipseLinkOrmV2_5Package#getXmlManyToOne_2_5()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_ONE_25 = eINSTANCE.getXmlManyToOne_2_5();

		/**
		 * The meta object literal for the '<em><b>Primary Key Foreign Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MANY_TO_ONE_25__PRIMARY_KEY_FOREIGN_KEY = eINSTANCE.getXmlManyToOne_2_5_PrimaryKeyForeignKey();

	}

} //EclipseLinkOrmV2_5Package
