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

import java.util.Map;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A transformer that will map the input to an output via a client-configured
 * map.
 * 
 * @param <I> input: the type of the object passed to the transformer
 *   (i.e. the type of the map's keys)
 * @param <O> output: the type of the object returned by the transformer
 *   (i.e. the type of the map's values)
 */
public class MapTransformer<I, O>
	implements Transformer<I, O>
{
	private final Map<? super I, ? extends O> map;


	public MapTransformer(Map<? super I, ? extends O> map) {
		super();
		if (map == null) {
			throw new NullPointerException();
		}
		this.map = map;
	}

	public O transform(I input) {
		return this.map.get(input);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.map);
	}
}
