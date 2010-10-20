/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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

	// JAXB package
	String PACKAGE = "javax.xml.bind.annotation";
	String PACKAGE_ = PACKAGE + '.';

	String ADAPTERS_PACKAGE = PACKAGE_ + "adapters";
	String ADAPTERS_PACKAGE_ = ADAPTERS_PACKAGE + '.';

	// ********** API **********

	// JAXB annotations
	String XML_ATTACHMENT_REF = PACKAGE_ + "XmlAttachmentRef";

	String XML_ACCESSOR_ORDER = PACKAGE_ + "XmlAccessorOrder";
		String XML_ACCESSOR_ORDER__VALUE = "value";

	String XML_ACCESSOR_TYPE = PACKAGE_ + "XmlAccessorType";
		String XML_ACCESSOR_TYPE__VALUE = "value";

	String XML_ANY_ATTRIBUTE = PACKAGE_ + "XmlAnyAttribute";

	String XML_ANY_ELEMENT = PACKAGE_ + "XmlAnyElement";
		String XML_ANY_ELEMENT__LAX = "lax";
		String XML_ANY_ELEMENT__VALUE = "value";

	String XML_ATTRIBUTE = PACKAGE_ + "XmlAttribute";
		String XML_ATTRIBUTE__NAME = "name";
		String XML_ATTRIBUTE__NAMESPACE = "namespace";
		String XML_ATTRIBUTE__REQUIRED = "required";

	String XML_ELEMENT = PACKAGE_ + "XmlElement";
		String XML_ELEMENT__NAME = "name";
		String XML_ELEMENT__NAMESPACE = "namespace";
		String XML_ELEMENT__DEFAULT_VALUE = "defaultValue";
		String XML_ELEMENT__NILLABLE = "nillable";
		String XML_ELEMENT__REQUIRED = "required";
		String XML_ELEMENT__TYPE = "type";

	String XML_ELEMENT_DECL = PACKAGE_ + "XmlElementDecl";
		String XML_ELEMENT_DECL__NAME = "name";
		String XML_ELEMENT_DECL__NAMESPACE = "namespace";
		String XML_ELEMENT_DECL__DEFAULT_VALUE = "defaultValue";
		String XML_ELEMENT_DECL__SCOPE = "scope";
		String XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME = "substitutionHeadName";
		String XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE = "substitutionHeadNamespace";

	String XML_ELEMENTS = PACKAGE_ + "XmlElements";
		String XML_ELEMENTS__VALUE = "value";

	String XML_ELEMENT_REF = PACKAGE_ + "XmlElementRef";
		String XML_ELEMENT_REF__NAME = "name";
		String XML_ELEMENT_REF__NAMESPACE = "namespace";
		String XML_ELEMENT_REF__TYPE = "type";

	String XML_ELEMENT_REFS = PACKAGE_ + "XmlElementRefs";
		String XML_ELEMENT_REFS__VALUE = "value";

	String XML_ELEMENT_WRAPPER = PACKAGE_ + "XmlElementWrapper";
		String XML_ELEMENT_WRAPPER__NAME = "name";
		String XML_ELEMENT_WRAPPER__NAMESPACE = "namespace";
		String XML_ELEMENT_WRAPPER__NILLABLE = "nillable";
		String XML_ELEMENT_WRAPPER__REQUIRED = "required";

	String XML_ENUM = PACKAGE_ + "XmlEnum";
		String XML_ENUM__VALUE = "value";

	String XML_ENUM_VALUE = PACKAGE_ + "XmlEnumValue";
		String XML_ENUM_VALUE__VALUE = "value";
	
	String XML_INLINE_BINARY_DATA = PACKAGE_ + "XmlInlineBinaryData";

	String XML_ID = PACKAGE_ + "XmlID";

	String XML_IDREF = PACKAGE_ + "XmlIDREF";

	String XML_JAVA_TYPE_ADAPTER = ADAPTERS_PACKAGE_ + "XmlJavaTypeAdapter";
		String XML_JAVA_TYPE_ADAPTER__TYPE = "type";
		String XML_JAVA_TYPE_ADAPTER__VALUE = "value";
	
	String XML_JAVA_TYPE_ADAPTERS = ADAPTERS_PACKAGE_ + "XmlJavaTypeAdapters";
		String XML_JAVA_TYPE_ADAPTERS__VALUE = "value";

	String XML_LIST = PACKAGE_ + "XmlList";

	String XML_MIME_TYPE = PACKAGE_ + "XmlMimeType";
		String XML_MIME_TYPE__VALUE = "value";

	String XML_MIXED = PACKAGE_ + "XmlMixed";
	
	String XML_NS = PACKAGE_ + "XmlNs";
		String XML_NS__NAMESPACE_URI = "namespaceURI";
		String XML_NS__PREFIX = "prefix";
	
	String XML_REGISTRY = PACKAGE_ + "XmlRegistry";
	
	String XML_ROOT_ELEMENT = PACKAGE_ + "XmlRootElement";
		String XML_ROOT_ELEMENT__NAME = "name";
		String XML_ROOT_ELEMENT__NAMESPACE = "namespace";
	
	String XML_SCHEMA = PACKAGE_ + "XmlSchema";
		String XML_SCHEMA__ATTRIBUTE_FORM_DEFAULT = "attributeFormDefault";
		String XML_SCHEMA__ELEMENT_FORM_DEFAULT = "elementFormDefault";
		String XML_SCHEMA__LOCATION = "location";
		String XML_SCHEMA__NAMESPACE = "namespace";
		String XML_SCHEMA__XMLNS = "xmlns";
	
	String XML_SCHEMA_TYPE = PACKAGE_ + "XmlSchemaType";
		String XML_SCHEMA_TYPE__NAME = "name";
		String XML_SCHEMA_TYPE__NAMESPACE = "namespace";
		String XML_SCHEMA_TYPE__TYPE = "type";
	
	String XML_SCHEMA_TYPES = PACKAGE_ + "XmlSchemaTypes";
		String XML_SCHEMA_TYPES__VALUE = "value";
	
	String XML_SEE_ALSO = PACKAGE_ + "XmlSeeAlso";
		String XML_SEE_ALSO__VALUE = "value";
	
	String XML_TYPE = PACKAGE_ + "XmlType";
		String XML_TYPE__FACTORY_CLASS = "factoryClass";
		String XML_TYPE__FACTORY_METHOD = "factoryMethod";
		String XML_TYPE__NAME = "name";
		String XML_TYPE__NAMESPACE = "namespace";
		String XML_TYPE__PROP_ORDER = "propOrder";

	String XML_TRANSIENT = PACKAGE_ + "XmlTransient";

	String XML_VALUE = PACKAGE_ + "XmlValue";


	// JAXB enums
	String XML_ACCESS_ORDER = PACKAGE_ + "XmlAccessOrder";
		String XML_ACCESS_ORDER_ = XML_ACCESS_ORDER + '.';
		String XML_ACCESS_ORDER__ALPHABETICAL = XML_ACCESS_ORDER_ + "ALPHABETICAL";
		String XML_ACCESS_ORDER__UNDEFINED = XML_ACCESS_ORDER_ + "UNDEFINED";

	String XML_ACCESS_TYPE = PACKAGE_ + "XmlAccessType";
		String XML_ACCESS_TYPE_ = XML_ACCESS_TYPE + '.';
		String XML_ACCESS_TYPE__FIELD = XML_ACCESS_TYPE_ + "FIELD";
		String XML_ACCESS_TYPE__NONE = XML_ACCESS_TYPE_ + "NONE";
		String XML_ACCESS_TYPE__PROPERTY = XML_ACCESS_TYPE_ + "PROPERTY";
		String XML_ACCESS_TYPE__PUBLIC_MEMBER = XML_ACCESS_TYPE_ + "PUBLIC_MEMBER";
	
	String XML_NS_FORM = PACKAGE_ + "XmlNsForm";
	String XML_NS_FORM_ = XML_NS_FORM + ".";
		String XML_NS_FORM__QUALIFIED = XML_NS_FORM_ + "QUALIFIED";
		String XML_NS_FORM__UNQUALIFIED = XML_NS_FORM_ + "UNQUALIFIED";
		String XML_NS_FORM__UNSET = XML_NS_FORM_ + "UNSET";

}
