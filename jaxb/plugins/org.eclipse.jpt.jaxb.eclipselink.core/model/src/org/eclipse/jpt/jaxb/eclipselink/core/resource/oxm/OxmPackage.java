/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import java.util.List;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.jpt.common.core.resource.xml.CommonPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.util.OxmValidator;

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
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmFactory
 * @model kind="package"
 * @generated
 */
public class OxmPackage extends EPackageImpl
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "oxm";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "jpt.eclipselink.oxm.xmi";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final OxmPackage eINSTANCE = org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping <em>EAbstract Type Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractTypeMapping()
	 * @generated
	 */
	public static final int EABSTRACT_TYPE_MAPPING = 0;

	/**
	 * The feature id for the '<em><b>Xml Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EABSTRACT_TYPE_MAPPING__XML_TRANSIENT = 0;

	/**
	 * The feature id for the '<em><b>Xml Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EABSTRACT_TYPE_MAPPING__XML_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Xml Root Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Xml See Also</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EABSTRACT_TYPE_MAPPING__XML_SEE_ALSO = 3;

	/**
	 * The number of structural features of the '<em>EAbstract Type Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EABSTRACT_TYPE_MAPPING_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy <em>EAbstract Xml Null Policy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractXmlNullPolicy()
	 * @generated
	 */
	public static final int EABSTRACT_XML_NULL_POLICY = 1;

	/**
	 * The feature id for the '<em><b>Xsi Nil Represents Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EABSTRACT_XML_NULL_POLICY__XSI_NIL_REPRESENTS_NULL = 0;

	/**
	 * The feature id for the '<em><b>Empty Node Represents Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EABSTRACT_XML_NULL_POLICY__EMPTY_NODE_REPRESENTS_NULL = 1;

	/**
	 * The feature id for the '<em><b>Null Representation For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EABSTRACT_XML_NULL_POLICY__NULL_REPRESENTATION_FOR_XML = 2;

	/**
	 * The number of structural features of the '<em>EAbstract Xml Null Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EABSTRACT_XML_NULL_POLICY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer <em>EAbstract Xml Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractXmlTransformer()
	 * @generated
	 */
	public static final int EABSTRACT_XML_TRANSFORMER = 2;

	/**
	 * The feature id for the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EABSTRACT_XML_TRANSFORMER__METHOD = 0;

	/**
	 * The feature id for the '<em><b>Transformer Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EABSTRACT_XML_TRANSFORMER__TRANSFORMER_CLASS = 1;

	/**
	 * The number of structural features of the '<em>EAbstract Xml Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EABSTRACT_XML_TRANSFORMER_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAccessibleJavaAttribute <em>EAccessible Java Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAccessibleJavaAttribute
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAccessibleJavaAttribute()
	 * @generated
	 */
	public static final int EACCESSIBLE_JAVA_ATTRIBUTE = 3;

	/**
	 * The feature id for the '<em><b>Xml Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EACCESSIBLE_JAVA_ATTRIBUTE__XML_ACCESS_METHODS = 0;

	/**
	 * The number of structural features of the '<em>EAccessible Java Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EACCESSIBLE_JAVA_ATTRIBUTE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAdaptableJavaAttribute <em>EAdaptable Java Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAdaptableJavaAttribute
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAdaptableJavaAttribute()
	 * @generated
	 */
	public static final int EADAPTABLE_JAVA_ATTRIBUTE = 4;

	/**
	 * The feature id for the '<em><b>Xml Java Type Adapter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EADAPTABLE_JAVA_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER = 0;

	/**
	 * The number of structural features of the '<em>EAdaptable Java Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EADAPTABLE_JAVA_ATTRIBUTE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EContainerJavaAttribute <em>EContainer Java Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EContainerJavaAttribute
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEContainerJavaAttribute()
	 * @generated
	 */
	public static final int ECONTAINER_JAVA_ATTRIBUTE = 5;

	/**
	 * The feature id for the '<em><b>Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ECONTAINER_JAVA_ATTRIBUTE__CONTAINER_TYPE = 0;

	/**
	 * The number of structural features of the '<em>EContainer Java Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ECONTAINER_JAVA_ATTRIBUTE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute <em>EJava Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaAttribute()
	 * @generated
	 */
	public static final int EJAVA_ATTRIBUTE = 6;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE = 1;

	/**
	 * The number of structural features of the '<em>EJava Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_ATTRIBUTE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EPropertyHolder <em>EProperty Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EPropertyHolder
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEPropertyHolder()
	 * @generated
	 */
	public static final int EPROPERTY_HOLDER = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType <em>EJava Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType()
	 * @generated
	 */
	public static final int EJAVA_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Xml Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_TRANSIENT = EABSTRACT_TYPE_MAPPING__XML_TRANSIENT;

	/**
	 * The feature id for the '<em><b>Xml Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_TYPE = EABSTRACT_TYPE_MAPPING__XML_TYPE;

	/**
	 * The feature id for the '<em><b>Xml Root Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_ROOT_ELEMENT = EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT;

	/**
	 * The feature id for the '<em><b>Xml See Also</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_SEE_ALSO = EABSTRACT_TYPE_MAPPING__XML_SEE_ALSO;

	/**
	 * The feature id for the '<em><b>Xml Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_PROPERTIES = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__NAME = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Super Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__SUPER_TYPE = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Xml Accessor Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_ACCESSOR_ORDER = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_ACCESSOR_TYPE = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Xml Customizer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_CUSTOMIZER = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Xml Discriminator Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_DISCRIMINATOR_NODE = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Xml Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_DISCRIMINATOR_VALUE = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Xml Inline Binary Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_INLINE_BINARY_DATA = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Xml Name Transformer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_NAME_TRANSFORMER = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Xml Virtual Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_VIRTUAL_ACCESS_METHODS = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Xml Java Type Adapter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_JAVA_TYPE_ADAPTER = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Xml Class Extractor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__XML_CLASS_EXTRACTOR = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Java Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE__JAVA_ATTRIBUTES = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 13;

	/**
	 * The number of structural features of the '<em>EJava Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EJAVA_TYPE_FEATURE_COUNT = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Xml Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EPROPERTY_HOLDER__XML_PROPERTIES = 0;

	/**
	 * The number of structural features of the '<em>EProperty Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EPROPERTY_HOLDER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EReadWriteJavaAttribute <em>ERead Write Java Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EReadWriteJavaAttribute
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEReadWriteJavaAttribute()
	 * @generated
	 */
	public static final int EREAD_WRITE_JAVA_ATTRIBUTE = 9;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EREAD_WRITE_JAVA_ATTRIBUTE__READ_ONLY = 0;

	/**
	 * The feature id for the '<em><b>Write Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EREAD_WRITE_JAVA_ATTRIBUTE__WRITE_ONLY = 1;

	/**
	 * The number of structural features of the '<em>ERead Write Java Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EREAD_WRITE_JAVA_ATTRIBUTE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.ETypedJavaAttribute <em>ETyped Java Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.ETypedJavaAttribute
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getETypedJavaAttribute()
	 * @generated
	 */
	public static final int ETYPED_JAVA_ATTRIBUTE = 10;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ETYPED_JAVA_ATTRIBUTE__TYPE = 0;

	/**
	 * The number of structural features of the '<em>ETyped Java Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ETYPED_JAVA_ATTRIBUTE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessMethods <em>EXml Access Methods</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessMethods
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAccessMethods()
	 * @generated
	 */
	public static final int EXML_ACCESS_METHODS = 11;

	/**
	 * The feature id for the '<em><b>Get Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ACCESS_METHODS__GET_METHOD = 0;

	/**
	 * The feature id for the '<em><b>Set Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ACCESS_METHODS__SET_METHOD = 1;

	/**
	 * The number of structural features of the '<em>EXml Access Methods</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ACCESS_METHODS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute <em>EXml Any Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAnyAttribute()
	 * @generated
	 */
	public static final int EXML_ANY_ATTRIBUTE = 12;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ATTRIBUTE__JAVA_ATTRIBUTE = EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ATTRIBUTE__XML_ACCESSOR_TYPE = EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Xml Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ATTRIBUTE__XML_ACCESS_METHODS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ATTRIBUTE__CONTAINER_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ATTRIBUTE__READ_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Write Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ATTRIBUTE__WRITE_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Xml Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ATTRIBUTE__XML_PROPERTIES = EJAVA_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Xml Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ATTRIBUTE__XML_PATH = EJAVA_ATTRIBUTE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>EXml Any Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ATTRIBUTE_FEATURE_COUNT = EJAVA_ATTRIBUTE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement <em>EXml Any Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAnyElement()
	 * @generated
	 */
	public static final int EXML_ANY_ELEMENT = 13;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT__JAVA_ATTRIBUTE = EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT__XML_ACCESSOR_TYPE = EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Xml Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT__XML_ACCESS_METHODS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Xml Java Type Adapter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT__XML_JAVA_TYPE_ADAPTER = EJAVA_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT__CONTAINER_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT__READ_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Write Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT__WRITE_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Xml Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT__XML_PROPERTIES = EJAVA_ATTRIBUTE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Xml Mixed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT__XML_MIXED = EJAVA_ATTRIBUTE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Lax</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT__LAX = EJAVA_ATTRIBUTE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Dom Handler</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT__DOM_HANDLER = EJAVA_ATTRIBUTE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Xml Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT__XML_PATH = EJAVA_ATTRIBUTE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Xml Element Refs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT__XML_ELEMENT_REFS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>EXml Any Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ANY_ELEMENT_FEATURE_COUNT = EJAVA_ATTRIBUTE_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute <em>EXml Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute()
	 * @generated
	 */
	public static final int EXML_ATTRIBUTE = 14;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__JAVA_ATTRIBUTE = EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_ACCESSOR_TYPE = EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Xml Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_ACCESS_METHODS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Xml Java Type Adapter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER = EJAVA_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__CONTAINER_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__READ_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Write Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__WRITE_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Xml Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_PROPERTIES = EJAVA_ATTRIBUTE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__NAME = EJAVA_ATTRIBUTE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__NAMESPACE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__REQUIRED = EJAVA_ATTRIBUTE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Xml Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_ID = EJAVA_ATTRIBUTE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Xml Id Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_ID_REF = EJAVA_ATTRIBUTE_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Xml Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_KEY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Xml List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_LIST = EJAVA_ATTRIBUTE_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Xml Inline Binary Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_INLINE_BINARY_DATA = EJAVA_ATTRIBUTE_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Xml Attachment Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_ATTACHMENT_REF = EJAVA_ATTRIBUTE_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Xml Mime Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_MIME_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Xml Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_PATH = EJAVA_ATTRIBUTE_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Xml Abstract Null Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_ABSTRACT_NULL_POLICY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Xml Schema Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE__XML_SCHEMA_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 19;

	/**
	 * The number of structural features of the '<em>EXml Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ATTRIBUTE_FEATURE_COUNT = EJAVA_ATTRIBUTE_FEATURE_COUNT + 20;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings <em>EXml Bindings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings()
	 * @generated
	 */
	public static final int EXML_BINDINGS = 15;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__VERSION = CommonPackage.EROOT_OBJECT_IMPL__VERSION;

	/**
	 * The feature id for the '<em><b>Schema Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__SCHEMA_LOCATION = CommonPackage.EROOT_OBJECT_IMPL__SCHEMA_LOCATION;

	/**
	 * The feature id for the '<em><b>Implied Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__IMPLIED_VERSION = CommonPackage.EROOT_OBJECT_IMPL__IMPLIED_VERSION;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__XML_ACCESSOR_TYPE = CommonPackage.EROOT_OBJECT_IMPL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Xml Accessor Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__XML_ACCESSOR_ORDER = CommonPackage.EROOT_OBJECT_IMPL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Xml Mapping Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__XML_MAPPING_METADATA_COMPLETE = CommonPackage.EROOT_OBJECT_IMPL_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__PACKAGE_NAME = CommonPackage.EROOT_OBJECT_IMPL_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Xml Name Transformer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__XML_NAME_TRANSFORMER = CommonPackage.EROOT_OBJECT_IMPL_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Xml Schema</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__XML_SCHEMA = CommonPackage.EROOT_OBJECT_IMPL_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Xml Schema Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__XML_SCHEMA_TYPE = CommonPackage.EROOT_OBJECT_IMPL_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Xml Schema Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__XML_SCHEMA_TYPES = CommonPackage.EROOT_OBJECT_IMPL_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Xml Java Type Adapters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__XML_JAVA_TYPE_ADAPTERS = CommonPackage.EROOT_OBJECT_IMPL_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Xml Registries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__XML_REGISTRIES = CommonPackage.EROOT_OBJECT_IMPL_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Xml Enums</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__XML_ENUMS = CommonPackage.EROOT_OBJECT_IMPL_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Java Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS__JAVA_TYPES = CommonPackage.EROOT_OBJECT_IMPL_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>EXml Bindings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_BINDINGS_FEATURE_COUNT = CommonPackage.EROOT_OBJECT_IMPL_FEATURE_COUNT + 12;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlClassExtractor <em>EXml Class Extractor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlClassExtractor
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlClassExtractor()
	 * @generated
	 */
	public static final int EXML_CLASS_EXTRACTOR = 16;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_CLASS_EXTRACTOR__CLASS_NAME = 0;

	/**
	 * The number of structural features of the '<em>EXml Class Extractor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_CLASS_EXTRACTOR_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement <em>EXml Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElement()
	 * @generated
	 */
	public static final int EXML_ELEMENT = 17;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__JAVA_ATTRIBUTE = EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_ACCESSOR_TYPE = EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Xml Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_ACCESS_METHODS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Xml Java Type Adapter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_JAVA_TYPE_ADAPTER = EJAVA_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__CONTAINER_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Xml Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_PROPERTIES = EJAVA_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__READ_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Write Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__WRITE_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__NAME = EJAVA_ATTRIBUTE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__NAMESPACE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__DEFAULT_VALUE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Nillable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__NILLABLE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__REQUIRED = EJAVA_ATTRIBUTE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Xml Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_ID = EJAVA_ATTRIBUTE_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Xml Id Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_ID_REF = EJAVA_ATTRIBUTE_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Xml Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_KEY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Xml List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_LIST = EJAVA_ATTRIBUTE_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Xml Inline Binary Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_INLINE_BINARY_DATA = EJAVA_ATTRIBUTE_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Xml Attachment Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_ATTACHMENT_REF = EJAVA_ATTRIBUTE_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Xml Mime Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_MIME_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Cdata</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__CDATA = EJAVA_ATTRIBUTE_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Xml Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_PATH = EJAVA_ATTRIBUTE_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>Xml Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_LOCATION = EJAVA_ATTRIBUTE_FEATURE_COUNT + 20;

	/**
	 * The feature id for the '<em><b>Xml Abstract Null Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_ABSTRACT_NULL_POLICY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 21;

	/**
	 * The feature id for the '<em><b>Xml Element Wrapper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_ELEMENT_WRAPPER = EJAVA_ATTRIBUTE_FEATURE_COUNT + 22;

	/**
	 * The feature id for the '<em><b>Xml Map</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_MAP = EJAVA_ATTRIBUTE_FEATURE_COUNT + 23;

	/**
	 * The feature id for the '<em><b>Xml Schema Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT__XML_SCHEMA_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 24;

	/**
	 * The number of structural features of the '<em>EXml Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_FEATURE_COUNT = EJAVA_ATTRIBUTE_FEATURE_COUNT + 25;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl <em>EXml Element Decl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementDecl()
	 * @generated
	 */
	public static final int EXML_ELEMENT_DECL = 18;

	/**
	 * The feature id for the '<em><b>Java Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_DECL__JAVA_METHOD = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_DECL__NAME = 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_DECL__NAMESPACE = 2;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_DECL__DEFAULT_VALUE = 3;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_DECL__SCOPE = 4;

	/**
	 * The feature id for the '<em><b>Substitution Head Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME = 5;

	/**
	 * The feature id for the '<em><b>Substitution Head Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_DECL__TYPE = 7;

	/**
	 * The number of structural features of the '<em>EXml Element Decl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_DECL_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef <em>EXml Element Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementRef()
	 * @generated
	 */
	public static final int EXML_ELEMENT_REF = 19;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF__JAVA_ATTRIBUTE = EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF__XML_ACCESSOR_TYPE = EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Xml Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF__XML_ACCESS_METHODS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Xml Java Type Adapter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF__XML_JAVA_TYPE_ADAPTER = EJAVA_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF__CONTAINER_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF__READ_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Write Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF__WRITE_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF__TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Xml Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF__XML_PROPERTIES = EJAVA_ATTRIBUTE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF__NAME = EJAVA_ATTRIBUTE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF__NAMESPACE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Xml Mixed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF__XML_MIXED = EJAVA_ATTRIBUTE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Xml Element Wrapper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF__XML_ELEMENT_WRAPPER = EJAVA_ATTRIBUTE_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>EXml Element Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REF_FEATURE_COUNT = EJAVA_ATTRIBUTE_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs <em>EXml Element Refs</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementRefs()
	 * @generated
	 */
	public static final int EXML_ELEMENT_REFS = 20;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REFS__JAVA_ATTRIBUTE = EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REFS__XML_ACCESSOR_TYPE = EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Xml Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REFS__XML_ACCESS_METHODS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Xml Java Type Adapter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REFS__XML_JAVA_TYPE_ADAPTER = EJAVA_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REFS__READ_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Write Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REFS__WRITE_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Xml Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REFS__XML_PROPERTIES = EJAVA_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Xml Mixed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REFS__XML_MIXED = EJAVA_ATTRIBUTE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Xml Element Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REFS__XML_ELEMENT_REFS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Xml Element Wrapper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REFS__XML_ELEMENT_WRAPPER = EJAVA_ATTRIBUTE_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>EXml Element Refs</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_REFS_FEATURE_COUNT = EJAVA_ATTRIBUTE_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements <em>EXml Elements</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElements()
	 * @generated
	 */
	public static final int EXML_ELEMENTS = 21;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENTS__JAVA_ATTRIBUTE = EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENTS__XML_ACCESSOR_TYPE = EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Xml Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENTS__XML_ACCESS_METHODS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENTS__CONTAINER_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENTS__READ_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Write Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENTS__WRITE_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Xml Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENTS__XML_PROPERTIES = EJAVA_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Xml Id Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENTS__XML_ID_REF = EJAVA_ATTRIBUTE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Xml List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENTS__XML_LIST = EJAVA_ATTRIBUTE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Xml Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENTS__XML_ELEMENTS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Xml Element Wrapper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENTS__XML_ELEMENT_WRAPPER = EJAVA_ATTRIBUTE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Xml Join Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENTS__XML_JOIN_NODES = EJAVA_ATTRIBUTE_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>EXml Elements</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENTS_FEATURE_COUNT = EJAVA_ATTRIBUTE_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper <em>EXml Element Wrapper</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementWrapper()
	 * @generated
	 */
	public static final int EXML_ELEMENT_WRAPPER = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_WRAPPER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_WRAPPER__NAMESPACE = 1;

	/**
	 * The feature id for the '<em><b>Nillable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_WRAPPER__NILLABLE = 2;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_WRAPPER__REQUIRED = 3;

	/**
	 * The number of structural features of the '<em>EXml Element Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ELEMENT_WRAPPER_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum <em>EXml Enum</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlEnum()
	 * @generated
	 */
	public static final int EXML_ENUM = 23;

	/**
	 * The feature id for the '<em><b>Xml Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ENUM__XML_TRANSIENT = EABSTRACT_TYPE_MAPPING__XML_TRANSIENT;

	/**
	 * The feature id for the '<em><b>Xml Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ENUM__XML_TYPE = EABSTRACT_TYPE_MAPPING__XML_TYPE;

	/**
	 * The feature id for the '<em><b>Xml Root Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ENUM__XML_ROOT_ELEMENT = EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT;

	/**
	 * The feature id for the '<em><b>Xml See Also</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ENUM__XML_SEE_ALSO = EABSTRACT_TYPE_MAPPING__XML_SEE_ALSO;

	/**
	 * The feature id for the '<em><b>Java Enum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ENUM__JAVA_ENUM = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ENUM__VALUE = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Xml Enum Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ENUM__XML_ENUM_VALUES = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>EXml Enum</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ENUM_FEATURE_COUNT = EABSTRACT_TYPE_MAPPING_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnumValue <em>EXml Enum Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnumValue
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlEnumValue()
	 * @generated
	 */
	public static final int EXML_ENUM_VALUE = 24;

	/**
	 * The feature id for the '<em><b>Java Enum Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ENUM_VALUE__JAVA_ENUM_VALUE = 0;

	/**
	 * The number of structural features of the '<em>EXml Enum Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ENUM_VALUE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlInverseReference <em>EXml Inverse Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlInverseReference
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlInverseReference()
	 * @generated
	 */
	public static final int EXML_INVERSE_REFERENCE = 25;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_INVERSE_REFERENCE__JAVA_ATTRIBUTE = EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_INVERSE_REFERENCE__XML_ACCESSOR_TYPE = EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Xml Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_INVERSE_REFERENCE__XML_ACCESS_METHODS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_INVERSE_REFERENCE__CONTAINER_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_INVERSE_REFERENCE__TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Xml Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_INVERSE_REFERENCE__XML_PROPERTIES = EJAVA_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_INVERSE_REFERENCE__MAPPED_BY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>EXml Inverse Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_INVERSE_REFERENCE_FEATURE_COUNT = EJAVA_ATTRIBUTE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetNullPolicy <em>EXml Is Set Null Policy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetNullPolicy
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlIsSetNullPolicy()
	 * @generated
	 */
	public static final int EXML_IS_SET_NULL_POLICY = 26;

	/**
	 * The feature id for the '<em><b>Xsi Nil Represents Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_IS_SET_NULL_POLICY__XSI_NIL_REPRESENTS_NULL = EABSTRACT_XML_NULL_POLICY__XSI_NIL_REPRESENTS_NULL;

	/**
	 * The feature id for the '<em><b>Empty Node Represents Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_IS_SET_NULL_POLICY__EMPTY_NODE_REPRESENTS_NULL = EABSTRACT_XML_NULL_POLICY__EMPTY_NODE_REPRESENTS_NULL;

	/**
	 * The feature id for the '<em><b>Null Representation For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_IS_SET_NULL_POLICY__NULL_REPRESENTATION_FOR_XML = EABSTRACT_XML_NULL_POLICY__NULL_REPRESENTATION_FOR_XML;

	/**
	 * The feature id for the '<em><b>Is Set Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_IS_SET_NULL_POLICY__IS_SET_METHOD_NAME = EABSTRACT_XML_NULL_POLICY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Xml Is Set Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_IS_SET_NULL_POLICY__XML_IS_SET_PARAMETERS = EABSTRACT_XML_NULL_POLICY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>EXml Is Set Null Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_IS_SET_NULL_POLICY_FEATURE_COUNT = EABSTRACT_XML_NULL_POLICY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetParameter <em>EXml Is Set Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetParameter
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlIsSetParameter()
	 * @generated
	 */
	public static final int EXML_IS_SET_PARAMETER = 27;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_IS_SET_PARAMETER__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_IS_SET_PARAMETER__TYPE = 1;

	/**
	 * The number of structural features of the '<em>EXml Is Set Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_IS_SET_PARAMETER_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter <em>EXml Java Type Adapter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlJavaTypeAdapter()
	 * @generated
	 */
	public static final int EXML_JAVA_TYPE_ADAPTER = 28;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JAVA_TYPE_ADAPTER__JAVA_ATTRIBUTE = EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JAVA_TYPE_ADAPTER__XML_ACCESSOR_TYPE = EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JAVA_TYPE_ADAPTER__VALUE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JAVA_TYPE_ADAPTER__TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JAVA_TYPE_ADAPTER__VALUE_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>EXml Java Type Adapter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JAVA_TYPE_ADAPTER_FEATURE_COUNT = EJAVA_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode <em>EXml Join Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlJoinNode()
	 * @generated
	 */
	public static final int EXML_JOIN_NODE = 29;

	/**
	 * The feature id for the '<em><b>Xml Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JOIN_NODE__XML_PATH = 0;

	/**
	 * The feature id for the '<em><b>Referenced Xml Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JOIN_NODE__REFERENCED_XML_PATH = 1;

	/**
	 * The number of structural features of the '<em>EXml Join Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JOIN_NODE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNodes <em>EXml Join Nodes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNodes
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlJoinNodes()
	 * @generated
	 */
	public static final int EXML_JOIN_NODES = 30;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JOIN_NODES__JAVA_ATTRIBUTE = EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JOIN_NODES__XML_ACCESSOR_TYPE = EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JOIN_NODES__CONTAINER_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JOIN_NODES__TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Xml Join Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JOIN_NODES__XML_JOIN_NODES = EJAVA_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>EXml Join Nodes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_JOIN_NODES_FEATURE_COUNT = EJAVA_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMap <em>EXml Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMap
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlMap()
	 * @generated
	 */
	public static final int EXML_MAP = 31;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EXml Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNullPolicy <em>EXml Null Policy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNullPolicy
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlNullPolicy()
	 * @generated
	 */
	public static final int EXML_NULL_POLICY = 32;

	/**
	 * The feature id for the '<em><b>Xsi Nil Represents Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_NULL_POLICY__XSI_NIL_REPRESENTS_NULL = EABSTRACT_XML_NULL_POLICY__XSI_NIL_REPRESENTS_NULL;

	/**
	 * The feature id for the '<em><b>Empty Node Represents Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_NULL_POLICY__EMPTY_NODE_REPRESENTS_NULL = EABSTRACT_XML_NULL_POLICY__EMPTY_NODE_REPRESENTS_NULL;

	/**
	 * The feature id for the '<em><b>Null Representation For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_NULL_POLICY__NULL_REPRESENTATION_FOR_XML = EABSTRACT_XML_NULL_POLICY__NULL_REPRESENTATION_FOR_XML;

	/**
	 * The feature id for the '<em><b>Is Set Performed For Absent Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_NULL_POLICY__IS_SET_PERFORMED_FOR_ABSENT_NODE = EABSTRACT_XML_NULL_POLICY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EXml Null Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_NULL_POLICY_FEATURE_COUNT = EABSTRACT_XML_NULL_POLICY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNs <em>EXml Ns</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNs
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlNs()
	 * @generated
	 */
	public static final int EXML_NS = 33;

	/**
	 * The feature id for the '<em><b>Namespace Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_NS__NAMESPACE_URI = 0;

	/**
	 * The feature id for the '<em><b>Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_NS__PREFIX = 1;

	/**
	 * The number of structural features of the '<em>EXml Ns</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_NS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlProperty <em>EXml Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlProperty
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlProperty()
	 * @generated
	 */
	public static final int EXML_PROPERTY = 34;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_PROPERTY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_PROPERTY__VALUE = 1;

	/**
	 * The feature id for the '<em><b>Value Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_PROPERTY__VALUE_TYPE = 2;

	/**
	 * The number of structural features of the '<em>EXml Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_PROPERTY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlReadTransformer <em>EXml Read Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlReadTransformer
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlReadTransformer()
	 * @generated
	 */
	public static final int EXML_READ_TRANSFORMER = 35;

	/**
	 * The feature id for the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_READ_TRANSFORMER__METHOD = EABSTRACT_XML_TRANSFORMER__METHOD;

	/**
	 * The feature id for the '<em><b>Transformer Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_READ_TRANSFORMER__TRANSFORMER_CLASS = EABSTRACT_XML_TRANSFORMER__TRANSFORMER_CLASS;

	/**
	 * The number of structural features of the '<em>EXml Read Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_READ_TRANSFORMER_FEATURE_COUNT = EABSTRACT_XML_TRANSFORMER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRegistry <em>EXml Registry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRegistry
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlRegistry()
	 * @generated
	 */
	public static final int EXML_REGISTRY = 36;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_REGISTRY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Xml Element Decls</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_REGISTRY__XML_ELEMENT_DECLS = 1;

	/**
	 * The number of structural features of the '<em>EXml Registry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_REGISTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRootElement <em>EXml Root Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRootElement
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlRootElement()
	 * @generated
	 */
	public static final int EXML_ROOT_ELEMENT = 37;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ROOT_ELEMENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ROOT_ELEMENT__NAMESPACE = 1;

	/**
	 * The number of structural features of the '<em>EXml Root Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_ROOT_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema <em>EXml Schema</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSchema()
	 * @generated
	 */
	public static final int EXML_SCHEMA = 38;

	/**
	 * The feature id for the '<em><b>Attribute Form Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_SCHEMA__ATTRIBUTE_FORM_DEFAULT = 0;

	/**
	 * The feature id for the '<em><b>Element Form Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_SCHEMA__ELEMENT_FORM_DEFAULT = 1;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_SCHEMA__LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_SCHEMA__NAMESPACE = 3;

	/**
	 * The feature id for the '<em><b>Xmlns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_SCHEMA__XMLNS = 4;

	/**
	 * The number of structural features of the '<em>EXml Schema</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_SCHEMA_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchemaType <em>EXml Schema Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchemaType
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSchemaType()
	 * @generated
	 */
	public static final int EXML_SCHEMA_TYPE = 39;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_SCHEMA_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_SCHEMA_TYPE__NAMESPACE = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_SCHEMA_TYPE__TYPE = 2;

	/**
	 * The number of structural features of the '<em>EXml Schema Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_SCHEMA_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransformation <em>EXml Transformation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransformation
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlTransformation()
	 * @generated
	 */
	public static final int EXML_TRANSFORMATION = 40;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TRANSFORMATION__JAVA_ATTRIBUTE = EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TRANSFORMATION__XML_ACCESSOR_TYPE = EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Xml Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TRANSFORMATION__XML_ACCESS_METHODS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Xml Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TRANSFORMATION__XML_PROPERTIES = EJAVA_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TRANSFORMATION__OPTIONAL = EJAVA_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Xml Read Transformer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TRANSFORMATION__XML_READ_TRANSFORMER = EJAVA_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Xml Write Transformers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TRANSFORMATION__XML_WRITE_TRANSFORMERS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>EXml Transformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TRANSFORMATION_FEATURE_COUNT = EJAVA_ATTRIBUTE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransient <em>EXml Transient</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransient
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlTransient()
	 * @generated
	 */
	public static final int EXML_TRANSIENT = 41;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TRANSIENT__JAVA_ATTRIBUTE = EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TRANSIENT__XML_ACCESSOR_TYPE = EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Xml Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TRANSIENT__XML_LOCATION = EJAVA_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EXml Transient</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TRANSIENT_FEATURE_COUNT = EJAVA_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType <em>EXml Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlType()
	 * @generated
	 */
	public static final int EXML_TYPE = 42;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TYPE__NAMESPACE = 1;

	/**
	 * The feature id for the '<em><b>Factory Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TYPE__FACTORY_CLASS = 2;

	/**
	 * The feature id for the '<em><b>Factory Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TYPE__FACTORY_METHOD = 3;

	/**
	 * The feature id for the '<em><b>Prop Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TYPE__PROP_ORDER = 4;

	/**
	 * The number of structural features of the '<em>EXml Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue <em>EXml Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlValue()
	 * @generated
	 */
	public static final int EXML_VALUE = 43;

	/**
	 * The feature id for the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VALUE__JAVA_ATTRIBUTE = EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VALUE__XML_ACCESSOR_TYPE = EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Xml Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VALUE__XML_ACCESS_METHODS = EJAVA_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Xml Java Type Adapter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VALUE__XML_JAVA_TYPE_ADAPTER = EJAVA_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VALUE__CONTAINER_TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VALUE__READ_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Write Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VALUE__WRITE_ONLY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VALUE__TYPE = EJAVA_ATTRIBUTE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Xml Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VALUE__XML_PROPERTIES = EJAVA_ATTRIBUTE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Cdata</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VALUE__CDATA = EJAVA_ATTRIBUTE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Xml Abstract Null Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VALUE__XML_ABSTRACT_NULL_POLICY = EJAVA_ATTRIBUTE_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>EXml Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VALUE_FEATURE_COUNT = EJAVA_ATTRIBUTE_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods <em>EXml Virtual Access Methods</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlVirtualAccessMethods()
	 * @generated
	 */
	public static final int EXML_VIRTUAL_ACCESS_METHODS = 44;

	/**
	 * The feature id for the '<em><b>Get Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VIRTUAL_ACCESS_METHODS__GET_METHOD = 0;

	/**
	 * The feature id for the '<em><b>Set Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VIRTUAL_ACCESS_METHODS__SET_METHOD = 1;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VIRTUAL_ACCESS_METHODS__SCHEMA = 2;

	/**
	 * The number of structural features of the '<em>EXml Virtual Access Methods</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_VIRTUAL_ACCESS_METHODS_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlWriteTransformer <em>EXml Write Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlWriteTransformer
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlWriteTransformer()
	 * @generated
	 */
	public static final int EXML_WRITE_TRANSFORMER = 45;

	/**
	 * The feature id for the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_WRITE_TRANSFORMER__METHOD = EABSTRACT_XML_TRANSFORMER__METHOD;

	/**
	 * The feature id for the '<em><b>Transformer Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_WRITE_TRANSFORMER__TRANSFORMER_CLASS = EABSTRACT_XML_TRANSFORMER__TRANSFORMER_CLASS;

	/**
	 * The feature id for the '<em><b>Xml Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_WRITE_TRANSFORMER__XML_PATH = EABSTRACT_XML_TRANSFORMER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EXml Write Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int EXML_WRITE_TRANSFORMER_FEATURE_COUNT = EABSTRACT_XML_TRANSFORMER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessOrder <em>EXml Access Order</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessOrder
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAccessOrder()
	 * @generated
	 */
	public static final int EXML_ACCESS_ORDER = 46;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType <em>EXml Access Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAccessType()
	 * @generated
	 */
	public static final int EXML_ACCESS_TYPE = 47;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMarshalNullRepresentation <em>EXml Marshal Null Representation</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMarshalNullRepresentation
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlMarshalNullRepresentation()
	 * @generated
	 */
	public static final int EXML_MARSHAL_NULL_REPRESENTATION = 48;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNsForm <em>EXml Ns Form</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNsForm
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlNsForm()
	 * @generated
	 */
	public static final int EXML_NS_FORM = 49;

	/**
	 * The meta object id for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethodsSchema <em>EXml Virtual Access Methods Schema</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethodsSchema
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlVirtualAccessMethodsSchema()
	 * @generated
	 */
	public static final int EXML_VIRTUAL_ACCESS_METHODS_SCHEMA = 50;

	/**
	 * The meta object id for the '<em>EProp Order</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.List
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEPropOrder()
	 * @generated
	 */
	public static final int EPROP_ORDER = 51;

	/**
	 * The meta object id for the '<em>EXml See Also</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.List
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSeeAlso()
	 * @generated
	 */
	public static final int EXML_SEE_ALSO = 52;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eAbstractTypeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eAbstractXmlNullPolicyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eAbstractXmlTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eAccessibleJavaAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eAdaptableJavaAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eContainerJavaAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eJavaAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eJavaTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ePropertyHolderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eReadWriteJavaAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eTypedJavaAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlAccessMethodsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlAnyAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlAnyElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlBindingsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlClassExtractorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlElementDeclEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlElementRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlElementRefsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlElementsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlElementWrapperEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlEnumEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlEnumValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlInverseReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlIsSetNullPolicyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlIsSetParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlJavaTypeAdapterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlJoinNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlJoinNodesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlNullPolicyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlNsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlReadTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlRegistryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlRootElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlSchemaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlSchemaTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlTransformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlTransientEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlVirtualAccessMethodsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eXmlWriteTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum eXmlAccessOrderEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum eXmlAccessTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum eXmlMarshalNullRepresentationEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum eXmlNsFormEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum eXmlVirtualAccessMethodsSchemaEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType ePropOrderEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType eXmlSeeAlsoEDataType = null;

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
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private OxmPackage()
	{
		super(eNS_URI, OxmFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link OxmPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static OxmPackage init()
	{
		if (isInited) return (OxmPackage)EPackage.Registry.INSTANCE.getEPackage(OxmPackage.eNS_URI);

		// Obtain or create and register package
		OxmPackage theOxmPackage = (OxmPackage)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof OxmPackage ? EPackage.Registry.INSTANCE.get(eNS_URI) : new OxmPackage());

		isInited = true;

		// Initialize simple dependencies
		CommonPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theOxmPackage.createPackageContents();

		// Initialize created meta-data
		theOxmPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theOxmPackage, 
			 new EValidator.Descriptor()
			 {
				 public EValidator getEValidator()
				 {
					 return OxmValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theOxmPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(OxmPackage.eNS_URI, theOxmPackage);
		return theOxmPackage;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping <em>EAbstract Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EAbstract Type Mapping</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping
	 * @generated
	 */
	public EClass getEAbstractTypeMapping()
	{
		return eAbstractTypeMappingEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlTransient <em>Xml Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Transient</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlTransient()
	 * @see #getEAbstractTypeMapping()
	 * @generated
	 */
	public EAttribute getEAbstractTypeMapping_XmlTransient()
	{
		return (EAttribute)eAbstractTypeMappingEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlType <em>Xml Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlType()
	 * @see #getEAbstractTypeMapping()
	 * @generated
	 */
	public EReference getEAbstractTypeMapping_XmlType()
	{
		return (EReference)eAbstractTypeMappingEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlRootElement <em>Xml Root Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Root Element</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlRootElement()
	 * @see #getEAbstractTypeMapping()
	 * @generated
	 */
	public EReference getEAbstractTypeMapping_XmlRootElement()
	{
		return (EReference)eAbstractTypeMappingEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlSeeAlso <em>Xml See Also</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml See Also</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlSeeAlso()
	 * @see #getEAbstractTypeMapping()
	 * @generated
	 */
	public EAttribute getEAbstractTypeMapping_XmlSeeAlso()
	{
		return (EAttribute)eAbstractTypeMappingEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy <em>EAbstract Xml Null Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EAbstract Xml Null Policy</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy
	 * @generated
	 */
	public EClass getEAbstractXmlNullPolicy()
	{
		return eAbstractXmlNullPolicyEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy#isXsiNilRepresentsNull <em>Xsi Nil Represents Null</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xsi Nil Represents Null</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy#isXsiNilRepresentsNull()
	 * @see #getEAbstractXmlNullPolicy()
	 * @generated
	 */
	public EAttribute getEAbstractXmlNullPolicy_XsiNilRepresentsNull()
	{
		return (EAttribute)eAbstractXmlNullPolicyEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy#isEmptyNodeRepresentsNull <em>Empty Node Represents Null</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Empty Node Represents Null</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy#isEmptyNodeRepresentsNull()
	 * @see #getEAbstractXmlNullPolicy()
	 * @generated
	 */
	public EAttribute getEAbstractXmlNullPolicy_EmptyNodeRepresentsNull()
	{
		return (EAttribute)eAbstractXmlNullPolicyEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy#getNullRepresentationForXml <em>Null Representation For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Null Representation For Xml</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy#getNullRepresentationForXml()
	 * @see #getEAbstractXmlNullPolicy()
	 * @generated
	 */
	public EAttribute getEAbstractXmlNullPolicy_NullRepresentationForXml()
	{
		return (EAttribute)eAbstractXmlNullPolicyEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer <em>EAbstract Xml Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EAbstract Xml Transformer</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer
	 * @generated
	 */
	public EClass getEAbstractXmlTransformer()
	{
		return eAbstractXmlTransformerEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer#getMethod <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Method</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer#getMethod()
	 * @see #getEAbstractXmlTransformer()
	 * @generated
	 */
	public EAttribute getEAbstractXmlTransformer_Method()
	{
		return (EAttribute)eAbstractXmlTransformerEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer#getTransformerClass <em>Transformer Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transformer Class</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer#getTransformerClass()
	 * @see #getEAbstractXmlTransformer()
	 * @generated
	 */
	public EAttribute getEAbstractXmlTransformer_TransformerClass()
	{
		return (EAttribute)eAbstractXmlTransformerEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAccessibleJavaAttribute <em>EAccessible Java Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EAccessible Java Attribute</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAccessibleJavaAttribute
	 * @generated
	 */
	public EClass getEAccessibleJavaAttribute()
	{
		return eAccessibleJavaAttributeEClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAccessibleJavaAttribute#getXmlAccessMethods <em>Xml Access Methods</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Access Methods</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAccessibleJavaAttribute#getXmlAccessMethods()
	 * @see #getEAccessibleJavaAttribute()
	 * @generated
	 */
	public EReference getEAccessibleJavaAttribute_XmlAccessMethods()
	{
		return (EReference)eAccessibleJavaAttributeEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAdaptableJavaAttribute <em>EAdaptable Java Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EAdaptable Java Attribute</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAdaptableJavaAttribute
	 * @generated
	 */
	public EClass getEAdaptableJavaAttribute()
	{
		return eAdaptableJavaAttributeEClass;
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAdaptableJavaAttribute#getXmlJavaTypeAdapter <em>Xml Java Type Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Java Type Adapter</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAdaptableJavaAttribute#getXmlJavaTypeAdapter()
	 * @see #getEAdaptableJavaAttribute()
	 * @generated
	 */
	public EReference getEAdaptableJavaAttribute_XmlJavaTypeAdapter()
	{
		return (EReference)eAdaptableJavaAttributeEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EContainerJavaAttribute <em>EContainer Java Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EContainer Java Attribute</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EContainerJavaAttribute
	 * @generated
	 */
	public EClass getEContainerJavaAttribute()
	{
		return eContainerJavaAttributeEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EContainerJavaAttribute#getContainerType <em>Container Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Container Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EContainerJavaAttribute#getContainerType()
	 * @see #getEContainerJavaAttribute()
	 * @generated
	 */
	public EAttribute getEContainerJavaAttribute_ContainerType()
	{
		return (EAttribute)eContainerJavaAttributeEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute <em>EJava Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EJava Attribute</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute
	 * @generated
	 */
	public EClass getEJavaAttribute()
	{
		return eJavaAttributeEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute#getJavaAttribute <em>Java Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Java Attribute</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute#getJavaAttribute()
	 * @see #getEJavaAttribute()
	 * @generated
	 */
	public EAttribute getEJavaAttribute_JavaAttribute()
	{
		return (EAttribute)eJavaAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute#getXmlAccessorType <em>Xml Accessor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Accessor Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute#getXmlAccessorType()
	 * @see #getEJavaAttribute()
	 * @generated
	 */
	public EAttribute getEJavaAttribute_XmlAccessorType()
	{
		return (EAttribute)eJavaAttributeEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType <em>EJava Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EJava Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType
	 * @generated
	 */
	public EClass getEJavaType()
	{
		return eJavaTypeEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getName()
	 * @see #getEJavaType()
	 * @generated
	 */
	public EAttribute getEJavaType_Name()
	{
		return (EAttribute)eJavaTypeEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getSuperType <em>Super Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Super Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getSuperType()
	 * @see #getEJavaType()
	 * @generated
	 */
	public EAttribute getEJavaType_SuperType()
	{
		return (EAttribute)eJavaTypeEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlAccessorOrder <em>Xml Accessor Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Accessor Order</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlAccessorOrder()
	 * @see #getEJavaType()
	 * @generated
	 */
	public EAttribute getEJavaType_XmlAccessorOrder()
	{
		return (EAttribute)eJavaTypeEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlAccessorType <em>Xml Accessor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Accessor Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlAccessorType()
	 * @see #getEJavaType()
	 * @generated
	 */
	public EAttribute getEJavaType_XmlAccessorType()
	{
		return (EAttribute)eJavaTypeEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlCustomizer <em>Xml Customizer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Customizer</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlCustomizer()
	 * @see #getEJavaType()
	 * @generated
	 */
	public EAttribute getEJavaType_XmlCustomizer()
	{
		return (EAttribute)eJavaTypeEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlDiscriminatorNode <em>Xml Discriminator Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Discriminator Node</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlDiscriminatorNode()
	 * @see #getEJavaType()
	 * @generated
	 */
	public EAttribute getEJavaType_XmlDiscriminatorNode()
	{
		return (EAttribute)eJavaTypeEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlDiscriminatorValue <em>Xml Discriminator Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Discriminator Value</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlDiscriminatorValue()
	 * @see #getEJavaType()
	 * @generated
	 */
	public EAttribute getEJavaType_XmlDiscriminatorValue()
	{
		return (EAttribute)eJavaTypeEClass.getEStructuralFeatures().get(6);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#isXmlInlineBinaryData <em>Xml Inline Binary Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Inline Binary Data</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#isXmlInlineBinaryData()
	 * @see #getEJavaType()
	 * @generated
	 */
	public EAttribute getEJavaType_XmlInlineBinaryData()
	{
		return (EAttribute)eJavaTypeEClass.getEStructuralFeatures().get(7);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlNameTransformer <em>Xml Name Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Name Transformer</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlNameTransformer()
	 * @see #getEJavaType()
	 * @generated
	 */
	public EAttribute getEJavaType_XmlNameTransformer()
	{
		return (EAttribute)eJavaTypeEClass.getEStructuralFeatures().get(8);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlVirtualAccessMethods <em>Xml Virtual Access Methods</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Virtual Access Methods</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlVirtualAccessMethods()
	 * @see #getEJavaType()
	 * @generated
	 */
	public EReference getEJavaType_XmlVirtualAccessMethods()
	{
		return (EReference)eJavaTypeEClass.getEStructuralFeatures().get(9);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlJavaTypeAdapter <em>Xml Java Type Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Java Type Adapter</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlJavaTypeAdapter()
	 * @see #getEJavaType()
	 * @generated
	 */
	public EReference getEJavaType_XmlJavaTypeAdapter()
	{
		return (EReference)eJavaTypeEClass.getEStructuralFeatures().get(10);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlClassExtractor <em>Xml Class Extractor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Class Extractor</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getXmlClassExtractor()
	 * @see #getEJavaType()
	 * @generated
	 */
	public EReference getEJavaType_XmlClassExtractor()
	{
		return (EReference)eJavaTypeEClass.getEStructuralFeatures().get(11);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getJavaAttributes <em>Java Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Java Attributes</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType#getJavaAttributes()
	 * @see #getEJavaType()
	 * @generated
	 */
	public EReference getEJavaType_JavaAttributes()
	{
		return (EReference)eJavaTypeEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EPropertyHolder <em>EProperty Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EProperty Holder</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EPropertyHolder
	 * @generated
	 */
	public EClass getEPropertyHolder()
	{
		return ePropertyHolderEClass;
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EPropertyHolder#getXmlProperties <em>Xml Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xml Properties</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EPropertyHolder#getXmlProperties()
	 * @see #getEPropertyHolder()
	 * @generated
	 */
	public EReference getEPropertyHolder_XmlProperties()
	{
		return (EReference)ePropertyHolderEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EReadWriteJavaAttribute <em>ERead Write Java Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ERead Write Java Attribute</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EReadWriteJavaAttribute
	 * @generated
	 */
	public EClass getEReadWriteJavaAttribute()
	{
		return eReadWriteJavaAttributeEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EReadWriteJavaAttribute#isReadOnly <em>Read Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Read Only</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EReadWriteJavaAttribute#isReadOnly()
	 * @see #getEReadWriteJavaAttribute()
	 * @generated
	 */
	public EAttribute getEReadWriteJavaAttribute_ReadOnly()
	{
		return (EAttribute)eReadWriteJavaAttributeEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EReadWriteJavaAttribute#isWriteOnly <em>Write Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Write Only</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EReadWriteJavaAttribute#isWriteOnly()
	 * @see #getEReadWriteJavaAttribute()
	 * @generated
	 */
	public EAttribute getEReadWriteJavaAttribute_WriteOnly()
	{
		return (EAttribute)eReadWriteJavaAttributeEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.ETypedJavaAttribute <em>ETyped Java Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ETyped Java Attribute</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.ETypedJavaAttribute
	 * @generated
	 */
	public EClass getETypedJavaAttribute()
	{
		return eTypedJavaAttributeEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.ETypedJavaAttribute#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.ETypedJavaAttribute#getType()
	 * @see #getETypedJavaAttribute()
	 * @generated
	 */
	public EAttribute getETypedJavaAttribute_Type()
	{
		return (EAttribute)eTypedJavaAttributeEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessMethods <em>EXml Access Methods</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Access Methods</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessMethods
	 * @generated
	 */
	public EClass getEXmlAccessMethods()
	{
		return eXmlAccessMethodsEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessMethods#getGetMethod <em>Get Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Get Method</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessMethods#getGetMethod()
	 * @see #getEXmlAccessMethods()
	 * @generated
	 */
	public EAttribute getEXmlAccessMethods_GetMethod()
	{
		return (EAttribute)eXmlAccessMethodsEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessMethods#getSetMethod <em>Set Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Set Method</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessMethods#getSetMethod()
	 * @see #getEXmlAccessMethods()
	 * @generated
	 */
	public EAttribute getEXmlAccessMethods_SetMethod()
	{
		return (EAttribute)eXmlAccessMethodsEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute <em>EXml Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Any Attribute</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute
	 * @generated
	 */
	public EClass getEXmlAnyAttribute()
	{
		return eXmlAnyAttributeEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute#getXmlPath <em>Xml Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Path</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute#getXmlPath()
	 * @see #getEXmlAnyAttribute()
	 * @generated
	 */
	public EAttribute getEXmlAnyAttribute_XmlPath()
	{
		return (EAttribute)eXmlAnyAttributeEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement <em>EXml Any Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Any Element</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement
	 * @generated
	 */
	public EClass getEXmlAnyElement()
	{
		return eXmlAnyElementEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement#isXmlMixed <em>Xml Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Mixed</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement#isXmlMixed()
	 * @see #getEXmlAnyElement()
	 * @generated
	 */
	public EAttribute getEXmlAnyElement_XmlMixed()
	{
		return (EAttribute)eXmlAnyElementEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement#isLax <em>Lax</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lax</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement#isLax()
	 * @see #getEXmlAnyElement()
	 * @generated
	 */
	public EAttribute getEXmlAnyElement_Lax()
	{
		return (EAttribute)eXmlAnyElementEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement#getDomHandler <em>Dom Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dom Handler</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement#getDomHandler()
	 * @see #getEXmlAnyElement()
	 * @generated
	 */
	public EAttribute getEXmlAnyElement_DomHandler()
	{
		return (EAttribute)eXmlAnyElementEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement#getXmlPath <em>Xml Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Path</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement#getXmlPath()
	 * @see #getEXmlAnyElement()
	 * @generated
	 */
	public EAttribute getEXmlAnyElement_XmlPath()
	{
		return (EAttribute)eXmlAnyElementEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement#getXmlElementRefs <em>Xml Element Refs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Element Refs</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement#getXmlElementRefs()
	 * @see #getEXmlAnyElement()
	 * @generated
	 */
	public EReference getEXmlAnyElement_XmlElementRefs()
	{
		return (EReference)eXmlAnyElementEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute <em>EXml Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Attribute</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute
	 * @generated
	 */
	public EClass getEXmlAttribute()
	{
		return eXmlAttributeEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getName()
	 * @see #getEXmlAttribute()
	 * @generated
	 */
	public EAttribute getEXmlAttribute_Name()
	{
		return (EAttribute)eXmlAttributeEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getNamespace()
	 * @see #getEXmlAttribute()
	 * @generated
	 */
	public EAttribute getEXmlAttribute_Namespace()
	{
		return (EAttribute)eXmlAttributeEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isRequired <em>Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Required</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isRequired()
	 * @see #getEXmlAttribute()
	 * @generated
	 */
	public EAttribute getEXmlAttribute_Required()
	{
		return (EAttribute)eXmlAttributeEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlId <em>Xml Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Id</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlId()
	 * @see #getEXmlAttribute()
	 * @generated
	 */
	public EAttribute getEXmlAttribute_XmlId()
	{
		return (EAttribute)eXmlAttributeEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlIdRef <em>Xml Id Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Id Ref</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlIdRef()
	 * @see #getEXmlAttribute()
	 * @generated
	 */
	public EAttribute getEXmlAttribute_XmlIdRef()
	{
		return (EAttribute)eXmlAttributeEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlKey <em>Xml Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Key</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlKey()
	 * @see #getEXmlAttribute()
	 * @generated
	 */
	public EAttribute getEXmlAttribute_XmlKey()
	{
		return (EAttribute)eXmlAttributeEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlList <em>Xml List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml List</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlList()
	 * @see #getEXmlAttribute()
	 * @generated
	 */
	public EAttribute getEXmlAttribute_XmlList()
	{
		return (EAttribute)eXmlAttributeEClass.getEStructuralFeatures().get(6);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlInlineBinaryData <em>Xml Inline Binary Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Inline Binary Data</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlInlineBinaryData()
	 * @see #getEXmlAttribute()
	 * @generated
	 */
	public EAttribute getEXmlAttribute_XmlInlineBinaryData()
	{
		return (EAttribute)eXmlAttributeEClass.getEStructuralFeatures().get(7);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlAttachmentRef <em>Xml Attachment Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Attachment Ref</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#isXmlAttachmentRef()
	 * @see #getEXmlAttribute()
	 * @generated
	 */
	public EAttribute getEXmlAttribute_XmlAttachmentRef()
	{
		return (EAttribute)eXmlAttributeEClass.getEStructuralFeatures().get(8);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlMimeType <em>Xml Mime Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Mime Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlMimeType()
	 * @see #getEXmlAttribute()
	 * @generated
	 */
	public EAttribute getEXmlAttribute_XmlMimeType()
	{
		return (EAttribute)eXmlAttributeEClass.getEStructuralFeatures().get(9);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlPath <em>Xml Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Path</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlPath()
	 * @see #getEXmlAttribute()
	 * @generated
	 */
	public EAttribute getEXmlAttribute_XmlPath()
	{
		return (EAttribute)eXmlAttributeEClass.getEStructuralFeatures().get(10);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlAbstractNullPolicy <em>Xml Abstract Null Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Abstract Null Policy</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlAbstractNullPolicy()
	 * @see #getEXmlAttribute()
	 * @generated
	 */
	public EReference getEXmlAttribute_XmlAbstractNullPolicy()
	{
		return (EReference)eXmlAttributeEClass.getEStructuralFeatures().get(11);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlSchemaType <em>Xml Schema Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Schema Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute#getXmlSchemaType()
	 * @see #getEXmlAttribute()
	 * @generated
	 */
	public EReference getEXmlAttribute_XmlSchemaType()
	{
		return (EReference)eXmlAttributeEClass.getEStructuralFeatures().get(12);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings <em>EXml Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Bindings</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings
	 * @generated
	 */
	public EClass getEXmlBindings()
	{
		return eXmlBindingsEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlAccessorType <em>Xml Accessor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Accessor Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlAccessorType()
	 * @see #getEXmlBindings()
	 * @generated
	 */
	public EAttribute getEXmlBindings_XmlAccessorType()
	{
		return (EAttribute)eXmlBindingsEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlAccessorOrder <em>Xml Accessor Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Accessor Order</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlAccessorOrder()
	 * @see #getEXmlBindings()
	 * @generated
	 */
	public EAttribute getEXmlBindings_XmlAccessorOrder()
	{
		return (EAttribute)eXmlBindingsEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Mapping Metadata Complete</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlMappingMetadataComplete()
	 * @see #getEXmlBindings()
	 * @generated
	 */
	public EAttribute getEXmlBindings_XmlMappingMetadataComplete()
	{
		return (EAttribute)eXmlBindingsEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getPackageName <em>Package Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getPackageName()
	 * @see #getEXmlBindings()
	 * @generated
	 */
	public EAttribute getEXmlBindings_PackageName()
	{
		return (EAttribute)eXmlBindingsEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlNameTransformer <em>Xml Name Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Name Transformer</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlNameTransformer()
	 * @see #getEXmlBindings()
	 * @generated
	 */
	public EAttribute getEXmlBindings_XmlNameTransformer()
	{
		return (EAttribute)eXmlBindingsEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlSchema <em>Xml Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Schema</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlSchema()
	 * @see #getEXmlBindings()
	 * @generated
	 */
	public EReference getEXmlBindings_XmlSchema()
	{
		return (EReference)eXmlBindingsEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlSchemaType <em>Xml Schema Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Schema Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlSchemaType()
	 * @see #getEXmlBindings()
	 * @generated
	 */
	public EReference getEXmlBindings_XmlSchemaType()
	{
		return (EReference)eXmlBindingsEClass.getEStructuralFeatures().get(6);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlSchemaTypes <em>Xml Schema Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xml Schema Types</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlSchemaTypes()
	 * @see #getEXmlBindings()
	 * @generated
	 */
	public EReference getEXmlBindings_XmlSchemaTypes()
	{
		return (EReference)eXmlBindingsEClass.getEStructuralFeatures().get(7);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlJavaTypeAdapters <em>Xml Java Type Adapters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xml Java Type Adapters</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlJavaTypeAdapters()
	 * @see #getEXmlBindings()
	 * @generated
	 */
	public EReference getEXmlBindings_XmlJavaTypeAdapters()
	{
		return (EReference)eXmlBindingsEClass.getEStructuralFeatures().get(8);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlRegistries <em>Xml Registries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xml Registries</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlRegistries()
	 * @see #getEXmlBindings()
	 * @generated
	 */
	public EReference getEXmlBindings_XmlRegistries()
	{
		return (EReference)eXmlBindingsEClass.getEStructuralFeatures().get(9);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlEnums <em>Xml Enums</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xml Enums</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getXmlEnums()
	 * @see #getEXmlBindings()
	 * @generated
	 */
	public EReference getEXmlBindings_XmlEnums()
	{
		return (EReference)eXmlBindingsEClass.getEStructuralFeatures().get(10);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getJavaTypes <em>Java Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Java Types</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings#getJavaTypes()
	 * @see #getEXmlBindings()
	 * @generated
	 */
	public EReference getEXmlBindings_JavaTypes()
	{
		return (EReference)eXmlBindingsEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlClassExtractor <em>EXml Class Extractor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Class Extractor</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlClassExtractor
	 * @generated
	 */
	public EClass getEXmlClassExtractor()
	{
		return eXmlClassExtractorEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlClassExtractor#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlClassExtractor#getClassName()
	 * @see #getEXmlClassExtractor()
	 * @generated
	 */
	public EAttribute getEXmlClassExtractor_ClassName()
	{
		return (EAttribute)eXmlClassExtractorEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement <em>EXml Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Element</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement
	 * @generated
	 */
	public EClass getEXmlElement()
	{
		return eXmlElementEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getName()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_Name()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getNamespace()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_Namespace()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getDefaultValue <em>Default Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Value</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getDefaultValue()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_DefaultValue()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isNillable <em>Nillable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nillable</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isNillable()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_Nillable()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isRequired <em>Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Required</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isRequired()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_Required()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlId <em>Xml Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Id</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlId()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_XmlId()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlIdRef <em>Xml Id Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Id Ref</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlIdRef()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_XmlIdRef()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(6);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlKey <em>Xml Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Key</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlKey()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_XmlKey()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(7);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlList <em>Xml List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml List</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlList()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_XmlList()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(8);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlInlineBinaryData <em>Xml Inline Binary Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Inline Binary Data</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlInlineBinaryData()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_XmlInlineBinaryData()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(9);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlAttachmentRef <em>Xml Attachment Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Attachment Ref</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlAttachmentRef()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_XmlAttachmentRef()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(10);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getXmlMimeType <em>Xml Mime Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Mime Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getXmlMimeType()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_XmlMimeType()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(11);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isCdata <em>Cdata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cdata</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isCdata()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_Cdata()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(12);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getXmlPath <em>Xml Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Path</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getXmlPath()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_XmlPath()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(13);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlLocation <em>Xml Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Location</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#isXmlLocation()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EAttribute getEXmlElement_XmlLocation()
	{
		return (EAttribute)eXmlElementEClass.getEStructuralFeatures().get(14);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getXmlAbstractNullPolicy <em>Xml Abstract Null Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Abstract Null Policy</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getXmlAbstractNullPolicy()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EReference getEXmlElement_XmlAbstractNullPolicy()
	{
		return (EReference)eXmlElementEClass.getEStructuralFeatures().get(15);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getXmlElementWrapper <em>Xml Element Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Element Wrapper</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getXmlElementWrapper()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EReference getEXmlElement_XmlElementWrapper()
	{
		return (EReference)eXmlElementEClass.getEStructuralFeatures().get(16);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getXmlMap <em>Xml Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Map</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getXmlMap()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EReference getEXmlElement_XmlMap()
	{
		return (EReference)eXmlElementEClass.getEStructuralFeatures().get(17);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getXmlSchemaType <em>Xml Schema Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Schema Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement#getXmlSchemaType()
	 * @see #getEXmlElement()
	 * @generated
	 */
	public EReference getEXmlElement_XmlSchemaType()
	{
		return (EReference)eXmlElementEClass.getEStructuralFeatures().get(18);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl <em>EXml Element Decl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Element Decl</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl
	 * @generated
	 */
	public EClass getEXmlElementDecl()
	{
		return eXmlElementDeclEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getJavaMethod <em>Java Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Java Method</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getJavaMethod()
	 * @see #getEXmlElementDecl()
	 * @generated
	 */
	public EAttribute getEXmlElementDecl_JavaMethod()
	{
		return (EAttribute)eXmlElementDeclEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getName()
	 * @see #getEXmlElementDecl()
	 * @generated
	 */
	public EAttribute getEXmlElementDecl_Name()
	{
		return (EAttribute)eXmlElementDeclEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getNamespace()
	 * @see #getEXmlElementDecl()
	 * @generated
	 */
	public EAttribute getEXmlElementDecl_Namespace()
	{
		return (EAttribute)eXmlElementDeclEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getDefaultValue <em>Default Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Value</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getDefaultValue()
	 * @see #getEXmlElementDecl()
	 * @generated
	 */
	public EAttribute getEXmlElementDecl_DefaultValue()
	{
		return (EAttribute)eXmlElementDeclEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getScope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scope</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getScope()
	 * @see #getEXmlElementDecl()
	 * @generated
	 */
	public EAttribute getEXmlElementDecl_Scope()
	{
		return (EAttribute)eXmlElementDeclEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getSubstitutionHeadName <em>Substitution Head Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Substitution Head Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getSubstitutionHeadName()
	 * @see #getEXmlElementDecl()
	 * @generated
	 */
	public EAttribute getEXmlElementDecl_SubstitutionHeadName()
	{
		return (EAttribute)eXmlElementDeclEClass.getEStructuralFeatures().get(5);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getSubstitutionHeadNamespace <em>Substitution Head Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Substitution Head Namespace</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getSubstitutionHeadNamespace()
	 * @see #getEXmlElementDecl()
	 * @generated
	 */
	public EAttribute getEXmlElementDecl_SubstitutionHeadNamespace()
	{
		return (EAttribute)eXmlElementDeclEClass.getEStructuralFeatures().get(6);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getType()
	 * @see #getEXmlElementDecl()
	 * @generated
	 */
	public EAttribute getEXmlElementDecl_Type()
	{
		return (EAttribute)eXmlElementDeclEClass.getEStructuralFeatures().get(7);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef <em>EXml Element Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Element Ref</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef
	 * @generated
	 */
	public EClass getEXmlElementRef()
	{
		return eXmlElementRefEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef#getName()
	 * @see #getEXmlElementRef()
	 * @generated
	 */
	public EAttribute getEXmlElementRef_Name()
	{
		return (EAttribute)eXmlElementRefEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef#getNamespace()
	 * @see #getEXmlElementRef()
	 * @generated
	 */
	public EAttribute getEXmlElementRef_Namespace()
	{
		return (EAttribute)eXmlElementRefEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef#isXmlMixed <em>Xml Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Mixed</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef#isXmlMixed()
	 * @see #getEXmlElementRef()
	 * @generated
	 */
	public EAttribute getEXmlElementRef_XmlMixed()
	{
		return (EAttribute)eXmlElementRefEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef#getXmlElementWrapper <em>Xml Element Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Element Wrapper</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef#getXmlElementWrapper()
	 * @see #getEXmlElementRef()
	 * @generated
	 */
	public EReference getEXmlElementRef_XmlElementWrapper()
	{
		return (EReference)eXmlElementRefEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs <em>EXml Element Refs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Element Refs</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs
	 * @generated
	 */
	public EClass getEXmlElementRefs()
	{
		return eXmlElementRefsEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#isXmlMixed <em>Xml Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Mixed</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#isXmlMixed()
	 * @see #getEXmlElementRefs()
	 * @generated
	 */
	public EAttribute getEXmlElementRefs_XmlMixed()
	{
		return (EAttribute)eXmlElementRefsEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#getXmlElementRefs <em>Xml Element Refs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xml Element Refs</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#getXmlElementRefs()
	 * @see #getEXmlElementRefs()
	 * @generated
	 */
	public EReference getEXmlElementRefs_XmlElementRefs()
	{
		return (EReference)eXmlElementRefsEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#getXmlElementWrapper <em>Xml Element Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Element Wrapper</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs#getXmlElementWrapper()
	 * @see #getEXmlElementRefs()
	 * @generated
	 */
	public EReference getEXmlElementRefs_XmlElementWrapper()
	{
		return (EReference)eXmlElementRefsEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements <em>EXml Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Elements</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements
	 * @generated
	 */
	public EClass getEXmlElements()
	{
		return eXmlElementsEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements#isXmlIdRef <em>Xml Id Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Id Ref</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements#isXmlIdRef()
	 * @see #getEXmlElements()
	 * @generated
	 */
	public EAttribute getEXmlElements_XmlIdRef()
	{
		return (EAttribute)eXmlElementsEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements#isXmlList <em>Xml List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml List</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements#isXmlList()
	 * @see #getEXmlElements()
	 * @generated
	 */
	public EAttribute getEXmlElements_XmlList()
	{
		return (EAttribute)eXmlElementsEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements#getXmlElements <em>Xml Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xml Elements</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements#getXmlElements()
	 * @see #getEXmlElements()
	 * @generated
	 */
	public EReference getEXmlElements_XmlElements()
	{
		return (EReference)eXmlElementsEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements#getXmlElementWrapper <em>Xml Element Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Element Wrapper</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements#getXmlElementWrapper()
	 * @see #getEXmlElements()
	 * @generated
	 */
	public EReference getEXmlElements_XmlElementWrapper()
	{
		return (EReference)eXmlElementsEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements#getXmlJoinNodes <em>Xml Join Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xml Join Nodes</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements#getXmlJoinNodes()
	 * @see #getEXmlElements()
	 * @generated
	 */
	public EReference getEXmlElements_XmlJoinNodes()
	{
		return (EReference)eXmlElementsEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper <em>EXml Element Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Element Wrapper</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper
	 * @generated
	 */
	public EClass getEXmlElementWrapper()
	{
		return eXmlElementWrapperEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper#getName()
	 * @see #getEXmlElementWrapper()
	 * @generated
	 */
	public EAttribute getEXmlElementWrapper_Name()
	{
		return (EAttribute)eXmlElementWrapperEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper#getNamespace()
	 * @see #getEXmlElementWrapper()
	 * @generated
	 */
	public EAttribute getEXmlElementWrapper_Namespace()
	{
		return (EAttribute)eXmlElementWrapperEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper#isNillable <em>Nillable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nillable</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper#isNillable()
	 * @see #getEXmlElementWrapper()
	 * @generated
	 */
	public EAttribute getEXmlElementWrapper_Nillable()
	{
		return (EAttribute)eXmlElementWrapperEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper#isRequired <em>Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Required</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper#isRequired()
	 * @see #getEXmlElementWrapper()
	 * @generated
	 */
	public EAttribute getEXmlElementWrapper_Required()
	{
		return (EAttribute)eXmlElementWrapperEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum <em>EXml Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Enum</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum
	 * @generated
	 */
	public EClass getEXmlEnum()
	{
		return eXmlEnumEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum#getJavaEnum <em>Java Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Java Enum</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum#getJavaEnum()
	 * @see #getEXmlEnum()
	 * @generated
	 */
	public EAttribute getEXmlEnum_JavaEnum()
	{
		return (EAttribute)eXmlEnumEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum#getValue()
	 * @see #getEXmlEnum()
	 * @generated
	 */
	public EAttribute getEXmlEnum_Value()
	{
		return (EAttribute)eXmlEnumEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum#getXmlEnumValues <em>Xml Enum Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xml Enum Values</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum#getXmlEnumValues()
	 * @see #getEXmlEnum()
	 * @generated
	 */
	public EReference getEXmlEnum_XmlEnumValues()
	{
		return (EReference)eXmlEnumEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnumValue <em>EXml Enum Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Enum Value</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnumValue
	 * @generated
	 */
	public EClass getEXmlEnumValue()
	{
		return eXmlEnumValueEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnumValue#getJavaEnumValue <em>Java Enum Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Java Enum Value</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnumValue#getJavaEnumValue()
	 * @see #getEXmlEnumValue()
	 * @generated
	 */
	public EAttribute getEXmlEnumValue_JavaEnumValue()
	{
		return (EAttribute)eXmlEnumValueEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlInverseReference <em>EXml Inverse Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Inverse Reference</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlInverseReference
	 * @generated
	 */
	public EClass getEXmlInverseReference()
	{
		return eXmlInverseReferenceEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlInverseReference#getMappedBy <em>Mapped By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mapped By</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlInverseReference#getMappedBy()
	 * @see #getEXmlInverseReference()
	 * @generated
	 */
	public EAttribute getEXmlInverseReference_MappedBy()
	{
		return (EAttribute)eXmlInverseReferenceEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetNullPolicy <em>EXml Is Set Null Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Is Set Null Policy</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetNullPolicy
	 * @generated
	 */
	public EClass getEXmlIsSetNullPolicy()
	{
		return eXmlIsSetNullPolicyEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetNullPolicy#getIsSetMethodName <em>Is Set Method Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Set Method Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetNullPolicy#getIsSetMethodName()
	 * @see #getEXmlIsSetNullPolicy()
	 * @generated
	 */
	public EAttribute getEXmlIsSetNullPolicy_IsSetMethodName()
	{
		return (EAttribute)eXmlIsSetNullPolicyEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetNullPolicy#getXmlIsSetParameters <em>Xml Is Set Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xml Is Set Parameters</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetNullPolicy#getXmlIsSetParameters()
	 * @see #getEXmlIsSetNullPolicy()
	 * @generated
	 */
	public EReference getEXmlIsSetNullPolicy_XmlIsSetParameters()
	{
		return (EReference)eXmlIsSetNullPolicyEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetParameter <em>EXml Is Set Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Is Set Parameter</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetParameter
	 * @generated
	 */
	public EClass getEXmlIsSetParameter()
	{
		return eXmlIsSetParameterEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetParameter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetParameter#getValue()
	 * @see #getEXmlIsSetParameter()
	 * @generated
	 */
	public EAttribute getEXmlIsSetParameter_Value()
	{
		return (EAttribute)eXmlIsSetParameterEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetParameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetParameter#getType()
	 * @see #getEXmlIsSetParameter()
	 * @generated
	 */
	public EAttribute getEXmlIsSetParameter_Type()
	{
		return (EAttribute)eXmlIsSetParameterEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter <em>EXml Java Type Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Java Type Adapter</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter
	 * @generated
	 */
	public EClass getEXmlJavaTypeAdapter()
	{
		return eXmlJavaTypeAdapterEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter#getValue()
	 * @see #getEXmlJavaTypeAdapter()
	 * @generated
	 */
	public EAttribute getEXmlJavaTypeAdapter_Value()
	{
		return (EAttribute)eXmlJavaTypeAdapterEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter#getType()
	 * @see #getEXmlJavaTypeAdapter()
	 * @generated
	 */
	public EAttribute getEXmlJavaTypeAdapter_Type()
	{
		return (EAttribute)eXmlJavaTypeAdapterEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter#getValueType <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter#getValueType()
	 * @see #getEXmlJavaTypeAdapter()
	 * @generated
	 */
	public EAttribute getEXmlJavaTypeAdapter_ValueType()
	{
		return (EAttribute)eXmlJavaTypeAdapterEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode <em>EXml Join Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Join Node</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode
	 * @generated
	 */
	public EClass getEXmlJoinNode()
	{
		return eXmlJoinNodeEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode#getXmlPath <em>Xml Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Path</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode#getXmlPath()
	 * @see #getEXmlJoinNode()
	 * @generated
	 */
	public EAttribute getEXmlJoinNode_XmlPath()
	{
		return (EAttribute)eXmlJoinNodeEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode#getReferencedXmlPath <em>Referenced Xml Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Referenced Xml Path</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode#getReferencedXmlPath()
	 * @see #getEXmlJoinNode()
	 * @generated
	 */
	public EAttribute getEXmlJoinNode_ReferencedXmlPath()
	{
		return (EAttribute)eXmlJoinNodeEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNodes <em>EXml Join Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Join Nodes</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNodes
	 * @generated
	 */
	public EClass getEXmlJoinNodes()
	{
		return eXmlJoinNodesEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNodes#getXmlJoinNodes <em>Xml Join Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xml Join Nodes</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNodes#getXmlJoinNodes()
	 * @see #getEXmlJoinNodes()
	 * @generated
	 */
	public EReference getEXmlJoinNodes_XmlJoinNodes()
	{
		return (EReference)eXmlJoinNodesEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMap <em>EXml Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Map</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMap
	 * @generated
	 */
	public EClass getEXmlMap()
	{
		return eXmlMapEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMap#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMap#getKey()
	 * @see #getEXmlMap()
	 * @generated
	 */
	public EAttribute getEXmlMap_Key()
	{
		return (EAttribute)eXmlMapEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMap#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMap#getValue()
	 * @see #getEXmlMap()
	 * @generated
	 */
	public EAttribute getEXmlMap_Value()
	{
		return (EAttribute)eXmlMapEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNullPolicy <em>EXml Null Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Null Policy</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNullPolicy
	 * @generated
	 */
	public EClass getEXmlNullPolicy()
	{
		return eXmlNullPolicyEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNullPolicy#isIsSetPerformedForAbsentNode <em>Is Set Performed For Absent Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Set Performed For Absent Node</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNullPolicy#isIsSetPerformedForAbsentNode()
	 * @see #getEXmlNullPolicy()
	 * @generated
	 */
	public EAttribute getEXmlNullPolicy_IsSetPerformedForAbsentNode()
	{
		return (EAttribute)eXmlNullPolicyEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNs <em>EXml Ns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Ns</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNs
	 * @generated
	 */
	public EClass getEXmlNs()
	{
		return eXmlNsEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNs#getNamespaceUri <em>Namespace Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace Uri</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNs#getNamespaceUri()
	 * @see #getEXmlNs()
	 * @generated
	 */
	public EAttribute getEXmlNs_NamespaceUri()
	{
		return (EAttribute)eXmlNsEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNs#getPrefix <em>Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Prefix</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNs#getPrefix()
	 * @see #getEXmlNs()
	 * @generated
	 */
	public EAttribute getEXmlNs_Prefix()
	{
		return (EAttribute)eXmlNsEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlProperty <em>EXml Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Property</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlProperty
	 * @generated
	 */
	public EClass getEXmlProperty()
	{
		return eXmlPropertyEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlProperty#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlProperty#getName()
	 * @see #getEXmlProperty()
	 * @generated
	 */
	public EAttribute getEXmlProperty_Name()
	{
		return (EAttribute)eXmlPropertyEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlProperty#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlProperty#getValue()
	 * @see #getEXmlProperty()
	 * @generated
	 */
	public EAttribute getEXmlProperty_Value()
	{
		return (EAttribute)eXmlPropertyEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlProperty#getValueType <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlProperty#getValueType()
	 * @see #getEXmlProperty()
	 * @generated
	 */
	public EAttribute getEXmlProperty_ValueType()
	{
		return (EAttribute)eXmlPropertyEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlReadTransformer <em>EXml Read Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Read Transformer</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlReadTransformer
	 * @generated
	 */
	public EClass getEXmlReadTransformer()
	{
		return eXmlReadTransformerEClass;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRegistry <em>EXml Registry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Registry</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRegistry
	 * @generated
	 */
	public EClass getEXmlRegistry()
	{
		return eXmlRegistryEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRegistry#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRegistry#getName()
	 * @see #getEXmlRegistry()
	 * @generated
	 */
	public EAttribute getEXmlRegistry_Name()
	{
		return (EAttribute)eXmlRegistryEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRegistry#getXmlElementDecls <em>Xml Element Decls</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xml Element Decls</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRegistry#getXmlElementDecls()
	 * @see #getEXmlRegistry()
	 * @generated
	 */
	public EReference getEXmlRegistry_XmlElementDecls()
	{
		return (EReference)eXmlRegistryEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRootElement <em>EXml Root Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Root Element</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRootElement
	 * @generated
	 */
	public EClass getEXmlRootElement()
	{
		return eXmlRootElementEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRootElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRootElement#getName()
	 * @see #getEXmlRootElement()
	 * @generated
	 */
	public EAttribute getEXmlRootElement_Name()
	{
		return (EAttribute)eXmlRootElementEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRootElement#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRootElement#getNamespace()
	 * @see #getEXmlRootElement()
	 * @generated
	 */
	public EAttribute getEXmlRootElement_Namespace()
	{
		return (EAttribute)eXmlRootElementEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema <em>EXml Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Schema</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema
	 * @generated
	 */
	public EClass getEXmlSchema()
	{
		return eXmlSchemaEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getAttributeFormDefault <em>Attribute Form Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Form Default</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getAttributeFormDefault()
	 * @see #getEXmlSchema()
	 * @generated
	 */
	public EAttribute getEXmlSchema_AttributeFormDefault()
	{
		return (EAttribute)eXmlSchemaEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getElementFormDefault <em>Element Form Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Element Form Default</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getElementFormDefault()
	 * @see #getEXmlSchema()
	 * @generated
	 */
	public EAttribute getEXmlSchema_ElementFormDefault()
	{
		return (EAttribute)eXmlSchemaEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getLocation()
	 * @see #getEXmlSchema()
	 * @generated
	 */
	public EAttribute getEXmlSchema_Location()
	{
		return (EAttribute)eXmlSchemaEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getNamespace()
	 * @see #getEXmlSchema()
	 * @generated
	 */
	public EAttribute getEXmlSchema_Namespace()
	{
		return (EAttribute)eXmlSchemaEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getXmlns <em>Xmlns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xmlns</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getXmlns()
	 * @see #getEXmlSchema()
	 * @generated
	 */
	public EReference getEXmlSchema_Xmlns()
	{
		return (EReference)eXmlSchemaEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchemaType <em>EXml Schema Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Schema Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchemaType
	 * @generated
	 */
	public EClass getEXmlSchemaType()
	{
		return eXmlSchemaTypeEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchemaType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchemaType#getName()
	 * @see #getEXmlSchemaType()
	 * @generated
	 */
	public EAttribute getEXmlSchemaType_Name()
	{
		return (EAttribute)eXmlSchemaTypeEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchemaType#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchemaType#getNamespace()
	 * @see #getEXmlSchemaType()
	 * @generated
	 */
	public EAttribute getEXmlSchemaType_Namespace()
	{
		return (EAttribute)eXmlSchemaTypeEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchemaType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchemaType#getType()
	 * @see #getEXmlSchemaType()
	 * @generated
	 */
	public EAttribute getEXmlSchemaType_Type()
	{
		return (EAttribute)eXmlSchemaTypeEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransformation <em>EXml Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Transformation</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransformation
	 * @generated
	 */
	public EClass getEXmlTransformation()
	{
		return eXmlTransformationEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransformation#isOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransformation#isOptional()
	 * @see #getEXmlTransformation()
	 * @generated
	 */
	public EAttribute getEXmlTransformation_Optional()
	{
		return (EAttribute)eXmlTransformationEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransformation#getXmlReadTransformer <em>Xml Read Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Read Transformer</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransformation#getXmlReadTransformer()
	 * @see #getEXmlTransformation()
	 * @generated
	 */
	public EReference getEXmlTransformation_XmlReadTransformer()
	{
		return (EReference)eXmlTransformationEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransformation#getXmlWriteTransformers <em>Xml Write Transformers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Xml Write Transformers</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransformation#getXmlWriteTransformers()
	 * @see #getEXmlTransformation()
	 * @generated
	 */
	public EReference getEXmlTransformation_XmlWriteTransformers()
	{
		return (EReference)eXmlTransformationEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransient <em>EXml Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Transient</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransient
	 * @generated
	 */
	public EClass getEXmlTransient()
	{
		return eXmlTransientEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransient#isXmlLocation <em>Xml Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Location</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransient#isXmlLocation()
	 * @see #getEXmlTransient()
	 * @generated
	 */
	public EAttribute getEXmlTransient_XmlLocation()
	{
		return (EAttribute)eXmlTransientEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType <em>EXml Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType
	 * @generated
	 */
	public EClass getEXmlType()
	{
		return eXmlTypeEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getName()
	 * @see #getEXmlType()
	 * @generated
	 */
	public EAttribute getEXmlType_Name()
	{
		return (EAttribute)eXmlTypeEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getNamespace()
	 * @see #getEXmlType()
	 * @generated
	 */
	public EAttribute getEXmlType_Namespace()
	{
		return (EAttribute)eXmlTypeEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getFactoryClass <em>Factory Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Factory Class</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getFactoryClass()
	 * @see #getEXmlType()
	 * @generated
	 */
	public EAttribute getEXmlType_FactoryClass()
	{
		return (EAttribute)eXmlTypeEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getFactoryMethod <em>Factory Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Factory Method</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getFactoryMethod()
	 * @see #getEXmlType()
	 * @generated
	 */
	public EAttribute getEXmlType_FactoryMethod()
	{
		return (EAttribute)eXmlTypeEClass.getEStructuralFeatures().get(3);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getPropOrder <em>Prop Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Prop Order</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getPropOrder()
	 * @see #getEXmlType()
	 * @generated
	 */
	public EAttribute getEXmlType_PropOrder()
	{
		return (EAttribute)eXmlTypeEClass.getEStructuralFeatures().get(4);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue <em>EXml Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Value</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue
	 * @generated
	 */
	public EClass getEXmlValue()
	{
		return eXmlValueEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#isCdata <em>Cdata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cdata</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#isCdata()
	 * @see #getEXmlValue()
	 * @generated
	 */
	public EAttribute getEXmlValue_Cdata()
	{
		return (EAttribute)eXmlValueEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#getXmlAbstractNullPolicy <em>Xml Abstract Null Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Xml Abstract Null Policy</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue#getXmlAbstractNullPolicy()
	 * @see #getEXmlValue()
	 * @generated
	 */
	public EReference getEXmlValue_XmlAbstractNullPolicy()
	{
		return (EReference)eXmlValueEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods <em>EXml Virtual Access Methods</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Virtual Access Methods</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods
	 * @generated
	 */
	public EClass getEXmlVirtualAccessMethods()
	{
		return eXmlVirtualAccessMethodsEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods#getGetMethod <em>Get Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Get Method</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods#getGetMethod()
	 * @see #getEXmlVirtualAccessMethods()
	 * @generated
	 */
	public EAttribute getEXmlVirtualAccessMethods_GetMethod()
	{
		return (EAttribute)eXmlVirtualAccessMethodsEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods#getSetMethod <em>Set Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Set Method</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods#getSetMethod()
	 * @see #getEXmlVirtualAccessMethods()
	 * @generated
	 */
	public EAttribute getEXmlVirtualAccessMethods_SetMethod()
	{
		return (EAttribute)eXmlVirtualAccessMethodsEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods#getSchema()
	 * @see #getEXmlVirtualAccessMethods()
	 * @generated
	 */
	public EAttribute getEXmlVirtualAccessMethods_Schema()
	{
		return (EAttribute)eXmlVirtualAccessMethodsEClass.getEStructuralFeatures().get(2);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlWriteTransformer <em>EXml Write Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EXml Write Transformer</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlWriteTransformer
	 * @generated
	 */
	public EClass getEXmlWriteTransformer()
	{
		return eXmlWriteTransformerEClass;
	}


	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlWriteTransformer#getXmlPath <em>Xml Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Path</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlWriteTransformer#getXmlPath()
	 * @see #getEXmlWriteTransformer()
	 * @generated
	 */
	public EAttribute getEXmlWriteTransformer_XmlPath()
	{
		return (EAttribute)eXmlWriteTransformerEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessOrder <em>EXml Access Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>EXml Access Order</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessOrder
	 * @generated
	 */
	public EEnum getEXmlAccessOrder()
	{
		return eXmlAccessOrderEEnum;
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType <em>EXml Access Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>EXml Access Type</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType
	 * @generated
	 */
	public EEnum getEXmlAccessType()
	{
		return eXmlAccessTypeEEnum;
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMarshalNullRepresentation <em>EXml Marshal Null Representation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>EXml Marshal Null Representation</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMarshalNullRepresentation
	 * @generated
	 */
	public EEnum getEXmlMarshalNullRepresentation()
	{
		return eXmlMarshalNullRepresentationEEnum;
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNsForm <em>EXml Ns Form</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>EXml Ns Form</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNsForm
	 * @generated
	 */
	public EEnum getEXmlNsForm()
	{
		return eXmlNsFormEEnum;
	}


	/**
	 * Returns the meta object for enum '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethodsSchema <em>EXml Virtual Access Methods Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>EXml Virtual Access Methods Schema</em>'.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethodsSchema
	 * @generated
	 */
	public EEnum getEXmlVirtualAccessMethodsSchema()
	{
		return eXmlVirtualAccessMethodsSchemaEEnum;
	}


	/**
	 * Returns the meta object for data type '{@link java.util.List <em>EProp Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>EProp Order</em>'.
	 * @see java.util.List
	 * @model instanceClass="java.util.List"
	 *        extendedMetaData="itemType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	public EDataType getEPropOrder()
	{
		return ePropOrderEDataType;
	}


	/**
	 * Returns the meta object for data type '{@link java.util.List <em>EXml See Also</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>EXml See Also</em>'.
	 * @see java.util.List
	 * @model instanceClass="java.util.List"
	 *        extendedMetaData="itemType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	public EDataType getEXmlSeeAlso()
	{
		return eXmlSeeAlsoEDataType;
	}


	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public OxmFactory getOxmFactory()
	{
		return (OxmFactory)getEFactoryInstance();
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
		eAbstractTypeMappingEClass = createEClass(EABSTRACT_TYPE_MAPPING);
		createEAttribute(eAbstractTypeMappingEClass, EABSTRACT_TYPE_MAPPING__XML_TRANSIENT);
		createEReference(eAbstractTypeMappingEClass, EABSTRACT_TYPE_MAPPING__XML_TYPE);
		createEReference(eAbstractTypeMappingEClass, EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT);
		createEAttribute(eAbstractTypeMappingEClass, EABSTRACT_TYPE_MAPPING__XML_SEE_ALSO);

		eAbstractXmlNullPolicyEClass = createEClass(EABSTRACT_XML_NULL_POLICY);
		createEAttribute(eAbstractXmlNullPolicyEClass, EABSTRACT_XML_NULL_POLICY__XSI_NIL_REPRESENTS_NULL);
		createEAttribute(eAbstractXmlNullPolicyEClass, EABSTRACT_XML_NULL_POLICY__EMPTY_NODE_REPRESENTS_NULL);
		createEAttribute(eAbstractXmlNullPolicyEClass, EABSTRACT_XML_NULL_POLICY__NULL_REPRESENTATION_FOR_XML);

		eAbstractXmlTransformerEClass = createEClass(EABSTRACT_XML_TRANSFORMER);
		createEAttribute(eAbstractXmlTransformerEClass, EABSTRACT_XML_TRANSFORMER__METHOD);
		createEAttribute(eAbstractXmlTransformerEClass, EABSTRACT_XML_TRANSFORMER__TRANSFORMER_CLASS);

		eAccessibleJavaAttributeEClass = createEClass(EACCESSIBLE_JAVA_ATTRIBUTE);
		createEReference(eAccessibleJavaAttributeEClass, EACCESSIBLE_JAVA_ATTRIBUTE__XML_ACCESS_METHODS);

		eAdaptableJavaAttributeEClass = createEClass(EADAPTABLE_JAVA_ATTRIBUTE);
		createEReference(eAdaptableJavaAttributeEClass, EADAPTABLE_JAVA_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER);

		eContainerJavaAttributeEClass = createEClass(ECONTAINER_JAVA_ATTRIBUTE);
		createEAttribute(eContainerJavaAttributeEClass, ECONTAINER_JAVA_ATTRIBUTE__CONTAINER_TYPE);

		eJavaAttributeEClass = createEClass(EJAVA_ATTRIBUTE);
		createEAttribute(eJavaAttributeEClass, EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE);
		createEAttribute(eJavaAttributeEClass, EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE);

		eJavaTypeEClass = createEClass(EJAVA_TYPE);
		createEAttribute(eJavaTypeEClass, EJAVA_TYPE__NAME);
		createEAttribute(eJavaTypeEClass, EJAVA_TYPE__SUPER_TYPE);
		createEAttribute(eJavaTypeEClass, EJAVA_TYPE__XML_ACCESSOR_ORDER);
		createEAttribute(eJavaTypeEClass, EJAVA_TYPE__XML_ACCESSOR_TYPE);
		createEAttribute(eJavaTypeEClass, EJAVA_TYPE__XML_CUSTOMIZER);
		createEAttribute(eJavaTypeEClass, EJAVA_TYPE__XML_DISCRIMINATOR_NODE);
		createEAttribute(eJavaTypeEClass, EJAVA_TYPE__XML_DISCRIMINATOR_VALUE);
		createEAttribute(eJavaTypeEClass, EJAVA_TYPE__XML_INLINE_BINARY_DATA);
		createEAttribute(eJavaTypeEClass, EJAVA_TYPE__XML_NAME_TRANSFORMER);
		createEReference(eJavaTypeEClass, EJAVA_TYPE__XML_VIRTUAL_ACCESS_METHODS);
		createEReference(eJavaTypeEClass, EJAVA_TYPE__XML_JAVA_TYPE_ADAPTER);
		createEReference(eJavaTypeEClass, EJAVA_TYPE__XML_CLASS_EXTRACTOR);
		createEReference(eJavaTypeEClass, EJAVA_TYPE__JAVA_ATTRIBUTES);

		ePropertyHolderEClass = createEClass(EPROPERTY_HOLDER);
		createEReference(ePropertyHolderEClass, EPROPERTY_HOLDER__XML_PROPERTIES);

		eReadWriteJavaAttributeEClass = createEClass(EREAD_WRITE_JAVA_ATTRIBUTE);
		createEAttribute(eReadWriteJavaAttributeEClass, EREAD_WRITE_JAVA_ATTRIBUTE__READ_ONLY);
		createEAttribute(eReadWriteJavaAttributeEClass, EREAD_WRITE_JAVA_ATTRIBUTE__WRITE_ONLY);

		eTypedJavaAttributeEClass = createEClass(ETYPED_JAVA_ATTRIBUTE);
		createEAttribute(eTypedJavaAttributeEClass, ETYPED_JAVA_ATTRIBUTE__TYPE);

		eXmlAccessMethodsEClass = createEClass(EXML_ACCESS_METHODS);
		createEAttribute(eXmlAccessMethodsEClass, EXML_ACCESS_METHODS__GET_METHOD);
		createEAttribute(eXmlAccessMethodsEClass, EXML_ACCESS_METHODS__SET_METHOD);

		eXmlAnyAttributeEClass = createEClass(EXML_ANY_ATTRIBUTE);
		createEAttribute(eXmlAnyAttributeEClass, EXML_ANY_ATTRIBUTE__XML_PATH);

		eXmlAnyElementEClass = createEClass(EXML_ANY_ELEMENT);
		createEAttribute(eXmlAnyElementEClass, EXML_ANY_ELEMENT__XML_MIXED);
		createEAttribute(eXmlAnyElementEClass, EXML_ANY_ELEMENT__LAX);
		createEAttribute(eXmlAnyElementEClass, EXML_ANY_ELEMENT__DOM_HANDLER);
		createEAttribute(eXmlAnyElementEClass, EXML_ANY_ELEMENT__XML_PATH);
		createEReference(eXmlAnyElementEClass, EXML_ANY_ELEMENT__XML_ELEMENT_REFS);

		eXmlAttributeEClass = createEClass(EXML_ATTRIBUTE);
		createEAttribute(eXmlAttributeEClass, EXML_ATTRIBUTE__NAME);
		createEAttribute(eXmlAttributeEClass, EXML_ATTRIBUTE__NAMESPACE);
		createEAttribute(eXmlAttributeEClass, EXML_ATTRIBUTE__REQUIRED);
		createEAttribute(eXmlAttributeEClass, EXML_ATTRIBUTE__XML_ID);
		createEAttribute(eXmlAttributeEClass, EXML_ATTRIBUTE__XML_ID_REF);
		createEAttribute(eXmlAttributeEClass, EXML_ATTRIBUTE__XML_KEY);
		createEAttribute(eXmlAttributeEClass, EXML_ATTRIBUTE__XML_LIST);
		createEAttribute(eXmlAttributeEClass, EXML_ATTRIBUTE__XML_INLINE_BINARY_DATA);
		createEAttribute(eXmlAttributeEClass, EXML_ATTRIBUTE__XML_ATTACHMENT_REF);
		createEAttribute(eXmlAttributeEClass, EXML_ATTRIBUTE__XML_MIME_TYPE);
		createEAttribute(eXmlAttributeEClass, EXML_ATTRIBUTE__XML_PATH);
		createEReference(eXmlAttributeEClass, EXML_ATTRIBUTE__XML_ABSTRACT_NULL_POLICY);
		createEReference(eXmlAttributeEClass, EXML_ATTRIBUTE__XML_SCHEMA_TYPE);

		eXmlBindingsEClass = createEClass(EXML_BINDINGS);
		createEAttribute(eXmlBindingsEClass, EXML_BINDINGS__XML_ACCESSOR_TYPE);
		createEAttribute(eXmlBindingsEClass, EXML_BINDINGS__XML_ACCESSOR_ORDER);
		createEAttribute(eXmlBindingsEClass, EXML_BINDINGS__XML_MAPPING_METADATA_COMPLETE);
		createEAttribute(eXmlBindingsEClass, EXML_BINDINGS__PACKAGE_NAME);
		createEAttribute(eXmlBindingsEClass, EXML_BINDINGS__XML_NAME_TRANSFORMER);
		createEReference(eXmlBindingsEClass, EXML_BINDINGS__XML_SCHEMA);
		createEReference(eXmlBindingsEClass, EXML_BINDINGS__XML_SCHEMA_TYPE);
		createEReference(eXmlBindingsEClass, EXML_BINDINGS__XML_SCHEMA_TYPES);
		createEReference(eXmlBindingsEClass, EXML_BINDINGS__XML_JAVA_TYPE_ADAPTERS);
		createEReference(eXmlBindingsEClass, EXML_BINDINGS__XML_REGISTRIES);
		createEReference(eXmlBindingsEClass, EXML_BINDINGS__XML_ENUMS);
		createEReference(eXmlBindingsEClass, EXML_BINDINGS__JAVA_TYPES);

		eXmlClassExtractorEClass = createEClass(EXML_CLASS_EXTRACTOR);
		createEAttribute(eXmlClassExtractorEClass, EXML_CLASS_EXTRACTOR__CLASS_NAME);

		eXmlElementEClass = createEClass(EXML_ELEMENT);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__NAME);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__NAMESPACE);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__DEFAULT_VALUE);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__NILLABLE);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__REQUIRED);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__XML_ID);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__XML_ID_REF);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__XML_KEY);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__XML_LIST);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__XML_INLINE_BINARY_DATA);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__XML_ATTACHMENT_REF);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__XML_MIME_TYPE);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__CDATA);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__XML_PATH);
		createEAttribute(eXmlElementEClass, EXML_ELEMENT__XML_LOCATION);
		createEReference(eXmlElementEClass, EXML_ELEMENT__XML_ABSTRACT_NULL_POLICY);
		createEReference(eXmlElementEClass, EXML_ELEMENT__XML_ELEMENT_WRAPPER);
		createEReference(eXmlElementEClass, EXML_ELEMENT__XML_MAP);
		createEReference(eXmlElementEClass, EXML_ELEMENT__XML_SCHEMA_TYPE);

		eXmlElementDeclEClass = createEClass(EXML_ELEMENT_DECL);
		createEAttribute(eXmlElementDeclEClass, EXML_ELEMENT_DECL__JAVA_METHOD);
		createEAttribute(eXmlElementDeclEClass, EXML_ELEMENT_DECL__NAME);
		createEAttribute(eXmlElementDeclEClass, EXML_ELEMENT_DECL__NAMESPACE);
		createEAttribute(eXmlElementDeclEClass, EXML_ELEMENT_DECL__DEFAULT_VALUE);
		createEAttribute(eXmlElementDeclEClass, EXML_ELEMENT_DECL__SCOPE);
		createEAttribute(eXmlElementDeclEClass, EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME);
		createEAttribute(eXmlElementDeclEClass, EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE);
		createEAttribute(eXmlElementDeclEClass, EXML_ELEMENT_DECL__TYPE);

		eXmlElementRefEClass = createEClass(EXML_ELEMENT_REF);
		createEAttribute(eXmlElementRefEClass, EXML_ELEMENT_REF__NAME);
		createEAttribute(eXmlElementRefEClass, EXML_ELEMENT_REF__NAMESPACE);
		createEAttribute(eXmlElementRefEClass, EXML_ELEMENT_REF__XML_MIXED);
		createEReference(eXmlElementRefEClass, EXML_ELEMENT_REF__XML_ELEMENT_WRAPPER);

		eXmlElementRefsEClass = createEClass(EXML_ELEMENT_REFS);
		createEAttribute(eXmlElementRefsEClass, EXML_ELEMENT_REFS__XML_MIXED);
		createEReference(eXmlElementRefsEClass, EXML_ELEMENT_REFS__XML_ELEMENT_REFS);
		createEReference(eXmlElementRefsEClass, EXML_ELEMENT_REFS__XML_ELEMENT_WRAPPER);

		eXmlElementsEClass = createEClass(EXML_ELEMENTS);
		createEAttribute(eXmlElementsEClass, EXML_ELEMENTS__XML_ID_REF);
		createEAttribute(eXmlElementsEClass, EXML_ELEMENTS__XML_LIST);
		createEReference(eXmlElementsEClass, EXML_ELEMENTS__XML_ELEMENTS);
		createEReference(eXmlElementsEClass, EXML_ELEMENTS__XML_ELEMENT_WRAPPER);
		createEReference(eXmlElementsEClass, EXML_ELEMENTS__XML_JOIN_NODES);

		eXmlElementWrapperEClass = createEClass(EXML_ELEMENT_WRAPPER);
		createEAttribute(eXmlElementWrapperEClass, EXML_ELEMENT_WRAPPER__NAME);
		createEAttribute(eXmlElementWrapperEClass, EXML_ELEMENT_WRAPPER__NAMESPACE);
		createEAttribute(eXmlElementWrapperEClass, EXML_ELEMENT_WRAPPER__NILLABLE);
		createEAttribute(eXmlElementWrapperEClass, EXML_ELEMENT_WRAPPER__REQUIRED);

		eXmlEnumEClass = createEClass(EXML_ENUM);
		createEAttribute(eXmlEnumEClass, EXML_ENUM__JAVA_ENUM);
		createEAttribute(eXmlEnumEClass, EXML_ENUM__VALUE);
		createEReference(eXmlEnumEClass, EXML_ENUM__XML_ENUM_VALUES);

		eXmlEnumValueEClass = createEClass(EXML_ENUM_VALUE);
		createEAttribute(eXmlEnumValueEClass, EXML_ENUM_VALUE__JAVA_ENUM_VALUE);

		eXmlInverseReferenceEClass = createEClass(EXML_INVERSE_REFERENCE);
		createEAttribute(eXmlInverseReferenceEClass, EXML_INVERSE_REFERENCE__MAPPED_BY);

		eXmlIsSetNullPolicyEClass = createEClass(EXML_IS_SET_NULL_POLICY);
		createEAttribute(eXmlIsSetNullPolicyEClass, EXML_IS_SET_NULL_POLICY__IS_SET_METHOD_NAME);
		createEReference(eXmlIsSetNullPolicyEClass, EXML_IS_SET_NULL_POLICY__XML_IS_SET_PARAMETERS);

		eXmlIsSetParameterEClass = createEClass(EXML_IS_SET_PARAMETER);
		createEAttribute(eXmlIsSetParameterEClass, EXML_IS_SET_PARAMETER__VALUE);
		createEAttribute(eXmlIsSetParameterEClass, EXML_IS_SET_PARAMETER__TYPE);

		eXmlJavaTypeAdapterEClass = createEClass(EXML_JAVA_TYPE_ADAPTER);
		createEAttribute(eXmlJavaTypeAdapterEClass, EXML_JAVA_TYPE_ADAPTER__VALUE);
		createEAttribute(eXmlJavaTypeAdapterEClass, EXML_JAVA_TYPE_ADAPTER__TYPE);
		createEAttribute(eXmlJavaTypeAdapterEClass, EXML_JAVA_TYPE_ADAPTER__VALUE_TYPE);

		eXmlJoinNodeEClass = createEClass(EXML_JOIN_NODE);
		createEAttribute(eXmlJoinNodeEClass, EXML_JOIN_NODE__XML_PATH);
		createEAttribute(eXmlJoinNodeEClass, EXML_JOIN_NODE__REFERENCED_XML_PATH);

		eXmlJoinNodesEClass = createEClass(EXML_JOIN_NODES);
		createEReference(eXmlJoinNodesEClass, EXML_JOIN_NODES__XML_JOIN_NODES);

		eXmlMapEClass = createEClass(EXML_MAP);
		createEAttribute(eXmlMapEClass, EXML_MAP__KEY);
		createEAttribute(eXmlMapEClass, EXML_MAP__VALUE);

		eXmlNullPolicyEClass = createEClass(EXML_NULL_POLICY);
		createEAttribute(eXmlNullPolicyEClass, EXML_NULL_POLICY__IS_SET_PERFORMED_FOR_ABSENT_NODE);

		eXmlNsEClass = createEClass(EXML_NS);
		createEAttribute(eXmlNsEClass, EXML_NS__NAMESPACE_URI);
		createEAttribute(eXmlNsEClass, EXML_NS__PREFIX);

		eXmlPropertyEClass = createEClass(EXML_PROPERTY);
		createEAttribute(eXmlPropertyEClass, EXML_PROPERTY__NAME);
		createEAttribute(eXmlPropertyEClass, EXML_PROPERTY__VALUE);
		createEAttribute(eXmlPropertyEClass, EXML_PROPERTY__VALUE_TYPE);

		eXmlReadTransformerEClass = createEClass(EXML_READ_TRANSFORMER);

		eXmlRegistryEClass = createEClass(EXML_REGISTRY);
		createEAttribute(eXmlRegistryEClass, EXML_REGISTRY__NAME);
		createEReference(eXmlRegistryEClass, EXML_REGISTRY__XML_ELEMENT_DECLS);

		eXmlRootElementEClass = createEClass(EXML_ROOT_ELEMENT);
		createEAttribute(eXmlRootElementEClass, EXML_ROOT_ELEMENT__NAME);
		createEAttribute(eXmlRootElementEClass, EXML_ROOT_ELEMENT__NAMESPACE);

		eXmlSchemaEClass = createEClass(EXML_SCHEMA);
		createEAttribute(eXmlSchemaEClass, EXML_SCHEMA__ATTRIBUTE_FORM_DEFAULT);
		createEAttribute(eXmlSchemaEClass, EXML_SCHEMA__ELEMENT_FORM_DEFAULT);
		createEAttribute(eXmlSchemaEClass, EXML_SCHEMA__LOCATION);
		createEAttribute(eXmlSchemaEClass, EXML_SCHEMA__NAMESPACE);
		createEReference(eXmlSchemaEClass, EXML_SCHEMA__XMLNS);

		eXmlSchemaTypeEClass = createEClass(EXML_SCHEMA_TYPE);
		createEAttribute(eXmlSchemaTypeEClass, EXML_SCHEMA_TYPE__NAME);
		createEAttribute(eXmlSchemaTypeEClass, EXML_SCHEMA_TYPE__NAMESPACE);
		createEAttribute(eXmlSchemaTypeEClass, EXML_SCHEMA_TYPE__TYPE);

		eXmlTransformationEClass = createEClass(EXML_TRANSFORMATION);
		createEAttribute(eXmlTransformationEClass, EXML_TRANSFORMATION__OPTIONAL);
		createEReference(eXmlTransformationEClass, EXML_TRANSFORMATION__XML_READ_TRANSFORMER);
		createEReference(eXmlTransformationEClass, EXML_TRANSFORMATION__XML_WRITE_TRANSFORMERS);

		eXmlTransientEClass = createEClass(EXML_TRANSIENT);
		createEAttribute(eXmlTransientEClass, EXML_TRANSIENT__XML_LOCATION);

		eXmlTypeEClass = createEClass(EXML_TYPE);
		createEAttribute(eXmlTypeEClass, EXML_TYPE__NAME);
		createEAttribute(eXmlTypeEClass, EXML_TYPE__NAMESPACE);
		createEAttribute(eXmlTypeEClass, EXML_TYPE__FACTORY_CLASS);
		createEAttribute(eXmlTypeEClass, EXML_TYPE__FACTORY_METHOD);
		createEAttribute(eXmlTypeEClass, EXML_TYPE__PROP_ORDER);

		eXmlValueEClass = createEClass(EXML_VALUE);
		createEAttribute(eXmlValueEClass, EXML_VALUE__CDATA);
		createEReference(eXmlValueEClass, EXML_VALUE__XML_ABSTRACT_NULL_POLICY);

		eXmlVirtualAccessMethodsEClass = createEClass(EXML_VIRTUAL_ACCESS_METHODS);
		createEAttribute(eXmlVirtualAccessMethodsEClass, EXML_VIRTUAL_ACCESS_METHODS__GET_METHOD);
		createEAttribute(eXmlVirtualAccessMethodsEClass, EXML_VIRTUAL_ACCESS_METHODS__SET_METHOD);
		createEAttribute(eXmlVirtualAccessMethodsEClass, EXML_VIRTUAL_ACCESS_METHODS__SCHEMA);

		eXmlWriteTransformerEClass = createEClass(EXML_WRITE_TRANSFORMER);
		createEAttribute(eXmlWriteTransformerEClass, EXML_WRITE_TRANSFORMER__XML_PATH);

		// Create enums
		eXmlAccessOrderEEnum = createEEnum(EXML_ACCESS_ORDER);
		eXmlAccessTypeEEnum = createEEnum(EXML_ACCESS_TYPE);
		eXmlMarshalNullRepresentationEEnum = createEEnum(EXML_MARSHAL_NULL_REPRESENTATION);
		eXmlNsFormEEnum = createEEnum(EXML_NS_FORM);
		eXmlVirtualAccessMethodsSchemaEEnum = createEEnum(EXML_VIRTUAL_ACCESS_METHODS_SCHEMA);

		// Create data types
		ePropOrderEDataType = createEDataType(EPROP_ORDER);
		eXmlSeeAlsoEDataType = createEDataType(EXML_SEE_ALSO);
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
		CommonPackage theCommonPackage = (CommonPackage)EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		eJavaTypeEClass.getESuperTypes().add(this.getEAbstractTypeMapping());
		eJavaTypeEClass.getESuperTypes().add(this.getEPropertyHolder());
		eXmlAnyAttributeEClass.getESuperTypes().add(this.getEJavaAttribute());
		eXmlAnyAttributeEClass.getESuperTypes().add(this.getEAccessibleJavaAttribute());
		eXmlAnyAttributeEClass.getESuperTypes().add(this.getEContainerJavaAttribute());
		eXmlAnyAttributeEClass.getESuperTypes().add(this.getEReadWriteJavaAttribute());
		eXmlAnyAttributeEClass.getESuperTypes().add(this.getEPropertyHolder());
		eXmlAnyElementEClass.getESuperTypes().add(this.getEJavaAttribute());
		eXmlAnyElementEClass.getESuperTypes().add(this.getEAccessibleJavaAttribute());
		eXmlAnyElementEClass.getESuperTypes().add(this.getEAdaptableJavaAttribute());
		eXmlAnyElementEClass.getESuperTypes().add(this.getEContainerJavaAttribute());
		eXmlAnyElementEClass.getESuperTypes().add(this.getEReadWriteJavaAttribute());
		eXmlAnyElementEClass.getESuperTypes().add(this.getEPropertyHolder());
		eXmlAttributeEClass.getESuperTypes().add(this.getEJavaAttribute());
		eXmlAttributeEClass.getESuperTypes().add(this.getEAccessibleJavaAttribute());
		eXmlAttributeEClass.getESuperTypes().add(this.getEAdaptableJavaAttribute());
		eXmlAttributeEClass.getESuperTypes().add(this.getEContainerJavaAttribute());
		eXmlAttributeEClass.getESuperTypes().add(this.getEReadWriteJavaAttribute());
		eXmlAttributeEClass.getESuperTypes().add(this.getETypedJavaAttribute());
		eXmlAttributeEClass.getESuperTypes().add(this.getEPropertyHolder());
		eXmlBindingsEClass.getESuperTypes().add(theCommonPackage.getERootObjectImpl());
		eXmlElementEClass.getESuperTypes().add(this.getEJavaAttribute());
		eXmlElementEClass.getESuperTypes().add(this.getEAccessibleJavaAttribute());
		eXmlElementEClass.getESuperTypes().add(this.getEAdaptableJavaAttribute());
		eXmlElementEClass.getESuperTypes().add(this.getEContainerJavaAttribute());
		eXmlElementEClass.getESuperTypes().add(this.getEPropertyHolder());
		eXmlElementEClass.getESuperTypes().add(this.getEReadWriteJavaAttribute());
		eXmlElementRefEClass.getESuperTypes().add(this.getEJavaAttribute());
		eXmlElementRefEClass.getESuperTypes().add(this.getEAccessibleJavaAttribute());
		eXmlElementRefEClass.getESuperTypes().add(this.getEAdaptableJavaAttribute());
		eXmlElementRefEClass.getESuperTypes().add(this.getEContainerJavaAttribute());
		eXmlElementRefEClass.getESuperTypes().add(this.getEReadWriteJavaAttribute());
		eXmlElementRefEClass.getESuperTypes().add(this.getETypedJavaAttribute());
		eXmlElementRefEClass.getESuperTypes().add(this.getEPropertyHolder());
		eXmlElementRefsEClass.getESuperTypes().add(this.getEJavaAttribute());
		eXmlElementRefsEClass.getESuperTypes().add(this.getEAccessibleJavaAttribute());
		eXmlElementRefsEClass.getESuperTypes().add(this.getEAdaptableJavaAttribute());
		eXmlElementRefsEClass.getESuperTypes().add(this.getEReadWriteJavaAttribute());
		eXmlElementRefsEClass.getESuperTypes().add(this.getEPropertyHolder());
		eXmlElementsEClass.getESuperTypes().add(this.getEJavaAttribute());
		eXmlElementsEClass.getESuperTypes().add(this.getEAccessibleJavaAttribute());
		eXmlElementsEClass.getESuperTypes().add(this.getEContainerJavaAttribute());
		eXmlElementsEClass.getESuperTypes().add(this.getEReadWriteJavaAttribute());
		eXmlElementsEClass.getESuperTypes().add(this.getEPropertyHolder());
		eXmlEnumEClass.getESuperTypes().add(this.getEAbstractTypeMapping());
		eXmlInverseReferenceEClass.getESuperTypes().add(this.getEJavaAttribute());
		eXmlInverseReferenceEClass.getESuperTypes().add(this.getEAccessibleJavaAttribute());
		eXmlInverseReferenceEClass.getESuperTypes().add(this.getEContainerJavaAttribute());
		eXmlInverseReferenceEClass.getESuperTypes().add(this.getETypedJavaAttribute());
		eXmlInverseReferenceEClass.getESuperTypes().add(this.getEPropertyHolder());
		eXmlIsSetNullPolicyEClass.getESuperTypes().add(this.getEAbstractXmlNullPolicy());
		eXmlJavaTypeAdapterEClass.getESuperTypes().add(this.getEJavaAttribute());
		eXmlJoinNodesEClass.getESuperTypes().add(this.getEJavaAttribute());
		eXmlJoinNodesEClass.getESuperTypes().add(this.getEContainerJavaAttribute());
		eXmlJoinNodesEClass.getESuperTypes().add(this.getETypedJavaAttribute());
		eXmlNullPolicyEClass.getESuperTypes().add(this.getEAbstractXmlNullPolicy());
		eXmlReadTransformerEClass.getESuperTypes().add(this.getEAbstractXmlTransformer());
		eXmlTransformationEClass.getESuperTypes().add(this.getEJavaAttribute());
		eXmlTransformationEClass.getESuperTypes().add(this.getEAccessibleJavaAttribute());
		eXmlTransformationEClass.getESuperTypes().add(this.getEPropertyHolder());
		eXmlTransientEClass.getESuperTypes().add(this.getEJavaAttribute());
		eXmlValueEClass.getESuperTypes().add(this.getEJavaAttribute());
		eXmlValueEClass.getESuperTypes().add(this.getEAccessibleJavaAttribute());
		eXmlValueEClass.getESuperTypes().add(this.getEAdaptableJavaAttribute());
		eXmlValueEClass.getESuperTypes().add(this.getEContainerJavaAttribute());
		eXmlValueEClass.getESuperTypes().add(this.getEReadWriteJavaAttribute());
		eXmlValueEClass.getESuperTypes().add(this.getETypedJavaAttribute());
		eXmlValueEClass.getESuperTypes().add(this.getEPropertyHolder());
		eXmlWriteTransformerEClass.getESuperTypes().add(this.getEAbstractXmlTransformer());

		// Initialize classes and features; add operations and parameters
		initEClass(eAbstractTypeMappingEClass, EAbstractTypeMapping.class, "EAbstractTypeMapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEAbstractTypeMapping_XmlTransient(), ecorePackage.getEBooleanObject(), "xmlTransient", null, 0, 1, EAbstractTypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEAbstractTypeMapping_XmlType(), this.getEXmlType(), null, "xmlType", null, 0, 1, EAbstractTypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEAbstractTypeMapping_XmlRootElement(), this.getEXmlRootElement(), null, "xmlRootElement", null, 0, 1, EAbstractTypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEAbstractTypeMapping_XmlSeeAlso(), this.getEXmlSeeAlso(), "xmlSeeAlso", null, 0, 1, EAbstractTypeMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eAbstractXmlNullPolicyEClass, EAbstractXmlNullPolicy.class, "EAbstractXmlNullPolicy", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEAbstractXmlNullPolicy_XsiNilRepresentsNull(), ecorePackage.getEBoolean(), "xsiNilRepresentsNull", null, 0, 1, EAbstractXmlNullPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEAbstractXmlNullPolicy_EmptyNodeRepresentsNull(), ecorePackage.getEBoolean(), "emptyNodeRepresentsNull", null, 0, 1, EAbstractXmlNullPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEAbstractXmlNullPolicy_NullRepresentationForXml(), this.getEXmlMarshalNullRepresentation(), "nullRepresentationForXml", null, 0, 1, EAbstractXmlNullPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eAbstractXmlTransformerEClass, EAbstractXmlTransformer.class, "EAbstractXmlTransformer", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEAbstractXmlTransformer_Method(), ecorePackage.getEString(), "method", null, 0, 1, EAbstractXmlTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEAbstractXmlTransformer_TransformerClass(), ecorePackage.getEString(), "transformerClass", null, 0, 1, EAbstractXmlTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eAccessibleJavaAttributeEClass, EAccessibleJavaAttribute.class, "EAccessibleJavaAttribute", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEAccessibleJavaAttribute_XmlAccessMethods(), this.getEXmlAccessMethods(), null, "xmlAccessMethods", null, 0, 1, EAccessibleJavaAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eAdaptableJavaAttributeEClass, EAdaptableJavaAttribute.class, "EAdaptableJavaAttribute", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEAdaptableJavaAttribute_XmlJavaTypeAdapter(), this.getEXmlJavaTypeAdapter(), null, "xmlJavaTypeAdapter", null, 0, 1, EAdaptableJavaAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eContainerJavaAttributeEClass, EContainerJavaAttribute.class, "EContainerJavaAttribute", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEContainerJavaAttribute_ContainerType(), ecorePackage.getEString(), "containerType", null, 0, 1, EContainerJavaAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eJavaAttributeEClass, EJavaAttribute.class, "EJavaAttribute", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEJavaAttribute_JavaAttribute(), ecorePackage.getEString(), "javaAttribute", null, 0, 1, EJavaAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEJavaAttribute_XmlAccessorType(), this.getEXmlAccessType(), "xmlAccessorType", null, 0, 1, EJavaAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eJavaTypeEClass, EJavaType.class, "EJavaType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEJavaType_Name(), ecorePackage.getEString(), "name", null, 0, 1, EJavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEJavaType_SuperType(), ecorePackage.getEString(), "superType", null, 0, 1, EJavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEJavaType_XmlAccessorOrder(), this.getEXmlAccessOrder(), "xmlAccessorOrder", null, 0, 1, EJavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEJavaType_XmlAccessorType(), this.getEXmlAccessType(), "xmlAccessorType", null, 0, 1, EJavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEJavaType_XmlCustomizer(), ecorePackage.getEString(), "xmlCustomizer", null, 0, 1, EJavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEJavaType_XmlDiscriminatorNode(), ecorePackage.getEString(), "xmlDiscriminatorNode", null, 0, 1, EJavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEJavaType_XmlDiscriminatorValue(), ecorePackage.getEString(), "xmlDiscriminatorValue", null, 0, 1, EJavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEJavaType_XmlInlineBinaryData(), ecorePackage.getEBoolean(), "xmlInlineBinaryData", null, 0, 1, EJavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEJavaType_XmlNameTransformer(), ecorePackage.getEString(), "xmlNameTransformer", null, 0, 1, EJavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEJavaType_XmlVirtualAccessMethods(), this.getEXmlVirtualAccessMethods(), null, "xmlVirtualAccessMethods", null, 0, 1, EJavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEJavaType_XmlJavaTypeAdapter(), this.getEXmlJavaTypeAdapter(), null, "xmlJavaTypeAdapter", null, 0, 1, EJavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEJavaType_XmlClassExtractor(), this.getEXmlClassExtractor(), null, "xmlClassExtractor", null, 0, 1, EJavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEJavaType_JavaAttributes(), this.getEJavaAttribute(), null, "javaAttributes", null, 0, -1, EJavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ePropertyHolderEClass, EPropertyHolder.class, "EPropertyHolder", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEPropertyHolder_XmlProperties(), this.getEXmlProperty(), null, "xmlProperties", null, 0, -1, EPropertyHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eReadWriteJavaAttributeEClass, EReadWriteJavaAttribute.class, "EReadWriteJavaAttribute", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEReadWriteJavaAttribute_ReadOnly(), ecorePackage.getEBoolean(), "readOnly", null, 0, 1, EReadWriteJavaAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEReadWriteJavaAttribute_WriteOnly(), ecorePackage.getEBoolean(), "writeOnly", null, 0, 1, EReadWriteJavaAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eTypedJavaAttributeEClass, ETypedJavaAttribute.class, "ETypedJavaAttribute", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getETypedJavaAttribute_Type(), ecorePackage.getEString(), "type", null, 0, 1, ETypedJavaAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlAccessMethodsEClass, EXmlAccessMethods.class, "EXmlAccessMethods", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlAccessMethods_GetMethod(), ecorePackage.getEString(), "getMethod", null, 0, 1, EXmlAccessMethods.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAccessMethods_SetMethod(), ecorePackage.getEString(), "setMethod", null, 0, 1, EXmlAccessMethods.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlAnyAttributeEClass, EXmlAnyAttribute.class, "EXmlAnyAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlAnyAttribute_XmlPath(), ecorePackage.getEString(), "xmlPath", null, 0, 1, EXmlAnyAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlAnyElementEClass, EXmlAnyElement.class, "EXmlAnyElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlAnyElement_XmlMixed(), ecorePackage.getEBoolean(), "xmlMixed", null, 0, 1, EXmlAnyElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAnyElement_Lax(), ecorePackage.getEBoolean(), "lax", null, 0, 1, EXmlAnyElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAnyElement_DomHandler(), ecorePackage.getEString(), "domHandler", null, 0, 1, EXmlAnyElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAnyElement_XmlPath(), ecorePackage.getEString(), "xmlPath", null, 0, 1, EXmlAnyElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlAnyElement_XmlElementRefs(), this.getEXmlElementRefs(), null, "xmlElementRefs", null, 0, 1, EXmlAnyElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlAttributeEClass, EXmlAttribute.class, "EXmlAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlAttribute_Name(), ecorePackage.getEString(), "name", null, 0, 1, EXmlAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAttribute_Namespace(), ecorePackage.getEString(), "namespace", null, 0, 1, EXmlAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAttribute_Required(), ecorePackage.getEBoolean(), "required", null, 0, 1, EXmlAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAttribute_XmlId(), ecorePackage.getEBoolean(), "xmlId", null, 0, 1, EXmlAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAttribute_XmlIdRef(), ecorePackage.getEBoolean(), "xmlIdRef", null, 0, 1, EXmlAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAttribute_XmlKey(), ecorePackage.getEBoolean(), "xmlKey", null, 0, 1, EXmlAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAttribute_XmlList(), ecorePackage.getEBoolean(), "xmlList", null, 0, 1, EXmlAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAttribute_XmlInlineBinaryData(), ecorePackage.getEBoolean(), "xmlInlineBinaryData", null, 0, 1, EXmlAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAttribute_XmlAttachmentRef(), ecorePackage.getEBoolean(), "xmlAttachmentRef", null, 0, 1, EXmlAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAttribute_XmlMimeType(), ecorePackage.getEString(), "xmlMimeType", null, 0, 1, EXmlAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlAttribute_XmlPath(), ecorePackage.getEString(), "xmlPath", null, 0, 1, EXmlAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlAttribute_XmlAbstractNullPolicy(), this.getEAbstractXmlNullPolicy(), null, "xmlAbstractNullPolicy", null, 0, 1, EXmlAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlAttribute_XmlSchemaType(), this.getEXmlSchemaType(), null, "xmlSchemaType", null, 0, 1, EXmlAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlBindingsEClass, EXmlBindings.class, "EXmlBindings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlBindings_XmlAccessorType(), this.getEXmlAccessType(), "xmlAccessorType", null, 0, 1, EXmlBindings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlBindings_XmlAccessorOrder(), this.getEXmlAccessOrder(), "xmlAccessorOrder", null, 0, 1, EXmlBindings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlBindings_XmlMappingMetadataComplete(), theXMLTypePackage.getBooleanObject(), "xmlMappingMetadataComplete", null, 0, 1, EXmlBindings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlBindings_PackageName(), ecorePackage.getEString(), "packageName", null, 0, 1, EXmlBindings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlBindings_XmlNameTransformer(), ecorePackage.getEString(), "xmlNameTransformer", null, 0, 1, EXmlBindings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlBindings_XmlSchema(), this.getEXmlSchema(), null, "xmlSchema", null, 0, 1, EXmlBindings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlBindings_XmlSchemaType(), this.getEXmlSchemaType(), null, "xmlSchemaType", null, 0, 1, EXmlBindings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlBindings_XmlSchemaTypes(), this.getEXmlSchemaType(), null, "xmlSchemaTypes", null, 0, -1, EXmlBindings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlBindings_XmlJavaTypeAdapters(), this.getEXmlJavaTypeAdapter(), null, "xmlJavaTypeAdapters", null, 0, -1, EXmlBindings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlBindings_XmlRegistries(), this.getEXmlRegistry(), null, "xmlRegistries", null, 0, -1, EXmlBindings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlBindings_XmlEnums(), this.getEXmlEnum(), null, "xmlEnums", null, 0, -1, EXmlBindings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlBindings_JavaTypes(), this.getEJavaType(), null, "javaTypes", null, 0, -1, EXmlBindings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlClassExtractorEClass, EXmlClassExtractor.class, "EXmlClassExtractor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlClassExtractor_ClassName(), ecorePackage.getEString(), "className", null, 0, 1, EXmlClassExtractor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlElementEClass, EXmlElement.class, "EXmlElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_Namespace(), ecorePackage.getEString(), "namespace", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_DefaultValue(), ecorePackage.getEString(), "defaultValue", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_Nillable(), ecorePackage.getEBoolean(), "nillable", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_Required(), ecorePackage.getEBoolean(), "required", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_XmlId(), ecorePackage.getEBoolean(), "xmlId", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_XmlIdRef(), ecorePackage.getEBoolean(), "xmlIdRef", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_XmlKey(), ecorePackage.getEBoolean(), "xmlKey", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_XmlList(), ecorePackage.getEBoolean(), "xmlList", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_XmlInlineBinaryData(), ecorePackage.getEBoolean(), "xmlInlineBinaryData", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_XmlAttachmentRef(), ecorePackage.getEBoolean(), "xmlAttachmentRef", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_XmlMimeType(), ecorePackage.getEString(), "xmlMimeType", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_Cdata(), ecorePackage.getEBoolean(), "cdata", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_XmlPath(), ecorePackage.getEString(), "xmlPath", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElement_XmlLocation(), ecorePackage.getEBoolean(), "xmlLocation", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlElement_XmlAbstractNullPolicy(), this.getEAbstractXmlNullPolicy(), null, "xmlAbstractNullPolicy", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlElement_XmlElementWrapper(), this.getEXmlElementWrapper(), null, "xmlElementWrapper", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlElement_XmlMap(), this.getEXmlElementWrapper(), null, "xmlMap", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlElement_XmlSchemaType(), this.getEXmlSchemaType(), null, "xmlSchemaType", null, 0, 1, EXmlElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlElementDeclEClass, EXmlElementDecl.class, "EXmlElementDecl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlElementDecl_JavaMethod(), ecorePackage.getEString(), "javaMethod", null, 0, 1, EXmlElementDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElementDecl_Name(), ecorePackage.getEString(), "name", null, 0, 1, EXmlElementDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElementDecl_Namespace(), ecorePackage.getEString(), "namespace", null, 0, 1, EXmlElementDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElementDecl_DefaultValue(), ecorePackage.getEString(), "defaultValue", null, 0, 1, EXmlElementDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElementDecl_Scope(), ecorePackage.getEString(), "scope", null, 0, 1, EXmlElementDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElementDecl_SubstitutionHeadName(), ecorePackage.getEString(), "substitutionHeadName", null, 0, 1, EXmlElementDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElementDecl_SubstitutionHeadNamespace(), ecorePackage.getEString(), "substitutionHeadNamespace", null, 0, 1, EXmlElementDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElementDecl_Type(), ecorePackage.getEString(), "type", null, 0, 1, EXmlElementDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlElementRefEClass, EXmlElementRef.class, "EXmlElementRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlElementRef_Name(), ecorePackage.getEString(), "name", null, 0, 1, EXmlElementRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElementRef_Namespace(), ecorePackage.getEString(), "namespace", null, 0, 1, EXmlElementRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElementRef_XmlMixed(), ecorePackage.getEBoolean(), "xmlMixed", null, 0, 1, EXmlElementRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlElementRef_XmlElementWrapper(), this.getEXmlElementWrapper(), null, "xmlElementWrapper", null, 0, 1, EXmlElementRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlElementRefsEClass, EXmlElementRefs.class, "EXmlElementRefs", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlElementRefs_XmlMixed(), ecorePackage.getEBoolean(), "xmlMixed", null, 0, 1, EXmlElementRefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlElementRefs_XmlElementRefs(), this.getEXmlElementRef(), null, "xmlElementRefs", null, 0, -1, EXmlElementRefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlElementRefs_XmlElementWrapper(), this.getEXmlElementWrapper(), null, "xmlElementWrapper", null, 0, 1, EXmlElementRefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlElementsEClass, EXmlElements.class, "EXmlElements", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlElements_XmlIdRef(), ecorePackage.getEBoolean(), "xmlIdRef", null, 0, 1, EXmlElements.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElements_XmlList(), ecorePackage.getEBoolean(), "xmlList", null, 0, 1, EXmlElements.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlElements_XmlElements(), this.getEXmlElement(), null, "xmlElements", null, 0, -1, EXmlElements.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlElements_XmlElementWrapper(), this.getEXmlElementWrapper(), null, "xmlElementWrapper", null, 0, 1, EXmlElements.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlElements_XmlJoinNodes(), this.getEXmlJoinNodes(), null, "xmlJoinNodes", null, 0, -1, EXmlElements.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlElementWrapperEClass, EXmlElementWrapper.class, "EXmlElementWrapper", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlElementWrapper_Name(), ecorePackage.getEString(), "name", null, 0, 1, EXmlElementWrapper.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElementWrapper_Namespace(), ecorePackage.getEString(), "namespace", null, 0, 1, EXmlElementWrapper.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElementWrapper_Nillable(), ecorePackage.getEBoolean(), "nillable", null, 0, 1, EXmlElementWrapper.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlElementWrapper_Required(), ecorePackage.getEBoolean(), "required", null, 0, 1, EXmlElementWrapper.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlEnumEClass, EXmlEnum.class, "EXmlEnum", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlEnum_JavaEnum(), ecorePackage.getEString(), "javaEnum", null, 0, 1, EXmlEnum.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlEnum_Value(), ecorePackage.getEString(), "value", null, 0, 1, EXmlEnum.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlEnum_XmlEnumValues(), this.getEXmlEnumValue(), null, "xmlEnumValues", null, 0, -1, EXmlEnum.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlEnumValueEClass, EXmlEnumValue.class, "EXmlEnumValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlEnumValue_JavaEnumValue(), ecorePackage.getEString(), "javaEnumValue", null, 0, 1, EXmlEnumValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlInverseReferenceEClass, EXmlInverseReference.class, "EXmlInverseReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlInverseReference_MappedBy(), ecorePackage.getEString(), "mappedBy", null, 0, 1, EXmlInverseReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlIsSetNullPolicyEClass, EXmlIsSetNullPolicy.class, "EXmlIsSetNullPolicy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlIsSetNullPolicy_IsSetMethodName(), ecorePackage.getEString(), "isSetMethodName", null, 0, 1, EXmlIsSetNullPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlIsSetNullPolicy_XmlIsSetParameters(), this.getEXmlIsSetParameter(), null, "xmlIsSetParameters", null, 0, -1, EXmlIsSetNullPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlIsSetParameterEClass, EXmlIsSetParameter.class, "EXmlIsSetParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlIsSetParameter_Value(), ecorePackage.getEString(), "value", null, 0, 1, EXmlIsSetParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlIsSetParameter_Type(), ecorePackage.getEString(), "type", null, 0, 1, EXmlIsSetParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlJavaTypeAdapterEClass, EXmlJavaTypeAdapter.class, "EXmlJavaTypeAdapter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlJavaTypeAdapter_Value(), ecorePackage.getEString(), "value", null, 0, 1, EXmlJavaTypeAdapter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlJavaTypeAdapter_Type(), ecorePackage.getEString(), "type", null, 0, 1, EXmlJavaTypeAdapter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlJavaTypeAdapter_ValueType(), ecorePackage.getEString(), "valueType", null, 0, 1, EXmlJavaTypeAdapter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlJoinNodeEClass, EXmlJoinNode.class, "EXmlJoinNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlJoinNode_XmlPath(), ecorePackage.getEString(), "xmlPath", null, 0, 1, EXmlJoinNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlJoinNode_ReferencedXmlPath(), ecorePackage.getEString(), "referencedXmlPath", null, 0, 1, EXmlJoinNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlJoinNodesEClass, EXmlJoinNodes.class, "EXmlJoinNodes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEXmlJoinNodes_XmlJoinNodes(), this.getEXmlJoinNode(), null, "xmlJoinNodes", null, 0, -1, EXmlJoinNodes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlMapEClass, EXmlMap.class, "EXmlMap", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlMap_Key(), ecorePackage.getEString(), "key", null, 0, 1, EXmlMap.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlMap_Value(), ecorePackage.getEString(), "value", null, 0, 1, EXmlMap.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlNullPolicyEClass, EXmlNullPolicy.class, "EXmlNullPolicy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlNullPolicy_IsSetPerformedForAbsentNode(), ecorePackage.getEBoolean(), "isSetPerformedForAbsentNode", null, 0, 1, EXmlNullPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlNsEClass, EXmlNs.class, "EXmlNs", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlNs_NamespaceUri(), ecorePackage.getEString(), "namespaceUri", null, 0, 1, EXmlNs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlNs_Prefix(), ecorePackage.getEString(), "prefix", null, 0, 1, EXmlNs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlPropertyEClass, EXmlProperty.class, "EXmlProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlProperty_Name(), ecorePackage.getEString(), "name", null, 0, 1, EXmlProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlProperty_Value(), ecorePackage.getEString(), "value", null, 0, 1, EXmlProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlProperty_ValueType(), ecorePackage.getEString(), "valueType", null, 0, 1, EXmlProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlReadTransformerEClass, EXmlReadTransformer.class, "EXmlReadTransformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(eXmlRegistryEClass, EXmlRegistry.class, "EXmlRegistry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlRegistry_Name(), ecorePackage.getEString(), "name", null, 0, 1, EXmlRegistry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlRegistry_XmlElementDecls(), this.getEXmlElementDecl(), null, "xmlElementDecls", null, 0, -1, EXmlRegistry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlRootElementEClass, EXmlRootElement.class, "EXmlRootElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlRootElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, EXmlRootElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlRootElement_Namespace(), ecorePackage.getEString(), "namespace", null, 0, 1, EXmlRootElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlSchemaEClass, EXmlSchema.class, "EXmlSchema", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlSchema_AttributeFormDefault(), this.getEXmlNsForm(), "attributeFormDefault", null, 0, 1, EXmlSchema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlSchema_ElementFormDefault(), this.getEXmlNsForm(), "elementFormDefault", null, 0, 1, EXmlSchema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlSchema_Location(), ecorePackage.getEString(), "location", null, 0, 1, EXmlSchema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlSchema_Namespace(), ecorePackage.getEString(), "namespace", null, 0, 1, EXmlSchema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlSchema_Xmlns(), this.getEXmlNs(), null, "xmlns", null, 0, -1, EXmlSchema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlSchemaTypeEClass, EXmlSchemaType.class, "EXmlSchemaType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlSchemaType_Name(), ecorePackage.getEString(), "name", null, 0, 1, EXmlSchemaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlSchemaType_Namespace(), ecorePackage.getEString(), "namespace", null, 0, 1, EXmlSchemaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlSchemaType_Type(), ecorePackage.getEString(), "type", null, 0, 1, EXmlSchemaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlTransformationEClass, EXmlTransformation.class, "EXmlTransformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlTransformation_Optional(), ecorePackage.getEBoolean(), "optional", null, 0, 1, EXmlTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlTransformation_XmlReadTransformer(), this.getEXmlReadTransformer(), null, "xmlReadTransformer", null, 0, 1, EXmlTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlTransformation_XmlWriteTransformers(), this.getEXmlWriteTransformer(), null, "xmlWriteTransformers", null, 0, -1, EXmlTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlTransientEClass, EXmlTransient.class, "EXmlTransient", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlTransient_XmlLocation(), ecorePackage.getEBoolean(), "xmlLocation", null, 0, 1, EXmlTransient.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlTypeEClass, EXmlType.class, "EXmlType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlType_Name(), ecorePackage.getEString(), "name", null, 0, 1, EXmlType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlType_Namespace(), ecorePackage.getEString(), "namespace", null, 0, 1, EXmlType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlType_FactoryClass(), ecorePackage.getEString(), "factoryClass", null, 0, 1, EXmlType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlType_FactoryMethod(), ecorePackage.getEString(), "factoryMethod", null, 0, 1, EXmlType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlType_PropOrder(), this.getEPropOrder(), "propOrder", null, 0, 1, EXmlType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlValueEClass, EXmlValue.class, "EXmlValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlValue_Cdata(), ecorePackage.getEBoolean(), "cdata", null, 0, 1, EXmlValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEXmlValue_XmlAbstractNullPolicy(), this.getEAbstractXmlNullPolicy(), null, "xmlAbstractNullPolicy", null, 0, 1, EXmlValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlVirtualAccessMethodsEClass, EXmlVirtualAccessMethods.class, "EXmlVirtualAccessMethods", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlVirtualAccessMethods_GetMethod(), ecorePackage.getEString(), "getMethod", null, 0, 1, EXmlVirtualAccessMethods.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlVirtualAccessMethods_SetMethod(), ecorePackage.getEString(), "setMethod", null, 0, 1, EXmlVirtualAccessMethods.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEXmlVirtualAccessMethods_Schema(), this.getEXmlVirtualAccessMethodsSchema(), "schema", null, 0, 1, EXmlVirtualAccessMethods.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eXmlWriteTransformerEClass, EXmlWriteTransformer.class, "EXmlWriteTransformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEXmlWriteTransformer_XmlPath(), ecorePackage.getEString(), "xmlPath", null, 0, 1, EXmlWriteTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(eXmlAccessOrderEEnum, EXmlAccessOrder.class, "EXmlAccessOrder");
		addEEnumLiteral(eXmlAccessOrderEEnum, EXmlAccessOrder.ALPHABETICAL);
		addEEnumLiteral(eXmlAccessOrderEEnum, EXmlAccessOrder.UNDEFINED);

		initEEnum(eXmlAccessTypeEEnum, EXmlAccessType.class, "EXmlAccessType");
		addEEnumLiteral(eXmlAccessTypeEEnum, EXmlAccessType.FIELD);
		addEEnumLiteral(eXmlAccessTypeEEnum, EXmlAccessType.NONE);
		addEEnumLiteral(eXmlAccessTypeEEnum, EXmlAccessType.PROPERTY);
		addEEnumLiteral(eXmlAccessTypeEEnum, EXmlAccessType.PUBLIC_MEMBER);

		initEEnum(eXmlMarshalNullRepresentationEEnum, EXmlMarshalNullRepresentation.class, "EXmlMarshalNullRepresentation");
		addEEnumLiteral(eXmlMarshalNullRepresentationEEnum, EXmlMarshalNullRepresentation.XSI_NIL);
		addEEnumLiteral(eXmlMarshalNullRepresentationEEnum, EXmlMarshalNullRepresentation.ABSENT_NODE);
		addEEnumLiteral(eXmlMarshalNullRepresentationEEnum, EXmlMarshalNullRepresentation.EMPTY_NODE);

		initEEnum(eXmlNsFormEEnum, EXmlNsForm.class, "EXmlNsForm");
		addEEnumLiteral(eXmlNsFormEEnum, EXmlNsForm.UNQUALIFIED);
		addEEnumLiteral(eXmlNsFormEEnum, EXmlNsForm.QUALIFIED);
		addEEnumLiteral(eXmlNsFormEEnum, EXmlNsForm.UNSET);

		initEEnum(eXmlVirtualAccessMethodsSchemaEEnum, EXmlVirtualAccessMethodsSchema.class, "EXmlVirtualAccessMethodsSchema");
		addEEnumLiteral(eXmlVirtualAccessMethodsSchemaEEnum, EXmlVirtualAccessMethodsSchema.NODES);
		addEEnumLiteral(eXmlVirtualAccessMethodsSchemaEEnum, EXmlVirtualAccessMethodsSchema.ANY);

		// Initialize data types
		initEDataType(ePropOrderEDataType, List.class, "EPropOrder", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(eXmlSeeAlsoEDataType, List.class, "EXmlSeeAlso", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations()
	{
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
		addAnnotation
		  (ePropOrderEDataType, 
		   source, 
		   new String[] 
		   {
			 "itemType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (eXmlSeeAlsoEDataType, 
		   source, 
		   new String[] 
		   {
			 "itemType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });
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
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping <em>EAbstract Type Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractTypeMapping()
		 * @generated
		 */
		public static final EClass EABSTRACT_TYPE_MAPPING = eINSTANCE.getEAbstractTypeMapping();

		/**
		 * The meta object literal for the '<em><b>Xml Transient</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EABSTRACT_TYPE_MAPPING__XML_TRANSIENT = eINSTANCE.getEAbstractTypeMapping_XmlTransient();

		/**
		 * The meta object literal for the '<em><b>Xml Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EABSTRACT_TYPE_MAPPING__XML_TYPE = eINSTANCE.getEAbstractTypeMapping_XmlType();

		/**
		 * The meta object literal for the '<em><b>Xml Root Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT = eINSTANCE.getEAbstractTypeMapping_XmlRootElement();

		/**
		 * The meta object literal for the '<em><b>Xml See Also</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EABSTRACT_TYPE_MAPPING__XML_SEE_ALSO = eINSTANCE.getEAbstractTypeMapping_XmlSeeAlso();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy <em>EAbstract Xml Null Policy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractXmlNullPolicy()
		 * @generated
		 */
		public static final EClass EABSTRACT_XML_NULL_POLICY = eINSTANCE.getEAbstractXmlNullPolicy();

		/**
		 * The meta object literal for the '<em><b>Xsi Nil Represents Null</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EABSTRACT_XML_NULL_POLICY__XSI_NIL_REPRESENTS_NULL = eINSTANCE.getEAbstractXmlNullPolicy_XsiNilRepresentsNull();

		/**
		 * The meta object literal for the '<em><b>Empty Node Represents Null</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EABSTRACT_XML_NULL_POLICY__EMPTY_NODE_REPRESENTS_NULL = eINSTANCE.getEAbstractXmlNullPolicy_EmptyNodeRepresentsNull();

		/**
		 * The meta object literal for the '<em><b>Null Representation For Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EABSTRACT_XML_NULL_POLICY__NULL_REPRESENTATION_FOR_XML = eINSTANCE.getEAbstractXmlNullPolicy_NullRepresentationForXml();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer <em>EAbstract Xml Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractXmlTransformer()
		 * @generated
		 */
		public static final EClass EABSTRACT_XML_TRANSFORMER = eINSTANCE.getEAbstractXmlTransformer();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EABSTRACT_XML_TRANSFORMER__METHOD = eINSTANCE.getEAbstractXmlTransformer_Method();

		/**
		 * The meta object literal for the '<em><b>Transformer Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EABSTRACT_XML_TRANSFORMER__TRANSFORMER_CLASS = eINSTANCE.getEAbstractXmlTransformer_TransformerClass();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAccessibleJavaAttribute <em>EAccessible Java Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAccessibleJavaAttribute
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAccessibleJavaAttribute()
		 * @generated
		 */
		public static final EClass EACCESSIBLE_JAVA_ATTRIBUTE = eINSTANCE.getEAccessibleJavaAttribute();

		/**
		 * The meta object literal for the '<em><b>Xml Access Methods</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EACCESSIBLE_JAVA_ATTRIBUTE__XML_ACCESS_METHODS = eINSTANCE.getEAccessibleJavaAttribute_XmlAccessMethods();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAdaptableJavaAttribute <em>EAdaptable Java Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAdaptableJavaAttribute
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAdaptableJavaAttribute()
		 * @generated
		 */
		public static final EClass EADAPTABLE_JAVA_ATTRIBUTE = eINSTANCE.getEAdaptableJavaAttribute();

		/**
		 * The meta object literal for the '<em><b>Xml Java Type Adapter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EADAPTABLE_JAVA_ATTRIBUTE__XML_JAVA_TYPE_ADAPTER = eINSTANCE.getEAdaptableJavaAttribute_XmlJavaTypeAdapter();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EContainerJavaAttribute <em>EContainer Java Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EContainerJavaAttribute
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEContainerJavaAttribute()
		 * @generated
		 */
		public static final EClass ECONTAINER_JAVA_ATTRIBUTE = eINSTANCE.getEContainerJavaAttribute();

		/**
		 * The meta object literal for the '<em><b>Container Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ECONTAINER_JAVA_ATTRIBUTE__CONTAINER_TYPE = eINSTANCE.getEContainerJavaAttribute_ContainerType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute <em>EJava Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaAttribute()
		 * @generated
		 */
		public static final EClass EJAVA_ATTRIBUTE = eINSTANCE.getEJavaAttribute();

		/**
		 * The meta object literal for the '<em><b>Java Attribute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE = eINSTANCE.getEJavaAttribute_JavaAttribute();

		/**
		 * The meta object literal for the '<em><b>Xml Accessor Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE = eINSTANCE.getEJavaAttribute_XmlAccessorType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType <em>EJava Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaType()
		 * @generated
		 */
		public static final EClass EJAVA_TYPE = eINSTANCE.getEJavaType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EJAVA_TYPE__NAME = eINSTANCE.getEJavaType_Name();

		/**
		 * The meta object literal for the '<em><b>Super Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EJAVA_TYPE__SUPER_TYPE = eINSTANCE.getEJavaType_SuperType();

		/**
		 * The meta object literal for the '<em><b>Xml Accessor Order</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EJAVA_TYPE__XML_ACCESSOR_ORDER = eINSTANCE.getEJavaType_XmlAccessorOrder();

		/**
		 * The meta object literal for the '<em><b>Xml Accessor Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EJAVA_TYPE__XML_ACCESSOR_TYPE = eINSTANCE.getEJavaType_XmlAccessorType();

		/**
		 * The meta object literal for the '<em><b>Xml Customizer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EJAVA_TYPE__XML_CUSTOMIZER = eINSTANCE.getEJavaType_XmlCustomizer();

		/**
		 * The meta object literal for the '<em><b>Xml Discriminator Node</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EJAVA_TYPE__XML_DISCRIMINATOR_NODE = eINSTANCE.getEJavaType_XmlDiscriminatorNode();

		/**
		 * The meta object literal for the '<em><b>Xml Discriminator Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EJAVA_TYPE__XML_DISCRIMINATOR_VALUE = eINSTANCE.getEJavaType_XmlDiscriminatorValue();

		/**
		 * The meta object literal for the '<em><b>Xml Inline Binary Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EJAVA_TYPE__XML_INLINE_BINARY_DATA = eINSTANCE.getEJavaType_XmlInlineBinaryData();

		/**
		 * The meta object literal for the '<em><b>Xml Name Transformer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EJAVA_TYPE__XML_NAME_TRANSFORMER = eINSTANCE.getEJavaType_XmlNameTransformer();

		/**
		 * The meta object literal for the '<em><b>Xml Virtual Access Methods</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EJAVA_TYPE__XML_VIRTUAL_ACCESS_METHODS = eINSTANCE.getEJavaType_XmlVirtualAccessMethods();

		/**
		 * The meta object literal for the '<em><b>Xml Java Type Adapter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EJAVA_TYPE__XML_JAVA_TYPE_ADAPTER = eINSTANCE.getEJavaType_XmlJavaTypeAdapter();

		/**
		 * The meta object literal for the '<em><b>Xml Class Extractor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EJAVA_TYPE__XML_CLASS_EXTRACTOR = eINSTANCE.getEJavaType_XmlClassExtractor();

		/**
		 * The meta object literal for the '<em><b>Java Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EJAVA_TYPE__JAVA_ATTRIBUTES = eINSTANCE.getEJavaType_JavaAttributes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EPropertyHolder <em>EProperty Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EPropertyHolder
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEPropertyHolder()
		 * @generated
		 */
		public static final EClass EPROPERTY_HOLDER = eINSTANCE.getEPropertyHolder();

		/**
		 * The meta object literal for the '<em><b>Xml Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EPROPERTY_HOLDER__XML_PROPERTIES = eINSTANCE.getEPropertyHolder_XmlProperties();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EReadWriteJavaAttribute <em>ERead Write Java Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EReadWriteJavaAttribute
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEReadWriteJavaAttribute()
		 * @generated
		 */
		public static final EClass EREAD_WRITE_JAVA_ATTRIBUTE = eINSTANCE.getEReadWriteJavaAttribute();

		/**
		 * The meta object literal for the '<em><b>Read Only</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EREAD_WRITE_JAVA_ATTRIBUTE__READ_ONLY = eINSTANCE.getEReadWriteJavaAttribute_ReadOnly();

		/**
		 * The meta object literal for the '<em><b>Write Only</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EREAD_WRITE_JAVA_ATTRIBUTE__WRITE_ONLY = eINSTANCE.getEReadWriteJavaAttribute_WriteOnly();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.ETypedJavaAttribute <em>ETyped Java Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.ETypedJavaAttribute
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getETypedJavaAttribute()
		 * @generated
		 */
		public static final EClass ETYPED_JAVA_ATTRIBUTE = eINSTANCE.getETypedJavaAttribute();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ETYPED_JAVA_ATTRIBUTE__TYPE = eINSTANCE.getETypedJavaAttribute_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessMethods <em>EXml Access Methods</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessMethods
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAccessMethods()
		 * @generated
		 */
		public static final EClass EXML_ACCESS_METHODS = eINSTANCE.getEXmlAccessMethods();

		/**
		 * The meta object literal for the '<em><b>Get Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ACCESS_METHODS__GET_METHOD = eINSTANCE.getEXmlAccessMethods_GetMethod();

		/**
		 * The meta object literal for the '<em><b>Set Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ACCESS_METHODS__SET_METHOD = eINSTANCE.getEXmlAccessMethods_SetMethod();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute <em>EXml Any Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAnyAttribute()
		 * @generated
		 */
		public static final EClass EXML_ANY_ATTRIBUTE = eINSTANCE.getEXmlAnyAttribute();

		/**
		 * The meta object literal for the '<em><b>Xml Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ANY_ATTRIBUTE__XML_PATH = eINSTANCE.getEXmlAnyAttribute_XmlPath();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement <em>EXml Any Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyElement
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAnyElement()
		 * @generated
		 */
		public static final EClass EXML_ANY_ELEMENT = eINSTANCE.getEXmlAnyElement();

		/**
		 * The meta object literal for the '<em><b>Xml Mixed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ANY_ELEMENT__XML_MIXED = eINSTANCE.getEXmlAnyElement_XmlMixed();

		/**
		 * The meta object literal for the '<em><b>Lax</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ANY_ELEMENT__LAX = eINSTANCE.getEXmlAnyElement_Lax();

		/**
		 * The meta object literal for the '<em><b>Dom Handler</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ANY_ELEMENT__DOM_HANDLER = eINSTANCE.getEXmlAnyElement_DomHandler();

		/**
		 * The meta object literal for the '<em><b>Xml Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ANY_ELEMENT__XML_PATH = eINSTANCE.getEXmlAnyElement_XmlPath();

		/**
		 * The meta object literal for the '<em><b>Xml Element Refs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ANY_ELEMENT__XML_ELEMENT_REFS = eINSTANCE.getEXmlAnyElement_XmlElementRefs();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute <em>EXml Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAttribute
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAttribute()
		 * @generated
		 */
		public static final EClass EXML_ATTRIBUTE = eINSTANCE.getEXmlAttribute();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ATTRIBUTE__NAME = eINSTANCE.getEXmlAttribute_Name();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ATTRIBUTE__NAMESPACE = eINSTANCE.getEXmlAttribute_Namespace();

		/**
		 * The meta object literal for the '<em><b>Required</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ATTRIBUTE__REQUIRED = eINSTANCE.getEXmlAttribute_Required();

		/**
		 * The meta object literal for the '<em><b>Xml Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ATTRIBUTE__XML_ID = eINSTANCE.getEXmlAttribute_XmlId();

		/**
		 * The meta object literal for the '<em><b>Xml Id Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ATTRIBUTE__XML_ID_REF = eINSTANCE.getEXmlAttribute_XmlIdRef();

		/**
		 * The meta object literal for the '<em><b>Xml Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ATTRIBUTE__XML_KEY = eINSTANCE.getEXmlAttribute_XmlKey();

		/**
		 * The meta object literal for the '<em><b>Xml List</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ATTRIBUTE__XML_LIST = eINSTANCE.getEXmlAttribute_XmlList();

		/**
		 * The meta object literal for the '<em><b>Xml Inline Binary Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ATTRIBUTE__XML_INLINE_BINARY_DATA = eINSTANCE.getEXmlAttribute_XmlInlineBinaryData();

		/**
		 * The meta object literal for the '<em><b>Xml Attachment Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ATTRIBUTE__XML_ATTACHMENT_REF = eINSTANCE.getEXmlAttribute_XmlAttachmentRef();

		/**
		 * The meta object literal for the '<em><b>Xml Mime Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ATTRIBUTE__XML_MIME_TYPE = eINSTANCE.getEXmlAttribute_XmlMimeType();

		/**
		 * The meta object literal for the '<em><b>Xml Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ATTRIBUTE__XML_PATH = eINSTANCE.getEXmlAttribute_XmlPath();

		/**
		 * The meta object literal for the '<em><b>Xml Abstract Null Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ATTRIBUTE__XML_ABSTRACT_NULL_POLICY = eINSTANCE.getEXmlAttribute_XmlAbstractNullPolicy();

		/**
		 * The meta object literal for the '<em><b>Xml Schema Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ATTRIBUTE__XML_SCHEMA_TYPE = eINSTANCE.getEXmlAttribute_XmlSchemaType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings <em>EXml Bindings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlBindings()
		 * @generated
		 */
		public static final EClass EXML_BINDINGS = eINSTANCE.getEXmlBindings();

		/**
		 * The meta object literal for the '<em><b>Xml Accessor Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_BINDINGS__XML_ACCESSOR_TYPE = eINSTANCE.getEXmlBindings_XmlAccessorType();

		/**
		 * The meta object literal for the '<em><b>Xml Accessor Order</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_BINDINGS__XML_ACCESSOR_ORDER = eINSTANCE.getEXmlBindings_XmlAccessorOrder();

		/**
		 * The meta object literal for the '<em><b>Xml Mapping Metadata Complete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_BINDINGS__XML_MAPPING_METADATA_COMPLETE = eINSTANCE.getEXmlBindings_XmlMappingMetadataComplete();

		/**
		 * The meta object literal for the '<em><b>Package Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_BINDINGS__PACKAGE_NAME = eINSTANCE.getEXmlBindings_PackageName();

		/**
		 * The meta object literal for the '<em><b>Xml Name Transformer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_BINDINGS__XML_NAME_TRANSFORMER = eINSTANCE.getEXmlBindings_XmlNameTransformer();

		/**
		 * The meta object literal for the '<em><b>Xml Schema</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_BINDINGS__XML_SCHEMA = eINSTANCE.getEXmlBindings_XmlSchema();

		/**
		 * The meta object literal for the '<em><b>Xml Schema Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_BINDINGS__XML_SCHEMA_TYPE = eINSTANCE.getEXmlBindings_XmlSchemaType();

		/**
		 * The meta object literal for the '<em><b>Xml Schema Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_BINDINGS__XML_SCHEMA_TYPES = eINSTANCE.getEXmlBindings_XmlSchemaTypes();

		/**
		 * The meta object literal for the '<em><b>Xml Java Type Adapters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_BINDINGS__XML_JAVA_TYPE_ADAPTERS = eINSTANCE.getEXmlBindings_XmlJavaTypeAdapters();

		/**
		 * The meta object literal for the '<em><b>Xml Registries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_BINDINGS__XML_REGISTRIES = eINSTANCE.getEXmlBindings_XmlRegistries();

		/**
		 * The meta object literal for the '<em><b>Xml Enums</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_BINDINGS__XML_ENUMS = eINSTANCE.getEXmlBindings_XmlEnums();

		/**
		 * The meta object literal for the '<em><b>Java Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_BINDINGS__JAVA_TYPES = eINSTANCE.getEXmlBindings_JavaTypes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlClassExtractor <em>EXml Class Extractor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlClassExtractor
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlClassExtractor()
		 * @generated
		 */
		public static final EClass EXML_CLASS_EXTRACTOR = eINSTANCE.getEXmlClassExtractor();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_CLASS_EXTRACTOR__CLASS_NAME = eINSTANCE.getEXmlClassExtractor_ClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement <em>EXml Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElement()
		 * @generated
		 */
		public static final EClass EXML_ELEMENT = eINSTANCE.getEXmlElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__NAME = eINSTANCE.getEXmlElement_Name();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__NAMESPACE = eINSTANCE.getEXmlElement_Namespace();

		/**
		 * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__DEFAULT_VALUE = eINSTANCE.getEXmlElement_DefaultValue();

		/**
		 * The meta object literal for the '<em><b>Nillable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__NILLABLE = eINSTANCE.getEXmlElement_Nillable();

		/**
		 * The meta object literal for the '<em><b>Required</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__REQUIRED = eINSTANCE.getEXmlElement_Required();

		/**
		 * The meta object literal for the '<em><b>Xml Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__XML_ID = eINSTANCE.getEXmlElement_XmlId();

		/**
		 * The meta object literal for the '<em><b>Xml Id Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__XML_ID_REF = eINSTANCE.getEXmlElement_XmlIdRef();

		/**
		 * The meta object literal for the '<em><b>Xml Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__XML_KEY = eINSTANCE.getEXmlElement_XmlKey();

		/**
		 * The meta object literal for the '<em><b>Xml List</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__XML_LIST = eINSTANCE.getEXmlElement_XmlList();

		/**
		 * The meta object literal for the '<em><b>Xml Inline Binary Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__XML_INLINE_BINARY_DATA = eINSTANCE.getEXmlElement_XmlInlineBinaryData();

		/**
		 * The meta object literal for the '<em><b>Xml Attachment Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__XML_ATTACHMENT_REF = eINSTANCE.getEXmlElement_XmlAttachmentRef();

		/**
		 * The meta object literal for the '<em><b>Xml Mime Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__XML_MIME_TYPE = eINSTANCE.getEXmlElement_XmlMimeType();

		/**
		 * The meta object literal for the '<em><b>Cdata</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__CDATA = eINSTANCE.getEXmlElement_Cdata();

		/**
		 * The meta object literal for the '<em><b>Xml Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__XML_PATH = eINSTANCE.getEXmlElement_XmlPath();

		/**
		 * The meta object literal for the '<em><b>Xml Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT__XML_LOCATION = eINSTANCE.getEXmlElement_XmlLocation();

		/**
		 * The meta object literal for the '<em><b>Xml Abstract Null Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ELEMENT__XML_ABSTRACT_NULL_POLICY = eINSTANCE.getEXmlElement_XmlAbstractNullPolicy();

		/**
		 * The meta object literal for the '<em><b>Xml Element Wrapper</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ELEMENT__XML_ELEMENT_WRAPPER = eINSTANCE.getEXmlElement_XmlElementWrapper();

		/**
		 * The meta object literal for the '<em><b>Xml Map</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ELEMENT__XML_MAP = eINSTANCE.getEXmlElement_XmlMap();

		/**
		 * The meta object literal for the '<em><b>Xml Schema Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ELEMENT__XML_SCHEMA_TYPE = eINSTANCE.getEXmlElement_XmlSchemaType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl <em>EXml Element Decl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementDecl()
		 * @generated
		 */
		public static final EClass EXML_ELEMENT_DECL = eINSTANCE.getEXmlElementDecl();

		/**
		 * The meta object literal for the '<em><b>Java Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_DECL__JAVA_METHOD = eINSTANCE.getEXmlElementDecl_JavaMethod();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_DECL__NAME = eINSTANCE.getEXmlElementDecl_Name();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_DECL__NAMESPACE = eINSTANCE.getEXmlElementDecl_Namespace();

		/**
		 * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_DECL__DEFAULT_VALUE = eINSTANCE.getEXmlElementDecl_DefaultValue();

		/**
		 * The meta object literal for the '<em><b>Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_DECL__SCOPE = eINSTANCE.getEXmlElementDecl_Scope();

		/**
		 * The meta object literal for the '<em><b>Substitution Head Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME = eINSTANCE.getEXmlElementDecl_SubstitutionHeadName();

		/**
		 * The meta object literal for the '<em><b>Substitution Head Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE = eINSTANCE.getEXmlElementDecl_SubstitutionHeadNamespace();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_DECL__TYPE = eINSTANCE.getEXmlElementDecl_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef <em>EXml Element Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRef
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementRef()
		 * @generated
		 */
		public static final EClass EXML_ELEMENT_REF = eINSTANCE.getEXmlElementRef();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_REF__NAME = eINSTANCE.getEXmlElementRef_Name();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_REF__NAMESPACE = eINSTANCE.getEXmlElementRef_Namespace();

		/**
		 * The meta object literal for the '<em><b>Xml Mixed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_REF__XML_MIXED = eINSTANCE.getEXmlElementRef_XmlMixed();

		/**
		 * The meta object literal for the '<em><b>Xml Element Wrapper</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ELEMENT_REF__XML_ELEMENT_WRAPPER = eINSTANCE.getEXmlElementRef_XmlElementWrapper();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs <em>EXml Element Refs</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementRefs()
		 * @generated
		 */
		public static final EClass EXML_ELEMENT_REFS = eINSTANCE.getEXmlElementRefs();

		/**
		 * The meta object literal for the '<em><b>Xml Mixed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_REFS__XML_MIXED = eINSTANCE.getEXmlElementRefs_XmlMixed();

		/**
		 * The meta object literal for the '<em><b>Xml Element Refs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ELEMENT_REFS__XML_ELEMENT_REFS = eINSTANCE.getEXmlElementRefs_XmlElementRefs();

		/**
		 * The meta object literal for the '<em><b>Xml Element Wrapper</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ELEMENT_REFS__XML_ELEMENT_WRAPPER = eINSTANCE.getEXmlElementRefs_XmlElementWrapper();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements <em>EXml Elements</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElements()
		 * @generated
		 */
		public static final EClass EXML_ELEMENTS = eINSTANCE.getEXmlElements();

		/**
		 * The meta object literal for the '<em><b>Xml Id Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENTS__XML_ID_REF = eINSTANCE.getEXmlElements_XmlIdRef();

		/**
		 * The meta object literal for the '<em><b>Xml List</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENTS__XML_LIST = eINSTANCE.getEXmlElements_XmlList();

		/**
		 * The meta object literal for the '<em><b>Xml Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ELEMENTS__XML_ELEMENTS = eINSTANCE.getEXmlElements_XmlElements();

		/**
		 * The meta object literal for the '<em><b>Xml Element Wrapper</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ELEMENTS__XML_ELEMENT_WRAPPER = eINSTANCE.getEXmlElements_XmlElementWrapper();

		/**
		 * The meta object literal for the '<em><b>Xml Join Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ELEMENTS__XML_JOIN_NODES = eINSTANCE.getEXmlElements_XmlJoinNodes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper <em>EXml Element Wrapper</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementWrapper
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementWrapper()
		 * @generated
		 */
		public static final EClass EXML_ELEMENT_WRAPPER = eINSTANCE.getEXmlElementWrapper();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_WRAPPER__NAME = eINSTANCE.getEXmlElementWrapper_Name();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_WRAPPER__NAMESPACE = eINSTANCE.getEXmlElementWrapper_Namespace();

		/**
		 * The meta object literal for the '<em><b>Nillable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_WRAPPER__NILLABLE = eINSTANCE.getEXmlElementWrapper_Nillable();

		/**
		 * The meta object literal for the '<em><b>Required</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ELEMENT_WRAPPER__REQUIRED = eINSTANCE.getEXmlElementWrapper_Required();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum <em>EXml Enum</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlEnum()
		 * @generated
		 */
		public static final EClass EXML_ENUM = eINSTANCE.getEXmlEnum();

		/**
		 * The meta object literal for the '<em><b>Java Enum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ENUM__JAVA_ENUM = eINSTANCE.getEXmlEnum_JavaEnum();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ENUM__VALUE = eINSTANCE.getEXmlEnum_Value();

		/**
		 * The meta object literal for the '<em><b>Xml Enum Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_ENUM__XML_ENUM_VALUES = eINSTANCE.getEXmlEnum_XmlEnumValues();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnumValue <em>EXml Enum Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnumValue
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlEnumValue()
		 * @generated
		 */
		public static final EClass EXML_ENUM_VALUE = eINSTANCE.getEXmlEnumValue();

		/**
		 * The meta object literal for the '<em><b>Java Enum Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ENUM_VALUE__JAVA_ENUM_VALUE = eINSTANCE.getEXmlEnumValue_JavaEnumValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlInverseReference <em>EXml Inverse Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlInverseReference
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlInverseReference()
		 * @generated
		 */
		public static final EClass EXML_INVERSE_REFERENCE = eINSTANCE.getEXmlInverseReference();

		/**
		 * The meta object literal for the '<em><b>Mapped By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_INVERSE_REFERENCE__MAPPED_BY = eINSTANCE.getEXmlInverseReference_MappedBy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetNullPolicy <em>EXml Is Set Null Policy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetNullPolicy
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlIsSetNullPolicy()
		 * @generated
		 */
		public static final EClass EXML_IS_SET_NULL_POLICY = eINSTANCE.getEXmlIsSetNullPolicy();

		/**
		 * The meta object literal for the '<em><b>Is Set Method Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_IS_SET_NULL_POLICY__IS_SET_METHOD_NAME = eINSTANCE.getEXmlIsSetNullPolicy_IsSetMethodName();

		/**
		 * The meta object literal for the '<em><b>Xml Is Set Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_IS_SET_NULL_POLICY__XML_IS_SET_PARAMETERS = eINSTANCE.getEXmlIsSetNullPolicy_XmlIsSetParameters();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetParameter <em>EXml Is Set Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetParameter
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlIsSetParameter()
		 * @generated
		 */
		public static final EClass EXML_IS_SET_PARAMETER = eINSTANCE.getEXmlIsSetParameter();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_IS_SET_PARAMETER__VALUE = eINSTANCE.getEXmlIsSetParameter_Value();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_IS_SET_PARAMETER__TYPE = eINSTANCE.getEXmlIsSetParameter_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter <em>EXml Java Type Adapter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlJavaTypeAdapter()
		 * @generated
		 */
		public static final EClass EXML_JAVA_TYPE_ADAPTER = eINSTANCE.getEXmlJavaTypeAdapter();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_JAVA_TYPE_ADAPTER__VALUE = eINSTANCE.getEXmlJavaTypeAdapter_Value();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_JAVA_TYPE_ADAPTER__TYPE = eINSTANCE.getEXmlJavaTypeAdapter_Type();

		/**
		 * The meta object literal for the '<em><b>Value Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_JAVA_TYPE_ADAPTER__VALUE_TYPE = eINSTANCE.getEXmlJavaTypeAdapter_ValueType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode <em>EXml Join Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlJoinNode()
		 * @generated
		 */
		public static final EClass EXML_JOIN_NODE = eINSTANCE.getEXmlJoinNode();

		/**
		 * The meta object literal for the '<em><b>Xml Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_JOIN_NODE__XML_PATH = eINSTANCE.getEXmlJoinNode_XmlPath();

		/**
		 * The meta object literal for the '<em><b>Referenced Xml Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_JOIN_NODE__REFERENCED_XML_PATH = eINSTANCE.getEXmlJoinNode_ReferencedXmlPath();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNodes <em>EXml Join Nodes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNodes
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlJoinNodes()
		 * @generated
		 */
		public static final EClass EXML_JOIN_NODES = eINSTANCE.getEXmlJoinNodes();

		/**
		 * The meta object literal for the '<em><b>Xml Join Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_JOIN_NODES__XML_JOIN_NODES = eINSTANCE.getEXmlJoinNodes_XmlJoinNodes();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMap <em>EXml Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMap
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlMap()
		 * @generated
		 */
		public static final EClass EXML_MAP = eINSTANCE.getEXmlMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_MAP__KEY = eINSTANCE.getEXmlMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_MAP__VALUE = eINSTANCE.getEXmlMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNullPolicy <em>EXml Null Policy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNullPolicy
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlNullPolicy()
		 * @generated
		 */
		public static final EClass EXML_NULL_POLICY = eINSTANCE.getEXmlNullPolicy();

		/**
		 * The meta object literal for the '<em><b>Is Set Performed For Absent Node</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_NULL_POLICY__IS_SET_PERFORMED_FOR_ABSENT_NODE = eINSTANCE.getEXmlNullPolicy_IsSetPerformedForAbsentNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNs <em>EXml Ns</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNs
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlNs()
		 * @generated
		 */
		public static final EClass EXML_NS = eINSTANCE.getEXmlNs();

		/**
		 * The meta object literal for the '<em><b>Namespace Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_NS__NAMESPACE_URI = eINSTANCE.getEXmlNs_NamespaceUri();

		/**
		 * The meta object literal for the '<em><b>Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_NS__PREFIX = eINSTANCE.getEXmlNs_Prefix();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlProperty <em>EXml Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlProperty
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlProperty()
		 * @generated
		 */
		public static final EClass EXML_PROPERTY = eINSTANCE.getEXmlProperty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_PROPERTY__NAME = eINSTANCE.getEXmlProperty_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_PROPERTY__VALUE = eINSTANCE.getEXmlProperty_Value();

		/**
		 * The meta object literal for the '<em><b>Value Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_PROPERTY__VALUE_TYPE = eINSTANCE.getEXmlProperty_ValueType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlReadTransformer <em>EXml Read Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlReadTransformer
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlReadTransformer()
		 * @generated
		 */
		public static final EClass EXML_READ_TRANSFORMER = eINSTANCE.getEXmlReadTransformer();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRegistry <em>EXml Registry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRegistry
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlRegistry()
		 * @generated
		 */
		public static final EClass EXML_REGISTRY = eINSTANCE.getEXmlRegistry();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_REGISTRY__NAME = eINSTANCE.getEXmlRegistry_Name();

		/**
		 * The meta object literal for the '<em><b>Xml Element Decls</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_REGISTRY__XML_ELEMENT_DECLS = eINSTANCE.getEXmlRegistry_XmlElementDecls();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRootElement <em>EXml Root Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRootElement
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlRootElement()
		 * @generated
		 */
		public static final EClass EXML_ROOT_ELEMENT = eINSTANCE.getEXmlRootElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ROOT_ELEMENT__NAME = eINSTANCE.getEXmlRootElement_Name();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_ROOT_ELEMENT__NAMESPACE = eINSTANCE.getEXmlRootElement_Namespace();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema <em>EXml Schema</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSchema()
		 * @generated
		 */
		public static final EClass EXML_SCHEMA = eINSTANCE.getEXmlSchema();

		/**
		 * The meta object literal for the '<em><b>Attribute Form Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_SCHEMA__ATTRIBUTE_FORM_DEFAULT = eINSTANCE.getEXmlSchema_AttributeFormDefault();

		/**
		 * The meta object literal for the '<em><b>Element Form Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_SCHEMA__ELEMENT_FORM_DEFAULT = eINSTANCE.getEXmlSchema_ElementFormDefault();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_SCHEMA__LOCATION = eINSTANCE.getEXmlSchema_Location();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_SCHEMA__NAMESPACE = eINSTANCE.getEXmlSchema_Namespace();

		/**
		 * The meta object literal for the '<em><b>Xmlns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_SCHEMA__XMLNS = eINSTANCE.getEXmlSchema_Xmlns();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchemaType <em>EXml Schema Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchemaType
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSchemaType()
		 * @generated
		 */
		public static final EClass EXML_SCHEMA_TYPE = eINSTANCE.getEXmlSchemaType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_SCHEMA_TYPE__NAME = eINSTANCE.getEXmlSchemaType_Name();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_SCHEMA_TYPE__NAMESPACE = eINSTANCE.getEXmlSchemaType_Namespace();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_SCHEMA_TYPE__TYPE = eINSTANCE.getEXmlSchemaType_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransformation <em>EXml Transformation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransformation
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlTransformation()
		 * @generated
		 */
		public static final EClass EXML_TRANSFORMATION = eINSTANCE.getEXmlTransformation();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_TRANSFORMATION__OPTIONAL = eINSTANCE.getEXmlTransformation_Optional();

		/**
		 * The meta object literal for the '<em><b>Xml Read Transformer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_TRANSFORMATION__XML_READ_TRANSFORMER = eINSTANCE.getEXmlTransformation_XmlReadTransformer();

		/**
		 * The meta object literal for the '<em><b>Xml Write Transformers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_TRANSFORMATION__XML_WRITE_TRANSFORMERS = eINSTANCE.getEXmlTransformation_XmlWriteTransformers();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransient <em>EXml Transient</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlTransient
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlTransient()
		 * @generated
		 */
		public static final EClass EXML_TRANSIENT = eINSTANCE.getEXmlTransient();

		/**
		 * The meta object literal for the '<em><b>Xml Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_TRANSIENT__XML_LOCATION = eINSTANCE.getEXmlTransient_XmlLocation();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType <em>EXml Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlType()
		 * @generated
		 */
		public static final EClass EXML_TYPE = eINSTANCE.getEXmlType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_TYPE__NAME = eINSTANCE.getEXmlType_Name();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_TYPE__NAMESPACE = eINSTANCE.getEXmlType_Namespace();

		/**
		 * The meta object literal for the '<em><b>Factory Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_TYPE__FACTORY_CLASS = eINSTANCE.getEXmlType_FactoryClass();

		/**
		 * The meta object literal for the '<em><b>Factory Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_TYPE__FACTORY_METHOD = eINSTANCE.getEXmlType_FactoryMethod();

		/**
		 * The meta object literal for the '<em><b>Prop Order</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_TYPE__PROP_ORDER = eINSTANCE.getEXmlType_PropOrder();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue <em>EXml Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlValue
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlValue()
		 * @generated
		 */
		public static final EClass EXML_VALUE = eINSTANCE.getEXmlValue();

		/**
		 * The meta object literal for the '<em><b>Cdata</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_VALUE__CDATA = eINSTANCE.getEXmlValue_Cdata();

		/**
		 * The meta object literal for the '<em><b>Xml Abstract Null Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference EXML_VALUE__XML_ABSTRACT_NULL_POLICY = eINSTANCE.getEXmlValue_XmlAbstractNullPolicy();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods <em>EXml Virtual Access Methods</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlVirtualAccessMethods()
		 * @generated
		 */
		public static final EClass EXML_VIRTUAL_ACCESS_METHODS = eINSTANCE.getEXmlVirtualAccessMethods();

		/**
		 * The meta object literal for the '<em><b>Get Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_VIRTUAL_ACCESS_METHODS__GET_METHOD = eINSTANCE.getEXmlVirtualAccessMethods_GetMethod();

		/**
		 * The meta object literal for the '<em><b>Set Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_VIRTUAL_ACCESS_METHODS__SET_METHOD = eINSTANCE.getEXmlVirtualAccessMethods_SetMethod();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_VIRTUAL_ACCESS_METHODS__SCHEMA = eINSTANCE.getEXmlVirtualAccessMethods_Schema();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlWriteTransformer <em>EXml Write Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlWriteTransformer
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlWriteTransformer()
		 * @generated
		 */
		public static final EClass EXML_WRITE_TRANSFORMER = eINSTANCE.getEXmlWriteTransformer();

		/**
		 * The meta object literal for the '<em><b>Xml Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute EXML_WRITE_TRANSFORMER__XML_PATH = eINSTANCE.getEXmlWriteTransformer_XmlPath();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessOrder <em>EXml Access Order</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessOrder
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAccessOrder()
		 * @generated
		 */
		public static final EEnum EXML_ACCESS_ORDER = eINSTANCE.getEXmlAccessOrder();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType <em>EXml Access Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAccessType()
		 * @generated
		 */
		public static final EEnum EXML_ACCESS_TYPE = eINSTANCE.getEXmlAccessType();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMarshalNullRepresentation <em>EXml Marshal Null Representation</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMarshalNullRepresentation
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlMarshalNullRepresentation()
		 * @generated
		 */
		public static final EEnum EXML_MARSHAL_NULL_REPRESENTATION = eINSTANCE.getEXmlMarshalNullRepresentation();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNsForm <em>EXml Ns Form</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNsForm
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlNsForm()
		 * @generated
		 */
		public static final EEnum EXML_NS_FORM = eINSTANCE.getEXmlNsForm();

		/**
		 * The meta object literal for the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethodsSchema <em>EXml Virtual Access Methods Schema</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethodsSchema
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlVirtualAccessMethodsSchema()
		 * @generated
		 */
		public static final EEnum EXML_VIRTUAL_ACCESS_METHODS_SCHEMA = eINSTANCE.getEXmlVirtualAccessMethodsSchema();

		/**
		 * The meta object literal for the '<em>EProp Order</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.List
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEPropOrder()
		 * @generated
		 */
		public static final EDataType EPROP_ORDER = eINSTANCE.getEPropOrder();

		/**
		 * The meta object literal for the '<em>EXml See Also</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.List
		 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSeeAlso()
		 * @generated
		 */
		public static final EDataType EXML_SEE_ALSO = eINSTANCE.getEXmlSeeAlso();

	}

} //OxmPackage
