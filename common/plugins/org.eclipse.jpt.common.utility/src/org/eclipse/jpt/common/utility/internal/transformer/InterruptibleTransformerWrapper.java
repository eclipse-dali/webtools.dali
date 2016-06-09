/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
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
 * @see TransformerWrapper
 */
public class InterruptibleTransformerWrapper<I, O>
	implements InterruptibleTransformer<I, O>
{
	protected volatile InterruptibleTransformer<? super I, ? extends O> transformer;

	public InterruptibleTransformerWrapper(InterruptibleTransformer<? super I, ? extends O> transformer) {
		super();
		this.setTransformer(transformer);
	}

	public O transform(I input) throws InterruptedException {
		return this.transformer.transform(input);
	}

	public InterruptibleTransformer<? super I, ? extends O> getTransformer() {
		return this.transformer;
	}

	public void setTransformer(InterruptibleTransformer<? super I, ? extends O> transformer) {
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
