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

import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;

/**
 * @see SafeTransformerWrapper
 */
public class SafeInterruptibleTransformerWrapper<I, O>
	implements InterruptibleTransformer<I, O>
{
	private final InterruptibleTransformer<? super I, ? extends O> transformer;
	private final ExceptionHandler exceptionHandler;
	private final O exceptionOutput;


	public SafeInterruptibleTransformerWrapper(InterruptibleTransformer<? super I, ? extends O> transformer, ExceptionHandler exceptionHandler, O exceptionOutput) {
		super();
		if ((transformer == null) || (exceptionHandler == null)) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
		this.exceptionHandler = exceptionHandler;
		this.exceptionOutput = exceptionOutput;
	}

	public O transform(I input) throws InterruptedException {
		try {
			return this.transformer.transform(input);
		} catch (InterruptedException ex) {
			throw ex;
		} catch (Throwable ex) {
			this.exceptionHandler.handleException(ex);
			return this.exceptionOutput;
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}
