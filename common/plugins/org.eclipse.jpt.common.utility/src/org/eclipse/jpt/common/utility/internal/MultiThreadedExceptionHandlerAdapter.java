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

import org.eclipse.jpt.common.utility.MultiThreadExceptionHandler;

/**
 * Convenience exception handler that does nothing.
 */
public class MultiThreadedExceptionHandlerAdapter
	extends ExceptionHandlerAdapter
	implements MultiThreadExceptionHandler
{
	public void handleException(Thread thread, Throwable t) {
		// NOP
	}
}
