/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

/**
 * JAXB Java-related stuff (annotations etc.)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
@SuppressWarnings("nls")
public interface JAXB {
	
	// Generic java
	
	String CORE_JAVA_TYPE_PACKAGE_PREFIX = "java";
	
	
	// JAXB packages
	
	String JAXB_PACKAGE = "javax.xml.bind";
	String JAXB_PACKAGE_ = JAXB_PACKAGE + '.';
	
	String JAXB_ANNOTATION_PACKAGE = "javax.xml.bind.annotation";
	String JAXB_ANNOTATION_PACKAGE_ = JAXB_ANNOTATION_PACKAGE + '.';
	
	String JAXB_ADAPTERS_PACKAGE = JAXB_ANNOTATION_PACKAGE_ + "adapters";
	String JAXB_ADAPTERS_PACKAGE_ = JAXB_ADAPTERS_PACKAGE + '.';
	
	
	// JAXB annotations
	
	String DEFAULT_STRING = "##default";
	
	String XML_ATTACHMENT_REF = JAXB_ANNOTATION_PACKAGE_ + "XmlAttachmentRef";
	
	String XML_ACCESSOR_ORDER = JAXB_ANNOTATION_PACKAGE_ + "XmlAccessorOrder";
		String XML_ACCESSOR_ORDER__VALUE = "value";
	
	String XML_ACCESSOR_TYPE = JAXB_ANNOTATION_PACKAGE_ + "XmlAccessorType";
		String XML_ACCESSOR_TYPE__VALUE = "value";
	
	String XML_ANY_ATTRIBUTE = JAXB_ANNOTATION_PACKAGE_ + "XmlAnyAttribute";
	
	String XML_ANY_ELEMENT = JAXB_ANNOTATION_PACKAGE_ + "XmlAnyElement";
		String XML_ANY_ELEMENT__LAX = "lax";
		String XML_ANY_ELEMENT__VALUE = "value";
	
	String XML_ATTRIBUTE = JAXB_ANNOTATION_PACKAGE_ + "XmlAttribute";
		String XML_ATTRIBUTE__NAME = "name";
		String XML_ATTRIBUTE__NAMESPACE = "namespace";
		String XML_ATTRIBUTE__REQUIRED = "required";
	
	String XML_ELEMENT = JAXB_ANNOTATION_PACKAGE_ + "XmlElement";
		String XML_ELEMENT__NAME = "name";
		String XML_ELEMENT__NAMESPACE = "namespace";
		String XML_ELEMENT__DEFAULT_VALUE = "defaultValue";
		String XML_ELEMENT__NILLABLE = "nillable";
		String XML_ELEMENT__REQUIRED = "required";
		String XML_ELEMENT__TYPE = "type";
	
	String XML_ELEMENT_DECL = JAXB_ANNOTATION_PACKAGE_ + "XmlElementDecl";
		String XML_ELEMENT_DECL__NAME = "name";
		String XML_ELEMENT_DECL__NAMESPACE = "namespace";
		String XML_ELEMENT_DECL__DEFAULT_VALUE = "defaultValue";
		String XML_ELEMENT_DECL__SCOPE = "scope";
		String XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME = "substitutionHeadName";
		String XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE = "substitutionHeadNamespace";
	
	String XML_ELEMENTS = JAXB_ANNOTATION_PACKAGE_ + "XmlElements";
		String XML_ELEMENTS__VALUE = "value";
	
	String XML_ELEMENT_REF = JAXB_ANNOTATION_PACKAGE_ + "XmlElementRef";
		String XML_ELEMENT_REF__NAME = "name";
		String XML_ELEMENT_REF__NAMESPACE = "namespace";
		String XML_ELEMENT_REF__REQUIRED = "required";
		String XML_ELEMENT_REF__TYPE = "type";
	
	String XML_ELEMENT_REFS = JAXB_ANNOTATION_PACKAGE_ + "XmlElementRefs";
		String XML_ELEMENT_REFS__VALUE = "value";
	
	String XML_ELEMENT_WRAPPER = JAXB_ANNOTATION_PACKAGE_ + "XmlElementWrapper";
		String XML_ELEMENT_WRAPPER__NAME = "name";
		String XML_ELEMENT_WRAPPER__NAMESPACE = "namespace";
		String XML_ELEMENT_WRAPPER__NILLABLE = "nillable";
		String XML_ELEMENT_WRAPPER__REQUIRED = "required";
	
	String XML_ENUM = JAXB_ANNOTATION_PACKAGE_ + "XmlEnum";
		String XML_ENUM__VALUE = "value";
	
	String XML_ENUM_VALUE = JAXB_ANNOTATION_PACKAGE_ + "XmlEnumValue";
		String XML_ENUM_VALUE__VALUE = "value";
	
	String XML_INLINE_BINARY_DATA = JAXB_ANNOTATION_PACKAGE_ + "XmlInlineBinaryData";
	
	String XML_ID = JAXB_ANNOTATION_PACKAGE_ + "XmlID";
	
	String XML_IDREF = JAXB_ANNOTATION_PACKAGE_ + "XmlIDREF";
	
	String XML_JAVA_TYPE_ADAPTER = JAXB_ADAPTERS_PACKAGE_ + "XmlJavaTypeAdapter";
		String XML_JAVA_TYPE_ADAPTER__TYPE = "type";
		String XML_JAVA_TYPE_ADAPTER__VALUE = "value";
	
	String XML_JAVA_TYPE_ADAPTERS = JAXB_ADAPTERS_PACKAGE_ + "XmlJavaTypeAdapters";
		String XML_JAVA_TYPE_ADAPTERS__VALUE = "value";
	
	String XML_LIST = JAXB_ANNOTATION_PACKAGE_ + "XmlList";
	
	String XML_MIME_TYPE = JAXB_ANNOTATION_PACKAGE_ + "XmlMimeType";
		String XML_MIME_TYPE__VALUE = "value";
	
	String XML_MIXED = JAXB_ANNOTATION_PACKAGE_ + "XmlMixed";
	
	String XML_NS = JAXB_ANNOTATION_PACKAGE_ + "XmlNs";
		String XML_NS__NAMESPACE_URI = "namespaceURI";
		String XML_NS__PREFIX = "prefix";
	
	String XML_REGISTRY = JAXB_ANNOTATION_PACKAGE_ + "XmlRegistry";
	
	String XML_ROOT_ELEMENT = JAXB_ANNOTATION_PACKAGE_ + "XmlRootElement";
		String XML_ROOT_ELEMENT__NAME = "name";
		String XML_ROOT_ELEMENT__NAMESPACE = "namespace";
	
	String XML_SCHEMA = JAXB_ANNOTATION_PACKAGE_ + "XmlSchema";
		String XML_SCHEMA__ATTRIBUTE_FORM_DEFAULT = "attributeFormDefault";
		String XML_SCHEMA__ELEMENT_FORM_DEFAULT = "elementFormDefault";
		String XML_SCHEMA__LOCATION = "location";
		String XML_SCHEMA__NAMESPACE = "namespace";
		String XML_SCHEMA__XMLNS = "xmlns";
	
	String XML_SCHEMA_TYPE = JAXB_ANNOTATION_PACKAGE_ + "XmlSchemaType";
		String XML_SCHEMA_TYPE__NAME = "name";
		String XML_SCHEMA_TYPE__NAMESPACE = "namespace";
		String XML_SCHEMA_TYPE__TYPE = "type";
	
	String XML_SCHEMA_TYPES = JAXB_ANNOTATION_PACKAGE_ + "XmlSchemaTypes";
		String XML_SCHEMA_TYPES__VALUE = "value";
	
	String XML_SEE_ALSO = JAXB_ANNOTATION_PACKAGE_ + "XmlSeeAlso";
		String XML_SEE_ALSO__VALUE = "value";
	
	String XML_TYPE = JAXB_ANNOTATION_PACKAGE_ + "XmlType";
		String XML_TYPE__FACTORY_CLASS = "factoryClass";
		String XML_TYPE__FACTORY_METHOD = "factoryMethod";
		String XML_TYPE__NAME = "name";
		String XML_TYPE__NAMESPACE = "namespace";
		String XML_TYPE__PROP_ORDER = "propOrder";
		String XML_TYPE__DEFAULT_FACTORY_CLASS = XML_TYPE + ".DEFAULT";
		
	String XML_TRANSIENT = JAXB_ANNOTATION_PACKAGE_ + "XmlTransient";
	
	String XML_VALUE = JAXB_ANNOTATION_PACKAGE_ + "XmlValue";
	
	
	// JAXB enums
	
	String XML_ACCESS_ORDER = JAXB_ANNOTATION_PACKAGE_ + "XmlAccessOrder";
		String XML_ACCESS_ORDER_ = XML_ACCESS_ORDER + '.';
		String XML_ACCESS_ORDER__ALPHABETICAL = XML_ACCESS_ORDER_ + "ALPHABETICAL";
		String XML_ACCESS_ORDER__UNDEFINED = XML_ACCESS_ORDER_ + "UNDEFINED";
	
	String XML_ACCESS_TYPE = JAXB_ANNOTATION_PACKAGE_ + "XmlAccessType";
		String XML_ACCESS_TYPE_ = XML_ACCESS_TYPE + '.';
		String XML_ACCESS_TYPE__FIELD = XML_ACCESS_TYPE_ + "FIELD";
		String XML_ACCESS_TYPE__NONE = XML_ACCESS_TYPE_ + "NONE";
		String XML_ACCESS_TYPE__PROPERTY = XML_ACCESS_TYPE_ + "PROPERTY";
		String XML_ACCESS_TYPE__PUBLIC_MEMBER = XML_ACCESS_TYPE_ + "PUBLIC_MEMBER";
	
	String XML_NS_FORM = JAXB_ANNOTATION_PACKAGE_ + "XmlNsForm";
	String XML_NS_FORM_ = XML_NS_FORM + ".";
		String XML_NS_FORM__QUALIFIED = XML_NS_FORM_ + "QUALIFIED";
		String XML_NS_FORM__UNQUALIFIED = XML_NS_FORM_ + "UNQUALIFIED";
		String XML_NS_FORM__UNSET = XML_NS_FORM_ + "UNSET";
	
	
	// Other JAXB types
	
	String JAXB_ELEMENT = JAXB_PACKAGE_ + "JAXBElement";
	
	String XML_ADAPTER = JAXB_ADAPTERS_PACKAGE_ + "XmlAdapter";
	
}
