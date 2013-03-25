/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
 * Transform any object, except <code>null</code>, into a single
 * client-specified object. Any <code>null</code> object will be
 * transformed into <code>null</code>.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object returned by the transformer
 * @see NullOutputTransformer
 */
public class StaticOutputTransformer<I, O>
	implements Transformer<I, O>
{
	private final O output;


	public StaticOutputTransformer(O output) {
		super();
		this.output = output;
	}

	public O transform(I input) {
		return this.output;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.output);
	}
}
