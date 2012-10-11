/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
