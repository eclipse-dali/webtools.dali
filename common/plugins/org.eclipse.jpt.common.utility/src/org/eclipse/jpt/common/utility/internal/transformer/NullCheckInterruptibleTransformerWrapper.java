/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;

/**
 * @see NullCheckTransformerWrapper
 */
public class NullCheckInterruptibleTransformerWrapper<I, O>
	implements InterruptibleTransformer<I, O>
{
	private final InterruptibleTransformer<? super I, ? extends O> transformer;
	private final O nullOutput;


	public NullCheckInterruptibleTransformerWrapper(InterruptibleTransformer<? super I, ? extends O> transformer, O nullOutput) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
		this.nullOutput = nullOutput;
	}

	public O transform(I input) throws InterruptedException {
		return (input == null) ? this.nullOutput : this.transformer.transform(input);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}
