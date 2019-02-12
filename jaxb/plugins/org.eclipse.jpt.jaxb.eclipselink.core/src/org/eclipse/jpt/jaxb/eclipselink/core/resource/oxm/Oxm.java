/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.ContentTypeTools;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.plugin.JptJaxbEclipseLinkCorePlugin;

public interface Oxm {
	
	IContentType CONTENT_TYPE = JptJaxbEclipseLinkCorePlugin.instance().getContentType("oxm");
	
	String SCHEMA_NAMESPACE = "http://www.eclipse.org/eclipselink/xsds/persistence/oxm";
	
	String SCHEMA_VERSION_2_1 = "2.1";
	String SCHEMA_LOCATION_2_1 = "http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_1.xsd";
	
	JptResourceType RESOURCE_TYPE_2_1 = ContentTypeTools.getResourceType(CONTENT_TYPE, SCHEMA_VERSION_2_1);
	
	String SCHEMA_VERSION_2_2 = "2.2";
	String SCHEMA_LOCATION_2_2 = "http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_2.xsd";
	
	JptResourceType RESOURCE_TYPE_2_2 = ContentTypeTools.getResourceType(CONTENT_TYPE, SCHEMA_VERSION_2_2);
	
	String SCHEMA_VERSION_2_3 = "2.3";
	String SCHEMA_LOCATION_2_3 = "http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_3.xsd";
	
	JptResourceType RESOURCE_TYPE_2_3 = ContentTypeTools.getResourceType(CONTENT_TYPE, SCHEMA_VERSION_2_3);
	
	String SCHEMA_VERSION_2_4 = "2.4";
	String SCHEMA_LOCATION_2_4 = "http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_4.xsd";
	
	JptResourceType RESOURCE_TYPE_2_4 = ContentTypeTools.getResourceType(CONTENT_TYPE, SCHEMA_VERSION_2_4);
	
	String SCHEMA_VERSION_2_5 = "2.5";
	String SCHEMA_LOCATION_2_5 = "http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_5.xsd";
	
	JptResourceType RESOURCE_TYPE_2_5 = ContentTypeTools.getResourceType(CONTENT_TYPE, SCHEMA_VERSION_2_5);
	
	
	// Oxm specific nodes
	String ATTRIBUTE_FORM_DEFAULT = "attribute-form-default"; //$NON-NLS-1$
	String ELEMENT_FORM_DEFAULT = "element-form-default"; //$NON-NLS-1$
	String FACTORY_CLASS = "factory-class"; //$NON-NLS-1$
	String FACTORY_METHOD = "factory-method"; //$NON-NLS-1$
	String JAVA_ATTRIBUTE = "java-attribute"; //$NON-NLS-1$
	String JAVA_ATTRIBUTES = "java-attributes"; //$NON-NLS-1$
	String JAVA_ENUM = "java-enum"; //$NON-NLS-1$
	String JAVA_TYPE = "java-type"; //$NON-NLS-1$
	String JAVA_TYPES = "java-types"; //$NON-NLS-1$
	String LOCATION = "location"; //$NON-NLS-1$
	String NAME = "name"; //$NON-NLS-1$
	String NAMESPACE = "namespace"; //$NON-NLS-1$
	String PACKAGE_NAME = "package-name"; //$NON-NLS-1$
	String PROP_ORDER = "prop-order"; //$NON-NLS-1$
	String SUPER_TYPE = "super-type"; //$NON-NLS-1$
	String VALUE = "value"; //$NON-NLS-1$
	String XML_ACCESSOR_ORDER = "xml-accessor-order"; //$NON-NLS-1$
	String XML_ACCESSOR_TYPE = "xml-accessor-type"; //$NON-NLS-1$
	String XML_ANY_ATTRIBUTE = "xml-any-attribute"; //$NON-NLS-1$
	String XML_ANY_ELEMENT = "xml-any-element"; //$NON-NLS-1$
	String XML_ATTRIBUTE = "xml-attribute"; //$NON-NLS-1$
	String XML_BINDINGS = "xml-bindings"; //$NON-NLS-1$
	String XML_ELEMENT = "xml-element"; //$NON-NLS-1$
	String XML_ELEMENT_REF = "xml-element-ref"; //$NON-NLS-1$
	String XML_ELEMENT_REFS = "xml-element-refs"; //$NON-NLS-1$
	String XML_ELEMENTS = "xml-elements"; //$NON-NLS-1$
	String XML_ENUM = "xml-enum"; //$NON-NLS-1$
	String XML_ENUMS = "xml-enums"; //$NON-NLS-1$
	String XML_INVERSE_REFERENCE = "xml-inverse-reference"; //$NON-NLS-1$
	String XML_IS_SET_NULL_POLICY = "xml-is-set-null-policy"; //$NON-NLS-1$
	String XML_JAVA_TYPE_ADAPTER = "xml-java-type-adapter"; //$NON-NLS-1$
	String XML_JOIN_NODES = "xml-join-nodes"; //$NON-NLS-1$
	String XML_MAPPING_METADATA_COMPLETE = "xml-mapping-metadata-complete"; //$NON-NLS-1$
	String XML_NULL_POLICY = "xml-null-policy"; //$NON-NLS-1$
	String XML_ROOT_ELEMENT = "xml-root-element"; //$NON-NLS-1$
	String XML_SCHEMA = "xml-schema"; //$NON-NLS-1$
	String XML_SEE_ALSO = "xml-see-also"; //$NON-NLS-1$
	String XML_TRANSFORMATION = "xml-transformation"; //$NON-NLS-1$
	String XML_TRANSIENT = "xml-transient"; //$NON-NLS-1$
	String XML_TYPE = "xml-type"; //$NON-NLS-1$
	String XML_VALUE = "xml-value"; //$NON-NLS-1$
}
