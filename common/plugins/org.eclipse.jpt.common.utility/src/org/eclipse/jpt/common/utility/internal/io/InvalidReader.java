/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.io;

import java.io.IOException;
import java.io.Reader;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This reader will throw an exception
 * any time it is read from. Closing the reader
 * will <em>not</em> trigger an exception.
 */
public final class InvalidReader
	extends Reader
{
	// singleton
	private static Reader INSTANCE = new InvalidReader();

	/**
	 * Return the singleton.
	 */
	public static synchronized Reader instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private InvalidReader() {
		super();
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() throws IOException {
		// do nothing
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}
