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

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * This collator simply wraps a Java text collator and implements a
 * {@link String} {@link Comparator} (instead of an {@link Object}
 * {@link Comparator}, which is what {@link Collator} does, possibly for
 * backward-compatibility reasons(?)).
 * 
 * @see Collator
 */
public class StringCollator
	implements Comparator<String>
{
	private final Collator collator;


	/**
	 * Wrap the default collator.
	 * @see Collator#getInstance()
	 */
	public StringCollator() {
		this(Collator.getInstance());
	}

	/**
	 * Wrap the collator for the specified locale.
	 * @see Collator#getInstance(Locale)
	 */
	public StringCollator(Locale locale) {
		this(Collator.getInstance(locale));
	}

	/**
	 * Wrap the specified collator.
	 */
	public StringCollator(Collator collator) {
		super();
		this.collator = collator;
	}

	public int compare(String string1, String string2) {
		return this.collator.compare(string1, string2);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.collator);
	}
}