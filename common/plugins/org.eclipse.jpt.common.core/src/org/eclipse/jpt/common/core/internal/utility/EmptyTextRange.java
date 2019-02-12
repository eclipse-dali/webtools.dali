/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * Empty implementation of text range.
 */
public final class EmptyTextRange
	extends AbstractTextRange
{
	public static final TextRange INSTANCE = new EmptyTextRange();

	public static TextRange instance() {
		return INSTANCE;
	}

	// ensure single instance
	private EmptyTextRange() {
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
		return ObjectTools.singletonToString(this);
	}
}
