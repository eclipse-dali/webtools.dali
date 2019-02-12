/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.reference.BooleanReference;

/**
 * Singleton boolean reference whose value is always <code>true</code>.
 */
public final class TrueBooleanReference
	implements BooleanReference, Serializable
{
	public static final BooleanReference INSTANCE = new TrueBooleanReference();

	public static BooleanReference instance() {
		return INSTANCE;
	}

	// ensure single instance
	private TrueBooleanReference() {
		super();
	}

	public boolean getValue() {
		return true;
	}

	public boolean is(boolean value) {
		return value;
	}

	public boolean isNot(boolean value) {
		return ! value;
	}

	public boolean isTrue() {
		return true;
	}

	public boolean isFalse() {
		return false;
	}

	@Override
	public String toString() {
		return "[true]"; //$NON-NLS-1$
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
