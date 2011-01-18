/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
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
	
	// validation on package
	public static final String PACKAGE_NO_SCHEMA_FOR_NAMESPACE = "PACKAGE_NO_SCHEMA_FOR_NAMESPACE";
	
	// validation on type
	public static final String XML_TYPE_UNMATCHING_NAMESPACE_FOR_ANONYMOUS_TYPE = "XML_TYPE_UNMATCHING_NAMESPACE_FOR_ANONYMOUS_TYPE";
	public static final String XML_TYPE_UNRESOLVED_SCHEMA_TYPE = "XML_TYPE_UNRESOLVED_SCHEMA_TYPE";
	
	public static final String XML_ROOT_ELEMENT_UNRESOLVED_SCHEMA_ELEMENT = "XML_ROOT_ELEMENT_UNRESOLVED_SCHEMA_ELEMENT";
	public static final String XML_ROOT_ELEMENT_TYPE_CONFLICTS_WITH_XML_TYPE = "XML_ROOT_ELEMENT_TYPE_CONFLICTS_WITH_XML_TYPE";
}
