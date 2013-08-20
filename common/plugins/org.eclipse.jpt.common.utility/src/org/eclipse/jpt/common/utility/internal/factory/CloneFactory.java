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
 * A factory that will clone (via reflection) a configured prototype.
 * @param <T> the type of the object returned by the factory
 * @see Cloneable
 * @see Object#clone()
 */
public class CloneFactory<T extends Cloneable>
	implements Factory<T>
{
	private final T prototype;


	public CloneFactory(T prototype) {
		super();
		if (prototype == null) {
			throw new NullPointerException();
		}
		this.prototype = prototype;
	}

	/**
	 * Clone the prototype via reflection.
	 */
	@SuppressWarnings("unchecked")
	public T create() {
		return (T) ObjectTools.execute(this.prototype, "clone"); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.prototype);
	}
}
