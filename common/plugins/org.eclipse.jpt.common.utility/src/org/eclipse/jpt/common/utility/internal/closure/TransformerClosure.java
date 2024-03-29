/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Adapt a {@link Transformer} to the {@link Closure} interface.
 * The closure's argument is forwarded to the the transformer and the
 * transformer's output is ignored. This really only useful for a
 * transformer that has side-effects.
 * 
 * @param <A> the type of the object passed to the closure and forwarded to the
 *     transformer
 * 
 * @see org.eclipse.jpt.common.utility.internal.transformer.ClosureTransformer
 */
public class TransformerClosure<A>
	implements Closure<A>
{
	private final Transformer<? super A, ?> transformer;

	public TransformerClosure(Transformer<? super A, ?> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	public void execute(A argument) {
		this.transformer.transform(argument);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}
