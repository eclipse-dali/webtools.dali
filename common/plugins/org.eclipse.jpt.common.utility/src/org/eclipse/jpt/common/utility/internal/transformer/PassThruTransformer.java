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

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A transformer that will perform no transformation at all;
 * it will simply return the input if it is not <code>null</code>.
 * A <code>null</code> input is transformed into a client-configured
 * output.
 * 
 * @param <I> input: the type of the object passed to the transformer
 */
public class PassThruTransformer<I>
	implements Transformer<I, I>
{
	private final I nullOutput;


	public PassThruTransformer(I nullOutput) {
		super();
		this.nullOutput = nullOutput;
	}

	public I transform(I input) {
		return (input == null) ? this.nullOutput : input;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.nullOutput);
	}
}
