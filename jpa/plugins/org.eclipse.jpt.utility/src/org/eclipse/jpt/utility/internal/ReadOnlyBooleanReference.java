/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * Interface for a container for holding a <code>boolean</code> that cannot be
 * changed by clients.
 */
public interface ReadOnlyBooleanReference
{
	/**
	 * Return the current <code>boolean</code> value.
	 */
	boolean getValue();

	/**
	 * Return whether the current <code>boolean</code> value is equal to the
	 * specified value.
	 */
	boolean is(boolean value);

	/**
	 * Return whether the current <code>boolean</code> value is not equal to
	 * the specified value.
	 */
	boolean isNot(boolean value);

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
