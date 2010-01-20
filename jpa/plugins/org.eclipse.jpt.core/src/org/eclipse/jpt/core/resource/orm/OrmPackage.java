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

package org.eclipse.jpt.core.resource.orm;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.core.resource.persistence.PersistencePackage;
import org.eclipse.jpt.core.resource.persistence.v2_0.PersistenceV2_0Package;
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
 * @see org.eclipse.jpt.core.resource.orm.OrmFactory
 * @model kind="package"
 * @generated
 */
public class OrmPackage extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "orm";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.orm.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.core.resource.orm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final OrmPackage eINSTANCE = org.eclipse.jpt.core.resource.orm.OrmPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlAccessHolder <em>Xml Access Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlAccessHolder
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlAccessHolder()
	 * @generated
	 */
	public static final int XML_ACCESS_HOLDER = 10;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ACCESS_HOLDER__ACCESS = 0;

	/**
	 * The number of structural features of the '<em>Xml Access Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ACCESS_HOLDER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlAttributeMapping()
	 * @generated
	 */
	public static final int XML_ATTRIBUTE_MAPPING = 11;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING__ACCESS = OrmV2_0Package.XML_ATTRIBUTE_MAPPING_20__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING__NAME = OrmV2_0Package.XML_ATTRIBUTE_MAPPING_20_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Attribute Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_MAPPING_FEATURE_COUNT = OrmV2_0Package.XML_ATTRIBUTE_MAPPING_20_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlAttributeMapping <em>Abstract Xml Attribute Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlAttributeMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlAttributeMapping()
	 * @generated
	 */
	public static final int ABSTRACT_XML_ATTRIBUTE_MAPPING = 0;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS = XML_ATTRIBUTE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME = XML_ATTRIBUTE_MAPPING__NAME;

	/**
	 * The number of structural features of the '<em>Abstract Xml Attribute Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT = XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlNamedColumn <em>Abstract Xml Named Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlNamedColumn
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlNamedColumn()
	 * @generated
	 */
	public static final int ABSTRACT_XML_NAMED_COLUMN = 4;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_NAMED_COLUMN__NAME = 1;

	/**
	 * The number of structural features of the '<em>Abstract Xml Named Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlColumn <em>Abstract Xml Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlColumn
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlColumn()
	 * @generated
	 */
	public static final int ABSTRACT_XML_COLUMN = 1;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__COLUMN_DEFINITION = ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__NAME = ABSTRACT_XML_NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__INSERTABLE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__NULLABLE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__TABLE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__UNIQUE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN__UPDATABLE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Abstract Xml Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_COLUMN_FEATURE_COUNT = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlEmbedded <em>Abstract Xml Embedded</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlEmbedded
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlEmbedded()
	 * @generated
	 */
	public static final int ABSTRACT_XML_EMBEDDED = 2;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_EMBEDDED__ACCESS = ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_EMBEDDED__NAME = ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_EMBEDDED__ATTRIBUTE_OVERRIDES = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Xml Embedded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_EMBEDDED_FEATURE_COUNT = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping <em>Abstract Xml Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlRelationshipMapping()
	 * @generated
	 */
	public static final int ABSTRACT_XML_RELATIONSHIP_MAPPING = 5;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_RELATIONSHIP_MAPPING__ACCESS = ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_RELATIONSHIP_MAPPING__NAME = ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_RELATIONSHIP_MAPPING__TARGET_ENTITY = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_RELATIONSHIP_MAPPING__FETCH = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_RELATIONSHIP_MAPPING__CASCADE = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Abstract Xml Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping <em>Abstract Xml Multi Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlMultiRelationshipMapping()
	 * @generated
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING = 3;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ACCESS = ABSTRACT_XML_RELATIONSHIP_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__NAME = ABSTRACT_XML_RELATIONSHIP_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY = ABSTRACT_XML_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__FETCH = ABSTRACT_XML_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__CASCADE = ABSTRACT_XML_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_BY = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Map Key Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_TEMPORAL = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ENUMERATED = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ATTRIBUTE_OVERRIDES = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_JOIN_COLUMNS = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Abstract Xml Multi Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping <em>Abstract Xml Single Relationship Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlSingleRelationshipMapping()
	 * @generated
	 */
	public static final int ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING = 6;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__ACCESS = ABSTRACT_XML_RELATIONSHIP_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__NAME = ABSTRACT_XML_RELATIONSHIP_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY = ABSTRACT_XML_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__FETCH = ABSTRACT_XML_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__CASCADE = ABSTRACT_XML_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__ID = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Maps Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__MAPS_ID = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Abstract Xml Single Relationship Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT = ABSTRACT_XML_RELATIONSHIP_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlTable <em>Abstract Xml Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlTable
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlTable()
	 * @generated
	 */
	public static final int ABSTRACT_XML_TABLE = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__CATALOG = 1;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__SCHEMA = 2;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS = 3;

	/**
	 * The number of structural features of the '<em>Abstract Xml Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TABLE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping <em>Xml Type Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlTypeMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTypeMapping()
	 * @generated
	 */
	public static final int XML_TYPE_MAPPING = 80;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping <em>Abstract Xml Type Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlTypeMapping()
	 * @generated
	 */
	public static final int ABSTRACT_XML_TYPE_MAPPING = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping <em>Xml Join Columns Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinColumnsMapping()
	 * @generated
	 */
	public static final int XML_JOIN_COLUMNS_MAPPING = 44;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlAssociationOverride <em>Xml Association Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlAssociationOverride
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlAssociationOverride()
	 * @generated
	 */
	public static final int XML_ASSOCIATION_OVERRIDE = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer <em>Xml Association Override Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlAssociationOverrideContainer()
	 * @generated
	 */
	public static final int XML_ASSOCIATION_OVERRIDE_CONTAINER = 13;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverride <em>Xml Attribute Override</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeOverride
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlAttributeOverride()
	 * @generated
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE = 14;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverrideContainer <em>Xml Attribute Override Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeOverrideContainer
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlAttributeOverrideContainer()
	 * @generated
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE_CONTAINER = 15;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.Attributes <em>Attributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.Attributes
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAttributes()
	 * @generated
	 */
	public static final int ATTRIBUTES = 16;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlBasic <em>Xml Basic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlBasic
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlBasic()
	 * @generated
	 */
	public static final int XML_BASIC = 17;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.CascadeType <em>Cascade Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.CascadeType
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType()
	 * @generated
	 */
	public static final int CASCADE_TYPE = 18;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlReferenceTable <em>Abstract Xml Reference Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlReferenceTable
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlReferenceTable()
	 * @generated
	 */
	public static final int ABSTRACT_XML_REFERENCE_TABLE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_REFERENCE_TABLE__NAME = ABSTRACT_XML_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_REFERENCE_TABLE__CATALOG = ABSTRACT_XML_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_REFERENCE_TABLE__SCHEMA = ABSTRACT_XML_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_REFERENCE_TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_REFERENCE_TABLE__JOIN_COLUMNS = ABSTRACT_XML_TABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Xml Reference Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_REFERENCE_TABLE_FEATURE_COUNT = ABSTRACT_XML_TABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING__ACCESS = XML_ACCESS_HOLDER__ACCESS;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING__CLASS_NAME = XML_ACCESS_HOLDER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING__METADATA_COMPLETE = XML_ACCESS_HOLDER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING__DESCRIPTION = XML_ACCESS_HOLDER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING__ATTRIBUTES = XML_ACCESS_HOLDER_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Xml Type Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TYPE_MAPPING_FEATURE_COUNT = XML_ACCESS_HOLDER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TYPE_MAPPING__ACCESS = XML_TYPE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TYPE_MAPPING__CLASS_NAME = XML_TYPE_MAPPING__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TYPE_MAPPING__METADATA_COMPLETE = XML_TYPE_MAPPING__METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TYPE_MAPPING__DESCRIPTION = XML_TYPE_MAPPING__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TYPE_MAPPING__ATTRIBUTES = XML_TYPE_MAPPING__ATTRIBUTES;

	/**
	 * The number of structural features of the '<em>Abstract Xml Type Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT = XML_TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMNS_MAPPING__JOIN_COLUMNS = 0;

	/**
	 * The number of structural features of the '<em>Xml Join Columns Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMNS_MAPPING_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS = XML_JOIN_COLUMNS_MAPPING__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE__DESCRIPTION = XML_JOIN_COLUMNS_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE__JOIN_TABLE = XML_JOIN_COLUMNS_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE__NAME = XML_JOIN_COLUMNS_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Association Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE_FEATURE_COUNT = XML_JOIN_COLUMNS_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE_CONTAINER__ASSOCIATION_OVERRIDES = 0;

	/**
	 * The number of structural features of the '<em>Xml Association Override Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ASSOCIATION_OVERRIDE_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE__DESCRIPTION = OrmV2_0Package.XML_ATTRIBUTE_OVERRIDE_20__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE__COLUMN = OrmV2_0Package.XML_ATTRIBUTE_OVERRIDE_20_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE__NAME = OrmV2_0Package.XML_ATTRIBUTE_OVERRIDE_20_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Attribute Override</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE_FEATURE_COUNT = OrmV2_0Package.XML_ATTRIBUTE_OVERRIDE_20_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE_CONTAINER__ATTRIBUTE_OVERRIDES = 0;

	/**
	 * The number of structural features of the '<em>Xml Attribute Override Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ATTRIBUTE_OVERRIDE_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__DESCRIPTION = OrmV2_0Package.XML_ATTRIBUTES_20__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Element Collections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__ELEMENT_COLLECTIONS = OrmV2_0Package.XML_ATTRIBUTES_20__ELEMENT_COLLECTIONS;

	/**
	 * The feature id for the '<em><b>Ids</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__IDS = OrmV2_0Package.XML_ATTRIBUTES_20_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Embedded Ids</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__EMBEDDED_IDS = OrmV2_0Package.XML_ATTRIBUTES_20_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Basics</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__BASICS = OrmV2_0Package.XML_ATTRIBUTES_20_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Versions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__VERSIONS = OrmV2_0Package.XML_ATTRIBUTES_20_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Many To Ones</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__MANY_TO_ONES = OrmV2_0Package.XML_ATTRIBUTES_20_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>One To Manys</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__ONE_TO_MANYS = OrmV2_0Package.XML_ATTRIBUTES_20_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>One To Ones</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__ONE_TO_ONES = OrmV2_0Package.XML_ATTRIBUTES_20_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Many To Manys</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__MANY_TO_MANYS = OrmV2_0Package.XML_ATTRIBUTES_20_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Embeddeds</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__EMBEDDEDS = OrmV2_0Package.XML_ATTRIBUTES_20_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Transients</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES__TRANSIENTS = OrmV2_0Package.XML_ATTRIBUTES_20_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Attributes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTES_FEATURE_COUNT = OrmV2_0Package.XML_ATTRIBUTES_20_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__ACCESS = ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__NAME = ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__COLUMN = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__LOB = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__TEMPORAL = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__ENUMERATED = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__FETCH = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC__OPTIONAL = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Xml Basic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_BASIC_FEATURE_COUNT = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Cascade All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_ALL = 0;

	/**
	 * The feature id for the '<em><b>Cascade Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_PERSIST = 1;

	/**
	 * The feature id for the '<em><b>Cascade Merge</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_MERGE = 2;

	/**
	 * The feature id for the '<em><b>Cascade Remove</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_REMOVE = 3;

	/**
	 * The feature id for the '<em><b>Cascade Refresh</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE__CASCADE_REFRESH = 4;

	/**
	 * The number of structural features of the '<em>Cascade Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int CASCADE_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlCollectionTable <em>Xml Collection Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlCollectionTable
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlCollectionTable()
	 * @generated
	 */
	public static final int XML_COLLECTION_TABLE = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__NAME = ABSTRACT_XML_REFERENCE_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__CATALOG = ABSTRACT_XML_REFERENCE_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__SCHEMA = ABSTRACT_XML_REFERENCE_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_XML_REFERENCE_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE__JOIN_COLUMNS = ABSTRACT_XML_REFERENCE_TABLE__JOIN_COLUMNS;

	/**
	 * The number of structural features of the '<em>Xml Collection Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLLECTION_TABLE_FEATURE_COUNT = ABSTRACT_XML_REFERENCE_TABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlColumn <em>Xml Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlColumn
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlColumn()
	 * @generated
	 */
	public static final int XML_COLUMN = 20;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__COLUMN_DEFINITION = ABSTRACT_XML_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__NAME = ABSTRACT_XML_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__INSERTABLE = ABSTRACT_XML_COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__NULLABLE = ABSTRACT_XML_COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__TABLE = ABSTRACT_XML_COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__UNIQUE = ABSTRACT_XML_COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__UPDATABLE = ABSTRACT_XML_COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__LENGTH = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Precision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__PRECISION = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN__SCALE = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_COLUMN_FEATURE_COUNT = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.ColumnMapping <em>Column Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.ColumnMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getColumnMapping()
	 * @generated
	 */
	public static final int COLUMN_MAPPING = 21;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_MAPPING__COLUMN = 0;

	/**
	 * The number of structural features of the '<em>Column Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_MAPPING_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.ColumnResult <em>Column Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.ColumnResult
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getColumnResult()
	 * @generated
	 */
	public static final int COLUMN_RESULT = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_RESULT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Column Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_RESULT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping <em>Xml Convertible Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlConvertibleMapping()
	 * @generated
	 */
	public static final int XML_CONVERTIBLE_MAPPING = 23;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING__LOB = 0;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING__TEMPORAL = 1;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING__ENUMERATED = 2;

	/**
	 * The number of structural features of the '<em>Xml Convertible Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_CONVERTIBLE_MAPPING_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn <em>Xml Discriminator Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlDiscriminatorColumn()
	 * @generated
	 */
	public static final int XML_DISCRIMINATOR_COLUMN = 24;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION = ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__NAME = ABSTRACT_XML_NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Discriminator Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN__LENGTH = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Discriminator Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_DISCRIMINATOR_COLUMN_FEATURE_COUNT = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlElementCollection <em>Xml Element Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlElementCollection
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlElementCollection()
	 * @generated
	 */
	public static final int XML_ELEMENT_COLLECTION = 25;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ACCESS = ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__NAME = ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__LOB = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__TEMPORAL = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ENUMERATED = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ORDER_COLUMN = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ORDER_BY = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Target Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__TARGET_CLASS = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__FETCH = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_CLASS = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Map Key Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__COLUMN = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Collection Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION__COLLECTION_TABLE = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 17;

	/**
	 * The number of structural features of the '<em>Xml Element Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ELEMENT_COLLECTION_FEATURE_COUNT = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 18;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlEmbeddable <em>Xml Embeddable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlEmbeddable
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlEmbeddable()
	 * @generated
	 */
	public static final int XML_EMBEDDABLE = 26;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__ACCESS = ABSTRACT_XML_TYPE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__CLASS_NAME = ABSTRACT_XML_TYPE_MAPPING__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__METADATA_COMPLETE = ABSTRACT_XML_TYPE_MAPPING__METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__DESCRIPTION = ABSTRACT_XML_TYPE_MAPPING__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE__ATTRIBUTES = ABSTRACT_XML_TYPE_MAPPING__ATTRIBUTES;

	/**
	 * The number of structural features of the '<em>Xml Embeddable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDABLE_FEATURE_COUNT = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlEmbedded <em>Xml Embedded</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlEmbedded
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlEmbedded()
	 * @generated
	 */
	public static final int XML_EMBEDDED = 27;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ACCESS = ABSTRACT_XML_EMBEDDED__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__NAME = ABSTRACT_XML_EMBEDDED__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ATTRIBUTE_OVERRIDES = ABSTRACT_XML_EMBEDDED__ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED__ASSOCIATION_OVERRIDES = ABSTRACT_XML_EMBEDDED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Embedded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_FEATURE_COUNT = ABSTRACT_XML_EMBEDDED_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlEmbeddedId
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlEmbeddedId()
	 * @generated
	 */
	public static final int XML_EMBEDDED_ID = 28;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__ACCESS = ABSTRACT_XML_EMBEDDED__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__NAME = ABSTRACT_XML_EMBEDDED__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID__ATTRIBUTE_OVERRIDES = ABSTRACT_XML_EMBEDDED__ATTRIBUTE_OVERRIDES;

	/**
	 * The number of structural features of the '<em>Xml Embedded Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EMBEDDED_ID_FEATURE_COUNT = ABSTRACT_XML_EMBEDDED_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlEntity <em>Xml Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlEntity()
	 * @generated
	 */
	public static final int XML_ENTITY = 29;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ACCESS = ABSTRACT_XML_TYPE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CLASS_NAME = ABSTRACT_XML_TYPE_MAPPING__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__METADATA_COMPLETE = ABSTRACT_XML_TYPE_MAPPING__METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__DESCRIPTION = ABSTRACT_XML_TYPE_MAPPING__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ATTRIBUTES = ABSTRACT_XML_TYPE_MAPPING__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__NAMED_QUERIES = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__NAMED_NATIVE_QUERIES = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SEQUENCE_GENERATOR = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__TABLE_GENERATOR = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PRE_PERSIST = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__POST_PERSIST = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PRE_REMOVE = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__POST_REMOVE = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PRE_UPDATE = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__POST_UPDATE = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__POST_LOAD = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ATTRIBUTE_OVERRIDES = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Association Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ASSOCIATION_OVERRIDES = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__CACHEABLE = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__NAME = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__TABLE = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Secondary Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SECONDARY_TABLES = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__PRIMARY_KEY_JOIN_COLUMNS = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Id Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ID_CLASS = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Inheritance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__INHERITANCE = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__DISCRIMINATOR_VALUE = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 20;

	/**
	 * The feature id for the '<em><b>Discriminator Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__DISCRIMINATOR_COLUMN = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 21;

	/**
	 * The feature id for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__SQL_RESULT_SET_MAPPINGS = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 22;

	/**
	 * The feature id for the '<em><b>Exclude Default Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__EXCLUDE_DEFAULT_LISTENERS = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 23;

	/**
	 * The feature id for the '<em><b>Exclude Superclass Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__EXCLUDE_SUPERCLASS_LISTENERS = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 24;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY__ENTITY_LISTENERS = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 25;

	/**
	 * The number of structural features of the '<em>Xml Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_FEATURE_COUNT = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 26;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer <em>Xml Event Method Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlEventMethodContainer()
	 * @generated
	 */
	public static final int XML_EVENT_METHOD_CONTAINER = 35;

	/**
	 * The feature id for the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EVENT_METHOD_CONTAINER__PRE_PERSIST = 0;

	/**
	 * The feature id for the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EVENT_METHOD_CONTAINER__POST_PERSIST = 1;

	/**
	 * The feature id for the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EVENT_METHOD_CONTAINER__PRE_REMOVE = 2;

	/**
	 * The feature id for the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EVENT_METHOD_CONTAINER__POST_REMOVE = 3;

	/**
	 * The feature id for the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EVENT_METHOD_CONTAINER__PRE_UPDATE = 4;

	/**
	 * The feature id for the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EVENT_METHOD_CONTAINER__POST_UPDATE = 5;

	/**
	 * The feature id for the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EVENT_METHOD_CONTAINER__POST_LOAD = 6;

	/**
	 * The number of structural features of the '<em>Xml Event Method Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_EVENT_METHOD_CONTAINER_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.EntityListener <em>Entity Listener</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.EntityListener
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityListener()
	 * @generated
	 */
	public static final int ENTITY_LISTENER = 30;

	/**
	 * The feature id for the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__PRE_PERSIST = XML_EVENT_METHOD_CONTAINER__PRE_PERSIST;

	/**
	 * The feature id for the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__POST_PERSIST = XML_EVENT_METHOD_CONTAINER__POST_PERSIST;

	/**
	 * The feature id for the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__PRE_REMOVE = XML_EVENT_METHOD_CONTAINER__PRE_REMOVE;

	/**
	 * The feature id for the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__POST_REMOVE = XML_EVENT_METHOD_CONTAINER__POST_REMOVE;

	/**
	 * The feature id for the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__PRE_UPDATE = XML_EVENT_METHOD_CONTAINER__PRE_UPDATE;

	/**
	 * The feature id for the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__POST_UPDATE = XML_EVENT_METHOD_CONTAINER__POST_UPDATE;

	/**
	 * The feature id for the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__POST_LOAD = XML_EVENT_METHOD_CONTAINER__POST_LOAD;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__DESCRIPTION = XML_EVENT_METHOD_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER__CLASS_NAME = XML_EVENT_METHOD_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Entity Listener</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENER_FEATURE_COUNT = XML_EVENT_METHOD_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.EntityListeners <em>Entity Listeners</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.EntityListeners
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityListeners()
	 * @generated
	 */
	public static final int ENTITY_LISTENERS = 31;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENERS__ENTITY_LISTENERS = 0;

	/**
	 * The number of structural features of the '<em>Entity Listeners</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_LISTENERS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlEntityMappings()
	 * @generated
	 */
	public static final int XML_ENTITY_MAPPINGS = 32;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.EntityResult <em>Entity Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.EntityResult
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityResult()
	 * @generated
	 */
	public static final int ENTITY_RESULT = 33;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.EventMethod <em>Event Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.EventMethod
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEventMethod()
	 * @generated
	 */
	public static final int EVENT_METHOD = 34;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.FieldResult <em>Field Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.FieldResult
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getFieldResult()
	 * @generated
	 */
	public static final int FIELD_RESULT = 36;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlGeneratedValue <em>Xml Generated Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlGeneratedValue
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlGeneratedValue()
	 * @generated
	 */
	public static final int XML_GENERATED_VALUE = 37;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlGenerator <em>Xml Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlGenerator
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlGenerator()
	 * @generated
	 */
	public static final int XML_GENERATOR = 38;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer <em>Xml Generator Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlGeneratorContainer()
	 * @generated
	 */
	public static final int XML_GENERATOR_CONTAINER = 39;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlId <em>Xml Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlId
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlId()
	 * @generated
	 */
	public static final int XML_ID = 40;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlIdClass <em>Xml Id Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlIdClass
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlIdClass()
	 * @generated
	 */
	public static final int XML_ID_CLASS = 41;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.Inheritance <em>Inheritance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.Inheritance
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getInheritance()
	 * @generated
	 */
	public static final int INHERITANCE = 42;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn <em>Xml Join Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinColumn
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinColumn()
	 * @generated
	 */
	public static final int XML_JOIN_COLUMN = 43;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlJoinTable <em>Xml Join Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinTable
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinTable()
	 * @generated
	 */
	public static final int XML_JOIN_TABLE = 45;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping <em>Xml Join Table Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinTableMapping()
	 * @generated
	 */
	public static final int XML_JOIN_TABLE_MAPPING = 46;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.Lob <em>Lob</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.Lob
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getLob()
	 * @generated
	 */
	public static final int LOB = 47;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlManyToMany
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlManyToMany()
	 * @generated
	 */
	public static final int XML_MANY_TO_MANY = 48;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlManyToOne
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlManyToOne()
	 * @generated
	 */
	public static final int XML_MANY_TO_ONE = 49;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.MapKey <em>Map Key</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.MapKey
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getMapKey()
	 * @generated
	 */
	public static final int MAP_KEY = 50;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlMapKeyClass <em>Xml Map Key Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlMapKeyClass
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMapKeyClass()
	 * @generated
	 */
	public static final int XML_MAP_KEY_CLASS = 51;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlMappedByMapping <em>Xml Mapped By Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlMappedByMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMappedByMapping()
	 * @generated
	 */
	public static final int XML_MAPPED_BY_MAPPING = 52;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMappedSuperclass()
	 * @generated
	 */
	public static final int XML_MAPPED_SUPERCLASS = 53;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlQuery <em>Xml Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlQuery
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlQuery()
	 * @generated
	 */
	public static final int XML_QUERY = 71;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery <em>Xml Named Native Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlNamedNativeQuery()
	 * @generated
	 */
	public static final int XML_NAMED_NATIVE_QUERY = 54;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlNamedQuery <em>Xml Named Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlNamedQuery
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlNamedQuery()
	 * @generated
	 */
	public static final int XML_NAMED_QUERY = 55;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping <em>Xml Null Attribute Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlNullAttributeMapping()
	 * @generated
	 */
	public static final int XML_NULL_ATTRIBUTE_MAPPING = 56;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlOneToMany
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOneToMany()
	 * @generated
	 */
	public static final int XML_ONE_TO_MANY = 57;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlOneToOne
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOneToOne()
	 * @generated
	 */
	public static final int XML_ONE_TO_ONE = 58;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlOrderColumn <em>Xml Order Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlOrderColumn
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOrderColumn()
	 * @generated
	 */
	public static final int XML_ORDER_COLUMN = 59;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults <em>Xml Persistence Unit Defaults</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlPersistenceUnitDefaults()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS = 60;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata <em>Xml Persistence Unit Metadata</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlPersistenceUnitMetadata()
	 * @generated
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA = 61;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.PostLoad <em>Post Load</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.PostLoad
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPostLoad()
	 * @generated
	 */
	public static final int POST_LOAD = 63;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.PostPersist <em>Post Persist</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.PostPersist
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPostPersist()
	 * @generated
	 */
	public static final int POST_PERSIST = 64;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.PostRemove <em>Post Remove</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.PostRemove
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPostRemove()
	 * @generated
	 */
	public static final int POST_REMOVE = 65;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.PostUpdate <em>Post Update</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.PostUpdate
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPostUpdate()
	 * @generated
	 */
	public static final int POST_UPDATE = 66;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.PrePersist <em>Pre Persist</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.PrePersist
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPrePersist()
	 * @generated
	 */
	public static final int PRE_PERSIST = 67;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.PreRemove <em>Pre Remove</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.PreRemove
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPreRemove()
	 * @generated
	 */
	public static final int PRE_REMOVE = 68;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.PreUpdate <em>Pre Update</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.PreUpdate
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPreUpdate()
	 * @generated
	 */
	public static final int PRE_UPDATE = 69;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn <em>Xml Primary Key Join Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlPrimaryKeyJoinColumn()
	 * @generated
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN = 70;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlQueryContainer <em>Xml Query Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlQueryContainer
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlQueryContainer()
	 * @generated
	 */
	public static final int XML_QUERY_CONTAINER = 72;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__VERSION = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT__VERSION;

	/**
	 * The feature id for the '<em><b>Schema Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__SCHEMA_LOCATION = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT__SCHEMA_LOCATION;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__NAMED_QUERIES = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__ACCESS = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__DESCRIPTION = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Persistence Unit Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__PACKAGE = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__SCHEMA = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__CATALOG = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Sequence Generators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__SEQUENCE_GENERATORS = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Table Generators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__TABLE_GENERATORS = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Mapped Superclasses</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__MAPPED_SUPERCLASSES = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Entities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__ENTITIES = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Embeddables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS__EMBEDDABLES = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 13;

	/**
	 * The number of structural features of the '<em>Xml Entity Mappings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ENTITY_MAPPINGS_FEATURE_COUNT = CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Discriminator Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_RESULT__DISCRIMINATOR_COLUMN = 0;

	/**
	 * The feature id for the '<em><b>Entity Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_RESULT__ENTITY_CLASS = 1;

	/**
	 * The feature id for the '<em><b>Field Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_RESULT__FIELD_RESULTS = 2;

	/**
	 * The number of structural features of the '<em>Entity Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_RESULT_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EVENT_METHOD__DESCRIPTION = OrmV2_0Package.XML_EVENT_METHOD_20__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EVENT_METHOD__METHOD_NAME = OrmV2_0Package.XML_EVENT_METHOD_20_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Event Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EVENT_METHOD_FEATURE_COUNT = OrmV2_0Package.XML_EVENT_METHOD_20_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_RESULT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_RESULT__COLUMN = 1;

	/**
	 * The number of structural features of the '<em>Field Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_RESULT_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATED_VALUE__GENERATOR = 0;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATED_VALUE__STRATEGY = 1;

	/**
	 * The number of structural features of the '<em>Xml Generated Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATED_VALUE_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__DESCRIPTION = OrmV2_0Package.XML_GENERATOR_20__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__NAME = OrmV2_0Package.XML_GENERATOR_20_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__INITIAL_VALUE = OrmV2_0Package.XML_GENERATOR_20_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR__ALLOCATION_SIZE = OrmV2_0Package.XML_GENERATOR_20_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR_FEATURE_COUNT = OrmV2_0Package.XML_GENERATOR_20_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR_CONTAINER__SEQUENCE_GENERATOR = 0;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR_CONTAINER__TABLE_GENERATOR = 1;

	/**
	 * The number of structural features of the '<em>Xml Generator Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_GENERATOR_CONTAINER_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__ACCESS = ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__NAME = ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__COLUMN = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__LOB = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__TEMPORAL = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__ENUMERATED = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__SEQUENCE_GENERATOR = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__TABLE_GENERATOR = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID__GENERATED_VALUE = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Xml Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_FEATURE_COUNT = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_CLASS__CLASS_NAME = 0;

	/**
	 * The number of structural features of the '<em>Xml Id Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ID_CLASS_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INHERITANCE__STRATEGY = 0;

	/**
	 * The number of structural features of the '<em>Inheritance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int INHERITANCE_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__COLUMN_DEFINITION = ABSTRACT_XML_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__NAME = ABSTRACT_XML_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__INSERTABLE = ABSTRACT_XML_COLUMN__INSERTABLE;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__NULLABLE = ABSTRACT_XML_COLUMN__NULLABLE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__TABLE = ABSTRACT_XML_COLUMN__TABLE;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__UNIQUE = ABSTRACT_XML_COLUMN__UNIQUE;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__UPDATABLE = ABSTRACT_XML_COLUMN__UPDATABLE;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN__REFERENCED_COLUMN_NAME = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Join Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_COLUMN_FEATURE_COUNT = ABSTRACT_XML_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlReferenceTable <em>Xml Reference Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlReferenceTable
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlReferenceTable()
	 * @generated
	 */
	public static final int XML_REFERENCE_TABLE = 62;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__NAME = ABSTRACT_XML_REFERENCE_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__CATALOG = ABSTRACT_XML_REFERENCE_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__SCHEMA = ABSTRACT_XML_REFERENCE_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_XML_REFERENCE_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__JOIN_COLUMNS = ABSTRACT_XML_REFERENCE_TABLE__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Inverse Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS = ABSTRACT_XML_REFERENCE_TABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Join Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE_FEATURE_COUNT = ABSTRACT_XML_REFERENCE_TABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE_MAPPING__JOIN_TABLE = 0;

	/**
	 * The number of structural features of the '<em>Xml Join Table Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_JOIN_TABLE_MAPPING_FEATURE_COUNT = 1;

	/**
	 * The number of structural features of the '<em>Lob</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int LOB_FEATURE_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ACCESS = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__NAME = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__TARGET_ENTITY = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__FETCH = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__CASCADE = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAPPED_BY = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__JOIN_TABLE = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ORDER_COLUMN = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__ORDER_BY = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_CLASS = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS;

	/**
	 * The feature id for the '<em><b>Map Key Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_TEMPORAL = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_TEMPORAL;

	/**
	 * The feature id for the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_ENUMERATED = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ENUMERATED;

	/**
	 * The feature id for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_COLUMN = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN;

	/**
	 * The feature id for the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY__MAP_KEY = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY;

	/**
	 * The number of structural features of the '<em>Xml Many To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_MANY_FEATURE_COUNT = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__ACCESS = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__NAME = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__TARGET_ENTITY = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__FETCH = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__CASCADE = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__JOIN_TABLE = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__JOIN_COLUMNS = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__ID = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__ID;

	/**
	 * The feature id for the '<em><b>Maps Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__MAPS_ID = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__MAPS_ID;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE__OPTIONAL = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL;

	/**
	 * The number of structural features of the '<em>Xml Many To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MANY_TO_ONE_FEATURE_COUNT = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAP_KEY__NAME = 0;

	/**
	 * The number of structural features of the '<em>Map Key</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int MAP_KEY_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_CLASS__CLASS_NAME = OrmV2_0Package.XML_MAP_KEY_CLASS_20__CLASS_NAME;

	/**
	 * The number of structural features of the '<em>Xml Map Key Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAP_KEY_CLASS_FEATURE_COUNT = OrmV2_0Package.XML_MAP_KEY_CLASS_20_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_BY_MAPPING__MAPPED_BY = 0;

	/**
	 * The number of structural features of the '<em>Xml Mapped By Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_BY_MAPPING_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ACCESS = ABSTRACT_XML_TYPE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__CLASS_NAME = ABSTRACT_XML_TYPE_MAPPING__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__METADATA_COMPLETE = ABSTRACT_XML_TYPE_MAPPING__METADATA_COMPLETE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__DESCRIPTION = ABSTRACT_XML_TYPE_MAPPING__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ATTRIBUTES = ABSTRACT_XML_TYPE_MAPPING__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Id Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ID_CLASS = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Exclude Default Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Exclude Superclass Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Xml Mapped Superclass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_MAPPED_SUPERCLASS_FEATURE_COUNT = ABSTRACT_XML_TYPE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY__DESCRIPTION = OrmV2_0Package.XML_QUERY_20__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY__NAME = OrmV2_0Package.XML_QUERY_20_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY__QUERY = OrmV2_0Package.XML_QUERY_20_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY__HINTS = OrmV2_0Package.XML_QUERY_20_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_FEATURE_COUNT = OrmV2_0Package.XML_QUERY_20_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__DESCRIPTION = XML_QUERY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__NAME = XML_QUERY__NAME;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__QUERY = XML_QUERY__QUERY;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__HINTS = XML_QUERY__HINTS;

	/**
	 * The feature id for the '<em><b>Result Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__RESULT_CLASS = XML_QUERY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Result Set Mapping</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING = XML_QUERY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Named Native Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_NATIVE_QUERY_FEATURE_COUNT = XML_QUERY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY__DESCRIPTION = XML_QUERY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY__NAME = XML_QUERY__NAME;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY__QUERY = XML_QUERY__QUERY;

	/**
	 * The feature id for the '<em><b>Hints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY__HINTS = XML_QUERY__HINTS;

	/**
	 * The feature id for the '<em><b>Lock Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY__LOCK_MODE = XML_QUERY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Named Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NAMED_QUERY_FEATURE_COUNT = XML_QUERY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NULL_ATTRIBUTE_MAPPING__ACCESS = ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NULL_ATTRIBUTE_MAPPING__NAME = ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;

	/**
	 * The number of structural features of the '<em>Xml Null Attribute Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_NULL_ATTRIBUTE_MAPPING_FEATURE_COUNT = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ACCESS = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__NAME = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__TARGET_ENTITY = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__FETCH = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__CASCADE = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAPPED_BY = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__JOIN_TABLE = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ORDER_COLUMN = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ORDER_BY = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_BY;

	/**
	 * The feature id for the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_CLASS = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS;

	/**
	 * The feature id for the '<em><b>Map Key Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_TEMPORAL = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_TEMPORAL;

	/**
	 * The feature id for the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_ENUMERATED = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ENUMERATED;

	/**
	 * The feature id for the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ATTRIBUTE_OVERRIDES;

	/**
	 * The feature id for the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_COLUMN = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN;

	/**
	 * The feature id for the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY_JOIN_COLUMNS = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__MAP_KEY = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__JOIN_COLUMNS = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Orphan Removal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY__ORPHAN_REMOVAL = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml One To Many</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_MANY_FEATURE_COUNT = ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__ACCESS = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__NAME = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__TARGET_ENTITY = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__TARGET_ENTITY;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__FETCH = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__FETCH;

	/**
	 * The feature id for the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__CASCADE = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__CASCADE;

	/**
	 * The feature id for the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__JOIN_TABLE = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__JOIN_COLUMNS = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__ID = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__ID;

	/**
	 * The feature id for the '<em><b>Maps Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__MAPS_ID = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__MAPS_ID;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__OPTIONAL = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__MAPPED_BY = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Orphan Removal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__ORPHAN_REMOVAL = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml One To One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ONE_TO_ONE_FEATURE_COUNT = ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__COLUMN_DEFINITION = ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__NAME = ABSTRACT_XML_NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__NULLABLE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__INSERTABLE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN__UPDATABLE = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Order Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDER_COLUMN_FEATURE_COUNT = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS = XML_ACCESS_HOLDER__ACCESS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__DESCRIPTION = XML_ACCESS_HOLDER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Delimited Identifiers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__DELIMITED_IDENTIFIERS = XML_ACCESS_HOLDER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA = XML_ACCESS_HOLDER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG = XML_ACCESS_HOLDER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Cascade Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST = XML_ACCESS_HOLDER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Entity Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS = XML_ACCESS_HOLDER_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Xml Persistence Unit Defaults</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_DEFAULTS_FEATURE_COUNT = XML_ACCESS_HOLDER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA__DESCRIPTION = OrmV2_0Package.XML_PERSISTENCE_UNIT_METADATA_20__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Xml Mapping Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE = OrmV2_0Package.XML_PERSISTENCE_UNIT_METADATA_20_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Persistence Unit Defaults</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS = OrmV2_0Package.XML_PERSISTENCE_UNIT_METADATA_20_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Persistence Unit Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PERSISTENCE_UNIT_METADATA_FEATURE_COUNT = OrmV2_0Package.XML_PERSISTENCE_UNIT_METADATA_20_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_REFERENCE_TABLE__JOIN_COLUMNS = 0;

	/**
	 * The number of structural features of the '<em>Xml Reference Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_REFERENCE_TABLE_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_LOAD__DESCRIPTION = EVENT_METHOD__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_LOAD__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Post Load</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_LOAD_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_PERSIST__DESCRIPTION = EVENT_METHOD__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_PERSIST__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Post Persist</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_PERSIST_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_REMOVE__DESCRIPTION = EVENT_METHOD__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_REMOVE__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Post Remove</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_REMOVE_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_UPDATE__DESCRIPTION = EVENT_METHOD__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_UPDATE__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Post Update</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int POST_UPDATE_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_PERSIST__DESCRIPTION = EVENT_METHOD__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_PERSIST__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Pre Persist</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_PERSIST_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_REMOVE__DESCRIPTION = EVENT_METHOD__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_REMOVE__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Pre Remove</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_REMOVE_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_UPDATE__DESCRIPTION = EVENT_METHOD__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_UPDATE__METHOD_NAME = EVENT_METHOD__METHOD_NAME;

	/**
	 * The number of structural features of the '<em>Pre Update</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PRE_UPDATE_FEATURE_COUNT = EVENT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION = ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN__NAME = ABSTRACT_XML_NAMED_COLUMN__NAME;

	/**
	 * The feature id for the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Primary Key Join Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_PRIMARY_KEY_JOIN_COLUMN_FEATURE_COUNT = ABSTRACT_XML_NAMED_COLUMN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Named Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_CONTAINER__NAMED_QUERIES = 0;

	/**
	 * The feature id for the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_CONTAINER__NAMED_NATIVE_QUERIES = 1;

	/**
	 * The number of structural features of the '<em>Xml Query Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_CONTAINER_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlQueryHint <em>Xml Query Hint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlQueryHint
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlQueryHint()
	 * @generated
	 */
	public static final int XML_QUERY_HINT = 73;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_HINT__DESCRIPTION = OrmV2_0Package.XML_QUERY_HINT_20__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_HINT__NAME = OrmV2_0Package.XML_QUERY_HINT_20_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_HINT__VALUE = OrmV2_0Package.XML_QUERY_HINT_20_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Xml Query Hint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_QUERY_HINT_FEATURE_COUNT = OrmV2_0Package.XML_QUERY_HINT_20_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlSecondaryTable <em>Xml Secondary Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlSecondaryTable
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlSecondaryTable()
	 * @generated
	 */
	public static final int XML_SECONDARY_TABLE = 74;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__NAME = ABSTRACT_XML_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__CATALOG = ABSTRACT_XML_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__SCHEMA = ABSTRACT_XML_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS = ABSTRACT_XML_TABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Secondary Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SECONDARY_TABLE_FEATURE_COUNT = ABSTRACT_XML_TABLE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator <em>Xml Sequence Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlSequenceGenerator()
	 * @generated
	 */
	public static final int XML_SEQUENCE_GENERATOR = 75;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__DESCRIPTION = XML_GENERATOR__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__NAME = XML_GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__INITIAL_VALUE = XML_GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__ALLOCATION_SIZE = XML_GENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__CATALOG = XML_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__SCHEMA = XML_GENERATOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR__SEQUENCE_NAME = XML_GENERATOR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Xml Sequence Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_SEQUENCE_GENERATOR_FEATURE_COUNT = XML_GENERATOR_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.SqlResultSetMapping <em>Sql Result Set Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.SqlResultSetMapping
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getSqlResultSetMapping()
	 * @generated
	 */
	public static final int SQL_RESULT_SET_MAPPING = 76;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING__DESCRIPTION = OrmV2_0Package.XML_SQL_RESULT_SET_MAPPING_20__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING__NAME = OrmV2_0Package.XML_SQL_RESULT_SET_MAPPING_20_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entity Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING__ENTITY_RESULTS = OrmV2_0Package.XML_SQL_RESULT_SET_MAPPING_20_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Column Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING__COLUMN_RESULTS = OrmV2_0Package.XML_SQL_RESULT_SET_MAPPING_20_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Sql Result Set Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int SQL_RESULT_SET_MAPPING_FEATURE_COUNT = OrmV2_0Package.XML_SQL_RESULT_SET_MAPPING_20_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlTable <em>Xml Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlTable
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTable()
	 * @generated
	 */
	public static final int XML_TABLE = 77;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__NAME = ABSTRACT_XML_TABLE__NAME;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__CATALOG = ABSTRACT_XML_TABLE__CATALOG;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__SCHEMA = ABSTRACT_XML_TABLE__SCHEMA;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE__UNIQUE_CONSTRAINTS = ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS;

	/**
	 * The number of structural features of the '<em>Xml Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_FEATURE_COUNT = ABSTRACT_XML_TABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlTableGenerator <em>Xml Table Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlTableGenerator
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTableGenerator()
	 * @generated
	 */
	public static final int XML_TABLE_GENERATOR = 78;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__DESCRIPTION = XML_GENERATOR__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__NAME = XML_GENERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__INITIAL_VALUE = XML_GENERATOR__INITIAL_VALUE;

	/**
	 * The feature id for the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__ALLOCATION_SIZE = XML_GENERATOR__ALLOCATION_SIZE;

	/**
	 * The feature id for the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__TABLE = XML_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__CATALOG = XML_GENERATOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__SCHEMA = XML_GENERATOR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__PK_COLUMN_NAME = XML_GENERATOR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__VALUE_COLUMN_NAME = XML_GENERATOR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__PK_COLUMN_VALUE = XML_GENERATOR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR__UNIQUE_CONSTRAINTS = XML_GENERATOR_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Xml Table Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TABLE_GENERATOR_FEATURE_COUNT = XML_GENERATOR_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlTransient <em>Xml Transient</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlTransient
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTransient()
	 * @generated
	 */
	public static final int XML_TRANSIENT = 79;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT__ACCESS = ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT__NAME = ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;

	/**
	 * The number of structural features of the '<em>Xml Transient</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_TRANSIENT_FEATURE_COUNT = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint <em>Xml Unique Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlUniqueConstraint()
	 * @generated
	 */
	public static final int XML_UNIQUE_CONSTRAINT = 81;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNIQUE_CONSTRAINT__NAME = OrmV2_0Package.XML_UNIQUE_CONSTRAINT_20__NAME;

	/**
	 * The feature id for the '<em><b>Column Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNIQUE_CONSTRAINT__COLUMN_NAMES = OrmV2_0Package.XML_UNIQUE_CONSTRAINT_20_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Unique Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_UNIQUE_CONSTRAINT_FEATURE_COUNT = OrmV2_0Package.XML_UNIQUE_CONSTRAINT_20_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlVersion <em>Xml Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlVersion
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlVersion()
	 * @generated
	 */
	public static final int XML_VERSION = 82;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__ACCESS = ABSTRACT_XML_ATTRIBUTE_MAPPING__ACCESS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__NAME = ABSTRACT_XML_ATTRIBUTE_MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__COLUMN = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__LOB = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Temporal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__TEMPORAL = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Enumerated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION__ENUMERATED = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Xml Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_VERSION_FEATURE_COUNT = ABSTRACT_XML_ATTRIBUTE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.XmlOrderable <em>Xml Orderable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.XmlOrderable
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOrderable()
	 * @generated
	 */
	public static final int XML_ORDERABLE = 83;

	/**
	 * The feature id for the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDERABLE__ORDER_COLUMN = OrmV2_0Package.XML_ORDERABLE_20__ORDER_COLUMN;

	/**
	 * The feature id for the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDERABLE__ORDER_BY = OrmV2_0Package.XML_ORDERABLE_20_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Xml Orderable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int XML_ORDERABLE_FEATURE_COUNT = OrmV2_0Package.XML_ORDERABLE_20_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.AccessType <em>Access Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.AccessType
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAccessType()
	 * @generated
	 */
	public static final int ACCESS_TYPE = 84;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.DiscriminatorType <em>Discriminator Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.DiscriminatorType
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getDiscriminatorType()
	 * @generated
	 */
	public static final int DISCRIMINATOR_TYPE = 85;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.EnumType <em>Enum Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.EnumType
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEnumType()
	 * @generated
	 */
	public static final int ENUM_TYPE = 86;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.FetchType <em>Fetch Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.FetchType
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getFetchType()
	 * @generated
	 */
	public static final int FETCH_TYPE = 87;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.GenerationType <em>Generation Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.GenerationType
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getGenerationType()
	 * @generated
	 */
	public static final int GENERATION_TYPE = 88;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.InheritanceType <em>Inheritance Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.InheritanceType
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getInheritanceType()
	 * @generated
	 */
	public static final int INHERITANCE_TYPE = 89;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.core.resource.orm.TemporalType <em>Temporal Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.core.resource.orm.TemporalType
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getTemporalType()
	 * @generated
	 */
	public static final int TEMPORAL_TYPE = 90;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractXmlAttributeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractXmlColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractXmlEmbeddedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractXmlMultiRelationshipMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractXmlNamedColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractXmlRelationshipMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractXmlSingleRelationshipMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractXmlTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractXmlTypeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAccessHolderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAttributeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAssociationOverrideEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAssociationOverrideContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAttributeOverrideEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlAttributeOverrideContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlBasicEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cascadeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlCollectionTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass columnMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass columnResultEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlConvertibleMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlDiscriminatorColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlElementCollectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEmbeddableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEmbeddedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEmbeddedIdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityListenerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityListenersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEntityMappingsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityResultEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventMethodEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlEventMethodContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fieldResultEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlGeneratedValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlGeneratorContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlIdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlIdClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inheritanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlJoinColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlJoinColumnsMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlJoinTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlJoinTableMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lobEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlManyToManyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlManyToOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapKeyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMapKeyClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMappedByMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlMappedSuperclassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedNativeQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNamedQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlNullAttributeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOneToManyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOneToOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOrderColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPersistenceUnitDefaultsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPersistenceUnitMetadataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractXmlReferenceTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlReferenceTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass postLoadEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass postPersistEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass postRemoveEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass postUpdateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass prePersistEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass preRemoveEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass preUpdateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlPrimaryKeyJoinColumnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlQueryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlQueryContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlQueryHintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlSecondaryTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlSequenceGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sqlResultSetMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTableGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTransientEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlTypeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlUniqueConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlVersionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmlOrderableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum accessTypeEEnum = null;

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
	private EEnum enumTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum fetchTypeEEnum = null;

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
	private EEnum inheritanceTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum temporalTypeEEnum = null;

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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private OrmPackage()
	{
		super(eNS_URI, OrmFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link OrmPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static OrmPackage init()
	{
		if (isInited) return (OrmPackage)EPackage.Registry.INSTANCE.getEPackage(OrmPackage.eNS_URI);

		// Obtain or create and register package
		OrmPackage theOrmPackage = (OrmPackage)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof OrmPackage ? EPackage.Registry.INSTANCE.get(eNS_URI) : new OrmPackage());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		CommonPackage theCommonPackage = (CommonPackage)(EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) instanceof CommonPackage ? EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) : CommonPackage.eINSTANCE);
		OrmV2_0Package theOrmV2_0Package = (OrmV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(OrmV2_0Package.eNS_URI) instanceof OrmV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(OrmV2_0Package.eNS_URI) : OrmV2_0Package.eINSTANCE);
		PersistencePackage thePersistencePackage = (PersistencePackage)(EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) instanceof PersistencePackage ? EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI) : PersistencePackage.eINSTANCE);
		PersistenceV2_0Package thePersistenceV2_0Package = (PersistenceV2_0Package)(EPackage.Registry.INSTANCE.getEPackage(PersistenceV2_0Package.eNS_URI) instanceof PersistenceV2_0Package ? EPackage.Registry.INSTANCE.getEPackage(PersistenceV2_0Package.eNS_URI) : PersistenceV2_0Package.eINSTANCE);

		// Create package meta-data objects
		theOrmPackage.createPackageContents();
		theCommonPackage.createPackageContents();
		theOrmV2_0Package.createPackageContents();
		thePersistencePackage.createPackageContents();
		thePersistenceV2_0Package.createPackageContents();

		// Initialize created meta-data
		theOrmPackage.initializePackageContents();
		theCommonPackage.initializePackageContents();
		theOrmV2_0Package.initializePackageContents();
		thePersistencePackage.initializePackageContents();
		thePersistenceV2_0Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theOrmPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(OrmPackage.eNS_URI, theOrmPackage);
		return theOrmPackage;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlAttributeMapping <em>Abstract Xml Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Attribute Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlAttributeMapping
	 * @generated
	 */
	public EClass getAbstractXmlAttributeMapping()
	{
		return abstractXmlAttributeMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlColumn <em>Abstract Xml Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlColumn
	 * @generated
	 */
	public EClass getAbstractXmlColumn()
	{
		return abstractXmlColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlColumn#getInsertable <em>Insertable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Insertable</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlColumn#getInsertable()
	 * @see #getAbstractXmlColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlColumn_Insertable()
	{
		return (EAttribute)abstractXmlColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlColumn#getNullable <em>Nullable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nullable</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlColumn#getNullable()
	 * @see #getAbstractXmlColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlColumn_Nullable()
	{
		return (EAttribute)abstractXmlColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlColumn#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Table</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlColumn#getTable()
	 * @see #getAbstractXmlColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlColumn_Table()
	{
		return (EAttribute)abstractXmlColumnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlColumn#getUnique <em>Unique</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlColumn#getUnique()
	 * @see #getAbstractXmlColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlColumn_Unique()
	{
		return (EAttribute)abstractXmlColumnEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlColumn#getUpdatable <em>Updatable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Updatable</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlColumn#getUpdatable()
	 * @see #getAbstractXmlColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlColumn_Updatable()
	{
		return (EAttribute)abstractXmlColumnEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlEmbedded <em>Abstract Xml Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Embedded</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlEmbedded
	 * @generated
	 */
	public EClass getAbstractXmlEmbedded()
	{
		return abstractXmlEmbeddedEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping <em>Abstract Xml Multi Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Multi Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping
	 * @generated
	 */
	public EClass getAbstractXmlMultiRelationshipMapping()
	{
		return abstractXmlMultiRelationshipMappingEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping#getMapKey <em>Map Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Key</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping#getMapKey()
	 * @see #getAbstractXmlMultiRelationshipMapping()
	 * @generated
	 */
	public EReference getAbstractXmlMultiRelationshipMapping_MapKey()
	{
		return (EReference)abstractXmlMultiRelationshipMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlNamedColumn <em>Abstract Xml Named Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Named Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlNamedColumn
	 * @generated
	 */
	public EClass getAbstractXmlNamedColumn()
	{
		return abstractXmlNamedColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlNamedColumn#getColumnDefinition <em>Column Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column Definition</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlNamedColumn#getColumnDefinition()
	 * @see #getAbstractXmlNamedColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlNamedColumn_ColumnDefinition()
	{
		return (EAttribute)abstractXmlNamedColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlNamedColumn#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlNamedColumn#getName()
	 * @see #getAbstractXmlNamedColumn()
	 * @generated
	 */
	public EAttribute getAbstractXmlNamedColumn_Name()
	{
		return (EAttribute)abstractXmlNamedColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping <em>Abstract Xml Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping
	 * @generated
	 */
	public EClass getAbstractXmlRelationshipMapping()
	{
		return abstractXmlRelationshipMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping#getTargetEntity <em>Target Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Entity</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping#getTargetEntity()
	 * @see #getAbstractXmlRelationshipMapping()
	 * @generated
	 */
	public EAttribute getAbstractXmlRelationshipMapping_TargetEntity()
	{
		return (EAttribute)abstractXmlRelationshipMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping#getFetch()
	 * @see #getAbstractXmlRelationshipMapping()
	 * @generated
	 */
	public EAttribute getAbstractXmlRelationshipMapping_Fetch()
	{
		return (EAttribute)abstractXmlRelationshipMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping#getCascade <em>Cascade</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cascade</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping#getCascade()
	 * @see #getAbstractXmlRelationshipMapping()
	 * @generated
	 */
	public EReference getAbstractXmlRelationshipMapping_Cascade()
	{
		return (EReference)abstractXmlRelationshipMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping <em>Abstract Xml Single Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Single Relationship Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping
	 * @generated
	 */
	public EClass getAbstractXmlSingleRelationshipMapping()
	{
		return abstractXmlSingleRelationshipMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping#getOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping#getOptional()
	 * @see #getAbstractXmlSingleRelationshipMapping()
	 * @generated
	 */
	public EAttribute getAbstractXmlSingleRelationshipMapping_Optional()
	{
		return (EAttribute)abstractXmlSingleRelationshipMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlTable <em>Abstract Xml Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Table</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlTable
	 * @generated
	 */
	public EClass getAbstractXmlTable()
	{
		return abstractXmlTableEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlTable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlTable#getName()
	 * @see #getAbstractXmlTable()
	 * @generated
	 */
	public EAttribute getAbstractXmlTable_Name()
	{
		return (EAttribute)abstractXmlTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlTable#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlTable#getCatalog()
	 * @see #getAbstractXmlTable()
	 * @generated
	 */
	public EAttribute getAbstractXmlTable_Catalog()
	{
		return (EAttribute)abstractXmlTableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlTable#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlTable#getSchema()
	 * @see #getAbstractXmlTable()
	 * @generated
	 */
	public EAttribute getAbstractXmlTable_Schema()
	{
		return (EAttribute)abstractXmlTableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlTable#getUniqueConstraints <em>Unique Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Unique Constraints</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlTable#getUniqueConstraints()
	 * @see #getAbstractXmlTable()
	 * @generated
	 */
	public EReference getAbstractXmlTable_UniqueConstraints()
	{
		return (EReference)abstractXmlTableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping <em>Abstract Xml Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Type Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping
	 * @generated
	 */
	public EClass getAbstractXmlTypeMapping()
	{
		return abstractXmlTypeMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlAccessHolder <em>Xml Access Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Access Holder</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAccessHolder
	 * @generated
	 */
	public EClass getXmlAccessHolder()
	{
		return xmlAccessHolderEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlAccessHolder#getAccess <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAccessHolder#getAccess()
	 * @see #getXmlAccessHolder()
	 * @generated
	 */
	public EAttribute getXmlAccessHolder_Access()
	{
		return (EAttribute)xmlAccessHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Attribute Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeMapping
	 * @generated
	 */
	public EClass getXmlAttributeMapping()
	{
		return xmlAttributeMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeMapping#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeMapping#getName()
	 * @see #getXmlAttributeMapping()
	 * @generated
	 */
	public EAttribute getXmlAttributeMapping_Name()
	{
		return (EAttribute)xmlAttributeMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlAssociationOverride <em>Xml Association Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Association Override</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAssociationOverride
	 * @generated
	 */
	public EClass getXmlAssociationOverride()
	{
		return xmlAssociationOverrideEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlAssociationOverride#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAssociationOverride#getName()
	 * @see #getXmlAssociationOverride()
	 * @generated
	 */
	public EAttribute getXmlAssociationOverride_Name()
	{
		return (EAttribute)xmlAssociationOverrideEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer <em>Xml Association Override Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Association Override Container</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer
	 * @generated
	 */
	public EClass getXmlAssociationOverrideContainer()
	{
		return xmlAssociationOverrideContainerEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer#getAssociationOverrides <em>Association Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Association Overrides</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer#getAssociationOverrides()
	 * @see #getXmlAssociationOverrideContainer()
	 * @generated
	 */
	public EReference getXmlAssociationOverrideContainer_AssociationOverrides()
	{
		return (EReference)xmlAssociationOverrideContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverride <em>Xml Attribute Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Attribute Override</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeOverride
	 * @generated
	 */
	public EClass getXmlAttributeOverride()
	{
		return xmlAttributeOverrideEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverride#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeOverride#getColumn()
	 * @see #getXmlAttributeOverride()
	 * @generated
	 */
	public EReference getXmlAttributeOverride_Column()
	{
		return (EReference)xmlAttributeOverrideEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverride#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeOverride#getName()
	 * @see #getXmlAttributeOverride()
	 * @generated
	 */
	public EAttribute getXmlAttributeOverride_Name()
	{
		return (EAttribute)xmlAttributeOverrideEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverrideContainer <em>Xml Attribute Override Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Attribute Override Container</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeOverrideContainer
	 * @generated
	 */
	public EClass getXmlAttributeOverrideContainer()
	{
		return xmlAttributeOverrideContainerEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverrideContainer#getAttributeOverrides <em>Attribute Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Overrides</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeOverrideContainer#getAttributeOverrides()
	 * @see #getXmlAttributeOverrideContainer()
	 * @generated
	 */
	public EReference getXmlAttributeOverrideContainer_AttributeOverrides()
	{
		return (EReference)xmlAttributeOverrideContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.Attributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attributes</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Attributes
	 * @generated
	 */
	public EClass getAttributes()
	{
		return attributesEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.Attributes#getIds <em>Ids</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ids</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Attributes#getIds()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_Ids()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.Attributes#getEmbeddedIds <em>Embedded Ids</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Embedded Ids</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Attributes#getEmbeddedIds()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_EmbeddedIds()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.Attributes#getBasics <em>Basics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Basics</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Attributes#getBasics()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_Basics()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.Attributes#getVersions <em>Versions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Versions</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Attributes#getVersions()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_Versions()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.Attributes#getManyToOnes <em>Many To Ones</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Many To Ones</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Attributes#getManyToOnes()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_ManyToOnes()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.Attributes#getOneToManys <em>One To Manys</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>One To Manys</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Attributes#getOneToManys()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_OneToManys()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.Attributes#getOneToOnes <em>One To Ones</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>One To Ones</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Attributes#getOneToOnes()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_OneToOnes()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.Attributes#getManyToManys <em>Many To Manys</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Many To Manys</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Attributes#getManyToManys()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_ManyToManys()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.Attributes#getEmbeddeds <em>Embeddeds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Embeddeds</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Attributes#getEmbeddeds()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_Embeddeds()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.Attributes#getTransients <em>Transients</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transients</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Attributes#getTransients()
	 * @see #getAttributes()
	 * @generated
	 */
	public EReference getAttributes_Transients()
	{
		return (EReference)attributesEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlBasic <em>Xml Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Basic</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlBasic
	 * @generated
	 */
	public EClass getXmlBasic()
	{
		return xmlBasicEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlBasic#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlBasic#getFetch()
	 * @see #getXmlBasic()
	 * @generated
	 */
	public EAttribute getXmlBasic_Fetch()
	{
		return (EAttribute)xmlBasicEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlBasic#getOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlBasic#getOptional()
	 * @see #getXmlBasic()
	 * @generated
	 */
	public EAttribute getXmlBasic_Optional()
	{
		return (EAttribute)xmlBasicEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.CascadeType <em>Cascade Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cascade Type</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.CascadeType
	 * @generated
	 */
	public EClass getCascadeType()
	{
		return cascadeTypeEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeAll <em>Cascade All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade All</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeAll()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EAttribute getCascadeType_CascadeAll()
	{
		return (EAttribute)cascadeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadePersist <em>Cascade Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade Persist</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.CascadeType#isCascadePersist()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EAttribute getCascadeType_CascadePersist()
	{
		return (EAttribute)cascadeTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeMerge <em>Cascade Merge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade Merge</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeMerge()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EAttribute getCascadeType_CascadeMerge()
	{
		return (EAttribute)cascadeTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeRemove <em>Cascade Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade Remove</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeRemove()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EAttribute getCascadeType_CascadeRemove()
	{
		return (EAttribute)cascadeTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeRefresh <em>Cascade Refresh</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade Refresh</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeRefresh()
	 * @see #getCascadeType()
	 * @generated
	 */
	public EAttribute getCascadeType_CascadeRefresh()
	{
		return (EAttribute)cascadeTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlCollectionTable <em>Xml Collection Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Collection Table</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlCollectionTable
	 * @generated
	 */
	public EClass getXmlCollectionTable()
	{
		return xmlCollectionTableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlColumn <em>Xml Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlColumn
	 * @generated
	 */
	public EClass getXmlColumn()
	{
		return xmlColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlColumn#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlColumn#getLength()
	 * @see #getXmlColumn()
	 * @generated
	 */
	public EAttribute getXmlColumn_Length()
	{
		return (EAttribute)xmlColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlColumn#getPrecision <em>Precision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Precision</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlColumn#getPrecision()
	 * @see #getXmlColumn()
	 * @generated
	 */
	public EAttribute getXmlColumn_Precision()
	{
		return (EAttribute)xmlColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlColumn#getScale <em>Scale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scale</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlColumn#getScale()
	 * @see #getXmlColumn()
	 * @generated
	 */
	public EAttribute getXmlColumn_Scale()
	{
		return (EAttribute)xmlColumnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.ColumnMapping <em>Column Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Column Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.ColumnMapping
	 * @generated
	 */
	public EClass getColumnMapping()
	{
		return columnMappingEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.ColumnMapping#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.ColumnMapping#getColumn()
	 * @see #getColumnMapping()
	 * @generated
	 */
	public EReference getColumnMapping_Column()
	{
		return (EReference)columnMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.ColumnResult <em>Column Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Column Result</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.ColumnResult
	 * @generated
	 */
	public EClass getColumnResult()
	{
		return columnResultEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.ColumnResult#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.ColumnResult#getName()
	 * @see #getColumnResult()
	 * @generated
	 */
	public EAttribute getColumnResult_Name()
	{
		return (EAttribute)columnResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping <em>Xml Convertible Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Convertible Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping
	 * @generated
	 */
	public EClass getXmlConvertibleMapping()
	{
		return xmlConvertibleMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping#isLob <em>Lob</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lob</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping#isLob()
	 * @see #getXmlConvertibleMapping()
	 * @generated
	 */
	public EAttribute getXmlConvertibleMapping_Lob()
	{
		return (EAttribute)xmlConvertibleMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping#getTemporal <em>Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temporal</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping#getTemporal()
	 * @see #getXmlConvertibleMapping()
	 * @generated
	 */
	public EAttribute getXmlConvertibleMapping_Temporal()
	{
		return (EAttribute)xmlConvertibleMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping#getEnumerated <em>Enumerated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enumerated</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping#getEnumerated()
	 * @see #getXmlConvertibleMapping()
	 * @generated
	 */
	public EAttribute getXmlConvertibleMapping_Enumerated()
	{
		return (EAttribute)xmlConvertibleMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn <em>Xml Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Discriminator Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn
	 * @generated
	 */
	public EClass getXmlDiscriminatorColumn()
	{
		return xmlDiscriminatorColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn#getDiscriminatorType <em>Discriminator Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator Type</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn#getDiscriminatorType()
	 * @see #getXmlDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getXmlDiscriminatorColumn_DiscriminatorType()
	{
		return (EAttribute)xmlDiscriminatorColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn#getLength()
	 * @see #getXmlDiscriminatorColumn()
	 * @generated
	 */
	public EAttribute getXmlDiscriminatorColumn_Length()
	{
		return (EAttribute)xmlDiscriminatorColumnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlElementCollection <em>Xml Element Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Element Collection</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlElementCollection
	 * @generated
	 */
	public EClass getXmlElementCollection()
	{
		return xmlElementCollectionEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlEmbeddable <em>Xml Embeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embeddable</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEmbeddable
	 * @generated
	 */
	public EClass getXmlEmbeddable()
	{
		return xmlEmbeddableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlEmbedded <em>Xml Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEmbedded
	 * @generated
	 */
	public EClass getXmlEmbedded()
	{
		return xmlEmbeddedEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Embedded Id</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEmbeddedId
	 * @generated
	 */
	public EClass getXmlEmbeddedId()
	{
		return xmlEmbeddedIdEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlEntity <em>Xml Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity
	 * @generated
	 */
	public EClass getXmlEntity()
	{
		return xmlEntityEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlEntity#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity#getName()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EAttribute getXmlEntity_Name()
	{
		return (EAttribute)xmlEntityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlEntity#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Table</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity#getTable()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_Table()
	{
		return (EReference)xmlEntityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlEntity#getSecondaryTables <em>Secondary Tables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Secondary Tables</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity#getSecondaryTables()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_SecondaryTables()
	{
		return (EReference)xmlEntityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlEntity#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Primary Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity#getPrimaryKeyJoinColumns()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_PrimaryKeyJoinColumns()
	{
		return (EReference)xmlEntityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlEntity#getIdClass <em>Id Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Id Class</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity#getIdClass()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_IdClass()
	{
		return (EReference)xmlEntityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlEntity#getInheritance <em>Inheritance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inheritance</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity#getInheritance()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_Inheritance()
	{
		return (EReference)xmlEntityEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlEntity#getDiscriminatorValue <em>Discriminator Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator Value</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity#getDiscriminatorValue()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EAttribute getXmlEntity_DiscriminatorValue()
	{
		return (EAttribute)xmlEntityEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlEntity#getDiscriminatorColumn <em>Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Discriminator Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity#getDiscriminatorColumn()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_DiscriminatorColumn()
	{
		return (EReference)xmlEntityEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlEntity#getSqlResultSetMappings <em>Sql Result Set Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sql Result Set Mappings</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity#getSqlResultSetMappings()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_SqlResultSetMappings()
	{
		return (EReference)xmlEntityEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlEntity#isExcludeDefaultListeners <em>Exclude Default Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Default Listeners</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity#isExcludeDefaultListeners()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EAttribute getXmlEntity_ExcludeDefaultListeners()
	{
		return (EAttribute)xmlEntityEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlEntity#isExcludeSuperclassListeners <em>Exclude Superclass Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Superclass Listeners</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity#isExcludeSuperclassListeners()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EAttribute getXmlEntity_ExcludeSuperclassListeners()
	{
		return (EAttribute)xmlEntityEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlEntity#getEntityListeners <em>Entity Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entity Listeners</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity#getEntityListeners()
	 * @see #getXmlEntity()
	 * @generated
	 */
	public EReference getXmlEntity_EntityListeners()
	{
		return (EReference)xmlEntityEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.EntityListener <em>Entity Listener</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Listener</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.EntityListener
	 * @generated
	 */
	public EClass getEntityListener()
	{
		return entityListenerEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.EntityListener#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.EntityListener#getClassName()
	 * @see #getEntityListener()
	 * @generated
	 */
	public EAttribute getEntityListener_ClassName()
	{
		return (EAttribute)entityListenerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.EntityListeners <em>Entity Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Listeners</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.EntityListeners
	 * @generated
	 */
	public EClass getEntityListeners()
	{
		return entityListenersEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.EntityListeners#getEntityListeners <em>Entity Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entity Listeners</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.EntityListeners#getEntityListeners()
	 * @see #getEntityListeners()
	 * @generated
	 */
	public EReference getEntityListeners_EntityListeners()
	{
		return (EReference)entityListenersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Entity Mappings</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings
	 * @generated
	 */
	public EClass getXmlEntityMappings()
	{
		return xmlEntityMappingsEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getDescription()
	 * @see #getXmlEntityMappings()
	 * @generated
	 */
	public EAttribute getXmlEntityMappings_Description()
	{
		return (EAttribute)xmlEntityMappingsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getPersistenceUnitMetadata <em>Persistence Unit Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Persistence Unit Metadata</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getPersistenceUnitMetadata()
	 * @see #getXmlEntityMappings()
	 * @generated
	 */
	public EReference getXmlEntityMappings_PersistenceUnitMetadata()
	{
		return (EReference)xmlEntityMappingsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getPackage()
	 * @see #getXmlEntityMappings()
	 * @generated
	 */
	public EAttribute getXmlEntityMappings_Package()
	{
		return (EAttribute)xmlEntityMappingsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getSchema()
	 * @see #getXmlEntityMappings()
	 * @generated
	 */
	public EAttribute getXmlEntityMappings_Schema()
	{
		return (EAttribute)xmlEntityMappingsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getCatalog()
	 * @see #getXmlEntityMappings()
	 * @generated
	 */
	public EAttribute getXmlEntityMappings_Catalog()
	{
		return (EAttribute)xmlEntityMappingsEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getSequenceGenerators <em>Sequence Generators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sequence Generators</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getSequenceGenerators()
	 * @see #getXmlEntityMappings()
	 * @generated
	 */
	public EReference getXmlEntityMappings_SequenceGenerators()
	{
		return (EReference)xmlEntityMappingsEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getTableGenerators <em>Table Generators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Table Generators</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getTableGenerators()
	 * @see #getXmlEntityMappings()
	 * @generated
	 */
	public EReference getXmlEntityMappings_TableGenerators()
	{
		return (EReference)xmlEntityMappingsEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getSqlResultSetMappings <em>Sql Result Set Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sql Result Set Mappings</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getSqlResultSetMappings()
	 * @see #getXmlEntityMappings()
	 * @generated
	 */
	public EReference getXmlEntityMappings_SqlResultSetMappings()
	{
		return (EReference)xmlEntityMappingsEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getMappedSuperclasses <em>Mapped Superclasses</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mapped Superclasses</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getMappedSuperclasses()
	 * @see #getXmlEntityMappings()
	 * @generated
	 */
	public EReference getXmlEntityMappings_MappedSuperclasses()
	{
		return (EReference)xmlEntityMappingsEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getEntities <em>Entities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entities</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getEntities()
	 * @see #getXmlEntityMappings()
	 * @generated
	 */
	public EReference getXmlEntityMappings_Entities()
	{
		return (EReference)xmlEntityMappingsEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getEmbeddables <em>Embeddables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Embeddables</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings#getEmbeddables()
	 * @see #getXmlEntityMappings()
	 * @generated
	 */
	public EReference getXmlEntityMappings_Embeddables()
	{
		return (EReference)xmlEntityMappingsEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.EntityResult <em>Entity Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Result</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.EntityResult
	 * @generated
	 */
	public EClass getEntityResult()
	{
		return entityResultEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.EntityResult#getDiscriminatorColumn <em>Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.EntityResult#getDiscriminatorColumn()
	 * @see #getEntityResult()
	 * @generated
	 */
	public EAttribute getEntityResult_DiscriminatorColumn()
	{
		return (EAttribute)entityResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.EntityResult#getEntityClass <em>Entity Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entity Class</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.EntityResult#getEntityClass()
	 * @see #getEntityResult()
	 * @generated
	 */
	public EAttribute getEntityResult_EntityClass()
	{
		return (EAttribute)entityResultEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.EntityResult#getFieldResults <em>Field Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Field Results</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.EntityResult#getFieldResults()
	 * @see #getEntityResult()
	 * @generated
	 */
	public EReference getEntityResult_FieldResults()
	{
		return (EReference)entityResultEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.EventMethod <em>Event Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Method</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.EventMethod
	 * @generated
	 */
	public EClass getEventMethod()
	{
		return eventMethodEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.EventMethod#getMethodName <em>Method Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Method Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.EventMethod#getMethodName()
	 * @see #getEventMethod()
	 * @generated
	 */
	public EAttribute getEventMethod_MethodName()
	{
		return (EAttribute)eventMethodEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer <em>Xml Event Method Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Event Method Container</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer
	 * @generated
	 */
	public EClass getXmlEventMethodContainer()
	{
		return xmlEventMethodContainerEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPrePersist <em>Pre Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Persist</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPrePersist()
	 * @see #getXmlEventMethodContainer()
	 * @generated
	 */
	public EReference getXmlEventMethodContainer_PrePersist()
	{
		return (EReference)xmlEventMethodContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPostPersist <em>Post Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Persist</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPostPersist()
	 * @see #getXmlEventMethodContainer()
	 * @generated
	 */
	public EReference getXmlEventMethodContainer_PostPersist()
	{
		return (EReference)xmlEventMethodContainerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPreRemove <em>Pre Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Remove</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPreRemove()
	 * @see #getXmlEventMethodContainer()
	 * @generated
	 */
	public EReference getXmlEventMethodContainer_PreRemove()
	{
		return (EReference)xmlEventMethodContainerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPostRemove <em>Post Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Remove</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPostRemove()
	 * @see #getXmlEventMethodContainer()
	 * @generated
	 */
	public EReference getXmlEventMethodContainer_PostRemove()
	{
		return (EReference)xmlEventMethodContainerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPreUpdate <em>Pre Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Update</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPreUpdate()
	 * @see #getXmlEventMethodContainer()
	 * @generated
	 */
	public EReference getXmlEventMethodContainer_PreUpdate()
	{
		return (EReference)xmlEventMethodContainerEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPostUpdate <em>Post Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Update</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPostUpdate()
	 * @see #getXmlEventMethodContainer()
	 * @generated
	 */
	public EReference getXmlEventMethodContainer_PostUpdate()
	{
		return (EReference)xmlEventMethodContainerEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPostLoad <em>Post Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Load</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer#getPostLoad()
	 * @see #getXmlEventMethodContainer()
	 * @generated
	 */
	public EReference getXmlEventMethodContainer_PostLoad()
	{
		return (EReference)xmlEventMethodContainerEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.FieldResult <em>Field Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Field Result</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.FieldResult
	 * @generated
	 */
	public EClass getFieldResult()
	{
		return fieldResultEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.FieldResult#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.FieldResult#getName()
	 * @see #getFieldResult()
	 * @generated
	 */
	public EAttribute getFieldResult_Name()
	{
		return (EAttribute)fieldResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.FieldResult#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.FieldResult#getColumn()
	 * @see #getFieldResult()
	 * @generated
	 */
	public EAttribute getFieldResult_Column()
	{
		return (EAttribute)fieldResultEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlGeneratedValue <em>Xml Generated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Generated Value</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlGeneratedValue
	 * @generated
	 */
	public EClass getXmlGeneratedValue()
	{
		return xmlGeneratedValueEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlGeneratedValue#getGenerator <em>Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generator</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlGeneratedValue#getGenerator()
	 * @see #getXmlGeneratedValue()
	 * @generated
	 */
	public EAttribute getXmlGeneratedValue_Generator()
	{
		return (EAttribute)xmlGeneratedValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlGeneratedValue#getStrategy <em>Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strategy</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlGeneratedValue#getStrategy()
	 * @see #getXmlGeneratedValue()
	 * @generated
	 */
	public EAttribute getXmlGeneratedValue_Strategy()
	{
		return (EAttribute)xmlGeneratedValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlGenerator <em>Xml Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Generator</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlGenerator
	 * @generated
	 */
	public EClass getXmlGenerator()
	{
		return xmlGeneratorEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlGenerator#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlGenerator#getName()
	 * @see #getXmlGenerator()
	 * @generated
	 */
	public EAttribute getXmlGenerator_Name()
	{
		return (EAttribute)xmlGeneratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlGenerator#getInitialValue <em>Initial Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial Value</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlGenerator#getInitialValue()
	 * @see #getXmlGenerator()
	 * @generated
	 */
	public EAttribute getXmlGenerator_InitialValue()
	{
		return (EAttribute)xmlGeneratorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlGenerator#getAllocationSize <em>Allocation Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allocation Size</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlGenerator#getAllocationSize()
	 * @see #getXmlGenerator()
	 * @generated
	 */
	public EAttribute getXmlGenerator_AllocationSize()
	{
		return (EAttribute)xmlGeneratorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer <em>Xml Generator Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Generator Container</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer
	 * @generated
	 */
	public EClass getXmlGeneratorContainer()
	{
		return xmlGeneratorContainerEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer#getSequenceGenerator <em>Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sequence Generator</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer#getSequenceGenerator()
	 * @see #getXmlGeneratorContainer()
	 * @generated
	 */
	public EReference getXmlGeneratorContainer_SequenceGenerator()
	{
		return (EReference)xmlGeneratorContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer#getTableGenerator <em>Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Table Generator</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer#getTableGenerator()
	 * @see #getXmlGeneratorContainer()
	 * @generated
	 */
	public EReference getXmlGeneratorContainer_TableGenerator()
	{
		return (EReference)xmlGeneratorContainerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlId <em>Xml Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlId
	 * @generated
	 */
	public EClass getXmlId()
	{
		return xmlIdEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlId#getGeneratedValue <em>Generated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Generated Value</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlId#getGeneratedValue()
	 * @see #getXmlId()
	 * @generated
	 */
	public EReference getXmlId_GeneratedValue()
	{
		return (EReference)xmlIdEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlIdClass <em>Xml Id Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Id Class</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlIdClass
	 * @generated
	 */
	public EClass getXmlIdClass()
	{
		return xmlIdClassEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlIdClass#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlIdClass#getClassName()
	 * @see #getXmlIdClass()
	 * @generated
	 */
	public EAttribute getXmlIdClass_ClassName()
	{
		return (EAttribute)xmlIdClassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.Inheritance <em>Inheritance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inheritance</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Inheritance
	 * @generated
	 */
	public EClass getInheritance()
	{
		return inheritanceEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.Inheritance#getStrategy <em>Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strategy</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Inheritance#getStrategy()
	 * @see #getInheritance()
	 * @generated
	 */
	public EAttribute getInheritance_Strategy()
	{
		return (EAttribute)inheritanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn <em>Xml Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Join Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinColumn
	 * @generated
	 */
	public EClass getXmlJoinColumn()
	{
		return xmlJoinColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn#getReferencedColumnName <em>Referenced Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Referenced Column Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinColumn#getReferencedColumnName()
	 * @see #getXmlJoinColumn()
	 * @generated
	 */
	public EAttribute getXmlJoinColumn_ReferencedColumnName()
	{
		return (EAttribute)xmlJoinColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping <em>Xml Join Columns Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Join Columns Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping
	 * @generated
	 */
	public EClass getXmlJoinColumnsMapping()
	{
		return xmlJoinColumnsMappingEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping#getJoinColumns <em>Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Columns</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping#getJoinColumns()
	 * @see #getXmlJoinColumnsMapping()
	 * @generated
	 */
	public EReference getXmlJoinColumnsMapping_JoinColumns()
	{
		return (EReference)xmlJoinColumnsMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlJoinTable <em>Xml Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Join Table</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinTable
	 * @generated
	 */
	public EClass getXmlJoinTable()
	{
		return xmlJoinTableEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlJoinTable#getInverseJoinColumns <em>Inverse Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inverse Join Columns</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinTable#getInverseJoinColumns()
	 * @see #getXmlJoinTable()
	 * @generated
	 */
	public EReference getXmlJoinTable_InverseJoinColumns()
	{
		return (EReference)xmlJoinTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping <em>Xml Join Table Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Join Table Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping
	 * @generated
	 */
	public EClass getXmlJoinTableMapping()
	{
		return xmlJoinTableMappingEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping#getJoinTable <em>Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Table</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping#getJoinTable()
	 * @see #getXmlJoinTableMapping()
	 * @generated
	 */
	public EReference getXmlJoinTableMapping_JoinTable()
	{
		return (EReference)xmlJoinTableMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.Lob <em>Lob</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Lob</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.Lob
	 * @generated
	 */
	public EClass getLob()
	{
		return lobEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To Many</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlManyToMany
	 * @generated
	 */
	public EClass getXmlManyToMany()
	{
		return xmlManyToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Many To One</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlManyToOne
	 * @generated
	 */
	public EClass getXmlManyToOne()
	{
		return xmlManyToOneEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.MapKey <em>Map Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Key</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.MapKey
	 * @generated
	 */
	public EClass getMapKey()
	{
		return mapKeyEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.MapKey#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.MapKey#getName()
	 * @see #getMapKey()
	 * @generated
	 */
	public EAttribute getMapKey_Name()
	{
		return (EAttribute)mapKeyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlMapKeyClass <em>Xml Map Key Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Map Key Class</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlMapKeyClass
	 * @generated
	 */
	public EClass getXmlMapKeyClass()
	{
		return xmlMapKeyClassEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlMappedByMapping <em>Xml Mapped By Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mapped By Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlMappedByMapping
	 * @generated
	 */
	public EClass getXmlMappedByMapping()
	{
		return xmlMappedByMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlMappedByMapping#getMappedBy <em>Mapped By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mapped By</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlMappedByMapping#getMappedBy()
	 * @see #getXmlMappedByMapping()
	 * @generated
	 */
	public EAttribute getXmlMappedByMapping_MappedBy()
	{
		return (EAttribute)xmlMappedByMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Mapped Superclass</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass
	 * @generated
	 */
	public EClass getXmlMappedSuperclass()
	{
		return xmlMappedSuperclassEClass;
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#getIdClass <em>Id Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Id Class</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#getIdClass()
	 * @see #getXmlMappedSuperclass()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_IdClass()
	{
		return (EReference)xmlMappedSuperclassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#isExcludeDefaultListeners <em>Exclude Default Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Default Listeners</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#isExcludeDefaultListeners()
	 * @see #getXmlMappedSuperclass()
	 * @generated
	 */
	public EAttribute getXmlMappedSuperclass_ExcludeDefaultListeners()
	{
		return (EAttribute)xmlMappedSuperclassEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#isExcludeSuperclassListeners <em>Exclude Superclass Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude Superclass Listeners</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#isExcludeSuperclassListeners()
	 * @see #getXmlMappedSuperclass()
	 * @generated
	 */
	public EAttribute getXmlMappedSuperclass_ExcludeSuperclassListeners()
	{
		return (EAttribute)xmlMappedSuperclassEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#getEntityListeners <em>Entity Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entity Listeners</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#getEntityListeners()
	 * @see #getXmlMappedSuperclass()
	 * @generated
	 */
	public EReference getXmlMappedSuperclass_EntityListeners()
	{
		return (EReference)xmlMappedSuperclassEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery <em>Xml Named Native Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Native Query</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery
	 * @generated
	 */
	public EClass getXmlNamedNativeQuery()
	{
		return xmlNamedNativeQueryEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery#getResultClass <em>Result Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Class</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery#getResultClass()
	 * @see #getXmlNamedNativeQuery()
	 * @generated
	 */
	public EAttribute getXmlNamedNativeQuery_ResultClass()
	{
		return (EAttribute)xmlNamedNativeQueryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery#getResultSetMapping <em>Result Set Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Set Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery#getResultSetMapping()
	 * @see #getXmlNamedNativeQuery()
	 * @generated
	 */
	public EAttribute getXmlNamedNativeQuery_ResultSetMapping()
	{
		return (EAttribute)xmlNamedNativeQueryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlNamedQuery <em>Xml Named Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Named Query</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlNamedQuery
	 * @generated
	 */
	public EClass getXmlNamedQuery()
	{
		return xmlNamedQueryEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping <em>Xml Null Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Null Attribute Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping
	 * @generated
	 */
	public EClass getXmlNullAttributeMapping()
	{
		return xmlNullAttributeMappingEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To Many</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlOneToMany
	 * @generated
	 */
	public EClass getXmlOneToMany()
	{
		return xmlOneToManyEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml One To One</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlOneToOne
	 * @generated
	 */
	public EClass getXmlOneToOne()
	{
		return xmlOneToOneEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlOneToOne#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Primary Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlOneToOne#getPrimaryKeyJoinColumns()
	 * @see #getXmlOneToOne()
	 * @generated
	 */
	public EReference getXmlOneToOne_PrimaryKeyJoinColumns()
	{
		return (EReference)xmlOneToOneEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlOrderColumn <em>Xml Order Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Order Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlOrderColumn
	 * @generated
	 */
	public EClass getXmlOrderColumn()
	{
		return xmlOrderColumnEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults <em>Xml Persistence Unit Defaults</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence Unit Defaults</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults
	 * @generated
	 */
	public EClass getXmlPersistenceUnitDefaults()
	{
		return xmlPersistenceUnitDefaultsEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getSchema()
	 * @see #getXmlPersistenceUnitDefaults()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnitDefaults_Schema()
	{
		return (EAttribute)xmlPersistenceUnitDefaultsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getCatalog()
	 * @see #getXmlPersistenceUnitDefaults()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnitDefaults_Catalog()
	{
		return (EAttribute)xmlPersistenceUnitDefaultsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#isCascadePersist <em>Cascade Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cascade Persist</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#isCascadePersist()
	 * @see #getXmlPersistenceUnitDefaults()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnitDefaults_CascadePersist()
	{
		return (EAttribute)xmlPersistenceUnitDefaultsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getEntityListeners <em>Entity Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entity Listeners</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getEntityListeners()
	 * @see #getXmlPersistenceUnitDefaults()
	 * @generated
	 */
	public EReference getXmlPersistenceUnitDefaults_EntityListeners()
	{
		return (EReference)xmlPersistenceUnitDefaultsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata <em>Xml Persistence Unit Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Persistence Unit Metadata</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata
	 * @generated
	 */
	public EClass getXmlPersistenceUnitMetadata()
	{
		return xmlPersistenceUnitMetadataEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata#isXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Mapping Metadata Complete</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata#isXmlMappingMetadataComplete()
	 * @see #getXmlPersistenceUnitMetadata()
	 * @generated
	 */
	public EAttribute getXmlPersistenceUnitMetadata_XmlMappingMetadataComplete()
	{
		return (EAttribute)xmlPersistenceUnitMetadataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata#getPersistenceUnitDefaults <em>Persistence Unit Defaults</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Persistence Unit Defaults</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata#getPersistenceUnitDefaults()
	 * @see #getXmlPersistenceUnitMetadata()
	 * @generated
	 */
	public EReference getXmlPersistenceUnitMetadata_PersistenceUnitDefaults()
	{
		return (EReference)xmlPersistenceUnitMetadataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlReferenceTable <em>Abstract Xml Reference Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Xml Reference Table</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlReferenceTable
	 * @generated
	 */
	public EClass getAbstractXmlReferenceTable()
	{
		return abstractXmlReferenceTableEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlReferenceTable <em>Xml Reference Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Reference Table</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlReferenceTable
	 * @generated
	 */
	public EClass getXmlReferenceTable()
	{
		return xmlReferenceTableEClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlReferenceTable#getJoinColumns <em>Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Join Columns</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlReferenceTable#getJoinColumns()
	 * @see #getXmlReferenceTable()
	 * @generated
	 */
	public EReference getXmlReferenceTable_JoinColumns()
	{
		return (EReference)xmlReferenceTableEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.PostLoad <em>Post Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Load</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.PostLoad
	 * @generated
	 */
	public EClass getPostLoad()
	{
		return postLoadEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.PostPersist <em>Post Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Persist</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.PostPersist
	 * @generated
	 */
	public EClass getPostPersist()
	{
		return postPersistEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.PostRemove <em>Post Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Remove</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.PostRemove
	 * @generated
	 */
	public EClass getPostRemove()
	{
		return postRemoveEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.PostUpdate <em>Post Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Update</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.PostUpdate
	 * @generated
	 */
	public EClass getPostUpdate()
	{
		return postUpdateEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.PrePersist <em>Pre Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pre Persist</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.PrePersist
	 * @generated
	 */
	public EClass getPrePersist()
	{
		return prePersistEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.PreRemove <em>Pre Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pre Remove</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.PreRemove
	 * @generated
	 */
	public EClass getPreRemove()
	{
		return preRemoveEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.PreUpdate <em>Pre Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pre Update</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.PreUpdate
	 * @generated
	 */
	public EClass getPreUpdate()
	{
		return preUpdateEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn <em>Xml Primary Key Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Primary Key Join Column</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn
	 * @generated
	 */
	public EClass getXmlPrimaryKeyJoinColumn()
	{
		return xmlPrimaryKeyJoinColumnEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn#getReferencedColumnName <em>Referenced Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Referenced Column Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn#getReferencedColumnName()
	 * @see #getXmlPrimaryKeyJoinColumn()
	 * @generated
	 */
	public EAttribute getXmlPrimaryKeyJoinColumn_ReferencedColumnName()
	{
		return (EAttribute)xmlPrimaryKeyJoinColumnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlQuery <em>Xml Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Query</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlQuery
	 * @generated
	 */
	public EClass getXmlQuery()
	{
		return xmlQueryEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlQuery#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlQuery#getName()
	 * @see #getXmlQuery()
	 * @generated
	 */
	public EAttribute getXmlQuery_Name()
	{
		return (EAttribute)xmlQueryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlQuery#getQuery <em>Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Query</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlQuery#getQuery()
	 * @see #getXmlQuery()
	 * @generated
	 */
	public EAttribute getXmlQuery_Query()
	{
		return (EAttribute)xmlQueryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlQuery#getHints <em>Hints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Hints</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlQuery#getHints()
	 * @see #getXmlQuery()
	 * @generated
	 */
	public EReference getXmlQuery_Hints()
	{
		return (EReference)xmlQueryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlQueryContainer <em>Xml Query Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Query Container</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlQueryContainer
	 * @generated
	 */
	public EClass getXmlQueryContainer()
	{
		return xmlQueryContainerEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlQueryContainer#getNamedQueries <em>Named Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Queries</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlQueryContainer#getNamedQueries()
	 * @see #getXmlQueryContainer()
	 * @generated
	 */
	public EReference getXmlQueryContainer_NamedQueries()
	{
		return (EReference)xmlQueryContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlQueryContainer#getNamedNativeQueries <em>Named Native Queries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Native Queries</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlQueryContainer#getNamedNativeQueries()
	 * @see #getXmlQueryContainer()
	 * @generated
	 */
	public EReference getXmlQueryContainer_NamedNativeQueries()
	{
		return (EReference)xmlQueryContainerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlQueryHint <em>Xml Query Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Query Hint</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlQueryHint
	 * @generated
	 */
	public EClass getXmlQueryHint()
	{
		return xmlQueryHintEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlQueryHint#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlQueryHint#getName()
	 * @see #getXmlQueryHint()
	 * @generated
	 */
	public EAttribute getXmlQueryHint_Name()
	{
		return (EAttribute)xmlQueryHintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlQueryHint#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlQueryHint#getValue()
	 * @see #getXmlQueryHint()
	 * @generated
	 */
	public EAttribute getXmlQueryHint_Value()
	{
		return (EAttribute)xmlQueryHintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlSecondaryTable <em>Xml Secondary Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Secondary Table</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlSecondaryTable
	 * @generated
	 */
	public EClass getXmlSecondaryTable()
	{
		return xmlSecondaryTableEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlSecondaryTable#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Primary Key Join Columns</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlSecondaryTable#getPrimaryKeyJoinColumns()
	 * @see #getXmlSecondaryTable()
	 * @generated
	 */
	public EReference getXmlSecondaryTable_PrimaryKeyJoinColumns()
	{
		return (EReference)xmlSecondaryTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator <em>Xml Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Sequence Generator</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator
	 * @generated
	 */
	public EClass getXmlSequenceGenerator()
	{
		return xmlSequenceGeneratorEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator#getSequenceName <em>Sequence Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sequence Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator#getSequenceName()
	 * @see #getXmlSequenceGenerator()
	 * @generated
	 */
	public EAttribute getXmlSequenceGenerator_SequenceName()
	{
		return (EAttribute)xmlSequenceGeneratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.SqlResultSetMapping <em>Sql Result Set Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sql Result Set Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.SqlResultSetMapping
	 * @generated
	 */
	public EClass getSqlResultSetMapping()
	{
		return sqlResultSetMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.SqlResultSetMapping#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.SqlResultSetMapping#getName()
	 * @see #getSqlResultSetMapping()
	 * @generated
	 */
	public EAttribute getSqlResultSetMapping_Name()
	{
		return (EAttribute)sqlResultSetMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.SqlResultSetMapping#getEntityResults <em>Entity Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entity Results</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.SqlResultSetMapping#getEntityResults()
	 * @see #getSqlResultSetMapping()
	 * @generated
	 */
	public EReference getSqlResultSetMapping_EntityResults()
	{
		return (EReference)sqlResultSetMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.SqlResultSetMapping#getColumnResults <em>Column Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Column Results</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.SqlResultSetMapping#getColumnResults()
	 * @see #getSqlResultSetMapping()
	 * @generated
	 */
	public EReference getSqlResultSetMapping_ColumnResults()
	{
		return (EReference)sqlResultSetMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlTable <em>Xml Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Table</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTable
	 * @generated
	 */
	public EClass getXmlTable()
	{
		return xmlTableEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlTableGenerator <em>Xml Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Table Generator</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTableGenerator
	 * @generated
	 */
	public EClass getXmlTableGenerator()
	{
		return xmlTableGeneratorEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Table</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getTable()
	 * @see #getXmlTableGenerator()
	 * @generated
	 */
	public EAttribute getXmlTableGenerator_Table()
	{
		return (EAttribute)xmlTableGeneratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Catalog</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getCatalog()
	 * @see #getXmlTableGenerator()
	 * @generated
	 */
	public EAttribute getXmlTableGenerator_Catalog()
	{
		return (EAttribute)xmlTableGeneratorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getSchema()
	 * @see #getXmlTableGenerator()
	 * @generated
	 */
	public EAttribute getXmlTableGenerator_Schema()
	{
		return (EAttribute)xmlTableGeneratorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getPkColumnName <em>Pk Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pk Column Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getPkColumnName()
	 * @see #getXmlTableGenerator()
	 * @generated
	 */
	public EAttribute getXmlTableGenerator_PkColumnName()
	{
		return (EAttribute)xmlTableGeneratorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getValueColumnName <em>Value Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Column Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getValueColumnName()
	 * @see #getXmlTableGenerator()
	 * @generated
	 */
	public EAttribute getXmlTableGenerator_ValueColumnName()
	{
		return (EAttribute)xmlTableGeneratorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getPkColumnValue <em>Pk Column Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pk Column Value</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getPkColumnValue()
	 * @see #getXmlTableGenerator()
	 * @generated
	 */
	public EAttribute getXmlTableGenerator_PkColumnValue()
	{
		return (EAttribute)xmlTableGeneratorEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getUniqueConstraints <em>Unique Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Unique Constraints</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTableGenerator#getUniqueConstraints()
	 * @see #getXmlTableGenerator()
	 * @generated
	 */
	public EReference getXmlTableGenerator_UniqueConstraints()
	{
		return (EReference)xmlTableGeneratorEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlTransient <em>Xml Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Transient</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTransient
	 * @generated
	 */
	public EClass getXmlTransient()
	{
		return xmlTransientEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping <em>Xml Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Type Mapping</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTypeMapping
	 * @generated
	 */
	public EClass getXmlTypeMapping()
	{
		return xmlTypeMappingEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getClassName()
	 * @see #getXmlTypeMapping()
	 * @generated
	 */
	public EAttribute getXmlTypeMapping_ClassName()
	{
		return (EAttribute)xmlTypeMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getMetadataComplete <em>Metadata Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metadata Complete</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getMetadataComplete()
	 * @see #getXmlTypeMapping()
	 * @generated
	 */
	public EAttribute getXmlTypeMapping_MetadataComplete()
	{
		return (EAttribute)xmlTypeMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getDescription()
	 * @see #getXmlTypeMapping()
	 * @generated
	 */
	public EAttribute getXmlTypeMapping_Description()
	{
		return (EAttribute)xmlTypeMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attributes</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getAttributes()
	 * @see #getXmlTypeMapping()
	 * @generated
	 */
	public EReference getXmlTypeMapping_Attributes()
	{
		return (EReference)xmlTypeMappingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint <em>Xml Unique Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Unique Constraint</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint
	 * @generated
	 */
	public EClass getXmlUniqueConstraint()
	{
		return xmlUniqueConstraintEClass;
	}

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint#getColumnNames <em>Column Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Column Names</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint#getColumnNames()
	 * @see #getXmlUniqueConstraint()
	 * @generated
	 */
	public EAttribute getXmlUniqueConstraint_ColumnNames()
	{
		return (EAttribute)xmlUniqueConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Version</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlVersion
	 * @generated
	 */
	public EClass getXmlVersion()
	{
		return xmlVersionEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.core.resource.orm.XmlOrderable <em>Xml Orderable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xml Orderable</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlOrderable
	 * @generated
	 */
	public EClass getXmlOrderable()
	{
		return xmlOrderableEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.core.resource.orm.XmlOrderable#getOrderBy <em>Order By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Order By</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.XmlOrderable#getOrderBy()
	 * @see #getXmlOrderable()
	 * @generated
	 */
	public EAttribute getXmlOrderable_OrderBy()
	{
		return (EAttribute)xmlOrderableEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.resource.orm.AccessType <em>Access Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Access Type</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.AccessType
	 * @generated
	 */
	public EEnum getAccessType()
	{
		return accessTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.resource.orm.DiscriminatorType <em>Discriminator Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Discriminator Type</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.DiscriminatorType
	 * @generated
	 */
	public EEnum getDiscriminatorType()
	{
		return discriminatorTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.resource.orm.EnumType <em>Enum Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Enum Type</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.EnumType
	 * @generated
	 */
	public EEnum getEnumType()
	{
		return enumTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.resource.orm.FetchType <em>Fetch Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Fetch Type</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.FetchType
	 * @generated
	 */
	public EEnum getFetchType()
	{
		return fetchTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.resource.orm.GenerationType <em>Generation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Generation Type</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.GenerationType
	 * @generated
	 */
	public EEnum getGenerationType()
	{
		return generationTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.resource.orm.InheritanceType <em>Inheritance Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Inheritance Type</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.InheritanceType
	 * @generated
	 */
	public EEnum getInheritanceType()
	{
		return inheritanceTypeEEnum;
	}

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.core.resource.orm.TemporalType <em>Temporal Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Temporal Type</em>'.
	 * @see org.eclipse.jpt.core.resource.orm.TemporalType
	 * @generated
	 */
	public EEnum getTemporalType()
	{
		return temporalTypeEEnum;
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public OrmFactory getOrmFactory()
	{
		return (OrmFactory)getEFactoryInstance();
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
		abstractXmlAttributeMappingEClass = createEClass(ABSTRACT_XML_ATTRIBUTE_MAPPING);

		abstractXmlColumnEClass = createEClass(ABSTRACT_XML_COLUMN);
		createEAttribute(abstractXmlColumnEClass, ABSTRACT_XML_COLUMN__INSERTABLE);
		createEAttribute(abstractXmlColumnEClass, ABSTRACT_XML_COLUMN__NULLABLE);
		createEAttribute(abstractXmlColumnEClass, ABSTRACT_XML_COLUMN__TABLE);
		createEAttribute(abstractXmlColumnEClass, ABSTRACT_XML_COLUMN__UNIQUE);
		createEAttribute(abstractXmlColumnEClass, ABSTRACT_XML_COLUMN__UPDATABLE);

		abstractXmlEmbeddedEClass = createEClass(ABSTRACT_XML_EMBEDDED);

		abstractXmlMultiRelationshipMappingEClass = createEClass(ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING);
		createEReference(abstractXmlMultiRelationshipMappingEClass, ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY);

		abstractXmlNamedColumnEClass = createEClass(ABSTRACT_XML_NAMED_COLUMN);
		createEAttribute(abstractXmlNamedColumnEClass, ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION);
		createEAttribute(abstractXmlNamedColumnEClass, ABSTRACT_XML_NAMED_COLUMN__NAME);

		abstractXmlRelationshipMappingEClass = createEClass(ABSTRACT_XML_RELATIONSHIP_MAPPING);
		createEAttribute(abstractXmlRelationshipMappingEClass, ABSTRACT_XML_RELATIONSHIP_MAPPING__TARGET_ENTITY);
		createEAttribute(abstractXmlRelationshipMappingEClass, ABSTRACT_XML_RELATIONSHIP_MAPPING__FETCH);
		createEReference(abstractXmlRelationshipMappingEClass, ABSTRACT_XML_RELATIONSHIP_MAPPING__CASCADE);

		abstractXmlSingleRelationshipMappingEClass = createEClass(ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING);
		createEAttribute(abstractXmlSingleRelationshipMappingEClass, ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL);

		abstractXmlReferenceTableEClass = createEClass(ABSTRACT_XML_REFERENCE_TABLE);

		abstractXmlTableEClass = createEClass(ABSTRACT_XML_TABLE);
		createEAttribute(abstractXmlTableEClass, ABSTRACT_XML_TABLE__NAME);
		createEAttribute(abstractXmlTableEClass, ABSTRACT_XML_TABLE__CATALOG);
		createEAttribute(abstractXmlTableEClass, ABSTRACT_XML_TABLE__SCHEMA);
		createEReference(abstractXmlTableEClass, ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS);

		abstractXmlTypeMappingEClass = createEClass(ABSTRACT_XML_TYPE_MAPPING);

		xmlAccessHolderEClass = createEClass(XML_ACCESS_HOLDER);
		createEAttribute(xmlAccessHolderEClass, XML_ACCESS_HOLDER__ACCESS);

		xmlAttributeMappingEClass = createEClass(XML_ATTRIBUTE_MAPPING);
		createEAttribute(xmlAttributeMappingEClass, XML_ATTRIBUTE_MAPPING__NAME);

		xmlAssociationOverrideEClass = createEClass(XML_ASSOCIATION_OVERRIDE);
		createEAttribute(xmlAssociationOverrideEClass, XML_ASSOCIATION_OVERRIDE__NAME);

		xmlAssociationOverrideContainerEClass = createEClass(XML_ASSOCIATION_OVERRIDE_CONTAINER);
		createEReference(xmlAssociationOverrideContainerEClass, XML_ASSOCIATION_OVERRIDE_CONTAINER__ASSOCIATION_OVERRIDES);

		xmlAttributeOverrideEClass = createEClass(XML_ATTRIBUTE_OVERRIDE);
		createEReference(xmlAttributeOverrideEClass, XML_ATTRIBUTE_OVERRIDE__COLUMN);
		createEAttribute(xmlAttributeOverrideEClass, XML_ATTRIBUTE_OVERRIDE__NAME);

		xmlAttributeOverrideContainerEClass = createEClass(XML_ATTRIBUTE_OVERRIDE_CONTAINER);
		createEReference(xmlAttributeOverrideContainerEClass, XML_ATTRIBUTE_OVERRIDE_CONTAINER__ATTRIBUTE_OVERRIDES);

		attributesEClass = createEClass(ATTRIBUTES);
		createEReference(attributesEClass, ATTRIBUTES__IDS);
		createEReference(attributesEClass, ATTRIBUTES__EMBEDDED_IDS);
		createEReference(attributesEClass, ATTRIBUTES__BASICS);
		createEReference(attributesEClass, ATTRIBUTES__VERSIONS);
		createEReference(attributesEClass, ATTRIBUTES__MANY_TO_ONES);
		createEReference(attributesEClass, ATTRIBUTES__ONE_TO_MANYS);
		createEReference(attributesEClass, ATTRIBUTES__ONE_TO_ONES);
		createEReference(attributesEClass, ATTRIBUTES__MANY_TO_MANYS);
		createEReference(attributesEClass, ATTRIBUTES__EMBEDDEDS);
		createEReference(attributesEClass, ATTRIBUTES__TRANSIENTS);

		xmlBasicEClass = createEClass(XML_BASIC);
		createEAttribute(xmlBasicEClass, XML_BASIC__FETCH);
		createEAttribute(xmlBasicEClass, XML_BASIC__OPTIONAL);

		cascadeTypeEClass = createEClass(CASCADE_TYPE);
		createEAttribute(cascadeTypeEClass, CASCADE_TYPE__CASCADE_ALL);
		createEAttribute(cascadeTypeEClass, CASCADE_TYPE__CASCADE_PERSIST);
		createEAttribute(cascadeTypeEClass, CASCADE_TYPE__CASCADE_MERGE);
		createEAttribute(cascadeTypeEClass, CASCADE_TYPE__CASCADE_REMOVE);
		createEAttribute(cascadeTypeEClass, CASCADE_TYPE__CASCADE_REFRESH);

		xmlCollectionTableEClass = createEClass(XML_COLLECTION_TABLE);

		xmlColumnEClass = createEClass(XML_COLUMN);
		createEAttribute(xmlColumnEClass, XML_COLUMN__LENGTH);
		createEAttribute(xmlColumnEClass, XML_COLUMN__PRECISION);
		createEAttribute(xmlColumnEClass, XML_COLUMN__SCALE);

		columnMappingEClass = createEClass(COLUMN_MAPPING);
		createEReference(columnMappingEClass, COLUMN_MAPPING__COLUMN);

		columnResultEClass = createEClass(COLUMN_RESULT);
		createEAttribute(columnResultEClass, COLUMN_RESULT__NAME);

		xmlConvertibleMappingEClass = createEClass(XML_CONVERTIBLE_MAPPING);
		createEAttribute(xmlConvertibleMappingEClass, XML_CONVERTIBLE_MAPPING__LOB);
		createEAttribute(xmlConvertibleMappingEClass, XML_CONVERTIBLE_MAPPING__TEMPORAL);
		createEAttribute(xmlConvertibleMappingEClass, XML_CONVERTIBLE_MAPPING__ENUMERATED);

		xmlDiscriminatorColumnEClass = createEClass(XML_DISCRIMINATOR_COLUMN);
		createEAttribute(xmlDiscriminatorColumnEClass, XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE);
		createEAttribute(xmlDiscriminatorColumnEClass, XML_DISCRIMINATOR_COLUMN__LENGTH);

		xmlElementCollectionEClass = createEClass(XML_ELEMENT_COLLECTION);

		xmlEmbeddableEClass = createEClass(XML_EMBEDDABLE);

		xmlEmbeddedEClass = createEClass(XML_EMBEDDED);

		xmlEmbeddedIdEClass = createEClass(XML_EMBEDDED_ID);

		xmlEntityEClass = createEClass(XML_ENTITY);
		createEAttribute(xmlEntityEClass, XML_ENTITY__NAME);
		createEReference(xmlEntityEClass, XML_ENTITY__TABLE);
		createEReference(xmlEntityEClass, XML_ENTITY__SECONDARY_TABLES);
		createEReference(xmlEntityEClass, XML_ENTITY__PRIMARY_KEY_JOIN_COLUMNS);
		createEReference(xmlEntityEClass, XML_ENTITY__ID_CLASS);
		createEReference(xmlEntityEClass, XML_ENTITY__INHERITANCE);
		createEAttribute(xmlEntityEClass, XML_ENTITY__DISCRIMINATOR_VALUE);
		createEReference(xmlEntityEClass, XML_ENTITY__DISCRIMINATOR_COLUMN);
		createEReference(xmlEntityEClass, XML_ENTITY__SQL_RESULT_SET_MAPPINGS);
		createEAttribute(xmlEntityEClass, XML_ENTITY__EXCLUDE_DEFAULT_LISTENERS);
		createEAttribute(xmlEntityEClass, XML_ENTITY__EXCLUDE_SUPERCLASS_LISTENERS);
		createEReference(xmlEntityEClass, XML_ENTITY__ENTITY_LISTENERS);

		entityListenerEClass = createEClass(ENTITY_LISTENER);
		createEAttribute(entityListenerEClass, ENTITY_LISTENER__CLASS_NAME);

		entityListenersEClass = createEClass(ENTITY_LISTENERS);
		createEReference(entityListenersEClass, ENTITY_LISTENERS__ENTITY_LISTENERS);

		xmlEntityMappingsEClass = createEClass(XML_ENTITY_MAPPINGS);
		createEAttribute(xmlEntityMappingsEClass, XML_ENTITY_MAPPINGS__DESCRIPTION);
		createEReference(xmlEntityMappingsEClass, XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA);
		createEAttribute(xmlEntityMappingsEClass, XML_ENTITY_MAPPINGS__PACKAGE);
		createEAttribute(xmlEntityMappingsEClass, XML_ENTITY_MAPPINGS__SCHEMA);
		createEAttribute(xmlEntityMappingsEClass, XML_ENTITY_MAPPINGS__CATALOG);
		createEReference(xmlEntityMappingsEClass, XML_ENTITY_MAPPINGS__SEQUENCE_GENERATORS);
		createEReference(xmlEntityMappingsEClass, XML_ENTITY_MAPPINGS__TABLE_GENERATORS);
		createEReference(xmlEntityMappingsEClass, XML_ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS);
		createEReference(xmlEntityMappingsEClass, XML_ENTITY_MAPPINGS__MAPPED_SUPERCLASSES);
		createEReference(xmlEntityMappingsEClass, XML_ENTITY_MAPPINGS__ENTITIES);
		createEReference(xmlEntityMappingsEClass, XML_ENTITY_MAPPINGS__EMBEDDABLES);

		entityResultEClass = createEClass(ENTITY_RESULT);
		createEAttribute(entityResultEClass, ENTITY_RESULT__DISCRIMINATOR_COLUMN);
		createEAttribute(entityResultEClass, ENTITY_RESULT__ENTITY_CLASS);
		createEReference(entityResultEClass, ENTITY_RESULT__FIELD_RESULTS);

		eventMethodEClass = createEClass(EVENT_METHOD);
		createEAttribute(eventMethodEClass, EVENT_METHOD__METHOD_NAME);

		xmlEventMethodContainerEClass = createEClass(XML_EVENT_METHOD_CONTAINER);
		createEReference(xmlEventMethodContainerEClass, XML_EVENT_METHOD_CONTAINER__PRE_PERSIST);
		createEReference(xmlEventMethodContainerEClass, XML_EVENT_METHOD_CONTAINER__POST_PERSIST);
		createEReference(xmlEventMethodContainerEClass, XML_EVENT_METHOD_CONTAINER__PRE_REMOVE);
		createEReference(xmlEventMethodContainerEClass, XML_EVENT_METHOD_CONTAINER__POST_REMOVE);
		createEReference(xmlEventMethodContainerEClass, XML_EVENT_METHOD_CONTAINER__PRE_UPDATE);
		createEReference(xmlEventMethodContainerEClass, XML_EVENT_METHOD_CONTAINER__POST_UPDATE);
		createEReference(xmlEventMethodContainerEClass, XML_EVENT_METHOD_CONTAINER__POST_LOAD);

		fieldResultEClass = createEClass(FIELD_RESULT);
		createEAttribute(fieldResultEClass, FIELD_RESULT__NAME);
		createEAttribute(fieldResultEClass, FIELD_RESULT__COLUMN);

		xmlGeneratedValueEClass = createEClass(XML_GENERATED_VALUE);
		createEAttribute(xmlGeneratedValueEClass, XML_GENERATED_VALUE__GENERATOR);
		createEAttribute(xmlGeneratedValueEClass, XML_GENERATED_VALUE__STRATEGY);

		xmlGeneratorEClass = createEClass(XML_GENERATOR);
		createEAttribute(xmlGeneratorEClass, XML_GENERATOR__NAME);
		createEAttribute(xmlGeneratorEClass, XML_GENERATOR__INITIAL_VALUE);
		createEAttribute(xmlGeneratorEClass, XML_GENERATOR__ALLOCATION_SIZE);

		xmlGeneratorContainerEClass = createEClass(XML_GENERATOR_CONTAINER);
		createEReference(xmlGeneratorContainerEClass, XML_GENERATOR_CONTAINER__SEQUENCE_GENERATOR);
		createEReference(xmlGeneratorContainerEClass, XML_GENERATOR_CONTAINER__TABLE_GENERATOR);

		xmlIdEClass = createEClass(XML_ID);
		createEReference(xmlIdEClass, XML_ID__GENERATED_VALUE);

		xmlIdClassEClass = createEClass(XML_ID_CLASS);
		createEAttribute(xmlIdClassEClass, XML_ID_CLASS__CLASS_NAME);

		inheritanceEClass = createEClass(INHERITANCE);
		createEAttribute(inheritanceEClass, INHERITANCE__STRATEGY);

		xmlJoinColumnEClass = createEClass(XML_JOIN_COLUMN);
		createEAttribute(xmlJoinColumnEClass, XML_JOIN_COLUMN__REFERENCED_COLUMN_NAME);

		xmlJoinColumnsMappingEClass = createEClass(XML_JOIN_COLUMNS_MAPPING);
		createEReference(xmlJoinColumnsMappingEClass, XML_JOIN_COLUMNS_MAPPING__JOIN_COLUMNS);

		xmlJoinTableEClass = createEClass(XML_JOIN_TABLE);
		createEReference(xmlJoinTableEClass, XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS);

		xmlJoinTableMappingEClass = createEClass(XML_JOIN_TABLE_MAPPING);
		createEReference(xmlJoinTableMappingEClass, XML_JOIN_TABLE_MAPPING__JOIN_TABLE);

		lobEClass = createEClass(LOB);

		xmlManyToManyEClass = createEClass(XML_MANY_TO_MANY);

		xmlManyToOneEClass = createEClass(XML_MANY_TO_ONE);

		mapKeyEClass = createEClass(MAP_KEY);
		createEAttribute(mapKeyEClass, MAP_KEY__NAME);

		xmlMapKeyClassEClass = createEClass(XML_MAP_KEY_CLASS);

		xmlMappedByMappingEClass = createEClass(XML_MAPPED_BY_MAPPING);
		createEAttribute(xmlMappedByMappingEClass, XML_MAPPED_BY_MAPPING__MAPPED_BY);

		xmlMappedSuperclassEClass = createEClass(XML_MAPPED_SUPERCLASS);
		createEReference(xmlMappedSuperclassEClass, XML_MAPPED_SUPERCLASS__ID_CLASS);
		createEAttribute(xmlMappedSuperclassEClass, XML_MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS);
		createEAttribute(xmlMappedSuperclassEClass, XML_MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS);
		createEReference(xmlMappedSuperclassEClass, XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS);

		xmlNamedNativeQueryEClass = createEClass(XML_NAMED_NATIVE_QUERY);
		createEAttribute(xmlNamedNativeQueryEClass, XML_NAMED_NATIVE_QUERY__RESULT_CLASS);
		createEAttribute(xmlNamedNativeQueryEClass, XML_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING);

		xmlNamedQueryEClass = createEClass(XML_NAMED_QUERY);

		xmlNullAttributeMappingEClass = createEClass(XML_NULL_ATTRIBUTE_MAPPING);

		xmlOneToManyEClass = createEClass(XML_ONE_TO_MANY);

		xmlOneToOneEClass = createEClass(XML_ONE_TO_ONE);
		createEReference(xmlOneToOneEClass, XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS);

		xmlOrderColumnEClass = createEClass(XML_ORDER_COLUMN);

		xmlPersistenceUnitDefaultsEClass = createEClass(XML_PERSISTENCE_UNIT_DEFAULTS);
		createEAttribute(xmlPersistenceUnitDefaultsEClass, XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA);
		createEAttribute(xmlPersistenceUnitDefaultsEClass, XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG);
		createEAttribute(xmlPersistenceUnitDefaultsEClass, XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST);
		createEReference(xmlPersistenceUnitDefaultsEClass, XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS);

		xmlPersistenceUnitMetadataEClass = createEClass(XML_PERSISTENCE_UNIT_METADATA);
		createEAttribute(xmlPersistenceUnitMetadataEClass, XML_PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE);
		createEReference(xmlPersistenceUnitMetadataEClass, XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS);

		xmlReferenceTableEClass = createEClass(XML_REFERENCE_TABLE);
		createEReference(xmlReferenceTableEClass, XML_REFERENCE_TABLE__JOIN_COLUMNS);

		postLoadEClass = createEClass(POST_LOAD);

		postPersistEClass = createEClass(POST_PERSIST);

		postRemoveEClass = createEClass(POST_REMOVE);

		postUpdateEClass = createEClass(POST_UPDATE);

		prePersistEClass = createEClass(PRE_PERSIST);

		preRemoveEClass = createEClass(PRE_REMOVE);

		preUpdateEClass = createEClass(PRE_UPDATE);

		xmlPrimaryKeyJoinColumnEClass = createEClass(XML_PRIMARY_KEY_JOIN_COLUMN);
		createEAttribute(xmlPrimaryKeyJoinColumnEClass, XML_PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME);

		xmlQueryEClass = createEClass(XML_QUERY);
		createEAttribute(xmlQueryEClass, XML_QUERY__NAME);
		createEAttribute(xmlQueryEClass, XML_QUERY__QUERY);
		createEReference(xmlQueryEClass, XML_QUERY__HINTS);

		xmlQueryContainerEClass = createEClass(XML_QUERY_CONTAINER);
		createEReference(xmlQueryContainerEClass, XML_QUERY_CONTAINER__NAMED_QUERIES);
		createEReference(xmlQueryContainerEClass, XML_QUERY_CONTAINER__NAMED_NATIVE_QUERIES);

		xmlQueryHintEClass = createEClass(XML_QUERY_HINT);
		createEAttribute(xmlQueryHintEClass, XML_QUERY_HINT__NAME);
		createEAttribute(xmlQueryHintEClass, XML_QUERY_HINT__VALUE);

		xmlSecondaryTableEClass = createEClass(XML_SECONDARY_TABLE);
		createEReference(xmlSecondaryTableEClass, XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS);

		xmlSequenceGeneratorEClass = createEClass(XML_SEQUENCE_GENERATOR);
		createEAttribute(xmlSequenceGeneratorEClass, XML_SEQUENCE_GENERATOR__SEQUENCE_NAME);

		sqlResultSetMappingEClass = createEClass(SQL_RESULT_SET_MAPPING);
		createEAttribute(sqlResultSetMappingEClass, SQL_RESULT_SET_MAPPING__NAME);
		createEReference(sqlResultSetMappingEClass, SQL_RESULT_SET_MAPPING__ENTITY_RESULTS);
		createEReference(sqlResultSetMappingEClass, SQL_RESULT_SET_MAPPING__COLUMN_RESULTS);

		xmlTableEClass = createEClass(XML_TABLE);

		xmlTableGeneratorEClass = createEClass(XML_TABLE_GENERATOR);
		createEAttribute(xmlTableGeneratorEClass, XML_TABLE_GENERATOR__TABLE);
		createEAttribute(xmlTableGeneratorEClass, XML_TABLE_GENERATOR__CATALOG);
		createEAttribute(xmlTableGeneratorEClass, XML_TABLE_GENERATOR__SCHEMA);
		createEAttribute(xmlTableGeneratorEClass, XML_TABLE_GENERATOR__PK_COLUMN_NAME);
		createEAttribute(xmlTableGeneratorEClass, XML_TABLE_GENERATOR__VALUE_COLUMN_NAME);
		createEAttribute(xmlTableGeneratorEClass, XML_TABLE_GENERATOR__PK_COLUMN_VALUE);
		createEReference(xmlTableGeneratorEClass, XML_TABLE_GENERATOR__UNIQUE_CONSTRAINTS);

		xmlTransientEClass = createEClass(XML_TRANSIENT);

		xmlTypeMappingEClass = createEClass(XML_TYPE_MAPPING);
		createEAttribute(xmlTypeMappingEClass, XML_TYPE_MAPPING__CLASS_NAME);
		createEAttribute(xmlTypeMappingEClass, XML_TYPE_MAPPING__METADATA_COMPLETE);
		createEAttribute(xmlTypeMappingEClass, XML_TYPE_MAPPING__DESCRIPTION);
		createEReference(xmlTypeMappingEClass, XML_TYPE_MAPPING__ATTRIBUTES);

		xmlUniqueConstraintEClass = createEClass(XML_UNIQUE_CONSTRAINT);
		createEAttribute(xmlUniqueConstraintEClass, XML_UNIQUE_CONSTRAINT__COLUMN_NAMES);

		xmlVersionEClass = createEClass(XML_VERSION);

		xmlOrderableEClass = createEClass(XML_ORDERABLE);
		createEAttribute(xmlOrderableEClass, XML_ORDERABLE__ORDER_BY);

		// Create enums
		accessTypeEEnum = createEEnum(ACCESS_TYPE);
		discriminatorTypeEEnum = createEEnum(DISCRIMINATOR_TYPE);
		enumTypeEEnum = createEEnum(ENUM_TYPE);
		fetchTypeEEnum = createEEnum(FETCH_TYPE);
		generationTypeEEnum = createEEnum(GENERATION_TYPE);
		inheritanceTypeEEnum = createEEnum(INHERITANCE_TYPE);
		temporalTypeEEnum = createEEnum(TEMPORAL_TYPE);
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
		OrmV2_0Package theOrmV2_0Package = (OrmV2_0Package)EPackage.Registry.INSTANCE.getEPackage(OrmV2_0Package.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
		CommonPackage theCommonPackage = (CommonPackage)EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theOrmV2_0Package);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		abstractXmlAttributeMappingEClass.getESuperTypes().add(this.getXmlAttributeMapping());
		abstractXmlColumnEClass.getESuperTypes().add(this.getAbstractXmlNamedColumn());
		abstractXmlEmbeddedEClass.getESuperTypes().add(this.getAbstractXmlAttributeMapping());
		abstractXmlEmbeddedEClass.getESuperTypes().add(this.getXmlAttributeOverrideContainer());
		abstractXmlMultiRelationshipMappingEClass.getESuperTypes().add(this.getAbstractXmlRelationshipMapping());
		abstractXmlMultiRelationshipMappingEClass.getESuperTypes().add(this.getXmlMappedByMapping());
		abstractXmlMultiRelationshipMappingEClass.getESuperTypes().add(this.getXmlJoinTableMapping());
		abstractXmlMultiRelationshipMappingEClass.getESuperTypes().add(this.getXmlOrderable());
		abstractXmlMultiRelationshipMappingEClass.getESuperTypes().add(theOrmV2_0Package.getXmlMultiRelationshipMapping_2_0());
		abstractXmlRelationshipMappingEClass.getESuperTypes().add(this.getAbstractXmlAttributeMapping());
		abstractXmlSingleRelationshipMappingEClass.getESuperTypes().add(this.getAbstractXmlRelationshipMapping());
		abstractXmlSingleRelationshipMappingEClass.getESuperTypes().add(this.getXmlJoinTableMapping());
		abstractXmlSingleRelationshipMappingEClass.getESuperTypes().add(this.getXmlJoinColumnsMapping());
		abstractXmlSingleRelationshipMappingEClass.getESuperTypes().add(theOrmV2_0Package.getXmlSingleRelationshipMapping_2_0());
		abstractXmlReferenceTableEClass.getESuperTypes().add(this.getAbstractXmlTable());
		abstractXmlReferenceTableEClass.getESuperTypes().add(this.getXmlReferenceTable());
		abstractXmlTypeMappingEClass.getESuperTypes().add(this.getXmlTypeMapping());
		xmlAttributeMappingEClass.getESuperTypes().add(theOrmV2_0Package.getXmlAttributeMapping_2_0());
		xmlAssociationOverrideEClass.getESuperTypes().add(this.getXmlJoinColumnsMapping());
		xmlAssociationOverrideEClass.getESuperTypes().add(theOrmV2_0Package.getXmlAssociationOverride_2_0());
		xmlAttributeOverrideEClass.getESuperTypes().add(theOrmV2_0Package.getXmlAttributeOverride_2_0());
		attributesEClass.getESuperTypes().add(theOrmV2_0Package.getXmlAttributes_2_0());
		xmlBasicEClass.getESuperTypes().add(this.getAbstractXmlAttributeMapping());
		xmlBasicEClass.getESuperTypes().add(this.getColumnMapping());
		xmlBasicEClass.getESuperTypes().add(this.getXmlConvertibleMapping());
		xmlCollectionTableEClass.getESuperTypes().add(this.getAbstractXmlReferenceTable());
		xmlCollectionTableEClass.getESuperTypes().add(theOrmV2_0Package.getXmlCollectionTable_2_0());
		xmlColumnEClass.getESuperTypes().add(this.getAbstractXmlColumn());
		xmlDiscriminatorColumnEClass.getESuperTypes().add(this.getAbstractXmlNamedColumn());
		xmlElementCollectionEClass.getESuperTypes().add(this.getAbstractXmlAttributeMapping());
		xmlElementCollectionEClass.getESuperTypes().add(theOrmV2_0Package.getXmlElementCollection_2_0());
		xmlEmbeddableEClass.getESuperTypes().add(this.getAbstractXmlTypeMapping());
		xmlEmbeddedEClass.getESuperTypes().add(this.getAbstractXmlEmbedded());
		xmlEmbeddedEClass.getESuperTypes().add(theOrmV2_0Package.getXmlEmbedded_2_0());
		xmlEmbeddedIdEClass.getESuperTypes().add(this.getAbstractXmlEmbedded());
		xmlEntityEClass.getESuperTypes().add(this.getAbstractXmlTypeMapping());
		xmlEntityEClass.getESuperTypes().add(this.getXmlQueryContainer());
		xmlEntityEClass.getESuperTypes().add(this.getXmlGeneratorContainer());
		xmlEntityEClass.getESuperTypes().add(this.getXmlEventMethodContainer());
		xmlEntityEClass.getESuperTypes().add(this.getXmlAttributeOverrideContainer());
		xmlEntityEClass.getESuperTypes().add(this.getXmlAssociationOverrideContainer());
		xmlEntityEClass.getESuperTypes().add(theOrmV2_0Package.getXmlEntity_2_0());
		entityListenerEClass.getESuperTypes().add(this.getXmlEventMethodContainer());
		entityListenerEClass.getESuperTypes().add(theOrmV2_0Package.getXmlEntityListener_2_0());
		xmlEntityMappingsEClass.getESuperTypes().add(theCommonPackage.getAbstractJpaRootEObject());
		xmlEntityMappingsEClass.getESuperTypes().add(this.getXmlQueryContainer());
		xmlEntityMappingsEClass.getESuperTypes().add(this.getXmlAccessHolder());
		eventMethodEClass.getESuperTypes().add(theOrmV2_0Package.getXmlEventMethod_2_0());
		xmlGeneratorEClass.getESuperTypes().add(theOrmV2_0Package.getXmlGenerator_2_0());
		xmlIdEClass.getESuperTypes().add(this.getAbstractXmlAttributeMapping());
		xmlIdEClass.getESuperTypes().add(this.getColumnMapping());
		xmlIdEClass.getESuperTypes().add(this.getXmlConvertibleMapping());
		xmlIdEClass.getESuperTypes().add(this.getXmlGeneratorContainer());
		xmlJoinColumnEClass.getESuperTypes().add(this.getAbstractXmlColumn());
		xmlJoinTableEClass.getESuperTypes().add(this.getAbstractXmlReferenceTable());
		xmlManyToManyEClass.getESuperTypes().add(this.getAbstractXmlMultiRelationshipMapping());
		xmlManyToManyEClass.getESuperTypes().add(theOrmV2_0Package.getXmlManyToMany_2_0());
		xmlManyToOneEClass.getESuperTypes().add(this.getAbstractXmlSingleRelationshipMapping());
		xmlMapKeyClassEClass.getESuperTypes().add(theOrmV2_0Package.getXmlMapKeyClass_2_0());
		xmlMappedSuperclassEClass.getESuperTypes().add(this.getAbstractXmlTypeMapping());
		xmlNamedNativeQueryEClass.getESuperTypes().add(this.getXmlQuery());
		xmlNamedQueryEClass.getESuperTypes().add(this.getXmlQuery());
		xmlNamedQueryEClass.getESuperTypes().add(theOrmV2_0Package.getXmlNamedQuery_2_0());
		xmlNullAttributeMappingEClass.getESuperTypes().add(this.getAbstractXmlAttributeMapping());
		xmlOneToManyEClass.getESuperTypes().add(this.getAbstractXmlMultiRelationshipMapping());
		xmlOneToManyEClass.getESuperTypes().add(this.getXmlJoinColumnsMapping());
		xmlOneToManyEClass.getESuperTypes().add(theOrmV2_0Package.getXmlOneToMany_2_0());
		xmlOneToOneEClass.getESuperTypes().add(this.getAbstractXmlSingleRelationshipMapping());
		xmlOneToOneEClass.getESuperTypes().add(this.getXmlMappedByMapping());
		xmlOneToOneEClass.getESuperTypes().add(theOrmV2_0Package.getXmlOneToOne_2_0());
		xmlOrderColumnEClass.getESuperTypes().add(this.getAbstractXmlNamedColumn());
		xmlOrderColumnEClass.getESuperTypes().add(theOrmV2_0Package.getXmlOrderColumn_2_0());
		xmlPersistenceUnitDefaultsEClass.getESuperTypes().add(this.getXmlAccessHolder());
		xmlPersistenceUnitDefaultsEClass.getESuperTypes().add(theOrmV2_0Package.getXmlPersistenceUnitDefaults_2_0());
		xmlPersistenceUnitMetadataEClass.getESuperTypes().add(theOrmV2_0Package.getXmlPersistenceUnitMetadata_2_0());
		postLoadEClass.getESuperTypes().add(this.getEventMethod());
		postPersistEClass.getESuperTypes().add(this.getEventMethod());
		postRemoveEClass.getESuperTypes().add(this.getEventMethod());
		postUpdateEClass.getESuperTypes().add(this.getEventMethod());
		prePersistEClass.getESuperTypes().add(this.getEventMethod());
		preRemoveEClass.getESuperTypes().add(this.getEventMethod());
		preUpdateEClass.getESuperTypes().add(this.getEventMethod());
		xmlPrimaryKeyJoinColumnEClass.getESuperTypes().add(this.getAbstractXmlNamedColumn());
		xmlQueryEClass.getESuperTypes().add(theOrmV2_0Package.getXmlQuery_2_0());
		xmlQueryHintEClass.getESuperTypes().add(theOrmV2_0Package.getXmlQueryHint_2_0());
		xmlSecondaryTableEClass.getESuperTypes().add(this.getAbstractXmlTable());
		xmlSequenceGeneratorEClass.getESuperTypes().add(this.getXmlGenerator());
		xmlSequenceGeneratorEClass.getESuperTypes().add(theOrmV2_0Package.getXmlSequenceGenerator_2_0());
		sqlResultSetMappingEClass.getESuperTypes().add(theOrmV2_0Package.getXmlSqlResultSetMapping_2_0());
		xmlTableEClass.getESuperTypes().add(this.getAbstractXmlTable());
		xmlTableGeneratorEClass.getESuperTypes().add(this.getXmlGenerator());
		xmlTransientEClass.getESuperTypes().add(this.getAbstractXmlAttributeMapping());
		xmlTypeMappingEClass.getESuperTypes().add(this.getXmlAccessHolder());
		xmlUniqueConstraintEClass.getESuperTypes().add(theOrmV2_0Package.getXmlUniqueConstraint_2_0());
		xmlVersionEClass.getESuperTypes().add(this.getAbstractXmlAttributeMapping());
		xmlVersionEClass.getESuperTypes().add(this.getColumnMapping());
		xmlVersionEClass.getESuperTypes().add(this.getXmlConvertibleMapping());
		xmlOrderableEClass.getESuperTypes().add(theOrmV2_0Package.getXmlOrderable_2_0());

		// Initialize classes and features; add operations and parameters
		initEClass(abstractXmlAttributeMappingEClass, AbstractXmlAttributeMapping.class, "AbstractXmlAttributeMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(abstractXmlColumnEClass, AbstractXmlColumn.class, "AbstractXmlColumn", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractXmlColumn_Insertable(), theXMLTypePackage.getBooleanObject(), "insertable", null, 0, 1, AbstractXmlColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlColumn_Nullable(), theXMLTypePackage.getBooleanObject(), "nullable", null, 0, 1, AbstractXmlColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlColumn_Table(), theXMLTypePackage.getString(), "table", null, 0, 1, AbstractXmlColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlColumn_Unique(), theXMLTypePackage.getBooleanObject(), "unique", null, 0, 1, AbstractXmlColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlColumn_Updatable(), theXMLTypePackage.getBooleanObject(), "updatable", null, 0, 1, AbstractXmlColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractXmlEmbeddedEClass, AbstractXmlEmbedded.class, "AbstractXmlEmbedded", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(abstractXmlMultiRelationshipMappingEClass, AbstractXmlMultiRelationshipMapping.class, "AbstractXmlMultiRelationshipMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractXmlMultiRelationshipMapping_MapKey(), this.getMapKey(), null, "mapKey", null, 0, 1, AbstractXmlMultiRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractXmlNamedColumnEClass, AbstractXmlNamedColumn.class, "AbstractXmlNamedColumn", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractXmlNamedColumn_ColumnDefinition(), theXMLTypePackage.getString(), "columnDefinition", null, 0, 1, AbstractXmlNamedColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlNamedColumn_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, AbstractXmlNamedColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractXmlRelationshipMappingEClass, AbstractXmlRelationshipMapping.class, "AbstractXmlRelationshipMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractXmlRelationshipMapping_TargetEntity(), theXMLTypePackage.getString(), "targetEntity", null, 0, 1, AbstractXmlRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlRelationshipMapping_Fetch(), this.getFetchType(), "fetch", "LAZY", 0, 1, AbstractXmlRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractXmlRelationshipMapping_Cascade(), this.getCascadeType(), null, "cascade", null, 0, 1, AbstractXmlRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractXmlSingleRelationshipMappingEClass, AbstractXmlSingleRelationshipMapping.class, "AbstractXmlSingleRelationshipMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractXmlSingleRelationshipMapping_Optional(), theXMLTypePackage.getBooleanObject(), "optional", null, 0, 1, AbstractXmlSingleRelationshipMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractXmlReferenceTableEClass, AbstractXmlReferenceTable.class, "AbstractXmlReferenceTable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(abstractXmlTableEClass, AbstractXmlTable.class, "AbstractXmlTable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractXmlTable_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, AbstractXmlTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlTable_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, AbstractXmlTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractXmlTable_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, AbstractXmlTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractXmlTable_UniqueConstraints(), this.getXmlUniqueConstraint(), null, "uniqueConstraints", null, 0, -1, AbstractXmlTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractXmlTypeMappingEClass, AbstractXmlTypeMapping.class, "AbstractXmlTypeMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlAccessHolderEClass, XmlAccessHolder.class, "XmlAccessHolder", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlAccessHolder_Access(), this.getAccessType(), "access", null, 0, 1, XmlAccessHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlAttributeMappingEClass, XmlAttributeMapping.class, "XmlAttributeMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlAttributeMapping_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, XmlAttributeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlAssociationOverrideEClass, XmlAssociationOverride.class, "XmlAssociationOverride", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlAssociationOverride_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, XmlAssociationOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlAssociationOverrideContainerEClass, XmlAssociationOverrideContainer.class, "XmlAssociationOverrideContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlAssociationOverrideContainer_AssociationOverrides(), this.getXmlAssociationOverride(), null, "associationOverrides", null, 0, -1, XmlAssociationOverrideContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlAttributeOverrideEClass, XmlAttributeOverride.class, "XmlAttributeOverride", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlAttributeOverride_Column(), this.getXmlColumn(), null, "column", null, 1, 1, XmlAttributeOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlAttributeOverride_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, XmlAttributeOverride.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlAttributeOverrideContainerEClass, XmlAttributeOverrideContainer.class, "XmlAttributeOverrideContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlAttributeOverrideContainer_AttributeOverrides(), this.getXmlAttributeOverride(), null, "attributeOverrides", null, 0, -1, XmlAttributeOverrideContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributesEClass, Attributes.class, "Attributes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributes_Ids(), this.getXmlId(), null, "ids", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_EmbeddedIds(), this.getXmlEmbeddedId(), null, "embeddedIds", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_Basics(), this.getXmlBasic(), null, "basics", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_Versions(), this.getXmlVersion(), null, "versions", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_ManyToOnes(), this.getXmlManyToOne(), null, "manyToOnes", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_OneToManys(), this.getXmlOneToMany(), null, "oneToManys", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_OneToOnes(), this.getXmlOneToOne(), null, "oneToOnes", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_ManyToManys(), this.getXmlManyToMany(), null, "manyToManys", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_Embeddeds(), this.getXmlEmbedded(), null, "embeddeds", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributes_Transients(), this.getXmlTransient(), null, "transients", null, 0, -1, Attributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlBasicEClass, XmlBasic.class, "XmlBasic", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlBasic_Fetch(), this.getFetchType(), "fetch", "LAZY", 0, 1, XmlBasic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlBasic_Optional(), theXMLTypePackage.getBooleanObject(), "optional", null, 0, 1, XmlBasic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cascadeTypeEClass, CascadeType.class, "CascadeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCascadeType_CascadeAll(), theXMLTypePackage.getBoolean(), "cascadeAll", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCascadeType_CascadePersist(), theXMLTypePackage.getBoolean(), "cascadePersist", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCascadeType_CascadeMerge(), theXMLTypePackage.getBoolean(), "cascadeMerge", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCascadeType_CascadeRemove(), theXMLTypePackage.getBoolean(), "cascadeRemove", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCascadeType_CascadeRefresh(), theXMLTypePackage.getBoolean(), "cascadeRefresh", null, 0, 1, CascadeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlCollectionTableEClass, XmlCollectionTable.class, "XmlCollectionTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlColumnEClass, XmlColumn.class, "XmlColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlColumn_Length(), theXMLTypePackage.getIntObject(), "length", null, 0, 1, XmlColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlColumn_Precision(), theXMLTypePackage.getIntObject(), "precision", null, 0, 1, XmlColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlColumn_Scale(), theXMLTypePackage.getIntObject(), "scale", null, 0, 1, XmlColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(columnMappingEClass, ColumnMapping.class, "ColumnMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getColumnMapping_Column(), this.getXmlColumn(), null, "column", null, 0, 1, ColumnMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(columnResultEClass, ColumnResult.class, "ColumnResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getColumnResult_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ColumnResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlConvertibleMappingEClass, XmlConvertibleMapping.class, "XmlConvertibleMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlConvertibleMapping_Lob(), theXMLTypePackage.getBoolean(), "lob", null, 0, 1, XmlConvertibleMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlConvertibleMapping_Temporal(), this.getTemporalType(), "temporal", null, 0, 1, XmlConvertibleMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlConvertibleMapping_Enumerated(), this.getEnumType(), "enumerated", null, 0, 1, XmlConvertibleMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlDiscriminatorColumnEClass, XmlDiscriminatorColumn.class, "XmlDiscriminatorColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlDiscriminatorColumn_DiscriminatorType(), this.getDiscriminatorType(), "discriminatorType", "STRING", 0, 1, XmlDiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlDiscriminatorColumn_Length(), theXMLTypePackage.getIntObject(), "length", null, 0, 1, XmlDiscriminatorColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlElementCollectionEClass, XmlElementCollection.class, "XmlElementCollection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEmbeddableEClass, XmlEmbeddable.class, "XmlEmbeddable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEmbeddedEClass, XmlEmbedded.class, "XmlEmbedded", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEmbeddedIdEClass, XmlEmbeddedId.class, "XmlEmbeddedId", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlEntityEClass, XmlEntity.class, "XmlEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlEntity_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_Table(), this.getXmlTable(), null, "table", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_SecondaryTables(), this.getXmlSecondaryTable(), null, "secondaryTables", null, 0, -1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_PrimaryKeyJoinColumns(), this.getXmlPrimaryKeyJoinColumn(), null, "primaryKeyJoinColumns", null, 0, -1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_IdClass(), this.getXmlIdClass(), null, "idClass", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_Inheritance(), this.getInheritance(), null, "inheritance", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlEntity_DiscriminatorValue(), theXMLTypePackage.getString(), "discriminatorValue", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_DiscriminatorColumn(), this.getXmlDiscriminatorColumn(), null, "discriminatorColumn", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_SqlResultSetMappings(), this.getSqlResultSetMapping(), null, "sqlResultSetMappings", null, 0, -1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlEntity_ExcludeDefaultListeners(), theXMLTypePackage.getBoolean(), "excludeDefaultListeners", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlEntity_ExcludeSuperclassListeners(), theXMLTypePackage.getBoolean(), "excludeSuperclassListeners", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntity_EntityListeners(), this.getEntityListeners(), null, "entityListeners", null, 0, 1, XmlEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityListenerEClass, EntityListener.class, "EntityListener", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntityListener_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, EntityListener.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityListenersEClass, EntityListeners.class, "EntityListeners", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntityListeners_EntityListeners(), this.getEntityListener(), null, "entityListeners", null, 0, -1, EntityListeners.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEntityMappingsEClass, XmlEntityMappings.class, "XmlEntityMappings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlEntityMappings_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlEntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_PersistenceUnitMetadata(), this.getXmlPersistenceUnitMetadata(), null, "persistenceUnitMetadata", null, 0, 1, XmlEntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlEntityMappings_Package(), theXMLTypePackage.getString(), "package", null, 0, 1, XmlEntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlEntityMappings_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, XmlEntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlEntityMappings_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, XmlEntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_SequenceGenerators(), this.getXmlSequenceGenerator(), null, "sequenceGenerators", null, 0, -1, XmlEntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_TableGenerators(), this.getXmlTableGenerator(), null, "tableGenerators", null, 0, -1, XmlEntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_SqlResultSetMappings(), this.getSqlResultSetMapping(), null, "sqlResultSetMappings", null, 0, -1, XmlEntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_MappedSuperclasses(), this.getXmlMappedSuperclass(), null, "mappedSuperclasses", null, 0, -1, XmlEntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_Entities(), this.getXmlEntity(), null, "entities", null, 0, -1, XmlEntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEntityMappings_Embeddables(), this.getXmlEmbeddable(), null, "embeddables", null, 0, -1, XmlEntityMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityResultEClass, EntityResult.class, "EntityResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntityResult_DiscriminatorColumn(), theXMLTypePackage.getString(), "discriminatorColumn", null, 0, 1, EntityResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityResult_EntityClass(), theXMLTypePackage.getString(), "entityClass", null, 1, 1, EntityResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityResult_FieldResults(), this.getFieldResult(), null, "fieldResults", null, 0, -1, EntityResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventMethodEClass, EventMethod.class, "EventMethod", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEventMethod_MethodName(), theXMLTypePackage.getString(), "methodName", null, 1, 1, EventMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlEventMethodContainerEClass, XmlEventMethodContainer.class, "XmlEventMethodContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlEventMethodContainer_PrePersist(), this.getPrePersist(), null, "prePersist", null, 0, 1, XmlEventMethodContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEventMethodContainer_PostPersist(), this.getPostPersist(), null, "postPersist", null, 0, 1, XmlEventMethodContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEventMethodContainer_PreRemove(), this.getPreRemove(), null, "preRemove", null, 0, 1, XmlEventMethodContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEventMethodContainer_PostRemove(), this.getPostRemove(), null, "postRemove", null, 0, 1, XmlEventMethodContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEventMethodContainer_PreUpdate(), this.getPreUpdate(), null, "preUpdate", null, 0, 1, XmlEventMethodContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEventMethodContainer_PostUpdate(), this.getPostUpdate(), null, "postUpdate", null, 0, 1, XmlEventMethodContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlEventMethodContainer_PostLoad(), this.getPostLoad(), null, "postLoad", null, 0, 1, XmlEventMethodContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fieldResultEClass, FieldResult.class, "FieldResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFieldResult_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, FieldResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldResult_Column(), theXMLTypePackage.getString(), "column", null, 1, 1, FieldResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlGeneratedValueEClass, XmlGeneratedValue.class, "XmlGeneratedValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlGeneratedValue_Generator(), theXMLTypePackage.getString(), "generator", null, 0, 1, XmlGeneratedValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlGeneratedValue_Strategy(), this.getGenerationType(), "strategy", "TABLE", 0, 1, XmlGeneratedValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlGeneratorEClass, XmlGenerator.class, "XmlGenerator", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlGenerator_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, XmlGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlGenerator_InitialValue(), theXMLTypePackage.getIntObject(), "initialValue", null, 0, 1, XmlGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlGenerator_AllocationSize(), theXMLTypePackage.getIntObject(), "allocationSize", null, 0, 1, XmlGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlGeneratorContainerEClass, XmlGeneratorContainer.class, "XmlGeneratorContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlGeneratorContainer_SequenceGenerator(), this.getXmlSequenceGenerator(), null, "sequenceGenerator", null, 0, 1, XmlGeneratorContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlGeneratorContainer_TableGenerator(), this.getXmlTableGenerator(), null, "tableGenerator", null, 0, 1, XmlGeneratorContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlIdEClass, XmlId.class, "XmlId", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlId_GeneratedValue(), this.getXmlGeneratedValue(), null, "generatedValue", null, 0, 1, XmlId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlIdClassEClass, XmlIdClass.class, "XmlIdClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlIdClass_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, XmlIdClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inheritanceEClass, Inheritance.class, "Inheritance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInheritance_Strategy(), this.getInheritanceType(), "strategy", "SINGLE_TABLE", 0, 1, Inheritance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlJoinColumnEClass, XmlJoinColumn.class, "XmlJoinColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlJoinColumn_ReferencedColumnName(), theXMLTypePackage.getString(), "referencedColumnName", null, 0, 1, XmlJoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlJoinColumnsMappingEClass, XmlJoinColumnsMapping.class, "XmlJoinColumnsMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlJoinColumnsMapping_JoinColumns(), this.getXmlJoinColumn(), null, "joinColumns", null, 0, -1, XmlJoinColumnsMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlJoinTableEClass, XmlJoinTable.class, "XmlJoinTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlJoinTable_InverseJoinColumns(), this.getXmlJoinColumn(), null, "inverseJoinColumns", null, 0, -1, XmlJoinTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlJoinTableMappingEClass, XmlJoinTableMapping.class, "XmlJoinTableMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlJoinTableMapping_JoinTable(), this.getXmlJoinTable(), null, "joinTable", null, 0, 1, XmlJoinTableMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(lobEClass, Lob.class, "Lob", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlManyToManyEClass, XmlManyToMany.class, "XmlManyToMany", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlManyToOneEClass, XmlManyToOne.class, "XmlManyToOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mapKeyEClass, MapKey.class, "MapKey", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapKey_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, MapKey.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMapKeyClassEClass, XmlMapKeyClass.class, "XmlMapKeyClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlMappedByMappingEClass, XmlMappedByMapping.class, "XmlMappedByMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlMappedByMapping_MappedBy(), theXMLTypePackage.getString(), "mappedBy", null, 0, 1, XmlMappedByMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlMappedSuperclassEClass, XmlMappedSuperclass.class, "XmlMappedSuperclass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlMappedSuperclass_IdClass(), this.getXmlIdClass(), null, "idClass", null, 0, 1, XmlMappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlMappedSuperclass_ExcludeDefaultListeners(), theXMLTypePackage.getBoolean(), "excludeDefaultListeners", null, 0, 1, XmlMappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlMappedSuperclass_ExcludeSuperclassListeners(), theXMLTypePackage.getBoolean(), "excludeSuperclassListeners", null, 0, 1, XmlMappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlMappedSuperclass_EntityListeners(), this.getEntityListeners(), null, "entityListeners", null, 0, 1, XmlMappedSuperclass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNamedNativeQueryEClass, XmlNamedNativeQuery.class, "XmlNamedNativeQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlNamedNativeQuery_ResultClass(), theXMLTypePackage.getString(), "resultClass", null, 0, 1, XmlNamedNativeQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlNamedNativeQuery_ResultSetMapping(), theXMLTypePackage.getString(), "resultSetMapping", null, 0, 1, XmlNamedNativeQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlNamedQueryEClass, XmlNamedQuery.class, "XmlNamedQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlNullAttributeMappingEClass, XmlNullAttributeMapping.class, "XmlNullAttributeMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlOneToManyEClass, XmlOneToMany.class, "XmlOneToMany", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlOneToOneEClass, XmlOneToOne.class, "XmlOneToOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlOneToOne_PrimaryKeyJoinColumns(), this.getXmlPrimaryKeyJoinColumn(), null, "primaryKeyJoinColumns", null, 0, -1, XmlOneToOne.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlOrderColumnEClass, XmlOrderColumn.class, "XmlOrderColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPersistenceUnitDefaultsEClass, XmlPersistenceUnitDefaults.class, "XmlPersistenceUnitDefaults", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPersistenceUnitDefaults_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, XmlPersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistenceUnitDefaults_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, XmlPersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlPersistenceUnitDefaults_CascadePersist(), theXMLTypePackage.getBoolean(), "cascadePersist", null, 0, 1, XmlPersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPersistenceUnitDefaults_EntityListeners(), this.getEntityListeners(), null, "entityListeners", null, 0, 1, XmlPersistenceUnitDefaults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlPersistenceUnitMetadataEClass, XmlPersistenceUnitMetadata.class, "XmlPersistenceUnitMetadata", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPersistenceUnitMetadata_XmlMappingMetadataComplete(), theXMLTypePackage.getBoolean(), "xmlMappingMetadataComplete", null, 0, 1, XmlPersistenceUnitMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlPersistenceUnitMetadata_PersistenceUnitDefaults(), this.getXmlPersistenceUnitDefaults(), null, "persistenceUnitDefaults", null, 0, 1, XmlPersistenceUnitMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlReferenceTableEClass, XmlReferenceTable.class, "XmlReferenceTable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlReferenceTable_JoinColumns(), this.getXmlJoinColumn(), null, "joinColumns", null, 0, -1, XmlReferenceTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(postLoadEClass, PostLoad.class, "PostLoad", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(postPersistEClass, PostPersist.class, "PostPersist", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(postRemoveEClass, PostRemove.class, "PostRemove", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(postUpdateEClass, PostUpdate.class, "PostUpdate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(prePersistEClass, PrePersist.class, "PrePersist", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(preRemoveEClass, PreRemove.class, "PreRemove", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(preUpdateEClass, PreUpdate.class, "PreUpdate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlPrimaryKeyJoinColumnEClass, XmlPrimaryKeyJoinColumn.class, "XmlPrimaryKeyJoinColumn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlPrimaryKeyJoinColumn_ReferencedColumnName(), theXMLTypePackage.getString(), "referencedColumnName", null, 0, 1, XmlPrimaryKeyJoinColumn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlQueryEClass, XmlQuery.class, "XmlQuery", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlQuery_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, XmlQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlQuery_Query(), theXMLTypePackage.getString(), "query", null, 1, 1, XmlQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlQuery_Hints(), this.getXmlQueryHint(), null, "hints", null, 0, -1, XmlQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlQueryContainerEClass, XmlQueryContainer.class, "XmlQueryContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlQueryContainer_NamedQueries(), this.getXmlNamedQuery(), null, "namedQueries", null, 0, -1, XmlQueryContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlQueryContainer_NamedNativeQueries(), this.getXmlNamedNativeQuery(), null, "namedNativeQueries", null, 0, -1, XmlQueryContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlQueryHintEClass, XmlQueryHint.class, "XmlQueryHint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlQueryHint_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, XmlQueryHint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlQueryHint_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, XmlQueryHint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlSecondaryTableEClass, XmlSecondaryTable.class, "XmlSecondaryTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getXmlSecondaryTable_PrimaryKeyJoinColumns(), this.getXmlPrimaryKeyJoinColumn(), null, "primaryKeyJoinColumns", null, 0, -1, XmlSecondaryTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlSequenceGeneratorEClass, XmlSequenceGenerator.class, "XmlSequenceGenerator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlSequenceGenerator_SequenceName(), theXMLTypePackage.getString(), "sequenceName", null, 0, 1, XmlSequenceGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sqlResultSetMappingEClass, SqlResultSetMapping.class, "SqlResultSetMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSqlResultSetMapping_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, SqlResultSetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSqlResultSetMapping_EntityResults(), this.getEntityResult(), null, "entityResults", null, 0, -1, SqlResultSetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSqlResultSetMapping_ColumnResults(), this.getColumnResult(), null, "columnResults", null, 0, -1, SqlResultSetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTableEClass, XmlTable.class, "XmlTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlTableGeneratorEClass, XmlTableGenerator.class, "XmlTableGenerator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlTableGenerator_Table(), theXMLTypePackage.getString(), "table", null, 0, 1, XmlTableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTableGenerator_Catalog(), theXMLTypePackage.getString(), "catalog", null, 0, 1, XmlTableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTableGenerator_Schema(), theXMLTypePackage.getString(), "schema", null, 0, 1, XmlTableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTableGenerator_PkColumnName(), theXMLTypePackage.getString(), "pkColumnName", null, 0, 1, XmlTableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTableGenerator_ValueColumnName(), theXMLTypePackage.getString(), "valueColumnName", null, 0, 1, XmlTableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTableGenerator_PkColumnValue(), theXMLTypePackage.getString(), "pkColumnValue", null, 0, 1, XmlTableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlTableGenerator_UniqueConstraints(), this.getXmlUniqueConstraint(), null, "uniqueConstraints", null, 0, -1, XmlTableGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlTransientEClass, XmlTransient.class, "XmlTransient", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlTypeMappingEClass, XmlTypeMapping.class, "XmlTypeMapping", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlTypeMapping_ClassName(), theXMLTypePackage.getString(), "className", null, 1, 1, XmlTypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTypeMapping_MetadataComplete(), theXMLTypePackage.getBooleanObject(), "metadataComplete", null, 0, 1, XmlTypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXmlTypeMapping_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, XmlTypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXmlTypeMapping_Attributes(), this.getAttributes(), null, "attributes", null, 0, 1, XmlTypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlUniqueConstraintEClass, XmlUniqueConstraint.class, "XmlUniqueConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlUniqueConstraint_ColumnNames(), theXMLTypePackage.getString(), "columnNames", null, 1, -1, XmlUniqueConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(xmlVersionEClass, XmlVersion.class, "XmlVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(xmlOrderableEClass, XmlOrderable.class, "XmlOrderable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXmlOrderable_OrderBy(), theXMLTypePackage.getString(), "orderBy", null, 0, 1, XmlOrderable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(accessTypeEEnum, AccessType.class, "AccessType");
		addEEnumLiteral(accessTypeEEnum, AccessType.PROPERTY);
		addEEnumLiteral(accessTypeEEnum, AccessType.FIELD);

		initEEnum(discriminatorTypeEEnum, DiscriminatorType.class, "DiscriminatorType");
		addEEnumLiteral(discriminatorTypeEEnum, DiscriminatorType.STRING);
		addEEnumLiteral(discriminatorTypeEEnum, DiscriminatorType.CHAR);
		addEEnumLiteral(discriminatorTypeEEnum, DiscriminatorType.INTEGER);

		initEEnum(enumTypeEEnum, EnumType.class, "EnumType");
		addEEnumLiteral(enumTypeEEnum, EnumType.ORDINAL);
		addEEnumLiteral(enumTypeEEnum, EnumType.STRING);

		initEEnum(fetchTypeEEnum, FetchType.class, "FetchType");
		addEEnumLiteral(fetchTypeEEnum, FetchType.LAZY);
		addEEnumLiteral(fetchTypeEEnum, FetchType.EAGER);

		initEEnum(generationTypeEEnum, GenerationType.class, "GenerationType");
		addEEnumLiteral(generationTypeEEnum, GenerationType.TABLE);
		addEEnumLiteral(generationTypeEEnum, GenerationType.SEQUENCE);
		addEEnumLiteral(generationTypeEEnum, GenerationType.IDENTITY);
		addEEnumLiteral(generationTypeEEnum, GenerationType.AUTO);

		initEEnum(inheritanceTypeEEnum, InheritanceType.class, "InheritanceType");
		addEEnumLiteral(inheritanceTypeEEnum, InheritanceType.SINGLE_TABLE);
		addEEnumLiteral(inheritanceTypeEEnum, InheritanceType.JOINED);
		addEEnumLiteral(inheritanceTypeEEnum, InheritanceType.TABLE_PER_CLASS);

		initEEnum(temporalTypeEEnum, TemporalType.class, "TemporalType");
		addEEnumLiteral(temporalTypeEEnum, TemporalType.DATE);
		addEEnumLiteral(temporalTypeEEnum, TemporalType.TIME);
		addEEnumLiteral(temporalTypeEEnum, TemporalType.TIMESTAMP);

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
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlAttributeMapping <em>Abstract Xml Attribute Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlAttributeMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlAttributeMapping()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_ATTRIBUTE_MAPPING = eINSTANCE.getAbstractXmlAttributeMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlColumn <em>Abstract Xml Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlColumn
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlColumn()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_COLUMN = eINSTANCE.getAbstractXmlColumn();

		/**
		 * The meta object literal for the '<em><b>Insertable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_COLUMN__INSERTABLE = eINSTANCE.getAbstractXmlColumn_Insertable();

		/**
		 * The meta object literal for the '<em><b>Nullable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_COLUMN__NULLABLE = eINSTANCE.getAbstractXmlColumn_Nullable();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_COLUMN__TABLE = eINSTANCE.getAbstractXmlColumn_Table();

		/**
		 * The meta object literal for the '<em><b>Unique</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_COLUMN__UNIQUE = eINSTANCE.getAbstractXmlColumn_Unique();

		/**
		 * The meta object literal for the '<em><b>Updatable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_COLUMN__UPDATABLE = eINSTANCE.getAbstractXmlColumn_Updatable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlEmbedded <em>Abstract Xml Embedded</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlEmbedded
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlEmbedded()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_EMBEDDED = eINSTANCE.getAbstractXmlEmbedded();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping <em>Abstract Xml Multi Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlMultiRelationshipMapping()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING = eINSTANCE.getAbstractXmlMultiRelationshipMapping();

		/**
		 * The meta object literal for the '<em><b>Map Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY = eINSTANCE.getAbstractXmlMultiRelationshipMapping_MapKey();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlNamedColumn <em>Abstract Xml Named Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlNamedColumn
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlNamedColumn()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_NAMED_COLUMN = eINSTANCE.getAbstractXmlNamedColumn();

		/**
		 * The meta object literal for the '<em><b>Column Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION = eINSTANCE.getAbstractXmlNamedColumn_ColumnDefinition();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_NAMED_COLUMN__NAME = eINSTANCE.getAbstractXmlNamedColumn_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping <em>Abstract Xml Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlRelationshipMapping()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_RELATIONSHIP_MAPPING = eINSTANCE.getAbstractXmlRelationshipMapping();

		/**
		 * The meta object literal for the '<em><b>Target Entity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_RELATIONSHIP_MAPPING__TARGET_ENTITY = eINSTANCE.getAbstractXmlRelationshipMapping_TargetEntity();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_RELATIONSHIP_MAPPING__FETCH = eINSTANCE.getAbstractXmlRelationshipMapping_Fetch();

		/**
		 * The meta object literal for the '<em><b>Cascade</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ABSTRACT_XML_RELATIONSHIP_MAPPING__CASCADE = eINSTANCE.getAbstractXmlRelationshipMapping_Cascade();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping <em>Abstract Xml Single Relationship Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlSingleRelationshipMapping()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING = eINSTANCE.getAbstractXmlSingleRelationshipMapping();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL = eINSTANCE.getAbstractXmlSingleRelationshipMapping_Optional();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlTable <em>Abstract Xml Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlTable
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlTable()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_TABLE = eINSTANCE.getAbstractXmlTable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_TABLE__NAME = eINSTANCE.getAbstractXmlTable_Name();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_TABLE__CATALOG = eINSTANCE.getAbstractXmlTable_Catalog();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ABSTRACT_XML_TABLE__SCHEMA = eINSTANCE.getAbstractXmlTable_Schema();

		/**
		 * The meta object literal for the '<em><b>Unique Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS = eINSTANCE.getAbstractXmlTable_UniqueConstraints();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping <em>Abstract Xml Type Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlTypeMapping()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_TYPE_MAPPING = eINSTANCE.getAbstractXmlTypeMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlAccessHolder <em>Xml Access Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlAccessHolder
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlAccessHolder()
		 * @generated
		 */
		public static final EClass XML_ACCESS_HOLDER = eINSTANCE.getXmlAccessHolder();

		/**
		 * The meta object literal for the '<em><b>Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ACCESS_HOLDER__ACCESS = eINSTANCE.getXmlAccessHolder_Access();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlAttributeMapping()
		 * @generated
		 */
		public static final EClass XML_ATTRIBUTE_MAPPING = eINSTANCE.getXmlAttributeMapping();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ATTRIBUTE_MAPPING__NAME = eINSTANCE.getXmlAttributeMapping_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlAssociationOverride <em>Xml Association Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlAssociationOverride
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlAssociationOverride()
		 * @generated
		 */
		public static final EClass XML_ASSOCIATION_OVERRIDE = eINSTANCE.getXmlAssociationOverride();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ASSOCIATION_OVERRIDE__NAME = eINSTANCE.getXmlAssociationOverride_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer <em>Xml Association Override Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlAssociationOverrideContainer()
		 * @generated
		 */
		public static final EClass XML_ASSOCIATION_OVERRIDE_CONTAINER = eINSTANCE.getXmlAssociationOverrideContainer();

		/**
		 * The meta object literal for the '<em><b>Association Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ASSOCIATION_OVERRIDE_CONTAINER__ASSOCIATION_OVERRIDES = eINSTANCE.getXmlAssociationOverrideContainer_AssociationOverrides();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverride <em>Xml Attribute Override</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeOverride
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlAttributeOverride()
		 * @generated
		 */
		public static final EClass XML_ATTRIBUTE_OVERRIDE = eINSTANCE.getXmlAttributeOverride();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ATTRIBUTE_OVERRIDE__COLUMN = eINSTANCE.getXmlAttributeOverride_Column();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ATTRIBUTE_OVERRIDE__NAME = eINSTANCE.getXmlAttributeOverride_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverrideContainer <em>Xml Attribute Override Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeOverrideContainer
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlAttributeOverrideContainer()
		 * @generated
		 */
		public static final EClass XML_ATTRIBUTE_OVERRIDE_CONTAINER = eINSTANCE.getXmlAttributeOverrideContainer();

		/**
		 * The meta object literal for the '<em><b>Attribute Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ATTRIBUTE_OVERRIDE_CONTAINER__ATTRIBUTE_OVERRIDES = eINSTANCE.getXmlAttributeOverrideContainer_AttributeOverrides();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.Attributes <em>Attributes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.Attributes
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAttributes()
		 * @generated
		 */
		public static final EClass ATTRIBUTES = eINSTANCE.getAttributes();

		/**
		 * The meta object literal for the '<em><b>Ids</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__IDS = eINSTANCE.getAttributes_Ids();

		/**
		 * The meta object literal for the '<em><b>Embedded Ids</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__EMBEDDED_IDS = eINSTANCE.getAttributes_EmbeddedIds();

		/**
		 * The meta object literal for the '<em><b>Basics</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__BASICS = eINSTANCE.getAttributes_Basics();

		/**
		 * The meta object literal for the '<em><b>Versions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__VERSIONS = eINSTANCE.getAttributes_Versions();

		/**
		 * The meta object literal for the '<em><b>Many To Ones</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__MANY_TO_ONES = eINSTANCE.getAttributes_ManyToOnes();

		/**
		 * The meta object literal for the '<em><b>One To Manys</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__ONE_TO_MANYS = eINSTANCE.getAttributes_OneToManys();

		/**
		 * The meta object literal for the '<em><b>One To Ones</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__ONE_TO_ONES = eINSTANCE.getAttributes_OneToOnes();

		/**
		 * The meta object literal for the '<em><b>Many To Manys</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__MANY_TO_MANYS = eINSTANCE.getAttributes_ManyToManys();

		/**
		 * The meta object literal for the '<em><b>Embeddeds</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__EMBEDDEDS = eINSTANCE.getAttributes_Embeddeds();

		/**
		 * The meta object literal for the '<em><b>Transients</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ATTRIBUTES__TRANSIENTS = eINSTANCE.getAttributes_Transients();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlBasic <em>Xml Basic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlBasic
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlBasic()
		 * @generated
		 */
		public static final EClass XML_BASIC = eINSTANCE.getXmlBasic();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_BASIC__FETCH = eINSTANCE.getXmlBasic_Fetch();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_BASIC__OPTIONAL = eINSTANCE.getXmlBasic_Optional();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.CascadeType <em>Cascade Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.CascadeType
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType()
		 * @generated
		 */
		public static final EClass CASCADE_TYPE = eINSTANCE.getCascadeType();

		/**
		 * The meta object literal for the '<em><b>Cascade All</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute CASCADE_TYPE__CASCADE_ALL = eINSTANCE.getCascadeType_CascadeAll();

		/**
		 * The meta object literal for the '<em><b>Cascade Persist</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute CASCADE_TYPE__CASCADE_PERSIST = eINSTANCE.getCascadeType_CascadePersist();

		/**
		 * The meta object literal for the '<em><b>Cascade Merge</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute CASCADE_TYPE__CASCADE_MERGE = eINSTANCE.getCascadeType_CascadeMerge();

		/**
		 * The meta object literal for the '<em><b>Cascade Remove</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute CASCADE_TYPE__CASCADE_REMOVE = eINSTANCE.getCascadeType_CascadeRemove();

		/**
		 * The meta object literal for the '<em><b>Cascade Refresh</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute CASCADE_TYPE__CASCADE_REFRESH = eINSTANCE.getCascadeType_CascadeRefresh();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlCollectionTable <em>Xml Collection Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlCollectionTable
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlCollectionTable()
		 * @generated
		 */
		public static final EClass XML_COLLECTION_TABLE = eINSTANCE.getXmlCollectionTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlColumn <em>Xml Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlColumn
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlColumn()
		 * @generated
		 */
		public static final EClass XML_COLUMN = eINSTANCE.getXmlColumn();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_COLUMN__LENGTH = eINSTANCE.getXmlColumn_Length();

		/**
		 * The meta object literal for the '<em><b>Precision</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_COLUMN__PRECISION = eINSTANCE.getXmlColumn_Precision();

		/**
		 * The meta object literal for the '<em><b>Scale</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_COLUMN__SCALE = eINSTANCE.getXmlColumn_Scale();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.ColumnMapping <em>Column Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.ColumnMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getColumnMapping()
		 * @generated
		 */
		public static final EClass COLUMN_MAPPING = eINSTANCE.getColumnMapping();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference COLUMN_MAPPING__COLUMN = eINSTANCE.getColumnMapping_Column();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.ColumnResult <em>Column Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.ColumnResult
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getColumnResult()
		 * @generated
		 */
		public static final EClass COLUMN_RESULT = eINSTANCE.getColumnResult();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute COLUMN_RESULT__NAME = eINSTANCE.getColumnResult_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping <em>Xml Convertible Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlConvertibleMapping()
		 * @generated
		 */
		public static final EClass XML_CONVERTIBLE_MAPPING = eINSTANCE.getXmlConvertibleMapping();

		/**
		 * The meta object literal for the '<em><b>Lob</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERTIBLE_MAPPING__LOB = eINSTANCE.getXmlConvertibleMapping_Lob();

		/**
		 * The meta object literal for the '<em><b>Temporal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERTIBLE_MAPPING__TEMPORAL = eINSTANCE.getXmlConvertibleMapping_Temporal();

		/**
		 * The meta object literal for the '<em><b>Enumerated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_CONVERTIBLE_MAPPING__ENUMERATED = eINSTANCE.getXmlConvertibleMapping_Enumerated();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn <em>Xml Discriminator Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlDiscriminatorColumn()
		 * @generated
		 */
		public static final EClass XML_DISCRIMINATOR_COLUMN = eINSTANCE.getXmlDiscriminatorColumn();

		/**
		 * The meta object literal for the '<em><b>Discriminator Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = eINSTANCE.getXmlDiscriminatorColumn_DiscriminatorType();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_DISCRIMINATOR_COLUMN__LENGTH = eINSTANCE.getXmlDiscriminatorColumn_Length();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlElementCollection <em>Xml Element Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlElementCollection
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlElementCollection()
		 * @generated
		 */
		public static final EClass XML_ELEMENT_COLLECTION = eINSTANCE.getXmlElementCollection();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlEmbeddable <em>Xml Embeddable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlEmbeddable
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlEmbeddable()
		 * @generated
		 */
		public static final EClass XML_EMBEDDABLE = eINSTANCE.getXmlEmbeddable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlEmbedded <em>Xml Embedded</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlEmbedded
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlEmbedded()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED = eINSTANCE.getXmlEmbedded();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlEmbeddedId
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlEmbeddedId()
		 * @generated
		 */
		public static final EClass XML_EMBEDDED_ID = eINSTANCE.getXmlEmbeddedId();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlEntity <em>Xml Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlEntity
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlEntity()
		 * @generated
		 */
		public static final EClass XML_ENTITY = eINSTANCE.getXmlEntity();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ENTITY__NAME = eINSTANCE.getXmlEntity_Name();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__TABLE = eINSTANCE.getXmlEntity_Table();

		/**
		 * The meta object literal for the '<em><b>Secondary Tables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__SECONDARY_TABLES = eINSTANCE.getXmlEntity_SecondaryTables();

		/**
		 * The meta object literal for the '<em><b>Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getXmlEntity_PrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '<em><b>Id Class</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__ID_CLASS = eINSTANCE.getXmlEntity_IdClass();

		/**
		 * The meta object literal for the '<em><b>Inheritance</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__INHERITANCE = eINSTANCE.getXmlEntity_Inheritance();

		/**
		 * The meta object literal for the '<em><b>Discriminator Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ENTITY__DISCRIMINATOR_VALUE = eINSTANCE.getXmlEntity_DiscriminatorValue();

		/**
		 * The meta object literal for the '<em><b>Discriminator Column</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__DISCRIMINATOR_COLUMN = eINSTANCE.getXmlEntity_DiscriminatorColumn();

		/**
		 * The meta object literal for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__SQL_RESULT_SET_MAPPINGS = eINSTANCE.getXmlEntity_SqlResultSetMappings();

		/**
		 * The meta object literal for the '<em><b>Exclude Default Listeners</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ENTITY__EXCLUDE_DEFAULT_LISTENERS = eINSTANCE.getXmlEntity_ExcludeDefaultListeners();

		/**
		 * The meta object literal for the '<em><b>Exclude Superclass Listeners</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ENTITY__EXCLUDE_SUPERCLASS_LISTENERS = eINSTANCE.getXmlEntity_ExcludeSuperclassListeners();

		/**
		 * The meta object literal for the '<em><b>Entity Listeners</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY__ENTITY_LISTENERS = eINSTANCE.getXmlEntity_EntityListeners();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.EntityListener <em>Entity Listener</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.EntityListener
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityListener()
		 * @generated
		 */
		public static final EClass ENTITY_LISTENER = eINSTANCE.getEntityListener();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_LISTENER__CLASS_NAME = eINSTANCE.getEntityListener_ClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.EntityListeners <em>Entity Listeners</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.EntityListeners
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityListeners()
		 * @generated
		 */
		public static final EClass ENTITY_LISTENERS = eINSTANCE.getEntityListeners();

		/**
		 * The meta object literal for the '<em><b>Entity Listeners</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_LISTENERS__ENTITY_LISTENERS = eINSTANCE.getEntityListeners_EntityListeners();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlEntityMappings()
		 * @generated
		 */
		public static final EClass XML_ENTITY_MAPPINGS = eINSTANCE.getXmlEntityMappings();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ENTITY_MAPPINGS__DESCRIPTION = eINSTANCE.getXmlEntityMappings_Description();

		/**
		 * The meta object literal for the '<em><b>Persistence Unit Metadata</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA = eINSTANCE.getXmlEntityMappings_PersistenceUnitMetadata();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ENTITY_MAPPINGS__PACKAGE = eINSTANCE.getXmlEntityMappings_Package();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ENTITY_MAPPINGS__SCHEMA = eINSTANCE.getXmlEntityMappings_Schema();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ENTITY_MAPPINGS__CATALOG = eINSTANCE.getXmlEntityMappings_Catalog();

		/**
		 * The meta object literal for the '<em><b>Sequence Generators</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS__SEQUENCE_GENERATORS = eINSTANCE.getXmlEntityMappings_SequenceGenerators();

		/**
		 * The meta object literal for the '<em><b>Table Generators</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS__TABLE_GENERATORS = eINSTANCE.getXmlEntityMappings_TableGenerators();

		/**
		 * The meta object literal for the '<em><b>Sql Result Set Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS = eINSTANCE.getXmlEntityMappings_SqlResultSetMappings();

		/**
		 * The meta object literal for the '<em><b>Mapped Superclasses</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS__MAPPED_SUPERCLASSES = eINSTANCE.getXmlEntityMappings_MappedSuperclasses();

		/**
		 * The meta object literal for the '<em><b>Entities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS__ENTITIES = eINSTANCE.getXmlEntityMappings_Entities();

		/**
		 * The meta object literal for the '<em><b>Embeddables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ENTITY_MAPPINGS__EMBEDDABLES = eINSTANCE.getXmlEntityMappings_Embeddables();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.EntityResult <em>Entity Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.EntityResult
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityResult()
		 * @generated
		 */
		public static final EClass ENTITY_RESULT = eINSTANCE.getEntityResult();

		/**
		 * The meta object literal for the '<em><b>Discriminator Column</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_RESULT__DISCRIMINATOR_COLUMN = eINSTANCE.getEntityResult_DiscriminatorColumn();

		/**
		 * The meta object literal for the '<em><b>Entity Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ENTITY_RESULT__ENTITY_CLASS = eINSTANCE.getEntityResult_EntityClass();

		/**
		 * The meta object literal for the '<em><b>Field Results</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference ENTITY_RESULT__FIELD_RESULTS = eINSTANCE.getEntityResult_FieldResults();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.EventMethod <em>Event Method</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.EventMethod
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEventMethod()
		 * @generated
		 */
		public static final EClass EVENT_METHOD = eINSTANCE.getEventMethod();

		/**
		 * The meta object literal for the '<em><b>Method Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EVENT_METHOD__METHOD_NAME = eINSTANCE.getEventMethod_MethodName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer <em>Xml Event Method Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlEventMethodContainer
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlEventMethodContainer()
		 * @generated
		 */
		public static final EClass XML_EVENT_METHOD_CONTAINER = eINSTANCE.getXmlEventMethodContainer();

		/**
		 * The meta object literal for the '<em><b>Pre Persist</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EVENT_METHOD_CONTAINER__PRE_PERSIST = eINSTANCE.getXmlEventMethodContainer_PrePersist();

		/**
		 * The meta object literal for the '<em><b>Post Persist</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EVENT_METHOD_CONTAINER__POST_PERSIST = eINSTANCE.getXmlEventMethodContainer_PostPersist();

		/**
		 * The meta object literal for the '<em><b>Pre Remove</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EVENT_METHOD_CONTAINER__PRE_REMOVE = eINSTANCE.getXmlEventMethodContainer_PreRemove();

		/**
		 * The meta object literal for the '<em><b>Post Remove</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EVENT_METHOD_CONTAINER__POST_REMOVE = eINSTANCE.getXmlEventMethodContainer_PostRemove();

		/**
		 * The meta object literal for the '<em><b>Pre Update</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EVENT_METHOD_CONTAINER__PRE_UPDATE = eINSTANCE.getXmlEventMethodContainer_PreUpdate();

		/**
		 * The meta object literal for the '<em><b>Post Update</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EVENT_METHOD_CONTAINER__POST_UPDATE = eINSTANCE.getXmlEventMethodContainer_PostUpdate();

		/**
		 * The meta object literal for the '<em><b>Post Load</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_EVENT_METHOD_CONTAINER__POST_LOAD = eINSTANCE.getXmlEventMethodContainer_PostLoad();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.FieldResult <em>Field Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.FieldResult
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getFieldResult()
		 * @generated
		 */
		public static final EClass FIELD_RESULT = eINSTANCE.getFieldResult();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute FIELD_RESULT__NAME = eINSTANCE.getFieldResult_Name();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute FIELD_RESULT__COLUMN = eINSTANCE.getFieldResult_Column();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlGeneratedValue <em>Xml Generated Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlGeneratedValue
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlGeneratedValue()
		 * @generated
		 */
		public static final EClass XML_GENERATED_VALUE = eINSTANCE.getXmlGeneratedValue();

		/**
		 * The meta object literal for the '<em><b>Generator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_GENERATED_VALUE__GENERATOR = eINSTANCE.getXmlGeneratedValue_Generator();

		/**
		 * The meta object literal for the '<em><b>Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_GENERATED_VALUE__STRATEGY = eINSTANCE.getXmlGeneratedValue_Strategy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlGenerator <em>Xml Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlGenerator
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlGenerator()
		 * @generated
		 */
		public static final EClass XML_GENERATOR = eINSTANCE.getXmlGenerator();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_GENERATOR__NAME = eINSTANCE.getXmlGenerator_Name();

		/**
		 * The meta object literal for the '<em><b>Initial Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_GENERATOR__INITIAL_VALUE = eINSTANCE.getXmlGenerator_InitialValue();

		/**
		 * The meta object literal for the '<em><b>Allocation Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_GENERATOR__ALLOCATION_SIZE = eINSTANCE.getXmlGenerator_AllocationSize();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer <em>Xml Generator Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlGeneratorContainer()
		 * @generated
		 */
		public static final EClass XML_GENERATOR_CONTAINER = eINSTANCE.getXmlGeneratorContainer();

		/**
		 * The meta object literal for the '<em><b>Sequence Generator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_GENERATOR_CONTAINER__SEQUENCE_GENERATOR = eINSTANCE.getXmlGeneratorContainer_SequenceGenerator();

		/**
		 * The meta object literal for the '<em><b>Table Generator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_GENERATOR_CONTAINER__TABLE_GENERATOR = eINSTANCE.getXmlGeneratorContainer_TableGenerator();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlId <em>Xml Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlId
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlId()
		 * @generated
		 */
		public static final EClass XML_ID = eINSTANCE.getXmlId();

		/**
		 * The meta object literal for the '<em><b>Generated Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ID__GENERATED_VALUE = eINSTANCE.getXmlId_GeneratedValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlIdClass <em>Xml Id Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlIdClass
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlIdClass()
		 * @generated
		 */
		public static final EClass XML_ID_CLASS = eINSTANCE.getXmlIdClass();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ID_CLASS__CLASS_NAME = eINSTANCE.getXmlIdClass_ClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.Inheritance <em>Inheritance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.Inheritance
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getInheritance()
		 * @generated
		 */
		public static final EClass INHERITANCE = eINSTANCE.getInheritance();

		/**
		 * The meta object literal for the '<em><b>Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute INHERITANCE__STRATEGY = eINSTANCE.getInheritance_Strategy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn <em>Xml Join Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlJoinColumn
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinColumn()
		 * @generated
		 */
		public static final EClass XML_JOIN_COLUMN = eINSTANCE.getXmlJoinColumn();

		/**
		 * The meta object literal for the '<em><b>Referenced Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_JOIN_COLUMN__REFERENCED_COLUMN_NAME = eINSTANCE.getXmlJoinColumn_ReferencedColumnName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping <em>Xml Join Columns Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinColumnsMapping()
		 * @generated
		 */
		public static final EClass XML_JOIN_COLUMNS_MAPPING = eINSTANCE.getXmlJoinColumnsMapping();

		/**
		 * The meta object literal for the '<em><b>Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_JOIN_COLUMNS_MAPPING__JOIN_COLUMNS = eINSTANCE.getXmlJoinColumnsMapping_JoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlJoinTable <em>Xml Join Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlJoinTable
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinTable()
		 * @generated
		 */
		public static final EClass XML_JOIN_TABLE = eINSTANCE.getXmlJoinTable();

		/**
		 * The meta object literal for the '<em><b>Inverse Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS = eINSTANCE.getXmlJoinTable_InverseJoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping <em>Xml Join Table Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinTableMapping()
		 * @generated
		 */
		public static final EClass XML_JOIN_TABLE_MAPPING = eINSTANCE.getXmlJoinTableMapping();

		/**
		 * The meta object literal for the '<em><b>Join Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_JOIN_TABLE_MAPPING__JOIN_TABLE = eINSTANCE.getXmlJoinTableMapping_JoinTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.Lob <em>Lob</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.Lob
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getLob()
		 * @generated
		 */
		public static final EClass LOB = eINSTANCE.getLob();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlManyToMany
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlManyToMany()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_MANY = eINSTANCE.getXmlManyToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlManyToOne
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlManyToOne()
		 * @generated
		 */
		public static final EClass XML_MANY_TO_ONE = eINSTANCE.getXmlManyToOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.MapKey <em>Map Key</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.MapKey
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getMapKey()
		 * @generated
		 */
		public static final EClass MAP_KEY = eINSTANCE.getMapKey();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute MAP_KEY__NAME = eINSTANCE.getMapKey_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlMapKeyClass <em>Xml Map Key Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlMapKeyClass
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMapKeyClass()
		 * @generated
		 */
		public static final EClass XML_MAP_KEY_CLASS = eINSTANCE.getXmlMapKeyClass();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlMappedByMapping <em>Xml Mapped By Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlMappedByMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMappedByMapping()
		 * @generated
		 */
		public static final EClass XML_MAPPED_BY_MAPPING = eINSTANCE.getXmlMappedByMapping();

		/**
		 * The meta object literal for the '<em><b>Mapped By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MAPPED_BY_MAPPING__MAPPED_BY = eINSTANCE.getXmlMappedByMapping_MappedBy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMappedSuperclass()
		 * @generated
		 */
		public static final EClass XML_MAPPED_SUPERCLASS = eINSTANCE.getXmlMappedSuperclass();

		/**
		 * The meta object literal for the '<em><b>Id Class</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS__ID_CLASS = eINSTANCE.getXmlMappedSuperclass_IdClass();

		/**
		 * The meta object literal for the '<em><b>Exclude Default Listeners</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS = eINSTANCE.getXmlMappedSuperclass_ExcludeDefaultListeners();

		/**
		 * The meta object literal for the '<em><b>Exclude Superclass Listeners</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS = eINSTANCE.getXmlMappedSuperclass_ExcludeSuperclassListeners();

		/**
		 * The meta object literal for the '<em><b>Entity Listeners</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS = eINSTANCE.getXmlMappedSuperclass_EntityListeners();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery <em>Xml Named Native Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlNamedNativeQuery()
		 * @generated
		 */
		public static final EClass XML_NAMED_NATIVE_QUERY = eINSTANCE.getXmlNamedNativeQuery();

		/**
		 * The meta object literal for the '<em><b>Result Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_NATIVE_QUERY__RESULT_CLASS = eINSTANCE.getXmlNamedNativeQuery_ResultClass();

		/**
		 * The meta object literal for the '<em><b>Result Set Mapping</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING = eINSTANCE.getXmlNamedNativeQuery_ResultSetMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlNamedQuery <em>Xml Named Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlNamedQuery
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlNamedQuery()
		 * @generated
		 */
		public static final EClass XML_NAMED_QUERY = eINSTANCE.getXmlNamedQuery();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping <em>Xml Null Attribute Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlNullAttributeMapping()
		 * @generated
		 */
		public static final EClass XML_NULL_ATTRIBUTE_MAPPING = eINSTANCE.getXmlNullAttributeMapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlOneToMany
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOneToMany()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_MANY = eINSTANCE.getXmlOneToMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlOneToOne
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOneToOne()
		 * @generated
		 */
		public static final EClass XML_ONE_TO_ONE = eINSTANCE.getXmlOneToOne();

		/**
		 * The meta object literal for the '<em><b>Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getXmlOneToOne_PrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlOrderColumn <em>Xml Order Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlOrderColumn
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOrderColumn()
		 * @generated
		 */
		public static final EClass XML_ORDER_COLUMN = eINSTANCE.getXmlOrderColumn();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults <em>Xml Persistence Unit Defaults</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlPersistenceUnitDefaults()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE_UNIT_DEFAULTS = eINSTANCE.getXmlPersistenceUnitDefaults();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA = eINSTANCE.getXmlPersistenceUnitDefaults_Schema();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG = eINSTANCE.getXmlPersistenceUnitDefaults_Catalog();

		/**
		 * The meta object literal for the '<em><b>Cascade Persist</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST = eINSTANCE.getXmlPersistenceUnitDefaults_CascadePersist();

		/**
		 * The meta object literal for the '<em><b>Entity Listeners</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS = eINSTANCE.getXmlPersistenceUnitDefaults_EntityListeners();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata <em>Xml Persistence Unit Metadata</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlPersistenceUnitMetadata()
		 * @generated
		 */
		public static final EClass XML_PERSISTENCE_UNIT_METADATA = eINSTANCE.getXmlPersistenceUnitMetadata();

		/**
		 * The meta object literal for the '<em><b>Xml Mapping Metadata Complete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE = eINSTANCE.getXmlPersistenceUnitMetadata_XmlMappingMetadataComplete();

		/**
		 * The meta object literal for the '<em><b>Persistence Unit Defaults</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS = eINSTANCE.getXmlPersistenceUnitMetadata_PersistenceUnitDefaults();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlReferenceTable <em>Abstract Xml Reference Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlReferenceTable
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlReferenceTable()
		 * @generated
		 */
		public static final EClass ABSTRACT_XML_REFERENCE_TABLE = eINSTANCE.getAbstractXmlReferenceTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlReferenceTable <em>Xml Reference Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlReferenceTable
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlReferenceTable()
		 * @generated
		 */
		public static final EClass XML_REFERENCE_TABLE = eINSTANCE.getXmlReferenceTable();

		/**
		 * The meta object literal for the '<em><b>Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_REFERENCE_TABLE__JOIN_COLUMNS = eINSTANCE.getXmlReferenceTable_JoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.PostLoad <em>Post Load</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.PostLoad
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPostLoad()
		 * @generated
		 */
		public static final EClass POST_LOAD = eINSTANCE.getPostLoad();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.PostPersist <em>Post Persist</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.PostPersist
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPostPersist()
		 * @generated
		 */
		public static final EClass POST_PERSIST = eINSTANCE.getPostPersist();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.PostRemove <em>Post Remove</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.PostRemove
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPostRemove()
		 * @generated
		 */
		public static final EClass POST_REMOVE = eINSTANCE.getPostRemove();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.PostUpdate <em>Post Update</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.PostUpdate
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPostUpdate()
		 * @generated
		 */
		public static final EClass POST_UPDATE = eINSTANCE.getPostUpdate();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.PrePersist <em>Pre Persist</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.PrePersist
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPrePersist()
		 * @generated
		 */
		public static final EClass PRE_PERSIST = eINSTANCE.getPrePersist();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.PreRemove <em>Pre Remove</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.PreRemove
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPreRemove()
		 * @generated
		 */
		public static final EClass PRE_REMOVE = eINSTANCE.getPreRemove();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.PreUpdate <em>Pre Update</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.PreUpdate
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getPreUpdate()
		 * @generated
		 */
		public static final EClass PRE_UPDATE = eINSTANCE.getPreUpdate();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn <em>Xml Primary Key Join Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlPrimaryKeyJoinColumn()
		 * @generated
		 */
		public static final EClass XML_PRIMARY_KEY_JOIN_COLUMN = eINSTANCE.getXmlPrimaryKeyJoinColumn();

		/**
		 * The meta object literal for the '<em><b>Referenced Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = eINSTANCE.getXmlPrimaryKeyJoinColumn_ReferencedColumnName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlQuery <em>Xml Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlQuery
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlQuery()
		 * @generated
		 */
		public static final EClass XML_QUERY = eINSTANCE.getXmlQuery();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_QUERY__NAME = eINSTANCE.getXmlQuery_Name();

		/**
		 * The meta object literal for the '<em><b>Query</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_QUERY__QUERY = eINSTANCE.getXmlQuery_Query();

		/**
		 * The meta object literal for the '<em><b>Hints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_QUERY__HINTS = eINSTANCE.getXmlQuery_Hints();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlQueryContainer <em>Xml Query Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlQueryContainer
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlQueryContainer()
		 * @generated
		 */
		public static final EClass XML_QUERY_CONTAINER = eINSTANCE.getXmlQueryContainer();

		/**
		 * The meta object literal for the '<em><b>Named Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_QUERY_CONTAINER__NAMED_QUERIES = eINSTANCE.getXmlQueryContainer_NamedQueries();

		/**
		 * The meta object literal for the '<em><b>Named Native Queries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_QUERY_CONTAINER__NAMED_NATIVE_QUERIES = eINSTANCE.getXmlQueryContainer_NamedNativeQueries();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlQueryHint <em>Xml Query Hint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlQueryHint
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlQueryHint()
		 * @generated
		 */
		public static final EClass XML_QUERY_HINT = eINSTANCE.getXmlQueryHint();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_QUERY_HINT__NAME = eINSTANCE.getXmlQueryHint_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_QUERY_HINT__VALUE = eINSTANCE.getXmlQueryHint_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlSecondaryTable <em>Xml Secondary Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlSecondaryTable
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlSecondaryTable()
		 * @generated
		 */
		public static final EClass XML_SECONDARY_TABLE = eINSTANCE.getXmlSecondaryTable();

		/**
		 * The meta object literal for the '<em><b>Primary Key Join Columns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS = eINSTANCE.getXmlSecondaryTable_PrimaryKeyJoinColumns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator <em>Xml Sequence Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlSequenceGenerator()
		 * @generated
		 */
		public static final EClass XML_SEQUENCE_GENERATOR = eINSTANCE.getXmlSequenceGenerator();

		/**
		 * The meta object literal for the '<em><b>Sequence Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_SEQUENCE_GENERATOR__SEQUENCE_NAME = eINSTANCE.getXmlSequenceGenerator_SequenceName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.SqlResultSetMapping <em>Sql Result Set Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.SqlResultSetMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getSqlResultSetMapping()
		 * @generated
		 */
		public static final EClass SQL_RESULT_SET_MAPPING = eINSTANCE.getSqlResultSetMapping();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute SQL_RESULT_SET_MAPPING__NAME = eINSTANCE.getSqlResultSetMapping_Name();

		/**
		 * The meta object literal for the '<em><b>Entity Results</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference SQL_RESULT_SET_MAPPING__ENTITY_RESULTS = eINSTANCE.getSqlResultSetMapping_EntityResults();

		/**
		 * The meta object literal for the '<em><b>Column Results</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference SQL_RESULT_SET_MAPPING__COLUMN_RESULTS = eINSTANCE.getSqlResultSetMapping_ColumnResults();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlTable <em>Xml Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlTable
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTable()
		 * @generated
		 */
		public static final EClass XML_TABLE = eINSTANCE.getXmlTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlTableGenerator <em>Xml Table Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlTableGenerator
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTableGenerator()
		 * @generated
		 */
		public static final EClass XML_TABLE_GENERATOR = eINSTANCE.getXmlTableGenerator();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TABLE_GENERATOR__TABLE = eINSTANCE.getXmlTableGenerator_Table();

		/**
		 * The meta object literal for the '<em><b>Catalog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TABLE_GENERATOR__CATALOG = eINSTANCE.getXmlTableGenerator_Catalog();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TABLE_GENERATOR__SCHEMA = eINSTANCE.getXmlTableGenerator_Schema();

		/**
		 * The meta object literal for the '<em><b>Pk Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TABLE_GENERATOR__PK_COLUMN_NAME = eINSTANCE.getXmlTableGenerator_PkColumnName();

		/**
		 * The meta object literal for the '<em><b>Value Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TABLE_GENERATOR__VALUE_COLUMN_NAME = eINSTANCE.getXmlTableGenerator_ValueColumnName();

		/**
		 * The meta object literal for the '<em><b>Pk Column Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TABLE_GENERATOR__PK_COLUMN_VALUE = eINSTANCE.getXmlTableGenerator_PkColumnValue();

		/**
		 * The meta object literal for the '<em><b>Unique Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_TABLE_GENERATOR__UNIQUE_CONSTRAINTS = eINSTANCE.getXmlTableGenerator_UniqueConstraints();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlTransient <em>Xml Transient</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlTransient
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTransient()
		 * @generated
		 */
		public static final EClass XML_TRANSIENT = eINSTANCE.getXmlTransient();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping <em>Xml Type Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlTypeMapping
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTypeMapping()
		 * @generated
		 */
		public static final EClass XML_TYPE_MAPPING = eINSTANCE.getXmlTypeMapping();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TYPE_MAPPING__CLASS_NAME = eINSTANCE.getXmlTypeMapping_ClassName();

		/**
		 * The meta object literal for the '<em><b>Metadata Complete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TYPE_MAPPING__METADATA_COMPLETE = eINSTANCE.getXmlTypeMapping_MetadataComplete();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_TYPE_MAPPING__DESCRIPTION = eINSTANCE.getXmlTypeMapping_Description();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference XML_TYPE_MAPPING__ATTRIBUTES = eINSTANCE.getXmlTypeMapping_Attributes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint <em>Xml Unique Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlUniqueConstraint()
		 * @generated
		 */
		public static final EClass XML_UNIQUE_CONSTRAINT = eINSTANCE.getXmlUniqueConstraint();

		/**
		 * The meta object literal for the '<em><b>Column Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_UNIQUE_CONSTRAINT__COLUMN_NAMES = eINSTANCE.getXmlUniqueConstraint_ColumnNames();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlVersion <em>Xml Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlVersion
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlVersion()
		 * @generated
		 */
		public static final EClass XML_VERSION = eINSTANCE.getXmlVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.XmlOrderable <em>Xml Orderable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.XmlOrderable
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOrderable()
		 * @generated
		 */
		public static final EClass XML_ORDERABLE = eINSTANCE.getXmlOrderable();

		/**
		 * The meta object literal for the '<em><b>Order By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute XML_ORDERABLE__ORDER_BY = eINSTANCE.getXmlOrderable_OrderBy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.AccessType <em>Access Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.AccessType
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAccessType()
		 * @generated
		 */
		public static final EEnum ACCESS_TYPE = eINSTANCE.getAccessType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.DiscriminatorType <em>Discriminator Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.DiscriminatorType
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getDiscriminatorType()
		 * @generated
		 */
		public static final EEnum DISCRIMINATOR_TYPE = eINSTANCE.getDiscriminatorType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.EnumType <em>Enum Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.EnumType
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEnumType()
		 * @generated
		 */
		public static final EEnum ENUM_TYPE = eINSTANCE.getEnumType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.FetchType <em>Fetch Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.FetchType
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getFetchType()
		 * @generated
		 */
		public static final EEnum FETCH_TYPE = eINSTANCE.getFetchType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.GenerationType <em>Generation Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.GenerationType
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getGenerationType()
		 * @generated
		 */
		public static final EEnum GENERATION_TYPE = eINSTANCE.getGenerationType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.InheritanceType <em>Inheritance Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.InheritanceType
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getInheritanceType()
		 * @generated
		 */
		public static final EEnum INHERITANCE_TYPE = eINSTANCE.getInheritanceType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.core.resource.orm.TemporalType <em>Temporal Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.core.resource.orm.TemporalType
		 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getTemporalType()
		 * @generated
		 */
		public static final EEnum TEMPORAL_TYPE = eINSTANCE.getTemporalType();

	}

} //OrmPackage
