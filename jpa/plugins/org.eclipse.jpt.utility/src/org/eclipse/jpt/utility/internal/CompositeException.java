/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * Provide a way for multiple exceptions to be packaged and reported.
 */
public class CompositeException
	extends RuntimeException
{
	private final Throwable[] exceptions;
	private static final long serialVersionUID = 1L;

	/**
	 * The specified exceptions list must not be empty.
	 */
	public CompositeException(Throwable[] exceptions) {
		// grab the first exception and make it the "cause"
		super(exceptions[0]);
		this.exceptions = exceptions;
	}

	public Throwable[] getExceptions() {
		return this.exceptions;
	}

}
