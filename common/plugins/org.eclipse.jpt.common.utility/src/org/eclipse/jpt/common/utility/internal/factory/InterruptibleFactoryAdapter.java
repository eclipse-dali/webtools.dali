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

import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * @see FactoryAdapter
 */
public class InterruptibleFactoryAdapter<T>
	implements InterruptibleFactory<T>
{
	public T create() throws InterruptedException {
		return null;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
