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

import java.io.OutputStream;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This output stream will throw an exception
 * any time it is written to. Flushing or closing the stream
 * will <em>not</em> trigger an exception.
 */
public final class InvalidOutputStream
	extends OutputStream
{
	// singleton
	private static OutputStream INSTANCE = new InvalidOutputStream();

	/**
	 * Return the singleton.
	 */
	public static synchronized OutputStream instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private InvalidOutputStream() {
		super();
	}

	@Override
	public void write(int b) {
		// we don't throw an IOException because that is swallowed by PrintStream
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}
