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

import org.eclipse.jpt.core.context.JpaContextNode;

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
public interface DerivedId2_0
	extends JpaContextNode
{
	/**
	 * String associated with changes to the value property of this object
	 */
	public static final String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	
	/**
	 * Return whether this object uses a derived id
	 */
	boolean getValue();
	
	/**
	 * Set whether this object uses a derived id
	 */
	void setValue(boolean newValue);
}
