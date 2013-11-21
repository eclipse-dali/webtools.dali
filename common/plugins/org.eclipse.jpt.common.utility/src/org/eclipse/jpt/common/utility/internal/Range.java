/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;

/**
 * This simple container class simply puts a bit of semantics
 * around a pair of numbers.
 * <p>
 * <strong>NB:</strong> The {@link #start} will be less than (or equal to)
 * the {@link #end}.
 */
public class Range
	implements Cloneable, Serializable
{
	/**
	 * The range's starting index. This will always be less than or equal to
	 * {@link #end the range's ending index}.
	 */
	public final int start;

	/**
	 * The range's ending index. This will always be greater than or equal to
	 * {@link #start the range's starting index}.
	 */
	public final int end;

	/**
	 * The range's length. The range's length will never be negative.
	 */
	public final int length;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a range with the specified start and end, both of which are
	 * mmutable. If the specified end is less than the specified start, the
	 * values will be swapped in the range.
	 */
	public Range(int start, int end) {
		super();
		this.start = Math.min(start, end);
		this.end = Math.max(start, end);
		this.length = this.end - this.start + 1;
	}

	/**
	 * Return whether the range includes the specified
	 * index.
	 */
	public boolean includes(int index) {
		return (this.start <= index) && (index <= this.end);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ( ! (o instanceof Range)) {
			return false;
		}
		Range otherRange = (Range) o;
		return (this.start == otherRange.start)
			&& (this.end == otherRange.end);
	}

	@Override
	public int hashCode() {
		return this.start ^ this.end;
	}

	@Override
	public Range clone() {
		try {
			return (Range) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		this.toString(sb);
		return sb.toString();
	}

	public void toString(StringBuilder sb) {
		sb.append('[');
		sb.append(this.start);
		sb.append(", "); //$NON-NLS-1$
		sb.append(this.end);
		sb.append(']');
	}
}
