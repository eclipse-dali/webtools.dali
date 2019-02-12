/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context;

import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;

/**
 * Represents an Oxm XmlInverseReference attribute mapping in either
 * java annotations or oxm file.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2
 */
public interface ELXmlInverseReferenceMapping
		extends JaxbAttributeMapping {
	
	// ***** mappedBy *****
	
	/**
	 * String associated with changes to the mappedBy property
	 */
	String MAPPED_BY_PROPERTY = "mappedBy";  ///$NON-NLS-1$
	
	/**
	 * Return the mappedBy property value.
	 * A null indicates it is not specified.
	 */
	String getMappedBy();
	
	/**
	 * Set the mappedBy property value.
	 * Setting to null will unspecify it.
	 */
	void setMappedBy(String mappedBy);
}
