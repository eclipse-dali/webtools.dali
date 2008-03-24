/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.utility;

/**
 * A text range defines the offset into, length of, and line of a piece
 * of text.
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface TextRange {

	/**
	 * Returns the offset of the text.
	 *
	 * @return the offset of the text
	 */
	int getOffset();
	
	/**
	 * Return the length of the text.
	 */
	int getLength();

	/**
	 * Return whether the range includes the character at the specified index.
	 */
	boolean includes(int index);

	/**
	 * Return whether the range touches an insertion cursor at the
	 * specified index.
	 */
	boolean touches(int index);

	/**
	 * Return the line number of the text.
	 */
	int getLineNumber();

	/**
	 * Return true if the offsets and lengths are the same.
	 */
	boolean equals(Object obj);

	/**
	 * Return a hash code that corresponds to the #equals() contract.
	 */
	int hashCode();


	/**
	 * Empty implementation of text range.
	 */
	final class Empty implements TextRange {
		public static final TextRange INSTANCE = new Empty();
		public static TextRange instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Empty() {
			super();
		}
		public int getOffset() {
			return 0;
		}
		public int getLength() {
			return 0;
		}
		public boolean includes(int index) {
			return false;
		}
		public boolean touches(int index) {
			return index == 0;  // ???
		}
		public int getLineNumber() {
			return 0;
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
			return (r.getOffset() == 0)
					&& (r.getLength() == 0);
		}
		@Override
		public int hashCode() {
			return 0;
		}
		@Override
		public String toString() {
			return "TextRange.Empty";
		}
	}

}
