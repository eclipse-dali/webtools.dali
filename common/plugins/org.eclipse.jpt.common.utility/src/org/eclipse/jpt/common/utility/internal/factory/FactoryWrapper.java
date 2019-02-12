/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Factory wrapper that can have its wrapped factory changed,
 * allowing a client to change a previously-supplied factory's
 * behavior mid-stream.
 * 
 * @param <T> the type of the object returned by the factory
 * @see #setFactory(Factory)
 */
public class FactoryWrapper<T>
	implements Factory<T>
{
	protected volatile Factory<? extends T> factory;

	public FactoryWrapper(Factory<? extends T> factory) {
		super();
		this.setFactory(factory);
	}

	public T create() {
		return this.factory.create();
	}

	public void setFactory(Factory<? extends T> factory) {
		if (factory == null) {
			throw new NullPointerException();
		}
		this.factory = factory;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.factory);
	}
}
