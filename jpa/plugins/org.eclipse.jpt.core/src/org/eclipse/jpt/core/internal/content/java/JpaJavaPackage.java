/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.jem.java.JavaRefPackage;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.content.persistence.PersistencePackage;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

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
 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaFactory
 * @model kind="package"
 * @generated
 */
public class JpaJavaPackage extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "java";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.java.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.core.content.java";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final JpaJavaPackage eINSTANCE = org.eclipse.jpt.core.internal.content.java.JpaJavaPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.JavaEObject <em>Java EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.JavaEObject
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaEObject()
	 * @generated
	 */
	public static final int JAVA_EOBJECT = 0;

	/**
	 * The number of structural features of the '<em>Java EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_EOBJECT_FEATURE_COUNT = JpaCorePackage.JPA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit <em>Jpa Compilation Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJpaCompilationUnit()
	 * @generated
	 */
	public static final int JPA_COMPILATION_UNIT = 1;

	/**
	 * The feature id for the '<em><b>Jpa File</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JPA_COMPILATION_UNIT__JPA_FILE = JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JPA_COMPILATION_UNIT__TYPES = JAVA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Jpa Compilation Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JPA_COMPILATION_UNIT_FEATURE_COUNT = JAVA_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentType <em>Java Persistent Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.JavaPersistentType
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaPersistentType()
	 * @generated
	 */
	public static final int JAVA_PERSISTENT_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Mapping Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PERSISTENT_TYPE__MAPPING_KEY = JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PERSISTENT_TYPE__MAPPING = JAVA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PERSISTENT_TYPE__ATTRIBUTES = JAVA_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PERSISTENT_TYPE__ACCESS = JAVA_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Java Persistent Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PERSISTENT_TYPE_FEATURE_COUNT = JAVA_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute <em>Java Persistent Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaPersistentAttribute()
	 * @generated
	 */
	public static final int JAVA_PERSISTENT_ATTRIBUTE = 3;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PERSISTENT_ATTRIBUTE__MAPPING = JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PERSISTENT_ATTRIBUTE__DEFAULT_MAPPING = JAVA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Specified Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PERSISTENT_ATTRIBUTE__SPECIFIED_MAPPING = JAVA_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Java Persistent Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PERSISTENT_ATTRIBUTE_FEATURE_COUNT = JAVA_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping <em>IJava Type Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getIJavaTypeMapping()
	 * @generated
	 */
	public static final int IJAVA_TYPE_MAPPING = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJAVA_TYPE_MAPPING__NAME = JpaCorePackage.ITYPE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJAVA_TYPE_MAPPING__TABLE_NAME = JpaCorePackage.ITYPE_MAPPING__TABLE_NAME;

	/**
	 * The number of structural features of the '<em>IJava Type Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJAVA_TYPE_MAPPING_FEATURE_COUNT = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping <em>IJava Attribute Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getIJavaAttributeMapping()
	 * @generated
	 */
	public static final int IJAVA_ATTRIBUTE_MAPPING = 5;

	/**
	 * The number of structural features of the '<em>IJava Attribute Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaEObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jpaCompilationUnitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaPersistentTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaPersistentAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iJavaTypeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iJavaAttributeMappingEClass = null;

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
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private JpaJavaPackage() {
		super(eNS_URI, JpaJavaFactory.eINSTANCE);
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
	public static JpaJavaPackage init() {
		if (isInited)
			return (JpaJavaPackage) EPackage.Registry.INSTANCE.getEPackage(JpaJavaPackage.eNS_URI);
		// Obtain or create and register package
		JpaJavaPackage theJpaJavaPackage = (JpaJavaPackage) (EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof JpaJavaPackage ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new JpaJavaPackage());
		isInited = true;
		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		JavaRefPackage.eINSTANCE.eClass();
		// Obtain or create and register interdependencies
		JpaCorePackage theJpaCorePackage = (JpaCorePackage) (EPackage.Registry.INSTANCE.getEPackage(JpaCorePackage.eNS_URI) instanceof JpaCorePackage ? EPackage.Registry.INSTANCE.getEPackage(JpaCorePackage.eNS_URI) : JpaCorePackage.eINSTANCE);
		JpaCoreMappingsPackage theJpaCoreMappingsPackage = (JpaCoreMappingsPackage) (EPackage.Registry.INSTANCE.getEPackage(JpaCoreMappingsPackage.eNS_URI) instanceof JpaCoreMappingsPackage ? EPackage.Registry.INSTANCE.getEPackage(JpaCoreMappingsPackage.eNS_URI) : JpaCoreMappingsPackage.eINSTANCE);
		JpaJavaMappingsPackage theJpaJavaMappingsPackage = (JpaJavaMappingsPackage) (EPackage.Registry.INSTANCE.getEPackage(JpaJavaMappingsPackage.eNS_URI) instanceof JpaJavaMappingsPackage ? EPackage.Registry.INSTANCE.getEPackage(JpaJavaMappingsPackage.eNS_URI) : JpaJavaMappingsPackage.eINSTANCE);
		OrmPackage theOrmPackage = (OrmPackage) (EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) : OrmPackage.eINSTANCE);
		PersistencePackage thePersistencePackage = (PersistencePackage) (EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);
		// Create package meta-data objects
		theJpaJavaPackage.createPackageContents();
		theJpaCorePackage.createPackageContents();
		theJpaCoreMappingsPackage.createPackageContents();
		theJpaJavaMappingsPackage.createPackageContents();
		theOrmPackage.createPackageContents();
		thePersistencePackage.createPackageContents();
		// Initialize created meta-data
		theJpaJavaPackage.initializePackageContents();
		theJpaCorePackage.initializePackageContents();
		theJpaCoreMappingsPackage.initializePackageContents();
		theJpaJavaMappingsPackage.initializePackageContents();
		theOrmPackage.initializePackageContents();
		thePersistencePackage.initializePackageContents();
		// Mark meta-data to indicate it can't be changed
		theJpaJavaPackage.freeze();
		return theJpaJavaPackage;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.JavaEObject <em>Java EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java EObject</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.JavaEObject
	 * @generated
	 */
	public EClass getJavaEObject() {
		return javaEObjectEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit <em>Jpa Compilation Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Jpa Compilation Unit</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit
	 * @generated
	 */
	public EClass getJpaCompilationUnit() {
		return jpaCompilationUnitEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit#getTypes()
	 * @see #getJpaCompilationUnit()
	 * @generated
	 */
	public EReference getJpaCompilationUnit_Types() {
		return (EReference) jpaCompilationUnitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentType <em>Java Persistent Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Persistent Type</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.JavaPersistentType
	 * @generated
	 */
	public EClass getJavaPersistentType() {
		return javaPersistentTypeEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentType#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.JavaPersistentType#getMapping()
	 * @see #getJavaPersistentType()
	 * @generated
	 */
	public EReference getJavaPersistentType_Mapping() {
		return (EReference) javaPersistentTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentType#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.JavaPersistentType#getAttributes()
	 * @see #getJavaPersistentType()
	 * @generated
	 */
	public EReference getJavaPersistentType_Attributes() {
		return (EReference) javaPersistentTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentType#getAccess <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.JavaPersistentType#getAccess()
	 * @see #getJavaPersistentType()
	 * @generated
	 */
	public EAttribute getJavaPersistentType_Access() {
		return (EAttribute) javaPersistentTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute <em>Java Persistent Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Persistent Attribute</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute
	 * @generated
	 */
	public EClass getJavaPersistentAttribute() {
		return javaPersistentAttributeEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute#getDefaultMapping <em>Default Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Default Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute#getDefaultMapping()
	 * @see #getJavaPersistentAttribute()
	 * @generated
	 */
	public EReference getJavaPersistentAttribute_DefaultMapping() {
		return (EReference) javaPersistentAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute#getSpecifiedMapping <em>Specified Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Specified Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute#getSpecifiedMapping()
	 * @see #getJavaPersistentAttribute()
	 * @generated
	 */
	public EReference getJavaPersistentAttribute_SpecifiedMapping() {
		return (EReference) javaPersistentAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping <em>IJava Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IJava Type Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping
	 * @generated
	 */
	public EClass getIJavaTypeMapping() {
		return iJavaTypeMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping <em>IJava Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IJava Attribute Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping
	 * @generated
	 */
	public EClass getIJavaAttributeMapping() {
		return iJavaAttributeMappingEClass;
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public JpaJavaFactory getJpaJavaFactory() {
		return (JpaJavaFactory) getEFactoryInstance();
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
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;
		// Create classes and their features
		javaEObjectEClass = createEClass(JAVA_EOBJECT);
		jpaCompilationUnitEClass = createEClass(JPA_COMPILATION_UNIT);
		createEReference(jpaCompilationUnitEClass, JPA_COMPILATION_UNIT__TYPES);
		javaPersistentTypeEClass = createEClass(JAVA_PERSISTENT_TYPE);
		createEReference(javaPersistentTypeEClass, JAVA_PERSISTENT_TYPE__MAPPING);
		createEReference(javaPersistentTypeEClass, JAVA_PERSISTENT_TYPE__ATTRIBUTES);
		createEAttribute(javaPersistentTypeEClass, JAVA_PERSISTENT_TYPE__ACCESS);
		javaPersistentAttributeEClass = createEClass(JAVA_PERSISTENT_ATTRIBUTE);
		createEReference(javaPersistentAttributeEClass, JAVA_PERSISTENT_ATTRIBUTE__DEFAULT_MAPPING);
		createEReference(javaPersistentAttributeEClass, JAVA_PERSISTENT_ATTRIBUTE__SPECIFIED_MAPPING);
		iJavaTypeMappingEClass = createEClass(IJAVA_TYPE_MAPPING);
		iJavaAttributeMappingEClass = createEClass(IJAVA_ATTRIBUTE_MAPPING);
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
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;
		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);
		// Obtain other dependent packages
		JpaJavaMappingsPackage theJpaJavaMappingsPackage = (JpaJavaMappingsPackage) EPackage.Registry.INSTANCE.getEPackage(JpaJavaMappingsPackage.eNS_URI);
		JpaCorePackage theJpaCorePackage = (JpaCorePackage) EPackage.Registry.INSTANCE.getEPackage(JpaCorePackage.eNS_URI);
		// Add subpackages
		getESubpackages().add(theJpaJavaMappingsPackage);
		// Create type parameters
		// Set bounds for type parameters
		// Add supertypes to classes
		javaEObjectEClass.getESuperTypes().add(theJpaCorePackage.getJpaEObject());
		javaEObjectEClass.getESuperTypes().add(theJpaCorePackage.getIJpaSourceObject());
		jpaCompilationUnitEClass.getESuperTypes().add(this.getJavaEObject());
		jpaCompilationUnitEClass.getESuperTypes().add(theJpaCorePackage.getIJpaRootContentNode());
		javaPersistentTypeEClass.getESuperTypes().add(this.getJavaEObject());
		javaPersistentTypeEClass.getESuperTypes().add(theJpaCorePackage.getIPersistentType());
		javaPersistentAttributeEClass.getESuperTypes().add(this.getJavaEObject());
		javaPersistentAttributeEClass.getESuperTypes().add(theJpaCorePackage.getIPersistentAttribute());
		iJavaTypeMappingEClass.getESuperTypes().add(theJpaCorePackage.getITypeMapping());
		iJavaAttributeMappingEClass.getESuperTypes().add(theJpaCorePackage.getIAttributeMapping());
		// Initialize classes and features; add operations and parameters
		initEClass(javaEObjectEClass, JavaEObject.class, "JavaEObject", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		addEOperation(javaEObjectEClass, theJpaCorePackage.getIJpaFile(), "getJpaFile", 0, 1);
		addEOperation(javaEObjectEClass, theJpaCorePackage.getIJpaRootContentNode(), "getRoot", 0, 1);
		initEClass(jpaCompilationUnitEClass, JpaCompilationUnit.class, "JpaCompilationUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJpaCompilationUnit_Types(), this.getJavaPersistentType(), null, "types", null, 0, -1, JpaCompilationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(javaPersistentTypeEClass, JavaPersistentType.class, "JavaPersistentType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJavaPersistentType_Mapping(), this.getIJavaTypeMapping(), null, "mapping", "", 1, 1, JavaPersistentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJavaPersistentType_Attributes(), this.getJavaPersistentAttribute(), null, "attributes", null, 0, -1, JavaPersistentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJavaPersistentType_Access(), theJpaCorePackage.getAccessType(), "access", "", 0, 1, JavaPersistentType.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(javaPersistentAttributeEClass, JavaPersistentAttribute.class, "JavaPersistentAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJavaPersistentAttribute_DefaultMapping(), this.getIJavaAttributeMapping(), null, "defaultMapping", null, 1, 1, JavaPersistentAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJavaPersistentAttribute_SpecifiedMapping(), this.getIJavaAttributeMapping(), null, "specifiedMapping", null, 1, 1, JavaPersistentAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iJavaTypeMappingEClass, IJavaTypeMapping.class, "IJavaTypeMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		addEOperation(iJavaTypeMappingEClass, null, "initialize");
		initEClass(iJavaAttributeMappingEClass, IJavaAttributeMapping.class, "IJavaAttributeMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		addEOperation(iJavaAttributeMappingEClass, null, "initialize");
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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.JavaEObject <em>Java EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.JavaEObject
		 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaEObject()
		 * @generated
		 */
		public static final EClass JAVA_EOBJECT = eINSTANCE.getJavaEObject();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit <em>Jpa Compilation Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit
		 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJpaCompilationUnit()
		 * @generated
		 */
		public static final EClass JPA_COMPILATION_UNIT = eINSTANCE.getJpaCompilationUnit();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference JPA_COMPILATION_UNIT__TYPES = eINSTANCE.getJpaCompilationUnit_Types();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentType <em>Java Persistent Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.JavaPersistentType
		 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaPersistentType()
		 * @generated
		 */
		public static final EClass JAVA_PERSISTENT_TYPE = eINSTANCE.getJavaPersistentType();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference JAVA_PERSISTENT_TYPE__MAPPING = eINSTANCE.getJavaPersistentType_Mapping();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference JAVA_PERSISTENT_TYPE__ATTRIBUTES = eINSTANCE.getJavaPersistentType_Attributes();

		/**
		 * The meta object literal for the '<em><b>Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JAVA_PERSISTENT_TYPE__ACCESS = eINSTANCE.getJavaPersistentType_Access();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute <em>Java Persistent Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute
		 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaPersistentAttribute()
		 * @generated
		 */
		public static final EClass JAVA_PERSISTENT_ATTRIBUTE = eINSTANCE.getJavaPersistentAttribute();

		/**
		 * The meta object literal for the '<em><b>Default Mapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference JAVA_PERSISTENT_ATTRIBUTE__DEFAULT_MAPPING = eINSTANCE.getJavaPersistentAttribute_DefaultMapping();

		/**
		 * The meta object literal for the '<em><b>Specified Mapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference JAVA_PERSISTENT_ATTRIBUTE__SPECIFIED_MAPPING = eINSTANCE.getJavaPersistentAttribute_SpecifiedMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping <em>IJava Type Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping
		 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getIJavaTypeMapping()
		 * @generated
		 */
		public static final EClass IJAVA_TYPE_MAPPING = eINSTANCE.getIJavaTypeMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping <em>IJava Attribute Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping
		 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getIJavaAttributeMapping()
		 * @generated
		 */
		public static final EClass IJAVA_ATTRIBUTE_MAPPING = eINSTANCE.getIJavaAttributeMapping();
	}
} //JpaJavaPackage
