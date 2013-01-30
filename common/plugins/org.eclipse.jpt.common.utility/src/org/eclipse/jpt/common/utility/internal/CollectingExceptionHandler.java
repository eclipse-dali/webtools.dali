/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.util.Vector;
import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * An exception handler that collects and holds the exceptions handed to it.
 * They can be retrieved at a later time via calls to {@link #getExceptions()}
 * and {{@link #clearExceptions()}.
 */
public class CollectingExceptionHandler
	implements ExceptionHandler
{
	private final Vector<Throwable> exceptions = new Vector<Throwable>();


	public CollectingExceptionHandler() {
		super();
	}

	public void handleException(Throwable t) {
		this.exceptions.add(t);
	}

	/**
	 * Return the current list of exceptions handled by the handler so far.
	 */
	public Iterable<Throwable> getExceptions() {
		return IterableTools.cloneSnapshot(this.exceptions);
	}

	/**
	 * Clear and return the current list of exceptions handled by the handler
	 * so far.
	 */
	public Iterable<Throwable> clearExceptions() {
		synchronized (this.exceptions) {
			Iterable<Throwable> result = IterableTools.cloneSnapshot(this.exceptions);
			this.exceptions.clear();
			return result;
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.exceptions);
	}
}
