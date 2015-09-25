/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.reference;

/**
 * Interface for a container for holding a <code>boolean</code>.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see ModifiableBooleanReference
 */
public interface BooleanReference {

	/**
	 * Return the current <code>boolean</code> value.
	 */
	boolean getValue();

	/**
	 * Return whether the current <code>boolean</code> value is equal to the
	 * specified value.
	 */
	boolean is(boolean b);

	/**
	 * Return whether the current <code>boolean</code> value is not equal to
	 * the specified value.
	 */
	boolean isNot(boolean b);

	/**
	 * Return whether the current <code>boolean</code> value is
	 * <code>true</code>.
	 */
	boolean isTrue();

	/**
	 * Return whether the current <code>boolean</code> value is
	 * <code>false</code>.
	 */
	boolean isFalse();
}
