/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.resource.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;

/**
 * Corresponds to the JPA 2.0 annotation
 * <code>javax.persistence.NamedQuery</code>
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
public interface NamedQueryAnnotation2_0
	extends NamedQueryAnnotation
{
	// ********** lock mode **********

	/**
	 * Corresponds to the 'lockMode' element of the NamedQuery annotation.
	 * Return null if the element does not exist in Java.
	 */
	LockModeType_2_0 getLockMode();
		String LOCK_MODE_PROPERTY = "lockMode"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'lockMode' element of the NamedQuery annotation.
	 * Set to null to remove the element.
	 */
	void setLockMode(LockModeType_2_0 lockMode);

	/**
	 * Return the {@link TextRange} for the 'lockMode' element. If the element 
	 * does not exist return the {@link TextRange} for the NamedQuery annotation.
	 */
	TextRange getLockModeTextRange();

	/**
	 * Return whether the specified position touches the 'lockMode' element.
	 * Return false if the element does not exist.
	 */
	boolean lockModeTouches(int pos);
}
