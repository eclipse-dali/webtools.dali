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

import org.eclipse.jpt.common.utility.exception.ExceptionHandler;

/**
 * An exception handler that forwards exceptions to a collection of other
 * exception handlers.
 */
public class CompositeExceptionHandler
	extends AbstractCompositeExceptionHandler<ExceptionHandler>
{
	public CompositeExceptionHandler() {
		super();
	}

	public CompositeExceptionHandler(ExceptionHandler... exceptionHandlers) {
		super(exceptionHandlers);
	}

	public CompositeExceptionHandler(Iterable<? extends ExceptionHandler> exceptionHandlers) {
		super(exceptionHandlers);
	}
}
