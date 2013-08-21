/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Closure wrapper that will handle any exceptions (other than
 * {@link InterruptedException}) thrown by the wrapped
 * closure with an {@link ExceptionHandler exception handler}. If the
 * wrapped closure throws an exception, the safe closure will handle
 * the exception and return.
 * 
 * @param <A> the type of the object passed to the closure
 */
public class SafeInterruptibleClosureWrapper<A>
	implements InterruptibleClosure<A>
{
	private final InterruptibleClosure<? super A> closure;
	private final ExceptionHandler exceptionHandler;


	public SafeInterruptibleClosureWrapper(InterruptibleClosure<? super A> closure, ExceptionHandler exceptionHandler) {
		super();
		if ((closure == null) || (exceptionHandler == null)) {
			throw new NullPointerException();
		}
		this.closure = closure;
		this.exceptionHandler = exceptionHandler;
	}

	public void execute(A argument) throws InterruptedException {
		try {
			this.closure.execute(argument);
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
