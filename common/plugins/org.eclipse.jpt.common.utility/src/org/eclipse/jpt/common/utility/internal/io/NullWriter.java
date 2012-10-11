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

import java.io.Writer;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This is a writer that does nothing.
 * Everything is thrown into the "bit bucket".
 * Performance should be pretty good....
 */
public final class NullWriter
	extends Writer
{
	// singleton
	private static Writer INSTANCE = new NullWriter();

	/**
	 * Return the singleton.
	 */
	public static synchronized Writer instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private NullWriter() {
		super();
	}
	
	@Override
	public void write(char[] cbuf, int off, int len) {
		// do nothing
	}
	
	@Override
	public void flush() {
		// do nothing
	}
	
	@Override
	public void close() {
		// do nothing
	}
	
	@Override
	public void write(char[] cbuf) {
		// do nothing
	}
	
	@Override
	public void write(int c) {
		// do nothing
	}
	
	@Override
	public void write(String str, int off, int len) {
		// do nothing
	}
	
	@Override
	public void write(String str) {
		// do nothing
	}
	
	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}
