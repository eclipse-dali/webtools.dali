/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
 * @see TransformerWrapper
 */
public class InterruptibleTransformerWrapper<I, O>
	implements InterruptibleTransformer<I, O>
{
	protected volatile InterruptibleTransformer<? super I, ? extends O> transformer;

	public InterruptibleTransformerWrapper(InterruptibleTransformer<? super I, ? extends O> transformer) {
		super();
		this.setInterruptibleTransformer(transformer);
	}

	public O transform(I input) throws InterruptedException {
		return this.transformer.transform(input);
	}

	public void setInterruptibleTransformer(InterruptibleTransformer<? super I, ? extends O> transformer) {
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
