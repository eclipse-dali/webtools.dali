/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.exception;

/**
 * Simple interface for allowing clients to pass an exception handler to a
 * service (e.g. to log the exception). This is particularly helpful if the
 * service executes on another, possibly inaccessible, thread.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MultiThreadExceptionHandler
	extends ExceptionHandler
{
	/**
	 * The specified exception was thrown while the specified thread was
	 * executing. Handle it appropriately.
	 */
	void handleException(Thread thread, Throwable t);
}
