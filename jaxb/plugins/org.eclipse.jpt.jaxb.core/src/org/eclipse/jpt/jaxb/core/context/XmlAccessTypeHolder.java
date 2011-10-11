/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;


/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface XmlAccessTypeHolder
		extends JaxbContextNode {
	
	/**
	 * Return the access type, whether specified or defaulted.
	 * This should never return null since at least the default will be set
	 */
	XmlAccessType getAccessType();
	
	/**
	 * String constant associated with changes to the default access type
	 */
	String DEFAULT_ACCESS_TYPE_PROPERTY = "defaultAccessType"; //$NON-NLS-1$
	
	/**
	 * Return the default access type, never null
	 */
	XmlAccessType getDefaultAccessType();
	
	/**
	 * String constant associated with changes to the specified access type
	 */
	String SPECIFIED_ACCESS_TYPE_PROPERTY = "specifiedAccessType"; //$NON-NLS-1$
	
	/**
	 * Return the specified access type;
	 */
	XmlAccessType getSpecifiedAccessType();

	/**
	 * Set the specified access type.
	 */
	void setSpecifiedAccessType(XmlAccessType newSpecifiedAccessType);
}
