/*******************************************************************************
 * Copyright (c) 2005, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * A text range defines the offset into, length of, and line of a piece
 * of text.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <p>
 * This interface is not intended to be implemented by clients.
 * 
 * @version 2.0
 * @since 2.0
 */
public interface TextRange {

	/**
	 * Return the offset of the text.
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
	 * Build and return a new text range for the specified values if they differ
	 * from the text range. If the specified values match the
	 * text range, simply return the text range unchanged.
	 */
	TextRange buildTextRange(int offset, int length, int lineNumber);

	/**
	 * Return whether the offsets, lengths, and line numbers are the same.
	 */
	boolean equals(Object obj);

	/**
	 * Return a hash code that corresponds to the {@link Object#equals(Object)}
	 * contract.
	 */
	int hashCode();


	/**
	 * Empty implementation of text range.
	 */
	final class Empty extends AbstractTextRange {
		public static final TextRange INSTANCE = new Empty();
		public static TextRange instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Empty() {
			super();
		}
		public int getLineNumber() {
			return IMessage.LINENO_UNSET;
		}
		public int getOffset() {
			return IMessage.OFFSET_UNSET;
		}
		public int getLength() {
			return IMessage.OFFSET_UNSET;
		}
		@Override
		public String toString() {
			return StringTools.buildSingletonToString(this);
		}
	}
}
