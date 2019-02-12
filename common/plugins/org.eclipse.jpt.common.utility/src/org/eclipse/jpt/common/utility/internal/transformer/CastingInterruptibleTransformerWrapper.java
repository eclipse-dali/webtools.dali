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
 * @see CastingTransformerWrapper
 */
public class CastingInterruptibleTransformerWrapper<I, X, O>
	implements InterruptibleTransformer<I, O>
{
	private final InterruptibleTransformer<? super I, ? extends X> transformer;


	public CastingInterruptibleTransformerWrapper(InterruptibleTransformer<? super I, ? extends X> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	/**
	 * Cast the output and hope for the best.
	 */
	@SuppressWarnings("unchecked")
	public O transform(I input) throws InterruptedException {
		return (O) this.transformer.transform(input);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}
