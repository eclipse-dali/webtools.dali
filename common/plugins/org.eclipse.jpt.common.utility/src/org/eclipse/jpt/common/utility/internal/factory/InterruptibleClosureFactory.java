/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * @see ClosureFactory
 */
public class InterruptibleClosureFactory<T>
	implements InterruptibleFactory<T>
{
	private final InterruptibleClosure<?> closure;

	public InterruptibleClosureFactory(InterruptibleClosure<?> closure) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
	}

	public T create() throws InterruptedException {
		this.closure.execute(null);
		return null;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closure);
	}
}
