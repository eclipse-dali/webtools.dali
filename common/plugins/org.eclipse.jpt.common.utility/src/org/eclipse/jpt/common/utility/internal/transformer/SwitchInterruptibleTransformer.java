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

import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * @see SwitchTransformer
 */
public class SwitchInterruptibleTransformer<I, O>
	implements InterruptibleTransformer<I, O>
{
	private final Iterable<Association<Predicate<? super I>, InterruptibleTransformer<? super I, ? extends O>>> transformers;
	private final InterruptibleTransformer<? super I, ? extends O> defaultInterruptibleTransformer;

	public SwitchInterruptibleTransformer(Iterable<Association<Predicate<? super I>, InterruptibleTransformer<? super I, ? extends O>>> transformers, InterruptibleTransformer<? super I, ? extends O> defaultInterruptibleTransformer) {
		super();
		if (IterableTools.isOrContainsNull(transformers) || (defaultInterruptibleTransformer == null)) {
			throw new NullPointerException();
		}
		this.transformers = transformers;
		this.defaultInterruptibleTransformer = defaultInterruptibleTransformer;
	}

	public O transform(I input) throws InterruptedException {
		for (Association<Predicate<? super I>, InterruptibleTransformer<? super I, ? extends O>> association : this.transformers) {
			if (association.getKey().evaluate(input)) {
				return association.getValue().transform(input); // execute only one transformer
			}
		}
		return this.defaultInterruptibleTransformer.transform(input);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformers);
	}
}
