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

import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * @see CastingFactoryWrapper
 * @see DowncastingInterruptibleFactoryWrapper
 * @see UpcastingInterruptibleFactoryWrapper
 */
public class CastingInterruptibleFactoryWrapper<X, T>
	implements InterruptibleFactory<T>
{
	private final InterruptibleFactory<X> factory;


	public CastingInterruptibleFactoryWrapper(InterruptibleFactory<X> factory) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.factory = factory;
	}

	/**
	 * Cast the result and hope for the best.
	 */
	@SuppressWarnings("unchecked")
	public T create() throws InterruptedException {
		return (T) this.factory.create();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.factory);
	}
}
