/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility;

import org.eclipse.jpt.core.utility.AbstractTextRange;

/**
 * Straightforward implementation of TextRange.
 */
public class SimpleTextRange extends AbstractTextRange {
	private final int offset;
	private final int length;
	private final int lineNumber;

	public SimpleTextRange(int offset, int length, int lineNumber) {
		super();
		this.offset = offset;
		this.length = length;
		this.lineNumber = lineNumber;
	}

	public int offset() {
		return this.offset;
	}

	public int length() {
		return this.length;
	}

	public int lineNumber() {
		return this.lineNumber;
	}

}
