/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context;

/**
 * Corresponds to XmlDiscriminatorValue annotation
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
public interface ELXmlDiscriminatorValue {
	
	// ***** value *****
	
	/**
	 * String associated with changes to the value property
	 */
	String VALUE_PROPERTY = "value";  ///$NON-NLS-1$
	
	/**
	 * Return the value property value.
	 * A null indicates it is not specified.
	 */
	String getValue();
	
	/**
	 * Set the value property value.
	 * Null unspecifies the value.
	 */
	void setValue(String value);
}
