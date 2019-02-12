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

import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;

/**
 * @see TransformerFactory
 */
public class InterruptibleTransformerFactory<T>
	implements InterruptibleFactory<T>
{
	private final InterruptibleTransformer<?, ? extends T> transformer;

	public InterruptibleTransformerFactory(InterruptibleTransformer<?, ? extends T> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	public T create() throws InterruptedException {
		return this.transformer.transform(null);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}
