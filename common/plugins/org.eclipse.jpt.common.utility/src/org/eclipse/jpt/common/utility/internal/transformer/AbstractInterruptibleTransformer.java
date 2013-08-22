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
 * @see AbstractTransformer
 */
public abstract class AbstractInterruptibleTransformer<I, O>
	extends InterruptibleTransformerAdapter<I, O>
{
	@Override
	public O transform(I input) throws InterruptedException {
		return (input == null) ? null : this.transform_(input);
	}

	/**
	 * Transform the specified input; its value is guaranteed to be not
	 * <code>null</code>.
	 */
	protected abstract O transform_(I input) throws InterruptedException;
}
