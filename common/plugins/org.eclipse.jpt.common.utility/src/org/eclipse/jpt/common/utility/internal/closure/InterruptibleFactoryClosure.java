/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * @see FactoryClosure
 */
public class InterruptibleFactoryClosure<A>
	implements InterruptibleClosure<A>
{
	private final InterruptibleFactory<?> factory;


	public InterruptibleFactoryClosure(InterruptibleFactory<?> factory) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.factory = factory;
	}

	public void execute(A argument) throws InterruptedException {
		this.factory.create();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.factory);
	}
}
