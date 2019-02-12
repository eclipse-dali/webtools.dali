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

import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;

/**
 * @see TransformerClosure
 */
public class InterruptibleTransformerClosure<A>
	implements InterruptibleClosure<A>
{
	private final InterruptibleTransformer<? super A, ?> transformer;

	public InterruptibleTransformerClosure(InterruptibleTransformer<? super A, ?> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	public void execute(A argument) throws InterruptedException {
		this.transformer.transform(argument);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}
