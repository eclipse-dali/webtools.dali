/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.InterruptibleBiClosure;
import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Adapt an {@link InterruptibleFactory} to the {@link InterruptibleBiClosure} interface.
 * The closure's arguments and the factory's output are ignored.
 * This really only useful for a factory that has side-effects.
 * 
 * @param <A1> the type of the first object passed to the closure; ignored
 * @param <A2> the type of the second object passed to the closure; ignored
 */
public class InterruptibleFactoryBiClosure<A1, A2>
	implements InterruptibleBiClosure<A1, A2>
{
	private final InterruptibleFactory<?> factory;


	public InterruptibleFactoryBiClosure(InterruptibleFactory<?> factory) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.factory = factory;
	}

	public void execute(A1 argument1, A2 argument2) throws InterruptedException {
		this.factory.create();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.factory);
	}
}
