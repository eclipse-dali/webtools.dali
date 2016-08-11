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

import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Adapt a {@link Transformer} to the {@link Factory} interface.
 * The transformer is passed <code>null</code> for input and its output is
 * returned by the factory. This really only useful for a transformer that
 * accepts <code>null</code> input.
 * 
 * @param <T> the type of the object returned by the factory
 * 
 * @see org.eclipse.jpt.common.utility.internal.transformer.TransformerTools#adapt(Factory)
 */
public class TransformerFactory<T>
	implements Factory<T>
{
	private final Transformer<?, ? extends T> transformer;


	public TransformerFactory(Transformer<?, ? extends T> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	public T create() {
		return this.transformer.transform(null);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}
