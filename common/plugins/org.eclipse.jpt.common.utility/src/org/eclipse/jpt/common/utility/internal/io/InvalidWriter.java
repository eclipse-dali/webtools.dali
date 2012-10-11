/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.io;

import java.io.IOException;
import java.io.Writer;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This writer will throw an exception
 * any time it is written to. Flushing or closing the writer
 * will <em>not</em> trigger an exception.
 */
public final class InvalidWriter
	extends Writer
{
	// singleton
	private static Writer INSTANCE = new InvalidWriter();

	/**
	 * Return the singleton.
	 */
	public static synchronized Writer instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private InvalidWriter() {
		super();
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void flush() throws IOException {
		// do nothing
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
