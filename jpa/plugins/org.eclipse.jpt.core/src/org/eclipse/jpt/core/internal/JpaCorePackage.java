/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.jpt.core.internal.content.java.JpaJavaPackage;
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
 * @see org.eclipse.jpt.core.internal.JpaCoreFactory
 * @model kind="package"
 * @generated
 */
public class JpaCorePackage extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "internal";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.core.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "jpt.core";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final JpaCorePackage eINSTANCE = org.eclipse.jpt.core.internal.JpaCorePackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.IJpaEObject <em>IJpa EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.IJpaEObject
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaEObject()
	 * @generated
	 */
	public static final int IJPA_EOBJECT = 0;

	/**
	 * The number of structural features of the '<em>IJpa EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJPA_EOBJECT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.JpaEObject <em>Jpa EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.JpaEObject
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaEObject()
	 * @generated
	 */
	public static final int JPA_EOBJECT = 1;

	/**
	 * The number of structural features of the '<em>Jpa EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JPA_EOBJECT_FEATURE_COUNT = IJPA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.IJpaDataSource <em>IJpa Data Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.IJpaDataSource
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaDataSource()
	 * @generated
	 */
	public static final int IJPA_DATA_SOURCE = 2;

	/**
	 * The number of structural features of the '<em>IJpa Data Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJPA_DATA_SOURCE_FEATURE_COUNT = IJPA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.JpaDataSource <em>Jpa Data Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.JpaDataSource
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaDataSource()
	 * @generated
	 */
	public static final int JPA_DATA_SOURCE = 3;

	/**
	 * The feature id for the '<em><b>Connection Profile Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JPA_DATA_SOURCE__CONNECTION_PROFILE_NAME = JPA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Jpa Data Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JPA_DATA_SOURCE_FEATURE_COUNT = JPA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.IJpaFile <em>IJpa File</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.IJpaFile
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaFile()
	 * @generated
	 */
	public static final int IJPA_FILE = 4;

	/**
	 * The number of structural features of the '<em>IJpa File</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJPA_FILE_FEATURE_COUNT = IJPA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.JpaFile <em>Jpa File</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.JpaFile
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaFile()
	 * @generated
	 */
	public static final int JPA_FILE = 5;

	/**
	 * The feature id for the '<em><b>Content Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JPA_FILE__CONTENT_ID = JPA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JPA_FILE__CONTENT = JPA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Jpa File</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JPA_FILE_FEATURE_COUNT = JPA_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.IJpaSourceObject <em>IJpa Source Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.IJpaSourceObject
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaSourceObject()
	 * @generated
	 */
	public static final int IJPA_SOURCE_OBJECT = 6;

	/**
	 * The number of structural features of the '<em>IJpa Source Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJPA_SOURCE_OBJECT_FEATURE_COUNT = IJPA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.IXmlEObject <em>IXml EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.IXmlEObject
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIXmlEObject()
	 * @generated
	 */
	public static final int IXML_EOBJECT = 7;

	/**
	 * The number of structural features of the '<em>IXml EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IXML_EOBJECT_FEATURE_COUNT = IJPA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.XmlEObject <em>Xml EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.XmlEObject
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getXmlEObject()
	 * @generated
	 */
	public static final int XML_EOBJECT = 8;

	/**
	 * The number of structural features of the '<em>Xml EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EOBJECT_FEATURE_COUNT = JPA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.IJpaContentNode <em>IJpa Content Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.IJpaContentNode
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaContentNode()
	 * @generated
	 */
	public static final int IJPA_CONTENT_NODE = 9;

	/**
	 * The number of structural features of the '<em>IJpa Content Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJPA_CONTENT_NODE_FEATURE_COUNT = IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.IJpaRootContentNode <em>IJpa Root Content Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.IJpaRootContentNode
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaRootContentNode()
	 * @generated
	 */
	public static final int IJPA_ROOT_CONTENT_NODE = 10;

	/**
	 * The feature id for the '<em><b>Jpa File</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJPA_ROOT_CONTENT_NODE__JPA_FILE = IJPA_CONTENT_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IJpa Root Content Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJPA_ROOT_CONTENT_NODE_FEATURE_COUNT = IJPA_CONTENT_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.IPersistentType <em>IPersistent Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.IPersistentType
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIPersistentType()
	 * @generated
	 */
	public static final int IPERSISTENT_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Mapping Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IPERSISTENT_TYPE__MAPPING_KEY = IJPA_CONTENT_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IPersistent Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IPERSISTENT_TYPE_FEATURE_COUNT = IJPA_CONTENT_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.ITypeMapping <em>IType Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.ITypeMapping
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getITypeMapping()
	 * @generated
	 */
	public static final int ITYPE_MAPPING = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITYPE_MAPPING__NAME = IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITYPE_MAPPING__TABLE_NAME = IJPA_SOURCE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IType Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITYPE_MAPPING_FEATURE_COUNT = IJPA_SOURCE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.NullTypeMapping <em>Null Type Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.NullTypeMapping
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getNullTypeMapping()
	 * @generated
	 */
	public static final int NULL_TYPE_MAPPING = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NULL_TYPE_MAPPING__NAME = JPA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NULL_TYPE_MAPPING__TABLE_NAME = JPA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Null Type Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int NULL_TYPE_MAPPING_FEATURE_COUNT = JPA_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.IPersistentAttribute <em>IPersistent Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.IPersistentAttribute
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIPersistentAttribute()
	 * @generated
	 */
	public static final int IPERSISTENT_ATTRIBUTE = 14;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IPERSISTENT_ATTRIBUTE__MAPPING = IJPA_CONTENT_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IPersistent Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IPERSISTENT_ATTRIBUTE_FEATURE_COUNT = IJPA_CONTENT_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.IAttributeMapping <em>IAttribute Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.IAttributeMapping
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIAttributeMapping()
	 * @generated
	 */
	public static final int IATTRIBUTE_MAPPING = 15;

	/**
	 * The number of structural features of the '<em>IAttribute Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IATTRIBUTE_MAPPING_FEATURE_COUNT = IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.AccessType <em>Access Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.AccessType
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getAccessType()
	 * @generated
	 */
	public static final int ACCESS_TYPE = 16;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iJpaEObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jpaEObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iJpaDataSourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jpaDataSourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iJpaFileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jpaFileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iJpaSourceObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iXmlEObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iJpaContentNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iJpaRootContentNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iPersistentTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iTypeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nullTypeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iPersistentAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iAttributeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum accessTypeEEnum = null;

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
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private JpaCorePackage() {
		super(eNS_URI, JpaCoreFactory.eINSTANCE);
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
	public static JpaCorePackage init() {
		if (isInited)
			return (JpaCorePackage) EPackage.Registry.INSTANCE.getEPackage(JpaCorePackage.eNS_URI);
		// Obtain or create and register package
		JpaCorePackage theJpaCorePackage = (JpaCorePackage) (EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof JpaCorePackage ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new JpaCorePackage());
		isInited = true;
		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		// Obtain or create and register interdependencies
		JpaCoreMappingsPackage theJpaCoreMappingsPackage = (JpaCoreMappingsPackage) (EPackage.Registry.INSTANCE.getEPackage(JpaCoreMappingsPackage.eNS_URI) instanceof JpaCoreMappingsPackage ? EPackage.Registry.INSTANCE.getEPackage(JpaCoreMappingsPackage.eNS_URI) : JpaCoreMappingsPackage.eINSTANCE);
		JpaJavaPackage theJpaJavaPackage = (JpaJavaPackage) (EPackage.Registry.INSTANCE.getEPackage(JpaJavaPackage.eNS_URI) instanceof JpaJavaPackage ? EPackage.Registry.INSTANCE.getEPackage(JpaJavaPackage.eNS_URI) : JpaJavaPackage.eINSTANCE);
		JpaJavaMappingsPackage theJpaJavaMappingsPackage = (JpaJavaMappingsPackage) (EPackage.Registry.INSTANCE.getEPackage(JpaJavaMappingsPackage.eNS_URI) instanceof JpaJavaMappingsPackage ? EPackage.Registry.INSTANCE.getEPackage(JpaJavaMappingsPackage.eNS_URI) : JpaJavaMappingsPackage.eINSTANCE);
		OrmPackage theOrmPackage = (OrmPackage) (EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) : OrmPackage.eINSTANCE);
		PersistencePackage thePersistencePackage = (PersistencePackage) (EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);
		// Create package meta-data objects
		theJpaCorePackage.createPackageContents();
		theJpaCoreMappingsPackage.createPackageContents();
		theJpaJavaPackage.createPackageContents();
		theJpaJavaMappingsPackage.createPackageContents();
		theOrmPackage.createPackageContents();
		thePersistencePackage.createPackageContents();
		// Initialize created meta-data
		theJpaCorePackage.initializePackageContents();
		theJpaCoreMappingsPackage.initializePackageContents();
		theJpaJavaPackage.initializePackageContents();
		theJpaJavaMappingsPackage.initializePackageContents();
		theOrmPackage.initializePackageContents();
		thePersistencePackage.initializePackageContents();
		// Mark meta-data to indicate it can't be changed
		theJpaCorePackage.freeze();
		return theJpaCorePackage;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.IJpaEObject <em>IJpa EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IJpa EObject</em>'.
	 * @see org.eclipse.jpt.core.internal.IJpaEObject
	 * @generated
	 */
	public EClass getIJpaEObject() {
		return iJpaEObjectEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.JpaEObject <em>Jpa EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Jpa EObject</em>'.
	 * @see org.eclipse.jpt.core.internal.JpaEObject
	 * @generated
	 */
	public EClass getJpaEObject() {
		return jpaEObjectEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.IJpaDataSource <em>IJpa Data Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IJpa Data Source</em>'.
	 * @see org.eclipse.jpt.core.internal.IJpaDataSource
	 * @generated
	 */
	public EClass getIJpaDataSource() {
		return iJpaDataSourceEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.JpaDataSource <em>Jpa Data Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Jpa Data Source</em>'.
	 * @see org.eclipse.jpt.core.internal.JpaDataSource
	 * @generated
	 */
	public EClass getJpaDataSource() {
		return jpaDataSourceEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.JpaDataSource#getConnectionProfileName <em>Connection Profile Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Connection Profile Name</em>'.
	 * @see org.eclipse.jpt.core.internal.JpaDataSource#getConnectionProfileName()
	 * @see #getJpaDataSource()
	 * @generated
	 */
	public EAttribute getJpaDataSource_ConnectionProfileName() {
		return (EAttribute) jpaDataSourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.IJpaFile <em>IJpa File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IJpa File</em>'.
	 * @see org.eclipse.jpt.core.internal.IJpaFile
	 * @generated
	 */
	public EClass getIJpaFile() {
		return iJpaFileEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.JpaFile <em>Jpa File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Jpa File</em>'.
	 * @see org.eclipse.jpt.core.internal.JpaFile
	 * @generated
	 */
	public EClass getJpaFile() {
		return jpaFileEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.JpaFile#getContentId <em>Content Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content Id</em>'.
	 * @see org.eclipse.jpt.core.internal.JpaFile#getContentId()
	 * @see #getJpaFile()
	 * @generated
	 */
	public EAttribute getJpaFile_ContentId() {
		return (EAttribute) jpaFileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.JpaFile#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Content</em>'.
	 * @see org.eclipse.jpt.core.internal.JpaFile#getContent()
	 * @see #getJpaFile()
	 * @generated
	 */
	public EReference getJpaFile_Content() {
		return (EReference) jpaFileEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.IJpaSourceObject <em>IJpa Source Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IJpa Source Object</em>'.
	 * @see org.eclipse.jpt.core.internal.IJpaSourceObject
	 * @generated
	 */
	public EClass getIJpaSourceObject() {
		return iJpaSourceObjectEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.IXmlEObject <em>IXml EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IXml EObject</em>'.
	 * @see org.eclipse.jpt.core.internal.IXmlEObject
	 * @generated
	 */
	public EClass getIXmlEObject() {
		return iXmlEObjectEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.XmlEObject <em>Xml EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml EObject</em>'.
	 * @see org.eclipse.jpt.core.internal.XmlEObject
	 * @generated
	 */
	public EClass getXmlEObject() {
		return xmlEObjectEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.IJpaContentNode <em>IJpa Content Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IJpa Content Node</em>'.
	 * @see org.eclipse.jpt.core.internal.IJpaContentNode
	 * @generated
	 */
	public EClass getIJpaContentNode() {
		return iJpaContentNodeEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.IJpaRootContentNode <em>IJpa Root Content Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IJpa Root Content Node</em>'.
	 * @see org.eclipse.jpt.core.internal.IJpaRootContentNode
	 * @generated
	 */
	public EClass getIJpaRootContentNode() {
		return iJpaRootContentNodeEClass;
	}

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.jpt.core.internal.IJpaRootContentNode#getJpaFile <em>Jpa File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Jpa File</em>'.
	 * @see org.eclipse.jpt.core.internal.IJpaRootContentNode#getJpaFile()
	 * @see #getIJpaRootContentNode()
	 * @generated
	 */
	public EReference getIJpaRootContentNode_JpaFile() {
		return (EReference) iJpaRootContentNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.IPersistentType <em>IPersistent Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IPersistent Type</em>'.
	 * @see org.eclipse.jpt.core.internal.IPersistentType
	 * @generated
	 */
	public EClass getIPersistentType() {
		return iPersistentTypeEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.IPersistentType#getMappingKey <em>Mapping Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mapping Key</em>'.
	 * @see org.eclipse.jpt.core.internal.IPersistentType#getMappingKey()
	 * @see #getIPersistentType()
	 * @generated
	 */
	public EAttribute getIPersistentType_MappingKey() {
		return (EAttribute) iPersistentTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.ITypeMapping <em>IType Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IType Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.ITypeMapping
	 * @generated
	 */
	public EClass getITypeMapping() {
		return iTypeMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.ITypeMapping#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.ITypeMapping#getName()
	 * @see #getITypeMapping()
	 * @generated
	 */
	public EAttribute getITypeMapping_Name() {
		return (EAttribute) iTypeMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.ITypeMapping#getTableName <em>Table Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Table Name</em>'.
	 * @see org.eclipse.jpt.core.internal.ITypeMapping#getTableName()
	 * @see #getITypeMapping()
	 * @generated
	 */
	public EAttribute getITypeMapping_TableName() {
		return (EAttribute) iTypeMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.NullTypeMapping <em>Null Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Null Type Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.NullTypeMapping
	 * @generated
	 */
	public EClass getNullTypeMapping() {
		return nullTypeMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.IPersistentAttribute <em>IPersistent Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IPersistent Attribute</em>'.
	 * @see org.eclipse.jpt.core.internal.IPersistentAttribute
	 * @generated
	 */
	public EClass getIPersistentAttribute() {
		return iPersistentAttributeEClass;
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.jpt.core.internal.IPersistentAttribute#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.IPersistentAttribute#getMapping()
	 * @see #getIPersistentAttribute()
	 * @generated
	 */
	public EReference getIPersistentAttribute_Mapping() {
		return (EReference) iPersistentAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.IAttributeMapping <em>IAttribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IAttribute Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.IAttributeMapping
	 * @generated
	 */
	public EClass getIAttributeMapping() {
		return iAttributeMappingEClass;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.AccessType <em>Access Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Access Type</em>'.
	 * @see org.eclipse.jpt.core.internal.AccessType
	 * @generated
	 */
	public EEnum getAccessType() {
		return accessTypeEEnum;
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public JpaCoreFactory getJpaCoreFactory() {
		return (JpaCoreFactory) getEFactoryInstance();
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
		iJpaEObjectEClass = createEClass(IJPA_EOBJECT);
		jpaEObjectEClass = createEClass(JPA_EOBJECT);
		iJpaDataSourceEClass = createEClass(IJPA_DATA_SOURCE);
		jpaDataSourceEClass = createEClass(JPA_DATA_SOURCE);
		createEAttribute(jpaDataSourceEClass, JPA_DATA_SOURCE__CONNECTION_PROFILE_NAME);
		iJpaFileEClass = createEClass(IJPA_FILE);
		jpaFileEClass = createEClass(JPA_FILE);
		createEAttribute(jpaFileEClass, JPA_FILE__CONTENT_ID);
		createEReference(jpaFileEClass, JPA_FILE__CONTENT);
		iJpaSourceObjectEClass = createEClass(IJPA_SOURCE_OBJECT);
		iXmlEObjectEClass = createEClass(IXML_EOBJECT);
		xmlEObjectEClass = createEClass(XML_EOBJECT);
		iJpaContentNodeEClass = createEClass(IJPA_CONTENT_NODE);
		iJpaRootContentNodeEClass = createEClass(IJPA_ROOT_CONTENT_NODE);
		createEReference(iJpaRootContentNodeEClass, IJPA_ROOT_CONTENT_NODE__JPA_FILE);
		iPersistentTypeEClass = createEClass(IPERSISTENT_TYPE);
		createEAttribute(iPersistentTypeEClass, IPERSISTENT_TYPE__MAPPING_KEY);
		iTypeMappingEClass = createEClass(ITYPE_MAPPING);
		createEAttribute(iTypeMappingEClass, ITYPE_MAPPING__NAME);
		createEAttribute(iTypeMappingEClass, ITYPE_MAPPING__TABLE_NAME);
		nullTypeMappingEClass = createEClass(NULL_TYPE_MAPPING);
		iPersistentAttributeEClass = createEClass(IPERSISTENT_ATTRIBUTE);
		createEReference(iPersistentAttributeEClass, IPERSISTENT_ATTRIBUTE__MAPPING);
		iAttributeMappingEClass = createEClass(IATTRIBUTE_MAPPING);
		// Create enums
		accessTypeEEnum = createEEnum(ACCESS_TYPE);
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
		JpaCoreMappingsPackage theJpaCoreMappingsPackage = (JpaCoreMappingsPackage) EPackage.Registry.INSTANCE.getEPackage(JpaCoreMappingsPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		// Add subpackages
		getESubpackages().add(theJpaCoreMappingsPackage);
		// Create type parameters
		// Set bounds for type parameters
		// Add supertypes to classes
		jpaEObjectEClass.getESuperTypes().add(this.getIJpaEObject());
		iJpaDataSourceEClass.getESuperTypes().add(this.getIJpaEObject());
		jpaDataSourceEClass.getESuperTypes().add(this.getJpaEObject());
		jpaDataSourceEClass.getESuperTypes().add(this.getIJpaDataSource());
		iJpaFileEClass.getESuperTypes().add(this.getIJpaEObject());
		jpaFileEClass.getESuperTypes().add(this.getJpaEObject());
		jpaFileEClass.getESuperTypes().add(this.getIJpaFile());
		iJpaSourceObjectEClass.getESuperTypes().add(this.getIJpaEObject());
		iXmlEObjectEClass.getESuperTypes().add(this.getIJpaEObject());
		iXmlEObjectEClass.getESuperTypes().add(this.getIJpaSourceObject());
		xmlEObjectEClass.getESuperTypes().add(this.getJpaEObject());
		xmlEObjectEClass.getESuperTypes().add(this.getIXmlEObject());
		iJpaContentNodeEClass.getESuperTypes().add(this.getIJpaSourceObject());
		iJpaRootContentNodeEClass.getESuperTypes().add(this.getIJpaContentNode());
		iPersistentTypeEClass.getESuperTypes().add(this.getIJpaContentNode());
		iTypeMappingEClass.getESuperTypes().add(this.getIJpaSourceObject());
		nullTypeMappingEClass.getESuperTypes().add(this.getJpaEObject());
		nullTypeMappingEClass.getESuperTypes().add(this.getITypeMapping());
		nullTypeMappingEClass.getESuperTypes().add(this.getIJpaSourceObject());
		iPersistentAttributeEClass.getESuperTypes().add(this.getIJpaContentNode());
		iAttributeMappingEClass.getESuperTypes().add(this.getIJpaSourceObject());
		// Initialize classes and features; add operations and parameters
		initEClass(iJpaEObjectEClass, IJpaEObject.class, "IJpaEObject", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(jpaEObjectEClass, JpaEObject.class, "JpaEObject", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iJpaDataSourceEClass, IJpaDataSource.class, "IJpaDataSource", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		addEOperation(iJpaDataSourceEClass, theEcorePackage.getEString(), "getConnectionProfileName", 1, 1, !IS_UNIQUE, !IS_ORDERED);
		initEClass(jpaDataSourceEClass, JpaDataSource.class, "JpaDataSource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJpaDataSource_ConnectionProfileName(), ecorePackage.getEString(), "connectionProfileName", null, 1, 1, JpaDataSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEClass(iJpaFileEClass, IJpaFile.class, "IJpaFile", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		addEOperation(iJpaFileEClass, ecorePackage.getEString(), "getContentId", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEOperation(iJpaFileEClass, this.getIJpaRootContentNode(), "getContent", 0, 1, IS_UNIQUE, IS_ORDERED);
		initEClass(jpaFileEClass, JpaFile.class, "JpaFile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJpaFile_ContentId(), ecorePackage.getEString(), "contentId", null, 1, 1, JpaFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJpaFile_Content(), this.getIJpaRootContentNode(), this.getIJpaRootContentNode_JpaFile(), "content", null, 0, 1, JpaFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iJpaSourceObjectEClass, IJpaSourceObject.class, "IJpaSourceObject", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		addEOperation(iJpaSourceObjectEClass, this.getIJpaFile(), "getJpaFile", 0, 1, IS_UNIQUE, IS_ORDERED);
		initEClass(iXmlEObjectEClass, IXmlEObject.class, "IXmlEObject", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(xmlEObjectEClass, XmlEObject.class, "XmlEObject", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iJpaContentNodeEClass, IJpaContentNode.class, "IJpaContentNode", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		addEOperation(iJpaContentNodeEClass, this.getIJpaFile(), "getJpaFile", 1, 1, IS_UNIQUE, IS_ORDERED);
		initEClass(iJpaRootContentNodeEClass, IJpaRootContentNode.class, "IJpaRootContentNode", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIJpaRootContentNode_JpaFile(), this.getIJpaFile(), this.getJpaFile_Content(), "jpaFile", null, 0, 1, IJpaRootContentNode.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iPersistentTypeEClass, IPersistentType.class, "IPersistentType", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIPersistentType_MappingKey(), ecorePackage.getEString(), "mappingKey", null, 1, 1, IPersistentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		addEOperation(iPersistentTypeEClass, this.getITypeMapping(), "getMapping", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEOperation(iPersistentTypeEClass, this.getIPersistentType(), "parentPersistentType", 0, 1, IS_UNIQUE, IS_ORDERED);
		initEClass(iTypeMappingEClass, ITypeMapping.class, "ITypeMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getITypeMapping_Name(), ecorePackage.getEString(), "name", null, 0, 1, ITypeMapping.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getITypeMapping_TableName(), ecorePackage.getEString(), "tableName", null, 0, 1, ITypeMapping.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		addEOperation(iTypeMappingEClass, this.getIPersistentType(), "getPersistentType", 1, 1, IS_UNIQUE, IS_ORDERED);
		initEClass(nullTypeMappingEClass, NullTypeMapping.class, "NullTypeMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iPersistentAttributeEClass, IPersistentAttribute.class, "IPersistentAttribute", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIPersistentAttribute_Mapping(), this.getIAttributeMapping(), null, "mapping", null, 1, 1, IPersistentAttribute.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		addEOperation(iPersistentAttributeEClass, this.getITypeMapping(), "typeMapping", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEOperation(iPersistentAttributeEClass, theEcorePackage.getEString(), "getName", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEOperation(iPersistentAttributeEClass, ecorePackage.getEString(), "mappingKey", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEOperation(iPersistentAttributeEClass, ecorePackage.getEString(), "defaultMappingKey", 1, 1, IS_UNIQUE, IS_ORDERED);
		initEClass(iAttributeMappingEClass, IAttributeMapping.class, "IAttributeMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		addEOperation(iAttributeMappingEClass, this.getIPersistentAttribute(), "getPersistentAttribute", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEOperation(iAttributeMappingEClass, theEcorePackage.getEBoolean(), "isDefault", 0, 1, IS_UNIQUE, IS_ORDERED);
		// Initialize enums and add enum literals
		initEEnum(accessTypeEEnum, AccessType.class, "AccessType");
		addEEnumLiteral(accessTypeEEnum, AccessType.DEFAULT);
		addEEnumLiteral(accessTypeEEnum, AccessType.PROPERTY);
		addEEnumLiteral(accessTypeEEnum, AccessType.FIELD);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.IJpaEObject <em>IJpa EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.IJpaEObject
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaEObject()
		 * @generated
		 */
		public static final EClass IJPA_EOBJECT = eINSTANCE.getIJpaEObject();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.JpaEObject <em>Jpa EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.JpaEObject
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaEObject()
		 * @generated
		 */
		public static final EClass JPA_EOBJECT = eINSTANCE.getJpaEObject();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.IJpaDataSource <em>IJpa Data Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.IJpaDataSource
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaDataSource()
		 * @generated
		 */
		public static final EClass IJPA_DATA_SOURCE = eINSTANCE.getIJpaDataSource();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.JpaDataSource <em>Jpa Data Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.JpaDataSource
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaDataSource()
		 * @generated
		 */
		public static final EClass JPA_DATA_SOURCE = eINSTANCE.getJpaDataSource();

		/**
		 * The meta object literal for the '<em><b>Connection Profile Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JPA_DATA_SOURCE__CONNECTION_PROFILE_NAME = eINSTANCE.getJpaDataSource_ConnectionProfileName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.IJpaFile <em>IJpa File</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.IJpaFile
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaFile()
		 * @generated
		 */
		public static final EClass IJPA_FILE = eINSTANCE.getIJpaFile();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.JpaFile <em>Jpa File</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.JpaFile
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaFile()
		 * @generated
		 */
		public static final EClass JPA_FILE = eINSTANCE.getJpaFile();

		/**
		 * The meta object literal for the '<em><b>Content Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute JPA_FILE__CONTENT_ID = eINSTANCE.getJpaFile_ContentId();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference JPA_FILE__CONTENT = eINSTANCE.getJpaFile_Content();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.IJpaSourceObject <em>IJpa Source Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.IJpaSourceObject
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaSourceObject()
		 * @generated
		 */
		public static final EClass IJPA_SOURCE_OBJECT = eINSTANCE.getIJpaSourceObject();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.IXmlEObject <em>IXml EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.IXmlEObject
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIXmlEObject()
		 * @generated
		 */
		public static final EClass IXML_EOBJECT = eINSTANCE.getIXmlEObject();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.XmlEObject <em>Xml EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.XmlEObject
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getXmlEObject()
		 * @generated
		 */
		public static final EClass XML_EOBJECT = eINSTANCE.getXmlEObject();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.IJpaContentNode <em>IJpa Content Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.IJpaContentNode
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaContentNode()
		 * @generated
		 */
		public static final EClass IJPA_CONTENT_NODE = eINSTANCE.getIJpaContentNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.IJpaRootContentNode <em>IJpa Root Content Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.IJpaRootContentNode
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaRootContentNode()
		 * @generated
		 */
		public static final EClass IJPA_ROOT_CONTENT_NODE = eINSTANCE.getIJpaRootContentNode();

		/**
		 * The meta object literal for the '<em><b>Jpa File</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IJPA_ROOT_CONTENT_NODE__JPA_FILE = eINSTANCE.getIJpaRootContentNode_JpaFile();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.IPersistentType <em>IPersistent Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.IPersistentType
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIPersistentType()
		 * @generated
		 */
		public static final EClass IPERSISTENT_TYPE = eINSTANCE.getIPersistentType();

		/**
		 * The meta object literal for the '<em><b>Mapping Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IPERSISTENT_TYPE__MAPPING_KEY = eINSTANCE.getIPersistentType_MappingKey();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.ITypeMapping <em>IType Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.ITypeMapping
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getITypeMapping()
		 * @generated
		 */
		public static final EClass ITYPE_MAPPING = eINSTANCE.getITypeMapping();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITYPE_MAPPING__NAME = eINSTANCE.getITypeMapping_Name();

		/**
		 * The meta object literal for the '<em><b>Table Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITYPE_MAPPING__TABLE_NAME = eINSTANCE.getITypeMapping_TableName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.NullTypeMapping <em>Null Type Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.NullTypeMapping
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getNullTypeMapping()
		 * @generated
		 */
		public static final EClass NULL_TYPE_MAPPING = eINSTANCE.getNullTypeMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.IPersistentAttribute <em>IPersistent Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.IPersistentAttribute
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIPersistentAttribute()
		 * @generated
		 */
		public static final EClass IPERSISTENT_ATTRIBUTE = eINSTANCE.getIPersistentAttribute();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IPERSISTENT_ATTRIBUTE__MAPPING = eINSTANCE.getIPersistentAttribute_Mapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.IAttributeMapping <em>IAttribute Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.IAttributeMapping
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIAttributeMapping()
		 * @generated
		 */
		public static final EClass IATTRIBUTE_MAPPING = eINSTANCE.getIAttributeMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.AccessType <em>Access Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.AccessType
		 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getAccessType()
		 * @generated
		 */
		public static final EEnum ACCESS_TYPE = eINSTANCE.getAccessType();
	}
} //JpaCorePackage
