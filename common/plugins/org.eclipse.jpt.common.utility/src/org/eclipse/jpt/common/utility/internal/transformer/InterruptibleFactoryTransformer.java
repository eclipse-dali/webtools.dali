/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;

/**
 * @see FactoryTransformer
 */
public class InterruptibleFactoryTransformer<I, O>
	implements InterruptibleTransformer<I, O>
{
	private final InterruptibleFactory<? extends O> factory;

	public InterruptibleFactoryTransformer(InterruptibleFactory<? extends O> factory) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.factory = factory;
	}

	public O transform(I input) throws InterruptedException {
		return this.factory.create();
	}

	public InterruptibleFactory<? extends O> getFactory() {
		return this.factory;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.factory);
	}
}
