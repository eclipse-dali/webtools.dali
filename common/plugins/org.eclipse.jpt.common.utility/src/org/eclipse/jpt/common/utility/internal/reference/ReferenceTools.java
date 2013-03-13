/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

import org.eclipse.jpt.common.utility.reference.BooleanReference;

/**
 * Reference utility methods.
 */
public final class ReferenceTools {
	/**
	 * Return a boolean reference with the specified value.
	 */
	public static BooleanReference booleanReference(boolean value) {
		return value ? TrueBooleanReference.instance() : FalseBooleanReference.instance();
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ReferenceTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
