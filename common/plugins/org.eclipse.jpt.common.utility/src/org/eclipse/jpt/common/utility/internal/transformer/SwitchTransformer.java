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

import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Transformer that loops over a configured set of predicate/transformer pairs,
 * passing its input to each predicate to determine
 * which of the transformers to execute. Only the first transformer whose predicate
 * evaluates to <code>true</code> is executed, even if other, following,
 * predicates would evaluate to <code>true</code>.
 * If none of the predicates evaluates to <code>true</code>, the default transformer
 * is executed.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object returned by the transformer
 */
public class SwitchTransformer<I, O>
	implements Transformer<I, O>
{
	private final Iterable<Association<Predicate<? super I>, Transformer<? super I, ? extends O>>> transformers;
	private final Transformer<? super I, ? extends O> defaultTransformer;

	public SwitchTransformer(Iterable<Association<Predicate<? super I>, Transformer<? super I, ? extends O>>> transformers, Transformer<? super I, ? extends O> defaultTransformer) {
		super();
		if (IterableTools.isOrContainsNull(transformers) || (defaultTransformer == null)) {
			throw new NullPointerException();
		}
		this.transformers = transformers;
		this.defaultTransformer = defaultTransformer;
	}

	public O transform(I input) {
		for (Association<Predicate<? super I>, Transformer<? super I, ? extends O>> association : this.transformers) {
			if (association.getKey().evaluate(input)) {
				return association.getValue().transform(input); // execute only one transformer
			}
		}
		return this.defaultTransformer.transform(input);
	}

	public Iterable<Association<Predicate<? super I>, Transformer<? super I, ? extends O>>> getTransformers() {
		return this.transformers;
	}

	public Transformer<? super I, ? extends O> getDefaultTransformer() {
		return this.defaultTransformer;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformers);
	}
}
