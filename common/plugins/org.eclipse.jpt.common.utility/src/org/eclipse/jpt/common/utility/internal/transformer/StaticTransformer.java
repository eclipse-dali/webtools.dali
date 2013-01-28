/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

/**
 * Transform any object, except <code>null</code>, into a single
 * client-specified object. Any <code>null</code> object will be
 * transformed into <code>null</code>.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object returned by the transformer
 */
public class StaticTransformer<I, O>
	extends AbstractTransformer<I, O>
{
	private final O object;

	public StaticTransformer() {
		this(null);
	}

	public StaticTransformer(O object) {
		super();
		this.object = object;
	}

	@Override
	protected O transform_(I o) {
		return this.object;
	}
}
