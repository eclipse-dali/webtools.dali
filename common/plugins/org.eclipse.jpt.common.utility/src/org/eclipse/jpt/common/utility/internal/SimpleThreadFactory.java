/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.util.concurrent.ThreadFactory;

/**
 * A <code>SimpleThreadFactory</code> is a straightforward implementation of
 * the JDK {@link ThreadFactory}.
 */
public class SimpleThreadFactory
	implements ThreadFactory
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
		return StringTools.buildToStringFor(this);
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}

}
