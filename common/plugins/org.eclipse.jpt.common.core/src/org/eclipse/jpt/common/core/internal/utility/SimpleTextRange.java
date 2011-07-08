/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import org.eclipse.jpt.common.core.utility.AbstractTextRange;

/**
 * Straightforward implementation of
 * {@link org.eclipse.jpt.common.core.utility.TextRange TextRange}.
 */
public class SimpleTextRange
	extends AbstractTextRange
{
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
}
