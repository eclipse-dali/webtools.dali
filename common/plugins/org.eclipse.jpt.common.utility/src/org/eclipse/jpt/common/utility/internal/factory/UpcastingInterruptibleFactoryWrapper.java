/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;

/**
 * @see UpcastingFactoryWrapper
 * @see CastingInterruptibleFactoryWrapper
 * @see DowncastingInterruptibleFactoryWrapper
 */
public class UpcastingInterruptibleFactoryWrapper<T, X extends T>
	implements InterruptibleFactory<T>
{
	private final InterruptibleFactory<X> factory;


	public UpcastingInterruptibleFactoryWrapper(InterruptibleFactory<X> factory) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.factory = factory;
	}

	/**
	 * No need for casting as the type is guaranteed by the generic type
	 * argument.
	 */
	public T create() throws InterruptedException {
		return this.factory.create();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.factory);
	}
}
