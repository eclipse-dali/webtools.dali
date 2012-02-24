/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.validation;

@SuppressWarnings("nls")
public interface ELJaxbValidationMessages {
	
	// bundle name
	String BUNDLE_NAME = "el_jaxb_validation";
	
	
	// validation on project
	String PROJECT_MISSING_ECLIPSELINK_JAXB_CONTEXT_FACTORY = "PROJECT_MISSING_ECLIPSELINK_JAXB_CONTEXT_FACTORY";
	
	String XML_ELEMENT_DECL__INVALID_METHOD_SIGNATURE_RETURN_TYPE = "XML_ELEMENT_DECL__INVALID_METHOD_SIGNATURE_RETURN_TYPE";
	
	String XML_PATH__NOT_SPECIFIED = "XML_PATH__NOT_SPECIFIED";
	String XML_PATH__ROOT_NOT_SUPPORTED = "XML_PATH__ROOT_NOT_SUPPORTED";
	String XML_PATH__INVALID_FORM_ILLEGAL_SEGMENT = "XML_PATH__INVALID_FORM_ILLEGAL_SEGMENT";
	String XML_PATH__SELF_SEGMENT_MUST_BE_FIRST_SEGMENT = "XML_PATH__SELF_SEGMENT_MUST_BE_FIRST_SEGMENT";
	String XML_PATH__TEXT_SEGMENT_MUST_BE_LAST_SEGMENT = "XML_PATH__TEXT_SEGMENT_MUST_BE_LAST_SEGMENT";
	String XML_PATH__ATTRIBUTE_SEGMENT_MUST_BE_LAST_SEGMENT = "XML_PATH__ATTRIBUTE_SEGMENT_MUST_BE_LAST_SEGMENT";
	String XML_PATH__INVALID_NS_PREFIX = "XML_PATH__INVALID_NS_PREFIX";
	String XML_PATH__UNRESOLVED_ELEMENT = "XML_PATH__UNRESOLVED_ELEMENT";
	String XML_PATH__UNRESOLVED_ATTRIBUTE = "XML_PATH__UNRESOLVED_ATTRIBUTE";
	
	// used on XmlElements mapping
	String XML_PATH__INSUFFICIENT_XML_PATHS_FOR_XML_ELEMENTS = "XML_PATH__INSUFFICIENT_XML_PATHS_FOR_XML_ELEMENTS";
	String XML_PATH__INSUFFICIENT_XML_ELEMENTS_FOR_XML_PATHS = "XML_PATH__INSUFFICIENT_XML_ELEMENTS_FOR_XML_PATHS";
	
	String XML_INVERSE_REFERENCE__MAPPED_BY_NOT_SPECIFIED = "XML_INVERSE_REFERENCE__MAPPED_BY_NOT_SPECIFIED";
	String XML_INVERSE_REFERENCE__MAPPED_BY_NOT_RESOLVED = "XML_INVERSE_REFERENCE__MAPPED_BY_NOT_RESOLVED";
	String XML_INVERSE_REFERENCE__MAPPED_BY_ILLEGAL_MAPPING_TYPE = "XML_INVERSE_REFERENCE__MAPPED_BY_ILLEGAL_MAPPING_TYPE";
}
