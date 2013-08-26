/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.comparator;

import java.io.Serializable;
import java.util.Comparator;

/**
 * This comparator will compare {@link Boolean}s and can be configured to sort
 * either boolean first.
 */
public final class TruesFirstBooleanComparator
	implements Comparator<Boolean>, Serializable
{
	public static final Comparator<Boolean> INSTANCE = new TruesFirstBooleanComparator();

	public static Comparator<Boolean> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private TruesFirstBooleanComparator() {
		super();
	}

	public int compare(Boolean b1, Boolean b2) {
		return b1.booleanValue() ? 
				(b2.booleanValue() ? 0 : -1) :
				(b2.booleanValue() ? 1 :  0);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
