/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.jem.java.JavaRefPackage;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.content.java.JpaJavaPackage;
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
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsFactory
 * @model kind="package"
 * @generated
 */
public class JpaJavaMappingsPackage extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "mappings";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.core.java.mappings.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "jpt.core.java.mappings";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final JpaJavaMappingsPackage eINSTANCE = org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTypeMapping <em>Java Type Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTypeMapping
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaTypeMapping()
	 * @generated
	 */
	public static final int JAVA_TYPE_MAPPING = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TYPE_MAPPING__NAME = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TYPE_MAPPING__TABLE_NAME = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Java Type Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TYPE_MAPPING_FEATURE_COUNT = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity <em>Java Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaEntity()
	 * @generated
	 */
	public static final int JAVA_ENTITY = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__NAME = JAVA_TYPE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__TABLE_NAME = JAVA_TYPE_MAPPING__TABLE_NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__SPECIFIED_NAME = JAVA_TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__DEFAULT_NAME = JAVA_TYPE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__TABLE = JAVA_TYPE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Specified Secondary Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__SPECIFIED_SECONDARY_TABLES = JAVA_TYPE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__PRIMARY_KEY_JOIN_COLUMNS = JAVA_TYPE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Specified Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS = JAVA_TYPE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Default Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS = JAVA_TYPE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Inheritance Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__INHERITANCE_STRATEGY = JAVA_TYPE_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Default Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__DEFAULT_DISCRIMINATOR_VALUE = JAVA_TYPE_MAPPING_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Specified Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__SPECIFIED_DISCRIMINATOR_VALUE = JAVA_TYPE_MAPPING_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__DISCRIMINATOR_VALUE = JAVA_TYPE_MAPPING_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Discriminator Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__DISCRIMINATOR_COLUMN = JAVA_TYPE_MAPPING_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__SEQUENCE_GENERATOR = JAVA_TYPE_MAPPING_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__TABLE_GENERATOR = JAVA_TYPE_MAPPING_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__ATTRIBUTE_OVERRIDES = JAVA_TYPE_MAPPING_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Specified Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES = JAVA_TYPE_MAPPING_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Default Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__DEFAULT_ATTRIBUTE_OVERRIDES = JAVA_TYPE_MAPPING_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__ASSOCIATION_OVERRIDES = JAVA_TYPE_MAPPING_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Specified Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__SPECIFIED_ASSOCIATION_OVERRIDES = JAVA_TYPE_MAPPING_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Default Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__DEFAULT_ASSOCIATION_OVERRIDES = JAVA_TYPE_MAPPING_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__NAMED_QUERIES = JAVA_TYPE_MAPPING_FEATURE_COUNT + 20;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY__NAMED_NATIVE_QUERIES = JAVA_TYPE_MAPPING_FEATURE_COUNT + 21;

	/**
	 * The number of structural features of the '<em>Java Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ENTITY_FEATURE_COUNT = JAVA_TYPE_MAPPING_FEATURE_COUNT + 22;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaMappedSuperclass <em>Java Mapped Superclass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaMappedSuperclass
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaMappedSuperclass()
	 * @generated
	 */
	public static final int JAVA_MAPPED_SUPERCLASS = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MAPPED_SUPERCLASS__NAME = JAVA_TYPE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MAPPED_SUPERCLASS__TABLE_NAME = JAVA_TYPE_MAPPING__TABLE_NAME;

	/**
	 * The number of structural features of the '<em>Java Mapped Superclass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MAPPED_SUPERCLASS_FEATURE_COUNT = JAVA_TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddable <em>Java Embeddable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddable
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaEmbeddable()
	 * @generated
	 */
	public static final int JAVA_EMBEDDABLE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_EMBEDDABLE__NAME = JAVA_TYPE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_EMBEDDABLE__TABLE_NAME = JAVA_TYPE_MAPPING__TABLE_NAME;

	/**
	 * The number of structural features of the '<em>Java Embeddable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_EMBEDDABLE_FEATURE_COUNT = JAVA_TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNullTypeMapping <em>Java Null Type Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNullTypeMapping
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNullTypeMapping()
	 * @generated
	 */
	public static final int JAVA_NULL_TYPE_MAPPING = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NULL_TYPE_MAPPING__NAME = JAVA_TYPE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NULL_TYPE_MAPPING__TABLE_NAME = JAVA_TYPE_MAPPING__TABLE_NAME;

	/**
	 * The number of structural features of the '<em>Java Null Type Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NULL_TYPE_MAPPING_FEATURE_COUNT = JAVA_TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeMapping <em>Java Attribute Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeMapping
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaAttributeMapping()
	 * @generated
	 */
	public static final int JAVA_ATTRIBUTE_MAPPING = 5;

	/**
	 * The number of structural features of the '<em>Java Attribute Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNullAttributeMapping <em>Java Null Attribute Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNullAttributeMapping
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNullAttributeMapping()
	 * @generated
	 */
	public static final int JAVA_NULL_ATTRIBUTE_MAPPING = 19;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic <em>Java Basic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaBasic()
	 * @generated
	 */
	public static final int JAVA_BASIC = 6;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_BASIC__FETCH = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_BASIC__OPTIONAL = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_BASIC__COLUMN = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_BASIC__LOB = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_BASIC__TEMPORAL = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_BASIC__ENUMERATED = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Java Basic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_BASIC_FEATURE_COUNT = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaId <em>Java Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaId
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaId()
	 * @generated
	 */
	public static final int JAVA_ID = 7;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ID__COLUMN = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ID__GENERATED_VALUE = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ID__TEMPORAL = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ID__TABLE_GENERATOR = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ID__SEQUENCE_GENERATOR = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Java Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ID_FEATURE_COUNT = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTransient <em>Java Transient</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTransient
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaTransient()
	 * @generated
	 */
	public static final int JAVA_TRANSIENT = 8;

	/**
	 * The number of structural features of the '<em>Java Transient</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TRANSIENT_FEATURE_COUNT = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaVersion <em>Java Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaVersion
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaVersion()
	 * @generated
	 */
	public static final int JAVA_VERSION = 9;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_VERSION__COLUMN = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_VERSION__TEMPORAL = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Java Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_VERSION_FEATURE_COUNT = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddedId <em>Java Embedded Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddedId
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaEmbeddedId()
	 * @generated
	 */
	public static final int JAVA_EMBEDDED_ID = 10;

	/**
	 * The number of structural features of the '<em>Java Embedded Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_EMBEDDED_ID_FEATURE_COUNT = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbedded <em>Java Embedded</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbedded
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaEmbedded()
	 * @generated
	 */
	public static final int JAVA_EMBEDDED = 11;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_EMBEDDED__ATTRIBUTE_OVERRIDES = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Java Embedded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_EMBEDDED_FEATURE_COUNT = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping <em>Java Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaRelationshipMapping()
	 * @generated
	 */
	public static final int JAVA_RELATIONSHIP_MAPPING = 12;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_RELATIONSHIP_MAPPING__TARGET_ENTITY = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Java Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_RELATIONSHIP_MAPPING_FEATURE_COUNT = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSingleRelationshipMapping <em>Java Single Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaSingleRelationshipMapping
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaSingleRelationshipMapping()
	 * @generated
	 */
	public static final int JAVA_SINGLE_RELATIONSHIP_MAPPING = 13;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY = JAVA_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY = JAVA_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY = JAVA_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SINGLE_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY = JAVA_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SINGLE_RELATIONSHIP_MAPPING__FETCH = JAVA_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS = JAVA_RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS = JAVA_RELATIONSHIP_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS = JAVA_RELATIONSHIP_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL = JAVA_RELATIONSHIP_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Java Single Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT = JAVA_RELATIONSHIP_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToOne <em>Java Many To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToOne
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaManyToOne()
	 * @generated
	 */
	public static final int JAVA_MANY_TO_ONE = 14;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_ONE__TARGET_ENTITY = JAVA_SINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_ONE__SPECIFIED_TARGET_ENTITY = JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_ONE__DEFAULT_TARGET_ENTITY = JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_ONE__RESOLVED_TARGET_ENTITY = JAVA_SINGLE_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_ONE__FETCH = JAVA_SINGLE_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_ONE__JOIN_COLUMNS = JAVA_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_ONE__SPECIFIED_JOIN_COLUMNS = JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_ONE__DEFAULT_JOIN_COLUMNS = JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_ONE__OPTIONAL = JAVA_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL;

	/**
	 * The number of structural features of the '<em>Java Many To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_ONE_FEATURE_COUNT = JAVA_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToOne <em>Java One To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToOne
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaOneToOne()
	 * @generated
	 */
	public static final int JAVA_ONE_TO_ONE = 15;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_ONE__TARGET_ENTITY = JAVA_SINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_ONE__SPECIFIED_TARGET_ENTITY = JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_ONE__DEFAULT_TARGET_ENTITY = JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_ONE__RESOLVED_TARGET_ENTITY = JAVA_SINGLE_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_ONE__FETCH = JAVA_SINGLE_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_ONE__JOIN_COLUMNS = JAVA_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_ONE__SPECIFIED_JOIN_COLUMNS = JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_ONE__DEFAULT_JOIN_COLUMNS = JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_ONE__OPTIONAL = JAVA_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_ONE__MAPPED_BY = JAVA_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Java One To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_ONE_FEATURE_COUNT = JAVA_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping <em>Java Multi Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaMultiRelationshipMapping()
	 * @generated
	 */
	public static final int JAVA_MULTI_RELATIONSHIP_MAPPING = 16;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY = JAVA_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MULTI_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY = JAVA_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MULTI_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY = JAVA_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MULTI_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY = JAVA_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY = JAVA_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MULTI_RELATIONSHIP_MAPPING__FETCH = JAVA_RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE = JAVA_RELATIONSHIP_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MULTI_RELATIONSHIP_MAPPING__ORDER_BY = JAVA_RELATIONSHIP_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MULTI_RELATIONSHIP_MAPPING__MAP_KEY = JAVA_RELATIONSHIP_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Java Multi Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT = JAVA_RELATIONSHIP_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToMany <em>Java One To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToMany
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaOneToMany()
	 * @generated
	 */
	public static final int JAVA_ONE_TO_MANY = 17;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_MANY__TARGET_ENTITY = JAVA_MULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_MANY__SPECIFIED_TARGET_ENTITY = JAVA_MULTI_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_MANY__DEFAULT_TARGET_ENTITY = JAVA_MULTI_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_MANY__RESOLVED_TARGET_ENTITY = JAVA_MULTI_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_MANY__MAPPED_BY = JAVA_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_MANY__FETCH = JAVA_MULTI_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_MANY__JOIN_TABLE = JAVA_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_MANY__ORDER_BY = JAVA_MULTI_RELATIONSHIP_MAPPING__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_MANY__MAP_KEY = JAVA_MULTI_RELATIONSHIP_MAPPING__MAP_KEY;

	/**
	 * The number of structural features of the '<em>Java One To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ONE_TO_MANY_FEATURE_COUNT = JAVA_MULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToMany <em>Java Many To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToMany
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaManyToMany()
	 * @generated
	 */
	public static final int JAVA_MANY_TO_MANY = 18;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_MANY__TARGET_ENTITY = JAVA_MULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_MANY__SPECIFIED_TARGET_ENTITY = JAVA_MULTI_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_MANY__DEFAULT_TARGET_ENTITY = JAVA_MULTI_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_MANY__RESOLVED_TARGET_ENTITY = JAVA_MULTI_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_MANY__MAPPED_BY = JAVA_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_MANY__FETCH = JAVA_MULTI_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_MANY__JOIN_TABLE = JAVA_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_MANY__ORDER_BY = JAVA_MULTI_RELATIONSHIP_MAPPING__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_MANY__MAP_KEY = JAVA_MULTI_RELATIONSHIP_MAPPING__MAP_KEY;

	/**
	 * The number of structural features of the '<em>Java Many To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_MANY_TO_MANY_FEATURE_COUNT = JAVA_MULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Java Null Attribute Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NULL_ATTRIBUTE_MAPPING_FEATURE_COUNT = JAVA_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaTable <em>Abstract Java Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaTable
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getAbstractJavaTable()
	 * @generated
	 */
	public static final int ABSTRACT_JAVA_TABLE = 20;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_TABLE__NAME = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_TABLE__SPECIFIED_NAME = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_TABLE__DEFAULT_NAME = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_TABLE__CATALOG = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_TABLE__DEFAULT_CATALOG = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_TABLE__SCHEMA = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_TABLE__DEFAULT_SCHEMA = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Abstract Java Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_TABLE_FEATURE_COUNT = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTable <em>Java Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTable
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaTable()
	 * @generated
	 */
	public static final int JAVA_TABLE = 21;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE__NAME = ABSTRACT_JAVA_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE__SPECIFIED_NAME = ABSTRACT_JAVA_TABLE__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE__DEFAULT_NAME = ABSTRACT_JAVA_TABLE__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE__CATALOG = ABSTRACT_JAVA_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE__SPECIFIED_CATALOG = ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE__DEFAULT_CATALOG = ABSTRACT_JAVA_TABLE__DEFAULT_CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE__SCHEMA = ABSTRACT_JAVA_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE__SPECIFIED_SCHEMA = ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE__DEFAULT_SCHEMA = ABSTRACT_JAVA_TABLE__DEFAULT_SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The number of structural features of the '<em>Java Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_FEATURE_COUNT = ABSTRACT_JAVA_TABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinTable <em>Java Join Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinTable
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaJoinTable()
	 * @generated
	 */
	public static final int JAVA_JOIN_TABLE = 23;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn <em>Java Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaColumn()
	 * @generated
	 */
	public static final int JAVA_COLUMN = 26;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinColumn <em>Java Join Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinColumn
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaJoinColumn()
	 * @generated
	 */
	public static final int JAVA_JOIN_COLUMN = 27;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeOverride <em>Java Attribute Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeOverride
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaAttributeOverride()
	 * @generated
	 */
	public static final int JAVA_ATTRIBUTE_OVERRIDE = 29;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAssociationOverride <em>Java Association Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAssociationOverride
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaAssociationOverride()
	 * @generated
	 */
	public static final int JAVA_ASSOCIATION_OVERRIDE = 30;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn <em>Java Discriminator Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaDiscriminatorColumn()
	 * @generated
	 */
	public static final int JAVA_DISCRIMINATOR_COLUMN = 31;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaPrimaryKeyJoinColumn <em>Java Primary Key Join Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaPrimaryKeyJoinColumn
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaPrimaryKeyJoinColumn()
	 * @generated
	 */
	public static final int JAVA_PRIMARY_KEY_JOIN_COLUMN = 32;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaGeneratedValue <em>Java Generated Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaGeneratedValue
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaGeneratedValue()
	 * @generated
	 */
	public static final int JAVA_GENERATED_VALUE = 33;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaGenerator <em>Java Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaGenerator
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaGenerator()
	 * @generated
	 */
	public static final int JAVA_GENERATOR = 34;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator <em>Java Table Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaTableGenerator()
	 * @generated
	 */
	public static final int JAVA_TABLE_GENERATOR = 35;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSequenceGenerator <em>Java Sequence Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaSequenceGenerator
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaSequenceGenerator()
	 * @generated
	 */
	public static final int JAVA_SEQUENCE_GENERATOR = 36;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSecondaryTable <em>Java Secondary Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaSecondaryTable
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaSecondaryTable()
	 * @generated
	 */
	public static final int JAVA_SECONDARY_TABLE = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE__NAME = ABSTRACT_JAVA_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE__SPECIFIED_NAME = ABSTRACT_JAVA_TABLE__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE__DEFAULT_NAME = ABSTRACT_JAVA_TABLE__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE__CATALOG = ABSTRACT_JAVA_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE__SPECIFIED_CATALOG = ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE__DEFAULT_CATALOG = ABSTRACT_JAVA_TABLE__DEFAULT_CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE__SCHEMA = ABSTRACT_JAVA_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE__SPECIFIED_SCHEMA = ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE__DEFAULT_SCHEMA = ABSTRACT_JAVA_TABLE__DEFAULT_SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS = ABSTRACT_JAVA_TABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS = ABSTRACT_JAVA_TABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS = ABSTRACT_JAVA_TABLE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Java Secondary Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SECONDARY_TABLE_FEATURE_COUNT = ABSTRACT_JAVA_TABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__NAME = ABSTRACT_JAVA_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__SPECIFIED_NAME = ABSTRACT_JAVA_TABLE__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__DEFAULT_NAME = ABSTRACT_JAVA_TABLE__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__CATALOG = ABSTRACT_JAVA_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__SPECIFIED_CATALOG = ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__DEFAULT_CATALOG = ABSTRACT_JAVA_TABLE__DEFAULT_CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__SCHEMA = ABSTRACT_JAVA_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__SPECIFIED_SCHEMA = ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__DEFAULT_SCHEMA = ABSTRACT_JAVA_TABLE__DEFAULT_SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__JOIN_COLUMNS = ABSTRACT_JAVA_TABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS = ABSTRACT_JAVA_TABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__DEFAULT_JOIN_COLUMNS = ABSTRACT_JAVA_TABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__INVERSE_JOIN_COLUMNS = ABSTRACT_JAVA_TABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Specified Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS = ABSTRACT_JAVA_TABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Default Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS = ABSTRACT_JAVA_TABLE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Java Join Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_TABLE_FEATURE_COUNT = ABSTRACT_JAVA_TABLE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedColumn <em>Java Named Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedColumn
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNamedColumn()
	 * @generated
	 */
	public static final int JAVA_NAMED_COLUMN = 24;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_COLUMN__NAME = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_COLUMN__SPECIFIED_NAME = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_COLUMN__DEFAULT_NAME = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_COLUMN__COLUMN_DEFINITION = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Java Named Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_COLUMN_FEATURE_COUNT = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn <em>Abstract Java Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getAbstractJavaColumn()
	 * @generated
	 */
	public static final int ABSTRACT_JAVA_COLUMN = 25;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_COLUMN__NAME = JAVA_NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_COLUMN__SPECIFIED_NAME = JAVA_NAMED_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_COLUMN__DEFAULT_NAME = JAVA_NAMED_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_COLUMN__COLUMN_DEFINITION = JAVA_NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_COLUMN__UNIQUE = JAVA_NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_COLUMN__NULLABLE = JAVA_NAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_COLUMN__INSERTABLE = JAVA_NAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_COLUMN__UPDATABLE = JAVA_NAMED_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_COLUMN__TABLE = JAVA_NAMED_COLUMN_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_COLUMN__SPECIFIED_TABLE = JAVA_NAMED_COLUMN_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_COLUMN__DEFAULT_TABLE = JAVA_NAMED_COLUMN_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Abstract Java Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_JAVA_COLUMN_FEATURE_COUNT = JAVA_NAMED_COLUMN_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__NAME = ABSTRACT_JAVA_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__SPECIFIED_NAME = ABSTRACT_JAVA_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__DEFAULT_NAME = ABSTRACT_JAVA_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__COLUMN_DEFINITION = ABSTRACT_JAVA_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__UNIQUE = ABSTRACT_JAVA_COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__NULLABLE = ABSTRACT_JAVA_COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__INSERTABLE = ABSTRACT_JAVA_COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__UPDATABLE = ABSTRACT_JAVA_COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__TABLE = ABSTRACT_JAVA_COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__SPECIFIED_TABLE = ABSTRACT_JAVA_COLUMN__SPECIFIED_TABLE;

	/**
	 * The feature id for the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__DEFAULT_TABLE = ABSTRACT_JAVA_COLUMN__DEFAULT_TABLE;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__LENGTH = ABSTRACT_JAVA_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Precision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__PRECISION = ABSTRACT_JAVA_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN__SCALE = ABSTRACT_JAVA_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Java Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_COLUMN_FEATURE_COUNT = ABSTRACT_JAVA_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__NAME = ABSTRACT_JAVA_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__SPECIFIED_NAME = ABSTRACT_JAVA_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__DEFAULT_NAME = ABSTRACT_JAVA_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__COLUMN_DEFINITION = ABSTRACT_JAVA_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__UNIQUE = ABSTRACT_JAVA_COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__NULLABLE = ABSTRACT_JAVA_COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__INSERTABLE = ABSTRACT_JAVA_COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__UPDATABLE = ABSTRACT_JAVA_COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__TABLE = ABSTRACT_JAVA_COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__SPECIFIED_TABLE = ABSTRACT_JAVA_COLUMN__SPECIFIED_TABLE;

	/**
	 * The feature id for the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__DEFAULT_TABLE = ABSTRACT_JAVA_COLUMN__DEFAULT_TABLE;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__REFERENCED_COLUMN_NAME = ABSTRACT_JAVA_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME = ABSTRACT_JAVA_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME = ABSTRACT_JAVA_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Java Join Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_JOIN_COLUMN_FEATURE_COUNT = ABSTRACT_JAVA_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOverride <em>Java Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOverride
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaOverride()
	 * @generated
	 */
	public static final int JAVA_OVERRIDE = 28;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_OVERRIDE__NAME = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Java Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_OVERRIDE_FEATURE_COUNT = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ATTRIBUTE_OVERRIDE__NAME = JAVA_OVERRIDE__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ATTRIBUTE_OVERRIDE__COLUMN = JAVA_OVERRIDE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Java Attribute Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ATTRIBUTE_OVERRIDE_FEATURE_COUNT = JAVA_OVERRIDE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ASSOCIATION_OVERRIDE__NAME = JAVA_OVERRIDE__NAME;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ASSOCIATION_OVERRIDE__JOIN_COLUMNS = JAVA_OVERRIDE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS = JAVA_OVERRIDE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS = JAVA_OVERRIDE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Java Association Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ASSOCIATION_OVERRIDE_FEATURE_COUNT = JAVA_OVERRIDE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_DISCRIMINATOR_COLUMN__NAME = JAVA_NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_NAME = JAVA_NAMED_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_DISCRIMINATOR_COLUMN__DEFAULT_NAME = JAVA_NAMED_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION = JAVA_NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Discriminator Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = JAVA_NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH = JAVA_NAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Specified Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH = JAVA_NAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_DISCRIMINATOR_COLUMN__LENGTH = JAVA_NAMED_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Java Discriminator Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_DISCRIMINATOR_COLUMN_FEATURE_COUNT = JAVA_NAMED_COLUMN_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PRIMARY_KEY_JOIN_COLUMN__NAME = JAVA_NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_NAME = JAVA_NAMED_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_NAME = JAVA_NAMED_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION = JAVA_NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = JAVA_NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME = JAVA_NAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME = JAVA_NAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Java Primary Key Join Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_PRIMARY_KEY_JOIN_COLUMN_FEATURE_COUNT = JAVA_NAMED_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_GENERATED_VALUE__STRATEGY = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_GENERATED_VALUE__GENERATOR = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Java Generated Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_GENERATED_VALUE_FEATURE_COUNT = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_GENERATOR__NAME = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_GENERATOR__INITIAL_VALUE = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Specified Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_GENERATOR__SPECIFIED_INITIAL_VALUE = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Default Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_GENERATOR__DEFAULT_INITIAL_VALUE = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_GENERATOR__ALLOCATION_SIZE = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Specified Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_GENERATOR__SPECIFIED_ALLOCATION_SIZE = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Default Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_GENERATOR__DEFAULT_ALLOCATION_SIZE = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Java Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_GENERATOR_FEATURE_COUNT = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__NAME = JAVA_GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__INITIAL_VALUE = JAVA_GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Specified Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__SPECIFIED_INITIAL_VALUE = JAVA_GENERATOR__SPECIFIED_INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Default Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__DEFAULT_INITIAL_VALUE = JAVA_GENERATOR__DEFAULT_INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__ALLOCATION_SIZE = JAVA_GENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Specified Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__SPECIFIED_ALLOCATION_SIZE = JAVA_GENERATOR__SPECIFIED_ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Default Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__DEFAULT_ALLOCATION_SIZE = JAVA_GENERATOR__DEFAULT_ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__TABLE = JAVA_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__SPECIFIED_TABLE = JAVA_GENERATOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__DEFAULT_TABLE = JAVA_GENERATOR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__CATALOG = JAVA_GENERATOR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__SPECIFIED_CATALOG = JAVA_GENERATOR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__DEFAULT_CATALOG = JAVA_GENERATOR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__SCHEMA = JAVA_GENERATOR_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__SPECIFIED_SCHEMA = JAVA_GENERATOR_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__DEFAULT_SCHEMA = JAVA_GENERATOR_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__PK_COLUMN_NAME = JAVA_GENERATOR_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Specified Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME = JAVA_GENERATOR_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Default Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__DEFAULT_PK_COLUMN_NAME = JAVA_GENERATOR_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__VALUE_COLUMN_NAME = JAVA_GENERATOR_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Specified Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME = JAVA_GENERATOR_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Default Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__DEFAULT_VALUE_COLUMN_NAME = JAVA_GENERATOR_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__PK_COLUMN_VALUE = JAVA_GENERATOR_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Specified Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE = JAVA_GENERATOR_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Default Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__DEFAULT_PK_COLUMN_VALUE = JAVA_GENERATOR_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR__UNIQUE_CONSTRAINTS = JAVA_GENERATOR_FEATURE_COUNT + 18;

	/**
	 * The number of structural features of the '<em>Java Table Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_TABLE_GENERATOR_FEATURE_COUNT = JAVA_GENERATOR_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SEQUENCE_GENERATOR__NAME = JAVA_GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SEQUENCE_GENERATOR__INITIAL_VALUE = JAVA_GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Specified Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SEQUENCE_GENERATOR__SPECIFIED_INITIAL_VALUE = JAVA_GENERATOR__SPECIFIED_INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Default Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SEQUENCE_GENERATOR__DEFAULT_INITIAL_VALUE = JAVA_GENERATOR__DEFAULT_INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SEQUENCE_GENERATOR__ALLOCATION_SIZE = JAVA_GENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Specified Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SEQUENCE_GENERATOR__SPECIFIED_ALLOCATION_SIZE = JAVA_GENERATOR__SPECIFIED_ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Default Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SEQUENCE_GENERATOR__DEFAULT_ALLOCATION_SIZE = JAVA_GENERATOR__DEFAULT_ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SEQUENCE_GENERATOR__SEQUENCE_NAME = JAVA_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME = JAVA_GENERATOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME = JAVA_GENERATOR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Java Sequence Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SEQUENCE_GENERATOR_FEATURE_COUNT = JAVA_GENERATOR_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOrderBy <em>Java Order By</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOrderBy
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaOrderBy()
	 * @generated
	 */
	public static final int JAVA_ORDER_BY = 37;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ORDER_BY__VALUE = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ORDER_BY__TYPE = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Java Order By</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ORDER_BY_FEATURE_COUNT = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAbstractQuery <em>Java Abstract Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAbstractQuery
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaAbstractQuery()
	 * @generated
	 */
	public static final int JAVA_ABSTRACT_QUERY = 38;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ABSTRACT_QUERY__NAME = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ABSTRACT_QUERY__QUERY = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ABSTRACT_QUERY__HINTS = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Java Abstract Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_ABSTRACT_QUERY_FEATURE_COUNT = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedQuery <em>Java Named Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedQuery
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNamedQuery()
	 * @generated
	 */
	public static final int JAVA_NAMED_QUERY = 39;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_QUERY__NAME = JAVA_ABSTRACT_QUERY__NAME;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_QUERY__QUERY = JAVA_ABSTRACT_QUERY__QUERY;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_QUERY__HINTS = JAVA_ABSTRACT_QUERY__HINTS;

	/**
	 * The number of structural features of the '<em>Java Named Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_QUERY_FEATURE_COUNT = JAVA_ABSTRACT_QUERY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedNativeQuery <em>Java Named Native Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedNativeQuery
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNamedNativeQuery()
	 * @generated
	 */
	public static final int JAVA_NAMED_NATIVE_QUERY = 40;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_NATIVE_QUERY__NAME = JAVA_ABSTRACT_QUERY__NAME;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_NATIVE_QUERY__QUERY = JAVA_ABSTRACT_QUERY__QUERY;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_NATIVE_QUERY__HINTS = JAVA_ABSTRACT_QUERY__HINTS;

	/**
	 * The feature id for the '<em><b>Result Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_NATIVE_QUERY__RESULT_CLASS = JAVA_ABSTRACT_QUERY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Result Set Mapping</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING = JAVA_ABSTRACT_QUERY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Java Named Native Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NAMED_NATIVE_QUERY_FEATURE_COUNT = JAVA_ABSTRACT_QUERY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaQueryHint <em>Java Query Hint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaQueryHint
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaQueryHint()
	 * @generated
	 */
	public static final int JAVA_QUERY_HINT = 41;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_QUERY_HINT__NAME = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_QUERY_HINT__VALUE = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Java Query Hint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_QUERY_HINT_FEATURE_COUNT = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaUniqueConstraint <em>Java Unique Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaUniqueConstraint
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaUniqueConstraint()
	 * @generated
	 */
	public static final int JAVA_UNIQUE_CONSTRAINT = 42;

	/**
	 * The feature id for the '<em><b>Column Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_UNIQUE_CONSTRAINT__COLUMN_NAMES = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Java Unique Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_UNIQUE_CONSTRAINT_FEATURE_COUNT = JpaJavaPackage.JAVA_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaMappedSuperclassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaEmbeddableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaNullTypeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaNullAttributeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaBasicEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaIdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaTransientEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaVersionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaEmbeddedIdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaEmbeddedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaAttributeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaTypeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractJavaTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaRelationshipMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaSingleRelationshipMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaManyToOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaOneToOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaMultiRelationshipMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaOneToManyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaManyToManyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaJoinTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaNamedColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractJavaColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaJoinColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaOverrideEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaAttributeOverrideEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaAssociationOverrideEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaDiscriminatorColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaPrimaryKeyJoinColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaGeneratedValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaTableGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaSequenceGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaOrderByEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaAbstractQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaNamedQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaNamedNativeQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaQueryHintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaUniqueConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaSecondaryTableEClass = null;

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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private JpaJavaMappingsPackage() {
		super(eNS_URI, JpaJavaMappingsFactory.eINSTANCE);
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
	public static JpaJavaMappingsPackage init() {
		if (isInited)
			return (JpaJavaMappingsPackage) EPackage.Registry.INSTANCE.getEPackage(JpaJavaMappingsPackage.eNS_URI);
		// Obtain or create and register package
		JpaJavaMappingsPackage theJpaJavaMappingsPackage = (JpaJavaMappingsPackage) (EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof JpaJavaMappingsPackage ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new JpaJavaMappingsPackage());
		isInited = true;
		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		JavaRefPackage.eINSTANCE.eClass();
		// Obtain or create and register interdependencies
		JpaCorePackage theJpaCorePackage = (JpaCorePackage) (EPackage.Registry.INSTANCE.getEPackage(JpaCorePackage.eNS_URI) instanceof JpaCorePackage ? EPackage.Registry.INSTANCE.getEPackage(JpaCorePackage.eNS_URI) : JpaCorePackage.eINSTANCE);
		JpaCoreMappingsPackage theJpaCoreMappingsPackage = (JpaCoreMappingsPackage) (EPackage.Registry.INSTANCE.getEPackage(JpaCoreMappingsPackage.eNS_URI) instanceof JpaCoreMappingsPackage ? EPackage.Registry.INSTANCE.getEPackage(JpaCoreMappingsPackage.eNS_URI) : JpaCoreMappingsPackage.eINSTANCE);
		JpaJavaPackage theJpaJavaPackage = (JpaJavaPackage) (EPackage.Registry.INSTANCE.getEPackage(JpaJavaPackage.eNS_URI) instanceof JpaJavaPackage ? EPackage.Registry.INSTANCE.getEPackage(JpaJavaPackage.eNS_URI) : JpaJavaPackage.eINSTANCE);
		OrmPackage theOrmPackage = (OrmPackage) (EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) : OrmPackage.eINSTANCE);
		PersistencePackage thePersistencePackage = (PersistencePackage) (EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);
		// Create package meta-data objects
		theJpaJavaMappingsPackage.createPackageContents();
		theJpaCorePackage.createPackageContents();
		theJpaCoreMappingsPackage.createPackageContents();
		theJpaJavaPackage.createPackageContents();
		theOrmPackage.createPackageContents();
		thePersistencePackage.createPackageContents();
		// Initialize created meta-data
		theJpaJavaMappingsPackage.initializePackageContents();
		theJpaCorePackage.initializePackageContents();
		theJpaCoreMappingsPackage.initializePackageContents();
		theJpaJavaPackage.initializePackageContents();
		theOrmPackage.initializePackageContents();
		thePersistencePackage.initializePackageContents();
		// Mark meta-data to indicate it can't be changed
		theJpaJavaMappingsPackage.freeze();
		return theJpaJavaMappingsPackage;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity <em>Java Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Entity</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity
	 * @generated
	 */
	public EClass getJavaEntity() {
		return javaEntityEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaMappedSuperclass <em>Java Mapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Mapped Superclass</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaMappedSuperclass
	 * @generated
	 */
	public EClass getJavaMappedSuperclass() {
		return javaMappedSuperclassEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddable <em>Java Embeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Embeddable</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddable
	 * @generated
	 */
	public EClass getJavaEmbeddable() {
		return javaEmbeddableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNullTypeMapping <em>Java Null Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Null Type Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNullTypeMapping
	 * @generated
	 */
	public EClass getJavaNullTypeMapping() {
		return javaNullTypeMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNullAttributeMapping <em>Java Null Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Null Attribute Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNullAttributeMapping
	 * @generated
	 */
	public EClass getJavaNullAttributeMapping() {
		return javaNullAttributeMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic <em>Java Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Basic</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic
	 * @generated
	 */
	public EClass getJavaBasic() {
		return javaBasicEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaId <em>Java Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Id</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaId
	 * @generated
	 */
	public EClass getJavaId() {
		return javaIdEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTransient <em>Java Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Transient</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTransient
	 * @generated
	 */
	public EClass getJavaTransient() {
		return javaTransientEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaVersion <em>Java Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Version</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaVersion
	 * @generated
	 */
	public EClass getJavaVersion() {
		return javaVersionEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddedId <em>Java Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Embedded Id</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddedId
	 * @generated
	 */
	public EClass getJavaEmbeddedId() {
		return javaEmbeddedIdEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbedded <em>Java Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Embedded</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbedded
	 * @generated
	 */
	public EClass getJavaEmbedded() {
		return javaEmbeddedEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeMapping <em>Java Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Attribute Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeMapping
	 * @generated
	 */
	public EClass getJavaAttributeMapping() {
		return javaAttributeMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTypeMapping <em>Java Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Type Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTypeMapping
	 * @generated
	 */
	public EClass getJavaTypeMapping() {
		return javaTypeMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaTable <em>Abstract Java Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Java Table</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaTable
	 * @generated
	 */
	public EClass getAbstractJavaTable() {
		return abstractJavaTableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTable <em>Java Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Table</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTable
	 * @generated
	 */
	public EClass getJavaTable() {
		return javaTableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn <em>Java Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Column</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn
	 * @generated
	 */
	public EClass getJavaColumn() {
		return javaColumnEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping <em>Java Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping
	 * @generated
	 */
	public EClass getJavaRelationshipMapping() {
		return javaRelationshipMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSingleRelationshipMapping <em>Java Single Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Single Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaSingleRelationshipMapping
	 * @generated
	 */
	public EClass getJavaSingleRelationshipMapping() {
		return javaSingleRelationshipMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToOne <em>Java Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Many To One</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToOne
	 * @generated
	 */
	public EClass getJavaManyToOne() {
		return javaManyToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToOne <em>Java One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java One To One</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToOne
	 * @generated
	 */
	public EClass getJavaOneToOne() {
		return javaOneToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping <em>Java Multi Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Multi Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping
	 * @generated
	 */
	public EClass getJavaMultiRelationshipMapping() {
		return javaMultiRelationshipMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToMany <em>Java One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java One To Many</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToMany
	 * @generated
	 */
	public EClass getJavaOneToMany() {
		return javaOneToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToMany <em>Java Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Many To Many</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToMany
	 * @generated
	 */
	public EClass getJavaManyToMany() {
		return javaManyToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinTable <em>Java Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Join Table</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinTable
	 * @generated
	 */
	public EClass getJavaJoinTable() {
		return javaJoinTableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedColumn <em>Java Named Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Named Column</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedColumn
	 * @generated
	 */
	public EClass getJavaNamedColumn() {
		return javaNamedColumnEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn <em>Abstract Java Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Java Column</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn
	 * @generated
	 */
	public EClass getAbstractJavaColumn() {
		return abstractJavaColumnEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinColumn <em>Java Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Join Column</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinColumn
	 * @generated
	 */
	public EClass getJavaJoinColumn() {
		return javaJoinColumnEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOverride <em>Java Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Override</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOverride
	 * @generated
	 */
	public EClass getJavaOverride() {
		return javaOverrideEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeOverride <em>Java Attribute Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Attribute Override</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeOverride
	 * @generated
	 */
	public EClass getJavaAttributeOverride() {
		return javaAttributeOverrideEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAssociationOverride <em>Java Association Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Association Override</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAssociationOverride
	 * @generated
	 */
	public EClass getJavaAssociationOverride() {
		return javaAssociationOverrideEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn <em>Java Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Discriminator Column</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn
	 * @generated
	 */
	public EClass getJavaDiscriminatorColumn() {
		return javaDiscriminatorColumnEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaPrimaryKeyJoinColumn <em>Java Primary Key Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Primary Key Join Column</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaPrimaryKeyJoinColumn
	 * @generated
	 */
	public EClass getJavaPrimaryKeyJoinColumn() {
		return javaPrimaryKeyJoinColumnEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaGeneratedValue <em>Java Generated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Generated Value</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaGeneratedValue
	 * @generated
	 */
	public EClass getJavaGeneratedValue() {
		return javaGeneratedValueEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaGenerator <em>Java Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaGenerator
	 * @generated
	 */
	public EClass getJavaGenerator() {
		return javaGeneratorEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator <em>Java Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Table Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator
	 * @generated
	 */
	public EClass getJavaTableGenerator() {
		return javaTableGeneratorEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSequenceGenerator <em>Java Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Sequence Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaSequenceGenerator
	 * @generated
	 */
	public EClass getJavaSequenceGenerator() {
		return javaSequenceGeneratorEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOrderBy <em>Java Order By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Order By</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOrderBy
	 * @generated
	 */
	public EClass getJavaOrderBy() {
		return javaOrderByEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAbstractQuery <em>Java Abstract Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Abstract Query</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAbstractQuery
	 * @generated
	 */
	public EClass getJavaAbstractQuery() {
		return javaAbstractQueryEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedQuery <em>Java Named Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Named Query</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedQuery
	 * @generated
	 */
	public EClass getJavaNamedQuery() {
		return javaNamedQueryEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedNativeQuery <em>Java Named Native Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Named Native Query</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedNativeQuery
	 * @generated
	 */
	public EClass getJavaNamedNativeQuery() {
		return javaNamedNativeQueryEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaQueryHint <em>Java Query Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Query Hint</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaQueryHint
	 * @generated
	 */
	public EClass getJavaQueryHint() {
		return javaQueryHintEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaUniqueConstraint <em>Java Unique Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Unique Constraint</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaUniqueConstraint
	 * @generated
	 */
	public EClass getJavaUniqueConstraint() {
		return javaUniqueConstraintEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSecondaryTable <em>Java Secondary Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Secondary Table</em>'.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaSecondaryTable
	 * @generated
	 */
	public EClass getJavaSecondaryTable() {
		return javaSecondaryTableEClass;
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public JpaJavaMappingsFactory getJpaJavaMappingsFactory() {
		return (JpaJavaMappingsFactory) getEFactoryInstance();
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
		javaTypeMappingEClass = createEClass(JAVA_TYPE_MAPPING);
		javaEntityEClass = createEClass(JAVA_ENTITY);
		javaMappedSuperclassEClass = createEClass(JAVA_MAPPED_SUPERCLASS);
		javaEmbeddableEClass = createEClass(JAVA_EMBEDDABLE);
		javaNullTypeMappingEClass = createEClass(JAVA_NULL_TYPE_MAPPING);
		javaAttributeMappingEClass = createEClass(JAVA_ATTRIBUTE_MAPPING);
		javaBasicEClass = createEClass(JAVA_BASIC);
		javaIdEClass = createEClass(JAVA_ID);
		javaTransientEClass = createEClass(JAVA_TRANSIENT);
		javaVersionEClass = createEClass(JAVA_VERSION);
		javaEmbeddedIdEClass = createEClass(JAVA_EMBEDDED_ID);
		javaEmbeddedEClass = createEClass(JAVA_EMBEDDED);
		javaRelationshipMappingEClass = createEClass(JAVA_RELATIONSHIP_MAPPING);
		javaSingleRelationshipMappingEClass = createEClass(JAVA_SINGLE_RELATIONSHIP_MAPPING);
		javaManyToOneEClass = createEClass(JAVA_MANY_TO_ONE);
		javaOneToOneEClass = createEClass(JAVA_ONE_TO_ONE);
		javaMultiRelationshipMappingEClass = createEClass(JAVA_MULTI_RELATIONSHIP_MAPPING);
		javaOneToManyEClass = createEClass(JAVA_ONE_TO_MANY);
		javaManyToManyEClass = createEClass(JAVA_MANY_TO_MANY);
		javaNullAttributeMappingEClass = createEClass(JAVA_NULL_ATTRIBUTE_MAPPING);
		abstractJavaTableEClass = createEClass(ABSTRACT_JAVA_TABLE);
		javaTableEClass = createEClass(JAVA_TABLE);
		javaSecondaryTableEClass = createEClass(JAVA_SECONDARY_TABLE);
		javaJoinTableEClass = createEClass(JAVA_JOIN_TABLE);
		javaNamedColumnEClass = createEClass(JAVA_NAMED_COLUMN);
		abstractJavaColumnEClass = createEClass(ABSTRACT_JAVA_COLUMN);
		javaColumnEClass = createEClass(JAVA_COLUMN);
		javaJoinColumnEClass = createEClass(JAVA_JOIN_COLUMN);
		javaOverrideEClass = createEClass(JAVA_OVERRIDE);
		javaAttributeOverrideEClass = createEClass(JAVA_ATTRIBUTE_OVERRIDE);
		javaAssociationOverrideEClass = createEClass(JAVA_ASSOCIATION_OVERRIDE);
		javaDiscriminatorColumnEClass = createEClass(JAVA_DISCRIMINATOR_COLUMN);
		javaPrimaryKeyJoinColumnEClass = createEClass(JAVA_PRIMARY_KEY_JOIN_COLUMN);
		javaGeneratedValueEClass = createEClass(JAVA_GENERATED_VALUE);
		javaGeneratorEClass = createEClass(JAVA_GENERATOR);
		javaTableGeneratorEClass = createEClass(JAVA_TABLE_GENERATOR);
		javaSequenceGeneratorEClass = createEClass(JAVA_SEQUENCE_GENERATOR);
		javaOrderByEClass = createEClass(JAVA_ORDER_BY);
		javaAbstractQueryEClass = createEClass(JAVA_ABSTRACT_QUERY);
		javaNamedQueryEClass = createEClass(JAVA_NAMED_QUERY);
		javaNamedNativeQueryEClass = createEClass(JAVA_NAMED_NATIVE_QUERY);
		javaQueryHintEClass = createEClass(JAVA_QUERY_HINT);
		javaUniqueConstraintEClass = createEClass(JAVA_UNIQUE_CONSTRAINT);
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
		JpaJavaPackage theJpaJavaPackage = (JpaJavaPackage) EPackage.Registry.INSTANCE.getEPackage(JpaJavaPackage.eNS_URI);
		JpaCoreMappingsPackage theJpaCoreMappingsPackage = (JpaCoreMappingsPackage) EPackage.Registry.INSTANCE.getEPackage(JpaCoreMappingsPackage.eNS_URI);
		// Create type parameters
		// Set bounds for type parameters
		// Add supertypes to classes
		javaTypeMappingEClass.getESuperTypes().add(theJpaJavaPackage.getJavaEObject());
		javaTypeMappingEClass.getESuperTypes().add(theJpaJavaPackage.getIJavaTypeMapping());
		javaEntityEClass.getESuperTypes().add(this.getJavaTypeMapping());
		javaEntityEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIEntity());
		javaMappedSuperclassEClass.getESuperTypes().add(this.getJavaTypeMapping());
		javaMappedSuperclassEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIMappedSuperclass());
		javaEmbeddableEClass.getESuperTypes().add(this.getJavaTypeMapping());
		javaEmbeddableEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIEmbeddable());
		javaNullTypeMappingEClass.getESuperTypes().add(this.getJavaTypeMapping());
		javaAttributeMappingEClass.getESuperTypes().add(theJpaJavaPackage.getJavaEObject());
		javaAttributeMappingEClass.getESuperTypes().add(theJpaJavaPackage.getIJavaAttributeMapping());
		javaBasicEClass.getESuperTypes().add(this.getJavaAttributeMapping());
		javaBasicEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIBasic());
		javaIdEClass.getESuperTypes().add(this.getJavaAttributeMapping());
		javaIdEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIId());
		javaTransientEClass.getESuperTypes().add(this.getJavaAttributeMapping());
		javaTransientEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getITransient());
		javaVersionEClass.getESuperTypes().add(this.getJavaAttributeMapping());
		javaVersionEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIVersion());
		javaEmbeddedIdEClass.getESuperTypes().add(this.getJavaAttributeMapping());
		javaEmbeddedIdEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIEmbeddedId());
		javaEmbeddedEClass.getESuperTypes().add(this.getJavaAttributeMapping());
		javaEmbeddedEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIEmbedded());
		javaRelationshipMappingEClass.getESuperTypes().add(this.getJavaAttributeMapping());
		javaRelationshipMappingEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIRelationshipMapping());
		javaSingleRelationshipMappingEClass.getESuperTypes().add(this.getJavaRelationshipMapping());
		javaSingleRelationshipMappingEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getISingleRelationshipMapping());
		javaManyToOneEClass.getESuperTypes().add(this.getJavaSingleRelationshipMapping());
		javaManyToOneEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIManyToOne());
		javaOneToOneEClass.getESuperTypes().add(this.getJavaSingleRelationshipMapping());
		javaOneToOneEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIOneToOne());
		javaMultiRelationshipMappingEClass.getESuperTypes().add(this.getJavaRelationshipMapping());
		javaMultiRelationshipMappingEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIMultiRelationshipMapping());
		javaOneToManyEClass.getESuperTypes().add(this.getJavaMultiRelationshipMapping());
		javaOneToManyEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIOneToMany());
		javaManyToManyEClass.getESuperTypes().add(this.getJavaMultiRelationshipMapping());
		javaManyToManyEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIManyToMany());
		javaNullAttributeMappingEClass.getESuperTypes().add(this.getJavaAttributeMapping());
		abstractJavaTableEClass.getESuperTypes().add(theJpaJavaPackage.getJavaEObject());
		abstractJavaTableEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getITable());
		javaTableEClass.getESuperTypes().add(this.getAbstractJavaTable());
		javaSecondaryTableEClass.getESuperTypes().add(this.getAbstractJavaTable());
		javaSecondaryTableEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getISecondaryTable());
		javaJoinTableEClass.getESuperTypes().add(this.getAbstractJavaTable());
		javaJoinTableEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIJoinTable());
		javaNamedColumnEClass.getESuperTypes().add(theJpaJavaPackage.getJavaEObject());
		javaNamedColumnEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getINamedColumn());
		abstractJavaColumnEClass.getESuperTypes().add(this.getJavaNamedColumn());
		abstractJavaColumnEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIAbstractColumn());
		javaColumnEClass.getESuperTypes().add(this.getAbstractJavaColumn());
		javaColumnEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIColumn());
		javaJoinColumnEClass.getESuperTypes().add(this.getAbstractJavaColumn());
		javaJoinColumnEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIJoinColumn());
		javaOverrideEClass.getESuperTypes().add(theJpaJavaPackage.getJavaEObject());
		javaOverrideEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIOverride());
		javaAttributeOverrideEClass.getESuperTypes().add(this.getJavaOverride());
		javaAttributeOverrideEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIAttributeOverride());
		javaAssociationOverrideEClass.getESuperTypes().add(this.getJavaOverride());
		javaAssociationOverrideEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIAssociationOverride());
		javaDiscriminatorColumnEClass.getESuperTypes().add(this.getJavaNamedColumn());
		javaDiscriminatorColumnEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIDiscriminatorColumn());
		javaPrimaryKeyJoinColumnEClass.getESuperTypes().add(this.getJavaNamedColumn());
		javaPrimaryKeyJoinColumnEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIPrimaryKeyJoinColumn());
		javaGeneratedValueEClass.getESuperTypes().add(theJpaJavaPackage.getJavaEObject());
		javaGeneratedValueEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIGeneratedValue());
		javaGeneratorEClass.getESuperTypes().add(theJpaJavaPackage.getJavaEObject());
		javaGeneratorEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIGenerator());
		javaTableGeneratorEClass.getESuperTypes().add(this.getJavaGenerator());
		javaTableGeneratorEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getITableGenerator());
		javaSequenceGeneratorEClass.getESuperTypes().add(this.getJavaGenerator());
		javaSequenceGeneratorEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getISequenceGenerator());
		javaOrderByEClass.getESuperTypes().add(theJpaJavaPackage.getJavaEObject());
		javaOrderByEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIOrderBy());
		javaAbstractQueryEClass.getESuperTypes().add(theJpaJavaPackage.getJavaEObject());
		javaAbstractQueryEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIQuery());
		javaNamedQueryEClass.getESuperTypes().add(this.getJavaAbstractQuery());
		javaNamedQueryEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getINamedQuery());
		javaNamedNativeQueryEClass.getESuperTypes().add(this.getJavaAbstractQuery());
		javaNamedNativeQueryEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getINamedNativeQuery());
		javaQueryHintEClass.getESuperTypes().add(theJpaJavaPackage.getJavaEObject());
		javaQueryHintEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIQueryHint());
		javaUniqueConstraintEClass.getESuperTypes().add(theJpaJavaPackage.getJavaEObject());
		javaUniqueConstraintEClass.getESuperTypes().add(theJpaCoreMappingsPackage.getIUniqueConstraint());
		// Initialize classes and features; add operations and parameters
		initEClass(javaTypeMappingEClass, JavaTypeMapping.class, "JavaTypeMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaEntityEClass, JavaEntity.class, "JavaEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaMappedSuperclassEClass, JavaMappedSuperclass.class, "JavaMappedSuperclass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaEmbeddableEClass, JavaEmbeddable.class, "JavaEmbeddable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaNullTypeMappingEClass, JavaNullTypeMapping.class, "JavaNullTypeMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaAttributeMappingEClass, JavaAttributeMapping.class, "JavaAttributeMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaBasicEClass, JavaBasic.class, "JavaBasic", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaIdEClass, JavaId.class, "JavaId", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaTransientEClass, JavaTransient.class, "JavaTransient", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaVersionEClass, JavaVersion.class, "JavaVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaEmbeddedIdEClass, JavaEmbeddedId.class, "JavaEmbeddedId", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaEmbeddedEClass, JavaEmbedded.class, "JavaEmbedded", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaRelationshipMappingEClass, JavaRelationshipMapping.class, "JavaRelationshipMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaSingleRelationshipMappingEClass, JavaSingleRelationshipMapping.class, "JavaSingleRelationshipMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaManyToOneEClass, JavaManyToOne.class, "JavaManyToOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaOneToOneEClass, JavaOneToOne.class, "JavaOneToOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaMultiRelationshipMappingEClass, JavaMultiRelationshipMapping.class, "JavaMultiRelationshipMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaOneToManyEClass, JavaOneToMany.class, "JavaOneToMany", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaManyToManyEClass, JavaManyToMany.class, "JavaManyToMany", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaNullAttributeMappingEClass, JavaNullAttributeMapping.class, "JavaNullAttributeMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(abstractJavaTableEClass, AbstractJavaTable.class, "AbstractJavaTable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaTableEClass, JavaTable.class, "JavaTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaSecondaryTableEClass, JavaSecondaryTable.class, "JavaSecondaryTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaJoinTableEClass, JavaJoinTable.class, "JavaJoinTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaNamedColumnEClass, JavaNamedColumn.class, "JavaNamedColumn", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(abstractJavaColumnEClass, AbstractJavaColumn.class, "AbstractJavaColumn", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaColumnEClass, JavaColumn.class, "JavaColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaJoinColumnEClass, JavaJoinColumn.class, "JavaJoinColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaOverrideEClass, JavaOverride.class, "JavaOverride", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaAttributeOverrideEClass, JavaAttributeOverride.class, "JavaAttributeOverride", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaAssociationOverrideEClass, JavaAssociationOverride.class, "JavaAssociationOverride", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaDiscriminatorColumnEClass, JavaDiscriminatorColumn.class, "JavaDiscriminatorColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaPrimaryKeyJoinColumnEClass, JavaPrimaryKeyJoinColumn.class, "JavaPrimaryKeyJoinColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaGeneratedValueEClass, JavaGeneratedValue.class, "JavaGeneratedValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaGeneratorEClass, JavaGenerator.class, "JavaGenerator", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaTableGeneratorEClass, JavaTableGenerator.class, "JavaTableGenerator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaSequenceGeneratorEClass, JavaSequenceGenerator.class, "JavaSequenceGenerator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaOrderByEClass, JavaOrderBy.class, "JavaOrderBy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaAbstractQueryEClass, JavaAbstractQuery.class, "JavaAbstractQuery", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaNamedQueryEClass, JavaNamedQuery.class, "JavaNamedQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaNamedNativeQueryEClass, JavaNamedNativeQuery.class, "JavaNamedNativeQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaQueryHintEClass, JavaQueryHint.class, "JavaQueryHint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(javaUniqueConstraintEClass, JavaUniqueConstraint.class, "JavaUniqueConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity <em>Java Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaEntity()
		 * @generated
		 */
		public static final EClass JAVA_ENTITY = eINSTANCE.getJavaEntity();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaMappedSuperclass <em>Java Mapped Superclass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaMappedSuperclass
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaMappedSuperclass()
		 * @generated
		 */
		public static final EClass JAVA_MAPPED_SUPERCLASS = eINSTANCE.getJavaMappedSuperclass();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddable <em>Java Embeddable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddable
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaEmbeddable()
		 * @generated
		 */
		public static final EClass JAVA_EMBEDDABLE = eINSTANCE.getJavaEmbeddable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNullTypeMapping <em>Java Null Type Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNullTypeMapping
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNullTypeMapping()
		 * @generated
		 */
		public static final EClass JAVA_NULL_TYPE_MAPPING = eINSTANCE.getJavaNullTypeMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNullAttributeMapping <em>Java Null Attribute Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNullAttributeMapping
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNullAttributeMapping()
		 * @generated
		 */
		public static final EClass JAVA_NULL_ATTRIBUTE_MAPPING = eINSTANCE.getJavaNullAttributeMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic <em>Java Basic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaBasic()
		 * @generated
		 */
		public static final EClass JAVA_BASIC = eINSTANCE.getJavaBasic();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaId <em>Java Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaId
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaId()
		 * @generated
		 */
		public static final EClass JAVA_ID = eINSTANCE.getJavaId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTransient <em>Java Transient</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTransient
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaTransient()
		 * @generated
		 */
		public static final EClass JAVA_TRANSIENT = eINSTANCE.getJavaTransient();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaVersion <em>Java Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaVersion
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaVersion()
		 * @generated
		 */
		public static final EClass JAVA_VERSION = eINSTANCE.getJavaVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddedId <em>Java Embedded Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddedId
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaEmbeddedId()
		 * @generated
		 */
		public static final EClass JAVA_EMBEDDED_ID = eINSTANCE.getJavaEmbeddedId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbedded <em>Java Embedded</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbedded
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaEmbedded()
		 * @generated
		 */
		public static final EClass JAVA_EMBEDDED = eINSTANCE.getJavaEmbedded();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeMapping <em>Java Attribute Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeMapping
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaAttributeMapping()
		 * @generated
		 */
		public static final EClass JAVA_ATTRIBUTE_MAPPING = eINSTANCE.getJavaAttributeMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTypeMapping <em>Java Type Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTypeMapping
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaTypeMapping()
		 * @generated
		 */
		public static final EClass JAVA_TYPE_MAPPING = eINSTANCE.getJavaTypeMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaTable <em>Abstract Java Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaTable
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getAbstractJavaTable()
		 * @generated
		 */
		public static final EClass ABSTRACT_JAVA_TABLE = eINSTANCE.getAbstractJavaTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTable <em>Java Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTable
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaTable()
		 * @generated
		 */
		public static final EClass JAVA_TABLE = eINSTANCE.getJavaTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn <em>Java Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaColumn()
		 * @generated
		 */
		public static final EClass JAVA_COLUMN = eINSTANCE.getJavaColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping <em>Java Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaRelationshipMapping()
		 * @generated
		 */
		public static final EClass JAVA_RELATIONSHIP_MAPPING = eINSTANCE.getJavaRelationshipMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSingleRelationshipMapping <em>Java Single Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaSingleRelationshipMapping
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaSingleRelationshipMapping()
		 * @generated
		 */
		public static final EClass JAVA_SINGLE_RELATIONSHIP_MAPPING = eINSTANCE.getJavaSingleRelationshipMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToOne <em>Java Many To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToOne
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaManyToOne()
		 * @generated
		 */
		public static final EClass JAVA_MANY_TO_ONE = eINSTANCE.getJavaManyToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToOne <em>Java One To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToOne
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaOneToOne()
		 * @generated
		 */
		public static final EClass JAVA_ONE_TO_ONE = eINSTANCE.getJavaOneToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping <em>Java Multi Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaMultiRelationshipMapping()
		 * @generated
		 */
		public static final EClass JAVA_MULTI_RELATIONSHIP_MAPPING = eINSTANCE.getJavaMultiRelationshipMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToMany <em>Java One To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToMany
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaOneToMany()
		 * @generated
		 */
		public static final EClass JAVA_ONE_TO_MANY = eINSTANCE.getJavaOneToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToMany <em>Java Many To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToMany
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaManyToMany()
		 * @generated
		 */
		public static final EClass JAVA_MANY_TO_MANY = eINSTANCE.getJavaManyToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinTable <em>Java Join Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinTable
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaJoinTable()
		 * @generated
		 */
		public static final EClass JAVA_JOIN_TABLE = eINSTANCE.getJavaJoinTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedColumn <em>Java Named Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedColumn
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNamedColumn()
		 * @generated
		 */
		public static final EClass JAVA_NAMED_COLUMN = eINSTANCE.getJavaNamedColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn <em>Abstract Java Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getAbstractJavaColumn()
		 * @generated
		 */
		public static final EClass ABSTRACT_JAVA_COLUMN = eINSTANCE.getAbstractJavaColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinColumn <em>Java Join Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinColumn
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaJoinColumn()
		 * @generated
		 */
		public static final EClass JAVA_JOIN_COLUMN = eINSTANCE.getJavaJoinColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOverride <em>Java Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOverride
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaOverride()
		 * @generated
		 */
		public static final EClass JAVA_OVERRIDE = eINSTANCE.getJavaOverride();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeOverride <em>Java Attribute Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeOverride
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaAttributeOverride()
		 * @generated
		 */
		public static final EClass JAVA_ATTRIBUTE_OVERRIDE = eINSTANCE.getJavaAttributeOverride();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAssociationOverride <em>Java Association Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAssociationOverride
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaAssociationOverride()
		 * @generated
		 */
		public static final EClass JAVA_ASSOCIATION_OVERRIDE = eINSTANCE.getJavaAssociationOverride();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn <em>Java Discriminator Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaDiscriminatorColumn()
		 * @generated
		 */
		public static final EClass JAVA_DISCRIMINATOR_COLUMN = eINSTANCE.getJavaDiscriminatorColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaPrimaryKeyJoinColumn <em>Java Primary Key Join Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaPrimaryKeyJoinColumn
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaPrimaryKeyJoinColumn()
		 * @generated
		 */
		public static final EClass JAVA_PRIMARY_KEY_JOIN_COLUMN = eINSTANCE.getJavaPrimaryKeyJoinColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaGeneratedValue <em>Java Generated Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaGeneratedValue
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaGeneratedValue()
		 * @generated
		 */
		public static final EClass JAVA_GENERATED_VALUE = eINSTANCE.getJavaGeneratedValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaGenerator <em>Java Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaGenerator
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaGenerator()
		 * @generated
		 */
		public static final EClass JAVA_GENERATOR = eINSTANCE.getJavaGenerator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator <em>Java Table Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaTableGenerator()
		 * @generated
		 */
		public static final EClass JAVA_TABLE_GENERATOR = eINSTANCE.getJavaTableGenerator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSequenceGenerator <em>Java Sequence Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaSequenceGenerator
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaSequenceGenerator()
		 * @generated
		 */
		public static final EClass JAVA_SEQUENCE_GENERATOR = eINSTANCE.getJavaSequenceGenerator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOrderBy <em>Java Order By</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOrderBy
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaOrderBy()
		 * @generated
		 */
		public static final EClass JAVA_ORDER_BY = eINSTANCE.getJavaOrderBy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAbstractQuery <em>Java Abstract Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAbstractQuery
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaAbstractQuery()
		 * @generated
		 */
		public static final EClass JAVA_ABSTRACT_QUERY = eINSTANCE.getJavaAbstractQuery();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedQuery <em>Java Named Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedQuery
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNamedQuery()
		 * @generated
		 */
		public static final EClass JAVA_NAMED_QUERY = eINSTANCE.getJavaNamedQuery();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedNativeQuery <em>Java Named Native Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedNativeQuery
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNamedNativeQuery()
		 * @generated
		 */
		public static final EClass JAVA_NAMED_NATIVE_QUERY = eINSTANCE.getJavaNamedNativeQuery();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaQueryHint <em>Java Query Hint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaQueryHint
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaQueryHint()
		 * @generated
		 */
		public static final EClass JAVA_QUERY_HINT = eINSTANCE.getJavaQueryHint();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaUniqueConstraint <em>Java Unique Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaUniqueConstraint
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaUniqueConstraint()
		 * @generated
		 */
		public static final EClass JAVA_UNIQUE_CONSTRAINT = eINSTANCE.getJavaUniqueConstraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSecondaryTable <em>Java Secondary Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaSecondaryTable
		 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaSecondaryTable()
		 * @generated
		 */
		public static final EClass JAVA_SECONDARY_TABLE = eINSTANCE.getJavaSecondaryTable();
	}
} //JavaMappingsPackage
