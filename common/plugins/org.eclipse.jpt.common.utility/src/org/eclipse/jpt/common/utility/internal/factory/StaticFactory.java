/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Transform any object, except <code>null</code>, into a single
 * client-specified object. Any <code>null</code> object will be
 * transformed into <code>null</code>.
 * 
 * @param <T> the type of the object returned by the factory
 * @see NullFactory
 */
public class StaticFactory<T>
	implements Factory<T>
{
	private final T value;


	public StaticFactory(T value) {
		super();
		this.value = value;
	}

	public T create() {
		return this.value;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.value);
	}
}
