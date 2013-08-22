/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;

/**
 * @see UpcastingTransformerWrapper
 */
public class UpcastingInterruptibleTransformerWrapper<I, O, X extends O>
	implements InterruptibleTransformer<I, O>
{
	private final InterruptibleTransformer<? super I, ? extends X> transformer;

	public UpcastingInterruptibleTransformerWrapper(InterruptibleTransformer<? super I, ? extends X> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	/**
	 * No need for casting as the type is guaranteed by the generic type
	 * argument.
	 */
	public O transform(I input) throws InterruptedException {
		return this.transformer.transform(input);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}
