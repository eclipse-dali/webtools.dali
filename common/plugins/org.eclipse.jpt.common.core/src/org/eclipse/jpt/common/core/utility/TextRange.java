/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility;

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
 * @see org.eclipse.wst.validation.internal.provisional.core.IMessage
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
}
