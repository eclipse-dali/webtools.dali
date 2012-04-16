/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.IOException;
import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;

/**
 * Container for holding and printing the {@StackTraceElement stack trace
 * elements} produced by a thread or exception.
 * 
 * @see Thread#getStackTrace()
 * @see Throwable#getStackTrace()
 */
public class StackTrace
	implements Serializable
{
	private final Thread thread;
	private final StackTraceElement[] elements;
	private volatile String string;
	private static final long serialVersionUID = 1L;


	public StackTrace() {
		this(Thread.currentThread());
	}

	public StackTrace(Thread thread) {
		super();
		if (thread == null) {
			throw new NullPointerException();
		}
		this.thread = thread;
		this.elements = this.buildElements();
	}

	/**
	 * Strip off all the elements associated with this class and {@link Thread}.
	 */
	private StackTraceElement[] buildElements() {
		StackTraceElement[] result = this.thread.getStackTrace();
		int len = result.length;
		if (len == 0) {
			return result;
		}

		String className = this.getClass().getName();
		boolean found = false;
		int i = 0;
		while (i < len) {
			if (result[i].getClassName().equals(className)) {
				found = true;
			} else {
				if (found) {
					break;
				}
			}
			i++;
		}
		return found ? ArrayTools.subArray(result, i, len) : result;
	}

	public Thread getThread() {
		return this.thread;
	}

	public Iterable<StackTraceElement> getElements() {
		return new ArrayIterable<StackTraceElement>(this.elements);
	}

	/**
	 * Append the stack trace to the specified stream.
	 */
	public void appendTo(Appendable stream) throws IOException {
		this.appendTo(stream, null);
	}

	/**
	 * Append the stack trace to the specified stream, prefixing each line
	 * with the specified prefix.
	 */
	public void appendTo(Appendable stream, String prefix) throws IOException {
		for (StackTraceElement element : this.elements) {
			if (prefix != null) {
				stream.append(prefix);
			}
			stream.append(String.valueOf(element)).append(StringTools.CR);
		}
	}

	@Override
	public String toString() {
		return this.getString();
	}

	private String getString() {
		if (this.string == null) {
			synchronized (this) {
				if (this.string == null) {
					this.string = this.buildString();
				}
			}
		}
		return this.string;
	}

	private String buildString() {
		StringBuffer sb = new StringBuffer(1000);
		try {
			this.appendTo(sb);
		} catch (IOException ex) {
			throw new RuntimeException(ex);  // should not happen with a StringBuffer
		}
		return sb.toString();
	}
}
