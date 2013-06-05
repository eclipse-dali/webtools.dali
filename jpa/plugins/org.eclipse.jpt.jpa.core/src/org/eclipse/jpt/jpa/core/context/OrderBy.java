/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * For primary key or specified other key ordering
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.4
 * @since 3.4
 */
public interface OrderBy
		extends JpaContextModel {
	
	/**
	 * String associated with changes to the "key" property
	 */
	static final String KEY_PROPERTY = "key"; //$NON-NLS-1$
	
	/**
	 * A value of <code>null</code> indicates that ordering is by primary key
	 * (see {@link #isByPrimaryKey()})
	 */
	String getKey();
	
	/**
	 * Set to <code>null</code> to indicate ordering by primary key
	 */
	void setKey(String newKey);
	
	/**
	 * Return if ordering is by primary key.
	 * (see {@link #getKey()}
	 */
	boolean isByPrimaryKey();
}
