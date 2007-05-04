/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

/**
 * A text range defines the offset into, length of, and line of a piece
 * of text.
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 */
public interface ITextRange {

	/**
	 * Returns the offset of the text.
	 *
	 * @return the offset of the text
	 */
	int getOffset();
	
	/**
	 * Returns the length of the text.
	 *
	 * @return the length of the text
	 */
	int getLength();

	/**
	 * Return whether the range includes the specified index.
	 */
	boolean includes(int index);

	/**
	 * Returns the line number of the text.
	 * 
	 * @return line number
	 */
	int getLineNumber();

}
