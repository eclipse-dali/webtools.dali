/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
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
 * Convenience factory that always returns <code>null</code>.
 * 
 * @param <T> the type of the object returned by the factory
 * 
 * @see NullFactory
 */
public class FactoryAdapter<T>
	implements Factory<T>
{
	public T create() {
		return null;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
