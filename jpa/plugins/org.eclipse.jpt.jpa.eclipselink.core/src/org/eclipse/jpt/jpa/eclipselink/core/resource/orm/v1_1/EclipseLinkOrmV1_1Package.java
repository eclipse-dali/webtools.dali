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

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.xml.CommonPackage;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package;

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
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Factory
 * @model kind="package"
 * @generated
 */
public class EclipseLinkOrmV1_1Package extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "v1_1";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.eclipselink.orm.v1_1.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLinkOrmV1_1Package eINSTANCE = org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlBasic_1_1 <em>Xml Basic 11</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlBasic_1_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlBasic_1_1()
	 * @generated
	 */
	public static final int XML_BASIC_11 = 0;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_11__SEQUENCE_GENERATOR = OrmPackage.XML_GENERATOR_CONTAINER__SEQUENCE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_11__TABLE_GENERATOR = OrmPackage.XML_GENERATOR_CONTAINER__TABLE_GENERATOR;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_11__GENERATED_VALUE = OrmPackage.XML_GENERATOR_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Basic 11</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_11_FEATURE_COUNT = OrmPackage.XML_GENERATOR_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlEntity_1_1 <em>Xml Entity 11</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlEntity_1_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlEntity_1_1()
	 * @generated
	 */
	public static final int XML_ENTITY_11 = 1;

	/**
	 * The feature id for the '<em><b>Primary Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_11__PRIMARY_KEY = 0;

	/**
	 * The number of structural features of the '<em>Xml Entity 11</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_11_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlMappedSuperclass_1_1 <em>Xml Mapped Superclass 11</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlMappedSuperclass_1_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlMappedSuperclass_1_1()
	 * @generated
	 */
	public static final int XML_MAPPED_SUPERCLASS_11 = 2;

	/**
	 * The feature id for the '<em><b>Primary Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_11__PRIMARY_KEY = 0;

	/**
	 * The number of structural features of the '<em>Xml Mapped Superclass 11</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_11_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlPrimaryKey_1_1 <em>Xml Primary Key 11</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlPrimaryKey_1_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlPrimaryKey_1_1()
	 * @generated
	 */
	public static final int XML_PRIMARY_KEY_11 = 3;

	/**
	 * The feature id for the '<em><b>Validation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_11__VALIDATION = 0;

	/**
	 * The feature id for the '<em><b>Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_11__COLUMNS = 1;

	/**
	 * The number of structural features of the '<em>Xml Primary Key 11</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_11_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.IdValidationType_1_1 <em>Id Validation Type 11</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.IdValidationType_1_1
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getIdValidationType_1_1()
	 * @generated
	 */
	public static final int ID_VALIDATION_TYPE_11 = 4;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBasic_1_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntity_1_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMappedSuperclass_1_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPrimaryKey_1_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum idValidationType_1_1EEnum = null;

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EclipseLinkOrmV1_1Package()
	{
		super(eNS_URI, EclipseLinkOrmV1_1Factory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EclipseLinkOrmV1_1Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EclipseLinkOrmV1_1Package init()
	{
		if (isInited) return (EclipseLinkOrmV1_1Package)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV1_1Package.eNS_URI);

		// Obtain or create and register package
		EclipseLinkOrmV1_1Package theEclipseLinkOrmV1_1Package = (EclipseLinkOrmV1_1Package)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EclipseLinkOrmV1_1Package ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EclipseLinkOrmV1_1Package());

		isInited = true;

		// Initialize simple dependencies
		OrmPackage.eINSTANCE.eClass();
		CommonPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		EclipseLinkOrmPackage theEclipseLinkOrmPackage = (EclipseLinkOrmPackage)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) instanceof EclipseLinkOrmPackage ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI) : EclipseLinkOrmPackage.eINSTANCE);
		EclipseLinkOrmV2_0Package theEclipseLinkOrmV2_0Package = (EclipseLinkOrmV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) instanceof EclipseLinkOrmV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_0Package.eNS_URI) : EclipseLinkOrmV2_0Package.eINSTANCE);
		EclipseLinkOrmV2_1Package theEclipseLinkOrmV2_1Package = (EclipseLinkOrmV2_1Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI) instanceof EclipseLinkOrmV2_1Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_1Package.eNS_URI) : EclipseLinkOrmV2_1Package.eINSTANCE);
		EclipseLinkOrmV2_2Package theEclipseLinkOrmV2_2Package = (EclipseLinkOrmV2_2Package)(EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) instanceof EclipseLinkOrmV2_2Package ? EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmV2_2Package.eNS_URI) : EclipseLinkOrmV2_2Package.eINSTANCE);

		// Create package meta-data objects
		theEclipseLinkOrmV1_1Package.createPackageContents();
		theEclipseLinkOrmPackage.createPackageContents();
		theEclipseLinkOrmV2_0Package.createPackageContents();
		theEclipseLinkOrmV2_1Package.createPackageContents();
		theEclipseLinkOrmV2_2Package.createPackageContents();

		// Initialize created meta-data
		theEclipseLinkOrmV1_1Package.initializePackageContents();
		theEclipseLinkOrmPackage.initializePackageContents();
		theEclipseLinkOrmV2_0Package.initializePackageContents();
		theEclipseLinkOrmV2_1Package.initializePackageContents();
		theEclipseLinkOrmV2_2Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEclipseLinkOrmV1_1Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EclipseLinkOrmV1_1Package.eNS_URI, theEclipseLinkOrmV1_1Package);
		return theEclipseLinkOrmV1_1Package;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlBasic_1_1 <em>Xml Basic 11</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic 11</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlBasic_1_1
	 * @generated
	 */
	public EClass getXmlBasic_1_1()
	{
		return xmlBasic_1_1EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlBasic_1_1#getGeneratedValue <em>Generated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Generated Value</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlBasic_1_1#getGeneratedValue()
	 * @see #getXmlBasic_1_1()
	 * @generated
	 */
	public EReference getXmlBasic_1_1_GeneratedValue()
	{
		return (EReference)xmlBasic_1_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlEntity_1_1 <em>Xml Entity 11</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity 11</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlEntity_1_1
	 * @generated
	 */
	public EClass getXmlEntity_1_1()
	{
		return xmlEntity_1_1EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlEntity_1_1#getPrimaryKey <em>Primary Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Primary Key</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlEntity_1_1#getPrimaryKey()
	 * @see #getXmlEntity_1_1()
	 * @generated
	 */
	public EReference getXmlEntity_1_1_PrimaryKey()
	{
		return (EReference)xmlEntity_1_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlMappedSuperclass_1_1 <em>Xml Mapped Superclass 11</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mapped Superclass 11</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlMappedSuperclass_1_1
	 * @generated
	 */
	public EClass getXmlMappedSuperclass_1_1()
	{
		return xmlMappedSuperclass_1_1EClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlMappedSuperclass_1_1#getPrimaryKey <em>Primary Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Primary Key</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlMappedSuperclass_1_1#getPrimaryKey()
	 * @see #getXmlMappedSuperclass_1_1()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_1_1_PrimaryKey()
	{
		return (EReference)xmlMappedSuperclass_1_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlPrimaryKey_1_1 <em>Xml Primary Key 11</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Primary Key 11</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlPrimaryKey_1_1
	 * @generated
	 */
	public EClass getXmlPrimaryKey_1_1()
	{
		return xmlPrimaryKey_1_1EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlPrimaryKey_1_1#getValidation <em>Validation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Validation</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlPrimaryKey_1_1#getValidation()
	 * @see #getXmlPrimaryKey_1_1()
	 * @generated
	 */
	public EAttribute getXmlPrimaryKey_1_1_Validation()
	{
		return (EAttribute)xmlPrimaryKey_1_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlPrimaryKey_1_1#getColumns <em>Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Columns</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlPrimaryKey_1_1#getColumns()
	 * @see #getXmlPrimaryKey_1_1()
	 * @generated
	 */
	public EReference getXmlPrimaryKey_1_1_Columns()
	{
		return (EReference)xmlPrimaryKey_1_1EClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.IdValidationType_1_1 <em>Id Validation Type 11</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Id Validation Type 11</em>'.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.IdValidationType_1_1
	 * @generated
	 */
	public EEnum getIdValidationType_1_1()
	{
		return idValidationType_1_1EEnum;
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public EclipseLinkOrmV1_1Factory getEclipseLinkOrmV1_1Factory()
	{
		return (EclipseLinkOrmV1_1Factory)getEFactoryInstance();
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
		xmlBasic_1_1EClass = createEClass(XML_BASIC_11);
		createEReference(xmlBasic_1_1EClass, XML_BASIC_11__GENERATED_VALUE);

		xmlEntity_1_1EClass = createEClass(XML_ENTITY_11);
		createEReference(xmlEntity_1_1EClass, XML_ENTITY_11__PRIMARY_KEY);

		xmlMappedSuperclass_1_1EClass = createEClass(XML_MAPPED_SUPERCLASS_11);
		createEReference(xmlMappedSuperclass_1_1EClass, XML_MAPPED_SUPERCLASS_11__PRIMARY_KEY);

		xmlPrimaryKey_1_1EClass = createEClass(XML_PRIMARY_KEY_11);
		createEAttribute(xmlPrimaryKey_1_1EClass, XML_PRIMARY_KEY_11__VALIDATION);
		createEReference(xmlPrimaryKey_1_1EClass, XML_PRIMARY_KEY_11__COLUMNS);

		// Create enums
		idValidationType_1_1EEnum = createEEnum(ID_VALIDATION_TYPE_11);
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
		EclipseLinkOrmPackage theEclipseLinkOrmPackage = (EclipseLinkOrmPackage)EPackage.Registry.INSTANCE.getEPackage(EclipseLinkOrmPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		xmlBasic_1_1EClass.getESuperTypes().add(theOrmPackage.getXmlGeneratorContainer());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlBasic_1_1EClass, XmlBasic_1_1.class, "XmlBasic_1_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlBasic_1_1_GeneratedValue(), theOrmPackage.getXmlGeneratedValue(), null, "generatedValue", null, 0, 1, XmlBasic_1_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntity_1_1EClass, XmlEntity_1_1.class, "XmlEntity_1_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEntity_1_1_PrimaryKey(), theEclipseLinkOrmPackage.getXmlPrimaryKey(), null, "primaryKey", null, 0, 1, XmlEntity_1_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMappedSuperclass_1_1EClass, XmlMappedSuperclass_1_1.class, "XmlMappedSuperclass_1_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlMappedSuperclass_1_1_PrimaryKey(), theEclipseLinkOrmPackage.getXmlPrimaryKey(), null, "primaryKey", null, 0, 1, XmlMappedSuperclass_1_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPrimaryKey_1_1EClass, XmlPrimaryKey_1_1.class, "XmlPrimaryKey_1_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPrimaryKey_1_1_Validation(), this.getIdValidationType_1_1(), "validation", null, 0, 1, XmlPrimaryKey_1_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPrimaryKey_1_1_Columns(), theOrmPackage.getXmlColumn(), null, "columns", null, 0, -1, XmlPrimaryKey_1_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(idValidationType_1_1EEnum, IdValidationType_1_1.class, "IdValidationType_1_1");
		addEEnumLiteral(idValidationType_1_1EEnum, IdValidationType_1_1.NULL);
		addEEnumLiteral(idValidationType_1_1EEnum, IdValidationType_1_1.ZERO);
		addEEnumLiteral(idValidationType_1_1EEnum, IdValidationType_1_1.NONE);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlBasic_1_1 <em>Xml Basic 11</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlBasic_1_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlBasic_1_1()
		 * @generated
		 */
		public static final EClass XML_BASIC_11 = eINSTANCE.getXmlBasic_1_1();

		/**
		 * The meta object literal for the '<em><b>Generated Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_BASIC_11__GENERATED_VALUE = eINSTANCE.getXmlBasic_1_1_GeneratedValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlEntity_1_1 <em>Xml Entity 11</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlEntity_1_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlEntity_1_1()
		 * @generated
		 */
		public static final EClass XML_ENTITY_11 = eINSTANCE.getXmlEntity_1_1();

		/**
		 * The meta object literal for the '<em><b>Primary Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_11__PRIMARY_KEY = eINSTANCE.getXmlEntity_1_1_PrimaryKey();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlMappedSuperclass_1_1 <em>Xml Mapped Superclass 11</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlMappedSuperclass_1_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlMappedSuperclass_1_1()
		 * @generated
		 */
		public static final EClass XML_MAPPED_SUPERCLASS_11 = eINSTANCE.getXmlMappedSuperclass_1_1();

		/**
		 * The meta object literal for the '<em><b>Primary Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS_11__PRIMARY_KEY = eINSTANCE.getXmlMappedSuperclass_1_1_PrimaryKey();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlPrimaryKey_1_1 <em>Xml Primary Key 11</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlPrimaryKey_1_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlPrimaryKey_1_1()
		 * @generated
		 */
		public static final EClass XML_PRIMARY_KEY_11 = eINSTANCE.getXmlPrimaryKey_1_1();

		/**
		 * The meta object literal for the '<em><b>Validation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PRIMARY_KEY_11__VALIDATION = eINSTANCE.getXmlPrimaryKey_1_1_Validation();

		/**
		 * The meta object literal for the '<em><b>Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PRIMARY_KEY_11__COLUMNS = eINSTANCE.getXmlPrimaryKey_1_1_Columns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.IdValidationType_1_1 <em>Id Validation Type 11</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.IdValidationType_1_1
		 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getIdValidationType_1_1()
		 * @generated
		 */
		public static final EEnum ID_VALIDATION_TYPE_11 = eINSTANCE.getIdValidationType_1_1();

	}

} //EclipseLinkOrmV1_1Package
