/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.exception;

/**
 * This exception can be used to interrupt a process
 * (such as in a UI dialog process).
 */
public class CancelException
	extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public CancelException() {
		super();
	}
}
