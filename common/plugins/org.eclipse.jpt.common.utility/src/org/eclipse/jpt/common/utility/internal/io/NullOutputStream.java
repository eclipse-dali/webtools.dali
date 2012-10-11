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
import java.io.OutputStream;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This is an output stream that does nothing.
 * Everything is thrown into the "bit bucket".
 * Performance should be pretty good....
 */
public final class NullOutputStream
	extends OutputStream
{
	// singleton
	private static OutputStream INSTANCE = new NullOutputStream();

	/**
	 * Return the singleton.
	 */
	public static synchronized OutputStream instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private NullOutputStream() {
		super();
	}

	@Override
	public void write(int b) throws IOException {
		// do nothing
	}

	@Override
	public void write(byte[] b) throws IOException {
		// do nothing
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		// do nothing
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}
