/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;

/**
 * @see TransformerAdapter
 */
public class InterruptibleTransformerAdapter<I, O>
	implements InterruptibleTransformer<I, O>
{
	public O transform(I input) throws InterruptedException {
		return null;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
