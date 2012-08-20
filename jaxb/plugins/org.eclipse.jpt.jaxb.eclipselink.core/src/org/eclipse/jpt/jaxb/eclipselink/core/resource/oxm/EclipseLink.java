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

public interface EclipseLink {
	
	String SCHEMA_NAMESPACE = "http://www.eclipse.org/eclipselink/xsds/persistence/oxm";
	
	String SCHEMA_VERSION_2_1 = "2.1";
	String SCHEMA_LOCATION_2_1 = "http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_1.xsd";
	
	String SCHEMA_VERSION_2_2 = "2.2";
	String SCHEMA_LOCATION_2_2 = "http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_2.xsd";
	
	String SCHEMA_VERSION_2_3 = "2.3";
	String SCHEMA_LOCATION_2_3 = "http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_3.xsd";
	
	String SCHEMA_VERSION_2_4 = "2.4";
	String SCHEMA_LOCATION_2_4 = "http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_4.xsd";
	
	
	// EclipseLink specific nodes
	String JAVA_ATTRIBUTES = "java-attributes"; //$NON-NLS-1$
	String JAVA_TYPE = "java-type"; //$NON-NLS-1$
	String JAVA_TYPES = "java-types"; //$NON-NLS-1$
	String XML_ANY_ATTRIBUTE = "xml-any-attribute"; //$NON-NLS-1$
	String XML_ANY_ELEMENT = "xml-any-element"; //$NON-NLS-1$
	String XML_ATTRIBUTE = "xml-attribute"; //$NON-NLS-1$
	String XML_BINDINGS = "xml-bindings"; //$NON-NLS-1$
	String XML_ELEMENT = "xml-element"; //$NON-NLS-1$
	String XML_ELEMENT_REF = "xml-element-ref"; //$NON-NLS-1$
	String XML_ELEMENT_REFS = "xml-element-refs"; //$NON-NLS-1$
	String XML_ELEMENTS = "xml-elements"; //$NON-NLS-1$
	String XML_INVERSE_REFERENCE = "xml-inverse-reference"; //$NON-NLS-1$
	String XML_IS_SET_NULL_POLICY = "xml-is-set-null-policy"; //$NON-NLS-1$
	String XML_JAVA_TYPE_ADAPTER = "xml-java-type-adapter"; //$NON-NLS-1$
	String XML_JOIN_NODES = "xml-join-nodes"; //$NON-NLS-1$
	String XML_NULL_POLICY = "xml-null-policy"; //$NON-NLS-1$
	String XML_TRANSFORMATION = "xml-transformation"; //$NON-NLS-1$
	String XML_TRANSIENT = "xml-transient"; //$NON-NLS-1$
	String XML_VALUE = "xml-value"; //$NON-NLS-1$
}
