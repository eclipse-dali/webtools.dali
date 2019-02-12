/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;
import java.util.concurrent.ThreadFactory;

/**
 * A <code>SimpleThreadFactory</code> is a straightforward implementation of
 * the JDK {@link ThreadFactory}.
 */
public class SimpleThreadFactory
	implements ThreadFactory, Serializable
{
	// singleton
	private static final SimpleThreadFactory INSTANCE = new SimpleThreadFactory();

	/**
	 * Return the singleton.
	 */
	public static ThreadFactory instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private SimpleThreadFactory() {
		super();
	}

	public Thread newThread(Runnable r) {
		return new Thread(r);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
