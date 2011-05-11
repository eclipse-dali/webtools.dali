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
	
	String XML_INVERSE_REFERENCE = PACKAGE_ + "XmlInverseReference";
		String XML_INVERSE_REFERENCE__MAPPED_BY = "mappedBy";
	
	String XML_TRANSFORMATION = PACKAGE_ + "XmlTransformation";
		String XML_TRANSFORMATION__OPTIONAL = "optional";
}
