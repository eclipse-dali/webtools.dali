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
package org.eclipse.jpt.jaxb.eclipselink.core.context.oxm;

import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute;

public interface OxmAttributeMappingDefinition {
	
	/**
	 * Return the mapping key {@see ELJaxbMappingKeys} for this mapping definition.
	 */
	String getKey();
	
	/**
	 * Return the XML element used for this mapping definition
	 */
	String getElement();
	
	/**
	 * Build the corresponding EMF EJavaAttribute.
	 */
	EJavaAttribute buildEJavaAttribute();
	
	/**
	 * Build a context mapping for the given parent and resource mapping.
	 */
	OxmAttributeMapping buildContextMapping(OxmJavaAttribute parent, EJavaAttribute resourceMapping);	
}
