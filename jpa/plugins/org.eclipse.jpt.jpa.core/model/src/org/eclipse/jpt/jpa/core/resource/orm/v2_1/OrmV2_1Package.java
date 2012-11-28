/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm.v2_1;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.jpt.common.core.resource.xml.CommonPackage;

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
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Factory
 * @model kind="package"
 * @generated
 */
public class OrmV2_1Package extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "v2_1";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.orm.v2_1.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.jpa.core.resource.orm.v2_1";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final OrmV2_1Package eINSTANCE = org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1 <em>Xml Converter 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConverter_2_1()
	 * @generated
	 */
	public static final int XML_CONVERTER_21 = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_21__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_21__CLASS_NAME = 1;

	/**
	 * The feature id for the '<em><b>Auto Apply</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_21__AUTO_APPLY = 2;

	/**
	 * The number of structural features of the '<em>Xml Converter 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_21_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1 <em>Xml Converter Container 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConverterContainer_2_1()
	 * @generated
	 */
	public static final int XML_CONVERTER_CONTAINER_21 = 2;

	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_CONTAINER_21__CONVERTERS = 0;

	/**
	 * The number of structural features of the '<em>Xml Converter Container 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTER_CONTAINER_21_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntityMappings_2_1 <em>Xml Entity Mappings 21</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntityMappings_2_1
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlEntityMappings_2_1()
	 * @generated
	 */
	public static final int XML_ENTITY_MAPPINGS_21 = 1;

	/**
	 * The feature id for the '<em><b>Converters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_21__CONVERTERS = XML_CONVERTER_CONTAINER_21__CONVERTERS;

	/**
	 * The number of structural features of the '<em>Xml Entity Mappings 21</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_21_FEATURE_COUNT = XML_CONVERTER_CONTAINER_21_FEATURE_COUNT + 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlConverter_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntityMappings_2_1EClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlConverterContainer_2_1EClass = null;

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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private OrmV2_1Package()
	{
		super(eNS_URI, OrmV2_1Factory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link OrmV2_1Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static OrmV2_1Package init()
	{
		if (isInited) return (OrmV2_1Package)EPackage.Registry.INSTANCE.getEPackage(OrmV2_1Package.eNS_URI);

		// Obtain or create and register package
		OrmV2_1Package theOrmV2_1Package = (OrmV2_1Package)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof OrmV2_1Package ? EPackage.Registry.INSTANCE.get(eNS_URI) : new OrmV2_1Package());

		isInited = true;

		// Initialize simple dependencies
		CommonPackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		OrmPackage theOrmPackage = (OrmPackage)(EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) : OrmPackage.eINSTANCE);
		OrmV2_0Package theOrmV2_0Package = (OrmV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(OrmV2_0Package.eNS_URI) instanceof OrmV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(OrmV2_0Package.eNS_URI) : OrmV2_0Package.eINSTANCE);
		PersistencePackage thePersistencePackage = (PersistencePackage)(EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);
		PersistenceV2_0Package thePersistenceV2_0Package = (PersistenceV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(PersistenceV2_0Package.eNS_URI) instanceof PersistenceV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(PersistenceV2_0Package.eNS_URI) : PersistenceV2_0Package.eINSTANCE);

		// Create package meta-data objects
		theOrmV2_1Package.createPackageContents();
		theOrmPackage.createPackageContents();
		theOrmV2_0Package.createPackageContents();
		thePersistencePackage.createPackageContents();
		thePersistenceV2_0Package.createPackageContents();

		// Initialize created meta-data
		theOrmV2_1Package.initializePackageContents();
		theOrmPackage.initializePackageContents();
		theOrmV2_0Package.initializePackageContents();
		thePersistencePackage.initializePackageContents();
		thePersistenceV2_0Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theOrmV2_1Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(OrmV2_1Package.eNS_URI, theOrmV2_1Package);
		return theOrmV2_1Package;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1 <em>Xml Converter 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Converter 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1
	 * @generated
	 */
	public EClass getXmlConverter_2_1()
	{
		return xmlConverter_2_1EClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1#getDescription()
	 * @see #getXmlConverter_2_1()
	 * @generated
	 */
	public EAttribute getXmlConverter_2_1_Description()
	{
		return (EAttribute)xmlConverter_2_1EClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1#getClassName()
	 * @see #getXmlConverter_2_1()
	 * @generated
	 */
	public EAttribute getXmlConverter_2_1_ClassName()
	{
		return (EAttribute)xmlConverter_2_1EClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1#getAutoApply <em>Auto Apply</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Apply</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1#getAutoApply()
	 * @see #getXmlConverter_2_1()
	 * @generated
	 */
	public EAttribute getXmlConverter_2_1_AutoApply()
	{
		return (EAttribute)xmlConverter_2_1EClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntityMappings_2_1 <em>Xml Entity Mappings 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity Mappings 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntityMappings_2_1
	 * @generated
	 */
	public EClass getXmlEntityMappings_2_1()
	{
		return xmlEntityMappings_2_1EClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1 <em>Xml Converter Container 21</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Converter Container 21</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1
	 * @generated
	 */
	public EClass getXmlConverterContainer_2_1()
	{
		return xmlConverterContainer_2_1EClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1#getConverters <em>Converters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Converters</em>'.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1#getConverters()
	 * @see #getXmlConverterContainer_2_1()
	 * @generated
	 */
	public EReference getXmlConverterContainer_2_1_Converters()
	{
		return (EReference)xmlConverterContainer_2_1EClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public OrmV2_1Factory getOrmV2_1Factory()
	{
		return (OrmV2_1Factory)getEFactoryInstance();
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
		xmlConverter_2_1EClass = createEClass(XML_CONVERTER_21);
		createEAttribute(xmlConverter_2_1EClass, XML_CONVERTER_21__DESCRIPTION);
		createEAttribute(xmlConverter_2_1EClass, XML_CONVERTER_21__CLASS_NAME);
		createEAttribute(xmlConverter_2_1EClass, XML_CONVERTER_21__AUTO_APPLY);

		xmlEntityMappings_2_1EClass = createEClass(XML_ENTITY_MAPPINGS_21);

		xmlConverterContainer_2_1EClass = createEClass(XML_CONVERTER_CONTAINER_21);
		createEReference(xmlConverterContainer_2_1EClass, XML_CONVERTER_CONTAINER_21__CONVERTERS);
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
		xmlEntityMappings_2_1EClass.getESuperTypes().add(this.getXmlConverterContainer_2_1());

		// Initialize classes and features; add operations and parameters
		initEClass(xmlConverter_2_1EClass, XmlConverter_2_1.class, "XmlConverter_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlConverter_2_1_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlConverter_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlConverter_2_1_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, XmlConverter_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlConverter_2_1_AutoApply(), theXMLTypePackage.getBooleanObject(), "autoApply", null, 0, 1, XmlConverter_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntityMappings_2_1EClass, XmlEntityMappings_2_1.class, "XmlEntityMappings_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlConverterContainer_2_1EClass, XmlConverterContainer_2_1.class, "XmlConverterContainer_2_1", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlConverterContainer_2_1_Converters(), this.getXmlConverter_2_1(), null, "converters", null, 0, -1, XmlConverterContainer_2_1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1 <em>Xml Converter 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConverter_2_1()
		 * @generated
		 */
		public static final EClass XML_CONVERTER_21 = eINSTANCE.getXmlConverter_2_1();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERTER_21__DESCRIPTION = eINSTANCE.getXmlConverter_2_1_Description();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERTER_21__CLASS_NAME = eINSTANCE.getXmlConverter_2_1_ClassName();

		/**
		 * The meta object literal for the '<em><b>Auto Apply</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERTER_21__AUTO_APPLY = eINSTANCE.getXmlConverter_2_1_AutoApply();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntityMappings_2_1 <em>Xml Entity Mappings 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntityMappings_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlEntityMappings_2_1()
		 * @generated
		 */
		public static final EClass XML_ENTITY_MAPPINGS_21 = eINSTANCE.getXmlEntityMappings_2_1();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1 <em>Xml Converter Container 21</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverterContainer_2_1
		 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConverterContainer_2_1()
		 * @generated
		 */
		public static final EClass XML_CONVERTER_CONTAINER_21 = eINSTANCE.getXmlConverterContainer_2_1();

		/**
		 * The meta object literal for the '<em><b>Converters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_CONVERTER_CONTAINER_21__CONVERTERS = eINSTANCE.getXmlConverterContainer_2_1_Converters();

	}

} //OrmV2_1Package
