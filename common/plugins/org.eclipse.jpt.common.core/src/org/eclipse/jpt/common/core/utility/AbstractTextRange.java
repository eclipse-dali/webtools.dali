/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility;

import org.eclipse.jpt.common.core.internal.utility.SimpleTextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Partial implementation of text range.
 * 
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.0
 * @since 2.0
 */
public abstract class AbstractTextRange
	implements TextRange
{
	protected AbstractTextRange() {
		super();
	}

	public boolean includes(int index) {
		return (this.getOffset() <= index) && (index < this.getEnd());
	}

	public boolean touches(int index) {
		return (this.getOffset() <= index) && (index <= this.getEnd());
	}

	/**
	 * The end offset is <em>exclusive</em>, i.e. the element at the end offset
	 * is not included in the range.
	 */
	protected int getEnd() {
		return this.getOffset() + this.getLength();
	}

	public TextRange buildTextRange(int offset, int length, int lineNumber) {
		return ((offset == this.getOffset()) &&
				(length == this.getLength()) &&
				(lineNumber == this.getLineNumber())) ?
					this :
					new SimpleTextRange(offset, length, lineNumber);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if ( ! (o instanceof TextRange)) {
			return false;
		}
		TextRange other = (TextRange) o;
		return (other.getOffset() == this.getOffset())
				&& (other.getLength() == this.getLength())
				&& (other.getLineNumber() == this.getLineNumber());
	}

	@Override
	public int hashCode() {
		return this.getOffset() ^ this.getLength() ^ this.getLineNumber();
	}

	@Override
	public String toString() {
		String start = String.valueOf(this.getOffset());
		String end = String.valueOf(this.getEnd());
		String line = String.valueOf(this.getLineNumber());
		return StringTools.buildToStringFor(this, start + ", " + end + " [" + line + ']'); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
