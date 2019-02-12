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

import java.io.Reader;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This is a reader that does nothing.
 * It returns nothing.
 * Performance should be pretty good....
 */
public final class NullReader
	extends Reader
{
	// singleton
	private static Reader INSTANCE = new NullReader();

	/**
	 * Return the singleton.
	 */
	public static synchronized Reader instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private NullReader() {
		super();
	}
	
	@Override
	public void close() {
		// do nothing
	}
	
	@Override
	public int read() {
		return -1;
	}
	
	@Override
	public int read(char[] cbuf) {
		return -1;
	}
	
	@Override
	public int read(char[] cbuf, int off, int len) {
		return -1;
	}
	
	@Override
	public long skip(long n) {
		return 0;
	}
	
	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}
