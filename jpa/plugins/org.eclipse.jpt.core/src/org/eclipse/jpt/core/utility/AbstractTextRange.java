/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.utility;

import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Partial implementation of text range.
 * 
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public abstract class AbstractTextRange
	implements TextRange
{

	public boolean includes(int index) {
		return (this.offset() <= index) && (index < this.end());
	}

	public boolean touches(int index) {
		return this.includes(index) || (index == this.end());
	}

	/**
	 * The end offset is "exclusive", i.e. the element at the end offset
	 * is not included in the range.
	 */
	protected int end() {
		return this.offset() + this.length();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if ( ! (o instanceof TextRange)) {
			return false;
		}
		TextRange r = (TextRange) o;
		return (r.offset() == this.offset())
				&& (r.length() == this.length());
	}

	@Override
	public int hashCode() {
		return this.offset() ^ this.length();
	}

	@Override
	public String toString() {
		String start = String.valueOf(this.offset());
		String end = String.valueOf(this.end());
		return StringTools.buildToStringFor(this, start + ", " + end);
	}

}
