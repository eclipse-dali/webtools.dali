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
 * Transform any object into a single client-specified
 * non-<code>null</code> object.
 * 
 * @param <T1> the type of the object passed to the transformer
 * @param <T2> the type of the object returned by the transformer
 */
public class NonNullStaticTransformer<T1, T2>
	implements Transformer<T1, T2>
{
	private final T2 object;

	public NonNullStaticTransformer(T2 object) {
		super();
		if (object == null) {
			throw new NullPointerException();
		}
		this.object = object;
	}

	public T2 transform(T1 o) {
		return this.object;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.object);
	}
}
