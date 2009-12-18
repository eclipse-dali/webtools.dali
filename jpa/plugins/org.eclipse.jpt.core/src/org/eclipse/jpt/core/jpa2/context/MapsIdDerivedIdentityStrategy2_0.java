/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.jpa2.context;

import java.util.Iterator;


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
public interface MapsIdDerivedIdentityStrategy2_0
	extends DerivedIdentityStrategy2_0
{
	/**
	 * String associated with changes to the specified value property of this object
	 */
	public static final String SPECIFIED_VALUE_PROPERTY = "specified-value"; //$NON-NLS-1$
	
	/**
	 * Return the specified value (the id which has been specified in code)
	 * Will return null if no value is specified
	 */
	String getSpecifiedValue();
	
	/**
	 * Set the specified value (the id to specify in code)
	 */
	void setSpecifiedValue(String newValue);
	
	/**
	 * String associated with changes to the default value property of this object
	 */
	public static final String DEFAULT_VALUE_PROPERTY = "default-value"; //$NON-NLS-1$
	
	/**
	 * Return the default value (the id which would be used in the absence of a specified value)
	 */
	String getDefaultValue();
	
	/**
	 * Return the specfied value, or in absence of that, the default value
	 */
	String getValue();
	
	/**
	 * Return a sorted iterator of possible value choices
	 */
	Iterator<String> sortedValueChoices();
}
