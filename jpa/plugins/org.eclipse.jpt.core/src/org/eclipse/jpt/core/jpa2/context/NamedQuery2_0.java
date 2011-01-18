/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.jpa2.context;

import org.eclipse.jpt.core.context.NamedQuery;

/**
 * JPA 2.0 named query
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface NamedQuery2_0
	extends NamedQuery
{
	// ********** lock mode **********

	/**
	 * Return the specified lock mode if present, otherwise return the default
	 * lock mode.
	 */
	LockModeType2_0 getLockMode();
	LockModeType2_0 getSpecifiedLockMode();
	void setSpecifiedLockMode(LockModeType2_0 lockMode);
		String SPECIFIED_LOCK_MODE_PROPERTY = "specifiedLockMode"; //$NON-NLS-1$
	LockModeType2_0 getDefaultLockMode();
		String DEFAULT_LOCK_MODE_PROPERTY = "defaultLockMode"; //$NON-NLS-1$
}
