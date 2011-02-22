/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;

/**
 * This collator simply wraps the default Java collator and implements a
 * {@link String} {@link Comparator} (instead of an {@link Object}
 * {@link Comparator}, which is what {@link Collator} does, possibly for
 * backward-compatibility reasons(?)).
 * 
 * @see Collator#getInstance()
 */
public class StringCollator
	implements Comparator<String>, Serializable
{
	// singleton
	private static final StringCollator INSTANCE = new StringCollator();

	/**
	 * Return the singleton.
	 */
	public static Comparator<String> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private StringCollator() {
		super();
	}

	public int compare(String string1, String string2) {
		return Collator.getInstance().compare(string1, string2);
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
