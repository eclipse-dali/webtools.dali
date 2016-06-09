/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;

/**
 * @see ClosureTransformer
 */
public class InterruptibleClosureTransformer<I, O>
	implements InterruptibleTransformer<I, O>
{
	private final InterruptibleClosure<? super I> closure;

	public InterruptibleClosureTransformer(InterruptibleClosure<? super I> closure) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
	}

	public O transform(I input) throws InterruptedException {
		this.closure.execute(input);
		return null;
	}

	public InterruptibleClosure<? super I> getClosure() {
		return this.closure;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closure);
	}
}
