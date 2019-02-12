/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;

/**
 * @see SafeFactoryWrapper
 */
public class SafeInterruptibleFactoryWrapper<T>
	implements InterruptibleFactory<T>
{
	private final InterruptibleFactory<? extends T> factory;
	private final ExceptionHandler exceptionHandler;
	private final T exceptionValue;


	public SafeInterruptibleFactoryWrapper(InterruptibleFactory<? extends T> factory, ExceptionHandler exceptionHandler, T exceptionOutput) {
		super();
		if ((factory == null) || (exceptionHandler == null)) {
			throw new NullPointerException();
		}
		this.factory = factory;
		this.exceptionHandler = exceptionHandler;
		this.exceptionValue = exceptionOutput;
	}

	public T create() throws InterruptedException {
		try {
			return this.factory.create();
		} catch (InterruptedException ex) {
			throw ex;
		} catch (Throwable ex) {
			this.exceptionHandler.handleException(ex);
			return this.exceptionValue;
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.factory);
	}
}
