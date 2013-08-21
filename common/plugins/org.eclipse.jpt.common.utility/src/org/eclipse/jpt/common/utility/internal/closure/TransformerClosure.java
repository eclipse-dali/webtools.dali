/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
 * The transformer's output is ignored. This really only useful for a
 * transformer that has side-effects.
 * 
 * @param <A> the type of the object passed to the closure and forwarded to the
 *     transformer
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
