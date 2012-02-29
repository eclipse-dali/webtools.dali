/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.resource.java;

/**
 * EclipseLink JAXB Java-related stuff (annotations etc.)
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
public interface ELJaxb {
	
	// EclipseLink annotations package
	
	String PACKAGE = "org.eclipse.persistence.oxm.annotations";
	String PACKAGE_ = PACKAGE + '.';
	
	
	// Annotations
	
	String XML_CDATA = PACKAGE_ + "XmlCDATA";
	
	String XML_DISCRIMINATOR_NODE = PACKAGE_ + "XmlDiscriminatorNode";
		String XML_DISCRIMINATOR_NODE__VALUE = "value";
	
	String XML_DISCRIMINATOR_VALUE = PACKAGE_ + "XmlDiscriminatorValue";
		String XML_DISCRIMINATOR_VALUE__VALUE = "value";
	
	String XML_INVERSE_REFERENCE = PACKAGE_ + "XmlInverseReference";
		String XML_INVERSE_REFERENCE__MAPPED_BY = "mappedBy";
	
	String XML_JOIN_NODE = PACKAGE_ + "XmlJoinNode";
		String XML_JOIN_NODE__XML_PATH = "xmlPath";
		String XML_JOIN_NODE__REFERENCED_XML_PATH = "referencedXmlPath";
	
	String XML_JOIN_NODES = PACKAGE_ + "XmlJoinNodes";
		String XML_JOIN_NODES__VALUE = "value";
	
	String XML_PATH = PACKAGE_ + "XmlPath";
		String XML_PATH__VALUE = "value";
	
	String XML_PATHS = PACKAGE_ + "XmlPaths";
	
	String XML_TRANSFORMATION = PACKAGE_ + "XmlTransformation";
		String XML_TRANSFORMATION__OPTIONAL = "optional";
}
