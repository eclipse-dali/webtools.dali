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

import java.io.Writer;

/**
 * Silly Sun. Why {@link java.io.StringWriter} does not allow us to pass in a
 * {@link StringBuffer} is beyond me....
 * 
 * @see java.io.StringWriter
 */
public class StringBufferWriter
	extends Writer
{
	private StringBuffer sb;

	/**
	 * Construct a writer on the specified string buffer.
	 */
	public StringBufferWriter(StringBuffer sb) {
		super();
		if (sb == null) {
			throw new NullPointerException();
		}
		this.sb = sb;
		this.lock = sb;
	}

	/**
	 * Construct a writer on a string buffer with the specified initial size.
	 */
	public StringBufferWriter(int initialSize) {
		this(new StringBuffer(initialSize));
	}

	/**
	 * Construct a writer on a string buffer.
	 */
	public StringBufferWriter() {
		this(new StringBuffer());
	}

	@Override
	public void write(int c) {
		this.sb.append((char) c);
	}

	@Override
	public void write(char cbuf[], int off, int len) {
		if (len != 0) {
			this.sb.append(cbuf, off, len);
		}
	}

	@Override
	public void write(String str) {
		this.sb.append(str);
	}

	@Override
	public void write(String str, int off, int len)  {
		this.sb.append(str, off, off + len);
	}

	public StringBuffer getBuffer() {
		return this.sb;
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
	public String toString() {
		return this.sb.toString();
	}
}
