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
import java.io.InputStream;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This is an input stream that does nothing.
 * It returns nothing.
 * Performance should be pretty good....
 */
public final class NullInputStream
	extends InputStream
{
	// singleton
	private static InputStream INSTANCE = new NullInputStream();

	/**
	 * Return the singleton.
	 */
	public static synchronized InputStream instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private NullInputStream() {
		super();
	}

	@Override
	public int read() throws IOException {
		return -1;
	}

	@Override
	public int read(byte[] b) throws IOException {
		return -1;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return -1;
	}

	@Override
	public long skip(long n) throws IOException {
		return 0;
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}
