/*******************************************************************************
 * Copyright (c) 2005, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.transformer;

/**
 * Simple interface for transforming an object into an <code>int</code>.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * 
 * @see org.eclipse.jpt.common.utility.transformer.Transformer
 */
@FunctionalInterface
public interface IntTransformer<I> {
	/**
	 * Return the transformed <code>int</code>.
	 * The semantics of "transform" is determined by the
	 * contract between the client and the server.
	 */
	int transform(I input);
}
