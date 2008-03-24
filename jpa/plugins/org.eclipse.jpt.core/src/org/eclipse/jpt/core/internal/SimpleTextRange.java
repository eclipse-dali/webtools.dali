/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Straightforward, albeit almost useless, implementation of ITextRange.
 */
public class SimpleTextRange implements TextRange {
	private final int offset;
	private final int length;
	private final int lineNumber;

	public SimpleTextRange(int offset, int length, int lineNumber) {
		super();
		this.offset = offset;
		this.length = length;
		this.lineNumber = lineNumber;
	}

	public int getOffset() {
		return this.offset;
	}

	public int getLength() {
		return this.length;
	}

	public int getLineNumber() {
		return this.lineNumber;
	}

	public boolean includes(int index) {
		return (this.offset <= index) && (index < this.end());
	}

	public boolean touches(int index) {
		return this.includes(index) || (index == this.end());
	}

	/**
	 * The end offset is "exclusive", i.e. the element at the end offset
	 * is not included in the range.
	 */
	private int end() {
		return this.offset + this.length;
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
		return (r.getOffset() == this.offset)
				&& (r.getLength() == this.length);
	}

	@Override
	public int hashCode() {
		return this.offset ^ this.length;
	}

	@Override
	public String toString() {
		String start = String.valueOf(this.offset);
		String end = String.valueOf(this.end());
		return StringTools.buildToStringFor(this, start + ", " + end);
	}

}
