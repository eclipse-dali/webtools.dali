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

import java.util.Iterator;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Factory that returns the elments returned by a configured {@link Iterator}.
 * 
 * @param <T> the type of the object returned by the factory
 */
public class IteratorFactory<T>
	implements Factory<T>
{
	private final Iterator<? extends T> iterator;

	public IteratorFactory(Iterator<? extends T> iterator) {
		super();
		if (iterator == null) {
			throw new NullPointerException();
		}
		this.iterator = iterator;
	}

	public T create() {
		return this.iterator.next();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.iterator);
	}
}
