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
import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A composite writer forwards the character data written to it
 * to multiple streams.
 */
public class CompositeWriter
	extends Writer
{
	private Writer[] writers;


	/**
	 * Construct a "tee" writer that writes to the specified writers.
	 */
	public CompositeWriter(Writer... writers) {
		super();
		this.writers = writers;
	}

	/**
	 * Construct a "tee" writer that writes to the specified writers and
	 * locks on the specified object.
	 */
	public CompositeWriter(Object lock, Writer... writers) {
		super(lock);
		this.writers = writers;
	}

	@Override
	public void write(int c) throws IOException {
		for (Writer writer : this.writers) {
			writer.write(c);
		}
	}

	@Override
	public void write(char[] cbuf) throws IOException {
		for (Writer writer : this.writers) {
			writer.write(cbuf);
		}
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		for (Writer writer : this.writers) {
			writer.write(cbuf, off, len);
		}
	}

	@Override
	public void write(String str) throws IOException {
		for (Writer writer : this.writers) {
			writer.write(str);
		}
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
		for (Writer writer : this.writers) {
			writer.write(str, off, len);
		}
	}

	@Override
	public void flush() throws IOException {
		for (Writer writer : this.writers) {
			writer.flush();
		}
	}

	@Override
	public void close() throws IOException {
		for (Writer writer : this.writers) {
			writer.close();
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, Arrays.toString(this.writers));
	}
}
