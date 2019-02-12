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
import java.io.OutputStream;
import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A composite output stream forwards the data written to it
 * to multiple streams.
 */
public class CompositeOutputStream
	extends OutputStream
{
	private OutputStream[] streams;


	/**
	 * Construct a "tee" output stream that writes to the
	 * specified streams.
	 */
	public CompositeOutputStream(OutputStream... streams) {
		super();
		if (streams == null) {
			throw new NullPointerException();
		}
		this.streams = streams;
	}

	@Override
	public synchronized void write(int i) throws IOException {
		for (OutputStream stream : this.streams) {
			stream.write(i);
		}
	}

	@Override
	public synchronized void write(byte[] bytes) throws IOException {
		for (OutputStream stream : this.streams) {
			stream.write(bytes);
		}
	}

	@Override
	public synchronized void write(byte[] bytes, int off, int len) throws IOException {
		for (OutputStream stream : this.streams) {
			stream.write(bytes, off, len);
		}
	}

	@Override
	public synchronized void flush() throws IOException {
		for (OutputStream stream : this.streams) {
			stream.flush();
		}
	}

	@Override
	public synchronized void close() throws IOException {
		for (OutputStream stream : this.streams) {
			stream.close();
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, Arrays.toString(this.streams));
	}
}
