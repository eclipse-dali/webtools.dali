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

import java.io.Serializable;
import org.eclipse.jpt.common.utility.reference.BooleanReference;

/**
 * Singleton boolean reference whose value is always <code>false</code>.
 */
public final class FalseBooleanReference
	implements BooleanReference, Serializable
{
	public static final BooleanReference INSTANCE = new FalseBooleanReference();

	public static BooleanReference instance() {
		return INSTANCE;
	}

	// ensure single instance
	private FalseBooleanReference() {
		super();
	}

	public boolean getValue() {
		return false;
	}

	public boolean is(boolean value) {
		return ! value;
	}

	public boolean isNot(boolean value) {
		return value;
	}

	public boolean isTrue() {
		return false;
	}

	public boolean isFalse() {
		return true;
	}

	@Override
	public String toString() {
		return "[false]"; //$NON-NLS-1$
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
