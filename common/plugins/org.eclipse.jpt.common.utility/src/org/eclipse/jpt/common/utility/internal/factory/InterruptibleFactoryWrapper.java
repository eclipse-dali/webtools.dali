/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
 * @see FactoryWrapper
 * @see #setFactory(InterruptibleFactory)
 */
public class InterruptibleFactoryWrapper<T>
	implements InterruptibleFactory<T>
{
	protected volatile InterruptibleFactory<? extends T> factory;

	public InterruptibleFactoryWrapper(InterruptibleFactory<? extends T> factory) {
		super();
		this.setFactory(factory);
	}

	public T create() throws InterruptedException {
		return this.factory.create();
	}

	public void setFactory(InterruptibleFactory<? extends T> factory) {
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
