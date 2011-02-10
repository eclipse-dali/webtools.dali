/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.validation;

@SuppressWarnings("nls")
public interface JaxbValidationMessages {
	
	// bundle name
	public static final String BUNDLE_NAME = "jaxb_validation";
	
	// validation on project
	public static final String NO_JAXB_PROJECT = "NO_JAXB_PROJECT"; 
	public static final String PROJECT_INVALID_LIBRARY_PROVIDER = "PROJECT_INVALID_LIBRARY_PROVIDER";
	
	// validation on package
	public static final String PACKAGE_NO_SCHEMA_FOR_NAMESPACE = "PACKAGE_NO_SCHEMA_FOR_NAMESPACE";
	public static final String PACKAGE_XML_JAVA_TYPE_ADAPTER_TYPE_NOT_SPECIFIED = "PACKAGE_XML_JAVA_TYPE_ADAPTER_TYPE_NOT_SPECIFIED";
	
	// validation on type
	public static final String XML_TYPE_UNMATCHING_NAMESPACE_FOR_ANONYMOUS_TYPE = "XML_TYPE_UNMATCHING_NAMESPACE_FOR_ANONYMOUS_TYPE";
	public static final String XML_TYPE_UNRESOLVED_SCHEMA_TYPE = "XML_TYPE_UNRESOLVED_SCHEMA_TYPE";

	public static final String XML_ROOT_ELEMENT_UNRESOLVED_SCHEMA_ELEMENT = "XML_ROOT_ELEMENT_UNRESOLVED_SCHEMA_ELEMENT";
	public static final String XML_ROOT_ELEMENT_TYPE_CONFLICTS_WITH_XML_TYPE = "XML_ROOT_ELEMENT_TYPE_CONFLICTS_WITH_XML_TYPE";
	
	// validation on attribute mapping
	public static final String ATTRIBUTE_MAPPING_XML_JAVA_TYPE_ADAPTER_TYPE_NOT_DEFINED = "ATTRIBUTE_MAPPING_XML_JAVA_TYPE_ADAPTER_TYPE_NOT_DEFINED";
	public static final String XML_ELEMENT_WRAPPER_DEFINED_ON_NON_ARRAY_NON_COLLECTION = "XML_ELEMENT_WRAPPER_DEFINED_ON_NON_ARRAY_NON_COLLECTION";
	public static final String XML_LIST_DEFINED_ON_NON_ARRAY_NON_COLLECTION = "XML_LIST_DEFINED_ON_NON_ARRAY_NON_COLLECTION";
	public static final String MULTIPLE_XML_ANY_ATTRIBUTE_MAPPINGS_DEFINED = "MULTIPLE_XML_ANY_ATTRIBUTE_MAPPINGS_DEFINED";
	public static final String MULTIPLE_XML_ANY_ELEMENT_MAPPINGS_DEFINED = "MULTIPLE_XML_ANY_ELEMENT_MAPPINGS_DEFINED";
	public static final String MULTIPLE_XML_VALUE_MAPPINGS_DEFINED = "MULTIPLE_XML_VALUE_MAPPINGS_DEFINED";
	public static final String XML_VALUE_MAPPING_WITH_NON_XML_ATTRIBUTE_MAPPING_DEFINED = "XML_VALUE_MAPPING_WITH_NON_XML_ATTRIBUTE_MAPPING_DEFINED";
	public static final String XML_ANY_ATTRIBUTE_MAPPING_DEFINED_ON_NON_MAP = "XML_ANY_ATTRIBUTE_MAPPING_DEFINED_ON_NON_MAP";
}
