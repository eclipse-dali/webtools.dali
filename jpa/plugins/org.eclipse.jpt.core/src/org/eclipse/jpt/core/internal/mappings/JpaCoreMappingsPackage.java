/*******************************************************************************
 *  Copyright (c) 2005, 2007 Oracle. All rights reserved.  This program and 
 *  the accompanying materials are made available under the terms of the 
 *  Eclipse Public License v1.0 which accompanies this distribution, and is 
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.jem.java.JavaRefPackage;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.content.java.JpaJavaPackage;
import org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.content.persistence.PersistencePackage;

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
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsFactory
 * @model kind="package"
 * @generated
 */
public class JpaCoreMappingsPackage extends EPackageImpl
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
	public static final String eNS_URI = "jpt.core.mappings.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "jpt.core.mappings";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final JpaCoreMappingsPackage eINSTANCE = org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IMappedSuperclass <em>IMapped Superclass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IMappedSuperclass
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIMappedSuperclass()
	 * @generated
	 */
	public static final int IMAPPED_SUPERCLASS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMAPPED_SUPERCLASS__NAME = JpaCorePackage.ITYPE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMAPPED_SUPERCLASS__TABLE_NAME = JpaCorePackage.ITYPE_MAPPING__TABLE_NAME;

	/**
	 * The number of structural features of the '<em>IMapped Superclass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMAPPED_SUPERCLASS_FEATURE_COUNT = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IEntity <em>IEntity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity()
	 * @generated
	 */
	public static final int IENTITY = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__NAME = JpaCorePackage.ITYPE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__TABLE_NAME = JpaCorePackage.ITYPE_MAPPING__TABLE_NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__SPECIFIED_NAME = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__DEFAULT_NAME = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__TABLE = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Specified Secondary Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__SPECIFIED_SECONDARY_TABLES = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__PRIMARY_KEY_JOIN_COLUMNS = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Specified Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Default Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Inheritance Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__INHERITANCE_STRATEGY = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Default Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__DEFAULT_DISCRIMINATOR_VALUE = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Specified Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__SPECIFIED_DISCRIMINATOR_VALUE = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__DISCRIMINATOR_VALUE = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Discriminator Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__DISCRIMINATOR_COLUMN = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__SEQUENCE_GENERATOR = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__TABLE_GENERATOR = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__ATTRIBUTE_OVERRIDES = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Specified Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Default Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__DEFAULT_ATTRIBUTE_OVERRIDES = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__ASSOCIATION_OVERRIDES = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Specified Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__SPECIFIED_ASSOCIATION_OVERRIDES = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Default Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__DEFAULT_ASSOCIATION_OVERRIDES = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__NAMED_QUERIES = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 20;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY__NAMED_NATIVE_QUERIES = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 21;

	/**
	 * The number of structural features of the '<em>IEntity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IENTITY_FEATURE_COUNT = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 22;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IEmbeddable <em>IEmbeddable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbeddable
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEmbeddable()
	 * @generated
	 */
	public static final int IEMBEDDABLE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IEMBEDDABLE__NAME = JpaCorePackage.ITYPE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IEMBEDDABLE__TABLE_NAME = JpaCorePackage.ITYPE_MAPPING__TABLE_NAME;

	/**
	 * The number of structural features of the '<em>IEmbeddable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IEMBEDDABLE_FEATURE_COUNT = JpaCorePackage.ITYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.ITable <em>ITable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.ITable
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITable()
	 * @generated
	 */
	public static final int ITABLE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE__NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE__SPECIFIED_NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE__DEFAULT_NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE__CATALOG = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE__SPECIFIED_CATALOG = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE__DEFAULT_CATALOG = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE__SCHEMA = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE__SPECIFIED_SCHEMA = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE__DEFAULT_SCHEMA = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>ITable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_FEATURE_COUNT = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.INamedColumn <em>INamed Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.INamedColumn
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINamedColumn()
	 * @generated
	 */
	public static final int INAMED_COLUMN = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_COLUMN__NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_COLUMN__SPECIFIED_NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_COLUMN__DEFAULT_NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_COLUMN__COLUMN_DEFINITION = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>INamed Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_COLUMN_FEATURE_COUNT = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn <em>IAbstract Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractColumn
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractColumn()
	 * @generated
	 */
	public static final int IABSTRACT_COLUMN = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_COLUMN__NAME = INAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_COLUMN__SPECIFIED_NAME = INAMED_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_COLUMN__DEFAULT_NAME = INAMED_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_COLUMN__COLUMN_DEFINITION = INAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_COLUMN__UNIQUE = INAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_COLUMN__NULLABLE = INAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_COLUMN__INSERTABLE = INAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_COLUMN__UPDATABLE = INAMED_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_COLUMN__TABLE = INAMED_COLUMN_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_COLUMN__SPECIFIED_TABLE = INAMED_COLUMN_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_COLUMN__DEFAULT_TABLE = INAMED_COLUMN_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>IAbstract Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_COLUMN_FEATURE_COUNT = INAMED_COLUMN_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IColumn <em>IColumn</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IColumn
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIColumn()
	 * @generated
	 */
	public static final int ICOLUMN = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__NAME = IABSTRACT_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__SPECIFIED_NAME = IABSTRACT_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__DEFAULT_NAME = IABSTRACT_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__COLUMN_DEFINITION = IABSTRACT_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__UNIQUE = IABSTRACT_COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__NULLABLE = IABSTRACT_COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__INSERTABLE = IABSTRACT_COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__UPDATABLE = IABSTRACT_COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__TABLE = IABSTRACT_COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__SPECIFIED_TABLE = IABSTRACT_COLUMN__SPECIFIED_TABLE;

	/**
	 * The feature id for the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__DEFAULT_TABLE = IABSTRACT_COLUMN__DEFAULT_TABLE;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__LENGTH = IABSTRACT_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Precision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__PRECISION = IABSTRACT_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN__SCALE = IABSTRACT_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IColumn</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN_FEATURE_COUNT = IABSTRACT_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IColumnMapping <em>IColumn Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IColumnMapping
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIColumnMapping()
	 * @generated
	 */
	public static final int ICOLUMN_MAPPING = 7;

	/**
	 * The number of structural features of the '<em>IColumn Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ICOLUMN_MAPPING_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IBasic <em>IBasic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IBasic
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIBasic()
	 * @generated
	 */
	public static final int IBASIC = 8;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IBASIC__FETCH = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IBASIC__OPTIONAL = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IBASIC__COLUMN = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IBASIC__LOB = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IBASIC__TEMPORAL = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IBASIC__ENUMERATED = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>IBasic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IBASIC_FEATURE_COUNT = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IId <em>IId</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IId
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIId()
	 * @generated
	 */
	public static final int IID = 9;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IID__COLUMN = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IID__GENERATED_VALUE = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IID__TEMPORAL = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IID__TABLE_GENERATOR = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IID__SEQUENCE_GENERATOR = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>IId</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IID_FEATURE_COUNT = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.ITransient <em>ITransient</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.ITransient
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITransient()
	 * @generated
	 */
	public static final int ITRANSIENT = 10;

	/**
	 * The number of structural features of the '<em>ITransient</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITRANSIENT_FEATURE_COUNT = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IVersion <em>IVersion</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IVersion
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIVersion()
	 * @generated
	 */
	public static final int IVERSION = 11;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IVERSION__COLUMN = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IVERSION__TEMPORAL = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IVersion</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IVERSION_FEATURE_COUNT = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IEmbeddedId <em>IEmbedded Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbeddedId
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEmbeddedId()
	 * @generated
	 */
	public static final int IEMBEDDED_ID = 12;

	/**
	 * The number of structural features of the '<em>IEmbedded Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IEMBEDDED_ID_FEATURE_COUNT = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IEmbedded <em>IEmbedded</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbedded
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEmbedded()
	 * @generated
	 */
	public static final int IEMBEDDED = 13;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IEMBEDDED__ATTRIBUTE_OVERRIDES = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IEMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IEMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IEmbedded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IEMBEDDED_FEATURE_COUNT = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping <em>IRelationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IRelationshipMapping
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIRelationshipMapping()
	 * @generated
	 */
	public static final int IRELATIONSHIP_MAPPING = 14;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IRELATIONSHIP_MAPPING__TARGET_ENTITY = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IRELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IRELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IRELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>IRelationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IRELATIONSHIP_MAPPING_FEATURE_COUNT = JpaCorePackage.IATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.INonOwningMapping <em>INon Owning Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.INonOwningMapping
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINonOwningMapping()
	 * @generated
	 */
	public static final int INON_OWNING_MAPPING = 15;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INON_OWNING_MAPPING__TARGET_ENTITY = IRELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INON_OWNING_MAPPING__SPECIFIED_TARGET_ENTITY = IRELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INON_OWNING_MAPPING__DEFAULT_TARGET_ENTITY = IRELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INON_OWNING_MAPPING__RESOLVED_TARGET_ENTITY = IRELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INON_OWNING_MAPPING__MAPPED_BY = IRELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>INon Owning Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INON_OWNING_MAPPING_FEATURE_COUNT = IRELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping <em>IMulti Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIMultiRelationshipMapping()
	 * @generated
	 */
	public static final int IMULTI_RELATIONSHIP_MAPPING = 16;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY = INON_OWNING_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMULTI_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY = INON_OWNING_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMULTI_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY = INON_OWNING_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMULTI_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY = INON_OWNING_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMULTI_RELATIONSHIP_MAPPING__MAPPED_BY = INON_OWNING_MAPPING__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMULTI_RELATIONSHIP_MAPPING__FETCH = INON_OWNING_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMULTI_RELATIONSHIP_MAPPING__JOIN_TABLE = INON_OWNING_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMULTI_RELATIONSHIP_MAPPING__ORDER_BY = INON_OWNING_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IMulti Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT = INON_OWNING_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IOneToMany <em>IOne To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IOneToMany
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIOneToMany()
	 * @generated
	 */
	public static final int IONE_TO_MANY = 17;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_MANY__TARGET_ENTITY = IMULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_MANY__SPECIFIED_TARGET_ENTITY = IMULTI_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_MANY__DEFAULT_TARGET_ENTITY = IMULTI_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_MANY__RESOLVED_TARGET_ENTITY = IMULTI_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_MANY__MAPPED_BY = IMULTI_RELATIONSHIP_MAPPING__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_MANY__FETCH = IMULTI_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_MANY__JOIN_TABLE = IMULTI_RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_MANY__ORDER_BY = IMULTI_RELATIONSHIP_MAPPING__ORDER_BY;

	/**
	 * The number of structural features of the '<em>IOne To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_MANY_FEATURE_COUNT = IMULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IManyToMany <em>IMany To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IManyToMany
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIManyToMany()
	 * @generated
	 */
	public static final int IMANY_TO_MANY = 18;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_MANY__TARGET_ENTITY = IMULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_MANY__SPECIFIED_TARGET_ENTITY = IMULTI_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_MANY__DEFAULT_TARGET_ENTITY = IMULTI_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_MANY__RESOLVED_TARGET_ENTITY = IMULTI_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_MANY__MAPPED_BY = IMULTI_RELATIONSHIP_MAPPING__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_MANY__FETCH = IMULTI_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_MANY__JOIN_TABLE = IMULTI_RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_MANY__ORDER_BY = IMULTI_RELATIONSHIP_MAPPING__ORDER_BY;

	/**
	 * The number of structural features of the '<em>IMany To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_MANY_FEATURE_COUNT = IMULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping <em>ISingle Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISingleRelationshipMapping()
	 * @generated
	 */
	public static final int ISINGLE_RELATIONSHIP_MAPPING = 19;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY = IRELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY = IRELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY = IRELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISINGLE_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY = IRELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISINGLE_RELATIONSHIP_MAPPING__FETCH = IRELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS = IRELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS = IRELATIONSHIP_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS = IRELATIONSHIP_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>ISingle Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT = IRELATIONSHIP_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IManyToOne <em>IMany To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IManyToOne
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIManyToOne()
	 * @generated
	 */
	public static final int IMANY_TO_ONE = 20;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_ONE__TARGET_ENTITY = ISINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_ONE__SPECIFIED_TARGET_ENTITY = ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_ONE__DEFAULT_TARGET_ENTITY = ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_ONE__RESOLVED_TARGET_ENTITY = ISINGLE_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_ONE__FETCH = ISINGLE_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_ONE__JOIN_COLUMNS = ISINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_ONE__SPECIFIED_JOIN_COLUMNS = ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_ONE__DEFAULT_JOIN_COLUMNS = ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS;

	/**
	 * The number of structural features of the '<em>IMany To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IMANY_TO_ONE_FEATURE_COUNT = ISINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IOneToOne <em>IOne To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IOneToOne
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIOneToOne()
	 * @generated
	 */
	public static final int IONE_TO_ONE = 21;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_ONE__TARGET_ENTITY = ISINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_ONE__SPECIFIED_TARGET_ENTITY = ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_ONE__DEFAULT_TARGET_ENTITY = ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_ONE__RESOLVED_TARGET_ENTITY = ISINGLE_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_ONE__FETCH = ISINGLE_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_ONE__JOIN_COLUMNS = ISINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_ONE__SPECIFIED_JOIN_COLUMNS = ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_ONE__DEFAULT_JOIN_COLUMNS = ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_ONE__MAPPED_BY = ISINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IOne To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IONE_TO_ONE_FEATURE_COUNT = ISINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IJoinTable <em>IJoin Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinTable
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIJoinTable()
	 * @generated
	 */
	public static final int IJOIN_TABLE = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__NAME = ITABLE__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__SPECIFIED_NAME = ITABLE__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__DEFAULT_NAME = ITABLE__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__CATALOG = ITABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__SPECIFIED_CATALOG = ITABLE__SPECIFIED_CATALOG;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__DEFAULT_CATALOG = ITABLE__DEFAULT_CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__SCHEMA = ITABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__SPECIFIED_SCHEMA = ITABLE__SPECIFIED_SCHEMA;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__DEFAULT_SCHEMA = ITABLE__DEFAULT_SCHEMA;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__JOIN_COLUMNS = ITABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__SPECIFIED_JOIN_COLUMNS = ITABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__DEFAULT_JOIN_COLUMNS = ITABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__INVERSE_JOIN_COLUMNS = ITABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Specified Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS = ITABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Default Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS = ITABLE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>IJoin Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_TABLE_FEATURE_COUNT = ITABLE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn <em>IAbstract Join Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractJoinColumn()
	 * @generated
	 */
	public static final int IABSTRACT_JOIN_COLUMN = 23;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_JOIN_COLUMN__NAME = INAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_JOIN_COLUMN__SPECIFIED_NAME = INAMED_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_JOIN_COLUMN__DEFAULT_NAME = INAMED_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_JOIN_COLUMN__COLUMN_DEFINITION = INAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_JOIN_COLUMN__REFERENCED_COLUMN_NAME = INAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME = INAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME = INAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IAbstract Join Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IABSTRACT_JOIN_COLUMN_FEATURE_COUNT = INAMED_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IJoinColumn <em>IJoin Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinColumn
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIJoinColumn()
	 * @generated
	 */
	public static final int IJOIN_COLUMN = 24;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__NAME = IABSTRACT_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__SPECIFIED_NAME = IABSTRACT_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__DEFAULT_NAME = IABSTRACT_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__COLUMN_DEFINITION = IABSTRACT_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__UNIQUE = IABSTRACT_COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__NULLABLE = IABSTRACT_COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__INSERTABLE = IABSTRACT_COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__UPDATABLE = IABSTRACT_COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__TABLE = IABSTRACT_COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__SPECIFIED_TABLE = IABSTRACT_COLUMN__SPECIFIED_TABLE;

	/**
	 * The feature id for the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__DEFAULT_TABLE = IABSTRACT_COLUMN__DEFAULT_TABLE;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__REFERENCED_COLUMN_NAME = IABSTRACT_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME = IABSTRACT_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME = IABSTRACT_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IJoin Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IJOIN_COLUMN_FEATURE_COUNT = IABSTRACT_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IOverride <em>IOverride</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IOverride
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIOverride()
	 * @generated
	 */
	public static final int IOVERRIDE = 25;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IOVERRIDE__NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IOverride</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IOVERRIDE_FEATURE_COUNT = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride <em>IAttribute Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IAttributeOverride
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAttributeOverride()
	 * @generated
	 */
	public static final int IATTRIBUTE_OVERRIDE = 26;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IATTRIBUTE_OVERRIDE__NAME = IOVERRIDE__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IATTRIBUTE_OVERRIDE__COLUMN = IOVERRIDE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IAttribute Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IATTRIBUTE_OVERRIDE_FEATURE_COUNT = IOVERRIDE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride <em>IAssociation Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IAssociationOverride
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAssociationOverride()
	 * @generated
	 */
	public static final int IASSOCIATION_OVERRIDE = 27;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IASSOCIATION_OVERRIDE__NAME = IOVERRIDE__NAME;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IASSOCIATION_OVERRIDE__JOIN_COLUMNS = IOVERRIDE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS = IOVERRIDE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS = IOVERRIDE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IAssociation Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IASSOCIATION_OVERRIDE_FEATURE_COUNT = IOVERRIDE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn <em>IDiscriminator Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIDiscriminatorColumn()
	 * @generated
	 */
	public static final int IDISCRIMINATOR_COLUMN = 28;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IDISCRIMINATOR_COLUMN__DEFAULT_NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IDISCRIMINATOR_COLUMN__SPECIFIED_NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IDISCRIMINATOR_COLUMN__NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Discriminator Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IDISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IDISCRIMINATOR_COLUMN__COLUMN_DEFINITION = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Default Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IDISCRIMINATOR_COLUMN__DEFAULT_LENGTH = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Specified Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IDISCRIMINATOR_COLUMN__SPECIFIED_LENGTH = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IDISCRIMINATOR_COLUMN__LENGTH = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>IDiscriminator Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IDISCRIMINATOR_COLUMN_FEATURE_COUNT = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable <em>ISecondary Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.ISecondaryTable
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISecondaryTable()
	 * @generated
	 */
	public static final int ISECONDARY_TABLE = 29;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISECONDARY_TABLE__NAME = ITABLE__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISECONDARY_TABLE__SPECIFIED_NAME = ITABLE__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISECONDARY_TABLE__DEFAULT_NAME = ITABLE__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISECONDARY_TABLE__CATALOG = ITABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISECONDARY_TABLE__SPECIFIED_CATALOG = ITABLE__SPECIFIED_CATALOG;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISECONDARY_TABLE__DEFAULT_CATALOG = ITABLE__DEFAULT_CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISECONDARY_TABLE__SCHEMA = ITABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISECONDARY_TABLE__SPECIFIED_SCHEMA = ITABLE__SPECIFIED_SCHEMA;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISECONDARY_TABLE__DEFAULT_SCHEMA = ITABLE__DEFAULT_SCHEMA;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS = ITABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS = ITABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS = ITABLE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>ISecondary Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISECONDARY_TABLE_FEATURE_COUNT = ITABLE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn <em>IPrimary Key Join Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIPrimaryKeyJoinColumn()
	 * @generated
	 */
	public static final int IPRIMARY_KEY_JOIN_COLUMN = 30;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IPRIMARY_KEY_JOIN_COLUMN__NAME = IABSTRACT_JOIN_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IPRIMARY_KEY_JOIN_COLUMN__SPECIFIED_NAME = IABSTRACT_JOIN_COLUMN__SPECIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IPRIMARY_KEY_JOIN_COLUMN__DEFAULT_NAME = IABSTRACT_JOIN_COLUMN__DEFAULT_NAME;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IPRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION = IABSTRACT_JOIN_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IPRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = IABSTRACT_JOIN_COLUMN__REFERENCED_COLUMN_NAME;

	/**
	 * The feature id for the '<em><b>Specified Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IPRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME = IABSTRACT_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME;

	/**
	 * The feature id for the '<em><b>Default Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IPRIMARY_KEY_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME = IABSTRACT_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME;

	/**
	 * The number of structural features of the '<em>IPrimary Key Join Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IPRIMARY_KEY_JOIN_COLUMN_FEATURE_COUNT = IABSTRACT_JOIN_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IGenerator <em>IGenerator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IGenerator
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIGenerator()
	 * @generated
	 */
	public static final int IGENERATOR = 31;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IGENERATOR__NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IGENERATOR__INITIAL_VALUE = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Specified Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IGENERATOR__SPECIFIED_INITIAL_VALUE = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Default Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IGENERATOR__DEFAULT_INITIAL_VALUE = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IGENERATOR__ALLOCATION_SIZE = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Specified Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IGENERATOR__SPECIFIED_ALLOCATION_SIZE = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Default Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IGENERATOR__DEFAULT_ALLOCATION_SIZE = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>IGenerator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IGENERATOR_FEATURE_COUNT = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator <em>ITable Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator()
	 * @generated
	 */
	public static final int ITABLE_GENERATOR = 32;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__NAME = IGENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__INITIAL_VALUE = IGENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Specified Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__SPECIFIED_INITIAL_VALUE = IGENERATOR__SPECIFIED_INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Default Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__DEFAULT_INITIAL_VALUE = IGENERATOR__DEFAULT_INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__ALLOCATION_SIZE = IGENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Specified Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__SPECIFIED_ALLOCATION_SIZE = IGENERATOR__SPECIFIED_ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Default Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__DEFAULT_ALLOCATION_SIZE = IGENERATOR__DEFAULT_ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__TABLE = IGENERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__SPECIFIED_TABLE = IGENERATOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__DEFAULT_TABLE = IGENERATOR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__CATALOG = IGENERATOR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__SPECIFIED_CATALOG = IGENERATOR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__DEFAULT_CATALOG = IGENERATOR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__SCHEMA = IGENERATOR_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__SPECIFIED_SCHEMA = IGENERATOR_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__DEFAULT_SCHEMA = IGENERATOR_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__PK_COLUMN_NAME = IGENERATOR_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Specified Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME = IGENERATOR_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Default Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__DEFAULT_PK_COLUMN_NAME = IGENERATOR_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__VALUE_COLUMN_NAME = IGENERATOR_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Specified Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME = IGENERATOR_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Default Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__DEFAULT_VALUE_COLUMN_NAME = IGENERATOR_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__PK_COLUMN_VALUE = IGENERATOR_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Specified Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE = IGENERATOR_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Default Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR__DEFAULT_PK_COLUMN_VALUE = IGENERATOR_FEATURE_COUNT + 17;

	/**
	 * The number of structural features of the '<em>ITable Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ITABLE_GENERATOR_FEATURE_COUNT = IGENERATOR_FEATURE_COUNT + 18;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.ISequenceGenerator <em>ISequence Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.ISequenceGenerator
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISequenceGenerator()
	 * @generated
	 */
	public static final int ISEQUENCE_GENERATOR = 33;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISEQUENCE_GENERATOR__NAME = IGENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISEQUENCE_GENERATOR__INITIAL_VALUE = IGENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Specified Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISEQUENCE_GENERATOR__SPECIFIED_INITIAL_VALUE = IGENERATOR__SPECIFIED_INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Default Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISEQUENCE_GENERATOR__DEFAULT_INITIAL_VALUE = IGENERATOR__DEFAULT_INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISEQUENCE_GENERATOR__ALLOCATION_SIZE = IGENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Specified Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISEQUENCE_GENERATOR__SPECIFIED_ALLOCATION_SIZE = IGENERATOR__SPECIFIED_ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Default Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISEQUENCE_GENERATOR__DEFAULT_ALLOCATION_SIZE = IGENERATOR__DEFAULT_ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISEQUENCE_GENERATOR__SEQUENCE_NAME = IGENERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specified Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME = IGENERATOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME = IGENERATOR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>ISequence Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ISEQUENCE_GENERATOR_FEATURE_COUNT = IGENERATOR_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IGeneratedValue <em>IGenerated Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IGeneratedValue
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIGeneratedValue()
	 * @generated
	 */
	public static final int IGENERATED_VALUE = 34;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IGENERATED_VALUE__STRATEGY = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IGENERATED_VALUE__GENERATOR = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IGenerated Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IGENERATED_VALUE_FEATURE_COUNT = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IOrderBy <em>IOrder By</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IOrderBy
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIOrderBy()
	 * @generated
	 */
	public static final int IORDER_BY = 35;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IORDER_BY__VALUE = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IORDER_BY__TYPE = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IOrder By</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IORDER_BY_FEATURE_COUNT = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IQuery <em>IQuery</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IQuery
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIQuery()
	 * @generated
	 */
	public static final int IQUERY = 36;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IQUERY__NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IQUERY__QUERY = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IQUERY__HINTS = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IQuery</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IQUERY_FEATURE_COUNT = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.INamedQuery <em>INamed Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.INamedQuery
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINamedQuery()
	 * @generated
	 */
	public static final int INAMED_QUERY = 37;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_QUERY__NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_QUERY__QUERY = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_QUERY__HINTS = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>INamed Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_QUERY_FEATURE_COUNT = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.INamedNativeQuery <em>INamed Native Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.INamedNativeQuery
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINamedNativeQuery()
	 * @generated
	 */
	public static final int INAMED_NATIVE_QUERY = 38;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_NATIVE_QUERY__NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_NATIVE_QUERY__QUERY = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_NATIVE_QUERY__HINTS = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Result Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_NATIVE_QUERY__RESULT_CLASS = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Result Set Mapping</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_NATIVE_QUERY__RESULT_SET_MAPPING = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>INamed Native Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INAMED_NATIVE_QUERY_FEATURE_COUNT = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.IQueryHint <em>IQuery Hint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.IQueryHint
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIQueryHint()
	 * @generated
	 */
	public static final int IQUERY_HINT = 39;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IQUERY_HINT__NAME = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IQUERY_HINT__VALUE = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IQuery Hint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IQUERY_HINT_FEATURE_COUNT = JpaCorePackage.IJPA_SOURCE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType <em>Default Eager Fetch Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getDefaultEagerFetchType()
	 * @generated
	 */
	public static final int DEFAULT_EAGER_FETCH_TYPE = 40;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType <em>Default Lazy Fetch Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getDefaultLazyFetchType()
	 * @generated
	 */
	public static final int DEFAULT_LAZY_FETCH_TYPE = 41;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean <em>Default False Boolean</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getDefaultFalseBoolean()
	 * @generated
	 */
	public static final int DEFAULT_FALSE_BOOLEAN = 42;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean <em>Default True Boolean</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getDefaultTrueBoolean()
	 * @generated
	 */
	public static final int DEFAULT_TRUE_BOOLEAN = 43;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.TemporalType <em>Temporal Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getTemporalType()
	 * @generated
	 */
	public static final int TEMPORAL_TYPE = 44;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.InheritanceType <em>Inheritance Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.InheritanceType
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getInheritanceType()
	 * @generated
	 */
	public static final int INHERITANCE_TYPE = 45;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.DiscriminatorType <em>Discriminator Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.DiscriminatorType
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getDiscriminatorType()
	 * @generated
	 */
	public static final int DISCRIMINATOR_TYPE = 46;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.GenerationType <em>Generation Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.GenerationType
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getGenerationType()
	 * @generated
	 */
	public static final int GENERATION_TYPE = 47;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.EnumType <em>Enum Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.EnumType
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getEnumType()
	 * @generated
	 */
	public static final int ENUM_TYPE = 48;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.internal.mappings.OrderingType <em>Ordering Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.internal.mappings.OrderingType
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getOrderingType()
	 * @generated
	 */
	public static final int ORDERING_TYPE = 49;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iMappedSuperclassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iEmbeddableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iNamedColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iAbstractColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iColumnMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBasicEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iIdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iTransientEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iVersionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iEmbeddedIdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iEmbeddedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iRelationshipMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iNonOwningMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iMultiRelationshipMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iOneToManyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iManyToManyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iSingleRelationshipMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iManyToOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iOneToOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iJoinTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iAbstractJoinColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iJoinColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iOverrideEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iAttributeOverrideEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iAssociationOverrideEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iDiscriminatorColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iSecondaryTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iPrimaryKeyJoinColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iTableGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iSequenceGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iGeneratedValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iOrderByEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iNamedQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iNamedNativeQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iQueryHintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum defaultEagerFetchTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum defaultLazyFetchTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum defaultFalseBooleanEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum defaultTrueBooleanEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum temporalTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum inheritanceTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum discriminatorTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum generationTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum enumTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum orderingTypeEEnum = null;

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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private JpaCoreMappingsPackage() {
		super(eNS_URI, JpaCoreMappingsFactory.eINSTANCE);
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
	public static JpaCoreMappingsPackage init() {
		if (isInited)
			return (JpaCoreMappingsPackage) EPackage.Registry.INSTANCE.getEPackage(JpaCoreMappingsPackage.eNS_URI);
		// Obtain or create and register package
		JpaCoreMappingsPackage theJpaCoreMappingsPackage = (JpaCoreMappingsPackage) (EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof JpaCoreMappingsPackage ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new JpaCoreMappingsPackage());
		isInited = true;
		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		JavaRefPackage.eINSTANCE.eClass();
		// Obtain or create and register interdependencies
		JpaCorePackage theJpaCorePackage = (JpaCorePackage) (EPackage.Registry.INSTANCE.getEPackage(JpaCorePackage.eNS_URI) instanceof JpaCorePackage ? EPackage.Registry.INSTANCE.getEPackage(JpaCorePackage.eNS_URI) : JpaCorePackage.eINSTANCE);
		JpaJavaPackage theJpaJavaPackage = (JpaJavaPackage) (EPackage.Registry.INSTANCE.getEPackage(JpaJavaPackage.eNS_URI) instanceof JpaJavaPackage ? EPackage.Registry.INSTANCE.getEPackage(JpaJavaPackage.eNS_URI) : JpaJavaPackage.eINSTANCE);
		JpaJavaMappingsPackage theJpaJavaMappingsPackage = (JpaJavaMappingsPackage) (EPackage.Registry.INSTANCE.getEPackage(JpaJavaMappingsPackage.eNS_URI) instanceof JpaJavaMappingsPackage ? EPackage.Registry.INSTANCE.getEPackage(JpaJavaMappingsPackage.eNS_URI) : JpaJavaMappingsPackage.eINSTANCE);
		OrmPackage theOrmPackage = (OrmPackage) (EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI) : OrmPackage.eINSTANCE);
		PersistencePackage thePersistencePackage = (PersistencePackage) (EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);
		// Create package meta-data objects
		theJpaCoreMappingsPackage.createPackageContents();
		theJpaCorePackage.createPackageContents();
		theJpaJavaPackage.createPackageContents();
		theJpaJavaMappingsPackage.createPackageContents();
		theOrmPackage.createPackageContents();
		thePersistencePackage.createPackageContents();
		// Initialize created meta-data
		theJpaCoreMappingsPackage.initializePackageContents();
		theJpaCorePackage.initializePackageContents();
		theJpaJavaPackage.initializePackageContents();
		theJpaJavaMappingsPackage.initializePackageContents();
		theOrmPackage.initializePackageContents();
		thePersistencePackage.initializePackageContents();
		// Mark meta-data to indicate it can't be changed
		theJpaCoreMappingsPackage.freeze();
		return theJpaCoreMappingsPackage;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IMappedSuperclass <em>IMapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IMapped Superclass</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IMappedSuperclass
	 * @generated
	 */
	public EClass getIMappedSuperclass() {
		return iMappedSuperclassEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IEntity <em>IEntity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IEntity</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity
	 * @generated
	 */
	public EClass getIEntity() {
		return iEntityEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedName <em>Specified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedName()
	 * @see #getIEntity()
	 * @generated
	 */
	public EAttribute getIEntity_SpecifiedName() {
		return (EAttribute) iEntityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultName <em>Default Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultName()
	 * @see #getIEntity()
	 * @generated
	 */
	public EAttribute getIEntity_DefaultName() {
		return (EAttribute) iEntityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Table</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getTable()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_Table() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedSecondaryTables <em>Specified Secondary Tables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Specified Secondary Tables</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedSecondaryTables()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_SpecifiedSecondaryTables() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getInheritanceStrategy <em>Inheritance Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Inheritance Strategy</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getInheritanceStrategy()
	 * @see #getIEntity()
	 * @generated
	 */
	public EAttribute getIEntity_InheritanceStrategy() {
		return (EAttribute) iEntityEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDiscriminatorColumn <em>Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Discriminator Column</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getDiscriminatorColumn()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_DiscriminatorColumn() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSequenceGenerator <em>Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sequence Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getSequenceGenerator()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_SequenceGenerator() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getTableGenerator <em>Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Table Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getTableGenerator()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_TableGenerator() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultDiscriminatorValue <em>Default Discriminator Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Discriminator Value</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultDiscriminatorValue()
	 * @see #getIEntity()
	 * @generated
	 */
	public EAttribute getIEntity_DefaultDiscriminatorValue() {
		return (EAttribute) iEntityEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedDiscriminatorValue <em>Specified Discriminator Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Discriminator Value</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedDiscriminatorValue()
	 * @see #getIEntity()
	 * @generated
	 */
	public EAttribute getIEntity_SpecifiedDiscriminatorValue() {
		return (EAttribute) iEntityEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDiscriminatorValue <em>Discriminator Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator Value</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getDiscriminatorValue()
	 * @see #getIEntity()
	 * @generated
	 */
	public EAttribute getIEntity_DiscriminatorValue() {
		return (EAttribute) iEntityEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Primary Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getPrimaryKeyJoinColumns()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_PrimaryKeyJoinColumns() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedPrimaryKeyJoinColumns <em>Specified Primary Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Specified Primary Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedPrimaryKeyJoinColumns()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_SpecifiedPrimaryKeyJoinColumns() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultPrimaryKeyJoinColumns <em>Default Primary Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Default Primary Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultPrimaryKeyJoinColumns()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_DefaultPrimaryKeyJoinColumns() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getAttributeOverrides <em>Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getAttributeOverrides()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_AttributeOverrides() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedAttributeOverrides <em>Specified Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Specified Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedAttributeOverrides()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_SpecifiedAttributeOverrides() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultAttributeOverrides <em>Default Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Default Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultAttributeOverrides()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_DefaultAttributeOverrides() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getAssociationOverrides <em>Association Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Association Overrides</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getAssociationOverrides()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_AssociationOverrides() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedAssociationOverrides <em>Specified Association Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Specified Association Overrides</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedAssociationOverrides()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_SpecifiedAssociationOverrides() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultAssociationOverrides <em>Default Association Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Default Association Overrides</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultAssociationOverrides()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_DefaultAssociationOverrides() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getNamedQueries <em>Named Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Queries</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getNamedQueries()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_NamedQueries() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getNamedNativeQueries <em>Named Native Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Native Queries</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity#getNamedNativeQueries()
	 * @see #getIEntity()
	 * @generated
	 */
	public EReference getIEntity_NamedNativeQueries() {
		return (EReference) iEntityEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IEmbeddable <em>IEmbeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IEmbeddable</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbeddable
	 * @generated
	 */
	public EClass getIEmbeddable() {
		return iEmbeddableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.ITable <em>ITable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ITable</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITable
	 * @generated
	 */
	public EClass getITable() {
		return iTableEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITable#getName()
	 * @see #getITable()
	 * @generated
	 */
	public EAttribute getITable_Name() {
		return (EAttribute) iTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITable#getSpecifiedName <em>Specified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITable#getSpecifiedName()
	 * @see #getITable()
	 * @generated
	 */
	public EAttribute getITable_SpecifiedName() {
		return (EAttribute) iTableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITable#getDefaultName <em>Default Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITable#getDefaultName()
	 * @see #getITable()
	 * @generated
	 */
	public EAttribute getITable_DefaultName() {
		return (EAttribute) iTableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITable#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITable#getCatalog()
	 * @see #getITable()
	 * @generated
	 */
	public EAttribute getITable_Catalog() {
		return (EAttribute) iTableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITable#getSpecifiedCatalog <em>Specified Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITable#getSpecifiedCatalog()
	 * @see #getITable()
	 * @generated
	 */
	public EAttribute getITable_SpecifiedCatalog() {
		return (EAttribute) iTableEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITable#getDefaultCatalog <em>Default Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITable#getDefaultCatalog()
	 * @see #getITable()
	 * @generated
	 */
	public EAttribute getITable_DefaultCatalog() {
		return (EAttribute) iTableEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITable#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITable#getSchema()
	 * @see #getITable()
	 * @generated
	 */
	public EAttribute getITable_Schema() {
		return (EAttribute) iTableEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITable#getSpecifiedSchema <em>Specified Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITable#getSpecifiedSchema()
	 * @see #getITable()
	 * @generated
	 */
	public EAttribute getITable_SpecifiedSchema() {
		return (EAttribute) iTableEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITable#getDefaultSchema <em>Default Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITable#getDefaultSchema()
	 * @see #getITable()
	 * @generated
	 */
	public EAttribute getITable_DefaultSchema() {
		return (EAttribute) iTableEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.INamedColumn <em>INamed Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>INamed Column</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedColumn
	 * @generated
	 */
	public EClass getINamedColumn() {
		return iNamedColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.INamedColumn#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedColumn#getName()
	 * @see #getINamedColumn()
	 * @generated
	 */
	public EAttribute getINamedColumn_Name() {
		return (EAttribute) iNamedColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.INamedColumn#getSpecifiedName <em>Specified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedColumn#getSpecifiedName()
	 * @see #getINamedColumn()
	 * @generated
	 */
	public EAttribute getINamedColumn_SpecifiedName() {
		return (EAttribute) iNamedColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.INamedColumn#getDefaultName <em>Default Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedColumn#getDefaultName()
	 * @see #getINamedColumn()
	 * @generated
	 */
	public EAttribute getINamedColumn_DefaultName() {
		return (EAttribute) iNamedColumnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.INamedColumn#getColumnDefinition <em>Column Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column Definition</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedColumn#getColumnDefinition()
	 * @see #getINamedColumn()
	 * @generated
	 */
	public EAttribute getINamedColumn_ColumnDefinition() {
		return (EAttribute) iNamedColumnEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn <em>IAbstract Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IAbstract Column</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractColumn
	 * @generated
	 */
	public EClass getIAbstractColumn() {
		return iAbstractColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getUnique <em>Unique</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getUnique()
	 * @see #getIAbstractColumn()
	 * @generated
	 */
	public EAttribute getIAbstractColumn_Unique() {
		return (EAttribute) iAbstractColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getNullable <em>Nullable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nullable</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getNullable()
	 * @see #getIAbstractColumn()
	 * @generated
	 */
	public EAttribute getIAbstractColumn_Nullable() {
		return (EAttribute) iAbstractColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getInsertable <em>Insertable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Insertable</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getInsertable()
	 * @see #getIAbstractColumn()
	 * @generated
	 */
	public EAttribute getIAbstractColumn_Insertable() {
		return (EAttribute) iAbstractColumnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getUpdatable <em>Updatable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Updatable</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getUpdatable()
	 * @see #getIAbstractColumn()
	 * @generated
	 */
	public EAttribute getIAbstractColumn_Updatable() {
		return (EAttribute) iAbstractColumnEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Table</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getTable()
	 * @see #getIAbstractColumn()
	 * @generated
	 */
	public EAttribute getIAbstractColumn_Table() {
		return (EAttribute) iAbstractColumnEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getSpecifiedTable <em>Specified Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Table</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getSpecifiedTable()
	 * @see #getIAbstractColumn()
	 * @generated
	 */
	public EAttribute getIAbstractColumn_SpecifiedTable() {
		return (EAttribute) iAbstractColumnEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getDefaultTable <em>Default Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Table</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getDefaultTable()
	 * @see #getIAbstractColumn()
	 * @generated
	 */
	public EAttribute getIAbstractColumn_DefaultTable() {
		return (EAttribute) iAbstractColumnEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IColumn <em>IColumn</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IColumn</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IColumn
	 * @generated
	 */
	public EClass getIColumn() {
		return iColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IColumn#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IColumn#getLength()
	 * @see #getIColumn()
	 * @generated
	 */
	public EAttribute getIColumn_Length() {
		return (EAttribute) iColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IColumn#getPrecision <em>Precision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Precision</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IColumn#getPrecision()
	 * @see #getIColumn()
	 * @generated
	 */
	public EAttribute getIColumn_Precision() {
		return (EAttribute) iColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IColumn#getScale <em>Scale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scale</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IColumn#getScale()
	 * @see #getIColumn()
	 * @generated
	 */
	public EAttribute getIColumn_Scale() {
		return (EAttribute) iColumnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IColumnMapping <em>IColumn Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IColumn Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IColumnMapping
	 * @generated
	 */
	public EClass getIColumnMapping() {
		return iColumnMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IBasic <em>IBasic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBasic</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IBasic
	 * @generated
	 */
	public EClass getIBasic() {
		return iBasicEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IBasic#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IBasic#getFetch()
	 * @see #getIBasic()
	 * @generated
	 */
	public EAttribute getIBasic_Fetch() {
		return (EAttribute) iBasicEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IBasic#getOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IBasic#getOptional()
	 * @see #getIBasic()
	 * @generated
	 */
	public EAttribute getIBasic_Optional() {
		return (EAttribute) iBasicEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.mappings.IBasic#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IBasic#getColumn()
	 * @see #getIBasic()
	 * @generated
	 */
	public EReference getIBasic_Column() {
		return (EReference) iBasicEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IBasic#isLob <em>Lob</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lob</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IBasic#isLob()
	 * @see #getIBasic()
	 * @generated
	 */
	public EAttribute getIBasic_Lob() {
		return (EAttribute) iBasicEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IBasic#getTemporal <em>Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temporal</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IBasic#getTemporal()
	 * @see #getIBasic()
	 * @generated
	 */
	public EAttribute getIBasic_Temporal() {
		return (EAttribute) iBasicEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IBasic#getEnumerated <em>Enumerated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enumerated</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IBasic#getEnumerated()
	 * @see #getIBasic()
	 * @generated
	 */
	public EAttribute getIBasic_Enumerated() {
		return (EAttribute) iBasicEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IId <em>IId</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IId</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IId
	 * @generated
	 */
	public EClass getIId() {
		return iIdEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.mappings.IId#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IId#getColumn()
	 * @see #getIId()
	 * @generated
	 */
	public EReference getIId_Column() {
		return (EReference) iIdEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.mappings.IId#getGeneratedValue <em>Generated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Generated Value</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IId#getGeneratedValue()
	 * @see #getIId()
	 * @generated
	 */
	public EReference getIId_GeneratedValue() {
		return (EReference) iIdEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IId#getTemporal <em>Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temporal</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IId#getTemporal()
	 * @see #getIId()
	 * @generated
	 */
	public EAttribute getIId_Temporal() {
		return (EAttribute) iIdEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.mappings.IId#getTableGenerator <em>Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Table Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IId#getTableGenerator()
	 * @see #getIId()
	 * @generated
	 */
	public EReference getIId_TableGenerator() {
		return (EReference) iIdEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.mappings.IId#getSequenceGenerator <em>Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sequence Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IId#getSequenceGenerator()
	 * @see #getIId()
	 * @generated
	 */
	public EReference getIId_SequenceGenerator() {
		return (EReference) iIdEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.ITransient <em>ITransient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ITransient</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITransient
	 * @generated
	 */
	public EClass getITransient() {
		return iTransientEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IVersion <em>IVersion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IVersion</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IVersion
	 * @generated
	 */
	public EClass getIVersion() {
		return iVersionEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.mappings.IVersion#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IVersion#getColumn()
	 * @see #getIVersion()
	 * @generated
	 */
	public EReference getIVersion_Column() {
		return (EReference) iVersionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IVersion#getTemporal <em>Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temporal</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IVersion#getTemporal()
	 * @see #getIVersion()
	 * @generated
	 */
	public EAttribute getIVersion_Temporal() {
		return (EAttribute) iVersionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IEmbeddedId <em>IEmbedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IEmbedded Id</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbeddedId
	 * @generated
	 */
	public EClass getIEmbeddedId() {
		return iEmbeddedIdEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IEmbedded <em>IEmbedded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IEmbedded</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbedded
	 * @generated
	 */
	public EClass getIEmbedded() {
		return iEmbeddedEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEmbedded#getAttributeOverrides <em>Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbedded#getAttributeOverrides()
	 * @see #getIEmbedded()
	 * @generated
	 */
	public EReference getIEmbedded_AttributeOverrides() {
		return (EReference) iEmbeddedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEmbedded#getSpecifiedAttributeOverrides <em>Specified Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Specified Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbedded#getSpecifiedAttributeOverrides()
	 * @see #getIEmbedded()
	 * @generated
	 */
	public EReference getIEmbedded_SpecifiedAttributeOverrides() {
		return (EReference) iEmbeddedEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IEmbedded#getDefaultAttributeOverrides <em>Default Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Default Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbedded#getDefaultAttributeOverrides()
	 * @see #getIEmbedded()
	 * @generated
	 */
	public EReference getIEmbedded_DefaultAttributeOverrides() {
		return (EReference) iEmbeddedEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping <em>IRelationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IRelationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IRelationshipMapping
	 * @generated
	 */
	public EClass getIRelationshipMapping() {
		return iRelationshipMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getTargetEntity <em>Target Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Entity</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getTargetEntity()
	 * @see #getIRelationshipMapping()
	 * @generated
	 */
	public EAttribute getIRelationshipMapping_TargetEntity() {
		return (EAttribute) iRelationshipMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getSpecifiedTargetEntity <em>Specified Target Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Target Entity</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getSpecifiedTargetEntity()
	 * @see #getIRelationshipMapping()
	 * @generated
	 */
	public EAttribute getIRelationshipMapping_SpecifiedTargetEntity() {
		return (EAttribute) iRelationshipMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getDefaultTargetEntity <em>Default Target Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Target Entity</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getDefaultTargetEntity()
	 * @see #getIRelationshipMapping()
	 * @generated
	 */
	public EAttribute getIRelationshipMapping_DefaultTargetEntity() {
		return (EAttribute) iRelationshipMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getResolvedTargetEntity <em>Resolved Target Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Resolved Target Entity</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getResolvedTargetEntity()
	 * @see #getIRelationshipMapping()
	 * @generated
	 */
	public EReference getIRelationshipMapping_ResolvedTargetEntity() {
		return (EReference) iRelationshipMappingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.INonOwningMapping <em>INon Owning Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>INon Owning Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.INonOwningMapping
	 * @generated
	 */
	public EClass getINonOwningMapping() {
		return iNonOwningMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.INonOwningMapping#getMappedBy <em>Mapped By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mapped By</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.INonOwningMapping#getMappedBy()
	 * @see #getINonOwningMapping()
	 * @generated
	 */
	public EAttribute getINonOwningMapping_MappedBy() {
		return (EAttribute) iNonOwningMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping <em>IMulti Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IMulti Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping
	 * @generated
	 */
	public EClass getIMultiRelationshipMapping() {
		return iMultiRelationshipMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping#getFetch()
	 * @see #getIMultiRelationshipMapping()
	 * @generated
	 */
	public EAttribute getIMultiRelationshipMapping_Fetch() {
		return (EAttribute) iMultiRelationshipMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping#getJoinTable <em>Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Table</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping#getJoinTable()
	 * @see #getIMultiRelationshipMapping()
	 * @generated
	 */
	public EReference getIMultiRelationshipMapping_JoinTable() {
		return (EReference) iMultiRelationshipMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping#getOrderBy <em>Order By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Order By</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping#getOrderBy()
	 * @see #getIMultiRelationshipMapping()
	 * @generated
	 */
	public EReference getIMultiRelationshipMapping_OrderBy() {
		return (EReference) iMultiRelationshipMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IOneToMany <em>IOne To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IOne To Many</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IOneToMany
	 * @generated
	 */
	public EClass getIOneToMany() {
		return iOneToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IManyToMany <em>IMany To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IMany To Many</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IManyToMany
	 * @generated
	 */
	public EClass getIManyToMany() {
		return iManyToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping <em>ISingle Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ISingle Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping
	 * @generated
	 */
	public EClass getISingleRelationshipMapping() {
		return iSingleRelationshipMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getFetch()
	 * @see #getISingleRelationshipMapping()
	 * @generated
	 */
	public EAttribute getISingleRelationshipMapping_Fetch() {
		return (EAttribute) iSingleRelationshipMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getJoinColumns <em>Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getJoinColumns()
	 * @see #getISingleRelationshipMapping()
	 * @generated
	 */
	public EReference getISingleRelationshipMapping_JoinColumns() {
		return (EReference) iSingleRelationshipMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getSpecifiedJoinColumns <em>Specified Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Specified Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getSpecifiedJoinColumns()
	 * @see #getISingleRelationshipMapping()
	 * @generated
	 */
	public EReference getISingleRelationshipMapping_SpecifiedJoinColumns() {
		return (EReference) iSingleRelationshipMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getDefaultJoinColumns <em>Default Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Default Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping#getDefaultJoinColumns()
	 * @see #getISingleRelationshipMapping()
	 * @generated
	 */
	public EReference getISingleRelationshipMapping_DefaultJoinColumns() {
		return (EReference) iSingleRelationshipMappingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IManyToOne <em>IMany To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IMany To One</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IManyToOne
	 * @generated
	 */
	public EClass getIManyToOne() {
		return iManyToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IOneToOne <em>IOne To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IOne To One</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IOneToOne
	 * @generated
	 */
	public EClass getIOneToOne() {
		return iOneToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IJoinTable <em>IJoin Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IJoin Table</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinTable
	 * @generated
	 */
	public EClass getIJoinTable() {
		return iJoinTableEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IJoinTable#getJoinColumns <em>Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinTable#getJoinColumns()
	 * @see #getIJoinTable()
	 * @generated
	 */
	public EReference getIJoinTable_JoinColumns() {
		return (EReference) iJoinTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IJoinTable#getSpecifiedJoinColumns <em>Specified Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Specified Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinTable#getSpecifiedJoinColumns()
	 * @see #getIJoinTable()
	 * @generated
	 */
	public EReference getIJoinTable_SpecifiedJoinColumns() {
		return (EReference) iJoinTableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IJoinTable#getDefaultJoinColumns <em>Default Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Default Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinTable#getDefaultJoinColumns()
	 * @see #getIJoinTable()
	 * @generated
	 */
	public EReference getIJoinTable_DefaultJoinColumns() {
		return (EReference) iJoinTableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IJoinTable#getInverseJoinColumns <em>Inverse Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inverse Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinTable#getInverseJoinColumns()
	 * @see #getIJoinTable()
	 * @generated
	 */
	public EReference getIJoinTable_InverseJoinColumns() {
		return (EReference) iJoinTableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IJoinTable#getSpecifiedInverseJoinColumns <em>Specified Inverse Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Specified Inverse Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinTable#getSpecifiedInverseJoinColumns()
	 * @see #getIJoinTable()
	 * @generated
	 */
	public EReference getIJoinTable_SpecifiedInverseJoinColumns() {
		return (EReference) iJoinTableEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IJoinTable#getDefaultInverseJoinColumns <em>Default Inverse Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Default Inverse Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinTable#getDefaultInverseJoinColumns()
	 * @see #getIJoinTable()
	 * @generated
	 */
	public EReference getIJoinTable_DefaultInverseJoinColumns() {
		return (EReference) iJoinTableEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn <em>IAbstract Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IAbstract Join Column</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn
	 * @generated
	 */
	public EClass getIAbstractJoinColumn() {
		return iAbstractJoinColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn#getReferencedColumnName <em>Referenced Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Referenced Column Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn#getReferencedColumnName()
	 * @see #getIAbstractJoinColumn()
	 * @generated
	 */
	public EAttribute getIAbstractJoinColumn_ReferencedColumnName() {
		return (EAttribute) iAbstractJoinColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn#getSpecifiedReferencedColumnName <em>Specified Referenced Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Referenced Column Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn#getSpecifiedReferencedColumnName()
	 * @see #getIAbstractJoinColumn()
	 * @generated
	 */
	public EAttribute getIAbstractJoinColumn_SpecifiedReferencedColumnName() {
		return (EAttribute) iAbstractJoinColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn#getDefaultReferencedColumnName <em>Default Referenced Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Referenced Column Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn#getDefaultReferencedColumnName()
	 * @see #getIAbstractJoinColumn()
	 * @generated
	 */
	public EAttribute getIAbstractJoinColumn_DefaultReferencedColumnName() {
		return (EAttribute) iAbstractJoinColumnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IJoinColumn <em>IJoin Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IJoin Column</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinColumn
	 * @generated
	 */
	public EClass getIJoinColumn() {
		return iJoinColumnEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IOverride <em>IOverride</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IOverride</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IOverride
	 * @generated
	 */
	public EClass getIOverride() {
		return iOverrideEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IOverride#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IOverride#getName()
	 * @see #getIOverride()
	 * @generated
	 */
	public EAttribute getIOverride_Name() {
		return (EAttribute) iOverrideEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride <em>IAttribute Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IAttribute Override</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAttributeOverride
	 * @generated
	 */
	public EClass getIAttributeOverride() {
		return iAttributeOverrideEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAttributeOverride#getColumn()
	 * @see #getIAttributeOverride()
	 * @generated
	 */
	public EReference getIAttributeOverride_Column() {
		return (EReference) iAttributeOverrideEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride <em>IAssociation Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IAssociation Override</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAssociationOverride
	 * @generated
	 */
	public EClass getIAssociationOverride() {
		return iAssociationOverrideEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride#getJoinColumns <em>Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAssociationOverride#getJoinColumns()
	 * @see #getIAssociationOverride()
	 * @generated
	 */
	public EReference getIAssociationOverride_JoinColumns() {
		return (EReference) iAssociationOverrideEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride#getSpecifiedJoinColumns <em>Specified Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Specified Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAssociationOverride#getSpecifiedJoinColumns()
	 * @see #getIAssociationOverride()
	 * @generated
	 */
	public EReference getIAssociationOverride_SpecifiedJoinColumns() {
		return (EReference) iAssociationOverrideEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride#getDefaultJoinColumns <em>Default Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Default Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IAssociationOverride#getDefaultJoinColumns()
	 * @see #getIAssociationOverride()
	 * @generated
	 */
	public EReference getIAssociationOverride_DefaultJoinColumns() {
		return (EReference) iAssociationOverrideEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn <em>IDiscriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IDiscriminator Column</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn
	 * @generated
	 */
	public EClass getIDiscriminatorColumn() {
		return iDiscriminatorColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getDefaultName <em>Default Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getDefaultName()
	 * @see #getIDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getIDiscriminatorColumn_DefaultName() {
		return (EAttribute) iDiscriminatorColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getSpecifiedName <em>Specified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getSpecifiedName()
	 * @see #getIDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getIDiscriminatorColumn_SpecifiedName() {
		return (EAttribute) iDiscriminatorColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getName()
	 * @see #getIDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getIDiscriminatorColumn_Name() {
		return (EAttribute) iDiscriminatorColumnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getDiscriminatorType <em>Discriminator Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator Type</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getDiscriminatorType()
	 * @see #getIDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getIDiscriminatorColumn_DiscriminatorType() {
		return (EAttribute) iDiscriminatorColumnEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getColumnDefinition <em>Column Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column Definition</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getColumnDefinition()
	 * @see #getIDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getIDiscriminatorColumn_ColumnDefinition() {
		return (EAttribute) iDiscriminatorColumnEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getDefaultLength <em>Default Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Length</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getDefaultLength()
	 * @see #getIDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getIDiscriminatorColumn_DefaultLength() {
		return (EAttribute) iDiscriminatorColumnEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getSpecifiedLength <em>Specified Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Length</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getSpecifiedLength()
	 * @see #getIDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getIDiscriminatorColumn_SpecifiedLength() {
		return (EAttribute) iDiscriminatorColumnEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getLength()
	 * @see #getIDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getIDiscriminatorColumn_Length() {
		return (EAttribute) iDiscriminatorColumnEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable <em>ISecondary Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ISecondary Table</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ISecondaryTable
	 * @generated
	 */
	public EClass getISecondaryTable() {
		return iSecondaryTableEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Primary Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ISecondaryTable#getPrimaryKeyJoinColumns()
	 * @see #getISecondaryTable()
	 * @generated
	 */
	public EReference getISecondaryTable_PrimaryKeyJoinColumns() {
		return (EReference) iSecondaryTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable#getSpecifiedPrimaryKeyJoinColumns <em>Specified Primary Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Specified Primary Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ISecondaryTable#getSpecifiedPrimaryKeyJoinColumns()
	 * @see #getISecondaryTable()
	 * @generated
	 */
	public EReference getISecondaryTable_SpecifiedPrimaryKeyJoinColumns() {
		return (EReference) iSecondaryTableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable#getDefaultPrimaryKeyJoinColumns <em>Default Primary Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Default Primary Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ISecondaryTable#getDefaultPrimaryKeyJoinColumns()
	 * @see #getISecondaryTable()
	 * @generated
	 */
	public EReference getISecondaryTable_DefaultPrimaryKeyJoinColumns() {
		return (EReference) iSecondaryTableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn <em>IPrimary Key Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IPrimary Key Join Column</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn
	 * @generated
	 */
	public EClass getIPrimaryKeyJoinColumn() {
		return iPrimaryKeyJoinColumnEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IGenerator <em>IGenerator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IGenerator</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IGenerator
	 * @generated
	 */
	public EClass getIGenerator() {
		return iGeneratorEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IGenerator#getName()
	 * @see #getIGenerator()
	 * @generated
	 */
	public EAttribute getIGenerator_Name() {
		return (EAttribute) iGeneratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getInitialValue <em>Initial Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial Value</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IGenerator#getInitialValue()
	 * @see #getIGenerator()
	 * @generated
	 */
	public EAttribute getIGenerator_InitialValue() {
		return (EAttribute) iGeneratorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getSpecifiedInitialValue <em>Specified Initial Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Initial Value</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IGenerator#getSpecifiedInitialValue()
	 * @see #getIGenerator()
	 * @generated
	 */
	public EAttribute getIGenerator_SpecifiedInitialValue() {
		return (EAttribute) iGeneratorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getDefaultInitialValue <em>Default Initial Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Initial Value</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IGenerator#getDefaultInitialValue()
	 * @see #getIGenerator()
	 * @generated
	 */
	public EAttribute getIGenerator_DefaultInitialValue() {
		return (EAttribute) iGeneratorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getAllocationSize <em>Allocation Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allocation Size</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IGenerator#getAllocationSize()
	 * @see #getIGenerator()
	 * @generated
	 */
	public EAttribute getIGenerator_AllocationSize() {
		return (EAttribute) iGeneratorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getSpecifiedAllocationSize <em>Specified Allocation Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Allocation Size</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IGenerator#getSpecifiedAllocationSize()
	 * @see #getIGenerator()
	 * @generated
	 */
	public EAttribute getIGenerator_SpecifiedAllocationSize() {
		return (EAttribute) iGeneratorEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getDefaultAllocationSize <em>Default Allocation Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Allocation Size</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IGenerator#getDefaultAllocationSize()
	 * @see #getIGenerator()
	 * @generated
	 */
	public EAttribute getIGenerator_DefaultAllocationSize() {
		return (EAttribute) iGeneratorEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator <em>ITable Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ITable Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator
	 * @generated
	 */
	public EClass getITableGenerator() {
		return iTableGeneratorEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Table</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getTable()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_Table() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedTable <em>Specified Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Table</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedTable()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_SpecifiedTable() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultTable <em>Default Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Table</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultTable()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_DefaultTable() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getCatalog()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_Catalog() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedCatalog <em>Specified Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedCatalog()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_SpecifiedCatalog() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultCatalog <em>Default Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Catalog</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultCatalog()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_DefaultCatalog() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSchema()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_Schema() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedSchema <em>Specified Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedSchema()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_SpecifiedSchema() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultSchema <em>Default Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Schema</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultSchema()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_DefaultSchema() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getPkColumnName <em>Pk Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pk Column Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getPkColumnName()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_PkColumnName() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedPkColumnName <em>Specified Pk Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Pk Column Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedPkColumnName()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_SpecifiedPkColumnName() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultPkColumnName <em>Default Pk Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Pk Column Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultPkColumnName()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_DefaultPkColumnName() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getValueColumnName <em>Value Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Column Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getValueColumnName()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_ValueColumnName() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedValueColumnName <em>Specified Value Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Value Column Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedValueColumnName()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_SpecifiedValueColumnName() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultValueColumnName <em>Default Value Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Value Column Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultValueColumnName()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_DefaultValueColumnName() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getPkColumnValue <em>Pk Column Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pk Column Value</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getPkColumnValue()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_PkColumnValue() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedPkColumnValue <em>Specified Pk Column Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Pk Column Value</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedPkColumnValue()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_SpecifiedPkColumnValue() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultPkColumnValue <em>Default Pk Column Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Pk Column Value</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultPkColumnValue()
	 * @see #getITableGenerator()
	 * @generated
	 */
	public EAttribute getITableGenerator_DefaultPkColumnValue() {
		return (EAttribute) iTableGeneratorEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.ISequenceGenerator <em>ISequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ISequence Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ISequenceGenerator
	 * @generated
	 */
	public EClass getISequenceGenerator() {
		return iSequenceGeneratorEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ISequenceGenerator#getSequenceName <em>Sequence Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sequence Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ISequenceGenerator#getSequenceName()
	 * @see #getISequenceGenerator()
	 * @generated
	 */
	public EAttribute getISequenceGenerator_SequenceName() {
		return (EAttribute) iSequenceGeneratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ISequenceGenerator#getSpecifiedSequenceName <em>Specified Sequence Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Sequence Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ISequenceGenerator#getSpecifiedSequenceName()
	 * @see #getISequenceGenerator()
	 * @generated
	 */
	public EAttribute getISequenceGenerator_SpecifiedSequenceName() {
		return (EAttribute) iSequenceGeneratorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.ISequenceGenerator#getDefaultSequenceName <em>Default Sequence Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Sequence Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.ISequenceGenerator#getDefaultSequenceName()
	 * @see #getISequenceGenerator()
	 * @generated
	 */
	public EAttribute getISequenceGenerator_DefaultSequenceName() {
		return (EAttribute) iSequenceGeneratorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IGeneratedValue <em>IGenerated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IGenerated Value</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IGeneratedValue
	 * @generated
	 */
	public EClass getIGeneratedValue() {
		return iGeneratedValueEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IGeneratedValue#getStrategy <em>Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strategy</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IGeneratedValue#getStrategy()
	 * @see #getIGeneratedValue()
	 * @generated
	 */
	public EAttribute getIGeneratedValue_Strategy() {
		return (EAttribute) iGeneratedValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IGeneratedValue#getGenerator <em>Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generator</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IGeneratedValue#getGenerator()
	 * @see #getIGeneratedValue()
	 * @generated
	 */
	public EAttribute getIGeneratedValue_Generator() {
		return (EAttribute) iGeneratedValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IOrderBy <em>IOrder By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IOrder By</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IOrderBy
	 * @generated
	 */
	public EClass getIOrderBy() {
		return iOrderByEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IOrderBy#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IOrderBy#getValue()
	 * @see #getIOrderBy()
	 * @generated
	 */
	public EAttribute getIOrderBy_Value() {
		return (EAttribute) iOrderByEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IOrderBy#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IOrderBy#getType()
	 * @see #getIOrderBy()
	 * @generated
	 */
	public EAttribute getIOrderBy_Type() {
		return (EAttribute) iOrderByEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IQuery <em>IQuery</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IQuery</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IQuery
	 * @generated
	 */
	public EClass getIQuery() {
		return iQueryEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IQuery#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IQuery#getName()
	 * @see #getIQuery()
	 * @generated
	 */
	public EAttribute getIQuery_Name() {
		return (EAttribute) iQueryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IQuery#getQuery <em>Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Query</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IQuery#getQuery()
	 * @see #getIQuery()
	 * @generated
	 */
	public EAttribute getIQuery_Query() {
		return (EAttribute) iQueryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.internal.mappings.IQuery#getHints <em>Hints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Hints</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IQuery#getHints()
	 * @see #getIQuery()
	 * @generated
	 */
	public EReference getIQuery_Hints() {
		return (EReference) iQueryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.INamedQuery <em>INamed Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>INamed Query</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedQuery
	 * @generated
	 */
	public EClass getINamedQuery() {
		return iNamedQueryEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.INamedNativeQuery <em>INamed Native Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>INamed Native Query</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedNativeQuery
	 * @generated
	 */
	public EClass getINamedNativeQuery() {
		return iNamedNativeQueryEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.INamedNativeQuery#getResultClass <em>Result Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Class</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedNativeQuery#getResultClass()
	 * @see #getINamedNativeQuery()
	 * @generated
	 */
	public EAttribute getINamedNativeQuery_ResultClass() {
		return (EAttribute) iNamedNativeQueryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.INamedNativeQuery#getResultSetMapping <em>Result Set Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Set Mapping</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedNativeQuery#getResultSetMapping()
	 * @see #getINamedNativeQuery()
	 * @generated
	 */
	public EAttribute getINamedNativeQuery_ResultSetMapping() {
		return (EAttribute) iNamedNativeQueryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.internal.mappings.IQueryHint <em>IQuery Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IQuery Hint</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IQueryHint
	 * @generated
	 */
	public EClass getIQueryHint() {
		return iQueryHintEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IQueryHint#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IQueryHint#getName()
	 * @see #getIQueryHint()
	 * @generated
	 */
	public EAttribute getIQueryHint_Name() {
		return (EAttribute) iQueryHintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.internal.mappings.IQueryHint#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.IQueryHint#getValue()
	 * @see #getIQueryHint()
	 * @generated
	 */
	public EAttribute getIQueryHint_Value() {
		return (EAttribute) iQueryHintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType <em>Default Eager Fetch Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Default Eager Fetch Type</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType
	 * @generated
	 */
	public EEnum getDefaultEagerFetchType() {
		return defaultEagerFetchTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType <em>Default Lazy Fetch Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Default Lazy Fetch Type</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType
	 * @generated
	 */
	public EEnum getDefaultLazyFetchType() {
		return defaultLazyFetchTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean <em>Default False Boolean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Default False Boolean</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean
	 * @generated
	 */
	public EEnum getDefaultFalseBoolean() {
		return defaultFalseBooleanEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean <em>Default True Boolean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Default True Boolean</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @generated
	 */
	public EEnum getDefaultTrueBoolean() {
		return defaultTrueBooleanEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.mappings.TemporalType <em>Temporal Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Temporal Type</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
	 * @generated
	 */
	public EEnum getTemporalType() {
		return temporalTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.mappings.InheritanceType <em>Inheritance Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Inheritance Type</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.InheritanceType
	 * @generated
	 */
	public EEnum getInheritanceType() {
		return inheritanceTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.mappings.DiscriminatorType <em>Discriminator Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Discriminator Type</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.DiscriminatorType
	 * @generated
	 */
	public EEnum getDiscriminatorType() {
		return discriminatorTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.mappings.GenerationType <em>Generation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Generation Type</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.GenerationType
	 * @generated
	 */
	public EEnum getGenerationType() {
		return generationTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.mappings.EnumType <em>Enum Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Enum Type</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.EnumType
	 * @generated
	 */
	public EEnum getEnumType() {
		return enumTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.internal.mappings.OrderingType <em>Ordering Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Ordering Type</em>'.
	 * @see org.eclipse.jpt.core.internal.mappings.OrderingType
	 * @generated
	 */
	public EEnum getOrderingType() {
		return orderingTypeEEnum;
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public JpaCoreMappingsFactory getJpaCoreMappingsFactory() {
		return (JpaCoreMappingsFactory) getEFactoryInstance();
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
		iMappedSuperclassEClass = createEClass(IMAPPED_SUPERCLASS);
		iEntityEClass = createEClass(IENTITY);
		createEAttribute(iEntityEClass, IENTITY__SPECIFIED_NAME);
		createEAttribute(iEntityEClass, IENTITY__DEFAULT_NAME);
		createEReference(iEntityEClass, IENTITY__TABLE);
		createEReference(iEntityEClass, IENTITY__SPECIFIED_SECONDARY_TABLES);
		createEReference(iEntityEClass, IENTITY__PRIMARY_KEY_JOIN_COLUMNS);
		createEReference(iEntityEClass, IENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS);
		createEReference(iEntityEClass, IENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS);
		createEAttribute(iEntityEClass, IENTITY__INHERITANCE_STRATEGY);
		createEAttribute(iEntityEClass, IENTITY__DEFAULT_DISCRIMINATOR_VALUE);
		createEAttribute(iEntityEClass, IENTITY__SPECIFIED_DISCRIMINATOR_VALUE);
		createEAttribute(iEntityEClass, IENTITY__DISCRIMINATOR_VALUE);
		createEReference(iEntityEClass, IENTITY__DISCRIMINATOR_COLUMN);
		createEReference(iEntityEClass, IENTITY__SEQUENCE_GENERATOR);
		createEReference(iEntityEClass, IENTITY__TABLE_GENERATOR);
		createEReference(iEntityEClass, IENTITY__ATTRIBUTE_OVERRIDES);
		createEReference(iEntityEClass, IENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES);
		createEReference(iEntityEClass, IENTITY__DEFAULT_ATTRIBUTE_OVERRIDES);
		createEReference(iEntityEClass, IENTITY__ASSOCIATION_OVERRIDES);
		createEReference(iEntityEClass, IENTITY__SPECIFIED_ASSOCIATION_OVERRIDES);
		createEReference(iEntityEClass, IENTITY__DEFAULT_ASSOCIATION_OVERRIDES);
		createEReference(iEntityEClass, IENTITY__NAMED_QUERIES);
		createEReference(iEntityEClass, IENTITY__NAMED_NATIVE_QUERIES);
		iEmbeddableEClass = createEClass(IEMBEDDABLE);
		iTableEClass = createEClass(ITABLE);
		createEAttribute(iTableEClass, ITABLE__NAME);
		createEAttribute(iTableEClass, ITABLE__SPECIFIED_NAME);
		createEAttribute(iTableEClass, ITABLE__DEFAULT_NAME);
		createEAttribute(iTableEClass, ITABLE__CATALOG);
		createEAttribute(iTableEClass, ITABLE__SPECIFIED_CATALOG);
		createEAttribute(iTableEClass, ITABLE__DEFAULT_CATALOG);
		createEAttribute(iTableEClass, ITABLE__SCHEMA);
		createEAttribute(iTableEClass, ITABLE__SPECIFIED_SCHEMA);
		createEAttribute(iTableEClass, ITABLE__DEFAULT_SCHEMA);
		iNamedColumnEClass = createEClass(INAMED_COLUMN);
		createEAttribute(iNamedColumnEClass, INAMED_COLUMN__NAME);
		createEAttribute(iNamedColumnEClass, INAMED_COLUMN__SPECIFIED_NAME);
		createEAttribute(iNamedColumnEClass, INAMED_COLUMN__DEFAULT_NAME);
		createEAttribute(iNamedColumnEClass, INAMED_COLUMN__COLUMN_DEFINITION);
		iAbstractColumnEClass = createEClass(IABSTRACT_COLUMN);
		createEAttribute(iAbstractColumnEClass, IABSTRACT_COLUMN__UNIQUE);
		createEAttribute(iAbstractColumnEClass, IABSTRACT_COLUMN__NULLABLE);
		createEAttribute(iAbstractColumnEClass, IABSTRACT_COLUMN__INSERTABLE);
		createEAttribute(iAbstractColumnEClass, IABSTRACT_COLUMN__UPDATABLE);
		createEAttribute(iAbstractColumnEClass, IABSTRACT_COLUMN__TABLE);
		createEAttribute(iAbstractColumnEClass, IABSTRACT_COLUMN__SPECIFIED_TABLE);
		createEAttribute(iAbstractColumnEClass, IABSTRACT_COLUMN__DEFAULT_TABLE);
		iColumnEClass = createEClass(ICOLUMN);
		createEAttribute(iColumnEClass, ICOLUMN__LENGTH);
		createEAttribute(iColumnEClass, ICOLUMN__PRECISION);
		createEAttribute(iColumnEClass, ICOLUMN__SCALE);
		iColumnMappingEClass = createEClass(ICOLUMN_MAPPING);
		iBasicEClass = createEClass(IBASIC);
		createEAttribute(iBasicEClass, IBASIC__FETCH);
		createEAttribute(iBasicEClass, IBASIC__OPTIONAL);
		createEReference(iBasicEClass, IBASIC__COLUMN);
		createEAttribute(iBasicEClass, IBASIC__LOB);
		createEAttribute(iBasicEClass, IBASIC__TEMPORAL);
		createEAttribute(iBasicEClass, IBASIC__ENUMERATED);
		iIdEClass = createEClass(IID);
		createEReference(iIdEClass, IID__COLUMN);
		createEReference(iIdEClass, IID__GENERATED_VALUE);
		createEAttribute(iIdEClass, IID__TEMPORAL);
		createEReference(iIdEClass, IID__TABLE_GENERATOR);
		createEReference(iIdEClass, IID__SEQUENCE_GENERATOR);
		iTransientEClass = createEClass(ITRANSIENT);
		iVersionEClass = createEClass(IVERSION);
		createEReference(iVersionEClass, IVERSION__COLUMN);
		createEAttribute(iVersionEClass, IVERSION__TEMPORAL);
		iEmbeddedIdEClass = createEClass(IEMBEDDED_ID);
		iEmbeddedEClass = createEClass(IEMBEDDED);
		createEReference(iEmbeddedEClass, IEMBEDDED__ATTRIBUTE_OVERRIDES);
		createEReference(iEmbeddedEClass, IEMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES);
		createEReference(iEmbeddedEClass, IEMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES);
		iRelationshipMappingEClass = createEClass(IRELATIONSHIP_MAPPING);
		createEAttribute(iRelationshipMappingEClass, IRELATIONSHIP_MAPPING__TARGET_ENTITY);
		createEAttribute(iRelationshipMappingEClass, IRELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY);
		createEAttribute(iRelationshipMappingEClass, IRELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY);
		createEReference(iRelationshipMappingEClass, IRELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY);
		iNonOwningMappingEClass = createEClass(INON_OWNING_MAPPING);
		createEAttribute(iNonOwningMappingEClass, INON_OWNING_MAPPING__MAPPED_BY);
		iMultiRelationshipMappingEClass = createEClass(IMULTI_RELATIONSHIP_MAPPING);
		createEAttribute(iMultiRelationshipMappingEClass, IMULTI_RELATIONSHIP_MAPPING__FETCH);
		createEReference(iMultiRelationshipMappingEClass, IMULTI_RELATIONSHIP_MAPPING__JOIN_TABLE);
		createEReference(iMultiRelationshipMappingEClass, IMULTI_RELATIONSHIP_MAPPING__ORDER_BY);
		iOneToManyEClass = createEClass(IONE_TO_MANY);
		iManyToManyEClass = createEClass(IMANY_TO_MANY);
		iSingleRelationshipMappingEClass = createEClass(ISINGLE_RELATIONSHIP_MAPPING);
		createEAttribute(iSingleRelationshipMappingEClass, ISINGLE_RELATIONSHIP_MAPPING__FETCH);
		createEReference(iSingleRelationshipMappingEClass, ISINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS);
		createEReference(iSingleRelationshipMappingEClass, ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS);
		createEReference(iSingleRelationshipMappingEClass, ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS);
		iManyToOneEClass = createEClass(IMANY_TO_ONE);
		iOneToOneEClass = createEClass(IONE_TO_ONE);
		iJoinTableEClass = createEClass(IJOIN_TABLE);
		createEReference(iJoinTableEClass, IJOIN_TABLE__JOIN_COLUMNS);
		createEReference(iJoinTableEClass, IJOIN_TABLE__SPECIFIED_JOIN_COLUMNS);
		createEReference(iJoinTableEClass, IJOIN_TABLE__DEFAULT_JOIN_COLUMNS);
		createEReference(iJoinTableEClass, IJOIN_TABLE__INVERSE_JOIN_COLUMNS);
		createEReference(iJoinTableEClass, IJOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS);
		createEReference(iJoinTableEClass, IJOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS);
		iAbstractJoinColumnEClass = createEClass(IABSTRACT_JOIN_COLUMN);
		createEAttribute(iAbstractJoinColumnEClass, IABSTRACT_JOIN_COLUMN__REFERENCED_COLUMN_NAME);
		createEAttribute(iAbstractJoinColumnEClass, IABSTRACT_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME);
		createEAttribute(iAbstractJoinColumnEClass, IABSTRACT_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME);
		iJoinColumnEClass = createEClass(IJOIN_COLUMN);
		iOverrideEClass = createEClass(IOVERRIDE);
		createEAttribute(iOverrideEClass, IOVERRIDE__NAME);
		iAttributeOverrideEClass = createEClass(IATTRIBUTE_OVERRIDE);
		createEReference(iAttributeOverrideEClass, IATTRIBUTE_OVERRIDE__COLUMN);
		iAssociationOverrideEClass = createEClass(IASSOCIATION_OVERRIDE);
		createEReference(iAssociationOverrideEClass, IASSOCIATION_OVERRIDE__JOIN_COLUMNS);
		createEReference(iAssociationOverrideEClass, IASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS);
		createEReference(iAssociationOverrideEClass, IASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS);
		iDiscriminatorColumnEClass = createEClass(IDISCRIMINATOR_COLUMN);
		createEAttribute(iDiscriminatorColumnEClass, IDISCRIMINATOR_COLUMN__DEFAULT_NAME);
		createEAttribute(iDiscriminatorColumnEClass, IDISCRIMINATOR_COLUMN__SPECIFIED_NAME);
		createEAttribute(iDiscriminatorColumnEClass, IDISCRIMINATOR_COLUMN__NAME);
		createEAttribute(iDiscriminatorColumnEClass, IDISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE);
		createEAttribute(iDiscriminatorColumnEClass, IDISCRIMINATOR_COLUMN__COLUMN_DEFINITION);
		createEAttribute(iDiscriminatorColumnEClass, IDISCRIMINATOR_COLUMN__DEFAULT_LENGTH);
		createEAttribute(iDiscriminatorColumnEClass, IDISCRIMINATOR_COLUMN__SPECIFIED_LENGTH);
		createEAttribute(iDiscriminatorColumnEClass, IDISCRIMINATOR_COLUMN__LENGTH);
		iSecondaryTableEClass = createEClass(ISECONDARY_TABLE);
		createEReference(iSecondaryTableEClass, ISECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS);
		createEReference(iSecondaryTableEClass, ISECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS);
		createEReference(iSecondaryTableEClass, ISECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS);
		iPrimaryKeyJoinColumnEClass = createEClass(IPRIMARY_KEY_JOIN_COLUMN);
		iGeneratorEClass = createEClass(IGENERATOR);
		createEAttribute(iGeneratorEClass, IGENERATOR__NAME);
		createEAttribute(iGeneratorEClass, IGENERATOR__INITIAL_VALUE);
		createEAttribute(iGeneratorEClass, IGENERATOR__SPECIFIED_INITIAL_VALUE);
		createEAttribute(iGeneratorEClass, IGENERATOR__DEFAULT_INITIAL_VALUE);
		createEAttribute(iGeneratorEClass, IGENERATOR__ALLOCATION_SIZE);
		createEAttribute(iGeneratorEClass, IGENERATOR__SPECIFIED_ALLOCATION_SIZE);
		createEAttribute(iGeneratorEClass, IGENERATOR__DEFAULT_ALLOCATION_SIZE);
		iTableGeneratorEClass = createEClass(ITABLE_GENERATOR);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__TABLE);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__SPECIFIED_TABLE);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__DEFAULT_TABLE);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__CATALOG);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__SPECIFIED_CATALOG);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__DEFAULT_CATALOG);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__SCHEMA);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__SPECIFIED_SCHEMA);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__DEFAULT_SCHEMA);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__PK_COLUMN_NAME);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__DEFAULT_PK_COLUMN_NAME);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__VALUE_COLUMN_NAME);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__DEFAULT_VALUE_COLUMN_NAME);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__PK_COLUMN_VALUE);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE);
		createEAttribute(iTableGeneratorEClass, ITABLE_GENERATOR__DEFAULT_PK_COLUMN_VALUE);
		iSequenceGeneratorEClass = createEClass(ISEQUENCE_GENERATOR);
		createEAttribute(iSequenceGeneratorEClass, ISEQUENCE_GENERATOR__SEQUENCE_NAME);
		createEAttribute(iSequenceGeneratorEClass, ISEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME);
		createEAttribute(iSequenceGeneratorEClass, ISEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME);
		iGeneratedValueEClass = createEClass(IGENERATED_VALUE);
		createEAttribute(iGeneratedValueEClass, IGENERATED_VALUE__STRATEGY);
		createEAttribute(iGeneratedValueEClass, IGENERATED_VALUE__GENERATOR);
		iOrderByEClass = createEClass(IORDER_BY);
		createEAttribute(iOrderByEClass, IORDER_BY__VALUE);
		createEAttribute(iOrderByEClass, IORDER_BY__TYPE);
		iQueryEClass = createEClass(IQUERY);
		createEAttribute(iQueryEClass, IQUERY__NAME);
		createEAttribute(iQueryEClass, IQUERY__QUERY);
		createEReference(iQueryEClass, IQUERY__HINTS);
		iNamedQueryEClass = createEClass(INAMED_QUERY);
		iNamedNativeQueryEClass = createEClass(INAMED_NATIVE_QUERY);
		createEAttribute(iNamedNativeQueryEClass, INAMED_NATIVE_QUERY__RESULT_CLASS);
		createEAttribute(iNamedNativeQueryEClass, INAMED_NATIVE_QUERY__RESULT_SET_MAPPING);
		iQueryHintEClass = createEClass(IQUERY_HINT);
		createEAttribute(iQueryHintEClass, IQUERY_HINT__NAME);
		createEAttribute(iQueryHintEClass, IQUERY_HINT__VALUE);
		// Create enums
		defaultEagerFetchTypeEEnum = createEEnum(DEFAULT_EAGER_FETCH_TYPE);
		defaultLazyFetchTypeEEnum = createEEnum(DEFAULT_LAZY_FETCH_TYPE);
		defaultFalseBooleanEEnum = createEEnum(DEFAULT_FALSE_BOOLEAN);
		defaultTrueBooleanEEnum = createEEnum(DEFAULT_TRUE_BOOLEAN);
		temporalTypeEEnum = createEEnum(TEMPORAL_TYPE);
		inheritanceTypeEEnum = createEEnum(INHERITANCE_TYPE);
		discriminatorTypeEEnum = createEEnum(DISCRIMINATOR_TYPE);
		generationTypeEEnum = createEEnum(GENERATION_TYPE);
		enumTypeEEnum = createEEnum(ENUM_TYPE);
		orderingTypeEEnum = createEEnum(ORDERING_TYPE);
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
		JpaCorePackage theJpaCorePackage = (JpaCorePackage) EPackage.Registry.INSTANCE.getEPackage(JpaCorePackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		// Create type parameters
		// Set bounds for type parameters
		// Add supertypes to classes
		iMappedSuperclassEClass.getESuperTypes().add(theJpaCorePackage.getITypeMapping());
		iEntityEClass.getESuperTypes().add(theJpaCorePackage.getITypeMapping());
		iEmbeddableEClass.getESuperTypes().add(theJpaCorePackage.getITypeMapping());
		iTableEClass.getESuperTypes().add(theJpaCorePackage.getIJpaSourceObject());
		iNamedColumnEClass.getESuperTypes().add(theJpaCorePackage.getIJpaSourceObject());
		iAbstractColumnEClass.getESuperTypes().add(this.getINamedColumn());
		iColumnEClass.getESuperTypes().add(this.getIAbstractColumn());
		iBasicEClass.getESuperTypes().add(theJpaCorePackage.getIAttributeMapping());
		iBasicEClass.getESuperTypes().add(this.getIColumnMapping());
		iIdEClass.getESuperTypes().add(theJpaCorePackage.getIAttributeMapping());
		iIdEClass.getESuperTypes().add(this.getIColumnMapping());
		iTransientEClass.getESuperTypes().add(theJpaCorePackage.getIAttributeMapping());
		iVersionEClass.getESuperTypes().add(theJpaCorePackage.getIAttributeMapping());
		iVersionEClass.getESuperTypes().add(this.getIColumnMapping());
		iEmbeddedIdEClass.getESuperTypes().add(theJpaCorePackage.getIAttributeMapping());
		iEmbeddedEClass.getESuperTypes().add(theJpaCorePackage.getIAttributeMapping());
		iRelationshipMappingEClass.getESuperTypes().add(theJpaCorePackage.getIAttributeMapping());
		iNonOwningMappingEClass.getESuperTypes().add(this.getIRelationshipMapping());
		iMultiRelationshipMappingEClass.getESuperTypes().add(this.getINonOwningMapping());
		iOneToManyEClass.getESuperTypes().add(this.getIMultiRelationshipMapping());
		iManyToManyEClass.getESuperTypes().add(this.getIMultiRelationshipMapping());
		iSingleRelationshipMappingEClass.getESuperTypes().add(this.getIRelationshipMapping());
		iManyToOneEClass.getESuperTypes().add(this.getISingleRelationshipMapping());
		iOneToOneEClass.getESuperTypes().add(this.getISingleRelationshipMapping());
		iOneToOneEClass.getESuperTypes().add(this.getINonOwningMapping());
		iJoinTableEClass.getESuperTypes().add(this.getITable());
		iAbstractJoinColumnEClass.getESuperTypes().add(this.getINamedColumn());
		iJoinColumnEClass.getESuperTypes().add(this.getIAbstractColumn());
		iJoinColumnEClass.getESuperTypes().add(this.getIAbstractJoinColumn());
		iOverrideEClass.getESuperTypes().add(theJpaCorePackage.getIJpaSourceObject());
		iAttributeOverrideEClass.getESuperTypes().add(this.getIOverride());
		iAttributeOverrideEClass.getESuperTypes().add(this.getIColumnMapping());
		iAssociationOverrideEClass.getESuperTypes().add(this.getIOverride());
		iDiscriminatorColumnEClass.getESuperTypes().add(theJpaCorePackage.getIJpaSourceObject());
		iSecondaryTableEClass.getESuperTypes().add(this.getITable());
		iPrimaryKeyJoinColumnEClass.getESuperTypes().add(this.getIAbstractJoinColumn());
		iGeneratorEClass.getESuperTypes().add(theJpaCorePackage.getIJpaSourceObject());
		iTableGeneratorEClass.getESuperTypes().add(this.getIGenerator());
		iSequenceGeneratorEClass.getESuperTypes().add(this.getIGenerator());
		iGeneratedValueEClass.getESuperTypes().add(theJpaCorePackage.getIJpaSourceObject());
		iOrderByEClass.getESuperTypes().add(theJpaCorePackage.getIJpaSourceObject());
		iQueryEClass.getESuperTypes().add(theJpaCorePackage.getIJpaSourceObject());
		iNamedQueryEClass.getESuperTypes().add(theJpaCorePackage.getIJpaSourceObject());
		iNamedQueryEClass.getESuperTypes().add(this.getIQuery());
		iNamedNativeQueryEClass.getESuperTypes().add(theJpaCorePackage.getIJpaSourceObject());
		iNamedNativeQueryEClass.getESuperTypes().add(this.getIQuery());
		iQueryHintEClass.getESuperTypes().add(theJpaCorePackage.getIJpaSourceObject());
		// Initialize classes and features; add operations and parameters
		initEClass(iMappedSuperclassEClass, IMappedSuperclass.class, "IMappedSuperclass", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iEntityEClass, IEntity.class, "IEntity", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIEntity_SpecifiedName(), ecorePackage.getEString(), "specifiedName", null, 0, 1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIEntity_DefaultName(), ecorePackage.getEString(), "defaultName", null, 0, 1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_Table(), this.getITable(), null, "table", null, 1, 1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_SpecifiedSecondaryTables(), this.getISecondaryTable(), null, "specifiedSecondaryTables", null, 0, -1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_PrimaryKeyJoinColumns(), this.getIPrimaryKeyJoinColumn(), null, "primaryKeyJoinColumns", null, 0, -1, IEntity.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_SpecifiedPrimaryKeyJoinColumns(), this.getIPrimaryKeyJoinColumn(), null, "specifiedPrimaryKeyJoinColumns", null, 0, -1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_DefaultPrimaryKeyJoinColumns(), this.getIPrimaryKeyJoinColumn(), null, "defaultPrimaryKeyJoinColumns", null, 0, -1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIEntity_InheritanceStrategy(), this.getInheritanceType(), "inheritanceStrategy", null, 0, 1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIEntity_DefaultDiscriminatorValue(), theEcorePackage.getEString(), "defaultDiscriminatorValue", null, 0, 1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIEntity_SpecifiedDiscriminatorValue(), theEcorePackage.getEString(), "specifiedDiscriminatorValue", null, 0, 1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIEntity_DiscriminatorValue(), theEcorePackage.getEString(), "discriminatorValue", null, 0, 1, IEntity.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_DiscriminatorColumn(), this.getIDiscriminatorColumn(), null, "discriminatorColumn", null, 0, 1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_SequenceGenerator(), this.getISequenceGenerator(), null, "sequenceGenerator", null, 0, 1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_TableGenerator(), this.getITableGenerator(), null, "tableGenerator", null, 0, 1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_AttributeOverrides(), this.getIAttributeOverride(), null, "attributeOverrides", null, 0, -1, IEntity.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_SpecifiedAttributeOverrides(), this.getIAttributeOverride(), null, "specifiedAttributeOverrides", null, 0, -1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_DefaultAttributeOverrides(), this.getIAttributeOverride(), null, "defaultAttributeOverrides", null, 0, -1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_AssociationOverrides(), this.getIAssociationOverride(), null, "associationOverrides", null, 0, -1, IEntity.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_SpecifiedAssociationOverrides(), this.getIAssociationOverride(), null, "specifiedAssociationOverrides", null, 0, -1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_DefaultAssociationOverrides(), this.getIAssociationOverride(), null, "defaultAssociationOverrides", null, 0, -1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_NamedQueries(), this.getINamedQuery(), null, "namedQueries", null, 0, -1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEntity_NamedNativeQueries(), this.getINamedNativeQuery(), null, "namedNativeQueries", null, 0, -1, IEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		addEOperation(iEntityEClass, ecorePackage.getEBoolean(), "discriminatorValueIsAllowed", 0, 1);
		addEOperation(iEntityEClass, this.getISecondaryTable(), "getSecondaryTables", 0, -1);
		addEOperation(iEntityEClass, this.getIEntity(), "parentEntity", 0, 1);
		addEOperation(iEntityEClass, this.getIEntity(), "rootEntity", 0, 1);
		initEClass(iEmbeddableEClass, IEmbeddable.class, "IEmbeddable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iTableEClass, ITable.class, "ITable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getITable_Name(), ecorePackage.getEString(), "name", null, 0, 1, ITable.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getITable_SpecifiedName(), ecorePackage.getEString(), "specifiedName", null, 0, 1, ITable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITable_DefaultName(), ecorePackage.getEString(), "defaultName", null, 0, 1, ITable.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITable_Catalog(), ecorePackage.getEString(), "catalog", null, 0, 1, ITable.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getITable_SpecifiedCatalog(), ecorePackage.getEString(), "specifiedCatalog", null, 0, 1, ITable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITable_DefaultCatalog(), ecorePackage.getEString(), "defaultCatalog", null, 0, 1, ITable.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITable_Schema(), ecorePackage.getEString(), "schema", null, 0, 1, ITable.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getITable_SpecifiedSchema(), ecorePackage.getEString(), "specifiedSchema", null, 0, 1, ITable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITable_DefaultSchema(), ecorePackage.getEString(), "defaultSchema", null, 0, 1, ITable.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iNamedColumnEClass, INamedColumn.class, "INamedColumn", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getINamedColumn_Name(), ecorePackage.getEString(), "name", null, 0, 1, INamedColumn.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getINamedColumn_SpecifiedName(), ecorePackage.getEString(), "specifiedName", null, 0, 1, INamedColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getINamedColumn_DefaultName(), ecorePackage.getEString(), "defaultName", null, 0, 1, INamedColumn.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getINamedColumn_ColumnDefinition(), ecorePackage.getEString(), "columnDefinition", null, 0, 1, INamedColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iAbstractColumnEClass, IAbstractColumn.class, "IAbstractColumn", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIAbstractColumn_Unique(), this.getDefaultFalseBoolean(), "unique", null, 0, 1, IAbstractColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIAbstractColumn_Nullable(), this.getDefaultTrueBoolean(), "nullable", null, 0, 1, IAbstractColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIAbstractColumn_Insertable(), this.getDefaultTrueBoolean(), "insertable", null, 0, 1, IAbstractColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIAbstractColumn_Updatable(), this.getDefaultTrueBoolean(), "updatable", null, 0, 1, IAbstractColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIAbstractColumn_Table(), ecorePackage.getEString(), "table", null, 0, 1, IAbstractColumn.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getIAbstractColumn_SpecifiedTable(), ecorePackage.getEString(), "specifiedTable", null, 0, 1, IAbstractColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIAbstractColumn_DefaultTable(), ecorePackage.getEString(), "defaultTable", null, 0, 1, IAbstractColumn.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iColumnEClass, IColumn.class, "IColumn", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIColumn_Length(), ecorePackage.getEInt(), "length", "255", 0, 1, IColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIColumn_Precision(), ecorePackage.getEInt(), "precision", null, 0, 1, IColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIColumn_Scale(), ecorePackage.getEInt(), "scale", null, 0, 1, IColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iColumnMappingEClass, IColumnMapping.class, "IColumnMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		addEOperation(iColumnMappingEClass, this.getIColumn(), "getColumn", 0, 1);
		initEClass(iBasicEClass, IBasic.class, "IBasic", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBasic_Fetch(), this.getDefaultEagerFetchType(), "fetch", null, 0, 1, IBasic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBasic_Optional(), this.getDefaultTrueBoolean(), "optional", null, 0, 1, IBasic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBasic_Column(), this.getIColumn(), null, "column", null, 1, 1, IBasic.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBasic_Lob(), ecorePackage.getEBoolean(), "lob", null, 0, 1, IBasic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBasic_Temporal(), this.getTemporalType(), "temporal", null, 0, 1, IBasic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBasic_Enumerated(), this.getEnumType(), "enumerated", null, 0, 1, IBasic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iIdEClass, IId.class, "IId", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIId_Column(), this.getIColumn(), null, "column", null, 1, 1, IId.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIId_GeneratedValue(), this.getIGeneratedValue(), null, "generatedValue", null, 0, 1, IId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIId_Temporal(), this.getTemporalType(), "temporal", null, 0, 1, IId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIId_TableGenerator(), this.getITableGenerator(), null, "tableGenerator", null, 0, 1, IId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIId_SequenceGenerator(), this.getISequenceGenerator(), null, "sequenceGenerator", null, 0, 1, IId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iTransientEClass, ITransient.class, "ITransient", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iVersionEClass, IVersion.class, "IVersion", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIVersion_Column(), this.getIColumn(), null, "column", null, 1, 1, IVersion.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIVersion_Temporal(), this.getTemporalType(), "temporal", null, 0, 1, IVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iEmbeddedIdEClass, IEmbeddedId.class, "IEmbeddedId", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iEmbeddedEClass, IEmbedded.class, "IEmbedded", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIEmbedded_AttributeOverrides(), this.getIAttributeOverride(), null, "attributeOverrides", null, 0, -1, IEmbedded.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEmbedded_SpecifiedAttributeOverrides(), this.getIAttributeOverride(), null, "specifiedAttributeOverrides", null, 0, -1, IEmbedded.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIEmbedded_DefaultAttributeOverrides(), this.getIAttributeOverride(), null, "defaultAttributeOverrides", null, 0, -1, IEmbedded.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		addEOperation(iEmbeddedEClass, this.getIEmbeddable(), "embeddable", 0, 1);
		initEClass(iRelationshipMappingEClass, IRelationshipMapping.class, "IRelationshipMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIRelationshipMapping_TargetEntity(), ecorePackage.getEString(), "targetEntity", null, 0, 1, IRelationshipMapping.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getIRelationshipMapping_SpecifiedTargetEntity(), ecorePackage.getEString(), "specifiedTargetEntity", null, 0, 1, IRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIRelationshipMapping_DefaultTargetEntity(), ecorePackage.getEString(), "defaultTargetEntity", null, 0, 1, IRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIRelationshipMapping_ResolvedTargetEntity(), this.getIEntity(), null, "resolvedTargetEntity", null, 0, 1, IRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iNonOwningMappingEClass, INonOwningMapping.class, "INonOwningMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getINonOwningMapping_MappedBy(), theEcorePackage.getEString(), "mappedBy", null, 0, 1, INonOwningMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iMultiRelationshipMappingEClass, IMultiRelationshipMapping.class, "IMultiRelationshipMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIMultiRelationshipMapping_Fetch(), this.getDefaultLazyFetchType(), "fetch", null, 0, 1, IMultiRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIMultiRelationshipMapping_JoinTable(), this.getIJoinTable(), null, "joinTable", null, 1, 1, IMultiRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIMultiRelationshipMapping_OrderBy(), this.getIOrderBy(), null, "orderBy", null, 1, 1, IMultiRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iOneToManyEClass, IOneToMany.class, "IOneToMany", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iManyToManyEClass, IManyToMany.class, "IManyToMany", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iSingleRelationshipMappingEClass, ISingleRelationshipMapping.class, "ISingleRelationshipMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getISingleRelationshipMapping_Fetch(), this.getDefaultEagerFetchType(), "fetch", null, 0, 1, ISingleRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getISingleRelationshipMapping_JoinColumns(), this.getIJoinColumn(), null, "joinColumns", null, 0, -1, ISingleRelationshipMapping.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getISingleRelationshipMapping_SpecifiedJoinColumns(), this.getIJoinColumn(), null, "specifiedJoinColumns", null, 0, -1, ISingleRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getISingleRelationshipMapping_DefaultJoinColumns(), this.getIJoinColumn(), null, "defaultJoinColumns", null, 0, -1, ISingleRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iManyToOneEClass, IManyToOne.class, "IManyToOne", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iOneToOneEClass, IOneToOne.class, "IOneToOne", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iJoinTableEClass, IJoinTable.class, "IJoinTable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIJoinTable_JoinColumns(), this.getIJoinColumn(), null, "joinColumns", null, 0, -1, IJoinTable.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIJoinTable_SpecifiedJoinColumns(), this.getIJoinColumn(), null, "specifiedJoinColumns", null, 0, -1, IJoinTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIJoinTable_DefaultJoinColumns(), this.getIJoinColumn(), null, "defaultJoinColumns", null, 0, -1, IJoinTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIJoinTable_InverseJoinColumns(), this.getIJoinColumn(), null, "inverseJoinColumns", null, 0, -1, IJoinTable.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIJoinTable_SpecifiedInverseJoinColumns(), this.getIJoinColumn(), null, "specifiedInverseJoinColumns", null, 0, -1, IJoinTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIJoinTable_DefaultInverseJoinColumns(), this.getIJoinColumn(), null, "defaultInverseJoinColumns", null, 0, -1, IJoinTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iAbstractJoinColumnEClass, IAbstractJoinColumn.class, "IAbstractJoinColumn", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIAbstractJoinColumn_ReferencedColumnName(), ecorePackage.getEString(), "referencedColumnName", null, 0, 1, IAbstractJoinColumn.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getIAbstractJoinColumn_SpecifiedReferencedColumnName(), ecorePackage.getEString(), "specifiedReferencedColumnName", null, 0, 1, IAbstractJoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIAbstractJoinColumn_DefaultReferencedColumnName(), ecorePackage.getEString(), "defaultReferencedColumnName", null, 0, 1, IAbstractJoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iJoinColumnEClass, IJoinColumn.class, "IJoinColumn", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iOverrideEClass, IOverride.class, "IOverride", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIOverride_Name(), theEcorePackage.getEString(), "name", null, 0, 1, IOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iAttributeOverrideEClass, IAttributeOverride.class, "IAttributeOverride", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIAttributeOverride_Column(), this.getIColumn(), null, "column", null, 1, 1, IAttributeOverride.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iAssociationOverrideEClass, IAssociationOverride.class, "IAssociationOverride", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIAssociationOverride_JoinColumns(), this.getIJoinColumn(), null, "joinColumns", null, 0, -1, IAssociationOverride.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIAssociationOverride_SpecifiedJoinColumns(), this.getIJoinColumn(), null, "specifiedJoinColumns", null, 0, -1, IAssociationOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIAssociationOverride_DefaultJoinColumns(), this.getIJoinColumn(), null, "defaultJoinColumns", null, 0, -1, IAssociationOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iDiscriminatorColumnEClass, IDiscriminatorColumn.class, "IDiscriminatorColumn", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIDiscriminatorColumn_DefaultName(), theEcorePackage.getEString(), "defaultName", null, 0, 1, IDiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIDiscriminatorColumn_SpecifiedName(), theEcorePackage.getEString(), "specifiedName", null, 0, 1, IDiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIDiscriminatorColumn_Name(), theEcorePackage.getEString(), "name", null, 0, 1, IDiscriminatorColumn.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getIDiscriminatorColumn_DiscriminatorType(), this.getDiscriminatorType(), "discriminatorType", null, 0, 1, IDiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIDiscriminatorColumn_ColumnDefinition(), ecorePackage.getEString(), "columnDefinition", null, 0, 1, IDiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIDiscriminatorColumn_DefaultLength(), theEcorePackage.getEInt(), "defaultLength", "31", 0, 1, IDiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIDiscriminatorColumn_SpecifiedLength(), theEcorePackage.getEInt(), "specifiedLength", "-1", 0, 1, IDiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIDiscriminatorColumn_Length(), theEcorePackage.getEInt(), "length", null, 0, 1, IDiscriminatorColumn.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEClass(iSecondaryTableEClass, ISecondaryTable.class, "ISecondaryTable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getISecondaryTable_PrimaryKeyJoinColumns(), this.getIPrimaryKeyJoinColumn(), null, "primaryKeyJoinColumns", null, 0, -1, ISecondaryTable.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getISecondaryTable_SpecifiedPrimaryKeyJoinColumns(), this.getIPrimaryKeyJoinColumn(), null, "specifiedPrimaryKeyJoinColumns", null, 0, -1, ISecondaryTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getISecondaryTable_DefaultPrimaryKeyJoinColumns(), this.getIPrimaryKeyJoinColumn(), null, "defaultPrimaryKeyJoinColumns", null, 0, -1, ISecondaryTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		addEOperation(iSecondaryTableEClass, theJpaCorePackage.getITypeMapping(), "typeMapping", 0, 1);
		initEClass(iPrimaryKeyJoinColumnEClass, IPrimaryKeyJoinColumn.class, "IPrimaryKeyJoinColumn", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iGeneratorEClass, IGenerator.class, "IGenerator", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIGenerator_Name(), ecorePackage.getEString(), "name", null, 0, 1, IGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIGenerator_InitialValue(), theEcorePackage.getEInt(), "initialValue", null, 0, 1, IGenerator.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getIGenerator_SpecifiedInitialValue(), theEcorePackage.getEInt(), "specifiedInitialValue", "-1", 0, 1, IGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIGenerator_DefaultInitialValue(), theEcorePackage.getEInt(), "defaultInitialValue", null, 0, 1, IGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIGenerator_AllocationSize(), theEcorePackage.getEInt(), "allocationSize", null, 0, 1, IGenerator.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getIGenerator_SpecifiedAllocationSize(), theEcorePackage.getEInt(), "specifiedAllocationSize", "-1", 0, 1, IGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIGenerator_DefaultAllocationSize(), theEcorePackage.getEInt(), "defaultAllocationSize", null, 0, 1, IGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iTableGeneratorEClass, ITableGenerator.class, "ITableGenerator", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getITableGenerator_Table(), ecorePackage.getEString(), "table", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_SpecifiedTable(), ecorePackage.getEString(), "specifiedTable", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_DefaultTable(), ecorePackage.getEString(), "defaultTable", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_Catalog(), ecorePackage.getEString(), "catalog", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_SpecifiedCatalog(), ecorePackage.getEString(), "specifiedCatalog", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_DefaultCatalog(), ecorePackage.getEString(), "defaultCatalog", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_Schema(), ecorePackage.getEString(), "schema", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_SpecifiedSchema(), ecorePackage.getEString(), "specifiedSchema", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_DefaultSchema(), ecorePackage.getEString(), "defaultSchema", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_PkColumnName(), ecorePackage.getEString(), "pkColumnName", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_SpecifiedPkColumnName(), ecorePackage.getEString(), "specifiedPkColumnName", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_DefaultPkColumnName(), ecorePackage.getEString(), "defaultPkColumnName", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_ValueColumnName(), ecorePackage.getEString(), "valueColumnName", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_SpecifiedValueColumnName(), ecorePackage.getEString(), "specifiedValueColumnName", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_DefaultValueColumnName(), ecorePackage.getEString(), "defaultValueColumnName", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_PkColumnValue(), ecorePackage.getEString(), "pkColumnValue", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_SpecifiedPkColumnValue(), ecorePackage.getEString(), "specifiedPkColumnValue", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITableGenerator_DefaultPkColumnValue(), ecorePackage.getEString(), "defaultPkColumnValue", null, 0, 1, ITableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iSequenceGeneratorEClass, ISequenceGenerator.class, "ISequenceGenerator", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getISequenceGenerator_SequenceName(), ecorePackage.getEString(), "sequenceName", null, 0, 1, ISequenceGenerator.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getISequenceGenerator_SpecifiedSequenceName(), ecorePackage.getEString(), "specifiedSequenceName", null, 0, 1, ISequenceGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getISequenceGenerator_DefaultSequenceName(), ecorePackage.getEString(), "defaultSequenceName", null, 0, 1, ISequenceGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iGeneratedValueEClass, IGeneratedValue.class, "IGeneratedValue", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIGeneratedValue_Strategy(), this.getGenerationType(), "strategy", null, 0, 1, IGeneratedValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIGeneratedValue_Generator(), theEcorePackage.getEString(), "generator", null, 0, 1, IGeneratedValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iOrderByEClass, IOrderBy.class, "IOrderBy", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIOrderBy_Value(), theEcorePackage.getEString(), "value", null, 0, 1, IOrderBy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIOrderBy_Type(), this.getOrderingType(), "type", null, 0, 1, IOrderBy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iQueryEClass, IQuery.class, "IQuery", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIQuery_Name(), theEcorePackage.getEString(), "name", null, 0, 1, IQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIQuery_Query(), theEcorePackage.getEString(), "query", null, 0, 1, IQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIQuery_Hints(), this.getIQueryHint(), null, "hints", null, 0, -1, IQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iNamedQueryEClass, INamedQuery.class, "INamedQuery", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEClass(iNamedNativeQueryEClass, INamedNativeQuery.class, "INamedNativeQuery", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getINamedNativeQuery_ResultClass(), theEcorePackage.getEString(), "resultClass", null, 0, 1, INamedNativeQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getINamedNativeQuery_ResultSetMapping(), theEcorePackage.getEString(), "resultSetMapping", null, 0, 1, INamedNativeQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEClass(iQueryHintEClass, IQueryHint.class, "IQueryHint", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIQueryHint_Name(), theEcorePackage.getEString(), "name", null, 0, 1, IQueryHint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIQueryHint_Value(), theEcorePackage.getEString(), "value", null, 0, 1, IQueryHint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		// Initialize enums and add enum literals
		initEEnum(defaultEagerFetchTypeEEnum, DefaultEagerFetchType.class, "DefaultEagerFetchType");
		addEEnumLiteral(defaultEagerFetchTypeEEnum, DefaultEagerFetchType.DEFAULT);
		addEEnumLiteral(defaultEagerFetchTypeEEnum, DefaultEagerFetchType.EAGER);
		addEEnumLiteral(defaultEagerFetchTypeEEnum, DefaultEagerFetchType.LAZY);
		initEEnum(defaultLazyFetchTypeEEnum, DefaultLazyFetchType.class, "DefaultLazyFetchType");
		addEEnumLiteral(defaultLazyFetchTypeEEnum, DefaultLazyFetchType.DEFAULT);
		addEEnumLiteral(defaultLazyFetchTypeEEnum, DefaultLazyFetchType.LAZY);
		addEEnumLiteral(defaultLazyFetchTypeEEnum, DefaultLazyFetchType.EAGER);
		initEEnum(defaultFalseBooleanEEnum, DefaultFalseBoolean.class, "DefaultFalseBoolean");
		addEEnumLiteral(defaultFalseBooleanEEnum, DefaultFalseBoolean.DEFAULT);
		addEEnumLiteral(defaultFalseBooleanEEnum, DefaultFalseBoolean.FALSE);
		addEEnumLiteral(defaultFalseBooleanEEnum, DefaultFalseBoolean.TRUE);
		initEEnum(defaultTrueBooleanEEnum, DefaultTrueBoolean.class, "DefaultTrueBoolean");
		addEEnumLiteral(defaultTrueBooleanEEnum, DefaultTrueBoolean.DEFAULT);
		addEEnumLiteral(defaultTrueBooleanEEnum, DefaultTrueBoolean.TRUE);
		addEEnumLiteral(defaultTrueBooleanEEnum, DefaultTrueBoolean.FALSE);
		initEEnum(temporalTypeEEnum, TemporalType.class, "TemporalType");
		addEEnumLiteral(temporalTypeEEnum, TemporalType.NULL);
		addEEnumLiteral(temporalTypeEEnum, TemporalType.DATE);
		addEEnumLiteral(temporalTypeEEnum, TemporalType.TIME);
		addEEnumLiteral(temporalTypeEEnum, TemporalType.TIMESTAMP);
		initEEnum(inheritanceTypeEEnum, InheritanceType.class, "InheritanceType");
		addEEnumLiteral(inheritanceTypeEEnum, InheritanceType.DEFAULT);
		addEEnumLiteral(inheritanceTypeEEnum, InheritanceType.SINGLE_TABLE);
		addEEnumLiteral(inheritanceTypeEEnum, InheritanceType.JOINED);
		addEEnumLiteral(inheritanceTypeEEnum, InheritanceType.TABLE_PER_CLASS);
		initEEnum(discriminatorTypeEEnum, DiscriminatorType.class, "DiscriminatorType");
		addEEnumLiteral(discriminatorTypeEEnum, DiscriminatorType.DEFAULT);
		addEEnumLiteral(discriminatorTypeEEnum, DiscriminatorType.STRING);
		addEEnumLiteral(discriminatorTypeEEnum, DiscriminatorType.CHAR);
		addEEnumLiteral(discriminatorTypeEEnum, DiscriminatorType.INTEGER);
		initEEnum(generationTypeEEnum, GenerationType.class, "GenerationType");
		addEEnumLiteral(generationTypeEEnum, GenerationType.DEFAULT);
		addEEnumLiteral(generationTypeEEnum, GenerationType.AUTO);
		addEEnumLiteral(generationTypeEEnum, GenerationType.IDENTITY);
		addEEnumLiteral(generationTypeEEnum, GenerationType.SEQUENCE);
		addEEnumLiteral(generationTypeEEnum, GenerationType.TABLE);
		initEEnum(enumTypeEEnum, EnumType.class, "EnumType");
		addEEnumLiteral(enumTypeEEnum, EnumType.DEFAULT);
		addEEnumLiteral(enumTypeEEnum, EnumType.ORDINAL);
		addEEnumLiteral(enumTypeEEnum, EnumType.STRING);
		initEEnum(orderingTypeEEnum, OrderingType.class, "OrderingType");
		addEEnumLiteral(orderingTypeEEnum, OrderingType.NONE);
		addEEnumLiteral(orderingTypeEEnum, OrderingType.PRIMARY_KEY);
		addEEnumLiteral(orderingTypeEEnum, OrderingType.CUSTOM);
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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IMappedSuperclass <em>IMapped Superclass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IMappedSuperclass
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIMappedSuperclass()
		 * @generated
		 */
		public static final EClass IMAPPED_SUPERCLASS = eINSTANCE.getIMappedSuperclass();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IEntity <em>IEntity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IEntity
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity()
		 * @generated
		 */
		public static final EClass IENTITY = eINSTANCE.getIEntity();

		/**
		 * The meta object literal for the '<em><b>Specified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IENTITY__SPECIFIED_NAME = eINSTANCE.getIEntity_SpecifiedName();

		/**
		 * The meta object literal for the '<em><b>Default Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IENTITY__DEFAULT_NAME = eINSTANCE.getIEntity_DefaultName();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__TABLE = eINSTANCE.getIEntity_Table();

		/**
		 * The meta object literal for the '<em><b>Specified Secondary Tables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__SPECIFIED_SECONDARY_TABLES = eINSTANCE.getIEntity_SpecifiedSecondaryTables();

		/**
		 * The meta object literal for the '<em><b>Inheritance Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IENTITY__INHERITANCE_STRATEGY = eINSTANCE.getIEntity_InheritanceStrategy();

		/**
		 * The meta object literal for the '<em><b>Discriminator Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__DISCRIMINATOR_COLUMN = eINSTANCE.getIEntity_DiscriminatorColumn();

		/**
		 * The meta object literal for the '<em><b>Sequence Generator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__SEQUENCE_GENERATOR = eINSTANCE.getIEntity_SequenceGenerator();

		/**
		 * The meta object literal for the '<em><b>Table Generator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__TABLE_GENERATOR = eINSTANCE.getIEntity_TableGenerator();

		/**
		 * The meta object literal for the '<em><b>Default Discriminator Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IENTITY__DEFAULT_DISCRIMINATOR_VALUE = eINSTANCE.getIEntity_DefaultDiscriminatorValue();

		/**
		 * The meta object literal for the '<em><b>Specified Discriminator Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IENTITY__SPECIFIED_DISCRIMINATOR_VALUE = eINSTANCE.getIEntity_SpecifiedDiscriminatorValue();

		/**
		 * The meta object literal for the '<em><b>Discriminator Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IENTITY__DISCRIMINATOR_VALUE = eINSTANCE.getIEntity_DiscriminatorValue();

		/**
		 * The meta object literal for the '<em><b>Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getIEntity_PrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Specified Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getIEntity_SpecifiedPrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Default Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getIEntity_DefaultPrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__ATTRIBUTE_OVERRIDES = eINSTANCE.getIEntity_AttributeOverrides();

		/**
		 * The meta object literal for the '<em><b>Specified Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES = eINSTANCE.getIEntity_SpecifiedAttributeOverrides();

		/**
		 * The meta object literal for the '<em><b>Default Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__DEFAULT_ATTRIBUTE_OVERRIDES = eINSTANCE.getIEntity_DefaultAttributeOverrides();

		/**
		 * The meta object literal for the '<em><b>Association Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__ASSOCIATION_OVERRIDES = eINSTANCE.getIEntity_AssociationOverrides();

		/**
		 * The meta object literal for the '<em><b>Specified Association Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__SPECIFIED_ASSOCIATION_OVERRIDES = eINSTANCE.getIEntity_SpecifiedAssociationOverrides();

		/**
		 * The meta object literal for the '<em><b>Default Association Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__DEFAULT_ASSOCIATION_OVERRIDES = eINSTANCE.getIEntity_DefaultAssociationOverrides();

		/**
		 * The meta object literal for the '<em><b>Named Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__NAMED_QUERIES = eINSTANCE.getIEntity_NamedQueries();

		/**
		 * The meta object literal for the '<em><b>Named Native Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IENTITY__NAMED_NATIVE_QUERIES = eINSTANCE.getIEntity_NamedNativeQueries();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IEmbeddable <em>IEmbeddable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IEmbeddable
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEmbeddable()
		 * @generated
		 */
		public static final EClass IEMBEDDABLE = eINSTANCE.getIEmbeddable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.ITable <em>ITable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.ITable
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITable()
		 * @generated
		 */
		public static final EClass ITABLE = eINSTANCE.getITable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE__NAME = eINSTANCE.getITable_Name();

		/**
		 * The meta object literal for the '<em><b>Specified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE__SPECIFIED_NAME = eINSTANCE.getITable_SpecifiedName();

		/**
		 * The meta object literal for the '<em><b>Default Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE__DEFAULT_NAME = eINSTANCE.getITable_DefaultName();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE__CATALOG = eINSTANCE.getITable_Catalog();

		/**
		 * The meta object literal for the '<em><b>Specified Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE__SPECIFIED_CATALOG = eINSTANCE.getITable_SpecifiedCatalog();

		/**
		 * The meta object literal for the '<em><b>Default Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE__DEFAULT_CATALOG = eINSTANCE.getITable_DefaultCatalog();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE__SCHEMA = eINSTANCE.getITable_Schema();

		/**
		 * The meta object literal for the '<em><b>Specified Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE__SPECIFIED_SCHEMA = eINSTANCE.getITable_SpecifiedSchema();

		/**
		 * The meta object literal for the '<em><b>Default Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE__DEFAULT_SCHEMA = eINSTANCE.getITable_DefaultSchema();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.INamedColumn <em>INamed Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.INamedColumn
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINamedColumn()
		 * @generated
		 */
		public static final EClass INAMED_COLUMN = eINSTANCE.getINamedColumn();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute INAMED_COLUMN__NAME = eINSTANCE.getINamedColumn_Name();

		/**
		 * The meta object literal for the '<em><b>Specified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute INAMED_COLUMN__SPECIFIED_NAME = eINSTANCE.getINamedColumn_SpecifiedName();

		/**
		 * The meta object literal for the '<em><b>Default Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute INAMED_COLUMN__DEFAULT_NAME = eINSTANCE.getINamedColumn_DefaultName();

		/**
		 * The meta object literal for the '<em><b>Column Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute INAMED_COLUMN__COLUMN_DEFINITION = eINSTANCE.getINamedColumn_ColumnDefinition();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn <em>IAbstract Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IAbstractColumn
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractColumn()
		 * @generated
		 */
		public static final EClass IABSTRACT_COLUMN = eINSTANCE.getIAbstractColumn();

		/**
		 * The meta object literal for the '<em><b>Unique</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IABSTRACT_COLUMN__UNIQUE = eINSTANCE.getIAbstractColumn_Unique();

		/**
		 * The meta object literal for the '<em><b>Nullable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IABSTRACT_COLUMN__NULLABLE = eINSTANCE.getIAbstractColumn_Nullable();

		/**
		 * The meta object literal for the '<em><b>Insertable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IABSTRACT_COLUMN__INSERTABLE = eINSTANCE.getIAbstractColumn_Insertable();

		/**
		 * The meta object literal for the '<em><b>Updatable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IABSTRACT_COLUMN__UPDATABLE = eINSTANCE.getIAbstractColumn_Updatable();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IABSTRACT_COLUMN__TABLE = eINSTANCE.getIAbstractColumn_Table();

		/**
		 * The meta object literal for the '<em><b>Specified Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IABSTRACT_COLUMN__SPECIFIED_TABLE = eINSTANCE.getIAbstractColumn_SpecifiedTable();

		/**
		 * The meta object literal for the '<em><b>Default Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IABSTRACT_COLUMN__DEFAULT_TABLE = eINSTANCE.getIAbstractColumn_DefaultTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IColumn <em>IColumn</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IColumn
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIColumn()
		 * @generated
		 */
		public static final EClass ICOLUMN = eINSTANCE.getIColumn();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ICOLUMN__LENGTH = eINSTANCE.getIColumn_Length();

		/**
		 * The meta object literal for the '<em><b>Precision</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ICOLUMN__PRECISION = eINSTANCE.getIColumn_Precision();

		/**
		 * The meta object literal for the '<em><b>Scale</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ICOLUMN__SCALE = eINSTANCE.getIColumn_Scale();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IColumnMapping <em>IColumn Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IColumnMapping
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIColumnMapping()
		 * @generated
		 */
		public static final EClass ICOLUMN_MAPPING = eINSTANCE.getIColumnMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IBasic <em>IBasic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IBasic
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIBasic()
		 * @generated
		 */
		public static final EClass IBASIC = eINSTANCE.getIBasic();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IBASIC__FETCH = eINSTANCE.getIBasic_Fetch();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IBASIC__OPTIONAL = eINSTANCE.getIBasic_Optional();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IBASIC__COLUMN = eINSTANCE.getIBasic_Column();

		/**
		 * The meta object literal for the '<em><b>Lob</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IBASIC__LOB = eINSTANCE.getIBasic_Lob();

		/**
		 * The meta object literal for the '<em><b>Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IBASIC__TEMPORAL = eINSTANCE.getIBasic_Temporal();

		/**
		 * The meta object literal for the '<em><b>Enumerated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IBASIC__ENUMERATED = eINSTANCE.getIBasic_Enumerated();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IId <em>IId</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IId
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIId()
		 * @generated
		 */
		public static final EClass IID = eINSTANCE.getIId();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IID__COLUMN = eINSTANCE.getIId_Column();

		/**
		 * The meta object literal for the '<em><b>Generated Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IID__GENERATED_VALUE = eINSTANCE.getIId_GeneratedValue();

		/**
		 * The meta object literal for the '<em><b>Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IID__TEMPORAL = eINSTANCE.getIId_Temporal();

		/**
		 * The meta object literal for the '<em><b>Table Generator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IID__TABLE_GENERATOR = eINSTANCE.getIId_TableGenerator();

		/**
		 * The meta object literal for the '<em><b>Sequence Generator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IID__SEQUENCE_GENERATOR = eINSTANCE.getIId_SequenceGenerator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.ITransient <em>ITransient</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.ITransient
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITransient()
		 * @generated
		 */
		public static final EClass ITRANSIENT = eINSTANCE.getITransient();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IVersion <em>IVersion</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IVersion
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIVersion()
		 * @generated
		 */
		public static final EClass IVERSION = eINSTANCE.getIVersion();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IVERSION__COLUMN = eINSTANCE.getIVersion_Column();

		/**
		 * The meta object literal for the '<em><b>Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IVERSION__TEMPORAL = eINSTANCE.getIVersion_Temporal();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IEmbeddedId <em>IEmbedded Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IEmbeddedId
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEmbeddedId()
		 * @generated
		 */
		public static final EClass IEMBEDDED_ID = eINSTANCE.getIEmbeddedId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IEmbedded <em>IEmbedded</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IEmbedded
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEmbedded()
		 * @generated
		 */
		public static final EClass IEMBEDDED = eINSTANCE.getIEmbedded();

		/**
		 * The meta object literal for the '<em><b>Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IEMBEDDED__ATTRIBUTE_OVERRIDES = eINSTANCE.getIEmbedded_AttributeOverrides();

		/**
		 * The meta object literal for the '<em><b>Specified Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IEMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES = eINSTANCE.getIEmbedded_SpecifiedAttributeOverrides();

		/**
		 * The meta object literal for the '<em><b>Default Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IEMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES = eINSTANCE.getIEmbedded_DefaultAttributeOverrides();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping <em>IRelationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IRelationshipMapping
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIRelationshipMapping()
		 * @generated
		 */
		public static final EClass IRELATIONSHIP_MAPPING = eINSTANCE.getIRelationshipMapping();

		/**
		 * The meta object literal for the '<em><b>Target Entity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IRELATIONSHIP_MAPPING__TARGET_ENTITY = eINSTANCE.getIRelationshipMapping_TargetEntity();

		/**
		 * The meta object literal for the '<em><b>Specified Target Entity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IRELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY = eINSTANCE.getIRelationshipMapping_SpecifiedTargetEntity();

		/**
		 * The meta object literal for the '<em><b>Default Target Entity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IRELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY = eINSTANCE.getIRelationshipMapping_DefaultTargetEntity();

		/**
		 * The meta object literal for the '<em><b>Resolved Target Entity</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IRELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY = eINSTANCE.getIRelationshipMapping_ResolvedTargetEntity();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.INonOwningMapping <em>INon Owning Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.INonOwningMapping
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINonOwningMapping()
		 * @generated
		 */
		public static final EClass INON_OWNING_MAPPING = eINSTANCE.getINonOwningMapping();

		/**
		 * The meta object literal for the '<em><b>Mapped By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute INON_OWNING_MAPPING__MAPPED_BY = eINSTANCE.getINonOwningMapping_MappedBy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping <em>IMulti Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIMultiRelationshipMapping()
		 * @generated
		 */
		public static final EClass IMULTI_RELATIONSHIP_MAPPING = eINSTANCE.getIMultiRelationshipMapping();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IMULTI_RELATIONSHIP_MAPPING__FETCH = eINSTANCE.getIMultiRelationshipMapping_Fetch();

		/**
		 * The meta object literal for the '<em><b>Join Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IMULTI_RELATIONSHIP_MAPPING__JOIN_TABLE = eINSTANCE.getIMultiRelationshipMapping_JoinTable();

		/**
		 * The meta object literal for the '<em><b>Order By</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IMULTI_RELATIONSHIP_MAPPING__ORDER_BY = eINSTANCE.getIMultiRelationshipMapping_OrderBy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IOneToMany <em>IOne To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IOneToMany
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIOneToMany()
		 * @generated
		 */
		public static final EClass IONE_TO_MANY = eINSTANCE.getIOneToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IManyToMany <em>IMany To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IManyToMany
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIManyToMany()
		 * @generated
		 */
		public static final EClass IMANY_TO_MANY = eINSTANCE.getIManyToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping <em>ISingle Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISingleRelationshipMapping()
		 * @generated
		 */
		public static final EClass ISINGLE_RELATIONSHIP_MAPPING = eINSTANCE.getISingleRelationshipMapping();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ISINGLE_RELATIONSHIP_MAPPING__FETCH = eINSTANCE.getISingleRelationshipMapping_Fetch();

		/**
		 * The meta object literal for the '<em><b>Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ISINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS = eINSTANCE.getISingleRelationshipMapping_JoinColumns();

		/**
		 * The meta object literal for the '<em><b>Specified Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS = eINSTANCE.getISingleRelationshipMapping_SpecifiedJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Default Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS = eINSTANCE.getISingleRelationshipMapping_DefaultJoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IManyToOne <em>IMany To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IManyToOne
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIManyToOne()
		 * @generated
		 */
		public static final EClass IMANY_TO_ONE = eINSTANCE.getIManyToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IOneToOne <em>IOne To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IOneToOne
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIOneToOne()
		 * @generated
		 */
		public static final EClass IONE_TO_ONE = eINSTANCE.getIOneToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IJoinTable <em>IJoin Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IJoinTable
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIJoinTable()
		 * @generated
		 */
		public static final EClass IJOIN_TABLE = eINSTANCE.getIJoinTable();

		/**
		 * The meta object literal for the '<em><b>Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IJOIN_TABLE__JOIN_COLUMNS = eINSTANCE.getIJoinTable_JoinColumns();

		/**
		 * The meta object literal for the '<em><b>Specified Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IJOIN_TABLE__SPECIFIED_JOIN_COLUMNS = eINSTANCE.getIJoinTable_SpecifiedJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Default Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IJOIN_TABLE__DEFAULT_JOIN_COLUMNS = eINSTANCE.getIJoinTable_DefaultJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Inverse Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IJOIN_TABLE__INVERSE_JOIN_COLUMNS = eINSTANCE.getIJoinTable_InverseJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Specified Inverse Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IJOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS = eINSTANCE.getIJoinTable_SpecifiedInverseJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Default Inverse Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IJOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS = eINSTANCE.getIJoinTable_DefaultInverseJoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn <em>IAbstract Join Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractJoinColumn()
		 * @generated
		 */
		public static final EClass IABSTRACT_JOIN_COLUMN = eINSTANCE.getIAbstractJoinColumn();

		/**
		 * The meta object literal for the '<em><b>Referenced Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IABSTRACT_JOIN_COLUMN__REFERENCED_COLUMN_NAME = eINSTANCE.getIAbstractJoinColumn_ReferencedColumnName();

		/**
		 * The meta object literal for the '<em><b>Specified Referenced Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IABSTRACT_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME = eINSTANCE.getIAbstractJoinColumn_SpecifiedReferencedColumnName();

		/**
		 * The meta object literal for the '<em><b>Default Referenced Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IABSTRACT_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME = eINSTANCE.getIAbstractJoinColumn_DefaultReferencedColumnName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IJoinColumn <em>IJoin Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IJoinColumn
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIJoinColumn()
		 * @generated
		 */
		public static final EClass IJOIN_COLUMN = eINSTANCE.getIJoinColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IOverride <em>IOverride</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IOverride
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIOverride()
		 * @generated
		 */
		public static final EClass IOVERRIDE = eINSTANCE.getIOverride();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IOVERRIDE__NAME = eINSTANCE.getIOverride_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride <em>IAttribute Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IAttributeOverride
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAttributeOverride()
		 * @generated
		 */
		public static final EClass IATTRIBUTE_OVERRIDE = eINSTANCE.getIAttributeOverride();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IATTRIBUTE_OVERRIDE__COLUMN = eINSTANCE.getIAttributeOverride_Column();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride <em>IAssociation Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IAssociationOverride
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAssociationOverride()
		 * @generated
		 */
		public static final EClass IASSOCIATION_OVERRIDE = eINSTANCE.getIAssociationOverride();

		/**
		 * The meta object literal for the '<em><b>Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IASSOCIATION_OVERRIDE__JOIN_COLUMNS = eINSTANCE.getIAssociationOverride_JoinColumns();

		/**
		 * The meta object literal for the '<em><b>Specified Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS = eINSTANCE.getIAssociationOverride_SpecifiedJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Default Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS = eINSTANCE.getIAssociationOverride_DefaultJoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn <em>IDiscriminator Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIDiscriminatorColumn()
		 * @generated
		 */
		public static final EClass IDISCRIMINATOR_COLUMN = eINSTANCE.getIDiscriminatorColumn();

		/**
		 * The meta object literal for the '<em><b>Default Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IDISCRIMINATOR_COLUMN__DEFAULT_NAME = eINSTANCE.getIDiscriminatorColumn_DefaultName();

		/**
		 * The meta object literal for the '<em><b>Specified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IDISCRIMINATOR_COLUMN__SPECIFIED_NAME = eINSTANCE.getIDiscriminatorColumn_SpecifiedName();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IDISCRIMINATOR_COLUMN__NAME = eINSTANCE.getIDiscriminatorColumn_Name();

		/**
		 * The meta object literal for the '<em><b>Discriminator Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IDISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = eINSTANCE.getIDiscriminatorColumn_DiscriminatorType();

		/**
		 * The meta object literal for the '<em><b>Column Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IDISCRIMINATOR_COLUMN__COLUMN_DEFINITION = eINSTANCE.getIDiscriminatorColumn_ColumnDefinition();

		/**
		 * The meta object literal for the '<em><b>Default Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IDISCRIMINATOR_COLUMN__DEFAULT_LENGTH = eINSTANCE.getIDiscriminatorColumn_DefaultLength();

		/**
		 * The meta object literal for the '<em><b>Specified Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IDISCRIMINATOR_COLUMN__SPECIFIED_LENGTH = eINSTANCE.getIDiscriminatorColumn_SpecifiedLength();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IDISCRIMINATOR_COLUMN__LENGTH = eINSTANCE.getIDiscriminatorColumn_Length();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable <em>ISecondary Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.ISecondaryTable
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISecondaryTable()
		 * @generated
		 */
		public static final EClass ISECONDARY_TABLE = eINSTANCE.getISecondaryTable();

		/**
		 * The meta object literal for the '<em><b>Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ISECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getISecondaryTable_PrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Specified Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ISECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getISecondaryTable_SpecifiedPrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Default Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ISECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getISecondaryTable_DefaultPrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn <em>IPrimary Key Join Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIPrimaryKeyJoinColumn()
		 * @generated
		 */
		public static final EClass IPRIMARY_KEY_JOIN_COLUMN = eINSTANCE.getIPrimaryKeyJoinColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IGenerator <em>IGenerator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IGenerator
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIGenerator()
		 * @generated
		 */
		public static final EClass IGENERATOR = eINSTANCE.getIGenerator();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IGENERATOR__NAME = eINSTANCE.getIGenerator_Name();

		/**
		 * The meta object literal for the '<em><b>Initial Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IGENERATOR__INITIAL_VALUE = eINSTANCE.getIGenerator_InitialValue();

		/**
		 * The meta object literal for the '<em><b>Specified Initial Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IGENERATOR__SPECIFIED_INITIAL_VALUE = eINSTANCE.getIGenerator_SpecifiedInitialValue();

		/**
		 * The meta object literal for the '<em><b>Default Initial Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IGENERATOR__DEFAULT_INITIAL_VALUE = eINSTANCE.getIGenerator_DefaultInitialValue();

		/**
		 * The meta object literal for the '<em><b>Allocation Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IGENERATOR__ALLOCATION_SIZE = eINSTANCE.getIGenerator_AllocationSize();

		/**
		 * The meta object literal for the '<em><b>Specified Allocation Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IGENERATOR__SPECIFIED_ALLOCATION_SIZE = eINSTANCE.getIGenerator_SpecifiedAllocationSize();

		/**
		 * The meta object literal for the '<em><b>Default Allocation Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IGENERATOR__DEFAULT_ALLOCATION_SIZE = eINSTANCE.getIGenerator_DefaultAllocationSize();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator <em>ITable Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator()
		 * @generated
		 */
		public static final EClass ITABLE_GENERATOR = eINSTANCE.getITableGenerator();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__TABLE = eINSTANCE.getITableGenerator_Table();

		/**
		 * The meta object literal for the '<em><b>Specified Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__SPECIFIED_TABLE = eINSTANCE.getITableGenerator_SpecifiedTable();

		/**
		 * The meta object literal for the '<em><b>Default Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__DEFAULT_TABLE = eINSTANCE.getITableGenerator_DefaultTable();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__CATALOG = eINSTANCE.getITableGenerator_Catalog();

		/**
		 * The meta object literal for the '<em><b>Specified Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__SPECIFIED_CATALOG = eINSTANCE.getITableGenerator_SpecifiedCatalog();

		/**
		 * The meta object literal for the '<em><b>Default Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__DEFAULT_CATALOG = eINSTANCE.getITableGenerator_DefaultCatalog();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__SCHEMA = eINSTANCE.getITableGenerator_Schema();

		/**
		 * The meta object literal for the '<em><b>Specified Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__SPECIFIED_SCHEMA = eINSTANCE.getITableGenerator_SpecifiedSchema();

		/**
		 * The meta object literal for the '<em><b>Default Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__DEFAULT_SCHEMA = eINSTANCE.getITableGenerator_DefaultSchema();

		/**
		 * The meta object literal for the '<em><b>Pk Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__PK_COLUMN_NAME = eINSTANCE.getITableGenerator_PkColumnName();

		/**
		 * The meta object literal for the '<em><b>Specified Pk Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME = eINSTANCE.getITableGenerator_SpecifiedPkColumnName();

		/**
		 * The meta object literal for the '<em><b>Default Pk Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__DEFAULT_PK_COLUMN_NAME = eINSTANCE.getITableGenerator_DefaultPkColumnName();

		/**
		 * The meta object literal for the '<em><b>Value Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__VALUE_COLUMN_NAME = eINSTANCE.getITableGenerator_ValueColumnName();

		/**
		 * The meta object literal for the '<em><b>Specified Value Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME = eINSTANCE.getITableGenerator_SpecifiedValueColumnName();

		/**
		 * The meta object literal for the '<em><b>Default Value Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__DEFAULT_VALUE_COLUMN_NAME = eINSTANCE.getITableGenerator_DefaultValueColumnName();

		/**
		 * The meta object literal for the '<em><b>Pk Column Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__PK_COLUMN_VALUE = eINSTANCE.getITableGenerator_PkColumnValue();

		/**
		 * The meta object literal for the '<em><b>Specified Pk Column Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE = eINSTANCE.getITableGenerator_SpecifiedPkColumnValue();

		/**
		 * The meta object literal for the '<em><b>Default Pk Column Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ITABLE_GENERATOR__DEFAULT_PK_COLUMN_VALUE = eINSTANCE.getITableGenerator_DefaultPkColumnValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.ISequenceGenerator <em>ISequence Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.ISequenceGenerator
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISequenceGenerator()
		 * @generated
		 */
		public static final EClass ISEQUENCE_GENERATOR = eINSTANCE.getISequenceGenerator();

		/**
		 * The meta object literal for the '<em><b>Sequence Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ISEQUENCE_GENERATOR__SEQUENCE_NAME = eINSTANCE.getISequenceGenerator_SequenceName();

		/**
		 * The meta object literal for the '<em><b>Specified Sequence Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ISEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME = eINSTANCE.getISequenceGenerator_SpecifiedSequenceName();

		/**
		 * The meta object literal for the '<em><b>Default Sequence Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ISEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME = eINSTANCE.getISequenceGenerator_DefaultSequenceName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IGeneratedValue <em>IGenerated Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IGeneratedValue
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIGeneratedValue()
		 * @generated
		 */
		public static final EClass IGENERATED_VALUE = eINSTANCE.getIGeneratedValue();

		/**
		 * The meta object literal for the '<em><b>Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IGENERATED_VALUE__STRATEGY = eINSTANCE.getIGeneratedValue_Strategy();

		/**
		 * The meta object literal for the '<em><b>Generator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IGENERATED_VALUE__GENERATOR = eINSTANCE.getIGeneratedValue_Generator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IOrderBy <em>IOrder By</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IOrderBy
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIOrderBy()
		 * @generated
		 */
		public static final EClass IORDER_BY = eINSTANCE.getIOrderBy();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IORDER_BY__VALUE = eINSTANCE.getIOrderBy_Value();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IORDER_BY__TYPE = eINSTANCE.getIOrderBy_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IQuery <em>IQuery</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IQuery
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIQuery()
		 * @generated
		 */
		public static final EClass IQUERY = eINSTANCE.getIQuery();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IQUERY__NAME = eINSTANCE.getIQuery_Name();

		/**
		 * The meta object literal for the '<em><b>Query</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IQUERY__QUERY = eINSTANCE.getIQuery_Query();

		/**
		 * The meta object literal for the '<em><b>Hints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference IQUERY__HINTS = eINSTANCE.getIQuery_Hints();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.INamedQuery <em>INamed Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.INamedQuery
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINamedQuery()
		 * @generated
		 */
		public static final EClass INAMED_QUERY = eINSTANCE.getINamedQuery();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.INamedNativeQuery <em>INamed Native Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.INamedNativeQuery
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINamedNativeQuery()
		 * @generated
		 */
		public static final EClass INAMED_NATIVE_QUERY = eINSTANCE.getINamedNativeQuery();

		/**
		 * The meta object literal for the '<em><b>Result Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute INAMED_NATIVE_QUERY__RESULT_CLASS = eINSTANCE.getINamedNativeQuery_ResultClass();

		/**
		 * The meta object literal for the '<em><b>Result Set Mapping</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute INAMED_NATIVE_QUERY__RESULT_SET_MAPPING = eINSTANCE.getINamedNativeQuery_ResultSetMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.IQueryHint <em>IQuery Hint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.IQueryHint
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIQueryHint()
		 * @generated
		 */
		public static final EClass IQUERY_HINT = eINSTANCE.getIQueryHint();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IQUERY_HINT__NAME = eINSTANCE.getIQueryHint_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IQUERY_HINT__VALUE = eINSTANCE.getIQueryHint_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType <em>Default Eager Fetch Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getDefaultEagerFetchType()
		 * @generated
		 */
		public static final EEnum DEFAULT_EAGER_FETCH_TYPE = eINSTANCE.getDefaultEagerFetchType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType <em>Default Lazy Fetch Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getDefaultLazyFetchType()
		 * @generated
		 */
		public static final EEnum DEFAULT_LAZY_FETCH_TYPE = eINSTANCE.getDefaultLazyFetchType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean <em>Default False Boolean</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getDefaultFalseBoolean()
		 * @generated
		 */
		public static final EEnum DEFAULT_FALSE_BOOLEAN = eINSTANCE.getDefaultFalseBoolean();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean <em>Default True Boolean</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getDefaultTrueBoolean()
		 * @generated
		 */
		public static final EEnum DEFAULT_TRUE_BOOLEAN = eINSTANCE.getDefaultTrueBoolean();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.TemporalType <em>Temporal Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getTemporalType()
		 * @generated
		 */
		public static final EEnum TEMPORAL_TYPE = eINSTANCE.getTemporalType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.InheritanceType <em>Inheritance Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.InheritanceType
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getInheritanceType()
		 * @generated
		 */
		public static final EEnum INHERITANCE_TYPE = eINSTANCE.getInheritanceType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.DiscriminatorType <em>Discriminator Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.DiscriminatorType
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getDiscriminatorType()
		 * @generated
		 */
		public static final EEnum DISCRIMINATOR_TYPE = eINSTANCE.getDiscriminatorType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.GenerationType <em>Generation Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.GenerationType
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getGenerationType()
		 * @generated
		 */
		public static final EEnum GENERATION_TYPE = eINSTANCE.getGenerationType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.EnumType <em>Enum Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.EnumType
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getEnumType()
		 * @generated
		 */
		public static final EEnum ENUM_TYPE = eINSTANCE.getEnumType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.internal.mappings.OrderingType <em>Ordering Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.internal.mappings.OrderingType
		 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getOrderingType()
		 * @generated
		 */
		public static final EEnum ORDERING_TYPE = eINSTANCE.getOrderingType();
	}
} //JpaCoreMappingsPackage
