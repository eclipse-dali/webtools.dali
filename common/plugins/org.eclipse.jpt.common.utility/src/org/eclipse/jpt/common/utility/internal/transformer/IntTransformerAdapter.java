/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.IntTransformer;

/**
 * Convenience <code>int</code> transformer that returns zero.
 * 
 * @param <I> input: the type of the object passed to the transformer
 */
public class IntTransformerAdapter<I>
	implements IntTransformer<I>
{
	public int transform(I input) {
		return 0;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
