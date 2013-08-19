/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.exception;

import java.util.Vector;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * An exception handler that forwards exceptions to a collection of other
 * exception handlers.
 */
public abstract class AbstractCompositeExceptionHandler<H extends ExceptionHandler>
	implements ExceptionHandler
{
	private final Vector<H> exceptionHandlers = new Vector<H>();


	protected AbstractCompositeExceptionHandler() {
		super();
	}

	protected AbstractCompositeExceptionHandler(H... exceptionHandlers) {
		super();
		CollectionTools.addAll(this.exceptionHandlers, exceptionHandlers);
		this.checkExceptionHandlers();
	}

	protected AbstractCompositeExceptionHandler(Iterable<? extends H> exceptionHandlers) {
		super();
		CollectionTools.addAll(this.exceptionHandlers, exceptionHandlers);
		this.checkExceptionHandlers();
	}

	private void checkExceptionHandlers() {
		for (H exceptionHandler : this.exceptionHandlers) {
			if (exceptionHandler == null) {
				throw new NullPointerException();
			}
		}
	}


	/**
	 * Forward the exception to the current list of exception handlers.
	 */
	public void handleException(Throwable t) {
		for (H handler : this.getExceptionHandlers()) {
			handler.handleException(t);
		}
	}

	/**
	 * Return the current list of exception handlers.
	 */
	public Iterable<H> getExceptionHandlers() {
		return IterableTools.cloneSnapshot(this.exceptionHandlers);
	}

	/**
	 * Add the specified exception handler to the list of exception handlers.
	 */
	public void addExceptionHandler(H exceptionHandler) {
		if (exceptionHandler == null) {
			throw new NullPointerException(); // better sooner than later
		}
		synchronized (this.exceptionHandlers) {
			if (this.exceptionHandlers.contains(exceptionHandler)) {
				throw new IllegalArgumentException("duplicate handler: " + exceptionHandler); //$NON-NLS-1$
			}
			this.exceptionHandlers.add(exceptionHandler);
		}
	}

	/**
	 * Remove the specified exception handler from the list of exception
	 * handlers.
	 */
	public void removeExceptionHandler(H exceptionHandler) {
		if ( ! this.exceptionHandlers.remove(exceptionHandler)) {
			throw new IllegalArgumentException("handler not registered: " + exceptionHandler); //$NON-NLS-1$
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.exceptionHandlers);
	}
}
