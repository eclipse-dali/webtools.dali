/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Tranformer wrapper that can have its wrapped transformer changed,
 * allowing a client to change a previously-supplied transformer's
 * behavior mid-stream.
 * 
 * @param <T1> the type of the object passed to the transformer
 * @param <T2> the type of the object returned by the transformer
 * @see #setTransformer(Transformer)
 */
public class TransformerWrapper<T1, T2>
	implements Transformer<T1, T2>
{
	protected volatile Transformer<T1, T2> transformer;

	public TransformerWrapper(Transformer<T1, T2> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	public T2 transform(T1 o) {
		return this.transformer.transform(o);
	}

	public void setTransformer(Transformer<T1, T2> transformer) {
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}
