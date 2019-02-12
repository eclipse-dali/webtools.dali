/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.comparator;

import java.text.Collator;
import java.util.Comparator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

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
	 * Wrap the specified collator.
	 */
	public StringCollator(Collator collator) {
		super();
		if (collator == null) {
			throw new NullPointerException();
		}
		this.collator = collator;
	}

	public int compare(String string1, String string2) {
		return this.collator.compare(string1, string2);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.collator);
	}
}
