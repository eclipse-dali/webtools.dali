/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.InterruptibleBiClosure;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Closure wrapper that will handle any exceptions thrown by the wrapped
 * closure with an {@link ExceptionHandler exception handler}. If the
 * wrapped closure throws an exception, the safe closure will handle
 * the exception and return.
 * 
 * @param <A1> the type of the first object passed to the closure
 * @param <A2> the type of the second object passed to the closure
 */
public class SafeInterruptibleBiClosureWrapper<A1, A2>
	implements InterruptibleBiClosure<A1, A2>
{
	private final InterruptibleBiClosure<? super A1, ? super A2> closure;
	private final ExceptionHandler exceptionHandler;


	public SafeInterruptibleBiClosureWrapper(InterruptibleBiClosure<? super A1, ? super A2> closure, ExceptionHandler exceptionHandler) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
		if (exceptionHandler == null) {
			throw new NullPointerException();
		}
		this.exceptionHandler = exceptionHandler;
	}

	public void execute(A1 argument1, A2 argument2) throws InterruptedException {
		try {
			this.closure.execute(argument1, argument2);
		} catch (InterruptedException ex) {
			throw ex;
		} catch (Throwable ex) {
			this.exceptionHandler.handleException(ex);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closure);
	}
}
