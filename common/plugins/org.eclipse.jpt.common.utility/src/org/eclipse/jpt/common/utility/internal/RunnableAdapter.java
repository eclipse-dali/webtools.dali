/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

/**
 * Convenience runnable that does nothing.
 */
public class RunnableAdapter
	implements Runnable
{
	public void run() {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
