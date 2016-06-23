/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
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
 * Tranformer that checks for <code>null</code> input.
 * If the input is <code>null</code>,
 * the transformer will return the configured output value;
 * otherwise, it will simply return the input.
 * 
 * @param <I> input: the type of the object passed to and
 * returned by the transformer
 */
public class NullCheckTransformer<I>
	implements Transformer<I, I>
{
	private final I nullOutput;


	public NullCheckTransformer(I nullOutput) {
		super();
		this.nullOutput = nullOutput;
	}

	public I transform(I input) {
		return (input != null) ? input : this.nullOutput;
	}

	public I getNullOutput() {
		return this.nullOutput;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.nullOutput);
	}
}
