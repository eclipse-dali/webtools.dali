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

import java.io.InputStream;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This input stream will throw an exception
 * any time it is read from. Marking or closing the stream
 * will <em>not</em> trigger an exception.
 */
public final class InvalidInputStream
	extends InputStream
{
	// singleton
	private static InputStream INSTANCE = new InvalidInputStream();

	/**
	 * Return the singleton.
	 */
	public static synchronized InputStream instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private InvalidInputStream() {
		super();
	}

	@Override
	public int read() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}
